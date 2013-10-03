<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.iltds.helpers.FormLogic" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<html>
<head>
<title>Add History Note</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<html:rewrite page="/css/bigr.css"/>" type="text/css">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript" src="<html:rewrite page="/js/BigrLib.js"/>"></script>
<script type="text/javascript">
var myBanner = 'Add History Note';
var isWarnOnNavigate = true;

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
  
  resizeIdListButtons("objectIdsButton");
  deselectAllFromList("objectIds");

  document.forms[0].note.focus();
}

function resizeIdListButtons(idListName) {
  var buttons = document.all[idListName];
  var maxWidth = 0;
  for (var i = 0; i < buttons.length; i++) {
    var width = buttons[i].clientWidth;
	maxWidth = (width > maxWidth) ? width : maxWidth;
  }
  for (var i = 0; i < buttons.length; i++) {
    buttons[i].width = maxWidth;
  }
}

function confirmNavigate() {
  if (needNavagationWarning()) {
    event.returnValue = "You have made changes that you have not submitted yet.  Are you sure that you want to continue?";
  }
}

function needNavagationWarning() {
  if (! isWarnOnNavigate) return false;
  
  var f = document.forms[0];
  if (f.note.value != f.note.defaultValue) return true;
  if (f.objectId.value != f.objectId.defaultValue) return true;

  if (!isEmptyList(f.elements, "objectIds")) {
    return true;
  }

  return false;
}

function isEmptyList(coll, ename) {
  var items = coll[ename];
  var item;

  if (items == null) return null;
  if (0 == items.length) {
    item = items;
  }
  else {
    item = items[0];
  }

  if (ename != item.name) item = item.parentElement;

  if (item.length > 0) {
    return false;
  }
  return true;
}

function onClickCancel() {
  setActionButtonEnabling(false);
  isWarnOnNavigate = false;
  window.location = '<html:rewrite page="/iltds/icp/historyNoteStart.do"/>';
}

function onFormSubmit() {
  setActionButtonEnabling(false);
  isWarnOnNavigate = false;

  if(!selectAllIds()) {return;}

  return true;
}
   
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.forms[0];
  f.btnSubmit.disabled = isDisabled;
  f.btnCancel.disabled = isDisabled;
}

function selectAllIds() {
  if (!addItemToList("objectId", "objectIds", IGNORE_TYPE, true)) {return false;}
  selectAllFromList("objectIds");
  return true;
}

</script>
</head>
<body class="bigr" onload="initPage();" onbeforeunload="confirmNavigate();">
<html:form method="POST" action="/iltds/icp/addHistoryNote" onsubmit="return(onFormSubmit());">
  <table border="0" cellspacing="0" cellpadding="0" align="center">
    <%-- begin create/edit input table --%>
    <tr> 
      <td> 
        <table width="100%" border="1" cellspacing="1" cellpadding="3" class="foreground">
          <col width="33%">
          <col width="34%">
          <col width="33%">
          <tbody>
          <logic:present name="org.apache.struts.action.ERROR">
          <tr class="yellow">
	        <td colspan="3"><font color="#FF0000"><b><html:errors/></b></font></td>
          </tr>
          </logic:present>
          <logic:present name="com.ardais.BTX_ACTION_MESSAGES">
          <tr class="yellow"> 
            <td colspan="3" align="center"><font color="#000000"><b><bigr:btxActionMessages/></b></font></td>
	      </tr>
		  </logic:present>

          <tr class="white"> 
            <td colspan="3" align="center"><b>Add History Note</b></td>
          </tr>
		  <tr>
		    <td colspan="3" align="center">
		      <html:textarea property="note" title="Note" cols="80" rows="10"/>
		    </td>
		  </tr>
		  <tr class="white">
		    <td align="center" valign="center" style="padding: 1em 0 1em 0;">
		      <span><bean:message key="iltds.icp.historyNote.objectId.label"/></span><br>
		      <input type="text" name="objectId" size="20" maxlength="30"/><br>
		    </td>
		    <td align="center" valign="center" style="padding: 1em;">
		      <bigr:idlist property="objectIdsButton" type="IGNORE_TYPE" idfrom="objectId" idlist="objectIds" style="FONT-SIZE:xx-small"/>
		    </td>
		    <td align="center" valign="center" style="padding: 1em 0 1em 0;">
		      <span><bean:message key="iltds.icp.historyNote.objectIdList.label"/></span><br>
		      <html:select property="objectIds" size="4" multiple="true" style="width: 175px">
		        <logic:present name="historyNoteForm" property="objectIds">
		          <html:options property="objectIds"/>
		        </logic:present>
		      </html:select>
		    </td>
		  </tr>
          <tr class="white"> 
            <td colspan="3" align="center"> 
              <html:submit property="btnSubmit" value="Submit"/>
              <html:cancel property="btnCancel" value="Cancel" onclick="onClickCancel();"/>
            </td>
          </tr>
          </tbody>
        </table>
      </td>
    </tr>
  </table>
</html:form>
</body>
</html>
