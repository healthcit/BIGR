<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ page import="com.ardais.bigr.api.ApiProperties"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, getServletContext(), "P");
%>
<bean:define name="helper" id="donor" />
<bean:define name="context" id="op" />

<html>
<head>
<title><bean:write name="op" property="listHeader"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript">
function initPage() {
	showBanner('<bean:write name="op" property="listHeader"/>');
}
</script>
</head>

<body class="bigr" onLoad="initPage();">
  <div align="center">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" border="0" cellpadding="3" cellspacing="1">
            <tr class="white"> 
              <td class="yellow" align="right"><b>Donor</b></td>
              <td>
                <bean:write name="donor" property="ardaisId" />
                <logic:notEmpty name="donor" property="customerId">
                  (<bean:write name="donor" property="customerId" />)
                </logic:notEmpty>
              </td>
              <logic:notPresent name="donor" property="consents">
                <td class="yellow" align="right"><b>does not have any cases.</b></td>
              </logic:notPresent>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </div>

  <logic:present name="donor" property="consents">
    <p><table class="background" cellpadding="0" cellspacing="0" border="0" width="100%">
      <tr><td>
        <table class="foreground" cellpadding="3" cellspacing="1" border="0" width="100%">
          <tr class="green"> 
            <logic:equal name="donor" property="importedYN" value="Y">
              <td><b>Case Id</b></td>
              <td><b>Case Alias</b></td>
              <td><b>Primary Dx from DI Path Report</b></td>
              <td align="center"><b>Full Text Pathology Report Entered?</b></td>
<% if (ApiProperties.isEnabledDDC_Check()) { %>                
              <td align="center"><b>Abstraction Complete?</b></td>
<% } %> 
            </logic:equal>
            <logic:notEqual name="donor" property="importedYN" value="Y">
              <td><b>Case Id</b></td>
              <td><b>Primary Dx from DI Path Report</b></td>
              <td align="center"><b>Full Text Pathology Report Entered?</b></td>
<% if (ApiProperties.isEnabledDDC_Check()) { %>                
              <td align="center"><b>Abstraction Complete?</b></td>
<% } %>              
            </logic:notEqual>
          </tr>
  		  <bean:write name="donor" property="startConsents"/>
          <logic:iterate name="donor" property="consents" id="consent">
            <tr class="white"> 
              <logic:equal name="donor" property="importedYN" value="Y">
                <td>
			      <html:link page="/ddc/Dispatch" name="donor" property="currentNextOpLinkParams">
                  <bean:write name="consent" property="consentId" /></html:link>
                </td>
                <td><bean:write name="consent" property="customerId" /></td>
                <td><bean:write name="consent" property="diagnosis" /></td>
                <td align="center"><bean:write name="consent" property="isRawPathReportEntered" /></td>
<% if (ApiProperties.isEnabledDDC_Check()) { %>                  
                <td align="center"><bean:write name="consent" property="isAbstracted" /></td>
<% } %>                
              </logic:equal>
              <logic:notEqual name="donor" property="importedYN" value="Y">
                <td>
			      <html:link page="/ddc/Dispatch" name="donor" property="currentNextOpLinkParams">
                  <bean:write name="consent" property="consentId" /></html:link>
                </td>
                <td><bean:write name="consent" property="diagnosis" /></td>
                <td align="center"><bean:write name="consent" property="isRawPathReportEntered" /></td>
<% if (ApiProperties.isEnabledDDC_Check()) { %>                                 
                <td align="center"><bean:write name="consent" property="isAbstracted" /></td>
<% } %>                
              </logic:notEqual>
            </tr>
    		<bean:write name="donor" property="nextConsent"/>
          </logic:iterate>
        </table>
      </td></tr>
    </table></p>
  </logic:present>
  
</body>
</html>
