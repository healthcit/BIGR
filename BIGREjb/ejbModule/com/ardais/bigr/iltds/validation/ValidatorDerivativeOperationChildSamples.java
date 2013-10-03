package com.ardais.bigr.iltds.validation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.iltds.bizlogic.DerivationOperation;
import com.ardais.bigr.iltds.bizlogic.DerivationOperationFactory;
import com.ardais.bigr.iltds.bizlogic.SampleFinder;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.iltds.helpers.SampleSelect;
import com.ardais.bigr.javabeans.AccountDto;
import com.ardais.bigr.javabeans.DerivationDto;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.gulfstreambio.bigr.error.BigrValidationException;
import com.gulfstreambio.bigr.id.SampleIdService;

/**
 * Validates the child samples for a single derivative operation.  Performs many different
 * validations and may return one or more of the following errors, with insertion strings listed 
 * below the error:
 * <ul>
 * <li>{@link #ERROR_KEY1} - No child samples were specified.
 *   <ol>
 *   <li>The name of the property containing the value.  This must be supplied by the caller
 *       by calling {@link #setPropertyDisplayName(java.lang.String) setPropertyDisplayName}. 
 *   </li>
 *   </ol>
 * </li>
 * <li>{@link #ERROR_KEY2} - A child with the specified sample type is not creatable based on the 
 *     derivative operation and policy of the parent sample(s).
 *   <ol>
 *   <li>The current child's sample barcode, as obtained by calling 
 *       {@link com.ardais.bigr.javabeans.SampleData#getSampleId() getSampleId} on the
 *       sample returned by {@link #getCurrentChildSample() getCurrentChildSample}.
 *   </li>
 *   <li>The current child's sample type, as obtained by calling 
 *       {@link com.ardais.bigr.javabeans.SampleData#getSampleType() getSampleType} on the
 *       sample returned by {@link #getCurrentChildSample() getCurrentChildSample}.
 *   </li>
 *   <li>The name of the operation.  This is obtained by calling 
 *       {@link com.ardais.bigr.javabeans.DerivationDto#getOperation() getOperation} on the
 *       DTO returned by {@link #getDto() getDto}.
 *   </li>
 *   </ol>
 * </li>
 * <li>{@link #ERROR_KEY3} - One or more of the child sample barcodes is syntactically incorrect 
 *     or specifies a sample that cannot be created by the specified user.
 *   <ol>
 *   <li>The name of the property containing the value.  This must be supplied by the caller
 *       by calling {@link #setPropertyDisplayName(java.lang.String) setPropertyDisplayName}. 
 *   </li>
 *   <li>The list of sample barcodes as obtained by calling 
 *       {@link #getNonExistingIds() getNonExistingIds}.
 *   </li>
 *   </ol>
 * </li>
 * <li>{@link #ERROR_KEY4} - One or more of the child sample barcodes specifies a sample that 
 *      has already been created.
 *   <ol>
 *   <li>The name of the property containing the value.  This must be supplied by the caller
 *       by calling {@link #setPropertyDisplayName(java.lang.String) setPropertyDisplayName}. 
 *   </li>
 *   <li>The list of sample barcodes as obtained by calling 
 *       {@link #getCreatedIds() getCreatedIds}.
 *   </li>
 *   </ol>
 * </li>
 * <li>{@link #ERROR_KEY5} - One or more of the child sample barcodes are in the range of 
 *     assigned ids for an account other than the account of the parent sample(s).
 *   <ol>
 *   <li>The name of the property containing the value.  This must be supplied by the caller
 *       by calling {@link #setPropertyDisplayName(java.lang.String) setPropertyDisplayName}. 
 *   </li>
 *   <li>The list of sample barcodes as obtained by calling 
 *       {@link #getIdsInDifferentAccount() getIdsInDifferentAccount}.
 *   </li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorDerivativeOperationChildSamples extends AbstractValidator  {
  
  /**
   * The key of the error that is returned if no child samples were specified.
   */
  public final static String ERROR_KEY1 = "error.noValuesSpecified";
  
  /**
   * The key of the error that is returned if a child with the specified sample type is not 
   * creatable based on the derivative operation and policy of the parent sample(s).
   */
  public final static String ERROR_KEY2 =
    "iltds.error.genealogy.invalidChildSampleTypeForOperation";

  /**
   * The key of the error that is returned if one or more of the child sample barcodes is
   * syntactically incorrect or specifies a sample that cannot be created by the specified user.
   */
  public final static String ERROR_KEY3 = "iltds.error.genealogy.unknownSamples";
  
  /**
   * The key of the error that is returned if one or more of the child sample barcodes specifies
   * a sample that has already been created.
   */
  public final static String ERROR_KEY4 = "iltds.error.genealogy.createdSamples";
  
  /**
   * The key of the error that is returned if one or more of the child sample barcodes are in the 
   * range of assigned ids for an account other than the account of the parent sample(s).
   */
  public final static String ERROR_KEY5 = "iltds.error.genealogy.sampleIdsBelongToDifferentAccount";
  
  /**
   * The key of the error that is returned if one or more of the child sample types are missing.
   */
  public final static String ERROR_KEY6 = "error.noValue";
  
  private DerivationDto _dto;
  private boolean _checkIdsOnly;
  private SampleData _currentChildSample;
  private List nonExistingIds;
  private List createdIds;
  private List invalidIdsForConsent;

  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorDerivativeOperationChildSamples v1 = (ValidatorDerivativeOperationChildSamples) v;
      if (errorKey.equals(ValidatorDerivativeOperationChildSamples.ERROR_KEY1)) {
        v1.addErrorMessage(errorKey, v1.getPropertyDisplayName());
      }
      else if (errorKey.equals(ValidatorDerivativeOperationChildSamples.ERROR_KEY2)) {
        SampleData child = v1.getCurrentChildSample(); 
        v1.addErrorMessage(
          errorKey,
          child.getSampleId(),
          child.getSampleType(),
          v1.getDto().getOperation());
      }
      else if (errorKey.equals(ValidatorDerivativeOperationChildSamples.ERROR_KEY3)) {
        String ids =
          ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getNonExistingIds()));
        v1.addErrorMessage(errorKey, v1.getPropertyDisplayName(), ids);
      }
      else if (errorKey.equals(ValidatorDerivativeOperationChildSamples.ERROR_KEY4)) {
        String ids =
          Escaper.htmlEscapeAndPreserveWhitespace(ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getCreatedIds())));
        v1.addErrorMessage(errorKey, v1.getPropertyDisplayName(), ids);
      }
      else if (errorKey.equals(ValidatorDerivativeOperationChildSamples.ERROR_KEY5)) {
        String ids =
          ApiFunctions.toCommaSeparatedList(
            ApiFunctions.toStringArray(v1.getIdsInDifferentAccount()));
        v1.addErrorMessage(errorKey, v1.getPropertyDisplayName(), ids);
      }
      else if (errorKey.equals(ValidatorDerivativeOperationChildSamples.ERROR_KEY6)) {
        v1.addErrorMessage(errorKey, "sample type");
      }
      else {
        return false;
      }
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorDerivativeOperationChildSamples</code> validator.
   */
  public ValidatorDerivativeOperationChildSamples() {
    super();
    addErrorKey(ERROR_KEY1);
    addErrorKey(ERROR_KEY2);
    addErrorKey(ERROR_KEY3);
    addErrorKey(ERROR_KEY4);
    addErrorKey(ERROR_KEY5);
    addErrorKey(ERROR_KEY6);
    ValidatorErrorListener listener = new DefaultErrorListener();
    addValidatorErrorListener(listener, ERROR_KEY1);
    addValidatorErrorListener(listener, ERROR_KEY2);
    addValidatorErrorListener(listener, ERROR_KEY3);
    addValidatorErrorListener(listener, ERROR_KEY4);
    addValidatorErrorListener(listener, ERROR_KEY5);
    addValidatorErrorListener(listener, ERROR_KEY6);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.AbstractValidator#validate()
   */
  public BtxActionErrors validate() {
    // Note: Since the current rules are that all parents must already be created and all children 
    // must not already be created, the rule that a sample cannot be its own ancestor cannot be
    // violated.  If we allow parents to not already exist or children to already exist in the
    // future, then we'll have to enforce this ancestry rule.  If we have to enforce this,
    // consider using a DirectedAcyclicGraph.

    // If there are no child samples, return an error since each derivation operation requires 
    // at least one.
    DerivationDto dto = getDto();
    if (ApiFunctions.isEmpty(dto.getChildren())) {
      notifyValidatorErrorListener(ERROR_KEY1);
    }

    else {
      doValidate();
    }
    
    return getActionErrors();
  }

  /*
   * Perform the validations.
   */  
  private void doValidate() {
    DerivationDto dto = getDto();
    ValidateIds idValidator = new ValidateIds();
    List nonExistingIds = new ArrayList();
    List createdIds = new ArrayList();
    List invalidIdsForConsent = new ArrayList();
    List validSampleIds = new ArrayList();
    Set consentIds = new HashSet();
    String consentAccountId = null;

    DerivationOperation derivOp = null;
    List validChildSampleTypes = null;
    
    // If we are to validate full child sample information, create the appropriate derivation 
    // operation instance and populate it with parents, since we're going to need it to populate 
    // the children from the parents before we validate full child information.
    if (!isCheckIdsOnly()) {
      derivOp = DerivationOperationFactory.SINGLETON.createDerivationOperation(getDto());
    }

    Iterator parentIterator = dto.getParents().iterator();
    while (parentIterator.hasNext()) {
      SampleData parent = (SampleData) parentIterator.next();
      SampleData persistedParent = 
        SampleFinder.find(getSecurityInfo(), SampleSelect.BASIC_IMPORTED, parent.getSampleId());
      //hang on to the consent ids, so we can do some validation on the sample ids
      //if all of the parents came from the same consent
      if (persistedParent != null) {
        consentIds.add(persistedParent.getConsentId());
        //if we're doing full validation, populate the deriv op with this parent
        if (!isCheckIdsOnly()) {
          derivOp.addParentSample(persistedParent);
        }
      }
    }

    // If we are to validate full child sample information, also get the list of valid child 
    // types for this operation/parent combination.
    if (!isCheckIdsOnly()) {
      validChildSampleTypes = derivOp.findValidChildSampleTypes(); 
    }
      
    //if all of the parents came from a common consent, get the account to which the
    //consent belongs as we might need to validate that the child ids have been assigned to 
    //that account
    if (consentIds.size() == 1) {
      Iterator iterator = consentIds.iterator();
      while (iterator.hasNext()) {
         String consentId = (String)iterator.next();
         consentAccountId = IltdsUtils.getAccountForCase(consentId);
      }
    }
    
    //get a reference to the sample id service, in case child sample ids are to be generated.
    SampleIdService service = SampleIdService.getInstance();

    boolean invalidChildFound = false;    
    String accountId = getSecurityInfo().getAccount();
    AccountDto accountDto = IltdsUtils.getAccountById(accountId, false, false);
    boolean aliasMustBeUnique = FormLogic.DB_YES.equalsIgnoreCase(accountDto.getRequireUniqueSampleAliases());
    Iterator childIterator = dto.getChildren().iterator();
    while (childIterator.hasNext()) {
      SampleData child = (SampleData) childIterator.next();
      setCurrentChildSample(child);
      String sampleId = ApiFunctions.safeTrim(child.getSampleId());
      String sampleTypeCui = child.getSampleTypeCui();

      //If the child sample id starts with any of the known sample prefixes (SX, FR, PA) then
      //assume the user meant to indicate a sample id and proceed as normal.  Otherwise assume they 
      //meant to indicate a sample alias and do the following:
      //   - if unique alias values are required and the specified alias maps to a single previously 
      //     box scanned (but not fully registered) sample, get the id for that sample and use it as 
      //     we assume the user is registering the previously box-scanned sample.
      //   - if unique alias values are required and the specified alias maps to a single previously 
      //     fully registered sample, return an error since the new alias cannot be used.
      //   - any other situation (i.e. unique aliases are not required, or they are but there isn't
      //     a single box-scanned sample or fully registered sample that corresponds to the 
      //     specified alias) we assume a new sample is to be created so a sample id must be 
      //     generated.
      boolean sampleIdAssumed = (sampleId.length() > 2) && 
                                (ValidateIds.TYPESET_SAMPLE.contains(sampleId.substring(0, 2)));
      if (!sampleIdAssumed) {
        boolean foundMatchingSample = false;
        String alias = sampleId;
        //if alias values are required to be unique, then see if we can map this alias to an
        //existing sample
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
            foundMatchingSample = true;
            sampleId = boxScannedSampleId;
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
            foundMatchingSample = true;
            sampleId = fullyRegisteredSampleId;
          }
        }
        //if we couldn't map the alias to a single box-scanned or fully registered sample, assume it
        //is a new sample and we therefore need to generate an id for it
        if (!foundMatchingSample) {
          try {
            String generatedSampleId = service.generateId(getSecurityInfo());
            child.setGeneratedSampleId(generatedSampleId);
            child.setSampleId(generatedSampleId);
            // If we generated a child id, then we assume that the user will want to print a label as 
            // well so set the print flag.  The user may choose to override this in the UI,
            // but this is a reasonable default.
            child.setPrintLabel(true);
            //copy the sampleId to the sample alias, since we're making the assumption an alias was
            //specified in place of a sample id.
            child.setSampleAlias(sampleId);
            //set the sample id to be the generated value
            sampleId = generatedSampleId;
          }
          catch (BigrValidationException bve) {
            //do nothing - a sample id could not be generated and the following code will take care
            //of informing the user there was a problem with the child.
          }
        }
      }
      
      // If the child sample id doesn't have the correct syntax of an imported sample, add it
      // to the unknown list
      String validatedId = 
        idValidator.validate(sampleId, ValidateIds.TYPESET_SAMPLE_IMPORTED, true);
      
      if (ApiFunctions.isEmpty(validatedId)) {
        nonExistingIds.add(sampleId);
        invalidChildFound = true;
      }
      else {
        // Update the child's sample id with the validated version.
        child.setSampleId(validatedId);

        String accountAssignedToSample = IltdsUtils.getAccountAssignedToSample(validatedId);
        
        // If the child sample id is not creatable by this user, add it to the unknown list.
        if (!IltdsUtils.isSampleCreatableByAccount(validatedId, getSecurityInfo())) {
          nonExistingIds.add(validatedId);
          invalidChildFound = true;
        }
      
        //if the child sample is not assigned to the Ardais account and is different from the
        //account to which the consent belongs, add it to the invalid ids for consent list
        else if (!ApiFunctions.isEmpty(accountAssignedToSample) &&
                 !ApiFunctions.isEmpty(consentAccountId) &&
                 !(getSecurityInfo().isInRoleSystemOwner()) &&
                 !accountAssignedToSample.equals(consentAccountId)) {
          invalidIdsForConsent.add(validatedId);           
          invalidChildFound = true;
        }

        // If the child sample has already been imported, add it to the created list.
        else if (IltdsUtils.isSampleImported(validatedId)) {
          createdIds.add(IltdsUtils.getSampleIdAndAlias(child));
          invalidChildFound = true;
        }

        // Make sure that a sample type was specified.  
        else if (!isCheckIdsOnly() && ApiFunctions.isEmpty(sampleTypeCui)) {
          notifyValidatorErrorListener(ERROR_KEY6);
          invalidChildFound = true;
        }
        
        // Make sure that the sample type is valid based on the parents and the policy.  
        else if (
          !isCheckIdsOnly()
            && !ApiFunctions.isEmpty(sampleTypeCui)
            && !validChildSampleTypes.contains(sampleTypeCui)) {
          notifyValidatorErrorListener(ERROR_KEY2);
          invalidChildFound = true;
        }

        // Otherwise this id is valid, so add the child to the list of child samples to be populated
        // if we're checking full sample details.  Create a copy of the child so we don't alter the
        // original child sample.
        else if (!isCheckIdsOnly()) {
          derivOp.addChildSample(new SampleData(child));
        }
      }
    }
  
    // Add an error if there are any non-existing samples.
    if (nonExistingIds.size() > 0) {
      setNonExistingIds(nonExistingIds);
      notifyValidatorErrorListener(ERROR_KEY3);
    }

    // Add an error if there are any created samples.
    if (createdIds.size() > 0) {
      setCreatedIds(createdIds);
      notifyValidatorErrorListener(ERROR_KEY4);
    }
    
    // Add an error if there are any samples with an id assigned to an account that
    // does not match the account of the case to which the parent(s) belong
    if (invalidIdsForConsent.size() > 0) {
      setIdsInDifferentAccount(invalidIdsForConsent);
      notifyValidatorErrorListener(ERROR_KEY5);
    }

    // If we're checking full sample details, then populate the children from the parents and
    // validate the children by calling the create sample validation. 
    if (!isCheckIdsOnly() && !invalidChildFound) {
      List children = derivOp.populateChildren(getSecurityInfo(), dto);
      SampleOperationsValidationService validator = new SampleOperationsValidationService();
      validator.setSecurityInfo(getSecurityInfo());
      validator.setSamples(children);
      validator.setDerivativeOperationAction(true);
      validator.setCheckCreateImportedSample(true);
      getActionErrors().addErrors(validator.validate());      
    }
  }
  
  public boolean isCheckIdsOnly() {
    return _checkIdsOnly;
  }

  /**
   * Indicates whether only the child sample ids should be validated, or whether full child sample
   * information should be validated.
   * 
   * @param b  true if only child sample ids should be validated; false if all child sample
   *           information should be validated 
   */
  public void setCheckIdsOnly(boolean b) {
    _checkIdsOnly = b;
  }

  public DerivationDto getDto() {
    return _dto;
  }

  /**
   * Sets the DerivationDto that contains the details of the derivation operation that holds the
   * child samples to validate.
   * 
   * @param dto  the DerivationDto
   */
  public void setDto(DerivationDto dto) {
    _dto = dto;
  }

  /**
   * Returns the child sample that is currently being validated when an error occurs.  This can
   * be used by listeners to form insertion strings if necessary. 
   * 
   * @return  The current child sample.
   */
  public SampleData getCurrentChildSample() {
    return _currentChildSample;
  }

  private void setCurrentChildSample(SampleData data) {
    _currentChildSample = data;
  }
  /**
   * Returns the list of child sample barcodes for samples that have already been created
   * in the derivation operation being validated.  This may be useful as an insertion string 
   * when forming an error message.
   * 
   * @return  The list of already created barcodes.
   * @return
   */
  public List getCreatedIds() {
    return createdIds;
  }

  /**
   * Returns the list of child sample barcodes that are in the range of assigned ids for an
   * account other than the account of the parent sample(s).  This may be useful as an insertion 
   * string when forming an error message.
   * 
   * @return  The list of child barcodes in a different account.
   */
  public List getIdsInDifferentAccount() {
    return invalidIdsForConsent;
  }

  /**
   * Returns the list of child sample barcodes for samples that do not exist
   * in the derivation operation being validated.  This may be useful as an insertion string 
   * when forming an error message.
   * 
   * @return  The list of non-existing child barcodes.
   */
  public List getNonExistingIds() {
    return nonExistingIds;
  }

  private void setCreatedIds(List list) {
    createdIds = list;
  }

  private void setIdsInDifferentAccount(List list) {
    invalidIdsForConsent = list;
  }

  private void setNonExistingIds(List list) {
    nonExistingIds = list;
  }

}
