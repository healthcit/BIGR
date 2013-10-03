<%@ page language="java"%>
<%@ page import="com.ardais.bigr.orm.databeans.ConsentDatabean"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.Vector"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Consent Text</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script>

function onPageLoad() {
  //alert("entering consentText page");
  document.forms[0].irbProtocolID.value = <%=request.getAttribute("irbProtocolID")%>;
  document.forms[0].consentID.value = <%=request.getAttribute("consentID")%>;
}

</script>
</head>
<body class="bigr" onLoad="onPageLoad();">
<form name="form1" method="post" action="<html:rewrite page='/orm/Dispatch'/>">
  <input type="hidden" name="op" value="WriteConsentTexttoIRB">
  <input type="hidden" name="irbProtocolID" value="">
  <input type="hidden" name="consentID" value="">
  <table border="0" cellspacing="0" cellpadding="0" class="background" align="center">
    <tr> 
      <td> 
        <table border="0" cellspacing="1" cellpadding="3" class="foreground" width="100%">
		  <tr class="yellow"> 
            <td align="center"> 
			  <b><%= ((request.getAttribute("myError")!=null)?request.getAttribute("myError"):" Consent Text ") %></b>
			</td>
          </tr>
          <tr class="white">
          	<td align="left">
              <textarea name="consentText" rows="20" cols="80"><%=( (request.getAttribute("consentText")== null)?"Please enter consent Text":request.getAttribute("consentText") )%></textarea>
            </td>
          </tr>
          <tr class="white"> 
            <td align="center"> 
              <input type="submit" name="Submit" value="Submit">
              <input type="reset" name="Submit2" value="Cancel" onClick="window.close()">
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</form>
</body>
</html>