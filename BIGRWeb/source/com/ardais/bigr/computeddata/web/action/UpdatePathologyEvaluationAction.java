package com.ardais.bigr.computeddata.web.action;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForward;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.btx.BTXDetailsUpdateComputedData;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.javabeans.DataComputationRequestData;
import com.ardais.bigr.lims.javabeans.PathologyEvaluationData;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.StrutsUtils;
import com.ardais.bigr.util.WebUtils;
import com.ardais.bigr.web.action.BigrAction;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * This represents the Action that is used to update PathologyEvaluations.
 * It currently updates only external comments in LIMS_PATHOLOGY_EVALUATION. 
 * This operation is expensive.
 */
public class UpdatePathologyEvaluationAction extends BigrAction {

  /**
   * Logger for logging data changes.
   */
  private static final Log _log = LogFactory.getLog(ApiLogger.BIGR_LOGNAME_COMPUTED_DATA);

  /**
   * @see com.ardais.bigr.web.action.BigrAction#doPerform(BigrActionMapping, BigrActionForm, HttpServletRequest, HttpServletResponse)
   */
  protected ActionForward doPerform(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    //make sure something was specified for id
    String ids[] = request.getParameterValues("id");
    /* there should always be something in the id parameter(s) - either 1 or more
     * evaluation ids or the word "all" to indicate that all evaluations should 
     * be updated */
    if (ids == null) {
      throw new ApiException("No value was specified in the request parameter \"id\" - specify \"all\" or the ids of the evaluations to update.");
    }

    //make sure something was specified for fields to update
    String fields[] = request.getParameterValues("field");
    /* there should always be something in the field parameter(s) - either 1 or more
     * fields or the word "all" to indicate that all fields we compute should be updated */
    if (fields == null) {
      throw new ApiException("No value was specified in the request parameter \"field\" - specify \"all\" or the fields to be updated.");
    }

    //if the user specified all evaluations be updated, get all the ids.  Otherwise grab the ones they
    //specified.
    Vector evalIds = new Vector();
    if (ids[0].equalsIgnoreCase("all")) {
      String sql = "SELECT pe_id FROM lims_pathology_evaluation";
      List evals = ApiFunctions.runQueryForMappedDataBean(sql, null, PathologyEvaluationData.class);
      Iterator iterator = evals.iterator();
      while (iterator.hasNext()) {
        evalIds.add(((PathologyEvaluationData) iterator.next()).getEvaluationId());
      }
    }
    else {
      for (int i = 0; i < ids.length; i++) {
        evalIds.add(ids[i]);
      }
    }

    //if the user specified all fields be updated, get all the fields. Otherwise grab the ones they
    //specified.
    Vector evalFields = new Vector();
    if (fields[0].equalsIgnoreCase("all")) {
      Iterator iterator = Constants.VALID_COMPUTED_DATA_EVAL_FIELDS.iterator();
      while (iterator.hasNext()) {
        evalFields.add(iterator.next());
      }
    }
    else {
      for (int i = 0; i < fields.length; i++) {
        evalFields.add(fields[i]);
      }
    }

    ActionErrors errors = null;
    //now that we know what ids to update, do them in chunks of 100
    int chunkSize = 100;
    Vector v = new Vector();
    int inLoopCount = 0;
    int processedCount = 0;
    DataComputationRequestData req = new DataComputationRequestData();
    req.setObjectType(Constants.COMPUTED_DATA_OBJECT_TYPE_EVALUATION);
    req.setFieldsToCompute(evalFields);
    for (int i = 0; i < evalIds.size(); i++) {
      v.add(evalIds.elementAt(i));
      inLoopCount = inLoopCount + 1;
      //if we've accumulated "chunkSize" ids or hit the last of the ids, process 
      //them and get ready for the next batch
      if (inLoopCount == chunkSize || i == evalIds.size() - 1) {
        //call the update bean to update the evaluations
        req.setIds(v);
        BTXDetailsUpdateComputedData btxDetails = new BTXDetailsUpdateComputedData();
        btxDetails.setLoggedInUserSecurityInfo(WebUtils.getSecurityInfo(request));
        btxDetails.setBeginTimestamp(btxDetails.getBeginTimestamp());
        btxDetails.setUserId(btxDetails.getUserId());
        Vector requests = new Vector();
        requests.add(req);
        btxDetails.setDataComputationRequests(requests);
        btxDetails.setTransactionType("bigr_update_computed_data");
        btxDetails = (BTXDetailsUpdateComputedData) Btx.perform(btxDetails);
        BtxActionErrors btxErrors = btxDetails.getActionErrors();
        errors =
          StrutsUtils.addActionErrors(
            errors,
            StrutsUtils.convertBtxActionErrorsToStruts(btxErrors));
        processedCount = processedCount + btxDetails.getCount();
        //System.out.println("Processed " + inLoopCount + " evaluations, total number processed = " + processedCount);
        //clear out the count and vector of ids to get ready for the next batch
        inLoopCount = 0;
        v = new Vector();
      }
    }
    request.setAttribute("count", String.valueOf(processedCount));
    request.setAttribute("objectType", "evaluation");
    //Log the output.
    _log.info("UpdatePathologyEvaluationAction: " + processedCount + " evaluations successfully updated.");
    saveErrors(request, errors);
    return (mapping.findForward("success"));
  }

}
