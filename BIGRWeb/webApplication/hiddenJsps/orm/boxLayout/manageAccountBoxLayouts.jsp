<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.iltds.helpers.FormLogic" %>
<%@ page import="com.ardais.bigr.util.Constants" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<html>
<head>
<title>Manage Account Box Layouts</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<html:rewrite page="/css/bigr.css"/>" type="text/css">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script type="text/javascript">
var isWarnOnNavigate = true;

function initPage() {
  
  var f = document.forms[0];
  var accountId = f.accountId.value;

  var myBanner = 'Manage Account Box Layouts for (' + htmlEscape(accountId) + ')';

  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
  
  document.forms[0].boxLayoutName.focus();
}

function confirmNavigate() {
  if (needNavagationWarning()) {
    event.returnValue = "You have made changes that you have not submitted yet.  Are you sure that you want to continue?";
  }
}

function needNavagationWarning() {
  if (! isWarnOnNavigate) return false;
  
  var f = document.forms[0];
  if (f.boxLayoutName.value != f.boxLayoutName.defaultValue) return true;

  var boxLayoutIdSelect = getFirstWithName(f.elements, "boxLayoutId");
  if (getControlValue(boxLayoutIdSelect) != getControlDefaultValue(boxLayoutIdSelect)) return true;

  if (f.defaultAccountBoxLayoutCB.checked != (f.defaultAccountBoxLayout.value == "true")) return true;

  return false;
}

function onClickCancel() {
  var f = document.forms[0];
  
  var accountId = f.accountId.value;
  
  setActionButtonEnabling(false);
  isWarnOnNavigate = false;
  window.location = '<html:rewrite page="/orm/boxLayout/manageAccountBoxLayoutStart.do?resetForm=true' + '&accountId=' + accountId + '"/>';
}

function onClickDelete(accountBoxLayoutId) {
  setActionButtonEnabling(false);
  var f = document.forms[0];
  var cancelAction = false;
  var savedOperation = f.operation.value;
  var savedAccountBoxLayoutId = f.accountBoxLayoutId.value;
  f.operation.value = '<%=Constants.OPERATION_DELETE%>';
  f.accountBoxLayoutId.value = accountBoxLayoutId;

  if (needNavagationWarning()) {
    if (!confirm("You have made changes that have not been saved.  Are you sure you want to continue?\n\nPress OK to continue, or Cancel to stay on the current page.")) {
      cancelAction = true;
    }
  }
  
  if (!cancelAction && !onFormSubmit()) {
    cancelAction = true;
  }
  
  if (cancelAction) { // restore field values
    f.operation.value = savedOperation;
    f.accountBoxLayoutId.value = savedAccountBoxLayoutId;
    setActionButtonEnabling(true);
  }
  else {
    isWarnOnNavigate = false;
    f.submit();
  }
}

function onFormSubmit() {
  setActionButtonEnabling(false);
  isWarnOnNavigate = false;

  return true;
}

function updateHiddenField() {
  document.all["defaultAccountBoxLayout"].value = document.all.defaultAccountBoxLayoutCB.checked;
}

   
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.forms[0];
  f.btnSubmit.disabled = isDisabled;
  f.btnCancel.disabled = isDisabled;
  f.btnReturn.disabled = isDisabled;
  setInputEnabled(f, "btnDelete", isEnabled);
  setInputEnabled(document.all, "linkEditBoxLayout", isEnabled);
}

function onClickReturnToAccountProfile() {
  setActionButtonEnabling(false);
  var f = document.forms[0];
  f.action='<html:rewrite page="/orm/accounts/modifyAccountStart.do?accountData.id=' + f.accountId.value + '"/>';
  f.submit();
}
</script>
</head>
<body class="bigr" onload="initPage();" onbeforeunload="confirmNavigate();">
<html:form method="POST" action="/orm/boxLayout/manageAccountBoxLayoutSave" onsubmit="return(onFormSubmit());">
  <html:hidden property="operation"/>
  <html:hidden property="accountBoxLayoutId"/>
  <html:hidden property="accountId"/>
  <table border="0" cellspacing="0" cellpadding="0" align="center">
    <%-- begin create/edit input table --%>
    <tr> 
      <td> 
        <table width="100%" border="1" cellspacing="1" cellpadding="3" class="foreground">
          <logic:present name="org.apache.struts.action.ERROR">
          <tr class="yellow">
	        <td colspan="2"><font color="#FF0000"><b><html:errors/></b></font></td>
          </tr>
          </logic:present>
          <logic:present name="com.ardais.BTX_ACTION_MESSAGES">
          <tr class="yellow"> 
            <td colspan="2"><font color="#000000"><b><bigr:btxActionMessages/></b></font></td>
	      </tr>
		  </logic:present>
          <tr class="white"> 
<logic:equal name="manageAccountBoxLayoutForm" property="operation" value="<%= Constants.OPERATION_CREATE %>">
            <td colspan="2" align="center"><b>Create a new account box layout</b></td>
</logic:equal>
<logic:equal name="manageAccountBoxLayoutForm" property="operation" value="<%= Constants.OPERATION_UPDATE %>">
            <td colspan="2" align="center">
              <b>Edit account box layout:
              <bean:write name="manageAccountBoxLayoutForm" property="boxLayoutName" />
              </b>
            </td>
</logic:equal>
          </tr>
          <tr class="white"> 
            <td class="grey" nowrap>Box Layout Name <font color="Red">*</font></td>
            <td> 
              <html:text property="boxLayoutName" size="30" maxlength="100"/>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey" nowrap>Box Layout <font color="Red">*</font></td>
            <td>
              <bigr:selectList
                name="manageAccountBoxLayoutForm" property="boxLayoutId" 
		        legalValueSetProperty="boxLayoutAttributeSet"
		        firstValue="" firstDisplayValue=""
		      />
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey" nowrap>Set as default account box layout?</td>
            <td>
              <logic:equal name="manageAccountBoxLayoutForm" property="defaultAccountBoxLayout" value="true">
              <input type="checkbox" name="defaultAccountBoxLayoutCB" onclick="updateHiddenField();" checked/>
              </logic:equal>
              <logic:equal name="manageAccountBoxLayoutForm" property="defaultAccountBoxLayout" value="false">
              <input type="checkbox" name="defaultAccountBoxLayoutCB" onclick="updateHiddenField();"/>
              </logic:equal>
              <html:hidden property="defaultAccountBoxLayout"/>
            </td>
          </tr>
          <tr class="white"> 
            <td colspan="2" align="center"> 
              <html:submit property="btnSubmit" value="Submit"/>
              <html:cancel property="btnCancel" value="Cancel" onclick="onClickCancel();"/>
              <input type="button" name="btnReturn" value="Return to Account Profile" onClick="onClickReturnToAccountProfile();">
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table> <%-- end create/edit input table --%>
  <br/>
  <table border="0" cellspacing="0" cellpadding="0" align="center">
  <%-- begin box layout list table --%>
    <tr> 
      <td>
        <table width="100%" border="1" cellspacing="1" cellpadding="3" class="foreground">
	      <tr class="white">
      	    <td class="grey" colspan="5">
          	  To edit an account box layout, click on its Account Box Layout
          	  Link.  To delete an account box layout, click on its Delete
          	  button.  The account box layout will be deleted as soon as you
          	  click on the button.  There can only be one designated default
          	  account box layout.
          	</td>
    	  </tr>
          <tr class="white">
            <td nowrap><b>Box Layout Name</b></td>
            <td nowrap align="center"><b>Box Layout Id</b></td>
            <td nowrap align="center"><b>Default</b></td>
            <td nowrap align="center"><b>Delete</b></td>
          </tr>
<logic:iterate id="alyForm" name="manageAccountBoxLayoutForm" property="accountBoxLayoutForms" type="com.ardais.bigr.orm.web.form.AccountBoxLayoutForm">
          <tr class="white">
            <td>
              <a id="linkEditBoxLayout"
                 href="<html:rewrite page="/orm/boxLayout/manageAccountBoxLayoutStart.do?operation=Update&accountId="/><bean:write name="alyForm" property="accountId"/>&accountBoxLayoutId=<bean:write name="alyForm" property="accountBoxLayoutId"/>"
                 onclick="return(isLinkEnabled());">
                <bean:write name="alyForm" property="boxLayoutName"/>
              </a>
            </td>
            <td align="center">
			  <bigr:icpLink linkText="<%=alyForm.getBoxLayoutId()%>" popup="true">
			    <%=alyForm.getBoxLayoutId()%>
			  </bigr:icpLink>
            </td>
            <td align="center">
              <html:checkbox name="alyForm" property="currentDefaultAccountBoxLayout" disabled="true"/>
            </td>
            <td align="center">
			  <button type="button" name="btnDelete" value="Delete"
                style="height: 16px; width: 16px;"
                onclick="onClickDelete('<bean:write name="alyForm" property="accountBoxLayoutId"/>');">
                <html:img page="/images/delete.gif" alt="Delete" border="0"/>
              </button>
            </td>
          </tr>
</logic:iterate>
        </table>
      </td>
    </tr>
  </table> <%-- end box layout list table --%>
</html:form>
</body>
</html>
