<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, getServletContext(), "P");
%>
<bean:define name="helper" id="consent" />
<bean:define name="context" id="windowType" />

<html>
<head>
<title>Case Profile Notes</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<script language="JavaScript">
function showReadWrite() {
  document.all.readOnly.style.display = "none";
  document.all.readOnlyButtons.style.display = "none";
  document.all.readWrite.style.display = "block";
  document.all.readWriteButtons.style.display = "block";
}
</script>
</head>

<body class="bigr" <logic:notEqual name="windowType" value="popup">onLoad="if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = 'Case Profile Notes';"</logic:notEqual>>
  <form name="consent" method="POST" action="<html:rewrite page="/ddc/Dispatch"/>">
  <input type="hidden" name="op" value="CaseProfileNotesEdit">
  <html:hidden name="consent" property="ardaisId" />
  <html:hidden name="consent" property="consentId" />
  <html:hidden name="consent" property="donorImportedYN"/>

  <p>

  <div align="center">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" border="0" cellpadding="3" cellspacing="1">
            <tr class="white"> 
              <td class="yellow" align="right"><b>Donor</b></td>
              <td>
                <logic:equal name="windowType" value="popup">
                  <bean:write name="consent" property="ardaisId" />
                  <logic:notEmpty name="consent" property="donorCustomerId">
                    (<bean:write name="consent" property="donorCustomerId" />)
                  </logic:notEmpty>
                </logic:equal>
                <logic:notEqual name="windowType" value="popup">
              	  <html:link page="/ddc/Dispatch" name="consent" property="donorLinkParams">
                  <bean:write name="consent" property="ardaisId" />
                  </html:link>
                  <logic:notEmpty name="consent" property="donorCustomerId">
                    (<bean:write name="consent" property="donorCustomerId" />)
                  </logic:notEmpty>
                </logic:notEqual>
              </td>
            </tr>
            <tr class="white"> 
              <td class="yellow" align="right"><b>Case</b></td>
              <td>
                <bean:write name="consent" property="consentId" />
                <logic:notEmpty name="consent" property="customerId">
                  (<bean:write name="consent" property="customerId" />)
                </logic:notEmpty>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </div>

  <p><table class="background" cellpadding="0" cellspacing="0" border="0" width="100%">
    <tr><td> 
      <table border="0" cellspacing="1" cellpadding="3" class="foreground" width="100%">
        <tr class="white"> 
          <td>
            <logic:equal name="windowType" value="popup">
              <logic:present name="consent" property="diCaseProfileNotes">
                <p><bigr:beanWrite name="consent" property="diCaseProfileNotes" filter="true" whitespace="true"/></p>
              </logic:present>
              <logic:notPresent name="consent" property="diCaseProfileNotes">
                <p><div align="center">&lt;Case Profile Notes has not been entered&gt;</div></p>              
              </logic:notPresent>
            </logic:equal>
            
            <logic:notEqual name="windowType" value="popup">
              <div id="readWrite" align="center" <logic:notEqual name="windowType" value="new">style="display: none"</logic:notEqual>>
                <html:textarea name="consent" property="diCaseProfileNotes" cols="100" rows="25"/>
              </div>
              <logic:equal name="windowType" value="existing">
                <div id="readOnly">
                  <p><bigr:beanWrite name="consent" property="diCaseProfileNotes" filter="true" whitespace="true"/></p>
                </div>
              </logic:equal>
            </logic:notEqual>
          </td>
        </tr>
      </table>
    </td></tr>
  </table></p>
  
  <logic:equal name="windowType" value="popup">
    <div align="center">
      <input type="button" value="Close" onclick="window.close();">&nbsp;
      <logic:present name="consent" property="diCaseProfileNotes">
        <input type="button" value="Print" onclick="window.print();">
      </logic:present>
    </div>
  </logic:equal>

  <logic:notEqual name="windowType" value="popup">
    <div id="readWriteButtons" align="center" <logic:notEqual name="windowType" value="new">style="display: none"</logic:notEqual>>
      <logic:present name="consent" property="diCaseProfileNotes">
      	<input type="button" value="Update" onclick="document.all.consent.submit();">
      </logic:present>
      <logic:notPresent name="consent" property="diCaseProfileNotes">
      	<input type="button" value="Save" onclick="document.all.consent.submit();">
      </logic:notPresent>
      <input type="button" value="Cancel" onclick="window.location.href='<html:rewrite page="/ddc/Dispatch" name="consent" property="donorLinkParams"/>';">
    </div>
    <logic:equal name="windowType" value="existing">
      <div id="readOnlyButtons" align="center">
        <input type="button" value="Edit" onclick="showReadWrite();">
        <input type="button" value="Print" onclick="window.print();">
      </div>
    </logic:equal>
  </logic:notEqual>
  
  </form>
</body>
</html>
