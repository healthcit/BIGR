<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.api.Escaper" %>
<%@ page import="com.ardais.bigr.util.UniqueIdGenerator" %>
<%@ page import="com.gulfstreambio.kc.det.DetAdeElement" %>
<%@ page import="com.gulfstreambio.kc.det.DetAde" %>
<%@ page import="com.gulfstreambio.kc.form.Ade" %>
<%@ page import="com.gulfstreambio.kc.form.DataElement" %>
<%@ page import="com.gulfstreambio.kc.form.DataElementValue" %>
<%@ page import="com.gulfstreambio.kc.web.support.DataFormAdeElementContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.DataFormDataElementContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContextStack" %>
<%@ page import="com.gulfstreambio.kc.web.support.KcUiContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.WebUtils" %>
<%@ taglib uri="/kctaglib" prefix="kc" %>
<%
FormContextStack stack = FormContextStack.getFormContextStack(request);
FormContext formContext = stack.peek();
DataFormDataElementContext dataElementContext = formContext.getDataFormDataElementContext();

UniqueIdGenerator idgen = KcUiContext.getKcUiContext(request).getUniqueIdGenerator();
String adeTableId = idgen.getUniqueId();
String adeContainerId = idgen.getUniqueId();
dataElementContext.setHtmlIdAdeTable(adeTableId);
dataElementContext.setHtmlIdAdeContainer(adeContainerId);

String[] dataElementValues = dataElementContext.getValues();
int valuesCount = (dataElementValues == null) ? 0 : dataElementValues.length; 

// Create the table element even if there are no ADEs so that ADEs can be added to the
// table by JavaScript in the browser. 
boolean isDisplayTable = 
  (!dataElementContext.isValueStandardValue() && valuesCount > 0);
%>
<div id="<%=adeContainerId%>" class="kcElementAdeSummary" <%=isDisplayTable ? "" : "style=\"display: none;\""%>>
<table cellspacing="0" cellpadding="0" id="<%=adeTableId%>">
<%
// Create the first row of the table, which contains the column headers.  The 
// first column is for the data element's value, the remaining columns beside 
// the last are for the ADE element's values, and the last is for the edit and 
// delete buttons.
%>
	<tr class="kcElementAdeHeading">
		<td>
      <%=dataElementContext.getLabelWithUnits()%>
		</td>
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
		<td>
			<%=adeElementContext.getLabelWithUnits()%>
		</td>
<%
	stack.pop();
}	
%>
		<td/>
	</tr>
<%
// If there are any ADEs, then create a table row for each.
if (isDisplayTable) {
  StringBuffer scriptBuf = WebUtils.getJavaScriptBuffer(pageContext);

  // Create the remaining rows of the table, one for each data element value 
  // and the associated ADE values. 
	DataElement dataElement = formContext.getDataElement();
  for (int j = 0; j < dataElementValues.length; j++) {
    Ade ade = ((DataElementValue) dataElement.getElementValue(j)).getAde();
    String dataElementValue = dataElementValues[j];

    // Create the TR with a unique id so we can uniquely identify this row 
 	  // for editing and deleting, and create the TD with the value of the data 
 	  // element.
%>
 	<tr>
 		<td>
 			<%=dataElementContext.getValueDescription(j)%>
 		</td>
<%
   	// Iterate over all ADE elements within the data element.
   	for (int i = 0; i < adeElements.length; i++) {
   		DetAdeElement adeElementMetadata = adeElements[i];
    	formContext.setDetAdeElement(adeElementMetadata);
    	if (ade != null) {
        formContext.setAdeElement(ade.getAdeElement(adeElementMetadata.getCui()));
    	}
    	stack.push(formContext);

    	formContext = stack.peek();
    	DataFormAdeElementContext adeElementContext = formContext.getDataFormAdeElementContext();    	
    	String adeElementValueDescription = adeElementContext.getValueDescription();
%>
    <td>
    	<kc:adeElementSummary valueIndex="<%=String.valueOf(j)%>">
			<%=ApiFunctions.isEmpty(adeElementValueDescription) ? "&nbsp;" :  Escaper.htmlEscapeAndPreserveWhitespace(adeElementValueDescription)%>
    	</kc:adeElementSummary>
		</td>
<%
			stack.pop();
    }	  // for each ADE element

    String editId = idgen.getUniqueId();
    String deleteId = idgen.getUniqueId();

    String jsFormObjId = formContext.getJavascriptObjectId();
    String jsDataElementObjId = dataElementContext.getJavascriptObjectId();
    scriptBuf.append("GSBIO.kc.data.FormInstances.getFormInstance(");
    if (jsFormObjId != null) {
      scriptBuf.append("'");
      scriptBuf.append(jsFormObjId);
      scriptBuf.append("'");
    }
    scriptBuf.append(").getDataElement('");
    scriptBuf.append(jsDataElementObjId);
    scriptBuf.append("').addAdeEditId('");
    scriptBuf.append(editId);
    scriptBuf.append("');");
    scriptBuf.append("GSBIO.kc.data.FormInstances.getFormInstance(");
    if (jsFormObjId != null) {
      scriptBuf.append("'");
      scriptBuf.append(jsFormObjId);
      scriptBuf.append("'");
    }
    scriptBuf.append(").getDataElement('");
    scriptBuf.append(jsDataElementObjId);
    scriptBuf.append("').addAdeDeleteId('");
    scriptBuf.append(deleteId);
    scriptBuf.append("');");
%>
		<td class="kcElementAdeButtons">
			<input id="<%=editId%>" type="button" value="Edit" class="kcButton"/>
			&nbsp;<input id="<%=deleteId%>" type="button" value="Delete" class="kcButton"/>
		</td>
 	</tr>
<%
  }  // for each data element value
}  // if ADE table should be displayed
%>
</table>
</div> <!-- kcElementAdeSummary -->
