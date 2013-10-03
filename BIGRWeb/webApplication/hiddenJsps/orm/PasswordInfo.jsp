<%@ taglib uri="/tld/struts-html" prefix="html" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Password Changed</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script type="text/javascript">
<!--
function initPage() {
	window.focus();
	document.all.Close.focus();
}
//-->
</script>
</head>
<body class="bigr" onload="initPage();">
<div align="center">
  <table border="0" cellspacing="0" cellpadding="0" class="background" width="400">
    <tr> 
      <td> 
        <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
          <% if(request.getAttribute("myError") != null) { %>
          <tr class="green"> 
            <td align="center"> 
              <font color="#FF0000"><%= request.getAttribute("myError") %></font>
            </td>
          </tr>
          <% } %>
          <tr class="white"> 
            <td align="center"> 
              <b>Your new password has been mailed to you.</b>
            </td>
          </tr>
          <tr class="white"> 
            <td align="center"> 
              <input type="button" name="Close" value="Close" onclick="window.close();">
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>
</body>
</html>
