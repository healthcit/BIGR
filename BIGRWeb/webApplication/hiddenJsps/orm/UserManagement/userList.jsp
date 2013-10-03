<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.security.SecurityInfo"%>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<html>
<head>
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>User Management</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="javascript">
<!--

function initPage() {
  if (parent.topFrame) {
    parent.topFrame.document.all.banner.innerHTML = 'User Management';
  }
}
  
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.forms[0];
  if (isControlCollection('btnModify')) {
    disableControlCollection('btnModify', isDisabled);
  }
  else {
    f.btnModify.disabled = isDisabled;
  }
  f.btnAdd.disabled = isDisabled;
}

function onFormSubmit() {
  setActionButtonEnabling(false);
  return true;
}

function onAddNewClick() {
  //clear user and account id, since a new user is to be added
  document.forms[0]["userData.userId"].value = "";
  document.forms[0]["userData.accountId"].value = "";
  document.forms[0].action='<html:rewrite page="/orm/users/createUserStart.do"/>';
  setActionButtonEnabling(false);
  document.forms[0].submit();
}

function onModifyClick(userId, acctId) {
  document.forms[0]["userData.userId"].value = userId;
  document.forms[0]["userData.accountId"].value = acctId;
  setActionButtonEnabling(false);
  document.forms[0].submit();
}

-->
</script>
</head>
<body class="bigr" onload="initPage();">
<html:form method="post" action="/orm/users/modifyUserStart.do" onsubmit="return(onFormSubmit());">
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
  <div align="center">
    <html:hidden property="userData.userId"/>
    <html:hidden property="userData.accountId"/>
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table cellpadding="3" cellspacing="1" border="0" class="foreground">
            <tr class="yellow"> 
              <td align="center"> 
                <b>Account ID</b>
              </td>
              <td align="center"> 
                <b>User ID</b>
              </td>
              <td align="center"> 
                <b>User Last Name</b>
              </td>
              <td align="center"> 
                <b>User First Name</b>
              </td>
              <td align="center"> 
                <b>Status</b>
              </td>
              <bigr:hasPrivilege priv="<%=SecurityInfo.PRIV_ORM_USER_MANAGEMENT%>">
                <td align="center"> 
                  &nbsp;
                </td>
              </bigr:hasPrivilege>
            </tr>
            <logic:iterate name="maintainUserForm" property="matchingUsers" indexId="userCount" id="user" type="com.ardais.bigr.javabeans.UserDto">
              <tr class="<%if((userCount.intValue()%2) == 0) out.print("white"); else out.print("grey");%>"> 
                <td><bean:write name="user" property="accountId"/></td>
                <td><bean:write name="user" property="userId"/></td>
                <td><bean:write name="user" property="lastName"/></td>
                <td><bean:write name="user" property="firstName"/></td>
                <td>
                  <logic:equal name="user" property="activeYN" value="Y">
                    Active
                  </logic:equal>
                  <logic:notEqual name="user" property="activeYN" value="Y">
                    Inactive
                  </logic:notEqual>
                </td>
                <%
                  //only show the Edit button if the user has User Management privs
                %>
                <bigr:hasPrivilege priv="<%=SecurityInfo.PRIV_ORM_USER_MANAGEMENT%>">
                  <td>
                  <%
                    String onClick = "onModifyClick('" + user.getUserId() + "','" + user.getAccountId() + "');";
                  %>
                    <html:button property="btnModify" value="Edit" onclick="<%=onClick%>"/>
                  </td>
                </bigr:hasPrivilege>
              </tr>
            </logic:iterate>
            <bigr:hasPrivilege priv="<%=SecurityInfo.PRIV_ORM_USER_MANAGEMENT%>">
              <tr class="white"> 
                <td colspan="6"> 
                  <div align="center"> 
                    <html:button property="btnAdd" value="Add New" onclick="onAddNewClick();"/>
                  </div>
                </td>
              </tr>
            </bigr:hasPrivilege>
          </table>
        </td>
      </tr>
    </table>
  </div>
</html:form>
</body>
</html>
