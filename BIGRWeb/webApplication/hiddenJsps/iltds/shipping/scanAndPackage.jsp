<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ValidateIds" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html"  prefix="html" %>
<%@ taglib uri="/tld/struts-bean"  prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<html>
<head>
<%--
  ** If the confirmOnly request parameter is true, we don't get any more
  ** input, we just show a summary of what we did and confirm to the user
  ** that the manifest has been successfully scanned and packaged.
  ** Otherwise, if the ActionForm's allBoxesConfirmed parameter is true,
  ** we've confirmed all of the box ids and now we need to get the tracking
  ** number.  When allBoxesConfirmed is false, we need to continue prompting
  ** for more box ids.  Here, we set up beans that make it easier for us
  ** to test which variation of the page we're generating.
  --%>
<logic:present parameter="confirmOnly">
	<logic:equal parameter="confirmOnly" value="true">
	  <bean:define id="generateConfirmOnly" value="true" type="java.lang.String"/>
	</logic:equal>
</logic:present>
<logic:notPresent name="generateConfirmOnly" scope="page">
	<logic:equal name="scanAndPackageForm" property="allBoxesConfirmed" value="true">
    <bean:define id="generateGetTrackingNumber" value="true" type="java.lang.String"/>
	</logic:equal>
	<logic:notEqual name="scanAndPackageForm" property="allBoxesConfirmed" value="true">
    <bean:define id="generateGetBox" value="true" type="java.lang.String"/>
	</logic:notEqual>
</logic:notPresent>
<title>Scan and Package Manifest</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<html:rewrite page="/css/bigr.css"/>" type="text/css">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script type="text/javascript">
var myBanner = 'Scan and Package Manifest';

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;

<logic:present name="generateGetBox" scope="page">
  document.forms[0].boxId.focus();
</logic:present>
<logic:present name="generateGetTrackingNumber" scope="page">
  document.forms[0]['manifestDto.trackingNumber'].focus();
</logic:present>
<logic:present name="generateConfirmOnly" scope="page">
  document.forms[0].btnPrintManifest.focus();
</logic:present>
}

function onClickPrintManifest() {
  var theUrl = '<html:rewrite page="/iltds/shipping/printManifest.do"/>' +
    '?autoPrint=true&manifestNumber=<bean:write name="scanAndPackageForm" property="manifestDto.manifestNumber"/>';
  var w = window.open(theUrl, 'BIGRManifest',
    'scrollbars=yes,resizable=yes,width=700,height=700,top=0');
  w.focus();
}

function onFormSubmit() {
<%-- Confirm-only pages don't have any submit actions so prohibit submit. --%>
<logic:notPresent name="generateConfirmOnly" scope="page">
  setActionButtonEnabling(false);
  return true;
</logic:notPresent>
<logic:present name="generateConfirmOnly" scope="page">
  return false;
</logic:present>
}

function doSubmitForm() {
  if (onFormSubmit()) {
    document.forms[0].submit();
  }
}

function checkTabBoxId(event) { 	
	var code = 0;
	code = event.keyCode;  
	if (code == 9) {
	  var textBox = document.all.boxId;
	  textBox.value = trim(textBox.value);
		if (textBox.value.length > 0) {
			doSubmitForm();
			event.returnValue = false;
		}
	}
}
   
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.forms[0];
  setInputEnabled(f, 'btnContinue', isEnabled);
  <%-- The print button isn't a submit-action button so don't disable it. --%>
}
</script>
</head>
<body class="bigr" onload="initPage();">
<html:errors/>
<bigr:btxActionMessages/>
<logic:present name="generateConfirmOnly" scope="page">
  <ul><li>
    Manifest <bean:write name="scanAndPackageForm" property="manifestDto.manifestNumber"/>
    was packed and linked to tracking number
    <bean:write name="scanAndPackageForm" property="manifestDto.trackingNumber"/>.
  </li></ul>
</logic:present>
<%
  // Set the form submit action.  In the confirmOnly case, there's nothing
  // to submit but the Struts html:form tag requires a valid action,
  // so we give it the "start" action to keep it happy.  The value of
  // submitAction will get overwritten with real submit targets later
  // for the variations of this page that actually submit to somewhere.
  //
  String submitAction = "/iltds/shipping/scanAndPackageStart";
%>
<logic:present name="generateGetBox" scope="page">
  <% submitAction = "/iltds/shipping/scanAndPackageConfirmBox"; %>
</logic:present>
<logic:present name="generateGetTrackingNumber" scope="page">
  <% submitAction = "/iltds/shipping/scanAndPackageFinish"; %>
</logic:present>
<html:form method="POST"
			action="<%= submitAction %>"
      onsubmit="return(onFormSubmit());">
  <div align="center">
    <table border="0" cellspacing="0" cellpadding="0" class="background">
      <tr> 
        <td> 
          <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
            <tr class="yellow" align="center"> 
              <td colspan="2"><b>
                Manifest <bean:write name="scanAndPackageForm" property="manifestDto.manifestNumber"/>
                <html:hidden property="manifestDto.manifestNumber"/>
              </b></td>
            </tr>
            <tr class="grey"> 
              <td><b>Expected Boxes 
                (<bean:write name="scanAndPackageForm" property="allBoxesCount"/>)
              </b></td>
              <td><b>Confirmed Boxes 
                (<bean:write name="scanAndPackageForm" property="confirmedBoxIdsCount"/>)
              </b></td>
            </tr>
<logic:iterate id="box"
  name="scanAndPackageForm" property="allBoxes"
  type="com.ardais.bigr.iltds.web.form.ScanAndPackageForm.BoxStatus">
            <tr class="grey"> 
              <td>
                <bean:write name="box" property="boxId"/>
                <input type="hidden" name="allBoxIds"
                  value="<bean:write name="box" property="boxId"/>"/>
              </td>
              <td>
								<logic:equal name="box" property="confirmed" value="true">
								  OK
                  <input type="hidden" name="confirmedBoxIds"
                    value="<bean:write name="box" property="boxId"/>"/>
								</logic:equal>
              </td>
            </tr>
</logic:iterate>
<logic:present name="generateGetBox" scope="page">
            <tr class="white"> 
              <td class="grey" nowrap>Scan Box Id:</td>
              <td> 
                <html:text property="boxId"
                  size="<%= String.valueOf(ValidateIds.LENGTH_BOX_ID + 5) %>"
                  maxlength="<%= String.valueOf(ValidateIds.LENGTH_BOX_ID) %>"
                  value=""
							    onkeydown="checkTabBoxId(event);"/>
              </td>
            </tr>
</logic:present>
<logic:present name="generateGetTrackingNumber" scope="page">
            <%-- We deliberately don't autosubmit on tab from the tracking
              ** number field since that finishes the transaction and
              ** records the changes in the database, and we want to give the
              ** user a chance to see how the tracking number scanned in
              ** and otherwise check things over before doing that. --%>
            <tr class="white"> 
              <td class="grey" nowrap>Enter tracking number:</td>
              <td> 
                <html:text property="manifestDto.trackingNumber"
                  size="30" maxlength="25" value=""/>
              </td>
            </tr>
</logic:present>
            <tr class="white"> 
              <td colspan="2" align="center"> 
<logic:present name="generateGetBox" scope="page">
                <input type="submit" name="btnContinue" value="Continue">
</logic:present>
<logic:present name="generateGetTrackingNumber" scope="page">
                <input type="submit" name="btnContinue" value="Finish">
</logic:present>
<logic:present name="generateConfirmOnly" scope="page">
                <input type="button" name="btnPrintManifest" value="Print Manifest"
                  onclick="onClickPrintManifest();">
</logic:present>
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
