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
<title>User Management</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="javascript">
<!--

function initPage() {
  if (parent.topFrame) {
    parent.topFrame.document.all.banner.innerHTML = 'User Management';
  }
  document.forms[0]["userData.userId"].focus();
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
  document.forms[0].action='<html:rewrite page="/orm/users/createUserStart.do"/>';
  setActionButtonEnabling(false);
  document.forms[0].submit();
}

-->
</script>
</head>
<body class="bigr" onLoad="initPage();">
<html:form action="/orm/users/findUsers.do" onsubmit="return(onFormSubmit());">
  
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
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr>
        <td> 
          <table class="foreground" cellpadding="3" cellspacing="1" border="0">
            <tr class="yellow"> 
              <td colspan="2"> 
                <div align="center"><b>Enter User Information for Search</b></div>
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey"> 
                <div align="right"><b>User Id</b></div>
              </td>
              <td> 
                <html:text property="userData.userId" maxlength="12" size="27"/>
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey"> 
                <div align="right"><b>Account Id</b></div>
              </td>
              <td> 
                <bigr:selectList
                  name="maintainUserForm"
                  property="userData.accountId" 
				  legalValueSetProperty="accountChoices"/>
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey"> 
                <div align="right"><b>User Last Name</b></div>
              </td>
              <td> 
                <html:text property="userData.lastName" maxlength="25" size="27"/>
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey"> 
                <div align="right"> 
                  <p><b>User First Name</b></p>
                </div>
              </td>
              <td> 
                <html:text property="userData.firstName" maxlength="25" size="27"/>
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
  </div>
</html:form>
</body>
</html>