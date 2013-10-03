<%@ page language="java" import="java.util.*" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.query.*"%>
<%@ page import="com.ardais.bigr.lims.helpers.*"%>
<%@ page import="com.ardais.bigr.util.Constants"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions"%>
<%@ page import="com.ardais.bigr.lims.web.helpers.PathQcHelper"%>
<%@ page import="com.ardais.bigr.web.StrutsProperties"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-bean"  prefix="bean" %>
<%@ taglib uri="/tld/struts-html"  prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic"%>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<%@ taglib uri="/tld/datetime"     prefix="dt"   %>

<%
    Properties prop = StrutsProperties.getInstance();
    String invStatusTooltip = prop.getProperty("library.ss.result.header.invStatus.comment");
    String salesStatusTooltip = prop.getProperty("library.ss.result.header.salesStatus.comment"); 
    String projStatusTooltip = prop.getProperty("library.ss.result.header.projectStatus.comment"); 
%>


<div align="left" >
<input type="hidden" name="viewQCEdit" value="false">
<table width="100%" cellpadding="1" cellspacing="0" border="0" class="background">
    <tr > 
      <td> 
        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small" style="font-size:xx-small">
          <tr  class="green"> 
            
	    <td rowspan="4"><div align="center">
	    	<b>Select</b>
		</div>
	    </td>
	    <td rowspan="4"><div align="center">
	    	<b>QC</b>
		</div>
	    </td>
	    <td rowspan="4"><div align="center">
	    	<b>QC Status</b>
		</div>
	    </td>
	    <td rowspan="2"><div align="center">
	    	<b>Case ID</b>
		</div>
	    </td>
	    <td rowspan="4"><div align="center">
	    	<b>Inventory Groups</b>
		</div>
	    </td>
	    <td rowspan="4"><div align="center">
	    	<b>Reports</b>
		</div>
	    </td>
	    <td rowspan="2"><div align="center">
	    	<b>Case Dx from Path Report</b>
		</div>
	    </td>
	    <td rowspan="2" nowrap><div align="center">
	    	<b>ASM Site of Finding</b>
		</div>
	    </td>
	    <td rowspan="4" colspan="2"><div align="center">
	    	<b>Composition</b>
		</div>
	    </td>
	    <td rowspan="4" nowrap><div align="center">
	    	<b>Extended Composition/Features/Comments<br>(Pull Reason if sample is pulled)</b>
		</div>
	    </td>
	    <td rowspan="4"><div align="center">
	    	<b>Internal QI/Comments</b>
		</div>
	    </td>
	    <td rowspan="4"><div align="center">
	    	<b>Microscopic Appearance</b>
	    	</div>
	    </td>
	    <span onmouseout="return nd();" onmouseover="return overlib('<%=invStatusTooltip%>');">
	      <td rowspan="2">
	        <div align="center">
	          <b>Inventory Status</b>
		    </div>
		  </td>
		</span>
	    <td rowspan="4"><div align="center">
	    	<b>TNM Staging</b>
		</div>
	    </td>
	    <td rowspan="4"><div align="center">
	    	<b>Histo Nuclear Grade</b>
		</div>
	    </td>
	    <td rowspan="2"><div align="center">
	    	<b>Tumor Size</b>
		</div>
	    </td>
	    
	    <td rowspan="2"><div align="center">
	    	<b>Age</b>
		</div>
	    </td>
      </tr>
      <tr></tr>
	  <tr class="green">	  
	  	<td>
	  	  <div align="center"><b>Sample Id</b></div>
	  	</td>	    
	    <td rowspan="2" nowrap><div align="center">
	    	<b>Sample Pathology</b>
		</div>
	    </td>
	    <td rowspan="2" nowrap><div align="center">
	    	<b>Tissue of Origin of Dx /<br>Tissue of Site of Finding</b>
		</div>
	    </td>
	    <span onmouseout="return nd();" onmouseover="return overlib('<%=salesStatusTooltip%>');">
	  	  <td>
	  	    <div align="center"><b>Sales Status</b></div>
	  	  </td>
	  	</span>
		<td rowspan="2"><div align="center">
	    	<b>Weight</b>
		</div>
	    </td>
		<td rowspan="2"><div align="center">
	    	<b>Gender</b>
		</div>
	    </td>
	  </tr>
	  <tr class="green">	
	    <td><div align="center"><b>ASM Position</b></div></td> 	  	
	    <span onmouseout="return nd();" onmouseover="return overlib('<%=projStatusTooltip%>');"><td><div align="center"><b>Project Status</b></div></td></span>
	  </tr>
	  <logic:present name='<%= com.ardais.bigr.query.LimsPathQcQuery.LIMSPATHQCQUERY_KEY%>'>
	  <bean:define name='<%= com.ardais.bigr.query.LimsPathQcQuery.LIMSPATHQCQUERY_KEY%>' property="markedSamples" id="markedList" type="java.util.HashSet"/>
	  
	  <%
	  	Iterator markedIter = markedList.iterator();
	  	
	  	while (markedIter.hasNext()) {
	  		%>
	  		<script>
	  			markedSampleList.push('<%= markedIter.next() %>');
	  		</script>
	  		<%	
	  	}
	  %>
	  <bean:define id="limsPathQcQuery" name='<%= LimsPathQcQuery.LIMSPATHQCQUERY_KEY %>' type="LimsPathQcQuery"/>
	  <%
	  		  boolean showPull;
			  boolean showUnPull;
			  boolean showRelease;
			  boolean showUnRelease;
			  boolean showEditQc;
			  boolean showQCPost;
			  boolean showUnQCPost;
			  boolean showPv;
			  String rowClass = null;
			  String sampleId = null;
			  
	  
	  %>
	  <logic:iterate name="limsPathQcForm" property="pathQcSampleDetails" id="sampleDetail" type="com.ardais.bigr.lims.web.helpers.PathQcHelper">
	  <%
	  showPull = false;
	  showUnPull = false;
	  showRelease = false;
	  showUnRelease = false;
	  showEditQc = false;
	  showQCPost = false;
	  showUnQCPost = false;
	  showPv = false;
	  
	  if(sampleDetail.isUnPved()){
	  	showPull = true;
	  	showPv = true;
	  } 
	  if(sampleDetail.isPved()){
	  	showRelease = true;
	  	showPull = true;
	  	showEditQc = true;
	  } 
	  else if(sampleDetail.isPulled()){
	  	showUnPull = true;
	  	showEditQc = false;
	  } 
	  else if(sampleDetail.isReleased()){
	  	showPull = true;
	  	showUnRelease = true;
	  	showEditQc = true;
	  	showQCPost = true;
	  } else if(sampleDetail.isQCPosted()){
	  	showPull = true;
	  	showUnRelease = true;
	  	showEditQc = true;
	  	showUnQCPost = true;
	  }
	  
	  if (limsPathQcQuery.isModifiedSample(sampleDetail.getSampleId())) {
	      rowClass = "highlight";
	  } else {
	      rowClass = "white";
	  }
	  sampleId = sampleDetail.getSampleId();
	  %>
	  
	    <tr id="<%= sampleId %>" class="<%= rowClass%>">
	  
            
	    <td rowspan="6"><div align="center">

	    	<input type= "checkbox" name="markedSamples" value='<%= sampleId %>' onClick="changeMarkedSample(this)" <%= (markedList.contains(sampleDetail.getSampleId()))?"checked":"" %>>
		</div>
	    </td>
	    <td  rowspan="6" nowrap>
	        <table class="foreground-small" style="font-size:xx-small">
	    	   <% if (showRelease) { %>	    	   
	    	        <tr>
	    	          <td>
	    			    <input type="radio" name="QC<%= sampleId %>" value="<%=LimsConstants.LIMS_TX_RELEASE%>" <%=limsPathQcQuery.getCheckedValueForRadio("QC"+sampleId, LimsConstants.LIMS_TX_RELEASE)%>>
                      </td>  
                      <td nowrap>
                        <a href="#" onClick="invokeFunction('<%= sampleId %>', '<%=LimsConstants.LIMS_TX_RELEASE%>')">
	    			      Path Release
	    			    </a>
	    			    
                      </td>  
                    </tr>                     
	    		<%} 
	    	
	    		if(showUnRelease) {	%>
	    			<tr>
	    	          <td>
	    	            <input type="radio" name="QC<%= sampleId %>" value="<%=LimsConstants.LIMS_TX_UNRELEASE%>" <%=limsPathQcQuery.getCheckedValueForRadio("QC"+sampleId, LimsConstants.LIMS_TX_UNRELEASE)%>>	    			    
                      </td>  
                      <td nowrap>
	    			    <a href="#" onClick="invokeFunction('<%= sampleId %>', '<%=LimsConstants.LIMS_TX_UNRELEASE%>')">
	    			      Path Unrelease
	    			    </a>
                      </td>  
                    </tr>   
	    		<%} 
	    		if (showEditQc) {
	    		%>
	    			<tr>
	    	          <td>	    			                         			    
                      </td> 
                      <td nowrap> 
    			    		<a href="#" onClick="document.forms[0].viewQCEdit.value='true';invokeFunction('<%= sampleId %>', '<%= LimsConstants.LIMS_TX_QCEDITPV %>')">
	    			      QC/Edit PV
	    			    </a>                       
                      </td>
                    </tr>   
	    		<%}
	    		if(showPull){ %>
					<tr>
	    	          <td>
	    			    <input type="hidden" name="<%= "reason" + sampleId %>" value="<%=limsPathQcQuery.getReason("reason" + sampleId)%>">
                      </td>  
                      <td>	    			    
	    			    <a href="#" onClick="invokeFunction('<%= sampleId %>', '<%=LimsConstants.LIMS_TX_PULL%>')">
	    			      Pull
	    			    </a>
                      </td>  
                    </tr>  
				<%} 
				if(showUnPull){ %>
					<tr>
	    	          <td>
	    			    <input type="hidden" name="<%= "reason" + sampleId %>" value="<%=limsPathQcQuery.getReason("reason" + sampleId)%>">
                      </td>  
                      <td>	    			    
	    			    <a href="#" onClick="invokeFunction('<%= sampleId %>', '<%=LimsConstants.LIMS_TX_UNPULL%>')" >
	    			      Unpull
	    			    </a>
                      </td>  
                    </tr>  
				<%}
				if(showQCPost){%>
				    <tr>
	    	          <td>
	    	            <input type="radio" name="QC<%= sampleId %>" value="<%=LimsConstants.LIMS_TX_QCPOST%>" <%=limsPathQcQuery.getCheckedValueForRadio("QC"+sampleId, LimsConstants.LIMS_TX_QCPOST)%>>	    			    
                      </td>  
                      <td>
	    			    <a href="#" onClick="invokeFunction('<%= sampleId %>', '<%=LimsConstants.LIMS_TX_QCPOST%>')">
	    			      QCPost
                        </a>
                      </td>  
                    </tr> 
				<%}
				if(showUnQCPost){%>
				    <tr>
	    	          <td>
	    			    <input type="radio" name="QC<%= sampleId %>" value="<%=LimsConstants.LIMS_TX_UNQCPOST%>" <%=limsPathQcQuery.getCheckedValueForRadio("QC"+sampleId, LimsConstants.LIMS_TX_UNQCPOST)%>>
                      </td>  
                      <td>	    			    
	    			    <a href="#" onClick="invokeFunction('<%= sampleId %>', '<%=LimsConstants.LIMS_TX_UNQCPOST%>')">
	    			      UnQCPost
	    			    </a>
                      </td>  
                    </tr> 
				<%}
				if (showPv) {%>
                    <tr>
                      <td>
                      </td>
	    	          <td nowrap>
	    			    <a href="javascript:void(0)" onClick="window.open('<html:rewrite page="/lims/limsSlideQueryStart.do"/>?id=<%= sampleId %>', null, 'scrollbars=yes, status=yes, resizable=yes,width=1024,height=725,top=0,left=0')">
	    			      PV
	    			    </a>                      
                      </td>  
                    </tr> 
				<%}%>
					<tr>
	    	          <td>
	    			    <input type="radio" name="QC<%= sampleId %>" value="<%=LimsConstants.LIMS_TX_NONE%>" <%=limsPathQcQuery.getCheckedValueForRadio("QC"+sampleId, LimsConstants.LIMS_TX_NONE)%>>
                      </td>  
                      <td>
	    			      None
                      </td>  
                    </tr>  
			</table>
		</td>
	    <td rowspan="6">
	    	<div onmouseover="return overlib('Pathologist: <bean:write name="sampleDetail" property="reportedEvaluationPathologist"/> <br>Slide ID: <bean:write name="sampleDetail" property="reportedEvaluationSlideId"/><br>Evaluation Id: <bean:write name="sampleDetail" property="reportedEvaluationId"/><br>Date Created: <bean:write name="sampleDetail" property="reportedEvaluationCreateDate"/> <br>Date Reported: <bean:write name="sampleDetail" property="reportedEvaluationDate"/> <br>Date Pulled: <bean:write name="sampleDetail" property="pulledDate"/>');" onmouseout="return nd();">
	    		<%= sampleDetail.getPvStatus() %>
			</div>
	    </td>
	    <td rowspan="2">
	      <div align="left">
	    	  &nbsp;<bigr:icpLink popup="true"><bean:write name="sampleDetail" property="caseId"/></bigr:icpLink>
	      </div>
	    </td>
	    <td rowspan="6">
	    	<div onmouseover="return overlib('<bean:write name="sampleDetail" property="sampleLogicalRepositoryShortNames"/>');" onmouseout="return nd();">
	    		<%= sampleDetail.getSampleLogicalRepositoryShortNamesShort() %>
			</div>
	    </td>
	    
	    <td rowspan="6" align="center">
	      <logic:present name="sampleDetail" property="pathReport">
	      	<a href="javascript:void(0)" onClick="window.open('<html:rewrite page="/ddc/Dispatch"/>?op=PathRawPrepare&popup=true&pathReportId=<bean:write name="sampleDetail" property="pathReportId"/>','ASCII2373','scrollbars=yes,resizable=yes,status=yes,width=640,height=480')">Raw</a><p>
	      </logic:present>
	      <logic:notPresent name="sampleDetail" property="pathReport">
	        <a href="javascript:void(0)" onClick="alert('There is no raw path report available for this sample.');">Raw</a><p>
	      </logic:notPresent>
	      
	      <a href="javascript:void(0)" onclick="window.open('<html:rewrite page="/library/Dispatch"/>?op=PathInfoStart&case_id=<bean:write name="sampleDetail" property="caseId"/>&donor_id=<bean:write name="sampleDetail" property="donorId"/>&popUp=true','DonorProfile<bean:write name="sampleDetail" property="donorId"/>','scrollbars=yes,resizable=yes,status=yes,width=640,height=480')">Abstracted</a><p>
	     
	      <logic:present name="sampleDetail" property="reportedEvaluation">
	      <!-- pass the sample id to the tag instead of the evaluation id, so the code will look up the reported
	      	   evaluation instead of just using the one that was reported when this page was rendered (MR5824) -->
	      	<bigr:viewPvReport sample='<%= sampleDetail.getSampleId() %>' image1="/images/microscope.gif"/>
	      </logic:present>
   	      <logic:present name="sampleDetail" property="sampleImages">
		      <bigr:viewPvReport sample='<%= sampleDetail.getSampleId() %>' image1="/images/camera.jpg" showImageReport="true"/> 	      
		  </logic:present>
	    </td>
	    <td rowspan="3"><bean:write name="sampleDetail" property="DIPathReportPrimaryDxName"/></td>
	    <td rowspan="3"><bean:write name="sampleDetail" property="ASMTissueOfFindingName"/></td>
	    <td class="grey">NRM</td>
	    <td align="center"><bean:write name="sampleDetail" property="reportedEvaluationNormalCells"/></td>
	    <% if(sampleDetail.isPulled()){ %>
	    	<td rowspan="6"> PULLED REASON:  <bigr:beanWrite name="sampleDetail" property="pullOrDiscordantReason" filter="false" whitespace="false"/></td>
	    <% } else { %>
 	    	<td rowspan="6"><bigr:beanWrite name="sampleDetail" property="reportedEvaluationFormattedConcatenatedExternalData" filter="false" whitespace="false"/></td>
 	    <% } %>	
		<td rowspan="6"><bean:write name="sampleDetail" property="reportedEvaluationConcatenatedInternalData"/></td>
		<td rowspan="6"><bean:write name="sampleDetail" property="reportedEvaluationMicroscopicAppearance"/></td>
	    <td rowspan="2"><bean:write name="sampleDetail" property="inventoryStatus"/></td>
		<td rowspan="6">
		  <div onmouseover="return overlib('Tumor/Lymph Node/Distant Metastatis/Stage Grouping');" onmouseout="return nd();">
		    <logic:present name="sampleDetail" property="primarySection">
		      <% boolean showSlash = false;
		         if ((!ApiFunctions.isEmpty(sampleDetail.getPrimarySectionTumorStageDescName())) ||
		         	 (!ApiFunctions.isEmpty(sampleDetail.getPrimarySectionLymphNodeStageName())) ||
		         	 (!ApiFunctions.isEmpty(sampleDetail.getPrimarySectionDistantMetastasisName())) ||
		         	 (!ApiFunctions.isEmpty(sampleDetail.getPrimarySectionStageGroupingName()))
		            ) {
		         	showSlash = true;
		         }  
		      %>
		      <bean:write name="sampleDetail" property="primarySectionTumorStageDescName"/>
		      <%if (showSlash) {%>
		      /		      
		      <%}%>
		      <bean:write name="sampleDetail" property="primarySectionLymphNodeStageName"/>
		      	<%if (showSlash) {%>
		      /		      
		      <%}%>	      
		      <bean:write name="sampleDetail" property="primarySectionDistantMetastasisName"/>
		      <%if (showSlash) {%>
		      /		      
		      <%}%>      
		      <bean:write name="sampleDetail" property="primarySectionStageGroupingName"/>
		    </logic:present>
		  </div></td>
		<td rowspan="6"><bean:write name="sampleDetail" property="primarySectionHistologicalNuclearGradeName"/></td>
	    <td rowspan="3">
	    	<%
	    		String tumorSize = sampleDetail.getPrimarySectionTumorSize();
	    		if (Constants.NOT_SPECIFIED.equals(tumorSize)) {%>
	    			Not Specified
	    		<%} else {%>
	    			<%= tumorSize %>
	    		<%}
			%>
	    </td>
	    <td rowspan="3"><bean:write name="sampleDetail" property="ageAtCollection"/></td>
	  </tr>
	  <tr class="<%= rowClass%>">
	  	<td class="grey">
	    	LSN
	    </td>
	    <td align="center">
	    	<bean:write name="sampleDetail" property="reportedEvaluationLesionCells"/>
	    </td>
	  </tr>
	  <tr class="<%= rowClass%>">
	 	 
	  	<td rowspan="2"><div  align="left">&nbsp;<bigr:icpLink popup="true"><%= sampleDetail.getSampleId() %></bigr:icpLink></div></td>
	 
	  	<td class="grey">
	    	TMR
	    </td>
	    <td align="center">
	    	<bean:write name="sampleDetail" property="reportedEvaluationTumorCells"/>
	    </td>
	    <td rowspan="2"><bean:write name="sampleDetail" property="salesStatus"/></td>
	  </tr>
	  <tr class="<%= rowClass%>">	  	
	    <td rowspan="3"><bean:write name="sampleDetail" property="reportedEvaluationDxName"/></td>
	    <td rowspan="3">
	      <div align="left">
		      <bean:write name="sampleDetail" property="reportedEvaluationTissueOfOriginName"/>
		      <%if(!ApiFunctions.isEmpty(sampleDetail.getReportedEvaluationTissueOfFindingName())) {%>
		       / 
		      <%}%> 
		      <bean:write name="sampleDetail" property="reportedEvaluationTissueOfFindingName"/>
	      </div>
	    </td>
	  	<td class="grey">
	    	TCS
	    </td>
	    <td align="center">
	    	<bean:write name="sampleDetail" property="reportedEvaluationCellularStromaCells"/>
	    </td>
	    <td rowspan="3">
	    	<%
	    		String tumorWeight = sampleDetail.getPrimarySectionTumorWeight();
	    		if (Constants.NOT_SPECIFIED.equals(tumorWeight)) {%>
	    			Not Specified
	    		<%} else {%>
	    			<%= tumorWeight %>
	    		<%}
			%>
	    </td>
	    <td rowspan="3">
	    	<%
	    		String genderCode = sampleDetail.getDonorGender();
	    		if (Constants.GENDER_MALE.equals(genderCode)) {%>
	    			Male
	    		<%} else if (Constants.GENDER_FEMALE.equals(genderCode)) {%>
	    			Female
	    		<%} else if (Constants.GENDER_OTHER.equals(genderCode)) {%>
	    			Other
	    		<%} else if (Constants.GENDER_UNKNOWN.equals(genderCode)) {%>
	    			Unknown
	    		<%} else {%>
	    			Not Specified
	    		<%}
			%>
	    </td>
	  </tr>
	  <tr class="<%= rowClass%>">
	    <td rowspan="2"><div align="left">&nbsp;<bean:write name="sampleDetail" property="asmPosition"/></div></td>
	  	<td class="grey">
	    	TAS
	    </td>
	    <td align="center">
	    	<bean:write name="sampleDetail" property="reportedEvaluationHypoacellularStromaCells"/>
	    </td>
	    <td rowspan="2" title="<%= sampleDetail.getProjectStatusTitle()%>"><bean:write name="sampleDetail" property="projectStatus"/></td>
	  </tr>
	  <tr class="<%= rowClass%>">
	  	<td class="grey">
	    	NEC
	    </td>
	    <td align="center">
	    	<bean:write name="sampleDetail" property="reportedEvaluationNecrosisCells"/>
	    </td>
	  </tr>
	  <tr>
	  	<td class="white" colspan="18">
	  		&nbsp;
	  	</td>
	  </tr>
	  </logic:iterate>
	  </logic:present>
        </table>
      </td>
    </tr>
  </table>
</div>



