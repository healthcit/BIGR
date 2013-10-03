<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%> 
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/struts-template" prefix="template" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title><template:get name='title'/></title>
<script type="text/javascript" src='<html:rewrite page="/js/common.js"/>'></script>
<script type="text/javascript" src="<html:rewrite page='/js/overlib.js'/>"><!-- overLIB (c) Erik Bosrup --></script> 
<script type="text/javascript" src='<html:rewrite page="/js/ssresults.js"/>'></script>

<script type="text/javascript">
  var myBanner = '<template:get name='title'/>';

  function positionItems() {
    var detailsDiv = document.all.Details;
    var maxHeight = document.body.clientHeight - detailsDiv.offsetTop - 5;
    if (maxHeight < 125) {
      maxHeight = 125;
    }

    <%-- Setting the detailsDiv width to a specific size then back to 100%
      -- works around an IE bug where the detailsDiv would come up initially
      -- wider than it should have been (by the width of a scroll bar). --%>
    detailsDiv.style.width = detailsDiv.offsetWidth - 1;
    detailsDiv.style.width = "100%";
    detailsDiv.style.height = maxHeight;
  }

  function initPage() {
    if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
    positionItems();
  _isPageReadyForInteraction = true;
  }

  var priorResizeHeight = 0;

  function onResizePage() {
    var currentHeight = document.body.clientHeight;
    if (currentHeight == priorResizeHeight) return;
    priorResizeHeight = currentHeight;
    positionItems();
  }

  window.onresize = onResizePage;
</script>
</head>
<body class="bigr" onLoad="initPage();" style="margin-top: 0; margin-bottom: 0;">
  <%--
    ** DIV for overlib, which is used for tooltips.
    --%>
  <bigr:overlibDiv/>
  
  <div id="icpHeader" style="position: relative; width:100%;">
    <table>
      <tr>
        <td valign="top"><template:get name='header' flush="true"/></td>
      </tr>
    </table>
  </div>
    
  <div id="Details"
       style="position: relative; width:100%; overflow:visible border-width: 2px 2px 2px 2px; border-style: solid; border-color: #336699;"> 
    <script type="text/javascript">positionItems();</script>
    <template:get name='detail' flush="true"/>
    <jsp:include page="/hiddenJsps/iltds/icp/icpHistory.jsp" flush="true"/>
  </div>
</body>
</html> 
