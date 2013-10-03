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
 * Used to create a new line item or edit a line item.
 * The following are the input HTTP request parameters:
 * <ul>
 * <li>
 *	LineItemHelper.ID_LINEITEM_ID - The id of the line item
 *		to edit.  If this is specified, then the corresponding
 *		line item is found and used to populate the JSP.  If
 * 		this is not specified then it is assumed that a new 
 *		line item is being created.
 * </li>
 * <li>
 *	ProjectHelper.ID_PROJECT_ID (required) - The id of
 *		the project to which the line item is or should be
 *		associated.
 * </li>
 * </ul>
 * After looking up the existing line item, if necessary,
 * this op will populate a <code>LineItemHelper</code> with
 * the existing line item values and dropdown lists, set
 * request attribute <code>JspHelper.ID_HELPER</code> to the
 * helper, and forward to the JSP to allow the user to edit 
 * the line item.
 */
public class PtsLineItemEditPrepare extends StandardOperation {
	/**
	 * Creates the PtsLineItemEditPrepare op.
	 *
	 * @param  req  the HttpServletRequest
	 * @param  res  the HttpServletResponse
	 * @param  ctx  the ServletContext
	 */
	public PtsLineItemEditPrepare(
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
					+ "' is not defined.");
		}

		LineItemHelper helper = null;
		String lineItemId = request.getParameter(LineItemHelper.ID_LINEITEM_ID);
		if (lineItemId == null) {
			helper = new LineItemHelper(projectId);
		} else {
			try {
        PtsOperationHome home = (PtsOperationHome) EjbHomes.getHome(PtsOperationHome.class);
        PtsOperation ptsOp = home.create();
				helper = new LineItemHelper(ptsOp.getLineItem(lineItemId));
			} catch (javax.ejb.CreateException e) {
				throw new ApiException("Error finding line item", e);
      } catch (javax.naming.NamingException e) {
        throw new ApiException("Error finding line item", e);
      } catch (ClassNotFoundException e) {
        throw new ApiException("Error finding line item", e);
      }
		}

		request.setAttribute(JspHelper.ID_HELPER, helper);
		servletCtx.getRequestDispatcher("/hiddenJsps/iltds/pts/LineItemEdit.jsp").forward(
			request,
			response);
	}
}
