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
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.StrutsUtils;
import com.ardais.bigr.util.WebUtils;
import com.ardais.bigr.web.action.BigrAction;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * This represents the Action that is used to update Samples.
 * It currently updates only properties in ILTDS_SAMPLE. 
 * This operation is expensive.
 */
public class UpdateSampleAction extends BigrAction {

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
      throw new ApiException("No value was specified in the request parameter \"id\" - specify \"all\" or the ids of the samples to update.");
    }

    //if the user specified all samples be updated, get all the ids.  Otherwise grab the ones they
    //specified.
    Vector sampleIds = new Vector();
//SWP-807.  Removed the sample.properties field, which was the only thing being updated.
//However, wanted to keep the infrastructure in place in case we ever want to update
//samples.  So, to make this a no-op for now don't populate the sampleIds vector.
/*
    if (ids[0].equalsIgnoreCase("all")) {
      String sql = "SELECT sample_barcode_id FROM iltds_sample";
      List samples = ApiFunctions.runQueryForMappedDataBean(sql, null, SampleData.class);
      Iterator iterator = samples.iterator();
      while (iterator.hasNext()) {
        sampleIds.add(((SampleData) iterator.next()).getSampleId());
      }
    }
    else {
      for (int i = 0; i < ids.length; i++) {
        sampleIds.add(ids[i]);
      }
    }
*/
    ActionErrors errors = null;
    //now that we know what ids to update, do them in chunks of 100
    int chunkSize = 100;
    Vector v = new Vector();
    int inLoopCount = 0;
    int processedCount = 0;
    DataComputationRequestData req = new DataComputationRequestData();
    req.setObjectType(Constants.COMPUTED_DATA_OBJECT_TYPE_SAMPLE);
    for (int i = 0; i < sampleIds.size(); i++) {
      v.add(sampleIds.elementAt(i));
      inLoopCount = inLoopCount + 1;
      //if we've accumulated "chunkSize" ids or hit the last of the ids, process 
      //them and get ready for the next batch
      if (inLoopCount == chunkSize || i == sampleIds.size() - 1) {
        //call the update bean to update the samples
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
        //System.out.println("Processed " + inLoopCount + " samples, total number processed = " + processedCount);
        //clear out the count and vector of ids to get ready for the next batch
        inLoopCount = 0;
        v = new Vector();
      }
    }
    request.setAttribute("count", String.valueOf(processedCount));
    request.setAttribute("objectType", "sample");
    //Log the output.
    _log.info("UpdateSampleAction: " + processedCount + " samples successfully updated.");
    saveErrors(request, errors);
    return (mapping.findForward("success"));
  }

}
