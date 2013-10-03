package com.ardais.bigr.iltds.op;

import java.sql.Timestamp;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.iltds.beans.ConsentAccessBean;
import com.ardais.bigr.iltds.beans.ConsentKey;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.WebUtils;

public class ConsentVerifyInsert extends com.ardais.bigr.iltds.op.StandardOperation {

  public ConsentVerifyInsert(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
    super(req, res, ctx);
  }

  public void invoke() throws Exception {
    String myConsentId = request.getParameter("consentId");
    String myBankable = (request.getParameter("bankable").equalsIgnoreCase("Yes") ? "Y" : "N");

    Timestamp myTimestamp = new Timestamp(System.currentTimeMillis());

    SecurityInfo secInfo = WebUtils.getSecurityInfo(request);
    String userId = secInfo.getUsername();

    ConsentAccessBean myConsent = new ConsentAccessBean(new ConsentKey(myConsentId));
    myConsent.setForm_verified_by_staff_id(userId);
    myConsent.setForm_verified_datetime(myTimestamp);
    myConsent.setBankable_ind(myBankable);
    myConsent.commitCopyHelper();
                
    //give the samples in this case a sales status of GENERELEASED if appropriate
    IltdsUtils.applyPolicyToSamplesInCase(myConsent, IltdsUtils.APPLY_POLICY_FOR_SALES_STATUS);

    String confirmationMessage = "Case ID " + myConsentId + " has been verified.";
    request.setAttribute("confirmationMessage", confirmationMessage);

    // Pass the reset parameter to the page so that the user will be sent back to
    // a clean data-entry slate.
    //
    servletCtx.getRequestDispatcher("/iltds/Dispatch?op=ConsentStartVerify&resetForm=true").forward(
      request,
      response);
  }

  private void retry(String myError) throws Exception {
    request.setAttribute("myError", myError);
    servletCtx.getRequestDispatcher("/iltds/Dispatch?op=ConsentStartVerify").forward(
      request,
      response);
  }
}
