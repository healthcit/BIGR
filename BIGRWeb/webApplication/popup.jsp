<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"N"); // "N" => user not required to be logged in
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>HealthCare IT Corporation BIGR Library</title>
</head>
<body class="bigr">
<div align="center"><p>&nbsp;
<p><table border="0" cellspacing="0" cellpadding="0" class="background" width="300">
    <tr> 
      <td> 
        <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
          <tr align="center" class="yellow"> 
            <% String imageName = "/images/" + request.getParameter("message") + ".gif"; %>
            <td><html:img page="<%= imageName %>"/></td>
		  </tr>
		</table>
      </td>
    </tr>
   </table>
</div>
</body>
</html>
