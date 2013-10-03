package com.ardais.bigr.iltds.op;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.iltds.beans.ListGenerator;
import com.ardais.bigr.iltds.beans.ListGeneratorHome;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.PolicyUtils;
import com.ardais.bigr.util.WebUtils;

/**
 * Start a Consent Operation depending on what 'op' is collected.
 */
public class ConsentStart extends com.ardais.bigr.iltds.op.StandardOperation {

  public ConsentStart(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
    super(req, res, ctx);
  }

  public void invoke() throws Exception {
    String myStartingOp = request.getParameter("op");

    // Currently 3 options: Initial, Initial Verify, or Unlinked

    if (myStartingOp.equals("ConsentStartVerify")) {
      servletCtx.getRequestDispatcher("/hiddenJsps/iltds/consent/consentVerify.jsp").forward(
        request,
        response);
    }
    else {
      String myLinked = request.getParameter("linked");
      SecurityInfo secInfo = WebUtils.getSecurityInfo(request);
      String accountId = secInfo.getAccount();

      if ("true".equals(myLinked)) {
        // Look up consent versions for account
        ListGeneratorHome home = (ListGeneratorHome) EjbHomes.getHome(ListGeneratorHome.class);
        ListGenerator list = home.create();
        List consentChoices = list.findActiveConsentVersions(accountId);
        request.setAttribute("consentVersionChoices", consentChoices);
        request.setAttribute("policyMap", PolicyUtils.getAllPolicyMap());
      }
      else if ("false".equals(myLinked)) {
        // Look up policy choices for account
        List policyChoices = PolicyUtils.getPoliciesByAccountId(accountId);
        request.setAttribute("policyChoices", policyChoices);
      }

      servletCtx.getRequestDispatcher("/hiddenJsps/iltds/consent/createCaseStart.jsp").forward(
        request,
        response);
    }
  }
}
