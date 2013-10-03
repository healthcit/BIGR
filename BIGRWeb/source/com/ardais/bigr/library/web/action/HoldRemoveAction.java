package com.ardais.bigr.library.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.library.btx.BTXDetailsRemoveFromHoldList;
import com.ardais.bigr.library.web.form.ResultsForm;
import com.ardais.bigr.library.web.helper.ResultsHelper;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.action.BtxAction;
import com.ardais.bigr.web.form.BigrActionForm;

public class HoldRemoveAction extends BtxAction {

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

    BTXDetailsRemoveFromHoldList removeDetails = (BTXDetailsRemoveFromHoldList) details;
    String[] idsToRemove = removeDetails.getSamples();

    String txType = resultsForm.getTxType();
    ResultsHelper helper = ResultsHelper.get(txType, request);
    synchronized (helper) {
      helper.getSelectedSamples().removeIds(idsToRemove);
      helper.clearSelectedIds(); // they have been processed
    }

    removeDetails.setActionForwardTXCompleted("success");
    return removeDetails;
  }
}
