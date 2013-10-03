 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
<html>
<head>
<title>Modify Sample</title>
<script language="JavaScript" src="/BIGR/js/bigrTextArea.js"></script>
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
 
var myBanner = 'Modify Sample';
 
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
 
    GSBIO.bigr.reg.RegFormIds['CA12599C'] = 'FD00000441';
 
    GSBIO.bigr.reg.RegFormIds['CA00011C'] = 'FD00000115';
 
  GSBIO.bigr.reg.CurrentSampleTypeCui = $F('sampleData.sampleTypeCui');
  $('sampleData.sampleAlias').focus();
  $('originalSampleType').value = $F('sampleData.sampleTypeCui');
 
 
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
  if (GSBIO.bigr.reg.verifySampleTypeChange()) {
    if (GSBIO.kc.data.FormInstances.getFormInstance()) {
      $('form').value = GSBIO.kc.data.FormInstances.getFormInstance().serialize();
    }
    return true;
  }
  else {
    GSBIO.bigr.reg.setActionButtonEnabling(true);
    return false;
  }
}
 
function updatePrintLabelCheckBox() {
  $('printSampleLabel').value = document.all.printSampleLabelCB.checked;
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

function getAttachments(){
  window.open('/BIGR/testApps/attachmentsList.jsp','attachDocuments','toolbar=no,menubar=no,resizable=yes,status=yes,width=700,height=600,left=400,top=200')
}
</script>
</head>
<body class="bigr" onload="initPage();">
<form name="sampleForm" method="POST" action="/BIGR/dataImport/modifySample.do" onsubmit="return(onFormSubmit());">
  <input type="hidden" name="sampleData.ardaisId" value="AX0000000497">
  <input type="hidden" name="sampleData.consentId" value="CX0000000517">
  <input type="hidden" name="sampleData.sampleId" value="SX0001D308">
  <input type="hidden" name="sampleData.consumed" value="false">
  <input type="hidden" name="sampleData.registrationFormId" value="FD00000114">
	<input type="hidden" name="originalSampleType"/>
  <input type="hidden" name="form" value=""/>
 
<div id="errorsDiv" class="errorsMessages">
 
</div>
<div id="messagesDiv" class="errorsMessages">
 
</div>
 
<div class="kcElements bigrElements">
 
<div class="kcElement bigrElement">
<label class="inlineLabel">Donor:</label>
AX0000000497
 
(D-TN05-00525)
 
</div>
 
<div class="kcElement bigrElement">
<label class="inlineLabel">Case:</label>
CX0000000517
 
(TN05-00525)
 
</div>
 
<div class="kcElement bigrElement">
<label class="inlineLabel">Sample Id:</label>
SX0001D308
</div>
 
<div class="kcElement bigrElement">
<label class="inlineLabel">
 
 Sample Alias:</label>
 
<input type="text" name="sampleData.sampleAlias" maxlength="30" size="30" value="TS05-0003966">
 
</div>
 
<div class="kcElement bigrElement">
<label class="inlineLabel">
 
<span class="kcRequiredInd">*</span> 
 
Sample Type:</label>
 
<select name="sampleData.sampleTypeCui" onchange="GSBIO.bigr.reg.getSampleRegistrationForm();updateLabelTemplateChoices();"><option value="">Select Sample Type</option><option value="CA00011C">Buffy Coat</option><option value="CA11725C">DNA</option><option value="CA12599C">Fine Needle Aspirate</option><option value="CA00052C">Frozen Tissue</option><option value="CA09400C" selected>Paraffin Tissue</option><option value="CA09402C">PBMC</option><option value="CA09556C">PBMC Pellet</option><option value="CA09563C">Plasma</option><option value="CA11117C">Serum</option><option value="CA11144C">Whole Blood</option></select>
 
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
				<select name="templateDefinitionName" onchange="updatePrinterChoices();"><option value="">Select</option><option value="3 Up Label">3 Up Label</option><option value="Tube Label">Tube Label</option><option value="Vial Wraparound Label">Vial Wraparound Label</option></select>
  <br>
	<label class="inlineLabel">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
	Label printer:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<select name="printerName"><option value="">Select</option></select>
 
</div>
 
</div> <!-- kcElements -->
 
<div id="sampleTypeSpecific">
 
 
 
 
 
 
 
 
 
 
 
<script type="text/javascript">GSBIO.kc.data.FormInstances.createFormInstance({"formDefinitionId":"FD00000114","domainObjectId":"SX0001D308"});</script>
 
 
 
<div class="kcElementsContainer">
<div class="kcElements">
 
 
 
 
 
 
 
 
 
 
<div class="kcElement">
<label>
 
Source:
</label>
<input type="text" name="sampleData.source" maxlength="200" size="30" value="DUKE">
</div>
<script type="text/javascript"> 
GSBIO.bigr.reg.Elements.register("sampleData.source");
</script>
 
 
 
 
 
 
 
 
 
<div class="kcElement">
<label>
 
Procedure:
</label>
<input type="text" name="searchDiagsampleData.procedure" maxlength="20" onKeyPress="if(event.keyCode == 13){findListOptions(this.form['searchDiagsampleData.procedure'], this.form['sampleData.procedure']);showOtherForList(this.form['sampleData.procedure'].value,'sampleData.procedureOther','CA00004P');return false;}">&nbsp;<input type="button" value="Search" name="search" onClick="findListOptions(this.form['searchDiagsampleData.procedure'], this.form['sampleData.procedure']);showOtherForList(this.form['sampleData.procedure'].value,'sampleData.procedureOther','CA00004P');">&nbsp;<input type="button" value="Refresh" name="refresh" onClick="restoreListOptions(this.form['searchDiagsampleData.procedure'], this.form['sampleData.procedure']);showOtherForList(this.form['sampleData.procedure'].value,'sampleData.procedureOther','CA00004P');"><p style="margin: 3px 0 0 0; border: 0; padding: 0;"><select name="sampleData.procedure" onChange="showOtherForList(this.value,'sampleData.procedureOther','CA00004P');">
<option value="">Select Procedure</option>
<option value="81751006">Abdominal mass excision</option>
<option value="89305009">Abdominal paracentesis</option>
<option value="265414003">Abdominoperineal resection</option>
<option value="177250006">Abdominoplasty</option>
<option value="CA07551P">Ablative therapy</option>
<option value="25693003">Adenoidectomy</option>
<option value="31904001">Adrenalectomy</option>
<option value="81723002">Amputation</option>
<option value="54104006">Amputation revision</option>
<option value="79733001">Amputation, above knee</option>
<option value="88312006">Amputation, below knee</option>
<option value="CA01722P">Amputation, foot or toe</option>
<option value="119894003">Anal mass excision</option>
<option value="257741005">Anastomosis</option>
<option value="75087007">Aneurysm repair</option>
<option value="2119009">Angioplasty</option>
<option value="80146002">Appendectomy</option>
<option value="29858004">Artery excision</option>
<option value="226083002">Arthrectomy</option>
<option value="70805003">Arthroplasty</option>
<option value="13714004">Arthroscopy</option>
<option value="29240004">Autopsy</option>
<option value="CA00247P">Axillary mass excision</option>
<option value="234271004">Axillary node biopsy</option>
<option value="CA00248P">Back mass excision</option>
<option value="CA00373P">Bile duct mass excision</option>
<option value="CA07546P">Biliary bypass</option>
<option value="CA07550P">Blood brain barrier disruption</option>
<option value="239329001">Bone excision</option>
<option value="288056002">Bone fusion</option>
<option value="49401003">Bone marrow aspiration</option>
<option value="56241004">Bone marrow biopsy, needle or trocar</option>
<option value="230842002">Brain biopsy</option>
<option value="807005">Brain excision</option>
<option value="53423001">Brain lobectomy</option>
<option value="44578009">Breast core-needle biopsy</option>
<option value="CA00629P">Breast core-needle biopsy, image-guided</option>
<option value="42125001">Breast mass excisional biopsy</option>
<option value="116219004">Breast mass excisional biopsy with axillary contents</option>
<option value="116220005">Breast mass excisional biopsy without axillary contents</option>
<option value="10847001">Bronchoscopy</option>
<option value="49045006">Bronchotomy</option>
<option value="232651003">Bullectomy</option>
<option value="CA07543P">Cardiac device placement</option>
<option value="408466002">Cardiac surgery</option>
<option value="120146008">Chest wall mass excision</option>
<option value="38102005">Cholecystectomy</option>
<option value="395724000">Choledocholithotomy</option>
<option value="294034007">Choledochotomy</option>
<option value="72310004">Circumcision</option>
<option value="23968004">Colectomy</option>
<option value="82619000">Colectomy, left</option>
<option value="359571009">Colectomy, right</option>
<option value="84604002">Colectomy, sigmoid</option>
<option value="43075005">Colectomy, subtotal</option>
<option value="26390003">Colectomy, total</option>
<option value="26925005">Colectomy, transverse</option>
<option value="398740003">Colostomy</option>
<option value="171728001">Cordectomy of spinal cord</option>
<option value="10836008">Core decompression</option>
<option value="232717009">Coronary artery bypass graft  ~  CABG</option>
<option value="CA00383P">Cranial mass excision</option>
<option value="25353009">Craniotomy</option>
<option value="5269003">Cryosurgery</option>
<option value="108034003">Cystectomy</option>
<option value="112902005">Cystectomy, partial</option>
<option value="287720006">Cystoprostatectomy</option>
<option value="108306002">Dental procedure</option>
<option value="307272003">Electrocautery</option>
<option value="71815002">Embolectomy</option>
<option value="122463005">Embolization</option>
<option value="392031002">Endarterectomy</option>
<option value="387829002">Endomyocardial biopsy</option>
<option value="39250009">Enucleation</option>
<option value="45900003">Esophagectomy</option>
<option value="24506003">Esophagogastrectomy</option>
<option value="23797008">Esophagojejunostomy</option>
<option value="74770008">Exploratory laparotomy</option>
<option value="CA00009P">Fallopian tube fragments excision</option>
<option value="74422001">Fasciectomy</option>
<option value="387736007">Fine needle aspiration of breast</option>
<option value="CA07549P">Fracture repair</option>
<option value="359890009">Fundoplication</option>
<option value="53442002">Gastrectomy</option>
<option value="49209004">Gastrectomy, subtotal</option>
<option value="26452005">Gastrectomy, total</option>
<option value="79121003">Gastric biopsy</option>
<option value="11127003">Gastric bypass</option>
<option value="CA00374P">Gastric mass excision</option>
<option value="358575006">Gastroduodenostomy  ~  Billroth I</option>
<option value="49245001">Gastrojejunostomy  ~  Billroth II</option>
<option value="CA00250P">Groin mass excision</option>
<option value="119766003">Heart defect repair</option>
<option value="287821000">Heart explantation</option>
<option value="CA00349P">Heart mass excision</option>
<option value="32413006">Heart transplant</option>
<option value="87123001">Heart valve excision</option>
<option value="85830006">Heart valve repair</option>
<option value="34068001">Heart valve replacement</option>
<option value="39309004">Hemilaryngectomy</option>
<option value="24496007">Hemorrhoidectomy</option>
<option value="107963000">Hepatectomy</option>
<option value="17203000">Hepatectomy, complete</option>
<option value="71273005">Hepatectomy, partial</option>
<option value="42246001">Hepatojejunostomy</option>
<option value="50465008">Herniorrhaphy</option>
<option value="236886002">Hysterectomy</option>
<option value="86477000">Hysterectomy with bilateral salpingo-oophorectomy</option>
<option value="CA00378P">Hysterectomy with unilateral salpingo-oophorectomy</option>
<option value="116144002">Hysterectomy, abdominal, with bilateral salpingo-oophorectomy, total</option>
<option value="59750000">Hysterectomy, abdominal, with unilateral salpingo-oophorectomy</option>
<option value="387644004">Hysterectomy, supracervical</option>
<option value="29529008">Hysterectomy, supracervical, with bilateral salpingo-oophorectomy, total</option>
<option value="112917009">Hysterectomy, supracervical, with unilateral salpingo-oophorectomy</option>
<option value="51346007">Ileectomy</option>
<option value="CA00375P">Ileocecal mass excision</option>
<option value="83462004">Ileocolectomy</option>
<option value="301784005">Ileostomy</option>
<option value="398182006">Inferior vena caval filter placement</option>
<option value="3418002">Intervertebral disc excision</option>
<option value="103716009">Intravascular stent</option>
<option value="61557004">Joint replacement</option>
<option value="70536003">Kidney transplant</option>
<option value="387731002">Laminectomy</option>
<option value="86481000">Laparotomy</option>
<option value="72791001">Laryngectomy</option>
<option value="3324009">Laser coagulation</option>
<option value="229565001">Laser therapy</option>
<option value="133864008">Lithotripsy</option>
<option value="86259008">Liver biopsy</option>
<option value="18027006">Liver transplant</option>
<option value="392022002">Lumpectomy</option>
<option value="78603008">Lung biopsy</option>
<option value="173171007">Lung lobectomy</option>
<option value="119746007">Lung mass excision</option>
<option value="88039007">Lung transplant</option>
<option value="58347006">Lymphadenectomy</option>
<option value="33496007">Mammoplasty</option>
<option value="5884001">Mastectomy</option>
<option value="27865001">Mastectomy, bilateral</option>
<option value="406505007">Mastectomy, modified radical</option>
<option value="64368001">Mastectomy, partial</option>
<option value="384723003">Mastectomy, radical</option>
<option value="172043006">Mastectomy, total</option>
<option value="68495004">Mediastinal mass excision</option>
<option value="36143002">Muscle excision</option>
<option value="42010004">Myomectomy, uterine</option>
<option value="13529000">Nasal septoplasty</option>
<option value="43584001">Nasal sinus excision</option>
<option value="CA00211P">Nasal turbinate ablation</option>
<option value="119582004">Neck mass excision</option>
<option value="108022006">Nephrectomy</option>
<option value="81516001">Nephrectomy, partial</option>
<option value="CA01462P">Nephrectomy, robotic</option>
<option value="17444001">Omental biopsy</option>
<option value="78384007">Omentectomy</option>
<option value="83152002">Oophorectomy</option>
<option value="76876009">Oophorectomy, bilateral</option>
<option value="386633000">Orchiectomy</option>
<option value="85419002">Orchiopexy</option>
<option value="79881004">Osteotomy</option>
<option value="CA02088P">PAG tissue recovery</option>
<option value="33149006">Pancreatectomy</option>
<option value="287847009">Pancreatectomy, subtotal</option>
<option value="235477008">Pancreaticojejunostomy</option>
<option value="CA00210P">Pancreaticosplenectomy</option>
<option value="116241004">Pancreatoduodenectomy  ~  Whipple</option>
<option value="CA00209P">Paraganglioma excision</option>
<option value="53304009">Parathyroidectomy</option>
<option value="81870009">Parotidectomy</option>
<option value="76186007">Pelvic excision</option>
<option value="52224004">Pelvic exenteration</option>
<option value="CA00251P">Pelvic mass excision</option>
<option value="80855002">Penectomy</option>
<option value="CA00207P">Pericardial excision</option>
<option value="309849004">Pericardiocentesis</option>
<option value="120177005">Peritoneal mass excision</option>
<option value="CA00382P">Pharyngeal mass excision</option>
<option value="23979009">Pleural mass excision</option>
<option value="49795001">Pneumonectomy</option>
<option value="87677003">Proctectomy</option>
<option value="174059005">Proctocolectomy</option>
<option value="90470006">Prostatectomy</option>
<option value="26294005">Prostatectomy, radical</option>
<option value="CA04150P">Prostatectomy, robotic radical</option>
<option value="90199006">Prostatectomy, transurethral  ~  TURP</option>
<option value="363742001">Pyloroplasty</option>
<option value="88141006">Reanastomosis</option>
<option value="12092003">Rectal prolapse surgery</option>
<option value="54164009">Rectosigmoidectomy</option>
<option value="120175002">Retroperitoneal excision</option>
<option value="CA00252P">Retroperitoneal mass excision</option>
<option value="13707001">Revision of stoma</option>
<option value="71735005">Salivary gland excision</option>
<option value="120053002">Salpingectomy</option>
<option value="29827000">Salpingo-oophorectomy, bilateral</option>
<option value="78698008">Salpingo-oophorectomy, unilateral</option>
<option value="275249009">Scoliosis correction</option>
<option value="76311002">Seminal vesicle biopsy</option>
<option value="396487001">Sentinel node biopsy</option>
<option value="88834003">Shunt placement</option>
<option value="67373001">Skin excision</option>
<option value="107938000">Small intestine excision</option>
<option value="75016008">Soft tissue biopsy</option>
<option value="56361007">Soft tissue excision</option>
<option value="CA00253P">Soft tissue mass excision</option>
<option value="55705006">Spinal fusion</option>
<option value="CA11964C">Spinal mass excision</option>
<option value="234319005">Splenectomy</option>
<option value="CA00384P">Sternal mass excision</option>
<option value="71025006">Surgical dilatation</option>
<option value="122478003">Synovium excision</option>
<option value="74795009">Tendon excision</option>
<option value="91602002">Thoracentesis</option>
<option value="177765008">Thoracotomy</option>
<option value="43810009">Thrombectomy</option>
<option value="399750009">Thymectomy</option>
<option value="13619001">Thyroidectomy</option>
<option value="107916002">Tongue mass excision</option>
<option value="173422009">Tonsillectomy</option>
<option value="48387007">Tracheotomy</option>
<option value="77543007">Tubal ligation</option>
<option value="8335001">Uvulopalatopharyngoplasty</option>
<option value="CA01777P">Vagina mass excision</option>
<option value="65756000">Vaginectomy</option>
<option value="65310005">Vagotomy</option>
<option value="119761008">Vascular defect repair</option>
<option value="408463005">Vascular surgery</option>
<option value="60941001">Vein excision</option>
<option value="36901005">Vulvectomy</option>
<option value="CA00004P">Other procedure</option>
</select>&nbsp;<input type="Button"  name="showsampleData.procedureHierarchy" value="..." onClick=" showHierarchyWithSearch('sampleData.procedure','/BIGR/orm/Dispatch?op=GetLIMSDxTcHierarchy&type=P','searchDiagsampleData.procedure');">
<p style="margin: 3px 0 0 0; border: 0; padding: 0;">Other Procedure:&nbsp;<input type="text" name="sampleData.procedureOther" size="30" maxlength="200" value = "N/A" disabled>
 
</div>
<script type="text/javascript"> 
GSBIO.bigr.reg.Elements.register("sampleData.procedure");
GSBIO.bigr.reg.Elements.register("sampleData.procedureOther");
</script>
 
 
 
 
 
 
 
 
 
<div class="kcElement">
<label>
 
Tissue:
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
<option value="80248007" selected>Breast, left</option><option value="CA03650T">Breast, left lateral</option>
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
 
Date of Collection:
</label>
<input type="text" name="sampleData.collectionDateTime.date" value="" readonly="readonly">
&nbsp;
<span class="fakeLink" onclick="openCalendar('sampleData.collectionDateTime.date')">Select Date</span>
&nbsp;&nbsp;&nbsp;&nbsp;
<select name="sampleData.collectionDateTime.hour"><option value="">Hr</option>
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
<option value="12">12</option></select>:
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
&nbsp;
<input type="radio" name="sampleData.collectionDateTime.meridian" value="AM">AM
<input type="radio" name="sampleData.collectionDateTime.meridian" value="PM">PM
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
 
Date of Preservation:
</label>
<input type="text" name="sampleData.preservationDateTime.date" value="" readonly="readonly">
&nbsp;
<span class="fakeLink" onclick="openCalendar('sampleData.preservationDateTime.date')">Select Date</span>
&nbsp;&nbsp;&nbsp;&nbsp;
<select name="sampleData.preservationDateTime.hour"><option value="">Hr</option>
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
<option value="12">12</option></select>:
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
&nbsp;
<input type="radio" name="sampleData.preservationDateTime.meridian" value="AM">AM
<input type="radio" name="sampleData.preservationDateTime.meridian" value="PM">PM
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
 
Gross Appearance:
</label>
<select name="sampleData.grossAppearance"><option value="">Select Gross Appearance</option><option value="D" selected>Gross Diseased</option><option value="M">Gross Mixed</option><option value="N">Gross Normal</option><option value="U">Gross Unknown</option></select>
</div>		    
<script type="text/javascript"> 
GSBIO.bigr.reg.Elements.register("sampleData.grossAppearance");
</script>
 
 
 
 
 
 
 
 
 
<div class="kcElement">
<label>
 
Fixative Type:
</label>
<select name="sampleData.fixativeType"><option value="">Select Fixative Type</option><option value="CA00499C" selected>Formalin</option><option value="CA00500C">Zinc formalin</option></select>
</div>		    
<script type="text/javascript"> 
GSBIO.bigr.reg.Elements.register("sampleData.fixativeType");
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
 
<div class="kcElements bigrElements">
<div class="kcElement bigrElement">
 
<input type="checkbox" name="consumedCheckbox" 
	
  onclick="GSBIO.bigr.reg.adjustVolumeAndWeight();">
  This sample is consumed
 
</div>
</div>
 
<div class="sampleRegButtons">
<p>
 
<input type="submit" id="btnSubmit" name="btnSubmit" value="Update"/>
 
 
<input type="button" id="btnCancel" name="btnCancel" value="Cancel" 
onclick="window.location.href='/BIGR/dataImport/getSampleFormSummary.do?sampleData.sampleId=SX0001D308';"> 

<input type="button" id="btnAttach" name="btnAttach" value="Attachments" 
onclick="getAttachments();"> 
</p>
</div>
 
 
<p style="font-size: 8pt;"><span class="kcRequiredInd">*</span> indicates a required field </p>
 
 
</form>
</body>
</html>

