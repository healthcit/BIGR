package com.ardais.bigr.orm.op;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Nagraj
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class PrintUserLabelStart extends StandardOperation {

	/**
	 * Constructor for PrintUserLabelStart.
	 * @param req
	 * @param res
	 * @param ctx
	 */
	public PrintUserLabelStart(
		HttpServletRequest req,
		HttpServletResponse res,
		ServletContext ctx) {
		super(req, res, ctx);
	}

	/**
	 * @see com.ardais.bigr.orm.op.StandardOperation#invoke()
	 */
	public void invoke() throws IOException, Exception {
		servletCtx.getRequestDispatcher("/hiddenJsps/orm/PrintUserLabel/GetUser.jsp").
		           forward(request, response);
	}

}
