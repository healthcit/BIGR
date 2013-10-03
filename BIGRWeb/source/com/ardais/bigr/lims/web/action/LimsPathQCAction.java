package com.ardais.bigr.lims.web.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForward;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.api.ApiProperties;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.iltds.btx.BtxActionMessage;
import com.ardais.bigr.iltds.btx.BtxActionMessages;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.lims.beans.LimsOperation;
import com.ardais.bigr.lims.beans.LimsOperationHome;
import com.ardais.bigr.lims.btx.BTXDetailsCreatePathologyEvaluationPrepare;
import com.ardais.bigr.lims.btx.BTXDetailsGetPathQCSampleSummary;
import com.ardais.bigr.lims.btx.BTXDetailsMarkSamplePulled;
import com.ardais.bigr.lims.btx.BTXDetailsMarkSampleQCPosted;
import com.ardais.bigr.lims.btx.BTXDetailsMarkSampleReleased;
import com.ardais.bigr.lims.btx.BTXDetailsMarkSampleUnQCPosted;
import com.ardais.bigr.lims.btx.BTXDetailsMarkSampleUnpulled;
import com.ardais.bigr.lims.btx.BTXDetailsMarkSampleUnreleased;
import com.ardais.bigr.lims.helpers.LimsConstants;
import com.ardais.bigr.lims.javabeans.PathologyEvaluationData;
import com.ardais.bigr.lims.web.form.LimsPathQcForm;
import com.ardais.bigr.query.LimsPathQcQuery;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.BigrValidator;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.StrutsUtils;
import com.ardais.bigr.util.WebUtils;
import com.ardais.bigr.web.action.BigrAction;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

public class LimsPathQCAction extends BigrAction {

  /**
   * @see com.ardais.bigr.web.action.BigrAction#doPerform(BigrActionMapping, BigrActionForm, 
   * HttpServletRequest, HttpServletResponse)
   */
  protected ActionForward doPerform(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);
    LimsPathQcForm lf = (LimsPathQcForm) form;
    IdList summaryList = null;
    ActionErrors errors = null;
    BtxActionMessages messages = null;
    //Map which stores the pairs of transaction type, lists of samples 
    //as its values.
    Map resultsMap = new HashMap();
    LimsPathQcQuery query =
      (LimsPathQcQuery) request.getSession().getAttribute(LimsPathQcQuery.LIMSPATHQCQUERY_KEY);

    if (request.getParameter("clearMark") != null) {
      query.removeAllMarkedSamples();
      lf.setMarkedSamples(new String[0]);
    }

    //Add to markedSampleFunctions map, reasons Map if there are
    //any samples marked for QC. Added this functionality for MR 5605.
    Enumeration params = request.getParameterNames();
    String sampleFunction = null;
    while (params.hasMoreElements()) {
      String paramName = (String) params.nextElement();
      //Add all marked samples
      if (paramName.startsWith("QC")) {
        sampleFunction = request.getParameter(paramName);
        query.addToMarkedSampleFunctions(paramName, sampleFunction);
      }
      //Add all reasons
      if (paramName.startsWith("reason")) {
        String reason = request.getParameter(paramName);
        query.addToReasons(paramName, reason);
      }

    }

    //If user selects Search button
    if ((request.getParameter("filter") != null)
      && (request.getParameter("filter").equals("Search"))) {
      //Remove attributes from session.	
      request.getSession().removeAttribute(LimsPathQcQuery.LIMSPATHQCQUERY_KEY);
      request.getSession().removeAttribute(LimsConstants.LIMS_CHUNK_INDEX);
      request.getSession().removeAttribute(LimsConstants.LIMS_CURRENT_CHUNK);
      lf.setIndex("1");
      query = null;
      lf.setPathQcSampleDetails(null);
      BTXDetailsGetPathQCSampleSummary btx = getBTXDetailsGetPathQcSampleSummary(lf);
      btx.setLoggedInUserSecurityInfo(securityInfo);
      if (ApiFunctions.isEmpty(lf.getRetrieveCounts())) {
        btx.setRetrieveCounts(false);
      }
      btx.setTransactionType("lims_get_path_qc_sample_summary");
      btx = (BTXDetailsGetPathQCSampleSummary) Btx.perform(btx);
      if (btx.isTransactionCompleted()) {
        summaryList = (IdList) btx.getIds();

        // Get the chunk size.
        Integer chunkSize = new Integer(0);
        try {
          chunkSize = new Integer(ApiProperties.getProperty("api.bigr.lims.pathqc.chunk.size"));
        }
        catch (RuntimeException e) {
          ApiLogger.log("Error Reading Chunk Size: defaulting to 0", e);
        }
        if ((summaryList != null) && (summaryList.size() > 0)) {
          query = new LimsPathQcQuery(chunkSize.intValue(), summaryList, securityInfo);
          //Add to session
          request.getSession().setAttribute(LimsPathQcQuery.LIMSPATHQCQUERY_KEY, query);

          //Add samples details to form
          //lf.setPathQcSampleDetails(query.getSamplesChunk(0));
        }

        lf.setUnPVedCount(String.valueOf(btx.getUnPVedCount()));
        lf.setPvCount(String.valueOf(btx.getPvCount()));
        lf.setReleasedCount(String.valueOf(btx.getReleasedCount()));
        lf.setPulledCount(String.valueOf(btx.getPulledCount()));
        lf.setReturnedSamplesCount(String.valueOf(btx.getReturnedSampleCount()));
        lf.setQcPostedCount(String.valueOf(btx.getQcPostedCount()));
        request.getSession().setAttribute(LimsConstants.LIMS_CURRENT_CHUNK, "1");
      }
      //Save errors.  This code is here because we might get errors even if the transaction
      //completed (i.e. zero samples returned)
      BtxActionErrors btxErrors = btx.getActionErrors();
      errors =
        StrutsUtils.addActionErrors(errors, StrutsUtils.convertBtxActionErrorsToStruts(btxErrors));

    }
    //User selected hyperlink for PathQC. 
    else if (!ApiFunctions.isEmpty(lf.getSampleFunction())) {
      //Do required function.
      errors =
        doPathQCFunction(
          lf.getTargetSampleId(),
          lf.getSampleFunction(),
          request,
          errors,
          query,
          resultsMap,
          lf);
    }
    //User selects UpdateAll button.
    else if (!ApiFunctions.isEmpty(lf.getUpdateAll())) {
      //Get the marked functions from LimsPathQcQuery object
      //which is in session. All the current changes are added to this
      //object at the begining of this method.
      if (query.getMarkedSampleFunctions() != null) {
        sampleFunction = null;
        String paramName = null;
        //Each key in MarkedSampleFunctions represents the component
        //name user selected.             
        Iterator qcIterator = query.getMarkedSampleFunctions().keySet().iterator();
        while (qcIterator.hasNext()) {
          paramName = (String) qcIterator.next();
          //Get sampleId from component name. 
          //component is in the form of QCFRXXXXXXXX.
          String sampleId = paramName.substring(2);
          //Get sample function.
          sampleFunction = (String) query.getMarkedSampleFunctions().get(paramName);
          //Do PathQC function if it is not "none".
          if (!ApiFunctions.safeEquals(LimsConstants.LIMS_TX_NONE, sampleFunction)) {

            errors =
              doPathQCFunction(sampleId, sampleFunction, request, errors, query, resultsMap, lf);
          }
        }
        //empty markedSampleFuctions Map
        query.removeAllMarkedSampleFunctions();
        query.removeAllReasons();
      }
    }

    //Construct error and informational messages. These messages represent the work which 
    //has been done in this transaction.    
    if ((resultsMap != null) && (resultsMap.size() > 0)) {
      //For most actions, we only want to return a message that it was successful (as
      //opposed to an error, which will display a popup).  The following call constructs
      //the errors and messages we want to display to capture the work which was performed.
      //Note: any messages returned from the Btx operations (such as when a
      //sample is pulled or unpulled) are not retained.  The following call will return
      //one message for all the samples pulled, instead of 1 per sample.  Any errors returned
      //from the Btx operations ARE retained however.
      if (errors == null) {
        errors = new ActionErrors();
      }
      if (messages == null) {
        messages = new BtxActionMessages();
      }
      constructMessagesAndErrors(errors, messages, resultsMap);
    }
    //Chunking
    int previousIndex =
      new Integer((String) request.getSession().getAttribute(LimsConstants.LIMS_CURRENT_CHUNK))
        .intValue();
    String index = lf.getIndex();
    if (!ApiFunctions.isEmpty(index)) {
      request.getSession().setAttribute(LimsConstants.LIMS_CURRENT_CHUNK, index);

      if (query != null) {
        //Update marked samples
        updateMarkedSamples(query, lf.getMarkedSamples(), previousIndex);
        lf.setPathQcSampleDetails(query.getSamplesChunk(new Integer(index).intValue() - 1));
      }

    }
    //Save errors and messages to request
    saveErrors(request, errors);
    saveMessages(request, messages);

    return (mapping.findForward("success"));
  }
  /**
   * Performs PathQC functionality based on sampleFunction.
   * @param targetSampleId the sampleId.
   * @param sampleFunction.
   * @param request.
   * @param errors.
   * @param query.
   * @param resultsMap
   * @return errors if any,
   */
  private ActionErrors doPathQCFunction(
    String targetSampleId,
    String sampleFunction,
    HttpServletRequest request,
    ActionErrors errors,
    LimsPathQcQuery query,
    Map resultsMap,
    LimsPathQcForm form)
    throws Exception {
    //Release sample                            
    if ((sampleFunction.equals(LimsConstants.LIMS_TX_RELEASE))) {
      BTXDetailsMarkSampleReleased btxDetails = new BTXDetailsMarkSampleReleased();
      btxDetails.setLoggedInUserSecurityInfo(WebUtils.getSecurityInfo(request));
      btxDetails.setBeginTimestamp(new java.sql.Timestamp(new java.util.Date().getTime()));
      btxDetails.setSampleId(targetSampleId);
      btxDetails.setTransactionType("lims_mark_sample_released");
      btxDetails =
        (BTXDetailsMarkSampleReleased) Btx.perform(btxDetails);

      if (btxDetails.isTransactionCompleted()) {
        //Add to modified samples. 
        query.addToModifiedSamples(targetSampleId);
        //Add sample to releasedSamples List. 
        List releasedSamples = (List) resultsMap.get("releasedSamples");
        if (ApiFunctions.isEmpty(releasedSamples)) {
          releasedSamples = new ArrayList();
          resultsMap.put("releasedSamples", releasedSamples);
        }
        releasedSamples.add(targetSampleId);
      }
      //Save errors.
      BtxActionErrors btxErrors = btxDetails.getActionErrors();
      errors =
        StrutsUtils.addActionErrors(errors, StrutsUtils.convertBtxActionErrorsToStruts(btxErrors));

    }
    //Un release sample 
    else if ((sampleFunction.equals(LimsConstants.LIMS_TX_UNRELEASE))) {
      BTXDetailsMarkSampleUnreleased btxDetails = new BTXDetailsMarkSampleUnreleased();
      btxDetails.setLoggedInUserSecurityInfo(WebUtils.getSecurityInfo(request));
      btxDetails.setBeginTimestamp(new java.sql.Timestamp(new java.util.Date().getTime()));
      btxDetails.setSampleId(targetSampleId);
      btxDetails.setTransactionType("lims_mark_sample_unreleased");
      btxDetails =
        (BTXDetailsMarkSampleUnreleased) Btx.perform(btxDetails);

      if (btxDetails.isTransactionCompleted()) {
        //Add to modified samples. 
        query.addToModifiedSamples(targetSampleId);
        //Add sample to unreleasedSamples List. 
        List unReleasedSamples = (List) resultsMap.get("unReleasedSamples");
        if (ApiFunctions.isEmpty(unReleasedSamples)) {
          unReleasedSamples = new ArrayList();
          resultsMap.put("unReleasedSamples", unReleasedSamples);
        }
        unReleasedSamples.add(targetSampleId);
      }
      //Save errors.
      BtxActionErrors btxErrors = btxDetails.getActionErrors();
      errors =
        StrutsUtils.addActionErrors(errors, StrutsUtils.convertBtxActionErrorsToStruts(btxErrors));
    }
    //Pull sample
    else if ((sampleFunction.equals(LimsConstants.LIMS_TX_PULL))) {
      BTXDetailsMarkSamplePulled btxDetails = new BTXDetailsMarkSamplePulled();
      btxDetails.setLoggedInUserSecurityInfo(WebUtils.getSecurityInfo(request));
      btxDetails.setBeginTimestamp(new java.sql.Timestamp(new java.util.Date().getTime()));
      btxDetails.setSampleId(targetSampleId);
      //Get pull reason
      String reason = query.getReason("reason" + targetSampleId);
      btxDetails.setReason(reason);
      btxDetails.setTransactionType("lims_mark_sample_pulled");
      
      btxDetails = (BTXDetailsMarkSamplePulled) Btx.perform(btxDetails);

      if (btxDetails.isTransactionCompleted()) {
        //Add to modified samples. 
        query.addToModifiedSamples(targetSampleId);

        //Add sample to pulledSamples List. 
        List pulledSamples = (List) resultsMap.get("pulledSamples");
        if (ApiFunctions.isEmpty(pulledSamples)) {
          pulledSamples = new ArrayList();
          resultsMap.put("pulledSamples", pulledSamples);
        }
        pulledSamples.add(targetSampleId);
      }
      else {
        //Save errors.
        BtxActionErrors btxErrors = btxDetails.getActionErrors();
        errors =
          StrutsUtils.addActionErrors(
            errors,
            StrutsUtils.convertBtxActionErrorsToStruts(btxErrors));
      }
    }
    //Un pull sample
    else if ((sampleFunction.equals(LimsConstants.LIMS_TX_UNPULL))) {
      BTXDetailsMarkSampleUnpulled btxDetails = new BTXDetailsMarkSampleUnpulled();
      btxDetails.setLoggedInUserSecurityInfo(WebUtils.getSecurityInfo(request));
      btxDetails.setBeginTimestamp(new java.sql.Timestamp(new java.util.Date().getTime()));
      btxDetails.setSampleId(targetSampleId);
      //Get pull reason.
      String reason = query.getReason("reason" + targetSampleId);
      btxDetails.setReason(reason);
      //Get indicator of whether we should try to report the most recent evaluation or not
      String reportMostRecentEval = form.getReportMostRecentEval();
      btxDetails.setReportMostRecentEval(reportMostRecentEval);
      btxDetails.setTransactionType("lims_mark_sample_unpulled");
      btxDetails =
        (BTXDetailsMarkSampleUnpulled) Btx.perform(btxDetails);

      if (btxDetails.isTransactionCompleted()) {
        //Add to modified samples. 
        query.addToModifiedSamples(targetSampleId);

        //Add sample to unpulledSamples List. 
        List unPulledSamples = (List) resultsMap.get("unPulledSamples");
        if (ApiFunctions.isEmpty(unPulledSamples)) {
          unPulledSamples = new ArrayList();
          resultsMap.put("unPulledSamples", unPulledSamples);
        }
        unPulledSamples.add(targetSampleId);
        //If the unpulling of the sample resulted in an evaluation being reported, add that
        //evaluation id to the reportedEval List.
        if (btxDetails.getReportedEvalId() != null) {
          List unpullReportedEvaluations = (List) resultsMap.get("unpullReportedEvaluations");
          if (ApiFunctions.isEmpty(unpullReportedEvaluations)) {
            unpullReportedEvaluations = new ArrayList();
            resultsMap.put("unpullReportedEvaluations", unpullReportedEvaluations);
          }
          unpullReportedEvaluations.add(btxDetails.getReportedEvalId());
        }
      }
      else {
        //Save errors.
        BtxActionErrors btxErrors = btxDetails.getActionErrors();
        errors =
          StrutsUtils.addActionErrors(
            errors,
            StrutsUtils.convertBtxActionErrorsToStruts(btxErrors));
      }
    }
    //QCPost sample
    else if ((sampleFunction.equals(LimsConstants.LIMS_TX_QCPOST))) {
      BTXDetailsMarkSampleQCPosted btxDetails = new BTXDetailsMarkSampleQCPosted();
      btxDetails.setLoggedInUserSecurityInfo(WebUtils.getSecurityInfo(request));
      btxDetails.setBeginTimestamp(new java.sql.Timestamp(new java.util.Date().getTime()));
      btxDetails.setSampleId(targetSampleId);
      btxDetails.setTransactionType("lims_mark_sample_qc_posted");
      btxDetails =
        (BTXDetailsMarkSampleQCPosted) Btx.perform(btxDetails);

      if (btxDetails.isTransactionCompleted()) {
        //Add to modified samples. 
        query.addToModifiedSamples(targetSampleId);

        //Add sample to postedSamples List. 
        List postedSamples = (List) resultsMap.get("postedSamples");
        if (ApiFunctions.isEmpty(postedSamples)) {
          postedSamples = new ArrayList();
          resultsMap.put("postedSamples", postedSamples);
        }
        postedSamples.add(targetSampleId);
      }
      //Save errors.
      BtxActionErrors btxErrors = btxDetails.getActionErrors();
      errors =
        StrutsUtils.addActionErrors(errors, StrutsUtils.convertBtxActionErrorsToStruts(btxErrors));
    }
    //UnQCPost sample
    else if ((sampleFunction.equals(LimsConstants.LIMS_TX_UNQCPOST))) {
      BTXDetailsMarkSampleUnQCPosted btxDetails = new BTXDetailsMarkSampleUnQCPosted();
      btxDetails.setLoggedInUserSecurityInfo(WebUtils.getSecurityInfo(request));
      btxDetails.setBeginTimestamp(new java.sql.Timestamp(new java.util.Date().getTime()));
      btxDetails.setSampleId(targetSampleId);
      btxDetails.setTransactionType("lims_mark_sample_un_qc_posted");
      btxDetails =
        (BTXDetailsMarkSampleUnQCPosted) Btx.perform(btxDetails);

      if (btxDetails.isTransactionCompleted()) {
        //Add to modified samples. 
        query.addToModifiedSamples(targetSampleId);

        //Add sample to unPostedSamples List. 
        List unPostedSamples = (List) resultsMap.get("unPostedSamples");
        if (ApiFunctions.isEmpty(unPostedSamples)) {
          unPostedSamples = new ArrayList();
          resultsMap.put("unPostedSamples", unPostedSamples);
        }
        unPostedSamples.add(targetSampleId);
      }
      //Save errors.
      BtxActionErrors btxErrors = btxDetails.getActionErrors();
      errors =
        StrutsUtils.addActionErrors(errors, StrutsUtils.convertBtxActionErrorsToStruts(btxErrors));
    }
    //User selects "QC/Edit PV"
    else if ((sampleFunction.equals(LimsConstants.LIMS_TX_QCEDITPV))) {
      //get the reported evaluation for the sample
      LimsOperationHome home = (LimsOperationHome) EjbHomes.getHome(LimsOperationHome.class);
      LimsOperation operation = home.create();
      PathologyEvaluationData peData = operation.getReportedPathologyEvaluation(targetSampleId);
      String reportedEvalSlideId = peData.getSlideId();
      String reportedEvalId = peData.getEvaluationId();
      //see if we can successfully prepare a new evaluation
      BTXDetailsCreatePathologyEvaluationPrepare btxDetails =
        new BTXDetailsCreatePathologyEvaluationPrepare();
      btxDetails.setLoggedInUserSecurityInfo(WebUtils.getSecurityInfo(request));
      btxDetails.setBeginTimestamp(new java.sql.Timestamp(new java.util.Date().getTime()));
      btxDetails.setSlideId(reportedEvalSlideId);
      btxDetails.setSourceEvaluationId(reportedEvalId);
      btxDetails.setTransactionType("lims_create_path_eval_prepare");
      btxDetails =
        (BTXDetailsCreatePathologyEvaluationPrepare) Btx.perform(
          btxDetails);
      //if the transaction was successful then pass up the slide id and evaluation id that
      //the popup on the PathQC screen needs
      if (btxDetails.isTransactionCompleted()) {
        request.setAttribute("QCEditSlideId", reportedEvalSlideId);
        request.setAttribute("QCEditEvaluationId", reportedEvalId);
      }
      //otherwise turn off the flag that indicates we should show the popup (if
      //this isn't done the PathQC screen will show the popup even through there
      //were errors) and to be safe pass blank values for the slide and evaluation ids.
      else {
        form.setViewQCEdit("false");
        request.setAttribute("QCEditSlideId", "");
        request.setAttribute("QCEditEvaluationId", "");
      }
      //Save errors.
      BtxActionErrors btxErrors = btxDetails.getActionErrors();
      errors =
        StrutsUtils.addActionErrors(errors, StrutsUtils.convertBtxActionErrorsToStruts(btxErrors));
    }
    //we're done creating the new evaluation and this action has been invoked from the
    //LimsManageEvaluationsTemplate.jsp
    else if ((sampleFunction.equals(LimsConstants.LIMS_TX_UPDATEPV))) {
      //Add to modified samples. 
      query.addToModifiedSamples(targetSampleId);
    }
    return errors;
  }
  /**
   * Updates the list of marked sample for Incident report. 
   * @param <code>LimsPathQcQuery</code> query.
   * @param <code>String[]</code> markedSamples.
   * @param <code>int</code> index.
   */
  private void updateMarkedSamples(LimsPathQcQuery query, String[] markedSamples, int index) {
    List allSamples = query.getSampleSummaryChunk(index - 1);
    List markedSamplesList = null;

    if (markedSamples != null) {
      markedSamplesList = Arrays.asList(markedSamples);
    }

    //Loop through all the samples in the current chunk. Add the sample
    //to marked samples list if any of them are checked. If not
    //remove from list.
    for (Iterator iter = allSamples.iterator(); iter.hasNext();) {
      String sampleId = (String) iter.next();

      if (markedSamplesList != null) {
        //Add to marked samples List.
        if (markedSamplesList.contains(sampleId)) {
          query.getMarkedSamples().add(sampleId);
        }
        else {
          query.getMarkedSamples().remove(sampleId);
        }
      }
      //Remove from marked samples List.
      else {
        query.getMarkedSamples().remove(sampleId);
      }
    }
  }

  private BTXDetailsGetPathQCSampleSummary getBTXDetailsGetPathQcSampleSummary(LimsPathQcForm lf) {
    BTXDetailsGetPathQCSampleSummary pathQC = new BTXDetailsGetPathQCSampleSummary();

    //setup initial information
    pathQC.setBeginTimestamp(new Timestamp(new java.util.Date().getTime()));

    pathQC.setCaseIds(getCaseIds(lf));
    pathQC.setSlideIds(getSlideIds(lf));
    pathQC.setSampleIds(getSampleIds(lf));

    //check boxes
    setCheckboxValues(lf, pathQC);

    //radio buttons
    String pvStatus = lf.getPvStatus();
    if (LimsConstants.PATH_QC_INPROCESS_SAMPLES.equals(pvStatus)) {
      pathQC.setQcStatusAwaiting(false);
      pathQC.setQcStatusInProcess(true);
    }
    else if (LimsConstants.PATH_QC_AWAITING_SAMPLES.equals(pvStatus)) {
      pathQC.setQcStatusAwaiting(true);
      pathQC.setQcStatusInProcess(false);
    }
    else if (LimsConstants.PATH_QC_BOTH_SAMPLES.equals(pvStatus)) {
      pathQC.setQcStatusAwaiting(true);
      pathQC.setQcStatusInProcess(true);
    }

    //set sort order fields
    pathQC.setSortColumn(lf.getSortValue());
    pathQC.setSortOrder(lf.getSortOrderValue());

    pathQC.setPathologists(getPathologist(lf));

    pathQC.setLogicalRepositories(getLogicalRepositories(lf));

    pathQC.setReportedDateStart(getPvStartDate(lf));
    pathQC.setReportedDateEnd(getPvEndDate(lf));

    return pathQC;
  }

  private void setCheckboxValues(LimsPathQcForm lf, BTXDetailsGetPathQCSampleSummary pathQC) {

    String[] checkboxes = lf.getIncludeList();
    if (checkboxes != null) {
      for (int i = 0; i < checkboxes.length; i++) {
        String temp = checkboxes[i];

        if (temp.equals(LimsConstants.PATH_QC_PULLED_SAMPLES)) {
          pathQC.setIncludePulledSamples(true);
        }
        else if (temp.equals(LimsConstants.PATH_QC_RELEASED_SAMPLES)) {
          pathQC.setIncludeReleasedSamples(true);
        }
        else if (temp.equals(LimsConstants.PATH_QC_SAMPLES_NOT_PVD)) {
          pathQC.setIncludeUnPVdSamples(true);
        }
        else if (temp.equals(LimsConstants.PATH_QC_SAMPLES_FOR_RELEASE)) {
          pathQC.setIncludeUnreleasedSamples(true);
        }
        else if (temp.equals(LimsConstants.PATH_QC_POSTING_SAMPLES)) {
          pathQC.setIncludeQCPostedSamples(true);
        }
      }
    }

  }

  private IdList getCaseIds(LimsPathQcForm lf) {
    IdList caseIds = new IdList();
    String[] caseArray = lf.getCaseIdList();

    if (caseArray != null) {
      for (int i = 0; i < caseArray.length; i++) {
        if (caseArray[i] != null)
          caseIds.add(caseArray[i]);
      }
    }
    return caseIds;
  }

  private IdList getSampleIds(LimsPathQcForm lf) {
    IdList sampleIds = new IdList();
    String[] sampleArray = lf.getSampleIdList();

    if (sampleArray != null) {
      for (int i = 0; i < sampleArray.length; i++) {
        sampleIds.add(sampleArray[i]);
      }
    }
    return sampleIds;
  }

  private IdList getSlideIds(LimsPathQcForm lf) {
    IdList slideIds = new IdList();
    String[] slideArray = lf.getSlideIdList();

    if (slideArray != null) {
      for (int i = 0; i < slideArray.length; i++) {
        slideIds.add(slideArray[i]);
      }
    }
    return slideIds;
  }

  private IdList getLogicalRepositories(LimsPathQcForm lf) {
    IdList logicalRepositories = new IdList();
    String[] logicalRepositoriesArray = lf.getLogicalRepositoryList();

    if (logicalRepositoriesArray != null) {
      for (int i = 0; i < logicalRepositoriesArray.length; i++) {
        if (logicalRepositoriesArray[i].equals(LimsConstants.DEFAULT_ALL)) {
          return null;
        }

        logicalRepositories.add(logicalRepositoriesArray[i]);
      }
    }
    return logicalRepositories;
  }

  private IdList getPathologist(LimsPathQcForm lf) {
    IdList pathologists = new IdList();
    String[] pathologistArray = lf.getPathologistList();

    if (pathologistArray != null) {
      for (int i = 0; i < pathologistArray.length; i++) {
        if (pathologistArray[i].equals(LimsConstants.DEFAULT_ALL)) {
          return null;
        }

        pathologists.add(pathologistArray[i]);
      }
    }
    return pathologists;
  }

  private java.util.Date getPvStartDate(LimsPathQcForm lf) {
    if (ApiFunctions.isEmpty(lf.getStartDate())) return null;
    return new java.util.Date(BigrValidator.formatDate(lf.getStartDate(), "MM/dd/yyyy", true).getTime());

  }

  private java.util.Date getPvEndDate(LimsPathQcForm lf) {
    if (ApiFunctions.isEmpty(lf.getEndDate())) return null;
    return new java.util.Date(BigrValidator.formatDate(lf.getEndDate(), "MM/dd/yyyy", true).getTime());
  }

  /**
   * Constructs user friendly messages/errors to capture the work that has been performed from
   * the resultsMap.
   * @param errorBuff StringBuffer to hold errors
   * @param messageBuff StringBuffer to hold messages
   * @param resultsMap.
   */
  private void constructMessagesAndErrors(
    ActionErrors errors,
    BtxActionMessages messages,
    Map resultsMap) {
    if (resultsMap != null) {
      List samplesList = null;

      //Check for released Samples - these get a message
      samplesList = (List) resultsMap.get("releasedSamples");
      if (!ApiFunctions.isEmpty(samplesList)) {
        messages.add(
          new BtxActionMessage(
            "lims.message.markSampleReleased.success",
            getDelimitedString(samplesList)));
      }

      //Check for UnReleased Samples - these get an error
      samplesList = (List) resultsMap.get("unReleasedSamples");
      if (!ApiFunctions.isEmpty(samplesList)) {
        errors.add(
          BtxActionErrors.GLOBAL_ERROR,
          new ActionError(
            "lims.message.markSampleUnreleased.success",
            getDelimitedString(samplesList)));
      }

      //Check for pulled Samples - these get a message
      samplesList = (List) resultsMap.get("pulledSamples");
      if (!ApiFunctions.isEmpty(samplesList)) {
        messages.add(
          new BtxActionMessage(
            "lims.message.markSamplePulled.success",
            getDelimitedString(samplesList)));
      }

      //Check for unpulled Samples - these get a message
      samplesList = (List) resultsMap.get("unPulledSamples");
      if (!ApiFunctions.isEmpty(samplesList)) {
        List unpullReportedEvaluations = (List) resultsMap.get("unpullReportedEvaluations");
        if (ApiFunctions.isEmpty(unpullReportedEvaluations)) {
          messages.add(
            new BtxActionMessage(
              "lims.message.markSampleUnPulled.success",
              getDelimitedString(samplesList)));
        }
        else {
          messages.add(
            new BtxActionMessage(
              "lims.message.markSampleUnPulledWithReportedEval.success",
              getDelimitedString(samplesList),
              getDelimitedString(unpullReportedEvaluations)));
        }
      }

      //Check for posted Samples - these get a message
      samplesList = (List) resultsMap.get("postedSamples");
      if (!ApiFunctions.isEmpty(samplesList)) {
        messages.add(
          new BtxActionMessage(
            "lims.message.markSampleQCPosted.success",
            getDelimitedString(samplesList)));
      }

      //Check for unposted Samples - these get an error
      samplesList = (List) resultsMap.get("unPostedSamples");
      if (!ApiFunctions.isEmpty(samplesList)) {
        errors.add(
          BtxActionErrors.GLOBAL_ERROR,
          new ActionError(
            "lims.message.markSampleUnQCPosted.success",
            getDelimitedString(samplesList)));
      }

    }
  }

  /**
   * Returns string which contains items in list seperated by ",". Returns 
   * blank string if list is null.
   * @param list of items.
   * @return comma seperated string of items.
   */
  private String getDelimitedString(List list) {
    StringBuffer buf = new StringBuffer(128);
    if (list != null) {
      Iterator iter = list.iterator();
      while (iter.hasNext()) {
        buf.append((String) iter.next());
        if (iter.hasNext()) {
          buf.append(", ");
        }
      }
    }
    return buf.toString();
  }
}
