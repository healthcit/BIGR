<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.List" %>
<%@ page import="com.ardais.bigr.iltds.helpers.FormLogic" %>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.iltds.assistants.LogicalRepository" %>
<%@ page import="com.ardais.bigr.util.Constants" %>
<%@ page import="com.ardais.bigr.api.ApiProperties"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<html>
<head>
<title>Maintain Inventory Groups</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<html:rewrite page="/css/bigr.css"/>" type="text/css">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script type="text/javascript">
var myBanner = 'Maintain Inventory Groups';
var isWarnOnNavigate = true;

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
  
  document.forms[0].repositoryFullName.focus();
}

function confirmNavigate() {
  if (needNavagationWarning()) {
    event.returnValue = "You have made changes that you have not submitted yet.  Are you sure that you want to continue?";
  }
}

function needNavagationWarning() {
  if (! isWarnOnNavigate) return false;
  
  var f = document.forms[0];
  if (f.repositoryFullName.value != f.repositoryFullName.defaultValue) return true;
  if (f.repositoryShortName.value != f.repositoryShortName.defaultValue) return true;
  var bmsRadio = getFirstWithName(f.elements, "bms");
  if (getControlValue(bmsRadio) != getControlDefaultValue(bmsRadio)) return true;
  
  return false;
}

function onClickCancel() {
  setActionButtonEnabling(false);
  isWarnOnNavigate = false;
  window.location = '<html:rewrite page="/orm/logicalRepository/maintainLogicalReposStart.do?resetForm=true"/>';
}

function onClickDelete(reposId) {
  setActionButtonEnabling(false);
  var f = document.forms[0];
  var cancelAction = false;
  var savedOperation = f.operation.value;
  var savedRepositoryId = f.repositoryId.value;
  f.operation.value = '<%= Constants.OPERATION_DELETE %>';
  f.repositoryId.value = reposId;

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
    f.repositoryId.value = savedRepositoryId;
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
   
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.forms[0];
  f.btnSubmit.disabled = isDisabled;
  f.btnCancel.disabled = isDisabled;
  setInputEnabled(f, "btnDelete", isEnabled);
  setInputEnabled(document.all, "linkEditRepository", isEnabled);
}
</script>
</head>
<body class="bigr" onload="initPage();" onbeforeunload="confirmNavigate();">
<html:form method="POST"
      action="/orm/logicalRepository/maintainLogicalReposSave"
      onsubmit="return(onFormSubmit());">
  <html:hidden property="operation"/>
  <html:hidden property="repositoryId"/>
  <div align="center">
    <table border="0" cellspacing="0" cellpadding="0" class="background">
      <%-- begin create/edit input table --%>
      <tr> 
        <td> 
          <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
	        <% if (request.getAttribute("org.apache.struts.action.ERROR") != null) { %>
	        <tr class="yellow">
	          <td colspan="2">
                <font color="#FF0000">
	              <b><html:errors/></b>
	            </font>
	          </td>
	        </tr>
	        <% } %>
	        <logic:present name="com.ardais.BTX_ACTION_MESSAGES">
            <tr class="yellow"> 
              <td colspan="2" align="center"> 
                <font color="#FF0000"><b><bigr:btxActionMessages/></b></font>
 		      </td>
		    </tr>
		    </logic:present>
            <tr class="white"> 
<logic:equal name="maintainLogicalRepositoryForm" property="operation"
   value="<%= Constants.OPERATION_CREATE %>">
              <td colspan="2" align="center"><b>Create a new inventory group</b></td>
</logic:equal>
<logic:equal name="maintainLogicalRepositoryForm" property="operation"
   value="<%= Constants.OPERATION_UPDATE %>">
              <td colspan="2" align="center"><b>
                Edit inventory group:
                <bean:write name="maintainLogicalRepositoryForm" property="repositoryFullName" />
              </b></td>
</logic:equal>
            </tr>
            <tr class="white"> 
              <td class="grey" nowrap>Full name</td>
              <td> 
                <html:text property="repositoryFullName"
                  size="55" maxlength="50"/>
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey" nowrap>Short name</td>
              <td> 
                <html:text property="repositoryShortName"
                  size="35" maxlength="30"/>
              </td>
            </tr>
<% if (ApiProperties.isEnabledLegacy()) { %>            
            <tr class="white"> 
              <td class="grey" nowrap>Is this a BMS group?</td>
              <td> 
                <html:radio property="bms" value="true">Yes</html:radio>
                <html:radio property="bms" value="false">No</html:radio>
<logic:equal name="maintainLogicalRepositoryForm" property="operation"
   value="<%= Constants.OPERATION_UPDATE %>">
                <br/>Changing this value for an existing group updates every
                item in the group to correctly reflect whether or not it is
                a BMS item.  If there are many items in the group this can
                take some time, so please be patient.
</logic:equal>
              </td>
            </tr>
<% } else { %>   
						<html:hidden property="bms" value="true"/>         
<% } %>				
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
    <div> <%-- begin repostory list --%>
    <table border="0" cellspacing="0" cellpadding="0" class="background">
      <%-- begin repostory list table --%>
      <tr> 
        <td> 
          <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
	        <tr class="white">
      		  <td class="grey" colspan="5">
          		To edit an inventory group, click on its Full Name.  To delete
		        an inventory group, click on its Delete button.  The group will
		        be deleted as soon as you click on the button.  You will only
		        be able to delete empty groups.
	          </td>
    	    </tr>
            <tr class="white">
              <td nowrap><b>Full Name</b></td>
              <td nowrap><b>Short Name</b></td>
<% if (ApiProperties.isEnabledLegacy()) { %>                 
              <td nowrap align="center"><b>BMS?</b></td>
<% } %>
              <td nowrap align="center"><b>ICP</b></td>
              <td nowrap align="center"><b>Delete</b></td>
            </tr>
<logic:iterate id="repository"
  name="maintainLogicalRepositoryForm" property="logicalRepositories"
  type="com.ardais.bigr.iltds.assistants.LogicalRepository">
            <tr class="white">
              <td>
                <a id="linkEditRepository"
                   href="<html:rewrite page="/orm/logicalRepository/maintainLogicalReposStart.do?operation=Update"/>&repositoryId=<bean:write name="repository" property="id"/>"
                   onclick="return(isLinkEnabled());">
                  <bean:write name="repository" property="fullName"/>
                </a>
              </td>
              <td><bean:write name="repository" property="shortName"/></td>
<% if (ApiProperties.isEnabledLegacy()) { %>                
              <td align="center">
                <logic:equal name="repository" property="bmsYN" value="Y">Y</logic:equal>
              </td>
<% } %>
              <td align="center">
				<bigr:icpLink linkText="ICP" popup="true">
				  <%= FormLogic.makePrefixedLogicalRepositoryId(repository.getId()) %>
				</bigr:icpLink>
              </td>
              <td align="center">
				<button type="button" name="btnDelete" value="Delete"
                    style="height: 16px; width: 16px;"
                    onclick="onClickDelete('<bean:write name="repository" property="id"/>');">
                  <html:img page="/images/delete.gif" alt="Delete" border="0"/>
                </button>
              </td>
            </tr>
</logic:iterate>
          </table>
        </td>
      </tr>
    </table> <%-- end repostory list table --%>
    </div> <%-- end repostory list --%>
  </div> <%-- end centered div --%>
</html:form>
</body>
</html>
