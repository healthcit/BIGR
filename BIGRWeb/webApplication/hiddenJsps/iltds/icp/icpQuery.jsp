<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%> 
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<title>ICP Query</title>

<script type="text/javascript">    
function icpQueryCheckTab(event) { 	
	var code = 0;
	code = event.keyCode;
  if (code == 9) {
    if (document.forms["icpQuery"].elements["id"].value.length > 0) {
      document.icpQuery.submit();
    }
  }
}

function onInitPage() { 	
	window.processing.style.display='none';
	showBanner('ICP: Query');
  document.forms["icpQuery"].elements["id"].focus()
}

function onFormSubmit() {
  window.query.style.display='none';
  window.processing.style.display='block';
  
  return true;
}
</script>
</head>
<body class="bigr" onload="onInitPage();">
<div id="query" align="center"> 
  <form action="<html:rewrite page='/iltds/Dispatch'/>" name="icpQuery"
        onsubmit="return(onFormSubmit());"
        method="get">
    <table cellpadding="0" cellspacing="0" border="0" class="background">
      <tr> 
        <td> 
          <table border="0" cellspacing="1" cellpadding="3" class="foreground">
          <logic:present name="myError">
            <tr class="green">
              <td colspan="2">
                <div align="center"><bean:write name="myError"/></div>
              </td>
            </tr>
          </logic:present>
            <tr class="yellow"> 
              <td colspan="2"> 
                <div align="center"><b>Inventory Control Panel</b></div>
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey"> 
                <div align="right"><b>Enter/Scan Inventory Item:</b></div>
              </td>
              <td> 
                <div align="left"> 
                  <input type="text" name="id" size="15" maxlength="30" onKeyDown="icpQueryCheckTab(event);">
                </div>
              </td>
            </tr>
            <tr class="white"> 
              <td colspan="2"> 
                <div align="center"> 
                  <input type="hidden" name="op" value="IcpQuery">
                  <input type="hidden" name="printerFriendly" value="N">
                  <input type="submit" name="Submit" value="Submit">
                </div>
              </td>
            </tr>
            <tr class="yellow"> 
              <td colspan="2"> 
                <div align="center"><b>Currently Supported Inventory Types</b></div>
              </td>
            </tr>
            <tr class="white"> 
              <td colspan="2"> 
                <div align="center"> 
                  <table class="foreground" cellpadding="0" cellspacing="0" border="0">
                    <tr>
                      <td> 
                        <ul> 
                          <li>Box ID</li>
                          <li>Case ID</li>
                          <li>Donor ID</li>
                          <li>Manifest ID</li>
                          <li>Request ID</li>
                          <li>Sample ID</li>
                        </ul>
                      </td>
                    </tr>
                  </table>
                </div>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </form>
</div>
<div id="processing" display="none"> 
  <table align="center" border="0" cellspacing="0" cellpadding="0" class="background" width="300">
    <tr> 
      <td> 
        <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
          <tr align="center" class="yellow"> 
            <td><img src="<html:rewrite page='/images/processing.gif'/>"></td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>
</body>
</html>
