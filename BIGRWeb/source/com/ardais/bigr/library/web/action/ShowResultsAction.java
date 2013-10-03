package com.ardais.bigr.library.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForward;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.javabeans.DiagnosticTestFilterDto;
import com.ardais.bigr.kc.form.helpers.FormUtils;
import com.ardais.bigr.library.btx.BTXDetailsGetSamples;
import com.ardais.bigr.library.web.form.QueryForm;
import com.ardais.bigr.library.web.form.ShowResultsForm;
import com.ardais.bigr.library.web.helper.ResultsHelper;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.WebUtils;
import com.ardais.bigr.web.action.BigrAction;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * This action redirects to the query results pane, without updating the 
 * ResultsHelper, other than passing along things like the TxType, which
 * is passed along from Action to Form to Action and so on.
 */
public class ShowResultsAction extends BigrAction {

  /**
   * @see com.ardais.bigr.web.action.BigrAction#doPerform(BigrActionMapping, BigrActionForm, HttpServletRequest, HttpServletResponse)
   */
  protected ActionForward doPerform(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    // this request has virutally no data (once BTXified, most fields will be null)
    // so we gather a lot of session data into a btx here.

    BTXDetailsGetSamples btx = new BTXDetailsGetSamples();
    btx.setLoggedInUserSecurityInfo(WebUtils.getSecurityInfo(request));

    ShowResultsForm resultsForm = (ShowResultsForm) form;
    SecurityInfo securityInfo = getSecurityInfo(request);

    // Create the results helper.
    String txType = (String) resultsForm.getTxType();
    //  no idea how this got in here, or what it ever did:    btx.setTxType(txType);  // BTX SET

    ResultsHelper helper = ResultsHelper.get(txType, request);

    synchronized (helper) {
      if (!helper.isAnyResults())
        return mapping.findForward("noResults");

      // if we are changing tab, clear all selected samples
      String lastType = helper.getRememberedType();
      String productTypeExplicit = resultsForm.getProductType();

      // for consisency, clear ids when tabs are changed (remove this "feature" later)
      if (lastType == null) {
        helper.clearSelectedIds();
      }
      else {
        if (productTypeExplicit != null) {
          if (!lastType.equals(productTypeExplicit)) {
            helper.clearSelectedIds();
          }
        }
      }

      if (productTypeExplicit != null) {
        helper.setProductType(productTypeExplicit);
        helper.setRememberedProductType(productTypeExplicit);
      }
      else { // no type set.  use tissue, unless there is none, then use rna
        // show the last viewed tab.  leave the paging, selections, etc. alone    
        lastType = helper.getRememberedType();
        if (lastType == null)
          lastType = ResultsHelper.PRODUCT_TYPE_SAMPLE;
        helper.setProductType(lastType);
        productTypeExplicit = lastType; // set this so it gets into the btx request
      }

      int prod = ResultsHelper.getProductBits(helper.getProductType());
      int tx = ResultsHelper.getTxBits(txType);
      int scrn = ColumnPermissions.SCRN_SELECTION;
      ViewParams vp = new ViewParams(securityInfo, prod, scrn, tx);
      btx.setViewParams(vp);
      ShowResultsForm theForm = (ShowResultsForm)form;
      //if a results form definition to use for querying/rendering the details
      //was specified, pass it along to the code that will get the results
      btx.setResultsFormDefinitionId(theForm.getResultsFormDefinitionId());

      HttpSession session = request.getSession(false);
      DiagnosticTestFilterDto dto =
        (DiagnosticTestFilterDto) session.getAttribute(txType + Constants.DIAGNOSTIC_TEST_RESULT_FILTER);
      btx.setDiagnosticTestFilterDto(dto);

      // now that the btx has product type and type set the data
      // note btx does not have chunk or selections or ids, so these should be saved from last time
      helper.updateHelpers(btx);
      //set up the request attributes needed to display the view link
      request.setAttribute("resultsFormDefinitionId", btx.getViewProfile().getResultsFormDefinitionId());
      request.setAttribute("resultsFormDefinitions", FormUtils.getResultsFormDefinitionsForUser(btx.getLoggedInUserSecurityInfo(), true));

    }
    return mapping.findForward("success");
  }
}
