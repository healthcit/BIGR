<%@ page import="com.gulfstreambio.kc.det.DetAde" %>
<%@ page import="com.gulfstreambio.kc.det.DetAdeElement" %>
<%@ page import="com.gulfstreambio.kc.web.support.QueryFormAdeElementContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContextStack" %>
<%@ page import="com.gulfstreambio.kc.web.support.WebUtils" %>
<%@ taglib uri="/kctaglib" prefix="kc" %>
<%
FormContextStack stack = FormContextStack.getFormContextStack(request);
FormContext formContext = stack.peek();

String eventHandler = 
  WebUtils.getJavaScriptReferenceQueryElements() + ".commonEventHandler();";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>Add Ancillary Data Element Query Criteria</title>
<jsp:include page="/hiddenJsps/kc/query/misc/scriptAndCss.jsp" flush="true"/>
<script>
function bodyLoaded() {
  var i, adeElement, genericElement;

  // Merge our ARTS concepts into those of the calling window.  The first
  // dialogArgument is the calling window's ARTS object.
  window.dialogArguments[0].merge(GSBIO.ARTS.concepts);
  
  // The second dialog argument is is the JSON string that holds all of the 
  // ADE element's values, and it is used to initialize the input controls.
  // The JSON string is an array, where each array entry contains the values 
  // for a single ADE element.
  var genericElements = window.dialogArguments[1];

	// First, clear all ADE element's values in preparation for initializing.
  var adeElements = GSBIO.kc.query.Elements.elements;
  for (i = 0; i < adeElements.length; i++) {
    adeElements[i].clear();
  }
  
  // If the ADE element's values were passed, then use them to initialize the
  // input controls.  It is legit for the values to be not passed, which 
  // indicates that the ADE elements do not have values, or the user cleared
  // them.
  if (genericElements) {
    genericElements = genericElements.evalJSON();
    for (i = 0; i < genericElements.length; i++) {
      genericElement = genericElements[i];
      adeElement = GSBIO.kc.query.Elements.getElement(genericElement.elementId);
      if (adeElement) {
        adeElement.initialize(genericElement);
        adeElement.updateInputs();
        adeElement.clearExceptAnyAde();
      }
    }
  }
}
function returnAdeCriteria() {
  window.returnValue = GSBIO.kc.query.Elements.toRequestParameter();
  window.close();  
}
</script>
</head>
<body onload="bodyLoaded();">
<form>
  <div class="kcElements" 
   onclick="<%=eventHandler%>"
   onkeyup="<%=eventHandler%>">
<%
// Get the ADE metadata and iterate over all ADE elements.  Update the
// form context with the ADE metadata and ADE element instance if it exists,
// and push the new form context onto the form context stack for subsequent
// processing.
DetAde adeMetadata = formContext.getDetDataElement().getAde();
DetAdeElement[] adeElements = adeMetadata.getAdeElements();
for (int i = 0; i < adeElements.length; i++) {
	DetAdeElement detAdeElement = adeElements[i];
	formContext.setDetAdeElement(detAdeElement);
	stack.push(formContext);

	// Get the new form context and get the ADE element context to be used for
	// generating the query criteria inputs for the ADE elements.
	formContext = stack.peek();
	QueryFormAdeElementContext adeElementContext = formContext.getQueryFormAdeElementContext();

	// Determine the input page for collecting query criteria.
	String inputPage = null;
	String jspPath = WebUtils.getContextRelativePath();
	if (adeElementContext.isRenderAsDiscrete()) {
	  inputPage = jspPath + "/query/element/elementDiscrete.jsp";    
	}
	else if (adeElementContext.isRenderAsRange()) {
		inputPage = jspPath + "/query/element/elementRange.jsp";
	}

	// It is OK if we could not determine how to render the element, since
	// we may not want to render all elements since not all types of elements
	// may be valid for query criteria.
	if (inputPage == null) {
	 	continue;
	}

%>
<kc:queryElement>
<%
// Generate the label, has any value checkbox, and Clear button within a 
// single TR within a table.
%>
	<table border="0" cellspacing="0" cellpadding="0" width="100%">
    <col width="40%">
    <col width="20%">
    <col width="40%">
    <tbody>
      <tr>
      	<td><label><%=adeElementContext.getDisplayNameWithUnits()%></label></td>
				<td align="right">
          <input type="checkbox" 
                 name="<%=adeElementContext.getPropertyAny()%>"
                 <%=adeElementContext.isValueAny() ? "checked" : ""%>/>
                 has any value
        </td>
				<td align="right">
		        <input type="button" 
                   name="<%=adeElementContext.getPropertyButtonClear()%>" 
                   value="Clear"
                   class="kcButton"/>
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

</kc:queryElement>
<%
	stack.pop();
}
%>
</div> <!-- kcElements -->

<div class="kcPopupButtons">
  <input type="button" name="okButton" value="Ok" onclick="returnAdeCriteria();"/>
  <input type="button" name="cancelButton" value="Cancel" onclick="window.close();" />
</div>
</form>
<%
WebUtils.writeJavaScriptBuffer(pageContext);
%>
</body>
</html>