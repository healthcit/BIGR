package com.ardais.bigr.iltds.validation;

import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.bizlogic.DerivationOperation;
import com.ardais.bigr.iltds.bizlogic.DerivationOperationFactory;
import com.ardais.bigr.iltds.bizlogic.SampleFinder;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.iltds.databeans.SampleType;
import com.ardais.bigr.iltds.databeans.SampleTypeConfiguration;
import com.ardais.bigr.iltds.helpers.SampleSelect;
import com.ardais.bigr.javabeans.DerivationDto;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.kc.form.def.TagTypes;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.gulfstreambio.kc.det.DataElementTaxonomy;
import com.gulfstreambio.kc.det.DetDataElement;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.form.def.FormDefinitionService;
import com.gulfstreambio.kc.form.def.FormDefinitionServiceResponse;
import com.gulfstreambio.kc.form.def.Tag;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionElement;

/**
 * Validates the data elements that might be utilized for child samples in a single derivative 
 * operation, making sure that no multi-valued and/or multi-level data elements might be
 * rendered.  May return the following error, with insertion strings listed 
 * below the error:
 * <ul>
 * <li>{@link #ERROR_KEY1} - A multi-level and/or multi-valued data element might be rendered.
 *   <ol>
 *   <li>The id of the registration form containing the offending data element.
 *   </li>
 *   <li>The sample type which uses the registration form containing the offending data element.
 *   </li>
 *   <li>The derivation number.  This must be supplied by the caller
 *       by calling {@link #setPropertyDisplayName(java.lang.String) setPropertyDisplayName}. 
 *   </li>
 *   <li>The invalid data element.
 *   </li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorDerivativeOperationInvalidDataElements extends AbstractValidator  {
  
  /**
   * The key of the error that is returned if invalid data elements were found.
   */
  public final static String ERROR_KEY1 = "iltds.error.genealogy.invalidDataElementsFound";
  
  private DerivationDto _dto;
  private String _offendingRegistrationFormId;
  private String _offendingSampleType;
  private String _offendingDataElement;

  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorDerivativeOperationInvalidDataElements v1 = (ValidatorDerivativeOperationInvalidDataElements) v;
      if (errorKey.equals(ValidatorDerivativeOperationInvalidDataElements.ERROR_KEY1)) {
        v1.addErrorMessage(errorKey, v1.getOffendingRegistrationFormId(), v1.getOffendingSampleType(), v1.getPropertyDisplayName(), v1.getOffendingDataElement());
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
  public ValidatorDerivativeOperationInvalidDataElements() {
    super();
    addErrorKey(ERROR_KEY1);
    ValidatorErrorListener listener = new DefaultErrorListener();
    addValidatorErrorListener(listener, ERROR_KEY1);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.AbstractValidator#validate()
   */
  public BtxActionErrors validate() {
    doValidate();    
    return getActionErrors();
  }

  /*
   * Perform the validations.
   */  
  private void doValidate() {
    DerivationDto dto = getDto();
    //if no parents were specified there is nothing to validate so just return
    if (ApiFunctions.isEmpty(ApiFunctions.safeList(dto.getParents()))) {
      return;
    }
    DataElementTaxonomy dataElementTaxonomy = DetService.SINGLETON.getDataElementTaxonomy();
    DerivationOperation derivOp = DerivationOperationFactory.SINGLETON.createDerivationOperation(dto);
    SampleTypeConfiguration stc = null;
    Iterator parentIterator = dto.getParents().iterator();
    boolean legitParentFound = false;
    while (parentIterator.hasNext()) {
      SampleData parent = (SampleData) parentIterator.next();
      SampleData persistedParent = 
        SampleFinder.find(getSecurityInfo(), SampleSelect.BASIC_IMPORTED_CONFIG_INVENTORYGROUPS, parent.getSampleId());
      //at this point we cannot rely on valid parents having been specified, so only add the parent
      //if it has been created
      if (persistedParent != null && persistedParent.isCreated()) {
        legitParentFound = true;
        derivOp.addParentSample(persistedParent);
        //if we haven't already done so, get the sample type configuration information from the policy 
        //of the parents in the operation (all parents must belong to the same case, so just use the 
        //first parent)
        if (stc == null) {
          stc = persistedParent.getPolicyData().getSampleTypeConfiguration();
        }
      }
    }
    //if no legit parents were found there is nothing to validate so just return
    if (!legitParentFound) {
      return;
    }
    
    //get the potential child sample types
    List validChildSampleTypes = derivOp.findValidChildSampleTypes(); 
    //now iterate over the choices for the child sample types (determined above), getting the 
    //registration form for each and seeing if the form contains any invalid data elements
    Iterator sampleTypeIterator = validChildSampleTypes.iterator();
    while (sampleTypeIterator.hasNext()) {
      SampleType childSampleType = stc.getSampleType(((String) sampleTypeIterator.next()));
      String registrationFormId = childSampleType.getRegistrationFormId();
      if (!ApiFunctions.isEmpty(registrationFormId)) {
        FormDefinitionServiceResponse response = FormDefinitionService.SINGLETON.findFormDefinitionById(registrationFormId);
        if (response.getErrors().empty()) {
          DataFormDefinition registrationFormDef = response.getDataFormDefinition();
          DataFormDefinitionElement[] existingElements = registrationFormDef.getDataFormElements().getDataFormElements();
          for (int elementCount=0; elementCount < existingElements.length; elementCount ++) { 
            DataFormDefinitionElement formElement = existingElements[elementCount];
            if (formElement.isDataElement()) {
              String dataElementCui = formElement.getDataDataElement().getCui();
              DetDataElement detDataElement = dataElementTaxonomy.getDataElement(dataElementCui);
              //if the data element is multivalued or multileveled and not to be ignored in
              //derivative operations, return an error
              if (detDataElement.isMultilevelValueSet() || detDataElement.isMultivalued()) {
                Tag[] tags = formElement.getDataDataElement().getTags();
                boolean ignoreElement = false;
                for (int tagCount=0; tagCount<tags.length; tagCount++) {
                  Tag tag = tags[tagCount];
                  if (TagTypes.DERIV_IGNORES.equalsIgnoreCase(tag.getType())) {
                    ignoreElement = new Boolean(tag.getValue()).booleanValue();
                  }
                }
                if (!ignoreElement) {
                  setOffendingRegistrationFormId(registrationFormId);
                  setOffendingSampleType(childSampleType.getCuiDescription());
                  String displayName = formElement.getDataDataElement().getDisplayName();
                  if (ApiFunctions.isEmpty(displayName)) {
                    displayName = detDataElement.getDescription();
                  }
                  setOffendingDataElement(displayName);
                  notifyValidatorErrorListener(ERROR_KEY1);
                }
              }
            }
          }
        }
      }
    }
    
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
   * Returns the id of the registration form containing invalid data elements.  This may be useful 
   * as an insertion string when forming an error message.
   * 
   * @return  The id of the registration form containing invalid data elements.
   */
  private String getOffendingRegistrationFormId() {
    return _offendingRegistrationFormId;
  }
  /**
   * @param offendingRegistrationFormId The offendingRegistrationFormId to set.
   */
  private void setOffendingRegistrationFormId(String offendingRegistrationFormId) {
    _offendingRegistrationFormId = offendingRegistrationFormId;
  }

  /**
   * Returns the sample type referencing the registration form containing invalid data elements.  
   * This may be useful as an insertion string when forming an error message.
   * 
   * @return  The sample type referencing the registration form containing invalid data elements.
   */
  private String getOffendingSampleType() {
    return _offendingSampleType;
  }
  
  /**
   * @param offendingSampleType The offendingSampleType to set.
   */
  private void setOffendingSampleType(String offendingSampleType) {
    _offendingSampleType = offendingSampleType;
  }
  
  /**
   * Returns the invalid data element.  
   * This may be useful as an insertion string when forming an error message.
   * 
   * @return  The invalid data element.
   */
  private String getOffendingDataElement() {
    return _offendingDataElement;
  }
  
  /**
   * @param offendingDataElement The offendingDataElement to set.
   */
  private void setOffendingDataElement(String offendingDataElement) {
    _offendingDataElement = offendingDataElement;
  }
}
