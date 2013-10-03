<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="java.util.Vector" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Pathology Pick List</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript">
<!--
function MM_callJS(jsStr) { //v2.0
  return eval(jsStr)
}
<%
String rAndD = (String) request.getAttribute("rd");
if (rAndD == null || rAndD.trim().equals("") || rAndD.trim().equals("null")){%>
myBanner = 'Pathology Pick List';
<%} else { %>
myBanner = 'R & D Pick List';

<%}%>

//-->
</script>
</head>
<script>
<!--

//--!>
</script>
<body  class="bigr" onLoad="if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;">
<table border="0" cellspacing="0" cellpadding="0" align="center" class="background">
  <tr> 
    <td> 
      <table border="0" cellspacing="1" cellpadding="3" align="center" class="foreground">
        <tr class="white"> 
          <td > 
            <table border="0" cellspacing="0" cellpadding="0" class="background" align="center">
              <tr> 
                <td> 
                  <table border="0" cellspacing="1" cellpadding="3" class="foreground" width="382">
                    <tr class="yellow"> 
                      <td colspan="6"> 
                        <div align="center"><font size="4">
				<%if (rAndD == null || rAndD.trim().equals("null") || rAndD.trim().equals("")){%>Pathology Pick List<%} else { %>R & D Pick List<%}%></font></div>
                      </td>
                    </tr>
                    <tr class="white"> 
                      <td class="grey"><b> Deliver To: </b></td>
                      <td> <%= ((request.getParameter("deliverTo")!=null)?request.getParameter("deliverTo"):"") %> </td>
					  <td class="grey"><b> Created By: </b></td>
                      <td> <%= ((request.getParameter("deliverTo")!=null)?request.getAttribute("createdBy"):"") %> </td>
					  <td class="grey"><b> Pick List Number: </b></td>
                      <td> <%= ((request.getAttribute("picklist")!=null)?request.getAttribute("picklist"):"")%> </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>         
            <br>
            <table border="0" cellspacing="0" cellpadding="0" class="background">
              <tr> 
                <td > 
                  <table border="0" cellspacing="1" cellpadding="3" class="foreground">
                    <tr class="yellow"> 
                      <td > 
                        <div align="left"><b>Box ID:</b></div>
                      </td>
                      <td ><b>Room :</b></td>
                      <td ><b>Storage<br>
                        Unit:</b></td>
                      <td > 
                        <div align="left"><b>Drawer:</b></div>
                      </td>
                      <td > 
                        <div align="left"><b>Slot:</b></div>
                      </td>
                      <td > 
                        <div align="left"><b>Cell<br>
                          Ref:</b></div>
                      </td>
                      <td > 
                        <div align="left"><b>Sample</b>:</div>
                      </td>
                      <td > 
                        <div align="left"><b>Case ID:</b></div>
                      </td>
                      <td > 
                        <div align="left"><b>ASM<br>Pos:</b></div>
                      </td>
					  <td > 
                        <div align="left"><b>Tissue:</b></div>
                      </td>
					  <td > 
                        <div align="left"><b>App:</b></div>
                      </td>
                      <td > 
                        <div align="left"><b>Last two<br>slides (QCed?):</b></div>
                      </td>
					  <td > 
                        <div align="left"><b>DI</b></div>
                      </td>
					  <td > 
                        <div align="left"><b>BMS</b></div>
                      </td>
					  <td > 
                        <div align="left"><b>Format<br>Detail</b></div>
                      </td>
                    </tr>
                    <% 
		  Vector samples = (Vector) request.getAttribute("samples");
		  String value = null;
		  %>
                    <% for (int i = 0; i < samples.size(); i ++) { %>
                    <tr class="<%= ((i%2)== 0)?"white":"grey"%>"> 
                      <% 
                      	for (int j = 0; j < 15; j++) { 
                      	  value = (String)((java.util.Vector)samples.get(i)).get(j);
                      	  if (value == null) {
                      		value = "";
                      	  }
                      %>
                      <td > 
                        <div align="center"><%= value%></div>
                      </td>
                      <%}%>
                    </tr>
                    <% } %>
                    <tr class="white" > 
                      <td colspan="15" align="center"> 
                        <input type="button" name="Button" value="Print" onClick="MM_callJS('window.print();')" class="noprint">
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>
