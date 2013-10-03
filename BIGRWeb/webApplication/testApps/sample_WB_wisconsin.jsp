

<html>
<head>
<title>Create Sample</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 
 
<link rel="stylesheet" type="text/css" href="/BIGR/hiddenJsps/kc/css/kc.css" />
 
<script type="text/javascript" src="/BIGR/hiddenJsps/kc/js/prototype.js"></script>
<script type="text/javascript" src="/BIGR/hiddenJsps/kc/js/gsbio.js"></script>
<script type="text/javascript" src="/BIGR/hiddenJsps/kc/js/gsbioUtil.js"></script>
<script type="text/javascript" src="/BIGR/hiddenJsps/kc/js/tabs.js"></script>
<script type="text/javascript" src="/BIGR/hiddenJsps/kc/data/js/dataElement.js"></script>
<script type="text/javascript" src="/BIGR/hiddenJsps/kc/data/js/calendar.js"></script>
<script type="text/javascript"> 
GSBIO.contextPath = '/BIGR';
</script>
 
 
<link rel="stylesheet" href="/BIGR/css/bigr.css" type="text/css">
<script language="JavaScript" src="/BIGR/js/common.js"></script>
<script language="JavaScript" src="/BIGR/js/bigrTextArea.js"></script>
<script language="JavaScript" src="/BIGR/js/calendar.js"></script>
<script language="JavaScript" src="/BIGR/js/registration.js"></script>
<script type="text/javascript"> 
 
var myBanner = 'Create Sample';
 
function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
  if (GSBIO.kc.data.FormInstances.getFormInstance()) {    
    GSBIO.kc.data.FormInstances.getFormInstance().alternateRowColor();
  }
 
    GSBIO.bigr.reg.RegFormIds['CA09400C'] = 'FD00000114';
 
    GSBIO.bigr.reg.RegFormIds['CA11725C'] = 'FD00000421';
 
    GSBIO.bigr.reg.RegFormIds['CA00052C'] = 'FD00000117';
 
    GSBIO.bigr.reg.RegFormIds['CA09563C'] = 'FD00000115';
 
    GSBIO.bigr.reg.RegFormIds['CA11117C'] = 'FD00000115';
 
    GSBIO.bigr.reg.RegFormIds['CA09402C'] = 'FD00000119';
 
    GSBIO.bigr.reg.RegFormIds['CA09556C'] = 'FD00000118';
 
    GSBIO.bigr.reg.RegFormIds['CA11144C'] = 'FD00000115';
 
    GSBIO.bigr.reg.RegFormIds['CA00011C'] = 'FD00000115';
 
  GSBIO.bigr.reg.CurrentSampleTypeCui = $F('sampleData.sampleTypeCui');
	var c = $('generateSampleIdCheckbox');
	if (c && c.checked) {
		$('sampleData.sampleAlias').focus();
	}
	else {
		$('sampleData.sampleId').focus();
	}
 
//	if no value has been selected in either of the print label drop downs, auto select any single choices
	if (document.forms[0].printerName.value == "" 
	  && document.forms[0].templateDefinitionName.value == "") {
	  //if there is only one choice (other than "Select") in the label template drop-down, select it
	  selectSingleDropDownChoice(document.forms[0].printerName, 2);
	  //if there is only one choice (other than "Select") in the label printer drop-down, select it
	  selectSingleDropDownChoice(document.forms[0].templateDefinitionName, 2)
	}
	enablePrintLabelControls();
 
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
 
  if (selectedSampleType == 'CA12312C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA12592C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA00052C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA12316C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA12602C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA12589C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA09556C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA12314C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA11144C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA12599C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA12311C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA09400C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
      document.forms[0].templateDefinitionName.options[3]=new Option('Vial Wraparound Label', 'Vial Wraparound Label');
 
  }
 
  if (selectedSampleType == 'CA12594C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA12591C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA12315C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA00017C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA11143C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA12603C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA12593C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA12604C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA12601C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA11725C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA12605C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA12600C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA09563C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA11117C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA09402C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA12590C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA12595C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA12606C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA00049C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA12504C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA12310C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA00011C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA09582C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA00008C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA12313C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA12596C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA12505C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA09572C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
  if (selectedSampleType == 'CA12607C') {
 
      document.forms[0].templateDefinitionName.options[1]=new Option('3 Up Label', '3 Up Label');
 
      document.forms[0].templateDefinitionName.options[2]=new Option('Tube Label', 'Tube Label');
 
  }
 
	//if there is only one choice (other than "Select") in the drop-down, select it
	selectSingleDropDownChoice(document.forms[0].templateDefinitionName, 2);
	updatePrinterChoices();
 
}
 
function updatePrinterChoices() {
 
  //clear the existing printer choices
  for(var i=document.forms[0].printerName.length; i>0; i--)
  {
    document.forms[0].printerName.remove(i);    
  }
  
  //populate new choices based on the selected template
  var selectedTemplate = $('templateDefinitionName').value;
 
  if (selectedTemplate == '3 Up Label') {
 
    document.forms[0].printerName.options[1]=new Option('HP5000 by the kitchen', '\\\\spurge\\HP5000DN_1');
 
    document.forms[0].printerName.options[2]=new Option('Zebra printer 1', 'Zebra 110XiIII Plus (600 dpi)');
 
  }
 
  if (selectedTemplate == 'Tube Label') {
 
    document.forms[0].printerName.options[1]=new Option('HP5000 by the kitchen', '\\\\spurge\\HP5000DN_1');
 
    document.forms[0].printerName.options[2]=new Option('Zebra printer 1', 'Zebra 110XiIII Plus (600 dpi)');
 
  }
 
  if (selectedTemplate == 'Vial Wraparound Label') {
 
    document.forms[0].printerName.options[1]=new Option('HP5000 by the kitchen', '\\\\spurge\\HP5000DN_1');
 
    document.forms[0].printerName.options[2]=new Option('Zebra printer 1', 'Zebra 110XiIII Plus (600 dpi)');
 
  }
 
	//if there is only one choice (other than "Select") in the drop-down, select it
	selectSingleDropDownChoice(document.forms[0].printerName, 2);
 
}
 
function enablePrintLabelControls() {
 
  $('templateDefinitionName').disabled = !document.all.printSampleLabelCB.checked;
  $('printerName').disabled = !document.all.printSampleLabelCB.checked;
  $('labelCount').disabled = !document.all.printSampleLabelCB.checked;
 
}
</script>
</head>
<body class="bigr" onload="initPage();">
<form name="sampleForm" method="POST" action="/BIGR/dataImport/createSample.do" onsubmit="return(onFormSubmit());" id="theForm">
  <input type="hidden" name="sampleData.ardaisAcctKey" value="ARD0000001">
  <input type="hidden" name="sampleData.importedYN" value="Y">
  <input type="hidden" name="rememberedData" value="">
  <input type="hidden" name="newDonor" value="">
  <input type="hidden" name="newCase" value="">
  <input type="hidden" name="linkedIndicator" value="">
  <input type="hidden" name="createForm" value="false">
  <input type="hidden" name="sampleData.ardaisId" value="">
  <input type="hidden" name="sampleData.donorAlias" value="JMTEST1">
  <input type="hidden" name="sampleData.consentId" value="">
  <input type="hidden" name="sampleData.consentAlias" value="JMTEST2">
  <input type="hidden" name="policyId" value="261">
  <input type="hidden" name="consentVersionId" value="782">
  <input type="hidden" name="form" value=""/>
 
<div id="errorsDiv" class="errorsMessages">
 
</div>
<div id="messagesDiv" class="errorsMessages">
 <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" border="0" cellpadding="3" cellspacing="1">
          
          </table>
        </td>
      </tr>
    </table>
  </div>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="1%" height="100%">&nbsp;</td>
        <td height="1%" width="98%">
          <div align="center" id="banner" class="banner">Create Sample 
          <br><u>Whole Blood Registration Form</u></div>
    	  </td>
        <td height="1%">
          <table class="background" cellpadding="0" cellspacing="0" border="0">
            <tr>
              <td>
              <!--   <table class="foreground" cellpadding="3" cellspacing="1" border="0">
                  <tr class="white"> 
                    <td nowrap align="center"><a href="#" onClick="openHelpWindow('/BIGR/help/help.jsp');">Help</a></td>
                    <td nowrap align="center"><a href="/BIGR/terms/terms.jsp" target="mainFrame">Terms of Use</a></td>
                  </tr>
                  <tr class="white">
                    <td colspan="3" nowrap align="center" colspan="2">
                      <a href="/BIGR/orm/DispatchLogin?op=Logout" target="_top">Logout: jmohammed (Jaweed Mohammed)</a>
                    </td>
                  </tr>
                </table>-->
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  
  <br>
</div>
 
<div class="kcElements bigrElements">
<div class="kcElement bigrElement">
<label class="inlineLabel">Donor:</label> JMTEST1
</div>
 
<div class="kcElement bigrElement">
<label class="inlineLabel">Case:</label>JMTEST2
</div>
 
<div class="kcElement bigrElement">
<label class="inlineLabel"><span class="kcRequiredInd">*</span> Sample Id:</label>
<input type="text" name="sampleData.sampleId" maxlength="10" size="15" value="" onkeydown="clearGenerateCheckbox();" onkeyup="clearGenerateCheckbox();">
 
  &nbsp;&nbsp;&nbsp;&nbsp;
  <input type="checkbox" name="generateSampleIdCheckbox" onclick="updateGenerateSampleId();" />
  &nbsp;Generate Sample Id
  <input type="hidden" name="generateSampleId" value="false">
  <input type="hidden" name="generatedSampleId" value="">
 
</div>
 
<div class="kcElement bigrElement">
<label class="inlineLabel">
 
 Sample Alias:</label>
<input type="text" name="sampleData.sampleAlias" maxlength="30" size="30" value="">
</div>
 
<div class="kcElement bigrElement">
<label class="inlineLabel"><span class="kcRequiredInd">*</span> Sample Type:</label>
<select name="sampleData.sampleTypeCui">
<option value="">Select Sample Type</option><option value="CA00011C">Buffy Coat</option><option value="CA11725C">DNA</option><option value="CA00052C" >Frozen Tissue</option><option value="CA09400C">Paraffin Tissue</option><option value="CA09402C">PBMC</option><option value="CA09556C">PBMC Pellet</option><option value="CA09563C">Plasma</option><option value="CA11117C">Serum</option><option value="CA11144C" selected="selected">Whole Blood</option></select>
</div>
 
 
<div class="kcElement bigrElement">
<label class="inlineLabel">Label Printing:</label>
  <input type="checkbox" name="printSampleLabelCB" onclick="updatePrintLabelCheckBox();enablePrintLabelControls();" />&nbsp;Print Label
  <input type="hidden" name="printSampleLabel" value="false">
  <br>
	<label class="inlineLabel">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
	Number of labels:&nbsp;&nbsp;<input type="text" name="labelCount" maxlength="2" size="5" value="1">
  <br>
	<label class="inlineLabel">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
	Label template:&nbsp;&nbsp;&nbsp;&nbsp;
				<select name="templateDefinitionName" onchange="updatePrinterChoices();"><option value="">Select</option></select>
  <br>
	<label class="inlineLabel">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
	Label printer:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<select name="printerName"><option value="">Select</option></select>
 
</div>
 
</div> <!-- kcElements -->
 
<div id="sampleTypeSpecific">
 
<script type="text/javascript">GSBIO.kc.data.FormInstances.createFormInstance({"formDefinitionId":"FD00000117","domainObjectId":"SX0001D29D"});</script>
 

<div class="kcElementsContainer">
<div class="kcElements">
 

<div class="kcElement">
<label>
 
Source:
</label>
<input type="text" name="sampleData.source" maxlength="200" size="30" value="">
</div>
<script type="text/javascript"> 
GSBIO.bigr.reg.Elements.register("sampleData.source");
</script>
 
<div class="kcElement">
<label>
 
Date of  Collection:
</label>
<input type="text" name="sampleData.collectionDateTime.date" value="" readonly="readonly">
&nbsp;
<span class="fakeLink" onclick="openCalendar('sampleData.collectionDateTime.date')">Select Date</span>
&nbsp;&nbsp;&nbsp;&nbsp;
<select name="sampleData.collectionDateTime.hour" class="DISABLED"><option value="">Hr</option>
<option value="1">1</option>
<option value="2">2</option>
<option value="3">3</option>
<option value="4">4</option>
<option value="5">5</option>
<option value="6">6</option>
<option value="7">7</option>
<option value="8">8</option>
<option value="9">9</option>
<option value="10">10</option>
<option value="11">11</option>
<option value="12">12</option>
<option value="13">13</option>
<option value="14">14</option>
<option value="15">15</option>
<option value="16">16</option>
<option value="17">17</option>
<option value="18">18</option>
<option value="19">19</option>
<option value="20">20</option>
<option value="21">21</option>
<option value="22">22</option>
<option value="23">23</option>
</select>:
<select name="sampleData.collectionDateTime.minute"><option value="">Min</option>
  <option value="00">00</option>
<option value="01">01</option>
<option value="02">02</option>
<option value="03">03</option>
<option value="04">04</option>
<option value="05">05</option>
<option value="06">06</option>
<option value="07">07</option>
<option value="08">08</option>
<option value="09">09</option>
<option value="10">10</option>
<option value="11">11</option>
<option value="12">12</option>
<option value="13">13</option>
<option value="14">14</option>
<option value="15">15</option>
<option value="16">16</option>
<option value="17">17</option>
<option value="18">18</option>
<option value="19">19</option>
<option value="20">20</option>
<option value="21">21</option>
<option value="22">22</option>
<option value="23">23</option>
<option value="24">24</option>
<option value="25">25</option>
<option value="26">26</option>
<option value="27">27</option>
<option value="28">28</option>
<option value="29">29</option>
<option value="30">30</option>
<option value="31">31</option>
<option value="32">32</option>
<option value="33">33</option>
<option value="34">34</option>
<option value="35">35</option>
<option value="36">36</option>
<option value="37">37</option>
<option value="38">38</option>
<option value="39">39</option>
<option value="40">40</option>
<option value="41">41</option>
<option value="42">42</option>
<option value="43">43</option>
<option value="44">44</option>
<option value="45">45</option>
<option value="46">46</option>
<option value="47">47</option>
<option value="48">48</option>
<option value="49">49</option>
<option value="50">50</option>
<option value="51">51</option>
<option value="52">52</option>
<option value="53">53</option>
<option value="54">54</option>
<option value="55">55</option>
<option value="56">56</option>
<option value="57">57</option>
<option value="58">58</option>
<option value="59">59</option></select>
<!-- &nbsp;
<input type="radio" name="sampleData.collectionDateTime.meridian" value="AM">AM
<input type="radio" name="sampleData.collectionDateTime.meridian" value="PM">PM-->
&nbsp;&nbsp;&nbsp;
<input type="button" name="resetCollection" value="Clear" onclick="GSBIO.bigr.reg.clearDateTime('collection');">
</div>		    
<script type="text/javascript"> 
GSBIO.bigr.reg.Elements.register("sampleData.collectionDateTime.date");
GSBIO.bigr.reg.Elements.register("sampleData.collectionDateTime.hour");
GSBIO.bigr.reg.Elements.register("sampleData.collectionDateTime.minute");
GSBIO.bigr.reg.Elements.registerRadio("sampleData.collectionDateTime.meridian");
</script>
 
 
 
 
 
 
 
 
 
<div class="kcElement">
<label>
 
Date of  Preservation:
</label>
<input type="text" name="sampleData.preservationDateTime.date" value="" readonly="readonly">
&nbsp;
<span class="fakeLink" onclick="openCalendar('sampleData.preservationDateTime.date')">Select Date</span>
&nbsp;&nbsp;&nbsp;&nbsp;
<select name="sampleData.preservationDateTime.hour" class="DISABLED"><option value="">Hr</option>
	<option value="1">1</option>
<option value="2">2</option>
<option value="3">3</option>
<option value="4">4</option>
<option value="5">5</option>
<option value="6">6</option>
<option value="7">7</option>
<option value="8">8</option>
<option value="9">9</option>
<option value="10">10</option>
<option value="11">11</option>
<option value="12">12</option>
<option value="13">13</option>
<option value="14">14</option>
<option value="15">15</option>
<option value="16">16</option>
<option value="17">17</option>
<option value="18">18</option>
<option value="19">19</option>
<option value="20">20</option>
<option value="21">21</option>
<option value="22">22</option>
<option value="23">23</option>
</select>:
<select name="sampleData.preservationDateTime.minute"><option value="">Min</option>
  <option value="00">00</option>
<option value="01">01</option>
<option value="02">02</option>
<option value="03">03</option>
<option value="04">04</option>
<option value="05">05</option>
<option value="06">06</option>
<option value="07">07</option>
<option value="08">08</option>
<option value="09">09</option>
<option value="10">10</option>
<option value="11">11</option>
<option value="12">12</option>
<option value="13">13</option>
<option value="14">14</option>
<option value="15">15</option>
<option value="16">16</option>
<option value="17">17</option>
<option value="18">18</option>
<option value="19">19</option>
<option value="20">20</option>
<option value="21">21</option>
<option value="22">22</option>
<option value="23">23</option>
<option value="24">24</option>
<option value="25">25</option>
<option value="26">26</option>
<option value="27">27</option>
<option value="28">28</option>
<option value="29">29</option>
<option value="30">30</option>
<option value="31">31</option>
<option value="32">32</option>
<option value="33">33</option>
<option value="34">34</option>
<option value="35">35</option>
<option value="36">36</option>
<option value="37">37</option>
<option value="38">38</option>
<option value="39">39</option>
<option value="40">40</option>
<option value="41">41</option>
<option value="42">42</option>
<option value="43">43</option>
<option value="44">44</option>
<option value="45">45</option>
<option value="46">46</option>
<option value="47">47</option>
<option value="48">48</option>
<option value="49">49</option>
<option value="50">50</option>
<option value="51">51</option>
<option value="52">52</option>
<option value="53">53</option>
<option value="54">54</option>
<option value="55">55</option>
<option value="56">56</option>
<option value="57">57</option>
<option value="58">58</option>
<option value="59">59</option></select>
<!-- &nbsp;
<input type="radio" name="sampleData.preservationDateTime.meridian" value="AM">AM
<input type="radio" name="sampleData.preservationDateTime.meridian" value="PM">PM-->
&nbsp;&nbsp;&nbsp;
<input type="button" name="resetPreservation" value="Clear" onclick="GSBIO.bigr.reg.clearDateTime('preservation');">
</div>		    
<script type="text/javascript"> 
GSBIO.bigr.reg.Elements.register("sampleData.preservationDateTime.date");
GSBIO.bigr.reg.Elements.register("sampleData.preservationDateTime.hour");
GSBIO.bigr.reg.Elements.register("sampleData.preservationDateTime.minute");
GSBIO.bigr.reg.Elements.registerRadio("sampleData.preservationDateTime.meridian");
</script>


 
<!-- <div class="kcElement">
<label>
<span class="DISABLED">Hemolyzed:</span>
</label>
<select name="sampleData.hemolyzedCui" class="disabled">
<option value="XXXXXXXX">No</option>
<option value="XXXXXXXX">Yes</option>
</select>
</div>-->
 
<div class="kcElement">
<label>
 
Volume:
</label>
 
<input type="text" name="sampleData.weightAsString" maxlength="12" size="12" value="" onkeyup="GSBIO.bigr.reg.adjustConsumed();">&nbsp; &nbsp;
<select name="sampleData.weightUnitCui"><option value="">Select unit of volume</option>
<option value="XXXXXXXX">l</option>
<option value="XXXXXXXX">ml</option>
</select>
 
</div>
<script type="text/javascript"> 
GSBIO.bigr.reg.Elements.register("sampleData.weightAsString");
GSBIO.bigr.reg.Elements.register("sampleData.weightUnitCui");
</script>
 
 
<div class="kcElement">
<label>
 
<span class="DISABLED">Tube Type:</span>
</label>
<select name="sampleData.weightUnitCui" class="disabled">
<option value="">Select tube type</option>
<option value="XXXXXXXX">No additive (Red)</option>
<option value="XXXXXXXX">K2 EDTA (Purple)</option>
<option value="XXXXXXXX">Li Heparin (Light green)</option>
<option value="XXXXXXXX">Na Heparin (Dark green)</option>
<option value="XXXXXXXX">Na Citrate(Light blue)</option>
<option value="XXXXXXXX">Serum Seperator(SST)</option>
<option value="OT" selected="selected">Other</option>
</select>
<br>
<textarea name="donorProfileNotes" cols="30" rows="2" class="disabled"></textarea>
</div>
<script type="text/javascript"> 
GSBIO.bigr.reg.Elements.register("sampleData.weightAsString");
GSBIO.bigr.reg.Elements.register("sampleData.weightUnitCui");
</script>
 
<div class="kcElement">
<label>
 
Comment:
</label>
<textarea name="sampleData.note" cols="80" rows="10" onkeyup="BigrTextAreaComments.enforceMaxLength();"></textarea>
<script language="JavaScript"> 
  BigrTextAreaComments = new BigrTextArea('sampleData.note');
  BigrTextAreaComments.maxLength = 255;
</script>
</div>		    
<script type="text/javascript"> 
GSBIO.bigr.reg.Elements.register("sampleData.note");
</script>
 
<div class="kcElement">
<label>
 
Prepared By:
</label>
<select name="sampleData.preparedBy"><option value="">Select Prepared By</option>
  
  <option value="Ardais">Martin Ferguson</option>
<option value="alagos">Lagos Alexandria</option>
<option value="abuckler">Alan Buckler</option>
<option value="mmarano">Matt Marano</option>
<option value="sthomashow">Steven Thomashow</option>
<option value="PRusso">Peter Russo</option>
<option value="MFerguson">Martin Ferguson</option>
<option value="CGuy">Chantale Guy</option>
<option value="MDuyao">Mabel Duyao</option>
<option value="RRone">Rebecca Rone</option>
<option value="tallie">Tom Allie</option>
<option value="eturner">Ed Turner</option>
<option value="jschiller">Jim Schiller</option>
<option value="schasse">Steve Chasse</option>
<option value="sbuia">Steve Buia</option>
<option value="bchen">Beiru Chen</option>
<option value="thaliotis">Tina Haliotis</option>
<option value="jhunt1">John Hunt</option>
<option value="hcrowley">Heather Crowley</option>
<option value="cstone">Cynthia Stone</option>
<option value="lcharette">LaSalette Charette</option>
<option value="ckeith">Cindy Keith</option>
<option value="dbugg">Darreyl Bugg</option>
<option value="aferreira">Ann Ferreira</option>
<option value="lrogers">Linda Rogers</option>
<option value="vnguyen">Vuong Nguyen</option>
<option value="labuser">labuser labuser</option>
<option value="limsSuper">limsSuper limsSuper</option>
<option value="GWiseman">Gail Wiseman</option>
<option value="HGreenman">Howard Greenman</option>
<option value="jkutok">Jeffrey Kutok</option>
<option value="ptan">Puay Tan</option>
<option value="yfu">Yineng Fu</option>
<option value="dkern">Daniel Kern</option>
<option value="PArsenault">Pam Arsenault</option>
<option value="JSoybel">Jeremy Soybel</option>
<option value="JDeMassi">John DeMassi</option>
<option value="CCounsell">Cathy Counsell</option>
<option value="ELonergan">Eileen Lonergan</option>
<option value="kutok">Jeffery Kutok</option>
<option value="GCardoso">Gizela Cardoso</option>
<option value="DSlater">Damien Slater</option>
<option value="GRogers">Gary Rogers</option>
<option value="Reporter">Howard Greenman</option>
<option value="Lshi">Lijia Shi</option>
<option value="Ageorge">Annie George</option>
<option value="Klentini">Kristen Lentini</option>
<option value="SVincent">Stephen Vincent</option>
<option value="shankar">Shankar Hegde</option>
<option value="SChu">Sunny Chu</option>
<option value="DrDataA">David Aronow</option>
<option value="julie1">Julie McDowell</option>
<option value="roger">Roger Luo</option>
<option value="dlangendorf">Dean Langendorf</option>
<option value="JGSoybel">Jeremy Soybel</option>
<option value="bulkloader">bulk loader</option>
<option value="CMackenzie">Christine Mackenzie</option>
<option value="denpath">d d</option>
<option value="denhist">d d</option>
<option value="JMasterson">Jennifer Masterson</option>
<option value="jblander">Jeff Blander</option>
<option value="bmartin">Beth Martin</option>
<option value="holt0014">John Holt</option>
<option value="snyde020">Matt Snyder</option>
<option value="dannad">Danna Digesse</option>
<option value="rschwartz">Robert Schwartz</option>
<option value="agoldarn">Atoussa Goldar-Najafi</option>
<option value="lpantano">Liron Pantanowitz</option>
<option value="agnieszka">Agnieszka Witkiewicz</option>
<option value="dsherman">David Sherman</option>
<option value="kmckeon">Kathy McKeon</option>
<option value="DChandler">Dennis Chandler</option>
<option value="saras">Sara Szafman</option>
<option value="mehta">monica mehta</option>
<option value="Demo">Rebecca Rone</option>
<option value="MSwift">Marge Swift</option>
<option value="bulkload1"> </option>
<option value="DMorosini">Deborah Morosini</option>
<option value="bulkload1b"> </option>
<option value="bulkload2a"> </option>
<option value="JSutton">Jocelyn Sutton</option>
<option value="VMurthy">Vani Murthy</option>
<option value="bulkload3a"> </option>
<option value="JJudge">Joyce Judge</option>
<option value="CShapiro">Cynthia Shapiro</option>
<option value="awright">Ami Wright</option>
<option value="SIslam">Sahibul Islam</option>
<option value="bulkload4a"> </option>
<option value="IWelsford">Ian Welsford</option>
<option value="bulkload5a"> </option>
<option value="QHuang">Qin Huang</option>
<option value="DHuron">Duncan Huron</option>
<option value="ZJiang">Zhong Jiang</option>
<option value="GisellaB">Gisella Blanchette</option>
<option value="NLawrence">Nicole Lawrence</option>
<option value="TYoung">Ted Young</option>
<option value="bzeigler">Bonnie Zeigler</option>
<option value="kegnitz">Kelly Egnitz</option>
<option value="KWetzel">Kristie Wetzel</option>
<option value="CHamilton">Catherine Hamilton</option>
<option value="mbamberger">Maria Bamberger</option>
<option value="LBelanger">Leona Belanger</option>
<option value="therlihy">Tena Herlihy</option>
<option value="edonskoy">Elina Donskoy</option>
<option value="caitlinw">Caitlin Willoughby</option>
<option value="jlaguette">Julia LaGuette</option>
<option value="JCyr">Jennifer Cyr</option>
<option value="KGowell">Ken Gowell</option>
<option value="rfriedberg">Ross Friedberg</option>
<option value="ebundock">Elizabeth Bundock</option>
<option value="tphung">Thuy Phung</option>
<option value="bbryan">Brad Bryan</option>
<option value="ACornfield">Adam Cornfield</option>
<option value="ituser">ituser ituser</option>
<option value="gcarson">Geoff Carson</option>
<option value="QCPoster">RnD RnD</option>
<option value="SPonnuswamy">Satt Ponnuswamy</option>
<option value="ppool">Philip Pool</option>
<option value="mcintron">Miguel Cintron</option>
<option value="ssteelman">Scott Steelman</option>
<option value="KHackett">Kristel Hackett</option>
<option value="Counsell">Cathy Counsell</option>
<option value="JFlynn">James Flynn</option>
<option value="CGowan">Christie Gowan</option>
<option value="ezhang">Eileen Zhang</option>
<option value="RimalP">Rimal Patel</option>
<option value="bsutton1">Brenton Sutton</option>
<option value="mschwab2">Michelle Schwab</option>
<option value="yryan2">Yvonne Ryan</option>
<option value="bsutton2">Brenton Sutton</option>
<option value="scarr">Sally Carr</option>
<option value="sgranger1">Scott Granger</option>
<option value="ncobb1">Nick Cobb</option>
<option value="AArora">Amy Arora</option>
<option value="pminor">Peter Minor</option>
<option value="jdorrian">Jennifer Dorrian</option>
<option value="cfrenchko">Carla Frenchko</option>
<option value="amordkoff">Anne Mordkoff</option>
<option value="nbarry">Nancy Barry</option>
<option value="DrData">David Aronow</option>
<option value="mgallagher">Meagan Gallagher</option>
<option value="jmurdock">Jason Murdock</option>
<option value="pathology">Hong Peng</option>
<option value="histology">Jason Murdock</option>
<option value="tuser">Test User</option>
<option value="JAWEED">Jaweed Mohammed</option></select>
</div>
<script type="text/javascript"> 
GSBIO.bigr.reg.Elements.register("sampleData.preparedBy");
</script></div> <!-- kcElements -->
</div>
 
</div> <!-- sampleTypeSpecific -->


<div class="sampleRegButtons">
<p>
<input type="submit" id="btnSubmit" name="btnSubmit" value="Save This Sample" 
onclick="rememberedData.value='';onSave();"/>
</p>
 
<p>
<input type="submit" id="btnNextSample" name="btnNextSample" 
value="Save and Add Next Sample for This Case" 
onclick="rememberedData.value='CASE';onSave();">
</p>
<p>
<input type="submit" id="btnNextCase" name="btnNextCase" 
value="Save and Add Next Case for This Donor" 
onclick="rememberedData.value='DONOR';onSave();">
</p>
<p>
<input type="button" id="btnCancel" name="btnCancel" value="Cancel" 
onclick="window.location.href='/BIGR/dataImport/createSampleStart.do';"> 
</p>
</div>
<p style="font-size: 8pt;"><span class="kcRequiredInd">*</span> indicates a required field </p>
</form>
</body>
</html>

