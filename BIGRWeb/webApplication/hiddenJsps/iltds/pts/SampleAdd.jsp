<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.ardais.bigr.iltds.helpers.JspHelper"%>
<%@ page import="com.ardais.bigr.iltds.helpers.LineItemHelper"%>
<%@ page import="com.ardais.bigr.iltds.helpers.ProjectHelper"%>
<%@ page import="com.ardais.bigr.iltds.helpers.SampleTableHelper"%>
<%@ page import="com.ardais.bigr.security.SecurityInfo"%>
<%@ page import="com.ardais.bigr.util.WebUtils"%>
<%@ page import="com.ardais.bigr.api.Escaper" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%
com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, application, "P");
SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);
ProjectHelper projectHelper = (ProjectHelper)request.getAttribute(JspHelper.ID_HELPER);
LineItemHelper lineItemHelper = (LineItemHelper)projectHelper.getLineItems().get(0);
List samplesNotReturned = lineItemHelper.getSamplesNotReturned();
SampleTableHelper tableHelper = (SampleTableHelper)lineItemHelper.getSampleTableHelper();
String myBanner = "Select Samples (" + Escaper.htmlEscapeAndPreserveWhitespace(projectHelper.getProjectName()) + ")";
String submitToOp = "PtsSampleAdd";
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<title><%=myBanner%></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript">
<!--
var selectedCount = 0;

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = "<%=myBanner%>";
  stDiv = document.all.sampleTableDiv;
  var maxHeight = stDiv.offsetParent.clientHeight - stDiv.offsetTop - 16;
  if (maxHeight < 200) maxHeight = 200;
  if (stDiv.offsetHeight > maxHeight) {
      stDiv.style.height = maxHeight;
  }
}

function repositionHeader() {
	document.all.headerTR.style.top = sampleTableDiv.scrollTop;
}

function isValid() {
  if (selectedCount <= 0) {
    alert("Please select at least 1 sample");
    return false;
  }
  return true;
}

function clearAll() {
  modifyAllCheckboxes(false);
}

function selectAll() {
  modifyAllCheckboxes(true);
}

function modifyAllCheckboxes(checked) {
  var samples = document.all.sample;
  if (samples != null) {
    if (samples.length == null) {
      document.all.sample.checked = checked;
      selectedCount = (checked) ? 1 : 0;          
    }
    else {
      for (var i = 0; i < samples.length; i++) {
        document.all.sample[i].checked = checked;    
        selectedCount = (checked) ? samples.length : 0;          
      }
    }
    document.all.numSelected.innerHTML = selectedCount;
  }
}

function updateTotalSelected() {
  if (event.srcElement.checked) {
    selectedCount++;
  }
  else {
    selectedCount--;
  }
  document.all.numSelected.innerHTML = selectedCount;
  event.cancelBubble;
}
//-->
</script>
</head>

<body class="bigr" onLoad="initPage();">
<form name="theForm" method="post" action="<html:rewrite page='/iltds/Dispatch'/>" onsubmit="return isValid();">
  <input type="hidden" name="op" value="<%=submitToOp%>">
  <input type="hidden" name="<%=ProjectHelper.ID_PROJECT_ID%>" value="<%=projectHelper.getRawId()%>">
  <input type="hidden" name="<%=LineItemHelper.ID_LINEITEM_ID%>" value="<%=lineItemHelper.getRawId()%>">
  <div align="center">

<!-- Summary - The line item -->        
  <table class="background" cellpadding="0" cellspacing="0" border="0">
    <tr> 
      <td> 
          <table class="foreground" cellpadding="3" cellspacing="1" border="0">
            <tr> 
              <td class="yellow" colspan="6" align="center"> 
                <b>Line Item #<%=lineItemHelper.getLineItemNumber()%></b>
              </td>
            </tr>
            <tr><td colspan="6"></td></tr>
			      <tr class="white"> 
              <td class="grey" align="right" nowrap> 
                <b>Requested Quantity:</b>
              </td>
              <td align="left"> 
                <%=lineItemHelper.getQuantity()%>
              </td>
              <td class="grey" align="right" nowrap> 
                <b>Format:</b>
              </td>
              <td align="left"> 
                <%=lineItemHelper.getFormat()%>
              </td>
              <td align="right" class="grey" nowrap> 
                <b>Quantity Selected:</b>
              </td>
              <td><span id="numSelected">0</span>
              </td>
            </tr>
<%
if (lineItemHelper.hasNotes()) {
%>
			      <tr class="white"> 
              <td class="grey" align="right" valign="top"> 
                <b>Description:</b>
              </td>
              <td align="left" colspan="5"> 
                <%=Escaper.htmlEscapeAndPreserveWhitespace(lineItemHelper.getNotes())%>
              </td>
            </tr>
<%
}
%>
			      <tr class="white">
              <td colspan="6" align="center">
              <table class="foreground" cellpadding="3" cellspacing="1" border="0">
                <tr class="white"> 
                  <td align="center">
                    <input type="submit" value="Add to Project">
                    <input type="button" value="Select All" onclick="selectAll();">
                    <input type="button" value="Clear All" onclick="clearAll();">
                  </td>
                </tr>
              </table>
              </td>
            </tr>

          </table>
        </td>
      </tr>
   </table>

<!-- Samples -->  
  <p></p>
  <% tableHelper.setSecurityInfo(securityInfo);%>      
  <%=tableHelper.getSampleTable()%>

  </div>
  <% if ((samplesNotReturned != null) && (samplesNotReturned.size() > 0)) { %>
  <p></p>
  <b>List of Samples Not Returned by the Query</b>
  <br>
  <% 	Iterator it = samplesNotReturned.iterator(); 
  		while (it.hasNext()) { %>
  			<%=it.next()%>
  <br>
  <%
  		} // end while
  	}	  // end if
   %>
</form>
</body>
</html>
