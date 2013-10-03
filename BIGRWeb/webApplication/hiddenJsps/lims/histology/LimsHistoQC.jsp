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
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">

<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script type="text/javascript">

function onPageLoad() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
  document.limsHistoQCForm.inputId.focus();
}

myBanner = 'Histology QC';

function checkKey(event)
{ 	
	var code = 0;
	code = event.keyCode;

	if ((code == 9) || (code == 13)) {
		if (document.limsHistoQCForm.inputId.value.length >= 0) {
			document.limsHistoQCForm.action = '<html:rewrite page="/lims/limsHistoQCGetSample.do"/>';
			document.limsHistoQCForm.submit();
		}
	}
}

function checkEnter(event) {
	var code = 0;
	code = event.keyCode;
	
	if (code == 13) {
		return false;
    }
}

function scanInput() {
    event.returnValue = false; // for MR 5103
	document.limsHistoQCForm.action = '<html:rewrite page="/lims/limsHistoQCGetSample.do"/>';
	document.limsHistoQCForm.submit();
}

function removeRows(obj) {
	removeRow(obj);
	removeRow(obj);
	removeRow(obj);

    event.returnValue = false; // for MR 5103
	document.limsHistoQCForm.action = '<html:rewrite page="/lims/limsHistoQCRefresh.do"/>';
	document.limsHistoQCForm.submit();
}

function removeRow(obj) {
	var allRows = document.all.histoQCTable.rows;
	for (i = 0 ; i < allRows.length ; i++) {
		var submittedRow = allRows[i];
		if (obj == submittedRow.id) {
		   document.all.histoQCTable.deleteRow(i);
		}
	}
}

function setOther(index, value) {

	if (typeof(document.limsHistoQCForm.otherHistoReembedReasonList.length) == 'number') {
		document.limsHistoQCForm.otherHistoReembedReasonList[index].value = value;
	}
	else {
		document.limsHistoQCForm.otherHistoReembedReasonList.value = value;
	}
}

function updateOther(index, histoReembedReasonCode, otherCode, value){
	var otherName = "otherHistoReembedReason" + index;
	if (histoReembedReasonCode == otherCode)	{
		document.limsHistoQCForm.elements[otherName].value = value;
		document.limsHistoQCForm.elements[otherName].disabled = false;
	}
	else {
		document.limsHistoQCForm.elements[otherName].value = "N/A";
		document.limsHistoQCForm.elements[otherName].disabled = true;
	}
	setOther(index, value);
}

function updateRows() {

	var allHistoReembedReason = document.limsHistoQCForm.allHistoReembedReason;
	var allOtherHistoReembedReason = document.limsHistoQCForm.allOtherHistoReembedReason;
	var allHistoSubdividable = document.limsHistoQCForm.allHistoSubdividable;

	// Skip if blank.
	if (allHistoReembedReason.selectedIndex != 0) {
		// Set the histo reembed reason.
		var histoReembedReasonList = document.limsHistoQCForm.histoReembedReasonList;
		if (histoReembedReasonList != null) {
			var otherCount = 0;
			
			for (i = 0; i < histoReembedReasonList.length; i++) {
				var histoReembedReason = histoReembedReasonList[i];

				if ((histoReembedReason.tagName == "OPTION") && (histoReembedReason.value == allHistoReembedReason.value)) {
					histoReembedReason.selected = true;
					updateOther(otherCount, allHistoReembedReason.value, "<%=FormLogic.OTHER_HISTO_REEMBED_REASON%>", allOtherHistoReembedReason.value);
					otherCount++;
				}
				else if ((histoReembedReason.tagName == "SELECT") && (histoReembedReason.disabled == false)) {
					histoReembedReason.selectedIndex = allHistoReembedReason.selectedIndex;
					updateOther(otherCount, allHistoReembedReason.value, "<%=FormLogic.OTHER_HISTO_REEMBED_REASON%>", allOtherHistoReembedReason.value);
				}

				if (histoReembedReason.tagName == "SELECT") {
					otherCount++;
				}
			}
		}
	}

	// Skip if blank.
	if (allHistoSubdividable.selectedIndex != 0) {
		// Set the histo subdividable.
		var histoSubdividableList = document.limsHistoQCForm.histoSubdividableList;
		if (histoSubdividableList != null) {
			var subdividableCount = 0;
			
			for (i = 0; i < histoSubdividableList.length; i++) {
				var histoSubdividable = histoSubdividableList[i];

				if ((histoSubdividable.tagName == "OPTION") && (histoSubdividable.value == allHistoSubdividable.value)) {
					histoSubdividable.selected = true;
					subdividableCount++;
				}
				else if ((histoSubdividable.tagName == "SELECT") && (histoSubdividable.disabled == false)) {
					histoSubdividable.selectedIndex = allHistoSubdividable.selectedIndex;
				}

				if (histoSubdividable.tagName == "SELECT") {
					subdividableCount++;
				}
			}
		}
	}
}

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

function validateForm()
{
	// guard against double-submit
	document.limsHistoQCForm.Submit.disabled = true;
	return true;
}

</script>
</head>

<body class="bigr" onLoad="onPageLoad();">
<html:form action="/lims/limsHistoQCUpdateSamples" onsubmit="return(validateForm());">
  <div align="center">
    <table><tr><td  valign="top"><div align="left">
      <bean:define id="samples" name="limsHistoQCForm" property="sampleDataList" type="java.util.List"/>
      <table class="fgTableSmall" cellpadding="2" cellspacing="0" border="0">
        <tr class="yellow">
          <td nowrap>
            <div align="left"><b>Enter/Scan Sample or Slide Inventory Item:</b></div>
          </td>
        </tr>
        <% if (request.getAttribute("org.apache.struts.action.ERROR") != null) { %>
        <tr class="yellow">
          <td>
            <div align="left">
              <font color="#FF0000">
                <b><html:errors property="inputError"/></b>
              </font>
            </div>
          </td>
        </tr>
        <% } %>
        <tr class="white">
          <td>
            <div align="left">
              <input type="text" name="inputId" maxlength="10" size="12" value="" autocomplete="off" onkeydown="checkKey(event);" style="font-size:xx-small;">
              <input type="button" style="font-size:xx-small;" name="inputSubmit" value="Go" onclick="scanInput()"/>
            </div>
          </td>
        </tr>
      </table>
    </div></td>
    <td valign="top"><div align="right">
      <table class="fgTableSmall" cellpadding="2" cellspacing="0" border="0">
        <tr class="yellow">
          <td nowrap colspan="3">
            <div align="center"><b>Apply to all editable rows.</b></div>
          </td>
        </tr>
        <tr class="green">
          <td nowrap>
            <div align="center"><b>Re-embed Reason</b></div>
          </td>
          <td nowrap>
            <div align="center"><b>Other Reason</b></div>
          </td>
          <td>
            <div align="center"><b>Sub-dividable?</b></div>
          </td>
        </tr>
        <tr class="white">
          <td>
            <div align="center">
              <bigr:selectListWithOther 
                style="font-size:xx-small;"
                name="limsHistoQCForm"
                property="allHistoReembedReason"
                otherProperty="allOtherHistoReembedReason"
                legalValueSetName="limsHistoQCForm"
                legalValueSetProperty="histoReembedReasonSet"
                firstValue="" firstDisplayValue=""
                otherCode='<%=FormLogic.OTHER_HISTO_REEMBED_REASON%>'/>
            </div>
          </td>
          <td>
            <div align="center">
              <bigr:otherText
                style="font-size:xx-small;"
                name="limsHistoQCForm"
                property="allOtherHistoReembedReason"
                parentProperty="allHistoReembedReason"
                otherCode='<%=FormLogic.OTHER_HISTO_REEMBED_REASON%>'
                onkeydown="return checkEnter(event);"
                size="29"
                maxlength="200"/>
            </div>
          </td>
          <td>
            <div align="center">
              <html:select
                style="font-size:xx-small;"
                property="allHistoSubdividable">
                <html:option value=""></html:option>
                <html:option value="Y">Yes</html:option>
                <html:option value="N">No</html:option>
              </html:select>
            </div>
          </td>
        </tr>
        <tr>
          <td colspan="3">
            <div align="center">
              <html:button style="font-size:xx-small;" property="applyToAll" value="Apply to All" onclick="updateRows()" disabled='<%=samples.isEmpty()%>'/>
            </div>
          </td>
        </tr>
      </table>
    </div></td></tr>
    <tr><td colspan="2"><div align="justify">
      <table id="histoQCTable" class="fgTableSmall" cellpadding="2" cellspacing="0" border="0">
        <tr class="yellow">
          <td nowrap colspan="10">
            <div align="center"><b>Please press "Submit" to save updates.</b></div>
          </td>
        </tr>
        <% if (request.getAttribute("org.apache.struts.action.ERROR") != null) { %>
        <tr class="yellow">
          <td colspan="10">
            <div align="left">
              <font color="#FF0000">
                <b><html:errors property="submitError"/></b>
              </font>
            </div>
          </td>
        </tr>
        <% } %>
        <tr id="header" class="green">
          <td nowrap>
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
          <td nowrap rowspan="2">
            <div align="center"><b>Re-embed Reason</b></div>
          </td>
          <td rowspan="2">
            <div align="center"><b>Action</b></div>
          </td>
        </tr>
        <tr class="green">
          <td nowrap>
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
          <tr id='row<%=index%>' class="white">
            <html:hidden property="parentIdList" value='<%=sampleData.getParentId()%>'/>
            <html:hidden property="sampleTypeCidList" value='<%=sampleData.getSampleTypeCui()%>'/>
            <html:hidden property="salesStatusList" value='<%=sampleData.getSalesStatus()%>'/>
            <html:hidden property="pulledList" value='<%=(sampleData.isPulled()) ? "Y" : "N"%>'/>
            <html:hidden property="subdividedList" value='<%=(sampleData.isSubdivided()) ? "true" : "false"%>'/>
            <td nowrap>
              <bigr:icpLink popup="true"><bean:write name="sampleData" property="sampleId"/></bigr:icpLink>
              <html:hidden property="sampleIdList" value='<%=sampleData.getSampleId()%>'/>
              <bean:define id="isSubdivided" value='<%=(sampleData.isSubdivided()) ? "true" : "false"%>'/>
              <logic:match name="isSubdivided" value="true">
                <html:img page="/images/Alert.gif"/>
                <% showSubdividedLegend = "true"; %>
              </logic:match>
              <bean:define id="isChild" value='<%=(!ApiFunctions.isEmpty(sampleData.getParentId())) ? "true" : "false"%>'/>
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
              <html:hidden property="slidesExistList" value='<%=sampleData.getSlidesExist()%>'/>
            </td>
            <td>
              <bean:define id="diMinThicknessCid" name="sampleData" property="diMinThicknessPfinCid" type="String"/>
              <div align="center"><%=(ApiFunctions.isEmpty(diMinThicknessCid)) ? "" : GbossFactory.getInstance().getDescription(diMinThicknessCid) %></div>
              <html:hidden property="diMinThicknessList" value='<%=diMinThicknessCid%>'/>
            </td>
            <td>
              <bean:define id="diMaxThicknessCid" name="sampleData" property="diMaxThicknessPfinCid" type="String"/>
              <div align="center"><%=(ApiFunctions.isEmpty(diMaxThicknessCid)) ? "" : GbossFactory.getInstance().getDescription(diMaxThicknessCid)%></div>
              <html:hidden property="diMaxThicknessList" value='<%=diMaxThicknessCid%>'/>
            </td>
            <td>
              <bean:define id="diWidthAcrossCid" name="sampleData" property="diWidthAcrossPfinCid" type="String"/>
              <div align="center"><%=(ApiFunctions.isEmpty(diWidthAcrossCid)) ? "" : GbossFactory.getInstance().getDescription(diWidthAcrossCid)%></div>
              <html:hidden property="diWidthAcrossList" value='<%=diWidthAcrossCid%>'/>
            </td>
            <td rowspan="2">
              <div align="center">
                <html:select
                  style="font-size:xx-small;"
                  property="histoSubdividableList"
                  value='<%=sampleData.getHistoSubdividable()%>'
                  disabled='<%=(isSubdivided.equals("true") || isChild.equals("true") || isDisabled.equals("true") || isError.equals("true")) ? true : false%>'>
                  <html:option value=""></html:option>
                  <html:option value="Y">Yes</html:option>
                  <html:option value="N">No</html:option>
                </html:select>
              </div>
              <% if (isSubdivided.equals("true") || isChild.equals("true") || isDisabled.equals("true") || isError.equals("true")) { %>
              <html:hidden property="histoSubdividableList" value='<%=sampleData.getHistoSubdividable()%>'/>
              <% } %>
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
              <html:hidden property="paraffinFeatureCidList" value='<%=sampleData.getParaffinFeatureCid()%>'/>
              <html:hidden property="otherParaffinFeatureList" value='<%=sampleData.getOtherParaffinFeature()%>'/>
            </td>
            <td>
              <div align="center">
                <bigr:selectListWithOther 
                  style="font-size:xx-small;"
                  property="histoReembedReasonList"
                  otherProperty='<%="otherHistoReembedReason" + index%>'
                  legalValueSetName="limsHistoQCForm"
                  legalValueSetProperty="histoReembedReasonSet"
                  firstValue="" firstDisplayValue=""
                  otherCode='<%=FormLogic.OTHER_HISTO_REEMBED_REASON%>'
                  value='<%=sampleData.getHistoReembedReasonCid()%>'
                  disabled='<%=(isDisabled.equals("true") || isError.equals("true")) ? true : false%>'/>
              </div>
              <% if (isDisabled.equals("true") || isError.equals("true")) { %>
              <html:hidden property="histoReembedReasonList" value='<%=sampleData.getHistoReembedReasonCid()%>'/>
              <% } %>
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
                  <tr><td>
                    <input
                      type="button"
                      style="font-size:xx-small; width: 110px;"
                      name="removeSample"
                      value="Remove"
                      onclick="removeRows('row' + <%=index%>)"/>
                  </td></tr>
                </table>
              </div>
            </td> 
          </tr>
          <tr id='row<%=index%>' class="white">
            <td>
              <bigr:icpLink popup="true"><bean:write name="sampleData" property="consentId"/></bigr:icpLink>
              <html:hidden property="consentIdList" value='<%=sampleData.getConsentId()%>'/>
            </td>
            <td>
              <div align="center">
                <bigr:selectList
                  style="font-size:xx-small;"
                  property="histoMinThicknessList"
                  legalValueSetName="limsHistoQCForm"
                  legalValueSetProperty="paraffinDimensionsSet"
                  value='<%=sampleData.getHistoMinThicknessPfinCid()%>'
                  firstValue=""
                  firstDisplayValue=""
                  disabled='<%=(isDisabled.equals("true") || isError.equals("true")) ? true : false%>'/>
              </div>
              <% if (isDisabled.equals("true") || isError.equals("true")) { %>
              <html:hidden property="histoMinThicknessList" value='<%=sampleData.getHistoMinThicknessPfinCid()%>'/>
              <% } %>
            </td>
            <td>
              <div align="center">
                <bigr:selectList
                  style="font-size:xx-small;"
                  property="histoMaxThicknessList"
                  legalValueSetName="limsHistoQCForm"
                  legalValueSetProperty="paraffinDimensionsSet"
                  value='<%=sampleData.getHistoMaxThicknessPfinCid()%>'
                  firstValue="" firstDisplayValue=""
                  disabled='<%=(isDisabled.equals("true") || isError.equals("true")) ? true : false%>'/>
              </div>
              <% if (isDisabled.equals("true") || isError.equals("true")) { %>
              <html:hidden property="histoMaxThicknessList" value='<%=sampleData.getHistoMaxThicknessPfinCid()%>'/>
              <% } %>
            </td>
            <td>
              <div align="center">
                <bigr:selectList
                  style="font-size:xx-small;"
                  property="histoWidthAcrossList"
                  legalValueSetName="limsHistoQCForm"
                  legalValueSetProperty="paraffinDimensionsSet"
                  value='<%=sampleData.getHistoWidthAcrossPfinCid()%>'
                  firstValue=""
                  firstDisplayValue=""
                  disabled='<%=(isError.equals("true")) ? true : false%>'/>
              </div>
              <% if (isError.equals("true")) { %>
              <html:hidden property="histoWidthAcrossList" value='<%=sampleData.getHistoWidthAcrossPfinCid()%>'/>
              <% } %>
            </td>
            <td>
              <div align="center">
                <html:textarea style="font-size:xx-small;" property="histoNotesList" cols="25" rows="2" onkeyup='maxTextarea(255)' value='<%=sampleData.getHistoNotes()%>'/>
              </div>
            </td>
            <td>
              <div align="center">
              <% if (sampleData.getHistoReembedReasonCid().equals(FormLogic.OTHER_HISTO_REEMBED_REASON) && isDisabled.equals("false") && isError.equals("false")) { %>
                <bigr:otherText
                  style="font-size:xx-small;"
                  property='<%="otherHistoReembedReason" + index%>'
                  value='<%=sampleData.getOtherHistoReembedReason()%>'
                  name='sampleData'
                  parentProperty="histoReembedReasonCid"
                  otherCode='<%=FormLogic.OTHER_HISTO_REEMBED_REASON%>'
                  onblur='<%="setOther("+index+", this.value);"%>'
                  onkeydown="return checkEnter(event);"
                  size="29"
                  maxlength="200"/>
              <% } else { %>
                <input type="text"
                  style="font-size:xx-small;"
                  name='<%="otherHistoReembedReason" + index%>'
                  value="N/A"
                  onblur='setOther(<%=index%>, this.value)'
                  onkeydown="return checkEnter(event);"
                  disabled
                  size="29"
                  maxlength="200"/>
              <% } %>
              </div>
              <html:hidden property="otherHistoReembedReasonList" value="<%=sampleData.getOtherHistoReembedReason()%>"/>
            </td>
          </tr>
          <tr id='row<%=index%>' class="white">
            <td colspan="10">
              &nbsp;
            </td>
          </tr>
        </logic:iterate>
        <tr class="white">
          <td colspan="10">
            <div align="center">
              <html:submit style="font-size:xx-small; width: 90px;" property="Submit" value="Submit" disabled='<%=samples.isEmpty()%>'/>
              <html:button style="font-size:xx-small; width: 90px;" property="printAllLabels" value="Print All Labels" onclick="printSampleList(this,document.limsHistoQCForm.sampleIdList,document.limsHistoQCForm.asmPositionList)" disabled="<%=samples.isEmpty()%>"/>
            </div>
          </td>
        </tr>
      </table>
    </div></td></tr>
    <% if (showSubdividedLegend.equals("true") || showChildLegend.equals("true") || showDisabledLegend.equals("true") || showErrorLegend.equals("true")) { %>
    <tr><td colspan="2"><div align="center">
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
          <td><div align="center"><html:img page="/images/Error.gif"/></div></td>
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
