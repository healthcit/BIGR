<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.library.web.helper.ResultsHelper" %> 
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%@ taglib uri="/tld/datetime" prefix="dt" %>

<bean:define id="icpData" name="icpData"
  type="com.ardais.bigr.iltds.databeans.IcpData" />
<bean:define id="sampleBean" name="icpData" property="data"
  type="com.ardais.bigr.iltds.databeans.SampleData"/>
  
<script type="text/javascript">
	function reloadTable(resultsFormDefinitionId) { 		
    this.focus();
    if (resultsFormDefinitionId != null) {
			document.all.wholePage.style.display = 'none';
			document.all.waitMessage.style.display = 'block';
	    var URL = '<html:rewrite page="/iltds/Dispatch"/>'
	      + "?op=IcpQuery&id=<bean:write name="sampleBean" property="sample_id"/>";
	    URL = URL + "&resultsFormDefinitionId=" + resultsFormDefinitionId;
	    window.location.href = URL;
    }
	}
</script>

<jsp:include page="/hiddenJsps/library/TissueDetailTable.jsp" flush="true">
  <jsp:param name="includeItemSelector" value="false"/>
  <jsp:param name="noSampleMessage" value="<div align='center'><br><b>No sample information is available.</b></div>"/>
  <jsp:param name="includeSelectViewLink" value="true"/>
  <jsp:param name="itemViewElementsStartedCallback" value="none"/>
  <jsp:param name="txType" value="<%=ResultsHelper.TX_TYPE_ICP%>"/>
</jsp:include>
<br/>
