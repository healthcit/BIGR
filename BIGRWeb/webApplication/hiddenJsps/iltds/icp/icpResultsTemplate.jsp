<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%> 
<%@ page import="com.ardais.bigr.iltds.helpers.TypeFinder" %> 
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
<bean:define id="icpType" name="icpData" property="type" type="java.lang.String" />
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
    <%-- Note that due to MR8925 there is now a check to see if detailsDiv.offsetWidth
         is greater than 1 before subtracting 1 --%>
    if (detailsDiv.offsetWidth > 1) {
      detailsDiv.style.width = detailsDiv.offsetWidth - 1;
    }
    else {
      detailsDiv.style.width = 1;
    }
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
  
  function showPrinterFriendlyVersion() {
    toIcp('<template:get name='objectId'/>','true', 'Y');
  }

  window.onresize = onResizePage;
</script>
</head>
<body class="bigr" onLoad="initPage();" style="margin-top: 0; margin-bottom: 0;">
  <%--
    ** DIV for overlib, which is used for tooltips.
    --%>
  <bigr:overlibDiv/>
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
  
  <div id="icpHeader">
    <table>
      <col width="75%"><col width="25%">
<%
// only display the "printer friendly" link if this is not a "multiple ids" situation
if (!TypeFinder.MULTIPLEIDS.equalsIgnoreCase(icpType)) {
%>
      <tr>
        <td align="left" valign="bottom" colspan="2">
          <div class="FOREGROUND-SMALL">
					  <span class="fakeLink" onclick="showPrinterFriendlyVersion();">Show printer friendly version</span>
					</div>
		    </td>
      </tr>
      <tr>
<%
}
%>
        <td colspan="2">
          &nbsp;
        </td>
      </tr>
      <tr>
        <td valign="top"><template:get name='header' flush="true"/></td>
        <td valign="top">
          <template:get name='query' flush="true"/>
		    </td>
      </tr>
    </table>
  </div>
  
  <div id="icpMiddle">
    <template:get name='middle' flush="true"/>
  </div>
    
  <div id="Details"
       style="position: relative; width:100%; overflow: auto; border-width: 2px 2px 2px 2px; border-style: solid; border-color: #336699;"> 
    <script type="text/javascript">positionItems();</script>
    <template:get name='detail' flush="true"/>
<%
  // only display the history if this is not a "multiple ids" situation
  if (!TypeFinder.MULTIPLEIDS.equalsIgnoreCase(icpType)) {
%>
    <jsp:include page="/hiddenJsps/iltds/icp/icpHistory.jsp" flush="true"/>
<%
  }
%>
  </div>
  </div>
</body>
</html> 
