package com.ardais.bigr.pdc.op;

import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.pdc.helpers.SelectDonorHelper;

/**
 * <code>SelectDonorPrepare</code> prepares the JSP that allows
 * searching for donors.  It is the first op in many transactions
 * that require a donor (Ardaid ID) upon which to operate.
 *
 * HttpServletRequest parameters:
 * <ul>
 * <li>
 *	JspHelper.ID_CONTEXT (required) - Context for the search, i.e. which 
 *		transaction will be initiated after the search.  Value must be one 
 *		of the JspHelper.SEARCH_CONTEXT_* constants.
 * </li>
 * </ul>
 */
public class SelectDonorPrepare extends StandardOperation {
/**
 * Create a new <code>SelectDonorPrepare</code>.
 *
 * @param  req  the HTTP request
 * @param  res  the HTTP response
 * @param  ctx  the servlet context
 */
public SelectDonorPrepare(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
	super(req, res, ctx);
}
/**
 * Set the context request attributes based on the context parameter,
 * and forward to the search JSP.
 */
public void invoke() {
	SelectDonorHelper helper = (SelectDonorHelper)request.getAttribute("helper");
	if (helper != null) {
		try {
		    forward("/hiddenJsps/ddc/SelectDonor.jsp");
		}
		catch (Exception e) {
			ApiFunctions.throwAsRuntimeException(e);
		}
	}

	else {
		helper = new SelectDonorHelper(request);

		// Forward to the search page.
		try {
			request.setAttribute("helper", helper);
		    forward("/hiddenJsps/ddc/SelectDonor.jsp");
		}
		catch (Exception e) {
			ApiFunctions.throwAsRuntimeException(e);
		}
	}	
}
public void preInvoke(Collection fileItems) {
  //method for potential file attachment, do nothing for now ;
  
}
}
