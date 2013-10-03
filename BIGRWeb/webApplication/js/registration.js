// File: registration.js
//
// Depends on: gsbio.js, prototype.js.
//
// Description: Objects and methods used by sample and case registration.

// Create the namespace for BIGR data elements.
GSBIO.namespace("bigr.reg");

/**
 * Contains a collection of BIGR host elements.  BIGR elements are registered via their property
 * name (i.e. the name of the input control).
 */
GSBIO.bigr.reg.Elements = function() {
  var _props = $A();
  var _propsRadio = $A();

  return {
    // Registers the property name of a BIGR element.  Use this method for properties whose
    // value can be retrieved by the prototype.js Form.Element.getValue() method.
    register:
      function(prop, propType) {
        _props.push(prop);
      },
      
    // Registers the property name of a BIGR element.  Use this method for properties whose
    // inputs controls are radio buttons.
    registerRadio:
      function(prop) {
        _propsRadio.push(prop);
      },
      
    // Clears all registered property names.
    clear:
      function () {
        _props.clear();
        _propsRadio.clear();
      },

    // Creates and returns an object that contains the values of all registered elements.  The 
    // returned object has the element property names as its property names, each with its 
    // assigned value.
    getElementValues:
      function() {
        var values = {};
        _props.each(function(prop) {
          var c = $(prop);
          if (c && !c.disabled) {
            values[prop] = c.getValue();
          }
        });
        _propsRadio.each(function(prop) {
          var c = $(prop);
          if (c && !c.disabled) {
            values[prop] = c.getRadiosValue();
          }
        });
        return values; 
      }  
  }
}();

/**
 * A hash that maps either sample type CUIs or policy ids to registration form ids.  This allows 
 * getSampleRegistrationForm() to be intelligent about when to make an AJAX call to get 
 * a new page when a sample type or policy is selected.  It must be initialized by the including 
 * page.
 */
GSBIO.bigr.reg.RegFormIds = $H();

/**
 * A hash that maps consent version ids to policy ids.  This allows getCaseRegistrationForm() to 
 * determine the policy id if a consent version is selected.  It must be initialized by the 
 * including page.
 */
GSBIO.bigr.reg.PolicyIds = $H();

/**
 * The currently selected sample type CUI.  This allows getSampleRegistrationForm() to be 
 * intelligent about when to make an AJAX call to get a new page when a sample type is selected.
 */
GSBIO.bigr.reg.CurrentSampleTypeCui = null;

/**
 * The currently selected policy id.  This allows getSampleRegistrationForm() to be 
 * intelligent about when to make an AJAX call to get a new page when a policy is selected.
 */
GSBIO.bigr.reg.CurrentPolicyId = null;

/**
 * Retrievs the sample registration form for the currently selected sample type, via an AJAX call,
 * and displays it.
 */
GSBIO.bigr.reg.getSampleRegistrationForm = function() {
  var newSampleType = $F('sampleData.sampleTypeCui');
  var newRegFormId = GSBIO.bigr.reg.RegFormIds[newSampleType];
  
  // Only make the AJAX call to get a new registration form if the newly selected sample type
  // has a different registration form.
  if (GSBIO.bigr.reg.CurrentSampleTypeCui) {
    var curRegFormId = GSBIO.bigr.reg.RegFormIds[GSBIO.bigr.reg.CurrentSampleTypeCui];
    if (curRegFormId == newRegFormId) {
      
      // Even though we're not making the AJAX call to get the new registration form, we still
      // have to save the newly selected sample type.
      GSBIO.bigr.reg.CurrentSampleTypeCui = newSampleType;
      return;
    }
  } 

  // Disable buttons and hide the existing data elements.
  GSBIO.bigr.reg.setActionButtonEnabling(false);
  $('sampleTypeSpecific').hide();
  
  // Clear any errors or messages that may be showing.
  if ($('errorsDiv')) {
    $('errorsDiv').update();
  }
  if ($('messagesDiv')) {
    $('messagesDiv').update();
  }
  
  // Save the newly selected sample type.  In addition, if we're submitting the registration
  // form id then save the new one so it will be submitted.
  GSBIO.bigr.reg.CurrentSampleTypeCui = newSampleType;
  if ($('sampleData.registrationFormId')) {
    $('sampleData.registrationFormId').value = newRegFormId;
  }

  // The URL to get the sample registration form.
  var url = GSBIO.contextPath + '/dataImport/getSampleRegistrationForm.do';
  
  // Build the list of parameters.  We'll submit all values entered by the user so they will
  // be displayed on the page that is returned.  We also have to submit the sample type and
  // either the policy id or the sample id.  If the policy id is present (which it will be
  // in create sample), then submit it.  Otherwise submit the sample id (for modify sample)
  // which the back-end logic will use to get the policy id.
  var pars = GSBIO.bigr.reg.Elements.getElementValues();
  pars['sampleData.sampleTypeCui'] = newSampleType;
  if ($('policyId')) {
    pars['policyId'] = $F('policyId');
  }
  else {
    pars['sampleData.sampleId'] = $F('sampleData.sampleId');    
  }
  
  // Clear all BIGR and KC elements.  The appropriate elements will be registered by the new page.
  GSBIO.bigr.reg.Elements.clear();
  GSBIO.kc.data.FormInstances.deleteFormInstance();
  
  // Perform the AJAX call to get and display the new new page.
  var ajaxUpdater = new Ajax.Updater(
        'sampleTypeSpecific', 
 		url, 
 		{
          onComplete: function() {
            $('sampleTypeSpecific').show();
            GSBIO.bigr.reg.setActionButtonEnabling(true);
            $('sampleData.sampleTypeCui').up('body').focus();
            GSBIO.kc.data.FormInstances.getFormInstance().alternateRowColor();
            if ($('sampleTypeErrorsDiv')) {
              if ($('errorsDiv')) {
                var e = $('sampleTypeErrorsDiv').innerHTML;
                $('sampleTypeErrorsDiv').update();
                $('errorsDiv').update(e);
              }
              else {
                $('sampleTypeErrorsDiv').show();
              }
            }
            if ($('sampleTypeMessagesDiv')) {
              if ($('messagesDiv')) {
                var m = $('sampleTypeMessagesDiv').innerHTML;
                $('sampleTypeMessagesDiv').update();
                $('messagesDiv').update(m);
              }
              else {
                $('sampleTypeMessagesDiv').show();
              }
            }
 		  },
          method: 'post', 
          parameters: pars, 
          evalScripts: true
        });  
};

/**
 * Retrievs the case registration form for the currently selected policy, via an AJAX call,
 * and displays it.
 */
GSBIO.bigr.reg.getCaseRegistrationForm = function() {
  // Get the policy id, either from the user directly selecting the policy, or from the user
  // selecting the consent version, from which we can get the policy id via the PolicyIds hash.
  var newPolicyId = $F('policyId');
  if (!newPolicyId) {
    newPolicyId = GSBIO.bigr.reg.PolicyIds[$F('consentVersionId')];
  }
  var newRegFormId = GSBIO.bigr.reg.RegFormIds[newPolicyId];
  
  // Only make the AJAX call to get a new registration form if the newly selected policy
  // has a different registration form, and if the DIV holding the policy specific elements
  // is not empty.  The DIV will be empty if the user switched their choice of linked/unlinked,
  // so in that case we want to make the AJAX call even if the registration form is not different
  // since we blew away the elements of the registration form when the user selected 
  // linked/unlinked.
  if (!($('policySpecific').empty())) {
    if (GSBIO.bigr.reg.CurrentPolicyId) {
      var curRegFormId = GSBIO.bigr.reg.RegFormIds[GSBIO.bigr.reg.CurrentPolicyId];
      if (curRegFormId == newRegFormId) {
      
        // Even though we're not making the AJAX call to get the new registration form, we still
        // have to save the newly selected policy id.
        GSBIO.bigr.reg.CurrentPolicyId = newPolicyId;
        return;
      }
    }
  }

  // Disable buttons and hide the existing data elements.
  GSBIO.bigr.reg.setActionButtonEnabling(false);
  $('policySpecific').hide();
  
  // Clear any errors or messages that may be showing.
  if ($('errorsDiv')) {
    $('errorsDiv').update();
  }
  if ($('messagesDiv')) {
    $('messagesDiv').update();
  }
  
  // Save the newly selected policy id.
  GSBIO.bigr.reg.CurrentPolicyId = newPolicyId;

  // The URL to get the sample registration form.
  var url = GSBIO.contextPath + '/dataImport/getCaseRegistrationForm.do';
  
  // Build the list of parameters.  We'll submit all values entered by the user so they will
  // be displayed on the page that is returned.  We also have to submit the policy id.
  var pars = GSBIO.bigr.reg.Elements.getElementValues();
  pars['policyId'] = newPolicyId;
  
  // Clear all BIGR and KC elements.  The appropriate elements will be registered by the new page.
  GSBIO.bigr.reg.Elements.clear();
  GSBIO.kc.data.FormInstances.deleteFormInstance();
  
  // Perform the AJAX call to get and display the new new page.
  var ajaxUpdater = new Ajax.Updater(
        'policySpecific', 
 		url, 
 		{
          onComplete: function() {
            $('policySpecific').show();
            GSBIO.bigr.reg.setActionButtonEnabling(true);
            $('customerId').up('body').focus();
            GSBIO.kc.data.FormInstances.getFormInstance().alternateRowColor();
            if ($('caseErrorsDiv')) {
              if ($('errorsDiv')) {
                var e = $('caseErrorsDiv').innerHTML;
                $('caseErrorsDiv').update();
                $('errorsDiv').update(e);
              }
              else {
                $('caseErrorsDiv').show();
              }
            }
            if ($('caseMessagesDiv')) {
              if ($('messagesDiv')) {
                var m = $('caseMessagesDiv').innerHTML;
                $('caseMessagesDiv').update();
                $('messagesDiv').update(m);
              }
              else {
                $('caseMessagesDiv').show();
              }
            }
 		  },
          method: 'post', 
          parameters: pars, 
          evalScripts: true
        });  
};

GSBIO.bigr.reg.setActionButtonEnabling = function(isEnabled) {
  var buttons = $A(['btnSubmit', 'btnReset', 'btnCancel', 'btnSaveAndCreateForm', 'btnNextSample', 'btnNextCase']);
  buttons.each(function(button) {
    var b = $(button);
    if (b) {
      if (isEnabled) {
        b.enable();
      }
      else {
        b.disable();
      }
    }
  });
};

GSBIO.bigr.reg.adjustVolumeAndWeight = function() {
  if ($('consumedCheckbox').checked) {
    var volume = $('sampleData.volumeAsString');
    var weight = $('sampleData.weightAsString');
    if (volume) {
      volume.clear();
    }
    if (weight) {
      weight.clear();
    }
  }
  GSBIO.bigr.reg.adjustHiddenConsumed();
};

GSBIO.bigr.reg.adjustConsumed = function() {
  var volume = $('sampleData.volumeAsString');
  var weight = $('sampleData.weightAsString');
  var consumed = $('consumedCheckbox');

  // If the only characters in the volume or weight control are 0's or 0's with one
  // decimal place (in an appropriate order) then check the check box.
  // Otherwise uncheck it
  var expr;
  var volFirstDec = (volume) ? volume.value.indexOf(".") : -1;
  var volLastDec = (volume) ? volume.value.lastIndexOf(".") : -1;
  var weightFirstDec = (weight) ? weight.value.indexOf(".") : -1;
  var weightLastDec = (weight) ? weight.value.lastIndexOf(".") : -1;
  var zeroVolume;
  var zeroWeight;
  
  // If there are multiple decimal places then the value is invalid so uncheck
  // the checkbox.
  if (volFirstDec != volLastDec || weightFirstDec != weightLastDec ) {
    consumed.checked = false;
  }
  else {
    if (volume && !weight) {
      expr = (volFirstDec >= 0 ) ? new RegExp("0+\.0*") : new RegExp("0+");
      zeroVolume =  expr.test(volume.value) && isValidChars('[0.]', volume.value);
      consumed.checked = zeroVolume;
    }
    else if (weight && !volume) {
      expr = (weightFirstDec >= 0 ) ? new RegExp("0+\.0*") : new RegExp("0+");	  
      zeroWeight =  expr.test(weight.value) && isValidChars('[0.]', weight.value);	    
      consumed.checked = zeroWeight;
    }
    else if (volume && weight) {
      expr = (volFirstDec >= 0 ) ? new RegExp("0+\.0*") : new RegExp("0+");
      zeroVolume =  expr.test(volume.value) && isValidChars('[0.]', volume.value);	    
      expr = (weightFirstDec >= 0 ) ? new RegExp("0+\.0*") : new RegExp("0+");	  
      zeroWeight =  expr.test(weight.value) && isValidChars('[0.]', weight.value);	    
      consumed.checked = (zeroWeight && zeroVolume);
    }
  }
  GSBIO.bigr.reg.adjustHiddenConsumed();
};

GSBIO.bigr.reg.adjustHiddenConsumed = function() {
  $('sampleData.consumed').value = ($('consumedCheckbox').checked) ? "true" : "false";
};

GSBIO.bigr.reg.clearDateTime = function(which) {
  var prefix = 'sampleData.' + which + 'DateTime';
  $(prefix + '.date').clear();
  $(prefix + '.hour').clear();
  $(prefix + '.minute').clear();
  $(prefix + '.meridian').clearRadios();
};

GSBIO.bigr.reg.verifySampleTypeChange = function() {
  var v = $F('sampleData.sampleTypeCui');
  if (v && (v != $('originalSampleType').getValue())) {
    return confirm('The Sample Type value has been changed, and all data related to the previous sample type will be lost.  Are you sure you wish to change the Sample Type value?');
  }
  else {
    return true;
  }
};

GSBIO.bigr.reg.CurrentLinkedValue = null;

GSBIO.bigr.reg.showConsentsOrPolicies = function() {
  // Show/hide the consent version/policy questions based on the value of linked.
  var linkedValue = $('linkedIndicator').getRadiosValue();
  if (linkedValue == 'Y') {
    $('consentSelect').show();
    $('consentTime').show();
    $('policySelect').hide();
    $('policyId').clear();
  }
  else if (linkedValue == 'N') {
    $('consentSelect').hide();
    $('consentTime').hide();
    $('consentVersionId').clear();
    $('month').clear();
    $('year').clear();
    $('hours').clear();
    $('minutes').clear();
    $('ampm').clear();
    $('policySelect').show();
  }
  else {
    $('consentSelect').hide();
    $('consentTime').hide();
    $('consentVersionId').clear();
    $('month').clear();
    $('year').clear();
    $('hours').clear();
    $('minutes').clear();
    $('ampm').clear();
    $('policySelect').hide();	  
    $('policyId').clear();
  }
  
  // In addition, if the linked value has changed, blow away the registration form.
  if (linkedValue != GSBIO.bigr.reg.CurrentLinkedValue) {
    $('policySpecific').update();
  }
  GSBIO.bigr.reg.CurrentLinkedValue = linkedValue;
};
