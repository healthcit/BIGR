package com.ardais.bigr.orm.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiProperties;
import com.ardais.bigr.api.ApiResources;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.orm.btx.BTXDetailsLogin;
import com.ardais.bigr.orm.web.form.LoginForm;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.action.BtxAction;
import com.ardais.bigr.web.form.BigrActionForm;

public class LoginAction extends BtxAction {

  /**
   * @see com.ardais.bigr.web.action.BigrAction#doPerform(BigrActionMapping, BigrActionForm, HttpServletRequest, HttpServletResponse)
   */
  protected BTXDetails doBtxPerform(
    BTXDetails details,
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    BTXDetailsLogin loginDetails = (BTXDetailsLogin) details;
    LoginForm loginForm = (LoginForm) form;

    com.ardais.bigr.orm.helpers.FormLogic.invalidateSession(request, response);

    String password = loginForm.getPassword();

    // We don't know who the user really is yet, so we set the userId and account that will get
    // logged in BTX transaction history to a generic IT user.  See the comments on the
    // GENERIC_BTX_LOGIN_* constants for more details.  If we don't do this, then the BeanUtils
    // copyProperties that populates loginDetails initially will copy the userId property
    // from the login form to it, which is wrong.
    loginDetails.setUserId(ApiProperties.getProperty("api.bigr.bootstrap.user"));
    loginDetails.setUserAccount(ApiProperties.getProperty("api.bigr.bootstrap.account"));

    loginDetails.setAttemptUser(loginForm.getUserId());
    loginDetails.setAttemptAccount(loginForm.getAccountId());
    loginDetails.setPassword(password);

    if (ApiFunctions.isEmpty(password)) {
      loginDetails.addActionError(new BtxActionError("error.noValue", "Password"));
      loginDetails.setActionForwardRetry(new BtxActionError("error.login.retry"));
    }
    else {
      loginDetails = (BTXDetailsLogin) invokeBusinessTransaction(loginDetails, mapping);
    }

    if (loginDetails.isTransactionCompleted()) {
      if (!loginDetails.isPasswordExpired()) {
        createSession(request, loginDetails);
      }
    }

    loginForm.reset(mapping, request);
    loginForm.setUserId(loginDetails.getAttemptUser());
    loginForm.setAccountId(loginDetails.getAttemptAccount());

    if ("session".equalsIgnoreCase(mapping.getScope())) {
      request.getSession().setAttribute(mapping.getName(), loginForm);
    }
    else {
      request.setAttribute(mapping.getName(), loginForm);
    }

    return loginDetails;
  }

  /**
   * Get the validated user's user profile
   */
  private static void createSession(HttpServletRequest httpRequest, BTXDetailsLogin details) {

    try {
      HttpSession sess = httpRequest.getSession(true);
      synchronized (sess) {
        sess.setAttribute("user", details.getAttemptUser());
        sess.setAttribute("account", details.getAttemptAccount());
        sess.setAttribute("userprofile", details.getProfile());
        sess.setAttribute("menuUrls", details.getMenuUrls());
        SecurityInfo securityInfo = new SecurityInfo(details.getAttemptUser());
        sess.setAttribute(SecurityInfo.SECURITY_KEY, securityInfo);
        sess.setAttribute(
          com.ardais.bigr.orm.helpers.FormLogic.SESSION_PROPERTY_LAST_NON_KEEPALIVE_ACTIVITY,
          new Long(System.currentTimeMillis()));
        sess.setMaxInactiveInterval(ApiProperties.getPropertyAsInt(ApiResources.USER_INACTIVITY));
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }
}
