<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.iltds.assistants.LegalValueSet"%>
<%@ page import="com.ardais.bigr.iltds.btx.BTXDetailsUpdateBoxLocation" %>
<%@ page import="com.ardais.bigr.iltds.btx.BTXBoxLocation" %>
<%@ page import="java.util.Vector" %>
<%@ taglib uri="/tld/bigr" prefix="bigr"%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean"  prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>

<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%
	BTXDetailsUpdateBoxLocation btxDetails = (BTXDetailsUpdateBoxLocation) request.getAttribute("btxDetails");
	
    String updateValue = request.getParameter("update");
	boolean updated = false;
	if(updateValue != null && updateValue.equals("finish")){
		updated = true;
	} 
	
	String override_from_boxscan = ApiFunctions.safeToString(request.getAttribute("override"));
	if (override_from_boxscan.length() == 0) override_from_boxscan = ApiFunctions.safeString(request.getParameter("override"));
	boolean overridden = false;
	if ((override_from_boxscan != null) && (override_from_boxscan.equals("Y"))) {
		overridden = true;
	}
	
	String refresh_from_override = ApiFunctions.safeToString(request.getAttribute("refresh"));
	if (refresh_from_override.length() == 0) refresh_from_override = ApiFunctions.safeString(request.getParameter("refresh"));
	boolean refresh_override = false;
	if ((refresh_from_override != null) && (refresh_from_override.equals("Y"))) {
		refresh_override = true;
	}
	
    String barcodeID = ApiFunctions.safeString(request.getParameter("barcodeID")).trim();
    String sampleID = ApiFunctions.safeToString(request.getAttribute("sampleID"));
    String location = ApiFunctions.safeToString(request.getAttribute("location"));
	if (location.length() == 0) location = ApiFunctions.safeString(request.getParameter("location"));
    String numSamples = ApiFunctions.safeToString(request.getAttribute("numSamples"));
	if (numSamples.length() == 0) numSamples = ApiFunctions.safeString(request.getParameter("numSamples"));

    String attr_storageType = ApiFunctions.safeToString(request.getAttribute("storageType"));
	if (attr_storageType.length() == 0) attr_storageType = ApiFunctions.safeString(request.getParameter("storageType"));

    String attr_room = ApiFunctions.safeToString(request.getAttribute("room"));
	if (attr_room.length() == 0) attr_room = ApiFunctions.safeString(request.getParameter("room"));
    String attr_freezer = ApiFunctions.safeToString(request.getAttribute("freezer"));
	if (attr_freezer.length() == 0) attr_freezer = ApiFunctions.safeString(request.getParameter("freezer"));
    String attr_drawer = ApiFunctions.safeToString(request.getAttribute("drawer"));
	if (attr_drawer.length() == 0) attr_drawer = ApiFunctions.safeString(request.getParameter("drawer"));
    String attr_slot = ApiFunctions.safeToString(request.getAttribute("slot"));
	if (attr_slot.length() == 0) attr_slot = ApiFunctions.safeString(request.getParameter("slot"));
    String newLocation = ApiFunctions.safeToString(request.getAttribute("newLocation"));
	if (newLocation.length() == 0) newLocation = ApiFunctions.safeString(request.getParameter("newLocation"));
    String boxID = ApiFunctions.safeToString(request.getAttribute("boxID"));
	if (boxID.length() == 0) boxID = ApiFunctions.safeString(request.getParameter("boxID"));

    String storageTypeCid = ApiFunctions.safeToString(request.getAttribute("storageTypeCid"));
	if (storageTypeCid.length() == 0) storageTypeCid = ApiFunctions.safeString(request.getParameter("storageTypeCid"));

	if (btxDetails != null) {
		String tempBoxID = ApiFunctions.safeString(btxDetails.getBoxId());
		if (tempBoxID.length() > 0) boxID = tempBoxID;
		BTXBoxLocation newBTXBoxLoc = btxDetails.getNewBoxLocation();
		if (newBTXBoxLoc != null) {
			String tempNewLocation = ApiFunctions.safeString(newBTXBoxLoc.getLocationAddressID());
			if (tempNewLocation.length() > 0) newLocation = tempNewLocation;
			String tempStorageTypeCid = ApiFunctions.safeString(newBTXBoxLoc.getStorageTypeCid());
			if (tempStorageTypeCid.length() > 0) storageTypeCid = tempStorageTypeCid;
		}
	}

    String attr_roomlist = ApiFunctions.safeToString(request.getAttribute("roomlist"));
    if (attr_roomlist.length() == 0) attr_roomlist = ApiFunctions.safeString(request.getParameter("roomlist"));
    String attr_freezerlist = ApiFunctions.safeString(request.getParameter("freezerlist"));
    String attr_drawerlist = ApiFunctions.safeString(request.getParameter("drawerlist"));
    String attr_slotlist = ApiFunctions.safeString(request.getParameter("slotlist"));
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<title>Update Box Location</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript">
<!--
var myBanner = 'Update Box Location';

function updateList(param, refresh, theBox){
	//alert("updateList:param, refresh, theBox=" + param + " : " + refresh + " : " + theBox);
	if (param == 'storageType') {
		if (document.forms[0].storageTypeCid.value == '-1'){
			alert("Please enter a value for Storage Type");
		} else {

			document.forms[0].update.value = 'storageType';
			
			// resetting for MR 7238...
			document.forms[0].roomlist.value='-1';
			document.forms[0].freezerlist.value='-1';
			document.forms[0].drawerlist.value='-1';
			document.forms[0].slotlist.value='-1';
			
			document.forms[0].submit();
		}  

	} else if(param == 'room'){
		if (document.forms[0].storageTypeCid.value == '-1'){
			alert("Please enter a value for Storage Type");
		} else if (document.forms[0].roomlist.value == '-1'){
			alert("Please enter a value for Room");
		} else {
			document.forms[0].update.value = 'room';
			
			// resetting for MR 7238...
			document.forms[0].freezerlist.value='-1';
			document.forms[0].drawerlist.value='-1';
			document.forms[0].slotlist.value='-1';
			
			document.forms[0].submit();
		}  
	} else if (param == 'freezer'){
		if (document.forms[0].storageTypeCid.value == '-1'){
			alert("Please enter a value for Storage Type");
		} else if (document.forms[0].roomlist.value == '-1'){
			alert("Please enter a value for Room");
		} else if (document.forms[0].freezerlist.value == '-1'){
			alert("Please enter a value for Storage Unit");
		} else {
			document.forms[0].update.value = 'freezer';
			
			// resetting for MR 7238...
			document.forms[0].drawerlist.value='-1';
			document.forms[0].slotlist.value='-1';
			
			document.forms[0].submit();
		}
	} else if (param == 'drawer'){
		if (document.forms[0].storageTypeCid.value == '-1'){
			alert("Please enter a value for Storage Type");
		} else if (document.forms[0].roomlist.value == '-1'){
			alert("Please enter a value for Room");
		} else if (document.forms[0].freezerlist.value == '-1'){
			alert("Please enter a value for Storage Unit");
		} else if (document.forms[0].drawerlist.value == '-1'){
			alert("Please enter a value for Drawer");
		} else {
			document.forms[0].update.value = 'drawer';
			
			// resetting for MR 7238...
			document.forms[0].slotlist.value='-1';
			
			document.forms[0].submit();
		}
	} else if (param == 'finish'){
		if (document.forms[0].storageTypeCid.value == '-1'){
			alert("Please enter a value for Storage Type");
		} else if (document.forms[0].roomlist.value == '-1'){
			alert("Please enter a value for Room");
		} else if (document.forms[0].freezerlist.value == '-1'){
			alert("Please enter a value for Storage Unit");
		} else if (document.forms[0].drawerlist.value == '-1'){
			alert("Please enter a value for Drawer");
		} else if (document.forms[0].slotlist.value == '-1'){
			alert("Please enter a value for Slot");
		} else {
			document.forms[0].update.value = 'finish';
			//alert("refresh=" + refresh);
			//alert("roomlist=" + document.forms[0].roomlist.options(document.forms[0].roomlist.selectedIndex).text);
			//alert("roomlist=" + document.forms[0].roomlist.selectedIndex);
			if (refresh) { // if there is a callback...
				window.opener.refreshOverridePopup(theBox, 
				document.forms[0].storageTypeCid.options(document.forms[0].storageTypeCid.selectedIndex).text,
				document.forms[0].roomlist.options(document.forms[0].roomlist.selectedIndex).text,
				document.forms[0].freezerlist.options(document.forms[0].freezerlist.selectedIndex).text,
				document.forms[0].drawerlist.options(document.forms[0].drawerlist.selectedIndex).text,
				document.forms[0].slotlist.options(document.forms[0].slotlist.selectedIndex).text);
			}
			//alert("after callback function");
			document.forms[0].submit();
		}
	}	
}

function initLoad() {
	if (parent.topframe != null) {
		parent.topFrame.document.all.banner.innerHTML = myBanner;
	}
}

function openExpectedSamples() {
  var w = window.open('<html:rewrite page="/iltds/Dispatch"/>?op=ReceiptExpectedSamples&boxID=<%= boxID %>',
    'ExpectedSamples',
    'scrollbars=yes,resizable=yes,width=400,height=300');
  w.focus();
}

//-->
</script>
</head>
<body class="bigr" onload="initLoad();">
<form name="form1" method="post" action="<html:rewrite page='/iltds/updateBoxLocationEdit.do'/>">
<input type="hidden" name="update" value="">
<input type="hidden" name="numSamples" value="<%= numSamples %>">
<input type="hidden" name="barcodeID" value="<%= barcodeID %>">
<input type="hidden" name="override" value="<%= override_from_boxscan %>">
<input type="hidden" name="refresh"  value="<%= refresh_from_override %>">
<table border="0" cellspacing="0" cellpadding="0" class="background" align="center">
  <tr> 
    <td> 
        <table border="0" cellspacing="1" cellpadding="3" class="foreground" width="100%">
          <%if(request.getAttribute("org.apache.struts.action.ERROR")!=null){ %>
          <tr class="yellow"> 
            <td colspan="4"> 
              <div align="center"><b><font color="#FF0000">
				<html:errors/></font></b></div>
            </td>
          </tr>
          <%}%>
		  <input type="hidden" name="boxID" value="<%= boxID %>">
          <% if (sampleID.length() == 0) {%>
          <tr class="green"> 
            <td colspan="4"><b>Box ID: <%= boxID %></b></td>
          </tr>
          <%} else {%>
          <tr class="green"> 
            <td colspan="2"><b>Sample ID: <%= sampleID %></b></td>
            <td colspan="2"><b>Box ID: <%= boxID %></b></td>
          </tr>
          <%}%>
          <tr class="yellow"> 
            <td colspan="2"><b>Expected Location:</b></td>
            <td colspan="2"><b>New Location: </b></td>
          </tr>
          <tr class="white"> 
            <td class="grey" > 
              <div align="right"><b>Location:</b></div>
            </td>
            <td> 
              <div id="locationID0"><%= location %>
			  <input type="hidden" name="location" value="<%= location %>">
			  </div>
            </td>
            <td class="grey"> 
              <div align="right"><b>Location:</b></div>
            </td>
            <td> <%= ((newLocation.length() > 0)?newLocation:location) %>
      		   <input type="hidden" name="newLocation" value="<%= ((newLocation.length() > 0)?newLocation:location) %>">
            </td>
          </tr>


          <%
            LegalValueSet storageTypes = (LegalValueSet)request.getSession(false).getAttribute("storageTypes");
          %>
          <tr class="white"> 
            <td class="grey"> 
              <div align="right"><b>Storage Type:</b></div>
            </td>
            <td> 
              <div id="storageTypeID0"><%=attr_storageType%>
			  <input type="hidden" name="storageType" value="<%=attr_storageType%>">
			  </div>
            </td>
            <td class="grey"> 
              <div align="right"><b>Storage Type: </b></div>
            </td>
            <td>
              <bigr:selectList
                property="storageTypeCid"
                legalValueSet="<%=storageTypes%>"
                firstValue="-1"
                firstDisplayValue="Storage Type"
                value="<%=storageTypeCid%>"
                onchange="return updateList('storageType', false, 0)"/>
            </td>
          </tr>




          <tr class="white"> 
            <td class="grey"> 
              <div align="right"><b>Room:</b></div>
            </td>
            <td> 
              <div id="roomID0"><%= attr_room %>
			  <input type="hidden" name="room" value="<%= attr_room %>">
			  </div>
            </td>
            <td class="grey"> 
              <div align="right"><b>Room: </b></div>
            </td>
            <td> 
			<% 
			Vector roomlist = new Vector();
			roomlist = (Vector) request.getSession(false).getAttribute("roomlist");
			if(roomlist != null && roomlist.size() != 0){
 				String room = attr_roomlist;
			   %>
              <select name="roomlist" onchange="return updateList('room', false, 0)">
                <option value="-1">Room</option>
                <%
				for(int i=0;i<roomlist.size();i++) {%>
                <option
                  <% if(room.equalsIgnoreCase((String)roomlist.elementAt(i))) out.print("selected"); %>>
                  <%=(String)roomlist.elementAt(i)%>
                </option>
                <%}%>
              </select>
              <%} else {%>
			  &nbsp;<input type="hidden" name="roomlist" value=""> 
			  <%}%>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey"> 
              <div align="right"><b>Storage Unit:</b></div>
            </td>
            <td> 
              <div id="freezerID0"><%= attr_freezer %>
			  <input type="hidden" name="freezer" value="<%= attr_freezer %>">
			  </div>
            </td>
            <td class="grey"> 
              <div align="right"><b>Storage Unit:</b></div>
            </td>
            <td> 
              <% 
			Vector freezerlist = new Vector();
			freezerlist = (Vector) request.getSession(false).getAttribute("freezerlist");
			if(freezerlist != null && freezerlist.size() != 0){
				String freezer = attr_freezerlist;
			   %>
              <select name="freezerlist" onchange="return updateList('freezer', false, 0)">
                <option value="-1">Storage Unit</option>
                <%
				 for(int i=0;i<freezerlist.size();i++) {%>
                <option <%if(freezer.equalsIgnoreCase((String)freezerlist.elementAt(i)) && !updated) out.print("selected");%>><%=(String)freezerlist.elementAt(i)%></option>
                <%}%>
              </select>
			  <%} else {%>
			  &nbsp;<input type="hidden" name="freezerlist" value=""> 
			  <%}%>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey"> 
              <div align="right"><b>Drawer:</b></div>
            </td>
            <td> 
              <div id="drawerID0"><%= attr_drawer %>
			  <input type="hidden" name="drawer" value="<%= attr_drawer %>">
			  </div>
            </td>
            <td class="grey"> 
              <div align="right"><b>Drawer: </b></div>
            </td>
            <td> 
              <% 
			Vector drawerlist = new Vector();
			drawerlist = (Vector) request.getSession(false).getAttribute("drawerlist");
			if(drawerlist != null && drawerlist.size() != 0){
				String drawer = attr_drawerlist;
				
			   %>
              <select name="drawerlist" onchange="return updateList('drawer', false, 0)">
                <option value="-1">Drawer</option>
                <%
				 for(int i=0;i<drawerlist.size();i++) {%>
                <option <%if(drawer.equalsIgnoreCase((String)drawerlist.elementAt(i)) && !updated) out.print("selected");%>><%=(String)drawerlist.elementAt(i)%></option>
                <%}%>
              </select>
			  <%} else {%>
			  &nbsp;<input type="hidden" name="drawerlist" value=""> 
			  <%}%>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey"> 
              <div align="right"><b>Slot:</b></div>
            </td>
            <td> 
              <div id="slotID0"><%= attr_slot %>
			  <input type="hidden" name="slot" value="<%= attr_slot %>">
			  </div>
            </td>
            <td class="grey"> 
              <div align="right"><b>Slot: </b></div>
            </td>
            <td> 
             <% 
			Vector slotlist = new Vector();
			slotlist = (Vector) request.getSession(false).getAttribute("slotlist");
			if(slotlist != null && slotlist.size() != 0){
				String slot = attr_slotlist;
			   %>
              <select name="slotlist">
			  	<option value="-1">Slot</option>
			  	<%
				 for(int i=0;i<slotlist.size();i++) {%>
                <option <%if(slot.equalsIgnoreCase((String)slotlist.elementAt(i)) && !updated
				) out.print("selected");%>><%=(String)slotlist.elementAt(i)%></option>
                <%}%>
              </select>
			  <%} else {%>
			  &nbsp;<input type="hidden" name="slotlist" value=""> 			  <%}%>
            </td>
          </tr>
          <tr class="white"> 
            <td colspan="4"> 
              <div align="center"> 
                <input type="button" name="Button" value="Update Location" onclick="updateList('finish', <%=refresh_override%>, '<%=boxID%>');">
                <input type="button" name="Button1" value="<%= numSamples %>"
                       onclick="openExpectedSamples();">
				<%if (!overridden) { %>
					<input type="button" name="Submit2" value="Cancel" onclick="MM_goToURL('parent.mainFrame','<html:rewrite page="/iltds/Dispatch?op=UpdateBoxLocation"/>');return document.MM_returnValue">
				<%}
				else {%>
               		<input type="button" name="Print" value="Print" onclick="window.print();">				
				<%}%>
                <br>
              </div>
            </td>
          </tr>
        </table>
    </td>
  </tr>
</table>
</form>
</body>
</html>
