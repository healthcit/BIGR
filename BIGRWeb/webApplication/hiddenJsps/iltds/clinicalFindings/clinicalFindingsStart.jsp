<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.api.ValidateIds" %>
<%@ page import="com.ardais.bigr.util.Constants" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, getServletContext(), "P");
%>
<%
	String pageTitle = "Select Donor and/or Consent (Clinical Findings)";
	String formAction = "/iltds/clinicalFindings/getClinicalFindings";
	String donorLinkText = null;
	String donorLinkAction = null;
	String caseLinkText = null;
	String caseLinkAction = null;
	if (session != null) {
	  String donorId = (String) session.getAttribute(Constants.MOST_RECENT_DONOR_ID);
	  String caseId = (String)session.getAttribute(Constants.MOST_RECENT_CASE_ID);
	  if (!ApiFunctions.isEmpty(donorId)) {
		donorLinkText = "Select Donor (" + donorId + ")";
		donorLinkAction = formAction + ".do?ardaisId=" + donorId;
	  }
	  if (!ApiFunctions.isEmpty(donorId) && !ApiFunctions.isEmpty(caseId)) {	    
		caseLinkText = "Select Donor (" + donorId + ") and Case (" + caseId + ")";
		caseLinkAction = formAction + ".do?ardaisId=" + donorId + "&consentId=" + caseId;
	  }
	}		
%>
<html>
<head>
<title><%= pageTitle %></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript">
function initPage() {
  if (parent.topFrame) {
    parent.topFrame.document.all.banner.innerHTML = '<%= pageTitle %>';
  }
  document.all.ardaisId.focus();
}
  
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.clinicalFindingsForm;
  f.btnSubmit.disabled = isDisabled;
}

function validate() {
  setActionButtonEnabling(false);
  var f = document.clinicalFindingsForm;
  var donorIsValid = isValidArdaisIdPrefix(f.ardaisId.value, false) || isValidArdaisIdPrefix(f.ardaisId.value, true);
  var consentIsValid = isValidCaseIdPrefix(f.consentId.value, false) || isValidCaseIdPrefix(f.consentId.value, true);
  if (!donorIsValid && !consentIsValid) {
    alert("Please enter a valid donor and/or consent id");
    setActionButtonEnabling(true);
    document.all.ardaisId.focus();
  }
  return donorIsValid || consentIsValid;
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

  <p><div align="center">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table border="0" class="foreground" cellpadding="3" cellspacing="1">
            <tr class="yellow"> 
              <td colspan="2" align="center"><b>Please Select a Donor or Consent</b></td>
            </tr>
            <tr class="white"> 
              <td class="grey" align="right"><b>Donor id:</b></td>
              <td><html:text name="clinicalFindingsForm" property="ardaisId" maxlength="12" size="13" /></td>
            </tr>
            <tr class="white"> 
              <td colspan="2" align="center"><b>Or</b></td>
            </tr>
            <tr class="white"> 
              <td class="grey" align="right"><b>Consent id:</b></td>
              <td><html:text name="clinicalFindingsForm" property="consentId" maxlength="12" size="13" /></td>
            </tr>
            <tr class="white"> 
              <td colspan="2" align="center"><html:submit property="btnSubmit" value="Continue" /></td>
            </tr>
          </table>
        </td>
      </tr> 
    </table>  
  </div></p>
  <%
  	if (!ApiFunctions.isEmpty(donorLinkAction)) {
  %>
		<p><div align="center">
		<html:link page="<%=donorLinkAction%>">
		<%=donorLinkText%>
		</html:link>
		</div></p>
  <%
    }
  %>
  <%
  	if (!ApiFunctions.isEmpty(caseLinkAction)) {
  %>
		<p><div align="center">
		<html:link page="<%=caseLinkAction%>">
		<%=caseLinkText%>
		</html:link>
		</div></p>
  <%
    }
  %>
</html:form>
</body>
</html>
