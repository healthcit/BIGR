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
  document.passwordAnswer.verificationAnswer.focus();
}

function taLimit() {
	var taObj=event.srcElement;
	if (taObj.value.length==taObj.maxLength*1 || (event.keyCode==13 && taObj.value.length==(taObj.maxLength*1)-1)) return false;
}

function taCount(visCnt) { 
	var taObj=event.srcElement;
	if (taObj.value.length>taObj.maxLength*1) taObj.value=taObj.value.substring(0,taObj.maxLength*1);
	if (visCnt) visCnt.innerText=taObj.maxLength-taObj.value.length;
}

function assignValueToOp(param)
{
  if (param == "ok") {
			if(checkAnswerLength()) {
				document.passwordAnswer.op.value = "ForgotPasswordAnswer";
				document.passwordAnswer.submit();
			}
  }
}

function checkAnswerLength() {
		if(document.passwordAnswer.verificationAnswer.value.length > 100)
		{
			
			alert("Invalid entry!  Answer can only have hundred characters!");
			document.passwordAnswer.verificationAnswer.focus();
			
   			return false;
		}
		else
		{
			return true;
		}
	}
//-->
</script>
</head>
<body class="bigr" onload="initPage();">
<form name="passwordAnswer" method="post" action="<html:rewrite page='/orm/DispatchLogin'/>"> 
<div align="center">
  <table border="0" cellspacing="0" cellpadding="0" class="background" width="400">
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
              <b>Please answer your security verification question.  If you
                 answer it correctly, a new password will be emailed to you.</b>
            </td>
          </tr>
         
                       
					  <input type="hidden" name="AccountID" value="<%= ((request.getAttribute("AccountID")!=null)?request.getAttribute("AccountID"):"") %>">
					   <input type="hidden" name="UserID" value="<%= ((request.getAttribute("UserID")!=null)?request.getAttribute("UserID"):"") %>">
                        <tr class="white"> 
                          <td class="grey" width="48%" nowrap> 
                            <div align="right"><b>Verification Question:</b></div>
							<input type="hidden" name="verificationQuestion" value ="<%= ((request.getAttribute("VerificationQuestion")!=null)?request.getAttribute("VerificationQuestion"):"") %> ">
                          </td>
                          <td width="52%"><%= ((request.getAttribute("VerificationQuestion")!=null)?request.getAttribute("VerificationQuestion"):"") %> </td>
                        </tr>
                        <tr class="white"> 
                          <td class="grey" width="48%"> 
                            <div align="right"><b>Verification Answer:</b></div>
                          </td>
                          <td width="52%"> 
                            <textarea name="verificationAnswer" rows="4" cols="25"  maxLength=100 onKeyPress="return taLimit()" onKeyUp="return taCount()"></textarea>
                          </td>
                        </tr>
                        <tr class="white"> 
                          <td colspan="2"> 
                            <div align="center"> 
                              <input type="hidden" name="op" >
                              <input type="button" name="Submit" value="  Ok  " onclick="assignValueToOp('ok')">
                              <input type="button" name="Submit" value="Cancel" onclick="closeThis()">
                            </div>
                       
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>
</form>
</body>
</html>
