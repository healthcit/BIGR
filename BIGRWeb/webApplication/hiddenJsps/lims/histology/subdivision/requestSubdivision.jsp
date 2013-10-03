<%@ page language="java" import="java.util.*" errorPage="/hiddenJsps/reportError/errorReport.jsp" %>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.api.ValidateIds" %>
<%@ page import="com.ardais.bigr.iltds.helpers.FormLogic" %>
<%@ page import="com.gulfstreambio.gboss.GbossFactory" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.io.IOException" %>

<%
    com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>

<%!
// WARNING:  Don't define any member variables in this area.
// If you do, the values of those variables
// will be shared by all request threads and there is great potential for one
// user's request seeing data that was meant for another user's request.

private void printSubdivisionWarningHTML(JspWriter out, String statusTypeCode, java.util.Date statusDateTime) throws IOException {
    String formattedDate = "(date/time unknown)";
    if (statusDateTime != null) {
        formattedDate = DateFormat.getDateTimeInstance().format(statusDateTime);
    }

    out.print("On ");
    out.print(formattedDate);
	
	if (statusTypeCode.equals(FormLogic.STATE_SAMPLE_IN_REPOSITORY)) {
		out.print(", the sample is in the repository.");
	}
	else if (statusTypeCode.equals(FormLogic.SMPL_ESSHIPPED)) {
		out.print(", the sample was shipped to a customer.");
	}
	else if (statusTypeCode.equals(FormLogic.SMPL_ESSOLD)) {
		out.print(", the sample was sold to a customer.");
	}
	else if (statusTypeCode.equals(FormLogic.STATE_SAMPLE_BMS)) {
		out.print(", the sample is a BMS sample.");
	}		
	else {
		out.print(", the sample has a status record of type ");
		out.print(statusTypeCode);
	}
}
// END of declaration block.
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
	document.limsSubdivisionRequestForm.inputId.focus();
}

myBanner = 'Subdivide Paraffin Sample';


var NS4 = (document.layers) ? true : false;

function checkKey(event)
{ 	
	var code = 0;

	if (NS4) {
		code = event.which;
	}
	else {
		code = event.keyCode;
	}

	if ((code == 9) || (code == 13)) {
		if (document.limsSubdivisionRequestForm.inputId.value.length >= 0) {
			document.limsSubdivisionRequestForm.action = '<html:rewrite page="/lims/subdivideGetSample.do"/>';
			document.limsSubdivisionRequestForm.submit();
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
	document.limsSubdivisionRequestForm.action = '<html:rewrite page="/lims/subdivideGetSample.do"/>';
	document.limsSubdivisionRequestForm.submit();
}

function onClickCancel() {
	event.returnValue = false;
	document.limsSubdivisionRequestForm.action = '<html:rewrite page="/lims/subdivideStart.do"/>';
	document.limsSubdivisionRequestForm.submit();
}

function printSample(button, sampleId, asmPosition) {
    event.returnValue = false;

    button.disabled = true;

    var w = window.open('<html:rewrite page="/lims/printLabel.do"/>' + '?printSingle=' + sampleId + '&asmPosition=' + asmPosition,'subdividePrint','scrollbars=no,resizable=no,status=no,width=330,height=50');
    w.focus();

    button.disabled = false;
}

function validateForm()
{
	// guard against double-submit
	document.limsSubdivisionRequestForm.Submit.disabled = true;

	if (document.limsSubdivisionRequestForm.numberOfChildren != null) {	
		var message = "Are you sure you want to subdivide?\n\n";
		message += "Sample Id\t: " + document.limsSubdivisionRequestForm.parentId.value + "\n\n";
		message += "Children\t: " + document.limsSubdivisionRequestForm.numberOfChildren.value;
		}
	else 
		return false;	

	if (confirm(message)) {
		return true;
	}
	else {
		document.limsSubdivisionRequestForm.Submit.disabled = false;
		return false;
	}
}

</script>
</head>
<body class="bigr" onLoad="onPageLoad();">
<html:form action="/lims/subdivideEdit" onsubmit="return(validateForm());">
  <div align="center">
    <table><tr><td  valign="top"><div align="left">
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
              <input type="text"
                autocomplete="off"
                style="font-size:xx-small;"
                name="inputId"
                size="<%=String.valueOf((ValidateIds.LENGTH_SAMPLE_ID + 2))%>"
                maxlength="<%=String.valueOf(ValidateIds.LENGTH_SAMPLE_ID)%>"
                onkeydown="checkKey(event);"/>
              <input type="button" style="font-size:xx-small;" name="inputSubmit" value="Go" onclick="scanInput()"/>
            </div>
          </td>
        </tr>
      </table>
    </div></td>
    <tr><td colspan="2"><div align="justify">
      <table id="histoQCTable" class="fgTableSmall" cellpadding="2" cellspacing="0" border="0">
        <tr class="yellow">
          <td nowrap colspan="7">
            <div align="center"><b>"Subdivide" will consume the parent sample in the system.</b></div>
          </td>
        </tr>
        <% if (request.getAttribute("org.apache.struts.action.ERROR") != null) { %>
        <tr class="yellow">
          <td colspan="7">
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
            <div align="center"><b># of Children</b></div>
          </td>
          <td>
            <div align="center"><b>DI: Paraffin Feature</b></div>
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
        <logic:present name="limsSubdivisionRequestForm" property="parentId">
        <logic:notEqual name="limsSubdivisionRequestForm" property="parentId" value="">
          <html:hidden property="parentId"/>
          <html:hidden property="consentId"/>
          <html:hidden property="asmPosition"/>
          <html:hidden property="diMinThicknessPfinCid"/>
          <html:hidden property="diMaxThicknessPfinCid"/>
          <html:hidden property="diWidthAcrossPfinCid"/>
          <html:hidden property="paraffinFeatureCid"/>
          <html:hidden property="otherParaffinFeature"/>
          <tr class="white">
            <td nowrap>
              <bigr:icpLink popup="true"><bean:write name="limsSubdivisionRequestForm" property="parentId"/></bigr:icpLink>
            </td>
            <td rowspan="2">
              <div align="center"><bean:write name="limsSubdivisionRequestForm" property="asmPosition"/></div>
            </td>
            <td>
              <div align="center">
                <bean:write name="limsSubdivisionRequestForm" property="diMinThicknessPfin"/>
              </div>
            </td>
            <td>
              <div align="center">
                <bean:write name="limsSubdivisionRequestForm" property="diMaxThicknessPfin"/>
              </div>
            </td>
            <td>
              <div align="center">
                <bean:write name="limsSubdivisionRequestForm" property="diWidthAcrossPfin"/>
              </div>
            </td>
            <td rowspan="2">
              <div align="center">
                <html:select name="limsSubdivisionRequestForm" property="numberOfChildren" onkeypress="checkEnter(event)">
                  <html:option value=""></html:option>
                  <html:option value="2">2</html:option>
                  <html:option value="3">3</html:option>
                  <html:option value="4">4</html:option>
                </html:select>
              </div>
            </td>
            <td>
              <logic:present name="limsSubdivisionRequestForm" property="paraffinFeatureCid">
              <bean:define id="paraffinFeatureCid" name="limsSubdivisionRequestForm" property="paraffinFeatureCid" type="String"/>
              <logic:present name="limsSubdivisionRequestForm" property="otherParaffinFeature">
              <bean:define id="otherParaffinFeature" name="limsSubdivisionRequestForm" property="otherParaffinFeature" type="String"/>
              </logic:present>
              <logic:equal name="limsSubdivisionRequestForm" property="paraffinFeatureCid" value="<%=FormLogic.OTHER_PARAFFIN_FEATURE%>">
              <div align="center">Other: <bean:write name="limsSubdivisionRequestForm" property="otherParaffinFeature"/></div>
              </logic:equal>
              <logic:notEqual name="limsSubdivisionRequestForm" property="paraffinFeatureCid" value="<%=FormLogic.OTHER_PARAFFIN_FEATURE%>">
              <div align="center"><%=(ApiFunctions.isEmpty(paraffinFeatureCid)) ? "" : GbossFactory.getInstance().getDescription(paraffinFeatureCid)%></div>
              </logic:notEqual>
              </logic:present>
            </td>
          </tr>
          <tr class="white">
            <td>
              <bigr:icpLink popup="true"><bean:write name="limsSubdivisionRequestForm" property="consentId"/></bigr:icpLink>
            </td>
            <td>
              <div align="center">
                <bigr:selectList
                  style="font-size:xx-small;"
                  name="limsSubdivisionRequestForm"
                  property="histoMinThicknessPfinCid"
                  legalValueSetName="limsSubdivisionRequestForm"
                  legalValueSetProperty="paraffinDimensionsSet"
                  firstValue=""
                  firstDisplayValue=""/>
              </div>
            </td>
            <td>
              <div align="center">
                <bigr:selectList
                  style="font-size:xx-small;"
                  name="limsSubdivisionRequestForm"
                  property="histoMaxThicknessPfinCid"
                  legalValueSetName="limsSubdivisionRequestForm"
                  legalValueSetProperty="paraffinDimensionsSet"
                  firstValue="" firstDisplayValue=""/>
              </div>
            </td>
            <td>
              <div align="center">
                <bigr:selectList
                  style="font-size:xx-small;"
                  name="limsSubdivisionRequestForm"
                  property="histoWidthAcrossPfinCid"
                  legalValueSetName="limsSubdivisionRequestForm"
                  legalValueSetProperty="paraffinDimensionsSet"
                  firstValue=""
                  firstDisplayValue=""/>
              </div>
            </td>
            <td>
              <div align="center">
                <html:textarea style="font-size:xx-small;" name="limsSubdivisionRequestForm" property="histoNotes" cols="25" rows="2" onkeyup='maxTextarea(255)'/>
              </div>
            </td>
          </tr>
          <tr class="white">
            <td colspan="7">
              &nbsp;
            </td>
          </tr>
        </logic:notEqual>
        </logic:present>
        <tr class="white">
          <td colspan="7">
            <div align="center">
              <logic:present name="limsSubdivisionRequestForm" property="parentId">
                <logic:notEqual name="limsSubdivisionRequestForm" property="parentId" value="">
                  <bean:define id="sampleId" name="limsSubdivisionRequestForm" property="parentId" type="String"/>
                  <bean:define id="asmPosition" name="limsSubdivisionRequestForm" property="asmPosition" type="String"/>
                  <html:submit style="font-size:xx-small; width: 70px;" property="Submit" value="Subdivide"/>
                  <input type="button" style="font-size:xx-small; width: 70px;" property="printLabel" value="Print Label" onclick="printSample(this,'<%=sampleId%>','<%=asmPosition%>')"/>
                  <html:cancel style="font-size:xx-small; width: 70px;" property="cancel" value="Cancel" onclick="onClickCancel()"/>
                </logic:notEqual>
                <logic:equal name="limsSubdivisionRequestForm" property="parentId" value="">
                  <html:submit style="font-size:xx-small; width: 70px;" property="Submit" value="Subdivide" disabled="true"/>
                  <html:button style="font-size:xx-small; width: 70px;" property="printLabel" value="Print Label" disabled="true"/>
                  <html:cancel style="font-size:xx-small; width: 70px;" property="cancel" value="Cancel" disabled="true"/>
                </logic:equal>
              </logic:present>
              <logic:notPresent name="limsSubdivisionRequestForm" property="parentId">
                <html:submit style="font-size:xx-small; width: 70px;" property="Submit" value="Subdivide" disabled="true"/>
                <html:button style="font-size:xx-small; width: 70px;" property="printLabel" value="Print Label" disabled="true"/>
                <html:cancel style="font-size:xx-small; width: 70px;" property="cancel" value="Cancel" disabled="true"/>
              </logic:notPresent>
            </div>
          </td>
        </tr>
      </table>
    </div></td></tr>
    <logic:present name="limsSubdivisionRequestForm" property="warningInfo">
      <tr>
        <td>
          <div align="center">
          <table class="fgTableSmall" cellpadding="3" cellspacing="1" border="0" width="500">
            <tr class="white">
              <td class="grey">
                <p><b>
                  The parent sample that you specified has characteristics that are
                  unusual for a subdivision parent.  Please check that you have
                  entered the correct parent sample id.
                </b></p>
                <p>These are the unusual characteristics:</p>
                <ul>
                <bean:define id="warningInfo" name="limsSubdivisionRequestForm" property="warningInfo" type="Map"/>
                <%
                {
                  Set warningEntries = warningInfo.entrySet();
                  Iterator iter = warningEntries.iterator();
                  while (iter.hasNext()) {
                    Map.Entry mapEntry = (Map.Entry) iter.next();
                    String statusTypeCode = (String) mapEntry.getKey();
                    java.util.Date statusDateTime = (java.util.Date) mapEntry.getValue();
                    out.print("<li>");
                    printSubdivisionWarningHTML(out, statusTypeCode, statusDateTime);
                    out.print("</li>");
                  }
				}
				%>
			    </ul>
			  </td>
			</tr>
          </table>
          </div>
        </td>
      </tr>
    </logic:present>
    </table>
  </div>
</html:form>
</body>
</html>