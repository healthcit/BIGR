package com.ardais.bigr.orm.op;

import java.util.Date;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.orm.beans.AccountSrchSB;
import com.ardais.bigr.orm.beans.AccountSrchSBHome;
import com.ardais.bigr.util.EjbHomes;

public class AddConsentVertoIRB extends StandardOperation {

  public AddConsentVertoIRB(
    javax.servlet.http.HttpServletRequest req,
    javax.servlet.http.HttpServletResponse res,
    javax.servlet.ServletContext ctx) {
    super(req, res, ctx);
  }

  public void invoke() throws Exception, java.io.IOException {
    
    String expirationDate = null;
    
    String irbProtocolID =
      ApiFunctions.safeString(ApiFunctions.safeTrim(request.getParameter("irbProtocolID")));
    String consentVersion =
      ApiFunctions.safeString(ApiFunctions.safeTrim(request.getParameter(irbProtocolID)));
    String expirationDateInput = irbProtocolID + "ExpirationDate";
    if (request.getParameterValues("expirationDate") != null) {
     expirationDate = ApiFunctions.safeString(ApiFunctions.safeTrim(request.getParameter(expirationDateInput)));
     }
     

    request.setAttribute("irbProtocolID", irbProtocolID);
    request.setAttribute("consentVersion", consentVersion);
    if (expirationDate != null)
      request.setAttribute("expirationDate", expirationDate);

    if (ApiFunctions.isEmpty(consentVersion)) {
      request.setAttribute(
        "myError",
        "<font color=red>You Cannot Add Empty Consent Version.</font>");
    } 
    else {
          AccountSrchSBHome home = (AccountSrchSBHome) EjbHomes.getHome(AccountSrchSBHome.class);
          AccountSrchSB addConsentVer = home.create();
          addConsentVer.addConsentVertoIrb(irbProtocolID, consentVersion, expirationDate);
        }

    com.ardais.bigr.orm.op.StandardOperation op =
      new IRBConsentStart(request, response, this.servletCtx);
    op.invoke();
  }
}
