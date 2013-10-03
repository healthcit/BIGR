package com.ardais.bigr.library.web.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForward;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.iltds.beans.ListGenerator;
import com.ardais.bigr.iltds.beans.ListGeneratorHome;
import com.ardais.bigr.iltds.beans.PathologyOperation;
import com.ardais.bigr.iltds.beans.PathologyOperationHome;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXDetailsPickListRequest;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.library.web.form.ResultsForm;
import com.ardais.bigr.library.web.helper.ResultsHelper;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.WebUtils;
import com.ardais.bigr.web.action.BigrAction;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

public class CreatePicklistAction extends BigrAction {

  /**
   * @see com.ardais.bigr.web.action.BigrAction#doPerform(BigrActionMapping, BigrActionForm, HttpServletRequest, HttpServletResponse)
   */
  protected ActionForward doPerform(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    SecurityInfo securityInfo = getSecurityInfo(request);

    ResultsForm rf = (ResultsForm) form;

    // stuff we set up from the helper in the synchronized block.
    IdList btxSamples = null;
    String btxTransactionType = null;
    Vector sampleLocations = null;

    ResultsHelper helper = ResultsHelper.get(ResultsHelper.TX_TYPE_SAMP_REQUEST, request);

    synchronized (helper) {
      String[] ids = helper.getSelectedSamples().getSampleIds();
      btxSamples = new IdList(Arrays.asList(ids));

      ActionErrors errs = new ActionErrors();
      
      //make sure the user is allowed to create Pathology or R&D requests.  If not,
      //return an error and don't do any further validation
      if (rf.getRequestType().equals(BTXDetails.BTX_TYPE_PATH_SAMPLE_REQUEST)) {
        if (!securityInfo.isHasPrivilege(SecurityInfo.PRIV_ILTDS_CREATE_PATH_REQUESTS)) {
           errs.add(null, new ActionError("error.noPrivilege", "create Pathology requests"));
           saveErrors(request, errs);
           return mapping.findForward("fail");
        }
      }
      else if (rf.getRequestType().equals(BTXDetails.BTX_TYPE_RND_SAMPLE_REQUEST)) {
        if (!securityInfo.isHasPrivilege(SecurityInfo.PRIV_ILTDS_CREATE_RAND_REQUESTS)) {
           errs.add(null, new ActionError("error.noPrivilege", "create R&D requests"));
           saveErrors(request, errs);
           return mapping.findForward("fail");
        }
      }
      
      if (btxSamples.getList().isEmpty()) {
        errs.add("nosampleselected", new ActionError("iltds.error.rs.noitemsselected", "picklist"));
        saveErrors(request, errs);
      }

      //make sure the user has not chosen a combination of BMS and non-BMS items
      ArrayList tissueIds = new ArrayList(ids.length);
      for (int i = 0; i < ids.length; i++) {
        tissueIds.add(ids[i]);
      }
      ArrayList bmsSamples = IltdsUtils.getBMSSamplesFromList(IltdsUtils.BMS, tissueIds);
      ArrayList nonBmsSamples = IltdsUtils.getBMSSamplesFromList(IltdsUtils.NONBMS, tissueIds);
      if (bmsSamples.size() > 0 && nonBmsSamples.size() > 0) {
        errs.add(null, new ActionError("iltds.error.rs.cannotmixBMSwithnonBMS", "picklist"));
        saveErrors(request, errs);
      }

      if (ApiFunctions.isEmpty(request.getParameter("requestType"))) {
        errs.add(null, new ActionError("iltds.error.rs.norequesttype"));
        saveErrors(request, errs);
      }

      if (ApiFunctions.isEmpty(request.getParameter("deliverTo"))) {
        errs.add(null, new ActionError("iltds.error.rs.nodeliverto"));
        saveErrors(request, errs);
      }

      if (errs.size() > 0) {
        return mapping.findForward("fail");
      }

      helper.clearSelectedIds();

      //select the appropriate status to update, RNDREQUEST or QCINPROCESS
      //and set the BTX transaction type.
      String newStatus = "";
      if (rf.getRequestType().equals(BTXDetails.BTX_TYPE_PATH_SAMPLE_REQUEST)) {
        newStatus = FormLogic.SMPL_QCINPROCESS;
        btxTransactionType = BTXDetails.BTX_TYPE_PATH_SAMPLE_REQUEST;
        request.removeAttribute("rd");
      }
      else if (rf.getRequestType().equals(BTXDetails.BTX_TYPE_RND_SAMPLE_REQUEST)) {
        newStatus = FormLogic.SMPL_RNDREQUEST;
        btxTransactionType = BTXDetails.BTX_TYPE_RND_SAMPLE_REQUEST;
        request.setAttribute("rd", "rd");
      }
      else {
        throw new ApiException("Invalid transaction type for picklist: " + rf.getRequestType());
      }

      //update the sample statuses and grab their locations for the picklist
      List sampleList = btxSamples.getList();
      PathologyOperationHome home = (PathologyOperationHome) EjbHomes.getHome(PathologyOperationHome.class);
      PathologyOperation pathOp = home.create();
      sampleLocations = pathOp.getSampleLocations(new Vector(sampleList));
      pathOp.updateSampleStatus(new Vector(btxSamples.getList()), newStatus);

      // now that we succeeded, clear the picklist
      helper.getSelectedSamples().removeIds(helper.getSelectedSamples().getSampleIds());
    }

    //Collect the information needed for the real name of <created by>
    //set the information to the request
    String user = securityInfo.getUsername();
    String account = securityInfo.getAccount();
    ListGeneratorHome home = (ListGeneratorHome) EjbHomes.getHome(ListGeneratorHome.class);
    ListGenerator list = home.create();
    String createdByRealName = list.getUserRealName(user, account);
    request.setAttribute("createdBy", createdByRealName);

    String btxDeliverTo = request.getParameter("deliverTo");

    //grab the picklist id
    String btxPickList = IltdsUtils.getNextPicklistID();
    request.setAttribute("picklist", btxPickList);

    //contains samples and all the pertinant info for the next page
    request.setAttribute("samples", sampleLocations);

    // Record Transaction START
    {
      BTXDetailsPickListRequest btxDetails = new BTXDetailsPickListRequest();
      btxDetails.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
      btxDetails.setLoggedInUserSecurityInfo(WebUtils.getSecurityInfo(request));

      btxDetails.setPickList(btxPickList);
      btxDetails.setDeliverTo(btxDeliverTo);
      btxDetails.setBTXType(btxTransactionType);
      btxDetails.setSampleList(btxSamples);
      btxDetails.setTransactionType("iltds_placeholder");
      Btx.perform(btxDetails);

    }
    // END RECORD TRANSACTION

    return (mapping.findForward("success"));
  }
}
