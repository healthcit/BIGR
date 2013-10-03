package com.ardais.bigr.iltds.validation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.configuration.SystemConfiguration;
import com.ardais.bigr.iltds.bizlogic.DerivationOperation;
import com.ardais.bigr.iltds.bizlogic.DerivationOperationFactory;
import com.ardais.bigr.iltds.bizlogic.SampleFinder;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.iltds.databeans.DerivativeOperation;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.iltds.helpers.SampleSelect;
import com.ardais.bigr.javabeans.AccountDto;
import com.ardais.bigr.javabeans.DerivationDto;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.util.IcpUtils;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;

/**
 * Validates the parent samples for a single derivative operation.  Performs many different
 * validations and may return one or more of the following errors, with insertion strings listed 
 * below the error:
 * <ul>
 * <li>{@link #ERROR_KEY1} - No parent samples were specified.
 *   <ol>
 *   <li>The name of the property containing the value.  This must be supplied by the caller
 *       by calling {@link #setPropertyDisplayName(java.lang.String) setPropertyDisplayName}. 
 *   </li>
 *   </ol>
 * </li>
 * <li>{@link #ERROR_KEY2} - Multiple parent samples were specified for a derivative operation
 *      that only allows a single parent.
 *   <ol>
 *   <li>The name of the property containing the value.  This must be supplied by the caller
 *       by calling {@link #setPropertyDisplayName(java.lang.String) setPropertyDisplayName}. 
 *   </li>
 *   <li>The name of the operation.  This is obtained by calling 
 *       {@link com.ardais.bigr.javabeans.DerivationDto#getOperation() getOperation} on the
 *       DTO returned by {@link #getDto() getDto}.
 *   </li>
 *   </ol>
 * </li>
 * <li>{@link #ERROR_KEY3} - The sample type of the parent sample is not allowed for the 
 *     specified derivative operation.
 *   <ol>
 *   <li>The current parent's sample barcode, as obtained by calling 
 *       {@link com.ardais.bigr.javabeans.SampleData#getSampleId() getSampleId} on the
 *       sample returned by {@link #getCurrentParentSample() getCurrentParentSample}.
 *   </li>
 *   <li>The current parent's sample type, as obtained by calling 
 *       {@link com.ardais.bigr.javabeans.SampleData#getSampleType() getSampleType} on the
 *       sample returned by {@link #getCurrentParentSample() getCurrentParentSample}.
 *   </li>
 *   <li>The name of the operation.  This is obtained by calling 
 *       {@link com.ardais.bigr.javabeans.DerivationDto#getOperation() getOperation} on the
 *       DTO returned by {@link #getDto() getDto}.
 *   </li>
 *   </ol>
 * </li>
 * <li>{@link #ERROR_KEY4} - The type of child samples that could be created from this 
 *     derivative operation cannot be created according to the policy of the parent sample(s). 
 *   <ol>
 *   <li>The current parent's sample barcode, as obtained by calling 
 *       {@link com.ardais.bigr.javabeans.SampleData#getSampleId() getSampleId} on the
 *       sample returned by {@link #getCurrentParentSample() getCurrentParentSample}.
 *   </li>
 *   <li>The name of the policy associated with the current parents sample(s), as obtained by 
 *       calling {@link com.ardais.bigr.iltds.databeans.PolicyData#getPolicyName() getPolicyName}
 *       on the policy obtained by calling 
 *       {@link com.ardais.bigr.javabeans.SampleData#getPolicyData() getPolicyData} on the
 *       sample returned by {@link #getCurrentParentSample() getCurrentParentSample}.
 *   </li>
 *   <li>The name of the operation.  This is obtained by calling 
 *       {@link com.ardais.bigr.javabeans.DerivationDto#getOperation() getOperation} on the
 *       DTO returned by {@link #getDto() getDto}.
 *   </li>
 *   </ol>
 * </li>
 * <li>{@link #ERROR_KEY5} - One or more of the parent sample barcodes specify samples that do 
 *     not exist.
 *   <ol>
 *   <li>The name of the property containing the value.  This must be supplied by the caller
 *       by calling {@link #setPropertyDisplayName(java.lang.String) setPropertyDisplayName}. 
 *   </li>
 *   <li>The list of sample barcodes as obtained by calling 
 *       {@link #getNonExistingIds() getNonExistingIds}.
 *   </li>
 *   </ol>
 * </li>
 * <li>{@link #ERROR_KEY6} - One or more of the parent sample barcodes specify samples that have 
 *     not yet been created in the system.
 *   <ol>
 *   <li>The name of the property containing the value.  This must be supplied by the caller
 *       by calling {@link #setPropertyDisplayName(java.lang.String) setPropertyDisplayName}. 
 *   </li>
 *   <li>The list of sample barcodes as obtained by calling 
 *       {@link #getNonCreatedIds() getNonCreatedIds}.
 *   </li>
 *   </ol>
 * </li>
 * <li>{@link #ERROR_KEY7} - One or more of the parent sample barcodes specify samples that have 
 *     been consumed.
 *   <li>The name of the property containing the value.  This must be supplied by the caller
 *       by calling {@link #setPropertyDisplayName(java.lang.String) setPropertyDisplayName}. 
 *   </li>
 *   <li>The list of sample barcodes as obtained by calling 
 *       {@link #getDuplicateIds() getDuplicateIds}.
 *   </li>
 *   </ol>
 * </li>
 * <li>{@link #ERROR_KEY8} - One or more of the parent sample barcodes is duplicated. 
 *   <ol>
 *   <li>The name of the property containing the value.  This must be supplied by the caller
 *       by calling {@link #setPropertyDisplayName(java.lang.String) setPropertyDisplayName}. 
 *   </li>
 *   <li>The list of sample barcodes as obtained by calling 
 *       {@link #getConsumedIds() getConsumedIds}.
 *   </li>
 *   </ol>
 * </li>
 * <li>{@link #ERROR_KEY9} - One or more of the parent sample barcodes specify samples that
 *     belong to different cases.
 *   <ol>
 *   <li>The name of the property containing the value.  This must be supplied by the caller
 *       by calling {@link #setPropertyDisplayName(java.lang.String) setPropertyDisplayName}. 
 *   </li>
 *   <li>The list of unique sample barcodes as obtained by calling 
 *       {@link #getUniqueIds() getUniqueIds}.
 *   </li>
 *   </ol>
 * </li>
 * <li>{@link #ERROR_KEY10} - One or more of the parent sample aliases are non-unique so the
 *      specific parent sample cannot be determined.
 *   <ol>
 *   <li>The name of the property containing the value.  This must be supplied by the caller
 *       by calling {@link #setPropertyDisplayName(java.lang.String) setPropertyDisplayName}. 
 *   </li>
 *   <li>The list of non-unique parent aliases as obtained by calling 
 *       {@link #getNonUniqueParentAliases() getNonUniqueParentAliases}.
 *   </li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorDerivativeOperationParentSamples extends AbstractValidator  {
  
  /**
   * The key of the error that is returned if no parent samples were specified.
   */
  public final static String ERROR_KEY1 = "error.noValuesSpecified";

  /**
   * The key of the error that is returned if multiple parent samples were specified for
   * a derivative operation that expects a single parent.
   */
  public final static String ERROR_KEY2 = "iltds.error.genealogy.multipleSamplesDisallowed";
  
  /**
   * The key of the error that is returned if the sample type of the parent sample is not allowed
   * for the specified derivative operation.
   */
  public final static String ERROR_KEY3 =
    "iltds.error.genealogy.invalidParentSampleTypeForOperation";
    
  /**
   * The key of the error that is returned if the type of child samples that could be created
   * from this derivative operation cannot be created according to the policy of the parent 
   * sample(s). 
   */
  public final static String ERROR_KEY4 = 
    "iltds.error.genealogy.policyAndOperationChildTypeMismatch";

  /**
   * The key of the error that is returned if one or more of the parent sample barcodes specify
   * samples that do not exist.
   */
  public final static String ERROR_KEY5 = "iltds.error.genealogy.unknownSamples";

  /**
   * The key of the error that is returned if one or more of the parent sample barcodes specify
   * samples that have not been created in the system.
   */
  public final static String ERROR_KEY6 = "iltds.error.genealogy.uncreatedSamples";

  /**
   * The key of the error that is returned if one or more of the parent sample barcodes is
   * duplicated.
   */
  public final static String ERROR_KEY7 = "error.duplicatedEntities";

  /**
   * The key of the error that is returned if one or more of the parent sample barcodes specify
   * samples that are consumed.
   */
  public final static String ERROR_KEY8 = "iltds.error.genealogy.consumedSamples";

  /**
   * The key of the error that is returned if one or more of the parent sample barcodes specify
   * samples that belong to different cases.
   */
  public final static String ERROR_KEY9 = "iltds.error.genealogy.samplesFromDifferentCases";

  /**
   * The key of the error that is returned if one or more of the parent sample aliases do not
   * uniquely identify the parent sample (i.e. multiple existing samples share the specified alias.
   */
  public final static String ERROR_KEY10 = "iltds.error.genealogy.nonUniqueParentAliases";

  private DerivationDto _dto;
  private boolean _checkIdsOnly;
  private SampleData _currentParentSample;
  private List _nonExistingIds;
  private List _nonCreatedIds;
  private List _duplicateIds;
  private List _consumedIds;
  private Set _caseIds;
  private Set _uniqueIds;
  private List _nonUniqueParentAliases;

  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorDerivativeOperationParentSamples v1 = (ValidatorDerivativeOperationParentSamples) v;
      if (errorKey.equals(ValidatorDerivativeOperationParentSamples.ERROR_KEY1)) {
        v1.addErrorMessage(errorKey, v1.getPropertyDisplayName());
      }
      else if (errorKey.equals(ValidatorDerivativeOperationParentSamples.ERROR_KEY2)) {
        v1.addErrorMessage(errorKey, v1.getPropertyDisplayName(), v1.getDto().getOperation());
      }
      else if (errorKey.equals(ValidatorDerivativeOperationParentSamples.ERROR_KEY3)) {
        SampleData parent = v1.getCurrentParentSample(); 
        v1.addErrorMessage(
          errorKey,
          parent.getSampleId(),
          parent.getSampleType(),
          v1.getDto().getOperation());
      }
      else if (errorKey.equals(ValidatorDerivativeOperationParentSamples.ERROR_KEY4)) {
        SampleData parent = v1.getCurrentParentSample(); 
        v1.addErrorMessage(
          errorKey,
          parent.getSampleId(),
          parent.getPolicyData().getPolicyName(),
          v1.getDto().getOperation());
      }
      else if (errorKey.equals(ValidatorDerivativeOperationParentSamples.ERROR_KEY5)) {
        String ids =
          Escaper.htmlEscapeAndPreserveWhitespace(ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getNonExistingIds())));
        v1.addErrorMessage(errorKey, v1.getPropertyDisplayName(), ids);
      }
      else if (errorKey.equals(ValidatorDerivativeOperationParentSamples.ERROR_KEY6)) {
        String ids =
          Escaper.htmlEscapeAndPreserveWhitespace(ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getNonCreatedIds())));
        v1.addErrorMessage(errorKey, v1.getPropertyDisplayName(), ids);
      }
      else if (errorKey.equals(ValidatorDerivativeOperationParentSamples.ERROR_KEY7)) {
        String ids =
          Escaper.htmlEscapeAndPreserveWhitespace(ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getDuplicateIds())));
        v1.addErrorMessage(errorKey, v1.getPropertyDisplayName(), ids);
      }
      else if (errorKey.equals(ValidatorDerivativeOperationParentSamples.ERROR_KEY8)) {
        String ids =
          Escaper.htmlEscapeAndPreserveWhitespace(ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getConsumedIds())));
        v1.addErrorMessage(errorKey, v1.getPropertyDisplayName(), ids);
      }
      else if (errorKey.equals(ValidatorDerivativeOperationParentSamples.ERROR_KEY9)) {
        String ids =
          Escaper.htmlEscapeAndPreserveWhitespace(ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getUniqueIds())));
        v1.addErrorMessage(errorKey, v1.getPropertyDisplayName(), ids);
      }
      else if (errorKey.equals(ValidatorDerivativeOperationParentSamples.ERROR_KEY10)) {
        String ids =
          Escaper.htmlEscapeAndPreserveWhitespace(ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getNonUniqueParentAliases())));
        v1.addErrorMessage(errorKey, v1.getPropertyDisplayName(), ids);
      }
      else {
        return false;
      }
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorDerivativeOperationParentSamples</code> validator.
   */
  public ValidatorDerivativeOperationParentSamples() {
    super();
    addErrorKey(ERROR_KEY1);
    addErrorKey(ERROR_KEY2);
    addErrorKey(ERROR_KEY3);
    addErrorKey(ERROR_KEY4);
    addErrorKey(ERROR_KEY5);
    addErrorKey(ERROR_KEY6);
    addErrorKey(ERROR_KEY7);
    addErrorKey(ERROR_KEY8);
    addErrorKey(ERROR_KEY9);
    addErrorKey(ERROR_KEY10);
    ValidatorErrorListener listener = new DefaultErrorListener();
    addValidatorErrorListener(listener, ERROR_KEY1);
    addValidatorErrorListener(listener, ERROR_KEY2);
    addValidatorErrorListener(listener, ERROR_KEY3);
    addValidatorErrorListener(listener, ERROR_KEY4);
    addValidatorErrorListener(listener, ERROR_KEY5);
    addValidatorErrorListener(listener, ERROR_KEY6);
    addValidatorErrorListener(listener, ERROR_KEY7);
    addValidatorErrorListener(listener, ERROR_KEY8);
    addValidatorErrorListener(listener, ERROR_KEY9);
    addValidatorErrorListener(listener, ERROR_KEY10);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.AbstractValidator#validate()
   */
  public BtxActionErrors validate() {
    DerivationDto dto = getDto();
    List parentSamples = dto.getParents();
    List validImportedParentSamples = new ArrayList();
    List validNonImportedParentSamples = new ArrayList();

    // If there are no parent samples, return an error since each derivation operation requires 
    // at least one.
    if (ApiFunctions.isEmpty(parentSamples)) {
      notifyValidatorErrorListener(ERROR_KEY1);
    }

    else {
      ValidateIds idValidator = new ValidateIds();
      Set sampleIds = new HashSet();
      List nonExistingIds = new ArrayList();
      List nonCreatedIds = new ArrayList();
      List duplicateIds = new ArrayList();
      List consumedIds = new ArrayList();
      Set caseIds = new HashSet();
      List nonUniqueParentAliases = new ArrayList();
    
      // Get the DerivativeOperation information.
      DerivativeOperation derivOp =
        SystemConfiguration.getDerivativeOperation(dto.getOperationCui());

      // If there are multiple parents, make sure the operation allows for that.
      if (parentSamples.size() > 1) {
        if (!derivOp.isMultipleParents()) {
          notifyValidatorErrorListener(ERROR_KEY2);
        }
      }
      
      // Validate each parent sample.
      for (int i = 0; i < parentSamples.size(); i++) {
        SampleData sample = (SampleData) parentSamples.get(i);
        String sampleId = sample.getSampleId();

        // Convert the id into a fully formed version (imported or traditional sample).
        String validatedId = idValidator.validate(sampleId, ValidateIds.TYPESET_SAMPLE, true);
        //if we were able to get the fully formed version of the id, set it on the sample
        if (!ApiFunctions.isEmpty(validatedId)) {
          sample.setSampleId(validatedId);
        }
        //if not, the value specified could be a sample alias.  Query to see if there are any
        //samples with that alias value and do the following:
        //  - If no matches are found then do nothing as code below will handle that situation by
        //    returning an error.  
        //  - If a single match is found, get the account to which that sample belongs.
        //      -If the account does not require unique aliases, return an error
        //      -If the account does require unique aliases, use its sample id as the validated id.
        //  - If multiple matches are found, return an error to the user since there isn't a way to 
        //    tell which of the matches the user intended to use.  
        //Note that the search for a match is independent of account, since a system owner is not 
        //limited to using only samples within their account as parents.
        //An enhancement to this code would be to provide a means for the user to indicate the
        //specific match instead of returning an error.
        else {
          String alias = ApiFunctions.safeTrim(sampleId);
          if (!ApiFunctions.isEmpty(alias)) {
            List samplesWithMatchingAlias = IltdsUtils.findSamplesFromCustomerId(sampleId, false);
            int matchCount = samplesWithMatchingAlias.size();
            if (matchCount == 0) {
              //nothing to do - code below will handle the fact that the validated id is empty.
            }
            if (matchCount == 1) {
              String matchingSampleId = ((SampleData)samplesWithMatchingAlias.get(0)).getSampleId();
              String accountId = IltdsUtils.getAccountAssignedToSample(matchingSampleId);
              AccountDto accountDto = IltdsUtils.getAccountById(accountId, false, false);
              boolean aliasMustBeUnique = FormLogic.DB_YES.equalsIgnoreCase(accountDto.getRequireUniqueSampleAliases());
              if (!aliasMustBeUnique) {
                nonUniqueParentAliases.add(alias);
                continue;
              }
              else {
                validatedId = matchingSampleId;
                sample.setSampleId(validatedId);
                //move the id to the alias field, since that is what the value represents.
                sample.setSampleAlias(sampleId);
              }
            }
            else if (matchCount > 1) {
              nonUniqueParentAliases.add(alias);
              continue;
            }
          }
        }
        
        // Try to find the existing (and accesible to the user) sample.
        SampleData persistedSample = getPersistedSample(validatedId);

        // If we couldn't find it, add the sampleId to the non-exist pile.
        if (persistedSample == null) {
          nonExistingIds.add(sampleId);
        }

        // Verify that the parent sample barcode has been properly registered.
        else if (!persistedSample.isCreated()) {
          nonCreatedIds.add(IltdsUtils.getSampleIdAndAlias(persistedSample));
        }
          
        // Perform additional validation if the sample exists and has been registered.
        else {
          boolean validParent = true;
          String sampleTypeCui = persistedSample.getSampleTypeCui();
          sample.setConsentId(persistedSample.getConsentId());
          sample.setSampleAlias(persistedSample.getSampleAlias());
          sample.setSampleTypeCui(sampleTypeCui);

          // Verify that the parent sample barcode is not a duplicate.
          if (sampleIds.contains(validatedId)) {
            duplicateIds.add(IltdsUtils.getSampleIdAndAlias(persistedSample));
            validParent = false;
          }
          else {
            sampleIds.add(validatedId);
          }
          
          // Save the case id to verify that all parent samples belong to the same case.
          caseIds.add(persistedSample.getConsentId());
          
          // Verify that the parent sample barcode has not already been consumed.
          if (persistedSample.isConsumed()) {
            consumedIds.add(IltdsUtils.getSampleIdAndAlias(persistedSample));
            validParent = false;
          }
          
          // Verify the parent sample type is permitted for the operation.
          if (!derivOp.isSampleTypeValidParent(sampleTypeCui)) {
            setCurrentParentSample(persistedSample);
            notifyValidatorErrorListener(ERROR_KEY3);
            validParent = false;
          }
          
          // If the persisted sample is valid, then save it in our set of valid parents, so at the 
          // end of checking the parents we have a list of valid parent samples for more validation.
          // Make sure to set the consumed and volume from the input parent, though, since 
          // we want to validate the user input for these two fields.
          if (validParent) {
            persistedSample.setConsumed(sample.isConsumed());
            persistedSample.setVolumeAsString(sample.getVolumeAsString());
            persistedSample.setVolumeUnitCui(sample.getVolumeUnitCui());
            persistedSample.setWeightAsString(sample.getWeightAsString());
            persistedSample.setWeightUnitCui(sample.getWeightUnitCui());
            if (persistedSample.isImported()) {
              validImportedParentSamples.add(persistedSample);
            }
            else {
              validNonImportedParentSamples.add(persistedSample);
            }
          }
        }
      }
          
      // If we're not just checking ids, validate the details of all parents.  Only validate those
      // that have not failed for some other reason.
      if (!isCheckIdsOnly()) {
        if (!validImportedParentSamples.isEmpty() || !validNonImportedParentSamples.isEmpty()) {
          if (!validImportedParentSamples.isEmpty()) {
            SampleOperationsValidationService validator = new SampleOperationsValidationService();
            validator.setSecurityInfo(getSecurityInfo());
            validator.setSamples(validImportedParentSamples);
            validator.setDerivativeOperationAction(true);
            validator.setCheckModifyImportedSample(true);
            getActionErrors().addErrors(validator.validate());      
          }

          if (!validNonImportedParentSamples.isEmpty()) {
            SampleOperationsValidationService validator = new SampleOperationsValidationService();
            validator.setSecurityInfo(getSecurityInfo());
            validator.setSamples(validNonImportedParentSamples);
            validator.setDerivativeOperationAction(true);
            validator.setCheckModifyNonImportedSample(true);
            getActionErrors().addErrors(validator.validate());      
          }

          // Validate that if all of the samples belong to one consent that at least one type
          // of child sample can be produced from the parent(s)
          if (caseIds.size() == 1) {
            DerivationOperation derivOperation = 
              DerivationOperationFactory.SINGLETON.createDerivationOperation(dto);
            for (int i = 0; i < validImportedParentSamples.size(); i++) {
              derivOperation.addParentSample((SampleData) validImportedParentSamples.get(i));
            }
            for (int i = 0; i < validNonImportedParentSamples.size(); i++) {
              derivOperation.addParentSample((SampleData) validNonImportedParentSamples.get(i));
            }
            List validChildSampleTypes = derivOperation.findValidChildSampleTypes(); 
            if (validChildSampleTypes.size() <= 0) {
              SampleData parent = null;
              if (!validImportedParentSamples.isEmpty()) {
                parent = (SampleData) validImportedParentSamples.get(0);
              }
              else if (!validNonImportedParentSamples.isEmpty()) {
                parent = (SampleData) validNonImportedParentSamples.get(0);
              }  
              setCurrentParentSample(parent);
              notifyValidatorErrorListener(ERROR_KEY4);
            }
          }
        }
      }

      setUniqueIds(sampleIds);

      // Add an error if there are any non-existing samples.
      if (nonExistingIds.size() > 0) {
        setNonExistingIds(nonExistingIds);
        notifyValidatorErrorListener(ERROR_KEY5);
      }

      // Add an error if there are any non-created samples.
      if (nonCreatedIds.size() > 0) {
        setNonCreatedIds(nonCreatedIds);
        notifyValidatorErrorListener(ERROR_KEY6);
      }

      // Add an error if there are any duplicate sample barcodes.
      if (duplicateIds.size() > 0) {
        setDuplicateIds(duplicateIds);
        notifyValidatorErrorListener(ERROR_KEY7);
      }

      // Add an error if there are any consumed samples.
      if (consumedIds.size() > 0) {
        setConsumedIds(consumedIds);
        notifyValidatorErrorListener(ERROR_KEY8);
      }

      // Add an error if the samples belong to more than one case.
      if (caseIds.size() > 1) {
        setCaseIds(caseIds);
        notifyValidatorErrorListener(ERROR_KEY9);
      }

      // Add an error if any parent aliases are non-unique.
      if (nonUniqueParentAliases.size() > 0) {
        setNonUniqueParentAliases(nonUniqueParentAliases);
        notifyValidatorErrorListener(ERROR_KEY10);
      }
    }
    
    return getActionErrors();
  }
  
  private SampleData getPersistedSample(String sampleId) {
    SampleData sample = null;
    if (!ApiFunctions.isEmpty(sampleId) && IcpUtils.isItemAccessibleToUserByInvGroup(getSecurityInfo(), sampleId)) {
      sample = SampleFinder.find(getSecurityInfo(), SampleSelect.BASIC_IMPORTED_STATUSES, sampleId);
    }
    return sample;
  }

  public boolean isCheckIdsOnly() {
    return _checkIdsOnly;
  }

  public void setCheckIdsOnly(boolean b) {
    _checkIdsOnly = b;
  }

  public DerivationDto getDto() {
    return _dto;
  }

  /**
   * Sets the DerivationDto that contains the details of the derivation operation that holds the
   * parent samples to validate.
   * 
   * @param dto  the DerivationDto
   */
  public void setDto(DerivationDto dto) {
    _dto = dto;
  }

  /**
   * Returns the parent sample that is currently being validated when an error occurs.  This can
   * be used by listeners to form insertion strings if necessary. 
   * 
   * @return  The current parent sample.
   */
  public SampleData getCurrentParentSample() {
    return _currentParentSample;
  }

  private void setCurrentParentSample(SampleData data) {
    _currentParentSample = data;
  }

  /**
   * Returns the set of unique case ids of the parent samples in the derivation operation being
   * validated.  This may be useful as an insertion string when forming an error message.
   * 
   * @return  The set of unique case ids.
   */
  public Set getCaseIds() {
    return _caseIds;
  }

  /**
   * Returns the list of parent sample barcodes that are consumed in the derivation operation
   * being validated.  This may be useful as an insertion string when forming an error message.
   * 
   * @return  The list of consumed parent barcodes.
   */
  public List getConsumedIds() {
    return _consumedIds;
  }

  /**
   * Returns the list of parent sample barcodes that are duplicated in the derivation operation
   * being validated.  This may be useful as an insertion string when forming an error message.
   * 
   * @return  The list of duplicated parent barcodes.
   */
  public List getDuplicateIds() {
    return _duplicateIds;
  }

  /**
   * Returns the list of parent sample barcodes for samples that have not yet been created
   * in the derivation operation being validated.  This may be useful as an insertion string 
   * when forming an error message.
   * 
   * @return  The list of non-created parent barcodes.
   */
  public List getNonCreatedIds() {
    return _nonCreatedIds;
  }

  /**
   * Returns the list of parent sample barcodes for samples that do not exist
   * in the derivation operation being validated.  This may be useful as an insertion string 
   * when forming an error message.
   * 
   * @return  The list of non-existing parent barcodes.
   */
  public List getNonExistingIds() {
    return _nonExistingIds;
  }

  /**
   * Returns the list of parent sample aliases that do not uniquely identify a sample
   * in the derivation operation being validated.  This may be useful as an insertion string 
   * when forming an error message.
   * 
   * @return  The list of non-unique parent aliases.
   */
  public List getNonUniqueParentAliases() {
    return _nonUniqueParentAliases;
  }

  /**
   * Returns the list of unique parent sample barcodes for the derivation operation being 
   * validated.  This may be useful as an insertion string when forming an error message.
   * 
   * @return  The list of unique parent barcodes.
   */
  public Set getUniqueIds() {
    return _uniqueIds;
  }

  private void setCaseIds(Set set) {
    _caseIds = set;
  }

  private void setConsumedIds(List list) {
    _consumedIds = list;
  }

  private void setDuplicateIds(List list) {
    _duplicateIds = list;
  }

  private void setNonCreatedIds(List list) {
    _nonCreatedIds = list;
  }

  private void setNonExistingIds(List list) {
    _nonExistingIds = list;
  }

  private void setNonUniqueParentAliases(List list) {
    _nonUniqueParentAliases = list;
  }

  private void setUniqueIds(Set set) {
    _uniqueIds = set;
  }

}
