<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.api.Escaper" %>
<%@ page import="com.ardais.bigr.iltds.assistants.LegalValueSet" %>
<%@ page import="com.ardais.bigr.iltds.assistants.LogicalRepository" %>
<%@ page import="com.ardais.bigr.iltds.bizlogic.DerivationOperationFactory" %>
<%@ page import="com.ardais.bigr.iltds.bizlogic.DerivationOperation" %>
<%@ page import="com.ardais.bigr.iltds.helpers.FormLogic" %>
<%@ page import="com.ardais.bigr.javabeans.DerivationDto" %>
<%@ page import="com.ardais.bigr.javabeans.SampleData" %>
<%@ page import="com.ardais.bigr.kc.form.def.TagTypes" %>
<%@ page import="com.ardais.bigr.security.SecurityInfo" %>
<%@ page import="com.ardais.bigr.util.ArtsConstants" %>
<%@ page import="com.ardais.bigr.util.Constants" %>
<%@ page import="com.ardais.bigr.util.IcpUtils" %> 
<%@ page import="com.ardais.bigr.util.WebUtils"%>
<%@ page import="com.gulfstreambio.kc.form.DataElement" %>
<%@ page import="com.gulfstreambio.kc.form.ElementValue" %>
<%@ page import="com.gulfstreambio.kc.form.FormInstance" %>
<%@ page import="com.gulfstreambio.kc.form.def.Tag" %>
<%@ page import="com.gulfstreambio.kc.form.def.data.DataFormDefinition" %>
<%@ page import="com.gulfstreambio.kc.form.def.data.DataFormDefinitionElement" %>
<%@ page import="com.gulfstreambio.kc.form.def.data.DataFormDefinitionElements" %>
<%@ page import="com.gulfstreambio.kc.form.def.data.DataFormDefinitionDataElement" %>
<%@ page import="com.gulfstreambio.kc.form.def.data.DataFormDefinitionHostElement" %>
<%@ page import="com.gulfstreambio.kc.web.support.DataFormDataElementContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContextStack" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>

<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, getServletContext(), "P");
%>
<bean:define id="myForm" name="derivationBatchForm" type="com.ardais.bigr.iltds.web.form.DerivationBatchForm"/>

<html>
<head>
<title>Operation Samples</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<jsp:include page="/hiddenJsps/kc/data/misc/scriptAndCss.jsp" flush="true"/>
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript" src="<html:rewrite page="/js/calendar.js"/>"></script>
<script language="JavaScript" src="<html:rewrite page="/js/derivation.js"/>"></script>
<script language="JavaScript" src="<html:rewrite page="/js/linkedlist.js"/>"></script>
<script language="JavaScript" src="<html:rewrite page="/js/map.js"/>"></script>
<script language="JavaScript" src="<html:rewrite page="/js/sample.js"/>"></script>
<script language="JavaScript" src="<html:rewrite page="/js/BigrLib.js"/>"></script>
<script language="JavaScript" src="<html:rewrite page="/js/prototype.js"/>"></script>
<script language="JavaScript">

var ATTRIBUTE_SAMPLE_TYPE = "SAMPLE_TYPE";
var ATTRIBUTE_SAMPLE_ALIAS = "SAMPLE_ALIAS";

var currentOperationDate = "";
var currentPreparedBy = "";

var allPotentialChildAttributes = new Object();
var potentialChildAttributes;
var allChildAttributes = new Object();
var dtoChildAttributes;
var childAttributes;
var jsAttribute;

<%
//determine if child ids are generated
boolean isGenerated = myForm.getDto().isGenerateChildIds();

int derivationIndex = 0;
Iterator derivationIterator = myForm.getDto().getDerivations().iterator();
while (derivationIterator.hasNext()) {
  DerivationDto derivation = (DerivationDto) derivationIterator.next();
%>
potentialChildAttributes = new Attributes();
allPotentialChildAttributes['<%=derivationIndex%>'] = potentialChildAttributes;
dtoChildAttributes = new Object();
allChildAttributes['<%=derivationIndex%>'] = dtoChildAttributes;
<%
  //convert the universe of data elements used across the child sample types into 
  //a collection of attributes
  DataFormDefinitionElement[] formElements = myForm.getPotentialDataFormDefinitionElementsForDerivation(derivationIndex);
  for (int elementCount=0; elementCount < formElements.length; elementCount ++) { 
    DataFormDefinitionElement formElement = formElements[elementCount];
    if (formElement.isHostElement()) {
      DataFormDefinitionHostElement hostElement = formElement.getDataHostElement();
%>
jsAttribute = new Attribute();
jsAttribute.setCui("<%=hostElement.getHostId()%>");
jsAttribute.setCuiDescription("<%=hostElement.getDisplayName()%>");
potentialChildAttributes.addAttribute(jsAttribute);

<%
    }
    else {
      DataFormDefinitionDataElement dataElement = formElement.getDataDataElement();
%>
jsAttribute = new Attribute();
jsAttribute.setCui("<%=dataElement.getCui()%>");
jsAttribute.setCuiDescription("<%=dataElement.getDisplayName()%>");
potentialChildAttributes.addAttribute(jsAttribute);

<%
    }
  }
  //now convert the data elements used for each child sample type into 
  //a collection of attributes
  Iterator childTypeIterator = ((Map)myForm.getChildSampleRegistrationForms().get(derivationIndex)).keySet().iterator();
	while (childTypeIterator.hasNext()) {
    String childTypeCui = (String)childTypeIterator.next();
%>
childAttributes = new Attributes();
dtoChildAttributes['<%=childTypeCui%>'] = childAttributes;

<%
    formElements = ((DataFormDefinition)((Map)myForm.getChildSampleRegistrationForms().get(derivationIndex)).get(childTypeCui)).getDataFormElements().getDataFormElements();
		for (int elementCount=0; elementCount < formElements.length; elementCount ++) { 
		  DataFormDefinitionElement formElement = formElements[elementCount];
		  boolean inherits = false;
		  boolean defaults = false;
		  if (formElement.isHostElement()) {
		    DataFormDefinitionHostElement hostElement = formElement.getDataHostElement();
		    Tag[] tags = hostElement.getTags();
		    for (int tagCount=0; tagCount<tags.length; tagCount++) {
		      Tag tag = tags[tagCount];
		      if (TagTypes.DERIV_DEFAULTS.equalsIgnoreCase(tag.getType())) {
		        defaults = new Boolean(tag.getValue()).booleanValue();
		      }
		      else if (TagTypes.DERIV_INHERITS.equalsIgnoreCase(tag.getType())) {
		        inherits = new Boolean(tag.getValue()).booleanValue();
		      }
		    }
%>
jsAttribute = new Attribute();
jsAttribute.setCui("<%=hostElement.getHostId()%>");
jsAttribute.setCuiDescription("<%=hostElement.getDisplayName()%>");
jsAttribute.setRequired(<%=hostElement.isRequired()%>);
jsAttribute.setDerivativeInherits(<%=inherits%>);
jsAttribute.setDerivativeDefaults(<%=defaults%>);
childAttributes.addAttribute(jsAttribute);

<%
      }
      else {
        DataFormDefinitionDataElement dataElement = formElement.getDataDataElement();
		    Tag[] tags = dataElement.getTags();
		    for (int tagCount=0; tagCount<tags.length; tagCount++) {
		      Tag tag = tags[tagCount];
		      if (TagTypes.DERIV_DEFAULTS.equalsIgnoreCase(tag.getType())) {
		        defaults = new Boolean(tag.getValue()).booleanValue();
		      }
		      else if (TagTypes.DERIV_INHERITS.equalsIgnoreCase(tag.getType())) {
		        inherits = new Boolean(tag.getValue()).booleanValue();
		      }
		    }
%>
jsAttribute = new Attribute();
jsAttribute.setCui("<%=dataElement.getCui()%>");
jsAttribute.setCuiDescription("<%=dataElement.getDisplayName()%>");
jsAttribute.setRequired(<%=dataElement.isRequired()%>);
jsAttribute.setDerivativeInherits(<%=inherits%>);
jsAttribute.setDerivativeDefaults(<%=defaults%>);
childAttributes.addAttribute(jsAttribute);

<%
      }
    }
  }
  derivationIndex = derivationIndex + 1;
}
%>

//map to keep track of the visibility of an attribute within a derivation
var attributeVisibilityCountMap = new Map();

var derivationBatch = new DerivationBatch(); 
derivationBatch.setNumberOfDerivations(0); 
derivationBatch.setTableId("derivationBatchTable");
  
var derivation;
  
var copyButtonMap = new Map();
var copyButtonCount = 0;

function initPage() {
  if (parent.topFrame) {
    parent.topFrame.document.all.banner.innerHTML = "Derivative Operations";
  }
  showAllSampleAttributes();
}

function startOver() {
  setActionButtonEnabling(false);
  if (!handleStartOverRequest()) {
    setActionButtonEnabling(true);
  }
}

function goBack() {
  setActionButtonEnabling(false);
  if (confirm("All new information entered on this page will be lost. Press Ok to go back to the previous page, or Cancel to stay on this page.")) {
  	if (validate()) {
		  var f = document.forms[0];
		  f["dto.operationDateAsString"].value = currentOperationDate;
		  for (var i = 0; i < f["dto.preparedBy"].length; i++) {
			  if (f["dto.preparedBy"].options[i].value == currentPreparedBy) {
			  	f["dto.preparedBy"].options[i].selected = true;
			  }
		  }
		  f.action = '<html:rewrite page="/iltds/derivation/derivationBatchGoBack.do"/>'
		  f.submit();
  	}
  	else {
	    setActionButtonEnabling(true);
  	}
  }
  else {
    setActionButtonEnabling(true);
  }
}

function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.forms[0];
  f.btnStartOver.disabled = isDisabled;
  f.btnBack.disabled = isDisabled;
  f.btnFinish.disabled = isDisabled;
  if (isControlCollection('btnCopyAttributes')) {
    disableControlCollection('btnCopyAttributes', isDisabled);
  }
  else {
    f.btnCopyAttributes.disabled = isDisabled;
  }
}

function validate() {
  setActionButtonEnabling(false);
  for (var i = 0; i < derivationBatch.getNumberOfDerivations(); i++) {
    var d = derivationBatch.getDerivation(i);
    for (var j = 0; j < d.getNumberOfDerivatives(); j++) {
      var formId = getFormId(i, j);
      var formInstance = GSBIO.kc.data.FormInstances.getFormInstance(formId);
      if (formInstance) {
        var hiddenInput = getKcFormHiddenInputName(i, j);
        $(hiddenInput).value = formInstance.serialize();
      }
    }
  }
  return true;
}

function getFormId(derivationIndex, derivativeIndex) {
  return 'f' + derivationIndex + derivativeIndex;
}

function getKcFormHiddenInputName(derivationIndex, derivativeIndex) {
  return 'dto.derivation[' + derivationIndex + '].child[' + derivativeIndex + '].kcForm';
}

function adjustAmtRemaining(derivationIndex, parentIndex) {
  var control = event.srcElement;
  //if this call was triggered by a "consumed" checkbox being toggled,
  //update the corresponding "volume" textbox as appropriate
  
  if ('checkbox' == control.type) {
    var controlName = "dto.derivation[" + derivationIndex + "].parent[" + parentIndex + "].volumeAsString";
    if (document.forms[0][controlName] != null) {
	    if (control.checked) {
	      document.forms[0][controlName].value = "";
	    }
	    else {
	      document.forms[0][controlName].focus();
	    }
    }
  
//  if this call was triggered by a "consumed" checkbox being toggled,
    //update the corresponding "weight" textbox as appropriate
 
      var controlName = "dto.derivation[" + derivationIndex + "].parent[" + parentIndex + "].weightAsString";
      if (document.forms[0][controlName] != null) {
  	    if (control.checked) {
  	      document.forms[0][controlName].value = "";
  	    }
  	    else {
  	      document.forms[0][controlName].focus();
  	    }
      }
  }
  
  //otherwise, this call was triggered by a "volume or weight" textbox being altered so
  //update the corresponding "consumed" checkbox as appropriate
  else {
    var controlName = "dto.derivation[" + derivationIndex + "].parent[" + parentIndex + "].consumed";
    //if the only characters in the volume control are 0's or 0's with one
    //decimal place (in an appropriate order) then check the check box.
    //Otherwise uncheck it
    var expr;
    var firstDec = control.value.indexOf(".");
    var lastDec = control.value.lastIndexOf(".");
    //if there are multiple decimal places then the value is invalid so uncheck
    //the checkbox
    if (firstDec != lastDec) {
      document.forms[0][controlName].checked = false;
    }
    else {    
      if (firstDec >= 0) {
	    expr = new RegExp("0+\.0*");
	  }
	  else {
	    expr = new RegExp("0+");
	  }
	  //alert(control.value + " " + expr.test(control.value) + " " + isValidChars('[0.]', control.value));
      document.forms[0][controlName].checked = expr.test(control.value) && isValidChars('[0.]', control.value);
    }
  }
}

function onSampleTypeChange(sampleTypeCui, derivationIndex, derivativeIndex) {  
  //enable or disable the copy button for this row as appropriate
  var isDisabled = isEmpty(sampleTypeCui);
  if (isControlCollection('btnCopyAttributes')) {
    var copyButtonKey = derivationIndex + '_' + derivativeIndex;
    var copyButtonIndex = copyButtonMap.get(copyButtonKey).getItem();
    disableControlInCollection('btnCopyAttributes', copyButtonIndex, isDisabled);
  }
  else {
    document.forms[0].btnCopyAttributes.disabled = isDisabled;
  }
  
  //walk all potential attributes for this derivation, hiding/showing each as appropriate
  var allAttributes = allPotentialChildAttributes[derivationIndex];
  var childAttributes;
  if (isEmpty(sampleTypeCui)) {
    childAttributes = new Attributes();
  }
  else {
    childAttributes = allChildAttributes[derivationIndex][sampleTypeCui];
  }
  for (var attribute = allAttributes.getFirstAttribute(); attribute != null; attribute = allAttributes.getNextAttribute()) {
    var sampleAttribute = childAttributes.getAttribute(attribute.getCui());
    if (sampleAttribute != null && attributeShouldBeShown(derivationIndex, sampleAttribute)) {
      showColumn(sampleAttribute, derivationIndex); 
      showAttribute(sampleAttribute, derivationIndex, derivativeIndex );
    }
    else {
      var attributeKey = attribute.getCui() + derivationIndex;
      var startingAttributeVisibilityCount = attributeVisibilityCountMap.get(attributeKey);
      var columnWasShowing = (startingAttributeVisibilityCount != null && startingAttributeVisibilityCount.getItem() > 0);
      hideAttribute(attribute, derivationIndex, derivativeIndex);
      var endingAttributeVisibilityCount = attributeVisibilityCountMap.get(attributeKey);
      var columnShouldBeHidden = (endingAttributeVisibilityCount == null || endingAttributeVisibilityCount.getItem() < 1);
  	  if (columnShouldBeHidden && columnWasShowing) {
  	    hideColumn(attribute, derivationIndex); 
  	  }
    }
  }
}

function attributeShouldBeShown(derivationIndex, attribute) {
  var showAttribute = true;
  var derivation = derivationBatch.getDerivation(derivationIndex);
  var repSample = derivation.getRepresentativeParentSample();
  var isInherits = attribute.getDerivativeInherits();
  var attributeCui = attribute.getCui();
  //never show prepared by attribute, as this information is collected at the derivation
  //level (not the child sample level)
  if ('<%= ArtsConstants.ATTRIBUTE_PREPARED_BY %>' == attributeCui) {
    showAttribute = false;
  }
  //date of collection (show only if it is required and no common parent value)
  else if ('<%= ArtsConstants.ATTRIBUTE_DATE_OF_COLLECTION %>' == attributeCui) {
    if (!attribute.getRequired()) {
      showAttribute = false;
    }
    else {
      showAttribute = (repSample.getCollectionDateTime() == null);
    }
  }
  //date of preservation (show only if it is required and no common parent value)
  else if ('<%= ArtsConstants.ATTRIBUTE_DATE_OF_PRESERVATION %>' == attributeCui) {
    if (!attribute.getRequired()) {
      showAttribute = false;
    }
    else {
      showAttribute = (repSample.getPreservationDateTime() == null);
    }
  }
  //for all other attributes, if the attribute is inherited it will be shown only if
  //there is no common parent value.
  else {
    if (isInherits) {
      //buffer type
      if ('<%= ArtsConstants.ATTRIBUTE_BUFFER_TYPE %>' == attributeCui) {
        showAttribute = (repSample.getBufferTypeCui() == null);
      }
      //cells per ml
      else if ('<%= ArtsConstants.ATTRIBUTE_CELLS_PER_ML %>' == attributeCui) {
        showAttribute = (repSample.getCellsPerMl() == null);
      }
      //comment
      else if ('<%= ArtsConstants.ATTRIBUTE_COMMENT %>' == attributeCui) {
        showAttribute = (repSample.getComment() == null);
      }
      //concentration
      else if ('<%= ArtsConstants.ATTRIBUTE_CONCENTRATION %>' == attributeCui) {
        showAttribute = (repSample.getConcentration() == null);
      }
      //fixative type
      else if ('<%= ArtsConstants.ATTRIBUTE_FIXATIVE_TYPE %>' == attributeCui) {
        showAttribute = (repSample.getFixativeType() == null);
      }
      //format detail
      else if ('<%= ArtsConstants.ATTRIBUTE_FORMAT_DETAIL %>' == attributeCui) {
        showAttribute = (repSample.getFormatDetail() == null);
      }
      //gross appearance
      else if ('<%= ArtsConstants.ATTRIBUTE_GROSS_APPEARANCE %>' == attributeCui) {
        showAttribute = (repSample.getGrossAppearance() == null);
      }
      //percent viability
      else if ('<%= ArtsConstants.ATTRIBUTE_PERCENT_VIABILITY %>' == attributeCui) {
        showAttribute = (repSample.getPercentViability() == null);
      }
      //procedure
      else if ('<%= ArtsConstants.ATTRIBUTE_PROCEDURE %>' == attributeCui) {
        showAttribute = (repSample.getProcedure() == null);
      }
      //tissue
      else if ('<%= ArtsConstants.ATTRIBUTE_TISSUE %>' == attributeCui) {
        showAttribute = (repSample.getTissue() == null);
      }
      //total number of cells
      else if ('<%= ArtsConstants.ATTRIBUTE_TOTAL_NUMBER_OF_CELLS %>' == attributeCui) {
        showAttribute = (repSample.getTotalNumOfCells() == null);
      }
      //volume
      else if ('<%= ArtsConstants.ATTRIBUTE_VOLUME %>' == attributeCui) {
        showAttribute = (repSample.getVolume() == null);
      }
      //weight
      else if ('<%= ArtsConstants.ATTRIBUTE_WEIGHT %>' == attributeCui) {
        showAttribute = (repSample.getWeight() == null);
      }
      //yield
      else if ('<%= ArtsConstants.ATTRIBUTE_YIELD %>' == attributeCui) {
        showAttribute = (repSample.getYield() == null);
      }
      //source
      else if ('<%= ArtsConstants.ATTRIBUTE_SOURCE %>' == attributeCui) {
        showAttribute = (repSample.getSource() == null);
      }
      else {
        //KC attribute
        showAttribute = (repSample.getDataElements().getDataElement(attributeCui) == null);
      }
    }
  }
  return showAttribute;
}

function showAttribute(attribute, derivationIndex, derivativeIndex) {
  var attributeCui = attribute.getCui();
  var attributeDiv = attributeCui  + "Div" + derivationIndex + derivativeIndex;
  //if the attribute is already shown, just set the default value. 
  if ($(attributeDiv).style.display == "inline") {
    populateDefaultValue(attribute, derivationIndex, derivativeIndex);
  }
  //if the attribute isn't already shown, make it so. 
  else {
    $(attributeDiv).style.display = "inline";
    populateDefaultValue(attribute, derivationIndex, derivativeIndex);
    //increment the count of visible occurances of this attribute in this derivation
    var attributeKey = attributeCui + derivationIndex;
    var attributeCount = attributeVisibilityCountMap.get(attributeKey);
    var count = null;
    if (attributeCount == null) {
      count = 1;
    }
    else {
      count = attributeCount.getItem() + 1;
    }
    attributeVisibilityCountMap.put(attributeKey, count);
  }
}

function populateDefaultValue(attribute, derivationIndex, derivativeIndex) {
  var derivation = derivationBatch.getDerivation(derivationIndex);
  var repSample = derivation.getRepresentativeParentSample();
  var defaultValue;
  var inputName = "dto.derivation[" + derivationIndex + "].child[" + derivativeIndex + "].";
  var otherName = "";
  var isDefaults = attribute.getDerivativeDefaults();
  
  //if the attribute defaults to the common parent value (if any), set that value now
  if (isDefaults) {
    var attributeCui = attribute.getCui();
    
    //buffer type
    if ('<%= ArtsConstants.ATTRIBUTE_BUFFER_TYPE %>' == attributeCui) {
      defaultValue = repSample.getBufferTypeCui();
      if (defaultValue != null) {
        otherName = inputName + "bufferTypeOther";
        inputName = inputName + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_BUFFER_TYPE) %>';
        if (isEmpty($(inputName).value)) {
          $(inputName).value = defaultValue;
          defaultValue = repSample.getBufferTypeOther();
          if (defaultValue != null) {
            $(otherName).value = defaultValue;
          }
          //to ensure the drop-down is synchronized with the "other" text box, call the
          //onchange event.  Without this the "other" text box remains disabled, even
          //when it should be enabled.
          $(inputName).onchange();
        }
      }
    }
    
    //cells per ml
    else if ('<%= ArtsConstants.ATTRIBUTE_CELLS_PER_ML %>' == attributeCui) {
      defaultValue = repSample.getCellsPerMl();
      if (defaultValue != null) {
        inputName = inputName + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_CELLS_PER_ML) %>';
        if (isEmpty($(inputName).value)) {
          $(inputName).value = defaultValue;
        }
      }
    }
    
    //comment
    else if ('<%= ArtsConstants.ATTRIBUTE_COMMENT %>' == attributeCui) {
      defaultValue = repSample.getComment();
      if (defaultValue != null) {
        inputName = inputName + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_COMMENT) %>';
        if (isEmpty($(inputName).value)) {
          $(inputName).value = defaultValue;
        }
      }
    }
    
    //concentration
    else if ('<%= ArtsConstants.ATTRIBUTE_CONCENTRATION %>' == attributeCui) {
      defaultValue = repSample.getConcentration();
      if (defaultValue != null) {
        inputName = inputName + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_CONCENTRATION) %>';
        if (isEmpty($(inputName).value)) {
          $(inputName).value = defaultValue;
        }
      }
    }
    
    //don't do anything with date of collection or date of preservation, as those attributes
    //have special rules (i.e. they will never be defaulted - they will always be inherited 
    //if there is a common parent value and if not will only be shown if they are required).
    
    //fixative type
    else if ('<%= ArtsConstants.ATTRIBUTE_FIXATIVE_TYPE %>' == attributeCui) {
      defaultValue = repSample.getFixativeType();
      if (defaultValue != null) {
        inputName = inputName + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_FIXATIVE_TYPE) %>';
        if (isEmpty($(inputName).value)) {
          $(inputName).value = defaultValue;
        }
      }
    }
    
    //format detail
    else if ('<%= ArtsConstants.ATTRIBUTE_FORMAT_DETAIL %>' == attributeCui) {
      defaultValue = repSample.getFormatDetail();
      if (defaultValue != null) {
        inputName = inputName + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_FORMAT_DETAIL) %>';
        if (isEmpty($(inputName).value)) {
          $(inputName).value = defaultValue;
        }
      }
    }
    
    //gross appearance
    else if ('<%= ArtsConstants.ATTRIBUTE_GROSS_APPEARANCE %>' == attributeCui) {
      defaultValue = repSample.getGrossAppearance();
      if (defaultValue != null) {
        inputName = inputName + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_GROSS_APPEARANCE) %>';
        if (isEmpty($(inputName).value)) {
          $(inputName).value = defaultValue;
        }
      }
    }
    
    //percent viability
    else if ('<%= ArtsConstants.ATTRIBUTE_PERCENT_VIABILITY %>' == attributeCui) {
      defaultValue = repSample.getPercentViability();
      if (defaultValue != null) {
        inputName = inputName + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_PERCENT_VIABILITY) %>';
        if (isEmpty($(inputName).value)) {
          $(inputName).value = defaultValue;
        }
      }
    }
    
    //don't do anything with prepared by, as that attribute has special rules (i.e. it will never 
    //be defaulted, and is captured at the operation level not the child sample level)

    //procedure
    else if ('<%= ArtsConstants.ATTRIBUTE_PROCEDURE %>' == attributeCui) {
      defaultValue = repSample.getProcedure();
      if (defaultValue != null) {
        otherName = inputName + "procedureOther";
        inputName = inputName + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_PROCEDURE) %>';
        if (isEmpty($(inputName).value)) {
          $(inputName).value = defaultValue;
          defaultValue = repSample.getProcedureOther();
          if (defaultValue != null) {
            $(otherName).value = defaultValue;
          }
          //to ensure the drop-down is synchronized with the "other" text box, call the
          //onchange event.  Without this the "other" text box remains disabled, even
          //when it should be enabled.
          $(inputName).onchange();
        }
      }
    }
    
    //tissue
    else if ('<%= ArtsConstants.ATTRIBUTE_TISSUE %>' == attributeCui) {
      defaultValue = repSample.getTissue();
      if (defaultValue != null) {
        otherName = inputName + "tissueOther";
        inputName = inputName + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_TISSUE) %>';
        if (isEmpty($(inputName).value)) {
          $(inputName).value = defaultValue;
          defaultValue = repSample.getTissueOther();
          if (defaultValue != null) {
            $(otherName).value = defaultValue;
          }
          //to ensure the drop-down is synchronized with the "other" text box, call the
          //onchange event.  Without this the "other" text box remains disabled, even
          //when it should be enabled.
          $(inputName).onchange();
        }
      }
    }
    
    //total number of cells
    else if ('<%= ArtsConstants.ATTRIBUTE_TOTAL_NUMBER_OF_CELLS %>' == attributeCui) {
      defaultValue = repSample.getTotalNumOfCells();
      if (defaultValue != null) {
        otherName = inputName + "totalNumOfCellsExRepCui";
        inputName = inputName + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_TOTAL_NUMBER_OF_CELLS) %>';
        if (isEmpty($(inputName).value)) {
          $(inputName).value = defaultValue;
          defaultValue = repSample.getTotalNumOfCellsUnit();
          if (defaultValue != null) {
            $(otherName).value = defaultValue;
          }
        }
      }
    }
    
    //volume
    else if ('<%= ArtsConstants.ATTRIBUTE_VOLUME %>' == attributeCui) {
      defaultValue = repSample.getVolume();
      if (defaultValue != null) {
        otherName = inputName + "volumeUnitCui";
        inputName = inputName + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_VOLUME) %>';
        if (isEmpty($(inputName).value)) {
          $(inputName).value = defaultValue;
          defaultValue = repSample.getVolumeUnitCui();
          if (defaultValue != null) {
            $(otherName).value = defaultValue;
          }
        }
      }
    }
    
   //weight
    else if ('<%= ArtsConstants.ATTRIBUTE_WEIGHT %>' == attributeCui) {
      defaultValue = repSample.getWeight();
      if (defaultValue != null) {
        otherName = inputName + "weightUnitCui";
        inputName = inputName + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_WEIGHT) %>';
        if (isEmpty($(inputName).value)) {
          $(inputName).value = defaultValue;
          defaultValue = repSample.getWeightUnitCui();
          if (defaultValue != null) {
            $(otherName).value = defaultValue;
          }
        }
      }
    }
	
    
    //yield
    else if ('<%= ArtsConstants.ATTRIBUTE_YIELD %>' == attributeCui) {
      defaultValue = repSample.getYield();
      if (defaultValue != null) {
        inputName = inputName + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_YIELD) %>';
        if (isEmpty($(inputName).value)) {
          $(inputName).value = defaultValue;
        }
      }
    }
    
    //source
    else if ('<%= ArtsConstants.ATTRIBUTE_SOURCE %>' == attributeCui) {
      defaultValue = repSample.getSource();
      if (defaultValue != null) {
        inputName = inputName + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_SOURCE) %>';
        if (isEmpty($(inputName).value)) {
          $(inputName).value = defaultValue;
        }
      }
    }
    
    //kc attribute
    else {
      defaultValue = repSample.getDataElements().getDataElement(attributeCui);
      if (defaultValue != null) {
        var id = getFormId(derivationIndex, derivativeIndex);
        var form = GSBIO.kc.data.FormInstances.getFormInstance(id);
        for (n = 0; n < form.elements.length; n++) {
          var e = form.elements[n];
          var m = e.getMeta();
          if (m.cui == attributeCui) {
            if (!e.getPrimaryValue()) {
              var v = defaultValue.getFirstDataElementValue().getValue();
              e.setPrimaryValue(v);
              if (m.isDatatypeCv && v == m.otherCui) {
                $(e.getHtmlIds().primary).fireEvent('onchange');
                v = defaultValue.getFirstDataElementValue().getValueOther();
                e.setOtherValue(v);
              }
            }
          }
        }
      }
    }
  }
}

function hideAttribute(attribute, derivationIndex, derivativeIndex) {
  var attributeCui = attribute.getCui();
  var attributeDiv = attributeCui  + "Div" + derivationIndex + derivativeIndex;
  //if the attribute is already hidden, no need to do anything
  if ($(attributeDiv).style.display != "none") {
    $(attributeDiv).style.display = "none";
    //decrement the count of visible occurances of this attribute in this derivation
    var attributeKey = attributeCui + derivationIndex;
    var attributeCount = attributeVisibilityCountMap.get(attributeKey);
    var count = null;
    if (attributeCount == null || attributeCount.getItem() <= 0) {
      count = 0;
    }
    else {
      count = attributeCount.getItem() - 1;
    }
    attributeVisibilityCountMap.put(attributeKey, count);
    //some attributes have more than one control, so handle all controls in the div
    var elements = $(attributeDiv).children;
    for (var i=0; i < elements.length; i++) {
      var control = elements[i];
      if ('radio' == control.type || 'checkbox' == control.type) {
        control.checked = false;
      }
      else if ('select-one' == control.type){
        control.options[0].selected = true;
      }
      else if ('text' == control.type){
        control.value = "";
      }
    }
  }
}

function hideColumn(attribute, derivationIndex) {
  setColumnVisibility(attribute, derivationIndex, "none");
}

function showColumn(attribute, derivationIndex) {
  setColumnVisibility(attribute, derivationIndex, "inline");
}

function setColumnVisibility(attribute, derivationIndex, style) {
  var attributeCui = attribute.getCui();
  var columnHeader = attributeCui + "Header" + derivationIndex;
  if ($(columnHeader).style.display != style) {
    $(columnHeader).style.display = style;
    var derivation = derivationBatch.getDerivation(derivationIndex);
    var totalDerivatives = derivation.getNumberOfDerivatives();
    for (var count=0; count < totalDerivatives; count++) {
      var attributeTdId = attributeCui + "Td" + derivationIndex + count;
      $(attributeTdId).style.display = style;
    }
  }
}

function clearDateTime(controlGroup) {
  $(controlGroup + 'date').value = '';
  $(controlGroup + 'hour').value = '';
  $(controlGroup + 'minute').value = '';
  var options = document.forms[0].elements[controlGroup + 'meridian'];
  for (i = 0; i < options.length; i++) {
    options[i].checked = false;
  }
}

function enableInventoryGroupDropdown(derivationIndex, inherit) {
  var controlName = "dto.derivation[" + derivationIndex + "].inventoryGroups";
  var isDisabled = ('<%= FormLogic.DB_YES %>' == inherit);
  if (isDisabled) {
    deselectAllFromList(controlName);
  }
  $(controlName).disabled = isDisabled;
}

function copyAttributes(derivationIndex, derivativeIndex) {
  var popupInput = new Object();
<%
	if (isGenerated) {
%>
  var sampleIdInputName = "dto.derivation[" + derivationIndex + "].child[" + derivativeIndex + "].generatedSampleId";
<%
	}
  else {
%>
  var sampleIdInputName = "dto.derivation[" + derivationIndex + "].child[" + derivativeIndex + "].sampleId";
<%
	}
%>
  popupInput.sampleId = $(sampleIdInputName).value;
  var candidateAttributes = new Array();
  popupInput.attributes = candidateAttributes;
  var candidateAttribute;
  var candidateAttributeCount = 0;
  
  //include the alias and sample type attributes as candidates (they are always options)
  candidateAttribute = new Attribute();
  candidateAttribute.setCui(ATTRIBUTE_SAMPLE_ALIAS);
  candidateAttribute.setCuiDescription('Alias');
  candidateAttributes[candidateAttributeCount] = candidateAttribute;
  candidateAttributeCount = candidateAttributeCount + 1;
  candidateAttribute = new Attribute();
  candidateAttribute.setCui(ATTRIBUTE_SAMPLE_TYPE);
  candidateAttribute.setCuiDescription('Sample Type');
  candidateAttributes[candidateAttributeCount] = candidateAttribute;
  candidateAttributeCount = candidateAttributeCount + 1;
  
  //include the visible attributes for the child sample chosen as a source as candidates
  var allAttributes = allPotentialChildAttributes[derivationIndex];
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
  }
  
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
    //determine if sample type is to be copied
    var isSampleTypeCopied = false;
    for (var i=0; i<copiedAttributes.length; i++) {
      if (ATTRIBUTE_SAMPLE_TYPE == copiedAttributes[i]) {
        isSampleTypeCopied = true;
        break;
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
    for (var i=startingIndex; i<endingIndex; i++) {
      //don't update the source row
      if (i != derivativeIndex) {
        targetPrefix = "dto.derivation[" + derivationIndex + "].child[" + i + "].";
        //handle sample type first, as that drives the visibility of all other attributes
        if (isSampleTypeCopied) {
          sourceName = sourcePrefix + "sampleTypeCui";
          sourceValue = $(sourceName).value;
          targetName = targetPrefix + "sampleTypeCui";
          //if the sample types already match leave the value as is
          if ($(targetName).value != sourceValue) {
            $(targetName).value = sourceValue;
  				  onSampleTypeChange($(targetName).value, derivationIndex, i);
          }
        }
        //now do the other attributes
        for (var j=0; j<copiedAttributes.length; j++) {
          cui = copiedAttributes[j];
          attributeDiv = cui  + "Div" + derivationIndex + i;
          //don't bother with sample type as it's already been handled
          if (ATTRIBUTE_SAMPLE_TYPE != cui) {
            //sample alias
            if (ATTRIBUTE_SAMPLE_ALIAS == cui) {
              sourceName = sourcePrefix + 'sampleAlias';
              sourceValue = $(sourceName).value;
              targetName = targetPrefix + 'sampleAlias';
              $(targetName).value = sourceValue;
            }
            //buffer type
            else if ('<%= ArtsConstants.ATTRIBUTE_BUFFER_TYPE %>' == cui) {
              if ($(attributeDiv).style.display == "inline") {
                sourceName = sourcePrefix + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_BUFFER_TYPE) %>';
                sourceValue = $(sourceName).value;
                targetName = targetPrefix + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_BUFFER_TYPE) %>';
                $(targetName).value = sourceValue;
        	      //to ensure the drop-down is synchronized with the "other" text box, call the
        	      //onchange event.  Without this the "other" text box remains disabled, even
        	      //when it should be enabled.
        	      $(targetName).onchange();
                sourceName = sourcePrefix + "bufferTypeOther";
                sourceValue = $(sourceName).value;
                targetName = targetPrefix + "bufferTypeOther";
                $(targetName).value = sourceValue;
              }
            }
            //cells per ml
            else if ('<%= ArtsConstants.ATTRIBUTE_CELLS_PER_ML %>' == cui) {
              if ($(attributeDiv).style.display == "inline") {
                sourceName = sourcePrefix + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_CELLS_PER_ML) %>';
                sourceValue = $(sourceName).value;
                targetName = targetPrefix + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_CELLS_PER_ML) %>';
                $(targetName).value = sourceValue;
              }
            }
            //comment
            else if ('<%= ArtsConstants.ATTRIBUTE_COMMENT %>' == cui) {
              if ($(attributeDiv).style.display == "inline") {
                sourceName = sourcePrefix + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_COMMENT) %>';
                sourceValue = $(sourceName).value;
                targetName = targetPrefix + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_COMMENT) %>';
                $(targetName).value = sourceValue;
              }
            }
            //concentration
            else if ('<%= ArtsConstants.ATTRIBUTE_CONCENTRATION %>' == cui) {
              if ($(attributeDiv).style.display == "inline") {
                sourceName = sourcePrefix + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_CONCENTRATION) %>';
                sourceValue = $(sourceName).value;
                targetName = targetPrefix + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_CONCENTRATION) %>';
                $(targetName).value = sourceValue;
              }
            }
            //date of collection
            else if ('<%= ArtsConstants.ATTRIBUTE_DATE_OF_COLLECTION %>' == cui) {
              if ($(attributeDiv).style.display == "inline") {
                sourceName = sourcePrefix + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_DATE_OF_COLLECTION) %>';
                sourceValue = $(sourceName).value;
                targetName = targetPrefix + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_DATE_OF_COLLECTION) %>';
                $(targetName).value = sourceValue;
                sourceName = sourcePrefix + "collectionDateTime.hour";
                sourceValue = $(sourceName).value;
                targetName = targetPrefix + "collectionDateTime.hour";
                $(targetName).value = sourceValue;
                sourceName = sourcePrefix + "collectionDateTime.minute";
                sourceValue = $(sourceName).value;
                targetName = targetPrefix + "collectionDateTime.minute";
                $(targetName).value = sourceValue;
        				sourceValue = "";
                sourceName = sourcePrefix + "collectionDateTime.meridian";
                var choices = document.getElementsByName(sourceName);
                for (var k = 0; k < choices.length; k++) {
                  if (choices[k].checked) {
                    sourceValue = choices[k].value;
                  }
                }
                targetName = targetPrefix + "collectionDateTime.meridian";
                choices = document.getElementsByName(targetName);
                for (var k = 0; k < choices.length; k++) {
                  if (sourceValue == choices[k].value) {
                    choices[k].checked = true;
                  }
                  else {
                    choices[k].checked = false;
                  }
                }
              }
            }
            //date of preservation
            else if ('<%= ArtsConstants.ATTRIBUTE_DATE_OF_PRESERVATION %>' == cui) {
              if ($(attributeDiv).style.display == "inline") {
                sourceName = sourcePrefix + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_DATE_OF_PRESERVATION) %>';
                sourceValue = $(sourceName).value;
                targetName = targetPrefix + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_DATE_OF_PRESERVATION) %>';
                $(targetName).value = sourceValue;
                sourceName = sourcePrefix + "preservationDateTime.hour";
                sourceValue = $(sourceName).value;
                targetName = targetPrefix + "preservationDateTime.hour";
                $(targetName).value = sourceValue;
                sourceName = sourcePrefix + "preservationDateTime.minute";
                sourceValue = $(sourceName).value;
                targetName = targetPrefix + "preservationDateTime.minute";
                $(targetName).value = sourceValue;
        				sourceValue = "";
                sourceName = sourcePrefix + "preservationDateTime.meridian";
                var choices = document.getElementsByName(sourceName);
                for (var k = 0; k < choices.length; k++) {
                  if (choices[k].checked) {
                    sourceValue = choices[k].value;
                  }
                }
                targetName = targetPrefix + "preservationDateTime.meridian";
                choices = document.getElementsByName(targetName);
                for (var k = 0; k < choices.length; k++) {
                  if (sourceValue == choices[k].value) {
                    choices[k].checked = true;
                  }
                  else {
                    choices[k].checked = false;
                  }
                }
              }
            }
            //fixative type
            else if ('<%= ArtsConstants.ATTRIBUTE_FIXATIVE_TYPE %>' == cui) {
              if ($(attributeDiv).style.display == "inline") {
                sourceName = sourcePrefix + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_FIXATIVE_TYPE) %>';
                sourceValue = $(sourceName).value;
                targetName = targetPrefix + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_FIXATIVE_TYPE) %>';
                $(targetName).value = sourceValue;
              }
            }
            //format detail
            else if ('<%= ArtsConstants.ATTRIBUTE_FORMAT_DETAIL %>' == cui) {
              if ($(attributeDiv).style.display == "inline") {
                sourceName = sourcePrefix + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_FORMAT_DETAIL) %>';
                sourceValue = $(sourceName).value;
                targetName = targetPrefix + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_FORMAT_DETAIL) %>';
                $(targetName).value = sourceValue;
              }
            }
            //gross appearance
            else if ('<%= ArtsConstants.ATTRIBUTE_GROSS_APPEARANCE %>' == cui) {
              if ($(attributeDiv).style.display == "inline") {
                sourceName = sourcePrefix + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_GROSS_APPEARANCE) %>';
                sourceValue = $(sourceName).value;
                targetName = targetPrefix + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_GROSS_APPEARANCE) %>';
                $(targetName).value = sourceValue;
              }
            }
            //percent viability
            else if ('<%= ArtsConstants.ATTRIBUTE_PERCENT_VIABILITY %>' == cui) {
              if ($(attributeDiv).style.display == "inline") {
                sourceName = sourcePrefix + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_PERCENT_VIABILITY) %>';
                sourceValue = $(sourceName).value;
                targetName = targetPrefix + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_PERCENT_VIABILITY) %>';
                $(targetName).value = sourceValue;
              }
            }
            //don't do anything with prepared by, as that attribute is captured at the operation 
            //level not the child sample level
            //procedure
            else if ('<%= ArtsConstants.ATTRIBUTE_PROCEDURE %>' == cui) {
              if ($(attributeDiv).style.display == "inline") {
                sourceName = sourcePrefix + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_PROCEDURE) %>';
                sourceValue = $(sourceName).value;
                targetName = targetPrefix + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_PROCEDURE) %>';
                $(targetName).value = sourceValue;
        	      //to ensure the drop-down is synchronized with the "other" text box, call the
        	      //onchange event.  Without this the "other" text box remains disabled, even
        	      //when it should be enabled.
        	      $(targetName).onchange();
                sourceName = sourcePrefix + "procedureOther";
                sourceValue = $(sourceName).value;
                targetName = targetPrefix + "procedureOther";
                $(targetName).value = sourceValue;
              }
            }
            //tissue
            else if ('<%= ArtsConstants.ATTRIBUTE_TISSUE %>' == cui) {
              if ($(attributeDiv).style.display == "inline") {
                sourceName = sourcePrefix + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_TISSUE) %>';
                sourceValue = $(sourceName).value;
                targetName = targetPrefix + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_TISSUE) %>';
                $(targetName).value = sourceValue;
        	      //to ensure the drop-down is synchronized with the "other" text box, call the
        	      //onchange event.  Without this the "other" text box remains disabled, even
        	      //when it should be enabled.
        	      $(targetName).onchange();
                sourceName = sourcePrefix + "tissueOther";
                sourceValue = $(sourceName).value;
                targetName = targetPrefix + "tissueOther";
                $(targetName).value = sourceValue;
              }
            }
            //total number of cells
            else if ('<%= ArtsConstants.ATTRIBUTE_TOTAL_NUMBER_OF_CELLS %>' == cui) {
              if ($(attributeDiv).style.display == "inline") {
                sourceName = sourcePrefix + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_TOTAL_NUMBER_OF_CELLS) %>';
                sourceValue = $(sourceName).value;
                targetName = targetPrefix + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_TOTAL_NUMBER_OF_CELLS) %>';
                $(targetName).value = sourceValue;
                sourceName = sourcePrefix + "totalNumOfCellsExRepCui";
                sourceValue = $(sourceName).value;
                targetName = targetPrefix + "totalNumOfCellsExRepCui";
                $(targetName).value = sourceValue;
              }
            }
            //volume
            else if ('<%= ArtsConstants.ATTRIBUTE_VOLUME %>' == cui) {
              if ($(attributeDiv).style.display == "inline") {
                sourceName = sourcePrefix + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_VOLUME) %>';
                sourceValue = $(sourceName).value;
                targetName = targetPrefix + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_VOLUME) %>';
                $(targetName).value = sourceValue;
        				sourceName = sourcePrefix + "volumeUnitCui";
                sourceValue = $(sourceName).value;
                targetName = targetPrefix + "volumeUnitCui";
                $(targetName).value = sourceValue;
              }
            }
			      //weight
            else if ('<%= ArtsConstants.ATTRIBUTE_WEIGHT %>' == cui) {
              if ($(attributeDiv).style.display == "inline") {
                sourceName = sourcePrefix + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_WEIGHT) %>';
                sourceValue = $(sourceName).value;
                targetName = targetPrefix + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_WEIGHT) %>';
                $(targetName).value = sourceValue;
        				sourceName = sourcePrefix + "weightUnitCui";
                sourceValue = $(sourceName).value;
                targetName = targetPrefix + "weightUnitCui";
                $(targetName).value = sourceValue;
              }
            }
           
           //yield
            else if ('<%= ArtsConstants.ATTRIBUTE_YIELD %>' == cui) {
              if ($(attributeDiv).style.display == "inline") {
                sourceName = sourcePrefix + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_YIELD) %>';
                sourceValue = $(sourceName).value;
                targetName = targetPrefix + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_YIELD) %>';
                $(targetName).value = sourceValue;
              }
            }
            
            //source
             else if ('<%= ArtsConstants.ATTRIBUTE_SOURCE %>' == cui) {
               if ($(attributeDiv).style.display == "inline") {
                 sourceName = sourcePrefix + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_SOURCE) %>';
                 sourceValue = $(sourceName).value;
                 targetName = targetPrefix + '<%= Constants.getPropertyName(ArtsConstants.ATTRIBUTE_SOURCE) %>';
                 $(targetName).value = sourceValue;
               }
             }
             
             //kc attribute
             else {
               if ($(attributeDiv).style.display == "inline") {
                 sourceValue = null;
                 sourceElement = null;
                 targetElement = null;
                 sourceForm = GSBIO.kc.data.FormInstances.getFormInstance(getFormId(derivationIndex, derivativeIndex));
                 for (n = 0; n < sourceForm.elements.length; n++) {
                   if (sourceForm.elements[n].getMeta().cui == cui) {
                     sourceElement = sourceForm.elements[n];
                     sourceMeta = sourceForm.elements[n].getMeta();
                     sourceValue = sourceElement.getPrimaryValue();
                   }
                 }
                 if (sourceElement) {
                   targetForm = GSBIO.kc.data.FormInstances.getFormInstance(getFormId(derivationIndex, i));
                   for (n = 0; n < targetForm.elements.length; n++) {
                     if (targetForm.elements[n].getMeta().cui == cui) {
                       targetElement = targetForm.elements[n];
                     }
                   }
                   if (targetElement) {
                     targetElement.setPrimaryValue(sourceValue);
                     $(targetElement.getHtmlIds().primary).fireEvent('onchange');
                     if (sourceMeta.isDatatypeCv && sourceValue == sourceMeta.otherCui) {
                       sourceValue = sourceElement.getOtherValue();
                       if (sourceValue) {
                         targetElement.setOtherValue(sourceValue);
                       }
                     }
                   }
                 }
               }
             }
          }
        }
      }
    }
  }
}

</script>
</head>

<body class="bigr" onLoad="initPage();">
<html:form action="/iltds/derivation/derivationBatchOperation.do"  onsubmit="return(validate());">
<%
SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);
boolean isFirst = true;

%>
<%// DIV for errors %>
  <div id="errorDiv" align="center">
    <table width="100%" border="1" cellspacing="1" cellpadding="1" class="foreground-small">
      <logic:present name="org.apache.struts.action.ERROR">
      <tr class="yellow"> 
	    <td> 
	      <font color="#FF0000"><b><html:errors/></b></font>
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
    <br/>
    <br/>
<html:hidden name="derivationBatchForm" property="dto.operationCui"/>
<html:hidden name="derivationBatchForm" property="dto.otherOperation"/>
<html:hidden name="derivationBatchForm" property="dto.childIdsStyle"/>
  
<%
// The batch information (operation, date, prepared by...)
%>
	<table border="0" class="foreground" cellpadding="3" cellspacing="0">
	  <tr>
	    <td align="left">
	      Operation: 
	    </td>
	    <td align="left">
	      <bean:write name="derivationBatchForm" property="operation"/>
	    </td>
	  </tr>
	  <tr>
	    <td align="left">
	      Date of operation: 
	    </td>
	    <td align="left">
				<script language="JavaScript">
					currentOperationDate = '<bean:write name="derivationBatchForm" property="dto.operationDateAsString"/>';
				</script>
	      <html:text name="derivationBatchForm" property="dto.operationDateAsString" readonly="true"/>&nbsp;
	      <span class="fakeLink" onclick="openCalendar('dto.operationDateAsString')">Select Date</span>              
	      &nbsp;&nbsp;
	      <span class="fakeLink" onclick="$('dto.operationDateAsString').value = ''">Clear Date</span>              
	    </td>
	  </tr>
	  <tr>
	    <td align="left">
	      Prepared by: 
	    </td>
	    <td align="left">
				<script language="JavaScript">
					currentPreparedBy = '<bean:write name="derivationBatchForm" property="dto.preparedBy"/>';
				</script>
	      <bigr:selectList name="derivationBatchForm" property="dto.preparedBy"
	        firstValue="" firstDisplayValue="Select"
	        legalValueSetProperty="preparedByList"/>
	    </td>
	  </tr>
	</table>

 	<hr style="border: 2px solid black;">
 	<p>Please enter all details for parent and derivative samples.</p>

<%
FormContextStack stack = FormContextStack.getFormContextStack(request);
%>
	<logic:iterate name="derivationBatchForm" property="dto.derivations" indexId="derivationCount" id="derivation" type="com.ardais.bigr.javabeans.DerivationDto">

<html:hidden name="derivationBatchForm" 
property='<%="dto.derivation[" + derivationCount + "].generatedSampleCount"%>'>
</html:hidden>

<script language="JavaScript">
  derivation = new Derivation();
  derivation.setIndex(<%=derivationCount%>);
  derivation.setParentGroupTdId('<%="parentGroup" + derivationCount%>');  
  derivation.setDerivativeGroupTdId('<%="derivativeGroup" + derivationCount%>');  

</script>

<%
DerivationOperation derivOp = 
	DerivationOperationFactory.SINGLETON.createDerivationOperation(derivation);

boolean isShowAlias = false;
Set parentsWithVolume = new HashSet();
Set parentsWithWeight = new HashSet();
%>
	<logic:iterate name="derivation" property="parents" indexId="parentCount" id="parent" type="com.ardais.bigr.javabeans.SampleData">
<%
	if (parent.isImported()) {
		isShowAlias = true;
	}
	//see which parents (if any) should show volume and/or weight.  First see if the
	//registration form contains either attribute and if so, it should show the attribute 
	DataFormDefinition registrationForm = parent.getRegistrationForm();
	if (registrationForm != null) {
	  DataFormDefinitionHostElement[] elements = registrationForm.getDataHostElements();
	  for (int elementCount=0; elementCount<elements.length; elementCount++) {
	    DataFormDefinitionHostElement element = elements[elementCount];
	    if (ArtsConstants.ATTRIBUTE_VOLUME.equalsIgnoreCase(element.getHostId())) {
	      parentsWithVolume.add(parent.getSampleId());
	    }
	    else if (ArtsConstants.ATTRIBUTE_WEIGHT.equalsIgnoreCase(element.getHostId())) {
	      parentsWithWeight.add(parent.getSampleId());
	    }
	  }
	}
	//if the parent has a non-empty value for volume and/or weight, it should show the attribute
	BigDecimal currentValue = ApiFunctions.safeBigDecimal(parent.getVolumeAsString());
	if (currentValue != null && currentValue.compareTo(new BigDecimal("0")) > 0) {
    parentsWithVolume.add(parent.getSampleId());
	}
	currentValue = ApiFunctions.safeBigDecimal(parent.getWeightAsString());
	if (currentValue != null && currentValue.compareTo(new BigDecimal("0")) > 0) {
    parentsWithWeight.add(parent.getSampleId());
	}
%>
	</logic:iterate>

  <table class="foreground" cellpadding="3" cellspacing="1" border="0">
    <tr class="white">
	    <td valign="top"><%=String.valueOf(derivationCount.intValue()+1)%>.</td>
	    <td>Parents<br>
			  <table class="graphTable" cellpadding="0" cellspacing="0">
			    <tr class="white">
			      <td/>
			      <td>Barcode</td>
<% if (isShowAlias) { %>
			      <td>Alias</td>
<% } %>
			      <td>Sample Type</td>
<% if (!parentsWithVolume.isEmpty()) { %>
			      <td>Volume</td>
<% } %>
<% if (!parentsWithWeight.isEmpty()) { %>
			      <td>Weight</td>
<% } %>
                  <bigr:isInRole role1="<%=SecurityInfo.ROLE_SYSTEM_OWNER%>">
                    <td>Inventory Groups</td>
                  </bigr:isInRole>
			    </tr>

				  <logic:iterate name="derivation" property="parents" indexId="parentCount" id="parent" type="com.ardais.bigr.javabeans.SampleData">
						<script language="JavaScript">
						  derivation.incrementNumberOfParents();  
						</script>

				    <tr class="white">
				      <td nowrap>
				        <html:checkbox name="derivationBatchForm" 
				          property='<%="dto.derivation[" + derivationCount + "].parent[" + parentCount + "].consumed"%>' 
				          value="Y" 
				          onclick='<%="adjustAmtRemaining(" + derivationCount + ", " + parentCount+ ");"%>'>
				          Consumed
				        </html:checkbox>
<%
    if (parentsWithVolume.contains(parent.getSampleId())) {
%>
				      	OR Volume Remaining 
				      	<html:text name="derivationBatchForm"
				      		property='<%="dto.derivation[" + derivationCount + "].parent[" + parentCount + "].volumeAsString"%>'
                  size="12" maxlength="12"
                  onkeyup='<%="adjustAmtRemaining(" + derivationCount + ", " + parentCount+ ");"%>'/>
                   <bigr:selectList name="derivationBatchForm"
		          			property='<%="dto.derivation[" + derivationCount + "].parent[" + parentCount + "].volumeUnitCui"%>' 
		          			legalValueSetProperty="volumeUnitChoices"
		          			firstValue="" firstDisplayValue="Select"/>
                
<%
		}
%>
<%
		if (parentsWithWeight.contains(parent.getSampleId())) {
%>
				      	OR Weight Remaining 
				      	<html:text name="derivationBatchForm"
				      		property='<%="dto.derivation[" + derivationCount + "].parent[" + parentCount + "].weightAsString"%>'
                  size="12" maxlength="12"
                  onkeyup='<%="adjustAmtRemaining(" + derivationCount + ", " + parentCount+ ");"%>'/>
                   <bigr:selectList name="derivationBatchForm"
		          			property='<%="dto.derivation[" + derivationCount + "].parent[" + parentCount + "].weightUnitCui"%>' 
		          			legalValueSetProperty="weightUnitChoices"
		          			firstValue="" firstDisplayValue="Select"/>
<%
		}
%>
				      </td>
				      <td>
				        <html:hidden name="derivationBatchForm" property='<%="dto.derivation[" + derivationCount + "].parent[" + parentCount + "].sampleId"%>'/>
				        <% 
				      		securityInfo = WebUtils.getSecurityInfo(request);
				        	String sampleId = parent.getSampleId();
									String link = IcpUtils.preparePopupLink(sampleId, securityInfo);
								%>
				        <%= link %>
				      </td>
<% if (isShowAlias) { %>
				      <td><bean:write name="parent" property="sampleAlias"/></td>
<% } %>
				      <td><bean:write name="parent" property="sampleType"/></td>
<% if (!parentsWithVolume.isEmpty()) { %>
				      <td><bean:write name="parent" property="volumeAsString"/>&nbsp
				     <bean:write name="parent" property="volumeUnitAsString"/></td>
<% } %>
<% if (!parentsWithWeight.isEmpty()) { %>
				      <td><bean:write name="parent" property="weightAsString"/>&nbsp
				     <bean:write name="parent" property="weightUnitAsString"/></td>
<% } %>
<%
    String lrNames = "";
    List logicalRepositories = parent.getLogicalRepositories();
    if (logicalRepositories != null && !logicalRepositories.isEmpty()) {
      StringBuffer buff = new StringBuffer(50);
      Iterator iterator = logicalRepositories.iterator();
      boolean includeComma = false;
      while (iterator.hasNext()) {
        LogicalRepository lr = (LogicalRepository) iterator.next();
        if (includeComma) {
          buff.append(", ");
        }
        buff.append(lr.getFullName());
        includeComma = true;
      }
      lrNames = buff.toString();
    }
%>
                      <bigr:isInRole role1="<%=SecurityInfo.ROLE_SYSTEM_OWNER%>">
                        <td><%=lrNames%></td>
                      </bigr:isInRole>
				    </tr>
<%
derivOp.addParentSample(parent);
%>
          </logic:iterate>  <% // all parents %>
        </table>
      </td>
    </tr>
	  <script language="JavaScript">
	    var repSample = new Sample();  
	  </script>
<%
SampleData repSample = derivOp.findCommonParentValues();
if (!ApiFunctions.isEmpty(repSample.getBufferTypeCui())) {
%>
	  <script language="JavaScript">
	  	repSample.setBufferTypeCui('<%=repSample.getBufferTypeCui()%>');
		</script>
<%
}
if (!ApiFunctions.isEmpty(repSample.getBufferTypeOther())) {
%>
    <script language="JavaScript">
			repSample.setBufferTypeOther('<%=Escaper.jsEscapeInScriptTag(repSample.getBufferTypeOther())%>');
		</script>
<%
}
if (repSample.getCellsPerMl() != null) {
%>
    <script language="JavaScript">
			repSample.setCellsPerMl('<%=repSample.getCellsPerMl().toString()%>');
		</script>
<%
}
if (!ApiFunctions.isEmpty(repSample.getAsmNote())) {
%>
    <script language="JavaScript">
			repSample.setComment('<%=Escaper.jsEscapeInScriptTag(repSample.getAsmNote())%>');
		</script>
<%
}
if (!ApiFunctions.isEmpty(repSample.getConcentrationAsString())) {
%>
    <script language="JavaScript">
			repSample.setConcentration('<%=Escaper.jsEscapeInScriptTag(repSample.getConcentrationAsString())%>');
		</script>
<%
}
if (repSample.getCollectionDateTime() != null) {
%>
	  <script language="JavaScript">
			repSample.setCollectionDateTime('<%=repSample.getCollectionDateTime().toString()%>');
      repSample.setCollectionDateDate('<%=repSample.getCollectionDateTime().getDate()%>');
			repSample.setCollectionDateHour('<%=repSample.getCollectionDateTime().getHour()%>');
			repSample.setCollectionDateMinute('<%=repSample.getCollectionDateTime().getMinute()%>');
			repSample.setCollectionDateMeridian('<%=repSample.getCollectionDateTime().getMeridian()%>');
		</script>
<%
}
if (repSample.getPreservationDateTime() != null) {
%>
	  <script language="JavaScript">
			repSample.setPreservationDateTime('<%=repSample.getPreservationDateTime().toString()%>');
      repSample.setPreservationDateDate('<%=repSample.getPreservationDateTime().getDate()%>');
      repSample.setPreservationDateHour('<%=repSample.getPreservationDateTime().getHour()%>');
      repSample.setPreservationDateMinute('<%=repSample.getPreservationDateTime().getMinute()%>');
      repSample.setPreservationDateMeridian('<%=repSample.getPreservationDateTime().getMeridian()%>');
		</script>
<%
}
if (!ApiFunctions.isEmpty(repSample.getFixativeType())) {
%>
	  <script language="JavaScript">
			repSample.setFixativeType('<%=repSample.getFixativeType()%>');
		</script>
<%
}
if (!ApiFunctions.isEmpty(repSample.getFormatDetail())) {
%>
	  <script language="JavaScript">
			repSample.setFormatDetail('<%=repSample.getFormatDetail()%>');
		</script>
<%
}
if (!ApiFunctions.isEmpty(repSample.getGrossAppearance())) {
%>
	  <script language="JavaScript">
			repSample.setGrossAppearance('<%=repSample.getGrossAppearance()%>');
		</script>
<%
}
if (repSample.getPercentViability() != null) {
%>
	  <script language="JavaScript">
			repSample.setPercentViability('<%=repSample.getPercentViability().toString()%>');
		</script>
<%
}
if (!ApiFunctions.isEmpty(repSample.getPreparedBy())) {
%>
	  <script language="JavaScript">
			repSample.setPreparedBy('<%=repSample.getPreparedBy()%>');
		</script>
<%
}
if (!ApiFunctions.isEmpty(repSample.getProcedure())) {
%>
	  <script language="JavaScript">
			repSample.setProcedure('<%=repSample.getProcedure()%>');
		</script>
<%
}
if (!ApiFunctions.isEmpty(repSample.getProcedureOther())) {
%>
	  <script language="JavaScript">
			repSample.setProcedureOther('<%=Escaper.jsEscapeInScriptTag(repSample.getProcedureOther())%>');
		</script>
<%
}
if (!ApiFunctions.isEmpty(repSample.getSource())) {
%>
  	<script language="JavaScript">
  	  repSample.setSource('<%=repSample.getSource()%>');
    </script>
<%
}
if (!ApiFunctions.isEmpty(repSample.getTissue())) {
%>
	  <script language="JavaScript">
			repSample.setTissue('<%=repSample.getTissue()%>');
		</script>
<%
}
if (!ApiFunctions.isEmpty(repSample.getTissueOther())) {
%>
	  <script language="JavaScript">
			repSample.setTissueOther('<%=Escaper.jsEscapeInScriptTag(repSample.getTissueOther())%>');
		</script>
<%
}
if (repSample.getTotalNumOfCells() != null) {
%>
	  <script language="JavaScript">
			repSample.setTotalNumOfCells('<%=repSample.getTotalNumOfCells().toString()%>');
		</script>
<%
}
if (repSample.getTotalNumOfCellsExRepCui() != null) {
%>
	  <script language="JavaScript">
			repSample.setTotalNumOfCellsUnit('<%=repSample.getTotalNumOfCellsExRepCui()%>');
		</script>
<%
}
if (!ApiFunctions.isEmpty(repSample.getVolumeAsString())) {
%>
	  <script language="JavaScript">
			repSample.setVolume('<%=repSample.getVolume().toString()%>');
		</script>
<%
}
if (repSample.getVolumeUnitCui() != null) {
  %>
  	  <script language="JavaScript">
  			repSample.setVolumeUnitCui('<%=repSample.getVolumeUnitCui()%>');
  		</script>
  <%
  }
if (!ApiFunctions.isEmpty(repSample.getWeightAsString())) {
  %>
  	  <script language="JavaScript">
  			repSample.setWeight('<%=repSample.getWeight().toString()%>');
  		</script>
  <%
  }
  if (repSample.getWeightUnitCui() != null) {
    %>
    	  <script language="JavaScript">
    			repSample.setWeightUnitCui('<%=repSample.getWeightUnitCui()%>');
    		</script>
    <%
    }
if (!ApiFunctions.isEmpty(repSample.getYieldAsString())) {
%>
	  <script language="JavaScript">
			repSample.setYield('<%=repSample.getYieldAsString()%>');
		</script>
<%
}
if (repSample.getRegistrationFormInstance() != null) {
  DataElement[] dataElements = repSample.getRegistrationFormInstance().getDataElements();
  for (int dataElementCount=0; dataElementCount<dataElements.length; dataElementCount++) {
    DataElement dataElement = dataElements[dataElementCount];
%>
	  <script language="JavaScript">
      var dataElement = new DataElement();
      dataElement.setCui('<%=dataElement.getCuiOrSystemName()%>');
      dataElement.setNote('<%=dataElement.getValueNote()%>');
<%
		ElementValue[] values = dataElement.getElementValues();
		for (int valueCount=0; valueCount<values.length; valueCount++) {
		  ElementValue value = values[valueCount];
%>
		  var value = new DataElementValue();
		  value.setValue('<%=value.getValue()%>');
		  value.setValueOther('<%=value.getValueOther()%>');
		  dataElement.addDataElementValue(value);
<%
		}
%>
      repSample.getDataElements().addDataElement(dataElement);
		</script>
<%
  }
}
%>
		<script language="JavaScript">
		  derivation.setRepresentativeParentSample(repSample);  
		</script>

    <tr class="white">
	    <td>&nbsp;</td>
	    <td>
	      <br>Derivatives<br>
		  <bigr:isInRole role1="<%=SecurityInfo.ROLE_SYSTEM_OWNER%>">
		    <table class="foreground" cellpadding="0" cellspacing="0">
		      <tr>
		        <%
		          String onclick = "enableInventoryGroupDropdown(" + derivationCount + ",this.value);";
		          boolean disabled = false;
		          boolean allowInheritance = myForm.isAllowInventoryGroupInheritance(derivationCount.toString());
		          if (!allowInheritance) {
		            disabled = true;
		          }
		          //if no value has been specified for either radio button, default to inherit if that
		          //has not been disabled.  Otherwise default to explicitly specifying groups.
		          String inheritIGs = myForm.getDto().getDerivation(derivationCount.intValue()).getInheritInventoryGroupsYN();
		          if (ApiFunctions.isEmpty(inheritIGs)) {
		            if (allowInheritance) {
		              inheritIGs = FormLogic.DB_YES;
		            }
		            else {
		              inheritIGs = FormLogic.DB_NO;
		            }
		            myForm.getDto().getDerivation(derivationCount.intValue()).setInheritInventoryGroupsYN(inheritIGs);
		          }
		        %>
		        <td align="left">
		          <html:radio name="derivationBatchForm" property='<%="dto.derivation[" + derivationCount + "].inheritInventoryGroupsYN"%>' value="<%= FormLogic.DB_YES %>" onclick="<%=onclick%>" disabled='<%=disabled%>'/>Inherit inventory groups from parents
		        </td>
		        <td>
		          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		        </td>
		        <td align="left">
		          <html:radio name="derivationBatchForm" property='<%="dto.derivation[" + derivationCount + "].inheritInventoryGroupsYN"%>' value="<%= FormLogic.DB_NO %>" onclick="<%=onclick%>"/>Specify inventory groups:
		        <td>
		      </tr>
		      <tr>
		        <td colspan="2">
		        </td>
		        <td align="left">
		          <%
		            String inventoryGroupProperty = "dto.derivation[" + derivationCount + "].inventoryGroups";
		            boolean isEnabled = FormLogic.DB_NO.equalsIgnoreCase(inheritIGs);
		          %>
                  <bigr:selectList
                    size="3"
                    multiple="true"
                    name="derivationBatchForm"
                    property="<%= inventoryGroupProperty %>"
                    legalValueSetName="derivationBatchForm"
                    legalValueSetProperty="inventoryGroupChoices"
                    disabled="<%= !isEnabled %>"/><br>
		        <td>
		      </tr>
            </table>
            <br>
          </bigr:isInRole>
   		  <table class="graphTable" cellpadding="0" cellspacing="0">
			    <tr class="white" >
			      <td nowrap class="sampleAttribute">&nbsp;</td>
			      <td nowrap class="sampleAttribute">Barcode</td>
			      <td nowrap class="sampleAttribute">Alias</td>
			      <td nowrap class="sampleAttribute">Sample Type</td>

<%
//iterate over the universe of data elements used across the child sample types in this derivation
//and create table column headers
DataFormDefinitionElement[] formElements = myForm.getPotentialDataFormDefinitionElementsForDerivation(derivationCount.intValue());
for (int elementCount=0; elementCount < formElements.length; elementCount ++) { 
  DataFormDefinitionElement formElement = formElements[elementCount];
  if (formElement.isHostElement()) {
    DataFormDefinitionHostElement hostElement = formElement.getDataHostElement();
%>
			      <td nowrap id='<%=hostElement.getHostId() + "Header" + derivationCount%>' style="display:none"><%=hostElement.getDisplayName()%></td>
<%
  }
  else {
    DataFormDefinitionDataElement dataElement = formElement.getDataDataElement();
%>
			      <td nowrap id='<%=dataElement.getCui() + "Header" + derivationCount%>' style="display:none"><%=dataElement.getDisplayName()%></td>
<%
  }
}
%>
			    </tr>


				  <logic:iterate name="derivation" property="children" indexId="derivativeCount" id="derivative" type="com.ardais.bigr.javabeans.SampleData">
						<script language="JavaScript">
						  derivation.incrementNumberOfDerivatives();
              var copyButtonKey = '<%=derivationCount%>' + '_' + '<%=derivativeCount%>';
              copyButtonMap.put(copyButtonKey, copyButtonCount);
              copyButtonCount = copyButtonCount + 1;
						</script>
<%
FormContext formContext = stack.peek();
FormInstance formInstance = derivative.getRegistrationFormInstance();
if (formInstance != null && formInstance.getDataElements().length > 0) {
  formContext.setFormInstance(formInstance);
}
String jsFormInstanceId = "f" + derivationCount + derivativeCount;
formContext.setJavascriptObjectId(jsFormInstanceId);
formContext = stack.push(formContext);
%>
<input type="hidden" name="<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "].kcForm"%>"/>
				    <tr class="white">
				      <td>
							  <html:button property="btnCopyAttributes" disabled="true" styleClass="libraryButtons" 
							     onclick='<%="copyAttributes(" + derivationCount + "," + derivativeCount + ");"%>'>
								  Copy...
							  </html:button>
				      </td>
				      <td style="text-align:center;">
<%
	if (isGenerated) {
%>
				        <html:hidden name="derivationBatchForm" property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "].generatedSampleId"%>'/>
				      	<bean:write name="derivative" property="generatedSampleId"/>
								<br><span style="font-size:8pt;">(generated)</span>
<%
	}
	else {
%>
				        <html:hidden name="derivationBatchForm" property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "].sampleId"%>'/>
				      	<bean:write name="derivative" property="sampleId"/>
<%
	}
%>
				      </td>				      
				      <td><html:text name="derivationBatchForm" 
				        property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "].sampleAlias"%>'
				        size="30" maxlength="30"/>
				      </td>				      
              <td> 
	        <% 
	        //if Multiple Sample Types then prompt dropdown selection
	        int typeCount = ((LegalValueSet)myForm.getChildSampleTypeChoices().get(derivationCount.intValue())).getCount();
					if (typeCount > 1) { %>
				        <bigr:selectList name="derivationBatchForm" 
				        	property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "].sampleTypeCui"%>'
			            onchange='<%="onSampleTypeChange(this.value," + derivationCount + ", " + derivativeCount+ ");"%>'
						  	  firstValue="" firstDisplayValue="Select Type"
					        legalValueSetProperty='<%="childSampleTypeChoices[" + derivationCount + "]"%>'/>
	         <% } else { //no prompt required %>			        
				        <bigr:selectList name="derivationBatchForm" 
				        	property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "].sampleTypeCui"%>'
			            onchange='<%="onSampleTypeChange(this.value," + derivationCount + ", " + derivativeCount+ ");"%>'
					        legalValueSetProperty='<%="childSampleTypeChoices[" + derivationCount + "]"%>'/>
					<% } %>			        
              </td>
<%
//now iterate over the universe of data elements used across the child sample types in this 
//derivation and create table data elements
DataFormDefinitionElement formElement = null;
DataFormDefinitionHostElement hostElement = null;
DataFormDefinitionDataElement dataElement = null;
String attr = null;
String attrProperty = null;
String attrOther = null;
String firstDisplayValue = null;
for (int elementCount=0; elementCount < formElements.length; elementCount ++) { 
  formElement = formElements[elementCount];
  if (formElement.isHostElement()) {
    hostElement = formElement.getDataHostElement();
    attr = hostElement.getHostId();
    attrProperty = Constants.getPropertyName(attr);
    attrOther = attrProperty + "Other";
    firstDisplayValue = "Select " +  hostElement.getDisplayName();
  }
  else {
    dataElement = formElement.getDataDataElement();
    attr = dataElement.getCui();
    DataFormDefinition formDef = (DataFormDefinition)dataElement.getForm();
    if (formDef != null) {
      formContext.setDataFormDefinition(formDef);
    }
    formContext.setDataFormDefinitionDataElement(dataElement);
    if (formInstance != null && formInstance.getDataElement(attr) != null) {
      formContext.setDataElement(formInstance.getDataElement(attr));
    }
    else{
      //SWP793 - need to clear out the previous form instance data element to prevent it's use 
      //for this form definition data element
      formContext.setDataElement(null);
    }
    formContext = stack.push(formContext);
  }
%>
              <td nowrap id='<%=attr + "Td" + derivationCount + derivativeCount%>' style="display:none">
              	<div id='<%=attr + "Div" + derivationCount + derivativeCount%>' style="display:none">
<%
    if (ArtsConstants.ATTRIBUTE_DATE_OF_COLLECTION.equalsIgnoreCase(attr)) {
      String resetString = "clearDateTime('dto.derivation[" + derivationCount + "].child[" + derivativeCount + "].collectionDateTime.');";
%>
                	<html:text name="derivationBatchForm" 
                  	property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "]." + attrProperty%>' 
                    readonly="true"/>
                  &nbsp;
	                <span class="fakeLink" onclick="openCalendar('<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "]." + attrProperty%>')">Select Date</span>
	                &nbsp;
				        	<html:select name="derivationBatchForm" property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "].collectionDateTime.hour"%>'>
          		    	<html:option value="">Hr</html:option>
				          	<html:options name="derivationBatchForm" property="hourList"/>
				        	</html:select>
				        	:
				        	<html:select name="derivationBatchForm" property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "].collectionDateTime.minute"%>'>
          		    	<html:option value="">Min</html:option>
				          	<html:options name="derivationBatchForm" property="minuteList"/>
				        	</html:select>
				        	&nbsp;
				        	<html:radio name="derivationBatchForm" property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "].collectionDateTime.meridian"%>' value="AM"/>AM
				        	<html:radio name="derivationBatchForm" property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "].collectionDateTime.meridian"%>' value="PM"/>PM                        
				        	&nbsp;&nbsp;&nbsp;
				        	<html:button property="resetCollection" value="Clear" onclick="<%=resetString%>"/>
<%
    }
    else if (ArtsConstants.ATTRIBUTE_DATE_OF_PRESERVATION.equalsIgnoreCase(attr)) {
      String resetString = "clearDateTime('dto.derivation[" + derivationCount + "].child[" + derivativeCount + "].preservationDateTime.');";
%>
                  <html:text name="derivationBatchForm" 
                  	property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "]." + attrProperty%>' 
                    readonly="true"/>
                  &nbsp;
	                <span class="fakeLink" onclick="openCalendar('<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "]." + attrProperty%>')">Select Date</span>
	                &nbsp;
				        	<html:select name="derivationBatchForm" property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "].preservationDateTime.hour"%>'>
          		    	<html:option value="">Hr</html:option>
				          	<html:options name="derivationBatchForm" property="hourList"/>
				        	</html:select>
				        	:
				        	<html:select name="derivationBatchForm" property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "].preservationDateTime.minute"%>'>
          		    	<html:option value="">Min</html:option>
				          	<html:options name="derivationBatchForm" property="minuteList"/>
				        	</html:select>
				        	&nbsp;
				        	<html:radio name="derivationBatchForm" property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "].preservationDateTime.meridian"%>' value="AM"/>AM
				        	<html:radio name="derivationBatchForm" property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "].preservationDateTime.meridian"%>' value="PM"/>PM                                              
				        	&nbsp;&nbsp;&nbsp;
				        	<html:button property="resetCollection" value="Clear" onclick="<%=resetString%>"/>
<%
    }
    else if (ArtsConstants.ATTRIBUTE_PROCEDURE.equalsIgnoreCase(attr)) {
      String onChangeEvent = "showOtherForList(this.value,'dto.derivation[" + derivationCount + "].child[" + derivativeCount + "]." + attrOther + "','" + FormLogic.OTHER_PX + "');";
%>
				 					<bigr:procedureWithOther name="derivationBatchForm" 
		          		  property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "]." + attrProperty%>'
				 		  			propertyLabel=''
				 		  			mode='basic' onChange='<%=onChangeEvent%>'
		          		  firstValue="" firstDisplayValue="<%=firstDisplayValue%>"/>
		              <bigr:otherText name="derivationBatchForm" 
		              	property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "]." + attrOther%>'
		                parentProperty='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "]." + attrProperty%>'
		                otherCode="<%=FormLogic.OTHER_PX%>" size="15" maxlength="200"/>
<%      
    }
    else if (ArtsConstants.ATTRIBUTE_TISSUE.equalsIgnoreCase(attr)) {
      String onChangeEvent = "showOtherForList(this.value,'dto.derivation[" + derivationCount + "].child[" + derivativeCount + "]." + attrOther + "','" + FormLogic.OTHER_TISSUE + "');";
%>
									<bigr:tissueWithOther name="derivationBatchForm"
				        	  property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "]." + attrProperty%>'
						  			propertyLabel=''
						  			mode='basic' onChange='<%=onChangeEvent%>'
				    		    firstValue="" firstDisplayValue="<%=firstDisplayValue%>"/>
				          <bigr:otherText name="derivationBatchForm" 
				        	  property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "]." + attrOther%>' 
				            parentProperty='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "]." + attrProperty%>'
				            otherCode="<%=FormLogic.OTHER_TISSUE%>" size="15" maxlength="200"/>
<%
    }
    else if (ArtsConstants.ATTRIBUTE_GROSS_APPEARANCE.equalsIgnoreCase(attr)) {
%>
				        	<bigr:selectList name="derivationBatchForm" 
                  	property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "]." + attrProperty%>'
			              firstValue="" firstDisplayValue="<%=firstDisplayValue%>"
				          	legalValueSetProperty="appearanceChoices"/>
<%      
    }
    else if (ArtsConstants.ATTRIBUTE_FIXATIVE_TYPE.equalsIgnoreCase(attr)) {
%>
				        	<bigr:selectList name="derivationBatchForm" 
                  	property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "]." + attrProperty%>'
			              firstValue="" firstDisplayValue="<%=firstDisplayValue%>"
				          	legalValueSetProperty="fixativeTypeChoices"/>
<%
    }
    else if (ArtsConstants.ATTRIBUTE_FORMAT_DETAIL.equalsIgnoreCase(attr)) {
%>
				        	<bigr:selectList name="derivationBatchForm" 
                  	property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "]." + attrProperty%>'
			              firstValue="" firstDisplayValue="<%=firstDisplayValue%>"
				          	legalValueSetProperty="formatDetailChoices"/>
<%
      
    }
    else if (ArtsConstants.ATTRIBUTE_PREPARED_BY.equalsIgnoreCase(attr)) {
%>
                	<bigr:selectList name="derivationBatchForm" 
                  	property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "]." + attrProperty%>'
                    firstValue="" firstDisplayValue="Select"
                    legalValueSetProperty="preparedByList"/>
<% 
    }
    else if (ArtsConstants.ATTRIBUTE_BUFFER_TYPE.equalsIgnoreCase(attr)) {
      attrOther = "bufferTypeOther";
%>
				 					<bigr:selectListWithOther name="derivationBatchForm"
                  	property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "]." + attrProperty%>'
		          		  legalValueSetProperty="bufferTypeChoices"
		          		  otherProperty='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "]." + attrOther%>' 
		          		  otherCode="<%=ArtsConstants.OTHER_BUFFER_TYPE%>"
		          		  firstValue="" firstDisplayValue="<%=firstDisplayValue%>"/>
		              <bigr:otherText name="derivationBatchForm" 
		              	property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "]." + attrOther%>' 
		                parentProperty='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "]." + attrProperty%>'
		                otherCode="<%=ArtsConstants.OTHER_BUFFER_TYPE%>" size="15" maxlength="200"/>
<%
    }
    else if (ArtsConstants.ATTRIBUTE_TOTAL_NUMBER_OF_CELLS.equalsIgnoreCase(attr)) {
%>
		            	<html:text name="derivationBatchForm" 
                  	property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "]." + attrProperty%>'
		                size="6"
		                maxlength="6"/>
				 					<bigr:selectList name="derivationBatchForm"
		          			property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "].totalNumOfCellsExRepCui"%>' 
		          			legalValueSetProperty="totalNumberOfCellsExponentialRepChoices"
		          			firstValue="" firstDisplayValue="Select"/>
<%
    }
    else if (ArtsConstants.ATTRIBUTE_CELLS_PER_ML.equalsIgnoreCase(attr)) {
%>
		            	<html:text name="derivationBatchForm" 
                  	property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "]." + attrProperty%>'
		                size="11"
		                maxlength="11"/>
<%
    }
    else if (ArtsConstants.ATTRIBUTE_PERCENT_VIABILITY.equalsIgnoreCase(attr)) {
%>
		            	<html:text name="derivationBatchForm" 
                  	property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "]." + attrProperty%>'
		                size="3"
		                maxlength="3"/>
<%
    }
    else if (ArtsConstants.ATTRIBUTE_VOLUME.equalsIgnoreCase(attr)) {
%>
                	<html:text name="derivationBatchForm" 
                  	property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "]." + attrProperty%>'
                    size="12" maxlength="12"/>
                    	<bigr:selectList name="derivationBatchForm"
		          			property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "].volumeUnitCui"%>' 
		          			legalValueSetProperty="volumeUnitChoices"
		          			firstValue="" firstDisplayValue="Select"/>
<%      
    }
    else if (ArtsConstants.ATTRIBUTE_WEIGHT.equalsIgnoreCase(attr)) {
%>
                	<html:text name="derivationBatchForm" 
                  	property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "]." + attrProperty%>'
                    size="12" maxlength="12"/>
                    	<bigr:selectList name="derivationBatchForm"
		          			property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "].weightUnitCui"%>' 
		          			legalValueSetProperty="weightUnitChoices"
		          			firstValue="" firstDisplayValue="Select"/>
<%
    }
    else if (ArtsConstants.ATTRIBUTE_CONCENTRATION.equalsIgnoreCase(attr)) {
%>
		            	<html:text name="derivationBatchForm" 
                  	property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "]." + attrProperty%>'
		                size="8"
		                maxlength="8"/>
<%
    }
    else if (ArtsConstants.ATTRIBUTE_YIELD.equalsIgnoreCase(attr)) {
%>
		            	<html:text name="derivationBatchForm" 
                  	property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "]." + attrProperty%>'
		                size="8"
		                maxlength="8"/>
<%      
    }
    else if (ArtsConstants.ATTRIBUTE_SOURCE.equalsIgnoreCase(attr)) {
%>
		            	<html:text name="derivationBatchForm" 
                  	property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "]." + attrProperty%>'
		                size="50"
		                maxlength="200"/>
<%
    }
    else if (ArtsConstants.ATTRIBUTE_COMMENT.equalsIgnoreCase(attr)) {
%>
                	<html:text name="derivationBatchForm" property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "]." + attrProperty%>'
                  	size="25" maxlength="255"/>
<%
    }
    //if the attr is not a BIGR core attribute, it must be a KC data element
    else {
      DataFormDataElementContext dataElementContext = formContext.getDataFormDataElementContext();
      String firstSelect = "Select " + dataElementContext.getLabel();
%>
		<script language="JavaScript">
		  if (!GSBIO.kc.data.FormInstances.getFormInstance('<%=jsFormInstanceId%>')) {
		    GSBIO.kc.data.FormInstances.createFormInstance({'id': '<%=jsFormInstanceId%>'});
		  }
		</script>
        <bigr:dataElementInDerivativeOperation firstValue="" firstDisplay="<%=firstSelect%>"/>
<%      
      stack.pop();
    }
%>
                </div>
              </td>
<%
  }
%>
				    </tr>
          </logic:iterate>
        </table>
      </td>
    </tr>
		<script language="JavaScript">
		  derivationBatch.setDerivation(derivation);  
		</script>			
  </table>
    <br>
 	<hr style="border: 2px solid black;">
<%
stack.pop();
%>
  </logic:iterate>

<%
// Buttons.
%>
  <table border="0" class="foreground" cellpadding="3" cellspacing="0" width="100%">
    <tr class="white"> 
      <td align="left">
        <input type="button" name="btnStartOver" value="Start Over" onclick="startOver();"/>
      </td>
      <td align="right">
        <input type="button" name="btnBack" value="&lt; Back" onclick="goBack();"/>
        <input type="submit" name="btnFinish" value="Finish"/> 
      </td>
    </tr>
  </table>

<%
// If the derivative has a sample type cui value, then call the onchange event.
// This happens when we are repopulating the page after a validation error,
// but programatically setting the sample type cui does not automatically 
// trigger the call to the onchange event.  Here we'll create a function that
// is called by the initPage function, which is called by the body onload event.
%>
	  <script language="JavaScript">
  	function showAllSampleAttributes() {
  <logic:iterate name="derivationBatchForm" property="dto.derivations" indexId="derivationCount" id="derivation" type="com.ardais.bigr.javabeans.DerivationDto">
    <logic:iterate name="derivation" property="children" indexId="derivativeCount" id="derivative" type="com.ardais.bigr.javabeans.SampleData">
		<%
			int typeCount = ((LegalValueSet)myForm.getChildSampleTypeChoices().get(derivationCount.intValue())).getCount();
			if ( !ApiFunctions.isEmpty(derivative.getSampleTypeCui()) || (typeCount == 1) ) {
				String controlName = "dto.derivation[" + derivationCount + "].child[" + derivativeCount + "].sampleTypeCui";
		%>
			  onSampleTypeChange($('<%=controlName%>').value,<%=derivationCount%>,<%=derivativeCount%>);
		<%
		}
		%>
		</logic:iterate>
  </logic:iterate>
		}
  </script>

</html:form>
</body>
</html>