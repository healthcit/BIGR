<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.Escaper"%>
<%@ page import="com.ardais.bigr.util.Constants"%>
<%@ page import="com.ardais.bigr.iltds.assistants.LegalValueSet"%>
<%@ page import="com.ardais.bigr.security.SecurityInfo" %> 
<%@ page import="com.ardais.bigr.iltds.helpers.FormLogic" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<bean:define name="maintainAccountForm" id="myForm" type="com.ardais.bigr.orm.web.form.MaintainAccountForm"/>
<html>
<head>
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Modify Account</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="javascript">
<!--

var oldAccountType = '<%= myForm.getAccountData().getType() %>';

function initPage() {
  if (parent.topFrame) {
    parent.topFrame.document.all.banner.innerHTML = 'Modify Account';
  }
}
  
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.forms[0];
  if (f.btnLocations) {
    f.btnLocations.disabled = isDisabled;
  }
  if (f.btnIRBs) {
    f.btnIRBs.disabled = isDisabled;
  }
  if (f.btnBoxLayouts) {
    f.btnBoxLayouts.disabled = isDisabled;
  }
  if (f.btnShippingPartners) {
    f.btnShippingPartners.disabled = isDisabled;
  }
  if (f.btnPrivileges) {
    f.btnPrivileges.disabled = isDisabled;
  }
  if (f.btnInventoryGroups) {
    f.btnInventoryGroups.disabled = isDisabled;
  }
  f.btnUsers.disabled = isDisabled;
  f.btnSubmit.disabled = isDisabled;
  f.btnReset.disabled = isDisabled;
  f.btnCancel.disabled = isDisabled;
}

function onFormSubmit() {
  setActionButtonEnabling(false);
  return true;
}

function onLocationsClick() {
  <% String locationsURL = "/orm/accounts/manageAccountLocations.do?accountData.id=" + Escaper.jsEscapeInScriptTag(myForm.getAccountData().getId()); %>
  setActionButtonEnabling(false);
  window.location = '<html:rewrite page="<%= locationsURL %>"/>';
}

function onIRBsClick() {
  <% String irbURL = "/orm/Dispatch?op=IrbConsentStart&accountID=" + Escaper.jsEscapeInScriptTag(myForm.getAccountData().getId()); %>
  window.open('<html:rewrite page="<%= irbURL %>"/>','IrbConsentVersion','scrollbars=yes,resizable=yes,width=900,height=600');
}

function onBoxLayoutsClick() {
  <% String boxLayoutURL = "/orm/boxLayout/manageAccountBoxLayoutStart.do?accountId=" + Escaper.jsEscapeInScriptTag(myForm.getAccountData().getId()); %>
  setActionButtonEnabling(false);
  window.location = '<html:rewrite page="<%= boxLayoutURL %>"/>';
}

function onShippingPartnersClick() {
  <% String shippingPartnersURL = "/orm/shippingPartners/manageShippingPartnersStart.do?accountId=" + Escaper.jsEscapeInScriptTag(myForm.getAccountData().getId()); %>
  window.open('<html:rewrite page="<%= shippingPartnersURL %>"/>','ManageShippingPartners','scrollbars=yes,resizable=yes,width=800,height=600');
}

function onPrivilegesClick() {
  <%
    String privilegesURL = "/orm/managePrivilegesStart.do?objectType=" 
    + Constants.OBJECT_TYPE_ACCOUNT 
    + "&accountData.id=" + Escaper.jsEscapeInScriptTag(myForm.getAccountData().getId())
    + "&accountData.name=" + Escaper.jsEscapeInScriptTag(myForm.getAccountData().getName());
  %>
  setActionButtonEnabling(false);
  window.location = '<html:rewrite page="<%= privilegesURL %>"/>';
}

function onInventoryGroupsClick() {
  <%
    String inventoryGroupsURL = "/orm/manageInventoryGroupsStart.do?objectType=" 
    + Constants.OBJECT_TYPE_ACCOUNT 
    + "&accountData.id=" + Escaper.jsEscapeInScriptTag(myForm.getAccountData().getId())
    + "&accountData.name=" + Escaper.jsEscapeInScriptTag(myForm.getAccountData().getName());
  %>
  setActionButtonEnabling(false);
  window.location = '<html:rewrite page="<%= inventoryGroupsURL %>"/>';
}

function onUsersClick() {
  <%
    String usersURL = "/orm/users/findUsers.do?userData.accountId=" + Escaper.jsEscapeInScriptTag(myForm.getAccountData().getId());
  %>
  setActionButtonEnabling(false);
  window.location = '<html:rewrite page="<%= usersURL %>"/>';
}

function onSubmitClick() {
  if (dataIsValid() && accountTypeChangeVerified() && onFormSubmit()) {
    document.forms[0].submit();
  }
}

function onCancelClick() {
  document.forms[0].action='<html:rewrite page="/orm/accounts/findAccountsStart.do"/>';
  setActionButtonEnabling(false);
  document.forms[0].submit();
}
	
function areRequiredFieldsPopulated() {
  return ((document.forms[0]["accountData.type"].value != "")
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

function accountTypeChangeVerified() {
  var newAccountType = document.forms[0]["accountData.type"].value;
  if (oldAccountType != newAccountType && 
      oldAccountType == '<%= Constants.ACCOUNT_TYPE_CONSUMER %>') {
    var msg = 'The account type you have selected requires that information about locations, shipping partners, etc. be specified.';
    msg = msg + '\nTherefore, if you proceed with changing the type of this account please remember to provide that information.';
    msg = msg + '\n\nPress OK to continue, or Cancel to stay on the current page.';
    return confirm(msg);
  }
  else {
    return true;
  }
}
-->
</script>
</head>
<body class="bigr" onload="initPage();">
<html:form method="post" action="/orm/accounts/modifyAccount.do" onsubmit="return(onFormSubmit());">
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
            <bean:write name="maintainAccountForm" property="accountData.id"/>
            <html:hidden property="accountData.id"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>Account Type <font color="Red">*</font></b></div>
          </td>
          <td>
	          <bigr:isInRole role1="<%=SecurityInfo.ROLE_SYSTEM_OWNER%>">
              <bigr:selectList
                name="maintainAccountForm"
                property="accountData.type" 
		            legalValueSetProperty="accountTypeChoices"/>
		        </bigr:isInRole>
		        <bigr:notIsInRole role1="<%=SecurityInfo.ROLE_SYSTEM_OWNER%>">
              <%= (String)Constants.ACCOUNT_TYPE_MAP.get(myForm.getAccountData().getType()) %>
              <html:hidden property="accountData.type"/>
		        </bigr:notIsInRole>
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
	          <bigr:isInRole role1="<%=SecurityInfo.ROLE_SYSTEM_OWNER%>">
              <bigr:selectList
                name="maintainAccountForm"
                property="accountData.status" 
		            legalValueSetProperty="accountStatusChoices"/>
		        </bigr:isInRole>
		        <bigr:notIsInRole role1="<%=SecurityInfo.ROLE_SYSTEM_OWNER%>">
              <%= (String)Constants.ACCOUNT_STATUS_MAP.get(myForm.getAccountData().getStatus()) %>
              <html:hidden property="accountData.status"/>
		        </bigr:notIsInRole>
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
	          <bigr:isInRole role1="<%=SecurityInfo.ROLE_SYSTEM_OWNER%>">
              <bigr:selectList
                name="maintainAccountForm"
                property="accountData.viewLinkedCasesOnly" 
		            legalValueSetProperty="linkedCasesOnlyChoices"/>
		        </bigr:isInRole>
		        <bigr:notIsInRole role1="<%=SecurityInfo.ROLE_SYSTEM_OWNER%>">
              <%
                if (FormLogic.DB_YES.equalsIgnoreCase(myForm.getAccountData().getViewLinkedCasesOnly())) {
              %>
                  Yes
              <%
                }
                if (FormLogic.DB_NO.equalsIgnoreCase(myForm.getAccountData().getViewLinkedCasesOnly())) {
              %>
                  No
              <%
                }
              %>
              <html:hidden property="accountData.viewLinkedCasesOnly"/>
		        </bigr:notIsInRole>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey">
            <div align="right"><b>Require Sample Aliases <font color="Red">*</font></b></div>
          </td>
          <td>
	          <bigr:isInRole role1="<%=SecurityInfo.ROLE_SYSTEM_OWNER%>">
            <bigr:selectList
              name="maintainAccountForm"
              property="accountData.requireSampleAliases" 
		          legalValueSetProperty="requireSampleAliasesChoices"/>
		        </bigr:isInRole>
		        <bigr:notIsInRole role1="<%=SecurityInfo.ROLE_SYSTEM_OWNER%>">
              <%
                if (FormLogic.DB_YES.equalsIgnoreCase(myForm.getAccountData().getRequireSampleAliases())) {
              %>
                  Yes
              <%
                }
                if (FormLogic.DB_NO.equalsIgnoreCase(myForm.getAccountData().getRequireSampleAliases())) {
              %>
                  No
              <%
                }
              %>
              <html:hidden property="accountData.requireSampleAliases"/>
		        </bigr:notIsInRole>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey">
            <div align="right"><b>Require Unique Sample Aliases <font color="Red">*</font></b></div>
          </td>
          <td>
	          <bigr:isInRole role1="<%=SecurityInfo.ROLE_SYSTEM_OWNER%>">
            <bigr:selectList
              name="maintainAccountForm"
              property="accountData.requireUniqueSampleAliases" 
		          legalValueSetProperty="requireUniqueSampleAliasesChoices"/>
		        </bigr:isInRole>
		        <bigr:notIsInRole role1="<%=SecurityInfo.ROLE_SYSTEM_OWNER%>">
              <%
                if (FormLogic.DB_YES.equalsIgnoreCase(myForm.getAccountData().getRequireUniqueSampleAliases())) {
              %>
                  Yes
              <%
                }
                if (FormLogic.DB_NO.equalsIgnoreCase(myForm.getAccountData().getRequireUniqueSampleAliases())) {
              %>
                  No
              <%
                }
              %>
              <html:hidden property="accountData.requireUniqueSampleAliases"/>
		        </bigr:notIsInRole>
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
        <tr class="white"> 
          <td class="grey">
            <div align="right"><b>Default Donor Registration Form </b></div>
          </td>
          <td>
            <bigr:selectList
              name="maintainAccountForm"
              property="accountData.donorRegistrationFormId" 
		          legalValueSetProperty="donorRegistrationFormChoices"/>
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
        <%
        	//Include buttons to manage things like locations, IRBs, etc only if the account type
        	//is not Consumer
        %>
        <logic:notEqual name="maintainAccountForm" property="accountData.type" value="<%=Constants.ACCOUNT_TYPE_CONSUMER %>">
          <tr class="white"> 
            <td colspan="2"> 
              <div align="center">
                <html:button property="btnLocations" value="Manage Locations and Storage" onclick="onLocationsClick();"/>
              </div>
            </td>
          </tr>
          <tr class="white"> 
            <td colspan="2"> 
              <div align="center">
                <html:button property="btnIRBs" value="Edit IRB Protocols/Consent Versions" onclick="onIRBsClick();"/>
              </div>
            </td>
          </tr>
          <tr class="white"> 
            <td colspan="2"> 
              <div align="center">
                <html:button property="btnBoxLayouts" value="Manage Box Layouts" onclick="onBoxLayoutsClick();"/>
              </div>
            </td>
          </tr>
	        <bigr:isInRole role1="<%=SecurityInfo.ROLE_SYSTEM_OWNER%>">
            <tr class="white"> 
              <td colspan="2"> 
                <div align="center">
                  <html:button property="btnShippingPartners" value="Manage Shipping Partners" onclick="onShippingPartnersClick();"/>
                </div>
              </td>
            </tr>
          </bigr:isInRole>
        </logic:notEqual>
	      <bigr:isInRole role1="<%=SecurityInfo.ROLE_SYSTEM_OWNER%>">
          <tr class="white"> 
            <td colspan="2"> 
              <div align="center">
                <html:button property="btnPrivileges" value="Manage Assignable Privileges" onclick="onPrivilegesClick();"/>
              </div>
            </td>
          </tr>
          <tr class="white"> 
            <td colspan="2"> 
              <div align="center">
                <html:button property="btnInventoryGroups" value="Manage Assignable Inventory Groups" onclick="onInventoryGroupsClick();"/>
              </div>
            </td>
          </tr>
        </bigr:isInRole>
        <tr class="white"> 
          <td colspan="2"> 
            <div align="center">
              <html:button property="btnUsers" value="Show Users" onclick="onUsersClick();"/>
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