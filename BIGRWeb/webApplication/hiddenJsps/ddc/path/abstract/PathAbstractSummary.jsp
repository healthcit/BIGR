<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, getServletContext(), "P");
%>
<bean:define name="helper" id="path" />

<html>
<head>
<title>Summary of Pathology Report Abstraction</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
</head>

<body class="bigr"
      onLoad="if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = 'Summary of Pathology Report Abstraction';">

  <!-- Context -->
  <div align="center">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" border="0" cellpadding="3" cellspacing="1">
            <tr class="white"> 
              <td class="yellow" align="right"><b>Donor</b></td>
              <td>
              	<html:link page="/ddc/Dispatch" name="path" property="donorLinkParams">
                <bean:write name="path" property="ardaisId"/>
                </html:link>
                <logic:notEmpty name="path" property="donorCustomerId">
                  (<bean:write name="path" property="donorCustomerId" />)
                </logic:notEmpty>
              </td>
            </tr>
            <tr class="white"> 
              <td class="yellow" align="right"><b>Case</b></td>
              <td>
                <bean:write name="path" property="consentId"/>
                <logic:notEmpty name="path" property="consentCustomerId">
                  (<bean:write name="path" property="consentCustomerId" />)
                </logic:notEmpty>
              </td>
            </tr>
            <tr class="white"> 
              <td class="yellow" align="right"><b>Disease Type</b></td>
              <td><bean:write name="path" property="diseaseName"/></td>
            </tr>
            <tr class="white">
              <td colspan="6" align="center">
                <logic:present name="path" property="rawPathReport">
                  <span class="fakeLink" onclick="window.open('<html:rewrite page="/ddc/Dispatch" name="path" property="rawPopupLinkParams"/>', 'RawPathReport','width=600,height=600,top=100,left=100,resizable=yes,scrollbars=yes,status=yes');">
                  Show Full Text Pathology Report</span>
                </logic:present>
                <logic:notPresent name="path" property="rawPathReport">
                  &lt;The Full Text Pathology Report has not been entered&gt;                                
                </logic:notPresent>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </div>

  <!-- Case-level details -->
  <p>
    <table class="foreground" border="0" cellpadding="3" cellspacing="0" width="100%">
      <tr class="green"> 
        <td align="left" style="border: 1px solid #336699; border-right: none;"> 
          <b>Pathology Report Case-Level Details</b>
        </td>
        <td align="right" style="border: 1px solid #336699; border-left: none;">
          <input type="button" style="font-size: xx-small;" value="Edit" onclick="window.location.href='<html:rewrite page="/ddc/Dispatch" name="path" property="editPathLinkParams"/>';">
        </td>
      </tr>
    </table>
    <table class="foreground" border="0" cellpadding="3" cellspacing="0">
      <tr class="white"> 
        <td align="right"><b>Pathology Report Date:</b></td>
        <td>
          <bean:write name="path" property="pathReportMonth"/>
          <logic:present name="path" property="pathReportMonth">/</logic:present>
          <bean:write name="path" property="pathReportYear"/>
        </td>
      </tr>
      <tr class="white"> 
        <td align="right"><b>Procedure:</b></td>
        <td><bean:write name="path" property="procedureName"/></td>
      </tr>
      <tr class="white"> 
        <td align="right">Other Procedure:</td>
        <td><bean:write name="path" property="procedureOther"/></td>
      </tr>
      <tr class="white"> 
        <td align="right" valign="top">Case Pathology Notes:</td>
        <td><bigr:beanWrite name="path" property="additionalNote" filter="true" whitespace="true"/></td>
      </tr>
    </table>
  </p>
    
  <!-- Sections -->
  <p>
    <table class="foreground" border="0" cellpadding="3" cellspacing="0" width="100%">
      <tr class="green"> 
        
    <td align="left" style="border: 1px solid #336699; border-right: none;"> <b>Pathology 
      Report Sections (Section: Id -- Diagnosis -- Tissue of Origin of Diagnosis&nbsp;-- 
      Site of Finding)</b> </td>
        <td align="right" style="border: 1px solid #336699; border-left: none;">
				  <logic:notEqual name="path" property="totalSections" value="12">
          	<input type="button" style="font-size: xx-small;" value="Add New Section" onclick="window.location.href='<html:rewrite page="/ddc/Dispatch" name="path" property="newSectionLinkParams"/>';">
					</logic:notEqual>
				  <logic:equal name="path" property="totalSections" value="12">
          	<input type="button" style="font-size: xx-small;" value="Add New Section" disabled/>
					</logic:equal>
					
				</td>
      </tr>
    </table>
    <logic:present name="path" property="sections">
      <table class="foreground" border="0" cellpadding="3" cellspacing="0">
        <bean:write name="path" property="startSections"/>
        <logic:iterate name="path" property="sections" id="section">
          <tr class="white"> 
            <td align="right" nowrap>
              <html:link page="/ddc/Dispatch" name="path" property="currentSectionLinkParams">
                <logic:equal name="section" property="primary" value="Y"><b></logic:equal>
                <bean:write name="section" property="sectionIdentifierDisplay"/>
                <logic:equal name="section" property="primary" value="Y"></b></logic:equal>
              </html:link>
			  <logic:equal name="section" property="primary" value="Y">&nbsp;&nbsp;<b>[primary section]</b></logic:equal>
            </td>
            <td align="center" valign="center" nowrap>--</td>
            <td>
              <html:link page="/ddc/Dispatch" name="path" property="currentSectionLinkParams">
                <logic:equal name="section" property="primary" value="Y"><b></logic:equal>
                  <bean:write name="section" property="diagnosisDisplay"/>
                  <logic:equal name="section" property="diagnosis" value="CA00038D^^">
                    (<bean:write name="section" property="diagnosisOther"/>)
                  </logic:equal>
                <logic:equal name="section" property="primary" value="Y"></b></logic:equal>
              </html:link>
            </td>
            <td align="center" valign="center" nowrap>--</td>
            <td nowrap>
              <logic:equal name="section" property="primary" value="Y"><b></logic:equal>
              <bean:write name="section" property="tissueOriginDisplay"/>
              <logic:equal name="section" property="tissueOrigin" value="91723000">
                (<bean:write name="section" property="tissueOriginOther"/>)
              </logic:equal>
              <logic:equal name="section" property="primary" value="Y"></b></logic:equal>
            </td>
            <td align="center" valign="center" nowrap>--</td>
            <td nowrap>
                <logic:equal name="section" property="primary" value="Y"><b></logic:equal>
                <bean:write name="section" property="tissueFindingDisplay"/>
                <logic:equal name="section" property="tissueFinding" value="91723000">
                  (<bean:write name="section" property="tissueFindingOther"/>)
                </logic:equal>
                </b>
            </td>
          </tr>
          <bean:write name="path" property="nextSection"/>
        </logic:iterate>
      </table>
    </logic:present>
    <logic:notPresent name="path" property="sections">
      &nbsp;&lt;There are no Pathology Report Sections associated with this Pathology Report&gt;  
    </logic:notPresent>
  </p>

  <!-- Diagnostic Tests -->
  <p>
    <table class="foreground" border="0" cellpadding="3" cellspacing="0" width="100%">
      <tr class="green"> 
        
    <td align="left" style="border: 1px solid #336699; border-right: none;"> <b>Diagnostic 
      Tests (Diagnostic Test Name: Result)</b> </td>
        <td align="right" style="border: 1px solid #336699; border-left: none;">
          <input type="button" style="font-size: xx-small;" value="Add New Diagnostic Test" onclick="window.location.href='<html:rewrite page="/ddc/Dispatch" name="path" property="newDiagnosticLinkParams"/>';">
        </td>
      </tr>
    </table>
    <logic:present name="path" property="diagnostics">
      <bean:write name="path" property="startDiagnostics"/>   
      <table class="foreground" border="0" cellpadding="3" cellspacing="0">
        <logic:iterate name="path" property="diagnostics" id="diagnostic">
          <tr class="white">
            <td align="right">
              <html:link page="/ddc/Dispatch" name="path" property="currentDiagnosticLinkParams">
              <bean:write name="diagnostic" property="diagnosticDisplay"/>
              </html:link>:
            </td>
            <td>
              <bean:write name="diagnostic" property="resultDisplay"/>
            </td>
          </tr>
          <bean:write name="path" property="nextDiagnostic"/>
        </logic:iterate>
      </table>
    </logic:present>
    <logic:notPresent name="path" property="diagnostics">
      &nbsp;&lt;There are no Diagnostic Tests associated with this Pathology Report&gt;  
    </logic:notPresent>
  </p>

</body>
</html>
