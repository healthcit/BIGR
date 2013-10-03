// Save prior height to avoid repositioning DIVs if height doesn't change.
var priorResizeHeight = 0;  
  
function positionItems() {
  var contentHeight;
  var buttonsDiv = document.all["div.buttons"];
  var contentDiv = null;
  var allPages = $$('.kcElementsContainer');
  if (allPages && allPages.length > 0) {
	for (i = 0; i < allPages.length; i++) {
	  var elements = allPages[i];
	  if (elements.visible()) {
	    contentDiv = elements;
	  }
	}
  }
  if (!buttonsDiv && !contentDiv) {
    return;
  }

  if (buttonsDiv == null) {
    contentHeight = document.body.clientHeight - 10 - contentDiv.offsetTop;
  }
  else {
//    contentHeight = document.body.clientHeight - buttonsDiv.offsetHeight - contentDiv.offsetTop;
    var bodyDim = $(document.body).getDimensions();
    var buttonsDim = $(buttonsDiv).getDimensions();
    var contentTop = contentDiv.offsetTop;
//    var contentOffset = $(contentDiv).cumulativeOffset();
    contentHeight = bodyDim.height - buttonsDim.height - contentTop;
    
//	buttonsDiv.style.setExpression('pixelTop', contentDiv.offsetTop + contentDiv.offsetHeight);
//	buttonsDiv.style.top = contentDiv.offsetTop + contentHeight + "px";
//	buttonsDiv.style.top = contentDiv.offsetTop + contentDiv.offsetHeight + "px";
	buttonsDiv.style.top = contentTop + contentHeight + "px";
  }
//  contentDiv.style.setExpression('pixelHeight', Math.max(200, contentHeight));
//  contentDiv.style.height = Math.max(200, contentHeight) + "px";
  contentDiv.style.height = Math.max(200, contentHeight) + "px";
  document.recalc(true);
}

// Reposition items when the page is resized.
function onResizePage() {
  var currentHeight = document.body.clientHeight;
  if (currentHeight == priorResizeHeight) return;
  priorResizeHeight = currentHeight;
  positionItems();
}

// Reposition items when the page is resized.
//window.onresize = onResizePage;

// Flag used to warn the user about navigating away from the page if there
// are any changes.
var isWarnOnNavigate = true;

function confirmNavigate() {
  if ((isWarnOnNavigate) && (GSBIO.kc.data.FormInstances.getFormInstance().isChanged())) {
    enableActionButtons();
    event.returnValue = "You have made changes that have not been saved.  Are you sure you want to continue?";
  }
}

function validateSubmit() {
  isWarnOnNavigate = false;
  $('form').value = GSBIO.kc.data.FormInstances.getFormInstance().serialize();
  return true;
}

function enableActionButtons() {
  $('saveButton').enable();
  $('cancelButton').enable();
  GSBIO.kc.data.FormInstances.getFormInstance().enableButtons();
}

function disableActionButtons() {
  $('saveButton').disable();
  $('cancelButton').disable();
  GSBIO.kc.data.FormInstances.getFormInstance().disableButtons();
}

function handleSave() {
  disableActionButtons();
  window.setTimeout("handleSave1()", 1);
}

function handleSave1() {
  if (validateSubmit()) {
    document.forms('formInstance').submit();
  }
}

function handleCancel(url) {
  disableActionButtons();
  window.location.href = url;
}