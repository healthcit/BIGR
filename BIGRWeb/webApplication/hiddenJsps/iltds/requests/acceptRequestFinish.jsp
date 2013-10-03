<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.iltds.helpers.RequestSelect"%>
<%@ page import="com.ardais.bigr.iltds.helpers.RequestType"%>
<%@ taglib uri="/tld/struts-html"  prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/struts-bean"  prefix="bean" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>

<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<bean:define id="myForm" name="requestDtoForm" type="com.ardais.bigr.iltds.web.form.RequestForm"/>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Accept Request</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script type="text/javascript" src="<html:rewrite page="/js/common.js"/>"> </script>
<script type="text/javascript" src="<html:rewrite page="/js/calendar.js"/>"> </script>
<script language="JavaScript">
<!--

var myBanner = 'Accept Request';

function onFormSubmit() {
  setActionButtonEnabling(false);
  return true;
}

function setActionButtonEnabling(isEnabled) {
  var f = document.forms[0];
  setInputEnabled(f,'boxContents',isEnabled);
  setInputEnabled(f,'manageRequests',isEnabled);
  setInputEnabled(f,'shipBoxes',isEnabled);
}

function printBoxContentsForRequest(requestId) {
  var URL = '<html:rewrite page="/iltds/printRequestBoxContents.do"/>' + "?requestId=" + requestId;
  URL = URL + "&itemDetailLevel=" + <%=RequestSelect.ITEM_INFO_NONE%>;
  URL = URL + "&boxDetailLevel=" + <%=RequestSelect.BOX_INFO_DETAILS%>;
  var w = window.open(URL, "BoxContents" + requestId, "scrollbars=yes,resizable=yes,top=0,left=0,width=750,height=700");
  w.focus();
}

function goToManageRequests() {
  setActionButtonEnabling(false);
  window.location.href = '<html:rewrite page="/iltds/manageRequestsStart.do"/>';
}

function goToShipBoxes() {
  setActionButtonEnabling(false);
  window.location.href = '<html:rewrite page="/iltds/shipping/createManifestStart.do"/>';
}

//-->
</script>
</head>

<body  class="bigr" onLoad="if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;">
<html:form method="POST"
      action="/iltds/manageRequestsStart"
      onsubmit="return(onFormSubmit());">
<%
  // DIV for errors
%>
  <div id="errorDiv" align="center">
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
      <logic:present name="org.apache.struts.action.ERROR">
        <tr class="yellow"> 
	      <td> 
	        <div align="left">
	          <font color="#FF0000"><b><html:errors/></b></font>
	        </div>
	      </td>
	    </tr>
	  </logic:present>
      <logic:present name="com.ardais.BTX_ACTION_MESSAGES">
	    <tr class="yellow"> 
	      <td> 
	        <div align="left">
	          <font color="#FF0000"><b><bigr:btxActionMessages/></b></font>
	        </div>
	      </td>
	    </tr>
	  </logic:present>
	</table>
  </div>
<p>
<div align="center">
You have successfully accepted Request <bean:write name="requestDtoForm" property="requestId"/>.  Please select the next action you wish to take.
<br><br><br>
&nbsp;&nbsp;&nbsp;
<input type="button" name="boxContents" value="Print Box Contents" onClick="printBoxContentsForRequest('<%=myForm.getRequestId()%>');"> 
&nbsp;&nbsp;&nbsp;
<input type="button" name="manageRequests" value="Return to Manage Requests" onClick="goToManageRequests();"> 
<logic:equal name="requestDtoForm" property="requestType" value="<%=RequestType.TRANSFER.toString()%>">
  &nbsp;&nbsp;&nbsp;
  <input type="button" name="shipBoxes" value="Ship Boxes" onClick="goToShipBoxes();"> 
</logic:equal>
</html:form>
</body>
</html>
