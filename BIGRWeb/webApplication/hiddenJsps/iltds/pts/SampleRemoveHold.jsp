<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.iltds.helpers.JspHelper"%>
<%@ page import="com.ardais.bigr.iltds.helpers.ProjectHelper"%>
<%@ page import="com.ardais.bigr.iltds.helpers.SampleTableHelper"%>
<%@ page import="com.ardais.bigr.iltds.helpers.SampleHelper"%>
<%@ page import="com.ardais.bigr.api.Escaper"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%
com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, application, "P");
ProjectHelper helper = (ProjectHelper)request.getAttribute(JspHelper.ID_HELPER);
SampleTableHelper tableHelper = (SampleTableHelper)helper.getSampleTableHelper();
String myBanner = "Remove Samples from Hold ( " + helper.getProjectName() + ")";
String submitToOp = "PtsSampleRemoveHold";
int size = tableHelper.getSamples().size();
StringBuffer sb = new StringBuffer();

// need a list of the sample ids...
List sampleList = tableHelper.getSamples();
// we will take that list of samples and build a javascript array
// to be used in javascript function matchSamplesFromClipboard()
sb.append("\n<script type=\"text/javascript\">");
sb.append("\nvar samplesProj = new Array();");

int loop = 0;
for (int i=0; i < size; i++) {
      SampleHelper sampHelper = (SampleHelper) sampleList.get(i);
      sb.append("\n  samplesProj[");
      Escaper.jsEscapeInScriptTag(new Integer(i).toString(), sb);
      sb.append("] = '");
      Escaper.jsEscapeInScriptTag(sampHelper.getId(), sb);
      sb.append("';");
      loop++;
	}
sb.append("\n</script>");
%>
<%=sb.toString()%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title><%=myBanner%></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script type="text/javascript" src='<html:rewrite page="/js/common.js"/>'></script>
<script type="text/javascript" src='<html:rewrite page="/js/matchClipboard.js"/>'></script>
<script language="JavaScript">
<!--
var selectedCount = 0;

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = "<%=myBanner%>";
  stDiv = document.all.sampleTableDiv;
  var maxHeight = stDiv.offsetParent.clientHeight - stDiv.offsetTop - 16;
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
  <input type="hidden" name="<%=ProjectHelper.ID_PROJECT_ID%>" value="<%=helper.getId()%>">
  <div align="center">

<%
if (size > 0) {
%>
<!-- Summary -->
  <table class="background" cellpadding="0" cellspacing="0" border="0">
    <tr> 
      <td> 
        <table class="background" cellpadding="0" cellspacing="0" border="0">
          <tr> 
            <td> 
              <table class="foreground" cellpadding="3" cellspacing="1" border="0">
                <%=helper.getMessagesForColspan(2)%>
                <tr class="white"> 
                  <td align="right" class="grey"> 
                    <b>Number of Samples Selected:</b>
                  </td>
                  <td><span id="numSelected">0</span>
                  </td>
                </tr>
                <tr class="white"> 
                  <td align="center" colspan="2">
                    <input type="submit" value="Remove from Hold">
                    <input type="button" value="Select All" onclick="selectAll();">
                    <input type="button" value="Clear All" onclick="clearAll();">
                    <input type="button" value="Match Clipboard" onclick="matchSamplesFromClipboard();">                  
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <p></p>      
<%
}
%>

<!-- Samples -->  
<%=tableHelper.getSampleTable()%>

  </div>
</form>
</body>
</html>
