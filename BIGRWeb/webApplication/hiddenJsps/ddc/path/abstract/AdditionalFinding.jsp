<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, getServletContext(), "P");
%>
<bean:define name="helper" id="finding"/>
<bean:define name="helper" property="pathReportSection" id="section"/>
<bean:define name="helper" property="pathReport" id="path"/>

<html>
<head>
<title>Pathology Report Additional Finding</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript">
function setAdd() {
  document.all.addnext.value = "addnext";
}

function doSave(isSaveAndAdd) {
  setActionButtonEnabling(false);
  if (isSaveAndAdd) {
    setAdd();
  }
  document.path.submit();
}

function doReset() {
  setActionButtonEnabling(false);
  document.path.reset();
  setActionButtonEnabling(true);
}

function doCancel(gotoURL) {
  setActionButtonEnabling(false);
  window.location.href = gotoURL;
}
   
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.path;
  f.btnSave.disabled = isDisabled;
  f.btnSaveAdd.disabled = isDisabled;
  f.btnReset.disabled = isDisabled;
  f.btnCancel.disabled = isDisabled;
}
</script>
</head>

<body class="bigr" onLoad="if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = 'Pathology Report Additional Finding';">
  <form name="path" method="POST" action="<html:rewrite page="/ddc/Dispatch"/>">
  <input type="hidden" name="op" value="AdditionalFindingEdit">
  <input type="hidden" name="addnext" value="">
  <html:hidden name="path" property="pathReportId"/>
  <html:hidden name="finding" property="pathReportSectionId"/>
  <html:hidden name="finding" property="findingId"/>
  <html:hidden name="finding" property="donorImportedYN"/>
  <html:hidden name="finding" property="consentId"/>

  <div align="center">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" border="0" cellpadding="3" cellspacing="1">
            <bean:write name="finding" property="messages" filter="false"/>
          </table>
        </td>
      </tr>
    </table>
  </div>
  <p>
  
  <div align="center">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" border="0" cellpadding="3" cellspacing="1">
            <tr class="white"> 
              <td class="yellow" align="right"><b>Donor</b></td>
              <td colspan="3">
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
              <td colspan="3">
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
              <td class="yellow" align="right"><b>Section</b></td>
              <td>
              	<html:link page="/ddc/Dispatch" name="section" property="sectionLinkParams">
                <bean:write name="section" property="sectionIdentifierDisplay"/>
                <logic:equal name="section" property="primary" value="Y">&nbsp;&nbsp;(primary)</logic:equal>
                </html:link>
              </td>
            </tr>
            <tr class="white">
              <td colspan="4" align="center">
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
  </div>

  <p><table class="background" cellpadding="0" cellspacing="0" border="0" width="100%">
    <tr><td> 
      <table class="foreground" border="0" cellpadding="3" cellspacing="1" width="100%">
        <tr class="green"> 
          <td colspan="2"><b>Additional Pathology Finding Details</b></td>
        </tr>
        <bigr:diagnosisWithOther name="finding"
          property="diagnosis" propertyLabel="Additional Pathology Finding"
          otherProperty="diagnosisOther" otherPropertyLabel="Other Additional Pathology Finding"
          required="true"
          firstValue="" firstDisplayValue="Select Diagnosis"
          includeAlphaLookup="true"/>
        <bigr:tissueWithOther name="finding"
          property="tissue" propertyLabel="Tissue"
          otherProperty="tissueOther" otherPropertyLabel="Other Tissue"
          required="true"
          firstValue="" firstDisplayValue="Select Tissue"
          includeAlphaLookup="true"/>
        <tr class="white"> 
          <td class="grey" align="right">Additional Finding Notes</td>
          <td> 
            <html:textarea name="finding" property="note" rows="4" cols="80" onkeyup="maxTextarea(500);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td colspan="2"> 
            <div align="center"> 
						<logic:notPresent name="finding" property="findingId">
              <input type="button" name="btnSave" value="Save" onclick="doSave(false);"/>
						</logic:notPresent>
 						<logic:present name="finding" property="findingId">
              <input type="button" name="btnSave" value="Update" onclick="doSave(false);"/>
						</logic:present>
						<logic:notPresent name="finding" property="findingId">
             	<input type="button" name="btnSaveAdd" value="Save & Add Next"
             	  onclick="doSave(true);"/>
 						</logic:notPresent>
						<logic:present name="finding" property="findingId">
             	<input type="button" name="btnSaveAdd" value="Update & Add Next"
             	  onclick="doSave(true);"/>
 						</logic:present>
              <input type="button" value="Reset" name="btnReset" onclick="doReset();"/>
              <input type="button" name="btnCancel" value="Cancel"
                onclick="doCancel('<html:rewrite page="/ddc/Dispatch" name="section" property="sectionLinkParams"/>');"> 
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
