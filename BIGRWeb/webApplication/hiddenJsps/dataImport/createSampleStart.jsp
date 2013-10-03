<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.iltds.helpers.TypeFinder" %>
<%@ page import="com.ardais.bigr.api.ValidateIds" %>
<%@ page import="com.ardais.bigr.security.SecurityInfo"%>
<%@ page import="com.ardais.bigr.util.ArtsConstants" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<bean:define name="sampleForm" id="myForm" type="com.ardais.bigr.dataImport.web.form.SampleForm"/>
<html>
<head>
<title>Create Sample</title>
<script language="JavaScript" src="<html:rewrite page="/js/bigrTextArea.js"/>"></script>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<html:rewrite page="/css/bigr.css"/>" type="text/css">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript" src="<html:rewrite page="/js/calendar.js"/>"></script>
<script type="text/javascript">
var myBanner = 'Create Sample';

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
  showOrHideDonorInfo();
  showOrHideConsentInfo();
  if (document.all('donorFiller').style.display == 'inline') {
  	if (document.all.rememberedData.value == '<%=TypeFinder.DONOR%>') {
    	document.all["sampleData.consentId"].focus();
  	}
  	else if (document.all.rememberedData.value == '<%=TypeFinder.CASE%>') {
    	document.all["sampleData.sampleId"].focus();
  	}
  	else {
    	document.all["sampleData.ardaisId"].focus();
    }
  }
  else {
    document.all["sampleData.donorAlias"].focus();
  }
}

function onFormSubmit() {
  setActionButtonEnabling(false);
  return true;
}

//if the sample type is serum, warn the user if the collection date is not the
//same as the preservation date.
   
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.forms[0];
  setInputEnabled(f,'btnContinue',isEnabled);
}

function showOrHideDonorInfo() {
    if (document.forms[0].elements['newDonor'] == null) {
      return;
    }
    var newDonorValue = getControlValue(document.forms[0].elements['newDonor']);
	var displayValue = 'inline';
	if (newDonorValue == 'Y') {
	  displayValue = 'none';
	}
	//show or hide the donor related controls as appropriate
	document.all('donorFiller').style.display = displayValue;
	//if this is a new donor, then they must be creating a new case as well
	//so set that checkbox to be checked
	if (newDonorValue == 'Y') {
	  document.forms[0].elements['newCase'].checked = true;
	  showOrHideConsentInfo();
	}	
}

function showOrHideConsentInfo() {
    if (document.forms[0].elements['newCase'] == null) {
      return;
    }
    var newCaseValue = getControlValue(document.forms[0].elements['newCase']);
	var displayValue = 'inline';
	var linkedDisplayValue = 'none';
	if (newCaseValue == 'Y') {
	  displayValue = 'none';
	  linkedDisplayValue = 'inline';
	}
	else {
		//we need to clear the linked indicator control or else the
		//consent IRB or protocol controls will remain visible
	    var options = document.forms[0].elements['linkedIndicator'];
	    for (i = 0; i < options.length; i++) {
	      options[i].checked = false;
	    }
	}
	//show or hide the case related controls as appropriate
	document.all('consentFiller').style.display = displayValue;
	document.all('linkedSelect').style.display = linkedDisplayValue;
	showOrHideConsentsAndPolicies();
}

function showOrHideConsentsAndPolicies() {
    var linkedValue = '';
    var options = document.forms[0].elements['linkedIndicator'];
    for (i = 0; i < options.length; i++) {
      if (options[i].checked) {
        linkedValue = options[i].value;
        break;
      }
    }
	var policyDisplayValue = 'none';
	var consentDisplayValue = 'none';
	if (linkedValue == 'Y') {
	  consentDisplayValue = 'inline';
	}
	if (linkedValue == 'N') {
	  policyDisplayValue = 'inline';
	}
	//show or hide the policy and consent controls as appropriate
	document.all('policySelect').style.display = policyDisplayValue;
	document.all('consentLabel').style.display = consentDisplayValue;
	document.all('consentSearch').style.display = consentDisplayValue;
	document.all('consentSelect').style.display = consentDisplayValue;
}

function populateCustomerId() {
  var sampleId = getControlValue(document.forms[0].elements['sampleData.sampleId']);
  var customerId = getControlValue(document.forms[0].elements['sampleData.sampleAlias']);
  if (isEmpty(customerId) && !isEmpty(sampleId)) {
    document.forms[0].elements['sampleData.sampleAlias'].value = sampleId;
  }
}

</script>
</head>
<body class="bigr" onload="initPage();">
<html:form method="POST"
      action="/dataImport/createSamplePrepare"
      onsubmit="return(onFormSubmit());">
  <html:hidden name="sampleForm" property="sampleData.ardaisAcctKey"/>
  <html:hidden name="sampleForm" property="sampleData.importedYN"/>
  <html:hidden name="sampleForm" property="rememberedData"/>
  <div align="center">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="background">
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
              <td width="30%" class="grey" align="right" nowrap>Donor:<font color="Red">&nbsp;*</font></td>
              <td width="70%" nowrap>
                <span id="donorFiller">Donor Id:
                <html:text name="sampleForm" property="sampleData.ardaisId"
                  size="<%= String.valueOf(ValidateIds.LENGTH_DONOR_ID + 5) %>"
                  maxlength="<%= String.valueOf(ValidateIds.LENGTH_DONOR_ID) %>"/>
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Or&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                </span>Donor Alias:
                <html:text name="sampleForm" property="sampleData.donorAlias"
                  size="30"
                  maxlength="30"/>
                <%
                   //wrap the check for the new donor priv inside a check for the
                   //create case priv, since you cannot create a new donor without
                   //creating a new case so it would be invalid to let them check
                   //the new donor checkbox if they are unable to check the new case
                   //checkbox
                %>
                <bigr:hasPrivilege priv="<%=SecurityInfo.PRIV_DATA_IMPORT_CREATE_CASE%>">
                  <bigr:hasPrivilege priv="<%=SecurityInfo.PRIV_DATA_IMPORT_CREATE_DONOR%>">
                    <br>
				    <html:checkbox name="sampleForm" property="newDonor" value="Y" onclick="showOrHideDonorInfo()">
				      This is a new donor
				    </html:checkbox>
				  </bigr:hasPrivilege>
				</bigr:hasPrivilege>
              </td>
            </tr>
            <tr class="white">
              <td width="30%" class="grey" align="right" nowrap>Case:<font color="Red">&nbsp;*</font></td>
              <td width="70%" nowrap>
                <span id="consentFiller">&nbsp;&nbsp;Case Id:
                <html:text name="sampleForm" property="sampleData.consentId"
                  size="<%= String.valueOf(ValidateIds.LENGTH_CASE_ID + 5) %>"
                  maxlength="<%= String.valueOf(ValidateIds.LENGTH_CASE_ID) %>"/>
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Or&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                </span>&nbsp;&nbsp;Case Alias:
                <html:text name="sampleForm" property="sampleData.consentAlias"
                  size="30"
                  maxlength="30"/>
                <bigr:hasPrivilege priv="<%=SecurityInfo.PRIV_DATA_IMPORT_CREATE_CASE%>">
                  <br>
				  <html:checkbox name="sampleForm" property="newCase" value="Y" onclick="showOrHideConsentInfo()">
				    This is a new case
				  </html:checkbox>
				</bigr:hasPrivilege>
              </td>
            </tr>
            <tr style="display: none;" id="linkedSelect" class="white"> 
              <td width="30%" class="grey" align="right">Did the donor sign an informed consent?:<font color="Red">&nbsp;*</font></td>
              <td width="70%"> 
                <html:radio name="sampleForm" property="linkedIndicator" value="Y" onclick="showOrHideConsentsAndPolicies()">Yes</html:radio>
                <html:radio name="sampleForm" property="linkedIndicator" value="N" onclick="showOrHideConsentsAndPolicies()">No</html:radio>
              </td>
            </tr>
            <tr style="display: none;" id="policySelect" class="white"> 				
              <td width="30%" class="grey" align="right" nowrap>Case Policy:<font color="Red">&nbsp;*</font></td>
			  <td width="70%"> 
                <%
                  int numPolicyChoices = myForm.getPolicyChoices().size();
                %>
                <html:select property="policyId">
                <% if (numPolicyChoices > 1) { %>
                  <html:option value="">Select</html:option>
                <% } %>
                  <bean:define name="myForm" property="policyChoices" id="policyCollection" type="java.util.List"/>
                  <html:options collection="policyCollection"
                    property="policyId"
                    labelProperty="policyName"/>
                </html:select>
              </td>
            </tr>
            <tr style="display: none;" id="consentLabel" class="white"> 				
              <td width="30%" class="grey" align="right" rowspan="3" nowrap>Consent Version:<font color="Red">&nbsp;*</font></td>
			  <td width="70%" nowrap>IRB Protocol / Consent Version</td>
			</tr>
			<bigr:searchText
			  style="display: none;" id="consentSearch" 
			  name="test" length="40" 
			  searchButton="Search"
			  refreshButton="Refresh"
			  refresh_yn="Y" searchedField="consentVersionId"/>
            <tr style="display: none;" id="consentSelect" class="white">
			  <td> 
                <%
                  int numConsentChoices = myForm.getConsentChoices().size();
                %>
                <html:select name="sampleForm" property="consentVersionId">
                <% if (numConsentChoices > 1) { %>
                  <html:option value="">Select</html:option>
                <% } %>
                <bean:define name="myForm" property="consentChoices" id="consentCollection" type="java.util.List"/>
                  <html:options name="sampleForm" collection="consentCollection"
                      property="consentVersionId"
                      labelProperty="irbProtocolAndVersionName"/>
                </html:select>
              </td>
            </tr>
            <tr class="white">
              <td colspan="2">
    		    <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
                  <tr class="white" align="center">
                    <td>
                      &nbsp;
                    </td>
                    <td>
                      <html:submit property="btnContinue" value="Continue"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                    <td>
                      &nbsp;
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
            <tr class="white"> 
              <td colspan="2"> 
                <font color="Red">*</font> indicates a required field  
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