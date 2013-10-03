<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.ardais.bigr.iltds.databeans.IrbVersionData" %>
<%@ page import="com.ardais.bigr.iltds.helpers.FormLogic" %>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<bean:define name="caseForm" id="myForm" type="com.ardais.bigr.dataImport.web.form.CaseForm"/>
<bean:define name="caseForm" property="txType" id="txType" type="java.lang.String"/>
<html>
<head>
<title>Pull Case</title>
<script language="JavaScript" src="<html:rewrite page="/js/bigrTextArea.js"/>"></script>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<html:rewrite page="/css/bigr.css"/>" type="text/css">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script type="text/javascript">
var myBanner = 'Consent Revocation';

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
  document.forms[0].pullRequestedBy.focus();
}

function onFormSubmit() {
  setActionButtonEnabling(false);
  return true;
}
   
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.forms[0];
  f.btnSubmit.disabled = isDisabled;
  f.btnReset.disabled = isDisabled;
  f.btnCancel.disabled = isDisabled;
}

</script>
</head>
<body class="bigr" onload="initPage();">
<html:form method="POST"
      action="/iltds/consent/revokeCase"
      onsubmit="return(onFormSubmit());">
  <div align="center">
    <table border="0" cellspacing="0" cellpadding="0" class="background">
      <tr> 
        <td> 
          <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
	        <% if (request.getAttribute("org.apache.struts.action.ERROR") != null) { %>
	        <tr class="yellow">
	          <td colspan="2">
                <font color="#FF0000">
	              <b><html:errors/></b>
	            </font>
	          </td>
	        </tr>
	        <% } %>
	        <logic:present name="com.ardais.BTX_ACTION_MESSAGES">
            <tr class="yellow"> 
              <td colspan="2" align="center"> 
                <font color="#FF0000"><b><bigr:btxActionMessages/></b></font>
 		      </td>
		    </tr>
		    </logic:present>
            <tr class="white"> 
              <td class="grey" align="right" nowrap><b>Case ID:</b></td>
              <td> 
                <bean:write name="caseForm" property="consentId"/>
  				<html:hidden name="caseForm" property="consentId"/>
  				<html:hidden name="caseForm" property="consentId2"/>
              </td>
            </tr>
            <tr class="white">
              <td class="grey" align="right" nowrap><b>Requested By:</b><font color="Red">&nbsp;*</font></td>
              <td>
		        <bigr:selectList name="caseForm" property="pullRequestedBy"
	              firstValue="" firstDisplayValue="Select Requested By"
		          legalValueSetProperty="pullRequestedByChoices"/>
              </td>
            </tr>
            <tr class="white"> 
              <td colspan="2"> 
                <div align="center"> 
                  <html:hidden name="caseForm" property="txType" value="<%=txType%>"/>
                  <html:submit property="btnSubmit" value="Revoke"/>
                  <html:reset property="btnReset" value="Reset"/>
                  <input type="button" name="btnCancel" value="Cancel" onclick="window.location.href='<html:rewrite page="/iltds/consent/selectCaseToRevoke.do"/>';"> 
                </div>
                <font color="Red">*</font> indicates a required field  
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
