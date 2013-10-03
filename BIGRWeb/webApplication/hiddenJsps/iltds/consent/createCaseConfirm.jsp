<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<%@ taglib uri="/tld/datetime" prefix="dt" %>
<bean:define id="consentDateFormat" value="M/yyyy '@' h:mm a" type="java.lang.String"/>
<html>
<head>
<title>Confirm Case Creation</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<html:rewrite page="/css/bigr.css"/>" type="text/css">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script type="text/javascript">
var myBanner = 'Confirm Case Creation';

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
  
  document.forms[0].Submit.focus();
}

function onClickCancel() {
  disableActionButtons();
  var f = document.forms[0];
  f.confirmCanceled.value = "true";
  f.submit();
}

function onFormSubmit() {
  disableActionButtons();
  return true;
}
  
function disableActionButtons() {
  var f = document.forms[0];
  f.Submit.disabled = true;
  f.Cancel.disabled = true;
}
</script>
</head>
<body class="bigr" onload="initPage();">
<div align="center"> 
<html:form action="/iltds/consent/consentConfirm" method="POST"
           onsubmit="return(onFormSubmit());">
    <input type="hidden" name="validateOnly" value="false">
    <input type="hidden" name="confirmCanceled" value="false">
    <html:hidden property="linked" />
    <table border="0" cellspacing="0" cellpadding="0" class="background">
      <tr> 
        <td> 
          <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
	        <logic:present name="com.ardais.BTX_ACTION_MESSAGES">
            <tr class="yellow"> 
              <td colspan="2" align="center"> 
                <font color="#FF0000"><b><bigr:btxActionMessages/></b></font>
 		      </td>
		    </tr>
		    </logic:present>
<logic:equal name="createCaseForm" property="linked" value="true">
            <tr class="yellow"> 
              <td colspan="2" align="center"> 
                <font color="red"><b>
                  You have selected IRB protocol
                  "<bean:write name="createCaseForm" property="irbProtocolAndVersionName" />".
                  You have entered the date and time of consent as
				  <dt:format patternId="consentDateFormat">
                     <bean:write name="createCaseForm" property="consentDate.time" />
                  </dt:format>.
                  If the IRB protocol, date, time and other information for
                  this consent are correct, press Submit to save.  If any of
                  the information is not correct, press Cancel to change the
                  information.
                </b></font>
              </td>
            </tr>
</logic:equal>
<logic:equal name="createCaseForm" property="linked" value="false">
            <tr class="yellow"> 
              <td colspan="2" align="center"> 
                <font color="red"><b>
                  You have selected case policy
                  "<bean:write name="createCaseForm" property="policyName" />".
                  If the case policy and other information for
                  this case are correct, press Submit to save.  If any of
                  the information is not correct, press Cancel to change the
                  information.
                </b></font>
              </td>
            </tr>
</logic:equal>
            <tr class="white"> 
              <td colspan="2" align="center"><b>Confirm</b></td>
            </tr>
            <tr class="white"> 
              <td class="grey">Donor ID:</td>
              <td>
                <bean:write name="createCaseForm" property="ardaisId" /> 
                <html:hidden property="ardaisId" />
                <%-- We need ardaisId_2 so that we can fill that field in
                  -- on the page we'll send the user back to if they click
                  -- the Cancel button. --%>
                <html:hidden property="ardaisId_2" />
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey">Case ID:</td>
              <td> 
                <bean:write name="createCaseForm" property="consentId" /> 
                <html:hidden property="consentId" />
                <%-- We need consentId_2 so that we can fill that field in
                  -- on the page we'll send the user back to if they click
                  -- the Cancel button. --%>
                <html:hidden property="consentId_2" />
              </td>
            </tr>
<logic:equal name="createCaseForm" property="linked" value="true">
            <tr class="white"> 
              <td class="grey">Consent Version:</td>
              <td>
                <bean:write name="createCaseForm" property="irbProtocolAndVersionName" />
                <html:hidden property="consentVersionId" />
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey">Time:</td>
              <td>
                <p><dt:format patternId="consentDateFormat">
                     <bean:write name="createCaseForm" property="consentDate.time" />
                   </dt:format>
                  <html:hidden property="year"/>
                  <html:hidden property="month"/>
                  <html:hidden property="hours"/>
                  <html:hidden property="minutes"/>
                  <html:hidden property="ampm"/>
                </p>
              </td>
            </tr>
            <logic:present name="createCaseForm" property="bloodSampleYN">
              <tr class="white"> 
                <td class="grey">The donor agrees that a blood sample<br>may be obtained at the time of routine<br>blood drawing:</td>
                <td>
                  <bean:write name="createCaseForm" property="bloodSampleYN" />
                </td>
              </tr>
            </logic:present>
            <logic:present name="createCaseForm" property="additionalNeedleStickYN">
              <tr class="white"> 
                <td class="grey">The donor agrees to allow an additional<br>needle stick if necessary to obtain a<br>blood sample:</td>
                <td>
                  <bean:write name="createCaseForm" property="additionalNeedleStickYN" />
                </td>
              </tr>
            </logic:present>
            <logic:present name="createCaseForm" property="futureContactYN">
              <tr class="white"> 
                <td class="grey">The donor is willing to be contacted by<br>Duke researchers in the future:</td>
                <td>
                  <bean:write name="createCaseForm" property="futureContactYN" />
                </td>
              </tr>
            </logic:present>
            <html:hidden property="bloodSampleYN"/>
            <html:hidden property="additionalNeedleStickYN"/>
            <html:hidden property="futureContactYN"/>
</logic:equal>
<logic:equal name="createCaseForm" property="linked" value="false">
            <tr class="white"> 
              <td class="grey">Case Policy:</td>
              <td>
                <bean:write name="createCaseForm" property="policyName" />
                <html:hidden property="policyId" />
              </td>
            </tr>
</logic:equal>
            <tr class="white"> 
              <td class="grey">Comments:</td>
              <td>
                <bigr:beanWrite filter="true" whitespace="true" name="createCaseForm" property="comments"/>
				<html:hidden property="comments"/>
              </td>
            </tr>
            <tr class="white"> 
              <td colspan="2"> 
                <div align="center"> 
                  <input type="submit" name="Submit" value="Submit">
  			      <input type="button" name="Cancel" value="Cancel"
				         onclick="onClickCancel();">
                </div>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
</html:form>
</div>
</body>
</html>
