<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.iltds.assistants.LegalValueSet"%>
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
<title>Create Account</title>
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
  f.btnReset.disabled = isDisabled;
  f.btnCancel.disabled = isDisabled;
}

function onFormSubmit() {
  setActionButtonEnabling(false);
  return true;
}

function onSubmitClick() {
  if (dataIsValid() && onFormSubmit()) {
    document.forms[0].submit();
  }
}

function onCancelClick() {
  document.forms[0].action='<html:rewrite page="/orm/accounts/findAccountsStart.do"/>';
  setActionButtonEnabling(false);
  document.forms[0].submit();
}
	
function areRequiredFieldsPopulated() {
  return ((document.forms[0]["accountData.id"].value != "")
        &&(document.forms[0]["accountData.type"].value != "")
	      &&(document.forms[0]["accountData.name"].value != "")
	      &&(document.forms[0]["accountData.status"].value != "")
	      &&(document.forms[0]["accountData.viewLinkedCasesOnly"].value != "")
	      &&(document.forms[0]["accountData.requireSampleAliases"].value != "")
	      &&(document.forms[0]["accountData.requireUniqueSampleAliases"].value != "")
	      &&(document.forms[0]["accountData.passwordPolicy"].value != "")
	      &&(document.forms[0]["accountData.passwordLifeSpan"].value != "")
	       )
}

function dataIsValid() {
  if (!areRequiredFieldsPopulated()) {
	  alert("Please enter values for all fields marked with a RED *");
	  return false;
  }
  return true;
}
-->
</script>
</head>
<body class="bigr" onload="initPage();">
<html:form method="post" action="/orm/accounts/createAccount.do" onsubmit="return(onFormSubmit());">
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
            <div align="center"><b>Account Information</b></div>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>Account Id <font color="Red">*</font></b></div>
          </td>
          <td> 
            <html:text property="accountData.id" size="10" maxlength="10" onchange="this.value=trim(this.value);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>Account Type <font color="Red">*</font></b></div>
          </td>
          <td>
            <bigr:selectList
              name="maintainAccountForm"
              property="accountData.type" 
		          legalValueSetProperty="accountTypeChoices"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>Account Name <font color="Red">*</font></b></div>
          </td>
          <td> 
            <html:text property="accountData.name" size="40" maxlength="40"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey">
            <div align="right"><b>Account Status <font color="Red">*</font></b></div>
          </td>
          <td>
            <bigr:selectList
              name="maintainAccountForm"
              property="accountData.status" 
		          legalValueSetProperty="accountStatusChoices"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>Request Manager Email Address</b></div>
          </td>
          <td> 
            <html:text property="accountData.requestManagerEmail" size="50" maxlength="100"
              onchange="javascript:while(''+this.value.charAt(0)==' ')this.value=this.value.substring(1,this.value.length);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey">
            <div align="right"><b>View Linked Cases Only <font color="Red">*</font></b></div>
          </td>
          <td>
            <bigr:selectList
              name="maintainAccountForm"
              property="accountData.viewLinkedCasesOnly" 
		          legalValueSetProperty="linkedCasesOnlyChoices"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey">
            <div align="right"><b>Require Sample Aliases <font color="Red">*</font></b></div>
          </td>
          <td>
            <bigr:selectList
              name="maintainAccountForm"
              property="accountData.requireSampleAliases" 
		          legalValueSetProperty="requireSampleAliasesChoices"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey">
            <div align="right"><b>Require Unique Sample Aliases <font color="Red">*</font></b></div>
          </td>
          <td>
            <bigr:selectList
              name="maintainAccountForm"
              property="accountData.requireUniqueSampleAliases" 
		          legalValueSetProperty="requireUniqueSampleAliasesChoices"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey">
            <div align="right"><b>Default User Password Policy <font color="Red">*</font></b></div>
          </td>
          <td>
            <bigr:selectList
              name="maintainAccountForm"
              property="accountData.passwordPolicy" 
		          legalValueSetProperty="passwordPolicyChoices"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>User Password Life Span (days) <font color="Red">*</font></b></div>
          </td>
          <td> 
            <html:text property="accountData.passwordLifeSpan" size="3" maxlength="3"/>
            <br>(This is the password lifespan for any users in this account whose passwords expire, either due to 
            the Password Policy value for the account or due to the Password Policy value for that user.)
          </td>
        </tr>
        <tr class="yellow"> 
          <td colspan="2"> 
            <div align="center"><b>Contact Information</b></div>
          </td>
        </tr>
        <tr class="white"> 
          <td nowrap class="grey"> 
            <div align="right"><b>First Name</b></div>
          </td>
          <td> 
            <html:text property="accountData.contact.address.firstName" size="35" maxlength="35"
              onchange="javascript:while(''+this.value.charAt(0)==' ')this.value=this.value.substring(1,this.value.length);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td nowrap class="grey"> 
            <div align="right"><b>Last Name</b></div>
          </td>
          <td> 
            <html:text property="accountData.contact.address.lastName" size="30" maxlength="30"
              onchange="javascript:while(''+this.value.charAt(0)==' ')this.value=this.value.substring(1,this.value.length);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>Middle Name</b></div>
          </td>
          <td>
            <html:text property="accountData.contact.address.middleName" size="2" maxlength="2" 
              onchange="javascript:while(''+this.value.charAt(0)==' ')this.value=this.value.substring(1,this.value.length);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td nowrap class="grey"> 
            <div align="right"><b>Phone Number</b></div>
          </td>
          <td> 
            <html:text property="accountData.contact.phoneNumber" size="20" maxlength="20"
              onchange="javascript:while(''+this.value.charAt(0)==' ')this.value=this.value.substring(1,this.value.length);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td nowrap class="grey"> 
            <div align="right"><b>Extension</b></div>
          </td>
          <td> 
            <html:text property="accountData.contact.extension" size="10" maxlength="10"
              onchange="javascript:while(''+this.value.charAt(0)==' ')this.value=this.value.substring(1,this.value.length);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td nowrap class="grey"> 
            <div align="right"><b>Fax Number</b></div>
          </td>
          <td> 
            <html:text property="accountData.contact.faxNumber" size="20" maxlength="20"
              onchange="javascript:while(''+this.value.charAt(0)==' ')this.value=this.value.substring(1,this.value.length);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td nowrap class="grey"> 
            <div align="right"><b>Mobile Number</b></div>
          </td>
          <td> 
            <html:text property="accountData.contact.mobileNumber" size="20" maxlength="20"
              onchange="javascript:while(''+this.value.charAt(0)==' ')this.value=this.value.substring(1,this.value.length);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td nowrap class="grey"> 
            <div align="right"><b>Pager Number</b></div>
          </td>
          <td> 
            <html:text property="accountData.contact.pagerNumber" size="20" maxlength="20"
              onchange="javascript:while(''+this.value.charAt(0)==' ')this.value=this.value.substring(1,this.value.length);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td nowrap class="grey"> 
            <div align="right"><b>Email Address</b></div>
          </td>
          <td> 
            <html:text property="accountData.contact.email" size="50" maxlength="100"
              onchange="javascript:while(''+this.value.charAt(0)==' ')this.value=this.value.substring(1,this.value.length);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td nowrap class="grey"> 
            <div align="right"><b>Address 1</b></div>
          </td>
          <td> 
            <html:text property="accountData.contact.address.address1" size="60" maxlength="60"
              onchange="javascript:while(''+this.value.charAt(0)==' ')this.value=this.value.substring(1,this.value.length);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td nowrap class="grey"> 
            <div align="right"><b>Address 2</b></div>
          </td>
          <td> 
            <html:text property="accountData.contact.address.address2" size="60" maxlength="60"
              onchange="javascript:while(''+this.value.charAt(0)==' ')this.value=this.value.substring(1,this.value.length);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>City</b></div>
          </td>
          <td> 
            <html:text property="accountData.contact.address.city" size="25" maxlength="25"
              onchange="javascript:while(''+this.value.charAt(0)==' ')this.value=this.value.substring(1,this.value.length);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>State</b></div>
          </td>
          <td> 
            <div align="left"> 
              <html:text property="accountData.contact.address.state" size="25" maxlength="25"
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
              <html:text property="accountData.contact.address.zipCode" size="15" maxlength="15"
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
              <html:text property="accountData.contact.address.country" size="35" maxlength="35"
                onchange="javascript:while(''+this.value.charAt(0)==' ')this.value=this.value.substring(1,this.value.length);"/>
            </div>
          </td>
        </tr>
        <tr class="white"> 
          <td colspan="2"> 
            <div align="center">
              <html:button property="btnSubmit" value="Submit" onclick="onSubmitClick();"/>
              <html:reset property="btnReset" value="Reset"/>
              <html:button property="btnCancel" value="Cancel" onclick="onCancelClick();"/>
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