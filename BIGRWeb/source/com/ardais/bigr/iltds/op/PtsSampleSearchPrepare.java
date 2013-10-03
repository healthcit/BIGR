package com.ardais.bigr.iltds.op;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.assistants.LineItemDataBean;
import com.ardais.bigr.iltds.beans.PtsOperation;
import com.ardais.bigr.iltds.beans.PtsOperationHome;
import com.ardais.bigr.iltds.helpers.JspHelper;
import com.ardais.bigr.iltds.helpers.LineItemHelper;
import com.ardais.bigr.iltds.helpers.ProjectHelper;
import com.ardais.bigr.util.EjbHomes;

/**
 */
public class PtsSampleSearchPrepare extends StandardOperation {
	/**
	 * Creates the SampleSearchPrepare op.
	 *
	 * @param  req  the HttpServletRequest
	 * @param  res  the HttpServletResponse
	 * @param  ctx  the ServletContext
	 */
	public PtsSampleSearchPrepare(
		HttpServletRequest req,
		HttpServletResponse res,
		ServletContext ctx) {
		super(req, res, ctx);
	}
	/**
	 * temporary only for mockup
	 */
	public LineItemHelper addLineItem(String projectId, String liID) {
		LineItemDataBean liBean;
		liBean = new LineItemDataBean();

		try {
      PtsOperationHome home = (PtsOperationHome) EjbHomes.getHome(PtsOperationHome.class);
      PtsOperation ptsOp = home.create();
			liBean = ptsOp.getLineItem(liID);
			return new LineItemHelper(liBean);

		} catch (Exception e) {
			return null;
		}
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

		try {
      PtsOperationHome home = (PtsOperationHome) EjbHomes.getHome(PtsOperationHome.class);
      PtsOperation ptsOp = home.create();
			ProjectHelper helper =
				new ProjectHelper(ptsOp.getProject(projectId));
			helper.addLineItem(
				new LineItemHelper(ptsOp.getLineItem(lineItemId)));
			String msg =
				(String) request.getAttribute(ProjectHelper.ID_MESSAGE);
			if (msg != null) {
				helper.getMessageHelper().addMessage(msg);
			}
			request.setAttribute(JspHelper.ID_HELPER, helper);
		} catch (javax.ejb.CreateException e) {
			throw new ApiException(e);
    } catch (javax.naming.NamingException e) {
      throw new ApiException(e);
    } catch (ClassNotFoundException e) {
      throw new ApiException(e);
		}

		servletCtx.getRequestDispatcher("/hiddenJsps/iltds/pts/SampleSearch.jsp").forward(
			request,
			response);
	}
}
