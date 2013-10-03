<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="java.util.Vector" %>
<%@ page import="com.ardais.bigr.iltds.btx.BTXBoxLocation" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript">
<!--
function MM_callJS(jsStr) { //v2.0
  return eval(jsStr)
}
myBanner = 'Box Location';
var w = null;
function MM_openBrWindow(theURL,winName,features) { //v2.0
	w = window.open(theURL,winName,features);
	w.focus();
}

function showPopup() {
	if ((w != null) && !(w.closed)) {
		w.focus();
	}
}

window.onfocus = showPopup;
//-->
</script>
</head>
<body class="bigr"
      onLoad="if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;"
      onfocus="showPopup();">
<table border="0" cellspacing="0" cellpadding="0" class="background" align="center">
  <tr> 
    <td> 
      <table border="0" cellspacing="1" cellpadding="3" class="foreground" width="100%">
        <tr class="yellow"> 
          <td colspan="2"> 
            <div align="center"><b>Put Away Ticket</b></div>
          </td>
        </tr>
        <tr class="white"> 
          <%if(request.getAttribute("myError") == null) {%>
          <td class="grey"><b>Box ID:</b></td>
          <td><%= ((request.getParameter("boxID")!=null)?request.getParameter("boxID"):"") %> </td>
          <%} else {%>
          <td colspan="2"><b><font color="#FF0000"><%=(String)request.getAttribute("myError")%></font></b></td>
          <script>
			alert("<%= request.getAttribute("myError") %>");
			</script>
          <%}%>
        </tr>
        <tr class="white"> 
          <td class="grey" width="50"><b>Storage Type:</b></td>
          <td> 
            <div id="storageTypeID0"><%= ((request.getAttribute("storageType")!=null)?request.getAttribute("storageType"):"") %></div>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey" width="50"><b>Room:</b></td>
          <td> 
            <div id="roomID0"><%= ((request.getAttribute("room")!=null)?request.getAttribute("room"):"") %></div>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"><b>Storage Unit:</b></td>
          <td> 
            <div id="freezerID0"> <%= ((request.getAttribute("freezer")!=null)?request.getAttribute("freezer"):"") %></div>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"><b>Drawer:</b></td>
          <td> 
            <div id="drawerID0"><%= ((request.getAttribute("drawer")!=null)?request.getAttribute("drawer"):"") %></div>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey"><b>Slot:</b></td>
          <td> 
            <div id="slotID0"><%= ((request.getAttribute("slot")!=null)?request.getAttribute("slot"):"") %></div>
          </td>
        </tr>
        <tr class="white"> 
          <td colspan="2"> 
            <form name="form1" method="post" action="<html:rewrite page='/iltds/Dispatch'/>">
              <div id="blah" align="center"></div>
              <div align="center"> 
                <input type="hidden" name="op" value="BoxScanSubmitted">
                <input type="hidden" name="boxID" value="<%= ((request.getParameter("boxID")!=null)?request.getParameter("boxID"):"") %>">
                <input type="hidden" name="title" value="<%= ((request.getParameter("title")!=null)?request.getParameter("title"):"") %>">

                <% 
                	
                	// new URL for overriding...MR 4805
                	StringBuffer theURLSB1 = new StringBuffer(250);
                	theURLSB1.append("/iltds/Dispatch?op=LocationLookupStart&barcodeID=");
                	if (request.getAttribute("boxID")!=null) {
                		theURLSB1.append(request.getAttribute("boxID"));
                		theURLSB1.append("&override=Y");
                	}
                	String theURL1 = theURLSB1.toString();
                	
                %>
                
                <input type="button" name="button1" value="Override Location" onClick="location.href='<html:rewrite page="<%= theURL1 %>"/>';">
                <input type="button" name="Submit" value="Print" onClick="MM_callJS('window.print();')">
               </div>
            </form>
          </td>
        </tr>
      </table>
      
	                  <% 
		  Vector boxes = (Vector) request.getAttribute("emptyBoxes");
		  if(boxes != null && boxes.size() != 0){ 
		  %>
        <table border="0" cellspacing="0" cellpadding="0" class="background">
          <tr> 
            <td> 
        
                    <table border="0" cellspacing="1" cellpadding="3" class="foreground">
                      <tr class="green"> 
                        <td colspan="7"> 
                            <div align="center"><b>The following boxes are now 
                              empty and need to be removed from their locations.</b></div>
                        </td>
                      </tr>
                      <tr class="yellow"> 
                          
                          <td width="60"> 
                            <div align="left"><b>Box ID:</b></div>
                        </td>
                          <td width="63"> 
                            <div align="left"><b>Location:</b></div>
                        </td>
                          <td width="45"> 
                            <div align="left"><b>Room:</b></div>
                        </td>
                          <td width="89"> 
                            <div align="left"><b>Storage Unit:</b></div>
                        </td>
                          <td width="55"> 
                            <div align="left"><b>Drawer:</b></div>
                        </td>
                          <td width="31"> 
                            <div align="left"><b>Slot:</b></div>
                        </td>
                          
                          <td width="72"> 
                            <div align="left"><b>Retrieved:</b></div>
                        </td>
                          
                      </tr>
                      <% for (int i = 0; i < boxes.size(); i ++) { 
                           Vector temp = (Vector)boxes.get(i);
                           String emptyBoxId = (String)temp.get(0);
                           BTXBoxLocation emptyBoxLoc = (BTXBoxLocation)temp.get(1);
                      %>
                      <tr class="<%= ((i%2)== 0)?"white":"grey"%>"> 
                        <td width="70"> 
                          <div align="center"><%=emptyBoxId%></div>
                        </td>
                        <td width="70"> 
                          <div align="center"><%=emptyBoxLoc.getLocationAddressID()%></div>
                        </td>
                        <td width="70"> 
                          <div align="center"><%=emptyBoxLoc.getRoomID()%></div>
                        </td>
                        <td width="70"> 
                          <div align="center"><%=emptyBoxLoc.getUnitName()%></div>
                        </td>
                        <td width="70"> 
                          <div align="center"><%=emptyBoxLoc.getDrawerID()%></div>
                        </td>
                        <td width="70"> 
                          <div align="center"><%=emptyBoxLoc.getSlotID()%></div>
                        </td>
                        <td width="63"> 
                          <div align="center"> 
                            <input type="checkbox" name="checkbox2" value="checkbox">
                          </div>
                        </td>
                      </tr>
                      <% } %>
                      
                    </table>
			</td>
		</tr>
        </table>
        <%}%>
             
        
    </td>
  </tr>
</table>
</body>
</html>
