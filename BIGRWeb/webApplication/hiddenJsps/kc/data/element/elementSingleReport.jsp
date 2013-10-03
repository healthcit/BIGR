<%@ page import="com.ardais.bigr.util.UniqueIdGenerator" %>
<%@ page import="com.gulfstreambio.kc.web.support.DataFormElementContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContextStack" %>
<%@ page import="com.gulfstreambio.kc.web.support.KcUiContext" %>
<%
FormContextStack stack = FormContextStack.getFormContextStack(request);
FormContext formContext = stack.peek();
DataFormElementContext elementContext = formContext.getDataFormElementContext();

UniqueIdGenerator idgen = KcUiContext.getKcUiContext(request).getUniqueIdGenerator();
String name = idgen.getUniqueId();
String id = idgen.getUniqueId();
elementContext.setHtmlIdPrimary(id);

String value = elementContext.getValue();
if (value == null || elementContext.isValueStandardValue()) {
  value = "";
}
%>
<textarea id="<%=id%>" name="<%=name%>" cols="60" rows="8"><%=value%></textarea>