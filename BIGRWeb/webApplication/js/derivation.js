var COPY_TO_CHOICE_ALL = "All";
var COPY_TO_CHOICE_ALL_AFTER_SOURCE = "AllAfterSource";
var COPY_TO_CHOICE_NEXT_N = "NextN";

var controlWithFocus = null;
function keepFocus() {
  document.forms[0][controlWithFocus].focus();
}
  
function handleStartOverRequest() {
  if (confirm("All information entered to this point will be lost. Press Ok to start over, or Cancel to stay on this page.")) {
    window.location.href='/BIGR/iltds/derivation/derivationBatchStart.do';
    return true;
  }
  else {
    return false;
  }
}

function DerivationBatch() {
  this.newDerivation = newDerivation;
  this.getDerivation = getDerivation;
  this.setDerivation = setDerivation;
  this.getNumberOfDerivations = getNumberOfDerivations;
  this.setNumberOfDerivations = setNumberOfDerivations;
  this.incrementNumberOfDerivations = incrementNumberOfDerivations;
  this.getTableId = getTableId;
  this.setTableId = setTableId;
  this.handleEvent = handleEvent;
  this.isMultipleParentsAllowed = isMultipleParentsAllowed;
  this.setMultipleParentsAllowed = setMultipleParentsAllowed;
  this.showDerivativeBarcodes = showDerivativeBarcodes;
  this.showDerivativeCounts = showDerivativeCounts;
  this.isDerivativeGenerated = isDerivativeGenerated;
  this.setDerivativeGenerated = setDerivativeGenerated;
  
  var _numberOfDerivations = 0;
  var _tableId = null;
  var _multipleParentsAllowed = false;
  var _derivations = new Array();
  var _self = this;
  var _derivativeGenerated = false;
  
  function isDerivativeGenerated() {
	return _derivativeGenerated;
  }
  function setDerivativeGenerated(generated) {
	_derivativeGenerated = generated;
  }

  function setMultipleParentsAllowed(trueOrFalse) {
    _multipleParentsAllowed = trueOrFalse;
  }
  
  function isMultipleParentsAllowed() {
    return _multipleParentsAllowed;
  }
  
  function getTableId() {
    return _tableId;
  }
  
  function setTableId(tableId) {
    _tableId = tableId;
  }

  function getDerivation(index) {
  	return _derivations[index];
  }
  
  function setDerivation(derivation) {
	_derivations[_numberOfDerivations] = derivation;
	incrementNumberOfDerivations();
  }

  function getNumberOfDerivations() {
  	return _numberOfDerivations;
  }

  function setNumberOfDerivations(numberOfDerivations) {
  	_numberOfDerivations = numberOfDerivations;
  }

  function incrementNumberOfDerivations() {
  	_numberOfDerivations++;
  }

  function showDerivativeBarcodes() {
    for (var i = 0; i < _numberOfDerivations; i++) {
      _derivations[i].showDerivativeBarcodes();
    }
  }
  
  function showDerivativeCounts() {
    for (var i = 0; i < _numberOfDerivations; i++) {
      _derivations[i].showDerivativeCounts();
    }
  }
  
  function newDerivation() {
	var derivationNumber = getNumberOfDerivations();
	var oTR = document.all[getTableId()].insertRow();
	oTR.style.cssText = "background-color:#FFFFFF;";
	var oTD = oTR.insertCell();
	oTD.innerText = derivationNumber + 1;
	oTD = oTR.insertCell();
	oTD.id = "parentGroup" + derivationNumber;
	oTD = oTR.insertCell();
	oTD.innerHTML = '<img src="/BIGR/images/derivarrow.gif">';
	oTD = oTR.insertCell(); 
	oTD.id = "derivativeGroup" + derivationNumber;
	var html = '<span';
	if (isDerivativeGenerated()) {
	  html += ' style="display:none;"';
	}
	html += '></span>';
	html += '<span';
	if (!isDerivativeGenerated()) {
	  html += ' style="display:none;"';
	}
	html += '><input type="text" size="3" name="dto.derivation[';
	html += derivationNumber;
	html += '].generatedSampleCount"';
    html += ' onfocus="derivationBatch.handleEvent(' + derivationNumber + ', false, 0);"';
	html += '/></span>';
	oTD.innerHTML = html;

	var derivation = new Derivation();
	derivation.setIndex(derivationNumber);
	var pgTdId = "parentGroup" + derivationNumber;
	var dgTdId = "derivativeGroup" + derivationNumber;
	derivation.setDerivationBatch(this);
	derivation.setParentGroupTdId(pgTdId);
	derivation.setDerivativeGroupTdId(dgTdId);
	derivation.addParentSample();
	derivation.addDerivativeSample();
	setDerivation(derivation);		
  }
  
  function handleEvent(derivationIndex, isParent, parentOrDerivativeIndex) {
    var control = event.srcElement;

	switch (event.type) {
	  
	  case "focus":
    	controlWithFocus = control.name;
	    var derivation = getDerivation(derivationIndex);
	    if (isParent) {
	      if (parentOrDerivativeIndex == (derivation.getNumberOfParents() -1)) {
	    	if (isMultipleParentsAllowed()) {
	    	  derivation.addParentSample();
	    	}
	      }
	    }
	    else {
	      if (parentOrDerivativeIndex == (derivation.getNumberOfDerivatives() -1)) {
	    	derivation.addDerivativeSample();
	      }
	    }
	    if (derivationIndex == (getNumberOfDerivations() - 1)) {
	      newDerivation();
	    }
		setTimeout('keepFocus()', 100);
		break;

	}
  
  }
  
}

function Derivation() {
  this.getDerivationBatch = getDerivationBatch;
  this.setDerivationBatch = setDerivationBatch;
  this.addParentSample = addParentSample;
  this.addDerivativeSample = addDerivativeSample;
  this.getNumberOfParents = getNumberOfParents;
  this.setNumberOfParents = setNumberOfParents;
  this.incrementNumberOfParents = incrementNumberOfParents;
  this.getNumberOfDerivatives = getNumberOfDerivatives;
  this.setNumberOfDerivatives = setNumberOfDerivatives;
  this.incrementNumberOfDerivatives = incrementNumberOfDerivatives;
  this.getIndex = getIndex; 
  this.setIndex = setIndex; 
  this.getParentGroupTdId = getParentGroupTdId;
  this.setParentGroupTdId = setParentGroupTdId;
  this.getDerivativeGroupTdId = getDerivativeGroupTdId;
  this.setDerivativeGroupTdId = setDerivativeGroupTdId;
  this.showDerivativeBarcodes = showDerivativeBarcodes;
  this.showDerivativeCounts = showDerivativeCounts;
  this.setRepresentativeParentSample = setRepresentativeParentSample;
  this.getRepresentativeParentSample = getRepresentativeParentSample;
  
  var _derivationBatch = null;
  var _numberOfParents = 0;
  var _numberOfDerivatives = 0;
  var _index = 0; 
  var _parentGroupTdId = null;
  var _derivativeGroupTdId = null;
  var _self = this;
  var _representativeParentSample = null;
  
  function getDerivativeGroupTdId() {
    return _derivativeGroupTdId;
  }
  
  function setDerivativeGroupTdId(derivativeGroupTdId) {
    _derivativeGroupTdId = derivativeGroupTdId;
  }
  
  function getParentGroupTdId() {
    return _parentGroupTdId;
  }
  
  function setParentGroupTdId(parentGroupTdId) {
    _parentGroupTdId = parentGroupTdId;
  }
  
  function getIndex() {
    return _index;
  }
  
  function setIndex(index) {
    _index = index;
  }
  
  function getDerivationBatch() {
  	return _derivationBatch;	
  }
  
  function setDerivationBatch(derivationBatch) {
  	_derivationBatch = derivationBatch;
  }

  function getNumberOfDerivatives() {
    return _numberOfDerivatives;
  }

  function setNumberOfDerivatives(numberOfDerivatives) {
    _numberOfDerivatives = numberOfDerivatives;
  }

  function incrementNumberOfDerivatives() {
  	_numberOfDerivatives++;
  } 

  function getNumberOfParents() {
    return _numberOfParents;
  }

  function setNumberOfParents(numberOfParents) {
    _numberOfParents = numberOfParents;
  }

  function incrementNumberOfParents() {
  	_numberOfParents++;
  } 
  
  function addParentSample() {
    var oTD = document.all[this.getParentGroupTdId()];
    
    var innerHTML = oTD.innerHTML;
    innerHTML += ' <INPUT type="text" name="dto.derivation[' + getIndex() + '].parent[' + getNumberOfParents() + '].sampleId"';
    innerHTML += ' size="20"';
    innerHTML += ' maxlength="30"';        
    innerHTML += ' onfocus="derivationBatch.handleEvent(' + getIndex() + ', true, ' + getNumberOfParents() + ');"';        
    innerHTML += '/>'
                  
    oTD.innerHTML = innerHTML;
    incrementNumberOfParents(); 
  }

  function addDerivativeSample() {
    var oTD = document.all[this.getDerivativeGroupTdId()];
    var span = oTD.getElementsByTagName("span")[0];

    var innerHTML = span.innerHTML;
    innerHTML += ' <INPUT type="text" name="dto.derivation[' + getIndex() + '].child[' + getNumberOfDerivatives() + '].sampleId"';
    innerHTML += ' size="20"';
    innerHTML += ' maxlength="30"';        
    innerHTML += ' onfocus="derivationBatch.handleEvent(' + getIndex() + ', false, ' + getNumberOfDerivatives() + ');"';        
    innerHTML += '/>'
                  
    span.innerHTML = innerHTML;
    incrementNumberOfDerivatives(); 
  }  
  
  function showDerivativeBarcodes() {
    var oTD = document.all[this.getDerivativeGroupTdId()];
    var spans = oTD.getElementsByTagName("span");
    spans[0].style.display = '';
    spans[1].style.display = 'none';
  }
  
  function showDerivativeCounts() {
    var oTD = document.all[this.getDerivativeGroupTdId()];
    var spans = oTD.getElementsByTagName("span");
    spans[0].style.display = 'none';
    spans[1].style.display = '';
  }

  function getRepresentativeParentSample() {
  	return _representativeParentSample;
  }
  
  function setRepresentativeParentSample(representativeParentSample) {
  	_representativeParentSample = representativeParentSample;
  }
}

function Attribute() {
  this.setCui = setCui;
  this.getCui = getCui;
  this.setCuiDescription = setCuiDescription;
  this.getCuiDescription = getCuiDescription;
  this.setDerivativeInherits = setDerivativeInherits;
  this.getDerivativeInherits = getDerivativeInherits;
  this.setDerivativeDefaults = setDerivativeDefaults;
  this.getDerivativeDefaults = getDerivativeDefaults;
  this.setRelevance = setRelevance;
  this.getRelevance = getRelevance;
  this.setInputName = setInputName;
  this.getInputName = getInputName;
  this.setRequired = setRequired;
  this.getRequired = getRequired;
  
  var _self = this;
  var _cui;
  var _cuiDescription;
  var _inputName;
  var _derivativeInherits;
  var _derivativeDefaults;
  var _relevance;
  var _required;
  
  function setCui(cui) {
    _cui = cui;
  }
  
  function getCui() {
    return _cui;
  }
  
  function setCuiDescription(cuiDescription) {
    _cuiDescription = cuiDescription;
  }
  
  function getCuiDescription() {
    return _cuiDescription;
  }
  
  function setDerivativeInherits(derivativeInherits) {
    _derivativeInherits = derivativeInherits;
  }
  
  function getDerivativeInherits() {
    return _derivativeInherits;
  }
  
  function setDerivativeDefaults(derivativeDefaults) {
    _derivativeDefaults = derivativeDefaults;
  }
  
  function getDerivativeDefaults() {
    return _derivativeDefaults;
  }
  
  function setRelevance(relevance) {
    _relevance = relevance;
  }
  
  function getRelevance() {
    return _relevance;
  }
  
  function setInputName(name) {
    _inputName = name;
  }
  
  function getInputName() {
    return _inputName;
  }
  
  function setRequired(required) {
    _required = required;
  }
  
  function getRequired() {
    return _required;
  }
}

function Attributes() {
  this.addAttribute = addAttribute;
  this.getAttribute = getAttribute; 
  this.getFirstAttribute = getFirstAttribute;
  this.getNextAttribute = getNextAttribute;
  
  var _attributes = new LinkedList(true);
  var _self = this;
  

  function addAttribute(attribute) {
	  _attributes.insertBefore(null, attribute, attribute.getCui());
  }

  function getAttribute(cui) {
	  var cell = _attributes.getCellByKey(cui);	
	  return (cell == null) ? null : cell.item;
  }
  
  function getFirstAttribute() {
	  this.currentCell = _attributes.getFirstCell();
	  if (this.currentCell == null) {
		  return null;
	  }
	  else {
		  return this.currentCell.item;
	  }
  }
  
  function getNextAttribute() {
	  if (this.currentCell == null) {
		  return null;
	  }
	  else {
		  this.currentCell = _attributes.getNextCell(this.currentCell);
		  if (this.currentCell == null) {
			  return null;
		  }
		  else {
			  return this.currentCell.item;
		  }
	  }
  }
}
