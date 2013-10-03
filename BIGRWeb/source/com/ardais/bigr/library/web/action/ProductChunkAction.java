package com.ardais.bigr.library.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ardais.bigr.query.sorting.SortByColumn;
import com.ardais.bigr.query.sorting.SortOrder;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;

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

public class ProductChunkAction extends BigrAction {

  /**
   * @see com.ardais.bigr.web.action.BigrAction#doPerform(BigrActionMapping, BigrActionForm, HttpServletRequest, HttpServletResponse)
   */
  protected ActionForward doPerform(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    ResultsForm resultsForm = (ResultsForm) form;
    SecurityInfo securityInfo = getSecurityInfo(request);

    // Create the results helper.
    String txType = (String) resultsForm.getTxType();
    ResultsHelper helper = ResultsHelper.get(txType, request);
    synchronized (helper) {
      helper.setSelectedIdsForCurrentChunk(resultsForm.getSamples());
      // submit checkboxes to helper

      String prodType = resultsForm.getProductType();
      helper.setProductType(prodType); // actually, should not change in chunk action (?)

      //      BTXDetailsGetSamples btx = new BTXDetailsGetSamples();
      //      btx.setLoggedInUserSecurityInfo(request);
      //      int prod = ResultsHelper.getProductBits(prodType);
      //      int tx = ResultsHelper.getTxBits(txType);
      //      int scrn = ColumnPermissions.SCRN_SELECTION;
      //      ViewParams vp = new ViewParams(securityInfo, prod, scrn, tx); 
      //      btx.setViewParams(vp);

      BTXDetailsGetSamples btx =
        resultsForm.getBtxGetSamples(securityInfo, ColumnPermissions.SCRN_SELECTION, prodType);

      btx.setCurrentChunk(resultsForm.getNextChunkAsInt()); // specific chunk from form

      HttpSession session = request.getSession(false);
      DiagnosticTestFilterDto dto =
        (DiagnosticTestFilterDto) session.getAttribute(txType + Constants.DIAGNOSTIC_TEST_RESULT_FILTER);
      btx.setDiagnosticTestFilterDto(dto);

		final String sortedColumn = resultsForm.getSortedColumn();
		final boolean isDesc = resultsForm.getIsDescSort();
		if (StringUtils.isNotBlank(sortedColumn))
		{
			btx.addSortColumn(new SortByColumn(sortedColumn,
											   isDesc ? SortOrder.DESC : SortOrder.ASC));
		}
		else
		{
			btx.removeSorting();
		}
      helper.updateHelpers(btx);
      //set up the request attributes needed to display the view link
      request.setAttribute("resultsFormDefinitionId", btx.getViewProfile().getResultsFormDefinitionId());
      request.setAttribute("resultsFormDefinitions", FormUtils.getResultsFormDefinitionsForUser(btx.getLoggedInUserSecurityInfo(), true));
    }

    return mapping.findForward("success");
  }
}
