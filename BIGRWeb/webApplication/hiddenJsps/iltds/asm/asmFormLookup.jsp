<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ValidateIds" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script>
function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
  
  document.form1.asmID_1.focus();
}

function onFormSubmit() {
  disableActionButtons();
  return true;
}
  
function disableActionButtons() {
	document.form1.Submit.disabled = true;
}

myBanner = 'ASM Form Lookup';
</script>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
</head>
<body class="bigr" onload="initPage();">
      <div align="center"> 
        <form name="form1" method="post" action="<html:rewrite page="/iltds/Dispatch"/>"
              onsubmit="return(onFormSubmit());">
   
          
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
              <td> 
                
          <table class="foreground" cellpadding="3" cellspacing="1" border="0">
            <% if(request.getAttribute("myError")!=null) { %>
				  <tr class="yellow"> 
                    <td colspan="2"> 
                      
                <div align="center"><b><font color="#FF0000"><%= request.getAttribute("myError") %></font></b></div>
                    </td>
                  </tr>
				  <script>
			alert("<%= request.getAttribute("myError") %>");
			</script>
				  <% } %>
                  <tr class="white"> 
                    <td class="grey">ASM Form ID:</td>
                    <td> 
                      <input type="text" name="asmID_1" size="<%= (ValidateIds.LENGTH_ASM_ID + 5)%>" maxlength="<%= ValidateIds.LENGTH_ASM_ID %>" value="<%= ((request.getParameter("asmID_1")!=null)?request.getParameter("asmID_1"):"") %>" >
                    </td>
                  </tr>
                  <tr class="white"> 
                    <td colspan="2"> 
                      <div align="center"> 
                        <input type="hidden" name="op" value="ASMFormLookup">
                        <input type="hidden" name="title" value="ASM Information">
                        <input type="submit" name="Submit" value="Continue">
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
