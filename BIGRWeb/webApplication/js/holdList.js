// Copyright 2004 Ardais Corporation.  All rights reserved.

//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//\\ This is a set of functions to be used by the pages that display
//\\ users' inventory item hold lists.  This creates some global
//\\ variables and functions that may conflict with things of the
//\\ same name on non-hold-list pages, so this should *ONLY*
//\\ be included by the hold list pages (currently HoldListSingle.jsp
//\\ and HoldListMulti.jsp).
//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
// A flag that is used to indicate whether we should inform the
// user that they have selected samples that they need to do
// something with before navigating away from the page.
var _isWarnOnNavigate = true;

var _customNavigationWarning = 'You have samples selected but have not removed them from hold.   If you leave this page now, the samples will no longer be selected for removal.  Do you want to continue?';
   
function confirmNavigate() {
  if (needNavagationWarning()) {
    event.returnValue = _customNavigationWarning;
  }
}

function shouldNavigateAway() {
  if (needNavagationWarning()) {
    var standardMsg = '\n\nPress OK to continue, or Cancel to stay on the current page.';
    var msg = _customNavigationWarning + standardMsg;
    if (! confirm(msg)) {
      return false;
    }
  }
  return true;
}

function needNavagationWarning() {
  if (! _isWarnOnNavigate) return false;
  return (_selectedCounts.getTotal() > 0);
}

function onClickPlaceItemsOnClipboard(elementName) {
  setActionButtonEnabling(false);
  setClipboardItems(accumulateClipboardText('', elementName));
  setActionButtonEnabling(true);
}

function accumulateClipboardText(existingText, elementName){ 
  // MR 6856: Use newline to separate clipboard items.
  var itemClip = existingText; 
  var e = document.all[elementName];
  if (e != null) {
    var items = e.value;
    
    if (items != null && items.length > 0) {
      items = items.replace(/,/g,'\n');
      if (itemClip != null && itemClip.length > 0) {
        itemClip += ('\n' + items);
      }
      else {
        itemClip = items;
      }
    }
  }
  
  return itemClip;
}

function accumulateSelectedItemsClipboardText(existingText, elementName) {
    // MR 6856: Use newline to separate clipboard items.
	var itemClip = existingText;  
	var itemsArr = document.all[elementName];
	itemsArr = arrayFor(itemsArr);  // in case there is just one row
	for (var j = 0; j < itemsArr.length; j++) {
		if (itemsArr[j].checked) {
			if (itemClip != "") {
			  itemClip += "\n";				
			}
			itemClip += itemsArr[j].value;
		}
	}			

	return itemClip;
}

function setClipboardItems(itemClip) {
  if (itemClip == '') {
    alert('No items have been placed on the clipboard.');		  
  } else {
    copyToClipboard(itemClip);
    alert('The items have been placed on the clipboard.');
  }
}

function onUnloadPage() {
  if (parent.topFrame) {
    parent.topFrame.closePopup();
  }
}
