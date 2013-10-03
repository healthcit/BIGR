<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.api.Escaper" %>
<%@ page import="com.ardais.bigr.configuration.LabelPrinter" %>
<%@ page import="com.ardais.bigr.configuration.LabelTemplateDefinition" %>
<%@ page import="com.ardais.bigr.iltds.assistants.LegalValueSet" %>
<%@ page import="com.ardais.bigr.security.SecurityInfo" %>
<%@ page import="com.ardais.bigr.util.IcpUtils" %> 
<%@ page import="com.ardais.bigr.util.WebUtils"%>
<%@ page import="com.ardais.bigr.javabeans.DerivationDto" %>
<%@ page import="com.ardais.bigr.iltds.helpers.IltdsUtils" %>
<%@ page import="com.ardais.bigr.util.ArtsConstants" %>
<%@ page import="com.ardais.bigr.util.Constants" %>
<%@ page import="com.ardais.bigr.util.IcpUtils" %> 
<%@ page import="com.ardais.bigr.util.WebUtils"%>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.gulfstreambio.kc.form.def.data.DataFormDefinition" %>
<%@ page import="com.gulfstreambio.kc.form.def.data.DataFormDefinitionElement" %>
<%@ page import="com.gulfstreambio.kc.form.def.data.DataFormDefinitionHostElement" %>
<%@ page import="com.gulfstreambio.kc.web.support.DataFormDataElementContext" %>
<%@ page import="com.gulfstreambio.kc.form.def.data.DataFormDefinitionDataElement" %>
<%@ page import="com.gulfstreambio.kc.form.def.Tag" %>
<%@ page import="com.ardais.bigr.kc.form.def.TagTypes" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>



<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>

<bean:define name="derivationBatchForm" property="dto" id="batchDto" type="com.ardais.bigr.javabeans.DerivationBatchDto"/>
<bean:define id="batchForm" name="derivationBatchForm" type="com.ardais.bigr.iltds.web.form.DerivationBatchForm"/>
<html>
<head>
<title>Print Labels for Derivation</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript" src='<html:rewrite page="/js/ssresults.js"/>'></script>
<script type="text/javascript" src='<html:rewrite page="/js/overlib.js"/>'><!-- overLIB (c) Erik Bosrup --></script>
<script language="JavaScript" src="<html:rewrite page="/js/derivation.js"/>"></script>
<script language="JavaScript" src="<html:rewrite page="/js/prototype.js"/>"></script>
<script language="JavaScript" src="<html:rewrite page="/js/linkedlist.js"/>"></script>
<script language="JavaScript" src="<html:rewrite page="/js/map.js"/>"></script>

<script language="JavaScript">
<%
//build a map of sample types to supported template definitions, and a map of template definitions 
//to supported printers
Map labelPrintingData = batchForm.getLabelPrintingData();
boolean allowLabelPrinting = !ApiFunctions.isEmpty(labelPrintingData);
Map sampleTypeToTemplates = new HashMap();
Map templateToPrinters = new HashMap();
Iterator sampleTypeIterator = labelPrintingData.keySet().iterator();
while (sampleTypeIterator.hasNext()) {
  String sampleType = (String)sampleTypeIterator.next();
  LegalValueSet labelTemplateDefinitions = new LegalValueSet();
  sampleTypeToTemplates.put(sampleType, labelTemplateDefinitions);
  Iterator labelTemplateDefinitionIterator = ((Map)labelPrintingData.get(sampleType)).keySet().iterator();
  while (labelTemplateDefinitionIterator.hasNext()) {
  	LabelTemplateDefinition templateDefinition = (LabelTemplateDefinition)labelTemplateDefinitionIterator.next();
  	labelTemplateDefinitions.addLegalValue(templateDefinition.getName(), templateDefinition.getDisplayName());
  	Collection supportedPrinters = (Collection)((Map)labelPrintingData.get(sampleType)).get(templateDefinition);
    LegalValueSet printers = new LegalValueSet();
    templateToPrinters.put(templateDefinition.getName(), printers);
    Iterator printerIterator = supportedPrinters.iterator();
	  while (printerIterator.hasNext()) {
	    LabelPrinter printer = (LabelPrinter)printerIterator.next();
	    printers.addLegalValue(printer.getName(), printer.getDisplayName());
	  }
  }
}

%>

function initPage() {
  if (parent.topFrame) {
   // parent.topFrame.document.all.banner.innerHTML = "Derivative Operations";
   parent.topFrame.document.getElementById("banner").innerHTML = "Derivative Operations";
  }
<%
{
	int derivationCount = batchDto.getDerivations().size();
	for (int i=0; i<derivationCount; i++) {
	  DerivationDto derivation = batchDto.getDerivation(i);
	  int childCount = derivation.getChildren().size();
	  for (int j=0; j<childCount; j++) {
%>
  //if no value has been selected in either of the print label drop downs, auto select any single choices
  if (document.all['dto.derivation[<%=i%>].child[<%=j%>].templateDefinitionName'].value == "" 
    && document.all['dto.derivation[<%=i%>].child[<%=j%>].printerName'].value == "") {
      selectSingleDropDownChoice(document.all['dto.derivation[<%=i%>].child[<%=j%>].templateDefinitionName'], 2);
      selectSingleDropDownChoice(document.all['dto.derivation[<%=i%>].child[<%=j%>].printerName'], 2);
  }
  enablePrintLabelControls(<%=i%>,<%=j%>);
<%
	  }
	}
}
%>
}
  
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.forms[0];
  f.btnSubmit.disabled = isDisabled;
  f.btnCancel.disabled = isDisabled;
}

function validate() {
  setActionButtonEnabling(false);
  return true;
}

function updatePrintLabelCheckBox(derivationIndex, derivativeIndex) {
  var checkBoxName = "dto.derivation[" + derivationIndex + "].child[" + derivativeIndex + "].printLabelCheckBox"
  var propertyName = "dto.derivation[" + derivationIndex + "].child[" + derivativeIndex + "].printLabel"
  document.all[propertyName].value = document.all[checkBoxName].checked;
 
}

function updatePrinterChoices(derivationIndex, derivativeIndex) {
<%
	if (allowLabelPrinting) {
%>
  //clear the existing printer choices
  var propertyName = "dto.derivation[" + derivationIndex + "].child[" + derivativeIndex + "].printerName"
  
  for(var i=document.all[propertyName].length; i>0; i--)
  {
    document.all[propertyName].remove(i);    
  }
  
  //populate new choices based on the selected template
  propertyName = "dto.derivation[" + derivationIndex + "].child[" + derivativeIndex + "].templateDefinitionName"
  var selectedTemplate = document.all[propertyName].value;
 
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
    propertyName = "dto.derivation[" + derivationIndex + "].child[" + derivativeIndex + "].printerName"
    document.all[propertyName].options[<%=index%>]=new Option('<%=Escaper.jsEscapeInScriptTag(printer.getDisplayName())%>', '<%=Escaper.jsEscapeInScriptTag(printer.getName())%>');
   
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
      selectSingleDropDownChoice(document.all['dto.derivation[' + derivationIndex + '].child[' + derivativeIndex + '].printerName'], 2);
<%
	}
%>
}

function enablePrintLabelControls(derivationIndex, derivativeIndex) {
  var controlName = 'dto.derivation[' + derivationIndex + '].child[' + derivativeIndex + '].printLabelCheckBox';
  var isChecked = document.all[controlName].checked;
  controlName = 'dto.derivation[' + derivationIndex + '].child[' + derivativeIndex + '].labelCount';
  document.all[controlName].disabled = !isChecked;
  controlName = 'dto.derivation[' + derivationIndex + '].child[' + derivativeIndex + '].templateDefinitionName';
  document.all[controlName].disabled = !isChecked;
  controlName = 'dto.derivation[' + derivationIndex + '].child[' + derivativeIndex + '].printerName';
  document.all[controlName].disabled = !isChecked;
}


var ATTRIBUTE_LABEL_TEMPLATE = "LABEL_TEMPLATE";
var ATTRIBUTE_LABEL_PRINTER = "LABEL_PRINTER";
var ATTRIBUTE_LABEL_PRINT = "LABEL_PRINT";
var ATTRIBUTE_LABEL_NUMBER = "LABEL_NUMBER";

var derivationBatch = new DerivationBatch(); 
derivationBatch.setNumberOfDerivations(0); 
derivationBatch.setTableId("derivationBatchTable");
var derivation; 
var copyButtonMap = new Map();
var copyButtonCount = 0;


function copyAttributes(derivationIndex, derivativeIndex) {
  var popupInput = new Object();
 
  var sampleIdInputName = "dto.derivation[" + derivationIndex + "].child[" + derivativeIndex + "].sampleId";
  
  popupInput.sampleId = $(sampleIdInputName).value;
  var candidateAttributes = new Array();
  popupInput.attributes = candidateAttributes;
  var candidateAttribute;
  var candidateAttributeCount = 0;
  var allPotentialChildAttributes = new Object();
  
  //
  candidateAttribute = new Attribute();
  candidateAttribute.setCui(ATTRIBUTE_LABEL_PRINT);
  candidateAttribute.setCuiDescription('Print Label');
  candidateAttributes[candidateAttributeCount] = candidateAttribute;
  candidateAttributeCount = candidateAttributeCount + 1;
  candidateAttribute = new Attribute();
  candidateAttribute.setCui(ATTRIBUTE_LABEL_NUMBER);
  candidateAttribute.setCuiDescription('Number of Labels');
  candidateAttributes[candidateAttributeCount] = candidateAttribute;
  candidateAttributeCount = candidateAttributeCount + 1;
  candidateAttribute = new Attribute();
  candidateAttribute.setCui(ATTRIBUTE_LABEL_TEMPLATE );
  candidateAttribute.setCuiDescription('Label Template');
  candidateAttributes[candidateAttributeCount] = candidateAttribute;
  candidateAttributeCount = candidateAttributeCount + 1;
  candidateAttribute = new Attribute();
  candidateAttribute.setCui(ATTRIBUTE_LABEL_PRINTER);
  candidateAttribute.setCuiDescription('Label Printer');
  candidateAttributes[candidateAttributeCount] = candidateAttribute;
  candidateAttributeCount = candidateAttributeCount + 1;
 
 
  //include the visible attributes for the child sample chosen as a source as candidates
 /* var allAttributes = allPotentialChildAttributes[derivationIndex];
  
  for (var attribute = allAttributes.getFirstAttribute(); attribute != null; attribute = allAttributes.getNextAttribute()) {
   
    var attributeCui = attribute.getCui();
    var attributeDiv = attributeCui  + "Div" + derivationIndex + derivativeIndex;
    if ($(attributeDiv).style.display == "inline") {
      candidateAttribute = new Attribute();
      candidateAttribute.setCui(attributeCui);
      candidateAttribute.setCuiDescription(attribute.getCuiDescription());
      candidateAttributes[candidateAttributeCount] = candidateAttribute;
      candidateAttributeCount = candidateAttributeCount + 1;
    }
  } */
  
  //show the popup 
  var popupOutput = window.showModalDialog("<html:rewrite page='/iltds/derivation/copyAttributes.do'/>",popupInput,'status:yes;resizable:yes;help:no;dialogWidth:750px;dialogHeight:725px');
  
  //if there was information returned from the popup, copy the attributes as specified
  if (popupOutput != null) {
    var copyToChoice = popupOutput.selectedCopyTo;
    var copyToSize = popupOutput.selectedCopyToSize;
    var copiedAttributes = popupOutput.selectedAttributes;
    //determine what child samples to modify
    var startingIndex;
    var endingIndex;
   
    var maxEndingIndex = derivationBatch.getDerivation(derivationIndex).getNumberOfDerivatives();
   
    if (COPY_TO_CHOICE_ALL == copyToChoice) {
      startingIndex = 0;
      endingIndex = maxEndingIndex;
    }
    else if (COPY_TO_CHOICE_ALL_AFTER_SOURCE == copyToChoice) {
      startingIndex = derivativeIndex + 1;
      endingIndex = maxEndingIndex;
    }
    else if (COPY_TO_CHOICE_NEXT_N == copyToChoice) {
      startingIndex = derivativeIndex + 1;
      endingIndex = startingIndex + parseInt(copyToSize);
      if (endingIndex > maxEndingIndex) {
        endingIndex = maxEndingIndex;
      }
    }
    
   

    //now copy the attributes to the other child samples
    var n, cui;
    var attributeDiv
    var sourcePrefix = "dto.derivation[" + derivationIndex + "].child[" + derivativeIndex + "].";
    var sourceName;
    var sourceForm;
    var sourceMeta;
    var sourceElement;
    var sourceValue;
    var targetPrefix;
    var targetName;
    var targetForm;
    var targetElement;
    
       
 
 for (var j=0; j<copiedAttributes.length; j++) {
        
     for (var i=startingIndex; i<endingIndex; i++) {
      //don't update the source row
      if (i != derivativeIndex) {
        targetPrefix = "dto.derivation[" + derivationIndex + "].child[" + i + "].";
        
        
        if(ATTRIBUTE_LABEL_PRINT == copiedAttributes[j]){
          sourceName = sourcePrefix + "printLabelCheckBox";
        
          targetName = targetPrefix + "printLabelCheckBox";
          
          sourceValue = $(sourceName).checked;
          $(targetName).checked = sourceValue;
          enablePrintLabelControls(derivationIndex, i);
          updatePrintLabelCheckBox(derivationIndex, i);
          continue;
        }
        
        if(ATTRIBUTE_LABEL_NUMBER == copiedAttributes[j]){
          sourceName = sourcePrefix + "labelCount";
        
          targetName = targetPrefix + "labelCount";
        }
        
        
         
         if (ATTRIBUTE_LABEL_TEMPLATE == copiedAttributes[j]) {
          sourceName = sourcePrefix + "templateDefinitionName";
        
          targetName = targetPrefix + "templateDefinitionName";
          
         }
         
         if (ATTRIBUTE_LABEL_PRINTER == copiedAttributes[j]) {
          sourceName = sourcePrefix + "printerName";
        
          targetName = targetPrefix + "printerName";
         }
         
           sourceValue = $(sourceName).value;
     
          //if the letter template already match leave the value as is
          if ($(targetName).value != sourceValue) {
          
            $(targetName).value = sourceValue;
  		  
  		   if (ATTRIBUTE_LABEL_TEMPLATE == copiedAttributes[j])
  		    updatePrinterChoices(derivationIndex, i);
  		          	 
          }
               
       
     
      }
    }
    
   } 
    
  }
}



</script>
</head>

<body class="bigr" onLoad="initPage();">

<html:form action="/iltds/derivation/derivationBatchPrintLabels.do"  onsubmit="return(validate());">
<%
  // DIV for errors
%>
  <div id="errorDiv" align="center">
    <table width="100%" border="1" cellspacing="1" cellpadding="1" class="foreground-small">
      <logic:present name="org.apache.struts.action.ERROR">
      <tr class="yellow"> 
	      <td><font color="#FF0000"><b><html:errors/></b></font></td>
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

  <logic:iterate name="batchDto" property="derivations" indexId="derivationCount" id="derivation" type="com.ardais.bigr.javabeans.DerivationDto">
      <script language="JavaScript">
                      derivation = new Derivation();
                      derivation.setIndex(<%=derivationCount%>);
                      derivation.setParentGroupTdId('<%="parentGroup" + derivationCount%>');  
                      derivation.setDerivativeGroupTdId('<%="derivativeGroup" + derivationCount%>');  
                      
     </script>
	<table border="0" class="foreground" cellpadding="3" cellspacing="1">
			<% int derivationCountInt = derivationCount.intValue(); %>
			<html:hidden name="derivationBatchForm" property="removeEmptyDerivations" value="false"/>
			<html:hidden name="derivationBatchForm" property='<%="dto.derivation[" + derivationCountInt + "].derivationId"%>'/>
 	   	<tr class="white">
			  <td><%=String.valueOf(derivationCountInt+1)%>.</td>
			  <td>
          Operation: <bean:write name="derivation" property="operation"/>
          <%=ApiFunctions.isEmpty(derivation.getOtherOperation()) ? "" : "(" + derivation.getOtherOperation() + ")"%>
          &nbsp;&nbsp;-&nbsp;&nbsp;
      	  Operation Date: <%=ApiFunctions.isEmpty(derivation.getOperationDateAsString()) ? "(not specified)" : derivation.getOperationDateAsString()%>
          &nbsp;&nbsp;-&nbsp;&nbsp;
      	  Prepared by: <%=ApiFunctions.isEmpty(derivation.getPreparedByName()) ? "(not specified)" : derivation.getPreparedByName()%>
			    <html:hidden name="derivationBatchForm" property='<%="dto.derivation[" + derivationCountInt + "].operationCui"%>'/>
			    <html:hidden name="derivationBatchForm" property='<%="dto.derivation[" + derivationCountInt + "].otherOperation"%>'/>
			    <html:hidden name="derivationBatchForm" property='<%="dto.derivation[" + derivationCountInt + "].operationDateAsString"%>'/>
			    <html:hidden name="derivationBatchForm" property='<%="dto.derivation[" + derivationCountInt + "].preparedBy"%>'/>
			  </td>
 	  	</tr>
 	   	<tr class="white">
			  <td>&nbsp;</td>
			  <td>
		    	<%=(derivation.getParents().size() > 1) ? "Parent Barcodes:" : "Parent Barcode:"%>
      		<logic:iterate name="derivation" property="parents" indexId="parentCount" id="parent">
		    		<%=(parentCount.intValue() > 0) ? ", " : ""%>
        		<% 
				      		SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);
				        	String sampleId = derivation.getParent(parentCount.intValue()).getSampleId();
				        	String linkText = IltdsUtils.getSampleIdAndAlias(derivation.getParent(parentCount.intValue()));
									String link = IcpUtils.preparePopupLink(sampleId, linkText, securityInfo);
						%>
				    <%= link %>
			    <html:hidden name="derivationBatchForm" property='<%="dto.derivation[" + derivationCountInt + "].parent[" + parentCount + "].sampleId"%>'/>
					</logic:iterate>
			  </td>
 	  	</tr>
 	  	<tr class="white">
			  <td>&nbsp;</td>
		 		<td>
		 		  Derivatives:
				</td>
			</tr>
	</table>
			<div style="margin: 5px 0px 12px 15px;">
   		  <table class="graphTable" cellpadding="0" cellspacing="0">
			    <tr class="white" >
			      <td>&nbsp;</td>
			      <td nowrap class="sampleAttribute">Barcode</td>
			      <td nowrap class="sampleAttribute">Alias</td>
			      <td nowrap class="sampleAttribute">Sample Type</td>
			      <td nowrap class="sampleAttribute">Print Label</td>
			      <td nowrap class="sampleAttribute">Number of Labels</td>
			      <td nowrap class="sampleAttribute">Label Template</td>
			      <td nowrap class="sampleAttribute">Label Printer</td>
			    </tr>
				  <logic:iterate name="derivation" property="children" indexId="derivativeCount" id="derivative" type="com.ardais.bigr.javabeans.SampleData">
               
                
                  <script language="JavaScript">
						  derivation.incrementNumberOfDerivatives();
                          var copyButtonKey = '<%=derivationCount%>' + '_' + '<%=derivativeCount%>';
                          copyButtonMap.put(copyButtonKey, copyButtonCount);
                          copyButtonCount = copyButtonCount + 1;
				   </script>
                  
				
			      <tr>
			          <td>
			                 
							  <html:button property="btnCopyAttributes" disabled="false" styleClass="libraryButtons" 
							     onclick='<%="copyAttributes(" + derivationCount + "," + derivativeCount + ");"%>'>
								  Copy...
							  </html:button>
				      </td>
				      <td style="text-align:center;">
        		<% 
				      		SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);
				        	String sampleId = derivation.getChild(derivativeCount.intValue()).getSampleId();
									String link = IcpUtils.preparePopupLink(sampleId, securityInfo);
						%>
				    <%= link %>
				        <html:hidden name="derivationBatchForm" property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "].sampleId"%>'/>
				      </td>				      
				      <td> 
				        <bean:write name="derivative" property="sampleAlias"/>
				        <html:hidden name="derivationBatchForm" property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "].sampleAlias"%>'/>
				      </td>				      
              <td> 
				        <bean:write name="derivative" property="sampleType"/>
				        <html:hidden name="derivationBatchForm" property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "].sampleTypeCui"%>'/>
              </td>
			        <td>
<%
	boolean isChecked = derivation.getChild(derivativeCount.intValue()).isPrintLabel();
	String checked;
	if (isChecked) {
	  checked = "checked";
	}
	else {
	  checked = "";
	}
%>
				        <input type="checkbox" name='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "].printLabelCheckBox"%>' 
				      		onclick='<%="updatePrintLabelCheckBox(" + derivationCount + "," + derivativeCount + ");enablePrintLabelControls(" + derivationCount + "," + derivativeCount + ");"%>' <%=checked%>/>
				        <html:hidden name="derivationBatchForm" property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "].printLabel"%>'/>
			        </td>
              <td> 
				        <html:text name="derivationBatchForm" property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "].labelCount"%>'
				        size="2" maxlength="2"/>
              </td>
              <td> 
							<bigr:selectList name="derivationBatchForm" property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "].templateDefinitionName"%>'
			  				firstValue="" firstDisplayValue="Select"
			  				legalValueSet="<%=(LegalValueSet)sampleTypeToTemplates.get(derivative.getSampleTypeCui())%>"
			  				onchange='<%="updatePrinterChoices(" + derivationCount + "," + derivativeCount + ");"%>'/>
              </td>
              <td> 
<%
	LegalValueSet printers;
	String templateName = derivation.getChild(derivativeCount.intValue()).getTemplateDefinitionName();
	if (!ApiFunctions.isEmpty(templateName)) {
	  printers = (LegalValueSet)templateToPrinters.get(templateName);
	}
	else {
	  printers = new LegalValueSet();
	}
%>
							<bigr:selectList name="derivationBatchForm" property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "].printerName"%>'
			  				firstValue="" firstDisplayValue="Select"
			  				legalValueSet="<%=printers%>"/>
              </td>
            </tr>
          
          </logic:iterate>
        </table>
			</div>
 	<hr style="border: 2px solid black;">
 	  <script language="JavaScript">
		     derivationBatch.setDerivation(derivation); 
		     
	  </script>		
 	
  </logic:iterate> <% // all derivations in the batch %>
	<table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
	    <tr class="white"> 
	      <td align="center">
	          <html:submit property="btnSubmit" value="Submit"/>&nbsp;&nbsp;
            <input type="button" name="btnCancel" value="Cancel" onclick="window.close();">
	      </td>
	    </tr>
	</table>
</html:form>
</body>
</html>
