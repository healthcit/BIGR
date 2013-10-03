package com.ardais.bigr.lims.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.lims.helpers.LimsConstants;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.web.action.BigrAction;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * @author jesielionis
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class LimsIncidentReasonAction extends BigrAction {

  /**
   * @see com.ardais.bigr.web.action.BigrAction#doPerform(BigrActionMapping, BigrActionForm, HttpServletRequest, HttpServletResponse)
   */
  protected ActionForward doPerform(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {
      
    String reasonText = request.getParameter("reasonText");
    String actionCode = request.getParameter("actionCode");
    request.setAttribute("reasonText", reasonText);
    request.setAttribute("actionCode", actionCode);

    //determine which reasons we need to show
    if (ApiFunctions.safeEquals(actionCode,LimsConstants.INCIDENT_ACTION_REPV)) {
      request.setAttribute(
      "reasonList",
      BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_LIMS_RE_PV_REASON));
      request.setAttribute(
      "reasonSource",
      BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_LIMS_RE_PV_SOURCE));
    }
    //for now, any non-RePv reason will just offer the "other" choice.  If we decide to offer
    //additional choices we'll need to set up ARTS codes, as we did for Re-PV
    else {
      LegalValueSet reasons = new LegalValueSet();
      reasons.addLegalValue("Other reason");
      request.setAttribute("reasonList",reasons);
    }
    
    return mapping.findForward("success");
  }

}
