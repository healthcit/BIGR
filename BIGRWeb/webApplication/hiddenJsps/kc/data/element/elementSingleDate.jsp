<%@ page import="com.ardais.bigr.util.UniqueIdGenerator" %>
<%@ page import="com.gulfstreambio.kc.web.support.DataFormElementContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContextStack" %>
<%@ page import="com.gulfstreambio.kc.web.support.WebUtils" %>
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

String imageSrc = WebUtils.getFullPath(request.getContextPath()) + "/images/calendar.gif";
String imageOnclick = 
  "openCalendar('" + id + "');$('" + id + "').fireEvent('onclick');";
%>
<input type="text" id="<%=id%>" name="<%=name%>" value="<%=value%>" size="12" maxlength="12"/>
&nbsp;<img src="<%=imageSrc%>" border="0" style="cursor:hand;" onclick="<%=imageOnclick%>"/>
