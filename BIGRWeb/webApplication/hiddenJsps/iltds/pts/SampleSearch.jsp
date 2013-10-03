<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.iltds.helpers.JspHelper"%>
<%@ page import="com.ardais.bigr.iltds.helpers.LineItemHelper"%>
<%@ page import="com.ardais.bigr.iltds.helpers.ProjectHelper"%>
<%@ page import="com.ardais.bigr.iltds.helpers.SampleTableHelper"%>
<%@ page import="com.ardais.bigr.es.javabeans.QueryBean"%>
<%@ page import="com.ardais.bigr.api.Escaper" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%
com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, application, "P");
ProjectHelper helper = (ProjectHelper)request.getAttribute(JspHelper.ID_HELPER);
LineItemHelper liHelper = (LineItemHelper)helper.getLineItems().get(0);
String myBanner = "Search for Samples (" + Escaper.htmlEscapeAndPreserveWhitespace(helper.getProjectName()) + ")";
String submitToOp = "PtsSampleAddPrepare";

QueryBean query = (QueryBean)request.getSession(false).getAttribute("pts.samplequery");
String cart = "";
List caseIds = null;
List sampleIds = null;
if (query != null) {
  cart = (query.getShoppingCartUserId() == null) ? "" : query.getShoppingCartUserId(); 
  caseIds = query.getCaseList(); 
  sampleIds = query.getSampleList();
}
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title><%=myBanner%></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script type="text/javascript" src='<html:rewrite page="/js/common.js"/>'></script>
<script type="text/javascript">
<%
String myError = (String)request.getAttribute("myError");
if(myError != null && !myError.equals("")){
	%>
	alert('<%= myError%>');
<%
}
%>
var validCaseID = 'true';
function MM_callJS(jsStr) { //v2.0
  return eval(jsStr)
}
function addCase(){
	var l = document.forms[0].caseList.length;
	var flag = 'false';
	validCaseID = 'true';
	if(document.forms[0].caseId.value != ''){
	
		strCaseID = new String(document.forms[0].caseId.value);
		if (strCaseID.length > 0) {
			if (strCaseID.length != 12) {
				alert("Case ID must be 12 characters string.");
				validCaseID = 'false';
				document.forms[0].caseId.focus();
				return;
			}
			if ((strCaseID.charAt(0) != 'C') || (strCaseID.charAt(1) != 'U') && (strCaseID.charAt(1) != 'I') && (strCaseID.charAt(1) != 'X')) {
				alert("Case ID must start with CU, CI, or CX.");
				validCaseID = 'false';
				document.forms[0].caseId.focus();
				return;
			}
		}
		
		for ( i = 0; i < l ; i++) {
			if ( document.forms[0].caseList[i].value == document.forms[0].caseId.value) {
				flag = 'true';
			}
	}
	
	if (flag != 'true'){
		document.forms[0].caseList.options[document.forms[0].caseList.length] = new Option(document.forms[0].caseId.value, document.forms[0].caseId.value);
		document.forms[0].caseId.value = '';
	} else {
		document.forms[0].caseId.value = '';
	}
	}
	document.forms[0].caseId.focus();
	
}

function removeCase(){
	var len = document.forms[0].caseList.length;
	
	for ( i = (len -1); i>=0; i--){
        if (document.forms[0].caseList.options[i].selected == true ) {
           document.forms[0].caseList.options[i] = null;
        }
    }
	document.forms[0].caseId.focus();
	
}

function removeAllCases(){
	selectAllCases();
	removeCase();
}

function selectAllCases(){
	for (i=0; i<document.forms[0].caseList.length; i++) { 
		document.forms[0].caseList.options[i].selected = true; 
	} 
}
var validSampleID = 'true';

function addSample(){
	var l = document.forms[0].sampleList.length;
	var flag = 'false';
	validSampleID = 'true';
	if(document.forms[0].sampleId.value != ''){
	
		strSampleID = new String(document.forms[0].sampleId.value);
		if (strSampleID.length > 0) {
			if (strSampleID.length != 10) {
				alert("Sample ID must be 10 characters string.");
				validSampleID = 'false';
				document.forms[0].sampleId.focus();
				return;
			}
			if ((strSampleID.slice(0,2) != 'FR') && (strSampleID.slice(0,2) != 'PA') && (strSampleID.slice(0,2) != 'SX')) {
				alert("Sample ID must start with FR, PA, or SX.");
				validSampleID = 'false';
				document.forms[0].sampleId.focus();
				return;
			}
		}
		
		for ( i = 0; i < l ; i++) {
			if ( document.forms[0].sampleList[i].value == document.forms[0].sampleId.value) {
				flag = 'true';
			}
	}
	
	if (flag != 'true'){
		document.forms[0].sampleList.options[document.forms[0].sampleList.length] = new Option(document.forms[0].sampleId.value, document.forms[0].sampleId.value);
		document.forms[0].sampleId.value = '';
	} else {
		document.forms[0].sampleId.value = '';
	}
	}
	document.forms[0].sampleId.focus();
}

function removeSample(){
	var len = document.forms[0].sampleList.length;
	
	for ( i = (len -1); i>=0; i--){
        if (document.forms[0].sampleList.options[i].selected == true ) {
           document.forms[0].sampleList.options[i] = null;
        }
    }
	document.forms[0].sampleId.focus();
}

function removeAllSamples(){
	selectAllSamples();
	removeSample();
}

function selectAllSamples(){
	for (i=0; i<document.forms[0].sampleList.length; i++) { 
		document.forms[0].sampleList.options[i].selected = true; 
	} 
}

function finish(){
  setActionButtonEnabling(false);

	addCase();
	selectAllCases();
	addSample();
	selectAllSamples();
	
	if (validCaseID == 'true' && validSampleID == 'true') {
	  return true;
	}
	else {
    setActionButtonEnabling(true);
    return false;
	}
}
   
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.forms[0];
  
  f.Submit.disabled = isDisabled;
  f.btnClear.disabled = isDisabled;
  setInputEnabled(f, "Submit2", isEnabled);
  setInputEnabled(f, "Submit3", isEnabled);
  setInputEnabled(f, "Submit4", isEnabled);
  setInputEnabled(f, "btnAddFromClip", isEnabled);
}

function addSamplesFromClipboard() {
  var added = false;
  var clipboard = getClipboardText();
  if (clipboard != null) {
    var re = /\s*,\s*|\s+/;
    var samples = clipboard.split(re);
    if (samples.length > 0) {
      var options = document.forms[0].sampleList.options;
      var len = document.forms[0].sampleList.length;
      var list = "";
      for (var i = 0; i < len; i++) {
        list += "," + options[i].value;
      }
      re = /(^(SX|FR|PA)(\d|[A-F]){8}$)/;
      for (i = 0; i < samples.length; i++) {
        var sampleId = samples[i];
        if ((sampleId.search(re) != -1) && (list.indexOf(sampleId) == -1)) {
          options[len++] = new Option(sampleId, sampleId);
          added = true;
        }
      }
    }
  }
  if (!added) alert('No new valid sample barcode IDs were present in the clipboard');
}

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = "<%=myBanner%>";
}
</script>
</head>
<body class="bigr" onload="initPage();">
<form name="theForm" method="post" action="<html:rewrite page='/iltds/Dispatch'/>"
      onsubmit="return(finish());">
  <input type="hidden" name="op" value="<%=submitToOp%>">
  <input type="hidden" name="<%=LineItemHelper.ID_LINEITEM_ID%>" value="<%=liHelper.getRawId()%>">
  <input type="hidden" name="<%=ProjectHelper.ID_PROJECT_ID%>" value="<%=helper.getRawId()%>">
  <div align="center">
   <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr>  
        <td> 

<!-- The line item -->        
          <table class="foreground" cellpadding="3" cellspacing="1" border="0">
            <%=helper.getMessagesForColspan(4)%>
            <tr> 
              <td class="yellow" colspan="4" align="center"> 
                <b>Line Item #<%=liHelper.getLineItemNumber()%></b>
              </td>
            </tr>
            <tr><td colspan="4"></td></tr>
			      <tr class="white"> 
              <td class="grey" align="right"> 
                <b>Requested Quantity:</b>
              </td>
              <td align="left"> 
                <%=liHelper.getQuantity()%>
              </td>
              <td class="grey" align="right"> 
                <b>Format:</b>
              </td>
              <td align="left"> 
                <%=liHelper.getFormat()%>
              </td>
            </tr>
<%
if (liHelper.hasNotes()) {
%>
			      <tr class="white"> 
              <td class="grey" align="right"> 
                <b>Description:</b>
              </td>
              <td align="left" colspan="3"> 
                <%=Escaper.htmlEscapeAndPreserveWhitespace(liHelper.getNotes())%>
              </td>
            </tr>
<%
}
%>
          </table>
        </td>
      </tr>
   </table>

<!-- The search criteria -->        
   <hr style="color: #336699;">
    <table border="0" cellspacing="0" cellpadding="0" class="background">
      <tr> 
        <td> 
          <table border="0" cellspacing="1" cellpadding="3" vspace="0" class="foreground">
            <tr class="yellow"> 
              <td colspan="4" align="center"> 
                <b>Choose samples that are on hold:</b> 
              </td>
            </tr>
            <tr class="white"> 
              <td colspan="4">
                <table border="0" cellspacing="0" cellpadding="0" class="background" width="100%">
            		  <tr> 
                    <td> 
                      <table border="0" cellspacing="1" cellpadding="3" vspace="0" class="foreground" width="100%">
                        <tr class="white"> 
                          <td class="grey" align="right"> 
                            <b>On Hold For:</b>
                          </td>
                          <td> 
                            <%=helper.getShoppingCartList(cart)%>
			                    </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
            <tr class="green"> 
              <td colspan="4" align="center"><b>OR</b></td>
            </tr>
            <tr> 
              <td class="yellow" colspan="4" align="center"> 
                <b>Specific:</b> Please enter one of the following 
              </td>
 

            <tr class="white"> 
              <td class="grey" align="right"> 
                <b>Enter a Case ID:</b>
              </td>
              <td align="center"> 
                <input type="text" name="caseId" size="13" maxlength="12" >
              </td>
              <td align="center"> 
                  
                <input type="button" name="Submit2" value="Add &gt;" onclick="addCase()">
                  <p style="margin: 1px 1px 1px 1px"></p>
                  <input type="button" name="Submit3" value="< Remove" onclick="removeCase()">
                  <p style="margin: 1px 1px 1px 1px"></p>
                  <input type="button" name="Submit4" value="<< Remove All" onclick="removeAllCases()">
              </td>
              <td align="center">
          				<b>List of Cases:</b><br>
                  <select name="caseList" size="5" multiple></select>
<%
if (caseIds != null) {
  for (int i = 0; i < caseIds.size(); i++) {
    String caseId = (String)caseIds.get(i);
%>                  
<script language="JavaScript">
		document.forms[0].caseList.options[<%=i%>] = new Option('<%=caseId%>', '<%=caseId%>');
</script>
<%
  }
}
%>                  
              </td>
            </tr>
            <tr class="green"> 
              <td colspan="4" align="center"><b>OR</b></td>
            </tr>
            <tr class="white"> 
              <td class="grey" align="right"> 
                <b>Enter a Sample ID:</b>
              </td>
              <td align="center"> 
                <input type="text" name="sampleId" size="13" maxlength="10" >
              </td>
              <td align="center"> 
                  <input type="button" name="btnAddFromClip" value="Add Clipboard" onclick="addSamplesFromClipboard();">
                  <p style="margin: 1px 1px 4px 1px"></p>
                  <input type="button" name="Submit2" value="Add &gt;" onclick="addSample()">
                  <p style="margin: 1px 1px 1px 1px"></p>
                  <input type="button" name="Submit3" value="< Remove" onclick="removeSample()">
                  <p style="margin: 1px 1px 1px 1px"></p>
                  <input type="button" name="Submit4" value="<< Remove All" onclick="removeAllSamples()">
              </td>
              <td align="center">
          				<b>List of Samples:</b><br>
                  <select name="sampleList" size="5" multiple></select>
              </td>
<%
if (sampleIds != null) {
  for (int i = 0; i < sampleIds.size(); i++) {
    String sampleId = (String)sampleIds.get(i);
%>                  
<script language="JavaScript">
		document.forms[0].sampleList.options[<%=i%>] = new Option('<%=sampleId%>', '<%=sampleId%>');
</script>
<%
  }
}
%>                  

           <tr class="white"> 
              <td colspan="4"> 
                <div align="center"> 
                  <input type="submit" name="Submit" value="Search">
                  <input type="hidden" name="clear" value="">
                  <input type="submit" name="btnClear" value="Clear" onClick="document.theForm.clear.value='yes';">
                </div>
              </td>
            </tr>

          </table>
        </td>
      </tr>
    </table>
   </div>
</form>
</body>
</html>
