<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.ardais.bigr.iltds.databeans.IrbVersionData" %>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.api.ValidateIds" %>
<%@ page import="com.ardais.bigr.iltds.databeans.PolicyData" %>
<%@ page import="com.ardais.bigr.iltds.helpers.FormLogic" %>
<%@ page import="com.ardais.bigr.iltds.helpers.IltdsUtils" %>
<%@ page import="com.ardais.bigr.util.Constants" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<html>
<head>
<logic:equal name="createCaseForm" property="linked" value="true">
  <title>Initial Linked Consent Entry</title>
</logic:equal>
<logic:equal name="createCaseForm" property="linked" value="false">
  <title>Initial Unlinked Entry</title>
</logic:equal>
<script language="JavaScript" src="<html:rewrite page="/js/bigrTextArea.js"/>"></script>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<html:rewrite page="/css/bigr.css"/>" type="text/css">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script type="text/javascript">
<logic:equal name="createCaseForm" property="linked" value="true">
  var myBanner = 'Initial Linked Consent Entry';
</logic:equal>
<logic:equal name="createCaseForm" property="linked" value="false">
  var myBanner = 'Initial Unlinked Entry';
</logic:equal>

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
  showOrHideAdditionalQuestions();
  document.forms[0].ardaisId.focus();
}

function onFormSubmit() {
  setActionButtonEnabling(false);
  return true;
}
   
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.forms[0];
  f.Submit.disabled = isDisabled;
}

function showOrHideAdditionalQuestions() {
	var dropdown = document.forms[0].elements['consentVersionId'];
	var displayValue = 'none';
	if (dropdown != null) {
	  var value = dropdown.value;
	  <%
	    Map policyMap = (HashMap) request.getAttribute("policyMap");
	    List consentVers = (List) request.getAttribute("consentVersionChoices");
	    if (!ApiFunctions.isEmpty(consentVers)) {
	      Iterator iterator = consentVers.iterator();
	      while (iterator.hasNext()) {
	        IrbVersionData consentVer = (IrbVersionData)iterator.next();
	        PolicyData policy = (PolicyData) policyMap.get(consentVer.getIrbPolicyId());
	        String additionalQuestionsGroup = policy.getAdditionalQuestionsGrp();
	        if (!ApiFunctions.isEmpty(additionalQuestionsGroup)) {
	          if (additionalQuestionsGroup.equalsIgnoreCase(FormLogic.ADDITIONAL_QUESTIONS_GRP_DBS)) {
	  %>
	  if (value == '<%=consentVer.getConsentVersionId()%>') {
	  	displayValue = 'inline';
	  }
	  <%
	  		  }
	  		}
	      }
	    }
	  %>
	  //show or hide the 3 questions as appropriate
	  document.all('bloodSampleQuestion').style.display = displayValue;
	  document.all('needleStickQuestion').style.display = displayValue;
	  document.all('futureContactQuestion').style.display = displayValue;
	  //if we're hiding the questions, blank out their values
	  if (displayValue == 'none') {
	    var i;
	    var options = document.forms[0].elements['bloodSampleYN'];
	    if (null == options.length) { // Only one radio button with this name
	      options.checked = false;
	    }
	    else {
	      for (i = 0; i < options.length; i++) {
	        options[i].checked = false;
	      }
	    }
	    options = document.forms[0].elements['additionalNeedleStickYN'];
	    if (null == options.length) { // Only one radio button with this name
	      options.checked = false;
	    }
	    else {
	      for (i = 0; i < options.length; i++) {
	        options[i].checked = false;
	      }
	    }
	    options = document.forms[0].elements['futureContactYN'];
	    if (null == options.length) { // Only one radio button with this name
	      options.checked = false;
	    }
	    else {
	      for (i = 0; i < options.length; i++) {
	        options[i].checked = false;
	      }
	    }
	  }
	}
}
</script>
</head>
<body class="bigr" onload="initPage();">
<html:form method="POST"
      action="/iltds/consent/consentConfirm"
      onsubmit="return(onFormSubmit());">
  <html:hidden property="linked"/>
  <input type="hidden" name="validateOnly" value="true">
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
              <td class="grey" nowrap>Donor ID: </td>
              <td> 
                <html:text property="ardaisId"
                  size="<%= String.valueOf(ValidateIds.LENGTH_DONOR_ID + 5) %>"
                  maxlength="<%= String.valueOf(ValidateIds.LENGTH_DONOR_ID) %>"/>
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey" nowrap>Donor ID (confirm):</td>
              <td> 
                <html:text property="ardaisId_2"
                  size="<%= String.valueOf(ValidateIds.LENGTH_DONOR_ID + 5) %>"
                  maxlength="<%= String.valueOf(ValidateIds.LENGTH_DONOR_ID) %>"/>
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey" nowrap>Case ID:</td>
              <td> 
                <html:text property="consentId"
                  size="<%= String.valueOf(ValidateIds.LENGTH_CASE_ID + 5) %>"
                  maxlength="<%= String.valueOf(ValidateIds.LENGTH_CASE_ID) %>"/>
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey" nowrap>Case ID (confirm):</td>
              <td> 
                <html:text property="consentId_2"
                  size="<%= String.valueOf(ValidateIds.LENGTH_CASE_ID + 5) %>"
                  maxlength="<%= String.valueOf(ValidateIds.LENGTH_CASE_ID) %>"/>
              </td>
            </tr>
<logic:equal name="createCaseForm" property="linked" value="false">
            <tr class="white"> 				
              <td class="grey" nowrap>Case Policy:</td>
			  <td> 
                <%
                  int numPolicyChoices =
                    ((List) request.getAttribute("policyChoices")).size();
                %>
                <html:select property="policyId">
                  <% if (numPolicyChoices > 1) { %>
                  <html:option value="">Select</html:option>
                  <% } %>
                  <html:options collection="policyChoices"
                    property="policyId"
                    labelProperty="policyName"/>
                </html:select>
              </td>
            </tr>
</logic:equal>
<logic:equal name="createCaseForm" property="linked" value="true">
            <tr class="white"> 				
              <td class="grey" rowspan="3" nowrap>Consent Version:</td>
			  <td nowrap>IRB Protocol / Consent Version</td>
			  </tr>
			  <bigr:searchText 
			  	name="test" length="40" 
			  	searchButton="Search"
			  	refreshButton="Refresh"
			  	refresh_yn="Y" searchedField="consentVersionId"/>
              <tr class="white">
			  <td> 
                <%
                  int numConsentChoices =
                    ((List) request.getAttribute("consentVersionChoices")).size();
                %>
                <html:select property="consentVersionId" onchange="showOrHideAdditionalQuestions()">
                  <% if (numConsentChoices > 1) { %>
                  <html:option value="">Select</html:option>
                  <% } %>
                  <html:options collection="consentVersionChoices"
                    property="consentVersionId"
                    labelProperty="irbProtocolAndVersionName"/>
                </html:select>
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey" nowrap>Date/Time of Consent:</td>
              <td> 
                  <% java.util.Calendar currentDate = java.util.Calendar.getInstance(); %>
                  <html:select property="month">
                    <% for(int i = 1; i <= 12; i++) { %>
                         <html:option value="<%= String.valueOf(i) %>"><%= i %></html:option>
                    <% } %>
                  </html:select>
                  / 
                  <html:select property="year">
                    <% int currentYear = currentDate.get(java.util.Calendar.YEAR);
                       for (int i = currentYear; i >= currentYear - Constants.CONSENT_DATE_LOOKBACK_YEARS; i--) { %>
                         <html:option value="<%= String.valueOf(i) %>"><%= i %></html:option>
                    <% } %>
                  </html:select>
                  @ 
                  <html:select property="hours">
                    <% for (int i = 1 ; i <= 12; i++) { %>
                      <html:option value="<%= String.valueOf(i) %>"><%= i %></html:option>
                    <% } %>
                  </html:select>
                  : 
                  <html:select property="minutes">
                    <% for(int i = 0; i <= 59; i++) { %>
                      <html:option value="<%= String.valueOf(i) %>">
                        <%= ((i >= 10) ? String.valueOf(i) : ("0" + i)) %>
                    </html:option>
                    <% } %>
                  </html:select>
                  <html:radio property="ampm" value="am">AM</html:radio>
                  <html:radio property="ampm" value="pm">PM</html:radio>
              </td>
            </tr>
            <tr style="display: none;" id="bloodSampleQuestion" class="white"> 
              <td class="grey" nowrap> 
                The donor agrees that a blood sample<br>may be obtained at the time of routine<br>blood drawing:
              </td>
              <td> 
                <html:radio property="bloodSampleYN" value="Y">Yes</html:radio>
                <html:radio property="bloodSampleYN" value="N">No</html:radio>
              </td>
            </tr>
            <tr style="display: none;" id="needleStickQuestion" class="white"> 
              <td class="grey" nowrap> 
                The donor agrees to allow an additional<br>needle stick if necessary to obtain a<br>blood sample:
              </td>
              <td> 
                <html:radio property="additionalNeedleStickYN" value="Y">Yes</html:radio>
                <html:radio property="additionalNeedleStickYN" value="N">No</html:radio>
              </td>
            </tr>
            <tr style="display: none;" id="futureContactQuestion" class="white"> 
              <td class="grey" nowrap> 
                The donor is willing to be contacted by<br>Duke researchers in the future:
              </td>
              <td> 
                <html:radio property="futureContactYN" value="Y">Yes</html:radio>
                <html:radio property="futureContactYN" value="N">No</html:radio>
              </td>
            </tr>
</logic:equal>
            <tr class="white"> 
              <td class="grey" nowrap> 
                Comments:
              </td>
              <td> 
	      		<html:textarea property="comments" rows="10" cols="80" onkeyup="BigrTextAreaComments.enforceMaxLength()"/>
				<script language="JavaScript">
					var BigrTextAreaComments = new BigrTextArea('comments');
					BigrTextAreaComments.maxLength = 4000;
				</script>
              </td>
            </tr>
            <tr class="white"> 
              <td colspan="2" align="center"> 
                <input type="submit" name="Submit" value="Confirm">
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
