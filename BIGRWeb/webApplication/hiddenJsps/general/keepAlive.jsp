<%@ page language="java"
  import="com.ardais.bigr.api.*,com.ardais.bigr.security.*,com.ardais.bigr.util.WebUtils"%>
<%
  // This page doesn't require the user to be logged in, it is just here to give
  // a simple, non-cached page that we can ping as part of our session-keepalive
  // mechanism.
  
  // Don't require login for this page.  This is important because below we
  // return a different page based on whether there's a user logged in or
  // not.  We use this difference in MenuLHS.jsp to turn off the keepAlive
  // timer if we detect that the user's session is gone (for example, the
  // session was being kept alive by keepalives alone long enough so that
  // we expired the session anyways, or the server was restarted).
  //
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"N"); // "N" => user not required to be logged in
%>
<html>
<head>
<title>BIGR(TM)</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<body>
<% if (WebUtils.getSecurityInfo(request).isInRoleNone()) {
     // no valid user session, send the string STOP_KEEPALIVE in the result page.
     // This string in the response is the signal that MenuLHS.jsp keys on to
     // turn off the keepAlive requests.  There's no reason to keep doing them
     // once the session is gone. %>
STOP_KEEPALIVE
<% } else { // we have a valid user session %>
Done.
<% } %>
</body>
</html>
