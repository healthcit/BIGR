<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, getServletContext(), "P");
%>
<bean:define name="helper" id="diagnostic"/>
<bean:define name="helper" property="pathReport" id="path"/>

<html>
<head>
<title>Pathology Report Diagnostic Test</title>
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

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = 'Pathology Report Diagnostic Test';
  var f = document.all.diagnosticSearchRefreshDiv
  if (f != null) {
  	f.style.display = "none";	
  }
  
}

function hideResultIfNecessary() {
  var testType = document.all.type.value;
  if (testType == "") {
  	var e = document.all.resultDiv;
  	e.innerHTML = "";
  }
}
</script>
</head>

<body class="bigr" onLoad="initPage();">
  <form name="path" method="POST" action="<html:rewrite page="/ddc/Dispatch"/>">
  <input type="hidden" name="op" value="PathDiagnosticEdit">
  <input type="hidden" name="addnext" value="">
  <html:hidden name="path" property="pathReportId"/>
  <html:hidden name="diagnostic" property="new"/>
  <logic:present name="diagnostic" property="id">
  	<html:hidden name="diagnostic" property="id"/>
  </logic:present>
  <html:hidden name="diagnostic" property="donorImportedYN"/>
  <html:hidden name="diagnostic" property="consentId"/>

  <div align="center">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" border="0" cellpadding="3" cellspacing="1">
            <bean:write name="diagnostic" property="messages" filter="false"/>
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
              <td colspan="2" align="center">
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
        <tr class="white"> 
          <td class="grey" align="right"> 
            <b>Test Type</b>
            <logic:equal name="diagnostic" property="new" value="true">            
              <font color="Red">*</font>
            </logic:equal>
          </td>
          <td>
            <logic:equal name="diagnostic" property="new" value="true">
              <bigr:selectList name="diagnostic" property="type" childProperty="diagnostic" onchange="hideResultIfNecessary();" legalValueSetProperty="typeList" firstValue=" " firstDisplayValue="Select a Test Type"/>
            </logic:equal>
            <logic:equal name="diagnostic" property="new" value="false">
              <bean:write name="diagnostic" property="typeDisplay"/>
              <html:hidden name="diagnostic" property="type"/>
            </logic:equal>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey" align="right"> 
            <b>Test Name</b>
            <logic:equal name="diagnostic" property="new" value="true">            
              <font color="Red">*</font>
            </logic:equal>
          </td>
          <td> 
            <logic:equal name="diagnostic" property="new" value="true">
              <bigr:selectList name="diagnostic" property="diagnostic" childProperty="result" parentProperty="type" legalValueSetProperty="diagnosticList"
              firstValue=" "
              firstDisplayValue="Select a Test Name"
              includeAlphaLookup="true"/>
            </logic:equal>
            <logic:equal name="diagnostic" property="new" value="false">
              <bean:write name="diagnostic" property="diagnosticDisplay"/>
              <html:hidden name="diagnostic" property="diagnostic"/>
            </logic:equal>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey" align="right">Result 
          	<logic:equal name="diagnostic" property="new" value="true">
          		<font color="Red">*</font>
           	</logic:equal>          
          </td>
          <td> 
            <logic:equal name="diagnostic" property="new" value="true">
              <bigr:selectList name="diagnostic" property="result" parentProperty="diagnostic" legalValueSetProperty="resultList" firstValue=" " firstDisplayValue="Select a Test Result"/>
            </logic:equal>
            <logic:equal name="diagnostic" property="new" value="false">
              <bigr:selectList name="diagnostic" property="result" parentProperty="diagnostic" legalValueSetProperty="resultList" firstValue=" " firstDisplayValue="Select a Test Result"/>
              <html:hidden name="diagnostic" property="result"/>
            </logic:equal>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey" align="right">Diagnostic Note</td>
          <td> 
            <html:textarea name="diagnostic" property="note" rows="4" cols="80" onkeyup="maxTextarea(500);"/>
          </td>
        </tr>
        <tr class="white"> 
          <td colspan="2"> 
            <div align="center"> 
						<logic:notEqual name="diagnostic" property="new" value="true">
								<input type="button" name="btnSave" value="Update"
								  onclick="doSave(false);"/>
								<input type="button" name="btnSaveAdd" value="Update & Add Next"
								  onclick="doSave(true);"/>
						</logic:notEqual>		
						<logic:equal name="diagnostic" property="new" value="true">
								<input type="button" name="btnSave" value="Save"
								  onclick="doSave(false);"/>
								<input type="button" name="btnSaveAdd" value="Save & Add Next"
								  onclick="doSave(true);"/>
						</logic:equal>		
              <input type="button" value="Reset" name="btnReset" onclick="doReset();"/>
              <input type="button" name="btnCancel" value="Cancel"
                onclick="doCancel('<html:rewrite page="/ddc/Dispatch" name="path" property="consentLinkParams"/>');"> 
            </div>
            <logic:equal name="diagnostic" property="new" value="true">            
              <font color="Red">*</font> indicates a required field  
            </logic:equal>
          </td>
        </tr>
      </table>
    </td></tr>
  </table></p>
  </form>
</body>
</html>
