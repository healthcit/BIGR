package com.ardais.bigr.orm.op;

import com.ardais.bigr.orm.beans.AccountSrchSB;
import com.ardais.bigr.orm.beans.AccountSrchSBHome;
import com.ardais.bigr.util.EjbHomes;

public class PrepareConsentTexttoIRB extends com.ardais.bigr.orm.op.StandardOperation {

  public PrepareConsentTexttoIRB(
    javax.servlet.http.HttpServletRequest req,
    javax.servlet.http.HttpServletResponse res,
    javax.servlet.ServletContext ctx) {
    super(req, res, ctx);
  }

  public void invoke() throws Exception, java.io.IOException {
    String irbProtocolId = (String) request.getParameter("irbProtocol").trim();
    String consentVersionId = (String) request.getParameter("consentIrb").trim();

    // get the current text for the {consentVersionId}  
    AccountSrchSBHome home = (AccountSrchSBHome) EjbHomes.getHome(AccountSrchSBHome.class);
    AccountSrchSB addConsentVer = home.create();
    String consentText = addConsentVer.getConsentText(consentVersionId);

    // set the text parameter to pass in...
    request.setAttribute("consentText", consentText);
    request.setAttribute("irbProtocolID", irbProtocolId);
    request.setAttribute("consentID", consentVersionId);

    servletCtx.getRequestDispatcher("/hiddenJsps/orm/AccountManagement/consentText.jsp").forward(
      request,
      response);

  }
}
