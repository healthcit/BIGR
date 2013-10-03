// Copyright 2004 Ardais Corporation.  All rights reserved.

//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//\\  This is a set of functions to be used by setting up a list of 
//\\  elments that are moved from a text area to a select box.
//\\
//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//\\ Static VARS
//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
var ITEMID_LENGTH   = 10;
var RNAID_LENGTH    = 10;
var CASEID_LENGTH   = 10;
var DONORID_LENGTH  = 12;
var ITEM_TYPE       = "ITEM_TYPE";
var CASE_TYPE       = "CASE_TYPE";
var DONOR_TYPE      = "DONOR_TYPE";
var SLIDE_TYPE      = "SLIDE_TYPE";
var SAMPLE_TYPE     = "SAMPLE_TYPE";
var IGNORE_TYPE		= "IGNORE_TYPE";

//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//\\ Functions used for collection lists
//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
function addItemToList(input, list, type, doAlert){
  var inputElem = document.all[input];
  if(inputElem.value.length == 0) {return true;}
  
  var options = document.all[list].options;
  var len = options.length;
  var isFound = false;
  var inputElemValue = inputElem.value;

  for (i = 0; i < len ; i++) {
    if (options[i].value == inputElemValue) {
      isFound = true;
      break;
    }
  }
  if (!isFound){
    var isValid = false;
    isValid = isValidId_Alert(inputElemValue, type, doAlert);

    if(isValid){
      options.add(new Option(inputElemValue, inputElemValue));
      inputElem.value = '';
      inputElem.focus();
    }
    else {
      inputElem.focus();
      return false;
    }
  } 
  else {
    	inputElem.value = '';
    	inputElem.focus();
  }
  return true;
}

//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//\\  function to add item to lists but
//\\   do not do full validation b/c that 
//\\   will be done in ValidateIds class
//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
function addItemToListPrefix(input, list){
  var inputElem = document.all[input];
  if(inputElem.value.length == 0) {return true;}
  
  var options = document.all[list].options;
  var len = options.length;
  var isFound = false;
  var inputElemValue = inputElem.value;

  for ( i = 0; i < len ; i++) {
    if (options[i].value == inputElemValue) {
      isFound = true;
      break;
    }
  }
  if (!isFound){
    options.add(new Option(inputElemValue, inputElemValue));
    inputElem.value = '';
    inputElem.focus();
  } 
  else {
    inputElem.value = '';
    inputElem.focus();
  }
  return true;
}

function removeItemFromList(list){
  var options = document.all[list].options;
  var len = options.length;
  for (i = (len -1); i >= 0; i--){
    if (options[i].selected == true ) {
      options[i] = null;
    }
  }
}

function clearList(list){
  var options = document.all[list].options;
  var len = options.length;
  for (i = (len -1); i >= 0; i--){
    options[i] = null;
  }
}

function selectAllFromList(list){
  var options = document.all[list].options;
  var len = options.length;
  for (i = (len -1); i>=0; i--) {
    options[i].selected = true;
  }
}

function deselectAllFromList(list){
  var options = document.all[list].options;
  var len = options.length;
  for (i = (len -1); i>=0; i--) {
    options[i].selected = false;
  }
}

function addClipboardToList(list, type){
  var added = false;
  var clipboard = window.clipboardData.getData("Text");
  if (clipboard != null) {
    var options = document.all[list].options;
    var re = /\s*,\s*|\s+/;
    var ids = clipboard.split(re);
    var idslen = ids.length;
    for (i = 0; i < idslen; i++) {
      var len = options.length;
      var id = ids[i];
      var isFound = false;
      for (j = 0; j < len ; j++) {
        if (options[j].value == id) {
          isFound = true;
          break;
        }
      }
      if (!isFound){
        if(isValidId_Alert(id, type, false)){
          options.add(new Option(id, id));
          added = true;
        }
      }
    }
  }
  if(!added){
    alert("No new items were added from clipboard");
  }  
}

//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//\\  Check that we have a valid case id
//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
function isValidCaseId_Alert(id, doAlert){
  if(doAlert == null) doAlert = true;
  if (id.length < CASEID_LENGTH) {
    if(doAlert) alert("Invalid Case Id: " + id);
    return false;
  }
  
  // MR 7777 ideally, these should have constants.  for now, we'll
  // put in text that matches ValidateIds so this will hit
  // on searches.  TYPESET_CASE   PREFIX_CASE_IMPORTED 
  //  PREFIX_CASE_UNLINKED PREFIX_CASE_LINKED
  var linkedExpr = /^CI\d{10}$/;
  var unlinkedExpr = /^CU\d{10}$/;
  var importedExpr = /^CX\d{10}$/;
  
  if(linkedExpr.test(id)){
    return true;
  }
  if(unlinkedExpr.test(id)){
    return true;
  }
  if(importedExpr.test(id)){
    return true;
  }
  if(doAlert) alert("Invalid Case Id: " + id);
  return false;
}

//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//\\  Another case id validator, to be used in
//\\ conjunction with ValidateIds functionality
//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
function isValidCaseIdPrefix_Alert(id, doAlert){

  if(doAlert == null) doAlert = true;

  var linkedExpr = /^(CI|ci|Ci|cI)\d+$/;
  var unlinkedExpr = /^(CU|cu|Cu|cU)\d+$/;
  var importedExpr = /^(CX|cx|Cx|cX)\d+$/;
  
  if(linkedExpr.test(id)){
    return true;
  }
  if(unlinkedExpr.test(id)){
    return true;
  }
  if(importedExpr.test(id)){
    return true;
  }
  if(doAlert) alert("Invalid Case Id: " + id);
  return false;
}

//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//\\  Functions to check for the validity of id's in BIGR
//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//\\  Check that we have a valid donor id
//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
function isValidDonorId_Alert(id, doAlert){
  if(doAlert == null) doAlert = true;
  if (id.length < DONORID_LENGTH) {
    if(doAlert) alert("Invalid Donor Id: " + id);
    return false;
  }
  var linkedExpr = /^AI\d{10}$/;
  var unlinkedExpr = /^AU\d{10}$/;
  var importedExpr = /^AX\d{10}$/;
  
  if(linkedExpr.test(id)){
    return true;
  }
  if(unlinkedExpr.test(id)){
    return true;
  }
  if(importedExpr.test(id)){
    return true;
  }
  if(doAlert) alert("Invalid Donor Id: " + id);
  return false;
}


//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//\\  Another donor id validator, to be used in
//\\ conjunction with ValidateIds functionality
//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
function isValidDonorIdPrefix_Alert(id, doAlert){

  if(doAlert == null) doAlert = true;

  var linkedExpr = /^(AI|ai|Ai|aI)\d+$/;
  var unlinkedExpr = /^(AU|au|Au|aU)\d+$/;
  var importedExpr = /^(AX|ax|Ax|aX)\d+$/;
  
  if(linkedExpr.test(id)){
    return true;
  }
  if(unlinkedExpr.test(id)){
    return true;
  }
  if(importedExpr.test(id)){
    return true;
  }
  if(doAlert) alert("Invalid Donor Id: " + id);
  return false;
}



//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//\\  Check that we have a valid sample id
//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
function isValidSampleId_Alert(id, doAlert){
  if(doAlert == null) doAlert = true;
  var valid = (isValidFrozenSampleId_Alert(id, false) || isValidParaffinSampleId_Alert(id, false) || isValidImportedSampleId_Alert(id, false));
  if (!valid && doAlert) alert("Invalid Sample Id: " + id);
  return valid;
}

//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//\\  Check that we have a valid slide id
//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
function isValidSlideId_Alert(id, doAlert){
  if(doAlert == null) doAlert = true;
  var valid = (isValidFrozenSlideId_Alert(id, false) || isValidParaffinSlideId_Alert(id, false) || isValidSLSlideId_Alert(id, false) );
  if (!valid && doAlert) alert("Invalid Slide Id: " + id);
  return valid;
}

//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//\\  Check that we have a valid frozen sample id
//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
function isValidFrozenSampleId_Alert(id, doAlert){
  if(doAlert == null) doAlert = true;
  var frozenExpr = /^FR[0-9A-F]{8}$/;
  var valid = false;
  if(frozenExpr.test(id)){
    valid = true;
  }
  if (!valid && doAlert) alert("Invalid Frozen Sample Id: " + id);
  return valid;
}

//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//\\  Check that we have a valid paraffin sample id
//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
function isValidParaffinSampleId_Alert(id, doAlert){
  if(doAlert == null) doAlert = true;
  var paraffinExpr = /^PA[0-9A-F]{8}$/;
  var valid = false;
  if(paraffinExpr.test(id)){
    valid = true;
  }
  if (!valid && doAlert) alert("Invalid Paraffin Sample Id: " + id);
  return valid;
}
  
//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//\\  Check that we have a valid imported sample id
//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
function isValidImportedSampleId_Alert(id, doAlert){
  if(doAlert == null) doAlert = true;
  var importedExpr = /^SX[0-9A-F]{8}$/;
  var valid = false;
  if(importedExpr.test(id)){
    valid = true;
  }
  if (!valid && doAlert) alert("Invalid Imported Sample Id: " + id);
  return valid;
}

//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//\\  Check that we have a valid frozen slide id
//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
function isValidFrozenSlideId_Alert(id, doAlert){
  if(doAlert == null) doAlert = true;
  var frozenExpr = /^SF[0-9A-F]{8}$/;
  if(frozenExpr.test(id)){
    return true;
  } else {
    return false;
  }
}

//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//\\  Check that we have a valid paraffin slide id
//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
function isValidParaffinSlideId_Alert(id, doAlert){
  if(doAlert == null) doAlert = true;
  var paraffinExpr = /^SP[0-9A-F]{8}$/;
  if(paraffinExpr.test(id)){
    return true;
  } else {
    return false;
  }
}
//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//\\  Check that we have a valid SL slide id
//\\    (all others besides frozen and paraffin)
//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
function isValidSLSlideId_Alert(id, doAlert){
  if(doAlert == null) doAlert = true;
  var SLExpr = /^SL[0-9A-F]{8}$/;
  if(SLExpr.test(id)){
    return true;
  } else {
    return false;
  }
}
//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//\\  Check that we have a valid rna id
//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
function isValidRnaId_Alert(id, doAlert){
  if(doAlert == null) doAlert = true;
  var paraffinExpr = /^RN[0-9A-F]{8}$/;
  if(paraffinExpr.test(id)){
    return true;
  } else {
    return false;
  }
}

//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//\\  Check that we have a valid item id
//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
function isValidItemId_Alert(id, doAlert){
  if(doAlert == null) doAlert = true;
  if (id.length < ITEMID_LENGTH) {
    if(doAlert) alert("Invalid Item Id: " + id);
    return false;
  }
  var isValid = (isValidSampleId_Alert(id, false) || isValidRnaId_Alert(id, false));
  if(!isValid){
    if(doAlert) alert("Invalid Item Id: " + id);
  }
  return isValid;
}

function isValidId_Alert(id, type, doAlert){
  if(doAlert == null) doAlert = true;
  var isValid = false;
  if(type == ITEM_TYPE){
    isValid = isValidItemId_Alert(id, doAlert);
  }
  else if(type == DONOR_TYPE){
    isValid = isValidDonorId_Alert(id, doAlert);
  }
  else if(type == CASE_TYPE){
    isValid = isValidCaseId_Alert(id, doAlert);
  }
  else if(type == SAMPLE_TYPE){
  	isValid = isValidSampleId_Alert(id, doAlert);
  }
  else if(type == SLIDE_TYPE){
  	isValid = isValidSlideId_Alert(id, doAlert);
  }
  else if(type == IGNORE_TYPE){
	var s = trim(id);
	isValid = !((s == null) || (s == ""));
  }
  
  return isValid;
}
