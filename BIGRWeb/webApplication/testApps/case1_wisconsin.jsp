<html>
<head>
<title>Create Case</title>
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
<script language="JavaScript" src="/BIGR/js/bigrTextArea.js"></script>
<script language="JavaScript" src="/BIGR/js/common.js"></script>
<script language="JavaScript" src="/BIGR/js/registration.js"></script>
<script type="text/javascript"> 
 
 
var myBanner = 'Create Case';
 
function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
  GSBIO.bigr.reg.CurrentLinkedValue = $('linkedIndicator').getRadiosValue();
  GSBIO.bigr.reg.showConsentsOrPolicies();
  if (GSBIO.kc.data.FormInstances.getFormInstance()) {    
    GSBIO.kc.data.FormInstances.getFormInstance().alternateRowColor();
  }
 
    GSBIO.bigr.reg.RegFormIds['1'] = 'FD00000002';
 
    GSBIO.bigr.reg.PolicyIds['3'] = '1';
 
    GSBIO.bigr.reg.PolicyIds['2'] = '1';
 
    GSBIO.bigr.reg.PolicyIds['1'] = '1';
 
  $('customerId').focus();
 
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
 
}
 
function enablePrintLabelControls() {
 
}
</script>
</head>
<body class="bigr" onload="initPage();">
<form name="caseForm" method="POST" action="/BIGR/dataImport/createCase.do" onsubmit="return(onFormSubmit());" id="theForm">
  <input type="hidden" name="ardaisAcctKey" value="HCIT">
  <input type="hidden" name="importedYN" value="Y">
  <input type="hidden" name="createForm" value="false">
  <input type="hidden" name="form" value=""/>
 
<div id="errorsDiv" class="errorsMessages">
 
</div>
<div id="messagesDiv" class="errorsMessages">
 
</div>
 <div align="center">
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
          <div align="center" id="banner" class="banner">Create Case 
          </div>
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
 
<div class="kcElements bigrElements">
<div class="kcElement bigrElement">
<label class="inlineLabel"><span class="kcRequiredInd">*</span> Case Alias:</label>
<select name="race" class="DISABLED">
<option value=""></option>
<option value="AC" selected="selected">AC</option>
<option value="AV">AV</option>
<option value="BM">BM</option>
<option value="CY">CY</option>
<option value="GE">GE</option>
<option value="PL">PL</option>
<option value="SC">SC</option>
<option value="SV">SV</option>
<option value="XX">XX</option>
</select>-<input type="text" name="customerId" maxlength="2" size="2" value="10" class="disabled">-<input type="text" name="customerId" maxlength="4" size="4" value="0001" class="disabled">
</div>
 
<div class="kcElement bigrElement">
<label class="inlineLabel"><span class="kcRequiredInd">*</span> Donor:</label>
Donor Id:
<input type="text" name="ardaisId" maxlength="12" size="17" value="">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Or&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Donor Alias:
<input type="text" name="ardaisCustomerId" maxlength="30" size="30" value="">
</div>

<div class="kcElement">
<label>
<span class="DISABLED">Case Category:</span>
</label>
<select name="race" class="DISABLED">
<option value="" selected="selected">Other</option>
<option value="TM">Tumor</option>
<option value="PL">Perinatal</option>
<option value="VC">Vascular</option>
</select>
<br>
<textarea name="donorProfileNotes" cols="50" rows="1" class="disabled"></textarea>
</div>
 
<div class="kcElement">
<label><span class="kcRequiredInd">*</span> Did the donor sign an informed consent?:</label>
<input type="radio" name="linkedIndicator" value="Y" onclick="GSBIO.bigr.reg.showConsentsOrPolicies()">Yes
<input type="radio" name="linkedIndicator" value="N" onclick="GSBIO.bigr.reg.showConsentsOrPolicies()">No
</div>
 
<div class="kcElement">
<label><span class="kcRequiredInd">*</span> Case Policy:</label>
<select name="policyId" onchange="GSBIO.bigr.reg.getCaseRegistrationForm();"><option value="1">Policy Test</option></select>
</div>
 
<div class="kcElement">
<label><span class="kcRequiredInd">*</span> IRB Protocol / Consent Version:</label>
<input type="text" name="searchconsentVersionId" size="40" maxlength="40" onKeyPress="if(event.keyCode == 13){findListOptions(this.form.searchconsentVersionId,this.form.consentVersionId);return false;}"/>&nbsp;<input type="button" value="Search" name="search" onClick="findListOptions(this.form.searchconsentVersionId,this.form.consentVersionId);">&nbsp;<input type="button" value="Refresh" name="refresh" onClick="restoreListOptions(this.form.searchconsentVersionId,this.form.consentVersionId);">
<br>
<label class="inlineLabel">&nbsp;</label> <!--  bogus label for layout -->
<select name="consentVersionId" onchange="GSBIO.bigr.reg.getCaseRegistrationForm();"><option value="">Select</option>
 
 
<option value="1">IRB Test/v1.0</option>
<option value="2">IRB Test/v1.1</option>
<option value="3">IRB Test/v1.1</option></select>
</div>
 
<div id="consentTime" class="kcElement bigrElement" style="display:none;">
<span class="DISABLED"><label class="inlineLabel">Date of Consent:</label></span>
<input type="text" name="newConsentDate" value="" READONLY size="10" onclick="openCalendar('newConsentDate')" class="DISABLED">
</div>

<div class="kcElement">
<label>
<span class="DISABLED">Attending Surgeon:</span>
</label>
<select name="attendingSurgeon" class="DISABLED">
<option value="" selected="selected">Other</option>
<option value="CA01005C">Jaweed Mohammed</option>
<option value="CA01006C">Gail Wiseman</option>
</select></span>
<br>
<textarea name="donorProfileNotes" cols="30" rows="2" class="disabled"></textarea>
</div>

<div class="kcElement">
<label><span class="kcRequiredInd">*</span> <span class="disabled">Clinical Diagnosis:</span></label>
<input type="text" name="searchconsentVersionId" size="20" maxlength="20" onKeyPress="if(event.keyCode == 13){findListOptions(this.form.searchconsentVersionId,this.form.consentVersionId);return false;}"/>&nbsp;<input type="button" value="Search" name="search" onClick="findListOptions(this.form.searchconsentVersionId,this.form.consentVersionId);">&nbsp;<input type="button" value="Refresh" name="refresh" onClick="restoreListOptions(this.form.searchconsentVersionId,this.form.consentVersionId);">
<br>
<label class="inlineLabel">&nbsp;</label> <!--  bogus label for layout -->
<select name="consentVersionId" onchange="GSBIO.bigr.reg.getCaseRegistrationForm();"><option value="">Select</option>
<option value="1">Abscess of tissue</option>
<option value="2">Acrochordon</option>
<option value="3">Actinomycosis</option></select>
</div>
<div class="kcElement">
<label>

<div class="kcElement">
<label><span class="kcRequiredInd">*</span> <span class="disabled">Pathological Diagnosis:</span></label>
<input type="text" name="searchconsentVersionId" size="20" maxlength="20" onKeyPress="if(event.keyCode == 13){findListOptions(this.form.searchconsentVersionId,this.form.consentVersionId);return false;}"/>&nbsp;<input type="button" value="Search" name="search" onClick="findListOptions(this.form.searchconsentVersionId,this.form.consentVersionId);">&nbsp;<input type="button" value="Refresh" name="refresh" onClick="restoreListOptions(this.form.searchconsentVersionId,this.form.consentVersionId);">
<br>
<label class="inlineLabel">&nbsp;</label> <!--  bogus label for layout -->
<select name="consentVersionId" onchange="GSBIO.bigr.reg.getCaseRegistrationForm();"><option value="">Select</option>
<option value="1">Abscess of tissue</option>
<option value="2">Acrochordon</option>
<option value="3">Actinomycosis</option></select>
</div>
<div class="kcElement">
<label>
 
Comments:
</label>
<textarea name="donorProfileNotes" cols="80" rows="15"></textarea> 
</div>
</div> <!-- kcElements -->
 
<div id="policySpecific">
 
</div> <!-- policySpecific -->
 
<div class="sampleRegButtons">
<p>
<input type="submit" id="btnSubmit" name="btnSubmit" value="Save" onclick="onSave();"/>
</p>
 
</div>
<p style="font-size: 8pt;"><span class="kcRequiredInd">*</span> indicates a required field </p>
</form>
</body>
</html>



