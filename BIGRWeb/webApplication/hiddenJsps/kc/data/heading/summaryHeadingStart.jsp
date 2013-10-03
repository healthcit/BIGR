<%@ page import="com.gulfstreambio.kc.form.def.data.DataFormDefinitionCategory" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContextStack" %>
<%
FormContextStack stack = FormContextStack.getFormContextStack(request);
DataFormDefinitionCategory category = stack.peek().getDataFormDefinitionCategory();
%>
<div class="kcHeading">
<h1><%=category.getDisplayName()%></h1>
