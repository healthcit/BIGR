/**
 * Creates the global GSBIO object if it is not already defined.
 */
if (typeof GSBIO == "undefined") {
  var GSBIO = {};
}

/**
 * Creates one or more namespaces if they do not exist, returning the last one 
 * created.  A namespace is specified as a string with "." separating the 
 * components of the namespace, similar to a Java package.  Unlike Java, GSBIO 
 * namespaces are hierarchical.  The leading "GSBIO" can be included or omitted.
 * Some examples:
 * <pre>
 * GSBIO.namespace("kc");
 * GSBIO.namespace("GSBIO.kc.query");
 * </pre>
 * The first example creates and returns the "kc" namespace.  The second creates
 * the "query" namespace in the "kc" namespace, creating the "kc" namespace if
 * it does not exist.
 */
GSBIO.namespace = function() {
  var a = arguments;
  var ns = null;
  var nss = null;
  var i, j;
  
  // Loop through all arguments.  Split each across periods to get all of the
  // namespaces in the namespace hierarchy.
  for (i = 0; i < a.length; i = i + 1) {
    nss = a[i].split(".");
    ns = GSBIO;

    // Loop through all of the namespaces in the hierarchy, creating namespaces
    // that do not exist.  Ignore GSBIO if it is included as the first 
    // namespace since it is implied.
    for (j = (nss[0] == "GSBIO") ? 1 : 0; j < nss.length; j = j + 1) {
      ns[nss[j]] = ns[nss[j]] || {};
      ns = ns[nss[j]];
    }
  }
  return ns;
};

/**
 * Initializes the GSBIO global object.
 */
GSBIO.init = function() {

  /**
   * Adds an option to the specified select element as the last option and returns it.  If the 
   * specified element is not a select element, then this method quietly does nothing and returns 
   * null.
   *
   * @param element the select element
   * @param value the option's value
   * @param text the text to display for the option
   * @return The newly created option.
   */
  Form.Element.Methods.addOption = function(element, value, text) {
    element = $(element);
    if (element.tagName.toLowerCase() == 'select') {
      var opt = document.createElement('option');
      opt.value = value;
      opt.text = GSBIO.util.htmlEscape(text);
      element.options.add(opt);
      return opt;
    }
    return null;
  };

  /**
   * Deselects all radio buttons with the same HTML name as the specified element.  If the
   * specified element is not a radio button, then this method quietly does nothing.
   *
   * @param element the radio button.  This can be any radio button in the set of radio buttons
   *                with the same name.
   * @return The extended input element.
   */
  Form.Element.Methods.clearRadios = function(element) {
    element = $(element);
    if ((element.tagName.toLowerCase() == 'input') && (element.type.toLowerCase() == 'radio')) {
      var a = element.name ? document.getElementsByName(element.name) : [element];
      for (var i = 0, n = a.length; i < n; i++) {
        a[i].checked = false;
      }
    }
    return element;
  };

  /**
   * Returns the value of the selected radio button from the set of radio buttons with the same 
   * HTML name as the specified element.  If the specified element is not a radio button, then 
   * this method returns null.
   *
   * @param element the radio button.  This can be any radio button in the set of radio buttons
   *                with the same name.
   * @return The value, or null if no radio button is selected.
   */
  Form.Element.Methods.getRadiosValue = function(element) {
    element = $(element);
    if ((element.tagName.toLowerCase() == 'input') && (element.type.toLowerCase() == 'radio')) {
      var a = element.name ? document.getElementsByName(element.name) : [element];
      for (var i = 0, n = a.length; i < n; i++) {
        if (a[i].checked) {
          return a[i].value;
        }
      }
    }
    return null;
  };

  /**
   * Selects the radio button with the specified value from the set of radio buttons with the same 
   * HTML name as the specified element.  If the specified element is not a radio button, then 
   * this method quietly does nothing.
   *
   * @param element the radio button.  This can be any radio button in the set of radio buttons
   *                with the same name.
   * @return The extended input element.
   */
  Form.Element.Methods.setRadiosValue = function(element, value) {
    element = $(element);
    if ((element.tagName.toLowerCase() == 'input') && (element.type.toLowerCase() == 'radio')) {
      var a = element.name ? document.getElementsByName(element.name) : [element];
      for (var i = 0, n = a.length; i < n; i++) {
        if (a[i].value == value) {
          a[i].checked = true;
          return;
        }
      }
    }
    return element;
  };

  /**
   * Returns an array of values of all of the checked checkboxes with the same HMTL name as 
   * the specified element.  This is useful when a set of checkboxes with the same name is being
   * used as a means to select multiple values.  If the specified element is not a checkbox, then 
   * this method returns an empty array.
   *
   * @param element the checkbox.  This can be any checkbox in the set of checkboxes with the 
   *                same name.
   * @return The array of values.  The array is a prototype extended array.
   */
  Form.Element.Methods.getCheckboxesValues = function(element) {
    element = $(element);
    var values = $A();
    if ((element.tagName.toLowerCase() == 'input') && (element.type.toLowerCase() == 'checkbox')) {
      var a = element.name ? document.getElementsByName(element.name) : [element];
      for (var i = 0, n = a.length; i < n; i++) {
        if (a[i].checked) {
          values.push(a[i].value);
        }
      }
    }
    return values;
  };

  /**
   * Checks the checkboxes with the specified values from the set of checkboxes with the same 
   * HTML name as the specified element.  This is useful when a set of checkboxes with the same 
   * name is being used as a means to select multiple values.  If the specified element is not a 
   * checkbox, then this method quietly does nothing.
   *
   * @param element the checkbox.  This can be any checkbox in the set of checkboxes with the
   *                same name.
   * @param values the array of values of checkboxes to be checked
   * @return The extended input element.
   */
  Form.Element.Methods.setCheckboxesValues = function(element, values) {
    element = $(element);
    var v = $A(values);
    if ((element.tagName.toLowerCase() == 'input') && (element.type.toLowerCase() == 'checkbox')) {
      var a = element.name ? document.getElementsByName(element.name) : [element];
      for (var i = 0, n = a.length; i < n; i++) {
        if (v.indexOf(a[i].value) != -1) {
          a[i].checked = true;
        }
      }
    }
    return element;
  };

  /**
   * Unchecks all checkboxes in the set of checkboxes with the same HTML name as the specified 
   * element.  This is useful when a set of checkboxes with the same name is being used as a 
   * means to select multiple values.  If the specified element is not a checkbox, then this 
   * method quietly does nothing.
   *
   * @param element the checkbox.  This can be any checkbox in the set of checkboxes with the
   *                same name.
   * @return The extended input element.
   */
  Form.Element.Methods.clearCheckboxes = function(element) {
    element = $(element);
    if ((element.tagName.toLowerCase() == 'input') && (element.type.toLowerCase() == 'checkbox')) {
      var a = element.name ? document.getElementsByName(element.name) : [element];
      for (var i = 0, n = a.length; i < n; i++) {
        a[i].checked = false;
      }
    }
    return element;
  };

  Element.addMethods();
};

GSBIO.init();

/**
 * A singleton that holds ARTS concepts.  This is a simple JavaScript 
 * representation of ARTS to which concepts can be added and retrieved.
 */
GSBIO.ARTS = function() {
  return {
  	concepts: $H(),

    // Adds an ARTS concept with the specified CUI and display name to ARTS.
	addConcept:
	  function(cui, display) {
        this.concepts[cui] = display;
	  },
	  
    // Returns the display name of the ARTS concept with the specified CUI.
    getDisplay: 
      function (cui) {
        return this.concepts[cui];
      },
      
    // Merges the concepts from another ARTS object into this one.  Useful
    // for merging the ARTS concepts of a popup window into those of the 
    // calling window.
    merge:
      function (newConcepts) {
        this.concepts.merge(newConcepts);
      }
  };
}();

/**
 * A singleton unique id generator.  This is useful when generating HTML via Javascript that
 * needs random unique ids.
 */
GSBIO.UniqueIdGenerator = function() {
  var object = {};
  var _prefixes = ['js', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'];
  var _prefixIndex = 0;
  var _nextNumber = 0;

  /**
   * Returns a unique identifier.
   */ 
  object.getUniqueId = function() {
    var n = _nextNumber;
    var id = _prefixes[_prefixIndex] + n;

    // We've added some robustness around checking that there is no element with the id we're 
    // generating, and if so trying a different prefix for the id.
    while ($(id) && _prefixIndex < _prefixes.length) {
      id = _prefixes[_prefixIndex++] + n;
    }    
    if (_prefixIndex == _prefixes.length) {
      alert('Cannot generate unique id!');
    }
    else {
      _nextNumber++;
      return id;
    }
  };

  return object;
}();

/**
 * The context path of the web application.  This can be used to form URLs.  Instead of hardcoding
 * it here, it must be set by some server-side code with access to the request, from which the
 * context path can be obtained.
 */
GSBIO.contextPath = '';
