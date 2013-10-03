<%@ page language="java" import="java.util.*" errorPage="/hiddenJsps/reportError/errorReport.jsp" %>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.iltds.helpers.FormLogic" %>
<%@ page import="com.gulfstreambio.gboss.GbossFactory" %>
<%@ page import="com.ardais.bigr.util.ArtsConstants" %>
<%
        com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>

<%@ taglib uri="/tld/struts-html"  prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/struts-bean"  prefix="bean" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<html>
<head>

<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script type="text/javascript">

function onLoadPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = mybanner;
}

mybanner = 'Histology QC Confirmation';

function onClickView(button, sampleId) {
	event.returnValue = false;
	
	button.disabled = true;

	var w = window.open('<html:rewrite page="/lims/subdivideView.do"/>' + '?popup=true&parentId=' + sampleId,'viewSubdivide','scrollbars=yes,resizable=yes,status=yes,width=540,height=400');
	w.focus();
	
	button.disabled = false;
}

function printSample(button, sampleId, asmPosition) {
    event.returnValue = false;

    button.disabled = true;

    var w = window.open('<html:rewrite page="/lims/printLabel.do"/>' + '?printSingle=' + sampleId + '&asmPosition=' + asmPosition,'subdividePrint','scrollbars=no,resizable=no,status=no,width=330,height=50');
    w.focus();

    button.disabled = false;
}

function printSampleList(button, samples, asmPositions) {
    event.returnValue = false;

    button.disabled = true;
    
    var sampleIdList = arrayFor(samples);
	var sampleParams = "";
	for (i = 0; i < sampleIdList.length; i++) {
		sampleParams += "&sampleIdList=" + sampleIdList[i].value;
	}
	
	var asmPositionList = arrayFor(asmPositions);
	var asmPositionParams = "";
	for (i = 0; i < asmPositionList.length; i++) {
		asmPositionParams += "&asmPositionList=" + asmPositionList[i].value;
	}

    var w = window.open('<html:rewrite page="/lims/printLabel.do"/>' + '?printAll=true' + sampleParams + asmPositionParams,'subdividePrint','scrollbars=no,resizable=no,status=no,width=330,height=50');
    w.focus();

    button.disabled = false;
}

</script>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
</head>

<body class="bigr" onLoad="onLoadPage();">
<html:form action="/lims/limsHistoQCConfirm">
  <div align="center">
    <table><tr><td><div align="center">
      <bean:define id="samples" name="limsHistoQCForm" property="sampleDataList" type="java.util.List"/>
      <table id="histoQCTable" class="fgTableSmall" cellpadding="2" cellspacing="0" border="0">
        <tr class="yellow">
          <td colspan="10">
            <div align="center"><b>Changes have been saved.</b></div>
          </td>
        </tr>
        <% if (request.getAttribute("org.apache.struts.action.ERROR")!=null) { %>
        <tr class="yellow"> 
          <td colspan="10">
            <div align="left"><font color="#FF0000"><b>
              <html:errors/></b></font>
            </div>
          </td>
        </tr>
        <% } %>
        <tr id="header" class="green">
          <td>
            <div align="left"><b>Sample ID</b></div>
          </td>
          <td rowspan="2">
            <div align="center"><b>ASM</b></div>
          </td>
          <td rowspan="2">
            <div align="center"><b>Slide Exists</b></div>
          </td>
          <td>
            <div align="center"><b>DI: Min Thickness</b></div>
          </td>
          <td>
            <div align="center"><b>DI: Max Thickness</b></div>
          </td>
          <td nowrap>
            <div align="center"><b>DI: Width</b></div>
          </td>
          <td rowspan="2">
            <div align="center"><b>Sub-dividable?</b></div>
          </td>
          <td>
            <div align="center"><b>DI: Paraffin Feature</b></div>
          </td>
          <td rowspan="2" nowrap>
            <div align="center"><b>Re-embed Reason</b></div>
          </td>
          <td rowspan="2">
            <div align="center"><b>Action</b></div>
          </td>
        </tr>
        <tr  class="green">
          <td>
            <div align="left"><b>Case ID</b></div>
          </td>
          <td>
            <div align="center"><b>Min Thickness</b></div>
          </td>
          <td>
            <div align="center"><b>Max Thickness</b></div>
          </td>
          <td>
            <div align="center"><b>Width</b></div>
          </td>
          <td>
            <div align="center"><b>Notes</b></div>
          </td>
        </tr>
        <bean:define id="showSubdividedLegend" value="false"/>
        <bean:define id="showChildLegend" value="false"/>
        <bean:define id="showDisabledLegend" value="false"/>
        <bean:define id="showErrorLegend" value="false"/>
        <logic:iterate id="sampleData" name="limsHistoQCForm" property="sampleDataList" indexId="index" type="com.ardais.bigr.javabeans.SampleData">
          <tr class="white">
            <td nowrap>
              <bigr:icpLink popup="true"><bean:write name="sampleData" property="sampleId"/></bigr:icpLink>
              <html:hidden property="sampleIdList" value='<%=sampleData.getSampleId()%>'/>
              <bean:define id="isSubdivided" value='<%=(sampleData.isSubdivided()) ? "true" : "false"%>'/>
              <logic:match name="isSubdivided" value="true">
                <html:img page="/images/Alert.gif"/>
                <% showSubdividedLegend = "true"; %>
              </logic:match>
              <bean:define id="isChild" value='<%=(sampleData.getParentId().equals("")) ? "false" : "true"%>'/>
              <logic:match name="isChild" value="true">
                <html:img page="/images/Warning.gif"/>
                <% showChildLegend = "true"; %>
              </logic:match>
              <bean:define id="isDisabled" value='<%=(ArtsConstants.SAMPLE_TYPE_PARAFFIN_TISSUE).equals(sampleData.getSampleTypeCui()) ? "false" : "true"%>'/>
              <logic:match name="isDisabled" value="true">
                <html:img page="/images/frozen.gif"/>
                <% showDisabledLegend = "true"; %>
              </logic:match>
              <bean:define id="isError" value='<%=(sampleData.getSalesStatus().equals(FormLogic.SMPL_ESSOLD) || sampleData.getSalesStatus().equals(FormLogic.SMPL_ESSHIPPED) || sampleData.getSalesStatus().equals(FormLogic.SMPL_GENRECALLED) || sampleData.isPulled()) ? "true" : "false"%>'/>
              <logic:match name="isError" value="true">
                <html:img page="/images/Error.gif"/>
                <% showErrorLegend = "true"; %>
              </logic:match>
            </td>
            <td rowspan="2">
              <div align="center"><bean:write name="sampleData" property="asmPosition"/></div>
              <html:hidden property="asmPositionList" value='<%=sampleData.getAsmPosition()%>'/>
            </td>
            <td rowspan="2">
              <div align="center"><bean:write name="sampleData" property="slidesExist"/></div>
            </td>
            <td>
              <bean:define id="diMinThicknessCid" name="sampleData" property="diMinThicknessPfinCid" type="String"/>
              <div align="center"><%=(ApiFunctions.isEmpty(diMinThicknessCid)) ? "" : GbossFactory.getInstance().getDescription(diMinThicknessCid) %></div>
            </td>
            <td>
              <bean:define id="diMaxThicknessCid" name="sampleData" property="diMaxThicknessPfinCid" type="String"/>
              <div align="center"><%=(ApiFunctions.isEmpty(diMaxThicknessCid)) ? "" : GbossFactory.getInstance().getDescription(diMaxThicknessCid)%></div>
            </td>
            <td>
              <bean:define id="diWidthAcrossCid" name="sampleData" property="diWidthAcrossPfinCid" type="String"/>
              <div align="center"><%=(ApiFunctions.isEmpty(diWidthAcrossCid)) ? "" : GbossFactory.getInstance().getDescription(diWidthAcrossCid)%></div>
            </td>
            <td rowspan="2">
              <div align="center"><bean:write name="sampleData" property="histoSubdividable"/></div>
            </td>
            <td>
              <%
                String paraffinFeatureCid = sampleData.getParaffinFeatureCid();
                if (paraffinFeatureCid.equalsIgnoreCase(FormLogic.OTHER_PARAFFIN_FEATURE)) {
              %>
              <div align="center">Other: <%=sampleData.getOtherParaffinFeature()%></div>
              <%
                } else {
              %>
              <div align="center"><%=(ApiFunctions.isEmpty(paraffinFeatureCid)) ? "" : GbossFactory.getInstance().getDescription(paraffinFeatureCid)%></div>
              <%
                }
              %>
            </td>
            <td>
              <bean:define id="histoReembedReasonCid" name="sampleData" property="histoReembedReasonCid" type="String"/>
              <div align="center"><%=(ApiFunctions.isEmpty(histoReembedReasonCid)) ? "" : GbossFactory.getInstance().getDescription(histoReembedReasonCid)%></div>
            </td>
            <td rowspan="2">
              <div align="center">
                <table>
                  <tr><td>
                    <input
                      type="button"
                      style="font-size:xx-small; width: 110px;"
                      name="viewRelationship"
                      value="View Relationship"
                      onclick="onClickView(this,'<%=isChild.equals("true") ? sampleData.getParentId() : sampleData.getSampleId()%>')"
                      <%=(isSubdivided.equals("true") || isChild.equals("true")) ? "" : " disabled "%>
                      />
                  </td></tr>
                  <tr><td>
                    <input
                      type="button"
                      style="font-size:xx-small; width: 110px;"
                      name="printLabel"
                      value="Print Label"
                      onclick="printSample(this,'<%=sampleData.getSampleId()%>','<%=sampleData.getAsmPosition()%>')"/>
                  </td></tr>
                </table>
              </div>
            </td>
          </tr>
          <tr class="white">
            <td>
              <bigr:icpLink popup="true"><bean:write name="sampleData" property="consentId"/></bigr:icpLink>
            </td>
            <td>
              <bean:define id="histoMinThicknessCid" name="sampleData" property="histoMinThicknessPfinCid" type="String"/>
              <div align="center"><%=(ApiFunctions.isEmpty(histoMinThicknessCid)) ? "" : GbossFactory.getInstance().getDescription(histoMinThicknessCid) %></div>
            </td>
            <td>
              <bean:define id="histoMaxThicknessCid" name="sampleData" property="histoMaxThicknessPfinCid" type="String"/>
              <div align="center"><%=(ApiFunctions.isEmpty(histoMaxThicknessCid)) ? "" : GbossFactory.getInstance().getDescription(histoMaxThicknessCid) %></div>
            </td>
            <td>
              <bean:define id="histoWidthAcrossCid" name="sampleData" property="histoWidthAcrossPfinCid" type="String"/>
              <div align="center"><%=(ApiFunctions.isEmpty(histoWidthAcrossCid)) ? "" : GbossFactory.getInstance().getDescription(histoWidthAcrossCid) %></div>
            </td>
            <td>
              <div align="center">
                <html:textarea style="font-size:xx-small;" name="sampleData" property="histoNotes" cols="25" rows="2" disabled="true"/>
              </div>
            </td>
            <td>
              <div align="center"><input type="text" style="font-size:xx-small;" name="otherHistoReembedReason" value="<%=sampleData.getOtherHistoReembedReason()%>" disabled size="29" maxlength="200"/></div>
            </td>
          </tr>
          <tr>
            <td colspan="10">
              &nbsp;
            </td>
          </tr>
        </logic:iterate>       
        <tr class="white"> 
          <td colspan="10"> 
            <div align="center">
              <html:submit style="font-size:xx-small; width: 90px;" property="Submit" value="OK"/>
              <html:button style="font-size:xx-small; width: 90px;" property="printAllLabels" value="Print All Labels" onclick="printSampleList(this,document.limsHistoQCForm.sampleIdList,document.limsHistoQCForm.asmPositionList)" disabled="<%=samples.isEmpty()%>"/>
            </div>
          </td>
        </tr>
      </table>
    </div></td></tr>
    <% if (showSubdividedLegend.equals("true") || showChildLegend.equals("true") || showDisabledLegend.equals("true") || showErrorLegend.equals("true")) { %>
    <tr><td><div align="center">
      <table class="fgTableSmall" cellpadding="2" cellspacing="0" border="0">
        <% if (showSubdividedLegend.equals("true")) { %>
        <tr>
          <td><html:img page="/images/Alert.gif"/></td>
          <td><font size="1">This sample has been previously subdivided.</font></td>
        </tr>
        <% } %>
        <% if (showChildLegend.equals("true")) { %>
        <tr>
          <td><html:img page="/images/Warning.gif"/></td>
          <td><font size="1">This is a child sample and cannot be subdivided.</font></td>
        </tr>
        <% } %>
        <% if (showDisabledLegend.equals("true")) { %>
        <tr>
          <td><html:img page="/images/frozen.gif"/></td>
          <td><font size="1">This is a non-paraffin sample.</font></td>
        </tr>
        <% } %>
        <% if (showErrorLegend.equals("true")) { %>
        <tr>
          <td><div align="center"><html:img page="/images/Error.gif"/><div></td>
          <td><font size="1">This is a sold, shipped, recalled, or pulled sample and cannot be modified.</font></td>
        </tr>
        <% } %>
      </table>
    </div></td></tr>
    <% } %>    
    </table>
  </div>
</html:form>
</body>
</html>
