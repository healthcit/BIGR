<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiProperties"%>
<%@ page import="com.ardais.bigr.util.Constants"%>
<%@ page import="com.ardais.bigr.security.*"%>
<%@ page import="com.ardais.bigr.library.web.helper.ResultsHelper"%>
<%@ page import="com.ardais.bigr.iltds.helpers.FormLogic"%>
<%@ page import="com.ardais.bigr.iltds.helpers.IltdsUtils"%>
<%@ page import="com.ardais.bigr.iltds.assistants.LegalValueSet"%>
<%@ page import="com.ardais.bigr.util.WebUtils" %> 
<%@ page import="com.ardais.bigr.security.SecurityInfo" %> 

<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%
// The Attributes tab of the SampleSelectionQuery JSP.
boolean showBMSControl = false;
boolean showRawPathKeywordSrch = false;
boolean showNotPVed = false;

String txType = request.getParameter("txType");
SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);

com.ardais.bigr.library.web.form.QueryForm qform = 
(com.ardais.bigr.library.web.form.QueryForm) pageContext.findAttribute("queryForm");

//only show the BMS Y/N control if this is a Request Samples transaction
if (ApiProperties.isEnabledLegacy()) {   
	if (securityInfo.isInRoleSystemOwner() && ResultsHelper.TX_TYPE_SAMP_REQUEST.equals(txType)) {
	  showBMSControl = true;
	}
} 

String overlibAdj;
if (showBMSControl) {
	overlibAdj = "50";
}
else {
	overlibAdj = "350";
}

if (ApiProperties.isEnabledLegacy()) {
  if ((securityInfo.isInRoleSystemOwner()) ||
      (securityInfo.isInRoleDi() && ResultsHelper.TX_TYPE_SAMP_REQUEST.equals(txType))) {
    showRawPathKeywordSrch = true;
  }
}

if (securityInfo.isInRoleDi()) {
  showNotPVed = true;
}

//MR6913 -- Storage Location Query parameter (Show Local Samples Only)
boolean boolStorageLocation = false;
if (IltdsUtils.isUserLocaltoStorageUnit(securityInfo.getUsername())) {
	boolStorageLocation = true;
}

%>

<script type="text/javascript">
function toggleLogicalRepositoryControls() {
	if (document.all["allLogicalRepositoriesDisplay"].checked) {
		document.all["attributes.allLogicalRepositories"].value="true";
		deselectAllFromList("attributes.logicalRepositoryList");
		document.all["attributes.logicalRepositoryList"].disabled=true;
	}
	else {
		document.all["attributes.allLogicalRepositories"].value="false";
		document.all["attributes.logicalRepositoryList"].disabled=false;
	}
}

function toggleSampleTypesControls() {
	if (document.all["allSampleTypes"].checked) {
		document.all["attributes.allSampleTypes"].value="true";
		deselectAllFromList("attributes.sampleType");
		document.all["attributes.sampleType"].disabled=true;
	}
	else {
		document.all["attributes.allSampleTypes"].value="false";
		document.all["attributes.sampleType"].disabled=false;
	}
}

<% 	
	if ((showBMSControl) || (!ApiProperties.isEnabledLegacy())) { 
%>
	function populateLogicalRepositoryControls(bmsValue) {
		deselectAllFromList("attributes.logicalRepositoryList");
		clearList("attributes.logicalRepositoryList");
  		var options = document.all["attributes.logicalRepositoryList"].options;
		if (bmsValue == "<%= FormLogic.DB_YES %>") {
		<%
			LegalValueSet legalValues = qform.getAttributes().getLogicalRepositoryOptionsBMS();
			for (int i=0; i<legalValues.getCount(); i++) {
				String value = legalValues.getValue(i);
				String displayValue = legalValues.getDisplayValue(i);
		%>
    			options.add(new Option("<%=displayValue%>", "<%=value%>"));
    	<%
    		}
    	%>
		}
		else {
		<%
			legalValues = qform.getAttributes().getLogicalRepositoryOptionsNonBMS();
			for (int i=0; i<legalValues.getCount(); i++) {
				String value = legalValues.getValue(i);
				String displayValue = legalValues.getDisplayValue(i);
		%>
    			options.add(new Option("<%=displayValue%>", "<%=value%>"));
    	<%
    		}
    	%>
		}
	}
<% 	
	} 
%>


function updateHiddenField() {
	document.all["attributes.localOnly"].value = document.all.localOnlyCheckbox.checked;
}
</script>
	<!-- Attributes tab -->
	<div id="attributesDiv" class="libraryTabContents" style="display: none;">
		<table border="0" cellspacing="0" cellpadding="0" vspace="0" class="foreground-small" width="100%">
		  <col width="5%">
		  <col width="25%">
		  <col width="25%">
		  <col width="25%">
		  <col width="15%">
		  <col width="5%">
      <tbody>

				<!-- Attributes.title -->
        <tr id="attributesTitle">
          <td colspan="6">
            <table border="0" cellspacing="0" cellpadding="3" class="foreground-small" width="100%">
              <col width="90%">
		      <col width="5%">
			  <col width="5%">	       
							<tbody>
	            	<td id="attributesTitle1" class="libraryTabTitle">
									<b>
										<bean:message key="library.ss.query.attributes.tabname"/> -
										<bean:message key="library.ss.query.attributes.title.label"/>
									</b>
	              </td>
	            	<td id="attributesTitle3" class="libraryTabTitle">
	              	
	              	<html:button property="clearQueryButton" styleClass="libraryButtons" onclick="clrQuery('attributes')"
							onmouseout="return nd()" onmouseover="return overlib('Resets all values on this tab to default values')">
								<bean:message key="library.ss.query.button.clearSection"/>
							</html:button>
	            	</td>
	            	<td  id="attributesTitle2" class="libraryTabTitle">
	            	<a href="#top">Top</a> 
	            	</td>
	            	
	            </tbody>
            </table>
          </td>
        </tr>

				<!-- Attributes.donor -->
        <tr class="white">
          <td colspan="6" style="padding: 1em 0 1em 1em;">
        		<b><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.attributes.title.donor.comment"/>');"><bean:message key="library.ss.query.attributes.title.donor.label"/></span></b>
        	</td>
        </tr>
        <tr class="white">
          <td></td>
          <td align="left" nowrap>
            <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.gender.comment"/>');"><bean:message key="library.ss.query.gender.label"/></span><br>
            <html:radio property="attributes.gender" value='<%= Constants.ANY %>' />
            <bean:message key="library.ss.query.gender.any"/>
            &nbsp;<html:radio property="attributes.gender" value="<%= Constants.GENDER_MALE %>" />
            <bean:message key="library.ss.query.gender.male"/>
            &nbsp;<html:radio property="attributes.gender" value="<%= Constants.GENDER_FEMALE %>" />
            <bean:message key="library.ss.query.gender.female"/>
          </td>
          <td nowrap>
            <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.ageatcollection.comment"/>',FIXX,20);"><bean:message key="library.ss.ageatcollection.label"/></span><br>
            &nbsp;<bean:message key="library.ss.query.ageatcollection.from"/>
						&nbsp;<html:select property="attributes.ageAtCollectionFrom"><html:options property="attributes.ageAtCollectionFromList"/></html:select>
            &nbsp;<bean:message key="library.ss.query.ageatcollection.to"/>
						&nbsp;<html:select property="attributes.ageAtCollectionTo"><html:options property="attributes.ageAtCollectionToList"/></html:select>
          </td>
          <td colspan="3">&nbsp;</td>
        </tr>

		<%// -------------------------- Attributes.separator ------------------------ %>
		<tr>
			<td style="padding: 1em 0 1em 1em;" colspan="5">
				<hr style="border: 1px solid #336699; height: 1px;">
			</td>
			<td>&nbsp;</td>
		</tr>

				<!-- Attributes.case -->
        <tr class="white">
        	<td colspan="6" style="padding: 0.25em 0 1em 1em;">
        		<b><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.attributes.title.case.comment"/>');"><bean:message key="library.ss.query.attributes.title.case.label"/></span></b>
        	</td>
        </tr>
        <tr class="white">
          <td>&nbsp;</td>
          <%
          	boolean disabled = false;
          	if (securityInfo.isHasPrivilege(SecurityInfo.PRIV_LINKED_CASES_ONLY)) {
          		disabled = true;
          		//set the form to have it's linked value set to linked, so the linked
          		//radio button will be selected
          		qform.getAttributes().setLinked(Constants.LINKED_LINKED);
          	}
          %>
          <td align="left" nowrap>
            <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.linked.comment"/>');"><bean:message key="library.ss.query.linked.label"/></span><br>
            <html:radio property="attributes.linked" disabled="<%=disabled%>" value="<%= Constants.ANY %>" />
            <bean:message key="library.ss.query.linked.any"/>
            &nbsp;<html:radio property="attributes.linked" disabled="<%=disabled%>" value="<%= Constants.LINKED_LINKED %>" />
            <bean:message key="library.ss.query.linked.linked"/>
            &nbsp;<html:radio property="attributes.linked" disabled="<%=disabled%>" value="<%= Constants.LINKED_UNLINKED %>" />
            <bean:message key="library.ss.query.linked.unlinked"/>
          </td>

          <td colspan="3" valign="middle" style="padding: 0.25em 0 0 1em;">
          <%
            if (showRawPathKeywordSrch) {
          %>
            <table border="0" cellspacing="0" cellpadding="1" width="100%" class="foreground-small" >
			<tbody>
			  <tr class="white">
			     <td>
			       <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.attributes.rawpathnotes.comment"/>',FIXX,20);"><bean:message key="library.ss.query.attributes.rawpathnotes.label"/></span><br>
			       <html:text property="attributes.asciiReportContains" size="40" />
	             </td>
			   </tr>
			 </tbody>
			 </table>
          <%
            }
          %>
	      </td>

          <td>&nbsp;</td>
        </tr>
      </tbody>
    </table>
        
    <bigr:hasPrivilege priv="<%=SecurityInfo.PRIV_HOLDSOLD_ACCOUNT%>">
        
		<table border="0" cellspacing="0" cellpadding="0" vspace="0" class="foreground-small" width="100%">
		  <col width="5%">
		  <col width="2%">
		  <col width="43%">
		  <col width="2%">
		  <col width="43%">
		  <col width="5%">
	      <tbody>
	        
	        <tr class="white">
	          <td>&nbsp;</td>
	          <td align="left" nowrap colspan="4" style="padding-top: 0.5em;">
	            <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.attributes.holdsold.comment"/>');"><bean:message key="library.ss.query.attributes.holdsold.label"/></span>
	          </td>
	          <td>&nbsp;</td>
	        </tr>
	        <tr class="white">
	          <td>&nbsp;</td>
	          <td colspan="4">
	          	<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.attributes.holdsold.showall.comment"/>');">
	            <html:radio property="attributes.holdSoldOption" value='<%= Constants.HOLDSOLD_ALL_SAMPLES %>' />
	            <bean:message key="library.ss.query.attributes.holdsold.showall"/>
	            </span><br>
	          </td>
	          <td>&nbsp;</td>
	        </tr>
			<tr class="white">
	          <td>&nbsp;</td>
	          <td style="padding-top: 0.5em;">
	            <html:radio property="attributes.holdSoldOption" value="<%= Constants.HOLDSOLD_USER_CASE %>" />
	          </td>
	          <td style="padding-top: 0.5em;">
   	          	<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.attributes.holdsold.usercase.comment"/>',FIXX,<%=overlibAdj%>);">
	            <bean:message key="library.ss.query.attributes.holdsold.usercase"/>
	            </span><br>
	          </td>
	          <td>
	            <html:radio property="attributes.holdSoldOption" value="<%= Constants.HOLDSOLD_ACCOUNT_CASE %>" />
	          </td>
	          <td>  
   	          	<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.attributes.holdsold.accountcase.comment"/>',FIXX,<%=overlibAdj%>);"> 
	            <bean:message key="library.ss.query.attributes.holdsold.accountcase"/>
	            </span><br>
	          </td>
	          <td></td>
	          </tr>
	          
	        <tr class="white">
	          <td>&nbsp;</td>
	          <td style="padding-top: 0.5em;">  
	            <html:radio property="attributes.holdSoldOption" value="<%= Constants.HOLDSOLD_USER_CASE_NOT %>" />
	          </td>
	          <td style="padding-top: 0.5em;">
   	          	<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.attributes.holdsold.usercasenot.comment"/>',FIXX,<%=overlibAdj%>);">
	            <bean:message key="library.ss.query.attributes.holdsold.usercasenot"/>
	            </span>
	          </td>
	          <td>  
	            <html:radio property="attributes.holdSoldOption" value="<%= Constants.HOLDSOLD_ACCOUNT_CASE_NOT %>" />
	          </td>
	          <td>
   	          	<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.attributes.holdsold.accountcasenot.comment"/>',FIXX,<%=overlibAdj%>);">  
	            <bean:message key="library.ss.query.attributes.holdsold.accountcasenot"/>
	            </span>
	          </td>
	          <td></td>
	        </tr>
	      </tbody>
	    </table>
    </bigr:hasPrivilege>
    <bigr:notHasPrivilege priv="<%=SecurityInfo.PRIV_HOLDSOLD_ACCOUNT%>">
	    <bigr:hasPrivilege priv="<%=SecurityInfo.PRIV_HOLDSOLD_USER%>">
			<table border="0" cellspacing="0" cellpadding="0" vspace="0" class="foreground-small" width="100%">
			  <col width="5%">
			  <col width="2%">
			  <col width="43%">
			  <col width="2%">
			  <col width="43%">
			  <col width="5%">
		      <tbody>
		        
		        <tr class="white">
		          <td>&nbsp;</td>
		          <td colspan="4" style="padding-top: 0.5em;">
		            <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.attributes.holdsold.comment"/>');"><bean:message key="library.ss.query.attributes.holdsold.label"/></span><br>
		          </td>
	          	  <td>&nbsp;</td>
		        </tr>
		        <tr class="white">
		          <td>&nbsp;</td>
		          <td colspan="4">
		          	<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.attributes.holdsold.showall.comment"/>');">
		            <html:radio property="attributes.holdSoldOption" value='<%= Constants.HOLDSOLD_ALL_SAMPLES %>' />
		            <bean:message key="library.ss.query.attributes.holdsold.showall"/>
		            </span><br>
		          </td>
	          	  <td>&nbsp;</td>
		        </tr>
				<tr class="white">
		          <td>&nbsp;</td>
		          <td style="padding-top: 0.5em;">
		            <html:radio property="attributes.holdSoldOption" value="<%= Constants.HOLDSOLD_USER_CASE %>" />
		          </td>
		          <td style="padding-top: 0.5em;">
       	          	<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.attributes.holdsold.usercase.comment"/>',FIXX,<%=overlibAdj%>);">
		            <bean:message key="library.ss.query.attributes.holdsold.usercase"/>
		            </span><br>
		          </td>
		          <td>
		          </td>
		          <td>  
		          </td>
		          <td></td>
		          </tr>
		          
		        <tr class="white">
		          <td>&nbsp;</td>
		          <td style="padding-top: 0.5em;">  
		            <html:radio property="attributes.holdSoldOption" value="<%= Constants.HOLDSOLD_USER_CASE_NOT %>" />
		          </td>
		          <td style="padding-top: 0.5em;">
       	          	<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.attributes.holdsold.usercasenot.comment"/>',FIXX,<%=overlibAdj%>);">
		            <bean:message key="library.ss.query.attributes.holdsold.usercasenot"/>
		            </span>
		          </td>
		          <td>  
		          </td>
		          <td>  
		          </td>
		          <td></td>
		        </tr>
		      </tbody>
		    </table>	    
	    </bigr:hasPrivilege>
    </bigr:notHasPrivilege>
        
	<table border="0" cellspacing="0" cellpadding="0" vspace="0" class="foreground-small" width="100%">
	  <col width="5%">
	  <col width="25%">
	  <col width="25%">
	  <col width="25%">
	  <col width="15%">
	  <col width="5%">
  		<tbody>

		<%// -------------------------- Attributes.separator ------------------------ %>
			<tr>
				<td style="padding: 1em 0 1em 1em;" colspan="5">
					<hr style="border: 1px solid #336699; height: 1px;">
				</td>
			<td>&nbsp;</td>
			</tr>		
			<!-- Attributes.sample -->
			<tr class="white">
				<td colspan="6" style="padding: 0.25em 0 1em 1em;">
					<b><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.attributes.title.sample.comment"/>');"><bean:message key="library.ss.query.attributes.title.sample.label"/></span></b>
				</td>
			</tr>

<% if (ApiProperties.isEnabledLegacy()) { %>		
		<bigr:isInRole role1="<%=SecurityInfo.ROLE_SYSTEM_OWNER%>" role2="<%=SecurityInfo.ROLE_DI%>">
			<tr>
				<td>&nbsp;</td>
				<td valign="top" nowrap>			
					<bigr:isInRole role1="<%=SecurityInfo.ROLE_DI%>" role2="<%=SecurityInfo.ROLE_SYSTEM_OWNER%>">
					<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.attributes.restricted.comment"/>',FIXX,<%=overlibAdj%>);"><bean:message key="library.ss.query.attributes.restricted.label"/></span><br>
					</bigr:isInRole>
				    <bigr:isInRole role1="<%=SecurityInfo.ROLE_DI%>">
					&nbsp;<html:radio property="attributes.restricted" value="<%= Constants.ANY %>" /><bean:message key="library.ss.query.attributes.restricted.all"/>
					&nbsp;<html:radio property="attributes.restricted" value="<%= Constants.RESTRICTED_MIUR %>" /><bean:message key="library.ss.query.attributes.restricted.miur"/>
					</bigr:isInRole>
					<bigr:isInRole role1="<%=SecurityInfo.ROLE_SYSTEM_OWNER%>">
					&nbsp;<html:radio property="attributes.restricted" value="<%= Constants.ANY %>" /><bean:message key="library.ss.query.attributes.restricted.all"/>
					&nbsp;<html:radio property="attributes.restricted" value="<%= Constants.RESTRICTED_R %>" /><bean:message key="library.ss.query.attributes.restricted.r"/>
					&nbsp;<html:radio property="attributes.restricted" value="<%= Constants.RESTRICTED_U %>" /><bean:message key="library.ss.query.attributes.restricted.u"/>
					</bigr:isInRole>
				</td>
				<td colspan="4">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="6">&nbsp;</td>
			</tr>
		</bigr:isInRole>
<% } else { %>
		<html:hidden property="attributes.restricted" value="<%= Constants.ANY %>"/>
<% } %>
		<!-- Attributes.localOnly -->
		<% if (boolStorageLocation) { %>
			<html:hidden property="attributes.localOnly"/>
			<tr>
				<td>&nbsp;</td>
				<td valign="top" nowrap>
					<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.attributes.storageLocation.comment"/>',FIXX,<%=overlibAdj%>);"><bean:message key="library.ss.query.attributes.storageLocation.label"/></span><br>	
					<input type="checkbox" name ="localOnlyCheckbox" onclick="updateHiddenField();"/>Show Local Samples Only<br>
				</td>
			</tr>
			<% } %>
		<tr>
			<td>&nbsp;</td>
			<% 
				boolean allLogicalRepositoriesSelected = "true".equalsIgnoreCase(qform.getAttributes().getAllLogicalRepositories()); 
				String checked;
				if (allLogicalRepositoriesSelected) {
					checked = "checked";
				}
				else {
					checked = "";
				}
				//determine which logical repository legal value set to use.  If the BMS Y/N value isn't
				//relevant then use all logical repositories, otherwise use whichever one applies to the
				//BMS value
				String logicalRepositories = "attributes.logicalRepositoryOptionsAll";
				boolean bmsSelected;
				if (ApiProperties.isEnabledLegacy()) {
				  logicalRepositories = "attributes.logicalRepositoryOptionsAll";
				}
				else if (showBMSControl) {
					bmsSelected = FormLogic.DB_YES.equalsIgnoreCase(qform.getAttributes().getBms_YN());
					if (bmsSelected) {
						logicalRepositories = "attributes.logicalRepositoryOptionsBMS";
						}
					else {
						logicalRepositories = "attributes.logicalRepositoryOptionsNonBMS";
					}
				}
			%>
	    <% 	
	    	if (showBMSControl) { 
	    %>
				  <td valign="top" nowrap style="padding-top: 0.5em;">
			          <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.attributes.bms_yn.comment"/>');"><bean:message key="library.ss.query.attributes.bms_yn.label"/></span><br>
			          &nbsp;<html:radio property="attributes.bms_YN" value="<%= FormLogic.DB_YES %>" onclick="populateLogicalRepositoryControls(this.value);"/><bean:message key="library.ss.query.attributes.bms.yes"/><br>
			          &nbsp;<html:radio property="attributes.bms_YN" value="<%= FormLogic.DB_NO %>" onclick="populateLogicalRepositoryControls(this.value);"/><bean:message key="library.ss.query.attributes.bms.no"/><br>          
			      </td>
			<%  
				} else { 
			%>
				<html:hidden property="attributes.bms_YN" value="" />		
			<%  
				}
			%>
			<td valign="top" nowrap style="padding-top: 0.5em;">
				<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.attributes.logicalRepository.comment"/>',FIXX,<%=overlibAdj%>);"><bean:message key="library.ss.query.attributes.logicalRepository.label"/></span><br>
				<input type="checkbox" name="allLogicalRepositoriesDisplay" <%=checked%> onclick="toggleLogicalRepositoryControls();"/>All<br>
                <html:hidden name="queryForm" property="attributes.allLogicalRepositories"/>
                <bigr:selectList
                  style="font-size:xx-small;"
                  size="4"
                  multiple="true"
                  name="queryForm"
                  property="attributes.logicalRepositoryList"
                  legalValueSetName="queryForm"
                  legalValueSetProperty="<%= logicalRepositories %>"
                  disabled="<%= allLogicalRepositoriesSelected %>"/>
            </td>
	      	<% 	
	      		if (!showBMSControl) { 
	      	%>
				<td>&nbsp;</td>
			<%  
				}
			%>
		    <td colspan="3">&nbsp;</td>
		</tr>
     
        <!--  Display date of Collection -->
        
		<tr>
		<td></td>
		<td>
		Date Of Collection:<br/> 
		From: <html:text  name="queryForm" property="attributes.dateOfCollectionFrom" size="10"/>&nbsp;To:<html:text  name="queryForm" property="attributes.dateOfCollectionTo" size="10"/><br>
		</td>
		</tr>
		<%// -------------------------- Attributes.separator before QC status ------------------------ %>
		<tr>
			<td style="padding: 1em 0 1em 1em;" colspan="5">
				<hr style="border: 1px solid #336699; height: 1px;">
			</td>
			<td>&nbsp;</td>
		</tr>


		<!-- Attributes.sample -->
        <tr class="white">
        	<td colspan="6" style="padding: 0.25em 0 1em 1em;">
        		<b><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.attributes.title.tissue.comment"/>',FIXX,<%=overlibAdj%>);"><bean:message key="library.ss.query.attributes.title.tissue.label"/></span></b>
        	</td>
        </tr>
		        
		<tr class="white">
			<td>&nbsp;</td>
			<td valign="top" nowrap>
				<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.sampletype.comment"/>',FIXX,<%=overlibAdj%>);">
				  <bean:message key="library.ss.query.sampletype.label"/>
				</span><br>
<%
				boolean allSampleTypesSelected = "true".equalsIgnoreCase(qform.getAttributes().getAllSampleTypes()); 
%>
				<input type="checkbox" name="allSampleTypes" <%=(allSampleTypesSelected) ? "checked" : ""%> onclick="toggleSampleTypesControls();"/>All<br>
                <html:hidden name="queryForm" property="attributes.allSampleTypes"/>
                <bigr:selectList
                  style="font-size:xx-small;"
                  size="4"
                  multiple="true"
                  name="queryForm"
                  property="attributes.sampleType"
                  legalValueSetName="queryForm"
                  legalValueSetProperty="attributes.sampleTypeList"
                  disabled="<%= allSampleTypesSelected %>"/>
			</td>
			<td colspan="3">
<% if (ApiProperties.isEnabledLegacy()) { %>       
				<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.attributes.received.comment"/>',FIXX,<%=overlibAdj%>);"><bean:message key="library.ss.query.attributes.received.label"/></span>
				<br/>
				&nbsp;&nbsp;<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.attributes.received.start.comment"/>');"><bean:message key="library.ss.query.attributes.received.start.label"/></span>
				<html:text property="attributes.recvDateStart" maxlength="10" size="12" readonly="true"/><span class="fakeLink" onclick="openCalendar('attributes.recvDateStart')"><html:img page="/images/show-calendar.gif" border="0"/></span>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="javascript:clearDateField('attributes.recvDateStart')">Clear <bean:message key="library.ss.query.attributes.received.start.label"/></a>
				<br/>
				&nbsp;&nbsp;<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.attributes.received.end.comment"/>');"><bean:message key="library.ss.query.attributes.received.end.label"/></span>&nbsp;&nbsp;
				<html:text property="attributes.recvDateEnd" maxlength="10" size="12" readonly="true"/><span class="fakeLink" onclick="openCalendar('attributes.recvDateEnd')"><html:img page="/images/show-calendar.gif" border="0"/></span>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="javascript:clearDateField('attributes.recvDateEnd')">Clear <bean:message key="library.ss.query.attributes.received.end.label"/></a>
<% } else { %>       
&nbsp;
<% } %>       
			</td>
			<td>&nbsp;</td>
		</tr>
		<tr><td colspan="6">&nbsp;</td></tr>
		<bigr:notIsInRole role1="<%=SecurityInfo.ROLE_SYSTEM_OWNER%>">
		<tr>
		  <td>&nbsp;</td>
		  <td colspan="4">
			<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.attributes.pv.comment"/>');"><bean:message key="library.ss.query.attributes.pv.label"/></span><br>
			&nbsp;<html:radio property="attributes.pathVerifiedStatus" value="any" />
			<bean:message key="library.ss.query.attributes.pv.all" />
			&nbsp;<html:radio property="attributes.pathVerifiedStatus" value="Y" />
			<bean:message key="library.ss.query.attributes.pv.only" />
            <%
              if (showNotPVed) {
            %>
			&nbsp;<html:radio property="attributes.pathVerifiedStatus" value="N" />
			<bean:message key="library.ss.query.attributes.pv.notPVed" />
            <%
              }
            %>
		  </td>
		  <td>&nbsp;</td>
		</tr>
		</bigr:notIsInRole>
    <bigr:isInRole role1="<%=SecurityInfo.ROLE_SYSTEM_OWNER%>">
<% if (ApiProperties.isEnabledLegacy()) { %>       
		<tr>
			<td>&nbsp;</td>
	    	<td colspan="4">
    		<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.attributes.section.qcstatus.comment"/>');"><bean:message key="library.ss.query.attributes.section.qcstatus.label"/></span>
    		</td>
			<td>&nbsp;</td>
    </tr>        
		<tr>
			<td>&nbsp;</td>
	        <td colspan="5">			
			<html:multibox property="attributes.qcStatus" onclick="clearQcStatuses()" value="<%= Constants.ANY %>" />
			<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.attributes.pv.any.comment"/>');">
			<bean:message key="library.ss.query.any"/> </span> &nbsp;
			
			<html:multibox property="attributes.qcStatus" onclick="clearQcStatuses()" value="<%= Constants.QC_NOTVER %>" />
			<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.attributes.qc.notpv.comment"/>');">
			<bean:message key="library.ss.query.attributes.qc.notpv"/> </span> &nbsp;
			
			<html:multibox property="attributes.qcStatus" onclick="clearQcStatuses()" value="<%= Constants.QC_VER_ONLY %>" />
			<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.attributes.qc.pvonly.comment"/>');">
			<bean:message key="library.ss.query.attributes.qc.pvonly"/> </span> &nbsp;
			
			<html:multibox property="attributes.qcStatus" onclick="clearQcStatuses()" value="<%= Constants.QC_REL_ONLY %>" />
			<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.attributes.qc.releaseonly.comment"/>');">
			<bean:message key="library.ss.query.attributes.qc.releaseonly"/> </span> &nbsp;
			
			<html:multibox property="attributes.qcStatus" onclick="clearQcStatuses()" value="<%= Constants.QC_POST %>" />
			<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.attributes.qc.posted.comment"/>');">
			<bean:message key="library.ss.query.attributes.qc.posted"/> </span> &nbsp;
			
			<html:multibox property="attributes.qcStatus" onclick="clearQcStatuses()" value="<%= Constants.QC_PULL %>" />
			<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.attributes.qc.pulled.comment"/>');">
			<bean:message key="library.ss.query.attributes.qc.pulled"/> </span> 

			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td colspan="5"> 
				&nbsp;
				<html:radio property="attributes.qcInProcess" value = "<%= Constants.ANY %>" /> 
				<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.attributes.qc.any.comment"/>');">	
					<bean:message key="library.ss.query.any"/> 
				</span>
				&nbsp;
				<html:radio property="attributes.qcInProcess" value = "<%= Constants.QC_INPROCESS %>" /> 
				<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.attributes.qc.inproc.comment"/>');">
					<bean:message key="library.ss.query.attributes.qc.inproc"/> 
				</span>
				&nbsp;
				<html:radio property="attributes.qcInProcess" value = "<%= Constants.QC_INPROCESS_NOT %>" /> 
				<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.attributes.qc.notinproc.comment"/>');">
					<bean:message key="library.ss.query.attributes.qc.notinproc"/> 
				</span>
			</td>
		</tr>
<% } %>   
		<%// -------------------------- Attributes.separator ------------------------ %>
		<tr>
			<td style="padding: 1em 0 1em 1em;" colspan="5">
				<hr style="border: 1px solid #336699; height: 1px;">
			</td>
			<td>&nbsp;</td>
		</tr>
				
        <!-- Attributes.ardais -->		
        <tr class="white">
        	<td colspan="6" style="padding: 0.25em 0 1em 1em;">
        		<b><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.attributes.title.ardais.comment"/>');"><bean:message key="library.ss.query.attributes.title.ardais.label"/></span></b>
        	</td>
        </tr>
        <tr class="white">
          <td>&nbsp;</td>
          <td valign="top" nowrap>
          <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.attributes.di.comment"/>');"><bean:message key="library.ss.query.attributes.di.label"/></span><br>
          &nbsp;<html:radio property="attributes.donorInst" value="<%= Constants.ANY %>" /><bean:message key="library.ss.query.attributes.restricted.all"/><br>
          <logic:iterate name="queryForm" property="attributes.donorInstList" id="donor" type="com.ardais.bigr.iltds.databeans.LabelValueBean">
            &nbsp;<html:radio property="attributes.donorInst" value='<%= (String) donor.getValue() %>'/><%= donor.getLabel() %><br>
          </logic:iterate>
          
          </td>
          <% 	if (ResultsHelper.TX_TYPE_SAMP_SEL.equals(txType) && ApiProperties.isEnabledLegacy()) { %>
			      <td valign="top" colspan="3" nowrap>
				      <html:checkbox property="attributes.excludeImplicitRNAFilters" value="true">
			  	      <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.attributes.excludeImplicitRNAFilters.comment"/>');"><bean:message key="library.ss.query.attributes.excludeImplicitRNAFilters.label"/></span>
				      </html:checkbox>
			      </td>
			    <% } %>
      		<td colspan="3">
      		</td>
        </tr>
        </bigr:isInRole>

      </tbody>
		</table>
	</div>

