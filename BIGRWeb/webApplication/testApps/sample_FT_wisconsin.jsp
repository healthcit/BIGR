
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
          <br><u>Frozen Tissue Registration Form</u></div>
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
<label class="inlineLabel">Donor:</label>JMTEST1
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
<option value="">Select Sample Type</option><option value="CA00011C">Buffy Coat</option><option value="CA11725C">DNA</option><option value="CA00052C" selected="selected">Frozen Tissue</option><option value="CA09400C">Paraffin Tissue</option><option value="CA09402C">PBMC</option><option value="CA09556C">PBMC Pellet</option><option value="CA09563C">Plasma</option><option value="CA11117C">Serum</option><option value="CA11144C">Whole Blood</option></select>
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
 
<span class="DISABLED">Tissue:</span>
</label>
<input type="text" name="searchDiagsampleData.tissue" maxlength="20" onKeyPress="if(event.keyCode == 13){findListOptions(this.form['searchDiagsampleData.tissue'], this.form['sampleData.tissue']);showOtherForList(this.form['sampleData.tissue'].value,'sampleData.tissueOther','91723000');return false;}">&nbsp;<input type="button" value="Search" name="search" onClick="findListOptions(this.form['searchDiagsampleData.tissue'], this.form['sampleData.tissue']);showOtherForList(this.form['sampleData.tissue'].value,'sampleData.tissueOther','91723000');">&nbsp;<input type="button" value="Refresh" name="refresh" onClick="restoreListOptions(this.form['searchDiagsampleData.tissue'], this.form['sampleData.tissue']);showOtherForList(this.form['sampleData.tissue'].value,'sampleData.tissueOther','91723000');"><p style="margin: 3px 0 0 0; border: 0; padding: 0;"><select name="sampleData.tissue" onChange="showOtherForList(this.value,'sampleData.tissueOther','91723000');">
<option value="">Select Tissue</option>
<option value="361294009">Abdominal cavity</option>
<option value="181613000">Abdominal wall</option>
<option value="181199001">Adenoid</option>
<option value="55603005">Adipose tissue</option>
<option value="23043003">Adnexa</option>
<option value="CA00246T">Adnexa: bilateral</option>
<option value="110635008">Adnexa: left</option>
<option value="110634007">Adnexa: right</option>
<option value="181127006">Adrenal gland</option>
<option value="67109009">Ampulla of Vater</option>
<option value="CA00390T">Anastomotic tissue</option>
<option value="361292008">Ankle</option>
<option value="CA00783T">Anorectal margin</option>
<option value="181262009">Anus</option>
<option value="181298001">Aorta</option>
<option value="181287002">Aortic valve</option>
<option value="181255000">Appendix</option>
<option value="29745008">Areola</option>
<option value="40983000">Arm</option>
<option value="CA00785T">Arterial plaque</option>
<option value="51114001">Artery</option>
<option value="69105007">Artery: carotid</option>
<option value="181294004">Artery: coronary</option>
<option value="50018008">Artery: coronary, left</option>
<option value="13647002">Artery: coronary, right</option>
<option value="76015000">Artery: hepatic</option>
<option value="CA00013T">Artery: peripheral</option>
<option value="181380003">Artery: pulmonary</option>
<option value="181339005">Artery: renal</option>
<option value="91470000">Axillary region</option>
<option value="38412008">Axillary tail</option>
<option value="302552004">Back</option>
<option value="276157007">Bile duct</option>
<option value="87612001">Blood</option>
<option value="361097006">Blood vessel</option>
<option value="90780006">Bone</option>
<option value="279729006">Bone marrow</option>
<option value="51299004">Bone: clavicle</option>
<option value="71341001">Bone: femur</option>
<option value="310652005">Bone: femur, distal</option>
<option value="2812003">Bone: femur, head</option>
<option value="CA02546T">Bone: femur, lateral</option>
<option value="CA02547T">Bone: femur, medial</option>
<option value="87342007">Bone: fibula</option>
<option value="85050009">Bone: humerus</option>
<option value="119524001">Bone: humerus, proximal</option>
<option value="29850006">Bone: iliac crest</option>
<option value="122496007">Bone: lumbar spine</option>
<option value="181812008">Bone: mandible</option>
<option value="70925003">Bone: maxilla</option>
<option value="118645006">Bone: pelvis</option>
<option value="62413002">Bone: radius</option>
<option value="113197003">Bone: rib</option>
<option value="79601000">Bone: scapula</option>
<option value="302522007">Bone: sternum</option>
<option value="181795004">Bone: temporal</option>
<option value="12611008">Bone: tibia</option>
<option value="64605006">Bone: tibia, distal</option>
<option value="CA02548T">Bone: tibia, lateral</option>
<option value="CA02549T">Bone: tibia, medial</option>
<option value="20285007">Bone: tibia, proximal</option>
<option value="CA02544T">Bone: tibia, subchondral</option>
<option value="23416004">Bone: ulna</option>
<option value="181002002">Brachial plexus</option>
<option value="258335003">Brain</option>
<option value="181131000">Breast</option>
<option value="110514002">Breast tissue, male</option>
<option value="63762007">Breast, bilateral</option>
<option value="80248007">Breast, left</option>
<option value="CA03650T">Breast, left lateral</option>
<option value="CA03651T">Breast, left medial</option>
<option value="73056007">Breast, right</option>
<option value="CA03652T">Breast, right lateral</option>
<option value="CA03654T">Breast, right medial</option>
<option value="34411009">Broad ligament</option>
<option value="181215002">Bronchus</option>
<option value="256682009">Buccal mucosa</option>
<option value="182483003">Bursa</option>
<option value="46862004">Buttock</option>
<option value="17401000">Cardiac valve</option>
<option value="51345006">Carotid body</option>
<option value="61496007">Cartilage</option>
<option value="305066005">Cartilage: femur, distal</option>
<option value="CA00392T">Cartilage: tibia, proximal</option>
<option value="181256004">Cecum</option>
<option value="CA11727C">Cerebral ventricle</option>
<option value="71252005">Cervix</option>
<option value="181608004">Chest wall</option>
<option value="181442003">Clitoris</option>
<option value="182028002">Coccyx</option>
<option value="302508007">Colon</option>
<option value="245427008">Colon: hepatic flexure</option>
<option value="55572008">Colon: left</option>
<option value="CA02551T">Colon: multiple sites</option>
<option value="81922002">Colon: rectosigmoid</option>
<option value="51342009">Colon: right</option>
<option value="60184004">Colon: sigmoid</option>
<option value="245428003">Colon: splenic flexure</option>
<option value="485005">Colon: transverse</option>
<option value="71854001">Colon: unspecified segment</option>
<option value="244447006">Cranial nerve</option>
<option value="245398005">Cystic duct</option>
<option value="90604005">Dendritic cells</option>
<option value="181614006">Diaphragm</option>
<option value="181247007">Duodenum</option>
<option value="1910005">Ear</option>
<option value="37949006">Endocardium</option>
<option value="CA00786T">Endometriotic tissue</option>
<option value="278867007">Endometrium</option>
<option value="181432000">Epididymis</option>
<option value="245502004">Epiglottis</option>
<option value="181245004">Esophagus</option>
<option value="66019005">Extremities</option>
<option value="244486005">Eye</option>
<option value="181463001">Fallopian tube</option>
<option value="181772008">Fascia</option>
<option value="60080006">Female germ cells</option>
<option value="CA00127T">Fibroadipose tissue</option>
<option value="CA00391T">Fibromuscular tissue</option>
<option value="34433006">Fibrous tissue</option>
<option value="CA00787T">Fibrovascular tissue</option>
<option value="302541005">Finger</option>
<option value="302545001">Foot</option>
<option value="17880006">Foreskin</option>
<option value="181269000">Gallbladder</option>
<option value="25271004">Gastroesophageal junction</option>
<option value="181224006">Gingiva</option>
<option value="182289002">Groin</option>
<option value="302539009">Hand</option>
<option value="302509004">Heart</option>
<option value="13383001">Heart: apex</option>
<option value="82471001">Heart: left atrium</option>
<option value="87878005">Heart: left ventricle</option>
<option value="73829009">Heart: right atrium</option>
<option value="53085002">Heart: right ventricle</option>
<option value="245399002">Hepatic duct</option>
<option value="112640005">Hernia sac</option>
<option value="29836001">Hip</option>
<option value="CA00789T">Ileal pouch</option>
<option value="23153004">Ileocecal valve</option>
<option value="181249005">Ileum</option>
<option value="243962005">Inguinal region</option>
<option value="244570000">Intervertebral disc</option>
<option value="181248002">Jejunum</option>
<option value="302536002">Joint</option>
<option value="182238002">Joint capsule</option>
<option value="42209000">Jugular foramen</option>
<option value="181414000">Kidney</option>
<option value="361291001">Knee</option>
<option value="181147003">Lacrimal gland</option>
<option value="181212004">Larynx</option>
<option value="30021000">Leg</option>
<option value="182358004">Ligament</option>
<option value="181221003">Lip</option>
<option value="181268008">Liver</option>
<option value="69842003">Liver: left lobe</option>
<option value="48521005">Liver: right lobe</option>
<option value="181216001">Lung</option>
<option value="41224006">Lung: left lower lobe</option>
<option value="44714003">Lung: left upper lobe</option>
<option value="266005">Lung: right lower lobe</option>
<option value="72481006">Lung: right middle lobe</option>
<option value="42400003">Lung: right upper lobe</option>
<option value="181756000">Lymph node</option>
<option value="35783009">Lymph node: aortic</option>
<option value="181759007">Lymph node: axillary</option>
<option value="181757009">Lymph node: cervical</option>
<option value="84219008">Lymph node: common iliac</option>
<option value="65266007">Lymph node: deep inguinal</option>
<option value="65349008">Lymph node: external iliac</option>
<option value="CA11728C">Lymph node: femoral</option>
<option value="279363006">Lymph node: hypogastric</option>
<option value="CA11729C">Lymph node: inguinal</option>
<option value="245357003">Lymph node: internal iliac</option>
<option value="245333003">Lymph node: interpectoral</option>
<option value="279795009">Lymph node: mesenteric</option>
<option value="36086000">Lymph node: obturator</option>
<option value="CA11730C">Lymph node: para-aortic</option>
<option value="CA11731C">Lymph node: paracervical</option>
<option value="CA11732C">Lymph node: parametrial</option>
<option value="245352009">Lymph node: pelvic</option>
<option value="CA11733C">Lymph node: presacral</option>
<option value="249613000">Lymph node: retroperitoneal</option>
<option value="279797001">Lymph node: sacral</option>
<option value="181758004">Lymph node: scalene</option>
<option value="76461007">Lymph node: sentinel</option>
<option value="113340006">Lymph node: superficial inguinal</option>
<option value="245325004">Lymph node: supraclavicular</option>
<option value="332457007">Lymph node: Virchow</option>
<option value="6969002">Lymphoid tissue</option>
<option value="CA01779T">Maxillofacial sinus</option>
<option value="264256006">Meckels diverticulum</option>
<option value="181217005">Mediastinum</option>
<option value="1231004">Meninges</option>
<option value="74135004">Meniscus</option>
<option value="89679009">Mesentery</option>
<option value="279900000">Mesosalpinx</option>
<option value="71400007">Mesothelium</option>
<option value="CA00108T">Metastasis, unknown source</option>
<option value="181286006">Mitral valve</option>
<option value="CA00802T">Multiple digestive system sites</option>
<option value="CA00803T">Multiple female reproductive system sites</option>
<option value="CA00804T">Multiple male reproductive system sites</option>
<option value="51576004">Multiple topographic sites</option>
<option value="CA00805T">Multiple urinary system sites</option>
<option value="91727004">Muscle tissue</option>
<option value="CA02554T">Myeloid tissue</option>
<option value="41621006">Myelomonocytic cells</option>
<option value="74281007">Myocardium</option>
<option value="279879004">Myometrium</option>
<option value="16178009">Nasal bone and cartilage</option>
<option value="181203001">Nasal sinus</option>
<option value="181200003">Nasopharynx</option>
<option value="302550007">Neck</option>
<option value="CA03535T">Nerve sheath</option>
<option value="362460007">Nerve: facial</option>
<option value="181010001">Nerve: median</option>
<option value="181011002">Nerve: radial</option>
<option value="181050003">Nerve: sciatic</option>
<option value="CA11734C">Nerve: sural</option>
<option value="27612005">Nerve: trigeminal</option>
<option value="181012009">Nerve: ulnar</option>
<option value="CA11735C">Nerve: vestibulocochlear</option>
<option value="91728009">Neural tissue</option>
<option value="CA00245T">Neurovascular bundle</option>
<option value="265780004">Nipple</option>
<option value="181195007">Nose</option>
<option value="27398004">Omentum</option>
<option value="181220002">Oral cavity</option>
<option value="181143004">Orbit</option>
<option value="263376008">Oropharynx</option>
<option value="181464007">Ovary</option>
<option value="83238006">Ovary: bilateral</option>
<option value="280124008">Ovary: left</option>
<option value="280123002">Ovary: right</option>
<option value="280108007">Ovary: unilateral</option>
<option value="181227004">Palate</option>
<option value="181277001">Pancreas</option>
<option value="19387007">Pancreas, ectopic</option>
<option value="181278006">Pancreatic duct</option>
<option value="279329009">Papillary muscle</option>
<option value="281695005">Paraganglia</option>
<option value="280455005">Parametrium</option>
<option value="CA00125T">Paraovarian tissue</option>
<option value="CA07554T">Paraspinal area</option>
<option value="CA00800T">Paratesticular tissue</option>
<option value="181121007">Parathyroid gland</option>
<option value="CA00126T">Paratubal tissue</option>
<option value="CA03536T">Paravaginal tissue</option>
<option value="181234002">Parotid gland</option>
<option value="3665003">Pelvic wall</option>
<option value="229765004">Pelvis</option>
<option value="265793009">Penis</option>
<option value="181295003">Pericardium</option>
<option value="243990009">Perineum</option>
<option value="244457007">Peripheral nerve</option>
<option value="CA00199T">Peritoneal washings</option>
<option value="15425007">Peritoneum</option>
<option value="181211006">Pharynx</option>
<option value="181126002">Pineal gland</option>
<option value="181125003">Pituitary gland</option>
<option value="181455002">Placenta</option>
<option value="181609007">Pleura</option>
<option value="281699004">Presacral region</option>
<option value="181422007">Prostate</option>
<option value="39057004">Pulmonic valve</option>
<option value="181261002">Rectum</option>
<option value="25990002">Renal pelvis</option>
<option value="CA00801T">Respiratory mucosa</option>
<option value="243984005">Retroperitoneal region</option>
<option value="181056009">Sacral plexus</option>
<option value="264186006">Sacrum</option>
<option value="181236000">Salivary gland</option>
<option value="CA11736C">Sella turcica</option>
<option value="181434004">Seminal vesicle</option>
<option value="361103004">Shoulder</option>
<option value="CA00203T">Sinus contents, maxillofacial</option>
<option value="181469002">Skin</option>
<option value="89546000">Skull</option>
<option value="181250005">Small intestine</option>
<option value="122447002">Smooth muscle</option>
<option value="181607009">Soft tissue</option>
<option value="181433005">Spermatic cord</option>
<option value="180959008">Spinal cord</option>
<option value="181279003">Spleen</option>
<option value="65146007">Spleen, ectopic</option>
<option value="91241007">Stoma site</option>
<option value="181246003">Stomach</option>
<option value="88928006">Synovium</option>
<option value="256667004">Tendon</option>
<option value="181431007">Testis</option>
<option value="302544002">Thigh</option>
<option value="70281000">Thrombus</option>
<option value="9875009">Thymus</option>
<option value="181117000">Thyroid gland</option>
<option value="181226008">Tongue</option>
<option value="265787001">Tonsil</option>
<option value="181213009">Trachea</option>
<option value="361384001">Tracheobronchial tree</option>
<option value="110725008">Tracheoesophagus</option>
<option value="181288007">Tricuspid valve</option>
<option value="280644003">Umbilical cord</option>
<option value="265800007">Umbilicus</option>
<option value="CA00202T">Unknown tissue</option>
<option value="CA03537T">Unknown tissue consistent with anus</option>
<option value="CA11186C">Unknown tissue consistent with biliary or pancreatic origin</option>
<option value="CA01782T">Unknown tissue consistent with bladder</option>
<option value="CA00164T">Unknown tissue consistent with breast</option>
<option value="CA00165T">Unknown tissue consistent with colon</option>
<option value="CA00168T">Unknown tissue consistent with female germ cells</option>
<option value="CA07553T">Unknown tissue consistent with gastrointestinal tissue</option>
<option value="CA00791T">Unknown tissue consistent with hepatobiliary tract</option>
<option value="CA00792T">Unknown tissue consistent with kidney</option>
<option value="CA00388T">Unknown tissue consistent with lung</option>
<option value="CA00167T">Unknown tissue consistent with lymphoma</option>
<option value="CA00169T">Unknown tissue consistent with male germ cells</option>
<option value="CA00389T">Unknown tissue consistent with nasopharyngeal origin</option>
<option value="CA00793T">Unknown tissue consistent with neuroendocrine origin</option>
<option value="CA03538T">Unknown tissue consistent with ovary</option>
<option value="CA00385T">Unknown tissue consistent with pancreas</option>
<option value="CA00166T">Unknown tissue consistent with prostate</option>
<option value="CA02550T">Unknown tissue consistent with rectum</option>
<option value="CA00796T">Unknown tissue consistent with skin</option>
<option value="CA00794T">Unknown tissue consistent with small intestine</option>
<option value="CA03539T">Unknown tissue consistent with stomach</option>
<option value="CA00797T">Unknown tissue consistent with thyroid</option>
<option value="CA00795T">Unknown tissue consistent with vagina</option>
<option value="276858000">Urachus</option>
<option value="302511008">Ureter</option>
<option value="302513006">Urethra</option>
<option value="89837001">Urinary bladder</option>
<option value="64344009">Urinary bladder neck</option>
<option value="CA00798T">Urothelial mucosa</option>
<option value="76195004">Uterine serosa</option>
<option value="181452004">Uterus</option>
<option value="CA00386T">Uterus and adnexa</option>
<option value="212940007">Uterus, lower segment</option>
<option value="26140008">Uvula</option>
<option value="181441005">Vagina</option>
<option value="245467009">Vas deferens</option>
<option value="181367001">Vein</option>
<option value="244403000">Vein: jugular</option>
<option value="CA00014T">Vein: peripheral</option>
<option value="32764006">Vein: portal</option>
<option value="181370002">Vein: renal</option>
<option value="244405007">Vena cava</option>
<option value="181817002">Vertebra</option>
<option value="245504003">Vocal cord</option>
<option value="265796001">Vulva</option>
<option value="91723000">Other tissue</option>
</select>&nbsp;<input type="Button"  name="showsampleData.tissueHierarchy" value="..." onClick=" showHierarchyWithSearch('sampleData.tissue','/BIGR/orm/Dispatch?op=GetLIMSDxTcHierarchy&type=T','searchDiagsampleData.tissue');">
<p style="margin: 3px 0 0 0; border: 0; padding: 0;">Other Tissue:&nbsp;<input type="text" name="sampleData.tissueOther" size="30" maxlength="200" value = "N/A" disabled>
 
</div><script type="text/javascript"> 
GSBIO.bigr.reg.Elements.register("sampleData.tissue");
GSBIO.bigr.reg.Elements.register("sampleData.tissueOther");
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


<div class="kcElement">
<label>
 
Gross  Appearance:
</label>
<select name="sampleData.grossAppearance"><option value="">Select Gross  Appearance</option><option value="D">Gross Diseased</option><option value="M">Gross Mixed</option><option value="N">Gross Normal</option><option value="U">Gross Unknown</option></select>
</div>		    
<script type="text/javascript"> 
GSBIO.bigr.reg.Elements.register("sampleData.grossAppearance");
</script>
 
<div class="kcElement">
<label>
 
<span class="DISABLED"> Format  Detail:</span>
</label>
<select name="sampleData.formatDetail" class="DISABLED">
<option value="">Select Format  Detail</option>
<option value="XXXXXXXX">SNAP frozen, liquid N2</option>
<option value="XXXXXXXX">SNAP frozen, Isopenthane</option>
<option value="XXXXXXXX">SNAP frozen, Prefixed in 4% PFA, liquid N2</option>
<option value="XXXXXXXX">SNAP frozen, Prefixed in 4% PFA, Isopenthane</option>
<option value="XXXXXXXX">SNAP frozen, other (add comment)</option>
<option value="XXXXXXXX">OCT  frozen, on dry ice</option>
<option value="XXXXXXXX">OCT frozen, in cryostat</option>
<option value="XXXXXXXX">OCT frozen, liquid N2</option>
<option value="XXXXXXXX">OCT frozen, other (add comment)</option>
<!-- <option value="XXXXXXXX">RNA stabilizer, liquid N2</option>
<option value="XXXXXXXX">RNA stabilizer, isopenthane</option>-->
<option value="XXXXXXXX">RNA stabilizer, -80</option>
<option value="XXXXXXXX">RNA stabilizer, other (add comment)</option>
<option value="OT" selected="selected">Other</option>
</select>
<br>
<textarea name="donorProfileNotes" cols="30" rows="2" class="disabled"></textarea>
</div>		    
<script type="text/javascript"> 
GSBIO.bigr.reg.Elements.register("sampleData.formatDetail");
</script>
 
<div class="kcElement">
<label>
 
Weight:
</label>
 
<input type="text" name="sampleData.weightAsString" maxlength="12" size="12" value="" onkeyup="GSBIO.bigr.reg.adjustConsumed();">&nbsp; &nbsp;
<select name="sampleData.weightUnitCui"><option value="">Select unit of weight</option><option value="CA11739C">g</option><option value="CA11740C">mg</option><option value="CA11746C">ug</option><option value="CA11741C">ng</option></select>
 
</div>
<script type="text/javascript"> 
GSBIO.bigr.reg.Elements.register("sampleData.weightAsString");
GSBIO.bigr.reg.Elements.register("sampleData.weightUnitCui");
</script>
 
 
<div class="kcElement">
<label>
 
<span class="DISABLED">Dimensions:</span>
</label>
 
<input type="text" name="sampleData.weightAsString" maxlength="12" size="12" value="" class="disabled">&nbsp; &nbsp;
<select name="sampleData.weightUnitCui" class="disabled"><option value="">Select unit of Dimension</option>
<option value="CA11739C">mm</option>
<option value="CA11740C">cm</option>
</select>
 
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
