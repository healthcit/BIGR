package com.ardais.bigr.library.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;

import com.ardais.bigr.web.action.BigrAction;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * @author dfeldman
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class QueryViewSummary extends BigrAction {

    /**
     * @see com.ardais.bigr.web.action.BigrAction#doPerform(BigrActionMapping, BigrActionForm, HttpServletRequest, HttpServletResponse)
     */
    protected ActionForward doPerform(
            BigrActionMapping mapping,
            BigrActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
        throws Exception {
        
        // "just do it"  no parameters, no possible failurecom.ardais.bigr.web.action.BtxAction
        return new ActionForward("success");
    }

}
