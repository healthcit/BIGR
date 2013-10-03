<%@ page import="com.gulfstreambio.kc.web.support.WebUtils" %>
<%
String eventHandler = 
  WebUtils.getJavaScriptReferenceQueryElements() + ".commonEventHandler();";
%>
<div class="kcElements" onclick="<%=eventHandler%>" onkeyup="<%=eventHandler%>">