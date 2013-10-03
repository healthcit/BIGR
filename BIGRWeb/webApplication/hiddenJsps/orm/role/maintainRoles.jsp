<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.api.Escaper" %>
<%@ page import="com.ardais.bigr.javabeans.PrivilegeDto" %>
<%@ page import="com.ardais.bigr.javabeans.RoleDto" %>
<%@ page import="com.ardais.bigr.javabeans.UserDto" %>
<%@ page import="com.ardais.bigr.orm.helpers.FormLogic" %>
<%@ page import="com.ardais.bigr.security.SecurityInfo" %> 
<%@ page import="com.ardais.bigr.util.Constants" %>
<%@ page import="javax.servlet.http.*" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<bean:define id="myForm" name="maintainRoleForm" type="com.ardais.bigr.orm.web.form.MaintainRoleForm"/>
<html>
<head>
<title>Maintain Roles</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<html:rewrite page="/css/bigr.css"/>" type="text/css">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript" src="<html:rewrite page="/js/linkedlist.js"/>"></script>
<script language="JavaScript" src="<html:rewrite page="/js/map.js"/>"></script>
<script language="JavaScript" src="<html:rewrite page="/js/privilege.js"/>"></script>
<script language="JavaScript" src="<html:rewrite page="/js/user.js"/>"></script>
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
  window.location = '<html:rewrite page="/orm/role/maintainRoleStart.do"/>';
}

function onClickDelete(roleId) {
  setActionButtonEnabling(false);
  var f = document.forms[0];
  var cancelAction = false;
  var savedOperation = f['operation'].value;
  var savedRoleId = f['role.id'].value;
  f['operation'].value = '<%= Constants.OPERATION_DELETE %>';
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

<%
  //for each entry in the privilege hashmap, creating a corresponding javascript
  //version
  Map privMap = myForm.getAllPrivileges();
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
<%
//for each entry in the user hashmap, creating a corresponding javascript
//version
Map userMap = myForm.getAllUsers();
keyIterator = userMap.keySet().iterator();
while (keyIterator.hasNext()) {
  String key = (String)keyIterator.next();
  List userList = (List)userMap.get(key);
%>
var userList = new UserList();
<%
  Iterator userIterator = userList.listIterator();
  while (userIterator.hasNext()) {
    UserDto user = (UserDto)userIterator.next();
%>
var user = new User();
user.setUserId('<%=user.getUserId()%>');
user.setFirstName('<%=Escaper.jsEscapeInScriptTag(user.getFirstName())%>');
user.setLastName('<%=Escaper.jsEscapeInScriptTag(user.getLastName())%>');
userList.addUser(user);
<%
  }
%>
userMap.put('<%=key%>', userList);
<%
}
%>
-->
</script>
</head>
<body class="bigr" onload="initPage();" onbeforeunload="confirmNavigate();">
<html:form method="POST" action="/orm/role/maintainRoleSave" onsubmit="return(onFormSubmit());">
  <html:hidden property="operation"/>
  <html:hidden property="role.id"/>
  <div align="center">
    <table border="0" cellspacing="0" cellpadding="0" class="background">
      <%-- begin create/edit input table --%>
      <tr> 
        <td> 
          <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
	        <% if (request.getAttribute("org.apache.struts.action.ERROR") != null) { %>
	          <tr class="yellow">
	            <td colspan="2">
                <font color="#FF0000">
	                <b>
	                  <html:errors/>
	                </b>
	              </font>
	            </td>
	          </tr>
	        <% } %>
	        <logic:present name="com.ardais.BTX_ACTION_MESSAGES">
            <tr class="yellow"> 
              <td colspan="2" align="center"> 
                <font color="#FF0000">
                  <b>
                    <bigr:btxActionMessages/>
                  </b>
                </font>
 		          </td>
		        </tr>
		      </logic:present>
            <tr class="white">
            <logic:equal name="maintainRoleForm" property="operation" value="<%= Constants.OPERATION_CREATE %>">
              <td colspan="2" align="center">
                <b>Create a new role</b>
              </td>
            </logic:equal>
            <logic:equal name="maintainRoleForm" property="operation" value="<%= Constants.OPERATION_UPDATE %>">
              <td colspan="2" align="center">
                <b>Edit role: <bean:write name="maintainRoleForm" property="role.name" /></b>
              </td>
            </logic:equal>
            </tr>
	        <bigr:isInRole role1="<%=SecurityInfo.ROLE_SYSTEM_OWNER%>">
	          <tr class="white"> 
	            <td class="grey" nowrap>
	              Account <font color="Red">*</font>
	            </td>
              <logic:equal name="maintainRoleForm" property="operation" value="<%= Constants.OPERATION_CREATE %>">
	            <td>
	              <bigr:selectList
	                name="maintainRoleForm" property="role.accountId"
		              legalValueSetProperty="accountChoices"
		              firstValue="" firstDisplayValue="Select"
		              onchange="onAccountChange();"/>
	            </td>
              </logic:equal>
              <logic:equal name="maintainRoleForm" property="operation" value="<%= Constants.OPERATION_UPDATE %>">
              <td>
                <bean:write name="maintainRoleForm" property="accountName" />
                <html:hidden property="role.accountId"/>
              </td>
              </logic:equal>
	          </tr>
	        </bigr:isInRole>
	        <bigr:notIsInRole role1="<%=SecurityInfo.ROLE_SYSTEM_OWNER%>">
	        <html:hidden property="role.accountId"/>
	        </bigr:notIsInRole>
            <tr class="white"> 
              <td class="grey" nowrap>Role Name <font color="Red">*</font>
              </td>
              <td> 
                <html:text property="role.name" size="55" maxlength="50"/>
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
                              <select name="availablePrivileges" size="10" multiple>
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
                              <html:select property="selectedPrivileges" size="10" multiple="true">
                              <logic:iterate name="maintainRoleForm" property="role.privileges"
                                id="privilege" type="com.ardais.bigr.javabeans.PrivilegeDto">
                                <option value="<%=privilege.getId()%>"><%=Escaper.htmlEscapeAndPreserveWhitespace(privilege.getDescription())%> 
		                          </logic:iterate>
                              </html:select>
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
                            <%
                              //note - select is used instead of html:select, as there is no
                              //"availableUsers" data element on the form.  The contents
                              //of this control are for input to the process of assigning/revoking
                              //users, and are not relevant to the output of the process.
                              //Therefore, the contents of this control are not required to
                              //be passed on to the server.
                            %>
                              <select name="availableUsers" size="10" multiple>
                            <%
                              //options will be filled in by refreshUserChoices() call in initPage();
                            %>
                              </select>
                            </div>
                          </td>
                          <td> 
                            <div align="center">
                              <html:button property="btnAdd" value="Add &gt;&gt;" onclick="addUsers();"/>
                              <br><br>
                              <html:button property="btnRemove" value="&lt;&lt; Remove" onclick="removeUsers();"/>
                            </div>
                          </td>
                          <td> 
                            <div align="center"> 
                              <html:select property="selectedUsers" size="10" multiple="true">
                              <logic:iterate name="maintainRoleForm" property="role.users"
                                id="user" type="com.ardais.bigr.javabeans.UserDto">
                                <option value="<%=user.getUserId()%>"><%=Escaper.htmlEscapeAndPreserveWhitespace(user.getLastName() + ", " + user.getFirstName() + " (" + user.getUserId() + ")")%> 
		                          </logic:iterate>
                              </html:select>
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
    </table> <%-- end create/edit input table --%>
    <br/>
    <div> <%-- begin role list --%>
    <table border="0" cellspacing="0" cellpadding="0" class="background">
      <%-- begin role list table --%>
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
			  <bigr:isInRole role1="<%=SecurityInfo.ROLE_SYSTEM_OWNER%>">
              <td nowrap><b>Account</b></td>
              </bigr:isInRole>
              <td nowrap align="center"><b>ICP</b></td>
              <td nowrap align="center"><b>Delete</b></td>
            </tr>
            <logic:iterate id="role" name="maintainRoleForm" property="roles" type="com.ardais.bigr.javabeans.RoleDto">
            <tr class="white">
              <td>
                <a id="linkEditRole"
                   href="<html:rewrite page="/orm/role/maintainRoleStart.do?operation=Update"/>&role.id=<bean:write name="role" property="id"/>"
                   onclick="return(isLinkEnabled());">
                  <bean:write name="role" property="name"/>
                </a>
              </td>
			  <bigr:isInRole role1="<%=SecurityInfo.ROLE_SYSTEM_OWNER%>">
              <td>
              	<bean:write name="role" property="accountName"/>
              </td>
              </bigr:isInRole>
              <td align="center">
				<bigr:icpLink linkText="ICP" popup="true">
				  <%=role.getId()%>
				</bigr:icpLink>
              </td>
              <td align="center">
				<button type="button" name="btnDelete" value="Delete"
                    style="height: 16px; width: 16px;"
                    onclick="onClickDelete('<bean:write name="role" property="id"/>');">
                  <html:img page="/images/delete.gif" alt="Delete" border="0"/>
                </button>
              </td>
            </tr>
            </logic:iterate>
          </table>
        </td>
      </tr>
    </table> <%-- end role list table --%>
    </div> <%-- end role list --%>
  </div> <%-- end centered div --%>
</html:form>
</body>
</html>
