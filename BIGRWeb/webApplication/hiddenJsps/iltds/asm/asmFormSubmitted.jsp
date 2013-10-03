<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script>
function MM_callJS(jsStr) { //v2.0
  return eval(jsStr)
}
myBanner = '<h3><font color="#336699">ASM Form Submitted</font></h3>';
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" onLoad="if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;">
<table width="640" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
      <div align="center">
        <table border="0" cellspacing="0" cellpadding="1" bgcolor="#336699">
          <tr>
    <td>
              <table width="100%" border="0" cellspacing="1" cellpadding="0" bgcolor="#FFFFFF">
                <tr>
                  <td>
                    <div align="center">Results:<%= ((request.getParameter("title")!=null)?request.getParameter("title"):"") %> 
                      <input type="hidden" name="title" value="<%= ((request.getParameter("title")!=null)?request.getParameter("title"):"") %>">
                      <form name="form1" method="post" action="<html:rewrite page='/iltds/Dispatch'/>">
                        <b><font color="#FF0000"><%= ((request.getAttribute("myError")!=null)?request.getAttribute("myError"):"") %> </font> </b> 
                      </form>
                    </div>
                  </td>
                </tr>
              </table>
            </td>
  </tr>
</table>
      </div>
    </td>
  </tr>
</table>
<p>&nbsp;</p>
<p>&nbsp;</p>
</body>
</html>
