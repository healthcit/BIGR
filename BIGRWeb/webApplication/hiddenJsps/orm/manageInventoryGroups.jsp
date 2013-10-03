<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.iltds.assistants.LogicalRepository" %>
<%@ page import="com.ardais.bigr.iltds.helpers.FormLogic" %>
<%@ page import="com.ardais.bigr.api.Escaper" %>
<%@ page import = "com.ardais.bigr.util.Constants"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>

<%
    com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>

<bean:define id="manageForm" name="manageInventoryGroupsForm" type="com.ardais.bigr.orm.web.form.ManageInventoryGroupsForm"/>

<html:html>
<head>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META http-equiv="Content-Style-Type" content="text/css">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<script type="text/javascript" src="<html:rewrite page='/js/overlib.js'/>"><!-- overLIB (c) Erik Bosrup --></script> 
<title>Manage Inventory Groups for <%= Escaper.jsEscapeInScriptTag(manageForm.getObjectInformation()) %></title>

<script language="javascript">

function initPage() {
  var myBanner = 'Manage Inventory Groups for <%= Escaper.jsEscapeInScriptTag(manageForm.getObjectInformation()) %>';
  if (parent.topFrame && parent.topFrame.document.all.banner) {
    parent.topFrame.document.all.banner.innerText = myBanner;
  }
  eAvailable = document.all.availableInventoryGroups;
  eSelected = document.all.selectedInventoryGroups;
  eTTDiv = document.all.tooltip;
}

  var oldSelections = new Array();
  var lastSelection = -1;
  var oldAvailable = new Array();
  var lastAvailable = -1;

// ----    add/remove ---------
function addSel() {
	var len = eAvailable.length;
	for (var i=0; i<len; i++) {
		if (eAvailable[i].selected) {
			addIdx(i); 
			i--; // decr i because next elem shifted down to current
			len--; // decr len because list is now smaller
		}
	}
	resetSelectionTooltip();  // selection may be in other list
	resetAvailableTooltip();  // selection may be in other list
}

function addIdx(i) {
	var opt = eAvailable[i];
	eAvailable.remove(i);
	eSelected[eSelected.length] = opt;
	opt.selected = false;
}

function removeSel() {
	var len = eSelected.length;
	for (var i=0; i<len; i++) {
		if (eSelected[i].selected) {
			removeIdx(i); 
			i--; // decr i because next elem shifted down to current
			len--; // decr len because list is now smaller
		}
	}
	resetSelectionTooltip();  // selection may be in other list	
	resetAvailableTooltip();  // selection may be in other list
}

function removeIdx(i) {
	var opt = eSelected[i];
	eSelected.remove(i);
	eAvailable[eAvailable.length] = opt;
	opt.selected = false;
}

// --  last clicked support ---
function onChangeSelected() {
	updateLastSelection();
	if (lastSelection==-1)
		return;	
	var inhtml = eSelected[lastSelection].tooltip;
	eTTDiv.innerHTML = inhtml
}

function onChangeAvailable() {
	updateLastAvailable();
	if (lastAvailable==-1)
		return;
	var inhtml = eAvailable[lastAvailable].tooltip;
	eTTDiv.innerHTML = inhtml
}

function resetSelectionTooltip() {
	oldSelections = new Array();
	lastSelection = -1;
	eTTDiv.innerHTML = '';
}

function resetAvailableTooltip() {
	oldAvailable = new Array();
	lastAvailable = -1;
	eTTDiv.innerHTML = '';
}

function updateLastSelection() {
	// do a diff between old and new selections
	var len = eSelected.length;
	for (var i=0; i<len; i++) {
		if (eSelected[i].selected) {
			if( !oldSelections[i]) {
				lastSelection=i;
			}
		}
	}
	// now set oldSelections to current
	for (var i=0; i<len; i++) {
		if (eSelected[i].selected)
			oldSelections[i]=true;
		else
			oldSelections[i]=false;
	}
}

function updateLastAvailable() {
	// do a diff between old and new selections
	var len = eAvailable.length;
	for (var i=0; i<len; i++) {
		if (eAvailable[i].selected) {
			if( !oldAvailable[i]) {
				lastAvailable=i;
			}
		}
	}
	// now set oldSelections to current
	for (var i=0; i<len; i++) {
		if (eAvailable[i].selected)
			oldAvailable[i]=true;
		else
			oldAvailable[i]=false;
	}
}

function disableActionButtons() {
	document.all.btnRemove.disabled = true;
	document.all.btnAdd.disabled = true;
	document.all.btnSubmit.disabled = true;
	document.all.btnReset.disabled = true;
	document.all.btnReturn.disabled = true;
}

function onSubmit() {
	disableActionButtons();
	//select all the entries in the selected dropdown so they are submitted
	var len = eSelected.length;
	for (var i=0; i<len; i++) {
		eSelected[i].selected = true;
	}
	return true;
}

function onResetClick() {
  disableActionButtons();
  document.forms[0].action="<html:rewrite page='/orm/manageInventoryGroupsStart.do'/>";
  document.forms[0].submit();
}

function onCancel() {
	disableActionButtons();
  <%
    String url = null;
    if (Constants.OBJECT_TYPE_ACCOUNT.equalsIgnoreCase(manageForm.getObjectType())) {
      url = "/orm/accounts/modifyAccountStart.do?accountData.id=" + manageForm.getAccountData().getId();
    }
    else {
      url = "/orm/users/modifyUserStart.do";
    }
  %>
  document.forms[0].action="<html:rewrite page='<%=url%>'/>";
  document.forms[0].submit();
}

</script>
</head>
<body class="bigr" onload="initPage();">
<bigr:overlibDiv/>
<div align="center">

<html:form method="post" action="/orm/manageInventoryGroups.do" onsubmit="return(onSubmit());">
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
<html:hidden property="objectType"/>
<html:hidden property="userData.userId"/>
<html:hidden property="userData.accountId"/>
<html:hidden property="userData.firstName"/>
<html:hidden property="userData.lastName"/>
<html:hidden property="accountData.id"/>
<html:hidden property="accountData.name"/>

<table width="750px" cellpadding="2" cellspacing="0" border="0">
<tr>
	<td width="40%" align="center" valign="top"><b>Available Inventory Groups</b></td>
	<td width="20%"></td>
	<td width="40%" align="center" valign="top"><b>Assigned Inventory Groups</b></td>
</tr>
<tr>
	<td width="40%" valign="top">
	<select multiple name="availableInventoryGroups" style="width:100%;height:325px;" onchange="onChangeAvailable();">
	<%
		List assignedInventoryGroups = manageForm.getAssignedInventoryGroups();
		List allInventoryGroups = manageForm.getAllInventoryGroups();
		Iterator allIterator = allInventoryGroups.iterator();
		while (allIterator.hasNext()) {
			LogicalRepository allRepository = (LogicalRepository) allIterator.next();
			Iterator assignedIterator = assignedInventoryGroups.iterator();
			boolean found = false;
			while (assignedIterator.hasNext() && !found) {
				LogicalRepository assignedRepository = (LogicalRepository) assignedIterator.next();
				if (assignedRepository.getId().equals(allRepository.getId())) {
					found = true;
				}
			}
			if (!found) {
				StringBuffer tooltip = new StringBuffer(50);
				tooltip.append("Full name: ");
				tooltip.append(Escaper.htmlEscape(allRepository.getFullName()));
				tooltip.append("<br>Short name: ");
				tooltip.append(Escaper.htmlEscape(allRepository.getShortName()));
				tooltip.append("<br>BMS: ");
				if (FormLogic.DB_YES.equalsIgnoreCase(allRepository.getBmsYN())) {
					tooltip.append(FormLogic.DB_YES_TEXT);
				}
				else {
					tooltip.append(FormLogic.DB_NO_TEXT);
				}
	%>
				<option 
					value="<%=allRepository.getId()%>"
					tooltip="<%= Escaper.jsEscapeInXMLAttr(tooltip.toString()) %>">
						<%=Escaper.htmlEscape(allRepository.getFullName())%>
				</option>
	<% 
			}
		}
	%>
	</select>
	</td>
	
	<td width="20%" valign="middle" align="center">
	<div>
		<input id="btnAdd" class="buttons" type="button" style="width:85" onclick="addSel();" value="Add &gt;&gt;">
		<p/>
		<input id="btnRemove" class="buttons" type="button" style="width:85" onclick="removeSel();" value="&lt;&lt; Remove">
	</div>
	</td>
	
	<td width="40%" valign="top">
	<select multiple name="selectedInventoryGroups" style="width:100%;height:325px;" onchange="onChangeSelected();">
	<%
		Iterator assignedIterator = assignedInventoryGroups.iterator();
		while (assignedIterator.hasNext()) {
			LogicalRepository assignedInventoryGroup = (LogicalRepository) assignedIterator.next();
			StringBuffer tooltip = new StringBuffer(50);
			tooltip.append("Full name: ");
			tooltip.append(Escaper.htmlEscape(assignedInventoryGroup.getFullName()));
			tooltip.append("<br>Short name: ");
			tooltip.append(Escaper.htmlEscape(assignedInventoryGroup.getShortName()));
			tooltip.append("<br>BMS: ");
			if (FormLogic.DB_YES.equalsIgnoreCase(assignedInventoryGroup.getBmsYN())) {
				tooltip.append(FormLogic.DB_YES_TEXT);
			}
			else {
				tooltip.append(FormLogic.DB_NO_TEXT);
			}	
	%>
			<option 
				value="<%=assignedInventoryGroup.getId()%>"
				tooltip="<%= Escaper.jsEscapeInXMLAttr(tooltip.toString()) %>">
					<%=Escaper.htmlEscape(assignedInventoryGroup.getFullName())%>
			</option>
	<% 
		}
	%>
	</select>
	</td>
</tr>
<tr><td colspan="3">&nbsp;</td></tr>
<tr>
	<td colspan="3" align="left" valign="bottom">Inventory Group description <small>(last clicked)</small></td>
</tr>
<tr><td colspan="3"> 
		<small>
			<div style="position: relative; overflow: auto; width:100%; height: 70; border: 2px solid #336699;" id="tooltip"> </div>
		</small>
	</td>
</tr>
<tr><td colspan="3">&nbsp;</td></tr>
<tr>
	<td align="center" colspan="3"> 	    
		<input type="submit" id="btnSubmit" value="Submit"/>&nbsp;
    <html:button property="btnReset" value="Reset" onclick="onResetClick();"/>
        <%
          String buttonText = "Return to " + manageForm.getObjectType() + " Profile";
        %>
		<input type="button" id="btnReturn" onclick="onCancel();" value="<%=buttonText%>"/> 
	</td>
</tr>

</table>


</html:form>

</body>
</html:html>

