package com.ardais.bigr.iltds.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.ObjectNotFoundException;

import org.apache.commons.lang.StringUtils;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.TemporaryBlob;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.javabeans.BoxLayoutDto;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.pdc.javabeans.AttachmentData;
import com.ardais.bigr.security.SecurityInfo;

/**
 * Bean implementation class for Enterprise Bean: SampleOperation
 */
public class SampleOperationBean implements javax.ejb.SessionBean {

  /**
   * Given a list of sample ids, return null if all of the sample ids
   * potentially describe a real sample.  If, in addition, the
   * samplesMustExist flag is true, then this also checks that the samples
   * all currently have records in the database.  If there are any
   * errors, then a string describing at least one of the problems that
   * was detected will be returned.
   * 
   * @param sampleIds a list of sample ids to check
   * @param samplesMustExist if true, also check that the samples exist
   *     in the database
   * @param securityInfo a securityInfo object containing the account for which the
   *      samples will be added
   * @return null or an error string as described above
   */
  private String validSamples(
    List sampleIds,
    boolean samplesMustExist,
    SecurityInfo securityInfo) {
    try {
      SampleAccessBean sampleBean = new SampleAccessBean();
      SampleHome sampleHome = (SampleHome) sampleBean.getHome();

      Iterator iter = sampleIds.iterator();
      while (iter.hasNext()) {
        String sampleID = (String) iter.next();

        if (!ValidateIds.isValid(sampleID, ValidateIds.TYPESET_SAMPLE, false)) {
          return "Invalid sample id: " + sampleID;
        }

        boolean isImported =
          ValidateIds.isValid(sampleID, ValidateIds.TYPESET_SAMPLE_IMPORTED, false);

        //if the sample is not imported, make sure it corresponds to a valid ASM range
        if (!isImported) {
          try {
            FormLogic.genASMEntryID(sampleID);
          }
          catch (Exception e) {
            return "Invalid sample id: "
              + sampleID
              + ".  The id does not correspond to a valid ASM range.";
          }
        }

        try {
          sampleHome.findByPrimaryKey(new SampleKey(sampleID));
        }
        catch (ObjectNotFoundException e) {
          if (samplesMustExist) {
            return "Invalid sample id: "
              + sampleID
              + ".  The sample does not exist in the database.";
          }

          if (!IltdsUtils.isSampleCreatableByAccount(sampleID, securityInfo)) {
            return "Invalid sample id: "
              + sampleID
              + ".  The specified sample does not already exist and you are not authorized to create it.";
          }
        }
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }

    return null;
  }

  /**
   * @see SampleOperation#validSamplesForBoxScan(List, boolean)
   */
  public String validSamplesForBoxScan(
    List sampleIds,
    boolean samplesMustExist,
    boolean enforceRequestedSampleRestrictions,
    boolean enforceStorageTypes,
    SecurityInfo securityInfo,
    BoxLayoutDto boxLayoutDto) {
    String errorMessage = validSamples(sampleIds, samplesMustExist, securityInfo);

    if (errorMessage != null) {
      return errorMessage;
    }

    if (sampleIds.size() == 0) {
      return "Please enter sample(s) before continuing.";
    }

    if (sampleIds.size() > boxLayoutDto.getBoxCapacity()) {
      return "A box can contain at most "
        + boxLayoutDto.getBoxCapacity()
        + " samples, but you have supplied "
        + sampleIds.size()
        + " samples.";
    }

    Set duplicateIds = ApiFunctions.duplicateSet(sampleIds);
    if (!duplicateIds.isEmpty()) {
      //retrieve the alias value (if any) for each sample
      List samplesWithAliases = getSampleInfo(duplicateIds);
      return "The following samples appear more than once:  "
        + StringUtils.join(samplesWithAliases.iterator(), ", ");
    }

    //MR7356 - if we are to enforce restrictions on requested samples, tell the user
    //if any such samples have been passed
    if (enforceRequestedSampleRestrictions) {
      List requestedSamples = findRequestedSamplesInvalidForBoxScan(sampleIds);
      if (!requestedSamples.isEmpty()) {
        StringBuffer msg = new StringBuffer(100);
        msg.append(
          "The following samples have been packaged for a request and cannot be scanned: ");
        //retrieve the alias value (if any) for each sample
        List samplesWithAliases = getSampleInfo(requestedSamples);
        msg.append(StringUtils.join(samplesWithAliases.iterator(), ", "));
        msg.append(".");
        return msg.toString();
      }
    }

    // MR 7048 -- verify that all samples are at the same location as
    // the user...
    // one point of subtlety worth noting.  there are three scenarios that
    //   we can encounter:  1)box scan of existing samples
    //                      2)check out of existing samples
    //                      3)box scan of new samples
    //    in the third case, there won't be any location to compare to, but
    //    that is not an error condition.  The query that checks the location
    //    will not find these samples (since they are not in the database yet),
    //    so no error will be returned.  Just to be safe, we tell the method
    //    that does the location check to ignore samples that have no location
    //    value.
    List nonLocalSamples = identifyNonLocalSamples(sampleIds, securityInfo, !samplesMustExist, false);
    if (!nonLocalSamples.isEmpty()) {
      StringBuffer msg = new StringBuffer(50);
      msg.append("You have selected non-local sample(s):");
      //retrieve the alias value (if any) for each sample
      List samplesWithAliases = getSampleInfo(nonLocalSamples);
      msg.append(StringUtils.join(samplesWithAliases.iterator(), ", "));
      return msg.toString();
    }
    
    if (enforceStorageTypes) {
      // Check for common storage type(s).
      Set sampleTypes = IltdsUtils.getSampleTypesBySampleIds(sampleIds);
      if (!sampleTypes.isEmpty()) {
        Set commonStorageTypes = IltdsUtils.getCommonStorageTypesBySampleTypes(sampleTypes);

        if (commonStorageTypes.isEmpty()) {
          return "You cannot mix samples that do not have a common storage type in the same box.";
        }
      }
    }
    return null;
  }

  /**
   * Given a list of sample ids, return a list of those samples that are on requests
   * that shouldn't be allowed for a box scan.  This currently is limited to
   * samples on open requests that are in unshipped boxes.
   * 
   * @param sampleIds A list of sample ids
   * @return List - A list of sample ids that are invalid for scanning
   */
  private List findRequestedSamplesInvalidForBoxScan(List sampleIds) {
    List requestedSamples = new ArrayList();
    if (!ApiFunctions.isEmpty(sampleIds)) {
      StringBuffer sql = new StringBuffer(100);
      sql.append("select item.item_id");
      sql.append("\n from iltds_request request, iltds_request_item item, iltds_request_box box");
      //request is open
      sql.append("\n where request.closed_yn = 'N'");
      sql.append("\n and request.request_id = item.request_id");
      //item is in an unshipped box on the request
      sql.append("\n and item.box_barcode_id = box.box_barcode_id");
      sql.append("\n and box.request_id = request.request_id");
      sql.append("\n and box.shipped_yn = 'N'");
      sql.append("\n and ");
      //item is in the list passed in
      sql.append(ApiFunctions.makeBindConditionStringOrList("item.item_id", sampleIds.size()));
      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      try {
        con = ApiFunctions.getDbConnection();
        pstmt = con.prepareStatement(sql.toString());
        ApiFunctions.bindBindParameterList(pstmt, 1, sampleIds);
        rs = ApiFunctions.queryDb(pstmt, con);
        while (rs.next()) {
          requestedSamples.add(rs.getString("item_id"));
        }
      }
      catch (Exception e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
      finally {
        ApiFunctions.close(con, pstmt, rs);
      }
    }
    return requestedSamples;
  }

  public AttachmentData insertSampleAttachment(AttachmentData attachData, SecurityInfo securityInfo) {
    
   
     StringBuffer sql = new StringBuffer(256);
     sql.append("INSERT INTO iltds_attachments");
     sql.append(" (ardais_acct_key,");
     sql.append(" sample_barcode_id,");
    
     sql.append(" is_phi_yn,");
     sql.append(" comments,");
     sql.append(" name,");
     sql.append(" attachment,");
     sql.append(" created_by,");
     sql.append(" created_date,");
     sql.append(" contenttype");
    
     sql.append(")");
     sql.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

     Connection con = ApiFunctions.getDbConnection();
     TemporaryBlob blob = null;
     PreparedStatement pstmt = null;
     boolean success = false;
     try {
       // Insert the new attachment.
       pstmt = con.prepareStatement(sql.toString());
       pstmt.setString(1, attachData.getArdaisAccountKey());
       pstmt.setString(2, attachData.getSampleBarCodeId());
       pstmt.setString(3, attachData.getIsPHIYN());
       pstmt.setString(4, attachData.getComments());
       pstmt.setString(5, attachData.getName());

       blob = new TemporaryBlob(con, attachData.getAttachment());
       pstmt.setBlob(6, blob.getSQLBlob() );
       pstmt.setString(7, attachData.getCreatedBy());
       pstmt.setTimestamp(8, new java.sql.Timestamp((new java.util.Date()).getTime()));
       pstmt.setString(9, attachData.getContentType());
    
       pstmt.executeUpdate();
       success = true;
     }
     catch (Exception e) {
       ApiFunctions.throwAsRuntimeException(e);
     }
     finally {
       ApiFunctions.close(blob, con);
       ApiFunctions.close(pstmt);
       ApiFunctions.closeDbConnection(con);
     }

   /***

     //create an ICP history record if the donor was successfully created
     if (success) {
       BTXDetailsCreateDonor btxDetails = new BTXDetailsCreateDonor();
       btxDetails.setLoggedInUserSecurityInfo(securityInfo);
       btxDetails.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
       btxDetails.setTransactionType("pdc_placeholder");
       btxDetails.setDonorData(donorData);
       BtxPerformerPlaceHolder btx = new BtxPerformerPlaceHolder();
       btx.perform(btxDetails);
     } ***/

     return attachData;
   }
  
  /**
   * Identify what samples (if any) contained in a list of sample ids are non-local.
   *
   * @param  sampleIds  a list of sample ids
   * @param  securityInfo  the SecurityInfo object for the currently logged in user
   * @param  ignoreSamplesWithNoLocation  a boolean to indicate if samples with no location
   * are to be ignored (if true, they will not be returned as non-local).
   * @param  samplesMustBeInRepository a boolean to indicate if the samples must be in
   * a box at the current user's location.  True means they do, false means the samples
   * just have to have a last known location id equal to the current users location
   * @return List  a list of the samples in the sampleIds list that are non-local
   * to the currently logged in user
   */
  public List identifyNonLocalSamples(
    List sampleIds,
    SecurityInfo securityInfo,
    boolean ignoreSamplesWithNoLocation,
    boolean samplesMustBeInRepository) {

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    ArrayList nonLocalSamples = new ArrayList();

    //determine the user's location...
    String userLoc = securityInfo.getUserLocationId();

    try {
      con = ApiFunctions.getDbConnection();
      StringBuffer query = new StringBuffer(100);
      
      //if the samples have to be in the repository at the current user's location,
      //handle that case
      if (samplesMustBeInRepository) {
        query.append("SELECT sample.sample_barcode_id from iltds_sample sample");
        query.append(" where (sample.box_barcode_id is null or not exists ");
        query.append("(select 1 from iltds_box_location boxloc");
        query.append(" where sample.BOX_BARCODE_ID = boxloc.BOX_BARCODE_ID");
        query.append(" and boxloc.LOCATION_ADDRESS_ID = ?))");
        query.append(" and sample.sample_barcode_id in ");
        query.append(ApiFunctions.makeBindParameterList(sampleIds.size()));
        pstmt = con.prepareStatement(query.toString());
        pstmt.setString(1, userLoc);
        ApiFunctions.bindBindParameterList(pstmt, 2, (List) sampleIds);
        rs = ApiFunctions.queryDb(pstmt, con);
        while (rs.next()) {
          nonLocalSamples.add(ApiFunctions.safeString(rs.getString(1)));
        }
      }
      //otherwise the samples just have to have the same last_known_location_id as the
      //current user
      else {
        query.append("SELECT last_known_location_id, sample_barcode_id from iltds_sample ");
        query.append("where sample_barcode_id in ");
        query.append(ApiFunctions.makeBindParameterList(sampleIds.size()));
        if (ignoreSamplesWithNoLocation) {
          query.append(" and last_known_location_id is not null");
        }
        pstmt = con.prepareStatement(query.toString());
        ApiFunctions.bindBindParameterList(pstmt, 1, (List) sampleIds);
        rs = ApiFunctions.queryDb(pstmt, con);

        ArrayList locationIds = new ArrayList();
        ArrayList returnedSampleIds = new ArrayList();
        while (rs.next()) {
          locationIds.add(ApiFunctions.safeString(rs.getString(1)));
          returnedSampleIds.add(ApiFunctions.safeString(rs.getString(2)));
        }
        // loop thru sample locations....
        Iterator iter = locationIds.iterator();
        Iterator iterSamp = returnedSampleIds.iterator();
        while (iter.hasNext()) {
          String location = (String) iter.next();
          String sampleID = (String) iterSamp.next();
          boolean isSampleLocal = sampleLocationCheck(location, userLoc);
          if (!isSampleLocal) { // sample is a different location than the user...
            nonLocalSamples.add(sampleID);
          }
        }
      }

    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return nonLocalSamples;
  }

  private boolean sampleLocationCheck(String location, String userLoc) {

    if (location.equals(userLoc))
      return true; // same location...
    else
      return false; // different location...
  }
  
  private List getSampleInfo(Collection sampleIds) {
    List samples = new ArrayList();
    Iterator sampleIdIterator = sampleIds.iterator();
    while (sampleIdIterator.hasNext()) {
      String sampleId = (String)sampleIdIterator.next();
      String sampleAlias = null;
      try {
        SampleAccessBean sample = new SampleAccessBean(new SampleKey(sampleId));
        sampleAlias = sample.getCustomerId();
      }
      catch (Exception e) {
        //nothing to do
      }
      SampleData sample = new SampleData();
      sample.setSampleId(sampleId);
      sample.setSampleAlias(sampleAlias);
      samples.add(IltdsUtils.getSampleIdAndAlias(sample));
    }
    return samples;
  }

  private javax.ejb.SessionContext mySessionCtx;
  /**
   * getSessionContext
   */
  public javax.ejb.SessionContext getSessionContext() {
    return mySessionCtx;
  }
  /**
   * setSessionContext
   */
  public void setSessionContext(javax.ejb.SessionContext ctx) {
    mySessionCtx = ctx;
  }
  /**
   * ejbCreate
   */
  public void ejbCreate() throws javax.ejb.CreateException {
  }
  /**
   * ejbActivate
   */
  public void ejbActivate() {
  }
  /**
   * ejbPassivate
   */
  public void ejbPassivate() {
  }
  /**
   * ejbRemove
   */
  public void ejbRemove() {
  }
}
