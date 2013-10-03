<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>

<html>
<head>
<title>Confirm ASM Link Information</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script type="text/javascript">
function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
  
  document.form1.Submit.focus();
}

function onClickCancel() {
  disableActionButtons();
  window.location = '<html:rewrite page="/iltds/Dispatch?op=ASMStartLink"/>';
}

function onFormSubmit() {
  disableActionButtons();
  return true;
}
  
function disableActionButtons() {
	document.form1.Submit.disabled = true;
	document.form1.Cancel.disabled = true;
}

myBanner = 'Confirm ASM Link Information';
</script>
</head>
<body class="bigr" onload="initPage()">
<div align="center"> 
  <form name="form1" method="post" action="<html:rewrite page="/iltds/Dispatch"/>"
        onsubmit="return(onFormSubmit());">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" cellpadding="3" cellspacing="1" border="0">
            <% if(request.getParameter("myError")!=null) { %>
            <tr class="yellow"> 
              <td colspan="2"> 
                <div align="center"><b><font color="#FF0000"><%= ((request.getParameter("myError")!=null)?request.getParameter("myError"):"") %></font></b></div>
              </td>
            </tr>
			<script>
			alert("<%= request.getAttribute("myError") %>");
			</script>
            <% } %>
			<% if(request.getAttribute("pulled")!=null) { %>
            <tr class="yellow"> 
              <td colspan="2"> 
                <div align="center"><b><font color="#FF0000"><%= ((request.getAttribute("pulled")!=null)?request.getAttribute("pulled"):"") %></font></b></div>
              </td>
            </tr>
			<script>
			alert("<%= request.getAttribute("pulled") %>");
			</script>
            <% } %>
            <tr class="white"> 
              <td class="grey">ASM Form ID:</td>
              <td><%= ((request.getParameter("asmID_1")!=null)?request.getParameter("asmID_1"):"") %></td>
            </tr>
            <tr class="white"> 
              <td class="grey">
                <p>Case ID:</p>
                </td>
              <td><%= ((request.getParameter("consentID_1")!=null)?request.getParameter("consentID_1"):"") %> </td>
            </tr>
            <tr class="white"> 
              <td colspan="2"> 
                <div align="center"> 
                  <input type="hidden" name="op" value="ASMFormConsentLinkInsert">
                  <input type="hidden" name="asmID_1" value="<%= ((request.getParameter("asmID_1")!=null)?request.getParameter("asmID_1"):"") %>">
                  <input type="hidden" name="consentID_1" value="<%= ((request.getParameter("consentID_1")!=null)?request.getParameter("consentID_1"):"") %>">
                  <input type="hidden" name="myError" value="Successful">
                  <input type="submit" name="Submit" value="Submit">
  				  <input type="button" name="Cancel" value="Cancel" onclick="onClickCancel(); return document.MM_returnValue;">
                </div>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </form>
</div>
</body>
</html>
