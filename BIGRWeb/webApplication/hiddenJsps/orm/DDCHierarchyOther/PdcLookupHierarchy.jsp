<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<html>
<head>
<title>Pdc Lookup List</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" CONTENT="no-cache">
<meta http-equiv="Pragma" CONTENT="no-cache">
<link rel="stylesheet" type="text/css"
	href="<html:rewrite page="/css/bigr.css"/>">
<script>
<!--
var preSelected;
function setFocus()
{
	if(preSelected!=null)
	document.location.hash="#"+preSelected;
}
function selectValue(str) {
	if(parent.opener.document.forms[0].componentName==null) {
		if(str.indexOf('91723000')!=-1) {
			alert("You have selected 'Other Tissue' as a Tissue Type .\nPlease specify the Tissue name(s) in the Other Tissue box .");
			window.opener.document.forms[0].OtherTissue.disabled=false;
			window.opener.document.forms[0].TissueType.value=str;
			window.opener.document.forms[0].OtherTissue.value="";
			window.opener.document.forms[0].OtherTissue.focus();
		}
		else {
			window.opener.document.forms[0].TissueType.focus();
			window.opener.document.forms[0].TissueType.value=str;
			window.opener.document.forms[0].OtherTissue.value='N/A';
			window.opener.document.forms[0].OtherTissue.disabled=true;
		}
		window.close();
	}
	else {
		window.opener.document.forms[0].elements['list'+window.opener.document.forms[0].componentName.value].value=str;
		window.opener.document.forms[0].elements['update'+window.opener.document.forms[0].componentName.value].checked=true;
		window.opener.document.forms[0].elements['inapp'+window.opener.document.forms[0].componentName.value].checked=false;
		window.opener.document.forms[0].elements['concept'+window.opener.document.forms[0].componentName.value].checked=false;
		window.opener.document.forms[0].elements['hold'+window.opener.document.forms[0].componentName.value].checked=false;
		window.opener.document.forms[0].elements['Open'+window.opener.document.forms[0].componentName.value].focus();
		window.opener.setPreSelected(str);
		window.opener.toggleUpdateButton();
		preSelected=str;
	}	
}
function closeWindow(){
  window.close();
}

</script>
<body onFocus="setFocus();">
<form name="pdcLookup">
<div align="center">
<table border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
  	<tr class="darkgreen">
    	<td>
      	<table border="0" cellspacing="1" cellpadding="0" class="foreground">
	<tr class="yellow">
		<td align="center"><a href="#" onClick="window.close();">Close</a></td>
	</tr>
	<logic:present name="PdcLookupHierarchy">
		<logic:iterate name="PdcLookupHierarchy" property="iterator" id="legalValue">			
				<tr class="green">
					<td>
					  <a name="<bean:write name="legalValue" property="value" />" href="#" onClick="selectValue('<bean:write name="legalValue" property="value" />');">
					   <bean:write name="legalValue" property="displayValue" />
					  </a> 
					</td>					
				</tr>				
		</logic:iterate>
	</logic:present>
</table>
</td>
</tr>
</table>
</div>
</form>
</body>
</html>
