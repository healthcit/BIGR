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
 */
public class PtsSampleRemove extends StandardOperation {
	/**
	 * Creates the <code>PtsSampleRemove</code> op.
	 *
	 * @param  req  the HttpServletRequest
	 * @param  res  the HttpServletResponse
	 * @param  ctx  the ServletContext
	 */
	public PtsSampleRemove(
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
		String samples[] = request.getParameterValues("sample");
		if ((samples == null) || (samples.length == 0)) {
			request.setAttribute(
				JspHelper.ID_MESSAGE,
				"No samples were selected.");
		} else {
			String[] sampleIds = new String[samples.length];
			String[] lineItemIds = new String[samples.length];
			for (int i = 0; i < samples.length; i++) {
				String ids = samples[i];
				sampleIds[i] = ids.substring(0, ids.indexOf(';'));
				lineItemIds[i] = ids.substring(ids.indexOf(';') + 1);
			}
			try {
        PtsOperationHome home = (PtsOperationHome) EjbHomes.getHome(PtsOperationHome.class);
        PtsOperation ptsOp = home.create();
				int numRemoved =
					ptsOp.removeSamplesFromProject(
						projectId,
						lineItemIds,
						sampleIds);
				if (numRemoved == 1) {
					request.setAttribute(
						JspHelper.ID_MESSAGE,
						"1 sample was removed from the project.");
				} else {
					request.setAttribute(
						JspHelper.ID_MESSAGE,
						numRemoved + " samples were removed from the project.");
				}
			} catch (javax.ejb.CreateException e) {
				throw new ApiException(e);
      } catch (javax.naming.NamingException e) {
        throw new ApiException(e);
      } catch (ClassNotFoundException e) {
        throw new ApiException(e);
			}
		}
		(new PtsSampleRemovePrepare(request, response, servletCtx)).invoke();
	}
}
