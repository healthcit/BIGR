<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, getServletContext(), "P");
%>
<%
	String pageTitle = "Select Donor";
	com.ardais.bigr.pdc.helpers.SelectDonorHelper helper = (com.ardais.bigr.pdc.helpers.SelectDonorHelper)request.getAttribute("helper");
	if (helper != null) {
		String title = helper.getPageTitle();
		if (title != null && !title.equalsIgnoreCase("")) {
			pageTitle = title;
		}
	}
%>

<bean:define name="helper" id="selectDonor" />

<html>
<head>
<title>Select Donor</title>
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
  var f = document.selectDonor;
  f.btnSubmit.disabled = isDisabled;
}

function validate() {
  setActionButtonEnabling(false);
  var isValid = isValidArdaisIdPrefix(trim(document.all.ardaisId.value), <%="Y".equalsIgnoreCase(helper.getImportedYN())%>);
  if (!isValid) {
    if (<%="Y".equalsIgnoreCase(helper.getImportedYN())%>) {
      var cId = trim(document.all.customerId.value);
      if ((cId != "") && (cId != null)) {
        isValid = true;
      }
      else {
        alert("Please enter a valid Donor Id or Donor Alias");
        setActionButtonEnabling(true);
        document.all.ardaisId.focus();
      }
    }
    else {
      alert("Please enter a valid Donor Id");
      setActionButtonEnabling(true);
      document.all.ardaisId.focus();
    }
  }
  return isValid;
}

</script>
</head>

<body class="bigr" onLoad="initPage();">
<form name="selectDonor" method="POST" action="<html:rewrite page="/ddc/Dispatch"/>"
      onsubmit="return validate();">
  <html:hidden name="selectDonor" property="op" />
  <html:hidden name="selectDonor" property="pathOp" />
  <html:hidden name="selectDonor" property="ctx" />
  <html:hidden name="selectDonor" property="importedYN" />
  <!--  the following field is used to tell the op to store donor/case id information
		in the session. (MR5337).  If this field is not present then the
		op will not store this information
  -->
  <html:hidden property="storeInfoInSession" value="true" />

  <div align="center">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" border="0" cellpadding="3" cellspacing="1">
            <bean:write name="selectDonor" property="messages" filter="false"/>
          </table>
        </td>
      </tr>
    </table>
  </div>

  <p><div align="center">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table border="0" class="foreground" cellpadding="3" cellspacing="1">
            <tr class="yellow"> 
              <td colspan="2" align="center"><b>Please Select a Donor</b></td>
            </tr>
            <logic:notEqual name="selectDonor" property="importedYN" value="Y">
              <tr class="white"> 
                <td class="grey" align="right"><b>Donor ID:</b></td>
                <td><html:text name="selectDonor" property="ardaisId" maxlength="12" size="13" /></td>
              </tr>
            </logic:notEqual>
            <logic:equal name="selectDonor" property="importedYN" value="Y">
              <tr class="white"> 
                <td class="grey" align="right"><b>Donor ID:</b></td>
                <td><html:text name="selectDonor" property="ardaisId" maxlength="12" size="30" /></td>
              </tr>
              <tr class="white"> 
                <td colspan="2" align="center">OR</td>
              </tr>
              <tr class="white"> 
                <td class="grey" align="right"><b>Donor Alias:</b></td>
                <td><html:text name="selectDonor" property="customerId" maxlength="30" size="30" /></td>
              </tr>
            </logic:equal>
            <tr class="white"> 
              <td colspan="2" align="center"><html:submit property="btnSubmit" value="Continue" /></td>
            </tr>
          </table>
        </td>
      </tr> 
    </table>  
  </div></p>
</form>
</body>
<logic:present name="selectDonor" property="donorLinkParams">
	<p><div align="center">
	<html:link page="/ddc/Dispatch" name="selectDonor" property="donorLinkParams">
	<bean:write name="selectDonor" property="donorLinkText"/>
	</html:link>
	<logic:present name="selectDonor" property="donorAndCaseLinkParams">
		<p><div align="center">
		<html:link page="/ddc/Dispatch" name="selectDonor" property="donorAndCaseLinkParams">
		<bean:write name="selectDonor" property="donorAndCaseLinkText"/>
		</html:link>
		</div></p>
	</logic:present>
	</div></p>
</logic:present>
</html>
