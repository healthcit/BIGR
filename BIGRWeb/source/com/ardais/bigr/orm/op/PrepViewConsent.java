/*
 * Created on Oct 8, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ardais.bigr.orm.op;

import java.util.*;
import com.ardais.bigr.orm.beans.*;
import com.ardais.bigr.util.EjbHomes;

/**
 * @author sthomashow
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PrepViewConsent extends com.ardais.bigr.orm.op.StandardOperation {
/**
 * PrepareConsentTexttoIRB constructor comment.
 * @param req javax.servlet.http.HttpServletRequest
 * @param res javax.servlet.http.HttpServletResponse
 * @param ctx javax.servlet.ServletContext
 */
public PrepViewConsent (javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse res, javax.servlet.ServletContext ctx) {
  super(req, res, ctx);
}
/**
 * Insert the method's description here.
 * Creation date: (10/06/2003)
 */
public void invoke() throws Exception, java.io.IOException {

 
  String strConsentID = (String) request.getParameter("consentID").trim();  

  // get the current text for the {account, irb, consent}  
  AccountSrchSBHome home = (AccountSrchSBHome) EjbHomes.getHome(AccountSrchSBHome.class);
  AccountSrchSB addConsentVer = home.create();
  String consentText = addConsentVer.getConsentTextbyConsentID(strConsentID);

  // set the text parameter to pass in...
  request.setAttribute("consentText", consentText);

  servletCtx.getRequestDispatcher("/hiddenJsps/orm/AccountManagement/viewonlyConsentText.jsp").forward(request, response);
  
  }
}
