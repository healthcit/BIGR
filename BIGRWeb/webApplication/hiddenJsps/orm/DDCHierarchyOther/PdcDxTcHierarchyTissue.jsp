<%@ page language="java"%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Tissue Hierarchy</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" CONTENT="no-cache">
<meta http-equiv="Pragma" CONTENT="no-cache">

<script language="JavaScript">
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

//-->
</script>
<script language="JavaScript" src="<html:rewrite page="/js/menu.js"/>"></script>
</head>
<jsp:include page="/hiddenJsps/orm/DDCHierarchy/HierarchyBuilder.jsp" flush="true"/>
</html>
