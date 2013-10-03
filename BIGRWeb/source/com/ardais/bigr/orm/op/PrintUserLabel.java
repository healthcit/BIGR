package com.ardais.bigr.orm.op;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;

/**
 * @author Nagraj
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class PrintUserLabel extends StandardOperation {

	/**
	 * Constructor for PrintUserLabel.
	 * @param req
	 * @param res
	 * @param ctx
	 */
	public PrintUserLabel(
		HttpServletRequest req,
		HttpServletResponse res,
		ServletContext ctx) {
		super(req, res, ctx);
	}

	/**
	 * @see com.ardais.bigr.orm.op.StandardOperation#invoke()
	 */
	public void invoke() throws IOException, Exception {
		String userid = request.getParameter("userid");
		String message = null;
		if (ApiFunctions.isEmpty(userid)) {
			message = "User ID must not be null or empty";
		} else {
			try {
				ApiFunctions.printUserLabel(userid);
				message = "Label for User ID \"" + userid + "\" printed.";
			} catch (Exception ex) {
				message = "Exception occured while printing Label for User ID: " + userid;
				ApiLogger.log("Exception while printing label for User Id: " + userid, ex);
			}
	    }
	    request.setAttribute("message", message);
		servletCtx.getRequestDispatcher("/hiddenJsps/orm/PrintUserLabel/PrintConfirmation.jsp").
		           		   forward(request, response);
	}

}
