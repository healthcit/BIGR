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
String myBanner = "Place Samples on Hold (" + helper.getProjectName() + ")";
String submitToOp = "PtsSampleHold";
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
var MAX_SELECTED = 100;

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
  if (document.all.<%=ProjectHelper.ID_SHOPPING_CART%>.value == "") {
    alert("Please select a person to put the samples on hold for.");
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
      selectedCount = 0;
      var isMax = false;
      for (var i = 0; i < samples.length; i++) {
        if (checked) {
          if (isMax) {
            document.all.sample[i].checked = false;
          }
          else if (selectedCount < MAX_SELECTED) {
            document.all.sample[i].checked = checked;
            selectedCount++;
          }
          else {              
            alert('Only ' + MAX_SELECTED + ' samples can be selected to be put on hold in one transaction.  The first 100 samples have been selected');
            isMax = true;
          }
        }
        else {
          document.all.sample[i].checked = checked;    
        }
      }
    }
    document.all.numSelected.innerHTML = selectedCount;
  }
}

function updateTotalSelected() {
  if (event.srcElement.checked) {
    if (selectedCount < MAX_SELECTED) {
      selectedCount++;
    }
    else {
      alert('Only ' + MAX_SELECTED + ' samples can be selected to be put on hold in one transaction.');
      event.srcElement.checked = false;
    }
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
  <input type="hidden" name="<%=ProjectHelper.ID_CLIENT%>" value="<%=helper.getRawClient()%>">
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
                <%=helper.getMessagesForColspan(4)%>
                <tr class="white"> 
                  <td align="right" class="grey"> 
                    <b>Number of Samples Selected:</b>
                  </td>
                  <td><span id="numSelected">0</span>
                  </td>
                  <td align="right" class="grey"> 
                    <b>Place on Hold For:</b>
                  </td>
                  <td>
                    <%=helper.getUserList()%>
                  </td>
                </tr>
                <tr class="white"> 
                  <td align="center" colspan="4">
                    <input type="submit" value="Place on Hold">
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
