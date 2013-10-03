<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.security.SecurityInfo"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>

<%@ taglib uri="/tld/struts-bean"   prefix="bean" %>
<%@ taglib uri="/tld/struts-html"   prefix="html" %>
<%@ taglib uri="/tld/struts-logic"  prefix="logic"%>
<%@ taglib uri="/tld/bigr"          prefix="bigr" %>

<bean:define id="donorBean" name="donor" type="com.ardais.bigr.pdc.helpers.DonorHelper"/>
<bean:define id="consentBean" name="consent" type="com.ardais.bigr.pdc.helpers.ConsentHelper"/>

<html>
<head>
<title><bean:write name="consentBean" property="consentId"/>: Donor Profile</title>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
</head>
<body class="bigr">
<div align="center"> 
  <p><a name="top"></a> 
    
    <b><font size="+1">Donor Profile<br>
    Information Regarding Donor</font></b></p>
  <p> 
    <input type="button" name="Close2" value="Close" onClick="window.close();">
  </p>
  <table class="background" cellpadding="0" cellspacing="0" border="0" width="80%">
    <tr> 
      <td> 
        <table class="foreground" cellpadding="3" cellspacing="1" border="0" width="100%">
          <tr class="green"> 
            <td colspan="4"> 
              <div align="center"><b><bean:write name="donorBean" property="ardaisId"/> 
                <logic:notEmpty name="donorBean" property="customerId">
                  (<bean:write name="donorBean" property="customerId" />)
                </logic:notEmpty>
                > <bean:write name="consentBean" property="consentId"/>
                <logic:notEmpty name="consentBean" property="customerId">
                  (<bean:write name="consentBean" property="customerId" />)
                </logic:notEmpty>
                <br>
                <bean:write name="donorBean" property="genderDisplay"/> - <logic:present name="donorBean" property="ageAtCollection"> 
                <bean:write name="donorBean" property="ageAtCollection"/> - </logic:present> 
                <bean:write name="donorBean" property="raceDisplay"/> </b></div>
            </td>
            </tr>
           
          <tr class="white"> 
            <td class="grey"> 
              <div align="right"><b>Year of Birth</b></div>
            </td>
            <td><bean:write name="donorBean" property="yyyyDob"/></td>
            <td  align="right" class="grey"><b>Gender</b></td>
            <td><bean:write name="donorBean" property="genderDisplay"/></td>
          </tr>
          <tr class="white"> 
            <td class="grey"> 
              <div align="right"><b>Ethnicity</b></div>
            </td>
            <td><bean:write name="donorBean" property="ethnicCategoryDisplay"/></td>
            <td class="grey"> 
              <div align="right"><b>Race</b></div>
            </td>
            <td><bean:write name="donorBean" property="raceDisplay"/></td>
          </tr>
          
          <tr class="white"> 
            <td class="grey"> 
              <div align="right"><b>Country of Birth</b></div>
            </td>
            <td colspan="3"><bean:write name="donorBean" property="countryOfBirthDisplay"/></td>
          </tr>
          <tr class="white">
            <td class="grey">
              <div align="right"><b>Donor Profile Note</b></div>
            </td>
            <td colspan="3"><bean:write name="donorBean" property="donorProfileNotes"/></td>
          </tr>
          
        </table>
      </td>
    </tr>
  </table>
  
  
  <p> 
  <table class="background" cellpadding="0" cellspacing="0" border="0" width="80%">
    <tr> 
      <td> 
        <table class="foreground" border="0" cellpadding="3" cellspacing="1" width="100%">
          <tr class="green"> 
            <td colspan="2"> 
              <div align="center"><b><a name="PathologyReports"></a>Case Pathology 
                and Diagnostic Tests</b></div>
            </td>
          </tr>
          <tr class="yellow"> 
            <td><b>Case ID</b></td>
            <td><b>Case Diagnosis</b></td>
          </tr><!-- LOOP -->
          <logic:iterate id="consent" name="donorBean" property="consents" type="com.ardais.bigr.pdc.helpers.ConsentHelper">
         <tr class="white"> 
            <% String caseDx = "/library/Dispatch?op=PathInfoPathReport&path_id=" + consent.getPathReportId() + "&donor_id=" + donorBean.getArdaisId() + "&index=0&case_id=" + consent.getConsentId(); %>
            <td width="25%">
              <html:link page="<%=caseDx%>">
                <b>
                  <bean:write name="consent" property="consentId"/>
                  <logic:notEmpty name="consent" property="customerId">
                    (<bean:write name="consent" property="customerId" />)
                  </logic:notEmpty>
                </b>
              </html:link>
            </td>
            <td><bean:write name="consent" property="diagnosis"/></td>
          </tr>
          <bigr:isInRole role1='<%=com.ardais.bigr.security.SecurityInfo.ROLE_SYSTEM_OWNER%>'> 
           	<tr class="white">
            	<td class="grey">
              		<div align="right"><b>Case Profile Note</b></div>
            	</td>
            	<td>
              	<bigr:beanWrite name="consent" property="diCaseProfileNotes"
              	  filter="true" whitespace="true" />
            	</td>
          	</tr>
          </bigr:isInRole> 
          </logic:iterate>
          <!-- END LOOP -->
        </table>
      </td>
    </tr>
  </table>
  
  <p> 
<bigr:isInRole role1='<%=com.ardais.bigr.security.SecurityInfo.ROLE_SYSTEM_OWNER%>'> 
  <table class="background" cellpadding="0" cellspacing="0" border="0" width="80%">
    <tr> 
      <td> 
        <table class="foreground" cellpadding="3" cellspacing="1" border="0" width="100%">
          <tr class="green"> 
            <td colspan="3"> 
              <div align="center"><b> <a name="DonorDiagnosis"></a>Additional 
                Diagnoses</b></div>
            </td>
          </tr>
          <tr class="yellow"> 
            <td> 
              <div align="center"><b>Diagnosis Name</b></div>
            </td>
            <td> 
              <div align="center"><b>Diagnosis Type</b></div>
            </td>
            <td> 
              <div align="center"><b>Diagnosis Date</b></div>
            </td>
          </tr>
          <logic:present name="donorBean" property="diagnoses">
          <logic:iterate id="diagnosis" name="donorBean" property="diagnoses">
						<tr class="white"> 
							<td>
								<bean:write name="diagnosis" property="diagnosisDisplay"/>
								<logic:equal name="diagnosis" property="diagnosisDisplay" value = "Other diagnosis">
									( <bean:write name="diagnosis" property="otherDx"/> )
								</logic:equal>
							</td>
							<td><bean:write name="diagnosis" property="diagnosisTypeDisplay"/></td>
							<td><bean:write name="diagnosis" property="dateOfYYYY"/></td>
						</tr>
          </logic:iterate>
          </logic:present>
          <logic:notPresent name="donorBean" property="diagnoses">
          <tr class="white">
          <td colspan="3">
          &nbsp;
          </td>
          </tr>
          </logic:notPresent>
        </table>
      </td>
    </tr>
  </table>
</bigr:isInRole>  
  <p> 
<bigr:isInRole role1='<%=com.ardais.bigr.security.SecurityInfo.ROLE_SYSTEM_OWNER%>'>   
  <table class="background" cellpadding="0" cellspacing="0" border="0" width="80%">
    <tr> 
      <td> 
        <table class="foreground" border="0" cellspacing="1" cellpadding="3" width="100%">
          <tr class="green"> 
            <td colspan="3"> 
              <div align="center"><b><a name="DonorTreatment"></a> Donor Treatment</b></div>
            </td>
          </tr>
          <tr class="yellow"> 
            <td><b>Treatment Name</b></td>
            <td><b>Treatment Type</b></td>
            <td><b>Start Date</b></td>
          </tr>
          <logic:present name="donorBean" property="treatments">
          <logic:iterate id="treatment" name="donorBean" property="treatments">
          <tr class="white"> 
            <td> 
              <bean:write name="treatment" property="treatmentDisplay"/>
            </td>
            <td>
              <bean:write name="treatment" property="treatmentTypeDisplay"/>
            </td>
            <td>
              <bean:write name="treatment" property="startDateYYYY"/>
            </td>
          </tr>
          </logic:iterate>
          </logic:present>
          <logic:notPresent name="donorBean" property="treatments">
          <tr class="white">
          <td colspan="3">
          &nbsp;
          </td>
          </tr>
          </logic:notPresent>
        </table>
      </td>
    </tr>
  </table>
</bigr:isInRole>   
  <p> 
    <input type="button" name="Close2" value="Close" onClick="window.close();">
  </p>
  <p><a href="#top">Top Of Page</a> </p>
</div>
</body>
<script>
window.focus();
</script>
</html>