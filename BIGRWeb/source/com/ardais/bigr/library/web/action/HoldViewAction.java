package com.ardais.bigr.library.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;

import com.ardais.bigr.iltds.beans.ListGenerator;
import com.ardais.bigr.iltds.beans.ListGeneratorHome;
import com.ardais.bigr.kc.form.helpers.FormUtils;
import com.ardais.bigr.library.btx.BTXDetailsGetSamples;
import com.ardais.bigr.library.web.helper.ResultsHelper;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.web.action.BigrAction;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

public class HoldViewAction extends BigrAction {

  /**
   * @see com.ardais.bigr.web.action.BigrAction#doPerform(BigrActionMapping, BigrActionForm, HttpServletRequest, HttpServletResponse)
   */
  protected ActionForward doPerform(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    SecurityInfo securityInfo = getSecurityInfo(request);
    String txType = request.getParameter("txType");

    boolean useMultiCategory = isUseMultiCategoryView(txType, securityInfo);

    ResultsHelper helper = ResultsHelper.get(txType, request);
    synchronized (helper) {
      helper.setProductType(ResultsHelper.PRODUCT_TYPE_HOLD_LIST);

      // build the BTX operation.  the ResultsForm may be null, so do it manually
      BTXDetailsGetSamples btxDetails = new BTXDetailsGetSamples();
      btxDetails.setLoggedInUserSecurityInfo(securityInfo);
      int prodCode = ColumnPermissions.PROD_GENERIC;
      int txCode = ResultsHelper.getTxBits(txType);
      int scrnCode = ColumnPermissions.SCRN_HOLD_LIST;
      btxDetails.setViewParams(new ViewParams(securityInfo, prodCode, scrnCode, txCode));
      if (useMultiCategory) {
        btxDetails.setCategoriesToDetermine(BTXDetailsGetSamples.HOLD_CATEGORY_ALL);
      }
      //if a results form definition to use for querying/rendering the details
      //was specified, pass it along to the code that will get the results
      btxDetails.setResultsFormDefinitionId(request.getParameter("resultsFormDefinitionId"));

      helper.clearSelectedIds(); // show a "fresh" view, with all types included (tissue+rna)
      helper.updateHelpers(btxDetails);
      //set up the request attributes needed to display the view link
      request.setAttribute("resultsFormDefinitionId", btxDetails.getViewProfile().getResultsFormDefinitionId());
      request.setAttribute("resultsFormDefinitions", FormUtils.getResultsFormDefinitionsForUser(btxDetails.getLoggedInUserSecurityInfo(), true));
    }

    if (txType.equals(ResultsHelper.TX_TYPE_SAMP_REQUEST)) {
      String accountId = securityInfo.getAccount();
      ListGeneratorHome home = (ListGeneratorHome) EjbHomes.getHome(ListGeneratorHome.class);
      ListGenerator list = home.create();
      // passing along staff ids to be used for path and r&d picklists...          
      request.setAttribute("staff", list.lookupArdaisStaffByAccountId(accountId));
    }

    return mapping.findForward(useMultiCategory ? "successMultiCategory" : "successSingleCategory");
  }

  /**
   * Decide where to use a single-category or multi-category view of the hold list.
   * In most cases, we forward to a single-category view.  However, we use a multi-category
   * view if:
   * <ul>
   * <li>We're on the sample selection hold list (txType is TX_TYPE_SAMP_SEL) and</li>
   * <li>The current user has access to at least one BMS inventory group.</li>
   * </ul>
   * 
   * @param txType The transaction type code.
   * @param securityInfo The current user's SecurityInfo.
   * @return True if we should use a multi-category view.
   */
  private boolean isUseMultiCategoryView(String txType, SecurityInfo securityInfo) {
    boolean useMultiCategoryView = false;

    if (ResultsHelper.TX_TYPE_SAMP_SEL.equals(txType)) {
      boolean hasBmsAccess = securityInfo.isHasBmsAccess();

      if (hasBmsAccess) {
        useMultiCategoryView = true;
      }
    }

    return useMultiCategoryView;
  }

}
