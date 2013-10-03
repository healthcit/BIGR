<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<%
String inc = (String) request.getParameter("inc");
String room = (String) request.getParameter("roomlist");
String freezer = (String) request.getParameter("freezerlist");
String drawer = (String) request.getParameter("drawerlist");
String slot = (String) request.getParameter("slotlist");
String hiddenName = (String) request.getParameter("hiddenName");
if(hiddenName == null || hiddenName.equals("")){
	hiddenName = request.getParameter("hiddenName");
}
%>
<script language="JavaScript">
<!--
if (!window.opener.closed) {
	myHidden = '<input type="hidden" name="freezer<%=inc%>" value="<%=freezer%>"><input type="hidden" name="room<%=inc%>" value="<%= room%>"><input type="hidden" name="drawer<%=inc%>" value="<%= drawer%>"><input type="hidden" name="slot<%=inc%>" value="<%= slot%>">'
	window.opener.document.all.roomID<%=inc%>.innerHTML = '<%= room %>';
	window.opener.document.all.freezerID<%=inc%>.innerHTML = '<%= freezer %>';
	window.opener.document.all.drawerID<%=inc%>.innerHTML = '<%= drawer %>';
	window.opener.document.all.slotID<%=inc %>.innerHTML = '<%= slot %>';
	window.opener.document.all.<%= hiddenName%>.innerHTML = myHidden;
}
window.close();
//-->
</script>
<body bgcolor="#FFFFFF" text="#000000">
</body>
</html>