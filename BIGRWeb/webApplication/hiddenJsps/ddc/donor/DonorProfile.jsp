<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.api.Escaper" %>
<%@ page import="com.ardais.bigr.configuration.LabelPrinter" %>
<%@ page import="com.ardais.bigr.configuration.LabelTemplateDefinition" %>
<%@ page import="com.ardais.bigr.iltds.assistants.LegalValueSet" %>
<%@ page import="com.ardais.bigr.pdc.helpers.DonorHelper" %>
<%@ page import="com.gulfstreambio.kc.form.def.data.DataFormDefinition" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContextStack" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Map" %>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/kctaglib" prefix="kc" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, getServletContext(), "P");

//The form definition must be specified, so if it is not throw an exception.
FormContextStack stack = FormContextStack.getFormContextStack(request);
FormContext formContext = stack.peek();
DataFormDefinition formDefinition = formContext.getDataFormDefinition();
%>
<bean:define name="helper" id="donor" toScope="request" type="com.ardais.bigr.pdc.helpers.DonorHelper"/>
<%
	String pageTitle = "Donor Information";
	boolean importedDonor = false;
	if (donor != null) {
	  if ("Y".equalsIgnoreCase(donor.getImportedYN())) {
	  	importedDonor = true;
		if (donor.isNew()) {
			pageTitle = "Create Donor";			
		}
		else {
			pageTitle = "Modify Donor";
		}
	  }
	}
%>
<%
Map labelPrintingData = donor.getLabelPrintingData();
//allow label printing if there is label printing data and we're dealing with an imported donor
boolean allowLabelPrinting = !ApiFunctions.isEmpty(labelPrintingData) && importedDonor;
LegalValueSet labelTemplateDefinitions = new LegalValueSet();
LegalValueSet printers = new LegalValueSet();
Iterator labelTemplateDefinitionIterator = null;
if (allowLabelPrinting) {
  labelTemplateDefinitionIterator = labelPrintingData.keySet().iterator();
  while (labelTemplateDefinitionIterator.hasNext()) {
    LabelTemplateDefinition templateDefinition = (LabelTemplateDefinition)labelTemplateDefinitionIterator.next();
    //build a lvs of available templates
    labelTemplateDefinitions.addLegalValue(templateDefinition.getName(), templateDefinition.getDisplayName());
    Iterator printerIterator = ((Collection)labelPrintingData.get(templateDefinition)).iterator();
    //if a template name is selected, build a lvs of supported printers
    if (templateDefinition.getName().equalsIgnoreCase(donor.getTemplateDefinitionName())) {
  	  Iterator iterator = ((Collection)labelPrintingData.get(templateDefinition)).iterator();
  	  while (iterator.hasNext()) {
  	    LabelPrinter printer = (LabelPrinter)iterator.next();
  	    printers.addLegalValue(printer.getName(), printer.getDisplayName());
  	  }
    }
  }
}
%>

<html>
<head>
<title>Donor Information</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<jsp:include page="/hiddenJsps/kc/data/misc/scriptAndCss.jsp" flush="true"/>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script type="text/javascript">

function initPage() {
  if (parent.topFrame && parent.topFrame.document.all.banner) {
    parent.topFrame.document.all.banner.innerHTML = '<%=pageTitle%>';
  }
  <% if(importedDonor) { %>
	  document.all.customerId.focus();
  <% } %>
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
	  //enable/disable the print label controls as appropriate
  }
  enablePrintLabelControls();
  <%
  }
  %>
}

function onFormSubmit() {
  disableActionButtons();
  if (GSBIO.kc.data.FormInstances.getFormInstance()) {
    $('form').value = GSBIO.kc.data.FormInstances.getFormInstance().serialize();
  }
  return true;
}
  
function disableActionButtons() {
	document.donorprofile.btnSubmit.disabled = true;
	if (document.donorprofile.btnSaveAndCreateForm) {
	  document.donorprofile.btnSaveAndCreateForm.disabled = true;
	}
}

function onSaveAndCreateForm() {
  document.donorprofile.createForm.value = true;
  if (onFormSubmit()) {
    document.donorprofile.submit();
  }
}

<%
if (allowLabelPrinting) {
%>
function updatePrintLabelCheckBox() {
  $('printDonorLabel').value = document.all.printDonorLabelCB.checked;
}

function enablePrintLabelControls() {
  $('templateDefinitionName').disabled = !document.all.printDonorLabelCB.checked;
  $('printerName').disabled = !document.all.printDonorLabelCB.checked;
  $('labelCount').disabled = !document.all.printDonorLabelCB.checked;
}

function updatePrinterChoices() {
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
}
<%
}
%>
</script>
</head>

<body class="bigr" onLoad="initPage()">
<form name="donorprofile" method="POST" action="<html:rewrite page="/ddc/Dispatch"/>" onsubmit="return(onFormSubmit());">
<input type="hidden" name="op" value="DonorProfileEdit">
<html:hidden name="donor" property="ardaisId"/>
<html:hidden name="donor" property="new"/>
<html:hidden name="donor" property="importedYN"/>
<html:hidden name="donor" property="ardaisAccountKey"/>
<input type="hidden" name="createForm" value="false">
<logic:equal parameter="readOnly" value="true">
  <%
    Map linkParams = donor.getDonorLinkParams();
    linkParams.put("readOnly", "true");
  %>
</logic:equal>

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

  <div align="center">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" border="0" cellpadding="3" cellspacing="1">
          <%=donor.getMessageHelper().getMessages(1, false)%>
          </table>
        </td>
      </tr>
    </table>
  </div>
  
  <logic:notEqual name="donor" property="importedYN" value="Y">
    <p><div align="center">
      <table class="background" cellpadding="0" cellspacing="0" border="0">
        <tr> 
          <td> 
            <table class="foreground" border="0" cellpadding="3" cellspacing="1">
              <tr class="white"> 
                <td class="yellow" align="right"><b>Donor</b></td>
                <td>
              	  <html:link page="/ddc/Dispatch" name="donor" property="donorLinkParams">
                    <bean:write name="donor" property="ardaisId"/>
                  </html:link>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <br>
    </div>
  </logic:notEqual>
  <logic:equal name="donor" property="importedYN" value="Y">
    <logic:equal name="donor" property="new" value="false">
	    <p><div align="center">
	      <table class="background" cellpadding="0" cellspacing="0" border="0">
	        <tr> 
	          <td> 
	            <table class="foreground" border="0" cellpadding="3" cellspacing="1">
	              <tr class="white"> 
	                <td class="yellow" align="right"><b>Donor</b></td>
	                <td>
	              	  <html:link page="/ddc/Dispatch" name="donor" property="donorLinkParams">
	                    <bean:write name="donor" property="ardaisId"/>
	                  </html:link>
									  <logic:notEmpty name="donor" property="customerId">
									    &nbsp;(<bean:write name="donor" property="customerId"/>)
									  </logic:notEmpty>
	                </td>
	              </tr>
	            </table>
	          </td>
	        </tr>
	      </table>
	      <br>
	    </div>
	</logic:equal>
  </logic:equal>

  <logic:equal name="donor" property="importedYN" value="Y">
    <div class="kcElements bigrElements">
      <div class="kcElement bigrElement">
        <label class="inlineLabel"><span class="kcRequiredInd">*</span> Donor Alias:</label>
        <logic:notEqual parameter="readOnly" value="true">
          <html:text name="donor" property="customerId" size="30" maxlength="30"/> 
        </logic:notEqual>
        <logic:equal parameter="readOnly" value="true">
          <html:text name="donor" property="customerId" size="30" maxlength="30" readonly="true"/>
        </logic:equal>
      </div>
<%
	//if label printing is enabled for the account, provide a checkbox allowing the user to 
	//print a label (or labels) for the donor
	if (allowLabelPrinting) {
%>
      <div class="kcElement bigrElement">
        <label class="inlineLabel">Label Printing:</label>
        <input type="checkbox" name="printDonorLabelCB" onclick="updatePrintLabelCheckBox();enablePrintLabelControls();" <%=donor.isPrintDonorLabel() ? "checked" : ""%>/>&nbsp;Print Label
        <html:hidden name="donor" property="printDonorLabel"/>
        <br>
	      <label class="inlineLabel">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
	      Number of labels:&nbsp;&nbsp;<html:text name="donor" property="labelCount" size="5" maxlength="2"/>
        <br>
	      <label class="inlineLabel">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
	      Label template:&nbsp;&nbsp;&nbsp;&nbsp;
				<bigr:selectList name="donor" property="templateDefinitionName"
  				firstValue="" firstDisplayValue="Select"
  				legalValueSet="<%=labelTemplateDefinitions%>"
  				onchange="updatePrinterChoices();"/>
        <br>
	      <label class="inlineLabel">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
	      Label printer:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<bigr:selectList name="donor" property="printerName"
  				firstValue="" firstDisplayValue="Select"
  				legalValueSet="<%=printers%>"/>
      </div>
<%
	}
%>
    </div> <!-- kcElements -->
  </logic:equal>

<%
if (formDefinition != null) {
%>
<input type="hidden" name="form" value=""/>
<kc:formInstancePagesEdit/>
<%
}
%>

<div class="sampleRegButtons">
<p>
<logic:notEqual parameter="readOnly" value="true">
<logic:equal name="donor" property="new" value="true">
  <html:submit property="btnSubmit" value="Save"/>
<%
if (donor != null && donor.getFormDefinitions().length > 0) {
%>
  <html:button property="btnSaveAndCreateForm" value="Save and Create Form" onclick='onSaveAndCreateForm();'/>
<%
}
%>
</logic:equal>
<logic:equal name="donor" property="new" value="false">
  <html:submit property="btnSubmit" value="Update"/>
</logic:equal>							
<%
if (donor != null && "Y".equalsIgnoreCase(donor.getImportedYN())) {
  if (!donor.isNew()) {
%>
  <input type="button" value="Cancel" onclick="window.location.href='<html:rewrite page="/ddc/Dispatch" name="donor" property="donorLinkParams"/>';"> 
<%
  }
}
else {
%>
  <input type="button" value="Cancel" onclick="window.location.href='<html:rewrite page="/ddc/Dispatch" name="donor" property="donorLinkParams"/>';"> 
<%
}
%>
</logic:notEqual>
<logic:equal parameter="readOnly" value="true">
  <input type="button" value="Back" onclick="window.location.href='<html:rewrite page="/ddc/Dispatch" name="donor" property="donorLinkParams"/>';"> 
</logic:equal>
</p>
</div>
<p style="font-size: 8pt;"><span class="kcRequiredInd">*</span> indicates a required field </p>
</form>
</body>
</html>
