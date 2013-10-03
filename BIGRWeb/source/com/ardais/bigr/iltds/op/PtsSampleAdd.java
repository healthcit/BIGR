package com.ardais.bigr.iltds.op;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.beans.PtsOperation;
import com.ardais.bigr.iltds.beans.PtsOperationHome;
import com.ardais.bigr.iltds.helpers.JspHelper;
import com.ardais.bigr.iltds.helpers.LineItemHelper;
import com.ardais.bigr.iltds.helpers.ProjectHelper;
import com.ardais.bigr.util.EjbHomes;

/**
 * <code>SampleSelect</code> is used to select samples
 * for line items for a project.
 */
public class PtsSampleAdd extends StandardOperation {
	/**
	 * Creates a SampleSelect op.
	 *
	 * @param  req  the HttpServletRequest
	 * @param  res  the HttpServletResponse
	 * @param  ctx  the ServletContext
	 */
	public PtsSampleAdd(
		HttpServletRequest req,
		HttpServletResponse res,
		ServletContext ctx) {
		super(req, res, ctx);
	}
	/**
	 */
	public void invoke() throws IOException, ServletException, ApiException {
		String projectId = request.getParameter(ProjectHelper.ID_PROJECT_ID);
		if (projectId == null) {
			throw new ServletException(
				"Required parameter '"
					+ ProjectHelper.ID_PROJECT_ID
					+ "' is not present.");
		}

		String lineItemId = request.getParameter(LineItemHelper.ID_LINEITEM_ID);
		if (lineItemId == null) {
			throw new ServletException(
				"Required parameter '"
					+ LineItemHelper.ID_LINEITEM_ID
					+ "' is not present.");
		}

		String samples[] = request.getParameterValues("sample");
		if ((samples == null) || (samples.length == 0)) {
			request.setAttribute(
				JspHelper.ID_MESSAGE,
				"No samples were selected.");
		} else {
			try {
        PtsOperationHome home = (PtsOperationHome) EjbHomes.getHome(PtsOperationHome.class);
        PtsOperation ptsOp = home.create();
				ptsOp.addSamplesToProject(samples, lineItemId, projectId);
			} catch (javax.ejb.CreateException e) {
				throw new ApiException(e);
      } catch (javax.naming.NamingException e) {
        throw new ApiException(e);
      } catch (ClassNotFoundException e) {
        throw new ApiException(e);
			}
		}

		(new PtsLineItemListViewPrepare(request, response, servletCtx))
			.invoke();
	}
}
