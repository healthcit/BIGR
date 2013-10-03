<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ValidateIds" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>

<html>
<head>
<title>ASM Form Initial Link</title>
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

myBanner = 'ASM Form Initial Link';
</script>

<link rel="stylesheet" href="<html:rewrite page="/css/bigr.css"/>" type="text/css">
</head>
<body class="bigr" onload="initPage()">

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
                <div align="center"><font color="#FF0000"><b><%= request.getAttribute("myError") %></b></font></div>
                    </td>
                  </tr>
				  <script>
			alert("<%= request.getAttribute("myError") %>");
			</script>
				  <% } %>
                  <tr class="white"> 
                    <td class="grey">ASM Form ID:</td>
                    <td> 
                      <input type="text" name="asmID_1" size="<%= (ValidateIds.LENGTH_ASM_ID + 5)%>" maxlength="<%= ValidateIds.LENGTH_ASM_ID %>" value="<%= ((request.getParameter("asmID_1")!=null)?request.getParameter("asmID_1"):"") %>">
                    </td>
                  </tr>
                  <tr class="white"> 
                    <td class="grey">ASM Form ID(confirm):</td>
                    <td> 
                      <input type="text" name="asmID_2" size="<%= (ValidateIds.LENGTH_ASM_ID + 5)%>" maxlength="<%= ValidateIds.LENGTH_ASM_ID %>" value="<%= ((request.getParameter("asmID_2")!=null)?request.getParameter("asmID_2"):"") %>">
                    </td>
                  </tr>
                  <tr class="white"> 
                    <td class="grey">Case ID:</td>
                    <td> 
                      <input type="text" name="consentID_1" size="<%= (ValidateIds.LENGTH_CASE_ID + 5)%>" maxlength="<%= ValidateIds.LENGTH_CASE_ID %>" value="<%= ((request.getParameter("consentID_1")!=null)?request.getParameter("consentID_1"):"") %>">
                    </td>
                  </tr>
                  <tr class="white"> 
                    <td class="grey">Case ID(confirm):</td>
                    <td> 
                      <input type="text" name="consentID_2" size="<%= (ValidateIds.LENGTH_CASE_ID + 5)%>" maxlength="<%= ValidateIds.LENGTH_CASE_ID %>" value="<%= ((request.getParameter("consentID_2")!=null)?request.getParameter("consentID_2"):"") %>">
                    </td>
                  </tr>
                  <tr class="white"> 
                    <td colspan="2"> 
                      <div align="center">
                        <input type="hidden" name="op" value="ASMFormConsentLink">
                        
                        <input type="submit" name="Submit" value="Confirm">
                      </div>
                    </td>
                  </tr>
                </table>
</td>
          </tr>
        </table></form>
      </div>
</body>
</html>
