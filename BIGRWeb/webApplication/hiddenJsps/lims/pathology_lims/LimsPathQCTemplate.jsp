<%@ page language="java" import="java.util.*" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.lims.helpers.*"%>
<%@ page import="com.ardais.bigr.lims.helpers.LimsConstants" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-template" prefix="template" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic"%>
<%@ taglib uri="/tld/struts-bean"  prefix="bean" %>
<html>
  <head>
    <title><template:get name='title'/></title>
    <link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <script type="text/javascript" src="<html:rewrite page="/js/overlib.js"/>"> </script>
    <script type="text/javascript" src='<html:rewrite page="/js/BigrLib.js"/>'></script>
    <script type="text/javascript" src='<html:rewrite page="/js/common.js"/>'></script>
	<script language="JavaScript" src='<html:rewrite page="/js/calendar.js"/>'></script>

    <script type="text/javascript">
      var myBanner = '<template:get name='title'/>';

var markedSampleList = new Array();

// If this is set to true by the time body.onload is called, the incident
// report popup will be displayed.
var isShowViewIncidentReportPopup = false;
// If this is set to true by the time body.onload is called, the new evaluation
// popup will be displayed.
var isShowQCEditPopup = false;
	  
function addSamplesToClipboard() {
    // Use newline to separate clipboard items.  MR 6856.
	var clipboard = "";
	if (markedSampleList.length > 0) {
	  clipboard = markedSampleList.toString().replace(/,/g, "\n");
	}
	copyToClipboard(clipboard);
}

function changeMarkedSample(obj) {
//alert('in changeMarkedSample for ' + obj.value);
	if(obj.checked){
		markedSampleList.push(obj.value);				
	} else {
		removeFromMarkedSample(obj.value);
	}
}

function removeFromMarkedSample(sample) {
	for ( i = 0 ; i < markedSampleList.length ; i++) {
		if(sample == markedSampleList[i]){
			markedSampleList.splice(i, 1);
			break
		}
	}
}

//function to mark as checked any check boxes on the page that are not
//already checked
function markAllOnPage() {
	var marked = document.forms[0].markedSamples;
	if (marked != null) {
		//if there's just a single check box handle that
		if (marked.type == "checkbox") {
			var newCheck = marked;
			if (!newCheck.checked) {
		    	//alert('Marking ' + newCheck.value + ' checked.');
				newCheck.checked = true;
				newCheck.fireEvent("onClick");
			}
		}
		//otherwise there are multiple checkboxes so handle that
		else {
			for(i = 0 ; i < marked.length ; i++){
				var newCheck = marked[i];
				if (!newCheck.checked) {
		    		//alert('Marking ' + newCheck.value + ' checked.');
					newCheck.checked = true;
					newCheck.fireEvent("onClick");
				}
			}
		}
	}
}

function clearSearch(){

	//reset the pathologist dropdown to be the default of "All"
	var pathListOptions = document.forms[0].pathologistList.options;
	for(i = 0 ; i < pathListOptions.length ; i++) {
		var newOption = pathListOptions[i];
		if(newOption.value == '<%= LimsConstants.DEFAULT_ALL %>'){
			newOption.selected = true;
		} else {
			newOption.selected = false;
		}
	}
	
	//reset the logical repository dropdown to be the default of "All"
	var logicalRepositoryOptions = document.forms[0].logicalRepositoryList.options;
	for(i = 0 ; i < logicalRepositoryOptions.length ; i++) {
		var newOption = logicalRepositoryOptions[i];
		if(newOption.value == '<%= LimsConstants.DEFAULT_ALL %>'){
			newOption.selected = true;
		} else {
			newOption.selected = false;
		}
	}
	
	//reset the QC Status check boxes to be the default of "PV'd but not released"
	var includeListChecks = document.forms[0].includeList;
	for(i = 0 ; i < includeListChecks.length ; i++) {
		var newCheck = includeListChecks[i];
		if(newCheck.value == '<%= LimsConstants.PATH_QC_SAMPLES_FOR_RELEASE %>'){
			newCheck.checked = true;
		} else {
			newCheck.checked = false;
		}		 
	}
	
	//clear any "Date Last PVed" dates that have been specified
	document.forms[0].startDate.value = '';
	document.forms[0].endDate.value = '';
	
	//select the "Both" radio button under PV Status
	document.forms[0].pvStatus[2].checked = true;
	
	//reset the sort order to be "Evaluation Reported Date"
	var sortValueOptions = document.forms[0].sortValue.options;
	for(i = 0 ; i < sortValueOptions.length ; i++) {
		var newOption = sortValueOptions[i];
		if(newOption.value == '<%= LimsConstants.PATH_QC_SORT_REPORTEDDATE %>'){
			newOption.selected = true;
		} else {
			newOption.selected = false;
		}
	}
	
	//reset the sort direction to be "Ascending"
	var sortOrderValueOptions = document.forms[0].sortOrderValue.options;
	for(i = 0 ; i < sortOrderValueOptions.length ; i++) {
		var newOption = sortOrderValueOptions[i];
		if(newOption.value == '<%= LimsConstants.SORT_ORDER_ASC %>'){
			newOption.selected = true;
		} else {
			newOption.selected = false;
		}
	}
	
	//clear any ids that have been specified
	removeCase();
	removeSample();
	removeSlide();

}

function clearMarks(){
	
	var marked = document.forms[0].markedSamples;
	
	for(i = 0 ; i < marked.length ; i++){
		var newCheck = marked[i];
		newCheck.checked = false;
	}
}


function removeCase(){
	var len = document.forms[0].caseIdList.length;
	
	for ( i = (len -1); i>=0; i--){
        //if (document.forms[0].caseIdList.options[i].selected == true ) {
           document.forms[0].caseIdList.options[i] = null;
        //}
    }
	
}

function selectAllCases(){
	for (i=0; i<document.forms[0].caseIdList.length; i++) { 
		document.forms[0].caseIdList.options[i].selected = true; 
	} 
}

function removeSample(){
	var len = document.forms[0].sampleIdList.length;
	
	for ( i = (len -1); i>=0; i--){
		document.forms[0].sampleIdList.options[i] = null;
    }
}

function selectAllSamples(){
	for (i=0; i<document.forms[0].sampleIdList.length; i++) { 
		document.forms[0].sampleIdList.options[i].selected = true; 
	} 
}

function removeSlide(){
	var len = document.forms[0].slideIdList.length;
	
	for ( i = (len -1); i>=0; i--){
        document.forms[0].slideIdList.options[i] = null;
    }
}

function selectAllSlides(){
	for (i=0; i<document.forms[0].slideIdList.length; i++) { 
		document.forms[0].slideIdList.options[i].selected = true; 
	} 
}

function positionItems() {
    var detailsDiv = document.all.Details;
    var maxHeight = document.body.clientHeight - detailsDiv.offsetTop - 5;
    if (maxHeight < 200) {
      maxHeight = 200;
    }

    <%-- Setting the detailsDiv width to a specific size then back to 100%
      -- works around an IE bug where the detailsDiv would come up initially
      -- wider than it should have been (by the width of a scroll bar). --%>
    detailsDiv.style.width = detailsDiv.offsetWidth - 1;
    detailsDiv.style.width = "100%";
    detailsDiv.style.height = maxHeight;
}

var priorResizeHeight = 0;

function onResizePage() {    
    var currentHeight = document.body.clientHeight;
    if (currentHeight == priorResizeHeight) return;
    priorResizeHeight = currentHeight;
    positionItems();
    
}

window.onresize = onResizePage;
  
function initPage(){ 
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
  if (isShowViewIncidentReportPopup) showViewIncidentReportPopup();
  if (isShowQCEditPopup) showQCEditPopup();
  resizePage();
}  

function showViewIncidentReportPopup() {
	var w = window.open(
	  '<html:rewrite page="/lims/limsViewIncidentReport.do"/>',
	  'IncidentReportWindow',
	  'scrollbars=yes, status=yes, resizable=yes,width=700,height=700');
	w.focus();
}

function showQCEditPopup() {
	var w = window.open(
	  '<html:rewrite page="/lims/limsManageEvaluations.do"/>?popup=true&editCopyId=<%= request.getAttribute("QCEditSlideId") %>&editCopyButton=true&sourceEvaluationId=<%= request.getAttribute("QCEditEvaluationId")%>', 
	  'ViewPathologyEvaluationReport', 
	  'scrollbars=yes, status=yes, resizable=yes,width=1024,height=725,top=0,left=0');
	w.focus();
}

function showOrHideMenu(){
	if(document.all.searchCriteria.style.display == 'inline'){
		//alert("none");
		document.all.searchCriteria.style.display = 'none';
		document.all.titleDiv.innerText = 'Search (Click to show search criteria)';
	} else {
		//alert("inline");
		document.all.searchCriteria.style.display = 'inline';
		document.all.titleDiv.innerText = 'Search (Click to hide search criteria)';
	}
	
	resizePage();
}

function resizePage(){
	document.all.Header.style.width = document.all.Header.offsetWidth - 1;
    document.all.Header.style.width = "100%";
	document.all.Details.style.width = document.all.Details.offsetWidth - 1;
    document.all.Details.style.width = "100%";
	
	var maxHeight = document.body.clientHeight - document.all.Header.offsetTop - 5;
	document.all.Details.style.height = maxHeight - document.all.Header.clientHeight;

	<logic:present name="limsPathQcForm" property="targetSampleId">
	    <logic:notEqual name="limsPathQcForm" property="targetSampleId" value="">
	  	  document.all.<bean:write name="limsPathQcForm" property="targetSampleId"/>.scrollIntoView(true);
	  	</logic:notEqual>
	</logic:present>
	

}



function submitPagerForm(index) {
    saveDisplaySettings();
    var form = document.forms[0];
    
    if(!form.submitted){
      if(index < 1){
        form.index.value = 1;
      } else {
         form.index.value = index;
      }
      form.submit();
    }
  }

function invokeFunction(sampleId, name) { 
   saveDisplaySettings();  
   if ((name == '<%=LimsConstants.LIMS_TX_PULL%>') || (name == '<%=LimsConstants.LIMS_TX_UNPULL%>')) {
   	  var reason = null;
   	  var reportMostRecent = null;
      var returnedObject = window.showModalDialog('<html:rewrite page="/lims/limsPullDiscordantSample.do"/>?reasonType=' + name, 'resizable:yes;help:no;dialogWidth:200px;dialogHeight:200px', name);
      if (returnedObject != null) {
      	reason = returnedObject.reasonText;
   		reportMostRecentEval = returnedObject.reportMostRecentEval; 
      }
      if ((reason != null) && (reason != 'undefined')) {
        document.limsPathQcForm.elements['reason'+sampleId].value = reason;          
   		document.limsPathQcForm.targetSampleId.value = sampleId;
   		document.limsPathQcForm.sampleFunction.value = name;
   		if (name == '<%=LimsConstants.LIMS_TX_UNPULL%>') {
	      if (reportMostRecentEval == 'true') {
	        document.limsPathQcForm.reportMostRecentEval.value = 'true';
	      }
		}
        document.limsPathQcForm.submit();
      }
   } else {  
   	 document.limsPathQcForm.targetSampleId.value = sampleId;
   	 document.limsPathQcForm.sampleFunction.value = name;        
     document.limsPathQcForm.submit();
   }  
}

function checkInputAndAdd(input, list, type, doAlert) {
	if(document.all[input].value.length == 0) {
		return true;
	}

	var len = document.all[list].length;
	var isFound = false;
	for (i = 0; i < len ; i++) {
		if (document.all[list][i].value == document.all[input].value) {
			isFound = true;
		}
	}
	if (!isFound) {
		var isValid = false;
		if ( type == CASE_TYPE) {
			isValid = isValidCaseIdPrefix_Alert(document.all[input].value, doAlert);
			}
		else	
			isValid = isValidId_Alert(document.all[input].value, type, doAlert);
					
		if (isValid) {
			document.all[list].options[len] = new Option(document.all[input].value, document.all[input].value);
			document.all[input].value = '';
			document.all[input].focus();
			return true;
		}
		else {
			return false;
		}
	}
	else {
		document.all[input].value = '';
		document.all[input].focus();
		return true;
	}
}

function handleIdsOnSubmit(){

	addItemToList('consentID','caseIdList', CASE_TYPE, false); 
	addItemToList('sampleID','sampleIdList', SAMPLE_TYPE, false); 
	addItemToList('slideID','slideIdList', SLIDE_TYPE, false); 
	selectAllCases(); 
	selectAllSamples(); 
	selectAllSlides();
	saveDisplaySettings();

	return true;
}

function handleSubmit() {

    if (!addItemToListPrefix('consentID', 'caseIdList')) {
    	return false;
    }

    if (!checkInputAndAdd('sampleID', 'sampleIdList', SAMPLE_TYPE, true)) {
    	return false;
    }
    if (!checkInputAndAdd('slideID', 'slideIdList', SLIDE_TYPE, true)) {
    	return false;
    }
    selectAllCases();
    selectAllSamples();
    selectAllSlides();
    saveDisplaySettings();
    document.all.wholePage.style.display = 'none';
    if (parent.topFrame) { parent.topFrame.openPopup('searching'); }
    return true;
}

function saveDisplaySettings() {
	 document.forms[0].searchCriteriaStyle.value = document.all.searchCriteria.style.display;
}

function setAllQcStatus() {
	//alert("calling setAllQcStatus");
	var includeListChecks = document.forms[0].includeList;
	var checkField;
	for(i = 0 ; i < includeListChecks.length ; i++) {
		checkField = includeListChecks[i];
		checkField.checked = true;
		}
	
}

function doOnQCSelect(ev, sampleId) {
	var name = ev.srcElement.value;
	if ((name == '<%=LimsConstants.LIMS_TX_PULL%>') || (name == '<%=LimsConstants.LIMS_TX_UNPULL%>')) {
		var reason = null;
		var returnedObject = window.showModalDialog('<html:rewrite page="/lims/limsPullDiscordantSample.do"/>?reasonType=' + name, 'resizable:yes;help:no;dialogWidth:200px;dialogHeight:200px', name);
		if (returnedObject != null) {
			reason = returnedObject.reasonText;
		}
		if ((reason != null) && (reason != 'undefined')) {
	        document.limsPathQcForm.elements['reason'+sampleId].value = reason; 
        } else {
            var i;
            for (i = 0; i < document.limsPathQcForm.elements['QC'+sampleId].length; i++) {
            	if (document.limsPathQcForm.elements['QC'+sampleId][i].value == 'none') {
            		document.limsPathQcForm.elements['QC'+sampleId][i].checked = 'true';
            	}
            }
         	
        }
	}
}

    </script>
  </head>
  <body class="bigr" onLoad="initPage();" onunload="if (parent.topFrame) { parent.topFrame.closePopup(); }" style="margin-top: 0; margin-bottom: 0;">
  <div id="wholePage">
  <html:form  action="/lims/limsPathQC" target="mainFrame" onsubmit="return handleSubmit()">
    <html:hidden property="reportMostRecentEval" value="false"/>
    
    <div id="Header" style="width:100%; z-index: 2; "> 
      <template:get name='header' flush="true"/>      
    </div>
    
    <div id="Details" style="width:100%;  overflow: auto; border-width: 1px 1px 1px 1px; border-style: solid; border-color: #336699;"> 
      
      <template:get name='detail' flush="true"/>
    </div>
    </html:form>
  </div>  
  </body>
  
</html> 
