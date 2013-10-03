// File: queryElements.js
//
// Depends on: gsbio.js, prototype.js.
//
// Description: Contains a collection of query criteria values to be submitted 
// to the server.

// Create the namespace for KC query elements.
GSBIO.namespace("kc.query");

/**
 * A singleton that stores a collection of KnowledgeCapture query element 
 * objects that hold selected values and operators.
 */
GSBIO.kc.query.Elements = function () {
  var _elementMap = [];
  var _path = "";
  
  // This event handler deals with expanding/collapsing a node in a multi-level
  // value set, and is called by the commonEventHandler.  It relies on how the 
  // HTML is rendered, and will need to be updated if we decide to change the rendering.
  //
  // The HTML for the non-leaf node is rendered as a +/- IMG inside a TD inside 
  // a TR.  The leaf nodes that it controls are all rendered inside of a TR 
  // that is the next sibling TR of the TR that contains the IMG.
  //
  // Inputs:
  //   element: the source of the event
  //   type: the type of event
  function handleValueSetExpand(element, type) {
    var a, i, n, foundDiv, td, tr;

	// If an IMG was clicked and the IMG is in a DIV with class=kcCvList, then 
	// it appears that the user clicked on a non-leaf node of a value set.
    if ((type == 'click') && element.tagName.toLowerCase() == 'img') {
      a = element.ancestors();
      foundDiv = false;
      for (i = 0, n = a.length; i < n; i++) {
        if (a[i].tagName.toLowerCase() == 'div' && a[i].classNames().member('kcCvList')) {
          foundDiv = true;
          break;
        }
      }

      // If they clicked on the IMG, then get the parent TD.
      if (foundDiv) {
        td = element.ancestors()[0];
      }
    }

    if (td) {
      // Get the sibling TR of the parent TR of the TD, toggle its visibility
      // and change the image appropriately.  Also return true to let the
      // common event handler know that we handled the event here.
      tr = td.ancestors()[0].next();
      tr.toggle();
      element.src = tr.visible() ? _path + "/images/MenuOpened.gif" : _path + "/images/MenuClosed.gif";
      return true;
    }
    return false;
  }
  
  return {
    formDefinitionId: null,
    elements: [],

    // Clears the state of this collection.  This is useful if a new query
    // form is retrieved via AJAX.
	clear:
	  function() {
        this.formDefinitionId = null;
        this.elements = [];
	  },

	// Sets the full path at which the KC files are deployed.
	setPath:
	  function(path) {
	    _path = path;
	  },
	    
	  
    // Returns the query form definition id.
    getFormDefinitionId: 
      function () {
        return this.formDefinitionId;
      },

    // Sets the query form definition id.
    setFormDefinitionId: 
      function (id) {
        this.formDefinitionId = id;
      },

    // Adds the specified query element to this collection.
    addElement:
      function (element) {
        this.elements[this.elements.length] = element;
        _elementMap[element.elementId] = element;
      },

    // Returns the specified query element from this collection if it exists.
    // The input element can either be 1) the element id of an element in this
    // collection, or 2) the name of an HTML element or 3) the HTML element 
    // itself, for which an element exists in this collection.
    getElement:
      function (element) {
        var name = (typeof element == 'string') ? element : element.name;
        var e = _elementMap[name];
        if (!e) {
          e = _elementMap[(name.split('.'))[0]];
        }
        return e;
      },

    commonEventHandler:
      function () {
        // Get the HTML element that was the source of the event and the type
        // of event.
        var e = $(Event.element(event));
        var t = event.type;

		// First, check if this event is for expanding/collapsing a value set.
		// If it is, handleValueSetExpand() will handle the event appropriately
		// and return true, so simply return in this case.
        if (handleValueSetExpand(e, t)) {
          return;
        }

        // Parse the name of the HTML element.  If there aren't at least 2 
        // pieces to the name then it is not a input that we care about, since
        // all inputs that are used to capture query criteria have at least
        // 2 pieces.  The second piece indicates the type of input, so save it 
        // for later.
        var pieces = (e.name) ? e.name.split('.') : [];
        if (pieces.length <= 1) {
          return;
        }
        var input = pieces[1];
        
        // Get the query element that corresponds to the HTML element
        // from this collection of query elements.  If there is no such
        // query element, then return without doing anything.
        e = this.getElement(e);
        if (!e) {
          return;
        }

		// Handle the event.  First, if the input is the clear button and
		// it was clicked, perform the clear.
        if ((input == 'clear') && (t == 'click')) {
          e.clear();
        }
        
		// If the input is the ADE criteria button and it was clicked, 
		// show the ADE popup.
        else if ((input == 'addAde') && (t == 'click')) {
          e.adeCriteriaPopup();
        }

		// If the input is the any checkbox, then clear all other inputs except
		// the any checkbox and the ADE criteria.  Note that we do not care
		// about the event since the method being called is smart enough to
		// only perform the clear if the any checkbox is actually checked.
        else if (input == 'any') {
          e.clearExceptAnyAde();
        }

		// If the input is a value or a comparison operator, then clear the
		// any checkbox.  Note that we do not care about the event since the 
		// method being called is smart enough to only perform the clear if 
		// there are actually values entered and/or comparison operators 
		// selected.
        else if ((input == 'value') || (input == 'compOp') || (input == 'logOp')) {
          e.clearAny();
        }
      },
      
    // Converts this query elements collection to a single request parameter,
    // which is a JSON string.
    toRequestParameter:
      function () {
        // First updates all of the query elements in this collection from 
        // their HTML inputs.
        for (var i = 0, n = this.elements.length; i < n; i++) {
          this.elements[i].updateFromInputs();
        }
        return Object.toJSON(this);
      }
  };
}();

/**
 * Represents a query element.
 */
GSBIO.kc.query.Element = function (elementId, displayName, properties) {
  var element = {};
  var _properties = properties;
  
  element.elementId = elementId;
  element.anyValue = false;
  element.valueSet = null;
  element.adeElements = null;

  element.displayName = displayName;
  element.elementType = null;
      
  element.initialize = function (element) {
    this.elementId = element.elementId;
    this.anyValue = element.anyValue;
    this.displayName = element.displayName;    
    this.valueSet = GSBIO.kc.query.ValueSet();
    if (element.valueSet) {
      this.valueSet.initialize(element.valueSet);
    }
  };

  // Called when the user clicks the Add ADE Criteria button.  Opens the 
  // ADE popup, and then adds the values entered by the user to this element.
  element.adeCriteriaPopup = function () {
    var i, n, adeCriteria, elements, element, genericElement;
    
    // Build the URL for the popup, including its parameters.
    var url = _properties.adePopupUrl;
    if (url.indexOf('?') === -1) {
      url = url + '?';
    }
    else {
      url = url + '&';
    }
    var params = {"formDefinitionId": GSBIO.kc.query.Elements.getFormDefinitionId(), 
                  "dataElementId": this.elementId};
    url = url + $H(params).toQueryString();

    // Build the arguments for the popup.  The first argument is our ARTS 
    // object, which the popup will use to merge its ARTS concepts into.
    // The second argument is the values of all of the ADE elements of this
    // data element.  If there are no ADE elements, then the second argument
    // is not passed.
    var args = [];
    args[0] = GSBIO.ARTS;
    if (this.adeElements) {
      args[1] = Object.toJSON(this.adeElements);
    }

	// Show the ADE popup.
    var r = window.showModalDialog(url, args, "dialogWidth:750px;dialogHeight:700px;");
    
    // If the ADE popup returned anything, then create appropriate ADE elements,
    // initialize them and add them to this data element.
    if (r) {
      adeCriteria = r.evalJSON();
      elements = adeCriteria.elements;
      this.adeElements = [];
      for (i = 0, n = elements.length; i < n; i++) {
        genericElement = elements[i];
        switch (genericElement.elementType) {
          case "range":
            element = GSBIO.kc.query.ElementRange();
            break;
          case "discrete":
            element = GSBIO.kc.query.ElementDiscrete();
            break;
          default:
            // should report an error somehow since this should not happen
        }
        if (element) {
          element.initialize(genericElement);
          this.addAdeElement(element);
        }
      }      
      $(_properties.adeSummary).update(this.adeSummary());
    }
  };

  element.addAdeElement = function(adeElement) {
    if (!this.adeElements) {
      this.adeElements = [];
    }
    this.adeElements[this.adeElements.length] = adeElement;
  };

  element.adeSummary = function () {
    var i, es, s = "", header = false;
    var n = (this.adeElements) ? this.adeElements.length : 0;
    for (i = 0; i < n; i++) {
      es = this.adeElements[i].summary();
      if (es) {
        if (!header) {
          s = s + "ADE Criteria:";
          header = true;
        }
        s = s + "<br>&nbsp;&nbsp;";
        s = s + es;
      }
    }
    return s;
  };
  
  return element;
};

/**
 * Represents a query element expressed as a range of values.  It must be
 * initialized with the properties of the inputs that comprise the range
 * specification, in addition to the identifier of the element used as a query 
 * element.
 */
GSBIO.kc.query.ElementRange = function (elementId, displayName, properties) {
  var elementRange = GSBIO.kc.query.Element(elementId, displayName, properties);
  var _properties = properties;
  var _opDisplay = {"eq":"=", "ne":"<>", "lt":"<", "le":"<=", "gt":">", "ge":">="};
  
  elementRange.elementType = "range";
  
  // Clears all of the input controls and ADE criteria that comprise this query 
  // element.  Can be called as an event handler when the user presses the 
  // clear button for this element.
  elementRange.clear = function () {
    this.anyValue = false;
    this.valueSet = null;
    this.adeElements = null;
  	$(_properties.any).checked = false;
  	$(_properties.logOp).selectedIndex = 0;
  	$(_properties.compOp1).selectedIndex = 0;
  	$(_properties.compOp2).selectedIndex = 0;
  	$(_properties.value1).clear();
    $(_properties.value2).clear();
    var adeSummary = $(_properties.adeSummary);
    if (adeSummary) {
      adeSummary.update();
    }
  };
  
  // Clears all inputs except the ADE criteria and the any checkbox itself.
  // Can be called as an event handler when the user checks the any checkbox.
  elementRange.clearExceptAnyAde = function () {
  	if ($F(_properties.any)) {
      this.valueSet = null;
      $(_properties.logOp).selectedIndex = 0;
      $(_properties.compOp1).selectedIndex = 0;
      $(_properties.compOp2).selectedIndex = 0;
      $(_properties.value1).clear();
      $(_properties.value2).clear();
  	}    
  };

  // Clears the any checkbox if any of the main inputs have non-default values.
  // Can to be called as an event handler when the user enters/selects
  // values in the main inputs for this element.
  elementRange.clearAny = function () {
    if (($(_properties.compOp1).value.length > 0) ||
        ($(_properties.compOp2).value.length > 0) ||
        ($(_properties.value1).value.length > 0) ||
        ($(_properties.value2).value.length > 0)) {
      this.anyValue = false;
      $(_properties.any).checked = false;
  	}    
  };

  // Updates this query element from the inputs specified by its properties,
  // creating an appropriate value set.  This method does not attempt to
  // validate that inputs were specified appropriately (e.g. if value1 was
  // specified, then so was compOp1), leaving that to the server.
  elementRange.updateFromInputs = function () {
    var vs;
  	if ($F(_properties.any)) {
      this.anyValue = true;
      this.valueSet = null;
  	}
  	else {
      this.anyValue = false;
      vs = GSBIO.kc.query.ValueSet($F(_properties.logOp));
  	  vs.addValue($F(_properties.value1), $F(_properties.compOp1));
  	  vs.addValue($F(_properties.value2), $F(_properties.compOp2));
  	  this.valueSet = vs;
  	}
  };
  
  // Updates the input controls of this query element from the values in this
  // query element.
  elementRange.updateInputs = function () {
    var vs, value, values;
  	if (this.anyValue) {
  	  $(_properties.any).checked = true;
  	}
  	else {
      vs = this.valueSet;
      if (vs) {
        if (vs.logOp) {
          $(_properties.logOp).value = vs.logOp;
        }
        values = vs.values;
        if (values) {
          value = values[0];
          if (value) {
            if (value.compOp) {
              $(_properties.compOp1).value = value.compOp;
            }
            if (value.value) {
              $(_properties.value1).value = value.value;
            }
          }
          value = values[1];
          if (value) {
            if (value.compOp) {
              $(_properties.compOp2).value = value.compOp;
            }
            if (value.value) {
              $(_properties.value2).value = value.value;
            }
          }
        }
      }
  	}
  };

  // Returns a summary of this query element suitable for display in the
  // user interface.  One use for this is when displaying a summary of the
  // ADEs for the query element.
  elementRange.summary = function () {
    var v, vs, s = "";
    if (this.anyValue) {
      s = s + this.displayName;
      s = s + " has any value";
    }
    else if (this.valueSet && !this.valueSet.isEmpty()) {
      s = s + this.displayName;
	  vs = this.valueSet.values;
      v = vs[0];
      s = s + " is ";
      s = s + (_opDisplay[v.compOp] || "???");
      s = s + " ";
      s = s + (v.value || "???");
      v = vs[1];
      if (v.value || v.compOp) {
        s = s + " ";
        s = s + (this.valueSet.logOp || "???");
        s = s + " ";
        s = s + (_opDisplay[v.compOp] || "???");
        s = s + " ";
        s = s + (v.value || "???");
      }
    }
    return s;
  };  

  return elementRange;
};

/**
 * Represents a query element expressed as a discrete set of values.
 * It must be initialized with the properties of the inputs that comprise the
 * specification of the discrete value, in addition to the identifier of the 
 * element used as a query element.
 */
GSBIO.kc.query.ElementDiscrete = function (elementId, displayName, properties) {
  var elementDiscrete = GSBIO.kc.query.Element(elementId, displayName, properties);
  var _properties = properties;
  
  elementDiscrete.elementType = "discrete";
  
  // Clears all of the input controls and ADE criteria that comprise this query 
  // element.  Can be called as an event handler when the user presses the 
  // clear button for this element.
  elementDiscrete.clear = function () {
    this.anyValue = false;
    this.valueSet = null;
    this.adeElements = null;
  	$(_properties.any).checked = false;
    document.getElementsByName(_properties.logOp)[0].checked = true;
    if ($(_properties.value)) {
      $(_properties.value).clearCheckboxes();
    }
    var adeSummary = $(_properties.adeSummary);
    if (adeSummary) {
      adeSummary.update();
    }
  };
  
  // Clears all inputs except the ADE criteria and the any checkbox itself.
  // Can be called as an event handler when the user checks the any checkbox.
  elementDiscrete.clearExceptAnyAde = function () {
    var i, j, n;
  	if ($F(_properties.any)) {
      this.valueSet = null;
      i = document.getElementsByName(_properties.logOp);
      for (j = 0, n = i.length; j < n; j++) {
        i[j].checked = false;
      }
      if ($(_properties.value)) {
        $(_properties.value).clearCheckboxes();
      }
  	}    
  };
  
  // Clears the any checkbox if any of the main inputs have non-default values.
  // Can to be called as an event handler when the user enters/selects
  // values in the main inputs for this element.
  elementDiscrete.clearAny = function () {
    var j, n, v, lo = false;
    var i = document.getElementsByName(_properties.logOp);
    for (j = 0, n = i.length; j < n; j++) {
      if (i[j].checked) {
        lo = true;
      }
    }
    if (!lo && $(_properties.value)) {
      v = $(_properties.value).getCheckboxesValues();    
    }
    if (lo || (v && v.length > 0)) {
      this.anyValue = false;
      $(_properties.any).checked = false;
    }
  };

  // Updates this query element from the inputs specified by its properties,
  // creating an appropriate value set.  This method does not attempt to
  // validate that inputs were specified appropriately, leaving that to the 
  // server.
  elementDiscrete.updateFromInputs = function () {
    var i, n, logOp, compOp, vs, values;
  	if ($F(_properties.any)) {
      this.anyValue = true;
      this.valueSet = null;
  	}
  	else {
      this.anyValue = false;
      this.valueSet = null;
      if ($(_properties.value)) {
  	    logOp = $(_properties.logOp).getRadiosValue();
  	    compOp = (logOp == "and") ? "ne" : "eq";
        vs = GSBIO.kc.query.ValueSet(logOp);
  	    values = $(_properties.value).getCheckboxesValues();
  	    for (i = 0, n = values.length; i < n; i++) {
          vs.addValue(values[i], compOp);
        }
        this.valueSet = vs;
  	  }
  	  else {
        this.valueSet = GSBIO.kc.query.ValueSet(null);
  	  }
  	}
  };

  // Updates the input controls of this query element from the values in this
  // query element.
  elementDiscrete.updateInputs = function () {
    var i, j, n, v, vs;
  	if (this.anyValue) {
  	  $(_properties.any).checked = true;
  	}
  	else {
      vs = this.valueSet;
      if (vs) {
        if (vs.logOp) {
          i = document.getElementsByName(_properties.logOp);
          for (j = 0, n = i.length; j < n; j++) {
            if (i[j].value == vs.logOp) {
              i[j].checked = true;
            }
          }
        }
        values = vs.values;
        if (values && values.length && $(_properties.value)) {
          v = [];
          for (j = 0, n = values.length; j < n; j++) {
            v.push(values[j].value);
          }
  	      $(_properties.value).setCheckboxesValues(v);
        }
      }
  	}
  };

  // Returns a summary of this query element suitable for display in the
  // user interface.  One use for this is when displaying a summary of the
  // ADEs for the query element.
  elementDiscrete.summary = function () {
    var i, n, v, vs, d, s = "", logOp;
    if (this.anyValue) {
      s = s + this.displayName;
      s = s + " has any value";
      return s;
    }
    else if (this.valueSet && !this.valueSet.isEmpty()) {
      s = s + this.displayName;
      logOp = this.valueSet.logOp;
      if (logOp) {
        logOp = logOp.toLowerCase();
      }
      if (logOp === "or") {
        s = s + " is one of: ";
      }
      else if (logOp === "and") {
        s = s + " is not one of: ";
      }
      else {
        s = s + " ???: ";
      }
	  vs = this.valueSet.values;
	  for (i = 0, n = vs.length; i < n; i++) {
	    if (i > 0) {
	      s = s + ", ";
	    }
        v = vs[i];
        d = GSBIO.ARTS.getDisplay(v.value);
        d = d || v.value;
        s = s + d;
	  }
    }
    return s;
  };  

  return elementDiscrete;
};

/**
 * Represents a query element's value set.
 */
GSBIO.kc.query.ValueSet = function(logOp) {
  var valueSet = {};

  valueSet.logOp = logOp || null;
  valueSet.values = [];

  valueSet.initialize = function (valueSet) {
    this.logOp = valueSet.logOp;
    this.values = valueSet.values;
  };

  valueSet.addValue = function(value, compOp) {
    this.values[this.values.length] = {value: value, compOp: compOp};
  };
  
  valueSet.isEmpty = function() {
    if (!this.values || this.values.length === 0) {
      return true;
    }
    else {
      for (var i = 0, n = this.values.length; i < n; i++) {
        v = this.values[i];
        if (v.value || v.compOp) {
          return false;
        }
      }
      return true;
    }
  };
 
  return valueSet; 
};
