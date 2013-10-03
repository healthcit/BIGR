<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.api.Escaper" %>
<%@ page import="com.ardais.bigr.concept.BigrConcept" %>
<%@ page import="com.ardais.bigr.configuration.SystemConfiguration" %>
<%@ page import="com.ardais.bigr.iltds.assistants.LegalValue" %>
<%@ page import="com.ardais.bigr.iltds.assistants.LegalValueSet" %>
<%@ page import="com.ardais.bigr.iltds.databeans.PolicyData" %>
<%@ page import="com.ardais.bigr.iltds.helpers.FormLogic" %>
<%@ page import="com.gulfstreambio.gboss.GbossFactory" %>
<%@ page import="com.ardais.bigr.security.SecurityInfo" %> 
<%@ page import="com.ardais.bigr.util.ArtsConstants" %>
<%@ page import="com.ardais.bigr.util.Constants" %>
<%@ page import="javax.servlet.http.*" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<bean:define id="myForm" name="maintainPolicyForm" type="com.ardais.bigr.orm.web.form.MaintainPolicyForm"/>
<%
  PolicyData thePolicy = myForm.getPolicy();
	String accountId = ApiFunctions.safeString(thePolicy == null ? "" : thePolicy.getAccountId());
%>
<html>
<head>
<title>Maintain Policies</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<html:rewrite page="/css/bigr.css"/>" type="text/css">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script type="text/javascript">
var myBanner = 'Maintain Policies';
var isWarnOnNavigate = true;

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
  
  document.forms[0]['policy.policyName'].focus();
}

function confirmNavigate() {
  if (needNavagationWarning()) {
    event.returnValue = "You have made changes that you have not submitted yet.  Are you sure that you want to continue?";
  }
}

function needNavagationWarning() {
  if (! isWarnOnNavigate) return false;
  
  var f = document.forms[0];
  if (f['policy.policyName'].value != f['policy.policyName'].defaultValue) return true;

  var defaultLogicalReposSelect = getFirstWithName(f.elements, "policy.defaultLogicalReposId");
  if (getControlValue(defaultLogicalReposSelect) != getControlDefaultValue(defaultLogicalReposSelect)) return true;
  
  return false;
}

function onClickCancel() {
  setActionButtonEnabling(false);
  isWarnOnNavigate = false;
  window.location = '<html:rewrite page="/orm/policy/maintainPolicyStart.do"/>';
}

function onClickDelete(policyId) {
  setActionButtonEnabling(false);
  var f = document.forms[0];
  var cancelAction = false;
  var savedOperation = f['operation'].value;
  var savedPolicyId = f['policy.policyId'].value;
  f['operation'].value = '<%= Constants.OPERATION_DELETE %>';
  f['policy.policyId'].value = policyId;

  if (needNavagationWarning()) {
    if (!confirm("You have made changes that have not been saved.  Are you sure you want to continue?\n\nPress OK to continue, or Cancel to stay on the current page.")) {
      cancelAction = true;
    }
  }
  
  if (!cancelAction && !onFormSubmit()) {
    cancelAction = true;
  }
  
  if (cancelAction) { // restore field values
    f['operation'].value = savedOperation;
    f['policy.policyId'].value = savedPolicyId;
    setActionButtonEnabling(true);
  }
  else {
    isWarnOnNavigate = false;
    f.submit();
  }
}

function onFormSubmit() {
  setActionButtonEnabling(false);
  isWarnOnNavigate = false;

  return true;
}
   
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.forms[0];
  f.btnSubmit.disabled = isDisabled;
  f.btnCancel.disabled = isDisabled;
  setInputEnabled(f, "btnDelete", isEnabled);
  setInputEnabled(document.all, "linkEditPolicy", isEnabled);
}

<bigr:isInRole role1="<%=SecurityInfo.ROLE_SYSTEM_OWNER%>">
<logic:equal name="maintainPolicyForm" property="operation" value="<%= Constants.OPERATION_CREATE %>">
function onAccountChange() {
  var f = document.forms[0];
  var currentAccount = f['policy.accountId'].value;
  f['policy.caseRegistrationFormId'].options.length = 0;
	var index = 0;
  //update the options for the case and sample registration form controls to have
  //the appropriate choices for the selected account
  <%
    Map caseMap = myForm.getCaseRegistrationFormChoiceMap();
    Map sampleMap = myForm.getSampleRegistrationFormChoiceMap();
  	Iterator accountIterator = caseMap.keySet().iterator();
	  while (accountIterator.hasNext()) {
	    String theAccountId = (String)accountIterator.next();
	    LegalValueSet caseChoices = (LegalValueSet)caseMap.get(theAccountId);
	    LegalValueSet sampleChoices= (LegalValueSet)sampleMap.get(theAccountId);
	%>
    if (currentAccount == '<%=theAccountId%>') {
      //set the case registration form choices
      index = 0;
	<%
      Iterator caseChoiceIterator = caseChoices.getIterator();
	    while (caseChoiceIterator.hasNext()) {
	      LegalValue lv = (LegalValue)caseChoiceIterator.next();
	%>
      f['policy.caseRegistrationFormId'].options[index] = new Option('<%=Escaper.jsEscapeInScriptTag(lv.getDisplayValue())%>','<%=lv.getValue()%>');
      index = index + 1;
	<%
	    }
	%>
      //set the sample registration form choices
	<%
	    Iterator sampleTypeIterator = SystemConfiguration.getConceptList(SystemConfiguration.CONCEPT_LIST_SAMPLE_TYPES).iterator();
	    String sampleTypeCode = null;
	    while (sampleTypeIterator.hasNext()) {
	      BigrConcept sampleTypeConcept = (BigrConcept) sampleTypeIterator.next();
	      sampleTypeCode = sampleTypeConcept.getCode();
	      //ignore "Unknown" sample type
	      if (!ArtsConstants.SAMPLE_TYPE_UNKNOWN.equalsIgnoreCase(sampleTypeCode)) {
		      String controlValue = "policy.sampleTypeConfiguration.sampleType(" + sampleTypeCode + ").registrationFormId";
  %>
      f['<%=controlValue%>'].options.length = 0;
      index = 0;
			  <%
			    Iterator sampleChoiceIterator = sampleChoices.getIterator();
			    while (sampleChoiceIterator.hasNext()) {
			      LegalValue lv = (LegalValue)sampleChoiceIterator.next();
			  %>
      f['<%=controlValue%>'].options[index] = new Option('<%=Escaper.jsEscapeInScriptTag(lv.getDisplayValue())%>','<%=lv.getValue()%>');
      index = index + 1;
<%
			    }
	      }
	    }
%>
	}
	<%
    }
%>
}
</logic:equal>
</bigr:isInRole>

function onCollectedChange(spanId, styleIndicator) {
  if (styleIndicator) {
    document.all[spanId].style.display = "inline";    
  }
  else {
    document.all[spanId].style.display = "none";    
  }
}
</script>
</head>
<body class="bigr" onload="initPage();" onbeforeunload="confirmNavigate();">
<html:form method="POST" action="/orm/policy/maintainPolicySave" onsubmit="return(onFormSubmit());">
  <html:hidden property="operation"/>
  <html:hidden property="policy.policyId"/>
  <div align="center">
    <table border="0" cellspacing="0" cellpadding="0" class="background">
      <%-- begin create/edit input table --%>
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
            <logic:equal name="maintainPolicyForm" property="operation" value="<%= Constants.OPERATION_CREATE %>">
              <td colspan="2" align="center"><b>Create a new policy</b></td>
            </logic:equal>
            <logic:equal name="maintainPolicyForm" property="operation" value="<%= Constants.OPERATION_UPDATE %>">
              <td colspan="2" align="center">
                <b>Edit policy:
                <bean:write name="maintainPolicyForm" property="policy.policyName" />
                </b>
              </td>
            </logic:equal>
            </tr>
	        <bigr:isInRole role1="<%=SecurityInfo.ROLE_SYSTEM_OWNER%>">
	        <tr class="white"> 
	          <td class="grey" nowrap>Account <font color="Red">*</font></td>
              <logic:equal name="maintainPolicyForm" property="operation" value="<%= Constants.OPERATION_CREATE %>">
	          <td>
	            <bigr:selectList
	              name="maintainPolicyForm" property="policy.accountId"
		          legalValueSetProperty="accountChoices"
		          firstValue="" firstDisplayValue="Select"
		          onchange="onAccountChange();"/>
	          </td>
              </logic:equal>
              <logic:equal name="maintainPolicyForm" property="operation" value="<%= Constants.OPERATION_UPDATE %>">
              <td>
                <bean:write name="maintainPolicyForm" property="accountName" />
                <html:hidden property="policy.accountId"/>
              </td>
              </logic:equal>
	        </tr>
	        </bigr:isInRole>
	        <bigr:notIsInRole role1="<%=SecurityInfo.ROLE_SYSTEM_OWNER%>">
	        <html:hidden property="policy.accountId"/>
	        </bigr:notIsInRole>
            <tr class="white"> 
              <td class="grey" nowrap>Policy Name <font color="Red">*</font></td>
              <td> 
                <html:text property="policy.policyName" size="55" maxlength="50"/>
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey" nowrap>Allow For Unlinked Cases <font color="Red">*</font></td>
              <td>
              	<html:radio property="policy.allowForUnlinkedCases" value="Y">Yes</html:radio>
              	<html:radio property="policy.allowForUnlinkedCases" value="N">No</html:radio>
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey" nowrap>Default Inventory Group <font color="Red">*</font></td>
              <td>
                <bigr:selectList
                  name="maintainPolicyForm" property="policy.defaultLogicalReposId" 
		          legalValueSetProperty="logicalRepositoryChoices"
		          firstValue="" firstDisplayValue="Select"
		        />
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey" nowrap>Consent Verification Required <font color="Red">*</font></td>
              <td>
              	<html:hidden name="maintainPolicyForm" property="verifyRequiredOldValue"/>
              	<html:radio name="maintainPolicyForm" property="policy.verifyRequired" value="Y">Yes</html:radio>
              	<html:radio name="maintainPolicyForm" property="policy.verifyRequired" value="N">No</html:radio>
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey" nowrap>Case Release Required <font color="Red">*</font></td>
              <td>
              	<html:hidden name="maintainPolicyForm" property="releaseRequiredOldValue"/>
              	<html:radio name="maintainPolicyForm" property="policy.releaseRequired" value="Y">Yes</html:radio>
              	<html:radio name="maintainPolicyForm" property="policy.releaseRequired" value="N">No</html:radio>
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey" nowrap>Case Registration Form <font color="Red">*</font></td>
              <td>
              <%
              	LegalValueSet caseLVS = (LegalValueSet)myForm.getCaseRegistrationFormChoiceMap().get(accountId);
              %>
								<bigr:selectList
								  name="maintainPolicyForm"
							  	property="policy.caseRegistrationFormId"
								  legalValueSet="<%=caseLVS%>"/>
              </td>
            </tr>
	          <html:hidden property="policy.allocationFormatCid"/>
            <tr class="white"> 
              <td class="grey" nowrap>Associated IRBs</td>
              <td>
                <bean:write name="maintainPolicyForm" property="policy.associatedIrbs"/>
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey" nowrap valign="top">Sample Types</td>
              <td>
                <table border="0" cellspacing="1" cellpadding="3" class="foreground">
					        <%
					          int sampleTypeCount = 0;
					          String sampleTypeRowClass = null;
					        %>
                  <logic:iterate id="sampleType" name="maintainPolicyForm" property="policy.sampleTypeConfiguration.sampleTypeList" type="com.ardais.bigr.iltds.databeans.SampleType">
									  <%
										  if (sampleTypeCount%2 == 0) {
										    sampleTypeRowClass = "grey";
										  }
										  else {
										    sampleTypeRowClass = "white";
										  }
									  %>
		                <tr class="<%=sampleTypeRowClass%>">
		                  <td align="left" valign="top" rowspan="2">
											  <b><%= GbossFactory.getInstance().getDescription(sampleType.getCui()) %></b>
		                  </td>
		                  <td align="right">
		                    Collected
		                  </td>
		                  <td align="left">
		                    <%
		                    	String propertyName = "policy.sampleTypeConfiguration.sampleType(" + sampleType.getCui() + ").supported";
                					String spanId = "registrationForm" + sampleType.getCui();
                					String spanStyle="display: none;";
                					if (myForm.getPolicy().getSampleTypeConfiguration().getSampleType(sampleType.getCui()).isSupported()) {
                					  spanStyle="display: inline;";
                					}
                					String onYesClick = "onCollectedChange('" + spanId + "',true);";
                					String onNoClick = "onCollectedChange('" + spanId + "',false);";
 		                    %>
		              	    <html:radio name="maintainPolicyForm" property="<%=propertyName%>" value="true" onclick="<%=onYesClick%>">Yes</html:radio>
		              	    <html:radio name="maintainPolicyForm" property="<%=propertyName%>" value="false" onclick="<%=onNoClick%>">No</html:radio>
		                  </td>
		                </tr>
		                <tr class="<%=sampleTypeRowClass%>">
		                  <td align="right">
		                    Registration Form <span id='<%=spanId %>' style="<%=spanStyle %>"> <font color="Red">*</font></span>
		                  </td>
		                  <td>
					              <%
					              	LegalValueSet sampleLVS = (LegalValueSet)myForm.getSampleRegistrationFormChoiceMap().get(accountId);
	                    	  propertyName = "policy.sampleTypeConfiguration.sampleType(" + sampleType.getCui() + ").registrationFormId";
					              %>
											  <bigr:selectList
											    name="maintainPolicyForm"
										  	  property="<%=propertyName%>"
											    legalValueSet="<%=sampleLVS%>"/>
		                  </td>
		                </tr>
		                <%
					            sampleTypeCount = sampleTypeCount + 1;
		                %>
		              </logic:iterate>
    			      </table>
              </td>
            </tr>
            <tr class="white"> 
              <td colspan="2" align="center"> 
                <input type="submit" name="btnSubmit" value="Submit">
                <input type="button" name="btnCancel" value="Cancel" onclick="onClickCancel();">
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
    </table> <%-- end create/edit input table --%>
    <br/>
    <div> <%-- begin policy list --%>
    <table border="0" cellspacing="0" cellpadding="0" class="background">
      <%-- begin policy list table --%>
      <tr> 
        <td> 
          <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
	        <tr class="white">
      		  <td class="grey" colspan="5">
          		To edit a policy, click on its Policy Name.  To delete a policy,
          		click on its Delete button.  The policy will be deleted as soon
          		as you click on the button.  Only policies that are not associated
          		with any account, IRB, or consent can be deleted.
	          </td>
    	    </tr>
            <tr class="white">
              <td nowrap><b>Policy Name</b></td>
			  <bigr:isInRole role1="<%=SecurityInfo.ROLE_SYSTEM_OWNER%>">
              <td nowrap><b>Account</b></td>
              </bigr:isInRole>
              <td nowrap align="center"><b>ICP</b></td>
              <td nowrap align="center"><b>Delete</b></td>
            </tr>
            <logic:iterate id="policy" name="maintainPolicyForm" property="policies" type="com.ardais.bigr.iltds.databeans.PolicyData">
            <tr class="white">
              <td>
                <a id="linkEditPolicy"
                   href="<html:rewrite page="/orm/policy/maintainPolicyStart.do?operation=Update"/>&policy.policyId=<bean:write name="policy" property="policyId"/>"
                   onclick="return(isLinkEnabled());">
                  <bean:write name="policy" property="policyName"/>
                </a>
              </td>
			  <bigr:isInRole role1="<%=SecurityInfo.ROLE_SYSTEM_OWNER%>">
              <td>
              	<bean:write name="policy" property="accountName"/>
              </td>
              </bigr:isInRole>
              <td align="center">
				<bigr:icpLink linkText="ICP" popup="true">
				  <%=FormLogic.makePrefixedPolicyId(policy.getPolicyId())%>
				</bigr:icpLink>
              </td>
              <td align="center">
				<button type="button" name="btnDelete" value="Delete"
                    style="height: 16px; width: 16px;"
                    onclick="onClickDelete('<bean:write name="policy" property="policyId"/>');">
                  <html:img page="/images/delete.gif" alt="Delete" border="0"/>
                </button>
              </td>
            </tr>
            </logic:iterate>
          </table>
        </td>
      </tr>
    </table> <%-- end policy list table --%>
    </div> <%-- end policy list --%>
  </div> <%-- end centered div --%>
</html:form>
</body>
</html>
