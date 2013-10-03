package com.ardais.bigr.kc.form.def;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.configuration.SystemConfiguration;
import com.ardais.bigr.iltds.assistants.LegalValue;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.orm.performers.BtxPerformerOrmOperations;
import com.ardais.bigr.validation.AbstractValidationService;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorCollection;
import com.ardais.bigr.validation.ValidatorCollectionNonProceeding;
import com.ardais.bigr.validation.ValidatorCollectionProceeding;
import com.ardais.bigr.validation.ValidatorContextUpdater;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.ardais.bigr.validation.ValidatorInput;
import com.ardais.bigr.validation.ValidatorInputOutputProperties;
import com.ardais.bigr.validation.ValidatorOutput;
import com.ardais.bigr.validation.ValidatorRequired;
import com.ardais.bigr.validation.ValidatorUserHasAccountAccess;
import com.ardais.bigr.validation.ValidatorUserHasFormAccess;
import com.ardais.bigr.validation.ValidatorUserInAccount;
import com.ardais.bigr.validation.ValidatorValueInCollection;
import com.ardais.bigr.validation.ValidatorValuesEqual;
import com.gulfstreambio.kc.form.FormInstanceQueryCriteria;
import com.gulfstreambio.kc.form.FormInstanceService;
import com.gulfstreambio.kc.form.FormInstanceServiceResponse;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionService;
import com.gulfstreambio.kc.form.def.FormDefinitionServiceResponse;
import com.gulfstreambio.kc.form.def.FormDefinitionTypes;

/**
 * BIGR's form definition validation service.  This service performs BIGR-specific validation 
 * related to form definitions.  To use this service, set the form definition to be 
 * validated, call one or more setCheck* methods to indicate which validations are to be performed,
 * and then call the validate method to perform validation.
 * <p>
 * This validation service also uses the appropriate validation services available in 
 * {@link com.gulfstreambio.kc.form.def.FormDefinitionService FormDefinitionService}
 * to validate generic KnowledgeCapture-specific business rules in addition to validating 
 * the requested BIGR-specific rules.   
 */
public class BigrFormDefinitionValidationService
  extends AbstractValidationService
  implements ValidatorInputOutputProperties {

  private BigrFormDefinition _formDef;
  BigrFormDefinitionQueryCriteria _queryCriteria;
  
  private boolean _checkCreateable;  
  private boolean _checkUpdateable;  
  private boolean _checkDeleteable;
  private boolean _checkLookupableById;   
  private boolean _checkLookupable;  

  /**
   * Creates a new <code>BigrFormDefinitionValidationService</code>.
   */
  public BigrFormDefinitionValidationService() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    BtxActionErrors errors = getActionErrors();
    Validator v = null;
    int numChecks = 0;

    // Create the shared context and add the form definition to be validated. 
    BigrFormDefinitionValidatorContext context = new BigrFormDefinitionValidatorContext();
    context.setFormDefinition(getFormDefinition());
    context.setSecurityInfo(getSecurityInfo());
    context.setQueryCriteria(getQueryCriteria());

    // Create the appropriate validator based on what was requested. 
    if (isCheckCreateable()) {
      numChecks++;
      v = createCreateableValidator(context);
    }

    if (isCheckUpdateable()) {
      numChecks++;
      v = createUpdateableValidator(context);
    }

    if (isCheckDeleteable()) {
      numChecks++;
      v = createDeleteableValidator(context);
    }

    if (isCheckLookupableById()) {
      numChecks++;
      v = createLookupableByIdValidator(context);
    }
    
    if (isCheckLookupable()) {
      numChecks++;
      v = createLookupableValidator(context);
    }

    // We only expect one validation to be requested, so if more than one is requested throw an
    // exception.
    if (numChecks > 1) {
      throw new ApiException("BigrFormDefinitionValidationService: More than one validation requested.");
    }
    else {
      // Execute the specified validator.  If there are no errors, then set the form definition on
      // this service from the context so the user of this service can get the proper form 
      // definition output.
      if (v != null) {
        BtxActionErrors newErrors = v.validate();
        
        // If there were no errors and we queried for the persisted form during our validation,
        // then make the persisted form available to the caller of this service.
        if ((newErrors == null) || newErrors.empty()) {
          BigrFormDefinition persistedForm = context.getPersistedFormDefinition();
          if (persistedForm != null) {
            setFormDefinition(persistedForm);            
          }
        }

        else {
          errors.addErrors(newErrors);
        }
      }
      // If no validations were specified, then throw an exception. 
      else {
        throw new ApiException("BigrFormDefinitionValidationService: No validation checks requested.");
      }
    }

    return errors;
  }

  private Validator createCreateableValidator(BigrFormDefinitionValidatorContext context) {   

    // Create the outermost validator as a non-proceeding validator collection that first checks 
    // form type.  If the form type was not specified or is not valid, then it would be difficult 
    // to proceed since most of the remaining validation depends on knowing the type of form.
    ValidatorCollection np0 = new ValidatorCollectionNonProceeding("BIGR form definition creatable");    
    np0.setValidatorContext(context);
    np0.addValidator(createFormTypeSpecifiedValidator(), 
        new ValidatorInput(INPUT_VALUE, "formDefinition.formType"));
    np0.addValidator(createFormTypeValidValidator(), 
        new ValidatorInput(INPUT_VALUE, "formDefinition.formType"));
    
    String formType = context.getFormDefinition().getFormType();

    // Create a proceeding validator collection for the rest of the validation, to validate as
    // much as possible.
    ValidatorCollection p01 = new ValidatorCollectionProceeding("p01");
    
    // Create a non-proceeding collection to check a form name was specified
    // for results forms only.
    if (FormDefinitionTypes.RESULTS.equals(formType)) {
      ValidatorCollection np015 = new ValidatorCollectionNonProceeding("np015");
      np015.addValidator(createFormNameSpecifiedValidator(), 
        new ValidatorInput(INPUT_VALUE, "formDefinition.name"));
      p01.addValidator(np015);
    }

    // Create a non-proceeding collection to check the XML and perform validation that depend
    // upon the XML being parsed correctly, such as the KC-specific validations.
    ValidatorCollection np011 = new ValidatorCollectionNonProceeding("np011");
    np011.addValidator(createXmlDocumentSpecifiedValidator(), 
        new ValidatorInput(INPUT_VALUE, "formDefinition.formDefinitionXml"));
    np011.addValidator(createXmlDocumentParseableValidator(), 
        new ValidatorInput[] { 
          new ValidatorInput(INPUT_VALUE1, "formDefinition.formDefinitionXml"),
          new ValidatorInput(INPUT_VALUE2, "formDefinition.formType") },
        new ValidatorOutput(OUTPUT_FORM_DEFINITION, "kcFormDefinition"));

    // Add an updater that creates a new BIGR form definition from the existing BIGR form
    // definition and KC form definition that was parsed and puts it back into the context.
    // This gives us a complete BIGR form definition for the subsequent validators.
    ValidatorContextUpdater updater = new ContextUpdaterBigrFormDefinition();
    np011.addValidatorContextUpdater(updater);

    // Add a validator that wraps the KC-specific form definition validator.
    ValidatorKcCreateFormDefinitionWrapper v = new ValidatorKcCreateFormDefinitionWrapper();
    v.setName("BIGR wrapper for KC-specific validations");
    np011.addValidator(v, new ValidatorInput(INPUT_FORM_DEFINITION, "kcFormDefinition"));
    // validate that at least one form element was specified, and that any host elements are valid 
    // for results forms only.  This is only applicable if the XML could be parsed.
    if (FormDefinitionTypes.RESULTS.equals(formType)) {
      np011.addValidator(createFormElementsExistValidator(),
          new ValidatorInput(INPUT_FORM_DEFINITION, "kcFormDefinition"));
      np011.addValidator(createResultsFormHostElementsValidator(),
        new ValidatorInput(INPUT_FORM_DEFINITION, "kcFormDefinition"));
    }
    // validate that tags were properly specified for each data element
    // of a query form
    if (FormDefinitionTypes.QUERY.equals(formType)) {
      np011.addValidator(createQueryFormTagValidator(),
          new ValidatorInput(INPUT_FORM_DEFINITION, "kcFormDefinition"));
    }
        
    p01.addValidator(np011);

    // Create a non-proceeding collection to check that the account was specified and is valid
    // and accessible to the user, for all forms.
    ValidatorCollection np012 = new ValidatorCollectionNonProceeding("np012");
    np012.addValidator(createAccountSpecifiedValidator(), 
        new ValidatorInput(INPUT_VALUE, "formDefinition.account"));
    np012.addValidator(createAccountValidValidator(), 
        new ValidatorInput(INPUT_VALUE, "formDefinition.account"));
    np012.addValidator(createUserHasAccountAccessValidator(),
        new ValidatorInput[] { 
                   new ValidatorInput(INPUT_ACCOUNT, "formDefinition.account"),
                   new ValidatorInput(INPUT_SECURITY_INFO, "securityInfo") });

    // Add a validator that ensures that the form defintion name is unique within the account
    // for non-results forms.
    if (!FormDefinitionTypes.RESULTS.equals(formType)) {
      np012.addValidator(createFormDefinitionNameUniqueValidator(), 
        new ValidatorInput(INPUT_FORM_DEFINITION, "formDefinition"));
    }

    p01.addValidator(np012);
    
    // Create a non-proceeding collection to check that the object type was specified and that 
    // it is in the list of valid object types, for data forms only.
    // also check that uses was specified
    if (FormDefinitionTypes.DATA.equals(formType)) {
      ValidatorCollection np013 = new ValidatorCollectionNonProceeding("np013");
      np013.addValidator(createObjectTypeSpecifiedValidator(), 
          new ValidatorInput(INPUT_VALUE, "formDefinition.objectType"));
      np013.addValidator(createObjectTypeValidValidator(), 
          new ValidatorInput(INPUT_VALUE, "formDefinition.objectType"));
      np013.addValidator(createUsesSpecifiedValidator(), 
          new ValidatorInput(INPUT_VALUE, "formDefinition.uses"));      

      p01.addValidator(np013);      
    }
    
    // Create a non-proceeding collection to check that the username was specified and that 
    // it is valid for results forms only.
    if (FormDefinitionTypes.RESULTS.equals(formType)) {
      ValidatorCollection np014 = new ValidatorCollectionNonProceeding("np014");
      np014.addValidator(createUserNameSpecifiedValidator(), 
          new ValidatorInput(INPUT_VALUE, "formDefinition.userName"));
      np014.addValidator(createUserNameValidValidator(), 
          new ValidatorInput[] { 
          new ValidatorInput(INPUT_USERNAME, "formDefinition.userName"),
          new ValidatorInput(INPUT_ACCOUNT, "formDefinition.account")});
      p01.addValidator(np014);

    }    
       
    // Add a validator that ensures that a registration data form definition 
    // does not have categories
    if (FormDefinitionTypes.DATA.equals(formType)) {
    	if (getFormDefinition().getUses() != null) {
          if (getFormDefinition().getUses().equals(TagTypes.USES_VALUE_REGISTRATION)) {
            ValidatorCollection np015 = new ValidatorCollectionNonProceeding("np015");
            np015.addValidator(createRegistrationFormNoCategoriesValidator(), 
              new ValidatorInput(INPUT_FORM_DEFINITION, "formDefinition"));
            p01.addValidator(np015); 
          }
      }  
    }

    // Add a validator that checks the tags for both host(core) and KC elements
    //   in registration data form definitions.  Also verify that annotation
    //   forms have no tags.
    if (FormDefinitionTypes.DATA.equals(formType)) {
        ValidatorCollection np016 = new ValidatorCollectionNonProceeding("np016");
        np016.addValidator(createDataFormTags(), 
          new ValidatorInput(INPUT_FORM_DEFINITION, "formDefinition"));
        p01.addValidator(np016); 
    }  

    // Add a validator that verifies that annotation
    //   forms do not have host elements
    if (FormDefinitionTypes.DATA.equals(formType)) {
      if (getFormDefinition().getUses() != null) {
          if (getFormDefinition().getUses().equals(TagTypes.USES_VALUE_ANNOTATION)) {
            ValidatorCollection np017 = new ValidatorCollectionNonProceeding("np017");
            np017.addValidator(createAnnotationHostElementsValidator(), 
              new ValidatorInput(INPUT_FORM_DEFINITION, "formDefinition"));
            p01.addValidator(np017); 
          }
      }
    }
    
    // Add a validator that verifies host elements registration forms
    if (FormDefinitionTypes.DATA.equals(formType)) {
      if (getFormDefinition().getUses() != null) {
          if (getFormDefinition().getUses().equals(TagTypes.USES_VALUE_REGISTRATION)) {
            ValidatorCollection np018 = new ValidatorCollectionNonProceeding("np018");
            np018.addValidator(createRegistrationHostElementsValidator(), 
              new ValidatorInput(INPUT_FORM_DEFINITION, "formDefinition"));
            p01.addValidator(np018); 
          }
      }
    }
    
    np0.addValidator(p01);
    return np0;
  }

  private Validator createUpdateableValidator(BigrFormDefinitionValidatorContext context) {

    // We have to do a query to get the form definition so we can get its type, since some of
    // the validations that we need to do here depend on the type, and we do not want to assume
    // that the form type was specified (since it cannot be changed anyway), and even if it
    // was specified we don't want to assume that it is correct.
    String formDefinitionId = context.getFormDefinition().getFormDefinitionId();
    FormDefinitionServiceResponse response = 
      FormDefinitionService.SINGLETON.findFormDefinitionById(formDefinitionId);
    FormDefinition form = response.getFormDefinition();
    String formType = (form == null) ? null : form.getType();

    // Create the outermost validator as a non-proceeding validator collection that first checks
    // whether the form definition exists.  If it doesn't, then there no reason to proceed.
    ValidatorCollection np0 = 
      new ValidatorCollectionNonProceeding("BIGR form definition updateable");    
    np0.setValidatorContext(context);
    np0.addValidator(
        createIdSpecifiedValidator(),
        new ValidatorInput(INPUT_VALUE, "formDefinition.formDefinitionId"));
    np0.addValidator(createFormDefinitionExistsValidator(), 
        new ValidatorInput(INPUT_VALUE, "formDefinition.formDefinitionId"),
        new ValidatorOutput(OUTPUT_FORM_DEFINITION, "persistedFormDefinition"));

    // Create a proceeding validator collection for the rest of the validation, to validate as
    // much as possible.
    ValidatorCollection p01 = new ValidatorCollectionProceeding("p01");
    
    // Create a non-proceeding collection to check a form name was specified
    // for results forms only.
    if (FormDefinitionTypes.RESULTS.equals(formType)) {
      ValidatorCollection np015 = new ValidatorCollectionNonProceeding("np015");
      np015.addValidator(createFormNameSpecifiedValidator(), 
        new ValidatorInput(INPUT_VALUE, "formDefinition.name"));
      p01.addValidator(np015);
    }

    // Create a non-proceeding collection to check the XML and perform validation that depend
    // upon the XML being parsed correctly, such as the KC-specific validations.
    ValidatorCollection np01 = new ValidatorCollectionNonProceeding("np01");
    np01.addValidator(createXmlDocumentSpecifiedValidator(), 
        new ValidatorInput(INPUT_VALUE, "formDefinition.formDefinitionXml"));
    np01.addValidator(createXmlDocumentParseableValidator(), 
        new ValidatorInput[] { 
          new ValidatorInput(INPUT_VALUE1, "formDefinition.formDefinitionXml"),
          new ValidatorInput(INPUT_VALUE2, "persistedFormDefinition.formType") },
        new ValidatorOutput(OUTPUT_FORM_DEFINITION, "kcFormDefinition"));
    
    np01.addValidatorContextUpdater(new ContextUpdaterBigrFormDefinition());
    
    ValidatorKcUpdateFormDefinitionWrapper v = new ValidatorKcUpdateFormDefinitionWrapper();
    v.setName("BIGR wrapper for KC-specific validations");
    np01.addValidator(v, new ValidatorInput(INPUT_FORM_DEFINITION, "kcFormDefinition"));
    // validate that at least one form element was specified, and that any host elements are valid 
    // for results forms only.  This is only applicable if the XML could be parsed.
    if (FormDefinitionTypes.RESULTS.equals(formType)) {
      np01.addValidator(createFormElementsExistValidator(),
          new ValidatorInput(INPUT_FORM_DEFINITION, "kcFormDefinition"));
      np01.addValidator(createResultsFormHostElementsValidator(),
        new ValidatorInput(INPUT_FORM_DEFINITION, "kcFormDefinition"));
    }
    // validate that tags were properly specified for each data element
    // of a query form
    if (FormDefinitionTypes.QUERY.equals(formType)) {
      np01.addValidator(createQueryFormTagValidator(),
          new ValidatorInput(INPUT_FORM_DEFINITION, "kcFormDefinition"));
    }
    
    p01.addValidator(np01);
    
    // Determine whether the data form definition has any instances, since this will affect what
    // further validations have to be performed.
    boolean hasFormInstances = false;
    if (FormDefinitionTypes.DATA.equals(formType)) {
      FormInstanceQueryCriteria criteria = new FormInstanceQueryCriteria();
      criteria.addFormDefinitionId(formDefinitionId);
      FormInstanceServiceResponse response1 = 
        FormInstanceService.SINGLETON.findFormInstances(criteria);
      hasFormInstances = response1.getFormInstances().length > 0;
    }
 

    // Determine whether the form definition is used as an account donor registration form
    boolean isDonorRegForm = false;
    BtxPerformerOrmOperations ormOps = new BtxPerformerOrmOperations();
    isDonorRegForm = ormOps.isAccountDonorRegForm(formDefinitionId);        
      
    // Determine whether the form definition is used as a case policy registration form
    boolean isCaseRegForm = false;
    ormOps = new BtxPerformerOrmOperations();
    isCaseRegForm = ormOps.isPolicyCaseRegForm(formDefinitionId);

    // Determine whether the form definition is used as a sample registration form
    boolean isSampleRegForm = false;
    ormOps = new BtxPerformerOrmOperations();
    isSampleRegForm = ormOps.isSampleRegForm(formDefinitionId);
      
    // Create a non-proceeding collection to check that the account was specified and is valid
    // and accessible to the user, and that it has not changed if the any form instances have
    // been created, for all forms.
    ValidatorCollection np012 = new ValidatorCollectionNonProceeding("np012");
    np012.addValidator(createAccountSpecifiedValidator(), 
        new ValidatorInput(INPUT_VALUE, "formDefinition.account"));
    np012.addValidator(createAccountValidValidator(), 
        new ValidatorInput(INPUT_VALUE, "formDefinition.account"));
    np012.addValidator(createUserHasAccountAccessValidator(),
        new ValidatorInput[] { 
                   new ValidatorInput(INPUT_ACCOUNT, "formDefinition.account"),
                   new ValidatorInput(INPUT_SECURITY_INFO, "securityInfo") });
    if ((hasFormInstances)|| (isDonorRegForm) || (isCaseRegForm) || (isSampleRegForm)){
      np012.addValidator(createAccountNotChangedValidator(), 
          new ValidatorInput[] {
            new ValidatorInput(INPUT_VALUE1, "formDefinition.account"),
            new ValidatorInput(INPUT_VALUE2, "persistedFormDefinition.account") });
    }
    if ((hasFormInstances && FormDefinitionTypes.DATA.equals(formType))
         || (isDonorRegForm) || (isCaseRegForm) || (isSampleRegForm) ) {

      np012.addValidator(createUsesNotChangedValidator(), 
          new ValidatorInput[] {
            new ValidatorInput(INPUT_VALUE1, "formDefinition.uses"),
            new ValidatorInput(INPUT_VALUE2, "persistedFormDefinition.uses") });      
    }
    if (getFormDefinition().getUses() != null) {
        if ((hasFormInstances && FormDefinitionTypes.DATA.equals(formType) 
            && getFormDefinition().getUses().equals(TagTypes.USES_VALUE_REGISTRATION))
            || (isDonorRegForm) || (isCaseRegForm) || (isSampleRegForm)
            ) {
          np012.addValidator(createEnabledNotChangedValidator(), 
              new ValidatorInput[] {
                new ValidatorInput(INPUT_VALUE1, "formDefinition.enabled"),
                new ValidatorInput(INPUT_VALUE2, "persistedFormDefinition.enabled") });      
        }
    }
    if (!FormDefinitionTypes.RESULTS.equalsIgnoreCase(formType)) {
      np012.addValidator(createFormDefinitionNameUniqueValidator(), 
        new ValidatorInput(INPUT_FORM_DEFINITION, "formDefinition"));
    }

    p01.addValidator(np012);
      
    // Create a non-proceeding collection to check that the object type was specified and that 
    // it is in the list of valid object types, for data forms only.
    if (FormDefinitionTypes.DATA.equals(formType)) {
      ValidatorCollection np013 = new ValidatorCollectionNonProceeding("np013");
      np013.addValidator(createObjectTypeSpecifiedValidator(), 
          new ValidatorInput(INPUT_VALUE, "formDefinition.objectType"));
      np013.addValidator(createObjectTypeValidValidator(), 
          new ValidatorInput(INPUT_VALUE, "formDefinition.objectType"));
      if ((hasFormInstances) || (isDonorRegForm) || (isCaseRegForm) || (isSampleRegForm)){
        np013.addValidator(createObjectTypeNotChangedValidator(), 
            new ValidatorInput[] {
              new ValidatorInput(INPUT_VALUE1, "formDefinition.objectType"),
              new ValidatorInput(INPUT_VALUE2, "persistedFormDefinition.objectType") });
      }
      p01.addValidator(np013);      
    }
    
    // Create a non-proceeding collection to check that the username was specified and that 
    // it is valid for results forms only.
    if (FormDefinitionTypes.RESULTS.equals(formType)) {
      ValidatorCollection np014 = new ValidatorCollectionNonProceeding("np014");
      np014.addValidator(createUserNameSpecifiedValidator(), 
          new ValidatorInput(INPUT_VALUE, "formDefinition.userName"));
      np014.addValidator(createUserNameValidValidator(), 
          new ValidatorInput[] { 
          new ValidatorInput(INPUT_USERNAME, "formDefinition.userName"),
          new ValidatorInput(INPUT_ACCOUNT, "formDefinition.account")});
      p01.addValidator(np014);

    }    

    // Add a validator that ensures that a registration data form definition 
    // does not have categories
    if (FormDefinitionTypes.DATA.equals(formType)) {
      if (getFormDefinition().getUses() != null) {
          if (getFormDefinition().getUses().equals(TagTypes.USES_VALUE_REGISTRATION)) {
            ValidatorCollection np015 = new ValidatorCollectionNonProceeding("np015");
            np015.addValidator(createRegistrationFormNoCategoriesValidator(), 
              new ValidatorInput(INPUT_FORM_DEFINITION, "formDefinition"));
            p01.addValidator(np015); 
          }
      }
    }  
    
    // Add a validator that checks the tags for both host(core) and KC elements
    //   in registration data form definitions.  Also check that annotation forms
    //   have zero tags
    if (FormDefinitionTypes.DATA.equals(formType)) {
        ValidatorCollection np016 = new ValidatorCollectionNonProceeding("np016");
        np016.addValidator(createDataFormTags(), 
          new ValidatorInput(INPUT_FORM_DEFINITION, "formDefinition"));
        p01.addValidator(np016); 
    }
    
    // Add a validator that verifies that annotation
    //   forms do not have host elements
    if (FormDefinitionTypes.DATA.equals(formType)) {
      if (getFormDefinition().getUses() != null) {        
          if (getFormDefinition().getUses().equals(TagTypes.USES_VALUE_ANNOTATION)) {
            ValidatorCollection np017 = new ValidatorCollectionNonProceeding("np017");
            np017.addValidator(createAnnotationHostElementsValidator(), 
              new ValidatorInput(INPUT_FORM_DEFINITION, "formDefinition"));
            p01.addValidator(np017); 
          }
      }
    }
    
    // Add a validator that verifies host elements registration forms
    if (FormDefinitionTypes.DATA.equals(formType)) {
      if (getFormDefinition().getUses() != null) {       
          if (getFormDefinition().getUses().equals(TagTypes.USES_VALUE_REGISTRATION)) {
            ValidatorCollection np018 = new ValidatorCollectionNonProceeding("np018");
            np018.addValidator(createRegistrationHostElementsValidator(), 
              new ValidatorInput(INPUT_FORM_DEFINITION, "formDefinition"));
            p01.addValidator(np018); 
          }
      }
    }
    
    np0.addValidator(p01);
    return np0;
  }
  
  private Validator createDeleteableValidator(BigrFormDefinitionValidatorContext context) {
    // Create the validator as a non-proceeding validator collection that first checks
    // whether the form definition exists, then performs the KC-specific validations, and
    // then ensures that the user has permission to delete the form definition.
    ValidatorCollection np0 = 
      new ValidatorCollectionNonProceeding("BIGR form definition deleteable");    
    np0.setValidatorContext(context);

    np0.addValidator(
        createIdSpecifiedValidator(),
        new ValidatorInput(INPUT_VALUE, "formDefinition.formDefinitionId"));

    np0.addValidator(createFormDefinitionExistsValidator(), 
        new ValidatorInput(INPUT_VALUE, "formDefinition.formDefinitionId"),
        new ValidatorOutput(OUTPUT_FORM_DEFINITION, "persistedFormDefinition"));

    ValidatorKcDeleteFormDefinitionWrapper v = new ValidatorKcDeleteFormDefinitionWrapper();
    v.setName("BIGR wrapper for KC-specific validations");
    np0.addValidator(v, 
        new ValidatorInput(INPUT_FORM_DEFINITION, "persistedFormDefinition"));
    
    String formType = context.getFormDefinition().getFormType();
    
    //make sure the user has account access if the form type is not Results
    if (!FormDefinitionTypes.RESULTS.equalsIgnoreCase(formType)) {
      np0.addValidator(createUserHasAccountAccessValidator(),
          new ValidatorInput[] { 
             new ValidatorInput(INPUT_ACCOUNT, "persistedFormDefinition.account"),
             new ValidatorInput(INPUT_SECURITY_INFO, "securityInfo") });
    }
    
    //make sure the form belongs to the user if the form type is Results
    if (FormDefinitionTypes.RESULTS.equalsIgnoreCase(formType)) {
      np0.addValidator(createUserHasFormAccessValidator(),
          new ValidatorInput[] { 
             new ValidatorInput(INPUT_FORM_DEFINITION, "persistedFormDefinition"),
             new ValidatorInput(INPUT_SECURITY_INFO, "securityInfo") });
    }
    
    String formDefinitionId = context.getFormDefinition().getFormDefinitionId();
    // Determine whether the form definition is used as an account donor registration form
    boolean isDonorRegForm = false;
    BtxPerformerOrmOperations ormOps = new BtxPerformerOrmOperations();
    isDonorRegForm = ormOps.isAccountDonorRegForm(formDefinitionId);        
      
    // Determine whether the form definition is used as a case policy registration form
    boolean isCaseRegForm = false;
    ormOps = new BtxPerformerOrmOperations();
    isCaseRegForm = ormOps.isPolicyCaseRegForm(formDefinitionId);

    // Determine whether the form definition is used as a sample registration form
    boolean isSampleRegForm = false;
    ormOps = new BtxPerformerOrmOperations();
    isSampleRegForm = ormOps.isSampleRegForm(formDefinitionId);
    
    // if the form is used in donor, case, sample
    //   force a failure in validation  
    if ((isDonorRegForm) || (isCaseRegForm) || (isSampleRegForm)){
      np0.addValidator(createFormUsedValidator(), 
          new ValidatorInput[] { 
            new ValidatorInput(INPUT_VALUE1, "formDefinition.formDefinitionId"),
            new ValidatorInput(INPUT_VALUE2, "persistedFormDefinition.account") });
    }  
    
    return np0;
  }
  
  private Validator createLookupableByIdValidator(BigrFormDefinitionValidatorContext context) {
    // Create the validator collection and add the shared context.
    ValidatorCollection collection = new ValidatorCollectionNonProceeding();
    collection.setValidatorContext(context);

    collection.addValidator(
      new ValidatorKcLookupFormDefinitionWrapper(),
      new ValidatorInput(INPUT_FORM_DEFINITION_ID, "formDefinition.formDefinitionId"));

    // create an updater that queries for the persisted form
    // definition and adds it to the context. This will be used to check that the account
    // of the form definition is accesible by logged in user.
    collection.addValidatorContextUpdater(new ContextUpdaterPersistedBigrFormDefinition());
    
    //if the user is looking for a results form, make sure the form has a username value
    //matching the logged in user's username
    String formType = context.getFormDefinition().getFormType();
    if (FormDefinitionTypes.RESULTS.equalsIgnoreCase(formType)) {
      ValidatorValuesEqual v = new ValidatorValuesEqual();
      v.addValidatorErrorListener(new ValidatorErrorListener() {
        public boolean validatorError(Validator v, String errorKey) {
          ValidatorValuesEqual v1 = (ValidatorValuesEqual) v;
          v1.addErrorMessage("error.noPrivilege", "access the specified Results Form");
          return true;
        }
      }, ValidatorValuesEqual.ERROR_KEY1);
      collection.addValidator(v,
          new ValidatorInput[] { 
            new ValidatorInput(INPUT_VALUE1, "persistedFormDefinition.userName"),
            new ValidatorInput(INPUT_VALUE2, "securityInfo.username") });   
    }
    //otherwise, make sure the form has an account value equal to the logged in user's account
    else {
      collection.addValidator(new ValidatorUserHasAccountAccess(),
          new ValidatorInput[] { 
            new ValidatorInput(INPUT_ACCOUNT, "persistedFormDefinition.account"),
            new ValidatorInput(INPUT_SECURITY_INFO, "securityInfo") });   
    }
    return collection;
  }
  
  private Validator createLookupableValidator(BigrFormDefinitionValidatorContext context) {
    // Create the validator collection and add the shared context.
    ValidatorCollection collection = new ValidatorCollectionNonProceeding();
    collection.setValidatorContext(context);

    BigrFormDefinitionQueryCriteria queryCriteria = getQueryCriteria();
    
    // If an account is specified as a query criteria, make sure that the user has access to
    // the account.
    String account = queryCriteria.getAccount();
    if (!ApiFunctions.isEmpty(account)) {
      context.setAccount(account);
      collection.addValidator(new ValidatorUserHasAccountAccess(),
          new ValidatorInput[] { 
            new ValidatorInput(INPUT_ACCOUNT, "account"),
            new ValidatorInput(INPUT_SECURITY_INFO, "securityInfo") });         
    }
 
    return collection;
  }

  public BigrFormDefinition getFormDefinition() {
    return _formDef;
  }

  /**
   * Sets the form definition that is to be validated.   
   *  
   * @param definition  the form definition
   */
  public void setFormDefinition(BigrFormDefinition definition) {
    _formDef = definition;
  }

  public BigrFormDefinitionQueryCriteria getQueryCriteria() {
    return _queryCriteria;
  }

  /**
   * Sets the form definition query criteria that is to be validated.   
   *  
   * @param definition  the form definition
   */
  public void setQueryCriteria(BigrFormDefinitionQueryCriteria queryCriteria) {
    _queryCriteria = queryCriteria;
  }

  private boolean isCheckCreateable() {
    return _checkCreateable;
  }

  /**
   * Sets whether validation should be performed to determine whether the form definition is
   * createable.
   * 
   * @param  check  <code>true</code> if createability should be validated;
   *                <code>false</code> otherwise. 
   */
  public void setCheckCreateable(boolean check) {
    _checkCreateable = check;
  }

  private boolean isCheckUpdateable() {
    return _checkUpdateable;
  }

  /**
   * Sets whether validation should be performed to determine whether the form definition is
   * createable.
   * 
   * @param  check  <code>true</code> if createability should be validated;
   *                <code>false</code> otherwise. 
   */
  public void setCheckUpdateable(boolean check) {
    _checkUpdateable = check;
  }

  private boolean isCheckDeleteable() {
    return _checkDeleteable;
  }

  /**
   * Sets whether validation should be performed to determine whether the form definition is
   * deleteable.
   * 
   * @param  check  <code>true</code> if deleteability should be validated;
   *                <code>false</code> otherwise. 
   */
  public void setCheckDeleteable(boolean check) {
    _checkDeleteable = check;
  }

  /**
   * Creates and returns a <code>Validator</code> that checks whether the XML document containing
   * the form definition was specified.  The returned <code>Validator</code> must have an input
   * with property name {@link ValidatorInputOutputProperties#INPUT_VALUE} that takes the 
   * XML document to be validated. 
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createXmlDocumentSpecifiedValidator() {
    ValidatorRequired v = new ValidatorRequired();
    v.addValidatorErrorListener(new ValidatorErrorListener() {
      public boolean validatorError(Validator v, String errorKey) {
        ValidatorRequired v1 = (ValidatorRequired) v;
        v1.addErrorMessage("orm.error.formcreator.noxmldoc");
        return true;
      }
    }, ValidatorRequired.ERROR_KEY1);
    v.setName("xml document specified");
    return v;
  }

  /**
   * Creates and returns a <code>Validator</code> that checks whether the XML document containing
   * the form definition is parseable.  The returned <code>Validator</code> must have an input
   * with property name {@link ValidatorInputOutputProperties#INPUT_VALUE} that takes the 
   * XML document to be validated, an input with property name 
   * {@link ValidatorInputOutputProperties#INPUT_VALUE2} that takes the type of form definition, 
   * and have an output with property name 
   * {@link ValidatorInputOutputProperties#OUTPUT_FORM_DEFINITION} that returns the parsed
   * XML document. 
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createXmlDocumentParseableValidator() {
    ValidatorParseFormDefinitionXml v = new ValidatorParseFormDefinitionXml();
    v.setName("xml document parseable");
    return v;
  }

  protected Validator createFormDefinitionExistsValidator() {
    ValidatorFormDefinitionExists v = new ValidatorFormDefinitionExists(); 
    v.setName("form definition exists");
    return v;
  }
  
  /**
   * Creates and returns a <code>Validator</code> that checks whether the form definition name
   * is unique within the account.  The returned <code>Validator</code> must have an input
   * with property name {@link ValidatorInputOutputProperties#INPUT_FORM_DEFINITION} that takes the 
   * {@link com.ardais.bigr.kc.form.def.BigrFormDefinition BigrFormDefinition} containing the name and 
   * account to be validated.
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createFormDefinitionNameUniqueValidator() {
    ValidatorFormDefinitionNameUnique v = new ValidatorFormDefinitionNameUnique();
    v.setName("form definition name unique");
    return v;
  }
  
  /**
   * Creates and returns a <code>Validator</code> that checks whether the registration form
   * has any categories defined.  The returned <code>Validator</code> must have an input
   * with property name {@link ValidatorInputOutputProperties#INPUT_FORM_DEFINITION} that takes the 
   * {@link com.ardais.bigr.kc.form.def.BigrFormDefinition BigrFormDefinition} containing 
   * form to be validated.
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createRegistrationFormNoCategoriesValidator() {
    ValidatorRegistrationFormNoCategories v = new ValidatorRegistrationFormNoCategories();
    v.setName("registration form no categories");
    return v;
  }

  /**
   * Creates and returns a <code>Validator</code> that checks whether the registration form
   * has any categories defined.  The returned <code>Validator</code> must have an input
   * with property name {@link ValidatorInputOutputProperties#INPUT_FORM_DEFINITION} that takes the 
   * {@link com.ardais.bigr.kc.form.def.BigrFormDefinition BigrFormDefinition} containing 
   * form to be validated.
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createAnnotationHostElementsValidator() {
    ValidatorAnnotationFormHostElements v = new ValidatorAnnotationFormHostElements();
    v.setName("annotation form no host elements");
    return v;
  } 
  
  /**
   * Creates and returns a <code>Validator</code> that checks whether the registration form
   * has any categories defined.  The returned <code>Validator</code> must have an input
   * with property name {@link ValidatorInputOutputProperties#INPUT_FORM_DEFINITION} that takes the 
   * {@link com.ardais.bigr.kc.form.def.BigrFormDefinition BigrFormDefinition} containing 
   * form to be validated.
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createRegistrationHostElementsValidator() {
    ValidatorRegistrationFormHostElements v = new ValidatorRegistrationFormHostElements();
    v.setName("registration form host element rules");
    return v;
  }   
  
  /**
   * Creates and returns a <code>Validator</code> that checks whether a registration form
   * has valid tags.  The returned <code>Validator</code> must have an input
   * with property name {@link ValidatorInputOutputProperties#INPUT_FORM_DEFINITION} that takes the 
   * {@link com.ardais.bigr.kc.form.def.BigrFormDefinition BigrFormDefinition} containing 
   * form to be validated.
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createDataFormTags() {
    ValidatorDataFormTags v = new ValidatorDataFormTags();
    v.setName("registration form element tags");
    return v;
  }

  /**
   * Creates and returns a <code>Validator</code> that checks whether the form type was 
   * was specified.  The returned <code>Validator</code> must have an input
   * with property name {@link ValidatorInputOutputProperties#INPUT_VALUE} that takes the 
   * form type. 
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createFormTypeSpecifiedValidator() {
    ValidatorRequired v = new ValidatorRequired();
    v.setPropertyDisplayName("Form type");
    v.setName("form type specified");
    return v;
  }

  /**
   * Creates and returns a <code>Validator</code> that checks whether the specified form type 
   * is in the set of valid form types.  The returned <code>Validator</code> must have an input
   * with property name {@link ValidatorInputOutputProperties#INPUT_VALUE} that takes the 
   * object type. 
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createFormTypeValidValidator() {
    ValidatorValueInCollection v = new ValidatorValueInCollection();
    v.setPropertyDisplayName("Form type");
    v.setValueSet(ApiFunctions.safeSet(FormDefinitionTypes.getFormDefinitionTypes()));
    v.setName("form type valid");
    return v;
  }

  /* Creates and returns a <code>Validator</code> that checks whether the uses value was 
  * was specified.  The returned <code>Validator</code> must have an input
  * with property name {@link ValidatorInputOutputProperties#INPUT_VALUE} that takes the 
  * object type. 
  * 
  * @return  The <code>Validator</code>.
  */
 protected Validator createUsesSpecifiedValidator() {
   ValidatorRequired v = new ValidatorRequired();
   v.setPropertyDisplayName("Uses");
   v.setName("Uses specified");
   return v;
 }
  
  
  /**
   * Creates and returns a <code>Validator</code> that checks whether the object type was 
   * was specified.  The returned <code>Validator</code> must have an input
   * with property name {@link ValidatorInputOutputProperties#INPUT_VALUE} that takes the 
   * object type. 
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createObjectTypeSpecifiedValidator() {
    ValidatorRequired v = new ValidatorRequired();
    v.setPropertyDisplayName("Object type");
    v.setName("object type specified");
    return v;
  }

  /**
   * Creates and returns a <code>Validator</code> that checks whether the specified object type 
   * is in the set of valid object types.  The returned <code>Validator</code> must have an input
   * with property name {@link ValidatorInputOutputProperties#INPUT_VALUE} that takes the 
   * object type. 
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createObjectTypeValidValidator() {
    Set objects = new HashSet(); 
    LegalValueSet lvs =
      SystemConfiguration.getLegalValueSet(SystemConfiguration.LEGAL_VALUE_SET_KC_ANNOTATED_OBJECTS);
    Iterator i = lvs.getIterator();
    while (i.hasNext()) {
      LegalValue legalValue = (LegalValue) i.next();
      objects.add(legalValue.getValue());
    }
    ValidatorValueInCollection v = new ValidatorValueInCollection();
    v.setPropertyDisplayName("Object type");
    v.setValueSet(objects);
    v.setName("object type valid");
    return v;
  }

  /**
   * Creates and returns a <code>Validator</code> that checks whether the specified object type
   * has changed from its persisted state.   The returned <code>Validator</code> must have two
   * inputs with property names {@link ValidatorInputOutputProperties#INPUT_VALUE1} and 
   * {@link ValidatorInputOutputProperties#INPUT_VALUE2} that take the 
   * specified object type and persisted object type, respectively. 
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createObjectTypeNotChangedValidator() {
    ValidatorValuesEqual v = new ValidatorValuesEqual();
    v.addValidatorErrorListener(new ValidatorErrorListener() {
      public boolean validatorError(Validator v, String errorKey) {
        ValidatorValuesEqual v1 = (ValidatorValuesEqual) v;
        v1.addErrorMessage("orm.error.formcreator.objecttypechanged");
        return true;
      }
    }, ValidatorValuesEqual.ERROR_KEY1);
    return v;
  }

  /**
   * Creates and returns a <code>Validator</code> that checks whether the Uses value is
   * being changed from its persisted state. This is not allowed if there are
   *  instances of the form, or it is used as an account donor reg form, policy
   *  case reg form, or sample type reg form in a policy
   *    The returned <code>Validator</code> must have two
   * inputs with property names {@link ValidatorInputOutputProperties#INPUT_VALUE1} and 
   * {@link ValidatorInputOutputProperties#INPUT_VALUE2} that take the 
   * specified object type and persisted object type, respectively. 
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createUsesNotChangedValidator() {
    ValidatorValuesEqual v = new ValidatorValuesEqual();
    v.addValidatorErrorListener(new ValidatorErrorListener() {
      public boolean validatorError(Validator v, String errorKey) {
        ValidatorValuesEqual v1 = (ValidatorValuesEqual) v;
        v1.addErrorMessage("orm.error.formcreator.formusecannotchange");
        return true;
      }
    }, ValidatorValuesEqual.ERROR_KEY1);
    return v;
  }  
  

  /**
   * Creates and returns a <code>Validator</code> that checks whether the enabled value is
   * being changed from its persisted state. This is not allowed if there are
   *  instances of the form, or it is used as an account donor reg form, policy
   *  case reg form, or sample type reg form in a policy
   *    The returned <code>Validator</code> must have two
   * inputs with property names {@link ValidatorInputOutputProperties#INPUT_VALUE1} and 
   * {@link ValidatorInputOutputProperties#INPUT_VALUE2} that take the 
   * specified object type and persisted object type, respectively. 
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createEnabledNotChangedValidator() {
    ValidatorValuesEqual v = new ValidatorValuesEqual();
    v.addValidatorErrorListener(new ValidatorErrorListener() {
      public boolean validatorError(Validator v, String errorKey) {
        ValidatorValuesEqual v1 = (ValidatorValuesEqual) v;
        v1.addErrorMessage("orm.error.formcreator.formenabledcannotchange");
        return true;
      }
    }, ValidatorValuesEqual.ERROR_KEY1);
    return v;
  } 
  
  
  protected Validator createFormUsedValidator() {
    ValidatorValuesEqual v = new ValidatorValuesEqual();
    v.addValidatorErrorListener(new ValidatorErrorListener() {
      public boolean validatorError(Validator v, String errorKey) {
        ValidatorValuesEqual v1 = (ValidatorValuesEqual) v;
        v1.addErrorMessage("orm.error.formcreator.formenabledcannotchange");
        return true;
      }
    }, ValidatorValuesEqual.ERROR_KEY1);
    return v;
  }  
  
  /**
   * Creates and returns a <code>Validator</code> that checks whether the account was 
   * was specified.  The returned <code>Validator</code> must have an input
   * with property name {@link ValidatorInputOutputProperties#INPUT_VALUE} that takes the 
   * object type. 
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createAccountSpecifiedValidator() {
    ValidatorRequired v = new ValidatorRequired();
    v.setPropertyDisplayName("Account");
    v.setName("account specified");
    return v;
  }

  /**
   * Creates and returns a <code>Validator</code> that checks whether the specified account 
   * is in the set of system accounts.  The returned <code>Validator</code> must have an input
   * with property name {@link ValidatorInputOutputProperties#INPUT_VALUE} that takes the 
   * object type. 
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createAccountValidValidator() {
    LegalValueSet accounts = IltdsUtils.getAccountList();
    Set accountSet = new HashSet();
    Iterator i = accounts.getIterator();
    while (i.hasNext()) {
      LegalValue v = (LegalValue) i.next();
      accountSet.add(v.getValue());
    }
    
    ValidatorValueInCollection v = new ValidatorValueInCollection();
    v.addValidatorErrorListener(new ValidatorErrorListener() {
      public boolean validatorError(Validator v, String errorKey) {
        ValidatorValueInCollection v1 = (ValidatorValueInCollection) v;
        v1.addErrorMessage("orm.error.formcreator.invalidaccount", v1.getValue());
        return true;
      }
    }, ValidatorValueInCollection.ERROR_KEY1);
    v.setValueSet(new HashSet(accountSet));
    v.setName("account exists");
    return v;
  }

  /**
   * Creates and returns a <code>Validator</code> that checks whether the username was 
   * was specified.  The returned <code>Validator</code> must have an input
   * with property name {@link ValidatorInputOutputProperties#INPUT_VALUE} that takes the 
   * object type. 
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createUserNameSpecifiedValidator() {
    ValidatorRequired v = new ValidatorRequired();
    v.setPropertyDisplayName("Username");
    v.setName("username specified");
    return v;
  }

  /**
   * Creates and returns a <code>Validator</code> that checks whether the form name was 
   * was specified.  The returned <code>Validator</code> must have an input
   * with property name {@link ValidatorInputOutputProperties#INPUT_VALUE} that takes the 
   * object type. 
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createFormNameSpecifiedValidator() {
    ValidatorRequired v = new ValidatorRequired();
    v.setPropertyDisplayName("form name");
    v.setName("form name specified");
    return v;
  }

  /**
   * Creates and returns a <code>Validator</code> that checks whether the specified username 
   * is in the set of system users.  The returned <code>Validator</code> must have an input
   * with property name {@link ValidatorInputOutputProperties#INPUT_VALUE} that takes the 
   * object type. 
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createUserNameValidValidator() {
    ValidatorUserInAccount v = new ValidatorUserInAccount();
    v.setPropertyDisplayName("Username");
    v.setName("username exists");
    return v;
  }

  /**
   * Creates and returns a <code>Validator</code> that checks checks all aspects of each 
   * individual host element in the specified results form definition.  The returned 
   * <code>Validator</code> must have an input with property name 
   * {@link ValidatorInputOutputProperties#INPUT_FORM_DEFINITION} that takes the form definition
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createResultsFormHostElementsValidator() {
    return new ValidatorResultsFormDefinitionHostElements();
  }
  
  protected Validator createFormElementsExistValidator() {
    return new ValidatorFormDefinitionElementsExist();
  }
  
  protected Validator createQueryFormTagValidator() {
    return new ValidatorQueryFormTags();
  }

  /**
   * Creates and returns a <code>Validator</code> that checks whether the specified account
   * has changed from its persisted state.   The returned <code>Validator</code> must have two
   * inputs with property names {@link ValidatorInputOutputProperties#INPUT_VALUE1} and 
   * {@link ValidatorInputOutputProperties#INPUT_VALUE2} that take the 
   * specified account and persisted account, respectively. 
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createAccountNotChangedValidator() {
    ValidatorValuesEqual v = new ValidatorValuesEqual();
    v.addValidatorErrorListener(new ValidatorErrorListener() {
      public boolean validatorError(Validator v, String errorKey) {
        ValidatorValuesEqual v1 = (ValidatorValuesEqual) v;
        v1.addErrorMessage("orm.error.formcreator.accountchanged");
        return true;
      }
    }, ValidatorValuesEqual.ERROR_KEY1);
    return v;
  }

  protected Validator createUserHasAccountAccessValidator() {
    ValidatorUserHasAccountAccess v = new ValidatorUserHasAccountAccess();
    v.setName("user has access to the account");
    return v;
  }

  protected Validator createUserHasFormAccessValidator() {
    ValidatorUserHasFormAccess v = new ValidatorUserHasFormAccess();
    v.setName("user has access to the form");
    return v;
  }
  
  /**
   * Creates and returns a <code>Validator</code> that checks whether the form definition id was 
   * specified.  The returned <code>Validator</code> must have an input with property name 
   * {@link ValidatorInputOutputProperties#INPUT_VALUE} that takes the form definition id to be 
   * validated. 
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createIdSpecifiedValidator() {
    ValidatorRequired v = new ValidatorRequired();
    v.setPropertyDisplayName("form definition id");
    return v;
  }
  
  private boolean isCheckLookupableById() {
    return _checkLookupableById;
  }

  /**
   * Sets whether validation should be performed to determine whether the form definition is
   * lookupable by its id.
   * 
   * @param  checkLookupableById <code>true</code> if lookupability should be validated;
   *         <code>false</code> otherwise. 
   */
  public void setCheckLookupableById(boolean checkLookupableById) {
    _checkLookupableById = checkLookupableById;
  }

  private boolean isCheckLookupable() {
    return _checkLookupable;
  }
  
  /**
   * Sets whether validation should be performed to determine whether the form definition is
   * lookupable by the specified query criteria.
   * 
   * @param  checkLookupable <code>true</code> if lookupability by account should be validated;
   *         <code>false</code> otherwise. 
   */
  public void setCheckLookupable(boolean checkLookupable) {
    _checkLookupable = checkLookupable;
  }  
}
