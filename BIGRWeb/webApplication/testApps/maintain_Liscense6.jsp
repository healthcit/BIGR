
 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/tld/struts-html" prefix="html"%>
<%@ taglib uri="/tld/struts-bean" prefix="bean"%>

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
 
function Privilege() {
  this.setId = setId;
  this.getId = getId;
  this.setDescription = setDescription;
  this.getDescription = getDescription;
  
  var _self = this;
  var _id;
  var _description;
  
  function setId(id) {
    _id = id;
  }
  
  function getId() {
    return _id;
  }
  
  function setDescription(description) {
    _description = description;
  }
  
  function getDescription() {
    return _description;
  }
}
 
function PrivilegeList() {
  this.addPrivilege = addPrivilege;
  this.getPrivilege = getPrivilege; 
  this.getFirstPrivilege = getFirstPrivilege;
  this.getNextPrivilege = getNextPrivilege;
  
  var _privileges = new LinkedList(true);
  var _self = this;
  
 
  function addPrivilege(privilege) {
	_privileges.insertBefore(null, privilege, privilege.getId());
  }
 
  function getPrivilege(id) {
	var cell = _privileges.getCellByKey(id);	
	return (cell == null) ? null : cell.item;
  }
  
  function getFirstPrivilege() {
	this.currentCell = _privileges.getFirstCell();
	if (this.currentCell == null) {
		return null;
	}
	else {
		return this.currentCell.item;
	}
  }
  
  function getNextPrivilege() {
	if (this.currentCell == null) {
		return null;
	}
	else {
		this.currentCell = _privileges.getNextCell(this.currentCell);
		if (this.currentCell == null) {
			return null;
		}
		else {
			return this.currentCell.item;
		}
	}
  }
}
 
function initPage() {
  var myBanner = 'Manage Privileges for Preeti Gupta (PreetiTest)';
  if (parent.topFrame && parent.topFrame.document.all.banner) {
    parent.topFrame.document.all.banner.innerText = myBanner;
  }
  refreshPrivilegeChoices();
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
 
//map to keep track of all privileges, keyed by functional area
var privilegeMap = new Map();
 
 
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
 
-->
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
                <select name="userData.accountId"><option value="">Select</option><option value="HCITTEST">HCIT Test Account (HCITTEST)</option><option value="HCIT">HealthCare IT (HCIT)</option><option value="PreetiTest">Preeti Test Account (PreetiTest)</option></select>
              </td>
            </tr>
            <tr class="white"> 
            <td class="grey" nowrap>
            <div align="right"><b><font color="Red">*</font>Role Name:</b></div>
              </td>
              <td> 
                <input type="text" name="role.name" maxlength="50" size="55" value="">
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
          <table class="foreground" cellpadding="3" cellspacing="1" border="0">
            <tr class="lightGrey"> 
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
            <tr class="grey"> 
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
                  
                  <select name="availablePrivileges" size="20" multiple ondblclick="addPrivileges();">
                  <option value="10_ILTDS_150_TXNOTE">Add History Note 
		            
                      <option value="40_ORM_037_ADDTOIG">Add Samples To Inventory Group 
		            
                      <option value="10_ILTDS_045_NBOXSCAN">Box Scan 
		            
                      <option value="50_IMP_45_CASEPRF">Case Profile Entry (SR) 
		            
                      <option value="15_ES_050_PROFILE">Change Your Profile 
		            
                      <option value="10_ILTDS_050_CKOUTINV">Check Box out of Inventory 
		            
                      <option value="10_ILTDS_090_ACOINV">Check Samples out of Inventory 
		            
                      <option value="50_IMP_60_CLINDATAEXT">Clinical Data Extraction (SR) 
		            
                      <option value="50_IMP_20_CREATECASE">Create Case (SR) 
		            
                      <option value="50_IMP_10_CREATEDONOR">Create Donor (SR) 
		            
                      <option value="10_ILTDS_104_CRTMAN">Create Manifest 
		            
                      <option value="50_IMP_35_CREATESAMP">Create Sample (SR) 
		            
                      <option value="30_ORM_050_DETVIEWER">DET Viewer 
		            
                      <option value="50_IMP_70_DERIV">Derivative Operations 
		            
                      <option value="50_IMP_65_FINDBYID">Find by Id (SR) 
		            
                      <option value="40_ORM_062_FORMCREATE">Form Designer 
		            
                      <option value="10_ILTDS_145_ICP">ICP 
		            
                      <option value="40_ORM_040_MAINBXLYS">Maintain Box Layouts 
		            
                      <option value="40_ORM_035_MAINTLR">Maintain Inventory Groups 
		            
                      <option value="40_ORM_030_PLCYMGR">Maintain Policies 
		            
                      <option value="40_ORM_015_MAINTROLES">Maintain Roles 
		            
                      <option value="40_ORM_060_MAINTURLS">Maintain URLs 
		            
                      <option value="12_ILTDS_10_MNGREQS">Manage Requests 
		            
                      <option value="50_IMP_25_MODIFYCASE">Modify Case (SR) 
		            
                      <option value="50_IMP_15_MODIFYDONOR">Modify Donor (SR) 
		            
                      <option value="50_IMP_40_MODIFYSAMP">Modify Sample (SR) 
		            
                      <option value="10_ILTDS_085_ABOXSCAN">Owner Box Scan 
		            
                      <option value="50_IMP_55_PATHREPABS">Path Report Abstraction (SR) 
		            
                      <option value="50_IMP_50_PATHREPFULL">Path Report Full Text (SR) 
		            
                      <option value="50_IMP_90_PRNTLBLS">Print Labels 
		            
                      <option value="10_ILTDS_114_PRNMNFT">Print Manifests 
		            
                      <option value="15_ES_HOLDLIST_ADDRMV">Privilege: Add/Remove from Hold List 
		            
                      <option value="12_ILTDS_21_PATH_REQ">Privilege: Create Pathology Requests 
		            
                      <option value="12_ILTDS_22_RAND_REQ">Privilege: Create RandD Requests 
		            
                      <option value="15_ES_HOLDSOLD_ACCNT">Privilege: Hold/Sold Account 
		            
                      <option value="15_ES_HOLDSOLD_USER">Privilege: Hold/Sold User 
		            
                      <option value="10_ILTDS_147_ICP_SUSR">Privilege: ICP Super User 
		            
                      <option value="15_ES_SQL_TAB">Privilege: Show SQL Tab 
		            
                      <option value="40_ORM_VIEW_ALL_LRS">Privilege: View All Logical Repositories 
		            
                      <option value="15_ES_CONSENT_VIEW">Privilege: View Consent 
		            
                      <option value="50_IMP_30_CASEPULL">Pull Case (SR) 
		            
                      <option value="10_ILTDS_120_RCPTVERF">Receipt Verification 
		            
                      <option value="40_ORM_038_MOVEFROMIG">Remove Samples From Inventory Group 
		            
                      <option value="12_ILTDS_10_REQSAMP">Request Samples 
		            
                      <option value="15_ES_006_SS">Sample Selection 
		            
                      <option value="10_ILTDS_107_SCANPACK">Scan and Package 
		            
                      <option value="10_ILTDS_122_SCANSTOR">Scan and Store 
		            
                      <option value="10_ILTDS_055_UPDBXLOC">Update Box Location 
		            
                      <option value="10_ILTDS_100_UPDBXLOC">Update Box Location at Owner 
		            
                      <option value="40_ORM_010_USERMNG">User Management 
		            
                      <option value="30_ORM_070_VIEWGBOSS">View GBOSS Data 
		            
                      <option value="12_ILTDS_10_VIEWREQS">View Requests 
		            
                      <option value="15_ES_010_VIEWITEM">View Selected Items 
		            
                      <option value="40_ORM_065_VIEWSYSCFG">View System Configuration
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
                  <select name="selectedPrivileges" multiple="multiple" size="20" ondblclick="removePrivileges();">
                  </select>
                </div>
              </td>
            </tr>
            <tr class="grey"> 
              <td colspan="3"> 
                <div align="center"> 
                    <input type="button" name="btnSubmit" value="Submit" onclick="onSubmitClick();">
                    <input type="button" name="btnReset" value="Reset" onclick="onResetClick();">
                </div>
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
                  <img src="/BIGR/images/MenuOpened.gif" border="0" alt="Collapse">
                </button>
              </td>
              <td align="center"> 
                <b>
                 <a id="linkEditRole"
                   href=""
                   onclick="">
                Full Operational User </a></b>
              </td>
              <td align="center">
							<button type="button" name="btnDelete" value="Delete"
                    style="height: 16px; width: 16px;"
                    onclick="onClickDelete('RL00000015');">
                  <img src="/BIGR/images/delete.gif" border="0" alt="Delete">
                </button>
              </td>
            </tr>
              <tr class="white"> 
                <td colspan="3">
                	Create donor (SR)
                </td>
              </tr>
              <tr class="grey"> 
                <td colspan="3">
                  Modify donor (SR)
                </td>
              </tr>
               <tr class="white"> 
                <td colspan="3">
                	Create Case (SR)
                </td>
              </tr>
              <tr class="grey"> 
                <td colspan="3">
                  Modify Case (SR)
                </td>
              </tr>
                <tr class="white"> 
                <td colspan="3">
                	Create Sample (SR)
                </td>
              </tr>
              <tr class="grey"> 
                <td colspan="3">
                  Modify Sample (SR)
                </td>
              </tr>
               <tr class="white"> 
                <td colspan="3">
                	Derivative operations 
                </td>
              </tr>
              <tr class="grey"> 
                <td colspan="3">
                 Find by ID 
                </td>
              </tr>
              
              <tr class="lightGrey"> 
               <td align="center"> 
               	<button type="button" name="btnDelete" value="Expand"
                    style="height: 16px; width: 16px;"
                    onclick="onClickDelete('RL00000015');">
                  <img src="/BIGR/images/MenuClosed.gif" border="0" alt="Expand">
                </button>
              </td>
              <td align="center"> 
                <b>
                 <a id="linkEditRole"
                   href=""
                   onclick="">
                Modified Operational User </a></b>
              </td>
              <td align="center">
							<button type="button" name="btnDelete" value="Delete"
                    style="height: 16px; width: 16px;"
                    onclick="onClickDelete('RL00000015');">
                  <img src="/BIGR/images/delete.gif" border="0" alt="Delete">
                </button>
              </td>
            </tr>
            
            <tr class="lightGrey"> 
             <td align="center"> 
               	<button type="button" name="btnDelete" value="Expand"
                    style="height: 16px; width: 16px;"
                    onclick="onClickDelete('RL00000015');">
                  <img src="/BIGR/images/MenuOpened.gif" border="0" alt="Collapse">
                </button>
              </td>
              <td align="center"> 
                <b>
                 <a id="linkEditRole"
                   href=""
                   onclick="">
                Account Administrator </a></b>
              </td>
              <td align="center">
							<button type="button" name="btnDelete" value="Delete"
                    style="height: 16px; width: 16px;"
                    onclick="onClickDelete('RL00000015');">
                  <img src="/BIGR/images/delete.gif" border="0" alt="Delete">
                </button>
              </td>
            </tr>
              <tr class="white"> 
                <td colspan="3">
                	Account management
                </td>
              </tr>
              <tr class="grey"> 
                <td colspan="3">
                  Manage locations and storage
                </td>
              </tr>
               <tr class="white"> 
                <td colspan="3">
                 Manage IRB protocol/consent versions
                </td>
              </tr>
              <tr class="grey"> 
                <td colspan="3">
                  Manage shipping partners
                </td>
              </tr>
                <tr class="white"> 
                <td colspan="3">
                	User management
                </td>
              </tr>
              <tr class="grey"> 
                <td colspan="3">
                  Maintain policies
                </td>
              </tr>
               <tr class="white"> 
                <td colspan="3">
                	Maintain roles 
                </td>
              </tr>
              <tr class="grey"> 
                <td colspan="3">
                 Maintain inventory groups
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
