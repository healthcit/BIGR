<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.List" %>
<%@ page import="com.ardais.bigr.iltds.helpers.FormLogic" %>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.util.UrlUtils" %>
<%@ page import="com.ardais.bigr.javabeans.UrlDto" %>
<%@ page import="com.ardais.bigr.util.WebUtils" %> 
<%@ page import="com.ardais.bigr.security.SecurityInfo" %> 
<%@ page import="com.ardais.bigr.util.Constants" %> 
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");

	SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);
	String operation = request.getParameter("operation");
	boolean isSystemOwnerUser = securityInfo.isInRoleSystemOwner();

%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<bean:define id="maintainForm" name="maintainAccountUrlForm" type="com.ardais.bigr.orm.web.form.MaintainAccountUrlForm"/>
<bean:define id="currentOperation" name="maintainForm" property="operation"/>
<html>
<head>
<title>Maintain URLs</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<html:rewrite page="/css/bigr.css"/>" type="text/css">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script type="text/javascript">
var myBanner = 'Maintain URLs';
var isWarnOnNavigate = true;

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
<%
  String retry = request.getParameter("retry");
  if ((currentOperation == null || Constants.OPERATION_CREATE.equals(currentOperation)) && isSystemOwnerUser) {
    if (ApiFunctions.isEmpty(retry)) {
%>
      document.forms[0].accountId.value = "";
<%
    }
  }
%>
  document.forms[0].objectType.focus();
}

function confirmNavigate() {
  if (needNavigationWarning()) {
    event.returnValue = "You have made changes that you have not yet submitted.  Are you sure that you want to continue?";
  }
}

function needNavigationWarning() {
  if (!isWarnOnNavigate) return false;
  
  var f = document.forms[0];
  var objectTypeSelect = getFirstWithName(f.elements, "objectType");
  if (getControlValue(objectTypeSelect) != getControlDefaultValue(objectTypeSelect)) return true;
  if (f.url.value != f.url.defaultValue) return true;
  if (f.label.value != f.label.defaultValue) return true;
  if (f.target.value != f.target.defaultValue) return true;

  return false;
}

function onClickCancel() {
  setActionButtonEnabling(false);
  isWarnOnNavigate = false;
  window.location = '<html:rewrite page="/orm/url/maintainAccountUrlStart.do?resetForm=true"/>';
}

function onClickDelete(urlId) {
  setActionButtonEnabling(false);
  var f = document.forms[0];
  var cancelAction = false;
  var savedOperation = f.operation.value;
  var savedUrlId = f.urlId.value;
  f.operation.value = '<%= Constants.OPERATION_DELETE %>';
  f.urlId.value = urlId;

  if (needNavigationWarning()) {
    if (!confirm("You have made changes that have not been saved.  Are you sure you want to continue?\n\nPress OK to continue, or Cancel to stay on the current page.")) {
      cancelAction = true;
    }
  }
  
  if (!cancelAction && !onFormSubmit()) {
    cancelAction = true;
  }
  
  if (cancelAction) { // restore field values
    f.operation.value = savedOperation;
    f.urlId.value = savedUrlId;
    setActionButtonEnabling(true);
  }
  else {
    isWarnOnNavigate = false;
    f.submit();
  }
}

function onClickOpenHelp() {
  setActionButtonEnabling(false);
  var theURL = '<html:rewrite page="/orm/url/maintainAccountUrlHelp.do"/>';

  var w = window.open(theURL, 'BIGRHelp',
    'scrollbars=yes,resizable=yes,status=yes,width=640,height=600,top=0');
  w.focus();
  
  setActionButtonEnabling(true);
}

function onFormSubmit() {
  setActionButtonEnabling(false);
  isWarnOnNavigate = false;

  return true;
}
   
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (!isEnabled);
  var f = document.forms[0];
  f.btnSubmit.disabled = isDisabled;
  f.btnCancel.disabled = isDisabled;
  f.btnHelp.disabled = isDisabled;
  setInputEnabled(f, "btnDelete", isEnabled);
  setInputEnabled(document.all, "linkEditUrl", isEnabled);
}
</script>
</head>
<body class="bigr" onload="initPage();" onbeforeunload="confirmNavigate();">
<html:form method="POST" action="/orm/url/maintainAccountUrlSave.do" onsubmit="return(onFormSubmit());">
  <html:hidden property="operation"/>
  <html:hidden property="urlId"/>
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
<logic:equal name="maintainAccountUrlForm" property="operation" value="<%= Constants.OPERATION_CREATE %>">
              <td colspan="2" align="center"><b>Create a new URL</b></td>
</logic:equal>
<logic:equal name="maintainAccountUrlForm" property="operation" value="<%= Constants.OPERATION_UPDATE %>">
              <td colspan="2" align="center">
                <b>Edit URL: <%= FormLogic.makePrefixedUrlId(maintainForm.getUrlId()) %>
                </b>
              </td>
</logic:equal>
            </tr>
	      <bigr:isInRole role1="<%=SecurityInfo.ROLE_SYSTEM_OWNER%>">
	        <tr class="white"> 
	          <td class="grey" nowrap>Account</td>
	          <td>
	            <bigr:selectList
	              name="maintainAccountUrlForm" property="accountId"
		          legalValueSetProperty="accountList"
		          firstValue="" firstDisplayValue="Select An Account"
		        />
	          </td>
	        </tr>
	      </bigr:isInRole>
	      <bigr:notIsInRole role1="<%=SecurityInfo.ROLE_SYSTEM_OWNER%>">
	        <html:hidden property="accountId"/>
	      </bigr:notIsInRole>

            <tr class="white"> 
              <td class="grey" nowrap>Object Type</td>
              <td> 
                <bigr:selectList
                  name="maintainAccountUrlForm" property="objectType" 
		          legalValueSetProperty="legalObjectTypes"
		          firstValue="Select Object Type" firstDisplayValue="Select Object Type"
		        />
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey" nowrap>URL</td>
              <td> 
                <html:text property="url" size="75" maxlength="4000"/>
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey" nowrap>Label</td>
              <td> 
                <html:text property="label" size="50" maxlength="100"/>
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey" nowrap>Target</td>
              <td>
                <html:text property="target" size="50" maxlength="50"/>
              </td>
            </tr>
            <tr class="white"> 
              <td colspan="2" align="center"> 
                <input type="submit" name="btnSubmit" value="Submit">
                <input type="button" name="btnCancel" value="Cancel" onclick="onClickCancel();">
                <input type="button" name="btnHelp" value="Help" onclick="onClickOpenHelp();">
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table> <%-- end create/edit input table --%>
    
    <br/>
    <div> <%-- begin url list --%>
    <table border="0" cellspacing="0" cellpadding="0" class="background">
      <%-- begin url list table --%>
      <tr> 
        <td> 
          <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
	        <tr class="white">
      		  <td class="grey" colspan="5">
          		To edit a url, click on its id.  To delete a url,
          		click on its Delete button.  The url will be deleted as soon
          		as you click on the button.
	          </td>
    	    </tr>
            <tr class="white">
              <td nowrap><b>Id</b></td>
              <td nowrap><b>Label</b></td>
			<bigr:isInRole role1="<%=SecurityInfo.ROLE_SYSTEM_OWNER%>">
              <td nowrap><b>Account</b></td>
            </bigr:isInRole>
              <td nowrap align="center"><b>Delete</b></td>
            </tr>
<logic:iterate id="url" name="maintainAccountUrlForm" property="urls" type="com.ardais.bigr.javabeans.UrlDto">
            <tr class="white">
              <td>
                <a id="linkEditUrl"
                   href="<html:rewrite page="/orm/url/maintainAccountUrlStart.do?operation=Update"/>&urlId=<bean:write name="url" property="urlId"/>"
                   onclick="return(isLinkEnabled());">
                   <%= FormLogic.makePrefixedUrlId(url.getUrlId()) %>
                </a>
              </td>
              <td>
              	<bean:write name="url" property="label"/>
              </td>
			<bigr:isInRole role1="<%=SecurityInfo.ROLE_SYSTEM_OWNER%>">
              <td>
              	<bean:write name="url" property="accountId"/> - <bean:write name="url" property="accountName"/>
              </td>
            </bigr:isInRole>
              <td align="center">
				<button type="button" name="btnDelete" value="Delete"
                    style="height: 16px; width: 16px;"
                    onclick="onClickDelete('<bean:write name="url" property="urlId"/>');">
                  <html:img page="/images/delete.gif" alt="Delete" border="0"/>
                </button>
              </td>
            </tr>
</logic:iterate>
          </table>
        </td>
      </tr>
    </table> <%-- end url list table --%>
    </div> <%-- end url list --%>
  </div> <%-- end centered div --%>
</html:form>
</body>
</html>
