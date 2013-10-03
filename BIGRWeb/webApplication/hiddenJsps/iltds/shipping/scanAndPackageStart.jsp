<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ValidateIds" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html"  prefix="html" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<html>
<head>
<title>Scan and Package Manifest</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<html:rewrite page="/css/bigr.css"/>" type="text/css">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script type="text/javascript">
var myBanner = 'Scan and Package Manifest';

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
  
  document.forms[0]['manifestDto.manifestNumber'].focus();
}

function onFormSubmit() {
  setActionButtonEnabling(false);
  return true;
}

function doSubmitForm() {
  if (onFormSubmit()) {
    document.forms[0].submit();
  }
}

function checkTab(event) { 	
	var code = 0;
	code = event.keyCode;  
	if (code == 9) {
	  var textBox = document.forms[0]['manifestDto.manifestNumber'];
	  textBox.value = trim(textBox.value);
		if (textBox.value.length > 0) {
			doSubmitForm();
			event.returnValue = false;
		}
	}
}
   
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.forms[0];
  setInputEnabled(f, 'btnContinue', isEnabled);
}
</script>
</head>
<body class="bigr" onload="initPage();">
<html:errors/>
<bigr:btxActionMessages/>
<html:form method="POST"
      action="/iltds/shipping/scanAndPackageGetManifest"
      onsubmit="return(onFormSubmit());">
  <div align="center">
    <table border="0" cellspacing="0" cellpadding="0" class="background">
      <tr> 
        <td> 
          <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
            <tr class="yellow" align="center"> 
              <td colspan="2"><b>Please scan a manifest number</b></td>
            </tr>
            <tr class="white"> 
              <td class="grey" nowrap>Manifest Number:</td>
              <td> 
                <html:text property="manifestDto.manifestNumber"
                  size="<%= String.valueOf(ValidateIds.LENGTH_MANIFEST_ID + 5) %>"
                  maxlength="<%= String.valueOf(ValidateIds.LENGTH_MANIFEST_ID) %>"
                  value=""
							    onkeydown="checkTab(event);"/>
              </td>
            </tr>
            <tr class="white"> 
              <td colspan="2" align="center"> 
                <input type="submit" name="btnContinue" value="Continue">
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </div>
</html:form>
</body>
</html>
