// File: dataElement.js
//
// Depends on: gsbio.js, gsbioUtil.js, prototype.js.
//
// Description: Objects to support editing data elements.

// Create the namespace for KC data elements.
GSBIO.namespace("kc.data");

/**
 * A singleton that represents a collection of form instances.
 */
GSBIO.kc.data.FormInstances = function() {
  var object = {};
  var _formMap = $H();

  object.forms = $A();
  
  /**
   * Creates and returns a new GSBIO.kc.data.FormInstance object, using the specified initializer.
   * If the initializer contains a non-numeric 'id' property it will be used as the unique id of 
   * the created form instance and can later be used with the getFormInstance() method of this 
   * object to obtain the form instance.
   *
   * @param initializer an object that can be used to initialize the GSBIO.kc.data.FormInstance 
   *                    object.
   * @return the GSBIO.kc.data.FormInstance 
   */
  object.createFormInstance = function(initializer) {
    var f = GSBIO.kc.data.FormInstance(initializer);
    this.forms.push(f);
    var id = f.getId();
    if (id && !Number(id)) {
      _formMap[id] = f;
    }
    return f;
  };
  
  /**
   * Returns the GSBIO.kc.data.FormInstance object at the specified index or associated with the 
   * specified id.  If the input parameter is a number it is treated as an index and the form
   * at that index in this collection is returned.  If the input parameter is not a number it is
   * treated as an identifier and the form instance with that identifier is returned.  As a 
   * convenience, if the input parameter is missing it is treated as '0', indicating the the first
   * form instance should be returned.
   *
   * @param  idOrIndex  the numerical index or form identifier
   * @return the GSBIO.kc.data.FormInstance, or null if no such form exists
   */
  object.getFormInstance = function(idOrIndex) {  
    if (idOrIndex) {
      var f;
      var index = Number(idOrIndex);
      if (index) {
        f = this.forms[index];
      }
      else {
        f = _formMap[idOrIndex];
      }
      return f || null;
    }
    else {
      return this.forms.length > 0 ? this.forms[0] : null;
    }
  };

  /**
   * Deletes and returns the GSBIO.kc.data.FormInstance object at the specified index or associated 
   * with the  specified id.  If the input parameter is a number it is treated as an index and the 
   * form at that index in this collection is deleted.  If the input parameter is not a number it
   * is treated as an identifier and the form instance with that identifier is deleted.  As a 
   * convenience, if the input parameter is missing it is treated as '0', indicating the the first
   * form instance should be deleted.
   *
   * @param  idOrIndex  the numerical index or form identifier
   * @return the deleted GSBIO.kc.data.FormInstance, or null if no such form exists
   */
  object.deleteFormInstance = function(idOrIndex) {  
    var f, i, n;
    if (idOrIndex) {
      var index = Number(idOrIndex);
      if (index == NaN) {
        f = _formMap.remove(id);
        for (i = 0, n = this.forms.length; i < n; i++) {
          if (this.forms[i].getId() == f.getId()) {
            this.forms[i] = null;
          }
        }
        this.forms = this.forms.compact();
      }
      else {
        f = this.forms[index];
        this.forms[index] = null;
        this.forms = this.forms.compact();
      }
      return f || null;
    }
    else {
      f = this.forms.length > 0 ? this.forms[0] : null;
      this.forms = $A();
      return f;
    }
  };

  /**
   * Serializes all form instances to a JSON string.
   */
  object.serialize = function() {
    for (var i = 0, n = forms.length; i < n; i++) {
      forms[i].updateFromInputs();
    }
    return Object.toJSON(this);
  };

  return object;
}(); // GSBIO.kc.data.FormInstances

/**
 * Represents a single form instance.
 */
GSBIO.kc.data.FormInstance = function(initializer) {
  var object = {};
  var _id = null;
  var _elementMap = $H();

  object.formInstanceId = null;
  object.formDefinitionId = null;
  object.domainObjectId = null;
  object.domainObjectType = null;
  object.elements = null;

  /**
   * Returns the unique id of this object.  It is useful for uniquely identifying this form 
   * instance within the global collection of form instances.  It should be treated as a randomly 
   * generated unique id with no meaning.  It must be set via the initializer to this object.
   */
  object.getId = function() {
    return _id;
  };

  // Clears this form instance.
  object.clear = function() {
    this.formInstanceId = null;
    this.formDefinitionId = null;
    this.domainObjectId = null;
    this.domainObjectType = null;
    this.elements = null;
    _elementMap = $H();
  };

  // Adds the specified data element to this form instance.
  object.addDataElement = function(element) {
    if (!this.elements) {
      this.elements = $A();
    }
    this.elements.push(element);
    _elementMap[element.getId()] = element;
    element.setFormId(_id);
  };

  // Returns the specified data element from this form instance if it exists.
  object.getDataElement = function(elementId) {
    return _elementMap[elementId];
  };

  // Returns an indication of whether any data element in this form instance has changed.
  object.isChanged = function() {
    var changed = false;
    if (this.elements) {
      for (var i = 0, n = this.elements.length; i < n; i++) {
        if (this.elements[i].isChanged()) {
          changed = true;
          break;
        }
      }
    }
    return changed;
  };

  // Disables all of the buttons in all of the data elements of this form instance.
  object.disableButtons = function() {
    if (this.elements) {
      for (var i = 0, n = this.elements.length; i < n; i++) {
        this.elements[i].disableButtons();
      }
    }
  };

  // Enables all of the buttons in all of the data elements of this form instance.
  object.enableButtons = function() {
    if (this.elements) {
      for (var i = 0, n = this.elements.length; i < n; i++) {
        this.elements[i].enableButtons();
      }
    }
  };
	  
  // Updates all of the data elements in this form instance with the values of their input controls.
  object.updateFromInputs = function() {
    if (this.elements) {
      for (var i = 0, n = this.elements.length; i < n; i++) {
        this.elements[i].updateFromInputs();
      }
    }
  };

  /**
   * Serializes this form instance to a JSON string.
   */
  object.serialize = function() {
    this.updateFromInputs();
    return Object.toJSON(this);
  };

  // Applys appropriate CSS class names to alternate rows of data elements
  // to effectively alternate the color of each row.
  object.alternateRowColor = function() {
    var i, j, k, ni, nj, nk, index, allElements, element, elements, heads;
	var allPages = $$('.kcElements');
	if (allPages && allPages.length > 0) {
	  for (i = 0, n = allPages.length; i < n; i++) {
	    allElements = allPages[i];
	    heads = allElements.getElementsBySelector('.kcHeading');
	    if (heads && heads.length > 0) {
	      for (j = 0, nj = heads.length; j < nj; j++) {
	        elements = heads[j].childElements();
	        if (elements && elements.length > 0) {
	          for (k = 0, index = -1, nk = elements.length; k < nk; k++) {
	            element = elements[k];
	            if (element.hasClassName('kcElement')) {
	              index++;
	            }
	            if ((index % 2) === 0) {
	              element.addClassName('kcEven');
	            }
	            else {
	              element.addClassName('kcOdd');
	            }
	          }
	        }
	      }
	    }
	    else {
	      elements = allElements.childElements();
	      if (elements && elements.length > 0) {
            for (k = 0, index = -1, nk = elements.length; k < nk; k++) {
              element = elements[k];
	          if (element.hasClassName('kcElement')) {
	            index++;
	          }
	          if ((index % 2) === 0) {
	            element.addClassName('kcEven');
	          }
	          else {
	            element.addClassName('kcOdd');
	          }
	        }	          
	      }
	    }
	  }
	}
  };
  
  // Initialize the object to be returned from the initializer passed in to this object, if any.
  if (initializer) {
    _id = initializer.id;
    object.formInstanceId = initializer.formInstanceId;
    object.formDefinitionId = initializer.formDefinitionId;
    object.domainObjectId = initializer.domainObjectId;
    object.domainObjectType = initializer.domainObjectType;
    if (initializer.elements) {
      object.elements = $A();
      for (var i = 0, n = initializer.elements.length; i < n; i++) {
        var e = GSBIO.kc.data.DataElement(initializer.elements[i]);
        e.setFormId(_id);
        object.elements.push(e);
        _elementMap[e.getId()] = e;
      }
    }
  }
  
  return object;
}; // GSBIO.kc.data.FormInstance

/**
 * All metadata that is needed for event handling and other functionality related to processing
 * data elements and ADE elements.
 */
GSBIO.kc.data.Metadata = function() {
  var object = {};

  object.cui;                 // the CUI of the element
  object.isMultivalued;       // whether this element is multivalued
  object.isHasAde;            // whether this element has an ADE
  object.isDatatypeCv;        // whether this element's datatype is CV
  object.isMultilevelValueSet // whether this element's value set is multilevel
  object.otherCui;            // the CUI of this element's other value
  object.noCui;               // the CUI of this element's no value
  object.seeNoteCui;          // the CUI of the 'See Note' standard value
  object.noteButtonAdd;       // the text of the note button for adding a note
  object.noteButtonDelete;    // the text of the note button for deleting a note
  object.imgExpand;           // the URL of the expand image
  object.imgCollapse;         // the URL of the collapse image
  object.adePopup;            // the URL of ADE popup
  object.standardVsValue;     // the value of the OPTION that is used to show the standard value set
  object.broadVsValue;        // the value of the OPTION that is used to show the broad value set
  object.broadVsLeafCount;    // the count of leaf values (i.e. those that can be selected) in the broad value set
  object.nonAtvVs;            // this element's non-ATV value set.  An array if defined.

  return object;
}; // GSBIO.kc.data.Metadata

/**
 * The unique HTML ids of HTML input controls and other HTML elements of concern that are needed 
 * for event handling and other functionality related to processing data elements and ADE elements.
 */
GSBIO.kc.data.HtmlIds = function() {
  var object = {};

  /**
   * The id of the HTML container of all of the HTML elements for the element.
   */ 
  object.container;
  
  /**
   * The id of the primary HTML input control.  If the primary input control is actually multiple 
   * input controls with the same name but each with a unique id (e.g. radio buttons or 
   * checkboxes), this id is the id of any of the primary controls, and is generally just used to 
   * obtain the name.
   */
  object.primary;
  
  /**
   * The id of the standard HTML input control.  Since the standard input control is actually 
   * multiple radio buttons with the same name but each with a unique id, this id is the id of any 
   * of the standard controls, and is generally just used to obtain the name.
   */
  object.std;

  /**
   * The ids of the HTML elements and input controls used to obtain the 'other' value.
   * otherContainer is the id of the container wrapped around all of the other elements.
   * other is the id of the control that contains the actual other value(s).
   * otherText is the input that the user types an 'other' value into for multivalued data 
   * elements.
   * otherAdd and otherRemove are the ids of the buttons used to add and remove 'other' 
   * value from otherText to other for multivalued data elements.
   */
  object.otherContainer;
  object.other;
  object.otherText;
  object.otherAdd;
  object.otherRemove;
  
  /**
   * The ids of the HTML elements and input controls used to obtain the note value.
   * noteContainer is the id of the container wrapped around all of the note elements.
   * note is the id of the control that holds the note value itself.
   * noteButton is the id of the Add/Delete note button.
   */
  object.noteContainer;
  object.note;
  object.noteButton;

  /**
   * The ids of the HTML elements and input controls related to ADEs.
   * adeContainer is the id of the container wrapped around all of the ADE elements or ADE
   * summary table.
   * adeTable is the id of the ADE summary table that is used for multivalued data elements
   * whose actual ADE values are entered in the ADE popup.
   * adeLink is the id of the link that is used to display the ADE popup for multivalued data 
   * elements to add a new data element value along with its ADE values.
   * adeEdit is an array of ids of Edit buttons next to rows in the ADE summary table.
   * adeDelete is an array of ids of Delete buttons next to rows in the ADE summary table.
   */
  object.adeContainer;
  object.adeTable;
  object.adeLink;
  object.adeEdit;
  object.adeDelete;

  /**  
   * The ids of the HTML elements and input controls related to narrow, standard and broad value
   * sets.  Note that all defined value sets will be rendered, with only one ever being visible.
   * *ValueSetContainer is the id of the container wrapped around a value set.
   * *ValueSet is the id of the primary control of the value set itself.
   * standardLink is the id of the link to show the standard value set when the narrow value
   * set is defined and rendered as a set of radio buttons or checkboxes.  When the value set
   * is rendered as a SELECT element, an OPTION is used to show the standard value set.
   * broadLinks is the id(s) of the link(s) to show the broad value set when the narrow and/or
   * standard value sets are defined and rendered as a set of radio buttons or checkboxes.  There
   * may be 2 broad links, since there will be one in both the narrow and standard value sets if
   * both are defined, and thus broadLinks will be an array if defined.  When the value sets are 
   * rendered as a SELECT element, an OPTION is used to show the broad value set.
   */
  object.narrowValueSetContainer;
  object.narrowValueSet;
  object.standardValueSetContainer;
  object.standardValueSet;
  object.standardLink;
  object.broadValueSetContainer;
  object.broadValueSet;
  object.broadLinks;

  return object;
}; // GSBIO.kc.data.HtmlIds

/**
 * An abstract base class that represents a single element.  This class has methods that are
 * common to both data elements and ADE elements.  Many of the methods are intended to have
 * 'protected' visibility, and are marked as such, even though they are necessarily publicly 
 * accessible.
 * 
 * @abstract
 */
GSBIO.kc.data.Element = function(initializer) {
  var object = {};

  //-----------------------------
  //----- PRIVATE VARIABLES -----
  //-----------------------------  

  // An object that holds the metadata for the element.
  var _meta;

  // An object that holds the unique HTML ids of HTML input controls and other elements of concern.
  var _htmlIds;


  //---------------------------
  //----- PRIVATE METHODS -----
  //---------------------------
  

  //----------------------------
  //----- PUBLIC VARIABLES -----
  //----------------------------  

  // The system name of the element, in its canonical form as specified in the DET.  We specify
  // it as a public property instead of in the metadata since we want it to be part of the
  // serialized JSON string that represents this object.
  object.systemName = null;
  
  // The values of the element.  This is an array of either GSBIO.kc.data.DataElementValue 
  // or GSBIO.kc.data.AdeElementValue objects.
  object.values = null;


  //--------------------------
  //----- PUBLIC METHODS -----
  //--------------------------

  /**
   * Returns the metadata object for this object.
   *
   * @return the GSBIO.kc.data.Metadata object
   * @protected
   */
  object.getMeta = function() {
    return _meta;
  };

  /**
   * Returns the HTML ids object for this object.
   *
   * @return the GSBIO.kc.data.HtmlIds object
   * @protected
   */
  object.getHtmlIds = function() {
    if (!_htmlIds) {
      _htmlIds = {};
    }
    return _htmlIds;
  };
  
  /**
   * Sets HTML ids for this object.  If HTML ids have already been set this method will only 
   * replace those defined in the input parameter, and will not affect any other HTML ids.
   *
   * @param htmlIds a GSBIO.kc.data.HtmlIds object
   */
  object.setHtmlIds = function(htmlIds) {
    if (_htmlIds) {
      _htmlIds.container = htmlIds.container || _htmlIds.container;
      _htmlIds.primary = htmlIds.primary || _htmlIds.primary;
      _htmlIds.std = htmlIds.std || _htmlIds.std;
      _htmlIds.otherContainer = htmlIds.otherContainer || _htmlIds.otherContainer;
      _htmlIds.other = htmlIds.other || _htmlIds.other;
      _htmlIds.otherText = htmlIds.otherText || _htmlIds.otherText;
      _htmlIds.otherAdd = htmlIds.otherAdd || _htmlIds.otherAdd;
      _htmlIds.otherRemove = htmlIds.otherRemove || _htmlIds.otherRemove;
      _htmlIds.noteContainer = htmlIds.noteContainer || _htmlIds.noteContainer;
      _htmlIds.note = htmlIds.note || _htmlIds.note;
      _htmlIds.noteButton = htmlIds.noteButton || _htmlIds.noteButton;
      _htmlIds.adeContainer = htmlIds.adeContainer || _htmlIds.adeContainer;
      _htmlIds.adeTable = htmlIds.adeTable || _htmlIds.adeTable;
      _htmlIds.adeLink = htmlIds.adeLink || _htmlIds.adeLink;
      _htmlIds.adeEdit = htmlIds.adeEdit || _htmlIds.adeEdit;
      _htmlIds.adeDelete = htmlIds.adeDelete || _htmlIds.adeDelete;
      _htmlIds.narrowValueSetContainer = htmlIds.narrowValueSetContainer || _htmlIds.narrowValueSetContainer;
      _htmlIds.narrowValueSet = htmlIds.narrowValueSet || _htmlIds.narrowValueSet;
      _htmlIds.standardValueSetContainer = htmlIds.standardValueSetContainer || _htmlIds.standardValueSetContainer;
      _htmlIds.standardValueSet = htmlIds.standardValueSet || _htmlIds.standardValueSet;
      _htmlIds.standardLink = htmlIds.standardLink || _htmlIds.standardLink;
      _htmlIds.broadValueSetContainer = htmlIds.broadValueSetContainer || _htmlIds.broadValueSetContainer;
      _htmlIds.broadValueSet = htmlIds.broadValueSet || _htmlIds.broadValueSet;
      _htmlIds.broadLinks = htmlIds.broadLinks || _htmlIds.broadLinks;      
    }
    else {
      _htmlIds = htmlIds;
    }
  };

  /**
   * Returns the primary value of this element from its input control(s).  This method is intended 
   * for singlevalued elements.
   *
   * @return the primary value as a string
   * @protected
   */
  object.getPrimaryValue = function() {
    if (_meta.isMultivalued) {
      alert("getPrimaryValue() called on multivalued element!");
      return;
    }
    var c = $(_htmlIds.primary);
    var tag = c.tagName.toLowerCase();
    var type = (tag == 'input') ? c.type.toLowerCase() : null;
    if ((tag == 'input' && type == 'text') || (tag == 'textarea') || (tag == 'select' && !c.multiple)) {
      var v = c.getValue();
      return v ? v.strip() : v;
    }
    else if (tag == 'input' && type == 'radio') {
      return c.getRadiosValue();
    }
    else {
      alert("Cannot determine value in getPrimaryValue()!");
    }
  };

  /**
   * Returns the primary values of this element from its input control(s).  This method is intended 
   * for multivalued elements.
   *
   * @return the primary values as an array of strings
   * @protected
   */
  object.getPrimaryValues = function() {
    if (!_meta.isMultivalued) {
      alert("getPrimaryValues() called on singlevalued element!");
      return;
    }
    var c = $(_htmlIds.primary);
    var tag = c.tagName.toLowerCase();
    var type = (tag == 'input') ? c.type.toLowerCase() : null;
    if (tag == 'input' && type == 'checkbox') {
      return c.getCheckboxesValues();
    }
    else {
      alert("Cannot determine value in getPrimaryValues()!");
    }
  };

  /**
   * Sets the specified value in this element's primary value input control(s).  If value is falsy, 
   * clears the primary value input control(s).  This method is intended for singlevalued elements.
   *
   * @param  value  the primary value to set as a string
   * @protected
   */
  object.setPrimaryValue = function(value) {
    if (_meta.isMultivalued) {
      alert("setPrimaryValue() called on multivalued element!");
      return;
    }
    if (value) {
      var c = $(_htmlIds.primary);
      var tag = c.tagName.toLowerCase();
      var type = (tag == 'input') ? c.type.toLowerCase() : null;
      if ((tag == 'input' && type == 'text') || (tag == 'textarea') || (tag == 'select' && !c.multiple)) {
        c.value = value;
      }
      else if (tag == 'input' && type == 'radio') {
        c.setRadiosValue(value);
      }
      else {
        alert("Cannot set value in setPrimaryValue()!");
      }
    }
    else {
      this.clearPrimaryValues();
    }
  };

  /**
   * Sets the specified values in this element's primary value input control(s).  If value is falsy, 
   * clears the primary value input control(s).  This method is intended for multivalued elements.
   *
   * @param  value  the primary values to set as an array of strings
   * @protected
   */
  object.setPrimaryValues = function(values) {
    if (!_meta.isMultivalued) {
      alert("setPrimaryValues() called on singlevalued element!");
      return;
    }
    if (values) {
      var c = $(_htmlIds.primary);
      var tag = c.tagName.toLowerCase();
      var type = (tag == 'input') ? c.type.toLowerCase() : null;
      if (tag == 'input' && type == 'checkbox') {
        c.setCheckboxesValues(values);
      }
      else {
        alert("Cannot set values in setPrimaryValues()!");
      }
    }
    else {
      this.clearPrimaryValues();
    }
  };

  /**
   * Clears the primary value input control(s) of this element.  This method will work for both 
   * singlevalued and multivalued elements.
   *
   * @protected
   */
  object.clearPrimaryValues = function() {
    var c = $(_htmlIds.primary);
    if (c) {
      var tag = c.tagName.toLowerCase();
      var type = (tag == 'input') ? c.type.toLowerCase() : null;
      if ((tag == 'input' && type == 'text') || (tag == 'textarea')) {
        c.clear();
      }
      else if (tag == 'select') {
        c.selectedIndex = 0;
      }
      else if (tag == 'input' && type == 'radio') {
        c.clearRadios();
      }
      else if (tag == 'input' && type == 'checkbox') {
        c.clearCheckboxes();
      }
      else {
        alert("Cannot clear values in clearPrimaryValues()!");
      }
    }
  };

  /**
   * Selects the specified values of this element in its primary value input control(s).  This 
   * method is intended for multivalued elements.
   *
   * @param values the values to select
   * @protected
   */
  object.selectPrimaryValues = function(values) {
    var i, a, n;
    if (!_meta.isMultivalued) {
      alert("selectPrimaryValues() called on singlevalued element!");
      return;
    }
    if (values && values.length > 0) {
      a = document.getElementsByName($(_htmlIds.primary).name);
      for (i = 0, n = a.length; i < n; i++) {
        a[i].checked = (values.indexOf(a[i].value) != -1);
      }
    }
  };

  /**
   * Expands and collapses nodes of a multilevel value set as necessary to ensure that the
   * parent node of each selected value is expanded and all others are collapsed.  This method
   * currently works based on the shape of the HTML, so if the HTML is changed then this
   * method will have to be changed appropriately.  This method will work for both singlevalued
   * and multivalued elements that have a multilevel value set.
   *
   * @protected
   */
  object.expandCollapsePrimaryValues = function() {
    var i, j, ni, nj;
    var table, trs, tr, img;
    var subtable, subtrs, expand;

    // First find the div that contains the value set that is currently visible.
    var divId;
    if (_htmlIds.primary == _htmlIds.narrowValueSet) {
      divId = _htmlIds.narrowValueSetContainer;
    }
    else if (_htmlIds.primary == _htmlIds.standardValueSet) {
      divId = _htmlIds.standardValueSetContainer;
    }
    else if (_htmlIds.primary == _htmlIds.broadValueSet) {
      divId = _htmlIds.broadValueSetContainer;
    }
    if (!divId) {
      alert('Could not determine current value set in expandCollapsePrimaryValues()');
      return;
    }

    // Each value set DIV of a multilevel value set holds a single outer table that contains
    // the first level of values, so get the table and its collection of rows.
    table = $(divId).down();
    trs = table.rows;
    
    // Each row contains a single cell, each with a single child.  If the cell's child is an
    // image, then it is a collapsable node that controls the next row in the table.
    for (i = 0, ni = trs.length; i < ni; i++) {
      img = $(trs[i].cells[0]).down();
      if (img.tagName.toLowerCase() == 'img') {
        tr = $(trs[++i]); // the row to show/hide
        
        // The row to show/hide has a table, where each row contains a single cell that contains
        // either a checkbox or radio button input.  Check the input in each row to determine
        // whether any inputs are checked.
        subtable = $(tr.cells[0]).down();
        subtrs = subtable.rows;
        expand = false;
        for (j = 0, nj = subtrs.length; j < nj; j++) {
          if ($(subtrs[j]).down().down().checked) {
            expand = true;
            break;
          }
        }
        
        // If at least one input is checked, then show the row, else hide it.
        if (expand) {
          tr.show();
          img.src = _meta.imgCollapse;
        }
        else {
          tr.hide();
          img.src = _meta.imgExpand;
        }
      }
    }
  };

  /**
   * Returns the other value of this element from its input control(s).  This method is intended 
   * for singlevalued elements.
   *
   * @return the other value as a string, or null if the other control is not present or disabled
   * @protected
   */
  object.getOtherValue = function() {
    if (_meta.isMultivalued) {
      alert("getOtherValue() called on multivalued element!");
      return;
    }
    return (_htmlIds.other && !$(_htmlIds.other).disabled) ? $F(_htmlIds.other) : null;
  };

  /**
   * Returns the other values of this element from its input control(s).  This method is intended 
   * for multivalued elements.
   *
   * @return the other values as an array of strings, or null if the other control is not present 
   *         or disabled
   * @protected
   */
  object.getOtherValues = function() {
    if (_meta.isSinglevalued) {
      alert("getOtherValues() called on singlevalued element!");
      return;
    }
    if (_htmlIds.other && !$(_htmlIds.other).disabled) {
      var opts = $(_htmlIds.other).options;
      if (opts.length > 0) {
        var a = [];
        for (var i = 0, n = opts.length; i < n; i++) {
          a.push(opts[i].value);
        }
        return a;
      }
      else {
        return null;
      }
    }
    else {
      return null;
    }
  };

  /**
   * Sets the specified value in this element's other value input control.  If value is falsy, 
   * clears the other value input control.  This method is intended for singlevalued elements.
   *
   * @param value the other value to set as a string
   * @protected
   */
  object.setOtherValue = function(value) {
    if (value) {
      if (_htmlIds.other) {
        $(_htmlIds.other).value = value;
      }
    }
    else {
      this.clearOtherValues();
    }
  };

  /**
   * Sets the specified values in this element's other value input control.  If value is falsy, 
   * clears the other value input control.  This method is intended for multivalued elements.
   *
   * @param values the other values to set as an array of strings
   * @protected
   */
  object.setOtherValues = function(values) {
    var i, c, n;
    if (values) {
      if (_htmlIds.other) {
        c = $(_htmlIds.other);
        for (i = 0, n = values.length; i < n; i++) {
          if (values[i]) {
            c.addOption(values[i], values[i]);
          }
        }
      }
    }
    else {
      this.clearOtherValues();
    }
  };

  /**
   * Clears the value(s) in this element's other value input control.  This method can be used for
   * both singlevalued and multivalued elements.
   *
   * @protected
   */
  object.clearOtherValues = function() {
    var i, c;
    if (_htmlIds.other) {
      if (_meta.isMultivalued) {
        c = $(_htmlIds.other);
        for (i = c.length - 1; i >= 0; i--) {
          c.remove(i);
        }
      }
      else {
        $(_htmlIds.other).clear();
      }
    }
    if (_htmlIds.otherText) {
      $(_htmlIds.otherText).clear();
    }
  };

  /**
   * Shows the other value input control(s).  This method can be used for both singlevalued and 
   * multivalued elements.
   *
   * @protected
   */
  object.showOther = function() {
    if (_htmlIds.otherContainer) {
      $(_htmlIds.otherContainer).show();
    }
  };

  /**
   * Hides the other value input control(s).  This method can be used for both singlevalued and 
   * multivalued elements.
   *
   * @protected
   */
  object.hideOther = function() {
    if (_htmlIds.otherContainer) {
      $(_htmlIds.otherContainer).hide();
    }
  };

  /**
   * Enables the other value input control(s).  This method can be used for both singlevalued and 
   * multivalued elements.
   *
   * @protected
   */
  object.enableOther = function() {
    if (_htmlIds.other) {
      if (_meta.isMultivalued) {
        $(_htmlIds.otherText).enable();
        $(_htmlIds.otherAdd).enable();
        $(_htmlIds.otherRemove).enable();
      }
      else if ($(_htmlIds.other).value == 'N/A') {
        $(_htmlIds.other).clear();
      }
      $(_htmlIds.other).enable();
    }
  };

  /**
   * Disables the other value input control(s).  This method can be used for both singlevalued and 
   * multivalued elements.
   *
   * @protected
   */
  object.disableOther = function() {
    if (_htmlIds.other) {
      if (_meta.isMultivalued) {
        $(_htmlIds.otherText).disable();
        $(_htmlIds.otherAdd).disable();
        $(_htmlIds.otherRemove).disable();
      }
      else {
        $(_htmlIds.other).value = 'N/A';
      }
      $(_htmlIds.other).disable();
    }
  };
  

  /**
   * Moves an other value from its input control to the multi-select control that holds all other
   * values.  This method is intended for multivalued elements.
   *
   * @protected
   */
  object.addOtherMulti = function() {
    var dup, sel, opts;
    var v = $F(_htmlIds.otherText);
    if (!v.blank()) {
      v = v.strip();
      dup = false;
      sel = $(_htmlIds.other);
      opts = sel.options;
      if (opts) {
        for (var i = 0, n = opts.length; i < n; i++) {
          if (opts[i].value == v) {
            dup = true;
          }
        }
      }
      if (dup) {
        alert("'" + v + "' has already been added as an other value.");
      }
      else {
        $(_htmlIds.other).addOption(v, v);
      }
    }
    $(_htmlIds.otherText).clear();
    $(_htmlIds.otherText).focus();
  };

  /**
   * Removes all selected other values from the multi-select control that holds all other values.
   * This method is intended for multivalued elements.
   *
   * @protected
   */
  object.removeOtherMulti = function() {
    var sel = $(_htmlIds.other);
    var opts = sel.options;
    if (opts) {
      for (var i = opts.length - 1; i >= 0; i--) {
        if (opts[i].selected) {
          sel.remove(i);
        }
      }
    }
  };

  /**
   * Selects only the NOVAL in the primary value input controls.  This method is intended for 
   * multivalued elements.
   *
   * @protected
   */
  object.selectOnlyNoval = function() {
    var i, a, n;
    $(_htmlIds.primary).clearCheckboxes();
    a = document.getElementsByName($(_htmlIds.primary).name);
    for (i = 0, n = a.length; i < n; i++) {
      if (a[i].value == _meta.noCui) {
        $(a[i]).checked = true;
        break;
      }
    }
  };

  /**
   * Deselects the NOVAL in the primary value input controls.  This method is intended for 
   * multivalued elements.
   *
   * @protected
   */
  object.deselectNoval = function() {
    var i, a, n;
    a = document.getElementsByName($(_htmlIds.primary).name);
    for (i = 0, n = a.length; i < n; i++) {
      if (a[i].value == _meta.noCui) {
        $(a[i]).checked = false;
        break;
      }
    }
  };

  /**
   * Called by the event handler to deal with an expand/collapse image in a multilevel value set
   * being clicked.  We'll toggle the state of the expansion.  Note that we are relying upon the 
   * shape of the HTML, so if we change how multi-level value sets are rendered we'll have to 
   * change this code as well.  The HTML we are expectin is an IMG (in a TD) in a TR, which is in 
   * a DIV that is the value set container.  If we find this structure, then the node to 
   * toggle is the next sibling TR of the TR that contains the IMG.
   *
   * @param thisObj the element object bound to the event handler
   * @param source the source of the event, which is an expand/collapse image
   * @protected
   */
  object.handleExpandCollapseValueSetEvent = function(thisObj, source) {
    // Find the ancestor TR and DIV, if any.
    var i, n, e, tr, div;
    var h = thisObj.getHtmlIds();
    var m = thisObj.getMeta();
    var a = source.ancestors();
    
    for (i = 0, n = a.length; i < n; i++) {
      e = a[i];
      if (e.tagName.toLowerCase() == "tr") {
        tr = e;
      }
      if (e.tagName.toLowerCase() == "div") {
        div = e;
        break;
      }
    }
      
    // If there is an ancestor DIV that is one of the narrow, standard or broad value set
    // containers, then the user clicked on the expand/collapse image so we should toggle
    // the expand/collapse state.
    if (div && (div.id == h.narrowValueSetContainer || div.id == h.standardValueSetContainer || div.id == h.broadValueSetContainer)) {
      tr = tr.next();
      if (tr.visible()) {
        tr.hide();
        source.src = m.imgExpand;
      }
      else {
        tr.show();
        source.src = m.imgCollapse;
      }
    }
  };

  /**
   * Adds a value to this element.
   *
   * @param value either a GSBIO.kc.data.DataElementValue or GSBIO.kc.data.AdeElementValue
   */
  object.addValue = function(value) {
    if (!this.values) {
      this.values = $A();
    }
    this.values.push(value);
  };

  /**
   * Returns the value with the specified index in the collection of values for this element.
   *
   * @param index the index
   * @return the value, which is either a GSBIO.kc.data.DataElementValue or 
   *         GSBIO.kc.data.AdeElementValue, or null if no such value exists
   */
  object.getValue = function(index) {
    return this.values ? (index < this.values.length ? this.values[index] : null) : null;
  };

  // Initialize the object to be returned from the initializer passed in to this object, if any.
  if (initializer) {
    if (initializer.metadata) {
      _meta = initializer.metadata;
    }
    object.systemName = initializer.systemName;
  }

  return object;
}; // GSBIO.kc.data.Element 

/**
 * Represents a single data element.  This is a rather large object that handles all data element
 * event handling and converting all of the data element's values to a JSON string.
 * 
 * A single data element is relatively complex and thus rendered in HTML using a number of input 
 * controls and other HTML elements.  These HTML elements capture the primary value, which is
 * considered the main value(s) (e.g. the value selected from a dropdown or a number entered
 * for a numeric input), the standard value (one of 'Not Sought', 'Not Reported' or 'See Note'),
 * the optional note and the values of the optional ADE elements.  All of these HTML elements
 * are wrapped in an HTML DIV known as the container.
 *
 * Most of the methods are concerned with event handling.  A single method, handleEvent(), 
 * handles all events of concern.  The registerEventHandler method registers handleEvent() as
 * the event handler on the container DIV, and event bubbling is relied upon to bubble the events 
 * up to the container DIV.  The general structure of handleEvent() is to first determine the 
 * actual source and type of event, and then handle the event if appropriate by calling private
 * methods to do so.  These private event handling methods comprise the majority of code in
 * this object.
 */
GSBIO.kc.data.DataElement = function(initializer) {
  var object = GSBIO.kc.data.Element(initializer);
  
  //-----------------------------
  //----- PRIVATE VARIABLES -----
  //-----------------------------
  
  // A flag indicating whether this data element's values have changed.
  var _changed = false;  

  // The unique id of this object.  It is useful for uniquely identifying this data element within 
  // its FormInstance, for example.  It should be treated as a randomly generated unique id with 
  // no meaning.
  var _id;
  
  // The id of the GSBIO.kc.data.FormInstance that contains this data element.
  var _formId;

  
  //---------------------------
  //----- PRIVATE METHODS -----
  //---------------------------
  
  /**
   * Called by the event handler to deal with events on the primary control(s).
   *
   * @param source the source of the event, which is one of the primary input controls
   */
  function handlePrimaryControlEvent(thisObj, source) {
    var primaryValue, primaryValues;
    var m = thisObj.getMeta();
    
    // The data element is multivalued.  In this situation there is not much to do since there
    // is no ADE to show/hide (since a multivalued data element with an ADE is handled via the
    // ADE popup), and since others are always generally enabled (i.e. you don't have to select
    // the 'other' value to enable the other text box as in a singlevalued data element).  The
    // main work revolves around whether the data element has a NOVAL and if so whether the
    // NOVAL was just selected or not, since the NOVAL is mutually exclusive with all values,
    // including others.
    // What we need to do is as follows:
    //   - If there are any primary values selected, clear the standard values.
    //   - If the primary value just selected is the noval, then we must deselect all values 
    //     except the noval, and also clear all others and disable the other controls since no 
    //     values except the noval can be selected.  
    //   - If the primary value just selected is not the noval, then we must deselect the noval
    //     and enable the other controls.
    //   - If no primary values are selected, then enable the other controls.  This must be done
    //     to handle the case where the user selects the NOVAL, which disabled the other controls,
    //     then deselects the NOVAL.  In this case there will be no primary value selected, but
    //     other should be enabled since the NOVAL is now not selected.
    if (m.isMultivalued) {
      primaryValues = thisObj.getPrimaryValues();
      if (primaryValues && primaryValues.size() > 0) {
        _changed = true;
        thisObj.clearStandardValues();
        if (m.noCui) {
          if ($F(source) == m.noCui) {
            thisObj.selectOnlyNoval();
            thisObj.clearOtherValues();
            thisObj.disableOther();
          }
          else {
            thisObj.deselectNoval();
            thisObj.enableOther();
          }
        }
      }
      else {
        thisObj.enableOther();
      }
    }

    // The data element is singlvalued.
    else {

      // There is a primary value entered/selected.
      primaryValue = thisObj.getPrimaryValue();
      if (primaryValue) {
        _changed = true;
        
        // The data element is a CV data element, and the actions we take are more extensive
        // for CV data elements than all others
        if (m.isDatatypeCv) {
          // The standard value set item was selected (i.e. 'Show additional values'), so show
          // the standard value set.  In addition, make sure there is no value selected, since
          // for a singlevalued data element we want to force the user to look through all of
          // the new values that were just made visible.  Also clear the primaryValue so the
          // code below will do the correct thing when there is no primary value.
          if (primaryValue == m.standardVsValue) {
            thisObj.showStandardValueSet();
            thisObj.clearPrimaryValues();
            primaryValue = null;
          }

          // The broad value set item was selected (i.e. 'Show all values'), so show the broad
          // value set.  In addition, make sure there is no value selected, since for a 
          // singlevalued data element we want to force the user to look through all of the new 
          // values that were just made visible.  Also, show the other control since the user
          // has now seen all of the values, and clear the primaryValue so the code below will
          // do the correct thing when there is no primary value.
          else if (primaryValue == m.broadVsValue) {
            thisObj.showBroadValueSet();
            thisObj.clearPrimaryValues();
            thisObj.showOther();
            primaryValue = null;
          }
            
          // A real value was selected, so make sure that the standard value are cleared.
          else {
            thisObj.clearStandardValues();
          }
            
          // If the value selected was the 'other' value, then enable the other text control,
          // otherwise disable the other text control.
          if (primaryValue == m.otherCui) {
            thisObj.showOther();  // note: should already be showing but doesn't hurt to make this call
            thisObj.enableOther();
          }
          else {
            thisObj.disableOther();
          }
            
          // If the value selected was an ADE trigger value, then show the ADE, otherwise hide
          // the ADE and clear the values of its elements.
          if (thisObj.isAtvValue(primaryValue)) {
            thisObj.showAde();
          }
          else {
            thisObj.hideAde();            
            thisObj.clearAdeValues();
          }
        }
          
        // For all non-CV data elements, we need to clear the standard values and show the ADE,
        // since any value entered by the user for a non-CV data element is considered an ADE
        // trigger value.
        else {
          thisObj.clearStandardValues();
          thisObj.showAde();
        }
      }
        
      // If there is no primary value entered/selected, then we must hide and clear the ADE, if 
      // any, since the lack of a value is not considered an ADE trigger value, and we must also
      // make sure that other is disabled, since the 'other' value is not selected.
      if (!primaryValue) {
        thisObj.hideAde();            
        thisObj.clearAdeValues();
        thisObj.disableOther();
      }
    }
  }
  
  /**
   * Called by the event handler to deal with events on the standard value control(s).  In general 
   * we want to clear the primary value(s), which includes clearing other values and ADE element 
   * values.  In addition, if 'See Note' was clicked we want to enable the note.
   */
  function handleStdControlEvent(thisObj) {
    var m = thisObj.getMeta();
    var v = thisObj.getStandardValue();
    if (v) {
      _changed = true;
    
      // Hide the ADE and clear all of the ADE values since a standard value is not considered
      // an ATV value.  The clearAdeValues() method will deal with the differences between single 
      // and multivalued data elements, and both will quietly do nothing if there is no ADE.
      thisObj.hideAde();
      thisObj.clearAdeValues();
      
      // If this is a multivalued data element, clear all of the others and make sure the controls
      // for others are enabled.  They will have been disabled if the NOVAL was selected.  For
      // a singlevalued data element, disable the other control.
      if (m.isMultivalued) {
        thisObj.clearOtherValues();
        thisObj.enableOther();
      }
      else {
        thisObj.disableOther();
      }

      // Clear the primary value(s).
      thisObj.clearPrimaryValues();
      
      // If 'See Note' was selected, then show the note input.
      if (v == m.seeNoteCui) {
        thisObj.showNote();
      }
    }
  }

  /**
   * Called by the event handler to deal with the note button being clicked.  In general we want to 
   * toggle the visibility of the note and text of the note button.  The only exception is that if 
   * the note is already visible and the user has selected the 'See Note' standard value, then 
   * we'll just clear the note and leave it visible, since the user has to enter a note.
   */
  function handleNoteButtonEvent(thisObj) {
    _changed = true;
    if ($(thisObj.getHtmlIds().noteContainer).visible()) {
      if (thisObj.getStandardValue() == thisObj.getMeta().seeNoteCui) {
        thisObj.clearNote();
      }
      else {
        thisObj.hideNote();
      }
    }
    else {
      thisObj.showNote();
    }
  }

  /**
   * Called by the event handler to deal with the link to show the standard value set being 
   * clicked.  We'll show the standard value set, and in addition, for a singlevalued data element 
   * we'll make sure there is no value selected, since for a singlevalued data element we want to 
   * force the user to look through all of the new values that were just made visible.  For a 
   * multivalued data element, retain any values that were selected in the narrow value set.
   */
  function handleStandardLinkEvent(thisObj) {
    if (thisObj.getMeta().isMultivalued) {
      var selectedValues = thisObj.getPrimaryValues();
      thisObj.showStandardValueSet();
      thisObj.selectPrimaryValues(selectedValues);
    }
    else {
      thisObj.showStandardValueSet();
      thisObj.clearPrimaryValues();
    }

    // We want to expand/collapse in both situations (singlevalued and multivalued data elements).
    // The multivalued case is obvious since we kept the selections from the previous value set.
    // In the singlevalued case, we may have generated the HTML on the server with a value
    // selected, and since we clear the value above we need to make sure the parent is collapsed.
    thisObj.expandCollapsePrimaryValues();
  }

  /**
   * Called by the event handler to deal with a link to show the broad value set being clicked.
   * We'll show the broad value set, and in addition, for a singlevalued data element we'll make 
   * sure there is no value selected, since for a singlevalued data element we want to force the 
   * user to look through all of the new values that were just made visible.  For a multivalued 
   * data element, retain any values that were selected in the narrow value set.  Also, show the 
   * other  control since the user has now seen all of the values.
   */
  function handleBroadLinkEvent(thisObj) {
    if (thisObj.getMeta().isMultivalued) {
      var selectedValues = thisObj.getPrimaryValues();
      thisObj.showBroadValueSet();
      thisObj.selectPrimaryValues(selectedValues);
    }
    else {
      thisObj.showBroadValueSet();
      thisObj.clearPrimaryValues();
    }
    thisObj.showOther();

    // We want to expand/collapse in both situations (singlevalued and multivalued data elements).
    // The multivalued case is obvious since we kept the selections from the previous value set.
    // In the singlevalued case, we may have generated the HTML on the server with a value
    // selected, and since we clear the value above we need to make sure the parent is collapsed.
    thisObj.expandCollapsePrimaryValues();
  }
  
  /**
   * Called by the event handler to deal with the ADE link to add a new data element value and
   * its ADE values being clicked.  We'll call the ADE popup and then add a row to the ADE summary
   * table if the user did not cancel the popup.
   */
  function handleAdeLinkEvent(thisObj) {
    var i, n, a, r, c, ret, exclude, url, html, editId, deleteId;
    var args, dataElement, value;
    var h = thisObj.getHtmlIds();
    var m = thisObj.getMeta();
	var v = thisObj.values;
    
    // If all of the values have been selected, then inform the user that there are no more values
    // to select instead of showing the ADE popup.  Only do this if the data element does not have 
    // an other value, since the user can select as many others as desired.
    if (!m.otherCui && v && v.length == m.broadVsLeafCount) {
      alert("There are no more values to select for this data element.");
      return;
    }
    
	// Create a parameter list for the URL for the ADE popup.  Add the system name of the
	// data element and the form definition id.
	var p = $H();
	p.dataElementSystemName = thisObj.systemName;
    p.formDefinitionId = GSBIO.kc.data.FormInstances.getFormInstance(_formId).formDefinitionId;

	// Append the list of CUIs of values to exclude.  Never include the other CUI as an excluded
	// CUI since the user can enter as many others as desired.
    if (m.isDatatypeCv && v) {
	  exclude = [];
      for (i = 0, n = v.length; i < n; i++) {
        if (v[i].value != m.otherCui) {
          exclude.push(v[i].value);
        }
      }
      if (exclude.length > 0) {
        p.excludedValues = exclude;
      }
    }

    // Build the argument for the popup, which is our ARTS object, which the popup will use to 
    // merge its ARTS concepts into.
    args = [GSBIO.ARTS];

	// Build the URL and use it to show the modal popup.  If the user does not cancel the popup
	// a GSBIO.kc.data.DataElement will be returned with a new value for this data element and 
	// values that were entered for all of the value's ADE elements.
	url = m.adePopup + '?' + p.toQueryString();
	ret = window.showModalDialog(url, args, "dialogWidth:700px;dialogHeight:700px;");
	if (ret) {
	  _changed = true;
	  dataElement = ret.evalJSON();
      value = dataElement.values[0];
      a = $(h.adeTable);

      // If there is only a single row in the ADE table then it is the heading row, and
      // therefore this is the first data element value to be added.  In this case, clear
      // any existing values of this object.  One situation in which this object might 
      // contain a value is when the saved value is one of the system standard values.
      if (a.rows.length == 1) {
	    thisObj.values = null;
      }
      
      // If either the user selected the NOVAL, or the user selected a value other than the 
 	  // NOVAL and the current value of this data element is the NOVAL, first delete all 
      // existing values from this object and delete all rows from the ADE summary table.  We 
      // do this since the NOVAL is mutually exclusive with all other values.  The first case 
	  // (user selected the NOVAL) is fairly obvious, and in the second case if the current value
	  // of this data element is the NOVAL it must be the first (and only) value, so in either
	  // case we can delete all values.  Note also that the first row of the ADE table is the 
	  // heading row, so don't delete that row.
	  if (m.noCui && ((m.noCui == value.value) || (thisObj.getValue(0) && m.noCui == thisObj.getValue(0).value))) {
	    thisObj.values = null;
	    h.adeEdit = null;
	    h.adeDelete = null;
 	    for (i = a.rows.length - 1; i > 0; i--) {
	      a.deleteRow(i);
	    }
      }

      // Add the value that the user entered to this data element.
	  v = GSBIO.kc.data.DataElementValue(value);
	  thisObj.addValue(v);
	    
	  // Add a new row to the ADE summary table, and add the value of the data element in the
	  // first cell.
	  r = a.insertRow(a.rows.length);
	  r.insertCell().innerHTML = thisObj.getDisplayForAdeSummaryTable(thisObj.values.length-1);

      // Add the value of each ADE element to subsequent cells.
	  if (v.ade && v.ade.elements) {
		for (i = 0, n = v.ade.elements.length; i < n; i++) {
		  r.insertCell().innerHTML = v.ade.elements[i].getDisplayForAdeSummaryTable();
		}
	  }

      // Add the Edit and Delete button in the last cell.  We'll generate their ids here and
      // add them to this object for proper event handling when they are clicked.
      editId = GSBIO.UniqueIdGenerator.getUniqueId();
      deleteId = GSBIO.UniqueIdGenerator.getUniqueId();
      thisObj.addAdeEditId(editId);
      thisObj.addAdeDeleteId(deleteId);
	  c = r.insertCell();
	  c.className = 'kcElementAdeButtons';
	  html = '<input id="';
	  html += editId;
	  html += '" type="button" class="kcButton" value="Edit"/>&nbsp;';
	  html += '<input id="';
	  html += deleteId;
	  html += '" type="button" class="kcButton" value="Delete"/>';
	  c.innerHTML = html;

      // Make sure the standard values are all cleared.
      thisObj.clearStandardValues();
		
	  // Make sure the ADE summary table is showing.
	  thisObj.showAde();
	}    
  }

  function handleAdeEditEvent(thisObj, editId) {
    var i, n, p, r, v, args, exclude, url, ret;
    var h = thisObj.getHtmlIds();
    var m = thisObj.getMeta();
    var value, index;
    var dataElement;
    
    // Find the value corresponding to the edit button that was clicked.  Note that we'll use
    // its index later to update the value and ADE summary row.
    for (index = 0, n = h.adeEdit.length; index < n; index++) {
      if (h.adeEdit[index] == editId) {
        value = thisObj.values[index];
        break;
      }
    }

	// Create a parameter list for the URL for the ADE popup.  Add the system name of the
	// data element and the form definition id.
	p = $H();
	p.dataElementSystemName = thisObj.systemName;
    p.formDefinitionId = GSBIO.kc.data.FormInstances.getFormInstance(_formId).formDefinitionId;

	// Append the list of CUIs of values to exclude.  Never include the other CUI as an excluded
	// CUI since the user can enter as many others as desired, and don't include the value
	// being edited since it needs to be selected.
	v = thisObj.values;
    if (m.isDatatypeCv && v) {
	  exclude = [];
      for (i = 0, n = v.length; i < n; i++) {
        if (v[i].value != m.otherCui && v[i].value != value.value) {
          exclude.push(v[i].value);
        }
      }
      if (exclude.length > 0) {
        p.excludedValues = exclude;
      }
    }

    // Build the argument for the popup, consisting of our ARTS object, which the popup will use to 
    // merge its ARTS concepts into, and the value to be edited as a JSON string.
    args = [GSBIO.ARTS, Object.toJSON(value)];

	// Build the URL and use it to show the modal popup.  If the user does not cancel the popup
	// a GSBIO.kc.data.DataElement will be returned with a new value for this data element and 
	// values that were entered for all of the value's ADE elements.
	url = m.adePopup + '?' + p.toQueryString();
	ret = window.showModalDialog(url, args, "dialogWidth:700px;dialogHeight:700px;");
	if (ret) {
      _changed = true;
	  newDataElement = ret.evalJSON();
	  if (newDataElement.values && newDataElement.values[0]) {
	    newDataElement = GSBIO.kc.data.DataElement(newDataElement);
	    newValue = newDataElement.values[0];

	    // Update the ADE summary table with the new values.
	    r = $(h.adeTable).rows[index+1]; // Add 1 to the index since the first table row is the header.
		r.cells[0].innerHTML = newDataElement.getDisplayForAdeSummaryTable(0);
        for (i = 0, n = newValue.ade.elements.length; i < n; i++) {
		  r.cells[i+1].innerHTML = newValue.ade.elements[i].getDisplayForAdeSummaryTable();
		}
	  }
	  
	  // Update the data element and ADE element values in this object.
	  thisObj.values[index] = GSBIO.kc.data.DataElementValue(newValue);
	  
	  // If the user changed the value to the NOVAL, then delete all other values except the
	  // NOVAL.  Note that the table row index is one greater than the array that holds the
      // value since the first row of the table is the header that does not correspond to a value.
	  if (m.noCui && (m.noCui == newValue.value)) {
        for (i = h.adeDelete.length - 1; i >= 0; i--) {
          if (i != index) {
            h.adeDelete[i] = null;
            h.adeEdit[i] = null;
            thisObj.values[i] = null;
            $(h.adeTable).deleteRow(i+1);
          }
        }
        h.adeDelete = h.adeDelete.compact();
        h.adeEdit = h.adeEdit.compact();
        thisObj.values = thisObj.values.compact();
	  }
	}

  }

  function handleAdeDeleteEvent(thisObj, deleteId) {
    _changed = true;
    var h = thisObj.getHtmlIds();

    // Delete the delete button id, edit button id and value from this object, and delete the
    // row from the HTML table.  Note that the table row index is one greater than the array
    // that holds the value since the first row of the table is the header that does not
    // correspond to a value.
    for (var i = 0, n = h.adeDelete.length; i < n; i++) {
      if (h.adeDelete[i] == deleteId) {
        h.adeDelete[i] = null;
        h.adeEdit[i] = null;
        thisObj.values[i] = null;
        $(h.adeTable).deleteRow(i+1);
        break;
      }
    }
    h.adeDelete = h.adeDelete.compact();
    h.adeEdit = h.adeEdit.compact();
    thisObj.values = thisObj.values.compact();

    // If we've deleted the last row, then hide the ADE summary table.
    if (h.adeDelete.length == 0) {
      thisObj.hideAde();
    }
  }

  //----------------------------
  //----- PUBLIC VARIABLES -----
  //----------------------------

  // The value of the data element's note.
  object.valueNote = null;


  //--------------------------
  //----- PUBLIC METHODS -----
  //--------------------------

  /**
   * Returns the unique id of this object.  It is useful for uniquely identifying this data element 
   * within its form instance, for example.  It should be treated as a randomly generated unique id 
   * with no meaning.  It must be set via the initializer to this object.
   */
  object.getId = function() {
    return _id;
  };

  /**
   * Sets the id of the form instance that contains this data element.
   */
  object.setFormId = function(id) {
    _formId = id;
  };

  /**
   * Returns the id of the form instance that contains this data element.
   */
  object.getFormId = function() {
    return _formId;
  };

  /**
   * @private
   */
  object.getStandardValue = function() {
    var id = this.getHtmlIds().std;
    if (id) {
      return $(id).getRadiosValue();
    }
  };

  /**
   * @private
   */
  object.clearStandardValues = function() {
    var id = this.getHtmlIds().std;
    if (id) {
      $(id).clearRadios();
    }
  };
  
  /**
   * @private
   */
  object.showNote = function() {
    var h = this.getHtmlIds();
    $(h.noteContainer).show();
    $(h.note).focus();
    $(h.noteButton).value = this.getMeta().noteButtonDelete;
  };

  /**
   * @private
   */
  object.hideNote = function() {
    var h = this.getHtmlIds();
    $(h.noteContainer).hide();
    $(h.noteButton).value = this.getMeta().noteButtonAdd;
    this.clearNote();
  };

  /**
   * @private
   */
  object.clearNote = function() {
    $(this.getHtmlIds().note).clear();
  };

  /**
   * @private
   */
  object.showAde = function() {
    var h = this.getHtmlIds();
    if (h.adeContainer) {
      $(h.adeContainer).show();
    }
  };

  /**
   * @private
   */
  object.hideAde = function() {
    var h = this.getHtmlIds();
    if (h.adeContainer) {
      $(h.adeContainer).hide();
    }
  };

  /**
   * @private
   */
  object.clearAdeValues = function() {
    var i, n, a, v;
    var m = this.getMeta();
    var h = this.getHtmlIds();
    if (h.adeContainer) {
    
      // If the data element is multivalued, then clear all of the rows of the ADE summary table,
      // except the first which is the header row.  We must also  delete all of the data element's 
      // values and ADE values since we keep the values in sync with the UI for a multivalued data 
      // element with an ADE.
      if (m.isMultivalued) {
        a = $(h.adeTable);
 	    for (i = a.rows.length - 1; i > 0; i--) {
	      a.deleteRow(i);
	    }
        this.values = null;
	    h.adeEdit = null;
	    h.adeDelete = null;
      }
      
      // If the data element is singlevalued, then clear all of the primary and other values
      // of all ADE elements.
      else {
        v = this.getValue(0);
        if (v && v.ade && v.ade.elements) {
          for (i = 0, n = v.ade.elements.length; i < n; i++) {
            v.ade.elements[i].clearPrimaryValues();
            v.ade.elements[i].clearOtherValues();
          }
        }
      }
    }
  };
  
  /**
   * @private
   */
  object.addAdeEditId = function(id) {
    var h = this.getHtmlIds();
    if (!h.adeEdit) {
      h.adeEdit = $A();
    }
    h.adeEdit.push(id);
  };
  
  /**
   * @private
   */
  object.addAdeDeleteId = function(id) {
    var h = this.getHtmlIds();
    if (!h.adeDelete) {
      h.adeDelete = $A();
    }
    h.adeDelete.push(id);
  };

  /**
   * @private
   */
  object.isAtvValue = function(value) {
    var m = this.getMeta();
    return (m.nonAtvVs) ? (($A(m.nonAtvVs).indexOf(value) == -1) ? true : false) : true;
  };
  
  /**
   * @private
   */
  object.showStandardValueSet = function() {
    var h = this.getHtmlIds();
    $(h.narrowValueSetContainer).hide();
    $(h.standardValueSetContainer).show();
    h.primary = h.standardValueSet;
  };
  
  /**
   * @private
   */
  object.showBroadValueSet = function() {
    var h = this.getHtmlIds();
    if (h.narrowValueSetContainer) {
      $(h.narrowValueSetContainer).hide();
    }
    $(h.standardValueSetContainer).hide();
    $(h.broadValueSetContainer).show();
    h.primary = h.broadValueSet;
  };

  object.getDisplayForAdeSummaryTable = function(index) {
    var d;
    var v = this.values[index];
    if (v.valueOther) {
      d = v.valueOther;
    }
    else if (v.value) {
      d = GSBIO.ARTS.getDisplay(v.value) || v.value;
    }
    return d ? GSBIO.util.htmlEscapeAndPreserveWhitespace(d) : '&nbsp;';
  };

  object.isChanged = function() {
    return _changed;
  };
  
  object.enableButtons = function() {
    var i, n, h = this.getHtmlIds();
    if (h.noteButton) {
      $(h.noteButton).enable();
    }
    if (h.otherAdd) {
      $(h.otherAdd).enable();
    }
    if (h.otherRemove) {
      $(h.otherRemove).enable();
    }
    if (h.adeEdit) {
      for (i = 0, n = h.adeEdit.length; i < n; i++) {
        $(h.adeEdit[i]).enable();
      }
    }
    if (h.adeDelete) {
      for (i = 0, n = h.adeDelete.length; i < n; i++) {
        $(h.adeDelete[i]).enable();
      }
    }
  };
  
  object.disableButtons = function() {
    var i, n, h = this.getHtmlIds();
    if (h.noteButton) {
      $(h.noteButton).disable();
    }
    if (h.otherAdd) {
      $(h.otherAdd).disable();
    }
    if (h.otherRemove) {
      $(h.otherRemove).disable();
    }
    if (h.adeEdit) {
      for (i = 0, n = h.adeEdit.length; i < n; i++) {
        $(h.adeEdit[i]).disable();
      }
    }
    if (h.adeDelete) {
      for (i = 0, n = h.adeDelete.length; i < n; i++) {
        $(h.adeDelete[i]).disable();
      }
    }
  };

  /**
   * Updates the HTML input controls for the primary and other values from the values in this 
   * element.  We know that this is sufficient since we know that this is only called from the 
   * ADE popup, and the ADE popup does not deal with other data element controls such as the
   * standard value controls and note controls.  If we are concerned in the future with such
   * controls, then we must update this method.
   * 
   * @protected
   */
  object.updateInputs = function() {
    var m = this.getMeta();

    // There are some values, so update the controls.
    if (this.values && this.values.length > 0) {
      if (m.isMultivalued) {
        var v = [];
        var o = [];
        for (var i = 0, j = 0, n = this.values.length; i < n; i++) {
          v[i] = this.values[i].value;
          if (this.values[i].valueOther) {
            o[j++] = this.values[i].valueOther;
          }
        }
        this.setPrimaryValues(v);
        this.setOtherValues(o);
      }
      else {
        this.setPrimaryValue(this.values[0].value);
        this.setOtherValue(this.values[0].valueOther);
      }
    }
    
    // There are no values so clear the input controls.
    else {
      this.clearPrimaryValues();
      this.clearOtherValues();
    }
    
    // Simulate an event on the primary control, since we may have just altered its value.
    // This will ensure that associated controls are in their correct state (e.g. 'other' is
    // enabled appropriately).  In addition, make sure the collapse/expand state of an MLVS
    // value set is correct.
    handlePrimaryControlEvent(this, $(this.getHtmlIds().primary));
    if (m.isMultilevelValueSet) {
      this.expandCollapsePrimaryValues();
    }  
  };

  /**
   * Updates the values of this data element from its HTML input controls.
   */
  object.updateFromInputs = function() {
    var i, n, v, sv, pvs, ovs;
    var m = this.getMeta();
    
    // There are a number of different scenarios to consider:
    // 1. The data element is multivalued and has an ADE.  In this case we don't have to do
    //    anything since this object's values will always be in sync with what the user has entered
    //    since that is done as part of processing the return values of the ADE popup.  The
    //    only exception is if the value is one of the standard values, in which case it will not
    //    be in sync.
    // 2. The data element is multivalued and does not have an ADE.  In this case we have to create
    //    a DataElementValue object per value and per other value.
    // 3. The data element is singlevalued and already has a DataElementValue object.  This will
    //    be true if either the data element had a value when the page was initially generated
    //    and/or the data element has an ADE, in which case we create a DataElementValue so we can 
    //    attach the ADE and ADE elements to it.  In this case, update the existing 
    //    DataElementValue object and its ADE if it has one.
    // 4. The data element is singlevalued and does not have a DataElementValue object.  This will
    //    be true if the data element did not have a value when the page was initially generated 
    //    and it does not have an ADE.  In this case, create a DataElementValue object and populate
    //    it with the data element's value.
    if (m.isMultivalued) {
      sv = this.getStandardValue();
      if (sv) {
        this.values = $A();
        v = GSBIO.kc.data.DataElementValue();
        v.value = sv;
        this.addValue(v);
      }
      if (!m.isHasAde && !sv) {
        this.values = $A();
        pvs = this.getPrimaryValues();
        if (pvs) {
          for (i = 0, n = pvs.length; i < n; i++) {
            v = GSBIO.kc.data.DataElementValue();
            v.value = pvs[i];
            this.addValue(v);         
          }
        }
        ovs = this.getOtherValues();
        if (ovs) {
          for (i = 0, n = ovs.length; i < n; i++) {
            v = GSBIO.kc.data.DataElementValue();
            v.value = m.otherCui;
            v.valueOther = ovs[i];
            this.addValue(v);         
          }
        }
      }
    }
    else {
      v = this.getValue(0);
      if (v == null) {
        v = GSBIO.kc.data.DataElementValue();
        this.addValue(v);
      }
      v.value = this.getStandardValue() || (this.getPrimaryValue() || null);
      v.valueOther = this.getOtherValue() || null;
      if (v.ade) {
        v.ade.updateFromInputs();
      }
    }
    if (this.getHtmlIds().note) {
      this.valueNote = $F(this.getHtmlIds().note);
    }
  };
  
  /**
   * Serializes this data element to a JSON string.
   */
  object.serialize = function() {
    this.updateFromInputs();
    return Object.toJSON(this);
  };
  
  /**
   * The event handler for this data element.  See the comments in the method for additional
   * information.
   */
  object.handleEvent = function(event) {
    // Get the HTML element that was the source of the event and the type of event.
    var t = event.type;
    var source = $(Event.element(event));
    var id = source.id;
    var name = source.name;
    var tagName = source.tagName;
    var m = this.getMeta();
    var h = this.getHtmlIds();

    // The primary control (or one of the primary controls) was clicked/changed in some way.
    if ((t == 'click' || t == 'change' || t == 'paste' || t == 'keypress' || t == 'keyup') && $(h.primary) && name == $(h.primary).name) {
      handlePrimaryControlEvent(this, source);

      // For a singlevalued, cv, non multilevel value set, which is rendered as a single SELECT 
      // element, set focus to the other control if the other value was just selected.  We do this
      // here instead of in handlePrimaryControlEvent since we only want to do this for the change 
      // event (i.e. the value really changed), and handlePrimaryControlEvent is called for all
      // events.  If we do this in handlePrimaryControlEvent, a situation arises that once the
      // 'other' value is selected no different value can be selected, since we're setting focus
      // to the other input in the primary control event handler when the user clicks.
      if (m.isDatatypeCv && !m.isMultivalued && !m.isMultilevelValueSet && t == 'change') {
        if (this.getPrimaryValue() == m.otherCui) {
          $(h.other).focus();
        }
      }
    }
    
    // One of the standard values was clicked.
    else if ((t == 'click') && $(h.std) && (name == $(h.std).name)) {
      handleStdControlEvent(this);
    }

    // The note button was clicked.
    else if ((t == 'click') && h.noteButton && (id == h.noteButton)) {
      handleNoteButtonEvent(this);
    }

    // The other add button was clicked or the user pressed the enter key in the other text box.
    // In either case, we want add the other entered by the user to the list of others for a 
    // multivalued data element.  In addition, we'll clear the standard values since the user
    // has now entered a primary value.
    else if ((t == 'click' && h.otherAdd && id == h.otherAdd && !$(h.otherAdd).disabled) || (t == 'keyup' && h.otherText && id == h.otherText && event.keyCode == 13)) {
      _changed = true;
      this.addOtherMulti();
      this.clearStandardValues();
    }

    // The other remove button was clicked.  We want to remove all selected items in the list of
    // others for a multivalued data element.
    else if (t == 'click' && h.otherRemove && id == h.otherRemove && !$(h.otherRemove).disabled) {
      _changed = true;
      this.removeOtherMulti();
    }

    
    // The link to show the ADE popup to enter a new data element value and associated ADE values
    // was clicked.
    else if (t == 'click' && h.adeLink && id == h.adeLink) {
      handleAdeLinkEvent(this);
    }
    
    // The link to show the standard value set was clicked.
    else if (t == 'click' && h.standardLink && id == h.standardLink) {
      handleStandardLinkEvent(this);
    }

    // A link to show the broad value set was clicked.  Note that there may be 2 of these links
    // one in the narrow value and and one in the standard value set.
    else if (t == 'click' && h.broadLinks && $A(h.broadLinks).indexOf(id) != -1) {
      handleBroadLinkEvent(this);
    }

    // A button to edit a value and its associated ADE values from the ADE summary table
    // was clicked.
    else if (t == 'click' && h.adeEdit && $A(h.adeEdit).indexOf(id) != -1) {
      handleAdeEditEvent(this, id);
    }

    // A button to delete a value and its associated ADE values from the ADE summary table
    // was clicked.
    else if (t == 'click' && h.adeDelete && $A(h.adeDelete).indexOf(id) != -1) {
      handleAdeDeleteEvent(this, id);
    }

    // An image to expand/collapse a node of a multi-level value sets was clicked.
    else if (t == 'click' && tagName.toLowerCase() == 'img') {
      this.handleExpandCollapseValueSetEvent(this, source);
    }

  };

  /**
   * Registers the event handler (i.e. the handleEvent() method of this object) for events of
   * interest.  The event handler is registered on the container of all of the input controls of
   * the data element, and will thus catch all events via bubbling.  This must be called after
   * setHtmlIds(), and the id of the data element container must have been supplied in the call
   * to setHtmlIds().
   */
  object.registerEventHandler = function() {
    var h = this.getHtmlIds();
    var id = h.container;
    if (id) {
      Event.observe(id, 'click', this.handleEvent.bindAsEventListener(this));
      Event.observe(id, 'change', this.handleEvent.bindAsEventListener(this));
      Event.observe(id, 'paste', this.handleEvent.bindAsEventListener(this));
      Event.observe(id, 'keypress', this.handleEvent.bindAsEventListener(this));
      Event.observe(id, 'keyup', this.handleEvent.bindAsEventListener(this));
      
    }

    // Register the event handler for the change event on all possible HTML SELECT elements,
    // since change does not bubble for SELECT elements.
    id = h.primary;
    if (id && $(id).tagName.toLowerCase() == 'select') {
      Event.observe(id, 'change', this.handleEvent.bindAsEventListener(this));
    }
    id = h.narrowValueSet;
    if (id && $(id).tagName.toLowerCase() == 'select') {
      Event.observe(id, 'change', this.handleEvent.bindAsEventListener(this));
    }
    id = h.standardValueSet;
    if (id && $(id).tagName.toLowerCase() == 'select') {
      Event.observe(id, 'change', this.handleEvent.bindAsEventListener(this));
    }
    id = h.broadValueSet;
    if (id && $(id).tagName.toLowerCase() == 'select') {
      Event.observe(id, 'change', this.handleEvent.bindAsEventListener(this));
    }
      
  };
  
  // Initialize the object to be returned from the initializer passed in to this object, if any.
  if (initializer) {
    if (initializer.id) {
      _id = initializer.id;
    }
    object.valueNote = initializer.valueNote;
    if (initializer.values) {
      object.values = $A();
      for (var i = 0, n = initializer.values.length; i < n; i++) {
        object.values.push(GSBIO.kc.data.DataElementValue(initializer.values[i]));
      }
    }
  }
  
  return object;
}; // GSBIO.kc.data.DataElement

/**
 * Represents an ADE.
 */
GSBIO.kc.data.Ade = function(initializer) {
  var object = {};
  var _elementMap = $H();

  // The system name of the ADE, in its canonical form as specified in the DET.
  object.systemName;

  // The ADE elements that comprise this ADE.
  object.elements;

  object.addAdeElement = function(element) {
    if (!this.elements) {
      this.elements = $A();
    }
    this.elements.push(element);
    _elementMap[element.systemName] = element;
  };

  object.getAdeElement = function(systemName) {
    return _elementMap[systemName];
  };

  object.updateFromInputs = function() {
    if (this.elements) {
      for (var i = 0, n = this.elements.length; i < n; i++) {
        this.elements[i].updateFromInputs();
      }
    }
  };
  
  // Initialize the object to be returned from the initializer passed in to this object.
  if (initializer) {
    object.systemName = initializer.systemName;
    if (initializer.elements) {
      object.elements = $A();
      for (var i = 0, n = initializer.elements.length; i < n; i++) {
        var e = GSBIO.kc.data.AdeElement(initializer.elements[i]);
        object.elements.push(e);
        _elementMap[e.systemName] = e;
      }
    }
  }

  return object;
}; // GSBIO.kc.data.Ade

/**
 * Represents a single ADE element.  This object handles all ADE element event handling and 
 * converting all of the ADE element's values to a JSON string.  It is similar to a 
 * GSBIO.kc.data.DataElement but its needs are simpler since many of the data element constructs,
 * such as ADEs, notes, standard values and narrow and broad value sets, do not apply to ADEs.
 * 
 * A single ADE element is rendered in HTML using a number of input controls and other HTML 
 * elements, which are all wrapped in an HTML DIV known as the container.
 *
 * Most of the methods are concerned with event handling.  A single method, handleEvent(), 
 * handles all events of concern.  The registerEventHandler method registers handleEvent() as
 * the event handler on the container DIV, and event bubbling is relied upon to bubble the events 
 * up to the container DIV.  The general structure of handleEvent() is to first determine the 
 * actual source and type of event, and then handle the event if appropriate by calling private
 * methods to do so.  These private event handling methods comprise the majority of code in
 * this object.
 */
GSBIO.kc.data.AdeElement = function(initializer) {
  var object = GSBIO.kc.data.Element(initializer);
  
  //-----------------------------
  //----- PRIVATE VARIABLES -----
  //-----------------------------
  

  //---------------------------
  //----- PRIVATE METHODS -----
  //---------------------------
  
  /**
   * Called by the event handler to deal with events on the primary control(s).
   *
   * @param source the source of the event, which is one of the primary input controls
   */
  function handlePrimaryControlEvent(thisObj, source) {
    var m = thisObj.getMeta();
    var h = thisObj.getHtmlIds();
    // The ADE element is multivalued.  In this case we are only concerned with whether the
    // NOVAL was just selected or not, since the NOVAL is mutually exclusive with all values,
    // including others.
    // What we need to do is as follows:
    //   - If the primary value just selected is the noval, then we must deselect all values 
    //     except the noval, and also clear all others and disable the other controls since no 
    //     values except the noval can be selected.  
    //   - If the primary value just selected is not the noval, then we must deselect the noval
    //     and enable the other controls.
    //   - If no primary values are selected, then enable the other controls.  This must be done
    //     to handle the case where the user selects the NOVAL, which disabled the other controls,
    //     then deselects the NOVAL.  In this case there will be no primary value selected, but
    //     other should be enabled since the NOVAL is now not selected.
    if (m.isMultivalued) {
      primaryValues = thisObj.getPrimaryValues();
      if (primaryValues && primaryValues.size() > 0) {
        if (m.noCui) {
          if ($F(source) == m.noCui) {
            thisObj.selectOnlyNoval();
            thisObj.clearOtherValues();
            thisObj.disableOther();
          }
          else {
            thisObj.deselectNoval();
            thisObj.enableOther();
          }
        }
      }
      else {
        thisObj.enableOther();
      }
    }

    // The data element is singlvalued.
    else {

      // There is a primary value entered/selected.
      primaryValue = thisObj.getPrimaryValue();
      if (primaryValue) {
        
        // The data element is a CV data element and the value selected was the 'other' value, then 
        // enable the other text control, otherwise disable the other text control.
        if (m.isDatatypeCv && primaryValue == m.otherCui) {
          thisObj.showOther();  // note: should already be showing but doesn't hurt to make this call
          thisObj.enableOther();
        }
        else {
          thisObj.disableOther();
        }            
      }
        
      // If there is no primary value entered/selected, then we must make sure that other is 
      // disabled, since the 'other' value is not selected.
      else {
        thisObj.disableOther();
      }
    }
  }

  //----------------------------
  //----- PUBLIC VARIABLES -----
  //----------------------------


  //--------------------------
  //----- PUBLIC METHODS -----
  //--------------------------

  object.getDisplayForAdeSummaryTable = function() {
    var i, n, v, display = '';
    if (this.values) {
      for (i = 0, n = this.values.length; i < n; i++) {
        v = this.values[i];
        if (v.value || v.valueOther) {
          if (i > 0) {
            display += ', ';
          }
          display += v.valueOther || (GSBIO.ARTS.getDisplay(v.value) || v.value);
        }
      }
    }
    return display ? GSBIO.util.htmlEscapeAndPreserveWhitespace(display) : '&nbsp;';
  };

  /**
   * Updates the values of this data element from its HTML input controls.
   */
  object.updateFromInputs = function() {
    var i, n, v, pvs, ovs;
    var m = this.getMeta();
    
    this.values = $A();
    if (m.isMultivalued) {
      pvs = this.getPrimaryValues();
      if (pvs) {
        for (i = 0, n = pvs.length; i < n; i++) {
          v = GSBIO.kc.data.AdeElementValue();
          v.value = pvs[i];
          this.addValue(v);         
        }
      }
      ovs = this.getOtherValues();
      if (ovs) {
        for (i = 0, n = ovs.length; i < n; i++) {
          v = GSBIO.kc.data.AdeElementValue();
          v.value = m.otherCui;
          v.valueOther = ovs[i];
          this.addValue(v);         
        }
      }
    }
    else {
      v = GSBIO.kc.data.AdeElementValue();
      v.value = this.getPrimaryValue();
      v.valueOther = this.getOtherValue();
      this.addValue(v);
    }
  };

  /**
   * Updates the HTML input controls from the values in this ADE element.
   */
  object.updateInputs = function() {
    var m = this.getMeta();
    if (this.values && this.values.length > 0) {
      if (m.isMultivalued) {
        var v = [];
        var o = [];
        for (var i = 0, j = 0, n = this.values.length; i < n; i++) {
          v[i] = this.values[i].value;
          if (this.values[i].valueOther) {
            o[j++] = this.values[i].valueOther;
          }
        }
        this.setPrimaryValues(v);
        this.setOtherValues(o);
      }
      else {
        this.setPrimaryValue(this.values[0].value);
        this.setOtherValue(this.values[0].valueOther);
      }
    }
    
    // There are no values so clear the input controls.
    else {
      this.clearPrimaryValues();
      this.clearOtherValues();
    }
    // Simulate an event on the primary control, since we may have just altered its value.
    // This will ensure that associated controls are in their correct state (e.g. 'other' is
    // enabled appropriately).  In addition, make sure the collapse/expand state of an MLVS
    // value set is correct.
    handlePrimaryControlEvent(this, $(this.getHtmlIds().primary));
    if (m.isMultilevelValueSet) {
      this.expandCollapsePrimaryValues();
    }  
  };

  /**
   * The event handler for this data element.  See the comments in the method for additional
   * information.
   */
  object.handleEvent = function(event) {
    // Get the HTML element that was the source of the event and the type of event.
    var t = event.type;
    var source = $(Event.element(event));
    var id = source.id;
    var name = source.name;
    var tagName = source.tagName;
    var m = this.getMeta();
    var h = this.getHtmlIds();

    // The primary control (or one of the primary controls) was clicked/changed in some way.
    if ((t == 'click' || t == 'change' || t == 'paste' || t == 'keypress' || t == 'keyup') && $(h.primary) && name == $(h.primary).name) {
      handlePrimaryControlEvent(this, source);

      // For a singlevalued, cv, non multilevel value set, which is rendered as a single SELECT 
      // element, set focus to the other control if the other value was just selected.  We do this
      // here instead of in handlePrimaryControlEvent since we only want to do this for the change 
      // event (i.e. the value really changed), and handlePrimaryControlEvent is called for all
      // events.  If we do this in handlePrimaryControlEvent, a situation arises that once the
      // 'other' value is selected no different value can be selected, since we're setting focus
      // to the other input in the primary control event handler when the user clicks.
      if (m.isDatatypeCv && !m.isMultivalued && !m.isMultilevelValueSet && t == 'change') {
        if (this.getPrimaryValue() == m.otherCui) {
          $(h.other).focus();
        }
      }
    }
    
    // The other add button was clicked or the user pressed the enter key in the other text box.
    // In either case, we want add the other entered by the user to the list of others for a 
    // multivalued data element.
    else if ((t == 'click' && h.otherAdd && id == h.otherAdd && !$(h.otherAdd).disabled) || (t == 'keyup' && h.otherText && id == h.otherText && event.keyCode == 13)) {
      this.addOtherMulti();
    }

    // The other remove button was clicked.  We want to remove all selected items in the list of
    // others for a multivalued data element.
    else if (t == 'click' && h.otherRemove && id == h.otherRemove) {
      this.removeOtherMulti();
    }

    // An image to expand/collapse a node of a multi-level value sets was clicked.
    else if (t == 'click' && tagName.toLowerCase() == 'img') {
      this.handleExpandCollapseValueSetEvent(this, source);
    }
  };

  /**
   * Registers the event handler (i.e. the handleEvent() method of this object) for events of
   * interest.  The event handler is registered on the container of all of the input controls of
   * the ADE element, and will thus catch all events via bubbling.  This must be called after
   * setHtmlIds(), and the id of the ADE element container must have been supplied in the call
   * to setHtmlIds().
   */
  object.registerEventHandler = function() {
    var h = this.getHtmlIds();
    var id = h.container;
    if (id) {
      Event.observe(id, 'click', this.handleEvent.bindAsEventListener(this));
      Event.observe(id, 'change', this.handleEvent.bindAsEventListener(this));
      Event.observe(id, 'paste', this.handleEvent.bindAsEventListener(this));
      Event.observe(id, 'keypress', this.handleEvent.bindAsEventListener(this));
      Event.observe(id, 'keyup', this.handleEvent.bindAsEventListener(this));
    }

    // Register the event handler for the change event on all possible HTML SELECT elements,
    // since change does not bubble for SELECT elements.
    id = h.primary;
    if (id && $(id).tagName.toLowerCase() == 'select') {
      Event.observe(id, 'change', this.handleEvent.bindAsEventListener(this));
    }
    id = h.broadValueSet;
    if (id && $(id).tagName.toLowerCase() == 'select') {
      Event.observe(id, 'change', this.handleEvent.bindAsEventListener(this));
    }
  };
  
  // Initialize the object to be returned from the initializer passed in to this object, if any.
  if (initializer) {
    if (initializer.values) {
      object.values = $A();
      for (var i = 0, n = initializer.values.length; i < n; i++) {
        object.values.push(GSBIO.kc.data.AdeElementValue(initializer.values[i]));
      }
    }
  }

  return object;
}; // GSBIO.kc.data.AdeElement

/**
 * Represents an element value.
 */
GSBIO.kc.data.ElementValue = function(initializer) {
  var object = {};
  object.value = null;
  object.valueOther = null;

  // Initialize the object to be returned from the initializer passed in to this object.
  if (initializer) {
    object.value = initializer.value;
    object.valueOther = initializer.valueOther;
  }

  return object;
}; // GSBIO.kc.data.ElementValue

/**
 * Represents a data element value.
 */
GSBIO.kc.data.DataElementValue = function(initializer) {
  var object = GSBIO.kc.data.ElementValue(initializer);

  object.ade = null;

  // Initialize the object to be returned from the initializer passed in to this object.
  if (initializer) {
    if (initializer.ade) {
      object.ade = GSBIO.kc.data.Ade(initializer.ade);
    }
  }

  return object;
}; // GSBIO.kc.data.DataElementValue

/**
 * Represents an ADE element value.
 */
GSBIO.kc.data.AdeElementValue = function(initializer) {
  var object = GSBIO.kc.data.ElementValue(initializer);
  return object;
}; // GSBIO.kc.data.AdeElementValue
