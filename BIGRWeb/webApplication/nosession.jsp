<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%
    com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"N");

    String timeOutError = (String)request.getAttribute("TimeOutError");
    if (timeOutError == null) {
        timeOutError = (String)request.getParameter("TimeOutError");
    }
    if (timeOutError == null) {
        timeOutError = "Y";
    }
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Session Check</title>
<script type="text/javascript">

function initPage() {
    window.open("/BIGR/login.jsp?TimeOutError=<%= timeOutError %>", "_top");	
}

</script>
</head>
<body class="bigr" onload="initPage();">
</body>
</html>
