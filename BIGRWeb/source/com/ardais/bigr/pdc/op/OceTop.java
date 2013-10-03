package com.ardais.bigr.pdc.op;

import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 */
public class OceTop extends StandardOperation {

	/**
	 * Creates an <code>OceTop</code>.
	 *
	 * @param  req  the HTTP servlet request
	 * @param  res  the HTTP servlet response
	 * @param  ctx  the servlet context
	 */
	public OceTop(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
		super(req, res, ctx);
	}

    public void invoke() {
		forward("/hiddenJsps/ddc/Other_Top.jsp");
    }
    public void preInvoke(Collection fileItems) {
      //method for potential file attachment, do nothing for now ;
      
    }
}
