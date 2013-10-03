<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script>
function MM_callJS(jsStr) { //v2.0
  return eval(jsStr)
}
myBanner = 'Update Box Location';
</script>
</head>
<body class="bigr" onLoad="if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;">
      <div align="center">
        <table border="0" cellspacing="0" cellpadding="0" class="background">
          <tr>
    <td>
              <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
                <tr class="white">
                  <td>
                    <div align="center">
                      
                        The Box Location was successfully updated.
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