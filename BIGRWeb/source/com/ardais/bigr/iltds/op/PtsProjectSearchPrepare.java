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
import com.ardais.bigr.util.EjbHomes;

/**
 * <code>PtsProjectSearchPrepare</code> prepares the JSP that allows
 * searching for projects.  It is the first op in many transactions
 * that require a project upon which to operate.
 *
 * HttpServletRequest parameters:
 * <ul>
 * <li>
 *	JspHelper.ID_CONTEXT (required) - Context for the search, i.e. which 
 *		transactionwill be initiated after the search.  Value must be one 
 *		of the JpsHelper.SEARCH_CONTEXT_* constants.
 * </li>
 * </ul>
 * HttpSession attributes:
 * <ul>
 * <li>
 *	ProjectHelper.ID_PROJECT_ID (optional) - The id of the previously
 *		selected project.  If this is specified, then that project
 *		will be fetched, allowing the forwarded-to JSP to create a
 *		link to the project.
 * </li>
 * </ul>
 */
public class PtsProjectSearchPrepare extends StandardOperation {
	/**
	 * Creates a PtsProjectSearchPrepare op.
	 *
	 * @param  req  the HttpServletRequest
	 * @param  res  the HttpServletResponse
	 * @param  ctx  the ServletContext
	 */
	public PtsProjectSearchPrepare(
		HttpServletRequest req,
		HttpServletResponse res,
		ServletContext ctx) {
		super(req, res, ctx);
	}
	/**
	 * Set the context request attributes based on the context parameter,
	 * look up the project based on the project id saved in the session,
	 * if any, and forward to the search JSP.
	 */
	public void invoke() throws IOException, ServletException, ApiException {
		String context = request.getParameter(JspHelper.ID_CONTEXT);
		if (JspHelper.isEmpty(context)) {
			throw new ServletException(
				"Required parameter '"
					+ JspHelper.ID_CONTEXT
					+ "' is not present.");
		}

		// Set the context attributes that indicate the transaction
		// that is to be initiated after the search.
		if (context.equals(JspHelper.SEARCH_CONTEXT_MANAGE)) {
			request.setAttribute(
				JspHelper.ID_FOR_OP,
				"PtsProjectManagePrepare");
			request.setAttribute(JspHelper.ID_FOR_OP_TEXT, "Manage Project");
		} else if (context.equals(JspHelper.SEARCH_CONTEXT_ADD)) {
			request.setAttribute(
				JspHelper.ID_FOR_OP,
				"PtsLineItemListViewPrepare");
			request.setAttribute(
				JspHelper.ID_FOR_OP_TEXT,
				"Add Samples to Project");
		} else if (context.equals(JspHelper.SEARCH_CONTEXT_HOLD)) {
			request.setAttribute(JspHelper.ID_FOR_OP, "PtsSampleHoldPrepare");
			request.setAttribute(
				JspHelper.ID_FOR_OP_TEXT,
				"Place Samples on Hold for Project");
		} else if (context.equals(JspHelper.SEARCH_CONTEXT_RHOLD)) {
			request.setAttribute(
				JspHelper.ID_FOR_OP,
				"PtsSampleRemoveHoldPrepare");
			request.setAttribute(
				JspHelper.ID_FOR_OP_TEXT,
				"Remove Samples from Hold for Project");
		} else if (context.equals(JspHelper.SEARCH_CONTEXT_REMOVE)) {
			request.setAttribute(JspHelper.ID_FOR_OP, "PtsSampleRemovePrepare");
			request.setAttribute(
				JspHelper.ID_FOR_OP_TEXT,
				"Remove Samples from Project");
		}

		// Create a ProjectHelper for the search JSP.  If the project id of the
		// last project worked on in saved in the session, then look up the
		// project and create the ProjectHelper with that project, otherwise
		// create an empty ProjectHelper.
		ProjectHelper helper = null;
		String projectId =
			(String) request.getSession(false).getAttribute(
				ProjectHelper.ID_PROJECT_ID);
		if (projectId == null) {
			helper = new ProjectHelper();
		} else {
			try {
        PtsOperationHome home = (PtsOperationHome) EjbHomes.getHome(PtsOperationHome.class);
        PtsOperation ptsOp = home.create();
				helper = new ProjectHelper(ptsOp.getProject(projectId));
			} catch (javax.ejb.CreateException e) {
				throw new ApiException(
					"Error retrieving project information",
					e);
      } catch (javax.naming.NamingException e) {
        throw new ApiException(
          "Error retrieving project information",
          e);
      } catch (ClassNotFoundException e) {
        throw new ApiException(
          "Error retrieving project information",
          e);
			} 
		}

		// Forward to the search page.
		request.setAttribute(JspHelper.ID_HELPER, helper);
		servletCtx.getRequestDispatcher("/hiddenJsps/iltds/pts/ProjectSearch.jsp").forward(
			request,
			response);
	}
}
