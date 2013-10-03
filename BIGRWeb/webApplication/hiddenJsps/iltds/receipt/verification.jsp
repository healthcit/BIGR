<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.iltds.helpers.FormLogic"%>
<%@ taglib uri="/tld/struts-html" prefix="html"%>
<%@ taglib uri="/tld/struts-logic" prefix="logic"%>
<%@ taglib uri="/tld/struts-bean" prefix="bean"%>
<%@ taglib uri="/tld/bigr" prefix="bigr"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Receipt Verification</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script type="text/javascript" src="<html:rewrite page="/js/common.js"/>"> </script>
<script language="JavaScript">
<!--

var myBanner = 'Receipt Verification';

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
	document.forms[0].newTrackingNumber.focus();
}

function setActionButtonEnabling(isEnabled) {
	var f = document.forms[0];
	setInputEnabled(f,'submitButton',isEnabled);
	setInputEnabled(f,'clearButton',isEnabled);
}

function onFormSubmit() {
  setActionButtonEnabling(false);
  return true;
}

function clearTrackingNumber() {
	document.forms[0].newTrackingNumber.value = "";
}

//-->
</script>
</head>
<body class="bigr" onload="initPage();">
<html:form action="/iltds/receipt/shipmentVerification" onsubmit="return(onFormSubmit());">
  <table border="0" cellspacing="0" cellpadding="0" align="center">
    <logic:present name="org.apache.struts.action.ERROR">
    <tr> 
      <td>
        <table border="0" cellspacing="1" cellpadding="1" class="foreground-small">
          <tr class="white">
		    <td>
			  <div align="left">
			    <font color="#FF0000"><b><html:errors/></b></font>
			  </div>
			</td>
		  </tr>
		</table>
	  </td>
	</tr>
	</logic:present>
	<logic:present name="com.ardais.BTX_ACTION_MESSAGES">
    <tr> 
      <td>
        <table border="0" cellspacing="1" cellpadding="1" class="foreground-small">
		  <tr class="white">
			<td>
			  <div align="left">
			    <font color="#000000"><b><bigr:btxActionMessages/></b></font>
			  </div>
			</td>
		  </tr>
		</table>
	  </td>
	</tr>
	</logic:present>
	<tr> 
	  <td> 
		<table border="1" cellspacing="1" cellpadding="3" class="foreground" align="center">
          <tr class="yellow"> 
            <td colspan="2"> 
              <div align="center"><b>Please Verify Received Shipments</b></div>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey" ><b>Scan Tracking Number: </b></td>
            <td>
              <html:text property="newTrackingNumber" size="30" maxlength="<%=String.valueOf(FormLogic.LENGTH_WAYBILLID)%>"/>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey"><b>Tracking #:</b></td>
            <td class="grey"> 
              <div align="center"><b>Status:</b></div>
            </td>
          </tr>
          <bean:define name="shipmentForm" property="trackingNumberCount" id="count" type="java.lang.Integer"/>
          <% for (int index = (count.intValue() - 1); index >= 0; index--) {%>
          <tr class="white">
            <td>
              <bean:write name="shipmentForm" property='<%="trackingNumber[" + index + "]"%>'/>
              <html:hidden property='<%="trackingNumber[" + index + "]"%>'/>
            </td>
            <td> 
              <div align="center"><font color="#336699">Shipment has been verified.</font></div>
            </td>
          </tr>
          <% } %>
          <tr class="white"> 
            <td colspan="2"> 
              <div align="center">
                <html:submit property="submitButton" value="Submit" style="font-size:xx-small; width: 90px;"/>
                <html:button property="clearButton" value="Clear" onclick="clearTrackingNumber()" style="font-size:xx-small; width: 90px;"/>
              </div>
            </td>
          </tr>
		</table>
	  </td>
	</tr>
  </table>
</html:form>
</body>
</html>
