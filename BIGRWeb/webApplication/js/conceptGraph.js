// conceptGraph.js

//---------- Begin BigrConceptsCache class ----------
//
// The BigrConceptsCache class holds one or more BigrConcept instances 
// keyed by their CUI, allowing them to be retrieved by their CUI.  This
// allows a concept's description to be determined based on its CUI,
// and thus acts like the concept cache in Java.  It is instantiated as
// a singleton.
//
var bigrConceptsCache = new function() {

	this.SINGLETON = new BigrConceptsCache();

	function BigrConceptsCache() {
	
	  //--- Public methods
	  this.addConcept = addConcept;
	  this.getConcept = getConcept;
	  this.addSpecialConcept = addSpecialConcept;
	  this.getSpecialConcept = getSpecialConcept;
	
	  //--- Private member variables
	  var _self = this;
	  var _concepts = new Array();
	  var _specialConcepts = new Array();
	  
		//--- Private methods

		// Adds the specified concept to this collection.  If the concept
		// with the specified CUI already exists it is overwritten.
	  function addConcept(concept) {
	  	if (concept == null) {
	  		alert("In BigrConceptsCache.addConcept: attempt to add null concept.");
	  		return;
	  	}

			if (!checkObjectType(concept, "BigrConcept")) {
	  		alert("In BigrConceptsCache.addConcept: attempt to add a concept that is not of type BigrConcept.");
	  		return;
	    }  	
	  	
	  	var cui = concept.getCui();
	  	if (cui == null) {
	  		alert("In BigrConceptsCache.addConcept: attempt to add concept with null CUI.");
	  		return;
	  	}
	  	
	  	_concepts[cui] = concept;
	  }

		// Returns the specified concept.
	  function getConcept(cui) {
	  	return _concepts[cui];
	  }

	  // Adds the special concept to this collection.  In general, 
	  // special concepts are those that you may want to get via a
	  // well-defined key because some logic depends upon the concept.
	  function addSpecialConcept(conceptKey, concept) {
			if (getSpecialConcept(conceptKey) != null) {
	  		alert("In BigrConceptsCache.addSpecialConcept: attempt to add special concept with a key that already exists: " + conceptKey);
	  		return;
	  	}
	  
	  	addConcept(concept);
	  	_specialConcepts[conceptKey] = concept;
	  }
	  
	  // Returns the specified special concept.
	  function getSpecialConcept(conceptKey) {
	  	return _specialConcepts[conceptKey];
	  }

	}	
} //---------- End BigrConceptsCache class ----------

//
// BigrConceptGraph is a JavaScript object that encapsulates a BIGR 
// concept graph.  It supports viewing a concept graph as a hierarchy,
// with methods that allow expanding/collapsing vertices with children
// and selecting a vertex.

function BigrConceptGraph() {
	this.conceptsByCui = new Array();
	this.concepts = new Array();
	this.conceptCount = 0;
	this.modalPopup = false;
	this.hiddenInput = null;
	this.formName = null;
}

// Adds a BigrConcept to the BigrConceptGraph.  The concept parameter 
// must be a BigrConcept.
BigrConceptGraph.prototype.addConcept = function(concept) {
	this.conceptsByCui[concept.getCui()] = concept;
	this.concepts[this.conceptCount++] = concept;
	return concept;
}

// Adds a non-leaf BigrConcept to the BigrConceptGraph.  The concept 
// parameter must be a BigrConcept.
BigrConceptGraph.prototype.addNonleafConcept = function(concept, childContainerId, imageId) {
	this.addConcept(concept);
	concept.setChildContainerId(childContainerId);
	concept.setImageId(imageId);
	return concept;
}

// Returns the BigrConcept with the specified CUI from the 
// BigrConceptGraph.
BigrConceptGraph.prototype.getConcept = function(cui) {
	return this.conceptsByCui[cui];
}

BigrConceptGraph.prototype.getSelectedConcepts = function() {
	var concepts = new Array();
	var j = 0;
	for (var i = 0; i < this.conceptCount; i++) {
		if (this.concepts[i].isSelected()) {
			concepts[j++] = this.concepts[i];
		}
	}
	return concepts;
}

// Returns whether this BigrConceptGraph is on a modal popup page.
BigrConceptGraph.prototype.isModalPopup = function() {
	return this.modalPopup;
}

// Indicates that this BigrConceptGraph is on a modal popup page.
BigrConceptGraph.prototype.setModalPopup = function() {
	this.modalPopup = true;
}

// Returns the name of the hidden input control to which the CUI 
// should be assigned when a concept selected. Only applicable if this 
// BigrConceptGraph is not on a modal popup page.
BigrConceptGraph.prototype.getHiddenInput = function() {
	return this.hiddenInput;
}

// Sets the name of the hidden input control to which the CUI 
// should be assigned when a concept is selected. Only applicable if 
// this BigrConceptGraph is not on a modal popup page.
BigrConceptGraph.prototype.setHiddenInput = function(hiddenInput) {
	this.hiddenInput = hiddenInput;
}

// Returns the form that should be submitted when a concept is selected.
// Only applicable if this BigrConceptGraph is not on a modal popup 
// page.
BigrConceptGraph.prototype.getForm = function() {
	return document.all[this.formName];
}

// Returns the name of the form that should be submitted when a concept 
// is selected.  Only applicable if this BigrConceptGraph is not on a 
// modal popup page.
BigrConceptGraph.prototype.getFormName = function() {
	return this.formName;
}

// Sets the name of the form that should be submitted when a concept is 
// selected.  Only applicable if this BigrConceptGraph is not on a modal 
// popup page.
BigrConceptGraph.prototype.setFormName = function(formName) {
	this.formName = formName;
}

// Deselects all concepts.
BigrConceptGraph.prototype.deselectAll = function() {
	for (var i = 0; i < this.conceptCount; i++) {
		this.concepts[i].setSelected(false);
	}
}

// Deselects all concepts but no value.
BigrConceptGraph.prototype.deselectAllButNoValue = function(noValueCode) {
	for (var i = 0; i < this.conceptCount; i++) {
		if (this.concepts[i].cui != noValueCode) {
			this.concepts[i].setSelected(false);
		}
	}
}

// Deselects no value.
BigrConceptGraph.prototype.deselectNoValue = function(noValueCode) {
	for (var i = 0; i < this.conceptCount; i++) {
		if (this.concepts[i].cui == noValueCode) {
			this.concepts[i].setSelected(false);
		}
	}
}

// Selects the concept specified by CUI.
BigrConceptGraph.prototype.selectLink = function() {
	var control = event.srcElement;
	if (control.nonleaf != null) {
		var concept = this.getConcept(control.nonleaf);
		if (concept != null) {
			concept.toggle();
		}
	}
	else if (control.leaflink != null) {
		this.select(control.leaflink);
	}
	else {
		alert("In BigrConceptGraph.selectLink: event source does not have nonleaf or leaflink attribute set.");
	}
}

// Selects the concept specified by CUI.
BigrConceptGraph.prototype.select = function(cui) {
  var concept = this.getConcept(cui);
  if (this.isModalPopup()) {
    window.returnValue = new BigrConcept(concept.getCui(), concept.getDescription());
    window.close();
  }
  else {
  	var hiddenInput = this.getHiddenInput();
  	if (hiddenInput != null) {
  		document.all[hiddenInput].value = concept.getCui();
  	}
  	var form = this.getForm();
  	if (form != null) {
	    if (form.fireEvent("onsubmit")) {
	  	  form.submit();
	    }
  	}
  }
}

// This BigrConceptGraph's string representation.
BigrConceptGraph.prototype.toString = function() {
	var returnString = "BigrConceptGraph: ";
	returnString += this.conceptCount;
	returnString += " concepts, [modalPopup=";
	returnString += this.isModalPopup() ? "true" : "false";
	returnString += "], [hiddenInput=";
	returnString += this.getHiddenInput();
	returnString += "], [formName=";
	returnString += this.getFormName();
	returnString += "]";
	return returnString;
}

// BigrConcept is a JavaScript object that encapsulates a BIGR concept.
function BigrConcept(cui, description) {
  this.cui = cui;
  this.description = description;
  this.childContainerId = null;
  this.imageId = null;
  this.collapsed = true;
  this.selected = false;
  
	bigrConceptsCache.SINGLETON.addConcept(this);
}

BigrConcept.prototype.getCui = function() {
	return this.cui;
}

BigrConcept.prototype.getDescription = function() {
	return this.description;
}

BigrConcept.prototype.getChildContainer = function() {
	return document.all[this.childContainerId];
}

BigrConcept.prototype.setChildContainerId = function(id) {
	this.childContainerId = id;
}

BigrConcept.prototype.getImage = function() {
	return document.all[this.imageId];
}

BigrConcept.prototype.setImageId = function(id) {
	this.imageId = id;
}

BigrConcept.prototype.isCollapsed = function() {
  return this.collapsed;
}

BigrConcept.prototype.setCollapsed = function() {
  this.collapsed = true;
}

BigrConcept.prototype.isExpanded = function() {
  return !this.isCollapsed();
}

BigrConcept.prototype.setExpanded = function() {
  this.collapsed = false;
}

BigrConcept.prototype.isSelected = function() {
  return this.selected;
}

BigrConcept.prototype.setSelected = function(sel) {
  this.selected = sel;
}


// Toggles the state of this non-leaf concept.
BigrConcept.prototype.toggle = function() {
	if (this.isCollapsed()) {
		this.expand();
	}
	else {
		this.collapse();
	}
}

// Expands this non-leaf concept.
BigrConcept.prototype.expand = function() {
  var container = this.getChildContainer();
  var image = this.getImage();
  if (container != null) {
		container.style.display = "block";
		this.setExpanded();
		if (image != null) {
			image.src = "/BIGR/images/MenuOpened.gif";
		}
	}
  else {
    alert("Attempt to toggle concept " + cui + " which does not have an associated container");
  }
}

// Collapses this non-leaf concept.
BigrConcept.prototype.collapse = function() {
  var container = this.getChildContainer();
  var image = this.getImage();
  if (container != null) {
		container.style.display = "none";
		this.setCollapsed();
		if (image != null) {
			image.src = "/BIGR/images/MenuClosed.gif";
		}
	}
  else {
    alert("Attempt to toggle concept " + cui + " which does not have an associated container");
  }
}

// This BigrConcept's string representation.
BigrConcept.prototype.toString = function() {
	var returnString = "BigrConcept: ";
	returnString += "[cui=";
	returnString += this.getCui();
	returnString += "], [description=";
	returnString += this.getDescription();
	returnString += "]";
	return returnString;
}

