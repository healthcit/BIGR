package com.ardais.bigr.iltds.op;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.assistants.LineItemDataBean;
import com.ardais.bigr.iltds.assistants.SampleDataBean;
import com.ardais.bigr.iltds.beans.PtsOperation;
import com.ardais.bigr.iltds.beans.PtsOperationHome;
import com.ardais.bigr.iltds.helpers.LineItemHelper;
import com.ardais.bigr.iltds.helpers.ProjectHelper;
import com.ardais.bigr.iltds.helpers.SampleHelper;
import com.ardais.bigr.iltds.helpers.SampleTableHelper;
import com.ardais.bigr.util.EjbHomes;

/**
 */
public class PtsLineItemListViewPrepare extends StandardOperation {
	/**
	 * Creates the LineItemListViewPrepare op.
	 *
	 * @param  req  the HttpServletRequest
	 * @param  res  the HttpServletResponse
	 * @param  ctx  the ServletContext
	 */
	public PtsLineItemListViewPrepare(
		HttpServletRequest req,
		HttpServletResponse res,
		ServletContext ctx) {
		super(req, res, ctx);
	}
	/**
	 */
	public void invoke() throws IOException, ServletException, ApiException {
		ProjectHelper projectHelper = ProjectHelper.prepareFromRequest(request);

		try {
      PtsOperationHome home = (PtsOperationHome) EjbHomes.getHome(PtsOperationHome.class);
      PtsOperation ptsOp = home.create();
			List lineItems = ptsOp.getLineItems(projectHelper.getRawId());
			int numLineItems = lineItems.size();
			for (int i = 0; i < numLineItems; i++) {
				LineItemDataBean lineItemDataBean =
					(LineItemDataBean) lineItems.get(i);
				LineItemHelper lineItemHelper =
					new LineItemHelper(lineItemDataBean);
				projectHelper.addLineItem(lineItemHelper);

				SampleTableHelper tableHelper = new SampleTableHelper();
				tableHelper.setCheckboxIncluded(false);
				tableHelper.setNoSampleText(
					"There are no samples selected for this line item");
				tableHelper.setTableHeader(
					"Selected Samples for Line Item #" + String.valueOf(i + 1));
				lineItemHelper.setSampleTableHelper(tableHelper);

				List samples =
					ptsOp.getCompleteSampleInfo(lineItemDataBean.getId());
				int numSamples = samples.size();
				for (int j = 0; j < numSamples; j++) {
					tableHelper.addSample(
						new SampleHelper((SampleDataBean) samples.get(j)));
				}
			}
		} catch (javax.ejb.CreateException e) {
			throw new ApiException(e);
		} catch (javax.naming.NamingException e) {
      throw new ApiException(e);
    } catch (ClassNotFoundException e) {
      throw new ApiException(e);
    }

		servletCtx.getRequestDispatcher("/hiddenJsps/iltds/pts/LineItemListView.jsp").forward(
			request,
			response);
	}
}
