<%@ page import="com.ardais.bigr.api.Escaper" %>
<%@ page import="com.ardais.bigr.util.UniqueIdGenerator" %>
<%@ page import="com.gulfstreambio.kc.det.DataElementTaxonomy" %>
<%@ page import="com.gulfstreambio.kc.det.DetService" %>
<%@ page import="com.gulfstreambio.kc.det.DetValueSetValue" %>
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

String value = elementContext.getValue();
DataElementTaxonomy det = DetService.SINGLETON.getDataElementTaxonomy();
DetValueSetValue[] standardValues = det.getSystemStandardValues().getValues();

//determine if the standard value choices should be shown.  This is true if the
//existing value is one of the standard values or if the data element has been
//set up to show the standard values.
boolean displayStandardValues = formContext.getDataFormDefinitionDataElement().isDisplayStandardValueSet();
boolean existingValueIsAStandardValue = false;
for (int i = 0; i  < standardValues.length; i++) {
  String svCui = standardValues[i].getCui();
  if (svCui.equals(value)) {
    existingValueIsAStandardValue = true;
  }
}
%>
<div class="kcStandard">
<%
if (displayStandardValues || existingValueIsAStandardValue) {
  for (int i = 0; i  < standardValues.length; i++) {
    String svCui = standardValues[i].getCui();
    //no longer show the "See note" standard value choice, unless there is an existing value for
    //this data element and the value is the code for See Note (SWP-1021)
    if (DataElementTaxonomy.SYSTEM_STANDARD_VALUE_SEE_NOTE.equalsIgnoreCase(svCui) &&
      !svCui.equals(value)) {
      continue;
    }
    String id = idgen.getUniqueId();
    elementContext.setHtmlIdStandard(id);
%>
<input type="radio" id="<%=id%>" name="<%=name%>" value="<%=svCui%>" <%=(svCui.equals(value)) ? "checked" : ""%>/>
<%=Escaper.htmlEscape(det.getCuiDescription(svCui))%>
<%
  }
}
%>
</div> <!--  kcStandard -->