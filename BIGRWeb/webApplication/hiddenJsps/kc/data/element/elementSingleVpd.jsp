<%@ page import="com.ardais.bigr.util.UniqueIdGenerator" %>
<%@ page import="com.gulfstreambio.kc.web.support.DataFormElementContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContextStack" %>
<%@ page import="com.gulfstreambio.kc.web.support.KcUiContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.WebUtils" %>
<%
FormContextStack stack = FormContextStack.getFormContextStack(request);
FormContext formContext = stack.peek();
DataFormElementContext elementContext = formContext.getDataFormElementContext();

String value = elementContext.getValue();
if (value == null || elementContext.isValueStandardValue()) {
  value = "";
}

UniqueIdGenerator idgen = KcUiContext.getKcUiContext(request).getUniqueIdGenerator();
String name = idgen.getUniqueId();
String id = idgen.getUniqueId();
elementContext.setHtmlIdPrimary(id);

String imageSrc = WebUtils.getFullPath(request.getContextPath()) + "/images/calendar.gif";
String imageOnclick = 
  "openCalendar('" + id + "', 'true', 'true');$('" + id + "').fireEvent('onclick');";
%>
<input type="text" id="<%=id%>" name="<%=name%>" value="<%=value%>" size="12" maxlength="12"/>
&nbsp;<img src="<%=imageSrc%>" border="0" style="cursor:hand;" onclick="<%=imageOnclick%>"/>

