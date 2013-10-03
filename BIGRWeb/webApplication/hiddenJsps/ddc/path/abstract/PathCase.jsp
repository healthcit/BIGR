<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, getServletContext(), "P");
%>
<bean:define name="helper" id="path"/>

<html>
<head>
<title>Pathology Report Case-Level Details</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript">

// Perform page initialization functions.
function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = 'Pathology Report Case-Level Details';

  <logic:notPresent name="path" property="pathReportMonth">
    <logic:notPresent name="path" property="pathReportYear">
  setPathReportDate();
    </logic:notPresent>
  </logic:notPresent>
}

// Set the path report date to today's month and year.
function setPathReportDate() {
  var today = new Date();
  var month = today.getMonth() + 1;
  if (month <= 9) {
    month = "0" + month;
  }
  document.all.pathReportMonth.value = month;
  document.all.pathReportYear.value = today.getYear();
}

function doSave() {
  setActionButtonEnabling(false);
  document.path.submit();
}

function doReset() {
  setActionButtonEnabling(false);
  document.path.reset();
  setActionButtonEnabling(true);
}

function doCancel(gotoURL) {
  setActionButtonEnabling(false);
  window.location.href=gotoURL;
}
   
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.path;
  f.btnSave.disabled = isDisabled;
  f.btnReset.disabled = isDisabled;
  f.btnCancel.disabled = isDisabled;
}

</script>
</head>

<body class="bigr" onLoad="initPage();">
  <form name="path" method="POST" action="<html:rewrite page="/ddc/Dispatch"/>"
	    onsubmit="return(doSave());">
  <input type="hidden" name="op" value="PathReportEdit">
  <input type="hidden" name="context" value="case">
  <html:hidden name="path" property="pathReportId"/>
  <html:hidden name="path" property="ardaisId"/>
  <html:hidden name="path" property="consentId"/>
  <html:hidden name="path" property="disease"/>
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
          <table class="foreground" border="0" cellpadding="3" cellspacing="1">
            <tr class="white"> 
              <td class="yellow" align="right"><b>Donor</b></td>
              <td>
              	<html:link page="/ddc/Dispatch" name="path" property="donorLinkParams">
                <bean:write name="path" property="ardaisId"/>
                </html:link>
                <logic:notEmpty name="path" property="donorCustomerId">
                  (<bean:write name="path" property="donorCustomerId" />)
                </logic:notEmpty>
              </td>
            </tr>
            <tr class="white"> 
              <td class="yellow" align="right"><b>Case</b></td>
              <td>
              	<html:link page="/ddc/Dispatch" name="path" property="consentLinkParams">
                <bean:write name="path" property="consentId"/>
                </html:link>
                <logic:notEmpty name="path" property="consentCustomerId">
                  (<bean:write name="path" property="consentCustomerId" />)
                </logic:notEmpty>
              </td>
            </tr>
            <tr class="white"> 
              <td class="yellow" align="right"><b>Disease Type</b></td>
              <td><bean:write name="path" property="diseaseName"/></td>
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
          </table>
        </td>
      </tr>
    </table>
  </div></p>

  <p><table class="background" cellpadding="0" cellspacing="0" border="0" width="100%">
    <tr><td> 
      <table class="foreground" border="0" cellpadding="3" cellspacing="1" width="100%">
        <tr class="green"> 
          <td colspan="2"><b>Pathology Report Case-Level Details</b></td>
        </tr>
        <tr class="white"> 
          <td class="grey" align="right"> 
            <b>Pathology Report Date</b> <font color="Red">*</font>
          </td>
          <td valign="center">
            <bigr:selectList name="path" property="pathReportMonth" legalValueSetProperty="monthList"/>
            / 
            <bigr:selectList name="path" property="pathReportYear" legalValueSetProperty="yearList"/>
          </td>
        </tr>
        <bigr:procedureWithOther name="path"
          property="procedure" propertyLabel="Procedure"
          otherProperty="procedureOther" otherPropertyLabel="Other Procedure"
          required="true"
          firstValue="" firstDisplayValue="Select Procedure"
          includeAlphaLookup="true"/>
        <tr class="white"> 
          <td class="grey" align="right">Case Pathology Notes</td>
          <td> 
            <html:textarea name="path" property="additionalNote" rows="4" cols="80"/>
          </td>
        </tr>
        <tr class="white"> 
          <td colspan="2"> 
            <div align="center"> 
			  <input type="button" name="btnSave" value="Save" onclick="doSave();"/>
              <input type="button" value="Reset" name="btnReset" onclick="doReset();"/>
              <input type="button" name="btnCancel" value="Cancel"
                onclick="doCancel('<html:rewrite page="/ddc/Dispatch" name="path" property="consentLinkParams"/>');"> 
            </div>
            <font color="Red">*</font> indicates a required field  
          </td>
        </tr>
      </table>
    </td></tr>
  </table></p>
  </form>
</body>
</html>
