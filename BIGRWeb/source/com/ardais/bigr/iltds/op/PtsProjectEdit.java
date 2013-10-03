package com.ardais.bigr.iltds.op;

import java.io.IOException;

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
 * <code>PtsProjectEdit</code> creates a new project or updates and
 * existing project based on the HTTP request parameters.  First, 
 * the parameters are validated, and if they are valid the new project 
 * is created or te existing project is updated, as appropriate, and
 * the "Manage Project" transaction is invoked.  If parameter validation 
 * fails, the requesting JSP is forwarded to to allow the user to fix
 * their mistakes.
 *
 * @see PtsProjectManagePrepare
 */
public class PtsProjectEdit extends StandardOperation {
	/**
	 * Creates the PtsProjectEdit op.
	 *
	 * @param  req  the HttpServletRequest
	 * @param  res  the HttpServletResponse
	 * @param  ctx  the ServletContext
	 */
	public PtsProjectEdit(
		HttpServletRequest req,
		HttpServletResponse res,
		ServletContext ctx) {
		super(req, res, ctx);
	}
	/**
	 * Create a new project or update an existing one, and then
	 * forward to the "Manage Project" transaction.  If a project
	 * id is specified as a request parameter, then update that
	 * project; otherwise create a new one.
	 */
	public void invoke() throws IOException, ServletException, ApiException {
		ProjectHelper helper = new ProjectHelper(request);
		if (helper.isValid()) {
			try {
				ProjectDataBean dataBean = null;
				if (helper.isNew()) {
					try {
            ProjectDataBean pdBean = helper.getDataBean();
            PtsOperationHome home = (PtsOperationHome) EjbHomes.getHome(PtsOperationHome.class);
            PtsOperation ptsOp = home.create();
						dataBean = ptsOp.createProject(pdBean);
					} catch (javax.ejb.DuplicateKeyException e) {
						request.setAttribute(JspHelper.ID_HELPER, helper);
						helper.getMessageHelper().addMessage(
							"Project with name "
								+ helper.getProjectName()
								+ " already exists.  Please choose a new Project Name.");
						helper.getMessageHelper().setError(true);
						servletCtx.getRequestDispatcher(
							"/hiddenJsps/iltds/pts/ProjectEdit.jsp").forward(
							request,
							response);
						return;
					}
				} else {
          ProjectDataBean pdBean = helper.getDataBean();
          PtsOperationHome home = (PtsOperationHome) EjbHomes.getHome(PtsOperationHome.class);
          PtsOperation ptsOp = home.create();
					dataBean = ptsOp.updateProject(pdBean);
				}
				request.getSession().setAttribute(
					ProjectHelper.ID_PROJECT_ID,
					dataBean.getId());
				request.setAttribute(JspHelper.ID_DATA_BEAN, dataBean);
				(new PtsProjectManagePrepare(request, response, servletCtx))
					.invoke();
			} catch (javax.ejb.CreateException e) {
				throw new ApiException("Error creating/updating a project", e);
			} catch (javax.naming.NamingException e) {
        throw new ApiException("Error creating/updating a project", e);
      } catch (ClassNotFoundException e) {
        throw new ApiException("Error creating/updating a project", e);
      }
		}

		// There were validation errors in the new project, so forward back
		// to the new project JSP.
		else if (helper.isNew()) {
			request.setAttribute(JspHelper.ID_HELPER, helper);
			servletCtx.getRequestDispatcher("/hiddenJsps/iltds/pts/ProjectEdit.jsp").forward(
				request,
				response);
		}

		// There were validation errors in the existing project, so forward back
		// to the manage project op.
		else {
			request.setAttribute(JspHelper.ID_HELPER, helper);
			(new PtsProjectManagePrepare(request, response, servletCtx))
				.invoke();
		}
	}
}
