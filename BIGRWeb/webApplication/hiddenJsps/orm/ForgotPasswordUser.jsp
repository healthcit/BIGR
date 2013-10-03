<%@ taglib uri="/tld/struts-html" prefix="html" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Forgot Password</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript">
<!--
function closeThis() {
	window.close();
}

function initPage() {
	window.focus();
	document.getQuestionPass.UserID.focus();
}

function assignValueToOp(param) {
	if (param == "GetQuestion") {
		document.getQuestionPass.op.value = "GetPasswordQuestion";
		document.getQuestionPass.submit();
	}
}
//-->
</script>
</head>
<body class="bigr" onload="initPage();">
<form name="getQuestionPass" method="post" action="<html:rewrite page='/orm/DispatchLogin'/>">
<input type="hidden" name="op">
<div align="center">
  <table border="0" cellspacing="0" cellpadding="0" class="background" width="350">
    <tr> 
      <td> 
        <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
          <% if(request.getAttribute("myError") != null) { %>
          <tr class="green"> 
            <td colspan="2"> 
              <div align="center"><font color="#FF0000"><%= request.getAttribute("myError") %></font></div>
            </td>
          </tr>
          <% } %>
          <tr class="white"> 
            <td colspan="2" align="center"> 
              <b>If you have created a security question and answer in your user profile,
               please enter your User and Account ID and click on Get Question to 
               proceed.  You will be asked to answer your security question.
                 If you answer it correctly, a new password will be 
                 immediately emailed to you.</b>
            </td>
          </tr>
          
                      
                      
                        <tr class="white"> 
                          <td class="grey"> 
                            <div align="right"><b>User ID:</b></div>
                          </td>
                          <td> 
                            <input type="text" name="UserID" size="15" maxlength="12" value="<%= ((request.getParameter("UserID")!=null)?request.getParameter("UserID"):"")%>" onChange="javascript:while(''+this.value.charAt(0)==' ')this.value=this.value.substring(1,this.value.length);">
                          </td>
                        </tr>
                        <tr class="white"> 
                          <td class="grey"> 
                            <div align="right"><b>Account ID:</b></div>
                          </td>
                          <td> 
                            <input type="text" name="AccountID" size="15" maxlength="10" value="<%= ((request.getParameter("AccountID")!=null)?request.getParameter("AccountID"):"") %>" onChange="javascript:while(''+this.value.charAt(0)==' ')this.value=this.value.substring(1,this.value.length);">
                     
              
            </td>
          </tr>
		  <tr class="green">
           <td colspan="2">
       
		<div align = "center">
        <input type="button" name="Submit" value="Get Question" onclick="assignValueToOp('GetQuestion')">
		<input type="button" name="Submit" value="Cancel" onclick="closeThis()"><br>
                  For assistance, please call HealthCare IT Corporation Customer Service at 
                  866-989-0035<br>
				  <% String custservVal = com.ardais.bigr.api.ApiProperties.getProperty("api.custserv.email"); %>
                  <a href="mailto:<%=custservVal%>">Customer Service</a> 
                </div>
		</td></tr>
		 </table>
      </td>
    </tr>
  </table>
</div>
</form>
</body>
</html>
