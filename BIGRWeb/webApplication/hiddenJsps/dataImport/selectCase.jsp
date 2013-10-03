<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.api.Escaper" %>
<%@ page import="com.ardais.bigr.api.ValidateIds" %>
<%@ page import="com.ardais.bigr.util.Constants" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, getServletContext(), "P");
	String txType = "";
%>
<logic:present name="caseForm" property="txType">
<bean:define name="caseForm" property="txType" id="txTypeDefined" type="java.lang.String"/>
<%
	txType = txTypeDefined;
%>
</logic:present>
<logic:notPresent name="caseForm" property="txType">
<%
	txType = request.getParameter("txType");
%>
</logic:notPresent>

<%
	String formAction = "";
	String banner = "Select Case";
	String quickLinkText = null;
	String quickLinkAction = null;
	String caseAction = request.getParameter("caseAction");
	if (txType.equalsIgnoreCase(Constants.TRANSACTION_ILTDS)) {
		if (caseAction.equalsIgnoreCase("pullCase")) {
			formAction = "/dataImport/pullCaseStart";
			banner = "Case Pull";
		}
		else if (caseAction.equalsIgnoreCase("revokeCase")) {
			formAction = "/iltds/consent/revokeCaseStart";
			banner = "Consent Revocation";
		}
	}
	else if (txType.equalsIgnoreCase(Constants.TRANSACTION_SR)) {
		if (caseAction.equalsIgnoreCase("modifyCase")) {
			formAction = "/dataImport/getCaseFormSummary";
			banner = banner + " To Modify";
			if (session != null) {
				String donorId = (String) session.getAttribute(Constants.MOST_RECENT_DONOR_ID);
				String donorAlias = (String) session.getAttribute(Constants.MOST_RECENT_DONOR_ALIAS);
				String caseId = (String)session.getAttribute(Constants.MOST_RECENT_CASE_ID);
				String caseAlias = (String)session.getAttribute(Constants.MOST_RECENT_CASE_ALIAS);
				if (!ApiFunctions.isEmpty(donorId) &&
					!ApiFunctions.isEmpty(caseId) &&
					!ApiFunctions.isEmpty(ValidateIds.validateId(caseId, ValidateIds.TYPESET_CASE_IMPORTED, false))) {
				  StringBuffer quickLinkTextBuffer = new StringBuffer(100);
				  quickLinkTextBuffer.append("Select Case ");
				  quickLinkTextBuffer.append(caseId);
				  if (!ApiFunctions.isEmpty(caseAlias)) {
					  quickLinkTextBuffer.append(" (");
					  quickLinkTextBuffer.append(Escaper.htmlEscapeAndPreserveWhitespace(caseAlias));
					  quickLinkTextBuffer.append(")");
				  }
				  quickLinkTextBuffer.append(" for Donor ");
				  quickLinkTextBuffer.append(donorId);
				  if (!ApiFunctions.isEmpty(donorAlias)) {
					  quickLinkTextBuffer.append(" (");
					  quickLinkTextBuffer.append(Escaper.htmlEscapeAndPreserveWhitespace(donorAlias));
					  quickLinkTextBuffer.append(")");
				    
				  }
				  quickLinkText = quickLinkTextBuffer.toString();
				  quickLinkAction = formAction + ".do?consentId=" + caseId;
				}
			}		
		}
		else if (caseAction.equalsIgnoreCase("pullCase")) {
			formAction = "/dataImport/pullCaseStart";
			banner = banner + " To Pull";
		}
	}
%>
<html>
<head>
<title>Select Case</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript">
function initPage() {
  if (parent.topFrame) {
    parent.topFrame.document.all.banner.innerHTML = '<%=banner%>';
  }
  document.all.consentId.focus();
}
  
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.caseForm;
  f.btnSubmit.disabled = isDisabled;
}

function validate() {
  setActionButtonEnabling(false);
  var isValid = ""
  var msg = "";
  
  <% if (txType.equalsIgnoreCase(Constants.TRANSACTION_ILTDS)) { %>
  		isValid = (isValidCaseIdPrefix(trim(document.all.consentId.value), false) 
  				&& isValidCaseIdPrefix(trim(document.all.consentId2.value), false));  		
  		msg = "Please enter valid Case Ids";
  <% }
     else if (txType.equalsIgnoreCase(Constants.TRANSACTION_SR)) { %>
  		isValid = isValidCaseIdPrefix(trim(document.all.consentId.value), true);
  		if (!isValid) {
		    var cId = trim(document.all.customerId.value);
		    if ((cId != "") && (cId != null)) {
		      isValid = true;
		    }
  		}
  		msg = "Please enter a valid Case Id or Case Alias";
  <% } %>
  
  if (!isValid) {
      alert(msg);
      setActionButtonEnabling(true);
      document.all.consentId.focus();
  }  
  
  return isValid;
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
              <td colspan="2" align="center"><b>Please Select a Case</b></td>
            </tr>
            <tr class="white"> 
              <td class="grey" align="right"><b>Case Id:</b></td>
              <td><html:text name="caseForm" property="consentId" maxlength="12" size="30" /></td>
            </tr>
<% if (txType.equalsIgnoreCase(Constants.TRANSACTION_SR)) { %>
            <tr class="white"> 
              <td colspan="2" align="center">OR</td>
            </tr>
            <tr class="white"> 
              <td class="grey" align="right"><b>Case Alias:</b></td>
              <td><html:text name="caseForm" property="customerId" maxlength="30" size="30" /></td>
            </tr>
<% } 
   else if (txType.equalsIgnoreCase(Constants.TRANSACTION_ILTDS)) { %>
            <tr class="white"> 
              <td class="grey" align="right"><b>Case Id (confirm):</b></td>
              <td><html:text name="caseForm" property="consentId2" maxlength="12" size="30" /></td>
            </tr>
<% }%>          
            <tr class="white"> 
              <td colspan="2" align="center"><html:submit property="btnSubmit" value="Continue" /></td>
            </tr>
            <html:hidden name="caseForm" property="txType" value="<%=txType%>"/>
          </table>
        </td>
      </tr> 
    </table>  
  </div></p>
  <%
  	if (!ApiFunctions.isEmpty(quickLinkAction)) {
  %>
		<p><div align="center">
		<html:link page="<%=quickLinkAction%>">
		<%=quickLinkText%>
		</html:link>
		</div></p>
  <%
    }
  %>
</html:form>
</body>
</html>
