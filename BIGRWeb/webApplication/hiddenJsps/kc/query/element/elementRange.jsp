<%
// Renders the query criteria controls for a single data element whose query
// criteria is to be captured as a range.
%>
<%@ page import="com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueComparison" %>
<%@ page import="com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueSet" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContextStack" %>
<%@ page import="com.gulfstreambio.kc.web.support.QueryFormElementContext" %>
<% 
FormContextStack stack = FormContextStack.getFormContextStack(request);
FormContext formContext = stack.peek();
QueryFormElementContext elementContext = formContext.getQueryFormElementContext();

String logOp = elementContext.getValueLogicalOperator();
String compOp1 = elementContext.getValueComparisonOperator1();
String compOp2 = elementContext.getValueComparisonOperator2();
String value1 = elementContext.getValue1();
value1 = value1 == null ? "" : value1;
String value2 = elementContext.getValue2();
value2 = value2 == null ? "" : value2;
String eq = QueryFormDefinitionValueComparison.OPERATOR_EQ;
String ne = QueryFormDefinitionValueComparison.OPERATOR_NE;
String gt = QueryFormDefinitionValueComparison.OPERATOR_GT;
String ge = QueryFormDefinitionValueComparison.OPERATOR_GE;
String lt = QueryFormDefinitionValueComparison.OPERATOR_LT;
String le = QueryFormDefinitionValueComparison.OPERATOR_LE;
String and = QueryFormDefinitionValueSet.OPERATOR_AND;
String or = QueryFormDefinitionValueSet.OPERATOR_OR;

%>
<select name="<%=elementContext.getPropertyComparisonOperator1()%>">
  <option value=""></option>
  <option value="<%=eq%>" <%=eq.equals(compOp1) ? "selected" : "" %>>=</option>
  <option value="<%=ne%>" <%=ne.equals(compOp1) ? "selected" : "" %>>&lt;&gt;</option>
	<option value="<%=gt%>" <%=gt.equals(compOp1) ? "selected" : "" %>>&gt;</option>
	<option value="<%=ge%>" <%=ge.equals(compOp1) ? "selected" : "" %>>&gt;=</option>
	<option value="<%=lt%>" <%=lt.equals(compOp1) ? "selected" : "" %>>&lt;</option>
	<option value="<%=le%>" <%=le.equals(compOp1) ? "selected" : "" %>>&lt;=</option>
</select>&nbsp;
<input type="text" name="<%=elementContext.getPropertyValue1()%>" value="<%=value1%>" size="11"/>&nbsp;
<select name="<%=elementContext.getPropertyLogicalOperator()%>">
  <option value="<%=and%>" <%=and.equals(logOp) ? "selected" : "" %>>AND</option>
	<option value="<%=or%>" <%=or.equals(logOp) ? "selected" : "" %>>OR</option>
</select>&nbsp;
<select name="<%=elementContext.getPropertyComparisonOperator2()%>">
  <option value=""></option>
  <option value="<%=eq%>" <%=eq.equals(compOp2) ? "selected" : "" %>>=</option>
  <option value="<%=ne%>" <%=ne.equals(compOp2) ? "selected" : "" %>>&lt;&gt;</option>
	<option value="<%=gt%>" <%=gt.equals(compOp2) ? "selected" : "" %>>&gt;</option>
	<option value="<%=ge%>" <%=ge.equals(compOp2) ? "selected" : "" %>>&gt;=</option>
	<option value="<%=lt%>" <%=lt.equals(compOp2) ? "selected" : "" %>>&lt;</option>
	<option value="<%=le%>" <%=le.equals(compOp2) ? "selected" : "" %>>&lt;=</option>
</select>&nbsp;
<input type="text" name="<%=elementContext.getPropertyValue2()%>" value="<%=value2%>" size="11"/>&nbsp;
