<%@ page language="java"%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Procedure Hierarchy</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" CONTENT="no-cache">
<meta http-equiv="Pragma" CONTENT="no-cache">

<script>
<!--
var preSelected;
function setFocus()
{
	if(preSelected!=null)
	document.location.hash="#"+preSelected;
}
function selectValue(str) {
	window.returnValue = str;
	window.close();
}

//-->
</script>
<script language="JavaScript" src="<html:rewrite page="/js/menu.js"/>"></script>
</head>
<jsp:include page="/hiddenJsps/orm/DDCHierarchy/HierarchyBuilder.jsp" flush="true"/>
</html>
