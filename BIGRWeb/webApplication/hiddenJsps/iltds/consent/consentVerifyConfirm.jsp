<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%
  String ardaisId_1 = ApiFunctions.safeString(request.getParameter("ardaisId_1"));
  String consentId_1 = ApiFunctions.safeString(request.getParameter("consentId_1"));
  String bankable = ApiFunctions.safeString(request.getParameter("bankable"));
%>
<html>
<head>
<title>Confirm Consent Verification</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script>

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
  
  document.form1.Submit.focus();
}

function onClickCancel() {
  disableActionButtons();
  window.location = '<html:rewrite page="/iltds/Dispatch?op=ConsentStartVerify"/>';
}

function onFormSubmit() {
  disableActionButtons();
  return true;
}
  
function disableActionButtons() {
	document.form1.Submit.disabled = true;
	document.form1.Cancel.disabled = true;
}

var myBanner = 'Confirm Consent Verification';
</script>

<link rel="stylesheet" href="<html:rewrite page="/css/bigr.css"/>" type="text/css">
</head>
<body class="bigr" onload="initPage()">
<form name="form1" method="post" action="<html:rewrite page="/iltds/Dispatch"/>"
      onsubmit="return(onFormSubmit());">
  <div align="center"> 
    <table border="0" cellspacing="0" cellpadding="0" class="background">
      <tr> 
        <td> 
          <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
            <tr class="white"> 
              <td colspan="2" align="center"><b>Confirm</b></td>
            </tr>
            <tr class="white"> 
              <td class="grey">Donor ID:</td>
              <td> 
                <div align="left"><%= ardaisId_1 %> 
                  <input type="hidden" name="ardaisId" value="<%= ardaisId_1 %>">
                </div>
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey">Case ID:</td>
              <td> 
                <div align="left"><%= consentId_1 %> 
                  <input type="hidden" name="consentId" value="<%= consentId_1 %>">
                </div>
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey">Bankable:</td>
              <td><%= bankable %> 
                <input type="hidden" name="bankable" value="<%= bankable %>">
              </td>
            </tr>
            <tr class="white"> 
              <td colspan="2"> 
                <div align="center"> 
                  <input type="hidden" name="op" value="ConsentVerifyInsert">
                  <input type="submit" name="Submit" value="Submit">
  			      <input type="button" name="Cancel" value="Cancel"
				         onclick="onClickCancel(); return document.MM_returnValue">
                </div>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </div>
</form>
</body>
</html>
