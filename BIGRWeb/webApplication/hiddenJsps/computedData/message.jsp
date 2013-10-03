<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%
   com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"N"); // "N" => user not required to be logged in
%>

<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Update Computed Data</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<body class="bigr">

<TABLE>
     <table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
      <logic:present name="org.apache.struts.action.ERROR">
	   <tr class="yellow"> 
	     <td colspan="10"> 
	       <div align="center">
	         <font color="#FF0000"><b><html:errors/></b></font>
	       </div>
	     </td>
	    </tr>
	   </logic:present>
	  </table>
		<TR>
			<TD>
				<%= request.getAttribute("count") %> <%= request.getAttribute("objectType").toString().toLowerCase() %>(s) successfully updated.
			</TD>
		</TR>
</TABLE>
</body>
</html>
