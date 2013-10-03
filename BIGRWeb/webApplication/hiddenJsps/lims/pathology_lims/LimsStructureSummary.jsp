<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<html>
<head>
<title><%= request.getParameter("title") %></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" CONTENT="no-cache">
<meta http-equiv="Pragma" CONTENT="no-cache">
<link rel="stylesheet" type="text/css"
	href="<html:rewrite page="/css/bigr.css"/>">
<script type="text/javascript" src="<html:rewrite page="/js/common.js"/>"> </script>

<script>
<!--
function closeWindow(){
  window.close();
}
</script>

<form name="limsStructures">
<p align="center">
<input type="button" name="Close" value="Close"	onClick="closeWindow();">
</p>
<div align="center">
<table class="fgTableSmall">
	<tr class="green">
		<td>Tissue</td>
		<td>Structure(s)</td>
	</tr>
	<logic:present name="valueSet">
		<%
		  com.gulfstreambio.gboss.GbossValueSet valueSet = (com.gulfstreambio.gboss.GbossValueSet) request.getAttribute("valueSet");			
			java.util.Iterator roots = valueSet.getValues().iterator();
			boolean colorRow = true;
			while (roots.hasNext()){
			  com.gulfstreambio.gboss.GbossValue root = (com.gulfstreambio.gboss.GbossValue) roots.next();

			if(colorRow){
		%>							
			<tr class="grey">
		<%  	colorRow = false;
			} else {
			%>
			<tr class="white">
			<%	colorRow = true;
			}
			%>
				<td>
					<%= root.getDescription() %>
				</td>
				<td>
					<%						
						StringBuffer buff = new StringBuffer(50);
						boolean addComma = false; 
						java.util.Iterator children = root.getValues().iterator();			
						while (children.hasNext()) {
						  com.gulfstreambio.gboss.GbossValue child = (com.gulfstreambio.gboss.GbossValue) children.next();		
							if (addComma) {
								buff.append(", ");
							}
							buff.append(child.getDescription());
							addComma = true;
						}
					%>
					<%= buff.toString() %>
				</td>
			</tr>
		<% } %>
	</logic:present>
</table>
</div>
<p align="center">
<input type="button" name="Close" value="Close"	onClick="closeWindow();">
</p>
</form>
</body>
</html>
