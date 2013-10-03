<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.Escaper" %>
<%@ page import="com.ardais.bigr.util.Constants" %>
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
<title>Account Management</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="javascript">
<!--

function initPage() {
  if (parent.topFrame) {
    parent.topFrame.document.all.banner.innerHTML = 'Account Management';
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
  //clear account id, since a new account is to be added
  document.forms[0]["accountData.id"].value = "";
  document.forms[0].action='<html:rewrite page="/orm/accounts/createAccountStart.do"/>';
  setActionButtonEnabling(false);
  document.forms[0].submit();
}

function onModifyClick(acctId) {
  document.forms[0]["accountData.id"].value = acctId;
  setActionButtonEnabling(false);
  document.forms[0].submit();
}

-->
</script>
</head>
<body class="bigr" onload="initPage();">
<html:form method="post" action="/orm/accounts/modifyAccountStart.do" onsubmit="return(onFormSubmit());">
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
    <html:hidden property="accountData.id"/>
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table cellpadding="3" cellspacing="1" border="0" class="foreground">
            <tr class="yellow"> 
              <td align="center"> 
                <b>Account Id</b>
              </td>
              <td align="center"> 
                <b>Account Type</b>
              </td>
              <td align="center"> 
                <b>Account Name</b>
              </td>
              <td align="center"> 
                <b>Status</b>
              </td>
              <td align="center"> 
                &nbsp;
              </td>
            </tr>
            <logic:iterate name="maintainAccountForm" property="matchingAccounts" indexId="accountCount" id="account" type="com.ardais.bigr.javabeans.AccountDto">
              <tr class="<%if((accountCount.intValue()%2) == 0) out.print("white"); else out.print("grey");%>"> 
                <td>
                  <bean:write name="account" property="id"/>
                </td>
                <td>
                  <logic:equal name="account" property="type" value="<%=Constants.ACCOUNT_TYPE_CONSUMER%>">
                    <%= Constants.ACCOUNT_TYPE_CONSUMER_DESC%>
                  </logic:equal>
                  <logic:equal name="account" property="type" value="<%=Constants.ACCOUNT_TYPE_DONOR_AND_CONSUMER%>">
                    <%= Constants.ACCOUNT_TYPE_DONOR_AND_CONSUMER_DESC%>
                  </logic:equal>
                  <logic:equal name="account" property="type" value="<%=Constants.ACCOUNT_TYPE_SYSTEM_OWNER%>">
                    <%= Constants.ACCOUNT_TYPE_SYSTEM_OWNER_DESC%>
                  </logic:equal>
                </td>
                <td>
                  <bean:write name="account" property="name"/>
                </td>
                <td>
                  <logic:equal name="account" property="status" value="A">
                    Active
                  </logic:equal>
                  <logic:equal name="account" property="status" value="I">
                    Inactive
                  </logic:equal>
                </td>
                <td>
                <%
                  String onClick = "onModifyClick('" + Escaper.jsEscapeInScriptTag(account.getId()) + "');";
                %>
                  <html:button property="btnModify" value="Edit" onclick="<%=onClick%>"/>
                </td>
              </tr>
            </logic:iterate>
            <tr class="white"> 
              <td colspan="6"> 
                <div align="center"> 
                  <html:button property="btnAdd" value="Add New" onclick="onAddNewClick();"/>
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
