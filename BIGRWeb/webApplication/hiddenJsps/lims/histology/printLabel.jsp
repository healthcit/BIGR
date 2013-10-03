<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.iltds.helpers.FormLogic" %>

<%
    com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>

<%@ taglib uri="/tld/struts-html"  prefix="html" %>

<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Print Label</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<% if (request.getAttribute("org.apache.struts.action.ERROR") != null) { %>
<body class="bigr">
  <table class="fgTableSmall" cellpadding="2" cellspacing="0" border="0" width="300">
    <tr class="yellow">
      <td>
        <div align="left"><font color="#FF0000"><b><html:errors/></b></font></div>
      </td>
    </tr>
    <tr>
      <td>
        <div align="center">
          <html:cancel style="font-size:xx-small; width: 90px;" property="cancel" value="Close" onclick="window.close()"/>
        </div>
      </td>
    </tr>
  </table>
</body>
<% } else { %>
<body class="bigr" onload="window.setTimeout('window.close()', 1000);">
  <table align="center" border="0" cellspacing="0" cellpadding="0" class="background" width="300">
    <tr> 
      <td> 
        <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
          <tr align="center" class="yellow"> 
            <td><html:img page="/images/processing.gif"/></td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</body>
<% } %>
</html>
