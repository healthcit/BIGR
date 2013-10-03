<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions"%>
<%@ taglib uri="/tld/struts-html" prefix="html"%>
<%@ taglib uri="/tld/struts-logic" prefix="logic"%>
<%@ taglib uri="/tld/struts-bean" prefix="bean"%>
<%@ taglib uri="/tld/bigr" prefix="bigr"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Scan & Store</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script type="text/javascript" src="<html:rewrite page="/js/common.js"/>"> </script>
<script language="JavaScript">
<!--

var myBanner = 'Scan & Store';

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
}

function setActionButtonEnabling(isEnabled) {
	var f = document.forms[0];
	setInputEnabled(f,'submitButton',isEnabled);
	setInputEnabled(f,'printAllButton',isEnabled);
}

function onFormSubmit() {
  setActionButtonEnabling(false);
  return true;
}

function printPutAwayTicket() {
	var trackingNumber = document.all("trackingNumber").value;
	var manifestNumber = document.all("manifestNumber").value;

	var url = '<html:rewrite page="/iltds/receipt/printPutAwayTicket.do?popup=true"/>';
	url += '&trackingNumber=' + trackingNumber;
	url += '&manifestNumber=' + manifestNumber;

	var w = window.open(url, 'LocationCard', 'resizable=yes,width=500,height=600');
	w.focus();
}

function override(boxId) {
	var url = '<html:rewrite page="/iltds/Dispatch?op=LocationLookupStart"/>';
	url += '&barcodeID=' + boxId;
	url += '&override=Y';
	url += '&refresh=Y';

	var w = window.open(url, 'OverrideLocation', 'resizable=yes,width=640,height=300,top=200,left=220');
	w.focus();
}

function refreshOverridePopup(boxId, storageType, room, unitName, drawer, slot) {
	document.all("storageType_" + boxId).innerHTML = storageType;
	document.all("room_" + boxId).innerHTML = room;
	document.all("unitName_" + boxId).innerHTML = unitName;
	document.all("drawer_" + boxId).innerHTML = drawer;
	document.all("slot_" + boxId).innerHTML = slot;
}

function viewSamples(boxId) {
	var url = '<html:rewrite page="/iltds/Dispatch?op=ReceiptExpectedSamples"/>';
	url += '&boxID=' + boxId;

	var w = window.open(url, 'ExpectedSamples', 'scrollbars=yes,resizable=yes,width=400,height=400');
	w.focus();
}

function printBoxPutAwayTicket(boxId) {
	var trackingNumber = document.all("trackingNumber").value;
	var manifestNumber = document.all("manifestNumber").value;

	var url = '<html:rewrite page="/iltds/receipt/printPutAwayTicket.do?popup=true"/>';
	url += '&trackingNumber=' + trackingNumber;
	url += '&manifestNumber=' + manifestNumber;
	url += '&scannedBoxId=' + boxId;

	var w = window.open(url, 'PrintLocation', 'scrollbars=yes,resizable=yes,width=500,height=300');
	w.focus();
}

//-->
</script>
</head>
<body class="bigr" onload="initPage();">
<html:form action="/iltds/receipt/scanAndStoreBox" onsubmit="return(onFormSubmit());">
  <table border="0" cellspacing="0" cellpadding="0" align="center">
    <logic:present name="org.apache.struts.action.ERROR">
    <tr> 
      <td>
        <table border="0" cellspacing="1" cellpadding="1" class="foreground-small">
          <tr class="white">
		    <td><div align="left"><font color="#FF0000"><b><html:errors/></b></font></div></td>
		  </tr>
		</table>
	  </td>
	</tr>
	</logic:present>
	<logic:present name="com.ardais.BTX_ACTION_MESSAGES">
    <tr> 
      <td>
        <table border="0" cellspacing="1" cellpadding="1" class="foreground-small">
          <tr class="white">
		    <td><div align="left"><font color="#FF0000"><b><bigr:btxActionMessages/></b></font></div></td>
		  </tr>
		</table>
	  </td>
	</tr>
	</logic:present>
	<tr> 
	  <td> 
		<table border="1" cellspacing="1" cellpadding="3" class="foreground" align="center">
          <tr class="white"> 
            <td colspan="2" align="center" class="grey"><b>Tracking Number:</b></td>
            <td><bean:write name="manifestForm" property="trackingNumber"/></td>
            <td colspan="2" align="center" class="grey" ><b>Manifest Number:</b></td>
            <td><bean:write name="manifestForm" property="manifestNumber"/></td>
            <html:hidden property="trackingNumber"/>
            <html:hidden property="manifestNumber"/>
          </tr>
          <logic:notEqual name="manifestForm" property="finished" value="true">
          <tr class="white"> 
            <td colspan="6" align="center"> 
              <div align="center">Box Id: 
                <html:text property="scannedBoxId" size="20" maxlength="12"/>
                <script language="JavaScript">
                  document.forms[0].scannedBoxId.focus();
                </script>
              </div>
            </td>
          </tr>
          </logic:notEqual>
          <tr class="white">
            <td colspan="6" align="center">
              <logic:notEqual name="manifestForm" property="finished" value="true">
              <html:submit property="submitButton" value="Continue" style="font-size:xx-small; width: 90px;"/>
		      <html:button property="printAllButton" value="Print Put Away Ticket" onclick="printPutAwayTicket()" style="font-size:xx-small;"/>
              </logic:notEqual>
              <logic:equal name="manifestForm" property="finished" value="true">
		      <html:button property="printAllButton" value="Print Put Away Ticket" onclick="printPutAwayTicket()" style="font-size:xx-small;"/>
              </logic:equal>
            </td>
          </tr>
          <tr class="yellow"> 
            <td> 
              <div align="left"><b>Box Id:</b></div>
            </td>
            <td colspan="2" > 
              <div align="left"><b>Location:</b></div>
            </td>
            <td> 
              <div align="left"><b>Override Location:</b></div>
            </td>
            <td> 
              <div align="left"><b>Samples:</b></div>
            </td>
            <td> 
              <div align="left"><b>Print:</b></div>
            </td>
          </tr>
          <bean:define id="showLegend" value="false"/>
          <logic:iterate name="manifestForm" property="boxes" id="box" type="com.ardais.bigr.javabeans.BoxDto">
          <bean:define id="boxLocationFlag" value='<%=(!ApiFunctions.isEmpty(box.getLocation())) ? "true" : "false"%>'/>
          <bean:define id="multipleStorageTypeFlag" value='<%=(!ApiFunctions.isEmpty(box.getAvailableStorageTypes())) ? "true" : "false"%>'/>
          <bean:define id="warningFlag" value='<%=(!ApiFunctions.isEmpty(box.getWarning())) ? "true" : "false"%>'/>
          <tr class="white"> 
            <td rowspan="5">
              <div align="center">
                <logic:equal name="box" property="containsPulledOrRevokedSamples" value="true">
                 <font color="#FF0000"><bean:write name="box" property="boxId"/></font>
                 <% showLegend = "true"; %>
                </logic:equal>
                <logic:notEqual name="box" property="containsPulledOrRevokedSamples" value="true">
                 <bean:write name="box" property="boxId"/>
                </logic:notEqual>
              </div>
            </td>
            <logic:equal name="warningFlag" value="true">
			<td rowspan=5" colspan="3">
              <div align="left">
                 <font color="#FF0000"><bean:write name="box" property="warning"/></font>
              </div>
			</td>
            </logic:equal>
            <logic:notEqual name="warningFlag" value="true">
              <logic:notEqual name="boxLocationFlag" value="true">
			  <td rowspan=5" colspan="2">
                &nbsp; 
			  </td>
              </logic:notEqual>
              <logic:equal name="boxLocationFlag" value="true">
              <td><b>Storage Type:</b></td>
              <td> 
                <div id="storageType_<%=box.getBoxId()%>" align="center"><bean:write name="box" property="storageTypeDesc"/></div>
              </td>
              </logic:equal>
              <logic:notEqual name="boxLocationFlag" value="true">
              <td rowspan="5">
                <div align="center"> 
                  &nbsp; 
                </div>
              </td>
              </logic:notEqual>
              <logic:equal name="boxLocationFlag" value="true">
                <logic:equal name="multipleStorageTypeFlag" value="true">
                <td rowspan="3">
                  <div align="center"> 
                    <html:button property="overrideButton" value="Override" onclick='<%="override(\'"+box.getBoxId()+"\')"%>' style="font-size:xx-small;"/>
                  </div>
                </td>
                </logic:equal>
                <logic:notEqual name="multipleStorageTypeFlag" value="true">
                <td rowspan="5">
                  <div align="center"> 
  				    <html:button property="overrideButton" value="Override" onclick='<%="override(\'"+box.getBoxId()+"\')"%>' style="font-size:xx-small;"/>
                  </div>
                </td>
                </logic:notEqual>
              </logic:equal>
            </logic:notEqual>
            <td rowspan="5">
              <div align="center"> 
                <%
                  String buttonLabel = null;
                  int sampleCount = box.getBoxSampleCount();
                  int boxCapacity = box.getBoxLayoutDto().getBoxCapacity();
                  if (sampleCount == boxCapacity) {
                    buttonLabel = "Full";
                  }
                  else if (sampleCount == 1) {
                    buttonLabel = sampleCount + " Sample";
                  }
                  else {
                    buttonLabel = sampleCount + " Samples";
                  }
                %>
			    <html:button property="samplesButton" value='<%=buttonLabel%>' onclick='<%="viewSamples(\'"+box.getBoxId()+"\')"%>' style="font-size:xx-small;"/>
              </div>
            </td>
            <td rowspan="5">
              <div align="center"> 
                <logic:notEqual name="boxLocationFlag" value="true">
                &nbsp; 
                </logic:notEqual>
                <logic:equal name="boxLocationFlag" value="true">
				<html:button property="printBoxButton" value="Print" onclick='<%="printBoxPutAwayTicket(\'"+box.getBoxId()+"\')"%>' style="font-size:xx-small;"/>
                </logic:equal>
              </div>
            </td>
          </tr>
          <tr class="white">
            <logic:equal name="boxLocationFlag" value="true">
            <td><b>Room:</b></td>
            <td> 
              <div id="room_<%=box.getBoxId()%>" align="center"><bean:write name="box" property="room"/></div>
            </td>
            </logic:equal>
          </tr>
          <tr class="white">
            <logic:equal name="boxLocationFlag" value="true">
            <td><b>Storage Unit:</b></td>
            <td> 
              <div id="unitName_<%=box.getBoxId()%>" align="center"><bean:write name="box" property="unitName"/></div>
            </td>
            </logic:equal>
          </tr>
          <tr class="white"> 
            <logic:equal name="boxLocationFlag" value="true">
            <td><b>Drawer:</b></td>
            <td> 
              <div id="drawer_<%=box.getBoxId()%>" align="center"><bean:write name="box" property="drawer"/></div>
            </td>
            </logic:equal>
            <logic:equal name="boxLocationFlag" value="true">
              <logic:equal name="multipleStorageTypeFlag" value="true">
              <td class="yellow">
                <div align="center"><b>Available Storage Types</b></div>
              </td>
              </logic:equal>
            </logic:equal>
          </tr>
          <tr class="white"> 
            <logic:equal name="boxLocationFlag" value="true">
            <td><b>Slot:</b></td>
            <td> 
              <div id="slot_<%=box.getBoxId()%>" align="center"><bean:write name="box" property="slot"/></div>
            </td>
            </logic:equal>
            <logic:equal name="boxLocationFlag" value="true">
              <logic:equal name="multipleStorageTypeFlag" value="true">
              <td>
                <div align="center"><bean:write name="box" property="availableStorageTypes"/></div>
              </td>
              </logic:equal>
            </logic:equal>
          </tr>
          </logic:iterate>
          <logic:equal name="showLegend" value="true">
          <tr class="white">
            <td colspan="6" align="center">
              <font color="#FF0000">*Red Samples have been pulled or revoked.</font> 
            </td>
          </tr>
          </logic:equal>
		</table>
	  </td>
	</tr>
  </table>
</html:form>
</body>
</html>