package com.ardais.bigr.orm.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.orm.web.form.AccountBasedForm;
import com.ardais.bigr.orm.web.form.ManagePrivilegesForm;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.web.action.BigrAction;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

public class ManageAccountRetryAction extends BigrAction {

  /**
   * @see com.ardais.bigr.web.action.BigrAction#doPerform(BigrActionMapping, BigrActionForm, HttpServletRequest, HttpServletResponse)
   */
  protected ActionForward doPerform(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {
    
    ActionForward myActionForward = null;
    AccountBasedForm myForm = (AccountBasedForm)form;
    String objectType = myForm.getObjectType();
    if (Constants.OBJECT_TYPE_ACCOUNT.equalsIgnoreCase(objectType)) {
      String url = "/orm/accounts/modifyAccountStart.do?accountData.id=" + myForm.getAccountData().getId();
      request.getSession().getServletContext().getRequestDispatcher(url).forward(request,response);
      //return a null ActionForward in this case to let the ActionServlet know the request has been
      //handled and no further processing is necessary
      myActionForward = null;
    }
    else if (Constants.OBJECT_TYPE_USER.equalsIgnoreCase(objectType)){
      myActionForward = mapping.findForward("retry" + objectType);
    }
    else {
      throw new ApiException("Unknown object type (" + objectType + ") encountered.");
    }
    return myActionForward;
  }

}
