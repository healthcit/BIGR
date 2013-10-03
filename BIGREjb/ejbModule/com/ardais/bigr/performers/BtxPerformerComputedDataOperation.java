package com.ardais.bigr.performers;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.btx.BTXDetailsUpdateComputedData;
import com.ardais.bigr.btx.BTXDetailsUpdateComputedDataSingle;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.btx.framework.BtxTransactionPerformerBase;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.helpers.SampleAppearance;
import com.ardais.bigr.javabeans.DataComputationRequestData;
import com.ardais.bigr.lims.beans.LimsOperation;
import com.ardais.bigr.lims.beans.LimsOperationHome;
import com.ardais.bigr.lims.helpers.LimsConstants;
import com.ardais.bigr.lims.javabeans.PathologyEvaluationData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.EjbHomes;

public class BtxPerformerComputedDataOperation extends BtxTransactionPerformerBase {

  /**
   * Logger for logging data changes.
   */
  private static final Log _log = LogFactory.getLog(ApiLogger.BIGR_LOGNAME_COMPUTED_DATA);

  /**
   * Invoke the specified method on this class.  This is only meant to be
   * called from BtxTransactionPerformerBase, please don't call it from anywhere
   * else as a mechanism to gain access to private methods in this class.  Every
   * object that the BTX framework dispatches to must contain this
   * method definition, and its implementation should be exactly the same
   * in each class.  Please don't alter this method or its implementation
   * in any way.
   */
  protected BTXDetails invokeBtxEntryPoint(Method method, BTXDetails btxDetails) throws Exception {

    // **** DO NOT EDIT THIS METHOD, see the Javadoc comment above.
    return (BTXDetails) method.invoke(this, new Object[] { btxDetails });
  }

  //method to do the work of updating computed data.
  private BTXDetailsUpdateComputedData performUpdateComputedData(BTXDetailsUpdateComputedData btxDetails)
    throws Exception {

    //loop over the DataComputationRequestData beans in the BTXDetails object passed in.  Create a 
    //BTXDetailsUpdateComputedDataSingle btx object and call perform to actually update the computed
    //data.
    int updateCount = 0;
    Iterator iterator = btxDetails.getDataComputationRequests().iterator();
    while (iterator.hasNext()) {
      BTXDetailsUpdateComputedDataSingle singleBTX = new BTXDetailsUpdateComputedDataSingle();
      DataComputationRequestData request = (DataComputationRequestData) iterator.next();
      singleBTX.setDataComputationRequestData(request);
      singleBTX.setBeginTimestamp(btxDetails.getBeginTimestamp());
      singleBTX.setUserId(btxDetails.getUserId());
      singleBTX.setLoggedInUserSecurityInfo(btxDetails.getLoggedInUserSecurityInfo(), true);
      singleBTX =
        (BTXDetailsUpdateComputedDataSingle) Btx.perform(
          singleBTX,
          "bigr_update_computed_data_single");

      //if any errors were returned from the btxDetails for the individual transaction, 
      //add them to the btxDetails for this parent transaction, since that is what will
      //be returned to the front end
      Iterator errors = singleBTX.getActionErrors().get();
      while (errors.hasNext()) {
        btxDetails.addActionError((BtxActionError) errors.next());
      }
      //Add the count.
      updateCount = updateCount + singleBTX.getCount();
    }
    //Set total count of updates to btxDetails.
    btxDetails.setCount(updateCount);
    //this transaction is always successful (individual compute data transactions
    //may not be, in which case the btxDetails passed into this method will be updated 
    //with information about why that individual transaction failed, but this parent 
    //transaction always is), so go to the success page
    return (BTXDetailsUpdateComputedData) btxDetails.setActionForwardTXCompleted(
      LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
  }

  //method to handle one data computation request.
  //NOTE:  this method shouldn't be called directly - only the performUpdateComputedData method 
  //should trigger this method to be called.
  private BTXDetailsUpdateComputedDataSingle performUpdateComputedDataSingle(BTXDetailsUpdateComputedDataSingle btxDetails)
    throws Exception {
    String objectType = btxDetails.getDataComputationRequestData().getObjectType();
    List ids = btxDetails.getDataComputationRequestData().getIds();
    List fields = btxDetails.getDataComputationRequestData().getFieldsToCompute();
    boolean validationFailed = false;

    //if objectType is null make it "" so we can pass it as part of any errors that occur
    if (objectType == null || objectType.trim().equals("")) {
      objectType = "";
    }

    //make sure a valid object type has been specified.
    if (!Constants.VALID_COMPUTED_DATA_OBJECT_TYPES.contains(objectType.trim())) {
      String msgKey = "computeData.error.invalidObjectType";
      btxDetails.addActionError(new BtxActionError(msgKey, objectType));
      validationFailed = true;
    }

    //make sure ids have been specified.
    if (ids == null || ids.size() <= 0) {
      String msgKey = "computeData.error.invalidIds";
      btxDetails.addActionError(new BtxActionError(msgKey, objectType));
      validationFailed = true;
    }

    //if the object type is Evaluations, make sure valid fields have been specified
    if (objectType.equalsIgnoreCase(Constants.COMPUTED_DATA_OBJECT_TYPE_EVALUATION)) {
      if (fields.size() <= 0) {
        String msgKey = "computeData.error.noFieldsSpecified";
        btxDetails.addActionError(new BtxActionError(msgKey, objectType));
        validationFailed = true;
      }
      else {
        Iterator iterator = fields.iterator();
        while (iterator.hasNext()) {
          String field = (String) iterator.next();
          if (!Constants.VALID_COMPUTED_DATA_EVAL_FIELDS.contains(field)) {
            String msgKey = "computeData.error.unknownFieldSpecified";
            btxDetails.addActionError(new BtxActionError(msgKey, field, objectType));
            validationFailed = true;
          }
        }
      }
    }

    //if any validation errors occured, set the transaction completed value
    //to false and return the btxDetails object.
    if (validationFailed) {
      btxDetails.setActionForwardRetry();
      return btxDetails;
    }

    int count = 0;
    if (objectType.equals(Constants.COMPUTED_DATA_OBJECT_TYPE_EVALUATION)) {
      count = updateEvaluations(btxDetails);
    }
    if (objectType.equals(Constants.COMPUTED_DATA_OBJECT_TYPE_SAMPLE)) {
      count = updateSamples(btxDetails);
    }
    btxDetails.setCount(count);

    //return the btxDetails object passed in, marked as completed so logging takes place.
    return (BTXDetailsUpdateComputedDataSingle) btxDetails.setActionForwardTXCompleted(
      LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
  }

  private int updateEvaluations(BTXDetailsUpdateComputedDataSingle btxDetails) throws Exception {
    Connection con = null;
    PreparedStatement pstate = null;
    List evalIds = btxDetails.getDataComputationRequestData().getIds();
    List fields = btxDetails.getDataComputationRequestData().getFieldsToCompute();
    //a set of the evaluations modified via this method - note that when updating multiple
    //fields some evaluations will have all fields changed, others will have just one so
    //we use a set to keep track of all of the evaluations that were modified in some way
    //so we can return an accurate count.
    Set modifiedEvalIds = new HashSet();

    //find any bogus ids the user may have passed in
    List badIds = findBadEvaluationIds(evalIds);

    //remove any badIds from the evaluation ids
    evalIds.removeAll(badIds);

    LimsOperation operation = null;

    Iterator iterator = fields.iterator();
    while (iterator.hasNext()) {
      String field = (String) iterator.next();
      if (field.equalsIgnoreCase(Constants.COMPUTED_DATA_EVAL_FIELD_MICROAPPEARANCE)) {
        updateMicroAppearance(btxDetails.getLoggedInUserSecurityInfo(), evalIds, modifiedEvalIds);
      }
      else if (field.equalsIgnoreCase(Constants.COMPUTED_DATA_EVAL_FIELD_COMMENTS)) {
        try {
          if (evalIds != null && evalIds.size() > 0) {
            //get existing values
            HashMap existingIntValues =
              getExistingValues(
                Constants.COMPUTED_DATA_OBJECT_TYPE_EVALUATION,
                evalIds,
                Constants.COMPUTED_DATA_EVAL_FIELD_INT_COMMENTS);
            HashMap existingExtValues =
              getExistingValues(
                Constants.COMPUTED_DATA_OBJECT_TYPE_EVALUATION,
                evalIds,
                Constants.COMPUTED_DATA_EVAL_FIELD_EXT_COMMENTS);
            con = ApiFunctions.getDbConnection();
            //needed because we use addBatch/executeBatch.
            con.setAutoCommit(false);
            pstate =
              con.prepareStatement(
                "update lims_pathology_evaluation set external_comments = ?, internal_comments = ? where pe_id = ?");
            PathologyEvaluationData pathData;
            for (int i = 0; i < evalIds.size(); i++) {
              String evalId = (String) evalIds.get(i);
              if (operation == null) {
                LimsOperationHome home = (LimsOperationHome) EjbHomes.getHome(LimsOperationHome.class);
                operation = home.create();
              }
              pathData = operation.getPathologyEvaluationDataWithFeatureLists(evalId);
              //null out the existing comments on the data bean so they are recomputed
              pathData.setConcatenatedExternalData(null);
              pathData.setConcatenatedInternalData(null);
              //recompute the comments
              String externalComments = pathData.getConcatenatedExternalData();
              String internalComments = pathData.getConcatenatedInternalData();
              //if the existing int comment or ext comment value is different from 
              //the new values, log the change(s) and add a SQL statement to the batch 
              //to update the evaluation
              String existingIntValue = (String) existingIntValues.get(evalId);
              String existingExtValue = (String) existingExtValues.get(evalId);
              //treat nulls as blank strings
              if (externalComments == null) {
                externalComments = "";
              }
              if (internalComments == null) {
                internalComments = "";
              }
              if (existingIntValue == null) {
                existingIntValue = "";
              }
              if (existingExtValue == null) {
                existingExtValue = "";
              }
              boolean internalDiffers = !existingIntValue.equalsIgnoreCase(internalComments);
              boolean externalDiffers = !existingExtValue.equalsIgnoreCase(externalComments);
              if (internalDiffers || externalDiffers) {
                pstate.setString(1, externalComments);
                pstate.setString(2, internalComments);
                pstate.setString(3, evalId);
                pstate.addBatch();
                modifiedEvalIds.add(evalId);
                StringBuffer logMsg = new StringBuffer(100);
                logMsg.append(Constants.COMPUTED_DATA_OBJECT_TYPE_EVALUATION);
                logMsg.append(" ");
                logMsg.append(evalId);
                logMsg.append(" had its ");
                if (internalDiffers) {
                  logMsg.append(Constants.COMPUTED_DATA_EVAL_FIELD_INT_COMMENTS.toUpperCase());
                  logMsg.append(" value changed from \"");
                  logMsg.append(existingIntValue);
                  logMsg.append("\" to \"");
                  logMsg.append(internalComments);
                  logMsg.append("\"");
                }
                if (externalDiffers) {
                  if (internalDiffers) {
                    logMsg.append(", and its ");
                  }
                  logMsg.append(Constants.COMPUTED_DATA_EVAL_FIELD_EXT_COMMENTS.toUpperCase());
                  logMsg.append(" value changed from \"");
                  logMsg.append(existingExtValue);
                  logMsg.append("\" to \"");
                  logMsg.append(externalComments);
                  logMsg.append("\"");
                }
                logMsg.append(".");
                _log.warn(logMsg.toString());
              }
            } // end for (int i = 0; i < evalIds.size(); ...
            //execute the prepared statement
            pstate.executeBatch();
          }
        }
        finally {
          ApiFunctions.close(con, pstate, null);
          pstate = null;
          con = null;
        }
      }
      else {
        throw new ApiException("Unknown field encountered in BtxPerformerComputedDataOperation.updateEvaluations.");
      }
    } // end while (iterator.hasNext())

    //if there were bad ids encountered, return an error for each one
    Iterator badIdIterator = badIds.iterator();
    String msgKey = "computeData.error.inValidId";
    while (badIdIterator.hasNext()) {
      String id = (String) badIdIterator.next();
      btxDetails.addActionError(new BtxActionError(msgKey, id, " pathology evaluation"));
    }

    return modifiedEvalIds.size();
  }

  private int updateSamples(BTXDetailsUpdateComputedDataSingle btxDetails) throws Exception {
    int count = 0;
    Connection con = null;
    PreparedStatement pstate = null;
    List sampleIds = btxDetails.getDataComputationRequestData().getIds();

    //find any bogus ids the user may have passed in
    List badIds = findBadSampleIds(sampleIds);

    //remove any badIds from the sample ids
    sampleIds.removeAll(badIds);

    try {
      if (sampleIds != null && sampleIds.size() > 0) {
        //removed the properties field from samples, and that was the only field being computed
        //for samples, so nothing to do here
      }
    }
    catch (Exception e) {
      //System.out.println("updateSamples - processed = " + count + " samples before encountering exception.");
      throw e;
    }
    finally {
      ApiFunctions.close(con, pstate, null);
    }

    //if there were bad ids encountered, return an error for each one
    Iterator badIdIterator = badIds.iterator();
    String msgKey = "computeData.error.inValidId";
    while (badIdIterator.hasNext()) {
      String id = (String) badIdIterator.next();
      btxDetails.addActionError(new BtxActionError(msgKey, id, "sample"));
    }

    return count;
  }

  /**
   * Updates Microscopic Appearance column in LIMS_PATHOLOGY_EVALUATION. If ids 
   * are NULL, Microscopic Appearance for all the rows will be updated.
   * @param SecurityInfo securityInfo.
   * @param <code>Vector</code> List of pe_ids. 
   */
  private void updateMicroAppearance(SecurityInfo securityInfo, List ids, Set modifiedIds) {

    Connection connection = null;
    PreparedStatement pstmt = null;
    Statement stmt = null;
    ResultSet results = null;

    //if the list of ids is null or empty, just return
    if (ids == null || ids.size() <= 0) {
      return;
    }

    try {
      //get existing values
      HashMap existingValues =
        getExistingValues(
          Constants.COMPUTED_DATA_OBJECT_TYPE_EVALUATION,
          ids,
          Constants.COMPUTED_DATA_EVAL_FIELD_MICROAPPEARANCE);
      //Get connection
      connection = ApiFunctions.getDbConnection();
      //needed because we use addBatch/executeBatch.
      connection.setAutoCommit(false);
      pstmt =
        connection.prepareStatement(
          "UPDATE LIMS_PATHOLOGY_EVALUATION SET MICROSCOPIC_APPEARANCE = ? WHERE PE_ID = ?");

      //get the information we'll need to compute the microscopic appearance values for
      //the specified evaluations
      StringBuffer buf = new StringBuffer(700);
      buf.append("SELECT PE_ID, SAMPLE_BARCODE_ID, DIAGNOSIS_CONCEPT_ID, TUMOR_CELLS, \n");
      buf.append(
        "LESION_CELLS, NORMAL_CELLS, NECROSIS_CELLS, HYPOACELLULARSTROMA_CELLS, CELLULARSTROMA_CELLS \n");
      buf.append("FROM LIMS_PATHOLOGY_EVALUATION \n");
      if (ids != null && ids.size() > 0) {
        buf.append("WHERE PE_ID IN (");
        for (int i = 0; i < ids.size(); i++) {
          if (i > 0) {
            buf.append(",");
          }
          buf.append("'");
          buf.append((String) ids.get(i));
          buf.append("'");
        }
        buf.append(")");
      }
      stmt = connection.createStatement();
      results = stmt.executeQuery(buf.toString());

      //Iterate through result set, compute the microscopic appearance value for the
      //evaluation and if it's different from what's currently in the database
      //update the microscopic appearance value
      String sampleId;
      String diseaseConceptId;
      String donorInstDiagnosis;
      String microAppearance;
      String peId;
      int tumor;
      int lesion;
      int normal;
      int necrosis;
      int acellular;
      int cellular;
      while (results.next()) {
        sampleId = results.getString("SAMPLE_BARCODE_ID");
        diseaseConceptId = results.getString("DIAGNOSIS_CONCEPT_ID");
        peId = results.getString("PE_ID");
        tumor = results.getInt("TUMOR_CELLS");
        lesion = results.getInt("LESION_CELLS");
        normal = results.getInt("NORMAL_CELLS");
        necrosis = results.getInt("NECROSIS_CELLS");
        acellular = results.getInt("HYPOACELLULARSTROMA_CELLS");
        cellular = results.getInt("CELLULARSTROMA_CELLS");
        //get Donor Institution Diagnosis
        donorInstDiagnosis = getDiagnosisFromSampleId(sampleId);
        SampleAppearance objSampleApp =
          new SampleAppearance(
            sampleId,
            donorInstDiagnosis,
            diseaseConceptId,
            tumor,
            lesion,
            normal,
            necrosis,
            acellular,
            cellular,
            securityInfo);
        microAppearance = objSampleApp.computeSampleAppearance(false);
        //if the existing value is different from the new value, log the change and add
        //a SQL statement to the batch to update the evaluation
        String existingValue = (String) existingValues.get(peId);
        if (microAppearance == null) {
          microAppearance = "";
        }
        if (existingValue == null) {
          existingValue = "";
        }
        if (!microAppearance.equalsIgnoreCase(existingValue)) {
          pstmt.setString(1, microAppearance);
          pstmt.setString(2, peId);
          pstmt.addBatch();
          modifiedIds.add(peId);
          StringBuffer logMsg = new StringBuffer(100);
          logMsg.append(Constants.COMPUTED_DATA_OBJECT_TYPE_EVALUATION);
          logMsg.append(" ");
          logMsg.append(peId);
          logMsg.append(" had its ");
          logMsg.append(Constants.COMPUTED_DATA_EVAL_FIELD_MICROAPPEARANCE.toUpperCase());
          logMsg.append(" value changed from \"");
          logMsg.append(existingValue);
          logMsg.append("\" to \"");
          logMsg.append(microAppearance);
          logMsg.append("\".");
          _log.warn(logMsg.toString());
        }
      } // end while (results.next())
      //execute the prepared statement
      pstmt.executeBatch();
    }
    catch (Exception ex) {
      ApiFunctions.throwAsRuntimeException(ex);
    }
    finally {
      ApiFunctions.close(results);
      ApiFunctions.close(stmt);
      ApiFunctions.close(pstmt);
      ApiFunctions.close(connection);
    }
  }

  private String getDiagnosisFromSampleId(String sampleId) {
    String donorInstDiagnosis = null;
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet results = null;
    try {

      // first, try and get the path report diagnosis...if it is there, use it...if not, get the
      // iltds donor institution diagnosis...            

      StringBuffer buff = new StringBuffer();
      buff.append("select pdc.DIAGNOSIS_CONCEPT_ID  ");
      buff.append(" from pdc_pathology_report pdc, iltds_asm asm, iltds_sample sample  ");
      buff.append(" where sample.ASM_ID = asm.ASM_ID  ");
      buff.append(" and asm.CONSENT_ID = pdc.CONSENT_ID  ");
      buff.append(" and sample.SAMPLE_BARCODE_ID = ?  ");

      String sql = buff.toString();

      con = ApiFunctions.getDbConnection();

      pstmt = con.prepareStatement(sql);
      pstmt.setString(1, sampleId);
      results = pstmt.executeQuery();

      while (results.next()) {
        donorInstDiagnosis = results.getString("DIAGNOSIS_CONCEPT_ID");
      }

      ApiFunctions.close(results);
      results = null;
      ApiFunctions.close(pstmt);
      pstmt = null;

      if (donorInstDiagnosis == null) {
        // ok, could not get DDC diagnosis, so get from ILTDS...
        buff = new StringBuffer();
        buff.append("select ic.DISEASE_CONCEPT_ID  ");
        buff.append(" from iltds_informed_consent ic, iltds_asm asm, iltds_sample sample  ");
        buff.append(" where sample.ASM_ID = asm.ASM_ID  ");
        buff.append(" and asm.CONSENT_ID = ic.CONSENT_ID  ");
        buff.append(" and sample.SAMPLE_BARCODE_ID = ?  ");

        // continue...
        sql = buff.toString();
        // the SQL statement
        pstmt = con.prepareStatement(sql);
        pstmt.setString(1, sampleId);
        results = pstmt.executeQuery();

        while (results.next()) {
          donorInstDiagnosis = results.getString("DISEASE_CONCEPT_ID");
        }
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, results);
    }

    return donorInstDiagnosis;

  }

  /* Method to determine which sample ids are bogus from a list of sample ids
   * passed in */
  private List findBadSampleIds(List ids) throws Exception {

    Connection connection = null;
    Statement stmt = null;
    ResultSet results = null;
    ArrayList badIds = new ArrayList();
    ArrayList foundIds = new ArrayList();

    try {
      //Get connection
      connection = ApiFunctions.getDbConnection();
      //Get all the sample ids from the database matching the ids passed in.
      StringBuffer buf = new StringBuffer(700);
      buf.append("SELECT SAMPLE_BARCODE_ID ");
      buf.append("FROM ILTDS_SAMPLE ");
      if (ids != null && ids.size() > 0) {
        buf.append("WHERE SAMPLE_BARCODE_ID IN (");
        for (int i = 0; i < ids.size(); i++) {
          if (i > 0) {
            buf.append(",");
          }
          buf.append("'");
          buf.append((String) ids.get(i));
          buf.append("'");
        }
        buf.append(")");
      }
      stmt = connection.createStatement();
      results = stmt.executeQuery(buf.toString());

      //put the ids we found into a list
      while (results.next()) {
        foundIds.add(results.getString("SAMPLE_BARCODE_ID"));
      }

      //now iterate over the ids passed in.  Any id not in the found list is bad
      for (int i = 0; i < ids.size(); i++) {
        String id = (String) ids.get(i);
        if (!foundIds.contains(id)) {
          badIds.add(id);
        }
      }
    }
    finally {
      ApiFunctions.close(connection, stmt, results);
    }

    return badIds;
  }

  /* Method to determine which evaluation ids are bogus from a list of evaluation ids
   * passed in */
  private List findBadEvaluationIds(List ids) throws Exception {

    Connection connection = null;
    Statement stmt = null;
    ResultSet results = null;
    ArrayList badIds = new ArrayList();
    ArrayList foundIds = new ArrayList();

    try {
      //Get connection
      connection = ApiFunctions.getDbConnection();
      //Get all the sample ids from the database matching the ids passed in.
      StringBuffer buf = new StringBuffer(700);
      buf.append("SELECT PE_ID ");
      buf.append("FROM LIMS_PATHOLOGY_EVALUATION ");
      if (ids != null && ids.size() > 0) {
        buf.append("WHERE PE_ID IN (");
        for (int i = 0; i < ids.size(); i++) {
          if (i > 0) {
            buf.append(",");
          }
          buf.append("'");
          buf.append((String) ids.get(i));
          buf.append("'");
        }
        buf.append(")");
      }
      stmt = connection.createStatement();
      results = stmt.executeQuery(buf.toString());

      //put the ids we found into a list
      while (results.next()) {
        foundIds.add(results.getString("PE_ID"));
      }

      //now iterate over the ids passed in.  Any id not in the found list is bad
      for (int i = 0; i < ids.size(); i++) {
        String id = (String) ids.get(i);
        if (!foundIds.contains(id)) {
          badIds.add(id);
        }
      }
    }
    finally {
      ApiFunctions.close(connection, stmt, results);
    }

    return badIds;
  }

  /* Method to get existing values for a database field that could be updated. Input 
   * parameters are a string indicating the type of object, a list of object ids, and the field 
   * to retrieve.  The output is a HashMap of those values keyed by object id. */
  private HashMap getExistingValues(String objectType, List ids, String field) throws Exception {

    Connection connection = null;
    Statement stmt = null;
    ResultSet results = null;
    HashMap existingValues = new HashMap();

    try {
      //figure out the query to execute
      StringBuffer buff = new StringBuffer(700);
      if (objectType.equals(Constants.COMPUTED_DATA_OBJECT_TYPE_SAMPLE)) {
        throw new ApiException(
          "Unknown field ("
            + field
            + ") for "
            + objectType
            + " encountered in BtxPerformerComputedDataOperation.getExistingValues.");
      }
      else if (objectType.equals(Constants.COMPUTED_DATA_OBJECT_TYPE_EVALUATION)) {
        String fieldString = null;
        if (field.equalsIgnoreCase(Constants.COMPUTED_DATA_EVAL_FIELD_MICROAPPEARANCE)) {
          fieldString = "microscopic_appearance as field";
        }
        else if (field.equalsIgnoreCase(Constants.COMPUTED_DATA_EVAL_FIELD_INT_COMMENTS)) {
          fieldString = "internal_comments as field";
        }
        else if (field.equalsIgnoreCase(Constants.COMPUTED_DATA_EVAL_FIELD_EXT_COMMENTS)) {
          fieldString = "external_comments as field";
        }
        else {
          throw new ApiException(
            "Unknown field ("
              + field
              + ") for "
              + objectType
              + " encountered in BtxPerformerComputedDataOperation.getExistingValues.");
        }
        buff.append("Select pe_id as id, ");
        buff.append(fieldString);
        buff.append(" from lims_pathology_evaluation where pe_id = '");
        buff.append(ids.get(0));
        buff.append("'");
        for (int i = 1; i < ids.size(); i++) {
          buff.append(" or pe_id = '");
          buff.append(ids.get(i));
          buff.append("'");
        }
      }

      //Get connection and execute the query
      connection = ApiFunctions.getDbConnection();
      stmt = connection.createStatement();
      results = stmt.executeQuery(buff.toString());

      //put the ids and values we found into the hashmap
      while (results.next()) {
        existingValues.put(results.getString("id"), results.getString("field"));
      }
    }
    finally {
      ApiFunctions.close(connection, stmt, results);
    }

    return existingValues;
  }

}
