<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
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
	}
%>
<html:html>
<head>
<title>Request Submitted</title>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">

<script type="text/javascript"
	src='<html:rewrite page="/js/common.js"/>'></script>

<script type="text/javascript">
var myBanner = 'Request Submitted';  
  
function initPage() {
  if (parent.topFrame) {
  	parent.topFrame.document.all.banner.innerHTML = myBanner; 
  }
  setActionButtonEnabling(true);
}

function onFormSubmit() {
  setActionButtonEnabling(false);
  return true;
}

function onClickToSampleSelection() {
  if (!onFormSubmit()) {
    setActionButtonEnabling(true);
  }
  window.location.href =
    '<html:rewrite page="/library/showResults.do"/>?txType=<%=txType%>';
}

function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  setInputEnabled(document.all, 'btnToSampleSelection', isEnabled);
}
</script>
</head>
<body class="bigr" onload="initPage();">
  
<table cellpadding="0" cellspacing="0" border="0" class="background" width="100%">
  <tr> 
    <td> 
      <table cellspacing="1" cellpadding="3" border="0" class="foreground" width="100%">
        <tr class="yellow">
          <td><b>The following was sent to Customer Service:</b></td>
        </tr>
        <tr class="white">
          <td><bean:write name="email" filter="false"/></td>
        </tr>
      </table>
    </td>
  </tr>
</table>

<div id="Buttons" style="margin-top: 1em;">
  <table border="0" cellspacing="1" cellpadding="2"
         class="foreground-small" width="100%">
    <tr>
      <td align="center">
        <input type="button" disabled="true" id="btnToSampleSelection"
          style="FONT-SIZE:xx-small" value="Return to Sample Selection"
          onclick="onClickToSampleSelection();">
      </td>
    </tr>						
  </table>
</div>

</body>
</html:html>