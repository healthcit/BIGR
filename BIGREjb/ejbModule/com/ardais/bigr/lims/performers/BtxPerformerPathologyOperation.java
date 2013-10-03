package com.ardais.bigr.lims.performers;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import javax.ejb.CreateException;
import javax.ejb.ObjectNotFoundException;

import javax.naming.NamingException;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiProperties;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.btx.framework.BtxTransactionPerformerBase;
import com.ardais.bigr.iltds.beans.BTXHistoryReader;
import com.ardais.bigr.iltds.beans.BTXHistoryReaderHome;
import com.ardais.bigr.iltds.beans.PathologyOperation;
import com.ardais.bigr.iltds.beans.PathologyOperationHome;
import com.ardais.bigr.iltds.beans.SampleAccessBean;
import com.ardais.bigr.iltds.beans.SampleKey;
import com.ardais.bigr.iltds.beans.SamplestatusAccessBean;
import com.ardais.bigr.iltds.btx.BTXActionForward;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryDisplayLine;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.iltds.btx.BtxActionMessage;
import com.ardais.bigr.iltds.btx.BtxActionMessages;
import com.ardais.bigr.iltds.databeans.ProjectLineItem;
import com.ardais.bigr.iltds.databeans.ShoppingCartLineItem;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.SampleAppearance;
import com.ardais.bigr.javabeans.AsmData;
import com.ardais.bigr.javabeans.ConsentData;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.lims.beans.IncidentAccessBean;
import com.ardais.bigr.lims.beans.LimsDataValidator;
import com.ardais.bigr.lims.beans.LimsDataValidatorHome;
import com.ardais.bigr.lims.beans.LimsOperation;
import com.ardais.bigr.lims.beans.LimsOperationHome;
import com.ardais.bigr.lims.beans.PathologyEvaluationAccessBean;
import com.ardais.bigr.lims.beans.SlideAccessBean;
import com.ardais.bigr.lims.btx.BTXDetailsCreateIncidents;
import com.ardais.bigr.lims.btx.BTXDetailsCreateIncidentsSingle;
import com.ardais.bigr.lims.btx.BTXDetailsCreatePathologyEvaluation;
import com.ardais.bigr.lims.btx.BTXDetailsCreatePathologyEvaluationPrepare;
import com.ardais.bigr.lims.btx.BTXDetailsGetPathQCSampleDetails;
import com.ardais.bigr.lims.btx.BTXDetailsGetPathQCSampleSummary;
import com.ardais.bigr.lims.btx.BTXDetailsGetSamplePathologyInfo;
import com.ardais.bigr.lims.btx.BTXDetailsMarkSampleDiscordant;
import com.ardais.bigr.lims.btx.BTXDetailsMarkSampleNondiscordant;
import com.ardais.bigr.lims.btx.BTXDetailsMarkSamplePulled;
import com.ardais.bigr.lims.btx.BTXDetailsMarkSampleQCPosted;
import com.ardais.bigr.lims.btx.BTXDetailsMarkSampleReleased;
import com.ardais.bigr.lims.btx.BTXDetailsMarkSampleUnQCPosted;
import com.ardais.bigr.lims.btx.BTXDetailsMarkSampleUnpulled;
import com.ardais.bigr.lims.btx.BTXDetailsMarkSampleUnreleased;
import com.ardais.bigr.lims.btx.BTXDetailsReportPathologyEvaluation;
import com.ardais.bigr.lims.helpers.LimsConstants;
import com.ardais.bigr.lims.javabeans.IncidentReportData;
import com.ardais.bigr.lims.javabeans.IncidentReportLineItem;
import com.ardais.bigr.lims.javabeans.PathologyEvaluationData;
import com.ardais.bigr.lims.javabeans.PathologyEvaluationFeatureData;
import com.ardais.bigr.lims.javabeans.PathologyEvaluationSampleData;
import com.ardais.bigr.pdc.javabeans.PathReportData;
import com.ardais.bigr.pdc.javabeans.PathReportSectionData;
import com.ardais.bigr.pdc.oce.util.OceUtil;
import com.ardais.bigr.query.generator.ProductDetailQueryBuilder;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.LogicalRepositoryUtils;
import com.ardais.bigr.util.gen.DbAliases;
import com.ardais.bigr.util.gen.DbConstants;

public class BtxPerformerPathologyOperation extends BtxTransactionPerformerBase {

  // do not reference these member variables directly in the code, instead call their private get methods 
  private LimsDataValidator validator = null; 
  private LimsOperation operation2 = null;
  
  public BtxPerformerPathologyOperation() {
    super();
  }

  private LimsDataValidator getValidator() throws NamingException, RemoteException, CreateException, ClassNotFoundException {
    if (this.validator == null) {
      LimsDataValidatorHome ldvHome = (LimsDataValidatorHome) EjbHomes.getHome(LimsDataValidatorHome.class);
      this.validator = ldvHome.create();
    }
    return this.validator;
  }
  
  private LimsOperation getOperation() throws NamingException, RemoteException, CreateException, ClassNotFoundException {
    if (this.operation2 == null) {
      LimsOperationHome home = (LimsOperationHome) EjbHomes.getHome(LimsOperationHome.class);
      this.operation2 = home.create();
    }
    return this.operation2;
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
  protected BTXDetails invokeBtxEntryPoint(Method method, BTXDetails btxDetails) throws Exception {

    // **** DO NOT EDIT THIS METHOD, see the Javadoc comment above.
    return (BTXDetails) method.invoke(this, new Object[] { btxDetails });
  }

  //Method to do work of managing pathology evaluations. 
  private BTXDetailsGetSamplePathologyInfo performGetSamplePathologyInfo(BTXDetailsGetSamplePathologyInfo btxDetails)
    throws Exception {

    //get the sample id, validate it, and return the required information
    String sampleId = btxDetails.getSampleId();
    String slideId = btxDetails.getSlideId();

    //if either sample id or slide id are null, make them empty strings so we can just pass
    //them to our error creation code without worrying about null pointer exceptions
    if (sampleId == null)
      sampleId = "";
    if (slideId == null)
      slideId = "";

    //if the caller didn't specify a sample id, try to get that info from the slide id
    if (ApiFunctions.isEmpty(sampleId)) {
      boolean slideExists = getValidator().doesSlideExist(slideId);
      //if the slide the caller specified doesn't exist (or they didn't specify a slide), return an error
      if (!slideExists) {
        BtxActionError btxError =
          new BtxActionError("lims.error.getSamplePathInfo.invalidSlideId", slideId);
        return (BTXDetailsGetSamplePathologyInfo) btxDetails.setActionForwardRetry(btxError);
      }
      //otherwise try to get the sample for that slide
      else {
        sampleId = getOperation().getSampleIdForSlide(slideId);
        //set the sample id on the btxDetails so the front end can make use
        //of it
        btxDetails.setSampleId(sampleId);
      }
    }

    //see if the sample exists
    boolean sampleExists = getValidator().doesSampleExist(sampleId);
    //if the sample doesn't exist return an error       
    if (!sampleExists) {
      BtxActionError btxError =
        new BtxActionError("lims.error.getSamplePathInfo.invalidSampleId", sampleId);
      return (BTXDetailsGetSamplePathologyInfo) btxDetails.setActionForwardRetry(btxError);
    }

    //first create and populate the PathologyEvaluationSampleData data bean, and
    //attach it to the BTXDetails object passed in.  This is done before the
    //creation of the PathologyEvaluationData data bean, as information in this
    //sample data bean is used in the creation of the evaluation data bean
    PathologyEvaluationSampleData peSampleData = getOperation().getEvaluationSampleData(sampleId);
    btxDetails.setPathologyEvaluationSampleData(peSampleData);

    //now get all of the pathology evaluations for the sample and
    //attach them to the BTXDetails object passed in
    List pathologyEvaluations = getOperation().getPathologyEvaluationsForSample(sampleId);
    btxDetails.setPathologyEvaluations(pathologyEvaluations);

    //finally, get all the btx history items, weed out those not relevant to 
    //pathology evaluation, and attach them to the BTXDetails object passed in
    SecurityInfo secInfo = btxDetails.getLoggedInUserSecurityInfo();
    BTXHistoryReaderHome readerHome = (BTXHistoryReaderHome) EjbHomes.getHome(BTXHistoryReaderHome.class);
    BTXHistoryReader btxHist = readerHome.create();
    List allHistoryRecords =
      btxHist.getHistoryDisplayLines(sampleId, secInfo);
    List displayHistoryRecords = new ArrayList();
    Iterator iterator = allHistoryRecords.iterator();
    while (iterator.hasNext()) {
      BTXHistoryDisplayLine historyRecord = (BTXHistoryDisplayLine) iterator.next();
      String btxType = historyRecord.getBTXType();
      if (BTXDetails.BTX_TYPE_CREATE_PATH_EVAL.equals(btxType)
        || BTXDetails.BTX_TYPE_REPORT_PATH_EVAL.equals(btxType)
        || BTXDetails.BTX_TYPE_CREATE_SLIDES_SINGLE.equals(btxType)
        || BTXDetails.BTX_TYPE_PATHLAB_SLIDES_SINGLE.equals(btxType)
        || BTXDetails.BTX_TYPE_CREATE_IMAGE.equals(btxType)
        || BTXDetails.BTX_TYPE_SET_SLIDE_LOCATIONS_SINGLE.equals(btxType)
        || BTXDetails.BTX_TYPE_MARK_SAMPLE_PULLED.equals(btxType)
        || BTXDetails.BTX_TYPE_MARK_SAMPLE_UNPULLED.equals(btxType)
        || BTXDetails.BTX_TYPE_MARK_SAMPLE_DISCORDANT.equals(btxType)
        || BTXDetails.BTX_TYPE_MARK_SAMPLE_NONDISCORDANT.equals(btxType)
        || BTXDetails.BTX_TYPE_MARK_SAMPLE_RELEASED.equals(btxType)
        || BTXDetails.BTX_TYPE_MARK_SAMPLE_UNRELEASED.equals(btxType)
        || BTXDetails.BTX_TYPE_PATH_SAMPLE_REQUEST.equals(btxType)
        || BTXDetails.BTX_TYPE_CHECK_OUT_SAMPLES.equals(btxType)
        || BTXDetails.BTX_TYPE_MARK_SAMPLE_QCPOSTED.equals(btxType)
        || BTXDetails.BTX_TYPE_MARK_SAMPLE_UNQCPOSTED.equals(btxType)
        || BTXDetails.BTX_TYPE_SUBDIVIDE.equals(btxType)
        || BTXDetails.BTX_TYPE_CREATE_INCIDENTS_SINGLE.equals(btxType)
        || BTXDetails.BTX_TYPE_CREATE_RESEARCH_REQUEST.equals(btxType)
        || BTXDetails.BTX_TYPE_CREATE_TRANSFER_REQUEST.equals(btxType)
        || BTXDetails.BTX_TYPE_FULFILL_REQUEST.equals(btxType)
        || BTXDetails.BTX_TYPE_REJECT_REQUEST.equals(btxType)) {
        displayHistoryRecords.add(historyRecord);
      }
    }
    btxDetails.setBtxHistoryRecords(displayHistoryRecords);

    //return the btxDetails object passed in, marked as completed.
    return (BTXDetailsGetSamplePathologyInfo) btxDetails.setActionForwardTXCompleted(
      LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
  }

  //method to do the work of marking a sample discordant
  private BTXDetailsMarkSampleDiscordant performMarkSampleDiscordant(BTXDetailsMarkSampleDiscordant btxDetails)
    throws Exception {

    //get the sample and reason information, validate it, and mark the sample discordant
    boolean validationFailed = false;

    //if the specified sample doesn't exist, update the btxDetails object to contain 
    //an error.  Checking that the sample exists should already have been done by the 
    //UI, but do it here to be safe and to save the code below that relies on this 
    //being a valid sample from needing to check.
    String sampleId = btxDetails.getSampleId();
    boolean sampleExists = getValidator().doesSampleExist(sampleId);
    if (!sampleExists) {
      btxDetails.addActionError(
        new BtxActionError("lims.error.markSampleDiscordant.invalidSampleId", sampleId));
      validationFailed = true;
    }

    //if the sample is already discordant don't do anything.  In this case we don't want 
    //to send email out and we don't want to log anything
    boolean sampleIsDiscordant = getValidator().isSampleDiscordant(sampleId);
    if (sampleIsDiscordant) {
      btxDetails.addActionError(
        new BtxActionError("lims.error.sampleAlreadyInState", sampleId, "discordant"));
      validationFailed = true;
    }

    //if the reason is not valid, update the btxDetails object to contain an error.
    String reason = ApiFunctions.safeTrim(btxDetails.getReason());
    if (ApiFunctions.isEmpty(reason)) {
      btxDetails.addActionError(
        new BtxActionError("lims.error.markSampleDiscordant.invalidReason", sampleId));
      validationFailed = true;
    }

    //if any validation errors occured, mark the transaction incomplete and return.
    if (validationFailed) {
      btxDetails.setActionForwardRetry();
      return btxDetails;
    }

    //if the sample was released, we're going to mark it unreleased when we mark it discordant and
    //log that fact, so set the flag on the btxDetails object so we can log things
    //correctly
    boolean sampleIsReleased = getValidator().isSampleReleased(sampleId);
    btxDetails.setSampleUnreleased(sampleIsReleased);

    //if the sample was QCPosted, we're going to mark it unQCPosted when we mark it discordant and
    //log that fact, so set the flag on the btxDetails object so we can log things
    //correctly
    boolean sampleIsQCPosted = getValidator().isSampleQCPosted(sampleId);
    btxDetails.setSampleUnQCPosted(sampleIsQCPosted);

    //now that we've validated that all required data has been provided, mark the sample discordant
    //first, though, update the reason to have "Major Discordance" prepended to it and update
    //the btxDetails object with this updated reason.  We do this so that this prefix will 
    //be prepended to the reason in both the ILTDS_SAMPLE table and in the BTX history table.
    //Also mark the sample unreleased and unQCPosted
    reason = LimsConstants.LIMS_DISCORDANCE_PREFIX + reason;
    btxDetails.setReason(reason);
    SampleKey key = new SampleKey(sampleId);
    SampleAccessBean sampleBean = new SampleAccessBean(key);
    sampleBean.setPullYN(LimsConstants.LIMS_DB_YES);
    sampleBean.setDiscordantYN(LimsConstants.LIMS_DB_YES);
    sampleBean.setPullReason(reason);
    sampleBean.setPullDate(new java.sql.Timestamp(new java.util.Date().getTime()));
    sampleBean.setReleasedYN(LimsConstants.LIMS_DB_NO);
    sampleBean.setQcpostedYN(LimsConstants.LIMS_DB_NO);
    sampleBean.commitCopyHelper();

    //Set QCAWAITING status on the sample
    SamplestatusAccessBean sample = new SamplestatusAccessBean();
    sample.setInit_argSample(key);
    sample.setInit_argSample_status_datetime(new Timestamp(new java.util.Date().getTime()));
    sample.setInit_argStatus_type_code(FormLogic.SMPL_QCAWAITING);
    sample.commitCopyHelper();

    //if the sample had a reported evaluation, it must be marked unreported so do that now and
    //update the btxDetails object so the id of that evaluation can be logged.
    PathologyEvaluationData reportedEval = getOperation().getReportedPathologyEvaluation(sampleId);
    if (reportedEval != null) {
      btxDetails.setUnreportedEvaluationId(reportedEval.getEvaluationId());
      setReportedYN(sampleId, null);
    }

    //Mark status of rows in OCE for this sample evaluations as obsolete.
    //MR 4986
    String userId = btxDetails.getUserId();
    getOperation().updateLimsOCEDataStatus(sampleId, OceUtil.OCE_STATUS_OBSOLETE_IND, userId);

    //send email to notify the appropriate people that a sample has been marked discordant
    String sendEmailTo = ApiProperties.getProperty("api.email.majordiscordance");
    String sendEmailFrom = ApiProperties.getProperty("api.email.from_majordiscordance");
    StringBuffer mailMessage = new StringBuffer(100);
    mailMessage.append("Sample ");
    mailMessage.append(sampleId);
    mailMessage.append(" has been marked discordant for the following reason: \"");
    mailMessage.append(reason);
    mailMessage.append("\"");
    if (!(ApiFunctions.isEmpty(sendEmailTo))) {
      if (!ApiFunctions
        .generateEmail(
          sendEmailFrom,
          sendEmailTo,
          "Sample " + sampleId + " has been marked discordant.",
          mailMessage.toString())) {
        // MR7170 - let user know that email failed. Have them contact their supervisor.
        btxDetails.addActionError(new BtxActionError("lims.message.contactSupervisor"));
      }
    }

    //send email to let people know the sample was pulled
    sendPulledSampleEmail(sampleId, "discordant", reason);

    btxDetails.addActionError(
      new BtxActionError("lims.message.markSampleDiscordant.success", sampleId));
    //return the btxDetails object passed in, marked as completed so logging takes place.
    return (BTXDetailsMarkSampleDiscordant) btxDetails.setActionForwardTXCompleted(
      LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
  }

  /**
   * Updates REPORTED_YN and REPORTED_DATE column of LIMS_PATHOLOGY_EVALUATION. 
   * This method first updates REPORTED_YN column for <code>sampleId</code>
   * with "N" and updates REPORTED_YN column with "Y" where 
   * LIMS_PATHOLOGY_EVALUATION.PE_ID = <code>pathologyEvaluationId</code>. 
   */
  private void setReportedYN(String sampleId, String pathologyEvaluationId) throws Exception {
    StringBuffer sql = new StringBuffer(256);
    PreparedStatement pstmt = null;
    ResultSet rSet = null;
    int rowCount = 0;
    //Find all the existing evaluations for the sample and mark them as not reported
    sql.append("update lims_pathology_evaluation set reported_yn = 'N' ");
    sql.append("where sample_barcode_id = ?");

    Connection con = ApiFunctions.getDbConnection();
    try {
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, sampleId);
      rowCount = pstmt.executeUpdate();
      pstmt.close();

      if (rowCount > 0 && pathologyEvaluationId != null) {
        //mark the pathology evaluation specified for the pathologyEvaluationId as reported.
        sql = new StringBuffer(256);
        sql.append("update lims_pathology_evaluation set reported_yn = 'Y' ");
        sql.append(", reported_date = ? ");
        sql.append("where pe_id = ?");
        pstmt = con.prepareStatement(sql.toString());
        pstmt.setTimestamp(1, new java.sql.Timestamp(new java.util.Date().getTime()));
        pstmt.setString(2, pathologyEvaluationId);
        rowCount = pstmt.executeUpdate();
      }
    }
    finally {
      ApiFunctions.close(con, pstmt, rSet);
    }
  }

  //method to send email to appropriate parties when a sample is pulled
  private void sendPulledSampleEmail(String sampleId, String state, String reason)
    throws Exception {
    //if this sample is on a project or in a shopping cart, send email to notify the 
    //appropriate people that the sample has been marked pulled

    PathologyOperationHome home = (PathologyOperationHome) EjbHomes.getHome(PathologyOperationHome.class);
    PathologyOperation poBean = home.create();
    Vector cartsAndOrders = poBean.getProjectsAndShoppingCartsForSample(sampleId);
    if (cartsAndOrders.size() > 0) {
      StringBuffer mailMessage = new StringBuffer(100);
      mailMessage.append("Sample ");
      mailMessage.append(sampleId);
      mailMessage.append(" has been marked ");
      mailMessage.append(state);
      mailMessage.append(" for the following reason: \"");
      mailMessage.append(reason);
      mailMessage.append("\".");
      DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
      boolean sampleInCart = false;
      boolean sampleOnProject = false;
      for (int i = 0; i < cartsAndOrders.size(); i++) {
        if (cartsAndOrders.get(i) instanceof ProjectLineItem) {
          ProjectLineItem item = (ProjectLineItem) cartsAndOrders.get(i);
          mailMessage.append("\nThis sample appears as line item ");
          mailMessage.append(item.getLineNumber());
          mailMessage.append(" on project ");
          mailMessage.append(item.getProjectName());
          mailMessage.append(", created by ");
          mailMessage.append(item.getArdaisUserId());
          mailMessage.append(" (account ");
          mailMessage.append(item.getArdaisAccountKey());
          mailMessage.append(") and was requested on ");
          mailMessage.append(dateFormatter.format(item.getRequestDate()));
          mailMessage.append(".");
          sampleOnProject = true;
        }
        else if (cartsAndOrders.get(i) instanceof ShoppingCartLineItem) {
          ShoppingCartLineItem item = (ShoppingCartLineItem) cartsAndOrders.get(i);
          mailMessage.append("\nThis sample appears as line item ");
          mailMessage.append(item.getLineNumber());
          mailMessage.append(" in cart ");
          mailMessage.append(item.getShoppingCartId());
          mailMessage.append(", created by ");
          mailMessage.append(item.getArdaisUserId());
          mailMessage.append(" (account ");
          mailMessage.append(item.getArdaisAccountKey());
          mailMessage.append("), and was added to the cart on ");
          mailMessage.append(dateFormatter.format(item.getCreationDate()));
          mailMessage.append(".");
          sampleInCart = true;
        }
      }
      //send the email
      if (sampleOnProject) {
        String sendEmailTo = ApiProperties.getProperty("api.email.tosamplepulledproject");
        String sendEmailFrom = ApiProperties.getProperty("api.email.fromsamplepulled");
        if (!(ApiFunctions.isEmpty(sendEmailTo))) {
          ApiFunctions.generateEmail(
            sendEmailFrom,
            sendEmailTo,
            "Sample " + sampleId + " has been marked pulled.",
            mailMessage.toString());
        }
      }
      if (sampleInCart) {
        String sendEmailTo = ApiProperties.getProperty("api.email.tosamplepulledshoppingcart");
        String sendEmailFrom = ApiProperties.getProperty("api.email.fromsamplepulled");
        if (!(ApiFunctions.isEmpty(sendEmailTo))) {
          ApiFunctions.generateEmail(
            sendEmailFrom,
            sendEmailTo,
            "Sample " + sampleId + " has been marked " + state + ".",
            mailMessage.toString());
        }
      }
    }
  }

  //method to do the work of marking a sample pulled
  private BTXDetailsMarkSamplePulled performMarkSamplePulled(BTXDetailsMarkSamplePulled btxDetails)
    throws Exception {

    //get the sample and reason information, validate it, and mark the sample pulled
    boolean validationFailed = false;

    //if the specified sample doesn't exist, update the btxDetails object to contain 
    //an error.  Checking that the sample exists should already have been done by the 
    //UI, but do it here to be safe and to save the code below that relies on this 
    //being a valid sample from needing to check.
    String sampleId = btxDetails.getSampleId();
    boolean sampleExists = getValidator().doesSampleExist(sampleId);
    if (!sampleExists) {
      btxDetails.addActionError(
        new BtxActionError("lims.error.markSamplePulled.invalidSampleId", sampleId));
      validationFailed = true;
    }

    //if the sample is already pulled don't do anything.  In this case we don't want 
    //to log anything
    boolean sampleIsPulled = getValidator().isSamplePulled(sampleId);
    if (sampleIsPulled) {
      btxDetails.addActionError(
        new BtxActionError("lims.error.sampleAlreadyInState", sampleId, "pulled"));
      validationFailed = true;
    }

    //if the reason is not valid, update the btxDetails object to contain an error.
    String reason = ApiFunctions.safeTrim(btxDetails.getReason());
    if (ApiFunctions.isEmpty(reason)) {
      btxDetails.addActionError(
        new BtxActionError("lims.error.markSamplePulled.invalidReason", sampleId));
      validationFailed = true;
    }

    //if any validation errors occured, mark the transaction incomplete and return.
    if (validationFailed) {
      btxDetails.setActionForwardRetry();
      return btxDetails;
    }

    //if the sample was released, we're going to mark it unreleased when we pull it and
    //log that fact, so set the flag on the btxDetails object so we can log things
    //correctly
    boolean sampleIsReleased = getValidator().isSampleReleased(sampleId);
    btxDetails.setSampleUnreleased(sampleIsReleased);

    //if the sample was QCPosted, we're going to mark it unQCPosted when we pull it and
    //log that fact, so set the flag on the btxDetails object so we can log things
    //correctly
    boolean sampleIsQCPosted = getValidator().isSampleQCPosted(sampleId);
    btxDetails.setSampleUnQCPosted(sampleIsQCPosted);

    //now that we've validated that all required data has been provided, pull the sample.
    //Also mark it unreleased and unQCPosted
    SampleKey key = new SampleKey(sampleId);
    SampleAccessBean sampleBean = new SampleAccessBean(key);
    sampleBean.setPullYN(LimsConstants.LIMS_DB_YES);
    sampleBean.setDiscordantYN(LimsConstants.LIMS_DB_NO);
    sampleBean.setPullReason(reason);
    sampleBean.setPullDate(new java.sql.Timestamp(new java.util.Date().getTime()));
    sampleBean.setReleasedYN(LimsConstants.LIMS_DB_NO);
    sampleBean.setQcpostedYN(LimsConstants.LIMS_DB_NO);
    sampleBean.commitCopyHelper();

    //Set QCAWAITING status on the sample
    SamplestatusAccessBean sample = new SamplestatusAccessBean();
    sample.setInit_argSample(key);
    sample.setInit_argSample_status_datetime(new Timestamp(new java.util.Date().getTime()));
    sample.setInit_argStatus_type_code(FormLogic.SMPL_QCAWAITING);
    sample.commitCopyHelper();

    //if the sample had a reported evaluation, it must be marked unreported so do that now and
    //update the btxDetails object so the id of that evaluation can be logged.
    PathologyEvaluationData reportedEval = null;
    reportedEval = getOperation().getReportedPathologyEvaluation(sampleId);
    if (reportedEval != null) {
      btxDetails.setUnreportedEvaluationId(reportedEval.getEvaluationId());
      setReportedYN(sampleId, null);
    }

    //Mark status of rows in OCE for this sample evaluations as obsolete.
    //MR 4986
    String userId = btxDetails.getUserId();
    getOperation().updateLimsOCEDataStatus(sampleId, OceUtil.OCE_STATUS_OBSOLETE_IND, userId);

    //send email to let people know this sample was pulled
    sendPulledSampleEmail(sampleId, "pulled", reason);

    //btxDetails.addActionError(
    //  new BtxActionError("lims.message.markSamplePulled.success", sampleId));
    btxDetails.addActionMessage(
      new BtxActionMessage("lims.message.markSamplePulled.success", sampleId));
    //return the btxDetails object passed in, marked as completed so logging takes place.
    return (BTXDetailsMarkSamplePulled) btxDetails.setActionForwardTXCompleted(
      LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
  }

  //method to do the work of marking a sample unpulled
  private BTXDetailsMarkSampleUnpulled performMarkSampleUnpulled(BTXDetailsMarkSampleUnpulled btxDetails)
    throws Exception {

    //get the sample and reason information, validate it, and mark the sample unpulled
    boolean validationFailed = false;

    //if the specified sample doesn't exist, update the btxDetails object to contain 
    //an error.  Checking that the sample exists should already have been done by the 
    //UI, but do it here to be safe and to save the code below that relies on this 
    //being a valid sample from needing to check.
    String sampleId = btxDetails.getSampleId();
    boolean sampleExists = getValidator().doesSampleExist(sampleId);
    if (!sampleExists) {
      btxDetails.addActionError(
        new BtxActionError("lims.error.markSampleUnpulled.invalidSampleId", sampleId));
      validationFailed = true;
    }

    //if the sample is already unpulled don't do anything.  In this case we don't want 
    //to log anything
    boolean sampleIsPulled = getValidator().isSamplePulled(sampleId);
    if (!sampleIsPulled) {
      btxDetails.addActionError(
        new BtxActionError("lims.error.sampleAlreadyInState", sampleId, "unpulled"));
      validationFailed = true;
    }

    //if the reason is not valid, update the btxDetails object to contain an error.
    String reason = ApiFunctions.safeTrim(btxDetails.getReason());
    if (ApiFunctions.isEmpty(reason)) {
      btxDetails.addActionError(
        new BtxActionError("lims.error.markSampleUnpulled.invalidReason", sampleId));
      validationFailed = true;
    }

    //if any validation errors occured, mark the transaction incomplete and return.
    if (validationFailed) {
      btxDetails.setActionForwardRetry();
      return btxDetails;
    }

    //now that we've validated that all required data has been provided, unpull the sample
    SampleKey key = new SampleKey(sampleId);
    SampleAccessBean sampleBean = new SampleAccessBean(key);
    sampleBean.setPullYN(LimsConstants.LIMS_DB_NO);
    sampleBean.setDiscordantYN(LimsConstants.LIMS_DB_NO);
    sampleBean.setPullReason(null);
    sampleBean.setPullDate(null);
    sampleBean.commitCopyHelper();

    //Mark status of rows in OCE for this sample evaluations as not fixed.
    //MR 4986
    String userId = btxDetails.getUserId();
    getOperation().updateLimsOCEDataStatus(sampleId, OceUtil.OCE_STATUS_NOTFIXED_IND, userId);

    //if the flag to try to mark the most recent reportable evaluation as reported is set, 
    //try to do that
    String reportedEvalId = null;
    if ("true".equalsIgnoreCase(ApiFunctions.safeString(btxDetails.getReportMostRecentEval()))) {
      List evaluations = getOperation().getPathologyEvaluationsForSample(sampleId);
      Iterator iterator = evaluations.iterator();
      while (iterator.hasNext() && reportedEvalId == null) {
        PathologyEvaluationData eval = (PathologyEvaluationData) iterator.next();
        BTXDetailsReportPathologyEvaluation btxReportDetails =
          new BTXDetailsReportPathologyEvaluation();
        /* tell performReportEvaluation to ignore the checks it does on the state of the sample,
           since it doesn't have visibility to the fact we've just marked the sample unpulled
           (without this flag being set, when performReportEvaluation checks to see if the
           sample belonging to the evaluation we're trying to report (i.e. the sample we've
           just marked unpulled) is pulled, it sees it as still being pulled even though we've
           marked it unpulled here - that change is not committed to the database yet.)
        */
        btxReportDetails.setIgnoreSampleChecks(true);
        btxReportDetails.setLoggedInUserSecurityInfo(btxDetails.getLoggedInUserSecurityInfo());
        btxReportDetails.setPathologyEvaluationId(eval.getEvaluationId());
        btxReportDetails.setBeginTimestamp(btxDetails.getBeginTimestamp());
        /* to prevent the reporting of the evaluation from having it's own ICP record
           (which might be somewhat confusing, since we indicate in the Unpull record
           that an evaluation was marked reported) just call performReportPathologyEvaluation
           directly, instead of through an EJB call.
        */
        btxReportDetails = performReportPathologyEvaluation(btxReportDetails);
        if (btxReportDetails.isTransactionCompleted()) {
          reportedEvalId = btxReportDetails.getPathologyEvaluationId();
          btxDetails.setReportedEvalId(reportedEvalId);
        }
      }
    }

    //btxDetails.addActionError(
    //  new BtxActionError("lims.message.markSampleUnPulled.success", sampleId));
    if (reportedEvalId == null) {
      btxDetails.addActionMessage(
        new BtxActionMessage("lims.message.markSampleUnPulled.success", sampleId));
    }
    else {
      btxDetails.addActionMessage(
        new BtxActionMessage(
          "lims.message.markSampleUnPulledWithReportedEval.success",
          sampleId,
          reportedEvalId));
    }
    //return the btxDetails object passed in, marked as completed so logging takes place.
    return (BTXDetailsMarkSampleUnpulled) btxDetails.setActionForwardTXCompleted(
      LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
  }

  //Method to do work of reporting a pathology evaluation. 
  private BTXDetailsReportPathologyEvaluation performReportPathologyEvaluation(BTXDetailsReportPathologyEvaluation btxDetails)
    throws Exception {
    PathologyEvaluationAccessBean evaluation = null;
    //get pathology evaluation Id and validate it.
    boolean validationFailed = false;

    try {
      //Find PathologyEvaluationAccessBean object for pathology evaluation id.
      evaluation = new PathologyEvaluationAccessBean(btxDetails.getPathologyEvaluationId());
    }
    catch (ObjectNotFoundException onfe) {
      //Invalid pathology evaluation id. Mark the transaction incomplete and return.
      BtxActionError btxError =
        new BtxActionError(
          "lims.error.reportPathologyEvaluation.invalidEvaluationId",
          btxDetails.getPathologyEvaluationId());
      return (BTXDetailsReportPathologyEvaluation) btxDetails.setActionForwardRetry(btxError);
    }

    //if we retrieved the evaluation, get the sample and slide id and update the
    //btxDetails object.  This must be done for transaction history to be recorded correctly      
    String slideId = evaluation.getSlideId();
    String sampleId = evaluation.getSampleId();
    btxDetails.setSlideId(slideId);
    btxDetails.setSampleId(sampleId);

    //don't allow this evaluation to be marked reported if it's sample is pulled,
    //if the flag to check this hasn't been turned off
    if (!btxDetails.isIgnoreSampleChecks()) {
      boolean sampleIsPulled = getValidator().isSamplePulled(sampleId);
      if (sampleIsPulled) {
        btxDetails.addActionError(
          new BtxActionError(
            "lims.error.reportPathologyEvaluation.sampleIsPulled",
            btxDetails.getPathologyEvaluationId()));
        validationFailed = true;
      }
    }

    //if this evaluation is already marked reported, don't do anything.  In this case we don't
    //want to log anything
    boolean evaluationIsReported =
      evaluation.getReportedYN() != null
        && LimsConstants.LIMS_DB_YES.equalsIgnoreCase(evaluation.getReportedYN());
    if (evaluationIsReported) {
      btxDetails.addActionError(
        new BtxActionError(
          "lims.error.reportPathologyEvaluation.evaluationAlreadyReported",
          btxDetails.getPathologyEvaluationId()));
      validationFailed = true;
    }

    //if this evaluation would cause the sample to be marked pulled or discordant, don't allow it
    //to be reported
    int cellularStromaCells = evaluation.getCellularstromaCells().intValue();
    int hypoacellularStromaCells = evaluation.getHypoacellularstromaCells().intValue();
    int lesionCells = evaluation.getLesionCells().intValue();
    int necrosisCells = evaluation.getNecrosisCells().intValue();
    int normalCells = evaluation.getNormalCells().intValue();
    int tumorCells = evaluation.getTumorCells().intValue();
    String diagnosis = evaluation.getDiagnosis();
    String donorInstDiagnosis = getDiagnosisFromSampleId(sampleId);
    SampleAppearance sampleAppearance =
      new SampleAppearance(
        sampleId,
        donorInstDiagnosis,
        diagnosis,
        tumorCells,
        lesionCells,
        normalCells,
        necrosisCells,
        hypoacellularStromaCells,
        cellularStromaCells,
        btxDetails.getLoggedInUserSecurityInfo());

    int appearanceRule = sampleAppearance.sampleAppearanceAcceptability();
    if (appearanceRule != FormLogic.MICROSCOPIC_APPEARANCE_OK) {
      btxDetails.addActionError(
        new BtxActionError(
          "lims.error.reportPathologyEvaluation.evaluationWouldPullSample",
          btxDetails.getPathologyEvaluationId()));
      validationFailed = true;
    }

    //if any validation errors occured, mark the transaction incomplete and return.
    if (validationFailed) {
      btxDetails.setActionForwardRetry();
      return btxDetails;
    }

    //if the sample was released, we're going to mark it unreleased when we report this evaluation
    //and log that fact, so set the flag on the btxDetails object so we can log things
    //correctly
    boolean sampleIsReleased = getValidator().isSampleReleased(sampleId);
    btxDetails.setSampleUnreleased(sampleIsReleased);

    //if the sample was QCPosted, we're going to mark it unQCPosted when we report this evaluation
    //and log that fact, so set the flag on the btxDetails object so we can log things
    //correctly
    boolean sampleIsQCPosted = getValidator().isSampleQCPosted(sampleId);
    btxDetails.setSampleUnQCPosted(sampleIsQCPosted);

    //if the sample had a reported evaluation, update the btxDetails object so the id of 
    //that evaluation can be logged.
    PathologyEvaluationData reportedEval = getOperation().getReportedPathologyEvaluation(sampleId);
    if (reportedEval != null) {
      btxDetails.setUnreportedEvaluationId(reportedEval.getEvaluationId());
    }

    //unreport any existing reported evaluation and report this one
    setReportedYN(sampleId, btxDetails.getPathologyEvaluationId());

    //Set QCVERIFIED status on the sample
    SamplestatusAccessBean sample = new SamplestatusAccessBean();
    SampleKey key = new SampleKey(sampleId);
    sample.setInit_argSample(key);
    sample.setInit_argSample_status_datetime(new Timestamp(new java.util.Date().getTime()));
    sample.setInit_argStatus_type_code(FormLogic.SMPL_QCVERIFIED);
    sample.commitCopyHelper();
    //Mark the sample unreleased and unQCPosted
    SampleAccessBean sampleBean = new SampleAccessBean(key);
    sampleBean.setReleasedYN(LimsConstants.LIMS_DB_NO);
    sampleBean.setQcpostedYN(LimsConstants.LIMS_DB_NO);
    sampleBean.commitCopyHelper();

    // MR 5049 - do not pass back a message when this is successful     
    //btxDetails.addActionError("reported", new BtxActionError("lims.message.markEvaluationReported.success", btxDetails.getPathologyEvaluationId()));
    btxDetails.addActionMessage(
      new BtxActionMessage(
        "lims.message.markEvaluationReported.success",
        btxDetails.getPathologyEvaluationId()));
    //return the btxDetails object passed in, marked as completed so logging takes place.
    return (BTXDetailsReportPathologyEvaluation) btxDetails.setActionForwardTXCompleted(
      LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
  }

  //private method used in determining microscopic appearance when creating
  //a new pathology evaluation
  private String getDiagnosisFromSampleId(String sampleId) throws Exception {
    String donorInstDiagnosis = null;
    Connection con = ApiFunctions.getDbConnection();
    PreparedStatement pstmt = null;
    ResultSet results = null;
    try {
      //try and get the DDC diagnosis
      StringBuffer buff = new StringBuffer();
      buff.append("select pdc.DIAGNOSIS_CONCEPT_ID ");
      buff.append("from pdc_pathology_report pdc, iltds_asm asm, iltds_sample sample ");
      buff.append("where sample.ASM_ID = asm.ASM_ID ");
      buff.append("and asm.CONSENT_ID = pdc.CONSENT_ID ");
      buff.append("and sample.SAMPLE_BARCODE_ID = ?");
      pstmt = con.prepareStatement(buff.toString());
      pstmt.setString(1, sampleId);
      results = pstmt.executeQuery();
      while (results.next()) {
        donorInstDiagnosis = results.getString("DIAGNOSIS_CONCEPT_ID");
      }

      //if we could not get the DDC diagnosis, we have to use the iltds 
      //donor institution diagnosis
      if (donorInstDiagnosis == null) {
        buff = new StringBuffer();
        buff.append("select ic.DISEASE_CONCEPT_ID ");
        buff.append("from iltds_informed_consent ic, iltds_asm asm, iltds_sample sample ");
        buff.append("where sample.ASM_ID = asm.ASM_ID ");
        buff.append("and asm.CONSENT_ID = ic.CONSENT_ID ");
        buff.append("and sample.SAMPLE_BARCODE_ID = ?  ");
        pstmt = con.prepareStatement(buff.toString());
        pstmt.setString(1, sampleId);
        results = pstmt.executeQuery();
        while (results.next()) {
          donorInstDiagnosis = results.getString("DISEASE_CONCEPT_ID");
        }
      }
    }
    finally {
      ApiFunctions.close(con, pstmt, results);
    }
    return donorInstDiagnosis;
  }

  //method to do the work of preparing a new pathology evaluation
  private BTXDetailsCreatePathologyEvaluationPrepare performCreatePathologyEvaluationPrepare(BTXDetailsCreatePathologyEvaluationPrepare btxDetails)
    throws Exception {

    //get the slide information, validate it, and return the required information
    boolean validationFailed = false;

    //if the specified slide doesn't exist, update the btxDetails object to contain 
    //an error.  Checking that the slide exists should already have been done by the 
    //UI, but do it here to be safe and to save the code below that relies on this 
    //being a valid slide from needing to check.
    String slideId = btxDetails.getSlideId();
    boolean slideExists = getValidator().doesSlideExist(slideId);
    if (!slideExists) {
      btxDetails.addActionError(
        new BtxActionError("lims.error.createPathEvalPrepare.invalidSlideId", slideId));
      validationFailed = true;
    }

    //if a source evaluation id was specified, make sure it's valid
    String evalId = btxDetails.getSourceEvaluationId();
    if (!ApiFunctions.isEmpty(evalId)) {
      boolean evalExists = getValidator().doesEvaluationExist(evalId);
      if (!evalExists) {
        btxDetails.addActionError(
          new BtxActionError(
            "lims.error.createPathEvalPrepare.invalidSourceEvalId",
            slideId,
            evalId));
        validationFailed = true;
      }
    }

    //get the sample id for this slide and make sure it's not pulled
    String sampleId = getOperation().getSampleIdForSlide(slideId);
    //if we don't get a sample id back from the call above, return an error
    if (sampleId == null) {
      btxDetails.addActionError(
        new BtxActionError("lims.error.createPathEvalPrepare.sampleIdRetrievalFailed", slideId));
      validationFailed = true;
    }
    //otherwise do the pull check
    else {
      boolean sampleIsPulled = getValidator().isSamplePulled(sampleId);
      if (sampleIsPulled) {
        btxDetails.addActionError(
          new BtxActionError("lims.error.createPathEvalPrepare.sampleIsPulled", slideId, sampleId));
        validationFailed = true;
      }
    }

    //if there's a reported evaluation for this sample, get the re_pv incidents for the
    //sample that have been created after the reported date on that evaluation (so we can
    //display them on the CreateEvaluation screen).  If the user is required to create an 
    //incident before re-PVing a sample and this is a re-pv, make sure that's been done
    //(i.e. there's at least one rePV incident).
    PathologyEvaluationData reportedEval = getOperation().getReportedPathologyEvaluation(sampleId);
    List rePVIncidents = null;
    if (reportedEval != null) {
      Timestamp reportedDate = reportedEval.getReportedDate();
      rePVIncidents =
        getOperation().
        getIncidentsForSample(sampleId, LimsConstants.INCIDENT_ACTION_REPV, reportedDate);
      SecurityInfo security = btxDetails.getLoggedInUserSecurityInfo();
      if (security.isHasPrivilege(SecurityInfo.PRIV_LIMS_INC_FOR_REPV)) {
        if (rePVIncidents == null || rePVIncidents.size() < 1) {
          btxDetails.addActionError(
            new BtxActionError("lims.error.createPathEvalPrepare.noRePVIncident", sampleId));
          validationFailed = true;
        }
      }
    }

    //if any validation errors occured, mark the transaction incomplete and return.
    if (validationFailed) {
      btxDetails.setActionForwardRetry();
      return btxDetails;
    }

    //first create and populate the PathologyEvaluationSampleData data bean, and
    //attach it to the BTXDetails object passed in.  This is done before the
    //creation of the PathologyEvaluationData data bean, as information in this
    //sample data bean is used in the creation of the evaluation data bean
    PathologyEvaluationSampleData peSampleData = getOperation().getEvaluationSampleData(sampleId);
    btxDetails.setPathologyEvaluationSampleData(peSampleData);

    //now create and populate the PathologyEvaluationData data bean, and
    //attach it to the BTXDetails object passed in
    PathologyEvaluationData peData =
      initializeNewPathologyEvaluation(slideId, sampleId, evalId, peSampleData);
    btxDetails.setPathologyEvaluationData(peData);

    //finally attach the incidents we retrieved above to the BTXDetails object passed in
    btxDetails.setIncidents(rePVIncidents);

    //return the btxDetails object passed in, marked as completed.
    return (BTXDetailsCreatePathologyEvaluationPrepare) btxDetails.setActionForwardTXCompleted(
      LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
  }

  /**Private method to create a new PathologyEvaluationData data bean. */
  private PathologyEvaluationData initializeNewPathologyEvaluation(
    String slideId,
    String sampleId,
    String sourceEvalId,
    PathologyEvaluationSampleData sampleData)
    throws Exception {
    PathologyEvaluationData peData = new PathologyEvaluationData();

    //if the caller has specified an evaluation to use as the source for this new 
    //evaluation, populate the new evaluation from that evaluation
    if (!ApiFunctions.isEmpty(sourceEvalId)) {
      peData = getOperation().getPathologyEvaluationDataWithFeatureLists(sourceEvalId);
      //the concatenated internal and external comments should be blanked out (not taken
      //from the source evaluation), since these are computed when the evaluation is saved to the database.
      peData.setConcatenatedExternalData("");
      peData.setConcatenatedInternalData("");
      //determine the create method.  If the source evaluation is for the same sample as the
      //slide we're creating this evaluation for it's an Edit.  If it's for a different sample,
      //it's a copy
      String sourceSampleId = peData.getSampleId();
      if (sourceSampleId.equals(sampleId))
        peData.setCreateMethod(LimsConstants.LIMS_PE_CREATE_TYPE_EDIT);
      else
        peData.setCreateMethod(LimsConstants.LIMS_PE_CREATE_TYPE_COPY);
      //blank out the creation date, since this is determined when the evaluation is saved to the database
      peData.setCreationDate(null);
      //evaluation id should be blank - it will be determined when the evaluation is saved to the database
      peData.setEvaluationId("");
      //new evaluations are not migrated and not reported
      peData.setIsMigrated(false);
      peData.setIsReported(false);
      //microscopic appearance will be calculated when the evaluation is saved to the database
      peData.setMicroscopicAppearance("");
      //pathologist will be determined when the evaluation is saved to the database.
      peData.setPathologist("");
      //blank out the reported date, since new evaluations are not reported
      peData.setReportedDate(null);
      //set the new evaluation to have the correct sample, slide, and source evaluation ids
      peData.setSampleId(sampleId);
      peData.setSlideId(slideId);
      peData.setSourceEvaluationId(sourceEvalId);
    }
    //if the caller has not specified an evaluation to use as the source for this new 
    //evaluation, populate the new evaluation with just some bare bones information
    else {
      peData.setSlideId(slideId);
      peData.setSampleId(sampleId);
      peData.setCreateMethod(LimsConstants.LIMS_PE_CREATE_TYPE_NEW);
      /* if there is a primary section of DDC data for the case to which this sample 
       * belongs, prepopulate the diagnosis and tissue of origin 
       * fields on this PathologyEvaluationData data bean */
      PathReportSectionData primarySection = sampleData.getPrimarySection();
      if (primarySection != null) {
        peData.setDiagnosis(primarySection.getDiagnosis());
        peData.setDiagnosisOther(primarySection.getDiagnosisOther());
        peData.setTissueOfOrigin(primarySection.getTissueOrigin());
        peData.setTissueOfOriginOther(primarySection.getTissueOriginOther());
      }
      /* populate the tissue of finding field on this PathologyEvaluationData
       * data bean from the ASM (MR5785) */
      AsmData asm = sampleData.getAsm();
      if (asm != null) {
        peData.setTissueOfFinding(asm.getTissue());
        peData.setTissueOfFindingOther(asm.getTissueOther());
      }
    }
    return peData;
  }

  //method to do the work of storing a new pathology evaluation
  private BTXDetailsCreatePathologyEvaluation performCreatePathologyEvaluation(BTXDetailsCreatePathologyEvaluation btxDetails)
    throws Exception {

    if (btxDetails.isPvReport()) {
      btxDetails.setTransactionCompleted(false);
      btxDetails.setActionForward(new BTXActionForward(LimsConstants.BTX_ACTION_FORWARD_SUCCESS));
      return (BTXDetailsCreatePathologyEvaluation) btxDetails;
    }

    //get the slide and sample information, validate it, and store the evaluation information
    boolean validationFailed = false;

    //if the specified slide doesn't exist, update the btxDetails object to contain 
    //an error.  Checking that the slide exists should already have been done by the 
    //UI, but do it here to be safe and to save the code below that relies on this 
    //being a valid slide from needing to check.
    String slideId = btxDetails.getPathologyEvaluationData().getSlideId();
    boolean slideExists = getValidator().doesSlideExist(slideId);
    if (!slideExists) {
      btxDetails.addActionError(
        new BtxActionError("lims.error.createPathEval.invalidSlideId", slideId));
      validationFailed = true;
    }

    //if the specified sample doesn't exist, update the btxDetails object to contain 
    //an error.  Checking that the sample exists should already have been done by the 
    //UI, but do it here to be safe and to save the code below that relies on this 
    //being a valid sample id from needing to check.
    //if the sample id for this slide and make sure it's not pulled
    String sampleId = btxDetails.getPathologyEvaluationData().getSampleId();
    boolean sampleExists = getValidator().doesSampleExist(sampleId);
    if (!sampleExists) {
      btxDetails.addActionError(
        new BtxActionError("lims.error.createPathEval.invalidSamplelId", slideId, sampleId));
      validationFailed = true;
    }
    //otherwise do the pull check
    else {
      boolean sampleIsPulled = getValidator().isSamplePulled(sampleId);
      if (sampleIsPulled) {
        btxDetails.addActionError(
          new BtxActionError("lims.error.createPathEval.sampleIsPulled", slideId, sampleId));
        validationFailed = true;
      }
    }

    // make sure that the user has indicated that they've reviewed the DDC raw path report.
    if (!btxDetails.isReviewedRawPathReport()) {
      btxDetails.addActionError(
        new BtxActionError(
          "lims.error.createPathEval.rawPathReportNotReviewed",
          slideId,
          sampleId));
      validationFailed = true;
    }

    //make sure all the cell values (tumor cells, necrosis cells, etc) are all
    //greater than or equal to 0, and that they total to 100
    int cellularStromaCells = btxDetails.getPathologyEvaluationData().getCellularStromaCells();
    if (cellularStromaCells < 0) {
      btxDetails.addActionError(
        new BtxActionError(
          "lims.error.createPathEval.invalidCellValue",
          slideId,
          sampleId,
          "Cellular Stroma"));
      validationFailed = true;
    }
    int hypoacellularStromaCells =
      btxDetails.getPathologyEvaluationData().getHypoacellularStromaCells();
    if (hypoacellularStromaCells < 0) {
      btxDetails.addActionError(
        new BtxActionError(
          "lims.error.createPathEval.invalidCellValue",
          slideId,
          sampleId,
          "Hypo/Acellular Stroma"));
      validationFailed = true;
    }
    int lesionCells = btxDetails.getPathologyEvaluationData().getLesionCells();
    if (lesionCells < 0) {
      btxDetails.addActionError(
        new BtxActionError(
          "lims.error.createPathEval.invalidCellValue",
          slideId,
          sampleId,
          "Lesion"));
      validationFailed = true;
    }
    int necrosisCells = btxDetails.getPathologyEvaluationData().getNecrosisCells();
    if (necrosisCells < 0) {
      btxDetails.addActionError(
        new BtxActionError(
          "lims.error.createPathEval.invalidCellValue",
          slideId,
          sampleId,
          "Necrosis"));
      validationFailed = true;
    }
    int normalCells = btxDetails.getPathologyEvaluationData().getNormalCells();
    if (normalCells < 0) {
      btxDetails.addActionError(
        new BtxActionError(
          "lims.error.createPathEval.invalidCellValue",
          slideId,
          sampleId,
          "Normal"));
      validationFailed = true;
    }
    int tumorCells = btxDetails.getPathologyEvaluationData().getTumorCells();
    if (tumorCells < 0) {
      btxDetails.addActionError(
        new BtxActionError(
          "lims.error.createPathEval.invalidCellValue",
          slideId,
          sampleId,
          "Tumor"));
      validationFailed = true;
    }
    int total =
      cellularStromaCells
        + hypoacellularStromaCells
        + lesionCells
        + necrosisCells
        + normalCells
        + tumorCells;
    if (total != 100) {
      btxDetails.addActionError(
        new BtxActionError("lims.error.createPathEval.invalidCellTotal", slideId, sampleId));
      validationFailed = true;
    }

    //make sure a diagnosis has been specified.  If the diagnosis is "Other", make sure 
    //the other text has been provided
    String diagnosis = btxDetails.getPathologyEvaluationData().getDiagnosis();
    String otherDiagnosis = btxDetails.getPathologyEvaluationData().getDiagnosisOther();
    if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(diagnosis))) {
      btxDetails.addActionError(
        new BtxActionError("lims.error.createPathEval.invalidDiagnosis", slideId, sampleId));
      validationFailed = true;
    }
    else {
      if (FormLogic.OTHER_DX.equals(diagnosis)) {
        if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(otherDiagnosis))) {
          btxDetails.addActionError(
            new BtxActionError(
              "lims.error.createPathEval.invalidOtherValue",
              slideId,
              sampleId,
              "Diagnosis"));
          validationFailed = true;
        }
      }
    }

    //make sure a tissue of finding has been specified.  If the tissue is "Other", make sure 
    //the other text has been provided
    String tissueFinding = btxDetails.getPathologyEvaluationData().getTissueOfFinding();
    String otherTissueFinding = btxDetails.getPathologyEvaluationData().getTissueOfFindingOther();
    if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(tissueFinding))) {
      btxDetails.addActionError(
        new BtxActionError("lims.error.createPathEval.invalidTissueFinding", slideId, sampleId));
      validationFailed = true;
    }
    else {
      if (FormLogic.OTHER_TISSUE.equals(tissueFinding)) {
        if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(otherTissueFinding))) {
          btxDetails.addActionError(
            new BtxActionError(
              "lims.error.createPathEval.invalidOtherValue",
              slideId,
              sampleId,
              "Tissue of Finding"));
          validationFailed = true;
        }
      }
    }

    //make sure a tissue of origin has been specified.  If the tissue is "Other", make sure 
    //the other text has been provided
    String tissueOrigin = btxDetails.getPathologyEvaluationData().getTissueOfOrigin();
    String otherTissueOrigin = btxDetails.getPathologyEvaluationData().getTissueOfOriginOther();
    if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(tissueOrigin))) {
      btxDetails.addActionError(
        new BtxActionError("lims.error.createPathEval.invalidTissueOrigin", slideId, sampleId));
      validationFailed = true;
    }
    else {
      if (FormLogic.OTHER_TISSUE.equals(tissueOrigin)) {
        if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(otherTissueOrigin))) {
          btxDetails.addActionError(
            new BtxActionError(
              "lims.error.createPathEval.invalidOtherValue",
              slideId,
              sampleId,
              "Tissue of Origin"));
          validationFailed = true;
        }
      }
    }

    //make sure the lesion features total 100%  
    List lesionFeatures = btxDetails.getPathologyEvaluationData().getLesions();
    if (lesionFeatures != null && lesionFeatures.size() > 0) {
      int lesionTotal = 0;
      PathologyEvaluationFeatureData feature;
      Iterator iter = lesionFeatures.iterator();
      while (iter.hasNext()) {
        feature = (PathologyEvaluationFeatureData) iter.next();
        lesionTotal = lesionTotal + feature.getValue();
      }
      if (lesionTotal != 100) {
        btxDetails.addActionError(
          new BtxActionError(
            "lims.error.createPathEval.featureTotalError",
            slideId,
            sampleId,
            "lesion",
            String.valueOf(lesionTotal)));
        validationFailed = true;
      }
    }

    //make sure the structure features total 100% 
    List structureFeatures = btxDetails.getPathologyEvaluationData().getStructures();
    if (structureFeatures != null && structureFeatures.size() > 0) {
      int structureTotal = 0;
      PathologyEvaluationFeatureData feature;
      Iterator iter = structureFeatures.iterator();
      while (iter.hasNext()) {
        feature = (PathologyEvaluationFeatureData) iter.next();
        structureTotal = structureTotal + feature.getValue();
      }
      if (structureTotal != 100) {
        btxDetails.addActionError(
          new BtxActionError(
            "lims.error.createPathEval.featureTotalError",
            slideId,
            sampleId,
            "structure",
            String.valueOf(structureTotal)));
        validationFailed = true;
      }
    }

    //if the user has added lesions to the evaluation but hasn't given the lesion cell count
    //a value, return an error
    if (btxDetails.getPathologyEvaluationData().getLesions().size() > 0
      && btxDetails.getPathologyEvaluationData().getLesionCells() <= 0) {
      btxDetails.addActionError(
        new BtxActionError(
          "lims.error.createPathEval.lesionsButNoValue",
          slideId,
          sampleId,
          "lesion"));
      validationFailed = true;
    }

    //if any of the components that allow features have them, make sure the component(s)
    //have a value > 0
    if (btxDetails.getPathologyEvaluationData().getTumorFeatures().size() > 0
      && btxDetails.getPathologyEvaluationData().getTumorCells() <= 0) {
      btxDetails.addActionError(
        new BtxActionError(
          "lims.error.createPathEval.featuresButNoValue",
          slideId,
          sampleId,
          "tumor"));
      validationFailed = true;
    }
    if (btxDetails.getPathologyEvaluationData().getCellularStromaFeatures().size() > 0
      && btxDetails.getPathologyEvaluationData().getCellularStromaCells() <= 0) {
      btxDetails.addActionError(
        new BtxActionError(
          "lims.error.createPathEval.featuresButNoValue",
          slideId,
          sampleId,
          "cellular stroma"));
      validationFailed = true;
    }
    if (btxDetails.getPathologyEvaluationData().getHypoacellularStromaFeatures().size() > 0
      && btxDetails.getPathologyEvaluationData().getHypoacellularStromaCells() <= 0) {
      btxDetails.addActionError(
        new BtxActionError(
          "lims.error.createPathEval.featuresButNoValue",
          slideId,
          sampleId,
          "hypo/acellular stroma"));
      validationFailed = true;
    }

    //validate that internal and external "other" comments do not exceed 1000 characters
    Iterator extIterator = btxDetails.getPathologyEvaluationData().getExternalFeatures().iterator();
    while (extIterator.hasNext()) {
      PathologyEvaluationFeatureData feature = (PathologyEvaluationFeatureData) extIterator.next();
      String text = feature.getOtherText();
      if (text != null) {
        if (text.length() > LimsConstants.LIMS_NON_OCE_FEATURE_MAXLENGTH) {
          btxDetails.addActionError(
            new BtxActionError(
              "lims.error.createPathEval.featureLengthExceeded",
              slideId,
              sampleId,
              "External Comment",
              String.valueOf(LimsConstants.LIMS_NON_OCE_FEATURE_MAXLENGTH)));
          validationFailed = true;
          break;
        }
      }
    }
    Iterator intIterator = btxDetails.getPathologyEvaluationData().getInternalFeatures().iterator();
    while (intIterator.hasNext()) {
      PathologyEvaluationFeatureData feature = (PathologyEvaluationFeatureData) intIterator.next();
      String text = feature.getOtherText();
      if (text != null) {
        if (text.length() > LimsConstants.LIMS_NON_OCE_FEATURE_MAXLENGTH) {
          btxDetails.addActionError(
            new BtxActionError(
              "lims.error.createPathEval.featureLengthExceeded",
              slideId,
              sampleId,
              "Internal Quality Issue",
              String.valueOf(LimsConstants.LIMS_NON_OCE_FEATURE_MAXLENGTH)));
          validationFailed = true;
          break;
        }
      }
    }

    //validate that all OCE enabled features do not exceed 200 characters
    Iterator lesionIterator = btxDetails.getPathologyEvaluationData().getLesions().iterator();
    while (lesionIterator.hasNext()) {
      PathologyEvaluationFeatureData feature =
        (PathologyEvaluationFeatureData) lesionIterator.next();
      String text = feature.getOtherText();
      if (text != null) {
        if (text.length() > LimsConstants.LIMS_OCE_FEATURE_MAXLENGTH) {
          btxDetails.addActionError(
            new BtxActionError(
              "lims.error.createPathEval.featureLengthExceeded",
              slideId,
              sampleId,
              "Lesion",
              String.valueOf(LimsConstants.LIMS_OCE_FEATURE_MAXLENGTH)));
          validationFailed = true;
          break;
        }
      }
    }
    Iterator inflamIterator = btxDetails.getPathologyEvaluationData().getInflammations().iterator();
    while (inflamIterator.hasNext()) {
      PathologyEvaluationFeatureData feature =
        (PathologyEvaluationFeatureData) inflamIterator.next();
      String text = feature.getOtherText();
      if (text != null) {
        if (text.length() > LimsConstants.LIMS_OCE_FEATURE_MAXLENGTH) {
          btxDetails.addActionError(
            new BtxActionError(
              "lims.error.createPathEval.featureLengthExceeded",
              slideId,
              sampleId,
              "Inflammation",
              String.valueOf(LimsConstants.LIMS_OCE_FEATURE_MAXLENGTH)));
          validationFailed = true;
          break;
        }
      }
    }
    Iterator structureIterator = btxDetails.getPathologyEvaluationData().getStructures().iterator();
    while (structureIterator.hasNext()) {
      PathologyEvaluationFeatureData feature =
        (PathologyEvaluationFeatureData) structureIterator.next();
      String text = feature.getOtherText();
      if (text != null) {
        if (text.length() > LimsConstants.LIMS_OCE_FEATURE_MAXLENGTH) {
          btxDetails.addActionError(
            new BtxActionError(
              "lims.error.createPathEval.featureLengthExceeded",
              slideId,
              sampleId,
              "Structure",
              String.valueOf(LimsConstants.LIMS_OCE_FEATURE_MAXLENGTH)));
          validationFailed = true;
          break;
        }
      }
    }
    Iterator tumorIterator = btxDetails.getPathologyEvaluationData().getTumorFeatures().iterator();
    while (tumorIterator.hasNext()) {
      PathologyEvaluationFeatureData feature =
        (PathologyEvaluationFeatureData) tumorIterator.next();
      String text = feature.getOtherText();
      if (text != null) {
        if (text.length() > LimsConstants.LIMS_OCE_FEATURE_MAXLENGTH) {
          btxDetails.addActionError(
            new BtxActionError(
              "lims.error.createPathEval.featureLengthExceeded",
              slideId,
              sampleId,
              "Tumor",
              String.valueOf(LimsConstants.LIMS_OCE_FEATURE_MAXLENGTH)));
          validationFailed = true;
          break;
        }
      }
    }
    Iterator cellStromaIterator =
      btxDetails.getPathologyEvaluationData().getCellularStromaFeatures().iterator();
    while (cellStromaIterator.hasNext()) {
      PathologyEvaluationFeatureData feature =
        (PathologyEvaluationFeatureData) cellStromaIterator.next();
      String text = feature.getOtherText();
      if (text != null) {
        if (text.length() > LimsConstants.LIMS_OCE_FEATURE_MAXLENGTH) {
          btxDetails.addActionError(
            new BtxActionError(
              "lims.error.createPathEval.featureLengthExceeded",
              slideId,
              sampleId,
              "Cellular Stroma",
              String.valueOf(LimsConstants.LIMS_OCE_FEATURE_MAXLENGTH)));
          validationFailed = true;
          break;
        }
      }
    }
    Iterator acellStromaIterator =
      btxDetails.getPathologyEvaluationData().getHypoacellularStromaFeatures().iterator();
    while (acellStromaIterator.hasNext()) {
      PathologyEvaluationFeatureData feature =
        (PathologyEvaluationFeatureData) acellStromaIterator.next();
      String text = feature.getOtherText();
      if (text != null) {
        if (text.length() > LimsConstants.LIMS_OCE_FEATURE_MAXLENGTH) {
          btxDetails.addActionError(
            new BtxActionError(
              "lims.error.createPathEval.featureLengthExceeded",
              slideId,
              sampleId,
              "Hypo/Acellular Stroma",
              String.valueOf(LimsConstants.LIMS_OCE_FEATURE_MAXLENGTH)));
          validationFailed = true;
          break;
        }
      }
    }

    //make sure the concatenated internal/external comments do not exceed 4000 characters. 
    //we compute the comments and then null them out so they are recomputed later because
    //further down in the code we might remove features (if they were given a 0 for a value), 
    //and we want the final comments to reflect that.  This could result in a false
    //positive here, because the final comments may not exceed 4000 characters once the
    //features are removed, but most likely the features with a 0 are an error anyway so
    //this is okay.
    String intComments = btxDetails.getPathologyEvaluationData().getConcatenatedInternalData();
    btxDetails.getPathologyEvaluationData().setConcatenatedInternalData(null);
    String extComments = btxDetails.getPathologyEvaluationData().getConcatenatedExternalData();
    btxDetails.getPathologyEvaluationData().setConcatenatedExternalData(null);
    if (intComments.length() > LimsConstants.LIMS_CONCATENATED_COMMENT_MAXLENGTH) {
      btxDetails.addActionError(
        new BtxActionError(
          "lims.error.createPathEval.commentLengthExceeded",
          slideId,
          sampleId,
          "Internal Quality Issues",
          String.valueOf(LimsConstants.LIMS_CONCATENATED_COMMENT_MAXLENGTH)));
      validationFailed = true;
    }
    if (extComments.length() > LimsConstants.LIMS_CONCATENATED_COMMENT_MAXLENGTH) {
      btxDetails.addActionError(
        new BtxActionError(
          "lims.error.createPathEval.commentLengthExceeded",
          slideId,
          sampleId,
          "External Comments",
          String.valueOf(LimsConstants.LIMS_CONCATENATED_COMMENT_MAXLENGTH)));
      validationFailed = true;
    }

    //if any validation errors occured, mark the transaction incomplete and return.
    if (validationFailed) {
      btxDetails.setActionForwardRetry();
      return btxDetails;
    }

    //if this evaluation will result in the sample being marked pulled or discordant,
    //and the user has not been warned about that, return the btxDetails object to the
    //front end
    String donorInstDiagnosis = getDiagnosisFromSampleId(sampleId);
    SampleAppearance sampleAppearance =
      new SampleAppearance(
        sampleId,
        donorInstDiagnosis,
        diagnosis,
        tumorCells,
        lesionCells,
        normalCells,
        necrosisCells,
        hypoacellularStromaCells,
        cellularStromaCells,
        btxDetails.getLoggedInUserSecurityInfo());

    int appearanceRule = sampleAppearance.sampleAppearanceAcceptability();
    if (appearanceRule != FormLogic.MICROSCOPIC_APPEARANCE_OK && !btxDetails.isUserWarned()) {
      //Rule 1 is that the sample will be marked pulled
      if (appearanceRule == FormLogic.MICROSCOPIC_APPEARANCE_RULE_1) {
        btxDetails.setSampleWillBePulled(true);
      }
      //Rule 2 or 3 is that the sample will be marked discordant
      else if (
        appearanceRule == FormLogic.MICROSCOPIC_APPEARANCE_RULE_2
          || appearanceRule == FormLogic.MICROSCOPIC_APPEARANCE_RULE_3) {
        btxDetails.setSampleWillBeDiscordant(true);
      }
      //Unknown rule - return an error
      else {
        btxDetails.addActionError(
          new BtxActionError(
            "lims.error.createPathEval.unknownAppearanceRule",
            slideId,
            sampleId,
            (new Integer(appearanceRule)).toString()));
      }
      btxDetails.setTransactionCompleted(false);
      btxDetails.setActionForward(new BTXActionForward(LimsConstants.BTX_ACTION_FORWARD_WARNING));
      return btxDetails;
    }

    //get a new id for the evaluation.  If that fails return an error
    String evaluationId = getIdForNewEvaluation();
    if (evaluationId == null) {
      BtxActionError btxError =
        new BtxActionError("lims.error.createPathEval.newIdFailure", slideId, sampleId);
      return (BTXDetailsCreatePathologyEvaluation) btxDetails.setActionForwardRetry(btxError);
    }
    else {
      btxDetails.getPathologyEvaluationData().setEvaluationId(evaluationId);
    }

    //any lesions or structures that have a value of 0 must be removed.
    //store the name of each lesion/structure in a Stringbuffer so we can
    //return a BtxActionError letting the user know that they were removed
    StringBuffer removedItems = new StringBuffer();
    boolean firstItem = true;
    boolean featuresRemoved = false;
    //first do Lesions
    List lesions = btxDetails.getPathologyEvaluationData().getLesions();
    if (lesions != null && lesions.size() > 0) {
      PathologyEvaluationFeatureData feature;
      Iterator iter = lesions.iterator();
      while (iter.hasNext()) {
        feature = (PathologyEvaluationFeatureData) iter.next();
        if (feature.getValue() == 0) {
          if (!firstItem) {
            removedItems.append(", ");
          }
          removedItems.append(feature.getFeatureName());
          firstItem = false;
          featuresRemoved = true;
          iter.remove();
        }
      }
    }
    //now do Structures
    List structures = btxDetails.getPathologyEvaluationData().getStructures();
    if (structures != null && structures.size() > 0) {
      PathologyEvaluationFeatureData feature;
      Iterator iter = structures.iterator();
      while (iter.hasNext()) {
        feature = (PathologyEvaluationFeatureData) iter.next();
        if (feature.getValue() == 0) {
          if (!firstItem) {
            removedItems.append(", ");
          }
          removedItems.append(feature.getFeatureName());
          firstItem = false;
          featuresRemoved = true;
          iter.remove();
        }
      }
    }

    //set the isReported and isMigrated values to no.
    btxDetails.getPathologyEvaluationData().setIsReported(false);
    btxDetails.getPathologyEvaluationData().setIsMigrated(false);

    //if for any reason the create type is invalid, make it "new"
    String createType = btxDetails.getPathologyEvaluationData().getCreateMethod();
    if (createType == null
      || (!LimsConstants.LIMS_PE_CREATE_TYPE_COPY.equals(createType)
        && !LimsConstants.LIMS_PE_CREATE_TYPE_EDIT.equals(createType)
        && !LimsConstants.LIMS_PE_CREATE_TYPE_NEW.equals(createType))) {
      btxDetails.getPathologyEvaluationData().setCreateMethod(
        LimsConstants.LIMS_PE_CREATE_TYPE_NEW);
    }

    //set the create date and pathologist
    btxDetails.getPathologyEvaluationData().setCreationDate(
      new java.sql.Timestamp(new java.util.Date().getTime()));
    btxDetails.getPathologyEvaluationData().setPathologist(btxDetails.getUserId());

    //set the concatenated internal and external comments
    String internalComments = btxDetails.getPathologyEvaluationData().getConcatenatedInternalData();
    String externalComments = btxDetails.getPathologyEvaluationData().getConcatenatedExternalData();
    btxDetails.getPathologyEvaluationData().setConcatenatedExternalData(externalComments);
    btxDetails.getPathologyEvaluationData().setConcatenatedInternalData(internalComments);

    //determine the microscopic appearance
    String microscopicAppearance = sampleAppearance.computeSampleAppearance(false);
    btxDetails.getPathologyEvaluationData().setMicroscopicAppearance(microscopicAppearance);

    //save the evaluation data
    PathologyEvaluationAccessBean evalBean = new PathologyEvaluationAccessBean();
    evalBean.setInit_evaluationId(btxDetails.getPathologyEvaluationData().getEvaluationId());
    evalBean.setInit_slideID(btxDetails.getPathologyEvaluationData().getSlideId());
    evalBean.setInit_sampleId(btxDetails.getPathologyEvaluationData().getSampleId());
    evalBean.setInit_microscopicAppearance(
      btxDetails.getPathologyEvaluationData().getMicroscopicAppearance());
    if (btxDetails.getPathologyEvaluationData().isReported()) {
      evalBean.setInit_reportedYN(LimsConstants.LIMS_DB_YES);
    }
    else {
      evalBean.setInit_reportedYN(LimsConstants.LIMS_DB_NO);
    }
    evalBean.setInit_createMethod(btxDetails.getPathologyEvaluationData().getCreateMethod());
    evalBean.setInit_tumorCells(
      new Integer(btxDetails.getPathologyEvaluationData().getTumorCells()));
    evalBean.setInit_normalCells(
      new Integer(btxDetails.getPathologyEvaluationData().getNormalCells()));
    evalBean.setInit_hypoacellularstromaCells(
      new Integer(btxDetails.getPathologyEvaluationData().getHypoacellularStromaCells()));
    evalBean.setInit_necrosisCells(
      new Integer(btxDetails.getPathologyEvaluationData().getNecrosisCells()));
    evalBean.setInit_lesionCells(
      new Integer(btxDetails.getPathologyEvaluationData().getLesionCells()));
    evalBean.setInit_diagnosis(btxDetails.getPathologyEvaluationData().getDiagnosis());
    evalBean.setDiagnosisOther(btxDetails.getPathologyEvaluationData().getDiagnosisOther());
    evalBean.setInit_tissueOfOrigin(btxDetails.getPathologyEvaluationData().getTissueOfOrigin());
    evalBean.setInit_tissueOfFinding(btxDetails.getPathologyEvaluationData().getTissueOfFinding());
    evalBean.setTissueOfOriginOther(
      btxDetails.getPathologyEvaluationData().getTissueOfOriginOther());
    evalBean.setCreateDate(
      new java.sql.Timestamp(btxDetails.getPathologyEvaluationData().getCreationDate().getTime()));
    evalBean.setPathologist(btxDetails.getPathologyEvaluationData().getPathologist());
    if (btxDetails.getPathologyEvaluationData().isMigrated()) {
      evalBean.setMigratedYN(LimsConstants.LIMS_DB_YES);
    }
    else {
      evalBean.setMigratedYN(LimsConstants.LIMS_DB_NO);
    }
    evalBean.setTissueOfFindingOther(
      btxDetails.getPathologyEvaluationData().getTissueOfFindingOther());
    evalBean.setConcatenatedExternalComments(
      btxDetails.getPathologyEvaluationData().getConcatenatedExternalData());
    evalBean.setConcatenatedInternalComments(
      btxDetails.getPathologyEvaluationData().getConcatenatedInternalData());
    evalBean.setSourceEvaluationId(btxDetails.getPathologyEvaluationData().getSourceEvaluationId());
    evalBean.setInit_cellularstromaCells(
      new Integer(btxDetails.getPathologyEvaluationData().getCellularStromaCells()));
    evalBean.setLesions(btxDetails.getPathologyEvaluationData().getLesions());
    evalBean.setInflammations(btxDetails.getPathologyEvaluationData().getInflammations());
    evalBean.setStructures(btxDetails.getPathologyEvaluationData().getStructures());
    evalBean.setTumorFeatures(btxDetails.getPathologyEvaluationData().getTumorFeatures());
    evalBean.setCellularStromaFeatures(
      btxDetails.getPathologyEvaluationData().getCellularStromaFeatures());
    evalBean.setHypoacellularStromaFeatures(
      btxDetails.getPathologyEvaluationData().getHypoacellularStromaFeatures());
    evalBean.setExternalFeatures(btxDetails.getPathologyEvaluationData().getExternalFeatures());
    evalBean.setInternalFeatures(btxDetails.getPathologyEvaluationData().getInternalFeatures());
    evalBean.commitCopyHelper();

    //if we removed any lesions/structures above, send back a BtxActionError to let the user know
    if (featuresRemoved) {
      BtxActionError btxError =
        new BtxActionError("lims.error.createPathEval.featuresRemoved", removedItems.toString());
      btxDetails.addActionError(btxError);
    }

    //take care of any OCE related database insertions
    btxDetails = handleOCEDataForNewEvaluation(btxDetails);

    //set the slide location to be storage
    SlideAccessBean slideBean = new SlideAccessBean(slideId);
    slideBean.setCurrentLocation(LimsConstants.LIMS_LOCATION_STORAGE);
    slideBean.commitCopyHelper();

    //if the sample is to be marked pulled or discordant, do that now as long as
    //the sample isn't already pulled or discordant
    if (appearanceRule != FormLogic.MICROSCOPIC_APPEARANCE_OK) {
      //Rule 1 is that the sample will be marked pulled
      if (appearanceRule == FormLogic.MICROSCOPIC_APPEARANCE_RULE_1) {
        boolean sampleIsPulled = getValidator().isSamplePulled(sampleId);
        if (!sampleIsPulled) {
          BTXDetailsMarkSamplePulled btxDetailsPull = new BTXDetailsMarkSamplePulled();
          btxDetailsPull.setReason(LimsConstants.AUTO_PULL_REASON);
          btxDetailsPull.setSampleId(sampleId);
          btxDetailsPull.setLoggedInUserSecurityInfo(
            btxDetails.getLoggedInUserSecurityInfo(),
            true);
          btxDetailsPull.setBeginTimestamp(new java.sql.Timestamp(new java.util.Date().getTime()));
          btxDetailsPull.setTransactionType("lims_mark_sample_pulled");
          btxDetailsPull = (BTXDetailsMarkSamplePulled) Btx.perform(btxDetailsPull);
          BtxActionErrors errors = btxDetailsPull.getActionErrors();
          //if there were any problems pulling the sample, attach the errors to the
          //btxDetails object we're going to pass back.
          if (!errors.empty()) {
            Iterator iterator = errors.get();
            while (iterator.hasNext()) {
              btxDetails.addActionError((BtxActionError) iterator.next());
            }
          }
          BtxActionMessages messages = btxDetailsPull.getActionMessages();
          //if there were any problems pulling the sample, attach the messages to the
          //btxDetails object we're going to pass back.
          if (!messages.empty()) {
            Iterator iterator = messages.get();
            while (iterator.hasNext()) {
              btxDetails.addActionMessage((BtxActionMessage) iterator.next());
            }
          }
        }
      }
      //Rule 2 or 3 is that the sample will be marked discordant
      else if (
        appearanceRule == FormLogic.MICROSCOPIC_APPEARANCE_RULE_2
          || appearanceRule == FormLogic.MICROSCOPIC_APPEARANCE_RULE_3) {
        boolean sampleIsDiscordant = getValidator().isSampleDiscordant(sampleId);
        if (!sampleIsDiscordant) {
          BTXDetailsMarkSampleDiscordant btxDetailsDiscordant =
            new BTXDetailsMarkSampleDiscordant();
          if (appearanceRule == FormLogic.MICROSCOPIC_APPEARANCE_RULE_2) {
            btxDetailsDiscordant.setReason(LimsConstants.AUTO_DISCORDANT_REASON1);
          }
          else {
            btxDetailsDiscordant.setReason(LimsConstants.AUTO_DISCORDANT_REASON2);
          }
          btxDetailsDiscordant.setSampleId(sampleId);
          btxDetailsDiscordant.setLoggedInUserSecurityInfo(
            btxDetails.getLoggedInUserSecurityInfo(),
            true);
          btxDetailsDiscordant.setBeginTimestamp(
            new java.sql.Timestamp(new java.util.Date().getTime()));
          btxDetailsDiscordant.setTransactionType("lims_mark_sample_discordant");
          btxDetailsDiscordant = (BTXDetailsMarkSampleDiscordant) Btx.perform(btxDetailsDiscordant);
          BtxActionErrors errors = btxDetailsDiscordant.getActionErrors();
          //if there were any problems marking the sample discordant, attach the errors to the
          //btxDetails object we're going to pass back.
          if (!errors.empty()) {
            Iterator iterator = errors.get();
            while (iterator.hasNext()) {
              btxDetails.addActionError((BtxActionError) iterator.next());
            }
          }
          BtxActionMessages messages = btxDetailsDiscordant.getActionMessages();
          //if there were any problems pulling the sample, attach the messages to the
          //btxDetails object we're going to pass back.
          if (!messages.empty()) {
            Iterator iterator = messages.get();
            while (iterator.hasNext()) {
              btxDetails.addActionMessage((BtxActionMessage) iterator.next());
            }
          }
        }
      }
    }

    //return the btxDetails object passed in, marked as completed so logging takes place.
    return (BTXDetailsCreatePathologyEvaluation) btxDetails.setActionForwardTXCompleted(
      LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
  }

  //private method used to get an id for a new pathology evaluation
  private String getIdForNewEvaluation() throws Exception {
    String evalId = null;
    Connection connection = ApiFunctions.getDbConnection();
    CallableStatement cstmt = null;
    ResultSet results = null;
    try {
      cstmt = connection.prepareCall("begin LIMS_GET_EVALUATIONID(?,?); end;");
      cstmt.registerOutParameter(1, Types.VARCHAR);
      cstmt.registerOutParameter(2, Types.VARCHAR);
      cstmt.executeQuery();
      Object obj = cstmt.getObject(2);
      if (obj != null) {
        String emsg = obj.toString();
        //throw an exception
        throw new ApiException(
          "LIMS Stored Procedure LIMS_GET_EVALUATIONID failed at BtxPerformerPathologyOperation.getIdForNewEvaluation(): "
            + emsg);
      }
      else {
        evalId = cstmt.getString(1);
      }
    }
    finally {
      ApiFunctions.close(connection, cstmt, results);
    }
    return evalId;
  }

  //private method to handle any OCE requirements for a newly created pathology evaluation
  private BTXDetailsCreatePathologyEvaluation handleOCEDataForNewEvaluation(BTXDetailsCreatePathologyEvaluation btxDetails)
    throws Exception {

    String user = btxDetails.getUserId();
    String evaluationId = btxDetails.getPathologyEvaluationData().getEvaluationId();

    //If Diagnosis is OtherDiagnosis insert a row into ARD_OTHER_CODE_EDITS table to enable
    //Other Code Editor to fix it.
    String diagnosis = btxDetails.getPathologyEvaluationData().getDiagnosis();
    String otherDiagnosis = btxDetails.getPathologyEvaluationData().getDiagnosisOther();
    if ((FormLogic.OTHER_DX.equals(diagnosis))
      && (!ApiFunctions.isEmpty(ApiFunctions.safeTrim(otherDiagnosis)))) {

      List list = new ArrayList();
      list.add(evaluationId);
      OceUtil.createOce(OceUtil.LIMS_PE_OTHER_DIAGNOSIS, list, otherDiagnosis, user);
    }

    //If Tissue of Origin is OtherTissue insert a row into ARD_OTHER_CODE_EDITS table to enable
    //Other Code Editor to fix it.
    String tissueOrigin = btxDetails.getPathologyEvaluationData().getTissueOfOrigin();
    String otherTissueOrigin = btxDetails.getPathologyEvaluationData().getTissueOfOriginOther();
    if ((FormLogic.OTHER_TISSUE.equals(tissueOrigin))
      && (!ApiFunctions.isEmpty(ApiFunctions.safeTrim(otherTissueOrigin)))) {

      List list = new ArrayList();
      list.add(evaluationId);
      OceUtil.createOce(OceUtil.LIMS_PE_OTHER_TISSUE_ORIGIN, list, otherTissueOrigin, user);
    }

    //If Tissue of Finding is OtherTissue insert a row into ARD_OTHER_CODE_EDITS table to enable
    //Other Code Editor to fix it.
    String tissueFinding = btxDetails.getPathologyEvaluationData().getTissueOfFinding();
    String otherTissueFinding = btxDetails.getPathologyEvaluationData().getTissueOfFindingOther();
    if ((FormLogic.OTHER_TISSUE.equals(tissueFinding))
      && (!ApiFunctions.isEmpty(ApiFunctions.safeTrim(otherTissueFinding)))) {

      List list = new ArrayList();
      list.add(evaluationId);
      OceUtil.createOce(OceUtil.LIMS_PE_OTHER_TISSUE_FINDING, list, otherTissueFinding, user);
    }

    //return the btxDetails object, updated with any necessary action errors.
    return btxDetails;
  }

  //method to do the work of creating incidents
  private BTXDetailsCreateIncidents performCreateIncidents(BTXDetailsCreateIncidents btxDetails)
    throws Exception {

    //get the incident information, validate it, and call performCreateIncidentsSingle to
    //create the individual incidents
    boolean validationFailed = false;

    //get the incident report data
    IncidentReportData incidents = btxDetails.getIncidentReportData();

    //if the incident report data is null, or contains no incidents, return an error
    if ((incidents == null)
      || (incidents.getLineItems() == null)
      || (incidents.getLineItems().size() < 1)) {
      validationFailed = true;
      btxDetails.addActionError(new BtxActionError("lims.error.incidents.noIncidents"));
    }
    //otherwise walk through each incident and make sure that if the incident
    //is to be saved the data is valid.
    else {
      Iterator iterator = incidents.getLineItems().iterator();
      while (iterator.hasNext()) {
        IncidentReportLineItem incident = (IncidentReportLineItem) iterator.next();
        if (incident.isSave()) {
          StringBuffer missingData = new StringBuffer(100);
          boolean commaNeeded = false;
          //make sure an action has been specified
          String action = incident.getAction();
          if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(action))) {
            validationFailed = true;
            if (commaNeeded) {
              missingData.append(", ");
            }
            missingData.append("action");
            commaNeeded = true;
          }
          //if the action is "Other" make sure the other text has been specified
          String otherAction = incident.getActionOther();
          if (LimsConstants.INCIDENT_ACTION_OTHER.equals(action)
            && (ApiFunctions.isEmpty(ApiFunctions.safeTrim(otherAction)))) {
            validationFailed = true;
            if (commaNeeded) {
              missingData.append(", ");
            }
            missingData.append("other text for action");
            commaNeeded = true;
          }
          //make sure the sample's ASM position has been specified
          String asmPosition = incident.getAsmPosition();
          // If the sample is IMPORTED, ignore looking for an ASM position.
          if (ApiFunctions
            .isEmpty(
              ValidateIds.validateId(
                incident.getSampleId(),
                ValidateIds.TYPESET_SAMPLE_IMPORTED,
                false))) {
            if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(asmPosition))) {
              validationFailed = true;
              if (commaNeeded) {
                missingData.append(", ");
              }
              missingData.append("ASM position");
              commaNeeded = true;
            }
          }

          //make sure the case id has been specified
          String caseId = incident.getCaseId();
          if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(caseId))) {
            validationFailed = true;
            if (commaNeeded) {
              missingData.append(", ");
            }
            missingData.append("case id");
            commaNeeded = true;
          }
          //make sure the reason has been specified
          String reason = incident.getReason();
          if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(reason))) {
            validationFailed = true;
            if (commaNeeded) {
              missingData.append(", ");
            }
            missingData.append("reason");
            commaNeeded = true;
          }
          //make sure the sample id has been specified
          String sampleId = incident.getSampleId();
          if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(sampleId))) {
            validationFailed = true;
            if (commaNeeded) {
              missingData.append(", ");
            }
            missingData.append("sample id");
            commaNeeded = true;
          }
          //if this is a re-PV incident, make sure the data specific to that action is valid
          if (LimsConstants.INCIDENT_ACTION_REPV.equals(action)) {
            //make sure the pathologist has been specified
            String pathologist = incident.getPathologist();
            if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(pathologist))) {
              validationFailed = true;
              if (commaNeeded) {
                missingData.append(", ");
              }
              missingData.append("pathologist");
              commaNeeded = true;
            }
            //make sure the source of the re-pv request has been specified
            String requestorCode = incident.getRequestorCode();
            if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(requestorCode))) {
              validationFailed = true;
              if (commaNeeded) {
                missingData.append(", ");
              }
              missingData.append("source of Re-PV request");
              commaNeeded = true;
            }
            //make sure the slideId has been specified
            String slideId = incident.getSlideId();
            if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(slideId))) {
              validationFailed = true;
              if (commaNeeded) {
                missingData.append(", ");
              }
              missingData.append("slide id");
              commaNeeded = true;
            }
          }
          String theData = missingData.toString();
          if (theData.length() > 0) {
            if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(sampleId))) {
              sampleId = "{Unspecified}";
            }
            btxDetails.addActionError(
              new BtxActionError("lims.error.incidents.missingData", sampleId, theData));
          }
        }
      }
    }

    //if any validation errors occured, mark the transaction incomplete and return.
    if (validationFailed) {
      btxDetails.setActionForwardRetry();
      return btxDetails;
    }

    //otherwise, iterate over the individual incidents and store those that are
    //to be saved in the database.
    ListIterator iterator = incidents.getLineItems().listIterator();
    while (iterator.hasNext()) {
      IncidentReportLineItem incident = (IncidentReportLineItem) iterator.next();
      if (incident.isSave()) {
        BTXDetailsCreateIncidentsSingle singleBTX = new BTXDetailsCreateIncidentsSingle();
        singleBTX.setIncident(incident);
        singleBTX.setBeginTimestamp(btxDetails.getBeginTimestamp());
        singleBTX.setLoggedInUserSecurityInfo(btxDetails.getLoggedInUserSecurityInfo(), true);
        singleBTX =
          (BTXDetailsCreateIncidentsSingle) Btx.perform(singleBTX, "lims_create_incidents_single");
        //Update the current IncidentReportLineItem object with the one returned from the
        //"single" transaction inside the loop.
        iterator.set(singleBTX.getIncident());
      }
    }

    //this transaction is always successful, so go to the success page
    return (BTXDetailsCreateIncidents) btxDetails.setActionForwardTXCompleted(
      LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
  }

  //method to create a single new incident in the database.
  //NOTE:  this method shouldn't be called directly - only the performCreateIncidents method 
  //should trigger this method to be called.  We don't have a page to forward to for a 
  //single incident creation, but we do have a page for 1-n incidents and that's taken care of
  //in performCreateIncidents.  Even more important, all the validation for each incident is done
  //in performCreateIncidents, since we want all incidents to be created or none.
  private BTXDetailsCreateIncidentsSingle performCreateIncidentsSingle(BTXDetailsCreateIncidentsSingle btxDetails)
    throws Exception {

    //get the id for the new incident.
    IncidentReportLineItem incident = btxDetails.getIncident();
    incident.setIncidentId(getIdForNewIncident());

    //create the new incident
    IncidentAccessBean incidentBean = new IncidentAccessBean();
    incidentBean.setInit_action(incident.getAction());
    incidentBean.setInit_consentId(incident.getCaseId());
    incidentBean.setInit_createDate(new java.sql.Timestamp(new java.util.Date().getTime()));
    incidentBean.setInit_createUser(btxDetails.getLoggedInUserSecurityInfo().getUsername());
    incidentBean.setInit_incidentId(incident.getIncidentId());
    incidentBean.setInit_reason(incident.getReason());
    incidentBean.setInit_sampleId(incident.getSampleId());
    //only set the action "other" if the action is "other"
    if (LimsConstants.INCIDENT_ACTION_OTHER.equals(incident.getAction())) {
      incidentBean.setActionOther(incident.getActionOther());
    }
    else {
      incidentBean.setActionOther("");
    }
    incidentBean.setComments(incident.getGeneralComments());
    //only set the re-PV requestor code, pathologist, and slide id if the action 
    //is re-pv
    if (LimsConstants.INCIDENT_ACTION_REPV.equals(incident.getAction())) {
      incidentBean.setRePVRequestorCode(incident.getRequestorCode());
      incidentBean.setPathologist(incident.getPathologist());
      incidentBean.setSlideId(incident.getSlideId());
    }
    else {
      incidentBean.setRePVRequestorCode("");
      incidentBean.setPathologist("");
      incidentBean.setSlideId("");
    }
    incidentBean.commitCopyHelper();

    //return the btxDetails object passed in, marked as completed so logging takes place.
    return (BTXDetailsCreateIncidentsSingle) btxDetails.setActionForwardTXCompleted(
      LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
  }

  //private method used to get an id for a new incident
  private String getIdForNewIncident() throws Exception {
    String incidentId = null;
    Connection connection = ApiFunctions.getDbConnection();
    CallableStatement cstmt = null;
    ResultSet results = null;
    try {
      cstmt = connection.prepareCall("begin LIMS_GET_INCIDENTID(?,?); end;");
      cstmt.registerOutParameter(1, Types.VARCHAR);
      cstmt.registerOutParameter(2, Types.VARCHAR);
      cstmt.executeQuery();
      Object obj = cstmt.getObject(2);
      if (obj != null) {
        String emsg = obj.toString();
        //throw an exception
        throw new ApiException(
          "LIMS Stored Procedure LIMS_GET_INCIDENTID failed at BtxPerformerPathologyOperation.getIdForNewIncident(): "
            + emsg);
      }
      else {
        incidentId = cstmt.getString(1);
      }
    }
    finally {
      ApiFunctions.close(connection, cstmt, results);
    }
    return incidentId;
  }

  //method to do the work of marking a sample released
  private BTXDetailsMarkSampleReleased performMarkSampleReleased(BTXDetailsMarkSampleReleased btxDetails)
    throws Exception {

    //get the sample information, validate it, and mark the sample released
    boolean validationFailed = false;

    //if the specified sample doesn't exist, update the btxDetails object to contain 
    //an error.  Checking that the sample exists should already have been done by the 
    //UI, but do it here to be safe and to save the code below that relies on this 
    //being a valid sample from needing to check.
    String sampleId = btxDetails.getSampleId();
    boolean sampleExists = getValidator().doesSampleExist(sampleId);
    if (!sampleExists) {
      btxDetails.addActionError(
        new BtxActionError("lims.error.markSampleReleased.invalidSampleId", sampleId));
      validationFailed = true;
    }
    //If sample is not PVed return error. Samples without having reported 
    //evaluation cannot be released.
    PathologyEvaluationData reportedEval = getOperation().getReportedPathologyEvaluation(sampleId);
    if (reportedEval == null) {
      btxDetails.addActionError(
        new BtxActionError("lims.error.markSampleReleased.noReportedEvaluation", sampleId));
      validationFailed = true;
    }

    //if the sample is pulled return an error.  Pulled samples cannot be released.
    boolean sampleIsPulled = getValidator().isSamplePulled(sampleId);
    if (sampleIsPulled) {
      btxDetails.addActionError(
        new BtxActionError("lims.error.markSampleReleased.sampleIsPulled", sampleId));
      validationFailed = true;
    }

    //if the sample is already released don't do anything.  In this case we don't want 
    //to log anything
    boolean sampleIsReleased = getValidator().isSampleReleased(sampleId);
    if (sampleIsReleased) {
      btxDetails.addActionError(
        new BtxActionError("lims.error.sampleAlreadyInState", sampleId, "released"));
      validationFailed = true;
    }

    //if any validation errors occured, mark the transaction incomplete and return.
    if (validationFailed) {
      btxDetails.setActionForwardRetry();
      return btxDetails;
    }

    //now that we've validated that all required data has been provided, release the sample
    SampleKey key = new SampleKey(sampleId);
    SampleAccessBean sampleBean = new SampleAccessBean(key);
    sampleBean.setReleasedYN(LimsConstants.LIMS_DB_YES);
    sampleBean.commitCopyHelper();

    //update the btxDetails object with the reported evaluation id and the amount of time
    //that has passed between this transaction and when the reported evaluation was reported, 
    //so that data can be logged
    //NOTE: elapsed time is stored in minutes
    btxDetails.setReportedEvaluationId(reportedEval.getEvaluationId());
    Timestamp reportedEvalReportTime = reportedEval.getReportedDate();
    long milliseconds = btxDetails.getBeginTimestamp().getTime() - reportedEvalReportTime.getTime();
    //convert to minutes (1000 milliseconds per second * 60 seconds per minute = 60000)
    long elapsedTime = milliseconds / 60000;
    btxDetails.setElapsedTime(new Long(elapsedTime));

    //return the btxDetails object passed in, marked as completed so logging takes place.
    return (BTXDetailsMarkSampleReleased) btxDetails.setActionForwardTXCompleted(
      LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
  }

  //method to do the work of marking a sample unreleased
  private BTXDetailsMarkSampleUnreleased performMarkSampleUnreleased(BTXDetailsMarkSampleUnreleased btxDetails)
    throws Exception {

    //get the sample information, validate it, and mark the sample unreleased
    boolean validationFailed = false;

    //if the specified sample doesn't exist, update the btxDetails object to contain 
    //an error.  Checking that the sample exists should already have been done by the 
    //UI, but do it here to be safe and to save the code below that relies on this 
    //being a valid sample from needing to check.
    String sampleId = btxDetails.getSampleId();
    boolean sampleExists = getValidator().doesSampleExist(sampleId);
    if (!sampleExists) {
      btxDetails.addActionError(
        new BtxActionError("lims.error.markSampleUnreleased.invalidSampleId", sampleId));
      validationFailed = true;
    }

    //if the sample is already unreleased don't do anything.  In this case we don't want 
    //to log anything
    boolean sampleIsReleased = getValidator().isSampleReleased(sampleId);
    if (!sampleIsReleased) {
      btxDetails.addActionError(
        new BtxActionError("lims.error.sampleAlreadyInState", sampleId, "unreleased"));
      validationFailed = true;
    }

    //if any validation errors occured, mark the transaction incomplete and return.
    if (validationFailed) {
      btxDetails.setActionForwardRetry();
      return btxDetails;
    }

    //if the sample was QCPosted, we're going to mark it unQCPosted when we mark it unreleased and
    //log that fact, so set the flag on the btxDetails object so we can log things
    //correctly
    boolean sampleIsQCPosted = getValidator().isSampleQCPosted(sampleId);
    btxDetails.setSampleUnQCPosted(sampleIsQCPosted);

    //now that we've validated that all required data has been provided, unrelease the sample
    //and mark it unQCPosted
    SampleKey key = new SampleKey(sampleId);
    SampleAccessBean sampleBean = new SampleAccessBean(key);
    sampleBean.setReleasedYN(LimsConstants.LIMS_DB_NO);
    sampleBean.setQcpostedYN(LimsConstants.LIMS_DB_NO);
    sampleBean.commitCopyHelper();

    //return the btxDetails object passed in, marked as completed so logging takes place.
    return (BTXDetailsMarkSampleUnreleased) btxDetails.setActionForwardTXCompleted(
      LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
  }

  //method to do the work of marking a sample nondiscordant
  private BTXDetailsMarkSampleNondiscordant performMarkSampleNonDiscordant(BTXDetailsMarkSampleNondiscordant btxDetails)
    throws Exception {

    //get the sample and reason information, validate it, and mark the sample nondiscordant
    boolean validationFailed = false;

    //if the specified sample doesn't exist, update the btxDetails object to contain 
    //an error.  Checking that the sample exists should already have been done by the 
    //UI, but do it here to be safe and to save the code below that relies on this 
    //being a valid sample from needing to check.
    String sampleId = btxDetails.getSampleId();
    boolean sampleExists = getValidator().doesSampleExist(sampleId);
    if (!sampleExists) {
      btxDetails.addActionError(
        new BtxActionError("lims.error.markSampleNondiscordant.invalidSampleId", sampleId));
      validationFailed = true;
    }

    //if the sample is already nondiscordant don't do anything.  In this case we don't want 
    //to log anything
    boolean sampleIsDiscordant = getValidator().isSampleDiscordant(sampleId);
    if (!sampleIsDiscordant) {
      btxDetails.addActionError(
        new BtxActionError("lims.error.sampleAlreadyInState", sampleId, "non-discordant"));
      validationFailed = true;
    }

    //if the reason is not valid, update the btxDetails object to contain an error.
    String reason = ApiFunctions.safeTrim(btxDetails.getReason());
    if (ApiFunctions.isEmpty(reason)) {
      btxDetails.addActionError(
        new BtxActionError("lims.error.markSampleNondiscordant.invalidReason", sampleId));
      validationFailed = true;
    }

    //if any validation errors occured, mark the transaction incomplete and return.
    if (validationFailed) {
      btxDetails.setActionForwardRetry();
      return btxDetails;
    }

    //now that we've validated that all required data has been provided, mark the sample nondiscordant
    SampleKey key = new SampleKey(sampleId);
    SampleAccessBean sampleBean = new SampleAccessBean(key);
    sampleBean.setPullYN(LimsConstants.LIMS_DB_NO);
    sampleBean.setDiscordantYN(LimsConstants.LIMS_DB_NO);
    sampleBean.setPullReason(null);
    sampleBean.setPullDate(null);
    sampleBean.commitCopyHelper();

    //Mark status of rows in OCE for this sample evaluations as not fixed.
    //MR 4986
    String userId = btxDetails.getUserId();
    getOperation().updateLimsOCEDataStatus(sampleId, OceUtil.OCE_STATUS_NOTFIXED_IND, userId);

    //return the btxDetails object passed in, marked as completed so logging takes place.
    return (BTXDetailsMarkSampleNondiscordant) btxDetails.setActionForwardTXCompleted(
      LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
  }

  //method to do the work of marking a sample QCPosted
  private BTXDetailsMarkSampleQCPosted performMarkSampleQCPosted(BTXDetailsMarkSampleQCPosted btxDetails)
    throws Exception {

    //get the sample information, validate it, and mark the sample qcposted
    boolean validationFailed = false;

    //if the user does not have the privilege to perform this activity, return an error
    SecurityInfo security = btxDetails.getLoggedInUserSecurityInfo();
    if (!security.isHasPrivilege(SecurityInfo.PRIV_LIMS_QCPOST_UNPOST)) {
      btxDetails.addActionError(new BtxActionError("lims.error.userNotPrivileged", "QCPost"));
      validationFailed = true;
    }

    //if the specified sample doesn't exist, update the btxDetails object to contain 
    //an error.  Checking that the sample exists should already have been done by the 
    //UI, but do it here to be safe and to save the code below that relies on this 
    //being a valid sample from needing to check.
    String sampleId = btxDetails.getSampleId();
    boolean sampleExists = getValidator().doesSampleExist(sampleId);
    if (!sampleExists) {
      btxDetails.addActionError(
        new BtxActionError("lims.error.markSampleQCPosted.invalidSampleId", sampleId));
      validationFailed = true;
    }

    //if the sample is pulled return an error.  Pulled samples cannot be qcposted.
    boolean sampleIsPulled = getValidator().isSamplePulled(sampleId);
    if (sampleIsPulled) {
      btxDetails.addActionError(
        new BtxActionError("lims.error.markSampleQCPosted.sampleIsPulled", sampleId));
      validationFailed = true;
    }

    //if the sample is not released return an error.  A sample cannot be qcposted unless it's released
    boolean sampleIsReleased = getValidator().isSampleReleased(sampleId);
    if (!sampleIsReleased) {
      btxDetails.addActionError(
        new BtxActionError("lims.error.markSampleQCPosted.sampleIsUnreleased", sampleId));
      validationFailed = true;
    }

    //if the sample is already qcPosted don't do anything.  In this case we don't want 
    //to log anything
    boolean sampleIsQCPosted = getValidator().isSampleQCPosted(sampleId);
    if (sampleIsQCPosted) {
      btxDetails.addActionError(
        new BtxActionError("lims.error.sampleAlreadyInState", sampleId, "QCPosted"));
      validationFailed = true;
    }

    //if any validation errors occured, mark the transaction incomplete and return.
    if (validationFailed) {
      btxDetails.setActionForwardRetry();
      return btxDetails;
    }

    //now that we've validated that all required data has been provided, QCPost the sample
    SampleKey key = new SampleKey(sampleId);
    SampleAccessBean sampleBean = new SampleAccessBean(key);
    sampleBean.setQcpostedYN(LimsConstants.LIMS_DB_YES);
    sampleBean.commitCopyHelper();

    //return the btxDetails object passed in, marked as completed so logging takes place.
    return (BTXDetailsMarkSampleQCPosted) btxDetails.setActionForwardTXCompleted(
      LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
  }

  //method to do the work of marking a sample unQCPosted
  private BTXDetailsMarkSampleUnQCPosted performMarkSampleUnQCPosted(BTXDetailsMarkSampleUnQCPosted btxDetails)
    throws Exception {

    //get the sample information, validate it, and mark the sample unQCPosted
    boolean validationFailed = false;

    //if the user does not have the privilege to perform this activity, return an error
    SecurityInfo security = btxDetails.getLoggedInUserSecurityInfo();
    if (!security.isHasPrivilege(SecurityInfo.PRIV_LIMS_QCPOST_UNPOST)) {
      btxDetails.addActionError(new BtxActionError("lims.error.userNotPrivileged", "UnQCPost"));
      validationFailed = true;
    }

    //if the specified sample doesn't exist, update the btxDetails object to contain 
    //an error.  Checking that the sample exists should already have been done by the 
    //UI, but do it here to be safe and to save the code below that relies on this 
    //being a valid sample from needing to check.
    String sampleId = btxDetails.getSampleId();
    boolean sampleExists = getValidator().doesSampleExist(sampleId);
    if (!sampleExists) {
      btxDetails.addActionError(
        new BtxActionError("lims.error.markSampleUnQCPosted.invalidSampleId", sampleId));
      validationFailed = true;
    }

    //if the sample is already unQCPosted don't do anything.  In this case we don't want 
    //to log anything
    boolean isSampleQCPosted = getValidator().isSampleQCPosted(sampleId);
    if (!isSampleQCPosted) {
      btxDetails.addActionError(
        new BtxActionError("lims.error.sampleAlreadyInState", sampleId, "unQCPosted"));
      validationFailed = true;
    }

    //if any validation errors occured, mark the transaction incomplete and return.
    if (validationFailed) {
      btxDetails.setActionForwardRetry();
      return btxDetails;
    }

    //now that we've validated that all required data has been provided, unQCPost the sample
    SampleKey key = new SampleKey(sampleId);
    SampleAccessBean sampleBean = new SampleAccessBean(key);
    sampleBean.setQcpostedYN(LimsConstants.LIMS_DB_NO);
    sampleBean.commitCopyHelper();

    //return the btxDetails object passed in, marked as completed so logging takes place.
    return (BTXDetailsMarkSampleUnQCPosted) btxDetails.setActionForwardTXCompleted(
      LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
  }

  //Method to do work of retrieving all sample ids matching the query parameters for PathQC. 
  private BTXDetailsGetPathQCSampleSummary performGetPathQCSampleSummary(BTXDetailsGetPathQCSampleSummary btxDetails)
    throws Exception {

    Connection con = null;
    ResultSet results = null;

    //if the flag to do so is set, retrieve the totals for samples falling into the various
    //QC statuses   
    if (btxDetails.isRetrieveCounts()) {
      //build the query to determine the total number of samples in the database that
      //do not have a reported evaluation and are not pulled (unPVed count)
      StringBuffer sqlUnpvd = new StringBuffer(250);
      sqlUnpvd.append("SELECT a.acount - b.bcount FROM (SELECT COUNT(*) as acount from ");
      sqlUnpvd.append(DbConstants.TABLE_SAMPLE);
      sqlUnpvd.append(" ");
      sqlUnpvd.append(DbAliases.TABLE_SAMPLE);
      sqlUnpvd.append(" WHERE ");
      sqlUnpvd.append(DbAliases.TABLE_SAMPLE);
      sqlUnpvd.append(".");
      sqlUnpvd.append(DbConstants.SAMPLE_PULLED);
      sqlUnpvd.append(" = '");
      sqlUnpvd.append(LimsConstants.LIMS_DB_NO);
      sqlUnpvd.append("' AND ");
      sqlUnpvd.append(DbAliases.TABLE_SAMPLE);
      sqlUnpvd.append(".");
      sqlUnpvd.append(DbConstants.SAMPLE_QCSTATUS);
      sqlUnpvd.append(" IS NOT NULL) a, (SELECT COUNT(*) as bcount FROM ");
      sqlUnpvd.append(DbConstants.TABLE_SAMPLE);
      sqlUnpvd.append(" ");
      sqlUnpvd.append(DbAliases.TABLE_SAMPLE);
      sqlUnpvd.append(", ");
      sqlUnpvd.append(DbConstants.TABLE_LIMS_PE);
      sqlUnpvd.append(" ");
      sqlUnpvd.append(DbAliases.TABLE_LIMS_PE);
      sqlUnpvd.append(" WHERE ");
      sqlUnpvd.append(DbAliases.TABLE_SAMPLE);
      sqlUnpvd.append(".");
      sqlUnpvd.append(DbConstants.SAMPLE_ID);
      sqlUnpvd.append(" = ");
      sqlUnpvd.append(DbAliases.TABLE_LIMS_PE);
      sqlUnpvd.append(".");
      sqlUnpvd.append(DbConstants.LIMS_PE_SAMPLE_ID);
      sqlUnpvd.append(" AND ");
      sqlUnpvd.append(DbAliases.TABLE_SAMPLE);
      sqlUnpvd.append(".");
      sqlUnpvd.append(DbConstants.SAMPLE_PULLED);
      sqlUnpvd.append(" = '");
      sqlUnpvd.append(LimsConstants.LIMS_DB_NO);
      sqlUnpvd.append("' AND ");
      sqlUnpvd.append(DbAliases.TABLE_SAMPLE);
      sqlUnpvd.append(".");
      sqlUnpvd.append(DbConstants.SAMPLE_QCSTATUS);
      sqlUnpvd.append(" IS NOT NULL AND ");
      sqlUnpvd.append(DbAliases.TABLE_LIMS_PE);
      sqlUnpvd.append(".");
      sqlUnpvd.append(DbConstants.LIMS_PE_REPORTED);
      sqlUnpvd.append(" = '");
      sqlUnpvd.append(LimsConstants.LIMS_DB_YES);
      sqlUnpvd.append("') b");

      //build the query to determine the total number of samples in the database that
      //have a reported evaluation and are unreleased and released.  We also explicitly
      //exclude pulled samples, even though it's not necessary (since a pulled sample
      //will not have a reported evaluation), just to be safe. (PV'd count and released count)
      StringBuffer sqlPVedOrReleased = new StringBuffer(250);
      sqlPVedOrReleased.append("SELECT COUNT(1) FROM ");
      sqlPVedOrReleased.append(DbConstants.TABLE_SAMPLE);
      sqlPVedOrReleased.append(" ");
      sqlPVedOrReleased.append(DbAliases.TABLE_SAMPLE);
      sqlPVedOrReleased.append(", ");
      sqlPVedOrReleased.append(DbConstants.TABLE_LIMS_PE);
      sqlPVedOrReleased.append(" ");
      sqlPVedOrReleased.append(DbAliases.TABLE_LIMS_PE);
      sqlPVedOrReleased.append(" WHERE ");
      sqlPVedOrReleased.append(DbAliases.TABLE_SAMPLE);
      sqlPVedOrReleased.append(".");
      sqlPVedOrReleased.append(DbConstants.SAMPLE_RELEASED);
      sqlPVedOrReleased.append(" = ? AND ");
      sqlPVedOrReleased.append(DbAliases.TABLE_SAMPLE);
      sqlPVedOrReleased.append(".");
      sqlPVedOrReleased.append(DbConstants.SAMPLE_PULLED);
      sqlPVedOrReleased.append(" = '");
      sqlPVedOrReleased.append(LimsConstants.LIMS_DB_NO);
      sqlPVedOrReleased.append("' AND ");
      sqlPVedOrReleased.append(DbAliases.TABLE_SAMPLE);
      sqlPVedOrReleased.append(".");
      sqlPVedOrReleased.append(DbConstants.SAMPLE_QCPOSTED);
      sqlPVedOrReleased.append(" = '");
      sqlPVedOrReleased.append(LimsConstants.LIMS_DB_NO);
      sqlPVedOrReleased.append("' AND ");
      sqlPVedOrReleased.append(DbAliases.TABLE_SAMPLE);
      sqlPVedOrReleased.append(".");
      sqlPVedOrReleased.append(DbConstants.SAMPLE_QCSTATUS);
      sqlPVedOrReleased.append(" IS NOT NULL AND ");
      sqlPVedOrReleased.append(DbAliases.TABLE_SAMPLE);
      sqlPVedOrReleased.append(".");
      sqlPVedOrReleased.append(DbConstants.SAMPLE_ID);
      sqlPVedOrReleased.append(" = ");
      sqlPVedOrReleased.append(DbAliases.TABLE_LIMS_PE);
      sqlPVedOrReleased.append(".");
      sqlPVedOrReleased.append(DbConstants.LIMS_PE_SAMPLE_ID);
      sqlPVedOrReleased.append(" AND ");
      sqlPVedOrReleased.append(DbAliases.TABLE_LIMS_PE);
      sqlPVedOrReleased.append(".");
      sqlPVedOrReleased.append(DbConstants.LIMS_PE_REPORTED);
      sqlPVedOrReleased.append(" = '");
      sqlPVedOrReleased.append(LimsConstants.LIMS_DB_YES);
      sqlPVedOrReleased.append("'");

      //build the query to determine the total number of samples in the database that
      //are qcPosted.  We also explicitly exclude pulled samples, even though it's not 
      //necessary (since a pulled sample will not be qcPosted), just to be safe. (QCPosted count)
      StringBuffer sqlQCPosted = new StringBuffer(250);
      sqlQCPosted.append("SELECT COUNT(1) FROM ");
      sqlQCPosted.append(DbConstants.TABLE_SAMPLE);
      sqlQCPosted.append(" ");
      sqlQCPosted.append(DbAliases.TABLE_SAMPLE);
      sqlQCPosted.append(" WHERE ");
      sqlQCPosted.append(DbAliases.TABLE_SAMPLE);
      sqlQCPosted.append(".");
      sqlQCPosted.append(DbConstants.SAMPLE_PULLED);
      sqlQCPosted.append(" = '");
      sqlQCPosted.append(LimsConstants.LIMS_DB_NO);
      sqlQCPosted.append("' AND ");
      sqlQCPosted.append(DbAliases.TABLE_SAMPLE);
      sqlQCPosted.append(".");
      sqlQCPosted.append(DbConstants.SAMPLE_QCPOSTED);
      sqlQCPosted.append(" = '");
      sqlQCPosted.append(LimsConstants.LIMS_DB_YES);
      sqlQCPosted.append("' AND ");
      sqlQCPosted.append(DbAliases.TABLE_SAMPLE);
      sqlQCPosted.append(".");
      sqlQCPosted.append(DbConstants.SAMPLE_QCSTATUS);
      sqlQCPosted.append(" IS NOT NULL");

      //build the query to determine the total number of samples in the database that are pulled
      StringBuffer sqlPulled = new StringBuffer(250);
      sqlPulled.append("SELECT COUNT(1) FROM ");
      sqlPulled.append(DbConstants.TABLE_SAMPLE);
      sqlPulled.append(" ");
      sqlPulled.append(DbAliases.TABLE_SAMPLE);
      sqlPulled.append(" WHERE ");
      sqlPulled.append(DbAliases.TABLE_SAMPLE);
      sqlPulled.append(".");
      sqlPulled.append(DbConstants.SAMPLE_PULLED);
      sqlPulled.append(" = '");
      sqlPulled.append(LimsConstants.LIMS_DB_YES);
      sqlPulled.append("' AND ");
      sqlPulled.append(DbAliases.TABLE_SAMPLE);
      sqlPulled.append(".");
      sqlPulled.append(DbConstants.SAMPLE_QCSTATUS);
      sqlPulled.append(" IS NOT NULL");

      //create one sql statement to execute
      StringBuffer sqlAllCounts = new StringBuffer(500);
      sqlAllCounts.append("Select (");
      sqlAllCounts.append(sqlPVedOrReleased.toString());
      sqlAllCounts.append(") as count_pv, (");
      sqlAllCounts.append(sqlPVedOrReleased.toString());
      sqlAllCounts.append(") as count_released, (");
      sqlAllCounts.append(sqlPulled.toString());
      sqlAllCounts.append(") as count_pulled, (");
      sqlAllCounts.append(sqlQCPosted.toString());
      sqlAllCounts.append(") as count_qcposted, (");
      sqlAllCounts.append(sqlUnpvd.toString());
      sqlAllCounts.append(") as count_unpved from DUAL");

      //get the counts this transaction returns 
      con = ApiFunctions.getDbConnection();
      PreparedStatement pstmt = null;
      results = null;
      try {
        pstmt = con.prepareStatement(sqlAllCounts.toString());
        pstmt.setString(1, LimsConstants.LIMS_DB_NO);
        pstmt.setString(2, LimsConstants.LIMS_DB_YES);
        results = pstmt.executeQuery();
        if (results.next()) {
          btxDetails.setPvCount(results.getInt("count_pv"));
          btxDetails.setReleasedCount(results.getInt("count_released"));
          btxDetails.setPulledCount(results.getInt("count_pulled"));
          btxDetails.setUnPVedCount(results.getInt("count_unpved"));
          btxDetails.setQcPostedCount(results.getInt("count_qcposted"));
        }
      }
      finally {
        ApiFunctions.close(con, pstmt, results);
      }
    }

    //if the user has not specified to include unPVed samples, unreleased samples, 
    //released samples, or pulled samples, set the id list to an empty list and return
    if (!btxDetails.isIncludeUnPVdSamples()
      && !btxDetails.isIncludePulledSamples()
      && !btxDetails.isIncludeReleasedSamples()
      && !btxDetails.isIncludeUnreleasedSamples()
      && !btxDetails.isIncludeQCPostedSamples()) {
      btxDetails.setIds(new IdList(new ArrayList()));
      //return an error so the user gets an alert about no samples being found
      btxDetails.addActionError(new BtxActionError("lims.error.pathQc.noMatchingSamples"));
      return (BTXDetailsGetPathQCSampleSummary) btxDetails.setActionForwardTXCompleted(
        LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
    }

    //Regardless of what other criteria the user has specified, if they've specified
    //any id fields (case, sample, or slide) build the piece of the query to handle that
    IdList caseIds = btxDetails.getCaseIds();
    IdList sampleIds = btxDetails.getSampleIds();
    IdList slideIds = btxDetails.getSlideIds();
    boolean caseIdsSpecified = (caseIds != null && caseIds.size() > 0);
    boolean sampleIdsSpecified = (sampleIds != null && sampleIds.size() > 0);
    boolean slideIdsSpecified = (slideIds != null && slideIds.size() > 0);
    boolean idsSpecified = (caseIdsSpecified || sampleIdsSpecified || slideIdsSpecified);
    StringBuffer idClause = null;
    if (idsSpecified) {
      idClause = new StringBuffer(250);
      idClause.append("sample.sample_barcode_id in (");
      if (caseIdsSpecified) {
        idClause.append("select sample.sample_barcode_id from iltds_sample sample, iltds_asm asm ");
        idClause.append("where sample.asm_id = asm.asm_id and (");
        Iterator iter = caseIds.iterator();
        boolean isFirst = true;
        while (iter.hasNext()) {
          if (!isFirst) {
            idClause.append(" or ");
          }
          idClause.append("asm.consent_id = '");
          idClause.append((String) iter.next());
          idClause.append("'");
          isFirst = false;
        }
        idClause.append(")");
      }
      if (sampleIdsSpecified) {
        if (caseIdsSpecified) {
          idClause.append(" union ");
        }
        idClause.append("select sample.sample_barcode_id from iltds_sample sample where ");
        Iterator iter = sampleIds.iterator();
        boolean isFirst = true;
        while (iter.hasNext()) {
          if (!isFirst) {
            idClause.append(" or ");
          }
          idClause.append("sample.sample_barcode_id = '");
          idClause.append((String) iter.next());
          idClause.append("'");
          isFirst = false;
        }
      }
      if (slideIdsSpecified) {
        if (caseIdsSpecified || sampleIdsSpecified) {
          idClause.append(" union ");
        }
        idClause.append(
          "select sample.sample_barcode_id from iltds_sample sample, lims_slide slide ");
        idClause.append("where sample.sample_barcode_id = slide.sample_barcode_id and (");
        Iterator iter = slideIds.iterator();
        boolean isFirst = true;
        while (iter.hasNext()) {
          if (!isFirst) {
            idClause.append(" or ");
          }
          idClause.append("slide.slide_id = '");
          idClause.append((String) iter.next());
          idClause.append("'");
          isFirst = false;
        }
        idClause.append(")");
      }
      idClause.append(")");
    }

    //now build a sub query which we'll use to get the sample ids matching the user specified parameters,
    //without worrying about sort order. We'll need to build a query for pulled samples if they've been
    //requested, a query for PV'd samples (released and/or unreleased) if they've been requested, and a
    //query for unPVed samples if they've been requested.  If multiple queries are required they'll
    //be unioned at the end.
    //if the user has included pulled samples, build the query to get those samples
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    IdList logicalRepositories = btxDetails.getLogicalRepositories();
    StringBuffer pullQuery = null;
    if (btxDetails.isIncludePulledSamples()) {
      pullQuery = new StringBuffer(250);
      pullQuery.append("select sample.sample_barcode_id ");
      pullQuery.append("from iltds_sample sample ");
      //if the user is not allowed to see all logical repositories or they've chosen to restrict
      //samples to specific repositories, then limit the samples to
      //only those belonging to logical repositories to which the user has access and/or the 
      //repositories they've specified
      if (!securityInfo.isHasPrivilege(SecurityInfo.PRIV_VIEW_ALL_LOGICAL_REPOS)
        || (logicalRepositories != null && logicalRepositories.size() > 0)) {
        pullQuery.append(", ard_logical_repos_item alri ");
      }
      pullQuery.append("where sample.pull_yn = 'Y'");
      //if the user is not allowed to see all logical repositories, then limit the samples to
      //only those belonging to logical repositories to which the user has access.
      if (!securityInfo.isHasPrivilege(SecurityInfo.PRIV_VIEW_ALL_LOGICAL_REPOS)) {
        pullQuery.append(" and sample.sample_barcode_id = alri.item_id ");
        pullQuery.append(" and alri.repository_id in (");
        pullQuery.append(
          LogicalRepositoryUtils.getLogicalRepositoryIdsForSql(
            securityInfo.getLogicalRepositories()));
        pullQuery.append(") ");
      }
      //if the user has chosen to restrict samples to specific logical repositories, include 
      //that restriction
      if (logicalRepositories != null && logicalRepositories.size() > 0) {
        pullQuery.append(" and sample.sample_barcode_id = alri.item_id ");
        pullQuery.append(" and alri.repository_id in (");
        Iterator iter = logicalRepositories.iterator();
        boolean addComma = false;
        while (iter.hasNext()) {
          if (addComma) {
            pullQuery.append(", ");
          }
          addComma = true;
          pullQuery.append("'");
          pullQuery.append((String) iter.next());
          pullQuery.append("'");
        }
        pullQuery.append(")");
      }

      //include the restriction on the ids we built above, if any
      if (idClause != null) {
        pullQuery.append(" and ");
        pullQuery.append(idClause.toString());
      }
      //include restriction on QC_STATUS
      //if QCAwaiting was selected, return samples with a QC_STATUS of awaiting or verified
      //if QCInProcess was selected, return samples with a QC_STATUS of inprocess
      if (!btxDetails.isQcStatusAwaiting() && btxDetails.isQcStatusInProcess()) {
        pullQuery.append(" and sample.qc_status = '");
        pullQuery.append(FormLogic.SMPL_QCINPROCESS);
        pullQuery.append("'");
      }
      else if (btxDetails.isQcStatusAwaiting() && !btxDetails.isQcStatusInProcess()) {
        pullQuery.append(" and (sample.qc_status = '");
        pullQuery.append(FormLogic.SMPL_QCAWAITING);
        pullQuery.append("' or sample.qc_status = '");
        pullQuery.append(FormLogic.SMPL_QCVERIFIED);
        pullQuery.append("')");
      }
      //otherwise return all samples that don't have a null QC_STATUS
      else {
        pullQuery.append(" and sample.qc_status is not null");
      }
    }
    //if the user has included pved samples (ie. released and/or unreleased), build the query to get those samples
    StringBuffer pVedQuery = null;
    if (btxDetails.isIncludeReleasedSamples() || btxDetails.isIncludeUnreleasedSamples()) {
      pVedQuery = new StringBuffer(250);
      pVedQuery.append("select sample.sample_barcode_id ");
      pVedQuery.append("from iltds_sample sample, iltds_asm asm, lims_pathology_evaluation eval ");
      //if the user is not allowed to see all logical repositories or they've chosen to restrict
      //samples to specific repositories, then limit the samples to
      //only those belonging to logical repositories to which the user has access and/or the 
      //repositories they've specified
      if (!securityInfo.isHasPrivilege(SecurityInfo.PRIV_VIEW_ALL_LOGICAL_REPOS)
        || (logicalRepositories != null && logicalRepositories.size() > 0)) {
        pVedQuery.append(", ard_logical_repos_item alri ");
      }
      pVedQuery.append(
        "where sample.pull_yn = 'N' and sample.qcposted_yn = 'N' and sample.asm_id = asm.asm_id ");
      pVedQuery.append("and sample.sample_barcode_id = eval.sample_barcode_id ");
      pVedQuery.append("and eval.reported_yn = 'Y' ");
      //if the user is not allowed to see all logical repositories, then limit the samples to
      //only those belonging to logical repositories to which the user has access.
      if (!securityInfo.isHasPrivilege(SecurityInfo.PRIV_VIEW_ALL_LOGICAL_REPOS)) {
        pVedQuery.append(" and sample.sample_barcode_id = alri.item_id ");
        pVedQuery.append(" and alri.repository_id in (");
        pVedQuery.append(
          LogicalRepositoryUtils.getLogicalRepositoryIdsForSql(
            securityInfo.getLogicalRepositories()));
        pVedQuery.append(") ");
      }
      //if the user has chosen to restrict samples to specific logical repositories, include 
      //that restriction
      if (logicalRepositories != null && logicalRepositories.size() > 0) {
        pVedQuery.append(" and sample.sample_barcode_id = alri.item_id ");
        pVedQuery.append(" and alri.repository_id in (");
        Iterator iter = logicalRepositories.iterator();
        boolean addComma = false;
        while (iter.hasNext()) {
          if (addComma) {
            pVedQuery.append(", ");
          }
          addComma = true;
          pVedQuery.append("'");
          pVedQuery.append((String) iter.next());
          pVedQuery.append("'");
        }
        pVedQuery.append(")");
      }
      //if we're including both released and unreleased samples, no need to add anything
      //about the sample released value.  If it's only one or the other however, we do
      if (btxDetails.isIncludeReleasedSamples() && !btxDetails.isIncludeUnreleasedSamples()) {
        pVedQuery.append(" and sample.released_yn = 'Y' ");
      }
      else if (!btxDetails.isIncludeReleasedSamples() && btxDetails.isIncludeUnreleasedSamples()) {
        pVedQuery.append(" and sample.released_yn = 'N' ");
      }
      //if the user has restricted the reported evaluation only come from certain pathologists,
      //handle that here
      IdList pathologists = btxDetails.getPathologists();
      if (pathologists != null && pathologists.size() > 0) {
        pVedQuery.append(" and (");
        boolean addOr = false;
        Iterator iter = pathologists.iterator();
        while (iter.hasNext()) {
          if (addOr) {
            pVedQuery.append(" or ");
          }
          pVedQuery.append("eval.create_user = '");
          pVedQuery.append((String) iter.next());
          pVedQuery.append("'");
          addOr = true;
        }
        pVedQuery.append(")");
      }
      //if the user specified a restriction on the reported date of the evaluation, handle that here.  
      //Handle just a start date, just an end date, or both.
      Date startDate = btxDetails.getReportedDateStart();
      Date endDate = btxDetails.getReportedDateEnd();
      if (startDate != null || endDate != null) {
        DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
        if (startDate != null) {
          pVedQuery.append(" and eval.reported_date >= to_date('");
          pVedQuery.append(dateFormatter.format(startDate));
          pVedQuery.append(" 12:00:01 AM', 'mm/dd/rr hh:mi:ss AM')");
        }
        if (endDate != null) {
          pVedQuery.append(" and eval.reported_date <= to_date('");
          pVedQuery.append(dateFormatter.format(endDate));
          pVedQuery.append(" 11:59:59 PM', 'mm/dd/rr hh:mi:ss PM')");
        }
      }
      //include the restriction on the ids we built above, if any
      if (idClause != null) {
        pVedQuery.append(" and ");
        pVedQuery.append(idClause.toString());
      }
      //include restriction on QC_STATUS
      //if QCAwaiting was selected, return samples with a QC_STATUS of awaiting or verified
      //if QCInProcess was selected, return samples with a QC_STATUS of inprocess
      if (!btxDetails.isQcStatusAwaiting() && btxDetails.isQcStatusInProcess()) {
        pVedQuery.append(" and sample.qc_status = '");
        pVedQuery.append(FormLogic.SMPL_QCINPROCESS);
        pVedQuery.append("'");
      }
      else if (btxDetails.isQcStatusAwaiting() && !btxDetails.isQcStatusInProcess()) {
        pVedQuery.append(" and (sample.qc_status = '");
        pVedQuery.append(FormLogic.SMPL_QCAWAITING);
        pVedQuery.append("' or sample.qc_status = '");
        pVedQuery.append(FormLogic.SMPL_QCVERIFIED);
        pVedQuery.append("')");
      }
      //otherwise return all samples that don't have a null QC_STATUS
      else {
        pVedQuery.append(" and sample.qc_status is not null");
      }
    }

    //if the user has included unPVed samples, build the query to get those samples
    StringBuffer unPVedQuery = null;
    if (btxDetails.isIncludeUnPVdSamples()) {
      unPVedQuery = new StringBuffer(250);
      unPVedQuery.append("select sample.sample_barcode_id ");
      unPVedQuery.append("from iltds_sample sample ");
      //if the user is not allowed to see all logical repositories or they've chosen to restrict
      //samples to specific repositories, then limit the samples to
      //only those belonging to logical repositories to which the user has access and/or the 
      //repositories they've specified
      if (!securityInfo.isHasPrivilege(SecurityInfo.PRIV_VIEW_ALL_LOGICAL_REPOS)
        || (logicalRepositories != null && logicalRepositories.size() > 0)) {
        unPVedQuery.append(", ard_logical_repos_item alri ");
      }
      unPVedQuery.append("where sample.pull_yn = 'N' ");
      unPVedQuery.append("and not exists (select 1 from lims_pathology_evaluation path ");
      unPVedQuery.append("where sample.sample_barcode_id = path.sample_barcode_id ");
      unPVedQuery.append("and path.reported_yn = 'Y')");
      //if the user is not allowed to see all logical repositories, then limit the samples to
      //only those belonging to logical repositories to which the user has access.
      if (!securityInfo.isHasPrivilege(SecurityInfo.PRIV_VIEW_ALL_LOGICAL_REPOS)) {
        unPVedQuery.append(" and sample.sample_barcode_id = alri.item_id ");
        unPVedQuery.append(" and alri.repository_id in (");
        unPVedQuery.append(
          LogicalRepositoryUtils.getLogicalRepositoryIdsForSql(
            securityInfo.getLogicalRepositories()));
        unPVedQuery.append(") ");
      }
      //if the user has chosen to restrict samples to specific logical repositories, include 
      //that restriction
      if (logicalRepositories != null && logicalRepositories.size() > 0) {
        unPVedQuery.append(" and sample.sample_barcode_id = alri.item_id ");
        unPVedQuery.append(" and alri.repository_id in (");
        Iterator iter = logicalRepositories.iterator();
        boolean addComma = false;
        while (iter.hasNext()) {
          if (addComma) {
            unPVedQuery.append(", ");
          }
          addComma = true;
          unPVedQuery.append("'");
          unPVedQuery.append((String) iter.next());
          unPVedQuery.append("'");
        }
        unPVedQuery.append(")");
      }
      //include the restriction on the ids we built above, if any
      if (idClause != null) {
        unPVedQuery.append(" and ");
        unPVedQuery.append(idClause.toString());
      }
      //include restriction on QC_STATUS
      //if QCAwaiting was selected, return samples with a QC_STATUS of awaiting or verified
      //if QCInProcess was selected, return samples with a QC_STATUS of inprocess
      if (!btxDetails.isQcStatusAwaiting() && btxDetails.isQcStatusInProcess()) {
        unPVedQuery.append(" and sample.qc_status = '");
        unPVedQuery.append(FormLogic.SMPL_QCINPROCESS);
        unPVedQuery.append("'");
      }
      else if (btxDetails.isQcStatusAwaiting() && !btxDetails.isQcStatusInProcess()) {
        unPVedQuery.append(" and (sample.qc_status = '");
        unPVedQuery.append(FormLogic.SMPL_QCAWAITING);
        unPVedQuery.append("' or sample.qc_status = '");
        unPVedQuery.append(FormLogic.SMPL_QCVERIFIED);
        unPVedQuery.append("')");
      }
      //otherwise return all samples that don't have a null QC_STATUS
      else {
        unPVedQuery.append(" and sample.qc_status is not null");
      }
    }

    //if the user has included qcposted samples, build the query to get those samples
    StringBuffer qcPostedQuery = null;
    if (btxDetails.isIncludeQCPostedSamples()) {
      qcPostedQuery = new StringBuffer(250);
      qcPostedQuery.append("select sample.sample_barcode_id ");
      qcPostedQuery.append(
        "from iltds_sample sample, iltds_asm asm, lims_pathology_evaluation eval ");
      //if the user is not allowed to see all logical repositories or they've chosen to restrict
      //samples to specific repositories, then limit the samples to
      //only those belonging to logical repositories to which the user has access and/or the 
      //repositories they've specified
      if (!securityInfo.isHasPrivilege(SecurityInfo.PRIV_VIEW_ALL_LOGICAL_REPOS)
        || (logicalRepositories != null && logicalRepositories.size() > 0)) {
        qcPostedQuery.append(", ard_logical_repos_item alri ");
      }
      qcPostedQuery.append(
        "where sample.pull_yn = 'N' and sample.released_yn = 'Y' and sample.qcposted_yn = 'Y' ");
      qcPostedQuery.append(
        "and sample.asm_id = asm.asm_id and sample.sample_barcode_id = eval.sample_barcode_id ");
      qcPostedQuery.append("and eval.reported_yn = 'Y' ");
      //if the user is not allowed to see all logical repositories, then limit the samples to
      //only those belonging to logical repositories to which the user has access.
      if (!securityInfo.isHasPrivilege(SecurityInfo.PRIV_VIEW_ALL_LOGICAL_REPOS)) {
        qcPostedQuery.append(" and sample.sample_barcode_id = alri.item_id ");
        qcPostedQuery.append(" and alri.repository_id in (");
        qcPostedQuery.append(
          LogicalRepositoryUtils.getLogicalRepositoryIdsForSql(
            securityInfo.getLogicalRepositories()));
        qcPostedQuery.append(") ");
      }
      //if the user has chosen to restrict samples to specific logical repositories, include 
      //that restriction
      if (logicalRepositories != null && logicalRepositories.size() > 0) {
        qcPostedQuery.append(" and sample.sample_barcode_id = alri.item_id ");
        qcPostedQuery.append(" and alri.repository_id in (");
        Iterator iter = logicalRepositories.iterator();
        boolean addComma = false;
        while (iter.hasNext()) {
          if (addComma) {
            qcPostedQuery.append(", ");
          }
          addComma = true;
          qcPostedQuery.append("'");
          qcPostedQuery.append((String) iter.next());
          qcPostedQuery.append("'");
        }
        qcPostedQuery.append(")");
      }
      //if the user has restricted the reported evaluation only come from certain pathologists,
      //handle that here
      IdList pathologists = btxDetails.getPathologists();
      if (pathologists != null && pathologists.size() > 0) {
        qcPostedQuery.append(" and (");
        boolean addOr = false;
        Iterator iter = pathologists.iterator();
        while (iter.hasNext()) {
          if (addOr) {
            qcPostedQuery.append(" or ");
          }
          qcPostedQuery.append("eval.create_user = '");
          qcPostedQuery.append((String) iter.next());
          qcPostedQuery.append("'");
          addOr = true;
        }
        qcPostedQuery.append(")");
      }
      //if the user specified a restriction on the reported date of the evaluation, handle that here.  
      //Handle just a start date, just an end date, or both.
      Date startDate = btxDetails.getReportedDateStart();
      Date endDate = btxDetails.getReportedDateEnd();
      if (startDate != null || endDate != null) {
        DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
        if (startDate != null) {
          qcPostedQuery.append(" and eval.reported_date >= to_date('");
          qcPostedQuery.append(dateFormatter.format(startDate));
          qcPostedQuery.append(" 12:00:01 AM', 'mm/dd/rr hh:mi:ss AM')");
        }
        if (endDate != null) {
          qcPostedQuery.append(" and eval.reported_date <= to_date('");
          qcPostedQuery.append(dateFormatter.format(endDate));
          qcPostedQuery.append(" 11:59:59 PM', 'mm/dd/rr hh:mi:ss PM')");
        }
      }
      //include the restriction on the ids we built above, if any
      if (idClause != null) {
        qcPostedQuery.append(" and ");
        qcPostedQuery.append(idClause.toString());
      }
      //include restriction on QC_STATUS
      //if QCAwaiting was selected, return samples with a QC_STATUS of awaiting or verified
      //if QCInProcess was selected, return samples with a QC_STATUS of inprocess
      if (!btxDetails.isQcStatusAwaiting() && btxDetails.isQcStatusInProcess()) {
        qcPostedQuery.append(" and sample.qc_status = '");
        qcPostedQuery.append(FormLogic.SMPL_QCINPROCESS);
        qcPostedQuery.append("'");
      }
      else if (btxDetails.isQcStatusAwaiting() && !btxDetails.isQcStatusInProcess()) {
        qcPostedQuery.append(" and (sample.qc_status = '");
        qcPostedQuery.append(FormLogic.SMPL_QCAWAITING);
        qcPostedQuery.append("' or sample.qc_status = '");
        qcPostedQuery.append(FormLogic.SMPL_QCVERIFIED);
        qcPostedQuery.append("')");
      }
      //otherwise return all samples that don't have a null QC_STATUS
      else {
        qcPostedQuery.append(" and sample.qc_status is not null");
      }
    }

    //determine the overall subquery
    StringBuffer sampleIdSubquery = new StringBuffer(500);
    boolean subqueryUsed = false;
    if (pullQuery != null) {
      if (subqueryUsed) {
        sampleIdSubquery.append(" union ");
      }
      sampleIdSubquery.append(pullQuery.toString());
      subqueryUsed = true;
    }
    if (pVedQuery != null) {
      if (subqueryUsed) {
        sampleIdSubquery.append(" union ");
      }
      sampleIdSubquery.append(pVedQuery.toString());
      subqueryUsed = true;
    }
    if (unPVedQuery != null) {
      if (subqueryUsed) {
        sampleIdSubquery.append(" union ");
      }
      sampleIdSubquery.append(unPVedQuery.toString());
      subqueryUsed = true;
    }
    if (qcPostedQuery != null) {
      if (subqueryUsed) {
        sampleIdSubquery.append(" union ");
      }
      sampleIdSubquery.append(qcPostedQuery.toString());
      subqueryUsed = true;
    }

    //Now that we have the query that returns all of the appropriate sample ids, build the
    //query that will take the sort order into account
    StringBuffer completeQuery = new StringBuffer(500);
    completeQuery.append("select sample.sample_barcode_id, asm.consent_id, sample.asm_position ");
    //Get the sort order specified by the user.  If it's null, default to the reported 
    //evaluation's report date.
    String sortColumn = btxDetails.getSortColumn();
    boolean sortFromEval = false;
    if (sortColumn == null) {
      sortColumn = LimsConstants.PATH_QC_SORT_REPORTEDDATE;
    }
    //if the user has selected to sort by a column provided by the reported evaluation, add
    //it to the select clause and set the flag to indicate that
    if (LimsConstants.PATH_QC_SORT_CREATIONDATE.equals(sortColumn)) {
      completeQuery.append(", eval.create_date ");
      sortFromEval = true;
    }
    else if (LimsConstants.PATH_QC_SORT_PATHOLOGIST.equals(sortColumn)) {
      completeQuery.append(", eval.create_user ");
      sortFromEval = true;
    }
    else if (LimsConstants.PATH_QC_SORT_REPORTEDDATE.equals(sortColumn)) {
      completeQuery.append(", eval.reported_date ");
      sortFromEval = true;
    }
    completeQuery.append("from iltds_sample sample, iltds_asm asm ");
    //if the sort column is from the evaluation table we need to add it to the from clause
    if (sortFromEval) {
      completeQuery.append(", lims_pathology_evaluation eval ");
    }
    completeQuery.append("where sample.asm_id = asm.asm_id ");
    //if the sort column is from the evaluation table we need to add some extra
    //items to the where clause
    if (sortFromEval) {
      completeQuery.append("and sample.sample_barcode_id = eval.sample_barcode_id (+) ");
      completeQuery.append("and eval.reported_yn (+) = 'Y' ");
    }
    //specify what sample ids to use by including the sampleId subquery we composed above
    completeQuery.append("and sample.sample_barcode_id in (");
    completeQuery.append(sampleIdSubquery.toString());
    completeQuery.append(") ");
    //now build the order by clause
    //get the sort order the user has specified.  If it's null or not one of the
    //recognized values, default to ascending
    String sortOrder = btxDetails.getSortOrder();
    if (sortOrder == null
      || (!LimsConstants.SORT_ORDER_ASC.equals(sortOrder)
        && !LimsConstants.SORT_ORDER_DESC.equals(sortOrder)))
      sortOrder = LimsConstants.SORT_ORDER_ASC;
    completeQuery.append("order by ");
    if (LimsConstants.PATH_QC_SORT_QCSTATUS.equals(sortColumn)) {
      /*Note - this doesn't work 100%.  Pulled samples fall smack in the middle
       * of the sort order, but there's no way to get them there so for now we
       * always put them at the bottom of the list.
       * QC Status is determined as follows:
       * -----------------------------------
       * Not PVed - sample.pull_yn = n, sample.released_yn = n, sample.qcverified = n, qc_posted = n
       * PVed - sample.pull_yn = n, sample.released_yn = n, sample.qcverified = y, qc_posted = n
       * QCPosted - sample.pull_yn = n, sample.released_yn = y, sample.qcverified = y, qc_posted = y
       * Released - sample.pull_yn = n, sample.released_yn = y, sample.qcverified = y, qc_posted = n
       * Pulled - sample.pull_yn = y
       * so to sort on the QCStatus column:
       * first sort on pull_yn ascending (so they're always at the bottom),
       * followed by sample.qcverified in sort order,
       * followed by released_yn in sort order,
       * followed by qcposted_yn in opposite of sort order
       */
      completeQuery.append("sample.pull_yn ");
      completeQuery.append(LimsConstants.SORT_ORDER_ASC);
      completeQuery.append(", sample.qc_verified ");
      completeQuery.append(sortOrder);
      completeQuery.append(", sample.released_yn ");
      completeQuery.append(sortOrder);
      completeQuery.append(", sample.qcposted_yn ");
      if (LimsConstants.SORT_ORDER_ASC.equalsIgnoreCase(sortOrder)) {
        completeQuery.append(LimsConstants.SORT_ORDER_DESC);
      }
      else {
        completeQuery.append(LimsConstants.SORT_ORDER_ASC);
      }
    }
    else if (LimsConstants.PATH_QC_SORT_CASEID.equals(sortColumn)) {
      /** Nothing to do here, as we always append a sort by case id
       * to the end of whatever the user specified */
    }
    else if (LimsConstants.PATH_QC_SORT_SAMPLEID.equals(sortColumn)) {
      completeQuery.append("sample.sample_barcode_id ");
      completeQuery.append(sortOrder);
    }
    else if (LimsConstants.PATH_QC_SORT_PATHOLOGIST.equals(sortColumn)) {
      /** this column may not apply to all samples.  Put any samples
       * that don't have this column at the end, and they will be
       * sorted by case id */
      completeQuery.append("eval.create_user ");
      completeQuery.append(sortOrder);
      completeQuery.append(" nulls last");
    }
    else if (LimsConstants.PATH_QC_SORT_REPORTEDDATE.equals(sortColumn)) {
      /** this column may not apply to all samples.  Put any samples
       * that don't have this column at the end, and they will be
       * sorted by case id */
      completeQuery.append("eval.reported_date ");
      completeQuery.append(sortOrder);
      completeQuery.append(" nulls last");
    }
    else if (LimsConstants.PATH_QC_SORT_PULLDATE.equals(sortColumn)) {
      /** this column may not apply to all samples.  Put any samples
       * that don't have this column at the end, and they will be
       * sorted by case id */
      completeQuery.append("sample.pull_date ");
      completeQuery.append(sortOrder);
      completeQuery.append(" nulls last");
    }
    else if (LimsConstants.PATH_QC_SORT_CREATIONDATE.equals(sortColumn)) {
      /** this column may not apply to all samples.  Put any samples
       * that don't have this column at the end, and they will be
       * sorted by case id */
      completeQuery.append("eval.create_date ");
      completeQuery.append(sortOrder);
      completeQuery.append(" nulls last");
    }
    //no matter what sort column was specified, sort within that column by
    //case id/asm position.  This is because the user specified sort column
    //may not be applicable to all samples (i.e. pull_date for unpulled samples,
    //or reported_date for pulled samples).  Howie wants us to sort those samples
    //by case id/asm position
    //If we're sorting by something other than case id, add a comma
    if (!LimsConstants.PATH_QC_SORT_CASEID.equals(sortColumn)) {
      completeQuery.append(", ");
    }
    completeQuery.append("asm.consent_id ");
    completeQuery.append(sortOrder);
    completeQuery.append(", sample.asm_position ");
    completeQuery.append(sortOrder);

    String sql = completeQuery.toString();

    //execute the query
    con = ApiFunctions.getDbConnection();
    Statement stmt = null;
    results = null;
    ArrayList ids = new ArrayList();
    try {
      stmt = con.createStatement();
      results = stmt.executeQuery(sql);
      while (results.next()) {
        ids.add(results.getString("sample_barcode_id"));
      }
      btxDetails.setIds(new IdList(ids));
      //if no samples were found, return an error so the user gets an alert about no 
      //samples being found
      if (btxDetails.getIds().size() <= 0) {
        btxDetails.addActionError(new BtxActionError("lims.error.pathQc.noMatchingSamples"));
      }
    }
    finally {
      ApiFunctions.close(con, stmt, results);
    }

    //return the btxDetails object passed in, marked as completed.
    return (BTXDetailsGetPathQCSampleSummary) btxDetails.setActionForwardTXCompleted(
      LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
  }

  //Method to do work of retrieving the information for a chunk of sample ids matching the query parameters for PathQC. 
  private BTXDetailsGetPathQCSampleDetails performGetPathQCSampleDetails(BTXDetailsGetPathQCSampleDetails btxDetails)
    throws Exception {
    IdList sampleIds = btxDetails.getSampleIds();

    //make sure the list we were sent has at least 1 id in it
    if (sampleIds.size() <= 0) {
      BtxActionError btxError =
        new BtxActionError("lims.error.getPathQCSampleDetails.invalidSampleList");
      return (BTXDetailsGetPathQCSampleDetails) btxDetails.setActionForwardRetry(btxError);
    }

    ProductDetailQueryBuilder qb =
      new ProductDetailQueryBuilder(btxDetails.getLoggedInUserSecurityInfo());
    //qc status column
    qb.addColumnPullYN();
    qb.addColumnReleasedYN();
    qb.addColumnQCPostedYN();
    qb.addColumnPvCreateUser();
    qb.addColumnPvSlideId();
    qb.addColumnPvCreateDate();
    qb.addColumnPvReportedDate();
    qb.addColumnPullDate();
    qb.addColumnPulledReason();
    qb.addColumnPvId();
    //case id/asm position/sample id column
    qb.addColumnAsmConsentId();
    qb.addColumnAsmPosition();
    qb.addColumnSampleId();
    qb.addColumnDdcDiagnosis();
    qb.addColumnDdcDiagnosisOther();
    //reports column (any images for the sample will be on the sample data bean)
    qb.addColumnPathId();
    qb.addColumnDonorId();
    qb.addColumnSampleImagesId();
    qb.addColumnSampleImagesFilename();
    qb.addColumnSampleImagesSlideId();
    qb.addColumnSampleImagesMagnification();
    qb.addColumnSampleImagesCaptureDate();
    //sample pathology column
    qb.addColumnLimsDiagnosis();
    qb.addColumnLimsDiagnosisOther();
    //tissue of origin/finding column
    qb.addColumnLimsTissueOrigin();
    qb.addColumnLimsTissueOriginOther();
    qb.addColumnLimsTissueFinding();
    qb.addColumnLimsTissueFindingOther();
    qb.addColumnAsmTissue();
    qb.addColumnAsmTissueOther();
    //composition column
    qb.addColumnCompositionAcellularStroma();
    qb.addColumnCompositionCellularStroma();
    qb.addColumnCompositionLesion();
    qb.addColumnCompositionNecrosis();
    qb.addColumnCompositionNormal();
    qb.addColumnCompositionTumor();
    //extended composition column
    qb.addColumnPvNotes();
    qb.addColumnPvNotesInternal();
    //microscopic appearance column
    qb.addColumnMicroscopicAppearance();
    //tnm staging column
    qb.addColumnTumorStage();
    qb.addColumnLymphNodeStage();
    qb.addColumnDistantMetastasis();
    qb.addColumnStageGrouping();
    //histonuclear grade
    qb.addColumnHistologicNuclearGrade();
    //tumor size/weight column
    qb.addColumnTumorSize();
    qb.addColumnTumorWeight();
    //age/gender column   
    qb.addColumnAgeAtCollection();
    
    qb.addColumnGender();
    //inventory status column
    qb.addColumnInventoryStatus();
    qb.addColumnRnaId();
    qb.addColumnTmaId();
    qb.addColumnSampleBoxBarcodeId();
    qb.addColumnSampleSubdivisionDate();
    //sales status column
    qb.addColumnOrderDescription();
    //project status column
    qb.addColumnShoppingCartUser();
    qb.addColumnShoppingCartCreationDate();
    qb.addColumnProject();
    qb.addColumnProjectDateRequested();
    qb.addColumnSalesStatus();
    qb.addColumnProjectStatus();
    //logical repository columns
    qb.addMandatoryLogicalRepositoryColumns();
    qb.addColumnUserLogicalRepositoryShortNames();

    // For each id, create a SampleData bean that is keyed by the id, and 
    // insert the data bean into a List to preserve the order and 
    // Map for the call to the query builder.
    List ids = sampleIds.getList();
    List sampleDatas = new ArrayList();
    Map sampleMap = new HashMap();
    int numIds = sampleIds.size();
    for (int i = 0; i < numIds; i++) {
      String id = (String) ids.get(i);
      SampleData sample = new SampleData();
      sample.setSampleId(id);
      sampleMap.put(id, sample);
      sampleDatas.add(sample);
    }

    qb.getDetails(sampleMap);

    for (int i = 0; i < numIds; i++) {
      PathologyEvaluationSampleData peSampleData = new PathologyEvaluationSampleData();

      SampleData s = (SampleData) sampleDatas.get(i);
      peSampleData.setSample(s);
      peSampleData.setReportedEvaluation(s.getPathologyEvaluationData());

      peSampleData.setOrder(s.getOrderData());
      peSampleData.setProject(s.getProjectData());

      AsmData a = s.getAsmData();
      if (a != null) {
        peSampleData.setAsm(a);
        ConsentData c = a.getConsentData();
        if (c != null) {
          peSampleData.setConsent(c);
          PathReportData p = c.getPathReportData();
          peSampleData.setPathReport(p);
          if (p != null) {
            PathReportSectionData ps = p.getPrimarySectionData();
            peSampleData.setPrimarySection(ps);
          }
        }
      }
      btxDetails.getPathologyEvaluationSampleDatas().add(peSampleData);
    }

    //return the btxDetails object passed in, marked as completed.
    return (BTXDetailsGetPathQCSampleDetails) btxDetails.setActionForwardTXCompleted(
      LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
  }

}
