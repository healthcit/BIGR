package com.ardais.bigr.orm.op;

import com.ardais.bigr.orm.beans.AccountSrchSB;
import com.ardais.bigr.orm.beans.AccountSrchSBHome;
import com.ardais.bigr.util.EjbHomes;
/**
 * Insert the type's description here.
 * Creation date: (12/17/01 5:04:02 PM)
 * @author: Jake Thompson
 */
public class SaveIRBConsentVerRelation extends StandardOperation {
/**
 * SaveIRBConsentVerRelation constructor comment.
 * @param req javax.servlet.http.HttpServletRequest
 * @param res javax.servlet.http.HttpServletResponse
 * @param ctx javax.servlet.ServletContext
 */
public SaveIRBConsentVerRelation(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse res, javax.servlet.ServletContext ctx) {
	super(req, res, ctx);
}
/**
 * Insert the method's description here.
 * Creation date: (12/17/01 5:04:02 PM)
 */
public void invoke() throws Exception, java.io.IOException {

  String[] expirationDate = null;
  
	String accountID = request.getParameter("accountID");
	String[] activeConsentVer = request.getParameterValues("consentVersion");
	String userID = (String)request.getSession(false).getAttribute("user");
  String[] consentVersionId = request.getParameterValues("consentVersionId");
  if (request.getParameterValues("expirationDate") != null) {
    expirationDate = request.getParameterValues("expirationDate");
    }
  
  
	if((activeConsentVer != null) && (activeConsentVer.length > 0))
	{
    AccountSrchSBHome home = (AccountSrchSBHome) EjbHomes.getHome(AccountSrchSBHome.class);
    AccountSrchSB accountSrch = home.create();
    accountSrch.saveActiveConsentVer(accountID,activeConsentVer,userID);
    accountSrch.saveConsentExpirationDate(accountID,consentVersionId,expirationDate,userID);
	}
	else
	{
		request.setAttribute("myError","<font color=red>Please make at least one Consent Version Active</font>");
	}
	
	com.ardais.bigr.orm.op.StandardOperation op = new IRBConsentStart(request,response,this.servletCtx);
	op.invoke();
	
	}
}
