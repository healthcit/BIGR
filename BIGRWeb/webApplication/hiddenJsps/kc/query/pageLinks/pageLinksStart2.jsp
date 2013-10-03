<%@ page import="com.gulfstreambio.kc.web.support.FormContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContextStack" %>
<%@ page import="com.gulfstreambio.kc.web.support.PageLink" %>
<%
FormContextStack stack = FormContextStack.getFormContextStack(request);
FormContext formContext = stack.peek();
PageLink parentPageLink = formContext.getParentPageLink();
%>
<div id="<%=parentPageLink.getContentsId()%>" class="kcPages2" <%=parentPageLink.isSelected()?"":"style=\"display:none;\"" %>><ul>
