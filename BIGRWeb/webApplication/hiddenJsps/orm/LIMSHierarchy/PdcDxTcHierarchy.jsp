<%@ page language="java" import="java.util.Vector " %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>DxTc Hierarchy</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="javaScript">
<!--
function SelectValue(str)
{
	window.opener.document.forms[0].Type.focus();
	window.opener.document.forms[0].Type.value=str;
	window.close();
}
-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
  <div align="center"><font color="#FF0000"><%= ((request.getAttribute("myError")!= null)?request.getAttribute("myError"):"") %></font><br></div>
  
<table class="background" cellpadding="0" cellspacing="0" border="0">
  <tr> 
    <td  valign="top"> 
      <table class="foreground" border="0" cellpadding="3" cellspacing="1">
        <% Vector vDxTc = (Vector) request.getAttribute("DxTcHierarchy"); %>
        <% for (int i=0; i<vDxTc.size(); i++){ %>
        <% com.ardais.bigr.orm.assistants.DxTcAsst asst = (com.ardais.bigr.orm.assistants.DxTcAsst) vDxTc.elementAt(i); %>
        <%if (asst.getStep()==1) {%>
        <tr class="yellow"> 
          <% }else if (asst.getStep()==2 && asst.getLowest_level_flag().trim().equalsIgnoreCase("N")) {%>
        <tr class="green">
		<% }else if (asst.getStep()==2 && asst.getLowest_level_flag().trim().equalsIgnoreCase("Y")) {%>
        <tr class="white"> 
          <% }else if (asst.getStep()==3) {%>
        <tr class="white"> 
          <% }%>
          <td class="level_<%=asst.getStep()%>"><font face="Arial, Helvetica, sans-serif" size="2"> 
            <%if(asst.getLowest_level_flag().trim().equalsIgnoreCase("N")) {%>
            <%=asst.getFullName()%> 
            <% }else if(asst.getLowest_level_flag().trim().equalsIgnoreCase("Y")) {%>
            <a href="#" onClick="SelectValue('<%=asst.getOwnerCode().trim()%>');"><%=asst.getFullName()%></a> 
            <% }%>
            </font></td>
        </tr>
        <% }%>
      </table>
    </td>
  </tr>
</table>
</body>
</html>
