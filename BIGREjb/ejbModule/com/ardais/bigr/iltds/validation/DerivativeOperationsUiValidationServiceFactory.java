package com.ardais.bigr.iltds.validation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import sun.security.action.GetBooleanAction;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BtxDetailsDerivationBatch;
import com.ardais.bigr.javabeans.DerivationBatchDto;
import com.ardais.bigr.javabeans.DerivationDto;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.validation.AbstractValidatorFactory;
import com.ardais.bigr.validation.Validator;

/**
 * Factory to provide an appropriately configured ValidationService for the current
 * UI step.
 */
public class DerivativeOperationsUiValidationServiceFactory extends AbstractValidatorFactory {
  
  public static final String UI_STEP1 = "step1";
  public static final String UI_STEP2 = "step2";
  public static final String UI_STEP3 = "step3";
  private static final Set UI_STEPS = new HashSet();  
  static {
    UI_STEPS.add(UI_STEP1);
    UI_STEPS.add(UI_STEP2);
    UI_STEPS.add(UI_STEP3);
  }

  private BtxDetailsDerivationBatch _btxDetails;
  
  public DerivativeOperationsUiValidationServiceFactory(BtxDetailsDerivationBatch btxDetails) {
    super(btxDetails.getLoggedInUserSecurityInfo());
    setBtxDetails(btxDetails);
  }
  
  public Validator getInstance(String step) {
    BtxDetailsDerivationBatch btxDetails = getBtxDetails();
    DerivativeOperationsValidationService validator = null;
    
    if (UI_STEPS.contains(step)) {
      List derivationDtos = getDerivationDtos(btxDetails);
      validator = new DerivativeOperationsValidationService();
      validator.setSecurityInfo(getSecurityInfo());
      validator.setDerivations(derivationDtos);
      if (UI_STEP1.equalsIgnoreCase(step)) {
        configureStep1(validator);
      }
      else if (UI_STEP2.equalsIgnoreCase(step)) {
        configureStep2(validator);
      }
      else if (UI_STEP3.equalsIgnoreCase(step)) {
        configureStep3(validator);
      }
    }
    else {
      throw new ApiException("DerivativeOperationsUiValidationServiceFactory: Unknown UI step (" + step + ") encountered.");
    }
    return validator;
  }
  
  private List getDerivationDtos(BtxDetailsDerivationBatch btxDetails) {
    List returnValue = new ArrayList();
    //if there are no child derivations, just make a DerivationDto out of the
    //basic information.  Otherwise, get the DerivationDto for each child derivation
    //and populate the basic information on each if it isn't already there.  It should
    //already contain the parent/child information, but given the way the UI works the
    //children don't have the basic information populated.
    DerivationBatchDto master = btxDetails.getDto();
    if (ApiFunctions.isEmpty(master.getDerivations())) {
      DerivationDto dto = new DerivationDto(master);
      returnValue.add(dto);
    }
    else {
      Iterator iterator = master.getDerivations().iterator();
      int count = 1;
      while (iterator.hasNext()) {
        DerivationDto dto = (DerivationDto)iterator.next();
        dto.populateEmptyBasicInformation(master);
        dto.setDerivationNumber(count + "");
        count = count + 1;
        returnValue.add(dto);
      }
    }
    return returnValue;
  }
  
  private void configureStep1(DerivativeOperationsValidationService validator) {
    validator.setCheckOperation(true);
    validator.setCheckOperationDate(true);
    validator.setCheckPreparedBy(true);
  }
  
  private void configureStep2(DerivativeOperationsValidationService validator) {
    configureStep1(validator);
    validator.setCheckParentSampleIds(true);
    validator.setCheckChildSampleIds(true);
    validator.setCheckGlobalParentSampleIds(true);
    validator.setCheckGlobalChildSampleIds(true);
    validator.setCheckInvalidDataElements(true);
  }
  
  private void configureStep3(DerivativeOperationsValidationService validator) {
    configureStep1(validator);
    validator.setCheckParentSamples(true);
    validator.setCheckChildSamples(true);
    validator.setCheckInventoryGroupSpecifications(true);
    validator.setCheckGlobalParentSampleIds(true);
    validator.setCheckGlobalChildSampleIds(true);
    validator.setCheckGlobalChildSampleAliases(true);
  }

  private BtxDetailsDerivationBatch getBtxDetails() {
    return _btxDetails;
  }

  private void setBtxDetails(BtxDetailsDerivationBatch btxDetails) {
    if (btxDetails == null) {
      throw new ApiException("DerivativeOperationsUiValidationServiceFactory: No BTX details specified.");
    }
    _btxDetails = btxDetails;
  }

}
