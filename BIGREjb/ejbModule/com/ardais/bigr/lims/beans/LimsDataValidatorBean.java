package com.ardais.bigr.lims.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.lims.helpers.LimsConstants;

/**
 * Bean implementation class for Enterprise Bean: LIMSDataValidator
 */
public class LimsDataValidatorBean implements javax.ejb.SessionBean {
  private javax.ejb.SessionContext mySessionCtx;

  //method to validate that a user is a valid LIMS user
  public boolean isValidLimsUser(String username) {
    if (username == null || username.equals("")) {
      return false;
    }
    Connection connection = null;
    PreparedStatement pstmt = null;
    ResultSet results = null;
    boolean returnValue = false;
    try {
      connection = ApiFunctions.getDbConnection();
      String sql = "select count(1) as count from ES_ARDAIS_USER where ARDAIS_USER_ID=?";
      pstmt = connection.prepareStatement(sql);
      pstmt.setString(1, username);
      results = ApiFunctions.queryDb(pstmt, connection);
      int count = 0;
      results.next();
      count = results.getInt("count");
      returnValue = count > 0;
    }
    catch (SQLException se) {
      throw new ApiException("QUERY_ERROR in LimsDataValidatorBean.isValidLimsUser", se);
    }
    finally {
      ApiFunctions.close(connection, pstmt, results);
    }
    return returnValue;
  }

  //method to validate that a sample id is valid.  If we've already determined if this sample
  //exists, return that value.  Otherwise query the database to get the sample id and receipt date
  //values for the sample.  If no rows are returned the sample doesn't exist, and if a row 
  //is returned the sample does exist.
  public boolean doesSampleExist(String sampleId) {
    if (sampleId == null || sampleId.equals("")) {
      return false;
    }

    Connection connection = null;
    PreparedStatement pstmt = null;
    ResultSet results = null;
    boolean sampleExists = false;
    try {
      connection = ApiFunctions.getDbConnection();
      String sql =
        "SELECT COUNT(1) AS COUNT FROM ILTDS_SAMPLE WHERE SAMPLE_BARCODE_ID = ? AND LAST_KNOWN_LOCATION_ID = ?";
      pstmt = connection.prepareStatement(sql);
      pstmt.setString(1, sampleId);
      pstmt.setString(2, FormLogic.ARDAIS_LOCATION);
      results = ApiFunctions.queryDb(pstmt, connection);
      int count = 0;
      results.next();
      count = results.getInt("COUNT");
      sampleExists = (count > 0);
    }
    catch (SQLException se) {
      throw new ApiException("QUERY_ERROR in LimsDataValidatorBean.doesSampleExist", se);
    }
    finally {
      ApiFunctions.close(connection, pstmt, results);
    }
    return sampleExists;
  }

  //method to validate that a slide id is valid.  If we've already determined that this slide
  //exists, return true.  Otherwise query the database.
  public boolean doesSlideExist(String slideId) {
    if (slideId == null || slideId.equals("")) {
      return false;
    }

    Connection connection = null;
    PreparedStatement pstmt = null;
    ResultSet results = null;
    boolean slideExists = false;
    try {
      connection = ApiFunctions.getDbConnection();
      String sql = "SELECT COUNT(1) AS COUNT FROM LIMS_SLIDE WHERE SLIDE_ID = ?";
      pstmt = connection.prepareStatement(sql);
      pstmt.setString(1, slideId);
      results = ApiFunctions.queryDb(pstmt, connection);
      int count = 0;
      results.next();
      count = results.getInt("COUNT");
      slideExists = (count > 0);
    }
    catch (SQLException se) {
      throw new ApiException("QUERY_ERROR in LimsDataValidatorBean.doesSlideExist", se);
    }
    finally {
      ApiFunctions.close(connection, pstmt, results);
    }
    return slideExists;
  }

  //method to validate that an evaluation id is valid.  If we've already determined that this evaluation
  //exists, return true.  Otherwise query the database.
  public boolean doesEvaluationExist(String evaluationId) {
    if (evaluationId == null || evaluationId.equals("")) {
      return false;
    }
    Connection connection = null;
    PreparedStatement pstmt = null;
    ResultSet results = null;
    boolean evaluationExists = false;
    try {
      connection = ApiFunctions.getDbConnection();
      String sql = "SELECT COUNT(1) AS COUNT FROM LIMS_PATHOLOGY_EVALUATION WHERE PE_ID = ?";
      pstmt = connection.prepareStatement(sql);
      pstmt.setString(1, evaluationId);
      results = ApiFunctions.queryDb(pstmt, connection);
      int count = 0;
      results.next();
      count = results.getInt("COUNT");
      evaluationExists = (count > 0);
    }
    catch (SQLException se) {
      throw new ApiException("QUERY_ERROR in LimsDataValidatorBean.doesEvaluationExist", se);
    }
    finally {
      ApiFunctions.close(connection, pstmt, results);
    }
    return evaluationExists;
  }

  //method to determine if an evaluation has been migrated (i.e. created prior to LIMS2).
  public boolean isEvaluationMigrated(String evaluationId) {
    if (evaluationId == null || evaluationId.equals("")) {
      return false;
    }
    Connection connection = null;
    PreparedStatement pstmt = null;
    ResultSet results = null;
    boolean result = false;
    String value = null;
    try {
      connection = ApiFunctions.getDbConnection();
      String sql = "SELECT MIGRATED_YN FROM LIMS_PATHOLOGY_EVALUATION WHERE PE_ID = ?";
      pstmt = connection.prepareStatement(sql);
      pstmt.setString(1, evaluationId);
      results = ApiFunctions.queryDb(pstmt, connection);
      while (results.next()) {
        value = results.getString("MIGRATED_YN");
      }
      result = ApiFunctions.safeEquals(LimsConstants.LIMS_DB_YES, value);
    }
    catch (SQLException se) {
      throw new ApiException("QUERY_ERROR in LimsDataValidatorBean.isEvaluationMigrated", se);
    }
    finally {
      ApiFunctions.close(connection, pstmt, results);
    }
    return result;
  }

  //method to determine if the parent sample for a slide is pulled.  Gets the sample id
  //from the database and then calls isSamplePulled.
  public boolean isParentSamplePulled(String slideId) {
    Connection connection = null;
    PreparedStatement pstmt = null;
    ResultSet results = null;
    boolean result = false;
    try {
      connection = ApiFunctions.getDbConnection();
      String sql = "SELECT SAMPLE_BARCODE_ID FROM LIMS_SLIDE WHERE SLIDE_ID = ?";
      pstmt = connection.prepareStatement(sql);
      pstmt.setString(1, slideId);
      results = pstmt.executeQuery();
      if (results.next()) {
        String sampleId = results.getString("SAMPLE_BARCODE_ID");
        if (sampleId == null || sampleId.equals("")) {
          result = false;
        }
        else {
          result = isSamplePulled(sampleId);
        }
      }
      else {
        result = false;
      }
    }
    catch (SQLException se) {
      throw new ApiException("QUERY_ERROR in LimsDataValidatorBean.isParentSamplePulled", se);
    }
    finally {
      ApiFunctions.close(connection, pstmt, results);
    }
    return result;
  }

  //method to determine if a sample is pulled.
  public boolean isSamplePulled(String sampleId) {
    //if the sample doesn't exist, return false 
    if (!doesSampleExist(sampleId)) {
      return false;
    }
    //query the database to get the sample pull value.
    Connection connection = null;
    PreparedStatement pstmt = null;
    ResultSet results = null;
    boolean result = false;
    try {
      connection = ApiFunctions.getDbConnection();
      String sql = "SELECT PULL_YN FROM ILTDS_SAMPLE WHERE SAMPLE_BARCODE_ID = ?";
      pstmt = connection.prepareStatement(sql);
      pstmt.setString(1, sampleId);
      results = pstmt.executeQuery();
      if (results.next()) {
        String pullValue = results.getString("PULL_YN");
        result = pullValue != null && pullValue.equalsIgnoreCase(LimsConstants.LIMS_DB_YES);
      }
      else {
        result = false;
      }
    }
    catch (SQLException se) {
      throw new ApiException("QUERY_ERROR in LimsDataValidatorBean.isSamplePulled", se);
    }
    finally {
      ApiFunctions.close(connection, pstmt, results);
    }
    return result;
  }

  //method to determine if a sample is discordant.
  public boolean isSampleDiscordant(String sampleId) {
    //if the sample doesn't exist, return false 
    if (!doesSampleExist(sampleId)) {
      return false;
    }
    //query the database to get the sample pull value.
    Connection connection = null;
    PreparedStatement pstmt = null;
    ResultSet results = null;
    boolean result = false;
    try {
      connection = ApiFunctions.getDbConnection();
      String sql = "SELECT DISCORDANT_YN FROM ILTDS_SAMPLE WHERE SAMPLE_BARCODE_ID = ?";
      pstmt = connection.prepareStatement(sql);
      pstmt.setString(1, sampleId);
      results = pstmt.executeQuery();
      if (results.next()) {
        String discordantValue = results.getString("DISCORDANT_YN");
        result =
          discordantValue != null && discordantValue.equalsIgnoreCase(LimsConstants.LIMS_DB_YES);
      }
      else {
        result = false;
      }
    }
    catch (SQLException se) {
      throw new ApiException("QUERY_ERROR in LimsDataValidatorBean.isSampleDiscordant", se);
    }
    finally {
      ApiFunctions.close(connection, pstmt, results);
    }
    return result;
  }

  //method to determine if a sample is released.
  public boolean isSampleReleased(String sampleId) {
    //if the sample doesn't exist, return false 
    if (!doesSampleExist(sampleId)) {
      return false;
    }
    //query the database to get the sample pull value.
    Connection connection = null;
    PreparedStatement pstmt = null;
    ResultSet results = null;
    boolean result = false;
    try {
      connection = ApiFunctions.getDbConnection();
      String sql = "SELECT RELEASED_YN FROM ILTDS_SAMPLE WHERE SAMPLE_BARCODE_ID = ?";
      pstmt = connection.prepareStatement(sql);
      pstmt.setString(1, sampleId);
      results = pstmt.executeQuery();
      if (results.next()) {
        String releasedValue = results.getString("RELEASED_YN");
        result = releasedValue != null && releasedValue.equalsIgnoreCase(LimsConstants.LIMS_DB_YES);
      }
      else {
        result = false;
      }
    }
    catch (SQLException se) {
      throw new ApiException("QUERY_ERROR in LimsDataValidatorBean.isSampleReleased", se);
    }
    finally {
      ApiFunctions.close(connection, pstmt, results);
    }
    return result;
  }

  //method to determine if a sample is qcposted.
  public boolean isSampleQCPosted(String sampleId) {
    //if the sample doesn't exist, return false 
    if (!doesSampleExist(sampleId)) {
      return false;
    }
    //query the database to get the sample qcposted value.
    Connection connection = null;
    PreparedStatement pstmt = null;
    ResultSet results = null;
    boolean result = false;
    try {
      connection = ApiFunctions.getDbConnection();
      String sql = "SELECT QCPOSTED_YN FROM ILTDS_SAMPLE WHERE SAMPLE_BARCODE_ID = ?";
      pstmt = connection.prepareStatement(sql);
      pstmt.setString(1, sampleId);
      results = pstmt.executeQuery();
      if (results.next()) {
        String qcPostedValue = results.getString("QCPOSTED_YN");
        result = qcPostedValue != null && qcPostedValue.equalsIgnoreCase(LimsConstants.LIMS_DB_YES);
      }
      else {
        result = false;
      }
    }
    catch (SQLException se) {
      throw new ApiException("QUERY_ERROR in LimsDataValidatorBean.isSampleQCPosted", se);
    }
    finally {
      ApiFunctions.close(connection, pstmt, results);
    }
    return result;
  }

  //method to determine if a sample is requested.
  public boolean isSampleRequested(String sampleId) {
    //if the sample doesn't exist, return false 
    if (!doesSampleExist(sampleId)) {
      return false;
    }
    //query the database to get the sample pull value.
    Connection connection = null;
    PreparedStatement pstmt = null;
    ResultSet results = null;
    boolean result = false;
    try {
      connection = ApiFunctions.getDbConnection();
      String sql = "SELECT QC_STATUS, INV_STATUS FROM ILTDS_SAMPLE WHERE SAMPLE_BARCODE_ID = ?";
      pstmt = connection.prepareStatement(sql);
      pstmt.setString(1, sampleId);
      results = pstmt.executeQuery();
      if (results.next()) {
        String qcStatus = results.getString("QC_STATUS");
        String invStatus = results.getString("INV_STATUS");
        result =
          FormLogic.SMPL_QCINPROCESS.equalsIgnoreCase(qcStatus)
            || FormLogic.SMPL_ARMVTOPATH.equalsIgnoreCase(invStatus)
            || FormLogic.SMPL_RNDREQUEST.equalsIgnoreCase(invStatus)
            || FormLogic.SMPL_CORND.equalsIgnoreCase(invStatus);
      }
      else {
        result = false;
      }
    }
    catch (SQLException se) {
      throw new ApiException("QUERY_ERROR in LimsDataValidatorBean.isSampleRequested", se);
    }
    finally {
      ApiFunctions.close(connection, pstmt, results);
    }
    return result;
  }

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
   * ejbActivate
   */
  public void ejbActivate() {
  }
  /**
   * ejbCreate
   */
  public void ejbCreate() throws javax.ejb.CreateException {
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
