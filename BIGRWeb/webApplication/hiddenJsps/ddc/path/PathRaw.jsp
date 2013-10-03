<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, getServletContext(), "P");
%>
<bean:define name="helper" id="path" />
<bean:define name="context" id="windowType" />

<html>
<head>
<title>Full Text Pathology Report</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<script language="JavaScript">
function showReadWrite() {
  document.all.readOnly.style.display = "none";
  document.all.readOnlyButtons.style.display = "none";
  document.all.readWrite.style.display = "block";
  document.all.readWriteButtons.style.display = "block";
}

function onFormSubmit() {
  disableActionButtons();
  return true;
}

function doSubmit() {
  if (onFormSubmit()) {
    document.all.path.submit();
  }
}

function doCancel() {
  disableActionButtons();
  window.location.href='<html:rewrite page="/ddc/Dispatch" name="path" property="donorRawLinkParams"/>';
}
  
function disableActionButtons() {
  var f = document.path;
  if (f.btnEdit) f.btnEdit.disabled = true;
  if (f.btnUpdate) f.btnUpdate.disabled = true;
  if (f.btnSave) f.btnSave.disabled = true;
  if (f.btnClose) f.btnClose.disabled = true;
  if (f.btnPrint) f.btnPrint.disabled = true;
  if (f.btnCancel) f.btnCancel.disabled = true;
}
</script>
</head>

<body class="bigr" <logic:notEqual name="windowType" value="popup">onLoad="if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = 'Full Text Pathology Report';"</logic:notEqual>>
  <form name="path" method="POST" action="<html:rewrite page="/ddc/Dispatch"/>">
  <input type="hidden" name="op" value="PathRawEdit">
  <html:hidden name="path" property="pathReportId" />
  <html:hidden name="path" property="ardaisId" />
  <html:hidden name="path" property="consentId" />
  <html:hidden name="path" property="donorImportedYN"/>

  <div align="center">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" border="0" cellpadding="3" cellspacing="1">
            <bean:write name="path" property="messages" filter="false"/>
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
                <logic:notEqual name="windowType" value="popup">
              	  <html:link page="/ddc/Dispatch" name="path" property="donorRawLinkParams">
                  <bean:write name="path" property="ardaisId" />
                  </html:link>
                  <logic:notEmpty name="path" property="donorCustomerId">
                    (<bean:write name="path" property="donorCustomerId" />)
                  </logic:notEmpty>
                </logic:notEqual>

                <logic:equal name="windowType" value="popup">
                  <bean:write name="path" property="ardaisId" />
                  <logic:notEmpty name="path" property="donorCustomerId">
                    (<bean:write name="path" property="donorCustomerId" />)
                  </logic:notEmpty>
                </logic:equal>
              </td>
            </tr>
            <tr class="white"> 
              <td class="yellow" align="right"><b>Case</b></td>
              <td>
                <bean:write name="path" property="consentId" />
                <logic:notEmpty name="path" property="consentCustomerId">
                  (<bean:write name="path" property="consentCustomerId" />)
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
              <logic:present name="path" property="rawPathReport">
                <p><bigr:beanWrite name="path" property="rawPathReport" filter="true" whitespace="true"/></p>
              </logic:present>
              <logic:notPresent name="path" property="rawPathReport">
                <p><div align="center">&lt;The Full Text Pathology Report has not been entered&gt;</div></p>
              </logic:notPresent>
            </logic:equal>
            
            <logic:notEqual name="windowType" value="popup">
              <div id="readWrite" align="center" <logic:notEqual name="windowType" value="new">style="display: none"</logic:notEqual>>
                <html:textarea name="path" property="rawPathReport" cols="100" rows="25"/>
              </div>
              <logic:equal name="windowType" value="existing">
                <div id="readOnly">
                  <p><bigr:beanWrite name="path" property="rawPathReport" filter="true" whitespace="true"/></p>
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
      <input type="button" name="btnClose" value="Close" onclick="window.close();">&nbsp;
      <logic:present name="path" property="rawPathReport">
        <input type="button" name="btnPrint" value="Print" onclick="window.print();">
      </logic:present>
    </div>
  </logic:equal>

  <logic:notEqual name="windowType" value="popup">
    <div id="readWriteButtons" align="center" <logic:notEqual name="windowType" value="new">style="display: none"</logic:notEqual>>
      <logic:present name="path" property="rawPathReport">
      	<input type="button" name="btnUpdate" value="Update" onclick="doSubmit();">
      </logic:present>
      <logic:notPresent name="path" property="rawPathReport">
      	<input type="button" name="btnSave" value="Save" onclick="doSubmit();">
      </logic:notPresent>
      <input type="button" name="btnCancel" value="Cancel" onclick="doCancel();">
    </div>
    <logic:equal name="windowType" value="existing">
      <div id="readOnlyButtons" align="center">
        <input type="button" name="btnEdit" value="Edit" onclick="showReadWrite();">
        <input type="button" name="btnPrint" value="Print" onclick="window.print();">
      </div>
    </logic:equal>
  </logic:notEqual>
  
  </form>
</body>
<script>
window.focus();
</script>
</html>
