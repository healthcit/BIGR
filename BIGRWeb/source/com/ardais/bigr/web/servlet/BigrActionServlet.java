package com.ardais.bigr.web.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;

public class BigrActionServlet extends ActionServlet {

  /**
   * The logger for all messages logged by this validator.
   */
  protected static Log _logParams = LogFactory.getLog(ApiLogger.BIGR_LOGNAME_HTTP_REQUEST_PARAMS);
  protected static Log _logHeaders = LogFactory.getLog(ApiLogger.BIGR_LOGNAME_HTTP_REQUEST_HEADERS);

  /**
   * Constructor for BIGRActionServlet.
   */
  public BigrActionServlet() {
    super();
  }

  /**
   * Initialize this servlet.  This performs various initializations that
   * are specific to the BIGR application/platform.
   *
   * @exception ServletException if we cannot configure ourselves correctly
   */
  public void init() throws ServletException {
    super.init();

    ApiFunctions.initialize();
  }

  /**
   * Shut down BIGR gracefully.
   */
  public void destroy() {
    super.destroy();
    
    ApiFunctions.shutdownBIGR();
  }
  
  protected void process(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {

    if (_logParams.isDebugEnabled()) {
      logRequestParameters(request);
    }
    if (_logHeaders.isDebugEnabled()) {
      logRequestHeaders(request);
    }
    super.process(request, response);
  }

  private void logRequestParameters(HttpServletRequest request) throws IOException {

    // Put all of the parameter names in a sorted set so they'll be sorted in the log.
    SortedSet paramNames = new TreeSet();
    Enumeration e = request.getParameterNames();
    while (e.hasMoreElements()) {
      paramNames.add((String) e.nextElement());
    }

    _logParams.debug("---------- Request parameters at " + new Date().toString() + " ----------");
    _logParams.debug("Request URI: " + request.getRequestURI());

    // Log the value of each parameter.
    Iterator allParams = paramNames.iterator();
    if (allParams.hasNext()) {
      while (allParams.hasNext()) {
        String paramName = (String) allParams.next();
        // For security reasons, don't log values of things that appear password-related.
        boolean isValueMasked = (paramName.toLowerCase().indexOf("password") >= 0);
        String[] paramValues = request.getParameterValues(paramName);
        if (paramValues.length == 1) {
          _logParams.debug(paramName + "=" + (isValueMasked ? "<masked>" : paramValues[0]));
        }
        else {
          _logParams.debug(paramName);
          for (int i = 0; i < paramValues.length; i++) {
            _logParams.debug(
              "  " + String.valueOf(i) + "=" + (isValueMasked ? "<masked>" : paramValues[i]));
          }
        }
      }
    }

    // No request parameters.
    else {
      _logParams.debug("No request parameters specified!");
    }
  }

  private void logRequestHeaders(HttpServletRequest request) throws IOException {

    _logHeaders.debug("---------- Request headers at " + new Date().toString() + " ----------");

    if (request.getAuthType() != null) {
      _logHeaders.debug("authentiation scheme: " + request.getAuthType());
    }
    else {
      _logHeaders.debug("authentiation scheme: none");
    }
    _logHeaders.debug("character encoding: " + request.getCharacterEncoding());
    int contentLength = request.getContentLength();
    if (contentLength == -1) {
      _logHeaders.debug("content length: unknown");
    }
    else {
      _logHeaders.debug("content length" + String.valueOf(contentLength));
    }
    _logHeaders.debug("content type: " + request.getContentType());
    _logHeaders.debug("context path: " + request.getContextPath());
    _logHeaders.debug("method: " + request.getMethod());
    _logHeaders.debug("query string: " + request.getQueryString());
    _logHeaders.debug("path information: " + request.getPathInfo());
    _logHeaders.debug("path translated: " + request.getPathTranslated());
    _logHeaders.debug("protocol: " + request.getProtocol());
    _logHeaders.debug("remote address: " + request.getRemoteAddr());
    _logHeaders.debug("remote host: " + request.getRemoteHost());
    _logHeaders.debug("remote user: " + request.getRemoteUser());
    _logHeaders.debug("requested session id: " + request.getRequestedSessionId());
    _logHeaders.debug("request URI: " + request.getRequestURI());
    _logHeaders.debug("scheme: " + request.getScheme());
    _logHeaders.debug("server name: " + request.getServerName());
    _logHeaders.debug("server port: " + String.valueOf(request.getServerPort()));
    _logHeaders.debug("servlet path: " + request.getServletPath());

    if (request.isSecure()) {
      _logHeaders.debug("request is secure: true");
    }
    else {
      _logHeaders.debug("request is secure: false");
    }

    if (request.isRequestedSessionIdFromCookie()) {
      _logHeaders.debug("session id from: cookie");
    }
    else if (request.isRequestedSessionIdFromURL()) {
      _logHeaders.debug("session id from: URL");
    }

    if (request.isRequestedSessionIdValid()) {
      _logHeaders.debug("session id is valid: true");
    }
    else {
      _logHeaders.debug("session id is valid: false");
    }

    Enumeration headers = request.getHeaderNames();
    if (headers.hasMoreElements()) {
      _logHeaders.debug("--- RAW HEADERS ---");
      while (headers.hasMoreElements()) {
        String headerName = (String) headers.nextElement();
        _logHeaders.debug(headerName + ": " + request.getHeader(headerName));
      }
    }
  }

}
