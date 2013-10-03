<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<table width="100%" cellpadding="0" cellspacing="0" border="0" class="background"><tr><td> <%-- BEGIN: background table 1 --%>
<%-- BEGIN: Sample info header row + error messages --%>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
  <logic:present name="org.apache.struts.action.ERROR">
    <tr class="yellow">
      <td colspan="10">
        <div align="center"><font color="#FF0000"><b><html:errors /></b></font></div>
      </td>
    </tr>
  </logic:present>
  <logic:present name="message">
    <tr class="yellow">
      <td colspan="10">
        <div align="center"><font color="#FF0000"><b><bean:write name="message" /></b></font></div>
      </td>
    </tr>
  </logic:present>
  <tbody>
    <tr class="green">
      <td colspan="10">
        <div align="center">
          <b>Sample Information - <bean:write name="limsCreateEvaluationForm"property="sampleId" />
            (<bean:write name="limsCreateEvaluationForm" property="slideId" />)</b>
          <html:hidden name="limsCreateEvaluationForm" property="sampleId" />
          <html:hidden name="limsCreateEvaluationForm" property="slideId" />
        </div>
      </td>
    </tr>
  </tbody>
</table>
<%-- END: Sample info header row + error messages --%>

<%-- BEGIN: Main Info Table
     This table has one row and two columns.  The first column contains a table
     with all of the left-hand data items, and the second column contains the
     raw path report elements. --%>
<table border="0" cellspacing="1" cellpadding="2" class="foreground-small"><tr class="white">

<td valign="top"> <%-- BEGIN: First column --%>
<div id="LeftColumnDiv" class="background">
<table width="100%" border="0" cellspacing="1" cellpadding="2" class="foreground-small">
  <tbody>
    <tr class="white">
      <td nowrap class="grey">
        <div align="right"><b>Case ID:</b></div>
      </td>
      <td nowrap>
        <bean:write name="limsCreateEvaluationForm" property="consentId" />
        <html:hidden name="limsCreateEvaluationForm" property="consentId" />
      </td>
      <td nowrap class="grey">
        <div align="right"><b>ASM:</b></div>
      </td>
      <td nowrap>
        <bean:write name="limsCreateEvaluationForm" property="asmPosition" />
        <html:hidden name="limsCreateEvaluationForm" property="asmPosition" />
      </td>
    </tr>
    <tr class="white">
      <td nowrap class="grey" colspan="2">
        <div align="right"><b>Gross Appearance (ASM):</b></div>
      </td>
      <td colspan="2" nowrap>
        <bean:write name="limsCreateEvaluationForm" property="grossAppearance" />
        <html:hidden name="limsCreateEvaluationForm" property="grossAppearance" />
      </td>
    </tr>
    <tr class="white">
      <td class="grey" colspan="2">
        <div align="right"><b>Site of Finding (ASM):</b></div>
      </td>
      <td colspan="2">
        <bean:write name="limsCreateEvaluationForm" property="asmTissue" />
        <html:hidden name="limsCreateEvaluationForm" property="asmTissue" />
      </td>
    </tr>
    <tr class="white">
      <td colspan="2" class="grey">
        <div align="right"><b>Primary Site of Finding (Case):</b></div>
      </td>
      <td colspan="2">
        <bean:write name="limsCreateEvaluationForm" property="primaryTissueOfFinding" />
        <html:hidden name="limsCreateEvaluationForm" property="primaryTissueOfFinding" />
      </td>
    </tr>
    <tr class="white">
      <td nowrap class="grey" colspan="2">
        <div align="right"><b>Primary Tissue of Origin of Dx (Case):</b></div>
      </td>
      <td colspan="2">
        <bean:write name="limsCreateEvaluationForm" property="primaryTissueOfOrigin" />
        <html:hidden name="limsCreateEvaluationForm" property="primaryTissueOfOrigin" />
      </td>
    </tr>
    <tr class="white">
      <td nowrap class="grey" colspan="2" align="right"><b>Primary Dx from DI Path Report:</b></td>
      <td colspan="2">
        <bean:write name="limsCreateEvaluationForm" property="primaryPathReportDx" />
        <html:hidden name="limsCreateEvaluationForm" property="primaryPathReportDx" />
      </td>
    </tr>
    <tr class="white">
      <td nowrap class="grey" colspan="2" valign="top" align="right">
        <b>Section Dx from DI Path Report:</b>
      </td>
      <td colspan="2" valign="top">
        <div style="overflow: auto; height: 50px; width: 200px">
          <logic:present name="limsCreateEvaluationForm" property="pathReportDxList">
            <logic:iterate id="sectionDx" name="limsCreateEvaluationForm" property="pathReportDxList">
              <%=sectionDx%>
              <br>
              <html:hidden property="pathReportDxList" value="<%=sectionDx.toString()%>" />
            </logic:iterate>
          </logic:present>
        </div>
      </td>
    </tr>
    <logic:present name="limsCreateEvaluationForm" property="incidentList">
      <tr class="white">
        <td nowrap class="grey" colspan="2" valign="top" align="right" style="background-color:red">
          <b>Re-PV incident(s):</b>
        </td>
        <td colspan="2" valign="top">
          <div style="overflow: auto; height: 75px; width: 200px">
            <logic:iterate id="incident" name="limsCreateEvaluationForm" property="incidentList">
              <%=incident%>
              <html:hidden property="incidentList" value="<%=incident.toString()%>" />
              <br><br>
            </logic:iterate>
          </div>
        </td>
      </tr>
    </logic:present>
    <tr class="white">
      <td colspan="4">
        &nbsp;
        <button type="button" TABINDEX="-1" accesskey="p" accesskey="P" id="pullButton"
          onclick="addReason('<html:rewrite page="/lims/limsPullDiscordantSample.do"/>?reasonType=Pull', 'resizable:yes;help:no;dialogWidth:200px;dialogHeight:200px', 'pull');">
          <u>P</u>ull
        </button>
        <html:hidden property="reason" />
        <input type="button" value="Discordant" TABINDEX="-1" id="discordantButton"
          onclick="addReason('<html:rewrite page="/lims/limsPullDiscordantSample.do"/>?reasonType=Discordant', 'resizable:yes;help:no;dialogWidth:200px;dialogHeight:200px', 'discordant');">
      </td>
    </tr>
  </tbody>
</table>
</div>
</td> <%-- END: First column --%>

<td nowrap valign="top"> <%-- BEGIN: Second column --%>
	<%-- BEGIN: Raw Path Report --%>
	<div>
	  <div style="font-weight: bold; margin-bottom: 3px; border-bottom: 1px solid black; width: 100%;">
	    Raw Pathology Report:
	  </div>
	  <html:hidden name="limsCreateEvaluationForm" property="pathReportId" />
	  <html:hidden name="limsCreateEvaluationForm" property="donorId" />
	  <html:hidden name="limsCreateEvaluationForm" property="rawPathReport" />
	  <logic:present name="limsCreateEvaluationForm" property="rawPathReport">
	    <logic:notEqual name="limsCreateEvaluationForm" property="rawPathReport" value="">
	      <bigr:beanWrite name="limsCreateEvaluationForm" property="rawPathReport" filter="true"
	        whitespace="true" />
	      <br>
	      <div class="white" style="margin-top: 3px; padding-left: 2px; border-top: thin solid black;">
	        I have reviewed the raw Pathology Report:
	        <html:radio name="limsCreateEvaluationForm" property="reviewedRawPathReport" value="true">Yes
	        </html:radio>
	        <html:radio name="limsCreateEvaluationForm" property="reviewedRawPathReport" value="false">No
	        </html:radio>
	      </div>
	    </logic:notEqual>
	    <logic:equal name="limsCreateEvaluationForm" property="rawPathReport" value="">
	      There is no raw path report available for this sample.
	      <input type="hidden" name="reviewedRawPathReport" value="true">
	    </logic:equal>
	  </logic:present>
	  <logic:notPresent name="limsCreateEvaluationForm" property="rawPathReport">
	    There is no raw path report available for this sample.
	    <input type="hidden" name="reviewedRawPathReport" value="true">
	  </logic:notPresent>
	</div>
	<%-- END: Raw Path Report --%>
</td> <%-- END: Second column --%>
</tr></table> <%-- END: Main Info Table --%>

</td></tr></table> <%-- END: background table 1 --%>
