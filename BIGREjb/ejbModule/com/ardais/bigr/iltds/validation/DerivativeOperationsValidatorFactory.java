package com.ardais.bigr.iltds.validation;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.configuration.SystemConfiguration;
import com.ardais.bigr.javabeans.DerivationDto;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.validation.AbstractValidatorFactory;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorCollection;
import com.ardais.bigr.validation.ValidatorCollectionNonProceeding;
import com.ardais.bigr.validation.ValidatorConceptInConceptList;
import com.ardais.bigr.validation.ValidatorDate;
import com.ardais.bigr.validation.ValidatorDatePast;
import com.ardais.bigr.validation.ValidatorNoDuplicateSamples;
import com.ardais.bigr.validation.ValidatorOtherValue;
import com.ardais.bigr.validation.ValidatorRequired;
import com.ardais.bigr.validation.ValidatorUserInAccount;

/**
 * Factory that creates and returns Validator instances for derivative operations.  This factory is
 * intended only to serve the {@link DerivativeOperationsValidationService} class.
 */
class DerivativeOperationsValidatorFactory extends AbstractValidatorFactory {

  public static final String OPERATION = "operation";
  public static final String OPERATION_DATE = "operationDate";
  public static final String PREPARED_BY = "preparedBy";
  public static final String PARENT_SAMPLE_IDS = "parentSampleIds";
  public static final String GLOBAL_PARENT_SAMPLE_IDS = "globalParentSampleIds";
  public static final String PARENT_SAMPLES = "parentSamples";
  public static final String CHILD_SAMPLE_IDS = "childSampleIds";
  public static final String CHILD_SAMPLES = "childSamples";
  public static final String GLOBAL_CHILD_SAMPLE_IDS = "globalChildSampleIds";
  public static final String INVENTORY_GROUP_SPECIFICATIONS = "inventoryGroupSpecifications";
  public static final String INVALID_DATA_ELEMENTS = "invalidDataElements";
  public static final String GLOBAL_CHILD_SAMPLE_ALIASES = "globalChildSampleAliases";
  
  private DerivationDto _dto;

  /**
   * Creates a new DerivativeOperationsValidatorFactory.
   * 
   * @param securityInfo  the SecurityInfo
   * @param dto a DerivationDto that provides the context for creating the requested validator
   */
  DerivativeOperationsValidatorFactory(SecurityInfo securityInfo, DerivationDto dto) {
    super(securityInfo);
    setDto(dto);
  }
  
  /**
   * Returns a Validator instance based on the specified identifier.  
   * 
   * @param  identifier  one of the constants defined in this class  
   */
  public Validator getInstance(String identifier) {
    if (OPERATION.equalsIgnoreCase(identifier)) {
      return createOperationValidator();
    }
    else if (OPERATION_DATE.equalsIgnoreCase(identifier)) {
      return createOperationDateValidator();
    }
    else if (PREPARED_BY.equalsIgnoreCase(identifier)) {
      return createPreparedByValidator();
    }
    else if (PARENT_SAMPLE_IDS.equalsIgnoreCase(identifier)) {
      return createParentSampleIdValidator();
    }
    else if (GLOBAL_PARENT_SAMPLE_IDS.equalsIgnoreCase(identifier)) {
      return createGlobalParentSampleIdValidator();
    }
    else if (PARENT_SAMPLES.equalsIgnoreCase(identifier)) {
      return createParentSampleValidator();
    }
    else if (CHILD_SAMPLE_IDS.equalsIgnoreCase(identifier)) {
      return createChildSampleIdValidator();
    }
    else if (CHILD_SAMPLES.equalsIgnoreCase(identifier)) {
      return createChildSampleValidator();
    }
    else if (GLOBAL_CHILD_SAMPLE_IDS.equalsIgnoreCase(identifier)) {
      return createGlobalChildSampleIdValidator();
    }
    else if (INVENTORY_GROUP_SPECIFICATIONS.equalsIgnoreCase(identifier)) {
      return createInventoryGroupSpecificationValidator();
    }
    else if (INVALID_DATA_ELEMENTS.equalsIgnoreCase(identifier)) {
      return createInvalidDataElementsValidator();
    }
    else if (GLOBAL_CHILD_SAMPLE_ALIASES.equalsIgnoreCase(identifier)) {
      return createGlobalChildSampleAliasValidator();
    }
    else {
      throw new ApiException("DerivativeOperationsValidatorFactory: Unrecognized Validator identifier (" + identifier + ").");
    }
  }
  
  private Validator createOperationValidator() {
    DerivationDto dto = getDto();
    String displayName = "Operation";
    SecurityInfo securityInfo = getSecurityInfo();
    
    ValidatorCollection validators = new ValidatorCollectionNonProceeding();
    
    ValidatorRequired v1 = new ValidatorRequired();
    v1.setSecurityInfo(securityInfo);
    v1.setPropertyDisplayName(displayName);
    v1.setValue(dto.getOperationCui());
    validators.addValidator(v1);

    ValidatorConceptInConceptList v2 = new ValidatorConceptInConceptList();
    v2.setSecurityInfo(securityInfo);
    v2.setPropertyDisplayName(displayName);
    v2.setConceptListName(SystemConfiguration.CONCEPT_LIST_DERIVATION_OPERATIONS);
    v2.setCui(dto.getOperationCui());
    validators.addValidator(v2);

    ValidatorOtherValue v3 = new ValidatorOtherValue();
    v3.setSecurityInfo(securityInfo);
    v3.setPropertyDisplayName(displayName);
    v3.setCui(dto.getOperationCui());
    v3.setOtherCui(ArtsConstants.OTHER_DERIVATION_OPERATION);
    v3.setOtherValue(dto.getOtherOperation());
    validators.addValidator(v3);

    return validators;
  }
  
  private Validator createOperationDateValidator() {
    DerivationDto dto = getDto();
    String displayName = "Date of operation";
    SecurityInfo securityInfo = getSecurityInfo();
    
    ValidatorCollection validators = new ValidatorCollectionNonProceeding();
    
    ValidatorDate v1 = new ValidatorDate();
    v1.setSecurityInfo(securityInfo);
    v1.setPropertyDisplayName(displayName);
    v1.setDate(dto.getOperationDateAsString());
    validators.addValidator(v1);

    ValidatorDatePast v2 = new ValidatorDatePast();
    v2.setSecurityInfo(securityInfo);
    v2.setPropertyDisplayName(displayName);
    v2.setDate(dto.getOperationDate());
    validators.addValidator(v2);

    return validators;
  }
  
  private Validator createPreparedByValidator() {
    SecurityInfo securityInfo = getSecurityInfo();

    ValidatorUserInAccount v = new ValidatorUserInAccount();
    v.setSecurityInfo(securityInfo);
    v.setPropertyDisplayName("Prepared by");
    v.setUserId(getDto().getPreparedBy());
    v.setAccount(securityInfo.getAccount());
    return v;
  }
  
  private Validator createParentSampleIdValidator() {
    ValidatorDerivativeOperationParentSamples v = (ValidatorDerivativeOperationParentSamples)createParentSampleValidator();
    v.setCheckIdsOnly(true);
    return v;
  }
  
  private Validator createParentSampleValidator() {
    DerivationDto dto = getDto();

    ValidatorDerivativeOperationParentSamples v = new ValidatorDerivativeOperationParentSamples();
    v.setSecurityInfo(getSecurityInfo());
    v.setPropertyDisplayName(getPropertyDisplayName("parent samples"));
    v.setDto(dto);
    v.setCheckIdsOnly(false);
    return v;
  }
  
  private Validator createGlobalParentSampleIdValidator() {
    ValidatorNoDuplicateSamples v = new ValidatorNoDuplicateSamples();
    v.setSecurityInfo(getSecurityInfo());
    v.setPropertyDisplayName(getPropertyDisplayName("parent samples"));
    v.setSamples(getDto().getParents());
    return v;
  }

  private Validator createChildSampleIdValidator() {
    ValidatorDerivativeOperationChildSamples v = (ValidatorDerivativeOperationChildSamples)createChildSampleValidator();
    v.setCheckIdsOnly(true);
    return v;
  }
  
  private Validator createChildSampleValidator() {
    DerivationDto dto = getDto();
    ValidatorDerivativeOperationChildSamples v = new ValidatorDerivativeOperationChildSamples();
    v.setSecurityInfo(getSecurityInfo());
    v.setPropertyDisplayName(getPropertyDisplayName("child samples"));
    v.setDto(dto);
    v.setCheckIdsOnly(false);
    return v;
  }
  
  private Validator createGlobalChildSampleIdValidator() {
    ValidatorNoDuplicateSamples v = new ValidatorNoDuplicateSamples();
    v.setSecurityInfo(getSecurityInfo());
    v.setPropertyDisplayName(getPropertyDisplayName("child samples"));
    v.setSamples(getDto().getChildren());
    return v;
  }
  
  private Validator createGlobalChildSampleAliasValidator() {
    ValidatorDerivativeOperationChildAliases v = new ValidatorDerivativeOperationChildAliases();
    v.setSecurityInfo(getSecurityInfo());
    v.setPropertyDisplayName(getPropertyDisplayName("child samples"));
    v.setSamples(getDto().getChildren());
    return v;
  }
  
  private Validator createInventoryGroupSpecificationValidator() {
    DerivationDto dto = getDto();
    ValidatorDerivativeOperationInventoryGroupSpecifications v = new ValidatorDerivativeOperationInventoryGroupSpecifications();
    v.setSecurityInfo(getSecurityInfo());
    v.setPropertyDisplayName(getPropertyDisplayName("inventory groups"));
    v.setDto(dto);
    return v;
  }
  
  private Validator createInvalidDataElementsValidator() {
    ValidatorDerivativeOperationInvalidDataElements v = new ValidatorDerivativeOperationInvalidDataElements();
    v.setSecurityInfo(getSecurityInfo());
    if (!ApiFunctions.isEmpty(getDto().getDerivationNumber())) {
      StringBuffer buff = new StringBuffer(50);
      buff.append(" in derivative operation #");
      buff.append(getDto().getDerivationNumber());
      v.setPropertyDisplayName(buff.toString());
    }
    v.setDto(getDto());
    return v;
  }

  private String getPropertyDisplayName(String displayName) {
    StringBuffer buff = new StringBuffer(50);
    buff.append(displayName);
    if (!ApiFunctions.isEmpty(getDto().getDerivationNumber())) {
      buff.append(" (for derivative operation #");
      buff.append(getDto().getDerivationNumber());
      buff.append(")");
    }
    return buff.toString();
  }

  private DerivationDto getDto() {
    return _dto;
  }

  private void setDto(DerivationDto dto) {
    if (dto == null) {
      throw new ApiException("DerivativeOperationsValidatorFactory: No derivation DTO specified.");
    }
    _dto = dto;
  }

}
