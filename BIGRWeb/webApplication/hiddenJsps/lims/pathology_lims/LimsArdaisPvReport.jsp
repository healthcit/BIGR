<%@ page import="java.util.*" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.security.SecurityInfo"%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>

<logic:notPresent name="evaluation">
	<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<title>PV Report</title>
	<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
	</head>
	<body>
  <bigr:overlibDiv/>
	<div align="center" width="100%">
    <input type="button" class="noprint" value="Close" onClick="window.close()">
    <p>
    <logic:present name="noDataReason">
    	<bean:write name="noDataReason"/>
   	</logic:present>
   	<logic:notPresent name="noDataReason">
	    No pathology evaluation was available.
	  </logic:notPresent>
    </p>
  </body>
</logic:notPresent>
<logic:present name="evaluation">
<bean:define name="evaluation" id="evaluationData" type="com.ardais.bigr.lims.javabeans.PathologyEvaluationData"/>
<bean:define name="sample" id="sampleData" type="com.ardais.bigr.lims.javabeans.PathologyEvaluationSampleData"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>PV Report <bean:write name="sample" property="sampleId"/> </title>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
	
<script type="text/javascript"
	src='<html:rewrite page="/js/common.js"/>'></script>
<script type="text/javascript"
	src='<html:rewrite page="/js/overlib.js"/>'><!-- overLIB (c) Erik Bosrup --></script>

<script type="text/javascript">

function openImage(action, filename, mag, slideId) {

	var URL = action + "?imageFilename=" + filename + "&magnification=" + mag + "&slideId=" + slideId;

	var imageId = filename.replace(/[^a-zA-Z0-9]+/g,'');

	var w = window.open(URL, imageId, "scrollbars=no, resizable=yes,width=700,height=650")
	w.focus();
}

var caseInfoOpen = true;
var sampleInfoOpen = true;
var dAndTOpen = true;
var sampleCompOpen = true;
var imagesOpen = true;

function changeCaseInfo(){
	var displayValue;
	if(caseInfoOpen){
		caseInfoOpen = false;
		document.all.caseInfoImg.src = "<html:rewrite page='/images/MenuClosed.gif'/>";
		displayValue = 'none';
	} else {
		caseInfoOpen = true;
		document.all.caseInfoImg.src = "<html:rewrite page='/images/MenuOpened.gif'/>";
		displayValue = 'inline';
	}
	
	var tableArray = document.all.pvReportTable.rows;
	for(i = 0 ; i < tableArray.length ; i++){
		var submittedRow = tableArray[i];	
		if(submittedRow.id == 'caseInfo'){
			submittedRow.style.display = displayValue;
		}
	}	 	
}

function changeDAndT(){
	var displayValue;
	if(dAndTOpen){
		dAndTOpen = false;
		document.all.dAndTImg.src = "<html:rewrite page='/images/MenuClosed.gif'/>";
		displayValue = 'none';
	} else {
		dAndTOpen = true;
		document.all.dAndTImg.src = "<html:rewrite page='/images/MenuOpened.gif'/>";
		displayValue = 'inline';
	}
	
	var tableArray = document.all.pvReportTable.rows;
	for(i = 0 ; i < tableArray.length ; i++){
		var submittedRow = tableArray[i];	
		if(submittedRow.id == 'dAndT'){
			submittedRow.style.display = displayValue;
		}
	}	 	
}

function changeSampleComp(){
	var displayValue;
	if(sampleCompOpen){
		sampleCompOpen = false;
		document.all.sampleCompImg.src = "<html:rewrite page='/images/MenuClosed.gif'/>";
		displayValue = 'none';
	} else {
		sampleCompOpen = true;
		document.all.sampleCompImg.src = "<html:rewrite page='/images/MenuOpened.gif'/>";
		displayValue = 'inline';
	}
	
	var tableArray = document.all.pvReportTable.rows;
	for(i = 0 ; i < tableArray.length ; i++){
		var submittedRow = tableArray[i];	
		if(submittedRow.id == 'sampleComp' || submittedRow.id == 'extended' || submittedRow.id == 'internal' || submittedRow.id == 'micro'){
			submittedRow.style.display = displayValue;
		}
	}	 	
}

function changeImages(){
	var displayValue;
	if(imagesOpen){
		imagesOpen = false;
		document.all.imagesImg.src = "<html:rewrite page='/images/MenuClosed.gif'/>";
		displayValue = 'none';
	} else {
		imagesOpen = true;
		document.all.imagesImg.src = "<html:rewrite page='/images/MenuOpened.gif'/>";
		displayValue = 'inline';
	}
	
	var tableArray = document.all.pvReportTable.rows;
	for(i = 0 ; i < tableArray.length ; i++){
		var submittedRow = tableArray[i];	
		if(submittedRow.id == 'images'){
			submittedRow.style.display = displayValue;
		}
	}	 	
}
</script>
</head>
<body onLoad="document.all.pvReportTable.style.display='inline'">
<bigr:overlibDiv/>
<div align="center" width="100%"> 
  
   <table id="pvReportTable" style="display:none;" border="0" cellspacing="0" cellpadding="3" class="fgTableSmall" width="100%">
		<tr id="none" class="green"> 
        	<td  colspan="8"><div align="center">
                <input type="button" class="noprint" value="Print" onClick="window.print()">&nbsp;&nbsp;&nbsp;<input type="button" class="noprint" value="Close" onClick="window.close()">
				</div>
            </td>
        </tr>
        <tr id="none" class="green"> 
              <td  colspan="8">
                <div align="center"><b><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.title.comment"/>');"> Pathology Verification Report</span></b></div>
              </td>
            </tr>
	    <tr id="none" class="white"> 
		   	<td  class="yellow" colspan="4"><b><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.caseid.comment"/>');">Case ID</span></b>
			</td>
			<td  class="white">
			  <logic:present name="sampleData" property="consent">
				  <bigr:isInRole role1='<%=com.ardais.bigr.security.SecurityInfo.ROLE_SYSTEM_OWNER%>'>
					  <bigr:icpLink popup="true">
						  <bean:write name="sampleData" property="consent.consentId"/>
					  </bigr:icpLink>
				  </bigr:isInRole>
				  <bigr:notIsInRole role1='<%=com.ardais.bigr.security.SecurityInfo.ROLE_SYSTEM_OWNER%>'>
					  <bean:write name="sampleData" property="consent.consentId"/>
				  </bigr:notIsInRole>
				</logic:present>
				<logic:notPresent name="sampleData" property="consent">
				  &nbsp;
				</logic:notPresent>
			</td>
			<bigr:isInRole role1='<%=com.ardais.bigr.security.SecurityInfo.ROLE_SYSTEM_OWNER%>'>
				<td  class="yellow"  colspan="2"><b><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.sampleid.comment"/>');">Sample</span></b>
				</td>
				<td  class="white">
					<bigr:icpLink popup="true">
						<bean:write name="sampleData" property="sampleId"/>
					</bigr:icpLink>
                </td>
			</bigr:isInRole>
		</tr>
		<tr id="none">
			
			<td  class="yellow" colspan="4"><b><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.asm.comment"/>');">ASM</span></b>
			</td>
			<td  class="white"><bean:write name="sampleData" property="sample.asmPosition"/>
			</td>
			<bigr:isInRole role1='<%=com.ardais.bigr.security.SecurityInfo.ROLE_SYSTEM_OWNER%>'>
				<td  class="yellow" colspan="2">
			    <b><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.evaluationid.comment"/>');">Evaluation ID</span></b>
				</td>
				<td  class="white">
					<logic:present name="evaluationData">
						<logic:present name="evaluationData" property="evaluationId">
							<bean:write name="evaluationData" property="evaluationId"/>
						</logic:present>
					</logic:present>
				</td>
			</bigr:isInRole>
		</tr>
	    <tr id="none" class="darkgreen" style="color: #FFFFCC">
	    	<td colspan="8">
	    		<span onClick="changeCaseInfo()">
	    		<img id="caseInfoImg" alt="close menu" src="<html:rewrite page='/images/MenuOpened.gif'/>">
	    				<b>Case Information</b> 	
	    		</span>
	    	</td>
	    </tr>
		<tr id="caseInfo" class="yellow"> 
			<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;</td>
			<td  colspan="4">
		   		<b><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.primarydiag.comment"/>');">Primary Diagnosis from Donor Institution Pathology Report</span></b>
		   	</td>
			<td class="white"  colspan="3">
				<logic:present name="sampleData" property="primarySection">
					<bean:write name="sampleData" property="primarySection.diagnosisName"/>
				</logic:present>
			</td>
		</tr>
		
		
		<tr id="none" class="darkgreen" style="color: #FFFFCC">
	    	<td colspan="8">
	    		<span onClick="changeSampleInfo()">
	    		<img id="sampleInfoImg" alt="close menu" src="<html:rewrite page='/images/MenuOpened.gif'/>">
	    				<b>Sample Information</b> 
	    		</span>	
	    	</td>
	    </tr>
	    
	    <logic:notPresent name="showImageReport" scope="request">
	    <tr id="sampleInfo" class="green">
	    	<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;</td>
	    	<td colspan="7">
	    		<span onClick="changeDAndT();">
	    		<img id="dAndTImg" alt="close menu" src="<html:rewrite page='/images/MenuOpened.gif'/>">
	    				<b>Diagnosis and Tissue</b> 	
	    		</span>
	    	</td>
	    </tr>
	    <tr id="dAndT" class="yellow">
	    	<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    	<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    	<td  colspan="3">
				<b><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.samplepath.comment"/>');">Sample Pathology Verification</span></b>
			</td>
			<td  class="white" colspan="3">
				<logic:present name="evaluationData" property="diagnosisOther">
				  <bean:write name="evaluationData" property="diagnosisOther"/>
				</logic:present>
			
				<logic:notPresent name="evaluationData" property="diagnosisOther">
				  <logic:present name="evaluationData" property="diagnosisName">
				    <bean:write name="evaluationData" property="diagnosisName"/>
				  </logic:present>  
				</logic:notPresent>								
			</td>
	    </tr>
		<tr id="dAndT" class="yellow"> 
			<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    	<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    	<td  colspan="3">
		   		<b><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.sampleto.comment"/>');">Tissue of Origin of Diagnosis</span></b>
			</td>
			<td class="white" colspan="3">
				<logic:present name="evaluationData" property="tissueOfOriginOther">
				  <bean:write name="evaluationData" property="tissueOfOriginOther"/>
				</logic:present>					
					
				<logic:notPresent name="evaluationData" property="tissueOfOriginOther">
				  <logic:present name="evaluationData" property="tissueOfFindingName">
				    <bean:write name="evaluationData" property="tissueOfOriginName"/>
				  </logic:present>
				</logic:notPresent>								
			</td>
		</tr>
	    <tr id="dAndT" class="yellow"> 
	    	<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    	<td class="white" style="border-top:0; border-left:0; border-right:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    	
	    	<td  colspan="3">
	    		<b><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.samplesf.comment"/>');">Site of Finding</span></b>
			</td>
			<td class="white"  colspan="3">
				<logic:present name="evaluationData" property="tissueOfFindingOther">
				  <bean:write name="evaluationData" property="tissueOfFindingOther"/>
				</logic:present>
				
				<logic:notPresent name="evaluationData" property="tissueOfFindingOther">
				  <logic:present name="evaluationData" property="tissueOfFindingName">
				    <bean:write name="evaluationData" property="tissueOfFindingName"/>
				  </logic:present>  
				</logic:notPresent>								
			</td>
		</tr>

		<tr id="sampleInfo" class="green">
			<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;</td>	
			<td colspan="7">
			<span onClick="changeSampleComp();">
				<img id="sampleCompImg" alt="close menu" src="<html:rewrite page='/images/MenuOpened.gif'/>">
	    				<b>Sample Composition: Microscopic cell distribution by %</b> 	
	    		</span>
			</td>
		</tr>
		<tr id="sampleComp" class="yellow"> 
		   	<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    	<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    	<td  colspan="2">
		   		<b><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.nrm.comment"/>');">Normal Cells (NRM)</span></b>
			</td>
			<td class="white" >
				<div align="center"><bean:write name="evaluationData" property="normalCells"/></div>
			</td>
			<td class="white" rowspan="8" style="border-left:0; border-right:0;" id="sampleCompositionDivider">&nbsp;</td>
			<td class="yellow" colspan="2"><B><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.extended.comment"/>');">Extended Composition/Comments</span></B></td>
		</tr>
		<tr id="extended" class="yellow">
			<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    	<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    	<td  colspan="2">
				<b><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.lsn.comment"/>');">Lesion Cells (LSN)</span></b>
			</td>
			<td class="white" >
				<div align="center"><bean:write name="evaluationData" property="lesionCells"/></div>
			</td>
			
		</tr>
		<logic:present name="evaluationData" property="lesions">
		<logic:iterate name="evaluationData" property="lesions" id="lesion" type="com.ardais.bigr.lims.javabeans.PathologyEvaluationFeatureData">
		<tr id="extended" class="white"> 
			<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    	<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    	<td >
				<bigr:beanWrite name="lesion" property="HTMLSafeFeatureName" filter="false" whitespace="false"/>	
			</td>
			<td align="center" >
				<bean:write name="lesion" property="value"/>							
			</td>

		</tr>
		</logic:iterate>
		</logic:present>
		<tr id="extended" class="yellow">
			<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    	<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    	<td  colspan="2">
				<b><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.inflammation.comment"/>');">Inflammation</span></b>
			</td>
		</tr>
		<logic:present name="evaluationData" property="inflammations">
		<logic:iterate name="evaluationData" property="inflammations" id="inflammation" type="com.ardais.bigr.lims.javabeans.PathologyEvaluationFeatureData">
		<tr id="extended" class="white"> 
			<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    	<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    	<td>
				<bigr:beanWrite name="inflammation" property="HTMLSafeFeatureName" filter="false" whitespace="false"/>
			</td>
			<td align="center" >
				<bean:write name="inflammation" property="featureValueName"/>
			</td>
		</tr>
		</logic:iterate>
		</logic:present>
		<tr id="extended" class="yellow">
			<td class="white" colspan="2" style="border:0;pixelWidth:50;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td  colspan="2">
				<b><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.nontumor.comment"/>');"> Normal/Lesion Structures with %</span></b>
			</td>
	    </tr>
		<logic:present name="evaluationData" property="structures">
		<logic:iterate name="evaluationData" property="structures" id="structure" type="com.ardais.bigr.lims.javabeans.PathologyEvaluationFeatureData">
		<tr id="extended" class="white"> 
			<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    	<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    	<td >
				<bigr:beanWrite name="structure" property="HTMLSafeFeatureName" filter="false" whitespace="false"/>
			</td>
			<td>
				<bean:write name="structure" property="value"/>
			</td>
		</tr>
		</logic:iterate>
		</logic:present>
		<tr id="sampleComp" class="yellow">
			<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    	<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    	<td  colspan="2">
				<b><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.tmr.comment"/>');">Tumor Cells (TMR)</span></b>
			</td>
			<td class="white" align="center">
				<bean:write name="evaluationData" property="tumorCells"/>
			</td>
			<bigr:isInRole role1='<%=com.ardais.bigr.security.SecurityInfo.ROLE_SYSTEM_OWNER%>'>
				<td  class="yellow" colspan="2"><B>Internal Quality Issues/Comments (internal only)</B></td>
			</bigr:isInRole>
		</tr>
		<logic:present name="evaluationData" property="tumorFeatures">
		<logic:iterate name="evaluationData" property="tumorFeatures" id="tumor" type="com.ardais.bigr.lims.javabeans.PathologyEvaluationFeatureData">
		<tr id="internal" class="white"> 
			<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    	<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    	<td  colspan="2">
				<bigr:beanWrite name="tumor" property="HTMLSafeFeatureName" filter="false" whitespace="false"/>
			</td>
		</tr>
		</logic:iterate>
		</logic:present>
		<tr id="internal" type="dontAddRow" class="yellow">
			<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    	<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    	<td  colspan="2">
				<b><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.tcs.comment"/>');">Tumor Hypercellular Stroma (TCS)</span></b>
			</td>
			<td class="white" align="center">
				<bean:write name="evaluationData" property="cellularStromaCells"/>
			</td>
			
		</tr>
		<logic:present name="evaluationData" property="cellularStromaFeatures">
		<logic:iterate name="evaluationData" property="cellularStromaFeatures" id="cstroma" type="com.ardais.bigr.lims.javabeans.PathologyEvaluationFeatureData">
		<tr id="internal" class="white" colspan="2"> 
			<td class="white" colspan="2" style="border:0;pixelWidth:50;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td  colspan="2">
				<bigr:beanWrite name="cstroma" property="HTMLSafeFeatureName" filter="false" whitespace="false"/>
			</td>
		</tr>
		</logic:iterate>
		</logic:present>
		<tr id="sampleComp" class="yellow">
			<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    	<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    	<td  colspan="2">
				<b><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.tas.comment"/>');">Tumor Hypo-/Acellular Stroma (TAS)</span></b>
			</td>
			<td class="white" align="center">
				<bean:write name="evaluationData" property="hypoacellularStromaCells"/>
			</td>
			<td class='yellow' colspan='2'>
				<B><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.microappearance.comment"/>');">Microscopic Appearance</span></B>
			</td>
		</tr>
		<logic:present name="evaluationData" property="hypoacellularStromaFeatures">
		<logic:iterate name="evaluationData" property="hypoacellularStromaFeatures" id="hstroma" type="com.ardais.bigr.lims.javabeans.PathologyEvaluationFeatureData">
		<tr id="micro" class="white"> 
			<td class="white" colspan="2" style="border:0;pixelWidth:50;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td  colspan="2">
				<bigr:beanWrite name="hstroma" property="HTMLSafeFeatureName" filter="false" whitespace="false"/>
			</td>
		</tr>
		</logic:iterate>
		</logic:present>
		<tr id="micro" type="dontAddRow" class="yellow"> 
		   	<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    	<td class="white" style="border-top:0; border-left:0; border-right:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    	<td  colspan="2">
		   		<b><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.nec.comment"/>');">Necrosis (NEC)</span></b>
			</td>
			<td class="white"  align="center">
				<bean:write name="evaluationData" property="necrosisCells"/>
			</td>
			
		</tr>

	    <tr id="sampleInfo" class="green">
	    	<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;</td>
	    	<td colspan="7">
	    	<span onClick="changeImages();">
	    		<img id="imagesImg" alt="close menu" src="<html:rewrite page='/images/MenuOpened.gif'/>">
	    			<b>Pathology Images</b> 	
	    	</span>
	    	</td>
	    </tr>
	    <tr id="images" class="yellow">
	    	<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    	<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    	<td colspan="4"><B><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.pathimage4x.comment"/>');">Pathology Image (4x)</span></B></td>
	    	<td colspan="2" ><B><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.pathimage20x.comment"/>');">Pathology Image (20x)</span></B></td>
	    </tr>
		<tr id="images" class="white">
			<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    	<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    	<%  
				boolean seenA4x = false;
				boolean seenA20x = false;
			%>
			<logic:present name="sampleData" property="slides">
	          	<logic:iterate name="sampleData" property="slides" id="slides" type="com.ardais.bigr.lims.javabeans.SlideData">
				<logic:match name="slides" property="slideId" value="<%= evaluationData.getSlideId() %>">	            
				    <logic:iterate name="slides" property="images" id="images" type="com.ardais.bigr.lims.javabeans.ImageData"> 
						<%
						String mag = images.getMagnification(); 
						if(mag.equalsIgnoreCase("4X") && !seenA4x){
							seenA4x = true;
						%>
						<td colspan="3" align="center">
							<logic:present name="images" property="imageFilename">
          						 <a href="#" onClick="openImage('<html:rewrite page="/lims/limsViewSlideImage.do"/>','<bean:write name="images" property="imageFilename"/>','<bean:write name="images" property="magnification"/>','<bean:write name="slides" property="slideId"/>');">
          						 	<img border="0" src="<bean:write  name="images" property="thumbNailUrl" filter="false"/>"> 
          						 </a>
          					</logic:present>
						</td>
						<td align="center">
							<bean:write name="images" property="slideId"/><br>
							<bean:write name="slides" property="procedure"/><br>
							<bean:write name="images" property="notes"/>
						</td>
						<%}
						else if(seenA4x && mag.equalsIgnoreCase("20X") && !seenA20x){
							seenA20x = true;
						%>
						<td align="center">
							<logic:present name="images" property="imageFilename">
          						 <a href="#" onClick="openImage('<html:rewrite page="/lims/limsViewSlideImage.do"/>','<bean:write name="images" property="imageFilename"/>','<bean:write name="images" property="magnification"/>','<bean:write name="slides" property="slideId"/>');">
              						<img border="0" src="<bean:write  name="images" property="thumbNailUrl" filter="false"/>"> 
								 </a>
          					</logic:present>
						</td>
						<td align="center">
							<bean:write name="images" property="slideId"/><br>
							<bean:write name="slides" property="procedure"/><br>
							<bean:write name="images" property="notes"/>
						</td>
						<%}%>
		            </logic:iterate> 
		        </logic:match>
		        </logic:iterate>
	            </logic:present>
		</tr>
		<tr id="images" class="yellow">
			<td class="white" style="border-top:0; border-left:0; border-right:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    	<td class="white" style="border-top:0; border-left:0; border-right:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    	<td colspan="3" align="right">
				<B>All Images</B>
			</td>
			<td class="white" colspan="4">
				<logic:present name="sampleData" property="slides">
	          	<logic:iterate name="sampleData" property="slides" id="slides" type="com.ardais.bigr.lims.javabeans.SlideData">
				<logic:match name="slides" property="slideId" value="<%= evaluationData.getSlideId() %>">	            
				    <logic:iterate name="slides" property="images" id="images" type="com.ardais.bigr.lims.javabeans.ImageData">
				    <a href="#" onClick="openImage('<html:rewrite page="/lims/limsViewSlideImage.do"/>','<bean:write name="images" property="imageFilename"/>','<bean:write name="images" property="magnification"/>','<bean:write name="slides" property="slideId"/>');"><bean:write name="images" property="magnification"/> (<bean:write name="slides" property="procedure"/>)</a>&nbsp;&nbsp;&nbsp;
		            </logic:iterate> 
		        </logic:match>
		        </logic:iterate>
	            </logic:present> 

			</td>
		</tr>
		</logic:notPresent>
		<logic:present name="showImageReport">
		
		<%  
				boolean seenA4x = false;
				boolean seenA20x = false;
			%>
			<logic:present name="sampleData" property="slides">
	          	<logic:iterate name="sampleData" property="slides" id="slides" type="com.ardais.bigr.lims.javabeans.SlideData">
				    <logic:present name="slides" property="images">
				    <tr id="sampleInfo" class="yellow">
				    	<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				    	<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				    	<td colspan="4"><B>Representative 4x Image</B></td>
				    	<td colspan="2" ><B>Representative 20x Image</B></td>
				    </tr>
				    </logic:present>
				    <tr id="sampleInfo" class="white">
					    <td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    				<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				    <logic:iterate name="slides" property="images" id="images" type="com.ardais.bigr.lims.javabeans.ImageData"> 
						<%
						String mag = images.getMagnification();
						if(mag.equalsIgnoreCase("4X") && !seenA4x){
							seenA4x = true;
						%>
						<td colspan="3" align="center">
							<logic:present name="images" property="imageFilename">
								 <a href="#" onClick="openImage('<html:rewrite page="/lims/limsViewSlideImage.do"/>','<bean:write name="images" property="imageFilename"/>','<bean:write name="images" property="magnification"/>','<bean:write name="slides" property="slideId"/>');">
          						 	<img border="0" src="<bean:write  name="images" property="thumbNailUrl" filter="false"/>"> 
          						 </a>
          					</logic:present>
						</td>
						<td align="center">
							<bean:write name="images" property="slideId"/><br>
							<bean:write name="slides" property="procedure"/><br>
							<bean:write name="images" property="notes"/>
						</td>
						<%}
						else if(seenA4x && mag.equalsIgnoreCase("20X") && !seenA20x){
							seenA20x = true;
						%>
						<td align="center">
							<logic:present name="images" property="imageFilename">
								 <a href="#" onClick="openImage('<html:rewrite page="/lims/limsViewSlideImage.do"/>','<bean:write name="images" property="imageFilename"/>','<bean:write name="images" property="magnification"/>','<bean:write name="slides" property="slideId"/>');">
              						<img border="0" src="<bean:write  name="images" property="thumbNailUrl" filter="false"/>"> 
								 </a>
          					</logic:present>
						</td>
						<td align="center">
							<bean:write name="images" property="slideId"/><br>
							<bean:write name="slides" property="procedure"/><br>
							<bean:write name="images" property="notes"/>
						</td>
						<%}%>
		            </logic:iterate> 
		        	</tr>
		        	<logic:present name="slides" property="images">
		        	<tr id="sampleInfo" class="yellow">
						<td class="white" style="border-top:0; border-left:0; border-right:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				    	<td class="white" style="border-top:0; border-left:0; border-right:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				    	<td colspan="3">
							<B>All Images</B>
						</td>
						<td class="white" colspan="4">
						  	<logic:iterate name="sampleData" property="slides" id="slides1" type="com.ardais.bigr.lims.javabeans.SlideData">
							<logic:match name="slides1" property="slideId" value="<%= slides.getSlideId() %>">	            
							    <logic:iterate name="slides" property="images" id="images1" type="com.ardais.bigr.lims.javabeans.ImageData">
							    <a href="#" onClick="openImage('<html:rewrite page="/lims/limsViewSlideImage.do"/>','<bean:write name="images1" property="imageFilename"/>','<bean:write name="images1" property="magnification"/>','<bean:write name="slides1" property="slideId"/>');"><bean:write name="images1" property="magnification"/> (<bean:write name="slides1" property="procedure"/>)</a>&nbsp;&nbsp;&nbsp;
					            </logic:iterate> 
					        </logic:match>
					        </logic:iterate>
	        			</td>
					</tr>
		        	</logic:present>
		        </logic:iterate>
	            </logic:present>
		
</logic:present>
		<tr id="none" class="green"> 
        	<td  colspan="8"><div align="center">
                <input type="button" class="noprint" value="Print" onClick="window.print()">&nbsp;&nbsp;&nbsp;<input type="button" class="noprint" value="Close" onClick="window.close()">
				</div>
            </td>
        </tr>
        
        
      </table>
   
<script>

<logic:notPresent name="showImageReport">
function changeSampleInfo(){
	var displayValue;
	if(sampleInfoOpen){
		sampleInfoOpen = false;
		dAndTOpen = false;
		sampleCompOpen = false;
		imagesOpen = false;
		document.all.sampleInfoImg.src = "<html:rewrite page='/images/MenuClosed.gif'/>";
		displayValue = 'none';
	} else {
		sampleInfoOpen = true;
		dAndTOpen = true;
		sampleCompOpen = true;
		imagesOpen = true;
		document.all.sampleInfoImg.src = "<html:rewrite page='/images/MenuOpened.gif'/>";
		document.all.dAndTImg.src = "<html:rewrite page='/images/MenuOpened.gif'/>";
		document.all.sampleCompImg.src = "<html:rewrite page='/images/MenuOpened.gif'/>";
		document.all.imagesImg.src = "<html:rewrite page='/images/MenuOpened.gif'/>";
		displayValue = 'inline';
	}
	
	var tableArray = document.all.pvReportTable.rows;
	for(i = 0 ; i < tableArray.length ; i++){
		var submittedRow = tableArray[i];	
		if(submittedRow.id == 'sampleInfo' 
			|| submittedRow.id == 'dAndT'
			|| submittedRow.id == 'images'
			|| submittedRow.id == 'sampleComp' 
			|| submittedRow.id == 'extended' 
			|| submittedRow.id == 'internal' 
			|| submittedRow.id == 'micro'){
			submittedRow.style.display = displayValue;
		}
	}	 
	
	
}
</logic:notPresent>
<logic:present name="showImageReport">
function changeSampleInfo(){
	var displayValue;
	if(sampleInfoOpen){
		sampleInfoOpen = false;
		document.all.sampleInfoImg.src = "<html:rewrite page='/images/MenuClosed.gif'/>";
		displayValue = 'none';
	} else {
		sampleInfoOpen = true;
		imagesOpen = true;
		document.all.sampleInfoImg.src = "<html:rewrite page='/images/MenuOpened.gif'/>";
		displayValue = 'inline';
	}
	
	var tableArray = document.all.pvReportTable.rows;
	for(i = 0 ; i < tableArray.length ; i++){
		var submittedRow = tableArray[i];	
		if(submittedRow.id == 'sampleInfo'){
			submittedRow.style.display = displayValue;
		}
	}	 
	
	
}
</logic:present>
<logic:notPresent name="showImageReport">

var tableRows = document.all.pvReportTable.rows;

var extendedSpan = 0;
var addedExtended = false;
var extendedCell;

var internalSpan = 0;
var addedInternal = false;
var internalSpan;

var microSpan = 0;
var addedMicro = false;
var microSpan;

var dividerSpan = 0;
var temp;

for( i = 0 ; i < tableRows.length ; i++){
	temp = tableRows[i];
	if(temp.id == 'extended'){
		extendedSpan++;
		if(!addedExtended){
			extendedCell = temp.insertCell();
			addedExtended = true;
		}
	} else if(temp.id == 'internal'){
		internalSpan++;
		if(!addedInternal){
			<bigr:isInRole role1='<%=com.ardais.bigr.security.SecurityInfo.ROLE_SYSTEM_OWNER%>'>
			if(temp.type != 'dontAddRow'){
				internalCell = temp.insertCell();
				internalCell.className = "white";
				internalCell.style.border = 0;
			}
			internalCell = temp.insertCell();
			addedInternal = true;
			</bigr:isInRole>
		}
	} else if(temp.id == 'micro'){
		microSpan++;
		if(!addedMicro){
			if(temp.type != 'dontAddRow'){
				microCell = temp.insertCell();
				microCell.style.border = 0;
				microCell.className = "white";	
			}
			microCell = temp.insertCell();
			addedMicro = true;
		}
	}
}
extendedCell.innerHTML = "<td><bigr:beanWrite name="evaluationData" property="formattedConcatenatedExternalData" filter="false" whitespace="true"/></td>";
extendedCell.className = "white";
extendedCell.vAlign = "top";
<bigr:isInRole role1='<%=com.ardais.bigr.security.SecurityInfo.ROLE_SYSTEM_OWNER%>'>
extendedCell.rowSpan = extendedSpan;
</bigr:isInRole>
<bigr:notIsInRole role1='<%=com.ardais.bigr.security.SecurityInfo.ROLE_SYSTEM_OWNER%>'>
extendedCell.rowSpan = extendedSpan + internalSpan + 1;
</bigr:notIsInRole>
extendedCell.colSpan = 2;

<bigr:isInRole role1='<%=com.ardais.bigr.security.SecurityInfo.ROLE_SYSTEM_OWNER%>'>
internalCell.innerHTML = "<td><bigr:beanWrite name="evaluationData" property="HTMLSafeConcatenatedInternalData" filter="false" whitespace="true"/></td>";
internalCell.className = "white";
internalCell.vAlign = "top";
internalCell.rowSpan = internalSpan;
internalCell.colSpan = 2;
</bigr:isInRole>

microCell.innerHTML = "<td><bean:write name="evaluationData" property="microscopicAppearanceName"/></td>";
microCell.className = "white";
microCell.vAlign = "top";
microCell.rowSpan = microSpan;
microCell.colSpan = 2;

document.all.sampleCompositionDivider.rowSpan = ( extendedSpan + internalSpan + microSpan + 3);
</logic:notPresent>
</script>  
</div>
</body>
</html>
</logic:present>
