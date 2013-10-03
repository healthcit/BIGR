<%
// Renders the summary for a single data element.
%>
<%@ page import="com.gulfstreambio.kc.det.DetAdeElement" %>
<%@ page import="com.gulfstreambio.kc.det.DetAde" %>
<%@ page import="com.gulfstreambio.kc.form.Ade" %>
<%@ page import="com.gulfstreambio.kc.form.DataElement" %>
<%@ page import="com.gulfstreambio.kc.form.DataElementValue" %>
<%@ page import="com.gulfstreambio.kc.web.support.DataFormAdeElementContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.DataFormDataElementContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContextStack" %>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.api.Escaper" %>
<%@ page import="java.util.Iterator" %>
<%@ taglib uri="/kctaglib" prefix="kc" %>
<% 
FormContextStack stack = FormContextStack.getFormContextStack(request);
FormContext formContext = stack.peek();
DataFormDataElementContext dataElementContext = formContext.getDataFormDataElementContext();
String valueSummary = dataElementContext.getValueDescription();
%>

<kc:dataElementSummary>
<%
// If the data element has no value or one of the standard values, or it does
// not have an ADE, display it in two simple cells.
if (ApiFunctions.isEmpty(valueSummary) 
    || dataElementContext.isValueStandardValue()
    || !dataElementContext.isHasAde()) {
%>
<label class="kcSummary"><%=dataElementContext.getLabel()%>:</label>
<%=(ApiFunctions.isEmpty(valueSummary)) ? "&nbsp;" : Escaper.htmlEscapeAndPreserveWhitespace(valueSummary)%>
<%
}

// Otherwise, the data element has an ADE with at least one value, so display
// all of the values and corresponding ADE values in a table.
else {
%>
<div class="kcElementAdeSummary">
<table cellspacing="0" cellpadding="2">
<%
// Create the first row of the table, which contains the column headers.  The 
// first column is for the data element's value, and the remaining columns are 
// for the ADE element's values.
%>
	<tr class="kcElementAdeHeading">
		<td><%=dataElementContext.getLabelWithUnits()%></td>
<%
DetAde adeMetadata = formContext.getDetDataElement().getAde();
DetAdeElement[] adeElements = adeMetadata.getAdeElements();
for (int i = 0; i < adeElements.length; i++) {
	DetAdeElement adeElementMetadata = adeElements[i];
	formContext.setDetAdeElement(adeElementMetadata);
	stack.push(formContext);
	formContext = stack.peek();
	DataFormAdeElementContext adeElementContext = formContext.getDataFormAdeElementContext();
%>
    <td><%=adeElementContext.getLabelWithUnits()%></td>
<%
	stack.pop();
}
%>
  </tr>
<%
// Create a row in the table for each data element value with its corresponding
// ADE values.
String[] dataElementValues = dataElementContext.getValues();
int valuesCount = (dataElementValues == null) ? 0 : dataElementValues.length; 
for (int j = 0; j < valuesCount; j++) {
 	String dataElementValueDescription = dataElementContext.getValueDescription(j);
%>
  <tr>
    <td><%=ApiFunctions.isEmpty(dataElementValueDescription) ? "&nbsp;" : Escaper.htmlEscapeAndPreserveWhitespace(dataElementValueDescription)%></td>
<%
	// Iterate over all ADE elements within the data element.
	for (int i = 0; i < adeElements.length; i++) {
		DetAdeElement adeElementMetadata = adeElements[i];
		formContext.setDetAdeElement(adeElementMetadata);
  	Ade ade = null;
	  DataElement dataElement = formContext.getDataElement();
	  if ((dataElement != null) && dataElement.isElementValueExists(j)) {
	    ade = ((DataElementValue) dataElement.getElementValue(j)).getAde();
	  }
		if (ade != null) {
			formContext.setAdeElement(ade.getAdeElement(adeElementMetadata.getCui()));
		}
		stack.push(formContext);
		formContext = stack.peek();
		DataFormAdeElementContext adeElementContext = formContext.getDataFormAdeElementContext();
		String adeElementValueDescription = adeElementContext.getValueDescription();
%>
    <td class="adeElementValue"><%=ApiFunctions.isEmpty(adeElementValueDescription) ? "&nbsp;" :  Escaper.htmlEscapeAndPreserveWhitespace(adeElementValueDescription)%></td>
<%
		stack.pop();
	}
%>
	</tr>
<%
}
%>
</table>
</div>
<%
}
String note = dataElementContext.getValueNote();
if (!ApiFunctions.isEmpty(note)) {
%>
<div class="kcElementNote">
Note: <%=Escaper.htmlEscapeAndPreserveWhitespace(note)%>
</div>
<%
}
%>
</kc:dataElementSummary>
