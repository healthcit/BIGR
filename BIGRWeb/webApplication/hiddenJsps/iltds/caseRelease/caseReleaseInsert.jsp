<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>


<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script>
function MM_callJS(jsStr) { //v2.0
  return eval(jsStr)
}
myBanner = 'Case Release';
</script>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
</head>
<body class="bigr" onLoad="if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;">
<div align="center"> 
  <table border="0" cellspacing="0" cellpadding="0" class="background">
    <tr> 
      <td> 
        <table border="0" cellspacing="1" cellpadding="3" class="foreground">
         <% if (request.getAttribute("myError")!=null) { %> <tr class="yellow"> 
            <td> 
              <div align="center"><font color="#FF0000"><b><%= request.getAttribute("myError") %></b></font></div>
            </td>
          </tr><% } %>
          <tr class="white"> 
            <td> 
              <div align="center">Case ID <%= ((request.getParameter("consentID_1")!=null)?request.getParameter("consentID_1"):"") %> has been released.</div>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>
</body>
</html>
