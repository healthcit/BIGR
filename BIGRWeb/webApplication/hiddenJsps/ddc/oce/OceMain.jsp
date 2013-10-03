<%@ page language="java" autoFlush="true" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<%
  	response.setHeader("Cache-Control", "no-cache");
  	response.setHeader("Pragma", "no-cache");
  	response.setDateHeader("Expires", 0);
%>
<frameset rows="30%,*" frameborder="NO" border="0" framespacing="0"> 
  <frame name="topFrame"  scrolling="AUTO" src="<html:rewrite page="/oce/oceMenu.do"/>">
  <frame name="mainFrame" src="<html:rewrite page="/oce/oceBlank.do"/>">
</frameset>
<noframes><body bgcolor="#FFFFFF" text="#000000">

</body></noframes>
</html>
