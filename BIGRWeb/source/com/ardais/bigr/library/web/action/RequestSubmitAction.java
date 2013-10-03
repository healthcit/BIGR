package com.ardais.bigr.library.web.action;

import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForward;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiProperties;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.library.SampleViewData;
import com.ardais.bigr.library.btx.BTXDetailsGetSamples;
import com.ardais.bigr.library.web.form.RequestForm;
import com.ardais.bigr.library.web.helper.ResultsHelper;
import com.ardais.bigr.library.web.helper.SampleSelectionHelper;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.web.action.BigrAction;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * This action generates HTML to be e-mailed to the customer service
 * department for the order to be processed or posted, or whatever they choose to do.
 * The e-mail represents the items in the shopping cart (A.K.A. the Hold List).
 */
public class RequestSubmitAction extends BigrAction {

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
    RequestForm rForm = (RequestForm) form;

    String txType = rForm.getTxType();
    if (ApiFunctions.isEmpty(txType)) {
      throw new ApiException("No txType specified in sample order request.");
    }

    // This action may be invoked from either a single-category or multi-category hold list
    // page.  When we get here from a single-category page, then all of the items on the hold
    // list are to be placed in the order request.  However, when we get here from a
    // multi-category hold list, then only the items in the "Ardais Items" category are to be
    // placed on the order request.
    //
    boolean useMultiCategory = !rForm.isUseSingleCategory();

    ResultsHelper helper = ResultsHelper.get(txType, request);
    List itemHelpers;
    synchronized (helper) {
      helper.setProductType(ResultsHelper.PRODUCT_TYPE_HOLD_LIST);

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

      itemHelpers = helper.getHelpers().getSampleHelpers();
    }

    StringBuffer emailBuff = new StringBuffer(8192);
    StringBuffer confirmBuff = new StringBuffer(8192);

    emailBuff.append("<table cellpadding=\"3\" cellspacing=\"0\" border=\"0\">");
    confirmBuff.append("<table cellpadding=\"3\" cellspacing=\"0\" border=\"0\">");
    emailBuff.append("<tr>");
    confirmBuff.append("<tr>");
    emailBuff.append("<td>Account:</td><td>" + Escaper.htmlEscape(securityInfo.getAccount()) + "</td>");
    confirmBuff.append("<td>Account:</td><td>" + Escaper.htmlEscape(securityInfo.getAccount()) + "</td>");
    emailBuff.append("</tr>");
    confirmBuff.append("</tr>");
    emailBuff.append("<tr>");
    confirmBuff.append("<tr>");
    emailBuff.append("<td>User ID:</td><td>" + Escaper.htmlEscape(securityInfo.getUsername()) + "</td>");
    confirmBuff.append("<td>User ID:</td><td>" + Escaper.htmlEscape(securityInfo.getUsername()) + "</td>");
    emailBuff.append("</tr>");
    confirmBuff.append("</tr>");
    emailBuff.append("<tr>");
    confirmBuff.append("<tr>");
    emailBuff.append("<td>Name:</td><td>" + Escaper.htmlEscape(rForm.getName()) + "</td>");
    confirmBuff.append("<td>Name:</td><td>" + Escaper.htmlEscape(rForm.getName()) + "</td>");
    emailBuff.append("</tr>");
    confirmBuff.append("</tr>");
    emailBuff.append("<tr>");
    confirmBuff.append("<tr>");
    emailBuff.append("<td>Phone:</td><td>" + Escaper.htmlEscape(rForm.getPhone()) + "</td>");
    confirmBuff.append("<td>Phone:</td><td>" + Escaper.htmlEscape(rForm.getPhone()) + "</td>");
    emailBuff.append("</tr>");
    confirmBuff.append("</tr>");
    emailBuff.append("<tr>");
    confirmBuff.append("<tr>");
    emailBuff.append("<td>Email:</td><td>" + Escaper.htmlEscape(rForm.getEmail()) + "</td>");
    confirmBuff.append("<td>Email:</td><td>" + Escaper.htmlEscape(rForm.getEmail()) + "</td>");
    emailBuff.append("</tr>");
    confirmBuff.append("</tr>");
    emailBuff.append("<tr>");
    confirmBuff.append("<tr>");
    emailBuff.append("<td colspan=\"2\">Description:</td>");
    confirmBuff.append("<td colspan=\"2\">Description:</td>");
    emailBuff.append("</tr>");
    confirmBuff.append("</tr>");
    emailBuff.append("<tr>");
    confirmBuff.append("<tr>");
    emailBuff.append("<td colspan=\"2\"><div style=\"width: 550px;\">");
    confirmBuff.append("<td colspan=\"2\"><div style=\"width: 550px;\">");
    Escaper.htmlEscapeAndPreserveWhitespace(rForm.getDetails(), emailBuff);
    Escaper.htmlEscapeAndPreserveWhitespace(rForm.getDetails(), confirmBuff);
    emailBuff.append("</div></td>");
    confirmBuff.append("</div></td>");
    emailBuff.append("</tr>");
    confirmBuff.append("</tr>");
    emailBuff.append("</table>");
    confirmBuff.append("</table>");

    emailBuff.append(
      "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#000000\"><tr><td>");
    confirmBuff.append(
      "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#000000\"><tr><td>");
    emailBuff.append("<table cellpadding=\"3\" cellspacing=\"1\" border=\"0\">");
    confirmBuff.append("<table cellpadding=\"3\" cellspacing=\"1\" border=\"0\">");
    emailBuff.append("<tr bgcolor=\"#999999\">");
    confirmBuff.append("<tr bgcolor=\"#999999\">");
    emailBuff.append("<td>Delivery Type</td>");
    confirmBuff.append("<td>Delivery Type</td>");
    emailBuff.append("<td>Amount (ug)</td>");
    confirmBuff.append("<td>Amount (ug)</td>");
    emailBuff.append("<td>Consent ID</td>");
    confirmBuff.append("<td>Consent ID</td>");
    emailBuff.append("<td>Inv. Groups</td>");
    confirmBuff.append("<td>Inv. Groups</td>");
    emailBuff.append("<td>BMS</td>"); // only in email
    emailBuff.append("<td>ASM/Prep</td>");
    confirmBuff.append("<td>ASM/Prep</td>");
    emailBuff.append("<td>Sample ID</td>");
    confirmBuff.append("<td>Sample ID</td>");
    emailBuff.append("<td>Sample Pathology from Ardais Pathology Verification </td>");
    confirmBuff.append("<td>Sample Pathology from Ardais Pathology Verification </td>");
    emailBuff.append("<td>Case Diagnosis from Donor Institution Pathology Report </td>");
    confirmBuff.append("<td>Case Diagnosis from Donor Institution Pathology Report </td>");
    emailBuff.append("<td>Tissue of Origin of Diagnosis / Site of Finding</td>");
    confirmBuff.append("<td>Tissue of Origin of Diagnosis / Site of Finding</td>");
    emailBuff.append("<td>Appearance</td>");
    confirmBuff.append("<td>Appearance</td>");
    emailBuff.append("<td>Verified?</td>");
    confirmBuff.append("<td>Verified?</td>");
    emailBuff.append("</tr>");
    confirmBuff.append("</tr>");

    for (int i = 0; i < itemHelpers.size(); i++) {
      String delivType = "";
      if (rForm.getDelivType() != null) { // in form <sampleid>$<delivType>
        StringTokenizer toker = new StringTokenizer(rForm.getDelivType()[i], "$");
        toker.nextElement(); // consume sample ID
        delivType = (String) toker.nextElement();
      }

      SampleSelectionHelper ssHelper = (SampleSelectionHelper) itemHelpers.get(i);

      String asmOrPrep = ssHelper.isRna() ? ssHelper.getRnaPrep() : ssHelper.getAsmPosition();
      String sampleId = ssHelper.isRna() ? ssHelper.getRnaVialId() : ssHelper.getSampleId();

      emailBuff.append("<tr bgcolor=\"#ffffff\">");
      confirmBuff.append("<tr bgcolor=\"#ffffff\">");
      emailBuff.append("<td>" + Escaper.htmlEscape(delivType) + "</td>");
      confirmBuff.append("<td>" + Escaper.htmlEscape(delivType) + "</td>");
      emailBuff.append("<td>" + Escaper.htmlEscape(ssHelper.getAmountOnHold()) + "</td>");
      confirmBuff.append("<td>" + Escaper.htmlEscape(ssHelper.getAmountOnHold()) + "</td>");
      emailBuff.append("<td>" + Escaper.htmlEscape(ssHelper.getConsentId()) + "</td>");
      confirmBuff.append("<td>" + Escaper.htmlEscape(ssHelper.getConsentId()) + "</td>");
      emailBuff.append("<td>" + Escaper.htmlEscape(ssHelper.getLogicalRepositoryShortNames()) + "</td>");
      confirmBuff.append("<td>" + Escaper.htmlEscape(ssHelper.getLogicalRepositoryShortNames()) + "</td>");
      emailBuff.append("<td>" + Escaper.htmlEscape(ssHelper.getBmsYesNoDisplayText()) + "</td>"); // only in email
      emailBuff.append("<td align=\"center\">" + Escaper.htmlEscape(asmOrPrep) + "</td>");
      confirmBuff.append("<td align=\"center\">" + Escaper.htmlEscape(asmOrPrep) + "</td>");
      emailBuff.append("<td align=\"center\">" + Escaper.htmlEscape(sampleId) + "</td>");
      confirmBuff.append("<td align=\"center\">" + Escaper.htmlEscape(sampleId) + "</td>");
      emailBuff.append("<td>" + Escaper.htmlEscape(ssHelper.getLimsDiagnosis()) + "</td>");
      confirmBuff.append("<td>" + Escaper.htmlEscape(ssHelper.getLimsDiagnosis()) + "</td>");
      emailBuff.append("<td>" + Escaper.htmlEscape(ssHelper.getDdcDiagnosis()) + "</td>");
      confirmBuff.append("<td>" + Escaper.htmlEscape(ssHelper.getDdcDiagnosis()) + "</td>");
      emailBuff.append(
        "<td>"
          + Escaper.htmlEscape(((ssHelper.isVerified())
            ? ssHelper.getTissueOfOrigin() + "/" + ssHelper.getTissueOfFinding()
            : ssHelper.getSampleTissueOfOrigin() + "/" + ssHelper.getAsmTissue()))
          + "</td>");
      confirmBuff.append(
        "<td>"
          + Escaper.htmlEscape(((ssHelper.isVerified())
            ? ssHelper.getTissueOfOrigin() + "/" + ssHelper.getTissueOfFinding()
            : ssHelper.getSampleTissueOfOrigin() + "/" + ssHelper.getAsmTissue()))
          + "</td>");
      emailBuff.append("<td>" + Escaper.htmlEscape(ssHelper.getAppearanceBest()) + "</td>");
      confirmBuff.append("<td>" + Escaper.htmlEscape(ssHelper.getAppearanceBest()) + "</td>");
      emailBuff.append("<td>" + ((ssHelper.isVerified()) ? "Verified" : "&nbsp;") + "</td>");
      confirmBuff.append("<td>" + ((ssHelper.isVerified()) ? "Verified" : "&nbsp;") + "</td>");
      emailBuff.append("</tr>");
      confirmBuff.append("</tr>");
    }

    emailBuff.append("</table></td></tr></table>");
    confirmBuff.append("</table></td></tr></table>");
    
    // MR7170 - do not proceed with transaction if email message cannot be sent.
    if (ApiFunctions.generateEmail(rForm.getEmail(), ApiProperties.getProperty("api.orderrequest.email"), "Order Request", emailBuff.toString())) {
      request.setAttribute("email", confirmBuff.toString());
      return (mapping.findForward("success"));
    }
    else {
      ActionErrors errors = new ActionErrors();
      errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.emailFailed"));
      saveErrors(request, errors);
      return (mapping.getRetryActionForward());
    }
  }
}
