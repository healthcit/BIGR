package com.ardais.bigr.library.web.action;

import com.ardais.bigr.javabeans.DiagnosticTestFilterDto;
import com.ardais.bigr.kc.form.helpers.FormUtils;
import com.ardais.bigr.library.btx.BTXDetailsGetSamples;
import com.ardais.bigr.library.web.form.ResultsForm;
import com.ardais.bigr.library.web.helper.ResultsHelper;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.web.action.BigrAction;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Roman Boris
 * @since 8/3/12
 */
public class QueryPerformExportAction extends BigrAction
{
	@Override
	protected ActionForward doPerform(BigrActionMapping mapping, BigrActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		final ResultsForm resultsForm = (ResultsForm) form;
		final SecurityInfo securityInfo = getSecurityInfo(request);

		// Create the results helper.
		final String txType = resultsForm.getTxType();
		final ResultsHelper helper = ResultsHelper.get(txType, request);
		synchronized (helper)
		{
			helper.setSelectedIdsForCurrentChunk(resultsForm.getSamples());
			// submit checkboxes to helper

			String prodType = resultsForm.getProductType();
			helper.setProductType(prodType);

			final BTXDetailsGetSamples btx =
				resultsForm.getBtxGetSamples(securityInfo, ColumnPermissions.SCRN_SELECTION, prodType);

			btx.setRetrieveAllChunks(true);

			final HttpSession session = request.getSession(false);
			final DiagnosticTestFilterDto dto = (DiagnosticTestFilterDto) session.getAttribute(txType + Constants.DIAGNOSTIC_TEST_RESULT_FILTER);
			btx.setDiagnosticTestFilterDto(dto);
			helper.updateHelpers(btx);
			//set up the request attributes needed to display the view link
			request.setAttribute("resultsFormDefinitionId", btx.getViewProfile().getResultsFormDefinitionId());
			request.setAttribute("resultsFormDefinitions", FormUtils.getResultsFormDefinitionsForUser(btx.getLoggedInUserSecurityInfo(), true));

			btx.setRetrieveAllChunks(false);
		}

		return mapping.findForward("success");
	}
}
