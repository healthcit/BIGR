<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
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
<title>Receipt Box Location(s)</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script type="text/javascript" src="<html:rewrite page="/js/common.js"/>"> </script>
<script language="JavaScript">
<!--

myBanner = 'Receipt Box Location(s)';

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
}

function setActionButtonEnabling(isEnabled) {
	var f = document.forms[0];
	setInputEnabled(f,'print',isEnabled);
	setInputEnabled(f,'close',isEnabled);
}

//-->
</script>
</head>
<body class="bigr" <logic:notPresent parameter="popup"> onLoad="initPage();" </logic:notPresent>>
<html:form action="/iltds/receipt/scanShipmentInformation" onsubmit="return false;">
  <table border="0" cellspacing="0" cellpadding="0" align="center">
    <logic:present name="org.apache.struts.action.ERROR">
    <tr> 
      <td>
        <table border="0" cellspacing="1" cellpadding="1" class="foreground-small">
          <tr class="white">
		    <td><div align="left"><font color="#FF0000"><b><html:errors/></b></font></div></td>
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
		    <td><div align="left"><font color="#FF0000"><b><bigr:btxActionMessages/></b></font></div></td>
		  </tr>
	    </table>
	  </td>
    </tr>
    </logic:present>
    <tr> 
      <td> 
	    <table border="1" cellspacing="1" cellpadding="3" class="foreground" align="center">
          <tr class="white"> 
            <td colspan="5"> 
              <div align="center"><font size="+1"><b>Put-Away Ticket</b></font></div>
            </td>
          </tr>
          <tr  class="yellow"> 
            <td><div align="center"><b>Box Id</b></div></td>
            <td><div align="center"><b>Room</b></div></td>
            <td><div align="center"><b>Storage Unit</b></div></td>
            <td><div align="center"><b>Drawer</b></div></td>
            <td><div align="center"><b>Slot</b></div></td>
          </tr>
          <logic:iterate name="manifestForm" property="boxes" id="box" type="com.ardais.bigr.javabeans.BoxDto">
          <tr  class="white"> 
            <td><bean:write name="box" property="boxId"/></td>
            <td ><bean:write name="box" property="room"/></td>
            <td><bean:write name="box" property="unitName"/></td>
            <td><bean:write name="box" property="drawer"/></td>
            <td><bean:write name="box" property="slot"/></td>
          </tr>
          </logic:iterate>
          <tr class="white"> 
            <td colspan="5"> 
              <div align="center">
                <html:button property="print" value="Print" style="font-size:xx-small; width: 90px;" onclick="window.print();"/>
                <logic:present parameter="popup">
                <html:button property="close" value="Close" style="font-size:xx-small; width: 90px;" onclick="window.close();"/>
                </logic:present>
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
