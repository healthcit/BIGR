package com.ardais.bigr.library.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;

import com.ardais.bigr.library.btx.BTXDetailsGetSamples;
import com.ardais.bigr.library.web.form.ResultsForm;
import com.ardais.bigr.library.web.helper.ResultsHelper;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.WebUtils;
import com.ardais.bigr.web.action.BigrAction;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * This action updates the ResultsHelper and then displays the RNA Amounts screen (where the
 * user must select amounts for all the selected RNAs, which they are attempting to put on hold).
 */
public class HoldAmountAction extends BigrAction {

    /**
     * @see com.ardais.bigr.web.action.BigrAction#doPerform(BigrActionMapping, BigrActionForm, HttpServletRequest, HttpServletResponse)
     */
    protected ActionForward doPerform(
        BigrActionMapping mapping,
        BigrActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception {       

        ResultsForm rForm = (ResultsForm) form;
        String txType = rForm.getTxType();
        SecurityInfo secInfo = WebUtils.getSecurityInfo(request);

        // get the RNA helper to update the RNA query with the new selections
        // these selections are needed up-to-date to generate the amount form
        ResultsHelper helper = ResultsHelper.get(txType, request); 
        synchronized (helper) {
          helper.setProductType(ResultsHelper.PRODUCT_TYPE_RNA);
          helper.setSelectedIdsForCurrentChunk(rForm.getSamples());// add newly selected ids
          helper.setProductType(ResultsHelper.PRODUCT_TYPE_RNA_SELECTIONS);
          
//          BTXDetailsGetSamples btx = new BTXDetailsGetSamples();
//          btx.setLoggedInUserSecurityInfo(request);
//          int prod = ResultsHelper.getProductBits(ResultsHelper.PRODUCT_TYPE_RNA);
//          int tx = ResultsHelper.getTxBits(txType);
          int scrn = ColumnPermissions.SCRN_SAMP_AMOUNTS;
//          ViewParams vp = new ViewParams(secInfo, prod, scrn, tx); 
//          btx.setViewParams(vp);
          String prod = ResultsHelper.PRODUCT_TYPE_RNA;
          BTXDetailsGetSamples btx = rForm.getBtxGetSamples(secInfo, scrn, prod)          ;
          
          helper.updateHelpers(btx); // updates view and data
        }
        
        // get the helper, which also sets the form info, including selected items
        // getting for RNA_SELECTIONS will create a sample set based on RNA selections
//        ResultsHelper.get(ResultsHelper.PRODUCT_TYPE_RNA_SELECTIONS, txType ,rForm, request);

        return (mapping.findForward("success"));
    }
}
