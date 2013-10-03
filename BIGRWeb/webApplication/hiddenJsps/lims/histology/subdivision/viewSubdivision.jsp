<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.iltds.helpers.FormLogic" %>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.javabeans.IdList" %>
<%@ page import="com.ardais.bigr.lims.btx.BTXDetailsSubdivideRelationship" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.io.IOException" %>
<%
    com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html"  prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<%
    BTXDetailsSubdivideRelationship btxDetails = (BTXDetailsSubdivideRelationship) request.getAttribute("btxDetails");
%>
<%!
// WARNING:  Don't define any member variables in this areas outside of the
// inner class declarations below.  If you do, the values of those variables
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
        out.print(", the sample was marked as being in the repository.  " +
                  "The child samples are NOT marked as being in repository.");
    }
    else if (statusTypeCode.equals(FormLogic.SMPL_ESSHIPPED)) {
        out.print(", the sample was shipped to a customer." +
                  "The child samples are NOT automatically marked as shipped as well.");
    }
    else if (statusTypeCode.equals(FormLogic.SMPL_ESSOLD)) {
        out.print(", the sample was sold to a customer." +
                  "The child samples are NOT automatically marked as sold as well.");
    }
    else {
        out.print(", the sample has a status record of type ");
        out.print(statusTypeCode);
        out.print(".");
    }
}
// END of declaration block.
%>
<html>
<head>
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Subdivision Results</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script type="text/javascript">
var myBanner = 'Subdivision Results';

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

function onPageLoad() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
}
</script>
</head>
<body class="bigrPrintable" <logic:notPresent parameter="popup">onLoad="onPageLoad();"</logic:notPresent>>
<html:form action="/lims/subdivideResults">
  <div align="center">
    <table>
      <tr> 
        <td> 
          <table class="fgTableSmall" cellpadding="2" cellspacing="0" border="0" width="500">
            <% if (request.getAttribute("org.apache.struts.action.ERROR") != null) { %>
            <tr class="yellow">
              <td colspan="4">
                <div align="left">
                  <font color="#FF0000">
                    <b><html:errors/></b>
                  </font>
                </div>
              </td>
            </tr>
            <% } %>
            <tr class="yellow"> 
              <td colspan="4">
                <div align="center"><b>Subdivision Results</b></div>
              </td>
            </tr>
            <tr class="white"> 
              <td colspan="4">
                <div align="center">Subdivision recorded at <%=DateFormat.getDateTimeInstance().format(btxDetails.getSubdivisionTimestamp())%></div>
              </td>
            </tr>
            <tr class="white">
              <td colspan="4">
                <b>Notes:</b>
                <ul class="spacedbullets">
                 <%
                 {
                   java.util.Date qcInProcess = btxDetails.getQCInProcess();
                   if (qcInProcess != null) { 
                 %>
                   <li>
                   The parent sample was on a pathology pick list at the time of subdivision.
                   It was placed on the list on <%=DateFormat.getDateTimeInstance().format(qcInProcess) %>.
                   The children have all been automatically placed on a pathology pick list.
                   </li>
                 <%
                   }
                   java.util.Date rndRequested = btxDetails.getRNDRequested();
                   if (rndRequested != null) {
                 %>
                   <li>
                   The parent sample was on an R&D pick list at the time of subdivision.
                   It was placed on the list on <%= DateFormat.getDateTimeInstance().format(rndRequested) %>.
                   The children have all been automatically placed on an R&D pick list.
                   </li>
                 <%
                   }
                   Map warningInfo = btxDetails.getWarningInfo();
                   if (warningInfo != null) {
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
                 }
                 %>
                </ul>
              </td>
            </tr>
            <tr class="white">
              <td colspan="2" class="green">
                <div align="right"><b>Case Id:</b></div>
              </td>
              <td>
                <bigr:icpLink popup="true"><%=btxDetails.getConsentId()%></bigr:icpLink>
              </td>
              <td>
              </td>
            </tr>
            <tr class="white">
              <td colspan="2" class="green">
                <div align="right"><b>Sample Id:</b></div>
              </td>
              <td>
                <bigr:icpLink popup="true"><%=btxDetails.getParentId()%></bigr:icpLink>
              </td>
              <td nowrap>
                <div align="center"><span class="barcode"><%=FormLogic.checkSum(btxDetails.getParentId())%></span></div>
              </td>
            </tr>
            <tr class="white"> 
              <td colspan="2" class="green">
                <div align="right"><b>ASM:</b></div>
              </td>
              <td>
                <%= btxDetails.getAsmPosition() %>
              </td>
              <td>
              </td>
            </tr>
            <tr>
              <td colspan="4">
                &nbsp;
              </td>
            </tr>
            <tr class="green">
              <td nowrap>
                <div align="center"><b>Child Id</b></div>
              </td>
              <td nowrap>
                <div align="center"><b>ASM</b></div>
              </td>
              <td nowrap>
                <div align="center"><b>Print</b></div>
              </td>
              <td nowrap>
                <div align="center"><b>Barcode</b></div>
              </td>
            </tr>
            <%
            {
              IdList childIdList = btxDetails.getChildIdList();
              HashMap childAsmPositions = btxDetails.getChildAsmPositions();
              if (childIdList != null) {
                Iterator iter = childIdList.iterator();
                while (iter.hasNext()) {
                  String childId = (String) iter.next();
                  String asmPosition = ApiFunctions.safeString((String)childAsmPositions.get(childId));
                  %>
                  <tr>
                    <td nowrap>
                      <div align="center"><bigr:icpLink popup="true"><%=childId%></bigr:icpLink></div>
                    </td>
                    <td nowrap>
                      <div align="center"><%=asmPosition%></div>
                    </td>
                    <td nowrap>
                      <input type="hidden" name="sampleIdList" value="<%=childId%>">
                      <input type="hidden" name="asmPositionList" value="<%=asmPosition%>">
                      <div align="center">
                        <input
                          type="button"
                          style="font-size:xx-small; width: 70px;"
                          name="printLabel"
                          value="Print Label"
                          onclick="printSample(this,'<%=childId%>','<%=asmPosition%>')"/>
                      </div>
                    </td>
                    <td nowrap>
                      <div align="center"><span class="barcode"><%=FormLogic.checkSum(childId)%></span></div>
                    </td>
                  </tr>
                  <%
                }
              }
            }
            %>
            <tr>
              <td colspan="4">
                &nbsp;
              </td>
            </tr>
            <tr class="white"> 
              <td colspan="4"> 
                <div class="noprint" align="center"> 
                  <%-- We don't make the request-another-subdivision type="submit" because we don't want it
                       to be the default button.  The user needs to be able to print the page, and we don't
                       want to make it easy for them to navigate away accidentally. --%>
                  <logic:present parameter="popup">
                    <html:button style="font-size:xx-small; width: 100px;" property="printReceipt" value="Print Receipt" onclick="window.print()"/>
                    <html:button style="font-size:xx-small; width: 100px;" property="printAllLabels" value="Print Child Labels" onclick="printSampleList(this,document.limsSubdivisionRequestForm.sampleIdList,document.limsSubdivisionRequestForm.asmPositionList)"/>
                    <html:cancel style="font-size:xx-small; width: 100px;" property="cancel" value="Cancel" onclick="window.close()"/>
                  </logic:present>
                  <logic:notPresent parameter="popup">
                    <html:button style="font-size:xx-small;" property="printReceipt" value="Print Receipt" onclick="window.print()"/>
                    <html:button style="font-size:xx-small;" property="printAllLabels" value="Print Child Labels" onclick="printSampleList(this,document.limsSubdivisionRequestForm.sampleIdList,document.limsSubdivisionRequestForm.asmPositionList)"/>
                    <html:submit style="font-size:xx-small;" property="Submit" value="Request Another Subdivision"/>
                  </logic:notPresent>
                 </div>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </div>
</html:form>
</body>
</html>
