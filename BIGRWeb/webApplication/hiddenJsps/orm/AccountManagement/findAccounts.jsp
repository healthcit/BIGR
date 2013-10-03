<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
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

myBanner = 'Account Management';

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
  document.forms[0]["accountData.id"].focus();
}

function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.forms[0];
  f.btnSearch.disabled = isDisabled;
  f.btnAdd.disabled = isDisabled;
}

function onFormSubmit() {
  setActionButtonEnabling(false);
  return true;
}

function onAddNewClick() {
  document.forms[0].action='<html:rewrite page="/orm/accounts/createAccountStart.do"/>';
  setActionButtonEnabling(false);
  document.forms[0].submit();
}
//-->
</script>
</head>
<body class="bigr" onLoad="initPage();">
<html:form action="/orm/accounts/findAccounts.do" onsubmit="return(onFormSubmit());">
  
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
  <table cellpadding="0" cellspacing="0" class="background" border="0">
    <tr>
      <td>
	      <table cellspacing="1" cellpadding="3" class="foreground" border="0">
          <tr class="yellow">
				    <td colspan="2">
              <div align="center">
                <b>Enter Account Information for Search</b>
              </div>
            </td> 
			    </tr>
			    <tr> 
            <td class="grey"> 
              <div align="right">
                <b>Account Id:</b>
              </div>
            </td>
            <td class="white"> 
              <html:text property="accountData.id" maxlength="10" size="20"/>
            </td>
          </tr>
          <tr> 
            <td class="grey"> 
              <div align="right">
                <b>Account Type:</b>
              </div>
            </td>
            <td class="white"> 
              <bigr:selectList
                name="maintainAccountForm"
                property="accountData.type" 
				        legalValueSetProperty="accountTypeChoices"/>
            </td>
          </tr>
          <tr> 
            <td class="grey"> 
              <div align="right">
                <b>Account Name:</b>
              </div>
            </td>
            <td class="white"> 
              <html:text property="accountData.name" maxlength="40" size="20"/>
            </td>
          </tr>
          <tr class="white"> 
            <td colspan="2"> 
              <div align="center"> 
                <html:submit property="btnSearch" value="Search"/>
                <html:button property="btnAdd" value="Add New" onclick="onAddNewClick();"/>
              </div>
            </td>
          </tr>
        </table> 
      </td>
    </tr>
  </table>
</html:form>
</body>
</html>