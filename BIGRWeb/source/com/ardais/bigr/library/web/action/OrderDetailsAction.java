package com.ardais.bigr.library.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;

import com.ardais.bigr.es.beans.ArdaisaddressAccessBean;
import com.ardais.bigr.es.helpers.FormLogic;
import com.ardais.bigr.javabeans.OrderData;
import com.ardais.bigr.kc.form.helpers.FormUtils;
import com.ardais.bigr.library.btx.BTXDetailsGetSamples;
import com.ardais.bigr.library.performers.BtxPerformerOrderLineManager;
import com.ardais.bigr.library.web.helper.ResultsHelper;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.WebUtils;
import com.ardais.bigr.web.action.BigrAction;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;
import com.ibm.ivj.ejb.runtime.AccessBeanEnumeration;

public class OrderDetailsAction extends BigrAction {

    /**
     * 
     * @see com.ardais.bigr.web.action.BigrAction#doPerform(BigrActionMapping, BigrActionForm, HttpServletRequest, HttpServletResponse)
     */
    protected ActionForward doPerform(
        BigrActionMapping mapping,
        BigrActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception {        
        
        
        // set information required for the header.
        SecurityInfo securityInfo = getSecurityInfo(request);

        String orderNumber = request.getParameter("orderNumber");
        BtxPerformerOrderLineManager orderManager = new BtxPerformerOrderLineManager();

        OrderData orderData = orderManager.getOrderData(securityInfo, orderNumber);
        request.setAttribute("order", orderData);

        ArdaisaddressAccessBean address = new ArdaisaddressAccessBean();
        AccessBeanEnumeration addressEnum =
            (AccessBeanEnumeration) address.findByAccountandType(securityInfo.getAccount(), FormLogic.ADDR_SHIP_TO);
        ArdaisaddressAccessBean shipAddress = null;
        if (addressEnum.hasMoreElements()) {
            shipAddress = (ArdaisaddressAccessBean) addressEnum.nextElement();
        }

        if (shipAddress != null) {
            request.setAttribute("shipAddress", shipAddress);
        }
        
        // set up a ResultsHelper that views Order Details (tx and product are for Ord. Det.)
        String txType = ResultsHelper.TX_TYPE_ORDER_DETAIL;
        ResultsHelper helper = ResultsHelper.get(txType, request);
        //NOTE - the order number MUST be set before setting the product type.
        //Setting the product type causes the ResultsHelper to initialize it's list
        //of SampleResult objects (ResultsHelper.initLists()).  That code looks for
        //the presence of an order number or a request number.  If we've previously
        //looked at the detail of a request, the request number will be set and we'll end up
        //showing the items on that request instead of the items on the order. 
        helper.setOrderNumber(orderNumber);
        synchronized (helper) {        
          helper.setProductType(ResultsHelper.PRODUCT_TYPE_ORDER_DETAIL);
          
          BTXDetailsGetSamples btx = new BTXDetailsGetSamples();
          btx.setLoggedInUserSecurityInfo(WebUtils.getSecurityInfo(request));
          int prod = ColumnPermissions.PROD_GENERIC;
          int tx = ResultsHelper.getTxBits(txType);
          int scrn = ColumnPermissions.SCRN_ORDER_DET;
          ViewParams vp = new ViewParams(securityInfo, prod, scrn, tx); 
          btx.setViewParams(vp);          
          //if a results form definition to use for querying/rendering the details
          //was specified, pass it along to the code that will get the results
          btx.setResultsFormDefinitionId(request.getParameter("resultsFormDefinitionId"));
          helper.updateHelpers(btx);
          //set up the request attributes needed to display the view link
          request.setAttribute("resultsFormDefinitionId", btx.getViewProfile().getResultsFormDefinitionId());
          request.setAttribute("resultsFormDefinitions", FormUtils.getResultsFormDefinitionsForUser(btx.getLoggedInUserSecurityInfo(), true));
        }
                
        request.setAttribute("txType", txType);
        return (mapping.findForward("success"));
    }
}
