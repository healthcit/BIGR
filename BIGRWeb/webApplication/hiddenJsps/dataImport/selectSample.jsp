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
%>

<%
	String formAction = "";
	String banner = "Select Sample";
	String quickLinkText = null;
	String quickLinkAction = null;
	String sampleAction = request.getParameter("sampleAction");
	if (sampleAction.equalsIgnoreCase("modifySample")) {
		formAction = "/dataImport/getSampleFormSummary";
		banner = banner + " To Modify";
		if (session != null) {
			String donorId = (String) session.getAttribute(Constants.MOST_RECENT_DONOR_ID);
			String donorAlias = (String) session.getAttribute(Constants.MOST_RECENT_DONOR_ALIAS);
			String caseId = (String)session.getAttribute(Constants.MOST_RECENT_CASE_ID);
			String caseAlias = (String) session.getAttribute(Constants.MOST_RECENT_CASE_ALIAS);
			String sampleId = (String)session.getAttribute(Constants.MOST_RECENT_SAMPLE_ID);
			String sampleAlias = (String) session.getAttribute(Constants.MOST_RECENT_SAMPLE_ALIAS);
			if (!ApiFunctions.isEmpty(donorId) &&
				!ApiFunctions.isEmpty(caseId) &&
				!ApiFunctions.isEmpty(sampleId) &&
				!ApiFunctions.isEmpty(ValidateIds.validateId(sampleId, ValidateIds.TYPESET_SAMPLE_IMPORTED, false))) {
			  StringBuffer buff = new StringBuffer(100);
			  buff.append("Select sample ");
			  buff.append(sampleId);
			  if (!ApiFunctions.isEmpty(sampleAlias)) {
			    buff.append(" (");
			    buff.append(sampleAlias);
			    buff.append(")");
			  }
			  buff.append(" from case ");
			  buff.append(caseId);
			  if (!ApiFunctions.isEmpty(caseAlias)) {
			    buff.append(" (");
			    buff.append(caseAlias);
			    buff.append(")");
			  }
			  buff.append(" for donor ");
			  buff.append(donorId);
			  if (!ApiFunctions.isEmpty(donorAlias)) {
			    buff.append(" (");
			    buff.append(donorAlias);
			    buff.append(")");
			  }
			  quickLinkText = Escaper.htmlEscape(buff.toString());
			  quickLinkAction = formAction + ".do?sampleData.sampleId=" + Escaper.urlEncode(sampleId);
			}
		}		
	}
%>
<html>
<head>
<title>Select Sample</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript">
function initPage() {
  if (parent.topFrame) {
    parent.topFrame.document.all.banner.innerHTML = '<%=banner%>';
  }
  document.all["sampleData.sampleId"].focus();
}
  
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.sampleForm;
  f.btnSubmit.disabled = isDisabled;
}

function validate() {
  setActionButtonEnabling(false);
  var isValid = isValidSampleIdPrefix(document.all["sampleData.sampleId"].value, true);
  if (!isValid) {
    var cId = trim(document.all["sampleData.sampleAlias"].value);
    if ((cId != "") && (cId != null)) {
      isValid = true;
    }
    else {
      alert("Please enter a valid Sample Id or Sample Alias");
      setActionButtonEnabling(true);
      document.all["sampleData.sampleId"].focus();
    }
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

  <p>
    <div align="center">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table border="0" class="foreground" cellpadding="3" cellspacing="1">
            <tr class="yellow"> 
              <td colspan="2" align="center"><b>Please Select a Sample</b></td>
            </tr>
            <tr class="white"> 
              <td class="grey" align="right"><b>Sample Id:</b></td>
              <td><html:text name="sampleForm" property="sampleData.sampleId" maxlength="10" size="30" /></td>
            </tr>
            <tr class="white"> 
              <td colspan="2" align="center">OR</td>
            </tr>
            <tr class="white"> 
              <td class="grey" align="right"><b>Sample Alias:</b></td>
              <td><html:text name="sampleForm" property="sampleData.sampleAlias" maxlength="30" size="30" /></td>
            </tr>
            <tr class="white"> 
              <td colspan="2" align="center"><html:submit property="btnSubmit" value="Continue" /></td>
            </tr>
          </table>
        </td>
      </tr> 
    </table>  
    </div>
  </p>
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
