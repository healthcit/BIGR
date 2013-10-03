<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>

<%@ taglib uri="/tld/struts-bean"   prefix="bean" %>
<%@ taglib uri="/tld/struts-html"   prefix="html" %>
<%@ taglib uri="/tld/struts-logic"  prefix="logic"%>
<%@ taglib uri="/tld/bigr"          prefix="bigr" %>

<bean:define id="pathBean" name="pathReport"/>
<bean:define id="donorBean" name="donor"/>
<bean:define id="consentBean" name="consent"/>

<html>
  <head>
    <title><bean:write name="pathBean" property="consentId"/>: Case Report</title>
	<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
  </head>

  <body class="bigr">
    <p align="center">
      <b>
        <font size="+1"><a name="top"></a>Donor Case Report<br>
        Information Regarding Case</font>
      </b>
    <br>
    <form method="post" action="<html:rewrite page="/library/Dispatch"/>">
      <input type="hidden" name="donor_id" value="<bean:write name="donor" property="ardaisId"/>">
      <input type="hidden" name="case_id" value="<bean:write name="pathBean" property="consentId"/>">
      <input type="hidden" name="op" value="PathInfoStart">
      <input type="submit" name="Submit2" value="Donor Profile">
    </form>
</p>
    
    <table class="background" cellpadding="0" cellspacing="0" border="0" width="100%">
      <tr> 
        <td> 
          <table class="foreground" border="0" cellpadding="3" cellspacing="1" width="100%">
            <tr class="green"> 
              <td colspan="2"> 
                <div align="center">
                  <b>
                    <bean:write name="consentBean" property="consentId"/>
                    <logic:notEmpty name="consentBean" property="customerId">
                      (<bean:write name="consentBean" property="customerId" />)
                    </logic:notEmpty>
                    : 
                    <bean:write name="donorBean" property="genderDisplay"/> -
                    <logic:present name="consentBean" property="ageAtCollection">
                      <bean:write name="consentBean" property="ageAtCollection"/> -
                    </logic:present>
                    <bean:write name="donorBean" property="raceDisplay"/>-
                  </b>
                </div>
              </td>
            </tr>
            <tr class="white"> 
              <td width="25%" class="grey"> 
                <div align="right"><b>Case ID</b></div>
              </td>
              <td>
                <bean:write name="pathBean" property="consentId"/>
                <logic:notEmpty name="consentBean" property="customerId">
                  (<bean:write name="consentBean" property="customerId" />)
                </logic:notEmpty>
              </td>
            </tr>
            <tr class="white">
              <td width="25%" class="grey">
                <div align="right"><b>Age at Collection</b></div>
              </td>
              <td>
                <bean:write name="consentBean" property="ageAtCollection" ignore="true"/>
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey"> 
                <div align="right"><b>Procedure</b></div>
              </td>
              <td>
                <bean:write name="pathBean" property="procedureName"/>
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey"> 
                <div align="right"><b>Case Pathology Notes</b></div>
              </td>
              <td>
                <bean:write name="pathBean" property="additionalNote"/>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
    <br>
    
    
    <table class="background" cellpadding="0" cellspacing="0" border="0" width="100%">
      <tr> 
        <td> 
          <table class="foreground" cellpadding="3" cellspacing="1" border="0" width="100%">
            <tr class="green"> 
              <td colspan="2"> 
                <div align="center"><b>Diagnostic Test Detail</b></div>
              </td>
            </tr>
            <logic:present name="pathReport" property="diagnostics">
              <logic:iterate name="pathReport" property="diagnostics" id="diagnostic" indexId="cnt">
            <tr class="white"> 
              <td>
                <a href="#dtest<bean:write name="cnt"/>"><bean:write name="diagnostic" property="diagnosticDisplay"/></a>              </td>
              <td>
                <bean:write name="diagnostic" property="resultDisplay"/>
              </td>
            </tr>
            </logic:iterate>
            </logic:present>
            <logic:notPresent name="pathReport" property="diagnostics">
              <tr class="white">
                <td colspan="2">&nbsp;</td>
              </tr>
            </logic:notPresent>
          </table>
        </td>
      </tr>
    </table>
    <br>
    
    
    <table class="background" cellpadding="0" cellspacing="0" border="0" width="100%">
      <tr> 
        <td> 
          <table class="foreground" cellpadding="3" cellspacing="1" border="0" width="100%">
            <tr class="green"> 
              <td colspan="3"> 
                <div align="center"><b>Pathology Report Sections</b></div>
              </td>
            </tr>
            <tr class="green"> 
            	<td>
            		<div align="left"><b>Section Diagnosis from Donor Institution Pathology Report</b></div>
            	</td>
            	<td>
            		<div align="left"><b>Section Tissue of Origin of Diagnosis</b></div>
            	</td>
            	<td>
            		<div align="left"><b>Section Site of Finding</b></div>
            	</td>
            </tr>
            <logic:present name="pathReport" property="sections">
            <logic:iterate name="pathReport" property="sections" id="section" indexId="cnt">
              <tr class="white"> 
                <td> 
                  <a href="#report<bean:write name="cnt"/>">
                    <b>
                      <bean:write name="section" property="diagnosisDisplay"/>
                      <logic:equal name="section" property="primary" value="Y">(Primary Section of Report)</logic:equal>
                      <logic:notEqual name="section" property="primary" value="Y">(Secondary)</logic:notEqual>
                    </b>
                  </a>
                </td>
				<td>
                    <logic:present name="section" property="tissueOriginOther">
                      <bean:write name="section" property="tissueOriginOther"/>
                    </logic:present>
                    <logic:notPresent name="section" property="tissueOriginOther">
                      <bean:write name="section" property="tissueOriginDisplay"/>
                    </logic:notPresent>
				</td>
				<td>
                    <logic:present name="section" property="tissueFindingOther">
                      <bean:write name="section" property="tissueFindingOther"/>
                    </logic:present>
                    <logic:notPresent name="section" property="tissueFindingOther">
                      <bean:write name="section" property="tissueFindingDisplay"/>
                    </logic:notPresent>
				</td>
              </tr>
            </logic:iterate>
            </logic:present>
            <logic:notPresent name="pathReport" property="sections">
              <tr class="white">
                <td colspan="3">Data Not Yet Available</td>
              </tr>
            </logic:notPresent>
          </table>
        </td>
      </tr>
    </table>
    <br>
    
    
    <!-- Path Section details -->
    <logic:present name="pathReport" property="sections">
      <logic:iterate name="pathReport" property="sections" id="section" indexId="cnt">
        <a name="report<bean:write name="cnt"/>"></a>
        <table class="background" cellpadding="0" cellspacing="0" border="0" width="100%">
          <tr>
            <td> 
              <table class="foreground" border="0" cellpadding="3" cellspacing="1" width="100%">
                <tr class="green"> 
                  <td colspan="4"> 
                    <b>
                      Pathology Report Section Details
                      <logic:equal name="section" property="primary" value="Y">(Primary Section of Report)</logic:equal>
                      <logic:notEqual name="section" property="primary" value="Y">(Secondary)</logic:notEqual>
                    </b>
                  </td>
                </tr>
                <tr class="white"> 
                  <td class="grey" align="right">
                    <b>Section Diagnosis from Donor Institution Pathology Report</b>
                  </td>
                  <td colspan="3">
                    <logic:present name="section" property="diagnosisOther">
                      <bean:write name="section" property="diagnosisOther"/>
                    </logic:present>
                    <logic:notPresent name="section" property="diagnosisOther">
                      <bean:write name="section" property="diagnosisDisplay"/>
                    </logic:notPresent>
                  </td>
                </tr>
                <tr class="white"> 
                  <td class="grey" align="right">
                    <b>Section Tissue of Origin of Diagnosis</b>
                  </td>
                  <td colspan="3">
                    <logic:present name="section" property="tissueOriginOther">
                      <bean:write name="section" property="tissueOriginOther"/>
                    </logic:present>
                    <logic:notPresent name="section" property="tissueOriginOther">
                      <bean:write name="section" property="tissueOriginDisplay"/>
                    </logic:notPresent>
                  </td> 
                </tr>
                <tr class="white"> 
                  <td class="grey" align="right">
                    <b>Section Site of Finding</b>
                  </td>
                  <td colspan="3">
                    <logic:present name="section" property="tissueFindingOther">
                      <bean:write name="section" property="tissueFindingOther"/>
                    </logic:present>
                    <logic:notPresent name="section" property="tissueFindingOther">
                      <bean:write name="section" property="tissueFindingDisplay"/>
                    </logic:notPresent>
                  </td> 
                </tr>
              <logic:equal name="section" property="displayTumorSize" value="true">  
                <tr class="white"> 
                  <td class="grey" align="right"><b>Diseased Tissue Size</b> (cm x cm x cm)</td>
                  <td colspan="3"><bean:write name="section" property="tumorSizeDisplay" filter="false"/></td> 
                </tr>
              </logic:equal>
              <logic:equal name="section" property="displayTumorWeight" value="true">  
                <tr class="white"> 
                  <td class="grey" align="right"><b>Diseased Tissue Weight</b> (gm)</td>
                  <td colspan="3"><bean:write name="section" property="tumorWeightDisplay"/></td> 
                </tr>
              </logic:equal>
              <logic:equal name="section" property="displayJointComponent" value="true">  
                <tr class="white"> 
                  <td class="grey" align="right"><b>Joint Component</b></td>
                  <td colspan="3"><bean:write name="section" property="jointComponentDisplay"/></td> 
                </tr>
              </logic:equal>
 
              <logic:equal name="section" property="displayHistologicalType" value="true">  
                <tr class="white"> 
                  <td class="grey" align="right"><b>Histologic Type</b></td>
                  <td colspan="3">
                    <logic:present name="section" property="histologicalTypeOther">
                      <bean:write name="section" property="histologicalTypeOther"/>
                    </logic:present>
                    <logic:notPresent name="section" property="histologicalTypeOther">
                      <bean:write name="section" property="histologicalTypeDisplay"/></td>
                    </logic:notPresent>
                </tr>            
              </logic:equal>
              <logic:equal name="section" property="displayHistologicalNuclearGrade" value="true">  
              <tr class="white"> 
	            <td class="grey" align="right"><b>Histologic/Nuclear Grade</b></td>
                <td colspan="3">
                  	<logic:present name="section" property="histologicalNuclearGradeOther">
                  		<bean:write name="section" property="histologicalNuclearGradeOther"/>
                  	</logic:present>
                  	<logic:notPresent name="section" property="histologicalNuclearGradeOther">
	                  	<bean:write name="section" property="histologicalNuclearGradeDisplay"/>
	                </logic:notPresent>
                </td> 
              </tr>         
              </logic:equal>
              <logic:equal name="section" property="displayGleason" value="true">  
                <tr class="white"> 
                  <td class="grey" align="right"><b>Gleason Score</b></td>
                  <td colspan="3"><b>
                    Primary Score:</b> <bean:write name="section" property="gleasonPrimary"/>
                    &nbsp;&nbsp;&nbsp;<b>Secondary Score:</b> <bean:write name="section" property="gleasonSecondary"/>
                    &nbsp;&nbsp;&nbsp;<b>Total Score:</b> <bean:write name="section" property="gleasonTotal"/>
                  </td>
                </tr>
              </logic:equal>
              <logic:equal name="section" property="displayPerineuralInvasion" value="true">  
                <tr class="white"> 
                  <td class="grey" align="right"><b>Perineural Invasion Indicator</b></td>
                  <td colspan="3"><bean:write name="section" property="perineuralInvasionDisplay"/></td>
                </tr>
              </logic:equal>
              <logic:equal name="section" property="displayLymphaticVascularInvasion" value="true">  
                <tr class="white"> 
                  <td class="grey" align="right"><b>Lymphatic Vascular Invasion</b></td>
                  <td colspan="3"><bean:write name="section" property="lymphaticVascularInvasionDisplay"/></td>
                </tr>
              </logic:equal>
              <logic:equal name="section" property="displayMargins" value="true">  
                <tr class="white"> 
                  <td class="grey" align="right"><b>Margins Involved by Tumor</b></td>
                  <td><bean:write name="section" property="marginsInvolved"/></td> 
                  <td class="grey" align="right"><b>Margins Uninvolved (Include Distance)</b></td>
                  <td><bean:write name="section" property="marginsUninvolved"/></td> 
                </tr>
              </logic:equal>
              <logic:equal name="section" property="displayCellInfiltrate" value="true">  
                <tr class="white"> 
                  <td class="grey" align="right"><b>Type of Inflammatory Cell Infiltrate</b></td>
                  <td><bean:write name="section" property="inflammCellInfiltrateDisplay"/></td> 
                  <td class="grey" align="right"><b>Cell Infiltrate Amount</b></td>
                  <td><bean:write name="section" property="cellInfiltrateAmountDisplay"/></td> 
                </tr>
              </logic:equal>
              <logic:equal name="section" property="displayTumorStageType" value="true">  
                <tr class="white"> 
                  <td class="grey" align="right"><b>Tumor Stage Classification</b></td>
                  <td colspan="3">
                    <logic:present name="section" property="tumorStageTypeOther">
                      <bean:write name="section" property="tumorStageTypeOther"/>
                    </logic:present>
                    <logic:notPresent name="section" property="tumorStageTypeOther">
                      <bean:write name="section" property="tumorStageTypeDisplay"/></td>
                    </logic:notPresent>
                </tr>
              </logic:equal>
              <logic:equal name="section" property="displayTumorStageDesc" value="true">  
                <tr class="white"> 
                  <td class="grey" align="right"><b>Tumor Stage</b></td>
                  <td colspan="3">
                    <logic:present name="section" property="tumorStageDescOther">
                      <bean:write name="section" property="tumorStageDescOther"/>
                    </logic:present>
                    <logic:notPresent name="section" property="tumorStageDescOther">
                      <bean:write name="section" property="tumorStageDescDisplay"/>
                    </logic:notPresent>
                    <logic:present name="section" property="tumorStageDescInd">
                      <logic:equal name="section" property="tumorStageDescInd" value="R">(reported)</logic:equal>
                      <logic:equal name="section" property="tumorStageDescInd" value="D">(derived)</logic:equal>
                    </logic:present>
                  </td> 
                </tr>
              </logic:equal>
              <logic:equal name="section" property="displayVenousVesselInvasion" value="true">  
                <tr class="white"> 
                  <td class="grey" align="right"><b>Venous Vessel Invasion</b></td>
                  <td colspan="3"><bean:write name="section" property="venousVesselInvasionDisplay"/></td> 
                </tr>
              </logic:equal>
              <logic:equal name="section" property="displayTumorConfig" value="true">  
                <tr class="white"> 
                  <td class="grey" align="right"><b>Tumor Configuration</b></td>
                  <td colspan="3"><bean:write name="section" property="tumorConfigDisplay"/></td> 
                </tr>
              </logic:equal>
              <logic:equal name="section" property="displayExtensiveIntraductalComp" value="true">  
                <tr class="white"> 
                  <td class="grey" align="right"><b>Extensive Intraductal Component</b></td>
                  <td colspan="3"><bean:write name="section" property="extensiveIntraductalCompDisplay"/></td> 
                </tr>
              </logic:equal>
              <logic:equal name="section" property="displayExtranodal" value="true">  
                <tr class="white"> 
                  <td class="grey" align="right"><b>Extranodal Spread</b></td>
                  <td><bean:write name="section" property="extranodalSpreadDisplay"/></td> 
                  <td class="grey" align="right"><b>Size of Largest Nodal Metastasis</b></td>
                  <td><bean:write name="section" property="sizeLargestNodalMets"/></td>
                </tr>
              </logic:equal>
              <logic:equal name="section" property="displayLymphNodeNotes" value="true">  
                <tr class="white"> 
                  <td class="grey" align="right"><b>Lymph Node Notes</b></td>
                  <td colspan="3"><bean:write name="section" property="lymphNodeNotes"/></td> 
                </tr>
              </logic:equal>
              <logic:equal name="section" property="displayTotalNodes" value="true">  
                <tr class="white"> 
                  <td class="grey" align="right"><b>Total Number of Nodes Examined</b></td>
                  <td><bean:write name="section" property="totalNodesExaminedDisplay"/></td> 
                  <td class="grey" align="right"><b>Total Number of Positive Nodes</b></td>
                  <td><bean:write name="section" property="totalNodesPositiveDisplay"/></td> 
                </tr>
              </logic:equal>
              <logic:equal name="section" property="displayLymphNodeStage" value="true">  
                <tr class="white"> 
                  <td class="grey" align="right"><b>Lymph Node Stage</b></td>
                  <td colspan="3">
                    <logic:present name="section" property="lymphNodeStageOther">
                      <bean:write name="section" property="lymphNodeStageOther"/>
                    </logic:present>
                    <logic:notPresent name="section" property="lymphNodeStageOther">
                      <bean:write name="section" property="lymphNodeStageDisplay"/>
                    </logic:notPresent>
                    <logic:present name="section" property="lymphNodeStageInd">
                      <logic:equal name="section" property="lymphNodeStageInd" value="R">(reported)</logic:equal>
                      <logic:equal name="section" property="lymphNodeStageInd" value="D">(derived)</logic:equal>
                    </logic:present>
                  </td>
                </tr>
              </logic:equal>
              <logic:equal name="section" property="displayDistantMetastasis" value="true">  
                <tr class="white"> 
                  <td class="grey" align="right"><b>Distant Metastasis</b></td>
                  <td colspan="3">
                    <logic:present name="section" property="distantMetastasisOther">
                      <bean:write name="section" property="distantMetastasisOther"/>
                    </logic:present>
                    <logic:notPresent name="section" property="distantMetastasisOther">
                      <bean:write name="section" property="distantMetastasisDisplay"/>
                    </logic:notPresent>
                    <logic:present name="section" property="distantMetastasisInd">
                      <logic:equal name="section" property="distantMetastasisInd" value="R">(reported)</logic:equal>
                      <logic:equal name="section" property="distantMetastasisInd" value="D">(derived)</logic:equal>
                    </logic:present>
                  </td>
                </tr>
              </logic:equal>
              <logic:equal name="section" property="displayStageGrouping" value="true">  
                <tr class="white">
                  <td class="grey" align="right"><b>Minimum Stage Grouping</b></td>
                  <td colspan="3">
                    <logic:present name="section" property="stageGroupingOther">
                      <bean:write name="section" property="stageGroupingOther"/>
                    </logic:present>
                    <logic:notPresent name="section" property="stageGroupingOther">
                      <bean:write name="section" property="stageGroupingDisplay"/>
                    </logic:notPresent>
                  </td>
                </tr>
              </logic:equal>
              <tr class="white"> 
                <td class="grey" align="right"><b>Pathology Section Notes</b></td>
                <td colspan="3"><bean:write name="section" property="sectionNotes"/></td>
              </tr>
              <tr class="white"> 
                <td colspan="4">
                  <logic:present name="section" property="findings">
                    <logic:iterate id="finding" name="section" property="findings">
                      <div align="center">
                        <a name="report<bean:write name="cnt"/>af"></a>        
                        <table class="background" cellpadding="0" cellspacing="0" border="0" width="100%">
                          <tr> 
                            <td> 
                              <table class="foreground" cellpadding="3" cellspacing="1" border="0" width="100%">
                                <tr class="green"> 
                                  <td colspan="2"> 
                                    <div align="center"><b>Additional Pathology Detail</b></div>
                                  </td>
                                </tr>
                                <tr class="white"> 
                                  <td class="grey" width="40%"> 
                                    <div align="right"><b>Additional Pathology Finding</b></div>
                                  </td>
                                  <td> 
                                    <div align="left">
                                    <bean:write name="finding" property="diagnosisDisplay"/>
									<logic:equal name="finding" property="diagnosisDisplay" value = "Other diagnosis">
										( <bean:write name="finding" property="diagnosisOther"/> )
									</logic:equal>                                    
                                    </div>
                                  </td>
                                </tr>
                                <tr class="white"> 
                                  <td class="grey" width="40%"> 
                                    <div align="right"><b>Tissue</b></div>
                                  </td>
                                  <td> 
                                    <div align="left">
                                    <bean:write name="finding" property="tissueDisplay"/>
                           			<logic:equal name="finding" property="tissueDisplay" value = "Other tissue">
										( <bean:write name="finding" property="tissueOther"/> )
									</logic:equal> 
                                    </div>
                                  </td>
                                </tr>
                                <tr class="white"> 
                                  <td class="grey" width="40%"> 
                                    <div align="right"><b>Note</b></div>
                                  </td>
                                  <td> 
                                    <div align="left"><bean:write name="finding" property="note"/></div>
                                  </td>
                                </tr>
                              </table>
                            </td>
                          </tr> 
                        </table>
                      </div>
                      <br>
                    </logic:iterate>
                  </logic:present>
                  <logic:notPresent name="section" property="findings">
                    <div align="center">
                      <a name="report<bean:write name="cnt"/>af"></a>        
                      <table class="background" cellpadding="0" cellspacing="0" border="0" width="100%">
                        <tr> 
                          <td> 
                            <table class="foreground" cellpadding="3" cellspacing="1" border="0" width="100%">
                              <tr class="green">
                                <td> 
                                  <div align="center"><b>Additional Pathology Detail</b></div>
                                </td>
                              </tr>
                              <tr class="white">
                                <td> 
                                  <div align="center"><b>&nbsp;</b></div>
                                </td>
                              </tr>
                            </table>
                          </td>
                        </tr>
                      </table>
                    </div>  
                  </logic:notPresent>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <br>
    </logic:iterate>
  </logic:present>
  
  <logic:present name="pathReport" property="diagnostics">
    <logic:iterate name="pathReport" property="diagnostics" id="diagnostic" indexId="cnt">
      <a name="dtest<bean:write name="cnt"/>"></a>
      <table class="background" cellpadding="0" cellspacing="0" border="0" width="100%">
        <tr> 
          <td> 
            <table class="foreground" cellpadding="3" cellspacing="1" border="0" width="100%">
              <tr class="green"> 
                <td colspan="2"> 
                  <div align="center"><b>Diagnostic Test Detail</b></div>
                </td>
              </tr>
              <tr class="white"> 
                <td class="grey" width="20%">
                  <b>Test Type</b>
                </td>
                <td>
                  <bean:write name="diagnostic" property="typeDisplay"/>
                </td>
              </tr>
              <tr class="white"> 
                <td class="grey" width="20%">
                  <b>Test Name</b>
                </td>
                <td>
                  <bean:write name="diagnostic" property="diagnosticDisplay"/>
                </td>
              </tr>
              <tr class="white"> 
                <td class="grey" width="20%">
                  <b>Result</b>
                </td>
                <td>
                  <bean:write name="diagnostic" property="resultDisplay"/>
                </td>
              </tr>
              <tr class="white"> 
                <td class="grey" width="20%">
                  <b>Diagnostic Note</b>
                </td>
                <td>
                  <bean:write name="diagnostic" property="note"/>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <br>
    </logic:iterate>
  </logic:present>
  <p align="center">
      <form method="post" action="<html:rewrite page="/library/Dispatch"/>">
      <input type="hidden" name="donor_id" value="<bean:write name="donor" property="ardaisId"/>">
      <input type="hidden" name="case_id" value="<bean:write name="pathBean" property="consentId"/>">
      <input type="hidden" name="op" value="PathInfoStart">
      <input type="submit" name="Submit2" value="Donor Profile">
    </form>
</p>
  
  </body>
  <script>
    window.focus();
  </script>
</html>

