<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.util.RoleUtils" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<bean:define id="myForm" name="maintainUserForm" type="com.ardais.bigr.orm.web.form.MaintainUserForm"/>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%
	//if there are roles defined for the account, add a step related to roles
	boolean rolesDefinedForAccount = !ApiFunctions.isEmpty(RoleUtils.getRolesByAccountId(myForm.getUserData().getAccountId()));
%>
<html>
<head>
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Create User</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="javascript">
<!--

function initPage() {
  if (parent.topFrame) {
    parent.topFrame.document.all.banner.innerHTML = 'Create User';
  }
}
  
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.forms[0];
  f.btnSubmit.disabled = isDisabled;
}

function onFormSubmit() {
  setActionButtonEnabling(false);
  return true;
}
-->
</script>
</head>
<body class="bigr" onload="initPage();">
<html:form method="post" action="/orm/users/modifyUserStart.do" onsubmit="return(onFormSubmit());">
<html:hidden property="userData.userId"/>
<html:hidden property="userData.accountId"/>
<div align="center">
  <table cellpadding="0" cellspacing="0" border="0" class="background">
    <tr>
      <td>      
        <table border="0" cellpadding="3" cellspacing="1" class="foreground" width="679">
          <tr class="yellow"> 
            <td align="left">
              <b>User <bean:write name="maintainUserForm" property="userData.userId"/> in account <bean:write name="maintainUserForm" property="userData.accountId"/> has been created. Please remember to:</b> 
            </td>
          </tr>
          <tr> 
            <td class="green">
              <ul>
                <li><b>Grant this user access to the applicable privileges</b></li>
              </ul>
            </td>
          </tr>
<%
	if (rolesDefinedForAccount) {
%>
          <tr> 
            <td class="green">
              <ul>
                <li><b>Grant this user access to the applicable roles</b></li>
              </ul>
            </td>
          </tr>
<%
	}
%>
          <tr> 
            <td class="green">
              <ul>
                <li><b>Grant this user access to the applicable inventory groups</b></li>
              </ul>
            </td>
          </tr>
          <tr> 
            <td colspan="2" class="white" align="center"> 
              <html:submit property="btnSubmit" value="   Ok   "/>
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
