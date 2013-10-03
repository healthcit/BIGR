<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.util.Constants"%>
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
<title>Create User</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="javascript">
<!--

function initPage() {
  if (parent.topFrame) {
    parent.topFrame.document.all.banner.innerHTML = 'Create Account';
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
<html:form method="post" action="/orm/accounts/modifyAccountStart.do" onsubmit="return(onFormSubmit());">
<html:hidden property="accountData.id"/>
<div align="center">
  <table cellpadding="0" cellspacing="0" border="0" class="background">
    <tr>
      <td>      
        <table border="0" cellpadding="3" cellspacing="1" class="foreground" width="679">
          <tr class="yellow"> 
            <td align="left">
              <b>Account <bean:write name="maintainAccountForm" property="accountData.id"/> has been created. Please remember to:</b> 
            </td>
          </tr>
          <tr> 
            <td class="white">
              <ul>
                <logic:notEqual name="maintainAccountForm" property="accountData.type" value="<%= Constants.ACCOUNT_TYPE_CONSUMER %>">
                  <li>
                    <b>Specify location(s) for the account, if applicable.</b>
                  </li>
                </logic:notEqual>
                <li>
                  <b>Create inventory groups for the account, if applicable.</b>
                </li>
                <logic:notEqual name="maintainAccountForm" property="accountData.type" value="<%= Constants.ACCOUNT_TYPE_CONSUMER %>">
                  <li>
                    <b>Create policies for the account, if applicable.</b>
                  </li>
                  <li>
                    <b>Create IRBs for the account, if applicable.</b>
                  </li>
                  <li>
                    <b>Create box layouts for the account, if applicable.</b>
                  </li>
                  <li>
                    <b>Specify box layouts used by the account, if applicable.</b>
                  </li>
                  <li>
                    <b>Specify shipping partners for the account locations, if applicable.</b>
                  </li>
                </logic:notEqual>
                <li>
                  <b>Specify inventory groups the account can self-manage, if applicable.</b>
                </li>
                <li>
                  <b>Specify privileges the account can self-manage, if applicable.</b>
                </li>
                <li>
                  <b>Create users within the account.</b>
                </li>
                <li>
                  <b>Create a donor registration form for the account and update the account with that form, if applicable.</b>
                </li>
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
