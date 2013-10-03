<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions"%>
<%@ page import="com.ardais.bigr.orm.helpers.BoxScanData"%>
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

function onFormSubmit() {
  disableActionButtons();
  return true;
}
  
function disableActionButtons() {
  var f = document.boxForm;
  f.Submit.disabled = true;
}

myBanner = '<%=request.getParameter("title")%>';
</script>
</head>
<body class="bigr" onload="if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;">
<div align="center"> 
  <form name="boxForm" action="<html:rewrite page='/iltds/Dispatch'/>" method="post"
	    onsubmit="return(onFormSubmit());">
    <table border="0" cellspacing="0" cellpadding="0" class="background">
      <tr> 
        <td> 
          <table border="0" cellspacing="1" cellpadding="3" width="100%" class="foreground">
            <%if((request.getAttribute("myError")!=null)) {%>
            <tr class="green"> 
              <td colspan="2"> 
                <div align="center"><b><font color="#FF0000"><%= ((request.getAttribute("myError")!=null)?request.getAttribute("myError"):"") %> </font> </b></div>
              </td>
            </tr>
			<script>
			alert("<%= request.getAttribute("myError") %>");
			</script>
            <%}%>
            <%
               BoxScanData bsd = (BoxScanData)request.getAttribute("boxScanData");

               String boxLayoutId = request.getParameter("boxLayoutId");
               String defaultBoxLayoutId = bsd.getDefaultBoxLayoutId();

               if (bsd.getNumberOfAccountBoxLayouts() > 1) {
                 defaultBoxLayoutId = (ApiFunctions.isEmpty(boxLayoutId)) ?  defaultBoxLayoutId : boxLayoutId;
            %>
            <tr class="white"> 
              <td class="grey"> 
                <div align="right">Box Layout:&nbsp;</div>
              </td>
              <td>
                <bigr:selectList
                  property="boxLayoutId"
                  value="<%=defaultBoxLayoutId%>"
                  legalValueSet="<%=bsd.getAccountBoxLayoutSet()%>"
                />
              </td>
            </tr>
            <% } else {%>
            <input type="hidden" name="boxLayoutId" value="<%=bsd.getDefaultBoxLayoutId()%>"/>
            <% } %>
            <tr class="white"> 
              <td class="grey">
                <div align="right">Box ID:&nbsp;</div>
              </td>
              <td> 
                <input type="text" name="boxID" size="20" maxlength="12" value="<%= ((request.getParameter("boxID")!=null)?request.getParameter("boxID"):"") %>">
              </td>
            </tr>
            <tr class="white"> 
              <td colspan="2"> 
                <div align="center"> 
                  <input type="submit" name="Submit" value="Continue">
                  <input type="hidden" name="op" value="CheckOutBoxScan">
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
</div>
<script language="JavaScript">
		document.boxForm.boxID.focus()
		</script>
</body>
</html>
