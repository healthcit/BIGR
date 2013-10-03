 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
<html>
<head>
<title>Maintain Roles</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="/BIGR/css/bigr.css" type="text/css">
<script language="JavaScript" src="/BIGR/js/common.js"></script>
<script language="JavaScript" src="/BIGR/js/linkedlist.js"></script>
<script language="JavaScript" src="/BIGR/js/map.js"></script>
<script language="JavaScript" src="/BIGR/js/privilege.js"></script>
<script language="JavaScript" src="/BIGR/js/user.js"></script>
<script type="text/javascript"> 
<!--
 
var myBanner = 'Maintain Roles';
var isWarnOnNavigate = true;
 
function initPage() {
  if (parent.topFrame && parent.topFrame.document.all.banner) {
    parent.topFrame.document.all.banner.innerText = myBanner;
  }
  refreshPrivilegeChoices();
  refreshUserChoices();
  document.forms[0]['role.name'].focus();
}
 
function confirmNavigate() {
  if (needNavagationWarning()) {
    event.returnValue = "You have made changes that you have not submitted yet.  Are you sure that you want to continue?";
  }
}
 
function needNavagationWarning() {
  if (! isWarnOnNavigate) return false;
  
  var f = document.forms[0];
  if (f['role.name'].value != f['role.name'].defaultValue) {
    return true;
  }
  
  return false;
}
 
function onClickCancel() {
  setActionButtonEnabling(false);
  isWarnOnNavigate = false;
  window.location = '/BIGR/orm/role/maintainRoleStart.do';
}
 
function onClickDelete(roleId) {
  setActionButtonEnabling(false);
  var f = document.forms[0];
  var cancelAction = false;
  var savedOperation = f['operation'].value;
  var savedRoleId = f['role.id'].value;
  f['operation'].value = 'Delete';
  f['role.id'].value = roleId;
 
  if (needNavagationWarning()) {
    if (!confirm("You have made changes that have not been saved.  Are you sure you want to continue?\n\nPress OK to continue, or Cancel to stay on the current page.")) {
      cancelAction = true;
    }
  }
  
  if (!cancelAction && !onFormSubmit()) {
    cancelAction = true;
  }
  
  if (cancelAction) { // restore field values
    f['operation'].value = savedOperation;
    f['role.id'].value = savedRoleId;
    setActionButtonEnabling(true);
  }
  else {
    isWarnOnNavigate = false;
    f.submit();
  }
}
 
function onFormSubmit() {
  setActionButtonEnabling(false);
  isWarnOnNavigate = false;
  //select all the entries in the selected privileges dropdown so they are submitted
  var control = document.forms[0]["selectedPrivileges"];
  var len = control.length;
  for (var i=0; i<len; i++) {
	  control[i].selected = true;
  }
  //select all the entries in the selected users dropdown so they are submitted
  var control = document.forms[0]["selectedUsers"];
  var len = control.length;
  for (var i=0; i<len; i++) {
	  control[i].selected = true;
  }
 
  return true;
}
   
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.forms[0];
  f.btnSubmit.disabled = isDisabled;
  f.btnCancel.disabled = isDisabled;
  setInputEnabled(f, "btnDelete", isEnabled);
  setInputEnabled(document.all, "linkEditRole", isEnabled);
}
 
function onAccountChange() {
  var control = document.forms[0]["selectedUsers"];
  control.length = 0;
  refreshUserChoices();
}
 
function addPrivileges() {
  var availableControl = document.forms[0]["availablePrivileges"];
  var assignedControl = document.forms[0]["selectedPrivileges"];
  var len = availableControl.length;
  for (var i=0; i<len; i++) {
    if (availableControl[i].selected) {
	    var opt = availableControl[i];
	    availableControl.remove(i);
	    assignedControl[assignedControl.length] = opt;
	    opt.selected = false;
	    i--; // decr i because next elem shifted down to current
	    len--; // decr len because list is now smaller
	  }
  }
}
 
function addUsers() {
  var availableControl = document.forms[0]["availableUsers"];
  var assignedControl = document.forms[0]["selectedUsers"];
  var len = availableControl.length;
  for (var i=0; i<len; i++) {
    if (availableControl[i].selected) {
	    var opt = availableControl[i];
	    availableControl.remove(i);
	    assignedControl[assignedControl.length] = opt;
	    opt.selected = false;
	    i--; // decr i because next elem shifted down to current
	    len--; // decr len because list is now smaller
	  }
  }
}
 
function removePrivileges() {
  var availableControl = document.forms[0]["availablePrivileges"];
  var assignedControl = document.forms[0]["selectedPrivileges"];
  var len = assignedControl.length;
  for (var i=0; i<len; i++) {
	  if (assignedControl[i].selected) {
	    var opt = assignedControl[i];
	    assignedControl.remove(i);
      i--; // decr i because next elem shifted down to current
	    len--; // decr len because list is now smaller
	  }
  }
  refreshPrivilegeChoices();
}
 
function removeUsers() {
  var availableControl = document.forms[0]["availableUsers"];
  var assignedControl = document.forms[0]["selectedUsers"];
  var len = assignedControl.length;
  for (var i=0; i<len; i++) {
	  if (assignedControl[i].selected) {
	    var opt = assignedControl[i];
	    assignedControl.remove(i);
      i--; // decr i because next elem shifted down to current
	    len--; // decr len because list is now smaller
	  }
  }
  refreshUserChoices();
}
 
function getPrivilegeFilter() {
  var value;
  var control = document.forms[0]["privilegeFilter"];
  for (i = 0; i < control.length; i++) {
    if (control[i].checked) {
      value = control[i].value;
      break;
    }
  }
  return value;
}
 
//function to refresh the "Available" dropdown with the correct set of privileges.  
//Only those privileges that are not in the "Assigned" dropdown will be shown	
function refreshPrivilegeChoices() {
  var functionalArea = getPrivilegeFilter();
  var control = document.forms[0]["availablePrivileges"];
  control.length = 0;
  if (privilegeMap.containsKey(functionalArea)) {
    var privilegeList = privilegeMap.get(functionalArea).getItem();
    for (var privilege = privilegeList.getFirstPrivilege(); privilege != null; privilege = privilegeList.getNextPrivilege()) {
      if (!isPrivilegeAssigned(privilege.getId())) {
        control.options[control.length] = new Option(privilege.getDescription(),
                                                   privilege.getId(),
                                                   false, false);
      }
    }
  }
}
 
//function to refresh the "Available" dropdown with the correct set of users.  
//Only those users that are not in the "Assigned" dropdown will be shown	
function refreshUserChoices() {
  var control = document.forms[0]["availableUsers"];
  control.length = 0;
  var f = document.forms[0];
  var currentAccount = f['role.accountId'].value;
  if (userMap.containsKey(currentAccount)) {
    var userList = userMap.get(currentAccount).getItem();
    for (var user = userList.getFirstUser(); user != null; user = userList.getNextUser()) {
      if (!isUserAssigned(user.getUserId())) {
        control.options[control.length] = new Option(user.getFullName() + " (" + user.getUserId() + ")",
                                                 user.getUserId(),
                                                 false, false);
      }
    }
  }
}
 
//function to determine if a privilege is in the "Assigned" dropdown.
function isPrivilegeAssigned(id) {
  var control = document.forms[0]["selectedPrivileges"];
  var len = control.length;
  for (var i=0; i<len; i++) {
	  if (control[i].value == id) {
	    return true;
	  }
  }
  return false;
}
 
//function to determine if a user is in the "Assigned" dropdown.
function isUserAssigned(id) {
  var control = document.forms[0]["selectedUsers"];
  var len = control.length;
  for (var i=0; i<len; i++) {
    if (control[i].value == id) {
      return true;
    }
  }
  return false;
}
 
//map to keep track of all privileges, keyed by functional area
var privilegeMap = new Map();
//map to keep track of all users, keyed by account
var userMap = new Map();
 
 
var privilegeList = new PrivilegeList();
 
var privilege = new Privilege();
privilege.setId('10_ILTDS_150_TXNOTE');
privilege.setDescription('Add History Note');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('10_ILTDS_045_NBOXSCAN');
privilege.setDescription('Box Scan');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('10_ILTDS_050_CKOUTINV');
privilege.setDescription('Check Box out of Inventory');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('10_ILTDS_090_ACOINV');
privilege.setDescription('Check Samples out of Inventory');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('10_ILTDS_104_CRTMAN');
privilege.setDescription('Create Manifest');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('10_ILTDS_145_ICP');
privilege.setDescription('ICP');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('12_ILTDS_10_MNGREQS');
privilege.setDescription('Manage Requests');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('10_ILTDS_085_ABOXSCAN');
privilege.setDescription('Owner Box Scan');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('10_ILTDS_114_PRNMNFT');
privilege.setDescription('Print Manifests');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('12_ILTDS_21_PATH_REQ');
privilege.setDescription('Privilege: Create Pathology Requests');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('12_ILTDS_22_RAND_REQ');
privilege.setDescription('Privilege: Create RandD Requests');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('10_ILTDS_147_ICP_SUSR');
privilege.setDescription('Privilege: ICP Super User');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('10_ILTDS_120_RCPTVERF');
privilege.setDescription('Receipt Verification');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('12_ILTDS_10_REQSAMP');
privilege.setDescription('Request Samples');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('10_ILTDS_107_SCANPACK');
privilege.setDescription('Scan and Package');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('10_ILTDS_122_SCANSTOR');
privilege.setDescription('Scan and Store');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('10_ILTDS_055_UPDBXLOC');
privilege.setDescription('Update Box Location');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('10_ILTDS_100_UPDBXLOC');
privilege.setDescription('Update Box Location at Owner');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('12_ILTDS_10_VIEWREQS');
privilege.setDescription('View Requests');
privilegeList.addPrivilege(privilege);
 
privilegeMap.put('ILTDS', privilegeList);
 
var privilegeList = new PrivilegeList();
 
var privilege = new Privilege();
privilege.setId('40_ORM_005_ACCTMNG');
privilege.setDescription('Account Management');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('40_ORM_037_ADDTOIG');
privilege.setDescription('Add Samples To Inventory Group');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('30_ORM_050_DETVIEWER');
privilege.setDescription('DET Viewer');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('40_ORM_062_FORMCREATE');
privilege.setDescription('Form Designer');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('40_ORM_040_MAINBXLYS');
privilege.setDescription('Maintain Box Layouts');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('40_ORM_035_MAINTLR');
privilege.setDescription('Maintain Inventory Groups');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('40_ORM_030_PLCYMGR');
privilege.setDescription('Maintain Policies');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('40_ORM_015_MAINTROLES');
privilege.setDescription('Maintain Roles');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('40_ORM_060_MAINTURLS');
privilege.setDescription('Maintain URLs');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('40_ORM_VIEW_ALL_LRS');
privilege.setDescription('Privilege: View All Logical Repositories');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('40_ORM_038_MOVEFROMIG');
privilege.setDescription('Remove Samples From Inventory Group');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('40_ORM_010_USERMNG');
privilege.setDescription('User Management');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('30_ORM_070_VIEWGBOSS');
privilege.setDescription('View GBOSS Data');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('40_ORM_065_VIEWSYSCFG');
privilege.setDescription('View System Configuration');
privilegeList.addPrivilege(privilege);
 
privilegeMap.put('ORM', privilegeList);
 
var privilegeList = new PrivilegeList();
 
privilegeMap.put('PDC', privilegeList);
 
var privilegeList = new PrivilegeList();
 
var privilege = new Privilege();
privilege.setId('40_ORM_005_ACCTMNG');
privilege.setDescription('Account Management');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('10_ILTDS_150_TXNOTE');
privilege.setDescription('Add History Note');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('40_ORM_037_ADDTOIG');
privilege.setDescription('Add Samples To Inventory Group');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('10_ILTDS_045_NBOXSCAN');
privilege.setDescription('Box Scan');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('50_IMP_45_CASEPRF');
privilege.setDescription('Case Profile Entry (SR)');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('15_ES_050_PROFILE');
privilege.setDescription('Change Your Profile');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('10_ILTDS_050_CKOUTINV');
privilege.setDescription('Check Box out of Inventory');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('10_ILTDS_090_ACOINV');
privilege.setDescription('Check Samples out of Inventory');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('50_IMP_60_CLINDATAEXT');
privilege.setDescription('Clinical Data Extraction (SR)');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('50_IMP_20_CREATECASE');
privilege.setDescription('Create Case (SR)');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('50_IMP_10_CREATEDONOR');
privilege.setDescription('Create Donor (SR)');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('10_ILTDS_104_CRTMAN');
privilege.setDescription('Create Manifest');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('50_IMP_35_CREATESAMP');
privilege.setDescription('Create Sample (SR)');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('30_ORM_050_DETVIEWER');
privilege.setDescription('DET Viewer');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('50_IMP_70_DERIV');
privilege.setDescription('Derivative Operations');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('50_IMP_65_FINDBYID');
privilege.setDescription('Find by Id (SR)');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('40_ORM_062_FORMCREATE');
privilege.setDescription('Form Designer');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('10_ILTDS_145_ICP');
privilege.setDescription('ICP');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('40_ORM_040_MAINBXLYS');
privilege.setDescription('Maintain Box Layouts');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('40_ORM_035_MAINTLR');
privilege.setDescription('Maintain Inventory Groups');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('40_ORM_030_PLCYMGR');
privilege.setDescription('Maintain Policies');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('40_ORM_015_MAINTROLES');
privilege.setDescription('Maintain Roles');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('40_ORM_060_MAINTURLS');
privilege.setDescription('Maintain URLs');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('12_ILTDS_10_MNGREQS');
privilege.setDescription('Manage Requests');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('50_IMP_25_MODIFYCASE');
privilege.setDescription('Modify Case (SR)');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('50_IMP_15_MODIFYDONOR');
privilege.setDescription('Modify Donor (SR)');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('50_IMP_40_MODIFYSAMP');
privilege.setDescription('Modify Sample (SR)');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('10_ILTDS_085_ABOXSCAN');
privilege.setDescription('Owner Box Scan');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('50_IMP_55_PATHREPABS');
privilege.setDescription('Path Report Abstraction (SR)');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('50_IMP_50_PATHREPFULL');
privilege.setDescription('Path Report Full Text (SR)');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('50_IMP_90_PRNTLBLS');
privilege.setDescription('Print Labels');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('10_ILTDS_114_PRNMNFT');
privilege.setDescription('Print Manifests');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('15_ES_HOLDLIST_ADDRMV');
privilege.setDescription('Privilege: Add/Remove from Hold List');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('12_ILTDS_21_PATH_REQ');
privilege.setDescription('Privilege: Create Pathology Requests');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('12_ILTDS_22_RAND_REQ');
privilege.setDescription('Privilege: Create RandD Requests');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('15_ES_HOLDSOLD_ACCNT');
privilege.setDescription('Privilege: Hold/Sold Account');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('15_ES_HOLDSOLD_USER');
privilege.setDescription('Privilege: Hold/Sold User');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('10_ILTDS_147_ICP_SUSR');
privilege.setDescription('Privilege: ICP Super User');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('15_ES_SQL_TAB');
privilege.setDescription('Privilege: Show SQL Tab');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('40_ORM_VIEW_ALL_LRS');
privilege.setDescription('Privilege: View All Logical Repositories');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('15_ES_CONSENT_VIEW');
privilege.setDescription('Privilege: View Consent');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('50_IMP_30_CASEPULL');
privilege.setDescription('Pull Case (SR)');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('10_ILTDS_120_RCPTVERF');
privilege.setDescription('Receipt Verification');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('40_ORM_038_MOVEFROMIG');
privilege.setDescription('Remove Samples From Inventory Group');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('12_ILTDS_10_REQSAMP');
privilege.setDescription('Request Samples');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('15_ES_006_SS');
privilege.setDescription('Sample Selection');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('10_ILTDS_107_SCANPACK');
privilege.setDescription('Scan and Package');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('10_ILTDS_122_SCANSTOR');
privilege.setDescription('Scan and Store');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('10_ILTDS_055_UPDBXLOC');
privilege.setDescription('Update Box Location');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('10_ILTDS_100_UPDBXLOC');
privilege.setDescription('Update Box Location at Owner');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('40_ORM_010_USERMNG');
privilege.setDescription('User Management');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('30_ORM_070_VIEWGBOSS');
privilege.setDescription('View GBOSS Data');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('12_ILTDS_10_VIEWREQS');
privilege.setDescription('View Requests');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('15_ES_010_VIEWITEM');
privilege.setDescription('View Selected Items');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('40_ORM_065_VIEWSYSCFG');
privilege.setDescription('View System Configuration');
privilegeList.addPrivilege(privilege);
 
privilegeMap.put('ALL', privilegeList);
 
var privilegeList = new PrivilegeList();
 
privilegeMap.put('LIMS', privilegeList);
 
var privilegeList = new PrivilegeList();
 
var privilege = new Privilege();
privilege.setId('15_ES_050_PROFILE');
privilege.setDescription('Change Your Profile');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('15_ES_HOLDLIST_ADDRMV');
privilege.setDescription('Privilege: Add/Remove from Hold List');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('15_ES_HOLDSOLD_ACCNT');
privilege.setDescription('Privilege: Hold/Sold Account');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('15_ES_HOLDSOLD_USER');
privilege.setDescription('Privilege: Hold/Sold User');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('15_ES_SQL_TAB');
privilege.setDescription('Privilege: Show SQL Tab');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('15_ES_CONSENT_VIEW');
privilege.setDescription('Privilege: View Consent');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('15_ES_006_SS');
privilege.setDescription('Sample Selection');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('15_ES_010_VIEWITEM');
privilege.setDescription('View Selected Items');
privilegeList.addPrivilege(privilege);
 
privilegeMap.put('ES', privilegeList);
 
var privilegeList = new PrivilegeList();
 
var privilege = new Privilege();
privilege.setId('50_IMP_45_CASEPRF');
privilege.setDescription('Case Profile Entry (SR)');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('50_IMP_60_CLINDATAEXT');
privilege.setDescription('Clinical Data Extraction (SR)');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('50_IMP_20_CREATECASE');
privilege.setDescription('Create Case (SR)');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('50_IMP_10_CREATEDONOR');
privilege.setDescription('Create Donor (SR)');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('50_IMP_35_CREATESAMP');
privilege.setDescription('Create Sample (SR)');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('50_IMP_70_DERIV');
privilege.setDescription('Derivative Operations');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('50_IMP_65_FINDBYID');
privilege.setDescription('Find by Id (SR)');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('50_IMP_25_MODIFYCASE');
privilege.setDescription('Modify Case (SR)');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('50_IMP_15_MODIFYDONOR');
privilege.setDescription('Modify Donor (SR)');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('50_IMP_40_MODIFYSAMP');
privilege.setDescription('Modify Sample (SR)');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('50_IMP_55_PATHREPABS');
privilege.setDescription('Path Report Abstraction (SR)');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('50_IMP_50_PATHREPFULL');
privilege.setDescription('Path Report Full Text (SR)');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('50_IMP_90_PRNTLBLS');
privilege.setDescription('Print Labels');
privilegeList.addPrivilege(privilege);
 
var privilege = new Privilege();
privilege.setId('50_IMP_30_CASEPULL');
privilege.setDescription('Pull Case (SR)');
privilegeList.addPrivilege(privilege);
 
privilegeMap.put('IMP', privilegeList);
 
 
var userList = new UserList();
 
var user = new User();
user.setUserId('LLOCASCIO');
user.setFirstName('LISA');
user.setLastName('LOCASCIO');
userList.addUser(user);
 
userMap.put('HCITTEST', userList);
 
var userList = new UserList();
 
var user = new User();
user.setUserId('PGUPTA');
user.setFirstName('Preeti');
user.setLastName('Gupta');
userList.addUser(user);
 
userMap.put('PreetiTest', userList);
 
var userList = new UserList();
 
var user = new User();
user.setUserId('ituser');
user.setFirstName('BOOTSTRAP USER');
user.setLastName('BOOTSTRAP USER');
userList.addUser(user);
 
var user = new User();
user.setUserId('jmohammed');
user.setFirstName('Jaweed');
user.setLastName('Mohammed');
userList.addUser(user);

var user = new User();
user.setUserId('Gwiseman');
user.setFirstName('Gail');
user.setLastName('Wiseman');
userList.addUser(user);
 
userMap.put('HCIT', userList);
 
-->
</script>
</head>
<body class="bigr" onload="initPage();" onbeforeunload="confirmNavigate();">
<form name="maintainRoleForm" method="POST" action="/BIGR/orm/role/maintainRoleSave.do" onsubmit="return(onFormSubmit());">
  <input type="hidden" name="operation" value="Create">
  <input type="hidden" name="role.id" value="">
  <div align="center">
    <table border="0" cellspacing="0" cellpadding="0" class="background">
      
      <tr> 
        <td> 
          <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
	        
	        
            <tr class="white">
            
              <td colspan="2" align="center">
                <b>Assign role/s</b>
              </td>
            
            
            </tr>
	        
	          <tr class="white"> 
	            <td class="grey" nowrap>
	              Account <font color="Red">*</font>
	            </td>
              
	            <td>
	              <select name="role.accountId" onchange="onAccountChange();"><option value="">Select</option><option value="HCITTEST">HCIT Test Account (HCITTEST)</option><option value="HCIT">HealthCare IT (HCIT)</option><option value="PreetiTest">Preeti Test Account (PreetiTest)</option></select>
	            </td>
              
              
	          </tr>
	        
	        
            <tr class="white"> 
              <td class="grey" nowrap>Role Name <font color="Red">*</font>
              </td>
             <td> 
                <select name="userData.Roles">
                <option value="A" selected="selected">-Select a Role-</option>
                 <option value="B">Account Administrator </option>
                <option value="C">Full Operational User</option>
                <option value="I">Modified Operational User</option>
                <option value="O">Consenting Only or Consenting and Data Entry User</option>
                <option value="D">Data Entry Only User</option>
                <option value="L">Other</option>
                </select>
              </td>

            </tr>
            <tr class="white">
              <td class="grey" nowrap>Privileges
              </td>
              <td> 
                <table class="background" cellpadding="0" cellspacing="0" border="0">
                  <tr>
                    <td> 
                      <table class="foreground" cellpadding="3" cellspacing="1" border="0">
                        <tr class="green"> 
                          <td colspan="3" nowrap> 
                            <div align="center">
                              <b>Filter privileges:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b>
			                        <input type="radio" name="privilegeFilter" value="ALL" checked="checked" onclick="refreshPrivilegeChoices();">All
			                        <input type="radio" name="privilegeFilter" value="ES" onclick="refreshPrivilegeChoices();">BIGR Library &#174;
			                        <input type="radio" name="privilegeFilter" value="ILTDS" onclick="refreshPrivilegeChoices();">ILTDS
			                        <input type="radio" name="privilegeFilter" value="ORM" onclick="refreshPrivilegeChoices();">O&M
			                        <input type="radio" name="privilegeFilter" value="PDC" onclick="refreshPrivilegeChoices();">DDC
			                        <input type="radio" name="privilegeFilter" value="LIMS" onclick="refreshPrivilegeChoices();">LIMS
			                        <input type="radio" name="privilegeFilter" value="IMP" onclick="refreshPrivilegeChoices();">Sample Registration
			                      </div>
                          </td>
                        </tr>
                        <tr class="yellow"> 
                          <td nowrap> 
                            <div align="center"><b>Available Privileges</b></div>
                          </td>
                          <td>&nbsp;</td>
                          <td nowrap> 
                            <div align="center"><b>Assigned Privileges</b></div>
                          </td>
                        </tr>
                        <tr class="white"> 
                          <td> 
                            <div align="center"> 
                            
                              <select name="availablePrivileges" size="10" multiple>
                            
                              </select>
                            </div>
                          </td>
                          <td> 
                            <div align="center">
                              <input type="button" name="btnAdd" value="Add &gt;&gt;" onclick="addPrivileges();">
                              <br><br>
                              <input type="button" name="btnRemove" value="&lt;&lt; Remove" onclick="removePrivileges();">
                            </div>
                          </td>
                          <td> 
                            <div align="center"> 
                              <select name="selectedPrivileges" multiple="multiple" size="10"></select>
                            </div>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
            <tr class="white">
              <td class="grey" nowrap>Users
              </td>
              <td> 
                <table class="background" cellpadding="0" cellspacing="0" border="0">
                  <tr>
                    <td> 
                      <table class="foreground" cellpadding="3" cellspacing="1" border="0">
                        <tr class="yellow"> 
                          <td nowrap> 
                            <div align="center"><b>Available Users</b></div>
                          </td>
                          <td>&nbsp;</td>
                          <td nowrap> 
                            <div align="center"><b>Assigned Users</b></div>
                          </td>
                        </tr>
                        <tr class="white"> 
                          <td> 
                            <div align="center"> 
                            
                              <select name="availableUsers" size="10" multiple>
                            
                              </select>
                            </div>
                          </td>
                          <td> 
                            <div align="center">
                              <input type="button" name="btnAdd" value="Add &gt;&gt;" onclick="addUsers();">
                              <br><br>
                              <input type="button" name="btnRemove" value="&lt;&lt; Remove" onclick="removeUsers();">
                            </div>
                          </td>
                          <td> 
                            <div align="center"> 
                              <select name="selectedUsers" multiple="multiple" size="10"></select>
                            </div>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
            <tr class="white"> 
              <td colspan="2" align="center"> 
                <input type="submit" name="btnSubmit" value="Submit">
                <input type="button" name="btnCancel" value="Cancel" onclick="onClickCancel();">
              </td>
            </tr>
            <tr class="white"> 
              <td colspan="2"> 
                <font color="Red">*</font> indicates a required field  
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table> 
    <br/>
    <div> 
   <!--  <table border="0" cellspacing="0" cellpadding="0" class="background">
      <tr> 
        <td> 
          <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
	        <tr class="white">
      		  <td class="grey" colspan="5">
          		To edit a role, click on its Name.  To delete a role,
          		click on its Delete button.  The role will be deleted as soon
          		as you click on the button.  Note that only those roles not associated
          		with any user can be deleted.
	          </td>
    	    </tr>
            <tr class="white">
              <td nowrap><b>Name</b></td>
			  
              <td nowrap><b>Account</b></td>
              
              <td nowrap align="center"><b>ICP</b></td>
              <td nowrap align="center"><b>Delete</b></td>
            </tr>
            
            <tr class="white">
              <td>
                <a id="linkEditRole"
                   href="/BIGR/orm/role/maintainRoleStart.do?operation=Update&role.id=RL00000001"
                   onclick="return(isLinkEnabled());">
                  Role 1
                </a>
              </td>
			  
              <td>
              	HCIT Test Account (HCITTEST)
              </td>
              
              <td align="center">
				<span class="fakeLink" onclick="toIcp('RL00000001', true);">ICP</span>
              </td>
              <td align="center">
				<button type="button" name="btnDelete" value="Delete"
                    style="height: 16px; width: 16px;"
                    onclick="onClickDelete('RL00000001');">
                  <img src="/BIGR/images/delete.gif" border="0" alt="Delete">
                </button>
              </td>
            </tr>
            
            <tr class="white">
              <td>
                <a id="linkEditRole"
                   href="/BIGR/orm/role/maintainRoleStart.do?operation=Update&role.id=RL00000015"
                   onclick="return(isLinkEnabled());">
                  Account Manager
                </a>
              </td>
			  
              <td>
              	Preeti Test Account (PreetiTest)
              </td>
              
              <td align="center">
				<span class="fakeLink" onclick="toIcp('RL00000015', true);">ICP</span>
              </td>
              <td align="center">
				<button type="button" name="btnDelete" value="Delete"
                    style="height: 16px; width: 16px;"
                    onclick="onClickDelete('RL00000015');">
                  <img src="/BIGR/images/delete.gif" border="0" alt="Delete">
                </button>
              </td>
            </tr>
            
          </table>
        </td>
      </tr>
    </table>-->
    </div> 
  </div> 
</form>
</body>
</html>

