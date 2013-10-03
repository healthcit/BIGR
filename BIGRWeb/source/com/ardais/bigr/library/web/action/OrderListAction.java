package com.ardais.bigr.library.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;

import com.ardais.bigr.javabeans.OrderData;
import com.ardais.bigr.library.performers.BtxPerformerOrderLineManager;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.web.action.BigrAction;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

public class OrderListAction extends BigrAction {

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
        BtxPerformerOrderLineManager orderMan = new BtxPerformerOrderLineManager();
               
        OrderData[] orders = orderMan.getOrderIds(securityInfo);
        
        if(orders != null && orders.length > 0){
            request.setAttribute("orders", orders);            
        }

        return (mapping.findForward("success"));
    }
}
