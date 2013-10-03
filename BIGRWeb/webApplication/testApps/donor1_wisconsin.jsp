
<html>
<head>
<title>Donor Information</title>
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
 
 
<link rel="stylesheet" type="text/css" href="/BIGR/css/bigr.css">
<script language="JavaScript" src="/BIGR/js/common.js"></script>
<script type="text/javascript"> 
 
function initPage() {
  if (parent.topFrame && parent.topFrame.document.all.banner) {
    parent.topFrame.document.all.banner.innerHTML = 'Create Donor';
  }
  
	  document.all.customerId.focus();
  
  
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
 
 
</script>
</head>
 
<body class="bigr" onLoad="initPage()">
<form name="donorprofile" method="POST" action="/BIGR/ddc/Dispatch" onsubmit="return(onFormSubmit());">
<input type="hidden" name="op" value="DonorProfileEdit">
<input type="hidden" name="ardaisId" value="">
<input type="hidden" name="new" value="true">
<input type="hidden" name="importedYN" value="Y">
<input type="hidden" name="ardaisAccountKey" value="HCIT">
<input type="hidden" name="createForm" value="false">
 
 
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
          <div align="center" id="banner" class="banner">Create Donor 
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
        <label class="inlineLabel"><span class="kcRequiredInd">*</span> Donor Alias:</label>
        
          <input type="text" name="customerId" maxlength="30" size="30" value=""> 
        
        
      </div>
 
    </div> <!-- kcElements -->
  
 
 
<input type="hidden" name="form" value=""/>
<script type="text/javascript">GSBIO.kc.data.FormInstances.createFormInstance({"formDefinitionId":"FD00000001"});</script>
 
 
 
<div class="kcElementsContainer">
<div class="kcElements">
 
<div class="kcElement">
<label>
 <span class="DISABLED">CHW MRN:</span>
</label>
<input type="text" name="customerId" maxlength="50" size="51" value="" class="DISABLED">
</div>

<div class="kcElement">
<label>
 <span class="DISABLED">Other MRN:</span>
</label>
<input type="text" name="customerId" maxlength="50" size="51" value="" class="DISABLED">
</div>

<div class="kcElement">
<label>
 <span class="DISABLED">First Name:</span>
</label>
<input type="text" name="customerId" maxlength="50" size="51" value="" class="DISABLED">
</div>

<div class="kcElement">
<label>
 <span class="DISABLED">Middle Initial:</span>
</label>
<input type="text" name="customerId" maxlength="1" size="2" value="" class="DISABLED">
</div>

<div class="kcElement">
<label>
 <span class="DISABLED">Last Name:</span>
</label>
<input type="text" name="customerId" maxlength="50" size="51" value="" class="DISABLED">
</div>
 
<div class="kcElement">
<label>
 <span class="DISABLED">Date of Birth:</span>
</label>
 <input type="text" name="newBirthDate" value="" READONLY size="10" onclick="openCalendar('newBirthDate')" class="DISABLED">
</div>

<div class="kcElement">
<label>
 <span class="DISABLED">Date of Death:</span>
</label>
 <input type="text" name="newDeathDate" value="" READONLY size="10" onclick="openCalendar('newDeathDate')" class="DISABLED">
</div>

<div class="kcElement">
<label>
 <span class="DISABLED">Case History:</span>
</label>
<textarea name="donorProfileNotes" cols="40" rows="4" class="disabled"></textarea>
</div>

<div class="kcElement">
<label>
Gender:
</label>
<input type="radio" name="gender" value="M"> Male
<input type="radio" name="gender" value="F"> Female
<input type="radio" name="gender" value="U"> Unknown
</div>

<div class="kcElement">
<label>
<span class="DISABLED">Postal Code:
</label>
<input type="text" name="zipCode" maxlength="11" size="12" value="" class="DISABLED"> 
</div>

<div class="kcElement">
<label>
<span class="DISABLED">Country:
</label>
<!-- <input type="text" name="Country Code" maxlength="3" size="4" value="" class="DISABLED"> 
<font size="1">If not US</font>-->
<select name="race" class="DISABLED">
<option value="">Select Race</option>
<option value="USA" selected="selected">United States</option>
<option value="UMI">United States Minor Outlying Islands</option>
<option value="UKN">Unknown</option>
</select>
</span>
<br>
<textarea name="donorProfileNotes" cols="30" rows="2" class="disabled"></textarea>
</div>
 
<div class="kcElement">
<label>
<span class="DISABLED">Race:
</label>
<select name="race" class="DISABLED">
<option value="">Select Race</option>
<option value="CA01005C">American Indian</option>
<option value="CA01006C">Asian</option>
<option value="CA01007C">Black</option>
<option value="XXXXXXXX">Declined</option>
<option value="CA01008C">Hawaiian/Pac Island</option>
<option value="XXXXXXXX">Hispanic</option>
<option value="XXXXXXXX">Multiracial</option>
<option value="XXXXXXXX">Not asked</option>
<option value="CA01010C" selected="selected">Other</option>
<option value="XXXXXXXX">Unavailable</option>
<option value="XXXXXXXX">Unknown</option>
<option value="CA01009C">White</option>
<!-- <option value="CA00294C">Not Reported</option>-->
</select></span>
<br>
<textarea name="donorProfileNotes" cols="30" rows="2" class="disabled"></textarea>
</div>

<!-- <div class="kcElement" >
<label>
<span class="RESTRICTED">Ethnicity:
</label>
<select name="ethnicCategory"><option value="">Select Ethnicity</option><option value="CA00970C">Hispanic or Latino</option><option value="CA00971C">Other Ethnicity</option><option value="CA00972C">Unknown Ethnicity</option></select> 
<font size="1">Ethnicity will be removed</font>
</span>
</div>-->
 
 
<div class="kcElement">
<label>
 
Donor Note:
</label>
<textarea name="donorProfileNotes" cols="80" rows="15"></textarea> 
</div>
</div> <!-- kcElements -->
</div>
 
 
 
<div class="sampleRegButtons">
<p>
 
 
  <input type="submit" name="btnSubmit" value="Save">
 
 
							
 
 
 
</p>
</div>
<p style="font-size: 8pt;"><span class="kcRequiredInd">*</span> indicates a required field </p>
</form>
</body>
</html>


