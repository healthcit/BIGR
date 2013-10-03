<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.api.Escaper" %>
<%@ page import="com.ardais.bigr.configuration.LabelPrinter" %>
<%@ page import="com.ardais.bigr.configuration.LabelTemplateDefinition" %>
<%@ page import="com.ardais.bigr.iltds.assistants.LegalValueSet" %>
<%@ page import="com.ardais.bigr.util.Constants" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Map" %>
<bean:define name="printLabelsForm" id="myForm" type="com.ardais.bigr.dataImport.web.form.PrintLabelsForm"/>
<html>
<head>
<title>Print Labels</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<html:rewrite page="/css/bigr.css"/>" type="text/css">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript" src="<html:rewrite page="/js/BigrLib.js"/>"></script>
<script language="JavaScript" src="<html:rewrite page="/js/prototype.js"/>"></script>
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

function initPage() {
  if (parent.topFrame) {
    parent.topFrame.document.all.banner.innerHTML = 'Print Labels';
  }
  deselectAllFromList("ids");
  renderControls('<%=myForm.getAction()%>');
  //if there is only one choice (other than "Select") in the label template drop-down, select it
  selectSingleDropDownChoice(document.forms[0].printerName, 2);
  //if there is only one choice (other than "Select") in the label printer drop-down, select it
  selectSingleDropDownChoice(document.forms[0].templateDefinitionName, 2)
}

function doSubmit() {
  if (onFormSubmit()) {
    if (document.forms[0]) {
      document.forms[0].submit();
    }
  }
}

function onFormSubmit() {
  setActionButtonEnabling(false);
  if (!selectAllIds()) {
    setActionButtonEnabling(true);
    return false;
  }
  return true;
}
   
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.forms[0];
  f.btnSubmit.disabled = isDisabled;
  f.btnCancel.disabled = isDisabled;
  disableControlCollection('idsButton', isDisabled);
}

function onClickCancel() {
  setActionButtonEnabling(false);
  window.location = '<html:rewrite page="/dataImport/printLabelsStart.do"/>';
}

function selectAllIds() {
  if (!addItemToList("id", "ids", IGNORE_TYPE, true)) {return false;}
  selectAllFromList("ids");
  return true;
}

function renderControls(action) {
  if ('<%=Constants.LABEL_PRINTING_ACTION_PREPRINT%>' == action) {
    document.all.prePrintControls.style.display = 'inline';  
    document.all.rePrintControls.style.display = 'none';  
  }
  else if ('<%=Constants.LABEL_PRINTING_ACTION_REPRINT%>' == action) {
    document.all.prePrintControls.style.display = 'none';  
    document.all.rePrintControls.style.display = 'inline';  
  }
  else {
    document.all.prePrintControls.style.display = 'none';  
    document.all.rePrintControls.style.display = 'none';
  }
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
	}
%>
  //if there is only one choice (other than "Select") in the drop-down, select it
  selectSingleDropDownChoice(document.forms[0].printerName, 2);
}

</script>
</head>
<body onload="initPage();">
<html:form method="POST" action="/dataImport/printLabels" onsubmit="return(onFormSubmit());">
<%
  // DIV for errors
%>
  <div id="errorDiv" align="center">
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
      <logic:present name="org.apache.struts.action.ERROR">
      <tr class="yellow"> 
	    <td> 
	      <div align="white">
	        <font color="#FF0000"><b><html:errors/></b></font>
	      </div>
	    </td>
	  </tr>
	  </logic:present>
      <logic:present name="com.ardais.BTX_ACTION_MESSAGES">
	  <tr class="white"> 
	    <td> 
	      <div align="left">
	        <font color="#000000"><b><bigr:btxActionMessages/></b></font>
	      </div>
	    </td>
	  </tr>
	  </logic:present>
	</table>
  </div>
  <br>

	<html:hidden name="printLabelsForm" property="action"/>
	<html:hidden name="printLabelsForm" property="objectType"/>
 
	<div id="prePrintControls" style="display: none">
  	<table border="0" cellspacing="1" cellpadding="3" class="foreground">
		  <tr class="white">
			  <td align="right" valign="center">
					Number of <%=myForm.getObjectType()%>s for which labels<br>should be preprinted:
		    </td>
		    <td align="left" valign="center">
					<html:text name="printLabelsForm" property="preprintedLabelCount"/>
				</td>
			</tr>
		</table>
	</div>

	<div id="rePrintControls">
  	<table border="0" cellspacing="0" cellpadding="0">
    	<tr> 
      	<td> 
        	<table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
		  			<tr class="white" align="left" valign="center">
		    			<td style="padding: 1em 0 1em 0;">
		      			<%=ApiFunctions.capitalize(myForm.getObjectType()) %> Id or Alias
		      			<br>
<%
		String itemType = null;
		if (Constants.LABEL_PRINTING_OBJECT_TYPE_SAMPLE.equalsIgnoreCase(myForm.getObjectType())) {
			itemType = "IGNORE_TYPE";
%>
		      			<input type="text" name="id" size="20" maxlength="30"
  		         		onBlur="if (document.all['id'].value.length != 0) {
    			           if (!isValidId_Alert(document.all['id'].value, IGNORE_TYPE, true)) {
    		   		         document.all['id'].value = '';
    		   		         document.all['id'].focus();
    		   	           }
    		             }"/>
<%
		}
		else if (Constants.LABEL_PRINTING_OBJECT_TYPE_CASE.equalsIgnoreCase(myForm.getObjectType())) {
			itemType = "IGNORE_TYPE";
%>
		      			<input type="text" name="id" size="20" maxlength="30"
  		         		onBlur="if (document.all['id'].value.length != 0) {
    			           if (!isValidId_Alert(document.all['id'].value, IGNORE_TYPE, true)) {
    		   		         document.all['id'].value = '';
    		   		         document.all['id'].focus();
    		   	           }
    		             }"/>
<%
		}
		else if (Constants.LABEL_PRINTING_OBJECT_TYPE_DONOR.equalsIgnoreCase(myForm.getObjectType())) {
			itemType = "IGNORE_TYPE";
%>
		      			<input type="text" name="id" size="20" maxlength="30"
  		         		onBlur="if (document.all['id'].value.length != 0) {
    			           if (!isValidId_Alert(document.all['id'].value, IGNORE_TYPE, true)) {
    		   		         document.all['id'].value = '';
    		   		         document.all['id'].focus();
    		   	           }
    		             }"/>
<%
		}
%>
		    			</td>
		    			<td align="center" style="padding: 1em;">
		      			<bigr:idlist property="idsButton" type="<%=itemType%>" idfrom="id" 
		      				idlist="ids" style="FONT-SIZE:xx-small"/>
		    			</td>
		    			<td style="padding: 1em 0 1em 0;">
		      			<%=ApiFunctions.capitalize(myForm.getObjectType())%> Ids or Aliases
		      			<br>
		      			<html:select property="ids" size="20" multiple="true" 
		      				style="width: 150px">
		        			<logic:present name="printLabelsForm" property="ids">
		          			<html:options property="ids"/>
		        			</logic:present>
		      			</html:select>
		    			</td>
		  			</tr>
        	</table>
      	</td>
    	</tr>
  	</table>
	</div>
	<div id="commonControls">
  	<table border="0" cellspacing="1" cellpadding="3" class="foreground">
		  <tr class="white">
			  <td align="right" valign="center">
					Number of times to print each label:
		    </td>
		    <td align="left" valign="center">
					<html:text name="printLabelsForm" property="labelCount" size="5" maxlength="2"/>
				</td>
			</tr>
		<tr class="white">
			<td align="right" valign="center">
		  	Label template:
		  </td>
		  <td align="left" valign="center">
				<bigr:selectList name="printLabelsForm" property="templateDefinitionName"
  				firstValue="" firstDisplayValue="Select"
  				legalValueSet="<%=labelTemplateDefinitions%>"
  				onchange="updatePrinterChoices();"/>
		  </td>
		</tr>
		<tr class="white">
			<td align="right" valign="center">
		  	Label printer:
		  </td>
		  <td align="left" valign="center">
				<bigr:selectList name="printLabelsForm" property="printerName"
  				firstValue="" firstDisplayValue="Select"
  				legalValueSet="<%=printers%>"/>
		  </td>
		</tr>
		</table>
	</div>
	<br>
	<table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
	    <tr class="white"> 
	      <td align="center">
      		<input type="button" name="btnSubmit" value="Submit" onclick="doSubmit();">&nbsp;&nbsp;
      		<input type="button" name="btnCancel" value="Cancel" onclick="onClickCancel();">
	      </td>
	    </tr>
	</table>
</html:form>
</body>

</html>