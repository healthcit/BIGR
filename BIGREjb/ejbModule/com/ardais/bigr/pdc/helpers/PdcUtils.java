/**
 * Created on Apr 14, 2003
 *
 */
package com.ardais.bigr.pdc.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.beans.ConsentOperation;
import com.ardais.bigr.iltds.beans.ConsentOperationHome;
import com.ardais.bigr.pdc.beans.DDCDonor;
import com.ardais.bigr.pdc.beans.DDCDonorHome;
import com.ardais.bigr.pdc.javabeans.ConsentData;
import com.ardais.bigr.pdc.javabeans.DonorData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.DbUtils;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.gen.DbConstants;

public class PdcUtils {

	/**
	 * Constructor for PdcUtils.
	 */
	private PdcUtils() {
		super();
	}
	
	/**
	 * Method to determine if the specified donor is accessible to the logged in user.
	 * Creation date: (4/14/03 17:40:10 PM)
	 * @return boolean
	 * @param request javax.servlet.http.HttpServletRequest
	 * @param consentID java.lang.String
	 */
	public static boolean isDonorAccessibleByDdcUser(SecurityInfo securityInfo, String donorId) {
		//if invalid data is passed in then throw an exception
        if (securityInfo == null) {
			throw new ApiException("PdcUtils.isDonorAccessibleByDdcUser was passed a null securityInfo object.");
        }
        if (donorId == null) {
			throw new ApiException("PdcUtils.isDonorAccessibleByDdcUser was passed a null donorId.");
        }
        
        //If the user is an Ardais user, just return true. Otherwise, get the cases for this donor
        //and make sure they come from the user's institution.
    	if (securityInfo.isInRoleSystemOwner()) {
    		return true;
    	}
    	else {
    		//assume failure
    		boolean returnValue = false;
    		try {
        DDCDonorHome ddcDonorHome = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
        DDCDonor donorOperation = ddcDonorHome.create();
				DonorData donor = donorOperation.getDonorCaseSummary(donorId,false);
				if (donor.getConsents().size() > 0) {
					ConsentData consent = (ConsentData) donor.getConsents().get(0);
					String consentId = consent.getConsentId();
          ConsentOperationHome home = (ConsentOperationHome) EjbHomes.getHome(ConsentOperationHome.class);
          ConsentOperation bean = home.create();
	    			String donorAccount = bean.getCaseOrigin(consentId);				
					String userAccount = securityInfo.getAccount();
					returnValue = (donorAccount.equalsIgnoreCase(userAccount));
				}
        //if the donor was imported by a user in the same account as the current user,
        //allow access
        if (!returnValue){
          donor.setArdaisId(donorId);
          DonorData donorData = donorOperation.getDonorProfile(donor);
          if ("Y".equalsIgnoreCase(donorData.getImportedYN()) && securityInfo.getAccount().equals(donorData.getArdaisAccountKey())) {
            returnValue = true;
          }
        }
        
    	}
			catch (Exception e) {
				ApiFunctions.throwAsRuntimeException(e);
			}
			return returnValue;
    	}
	}
  
  public static boolean isCustomerIdUniqueInAccount(boolean isNew, String ardaisId, String customerId, String accountId) {
    if (ApiFunctions.isEmpty(customerId)) {
      throw new ApiException("customerId cannot be null");
    }
    if (ApiFunctions.isEmpty(accountId)) {
      throw new ApiException("accountId cannot be null");
    }
    if (!isNew && ApiFunctions.isEmpty(ardaisId)) {
      throw new ApiException("ardaisId cannot be null");
    }
    boolean returnValue = false;
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet results = null;
    try {
      conn = ApiFunctions.getDbConnection();
      //if this is a new donor, there can't be any donors with the specified customer id
      if (isNew) {
        stmt = conn.prepareStatement("select count(1) as count from pdc_ardais_donor where upper(customer_id) = ? and ardais_acct_key = ?");
        stmt.setString(1, customerId.toUpperCase());
        stmt.setString(2, accountId);
      }
      //if this is not a new donor, there can't be any donors with the specified customer id
      //other than the one with the specified Ardais Id
      else {
        stmt = conn.prepareStatement("select count(1) as count from pdc_ardais_donor where upper(customer_id) = ? and ardais_acct_key = ? and ardais_id <> ?");
        stmt.setString(1, customerId.toUpperCase());
        stmt.setString(2, accountId);
        stmt.setString(3, ardaisId);
      }
      results = stmt.executeQuery();
      results.next();
      int count = results.getInt("count");
      returnValue = (count <= 0);
    }
    catch (SQLException e){
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(conn, stmt, results);
    }
    
    return returnValue;
  }
  
  public static List findDonorIdsFromCustomerId(String customerId) {
    if (ApiFunctions.isEmpty(customerId)) {
      throw new ApiException("customerId cannot be null");
    }
    List returnValue = new ArrayList();
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet results = null;
    try {
      conn = ApiFunctions.getDbConnection();
      stmt = conn.prepareStatement("select ardais_id from pdc_ardais_donor where upper(customer_id) = ?");
      stmt.setString(1, customerId.toUpperCase());
      results = stmt.executeQuery();
      while (results.next()) {
        returnValue.add(results.getString("ardais_id"));
      }
    }
    catch (SQLException e){
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(conn, stmt, results);
    }
    
    return returnValue;
  }
  
  public static String findDonorIdFromCustomerId(String customerId, String accountId) {
    if (ApiFunctions.isEmpty(customerId)) {
      throw new ApiException("customerId cannot be null");
    }
    if (ApiFunctions.isEmpty(accountId)) {
      throw new ApiException("accountId cannot be null");
    }
    String returnValue = null;
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet results = null;
    try {
      conn = ApiFunctions.getDbConnection();
      stmt = conn.prepareStatement("select ardais_id from pdc_ardais_donor where upper(customer_id) = ? and ardais_acct_key = ?");
      stmt.setString(1, customerId.toUpperCase());
      stmt.setString(2, accountId);
      results = stmt.executeQuery();
      if (results.next()) {
        returnValue = results.getString("ardais_id");
      }
    }
    catch (SQLException e){
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(conn, stmt, results);
    }
    
    return ApiFunctions.safeString(returnValue);
  }

  public static List findDonorIdsFromLikeCustomerId(String customerId, String accountId) {
    if (ApiFunctions.isEmpty(customerId)) {
      throw new ApiException("customerId cannot be null");
    }
    if (ApiFunctions.isEmpty(accountId)) {
      throw new ApiException("accountId cannot be null");
    }

    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet results = null;
    boolean hasWildCharacter = false;
    StringBuffer selectSql = new StringBuffer(128);
    List donorList = new ArrayList();
    
    if (customerId.indexOf('*') >= 0) {
      hasWildCharacter = true;  
    }
    try {
      conn = ApiFunctions.getDbConnection();
      selectSql.append("select ardais_id from pdc_ardais_donor where upper(customer_id)");
      if (hasWildCharacter) {
        selectSql.append(" LIKE ");  
      }
      else {
        selectSql.append(" = ");  
      }
      selectSql.append("?");
      if (hasWildCharacter) {
        selectSql.append(" ESCAPE '/'");  
      }
      
      selectSql.append(" and ardais_acct_key = ?");
      
      stmt = conn.prepareStatement(selectSql.toString());
      if (hasWildCharacter) {
        stmt.setString(1, DbUtils.convertLikeSpecialChars(customerId.toUpperCase()));
      }
      else {
        stmt.setString(1, customerId.toUpperCase());
      }
      stmt.setString(2, accountId);
      results = stmt.executeQuery();
      while (results.next()) {
        donorList.add(results.getString("ardais_id"));
      }
    }
    catch (SQLException e){
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(conn, stmt, results);
    }
    
    return donorList;
  }
  
  /** Stores the last used donor, case, and sample ids in the session for use 
   * in subsequent operations.  Currently used by DDC and Sample Intake transactions.
   * @param request the HttpServletRequest
   * @param donorId  the donor id to store.  Must be specified to set caseid and/or sampleid.
   * @param caseId  the case id to store.  Must be specified to set sample id.
   * @param sampleeId  the sample id to store
   * @param ignoreNulls  a flag to indicate if a null value should be ignored for an id
   *                      or if the value should be set to null
   */
  public static void storeLastUsedDonorCaseAndSample(HttpServletRequest request, 
                                                     String donorId, String caseId, String sampleId,
                                                     boolean ignoreNulls) {
    storeLastUsedDonorCaseAndSample(request, donorId, null, caseId, null, sampleId, null, ignoreNulls);
  }
  
  /** Stores the last used donor, case, and sample ids in the session for use 
   * in subsequent operations.  Currently used by DDC and Sample Intake transactions.
   * @param request the HttpServletRequest
   * @param donorId  the donor id to store.  Must be specified to set donor alias, caseid 
   *                 and/or sampleid.
   * @param donorAlias  the donor alias to store.
   * @param caseId  the case id to store.  Must be specified to set case alias and sample id.
   * @param caseAlias  the case alias to store.
   * @param sampleId  the sample id to store.
   * @param sampleAlias  the sample alias to store.
   * @param ignoreNulls  a flag to indicate if a null value should be ignored for an id
   *                      or if the value should be set to null
   */
  public static void storeLastUsedDonorCaseAndSample(HttpServletRequest request, 
                                                     String donorId, String donorAlias, 
                                                     String caseId, String caseAlias,
                                                     String sampleId, String sampleAlias,
                                                     boolean ignoreNulls) {
    //ids are expected in a hierarchy from donor to case to sample, so don't allow a value to be 
    //set unless the values higher in the hierarchy are specified.  For example, don't allow a 
    //case id to be saved without a corresponding donor id. Also, don't allow an alias to be set
    //if its corresponding id was not set
    donorId = ApiFunctions.safeTrim(donorId);
    donorAlias = ApiFunctions.safeToString(donorAlias);
    caseId = ApiFunctions.safeTrim(caseId);
    caseAlias = ApiFunctions.safeTrim(caseAlias);
    sampleId = ApiFunctions.safeTrim(sampleId);
    sampleAlias = ApiFunctions.safeTrim(sampleAlias);
    if (!ApiFunctions.isEmpty(caseId) && ApiFunctions.isEmpty(donorId)) {
      throw new ApiException("Donor id is required when specifying case id.");
    }
    if (!ApiFunctions.isEmpty(sampleId) && (ApiFunctions.isEmpty(donorId) || ApiFunctions.isEmpty(caseId))) {
      throw new ApiException("Donor id and case id are required when specifying sample id.");
    }
    if (!ApiFunctions.isEmpty(donorAlias) && ApiFunctions.isEmpty(donorId)) {
      throw new ApiException("Donor id is required when specifying donor alias.");
    }
    if (!ApiFunctions.isEmpty(caseAlias) && ApiFunctions.isEmpty(caseId)) {
      throw new ApiException("Case id is required when specifying case alias.");
    }
    if (!ApiFunctions.isEmpty(sampleAlias) && ApiFunctions.isEmpty(sampleId)) {
      throw new ApiException("Sample id is required when specifying sample alias.");
    }
    
    //proceed to save data
    if (request != null) {
      HttpSession session = request.getSession(false);
      if (session != null) {
        
        //handle donor
        if (!ApiFunctions.isEmpty(donorId)) {
          //if the saved donor value does not equal the new donor value,
          //save the new donor value and clear out the existing case and sample values.
          //Anytime there is a change at a level in the hierarchy, the items below that
          //level need to be cleared.
          String savedDonor = ApiFunctions.safeTrim(ApiFunctions.safeString((String)session.getAttribute(Constants.MOST_RECENT_DONOR_ID)));
          if (!savedDonor.equalsIgnoreCase(donorId)) {
            session.setAttribute(Constants.MOST_RECENT_DONOR_ID, donorId);
            if (!ApiFunctions.isEmpty(donorAlias)) {
              session.setAttribute(Constants.MOST_RECENT_DONOR_ALIAS, donorAlias);
            }
            else {
              session.removeAttribute(Constants.MOST_RECENT_DONOR_ALIAS);
            }
            session.removeAttribute(Constants.MOST_RECENT_CASE_ID);
            session.removeAttribute(Constants.MOST_RECENT_CASE_ALIAS);
            session.removeAttribute(Constants.MOST_RECENT_SAMPLE_ID);
            session.removeAttribute(Constants.MOST_RECENT_SAMPLE_ALIAS);
          }
        }
        else {
          //if we're not ignoring nulls, remove any stored value for the donor.  This necessitates
          //the removal of the case and sample ids, since anytime there is a change at a level in 
          //the hierarchy the items below that level need to be cleared.
          if (!ignoreNulls) {
            session.removeAttribute(Constants.MOST_RECENT_DONOR_ID);
            session.removeAttribute(Constants.MOST_RECENT_DONOR_ALIAS);
            session.removeAttribute(Constants.MOST_RECENT_CASE_ID);
            session.removeAttribute(Constants.MOST_RECENT_CASE_ALIAS);
            session.removeAttribute(Constants.MOST_RECENT_SAMPLE_ID);
            session.removeAttribute(Constants.MOST_RECENT_SAMPLE_ALIAS);
          }
        }
        
        //handle case
        if (!ApiFunctions.isEmpty(caseId)) {
          //if the saved case value does not equal the new case value,
          //save the new case value and clear out the existing sample value
          //Anytime there is a change at a level in the hierarchy, the items below that
          //level need to be cleared.
          String savedCase = ApiFunctions.safeTrim(ApiFunctions.safeString((String)session.getAttribute(Constants.MOST_RECENT_CASE_ID)));
          if (!savedCase.equalsIgnoreCase(caseId)) {
            session.setAttribute(Constants.MOST_RECENT_CASE_ID, caseId);
            if (!ApiFunctions.isEmpty(caseAlias)) {
              session.setAttribute(Constants.MOST_RECENT_CASE_ALIAS, caseAlias);
            }
            else {
              session.removeAttribute(Constants.MOST_RECENT_CASE_ALIAS);
            }
            session.removeAttribute(Constants.MOST_RECENT_SAMPLE_ID);
            session.removeAttribute(Constants.MOST_RECENT_SAMPLE_ALIAS);
          }
        }
        else {
          //if we're not ignoring nulls, remove any stored value for the case.  This necessitates
          //the removal of the sample id, since anytime there is a change at a level in 
          //the hierarchy the items below that level need to be cleared.
          if (!ignoreNulls) {
            session.removeAttribute(Constants.MOST_RECENT_CASE_ID);
            session.removeAttribute(Constants.MOST_RECENT_CASE_ALIAS);
            session.removeAttribute(Constants.MOST_RECENT_SAMPLE_ID);
            session.removeAttribute(Constants.MOST_RECENT_SAMPLE_ALIAS);
          }
        }
        
        //handle sample        
        if (!ApiFunctions.isEmpty(sampleId)) {
          //if the saved sample value does not equal the new sample value,
          //save the new sample value
          String savedSample = ApiFunctions.safeTrim(ApiFunctions.safeString((String)session.getAttribute(Constants.MOST_RECENT_SAMPLE_ID)));
          if (!savedSample.equalsIgnoreCase(sampleId)) {
            session.setAttribute(Constants.MOST_RECENT_SAMPLE_ID, sampleId);
            if (!ApiFunctions.isEmpty(sampleAlias)) {
              session.setAttribute(Constants.MOST_RECENT_SAMPLE_ALIAS, sampleAlias);
            }
            else {
              session.removeAttribute(Constants.MOST_RECENT_SAMPLE_ALIAS);
            }
          }
        }
        else {
          //if we're not ignoring nulls, remove any stored value for the sample.
          if (!ignoreNulls) {
            session.removeAttribute(Constants.MOST_RECENT_SAMPLE_ID);
            session.removeAttribute(Constants.MOST_RECENT_SAMPLE_ALIAS);
          }
        }
      }
    }
  }


  /**
   * Checks to see if the path report has been abstarcted. If the value of disease_concept_id
   * is not null, then it has been abstracted.
   * @param pathReportId
   * @return true if the path report has been abstracted, otherwise false.
   * 
   */
  public static boolean isPathReportAbstracted(String pathReportId) {
    boolean isPresent = false;
    if (ApiFunctions.isEmpty(pathReportId)) {
      return isPresent;
    }

    String query =
      "SELECT "
        + DbConstants.PATH_DISEASE
        + " FROM "
        + DbConstants.TABLE_PATH
        + " WHERE "
        + DbConstants.PATH_ID
        + " = ?";
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      pstmt.setInt(1, ApiFunctions.safeInteger(pathReportId, 0));
      rs = pstmt.executeQuery();
      if (rs.next()) {
        if (!ApiFunctions.isEmpty(rs.getString(DbConstants.PATH_DISEASE))) {
          isPresent = true;  
        }
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }  
    return isPresent;  
  }
  
  public static boolean hasRawPathReport(String pathReportId) {
    boolean isPresent = false;
    if (ApiFunctions.isEmpty(pathReportId)) {
      return isPresent;
    }

    String query =
      "SELECT "
        + DbConstants.PATH_ASCII_REPORT
        + " FROM "
        + DbConstants.TABLE_PATH
        + " WHERE "
        + DbConstants.PATH_ID
        + " = ?";
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      pstmt.setInt(1, ApiFunctions.safeInteger(pathReportId, 0));
      rs = pstmt.executeQuery();
      if (rs.next()) {
        if (!ApiFunctions.isEmpty(ApiFunctions.getStringFromClob(rs.getClob(DbConstants.PATH_ASCII_REPORT)))) {
          isPresent = true;  
        }
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }  
    return isPresent; 
  }
}
