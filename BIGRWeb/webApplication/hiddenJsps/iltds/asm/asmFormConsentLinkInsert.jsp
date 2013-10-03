<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>

<html>
<head>
<title>ASM Form Linked</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript">
<!--
function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
  
  document.form1.Submit.focus();
}

function onClickSubmit2() {
  disableActionButtons();
  window.location = '<html:rewrite page="/iltds/Dispatch?op=ASMStartLink"/>';
}

function onFormSubmit() {
  disableActionButtons();
  return true;
}
  
function disableActionButtons() {
	document.form1.Submit.disabled = true;
	document.form1.Submit2.disabled = true;
}

myBanner = 'ASM Form Linked';
//-->
</script>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
</head>
<body class="bigr" onload="initPage()">
<div align="center"> 
  <table class="background" cellpadding="0" cellspacing="0" border="0">
    <tr> 
      <td> 
        <table class="foreground" cellpadding="3" cellspacing="1" border="0">
          <% if(request.getAttribute("myError")!=null) { %>
          <tr class="yellow"> 
            <td> 
              <div align="center"><b><font color="#FF0000"><%= ((request.getAttribute("myError")!=null)?request.getAttribute("myError"):"") %></font></b></div>
            </td>
          </tr>
          <% } %>
          <%if (request.getAttribute("myError") == null){%>
          <tr class="white"> 
            <td> 
              <div align="center">ASM Form ID <%= ((request.getParameter("asmID_1")!=null)?request.getParameter("asmID_1"):"") %> has been linked to Case ID <%= ((request.getParameter("consentID_1")!=null)?request.getParameter("consentID_1"):"") %>.</div>
            </td>
          </tr>
          <%}%>
          <tr class="white"> 
            <td> 
              <div align="center"> 
                <form name="form1" method="post" action="<html:rewrite page="/iltds/Dispatch"/>"
                      onsubmit="return(onFormSubmit());">
                  <input type="hidden" name="title" value="ASM Form Information">
                  <input type="hidden" name="op" value="ASMFormLookup">
                  <input type="hidden" name="consentID_1" value="<%= ((request.getParameter("consentID_1")!=null)?request.getParameter("consentID_1"):"") %>">
                  <input type="hidden" name="asmID_1" value="<%= ((request.getParameter("asmID_1")!=null)?request.getParameter("asmID_1"):"") %>">
                  <%if (request.getAttribute("myError") == null){%>
                  <input type="submit" name="Submit" value="Enter Info">
                  <input type="button" name="Submit2" value="Return to ASM Initial Link"
                         onClick="onClickSubmit2(); return document.MM_returnValue;">
                  <%}%>
                </form>
              </div>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>
</body>
</html>
