<%
// Renders the query criteria controls for a single data element whose query
// criteria is to be captured as a discrete set of values.
%>
<%@ page import="com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueSet" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContextStack" %>
<%@ page import="com.gulfstreambio.kc.web.support.QueryFormElementContext" %>
<%@ taglib uri="/kctaglib" prefix="kc" %>
<% 
FormContextStack stack = FormContextStack.getFormContextStack(request);
FormContext formContext = stack.peek();
QueryFormElementContext elementContext = formContext.getQueryFormElementContext();

String logOp = elementContext.getValueLogicalOperator();
String and = QueryFormDefinitionValueSet.OPERATOR_AND;
String or = QueryFormDefinitionValueSet.OPERATOR_OR;
%>
<input type="radio" name="<%=elementContext.getPropertyLogicalOperator()%>" value="<%=or%>" <%=(!elementContext.isValueAny() && (or.equals(logOp) || logOp == null)) ? "checked" : "" %>/>is one of&nbsp;&nbsp;
<input type="radio" name="<%=elementContext.getPropertyLogicalOperator()%>" value="<%=and%>" <%=(!elementContext.isValueAny() && and.equals(logOp)) ? "checked" : "" %>/>is NOT one of
<kc:queryValueSet/>
