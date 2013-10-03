<%
// Renders the edit controls for all of the ADE elements of a singlevalued
// data element.
%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
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
<%@ page import="java.util.Iterator" %>
<%@ page import="javax.servlet.jsp.JspException" %>
<%@ taglib uri="/kctaglib" prefix="kc" %>

<% 
FormContextStack stack = FormContextStack.getFormContextStack(request);
FormContext formContext = stack.peek();
DataFormDataElementContext dataElementContext = formContext.getDataFormDataElementContext();

String jspPath = WebUtils.getContextRelativePath();

UniqueIdGenerator idgen = KcUiContext.getKcUiContext(request).getUniqueIdGenerator();
String adeContainerId = idgen.getUniqueId();
dataElementContext.setHtmlIdAdeContainer(adeContainerId);

// Determine whether we should hide or show the ADE elements.  If the data
// element has a value, and if that value is an ATV value if the datatype is
// CV, then show the ADE.
String value = dataElementContext.getValue();
boolean hideOrDisplay = false;
if (!ApiFunctions.isEmpty(value)) {
	if (dataElementContext.isValueAtvValue()) {      
	  hideOrDisplay = true;     
	}
}

// First generate the DIV and table that will hold all of the ADE elements.
%>
<div id="<%=adeContainerId%>" class="kcElementsAde" <%=hideOrDisplay ? "" : "style=\"display:none;\"" %>>

<% 
// Get the ADE instance if it exists, i.e. if the data element has a value.
Ade ade = null;
DataElement dataElement = formContext.getDataElement();
if ((dataElement != null) && dataElement.isElementValueExists(0)) {
  ade = ((DataElementValue) dataElement.getElementValue(0)).getAde();
}

// Get the ADE metadata and iterate over all ADE elements.  Update the
// form context with the ADE metadata and ADE element instance if it exists,
// and push the new form context onto the form context stack for subsequent
// processing.
DetAde adeMetadata = formContext.getDetDataElement().getAde();
DetAdeElement[] adeElements = adeMetadata.getAdeElements();
for (int i = 0; i < adeElements.length; i++) {
	DetAdeElement adeElementMetadata = adeElements[i];
  formContext.setDetAdeElement(adeElementMetadata);
  if (ade != null) {
    formContext.setAdeElement(ade.getAdeElement(adeElementMetadata.getCui()));
  }
  stack.push(formContext);


	// Get the new form context and get the ADE element context to be used for
	// generating the edit inputs for the ADE elements.
	formContext = stack.peek();
	DataFormAdeElementContext adeElementContext = formContext.getDataFormAdeElementContext();

	// Determine the input page for the value of the ADE element and
	// the input pages for other values of the ADE element.
	String inputPage = null;
	if (adeElementContext.isDatatypeInt()) {
		inputPage = jspPath + "/data/element/elementSingleInt.jsp";
	}
	else if (adeElementContext.isDatatypeFloat()) {
		inputPage = jspPath + "/data/element/elementSingleFloat.jsp";
	}
	else if (adeElementContext.isDatatypeReport()) {
		inputPage = jspPath + "/data/element/elementSingleReport.jsp";
	}
	else if (adeElementContext.isDatatypeText()) {
	  inputPage = jspPath + "/data/element/elementSingleText.jsp";
	}
	else if (adeElementContext.isDatatypeDate()) {
		inputPage = jspPath + "/data/element/elementSingleDate.jsp";
	}
	else if (adeElementContext.isDatatypeVpd()) {
		inputPage = jspPath + "/data/element/elementSingleVpd.jsp";
	}
	else if (adeElementContext.isDatatypeCv()) {
		if (adeElementContext.isMultivalued() || adeElementContext.isMlvs()) {
			inputPage = jspPath + "/data/element/elementMultiCv.jsp";    
		}
		else {
			inputPage = jspPath + "/data/element/elementSingleCv.jsp";    
		}
	}
	if (inputPage == null) {
		throw new JspException("Could not determine ADE element input page to include");
	}
	
	String inputPageOther = adeElementContext.isMultivalued()
		? jspPath + "/data/element/elementMultiOther.jsp"
		: jspPath + "/data/element/elementSingleOther.jsp";

%>
	<kc:adeElementEdit>

<%
// Generate the label for the ADE element.
%>
<label>
<%=adeElementContext.getLabelWithUnits()%>:
</label>
<%
// Generate the input for the ADE, including the input for the other value 
// if necessary.
%>
<jsp:include page="<%=inputPage%>" flush="true"/>
<%
	if (adeElementContext.isHasOtherValue()) {
%>
<jsp:include page="<%=inputPageOther%>" flush="true"/>
<%
	}
%>
	</kc:adeElementEdit>
<%
	stack.pop();
}
%>
</div> <!-- kcElementsAde -->
