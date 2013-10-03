<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.util.Constants"%>
<%@ page import="com.ardais.bigr.security.*"%>
<%@ page import="com.ardais.bigr.library.web.helper.ResultsHelper"%>
<%@ page import="com.ardais.bigr.util.WebUtils" %> 
<%@ page import="com.ardais.bigr.api.ApiProperties"%>

<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<script>

function checkAny(id){
	var isAny = (event.srcElement.value == "any");
	var isChecked = (event.srcElement.checked);
	var boxes = document.all[id];
	 
	// If Any is checked, then clear all others.
	if ((isAny) && (isChecked)) {
		for (var i = 0; i < boxes.length; i++) {
			if (boxes[i].value != "any") {
				boxes[i].checked = false;
			}
		}
	}
	
	else {

		// Get the any checkbox, and determine if anything else is checked.
		var anyCheckbox = null;
		for (var i = 0; i < boxes.length; i++) {
			if (boxes[i].value == "any") {
				anyCheckbox = boxes[i];
			}
			if (boxes[i].checked) {
				isChecked = true;
			}
		}

		// If anything other than Any is checked, then clear Any;
		// otherwise if nothing is checked, then set Any.
		anyCheckbox.checked = !isChecked;
	}	
}
</script>

<%
// The Diagnosis tab of the SampleSelectionQuery JSP.

boolean showCaseDiagnosisCriteria = false;

String txType = request.getParameter("txType");
SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);

if (securityInfo.isInRoleSystemOwnerOrDi()) {
  showCaseDiagnosisCriteria = true;
}

%>

<!-- Diagnosis tab -->
  <div id="diagnosisDiv" class="libraryTabContents" style="display: none;">
	  <table border="0" cellspacing="0" cellpadding="0" vspace="0" width="100%" class="foreground-small">
		  <col width="5%">
		  <col width="45%"> 
		  <col width="45%">
		  <col width="5%">
      
      <!-- Diagnosis.title -->
		  <tr id="diagnosisTitle">
		    <td colspan="4">
		      <table border="0" cellspacing="0" cellpadding="3" class="foreground-small" width="100%">
		        <col width="90%">
				    <col width="5%"> 
				    <col width="5%">
						  <td id="diagnosisTitle1" class="libraryTabTitle">
							  <b><bean:message key="library.ss.query.diagnosis.tabname"/> -
<% if (ApiProperties.isEnabledLegacy()) { %>
								<bean:message key="library.ss.query.diagnosis.title.label.legacy"/>
<% } else { %>
								<bean:message key="library.ss.query.diagnosis.title.label"/>
<% } %>
								</b>
	            </td>
	            <td id="diagnosisTitle3" class="libraryTabTitle">
	              	<html:button property="clearQueryButton" styleClass="libraryButtons" onclick="clrQuery('diagnosis')"
							onmouseout="return nd()" onmouseover="return overlib('Resets all values on this tab to default values')">
								<bean:message key="library.ss.query.button.clearSection"/>
							</html:button>
	            	</td>
	            <td id="diagnosisTitle2" class="libraryTabTitle">
	              <a href="#top">Top</a>
              </td>
            </table>
          </td>
        </tr>
        <tr class="white">
		      <td>&nbsp;</td>
  				<!-- Diagnosis.case diagnosis link -->
					<td style="padding: 1em 1em 0 0;">
	          <b><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.diagnosis.case.title.comment"/>');"><bean:message key="library.ss.query.diagnosis.case.title.label"/></span></b>
	          <%
	            if (showCaseDiagnosisCriteria) {
	          %>
	            &nbsp;&nbsp;&nbsp;<html:radio property="diagnosis.from" value="<%= Constants.BEST %>" />
	            <bean:message key="library.ss.query.diagnosis.best"/>
	            &nbsp;<html:radio property="diagnosis.from" value="<%= Constants.ILTDS %>" />
	            <bean:message key="library.ss.query.diagnosis.iltds"/>
	            &nbsp;<html:radio property="diagnosis.from" value="<%= Constants.DDC %>" />
	            <bean:message key="library.ss.query.diagnosis.ddc"/>
	          <%
	            }
	          %>
	        </td>
          <td rowspan="3" valign="middle" style="padding: 0.25em 0 0 1em;">
<% if (ApiProperties.isEnabledLegacy()) { %>
						<table border="0" cellspacing="0" cellpadding="1" width="100%" class="foreground-small" >
							<tbody>
			          <tr class="white">
			            <td style="padding-top: 0.5em;">
			                        <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.diagnosis.pathnotes.comment"/>');"><bean:message key="library.ss.query.diagnosis.pathnotes.label"/></span><br>
			              <html:text property="diagnosis.pathNotesContains" size="40" />
			    
			            </td>
			          </tr>
							</tbody>
						</table>
<% } else {%>
&nbsp;
<% } %>
	        </td>
          
          </tr>
          
          
				<!-- Diagnosis.case diagnosis list -->
	      <tr class="white">
		      <td>&nbsp;</td>
	        <td style="padding: 0.25em 1em 0 0;">
	          <table border="0" cellspacing="0" cellpadding="0" width="100%" style="margin: 0 0 0.25em 0;" class="foreground-small">
		        	<tr>
		        		<td><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.diagnosis.case.is.comment"/>');"><bean:message key="library.ss.query.diagnosis.case.is.label"/></span></td>
		        		<td align="right">
		        			<html:link href="#" onclick="openDiagnosisHierarchy('libraryCaseDiagnosis');">
					          Select
				          </html:link>
		        		</td>
		        	</tr>
	          </table>

	          <ul id="libraryCaseDiagnosis" class="compactListBox" 
	              style="cursor: hand;" onclick="openDiagnosisHierarchy('libraryCaseDiagnosis');">
	              <logic:iterate name="queryForm" property="diagnosis.caseDiagnosisLabel" id="label">
	                <li><bean:write name="label"/></li>
	              </logic:iterate>
	          </ul>
	          	          
	        </td>
          <td>&nbsp;</td>
	      </tr>

				<!-- Diagnosis.case or contains -->
	      <tr class="white">
		      <td>&nbsp;</td>
	        <td style="padding: 0.25em 1em 0 0;">
	        	<%--span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.diagnosis.case.contains.comment"/>');"><bean:message key="library.ss.query.diagnosis.case.contains.label"/></span><br>
	          <html:text property="diagnosis.caseDiagnosisContains" size="20"/--%>
	        </td>
		      <td>&nbsp;</td>
	      </tr>
          <tr class="white">
				  <td>&nbsp;</td>
				  <td colspan="2"> <jsp:include page="/hiddenJsps/dataImport/attr/caseDiagnosis.jsp?fieldId=Diagnosis" flush="true"/></td>
				  <td>&nbsp;</td>
		  </tr>
		  <tr class="white">
				  <td>&nbsp;</td>
				  <td colspan="2"> <input type="button" value="Add" onClick="selectChoice($('diagnosis&Diagnosis'), '<%=request.getContextPath()%>','libraryCaseDiagnosis','<%=request.getParameter("txType") %>')"/></td>
								 
				  <td>&nbsp;</td>
		  </tr>
<% if (ApiProperties.isEnabledLegacy()) { %>      
        <tr>
          <td>&nbsp;</td>
          <td colspan="2" style="padding: 2em 0 0 0;">
	        	<b><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.diagnosis.path.title.comment"/>');"><bean:message key="library.ss.query.diagnosis.path.title.label"/></span></b>
          </td>
          <td>&nbsp;</td>

        <tr>
          <td>&nbsp;</td>
          <!-- Diagnosis.stage, t, n, m, hng, path notes -->
	        <td colspan="2" style="padding: 0.5em 0 0 0;">
						<table border="0" cellspacing="0" cellpadding="1" width="100%" class="foreground-small" >
							<tbody>
								<tr>
			            <td align="right" style="padding-top: 0.5em;">
			              <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.stage.query.comment"/>');"><bean:message key="library.ss.stage.label"/></span>&nbsp;
			            </td>
			            <td> 
			              <html:multibox property="diagnosis.stage" onclick="checkAny('diagnosis.stage');" value='<%= Constants.ANY %>'/>Any&nbsp;
			              <logic:iterate name="queryForm" property="diagnosis.stageList" id="stage" type="com.gulfstreambio.gboss.GbossValue">
			              <html:multibox property="diagnosis.stage" onclick="checkAny('diagnosis.stage');" value='<%= stage.getCui() %>'/><%= stage.getDescription() %>&nbsp;
			              </logic:iterate>
			            </td>
								</tr>
								<tr>
			            <td align="right" style="padding-top: 0.5em;">
			              <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.tumor.comment"/>');"><bean:message key="library.ss.tumor.label"/></span>&nbsp;
			            </td>
			            <td>
			              <html:multibox property="diagnosis.tumorStage" onclick="checkAny('diagnosis.tumorStage');" value='<%= Constants.ANY %>'/>Any&nbsp;
			              <logic:iterate   name="queryForm" 
			                               property="diagnosis.tumorStageList" 
			                               id="tumorStage"
			                               type="com.gulfstreambio.gboss.GbossValue">
			                               <html:multibox property="diagnosis.tumorStage" onclick="checkAny('diagnosis.tumorStage');" value='<%= tumorStage.getCui() %>'/><%= tumorStage.getDescription() %>&nbsp;
			             </logic:iterate>
			            </td>
								</tr>
								<tr>
			            <td align="right" style="padding-top: 0.5em;">
										<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.lymph.comment"/>');"><bean:message key="library.ss.lymph.label"/></span>&nbsp;
			            </td>
			            <td>
			            <html:multibox property="diagnosis.lymphNodeStage" onclick="checkAny('diagnosis.lymphNodeStage');" value='<%= Constants.ANY %>'/>Any&nbsp;
										<logic:iterate 	name="queryForm"
			 							              	property="diagnosis.lymphNodeStageList" 
			                               				id="lymphNodeStage"
			                               				type="com.gulfstreambio.gboss.GbossValue">
			                               				<html:multibox property="diagnosis.lymphNodeStage" onclick="checkAny('diagnosis.lymphNodeStage');" value='<%= lymphNodeStage.getCui() %>'/><%= lymphNodeStage.getDescription() %>&nbsp;
										</logic:iterate>
			            </td>
								</tr>
								<tr>
			            <td align="right" style="padding-top: 0.5em;">
			              <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.metastasis.comment"/>');"><bean:message key="library.ss.metastasis.label"/></span>&nbsp;
			            </td>
			            <td>
			            <html:multibox property="diagnosis.distantMetastasis" onclick="checkAny('diagnosis.distantMetastasis');" value='<%= Constants.ANY %>'/>Any&nbsp;
										<logic:iterate 	name="queryForm"
			 							               	id="distantMetastasis" 
			                               				property="diagnosis.distantMetastasisList"
			                               				type="com.gulfstreambio.gboss.GbossValue">
			                               				<html:multibox property="diagnosis.distantMetastasis" onclick="checkAny('diagnosis.distantMetastasis');" value='<%= distantMetastasis.getCui() %>'/><%= distantMetastasis.getDescription() %>&nbsp;
			                               </logic:iterate>
								  </td>
								</tr>
								<tr>
			            <td align="right" style="padding-top: 0.5em;">
			              <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.hng.query.comment"/>');"><bean:message key="library.ss.hng.label"/></span>&nbsp;
			            </td>
			            <td>
			            <html:multibox property="diagnosis.hng" onclick="checkAny('diagnosis.hng');" value='<%= Constants.ANY %>'/>Any&nbsp;
			              <logic:iterate name="queryForm" 
			                               property="diagnosis.hngList" 
			                               id="hng"
			                               type="com.gulfstreambio.gboss.GbossValue">
			                               <html:multibox property="diagnosis.hng" onclick="checkAny('diagnosis.hng');" value='<%= hng.getCui() %>'/><%= hng.getDescription() %>&nbsp;
			              </logic:iterate>
			                               
			            </td>
								</tr>
							</tbody>
						</table>
        	</td>
          </tr>

				<!-- Diagnosis.separator 
				<tr>
					<td style="padding: 1em 0 1em 0;">&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr> -->

	      <tr class="white">
		      <td>&nbsp;</td>

					<!-- Diagnosis.sample diagnosis link -->
	        <td style="padding: 2em 1em 0 0;">
	          <b><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.samplePathology.comment"/>');"><bean:message key="library.ss.samplePathology.label"/></span></b>
	        </td>

					<!-- Diagnosis.pv notes contains -->
	        <td rowspan="3" valign="middle" style="padding: 0.25em 0 0 1em;">
						<table border="0" cellspacing="0" cellpadding="1" width="100%" class="foreground-small" >
							<tbody>
			          <tr class="white">
			            <td style="padding-top: 0.5em;">
			              <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.diagnosis.pvnotes.comment"/>');"><bean:message key="library.ss.query.diagnosis.pvnotes.label"/></span><br>
			              <html:text property="diagnosis.pathVerifNotesContains" size="40" />
			            </td>
			          </tr>
							</tbody>
						</table>
	        </td>
	      </tr>

				<!-- Diagnosis.sample diagnosis list -->
	      <tr class="white">
		      <td>&nbsp;</td>
	        <td style="padding: 0.25em 1em 0 0;">
	          <table border="0" cellspacing="0" cellpadding="0" width="100%" style="margin: 0 0 0.25em 0;" class="foreground-small">
		        	<tr>
		        		<td><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.diagnosis.sample.is.comment"/>');"><bean:message key="library.ss.query.diagnosis.sample.is.label"/><span></td>
		        		<td align="right">
		        			<html:link href="#" onclick="openDiagnosisHierarchy('librarySamplePathology');">
					          Select
				          </html:link>
		        		</td>
		        	</tr>
	          </table>
	          
	          <ul id="librarySamplePathology" class="compactListBox" 
	              style="cursor: hand;" onclick="openDiagnosisHierarchy('librarySamplePathology');">
	              <logic:iterate name="queryForm" property="diagnosis.samplePathologyLabel" id="label">
	                <li><bean:write name="label"/></li>
	              </logic:iterate>
	          </ul>	          
	          
	        </td>  
		      <td>&nbsp;</td>
	      </tr>

				<!-- Diagnosis.sample or contains -->
	      <tr class="white">
		      <td>&nbsp;</td>
	        <td style="padding: 0.25em 1em 0 0;">
	        	<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.diagnosis.sample.contains.comment"/>');"><bean:message key="library.ss.query.diagnosis.sample.contains.label"/></span><br>
	          <html:text property="diagnosis.samplePathologyContains" size="20"/>
	        </td>
		      <td>&nbsp;</td>
	      </tr>
<% } %>

	    </tbody>
	  </table>
	</div>
