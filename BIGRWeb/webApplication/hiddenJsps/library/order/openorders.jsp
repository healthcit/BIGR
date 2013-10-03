<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.library.web.helper.ResultsHelper"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>

<%	// -- Standard block for all query pages to find the proper txType
	// -- also MUST put a hidden txType field in the form
	String txType = null;
	txType = ResultsHelper.TX_TYPE_ORDER_DETAIL;
%>

<script type="text/javascript"
	src='<html:rewrite page="/js/common.js"/>'></script>

<script type="text/javascript"
	src='<html:rewrite page="/js/BigrLib.js"/>'></script>

<script type="text/javascript"
	src='<html:rewrite page="/js/ssresults.js"/>'></script>

<script type="text/javascript"
	src='<html:rewrite page="/js/overlib.js"/>'><!-- overLIB (c) Erik Bosrup --></script>
	
<script type="text/javascript">
var myBanner = 'Order Status';  
function initPage() {
  commonInitPage();
  _isPageReadyForInteraction = true;
}
</script>
<html:html>
  <head>
    <title>Open Order List</title>
    <link rel="stylesheet" type="text/css" href='<html:rewrite page="/css/bigr.css"/>'>
  </head>

  <body class="bigr" onload="initPage();" onunload="if (parent.topFrame) {parent.topFrame.closePopup();}">
    <table width="400" cellpadding="0" cellspacing="0" border="0" align="center"><tr><td>
    <div class="background">
      <table class="fixedTable" cellspacing="1" align="center">
	    <tr class="yellow">
	      <td><b>Order ID</b></td>
	      <td><b>Order Date</b></td>
	      <td><b>Order Status</b></td>
	      <td><b>View Details</b></td>
	    </tr>		
        <logic:present name="orders">
          <logic:iterate name="orders" id="order">
          <form method="post" action="<html:rewrite page="/library/orderDetails.do"/>">
            <input type="hidden" name="orderNumber" value="<bean:write name="order" property="orderNumber"/>">
			<input type="hidden" name="txType" value="<%=txType%>"/>            
			<tr class="white">  
              <td><bean:write name="order" property="orderNumber"/></td>
              <td><bean:write name="order" property="creationDateString"/></td>
              <td><bean:write name="order" property="status"/></td>
              <td><input type="submit" value="View Details" class="libraryButtons"></td>
            </tr>
          </form>
          </logic:iterate>    
        </logic:present>
      </table>
    </div>
      </td></tr></table>
  </body>
</html:html>
