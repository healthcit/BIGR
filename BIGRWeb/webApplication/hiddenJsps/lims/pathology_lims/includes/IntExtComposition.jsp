<%@ taglib uri='/tld/struts-logic' prefix='logic' %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>

<table cellpadding="0" cellspacing="0" border="0" class="background"
	width="100%">
	<tr>
		<td>
		<table id=features border="0" cellspacing="1" cellpadding="3"
			class="foreground-small" width="100%">
			<col width="20%">
			<col width="80%">
			<tr class="green" type="header">
				<td nowrap colspan=2>
				<div align="center"><b>External Comments</b></div>
				</td>
			</tr>
			<logic:present name="limsCreateEvaluationForm" property="externalFeaturesOptions">
				<logic:iterate name="limsCreateEvaluationForm" property="externalFeaturesOptions.iterator" id="legalValue">
					<tr class="white" type="header">
						<td align="center" nowrap><html:multibox
							name="limsCreateEvaluationForm" property="externalFeaturesList" tabindex="26" 
							value="<%= ((com.ardais.bigr.iltds.assistants.LegalValue)legalValue).getValue() %>" /></td>
						<td nowrap><bean:write name="legalValue" property="displayValue"/></td>
					</tr>
				</logic:iterate>
			</logic:present>
			</tr>
			<tr class="white" type="efc">
				<td nowrap colspan=2>
				<div align="center">
				<p><textarea name="addExternal" rows=10 cols=35 wrap=SOFT tabindex="27"
					style="overflow:visible"
					onKeyUp="return maxTextarea(<%= com.ardais.bigr.lims.helpers.LimsConstants.LIMS_NON_OCE_FEATURE_MAXLENGTH %>)";></textarea>
				</p>
				<p><input type="button" value="Add" tabindex="28"  onClick="addItem('ext', null)"></p>
				<p>
				</div>
				</td>
			</tr>
			<tr class="green" type="header">
				<td nowrap colspan=2>
				<div align="center"><b>Internal Quality Issues</b></div>
				</td>
			</tr>
			<logic:present name="limsCreateEvaluationForm" property="internalQualityIssuesOptions">
				<logic:iterate name="limsCreateEvaluationForm" property="internalQualityIssuesOptions.iterator" id="legalValue">
					<tr class="white" type="header">
						<td align="center" nowrap><html:multibox
							name="limsCreateEvaluationForm" property="internalFeaturesList" tabindex="29"
							value="<%= ((com.ardais.bigr.iltds.assistants.LegalValue)legalValue).getValue() %>" /></td>
						<td nowrap><bean:write name="legalValue" property="displayValue"/></td>
					</tr>
				</logic:iterate>
			</logic:present>
			<tr class="white" type="iqic">
				<td nowrap colspan=2>
				<div align="center">
				<p><textarea name="addInternal" rows=10 cols=35 wrap=SOFT tabindex="30"
					style="overflow:visible"
					onKeyUp="return maxTextarea(<%= com.ardais.bigr.lims.helpers.LimsConstants.LIMS_NON_OCE_FEATURE_MAXLENGTH %>)";></textarea>
				</p>
				<p><input type="button" value="Add" tabindex="31" onClick="addItem('inte', null)"></p>
				<p>
				</div>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>