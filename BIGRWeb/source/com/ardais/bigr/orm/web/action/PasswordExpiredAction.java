package com.ardais.bigr.orm.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.orm.web.form.AccountBasedForm;
import com.ardais.bigr.orm.web.form.LoginForm;
import com.ardais.bigr.orm.web.form.ManagePrivilegesForm;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.web.action.BigrAction;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

public class PasswordExpiredAction extends BigrAction {

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
    LoginForm myForm = (LoginForm)form;
    StringBuffer url = new StringBuffer(200);
    url.append("/orm/changePasswordStartNoLoginRequired.do");
    url.append("?requireLogin=false");
    url.append("&requireOldPassword=false");
    url.append("&reasonForChange=");
    url.append(Constants.PASSWORD_CHANGE_REASON_EXPIRED);
    url.append("&userData.userId=");
    url.append(myForm.getUserId());
    url.append("&userData.accountId=");
    url.append(myForm.getAccountId());
    request.getSession().getServletContext().getRequestDispatcher(url.toString()).forward(request,response);
    //return a null ActionForward in this case to let the ActionServlet know the request has been
    //handled and no further processing is necessary
    return myActionForward;
  }

}
