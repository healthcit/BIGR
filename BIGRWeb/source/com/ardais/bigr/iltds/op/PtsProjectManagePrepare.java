package com.ardais.bigr.iltds.op;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.assistants.LineItemDataBean;
import com.ardais.bigr.iltds.assistants.WorkOrderDataBean;
import com.ardais.bigr.iltds.beans.PtsOperation;
import com.ardais.bigr.iltds.beans.PtsOperationHome;
import com.ardais.bigr.iltds.helpers.JspHelper;
import com.ardais.bigr.iltds.helpers.LineItemHelper;
import com.ardais.bigr.iltds.helpers.ProjectHelper;
import com.ardais.bigr.iltds.helpers.WorkOrderHelper;
import com.ardais.bigr.util.EjbHomes;

/**
 * <code>PtsProjectManagePrepare</code> prepares the JSP that allows
 * a project to be managed.  It is the first op in the
 * "Manage Project" transaction, which is the transaction that
 * gives the overall view of the project and its work orders and
 * line items.  It looks up the project specified by the input 
 * project id, if necessary, looks up all work orders and line
 * items, and forwards to the JSP to allow the project to be managed.
 *
 * HttpServletRequest parameters:
 * <ul>
 * <li>
 *	ProjectHelper.ID_PROJECT_ID (optional) - The id of the project
 *		to manage.  If this is specified, then the corresponding 
 * 		project is found.  This is typically specified by a link
 *		in a HTML page.
 * </li>
 * </ul>
 *
 * HttpServletRequest attributes:
 * <ul>
 * <li>
 *	JspHelper.ID_DATA_BEAN (optional) - A <code>ProjectDataBean</code> 
 *		representing the project to be managed.  This is typically 
 *		specified by another op that is forwarding to this op.
 * </li>
 * </ul>
 *
 * One of either project id or a <code>ProjectDataBean</code> must be
 * specified.
 */
public class PtsProjectManagePrepare extends StandardOperation {
	/**
	 * Creates the PtsProjectManagePrepare op.
	 *
	 * @param  req  the HttpServletRequest
	 * @param  res  the HttpServletResponse
	 * @param  ctx  the ServletContext
	 */
	public PtsProjectManagePrepare(
		HttpServletRequest req,
		HttpServletResponse res,
		ServletContext ctx) {
		super(req, res, ctx);
	}
	/**
	 * Initializes a <code>ProjectHelper</code>, adding the
	 * project's work orders and line items, and forwards to the
	 * JSP that allows the project to be managed.
	 */
	public void invoke() throws IOException, ServletException, ApiException {
		ProjectHelper helper =
			(ProjectHelper) request.getAttribute(JspHelper.ID_HELPER);
		if (helper == null) {
			helper = ProjectHelper.prepareFromRequest(request);
		}
		String projectId = helper.getRawId();

		// Get the work orders and line items for the project and
		// add them to the ProjectHelper.
		try {
      PtsOperationHome home = (PtsOperationHome) EjbHomes.getHome(PtsOperationHome.class);
      PtsOperation ptsOp = home.create();
			List woBeans = ptsOp.getWorkOrders(projectId);
			for (int i = 0; i < woBeans.size(); i++) {
				WorkOrderDataBean woBean = (WorkOrderDataBean) woBeans.get(i);
				helper.addWorkOrder(new WorkOrderHelper(woBean));
			}

			List liBeans = ptsOp.getLineItems(projectId);
			for (int i = 0; i < liBeans.size(); i++) {
				LineItemDataBean liBean = (LineItemDataBean) liBeans.get(i);
				helper.addLineItem(new LineItemHelper(liBean));
			}
		} catch (javax.ejb.CreateException e) {
			throw new ApiException(
				"Error retriving work orders or line items for project "
					+ projectId,
				e);
    } catch (javax.naming.NamingException e) {
      throw new ApiException(
        "Error retriving work orders or line items for project "
          + projectId,
        e);
    } catch (ClassNotFoundException e) {
      throw new ApiException(
        "Error retriving work orders or line items for project "
          + projectId,
        e);
		}

		servletCtx.getRequestDispatcher("/hiddenJsps/iltds/pts/ProjectManage.jsp").forward(
			request,
			response);
	}
}
