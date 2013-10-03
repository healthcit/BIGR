<%@ page import = "java.util.Iterator"%>
<%@ page import = "java.util.List"%>
<%@ page import = "java.util.Map"%>
<%@ page import = "com.ardais.bigr.api.ApiFunctions" %>
<%@ page import = "com.ardais.bigr.api.Escaper" %>
<%@ page import = "com.ardais.bigr.javabeans.PrivilegeDto" %>
<%@ page import = "com.ardais.bigr.orm.helpers.FormLogic"%>
<%@ page import = "com.ardais.bigr.util.Constants"%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>

<bean:define id="manageForm" name="managePrivilegesForm" type="com.ardais.bigr.orm.web.form.ManagePrivilegesForm"/>
<html>
<head>
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Manage Privileges for <%= Escaper.jsEscapeInScriptTag(manageForm.getObjectInformation()) %></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript" src="<html:rewrite page="/js/linkedlist.js"/>"></script>
<script language="JavaScript" src="<html:rewrite page="/js/map.js"/>"></script>
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
  var myBanner = 'Manage Privileges for <%= Escaper.jsEscapeInScriptTag(manageForm.getObjectInformation()) %>';
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
  document.forms[0].action="<html:rewrite page='/orm/managePrivilegesStart.do'/>";
  document.forms[0].submit();
}

function onCancelClick() {
  disableActionButtons();
  <%
    String url = null;
    if (Constants.OBJECT_TYPE_ACCOUNT.equalsIgnoreCase(manageForm.getObjectType())) {
      url = "/orm/accounts/modifyAccountStart.do?accountData.id=" + manageForm.getAccountData().getId();
    }
    else {
      url = "/orm/users/modifyUserStart.do";
    }
  %>
  document.forms[0].action="<html:rewrite page='<%=url%>'/>";
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

<%
  //for each entry in the privilege hashmap, creating a corresponding javascript
  //version
  Map privMap = manageForm.getAllPrivileges();
  Iterator keyIterator = privMap.keySet().iterator();
  while (keyIterator.hasNext()) {
    String key = (String)keyIterator.next();
    List privList = (List)privMap.get(key);
%>
var privilegeList = new PrivilegeList();
<%
    Iterator privilegeIterator = privList.listIterator();
    while (privilegeIterator.hasNext()) {
      PrivilegeDto priv = (PrivilegeDto)privilegeIterator.next();
%>
var privilege = new Privilege();
privilege.setId('<%=priv.getId()%>');
privilege.setDescription('<%=priv.getDescription()%>');
privilegeList.addPrivilege(privilege);
<%
    }
%>
privilegeMap.put('<%=key%>', privilegeList);
<%
  }
%>
-->
</script>
</head>
<body class="bigr" onload="initPage();">
<html:form method="post" action="/orm/managePrivileges.do">
<%
  // DIV for errors
%>
  <div id="errorDiv" align="center">
    <table width="100%" border="1" cellspacing="1" cellpadding="1" class="foreground-small">
      <logic:present name="org.apache.struts.action.ERROR">
      <tr class="yellow"> 
	    <td> 
	      <font color="#FF0000"><b><html:errors/></b></font>
	    </td>
	  </tr>
	  </logic:present>
      <logic:present name="com.ardais.BTX_ACTION_MESSAGES">
	  <tr class="white"> 
	    <td> 
	      <div align="left">
	        <font color="#000000"><b><bigr:btxActionMessages/></b></font>
	      </div>
	    </td>
	  </tr>
	  </logic:present>
	</table>
  </div>
  <p>
<html:hidden property="objectType"/>
<html:hidden property="userData.userId"/>
<html:hidden property="userData.accountId"/>
<html:hidden property="userData.firstName"/>
<html:hidden property="userData.lastName"/>
<html:hidden property="accountData.id"/>
<html:hidden property="accountData.name"/>
  <div align="center">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr>
        <td> 
          <table class="foreground" cellpadding="3" cellspacing="1" border="0">
            <tr class="green"> 
              <td colspan="3" nowrap> 
                <div align="center">
                  <b>Filter privileges:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b>
			      <html:radio property="privilegeFilter" value="<%=FormLogic.ALL%>" onclick="refreshPrivilegeChoices();"/><%=FormLogic.ALL_APP%>
			      <html:radio property="privilegeFilter" value="<%=FormLogic.ESTORE%>" onclick="refreshPrivilegeChoices();"/><%=FormLogic.BIGR_LIB_APP%> &#174;
			      <html:radio property="privilegeFilter" value="<%=FormLogic.ILTDS%>" onclick="refreshPrivilegeChoices();"/><%=FormLogic.ILTDS_APP%>
			      <html:radio property="privilegeFilter" value="<%=FormLogic.ORM%>" onclick="refreshPrivilegeChoices();"/><%=FormLogic.ONM_APP%>
			      <html:radio property="privilegeFilter" value="<%=FormLogic.PDC%>" onclick="refreshPrivilegeChoices();"/><%=FormLogic.DDC_APP%>
			      <html:radio property="privilegeFilter" value="<%=FormLogic.LIMS%>" onclick="refreshPrivilegeChoices();"/><%=FormLogic.LIMS_APP%>
			      <html:radio property="privilegeFilter" value="<%=FormLogic.IMPORT%>" onclick="refreshPrivilegeChoices();"/><%=FormLogic.IMPORT_APP%>
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
                  <%
                    //note - select is used instead of html:select, as there is no
                    //"availablePrivileges" data element on the form.  The contents
                    //of this control are for input to the process of assigning/revoking
                    //privileges, and are not relevant to the output of the process.
                    //Therefore, the contents of this control are not required to
                    //be passed on to the server.
                  %>
                  <select name="availablePrivileges" size="20" multiple>
                  <%
                    //options will be filled in by refreshPrivilegeChoices() call in initPage();
                  %>
                  </select>
                </div>
              </td>
              <td> 
                <div align="center">
                  <html:button property="btnAdd" value="Add &gt;&gt;" onclick="addPrivileges();"/>
                  <br><br>
                  <html:button property="btnRemove" value="&lt;&lt; Remove" onclick="removePrivileges();"/>
                </div>
              </td>
              <td> 
                <div align="center"> 
                  <html:select property="selectedPrivileges" size="20" multiple="true">
                    <logic:iterate name="managePrivilegesForm" property="assignedPrivileges"
                      id="privilege" type="com.ardais.bigr.javabeans.PrivilegeDto">
                      <option value="<%=privilege.getId()%>"><%=Escaper.htmlEscapeAndPreserveWhitespace(privilege.getDescription())%> 
		            </logic:iterate>
                  </html:select>
                </div>
              </td>
            </tr>
<%
//if there are privileges to which the user has access via membership in one or more account
//based roles, display them
if (!ApiFunctions.isEmpty(manageForm.getRoleBasedPrivileges())) {
  StringBuffer buffer = new StringBuffer(128);
  boolean addComma = false;
  Iterator privilegeIterator = manageForm.getRoleBasedPrivileges().iterator();
  while (privilegeIterator.hasNext()) {
    PrivilegeDto privilege = (PrivilegeDto)privilegeIterator.next();
    if (addComma) {
      buffer.append(", ");
    }
    addComma = true;
    buffer.append(privilege.getDescription());
  }
%>
            <tr class="white"> 
              <td colspan="3"> 
                <div align="left">
                  <b>Note: This user has access to the following privilege(s) via membership in one 
                     or more roles:&nbsp;&nbsp; <%=buffer.toString() %></b>
                  <br>
                </div>
              </td>
            </tr>
<%
}
%>
            <tr class="white"> 
              <td colspan="3"> 
                <div align="center"> 
                    <html:button property="btnSubmit" value="Submit" onclick="onSubmitClick();"/>
                    <html:button property="btnReset" value="Reset" onclick="onResetClick();"/>
                    <%
                      String buttonText = "Return to " + manageForm.getObjectType() + " Profile";
                    %>
                    <html:button property="btnCancel" value="<%=buttonText%>" onclick="onCancelClick();"/>
                </div>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </div>
</html:form>
</body>
</html>