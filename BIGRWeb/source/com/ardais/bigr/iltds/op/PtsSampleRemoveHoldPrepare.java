package com.ardais.bigr.iltds.op;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.assistants.SampleDataBean;
import com.ardais.bigr.iltds.beans.PtsOperation;
import com.ardais.bigr.iltds.beans.PtsOperationHome;
import com.ardais.bigr.iltds.helpers.ProjectHelper;
import com.ardais.bigr.iltds.helpers.SampleHelper;
import com.ardais.bigr.iltds.helpers.SampleTableHelper;
import com.ardais.bigr.util.EjbHomes;

/**
 */
public class PtsSampleRemoveHoldPrepare extends StandardOperation {
	/**
	 * Creates the <code>PtsSampleRemoveHoldPrepare</code> op.
	 *
	 * @param  req  the HttpServletRequest
	 * @param  res  the HttpServletResponse
	 * @param  ctx  the ServletContext
	 */
	public PtsSampleRemoveHoldPrepare(
		HttpServletRequest req,
		HttpServletResponse res,
		ServletContext ctx) {
		super(req, res, ctx);
	}
	/**
	 */
	public void invoke() throws IOException, ServletException, ApiException {
		ProjectHelper helper = ProjectHelper.prepareFromRequest(request);
		String projectId = helper.getRawId();

		String msg = (String) request.getAttribute(ProjectHelper.ID_MESSAGE);
		if (msg != null) {
			helper.getMessageHelper().addMessage(msg);
		}

		// Get all of the samples for the project that are on hold 
		// and add a SampleHelper for each to the ProjectHelper.
		SampleTableHelper tableHelper = new SampleTableHelper();
		tableHelper.setCheckboxIncluded(true);
		tableHelper.setCheckboxColumnHeader("Rmv");
		tableHelper.setCheckboxColumnTitle(
			"Check to remove the sample from hold");
		tableHelper.setCheckboxOnclick("updateTotalSelected();");
		tableHelper.setCheckboxValue(SampleTableHelper.CHECKBOX_CARTINFO);
		tableHelper.setNoSampleText(
			"There are no samples in this project that can be removed from hold.");
		tableHelper.setTableHeader("Samples that are On Hold");
		helper.setSampleTableHelper(tableHelper);
		try {
      PtsOperationHome home = (PtsOperationHome) EjbHomes.getHome(PtsOperationHome.class);
      PtsOperation ptsOp = home.create();
			List samples = ptsOp.getSamplesOnHold(projectId);
			int numSamples = samples.size();
			if (numSamples > 0) {
				for (int i = 0; i < numSamples; i++) {
					SampleDataBean dataBean = (SampleDataBean) samples.get(i);
					tableHelper.addSample(new SampleHelper(dataBean));
				}
			} else {
				numSamples = ptsOp.getTotalSamples(projectId);
				if (numSamples == 0) {
					tableHelper.setNoSampleText(
						"There are no samples in this project.");
				}
			}
		} catch (javax.ejb.CreateException e) {
			throw new ApiException(
				"Problem getting samples for project " + projectId,
				e);
    } catch (javax.naming.NamingException e) {
      throw new ApiException(
        "Problem getting samples for project " + projectId,
        e);
    } catch (ClassNotFoundException e) {
      throw new ApiException(
        "Problem getting samples for project " + projectId,
        e);
		}

		servletCtx.getRequestDispatcher("/hiddenJsps/iltds/pts/SampleRemoveHold.jsp").forward(
			request,
			response);
	}
}
