<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.api.Escaper" %>
<%@ page import="com.ardais.bigr.api.ValidateIds" %>
<%@ page import="com.ardais.bigr.configuration.LabelPrinter" %>
<%@ page import="com.ardais.bigr.configuration.LabelTemplateDefinition" %>
<%@ page import="com.ardais.bigr.iltds.assistants.LegalValueSet" %>
<%@ page import="com.ardais.bigr.iltds.helpers.TypeFinder" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<bean:define name="sampleForm" id="myForm" type="com.ardais.bigr.dataImport.web.form.SampleForm"/>
<html>
<head>
<title>Create Sample</title>
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
var myBanner = 'Create Sample';

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
  if (GSBIO.kc.data.FormInstances.getFormInstance()) {    
    GSBIO.kc.data.FormInstances.getFormInstance().alternateRowColor();
  }
<%
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
	var c = $('generateSampleIdCheckbox');
	if (c && c.checked) {
		$('sampleData.sampleAlias').focus();
	}
	else {
		$('sampleData.sampleId').focus();
	}
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
  if (GSBIO.kc.data.FormInstances.getFormInstance()) {
    GSBIO.kc.data.FormInstances.getFormInstance().domainObjectId = $('sampleData.sampleId').value;
    $('form').value = GSBIO.kc.data.FormInstances.getFormInstance().serialize();
  }
  return true;
}

function onSave() {
  $('createForm').value = "false";
}

function onSaveAndCreateForm() {
  $('createForm').value = "true";
  if (onFormSubmit()) {
    $('theForm').submit();
  }
}
   
function GSBIO.bigr.reg.adjustConsumed() {
  // This function is needed since the HTML that is generated is common to
  // both create and modify sample, and modify sample calls a function with
  // this name when the volume or weight are adjusted.  We don't need to do
  // anything here in create sample though, since consumed is not a valid
  // field in create sample.  Therefoer this function declaration effectively 
  // overrides the function declaration in registration.js to do nothing.
}

function updatePrintLabelCheckBox() {
  $('printSampleLabel').value = document.all.printSampleLabelCB.checked;
}

function updateGenerateSampleId() {
  var c = $('generateSampleIdCheckbox');
  if (c) {
    if (c.checked) {
      $('generateSampleId').value = 'true';
      $('sampleData.sampleId').clear();
      $('printSampleLabelCB').checked = true;
      updatePrintLabelCheckBox();
      enablePrintLabelControls();
    }
    else {
      $('generateSampleId').value = 'false';
    }
  }
}

function clearGenerateCheckbox() {
  if ($('sampleData.sampleId').value) {
    var c = $('generateSampleIdCheckbox');
    if (c) {
      c.checked = false;    
      $('generateSampleId').value = 'false';
    }
  }
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
<html:form styleId="theForm" method="POST" action="/dataImport/createSample" onsubmit="return(onFormSubmit());">
  <html:hidden name="sampleForm" property="sampleData.ardaisAcctKey"/>
  <html:hidden name="sampleForm" property="sampleData.importedYN"/>
  <html:hidden name="sampleForm" property="rememberedData"/>
  <html:hidden name="sampleForm" property="newDonor"/>
  <html:hidden name="sampleForm" property="newCase"/>
  <html:hidden name="sampleForm" property="linkedIndicator"/>
  <html:hidden name="sampleForm" property="createForm"/>
  <html:hidden name="sampleForm" property="sampleData.ardaisId"/>
  <html:hidden name="sampleForm" property="sampleData.donorAlias"/>
  <html:hidden name="sampleForm" property="sampleData.consentId"/>
  <html:hidden name="sampleForm" property="sampleData.consentAlias"/>
  <html:hidden name="sampleForm" property="policyId"/>
  <html:hidden name="sampleForm" property="consentVersionId"/>
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
<logic:notPresent name="sampleForm" property="newDonor">
  <bean:write name="sampleForm" property="sampleData.ardaisId"/>
  <logic:present name="sampleForm" property="sampleData.donorAlias">
    <bean:write name="sampleForm" property="sampleData.donorAlias"/>
  </logic:present>
</logic:notPresent>
<logic:present name="sampleForm" property="newDonor">
  <bean:write name="sampleForm" property="sampleData.donorAlias"/>
</logic:present>
</div>

<div class="kcElement bigrElement">
<label class="inlineLabel">Case:</label>
<logic:notPresent name="sampleForm" property="newCase">
  <bean:write name="sampleForm" property="sampleData.consentId"/>
  <logic:present name="sampleForm" property="sampleData.consentAlias">
    <bean:write name="sampleForm" property="sampleData.consentAlias"/>
  </logic:present>
</logic:notPresent>
<logic:present name="sampleForm" property="newCase">
  <bean:write name="sampleForm" property="sampleData.consentAlias"/>
</logic:present>
</div>

<logic:equal name="sampleForm" property="linkedIndicator" value="N">
<div class="kcElement bigrElement">
<label class="inlineLabel">Case Policy:</label>
<bean:write name="sampleForm" property="policyName"/>
</div>
</logic:equal>

<logic:equal name="sampleForm" property="linkedIndicator" value="Y">
<div class="kcElement bigrElement">
<label class="inlineLabel">IRB Protocol / Consent Version:</label>
<bean:write name="sampleForm" property="irbProtocolName"/> /
<bean:write name="sampleForm" property="consentVersionName"/>
</div>
</logic:equal>

<%
	//customize the sample id label depending upon if sample labels can be printed
	String sampleIdLabel = null;
	if (allowLabelPrinting) {
	  sampleIdLabel = " Sample Id:";
	}
	else {
	  sampleIdLabel = " Sample Id (from label):";
	}
%>

<div class="kcElement bigrElement">
<label class="inlineLabel"><span class="kcRequiredInd">*</span><%=sampleIdLabel%></label>
<html:text name="sampleForm" property="sampleData.sampleId"
  size="<%=String.valueOf(ValidateIds.LENGTH_SAMPLE_ID + 5)%>"
  maxlength="<%=String.valueOf(ValidateIds.LENGTH_SAMPLE_ID)%>"
  onkeydown="clearGenerateCheckbox();" onkeyup="clearGenerateCheckbox();"/>
<%
	//if label printing is enabled for the account, provide a checkbox allowing the user to 
	//auto-generate the next sample id
	if (allowLabelPrinting) {
%>
  &nbsp;&nbsp;&nbsp;&nbsp;
  <input type="checkbox" name="generateSampleIdCheckbox" onclick="updateGenerateSampleId();" <%=myForm.isGenerateSampleId() ? "checked" : ""%>/>
  &nbsp;Generate Sample Id
  <html:hidden property="generateSampleId"/>
  <html:hidden property="generatedSampleId"/>
<%
	}
%>
</div>

<div class="kcElement bigrElement">
<label class="inlineLabel">
<%
  if (myForm.isSampleAliasRequired()) {
%>
<span class="kcRequiredInd">*</span>
<%
  }
%>
 Sample Alias:</label>
<html:text name="sampleForm" property="sampleData.sampleAlias"
 size="30" maxlength="30"/>
</div>

<div class="kcElement bigrElement">
<label class="inlineLabel"><span class="kcRequiredInd">*</span> Sample Type:</label>
<bigr:selectList name="sampleForm" property="sampleData.sampleTypeCui"
  firstValue="" firstDisplayValue="Select Sample Type"
  legalValueSetProperty="sampleTypeChoices" onchange="GSBIO.bigr.reg.getSampleRegistrationForm();updateLabelTemplateChoices();"/>
</div>

<%
	//if label printing is enabled for the account, provide a checkbox allowing the user to 
	//print a label (or labels) for the sample
	if (allowLabelPrinting) {
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
// If a registration form has been chosen, then display it.  One situation in
// which this occurs is if we come back to this page after an error.
if (myForm.getSampleData().getRegistrationForm() != null) {
  request.setAttribute("ignoreErrors", "true");
%>
<jsp:include page="/hiddenJsps/dataImport/displaySampleRegForm.jsp" flush="true"/>
<%
}
%>
</div> <!-- sampleTypeSpecific -->

<div class="sampleRegButtons">
<p>
<input type="submit" id="btnSubmit" name="btnSubmit" value="Save This Sample" 
onclick="rememberedData.value='';onSave();"/>
</p>
<%
if (myForm.getFormDefinitions().length > 0) {
%>
<p>
<input type="button" id="btnSaveAndCreateForm" name="btnSaveAndCreateForm" 
value="Save and Create Form for This Sample" 
onclick="rememberedData.value='';onSaveAndCreateForm();"/> 
</p>
<%
}
%>
<p>
<input type="submit" id="btnNextSample" name="btnNextSample" 
value="Save and Add Next Sample for This Case" 
onclick="rememberedData.value='<%=TypeFinder.CASE%>';onSave();">
</p>
<p>
<input type="submit" id="btnNextCase" name="btnNextCase" 
value="Save and Add Next Case for This Donor" 
onclick="rememberedData.value='<%=TypeFinder.DONOR%>';onSave();">
</p>
<p>
<input type="button" id="btnCancel" name="btnCancel" value="Cancel" 
onclick="window.location.href='<html:rewrite page="/dataImport/createSampleStart.do"/>';"> 
</p>
</div>
<p style="font-size: 8pt;"><span class="kcRequiredInd">*</span> indicates a required field </p>
</html:form>
</body>
</html>
