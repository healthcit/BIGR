<%@ page import="com.ardais.bigr.util.UniqueIdGenerator" %>
<%@ page import="com.ardais.bigr.api.Escaper" %>
<%@ page import="com.gulfstreambio.kc.web.support.DataFormElementContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContextStack" %>
<%@ page import="com.gulfstreambio.kc.web.support.KcUiContext" %>
<%@ page import="javax.servlet.jsp.JspException" %>
<%
FormContextStack stack = FormContextStack.getFormContextStack(request);
FormContext formContext = stack.peek();
DataFormElementContext elementContext = formContext.getDataFormElementContext();

UniqueIdGenerator idgen = KcUiContext.getKcUiContext(request).getUniqueIdGenerator();
String name = idgen.getUniqueId();
String id = idgen.getUniqueId();
String containerId = idgen.getUniqueId();
elementContext.setHtmlIdOther(id);
elementContext.setHtmlIdOtherContainer(containerId);

boolean disabled = !elementContext.getOtherValueCui().equals(elementContext.getValue());
String valueOther = elementContext.getValueOther();
valueOther = (disabled) ? "N/A" : valueOther;
valueOther = (valueOther == null) ? "" : valueOther;

boolean isBroadValueSetDisplayed = 
  !elementContext.isAllValuesInNarrowValueSet() 
  && !elementContext.isAllValuesInStandardValueSet();
%>
<div id="<%=containerId%>" <%=isBroadValueSetDisplayed ? "" : "style=\"display:none;\""%>>
  <%=Escaper.htmlEscape(elementContext.getOtherValueDescription())%><br>
  <input type="text" id="<%=id%>" name="<%=name%>" value="<%=valueOther%>" size="40" maxlength="200" <%=disabled ? "disabled" : ""%>/>
</div>