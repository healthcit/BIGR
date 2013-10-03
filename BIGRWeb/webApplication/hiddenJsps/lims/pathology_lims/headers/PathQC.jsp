<%@ page language="java" import="java.util.*" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.query.*"%>
<%@ page import="com.ardais.bigr.lims.helpers.*"%>

<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-bean"  prefix="bean" %>
<%@ taglib uri="/tld/struts-html"  prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic"%>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<%@ taglib uri="/tld/datetime"     prefix="dt"   %>
<bigr:overlibDiv/>

<logic:present name="limsPathQcForm" property="viewIncidentReport">
<logic:equal name="limsPathQcForm" property="viewIncidentReport" value="true">
    <%-- Setting isShowViewIncidentReportPopup causes code in LimsPathQCTemplate.jsp to
         display the incident report popup when the page loads. We've changed it to do
         it this way so that we're sure that the parent page is completely loaded
         before we try to do things like open windows from it. --%>
	<script type="text/javascript">isShowViewIncidentReportPopup = true;</script>
</logic:equal>
</logic:present>

<logic:present name="limsPathQcForm" property="viewQCEdit">
<logic:equal name="limsPathQcForm" property="viewQCEdit" value="true">
    <%-- Setting isShowQCEditPopup causes code in LimsPathQCTemplate.jsp to
         display the new evaluation popup when the page loads. We've changed it to do
         it this way so that we're sure that the parent page is completely loaded
         before we try to do things like open windows from it. --%>
	<script type="text/javascript">isShowQCEditPopup = true;</script>
</logic:equal>
</logic:present>

<table width="100%" cellpadding="0" cellspacing="0" border="0" class="background">
  <tr>
    <td>
	  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
	    <div ondblclick="showOrHideMenu();">
		  <tr class="yellow">
		    <td>
			  <b>
			    <div align="center" id="titleDiv" onclick="showOrHideMenu();">
			      <logic:present name="limsPathQcForm" property="searchCriteriaStyle">
			        <logic:equal name="limsPathQcForm" property="searchCriteriaStyle" value="inline">
				  	  Search (Click to hide search criteria)
				    </logic:equal>
					<logic:notEqual name="limsPathQcForm" property="searchCriteriaStyle" value="inline">
				  	  Search (Click to show search criteria)
				    </logic:notEqual>
				  </logic:present>  
				</div>
			  </b>
			</td>
		  </tr>
		</div>
	  </td>
	</tr>
	<logic:present name="org.apache.struts.action.ERROR">
	  <tr class="yellow"> 
	    <td colspan="10"> 
	      <div align="center">
	        <font color="#FF0000"><b><html:errors/></b></font>
	      </div>
	    </td>
	  </tr>
	</logic:present>
    <logic:present name="com.ardais.BTX_ACTION_MESSAGES">
	  <tr class="yellow"> 
	    <td colspan="10"> 
	      <div align="center">
	        <font color="#FF0000"><b><bigr:btxActionMessages/></b></font>
	      </div>
	    </td>
	  </tr>
	</logic:present>
</table>
<div id="searchCriteria" style="display: <bean:write name="limsPathQcForm" property="searchCriteriaStyle"/>;">
<html:hidden name="limsPathQcForm" property="searchCriteriaStyle"/>
<table width="100%" border="0" cellspacing="1" cellpadding="1"
			class="foreground-small">
			
			<tr class="grey">
				<td>
					<table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
						<tr>
							<td width="50%" nowrap class="grey">
								<div onmouseover="return overlib('This option applies to Non-Pulled Samples only',FIXX,130,OFFSETY,0);" onmouseout="return nd();">
									<b>Pathologist</b>
								</div>
							</td>
							<td width="50%" nowrap class="grey">
								<b>Inventory Groups</b>
							</td>
						</tr>
					</table>
				</td>
				<td class="grey"><b>Date Last PVed</b></td>
				<td class="yellow" rowspan="7" align="center"><B>A<br>N<br>D</B>
				</td>
				<td rowspan="2" class="grey" nowrap>
				<div align="center"><b>Case(s)<br>Defaults<br>to All</b></div>
				</td>
				<td rowspan="2" class="white">
				<table>
					<tr valign="middle">
						<td><input type="text" class="smallTextBox"  name="consentID" size="15"
							maxlength="12"></td>
						<td>
						<div align="center">
							<bigr:idlist property="caseIdsButton" type="CASE_TYPE" showRemoveAll="false" idfrom="consentID" idlist="caseIdList" limitValidation="true" style="FONT-SIZE:xx-small"/></div>
						</td>
						<td><html:select  styleClass="smallSelectBox" multiple="true" name="limsPathQcForm"	property="caseIdList" size="4">
							<logic:present name="limsPathQcForm" property="caseIdList">
								<html:options name="limsPathQcForm" property="caseIdList" />
							</logic:present>
							</html:select>
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr class="white">
				<td>
					<table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
						<tr>
							<td width="50%">
								<html:select name="limsPathQcForm" styleClass="smallSelectBox" property="pathologistList" multiple="true" size="4" >
									<logic:present name="limsPathQcForm" property="pathologistSelectList">
											<html:options name="limsPathQcForm" property="pathologistSelectList" />
									</logic:present>	
								</html:select>
							</td>
							<td width="50%">
				                <bigr:selectList
				                  style="font-size:xx-small;"
				                  size="4"
				                  multiple="true"
				                  name="limsPathQcForm"
				                  property="logicalRepositoryList"
				                  legalValueSetName="limsPathQcForm"
				                  legalValueSetProperty="logicalRepositoryOptions"/>
							</td>
						</tr>
					</table>
				</td>
				<td><html:text styleClass="smallTextBox" name="limsPathQcForm"
					property="startDate" size="12" maxlength="10" readonly="true" />
				 <span class="fakeLink" onclick="openCalendar('startDate')">Start Date</span>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="javascript:clearDateField('startDate')">Clear Start Date</a>
					 <br>

				<html:text styleClass="smallTextBox" name="limsPathQcForm" property="endDate" size="12"
					maxlength="10" readonly="true" /> 
					<span class="fakeLink" onclick="openCalendar('endDate')">End Date</span>
				&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="javascript:clearDateField('endDate')">Clear End Date</a>
					</td>
			</tr>
			<tr class="white">
			    <td nowrap class="grey">
				<b>QC Status</b>
				</td>
				<td class="grey"><b>PV Status</b></td>
					
				<td class="grey" rowspan="3"><div align="center"><b>Sample(s)<br>Defaults<br>to All</b></div></td>
				<td class="white" rowspan="3">
				<table>
					<tr>
						<td><input type="text" class="smallTextBox" name="sampleID" size="15"
							maxlength="10"><br>
						</td>
						<td>
						<div align="center">
							<bigr:idlist property="sampleIdsButton" type="SAMPLE_TYPE" showRemoveAll="false" idfrom="sampleID" idlist="sampleIdList" style="FONT-SIZE:xx-small"/>
							<br>
							<input type="button" style="font-size:xx-small" value="Add Samples to Clipboard" onClick="addSamplesToClipboard()"/>
						</div>
						</td>
						<td><html:select   styleClass="smallSelectBox" multiple="true" name="limsPathQcForm"
							property="sampleIdList" size="4">
							<logic:present name="limsPathQcForm" property="sampleIdList">
								<html:options name="limsPathQcForm" property="sampleIdList" />
							</logic:present>
						</html:select></td>

					</tr>
				</table>
				</td>
			</tr>
			<tr>
			<td style="font-size:xx-small;" class="white">
			     <table>
						<tr>
							<td style="font-size:xx-small;"><html:multibox property="includeList" value='<%= com.ardais.bigr.lims.helpers.LimsConstants.PATH_QC_SAMPLES_NOT_PVD %>'/></td>
							<td nowrap style="font-size:xx-small;" >Include Samples not<br>Path Verified</td>
						</tr>							
						<tr>
							<td style="font-size:xx-small;"><html:multibox property="includeList" value='<%= com.ardais.bigr.lims.helpers.LimsConstants.PATH_QC_SAMPLES_FOR_RELEASE %>'/></td>
							<td nowrap style="font-size:xx-small;">Include Samples Path Verified,<br> but not Released</td>													
						</tr>
						<tr>
						    <td style="font-size:xx-small;"><html:multibox property="includeList" value='<%= com.ardais.bigr.lims.helpers.LimsConstants.PATH_QC_RELEASED_SAMPLES %>'/></td>
							<td nowrap style="font-size:xx-small;">Include Samples Released, but not QCPosted</td>							
						</tr>
						<tr>
						    <td style="font-size:xx-small;"><html:multibox property="includeList" value='<%= com.ardais.bigr.lims.helpers.LimsConstants.PATH_QC_POSTING_SAMPLES %>'/></td>
							<td nowrap style="font-size:xx-small;">Include Samples that are QCPosted</td>							
						</tr>
				</table>					
			</td>	
			<td style="font-size:xx-small;" class="white" rowspan="2">	
				<table>
						<tr>
							<td style="font-size:xx-small;"><html:radio property="pvStatus" value='<%= com.ardais.bigr.lims.helpers.LimsConstants.PATH_QC_INPROCESS_SAMPLES %>'/></td>
							<td style="font-size:xx-small;">Currently on order for PV<br>(InProcess)</td>
						</tr>						
						<tr>
						    <td style="font-size:xx-small;"><html:radio property="pvStatus" value='<%= com.ardais.bigr.lims.helpers.LimsConstants.PATH_QC_AWAITING_SAMPLES %>'/></td>
							<td style="font-size:xx-small;">Currently not on order for PV<br>(Awaiting)</td>							
						</tr>
						<tr>
						    <td style="font-size:xx-small;"><html:radio property="pvStatus" value='<%= com.ardais.bigr.lims.helpers.LimsConstants.PATH_QC_BOTH_SAMPLES %>'/></td>
							<td style="font-size:xx-small;">Both</td>							
						</tr>
				</table>								
			</td>					
			</tr>
			<tr class="white">
			<td>
			<table>						
						<tr>
							<td style="font-size:xx-small;"><html:multibox property="includeList" value='<%= com.ardais.bigr.lims.helpers.LimsConstants.PATH_QC_PULLED_SAMPLES %>'/></td>
							<td style="font-size:xx-small;">Include Pulled Samples</td>		
							<td style="font-size:xx-small;"><input  class="smallButton" type="button" name="All" value="All QC Status" onclick="setAllQcStatus()"></td>											
						</tr>
				</table>
			</td>
			</tr>
			<tr class="white">	
				<td class='grey'>
				  <B>Sort Order</B>				
				</td>
				<td>
				  
				  <bigr:selectList styleClass="smallSelectBox" property="sortValue" name="limsPathQcForm" legalValueSetName="limsPathQcForm" legalValueSetProperty="sortOptions"/>
				  <bigr:selectList styleClass="smallSelectBox" property="sortOrderValue" name="limsPathQcForm" legalValueSetName="limsPathQcForm" legalValueSetProperty="sortOrderOptions"/>				    				    					
				</td>				
				<td rowspan="2" class="grey" nowrap><div align="center"><b>Slide(s)<br>Defaults<br>to All</b></div></td>
				<td rowspan="2" class="white">
				<table>
					<tr>
						<td><input type="text" class="smallTextBox" name="slideID" size="15" maxlength="10">
						</td>
						<td>
						<div align="center">
							<bigr:idlist property="slideIdsButton" type="SLIDE_TYPE" showRemoveAll="false" idfrom="slideID" idlist="slideIdList" style="FONT-SIZE:xx-small"/>
						</div>
						</td>
						<td><html:select styleClass="smallSelectBox" multiple="true" name="limsPathQcForm"
							property="slideIdList" size="4">
							<logic:present name="limsPathQcForm" property="slideIdList">
								<html:options name="limsPathQcForm" property="slideIdList" />
							</logic:present>
						</html:select></td>

					</tr>
				</table>
				</td>
			</tr>	
			<tr class="white">
				<td colspan='2'>
					<div align="center">
					    <html:checkbox property="retrieveCounts" value="true" /> Include Counts &nbsp; &nbsp;
						<input  class="smallButton" type="submit" name="filter" value="Search"> 
						<input  class="smallButton" style="width:90px;" type="button" name="clear" value="Clear Search" onclick="clearSearch()">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input  class="smallButton"style="width:110px;"  type="button" name="markAll" value="Mark All on page" onclick="markAllOnPage()">
						<input  class="smallButton" style="width:80px;" type="submit" name="clearMark" value="Clear Marks">
					</div>
				</td>
			</tr>
		</table>
		</div>
		</td>
	</tr>
</table>

<table width="100%" cellpadding="0" cellspacing="0" border="0" class="background">
    <tr> 
      <td> 
        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
          <tr class="green">
          	<td style="padding-top:0;" colspan="6">
          		<div align="center">
          		<B>WorkQ - <bean:write name="limsPathQcForm" property="returnedSamplesCount"/> samples listed</B>
          		</div>
          	</td>
          </tr>
          <logic:present name="limsPathQcForm" property="retrieveCounts">
          <tr class="yellow">
          <td><div align="center">
          		Samples Not PVed: <bean:write name="limsPathQcForm" property="unPVedCount"/>
          		</div>
          	</td>
          	<td><div align="center">
          		Samples Eligible for Release: <bean:write name="limsPathQcForm" property="pvCount"/>
          		</div>
          	</td>
          	<td><div align="center">
				Samples Eligible for QCPosting: <bean:write name="limsPathQcForm" property="releasedCount"/>          	
          		</div>
          	</td>          	
          	<td><div align="center">
          		Samples QCPosted: <bean:write name="limsPathQcForm" property="qcPostedCount"/>
          		</div>
          	</td>
          	<td><div align="center">
          		Samples Pulled: <bean:write name="limsPathQcForm" property="pulledCount"/>          		
          		</div>
          	</td>
          </tr>
          </logic:present>
          <input type="hidden" name="targetSampleId">
          <input type="hidden" name="sampleFunction">
          <logic:present name='<%= com.ardais.bigr.query.LimsPathQcQuery.LIMSPATHQCQUERY_KEY %>'>
	     <bean:define id="limsPathQcQuery" name='<%= LimsPathQcQuery.LIMSPATHQCQUERY_KEY %>' type="LimsPathQcQuery"/>
	     <bean:define id="currentChunk" name='<%=  LimsConstants.LIMS_CURRENT_CHUNK %>' type="java.lang.String"/>
	    
				

	      <tr class="white">
	                <td align="center" colspan="1" style="padding-bottom:3px;">
	                  <input class="smallButton" type="submit" name="updateAll" value="Update All">
	                </td>
	                <td align="center" colspan="1" style="padding-bottom:3px;">
 				      <input type="hidden" name="viewIncidentReport">
					  <input class="smallButton" type="submit" value="Incident Report" 
								onClick="return document.all.viewIncidentReport.value = 'true';">
					</td>
					<td align="center" colspan="3" style="padding-bottom:3px;">
			        <% int fromNum = 1;
			           int toNum = 10;
			           int maxNum = limsPathQcQuery.getSamplesChunkNumber();
			           
			           int currentNum = (new Integer(currentChunk)).intValue();
			           
			           if(maxNum <= 10){
			             toNum = maxNum;
			           } else if(currentNum > 5) {
			             fromNum = currentNum - 5;
			             toNum = currentNum + 4;
			           }
			           
			           if(toNum > maxNum) {
			             toNum = maxNum;
			           }
			       %>
						<input type="hidden" name="index" value="<%= currentNum %>">					            
				    <b><a href="javascript: submitPagerForm(1);">&lt;&lt; First</a></b>&nbsp;&nbsp;
				    <b>
				     <%if (currentNum != 1) {%>
				     <a href="javascript: submitPagerForm(<%= currentNum - 1 %>);">&lt; Previous</a>
				     <%} else {%>
				     &lt; Previous
				     <%}%>
				    </b>&nbsp;&nbsp;
						<b><% for(int i = fromNum; i <= toNum; i++){ %>
								  <% if(i == currentNum) { %>
								    <font color="red"><%= i %></font>&nbsp;
								  <% } else { %>
								    <a href="javascript: submitPagerForm(<%= i %>);"><%= i %></a>&nbsp;
								  <% } %>
								<% } %></b>&nbsp;&nbsp;								
						<b>
						<% if (currentNum  != maxNum) { %>
						  <a href="javascript: submitPagerForm(<%= ((currentNum + 1) <= maxNum) ? currentNum + 1 : currentNum%>);">Next &gt;</a>
						<%} else {%>  
						  Next &gt;
						<%}%>
						</b>&nbsp;&nbsp;
						<b><a href="javascript: submitPagerForm(<%= maxNum%>);">Last &gt;&gt;</a></b>
					</td>
					<td align="right">
						<b>Page <%= currentNum %> of <%= maxNum %></b>
					</td>
				</tr>
				</logic:present>
   		</table>
   	  </td>
   	</tr>
</table>
