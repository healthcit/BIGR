<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>

<html>
<head>
<title>ASM Form Information Submitted</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script>
var myBanner = 'ASM Form Information Submitted';

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
  
  document.form1.Submit.focus();
}

function onFormSubmit() {
  disableActionButtons();
  return true;
}
  
function disableActionButtons() {
  document.form1.Submit.disabled = true;
  document.form2.Submit.disabled = true;
}
</script>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
</head>
<body class="bigr" onload="initPage();">
<div align="center"> 
  <table class="background" cellpadding="0" cellspacing="0" border="0">
    <tr> 
      <td> 
        <table class="foreground" cellpadding="3" cellspacing="1" border="0">
          <tr class="yellow"> 
            <td> 
              <div align="center">Results: </div>
            </td>
          </tr>
          <form name="form1" method="post" action="<html:rewrite page="/iltds/Dispatch"/>"
              onsubmit="return(onFormSubmit());">
            <% if(request.getAttribute("myError")!=null) { %>
            <tr class="green"> 
              <td> 
                <div align="center"><font color="#FF0000"><b><%= request.getAttribute("myError") %> </b></font></div>
              </td>
            </tr>
            <% } %>
            <tr class="white"> 
              <td> 
                <div align="center"> 
                  <input type="hidden" name="asmID_1" value="<%= ((request.getParameter("asmFormID")!=null)?request.getParameter("asmFormID"):"") %>">
                  <input type="hidden" name="op" value="ASMFormLookup">
                  <input type="hidden" name="title" value="ASM Form Lookup">
                  <input type="submit" name="Submit" value="Return to ASM Form Information">
                </div>
              </td>
            </tr>
          </form>
          <form name="form2" method="post" action="<html:rewrite page='/iltds/Dispatch'/>"
              onsubmit="return(onFormSubmit());">
            <tr class="white"> 
              <td> 
                <div align="center"> 
                  <input type="submit" name="Submit" value="Return to ASM Form Lookup">
                  <input type="hidden" name="op" value="ASMStartLookup">
                </div>
              </td>
            </tr>
          </form>
        </table>
      </td>
    </tr>
  </table>
</div>
</body>
</html>
