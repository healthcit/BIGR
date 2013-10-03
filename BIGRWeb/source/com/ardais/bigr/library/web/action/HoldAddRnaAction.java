package com.ardais.bigr.library.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.javabeans.ProductDto;
import com.ardais.bigr.library.btx.BTXDetailsAddToHoldList;
import com.ardais.bigr.library.btx.BTXDetailsGetSamples;
import com.ardais.bigr.library.web.form.ResultsForm;
import com.ardais.bigr.library.web.helper.ResultsHelper;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.WebUtils;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.action.BtxAction;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * Add the RNA items with amounts from the Form to the Hold List.
 * This does not pull its items from the ResultsHelper, like the tissue HoldAddAction does,
 * because the RNA comes from a single "amounts" screen, and all data is in the form.
 */
public class HoldAddRnaAction extends BtxAction {

  /**
   * @see com.ardais.bigr.web.action.BigrAction#doPerform(BigrActionMapping, BigrActionForm, HttpServletRequest, HttpServletResponse)
   */
  protected BTXDetails doBtxPerform(
    BTXDetails details,
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    ResultsForm resultsForm = (ResultsForm) form;
    BTXDetailsAddToHoldList btx = (BTXDetailsAddToHoldList) details;
    SecurityInfo secInfo = WebUtils.getSecurityInfo(request);
    
    String txType = request.getParameter("txType");
    ResultsHelper helper = ResultsHelper.get(txType, request);
    String[] unavailSampleIds;

    synchronized (helper) {

      //          Map idsAndAmts = resultsForm.getIdsAndAmounts(); form data should be in btx via introspection
      // ALL ids and amounts are in the btx object (all were on single amounts page HTML form)
      btx = helper.getSelectedSamples().addSamplesByIdWithAmounts(btx);
      unavailSampleIds = btx.getUnavailSamples();
      helper.addRemovedIds(unavailSampleIds);
      if (btx.isTransactionCompleted()) {
        // now that we've processed the selections from the RNA view, clear it
        helper.setProductType(ResultsHelper.PRODUCT_TYPE_RNA);
        helper.clearSelectedIds();
      }
      
      // update rna on hold amounts by doing  a refresh
//      BTXDetailsGetSamples btxRefresh = new BTXDetailsGetSamples();
//      btxRefresh.setLoggedInUserSecurityInfo(request);
//      int prod = ResultsHelper.getProductBits(ResultsHelper.PRODUCT_TYPE_RNA);
//      int tx = ResultsHelper.getTxBits(txType);
      int scrn = ColumnPermissions.SCRN_SELECTION;
//      ViewParams vp = new ViewParams(secInfo, prod, scrn, tx); 
//      btxRefresh.setViewParams(vp);
  
      String prod = ResultsHelper.PRODUCT_TYPE_RNA; // jump to rna screen next    
      BTXDetailsGetSamples btxRefresh = resultsForm.getBtxGetSamples(secInfo, scrn, prod);
      helper.updateHelpers(btxRefresh); 
    }

    if (!ApiFunctions.isEmpty(unavailSampleIds)) {
      String[] dispNames =
        displayNamesForRna(unavailSampleIds, details.getLoggedInUserSecurityInfo());
      btx.addActionError(
        new BtxActionError(
          "library.ss.error.unavailable.sample",
          StringUtils.join(dispNames, ", ")));
    }
    if (btx.getActionErrors().empty())
      btx.setActionForwardTXCompleted("success");

    return btx;
  }

  private String[] displayNamesForRna(String[] ids, SecurityInfo secInfo) throws Exception {
    BTXDetailsGetSamples btx = new BTXDetailsGetSamples();
    btx.setLoggedInUserSecurityInfo(secInfo, true);
    btx.setSampleIds(ids);
    btx.setTransactionType("library_get_details"); 
    btx = (BTXDetailsGetSamples) Btx.perform(btx);
    List data = btx.getSampleDetailsResult();
    int len = data.size();
    String[] result = new String[len];
    for (int i = 0; i < len; i++) {
      ProductDto ssd = (ProductDto) data.get(i);
      String disp;
      disp = "Case " + ssd.getConsentData().getConsentId() + " prep " + ssd.getRnaData().getPrep();
      result[i] = disp;
    }
    return result;
  }

}
