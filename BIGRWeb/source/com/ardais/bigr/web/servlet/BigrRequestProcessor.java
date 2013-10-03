package com.ardais.bigr.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.RequestProcessor;
import org.apache.struts.config.ForwardConfig;
import org.apache.struts.upload.MultipartRequestWrapper;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.WebUtils;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.taglib.BigrTagConstants;

public class BigrRequestProcessor extends RequestProcessor {

  /**
   * The name of a request property that is used to override the ActionServlet's default
   * means of populating an ActionForm with information from a request.
   * 
   * @see #processPopulate(ActionForm, ActionMapping, HttpServletRequest)
   */
  public final static String ACTION_FORM_POPULATOR_SOURCE_KEY =
    "com.ardais.bigr.actionFormPopulatorSource";

  /**
   * Puts the action form under our own key in the request so we can use it anywhere on
   * the forwarded to JSP without knowing the actual form name.  This will return null
   * if the mapping requires the user to be logged in but they are not.  Later, when
   * processValidate is called, the user will be redirected to the login page if the
   * login check fails.
   * 
   * @see org.apache.struts.action.RequestProcessor#processActionForm(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.apache.struts.action.ActionMapping)
   */
  protected ActionForm processActionForm(
    HttpServletRequest request,
    HttpServletResponse response,
    ActionMapping mapping) {

    ActionForm form = null;

    try {
      if ((mapping != null) && !(mapping instanceof BigrActionMapping)) {
        throw new IllegalArgumentException(
          "ActionMapping supplied to a BigrRequestProcessor must be a "
            + "BigrActionMapping: "
            + mapping.getClass().getName());
      }

      // Do as much authorization checking here as possible.  We can't check
      // everything yet at this point, more detailed checks are done in processValidate
      // and even more detailed checks may be done in BigrAction.verifyAuthorization or in
      // user-written Action code.  To really do all of the processing we're supposed to do
      // on a basic login check, we need all three of the mapping, the request, and the response.
      // Here we only have the mapping and request, the first place we have all three is in
      // processValidate.  What we do here is enough to prevent problems before processValidate
      // is called, for example calling the ActionForm's reset() method won't happen in
      // processPopulate if we do a basic login check here and return null if it fails.
      //
      {
        if (((BigrActionMapping) mapping).isLoginRequired()) {
          SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);
          if (securityInfo.isInRoleNone()) {
            return null;
          }
        }
      } // end of basic login checking

      form = super.processActionForm(request, response, mapping);
      request.setAttribute(BigrTagConstants.ACTION_FORM_KEY, form);
    }
    catch (Exception e) {
      if (!ApiLogger.isIgnorableException(e)) {
        ApiLogger.log(
          "Exception caught in BigrRequestProcessor.processActionForm at path " + mapping.getPath(),
          e);

        // There's no way for this method to forward to our standard error page because there's
        // no way for it to return a value that tells the Struts ActionServlet to stop
        // doing further processing on this request.  So we have to re-throw the exception here,
        // which isn't really ideal since then the user will see some ugly error and it won't
        // go through our usual error page logging.

        ApiFunctions.throwAsRuntimeException(e);
      }
    }
    return form;
  }
  
  /**
   * @see org.apache.struts.action.RequestProcessor#processForwardConfig(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.apache.struts.config.ForwardConfig)
   */
  protected void processForwardConfig(
    HttpServletRequest request,
    HttpServletResponse response,
    ForwardConfig forward)
    throws IOException, ServletException {

    try {
      super.processForwardConfig(request, response, forward);
    }
    catch (Exception e) {
      if (!ApiLogger.isIgnorableException(e)) {
        ApiLogger.log(
          "Exception caught in BigrRequestProcessor.processForwardConfig at path " + forward.getPath(),
          e);

        // Make the exception available to a JSP error page in the
        // standard way.
        //
        request.setAttribute("javax.servlet.jsp.jspException", e);

        //If we're not already trying to forward to the standard Ardais error page,
        //try to forward to there.  We make this special case to avoid infinite recursion.
        //If we are already trying to forward to the standard error page, we just rethrow
        //the exception.
        //
        if ("error".equals(forward.getName())) {
          if (e instanceof IOException) {
            throw (IOException) e;
          }
          else if (e instanceof ServletException) {
            throw (ServletException) e;
          }
          else if (e instanceof RuntimeException) {
            throw (RuntimeException) e;
          }
          else {
            throw new ServletException(e);
          }
        }
        else {
          // Forward to the standard Ardais error page.
          //
          //set the request back to it's normal state if it's currently wrapped,
          //to avoid ClassCastExceptions from ServletContainers if forwarding
          if (request instanceof MultipartRequestWrapper) {
            request = ((MultipartRequestWrapper) request).getRequest();
          }
          // Forward to "error".  error is a global forward, get it
          // directly from the moduleConfig rather than from the mapping to prevent an
          // accidental local override.
          processForwardConfig(request, response, moduleConfig.findForwardConfig("error"));
        }
      }
    }
  }

  /**
   * This is just like the standard implementation that ships with Struts except that
   * it does standard Ardais exception handling.  It logs the exception and forward to
   * the Ardais error page.
   * 
   * @see org.apache.struts.action.RequestProcessor#processForward(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.apache.struts.action.ActionMapping)
   */
  protected boolean processForward(
    HttpServletRequest request,
    HttpServletResponse response,
    ActionMapping mapping)
    throws IOException, ServletException {

    try {
      return super.processForward(request, response, mapping);
    }
    catch (Exception e) {
      if (!ApiLogger.isIgnorableException(e)) {
        ApiLogger.log(
          "Exception caught in BigrRequestProcessor.processForward at path " + mapping.getPath(),
          e);

        // Make the exception available to a JSP error page in the
        // standard way.
        //
        request.setAttribute("javax.servlet.jsp.jspException", e);

        // Forward to the standard Ardais error page.
        //
        //set the request back to it's normal state if it's currently wrapped,
        //to avoid ClassCastExceptions from ServletContainers if forwarding
        if (request instanceof MultipartRequestWrapper) {
          request = ((MultipartRequestWrapper) request).getRequest();
        }
        processForwardConfig(request, response, mapping.findForward("error"));
      }

      return false; // false => tell ActionServlet not to continue processing request
    }
  }

  /**
   * This is just like the standard implementation that ships with Struts except that
   * it does standard Ardais exception handling.  It logs the exception and forward to
   * the Ardais error page.
   * 
   * @see org.apache.struts.action.RequestProcessor#processInclude(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.apache.struts.action.ActionMapping)
   */
  protected boolean processInclude(
    HttpServletRequest request,
    HttpServletResponse response,
    ActionMapping mapping)
    throws IOException, ServletException {

    try {
      return super.processInclude(request, response, mapping);
    }
    catch (Exception e) {
      if (!ApiLogger.isIgnorableException(e)) {
        ApiLogger.log(
          "Exception caught in BigrRequestProcessor.processInclude at path " + mapping.getPath(),
          e);

        // Make the exception available to a JSP error page in the
        // standard way.
        //
        request.setAttribute("javax.servlet.jsp.jspException", e);

        // Forward to the standard Ardais error page.
        //
        //set the request back to it's normal state if it's currently wrapped,
        //to avoid ClassCastExceptions from ServletContainers if forwarding
        if (request instanceof MultipartRequestWrapper) {
          request = ((MultipartRequestWrapper) request).getRequest();
        }
        processForwardConfig(request, response, mapping.findForward("error"));
      }

      return false; // false => tell ActionServlet not to continue processing request
    }
  }

  /**
   * If the request has an attribute named {@link #ACTION_FORM_POPULATOR_SOURCE_KEY} with a
   * non-null value, then populate the ActionForm instance differently than usual.  If this
   * request attribute is missing or null, do the default population, which involves copying
   * values from request parameters into the ActionForm instance.  If it is non-null,
   * the default population is not done.  Instead,
   * {@link BigrBeanUtilsBean#copyProperties(Object, Object)} is used to copy values from the 
   * object that is the value of the ACTION_FORM_POPULATOR_SOURCE_KEY request attribute to
   * the ActionForm instance.  Once that object is used to populate an ActionForm, the
   * ACTION_FORM_POPULATOR_SOURCE_KEY attribute is removed from the request.
   * 
   * @see org.apache.struts.action.RequestProcessor#processPopulate(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.apache.struts.action.ActionForm, org.apache.struts.action.ActionMapping)
   */
  protected void processPopulate(
    HttpServletRequest request,
    HttpServletResponse response,
    ActionForm form,
    ActionMapping mapping)
    throws ServletException {

    try {
      if (form == null)
        return;

      Object populatorSource = request.getAttribute(ACTION_FORM_POPULATOR_SOURCE_KEY);

      if (populatorSource == null) {
        super.processPopulate(request, response, form, mapping);
      }
      else {
        // Set the servlet of the ActionForm
        form.setServlet(this.servlet);

        // Log what we're doing.
        if (log.isDebugEnabled()) {
          log.debug(" Populating bean properties from a request POPULATOR_SOURCE object");
        }

        // Remove the populator source object from the request.  We only use it once, not on
        // any subsequent resources that might get forwarded to in the same request.
        request.removeAttribute(ACTION_FORM_POPULATOR_SOURCE_KEY);

        form.reset(mapping, request);

        BigrBeanUtilsBean.SINGLETON.copyProperties(form, populatorSource);
      }
    }
    catch (Exception e) {
      if (!ApiLogger.isIgnorableException(e)) {
        ApiLogger.log(
          "Exception caught in BigrRequestProcessor.processPopulate at path " + mapping.getPath(),
          e);

        // There's no way for this method to forward to our standard error page because there's
        // no way for it to return a value that tells the Struts ActionServlet to stop
        // doing further processing on this request.  So we have to re-throw the exception here,
        // which isn't really ideal since then the user will see some ugly error and it won't
        // go through our usual error page logging.

        throw new ServletException(e);
      }
    }
  }

  /**
   * @see org.apache.struts.action.RequestProcessor#processValidate(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.apache.struts.action.ActionForm, org.apache.struts.action.ActionMapping)
   */
  protected boolean processValidate(
    HttpServletRequest request,
    HttpServletResponse response,
    ActionForm form,
    ActionMapping mapping)
    throws IOException, ServletException {

    // This does certain checks regardless of whether formInstance is non-null or
    // the mapping specifies validate == false.  In particular we *always* do a login
    // check for mappings that require the user to be logged in.

    try {
      if ((mapping != null) && !(mapping instanceof BigrActionMapping)) {
        throw new IllegalArgumentException(
          "ActionMapping supplied to a BigrRequestProcessor must be a "
            + "BigrActionMapping: "
            + mapping.getClass().getName());
      }

      // Do as much authorization checking here as possible.  We can't check
      // everything yet at this point, more detailed checks may be done in
      // BigrAction.verifyAuthorization or in user-written Action code.
      //
      {
        // Determine the type code that we'll pass to the commonPageActions
        // method.  This should be "D" for actions that require the user
        // to be logged in and "N" for actions that don't.
        //
        boolean authorizationFailed = false;
        String commonPageActionsTypeCode =
          (((BigrActionMapping) mapping).isLoginRequired() ? "D" : "N");

        boolean checkLogin =
          com.ardais.bigr.orm.helpers.FormLogic.commonPageActionsNoForward(
            request,
            response,
            commonPageActionsTypeCode);

        if (!checkLogin) {
          authorizationFailed = true;
        }

        if (authorizationFailed) {
          // Forward to the standard Ardais session timeout page.  While
          // we could get here because somebody got their hands on a URL
          // for an action they aren't authorized to perform, the more
          // likely situation is that the user was validly logged in and
          // then their session timed out.  We don't have a good way to
          // tell the difference between these two situations, so we make
          // the kind assumption that their session timed out rather than
          // that they're trying to hack the system.

          //set the request back to it's normal state if it's currently wrapped,
          //to avoid ClassCastExceptions from ServletContainers if forwarding
          if (request instanceof MultipartRequestWrapper) {
            request = ((MultipartRequestWrapper) request).getRequest();
          }
          // Forward to "sessionTimeout".  sessionTimeout is a global forward, get it
          // directly from the moduleConfig rather than from the mapping to prevent an
          // accidental local override.
          ForwardConfig forwardConfig = moduleConfig.findForwardConfig("sessionTimeout");
          processForwardConfig(request, response, forwardConfig);

          return false; // false => tell ActionServlet not to continue processing request
        }
      } // end of authorization checks

      // If we get here there were no authorization problems detected, so
      // proceeed with processing the validation.
      //
      return super.processValidate(request, response, form, mapping);
    }
    catch (Exception e) {
      if (!ApiLogger.isIgnorableException(e)) {
        ApiLogger.log(
          "Exception caught in BigrRequestProcessor.processValidate at path " + mapping.getPath(),
          e);

        // Make the exception available to a JSP error page in the
        // standard way.
        //
        request.setAttribute("javax.servlet.jsp.jspException", e);

        // Forward to the standard Ardais error page.
        //
        //set the request back to it's normal state if it's currently wrapped,
        //to avoid ClassCastExceptions from ServletContainers if forwarding
        if (request instanceof MultipartRequestWrapper) {
          request = ((MultipartRequestWrapper) request).getRequest();
        }
        // Forward to "error".  error is a global forward, get it
        // directly from the moduleConfig rather than from the mapping to prevent an
        // accidental local override.
        ForwardConfig forwardConfig = moduleConfig.findForwardConfig("error");
        processForwardConfig(request, response, forwardConfig);
      }

      return false; // false => tell ActionServlet not to continue processing request
    }
  }

}
