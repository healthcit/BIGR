<%@ page import="java.util.*" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.security.SecurityInfo"%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>

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
      <td colspan="8">
        <div align="center">
          <input type="button" class="noprint" value="Print" onClick="window.print()">
          &nbsp;&nbsp;&nbsp;
          <input type="button" class="noprint" value="Close" onClick="window.close()">
		</div>
      </td>
    </tr>
    <tr id="none" class="green"> 
      <td colspan="8">
        <div align="center">
          <b>
            <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.title.comment"/>');">
              Pathology Verification Report
            </span>
          </b>
        </div>
      </td>
    </tr>
    <tr id="none" class="green"> 
      <td colspan="8">
        <div align="center">
          <b>This sample has been pulled for the following reason:&nbsp;<bean:write name="sample" property="pullOrDiscordantReason"/></b>
        </div>
      </td>
    </tr>
	<tr id="none" class="white"> 
	  <td class="yellow" colspan="4">
	    <b>
	      <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.caseid.comment"/>');">
	        Case ID
	      </span>
	    </b>
	  </td>
	  <td class="white">
		<bigr:isInRole role1='<%=com.ardais.bigr.security.SecurityInfo.ROLE_SYSTEM_OWNER%>'>
		  <bigr:icpLink popup="true">
		    <bean:write name="sampleData" property="consent.consentId"/>
		  </bigr:icpLink>
		</bigr:isInRole>
		<bigr:notIsInRole role1='<%=com.ardais.bigr.security.SecurityInfo.ROLE_SYSTEM_OWNER%>'>
		  <bean:write name="sampleData" property="consent.consentId"/>
		</bigr:notIsInRole>
	  </td>
	  <bigr:isInRole role1='<%=com.ardais.bigr.security.SecurityInfo.ROLE_SYSTEM_OWNER%>'>
	    <td class="yellow" colspan="2">
	      <b>
	        <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.sampleid.comment"/>');">
	          Sample
	        </span>
	      </b>
		</td>
		<td class="white">
		  <bigr:icpLink popup="true">
			<bean:write name="sampleData" property="sampleId"/>
		  </bigr:icpLink>
		</td>
	  </bigr:isInRole>
	</tr>
	<tr id="none">
	  <td class="yellow" colspan="4">
	    <b>
	      <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.asm.comment"/>');">
	        ASM
	      </span>
	    </b>
	  </td>
	  <td colspan="4" class="white">
	    <bean:write name="sampleData" property="sample.asmPosition"/>
	  </td>
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
	  <td colspan="4">
	    <b>
	      <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.primarydiag.comment"/>');">
	        Primary Diagnosis from Donor Institution Pathology Report
	      </span>
	    </b>
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
	<tr id="sampleInfo" class="green">
	  <td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;</td>
	  <td colspan="7">
	    <span onClick="changeImages();">
	      <img id="imagesImg" alt="close menu" src="<html:rewrite page='/images/MenuOpened.gif'/>">
	      <b>Sample Images</b> 	
	    </span>
	  </td>
	</tr>
	<%  
	  boolean seenA4x = false;
	  boolean seenA20x = false;
	%>
	<logic:present name="sampleData" property="slides">
	  <logic:iterate name="sampleData" property="slides" id="slides" type="com.ardais.bigr.lims.javabeans.SlideData">
	    <logic:present name="slides" property="images">
		  <tr id="images" class="yellow">
		    <td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td colspan="4"><B>Representative 4x Image</B></td>
			<td colspan="2" ><B>Representative 20x Image</B></td>
		  </tr>
		  <tr id="images" class="white">
		    <td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    	<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    </logic:present>
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
		<logic:present name="slides" property="images">
		  </tr>
		  <tr id="images" class="yellow">
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
	<tr id="none" class="green"> 
   	  <td colspan="8">
   	    <div align="center">
          <input type="button" class="noprint" value="Print" onClick="window.print()">
          &nbsp;&nbsp;&nbsp;
          <input type="button" class="noprint" value="Close" onClick="window.close()">
		</div>
      </td>
    </tr>
  </table>
   
</div>
</body>
</html>
