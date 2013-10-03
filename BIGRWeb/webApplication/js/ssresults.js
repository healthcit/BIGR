// Copyright 2004 Ardais Corporation.  All rights reserved.
//
// The functions in this file are common to displaying results
// of a sample selection query.  There are functions to
// open popups, show tooltips, show the case menu, and select and
// highlight samples.

// A flag that is used to indicate whether the user has put all
// of the selected samples on the clipboard.
var _isAllSelectedOnClipboard = false;
 
// True when the page is ready for the user to interact with it.
// Pages that display inventory item detail tables must set this
// to true as the very last thing done by the handler for their
// BODY tag onload event.
var _isPageReadyForInteraction = false;

// An array of parameters that are used with the sample actions.
// There is one entry per row in the sample selection results table,
// and each entry is an object that contains the parameters.
var _sampleActionParams = [];

// Indicates whether the case menu is being shown or not, and
// parameters of the case of the case menu being shown.
var _caseMenu = null;
var _caseMenuDonorId = null;
var _caseMenuDonorAlias = null;
var _caseMenuConsentId = null;
var _caseMenuConsentAlias = null;
var _caseMenuPathId = null;
var _caseMenuSampleId = null;
var _caseMenuSampleAlias = null;
var _caseMenuSampleHasAperioImage = null;

// Global variables used for the clipboard menu.
var _clipboardMenu = null;
var _clipboardMenuSamplesIds = null;
var _clipboardMenuSectionId = null;

// Perform common initialization functions concerning positioning.
// All pages that contain an inventory item details view must call
// this from the handler for their BODY tag onload event.
// If the page defines a variable named "myBanner", then the banner
// on the top frame will be set to its value.
// NOTE: Don't assume that that's all this function does.
function commonInitPage() {
  if ((myBanner != null) && (parent.topFrame != null)) {
    parent.topFrame.document.all.banner.innerHTML = myBanner; 
  }
}

function prepareRowData(rownum)
{
	_caseMenuDonorId = _sampleActionParams[rownum].donorId;
	_caseMenuDonorAlias = _sampleActionParams[rownum].donorAlias;
	_caseMenuConsentId = _sampleActionParams[rownum].consentId;
	_caseMenuConsentAlias = _sampleActionParams[rownum].consentAlias;
	_caseMenuPathId = _sampleActionParams[rownum].pathId;
	_caseMenuSampleId = _sampleActionParams[rownum].sampleId;
	_caseMenuSampleAlias = _sampleActionParams[rownum].sampleAlias;
	_caseMenuSampleHasAperioImage = _sampleActionParams[rownum].sampleHasAperioImage;
}

function displaySampleActionMenu(rownum) {
    prepareRowData(rownum);

  // The positioning code can get confused when the menu is inside something
  // unusual, such as an absolutely positioned element.  So here, we make
  // sure that the menu is a direct child of the body before we do anything
  // else.
  var menu = document.getElementById('sampleActionMenuContainer');
  if (document.body != menu.parentElement) {
    document.body.appendChild(menu);
  }

	processAperioMenuItem(menu, _caseMenuSampleHasAperioImage);

  var offParent = menu.offsetParent;
  var menuWidth = menu.offsetWidth;
  var menuHeight = menu.offsetHeight;
  var newClientX = event.clientX;
  var newClientY = event.clientY;
  if (newClientX + menuWidth > offParent.clientWidth) {
    newClientX = offParent.clientWidth - menuWidth;
  }
  if (newClientX < 0) newClientX = 0;
  if (newClientY + menuHeight > offParent.clientHeight) {
    newClientY = offParent.clientHeight - menuHeight;
  }
  if (newClientY < 0) newClientY = 0;
  menu.style.left = newClientX + offParent.scrollLeft;
  menu.style.top = newClientY + offParent.scrollTop;
  menu.style.zIndex = 1000;
  menu.style.visibility = 'visible';
  event.cancelBubble = true;
}

function processAperioMenuItem(menu, visible)
{
	var divs = menu.getElementsByTagName("div");
	for (var i=0; i < divs.length; i++)
	{
		var div = divs[i];
		var regexp = /.*javascript:openAperio\(.*/;
		if (regexp.test(div.menuFunction))
		{
			div.style.display = (visible) ? '' : 'none';
		}
	}
}

function bodyOnClick() {
  hideSampleActionMenu();
}

function sampleActionMenuClick() {
  var src = event.srcElement;
  if (src.menuFunction) {
    eval(src.menuFunction);
    event.returnValue = false;
  }
	
  unHighlightSampleActionMenuItems();
  hideSampleActionMenu();
}

function sampleActionMouseOver() {
  unHighlightSampleActionMenuItems();
  highlightSampleActionMenuItem(event.srcElement);
}

function hideSampleActionMenu() {
  document.getElementById('sampleActionMenuContainer').style.visibility = 'hidden';
}

function highlightSampleActionMenuItem(e) {
  if (e && e.menuFunction) {
    e.className = 'sampleActionMenuHighlight';
  }
}

function unHighlightSampleActionMenuItems() {
  var c = document.getElementById('sampleActionMenuContainer');
  if (c) {
    var divs = c.getElementsByTagName('div');
    if (divs) {
      if (divs.length) {
        for (var i = 0; i < divs.length; i++) {
          divs[i].className = '';
        }
      }
      else {
        divs[0].className = '';
      }
    }
  }
}

function openDonorInfo() {
  var url = '/BIGR/ddc/Dispatch?op=DonorProfileSummaryPrepare';
  window.open(
    url + '&ardaisId=' + _caseMenuDonorId + '&readOnly=true&popUp=true',
    'DonorInfo' + _caseMenuDonorId,
    'scrollbars=yes,resizable=yes,status=yes,width=700,height=600');
}

function openCaseInfo() {
  var url = '/BIGR/dataImport/getCaseFormSummary.do?';
  window.open(
    url + '&consentId=' + _caseMenuConsentId + '&readOnly=true',
    'CaseInfo' + _caseMenuConsentId,
    'scrollbars=yes,resizable=yes,status=yes,width=700,height=600');
}

function openSampleInfo() {
  var url = '/BIGR/dataImport/getSampleFormSummary.do?';
  window.open(
    url + '&sampleData.sampleId=' + _caseMenuSampleId + '&readOnly=true',
    'SampleInfo' + _caseMenuSampleId,
    'scrollbars=yes,resizable=yes,status=yes,width=700,height=600');
}

// This is a caseMenu menuFunction.
function openConsentText()  {
  var url = '/BIGR/orm/Dispatch?op=PrepViewConsent';
  window.open(
    url + '&consentID=' + _caseMenuConsentId + '&popUp=true',
    'ViewTextConsent',
    'scrollbars=yes,resizable=yes,status=yes,width=640,height=480');	  
}
  
// This is a caseMenu menuFunction.
function openRawPath() {
  var url = '/BIGR/ddc/Dispatch?op=PathRawPrepare';
  // add AID, CID to URL in addition to PathID
  // this allows us to gracefully handle situations where
  // there is no full text path report -- MR5990
  // NOTE: _caseMenuPathId, _caseMenuDonorId, _caseMenuConsentId are
  // set in the inventoryItemView.htc on-click handler.
  window.open(url + '&pathReportId=' + _caseMenuPathId 
   	+ '&ardaisId=' +  _caseMenuDonorId
   	+ '&consentId=' + _caseMenuConsentId
   	+ '&popup=true',
   	'ASCII373','scrollbars=yes,resizable=yes,status=yes,width=640,height=480'); 
}

// This is a caseMenu menuFunction.
function openPatientCaseReport() {
  var url = '/BIGR/library/Dispatch?op=PathInfoPathReport';
  window.open(
    url + '&path_id=' + _caseMenuPathId + '&donor_id=' + _caseMenuDonorId + '&index=0&case_id=' + _caseMenuConsentId + '&popUp=true',
    'PatientCaseReport',
    'scrollbars=yes,resizable=yes,status=yes,width=640,height=480');	
}
  
// This is a caseMenu menuFunction.
function openUrl(url, target)  {
  //substitute ids for insertion strings
  var asIdRegExp = /\$\$sample_id\$\$/gi;
  var asAliasRegExp = /\$\$sample_alias\$\$/gi;
  var acIdRegExp = /\$\$case_id\$\$/gi;
  var acAliasRegExp = /\$\$case_alias\$\$/gi;
  var adIdRegExp = /\$\$donor_id\$\$/gi;
  var adAliasRegExp = /\$\$donor_alias\$\$/gi;
  url = url.replace(asIdRegExp, _caseMenuSampleId);
  url = url.replace(asAliasRegExp, _caseMenuSampleAlias);
  url = url.replace(acIdRegExp, _caseMenuConsentId);
  url = url.replace(acAliasRegExp, _caseMenuConsentAlias);
  url = url.replace(adIdRegExp, _caseMenuDonorId);
  url = url.replace(adAliasRegExp, _caseMenuDonorAlias);
  //change any non-word characters in the target to a character that won't cause issues
  var nonWordRegExp = /\W/g;
  target = target.replace(nonWordRegExp,"_");
  //open the window or call the javascript function
  if (url.indexOf('javascript:') != 0) {
    var w = window.open(url, target, 'scrollbars=yes,resizable=yes,status=yes,left=0,top=0,width=700,height=600');
    w.focus();
  }
  else {
    eval(url.substr(11));
  }
}

// Highlight an item in the case menu.
function highlightMenuItem() {
  if ((event.clientX > 0) && (event.clientY > 0) && (event.clientX < document.body.offsetWidth) && (event.clientY<document.body.offsetHeight)) {
		if (event.srcElement.getAttribute("menuFunction") != null) {
		  var theStyle = event.srcElement.style;
      theStyle.backgroundColor="#000066";
      theStyle.color="#FFFFFF";
		}
	}
}

// Unhighlight an item in the case menu.
function unHighlightMenuItem() {
  if ((event.clientX > 0) && (event.clientY > 0) && (event.clientX < document.body.offsetWidth) && (event.clientY<document.body.offsetHeight)) {
		if (event.srcElement.getAttribute("menuFunction") != null) {
		  var theStyle = event.srcElement.style;
      theStyle.backgroundColor="#E0E0E0";
      theStyle.color="#000000";
		}
	}
}
	       
function openQuerySummary(txType) {
  window.open(
  	'/BIGR/library/viewSummary.do?txType='+txType, 
  	'QuerySummary', 
    'scrollbars=yes,resizable=yes,status=yes,width=450,height=600');
}
	
function openRnaInfo(rnaVialId) {
  window.open('/BIGR/library/viewRnaPvInfo.do?rnaVialId=' + rnaVialId,
  	'_blank',
 	  'scrollbars=yes,resizable=yes,status=yes,width=750,height=750');
}
 
function doClipboardActionOnClick() {
    // Check if link is enable.
	if (!isLinkEnabled()) {
		return false;
	}

	var eSrc = event.srcElement;
	if (eSrc.clipboardMenu != null) {

		// The user clicked an element with a clipboard menu (i.e. the
		// clipboard action), so show the clipboard menu.
		_clipboardMenuSampleIds = eSrc.sampleIds;
		_clipboardMenuSectionId = eSrc.sectionId;
		_clipboardMenu = document.all[eSrc.clipboardMenu];

	    // Note that the following two hardcoded values control
    	// where the clipboard menu popup displays.  If more elements
    	// are added to the menu, this may have to be changed, or
    	// a more dynamic way of calculating the position implemented.
		var offParent = _clipboardMenu.offsetParent;
		var menuWidth = _clipboardMenu.offsetWidth;
		var menuHeight = _clipboardMenu.offsetHeight;
		var newClientX = event.clientX - 30;
		var newClientY = event.clientY - 30;
  	if (newClientX + menuWidth > offParent.clientWidth) {
 	  	newClientX = offParent.clientWidth - menuWidth;
    }
    if (newClientX < 0) newClientX = 0;
		if (newClientY + menuHeight > offParent.clientHeight) {
			newClientY = offParent.clientHeight - menuHeight;
		}
    if (newClientY < 0) newClientY = 0;
		_clipboardMenu.style.left = newClientX + offParent.scrollLeft;
		_clipboardMenu.style.top = newClientY + offParent.scrollTop;
		_clipboardMenu.style.visibility = 'visible';
		_clipboardMenu.style.zIndex = 1000;
		_clipboardMenu.setCapture();
		event.returnValue = false;
	}
}

function onClickClipboardMenu() {
	// If the clipboardMenu is showing, it has the mouse capture.
	// If the user clicked on one of the menu items we'll run it,
	// and in all cases we'll close the clipboard menu.
	//
	if (_clipboardMenu != null) {
		var eSrc = event.srcElement;
		
		// If we're over a clipboardMenu menu item, then perform its function.
		if (eSrc.menuFunction != null) {
			eval(eSrc.menuFunction);
			event.returnValue = false;
		}
		
		// Always hide the current case menu if one is being shown.
		unHighlightMenuItem();
		_clipboardMenu.style.visibility = 'hidden';
		_clipboardMenu.releaseCapture();
	}
}

//---------- Begin SampleCount class ----------
//
// The SampleCount class represents counts of samples in various 
// categories (e.g. matching a sample selection query, selected, 
// on hold), organized by sample type.
//
function SampleCount(countCategory) {

  //--- Public methods
  this.getCount = getCount;
  this.setCount = setCount;
  this.incrementCount = incrementCount;
  this.decrementCount = decrementCount;
  this.getTotal = getTotal;
	this.setTotalElementId = setTotalElementId
	this.updateTotalDisplay = updateTotalDisplay
	this.getCountsBySampleTypeHtml = getCountsBySampleTypeHtml;
	
  //--- Private member variables
  var _self = this;
  var _category = null;			// the category of counts being held
  var _total = null;				// the total number of samples of all types
  var _countByType = null		// the count of samples by sample type
  var _types = null					// the list of sample types that have a count
  var _totalElementId = null;	// the unqiue identifier of the element
  														// that displays the total

  // Call the initialization for this object.
  initialize(countCategory);

  //--- Private methods

  // Initializes this object.
  function initialize(countCategory) {
  	_category = countCategory;
	  _total = 0;
  	_countByType = new Array();
  	_types = new Array();
	}
	
	// Returns the count for the specified sample type.
	function getCount(sampleType) {
		return _countByType[sampleType];
	}

	// Sets the specified count for the specified sample type.  The 
	// sampleType argument should be one of the sample type CUIs.
	function setCount(sampleType, count) {
		_countByType[sampleType] = count;
		addSampleType(sampleType);
		_total += count;
	}

	// Increments the specified count for the specified sample type.  The 
	// sampleType argument should be one of the sample type CUIs.
  function incrementCount(sampleType) {
  	var count = _countByType[sampleType];
  	if (count == null) {
  		count = 1;
  		_total++;
  	}
  	else {
  		count++;
  		_total++;
  	}
  	_countByType[sampleType] = count;
		addSampleType(sampleType);
  }

	// Decrements the specified count for the specified sample type.  The 
	// sampleType argument should be one of the sample type CUIs.
  function decrementCount(sampleType) {
  	var count = _countByType[sampleType];
  	if (count == null) {
  		count = 0;
  	}
  	else {
  		count--;
  		_total--;
  	}
  	_countByType[sampleType] = count;
		addSampleType(sampleType);
  }

	// Adds the sample type to the list if it is not already there.
	function addSampleType(sampleType) {
		for (var i = 0; i < _types.length; i++) {
			if (_types[i] == sampleType) {
				return
			}
		}
		_types[_types.length] = sampleType;
	}

	// Returns the total across all sample types.
	function getTotal() {
		return _total;
	}

	// Sets the unique identifier of the element that holds the total.
	function setTotalElementId(elementId) {
  	_totalElementId = elementId;
	}

	// Updates the display of the total.  The name/id of the HTML element
	// that holds the total must have been previously set via a call to
	// setTotalElementId.  The entire contents of the element will be 
	// replaced with the current total.  If a valid element id was not
	// previously set, no update will occur.
	function updateTotalDisplay(totalElementId) {
		var element = document.all[_totalElementId];
		if (element != null) {
			element.innerHTML = getTotal();
		}
	}
	
	function getCountsBySampleTypeHtml() {

		var html = '<table cellspacing="0" cellpadding="0" border="0" class="libraryTotalPopup">';
		html += '<tr><td colspan="2" class="libraryTotalHeader">';
		html += _category;
		html += '</td></tr>';
		
		var anyCounts = false;
		
		for (var i = 0; i < _types.length; i++) {
			var sampleType = _types[i];
			var count = getCount(sampleType);
			if (count > 0) {
			    anyCounts = true;
				html += '<tr><td>';
				html += bigrConceptsCache.SINGLETON.getConcept(sampleType).getDescription();
				html += '</td><td align="right">';			
				html += getCount(sampleType);
				html += '</td></tr>';			
			}
		}
		
		if (!anyCounts) {
		  html += '<tr><td>';
		  html += 'None';
		  html += '</td></tr>';			
		}
		
		html += '</table>';
		return html;
	}

} //---------- End SampleCount class ----------

// Create variables to hold each category of count here so they'll be 
// globally available, and instantiate a SampleCount for each.
var _matchingCounts = new SampleCount("Matching");
var _selectedCounts = new SampleCount("Selected");
var _onHoldCounts = new SampleCount("On Hold");
