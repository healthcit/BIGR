package com.ardais.bigr.iltds.op;

/**
 * Insert the type's description here.
 * Creation date: (10/15/02 11:33:55 AM)
 * @author: John Esielionis
 */
public class BoxScanSubmitted extends StandardOperation {

	/**
	 * BoxScanSubmitted constructor comment.
	 * @param req javax.servlet.http.HttpServletRequest
	 * @param res javax.servlet.http.HttpServletResponse
	 * @param ctx javax.servlet.ServletContext
	 */
	public BoxScanSubmitted(
		javax.servlet.http.HttpServletRequest req,
		javax.servlet.http.HttpServletResponse res,
		javax.servlet.ServletContext ctx) {
		super(req, res, ctx);
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (10/15/02 11:33:55 AM)
	 */
	public void invoke() throws Exception, java.io.IOException {

		String pageName = "/hiddenJsps/iltds/storage/boxScanSubmitted.jsp";
		servletCtx.getRequestDispatcher(pageName).forward(request, response);
	}
}
