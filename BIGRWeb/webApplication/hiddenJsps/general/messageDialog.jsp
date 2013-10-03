<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"N"); // "N" => user not required to be logged in
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<html>
<head>
<title>Message</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<html:rewrite page="/css/bigr.css"/>" type="text/css">
<script type="text/javascript">
function initPage() {
    var initialDialogHeight = 100;
    window.dialogHeight = initialDialogHeight + "px";
    var msg = document.all.message;
    msg.innerHTML = dialogArguments;
    var windowHeight = document.body.clientHeight;
    var contentHeight = document.body.scrollHeight;
    var newDialogHeight = initialDialogHeight + (contentHeight - windowHeight);
    if (newDialogHeight > (screen.availHeight - 10)) {
        newDialogHeight = screen.availHeight - 10;
    }
    var newDialogTop = (screen.availHeight - newDialogHeight) / 2;
    window.dialogTop =  newDialogTop + "px";
    window.dialogHeight = newDialogHeight + "px";
    document.all.btnOK.focus();
}
</script>
</head>
<body onload="initPage();" bgcolor="lightyellow">
<div align="center" style="padding: 10px;">
<bgsound src="<html:rewrite page="/media/chord.wav"/>"/>
<table border="0"><tr><td id="message"></td></tr></table>
<input type="button" id="btnOK" value="     OK     "
       onclick="window.close();"/>
</div>
</body>
