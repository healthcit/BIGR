<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.javabeans.BoxLayoutDto"%>
<%@ taglib uri="/tld/struts-html" prefix="html"%>
<%@ taglib uri="/tld/bigr" prefix="bigr"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script>

<%
if (request.getAttribute("confirm") == null)
	request.setAttribute("confirm", "first");
%>

function onFormSubmit() {
  disableActionButtons();
  return true;
}
  
function disableActionButtons() {
  var f = document.form1;
  f.Submit.disabled = true;
}

<%if(request.getParameter("tx").equals("MvPath")) {%>
	myBanner = 'Move Samples to Pathology - Box Scan Details';
<%} else if(request.getParameter("tx").equals("CoInv")) {%>
	myBanner = 'Check Samples Out of Inventory - Box Scan Details';
<%} else {%>
	myBanner = 'Box Scan Details';
<%}%>
</script>
</head>
<body class="bigr" onLoad="if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;">
<form name="form1" action="<html:rewrite page='/iltds/Dispatch'/>" method="post"
	  onsubmit="return(onFormSubmit());">
  <table border="0" cellspacing="0" cellpadding="0" class="background" align="center">
    <tr> 
      <td> 
        <% BoxLayoutDto boxLayoutDto = (BoxLayoutDto)request.getAttribute("boxLayoutDto");%>
        <table border="0" cellspacing="1" cellpadding="3" class="foreground">
          <%if(request.getAttribute("myError")!=null){%>
          <tr class="yellow"> 
            <td align="center" colspan="<%=boxLayoutDto.getNumberOfColumns()+1%>"> <b><font color="red"><%= ((request.getAttribute("myError")!=null)?request.getAttribute("myError"):"") %> </font> </b></td>
          </tr>
		  <script>
			//alert("<%= request.getAttribute("myError") %>");
			</script>
          <%}%>
          <tr class="white"> 
            <td class="grey">Box ID:</td>
            <td colspan="<%=boxLayoutDto.getNumberOfColumns()%>"><b><%= ((request.getParameter("boxID")!=null)?request.getParameter("boxID"):"") %></b> 
              <input type="hidden" name="boxID" value="<%= ((request.getParameter("boxID")!=null)?request.getParameter("boxID"):"") %>">
            </td>
          </tr>
          <bigr:box
            boxLayoutDto="<%=boxLayoutDto%>"
            boxCellContentVar="smpl"
            cellType="empty"
            inputMaxLength="30"
            inputSize="20"
          />
          <input type="hidden" name="boxLayoutId" value="<%=boxLayoutDto.getBoxLayoutId()%>"/>
          <tr class="white"> 
            <td colspan="<%=boxLayoutDto.getNumberOfColumns()+1%>"> 
              <div align="center"> 
                <input type="hidden" value="CheckOutBoxConfirm" name="op">
                <input type="submit" name="Submit" value="Confirm">
                <input type="hidden" name="tx" value="<%=((request.getParameter("tx")!=null)?request.getParameter("tx"):"")%>">
                <input type="hidden" name="location" value="<%=((request.getParameter("location")!=null)?request.getParameter("location"):"")%>">
				<input type="hidden" name="title" value="<%=request.getParameter("title")%>">
              </div>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</form>
<script language="JavaScript">
		document.form1.smpl1.focus()
		</script>
</body>
</html>