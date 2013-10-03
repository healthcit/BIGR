<%@ page import="com.gulfstreambio.kc.form.def.query.QueryFormDefinitionCategory" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContextStack" %>
<%
FormContextStack stack = FormContextStack.getFormContextStack(request);
QueryFormDefinitionCategory category = stack.peek().getQueryFormDefinitionCategory();
%>
<div class="kcHeading">
<h1><%=category.getDisplayName()%></h1>
