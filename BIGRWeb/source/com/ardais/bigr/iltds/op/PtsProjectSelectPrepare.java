package com.ardais.bigr.iltds.op;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.assistants.ProjectDataBean;
import com.ardais.bigr.iltds.beans.PtsOperation;
import com.ardais.bigr.iltds.beans.PtsOperationHome;
import com.ardais.bigr.iltds.helpers.JspHelper;
import com.ardais.bigr.iltds.helpers.ProjectHelper;
import com.ardais.bigr.util.EjbHomes;

/**
 * <code>PtsProjectSelectPrepare</code> searches for projects as
 * dictated by the input parameters.  If a project name is specified,
 * then the desired op is invoked after looking up the project.
 * Otherwise the remaining search criteria are used to query for
 * project, and a JSP that presents the results is forwarded to.
 *
 * HttpServletRequest parameters:
 * <ul>
 * <li>
 *	JspHelper.ID_FOR_OP (required) - The name of op to invoke after
 *		the search.  If a project name is specified, this op will
 *		be immediately invoked.  Otherwise this is passed through to
 *		the JSP that allows selecting one of the projects.
 * </li>
 * <li>
 *	JspHelper.ID_FOR_OP_TEXT (required) - A string that describes the
 *		transaction to be invoked.  This is passed through to
 *		the JSP that allows selecting one of the projects, so that
 *		JSP can display its links appropriately.
 * </li>
 * <li>
 *	ProjectHelper.ID_PROJECT_NAME (optional) - The name of the project
 *		to search for.  If this is specified and the project exists,
 *		then the desired op is forwarded to with this project.
 * </li>
 * </ul>
 * The op to be forwarded to must take the project id as a request
 * attribute.  This op will also set the data bean representing the
 * project as a request attribute in the case that project name is
 * specified as a search criteria, so that the forwarded-to op does
 * not have to perform the lookup of the project again.
 */
public class PtsProjectSelectPrepare extends StandardOperation {
	/**
	 * Creates a PtsProjectSelectPrepare op.
	 *
	 * @param  req  the HttpServletRequest
	 * @param  res  the HttpServletResponse
	 * @param  ctx  the ServletContext
	 */
	public PtsProjectSelectPrepare(
		HttpServletRequest req,
		HttpServletResponse res,
		ServletContext ctx) {
		super(req, res, ctx);
	}
	/**
	 * Look up the project by project name if specified, or look up
	 * a list of projects by the other search criteria, and forward to
	 * the next op, or the JSP showing the list of projects, as appropriate.
	 */
	public void invoke() throws IOException, ServletException, ApiException {
		String nextOp = request.getParameter(JspHelper.ID_FOR_OP);
		if (nextOp == null) {
			throw new ServletException(
				"Required parameter '"
					+ JspHelper.ID_FOR_OP
					+ "' is not present.");
		}
		if (request.getParameter(JspHelper.ID_FOR_OP_TEXT) == null) {
			throw new ServletException(
				"Required parameter '"
					+ JspHelper.ID_FOR_OP_TEXT
					+ "' is not present.");
		}

		String projectName =
			JspHelper.safeTrim(
				request.getParameter(ProjectHelper.ID_PROJECT_NAME));
		String clientId =
			JspHelper.safeTrim(request.getParameter(ProjectHelper.ID_CLIENT));

		// If a project name was specified, then go directly to the
		// next op after checking that the project name is valid.
		if (!JspHelper.isEmpty(projectName)) {
			try {
        PtsOperationHome home = (PtsOperationHome) EjbHomes.getHome(PtsOperationHome.class);
        PtsOperation ptsOp = home.create();
				ProjectDataBean dataBean = ptsOp.getProjectByName(projectName);
				if (dataBean == null) {
					ProjectHelper helper = new ProjectHelper();
					helper.getMessageHelper().addMessage(
						"Project '" + projectName + "' does not exist.");
					helper.getMessageHelper().setError(true);
					request.setAttribute(JspHelper.ID_HELPER, helper);
					servletCtx.getRequestDispatcher(
						"/hiddenJsps/iltds/pts/ProjectSearch.jsp").forward(
						request,
						response);
					return;
				} else {
					request.getSession().setAttribute(
						ProjectHelper.ID_PROJECT_ID,
						dataBean.getId());
					request.setAttribute(JspHelper.ID_DATA_BEAN, dataBean);
				}
			} catch (javax.ejb.CreateException e) {
				throw new com.ardais.bigr.api.ApiException(e);
      } catch (javax.naming.NamingException e) {
        throw new com.ardais.bigr.api.ApiException(e);
      } catch (ClassNotFoundException e) {
        throw new com.ardais.bigr.api.ApiException(e);
			}

			try {
				Class opClass =
					Class.forName("com.ardais.bigr.iltds.op." + nextOp);
				Class constructorArgTypes[] =
					{
						Class.forName("javax.servlet.http.HttpServletRequest"),
						Class.forName("javax.servlet.http.HttpServletResponse"),
						Class.forName("javax.servlet.ServletContext")};
				java.lang.reflect.Constructor constructor =
					opClass.getConstructor(constructorArgTypes);
				Object constructorArgs[] = { request, response, servletCtx };
				StandardOperation op =
					(StandardOperation) constructor.newInstance(
						constructorArgs);
				try {
					op.invoke();
				} catch (Exception e) {
					throw new com.ardais.bigr.api.ApiException(e);
				}
			} catch (ClassNotFoundException e) {
				throw new ServletException(
					"Could not find op class for op '" + nextOp + "'.");
			} catch (NoSuchMethodException e) {
				throw new ServletException(
					"Could not find op class for op '" + nextOp + "'.");
			} catch (java.lang.reflect.InvocationTargetException e) {
				throw new ServletException(
					"Could not forward to op '" + nextOp + "'.");
			} catch (InstantiationException e) {
				throw new ServletException(
					"Could not forward to op '" + nextOp + "'.");
			} catch (IllegalAccessException e) {
				throw new ServletException(
					"Could not forward to op '" + nextOp + "'.");
			}
		}

		// If a project name was not specified, then use the search
		// criteria to get a list of projects, and forward to the
		// JSP to allow a project to be selected.
		else if (!JspHelper.isEmpty(clientId)) {
			try {
        PtsOperationHome home = (PtsOperationHome) EjbHomes.getHome(PtsOperationHome.class);
        PtsOperation ptsOp = home.create();
				List projectDataBeans = ptsOp.getProjects(clientId);
				ArrayList projectHelpers = new ArrayList();
				for (int i = 0; i < projectDataBeans.size(); i++) {
					ProjectDataBean dataBean =
						(ProjectDataBean) projectDataBeans.get(i);
					projectHelpers.add(new ProjectHelper(dataBean));
				}
				request.setAttribute(JspHelper.ID_HELPERS, projectHelpers);
				servletCtx.getRequestDispatcher(
					"/hiddenJsps/iltds/pts/ProjectSelect.jsp").forward(
					request,
					response);
			} catch (javax.ejb.CreateException e) {
				throw new com.ardais.bigr.api.ApiException(e);
			} catch (javax.naming.NamingException e) {
				throw new com.ardais.bigr.api.ApiException(e);
      } catch (ClassNotFoundException e) {
        throw new com.ardais.bigr.api.ApiException(e);
			}
		}

		// If no search criteria were specified, then inform the user.
		else {
			ProjectHelper helper = new ProjectHelper();
			helper.getMessageHelper().addMessage(
				"Please enter a Project Name or choose a Client");
			helper.getMessageHelper().setError(true);
			request.setAttribute(JspHelper.ID_HELPER, helper);
			servletCtx.getRequestDispatcher("/hiddenJsps/iltds/pts/ProjectSearch.jsp").forward(
				request,
				response);
			return;
		}
	}
}
