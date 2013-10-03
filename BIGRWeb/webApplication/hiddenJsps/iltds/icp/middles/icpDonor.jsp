<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.library.web.helper.ResultsHelper" %> 
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>

<bean:define id="donorBean" name="icpData" property="data"
  type="com.ardais.bigr.pdc.javabeans.DonorData"/>

<script type="text/javascript">

function reloadTable(resultsFormDefinitionId) { 		
  this.focus();
  if (resultsFormDefinitionId != null) {
		document.all.wholePage.style.display = 'none';
		document.all.waitMessage.style.display = 'block';
    var URL = '<html:rewrite page="/iltds/Dispatch"/>'
      + "?op=IcpQuery&id=<bean:write name="donorBean" property="ardaisId"/>";
    URL = URL + "&resultsFormDefinitionId=" + resultsFormDefinitionId;
    window.location.href = URL;
  }
}

function icpDonorPositionDetails(detailsContainerId) {
  <%-- Allow the sample details to take about at most a bit less than a
    ** half of the vertical space left on the screen that's not taken up by
    ** the header section.  The "diff" variable is the space needed for
    ** scrollbars, borders, etc outside the content itself --%>
  var e = document.all[detailsContainerId];
  var diff = e.offsetHeight - e.clientHeight;
  return Math.min(diff + e.scrollHeight,
    Math.max(125,
      ((document.body.clientHeight - document.all.icpMiddle.offsetTop) / 2) - 50));
}

function icpDonorViewElementsStartedCallback(detailsContainer) {
  var detailsContainerId = detailsContainer.id;
  detailsContainer.style.setExpression('pixelHeight',
    'icpDonorPositionDetails(\'' + detailsContainerId + '\')');
  document.recalc(true);
}
</script>

<jsp:include page="/hiddenJsps/library/TissueDetailTable.jsp" flush="true">
  <jsp:param name="includeItemSelector" value="false"/>
  <jsp:param name="noSampleMessage" value="<div align='center'><br><b>No sample information is available.</b></div>"/>
  <jsp:param name="includeSelectViewLink" value="true"/>
  <jsp:param name="itemViewElementsStartedCallback" value="icpDonorViewElementsStartedCallback"/>
  <jsp:param name="txType" value="<%=ResultsHelper.TX_TYPE_ICP%>"/>
</jsp:include>
<br/>
