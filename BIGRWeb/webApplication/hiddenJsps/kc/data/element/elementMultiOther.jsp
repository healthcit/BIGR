<%@ page import="com.ardais.bigr.util.UniqueIdGenerator" %>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.api.Escaper" %>
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
String textId = idgen.getUniqueId();
String containerId = idgen.getUniqueId();
String addId = idgen.getUniqueId();
String removeId = idgen.getUniqueId();
elementContext.setHtmlIdOther(id);
elementContext.setHtmlIdOtherContainer(containerId);
elementContext.setHtmlIdOtherText(textId);
elementContext.setHtmlIdOtherAdd(addId);
elementContext.setHtmlIdOtherRemove(removeId);

boolean isBroadValueSetDisplayed = 
  !elementContext.isAllValuesInNarrowValueSet() 
  && !elementContext.isAllValuesInStandardValueSet();
%>
<div id="<%=containerId%>" <%=isBroadValueSetDisplayed ? "" : "style=\"display:none;\""%>>
  <table><tr>
  <td>
  	<%=Escaper.htmlEscape(elementContext.getOtherValueDescription())%><br>
	  <input type="text" id="<%=textId%>" value="" size="40" maxlength="200" <%=elementContext.isValueNoValue() ? "disabled" : "" %>>
	</td>
	<td align="center">
	<input type="button" id="<%=addId%>" value="Add >" class="kcButton" <%=elementContext.isValueNoValue() ? "disabled" : "" %>>
	<p style="margin-top: 0.1em">
	<input type="button" id="<%=removeId%>" value="< Remove" class="kcButton" <%=elementContext.isValueNoValue() ? "disabled" : "" %>>
	</td>
	<td>
 	<select id="<%=id%>" name="<%=name%>" multiple>
<%
String[] valueOthers = elementContext.getValueOthers();
if (valueOthers != null) {
	for (int j = 0; j < valueOthers.length; j++) {
	  String valueOther = valueOthers[j];
	  if (!ApiFunctions.isEmpty(valueOther)) {
%>
	  <option value="<%=Escaper.htmlEscape(valueOther)%>"><%=Escaper.htmlEscape(valueOther)%></option>
<%
	  }
	}
}
%>
  </select>
  </td>
  </tr></table>
</div>