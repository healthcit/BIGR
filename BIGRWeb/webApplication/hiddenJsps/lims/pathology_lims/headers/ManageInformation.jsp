<%@ page language="java" import="java.util.*" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-bean"  prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic"%>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<%@ taglib uri="/tld/datetime"     prefix="dt"   %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<table>
  <tr>
    <td>
<table width="100%" cellpadding="0" cellspacing="0" border="0" class="background">
  <tr>
    <td>   
     <table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
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
	   <logic:present name="limsManageEvaluationsForm" property="message">
	    <tr class="yellow"> 
	     <td colspan="10"> 
	       <div align="center">
	         <font color="#FF0000"><b><bean:write name="limsManageEvaluationsForm" property="message"/></b></font>
	        </div>
	      </td>
	    </tr> 
	   </logic:present> 
	  </table>
    </td>
  </tr>
</table>
<table width="100%" cellpadding="0" cellspacing="0" border="0" class="background">	
		
  <tr>
    <td>
				<table width="100%" border="0" cellspacing="1" cellpadding="2"
					class="foreground-small">
					<tr>
						<td class="yellow" colspan="6">
						<div align="center"><b>SAMPLE - <bigr:icpLink popup="true"><bean:write
							name="limsManageEvaluationsForm" property="sampleId" /></bigr:icpLink> <html:hidden
							name="limsManageEvaluationsForm" property="sampleId" /> <logic:present
							name="limsManageEvaluationsForm" property="slideId"> 
                 (<bean:write name="limsManageEvaluationsForm"
								property="slideId" /> )
                 <html:hidden name="limsManageEvaluationsForm"
								property="slideId" />
						</logic:present> <logic:notPresent
							name="limsManageEvaluationsForm" property="slideId"> 
                    'Read only (Must Select Slide)'
                 </logic:notPresent> <logic:present
							name="limsManageEvaluationsForm" property="pulled">
							<logic:equal name="limsManageEvaluationsForm" property="pulled"
								value="Yes">
	  	  		     'Read only (Sample is Pulled)'
	  			   </logic:equal>
							</logic:present></b></div>
						</td>
						<td rowspan="14"></td>
						<td class="yellow">
						<div align="center"><b>Slides</b></div>
						</td>
					</tr>
					<tr class="white">
						<td nowrap class="grey">
						<div><b>ASM Position</b></div>
						</td>
						<td><bean:write name="limsManageEvaluationsForm"
							property="asmPosition" /></td>
						<td nowrap class="grey">
						<div><b>Sample Type</b></div>
						</td>
						<td><bean:write name="limsManageEvaluationsForm" property="sampleType" />
						</td>
						<td rowspan="3" class="grey"><b>Inventory Status</b></td>
						<td rowspan="3"><bean:write name="limsManageEvaluationsForm"
							property="invStatus" /></td>
						<td style="padding: 0">
						<table border="0" cellspacing="1" cellpadding="3"
							class="fgTableSmall" width="100%" height="100%">
							<col width="70">
							<col width="40">
							<col width="100">
							<tr class="grey">
								<td><b>Slide ID</b></td>
								<td class="grey"><b>Stain</b></td>
								<td nowrap class="grey"><b>Images</b></td>
							</tr>
						</table>
						</td>
					</tr>
					<tr class="white">
						<td nowrap class="grey">
						<div><b>Gross Appearance</b></div>
						</td>
						<td colspan="3"><bean:write name="limsManageEvaluationsForm"
							property="grossAppearance" /></td>
						<td rowspan="7" style="padding: 0">
						<div align="center"
							style="width: 240px; height: 120px; overflow-y: auto">

						<table border="0" cellspacing="1" cellpadding="3"
							class="fgTableSmall" width="100%">
							<col width="70">
							<col width="40">
							<col width="100">
							<logic:present name="limsManageEvaluationsForm" property="slides">
								<logic:iterate name="limsManageEvaluationsForm"
									property="slides" id="slides"
									type="com.ardais.bigr.lims.javabeans.SlideData">
									<tr class="white">
										<td nowrap><bean:write name="slides" property="slideId" /></td>
										<td nowrap><bean:write name="slides" property="procedure" /></td>
										<td><logic:iterate name="slides" property="images" id="images"
											type="com.ardais.bigr.lims.javabeans.ImageData">
											<a href="#"
												onClick="openImage('<html:rewrite page="/lims/limsViewSlideImage.do"/>?imageFilename=<bean:write name="images" property="imageFilename"/>&magnification=<bean:write name="images" property="magnification"/>&slideId=<bean:write name="slides" property="slideId"/>');"><bean:write
												name="images" property="magnification" /></a>&nbsp;&nbsp;&nbsp;
		            </logic:iterate></td>
									</tr>
								</logic:iterate>
							</logic:present>
						</table>
						</div>
						</td>
					</tr>
					<tr class="white">
						<td nowrap class="grey">
						<div><b>Microscopic Appearance</b></div>
						</td>
						<td colspan="3"><bean:write name="limsManageEvaluationsForm"
							property="microAppearance" /></td>
					</tr>
					<tr class="white">
						<td rowspan="2" class="grey">
						<div><b>Sample Path from PV</b></div>
						</td>
						<td rowspan="2" colspan="3"><bean:write
							name="limsManageEvaluationsForm" property="reportedDxName" /></td>
						<td nowrap class="grey">
						<div><b>PV Status</b></div>
						</td>
						<td><bean:write name="limsManageEvaluationsForm"
							property="pvStatus" /></td>
					</tr>
					<tr class="white">
						<td class="grey"><b>Released?</b></td>
						<td><bean:write name="limsManageEvaluationsForm"
							property="released" /></td>
					</tr>
					<tr class="white">
						<td class="grey" rowspan="2">
						<div><b>Tissue of Site of Finding</b></div>
						</td>
						<td colspan="3" rowspan="2"><bean:write
							name="limsManageEvaluationsForm"
							property="reportedTissueOfFindingName" /></td>
						<td class="grey"><b>Pulled?</b></td>
						<td>
						<div><bean:write name="limsManageEvaluationsForm"
							property="pulled" /></div>
						</td>
					</tr>
					<tr class="white">
						<td class="grey" colspan="2">
						<div><b>Pull/Discordant Reason</b></div>
						</td>
					</tr>
					<tr class="white">
						<td class="grey">
						<div><b>Tissue of Origin of Diagnosis</b></div>
						</td>
						<td colspan="3"><bean:write name="limsManageEvaluationsForm"
							property="reportedTissueOfOriginName" /></td>
						<td colspan="2">
						<div>&nbsp;<bean:write name="limsManageEvaluationsForm"
							property="reason" /></div>
						</td>
					</tr>
					<tr class="yellow">
						<td colspan="6">
						<div align="center"><b>CASE - <bigr:icpLink popup="true"><bean:write
							name="limsManageEvaluationsForm" property="consentId" /></bigr:icpLink></b></div>
						</td>
						<td><b>Enter/Scan Slide or Sample ID:</b></td>
					</tr>
					<tr class="white">
						<td nowrap class="grey">
						<div><b>Case Dx at DI</b></div>
						</td>
						<td nowrap colspan="5"><bean:write
							name="limsManageEvaluationsForm" property="caseDxName" /></td>
						
						<td rowspan="2">
						<div align="center"><input type="text" name="id" maxlength="10"
							size="15" AUTOCOMPLETE = "off" onkeydown="checkKey(event);"> 
							<input style="font-size:xx-small;" type="button" Name="slideQuery" value="Submit" onFocus="scanValue();" onClick="scanValue();"></div>
						</td>
					</tr>
					<tr class="white">
						<td class="grey">
						<div><b>Primary Dx from DI Path Report:</b></div>
						</td>
						<td colspan="5"><bean:write name="limsManageEvaluationsForm"
							property="DIPathReportPrimaryDxName" /></td>
					</tr>
					<tr class="white">
						<td class="grey">
						<div><b>Primary Site of Finding</b></div>
						</td>
						<td colspan="4"><bean:write name="limsManageEvaluationsForm"
							property="primaryTissueOfFindingName" /></td>
						<td rowspan="2" align="center">
						<input type="button" style="font-size:xx-small; width: 150px;" value="Raw Path Report" onClick="viewRawPathReport()"><br>
						<input style="font-size:xx-small; width: 150px;" type="button" value="Abstracted Path Report"
								onclick="window.open('<html:rewrite page="/library/Dispatch"/>?op=PathInfoStart&amp;case_id=<bean:write name="limsManageEvaluationsForm" property="consentId"/>&amp;donor_id=<bean:write name="limsManageEvaluationsForm" property="donorId"/>&amp;popUp=true','DonorProfile<bean:write name="limsManageEvaluationsForm" property="donorId"/>','scrollbars=yes,resizable=yes,status=yes,width=640,height=480')">
						</td>
						<td rowspan="2">
						<div align="center"><logic:present
							name="limsManageEvaluationsForm" property="pulled">
							<logic:equal name="limsManageEvaluationsForm" property="pulled"
								value="Yes">
								<input style="font-size:xx-small;" type="button" name="unPull" value="Unpull"
									onClick="setPullStatus(
		  		       '<html:rewrite page="/lims/limsPullDiscordantSample.do"/>?reasonType=Unpull',
		  		       'resizable:yes;help:no;dialogWidth:200px;dialogHeight:200px', 
		  		       'unpull');">
								<input type="hidden" name="isUnPull">
							</logic:equal>
							<logic:notEqual name="limsManageEvaluationsForm"
								property="pulled" value="Yes">
								<input style="font-size:xx-small;" type="button" name="pull" value="Pull"
									onClick="setPullStatus(
		  		       '<html:rewrite page="/lims/limsPullDiscordantSample.do"/>?reasonType=Pull',
		  		       'resizable:yes;help:no;dialogWidth:200px;dialogHeight:200px', 
		  		       'pull');">
								<input type="hidden" name="isPull">
							</logic:notEqual>
							</logic:present>
							<html:hidden name="limsManageEvaluationsForm" property="reason" />
							<input style="font-size:xx-small;" type="button" name="incident" value="Create Incident"
									onClick="showViewIncidentReportPopup('<bean:write
							name="limsManageEvaluationsForm" property="sampleId" />');">
							</div>
						</td>
					</tr>
					<tr class="white">
						<td nowrap class="grey">
						<div><b>Primary Tissue of Origin of Diagnosis</b></div>
						</td>
						<td colspan="4"><bean:write name="limsManageEvaluationsForm"
							property="primaryTissueOfOriginName" /></td>
					</tr>
				</table>
				</td>
  </tr>
</table>
</td>
<td>


</td>
    </tr>
  </table>
          
