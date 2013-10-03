package com.ardais.bigr.orm.op;

import com.ardais.bigr.orm.beans.AccountSrchSB;
import com.ardais.bigr.orm.beans.AccountSrchSBHome;
import com.ardais.bigr.util.EjbHomes;

public class AddIRBandConsentVersion extends StandardOperation {

  public AddIRBandConsentVersion(
    javax.servlet.http.HttpServletRequest req,
    javax.servlet.http.HttpServletResponse res,
    javax.servlet.ServletContext ctx) {
    super(req, res, ctx);
  }

  public void invoke() throws Exception {
    String strAccId = request.getParameter("accountID").trim();
    String strirbProtocol = request.getParameter("irbAndConsentVer").trim();
    String strconsentVersion = request.getParameter("consentAndIrb").trim();
    String policyId = request.getParameter("policyId").trim();
    String expirationDate = request.getParameter("newExpirationDate").trim();

    AccountSrchSBHome home = (AccountSrchSBHome) EjbHomes.getHome(AccountSrchSBHome.class);
    AccountSrchSB addirbConsentVer = home.create();
    addirbConsentVer.addIRBandConsentVer(strAccId, strirbProtocol, policyId, strconsentVersion, expirationDate);

    StandardOperation op = new IRBConsentStart(request, response, this.servletCtx);
    op.invoke();
  }
}
