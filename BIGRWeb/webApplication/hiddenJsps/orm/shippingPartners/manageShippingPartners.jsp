<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.javabeans.ShippingPartnerDto"%>
<%@ page import="com.ardais.bigr.iltds.helpers.FormLogic"%>
<%@ page import="com.ardais.bigr.api.Escaper"%>
<%@ page import="java.util.List"%>
<%@ taglib uri="/tld/struts-html"  prefix="html"%>
<%@ taglib uri="/tld/struts-logic" prefix="logic"%>
<%@ taglib uri="/tld/bigr"         prefix="bigr"%>
<%@ taglib uri="/tld/struts-bean"  prefix="bean"%>

<%
    com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>

<bean:define id="manageForm" name="manageShippingPartnersForm" type="com.ardais.bigr.orm.web.form.ManageShippingPartnersForm"/>

<html:html>
<head>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META http-equiv="Content-Style-Type" content="text/css">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<script type="text/javascript" src="<html:rewrite page='/js/overlib.js'/>"></script> 
<title>Manage Account Shipping Partners</title>

<script language="javascript">

function initPage() {
  var myBanner = 'Manage Shipping Partners for <%= Escaper.jsEscapeInScriptTag(manageForm.getAccountInformation()) %>';
  if (parent.topFrame && parent.topFrame.document.all.banner) {
    parent.topFrame.document.all.banner.innerText = myBanner;
  }
  eAvailable = document.all.unselectedShippingPartners;
  eSelected = document.all.selectedShippingPartners;
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
  document.all.btnCancel.disabled = true;
}

function onSubmitClick() {
  disableActionButtons();
  //select all the entries in the selected dropdown so they are submitted
  var len = eSelected.length;
  for (var i=0; i<len; i++) {
    eSelected[i].selected = true;
  }
  document.forms[0].submit();
}

function onResetClick() {
  disableActionButtons();
  document.forms[0].action="<html:rewrite page='/orm/shippingPartners/manageShippingPartnersStart.do'/>";
  document.forms[0].submit();
}

</script>
</head>
<body class="bigr" onload="initPage();">
<bigr:overlibDiv/>
<div align="center">

<html:form method="post" action="/orm/shippingPartners/manageShippingPartners.do">

<html:hidden property="accountId"/>

<table width="750px" cellpadding="2" cellspacing="0" border="0">

<tr><td colspan="3" align="center">
	<font color="#FF0000"><html:errors/></font>
</td></tr>

<tr>
  <td width="40%" align="center" valign="top"><b>Available Shipping Partners</b><br>Note: Only accounts with location/shipping information are displayed</td>
  <td width="20%"></td>
  <td width="40%" align="center" valign="top"><b>Assigned Shipping Partners</b></td>
</tr>
<tr>
  <td width="40%" valign="top">
  <select multiple name="unselectedShippingPartners" style="width:100%;height:325px;" onchange="onChangeAvailable();">
  <%
    List unselectedShippingPartners = manageForm.getAvailableShippingPartners();
    for (int i = 0; i < unselectedShippingPartners.size(); i++) {
      ShippingPartnerDto spdto = (ShippingPartnerDto)unselectedShippingPartners.get(i);
      StringBuffer tooltip = new StringBuffer(50);
      tooltip.append("Shipping Partner name: ");
      tooltip.append(Escaper.htmlEscape(spdto.getShippingPartnerName()));
  %>
    <option value="<%=spdto.getShippingPartnerId()%>"
			tooltip="<%=Escaper.jsEscapeInXMLAttr(tooltip.toString())%>">
			<%=Escaper.htmlEscape(spdto.getShippingPartnerName())%>
	</option>
  <% 
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
  <select multiple name="selectedShippingPartners" style="width:100%;height:325px;" onchange="onChangeSelected();">
  <%
    List selectedShippingPartners = manageForm.getAssignedShippingPartners();
    for (int i = 0; i < selectedShippingPartners.size(); i++) {
      ShippingPartnerDto spdto = (ShippingPartnerDto)selectedShippingPartners.get(i);
      StringBuffer tooltip = new StringBuffer(50);
      tooltip.append("Shipping Partner name: ");
      tooltip.append(Escaper.htmlEscape(spdto.getShippingPartnerName()));
  %>
    <option value="<%=spdto.getShippingPartnerId()%>"
			tooltip="<%=Escaper.jsEscapeInXMLAttr(tooltip.toString())%>">
			<%=Escaper.htmlEscape(spdto.getShippingPartnerName())%>
	</option>
  <% 
	}
  %>
  </select>
  </td>
</tr>
<tr><td colspan="3">&nbsp;</td></tr>
<tr>
  <td colspan="3" align="left" valign="bottom">Shipping Partner description <small>(last clicked)</small></td>
</tr>
<tr><td colspan="3"> 
  <small>
  <div style="position: relative; overflow: auto; width:100%; height: 70; border: 2px solid #336699;" id="tooltip"> </div>
  </small>
</td></tr>
<tr><td colspan="3">&nbsp;</td></tr>
<tr>
  <td align="center" colspan="3"> 	    
    <html:button property="btnSubmit" value="Submit" onclick="onSubmitClick();"/>
    <html:button property="btnReset" value="Reset" onclick="onResetClick();"/>
    <html:button property="btnCancel" value="Return to Account Profile" onclick="window.close();"/>
  </td>
</tr>

</table>

</html:form>

</body>
</html:html>
