// Copyright 2004 Ardais Corporation.  All rights reserved.

// Replace special HTML characters in the supplied string with their
// entity equivalents.
//
function htmlEscape(s) {
  if (s == null) return s;
  return s.replace(/&/g,"&amp;").replace(/'/g, "&#39;").replace(/"/g,"&quot;").replace(/</g,"&lt;").replace(/>/g,"&gt;");
}

function htmlEscapeAndPreserveWhitespace(s) {
  if (s == null) return s;
  return htmlEscape(s).replace(/\n/g,"<br>").replace(/ /g,"&nbsp;");
}

// Escapes characters in the input string so that the string can be used
// as part of a JavaScript string literal within the attribute value of
// an XML/HTML tag's attribute (such as an <code>onclick</code> attribute).
//
function jsEscapeInXMLAttr(s) {
  if (s == null) return s;
  return s.replace(/\\/g,"\\\\").replace(/\n/g,"\\n").replace(/\t/g,"\\t").replace(/\r/g,"\\r").replace(/\x08/g,"\\b").replace(/\f/g,"\\f").replace(/&/g,"&amp;").replace(/"/g,"\\&quot;").replace(/'/g, "\\&#39;").replace(/</g,"&lt;").replace(/>/g,"&gt;");
}

// Escapes characters in the input string so that the string can be used
// as part of a JavaScript string literal within an HTML <script> tag.
//
function jsEscapeInScriptTag(s) {
  if (s == null) return s;
  return s.replace(/\\/g,"\\\\").replace(/\n/g,"\\n").replace(/\t/g,"\\t").replace(/\r/g,"\\r").replace(/\x08/g,"\\b").replace(/\f/g,"\\f").replace(/\//g,"\\/").replace(/"/g,"\\\"").replace(/'/g, "\\\'");
}

// Used by the diagnosisWithOther/tissueWithOther/procedureWithOther tag
//Enables/disables "Other Text"  INPUT field for otherCode
function showOtherForList(selVal, otherName, otherCode){
 
  if(document.forms[0].elements[otherName]== null) return false;
  var e = document.forms[0].elements[otherName];
	if(selVal == otherCode)	{
    if (e.value == 'N/A') {
		  e.value = ""; 	
		}
		e.disabled = false;
		e.focus(); 
  }
  else {
    e.value = "N/A"; 	
		e.disabled = true;
	}
}

// Opens a modal popup window that shows the specified URL.
// Generally used to show a hierarchy and then select the 
// corresponding element in the specified list.  
function showHierarchy(listName, URL) {
  var list = document.forms[0].elements[listName];
  var currentVal = list.value;
  var newVal = window.showModalDialog(URL, "Diagpage", "diaglogWidth:350px;dialogHeight:450px;dialogTop:0;dialogLeft:0;help:off");
  if ((newVal != null) && (newVal != currentVal)) {
    // restore list options
    restoreListOptionsOnly(list);
    list.value = newVal;
    if (list.onchange != null) {
      (eval(list.onchange)).call(list);
    }
	}			
}

// Opens a modal popup window that shows the specified URL.
// Generally used to show a hierarchy and then select the 
// corresponding element in the specified list when there
// is associated search functionality.  
function showHierarchyWithSearch(listName, URL, searchInputName) {
  var list = document.forms[0].elements[listName];
  var currentVal = list.value;
  var newVal = window.showModalDialog(URL, "Diagpage", "diaglogWidth:350px;dialogHeight:450px;dialogTop:0;dialogLeft:0;help:off");
  if ((newVal != null) && (newVal != currentVal)) {
    restoreListOptions(document.all[searchInputName], list);
    list.value = newVal;
    if (list.onchange != null) {
      (eval(list.onchange)).call(list);
    }
	}			
}

// function to replace "." with replaceString
function replaceDot(str, replaceString) {
  strWReplacedDot = str;
  while (strWReplacedDot.indexOf(".") != -1) {
    strWReplacedDot = strWReplacedDot.replace(".", replaceString);
  }
  return strWReplacedDot;
}

//Function to trim any string
function ltrim(str) {
  re=/^ +/;
  var newstr=str.replace(re, "");
  return(newstr);
}
function rtrim(str) {
  re=/ +$/;
  var newstr=str.replace(re, "");
  return(newstr);
}
function trim(str) {
	if (str == null) return null;
  var newstr=ltrim(str);
  return(rtrim(newstr));
}

function isEmpty(str) {
	var s = trim(str);
	return (s == null) || (s == "");
}

//For text search on D/T/P lists
var masterListArray = new Array();

// store the original values of a select control, if that hasn't already been done
function storeListOptions(oSel) {
    if (masterListArray[oSel.name] == null) {
        var listOptions = new Array();  
        for (var i = 0; i < oSel.length; i++) {
            var oSelOpt = oSel.options[i];
            var opt = new Object();
            opt.text = oSelOpt.text;
            opt.value = oSelOpt.value;
            opt.defaultSelected = oSelOpt.defaultSelected;
            opt.selected = oSelOpt.selected;
            listOptions[i] = opt;
        }
        masterListArray[oSel.name] = listOptions;
    }
}
 
//This function searches for characters in str, with pattern "pattern".
function isValidChars(pattern, str) {
	 var r = new RegExp(pattern);
	 var blnResult = true;
	 var bool;
	   
   	for (i = 0;i<str.length && blnResult == true; i++) {
      	strChar = str.charAt(i);
      	bool = r.test(strChar);
       	if(!bool) { 
	       blnResult = false;
	       break;
	   	}      
     }     
     return blnResult;
}
 
// eliminate any choices from a select control that don't contain a 
// particular string
function findListOptions(oInput, oSel) {
    //alert("I am in Entering findListOptions for " + oSel.name + ".");
	if (!isValidChars('[A-Za-z0-9_ ;]', oInput.value)) {

		oInput.select();
		oInput.focus();
		return false;
 	}

	storeListOptions(oSel);
  oInput.value = trim(oInput.value);
  if (oInput.value.length > 0) {
      
    var rs = oInput.value.split(";");
    oSel.length = 0;
    //SWP1113
    for(var j=0; j <rs.length; j++) {
    
    var input =trim(rs[j]);
    if(input =="") continue;
      
	var r = new RegExp(input,"i");
	
    var originalListValues = null;
    
    if(j == 0)
    originalListValues = masterListArray[oSel.name];
    else {
    
    //construct another orig values and re-loop to narrow down the search entries
    originalListValues = new Array();
    
    for (var k=0; k< oSel.length; k++) {
    originalListValues[k] = oSel[k];  
   
    }
   
    oSel.length = 0;
    }  
    
     
    
       
	  for (var i = 0; i < originalListValues.length; i++) {   
	    var origVal = originalListValues[i];
	 
  		if(r.test(originalListValues[i].text)) {
  		
	      oSel.options[oSel.length] = new Option(
	         origVal.text,
    	     origVal.value,
			     origVal.defaultSelected,
			     origVal.selected
			    );
		  }
		/*  else if (origVal.value == "" && j == 0) {
		 
		      oSel.options[oSel.length] = new Option(
		         origVal.text,
	    	     origVal.value,
				     origVal.defaultSelected,
				     origVal.selected
				    );
		  }*/
	   }
	   
	     
	}  
	  
	
	  if (oSel.length == 0) {
		  oSel.options[0] = new Option('No options match your criteria', '');
		  oSel.selectedIndex = 0;
	  }
	  else if ((oSel.length == 1)) {
	  	  if (oSel.options[0].value == "") {
	  	      oSel.options[0].text = "No options match your criteria";
	  	  }
	  }
    
  }	  
}

// restore the original values of this select control
function restoreListOptions(oInput, oSel) {
	oInput.value = "";
  restoreListOptionsOnly(oSel);
}

// restore the original values of this select control
function restoreListOptionsOnly(oSel) {
  var originalListValues = masterListArray[oSel.name];
  if (originalListValues != null) {
    oSel.length = 0;
	  for (var i = 0; i < originalListValues.length; i++) { 
	    var origVal = originalListValues[i];
      oSel.options[oSel.length] = new Option(
         origVal.text,
     		 origVal.value,
     		 origVal.defaultSelected,
     		 origVal.selected
         );	
    }											   
  }
}

// Used by the BIGR selectList tag to populate a child list
// when a new value is selected in a parent list. If the child list
// supports the text search feature, corresponding controls will be 
// displayed or hidden depending on the value selected for the parent 
// list
function populateChildList(childProperty, parentProperty, parentValue) {
  var selectList = eval(childProperty + 'LegalValues[\"' + parentProperty + '.' + parentValue + '\"];');
  var e = document.all[childProperty + 'Div'];
  var childSearchRefreshDiv = document.all[childProperty + 'SearchRefreshDiv'];
  var childSearchText =  document.all["search" + childProperty];
  if (selectList == null) { 
    e.innerHTML = "";
    if (childSearchRefreshDiv != null) {
    	childSearchRefreshDiv.style.display = "none";
    }
    if (childSearchText != null) {
    	childSearchText.value = "";
    }
  }
  else {
    e.innerHTML = selectList;
    if (childSearchRefreshDiv != null) {
    	childSearchRefreshDiv.style.display = "inline";
    }
    var child = document.all[childProperty];
    if (child.onchange != null) {
      (eval(child.onchange)).call(child);
    }
  }
}

// Enforces a maximum length for a TEXTAREA.  Assign this 
// event handler to the TEXTAREA's onkeyup event.
function maxTextarea(maxlength) { 
	var textarea = event.srcElement;
  if (maxlength != null) {
    if (textarea.value.length > maxlength) { 
      textarea.value = textarea.value.substring(0, maxlength);
      alert("The maximum number of characters you can enter is " + maxlength);
      return false;
    }
  }
}

// Returns true if the is is a valid Ardais ID; false otherwise.
function isValidArdaisId(id) {
  return (id.search(/(^(AI|AU|AX)(\d){10}$)/) == 0);
} 

//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//\\  Another Ardais id validator, to be used in
//\\ conjunction with ValidateIds functionality
//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
function isValidArdaisIdPrefix(id, isImported) {
  if (isImported == null) isImported = false;
  if (isImported) {
	return (id.search(/^(AX|ax|Ax|aX)\d+$/) == 0);
  }
  else {
	return (id.search(/^(AI|ai|Ai|aI|AU|au|Au|aU)\d+$/) == 0);
  }
}
 
function isValidCaseIdPrefix(id, isImported) {
  if (isImported == null) isImported = false;
  if (isImported) {
	return (id.search(/^(CX|cx|Cx|cX)\d+$/) == 0);
  }
  else {
	return (id.search(/^(CI|ci|Ci|cI|CU|cu|Cu|cU)\d+$/) == 0);
  }
} 
 
function isValidSampleIdPrefix(id, isImported) {
  if (isImported == null) isImported = false;
  if (isImported) {
    // Accept SX, case-insensitive.
	  return (id.search(/^([Ss][Xx])[0-9A-Fa-f]+$/) == 0);
  }
  else {
    // Accept FR/PA, case-insensitive.
	  return (id.search(/^(([Ff][Rr])|([Pp][Aa]))[0-9A-Fa-f]+$/) == 0);
  }
} 

function addItemsFromClipboard(selectList, re) {
  var added = false;
  var clipboard = window.clipboardData.getData("Text");
  if (clipboard != null) {
    var tokensRe = /\s*,\s*|\s+/;
    var items = clipboard.split(tokensRe);
    if (items.length > 0) {
      var options = selectList.options;
      var len = selectList.length;
      var list = "";
      for (var i = 0; i < len; i++) {
        list += "," + options[i].value;
      }
      for (i = 0; i < items.length; i++) {
        var item = items[i];
        if ((item.search(re) != -1) && (list.indexOf(item) == -1)) {
          options[len++] = new Option(item, item);
          list += "," + item;
          added = true;
        }
      }
    }
  }
  return added;
}

function addCasesFromClipboard(sampleList) {
  var re = /(^(CU|CI)(\d){10}$)/;
  var added = addItemsFromClipboard(sampleList, re);
  if (!added) {
    alert('No new valid case IDs were present in the clipboard');
  }
}

// Open the LIMS PV report window.  The first argument must be
// either a sample id or a pathology evaluation id (PV...).  The
// second argument should be true if you want to show the image report.
//
function showPvRpt(id, showImageReport) {
  var isPEid = (id.indexOf("PV") == 0);
  var url = "/BIGR/lims/limsViewPvReport.do?"
  if (isPEid) {
    url = url + "peId=";
  }
  else {
    url = url + "sampleId=";
  }
  url = url + id;
  if (showImageReport) {
    url = url + "&showImageReport=true";
  }
  
  var w = window.open(url,
    '_blank',
    'scrollbars=yes, status=yes, resizable=yes,width=1000,height=700, top=5, left=5');
  w.focus();
}

// Generic function to substitute text of any page or present it in a popup.
// Note popup is a boolean.
function showText(text, popup) {
  var html = escape(text);
  var w;
  if (popup) 
    w = window.open('', '_blank',
    	'scrollbars=yes,resizable=yes,status=yes,width=760,height=700,top=0');
  else 
    w = window; //the current window
  w.document.open();
  w.document.write('<html><head><title></title></head><body>');
  w.document.write(text);
  w.document.write('</body></html>');  
  w.document.close();
  w.focus();  
}

// Go to the ICP page for the specified id.  popup is a boolean.
function toIcp(icpId, popup) {
  toIcp(icpId, popup, 'N');
}

// Go to the ICP page for the specified id.  popup is a boolean, printerFriendly is
// Y if the page should be displayed in printer-friendly form.
function toIcp(icpId, popup, printerFriendly) {
  if (icpId == null || icpId.length == 0) return;
  
  var theURL = '/BIGR/iltds/Dispatch?op=IcpQuery&id=' + escape(icpId);
  if ('Y' == printerFriendly) {
    theURL = theURL + '&printerFriendly=Y';
  }
  
  if (popup) {
    var features = 'scrollbars=yes,resizable=yes,status=yes,width=760,height=700,top=0';
    if ('Y' == printerFriendly) {
      features = features + ',menubar=1';
    }
    var w = window.open(theURL, printerFriendly, features);
    w.focus();
  }
  else {
    window.location.href = theURL;
  }
}

// Place the specified text on the clipboard.
//
function copyToClipboard(theText) {
  var clipText = theText;
  if (clipText == null) clipText = "";

  // Get the text onto the clipboard by creating a temporary hidden
  // textarea element, putting the text into it, and creating a TextRange
  // that represents the element's contents.  Use the execCommand
  // on the TextRange to copy the text to the clipboard.  Finally,
  // remove the temporary element from the document.  This is
  // all to work around problems with the clipboardData.saveData and
  // .clearData methods that were causing problems as described in
  // MR 5321.
  //
  var eHidden = document.createElement('<textarea style="display: none;"></textarea>');
  eHidden.value = clipText;
  document.body.appendChild(eHidden); // insert temporary node
  var tRange = eHidden.createTextRange();
  tRange.execCommand("Copy");
  eHidden.removeNode(true); // clean up
}

// Return the text that is currently on the clipboard, if any.
// Otherwise return null.
//
function getClipboardText() {
  return window.clipboardData.getData("Text");
}

// return an array.  If x is an array, return it.  If x is not an array
// create an array with just x in it.  Test for the "length" property
// to see if it is an array.  Explicitly exclude Strings, which also have length.
function arrayFor(x) {
		var y=x;
		if (x == null) {
			y = new Array(0);
		}
		else if (typeof x=='string' || !x.length) {
			y = new Array(1);
			y[0] = x;
		}
		return y;
}

// Enable/disable input element(s) with the specified name on the
// specified form.  isEnabled must be true or false.  This has been
// tested for input type="button" but needs to be tested with other
// input types before using it with them.
//
// It has also been tested with link ("<a>") elements.  However,
// there's a twist to getting it to work right for links due to
// an Internet Explorer bug.  See the isLinkEnabled function below
// for details.
//
function setInputEnabled(formElement, inputName, isEnabled) {
  var items = formElement[inputName];
  var isDisabled = (! isEnabled);
  
  if (items == null) return;
  if (items.length == null) { // 1 item, not an array
    items.disabled = isDisabled;
  }
  else { // >1 item, an array
    for (i = 0; i < items.length; i++) {
      items[i].disabled = isDisabled;
    }
  }
}

// Due to a bug in Internet Explorer, setting a link element ("<a>")
// to be disabled just turns it grey, it doesn't actually prevent
// the user from clicking the link (see Microsoft Knowledge Base
// Article 253579).  The workaround is to include an onclick event
// handler on your link that returns false when the link is disabled.
// This function is intended to be used as that onclick handler,
// like this:
//   <a href="whatever" onclick="return(isLinkEnabled());">something</a>
//
function isLinkEnabled() {
  var e = event.srcElement;
  return (!e.disabled);
}

function MM_goToURL() { //v3.0
  var i, args=MM_goToURL.arguments; document.MM_returnValue = false;
  for (i=0; i<(args.length-1); i+=2) eval(args[i]+".location='"+args[i+1]+"'");
}

// I ran across an odd inconsistency.  Suppose an element
// with name="XYZ" is a SELECT tag containing a bunch of
// OPTION elements.  Then document.all['XYZ'] returns a collection
// of the options rather than the select element itself.  Odd.
// So here, I check to see that the name of the item returned
// is the name I was looking for, and if not I return the parent
// of that items instead.
//
// The upshot of this is that when you have an element name
// and you want to get the corresponding element object,
// call, for example,
// 
//   getFirstWithName(document.all, ename)
//
// instead of doing just document.all[ename].
//
// When the named item is a radio button, this returns just
// one of the buttons, not the collection of all of them.
//
function getFirstWithName(coll, ename) {
  var items = coll[ename];
  var item;

  if (items == null) return null;
  if (null == items.length) {
    item = items;
  }
  else {
    item = items[0];
  }

  if (ename != item.name) item = item.parentElement;

  return item;
}

// Given an input element c (or SELECT or TEXTAREA),
// this returns the current value for that "control".
// The only tricky case is radio buttons, where the value
// of the logical control isn't that same as the value of
// the input element (it is the value of the radio button
// that has the same name as the specified input element
// and whose "checked" attribute is true).
//
function getControlValue(c) {
  var value = '';

  if ('radio' == c.type) {
    var i;
    var options = c.form.elements[c.name];

    // When no options are checked, return the empty string.

    if (null == options.length) { // Only one radio button with this name
      if (options.checked) {
        value = options.value;
      }
    }
    else {
      for (i = 0; i < options.length; i++) {
        if (options[i].checked) {
          value = options[i].value;
          break;
        }
      }
    }
  }
  else if ('checkbox' == c.type) {
  // When checkbox is not checked, return the empty string.
    if (c.checked) {
      value = c.value;
    }
    else {
      value = '';
    }
  }
  else {
    value = c.value;
  }

  if (null == value) value = '';
  return value;
}

// Given an input element c (or SELECT or TEXTAREA),
// this returns the default value for that "control".
//
function getControlDefaultValue(c) {
  var value = '';

  if ('radio' == c.type) {
    var i;
    var options = c.form.elements[c.name];

    // When no options are defaultChecked, return the empty string.

    if (null == options.length) { // Only one radio button with this name
      if (options.defaultChecked) {
        value = options.value;
      }
    }
    else {
      for (i = 0; i < options.length; i++) {
        if (options[i].defaultChecked) {
          value = options[i].value;
          break;
        }
      }
    }
  }
  else if ('select-one' == c.type) {
    var i;
    var options = c.options;

    // When no options are defaultSelected, return the empty string.

    for (i = 0; i < options.length; i++) {
      if (options[i].defaultSelected) {
        value = options[i].value;
        break;
      }
    }
  }
  else {
    value = c.defaultValue;
  }

  if (null == value) value = '';
  return value;
}

// Returns true if the control with the name or id is a collection.
// Returns false if the control with the name does not exist or it is
// a single element.
function isControlCollection(controlName) {
  var control = document.all[controlName];
  if ((control == null) || (control.length == null)) {
  	return false;
  }
  else {
  	return true;
  }
}

function disableControlCollection(controlName, isDisabled) {
  if (isControlCollection(controlName)) {
    var control = document.all[controlName];
    for (var i=0; i<control.length; i++) {
      control[i].disabled = isDisabled;
    }
  }
}

function disableControlInCollection(controlName, index, isDisabled) {
  if (isControlCollection(controlName)) {
    document.all[controlName][index].disabled = isDisabled;
  }
}

function showBanner(bannerText) {
	if ((window.parent != null) && (window.parent.topFrame != null)) {		
	  window.parent.topFrame.document.all.banner.innerHTML = bannerText;
	}
}

function isInArray(array, value) {
	var inArray = false;
	for (var i=0; i<array.length; i++) {
		if (array[i] == value) {
			return true;
		}	
	}
	return inArray;
}

  // Returns a more descriptive type than the built-in "typeof" 
  // operator.  
  // -----------------------------------------------------------------
  // dltypeof.js
  //  by Peter Belesis. v1.0 040823
  //  Copyright (c) 2004 Peter Belesis. All Rights Reserved.
  //  Originally published and documented at http://www.dhtmlab.com/
  //
	function dltypeof(vExpression) {
    var sTypeOf = typeof vExpression;
    if (sTypeOf == "function") {
      var sFunction = vExpression.toString();
      if((/^\/.*\/$/).test(sFunction)) {
				return "regexp";
      }
      else if((/^\[object.*\]$/i).test(sFunction)) {
        sTypeOf = "object"
      }
    }
    if (sTypeOf != "object") {
      return sTypeOf;
    }

    switch (vExpression) {
      case null:
        return "null";
      case window:
        return "window";
      case window.event:
        return "event";
    }

    if (window.event && (event.type == vExpression.type)) {
      return "event";
    }

    var fConstructor = vExpression.constructor;
    if (fConstructor != null) {
      switch (fConstructor) {
        case Array:
          sTypeOf = "array";
          break;
        case Date:
          return "date";
        case RegExp:
          return "regexp";
        case Object:
          sTypeOf = "jsobject";
          break;
        case ReferenceError:
          return "error";
        default:
          var sConstructor = fConstructor.toString();
          var aMatch = sConstructor.match(/\s*function (.*)\(/);
          if (aMatch != null) {
            return aMatch[1];
          }
      }
    }

    var nNodeType = vExpression.nodeType;
    if (nNodeType != null) {
      switch(nNodeType)  {
        case 1:
          if (vExpression.item == null) {
            return "domelement";
          }
          break;
        case 3:
          return "textnode";
      }
    }

    if (vExpression.toString != null) {
      var sExpression = vExpression.toString();
      var aMatch = sExpression.match(/^\[object (.*)\]$/i);
      if (aMatch != null) {
        var sMatch = aMatch[1];
        switch (sMatch.toLowerCase()) {
          case "event":
            return "event";
          case "math":
            return "math";
          case "error":
            return "error";
          case "mimetypearray":
            return "mimetypecollection";
          case "pluginarray":
            return "plugincollection";
          case "windowcollection":
            return "window";
          case "nodelist":
          case "htmlcollection":
          case "elementarray":
          return "domcollection";
        }
      }
    }

    if (vExpression.moveToBookmark && vExpression.moveToElementText) {
      return "textrange";
    }
    else if (vExpression.callee != null) {
      return "arguments";
    }
    else if (vExpression.item != null) {
      return "domcollection";
    }
    
    return sTypeOf;
  }

// Checks that the specified object is of the specified type, and
// returns true if it is; false otherwise.
//
// @param  obj  the object
// @param  type  the type, as a string
// @return  true if the specified object is of the specified type; 
//          false otherwise.
function checkObjectType(obj, type) {
	return (dltypeof(obj) == type);
}

// Return a string stripping off all characters except alphanumeric characters.
// 
// @param s String to parse
// @return String with alphanumeric characters only.
//
  function makeOnlyAlphaNumeric(str) {
    var result = "";
    
    for (i=0; i < str.length; i++) {
      var c = str.charCodeAt(i);
      var s = str.charAt(i);
      if ((c >= 65 && c<=90) || (c >= 97 && c<=122) || (c >= 48 && c<=57)) {
        result += s;
      }
    }
    return result;
  }

// Provides similar functionality to the Java method pack() in class
// com.ardais.bigr.javabeans.StringList.  The string returned by this
// function can be unpacked by the unpack() method in StringList.
// Input is an array of Strings.
function pack(stringArray) {
	var s = "";
  if (stringArray != null) {
	  if (stringArray.length == null) {
      var escaped = escapeForPack(stringArray[i]);
      if (escaped != null) {
	      s += escaped;
      }
	  }
	  else {
	    var first = true;
	    for (var i = 0; i < stringArray.length; i++) {
	      if (first) {
	        first = false;
	      }
	      else {
	        s += ", ";
	      }
	      var escaped = escapeForPack(stringArray[i]);
	      if (escaped != null) {
		      s += escaped;
	      }
	    }
	  }
  }
  return s;
}

// Used by the pack function in this file.  Provides similar 
// functionality to the Java method escape() in class
// com.ardais.bigr.javabeans.StringList.
function escapeForPack(s) {
  if (s == null) {
    return;
  }
  return s.replace(/\\/g,"\\\\").replace(/,\s/g,",\\ ");
}

//this method automatically selects the single non-"Select" choice in a drop down.  It assumes
//that if there are "length" options in the drop-down that the "length-1" of them should be selected.
function selectSingleDropDownChoice(dropdown, length) {
  if (dropdown.length == length) {
    dropdown[length-1].selected = true;
	if (dropdown.fireEvent)	{
		dropdown.fireEvent('onchange');
	}
  }
}

function isValidDate(value) {
  var validFullFormat = /^\d{2}\/\d{2}\/\d{4}$/;
  var validSingleDigitMonthFormat = /^\d{1}\/\d{2}\/\d{4}$/;
  var validSingleDigitDayFormat = /^\d{2}\/\d{1}\/\d{4}$/;
  var validSingleDigitMonthAndDayFormat = /^\d{1}\/\d{1}\/\d{4}$/;
  var returnValue = false;
  if (!isEmpty(value) && 
       (validFullFormat.test(value) ||
        validSingleDigitMonthFormat.test(value) ||
        validSingleDigitDayFormat.test(value) ||
        validSingleDigitMonthAndDayFormat.test(value))
     ) {
    var splitResult = value.split("/");
    var month = splitResult[0];
    var day = splitResult[1];
    var year = splitResult[2];
    var date = new Date(year, month-1, day);
    returnValue = ((date.getMonth()+1 == month) &&
      (date.getDate() == day) &&
      (date.getFullYear() == year));
  }
  return returnValue;
}

function isValidInteger(value) {
  var returnValue = !isEmpty(value) && !isNaN(parseInt(value));
  return returnValue;
}

function isValidFloat(value) {
  var returnValue = !isEmpty(value) && !isNaN(parseFloat(value));
  return returnValue;
}

function RealTypeOf(v) {
  if (typeof(v) == "object") {
    if (v === null) return "null";
    if (v.constructor == (new Array).constructor) return "array";
    if (v.constructor == (new Date).constructor) return "date";
    if (v.constructor == (new RegExp).constructor) return "regex";
    return "object";
  }
  return typeof(v);
}

function addOperands(operand1, operand2) {
  var returnValue = null;
  //if neither operand is a date, perform numerical addition
  if ((RealTypeOf(operand1) != "date") && (RealTypeOf(operand2) != "date")) {
    returnValue = operand1 + operand2;
  }
  //if one operand is a date, add the other operand to it
  else if ((RealTypeOf(operand1) == "date") && (RealTypeOf(operand2) != "date")) {
    operand1.setDate(operand1.getDate() + operand2);
    returnValue = operand1;
  }
  else if ((RealTypeOf(operand1) != "date") && (RealTypeOf(operand2) == "date")) {
    operand2.setDate(operand2.getDate() + operand1);
    returnValue = operand2;
  }
  return returnValue;
}

function subtractOperands(operand1, operand2) {
  var returnValue = null;
  //if neither operand is a date, perform numerical subtraction
  if ((RealTypeOf(operand1) != "date") && (RealTypeOf(operand2) != "date")) {
    returnValue = operand1 - operand2;
  }
  //if both operands are dates, determine the elapsed time between them (which will
  //be in milliseconds) and convert that to the number of days
  else if ((RealTypeOf(operand1) == "date") && (RealTypeOf(operand2) == "date")) {
    returnValue = ((operand1 - operand2)/(1000*60*60*24));
  }
  //if one operand is a date, subtract the other operand from it
  else if ((RealTypeOf(operand1) == "date") && (RealTypeOf(operand2) != "date")) {
    operand1.setDate(operand1.getDate() - operand2);
    returnValue = operand1;
  }
  else if ((RealTypeOf(operand1) != "date") && (RealTypeOf(operand2) == "date")) {
    operand2.setDate(operand2.getDate() - operand1);
    returnValue = operand2;
  }
  return returnValue;
}

function multiplyOperands(operand1, operand2) {
  var returnValue = operand1 * operand2;
  return returnValue;
}

function divideOperands(operand1, operand2) {
  var returnValue = operand1 / operand2;
  return returnValue;
}

var source;
//for SWP-1114
function selectChoice(select, path, fieldId, txType)
	{   source = fieldId;
		var url = path+'/library/start.do?txType='+txType+'&subActionType=SelectFromDropdown&fieldId='+fieldId;
       
		pars="selectedValue="+ select.value+"&selectedLabel="+ select.options[select.selectedIndex].text;
		
		if(select.value=="") {
		alert("You need to pick up an option to add!");
		return false;
		}
	
	  	var myAjax = new Ajax.Request(
          
			url, 
			{  
				method: 'get', 
				parameters: pars,
			    onComplete: showResponse
			}); 
	    
		
	}
	
	function showResponse(originalRequest)
	{
		//put returned HTML in the textarea
		$(source).innerHTML = originalRequest.responseText;
	}  


