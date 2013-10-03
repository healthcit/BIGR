<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>

<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, getServletContext(), "P");
%>
<bean:define name="clinicalFindingsForm" id="myForm" type="com.ardais.bigr.iltds.web.form.ClinicalFindingsForm"/>
<%
	String pageTitle = null;
	boolean isNew = myForm.isNewFinding();
	String formAction = null;
	String psaCheckBoxChecked = "";
	if (isNew) {
	  pageTitle = "Create Clinical Finding";
	  formAction = "/iltds/clinicalFindings/createClinicalFinding";
	}
	else {
	  pageTitle = "Modify Clinical Finding";
	  formAction = "/iltds/clinicalFindings/modifyClinicalFinding";
	  if (ApiFunctions.isEmpty(myForm.getClinicalFinding().getPsa())) {
	    psaCheckBoxChecked = "checked";
	  }
	}
%>
<html>
<head>
<title><%= pageTitle %></title>
<script language="JavaScript" src="<html:rewrite page="/js/bigrTextArea.js"/>"></script>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript">
function initPage() {
  if (parent.topFrame) {
    parent.topFrame.document.all.banner.innerHTML = '<%= pageTitle %>';
  }
}

function doReset() {
  setActionButtonEnabling(false);
  document.clinicalFindingsForm.reset();
  setActionButtonEnabling(true);
}

function doCancel(gotoURL) {
  setActionButtonEnabling(false);
  window.location.href=gotoURL;
}
   
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.clinicalFindingsForm;
  f.btnSave.disabled = isDisabled;
  f.btnReset.disabled = isDisabled;
  f.btnCancel.disabled = isDisabled;
}

function validate() {
  setActionButtonEnabling(false);
  var f = document.clinicalFindingsForm;
  var psaValue = trim(f.elements['clinicalFinding.psa'].value);
  var cbChecked = f.psaCheckBox.checked;
  //make sure that there is either a value for PSA or the not
  //available check box has been checked.
  if (psaValue.length < 1 && !cbChecked) {
    alert('Please enter a PSA value or check the "Not Available" checkbox');
    setActionButtonEnabling(true);
    f.elements['clinicalFinding.psa'].focus();
    return false;
  }
  else {
    return true;
  }
}

function togglePsaCheckBox() {
  var f = document.clinicalFindingsForm;
  var psaValue = trim(f.elements['clinicalFinding.psa'].value);
  if (psaValue.length < 1) {
    f.psaCheckBox.checked = true;
  }
  else {
    f.psaCheckBox.checked = false;
  }
}

function togglePsaValue() {
  var f = document.clinicalFindingsForm;
  if (f.psaCheckBox.checked) {
    f.elements['clinicalFinding.psa'].value = '';
  }
}
</script>
</head>

<body class="bigr" onLoad="initPage();">
<html:form action="<%=formAction%>"  onsubmit="return(validate());">
  
<%
  // DIV for errors
%>
  <div id="errorDiv" align="center">
    <table width="100%" border="1" cellspacing="1" cellpadding="1" class="foreground-small">
      <logic:present name="org.apache.struts.action.ERROR">
      <tr class="yellow"> 
	    <td> 
	      <div align="white">
	        <font color="#FF0000"><b><html:errors/></b></font>
	      </div>
	    </td>
	  </tr>
	  </logic:present>
      <logic:present name="com.ardais.BTX_ACTION_MESSAGES">
	  <tr class="white"> 
	    <td> 
	      <div align="left">
	        <font color="#000000"><b><bigr:btxActionMessages/></b></font>
	      </div>
	    </td>
	  </tr>
	  </logic:present>
	</table>
  </div>
  <p>
  <html:hidden name="clinicalFindingsForm" property="ardaisId"/>
  <html:hidden name="clinicalFindingsForm" property="consentId"/>
  <html:hidden name="clinicalFindingsForm" property="newFinding"/>

  <div align="center">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" border="0" cellpadding="3" cellspacing="1">
            <tr class="white"> 
              <td class="yellow" align="right"><b>Donor Id:</b></td>
              <td>
			    <html:link page="/iltds/clinicalFindings/getClinicalFindings.do" paramId="ardaisId" paramName="clinicalFindingsForm" paramProperty="ardaisId">
                  <bean:write name="clinicalFindingsForm" property="ardaisId"/>
                </html:link>
              </td>
            </tr>
            <tr class="white"> 
              <td class="yellow" align="right"><b>Consent Id:</b></td>
              <td>
                <bean:write name="clinicalFindingsForm" property="consentId"/>
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
            <b>Prostate Specific Antigen ~ PSA, free (serum)</b><font color="Red">*</font>
          </td>
          <td>
            <html:text name="clinicalFindingsForm" property="clinicalFinding.psa" maxlength="6" size="6" onkeyup="togglePsaCheckBox();"/>
            &nbsp; ng/mL &nbsp;&nbsp;&nbsp;&nbsp;<b>Or</b>&nbsp;&nbsp;&nbsp;
            <input type="checkbox" name="psaCheckBox" <%=psaCheckBoxChecked%> onclick='togglePsaValue();'> Not Available
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey" align="right"> 
            <b>Digital Rectal Exam</b><font color="Red">*</font>
          </td>
          <td> 
	        <bigr:selectList name="clinicalFindingsForm" property="clinicalFinding.dre"
              firstValue="" firstDisplayValue="Select"
	          legalValueSetProperty="dreChoices"/>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey" align="right">
            <b>Clinical Finding Notes</b>
          </td>
          <td> 
      		<html:textarea name="clinicalFindingsForm" property="clinicalFinding.clinicalFindingNotes" rows="10" cols="80" onkeyup="BigrTextAreaComments.enforceMaxLength()"/>
			<script language="JavaScript">
				var BigrTextAreaComments = new BigrTextArea('clinicalFinding.clinicalFindingNotes');
				BigrTextAreaComments.maxLength = 4000;
			</script>
          </td>
        </tr>
        <tr class="white"> 
          <td colspan="2"> 
            <div align="center"> 
			  <html:submit property="btnSave" value="Save"/>
              <input type="button" value="Reset" name="btnReset" onclick="doReset();"/>
              <input type="button" name="btnCancel" value="Cancel"
                onclick="doCancel('<html:rewrite page="/iltds/clinicalFindings/getClinicalFindings.do" paramId="ardaisId" paramName="clinicalFindingsForm" paramProperty="ardaisId"/>');"> 
            </div>
            <font color="Red">*</font> indicates a required field  
          </td>
        </tr>
      </table>
    </td></tr>
  </table></p>
  </html:form>
</body>
</html>
