package com.ardais.bigr.iltds.op;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.iltds.helpers.JspHelper;
import com.ardais.bigr.iltds.helpers.ProjectHelper;

/**
 * <code>PtsProjectEditPrepare</code> prepares the JSP that allows
 * a new project to be created.  It is the first op in the
 * "New Project" transaction.  There are no input parameters to
 * this op.  Note that an existing project is edited via the
 * "Manage Project" transaction.
 *
 * @see PtsProjectManagePrepare
 */
public class PtsProjectEditPrepare extends StandardOperation {
	/**
	 * Creates the PtsProjectEditPrepare op.
	 *
	 * @param  req  the HttpServletRequest
	 * @param  res  the HttpServletResponse
	 * @param  ctx  the ServletContext
	 */
	public PtsProjectEditPrepare(
		HttpServletRequest req,
		HttpServletResponse res,
		ServletContext ctx) {
		super(req, res, ctx);
	}
	/**
	 * Forward to the JSP that allows entering a new project.
	 */
	public void invoke() throws IOException, ServletException {
		request.setAttribute(JspHelper.ID_HELPER, new ProjectHelper());
		servletCtx.getRequestDispatcher("/hiddenJsps/iltds/pts/ProjectEdit.jsp").forward(
			request,
			response);
	}
}
