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
<%
// The Tissue tab of the SampleSelectionQuery JSP now contains all the fields formerly on
// the Appearance tab of the SampleSelectionQuery JSP.

boolean showOtherControl = false;

String txType = request.getParameter("txType");
SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);

if ((securityInfo.isInRoleSystemOwner()) ||
 (securityInfo.isInRoleDi() && ResultsHelper.TX_TYPE_SAMP_REQUEST.equals(txType))) {
showOtherControl = true;
}

%>

	<!-- Tissue tab -->
	<div id="tissueDiv" class="libraryTabContents" style="display: none;">
		<table border="0" cellspacing="0" cellpadding="0" vspace="0" width="100%" class="foreground-small">
			<col width="5%">
			<col width="45%"> 
			<col width="45%">
			<col width="5%"> 
			<tbody>

			<!-- tissue: title bar -->
	    <tr id="tissueTitle">
	      <td colspan="4">
	        <table border="0" cellspacing="0" cellpadding="3" class="foreground-small" width="100%">
          	<col width="90%">
		      <col width="5%">
			  <col width="5%">
						<tbody>
            	<td id="tissueTitle1" class="libraryTabTitle">
                <b>
                	<bean:message key="library.ss.query.tissue.tabname"/> - 
                  <bean:message key="library.ss.query.tissue.title.label"/>
                </b>
              </td>
              <td id="tissueTitle3" class="libraryTabTitle">
	              	
	              	<html:button property="clearQueryButton" styleClass="libraryButtons" onclick="clrQuery('tissue')"
							onmouseout="return nd()" onmouseover="return overlib('Resets all values on this tab to default values')">
								<bean:message key="library.ss.query.button.clearSection"/>
							</html:button>
	            	</td>
            	<td id="tissueTitle2" class="libraryTabTitle">
              	<a href="#top">Top</a>
            	</td>
            </tbody>
	        </table>
	      </td>
	    </tr>


			<!-- tissue: whitespace between gross and sample -->
			<tr>
				<td colspan="4" style="padding-top: 2em;">&nbsp;</td>
			</tr>

			<!-- tissue: sample links -->
			<tr class="white">
				<td>&nbsp;</td>
        <td style="padding: 0.25em 1em 0 0;">
        	<b><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.tissue.tissueorigin.title.comment"/>');"><bean:message key="library.ss.query.tissue.tissueorigin.title.label"/></span></b>
      	</td>
				<td style="padding: 0.25em 0 0 1em;">
					<b><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.tissue.tissuefinding.title.comment"/>');"><bean:message key="library.ss.query.tissue.tissuefinding.title.label"/></span></b>
				</td>
				<td>&nbsp;</td>
			</tr>

			<!-- tissue: sample lists and radio buttons -->
			<tr class="white">
				<td>&nbsp;</td>
				<td style="padding: 0.25em 2em 0 0;">
          <table border="0" cellspacing="0" cellpadding="0" width="100%" style="margin: 0 0 0.25em 0;" class="foreground-small">
	        	<tr>
	        		<td>
								<html:radio property="tissue.tissueOriginNot" value="false" onclick="setNot('tissue.tissueOriginNot', 'tissue.tissueOriginContainsLabel');"/>
								<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.tissue.is.comment"/>');"><bean:message key="library.ss.query.tissue.is.label"/></span>&nbsp;&nbsp;&nbsp;&nbsp;
								<html:radio property="tissue.tissueOriginNot" value="true" onclick="setNot('tissue.tissueOriginNot', 'tissue.tissueOriginContainsLabel');"/>
								<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.tissue.isnot.comment"/>');"><bean:message key="library.ss.query.tissue.isnot.label"/></span><br>
	        		</td>
	        		<td align="right">
	        			<html:link href="#" onclick="openTissueHierarchy('libraryTissueOrigin');">
				          Select
			          </html:link>
	        		</td>
	        	</tr>
          </table>
					<ul id="libraryTissueOrigin" class="compactListBox" style="cursor: hand;" onclick="openTissueHierarchy('libraryTissueOrigin');">					
						<logic:iterate name="queryForm" property="tissue.tissueOriginLabel" id="label">
							<li><bean:write name="label"/></li>
						</logic:iterate>
					</ul>
				</td>
				<td style="padding: 0.25em 1em 0 1em;">
          <table border="0" cellspacing="0" cellpadding="0" width="100%" style="margin: 0 0 0.25em 0;" class="foreground-small">
	        	<tr>
	        		<td>
								<html:radio property="tissue.tissueFindingNot" value="false" onclick="setNot('tissue.tissueFindingNot', 'tissue.tissueFindingContainsLabel');"/>
								<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.tissue.is.comment"/>');"><bean:message key="library.ss.query.tissue.is.label"/></span>&nbsp;&nbsp;&nbsp;&nbsp;
								<html:radio property="tissue.tissueFindingNot" value="true" onclick="setNot('tissue.tissueFindingNot', 'tissue.tissueFindingContainsLabel');"/>
								<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.tissue.isnot.comment"/>');"><bean:message key="library.ss.query.tissue.isnot.label"/></span><br>
	        		</td>
	        		<td align="right">
	        			<html:link href="#" onclick="openTissueHierarchy('libraryTissueFinding');">
				          Select
			          </html:link>
	        		</td>
	        	</tr>
          </table>
					<ul id="libraryTissueFinding" class="compactListBox" style="cursor: hand;" onclick="openTissueHierarchy('libraryTissueFinding');">
						<logic:iterate name="queryForm" property="tissue.tissueFindingLabel" id="label">
							<li><bean:write name="label"/></li>
						</logic:iterate>
					</ul>
				</td>
				<td>&nbsp;</td>
			</tr>						

			<!-- tissue: sample or contains input -->
			<tr class="white">
				<td>&nbsp;</td>
				<td style="padding: 0.25em 1em 0 0;">
					<%--span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.tissue.contains.comment"/>');" id="tissue.tissueOriginContainsLabel"><bean:message key="library.ss.query.tissue.contains.label"/></span>
					<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.tissue.containsnot.comment"/>');" id="tissue.tissueOriginContainsLabelNot" style="display: none;"><bean:message key="library.ss.query.tissue.containsnot.label"/></span>
					<br>
					<html:text property="tissue.tissueOriginContains" size="20" /--%>
				</td>
				<td style="padding: 0.25em 0 0 1em;">
					<%-- span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.tissue.contains.comment"/>');" id="tissue.tissueFindingContainsLabel"><bean:message key="library.ss.query.tissue.contains.label"/></span>
					<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.tissue.containsnot.comment"/>');" id="tissue.tissueFindingContainsLabelNot" style="display: none;"><bean:message key="library.ss.query.tissue.containsnot.label"/></span>
					<br>
					<html:text property="tissue.tissueFindingContains" size="20" /--%>
				</td>
				<td>&nbsp;</td>
				</tr>
				<tr class="white">
				  <td>&nbsp;</td>
				  <td > <jsp:include page="/hiddenJsps/dataImport/attr/tissue.jsp?fieldId=Origin" flush="true"/></td>
				  
				  <td style="padding-left: 10px;"> <jsp:include page="/hiddenJsps/dataImport/attr/tissue.jsp?fieldId=Finding" flush="true"/></td>
				  <td>&nbsp;</td>
				</tr>
				<tr class="white">
				  <td>&nbsp;</td>
				  <td > <input type="button" value="Add" onClick="selectChoice($('sampleData.tissue&Origin'), '<%=request.getContextPath()%>','libraryTissueOrigin', '<%=request.getParameter("txType") %>')"/></td>
				  <td style="padding-left: 10px;"> <input type="button" value="Add" onClick="selectChoice($('sampleData.tissue&Finding'), '<%=request.getContextPath()%>','libraryTissueFinding', '<%=request.getParameter("txType") %>')"/></td>				 
				  <td>&nbsp;</td>
				</tr>
			</tbody>
		</table>
		<br><br>
	<!-- everything below was formerly on the Appearance tab -->
		<table border="0" cellspacing="0" cellpadding="0" vspace="0" width="100%" class="foreground-small">
			<col width="5%">
			<col width="10%">
			<col width="10%">
			<col width="10%"> 
			<col width="12%">
			<col width="10%">
			<col width="40%">
			<col width="5%">
			<tbody>

			<!-- appearance: appearance -->
	    <tr class="white">
	    	<td colspan="8" style="padding: 1em;">
	    		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.appearance.comment"/>');"><bean:message key="library.ss.appearance.label"/></span></b>
	    	</td>
	    </tr>

<% if (ApiProperties.isEnabledLegacy()) { %>
			<tr class="white">
				<td>&nbsp;</td>
	
				<!-- appearance: any -->
				<td rowspan="2" valign="center" nowrap>
					<html:multibox property="appearance.appearanceBest" onclick="clearAppearances();updateGrossAppearance();" value="<%= Constants.ANY %>" />
					<bean:message key="library.ss.query.appearance.appearance.any"/>
				</td>

				<!-- appearance: microscopic -->
				<td align="right" style="padding-right: 0.5em;" nowrap>
					<b><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.appearance.microscopic.comment"/>');"><bean:message key="library.ss.query.appearance.microscopic.label"/></span></b>
				</td>
				<td style="padding-bottom: 0.5em;" nowrap>
					<html:multibox property="appearance.appearanceBest" onclick="clearAppearances();" value="<%= Constants.APPEARANCE_MICRO_NORMAL %>" />
					<bean:message key="library.ss.query.appearance.microscopic.normal"/>
				</td>
				<td style="padding-bottom: 0.5em;" nowrap>
					<html:multibox property="appearance.appearanceBest" onclick="clearAppearances();" value="<%= Constants.APPEARANCE_MICRO_LESION %>" />
					<bean:message key="library.ss.query.appearance.microscopic.lesion"/>
				</td>
				<td style="padding-bottom: 0.5em;" nowrap>
					<html:multibox property="appearance.appearanceBest" onclick="clearAppearances();" value="<%= Constants.APPEARANCE_MICRO_TUMOR %>" />
					<bean:message key="library.ss.query.appearance.microscopic.tumor"/>
				</td>
				<%if (showOtherControl) {%>
				  <td style="padding-bottom: 0.5em;" nowrap>
					  <html:multibox property="appearance.appearanceBest" onclick="clearAppearances();" value="<%= Constants.APPEARANCE_MICRO_OTHER %>" />
					  <bean:message key="library.ss.query.appearance.microscopic.other"/>
				  </td>
				<%
				  } else {
				%>
				  <td>&nbsp;</td>
				<%
				  }
				%>
				<td>&nbsp;</td>

			</tr>

			<!-- appearance: gross -->
			<tr class="white">
				<td>&nbsp;</td>
				<td align="right" style="padding-right: 0.5em;" nowrap>
					<b><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.appearance.gross.comment"/>');"><bean:message key="library.ss.query.appearance.gross.label"/></span></b>
				</td>
				<td nowrap>
					<html:multibox property="appearance.appearanceBest" onclick="clearGrossAppearances();" value="<%= Constants.APPEARANCE_GROSS_NORMAL %>" />
					<bean:message key="library.ss.query.appearance.gross.normal"/>
				</td>
				<td nowrap>
					<html:multibox property="appearance.appearanceBest" onclick="clearGrossAppearances();" value="<%= Constants.APPEARANCE_GROSS_DISEASED %>" />
					<bean:message key="library.ss.query.appearance.gross.diseased"/>
				</td>
			    <td colspan="2" nowrap>
					  <html:multibox property="appearance.appearanceBest" onclick="clearGrossAppearances();" value="<%= Constants.APPEARANCE_GROSS_UNKNOWN %>" />
					  <bean:message key="library.ss.query.appearance.gross.unknown"/>
				</td>
				<td>&nbsp;</td>				
			</tr>
			

			<tr>
					<td style="padding: 1em 0 0em 1em;" colspan="7">
						<hr style="border: 1px solid #336699; height: 1px;">
					</td>
					<td>&nbsp;</td>
				</tr>			
			
<% }  else { %>	

			<!-- appearance: gross -->
			<tr class="white">
				<td align="right" style="padding-right: 0.5em;" nowrap>
					<b><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.appearance.gross.comment"/>');"><bean:message key="library.ss.query.appearance.gross.label"/></span></b>
				</td>
				<!-- appearance: any -->
				<td>
					<html:multibox property="appearance.appearanceBest" onclick="clearAppearances();updateGrossAppearance();" value="<%= Constants.ANY %>" />
					<bean:message key="library.ss.query.appearance.appearance.any"/>
				</td>				
				<td>
					<html:multibox property="appearance.appearanceBest" onclick="clearGrossAppearances();" value="<%= Constants.APPEARANCE_GROSS_NORMAL %>" />
					<bean:message key="library.ss.query.appearance.gross.normal"/>
				</td>
				<td>
					<html:multibox property="appearance.appearanceBest" onclick="clearGrossAppearances();" value="<%= Constants.APPEARANCE_GROSS_DISEASED %>" />
					<bean:message key="library.ss.query.appearance.gross.diseased"/>
				</td>
			  <td>
					  <html:multibox property="appearance.appearanceBest" onclick="clearGrossAppearances();" value="<%= Constants.APPEARANCE_GROSS_UNKNOWN %>" />
					  <bean:message key="library.ss.query.appearance.gross.unknown"/>
				</td>		
			</tr>


<% } %>
			
			


<% if (ApiProperties.isEnabledLegacy()) { %>
				
	    	<tr>
					<td style="padding: 0em 0 0em 1em;" colspan="7">
						<b>Please specify either Appearance above or Composition below, not both. Case-matched pairs of diseased/normal samples can be seen by choosing both Normal and Tumor above. To use Composition specify Normal or Tumor below, but not both.</b>
					</td>
					<td>&nbsp;</td>
				</tr>
				
				
				<tr>
					<td style="padding: 0em 0 0em 1em;" colspan="7">
						<hr style="border: 1px solid #336699; height: 1px;">
					</td>
					<td>&nbsp;</td>
				</tr>
			

			<!-- appearance: composition -->
	    <tr class="white">
	    	<td colspan="8" style="padding: 2em 0 0 1em;">
	    		<b><span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.appearance.composition.comment"/>');"><bean:message key="library.ss.query.appearance.composition.label"/></span></b>
	    	</td>
	    </tr>

			<tr class="white">
				<td>&nbsp;</td>
				<td colspan="6">
					<table border="0" cellspacing="3" cellpadding="3" vspace="0" class="foreground-small">
						<tbody>
							<tr class="white">
								<td align="right" valign="center">
									<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.appearance.composition.normal.comment"/>');"><bean:message key="library.ss.query.appearance.composition.normal.label"/></span>
								</td>
								<td valign="center">
									&gt;= <html:select property="appearance.compNrmFrom"><html:options property="appearance.valueList"/></html:select>&nbsp;&nbsp;and&nbsp;
									&lt;= <html:select property="appearance.compNrmTo"><html:options property="appearance.valueList"/></html:select>
								</td>
							</tr>
							<tr class="white">
								<td align="right" valign="center">
									<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.appearance.composition.lesion.comment"/>');"><bean:message key="library.ss.query.appearance.composition.lesion.label"/></span>
								</td>
								<td valign="center">
									&gt;= <html:select property="appearance.compLsnFrom"><html:options property="appearance.valueList"/></html:select>&nbsp;&nbsp;and&nbsp;
									&lt;= <html:select property="appearance.compLsnTo"><html:options property="appearance.valueList"/></html:select>
								</td>
							</tr>
							<tr class="white">
								<td align="right" valign="center">
									<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.appearance.composition.tumor.comment"/>');"><bean:message key="library.ss.query.appearance.composition.tumor.label"/></span>
								</td>
								<td valign="center">
									&gt;= <html:select property="appearance.compTmrFrom"><html:options property="appearance.valueList"/></html:select>&nbsp;&nbsp;and&nbsp;
									&lt;= <html:select property="appearance.compTmrTo"><html:options property="appearance.valueList"/></html:select>
								</td>
							</tr>
							<tr class="white">
								<td align="right" valign="center">
									<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.appearance.composition.tcs.comment"/>');"><bean:message key="library.ss.query.appearance.composition.tcs.label"/></span>
								</td>
								<td valign="center">
									&gt;= <html:select property="appearance.compTcsFrom"><html:options property="appearance.valueList"/></html:select>&nbsp;&nbsp;and&nbsp;
									&lt;= <html:select property="appearance.compTcsTo"><html:options property="appearance.valueList"/></html:select>
								</td>
							</tr>
							<tr class="white">
								<td align="right" valign="center">
									<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.appearance.composition.tas.comment"/>');"><bean:message key="library.ss.query.appearance.composition.tas.label"/></span>
								</td>
								<td valign="center">
									&gt;= <html:select property="appearance.compTasFrom"><html:options property="appearance.valueList"/></html:select>&nbsp;&nbsp;and&nbsp;
									&lt;= <html:select property="appearance.compTasTo"><html:options property="appearance.valueList"/></html:select>
								</td>
							</tr>
							<tr class="white">
								<td align="right" valign="center">
									<span onmouseout="return nd();" onmouseover="return overlib('<bean:message key="library.ss.query.appearance.composition.necrosis.comment"/>');"><bean:message key="library.ss.query.appearance.composition.necrosis.label"/></span>
								</td>
								<td valign="center">
									&gt;= <html:select property="appearance.compNecFrom"><html:options property="appearance.valueList"/></html:select>&nbsp;&nbsp;and&nbsp;
									&lt;= <html:select property="appearance.compNecTo"><html:options property="appearance.valueList"/></html:select>
								</td>
							</tr>
						</tbody>
					</table>
				</td>
				<td>&nbsp;</td>
			</tr>
<% } %>
			</tbody>
		</table>
	</div>	
	
  