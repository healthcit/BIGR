<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.api.Escaper" %>
<%@ page import="com.ardais.bigr.api.ValidateIds" %>
<%@ page import="com.ardais.bigr.configuration.LabelPrinter" %>
<%@ page import="com.ardais.bigr.configuration.LabelTemplateDefinition" %>
<%@ page import="com.ardais.bigr.iltds.assistants.LegalValueSet" %>
<%@ page import="com.ardais.bigr.util.Constants" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Map" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>

<bean:define name="caseForm" id="myForm" type="com.ardais.bigr.dataImport.web.form.CaseForm"/>
<html>
<head>
<title>Create Case</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<jsp:include page="/hiddenJsps/kc/data/misc/scriptAndCss.jsp" flush="true"/>
<link rel="stylesheet" href="<html:rewrite page="/css/bigr.css"/>" type="text/css">
<script language="JavaScript" src="<html:rewrite page="/js/bigrTextArea.js"/>"></script>
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript" src="<html:rewrite page="/js/registration.js"/>"></script>
<script type="text/javascript"
	src='<html:rewrite page="/js/overlib.js"/>'><!-- overLIB (c) Erik Bosrup --></script>

<script type="text/javascript">
<%
Map labelPrintingData = myForm.getLabelPrintingData();
boolean allowLabelPrinting = !ApiFunctions.isEmpty(labelPrintingData);
LegalValueSet labelTemplateDefinitions = new LegalValueSet();
LegalValueSet printers = new LegalValueSet();
Iterator labelTemplateDefinitionIterator = labelPrintingData.keySet().iterator();
while (labelTemplateDefinitionIterator.hasNext()) {
  LabelTemplateDefinition templateDefinition = (LabelTemplateDefinition)labelTemplateDefinitionIterator.next();
  //build a lvs of available templates
  labelTemplateDefinitions.addLegalValue(templateDefinition.getName(), templateDefinition.getDisplayName());
  Iterator printerIterator = ((Collection)labelPrintingData.get(templateDefinition)).iterator();
  //if a template name is selected, build a lvs of supported printers
  if (templateDefinition.getName().equalsIgnoreCase(myForm.getTemplateDefinitionName())) {
	  Iterator iterator = ((Collection)labelPrintingData.get(templateDefinition)).iterator();
	  while (iterator.hasNext()) {
	    LabelPrinter printer = (LabelPrinter)iterator.next();
	    printers.addLegalValue(printer.getName(), printer.getDisplayName());
	  }
  }
}
%>

var myBanner = 'Create Case';

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
  GSBIO.bigr.reg.CurrentLinkedValue = $('linkedIndicator').getRadiosValue();
  GSBIO.bigr.reg.showConsentsOrPolicies();
  if (GSBIO.kc.data.FormInstances.getFormInstance()) {    
    GSBIO.kc.data.FormInstances.getFormInstance().alternateRowColor();
  }
<%
  Map regFormIds = myForm.getCaseRegistrationFormIds();
  Iterator policyIds = regFormIds.keySet().iterator();
  while (policyIds.hasNext()) {
    String id = (String) policyIds.next();
%>
    GSBIO.bigr.reg.RegFormIds['<%=id%>'] = '<%=(String)regFormIds.get(id)%>';
<%
  }
  Map consentPolicyIds = myForm.getConsentPolicyIds();
  Iterator consentIds = consentPolicyIds.keySet().iterator();
  while (consentIds.hasNext()) {
    String id = (String) consentIds.next();
%>
    GSBIO.bigr.reg.PolicyIds['<%=id%>'] = '<%=(String)consentPolicyIds.get(id)%>';
<%
  }
%>
  $('customerId').focus();
<%
  if (allowLabelPrinting) {
%>
	//if no value has been selected in either of the print label drop downs, auto select any single choices
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

function updatePrintLabelCheckBox() {
  $('printCaseLabel').value = document.all.printCaseLabelCB.checked;
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
  	labelTemplateDefinitionIterator = labelPrintingData.keySet().iterator();
  	while (labelTemplateDefinitionIterator.hasNext()) {
  	  LabelTemplateDefinition templateDefinition = (LabelTemplateDefinition)labelTemplateDefinitionIterator.next();
%>
  if (selectedTemplate == '<%=Escaper.jsEscapeInScriptTag(templateDefinition.getName())%>') {
<%
			Collection supportedPrinters = (Collection)labelPrintingData.get(templateDefinition);
			Iterator printerIterator = supportedPrinters.iterator();
			int index = 1;
  	  while (printerIterator.hasNext()) {
  	    LabelPrinter printer = (LabelPrinter)printerIterator.next();
%>
    document.forms[0].printerName.options[<%=index%>]=new Option('<%=Escaper.jsEscapeInScriptTag(printer.getDisplayName())%>', '<%=Escaper.jsEscapeInScriptTag(printer.getName())%>');
<%
				index++;
   		}
%>
  }
<%
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
  $('templateDefinitionName').disabled = !document.all.printCaseLabelCB.checked;
  $('printerName').disabled = !document.all.printCaseLabelCB.checked;
  $('labelCount').disabled = !document.all.printCaseLabelCB.checked;
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
<html:form styleId="theForm" method="POST"
      action="/dataImport/createCase"
      onsubmit="return(onFormSubmit());">
  <html:hidden name="caseForm" property="ardaisAcctKey"/>
  <html:hidden name="caseForm" property="importedYN"/>
  <html:hidden name="caseForm" property="createForm"/>
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
<label class="inlineLabel"><span class="kcRequiredInd">*</span> Case Alias:</label>
<html:text name="caseForm" property="customerId" size="30" maxlength="30"/>
</div>

<div class="kcElement bigrElement">
<label class="inlineLabel"><span class="kcRequiredInd">*</span> Donor:</label>
Donor Id:
<html:text name="caseForm" property="ardaisId"
size="<%= String.valueOf(ValidateIds.LENGTH_DONOR_ID + 5) %>"
maxlength="<%= String.valueOf(ValidateIds.LENGTH_DONOR_ID) %>"/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Or&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Donor Alias:
<html:text name="caseForm" property="ardaisCustomerId" size="30" maxlength="30"/>
</div>

<div class="kcElement bigrElement">
<label class="inlineLabel"><span class="kcRequiredInd">*</span> Did the donor sign an informed consent?:</label>
<html:radio name="caseForm" property="linkedIndicator" value="Y" onclick="GSBIO.bigr.reg.showConsentsOrPolicies()">Yes</html:radio>
<html:radio name="caseForm" property="linkedIndicator" value="N" onclick="GSBIO.bigr.reg.showConsentsOrPolicies()">No</html:radio>
</div>

<div id="policySelect" class="kcElement bigrElement" style="display:none;">
<label class="inlineLabel"><span class="kcRequiredInd">*</span> Case Policy:</label>
<html:select property="policyId" onchange="GSBIO.bigr.reg.getCaseRegistrationForm();">
<% 
int numPolicyChoices = myForm.getPolicyChoices().size();
if (numPolicyChoices > 1) {
%>
<html:option value="">Select</html:option>
<% 
}
%>
<bean:define name="myForm" property="policyChoices" id="policyCollection" type="java.util.List"/>
<html:options collection="policyCollection" property="policyId" labelProperty="policyName"/>
</html:select>
</div>

<div id="consentSelect" class="kcElement bigrElement" style="display:none;">
<label class="inlineLabel"><span class="kcRequiredInd">*</span> IRB Protocol / Consent Version:</label>
<bigr:searchText
  tableRow="false"
  name="test" length="40" 
  searchButton="Search"
  refreshButton="Refresh"
  refresh_yn="Y" searchedField="consentVersionId"/>
<br>
<label class="inlineLabel">&nbsp;</label> <!--  bogus label for layout -->
<html:select name="caseForm" property="consentVersionId" onchange="GSBIO.bigr.reg.getCaseRegistrationForm();">
<% 
int numConsentChoices = myForm.getConsentChoices().size();
if (numConsentChoices > 1) {
%>
<html:option value="">Select</html:option>
<% 
}
%>
<bean:define name="myForm" property="consentChoices" id="consentCollection" type="java.util.List"/>
<html:options name="caseForm" collection="consentCollection" property="consentVersionId" labelProperty="irbProtocolAndVersionName"/>
</html:select>
</div>

<div id="consentTime" class="kcElement bigrElement" style="display:none;">
<label class="inlineLabel">Date/Time of Consent:</label>
<%
Calendar currentDate = Calendar.getInstance();
%>
<html:select name="caseForm" property="month">
<html:option value="">Month</html:option>
<%
for (int i = 1; i <= 12; i++) {
%>
<html:option value="<%=String.valueOf(i)%>"><%=i%></html:option>
<%
} 
%>
</html:select> / 
<html:select name="caseForm" property="year">
<html:option value="">Year</html:option>
<% 
int currentYear = currentDate.get(Calendar.YEAR);
for (int i = currentYear; i >= currentYear - Constants.CONSENT_DATE_LOOKBACK_YEARS; i--) {
%>
<html:option value="<%=String.valueOf(i)%>"><%=i%></html:option>
<% 
} 
%>
</html:select> @ 
<html:select name="caseForm" property="hours">
<html:option value="">Hour</html:option>
<% 
for (int i = 1 ; i <= 12; i++) {
%>
<html:option value="<%=String.valueOf(i)%>"><%=i%></html:option>
<% 
} 
%>
</html:select> : 
<html:select name="caseForm" property="minutes">
<html:option value="">Minute</html:option>
<% 
for (int i = 0; i <= 59; i++) {
%>
<html:option value="<%=String.valueOf(i)%>"> <%=((i >= 10) ? String.valueOf(i) : ("0" + i))%></html:option>
<% 
} 
%>
</html:select>
<html:select name="caseForm" property="ampm">
<html:option value="">AM/PM</html:option>
<html:option value="am">AM</html:option>
<html:option value="pm">PM</html:option>
</html:select>
</div>
<%
	//if label printing is enabled for the account, provide a checkbox allowing the user to 
	//print a label (or labels) for the sample
	if (allowLabelPrinting) {
%>
<div class="kcElement bigrElement">
<label class="inlineLabel">Label Printing:</label>
  <input type="checkbox" name="printCaseLabelCB" onclick="updatePrintLabelCheckBox();enablePrintLabelControls();" <%=myForm.isPrintCaseLabel() ? "checked" : ""%>/>&nbsp;Print Label
  <html:hidden property="printCaseLabel"/>
  <br>
	<label class="inlineLabel">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
	Number of labels:&nbsp;&nbsp;<html:text name="caseForm" property="labelCount" size="5" maxlength="2"/>
  <br>
	<label class="inlineLabel">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
	Label template:&nbsp;&nbsp;&nbsp;&nbsp;
				<bigr:selectList name="caseForm" property="templateDefinitionName"
  				firstValue="" firstDisplayValue="Select"
  				legalValueSet="<%=labelTemplateDefinitions%>"
  				onchange="updatePrinterChoices();"/>
  <br>
	<label class="inlineLabel">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
	Label printer:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<bigr:selectList name="caseForm" property="printerName"
  				firstValue="" firstDisplayValue="Select"
  				legalValueSet="<%=printers%>"/>
</div>
<%
	}
%>

</div> <!-- kcElements -->

<div id="policySpecific">
<%
// If a registration form has been chosen, then display it.  One situation in
// which this occurs is if we come back to this page after an error.
if (myForm.getRegistrationForm() != null) {
  request.setAttribute("ignoreErrors", "true");
%>
<jsp:include page="/hiddenJsps/dataImport/displayCaseRegForm.jsp" flush="true"/>
<%
}
%>
</div> <!-- policySpecific -->

<div class="sampleRegButtons">
<p>
<input type="submit" id="btnSubmit" name="btnSubmit" value="Save" onclick="onSave();"/>
</p>
<%
if (myForm.getFormDefinitions().length > 0) {
%>
<p>
<input type="button" id="btnSaveAndCreateForm" name="btnSaveAndCreateForm" 
value="Save and Create Form" onclick="onSaveAndCreateForm();"/> 
</p>
<%
}
%>
</div>
<p style="font-size: 8pt;"><span class="kcRequiredInd">*</span> indicates a required field </p>
</html:form>
</body>
</html>
