// File: detViewer.js
//
// Depends on: gsbio.js, prototype.js.
//
// Description: Objects and functions to support the DET Viewer.

// Create the namespace for the DET viewer.
GSBIO.namespace("kc.det.viewer");

/**
 * A singleton that stores the history of searches to support back and forward navigation.
 */
GSBIO.kc.det.viewer.History = function () {
  var _history = $A();
  var _historyIndex = -1;
  
  var _backImgEnabled;
  var _backImgDisabled;
  var _forwardImgEnabled;
  var _forwardImgDisabled;
  var _url;
  
  // The strings that are used as the values of the "search" attribute on the buttons used to
  // search, and that are also saved in the history to determine which type of search was 
  // performed.
  var SEARCH_CUI = 'cui';
  var SEARCH_DESC = 'description';
  var SEARCH_ALL_ADE = 'allade';
  var SEARCH_ALL_CAT = 'allcat';
  var SEARCH_ALL_UNIT = 'allunit';
  var SEARCH_DE = 'de';
  var SEARCH_ADEE = 'adee';

  // The permissible values of the "ctype" parameter (concept type) that is submitted to the
  // server when performing a search.
  var CTYPE_ADE = 'ade';
  var CTYPE_CAT = 'cat';
  var CTYPE_UNIT = 'unit';
  var CTYPE_DE = 'de';
  var CTYPE_ADEE = 'adee';

  // The ids of the DIVs that hold the different types of search parameters.
  var PARAM_CUI = 'paramCui';
  var PARAM_DESC = 'paramDesc';
  var PARAM_ALL = 'paramAll';
  var PARAM_DE = 'paramDe';
  var PARAM_ADEE = 'paramAdee';
  var _allParamDivs = [PARAM_CUI, PARAM_DESC, PARAM_ALL, PARAM_DE, PARAM_ADEE];
  
  // Prepares to perform the specified search.
  function prepareSearch(s) {
    disableHistory();
    var params = null;
    if (s == SEARCH_CUI) {
      if (prepareSearchCui()) {
        params = {search: SEARCH_CUI, cuisys: $F('cuisys')};
      }
    }
    else if (s == SEARCH_DESC) {
      if (prepareSearchDesc()) {
        params = {search: SEARCH_DESC, desc: $F('desc')};
      }
    }
    else if (s == SEARCH_ALL_ADE) {
      if (prepareSearchAll()) {
        params = {search: SEARCH_ALL_ADE, ctype: CTYPE_ADE};
      }
    }
    else if (s == SEARCH_ALL_CAT) {
      if (prepareSearchAll()) {
        params = {search: SEARCH_ALL_CAT, ctype: CTYPE_CAT};
      }
    }
    else if (s == SEARCH_ALL_UNIT) {
      if (prepareSearchAll()) {
        params = {search: SEARCH_ALL_UNIT, ctype: CTYPE_UNIT};
      }
    }
    else if (s == SEARCH_DE) {
      if (prepareSearchDataElement()) {
        params = {search: SEARCH_DE, ctype: CTYPE_DE, dt: $F('deDt'), multi: $F('deMulti'),
                  unit: $F('deUnit'), oce: $F('deOce'), min: $F('deMin'), max: $F('deMax'), 
                  mlvs: $F('deMlvs'), noval: $F('deNoval'), nonatv: $F('deNonatv'), eav: $F('deEav'), 
                  ade: $F('deAde')};
      }
    }
    else if (s == SEARCH_ADEE) {
      if (prepareSearchAdeElement()) {
        params = {search: SEARCH_ADEE, ctype: CTYPE_ADEE, dt: $F('adeDt'), multi: $F('adeMulti'),
                  unit: $F('adeUnit'), oce: $F('adeOce'), min: $F('adeMin'), max: $F('adeMax'),
                  mlvs: $F('adeMlvs'), noval: $F('adeNoval'), eav: $F('adeEav')};
      }
    }
    if (params != null) {
      disappearResults();    
      disableHistory();
    }
    else {
      enableHistory();
    }
    return params;
  }
  
  // Prepares to perform a search based on CUI or system name.  
  function prepareSearchCui() {
    if ($F('cuisys').blank()) {
      alert('Please enter a CUI or system name');
      return false;
    }
    clearParamsDesc();
    clearParamsDataElements();
    clearParamsAdeElements();
    highlightParam(PARAM_CUI);
    return true;
  }

  // Prepares to perform a search based on description.  
  function prepareSearchDesc() {
    if ($F('desc').blank()) {
      alert('Please enter a description');
      return false;
    }
    clearParamsCui();
    clearParamsDataElements();
    clearParamsAdeElements();
    highlightParam(PARAM_DESC);
    return true;
  }

  // Prepares to perform a search for all ADEs, categories or units.
  function prepareSearchAll() {
    clearParamsCui();
    clearParamsDesc();
    clearParamsDataElements();
    clearParamsAdeElements();
    highlightParam(PARAM_ALL);
    return true;
  }

  // Prepares to perform a search for data elements.
  function prepareSearchDataElement() {
    clearParamsCui();
    clearParamsDesc();
    clearParamsAdeElements();
    highlightParam(PARAM_DE);
    return true;
  }

  // Prepares to perform a search for ADE elements.
  function prepareSearchAdeElement() {
    clearParamsCui();
    clearParamsDesc();
    clearParamsDataElements();
    highlightParam(PARAM_ADEE);
    return true;
  }

  function clearParamsCui() {
    $('cuisys').clear();
  }

  function clearParamsDesc() {
    $('desc').clear();
  }

  function clearParamsDataElements() {
    $('deDt').clear();
    $('deMulti').clear();
    $('deUnit').clear();
    $('deOce').clear();
    $('deMin').clear();
    $('deMax').clear();
    $('deMlvs').clear();
    $('deNonatv').clear();
    $('deNoval').clear();
    $('deEav').clear();
    $('deAde').clear();
  }

  function clearParamsAdeElements() {
    $('adeDt').clear();
    $('adeMulti').clear();
    $('adeUnit').clear();
    $('adeOce').clear();
    $('adeMin').clear();
    $('adeMax').clear();
    $('adeMlvs').clear();
    $('adeNoval').clear();
    $('adeEav').clear();
  }

  function highlightParam(id) {
    for (var i = 0; i < _allParamDivs.length; i++) {
      $(_allParamDivs[i]).removeClassName('selected');
    }
    $(id).addClassName('selected');
  }
  
  function disappearResults() {
    var r = $('results');
    if (!r.empty()) {
      if ($('resultCount')) new Effect.BlindUp('resultCount', {duration:1.0});
      if ($('basicInfo')) new Effect.BlindUp('basicInfo', {duration:1.0});
      if ($('associated')) new Effect.BlindUp('associated', {duration:1.0});
    }
  }
  
  function disableHistory() {
    $('back').disable();
      if (_backImgDisabled) {
        $('back').src = _backImgDisabled;
      }
    $('forward').disable()
      if (_forwardImgDisabled) {
        $('forward').src = _forwardImgDisabled;
      }
  }

  function enableHistory() {
    if (_historyIndex > 0) {
      $('back').enable();
      if (_backImgEnabled) {
        $('back').src = _backImgEnabled;
      }
    }
    else {
      $('back').disable();
      if (_backImgDisabled) {
        $('back').src = _backImgDisabled;
      }
    }
    if (_historyIndex < _history.length - 1) {
      $('forward').enable();
      if (_forwardImgEnabled) {
        $('forward').src = _forwardImgEnabled;
      }
    }
    else {
      $('forward').disable();
      if (_forwardImgDisabled) {
        $('forward').src = _forwardImgDisabled;
      }
    }
  }

  function addSearchToHistory(params) {
    // First remove all of the history past the current index.  If the user has gone back and then
    // performs a search without using back or forward, we want to delete all of the history
    // forward of what they are currently viewing.
    var historyCount = _history.length ;
    if ((_historyIndex > -1) && (_historyIndex < historyCount - 1)) {
      for (i = _historyIndex + 1; i < historyCount; i++) {
        _history[i] = null;
      }
      _history = _history.compact();
    }
    
    // Add the parameters to the history.
    _history.push(params);
    _historyIndex++;
  }
  
  function populateInputsFromHistory() {
    var params = _history[_historyIndex];
    var s = params.search;
    if (s == SEARCH_CUI) {
      $('cuisys').value = params.cuisys;
    }
    else if (s == SEARCH_DESC) {
      $('desc').value = params.desc;
    }
    else if ((s == SEARCH_ALL_ADE) || (s == SEARCH_ALL_CAT) || (s == SEARCH_ALL_UNIT)) {
      // explicitly do nothing as there are no inputs to populate
    }
    else if (s == SEARCH_DE) {
      $('deDt').value = params.dt;
      $('deMulti').value = params.multi;
      $('deUnit').value = params.unit;
      $('deOce').value = params.oce;
      $('deMin').value = params.min;
      $('deMax').value = params.max;
      $('deMlvs').value = params.mlvs;
      $('deNonatv').value = params.nonatv;
      $('deNoval').value = params.noval;
      $('deEav').value = params.eav;
      $('deAde').value = params.ade;
    }
    else if (s == SEARCH_ADEE) {
      $('adeDt').value = params.dt;
      $('adeMulti').value = params.multi;
      $('adeUnit').value = params.unit;
      $('adeOce').value = params.oce;
      $('adeMin').value = params.min;
      $('adeMax').value = params.max;
      $('adeMlvs').value = params.mlvs;
      $('adeNoval').value = params.noval;
      $('adeEav').value = params.eav;
    }
  }

  return {
    init:
      function(initializer) {
        _backImgEnabled = initializer.backImgEnabled;
        _backImgDisabled = initializer.backImgDisabled;
        _forwardImgEnabled = initializer.forwardImgEnabled;
        _forwardImgDisabled = initializer.forwardImgDisabled;
        _url = initializer.url;
      },  // init
      
    back:
      function() {
        if (_historyIndex > 0) {
          _historyIndex--;
          populateInputsFromHistory();
          prepareSearch(_history[_historyIndex].search);
          window.setTimeout("GSBIO.kc.det.viewer.History.search()", 1000);
        }
      }, // back

    forward:
      function() {
        if (_historyIndex < _history.length - 1) {
          _historyIndex++;
          populateInputsFromHistory();
          prepareSearch(_history[_historyIndex].search);
          window.setTimeout("GSBIO.kc.det.viewer.History.search()", 1000);
        }
      }, // forward

    handleButtonMouseover:
      function() {
        var e = $(Event.element(event));
        if (e.hasClassName('button')) {
          e.addClassName('buttonHover');
        }
      }, // handleButtonMouseover

    handleButtonMouseout:
      function() {
        var e = $(Event.element(event));
        if (e.hasClassName('buttonHover')) {
          e.removeClassName('buttonHover');
        }
      }, // handleButtonMouseover

    handleSearchClick:
      function() {
        var s, params;
        var e = $(Event.element(event));

        // Determine which button was pressed and perform the appropriate search.  Each button
        // that can be used to perform a search has an id attribute in its HTML that dictates the 
        // type of search requested by the user.
        s = e.id;
        if (s) {
          params = prepareSearch(s);
            
          // If the search parameters were determined, then add them to the history and perform
          // the search.
          if (params) {
            addSearchToHistory(params);
            window.setTimeout("GSBIO.kc.det.viewer.History.search()", 1000);
          }
        }
      }, // handleSearchClick

    handleSearchKeypress:
      function() {
        var params;
        var e = $(Event.element(event));

        // If the return key was pressed in either the CUI or description text box, then
        // perform the search.
        if (event.keyCode == Event.KEY_RETURN) {
          if (e.id == 'cuisys') {
            params = prepareSearch(SEARCH_CUI);
          }
          else if (e.id == 'desc') {
            params = prepareSearch(SEARCH_DESC);
          }

          // If the search parameters were determined, then add them to the history and perform
          // the search.
          if (params) {
            addSearchToHistory(params);
            window.setTimeout("GSBIO.kc.det.viewer.History.search()", 1000);
          }
        }
      }, // handleSearchKeypress

    handleResultsClick:
      function() {
        var params;
        var e = $(Event.element(event));
        var cui = e.id;
        if ((e.tagName.toUpperCase() == 'A') && cui) {
          $('cuisys').value = cui;
          params = prepareSearch(SEARCH_CUI);
          if (params) {
            addSearchToHistory(params);
            window.setTimeout("GSBIO.kc.det.viewer.History.search()", 1000);
          }
        }
      }, // handleResultsClick

    search:
      function() {
        var ajaxUpdater = new Ajax.Updater(
          'results', 
          _url, 
            {
              onComplete: function() {
                var r = $('resultCount');
                var b = $('basicInfo');
                var as = $('associated');
                if (r) r.hide();
                if (b) b.hide();
                if (as) as.hide();
                if (r) new Effect.BlindDown('resultCount', {duration:1.0});
                if (b) new Effect.BlindDown('basicInfo', {duration:1.0});
                if (as) new Effect.BlindDown('associated', {duration:1.0});
                enableHistory();
              },
              method: 'post', 
              parameters: _history[_historyIndex], 
              evalScripts: true
            });
      } // search
      
  };  // return
}();
