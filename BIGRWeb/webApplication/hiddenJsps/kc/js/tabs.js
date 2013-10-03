// File: tabs.js
//
// Depends on: gsbio.js, prototype.js, gsbioLightbox.js
//
// Description: Objects and methods to manage tabs and their contents.

// Create the namespace for the tabs.
GSBIO.namespace("tabs");

/**
 * A singleton that holds one or more GSBIO.tabs.Tabs objects.  This object will return a
 * named Tabs collection, creating it if necessary, thus allowing multiple Tabs collections to
 * exist on a single page.
 */
GSBIO.tabs.AllTabs = function() {
  var _tabsMap = $H();

  return {
	getTabs:
	  function(name) {
        var t = _tabsMap[name];
	    if (!t) {
	      t = new GSBIO.tabs.Tabs();
	      _tabsMap[name] = t;
	    }
	    return t;
	  } // getTabs
	  
  };  // return
}(); // GSBIO.tabs.AllTabs singleton

/**
 * Stores a collection of GSBIO.tabs.Tab objects, and allows a tab from the collection to 
 * be selected.
 */
GSBIO.tabs.Tabs = function() {
  var object = {};
  var _tabs = $A();
  var _tabsMap = $H();

  // Recursive finder for getTab.
  function findTab(tabId, tabs) {
    var t;
    for (var i = 0; i < tabs.length && !t; i++) {
      var tab = tabs[i];
      if (tab.id == tabId) {
        t = tab;
      }
      else {
        var subTabs = tab.tabs;
        if (subTabs.length > 0) {
          t = findTab(tabId, subTabs);
        }
      }
    }
    return t;
  }

  function findFirstLeafTab(tab) {
    var returnTab = tab;
    var tabs = tab.tabs;
    if (tabs.length > 0) {
      returnTab = findFirstLeafTab(tabs[0]);
    }
    return returnTab;
  }
  
  function deselectAllTabs(tabs) {
    for (var i = 0; i < tabs.length; i++) {
      var tab = tabs[i];
      tab.deselect();
      deselectAllTabs(tab.tabs);
    }
  }

  // Adds the specified tab to this collection.
  object.addTab = function(tab) {
    _tabs[_tabs.length] = tab;
    _tabsMap[tab.id] = tab;
  };

  // Returns the tab with the specified id from this collection.
  object.getTab = function(tabId) {
    return findTab(tabId, _tabs);
  };

  // Clears this tab collection.
  object.clear = function() {
    _tabs = $A();
    _tabsMap = $H();
  };

  // Selects the specified tab.  We always assume that if a tab that has 
  // children is selected then its first child should be selected, recursively, 
  // until a leaf tab is selected.  Thus we assume that only leaf tabs have
  // actual content to be shown, and the content of each parent tabs is just
  // its child tabs.
  object.selectTab = function(tabId) {
    deselectAllTabs(_tabs);
    
	// Find the specified tab, and select its first leaf tab.  Selecting the
	// leaf will cause all of its parents to be appropriately selected.
    var tab = this.getTab(tabId);
    if (!tab) {
      alert("In GSBIO.tabs.Tabs.selectTab: attempt to select a tab that does not exist (" + id + ").");
    }
    else {
      findFirstLeafTab(tab).select();
    }
  };

  return object;
}; // GSBIO.tabs.Tabs

/**
 * Stores a single tab, which can have sub-tabs to any level.  The most important function provided 
 * by this object is to select the tab.  Selecting a tab always causes the tab to be visually 
 * highlighted and always causes its parent tab to be selected as well, if it has a parent.  In 
 * addition:
 *
 *   o If the tab has sub-tabs, its corresponding sub-tabs will be shown.
 *   o If the tab is a leaf tab, then one of the following actions will be performed, depending 
 *     upon what is specified in the initializer when this tab is created:
 *     o If the ajax flag is set to true and a url is specified, then make an ajax call using
 *       the url, and set the contents associated with the tab's to the contents of the response
 *       and make the contents visible.  Do this only the first time the tab is selected.
 *     o If the ajax flag is not set to true and a url is specified, then replace the entire page
 *       with the url every time the tab is selected.
 *     o If a url is not specified, or the ajax flag and a url are set and we've aleady made the
 *       ajax call to get the contents of the tab, then simply make the contents visible.
 *
 * The following can be specified in the initializer:
 *
 * type (String): The "type" of tab.  This can be used as a flag to enable special behavior.
 * id (String): The unique identifier of the HTML element that is used to render the tab itself.  
 *              Used to select and deselect the tab.
 * contentsId (String): The unique identifier of the HTML element that holds contents of the tab.  
 *                      Used to show and hide the contents when appropriate.
 * displayName (String): The text that is displayed on the tab.
 * ajax (Boolean): Make an ajax call to get the contents of the tab.  This only applies to
 *                 leaf tabs and is ignored for non-leaf tabs.
 * url (String): The url to use to get the contents of the selected tab.  This only applies to
 *                 leaf tabs and is ignored for non-leaf tabs.
 * selectClassName (String): The name of the css class that should be applied when this tab is 
 *                           selected to highlight it.
 */
GSBIO.tabs.Tab = function(initializer) {
  var object = {};
  var _type;
  var _contentsId;
  var _displayName;
  var _url;
  var _ajax;
  var _madeAjaxCall = false;
  var _selectClassName;

  object.id;
  object.parent;
  object.tabs = $A();
  
  // Adds the specified sub-tab to this tab.
  object.addTab = function(tab) {
    this.tabs[this.tabs.length] = tab;
    tab.parent = this;
  };

  // Deselects this tab.  This jsut involves unhighlighting the tab itself and hiding its contents.
  object.deselect = function() {
    if (_selectClassName) {
      $(this.id).removeClassName(_selectClassName);
    }
    $(_contentsId).hide();
  };

  // Selects this tab.
  object.select = function() {
    var tab = $(this.id);
    var contents = $(_contentsId);

	// If the tab has sub-tabs then just show its content, which is presumably
	// just it's sub-tabs, and highlight it.
    if (this.tabs.length > 0) {
      contents.show();
      if (_selectClassName) {
        tab.addClassName(_selectClassName);
      }
    }
    else {
      // Replace the entire contents of the browser page.  When doing this we
      // don't worry about highlighting the selected tab or its parents since 
      // that is the job of the server when generating the page to return to 
      // the browser.
      if (_url && !_ajax) {
        window.location.href = _url;
      }

      // Make the ajax call to replace the contents of the tab, if we have not already done it.
      else if (_url && _ajax && !_madeAjaxCall) {
        //GSBIO.lightbox.LightBox.showActivity();
		var ajaxUpdater = new Ajax.Updater(
		  _contentsId, 
          _url, 
          {
            onComplete: function() {
	          //GSBIO.lightbox.LightBox.hide();
              _madeAjaxCall = true;
            },
            method: 'post', 
			evalScripts: true
          });
      }
      
      // Show the tab's content and highlight it.  	  
      contents.show();
      if (_selectClassName) {
        tab.addClassName(_selectClassName);
      }

      // If this is a KC tab and a display name was specified and there is a element whose id is 
      // 'page', then set the value of page to the display name to make sure that the same page
      // is selected after the page is submitted and returned to the browser.
      if (_type == 'kc' && _displayName && $('page')) {
        $('page').value = _displayName;
      }

      // Select the parent tab, if any.
      if (this.parent) {
        this.parent.select();
      }
    }  // leaf tab
  }; // select

  // Initialize the object to be returned from the initializer passed in to this object, if any.
  if (initializer) {
    object.id = initializer.id;
    _type = initializer.type;
    _contentsId = initializer.contentsId;
    _displayName = initializer.displayName;
    _url = initializer.url;
    _ajax = initializer.ajax;
    _selectClassName = initializer.selectClassName;
  }

  return object;
}; // GSBIO.tabs.Tab
