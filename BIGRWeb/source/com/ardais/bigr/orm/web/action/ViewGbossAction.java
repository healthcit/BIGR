package com.ardais.bigr.orm.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.orm.web.form.AccountBasedForm;
import com.ardais.bigr.orm.web.form.GbossForm;
import com.ardais.bigr.orm.web.form.ManagePrivilegesForm;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.web.action.BigrAction;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;
import com.gulfstreambio.gboss.GbossFactory;

public class ViewGbossAction extends BigrAction {

  /**
   * @see com.ardais.bigr.web.action.BigrAction#doPerform(BigrActionMapping, BigrActionForm, HttpServletRequest, HttpServletResponse)
   */
  protected ActionForward doPerform(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {
    
    GbossForm myForm = (GbossForm)form;
    myForm.setGboss(GbossFactory.getInstance());
    return mapping.findForward("success");
  }

}
