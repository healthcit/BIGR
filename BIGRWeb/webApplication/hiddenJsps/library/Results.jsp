<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ardais.bigr.api.ApiException"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions"%>
<%@ page import="com.ardais.bigr.api.Escaper"%>
<%@ page import="com.ardais.bigr.security.*"%>
<%@ page import="com.ardais.bigr.javabeans.*"%>
<%@ page import="com.ardais.bigr.library.javabeans.*"%>
<%@ page import="com.ardais.bigr.library.web.helper.*"%>
<%@ page import="com.ardais.bigr.query.*"%>
<%@ page import="com.ardais.bigr.util.*"%>
<%@ page import="com.ardais.bigr.library.web.helper.ResultsHelper"%>
<%@ page import="com.ardais.bigr.library.SampleViewData"%>
<%@ page import="com.ardais.bigr.library.web.column.*"%>
<%@ page import="com.ardais.bigr.query.ColumnPermissions"%>
<%@ page import="com.ardais.bigr.iltds.helpers.FormLogic"%>
<%@ page import="com.ardais.bigr.security.SecurityInfo"%>
<%@ page import="com.ardais.bigr.orm.helpers.BigrGbossData"%>
<%@ page import="com.ardais.bigr.concept.BigrConcept"%>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="com.ardais.bigr.iltds.assistants.LegalValue" %>
<%@ page import="com.ardais.bigr.iltds.assistants.LegalValueSet" %>
<%@ page import="org.apache.commons.lang.BooleanUtils" %>
<%@ page import="com.ardais.bigr.library.web.action.QueryPerformAction" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>

<%	// -- Standard block for all query pages to find the proper txType
	// -- also MUST put a hidden txType field in the form
	String txType = null;
	boolean isDescSort;
	String sortedColumn;
  String resultsFormDefinitionId = (String)request.getAttribute("resultsFormDefinitionId");
  List resultsFormDefinitions = (List)request.getAttribute("resultsFormDefinitions");
	com.ardais.bigr.library.web.form.QueryForm qform = 
		(com.ardais.bigr.library.web.form.QueryForm) request.getAttribute("queryForm");
	if (qform != null) {
		System.out.println("found query form.  type=" + qform.getTxType());
		txType = qform.getTxType();
		isDescSort = qform.getIsDescSort();
		sortedColumn = qform.getSortedColumn();
	}
	else {
		com.ardais.bigr.library.web.form.ResultsForm rform = 
			(com.ardais.bigr.library.web.form.ResultsForm) request.getAttribute("resultsForm");
		if (rform != null) {
			//System.out.println("found results form.  type=" + rform.getTxType());
			txType = rform.getTxType();
			isDescSort = rform.getIsDescSort();
			sortedColumn = rform.getSortedColumn();
		}
		else {
			txType = request.getParameter("txType");
			isDescSort = BooleanUtils.toBoolean(request.getParameter("isDescSort"));
			sortedColumn = request.getParameter("sortedColumn");
		}
	}
%>


<%

	String bannertxt;
	if (ResultsHelper.TX_TYPE_SAMP_SEL.equals(txType))
		bannertxt = "Sample Selection Results";
	else if (ResultsHelper.TX_TYPE_SAMP_REQUEST.equals(txType))
		bannertxt = "Request Samples Results";
	else if (ResultsHelper.TX_TYPE_ORDER_DETAIL.equals(txType))
		bannertxt = "Order Details";
	else
		bannertxt = "Matched Samples";
		

	ResultsHelper rh = ResultsHelper.get(txType, request);
	
	if (rh == null) {
		throw new ApiException("Request attribute " + ResultsHelper.KEY + txType + " not defined in Results.jsp");
	}
	
	//---------------------------  synch entire request on the ResultsHelper
	synchronized (rh) {
	//---------------------------  synch entire request on the ResultsHelper

	SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);

	String currentProductType = rh.getProductType();
	
  SampleViewData viewData = rh.getHelpers();

	List sampleHelpers = viewData.getSampleHelpers();
	List allResults = rh.getAllSampleResults();
	SampleSelectionSummary summary = rh.getQuerySummary(currentProductType);
	SampleSelectionSummary holdSummary = rh.getOnHoldSummary();

	int numChunks = rh.getNumberOfChunks();
	int currentNum = rh.getCurrentChunkNumber();

	// isViewingRna drives the amounts screen 
	boolean isViewingRna = ResultsHelper.PRODUCT_TYPE_RNA.equals(currentProductType);

	SampleResults holdResults = rh.getHoldResults(); 
	SampleResults currentResults = rh.getCurrentSampleResults();
	
	String[] selIdsStrings = rh.getSelectedIdsAsStrings(); 
	String[] removedIds = rh.getRemovedIds();
	
	int tabCount = allResults.size();
	
	// Determine the page numbers for the page navigation
	int fromNum = 0;
	int toNum = 9;
	int maxNum = numChunks - 1;
	if (maxNum <= 9) {
		toNum = maxNum;
	} 
	else if (currentNum > 4) {
		fromNum = currentNum - 4;
		toNum = currentNum + 5;
	}           
	if (toNum > maxNum) {
		toNum = maxNum;
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/baseFunctions.js"></script>

<script type="text/javascript">
<%-- ALL .js THAT PULLS INFORMATION OUT OF JAVA REQUEST OBJECTS GOES HERE
  ** TO LOCALIZE "INPUT" TO THIS PAGE FROM REQUEST, SESSION OBJECTS
  --%>

	// Selected ids for all chunks.
	// Each entry in the array is a space-separated list of ids for a particular chunk.
	// Used in copying all ids to the clipboard
	var selectedItemsByChunkVar = new Array(<%=numChunks%>); 
	<%
		for (int i = 0; i < numChunks; i++) {
			if (i != currentNum) {
	%>
		selectedItemsByChunkVar[<%=i%>] = '<%=selIdsStrings[i]%>';
	<%
			}
		}
	%>
	var currentChunkNumVar = <%=currentNum%>;
	<% Set onHoldIds = new HashSet();
	   { 
		String[] ids = holdResults.getSampleIds();
		for (int i = 0; i < ids.length; i++) {
		  onHoldIds.add(ids[i]);
		}
	}%>
	<% //-- MR6651 
	  Set unselectableIds = new HashSet();
	  { 
		if (securityInfo.isInRoleSystemOwner() && ResultsHelper.PRODUCT_TYPE_SAMPLE.equals(currentProductType)) {
			int count = 0;
			for (int i = 0; i < sampleHelpers.size(); i++) {
				SampleSelectionHelper ssHelper = (SampleSelectionHelper) sampleHelpers.get(i);
				boolean isSelectable = true;
			    //if a tissue is on hold, in a project, or sold, it is not selectable
	    		//for a client.
	    		
	    		String hold = ssHelper.getCartEntriesString();
	    		if (hold != null && !hold.trim().equals("")) {
	    			isSelectable = false;
	    		}
			    String project = ssHelper.getProject();
			    if (!"".equals(project)) {
			      isSelectable = false;
			    }
			    String salesStatus = ssHelper.getSalesStatus();
			    if (FormLogic.SMPL_ESSHIPPED.equalsIgnoreCase(salesStatus) ||
			    	FormLogic.SMPL_ESSOLD.equalsIgnoreCase(salesStatus)) {
			      isSelectable = false;
			    }
			    //if the inventory status is not INR, it is not selectable for a client
			    String invStatus = ssHelper.getInventoryStatus();
			    if (!"INR".equalsIgnoreCase(invStatus)) {
			      isSelectable = false;
			    }
			    //if a tissue is pulled, it is not selectable for a client
			    if (ssHelper.isPulled()) {
			      isSelectable = false;
			    }
				if (!isSelectable) {
				  unselectableIds.add(ssHelper.getId());
		  		count = count + 1;
				} 
			}
		}
	}%>
	<% Set selectedIds = new HashSet();
	   {
		String[] ids = currentResults.getSelectedIds();
		for (int i = 0; i < ids.length; i++) {
		  String id = ids[i];
		  selectedIds.add(id);
	    } 
	   }%>
	<% Set removedIdsSet = new HashSet();
	   { 
		String[] ids = removedIds;
		for (int i = 0; i < ids.length; i++) {
		  removedIdsSet.add(ids[i]);
		}
	}%>
<%
  {
    Iterator iter = sampleHelpers.iterator();
    while (iter.hasNext()) {
      SampleSelectionHelper ssHelper = (SampleSelectionHelper) iter.next();
      String id = ssHelper.getId();
      boolean isRemoved = removedIdsSet.contains(id);
      boolean isOnHold = onHoldIds.contains(id);
      boolean isSelected = selectedIds.contains(id);
      boolean isSelectable = (!unselectableIds.contains(id));

      ssHelper.setSelectable(isSelectable);
      ssHelper.setSelectionDisabled(isOnHold || isRemoved);
      ssHelper.setSelected(isSelected);

  	  // Colorization for on-hold takes precedence over the
	    // colorization of unselectable.  rowClassUnselected is the
	    // class that a row should revert to when it is not a selected row.
      String rowClass = "white";
      String rowClassUnselected = rowClass;
      if (isRemoved) {
        rowClass = "disabled-removed";
        rowClassUnselected = rowClass;
      }
      else if (isOnHold) {
        rowClass = "disabled";
        rowClassUnselected = rowClass;
      }
      else if (!isSelectable) {
        rowClass = "disabled-removed";
        rowClassUnselected = rowClass;
      }
      else if (isSelected) {
        rowClass = "highlight";
      }
      ssHelper.setRowClass(rowClass);
      ssHelper.setRowClassUnselected(rowClassUnselected);
    }
  }
%>
	var tabClassesVar = new Array();
	<%
	//--
	// set up an associative array mapping tabs to the enabled/disabled style for that tab
	for (int i = 0; i < allResults.size(); i++) {
		SampleResults sr = (SampleResults) allResults.get(i);
		if (sr==null) throw new ApiException("null Sample Results for number: " + i);
		String tdName = sr.getProductType()+"Td";
		if (sr==null) throw new ApiException("null results productType for no.: " + i);
		if (sr.getSummary()==null) throw new ApiException("null summary for Sample Results no.:" +i);
		String className = sr.getSummary().getTotalSampleCount() > 0 ? "libraryTab" : "libraryTabDisabled";
		%>
		tabClassesVar['<%=tdName%>'] = '<%=className%>';
	<%		
	}
	%>	

	function onClickCopyAllCaseIdsToClipboard() {
      setActionButtonEnabling(false);
	  var caseClip = document.all.allCaseIds.value;
	  if (caseClip == "") {
	    alert ("No case ids were added to the clipboard.");		  
	  }
	  else {
	  	caseClip = caseClip.replace(/,/g,"\n");
		copyToClipboard(caseClip);
		alert ("<%=summary.getConsentIds().length%> case id(s) have been placed on the clipboard.");
	  }
      setActionButtonEnabling(true);
	}

	function onClickCopyAllSampleIdsToClipboard() {
      setActionButtonEnabling(false);
	  var sampleClip = document.all.allSampleIds.value;
	  if (sampleClip == "") {
	    alert ("No sample ids were added to the clipboard.");		  
	  }
	  else {
	  	sampleClip = sampleClip.replace(/,/g,"\n");
		copyToClipboard(sampleClip);
		alert ("<%=summary.getIds().length%> sample id(s) have been placed on the clipboard.");
	  }
      setActionButtonEnabling(true);
	}
		
  function onClickCopyPageToClipboard(){
    setActionButtonEnabling(false);
    var sampleClip = document.all.clipBoardMarker.innerHTML;
    if (sampleClip == "") {
      alert ("No samples were added to the clipboard.");		  
    }
    else {
      copyToClipboard(sampleClip);
      alert ("Samples have been placed on the clipboard.");
    }
    setActionButtonEnabling(true);
  }
	
	var selectedResultsFormDefinitionId = '<%=resultsFormDefinitionId%>';
	
	var navigationConfirmed = false;
</script>

<script type="text/javascript">
	function sortSampleColumn(column, order)
	{
		var form = bigrBaseFunctions.createHiddenForm("post", "<html:rewrite page="/library/queryPerform.do"/>");

		<%
		final Map paramMap = (Map)request.getSession().getAttribute(QueryPerformAction.QUERY_REQUEST_PARAM_MAP);
		for (Object key : paramMap.keySet())
		{
			final String[] valueArray = (String[])paramMap.get(key);
			for (String value : valueArray)
			{
		%>
				form.appendChild(bigrBaseFunctions.createHiddenFormInput('<%=key.toString()%>', '<%=value%>'));
		<%
		  	}
		}
		%>

		var sortedColumnValue = "";

		if (order == 'asc' || order == 'desc')
		{
			sortedColumnValue = column;
			form.appendChild(bigrBaseFunctions.createHiddenFormInput("isDescSort", (order == 'desc') ? 'true' : 'false'));
		}

		form.appendChild(bigrBaseFunctions.createHiddenFormInput("sortedColumn", sortedColumnValue));

		document.body.appendChild(form);
		form.submit();
	}
</script>

<title>Sample Selection Query</title>
<jsp:include page="/hiddenJsps/kc/misc/coreScript.jsp" flush="true"/>
<link rel="stylesheet" type="text/css"
	href='<html:rewrite page="/css/bigr.css"/>'>
	
<script type="text/javascript"
	src='<html:rewrite page="/js/common.js"/>'></script>

<script type="text/javascript"
	src='<html:rewrite page="/js/BigrLib.js"/>'></script>

<script type="text/javascript"
	src='<html:rewrite page="/js/ssresults.js"/>'></script>

<script type="text/javascript"
	src='<html:rewrite page="/js/conceptGraph.js"/>'></script>

<script type="text/javaScript" 
	src="<html:rewrite page="/hiddenJsps/kc/detViewer/js/scriptaculous.js?load=builder"/>"></script>

<link rel="stylesheet" type="text/css" href='<html:rewrite page="/js/lightbox/gsbioLightbox.css"/>'>

<script type="text/javaScript" 
	src="<html:rewrite page="/js/lightbox/gsbioLightbox.js"/>"></script>

<script type="text/javascript"
	src='<html:rewrite page="/js/integration/bigrAperio.js"/>'></script>

<script type="text/javascript"
	src='<html:rewrite page="/js/overlib.js"/>'><!-- overLIB (c) Erik Bosrup --></script>

<script type="text/javascript">

	<bigr:javascriptConceptCache categoryDomain="<%=ArtsConstants.VALUE_SET_SAMPLE_TYPE%>" includeScriptElement="false"/>
	<bigr:javascriptSpecialConcept key="SAMPLE_TYPE_UNKNOWN" cui="<%=ArtsConstants.SAMPLE_TYPE_UNKNOWN%>" includeScriptElement="false"/>

	var myBanner = '<%=bannertxt%>';
	  
	<%-- Maximum allowed selected at once --%>
	var maxCount = <%= com.ardais.bigr.api.ApiProperties.getProperty("api.es.browse.maxCount")%>;
	  
	<%-- A flag that is used to indicate whether we should inform the
	  ** user that they have selected samples that they need to do
	  ** something with before navigating away from the page. --%>
	var _isWarnOnNavigate = true;
	
	function placeSelectedItemsOnClipboard(elementName) {
	  <%-- MR 6856: Use newline to separate clipboard items. --%>
		var sampleClip = "";  
		<%-- add items selected on the client --%>
		var samplesArr = document.all[elementName];
		samplesArr = arrayFor(samplesArr);  // in case there is just one row
		for (var j = 0; j < samplesArr.length; j++) {
			if (samplesArr[j].checked) {
				if (sampleClip != "") {
					sampleClip += "\n";				
				}
				sampleClip += samplesArr[j].value;
			}
		}			
	
		<%-- add selected ids stored on the server (for other chunks) --%>
		for (var i = 0; i < selectedItemsByChunkVar.length; i++) {
			var selectedIds = selectedItemsByChunkVar[i];
			if ((selectedIds != null) && (selectedIds != "")) {
				if (sampleClip != "") {
					sampleClip += "\n";				
				}
				sampleClip += selectedIds.replace(/ /g, "\n");
			}
		}
	
		if (sampleClip == "") {
			alert ("No samples have been selected.");		  
		}
		else {
			copyToClipboard(sampleClip);
			alert ("The selected samples have been placed on the clipboard.");
			_isAllSelectedOnClipboard = true;
		}
	}
	
	function onClickModifySearch() {
      setActionButtonEnabling(false);
      var cancelAction = false;
      
      if (! confirmNavigateOnClick()) {
        cancelAction = true;
      }
      
      if (cancelAction) {
        setActionButtonEnabling(true);
      }
      else {
        _isWarnOnNavigate = false;
		window.location.href = '<html:rewrite page="/library/start.do"/>?txType=<%=txType%>';
      }
	}
	
	function onClickPlaceSelectedItemsOnClipboard() {
    setActionButtonEnabling(false);
	  placeSelectedItemsOnClipboard('samples');
    setActionButtonEnabling(true);
	}
	
	function onClickGoToHoldView() {
      setActionButtonEnabling(false);
      var cancelAction = false;
      
      if (! confirmNavigateOnClick()) {
        cancelAction = true;
      }
      
      if (cancelAction) {
        setActionButtonEnabling(true);
      }
      else {
        _isWarnOnNavigate = false;
        window.location.href = '<html:rewrite page="/library/holdView.do"/>?txType=<%=txType%>';
      }
	}
	
	function onClickSubmitFormForHold() {
      setActionButtonEnabling(false);
	  if (!submitFormForHold(<%=isViewingRna?"true":"false"%>)) {
        setActionButtonEnabling(true);
	  }
	}
	
	function onClickSubmitFormForRequestSamples() {
      setActionButtonEnabling(false);
	  if (!submitFormForRequestSamples()) {
        setActionButtonEnabling(true);
	  }
	}
	
	function onClickSelectAllSamples(elementName) {
    setActionButtonEnabling(false);
    var theSamples = arrayFor(document.all[elementName]);
    var theSamplesCount = theSamples.length;
    for (var i = 0; i<theSamplesCount; i++) {
    	var checkbox = theSamples[i];
    	if (!checkbox.checked) {
        document.all.inventoryItemViewElem.doToggleSelectSample(checkbox, false, true);
    	}
	  }
	  _selectedCounts.updateTotalDisplay();
    setActionButtonEnabling(true);
	}
   
  function setActionButtonEnabling(isEnabled) {
    var isDisabled = (! isEnabled);
    var f = document.forms[0];
    setInputEnabled(f, "button1", isEnabled);
    setInputEnabled(f, "button3", isEnabled);
    setInputEnabled(f, "button4", isEnabled);
    setInputEnabled(f, "requestSamples", isEnabled);
    setInputEnabled(f, "selectAll", isEnabled);
  }
    
  function confirmNavigate() {
    var navWarning = getNavagationWarning();
    if (null != navWarning) {
      event.returnValue = navWarning;
    }
  }

  function getNavagationWarning() {
    if (! _isWarnOnNavigate) return null;
    if (_selectedCounts.getTotal() > 0) {
      if (_isAllSelectedOnClipboard) {
        return "You have samples selected but have not placed them on hold.  Are you sure you want to continue?";
      }
      else {
        return "You have samples selected but have not placed them on hold or the clipboard.  Are you sure you want to continue?";
      }
    }
    return null;
  }
  
  function confirmNavigateOnClick() {
    var navWarning = getNavagationWarning();
    if (null != navWarning) {
      var msg = navWarning + "\n\nPress OK to continue, or Cancel to stay on the current page.";
	    if (!confirm(msg)) {
        return false;
      }
    }
    return true;
  }
  
  function submitFormForHold(isRnaFlag){
	<bigr:notHasPrivilege priv="<%=SecurityInfo.PRIV_HOLDLIST_ADDRMV%>">    
		alert("You do not have the privilege to perform this action. Please contact your Administrator.");
		return false;
	</bigr:notHasPrivilege>
	<bigr:hasPrivilege priv="<%=SecurityInfo.PRIV_HOLDLIST_ADDRMV%>">
		// do not send this javascript text to the client at all without the privilege
		if (_selectedCounts.getTotal() == 0) {
			alert('You have not selected any samples to place on hold.  Please select one or more samples and the press the "Place Items on Hold" button.');
			return false;
		}
		if (_selectedCounts.getTotal() > maxCount) {
			alert("You have selected too many samples to place on hold.  You may select a maximum of " + maxCount + ".  Please deselect samples until you have less than " + maxCount + " selected.");
			return false;
		}
	
		if (isRnaFlag) {
			// go to the RNA amount screen first, which will then do the holdAdd.do action
			_isWarnOnNavigate = false;
			var form = document.forms[0];
			form.action = '<html:rewrite page="/library/holdAmountRequest.do"/>';
			document.all.wholePage.style.display = 'none';
			if (parent.topFrame) parent.topFrame.openPopup('processing');	
			form.submit();
			return true;
		}
		else {
			// popup submitted with OK button
			_isWarnOnNavigate = false;
			var form = document.forms[0];
			form.action = '<html:rewrite page="/library/holdAdd.do"/>' + '?resultsFormDefinitionId=' + '<%=resultsFormDefinitionId%>';
			document.all.wholePage.style.display = 'none';
			if (parent.topFrame) parent.topFrame.openPopup('processing');	
			form.submit();
			return true;
		}
	</bigr:hasPrivilege>
  }

  function submitFormForRequestSamples() {
	  if (_selectedCounts.getTotal() == 0) {
		  alert('You have not selected any samples to request.  Please select one or more samples and the press the "Request Samples" button.');
		  return false;
	  }

	  _isWarnOnNavigate = false;
	  var form = document.forms[0];
  	form.action = '<html:rewrite page="/library/requestSamplesAdd.do"/>' + '?resultsFormDefinitionId=' + '<%=resultsFormDefinitionId%>';;    
  	document.all.wholePage.style.display = 'none';
  	if (parent.topFrame) parent.topFrame.openPopup('processing');	
  	form.submit();
  	return true;
  }
  
  function submitPagerForm(chunkNumber){
	  _isWarnOnNavigate = false;
	  var form = document.forms[0];    
	  form.nextChunk.value = chunkNumber;
  <% if (StringUtils.isNotBlank(sortedColumn)) { %>
	  form.isDescSort.value = '<%=isDescSort%>';
	  form.sortedColumn.value = '<%=sortedColumn%>';
  <% } %>
	  form.action = '<html:rewrite page="/library/productChunk.do"/>';
	  form.submit();
  }

	function gotoTab(prodType) {
		if (!_isPageReadyForInteraction) // be sure the counts are set so confirmNavigate works
			return;
		var tab = event.srcElement;
		if ((prodType != '<%=currentResults.getProductType()%>') && (tab.count != '0')) {
			loadTab(prodType);
		}
	}
	
	<%-- called from .js to refresh the current view --%>
	function reloadTable(resultsFormDefinitionId) {
	  this.focus();
	  if (resultsFormDefinitionId != null) {
	    if (confirmNavigateOnClick()) {
	      navigationConfirmed = true;
				document.all.wholePage.style.display = 'none';
				document.all.waitMessage.style.display = 'block';
	      selectedResultsFormDefinitionId = resultsFormDefinitionId;
		    loadTab('<%=currentResults.getProductType()%>');
	    }
	    else {
	      navigationConfirmed = false;
	    }
	  }
	}
	
	function loadTab(prodType) {
	  setActionButtonEnabling(false);
	  var url = '<html:rewrite page="/library/showResults.do"/>';
		url += "?productType=" + prodType;
		url += "&txType=<%=txType%>";
		url += "&resultsFormDefinitionId=" + selectedResultsFormDefinitionId;
		window.location.href = url;			
	}
	
	<%-- iterate through the tabs, and disable them if they are empty (no results in summary)
	  ** then select the current tab --%>
	function showArea(id) { 
		for (var tabname in tabClassesVar) { 
			if (document.all[tabname]) {
				document.all[tabname].className = tabClassesVar[tabname];
			}
		}
 	  document.all[id+"Td"].className = "libraryTabSelected";
	}
	
	function initPage() {
	  commonInitPage();
	  showArea('<%=currentResults.getProductType()%>');
    initCounts();
  
	  setActionButtonEnabling(true);
    _isPageReadyForInteraction = true;
  }
    
  function initCounts() {
<%

LegalValueSet sampleTypes = BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_SAMPLE_TYPE);
Iterator sampleTypesIterator = sampleTypes.getIterator();
while (sampleTypesIterator.hasNext()) {
  LegalValue sampleType = (LegalValue) sampleTypesIterator.next();
	String cui = sampleType.getValue();
	if (cui.equals(ArtsConstants.SAMPLE_TYPE_RNA)) {
		continue;
	}
	if (holdSummary == null) {
%>
		_onHoldCounts.setCount('<%=cui%>', 0);
<%
	}
	else {
%>
		_onHoldCounts.setCount('<%=cui%>', <%=holdSummary.getSampleCount(cui).toString()%>);
<%
	}
}

if (isViewingRna) {
		SampleCountHelper countHelper = rh.getSampleCountHelper(ArtsConstants.SAMPLE_TYPE_RNA);
%>
		_matchingCounts.setCount('<%=ArtsConstants.SAMPLE_TYPE_RNA%>', <%=String.valueOf(countHelper.getTotal())%>);
		_selectedCounts.setCount('<%=ArtsConstants.SAMPLE_TYPE_RNA%>', <%=String.valueOf(countHelper.getSelected())%>);
<%
}
else {
	sampleTypesIterator = sampleTypes.getIterator();
  while (sampleTypesIterator.hasNext()) {
    LegalValue sampleType = (LegalValue) sampleTypesIterator.next();
		String cui = sampleType.getValue();
		if (!cui.equals(ArtsConstants.SAMPLE_TYPE_RNA)) {
			SampleCountHelper countHelper = rh.getSampleCountHelper(cui);
%>
		_matchingCounts.setCount('<%=cui%>', <%=String.valueOf(countHelper.getTotal())%>);
		_selectedCounts.setCount('<%=cui%>', <%=String.valueOf(countHelper.getSelected())%>);
<%
		}
  }
}
%>  	
	  _selectedCounts.setTotalElementId("tissueSelected");
	  _selectedCounts.updateTotalDisplay();
  }
  
  function matchingCountsPopup() {
    if (_isPageReadyForInteraction) {
			event.returnValue = overlib(_matchingCounts.getCountsBySampleTypeHtml(), FGCOLOR, '#CCFFFF');    
		}
  }
  
  function selectedCountsPopup() {
    if (_isPageReadyForInteraction) {
			event.returnValue = overlib(_selectedCounts.getCountsBySampleTypeHtml(), FGCOLOR, '#CCFFFF');    
		}
  }

  function onHoldCountsPopup() {
    if (_isPageReadyForInteraction) {
			event.returnValue = overlib(_onHoldCounts.getCountsBySampleTypeHtml(), FGCOLOR, '#CCFFFF');    
		}
  }

	function exportList()
	{
		var form = document.forms[0];
		form.action = '<html:rewrite page="/library/queryPerform/export.do"/>';
		form.target = '_blank';
		form.submit();
		form.target = '';
	}
</script>
</head>

<body class="bigr" 
  style="margin-top: 2px;"
	onload="initPage();" 
	onbeforeunload="if (!navigationConfirmed) {confirmNavigate();}" 
	onunload="if (parent.topFrame) {parent.topFrame.closePopup();}"
	onclick="bodyOnClick();">
	<% //div for "please wait" message %>
  <div id="waitMessage" style="display: none"> 
    <table align="center" border="0" cellspacing="0" cellpadding="0" class="background" width="300">
      <tr> 
        <td> 
          <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
            <tr align="center" class="yellow"> 
              <td><img id="waitMessageImage" 
                       src="<html:rewrite page='/images/pleasewait.gif'/>"
                       alt="Please Wait"></td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </div>

	<%
	// DIV for the whole page that we use to hide the page on submit 
	// for putting items on hold.
	%>
	<div id="wholePage">

	<%
	// DIV for errors
	%>
	<div id="errorDiv" align="center">
     <table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
      <logic:present name="org.apache.struts.action.ERROR">
	   <tr class="yellow"> 
	     <td colspan="10"> 
	       <div align="center">
	         <font color="#FF0000"><b><html:errors/></b></font>
	       </div>
	     </td>
	    </tr>
	   </logic:present>
      <logic:present name="com.ardais.BTX_ACTION_MESSAGES">
	   <tr class="yellow"> 
	     <td colspan="10"> 
	       <div align="center">
	         <font color="#FF0000"><b><bigr:btxActionMessages/></b></font>
	       </div>
	     </td>
	    </tr>
	   </logic:present>
	  </table>
	</div>
	
<table id="clipboardMenuPopup" class="contextMenu"
  onmouseover="highlightMenuItem();" onmouseout="unHighlightMenuItem();"
  onclick="onClickClipboardMenu();"
  border="0" cellspacing="0" cellpadding="3">
  <tr><td menuFunction="onClickCopyAllCaseIdsToClipboard();">Copy All Case Ids to Clipboard</td></tr>
  <tr><td menuFunction="onClickCopyAllSampleIdsToClipboard();">Copy All Sample Ids to Clipboard</td></tr>
  <tr><td menuFunction="onClickPlaceSelectedItemsOnClipboard();">Copy Selected Sample Ids to Clipboard</td></tr>
  <tr><td menuFunction="onClickCopyPageToClipboard();">Copy Samples to Clipboard</td></tr>  
</table>

<%
	//only include the select view menu if we're not looking at the RNA tab
	if (!isViewingRna) {
%>
    <bigr:selectViewLinkMenu
      currentResultsFormDefinitionId="<%=resultsFormDefinitionId%>"
      resultsFormDefinitions="<%=resultsFormDefinitions%>"
      menuId="selectViewMenu"
    />
<%
	}
%>

	<%
	// DIV for tabs
	%>
	<div id="header" style="overflow: hidden; width: 100%;">
	<table border="0" cellspacing="0" cellpadding="3" vspace="0" class="foreground-small" width="100%">
    	<colgroup span="<%=String.valueOf(tabCount + 2)%>" width="0*"></colgroup>
    	<colgroup span="1" width="100%"></colgroup>
    	<colgroup span="1" width="0*"/>
	<tbody>

		<%
		// The tabs row  
		// Each tab has a description followed by a count.  e.g.  "Frozen/Paraffin (238)"
		%>
        <tr>
			<td class="libraryTabSpacer">
				<html:img page="/images/px1.gif" width="6" border="0"/>
			</td>
			<%
			for (int i = 0; i < allResults.size(); i++) {
				SampleResults sr = (SampleResults) allResults.get(i);
				String prodType = sr.getProductType();
				String htmlTabName = prodType+"Td";
				String tabDisplayName = sr.getDisplay();
				SampleSelectionSummary sSum= sr.getSummary();
				int cnt = sSum.getTotalSampleCount();
				String cntStr = Integer.toString(cnt);
				String className = cnt > 0 ? "libraryTab" : "libraryTabDisabled";
			%>
				<td nowrap class="<%=className%>" id="<%=htmlTabName%>" onClick="gotoTab('<%=prodType%>');" count="<%=cntStr%>">
					<%=tabDisplayName%> (<%=cntStr%>)
				</td>
				<td class="libraryTabSpacer">
					<html:img page="/images/px1.gif" width="3" border="0"/>
				</td>
			<%
			}
			%>
			<td nowrap class="libraryTabSpacer">
          	  <bigr:isInRole role1='<%=com.ardais.bigr.security.SecurityInfo.ROLE_SYSTEM_OWNER%>' role2='<%=com.ardais.bigr.security.SecurityInfo.ROLE_DI%>'  > 
  				<input type="hidden" name="allCaseIds" value="<%=StringUtils.join(summary.getConsentIds(),",") %>">
  				<input type="hidden" name="allSampleIds" value="<%=StringUtils.join(summary.getIds(),",") %>">
  				<small>
      			<span class="fakeLink" onclick="doClipboardActionOnClick()"
            		clipboardMenu="clipboardMenuPopup">Clipboard Actions</span></small> &nbsp;&nbsp;&nbsp;
              </bigr:isInRole>
        <%
        	//only include the select view link if we're not looking at the RNA tab
        	if (!isViewingRna) {
        %>
		    <bigr:selectViewLink
		      currentResultsFormDefinitionId="<%=resultsFormDefinitionId%>"
		      resultsFormDefinitions="<%=resultsFormDefinitions%>"
		      menuId="selectViewMenu"
		    />
		    <%
        	}
		    %>
		    <small>
				  <span class="fakeLink" onclick="openQuerySummary('<%=txType%>');">Show Query Summary</span>
				<small>
			</td>
        </tr>

		<%
		// The totals displayed on each tab
		%>
        <tr>
			<td colspan="6" class="highlight" style="border-left: 2px solid #336699; border-right: 2px solid #336666; padding: 0.25em">
				<table border="0" cellspacing="0" cellpadding="0" class="foreground-small" width="100%">
					<tr>
						<td style="padding-bottom: 0.25em;">
							<span style="margin-left: 0.5em; margin-right: 0.5em">
								<b>Matching Totals:</b>
							</span>

							<%if (ResultsHelper.PRODUCT_TYPE_SAMPLE.equals(currentProductType)) {%>
								<span style="margin-left: 0.5em" class="fakeLink" onmouseout="return nd();" onmouseover="matchingCountsPopup();">
									<%=String.valueOf(summary.getTotalSampleCount())%> Samples</span>
								<span style="margin-left: 1em">
									<%=(summary.getAsmCount() == null) ? "0" : summary.getAsmCount().toString()%> ASMs
								</span>
							<%}%>

							<span style="margin-left: 1em">
								<%=(summary.getConsentCount() == null) ? "0" : summary.getConsentCount().toString()%> Cases
							</span>
							<span style="margin-left: 1em">
								<%=(summary.getDonorCount() == null) ? "0" : summary.getDonorCount().toString()%> Donors
							</span>

							<span style="margin-left: 2em;">
								<b>Selected:</b>
							</span>

							<span style="margin-left: 0.5em" class="fakeLink" onmouseout="return nd();" onmouseover="selectedCountsPopup();">
								<span id="tissueSelected">
									<%=rh.getSelectedIdsCount()%>
								</span>&nbsp;Samples</span>

							<span style="margin-left: 2em;">
								<b>On Hold:</b>
							</span>
							<span style="margin-left: 0.5em" class="fakeLink" onmouseout="return nd();" onmouseover="onHoldCountsPopup();">
								<%=(holdSummary == null) ? "0" : String.valueOf(holdSummary.getTotalSampleCount())%> Samples
							</span>

							<span style="margin-left: 2em;">
								<a href='#' onclick="exportList();" class="exportLink">Export to CSV</a>
							</span>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</tbody>
    </table>
	</div>

	<html:form method="POST" styleId="resultsForm" action="/library/holdAdd" style="margin: 0">
		<input type="hidden" name="productType" value="<%=currentProductType%>">					            
		<input type="hidden" name="currentChunk" value="<%= currentNum %>">					            
		<input type="hidden" name="nextChunk" value="<%= currentNum %>">
		<input type="hidden" name="txType" value="<%=txType%>"/>
		<input type="hidden" name="isDescSort" />
		<input type="hidden" name="sortedColumn" />

	<%
	// DIV for buttons and page navigation
	%>
	<div id="Buttons" style="position: absolute; width: 100%; overflow: hidden; z-index: 2; margin: 0; padding-bottom: 0.5em; border: 2px solid #336699; border-top: none;">
		<table border="0" cellspacing="0" cellpadding="2" class="foreground-small" width="100%">
			<col width="15%">
			<col width="70%">
			<col width="15%">
		<tbody>
			<tr><td><html:img page="/images/px1.gif" height="6" border="0"/></td></tr>	

		<% 
		// Only display the page navigation if there is more than 1 chunk
			if (maxNum > 0) {
		%>
				<tr>
					<td>&nbsp;</td>
					<td align="center">
					    <b>
						<span class="fakeLink" onclick="submitPagerForm(0);">&lt;&lt;&nbsp;First</span>
						</b>&nbsp;&nbsp;
						<b>
						<% if (currentNum > 0) { %>
							<span class="fakeLink" onclick="submitPagerForm(<%= currentNum - 1 %>);">&lt;&nbsp;Previous</span>
						<% } else { %>
						<font color="#999999">&lt; Previous</font>
						<% } %>
						</b>&nbsp;&nbsp;
						<b><% for(int i = fromNum; i <= toNum; i++){ %>
							<% if(i == currentNum) { %>
								<font color="#999999"><%= i+1 %></font>&nbsp;
							<% } else { %>
								<span class="fakeLink" onclick="submitPagerForm(<%= i %>);"><%= i+1 %></span>&nbsp;
							<% } %>
						<% } %>
						</b>&nbsp;&nbsp;
						<b>
						<% if (currentNum < maxNum) { %>
							<span class="fakeLink" onclick="submitPagerForm(<%= currentNum + 1 %>);">Next&nbsp;&gt;</span>
						<% } else { %>
			    		<font color="#999999">Next &gt;</font>
						<% } %>
						</b>&nbsp;&nbsp;
						<b>
							<span class="fakeLink" onclick="submitPagerForm(<%= maxNum%>);">Last&nbsp;&gt;&gt;</span>
						</b>
					</td>
					<td align="right">
						<b>Page <%= currentNum+1 %> of <%= maxNum+1 %></b>
					</td>
				</tr>
				<tr><td><html:img page="/images/px1.gif" height="4" border="0"/></td></tr>	
		<% 
			}
		%>

			<tr>
			<td colspan="3" align="center">
			<% if (txType.equals(ResultsHelper.TX_TYPE_SAMP_SEL)) { %>
				<input type="button" disabled="true" id="button1" style="FONT-SIZE:xx-small"
				  value="Place Items on Hold" onclick="onClickSubmitFormForHold();">
				<bigr:hasPrivilege priv="<%=SecurityInfo.PRIV_VIEW_HOLD%>">
					<input type="button" disabled="true" id="button3" style="FONT-SIZE:xx-small"
					  value="View Items on Hold" onclick="onClickGoToHoldView();">
				</bigr:hasPrivilege>
				<% } else if (txType.equals(ResultsHelper.TX_TYPE_SAMP_REQUEST)) { %>
					<input type="button" id="selectAll" disabled="true" style="FONT-SIZE:xx-small"
					  value="Select All" onclick="onClickSelectAllSamples('samples');">
					<input type="button" id="requestSamples" disabled="true" style="FONT-SIZE:xx-small"
					  value="Request Samples" onclick="onClickSubmitFormForRequestSamples();">
					<input type="button" disabled="true" id="button3" style="FONT-SIZE:xx-small"
					  value="View Selections" onclick="onClickGoToHoldView();">
				<% } %>
				<input type="button" disabled="true" id="button4" style="FONT-SIZE:xx-small"
				  value="Modify Search" onclick="onClickModifySearch();">
			</td>
		</tr>			
	</tbody>
	</table>
	</div>

	<div id="clipBoardMarker">
  <jsp:include page="/hiddenJsps/library/TissueDetailTable.jsp" flush="true">
  	<jsp:param name="groupedByCase" value="true"/>
  </jsp:include>
  </div>
	</html:form>
	</div>

</body>
</html:html>
<% } // END RESULTSHELPER SYNCHRONIZED BLOCK %>
