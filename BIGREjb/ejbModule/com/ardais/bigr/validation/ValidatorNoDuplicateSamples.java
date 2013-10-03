package com.ardais.bigr.validation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.javabeans.SampleData;

/**
 * Validates that a list of samples contains no duplicates.
 * <p>
 * This validator may return one error as follows, with insertion strings listed below the error:
 * <ul>
 * <li>{@link #ERROR_KEY1} - The list contains one or more duplicate samples.
 *   <ol>
 *   <li>The name of the property containing the value.  This must be supplied by the caller
 *       by calling {@link #setPropertyDisplayName(java.lang.String) setPropertyDisplayName}. 
 *   </li>
 *   <li>The list of duplicated sample barcodes, as returned by method 
 *       {@link #getDuplicatedSamples() getDuplicatedSamples}. 
 *   </li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorNoDuplicateSamples extends AbstractValidator {
  
  /**
   * The key of the error that may be returned from this validator. 
   */
  public final static String ERROR_KEY1 = "error.duplicatedEntities";
  
  private List _samples;
  private List _duplicatedSamples;

  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorNoDuplicateSamples v1 = (ValidatorNoDuplicateSamples) v;
      String dups =
        Escaper.htmlEscapeAndPreserveWhitespace(ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getDuplicatedSamples())));
      v1.addErrorMessage(ValidatorNoDuplicateSamples.ERROR_KEY1, v1.getPropertyDisplayName(), dups);
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorNoDuplicateSamples</code> validator.
   */
  public ValidatorNoDuplicateSamples() {
    super();
    addErrorKey(ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.javabeans.AbstractValidator#validate()
   */
  public BtxActionErrors validate() {
    if (!ApiFunctions.isEmpty(getSamples())) {
      List sampleIds = new ArrayList();
      List duplicateIds = new ArrayList();
      Iterator iterator = getSamples().iterator();
      while (iterator.hasNext()) {
        SampleData sample = (SampleData)iterator.next();
        String sampleId = sample.getSampleId();
        if (sampleIds.contains(sampleId)) {
          duplicateIds.add(sampleId);
        }
        else {
          sampleIds.add(sampleId);
        }
      }

      // Add an error if there are any duplicate sample barcodes.
      if (duplicateIds.size() > 0) {
        //in order to convey the most information possible, for each sample id in the duplicate
        //list try to find the sample in the list of samples passed in that has both the sample 
        //id and alias value.  If such a sample exists use it, otherwise just use the id
        List duplicates = new ArrayList();
        Iterator duplicateIterator = duplicateIds.iterator();
        while (duplicateIterator.hasNext()) {
          String duplicateId = (String)duplicateIterator.next();
          Iterator sampleIterator = getSamples().iterator();
          boolean foundAlias = false;
          SampleData foundSample = null;
          while (sampleIterator.hasNext() && !foundAlias) {
            SampleData sample = (SampleData)sampleIterator.next();
            if (duplicateId.equalsIgnoreCase(sample.getSampleId())) {
              foundSample = sample;
              if (!ApiFunctions.isEmpty(sample.getSampleAlias())) {
                foundAlias = true;
              }
            }
          }
          duplicates.add(IltdsUtils.getSampleIdAndAlias(foundSample));
        }
        setDuplicatedSamples(duplicates);
        notifyValidatorErrorListener(ERROR_KEY1);
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

  /**
   * Returns the list of duplicated samples, if any, as a list of sample barcodes.
   * 
   * @return  The list of duplicates.
   */
  public List getDuplicatedSamples() {
    return _duplicatedSamples;
  }

  private void setDuplicatedSamples(List samples) {
    _duplicatedSamples = samples;
  }

}
