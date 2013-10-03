<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.iltds.databeans.SampleImageBean,
                 com.ardais.bigr.es.helpers.FormLogic,
                 com.ardais.bigr.security.SecurityInfo,
                 com.ardais.bigr.util.WebUtils"%>

<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>

<%@ taglib uri="/tld/struts-bean"   prefix="bean" %>
<%@ taglib uri="/tld/struts-html"   prefix="html"%>
<%@ taglib uri="/tld/struts-logic"  prefix="logic"%>
<%@ taglib uri="/tld/bigr"          prefix="bigr" %>

<bean:define id="ssBean" name="ssHelper" type="com.ardais.bigr.library.web.helper.SampleSelectionHelper"/>

<bean:define id="rnaBean" name="ssBean" property="rnaDataObject" type="com.ardais.bigr.javabeans.RnaData"/>

<%
  String viewLinkAllUrl = "/library/viewRnaPvInfo.do?rnaVialId=" + ssBean.getRnaVialId();
  String view4xUrl = viewLinkAllUrl + "&singleImageType=" + FormLogic.RNA_IMAGE_PATH4X_TYPE;
  String view20xUrl = viewLinkAllUrl + "&singleImageType=" + FormLogic.RNA_IMAGE_PATH20X_TYPE;
  String viewBioUrl = viewLinkAllUrl + "&singleImageType=" + FormLogic.RNA_IMAGE_BIO_TYPE;
  String viewGelUrl = viewLinkAllUrl + "&singleImageType=" + FormLogic.RNA_IMAGE_GEL_TYPE;
  String viewRtPcrUrl = viewLinkAllUrl + "&singleImageType=" + FormLogic.RNA_IMAGE_RTPCR_TYPE;

	SecurityInfo securityInfo = 
		WebUtils.getSecurityInfo((HttpServletRequest)pageContext.getRequest());
	boolean showRnaNotes = (securityInfo.isInRole(SecurityInfo.ROLE_SYSTEM_OWNER)
			|| (securityInfo.isInRole(SecurityInfo.ROLE_DI) && (ssBean.isBms())));
	
%>
<html>
  <head>
    <title> 
        RNA Pathology Verification Report
    </title>

	<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
	<!--  to view outside server environment:  link rel="stylesheet" type="text/css" href="../../../css/bigr.css"/"-->

  <script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
	<script type="text/javascript" src='<html:rewrite page="/js/overlib.js"/>'><!-- overLIB (c) Erik Bosrup --></script>

<script type="text/javascript">	
  function openBrWindow(theURL,winName,features) { //v2.0
    window.open(theURL,winName,features);
  }

  function doViewPathImagePopup(rnaVialId, imageTypeCode, height) {
    var url = '/BIGR/library/viewRnaPvInfo.do?rnaVialId=' + rnaVialId + '&singleImageType=' + imageTypeCode;
    var imageId = rnaVialId + imageTypeCode;
    var w = window.open(
  		url,
  		imageId,
    	"scrollbars=no, resizable=yes,width=750,height=" + height);
	w.focus();
  }
  
var caseInfoOpen = true;
var sampleInfoOpen = true;

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

function changeSampleInfo(){
	var displayValue;
	if(sampleInfoOpen){
		sampleInfoOpen = false;
		document.all.sampleInfoImg.src = "<html:rewrite page='/images/MenuClosed.gif'/>";
		displayValue = 'none';
	} else {
		sampleInfoOpen = true;
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

function initPage() {
  window.focus();
}

</script>
	
</head>
<body class="bigr" onload="initPage();">
  <bigr:overlibDiv/>
  <p><a name="top"></a> 
    <div align="center"> 
	  <input type="button" class="noprint" value="Print" onClick="window.print()">
	  &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" name="Close2" value="Close" onClick="window.close();">
      </p>



	  <logic:present parameter="singleImageType"> <!-- view just one image -->
	  	<script> document.title = 'View RNA Pathology Verification Image'; </script>
    	<table id="pvImageTable" border="0" cellspacing="1" cellpadding="3" class="fgTableSmall"> 
			  <%
			    String imageType = request.getParameter("singleImageType");
			    String imageLink = rnaBean.getImageUrl(imageType); 
			  %>
	          <tr> 
	          	<td class="yellow"> Case </td>
	          	<td class="white"> <b> <bean:write name="ssBean" property="consentId"/> </b> </td>
	          	<td class="yellow"> Prep Number </td>
	          	<td class="white"> <b> <bean:write name="ssBean" property="rnaPrep"/> </b> </td>
	          	<td class="yellow"> Image </td>
	          	<td align="center" class="white"> 
			    	<b>
				  	<% if (FormLogic.RNA_IMAGE_BIO_TYPE.equals(imageType)) { %>
			          		Bioanalyzer Ratio (28S/18S) : <bean:write name="ssBean" property="rnaRatio"/>
 				    <%} else if (FormLogic.RNA_IMAGE_PATH4X_TYPE.equals(imageType)) {%>
				          	Pathology Image (4x) 
				    <%} else if (FormLogic.RNA_IMAGE_GEL_TYPE.equals(imageType)) {%>
				          	Gel Electrophoresis 
				    <%} else if (FormLogic.RNA_IMAGE_RTPCR_TYPE.equals(imageType)) {%>
				          	RT-PCR
				    <%} else if (FormLogic.RNA_IMAGE_PATH20X_TYPE.equals(imageType)) {%>
				    		Pathology Image (20x)
					<%}%>			    	
		        	</b> 
		        </td> 
		      </tr>
			  
			  <tr> <td colspan="6" align="center">
			    <img border="0" src="<%=imageLink%>"/> 
			  </td></tr>			      
		</table>
	  </logic:present>




    <logic:notPresent parameter="singleImageType"> <!-- view full report, not just image popup -->

    <table id="pvReportTable" border="0" cellspacing="1" cellpadding="3" class="fgTableSmall"> 
    	
    		<tr align="center"> 
			    <td colspan="7" class="green">
    				<b><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.title.comment"/>');">RNA Pathology Verification Report</span></b>
				</td>
			</tr>    
	        <tr> 
				<td/>
				<td colspan="3" class="yellow"> <b> <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.caseid.comment"/>');">Case ID</span></b> </td> 
				<td colspan="1">
					<bigr:isInRole role1='<%=com.ardais.bigr.security.SecurityInfo.ROLE_SYSTEM_OWNER%>'>
						<bigr:icpLink popup="true">
							<bean:write name="ssBean" property="consentId"/>
						</bigr:icpLink>
					</bigr:isInRole>
					<bigr:notIsInRole role1='<%=com.ardais.bigr.security.SecurityInfo.ROLE_SYSTEM_OWNER%>'>
						<bean:write name="ssBean" property="consentId"/>
					</bigr:notIsInRole>
				</td>
				<td class="yellow"><b><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.prep.comment"/>');">Prep Number</span></b> </td>
				<td> <bean:write name="ssBean" property="rnaPrep"/></td>
			</tr>
			<% if (rnaBean.isBms()) { %>
			<tr>
				<td/>
				<td colspan="3" class="yellow"> <b> RNA Vial ID </b> </td>
				<td colspan="1"> <bean:write name="ssBean" property="rnaVialId"/> </td>
			</tr>
			<% }
			   else  {%>
			<bigr:isInRole role1="<%=SecurityInfo.ROLE_SYSTEM_OWNER%>">
			<tr>
				<td/>
				<td colspan="3" class="yellow"> <b> RNA Vial ID </b> </td>
				<td colspan="1"> <bean:write name="ssBean" property="rnaVialId"/> </td>
			</tr>
			</bigr:isInRole>
			<% } %>   
			  

	        <tr> 
	        <td colspan="7" class="darkgreen" style="color: #FFFFCC">
	    		<span onClick="changeCaseInfo()">
	    			<img id="caseInfoImg" alt="close menu" src="<html:rewrite page='/images/MenuOpened.gif'/>"/>
					<b> Case Information </b>
				</span>
	       	  </td>
	       	</tr>
  			<tr id="caseInfo">
				<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;</td>
				<td colspan="3" class="yellow">  
					<b> <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.primarydiag.comment"/>');">Primary Diagnosis from Donor Institution Pathology Report </span></b>
				</td>
				<td colspan="3">
					<bean:write name="ssBean" property="ddcDiagnosis"/>
				</td>
			</tr>
	       	
	        <tr> <td colspan="7" class="darkgreen" style="color: #FFFFCC">
	    		<span onClick="changeSampleInfo()">
	    		<img id="sampleInfoImg" alt="close menu" src="<html:rewrite page='/images/MenuOpened.gif'/>">
	    				<b>Sample Information</b> 
	    		</span>	
	          </td>
	        </tr>
	        <tr id="sampleInfo"> 
				<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;</td>
	        	<td colspan="6" class="green">
	            	<b> Diagnosis and Tissue </b>
	          </td>
	        </tr>

			<tr id="sampleInfo">
				<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td colspan="2" class="yellow"> <b> <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.samplepath.comment"/>');">Sample Pathology from Ardais Pathology Verification </span></b> </td>
				<td colspan="3">
					<bean:write name ="ssBean" property="limsDiagnosis"/>
				</td>
			</tr>
			<tr id="sampleInfo">
				<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td colspan="2" class="yellow"> <b> <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.sampleto.comment"/>');">Tissue of Origin of Diagnosis </span></b> </td>
				<td colspan="3">
					<bean:write name ="ssBean" property="tissueOfOrigin"/>
				</td>
			</tr>
			<tr id="sampleInfo">
				<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="white" style="border-top:0; border-left:0; border-right:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td colspan="2" class="yellow"> <b> <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.samplesf.comment"/>');">Site of Finding </span></b> </td>
				<td colspan="3">
					<bean:write name ="ssBean" property="tissueOfFinding"/>
				</td>
			</tr>
			
			
			<tr id="sampleInfo"> 
				<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;</td>
				<td td colspan="6" class="green">
					<b>Sample Composition: Microscopic cell distribution by % </b>
				</td> 
			</tr>
			<tr id="sampleInfo"> 
				<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="yellow"> <b> <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.nrm.comment"/>');">Normal Cells (NRM): </span></b>  </td>
				<td colspan="1">
					<logic:notEqual name="ssBean" property="normal" value="">
					    <bean:write name="ssBean" property="normal"/>%
					</logic:notEqual>
				<td colspan="4" rowspan="6"/>
				</td>
			</tr>
		
			<tr id="sampleInfo"> 
				<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="yellow"> <b> <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.lsn.comment"/>');">Non-Neoplastic Lesional Cells (LSN):</span> </b> </td>
				<td colspan="1">
					<logic:notEqual name="ssBean" property="lesion" value="">
					    <bean:write name="ssBean" property="lesion"/>%
					</logic:notEqual>
				</td>
				</tr>
		
			<tr id="sampleInfo"> 
				<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="yellow"> <b> <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.tmr.comment"/>');">Tumor Cells (TMR):</span> </b> </td>
				  <td colspan="1">
				  <logic:notEqual name="ssBean" property="tumor" value="">
				    <bean:write name="ssBean" property="tumor"/>%
				  </logic:notEqual>
				</td>
			</tr>
	
			<tr id="sampleInfo"> 
				<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="yellow"> <b> <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.tcs.comment"/>');">Tumor Cellular Stroma (TCS):</span> </b> </td>
				  <td colspan="1">
				  <logic:notEqual name="ssBean" property="cellularStroma" value="">
				    <bean:write name="ssBean" property="cellularStroma"/>%
				  </logic:notEqual>
				</td></tr>
		
			<tr id="sampleInfo"> 
				<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="yellow"> <b> <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.tas.comment"/>');">Tumor Hypo-/Acellular Stroma (TAS): </span></b> </td>
				  <td colspan="1">
				  <logic:notEqual name="ssBean" property="acellularStroma" value="">
				    <bean:write name="ssBean" property="acellularStroma"/>%
				  </logic:notEqual>
				</td></tr>
		
			<tr id="sampleInfo"> 
				<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="yellow"> <b> <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.nec.comment"/>');">Necrosis (NEC):</span> </b> </td>
				  <td colspan="1">
				  <logic:notEqual name="ssBean" property="necrosis" value="">
				    <bean:write name="ssBean" property="necrosis"/>%
				  </logic:notEqual>
				</td>
			</tr>
		    
			<tr id="sampleInfo"> 
				<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td align="center" colspan="1" class="yellow"> 
					<b> <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.extended.comment"/>');">Extended Composition/Comments</span> </b> 
				</td> 
				<td align="center" colspan="4">
					<logic:notEqual name="ssBean" property="rnaComments" value="">
						<bean:write name="ssBean" property="rnaComments"/>
					</logic:notEqual>
				</td>
			</tr>
			<tr id="sampleInfo"> <!-- last line before full line needs bottom border, but no top or side -->
<%
			if (showRnaNotes) {
%>
				<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
<%
			}
			else {
%>
				<td class="white" style="border-top:0; border-left:0; border-right:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
<%
			}
%>
				<td class="white" style="border-top:0; border-left:0; border-right:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td align="center" colspan="1" class="yellow"> 
					<b> <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.microappearance.comment"/>');">Microscopic Appearance</span> </b> 
				</td> 
				<td align="center" colspan="4">
					<bean:write name="ssBean" property="appearanceBest"/>
				</td>
			</tr>
<%
			if (showRnaNotes) {
%>
			<tr id="sampleInfo"> 
				<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;</td>
				<td td colspan="6" class="green">
					<b>RNA Notes</b>
				</td> 
			</tr>
			<tr id="sampleInfo"> 
				<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="white" style="border:0;pixelWidth:20;display:block;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td colspan="5" class="white"> 
					<bigr:beanWrite name="ssBean" property="rnaNotes" whitespace="true" filter="true"/>
				</td> 
			</tr>
<%
			}
%>
		</table>
	</logic:notPresent> <!-- end removed section for image-only popup view -->
	
    	<table id="pvImageTable" border="0" cellspacing="1" cellpadding="3" class="fgTableSmall"> 
			
			<!-- --------  IMAGES SECTION ----------->	
			  
			  <logic:notPresent parameter="singleImageType"> <!-- view all available images -->
		          <tr>
		            <td colspan="4" align="center" class="green"> 
		              <b>Images</b>
		            </td>
		          </tr>

     			  <tr>   
     			         <td align="center" class="yellow"> <b> <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.pathimage4x.comment"/>');">Pathology Image (4x)</span> </b> </td> 
     			         <td align="center" class="yellow"> <b> <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="lims.pvreport.pathimage20x.comment"/>');">Pathology Image (20x)</span> </b> </td> 
     			         <td align="center" class="yellow"> <b> Gel Electrophoresis </b> </td>  
     			         <td align="center" class="yellow"> <b> RT-PCR </b> </td> </tr>
		          <tr> 
		            <td align="center"> 
		              <logic:present name="rnaBean" property="path4xFileName">
		                <a href="#" onClick="doViewPathImagePopup('<bean:write name="rnaBean" property="rnaVialId" filter="false"/>', '<%=FormLogic.RNA_IMAGE_PATH4X_TYPE%>', '650');">
					      <img border="0" height="150" width="150" src="<bean:write name="rnaBean" property="path4xUrl" filter="false"/>"/> 
		                </a>
		              </logic:present>
		              <logic:notPresent name="rnaBean" property="path4xFileName"> No Image Available </logic:notPresent>
		            </td>
		            <td align="center">
		              <logic:present name="rnaBean" property="path20xFileName">
		                <a href="#" onClick="doViewPathImagePopup('<bean:write name="rnaBean" property="rnaVialId" filter="false"/>', '<%=FormLogic.RNA_IMAGE_PATH20X_TYPE%>', '650');">
					      <img border="0" height="150" width="150" src="<bean:write name="rnaBean" property="path20xUrl" filter="false"/>"/> 
					    </a>
		              </logic:present>
		              <logic:notPresent name="rnaBean" property="path20xFileName"> No Image Available </logic:notPresent>
		            </td>
		            <td align="center">
		              <logic:present name="rnaBean" property="gelFileName">
		                <a href="#" onClick="doViewPathImagePopup('<bean:write name="rnaBean" property="rnaVialId" filter="false"/>', '<%=FormLogic.RNA_IMAGE_GEL_TYPE%>', '400');">
					      <img border="0" src="<bean:write name="rnaBean" property="gelUrl" filter="false"/>"/>
					    </a>
		              </logic:present>
		              <logic:notPresent name="rnaBean" property="gelFileName"> No Image Available </logic:notPresent>
		            </td>
		            <td align="center">
		              <logic:present name="rnaBean" property="rtpcrFileName">
		                <a href="#" onClick="doViewPathImagePopup('<bean:write name="rnaBean" property="rnaVialId" filter="false"/>', '<%=FormLogic.RNA_IMAGE_RTPCR_TYPE%>', '300');">
					      <img border="0" src="<bean:write name="rnaBean" property="rtpcrUrl" filter="false"/>"/>
					    </a>
		              </logic:present>
		              <logic:notPresent name="rnaBean" property="rtpcrFileName"> No Image Available </logic:notPresent>
		            </td>
		          </tr>
		          <tr> <td colspan="4" align="center" class="yellow"> 
		          		<b> Bioanalyzer Ratio (28S/18S) : <bean:write name="ssBean" property="rnaRatio"/></b> </td> </tr>
		          <tr>
		            <td colspan="4" align="center"> 
		              <logic:present name="rnaBean" property="bioFileName">
		                <a href="#" onClick="doViewPathImagePopup('<bean:write name="rnaBean" property="rnaVialId" filter="false"/>', '<%=FormLogic.RNA_IMAGE_BIO_TYPE%>', '500');">
					      <img border="0" src="<bean:write name="rnaBean" property="bioUrl" filter="false"/>"/>
					    </a>
		              </logic:present>
		              <logic:notPresent name="rnaBean" property="bioFileName"> No Image Available </logic:notPresent>
		            </td>
		          </tr>	        
		          
   			  </logic:notPresent> <!-- single image parameter -->
		</table>
	  <br> 
	
      <p/> 
		<div align="center"> 
			<input type="button" class="noprint" value="Print" onClick="window.print()">
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="Close2" value="Close" onClick="window.close();"> 
		</div>
    </div>
</body>
</html>

