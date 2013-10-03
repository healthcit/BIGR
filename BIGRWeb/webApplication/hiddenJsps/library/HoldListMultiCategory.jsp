<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ardais.bigr.security.SecurityInfo"%>
<%@ page import="com.ardais.bigr.api.ApiException"%>
<%@ page import="com.ardais.bigr.api.Escaper"%>
<%@ page import="com.ardais.bigr.library.SampleViewData"%>
<%@ page import="com.ardais.bigr.library.web.helper.ResultsHelper"%>
<%@ page import="com.ardais.bigr.iltds.btx.BTXDetails"%>
<%@ page import="com.ardais.bigr.iltds.assistants.LegalValueSet"%>
<%@ page import="com.ardais.bigr.library.btx.BTXDetailsGetSamples"%>
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
  ResultsHelper helper = ResultsHelper.get(txType, request);
	if (helper == null) {
		throw new ApiException("Request attribute " + ResultsHelper.KEY + txType
		  + " not defined in HoldListMultiCategory.jsp");
	}

	//---------------------------  synch entire request on the ResultsHelper
	synchronized (helper) {
	//---------------------------  synch entire request on the ResultsHelper
		
		// Get data to output in the actual table (row numbers and sample data).
		SampleViewData viewData = helper.getHelpers();
		viewData.setGroupedByCase(true);
		viewData.setHtmlForEmptyDisplay("There are no items in this category.");
		
		int categoryItemCount = 0; // set while processing each hold category below
%>
<html:html>
<head>
<title>Items on Hold</title>
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
var myBanner = 'Items on Hold';  
var navigationConfirmed = false;

<%-- These get set to true later for empty categories, as the page loads --%>
var _isCategoryEmptyArdais = false;
var _isCategoryBmsAvail = false;
var _isCategoryBmsPending = false;
var _isCategoryBmsUnavail = false;

function initPage() {
  commonInitPage();
  setActionButtonEnabling(true);
  _isPageReadyForInteraction = true;
}

function onClickCopySampleIdsToClipboard() {
	onClickPlaceItemsOnClipboard(_clipboardMenuSampleIds);
}

function onClickCopySectionToClipboard() {
	setActionButtonEnabling(false);
	if (isCategoryEmpty(_clipboardMenuSectionId)) {
		alert('No items have been placed on the clipboard.');
	} else {
		copyToClipboard(document.getElementById(_clipboardMenuSectionId).innerHTML);
		alert('The section has been placed on the clipboard.');
	}
	setActionButtonEnabling(true);
}

function isCategoryEmpty(sectionId) {
	if (sectionId == 'ardaisItems') {
		return _isCategoryEmptyArdais;
	}
	else if (sectionId == 'bmsAvailableItems') {
		return _isCategoryBmsAvail;
	}
	else if (sectionId == 'bmsPendingItems') {
		return _isCategoryBmsPending;
	}
	else if (sectionId == 'bmsUnavailableItems') {
		return _isCategoryBmsUnavail;
	}
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
      ** hold list.  See HoldListSingle.jsp for the other version. --%>
    form.action =
      '<html:rewrite page="/library/holdDispatch.do?action=holdRemove"/>';
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
    ** hold list.  See HoldListSingle.jsp for the other version. --%>
  window.location.href =
    '<html:rewrite page="/library/requestConfirm.do"/>?txType=<%=txType%>';
}

function onClickPlaceAllItemsOnClipboard() {
  setActionButtonEnabling(false);
  var itemClip = '';
  itemClip = accumulateClipboardText(itemClip, 'allItemsholdCatArdaisView');
  itemClip = accumulateClipboardText(itemClip, 'allItemsholdCatBmsAvailView');
  itemClip = accumulateClipboardText(itemClip, 'allItemsholdCatBmsPendingView');
  itemClip = accumulateClipboardText(itemClip, 'allItemsholdCatBmsUnavailView');
  setClipboardItems(itemClip);
  setActionButtonEnabling(true);
}

function onClickPlaceSelectedItemsOnClipboard() {
  setActionButtonEnabling(false);
  var itemClip = '';
  <%-- Accumulate selected sample ids from the three sections of the hold list where samples
       have selection checkboxes. --%>
  itemClip = accumulateSelectedItemsClipboardText(itemClip, 'cAi');
  itemClip = accumulateSelectedItemsClipboardText(itemClip, 'cBAi');
  itemClip = accumulateSelectedItemsClipboardText(itemClip, 'cBUi');
  setClipboardItems(itemClip);
  setActionButtonEnabling(true);
}

function onClickRequestItems() {
  setActionButtonEnabling(false);
  if (! shouldNavigateAway()) {
    setActionButtonEnabling(true);
    return;
  }
  _isWarnOnNavigate = false;
  var form = document.forms[0];
  form.action =
    '<html:rewrite page="/library/holdDispatch.do?action=requestItems"/>';
  form.submit();
}

function onClickViewMyRequests() {
  setActionButtonEnabling(false);
  if (! shouldNavigateAway()) {
    setActionButtonEnabling(true);
    return;
  }
  _isWarnOnNavigate = false;
  window.location.href = '<html:rewrite page="/iltds/viewRequestsStart.do"/>';
}

function onClickOpenHelp() {
  setActionButtonEnabling(false);
  var theURL = '<html:rewrite page="/library/holdViewMultiHelp.do"/>';

  var w = window.open(theURL, 'BIGRHelp',
    'scrollbars=yes,resizable=yes,status=yes,width=640,height=600,top=0');
  w.focus();
  
  setActionButtonEnabling(true);
}
   
function setActionButtonEnabling(isEnabled) {
  <%-- Don't enable buttons on empty categories --%>
  var f = document.forms[0];

  if (!(isEnabled && _isCategoryEmptyArdais)) {
    setInputEnabled(f, 'btnArdaisOrder', isEnabled);
    setInputEnabled(document.all, 'ardaisClipboard', isEnabled);
  }

  if (!(isEnabled && _isCategoryBmsAvail)) {
    setInputEnabled(f, 'btnBmsAvailRequest', isEnabled);
    setInputEnabled(document.all, 'bmsAvailableClipboard', isEnabled);
  }

  <%-- The "View Requests" button gets enabled even if the "BMS Pending"
    ** category is empty, since it do anything with the category items.
    --%>
  setInputEnabled(f, 'btnBmsPendingView', isEnabled);
  if (!(isEnabled && _isCategoryBmsPending)) {
    setInputEnabled(document.all, 'bmsPendingClipboard', isEnabled);
  }

  if (!(isEnabled && _isCategoryBmsUnavail)) {
    setInputEnabled(document.all, 'bmsUnavailableClipboard', isEnabled);
  }

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

<%--
  ** DIV for overlib, which is used for tooltips.  If we didn't include this
  ** here, it would get placed on the page by the bigr:inventoryItemView
  ** later.  We put it here because there's a bug in overLib that causes
  ** the tooltips to show up in the wrong place when the screen is scrolled
  ** partway down.  I suspect that the problem has to do with coordinate
  ** system math in overLib that gets thrown off if the overlib DIV is
  ** inside of an element that is itself positioned (the primaryArea DIV on
  ** this page).
  --%>
<bigr:overlibDiv/>
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

<table id="clipboardMenuPopupTop" class="contextMenu"
  onmouseover="highlightMenuItem();" onmouseout="unHighlightMenuItem();"
  onclick="onClickClipboardMenu();"
  border="0" cellspacing="0" cellpadding="3">
  <tr><td menuFunction="onClickPlaceAllItemsOnClipboard();">Copy All Sample Ids to Clipboard</td></tr>
  <tr><td menuFunction="onClickPlaceSelectedItemsOnClipboard();">Copy Selected Sample Ids to Clipboard</td></tr>
</table>

<table id="clipboardMenuPopup" class="contextMenu"
  onmouseover="highlightMenuItem();" onmouseout="unHighlightMenuItem();"
  onclick="onClickClipboardMenu();"
  border="0" cellspacing="0" cellpadding="3">
  <tr><td menuFunction="onClickCopySampleIdsToClipboard();">Copy Sample Ids to Clipboard</td></tr>
  <tr><td menuFunction="onClickCopySectionToClipboard();">Copy Section to Clipboard</td></tr>
</table>


<html:form action="/library/holdDispatch">
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
         style="margin-left: 4px;"
		    onclick="doClipboardActionOnClick()"
		    clipboardMenu="clipboardMenuPopupTop">Clipboard Actions</span>
    </SMALL>
    <small>
      <span class="fakeLink" style="margin-left: 20px;"
        onclick="onClickOpenHelp();">Help</span>
    </small>
  </div>
  
<div id="Buttons"
	style="position: absolute; width: 100%; overflow: hidden; z-index: 2;">
<table border="0" cellspacing="0" cellpadding="0" class="background"
       width="100%" style="margin-top: 4px;">
	<col width="100%">
	<tbody>
		<tr>
			<td>
			<table border="0" cellspacing="1" cellpadding="2" class="foreground-small" width="100%">
				<tbody>
					<tr class="yellow">
						<td align="center" colspan="4">If removing items, please press
						the "Remove Selected Items" button before continuing.  Items will
						not be removed unless you press this button.</td>
					</tr>
					<tr class="yellow">
					  <td align="center">
						  <bigr:hasPrivilege priv="<%=SecurityInfo.PRIV_SAMPLE_SELECT%>">
							  <input type="button" disabled="true" name="btnSelectAdditional"
								  value="Select Additional Items"
  						    style="font-size: xx-small;"
								  onclick="onClickSelectAdditionalItems();">
						  </bigr:hasPrivilege> 
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

<div id="primaryArea"
		 style="display: block; position: relative; overflow: auto; padding-right: 5px; border-width: 0px 1px 3px 0px; border-style: solid; border-color: #336699;">

<script type="text/javascript">
  var primaryAreaDiv = document.all.primaryArea;
  var buttonsDiv = document.all.Buttons;
  primaryAreaDiv.style.setExpression('pixelHeight',
    'Math.max(100, document.body.clientHeight - document.all.Buttons.offsetHeight - document.all.primaryArea.offsetTop)');
  primaryAreaDiv.style.setExpression('pixelWidth',
    'document.all.Buttons.offsetWidth');
  buttonsDiv.style.setExpression('pixelTop',
	  'document.all.primaryArea.offsetTop + document.all.primaryArea.offsetHeight');
  document.recalc(true);
</script>

<%-- *******************************************************************
  Category: Ardais Items ***********************************************
  ********************************************************************** --%>
<%
    viewData.setCategory(BTXDetailsGetSamples.HOLD_CATEGORY_ARDAIS);
		viewData.setIncludeItemSelector(true);
		viewData.setViewElementId("holdCatArdaisView");
		viewData.setItemSelectorName("cAi"); // "cAi": "Category Ardais Item"
		categoryItemCount = viewData.getSampleHelpers().size();
%>
<% if (categoryItemCount > 0) { %>
<table class="holdCategoryHeader" border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr>
    <td>Available items in your supplier biorepositories</td>
    <td align="right">
      <input type="button" disabled="true" name="btnArdaisOrder"
        value="Order These Items"
        onclick="onClickOrderItems();"
        style="width: 135px;"/>
      <span id="ardaisClipboard"
            disabled="true"
            class="fakeLink"
            onclick="doClipboardActionOnClick()"
            clipboardMenu="clipboardMenuPopup"
            sampleIds='<%=viewData.getAllItemsElementId()%>'
            sectionId="ardaisItems">Clipboard Actions</span>
    </td>
  </tr>
</table>
<div id="ardaisItems" style="margin: 5px 0px 12px 15px;">
  <bigr:inventoryItemView itemViewData="<%= viewData %>" />
</div>
<% } %>

<%-- *******************************************************************
  Category: BMS items that are available for research requests *********
  ********************************************************************** --%>
<%
    viewData.setCategory(BTXDetailsGetSamples.HOLD_CATEGORY_BMS_AVAILABLE);
		viewData.setIncludeItemSelector(true);
		viewData.setViewElementId("holdCatBmsAvailView");
		viewData.setItemSelectorName("cBAi"); // "cBAi": "Category BMS Available Item"
		categoryItemCount = viewData.getSampleHelpers().size();
%>
<table class="holdCategoryHeader" border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr>
    <td>Available items in your biorepositories</td>
    <td align="right">
      <input type="button" disabled="true" name="btnBmsAvailRequest"
        value="Request These Items"
        onclick="onClickRequestItems();"
        style="width: 135px;"/>
      <span id="bmsAvailableClipboard"
            disabled="true"
            class="fakeLink"
            onclick="doClipboardActionOnClick()"
            clipboardMenu="clipboardMenuPopup"
            sampleIds='<%=viewData.getAllItemsElementId()%>'
            sectionId="bmsAvailableItems">Clipboard Actions</span>
    </td>
  </tr>
</table>
<div id="bmsAvailableItems" style="margin: 5px 0px 12px 15px;">
  <% if (categoryItemCount == 0) { %>
    <script type="text/javascript">_isCategoryBmsAvail = true;</script>
  <% } %>
  <bigr:inventoryItemView itemViewData="<%= viewData %>" />
</div>

<%-- *******************************************************************
  Category: BMS items that are currently on user's pending research requests
  ********************************************************************** --%>
<%
    viewData.setCategory(BTXDetailsGetSamples.HOLD_CATEGORY_BMS_ON_PENDING_REQUEST);
		viewData.setIncludeItemSelector(false);
		viewData.setViewElementId("holdCatBmsPendingView");
		viewData.setItemSelectorName(null); // Pending items can't be selected
		categoryItemCount = viewData.getSampleHelpers().size();
%>
<table class="holdCategoryHeader" border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr>
    <td>Items on your pending requests</td>
    <td align="right">
      <bigr:hasPrivilege priv="<%=SecurityInfo.PRIV_ILTDS_VIEW_REQUESTS%>">
        <input type="button" disabled="true" name="btnBmsPendingView"
          value="View My Requests"
          onclick="onClickViewMyRequests();"
          style="width: 135px;"/>
      </bigr:hasPrivilege>
      <span id="bmsPendingClipboard"
            disabled="true"
            class="fakeLink"
            onclick="doClipboardActionOnClick()"
            clipboardMenu="clipboardMenuPopup"
            sampleIds='<%=viewData.getAllItemsElementId()%>'
            sectionId="bmsPendingItems">Clipboard Actions</span>
    </td>
  </tr>
</table>
<div id="bmsPendingItems" style="margin: 5px 0px 12px 15px;">
  <% if (categoryItemCount == 0) { %>
    <script type="text/javascript">_isCategoryBmsPending = true;</script>
  <% } %>
  <bigr:inventoryItemView itemViewData="<%= viewData %>" />
</div>

<%-- *******************************************************************
  Category: BMS items that aren't in the other BMS categories **********
  ********************************************************************** --%>
<%
    viewData.setCategory(BTXDetailsGetSamples.HOLD_CATEGORY_BMS_UNAVAILABLE);
		viewData.setIncludeItemSelector(true);
		viewData.setViewElementId("holdCatBmsUnavailView");
		viewData.setItemSelectorName("cBUi"); // "cBUi": "Category BMS Unavailable Item"
		categoryItemCount = viewData.getSampleHelpers().size();
%>
<table class="holdCategoryHeader" border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr>
    <td>Items in your biorepositories that are currently unavailable</td>
    <td align="right">
      <span id="bmsUnavailableClipboard"
            disabled="true"
            class="fakeLink"
            onclick="doClipboardActionOnClick()"
            clipboardMenu="clipboardMenuPopup"
            sampleIds='<%=viewData.getAllItemsElementId()%>'
            sectionId="bmsUnavailableItems">Clipboard Actions</span>
    </td>
  </tr>
</table>
<div  id="bmsUnavailableItems" style="margin: 5px 0px 12px 15px;">
  <% if (categoryItemCount == 0) { %>
    <script type="text/javascript">_isCategoryBmsUnavail = true;</script>
  <% } %>
  <bigr:inventoryItemView itemViewData="<%= viewData %>" />
</div>

<%-- End of hold list item categories ********************************** --%>

</div> <%-- End primaryArea --%>

</html:form>
</div>
</body>
</html:html>
<%
	}	// END RESULTSHELPER SYNCHRONIZED BLOCK
%>
