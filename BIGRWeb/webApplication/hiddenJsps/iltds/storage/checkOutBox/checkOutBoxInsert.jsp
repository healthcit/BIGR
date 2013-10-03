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
<script>
function MM_callJS(jsStr) { //v2.0
  return eval(jsStr)
}
myBanner = 'Checkout Completed';
</script>
</head>
<body class="bigr" onLoad="if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;">
<form name="consentRelease" method="post">
<table border="0" cellspacing="0" cellpadding="0" align="center" class="background">
  <tr> 
    <td> 
          <table border="0" cellspacing="1" cellpadding="3" class="foreground">
            <%if(request.getAttribute("myError")!=null){%>
			<tr class="green">               
            <td> 
              <div align="center"><font color="#FF0000"><b><%= ((request.getAttribute("myError")!=null)?request.getAttribute("myError"):"") %></b></font></div>
                    </td>
                  </tr>
				  <script>
			alert("<%= request.getAttribute("myError") %>");
			</script>
				  <%} else {%>
                  <tr class="white"> 
                    
            <td> 
              <div align="center">
                <p> 
                  <%String tx = request.getParameter("tx");%>
                  <b> Box <%= ((request.getParameter("boxID")!=null)?request.getParameter("boxID"):"") %> and its samples have been checked out of the system 
                  <%if( (tx != null) && (tx.equals("MvPath"))) {%>
                  and can be moved to Pathology. 
                  <%}%>
                  <br>
                  Reason Code: <%=(String)request.getAttribute("description")%> <br>
                  <br>
                  <% if (request.getAttribute("commentLength") != null && !request.getAttribute("commentLength").equals("")){%>
                  <script>alert("<%= request.getAttribute("commentLength")%>");</script>
                  <%= request.getAttribute("commentLength")%> 
                  <%}%>
                  </b> </p>
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
                      <tr class="white"> 
                        <td colspan="10"> 
                          <div align="center"> 
                            <input type="button" name="Button" value="Print" onClick="MM_callJS('window.print();')" class="noprint">
                          </div>
                        </td>
                      </tr>
                    </table>
			</td>
		</tr>
        </table>
        <%}%>
              </div>
            </td>
            </tr><%}%>
          </table>
    </td>
  </tr>
</table>
</form>
</body>
</html>