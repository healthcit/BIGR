 
<html>
<head>
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<link rel="stylesheet" type="text/css" href="/BIGR/css/bigr.css">
<title>Manage License</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" src="/BIGR/js/common.js"></script>
<script language="JavaScript" src="/BIGR/js/linkedlist.js"></script>
<script language="JavaScript" src="/BIGR/js/map.js"></script>
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
  window.open('/BIGR/testApps/maintain_Liscense3.jsp','modifyLicense','toolbar=no,menubar=no,resizable=yes,status=yes,width=550,height=200,left=400,top=200')
 
}
function onAddClick(str){
  window.open('/BIGR/testApps/userDefinedRoles.jsp','modifyLicense','toolbar=no,menubar=no,resizable=yes,status=yes,width=700,height=600,left=400,top=200')
}
</script>
</head>
<body class="bigr" onload="initPage();">
<form name="managePrivilegesForm" method="post" action="/BIGR/orm/managePrivileges.do">
 
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
              <td colspan="2"> 
                <div align="center"><b>Manage License</b></div>
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey"> 
                <div align="right"><b><font color="Red">*</font>Account:</b></div>
              </td>
              <td> 
                <select name="userData.accountId">
                <option value="">Select</option>
                <option value="HCIT">HealthCare IT (HCIT)</option>
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
                  <img src="/BIGR/images/MenuClosed.gif" border="0" alt="Expand All">
                </button>
              </td>
              <td align="center"> 
                <b>Role</b>
              </td>
              <td align="center"> 
                <b>Limit</b>
              </td>
              <td align="center"> 
                <b>Expiration Date</b>
              </td>
              <td align="center"> 
                &nbsp;
              </td>
            </tr>
              <tr class="white"> 
               <td align="center"> 
               	<button type="button" name="btnDelete" value="Expand"
                    style="height: 16px; width: 16px;"
                    onclick="onClickDelete('RL00000015');">
                  <img src="/BIGR/images/MenuOpened.gif" border="0" alt="Collapse">
                </button>
              </td>
                <td>
                 <a id="linkEditRole"
                   href="userDefinedRoles1.jsp"
                   onclick="onModifyClick('HCITTEST');">
                  Full Operational User</a>
                </td>
                <td align="center">
                    <font color="green"> 5</font>
                </td>
                <td>
                 12/31/2010
                </td>
                <td>
                   <input type="button" name="btnModify" value="Add" onclick="onAddClick('HCITTEST');">
                </td>
              </tr>
               <tr class="stCellLastOdd"> 
               <td colspan="1">
                  1
                </td>
                <td colspan="1" align="left">
                 <a id="linkEditRole"
                   href="userDefinedRoles1.jsp"
                  onclick="onAddClick('HCITTEST');">
                  Operations Personnel</a>
                </td>
                <td align="center">
                    3
                </td>
                 <td colspan="1" align="center">
                  -
                </td>
                <td>
                <button type="button" name="btnDelete" value="Delete"
                    style="height: 16px; width: 16px;"
                    onclick="onClickDelete('RL00000015');">
                  <img src="/BIGR/images/delete.gif" border="0" alt="Delete">
                </button>
                </td>
              </tr>
                <tr class="stCellLastOdd"> 
               <td colspan="1">
                 2
                </td>
                <td colspan="1" align="left">
                 <a id="linkEditRole"
                   href="userDefinedRoles1.jsp"
                  onclick="onAddClick('HCITTEST');">
                 Operations Support</a>
                </td>
                 <td align="center">
                  1
                </td>
                 <td colspan="1" align="center">
                  -
                </td>
                 <td>
                <button type="button" name="btnDelete" value="Delete"
                    style="height: 16px; width: 16px;"
                    onclick="onClickDelete('RL00000015');">
                  <img src="/BIGR/images/delete.gif" border="0" alt="Delete">
                </button>
                </td>
              </tr>
               <tr class="white"> 
                <td align="center"> 
               	<button type="button" name="btnDelete" value="Expand"
                    style="height: 16px; width: 16px;"
                    onclick="onClickDelete('RL00000015');">
                  <img src="/BIGR/images/MenuOpened.gif" border="0" alt="Collapse">
                </button>
              </td>
                <td> <a id="linkEditRole"
                   href="userDefinedRoles1.jsp"
                   onclick="onModifyClick('HCITTEST');">
                Account Administrator</a>
                </td>
                 <td align="center">
                  <font color="red">  2</font>
                </td>
                <td>
               12/31/2010
                </td>
                <td>
                   <input type="button" name="btnModify" value="Add" onclick="onAddClick('HCITTEST');">
                </td>
              </tr>
               <tr class="stCellLastOdd"> 
               <td colspan="1">
                  1
                </td>
                <td colspan="1" align="left">
                 <a id="linkEditRole"
                   href="userDefinedRoles1.jsp"
                  onclick="onAddClick('HCITTEST');">
                  System Admin</a>
                </td>
                 <td align="center">
                  2
                </td>
                <td colspan="1" align="center">
                  -
                </td>
                 <td>
                  <button type="button" name="btnDelete" value="Delete"
                    style="height: 16px; width: 16px;"
                    onclick="onClickDelete('RL00000015');">
                  <img src="/BIGR/images/delete.gif" border="0" alt="Delete">
                </button>
                </td>
              </tr>
               <tr class="white"> 
                <td align="center"> 
               	<button type="button" name="btnDelete" value="Expand"
                    style="height: 16px; width: 16px;"
                    onclick="onClickDelete('RL00000015');">
                  <img src="/BIGR/images/MenuClosed.gif" border="0" alt="Expand">
                </button>
              </td>
                <td> <a id="linkEditRole"
                    href="userDefinedRoles1.jsp"
                   onclick="onModifyClick('HCITTEST');">
                  Modified Operational User</a>
                </td>
                 <td align="center">
                  <font color="green">  3</font>
                </td>
                <td>
                  12/31/2010
                </td>
                <td>
                   <input type="button" name="btnModify" value="Add" onclick="onAddClick('HCITTEST');">
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

