<%
// Renders the edit controls for a single data element.
%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.util.SystemNameUtils" %>
<%@ page import="com.ardais.bigr.util.UniqueIdGenerator" %>
<%@ page import="com.gulfstreambio.kc.web.support.DataFormDataElementContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContextStack" %>
<%@ page import="com.gulfstreambio.kc.web.support.KcUiContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.WebUtils" %>
<%@ page import="javax.servlet.jsp.JspException" %>
<%@ taglib uri="/kctaglib" prefix="kc" %>

<%
FormContextStack stack = FormContextStack.getFormContextStack(request);
FormContext formContext = stack.peek();
DataFormDataElementContext dataElementContext = formContext.getDataFormDataElementContext();
String systemName = dataElementContext.getSystemName();

UniqueIdGenerator idgen = KcUiContext.getKcUiContext(request).getUniqueIdGenerator();
String noteButtonId = idgen.getUniqueId();
String adeLinkId = idgen.getUniqueId();
dataElementContext.setHtmlIdNoteButton(noteButtonId);
dataElementContext.setHtmlIdAdeLink(adeLinkId);

// Get some flags to guide the generation of inputs for the data element.
boolean isRequired = dataElementContext.isRequired();
boolean isMultivalued = dataElementContext.isMultivalued();
boolean isMlvs = dataElementContext.isMlvs();
boolean isHasAde = dataElementContext.isHasAde();
boolean isMultiAde = isMultivalued && isHasAde;
boolean isHasOtherValue = dataElementContext.isHasOtherValue();
boolean isCalculated = dataElementContext.isCalculated();

// Determine the input page for the main value of the data element and
// the input pages for other values of the data element.
String inputPage = null;
String jspPath = WebUtils.getContextRelativePath();
if (dataElementContext.isDatatypeInt()) {
	inputPage = jspPath + "/data/element/elementSingleInt.jsp";
}
else if (dataElementContext.isDatatypeFloat()) {
  inputPage = jspPath + "/data/element/elementSingleFloat.jsp";
}
else if (dataElementContext.isDatatypeReport()) {
  inputPage = jspPath + "/data/element/elementSingleReport.jsp";
}
else if (dataElementContext.isDatatypeText()) {
  inputPage = jspPath + "/data/element/elementSingleText.jsp";
}
else if (dataElementContext.isDatatypeDate()) {
  inputPage = jspPath + "/data/element/elementSingleDate.jsp";
}
else if (dataElementContext.isDatatypeVpd()) {
  inputPage = jspPath + "/data/element/elementSingleVpd.jsp";
}
else if (dataElementContext.isDatatypeCv()) {
  if (isMultivalued || isMlvs) {
    inputPage = jspPath + "/data/element/elementMultiCv.jsp";    
  }
  else {
    inputPage = jspPath + "/data/element/elementSingleCv.jsp";    
  }
}
if (inputPage == null) {
 	throw new JspException("Could not determine data element input page to include");
}

String inputPageNote = jspPath + "/data/element/elementNote.jsp";
String inputPageStandard = jspPath + "/data/element/elementStandardValues.jsp";
String inputPageOther = isMultivalued 
	? jspPath + "/data/element/elementMultiOther.jsp"
	: jspPath + "/data/element/elementSingleOther.jsp";
String inputPageAde = isMultivalued
	? jspPath + "/data/dataElement/dataElementMultiAde.jsp"
	: jspPath + "/data/dataElement/dataElementSingleAde.jsp";
%>

<kc:dataElementEdit>

<%
// Generate a TABLE with a single TR and two TDs.  The first TD contains the
// label and main input control(s).
//
// If the data element is a multivalued ADE, then make the label a link that 
// will open up a popup window to capture the data element value and the ADE 
// values.  In this case there are no input controls to generate directly
// on this page.
//
// Otherwise display the label with its units.  If the data element is 
// required, include an asterisk next to the label as an indicator.
// Generate the main input control(s) by including the appropriate JSP.
%>
<table cellpadding="0" cellspacing="0" class="kcElementInputs">
<tr><td class="kcElementLeft">

<label>
<% 
if (isRequired) {
%>
<span class="kcRequiredInd">*</span>
<%
}
if (isMultiAde) {
%>
<span id="<%=adeLinkId%>" class="kcFakeLink">
Add <%=dataElementContext.getLabel()%>
</span>
</label>
<%
}
else {
%>
<%=dataElementContext.getLabelWithUnits()%>:
<% 
if (isCalculated) {
%>
&nbsp;(calculated)&nbsp;</span>
<%
}
%>
</label>
<jsp:include page="<%=inputPage%>" flush="true"/>
<%
	if (isHasOtherValue) {
%>
<jsp:include page="<%=inputPageOther%>" flush="true"/>	  
<%
	}
}
%>
</td> <!--left-->


<%
// Generate te second TD for the note button and the inputs for the system 
// standard values.
%>
<td class="kcElementRight">
  <input id="<%=noteButtonId%>"
    type="button" class="kcButton"
    value="<%=ApiFunctions.isEmpty(dataElementContext.getValueNote()) ? "Add Note" : "Delete Note"%>"/>
 	<jsp:include page="<%=inputPageStandard%>" flush="true"/>
</td> <!--right-->
</tr></table>
<%
// Generate the input controls for the ADE, if any.
if (isHasAde) {
%>
<jsp:include page="<%=inputPageAde%>" flush="true"/>
<%
}
// Generate the input for the data element's note.
%>
<jsp:include page="<%=inputPageNote%>" flush="true"/>
</kc:dataElementEdit>