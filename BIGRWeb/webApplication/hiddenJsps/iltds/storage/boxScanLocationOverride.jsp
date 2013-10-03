<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="java.util.Vector" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Box Location Override</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript">
<!--
myBanner = 'Update Box Location';

function MM_callJS(jsStr) { //v2.0
  return eval(jsStr);
}

function updateList(param){
	if(param == 'room'){
		if (document.forms[0].roomlist.value == '-1'){
			alert("Please enter a value for Room");
		} else {
			document.forms[0].update.value = 'room';
			document.forms[0].submit();
		}  
	} else if (param == 'freezer'){
		if (document.forms[0].roomlist.value == '-1'){
			alert("Please enter a value for Room");
		} else if (document.forms[0].freezerlist.value == '-1'){
			alert("Please enter a value for Storage Unit");
		} else {
			document.forms[0].update.value = 'freezer';
			document.forms[0].submit();
		}
	} else if (param == 'drawer'){
		if (document.forms[0].roomlist.value == '-1'){
			alert("Please enter a value for Room");
		} else if (document.forms[0].freezerlist.value == '-1'){
			alert("Please enter a value for Storage Unit");
		} else if (document.forms[0].drawerlist.value == '-1'){
			alert("Please enter a value for Drawer");
		} else {
			document.forms[0].update.value = 'drawer';
			document.forms[0].submit();
		}
	} else if (param == 'finish'){
		if (document.forms[0].roomlist.value == '-1'){
			alert("Please enter a value for Room");
		} else if (document.forms[0].freezerlist.value == '-1'){
			alert("Please enter a value for Storage Unit");
		} else if (document.forms[0].drawerlist.value == '-1'){
			alert("Please enter a value for Drawer");
		} else if (document.forms[0].slotlist.value == '-1'){
			alert("Please enter a value for Slot");
		} else {
			document.forms[0].update.value = 'finish';
			document.forms[0].submit();
		}
	}	
}
//-->
</script>
</head>
<body class="bigr">
<form name="form1" method="post" action="<html:rewrite page='/iltds/Dispatch'/>">                            
<table border="0" cellspacing="0" cellpadding="0" class="background" align="center">
  <tr> 
	 <td> 
        <table border="0" cellspacing="1" cellpadding="3" class="foreground">
          <%if((request.getAttribute("myError") != null) && (!request.getAttribute("myError").equals(""))) {%>
          <tr class="yellow"> 
            <td colspan="2"> 
              <div align="center"><font color="#FF0000"><b><%=((request.getAttribute("myError")!=null)?request.getAttribute("myError"):"")%> </b></font></div>
            </td>
          </tr>
          <script>
			alert("<%= request.getAttribute("myError") %>");
			</script>
          <%}%>
          <tr class="white"> 
            <td class="grey"> 
              <div align="right"><b>Box ID:</b></div>
            </td>
            <td> 
              <% String boxID = (String) request.getAttribute("boxID");
				if(boxID == null || boxID.equals("")){
					boxID = request.getParameter("boxID");
					if(boxID == null){
						boxID = "";
					}
				}
				 if(boxID.equals("")){
				 %>
              <input type="text" name="boxID" value="<%= boxID%>" maxlength="12">
              <%} else {%>
              <%= boxID%> 
              <input type="hidden" name="boxID" value="<%= boxID%>">
              <%}%>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey"> 
              <div align="right"><b>Room:</b></div>
            </td>
            <td> 
              <% 
			String update = request.getParameter("update");
			if(update == null){
				update = "";
			}
			
			Vector roomlist = new Vector();
			roomlist = (Vector) request.getSession(false).getAttribute("roomlist");
			if(roomlist != null && roomlist.size() != 0){
				String room = ((request.getParameter("roomlist")!=null)?request.getParameter("roomlist"):"");
			   %>
              <select name="roomlist" onChange="return updateList('room')">
                <option value="-1">Room</option>
                <%
				 for(int i=0;i<roomlist.size();i++) {%>
                <option <%if(room.equalsIgnoreCase((String)roomlist.elementAt(i))) out.print("selected");%>><%=(String)roomlist.elementAt(i)%></option>
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
                <% 
			Vector freezerlist = new Vector();
			freezerlist = (Vector) request.getSession(false).getAttribute("freezerlist");
			if(freezerlist != null && freezerlist.size() != 0){
				String freezer = ((request.getParameter("freezerlist")!=null)?request.getParameter("freezerlist"):"");
			   %>
              <select name="freezerlist" onChange="return updateList('freezer')">
                <option value="-1">Storage Unit</option>
                <%
				 for(int i=0;i<freezerlist.size();i++) {%>
                <option <%if(freezer.equalsIgnoreCase((String)freezerlist.elementAt(i))) out.print("selected");%>><%=(String)freezerlist.elementAt(i)%></option>
                <%}%>
              </select>
              <%} else {%>
              &nbsp; 
              <input type="hidden" name="freezerlist" value=""> 
			  <%}%>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey"> 
              <div align="right"><b>Drawer:</b></div>
            </td>
            <td> 
               <% 
			Vector drawerlist = new Vector();
			drawerlist = (Vector) request.getSession(false).getAttribute("drawerlist");
			if(drawerlist != null && drawerlist.size() != 0){
				String drawer = ((request.getParameter("drawerlist")!=null)?request.getParameter("drawerlist"):"");
				
			   %>
              <select name="drawerlist" onChange="return updateList('drawer')">
                <option value="-1">Drawer</option>
                <%
				 for(int i=0;i<drawerlist.size();i++) {%>
                <option <%if(drawer.equalsIgnoreCase((String)drawerlist.elementAt(i))) out.print("selected");%>><%=(String)drawerlist.elementAt(i)%></option>
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
               <% 
			Vector slotlist = new Vector();
			slotlist = (Vector) request.getSession(false).getAttribute("slotlist");
			if(slotlist != null && slotlist.size() != 0){
				String slot = ((request.getParameter("slotlist")!=null)?request.getParameter("slotlist"):"");
			   %>
              <select name="slotlist">
			  	<option value="-1">Slot</option>
			  	<%
				 for(int i=0;i<slotlist.size();i++) {%>
                <option <%if(slot.equalsIgnoreCase((String)slotlist.elementAt(i))) out.print("selected");%>><%=(String)slotlist.elementAt(i)%></option>
                <%}%>
              </select>
			  <%} else {%>
			  &nbsp;<input type="hidden" name="slotlist" value=""> 			  <%}%>
            </td>
          </tr>
          <tr class="white"> 
            <td colspan="2"> 
              <div align="center"> 
			  	<input type="hidden" name="update" value="">
                <input type="hidden" name="storageTypeCid" value="<%= ((request.getAttribute("storageTypeCid")!=null)?request.getAttribute("storageTypeCid"):((request.getParameter("storageTypeCid")!=null)?request.getParameter("storageTypeCid"):"")) %>">
                <input type="hidden" name="title" value="<%= ((request.getParameter("title")!=null)?request.getParameter("title"):"") %>">
                <input type="button" name="Button" value="Submit"  onClick="return updateList('finish')">
                <input type="button" name="Cancel" value="Cancel" onclick="window.close();">
                <input type="hidden" name="inc" value="<%= ((request.getParameter("inc")!=null)?request.getParameter("inc"):"") %>">
                <input type="hidden" name="hiddenName" value="<%= ((request.getParameter("hiddenName")!=null)?request.getParameter("hiddenName"):"") %>">
                <input type="hidden" name="frz" value="<%= ((request.getAttribute("frz")!=null)?request.getAttribute("frz"):"") %>">
                <input type="hidden" name="drw" value="<%= ((request.getAttribute("drw")!=null)?request.getAttribute("drw"):"") %>">
                <input type="hidden" name="slt" value="<%= ((request.getAttribute("slt")!=null)?request.getAttribute("slt"):"") %>">
                <input type="hidden" name="loc" value="<%= ((request.getAttribute("loc")!=null)?request.getAttribute("loc"):((request.getParameter("loc")!=null)?request.getParameter("loc"):"")) %>">
                <input type="hidden" name="stp" value="<%= ((request.getAttribute("stp")!=null)?request.getAttribute("stp"):"") %>">
                <input type="hidden" name="room" value="<%= ((request.getAttribute("room")!=null)?request.getAttribute("room"):"") %>">
                <input type="hidden" name="op" value="OverrideBoxLocInsert">
                <input type="hidden" name="popup" value="<%= ((request.getAttribute("popup")!=null)?request.getAttribute("popup"):"") %>">
              </div>
            </td>
          </tr>
        </table>
    </td>
                </tr>
              </table>
          </form>
 <script language="JavaScript">
 <% if(boxID.equals("")){
				 %> 
		document.form1.boxID.focus()
		<%}%>
		</script>
</body>
<HEAD><META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE"></HEAD>     
</html>
