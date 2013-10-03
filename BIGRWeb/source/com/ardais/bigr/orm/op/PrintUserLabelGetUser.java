package com.ardais.bigr.orm.op;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.orm.beans.OrmUserManagement;
import com.ardais.bigr.orm.beans.OrmUserManagementHome;
import com.ardais.bigr.util.EjbHomes;

/**
 * @author Nagraj
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class PrintUserLabelGetUser extends StandardOperation {

	/**
	 * Constructor for PrintUserLabelGetUser.
	 * @param req
	 * @param res
	 * @param ctx
	 */
	public PrintUserLabelGetUser(
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
		if (ApiFunctions.isEmpty(userid)) {
      OrmUserManagementHome home = (OrmUserManagementHome) EjbHomes.getHome(OrmUserManagementHome.class);
      OrmUserManagement bean = home.create();
			List users = bean.getAllUserIds();
			request.setAttribute("users", users);
			servletCtx.getRequestDispatcher("/hiddenJsps/orm/PrintUserLabel/GetUserLabel.jsp").
		               forward(request, response);
				
			
		}else {
      OrmUserManagementHome home = (OrmUserManagementHome) EjbHomes.getHome(OrmUserManagementHome.class);
      OrmUserManagement bean = home.create();
			if (!bean.isUserExisting(userid)) {
				request.setAttribute("message", "Invalid User ID:" + userid);
				servletCtx.getRequestDispatcher("/hiddenJsps/orm/PrintUserLabel/GetUser.jsp").
		           	forward(request, response);
			} else {
				List users = new ArrayList();
				users.add(userid);
				request.setAttribute("users", users);
				servletCtx.getRequestDispatcher("/hiddenJsps/orm/PrintUserLabel/GetUserLabel.jsp").
		           		   forward(request, response);
				
			}
			
		}
	}

}
