// bigrTextArea.js
//
// A JavaScript object that encapsulates and manipulates an HTML 
// TEXTAREA element.

function BigrTextArea(textAreaId) {
	this.textAreaId = textAreaId;				// the id of the TEXTAREA element
	this.getTextArea = BigrTextAreaGetTextArea;	// returns the enclosed TEXTAREA element
	this.containsText = BigrTextAreaContainsText;	// returns true if the TEXTAREA contains the specified display text (case-insensitive)
	this.clear = BigrTextAreaClear;					// clears the TEXTAREA
	this.enforceMaxLength = BigrTextAreaEnforceMaxLength;	// enforces the max length of the TEXTAREA
	this.maxLength = 0;							// default is no max length
}

function BigrTextAreaGetTextArea() {
  return document.all[this.textAreaId];
}

function BigrTextAreaContainsText(text) {
  if (text == null || trim(text) == "") {
    return false;
  }
  var textAreaElement = this.getTextArea();
  var upperValue = trim(textAreaElement.value.toUpperCase());
  var upperText = trim(text.toUpperCase());
  var loc = upperValue.indexOf(upperText);
  if (loc == -1) {
    return false;
  }
  return true;
}

function BigrTextAreaClear() {
  var textAreaElement = this.getTextArea();
  textAreaElement.value = "";
}

function BigrTextAreaEnforceMaxLength() {
  if (this.maxLength == null || this.maxLength == 0) {
  	return true;
  }
  var value = parseInt(this.maxLength);
  if (isNaN(value) || value < 0) {
  	alert("Invalid maxLength value of " + this.maxLength + " encountered for " + this.getTextArea().name);
  	return false;
  }
  var textAreaElement = this.getTextArea();
  if (textAreaElement.value.length > this.maxLength) { 
    textAreaElement.value = textAreaElement.value.substring(0, this.maxLength);
    alert("The maximum number of characters you can enter is " + this.maxLength);
    return false;
  }
  return true;
}
