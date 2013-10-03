<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ardais.bigr.api.ApiException"%>
<%@ page import="com.ardais.bigr.security.*"%>
<%@ page import="com.ardais.bigr.javabeans.*"%>
<%@ page import="com.ardais.bigr.library.javabeans.*"%>
<%@ page import="com.ardais.bigr.library.web.helper.*"%>
<%@ page import="com.ardais.bigr.query.*"%>
<%@ page import="com.ardais.bigr.util.*"%>
<%@ page import="com.ardais.bigr.library.web.helper.ResultsHelper"%>
<%@ page import="com.ardais.bigr.library.web.column.*"%>
<%@ page import="com.ardais.bigr.query.ColumnPermissions"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%	// -- Standard block for all query pages to find the proper txType
	// -- also MUST put a hidden txType field in the form
	String txType = ResultsHelper.TX_TYPE_ORDER_DETAIL;
	
	String orderNumber = request.getParameter("orderNumber"); // for refresh redirect
%>
<html:html>
<head>
<title>Order Detail</title>
<link rel="stylesheet" type="text/css"
	href='<html:rewrite page="/css/bigr.css"/>'>
	
<script type="text/javascript"
	src='<html:rewrite page="/js/common.js"/>'></script>

<script type="text/javascript"
	src='<html:rewrite page="/js/BigrLib.js"/>'></script>

<script type="text/javascript"
	src='<html:rewrite page="/js/ssresults.js"/>'></script>

<script type="text/javascript"
	src='<html:rewrite page="/js/overlib.js"/>'><!-- overLIB (c) Erik Bosrup --></script>
<%
	//start files needed for display of Aperio images (SWP-1061)
%>
<jsp:include page="/hiddenJsps/kc/misc/coreScript.jsp" flush="true"/>
<script type="text/javascript" src='<html:rewrite page="/js/integration/bigrAperio.js"/>'></script>
<script type="text/javaScript" src="<html:rewrite page="/js/lightbox/gsbioLightbox.js"/>"></script>
<script type="text/javaScript" src="<html:rewrite page="/hiddenJsps/kc/detViewer/js/scriptaculous.js?load=builder"/>"></script>
<link rel="stylesheet" type="text/css" href='<html:rewrite page="/js/lightbox/gsbioLightbox.css"/>'>
<%
	//end files needed for display of Aperio images (SWP-1061)
%>

<script type="text/javascript">
var myBanner = 'Order Detail List';  

function initPage() {
  commonInitPage();
  _isPageReadyForInteraction = true;
}

function reloadTable(resultsFormDefinitionId) { 		
  this.focus();
  if (resultsFormDefinitionId != null) {
		document.all.wholePage.style.display = 'none';
		document.all.waitMessage.style.display = 'block';
    var url = '<html:rewrite page="/library/orderDetails.do"/>?txType=<%=txType%>&orderNumber=<%=orderNumber%>';
    url = url + "&resultsFormDefinitionId=" + resultsFormDefinitionId;
    window.location.href = url;					
  }
}
</script>
</head>
<body class="bigr" onload="initPage();" onunload="if (parent.topFrame) {parent.topFrame.closePopup();}">
	<% //div for "please wait" message %>
  <div id="waitMessage" style="display: none"> 
    <table align="center" border="0" cellspacing="0" cellpadding="0" class="background" width="300">
      <tr> 
        <td> 
          <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
            <tr align="center" class="yellow"> 
              <td><img id="waitMessageImage" 
                       src="<html:rewrite page='/images/pleasewait.gif'/>"
                       alt="Please Wait"></td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </div>
  <% //div for whole page %>
	<div id="wholePage">
	
<div id="Buttons"
		style="position: absolute; width: 100%; overflow: hidden; z-index: 2;">
</div>

  <div id="top" style="overflow: hidden; width: 100%;">

  <div class="background">
    <table border="0" cellspacing="1" cellpadding="2" class="foreground-small" width="100%">
      <tr class="yellow">
        <td colspan="6" align="center"><b>Order Details</b></td>
      </tr>
      <tr class="white">
        <td class="grey" align="right"><b>User Name:</b></td>
        <td><bean:write name="order" property="userFullName"/></td>
        <td class="grey" align="right"><b>Order Number:</b></td>
        <td><bean:write name="order" property="orderNumber"/></td>
        <td class="grey" align="right"><b>Date:</b></td>
        <td><bean:write name="order" property="creationDateString"/></td>
      </tr>
      <tr class="yellow">
        <td colspan="6">
          <b>Shipping Address</b>
        </td>
      </tr>
      <tr class="white">
         <td colspan="6">
                                    
                                    <jsp:useBean id="shipAddress" class="com.ardais.bigr.es.beans.ArdaisaddressAccessBean" scope="request" />
                                    <% if (shipAddress != null) {
									  %>
                                    <%= ((shipAddress.getAddress_1() != null) ? shipAddress.getAddress_1() : "&nbsp;") %><br>
                                    <%= ((shipAddress.getAddress_2() != null) ? shipAddress.getAddress_2() + "<br>" : "") %> <%= ((shipAddress.getAddr_city() != null) ? shipAddress.getAddr_city() : "&nbsp;") + ", " + ((shipAddress.getAddr_state() != null) ? shipAddress.getAddr_state() : "&nbsp;") + " " + ((shipAddress.getAddr_zip_code() != null) ? shipAddress.getAddr_zip_code() : "&nbsp;") %><br>
                                    <%= ((shipAddress.getAddr_country() != null) ? shipAddress.getAddr_country() : "&nbsp;") %> 
                                    <% } else { %>
                                    Not Available 
                                    <% } %>
</td>                                    
                                    



      </tr>
    </table>
  </div>
</div>

<input type="hidden" name="txType" value="<%=txType%>"/>

<jsp:include page="/hiddenJsps/library/TissueDetailTable.jsp" flush="true">
  <jsp:param name="includeItemSelector" value="false"/>
    <jsp:param name="includeSelectViewLink" value="true"/>
</jsp:include>
</div>
</body>
</html:html>
