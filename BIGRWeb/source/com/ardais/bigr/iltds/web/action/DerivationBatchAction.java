package com.ardais.bigr.iltds.web.action;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxDetailsDerivationBatch;
import com.ardais.bigr.iltds.web.form.DerivationBatchForm;
import com.ardais.bigr.javabeans.DerivationBatchDto;
import com.ardais.bigr.javabeans.DerivationDto;
import com.ardais.bigr.kc.form.helpers.FormUtils;
import com.ardais.bigr.library.btx.BTXDetailsGetSamples;
import com.ardais.bigr.library.web.helper.ResultsHelper;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.WebUtils;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.action.BtxAction;
import com.ardais.bigr.web.form.BigrActionForm;

public class DerivationBatchAction extends BtxAction {

  /**
   * @see com.ardais.bigr.web.action.BtxAction#doBtxPerform(BTXDetails, BigrActionMapping, BigrActionForm, HttpServletRequest, HttpServletResponse)
   */
  protected BTXDetails doBtxPerform(
    BTXDetails btxDetails0,
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    //invoke the specified transaction
    BtxDetailsDerivationBatch resultBtxDetails =
        (BtxDetailsDerivationBatch) super.doBtxPerform(btxDetails0, mapping, form, request, response);
            
    //retrieve the child sample details only if there were no errors encountered
    //when retrieving the derivation batch information
    if (resultBtxDetails.getActionErrors().empty()) {
      SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);
      //get the child sample information.  To do this, set up a ResultsHelper that 
      //views Derivation Batch details
      String txType = ResultsHelper.TX_TYPE_DERIV_OPS;
      ResultsHelper helper = ResultsHelper.get(txType, request);
      Set derivationIds = new HashSet();
      DerivationBatchDto derivationBatch = resultBtxDetails.getDto();
      //get a set of derivation ids, to be used to categorize the child samples
      Iterator derivationIterator = derivationBatch.getDerivations().iterator();
      while (derivationIterator.hasNext()) {
        derivationIds.add(((DerivationDto)derivationIterator.next()).getDerivationId());
      }
      synchronized (helper) {
        helper.setDerivationBatchDto(derivationBatch);
        helper.setProductType(ResultsHelper.PRODUCT_TYPE_SAMPLE);
        BTXDetailsGetSamples btx = new BTXDetailsGetSamples();
        btx.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
        btx.setLoggedInUserSecurityInfo(securityInfo);
        int prod = ColumnPermissions.PROD_TISSUE;
        int tx = ResultsHelper.getTxBits(txType);
        int scrn = ColumnPermissions.SCRN_DERIV_OPS_SUMMARY;
        ViewParams vp = new ViewParams(securityInfo, prod, scrn, tx);
        btx.setViewParams(vp);
        btx.setCategoriesToDetermine(derivationIds);
        DerivationBatchForm theForm = (DerivationBatchForm)form;
        //if a results form definition to use for querying/rendering the details
        //was specified, pass it along to the code that will get the results.
        btx.setResultsFormDefinitionId(theForm.getResultsFormDefinitionId());
        helper.updateHelpers(btx);
        //the code that gets the results will have set the view profile it used, so set that id on the form
        theForm.setResultsFormDefinitionId(btx.getViewProfile().getResultsFormDefinitionId());
        //populate the form with the list of results form definitions for the user
        theForm.setResultsFormDefinitions(FormUtils.getResultsFormDefinitionsForUser(securityInfo, true));
      }
      request.setAttribute("txType", txType);
      
    }
                
    return resultBtxDetails;
  }

}
