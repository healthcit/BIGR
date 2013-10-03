<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, getServletContext(), "P");
%>
<bean:define name="helper" id="path" />

<html>
<head>
<title>Select Disease</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<script>
function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
}

function onFormSubmit() {
  disableActionButtons();
  return true;
}
  
function disableActionButtons() {
	document.selectDisease.btnSubmit.disabled = true;
}

myBanner = 'Select Disease';
</script>
</head>

<body class="bigr" onload="initPage();">
<form name="selectDisease" method="POST" action="<html:rewrite page="/ddc/Dispatch"/>"
      onsubmit="return(onFormSubmit());">
  <input type="hidden" name="op" value="PathReportEdit"/>
  <input type="hidden" name="context" value="disease">
  <html:hidden name="path" property="pathReportId"/>
  <html:hidden name="path" property="ardaisId"/>
  <html:hidden name="path" property="consentId"/>
  <html:hidden name="path" property="donorImportedYN"/>

  <div align="center">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" border="0" cellpadding="3" cellspacing="1">
            <bean:write name="path" property="messages" filter="false"/>
          </table>
        </td>
      </tr>
    </table>
  </div>

  <p><div align="center">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table border="0" class="foreground" cellpadding="3" cellspacing="1">
            <tr class="yellow"> 
              <td colspan="2" align="center"><b>Please Select a Disease Type</b></td>
            </tr>
             <tr class="white">
              <td colspan="6" align="center">
                <logic:present name="path" property="rawPathReport">
                  <span class="fakeLink" onclick="window.open('<html:rewrite page="/ddc/Dispatch" name="path" property="rawPopupLinkParams"/>', 'RawPathReport','width=600,height=600,top=100,left=100,resizable=yes,scrollbars=yes,status=yes');">
                  Show Full Text Pathology Report</span>
                </logic:present>
                <logic:notPresent name="path" property="rawPathReport">
                  &lt;The Full Text Pathology Report has not been entered&gt;                                
                </logic:notPresent>
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey" align="right"><b>Donor ID:</b></td>
              <td align="left">
                <bean:write name="path" property="ardaisId"/>
                <logic:notEmpty name="path" property="donorCustomerId">
                  (<bean:write name="path" property="donorCustomerId" />)
                </logic:notEmpty>
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey" align="right"><b>Case ID:</b></td>
              <td align="left">
                <bean:write name="path" property="consentId"/>
                <logic:notEmpty name="path" property="consentCustomerId">
                  (<bean:write name="path" property="consentCustomerId" />)
                </logic:notEmpty>
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey" align="right"><b>Disease Type:</b></td>
              <td align="left">
                <bigr:selectList name="path" property="disease" legalValueSetProperty="diseaseList" firstValue="" firstDisplayValue="Select Disease"/>
              </td>
            </tr>
            <tr class="white"> 
              <td colspan="2" align="center"><html:submit property="btnSubmit" value="Continue"/></td>
            </tr>
          </table>
        </td>
      </tr> 
    </table>  
  </div></p>
  
</form>
</body>
</html>
