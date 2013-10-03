<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.util.Constants"%>
<%@ page import="com.ardais.bigr.iltds.assistants.LegalValueSet"%>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<bean:define name="maintainUserForm" id="myForm" type="com.ardais.bigr.orm.web.form.MaintainUserForm"/>
<html>
<head>
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Change Profile</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="javascript">
<!--

function initPage() {
  if (parent.topFrame) {
    parent.topFrame.document.all.banner.innerHTML = 'Change Profile';
  }
}
  
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.forms[0];
  f.btnChangePassword.disabled = isDisabled;
  f.btnSubmit.disabled = isDisabled;
  f.btnReset.disabled = isDisabled;
}

function onFormSubmit() {
  setActionButtonEnabling(false);
  return true;
}

function onChangePasswordClick() {
  <%
    StringBuffer passwordUrl = new StringBuffer(200);
    passwordUrl.append("/orm/changePasswordStart.do");
    passwordUrl.append("?requireLogin=true");
    passwordUrl.append("&requireOldPassword=true");
    passwordUrl.append("&userData.userId=");
    passwordUrl.append(myForm.getUserData().getUserId());
    passwordUrl.append("&userData.accountId=");
    passwordUrl.append(myForm.getUserData().getAccountId());
  %>
  window.open('<html:rewrite page="<%=passwordUrl.toString()%>"/>','ChangePassword','resizable=yes,status=yes,width=550,height=400,left=400,top=200')
}

function onSubmitClick() {
  if (dataIsValid() && onFormSubmit()) {
    document.forms[0].submit();
  }
}
	
function areRequiredFieldsPopulated() {
  return ((document.forms[0]["userData.firstName"].value != "")
	&&(document.forms[0]["userData.lastName"].value != "")
	&&(document.forms[0]["userData.email"].value !=""))
}

function dataIsValid() {
  if (!areRequiredFieldsPopulated()) {
	alert("Please enter values for all fields marked with a RED *");
	return false;
  }
  return true;
}
	
function validateExtension(field) {
  var ok = true;
  var temp;
  var msg = "Extension must contain only numbers, and be at most 10 characters.";
  if (field.value.length > 0) {
    if (field.value.length <= 10) {
      for (var i=0; i<field.value.length; i++) {
        temp = "" + field.value.substring(i, i+1);
		if ("0123456789".indexOf(temp) == "-1") {
		  ok = false;
		}
	  }
	}
	else {
	  ok = false;
	}
	if (!ok) {
	  alert(msg);
	  field.focus();
	  field.select();
	  return false;
	}
  }
}

-->
</script>
</head>
<body class="bigr" onload="initPage();">
<html:form method="post" action="/orm/users/changeProfile.do" onsubmit="return(onFormSubmit());">

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
  <table border="0" cellpadding="0" cellspacing="0" align="center" class="background">
  <tr> 
    <td>
      <table border="0" align="center" cellspacing="1" cellpadding="3" class="foreground">
        <tr class="yellow"> 
          <td colspan="2"> 
            <div align="center"><b>User Information</b></div>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>First Name <font color="Red">*</font></b></div>
          </td>
          <td> 
            <html:text property="userData.firstName" size="25" maxlength="25"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>Last Name <font color="Red">*</font></b></div>
          </td>
          <td> 
            <html:text property="userData.lastName" size="25" maxlength="25"/>
          </td>
        </tr>
        <tr class="white">
          <td class="grey"> 
            <div align="right"><b>Email <font color="Red">*</font></b></div>
          </td>
          <td> 
            <html:text property="userData.email" size="60" maxlength="100"
              onchange="javascript:while(''+this.value.charAt(0)==' ')this.value=this.value.substring(1,this.value.length);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>Department</b></div>
          </td>
          <td> 
            <html:text property="userData.department" size="50" maxlength="200"
              onchange="javascript:while(''+this.value.charAt(0)==' ')this.value=this.value.substring(1,this.value.length);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>Title</b></div>
          </td>
          <td> 
            <bigr:selectList
              name="maintainUserForm"
              property="userData.title" 
		 	  legalValueSetProperty="titleChoices"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>Functional Role</b></div>
          </td>
          <td> 
            <html:text property="userData.functionalTitle" size="44" maxlength="44"
              onchange="javascript:while(''+this.value.charAt(0)==' ')this.value=this.value.substring(1,this.value.length);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>Affiliation</b></div>
          </td>
          <td> 
            <html:text property="userData.affiliation" size="44" maxlength="44"
              onchange="javascript:while(''+this.value.charAt(0)==' ')this.value=this.value.substring(1,this.value.length);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>Phone Number</b></div>
          </td>
          <td> 
            <html:text property="userData.phoneNumber" size="20" maxlength="20"
              onchange="javascript:while(''+this.value.charAt(0)==' ')this.value=this.value.substring(1,this.value.length);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>Extension</b></div>
          </td>
          <td> 
            <html:text property="userData.extension" size="10" maxlength="10"
              onblur="validateExtension(this);"
              onchange="javascript:while(''+this.value.charAt(0)==' ')this.value=this.value.substring(1,this.value.length);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>Fax Number</b></div>
          </td>
          <td> 
            <html:text property="userData.faxNumber" size="20" maxlength="20"
              onchange="javascript:while(''+this.value.charAt(0)==' ')this.value=this.value.substring(1,this.value.length);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>Mobile Number</b></div>
          </td>
          <td> 
            <html:text property="userData.mobileNumber" size="20" maxlength="20"
              onchange="javascript:while(''+this.value.charAt(0)==' ')this.value=this.value.substring(1,this.value.length);"/>
          </td>
        </tr>
        <tr class="yellow"> 
          <td colspan="2"> 
            <div align="center"><b>Contact Address</b></div>
          </td>
        </tr>
        <tr class="white"> 
          <td nowrap class="grey"> 
            <div align="right"><b>Address 1</b></div>
          </td>
          <td> 
            <html:text property="userData.address.address1" size="60" maxlength="60"
              onchange="javascript:while(''+this.value.charAt(0)==' ')this.value=this.value.substring(1,this.value.length);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td nowrap class="grey"> 
            <div align="right"><b>Address 2</b></div>
          </td>
          <td> 
            <html:text property="userData.address.address2" size="60" maxlength="60"
              onchange="javascript:while(''+this.value.charAt(0)==' ')this.value=this.value.substring(1,this.value.length);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>City</b></div>
          </td>
          <td> 
            <html:text property="userData.address.city" size="25" maxlength="25"
              onchange="javascript:while(''+this.value.charAt(0)==' ')this.value=this.value.substring(1,this.value.length);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>State</b></div>
          </td>
          <td> 
            <div align="left"> 
              <html:text property="userData.address.state" size="25" maxlength="25"
                onchange="javascript:while(''+this.value.charAt(0)==' ')this.value=this.value.substring(1,this.value.length);"/>
            </div>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>Postal Code</b></div>
          </td>
          <td> 
            <div align="left"> 
              <html:text property="userData.address.zipCode" size="15" maxlength="15"
                onchange="javascript:while(''+this.value.charAt(0)==' ')this.value=this.value.substring(1,this.value.length);"/>
            </div>
          </td>
        </tr>
        <tr class="white"> 
          <td nowrap class="grey"> 
            <div align="right"><b>Country</b></div>
          </td>
          <td> 
            <div align="left"> 
              <html:text property="userData.address.country" size="35" maxlength="35"
                onchange="javascript:while(''+this.value.charAt(0)==' ')this.value=this.value.substring(1,this.value.length);"/>
            </div>
          </td>
        </tr>
        <tr class="yellow"> 
          <td colspan="2"> 
            <div align="center"><b>Password Verification Question and Answer</b></div>
          </td>
        </tr>
        <tr class="white">
          <td class="grey">
            <div align="right"><b>Question</b></div>
          </td>
          <td>
            <div align="left">
              <html:text property="userData.passwordQuestion" size="100" maxlength="100"
                onchange="javascript:while(''+this.value.charAt(0)==' ')this.value=this.value.substring(1,this.value.length);"/>
            </div>
          </td>
        </tr>
        <tr class="white">
          <td class="grey">
            <div align="right"><b>Answer</b></div>
          </td>
          <td>
            <div align="left">
              <html:text property="userData.passwordAnswer" size="100" maxlength="100"
                onchange="javascript:while(''+this.value.charAt(0)==' ')this.value=this.value.substring(1,this.value.length);"/>
            </div>
          </td>
        </tr>
        <tr class="white"> 
          <td colspan="2"> 
            <div align="center">
              <table border="0" cellpadding="1" cellspacing="1" align="center" class="foreground">
                <tr>
                  <td align="center">
                    <html:button property="btnChangePassword" value="Change Password" onclick="onChangePasswordClick();"/>
                  </td>
                </tr>
                <tr>
                  <td align="center">
                    <html:button property="btnSubmit" value="Submit" onclick="onSubmitClick();"/>
                    <html:reset property="btnReset" value="Reset"/>
                  </td>
                </tr>
              </table>
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