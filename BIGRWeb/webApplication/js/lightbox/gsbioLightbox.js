// File: gsbioLightbox.js
//
// Depends on: gsbio.js, prototype.js, scriptaculous.js, builder.js (scriptaculous), gsbioLightbox.css
//
// Description: Objects to support lightbox functionality.

// Create the namespace for the lightbox.
GSBIO.namespace("lightbox");

/**
 * A singleton that implements lightbox functionality.  We use a singleton since
 * we expect only a single lightbox per page.  The lightbox allows either a "modal" window 
 * to be loaded with some HTML content and displayed, or an activity/progress indicator to
 * be shown.
 */
GSBIO.lightbox.LightBox = function () {
  var _initializedModal = false;
  var _overlayModal;
  var _windowModal;
  var _contentsModal;

  var _initializedActivity = false;
  var _overlayActivity;
  var _windowActivity;
  var _contentsActivity;
  
  function initModal() {
    if (_initializedModal) return;
      _overlayModal = Builder.node("div", {className: "lightboxOverlay", onclick: "GSBIO.lightbox.LightBox.hide();"});
      _windowModal = Builder.node("div", {className: "lightboxWindow", style: "display: none"}, [
	    Builder.node("img", {className: "lightboxClose",
          src: GSBIO.contextPath + "/images/close.gif", 
          onclick: "GSBIO.lightbox.LightBox.hide();",
          alt: "Close",
          title: "Close this window"}),
        _contentsModal = Builder.node("div", {className: "lightboxContents"})]);
      _overlayModal = $(_overlayModal);
      _windowModal = $(_windowModal);
      _contentsModal = $(_contentsModal);
      document.body.insertBefore(_windowModal, document.body.childNodes[0]);
      document.body.insertBefore(_overlayModal, document.body.childNodes[0]);
      _initializedModal = true;
  }

  function initActivity() {
    if (_initializedActivity) return;
      _overlayActivity = Builder.node("div", {className: "lightboxOverlay", onclick: "GSBIO.lightbox.LightBox.hide();"});
      _windowActivity = Builder.node("div", {className: "lightboxWindowActivity", style: "display: none"}, [
		_contentsActivity = Builder.node("div", {}, [
		  Builder.node("img", {src: GSBIO.contextPath + "/images/activity.gif"})])]);
      _overlayActivity = $(_overlayActivity);
      _windowActivity = $(_windowActivity);
      _contentsActivity = $(_contentsActivity);
      document.body.insertBefore(_windowActivity, document.body.childNodes[0]);
      document.body.insertBefore(_overlayActivity, document.body.childNodes[0]);
      _initializedActivity = true;
  }
 
  function center(w) {
    var my_width  = 0;
    var my_height = 0;

    if (typeof(window.innerWidth) == 'number' ) {
      my_width  = window.innerWidth;
      my_height = window.innerHeight;
    }
    else if (document.documentElement &&
            (document.documentElement.clientWidth || document.documentElement.clientHeight)) {
      my_width  = document.documentElement.clientWidth;
      my_height = document.documentElement.clientHeight;
    }
    else if (document.body && (document.body.clientWidth || document.body.clientHeight)) {
      my_width  = document.body.clientWidth;
      my_height = document.body.clientHeight;
    }
    w.style.position = 'absolute';
    w.style.zIndex = 99;

    var scrollY = 0;

    if (document.documentElement && document.documentElement.scrollTop) {
      scrollY = document.documentElement.scrollTop;
    }
    else if (document.body && document.body.scrollTop) {
      scrollY = document.body.scrollTop;
    }
    else if (window.pageYOffset) {
      scrollY = window.pageYOffset;
    }
    else if (window.scrollY) {
      scrollY = window.scrollY;
    }

    var elementDimensions = Element.getDimensions(w);

    var setX = (my_width - elementDimensions.width) / 2;
    var setY = (my_height - elementDimensions.height) / 2 + scrollY;

    setX = (setX < 0) ? 0 : setX;
    setY = (setY < 0) ? 0 : setY;

    w.style.left = setX + "px";
    w.style.top  = setY + "px";
    w.style.display  = 'block';
  }
  
  function findFocusableElements() {
    return $A(_contentsModal.descendants()).findAll(function(e) {
      return (["input", "textarea", "select", "a", "button"].include(e.tagName.toLowerCase()));
    });
  }

  return {
	hide:
	  function() {
        if (_initializedModal) {
          _windowModal.hide();
          _overlayModal.hide();
        }
        if (_initializedActivity) {
          _windowActivity.hide();
          _overlayActivity.hide();
        }
      }, // hide

	showModal:
	  function(contents) {
	    initModal();
	    _contentsModal.innerHTML = contents;
	    var a = findFocusableElements();
    	_overlayModal.show();
    	center(_windowModal);
        _windowModal.show();
        if (a.length > 0) {
          a.first().focus();
        }
	  }, // showModal

	showActivity:
	  function() {
	    initActivity();
    	_overlayActivity.show();
    	center(_windowActivity);
        _windowActivity.show();
	  } // showActivity
      
  };  // return
}();
