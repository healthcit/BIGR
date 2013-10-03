// File: bigrAperio.js
//
// Depends on: gsbio.js, prototype.js, gsbioLightbox.js
//
// Description: Objects and functions to support integration with Aperio.

// Create the namespace for the Aperio integration objects.
GSBIO.namespace("bigr.integration.aperio");

/**
 * A singleton that queries the server for the H&E image for the specified
 * sample and opens either Aperio's ImageScope or WebViewer.
 */
GSBIO.bigr.integration.aperio.HeImage = function () {
  var _sampleId;
  var _dataServerBaseUrl;
  var _imageServerBaseUrl;
  //var _dataServerSisUrl;
  var _bigrUrl;
  
// json.dialog - flag to indicate that a dialog should be opened
// transport.responseText - html to be displayed in the dialog
// imageId - to call SingleViewWithImageScopeOrBrowser
// token - to call SingleViewWithImageScopeOrBrowser
//
  // Process the response from an AJAX call.  We expect the response to contain a JSON header
  // with an object with the following values:
  //   - exception=true to indicate that an exception occurred on the server
  //   - dialog=true to indicate that a dialog should be opened with the response text
  //   - neither exception=true or dialog=true to indicate that the image id was found.
  //     In this case the object will contain the imageId and authorization token.
  function processAjaxResponse(transport, jsonResponse) {
    if (jsonResponse.exception) {
	  GSBIO.lightbox.LightBox.showModal(transport.responseText);
	}
    else if (jsonResponse.dialog) {
	  GSBIO.lightbox.LightBox.showModal(transport.responseText);
	  var p = $('spectrumPassword');
	  if (p) {
	    Event.observe(p, 'keypress', GSBIO.bigr.integration.aperio.HeImage.checkPasswordReturn);
	  }
	}
    else {
      if (isImageScopeInstalled()) {
//      	var isUrl = _imageServerBaseUrl + '/@' + jsonResponse.imageId + '/view.apml';
      	var isUrl = _imageServerBaseUrl + '/@@'+ jsonResponse.token+'/@' + jsonResponse.imageId + '/is.sis?chost=' + jsonResponse.chost;
////ygb        var isUrl = _imageServerBaseUrl + '/BulkAction.php?Command=BulkImageScopeView&ImageIds[]=' + jsonResponse.imageId + '&TableName=Image';
													
//        var isUrl = _imageServerBaseUrl + '/imageserver/@@' + jsonResponse.token + '/@' + jsonResponse.imageId + '/is.sis?chost=' + _imageServerBaseUrl.substr(7) + '/imageserver';
        window.open(isUrl, 'Image' + jsonResponse.imageId);
      }
      else {
 ////ygb       var wvUrl = _imageServerBaseUrl + '/imageserver/@@' + jsonResponse.token + '/@' + jsonResponse.imageId + '/view.apml';
 		var wvUrl = _imageServerBaseUrl + '/@' + jsonResponse.imageId + '/view.apml';
//        var wvUrl = _imageServerBaseUrl + '/imageserver/@@' + jsonResponse.token + '/@' + jsonResponse.imageId + '/view.apml';
        
        window.open(wvUrl, 'Image' + jsonResponse.imageId, 'scrollbars=yes,resizable=yes,left=0,top=0,width=1024,height=768');
      }
    }
  }

  // Determines if ImageScope is installed on the client machine.  There are two ways to do this.
  // Under windows and IE we try to create the viewport.  Under all other browsers, we check the
  // check the MIME types for "text/sis".
  function isImageScopeInstalled() {
	var agt = navigator.userAgent.toLowerCase();
    if ((agt.indexOf("win") != -1) && (agt.indexOf("msie") != -1)) {
      try {
        var vp = new ActiveXObject("VIEWPORT.ViewportCtrl.1");
        return true;
      }
      catch(e) {
      }
    }
	else if (navigator.mimeTypes.length > 0 && navigator.mimeTypes["text/sis"]) {
      return true;
    }
    return false;
  }
  
  return {
    init:
      function(initializer) {
        _sampleId = initializer.sampleId;
        _dataServerBaseUrl = initializer.dataServerBaseUrl;
        _imageServerBaseUrl = initializer.imageServerBaseUrl;
//        _dataServerSisUrl = initializer.dataServerSisUrl;
        _bigrUrl = GSBIO.contextPath + '/integration/aperiohe.do';
      },  // init

    checkPasswordReturn:
      function(event) {
        if (event.keyCode == Event.KEY_RETURN) {
          GSBIO.bigr.integration.aperio.HeImage.logonSpectrum();
        }
      }, // checkPasswordReturn

	closeDialog:
	  function() {
	    GSBIO.lightbox.LightBox.hide();
      }, // closeDialog

    // Log on to Spectrum using the username and password supplied by the user, via an AJAX 
    // request.
    logonSpectrum:
      function() {
        this.closeDialog();
        var ajaxRequest = new Ajax.Request(
          _bigrUrl, 
            {
              onSuccess: processAjaxResponse,
              method: 'get', 
              parameters: {bigrSampleId: _sampleId, dataServerBaseUrl: _dataServerBaseUrl, spectrumUsername: $('spectrumUsername').value, spectrumPassword: $('spectrumPassword').value}, 
              evalScripts: true
            });
      },  // logonSpectrum
      
    // Display the H&E image for the sample.  This actually just gets the ImageServer image id,
    // and a valid authentication token, via an AJAX call, and uses that to display the image.
    displayHeImage:
      function() {
        var ajaxRequest = new Ajax.Request(
          _bigrUrl, 
            {
              onSuccess: processAjaxResponse,
              method: 'get', 
              parameters: {bigrSampleId: _sampleId, dataServerBaseUrl: _dataServerBaseUrl}, 
              evalScripts: true
            });
      } // displayHeImage
  };  // return
}();
