package com.ardais.bigr.library.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForward;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.es.beans.ArdaisaccountKey;
import com.ardais.bigr.es.beans.ArdaisuserAccessBean;
import com.ardais.bigr.es.beans.ArdaisuserKey;
import com.ardais.bigr.library.SampleViewData;
import com.ardais.bigr.library.btx.BTXDetailsGetSamples;
import com.ardais.bigr.library.web.form.RequestForm;
import com.ardais.bigr.library.web.helper.ResultsHelper;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.web.action.BigrAction;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * This is for requesting items to be ordered from Ardais.
 */
public class RequestConfirmAction extends BigrAction {

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

    ArdaisuserAccessBean user =
      new ArdaisuserAccessBean(
        new ArdaisuserKey(
          securityInfo.getUsername(),
          new ArdaisaccountKey(securityInfo.getAccount())));

    RequestForm rForm = (RequestForm) form;
    rForm.setEmail(user.getUser_email_address());
    rForm.setName(user.getUser_firstname() + " " + user.getUser_lastname());
    String phoneNumber = user.getUser_phone_number();
    String phoneExt = user.getUser_phone_ext();
    if (!ApiFunctions.isEmpty(phoneExt)) {
      phoneNumber += " x" + phoneExt;
    }
    rForm.setPhone(phoneNumber);
    saveForm(mapping, rForm, request);

    // Create the results helper, and indicate that tissue is to be displayed.
    String txType = rForm.getTxType();

    // This action may be invoked from either a single-category or multi-category hold list
    // page.  When we get here from a single-category page, then all of the items on the hold
    // list are to be placed in the order request.  However, when we get here from a
    // multi-category hold list, then only the items in the "Ardais Items" category are to be
    // placed on the order request.
    //
    boolean useMultiCategory = !rForm.isUseSingleCategory();

    boolean hasData;

    ResultsHelper helper = ResultsHelper.get(txType, request);
    synchronized (helper) {
      helper.setProductType(ResultsHelper.PRODUCT_TYPE_HOLD_LIST);
      hasData = helper.hasData();

      BTXDetailsGetSamples btxDetails = new BTXDetailsGetSamples();

      btxDetails.setLoggedInUserSecurityInfo(securityInfo);
      int prodCode = ColumnPermissions.PROD_GENERIC;
      int txCode = ResultsHelper.getTxBits(txType);
      int scrnCode = ColumnPermissions.SCRN_CONFIRM_REQ;
      btxDetails.setViewParams(new ViewParams(securityInfo, prodCode, scrnCode, txCode));
      if (useMultiCategory) {
        btxDetails.setCategoriesToDetermine(BTXDetailsGetSamples.HOLD_CATEGORY_ARDAIS_ONLY);
      }

      helper.updateHelpers(btxDetails);
      
      if (useMultiCategory) {
        // Tell the viewData to operate on a specific category (Ardais items) rather than
        // everything on the hold list.
        SampleViewData viewData = helper.getHelpers();
        viewData.setCategory(BTXDetailsGetSamples.HOLD_CATEGORY_ARDAIS);
      }
    }

    if (!hasData) {
      ActionErrors errs = new ActionErrors();
      errs.add("noitemsonhold", new ActionError("library.ss.confirm.noitemsonhold"));
      saveErrors(request, errs);
      return mapping.findForward("fail");
    }

    return (mapping.findForward("success"));
  }

  private void saveForm(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request) {

    HttpSession session = request.getSession();
    String scope = mapping.getScope();
    if ("session".equalsIgnoreCase(scope)) {
      session.setAttribute(mapping.getName(), form);
    }
    else {
      request.setAttribute(mapping.getName(), form);
    }
  }

}
