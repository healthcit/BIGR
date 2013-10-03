<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<html>
<head>
<title>OceBlank.html</title>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<script language="JavaScript">
<!--
	function validateForm()
	{
	 alert('No updates have been specified.');
	}
//-->
</script>
</head>
<body>
<div align="center">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" border="0" cellpadding="3" cellspacing="1">
            <bean:write name="helper" property="messages" filter="false"/>
          </table>
        </td>
      </tr>
    </table>
  </div>
  <p>
<div align="center">
<table class="background" cellpadding="0" cellspacing="0" border="0"
	width="100%">
	<tr>
		<td>
		<table class="foreground" cellpadding="3" cellspacing="1" border="0"
			width="100%">
			<tr class="white">
				<td colspan="5">
				<div align="center"><b><font color="#FF0000">Select parameters to
				search other codes. </font></b></div>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</div>
</body>
</html>
