/*
 * Created on Oct 3, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ardais.bigr.orm.op;

/**
 * @author sthomashow
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
import com.ardais.bigr.orm.beans.AccountSrchSB;
import com.ardais.bigr.orm.beans.AccountSrchSBHome;
import com.ardais.bigr.util.EjbHomes;
public class WriteConsentTexttoIRB extends StandardOperation {
/**
 * AddConsentTexttoIRB constructor comment.
 * @param req javax.servlet.http.HttpServletRequest
 * @param res javax.servlet.http.HttpServletResponse
 * @param ctx javax.servlet.ServletContext
 */
public WriteConsentTexttoIRB(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse res, javax.servlet.ServletContext ctx) {
  super(req, res, ctx);
}
/**
 * Insert the method's description here.
 * Creation date: (12/14/01 2:43:45 PM)
 */
public void invoke() throws Exception, java.io.IOException {
  
  String strConsentText = (String) request.getParameter("consentText");
  String strIrbProtocol = (String) request.getParameter("irbProtocolID").trim();
  String strConsentVersion = (String) request.getParameter("consentID").trim();
  String userID = (String)request.getSession(false).getAttribute("user");  

  if ( (strIrbProtocol != null) && (strConsentText != null) && (strConsentVersion != null))
    {    
      AccountSrchSBHome home = (AccountSrchSBHome) EjbHomes.getHome(AccountSrchSBHome.class);
      AccountSrchSB addConsentVer = home.create();
      addConsentVer.setConsentText(strIrbProtocol, strConsentVersion, strConsentText, userID);

      request.setAttribute("consentText", strConsentText);
      request.setAttribute("irbProtocolID", strIrbProtocol);
      request.setAttribute("consentID", strConsentVersion);
      request.setAttribute("myError", "<font color=red>The Consent Text was successfully updated </font>");
    }
  else
  {
    request.setAttribute("myError","<font color=red>You Cannot Add Empty Consent Text </font>");

  }

  servletCtx.getRequestDispatcher("/hiddenJsps/orm/AccountManagement/consentText.jsp").forward(request, response);

  }
}
