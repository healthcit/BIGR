package com.ardais.bigr.orm.op;

import java.util.List;
import java.util.Vector;

import com.ardais.bigr.orm.beans.AccountSrchSB;
import com.ardais.bigr.orm.beans.AccountSrchSBHome;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.security.WebSecurityManager;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.PolicyUtils;

public class IRBConsentStart extends com.ardais.bigr.orm.op.StandardOperation {

  public IRBConsentStart(
    javax.servlet.http.HttpServletRequest req,
    javax.servlet.http.HttpServletResponse res,
    javax.servlet.ServletContext ctx) {
    super(req, res, ctx);
  }

  public void invoke() throws Exception {

    //if the user is not authorized to edit accounts, return with an error
    boolean canEditAccounts = WebSecurityManager.hasPrivilege(request.getSession(), SecurityInfo.PRIV_ORM_ACCOUNT_MANAGEMENT);
    if (!canEditAccounts) {
      request.setAttribute("myError", "You are not authorized to perform account management");
      servletCtx.getRequestDispatcher("/orm/Dispatch?op=AccountSrch").forward(request, response);
    }
    
    String accountId = request.getParameter("accountID").trim();

    AccountSrchSBHome home = (AccountSrchSBHome) EjbHomes.getHome(AccountSrchSBHome.class);
    AccountSrchSB irbConsent = home.create();
    Vector irbConsentList = (Vector) irbConsent.getIrbConsentVer(accountId);
    request.setAttribute("IrbConsentList", irbConsentList);

    List policyChoices = PolicyUtils.getPoliciesByAccountId(accountId, true, true, false);
    request.setAttribute("policyChoices", policyChoices);

    servletCtx.getRequestDispatcher("/hiddenJsps/orm/AccountManagement/consentVer.jsp").forward(
      request,
      response);
  }
}
