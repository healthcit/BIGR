<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.security.SecurityInfo" %> 
<%@ page import="com.ardais.bigr.util.WebUtils" %> 

<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%
// The ID tab of the SampleSelectionQuery JSP.
%>
<%
	SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);
    boolean showAliasPattern = false;
    if (securityInfo.isInRoleSystemOwnerOrDi()) {
      showAliasPattern = true;
    }
%>

<script type="text/javascript">
	function isCaseAvailableToUser(id){
	  // This could potentially be an issue because of the new 'CX' case prefix.
	  // It is unlikely that a DI will be restricted to LINKED CASES ONLY.
	  <%
		if (securityInfo.isHasPrivilege(SecurityInfo.PRIV_LINKED_CASES_ONLY)) {
	  %>
		  var linkedExpr = /^(CI|ci|Ci|cI)\d+$/;  
		  if(!linkedExpr.test(id)){
		    alert("You must enter the id of a linked case.");
		    return false;
		  }
	  <%
	  	}
	  %>
	  return true;
	}
	
	function isDonorAvailableToUser(id){
	  // This could potentially be an issue because of the new 'AX' donor prefix.
	  // It is unlikely that a DI will be restricted to LINKED CASES ONLY.
	  <%
		if (securityInfo.isHasPrivilege(SecurityInfo.PRIV_LINKED_CASES_ONLY)) {
	  %>
		  var linkedExpr = /^(AI|ai|Ai|aI)\d+$/;  
		  if(!linkedExpr.test(id)){
		    alert("You must enter the id of a linked donor.");
		    return false;
		  }
	  <%
	  	}
	  %>
	  return true;
	}
</script>

	<!-- ID tab -->
	<div id="idDiv" class="libraryTabContents" style="display: none;">
    <table border="0" cellspacing="0" cellpadding="0" class="foreground-small" width="100%">
      <col width="5%">
      <col width="15%">
      <col width="25%">
      <col width="50%"> 
      <col width="5%">
			<tbody>

				<!-- ID.title -->
		    <tr id="idTitle">
		      <td colspan="5">
		        <table border="0" cellspacing="0" cellpadding="0" class="foreground-small" width="100%">
		          <col width="90%">
				      <col width="5%"> 
				      <col width="5%"> 
							<tbody>
	            	<td id="idTitle1" class="libraryTabTitle">
									<b>
										<bean:message key="library.ss.query.id.tabname"/> -
										<bean:message key="library.ss.query.id.title.label"/>
									</b>
	    		      </td>
	    		      <td id="idTitle3" class="libraryTabTitle">
	              	
	              	<html:button property="clearQueryButton" styleClass="libraryButtons" onclick="clrQuery('id')"
							onmouseout="return nd()" onmouseover="return overlib('Resets all values on this tab to default values')">
								<bean:message key="library.ss.query.button.clearSection"/>
							</html:button>
	            	</td>
	            	<td id="idTitle2" class="libraryTabTitle">
			          	<a href="#top">Top</a>
	    		    	</td>
			        </tbody>
		        </table>
		      </td>
		    </tr>

				<!-- ID.donor -->
		    <tr class="white">
		      <td>&nbsp;</td>
		      <td valign="center" style="padding: 1em 0 1em 0;">
		        <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.donorid.comment"/>');"><bean:message key="library.ss.query.donorid.label"/></span><br>
		        <input type="text" name="id.donorId" size="14" maxlength="12"
		         onBlur="if (document.all['id.donorId'].value.length != 0) {
		        			if (!isValidDonorIdPrefix_Alert(document.all['id.donorId'].value, true) ||
		        				!isDonorAvailableToUser(document.all['id.donorId'].value)) {
		        		   		document.all['id.donorId'].value = '';
		        		   		document.all['id.donorId'].focus();
		        		   	}
		        		}"/><br>
		      </td>
		      <td align="center" valign="center" style="padding: 1em;">
		        <bigr:idlist property="id.donorIdsButton" type="DONOR_TYPE" idfrom="id.donorId" idlist="id.donorIds" limitValidation="true" style="FONT-SIZE:xx-small"/>
		      </td>
		      <td valign="center" style="padding: 1em 0 1em 0;">
		        <span onmouseover="return overlib('<bean:message key="library.ss.query.donoridlist.comment"/>',FIXX,100);" onmouseout="return nd();">
		          <bean:message key="library.ss.query.donoridlist.label"/>
		        </span>
                
                <br>
		        <html:select property="id.donorIds" size="4" multiple="true" style="width: 150px">
		          <logic:present name="queryForm" property="id.donorIds">
		            <html:options property="id.donorIds"/>
		          </logic:present>
		        </html:select>
		      </td>
		      <td>&nbsp;</td>
		    </tr>

				<!-- ID.ORspacer -->
				<tr class="white">
				  <td>&nbsp;</td>
				  <td>&nbsp;</td>
				  <td align="center" valign="center"><b>--- OR ---</b></td>
				  <td>&nbsp;</td>
				  <td>&nbsp;</td>
				</tr>
	
				<!-- ID.case -->
		    <tr class="white">
		      <td>&nbsp;</td>
		      <td valign="center" style="padding: 1em 0 1em 0;">
		        <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.caseid.comment"/>');"><bean:message key="library.ss.query.caseid.label"/></span>
                <br>
		        <input type="text" name="id.caseId" size="14" maxlength="12" 
		         onBlur="if (document.all['id.caseId'].value.length != 0) {
		        			if (!isValidCaseIdPrefix_Alert(document.all['id.caseId'].value, true) ||
		        				!isCaseAvailableToUser(document.all['id.caseId'].value)) {
		        		   		document.all['id.caseId'].value = '';
		        		   		document.all['id.caseId'].focus();
		        		   	}
		        		}"/><br>
		      </td>
		      <td align="center" valign="center" style="padding: 1em;">
		        <bigr:idlist property="id.caseIdsButton" type="CASE_TYPE" idfrom="id.caseId" idlist="id.caseIds" limitValidation="true" style="FONT-SIZE:xx-small"/>
		      </td>
		      <td valign="center" style="padding: 1em 0 1em 0;">
		        <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.caseidlist.comment"/>',FIXX,110);"><bean:message key="library.ss.query.caseidlist.label"/></span>
                <br>
		        <html:select property="id.caseIds" size="4" multiple="true" style="width: 150px">
		          <logic:present name="queryForm" property="id.caseIds">
		            <html:options property="id.caseIds"/>
		          </logic:present>
		        </html:select>
		      </td>
		      <td>&nbsp;</td>
		    </tr>
	
				<!-- ID.ORspacer -->
		    <tr class="white">
		      <td>&nbsp;</td>
		      <td>&nbsp;</td>
		      <td align="center" valign="center"><b>--- OR ---</b></td>
		      <td>&nbsp;</td>
		      <td>&nbsp;</td>
		    </tr>

				<!-- ID.sample -->
		    <tr class="white">
		      <td>&nbsp;</td>
		      <td valign="center" style="padding: 1em 0 1em 0;">
		        <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.sampleid.comment"/>');"><bean:message key="library.ss.sampleid.label"/></span><br>
		        <input type="text" name="id.itemId" size="14" maxlength="10" 
		         onBlur="if (document.all['id.itemId'].value.length != 0) {
		        			if (!isValidId_Alert(document.all['id.itemId'].value, ITEM_TYPE, true)) {
		        		   		document.all['id.itemId'].value = '';
		        		   		document.all['id.itemId'].focus();
		        		   	}
		        		}"/><br>
		      </td>
		      <td align="center" valign="center" style="padding: 1em;">
		        <bigr:idlist property="id.itemIdsButton" type="ITEM_TYPE" idfrom="id.itemId" idlist="id.itemIds" style="FONT-SIZE:xx-small"/>
		      </td>
		      <td valign="center" style="padding: 1em 0 1em 0;">
		        <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.sampleidlist.comment"/>',FIXX,110);"><bean:message key="library.ss.query.sampleidlist.label"/></span><br>
		        <html:select property="id.itemIds" size="4" multiple="true" style="width: 150px">
		          <logic:present name="queryForm" property="id.itemIds">
		            <html:options property="id.itemIds"/>
		          </logic:present>
		        </html:select>
		      </td>
		      <td>&nbsp;</td>
		    </tr>

		    <%
		      if (showAliasPattern) {
		    %>
				<!-- ID.ORspacer -->
		    <tr class="white">
		      <td>&nbsp;</td>
		      <td>&nbsp;</td>
		      <td align="center" valign="center"><b>--- OR ---</b></td>
		      <td>&nbsp;</td>
		      <td>&nbsp;</td>
		    </tr>
		    
		    <tr class="white">
		      <td>&nbsp;</td>
		      <td>&nbsp;</td>
		      <td align="center" valign="center">
		        Donors<html:checkbox property="id.findDonors"/>
		        Cases<html:checkbox property="id.findConsents"/>
		        Samples<html:checkbox property="id.findSamples"/>
		      </td>
		      <td>&nbsp;</td>
		      <td>&nbsp;</td>
		    </tr>

				<!-- ID.alias -->
		    <tr class="white">
		      <td>&nbsp;</td>
		      <td valign="center" style="padding: 1em 0 1em 0;">
		        <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.aliasPattern.comment"/>');"><bean:message key="library.ss.aliasPattern.label"/></span><br>
		        <input type="text" name="id.aliasPattern" size="14" maxlength="30"/><br>
		      </td>
		      <td align="center" valign="center" style="padding: 1em;">
		        <bigr:idlist property="id.aliasPatternButtons" type="IGNORE_TYPE" idfrom="id.aliasPattern" idlist="id.aliasPatterns" style="FONT-SIZE:xx-small"/>
		      </td>
		      <td valign="center" style="padding: 1em 0 1em 0;">
		        <span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.aliasPatternList.comment"/>',FIXX,110);"><bean:message key="library.ss.query.aliasPatternList.label"/></span><br>
		        <html:select property="id.aliasPatterns" size="4" multiple="true" style="width: 150px">
		          <logic:present name="queryForm" property="id.aliasPatterns">
		            <html:options property="id.aliasPatterns"/>
		          </logic:present>
		        </html:select>
		      </td>
		      <td>&nbsp;</td>
		    </tr>
		    <%
		      }
		    %>
	 		</tbody>
		</table>
	</div>
