package com.ardais.bigr.iltds.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.javabeans.AccountDto;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;

/**
 * Validates the alias values for child samples for a batch derivative operation.  Performs many different
 * validations and may return one or more of the following errors, with insertion strings listed 
 * below the error:
 * <ul>
 * <li>{@link #ERROR_KEY1} - One or more of the children has no alias value but the account under
 * which the sample is to be created requires that every sample has an alias.
 *   <ol>
 *   <li>The name of the property containing the value.  This must be supplied by the caller
 *       by calling {@link #setPropertyDisplayName(java.lang.String) setPropertyDisplayName}. 
 *   </li>
 *   <li>The list of samples as obtained by calling 
 *       {@link #getNonExistingAliases() getNonExistingAliases}.
 *   </li>
 *   </ol>
 * </li>
 * <li>{@link #ERROR_KEY2} - One or more of the children has a duplicated alias value (either via
 * an existing sample or via another child that will be created via this derivation operation) but 
 * the account under which the sample is to be created requires that sample aliases be unique.
 *   <ol>
 *   <li>The name of the property containing the value.  This must be supplied by the caller
 *       by calling {@link #setPropertyDisplayName(java.lang.String) setPropertyDisplayName}. 
 *   </li>
 *   <li>The list of samples as obtained by calling 
 *       {@link #getDuplicatedAliases() getDuplicatedAliases}.
 *   </li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorDerivativeOperationChildAliases extends AbstractValidator  {
  
  /**
   * The key of the error that is returned if one or more of the children has no alias value but 
   * the account under which the sample is to be created requires that every sample has an alias.
   */
  public final static String ERROR_KEY1 = "iltds.error.genealogy.missingChildAliases";
  
  /**
   * The key of the error that is returned if one or more of the children has a duplicated alias 
   * value (either via an existing sample or via another child that will be created via this 
   * derivation operation) but the account under which the sample is to be created requires that 
   * sample aliases be unique.
   */
  public final static String ERROR_KEY2 = "iltds.error.genealogy.duplicatedChildAliases";
  
  private List _samples;
  private List _samplesWithMissingAlias;
  private List _samplesWithNonUniqueAlias;

  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorDerivativeOperationChildAliases v1 = (ValidatorDerivativeOperationChildAliases) v;
      if (errorKey.equals(ValidatorDerivativeOperationChildAliases.ERROR_KEY1)) {
        String missing =
          Escaper.htmlEscapeAndPreserveWhitespace(ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getSamplesWithMissingAlias())));
        v1.addErrorMessage(errorKey, v1.getPropertyDisplayName(), missing);
      }
      else if (errorKey.equals(ValidatorDerivativeOperationChildAliases.ERROR_KEY2)) {
        String nonUnique =
          Escaper.htmlEscapeAndPreserveWhitespace(ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getSamplesWithNonUniqueAlias())));
        v1.addErrorMessage(errorKey, v1.getPropertyDisplayName(), nonUnique);
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
  public ValidatorDerivativeOperationChildAliases() {
    super();
    addErrorKey(ERROR_KEY1);
    addErrorKey(ERROR_KEY2);
    ValidatorErrorListener listener = new DefaultErrorListener();
    addValidatorErrorListener(listener, ERROR_KEY1);
    addValidatorErrorListener(listener, ERROR_KEY2);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.AbstractValidator#validate()
   */
  public BtxActionErrors validate() {
    if (!ApiFunctions.isEmpty(getSamples())) {
      List sampleIds = new ArrayList();
      List samplesWithMissingAlias = new ArrayList();
      List samplesWithNonUniqueAlias = new ArrayList();
      
      //create a map of accountIds to accountDtos, as we'll need the account information to determine
      //if a sample alias is required and/or must be unique for each sample.  Also, create a
      //map of accountIds to proposed child sample aliases to help determine alias uniqueness later on
      Map accountIdToAccount = new HashMap();
      Map accountIdToSampleAliases = new HashMap();
      Iterator iterator = getSamples().iterator();
      while (iterator.hasNext()) {
        SampleData sample = (SampleData)iterator.next();
        String sampleId = sample.getSampleId();
        if (!ApiFunctions.isEmpty(sampleId)) {
          String accountId = IltdsUtils.getAccountAssignedToSample(sampleId);
          if (accountIdToAccount.get(accountId) == null) {
            AccountDto accountDto = IltdsUtils.getAccountById(accountId, false, false);
            accountIdToAccount.put(accountId, accountDto);
          }
          sample.setSampleAlias(ApiFunctions.safeTrim(sample.getSampleAlias()));
          String alias = sample.getSampleAlias();
          if (!ApiFunctions.isEmpty(alias)) {
            List proposedAliases = (List)accountIdToSampleAliases.get(accountId);
            if (proposedAliases == null) {
              List aliases = new ArrayList();
              aliases.add(alias);
              accountIdToSampleAliases.put(accountId, aliases);
            }
            else {
              proposedAliases.add(alias);
            }
          }
        }
      }
        
      //iterate over the samples looking for samples that must have an alias value
      //but do not
      iterator = getSamples().iterator();
      while (iterator.hasNext()) {
        SampleData sample = (SampleData)iterator.next();
        String sampleId = sample.getSampleId();
        if (!ApiFunctions.isEmpty(sampleId)) {
          String accountId = IltdsUtils.getAccountAssignedToSample(sampleId);
          AccountDto accountDto = (AccountDto)accountIdToAccount.get(accountId);
          boolean aliasRequired = FormLogic.DB_YES.equalsIgnoreCase(accountDto.getRequireSampleAliases());
          if (aliasRequired && ApiFunctions.isEmpty(sample.getSampleAlias())) {
            samplesWithMissingAlias.add(IltdsUtils.getSampleIdAndAlias(sample));
          }
        }
      }
      
      //iterate over the samples again, this time looking for samples that must have a unique
      //alias value (within their account) but do not
      iterator = getSamples().iterator();
      while (iterator.hasNext()) {
        SampleData sample = (SampleData)iterator.next();
        String sampleId = sample.getSampleId();
        if (!ApiFunctions.isEmpty(sampleId)) {
          String accountId = IltdsUtils.getAccountAssignedToSample(sampleId);
          AccountDto accountDto = (AccountDto)accountIdToAccount.get(accountId);
          boolean aliasMustBeUnique = FormLogic.DB_YES.equalsIgnoreCase(accountDto.getRequireUniqueSampleAliases());
          String alias = sample.getSampleAlias();
          if (aliasMustBeUnique && !ApiFunctions.isEmpty(alias)) {
            //if the alias is in use by an existing fully registered sample or it is duplicated in 
            //the list of proposed child aliases that will be created by this operation, flag it as 
            //a problem
            boolean existingSampleWithAlias = IltdsUtils.isSampleAliasValueInUse(accountId, alias, 
                sample.getSampleId());
            List proposedAliases = ApiFunctions.safeList((List)accountIdToSampleAliases.get(accountId));
            boolean duplicatedProposedAlias = proposedAliases.indexOf(alias) != proposedAliases.lastIndexOf(alias);
            if (existingSampleWithAlias || duplicatedProposedAlias) {
              samplesWithNonUniqueAlias.add(IltdsUtils.getSampleIdAndAlias(sample));
            }
          }
        }
      }

      // Add an error if there are any required but missing sample aliases.
      if (samplesWithMissingAlias.size() > 0) {
        setSamplesWithMissingAlias(samplesWithMissingAlias);
        notifyValidatorErrorListener(ERROR_KEY1);
      }

      // Add an error if there are any non-unique sample aliases.
      if (samplesWithNonUniqueAlias.size() > 0) {
        setSamplesWithNonUniqueAlias(samplesWithNonUniqueAlias);
        notifyValidatorErrorListener(ERROR_KEY2);
      }
    }
    
    return getActionErrors();
  }

  public List getSamples() {
    return _samples;
  }

  public void setSamples(List samples) {
    _samples = samples;
  }

  public List getSamplesWithMissingAlias() {
    return _samplesWithMissingAlias;
  }

  public List getSamplesWithNonUniqueAlias() {
    return _samplesWithNonUniqueAlias;
  }

  private void setSamplesWithMissingAlias(List samplesWithMissingAlias) {
    _samplesWithMissingAlias = samplesWithMissingAlias;
  }

  private void setSamplesWithNonUniqueAlias(List samplesWithNonUniqueAlias) {
    _samplesWithNonUniqueAlias = samplesWithNonUniqueAlias;
  }

}
