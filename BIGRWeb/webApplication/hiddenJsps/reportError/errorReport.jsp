<%@ page language="java" isErrorPage="true" %>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.api.ApiLogger" %>
<%@ page import="com.ardais.bigr.orm.helpers.FormLogic" %>
<%@ page import="com.ardais.bigr.security.SecurityInfo" %>
<%@ page import="com.ardais.bigr.util.WebUtils" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%!
// WARNING:  Don't define any member variables in this area.
// If you do, the values of those variables
// will be shared by all request threads and there is great potential for one
// user's request seeing data that was meant for another user's request.
// Only put method definitions and static code here.

// This counter is used as part of generating a unique error report number later.
// This should only be used by getNextErrorCounter.
//
private static int _errorCounter = 0;

// This is used to synchronized access to _errorCounter.
// This should only be used by getNextErrorCounter.
//
private static Object _errorCounterLock = new Object();

// This method is used to get and increment _errorCounter in
// a synchronized, thread-safe way.  This is a utility method
// to be used only by generateUniqueErrorReportId.
//
private static int getNextErrorCounter() {
  synchronized (_errorCounterLock) {
    _errorCounter++;
    return _errorCounter;
  }
}

// Create a unique id for an error.  The id incorporates both the current
// system time and a counter to ensure uniqueness.
//
private static String generateUniqueErrorReportId() {
  StringBuffer sb = new StringBuffer();
  sb.append(System.currentTimeMillis());
  sb.append('-');
  sb.append(getNextErrorCounter());
  return sb.toString();
}

// END of declaration block.
%>
<%
  // First off, make sure the exception gets logged.  This is particularly important
  // to do before anything else for the exception details that Struts stores in
  // org.apache.struts.Globals.EXCEPTION_KEY.  Here's why.  When certain parts
  // of Struts encounter an exception, they catch the original exception and store
  // it on a request attribute whose name is the value of
  // org.apache.struts.Globals.EXCEPTION_KEY.  Then, the Struts code throws a
  // new JspException that doesn't include any details about what the original
  // exception was.  To give a useful picture of what happened, we need to log both
  // the exception that Struts threw and the original exception that it caught.
  //
  // This is all further complicated by the fact that in the course of normal processing
  // the Struts exception attribute can get set in situations that don't really
  // have a problem at all.  For example, the logic:present tag calls RequestUtils.lookup
  // to see if the information is present.  When the information is NOT present,
  // RequestUtils.lookup throws an exception and sets the
  // org.apache.struts.Globals.EXCEPTION_KEY request attribute.  The logic:present
  // tag then catches this exception and interprets it as the information not being
  // present.  This is part of the logic:present tag's normal designed behavior.  But
  // the result is that the request attribute is set to an exception even though from
  // the application's perspective there was no exception.
  //
  // Because of this, when we find an exception in the Struts request attribute, we have
  // no way of knowing here whether it is related to the exception in the
  // javax.servlet.jsp.jspException request attribute, or whether it is simply something
  // that Struts left behind at some point in the past.  The person investigating the
  // problem from the log entries should be able to puzzle it out, but we can't
  // distinguish these situations in the code here.
  
  String errorReportId = generateUniqueErrorReportId();
  
  SecurityInfo secInfo = WebUtils.getSecurityInfo(request);
  String userId = secInfo.getUsername();
  if (ApiFunctions.isEmpty(userId)) {
    userId = "unknown user";
  }
  
  // fromOp and myError are generally only used by older BIGR code, newer code
  // puts the actual exception object in the "javax.servlet.jsp.jspException"
  // request property.
  //
  String theFromOp = ApiFunctions.safeString((String) request.getAttribute("fromOp"));
  String theMyError = ApiFunctions.safeString((String) request.getAttribute("myError"));
  if (!ApiFunctions.isEmpty(theFromOp) || !ApiFunctions.isEmpty(theMyError)) {
    ApiLogger.log("Error Report #" + errorReportId
      + ": Reported an error to the user (" + userId + ") with fromOp = '"
      + theFromOp + "' and myError = '" + theMyError + "'.");
  }

  Throwable theThrowable = (Throwable) request.getAttribute("javax.servlet.jsp.jspException");
  if (theThrowable != null) {
    ApiLogger.log("Error Report #" + errorReportId
      + ": Reported an exception to the user (" + userId + "):", theThrowable);
    Throwable theStrutsThrowable =
      (Throwable) request.getAttribute(org.apache.struts.Globals.EXCEPTION_KEY);
    if (theStrutsThrowable != null) {
      ApiLogger.log("Error Report #" + errorReportId + ": Here is the Struts exception.  " +
                    "Due to the way Struts works this may be unrelated to the previous " +
                    "exception or not indicate a problem at all, see errorReport.jsp for more details:",
                    theStrutsThrowable);
    }
  }

  // Don't cache the response.
  {
  	FormLogic.noCache(response);
  }
%>
<html>
<head>
<title>Error Report</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
</head>
<body class="bigr">
<div align="center"> 
  <table class="background" cellpadding="0" cellspacing="0" border="0">
    <tr> 
      <td> 
        <table class="foreground" cellpadding="3" cellspacing="1" border="0">
          <tr class="green"> 
            <td colspan="2"> 
              <div align="center"><b>Error Report</b></div>
            </td>
          </tr>
          <tr class="white"> 
            <td colspan="2" class="grey"> 
              <b>There was a problem processing your request.  If you contact customer service
                 regarding the problem, please reference error number <%= errorReportId %>.
              </b>
            </td>
          </tr>
          <logic:present name="fromOp">
	          <tr class="white"> 
	            <td class="grey"> 
	              <b>Error From Operation:</b>
	            </td>
	            <td><bean:write name="fromOp"/></td>
	          </tr>
          </logic:present>
          <logic:present name="myError">
	          <tr class="white"> 
	            <td class="grey"> 
	              <b>Error Message:</b>
	            </td>
	            <td><bean:write name="myError"/></td>
	          </tr>
          </logic:present>
        </table>
      </td>
    </tr>
  </table>
</div>
</body>
</html>
