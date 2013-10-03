<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ardais.bigr.security.SecurityInfo"%>
<%@ page import="com.ardais.bigr.library.web.helper.ResultsHelper"%>
<%@ page import="com.ardais.bigr.iltds.btx.BTXDetails"%>
<%@ page import="com.ardais.bigr.iltds.assistants.LegalValueSet"%>
<%@ page import="com.ardais.bigr.iltds.helpers.RequestType"%>
<%@ page import="com.ardais.bigr.util.WebUtils"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%
  // -- Standard block for all query pages to find the proper txType
	// -- also MUST put a hidden txType field in the form
	String txType = null;
  String resultsFormDefinitionId = (String)request.getAttribute("resultsFormDefinitionId");
  List resultsFormDefinitions = (List)request.getAttribute("resultsFormDefinitions");
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
	} // end standard txType block
%>
<%
  String pageTitle;
  if (txType.equals(ResultsHelper.TX_TYPE_SAMP_REQUEST)) {
    pageTitle = "Request Samples Hold List";
  }
  else {
    pageTitle = "Items on Hold";
  }
%>
<%
	boolean isCanCreatePathologyRequests = false;
	boolean isCanCreateRDRequests = false;
	SecurityInfo secInfo = WebUtils.getSecurityInfo(request);
	if (secInfo != null) {
		isCanCreatePathologyRequests = secInfo.isInRoleSystemOwner() &&
		                                       secInfo.isHasPrivilege(SecurityInfo.PRIV_ILTDS_CREATE_PATH_REQUESTS);
		isCanCreateRDRequests = secInfo.isInRoleSystemOwner() &&
		                                       secInfo.isHasPrivilege(SecurityInfo.PRIV_ILTDS_CREATE_RAND_REQUESTS);

	}
%>
<html:html>
<head>
<title><%= pageTitle %></title>
<link rel="stylesheet" type="text/css"
	href='<html:rewrite page="/css/bigr.css"/>'>

<script type="text/javascript"
	src='<html:rewrite page="/js/common.js"/>'></script>

<script type="text/javascript"
	src='<html:rewrite page="/js/BigrLib.js"/>'></script>

<script type="text/javascript"
	src='<html:rewrite page="/js/ssresults.js"/>'></script>

<script type="text/javascript"
	src='<html:rewrite page="/js/holdList.js"/>'></script>

<script type="text/javascript"
	src='<html:rewrite page="/js/overlib.js"/>'><!-- overLIB (c) Erik Bosrup --></script>
<%
	//start files needed for display of Aperio images (SWP-1061)
%>
<jsp:include page="/hiddenJsps/kc/misc/coreScript.jsp" flush="true"/>
<script type="text/javascript" src='<html:rewrite page="/js/integration/bigrAperio.js"/>'></script>
<script type="text/javaScript" src="<html:rewrite page="/js/lightbox/gsbioLightbox.js"/>"></script>
<script type="text/javaScript" src="<html:rewrite page="/hiddenJsps/kc/detViewer/js/scriptaculous.js?load=builder"/>"></script>
<link rel="stylesheet" type="text/css" href='<html:rewrite page="/js/lightbox/gsbioLightbox.css"/>'>
<%
	//end files needed for display of Aperio images (SWP-1061)
%>

<script type="text/javascript">
var myBanner = '<%= pageTitle %>';  
var navigationConfirmed = false;

function initPage() {
  commonInitPage();
<% if (txType.equals(ResultsHelper.TX_TYPE_SAMP_REQUEST)) { %>
  switchDeliverTo();
<% } %>
  setActionButtonEnabling(true);
  _isPageReadyForInteraction = true;
}

<%-- Function to refresh page after view selection menu closes or after being
     called from the Manage Views page. --%>
function reloadTable(resultsFormDefinitionId) { 		
  this.focus();
  if (resultsFormDefinitionId != null) {
    if (shouldNavigateAway()) {
      navigationConfirmed = true;
			document.all.wholePage.style.display = 'none';
			document.all.waitMessage.style.display = 'block';
      var url = '<html:rewrite page="/library/holdView.do"/>?txType=<%=txType%>';
      url = url + "&resultsFormDefinitionId=" + resultsFormDefinitionId;
      setActionButtonEnabling(false);
      window.location.href = url;
    }
    else {
      navigationConfirmed = false;
    }
  }
}

function onClickSelectAdditionalItems() {
  setActionButtonEnabling(false);
  if (! shouldNavigateAway()) {
    setActionButtonEnabling(true);
    return;
  }
  _isWarnOnNavigate = false;
  window.location.href =
    '<html:rewrite page="/library/showResults.do"/>?txType=<%=txType%>';
}

function onClickRemoveSelectedItems() {
  setActionButtonEnabling(false);
 	<bigr:notHasPrivilege priv="<%=SecurityInfo.PRIV_HOLDLIST_ADDRMV%>">
      alert("You do not have the privilege to perform this action. Please contact your Administrator.");
      setActionButtonEnabling(true);
      return; 	
 	</bigr:notHasPrivilege>
 	<bigr:hasPrivilege priv="<%=SecurityInfo.PRIV_HOLDLIST_ADDRMV%>">
    if (_selectedCounts.getTotal() == 0) {
      alert("You have not selected any items to remove.");
      setActionButtonEnabling(true);
      return;
    }
    _isWarnOnNavigate = false;
    var form = document.forms[0];
    <%-- The URL to use is slightly different for this action depending on
      ** whether we're on the single-category hold list or the multi-category
      ** hold list.  See HoldListMulti.jsp for the other version. --%>
    form.submit();
 	</bigr:hasPrivilege>
}

function onClickOrderItems() {
  setActionButtonEnabling(false);
  if (! shouldNavigateAway()) {
    setActionButtonEnabling(true);
    return;
  }
  _isWarnOnNavigate = false;
  <%-- The URL to use is slightly different for this action depending on
    ** whether we're on the single-category hold list or the multi-category
    ** hold list.  See HoldListMulti.jsp for the other version. --%>
  window.location.href =
    '<html:rewrite page="/library/requestConfirm.do"/>?txType=<%=txType%>&useSingleCategory=true';
}

function onClickPlaceAllItemsOnClipboard() {
  setActionButtonEnabling(false);
  var itemClip = '';
  itemClip = accumulateClipboardText(itemClip, 'allItemsinventoryItemViewElem');
  setClipboardItems(itemClip);
  setActionButtonEnabling(true);
}

function onClickPlaceSelectedItemsOnClipboard() {
  setActionButtonEnabling(false);
  placeSelectedItemsOnClipboard('samples');
  setActionButtonEnabling(true);
}

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

	if (sampleClip == "") {
		alert ("No samples have been selected.");		  
	}
	else {
		copyToClipboard(sampleClip);
		alert ("The selected samples have been placed on the clipboard.");
	}
}

function onClickCopySectionToClipboard() {
	setActionButtonEnabling(false);

	var itemClip = '';
	itemClip = accumulateClipboardText(itemClip, 'allItemsinventoryItemViewElem');
	
	if (itemClip == '') {
		alert('No items have been placed on the clipboard.');		  
	} else {
		var outterDiv = document.getElementById(_clipboardMenuSectionId);
		var innerHtml = outterDiv.innerHTML;
		copyToClipboard(innerHtml);
		alert('The section has been placed on the clipboard.');
	}
	setActionButtonEnabling(true);
}

function onClickCreateRequest() {
  setActionButtonEnabling(false);
  if (! shouldNavigateAway()) {
    setActionButtonEnabling(true);
    return;
  }

	// get the request type
<%
if ((isCanCreatePathologyRequests) || (isCanCreateRDRequests)) {
%> 
 	var requestType = document.all.requestType;
 	var requestTypeValue = requestType.options[requestType.selectedIndex].value;
<%
  }
  else {
%>
 	var requestType = document.all.requestType;
 	var requestTypeValue = requestType.value;
<%
  }
%>

  if (requestTypeValue == null || requestTypeValue == "") {
    alert('Please select a Request Type.');
    setActionButtonEnabling(true);
    return;
  }

  _isWarnOnNavigate = false;

  if ((requestTypeValue == '<%= BTXDetails.BTX_TYPE_PATH_SAMPLE_REQUEST %>')
		 || (requestTypeValue == '<%= BTXDetails.BTX_TYPE_RND_SAMPLE_REQUEST %>')) // path or r&d
  		{
	 	var url = '<html:rewrite page="/library/createPicklist.do"/>';
	 	
	 	var delivTo = document.all.deliverTo;
	 	var delivToValue = delivTo.options[delivTo.selectedIndex].value;
	 	var txType = document.all.txType.value;
	 	
	 	url = url + '?deliverTo=' + delivToValue + '&requestType=' + requestTypeValue + '&txType=' + txType;
	 	window.location.href = url;
	}
  else { //Transfer Request
	 	var url = '<html:rewrite page="/iltds/createTransferRequestStart.do"/>';
	 	
	 	var txType = document.all.txType.value;
	 	
	 	url = url + '?txType=' + txType;
	 	window.location.href = url;
  }
}

function switchDeliverTo() {
<%
if ((isCanCreatePathologyRequests) || (isCanCreateRDRequests)) {
%> 
	// get the request type
 	var requestType = document.all.requestType;
 	var requestTypeValue = requestType.options[requestType.selectedIndex].value;
 	var delivTo = document.all.deliverTo;
 	var delivToSpan = document.all.delivToSpan;

  if (requestTypeValue == null || requestTypeValue == "") {
    delivTo.disabled = true;
    delivTo.style.display = 'inline';
    delivToSpan.style.display = 'inline';
  }
  else if ((requestTypeValue == '<%= BTXDetails.BTX_TYPE_PATH_SAMPLE_REQUEST %>')
		 || (requestTypeValue == '<%= BTXDetails.BTX_TYPE_RND_SAMPLE_REQUEST %>')) { // path or r&d
    delivTo.disabled = false;
    delivTo.style.display = 'inline';
    delivToSpan.style.display = 'inline';
  }
	else {
	  // It is a transfer request.
    delivTo.disabled = true;
    delivTo.style.display = 'none';
    delivToSpan.style.display = 'none';
	}
<%
  }
%>
}
   
function setActionButtonEnabling(isEnabled) {
  var f = document.forms[0];

  setInputEnabled(f, 'btnCreateRequest', isEnabled);
  setInputEnabled(f, 'btnArdaisOrder', isEnabled);
  setInputEnabled(f, 'btnSelectAdditional', isEnabled);
  setInputEnabled(f, 'btnAllRemove', isEnabled);
  setInputEnabled(document.all, 'allClipboard', isEnabled);
}
</script>
</head>
<body class="bigr" style="margin-top: 2px;" onload="initPage();"
  onbeforeunload="if (!navigationConfirmed) {confirmNavigate();}" onunload="onUnloadPage();">

<html:errors/>
<bigr:btxActionMessages/>
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
  <% //div for whole page %>
	<div id="wholePage">

<table id="clipboardMenuPopup" class="contextMenu"
  onmouseover="highlightMenuItem();" onmouseout="unHighlightMenuItem();"
  onclick="onClickClipboardMenu();"
  border="0" cellspacing="0" cellpadding="3">
  <tr><td menuFunction="onClickPlaceAllItemsOnClipboard();">Copy All Sample Ids to Clipboard</td></tr>
  <tr><td menuFunction="onClickPlaceSelectedItemsOnClipboard();">Copy Selected Sample Ids to Clipboard</td></tr>
  <tr><td menuFunction="onClickCopySectionToClipboard();">Copy Section to Clipboard</td></tr>
</table>

<html:form action="/library/holdRemove">
	<input type="hidden" name="txType" value="<%=txType%>" />

  <div style="margin-bottom: 3px;">
    <bigr:selectViewLinkMenu
      currentResultsFormDefinitionId="<%=resultsFormDefinitionId%>"
      resultsFormDefinitions="<%=resultsFormDefinitions%>"
      menuId="selectViewMenu"
    />
    <bigr:selectViewLink
      currentResultsFormDefinitionId="<%=resultsFormDefinitionId%>"
      resultsFormDefinitions="<%=resultsFormDefinitions%>"
      menuId="selectViewMenu"
    />
    <SMALL>
      <span id="allClipboard"
        class="fakeLink"
		    onclick="doClipboardActionOnClick()"
		    clipboardMenu="clipboardMenuPopup"
		    sectionId="allItems">Clipboard Actions</span>
    </SMALL>
  </div>

<div id="Buttons"
	style="position: absolute; width: 100%; overflow: hidden; z-index: 2;">
<table border="0" cellspacing="0" cellpadding="0" class="background" width="100%">
	<col width="100%">
	<tbody>
		<tr>
			<td>
			<table border="0" cellspacing="1" cellpadding="2"
				class="foreground-small" width="100%">
				<tbody>
					<tr class="yellow">
						<td align="center" colspan="4">If removing items, please press
						the "Remove Selected Items" button before continuing.  Items will
						not be removed unless you press this button.</td>
					</tr>
					<tr class="yellow">
					<td align="center">
						<%
						  if(txType.equals(ResultsHelper.TX_TYPE_SAMP_REQUEST)){
						    if ((isCanCreatePathologyRequests) || (isCanCreateRDRequests)) {
						%>
							Request Type&nbsp;&nbsp; 
							<select name="requestType" style="FONT-SIZE: xx-small"  onchange="switchDeliverTo()">
								<option value="" selected>Select</option>
								<%
						    if (isCanCreatePathologyRequests)  {
								%>
									<option
										value="<%= BTXDetails.BTX_TYPE_PATH_SAMPLE_REQUEST %>">Pathology
										Request</option>
								<%} 
						    if (isCanCreateRDRequests) {
								%>												
									<option
										value="<%= BTXDetails.BTX_TYPE_RND_SAMPLE_REQUEST %>">R and D
										Request</option>
								<% } %>
								<option
									value="<%= RequestType.TRANSFER.toString() %>">Transfer
									Request</option>	
							</select><span id="delivToSpan"> &nbsp;&nbsp;&nbsp; Deliver To&nbsp;&nbsp;</span>
							<% Vector staff = (Vector) request.getAttribute("staff"); %>
							<select name="deliverTo"
							        style="FONT-SIZE: xx-small; display: none;" >
								<option value="" selected>Select</option>
								<%for (int i = 0 ; i < staff.size() ; i += 2) {%>
									<option value="<%= staff.get(i)%>"><%= staff.get(i)%></option>
								<%}%>							
							</select>
							<br/>
						<%
						    }
						    else {
						%>
							<input type="hidden" name="requestType" value="<%= RequestType.TRANSFER.toString() %>">
						<%
						    }
						%>
							<input type="button" disabled="true" name="btnCreateRequest"
							  value="Create Request"
							  style="FONT-SIZE: xx-small;"
								onclick="onClickCreateRequest();">
							<br/>
						<%
						  } else if(txType.equals(ResultsHelper.TX_TYPE_SAMP_SEL)){ 
						%> 
				      <input type="button" disabled="true" name="btnArdaisOrder"
				        value="Order These Items"
  						  style="font-size: xx-small;"
				        onclick="onClickOrderItems();"/>
						<%}%> 
						<bigr:hasPrivilege priv="<%=SecurityInfo.PRIV_SAMPLE_SELECT%>">
							<input type="button" disabled="true" name="btnSelectAdditional"
							  value="Select Additional Items"
  						  style="font-size: xx-small;"
								onclick="onClickSelectAdditionalItems();">
						</bigr:hasPrivilege>
						<%
							//MR8027
							//if the user has the Sample Selection privilege then
							//the "Select Additional Items" button will show up.  However,
							//we also need to handle the case where they don't have Sample Selection
							//but do have Request Samples (the button needs to appear for that as well).
							//since we don't want two buttons, only do this if the user doesn't have the
							//Sample Selection privilege
						%>
						<bigr:notHasPrivilege priv="<%=SecurityInfo.PRIV_SAMPLE_SELECT%>">
						  <bigr:hasPrivilege priv="<%=SecurityInfo.PRIV_ILTDS_REQUEST_SAMPLES%>">
							<input type="button" disabled="true" name="btnSelectAdditional"
							  value="Select Additional Items"
  						      style="font-size: xx-small;"
							  onclick="onClickSelectAdditionalItems();">
						  </bigr:hasPrivilege> 
						</bigr:notHasPrivilege> 
						<input type="button" disabled="true" name="btnAllRemove"
						  value="Remove Selected Items"
						  style="font-size: xx-small;"
							onclick="onClickRemoveSelectedItems();"> 
					</td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
	</tbody>
</table>
</div> <%-- End buttons DIV --%>

	<jsp:include page="/hiddenJsps/library/TissueDetailTable.jsp" flush="true">
  	<jsp:param name="groupedByCase" value="true"/>
	</jsp:include>
</html:form>
</div>
</body>
</html:html>
