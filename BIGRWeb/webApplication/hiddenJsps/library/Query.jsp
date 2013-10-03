<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.security.*"%>
<%@ page import="com.ardais.bigr.util.Constants"%>
<%@ page import="org.apache.commons.logging.*"%>
<%@ page import="com.ardais.bigr.api.ApiLogger"%>
<%@ page import="com.ardais.bigr.library.web.helper.ResultsHelper"%>
<%@ page import="com.ardais.bigr.util.WebUtils" %> 
<%@ page import="com.ardais.bigr.iltds.helpers.IltdsUtils"%>
<%@ page import="com.gulfstreambio.kc.form.def.query.QueryFormDefinition"%>
<%@ page import="com.ardais.bigr.kc.form.helpers.FormUtils" %>
<%@ page import="com.ardais.bigr.api.ApiProperties"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>

<bean:define name="queryForm" id="myForm" type="com.ardais.bigr.library.web.form.QueryForm"/>
<%
QueryFormDefinition[] kcQueryForms = myForm.getKc().getQueryFormDefinitions();
boolean hasKcQueryForms = kcQueryForms.length > 0;
String tab = (hasKcQueryForms) ? "kc" : "diagnosis";
%>
<bean:parameter id="currentTab" name="currentTab" value="<%=tab%>"/>

<%	// -- Standard block for all query pages to find the proper txType
	// -- also MUST put a hidden txType field in the form
	String txType = null;
	com.ardais.bigr.library.web.form.QueryForm qform = 
		(com.ardais.bigr.library.web.form.QueryForm) request.getAttribute("queryForm");
	if (qform != null) {
		txType = qform.getTxType();
	}
	else {
		com.ardais.bigr.library.web.form.ResultsForm rform = 
			(com.ardais.bigr.library.web.form.ResultsForm) request.getAttribute("resultsForm");
		if (rform != null) {
			txType = rform.getTxType();
		}
		else {
			txType = request.getParameter("txType");
		}
	}
%>

<%
	String viewQuerySummary = null;
	viewQuerySummary = request.getParameter("viewQuerySummary");

	String bannertxt;
	if (ResultsHelper.TX_TYPE_SAMP_SEL.equals(txType))
		bannertxt = "Sample Selection Query";
	else if (ResultsHelper.TX_TYPE_SAMP_REQUEST.equals(txType))
		bannertxt = "Request Samples Query";
	else
		bannertxt = "Query for Samples";	


    SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);
    boolean showAliasPattern = false;
    if (securityInfo.isInRoleSystemOwnerOrDi()) {
      showAliasPattern = true;
    }

	boolean boolStorageLocation = false;
	if (IltdsUtils.isUserLocaltoStorageUnit(securityInfo.getUsername())) {
		boolStorageLocation = true;
	}

	
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html> 
<head>
	<title>Sample Selection Query</title>

	<link rel="stylesheet" type="text/css" href='<html:rewrite page="/css/bigr.css"/>'>
	<jsp:include page="/hiddenJsps/kc/query/misc/scriptAndCss.jsp" flush="true"/>
	<script type="text/javascript" src='<html:rewrite page="/js/common.js"/>'></script>
	<script type="text/javascript" src='<html:rewrite page="/js/BigrLib.js"/>'></script>
	<script language="JavaScript" src='<html:rewrite page="/js/calendar.js"/>'></script>
	<script language="JavaScript" src='<html:rewrite page="/js/registration.js"/>'></script>
	<script type="text/javascript"
	src='<html:rewrite page="/js/overlib.js"/>'><!-- overLIB (c) Erik Bosrup --></script>

<script type="text/javascript">

var myBanner = '<%=bannertxt%>';

var tabLabels = new Array();
var tabTitles = new Array();
tabLabels["id"] = '<bean:message key="library.ss.query.id.tabname"/>';
tabTitles["id"] = '<bean:message key="library.ss.query.id.title.label"/>';
tabLabels["attributes"] = '<bean:message key="library.ss.query.attributes.tabname"/>';
tabTitles["attributes"] = '<bean:message key="library.ss.query.attributes.title.label"/>';
tabLabels["diagnosis"] = '<bean:message key="library.ss.query.diagnosis.tabname"/>';
<% if (ApiProperties.isEnabledLegacy()) { %>      
tabTitles["diagnosis"] = '<bean:message key="library.ss.query.diagnosis.title.label.legacy"/>';
<% } else { %>      
tabTitles["diagnosis"] = '<bean:message key="library.ss.query.diagnosis.title.label"/>';
<% } %>      
<% if (ApiProperties.isEnabledLegacy()) { %>      
tabLabels["diagnosticTest"] = '<bean:message key="library.ss.query.diagnosticTest.tabname"/>';
tabTitles["diagnosticTest"] = '<bean:message key="library.ss.query.diagnosticTest.title.label.path"/>';
<bigr:hasPrivilege priv="<%=SecurityInfo.PRIV_PSA_DRE%>">
tabTitles["clinLabTest"] = '<bean:message key="library.ss.query.diagnosticTest.title.label.clintests"/>';
tabTitles["clinFind"] = '<bean:message key="library.ss.query.diagnosticTest.title.label.clinfindings"/>';
</bigr:hasPrivilege>
<% } %>      
tabLabels["tissue"] = '<bean:message key="library.ss.query.tissue.tabname"/>';
tabTitles["tissue"] = '<bean:message key="library.ss.query.tissue.title.label"/>';
<%
if (hasKcQueryForms) {
%>
tabLabels["kc"] = '<bean:message key="library.ss.query.kc.tabname"/>';
tabTitles["kc"] = '<bean:message key="library.ss.query.kc.title.label"/>';
<%
}
%>


function openModelessPopup(relativeUrl, windowName, features) {
	var context = "<html:rewrite page=""/>";
	var url = context + relativeUrl;
	window.open(url, windowName, features);
}

function openHierarchyWindow(url, windowName) {
	openModelessPopup(url, windowName, 'status=yes,scrollbars=yes,resizable=yes,width=550,height=500');
}

function openTissueHierarchy(tissueKey) {
	openHierarchyWindow('/library/dxTcHierarchyStart.do?type=T&txType=<%=txType%>&id=' + tissueKey, 'tissueHierarchy');
}

function openDiagnosisHierarchy(diagnosisKey) {
	openHierarchyWindow('/library/dxTcHierarchyStart.do?type=D&txType=<%=txType%>&id=' + diagnosisKey, 'dxHierarchy');
}

function viewQuerySummary() {
	selectAllIds();
	<%
	if (hasKcQueryForms) {
	%>
		document.all["kc.queryCriteria"].value = <%=com.gulfstreambio.kc.web.support.WebUtils.getJavaScriptQueryElementsToRequestParameter()%>
	<%
	}
	%>

	document.forms[0].action = '<html:rewrite page="/library/viewQuerySummary.do?viewQuerySummary=True"/>';
	document.forms[0].submit();
}
function clrQuery(val) {
	document.all.clearQuery.value = val; 
	selectAllIds();
	<%
	if (hasKcQueryForms) {
	%>
		document.all["kc.queryCriteria"].value = <%=com.gulfstreambio.kc.web.support.WebUtils.getJavaScriptQueryElementsToRequestParameter()%>
	<%
	}
	%>
	document.forms[0].action = '<html:rewrite page="/library/queryClear.do"/>';
	document.forms[0].submit();
}

function executeQuery() {
	document.all.clearQuery.value = 'false'; 
	if(!selectAllIds()) {return;}
<%
if (hasKcQueryForms) {
%>
	document.all["kc.queryCriteria"].value = <%=com.gulfstreambio.kc.web.support.WebUtils.getJavaScriptQueryElementsToRequestParameter()%>
<%
}
%>
	document.all.wholePage.style.display = 'none';
  if (parent.topFrame) parent.topFrame.openPopup('searching');	
	document.forms[0].submit();
	return true;
}

<bigr:hasPrivilege priv="<%=SecurityInfo.PRIV_SHOW_SQL_TAB%>">
function showSql() {
	selectAllIds();
<%
if (hasKcQueryForms) {
%>
	document.all["kc.queryCriteria"].value = <%=com.gulfstreambio.kc.web.support.WebUtils.getJavaScriptQueryElementsToRequestParameter()%>
<%
}
%>
	document.forms[0].action = '<html:rewrite page="/library/querySql.do"/>';
	document.forms[0].submit();
}
</bigr:hasPrivilege>


//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//\\ This function already exist in BigrLib.js. The difference is in this 
//\\ function, in case of valid id, focus is sent to the input control if the
//\\ current tab is ID. If this function is call when other tab has focus, 
//\\ focus will not be sent to input control.
//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

function addItemToList(input, list, type, doAlert){
  if(document.all[input].value.length == 0) {return true;}
  var len = document.all[list].length;
  var isFound = false;
  for ( i = 0; i < len ; i++) {
    if ( document.all[list][i].value == document.all[input].value) {
      isFound = true;
    }
  }
  if (!isFound){
    var isValid = false;

    isValid = isValidId_Alert(document.all[input].value, type, doAlert);
       
    if(isValid){
      document.all[list].options[len] = new Option(document.all[input].value, document.all[input].value);
      document.all[input].value = '';
      if (document.all.currentTab.value == "id") {
      	document.all[input].focus();
      }
    }
    else {
      document.all[input].focus();
      return false;
    }
  } 
  else {
    	document.all[input].value = '';
    	document.all[input].focus();
  }
  return true;
}

//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//\\ This function already exist in BigrLib.js.  Two differences in this version:
//\\ 1. Limited validation is done here since this is to be used in conjunction
//\\ with ValidateIds on the server 
//\\ 2. In the case of valid id, focus is sent to the input control if the
//\\ current tab is ID. If this function is call when other tab has focus, 
//\\ focus will not be sent to input control.
//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

function addItemToListPrefix(input, list, type){
  //alert("in addItemToListPrefix, input = " + input);
  //alert("in addItemToListPrefix, list = " + list);
  if(document.all[input].value.length == 0) {return true;}
  
  var len = document.all[list].length;
  var isFound = false;

  //alert("in addItemToListPrefix, pre len-loop");
  for ( i = 0; i < len ; i++) {
    if ( document.all[list][i].value == document.all[input].value) {
      isFound = true;
    }
  }
  
  //alert("in addItemToListPrefix, post len-loop, isfound = " + isFound);  
  if (!isFound){

	  // MR 6620 bad characters "getting through" when hitting execute, so
	  // added logic to not process if there are bad characters.  this is
	  // likely to be a rare occurrence (probably a "fatfinger" episode), so
	  // no need for detailed error processing (or an alert message)	
	  if (type == "DONOR") {
	  	if (!(isValidDonorIdPrefix_Alert(document.all[input].value, false)))
	  		{
  		    document.all[input].value = '';
  		    return true;
  		    }
	  	}
	  else if (type == "CASE") {
	  	if (!(isValidCaseIdPrefix_Alert(document.all[input].value, false)))
	  		{
  		    document.all[input].value = '';
  		    return true;
  		    }	  
	  	}
	  	
      document.all[list].options[len] = new Option(document.all[input].value, document.all[input].value);
      document.all[input].value = '';
      if (document.all.currentTab.value == "id") {
      	document.all[input].focus();
      }
  } 
  else {
    	document.all[input].value = '';
    	document.all[input].focus();
  }
  return true;
}



function selectAllIds() {
  if (!addItemToListPrefix("id.donorId","id.donorIds", "DONOR")) {return false;}
  selectAllFromList("id.donorIds");
  if (!addItemToListPrefix("id.caseId","id.caseIds", "CASE")) {return false;}
  selectAllFromList("id.caseIds");
  if (!addItemToList("id.itemId","id.itemIds", ITEM_TYPE, true)) {return false;}
  selectAllFromList("id.itemIds");
<%
  if (showAliasPattern) {
%>
  if (!addItemToList("id.aliasPattern","id.aliasPatterns", IGNORE_TYPE, true)) {return false;}
  selectAllFromList("id.aliasPatterns");
<%
  }
%>
  return true;
}


function clearStageTNM() {
	var isStage = (event.srcElement.name == "diagnosis.stage");
	
	if (isStage && document.all["diagnosis.stage"].selectedIndex > 0) {
		document.all["diagnosis.tumorStage"].selectedIndex = 0;
		document.all["diagnosis.lymphNodeStage"].selectedIndex = 0;
		document.all["diagnosis.distantMetastasis"].selectedIndex = 0;
	}
	else if (!isStage
					&& ((document.all["diagnosis.tumorStage"].selectedIndex > 0)
					|| (document.all["diagnosis.lymphNodeStage"].selectedIndex > 0)
					|| (document.all["diagnosis.distantMetastasis"].selectedIndex > 0))) {
		document.all["diagnosis.stage"].selectedIndex = 0;
	}
}

function enableGrossUnknownAppearance(isEnabled) {
    var appearances = document.all["appearance.appearanceBest"];
    for (var i=0; i<appearances.length; i++) {
    	if (appearances[i].value == "<%=Constants.APPEARANCE_GROSS_UNKNOWN%>") {
    	    if (isEnabled) {
    		    appearances[i].disabled=false;
    		}
    		else {
    		    appearances[i].disabled=true;
    		    appearances[i].checked=false;
    		}
    	}
    }
}

function updateGrossAppearance() {
	var grossApps = document.all["appearance.appearanceBest"];
	var isSomeGrossChecked = false;
	for (var i=0; i<grossApps.length; i++) {
		if ((grossApps[i].value == "<%=Constants.APPEARANCE_GROSS_NORMAL%>"
		    || grossApps[i].value == "<%=Constants.APPEARANCE_GROSS_DISEASED%>")
			&& grossApps[i].checked) {
				isSomeGrossChecked = true;
			}
	}
	enableGrossUnknownAppearance(isSomeGrossChecked);
}

function clearAppearances() {
	clearCheckboxGroup(document.all["appearance.appearanceBest"]);
}

function clearQcStatuses() {
	clearCheckboxGroup(document.all["attributes.qcStatus"]);
}

// for a group with one ANY checkbox, which is exclusive with others,
// handle the clicks of the ANY and the non-ANY checkboxes
function clearCheckboxGroup(checkboxes) {
	var isAny = (event.srcElement.value == "any");
	var isChecked = (event.srcElement.checked);
	
	// If Any is checked, then clear all others.
	if ((isAny) && (isChecked)) {
		for (var i = 0; i < checkboxes.length; i++) {
			if (checkboxes[i].value != "any") {
				checkboxes[i].checked = false;
			}
		}
	}
	else {
		// Get the any checkbox, and determine if anything else is checked.
		var anyCheckbox = null;
		for (var i = 0; i < checkboxes.length; i++) {
			if (checkboxes[i].value == "any") {
				anyCheckbox = checkboxes[i];
			}
			if (checkboxes[i].checked) {
				isChecked = true;
			}
		}

		// If anything other than Any is checked, then clear Any;
		// otherwise if nothing is checked, then set Any.
		anyCheckbox.checked = !isChecked;
	}	
}

// for gross appearances, first update gross (we may automatically clear "unknown")
// then check if all are unchecked
function clearGrossAppearances() {
	updateGrossAppearance();
	clearAppearances();
}

function resizeIdListButtons(idListName) {
	var buttons = document.all[idListName];
	var maxWidth = 0;
	for (var i = 0; i < buttons.length; i++) {
		var width = buttons[i].clientWidth;
		maxWidth = (width > maxWidth) ? width : maxWidth;
	}
	for (var i = 0; i < buttons.length; i++) {
		buttons[i].width = maxWidth;
	}
}

function resizeOrContains(listName, orContainsName) {
	document.all[orContainsName].width = document.all[listName].offsetWidth;
}

function setNot(radioName, containsLabelName) {
	var value = (document.all[radioName][0].checked) ?
			document.all[radioName][0].value :
			document.all[radioName][1].value;

	if(value == "true"){
	  //alert("true");
      document.all[containsLabelName].style.display = "none";
      document.all[containsLabelName + 'Not'].style.display = "block";
	} else {
	  //alert("false");
	  document.all[containsLabelName].style.display = "block";
	  document.all[containsLabelName + 'Not'].style.display = "none";
	}
}

function hideTab(id) {
  document.all[id + "Div"].style.display = "none";
	document.all[id + "Div"].className = "libraryTabContents";
  document.all[id + "Title"].style.display = "none";
	document.all[id + "Title1"].className = "libraryTabTitle";
	document.all[id + "Title1"].innerHTML = "<b>" + tabLabels[id] + " - " + tabTitles[id] + "</b>";
	document.all[id + "Title2"].className = "libraryTabTitle";
  document.all[id + "Title2"].style.display = "block";
	document.all[id + "Title3"].className = "libraryTabTitle";
  document.all[id + "Td"].className = "libraryTab";
  
<% if (ApiProperties.isEnabledLegacy()) { %>      
<bigr:hasPrivilege priv="<%=SecurityInfo.PRIV_PSA_DRE%>">
  if (id == "diagnosticTest") {
	  document.all["clinLabTestTitle"].style.display = "none";
		document.all["clinLabTestTitle1"].className = "libraryTabTitle";
		document.all["clinLabTestTitle1"].innerHTML = "<b>" + tabLabels[id] + " - " + tabTitles["clinLabTest"] + "</b>";
		document.all["clinLabTestTitle2"].className = "libraryTabTitle";
	  document.all["clinLabTestTitle2"].style.display = "block";
		document.all["clinLabTestTitle3"].className = "libraryTabTitle";

	  document.all["clinFindTitle"].style.display = "none";
		document.all["clinFindTitle1"].className = "libraryTabTitle";
		document.all["clinFindTitle1"].innerHTML = "<b>" + tabLabels[id] + " - " + tabTitles["clinFind"] + "</b>";
		document.all["clinFindTitle2"].className = "libraryTabTitle";
	  document.all["clinFindTitle2"].style.display = "block";
		document.all["clinFindTitle3"].className = "libraryTabTitle";
  }
</bigr:hasPrivilege>
<% } %>      
}

function selectTab(id) {
  document.all[id + "Div"].style.display = "block";
	document.all[id + "Div"].className = "libraryTabContentsSelected";
  document.all[id + "Title"].style.display = "block";
  document.all[id + "Title1"].className = "libraryTabTitleSelected";
	document.all[id + "Title1"].innerHTML = "<b>" + tabTitles[id] + "</b>";
  document.all[id + "Title2"].className = "libraryTabTitleSelected";
  document.all[id + "Title2"].style.display = "none";
  document.all[id + "Title3"].className = "libraryTabTitleSelected";
  document.all[id + "Td"].className = "libraryTabSelected";	

<% if (ApiProperties.isEnabledLegacy()) { %>      
<bigr:hasPrivilege priv="<%=SecurityInfo.PRIV_PSA_DRE%>">
  if (id == "diagnosticTest") {
		document.all[id + "Div"].className = "libraryTabContents";
	  document.all[id + "Title"].style.display = "block";
	  document.all[id + "Title1"].className = "libraryTabTitle";
		document.all[id + "Title1"].innerHTML = "<b>" + tabTitles[id] + "</b>";
	  document.all[id + "Title2"].className = "libraryTabTitle";
	  document.all[id + "Title2"].style.display = "block";
	  document.all[id + "Title3"].className = "libraryTabTitle";

	  document.all["clinLabTestTitle"].style.display = "block";
		document.all["clinLabTestTitle1"].className = "libraryTabTitle";
		document.all["clinLabTestTitle1"].innerHTML = "<b>" + tabTitles["clinLabTest"] + "</b>";
		document.all["clinLabTestTitle2"].className = "libraryTabTitle";
	  document.all["clinLabTestTitle2"].style.display = "block";
		document.all["clinLabTestTitle3"].className = "libraryTabTitle";

	  document.all["clinFindTitle"].style.display = "block";
		document.all["clinFindTitle1"].className = "libraryTabTitle";
		document.all["clinFindTitle1"].innerHTML = "<b>" + tabTitles["clinFind"] + "</b>";
		document.all["clinFindTitle2"].className = "libraryTabTitle";
	  document.all["clinFindTitle2"].style.display = "block";
		document.all["clinFindTitle3"].className = "libraryTabTitle";
  }
</bigr:hasPrivilege>
<% } %>      
}

function showTab(id) { 
  document.all.currentTab.value = id;

	hideTab("id");
	hideTab("attributes");
	hideTab("diagnosis");
<% if (ApiProperties.isEnabledLegacy()) { %>      
	hideTab("diagnosticTest");
<% } %>      
	hideTab("tissue");
<%
if (hasKcQueryForms) {
%>
	hideTab("kc");
<%
}
%>
<bigr:hasPrivilege priv="<%=SecurityInfo.PRIV_SHOW_SQL_TAB%>">
  document.all["sqlDiv"].style.display = "none";
  document.all["sqlDiv"].className = "libraryTabContents";
  document.all["sqlTd"].className = "libraryTab";
</bigr:hasPrivilege>
  document.all["allTd"].className = "libraryTab";

  if (id == "all") {
    document.all["idDiv"].style.display = "block";
    document.all["idTitle"].style.display = "block";
    document.all["attributesDiv"].style.display = "block";
    document.all["attributesTitle"].style.display = "block";
    document.all["diagnosisDiv"].style.display = "block";
    document.all["diagnosisTitle"].style.display = "block";
<% if (ApiProperties.isEnabledLegacy()) { %>      
    document.all["diagnosticTestDiv"].style.display = "block";
    document.all["diagnosticTestTitle"].style.display = "block";
<bigr:hasPrivilege priv="<%=SecurityInfo.PRIV_PSA_DRE%>">
    document.all["clinLabTestTitle"].style.display = "block";
    document.all["clinFindTitle"].style.display = "block";
</bigr:hasPrivilege>
<% } %>      
    document.all["tissueDiv"].style.display = "block";
    document.all["tissueTitle"].style.display = "block";
<%
if (hasKcQueryForms) {
%>
    document.all["kcDiv"].style.display = "block";
    document.all["kcTitle"].style.display = "block";
<%
}
%>
    document.all["allTd"].className = "libraryTabSelected";	
	resizeIdListButtons("id.donorIdsButton");
	resizeIdListButtons("id.caseIdsButton");
	resizeIdListButtons("id.itemIdsButton");
<%
  if (showAliasPattern) {
%>
	resizeIdListButtons("id.aliasPatternButtons");
<%
  }
%>
	/*resizeOrContains("libraryCaseDiagnosis", "diagnosis.caseDiagnosisContains");*/
<% if (ApiProperties.isEnabledLegacy()) { %>   
		resizeOrContains("librarySamplePathology", "diagnosis.samplePathologyContains");	    
<% } %>
	/*resizeOrContains("libraryTissueOrigin", "tissue.tissueOriginContains");
	resizeOrContains("libraryTissueFinding", "tissue.tissueFindingContains");    
	setNot('tissue.tissueOriginNot', 'tissue.tissueOriginContainsLabel');
	setNot('tissue.tissueFindingNot', 'tissue.tissueFindingContainsLabel');	*/
  } 
	else if (id == "sql") {
	  document.all.currentTab.value = "all";
	  document.all["sqlDiv"].style.display = "block";
		document.all["sqlDiv"].className = "libraryTabContentsSelected";
	  document.all["sqlTd"].className = "libraryTabSelected";	
	}
	else {
		selectTab(id);
	  if (id == "diagnosis") {
			/*resizeOrContains("libraryCaseDiagnosis", "diagnosis.caseDiagnosisContains");*/
<% if (ApiProperties.isEnabledLegacy()) { %> 			
			resizeOrContains("librarySamplePathology", "diagnosis.samplePathologyContains");	    
<% } %>
	  }
	  else if (id == "tissue") {
			/*resizeOrContains("libraryTissueOrigin", "tissue.tissueOriginContains");
			resizeOrContains("libraryTissueFinding", "tissue.tissueFindingContains");    
			setNot('tissue.tissueOriginNot', 'tissue.tissueOriginContainsLabel');
			setNot('tissue.tissueFindingNot', 'tissue.tissueFindingContainsLabel');	*/
	  } 
	  else if (id == "id") {
			resizeIdListButtons("id.donorIdsButton");
			resizeIdListButtons("id.caseIdsButton");
			resizeIdListButtons("id.itemIdsButton");
<%
  if (showAliasPattern) {
%>
			resizeIdListButtons("id.aliasPatternButtons");
<%
  }
%>
		}
	}

	// Setting the query div width to a specific size then back to 100%
	// works around an IE bug where the query comes up wider than
	// it should be (by the width of a scroll bar).
  document.all.query.style.width = document.all.header.offsetWidth;
  document.all.query.style.width = "100%";
  
}

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
	<logic:present name="debugSql">
	showTab('sql');
	</logic:present>
	<logic:notPresent name="debugSql">
  showTab('<bean:write name="currentTab"/>');
	</logic:notPresent>  

	// Setting the query div width to a specific size then back to 100%
	// works around an IE bug where the query comes up wider than
	// it should be (by the width of a scroll bar).
  document.all.query.style.width = document.all.header.offsetWidth;
  document.all.query.style.width = "100%";  
  
  updateGrossAppearance();
  positionItems();
  <% if (viewQuerySummary != null) {%>
    window.open(
    	'/BIGR/library/viewSummary.do?txType=<%=txType%>', 
    	'QuerySummary', 
	    'scrollbars=yes,resizable=yes,status=yes,width=450,height=600');
  
  <% } %>

<% if (boolStorageLocation) { %>
    // update local sample only chekcbox value
    if (document.all["attributes.localOnly"].value == "true") {
    	document.all.localOnlyCheckbox.checked = true;
    }
    else {
    	document.all.localOnlyCheckbox.checked = false;
    }  
<% } %>
    
<% if (ApiProperties.isEnabledLegacy()) { %>      
	var performed = document.all["diagnosticTest.performed"];
	for (var i=0; i<performed.length; i++) {
		performed[i].checked = false;
	}
	var testShow = document.all["diagnosticTest.testShow"];
	for (var i=0; i<testShow.length; i++) {
		testShow[i].checked = false;
	}
	var testResult = document.all["diagnosticTest.testResult"];
	for (var i=0; i<testResult.length; i++) {
		testResult[i].checked = false;
	}
	
	var conceptId = document.all["diagnosticTest.diagnosticConceptId"];
	for (var i=0; i<conceptId.length; i++) {
		if (document.all["diagnosticTest.performedHidden"][i].value == "performed") {
			document.all["diagnosticTest.performed"][i].checked = true;
		}
		if (document.all["diagnosticTest.testShowHidden"][i].value == "true") {
			document.all["diagnosticTest.testShow"][i].checked = true;
		}
		var testResults = document.all["diagnosticTest.testResultHidden"][i].value;
		if (testResults.length > 0) {
			var results = testResults.split(",");
			if (results.length > 0) {
				var startIndex = document.all["diagnosticTest.startIndex"][i].value;
				var testCount = document.all["diagnosticTest.testCount"][i].value;
				for (var j=0; j<results.length; j++) {
					for (var k=0; k<testCount; k++) {
						var indIndex = parseInt(startIndex) + parseInt(k);
						if (document.all["diagnosticTest.testResult"][indIndex].value == results[j]) {
							document.all["diagnosticTest.testResult"][indIndex].checked = true;
							break;
						}
					}					
				}
			}
		}		
	} 	
	<% } %>      
  }
  
  // Save prior height to avoid repositioning DIVs if height
  // doesn't change.
  var priorResizeHeight = 0;  
  
  function positionItems() {
    var buttonsDiv = document.all.buttons;
    var queryDiv = document.all.query;
    var buttonsTop = document.body.clientHeight - buttonsDiv.offsetHeight;
    var queryDivHeight = buttonsTop - queryDiv.offsetTop;
    if (queryDivHeight < 200) {
      queryDivHeight = 200;
    }

    queryDiv.style.height = queryDivHeight;
  }
 
  // Reposition items when the page is resized.
  function onResizePage() {
    var currentHeight = document.body.clientHeight;
    if (currentHeight == priorResizeHeight) return;
    priorResizeHeight = currentHeight;
    positionItems();
  }

  
  // Reposition items when the page is resized.
  window.onresize = onResizePage;
  
  function setActionButtonEnabling(isEnabled) {
    var isDisabled = (! isEnabled);
    var f = document.forms[0];
    f.clearQueryButton.disabled = isDisabled;
    f.btnSubmit.disabled = isDisabled;
  }

</script>
</head>
<body onload="initPage();" onunload="if (parent.topFrame) {parent.topFrame.closePopup();}"
      style="margin-bottom: 0px;">

<%--
	** DIV for overlib, which is used for tooltips.
	--%>
<bigr:overlibDiv/>

<div id="wholePage">

<div id="hiddenErrors" style="display:none;">
<logic:present name="nomatchingitem">
<ul id="nomatching_">
<li><bean:message key="library.ss.error.nomatchingitem" arg0="<%=bannertxt%>"/></li>
</ul>

<script type="text/javascript">
  window.setTimeout('window.showModalDialog("<html:rewrite page="/general/messageDialog.do"/>", document.all.nomatching_.outerHTML,  "help:no; status:no; dialogHeight:100px;");', 1, 'JavaScript');
</script>
</logic:present>
</div> 

<div id="errors" style="position: relative;">
<logic:present name="org.apache.struts.action.ERROR"><%--
    --%><table class="fgTableSmall" width="100%" cellpadding="2" cellspacing="1" border="0"><%--
     		--%><tr class="yellow"><%--
       			--%><td colspan="2"><%--
         		--%><div><%--
           			--%><font color="#FF0000"><b><html:errors/></b></font><%--
         		--%></div><%--
       			--%></td><%--
     		--%></tr><%--
		--%></table><%--
--%></logic:present>
</div>


	<html:form action="/library/queryPerform" onsubmit="return false;">
		<html:hidden property="clearQuery" value="false"/>
		<html:hidden property="currentTab" value="all"/>
		<input type="hidden" name="txType"  value="<%=txType%>"/>
		<!-- Tabs -->
  <div id="header" style="position: relative;overflow: hidden; width: 100%;">
    <table border="0" cellspacing="0" cellpadding="3" vspace="0" class="foreground-small" width="100%">
<%
int colgroupSpan = 11;
if (hasKcQueryForms) {
  colgroupSpan += 2;
}
if (securityInfo.isHasPrivilege(SecurityInfo.PRIV_SHOW_SQL_TAB)) {
  colgroupSpan += 2;
}
if (ApiProperties.isEnabledLegacy()) {
  colgroupSpan += 2;
}
%>
    	<colgroup span="<%=colgroupSpan%>" width="0*"></colgroup>
    	<colgroup span="1" width="100%"></colgroup>
      <tbody>
        <tr>
          <td class="libraryTabSpacer">
          	<html:img page="/images/px1.gif" width="6" border="0"/>
          </td>
<%
if (hasKcQueryForms) {
%>
          <td nowrap id="kcTd" onClick="showTab('kc')" class="libraryTab">
          	<b><bean:message key="library.ss.query.kc.tabname"/></b>
          </td>
          <td class="libraryTabSpacer">
          	<html:img page="/images/px1.gif" width="3" border="0"/>
          </td>
<%
}
%>
          <td nowrap id="diagnosisTd" onClick="showTab('diagnosis')" class="libraryTab">
          	<b><bean:message key="library.ss.query.diagnosis.tabname"/></b>
          </td>
          <td class="libraryTabSpacer">
          	<html:img page="/images/px1.gif" width="3" border="0"/>
          </td>
          <td nowrap id="tissueTd" onClick="showTab('tissue')" class="libraryTab">
          	<b><bean:message key="library.ss.query.tissue.tabname"/></b>
          </td>
          <td class="libraryTabSpacer">
          	<html:img page="/images/px1.gif" width="3" border="0"/>
          </td>
<% if (ApiProperties.isEnabledLegacy()) { %>
          <td nowrap id="diagnosticTestTd" onClick="showTab('diagnosticTest')" class="libraryTab">
          	<b><bean:message key="library.ss.query.diagnosticTest.tabname"/></b>
          </td>
          <td class="libraryTabSpacer">
          	<html:img page="/images/px1.gif" width="3" border="0"/>
          </td>
<% } %>
          <td nowrap id="attributesTd" onClick="showTab('attributes')" class="libraryTab">
          	<b><bean:message key="library.ss.query.attributes.tabname"/></b>
          </td>
          <td class="libraryTabSpacer">
          	<html:img page="/images/px1.gif" width="3" border="0"/>
          </td>
          <td nowrap id="idTd" onClick="showTab('id')" class="libraryTab">
          	<b><bean:message key="library.ss.query.id.tabname"/></b>
          </td>
          <td class="libraryTabSpacer">
          	<html:img page="/images/px1.gif" width="9" border="0"/>
          </td>
          <td nowrap id="allTd" onClick="showTab('all')" class="libraryTab">
          	<b><bean:message key="library.ss.query.all.tabname"/></b>
          </td>
			<bigr:hasPrivilege priv="<%=SecurityInfo.PRIV_SHOW_SQL_TAB%>">
				<td class="libraryTabSpacer"><html:img page="/images/px1.gif"
					width="9" border="0" /></td>
				<td id="sqlTd" onClick="showSql();" class="libraryTab"><b>SQL</b></td>
			</bigr:hasPrivilege>
		  <td class="libraryTabSpacer">
          	<html:img page="/images/px1.gif" width="9" border="0"/>
      </td>
      <td nowrap class="libraryTabSpacer">
          	<span class="fakeLink" onclick="viewQuerySummary();">View Query Summary</span>
      </td>
      </tr>
      </tbody>
    </table>
  </div>

		<!-- Tab contents -->
  <div id="query" style="position: relative; overflow: auto; width:100%; left: 0; top: 0; height: 90%; border: 2px solid #336699; border-top: none;">
	  <a name="top"></a>

	  <jsp:include page="/hiddenJsps/library/personalfilter/manageBlock.jsp" flush="true" />

<%
if (hasKcQueryForms) {
%>
		<jsp:include page="/hiddenJsps/library/TabKc.jsp" flush="true">
			<jsp:param name="txType" value="<%=txType%>" />
		</jsp:include>
<%
}
%>
		<jsp:include page="/hiddenJsps/library/TabDiagnosis.jsp" flush="true"/>
<% if (ApiProperties.isEnabledLegacy()) { %>
		<jsp:include page="/hiddenJsps/library/TabDiagnosticTest.jsp" flush="true"/>
<% } %>
		<jsp:include page="/hiddenJsps/library/TabTissue.jsp" flush="true"/>
		<jsp:include page="/hiddenJsps/library/TabAttributes.jsp" flush="true">
			<jsp:param name="txType" value="<%=txType%>" />
		</jsp:include>
		<jsp:include page="/hiddenJsps/library/TabId.jsp" flush="true"/>

		<!-- SQL tab -->
		<bigr:hasPrivilege priv="<%=SecurityInfo.PRIV_SHOW_SQL_TAB%>">
			<div id="sqlDiv" style="margin-top: 1em; padding: 1em; font-family: Courier; font-size: xx-small; display: none;">
			<logic:present name="debugSql">
				<bigr:filterHtml filter="false" whitespace="true"><bean:write name="debugSql" filter="false"/></bigr:filterHtml>
			</logic:present>
			</div>
		</bigr:hasPrivilege>
	</div>

		<!-- Buttons -->
	  <div id="buttons" style="overflow: hidden; width:100%; padding-top: 0.25em;">
			<table border="0" cellspacing="1" cellpadding="3" class="foreground-small">
        <col width="90%">
	      <col width="10%"> 
				<tbody>
					<tr>
						<td align="left">
							<input type="submit" name="btnSubmit" class="libraryButtons" onclick="executeQuery();"
								value='<bean:message key="library.ss.query.button.execute"/>' />
								&nbsp;using view:
								<bigr:selectList
								  name="queryForm"
								  property="resultsFormDefinitionId"
								  legalValueSet="<%=FormUtils.getFormsAsLVS(myForm.getResultsFormDefinitions())%>"/>
						</td>
						<td align="right">
							<html:button property="clearQueryButton" styleClass="libraryButtons" onclick="clrQuery('all')"
							onmouseout="return nd()" onmouseover="return overlib('Resets all values on all tabs to default values')">
								<bean:message key="library.ss.query.button.clear"/>
							</html:button>
						</td>
					</tr>
				</tbody>
			</table>
		</div>

	</html:form>
</div>

</body>
</html:html>
