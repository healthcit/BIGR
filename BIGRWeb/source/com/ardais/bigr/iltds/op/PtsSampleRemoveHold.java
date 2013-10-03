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
public class PtsSampleRemoveHold extends StandardOperation {
	/**
	 * Creates the <code>PtsSampleRemoveHold</code> op.
	 *
	 * @param  req  the HttpServletRequest
	 * @param  res  the HttpServletResponse
	 * @param  ctx  the ServletContext
	 */
	public PtsSampleRemoveHold(
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

			// Each entry in the samples[] array is a string of
			// the form sampleId;accountId;userId.  Parse each of
			// these into 3 arrays for the EJB call.
			String[] sampleIds = new String[samples.length];
			String[] accounts = new String[samples.length];
			String[] users = new String[samples.length];
			for (int i = 0; i < samples.length; i++) {
				String ids = samples[i];
				int firstDelim = ids.indexOf(';');
				sampleIds[i] = ids.substring(0, firstDelim++);
				int secondDelim = ids.indexOf(';', firstDelim);
				accounts[i] = ids.substring(firstDelim, secondDelim);
				users[i] = ids.substring(++secondDelim);
			}

			// Remove the requested samples from hold.
			try {
        PtsOperationHome home = (PtsOperationHome) EjbHomes.getHome(PtsOperationHome.class);
        PtsOperation ptsOp = home.create();
				int numRemoved =
					ptsOp.removeSamplesFromHold(sampleIds, accounts, users);
				if (numRemoved == 1) {
					request.setAttribute(
						JspHelper.ID_MESSAGE,
						"1 sample was taken off hold.");
				} else {
					request.setAttribute(
						JspHelper.ID_MESSAGE,
						numRemoved + " samples were taken off hold.");
				}
			} catch (javax.ejb.CreateException e) {
				throw new ApiException(e);
      } catch (javax.naming.NamingException e) {
        throw new ApiException(e);
      } catch (ClassNotFoundException e) {
        throw new ApiException(e);
			}
		}
		(new PtsSampleRemoveHoldPrepare(request, response, servletCtx))
			.invoke();
	}
}
