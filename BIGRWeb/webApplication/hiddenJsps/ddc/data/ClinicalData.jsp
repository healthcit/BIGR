<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, getServletContext(), "P");
%>
<bean:define name="helper" id="clinical"/>

<html>
<head>
<title>Clinical Data Extraction</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
</head>
<script language="JavaScript">
function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = '<bean:write name="clinical" property="categoryDisplay"/>';
  document.all.clinicalData.focus();
}
</script>
<body class="bigr" onLoad="initPage();">
  <form name="clinical" method="POST" action="<html:rewrite page="/ddc/Dispatch"/>">
  <input type="hidden" name="op" value="ClinicalDataEdit">
  <html:hidden name="clinical" property="clinicalDataId"/>
  <html:hidden name="clinical" property="ardaisId"/>
  <html:hidden name="clinical" property="consentId"/>
  <html:hidden name="clinical" property="category"/>
  <html:hidden name="clinical" property="donorImportedYN"/>

  <div align="center">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" border="0" cellpadding="3" cellspacing="1">
            <tr class="white"> 
              <td class="yellow" align="right"><b>Donor</b></td>
              <td>
              	<html:link page="/ddc/Dispatch" name="clinical" property="donorLinkParams">
                <bean:write name="clinical" property="ardaisId"/>
                </html:link>
                <logic:notEmpty name="clinical" property="donorCustomerId">
                  (<bean:write name="clinical" property="donorCustomerId" />)
                </logic:notEmpty>
              </td>
            </tr>
            <tr class="white">
              <td class="yellow" align="right"><b>Case</b></td>
              <td>
              	<html:link page="/ddc/Dispatch" name="clinical" property="caseLinkParams">
                <bean:write name="clinical" property="consentId"/>
                </html:link>
                <logic:notEmpty name="clinical" property="consentCustomerId">
                  (<bean:write name="clinical" property="consentCustomerId" />)
                </logic:notEmpty>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </div>
  
  <p><table class="foreground" border="0" cellpadding="3" cellspacing="0" width="100%">
    <tr class="green"> 
      <td align="left" style="border: 1px solid #336699;"> 
        <b><bean:write name="clinical" property="categoryDisplay"/></b>
      </td>
    </tr>
    <tr class="white">
      <td align="center">
        <html:textarea name="clinical" property="clinicalData" cols="100" rows="25"/>
      </td>
    </tr>
  </table></p>

  <div align="center">
		<logic:notPresent name="clinical" property="clinicalData">
    	<input type="button" value="Save" onclick="document.all.clinical.submit();">
		</logic:notPresent>
		<logic:present name="clinical" property="clinicalData">
    	<input type="button" value="Update" onclick="document.all.clinical.submit();">
		</logic:present>
    <input type="button" value="Cancel" onclick="window.location.href='<html:rewrite page="/ddc/Dispatch" name="clinical" property="caseLinkParams"/>';">
  </div>

  </form>
</body>
</html>
