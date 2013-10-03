<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.configuration.SystemConfiguration" %>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.kc.form.def.TagTypes" %>
<%@ page import="com.ardais.bigr.kc.form.helpers.FormUtils" %>
<%@ page import="com.gulfstreambio.gboss.GbossFactory" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%
   // This page doesn't have a menu item in BIGR, you have to know its URL
   // and invoke it directly.  It causes cached data (such as DxTc Hierarchy
   // and PDC Lookup) to be refreshed.  Care should be taken to not call
   // this page casually, it is expensive.  Possibly we'll want a better
   // way to do this someday, that isn't so prone to, for example, a
   // web robot that happened to get its hands on this URL.  For now
   // we use a simple mechanism:  if the page isn't passed a request
   // parameter names "arg" whose value is "refresh", then it doesn't
   // refresh anything (and puts up a message saying so).

   com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"N"); // "N" => user not required to be logged in
%>
<%
String paramArg = ApiFunctions.safeString(request.getParameter("arg"));
boolean isArgOk = (paramArg.equals("refresh"));
if (isArgOk) {
    SystemConfiguration.initOrRefresh();
    //SWP-865 - cache all registration (and annotation) form definitions
    List formDefs = FormUtils.getRegistrationFormDefinitions(null, TagTypes.DOMAIN_OBJECT_VALUE_DONOR);
    formDefs = FormUtils.getRegistrationFormDefinitions(null, TagTypes.DOMAIN_OBJECT_VALUE_CASE);
    formDefs = FormUtils.getRegistrationFormDefinitions(null, TagTypes.DOMAIN_OBJECT_VALUE_SAMPLE);
    formDefs = FormUtils.getAnnotationFormDefinitions(null, TagTypes.DOMAIN_OBJECT_VALUE_DONOR);
    formDefs = FormUtils.getAnnotationFormDefinitions(null, TagTypes.DOMAIN_OBJECT_VALUE_CASE);
    formDefs = FormUtils.getAnnotationFormDefinitions(null, TagTypes.DOMAIN_OBJECT_VALUE_SAMPLE);
}
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Refresh BIGR Caches</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<body class="bigr">
<%= new java.util.Date() %>:
<% if (isArgOk) { %>
The master data caches were successfully refreshed.
<% } else { %>
<b>ERROR: Invalid page parameters, the caches have NOT been refreshed.</b>
<% } %>
</body>
</html>
