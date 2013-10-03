<%@ page language="java"%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>

<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>View Consent Text</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

</head>
<body class="bigr">
<table border="0" cellspacing="1" cellpadding="3" class="foreground">
  <tr> 
	  <td align="center"> 
		<b><%=((request.getAttribute("myError")!=null)?request.getAttribute("myError"):"Consent Text")%></b>
	  </td>
  </tr>
  <tr>
      <td>
        <textarea readonly name="consentText" rows="24" cols="80"><%=((request.getAttribute("consentText")== null)?"This is either an Unlinked Case or the Consent Text is not available":(request.getAttribute("consentText")))%></textarea>
      </td>
  </tr>
  <tr> 
     <td align="center"> 
       <input type="reset" name="Close" value="Close" onClick="window.close()">
     </td>
  </tr>
</table>
</body>
</html>
