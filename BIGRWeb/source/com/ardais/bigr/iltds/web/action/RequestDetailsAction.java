/*
 * Created on May 18, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ardais.bigr.iltds.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxDetailsFindRequestDetails;
import com.ardais.bigr.iltds.helpers.RequestSelect;
import com.ardais.bigr.iltds.web.form.RequestForm;
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

/**
 * @author jesielionis
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class RequestDetailsAction extends BtxAction {

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

    BtxDetailsFindRequestDetails btxDetails = (BtxDetailsFindRequestDetails) btxDetails0;
    //set the level of detail to find about the request.  We want no item information (the
    //item details are retrieved via a ResultsHelper later in this method), and the box details
    RequestSelect select = new RequestSelect(true, true, RequestSelect.ITEM_INFO_NONE, RequestSelect.BOX_INFO_DETAILS);
    btxDetails.setRequestSelect(select);

    //go get the request information
    BtxDetailsFindRequestDetails resultBtxDetails =
        (BtxDetailsFindRequestDetails) invokeBusinessTransaction(btxDetails,
            mapping);
            
    //retrieve the item details only if there were no errors encountered
    //when retrieving the request information
    if (resultBtxDetails.getActionErrors().empty()) {
      //get the request item information.  To do this, set up a ResultsHelper that 
      //views Order Details (tx and product are for Ord. Det.)
      String txType = ResultsHelper.TX_TYPE_ORDER_DETAIL;
      ResultsHelper helper = ResultsHelper.get(txType, request);
      //NOTE - the request number MUST be set before setting the product type.
      //Setting the product type causes the ResultsHelper to initialize it's list
      //of SampleResult objects (ResultsHelper.initLists()).  That code looks for
      //the presence of an order number or a request number.  If we've previously
      //looked at the detail of a order, the order number will be set and we'll end up
      //showing the items on that order instead of the items on the request. 
      helper.setRequestNumber(btxDetails.getInputRequestDto().getId());
      synchronized (helper) {        
        helper.setProductType(ResultsHelper.PRODUCT_TYPE_ORDER_DETAIL);
        BTXDetailsGetSamples btx = new BTXDetailsGetSamples();
        SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);
        btx.setLoggedInUserSecurityInfo(securityInfo);
        int prod = ColumnPermissions.PROD_GENERIC;
        int tx = ResultsHelper.getTxBits(txType);
        int scrn = ColumnPermissions.SCRN_ORDER_DET;
        ViewParams vp = new ViewParams(getSecurityInfo(request), prod, scrn, tx); 
        btx.setViewParams(vp);
        RequestForm theForm = (RequestForm)form;
        //if a results form definition to use for querying/rendering the details
        //was specified, pass it along to the code that will get the results
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
