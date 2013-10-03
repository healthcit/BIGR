<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.iltds.helpers.FormLogic" %>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<html>
<head>
<title>Message</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<html:rewrite page="/css/bigr.css"/>" type="text/css">
</head>

<body class="bigr">
<% if (request.getAttribute("org.apache.struts.action.ERROR") != null) { %>
<p>The following problems were found:
<table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
<tr class="yellow">
  <td colspan="2">
    <font color="#FF0000">
      <b><html:errors/></b>
    </font>
  </td>
</tr>
<% } %>
<logic:present name="com.ardais.BTX_ACTION_MESSAGES">
<tr class="yellow"> 
  <td colspan="2" align="center"> 
    <font color="#FF0000"><b><bigr:btxActionMessages/></b></font>
  </td>
</tr>
</table>
</logic:present>
</body>
</html>