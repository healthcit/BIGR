<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.api.Escaper" %>
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
<title>Modify Case</title>
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

var myBanner = 'Modify Case';

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
  if (GSBIO.kc.data.FormInstances.getFormInstance()) {    
    GSBIO.kc.data.FormInstances.getFormInstance().alternateRowColor();
  }
<%
if (!myForm.isReadOnly()) {
%>
  $('customerId').focus();
<%
}
%>
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
      action="/dataImport/modifyCase"
      onsubmit="return(onFormSubmit());">
  <html:hidden name="caseForm" property="ardaisAcctKey"/>
  <html:hidden name="caseForm" property="consentId"/>
  <html:hidden name="caseForm" property="importedYN"/>
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
<label class="inlineLabel">Case ID:</label>
<bean:write name="caseForm" property="consentId"/>
</div>

<div class="kcElement bigrElement">
<label class="inlineLabel">
<%
if (!myForm.isReadOnly()) {
%>
<span class="kcRequiredInd">*</span>
<%
}
%>
Case Alias:</label>
<%
if (myForm.isReadOnly()) {
%>
<bean:write name="caseForm" property="customerId"/>
<%
}
else {
%>
<html:text name="caseForm" property="customerId" size="30" maxlength="30"/>
<%
}
%>
</div>

<div class="kcElement bigrElement">
<label class="inlineLabel">Donor:</label>
<bean:write name="caseForm" property="ardaisId"/>
<logic:notEmpty name="caseForm" property="ardaisCustomerId">
(<bean:write name="caseForm" property="ardaisCustomerId" />)
</logic:notEmpty>
<html:hidden name="caseForm" property="ardaisId"/>
</div>

<div class="kcElement bigrElement">
<label class="inlineLabel">Did the donor sign an informed consent?:</label>
<bean:write name="caseForm" property="linkedIndicator"/>
<html:hidden name="caseForm" property="linkedIndicator"/>
</div>

<logic:equal name="caseForm" property="linkedIndicator" value="Y">
<div class="kcElement bigrElement">
<label class="inlineLabel">IRB Protocol / Consent Version:</label>
<bean:write name="caseForm" property="irbProtocolAndVersionName"/>
<html:hidden name="caseForm" property="consentVersionId"/>
</div>

<div class="kcElement bigrElement">
<label class="inlineLabel">Date/Time of Consent:</label>
<%
if (myForm.isReadOnly()) {
%>
<bean:write name="caseForm" property="month"/>/<bean:write name="caseForm" property="year"/>
@ <bean:write name="caseForm" property="hours"/>:<bean:write name="caseForm" property="minutes"/>
<bean:write name="caseForm" property="ampm"/>
<%
}
else {
%>
<%
Calendar currentDate = Calendar.getInstance();
%>
<html:select name="caseForm" property="month">
<html:option value="">Month</html:option>
<%
for (int i = 1; i <= 12; i++) {
%>
<html:option value="<%= String.valueOf(i) %>"><%= i %></html:option>
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
<html:option value="<%= String.valueOf(i) %>"><%= i %></html:option>
<%
}
%>
</html:select> @ 
<html:select name="caseForm" property="hours">
<html:option value="">Hour</html:option>
<%
for (int i = 1 ; i <= 12; i++) {
%>
<html:option value="<%= String.valueOf(i) %>"><%= i %></html:option>
<%
}
%>
</html:select> : 
<html:select name="caseForm" property="minutes">
<html:option value="">Minute</html:option>
<% 
for (int i = 0; i <= 59; i++) {
%>
<html:option value="<%= String.valueOf(i) %>">
<%=((i >= 10) ? String.valueOf(i) : ("0" + i))
%>
</html:option>
<%
}
%>
</html:select>
<html:select name="caseForm" property="ampm">
<html:option value="">AM/PM</html:option>
<html:option value="am">AM</html:option>
<html:option value="pm">PM</html:option>
</html:select>
<%
}
%>
</div>
</logic:equal>

<logic:equal name="caseForm" property="linkedIndicator" value="N">
<div class="kcElement bigrElement">
<label class="inlineLabel">Case Policy:</label>
<bean:write name="caseForm" property="policyName"/>
<html:hidden name="caseForm" property="policyId"/>
</div>
</logic:equal>
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
<%
	}
%>
</div>

</div> <!-- kcElements -->

<div id="policySpecific">
<%
// Display the registration form in either edit or summary mode.
if (myForm.isReadOnly()) {
%>
<jsp:include page="/hiddenJsps/dataImport/displayCaseRegFormSummary.jsp" flush="true"/>
<%
} 
else {
  request.setAttribute("ignoreErrors", "true");
%>
<jsp:include page="/hiddenJsps/dataImport/displayCaseRegForm.jsp" flush="true"/>
<%
}
%>
</div> <!-- policySpecific -->

<div class="sampleRegButtons">
<%
if (!myForm.isReadOnly()) {
%>
<p>
<input type="submit" id="btnSubmit" name="btnSubmit" value="Update"/>
<%
}
String cancelButtonName = "Cancel";
String cancelUrl = "/dataImport/getCaseFormSummary.do?consentId=" + myForm.getConsentId();
if (myForm.isReadOnly()) {
  cancelUrl = cancelUrl + "&readOnly=true";
  cancelButtonName = "Back";
}
%>
<input type="button" name="btnCancel" value="<%=cancelButtonName%>" onclick="window.location.href='<html:rewrite page="<%=cancelUrl%>"/>';"> 
</p>
</div>
<p style="font-size: 8pt;"><span class="kcRequiredInd">*</span> indicates a required field </p>
</html:form>
</body>
</html>
