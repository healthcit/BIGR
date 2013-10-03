<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ardais.bigr.library.javabeans.*"%>
<%@ page import="com.ardais.bigr.library.web.helper.*"%>
<%@ page import="com.ardais.bigr.library.web.column.*"%>
<%@ page import="com.ardais.bigr.query.ColumnPermissions"%>
<%@ page import="com.ardais.bigr.security.SecurityInfo"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%
  // -- Standard block for all query pages to find the proper txType
	// -- also MUST put a hidden txType field in the form
	String txType = null;
	com.ardais.bigr.library.web.form.QueryForm qform = 
		(com.ardais.bigr.library.web.form.QueryForm) request.getAttribute("queryForm");
	if (qform != null) {
		txType = qform.getTxType();
	}
	else {
		com.ardais.bigr.library.web.form.ResultsForm rform = 
			(com.ardais.bigr.library.web.form.ResultsForm) request.getAttribute("resultsForm");
		if (rform != null) {
			txType = rform.getTxType();
		}
		else {
			txType = request.getParameter("txType");
		}
	} // end standard txType block
%>
<html:html>
<head>
<title>Confirm Order Request</title>
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
var myBanner = 'Confirm Order Request';  

function initPage() {
  commonInitPage();
  setActionButtonEnabling(true);
  _isPageReadyForInteraction = true;
}

function onFormSubmit() {
  setActionButtonEnabling(false);
  return true;
}

function onClickCancel() {
  if (!onFormSubmit()) {
    setActionButtonEnabling(true);
    return;
  }
  window.location.href =
    '<html:rewrite page="/library/holdView.do"/>?txType=<%=txType%>';
}

function onClickSubmitOrder() {
  if (!onFormSubmit()) {
    setActionButtonEnabling(true);
    return;
  }
  document.forms[0].submit();
}
   
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.forms[0];
  setInputEnabled(f, 'btnSubmitOrder', isEnabled);
  setInputEnabled(f, 'btnCancel', isEnabled);
}
</script>
</head>
<body class="bigr" style="margin-top: 2px;" onload="initPage();">

<html:errors/>
<bigr:btxActionMessages/>

<html:form action="/library/requestSubmit">
	<input type="hidden" name="txType" value="<%=txType%>"/>
	<html:hidden property="useSingleCategory"/>
	
  <div id="top" style="overflow: hidden; width: 100%;">

  <div class="background">
    <table border="0" cellspacing="1" cellpadding="2" class="foreground-small" width="100%">
      <tr class="white">
        <td class="grey" align="right"><b>Name:</b></td>
        <td><html:text property="name" maxlength="80"/></td>
        <td class="grey" align="right"><b>Phone:</b></td>
        <td><html:text property="phone" maxlength="35"/></td>
        <td class="grey" align="right"><b>Email:</b></td>
        <td><html:text property="email" maxlength="100"/></td>
      </tr>
      <tr class="grey">
        <td colspan="6">
        <b>Please provide as much detail as possible in describing your order:</b><br>
		   (Did you find all the samples you were looking for? Are there special preparation, handling or delivery requirements?) 
        </td>
      </tr>
      <tr class="white">
        <td colspan="6">
          <textarea name="details" cols="80" rows="8"></textarea>
        </td>
      </tr>
    </table>
  </div>
</div>

<div id="Buttons"
		  style="position: absolute; width: 100%; overflow: hidden; z-index: 2;">
  <table border="0" cellspacing="1" cellpadding="2"
		     class="foreground-small" width="100%">
  	<tr>
	    <td align="center">
	      <input type="button" disabled="true" id="btnSubmitOrder"
	        style="FONT-SIZE:xx-small" value="Submit Request"
	        onclick="onClickSubmitOrder();">
		  	<input type="button" disabled="true" id="btnCancel"
		  	  style="FONT-SIZE:xx-small" value="Cancel"
		  	  onclick="onClickCancel();">
	      <br>
      </td>
		</tr>						
	</table>
</div>

<jsp:include page="/hiddenJsps/library/TissueDetailTable.jsp" flush="true">
	<jsp:param name="groupedByCase" value="true"/>
	<jsp:param name="includeItemSelector" value="false"/>
</jsp:include>

</html:form>
</body>
</html:html>
