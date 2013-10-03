<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, getServletContext(), "P");
%>
<bean:define name="helper" id="section"/>
<bean:define name="helper" property="pathReport" id="path"/>

<html>
<head>
<title>Summary of Pathology Report Section</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">

</head>

<body class="bigr" onLoad="if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = 'Summary of Pathology Report Section';">
  <form name="pathSection" method="POST" onsubmit="return false;">

  <div align="center">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" border="0" cellpadding="3" cellspacing="1">
            <bean:write name="section" property="messages" filter="false"/>
          </table>
        </td>
      </tr>
    </table>
  </div>
  <p>
  
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
              	<html:link page="/ddc/Dispatch" name="path" property="consentLinkParams">
                <bean:write name="path" property="consentId"/>
                </html:link>
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
              <td class="yellow" align="right"><b>Section</b></td>
              <td>
                <bean:write name="section" property="sectionIdentifierDisplay"/>
                <logic:equal name="section" property="primary" value="Y">&nbsp;&nbsp;(primary)</logic:equal>
              </td>
            </tr>
            <tr class="white">
              <td colspan="8" align="center">
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

  <!-- Path Section details -->
  <p>
    <table class="foreground" border="0" cellpadding="3" cellspacing="0" width="100%">
      <tr class="green"> 
        <td align="left" style="border: 1px solid #336699; border-right: none;"> 
          <b>Pathology Report Section Details</b>
        </td>
        <td align="right" style="border: 1px solid #336699; border-left: none;">
          <input type="button" style="font-size: xx-small;" value="Edit" onclick="window.location.href='<html:rewrite page="/ddc/Dispatch" name="section" property="editSectionLinkParams"/>';">
        </td>
      </tr>
    </table>

    <table class="background" cellpadding="0" cellspacing="0" border="0">
    <tr><td> 
      <table class="foreground" border="0" cellpadding="3" cellspacing="1">

        <tr class="white"> 
          <td class="grey" align="right"><b>Section Diagnosis from DI Pathology Report</b></td>
          <td colspan="3"><bean:write name="section" property="diagnosisDisplay"/></td> 
        </tr>
        <tr class="white"> 
          <td class="grey" align="right">Other Section Diagnosis from DI Pathology Report</td>
          <td colspan="3">
            <logic:present name="section" property="diagnosisOther">
              <bean:write name="section" property="diagnosisOther"/>
            </logic:present>
            <logic:notPresent name="section" property="diagnosisOther">
              N/A
            </logic:notPresent>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey" align="right"><b>Section Tissue of Origin of Diagnosis</b></td>
          <td colspan="3"><bean:write name="section" property="tissueOriginDisplay"/></td> 
        </tr>
        <tr class="white"> 
          <td class="grey" align="right">Other Section Tissue of Origin of Diagnosis</td>
          <td colspan="3">                 
            <logic:present name="section" property="tissueOriginOther">
              <bean:write name="section" property="tissueOriginOther"/>
            </logic:present>
            <logic:notPresent name="section" property="tissueOriginOther">
              N/A
            </logic:notPresent>
          </td>
        </tr>
        <tr class="white"> 
          <td class="grey" align="right"><b>Section Site of Finding</b></td>
          <td colspan="3"><bean:write name="section" property="tissueFindingDisplay"/></td> 
        </tr>
        <tr class="white"> 
          <td class="grey" align="right">Other Section Site of Finding</td>
          <td colspan="3">                 
            <logic:present name="section" property="tissueFindingOther">
              <bean:write name="section" property="tissueFindingOther"/>
            </logic:present>
            <logic:notPresent name="section" property="tissueFindingOther">
              N/A
            </logic:notPresent>
          </td>
          </td>
        </tr>

        <logic:equal name="section" property="displayTumorSize" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathTumorSize" trueValue="grey minPath" falseValue="grey"/>"> 
              Diseased Tissue Size (cm x cm x cm) </td>
            <td colspan="3"><bean:write name="section" property="tumorSizeDisplay" filter="false"/></td> 
          </tr>
        </logic:equal>

        <logic:equal name="section" property="displayTumorWeight" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathTumorWeight" trueValue="grey minPath" falseValue="grey"/>"> 
              Diseased Tissue Weight (gm) </td>
            <td colspan="3"><bean:write name="section" property="tumorWeightDisplay"/></td> 
          </tr>
        </logic:equal>

        <logic:equal name="section" property="displayJointComponent" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathJointComponent" trueValue="grey minPath" falseValue="grey"/>">
              Joint Component
            </td>
            <td colspan="3"><bean:write name="section" property="jointComponentDisplay"/></td> 
          </tr>
        </logic:equal>


        <logic:equal name="section" property="displayHistologicalType" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathHistologicalType" trueValue="grey minPath" falseValue="grey"/>"> 
              Histologic Type </td>
            <td colspan="3"><bean:write name="section" property="histologicalTypeDisplay"/></td>
          </tr>            
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathHistologicalTypeOther" trueValue="grey minPath" falseValue="grey"/>"> 
              Other Histologic Type </td>
            <td colspan="3">            
              <logic:present name="section" property="histologicalTypeOther">
                <bean:write name="section" property="histologicalTypeOther"/>
              </logic:present>
              <logic:notPresent name="section" property="histologicalTypeOther">
                N/A
              </logic:notPresent>
            </td>
          </tr>
        </logic:equal>

        <logic:equal name="section" property="displayHistologicalNuclearGrade" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathHistologicalNuclearGrade" trueValue="grey minPath" falseValue="grey"/>"> 
              Histologic/Nuclear Grade </td>
            <td colspan="3"><bean:write name="section" property="histologicalNuclearGradeDisplay"/></td> 
          </tr>            
        </logic:equal>

        <logic:equal name="section" property="displayHistologicalNuclearGradeOther" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathHistologicalNuclearGradeOther" trueValue="grey minPath" falseValue="grey"/>"> 
              Other Histologic/Nuclear Grade </td>
            <td colspan="3">            
              <logic:present name="section" property="histologicalNuclearGradeOther">
                <bean:write name="section" property="histologicalNuclearGradeOther"/>
              </logic:present>
              <logic:notPresent name="section" property="histologicalNuclearGradeOther">
                N/A
              </logic:notPresent>
            </td>
          </tr>
        </logic:equal>

        <logic:equal name="section" property="displayGleason" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathGleason" trueValue="grey minPath" falseValue="grey"/>">
              Gleason Score
            </td>
            <td colspan="3">
              Primary Score: <bean:write name="section" property="gleasonPrimary"/>
              &nbsp;&nbsp;&nbsp;Secondary Score: <bean:write name="section" property="gleasonSecondary"/>
              &nbsp;&nbsp;&nbsp;Total Score: <bean:write name="section" property="gleasonTotal"/>
            </td>
          </tr>
        </logic:equal>

        <logic:equal name="section" property="displayPerineuralInvasion" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathPerineuralInvasion" trueValue="grey minPath" falseValue="grey"/>">
              Perineural Invasion Indicator
            </td>
            <td colspan="3"><bean:write name="section" property="perineuralInvasionDisplay"/></td>
          </tr>
        </logic:equal>

        <logic:equal name="section" property="displayLymphaticVascularInvasion" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathLymphaticVascularInvasion" trueValue="grey minPath" falseValue="grey"/>">
              Lymphatic Vascular Invasion
            </td>
            <td colspan="3"><bean:write name="section" property="lymphaticVascularInvasionDisplay"/></td>
          </tr>
        </logic:equal>

        <logic:equal name="section" property="displayMargins" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathMarginsInvolved" trueValue="grey minPath" falseValue="grey"/>">
              Margins Involved by Tumor
            </td>
            <td><bean:write name="section" property="marginsInvolved"/></td> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathMarginsUninvolved" trueValue="grey minPath" falseValue="grey"/>"> 
              Margins Uninvolved (Include Distance) </td>
            <td><bean:write name="section" property="marginsUninvolved"/></td> 
          </tr>
        </logic:equal>

        <logic:equal name="section" property="displayCellInfiltrate" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathInflammCellInfiltrate" trueValue="grey minPath" falseValue="grey"/>">
              Type of Inflammatory Cell Infiltrate
            </td>
            <td><bean:write name="section" property="inflammCellInfiltrateDisplay"/></td> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathCellInfiltrateAmount" trueValue="grey minPath" falseValue="grey"/>">
              Cell Infiltrate Amount
            </td>
            <td><bean:write name="section" property="cellInfiltrateAmountDisplay"/></td> 
          </tr>
        </logic:equal>

        <logic:equal name="section" property="displayTumorStageType" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathTumorStageType" trueValue="grey minPath" falseValue="grey"/>"> 
              Tumor Stage Classification </td>
            <td colspan="3"><bean:write name="section" property="tumorStageTypeDisplay"/></td> 
          </tr>
          <tr class="white"> 
            <td class="grey" align="right">Other Tumor Stage Classification</td>
            <td colspan="3">            
              <logic:present name="section" property="tumorStageTypeOther">
                <bean:write name="section" property="tumorStageTypeOther"/>
              </logic:present>
              <logic:notPresent name="section" property="tumorStageTypeOther">
                N/A
              </logic:notPresent>
            </td>
          </tr>
        </logic:equal>

        <logic:equal name="section" property="displayTumorStageDesc" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathTumorStageDesc" trueValue="grey minPath" falseValue="grey"/>">
              Tumor Stage
            </td>
            <td colspan="3">
              <bean:write name="section" property="tumorStageDescDisplay"/>
              <logic:present name="section" property="tumorStageDescInd">
                <logic:equal name="section" property="tumorStageDescInd" value="R">(reported)</logic:equal>
                <logic:equal name="section" property="tumorStageDescInd" value="D">(derived)</logic:equal>
              </logic:present>
            </td> 
          </tr>
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathTumorStageDescOther" trueValue="grey minPath" falseValue="grey"/>">
              Other Tumor Stage
            </td>
            <td colspan="3">            
              <logic:present name="section" property="tumorStageDescOther">
                <bean:write name="section" property="tumorStageDescOther"/>
              </logic:present>
              <logic:notPresent name="section" property="tumorStageDescOther">
                N/A
              </logic:notPresent>
            </td>
          </tr>
        </logic:equal>

        <logic:equal name="section" property="displayVenousVesselInvasion" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathVenousVesselInvasion" trueValue="grey minPath" falseValue="grey"/>">
              Venous Vessel Invasion
            </td>
            <td colspan="3"><bean:write name="section" property="venousVesselInvasionDisplay"/></td> 
          </tr>
        </logic:equal>

        <logic:equal name="section" property="displayTumorConfig" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathTumorConfig" trueValue="grey minPath" falseValue="grey"/>">
              Tumor Configuration
            </td>
            <td colspan="3"><bean:write name="section" property="tumorConfigDisplay"/></td> 
          </tr>
        </logic:equal>

        <logic:equal name="section" property="displayExtensiveIntraductalComp" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathExtensiveIntraductalComp" trueValue="grey minPath" falseValue="grey"/>">
              Extensive Intraductal Component
            </td>
            <td colspan="3"><bean:write name="section" property="extensiveIntraductalCompDisplay"/></td> 
          </tr>
        </logic:equal>

        <logic:equal name="section" property="displayExtranodal" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathExtranodalSpread" trueValue="grey minPath" falseValue="grey"/>">
              Extranodal Spread
            </td>
            <td><bean:write name="section" property="extranodalSpreadDisplay"/></td> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathSizeLargestNodalMets" trueValue="grey minPath" falseValue="grey"/>">
              Size of Largest Nodal Metastasis
            </td>
            <td><bean:write name="section" property="sizeLargestNodalMets"/></td>
          </tr>
        </logic:equal>

        <logic:equal name="section" property="displayLymphNodeNotes" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathLymphNodeNotes" trueValue="grey minPath" falseValue="grey"/>">
              Lymph Node Notes
            </td>
            <td colspan="3"><bean:write name="section" property="lymphNodeNotes"/></td> 
          </tr>
        </logic:equal>

        <logic:equal name="section" property="displayTotalNodes" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathTotalNodesExamined" trueValue="grey minPath" falseValue="grey"/>">
              Total Number of Nodes Examined
            </td>
            <td><bean:write name="section" property="totalNodesExaminedDisplay"/></td> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathTotalNodesPositive" trueValue="grey minPath" falseValue="grey"/>">
              Total Number of Positive Nodes
            </td>
            <td><bean:write name="section" property="totalNodesPositiveDisplay"/></td> 
          </tr>
        </logic:equal>

        <logic:equal name="section" property="displayLymphNodeStage" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathLymphNodeStage" trueValue="grey minPath" falseValue="grey"/>">
              Lymph Node Stage
            </td>
            <td colspan="3">
              <bean:write name="section" property="lymphNodeStageDisplay"/>
              <logic:present name="section" property="lymphNodeStageInd">
                <logic:equal name="section" property="lymphNodeStageInd" value="R">(reported)</logic:equal>
                <logic:equal name="section" property="lymphNodeStageInd" value="D">(derived)</logic:equal>
              </logic:present>
            </td>
          </tr>
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathLymphNodeStageOther" trueValue="grey minPath" falseValue="grey"/>">
              Other Lymph Node Stage
            </td>
            <td colspan="3">            
              <logic:present name="section" property="lymphNodeStageOther">
                <bean:write name="section" property="lymphNodeStageOther"/>
              </logic:present>
              <logic:notPresent name="section" property="lymphNodeStageOther">
                N/A
              </logic:notPresent>
            </td>
          </tr>
        </logic:equal>

        <logic:equal name="section" property="displayDistantMetastasis" value="true">  
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathDistantMetastasis" trueValue="grey minPath" falseValue="grey"/>">
              Distant Metastasis
            </td>
            <td colspan="3">
              <bean:write name="section" property="distantMetastasisDisplay"/>
              <logic:present name="section" property="distantMetastasisInd">
                <logic:equal name="section" property="distantMetastasisInd" value="R">(reported)</logic:equal>
                <logic:equal name="section" property="distantMetastasisInd" value="D">(derived)</logic:equal>
              </logic:present>
            </td>
          </tr>
          <tr class="white"> 
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathDistantMetastasisOther" trueValue="grey minPath" falseValue="grey"/>">
              Other Distant Metastasis
            </td>
            <td colspan="3">            
              <logic:present name="section" property="distantMetastasisOther">
                <bean:write name="section" property="distantMetastasisOther"/>
              </logic:present>
              <logic:notPresent name="section" property="distantMetastasisOther">
                N/A
              </logic:notPresent>
            </td>
          </tr>
        </logic:equal>

        <logic:equal name="section" property="displayStageGrouping" value="true">  
          <tr class="white">
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathStageGrouping" trueValue="grey minPath" falseValue="grey"/>">
              Minimum Stage Grouping
            </td>
            <td colspan="3">
              <bean:write name="section" property="stageGroupingDisplay"/>
            </td>
          </tr>
          <tr class="white">
            <td align="right" class="<bigr:conditionalWrite name="section" property="minPathStageGroupingOther" trueValue="grey minPath" falseValue="grey"/>">
              Other Minimum Stage Grouping
            </td>
            <td colspan="3">
              <logic:present name="section" property="stageGroupingOther">
                <bean:write name="section" property="stageGroupingOther"/>
              </logic:present>
              <logic:notPresent name="section" property="stageGroupingOther">
                N/A
              </logic:notPresent>
            </td>
          </tr>
        </logic:equal>

        <tr class="white"> 
          <td align="right" class="<bigr:conditionalWrite name="section" property="minPathSectionNotes" trueValue="grey minPath" falseValue="grey"/>">
            Pathology Section Notes
          </td>
          <td colspan="3"><bigr:beanWrite name="section" property="sectionNotes" filter="true" whitespace="true"/></td>
        </tr>
      </table>
    </td></tr>
  </table></p>

  <!-- Additional Findings -->
  <p>
    <table class="foreground" border="0" cellpadding="3" cellspacing="0" width="100%">
      <tr class="green"> 
        <td align="left" style="border: 1px solid #336699; border-right: none;"> 
          <b>Additional Pathology Findings (Finding: Tissue)</b>
        </td>
        <td align="right" style="border: 1px solid #336699; border-left: none;">
          <input type="button" style="font-size: xx-small;" value="Add New Additional Finding" onclick="window.location.href='<html:rewrite page="/ddc/Dispatch" name="section" property="newFindingLinkParams"/>';">
        </td>
      </tr>
    </table>
    <logic:present name="section" property="findings">
      <table class="foreground" border="0" cellpadding="3" cellspacing="0">
		<bean:write name="section" property="startFindings"/>
        <logic:iterate name="section" property="findings" id="finding">
          <tr class="white"> 
            <td align="right">
              <html:link page="/ddc/Dispatch" name="section" property="currentFindingLinkParams">
                <logic:notEqual name="finding" property="diagnosis" value="CA00038D^^">
                  <bean:write name="finding" property="diagnosisDisplay"/>:
                </logic:notEqual>
                <logic:equal name="finding" property="diagnosis" value="CA00038D^^">
                  <bean:write name="finding" property="diagnosisDisplay"/>
                  (<bean:write name="finding" property="diagnosisOther"/>):
                </logic:equal>
              </html:link>
            </td>
            <td>
              <bean:write name="finding" property="tissueDisplay"/>
              <logic:equal name="finding" property="tissue" value="91723000">
                (<bean:write name="finding" property="tissueOther"/>)
              </logic:equal>
            </td>
          </tr>
		  <bean:write name="section" property="nextFinding"/>
        </logic:iterate>
      </table>
    </logic:present>
    <logic:notPresent name="section" property="findings">
      &nbsp;&lt;There are no Additional Pathology Findings associated with this Pathology Report Section&gt;  
    </logic:notPresent>
  </p>

  </form>
</body>
</html>
