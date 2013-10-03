<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions"%>
<%@ page import="com.ardais.bigr.api.ApiException"%>
<%@ page import="com.ardais.bigr.api.Escaper"%>
<%@ page import="com.ardais.bigr.iltds.assistants.LegalValue"%>
<%@ page import="java.util.Iterator"%>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<bean:define name="maintainAccountForm" id="myForm" type="com.ardais.bigr.orm.web.form.MaintainAccountForm"/>

<%
	String action = request.getParameter("action");
  if (ApiFunctions.isEmpty(action)) {
    throw new ApiException("No action defined");
  }
  else {
    if ("create".equalsIgnoreCase(action)) {
      action = "Create";
    }
    else if ("modify".equalsIgnoreCase(action)) {
      action = "Modify";
    }
    else {
      throw new ApiException("Unrecognized action (" + action + ") defined");
    }
  }
%>

<html>
<head>
<title><%=action %> Location and Storage Units</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<html:rewrite page="/css/bigr.css"/>" type="text/css">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="javascript">
<!--

function initPage() {
  if (parent.topFrame) {
    parent.topFrame.document.all.banner.innerHTML = '<%=action%> Location and Storage Units for ' + '<%= Escaper.jsEscapeInScriptTag(myForm.getAccountData().getId()) %>';
  }
}
  
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.forms[0];
  f.btnAddNewStorageUnit.disabled = isDisabled
  f.btnSubmit.disabled = isDisabled;
  f.btnReset.disabled = isDisabled;
  f.btnCancel.disabled = isDisabled;
  if (isControlCollection("btnRemoveNewStorageUnit")) {
    disableControlCollection("btnRemoveNewStorageUnit", isDisabled);
  }
  else {
    if (f.btnRemoveNewStorageUnit) {
      f.btnRemoveNewStorageUnit.disabled = isDisabled;
    }
  }
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

function onResetClick() {
  setActionButtonEnabling(false);
  <%
    String resetUrl = "/orm/accounts/manageAccountLocations.do?accountData.id=" + myForm.getAccountData().getId();
  %>
  document.forms[0].action="<html:rewrite page='<%=resetUrl%>'/>";
  document.forms[0].submit();
}

function onCancelClick() {
  setActionButtonEnabling(false);
  <%
    String cancelUrl = "/orm/accounts/modifyAccountStart.do?accountData.id=" + myForm.getAccountData().getId();
  %>
  document.forms[0].action="<html:rewrite page='<%=cancelUrl%>'/>";
  document.forms[0].submit();
}

function areRequiredFieldsPopulated() {
  var returnValue = false;
  returnValue = ((document.forms[0]["accountData.location.addressId"].value != "")
        &&(document.forms[0]["accountData.location.name"].value != "")
	       )
  var theTable = document.getElementById('newStorageUnitTable');
  for (var i=0; i < theTable.tBodies[0].rows.length; i++) {
    var index = theTable.tBodies[0].rows[i].id;
    returnValue = returnValue && (document.forms[0]["accountData.location.newStorageUnit[" + index + "].storageTypeCui"].value != "");
    returnValue = returnValue && (document.forms[0]["accountData.location.newStorageUnit[" + index + "].roomId"].value != "");
    returnValue = returnValue && (document.forms[0]["accountData.location.newStorageUnit[" + index + "].unitName"].value != "");
    returnValue = returnValue && (document.forms[0]["accountData.location.newStorageUnit[" + index + "].numberOfDrawers"].value != "");
    returnValue = returnValue && (document.forms[0]["accountData.location.newStorageUnit[" + index + "].slotsPerDrawer"].value != "");
  }
	return returnValue;
}

function dataIsValid() {
  if (!areRequiredFieldsPopulated()) {
	  alert("Please enter values for all fields marked with a RED *");
	  return false;
  }
  return true;
}

var nextNewUnitId;

function onAddNewStorageUnitClick() {
  var theTable = document.getElementById('newStorageUnitTable');
  var newRow = document.createElement('tr');
  newRow.style.cssText = "background-color:#FFFFFF;";
  newRow.id = nextNewUnitId;
  var newCell = document.createElement('td');
  newRow.appendChild(newCell);
  <%
    StringBuffer selectChoices = new StringBuffer(200);
    Iterator iterator = myForm.getStorageUnitTypeChoices().getIterator();
    while (iterator.hasNext()) {
      LegalValue legalValue = (LegalValue)iterator.next();
      selectChoices.append("<option value=\"");
      selectChoices.append(legalValue.getValue());
      selectChoices.append("\">");
      selectChoices.append(legalValue.getDisplayValue());
      selectChoices.append("</option>");
    }
  %>
  newCell.innerHTML = '<select name="<%="accountData.location.newStorageUnit["%>' + nextNewUnitId + '<%="].storageTypeCui"%>"><%=selectChoices%></select>';
  newCell = document.createElement('td');
  newRow.appendChild(newCell);
  newCell.innerHTML = '<input type="text" name="<%="accountData.location.newStorageUnit["%>' + nextNewUnitId + '<%="].roomId"%>" maxlength="22" value="" size="22">';
  newCell = document.createElement('td');
  newRow.appendChild(newCell);
  newCell.innerHTML = '<input type="text" name="<%="accountData.location.newStorageUnit["%>' + nextNewUnitId + '<%="].unitName"%>" maxlength="30" value="" size="30">';
  newCell = document.createElement('td');
  newRow.appendChild(newCell);
  newCell.innerHTML = '<input type="text" name="<%="accountData.location.newStorageUnit["%>' + nextNewUnitId + '<%="].numberOfDrawers"%>" size="3" maxlength="3" value="">';
  newCell = document.createElement('td');
  newRow.appendChild(newCell);
  <%
  selectChoices = new StringBuffer(200);
  iterator = myForm.getSlotPerDrawerChoices().getIterator();
  while (iterator.hasNext()) {
    LegalValue legalValue = (LegalValue)iterator.next();
    selectChoices.append("<option value=\"");
    selectChoices.append(legalValue.getValue());
    selectChoices.append("\"");
    selectChoices.append(">");
    selectChoices.append(legalValue.getDisplayValue());
    selectChoices.append("</option>");
  }
%>
  newCell.innerHTML = '<select name="<%="accountData.location.newStorageUnit["%>' + nextNewUnitId + '<%="].slotsPerDrawer"%>"><%=selectChoices%></select>';
  newCell = document.createElement('td');
  newRow.appendChild(newCell);
  newCell.innerHTML = '<input type="button" name="btnRemoveNewStorageUnit" value="Remove" onClick="onRemoveNewStorageUnitClick(\'' + nextNewUnitId + '\');">';
  nextNewUnitId = nextNewUnitId + 1;

  <%-- To resolve MR 8782 (IE5.5 crashes), we don't add the new row to the table
       until it is fully assembled. --%>
  theTable.tBodies[0].appendChild(newRow);
}

function onRemoveNewStorageUnitClick(rowId) {
  var rowToRemove;
  var theTable = document.getElementById('newStorageUnitTable');
  for (var i=0; i < theTable.tBodies[0].rows.length; i++) {
    if (theTable.tBodies[0].rows[i].id == rowId) {
      rowToRemove = theTable.tBodies[0].rows[i];
    }
  }
  theTable.tBodies[0].removeChild(rowToRemove);
}
-->
</script>
</head>
<body class="bigr" onload="initPage();">
<%
	String formAction = "/orm/accounts/" + action.toLowerCase() + "AccountLocation.do";
%>
<html:form method="post" action="<%=formAction%>" onsubmit="return(onFormSubmit());">
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
  <html:hidden name="maintainAccountForm" property="accountData.id"/>
  <table border="0" cellpadding="0" cellspacing="0" align="center" class="background">
  <tr> 
    <td>
      <table border="0" align="center" cellspacing="1" cellpadding="3" class="foreground">
        <tr class="yellow"> 
          <td colspan="2"> 
            <div align="center"><b>Location Information</b></div>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>Location Id <font color="Red">*</font></b></div>
          </td>
          <td>
            <%  
              if ("Create".equalsIgnoreCase(action)) {
            %>
              <html:text name="maintainAccountForm" property="accountData.location.addressId" size="12" maxlength="12" onchange="this.value=trim(this.value);"/>
            <%
              } else {
            %>
              <bean:write name="maintainAccountForm" property="accountData.location.addressId"/>
              <html:hidden name="maintainAccountForm" property="accountData.location.addressId"/>
            <%
              }
						%>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>Location Name <font color="Red">*</font></b></div>
          </td>
          <td>
            <html:text name="maintainAccountForm" property="accountData.location.name" size="50" maxlength="50" onchange="this.value=trim(this.value);"/>
          </td>
        </tr>
        <tr class="yellow"> 
          <td colspan="2"> 
            <div align="center"><b>Contact Information</b></div>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>Address 1</b></div>
          </td>
          <td>
            <html:text name="maintainAccountForm" property="accountData.location.address1" size="60" maxlength="60" onchange="this.value=trim(this.value);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>Address 2</b></div>
          </td>
          <td>
            <html:text name="maintainAccountForm" property="accountData.location.address2" size="60" maxlength="60" onchange="this.value=trim(this.value);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>City</b></div>
          </td>
          <td>
            <html:text name="maintainAccountForm" property="accountData.location.city" size="25" maxlength="25" onchange="this.value=trim(this.value);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>State</b></div>
          </td>
          <td>
            <html:text name="maintainAccountForm" property="accountData.location.state" size="25" maxlength="25" onchange="this.value=trim(this.value);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>Postal Code</b></div>
          </td>
          <td>
            <html:text name="maintainAccountForm" property="accountData.location.zipCode" size="15" maxlength="15" onchange="this.value=trim(this.value);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>Country</b></div>
          </td>
          <td>
            <html:text name="maintainAccountForm" property="accountData.location.country" size="35" maxlength="35" onchange="this.value=trim(this.value);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>Phone</b></div>
          </td>
          <td>
            <html:text name="maintainAccountForm" property="accountData.location.phoneNumber" size="12" maxlength="12" onchange="this.value=trim(this.value);"/>
          </td>
        </tr>
        <tr class="yellow"> 
          <td colspan="2"> 
            <div align="center"><b>Shipping Information</b></div>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>Address 1</b></div>
          </td>
          <td>
            <html:text name="maintainAccountForm" property="accountData.location.shipToAddress.address1" size="60" maxlength="60" onchange="this.value=trim(this.value);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>Address 2</b></div>
          </td>
          <td>
            <html:text name="maintainAccountForm" property="accountData.location.shipToAddress.address2" size="60" maxlength="60" onchange="this.value=trim(this.value);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>City</b></div>
          </td>
          <td>
            <html:text name="maintainAccountForm" property="accountData.location.shipToAddress.city" size="25" maxlength="25" onchange="this.value=trim(this.value);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>State</b></div>
          </td>
          <td>
            <html:text name="maintainAccountForm" property="accountData.location.shipToAddress.state" size="25" maxlength="25" onchange="this.value=trim(this.value);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>Postal Code</b></div>
          </td>
          <td>
            <html:text name="maintainAccountForm" property="accountData.location.shipToAddress.zipCode" size="15" maxlength="15" onchange="this.value=trim(this.value);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>Country</b></div>
          </td>
          <td>
            <html:text name="maintainAccountForm" property="accountData.location.shipToAddress.country" size="35" maxlength="35" onchange="this.value=trim(this.value);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>First Name</b></div>
          </td>
          <td>
            <html:text name="maintainAccountForm" property="accountData.location.shipToAddress.firstName" size="35" maxlength="35" onchange="this.value=trim(this.value);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>Last Name</b></div>
          </td>
          <td>
            <html:text name="maintainAccountForm" property="accountData.location.shipToAddress.lastName" size="30" maxlength="30" onchange="this.value=trim(this.value);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"> 
            <div align="right"><b>Middle Name</b></div>
          </td>
          <td>
            <html:text name="maintainAccountForm" property="accountData.location.shipToAddress.middleName" size="2" maxlength="2" onchange="this.value=trim(this.value);"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<p>
  <table border="0" cellpadding="0" cellspacing="0" align="center" class="background">
  <tr> 
    <td>
      <table border="0" align="center" cellspacing="1" cellpadding="3" class="foreground">
        <tr class="yellow"> 
          <td colspan="5"> 
            <div align="center"><b>Existing Storage Unit Information (for information only - may not be modified)</b></div>
          </td>
        </tr>
        <tr class="yellow"> 
          <td> 
            <b>Storage Type</b>
          </td>
          <td> 
            <b>Room</b>
          </td>
          <td> 
            <b>Storage Unit</b>
          </td>
          <td> 
            <b>Number of Drawers</b>
          </td>
          <td> 
            <b>Slots per Drawer</b>
          </td>
        </tr>
        <logic:iterate name="maintainAccountForm" property="accountData.location.existingStorageUnits" indexId="unitCount" id="unit" type="com.ardais.bigr.javabeans.StorageUnitSummaryDto">
          <tr class="<%if((unitCount.intValue()%2) == 0) out.print("white"); else out.print("grey");%>"> 
            <td><bean:write name="unit" property="storageTypeDescription"/></td>
            <td><bean:write name="unit" property="roomId"/></td>
            <td><bean:write name="unit" property="unitName"/></td>
            <td><bean:write name="unit" property="numberOfDrawers"/></td>
            <td><bean:write name="unit" property="minimumSlotId"/>-<bean:write name="unit" property="maximumSlotId"/></td>
          </tr>
        </logic:iterate>
      </table>
    </td>
  </tr>
</table>
<p>
<table border="0" cellpadding="0" cellspacing="0" align="center" class="background">
  <tr> 
    <td>
      <table id="newStorageUnitTable" border="0" align="center" cellspacing="1" cellpadding="3" class="foreground">
        <thead>
          <tr class="yellow"> 
            <td colspan="6"> 
              <div align="center"><b>New Storage Unit Information</b></div>
            </td>
          </tr>
          <tr class="yellow"> 
            <td> 
              <b>Storage Type <font color="Red">*</font></b>
            </td>
            <td> 
              <b>Room <font color="Red">*</font></b>
            </td>
            <td> 
              <b>Storage Unit <font color="Red">*</font></b>
            </td>
            <td> 
              <b>Number of Drawers (Maximum 500 Drawers) <font color="Red">*</font></b>
            </td>
            <td> 
              <b>Slots per Drawer (A through Z)<font color="Red">*</font></b>
            </td>
            <td>
              &nbsp;
            </td>
          </tr>
        </thead>
        <tbody>
          <logic:iterate name="maintainAccountForm" property="accountData.location.newStorageUnits" indexId="unitCount" id="unit" type="com.ardais.bigr.javabeans.StorageUnitDto">
            <tr id=<%= unitCount%> class="white"> 
              <td>            
                <bigr:selectList
                  name="maintainAccountForm"
                  property='<%="accountData.location.newStorageUnit[" + unitCount + "].storageTypeCui"%>'
		              legalValueSetProperty="storageUnitTypeChoices"/>
              </td>
              <td>
                <html:text name="maintainAccountForm" property='<%="accountData.location.newStorageUnit[" + unitCount + "].roomId"%>' size="22" maxlength="22" onchange="this.value=trim(this.value);"/>
              </td>
              <td>
                <html:text name="maintainAccountForm" property='<%="accountData.location.newStorageUnit[" + unitCount + "].unitName"%>' size="30" maxlength="30" onchange="this.value=trim(this.value);"/>
              </td>
              <td>
                <html:text name="maintainAccountForm" property='<%="accountData.location.newStorageUnit[" + unitCount + "].numberOfDrawers"%>' size="3" maxlength="3" onchange="this.value=trim(this.value);"/>
              </td>
              <td>            
                <bigr:selectList
                  name="maintainAccountForm"
                  property='<%="accountData.location.newStorageUnit[" + unitCount + "].slotsPerDrawer"%>'
				          legalValueSetProperty="slotPerDrawerChoices"/>
              </td>
              <td>
		  				  <%
			  			    String removeString = "onRemoveNewStorageUnitClick('" + unitCount + "');";
				  		  %>
				        <html:button property="btnRemoveNewStorageUnit" value="Remove" onclick="<%=removeString%>"/>
              </td>
            </tr>
          </logic:iterate>
        </tbody>
        <tfoot>
          <tr class="white" align="center">
            <td colspan="6"> 
              <html:button property="btnAddNewStorageUnit" value="Add New Unit" onclick="onAddNewStorageUnitClick();"/>
            </td>
          </tr>
        </tfoot>
      </table>
    </td>
  </tr>
</table>
<script language="javascript">
<!--
//determine next new storage unit id to use
nextNewUnitId = document.getElementById('newStorageUnitTable').tBodies[0].rows.length;
-->
</script>
<p>
  <table border="0" cellpadding="0" cellspacing="0" align="center" class="background">
  <tr class="white"> 
    <td>
      <div align="center">
        <html:button property="btnSubmit" value="Submit" onclick="onSubmitClick();"/>
        <html:button property="btnReset" value="Reset" onclick="onResetClick()"/>
        <html:button property="btnCancel" value="Cancel" onclick="onCancelClick();"/>
      </div>
    </td>
  </tr>
</table>

</html:form>
</body>
</html>