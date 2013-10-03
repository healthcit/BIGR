package com.ardais.bigr.id.btx;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.btx.framework.BtxTransactionPerformerBase;
import com.ardais.bigr.iltds.bizlogic.SampleFinder;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.security.SecurityInfo;
import com.gulfstreambio.bigr.error.BigrValidationError;
import com.gulfstreambio.bigr.error.BigrValidationErrors;
import com.gulfstreambio.bigr.error.BigrValidationException;
import com.gulfstreambio.bigr.error.ErrorUtils;

public class BtxPerformerIdServices extends BtxTransactionPerformerBase {

  // The maximum number of sample ids in a namespace.  Since each namespace is 3 digits, this 
  // leaves 5 hex digits for the namespace.  1048575 is equal to FFFFF in hex. 
  private final int MAX_NAMESPACE_COUNT = 1048575;

  // The default number of samples in an assigned sample range.  We'll create a range with this
  // many sample ids by default when necessary, unless there are not enough ids left in the 
  // namespace.  Note that 8192 = 2000 in hex, which allows 128 ranges within a namespace.
  private final int DEFAULT_RANGE_COUNT = 8192;

  public BtxPerformerIdServices() {
    super();
  }

  private BTXDetails performGenerateSampleId(BtxDetailsGenerateSampleId btxDetails) 
    throws Exception {

    try {
      SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
      String sampleId = null;
      while (sampleId == null) {
        sampleId = getNextUnusedSampleId(securityInfo);
        
        // If we didn't find a sample id in the existing assigned ranges, then add a new assigned
        // range an try again.
        if (sampleId == null) {
          assignNextSampleRange(securityInfo);
        }
      }
      
      btxDetails.setSampleId(sampleId);
      btxDetails.setActionForwardTXCompleted("success");
    }
    catch (BigrValidationException e) {
      btxDetails.setValidationErrors(e.getErrors());
      btxDetails.setActionForwardRetry();
    }
    return btxDetails;
  }

  /**
   * Returns the next unused sample id within the currently assigned ranges.  Generally, this 
   * should be the value of the next id in the last assigned range, but this method will start
   * with the first range that is not fully used and check all ranges that are not fully used
   * until an unused sample id is found.
   * 
   * @param securityInfo the <code>SecurityInfo</code> of the user
   * @return The sample id, or <code>null</code> if there are no unused sample ids within the
   *         assigned ranges for the user's account.
   */
  private String getNextUnusedSampleId(SecurityInfo securityInfo) {
    String sampleId = null;

    // Create the query to get the next sample id.
    String sql = "select sample_id_start, sample_id_end, sample_id_next"
        + " from iltds_assigned_sample_ids"
        + " where ardais_acct_key = ?"
        + " and sample_id_next <= sample_id_end"
        + " order by sample_id_start";
    
    // Prepare and perform the query.
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(sql);
      pstmt.setString(1, securityInfo.getAccount());
      rs = pstmt.executeQuery();
      
      // Even though the query should only return a single assigned range row, since we use all ids  
      // within an assigned range before creating another, we write the code to handle multiple
      // rows properly to be as robust as possible.  Note that the query could also not return any 
      // rows if all ids within all assigned ranges have been used.
      boolean foundId = false;
      while (!foundId && rs.next()) {
        String sampleIdStart = rs.getString("sample_id_start");
        String sampleIdEnd = rs.getString("sample_id_end");
        sampleId = rs.getString("sample_id_next");
        
        // Check that the next id is not already used by querying for a sample with that id.
        // We expect that the next id in the range is not used since any code that uses a generated
        // id should be calling this method, but we check anyway to be as robust as possible.  If 
        // the id is used then calculate the next one in and keep looking until we find an id that 
        // is not used or we reach the end of the assigned range.
        while (!foundId && sampleId.compareTo(sampleIdEnd) <= 0) {
          if (!SampleFinder.exists(sampleId)) {
            foundId = true;
          }
          else {
            sampleId = calculateNextSampleId(sampleId);
          }
        }
        
        // If we reached the end of the assigned range and did not find an unused id, then update 
        // the next id in the database with an id that is the next id past the end of the range 
        // so this range will not be looked at again.  This will be the last sample id we checked.
        // Also make sure to set the sample id to null since this is what we return.
        if (!foundId) {
          updateNextSampleId(sampleIdStart, sampleId);
          sampleId = null;
        }
        
        // If we found an unused next id then calculate the next id to write to the database for 
        // next time this method is called, and return the id.
        else {
          updateNextSampleId(sampleIdStart, calculateNextSampleId(sampleId));
        }
      }
    }
    catch (Exception e) {
      ErrorUtils.throwAsBigrException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return sampleId;
  }
  
  /**
   * Updates the next sample id in the database in the specified assigned range.  This is the next
   * sample id that will be returned, if it is not used, the next time 
   * {@link #generateId(SecurityInfo)} is called.
   * 
   * @param sampleIdStart the starting sample id of the assigned range
   * @param sampleIdNext the next sample id to store in the database
   */
  private void updateNextSampleId(String sampleIdStart, String sampleIdNext) {
    String sql = 
      "update iltds_assigned_sample_ids set sample_id_next = ? where sample_id_start = ?";
    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(sql);
      pstmt.setString(1, sampleIdNext);
      pstmt.setString(2, sampleIdStart);
      pstmt.executeUpdate();
    }
    catch (Exception e) {
      ErrorUtils.throwAsBigrException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }
  }
  
  /**
   * Calculates the next sample id after the specified sample id.
   * 
   * @param sampleId the current sample id 
   * @return The next sample id.
   */
  private String calculateNextSampleId(String sampleId) {
    int numericOnly = Integer.parseInt(sampleId.substring(2), 16);
    return "SX" + ApiFunctions.padLeft(Integer.toHexString(++numericOnly).toUpperCase(), '0', 8);
  }
  
  /**
   * Assigns the next sample range within the user's assigned namespace.  This calls the stored
   * procedure that was written to do so. 
   * 
   * @param securityInfo the <code>SecurityInfo</code> of the user
   * @throws BigrValidationException if all of the ids in the namespace are used
   */
  private void assignNextSampleRange(SecurityInfo securityInfo) throws BigrValidationException {
    int numSamples = calculateNextRangeCount();
    if (numSamples <= 0) {
      BigrValidationErrors errors = new BigrValidationErrors();
      errors.add(new BigrValidationError("com.gulfstreambio.bigr.id.namespaceFull"));
      throw new BigrValidationException(errors);
    }
    
    // Create the call to the stored procedure that assigns the next range.
    String sql = "{ call bigr_assign_sample_ids(?,?,?,?,?,?) }";
    
    Connection con = null;
    CallableStatement cstmt = null;
    try {
      con = ApiFunctions.getDbConnection();
      cstmt = con.prepareCall(sql);
      cstmt.setString(1, securityInfo.getUsername());
      cstmt.setInt(2, numSamples);
      cstmt.setString(3, securityInfo.getAccount());
      cstmt.registerOutParameter(4, Types.VARCHAR);
      cstmt.registerOutParameter(5, Types.VARCHAR);
      cstmt.registerOutParameter(6, Types.VARCHAR);
      cstmt.execute();
    }
    catch (Exception e) {
      ErrorUtils.throwAsBigrException(e);
    }
    finally {
      ApiFunctions.close(cstmt);
      ApiFunctions.close(con);
    }
  }

  /**
   * Calculates and returns the sample id count for the next assigned range.  This will always 
   * return the default range count if there is enough room available in the namespace, and will
   * otherwise return the number of available ids left in the namespace.  If there are no ids
   * left in the namespace, then zero is returned.
   *    
   * @return The sample id count for the next assigned range.
   */
  private int calculateNextRangeCount() {
    // We'll return the default range count if there is enough room in the namespace.
    int count = DEFAULT_RANGE_COUNT; 
    
    // Perform a query to get the last sample id of the last assigned range. 
    String sql = "select sample_id_end from iltds_assigned_sample_ids order by sample_id_end desc";
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(sql);
      rs = pstmt.executeQuery();
      
      // Get the last sample id and get its numeric value.  We'll get the numeric value
      // that is past the 3 namespace digits, i.e. we'll get the last 5 digits of the sample
      // id.  From that, determine how many sample ids remain in the namespace, and if that is
      // less than the default range count then we'll return that.  Note that if no rows are
      // returned, we will correctly return the default range count set above.
      if (rs.next()) {
        String sampleIdEnd = rs.getString("sample_id_end");
        int numericOnly = Integer.parseInt(sampleIdEnd.substring(5), 16);
        int available = MAX_NAMESPACE_COUNT - numericOnly;
        if (available < DEFAULT_RANGE_COUNT) {
          count = (available > 0) ? available : 0;
        }
      }
    }
    catch (Exception e) {
      ErrorUtils.throwAsBigrException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    
    return count;
  }

  /**
   * Invoke the specified method on this class.  This is only meant to be
   * called from BtxTransactionPerformerBase, please don't call it from anywhere
   * else as a mechanism to gain access to private methods in this class.  Every
   * object that the BTX framework dispatches to must contain this
   * method definition, and its implementation should be exactly the same
   * in each class.  Please don't alter this method or its implementation
   * in any way.
   */
  protected BTXDetails invokeBtxEntryPoint(java.lang.reflect.Method method, BTXDetails btxDetails)
    throws Exception {

    // **** DO NOT EDIT THIS METHOD, see the Javadoc comment above.
    return (BTXDetails) method.invoke(this, new Object[] { btxDetails });
  }

}
