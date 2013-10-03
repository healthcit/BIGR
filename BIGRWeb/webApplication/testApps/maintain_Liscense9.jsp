
<html>
<head>
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<link rel="stylesheet" type="text/css" href="/BIGR/css/bigr.css">
<title>Manage License</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" src="/BIGR/js/common.js"></script>
<script language="JavaScript" src="/BIGR/js/linkedlist.js"></script>
<script language="JavaScript" src="/BIGR/js/map.js"></script>
<script language="JavaScript" src="/BIGR/js/calendar.js"></script>

<script language="JavaScript"> 
<!--
 

function initPage() {
  var myBanner = 'Manage Privileges for Preeti Gupta (PreetiTest)';
  if (parent.topFrame && parent.topFrame.document.all.banner) {
    parent.topFrame.document.all.banner.innerText = myBanner;
  }
 // refreshPrivilegeChoices();
}
 
function disableActionButtons() {
	document.all.btnAdd.disabled = true;
	document.all.btnRemove.disabled = true;
	document.all.btnSubmit.disabled = true;
	document.all.btnReset.disabled = true;
	document.all.btnCancel.disabled = true;
}
 
function onSubmitClick() {
  disableActionButtons();
  //select all the entries in the selected dropdown so they are submitted
  var control = document.forms[0]["selectedPrivileges"];
  var len = control.length;
  for (var i=0; i<len; i++) {
	control[i].selected = true;
  }
  document.forms[0].submit();
}
 
function onResetClick() {
  disableActionButtons();
  document.forms[0].action="/BIGR/orm/managePrivilegesStart.do";
  document.forms[0].submit();
}
 
function onCancelClick() {
  disableActionButtons();
  
  document.forms[0].action="/BIGR/orm/users/modifyUserStart.do";
  document.forms[0].submit();
}

function onModifyClick(str){
  window.open('/BIGR/testApps/maintain_Liscense3.jsp','modifyLicense','resizable=yes,status=yes,width=550,height=400,left=400,top=200')

}
</script>
</head>
<body class="bigr" onload="initPage();">
<form name="managePrivilegesForm" method="post" action="/BIGR/orm/managePrivileges.do">
 <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr> 
              <td align="left"> 
               <font color="Red">*</font> Required
              </td>
              <td width="60%"> 
                &nbsp;
              </td>
              <td align="right"> 
                <input type="button" name="btnModify" value="Submit" onclick="doNoting();">
                <input type="button" name="btnCancel" value="Cancel" onclick="window.close();">
              </td>
     </tr>
	</table>
 
  <div id="errorDiv" align="center">
    <table width="100%" border="1" cellspacing="1" cellpadding="1" class="foreground-small">
      
      
	</table>
  </div>
  <p>
<input type="hidden" name="objectType" value="User">
<input type="hidden" name="userData.userId" value="PGUPTA">
<input type="hidden" name="userData.accountId" value="PreetiTest">
<input type="hidden" name="userData.firstName" value="Preeti">
<input type="hidden" name="userData.lastName" value="Gupta">
<input type="hidden" name="accountData.id" value="">
<input type="hidden" name="accountData.name" value="">
  <div align="center">
  <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr>
        <td> 
          <table class="foreground" cellpadding="3" cellspacing="1" border="0">
            <tr class="lightGrey"> 
              <td colspan="6"> 
                <div align="center"><b>Manage License</b></div>
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey"> 
                <div align="right"><b>Account:</b></div>
              </td>
              <td colspan="5"> 
                HealthCare IT (HCIT)
              </td>
            </tr>
             <tr class="white"> 
              <td class="grey"> 
                <div align="right"><b>Privileges:</b></div>
              </td>
               <td colspan="5">
                Add History Note
              </td>
            </tr>
             <tr class="white"> 
              <td class="grey"> 
                <div align="right"><b><font color="Red">*</font>Limit:</b></div>
              </td>
              <td> 
               <select name="userData.accountId">
                <option value="0">0</option>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5" selected="selected">5</option>
                <option value="6">6</option>
                <option value="7">7</option>
                <option value="8">8</option>
                <option value="9">9</option>
                <option value="10">10</option>
                </select>

              </td>
               <td class="grey"> 
                <div align="right"><b><font color="Red">*</font>Expiration Date:</b></div>
              </td>
              <td> 
              <input type="text" name="newExpirationDate" value="12/31/2010" READONLY size="10" onclick="openCalendar('newExpirationDate')">
              </td>
              <td class="grey"> 
                <div align="right"><b><font color="Red">*</font>Status:</b></div>
              </td>
              <td> 
              <select name="license.status">
                <option value="A" selected="selected">Active</option>
                <option value="I">Inactive</option>
                </select>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  <div class="ln">&nbsp;</div>
   <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table cellpadding="3" cellspacing="1" border="0" class="foreground">
            <tr class="lightGrey"> 
            <td align="center"> 
               	<button type="button" name="btnDelete" value="Expand"
                    style="height: 16px; width: 16px;"
                    onclick="onClickDelete('RL00000015');">
                  <img src="/BIGR/images/MenuClosed.gif" border="0" alt="Delete">
                </button>
              </td>
              <td align="center"> 
                <b>Privilege</b>
              </td>
              <td align="center"> 
                <b>Current<br>Limit</b>
              </td>
              <td align="center"> 
                <b>Current<br>Expiration Date</b>
              </td>
              <td align="center"> 
                <b>Current<br>Status</b>
              </td>
            </tr>
              <tr class="white"> 
              <td align="center"> 
               	<button type="button" name="btnDelete" value="Expand"
                    style="height: 16px; width: 16px;"
                    onclick="onClickDelete('RL00000015');">
                  <img src="/BIGR/images/MenuClosed.gif" border="0" alt="Delete">
                </button>
              </td>
                <td>
                  Add History Note
                </td>
                <td align="center">
                    5
                </td>
                <td>
                  12-31-2010
                </td>
                <td>
                    Active
                </td>
              </tr>
            
              <tr class="grey"> 
              <td align="center"> 
               	<button type="button" name="btnDelete" value="Expand"
                    style="height: 16px; width: 16px;"
                    onclick="onClickDelete('RL00000015');">
                  <img src="/BIGR/images/MenuOpened.gif" border="0" alt="Delete">
                </button>
              </td>
                <td>
                  Add Samples To Inventory Group 
                </td>
                <td align="center">
                    5
                </td>
                <td>
                  12/31/2010
                </td>
                <td>
                    Active
                </td>
              </tr>
              <tr class="grey"> 
               <td colspan="1">
                  &nbsp;
                </td>
                <td colspan="4">
                  Account Administrator 
                </td>
              </tr>
               <tr class="white"> 
               <td colspan="1">
                  &nbsp;
                </td>
                <td colspan="4">
                  Other User
                </td>
              </tr>
               <tr class="white"> 
               <td align="center"> 
               	<button type="button" name="btnDelete" value="Expand"
                    style="height: 16px; width: 16px;"
                    onclick="onClickDelete('RL00000015');">
                  <img src="/BIGR/images/MenuClosed.gif" border="0" alt="Delete">
                </button>
              </td>
                <td>
                  Box Scan  
                </td>
                 <td align="center">
                    0
                </td>
                <td>
                  
                </td>
                <td>
                    Inactive
                </td>
              </tr>
               <tr class="grey"> 
               <td align="center"> 
               	<button type="button" name="btnDelete" value="Expand"
                    style="height: 16px; width: 16px;"
                    onclick="onClickDelete('RL00000015');">
                  <img src="/BIGR/images/MenuClosed.gif" border="0" alt="Delete">
                </button>
              </td>
                <td>
                  Case Profile Entry (SR) 
                </td>
                <td align="center">
                    0
                </td>
                <td>
                  
                </td>
                <td>
                    Inactive
                </td>
              </tr>
               <tr class="white"> 
               <td align="center"> 
               	<button type="button" name="btnDelete" value="Expand"
                    style="height: 16px; width: 16px;"
                    onclick="onClickDelete('RL00000015');">
                  <img src="/BIGR/images/MenuClosed.gif" border="0" alt="Delete">
                </button>
              </td>
                <td>
                  Change Your Profile  
                </td>
                 <td align="center">
                    0
                </td>
                <td>
                  
                </td>
                <td>
                    Inactive
                </td>
              </tr>
               <tr class="grey"> 
               <td align="center"> 
               	<button type="button" name="btnDelete" value="Expand"
                    style="height: 16px; width: 16px;"
                    onclick="onClickDelete('RL00000015');">
                  <img src="/BIGR/images/MenuOpened.gif" border="0" alt="Delete">
                </button>
              </td>
                <td>
                 Check Box out of Inventory 
                </td>
                <td align="center">
                    0
                </td>
                <td>
                 
                </td>
                <td>
                    Inactive
                </td>
              </tr>
          </table>
        </td>
      </tr>
    </table>
     
  </div>
</form>
</body>
</html>
