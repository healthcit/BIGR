<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.security.*" %>
<%@ page import="com.ardais.bigr.util.WebUtils" %>
<%@ page import="com.ardais.bigr.iltds.helpers.IltdsUtils" %>
<%@ page import="com.ardais.bigr.kc.web.form.def.FormDefinitionForm" %>
<%@ page import="com.ardais.bigr.kc.form.def.BigrFormDefinition" %>
<%@ page import="com.gulfstreambio.kc.form.def.FormDefinitionTypes" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<bean:define id="myForm" name="formDefinitionForm" type="com.ardais.bigr.kc.web.form.def.FormDefinitionForm"/>
<%
boolean isCreate = ApiFunctions.isEmpty(myForm.getFormDefinitionId());
String action = (isCreate) ? "/kc/formdef/create" : "/kc/formdef/update";
boolean isQueryForm = FormDefinitionTypes.QUERY.equals(myForm.getFormType());

//show account as a choice only for System Owners 
SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);
boolean isAccountChoosable = securityInfo.isInRoleSystemOwner();
String defaultAccount = securityInfo.getAccount();
String defaultAccountDisplay = IltdsUtils.getSingleAccountInList(defaultAccount);
%>

<html>
<head>
<title>Form Designer</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript">
var isWarnOnNavigate = true;

function initPage() {
  if (parent.topFrame) {
    parent.topFrame.document.all.banner.innerHTML = "Form Designer";
  }
<%
String confirmMessage = myForm.getConfirmRemoveMessage();
if (!ApiFunctions.isEmpty(confirmMessage)) {
%>
  if (confirm('<%=confirmMessage%>')) {
    var f = document.forms[0];
    f.confirmedRemove.value = 'true';
    f.submit();
  }
<%
}
%>
}

function confirmNavigate() {
  if (needNavagationWarning()) {
    return confirm("You have made changes that have not been saved.  Are you sure you want to continue?\n\nPress OK to continue, or Cancel to stay on the current page.");
  }
  else {
  	return true;
  }
}

function needNavagationWarning() {
  if (! isWarnOnNavigate) return false;
  
  var f = document.forms[0];

  if (f.formDefinitionXml.value != f.formDefinitionXml.defaultValue) {
  	return true;
  }

<%
if (isCreate) {
%>
  for (var i = 0; i < f.formType.length; i++) {
	  if (f.formType[i].defaultChecked && !f.formType[i].checked) {
	  	return true;
	  }
  }
<%
}
%>

  var def = false;
  for (var i = 0; i < f.objectType.length; i++) {
	  if (f.objectType.options[i].defaultSelected) {
	  	def = true;
		  if (!f.objectType.options[i].selected) {
		  	return true;
		  }
	  }
  }
  if (!def && !f.objectType.options[0].selected) {
  	return true;	  	
  }

  def = false;
  for (var i = 0; i < f.uses.length; i++) {
	  if (f.uses.options[i].defaultSelected) {
	  	def = true;
		  if (!f.uses.options[i].selected) {
		  	return true;
		  }
	  }
  }
  if (!def && !f.uses.options[0].selected) {
  	return true;	  	
  }

<% //account is a choice only for System Owners 
	if (isAccountChoosable) {
%>
	def = false;
  for (var i = 0; i < f.account.length; i++) {
	  if (f.account.options[i].defaultSelected) {
	  	def = true;
		  if (!f.account.options[i].selected) {
		  	return true;
		  }
	  }
  }
  if (!def && !f.account.options[0].selected) {
  	return true;	  	
  }
 <% 
	 }
%>

  for (var i = 0; i < f.enabled.length; i++) {
	  if (f.enabled[i].defaultChecked && !f.enabled[i].checked) {
	  	return true;
	  }
  }
  return false;
}

function onClickEdit() {
  setActionButtonEnabling(false);
  var cancelAction = confirmNavigate() ? false : true;
  if (cancelAction) {
    setActionButtonEnabling(true);
  }
  else {
	  isWarnOnNavigate = false;
    var anchor = event.srcElement;
    url = anchor.href;
    url += '&selectedAccount=';
    url += document.forms[0].selectedAccount.value;
    anchor.href = url;
  } 
}

function onClickCancel() {
  setActionButtonEnabling(false);
  var cancelAction = confirmNavigate() ? false : true;
  if (cancelAction) {
    setActionButtonEnabling(true);
  }
  else {
	  isWarnOnNavigate = false;
		var url = '<html:rewrite page="/kc/formdef/start"/>';
    url += '.do?selectedAccount=';
    url += document.forms[0].selectedAccount.value;
	  window.location = url;
  }
}

function onClickGo() {
  setActionButtonEnabling(false);
  var cancelAction = confirmNavigate() ? false : true;
  if (cancelAction) {
    setActionButtonEnabling(true);
  }
  else {
    isWarnOnNavigate = false;
    var url = '<html:rewrite page="/kc/formdef/start/account"/>';
    url += '.do?selectedAccount=';
    url += document.forms[0].selectedAccount.value;
    window.location = url;
  }
}

function onClickDelete(id) {
  setActionButtonEnabling(false);
  var cancelAction = confirmNavigate() ? false : true;
  if (cancelAction) {
    setActionButtonEnabling(true);
  }
  else {
    isWarnOnNavigate = false;    
    document.forms[0].reset();
	  document.forms[0].formDefinitionId.value = id; 
	  document.forms[0].action = '<html:rewrite page="/kc/formdef/delete"/>' + '.do';
    document.forms[0].submit();
  }
}

function onClickFormType() {
  var f = document.forms[0];
  var queryForm = false;
  for (var i = 0; i < f.formType.length; i++) {
	  if (f.formType[i].checked && (f.formType[i].value == '<%=FormDefinitionTypes.QUERY%>')) {
	    queryForm = true;
	  }
  }
  if (queryForm) {
    document.all.objectTypeRow.style.display = 'none';
    document.all.usesRow.style.display = 'none';
  }
  else {
    document.all.objectTypeRow.style.display = 'inline';    
    document.all.usesRow.style.display = 'inline';    
  }
}

function onFormSubmit() {
  setActionButtonEnabling(false);
  isWarnOnNavigate = false;

<%
if (isAccountChoosable) {
%>
  // Select the account for the list of accounts that is the same as the
  // account that is selected for the form.
  var account = document.all.account.value;
  for (var i = 0; i < document.all.selectedAccount.length; i++) {
    var option = document.all.selectedAccount.options[i];
    if (option.value == account) {
      document.all.selectedAccount.selectedIndex = i;
      break;
    }
  }
<%
}
%>								  
  
  return true;
}
   
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.forms[0];
  f.btnSubmit.disabled = isDisabled;
  f.btnCancel.disabled = isDisabled;
  <% //account is a choice only for System Owners-- Check if GO button exists
if (isAccountChoosable) 
{
%>  
		f.btnGo.disabled = isDisabled;
<%
}
%>
  setInputEnabled(f, "btnDelete", isEnabled);
  setInputEnabled(document.all, "linkEditFormDef", isEnabled);
}
  
function checkEnter(event) {
	var code = 0;
	code = event.keyCode;
	
	if (code == 13) {
		return false;
  }
}

</script>
</head>

<body class="bigr" onLoad="initPage();">
<html:form method="POST" action="<%=action%>" onsubmit="return(onFormSubmit());">
  <html:hidden property="formDefinitionId"/>
  <html:hidden property="confirmedRemove"/>

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
    <br/>
    <br/>

  <div align="center">
    <table border="0" cellspacing="0" cellpadding="0" class="background">
      <%-- begin create/edit input table --%>
      <tr> 
        <td> 
          <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
				    <tr class="white"> 
				    	<% if (isCreate) { %>
	            <td colspan="2" align="center"><b>Create a new form definition</b></td>
				    	<% } else { %>
	            <td colspan="2" align="center">
	            	<b>Edit form definition: <bean:write name="formDefinitionForm" property="name" /></b>
	            </td>
				    	<% } %>
				    </tr>
				    <tr class="white"> 
			  	    <td colspan="2" align="left">XML form definition:<br>
			  	  	  <html:textarea property="formDefinitionXml" rows="20" cols="80">
			  		    	<bigr:beanWrite property="formDefinitionXml" filter="true" whitespace="true"/>
				    	  </html:textarea>
				      </td>
				    </tr>
			  	  <tr class="white"> 
			    	  <td align="left">Form type: </td>
			      	<td align="left">
				    	<% if (isCreate) { %>
			  	    	<html:radio property="formType" value="<%=FormDefinitionTypes.DATA%>" onclick="onClickFormType();"/><%=ApiFunctions.capitalize(FormDefinitionTypes.DATA)%>
			  	    	<html:radio property="formType" value="<%=FormDefinitionTypes.QUERY%>" onclick="onClickFormType();"/><%=ApiFunctions.capitalize(FormDefinitionTypes.QUERY)%>
				    	<% } else { %>
			  		    	<%=ApiFunctions.capitalize(myForm.getFormType())%>
			  	    	<html:hidden property="formType"/>
				    	<% } %>
				      </td>
			  	  </tr>
			  	  <tr id="objectTypeRow" class="white" <%=isQueryForm ? "style=display:none;" : ""%>> 
			    	  <td align="left">Object type: </td>
			      	<td align="left">
								<bigr:selectList
								  name="formDefinitionForm"
								  property="objectType"
								  legalValueSetProperty="objectTypeList"
								  firstValue="" firstDisplayValue="Select an object type"/>
				      </td>
			  	  </tr>
			  	  <tr id="usesRow" class="white" <%=isQueryForm ? "style=display:none;" : ""%>> 
			    	  <td align="left">Form Use: </td>
			      	<td align="left">
								<bigr:selectList
								  name="formDefinitionForm"
								  property="uses"
								  legalValueSetProperty="usesList"
								  firstValue="" firstDisplayValue="Select a use"/>
				      </td>
			  	  </tr>
				    <tr class="white"> 
			  	    <td align="left">Account: </td>
			    	  <td align="left">
				  	  <% //show account as a choice only for System Owners 
				  	  	if (isAccountChoosable) {
							%>
								<bigr:selectList
								  name="formDefinitionForm"
							  	property="account"
								  legalValueSetProperty="accountList"
								  firstValue="" firstDisplayValue="Select an account"/>
					    <% } else { %>
				      	<html:hidden name="formDefinitionForm" property="account" value="<%=defaultAccount%>"/>
								<%=defaultAccountDisplay%>
					    <% } %>								  
						  </td>
				    </tr>
				    <tr class="white"> 
			  	    <td colspan="2" align="left">
			  	    	<html:radio property="enabled" value="true"/>Enabled
			  	    	<html:radio property="enabled" value="false"/>Disabled
			  	    </td>
				    </tr>
				    <tr class="white"> 
				      <td colspan="2" align="center"> 
				        <input type="submit" name="btnSubmit" value="Submit">
				        <input type="button" name="btnCancel" value="Cancel" onclick="onClickCancel();">
				      </td>
				    </tr>
		      </table>
        </td>
      </tr>
    </table> <%-- end create/edit input table --%>
    <br/>
	  <div> <%-- begin form definition list --%>
	    <table border="0" cellspacing="0" cellpadding="0" class="background">
  	    <%-- begin form definition list table --%>
	      <tr> 
	        <td> 
	          <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
			        <tr class="white">
		      		  <td class="grey" colspan="5">
		      		  	Existing Form Definitions in Account:
		      				<%
		      		  		if (isAccountChoosable) {
									%>
										<bigr:selectList
										  name="formDefinitionForm"
									  	property="selectedAccount"
										  legalValueSetProperty="accountList"
										  firstValue="" firstDisplayValue="Select an account"/>
										&nbsp;<input type="button" name="btnGo" value="Go" onclick="onClickGo();"/>
							    <% } else { %>
						      	<html:hidden name="formDefinitionForm" property="selectedAccount" value="<%=defaultAccount%>"/>
										<%=defaultAccountDisplay%>
							    <% } %>								  
			          </td>
		    	    </tr>
		    	    <% if (!ApiFunctions.isEmpty(myForm.getSelectedAccount()) && (myForm.getFormDefinitions().length == 0)) { %>
			        <tr class="white">
		      		  <td colspan="5">
		      		  	No form definitions found in account
			          </td>
		    	    </tr>
		    	    <% } else { %>
	            <tr class="white">
	              <td nowrap><b>Name</b></td>
	              <td nowrap><b>Enabled</b></td>
	              <td nowrap><b>Type</b></td>
	              <td nowrap align="center"><b>ICP</b></td>
	              <td nowrap align="center"><b>Delete</b></td>
	            </tr>
	            <logic:iterate id="formDef" name="formDefinitionForm" property="formDefinitions" type="com.ardais.bigr.kc.form.def.BigrFormDefinition">
  	          <tr class="white">
    	          <td>
      	          <a id="linkEditFormDef"
        	           href='<html:rewrite page="/kc/formdef/edit.do"/>?formDefinitionId=<bean:write name="formDef" property="formDefinitionId"/>'
          	         onclick="onClickEdit();">
            	      <bean:write name="formDef" property="name"/>
              	  </a>
	              </td>
  	            <td>
    	          	<%=(formDef.isEnabled() ? "Yes" : "No")%>
      	        </td>
	              <td>
<%
String formType = formDef.getFormType();
if (formType.equals(FormDefinitionTypes.DATA)) {
%>	              
<%=ApiFunctions.capitalize(formType)%> (<%=formDef.getUses()%>): <%=ApiFunctions.capitalize(formDef.getObjectType())%>
<%
}
else {
%>	              
<%=ApiFunctions.capitalize(formType)%>
<%
}
%>	              
	              </td>
	              <td align="center">
									<bigr:icpLink linkText="ICP" popup="true">
									  <%=formDef.getFormDefinitionId()%>
									</bigr:icpLink>
	              </td>
	              <td align="center">
									<button type="button" name="btnDelete" value="Delete"
    	                style="height: 16px; width: 16px; cursor: hand;"
      	              onclick="onClickDelete('<bean:write name="formDef" property="formDefinitionId"/>');">
	                  <html:img page="/images/delete.gif" alt="Delete" border="0"/>
  	              </button>
        	      </td>
          	  </tr>
            	</logic:iterate>
		    	    <% } %>
	          </table>
	        </td>
	      </tr>
    	</table> <%-- end form definition list table --%>
	  </div> <%-- end form definition list --%>
  </div> <%-- end centered div --%>
</html:form>
</body>
</html>
