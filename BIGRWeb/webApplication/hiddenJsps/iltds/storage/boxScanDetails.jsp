<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.javabeans.BoxLayoutDto"%>
<%@ page import="java.util.Vector" %>
<%@ page import="com.ardais.bigr.iltds.btx.BTXBoxLocation" %>
<%@ taglib uri="/tld/struts-html" prefix="html"%>
<%@ taglib uri="/tld/bigr" prefix="bigr"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
String boxId = request.getParameter("boxID");
if (boxId == null) {
  boxId = "";
}
BoxLayoutDto boxLayoutDto = (BoxLayoutDto)request.getAttribute("boxLayoutDto");
boolean showEmptyBox = "true".equals(request.getParameter("empty"));
if (!showEmptyBox) {
  showEmptyBox = "true".equals((String)request.getAttribute("empty"));
}
String cellType = showEmptyBox ? "empty" : "mixed";
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script>
function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
	
	// Set focus to the first input text box.  Since the box may be mixed with
	// read-only cells and input cells, we can't assume that 'sampl1' is an
	// input, so go through all inputs and find the one with the lowest tabindex
	// and set focus to it.
	var box = document.getElementById('theBox');
	var inputs = box.getElementsByTagName("input");
	var numInputs = inputs.length;
	var focusInput = null;
	var lowestTab = 0;
	for (var i = 0; i < numInputs; i++) {
	  var input = inputs[i];
	  var t = input.getAttribute('type');
	  var n = input.getAttribute('name');
	  var ti = input.getAttribute('tabIndex');
	  if (t && n && ti && (t.toUpperCase() == 'TEXT') && (n.substring(0,4) == 'smpl')) {
	    var tab = parseInt(ti, 10);
	    if ((tab != NaN) && (tab > 0)) {
	      if (!focusInput || (tab < lowestTab)) {
		      focusInput = input;
		      lowestTab = tab;
	      }
	    }
	  }
	}
	if (focusInput) {
	  focusInput.focus();
	}
}

function onFormSubmit() {
  disableActionButtons();
  return true;
}
  
function disableActionButtons() {
	document.form1.Submit.disabled = true;
	document.form1.Clear.disabled = true;
	document.form1.Rescan.disabled = true;
}

myBanner = 'Box Scan Details';
</script>
</head>
<body class="bigr" onLoad="initPage();">
<form name="form1" action="<html:rewrite page='/iltds/Dispatch'/>" method="POST"
	  onsubmit="return(onFormSubmit());">
  <input type="hidden" name="boxID" value="<%=boxId%>">
  <input type="hidden" name="boxLayoutId" value="<%=boxLayoutDto.getBoxLayoutId()%>"/>
  <input type="hidden" name="op" value="BoxScanDetails">
  <input type="hidden" name="title" value="<%=request.getParameter("title")%>">
  <div id="theBox" align="center">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" cellpadding="3" cellspacing="1" border="0">
            <% if(request.getAttribute("myError")!=null) { %>
            <tr class="yellow"> 
              <td colspan="<%=boxLayoutDto.getNumberOfColumns()+1%>"> 
                <div align="center"><font color="#FF0000"><b><%= request.getAttribute("myError")%> </b></font></div>
              </td>
            </tr>
			<script>
			//alert("<%= request.getAttribute("myError") %>");
			</script>
            <% } %>
            <tr class="white"> 
              <td class="grey"><b>Box&nbsp;ID:</b></td>
              <td colspan="<%=boxLayoutDto.getNumberOfColumns()%>"><b><%=boxId%> 
                </b></td>
            </tr>
            <bigr:box
              boxLayoutDto="<%=boxLayoutDto%>"
              boxCellContentVar="smpl"
              sampleTypeVar="smplType"
              sampleAliasVar="smplAlias"
              cellType="<%=cellType%>"
              inputMaxLength="30"
              inputSize="20"
            />
            <tr class="white"> 
              <td colspan="<%=boxLayoutDto.getNumberOfColumns()+1%>"> 
                <div align="center"> 
                  <input type="submit" name="Submit" value="Confirm">
                <% 
                	String rescanUrl = "/iltds/Dispatch?op=BoxScanInitial";
              	  rescanUrl = rescanUrl + "&boxID=" + boxId;
              	  rescanUrl = rescanUrl + "&boxLayoutId=" + boxLayoutDto.getBoxLayoutId();
                	rescanUrl = rescanUrl + "&title=Box%20Scan%20Details"; 
                	rescanUrl = rescanUrl + "&confirm=false"; 
                	String clearUrl = rescanUrl + "&empty=true";
                %>
          				<input type="button" name="Clear" value="Clear All"
                   onClick="window.location.href = '<html:rewrite page="<%=clearUrl%>"/>';">
          				<input type="button" name="Rescan" value="Re-Scan New Samples"
                   onClick="window.location.href = '<html:rewrite page="<%=rescanUrl%>"/>';">
                </div>
              </td>
            </tr>
          </table>
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
  </div>
</form>
</body>
</html>
