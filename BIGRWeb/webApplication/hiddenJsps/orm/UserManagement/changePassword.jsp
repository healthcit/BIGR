<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.orm.helpers.FormLogic"%>
<%@ page import="com.ardais.bigr.util.Constants"%>
<%@ taglib uri="/tld/struts-html" prefix="html"%>
<%@ taglib uri="/tld/struts-logic" prefix="logic"%>
<%@ taglib uri="/tld/struts-bean" prefix="bean"%>
<%@ taglib uri="/tld/bigr" prefix="bigr"%>
<bean:define name="passwordForm" id="myForm" type="com.ardais.bigr.orm.web.form.PasswordForm"/>
<%
  String pageDispatchArg = "P";
  if (!myForm.isRequireLogin()) {
    pageDispatchArg = "N";
  }
  com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(), pageDispatchArg);
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Change Password</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script type="text/javascript" src="<html:rewrite page="/js/common.js"/>"> </script>
<script language="JavaScript">
<!--

var myBanner = 'Change Password';

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
  <%
    if (myForm.isRequireOldPassword()) {
  %>
      document.forms[0]["oldPassword"].focus();
  <%
    }
    else {
  %>
	  document.forms[0]["userData.password1"].focus();
  <%
    }
  %>
}

function setActionButtonEnabling(isEnabled) {
  var f = document.forms[0];
  setInputEnabled(f,'btnSubmit',isEnabled);
  <%
    if (myForm.isRequireLogin()) {
  %>
      setInputEnabled(f,'btnCancel',isEnabled);
  <%
    }
  %>
}

function onFormSubmit() {
  setActionButtonEnabling(false);
  return true;
}

function onSubmitClick() {
  if (onFormSubmit()) {
    document.forms[0].submit();
  }
}

function onCancelClick()
{
  window.close();
}

//-->
</script>
</head>
<body class="bigr" onload="initPage();">
<%
  String action = "/orm/changePassword";
  if (!myForm.isRequireLogin()) {
    action = action + "NoLoginRequired";
  }
%>
<html:form action="<%=action%>" onsubmit="return(onFormSubmit());">
  <html:hidden property="userData.userId"/>
  <html:hidden property="userData.accountId"/>
  <html:hidden property="reasonForChange"/>
  <html:hidden property="requireLogin"/>
  <html:hidden property="requireOldPassword"/>
  <table border="0" cellspacing="0" cellpadding="0" align="center">
    <logic:present name="org.apache.struts.action.ERROR">
    <tr> 
      <td>
        <table border="1" cellspacing="1" cellpadding="1" width="100%" class="foreground-small">
		  <tr class="white">
			<td><div align="left"><font color="#FF0000"><b><html:errors/></b></font></div></td>
		  </tr>
		</table>
	  </td>
	</tr>
	</logic:present>
	<logic:present name="com.ardais.BTX_ACTION_MESSAGES">
    <tr> 
      <td>
        <table border="1" cellspacing="1" cellpadding="1" width="100%" class="foreground-small">
		  <tr class="white">
		    <td><div align="left"><font color="#000000"><b><bigr:btxActionMessages/></b></font></div></td>
		  </tr>
		</table>
	  </td>
	</tr>
	</logic:present>
	<tr> 
	  <td> 
		<table border="1" cellspacing="1" cellpadding="3" width="100%" class="foreground" align="center">
		  <%
		    String message = null;
		    if (Constants.PASSWORD_CHANGE_REASON_EXPIRED.equalsIgnoreCase(myForm.getReasonForChange())) {
		      message = "Your password has expired - please enter a new password";
		    }
		    else {
		      message = "Please enter the new password";
		    }
		  %>
          <tr class="yellow"> 
            <td colspan="2"> 
              <div align="center"><b><%=message%></b></div>
            </td>
          </tr>
		  <logic:equal name="passwordForm" property="requireOldPassword" value="true">
            <tr class="white"> 
              <td class="grey" ><div align="right"><b>Old Password<font color="Red">*</font></b></div></td>
              <td><html:password property="oldPassword" size="60" maxlength="60"/></td>
            </tr>
		  </logic:equal>
          <tr class="white"> 
            <td class="grey" ><div align="right"><b>New Password<font color = "Red">*</font></b></div></td>
            <td><html:password property="userData.password1" size="60" maxlength="60"/></td>
          </tr>
          <tr class="white"> 
            <td class="grey" ><div align="right"><b>Confirm Password<font color = "Red">*</font></b></div></td>
            <td><html:password property="userData.password2" size="60" maxlength="60"/></td>
          </tr>
          <tr class="white"> 
            <td colspan="2"> 
              <div align="center">
                <html:submit property="btnSubmit" value="Submit" onclick="onSubmitClick();"/>
				<%
				  //only show the Cancel button if the user has already logged in - if they arrived here without
				  //logging in it's because their password has expired and they don't have the option of canceling
				  //out of the password change
				  if (myForm.isRequireLogin()) {
				%>
                    <html:button property="btnCancel" value="Cancel" onclick="onCancelClick();"/>
                <%
                  }
                %>
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
