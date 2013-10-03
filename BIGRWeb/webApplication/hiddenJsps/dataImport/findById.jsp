<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.iltds.helpers.JspHelper" %>
<%@ page import="com.ardais.bigr.util.Constants" %>
<%@ page import="com.ardais.bigr.util.WebUtils" %>
<%@ page import="com.ardais.bigr.security.SecurityInfo" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, getServletContext(), "P");
%>
<bean:define name="findByIdForm" id="myForm" type="com.ardais.bigr.dataImport.web.form.FindByIdForm"/>

<html>
<head>
<title>Find By Id</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript">
  
function initPage() {
  if (parent.topFrame) {
    parent.topFrame.document.all.banner.innerHTML = 'Find By Id';
  }
  document.all.ardaisId.focus();
}
  
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.forms[0];
  f.btnSubmit.disabled = isDisabled;
  setInputEnabled(f,'modify',isEnabled);
}

function onFormSubmit() {
  setActionButtonEnabling(false);
  return true;
}

function clearForm(form) {
  //blank out the ardais and customer id fields, since we're passing along the 
  //donor id and don't want these fields to interfere
  form.customerId.value = '';
  form.ardaisId.value = '';
}

function modifyDonor(donorId) {
  setActionButtonEnabling(false);
  var f = document.forms[0];
  clearForm(f);
  f.action = '<html:rewrite page="/ddc/Dispatch?op=DonorProfileSummaryPrepare&importedYN=Y&storeInfoInSession=true"/>' 
  			+ "&ctx=" + "<%=JspHelper.SEARCH_CONTEXT_DONOR_PROFILE%>"
  			+ "&ardaisId=" + donorId;
  f.submit();
}

function modifyCase(caseId) {
  setActionButtonEnabling(false);
  var f = document.forms[0];
  clearForm(f);
  f.action = '<html:rewrite page="/dataImport/modifyCaseStart.do"/>' 
  			+ "?consentId=" + caseId;
  f.submit();
}

function modifySample(sampleId) {
  setActionButtonEnabling(false);
  var f = document.forms[0];
  clearForm(f);
  f.action = '<html:rewrite page="/dataImport/modifySampleStart.do"/>' 
  			+ "?sampleData.sampleId=" + sampleId;
  f.submit();
}

function toggleFindByControl(name) {
  if (document.all[name + "Display"].checked) {
	document.all[name].value="Y";
	//alert(name + ' = ' + document.all[name].value);
  }
  else {
	document.all[name].value="N";
	//alert(name + ' = ' + document.all[name].value);
  }
}

</script>
</head>

<body class="bigr" onLoad="initPage();">
<html:form action="/dataImport/findById.do"  onsubmit="return(onFormSubmit());">
  
<%
  // DIV for errors
%>
  <div id="errorDiv" align="center">
    <table width="100%" border="1" cellspacing="1" cellpadding="1" class="foreground-small">
      <logic:present name="org.apache.struts.action.ERROR">
      <tr class="yellow"> 
	    <td> 
	      <font color="#FF0000"><b><html:errors/></b></font>
	    </td>
	  </tr>
	  </logic:present>
      <logic:present name="com.ardais.BTX_ACTION_MESSAGES">
	  <tr class="white"> 
	    <td> 
	      <div align="left">
	        <font color="#000000"><b><bigr:btxActionMessages/></b></font>
	      </div>
	    </td>
	  </tr>
	  </logic:present>
	</table>
  </div>
  <p>
	<%
		boolean showDonorButton = false;
		boolean showCaseButton = false;
		boolean showSampleButton = false;
		SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);
		if (securityInfo != null) {
		  if (securityInfo.isHasPrivilege(SecurityInfo.PRIV_DATA_IMPORT_MODIFY_DONOR)) {
		    showDonorButton = true;
		  }
		  if (securityInfo.isHasPrivilege(SecurityInfo.PRIV_DATA_IMPORT_MODIFY_CASE)) {
		    showCaseButton = true;
		  }
		  if (securityInfo.isHasPrivilege(SecurityInfo.PRIV_DATA_IMPORT_MODIFY_SAMPLE)) {
		    showSampleButton = true;
		  }
		}
		String donorColWidth;
		if (showDonorButton) {
		  donorColWidth = "20%";
		}
		else {
		  donorColWidth = "25%";
		}
		String caseColWidth;
		if (showCaseButton) {
		  caseColWidth = "17%";
		}
		else {
		  caseColWidth = "20%";
		}
		String sampleColWidth;
		if (showSampleButton) {
		  sampleColWidth = "17%";
		}
		else {
		  sampleColWidth = "14%";
		}
	%>

  <p><div align="center">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table border="0" class="foreground" cellpadding="3" cellspacing="1">
            <tr class="yellow"> 
              <td colspan="3" align="center"><b>Please Enter the ID or Alias of the Item(s) to Find</b></td>
            </tr>
            <tr class="white"> 
              <td align="center" valign="top">
	            <table border="0" class="foreground" cellpadding="3" cellspacing="1">
	              <tr class="white"> 
	                <td class="grey" align="center"><b>BIGR ID:</b></td>
	              </tr>
	              <tr class="white"> 
	                <td align="center"><html:text name="findByIdForm" property="ardaisId" maxlength="12" size="30" /></td>
	              </tr>
	            </table>
              </td>
              <td valign="middle">&nbsp;&nbsp;&nbsp;&nbsp;<b>OR</b>&nbsp;&nbsp;&nbsp;&nbsp;</td>
              <td align="center" valign="top">
	            <table border="0" class="foreground" cellpadding="3" cellspacing="1">
	              <tr class="white"> 
	                <td class="grey" align="center"><b>Alias:</b></td>
	              </tr>
	              <tr class="white"> 
	                <td align="center"><html:text name="findByIdForm" property="customerId" maxlength="30" size="30" /></td>
	              </tr>
	              <tr class="white">
	                <td align="center">
	                  <b>Item type(s) to find:</b>
	                  <%
	              	    String dChecked = null;
	              	    String cChecked = null;
	              	    String sChecked = null;
	              	    if ("Y".equalsIgnoreCase(myForm.getFindDonors())) {
	              		  dChecked = "checked";
	              	    }
	              	    if ("Y".equalsIgnoreCase(myForm.getFindCases())) {
	              		  cChecked = "checked";
	              	    }
	              	    if ("Y".equalsIgnoreCase(myForm.getFindSamples())) {
	              		  sChecked = "checked";
	              	    }
	                  %>
					  <input type="checkbox" name="findDonorsDisplay" <%=dChecked%> onclick="toggleFindByControl('findDonors');"/>Donors&nbsp;&nbsp;&nbsp;
	                  <html:hidden name="findByIdForm" property="findDonors"/>
					  <input type="checkbox" name="findCasesDisplay" <%=cChecked%> onclick="toggleFindByControl('findCases');"/>Cases&nbsp;&nbsp;&nbsp;
	                  <html:hidden name="findByIdForm" property="findCases"/>
					  <input type="checkbox" name="findSamplesDisplay" <%=sChecked%> onclick="toggleFindByControl('findSamples');"/>Samples&nbsp;&nbsp;&nbsp;
	                  <html:hidden name="findByIdForm" property="findSamples"/>
				    </td>
	              </tr>
	            </table>
              </td>
            </tr>
            <tr class="white"> 
              <td colspan="3" align="center"><html:submit property="btnSubmit" value="Continue" /></td>
            </tr>
          </table>
        </td>
      </tr> 
    </table>  
  </div></p>
  <p><div align="center">
  <logic:equal name="findByIdForm" property="donorsSearched" value="true">
  <logic:present name="findByIdForm" property="donors">
  	<br>
  	<br>
	<table width="100%" cellpadding="0" cellspacing="0" border="0" class="background">
	  <tr>
	    <td>
		  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
			<tr class="yellow">
			  <td>
				<b>
				  Donors
				</b>
			  </td>
			</tr>
		  </table>
		</td>
	  </tr>
	</table>
	<table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
	  <tr class="grey">
		<td nowrap width="<%=donorColWidth%>">
		  <b>Donor Id</b>
		</td>
		<td nowrap width="<%=donorColWidth%>">
		  <b>Donor Alias</b>
		</td>
		<td nowrap width="<%=donorColWidth%>">
		  <b>Gender</b>
		</td>
		<td nowrap width="<%=donorColWidth%>">
		  <b>Year of Birth</b>
		</td>
		<%
			if (showDonorButton) {
		%>
		  <td nowrap width="<%=donorColWidth%>">
		  </td>
		<%
			}
		%>
	  </tr>
	  <logic:empty name="findByIdForm" property="donors">
	  	<tr class="white">
	  	  <td colspan="5">No donors matched the specified id</td>
	  	</tr>
	  </logic:empty>
	  <logic:notEmpty name="findByIdForm" property="donors">
	    <% 
	      String rowClass = "white"; 
	    %>
	    <logic:iterate name="findByIdForm" property="donors" id="donorData" type="com.ardais.bigr.pdc.javabeans.DonorData">
	  	  <tr class="<%=rowClass%>">
	  	  <%
	  	    if (rowClass.equals("white")) {
	  	  	  rowClass = "grey";
	  	    }
	  	    else {
	  	   	  rowClass = "white";
	  	    }
	  	  %>
		    <td nowrap width="<%=donorColWidth%>">
			  <bigr:icpLink popup="true">
	            <bean:write name="donorData" property="ardaisId"/>
			  </bigr:icpLink>
	        </td>
		    <td nowrap width="<%=donorColWidth%>">
	          <bean:write name="donorData" property="customerId"/>
	        </td>
		    <td nowrap width="<%=donorColWidth%>">
		      <%=Constants.getGenderText(donorData.getGender())%>
	        </td>
		    <td nowrap width="<%=donorColWidth%>">
	          <bean:write name="donorData" property="yyyyDob"/>
	        </td>
			<%
				if (showDonorButton) {
			%>
			  <td nowrap width="<%=donorColWidth%>">
		        <input class="smallButton" type="button" name="modify" value="Modify" onClick="modifyDonor('<%=donorData.getArdaisId()%>');"> 
			  </td>
			<%
				}
			%>
	      </tr>
	    </logic:iterate>
	  </logic:notEmpty>
	</table>
  </logic:present>
  </logic:equal>
  </div></p>
  <p><div align="center">
  <logic:equal name="findByIdForm" property="casesSearched" value="true">
  <logic:present name="findByIdForm" property="cases">
  	<br>
  	<br>
	<table width="100%" cellpadding="0" cellspacing="0" border="0" class="background">
	  <tr>
	    <td>
		  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
			<tr class="yellow">
			  <td>
				<b>
				  Cases
				</b>
			  </td>
			</tr>
		  </table>
		</td>
	  </tr>
	</table>
	<table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
	  <tr class="grey">
		<td nowrap width="<%=caseColWidth%>">
		  <b>Case Id</b>
		</td>
		<td nowrap width="<%=caseColWidth%>">
		  <b>Case Alias</b>
		</td>
		<td nowrap width="<%=caseColWidth%>">
		  <b>Donor Id</b>
		</td>
		<td nowrap width="<%=caseColWidth%>">
		  <b>Donor Alias</b>
		</td>
		<td nowrap width="<%=caseColWidth%>">
		  <b>Linked</b>
		</td>
		<%
			if (showCaseButton) {
		%>
		  <td nowrap width="<%=caseColWidth%>">
		  </td>
		<%
			}
		%>
	  </tr>
	  <logic:empty name="findByIdForm" property="cases">
	  	<tr class="white">
	  	  <td colspan="5">No cases matched the specified id</td>
	  	</tr>
	  </logic:empty>
	  <logic:notEmpty name="findByIdForm" property="cases">
	    <% 
	      String rowClass = "white"; 
	    %>
	    <logic:iterate name="findByIdForm" property="cases" id="caseData" type="com.ardais.bigr.javabeans.ConsentData">
	  	  <tr class="<%=rowClass%>">
	  	  <%
	  	    if (rowClass.equals("white")) {
	  	  	  rowClass = "grey";
	  	    }
	  	    else {
	  	   	  rowClass = "white";
	  	    }
	  	  %>
		    <td nowrap width="<%=caseColWidth%>">
			  <bigr:icpLink popup="true">
	            <bean:write name="caseData" property="consentId"/>
			  </bigr:icpLink>
	        </td>
		    <td nowrap width="<%=caseColWidth%>">
	          <bean:write name="caseData" property="customerId"/>
	        </td>
		    <td nowrap width="<%=caseColWidth%>">
			  <bigr:icpLink popup="true">
	            <bean:write name="caseData" property="donorId"/>
			  </bigr:icpLink>
	        </td>
		    <td nowrap width="<%=caseColWidth%>">
	          <bean:write name="caseData" property="donorCustomerId"/>
	        </td>
		    <td nowrap width="<%=caseColWidth%>">
	          <bean:write name="caseData" property="linked"/>
	        </td>
			<%
				if (showCaseButton) {
			%>
			  <td nowrap width="<%=caseColWidth%>">
		        <input class="smallButton" type="button" name="modify" value="Modify" onClick="modifyCase('<%=caseData.getConsentId()%>');"> 
			  </td>
			<%
				}
			%>
	      </tr>
	    </logic:iterate>
	  </logic:notEmpty>
	</table>
  </logic:present>
  </logic:equal>
  </div></p>
  <p><div align="center">
  <logic:equal name="findByIdForm" property="samplesSearched" value="true">
  <logic:present name="findByIdForm" property="samples">
  	<br>
  	<br>
	<table width="100%" cellpadding="0" cellspacing="0" border="0" class="background">
	  <tr>
	    <td>
		  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
			<tr class="yellow">
			  <td>
				<b>
				  Samples
				</b>
			  </td>
			</tr>
		  </table>
		</td>
	  </tr>
	</table>
	<table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
	  <tr class="grey">
		<td nowrap width="<%=sampleColWidth%>">
		  <b>Sample Id</b>
		</td>
		<td nowrap width="<%=sampleColWidth%>">
		  <b>Sample Alias</b>
		</td>
		<td nowrap width="<%=sampleColWidth%>">
		  <b>Case Id</b>
		</td>
		<td nowrap width="<%=sampleColWidth%>">
		  <b>Case Alias</b>
		</td>
		<td nowrap width="<%=sampleColWidth%>">
		  <b>Donor Id</b>
		</td>
		<td nowrap width="<%=sampleColWidth%>">
		  <b>Donor Alias</b>
		</td>
		<%
			if (showSampleButton) {
		%>
		  <td nowrap width="<%=sampleColWidth%>">
		  </td>
		<%
			}
		%>
	  </tr>
	  <logic:empty name="findByIdForm" property="samples">
	  	<tr class="white">
	  	  <td colspan="5">No samples matched the specified id</td>
	  	</tr>
	  </logic:empty>
	  <logic:notEmpty name="findByIdForm" property="samples">
	    <% 
	      String rowClass = "white"; 
	    %>
	    <logic:iterate name="findByIdForm" property="samples" id="sampleData" type="com.ardais.bigr.javabeans.SampleData">
	  	  <tr class="<%=rowClass%>">
	  	  <%
	  	    if (rowClass.equals("white")) {
	  	  	  rowClass = "grey";
	  	    }
	  	    else {
	  	   	  rowClass = "white";
	  	    }
	  	  %>
		    <td nowrap width="<%=sampleColWidth%>">
			  <bigr:icpLink popup="true">
	            <bean:write name="sampleData" property="sampleId"/>
			  </bigr:icpLink>
	        </td>
		    <td nowrap width="<%=sampleColWidth%>">
	          <bean:write name="sampleData" property="sampleAlias"/>
	        </td>
		    <td nowrap width="<%=sampleColWidth%>">
			  <bigr:icpLink popup="true">
	            <bean:write name="sampleData" property="consentId"/>
			  </bigr:icpLink>
	        </td>
		    <td nowrap width="<%=sampleColWidth%>">
	          <bean:write name="sampleData" property="consentAlias"/>
	        </td>
		    <td nowrap width="<%=sampleColWidth%>">
			  <bigr:icpLink popup="true">
	            <bean:write name="sampleData" property="ardaisId"/>
			  </bigr:icpLink>
	        </td>
		    <td nowrap width="<%=sampleColWidth%>">
	          <bean:write name="sampleData" property="donorAlias"/>
	        </td>
			<%
				//only show the modify button if the user has the right priv
				//AND the sample has a consent associated with it (this is to
				//prevent someone trying to modify a sample that was created
				//via a box scan and has progressed no further)
				if (showSampleButton && !ApiFunctions.isEmpty(sampleData.getConsentId())) {
			%>
			  <td nowrap width="<%=sampleColWidth%>">
		        <input class="smallButton" type="button" name="modify" value="Modify" onClick="modifySample('<%=sampleData.getSampleId()%>');"> 
			  </td>
			<%
				}
			%>
	      </tr>
	    </logic:iterate>
	  </logic:notEmpty>
	</table>
  </logic:present>
  </logic:equal>
  </div></p>
</html:form>
</body>
</html>
