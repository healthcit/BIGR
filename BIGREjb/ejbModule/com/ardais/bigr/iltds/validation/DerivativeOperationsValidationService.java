package com.ardais.bigr.iltds.validation;

import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.javabeans.AccountDto;
import com.ardais.bigr.javabeans.DerivationDto;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.validation.AbstractValidationService;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorFactory;

/**
 * The validation service for derivative operations.  To use this service, set the SecurityInfo
 * and list of derivations to be validated, call one or more setCheck* methods to indicate which 
 * validations are to be performed, and then call the validate method to perform validation. 
 */
public class DerivativeOperationsValidationService extends AbstractValidationService {

  /**
   * The list of derivations to be validated.  Each must be a DerivationDto instance.
   */
  private List _derivations;

  private boolean _checkOperation;
  private boolean _checkOperationDate;
  private boolean _checkPreparedBy;
  private boolean _checkParentSampleIds;
  private boolean _checkParentSamples;
  private boolean _checkGlobalParentSampleIds;
  private boolean _checkChildSampleIds;
  private boolean _checkChildSamples;
  private boolean _checkGlobalChildSampleIds;
  private boolean _checkInventoryGroupSpecifications;
  private boolean _checkInvalidDataElements;
  private boolean _checkGlobalChildSampleAliases;
  
  /**
   * Creates a new DerivativeOperationsValidationService.
   */
  public DerivativeOperationsValidationService() {
    super();
  }

  private List getDerivations() {
    return _derivations;
  }

  /**
   * Sets the the derivations to be validated.
   * 
   * @param derivations the list of derivations to be validated, each of which is a DerivationDto 
   *                    instance
   */
  public void setDerivations(List derivations) {
    if (ApiFunctions.isEmpty(derivations)) {
      throw new ApiException("DerivativeOperationsValidationService: No derivation information provided.");
    }
    _derivations = derivations;
  }
  
  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.ValidationService#validate()
   */
  public BtxActionErrors validate() {
    
    boolean didValidation = false;    
    SecurityInfo securityInfo = getSecurityInfo();  
    BtxActionErrors errors = getActionErrors();
    List derivations = getDerivations();
    Iterator iterator = derivations.iterator();
    Validator validator = null; 
    while (iterator.hasNext()) {
      DerivationDto dto = (DerivationDto)iterator.next();
      ValidatorFactory factory = new DerivativeOperationsValidatorFactory(securityInfo, dto);
    
      //note - there is currently no ordering dependency among the validation
      //checks that may be requested, but if that should change then this is
      //where that requirement would be handled.
      if (isCheckOperation()) {
        didValidation = true;
        validator = factory.getInstance(DerivativeOperationsValidatorFactory.OPERATION); 
        errors.addErrors(validator.validate());          
      }
      if (isCheckOperationDate()) {
        didValidation = true;
        validator = factory.getInstance(DerivativeOperationsValidatorFactory.OPERATION_DATE);
        errors.addErrors(validator.validate());          
      }
      if (isCheckPreparedBy()) {
        didValidation = true;
        validator = factory.getInstance(DerivativeOperationsValidatorFactory.PREPARED_BY);
        errors.addErrors(validator.validate());          
      }
      if (isCheckParentSampleIds()) {
        didValidation = true;
        validator = factory.getInstance(DerivativeOperationsValidatorFactory.PARENT_SAMPLE_IDS);
        errors.addErrors(validator.validate());          
      }
      if (isCheckChildSampleIds()) {
        didValidation = true;
        validator = factory.getInstance(DerivativeOperationsValidatorFactory.CHILD_SAMPLE_IDS);
        errors.addErrors(validator.validate());          
      }
      if (isCheckParentSamples()) {
        didValidation = true;
        validator = factory.getInstance(DerivativeOperationsValidatorFactory.PARENT_SAMPLES);
        errors.addErrors(validator.validate());          
      }
      if (isCheckChildSamples()) {
        didValidation = true;
        validator = factory.getInstance(DerivativeOperationsValidatorFactory.CHILD_SAMPLES);
        errors.addErrors(validator.validate());          
      }
      if (isCheckInventoryGroupSpecifications()) {
        didValidation = true;
        validator = factory.getInstance(DerivativeOperationsValidatorFactory.INVENTORY_GROUP_SPECIFICATIONS);
        errors.addErrors(validator.validate());          
      }
      if (isCheckInvalidDataElements()) {
        didValidation = true;
        validator = factory.getInstance(DerivativeOperationsValidatorFactory.INVALID_DATA_ELEMENTS);
        errors.addErrors(validator.validate());          
      }
    }
    
    if (isCheckGlobalChildSampleIds()) {
      didValidation = true;    
      ValidateIds idValidator = new ValidateIds();
      DerivationDto globalDto = new DerivationDto();
      iterator = derivations.iterator();
      while (iterator.hasNext()) {
        DerivationDto dto = (DerivationDto)iterator.next();
        Iterator children = dto.getChildren().iterator();
        while (children.hasNext()) {
          //if the child was specified via an alias, see if that alias corresponds to an existing
          //box-scanned sample id and if so populate the sample with the sample id.  This is 
          //necessary to handle the case of a child being specified by an alias in one place and a 
          //sample id in another (unlikely, but could happen)
          SampleData child = (SampleData)children.next();
          String sampleId = child.getSampleId();

          // Convert the id into a fully formed version (imported or traditional sample).
          String validatedId = idValidator.validate(sampleId, ValidateIds.TYPESET_SAMPLE, true);
          //if we were able to get the fully formed version of the id, set it on the sample
          if (!ApiFunctions.isEmpty(validatedId)) {
            child.setSampleId(validatedId);
            globalDto.addChild(child);
          }
          //if not, the value specified could be a sample alias.  Query to see if there are any
          //existing samples (box scanned or fully registered) with that alias value and do the 
          //following:
          //  - If a single match is found and the account for the user requires 
          //    unique aliases, set the corresponding sample id on this child and add it to the
          //    global list of children to be checked for duplicates.
          //  - Otherwise do nothing as there is a problem with this child that will have been 
          //    caught when we validated the children individually.  
          else {
            String alias = ApiFunctions.safeTrim(sampleId);
            if (!ApiFunctions.isEmpty(alias)) {
              String accountId = getSecurityInfo().getAccount();
              AccountDto accountDto = IltdsUtils.getAccountById(accountId, false, false);
              boolean aliasMustBeUnique = FormLogic.DB_YES.equalsIgnoreCase(accountDto.getRequireUniqueSampleAliases());
              if (aliasMustBeUnique) {
                //first see if we can map the alias to an existing box-scanned sample and if so do it
                List matchingSamples = IltdsUtils.findBoxScannedSamplesFromCustomerId(alias, accountId);
                if (matchingSamples.size() == 1) {
                  SampleData matchingSample = (SampleData)matchingSamples.get(0);
                  String boxScannedSampleId = matchingSample.getSampleId();
                  child.setSampleId(boxScannedSampleId);
                  //copy the sampleId to the sample alias, since we're making the assumption an alias was
                  //specified in place of a sample id.
                  child.setSampleAlias(alias);
                  globalDto.addChild(child);
                }
                //next see if the sample matches a fully registered sample and if so return an error.
                matchingSamples = IltdsUtils.findSamplesFromCustomerId(alias, accountId);
                if (matchingSamples.size() == 1) {
                  SampleData matchingSample = (SampleData)matchingSamples.get(0);
                  String fullyRegisteredSampleId = matchingSample.getSampleId();
                  child.setSampleId(fullyRegisteredSampleId);
                  //copy the sampleId to the sample alias, since we're making the assumption an alias was
                  //specified in place of a sample id.
                  child.setSampleAlias(alias);
                  globalDto.addChild(child);
                }
              }
            }
          }
        }
      }
      ValidatorFactory factory = new DerivativeOperationsValidatorFactory(securityInfo, globalDto);
      validator = factory.getInstance(DerivativeOperationsValidatorFactory.GLOBAL_CHILD_SAMPLE_IDS);
      errors.addErrors(validator.validate());          
    }

    if (isCheckGlobalParentSampleIds()) {
      didValidation = true;    
      ValidateIds idValidator = new ValidateIds();
      DerivationDto globalDto = new DerivationDto();
      iterator = derivations.iterator();
      while (iterator.hasNext()) {
        DerivationDto dto = (DerivationDto)iterator.next();
        Iterator parents = dto.getParents().iterator();
        while (parents.hasNext()) {
          //if the parent was specified via an alias, see if that alias corresponds to an existing
          //sample id and if so populate the sample with the sample id.  This is necessary to handle
          //the case of a parent being specified by an alias in one place and a sample id in 
          //another (unlikely, but could happen)
          SampleData parent = (SampleData)parents.next();
          String sampleId = parent.getSampleId();

          // Convert the id into a fully formed version (imported or traditional sample).
          String validatedId = idValidator.validate(sampleId, ValidateIds.TYPESET_SAMPLE, true);
          //if we were able to get the fully formed version of the id, set it on the sample and add
          //the parent to the dto
          if (!ApiFunctions.isEmpty(validatedId)) {
            parent.setSampleId(validatedId);
            globalDto.addParent(parent);
          }
          //if not, the value specified could be a sample alias.  Query to see if there are any
          //samples with that alias value and do the following:
          //  - If a single match is found and the account to which that sample belongs requires 
          //    unique aliases, set the corresponding sample id on this parent and add it to the
          //    global list of parents to be checked for duplicates.
          //  - Otherwise do nothing as there is a problem with this parent that will have been 
          //    caught when we validated the parents individually.  
          //Note that the search for a match is independent of account, since a system owner is not 
          //limited to using only samples within their account as parents.
          else {
            String alias = ApiFunctions.safeTrim(sampleId);
            if (!ApiFunctions.isEmpty(alias)) {
              List samplesWithMatchingAlias = IltdsUtils.findSamplesFromCustomerId(sampleId, false);
              int matchCount = samplesWithMatchingAlias.size();
              if (matchCount == 1) {
                String matchingSampleId = ((SampleData)samplesWithMatchingAlias.get(0)).getSampleId();
                String accountId = IltdsUtils.getAccountAssignedToSample(matchingSampleId);
                AccountDto accountDto = IltdsUtils.getAccountById(accountId, false, false);
                boolean aliasMustBeUnique = FormLogic.DB_YES.equalsIgnoreCase(accountDto.getRequireUniqueSampleAliases());
                if (aliasMustBeUnique) {
                  validatedId = ((SampleData)samplesWithMatchingAlias.get(0)).getSampleId();
                  parent.setSampleId(validatedId);
                  //move the id to the alias field, since that is what the value represents.
                  parent.setSampleAlias(sampleId);
                  globalDto.addParent(parent);
                }
              }
            }
          }
        }
      }
      ValidatorFactory factory = new DerivativeOperationsValidatorFactory(securityInfo, globalDto);
      validator = factory.getInstance(DerivativeOperationsValidatorFactory.GLOBAL_PARENT_SAMPLE_IDS);
      errors.addErrors(validator.validate());          
    }
    
    if (isCheckGlobalChildSampleAliases()) {
      didValidation = true;    
      DerivationDto globalDto = new DerivationDto();
      iterator = derivations.iterator();
      while (iterator.hasNext()) {
        DerivationDto dto = (DerivationDto)iterator.next();
        Iterator children = dto.getChildren().iterator();
        while (children.hasNext()) {
          globalDto.addChild((SampleData)children.next());
        }
      }
      ValidatorFactory factory = new DerivativeOperationsValidatorFactory(securityInfo, globalDto);
      validator = factory.getInstance(DerivativeOperationsValidatorFactory.GLOBAL_CHILD_SAMPLE_ALIASES);
      errors.addErrors(validator.validate());          
    }

    // If we did not do any validations, then throw an exception. 
    if (!didValidation) {
      throw new ApiException("DerivativeOperationsValidationService: No validation checks requested.");
    }

    return getActionErrors();
  }

  private boolean isCheckOperation() {
    return _checkOperation;
  }

  private boolean isCheckOperationDate() {
    return _checkOperationDate;
  }

  private boolean isCheckPreparedBy() {
    return _checkPreparedBy;
  }

  private boolean isCheckParentSampleIds() {
    return _checkParentSampleIds;
  }

  private boolean isCheckGlobalParentSampleIds() {
    return _checkGlobalParentSampleIds;
  }

  private boolean isCheckChildSampleIds() {
    return _checkChildSampleIds;
  }

  private boolean isCheckGlobalChildSampleIds() {
    return _checkGlobalChildSampleIds;
  }

  private boolean isCheckChildSamples() {
    return _checkChildSamples;
  }

  private boolean isCheckParentSamples() {
    return _checkParentSamples;
  }

  private boolean isCheckInventoryGroupSpecifications() {
    return _checkInventoryGroupSpecifications;
  }

  public void setCheckOperation(boolean b) {
    _checkOperation = b;
  }

  public void setCheckOperationDate(boolean b) {
    _checkOperationDate = b;
  }

  public void setCheckPreparedBy(boolean b) {
    _checkPreparedBy = b;
  }

  public void setCheckParentSampleIds(boolean b) {
    _checkParentSampleIds = b;
  }

  public void setCheckGlobalParentSampleIds(boolean b) {
    _checkGlobalParentSampleIds = b;
  }

  public void setCheckChildSampleIds(boolean b) {
    _checkChildSampleIds = b;
  }

  public void setCheckGlobalChildSampleIds(boolean b) {
    _checkGlobalChildSampleIds = b;
  }

  public void setCheckChildSamples(boolean b) {
    _checkChildSamples = b;
  }

  public void setCheckParentSamples(boolean b) {
    _checkParentSamples = b;
  }

  public void setCheckInventoryGroupSpecifications(boolean b) {
    _checkInventoryGroupSpecifications = b;
  }

  public boolean isCheckInvalidDataElements() {
    return _checkInvalidDataElements;
  }
  
  public void setCheckInvalidDataElements(boolean checkInvalidDataElements) {
    _checkInvalidDataElements = checkInvalidDataElements;
  }
  
  public boolean isCheckGlobalChildSampleAliases() {
    return _checkGlobalChildSampleAliases;
  }

  public void setCheckGlobalChildSampleAliases(boolean checkGlobalChildSampleAliases) {
    _checkGlobalChildSampleAliases = checkGlobalChildSampleAliases;
  }
}
