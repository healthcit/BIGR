<%
// Renders the query criteria controls for a single data element.
%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.gulfstreambio.kc.det.DetAde" %>
<%@ page import="com.gulfstreambio.kc.det.DetAdeElement" %>
<%@ page import="com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValue" %>
<%@ page import="com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueComparison" %>
<%@ page import="com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueTypes" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContextStack" %>
<%@ page import="com.gulfstreambio.kc.web.support.QueryFormAdeElementContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.QueryFormElementContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.WebUtils" %>
<%@ page import="javax.servlet.jsp.JspException" %>
<%@ taglib uri="/kctaglib" prefix="kc" %>

<% 
FormContextStack stack = FormContextStack.getFormContextStack(request);
FormContext formContext = stack.peek();
QueryFormElementContext queryElementContext = formContext.getQueryFormDataElementContext();

// Determine the input page for collecting query criteria.
String inputPage = null;
String jspPath = WebUtils.getContextRelativePath();
if (queryElementContext.isRenderAsDiscrete()) {
  inputPage = jspPath + "/query/element/elementDiscrete.jsp";    
}
else if (queryElementContext.isRenderAsRange()) {
	inputPage = jspPath + "/query/element/elementRange.jsp";
}
if (inputPage == null) {
 	throw new JspException("Could not determine query element input page to include");
}
%>
<kc:queryElement>
<%
// Generate the label, has any value checkbox, and Add ADE Criteria and Clear
// buttons within a single TR within a table.
%>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <col width="40%">
  <col width="20%">
  <col width="40%">
	<tbody>
    <tr>
      <td><label><%=queryElementContext.getDisplayNameWithUnits()%></label></td>
			<td align="right">
        <input type="checkbox" 
               name="<%=queryElementContext.getPropertyAny()%>"
               <%=queryElementContext.isValueAny() ? "checked" : ""%>/>
               has any value
      </td>
		  <td align="right">
<%
if (queryElementContext.isHasAde()) {
%>
        <input type="button" 
               name="<%=queryElementContext.getPropertyButtonAddAde()%>" 
               value="ADE Criteria..."
               class="kcButton"/>&nbsp;
<%
}
%>
        <input type="button" 
               name="<%=queryElementContext.getPropertyButtonClear()%>" 
               value="Clear"
               class="kcButton"
               onmouseout="return nd()" onmouseover="return overlib('Clears all values or ADEs in this section')"/>
      </td>
    </tr>
  </tbody>
</table>
<%
// Generate the main input controls to capture the query criteria.  Include the
// appropriate JSP based on the datatype of the data element.
%>
<div>
  <jsp:include page="<%=inputPage%>" flush="true"/>
</div>
<%
// Generate the ADE criteria summary.
if (queryElementContext.isHasAde()) {
%>
<div id="<%=queryElementContext.getPropertyAdeSummary()%>" >
<%
  DetAde adeMetadata = formContext.getDetDataElement().getAde();
  DetAdeElement[] adeElements = adeMetadata.getAdeElements();
  boolean header = false;
  for (int i = 0; i < adeElements.length; i++) {
	  DetAdeElement detAdeElement = adeElements[i];
  	formContext.setDetAdeElement(detAdeElement);
	  stack.push(formContext);

    // Get the new form context and get the ADE element context to be used for
    // generating the query criteria summary for the ADE elements.  Only
    // generate the summary for an ADE element if it is present in the query
    // form and it has value(s).
    formContext = stack.peek();
    QueryFormAdeElementContext adeElementContext = formContext.getQueryFormAdeElementContext();
    String summary = adeElementContext.getSummary();
    if (!ApiFunctions.isEmpty(summary)) {
      if (!header) {
        header = true;
%>
        ADE Criteria:
<%      
      }
%>
      <br>&nbsp;&nbsp;<%=summary%>
<%      
    } // summary is not empty (i.e. ADE element has values)
	  stack.pop();
  } // all ADE elements
%>
</div>
<%
}
%>
</kc:queryElement>
