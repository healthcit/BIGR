<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.ardais.bigr.util.Constants" %>
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

<%  
	String formAction = "/iltds/consent/selectCaseToRevoke.do";	
%>
`
<html>
<head>
<title>Case Revoked</title>
<script language="JavaScript" src="<html:rewrite page="/js/bigrTextArea.js"/>"></script>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<html:rewrite page="/css/bigr.css"/>" type="text/css">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script type="text/javascript">
var myBanner = 'Case Revoked';

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
}

function onFormSubmit() {
  setActionButtonEnabling(false);
  return true;
}
   
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.forms[0];
  f.btnRevokeAnother.disabled = isDisabled;
  f.btnPickList.disabled = isDisabled;
}


function generatePickList() {
  setActionButtonEnabling(false);
  var URL = '<html:rewrite page="/dataImport/generateCasePickList.do"/>' + "?consentId=" + "<%=myForm.getConsentId()%>" + "&txType=" + "<%=myForm.getTxType()%>" + "&caseAction=revokeCase";
  var w = window.open(URL, "PickList" + "<%=myForm.getConsentId()%>", "scrollbars=yes,resizable=yes,top=0,left=0,width=1000,height=700");
  w.focus();
  setActionButtonEnabling(true);
}

</script>
</head>
<body class="bigr" onload="initPage();">
<html:form method="POST"
      action="<%=formAction%>"
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
          </table>
        </td>
      </tr>
    </table>
    <br>
    <table border="0" cellspacing="0" cellpadding="0" class="background">
      <tr class="white"> 
		<html:hidden name="caseForm" property="txType"/>
        <td align="center"><html:submit property="btnRevokeAnother" value="Revoke Another Case"/></td>
        <td align="center"><html:button property="btnPickList" value="Generate Pick List" onclick="generatePickList();"/></td>
    </table>
  </div>
</html:form>
</body>
</html>
