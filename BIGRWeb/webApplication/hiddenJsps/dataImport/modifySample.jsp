<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.api.Escaper" %>
<%@ page import="com.ardais.bigr.configuration.LabelPrinter" %>
<%@ page import="com.ardais.bigr.configuration.LabelTemplateDefinition" %>
<%@ page import="com.ardais.bigr.iltds.assistants.LegalValueSet" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<%
com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<bean:define name="sampleForm" id="myForm" type="com.ardais.bigr.dataImport.web.form.SampleForm"/>
<html>
<head>
<title>Modify Sample</title>
<script language="JavaScript" src="<html:rewrite page="/js/bigrTextArea.js"/>"></script>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<jsp:include page="/hiddenJsps/kc/data/misc/scriptAndCss.jsp" flush="true"/>
<link rel="stylesheet" href="<html:rewrite page="/css/bigr.css"/>" type="text/css">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript" src="<html:rewrite page="/js/bigrTextArea.js"/>"></script>
<script language="JavaScript" src="<html:rewrite page="/js/calendar.js"/>"></script>
<script language="JavaScript" src="<html:rewrite page="/js/registration.js"/>"></script>
<script type="text/javascript"
	src='<html:rewrite page="/js/overlib.js"/>'><!-- overLIB (c) Erik Bosrup --></script>
<script type="text/javascript">
<%
Map labelPrintingData = myForm.getLabelPrintingData();
boolean allowLabelPrinting = !ApiFunctions.isEmpty(labelPrintingData);
LegalValueSet labelTemplateDefinitions = new LegalValueSet();
LegalValueSet printers = new LegalValueSet();
Iterator sampleTypeIterator = labelPrintingData.keySet().iterator();
while (sampleTypeIterator.hasNext()) {
  String sampleType = (String)sampleTypeIterator.next();
  if (sampleType.equalsIgnoreCase(myForm.getSampleData().getSampleTypeCui())) {
  	Iterator labelTemplateDefinitionIterator = ((Map)labelPrintingData.get(sampleType)).keySet().iterator();
  	while (labelTemplateDefinitionIterator.hasNext()) {
  	  LabelTemplateDefinition templateDefinition = (LabelTemplateDefinition)labelTemplateDefinitionIterator.next();
  	  //build a lvs of available templates for the specified sample type
  	  labelTemplateDefinitions.addLegalValue(templateDefinition.getName(), templateDefinition.getDisplayName());
  	  //if a template name is selected, build a lvs of supported printers for the specified template
  	  if (templateDefinition.getName().equalsIgnoreCase(myForm.getTemplateDefinitionName())) {
  	    Collection supportedPrinters = (Collection)((Map)labelPrintingData.get(sampleType)).get(templateDefinition);
    	  Iterator printerIterator = supportedPrinters.iterator();
    	  while (printerIterator.hasNext()) {
    	    LabelPrinter printer = (LabelPrinter)printerIterator.next();
    	    printers.addLegalValue(printer.getName(), printer.getDisplayName());
    	  }
  	  }
  	}
  }
}

%>
var myBanner = 'Modify Sample';

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
  if (GSBIO.kc.data.FormInstances.getFormInstance()) {    
    GSBIO.kc.data.FormInstances.getFormInstance().alternateRowColor();
  }
<%
if (!myForm.isReadOnly()) {
  Map regFormIds = myForm.getSampleRegistrationFormIds();
  Iterator sampleTypeCuis = regFormIds.keySet().iterator();
  while (sampleTypeCuis.hasNext()) {
    String cui = (String) sampleTypeCuis.next();
%>
    GSBIO.bigr.reg.RegFormIds['<%=cui%>'] = '<%=(String)regFormIds.get(cui)%>';
<%
  }
%>
  GSBIO.bigr.reg.CurrentSampleTypeCui = $F('sampleData.sampleTypeCui');
  $('sampleData.sampleAlias').focus();
  $('originalSampleType').value = $F('sampleData.sampleTypeCui');
<%
}
%>
<%
if (allowLabelPrinting) {
%>
//	if no value has been selected in either of the print label drop downs, auto select any single choices
	if (document.forms[0].printerName.value == "" 
	  && document.forms[0].templateDefinitionName.value == "") {
	  //if there is only one choice (other than "Select") in the label template drop-down, select it
	  selectSingleDropDownChoice(document.forms[0].printerName, 2);
	  //if there is only one choice (other than "Select") in the label printer drop-down, select it
	  selectSingleDropDownChoice(document.forms[0].templateDefinitionName, 2)
	}
	enablePrintLabelControls();
<%
}
%>
}

function onFormSubmit() {
  GSBIO.bigr.reg.setActionButtonEnabling(false);
  if (GSBIO.bigr.reg.verifySampleTypeChange()) {
    if (GSBIO.kc.data.FormInstances.getFormInstance()) {
      $('form').value = GSBIO.kc.data.FormInstances.getFormInstance().serialize();
    }
    return true;
  }
  else {
    GSBIO.bigr.reg.setActionButtonEnabling(true);
    return false;
  }
}

function updatePrintLabelCheckBox() {
  $('printSampleLabel').value = document.all.printSampleLabelCB.checked;
}

function updateLabelTemplateChoices() {
<%
	if (allowLabelPrinting) {
%>
  //clear the existing label template and printer choices
  for(var i=document.forms[0].templateDefinitionName.length; i>0; i--)
  {
    document.forms[0].templateDefinitionName.remove(i);    
  }
  for(var i=document.forms[0].printerName.length; i>0; i--)
  {
    document.forms[0].printerName.remove(i);    
  }
  
  //populate new choices based on the selected sample type
  var selectedSampleType = $('sampleData.sampleTypeCui').value;
<%
  	sampleTypeIterator = labelPrintingData.keySet().iterator();
  	while (sampleTypeIterator.hasNext()) {
  	  String sampleType = (String)sampleTypeIterator.next();
  	  Map labelTemplateDefinitionMap = (Map)labelPrintingData.get(sampleType);
  		Iterator labelTemplateDefinitionIterator = labelTemplateDefinitionMap.keySet().iterator();
  		int index = 1;
%>
  if (selectedSampleType == '<%=sampleType%>') {
<%
  	  while (labelTemplateDefinitionIterator.hasNext()) {
  		  LabelTemplateDefinition templateDefinition = (LabelTemplateDefinition)labelTemplateDefinitionIterator.next();
%>
      document.forms[0].templateDefinitionName.options[<%=index%>]=new Option('<%=Escaper.jsEscapeInScriptTag(templateDefinition.getDisplayName())%>', '<%=Escaper.jsEscapeInScriptTag(templateDefinition.getName())%>');
<%
    		index++;
  	  }
%>
  }
<%
  	}
%>
  //if there is only one choice (other than "Select") in the drop-down, select it
  selectSingleDropDownChoice(document.forms[0].templateDefinitionName, 2);
  updatePrinterChoices();
<%
	}
%>
}

function updatePrinterChoices() {
<%
	if (allowLabelPrinting) {
%>
  //clear the existing printer choices
  for(var i=document.forms[0].printerName.length; i>0; i--)
  {
    document.forms[0].printerName.remove(i);    
  }
  
  //populate new choices based on the selected template
  var selectedTemplate = $('templateDefinitionName').value;
<%
  Map processedTemplateDefinitions = new HashMap();
	sampleTypeIterator = labelPrintingData.keySet().iterator();
	while (sampleTypeIterator.hasNext()) {
  	String sampleType = (String)sampleTypeIterator.next();
  	Iterator labelTemplateDefinitionIterator = ((Map)labelPrintingData.get(sampleType)).keySet().iterator();
  	while (labelTemplateDefinitionIterator.hasNext()) {
  	  LabelTemplateDefinition templateDefinition = (LabelTemplateDefinition)labelTemplateDefinitionIterator.next();
  	  String templateDefinitionName = templateDefinition.getName();
  	  //if we haven't already processed this template definition (via another sample subtype),
  	  //process it
  	  if (processedTemplateDefinitions.get(templateDefinitionName) == null) {
%>
  if (selectedTemplate == '<%=Escaper.jsEscapeInScriptTag(templateDefinition.getName())%>') {
<%
				Collection supportedPrinters = (Collection)((Map)labelPrintingData.get(sampleType)).get(templateDefinition);
				Iterator printerIterator = supportedPrinters.iterator();
	  		int index = 1;
				while (printerIterator.hasNext()) {
				  LabelPrinter printer = (LabelPrinter)printerIterator.next();
%>
    document.forms[0].printerName.options[<%=index%>]=new Option('<%=Escaper.jsEscapeInScriptTag(printer.getDisplayName())%>', '<%=Escaper.jsEscapeInScriptTag(printer.getName())%>');
<%
					index++;
	   		}
  	  	processedTemplateDefinitions.put(templateDefinitionName,templateDefinitionName);
%>
  }
<%
  	  }
  	}
	}
%>
	//if there is only one choice (other than "Select") in the drop-down, select it
	selectSingleDropDownChoice(document.forms[0].printerName, 2);
<%
	}
%>
}

function enablePrintLabelControls() {
<%
	if (allowLabelPrinting) {
%>
  $('templateDefinitionName').disabled = !document.all.printSampleLabelCB.checked;
  $('printerName').disabled = !document.all.printSampleLabelCB.checked;
  $('labelCount').disabled = !document.all.printSampleLabelCB.checked;
<%
	}
%>
}
</script>
</head>
<body class="bigr" onload="initPage();">
<%--
	** DIV for overlib, which is used for tooltips.
	--%>
<bigr:overlibDiv/>
<html:form method="POST" action="/dataImport/modifySample" onsubmit="return(onFormSubmit());">
  <html:hidden name="sampleForm" property="sampleData.ardaisId"/>
  <html:hidden name="sampleForm" property="sampleData.consentId"/>
  <html:hidden name="sampleForm" property="sampleData.sampleId"/>
  <html:hidden name="sampleForm" property="sampleData.consumed"/>
  <html:hidden name="sampleForm" property="sampleData.registrationFormId"/>
	<input type="hidden" name="originalSampleType"/>
  <input type="hidden" name="form" value=""/>

<div id="errorsDiv" class="errorsMessages">
<logic:present name="org.apache.struts.action.ERROR">
<html:errors/>
</logic:present>
</div>
<div id="messagesDiv" class="errorsMessages">
<logic:present name="com.ardais.BTX_ACTION_MESSAGES">
<bigr:btxActionMessages/>
</logic:present>
</div>

<div class="kcElements bigrElements">

<div class="kcElement bigrElement">
<label class="inlineLabel">Donor:</label>
<bean:write name="sampleForm" property="sampleData.ardaisId"/>
<logic:notEmpty name="sampleForm" property="sampleData.donorAlias">
(<bean:write name="sampleForm" property="sampleData.donorAlias" />)
</logic:notEmpty>
</div>

<div class="kcElement bigrElement">
<label class="inlineLabel">Case:</label>
<bean:write name="sampleForm" property="sampleData.consentId"/>
<logic:notEmpty name="sampleForm" property="sampleData.consentAlias">
(<bean:write name="sampleForm" property="sampleData.consentAlias" />)
</logic:notEmpty>
</div>

<div class="kcElement bigrElement">
<label class="inlineLabel">Sample Id:</label>
<bean:write name="sampleForm" property="sampleData.sampleId"/>
</div>

<div class="kcElement bigrElement">
<label class="inlineLabel">
<%
  if (myForm.isSampleAliasRequired() && !myForm.isReadOnly()) {
%>
<span class="kcRequiredInd">*</span>
<%
  }
%>
 Sample Alias:</label>
<% if (myForm.isReadOnly()) { %>
<bean:write name="sampleForm" property="sampleData.sampleAlias"/>
<% } else { %>
<html:text name="sampleForm" property="sampleData.sampleAlias" size="30" maxlength="30"/>
<% } %>
</div>

<div class="kcElement bigrElement">
<label class="inlineLabel">
<% if (!myForm.isReadOnly()) { %>
<span class="kcRequiredInd">*</span> 
<% } %>
Sample Type:</label>
<% if (myForm.isReadOnly()) { %>
<bean:write name="sampleForm" property="sampleData.sampleType"/>
<% } else { %>
<bigr:selectList name="sampleForm" property="sampleData.sampleTypeCui"
  firstValue="" firstDisplayValue="Select Sample Type"
  legalValueSetProperty="sampleTypeChoices" onchange="GSBIO.bigr.reg.getSampleRegistrationForm();updateLabelTemplateChoices();"/>
<% } %>
</div>

<%
	//if label printing is enabled for the account and we are not in read-only mode, 
	//provide a checkbox allowing the user to print a label (or labels) for the sample
	if (allowLabelPrinting && !myForm.isReadOnly()) {
%>
<div class="kcElement bigrElement">
<label class="inlineLabel">Label Printing:</label>
  <input type="checkbox" name="printSampleLabelCB" onclick="updatePrintLabelCheckBox();enablePrintLabelControls();" <%=myForm.isPrintSampleLabel() ? "checked" : ""%>/>&nbsp;Print Label
  <html:hidden property="printSampleLabel"/>
  <br>
	<label class="inlineLabel">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
	Number of labels:&nbsp;&nbsp;<html:text name="sampleForm" property="labelCount" size="5" maxlength="2"/>
  <br>
	<label class="inlineLabel">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
	Label template:&nbsp;&nbsp;&nbsp;&nbsp;
				<bigr:selectList name="sampleForm" property="templateDefinitionName"
  				firstValue="" firstDisplayValue="Select"
  				legalValueSet="<%=labelTemplateDefinitions%>"
  				onchange="updatePrinterChoices();"/>
  <br>
	<label class="inlineLabel">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
	Label printer:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<bigr:selectList name="sampleForm" property="printerName"
  				firstValue="" firstDisplayValue="Select"
  				legalValueSet="<%=printers%>"/>
<%
	}
%>
</div>

</div> <!-- kcElements -->

<div id="sampleTypeSpecific">
<%
// Display the registration form in either edit or summary mode.
if (myForm.isReadOnly()) {
%>
<jsp:include page="/hiddenJsps/dataImport/displaySampleRegFormSummary.jsp" flush="true"/>
<%
} 
else {
  request.setAttribute("ignoreErrors", "true");
%>
<jsp:include page="/hiddenJsps/dataImport/displaySampleRegForm.jsp" flush="true"/>
<%
}
%>
</div> <!-- sampleTypeSpecific -->

<div class="kcElements bigrElements">
<div class="kcElement bigrElement">
<%
boolean consumed = myForm.getSampleData().isConsumed();
if (myForm.isReadOnly()) {
  if (consumed) {
%>
This sample is consumed
<%
  }
}
else {
%>
<input type="checkbox" name="consumedCheckbox" 
	<%=consumed?"checked disabled":""%>
  onclick="GSBIO.bigr.reg.adjustVolumeAndWeight();">
  This sample is consumed
<%
}
%>
</div>
</div>

<div class="sampleRegButtons">
<p>
<% if (!myForm.isReadOnly()) { %>
<input type="submit" id="btnSubmit" name="btnSubmit" value="Update"/>
<% } %>
<%
String cancelButtonName = "Cancel";
String cancelUrl = "/dataImport/getSampleFormSummary.do?sampleData.sampleId=" + myForm.getSampleData().getSampleId();
if (myForm.isReadOnly()) {
  cancelUrl = cancelUrl + "&readOnly=true";
  cancelButtonName = "Back";
}
%>
<input type="button" id="btnCancel" name="btnCancel" value="<%=cancelButtonName%>" 
onclick="window.location.href='<html:rewrite page="<%=cancelUrl%>"/>';"> 
</p>
</div>

<% if (!myForm.isReadOnly()) { %>
<p style="font-size: 8pt;"><span class="kcRequiredInd">*</span> indicates a required field </p>
<% } %>

</html:form>
</body>
</html>
