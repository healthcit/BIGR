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
import com.ardais.bigr.iltds.helpers.ProjectHelper;
import com.ardais.bigr.iltds.helpers.WorkOrderHelper;
import com.ardais.bigr.util.EjbHomes;

/**
 * Used to create a new work order or edit a work order.
 * The following are the input HTTP request parameters:
 * <ul>
 * <li>
 *	WorkOrderHelper.ID_WORKORDER_ID (optional) - The id of
 *		the work order to edit.  If this is specified, then
 *		the corresponding work order is found and used to
 *		populate the JSP.  If this is not specified then
 *		it is assumed that a new work order is being created.
 * </li>
 * <li>
 *	ProjectHelper.ID_PROJECT_ID (required) - The id of
 *		the project to which the work order is or should be
 *		associated.
 * </li>
 * </ul>
 * After looking up the existing work order, if necessary,
 * this op will instantiate and populate a <code>WorkOrderHelper</code>
 * with the existing work order values, set request attribute 
 * <code>JspHelper.ID_HELPER</code>, to the helper, and forward to a JSP
 * to allow the user to edit the work order.
 */
public class PtsWorkOrderEditPrepare extends StandardOperation {
	/**
	 * Creates the WorkOrderEditPrepare op.
	 *
	 * @param  req  the HttpServletRequest
	 * @param  res  the HttpServletResponse
	 * @param  ctx  the ServletContext
	 */
	public PtsWorkOrderEditPrepare(
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

		WorkOrderHelper helper = null;
		String workOrderId =
			request.getParameter(WorkOrderHelper.ID_WORKORDER_ID);
		if (workOrderId == null) {
			helper = new WorkOrderHelper(projectId);
		} else {
			try {
        PtsOperationHome home = (PtsOperationHome) EjbHomes.getHome(PtsOperationHome.class);
        PtsOperation ptsOp = home.create();
				helper = new WorkOrderHelper(ptsOp.getWorkOrder(workOrderId));
			} catch (javax.ejb.CreateException e) {
				throw new ApiException("Error finding work order", e);
      } catch (javax.naming.NamingException e) {
        throw new ApiException("Error finding work order", e);
      } catch (ClassNotFoundException e) {
        throw new ApiException("Error finding work order", e);
			}
		}

		request.setAttribute(JspHelper.ID_HELPER, helper);
		servletCtx.getRequestDispatcher("/hiddenJsps/iltds/pts/WorkOrderEdit.jsp").forward(
			request,
			response);
	}
}
