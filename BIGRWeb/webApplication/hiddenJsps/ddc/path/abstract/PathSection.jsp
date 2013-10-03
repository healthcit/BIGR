<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.iltds.helpers.FormLogic" %>
<%@ page import="com.ardais.bigr.util.ArtsConstants" %>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, getServletContext(), "P");
%>
<bean:define name="helper" id="section"/>
<bean:define name="helper" property="pathReport" id="path"/>

<html>
<head>
<title>Pathology Report Section</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript">

function MinPathFields() {
  // Public methods
  this.addField = mpAddField;
  this.getCount = mpGetCount;
  this.getField = mpGetField;

  // Private members
  this.count = 0;
  this.fields = new Array();
}

function MinPathField(name, label, emptyValue) {
  // Public methods
  this.getName = mpGetFieldName;
  this.getLabel = mpGetFieldLabel;
  this.getEmptyValue = mpGetFieldEmptyValue;

  // Private members
  this.name = name;
  this.label = label;
  this.emptyValue = emptyValue;
}

function mpAddField(name, label, emptyValue) {
  this.fields[this.count++] = new MinPathField(name, label, emptyValue);
}
function mpGetCount() {
  return this.count;
}
function mpGetField(index) {
  return this.fields[index];
}
function mpGetFieldName() {
  return this.name;
}
function mpGetFieldLabel() {
  return this.label;
}
function mpGetFieldEmptyValue() {
  return this.emptyValue;
}

var minPathFields = new MinPathFields();
<logic:equal name="section" property="displayLymphNodeStage" value="true">  
  var currentLymphNodeStageList = null;
</logic:equal>

<%
String enableStageIndTumor = null; 
String enableStageIndLymph = null; 
String enableStageIndLymphG0 = null; 
String enableStageIndMets = null; 
%>

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = 'Pathology Report Section';

  <logic:equal name="section" property="displayTumorStageDesc" value="true">
<% enableStageIndTumor = "enableStageInd('tumorStageType', 'tumorStageDescInd', 'tumorStageDescIndLabel', 'tumorStageDesc', '" + FormLogic.NOT_REPORTED_TUMOR_STAGE_DESC + "');"; %>
<%=enableStageIndTumor%>
</logic:equal>  

<logic:equal name="section" property="displayLymphNodeStage" value="true">  
<% enableStageIndLymph = "enableStageInd('tumorStageType', 'lymphNodeStageInd', 'lymphNodeStageIndLabel', 'lymphNodeStage', '" + FormLogic.NOT_REPORTED_LYMPH_NODE_STAGE_DESC + "');"; %>
  document.all.lymphNodeStage1.style.display = "";
  <%=enableStageIndLymph%>
</logic:equal>

<logic:equal name="section" property="displayDistantMetastasis" value="true">  
<% enableStageIndMets = "enableStageInd('tumorStageType', 'distantMetastasisInd', 'distantMetastasisIndLabel', 'distantMetastasis', '" + FormLogic.NOT_REPORTED_DISTANT_METASTASIS + "');"; %>
<%=enableStageIndMets%>
</logic:equal>

<logic:equal name="section" property="displayTumorSize" value="true">  
  disableTissueSize();
</logic:equal>

<logic:equal name="section" property="displayTumorWeight" value="true">  
  disableTissueWeight();
</logic:equal>
}

function processAnnArborSelection(value, selectList) {
	if (selectList != null) {
		if (value == '<%= ArtsConstants.CODE_TUMOR_STAGE_TYPE_ANNARBOR %>') {
		    selectList.selectedIndex = 1;
		}
		else {
			selectList.selectedIndex = 0;
		}
	}
}

function calculateGleasonTotal() {  
  var primary = document.all.gleasonPrimary.value;
  var secondary = document.all.gleasonSecondary.value;
  var total = ((primary == "") || (secondary == "")) ? "" : parseInt(primary) + parseInt(secondary);
  document.all.gleasonTotalSpan.innerHTML = total;
  document.all.gleasonTotal.value = total;
}

function disableTissueSize() {
	if (document.all.tumorSizeNS.checked) {
    document.all.tumorSize1.value = "N/A";
    document.all.tumorSize2.value = "N/A";
    document.all.tumorSize3.value = "N/A";
    document.all.tumorSize1.disabled = true;
    document.all.tumorSize2.disabled = true;
    document.all.tumorSize3.disabled = true;
    return true;
	}
  else {
    return false;
  }
}

function toggleTissueSize() {
  if (!disableTissueSize()) {
    document.all.tumorSize1.value = "";
    document.all.tumorSize2.value = "";
    document.all.tumorSize3.value = "";
    document.all.tumorSize1.disabled = false;
    document.all.tumorSize2.disabled = false;
    document.all.tumorSize3.disabled = false;
	}
}

function disableTissueWeight() {
	if (document.all.tumorWeightNS.checked) {
    document.all.tumorWeight.value = "N/A";
    document.all.tumorWeight.disabled = true;
    return true;
	}
  else {
    return false;
	}
}

function toggleTissueWeight() {
	if (!disableTissueWeight()) {
    document.all.tumorWeight.value = "";
    document.all.tumorWeight.disabled = false;
	}
}

function checkNodeTotals() {
  var examinedIndex = document.all.totalNodesExamined.selectedIndex;
  var examined = document.all.totalNodesExamined.options[examinedIndex].text;
  var positeveIndex = document.all.totalNodesPositive.selectedIndex;
  var positive = document.all.totalNodesPositive.options[positeveIndex].text;
  
	if ((examined != "") && (positive != "")) {
		if (parseInt(positive) > parseInt(examined)) {
		    alert("Total Number of Postive Nodes cannot be greater than Total Number of Nodes Examined.");
		    event.srcElement.selectedIndex = 0;
		    event.srcElement.focus();
		}
		var examinedInt = parseInt(examined);
		var positiveValue = document.all.totalNodesPositive.value;
		if ((examinedInt > 0 && examinedInt <= 3) && (positiveValue == 'CA01084C')){
		    alert("If Total Number of Postive Nodes is Multiple then  Total Number of Nodes Examined must be greater than 3.");
		    event.srcElement.selectedIndex = 0;
		    event.srcElement.focus();
		}
		else if((examinedInt >= 1 && examinedInt <= 40) && (positiveValue == 'CA01083C')){
		    alert("Total Number of Postive Nodes cannot be greater than Total Number of Nodes Examined.");
		    event.srcElement.selectedIndex = 0;
		    event.srcElement.focus();
		}
	 }
}

function enableStageInd(tumorStage, ind, indLabel, value, nrCode) {
  var inds = document.all[ind];
  var indLabels = document.all[indLabel];
  if (document.all[value] == null) {
    for (var i = 0; i < inds.length; i++) {
      inds[i].checked = false;
      inds[i].disabled = true;
    }
    for (var i = 0; i < indLabels.length; i++) {
      indLabels[i].disabled = true;
    }
  }
  else {
  	if ((document.all[tumorStage] != null) && (document.all[tumorStage].value == '<%= ArtsConstants.CODE_TUMOR_STAGE_TYPE_ANNARBOR %>')) {
      for (var i = 0; i < inds.length; i++) {
        inds[i].checked = false;
        inds[i].disabled = true;
      }
      for (var i = 0; i < indLabels.length; i++) {
        indLabels[i].disabled = true;
      }
  	}
  	else { 
	    if ((document.all[value].value == '') || (document.all[value].value == nrCode)) {
	      for (var i = 0; i < inds.length; i++) {
	        inds[i].checked = false;
	        inds[i].disabled = true;
	      }
	      for (var i = 0; i < indLabels.length; i++) {
	        indLabels[i].disabled = true;
	      }
	    }
	    else {
	      for (var i = 0; i < inds.length; i++) {
	        inds[i].disabled = false;
	      }
	      for (var i = 0; i < indLabels.length; i++) {
	        indLabels[i].disabled = false;
	      }
	    }
	}
  }
}

// Check that tissue weight is of the form xxxx.xx
function checkTissueWeight() {
  var weight = document.all.tumorWeight.value;
  if (weight.search(/(^\d{0,4}(\.\d{0,2}){0,1}$)/) != 0) {
    alert("Tissue Weight must be entered in the form: xxxx.xx");
    document.all.tumorWeight.focus();
  }
}

// Check that the tissue size dimension is of the form xxx.x
function checkTissueSize() {
  var size = event.srcElement.value;
  if (size.search(/(^\d{0,3}(\.\d{0,1}){0,1}$)/) != 0) {
    alert("Tissue Size must be entered in the form: xxx.x");
    event.srcElement.focus();
  }
}

// Change the list of values for Lymph Node Stage if necessary,
// depending upon the value of Total Number of Positive Nodes.    
// Called from Total Number of Positive Nodes onchange.
function changeLymphNodeStageList() {
  var num = document.all.totalNodesPositive.value;
  var currentValue = null;
  if (((num == '') || (parseInt(num) == 0)) && (currentLymphNodeStageList == 'g0')) {
    document.all.lymphNodeStage2.style.display = "none";  
    document.all.lymphNodeStage1.style.display = "";
    currentLymphNodeStageList = 'full'
    if (document.all.lymphNodeStageG0 != null) {
      currentValue = document.all.lymphNodeStageG0.value;
      selectOption(document.all.lymphNodeStage, currentValue);
    }
<%=enableStageIndLymph%>
  }
  else if ((parseInt(num) > 0) && (currentLymphNodeStageList == 'full')) {
    document.all.lymphNodeStage1.style.display = "none";  
    document.all.lymphNodeStage2.style.display = "";
    currentLymphNodeStageList = 'g0'
    if (document.all.lymphNodeStage != null) {
      currentValue = document.all.lymphNodeStage.value;
      selectOption(document.all.lymphNodeStageG0, currentValue);
    }
<%=enableStageIndLymphG0%>
  }
}

function selectOption(selectList, value) {
  for (var i = 0; i < selectList.length; i++) {
    if (selectList.options[i].value == value) {
      selectList.selectedIndex = i;
      return;
    }
  }
  selectList.selectedIndex = 0;
}

function selectFindingTissue() {
  //alert('Entering selectFindingTissue');
  if (document.all.tissueOrigin.value != "") {
    //alert('setting value');
    //make sure the tissueFinding select control is fully populated - the user may have
    //done an alphanumeric search, and the value chosen in tissueOrigin would not be
    //guaranteed to be in the tissueFinding select control unless we take this step.
    restoreListOptions(document.all.searchDiagtissueFinding, document.all.tissueFinding);
    //now select the same value in tissueFinding as was selected in tissueOrigin.  If the
    //two controls have the same number of options (meaning they're both fully populated
    //since the line above fully populates the tissueFinding control) then just set the
    //selectedIndex on tissueFinding to be the same as the selectedIndex on tissueOrigin.
    //Otherwise, search through the tissueFinding control for the value selected in
    //tissueOrigin.  This would happen when the user did an alphanumeric search on the
    //tissueOrigin select control, so there isn't a 1-1 correspondence between the options
    //in the two controls.
    if (document.all.tissueFinding.length == document.all.tissueOrigin.length) {
        //alert("Both controls are fully populated, using selectedIndex");
        document.all.tissueFinding.selectedIndex = document.all.tissueOrigin.selectedIndex;
    }
    else {
        //alert("Both controls are NOT fully populated, walking tissueFinding list...");
        for (var i=0; i<document.all.tissueFinding.length; i++) {
            if (document.all.tissueFinding.options[i].value == document.all.tissueOrigin.value) {
                //alert("Found matching value at index " + i);
                document.all.tissueFinding.selectedIndex = i;
                break;
            }
        }
    }
    showOtherForList(document.all.tissueFinding.value, 'tissueFindingOther', '<%= FormLogic.OTHER_TISSUE %>');
    //set focus to tissueOrigin Other. MR 5808
    if (document.all.tissueOrigin.value == '<%= FormLogic.OTHER_TISSUE %>') {
    	document.forms[0].tissueOriginOther.focus();
    }
    //alert('leaving selectFindingTissue');
  }
}

function doSubmit() {
  var isValid = true;
  
  setActionButtonEnabling(false);

  // Check whether primary selected when there is already a primary.
  if ((document.all.primary[0].checked) && (!document.all.primary[0].defaultChecked) && (<bean:write name="path" property="primarySectionChosen"/>)) {
    isValid = confirm("You have chosen this Pathology Report Section to be the primary section, while a primary section has already been chosen for this Pathology Report.  Do you want to continue to save this section as the primary and change the current primary section to a secondary section?  (Press OK to save and Cancel to cancel the save)");
    if (!isValid) {
      setActionButtonEnabling(true);
      return;
    }
  }

  // Check whether same origin and finding tissue selected for metastatic diagnosis.
  var dx = document.all.diagnosis.value;
  if (dx.search(/\S*\<%= FormLogic.METASTATIC_CODE %>\s?\d?/) == 0) {  // dx is metastatic
    if (document.all.tissueOrigin.value == document.all.tissueFinding.value) {
      isValid = confirm("You have chosen the same Section Tissue of Origin of Diagnosis and Section Site of Finding for a metastatic Section Diagnosis.  Do you want to continue to save your changes?  (Press OK to save and Cancel to cancel the save)");
      if (!isValid) {
        setActionButtonEnabling(true);
        return;
      }
    }
  }
 
  // Check whether all min path data fields have a value.
  var mpFieldsCount = minPathFields.getCount();
  var emptyFields = "";
  if (mpFieldsCount > 0) {
    for (var i = 0; i < mpFieldsCount; i++) {
      var mpField = minPathFields.getField(i);
      if ((document.all[mpField.getName()] == null) || (document.all[mpField.getName()].value == mpField.getEmptyValue())) {
        if (emptyFields != "") emptyFields += ", ";
        emptyFields += "'" + mpField.getLabel() + "'";
      }
    }
  }
<logic:equal name="section" property="displayLymphNodeStage" value="true">
  if (currentLymphNodeStageList == 'g0') {
    if ((document.all.lymphNodeStageG0 == null) || (document.all.lymphNodeStageG0.value == "")) {
      if (emptyFields != "") emptyFields += ", ";
      emptyFields += "'Lymph Node Stage'";
    }
  }
  else {
    if ((document.all.lymphNodeStage == null) || (document.all.lymphNodeStage.value == "")) {
      if (emptyFields != "") emptyFields += ", ";
      emptyFields += "'Lymph Node Stage'";
    }
  }
</logic:equal>
  
<logic:equal name="section" property="displayTumorSize" value="true">
  if (!document.all.tumorSizeNS.checked 
     && (document.all.tumorSize1.value == "")  
     && (document.all.tumorSize2.value == "")  
     && (document.all.tumorSize3.value == "")
     && !document.all.tumorWeightNS.checked
     && (document.all.tumorWeight.value == "")) {  
    if (emptyFields != "") emptyFields += ", ";
    emptyFields += "'Tissue Size' or 'Tissue Weight'";
  }
</logic:equal>
  if (emptyFields != "") {
    isValid = confirm("A value has not been entered for the following minimum pathology data fields: " + emptyFields + ".  Do you want to continue to save this section?  (Press OK to save and Cancel to cancel the save)");
    if (!isValid) {
      setActionButtonEnabling(true);
      return;
    }
  }

  // Only submit if all validation checks passed.
  if (isValid) {
    document.all.pathSection.submit();
  }
  else {
    setActionButtonEnabling(true);
  }
}

function doReset() {
  setActionButtonEnabling(false);
  document.pathSection.reset();
  setActionButtonEnabling(true);
}

function doCancel(gotoURL) {
  setActionButtonEnabling(false);
  window.location.href = gotoURL;
}
   
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.pathSection;
  f.btnSubmit.disabled = isDisabled;
  f.btnReset.disabled = isDisabled;
  f.btnCancel.disabled = isDisabled;
}
</script>
</head>

<body class="bigr" onLoad="initPage();">
  <form name="pathSection" method="POST" action="<html:rewrite page="/ddc/Dispatch"/>">
  <input type="hidden" name="op" value="PathSectionEdit">
  <html:hidden name="path" property="pathReportId"/>
  <html:hidden name="path" property="consentId"/>
  <html:hidden name="section" property="pathReportSectionId"/>
  <html:hidden name="section" property="gleasonTotal"/>
  <html:hidden name="path" property="donorImportedYN"/>

  <div align="center">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" border="0" cellpadding="3" cellspacing="1">
            <bean:write name="section" property="messages" filter="false"/>
          </table>
        </td>
      </tr>
    </table>
  </div>
  <p>
  
  <div align="center">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" border="0" cellpadding="3" cellspacing="1">
            <tr class="white"> 
              <td class="yellow" align="right"><b>Donor</b></td>
              <td>
              	<html:link page="/ddc/Dispatch" name="path" property="donorLinkParams">
                <bean:write name="path" property="ardaisId"/>
                </html:link>
                <logic:notEmpty name="path" property="donorCustomerId">
                  (<bean:write name="path" property="donorCustomerId" />)
                </logic:notEmpty>
              </td>
            </tr>
            <tr class="white">
              <td class="yellow" align="right"><b>Case</b></td>
              <td>
              	<html:link page="/ddc/Dispatch" name="path" property="consentLinkParams">
                <bean:write name="path" property="consentId"/>
                </html:link>
                <logic:notEmpty name="path" property="consentCustomerId">
                  (<bean:write name="path" property="consentCustomerId" />)
                </logic:notEmpty>
              </td>
            </tr>
            <tr class="white">
              <td class="yellow" align="right"><b>Disease Type</b></td>
              <td><bean:write name="path" property="diseaseName"/></td>
            </tr>
            <tr class="white">
              <td colspan="6" align="center">
                <logic:present name="path" property="rawPathReport">
                  <span class="fakeLink" onclick="window.open('<html:rewrite page="/ddc/Dispatch" name="path" property="rawPopupLinkParams"/>', 'RawPathReport','width=600,height=600,top=100,left=100,resizable=yes,scrollbars=yes,status=yes');">
                  Show Full Text Pathology Report</span>
                </logic:present>
                <logic:notPresent name="path" property="rawPathReport">
                  &lt;The Full Text Pathology Report has not been entered&gt;                                
                </logic:notPresent>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </div>

  <p><table class="background" cellpadding="0" cellspacing="0" border="0" width="100%">
    <tr><td> 
      <table class="foreground" border="0" cellpadding="3" cellspacing="1" width="100%">

        <tr class="white"> 
          <td class="grey" align="right">Section Identifier <font color="red">*</font></td>
          <td><bigr:selectList name="section" property="sectionIdentifier" legalValueSetProperty="sectionIdentifierList"  firstValue=" " firstDisplayValue="Select Section"/></td> 
          <td class="grey" align="right">Section Type <font color="red">*</font></td>
          <td>
            <html:radio name="section" property="primary" value="Y"/>Primary
            <html:radio name="section" property="primary" value="N"/>Secondary
          </td> 
        </tr>

        <bigr:diagnosisWithOther name="section"
          property="diagnosis" propertyLabel="Section Diagnosis from DI Pathology Report"
          otherProperty="diagnosisOther" otherPropertyLabel="Other Section Diagnosis from DI Pathology Report"
          otherPropertySize="80"
          required="true" colspan="3"
          firstValue="" firstDisplayValue="Select Diagnosis"
          includeAlphaLookup="true"/>
        <logic:equal name="section" property="minPathDiagnosis" value="true">
          <script language="JavaScript">minPathFields.addField("diagnosis", "Section Diagnosis from DI Pathology Report", "");</script>
        </logic:equal>

        <bigr:tissueWithOther name="section"
          property="tissueOrigin" propertyLabel="Section Tissue of Origin of Diagnosis"
          otherProperty="tissueOriginOther" otherPropertyLabel="Other Section Tissue of Origin of Diagnosis"
          required="true" colspan="3"
          firstValue="" firstDisplayValue="Select Tissue"
          includeAlphaLookup="true"
          onChange="selectFindingTissue();"/>
        <logic:equal name="section" property="minPathTissueOrigin" value="true">
          <script language="JavaScript">minPathFields.addField("tissueOrigin", "Section Tissue of Origin of Diagnosis", "");</script>
        </logic:equal>

        <bigr:tissueWithOther name="section"
          property="tissueFinding" propertyLabel="Section Site of Finding"
          otherProperty="tissueFindingOther" otherPropertyLabel="Other Section Site of Finding"
          required="true" colspan="3"
          firstValue="" firstDisplayValue="Select Tissue"
          includeAlphaLookup="true"/>
        <logic:equal name="section" property="minPathTissueFinding" value="true">
          <script language="JavaScript">minPathFields.addField("tissueFinding", "Section Site of Finding", "");</script>
        </logic:equal>

        <logic:equal name="section" property="displayTumorSize" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathTumorSize" trueValue="grey minPath" falseValue="grey"/>"> 
              Diseased Tissue Size (cm x cm x cm) </td>
            <td colspan="3">
              <html:text name="section" property="tumorSize1" size="6" maxlength="5" onblur="checkTissueSize();"/>
              x <html:text name="section" property="tumorSize2" size="6" maxlength="5" onblur="checkTissueSize();"/>
              x <html:text name="section" property="tumorSize3" size="6" maxlength="5" onblur="checkTissueSize();"/>
              &nbsp;&nbsp;&nbsp;<html:checkbox name="section" property="tumorSizeNS" value="true" onclick="toggleTissueSize();"/>Not Specified
            </td> 
          </tr>
        </logic:equal>

        <logic:equal name="section" property="displayTumorWeight" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathTumorWeight" trueValue="grey minPath" falseValue="grey"/>"> 
              Diseased Tissue Weight (gm) </td>
            <td colspan="3">
              <html:text name="section" property="tumorWeight" size="8" maxlength="7" onblur="checkTissueWeight();"/>
              &nbsp;&nbsp;&nbsp;<html:checkbox name="section" property="tumorWeightNS" value="true" onclick="toggleTissueWeight();"/>Not Specified
            </td> 
          </tr>
        </logic:equal>

        <logic:equal name="section" property="displayJointComponent" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathJointComponent" trueValue="grey minPath" falseValue="grey"/>">
              Joint Component
            </td>
            <td colspan="3">
              <bigr:selectList name="section" property="jointComponent" legalValueSetProperty="jointComponentList" firstValue="" firstDisplayValue="Select Joint Component"/>
            </td> 
          </tr>
          <logic:equal name="section" property="minPathJointComponent" value="true">
            <script language="JavaScript">minPathFields.addField("jointComponent", "Joint Component", "");</script>
          </logic:equal>
        </logic:equal>

         <logic:equal name="section" property="displayHistologicalType" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathHistologicalType" trueValue="grey minPath" falseValue="grey"/>"> 
              Histologic Type </td>
            <td colspan="3">            
              <bigr:selectListWithOther name="section"
                property="histologicalType" otherProperty="histologicalTypeOther" 
                legalValueSetProperty="histologicalTypeList" 
                firstValue="" firstDisplayValue="Select Type"
                otherCode="<%= FormLogic.OTHER_HISTOLOGICAL_TYPE %>"/>
            </td>
          </tr>
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathHistologicalTypeOther" trueValue="grey minPath" falseValue="grey"/>"> 
              Other Histologic Type </td>
            <td colspan="3">            
              <bigr:otherText name="section" property="histologicalTypeOther" 
              	parentProperty="histologicalType" otherCode="<%= FormLogic.OTHER_HISTOLOGICAL_TYPE %>" size="80" maxlength="200"/>
            </td>
          </tr>
          <logic:equal name="section" property="minPathHistologicalType" value="true">
            <script language="JavaScript">minPathFields.addField("histologicalType", "Histologic Type", "");</script>
          </logic:equal>
        </logic:equal>

        <logic:equal name="section" property="displayHistologicalNuclearGrade" value="true">  
          <logic:equal name="section" property="displayHistologicalNuclearGradeOther" value="true">  
            <tr class="white"> 
              
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathHistologicalNuclearGrade" trueValue="grey minPath" falseValue="grey"/>"> 
              Histologic/Nuclear Grade </td>
              <td colspan="3">
                <bigr:selectListWithOther name="section"
                  property="histologicalNuclearGrade" otherProperty="histologicalNuclearGradeOther" 
                  legalValueSetProperty="histologicalNuclearGradeList" 
                  firstValue="" firstDisplayValue="Select Grade"
                  otherCode="CA00356C"/>
              </td>
            </tr>            
            <tr class="white"> 
              
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathHistologicalNuclearGradeOther" trueValue="grey minPath" falseValue="grey"/>"> 
              Other Histologic/Nuclear Grade </td>
              <td colspan="3">            
                <bigr:otherText name="section" property="histologicalNuclearGradeOther" 
                	parentProperty="histologicalNuclearGrade" otherCode="CA00356C" size="50" maxlength="200"/>
              </td>
            </tr>
          </logic:equal>
          <logic:notEqual name="section" property="displayHistologicalNuclearGradeOther" value="true">  
            <tr class="white"> 
              
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathHistologicalNuclearGrade" trueValue="grey minPath" falseValue="grey"/>"> 
              Histologic/Nuclear Grade </td>
              <td colspan="3">
                <bigr:selectList name="section"
                  property="histologicalNuclearGrade" 
                  legalValueSetProperty="histologicalNuclearGradeList" 
                  firstValue="" firstDisplayValue="Select Grade"/>
              </td>
            </tr>            
          </logic:notEqual>
          <logic:equal name="section" property="minPathHistologicalNuclearGrade" value="true">
            <script language="JavaScript">minPathFields.addField("histologicalNuclearGrade", "Histologic Nuclear Grade", "");</script>
          </logic:equal>
        </logic:equal>

        <logic:equal name="section" property="displayGleason" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathGleason" trueValue="grey minPath" falseValue="grey"/>">
              Gleason Score
            </td>
            <td colspan="3">
              Primary Score
              <bigr:selectList 
                name="section" property="gleasonPrimary" 
                legalValueSetProperty="gleasonPrimaryList" 
                firstValue=" " firstDisplayValue="Select Score" 
                onchange="calculateGleasonTotal();"/>
              &nbsp;&nbsp;&nbsp;Secondary Score
              <bigr:selectList 
                name="section" property="gleasonSecondary" 
                legalValueSetProperty="gleasonSecondaryList" 
                firstValue=" " firstDisplayValue="Select Score"
                onchange="calculateGleasonTotal();"/>
              &nbsp;&nbsp;&nbsp;Total Score:&nbsp;
              <span id="gleasonTotalSpan">
                <bean:write name="section" property="gleasonTotal"/>
              </span>
            </td>
          </tr>
          <logic:equal name="section" property="minPathGleason" value="true">
            <script language="JavaScript">minPathFields.addField("gleasonTotal", "Gleason Score", "");</script>
          </logic:equal>
        </logic:equal>

        <logic:equal name="section" property="displayPerineuralInvasion" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathPerineuralInvasion" trueValue="grey minPath" falseValue="grey"/>">
              Perineural Invasion Indicator
            </td>
            <td colspan="3">
              <bigr:selectList name="section" property="perineuralInvasion" legalValueSetProperty="perineuralInvasionList" firstValue="" firstDisplayValue="Select Invasion"/>
            </td>
          </tr>
          <logic:equal name="section" property="minPathPerineuralInvasion" value="true">
            <script language="JavaScript">minPathFields.addField("perineuralInvasion", "Perineural Invasion Indicator", "");</script>
          </logic:equal>
        </logic:equal>

        <logic:equal name="section" property="displayLymphaticVascularInvasion" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathLymphaticVascularInvasion" trueValue="grey minPath" falseValue="grey"/>">
              Lymphatic Vascular Invasion
            </td>
            <td colspan="3">
              <bigr:selectList name="section" property="lymphaticVascularInvasion" legalValueSetProperty="lymphaticVascularInvasionList" firstValue="" firstDisplayValue="Select Invasion"/>
            </td>
          </tr>
          <logic:equal name="section" property="minPathLymphaticVascularInvasion" value="true">
            <script language="JavaScript">minPathFields.addField("lymphaticVascularInvasion", "Lymphatic Vascular Invasion", "");</script>
          </logic:equal>
        </logic:equal>

        <logic:equal name="section" property="displayMargins" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathMarginsInvolved" trueValue="grey minPath" falseValue="grey"/>">
              Margins Involved by Tumor
            </td>
            <td><html:textarea name="section" property="marginsInvolved" rows="3" cols="25" onkeyup="maxTextarea(200);"/></td>
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathMarginsUninvolved" trueValue="grey minPath" falseValue="grey"/>"> 
              Margins Uninvolved (Include Distance) </td>
            <td><html:textarea name="section" property="marginsUninvolved" rows="3" cols="25" onkeyup="maxTextarea(200);"/></td>
          </tr>
          <logic:equal name="section" property="minPathMarginsInvolved" value="true">
            <script language="JavaScript">minPathFields.addField("marginsInvolved", "Margins Involved by Tumor", "");</script>
          </logic:equal>
          <logic:equal name="section" property="minPathMarginsUninvolved" value="true">
            <script language="JavaScript">minPathFields.addField("marginsUninvolved", "Margins Uninvolved (incl Distance)", "");</script>
          </logic:equal>
        </logic:equal>

        <logic:equal name="section" property="displayCellInfiltrate" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathInflammCellInfiltrate" trueValue="grey minPath" falseValue="grey"/>">
              Type of Inflammatory Cell Infiltrate
            </td>
            <td>
              <bigr:selectList name="section" property="inflammCellInfiltrate" legalValueSetProperty="inflammCellInfiltrateList" firstValue=" " firstDisplayValue="Select Infiltrate"/>
            </td> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathCellInfiltrateAmount" trueValue="grey minPath" falseValue="grey"/>">
              Cell Infiltrate Amount
            </td>
            <td> 
              <bigr:selectList name="section" property="cellInfiltrateAmount" legalValueSetProperty="cellInfiltrateAmountList" firstValue="" firstDisplayValue="Select Amount"/>
            </td>
          </tr>
          <logic:equal name="section" property="minPathInflammCellInfiltrate" value="true">
            <script language="JavaScript">minPathFields.addField("cellInfiltrateAmount", "Type of Inflammatory Cell Infiltrate", "");</script>
          </logic:equal>
        </logic:equal>

        <logic:equal name="section" property="displayTumorStageType" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathTumorStageType" trueValue="grey minPath" falseValue="grey"/>"> 
              Tumor Stage Classification </td>
            <td colspan="3">            
              <bigr:selectListWithOther name="section"
                property="tumorStageType" otherProperty="tumorStageTypeOther" 
                legalValueSetProperty="tumorStageTypeList"
                firstValue="" firstDisplayValue="Select Type"
                onchange="processAnnArborSelection(this.value, document.all.tumorStageDesc);processAnnArborSelection(this.value, document.all.lymphNodeStage);processAnnArborSelection(this.value, document.all.distantMetastasis);"
                otherCode="<%= FormLogic.OTHER_TUMOR_STAGE_TYPE %>">
                <bigr:childProperty property="tumorStageDesc"/>
                <bigr:childProperty property="lymphNodeStage"/>
                <bigr:childProperty property="distantMetastasis"/>
                <bigr:childProperty property="stageGrouping"/>
              </bigr:selectListWithOther>
            </td>
          </tr>
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathTumorStageTypeOther" trueValue="grey minPath" falseValue="grey"/>"> 
              Other Tumor Stage Classification </td>
            <td colspan="3">            
              <bigr:otherText name="section" property="tumorStageTypeOther" 
              	parentProperty="tumorStageType" otherCode="<%= FormLogic.OTHER_TUMOR_STAGE_TYPE %>" size="50" maxlength="200"/>
            </td>
          </tr>
          <logic:equal name="section" property="minPathTumorStageType" value="true">
            <script language="JavaScript">minPathFields.addField("tumorStageType", "Tumor Stage Classification System", "");</script>
          </logic:equal>
        </logic:equal>

        <logic:equal name="section" property="displayTumorStageDesc" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathTumorStageDesc" trueValue="grey minPath" falseValue="grey"/>">
              Tumor Stage
            </td>
            <td> 
              <bigr:selectListWithOther name="section" 
                property="tumorStageDesc" otherProperty="tumorStageDescOther"
                legalValueSetProperty="tumorStageDescList"
                parentProperty="tumorStageType"
                firstValue="" firstDisplayValue="Select Stage"
                onchange="<%=enableStageIndTumor%>"
                otherCode="<%= FormLogic.OTHER_TUMOR_STAGE_DESC %>"/>
            </td>
            <td colspan="2"> 
              <label id="tumorStageDescIndLabel">
                <html:radio name="section" property="tumorStageDescInd" value="R"/>Reported
              </label>&nbsp;&nbsp;
              <label id="tumorStageDescIndLabel">
                <html:radio name="section" property="tumorStageDescInd" value="D"/>Derived
              </label>
            </td>
          </tr>
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathTumorStageDescOther" trueValue="grey minPath" falseValue="grey"/>">
              Other Tumor Stage
            </td>
            <td colspan="3">            
              <bigr:otherText name="section" property="tumorStageDescOther" 
              	parentProperty="tumorStageDesc" otherCode="<%= FormLogic.OTHER_TUMOR_STAGE_DESC %>" size="50" maxlength="200"/>
            </td>
          </tr>
          <logic:equal name="section" property="minPathTumorStageDesc" value="true">
            <script language="JavaScript">minPathFields.addField("tumorStageDesc", "Tumor Stage", "");</script>
          </logic:equal>
        </logic:equal>

        <logic:equal name="section" property="displayVenousVesselInvasion" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathVenousVesselInvasion" trueValue="grey minPath" falseValue="grey"/>">
              Venous Vessel Invasion
            </td>
            <td colspan="3">
              <bigr:selectList name="section" property="venousVesselInvasion" legalValueSetProperty="venousVesselInvasionList" firstValue=" " firstDisplayValue="Select Invasion"/>
            </td> 
          </tr>
          <logic:equal name="section" property="minPathVenousVesselInvasion" value="true">
            <script language="JavaScript">minPathFields.addField("venousVesselInvasion", "Venous Vessel Invasion", "");</script>
          </logic:equal>
        </logic:equal>

        <logic:equal name="section" property="displayTumorConfig" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathTumorConfig" trueValue="grey minPath" falseValue="grey"/>">
              Tumor Configuration
            </td>
            <td colspan="3"> 
              <bigr:selectList name="section" property="tumorConfig" legalValueSetProperty="tumorConfigList" firstValue=" " firstDisplayValue="Select Configuration"/>
            </td>
          </tr>
          <logic:equal name="section" property="minPathTumorConfig" value="true">
            <script language="JavaScript">minPathFields.addField("tumorConfig", "Tumor Configuration", "");</script>
          </logic:equal>
        </logic:equal>

        <logic:equal name="section" property="displayExtensiveIntraductalComp" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathExtensiveIntraductalComp" trueValue="grey minPath" falseValue="grey"/>">
              Extensive Intraductal Component
            </td>
            <td colspan="3"> 
              <bigr:selectList name="section" property="extensiveIntraductalComp" legalValueSetProperty="extensiveIntraductalCompList" firstValue=" " firstDisplayValue="Select Component"/>
            </td>
          </tr>
          <logic:equal name="section" property="minPathExtensiveIntraductalComp" value="true">
            <script language="JavaScript">minPathFields.addField("extensiveIntraductalComp", "Extensive Intraductal Component", "");</script>
          </logic:equal>
        </logic:equal>

        <logic:equal name="section" property="displayExtranodal" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathExtranodalSpread" trueValue="grey minPath" falseValue="grey"/>">
              Extranodal Spread
            </td>
            <td>
              <bigr:selectList name="section" property="extranodalSpread" legalValueSetProperty="extranodalSpreadList" firstValue="" firstDisplayValue="Select Spread"/>
            </td> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathSizeLargestNodalMets" trueValue="grey minPath" falseValue="grey"/>">
              Size of Largest Nodal Metastasis
            </td>
            <td>
              <html:text name="section" property="sizeLargestNodalMets" size="15" maxlength="60"/>
            </td>
          </tr>
          <logic:equal name="section" property="minPathExtranodalSpread" value="true">
            <script language="JavaScript">minPathFields.addField("extranodalSpread", "Extranodal Spread", "");</script>
          </logic:equal>
        </logic:equal>

        <logic:equal name="section" property="displayLymphNodeNotes" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathLymphNodeNotes" trueValue="grey minPath" falseValue="grey"/>">
              Lymph Node Notes
            </td>
            <td colspan="3">
              <html:textarea name="section" property="lymphNodeNotes" rows="4" cols="80" onkeyup="maxTextarea(500);"/>
            </td>
          </tr>
          <logic:equal name="section" property="minPathLymphNodeNotes" value="true">
            <script language="JavaScript">minPathFields.addField("lymphNodeNotes", "Lymph Node Notes", "");</script>
          </logic:equal>
        </logic:equal>

        <logic:equal name="section" property="displayTotalNodes" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathTotalNodesExamined" trueValue="grey minPath" falseValue="grey"/>">
              Total Number of Nodes Examined
            </td>
            <td>
              <bigr:selectList 
                name="section" property="totalNodesExamined" 
                legalValueSetProperty="totalNodesExaminedList" 
                firstValue=" " firstDisplayValue="Select Total"
                onchange="checkNodeTotals();"/>
            </td>
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathTotalNodesPositive" trueValue="grey minPath" falseValue="grey"/>">
              Total Number of Positive Nodes
            </td>
            <td> 
              <bigr:selectList 
                name="section" property="totalNodesPositive" 
                legalValueSetProperty="totalNodesPositiveList" 
                firstValue=" " firstDisplayValue="Select Total"
                onchange="checkNodeTotals();"/>
            </td>
          </tr>
          <logic:equal name="section" property="minPathTotalNodesExamined" value="true">
            <script language="JavaScript">minPathFields.addField("totalNodesExamined", "Total Number of Nodes Examined", "");</script>
          </logic:equal>
          <logic:equal name="section" property="minPathTotalNodesPositive" value="true">
            <script language="JavaScript">minPathFields.addField("totalNodesPositive", "Total Number of Positive Nodes", "");</script>
          </logic:equal>
        </logic:equal>

        <logic:equal name="section" property="displayLymphNodeStage" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathLymphNodeStage" trueValue="grey minPath" falseValue="grey"/>">
              Lymph Node Stage
            </td>
            <td>
              <div style="display: none" id="lymphNodeStage1">
                <bigr:selectListWithOther name="section" 
                  property="lymphNodeStage" otherProperty="lymphNodeStageOther" 
                  legalValueSetProperty="lymphNodeStageList" 
                  parentProperty="tumorStageType"
                  firstValue="" firstDisplayValue="Select Stage"
                  onchange="<%=enableStageIndLymph%>"
                  otherCode="<%= FormLogic.OTHER_LYMPH_NODE_STAGE_DESC %>"/>
              </div>
            </td>
            <td colspan="2"> 
              <label id="lymphNodeStageIndLabel">
                <html:radio name="section" property="lymphNodeStageInd" value="R"/>Reported&nbsp;&nbsp;
              </label>
              <label id="lymphNodeStageIndLabel">
                <html:radio name="section" property="lymphNodeStageInd" value="D"/>Derived
              </label>
            </td>
          </tr>
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathLymphNodeStageOther" trueValue="grey minPath" falseValue="grey"/>">
              Other Lymph Node Stage
            </td>
            <td colspan="3">            
              <bigr:otherText name="section" property="lymphNodeStageOther" 
              	parentProperty="lymphNodeStage" otherCode="<%= FormLogic.OTHER_LYMPH_NODE_STAGE_DESC %>" size="50" maxlength="200"/>
            </td>
          </tr>
        </logic:equal>

        <logic:equal name="section" property="displayDistantMetastasis" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathDistantMetastasis" trueValue="grey minPath" falseValue="grey"/>">
              Distant Metastasis
            </td>
            <td>
              <bigr:selectListWithOther name="section" 
                property="distantMetastasis" otherProperty="distantMetastasisOther" 
                legalValueSetProperty="distantMetastasisList" 
                parentProperty="tumorStageType"
                firstValue="" firstDisplayValue="Select Metastasis"
                onchange="<%=enableStageIndMets%>"
                otherCode="<%= FormLogic.OTHER_DISTANT_METASTASIS %>"/>
            </td>
            <td colspan="2"> 
              <label id="distantMetastasisIndLabel">
                <html:radio name="section" property="distantMetastasisInd" value="R"/>Reported
              </label>
              <label id="distantMetastasisIndLabel">&nbsp;&nbsp;
                <html:radio name="section" property="distantMetastasisInd" value="D"/>Derived
              </label>
            </td>
          </tr>
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathDistantMetastasisOther" trueValue="grey minPath" falseValue="grey"/>">
              Other Distant Metastasis
            </td>
            <td colspan="3">            
              <bigr:otherText name="section" property="distantMetastasisOther" 
              	parentProperty="distantMetastasis" otherCode="<%= FormLogic.OTHER_DISTANT_METASTASIS %>" size="50" maxlength="200"/>
            </td>
          </tr>
          <logic:equal name="section" property="minPathDistantMetastasis" value="true">
            <script language="JavaScript">minPathFields.addField("distantMetastasis", "Distant Metastasis", "");</script>
          </logic:equal>
        </logic:equal>

        <logic:equal name="section" property="displayStageGrouping" value="true">
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathStageGrouping" trueValue="grey minPath" falseValue="grey"/>">
              Minimum Stage Grouping
            </td>
            <td colspan="3">            
              <bigr:selectListWithOther name="section"
                property="stageGrouping" otherProperty="stageGroupingOther" 
                legalValueSetProperty="stageGroupingList" 
                parentProperty="tumorStageType"
                firstValue="" firstDisplayValue="Select Grouping"
                otherCode="<%= FormLogic.OTHER_STAGE_GROUPING %>"/>
            </td>
          </tr>
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathStageGroupingOther" trueValue="grey minPath" falseValue="grey"/>">
              Other Minimum Stage Grouping
            </td>
            <td colspan="3">            
              <bigr:otherText name="section" property="stageGroupingOther" 
              	parentProperty="stageGrouping" otherCode="<%= FormLogic.OTHER_STAGE_GROUPING %>" size="50" maxlength="200"/>
            </td>
          </tr>
          <logic:equal name="section" property="minPathStageGrouping" value="true">
            <script language="JavaScript">minPathFields.addField("stageGrouping", "Minimum Stage Grouping", "");</script>
          </logic:equal>
        </logic:equal>

        <tr class="white"> 
          <td align="right" class="<bigr:conditionalWrite name="section" property="minPathSectionNotes" trueValue="grey minPath" falseValue="grey"/>">
            Pathology Section Notes
          </td>
          <td colspan="3">
            <html:textarea name="section" property="sectionNotes" rows="4" cols="80"/>
          </td>
          <logic:equal name="section" property="minPathSectionNotes" value="true">
            <script language="JavaScript">minPathFields.addField("sectionNotes", "Pathology Section Notes", "");</script>
          </logic:equal>
        </tr>
            
        <tr class="white"> 
          <td colspan="4">
            <div align="center"> 
							<logic:equal name="section" property="new" value="true">
              	<input type="button" name="btnSubmit" value="Save" onclick="doSubmit();"> 
							</logic:equal>                   
							<logic:notEqual name="section" property="new" value="true">
              	<input type="button" name="btnSubmit" value="Update" onclick="doSubmit();"> 
							</logic:notEqual>                   
              <input type="button" value="Reset" name="btnReset" onclick="doReset();"/>
              <logic:equal name="section" property="new" value="true">
                <input type="button" value="Cancel" name="btnCancel"
                  onclick="doCancel('<html:rewrite page="/ddc/Dispatch" name="path" property="consentLinkParams"/>');">
              </logic:equal>                    
              <logic:notEqual name="section" property="new" value="true">
                <input type="button" value="Cancel" name="btnCancel"
                  onclick="doCancel('<html:rewrite page="/ddc/Dispatch" name="section" property="sectionLinkParams"/>');">
              </logic:notEqual>
            </div>
            <font color="Red">*</font> indicates a required field                    
          </td>
        </tr>
      </table>
    </td></tr>
  </table></p>
  </form>
</body>
</html>
