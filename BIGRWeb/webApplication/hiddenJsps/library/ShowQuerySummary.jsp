<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.library.web.helper.ResultsHelper"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, getServletContext(), "P");
	String txType = null;
	com.ardais.bigr.library.web.form.QueryForm qform = 
		(com.ardais.bigr.library.web.form.QueryForm) request.getAttribute("queryForm");
	if (qform != null) {
		//System.out.println("found query form.  type=" + qform.getTxType());
		txType = qform.getTxType();
	}
	else {
		com.ardais.bigr.library.web.form.ResultsForm rform = 
			(com.ardais.bigr.library.web.form.ResultsForm) request.getAttribute("resultsForm");
		if (rform != null) {
			//System.out.println("found results form.  type=" + rform.getTxType());
			txType = rform.getTxType();
		}
		else {
			txType = request.getParameter("txType");
		}
	}
%>
<bean:define id="helper" name='<%= ResultsHelper.KEY + txType %>' type="com.ardais.bigr.library.web.helper.ResultsHelper"/>

<html>

<head>
<title>Sample Selection Query Summary</title>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<!--link rel="stylesheet" type="text/css" href="w:/BIGRWeb/webApplication/css/bigr.css"/-->

<script language="JavaScript">

</script>
</head>

<body class="bigr">

<div align="center">
	<table border="0" cellspacing="0" cellpadding="0" class="fgTableSmall">
		<tr><td align="center" class="green"> <b><big> Query Results </big></b> </td> </tr>
		<tr>
			<td align="center" colspan="1" class="yellow"><b>Frozen/Paraffin Tissue Summary</b></td>
		</tr>
		<!-- summary prints its own rows inside a 3-column table -->
		<logic:present name="helper" property="tissueQuerySummary">
		<bigr:indentedText name="helper" property="tissueQuerySummary" filter="true"/>
		</logic:present>
		<logic:present name="helper" property="rnaQuerySummary">
      <logic:notEqual name="helper" property="rnaQuerySummary" value="<%=ResultsHelper.PRODUCT_QUERY_NA%>">
    		<tr>
		    	<td align="center" colspan="1" class="yellow"><b>RNA Query Summary</b></td>
		    </tr>
		    <!-- summary prints its own rows inside a 3-column table -->
		    <bigr:indentedText name="helper" property="rnaQuerySummary" filter="true"/>
		  </logic:notEqual>
		</logic:present>
	</table>
</div>
<p/>
<div align="center">
	<input type="button" value="Close" onclick="window.close();"> 
</div>
</body>
</html>

