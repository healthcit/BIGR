package com.ardais.bigr.orm.op;

import com.ardais.bigr.orm.beans.*;
import com.ardais.bigr.util.EjbHomes;
/**
 * Insert the type's description here.
 * Creation date: (5/31/01 1:29:38 PM)
 * @author: Jake Thompson
 */
public class GetPasswordQuestion extends StandardOperation {


/**
 * GetPasswordQuestion constructor comment.
 * @param req javax.servlet.http.HttpServletRequest
 * @param res javax.servlet.http.HttpServletResponse
 * @param ctx javax.servlet.ServletContext
 */
public GetPasswordQuestion(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse res, javax.servlet.ServletContext ctx) {
	super(req, res, ctx);
}
/**
 * Insert the method's description here.
 * Creation date: (5/31/01 1:29:38 PM)
 */
public void invoke() throws Exception, java.io.IOException {
	
	String accountID = request.getParameter("AccountID");
	String userID = request.getParameter("UserID");
	if ((userID != null) && (userID.trim().length() > 0)) {
		if ((accountID != null) && (accountID.trim().length() > 0)) {
      OrmUserManagementHome home = (OrmUserManagementHome) EjbHomes.getHome(OrmUserManagementHome.class);
      OrmUserManagement forgotPass = home.create();
			String verifyQuestion = forgotPass.getVerificationQuestion(userID, accountID);

			if ((verifyQuestion != null) && (verifyQuestion.trim().length() > 0)) {
				request.setAttribute("VerificationQuestion", verifyQuestion);
				request.setAttribute("UserID",userID);
				request.setAttribute("AccountID",accountID);
	
				servletCtx
					.getRequestDispatcher("/hiddenJsps/orm/ForgotPassword.jsp")
					.forward(request, response);
				
			} else {
				retry("<b>Since you have no verification question stored in your profile, please contact HealthCare IT Corporation Customer Service to gain access.</b>","/hiddenJsps/orm/ForgotPasswordUser.jsp");
				
			}
		}
		else
		{
	        retry("<b>UserID and AccountID are Required Fields</b>","/hiddenJsps/orm/ForgotPasswordUser.jsp");
		}
	}
	else
	{
	    retry("<b>UserID and AccountID are Required Fields</b>","/hiddenJsps/orm/ForgotPasswordUser.jsp");
	}

	
	
	}
		/**
 * Insert the method's description here.
 * Creation date: (3/8/01 4:55:17 PM)
 */
public void retry(String myError, String page) 
{
	try {
		request.setAttribute("myError", myError);
		servletCtx.getRequestDispatcher(page).forward(request, response);
	} catch (Exception e) {
		e.printStackTrace();
	}
}
}
