package com.ardais.bigr.pdc.op;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.pdc.helpers.PdcUtils;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Collection;

/**
 * <code>StandardOperation</code> is the base class that all ops
 * extend.
 */
public abstract class StandardOperation {
	protected HttpServletRequest request = null;
	protected HttpServletResponse response = null;
	protected ServletContext servletCtx = null;
/**
 * Create a <code>StandardOperation</code>, initializing it with
 * servlet context information.
 */
public StandardOperation(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
    super();
    request = req;
    response = res;
    servletCtx = ctx;
}
/**
 * Forwards control to the JSP specified by the input <code>String</code>.
 *
 * @param  jsp  the JSP to forward to
 */
protected void forward(String jsp) {
	try {
		servletCtx.getRequestDispatcher(jsp).forward(request, response);
	}
	catch (IOException e) {
		throw new ApiException(e);
	}
	catch (ServletException e) {
		throw new ApiException(e);
	}
}
/**
 * The <code>invoke</code> method is where the work is performed
 * in the op subclass.
 */
public abstract void invoke();

/**
 * The <code>preInvoke</code> method is where the data in multpart form is parsed
 * in the op subclass
 */
public abstract void preInvoke(Collection fileItems);

  /** Stores the last used donor, case, and sample ids in the session for use 
   * in subsequent operations.  Currently used by DDC and Sample Intake transactions.
   * @param donorId  the donor id to store
   * @param caseId  the case id to store
   * @param sampleeId  the sample id to store
   * @param ignoreNulls  a flag to indicate if a null value should be ignored for an id
   *                      or if the value should be set to null
   */
protected void storeLastUsedInfo(String donorId, String caseId, String sampleId, boolean ignoreNulls) {
  PdcUtils.storeLastUsedDonorCaseAndSample(request, donorId, caseId, sampleId, ignoreNulls);
}
}
