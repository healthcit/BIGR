<%@ page import="com.ardais.bigr.util.UniqueIdGenerator" %>
<%@ page import="com.gulfstreambio.kc.det.DetAdeElement" %>
<%@ page import="com.gulfstreambio.kc.det.DetAde" %>
<%@ page import="com.gulfstreambio.kc.web.support.DataFormAdeElementContext" %>
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
String jsFormId = formContext.getJavascriptObjectId();

DataFormDataElementContext dataElementContext = formContext.getDataFormDataElementContext();

UniqueIdGenerator idgen = KcUiContext.getKcUiContext(request).getUniqueIdGenerator();
String adeContainerId = idgen.getUniqueId();
dataElementContext.setHtmlIdAdeContainer(adeContainerId);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>Edit Ancillary Data Element</title>
<jsp:include page="/hiddenJsps/kc/data/misc/scriptAndCss.jsp" flush="true"/>
<style type="text/css">
.buttons {
  font-family: tahoma,verdana,arial,helvetica,sans-serif;
  font-size: 9pt;
  border: 1px solid #000000;
}
</style>
<script type="text/javascript">
GSBIO.kc.data.FormInstances.createFormInstance({'id' : <%=jsFormId == null ? "null" : "'" + jsFormId + "'"%>});
</script>
</head>
<body onload="initPage();">
<form>

<%
// First generate the inputs for the data element.
boolean isMultivalued = dataElementContext.isMultivalued();
boolean isMlvs = dataElementContext.isMlvs();
boolean isHasOtherValue = dataElementContext.isHasOtherValue();
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
  if (isMlvs) {
    inputPage = jspPath + "/data/element/elementMultiCv.jsp";    
  }
  else {
    inputPage = jspPath + "/data/element/elementSingleCv.jsp";    
  }
}
if (inputPage == null) {
 	throw new JspException("Could not determine data element input page to include");
}

String inputPageOther = jspPath + "/data/element/elementSingleOther.jsp";
%>
<div class="kcElements">
  <kc:dataElementEdit>
  <label>
    <%=dataElementContext.getLabelWithUnits()%>:
  </label>
	<jsp:include page="<%=inputPage%>" flush="true"/>
<%
	if (isHasOtherValue) {
%>
  <jsp:include page="<%=inputPageOther%>" flush="true"/>
<%
	}
%>
<%
// Next generate the inputs for each ADE element.
%>
<div id="<%=adeContainerId%>" class="kcElementsAdePopup" style="display:none;border: 1px solid #FFFFFF;">

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
	// generating the edit inputs for the ADE elements.
	formContext = stack.peek();
	DataFormAdeElementContext adeElementContext = formContext.getDataFormAdeElementContext();

	isMultivalued = adeElementContext.isMultivalued();
	isMlvs = adeElementContext.isMlvs();
	isHasOtherValue = adeElementContext.isHasOtherValue();

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
	
	inputPageOther = isMultivalued
		? jspPath + "/data/element/elementMultiOther.jsp"
		: jspPath + "/data/element/elementSingleOther.jsp";

%>
<kc:adeElementEdit>
  <label>
   <%=adeElementContext.getLabelWithUnits()%>:
  </label>
	<jsp:include page="<%=inputPage%>" flush="true"/>
<%
if (isHasOtherValue) {
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
</div>
</kc:dataElementEdit>
</div> <!--  kcElements -->

<div class="kcPopupButtons">
  <input type="button" value="Ok" onclick="returnDataElement();"/>
  <input type="button" value="Cancel" onclick="window.close();" />
</div>
</form>

<script type="text/javascript">
function initPage() {
  // Merge our ARTS concepts into those of the calling window.  The first
  // dialogArgument is the calling window's ARTS object.
  window.dialogArguments[0].merge(GSBIO.ARTS.concepts);

  // The optional second dialog argument is the JSON string representation of the
  // GSBIO.kc.data.DataElementValue that holds the data element's value and all of the ADE 
  // element's values, and it is used to initialize the input controls.  It will be supplied when 
  // editing an existing value, and will be absent when creating a new value.
  var actualValues = window.dialogArguments[1];
  if (actualValues) {
    actualValues = actualValues.evalJSON();
    
    // First update our data element's value with that of the dialog argument and then
    // update the data element's input controls to display the value.
    var de = GSBIO.kc.data.FormInstances.getFormInstance(<%=jsFormId == null ? "null" : "'" + jsFormId + "'"%>).getDataElement('<%=dataElementContext.getJavascriptObjectId()%>');
    var deValue = de.values[0];
    deValue.value = actualValues.value;
    deValue.valueOther = actualValues.valueOther;
    de.updateInputs();
    
    // If there is a data element value, but the control was not updated, and the data element is
    // CV, it could be that the value is in the broad value set but only a narrow or standard
    // value set is being shown.  Therefore show the broad value set and try updating the input
    // controls again.
    if (deValue.value && !de.getPrimaryValue() && de.getMeta().isDatatypeCv) {
      de.showBroadValueSet();
      de.updateInputs();
    }

    // Next update each ADE element's value with that of the dialog argument and update the
    // ADE element's input controls to display the value.
    var adeElements = actualValues.ade.elements;
    for (i = 0; i < adeElements.length; i++) {
      var ae = deValue.ade.getAdeElement(adeElements[i].systemName);
      ae.values = null;
      if (adeElements[i].values) {
        for (j = 0; j < adeElements[i].values.length; j++) {
          ae.addValue(GSBIO.kc.data.AdeElementValue(adeElements[i].values[j]));
        }
      }
      ae.updateInputs();
    }
  }

  GSBIO.kc.data.FormInstances.getFormInstance(<%=jsFormId == null ? "null" : "'" + jsFormId + "'"%>).alternateRowColor();
}

function returnDataElement() {
  var de = GSBIO.kc.data.FormInstances.getFormInstance(<%=jsFormId == null ? "null" : "'" + jsFormId + "'"%>).getDataElement('<%=dataElementContext.getJavascriptObjectId()%>');
  if (de.getPrimaryValue()) {
    window.returnValue = de.serialize();
    window.close();
  }
  else {
    alert("Please select a value.");
  }
}
</script>
</body>
</html>