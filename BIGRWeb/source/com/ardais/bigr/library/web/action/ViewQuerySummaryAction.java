package com.ardais.bigr.library.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.dataImport.web.form.CaseForm;
import com.ardais.bigr.dataImport.web.form.SampleForm;
import com.ardais.bigr.iltds.btx.BTXActionForward;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.library.btx.BTXDetailsForSampleSummary;
import com.ardais.bigr.library.btx.BTXDetailsGetSampleSummary;
import com.ardais.bigr.library.web.form.QueryForm;
import com.ardais.bigr.library.web.helper.ResultsHelper;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.action.BtxAction;
import com.ardais.bigr.web.form.BigrActionForm;
/**
 * @author sislam
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ViewQuerySummaryAction extends BtxAction {
  /**
   * @see com.ardais.bigr.web.action.BigrAction#doPerform(BigrActionMapping, BigrActionForm, HttpServletRequest, HttpServletResponse)
   */
  protected BTXDetails doBtxPerform(
    BTXDetails genericBtx,
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    BTXDetailsGetSampleSummary btx = (BTXDetailsGetSampleSummary) genericBtx;

    QueryForm ssForm = (QueryForm) form;
    String txType = ssForm.getTxType();
    request.getSession(false).setAttribute("txType", txType);

    boolean doRnaQuery = false;
    boolean doSampleQuery = true;
    
    //SWP-1114, create a sampleForm bean in request to display tissue dropdown list in case this action fail
    if(ResultsHelper.TX_TYPE_SAMP_SEL.equalsIgnoreCase(txType) || ResultsHelper.TX_TYPE_SAMP_REQUEST.equalsIgnoreCase(txType)) {               
      SampleForm _sampleForm = new SampleForm();
      _sampleForm.setSampleData(new com.ardais.bigr.javabeans.SampleData());      
      CaseForm _caseForm = new CaseForm();
      
      request.setAttribute("caseForm", _caseForm);
      request.setAttribute("sampleForm", _sampleForm);
      request.setAttribute("CONTROL_OTHER", QueryForm.CONTROL_OTHER);
    }

    // Check if the user has privilege to view RNA.
    SecurityInfo securityInfo = btx.getLoggedInUserSecurityInfo();
    if (txType.equals(ResultsHelper.TX_TYPE_SAMP_SEL)) {
      // Check RNA has some criteria selected
      doRnaQuery = btx.isRnaCriteriaSelected();
    }

    if (doSampleQuery) {
      doSampleQuery = btx.isSampleCriteriaSelected();
    }

    // Create the results helper, and indicate what is to be displayed.
    ResultsHelper helper = ResultsHelper.get(txType, request);
    synchronized (helper) {

      // Check a product has some criteria selected, otherwise exit
      if (!doSampleQuery && !doRnaQuery) {
        btx.setFilterSet(null);
        // clear the filters
        helper.setSampleFilters((BTXDetailsGetSampleSummary) btx, 0);
        helper.setRnaFilters((BTXDetailsGetSampleSummary) btx);
        btx.addActionError(new BtxActionError("library.ss.error.criterianotselected"));
        btx.setActionForward(new BTXActionForward("fail"));
        return btx; // "fail"
      }

      String initialDisplayType = ResultsHelper.PRODUCT_TYPE_SAMPLE;
      if (doRnaQuery && !doSampleQuery) {
        initialDisplayType = ResultsHelper.PRODUCT_TYPE_RNA;
      }

      helper.setProductType(initialDisplayType);

      // do the queries, aborting if there are errors
      if (doSampleQuery) {
        btx.setBeginTimestamp(new java.sql.Timestamp(new java.util.Date().getTime()));
        btx.setProductDescription(BTXDetailsForSampleSummary.REQUEST_TYPE_TISSUE);
        BTXDetails btxReturn = helper.setSampleFilters((BTXDetailsGetSampleSummary) btx, 0);
        BtxActionErrors errors = btxReturn.getActionErrors();
        if ((errors != null) && (!errors.empty())) {
          btxReturn.setActionForward(new BTXActionForward("fail"));
          return btxReturn;
        }
      }
      else {
        helper.removeSampleQuery();
      }
      if (doRnaQuery) {
        btx.setBeginTimestamp(new java.sql.Timestamp(new java.util.Date().getTime()));
        btx.setProductDescription(BTXDetailsForSampleSummary.REQUEST_TYPE_RNA);
        BTXDetails btxReturn = helper.setRnaFilters((BTXDetailsGetSampleSummary) btx);
        BtxActionErrors errors = btxReturn.getActionErrors();
        if ((errors != null) && (!errors.empty())) {
          btxReturn.setActionForward(new BTXActionForward("fail"));
          return btxReturn;
        }
      }
      else {
        helper.removeRnaQuery();
      }
    }

    btx.setActionForward(new BTXActionForward("success"));
    return btx;
  }
}
