package com.ardais.bigr.web.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.iltds.btx.BtxActionMessages;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.StrutsUtils;
import com.ardais.bigr.util.WebUtils;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * BIGR-specific extensions to the standard Struts Action class.
 */
public abstract class BigrAction extends Action {

  /**
   * The request attributes key under which your action should store an
   * <code>com.ardais.bigr.iltds.btx.BtxActionMessages</code> object, if you
   * are using the corresponding custom tag library elements.
   */
  public static final String BTX_MESSAGES_KEY = "com.ardais.BTX_ACTION_MESSAGES";

  /**
   * @see org.apache.struts.action.Action#execute(ActionMapping,
   *     ActionForm, HttpServletRequest, HttpServletResponse)
   */
  public final ActionForward execute(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws IOException, ServletException {

    ActionForward actionForward = null;

    try {

      if ((mapping != null) && !(mapping instanceof BigrActionMapping)) {
        throw new IllegalArgumentException(
          "ActionMapping supplied to a BigrAction must be a "
            + "BigrActionMapping: "
            + mapping.getClass().getName());
      }

      if ((form != null) && !(form instanceof BigrActionForm)) {
        throw new IllegalArgumentException(
          "ActionForm supplied to a BigrAction must be a "
            + "BigrActionForm: "
            + form.getClass().getName());
      }

      BigrActionMapping bigrMapping = (BigrActionMapping) mapping;
      BigrActionForm bigrForm = (BigrActionForm) form;

      SecurityInfo securityInfo = getSecurityInfo(request);

      // This will return a non-null actionForward if the user is
      // not authorized to perform this action.
      //
      actionForward = verifyAuthorization(securityInfo, bigrMapping, bigrForm, request, response);

      // If our authorization check failed, return the indicated
      // failure ActionForward and don't actually perform the
      // requested action.
      //
      if (actionForward != null) {
        return actionForward;
      }

      actionForward = doPerform(bigrMapping, bigrForm, request, response);
    }
    catch (Exception e) {
      if (!ApiLogger.isIgnorableException(e)) {
        ApiLogger.log(
          "Exception caught in BigrAction.perform for action class "
            + this.getClass().getName()
            + " at path "
            + mapping.getPath(),
          e);

        // Make the exception available to a JSP error page in the
        // standard way.
        //
        request.setAttribute("javax.servlet.jsp.jspException", e);

        // Forward to the standard Ardais error page.
        //
        actionForward = mapping.findForward("error");
      }
      else {
        actionForward = null;
      }
    }

    return actionForward;
  }

  /**
   * @see com.ardais.bigr.security.SecurityInfo
   */
  protected SecurityInfo getSecurityInfo(HttpServletRequest request) {
    return WebUtils.getSecurityInfo(request);
  }

  /**
   * Verify that the user is authorized to perform this action.  This
   * method performs basic authorization checks that are required of
   * all transactions, and can't be overridden.  If the basic checks
   * pass it calls {@link #isActionAuthorized(SecurityInfo, ActionMapping,
   * ActionForm, HttpServletRequest) isActionAuthorized} to do additional
   * authorization checks, and subclasses may override that method to
   * perform action-specific checks.
   * 
   * @param securityInfo  the security information of the logged-in user 
   * 		(if no user is logged in, username and account will be null,
   * 		securityInfo.isInRoleNone() will be true, and the user will have no
   * 		privileges)
   * @param mapping the ActionMapping describing the requested action
   * @param form the ActionForm containing the user's input
   * @param request the HTTP request
   * @param response the HTTP response
   * @return ActionForward null if the user is authorized to perform the
   *     transaction, otherwise an ActionForward object to send the user to.
   */
  private ActionForward verifyAuthorization(
    SecurityInfo securityInfo,
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) {

    // Determine the type code that we'll pas to the commonPageActions
    // method.  This should be "D" for actions that require the user
    // to be logged in and "N" for actions that don't.
    //
    String commonPageActionsTypeCode = (mapping.isLoginRequired() ? "D" : "N");

    boolean checkLogin =
      com.ardais.bigr.orm.helpers.FormLogic.commonPageActionsNoForward(
        request,
        response,
        commonPageActionsTypeCode);

    if (!checkLogin) {
      // Forward to the standard Ardais session timeout page.  While
      // we could get here because somebody got their hands on a URL
      // for an action they aren't authorized to perform, the more
      // likely situation is that the user was validly logged in and
      // then their session timed out.  We don't have a good way to
      // tell the difference between these two situations, so we make
      // the kind assumption that their session timed out rather than
      // that they're trying to hack the system.
      //
      return mapping.findForward("sessionTimeout");
    }

    // If we get here all basic authorization checks have passed.
    // Perform any other checks now.  Actions may override the
    // isActionAuthorized method to perform action-specific checks.
    //
    if (!isActionAuthorized(securityInfo, mapping, form, request)) {
      // Forward to an error page and log the authorization failure.
      //
      ApiLogger.log(
        "UNAUTHORIZED OPERATION ATTEMPTED.  User: "
          + securityInfo.getUsername()
          + ", Action class: "
          + this.getClass().getName()
          + " at path "
          + mapping.getPath());

      request.setAttribute(
        "myError",
        "You are not authorized to perform the operation you requested.");

      return mapping.findForward("error");
    }

    // If we get here all authorization checks have passed, so return null.
    //
    return null;
  }

  /**
   * Check to make sure that the current user is authorized to perform this
   * action.  This is called after general non-overridable checks such
   * as making sure that the user is logged in have already passed (and it
   * won't be called if those general checks fail).  Actions that have
   * additional checks to perform should override this method and return
   * false if any of the checks fail.  When checks fail, the method that
   * calls this will use ApiLogger.log to record the authorization failure
   * and redirect the user to an error page.  Subclasses should also log an
   * additional message giving the specifics of the authorization failure.
   * 
   * <p>To ensure that no authorization checks are missed, subclasses that
   * override this method should always call the checks on the superclass
   * first, and should not assume, for example, that the superclass always
   * returns true (this may be true now but could change in the future).
   * For example, the method in the subclass should look like this:
   *
   * <pre>
   * protected boolean isActionAuthorized(
   *   SecurityInfo securityInfo,
   *   BigrActionMapping mapping,
   *   BigrActionForm form0,
   *   HttpServletRequest request) {
   * 
   *   boolean isAuthorized =
   *     super.isActionAuthorized(securityInfo, mapping, form0, request);
   * 
   *   if (! isAuthorized) {
   *     ... do the checks specific to the subclass and set isAuthorized
   *     ... appropriately.
   *   }
   * 
   *   return isAuthorized;
   * }
   * </pre>
   * 
   * @param securityInfo  the security information of the logged-in user 
   * 		(if no user is logged in, username and account will be null,
   * 		securityInfo.isInRoleNone() will be true, and the user will have no
   * 		privileges)
   * @param mapping the ActionMapping describing the requested action
   * @param form the ActionForm containing the user's input
   * @param request the HTTP request
   * 
   * @return true if the action is authorized, otherwise false
   */
  protected boolean isActionAuthorized(
    SecurityInfo securityInfo,
    BigrActionMapping mapping,
    BigrActionForm form0,
    HttpServletRequest request) {

    return true;
  }

  /**
   * Perform the action-specific details of this action.  Subclasses must
   * override this.
   */
  protected abstract ActionForward doPerform(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception;

  /**
   * Save the specified error messages keys into the appropriate request
   * attribute for use by the &lt;struts:errors&gt; tag, if any messages
   * are required.  Otherwise, ensure that the request attribute is not
   * created.
   *
   * @param request The servlet request we are processing
   * @param messages Error messages object
   * @param preservePrior if true, the new messages will be added to any existing ones
   *    that may already be present in the request (instead of overwriting them).
   */
  protected void saveErrors(
    HttpServletRequest request,
    ActionErrors messages,
    boolean preservePrior) {
    ActionErrors mergedMessages;

    if (!preservePrior) {
      mergedMessages = messages;
    }
    else {
      ActionErrors priorMessages = (ActionErrors) request.getAttribute(Globals.ERROR_KEY);
      if (priorMessages == null) {
        mergedMessages = messages;
      }
      else {
        mergedMessages = StrutsUtils.addActionErrors(priorMessages, messages);
      }
    }

    super.saveErrors(request, mergedMessages);
  }

  /**
   * Save the specified error messages keys into the appropriate request
   * attribute for use by the &lt;struts:errors&gt; tag, if any messages
   * are required.  Otherwise, ensure that the request attribute is not
   * created.
   *
   * @param request The servlet request we are processing
   * @param messages Error messages object
   */
  protected void saveErrors(HttpServletRequest request, ActionErrors messages) {
    saveErrors(request, messages, false);
  }

  /**
   * Save the specified messages keys into the appropriate request
   * attribute for use by the &lt;btxMessages&gt; tag, if any messages
   * are required.  Otherwise, ensure that the request attribute is not
   * created.
   *
   * @param request The servlet request we are processing
   * @param messages Messages object
   * @param preservePrior if true, the new messages will be added to any existing ones
   *    that may already be present in the request (instead of overwriting them).
   */
  protected void saveMessages(
    HttpServletRequest request,
    BtxActionMessages messages,
    boolean preservePrior) {
    BtxActionMessages mergedMessages;

    if (!preservePrior) {
      mergedMessages = messages;
    }
    else {
      BtxActionMessages priorMessages = (BtxActionMessages) request.getAttribute(BTX_MESSAGES_KEY);
      if (priorMessages == null) {
        mergedMessages = messages;
      }
      else {
        priorMessages.addAll(messages);
        mergedMessages = priorMessages;
      }
    }

    saveMessages(request, mergedMessages);
  }

  /**
   * Save the specified messages keys into the appropriate request
   * attribute for use by the &lt;btxMessages&gt; tag, if any messages
   * are required.  Otherwise, ensure that the request attribute is not
   * created.
   *
   * @param request The servlet request we are processing
   * @param messages Messages object
   */
  protected void saveMessages(HttpServletRequest request, BtxActionMessages messages) {
    // Remove any messages attribute if none are required
    if ((messages == null) || messages.empty()) {
      request.removeAttribute(BTX_MESSAGES_KEY);
      return;
    }
    // Save the messages we need
    request.setAttribute(BTX_MESSAGES_KEY, messages);
  }
}
