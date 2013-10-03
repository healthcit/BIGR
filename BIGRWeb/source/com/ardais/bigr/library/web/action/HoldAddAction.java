package com.ardais.bigr.library.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.kc.form.helpers.FormUtils;
import com.ardais.bigr.library.btx.BTXDetailsAddToHoldList;
import com.ardais.bigr.library.web.form.ResultsForm;
import com.ardais.bigr.library.web.helper.ResultsHelper;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.action.BtxAction;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * Action to put samples on hold without amounts.  For RNA, which also has amounts on hold,
 * use HoldAddRnaAction.
 */
public class HoldAddAction extends BtxAction {

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

    String txType = request.getParameter("txType");

    String[] ids = null;
    String[] unavailSampleIds = null;

    ResultsHelper helper = ResultsHelper.get(txType, request);
    synchronized (helper) {
      helper.setSelectedIdsForCurrentChunk(resultsForm.getSamples());
      // submit whatever is selected

      ids = helper.getSelectedIds(); // all ids -- old and new

      btx.setIdsNoAmounts(ids);
      btx = helper.getSelectedSamples().addSamplesByIdWithAmounts(btx);
      unavailSampleIds = btx.getUnavailSamples();
      helper.addRemovedIds(unavailSampleIds);
      helper.clearSelectedIds(); // now that they are on hold, clear them

      // no need to updateHelpers because view and data are the same, only pink ids changed
      //set up the request attributes needed to display the view link
      request.setAttribute("resultsFormDefinitionId", resultsForm.getResultsFormDefinitionId());
      request.setAttribute("resultsFormDefinitions", FormUtils.getResultsFormDefinitionsForUser(btx.getLoggedInUserSecurityInfo(), true));
    }

    // Report any problems putting things on hold.
    // Note that the ResultsHelper updates itself primarily from the form, so the BTXDetails
    // is not used for much.  To rework, use the BTX object to get the map of ids and amounts.
    //        BTXDetailsAddToHoldList addDetails = (BTXDetailsAddToHoldList) details;
    if (!ApiFunctions.isEmpty(unavailSampleIds)) {
      if (ResultsHelper.TX_TYPE_SAMP_SEL.equals(txType)) {
        btx.addActionError(
          new BtxActionError(
            "library.ss.error.unavailable.sample",
            StringUtils.join(unavailSampleIds, " ")));
      }
      else if (ResultsHelper.TX_TYPE_SAMP_REQUEST.equals(txType)) {
        btx.addActionError(
          new BtxActionError(
            "library.ss.error.unavailableforrequest.sample",
            StringUtils.join(unavailSampleIds, " ")));
      }
    }
    //if (btx.getActionErrors().empty())
    btx.setActionForwardTXCompleted("success");

    return btx;
  }
}
