package com.ardais.bigr.kc.form.def;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.btx.framework.BtxTransactionPerformerBase;
import com.ardais.bigr.configuration.SystemConfiguration;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.iltds.btx.BtxActionMessage;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.kc.form.helpers.FormUtils;
import com.ardais.bigr.query.ColumnConstants;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.Constants;
import com.gulfstreambio.kc.det.DataElementTaxonomy;
import com.gulfstreambio.kc.det.DetDataElement;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.form.FormInstanceQueryCriteria;
import com.gulfstreambio.kc.form.FormInstanceService;
import com.gulfstreambio.kc.form.FormInstanceServiceResponse;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionElements;
import com.gulfstreambio.kc.form.def.FormDefinitionQueryCriteria;
import com.gulfstreambio.kc.form.def.FormDefinitionService;
import com.gulfstreambio.kc.form.def.FormDefinitionServiceResponse;
import com.gulfstreambio.kc.form.def.FormDefinitionTypes;
import com.gulfstreambio.kc.form.def.Tag;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionHostElement;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinition;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinition;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionElement;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionElements;
import com.gulfstreambio.kc.form.def.shared.SharedViewService;

/**
 * Performs operations related to KnowledgeCapture form definitions.  For all operations, if 
 * {@link BtxDetailsKcFormDefinition#setQueryCriteria(FormDefinitionQueryCriteria) query criteria}
 * is specified in the input BTX details, the query is performed an a list of BIGR form definitions
 * is returned, which can be obtained using 
 * {@link BtxDetailsKcFormDefinition#getFormDefinitions() getFormDefinitions}.
 */
public class BtxPerformerKcFormDefinitionOperations extends BtxTransactionPerformerBase {

  public BtxPerformerKcFormDefinitionOperations() {
    super();
  }
  
  /**
   * Starts the process of creating/editing/deleting a KnowledgeCapture data or query 
   * form definition. 
   */
  private BTXDetails performStartFormDefinition(BtxDetailsKcFormDefinitionStart btxDetails) throws Exception {    
    commonSetup(btxDetails);
    btxDetails.setActionForwardTXCompleted("success");    
    return btxDetails;
  }
  
  /**
   * Starts the process of editing/deleting a KnowledgeCapture results form definition. 
   */
  private BTXDetails performStartResultsFormDefinition(BtxDetailsKcFormDefinitionStart btxDetails) throws Exception {
    // Find the form definition with the specified id.  If it's the system default just 
    // make a copy from the system configuration data since a query won't find it.  We make
    // a copy so that we can remove inaccessible columns before returning it (the system
    // default is immutable, and we wouldn't want to change it anyway).
	if (!btxDetails.getLoggedInUserSecurityInfo().isHasPrivilege(SecurityInfo.PRIV_ORM_ACCESS_VIEW_MANAGEMENT))
	{
		btxDetails.addActionError(new BtxActionError("error.noPrivilege", "manage views"));
		btxDetails.setActionForwardTXIncomplete("error");
		return btxDetails;
	}

    BigrFormDefinition bigrForm = btxDetails.getFormDefinition();
    String formId = btxDetails.getFormDefinition().getFormDefinitionId();
    if (Constants.DEFAULT_RESULTS_VIEW_ID.equalsIgnoreCase(formId)) {
      BigrFormDefinition defaultResultsView = SystemConfiguration.getDefaultResultsView();
      bigrForm = new BigrFormDefinition(defaultResultsView, defaultResultsView.getKcFormDefinition());
    }
    else {
      FormDefinitionServiceResponse response = 
        FormDefinitionService.SINGLETON.findFormDefinitionById(formId);

      // Get the KC form definition from the response, and create a new BIGR form definition to
      // be returned.
      FormDefinition kcForm = response.getFormDefinition();
      bigrForm = (kcForm != null) ? new BigrFormDefinition(kcForm) : null;
    }
    btxDetails.setFormDefinition(bigrForm);
    
    //get the data form definitions for the account.
    getDataFormsForAccount(btxDetails);
    
    //get the results forms for the user
    getResultsFormsForUser(btxDetails);
    
    //clean the results form we're returning of obsolete and/or inaccessible elements.
    cleanResultsFormDefinition(btxDetails);
    
    //if we're retrieving (a copy of) the system default, mark it immutable now that we have
    //cleaned it of inaccessible columns (see resultsFormCommonSetup() for details)
    if (Constants.DEFAULT_RESULTS_VIEW_ID.equalsIgnoreCase(formId)) {
      bigrForm.setImmutable();
    }
    //if the form definition was found return success, otherwise retry (which will make the
    //system default view selected
    if (bigrForm != null) {
      btxDetails.setActionForwardTXCompleted("success");
    }
    else {
      btxDetails.addActionMessage(new BtxActionMessage("orm.error.formcreator.formdefinitionnotfound", formId));
      btxDetails.setActionForwardTXIncomplete("retry");
    }
    return btxDetails;
  }

  /**
   * 
   */
  private BTXDetails validatePerformStartResultsFormDefinition(BtxDetailsKcFormDefinitionStart btxDetails) throws Exception {
    BigrFormDefinition bigrForm = btxDetails.getFormDefinition();
    String formId = btxDetails.getFormDefinition().getFormDefinitionId();
    //no need to perform validation if the user has specified the system default view since that is
    //available to everyone
    if (!Constants.DEFAULT_RESULTS_VIEW_ID.equalsIgnoreCase(formId)) {
      BtxActionErrors errors = null;
      BigrFormDefinitionValidationService service = new BigrFormDefinitionValidationService();
      service.setSecurityInfo(btxDetails.getLoggedInUserSecurityInfo());
      service.setFormDefinition(bigrForm);
      bigrForm.setFormType(FormDefinitionTypes.RESULTS);
      service.setCheckLookupableById(true);
      errors = service.validate();
      if (!errors.empty()) {
        btxDetails.addActionErrors(errors);
      }
    }

    return btxDetails;
  }
  
  /**
   * Starts the process of saving a KnowledgeCapture results form definition under a given name. 
   */
  private BTXDetails performResultsFormDefinitionSaveAsStart(BtxDetailsKcFormDefinitionStart btxDetails) throws Exception {
    // Find the form definition with the specified id.  If it's the system default just 
    // make a copy from the system configuration data since a query won't find it.  We make
    // a copy so that we can remove inaccessible columns before returning it (the system
    // default is immutable, and we wouldn't want to change it anyway).
    BigrFormDefinition bigrForm = btxDetails.getFormDefinition();
    String formId = btxDetails.getFormDefinition().getFormDefinitionId();
    if (Constants.DEFAULT_RESULTS_VIEW_ID.equalsIgnoreCase(formId)) {
      BigrFormDefinition defaultResultsView = SystemConfiguration.getDefaultResultsView();
      bigrForm = new BigrFormDefinition(defaultResultsView, defaultResultsView.getKcFormDefinition());
      bigrForm.setImmutable();
    }
    else {
      FormDefinitionServiceResponse response = 
        FormDefinitionService.SINGLETON.findFormDefinitionById(formId);

      // Get the KC form definition from the response, and create a new BIGR form definition to
      // be returned.
      FormDefinition kcForm = response.getFormDefinition();
      bigrForm = (kcForm != null) ? new BigrFormDefinition(kcForm) : null;
    }
    //since all we need for the save as operation is the name of the specified form and the
    //list of existing results forms for the user, don't use the common setup since that gets the
    //data forms and does other things we don't need.
    btxDetails.setFormDefinition(bigrForm);
    getResultsFormsForUser(btxDetails);
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * 
   */
  private BTXDetails validatePerformResultsFormDefinitionSaveAsStart(BtxDetailsKcFormDefinitionStart btxDetails) throws Exception {
    BigrFormDefinition bigrForm = btxDetails.getFormDefinition();
    String formId = btxDetails.getFormDefinition().getFormDefinitionId();
    //make sure a form id has been specified
    if (ApiFunctions.isEmpty(formId)) {
      btxDetails.addActionError(new BtxActionError("error.noValue", "form id"));
    }
    else {
      BtxActionErrors errors = null;
      BigrFormDefinitionValidationService service = new BigrFormDefinitionValidationService();
      service.setSecurityInfo(btxDetails.getLoggedInUserSecurityInfo());
      service.setFormDefinition(bigrForm);
      bigrForm.setFormType(FormDefinitionTypes.RESULTS);
      service.setCheckLookupableById(true);
      errors = service.validate();
      if (!errors.empty()) {
        btxDetails.addActionErrors(errors);
      }
    }
    return btxDetails;
  }
  
  private BigrFormDefinition findResultsForm(BigrFormDefinition formDefinition, String userName) {
    //get the results forms for the user
    BigrFormDefinitionQueryCriteria criteria = new BigrFormDefinitionQueryCriteria();
    criteria.addFormType(FormDefinitionTypes.RESULTS);
    criteria.setUser(userName);
    BigrFormDefinition[] forms = findFormDefinitions(criteria);
    BigrFormDefinition foundForm = null;
    //if the form definition passed in is anonymous, see if there is an existing anonymous form.
    //Otherwise, see if there is an existing form with the same name
    for (int i=0; i <forms.length; i++) {
      if (formDefinition.isAnonymous()) {
        if (forms[i].isAnonymous()) {
          foundForm = forms[i];
        }
      }
      else {
        if (formDefinition.getName().equalsIgnoreCase(forms[i].getName())) {
          foundForm = forms[i];
        }
      }
    }
    return foundForm;
  }
  
  private BtxDetailsKcFormDefinitionCreate makeCreateResultsFormDetails(BtxDetailsKcResultsFormDefinitionCreateOrUpdate btxDetails) {
    BtxDetailsKcFormDefinitionCreate createDetails = new BtxDetailsKcFormDefinitionCreate();
    BigrFormDefinition bigrForm = btxDetails.getFormDefinition();
    //if the form is anonymous, set the name
    if (bigrForm.isAnonymous()) {
      FormDefinition kcForm = bigrForm.getKcFormDefinition();
      kcForm.setName(BigrFormDefinition.ANONYMOUS_FORM_NAME);
      bigrForm.setFormDefinitionXml(kcForm.toXml());
    }
    createDetails.setFormDefinition(bigrForm);
    createDetails.setTransactionType("kc_form_def_create");
    createDetails.setBeginTimestamp(btxDetails.getBeginTimestamp());
    createDetails.setLoggedInUserSecurityInfo(btxDetails.getLoggedInUserSecurityInfo());
    return createDetails;
  }
  
  private BtxDetailsKcFormDefinitionUpdate makeUpdateResultsFormDetails(BigrFormDefinition existingForm, BtxDetailsKcResultsFormDefinitionCreateOrUpdate btxDetails) {
    BtxDetailsKcFormDefinitionUpdate updateDetails = new BtxDetailsKcFormDefinitionUpdate();
    BigrFormDefinition bigrForm = btxDetails.getFormDefinition();
    //the two things we can update here are the enabled field and the form definition xml, so set
    //them on the existing form - everything else should stay as it is
    existingForm.setEnabled(bigrForm.isEnabled());
    existingForm.setFormDefinitionXml(bigrForm.getFormDefinitionXml());
	existingForm.setRolesSharedTo(bigrForm.getRolesSharedTo());
	existingForm.setDefaultSharedViews(bigrForm.getDefaultSharedViews());
    updateDetails.setFormDefinition(existingForm);
    updateDetails.setTransactionType("kc_form_def_update");
    updateDetails.setBeginTimestamp(btxDetails.getBeginTimestamp());
    updateDetails.setLoggedInUserSecurityInfo(btxDetails.getLoggedInUserSecurityInfo());
    return updateDetails;
  }
  
  /**
   * Determines if a KnowledgeCapture results form definition should be created or updated
   * and delegates to the appropriate method. 
   */
  private BTXDetails performCreateOrUpdateResultsFormDefinition(BtxDetailsKcResultsFormDefinitionCreateOrUpdate btxDetails) throws Exception {
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    BigrFormDefinition formDefinition = btxDetails.getFormDefinition();
    //see if there is an existing form definition (either with the specified name or
    //as the anonymous form, as appropriate) for the specified user.  
    //If not we need to do a create and if so we need to do an update
    BigrFormDefinition foundForm = findResultsForm(formDefinition, securityInfo.getUsername());
    BtxDetailsKcFormDefinition createOrUpdateDetails = null;
    if (foundForm == null) {
      createOrUpdateDetails = makeCreateResultsFormDetails(btxDetails);
    }
    else {
      createOrUpdateDetails = makeUpdateResultsFormDetails(foundForm, btxDetails);
    }
	SharedViewService.SINGLETON.saveSharedViews(
		  formDefinition.getFormDefinitionId(),
		  formDefinition.getRolesSharedTo(),
		  formDefinition.getDefaultSharedViews());

    //execute the create or update
    createOrUpdateDetails = (BtxDetailsKcFormDefinition)Btx.perform(createOrUpdateDetails);
    //set the resulting form definition on the btxDetails we're going to return.
    btxDetails.setFormDefinition(createOrUpdateDetails.getFormDefinition());
    //carry forward any messages from the create/update
    btxDetails.addActionMessages(createOrUpdateDetails.getActionMessages());

    //get the data form definitions for the account.
    getDataFormsForAccount(btxDetails);
    
    //get the results forms for the user
    getResultsFormsForUser(btxDetails);
    
    //clean the results form we're returning of obsolete and/or inaccessible elements.
    //Probably not necessary, since we just created/updated the form and any invalid
    //elements should have already been removed, but to be on the safe side it's done
    cleanResultsFormDefinition(btxDetails);

    btxDetails.setActionForwardTXCompleted("success");    
    return btxDetails;    
  }

  /**
   * Determines if a KnowledgeCapture results form definition should be created or updated
   * and delegates to the appropriate validation method. 
   */
  private BTXDetails validatePerformCreateOrUpdateResultsFormDefinition(BtxDetailsKcResultsFormDefinitionCreateOrUpdate btxDetails) throws Exception {
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    BigrFormDefinition formDefinition = btxDetails.getFormDefinition();
    //see if there is an existing form definition (either with the specified name or
    //as the anonymous form, as appropriate) for the specified user.  
    //If not we need to do a create and if so we need to do an update
    BigrFormDefinition foundForm = findResultsForm(formDefinition, securityInfo.getUsername());
    BtxActionErrors errors = null;
    if (foundForm == null) {
      BtxDetailsKcFormDefinitionCreate createDetails = makeCreateResultsFormDetails(btxDetails);
      validatePerformCreateFormDefinition(createDetails);
      errors = createDetails.getActionErrors();
    }
    else {
      BtxDetailsKcFormDefinitionUpdate updateDetails = makeUpdateResultsFormDetails(foundForm, btxDetails);
      validatePerformUpdateFormDefinition(updateDetails);
      errors = updateDetails.getActionErrors();
    }
    
    //return any errors
    btxDetails.addActionErrors(errors);
    
    //if errors were found, get the information we'll need to repopulate the form
    if (!errors.empty()) {
      //get the data form definitions for the account.
      getDataFormsForAccount(btxDetails);
      //get the results forms for the user
      getResultsFormsForUser(btxDetails);
      //clean the results form we're returning of obsolete and/or inaccessible elements.
      cleanResultsFormDefinition(btxDetails);
    }
    return btxDetails;
  }

  /**
   * Returns a single KnowledgeCapture form definition for the id specified in the 
   * BigrFormDefinition in the input BTX details.
   */
  private BTXDetails performLookupFormDefinition(BtxDetailsKcFormDefinitionLookup btxDetails) throws Exception {
    // Find the form definition with the specified id.  If it's the system default just 
    // make a copy from the system configuration data since a query won't find it.  We make
    // a copy so that we can remove inaccessible columns before returning it (the system
    // default is immutable, and we wouldn't want to change it anyway).
    BigrFormDefinition bigrForm = btxDetails.getFormDefinition();
    if (Constants.DEFAULT_RESULTS_VIEW_ID.equalsIgnoreCase(bigrForm.getFormDefinitionId())) {
      BigrFormDefinition defaultResultsView = SystemConfiguration.getDefaultResultsView();
      bigrForm = new BigrFormDefinition(defaultResultsView, defaultResultsView.getKcFormDefinition());
    }
    else {
      FormDefinitionServiceResponse response = 
        FormDefinitionService.SINGLETON.findFormDefinitionById(bigrForm.getFormDefinitionId());

      // Get the KC form definition from the response, and create a new BIGR form definition to
      // be returned.
      FormDefinition kcForm = response.getFormDefinition();
      bigrForm = (kcForm != null) ? new BigrFormDefinition(kcForm) : null;
    }
    btxDetails.setFormDefinition(bigrForm);
    
    //if we're returning a results form, clean it of any obsolete columns.  
    //Otherwise call the regular common setup method
    if (bigrForm != null && FormDefinitionTypes.RESULTS.equalsIgnoreCase(bigrForm.getFormType())) {
      cleanResultsFormDefinition(btxDetails);
      //if we're retrieving (a copy of) the system default, mark it immutable now that we have
      //cleaned it of inaccessible columns (see resultsFormCommonSetup() for details)
      if (Constants.DEFAULT_RESULTS_VIEW_ID.equalsIgnoreCase(bigrForm.getFormDefinitionId())) {
        bigrForm.setImmutable();
      }
    }
    else {
      commonSetup(btxDetails);
    }
    btxDetails.setActionForwardTXCompleted("success");        
    return btxDetails;
  }

  /**
   * 
   */
  private BTXDetails validatePerformLookupFormDefinition(BtxDetailsKcFormDefinitionLookup btxDetails) throws Exception {
    BigrFormDefinitionValidationService service = new BigrFormDefinitionValidationService();
    service.setSecurityInfo(btxDetails.getLoggedInUserSecurityInfo());
    service.setFormDefinition(btxDetails.getFormDefinition());
    service.setCheckLookupableById(true);
    BtxActionErrors errors = service.validate();

    if (!errors.empty()) {
      btxDetails.addActionErrors(errors);
      commonSetup(btxDetails);
    }
    return btxDetails;
  }
  
  /**
   * Returns a collection of KnowledgeCapture form definitions.
   */
  private BTXDetails performLookupFormDefinitions(BtxDetailsKcFormDefinitionLookup btxDetails) throws Exception {
    commonSetup(btxDetails);
    btxDetails.setActionForwardTXCompleted("success");    
    return btxDetails;
  }

  /**
   * 
   */
  private BTXDetails validatePerformLookupFormDefinitions(BtxDetailsKcFormDefinitionLookup btxDetails) throws Exception {
    BigrFormDefinitionValidationService service = new BigrFormDefinitionValidationService();
    service.setSecurityInfo(btxDetails.getLoggedInUserSecurityInfo());
    service.setQueryCriteria(btxDetails.getQueryCriteria());
    service.setCheckLookupable(true);
    BtxActionErrors errors = service.validate();

    if (!errors.empty()) {
      btxDetails.addActionErrors(errors);
      commonSetup(btxDetails);
    }
    return btxDetails;
}

  /**
   * Creates a new KnowledgeCapture form definition.
   */
  private BTXDetails performCreateFormDefinition(BtxDetailsKcFormDefinitionCreate btxDetails) throws Exception {
    // Get the form definition to be created.
    BigrFormDefinition bigrForm = btxDetails.getFormDefinition();

    // Create the form definition based on its type.  The KC API has different methods for
    // creating different types of form definitions, and the methods called here will call the
    // appropriate KC API method.
    String formType = bigrForm.getFormType();
    if (FormDefinitionTypes.DATA.equals(formType)) {
      bigrForm = createDataFormDefinition(bigrForm);
    }
    else if (FormDefinitionTypes.QUERY.equals(formType)) {
      bigrForm = createQueryFormDefinition(bigrForm);
    }
    else if (FormDefinitionTypes.RESULTS.equals(formType)) {
      bigrForm = createResultsFormDefinition(bigrForm);
    }
    else {
      throw new ApiException("In performCreateFormDefinition, attempt to create form of unknown type: " + formType);
    }

    // Set the BIGR form definition that was just created as the one to be returned.
    btxDetails.setFormDefinition(bigrForm);
    
    // Add a message indicating that the form definition was successfully created.
    btxDetails.addActionMessage(
      new BtxActionMessage("orm.message.formcreator.created", Escaper.htmlEscapeAndPreserveWhitespace(bigrForm.getName())));

    // Perform the common setup to get the appropriate list of form definitions to return.
    commonSetup(btxDetails);    
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  private BigrFormDefinition createDataFormDefinition(BigrFormDefinition form) {
    DataFormDefinition kcForm = (DataFormDefinition) form.getKcFormDefinition();      
    FormDefinitionServiceResponse response =
      FormDefinitionService.SINGLETON.createDataFormDefinition(kcForm);
    kcForm = response.getDataFormDefinition();
    return new BigrFormDefinition(kcForm);
  }
    
  private BigrFormDefinition createQueryFormDefinition(BigrFormDefinition form) {
    QueryFormDefinition kcForm = (QueryFormDefinition) form.getKcFormDefinition();      
    FormDefinitionServiceResponse response =
      FormDefinitionService.SINGLETON.createQueryFormDefinition(kcForm);
    kcForm = response.getQueryFormDefinition();
    return new BigrFormDefinition(kcForm);
  }

  private BigrFormDefinition createResultsFormDefinition(BigrFormDefinition form) {
    ResultsFormDefinition kcForm = (ResultsFormDefinition) form.getKcFormDefinition();      
    FormDefinitionServiceResponse response =
      FormDefinitionService.SINGLETON.createResultsFormDefinition(kcForm);
    kcForm = response.getResultsFormDefinition();
    return new BigrFormDefinition(kcForm);
  }

  /**
   * 
   */
  private BTXDetails validatePerformCreateFormDefinition(BtxDetailsKcFormDefinitionCreate btxDetails) throws Exception {   
    // Use the BIGR form definition validation service to validate that the specified form
    // definition is creatable.
    BigrFormDefinitionValidationService service = new BigrFormDefinitionValidationService();
    service.setSecurityInfo(btxDetails.getLoggedInUserSecurityInfo());
    service.setFormDefinition(btxDetails.getFormDefinition());
    service.setCheckCreateable(true);
    try {   
      BtxActionErrors errors = service.validate();
      if (!errors.empty()) {
        btxDetails.addActionErrors(errors);
        commonSetup(btxDetails);
      }
      else {
        btxDetails.setFormDefinition(service.getFormDefinition());
      }
    } catch (Exception e){
      //Shouldn't really need to to this, but --  
      //Even if we setup the XML parser to validate against DTD
      //it is possible for the XML file to omit the DOCTYPE declaration and
      //sneak past an invalidate XML. Because Digester follows the 
      //SAX standard which currently doesn't provide a mechanism to force validation. (SAX
      //says: "if validation is 'on', _and_ the XML contains DOCTYPE, then validate
      //against the specified DTD"). 
      //So we may end up with partially populated DataFormDefinition 
      //thus we can anticipate all kinds of application exceptions e.g. MR-8511      
      btxDetails.addActionError(new BtxActionError("orm.error.formcreator.xmldocparseerror", 
          "Exception has occurred during validation: " + e.getMessage()));
      commonSetup(btxDetails);
    }

    return btxDetails;
  }

  /**
   * Updates an existing KnowledgeCapture form definition.
   */
  private BTXDetails performUpdateFormDefinition(BtxDetailsKcFormDefinitionUpdate btxDetails) throws Exception {
    // Get the form definition to be updated.
    BigrFormDefinition bigrForm = btxDetails.getFormDefinition();

    // Update the form definition based on its type.  The KC API has different methods for
    // updating different types of form definitions, and the methods called here will call the
    // appropriate KC API method.
    String formType = bigrForm.getFormType();
    if (FormDefinitionTypes.DATA.equals(formType)) {
      
      // For data forms, check whether any elements have been removed, and if so return to the
      // same page with a confirm message.
      if (!btxDetails.isConfirmedRemove() && needToConfirmRemove(btxDetails)) {
        commonSetup(btxDetails);
        btxDetails.setActionForwardRetry();
        return btxDetails;
      }
      bigrForm = updateDataFormDefinition(bigrForm);
    }
    else if (FormDefinitionTypes.QUERY.equals(formType)) {
      bigrForm = updateQueryFormDefinition(bigrForm);
    }
    else if (FormDefinitionTypes.RESULTS.equals(formType)) {
      bigrForm = updateResultsFormDefinition(bigrForm);
    }
    else {
      throw new ApiException("In performUpdateFormDefinition, attempt to update form of unknown type: " + formType);
    }

    // Set the BIGR form definition that was just updated as the one to be returned.
    btxDetails.setFormDefinition(bigrForm);

    // Add a message indicating that the form definition was successfully updated.
    btxDetails.addActionMessage(
      new BtxActionMessage("orm.message.formcreator.updated", Escaper.htmlEscapeAndPreserveWhitespace(bigrForm.getName())));

    commonSetup(btxDetails);    
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  private BigrFormDefinition updateDataFormDefinition(BigrFormDefinition form) {
    DataFormDefinition kcForm = (DataFormDefinition) form.getKcFormDefinition();
    TagTypes.addPersistedTagsToUpdateForm(kcForm);
    FormDefinitionServiceResponse response =
      FormDefinitionService.SINGLETON.updateDataFormDefinition(kcForm);
    kcForm = response.getDataFormDefinition();
    return new BigrFormDefinition(kcForm);
  }

  private BigrFormDefinition updateQueryFormDefinition(BigrFormDefinition form) {
    QueryFormDefinition kcForm = (QueryFormDefinition) form.getKcFormDefinition();      
    TagTypes.addPersistedTagsToUpdateForm(kcForm);
    FormDefinitionServiceResponse response =
      FormDefinitionService.SINGLETON.updateQueryFormDefinition(kcForm);
    kcForm = response.getQueryFormDefinition();
    return new BigrFormDefinition(kcForm);
  }

  private BigrFormDefinition updateResultsFormDefinition(BigrFormDefinition form) {
    ResultsFormDefinition kcForm = (ResultsFormDefinition) form.getKcFormDefinition();      
    TagTypes.addPersistedTagsToUpdateForm(kcForm);
	SharedViewService.SINGLETON.populateSharedViews(kcForm);
    FormDefinitionServiceResponse response =
      FormDefinitionService.SINGLETON.updateResultsFormDefinition(kcForm);
    kcForm = response.getResultsFormDefinition();
    return new BigrFormDefinition(kcForm);
  }
  
  /**
   * 
   */
  private BTXDetails validatePerformUpdateFormDefinition(BtxDetailsKcFormDefinitionUpdate btxDetails) throws Exception {
    // Use the BIGR form definition validation service to validate that the specified form
    // definition is updateable.
    BigrFormDefinitionValidationService service = new BigrFormDefinitionValidationService();
    service.setSecurityInfo(btxDetails.getLoggedInUserSecurityInfo());
    service.setFormDefinition(btxDetails.getFormDefinition());
    service.setCheckUpdateable(true);
      
    try {   
        BtxActionErrors errors = service.validate();
        if (!errors.empty()) {
          btxDetails.addActionErrors(errors);
          commonSetup(btxDetails);
        }

      } catch (Exception e){
        //Shouldn't really need to to this, but --  
        //Even if we setup the XML parser to validate against DTD
        //it is possible for the XML file to omit the DOCTYPE declaration and
        //sneak past an invalidate XML. Because Digester follows the 
        //SAX standard which currently doesn't provide a mechanism to force validation. (SAX
        //says: "if validation is 'on', _and_ the XML contains DOCTYPE, then validate
        //against the specified DTD"). 
        //So we may end up with partially populated DataFormDefinition 
        //thus we can anticipate all kinds of application exceptions e.g. MR-8511      
        btxDetails.addActionError(new BtxActionError("orm.error.formcreator.xmldocparseerror", 
            "Exception has occurred during update validation: " + e.getMessage()));
        commonSetup(btxDetails);
      }        

    
    
    return btxDetails;
  }

  private boolean needToConfirmRemove(BtxDetailsKcFormDefinitionUpdate btxDetails) {

    // First determine if a data form is being updated, since we only need to confirm removed
    // elements from a data form.
    FormDefinition inputForm = btxDetails.getFormDefinition().getKcFormDefinition();
    String formDefinitionId = inputForm.getFormDefinitionId();
    FormDefinitionServiceResponse response = 
      FormDefinitionService.SINGLETON.findFormDefinitionById(formDefinitionId);
    FormDefinition persistedForm = response.getFormDefinition();
    String formType = (persistedForm == null) ? null : persistedForm.getType();
    if (!FormDefinitionTypes.DATA.equals(formType)) {
      return false;
    }
    
    // Check whether any elements were removed from the input form.  If so, create a confirm
    // message and return true from this method.  Note that if only data elements were removed,
    // and not host elements, only create the confirm message if there are any form instances,
    // since removing data elements from a form definition without form instances is safe and
    // does not require confirmation.
    DataFormDefinition inputDataForm = (DataFormDefinition) inputForm;
    DataFormDefinition persistedDataForm = (DataFormDefinition) persistedForm;
    List removedDataElements = checkRemovedDataElements(inputDataForm, persistedDataForm);
    List removedHostElements = checkRemovedHostElements(inputDataForm, persistedDataForm);
    if ((removedHostElements.size() == 0) && (removedDataElements.size() > 0)) {
      FormInstanceQueryCriteria criteria = new FormInstanceQueryCriteria();
      criteria.addFormDefinitionId(formDefinitionId);
      FormInstanceServiceResponse response1 = 
        FormInstanceService.SINGLETON.findFormInstances(criteria);
      if (response1.getFormInstances().length == 0) {
        return false;
      }
    }
    if ((removedHostElements.size() > 0) || (removedDataElements.size() > 0)) {
      DataElementTaxonomy det = DetService.SINGLETON.getDataElementTaxonomy();
      StringBuffer msg = new StringBuffer(128);
      msg.append("The following elements have been removed from the form definition.");
      msg.append(" These elements will no longer be visible, but existing data values will be preserved.");
      for (int i = 0; i < removedHostElements.size(); i++) {
        String cui = (String) removedHostElements.get(i);
        msg.append("\\r\\n    - ");
        msg.append(det.getCuiDescription(cui));
        msg.append(" (");
        msg.append(cui);
        msg.append(")");
      }
      for (int i = 0; i < removedDataElements.size(); i++) {
        String cui = (String) removedDataElements.get(i);
        msg.append("\\r\\n    - ");
        msg.append(det.getCuiDescription(cui));
        msg.append(" (");
        msg.append(cui);
        msg.append(")");
      }
      msg.append("\\r\\n\\r\\nAre you sure you want to continue?");
      btxDetails.setConfirmRemoveMessage(msg.toString());
      return true;
    }
    
    return false;
  }

  private List checkRemovedDataElements(DataFormDefinition inputForm, 
                                        DataFormDefinition persistedForm) {
    List removed = new ArrayList();
    
    // Add all of the input data elements to a set for easy lookup.
    DataFormDefinitionDataElement dataElements[] = inputForm.getDataDataElements();
    Set inputElements = new HashSet();
    for (int i = 0; i < dataElements.length; i++) {
      inputElements.add(dataElements[i].getCui());      
    }

    // Loop through all all of the persisted data elements, checking whether each is in the input
    // form definition.  If a persisted data element is not in the input form, then add it to the 
    // list of removed elements to be returned.
    DataFormDefinitionDataElement persistedDataElements[] = persistedForm.getDataDataElements();
    for (int i = 0; i < persistedDataElements.length; i++) {
      String cui =  persistedDataElements[i].getCui();
      if (!inputElements.contains(cui)) {
        removed.add(cui);
      }
    }
    
    return removed;
  }

  private List checkRemovedHostElements(DataFormDefinition inputForm, 
                                        DataFormDefinition persistedForm) {
    List removed = new ArrayList();
    
    // Add all of the input host elements to a set for easy lookup.
    DataFormDefinitionHostElement hostElements[] = inputForm.getDataHostElements();
    Set inputElements = new HashSet();
    for (int i = 0; i < hostElements.length; i++) {
      inputElements.add(hostElements[i].getHostId());      
    }

    // Loop through all all of the persisted host elements, checking whether each is in the input
    // form definition.  If a persisted host element is not in the input form, then add it to the 
    // list of removed elements to be returned.
    DataFormDefinitionHostElement persistedHostElements[] = persistedForm.getDataHostElements();
    for (int i = 0; i < persistedHostElements.length; i++) {
      String hostId =  persistedHostElements[i].getHostId();
      if (!inputElements.contains(hostId)) {
        removed.add(hostId);
      }
    }
    
    return removed;
  }
  
  /**
   * Deletes a KnowledgeCapture form definition.
   */
  private BTXDetails performDeleteResultsFormDefinition(BtxDetailsKcFormDefinitionDelete btxDetails) throws Exception {
    performDeleteFormDefinition(btxDetails);
    return btxDetails;
  }

  /**
   * Deletes a KnowledgeCapture form definition.
   */
  private BTXDetails performDeleteFormDefinition(BtxDetailsKcFormDefinitionDelete btxDetails) throws Exception {
    // Get the form definition to be deleted.
    BigrFormDefinition bigrForm = btxDetails.getFormDefinition();

    // Delete the form definition based on its type.  The KC API has different methods for
    // deleting different types of form definitions, and the methods called here will call the
    // appropriate KC API method.
    String formType = bigrForm.getFormType();
    if (FormDefinitionTypes.DATA.equals(formType)) {
      bigrForm = deleteDataFormDefinition(bigrForm);
    }
    else if (FormDefinitionTypes.QUERY.equals(formType)) {
      bigrForm = deleteQueryFormDefinition(bigrForm);
    }
    else if (FormDefinitionTypes.RESULTS.equals(formType)) {
      bigrForm = deleteResultsFormDefinition(bigrForm);
    }
    else {
      throw new ApiException("In performDeleteFormDefinition, attempt to delete form of unknown type: " + formType);
    }

    // Set the BIGR form definition that was just deleted as the one to be returned.
    btxDetails.setFormDefinition(bigrForm);

    // Add a message indicating that the form definition was successfully deleted.
    btxDetails.addActionMessage(
      new BtxActionMessage("orm.message.formcreator.deleted", Escaper.htmlEscapeAndPreserveWhitespace(bigrForm.getName())));

    commonSetup(btxDetails);    
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  private BigrFormDefinition deleteDataFormDefinition(BigrFormDefinition form) {
    FormDefinitionServiceResponse response =
      FormDefinitionService.SINGLETON.deleteDataFormDefinition(form.getFormDefinitionId());
    return new BigrFormDefinition(response.getFormDefinition());
  }

  private BigrFormDefinition deleteQueryFormDefinition(BigrFormDefinition form) {
    FormDefinitionServiceResponse response =
      FormDefinitionService.SINGLETON.deleteQueryFormDefinition(form.getFormDefinitionId());
    return new BigrFormDefinition(response.getFormDefinition());
  }

  private BigrFormDefinition deleteResultsFormDefinition(BigrFormDefinition form) {
    FormDefinitionServiceResponse response =
      FormDefinitionService.SINGLETON.deleteQueryFormDefinition(form.getFormDefinitionId());
    return new BigrFormDefinition(response.getFormDefinition());
  }

  /**
   * 
   */
  private BTXDetails validatePerformDeleteResultsFormDefinition(BtxDetailsKcFormDefinitionDelete btxDetails) throws Exception {
    //don't allow the system default to be deleted
    BigrFormDefinition bigrForm = btxDetails.getFormDefinition();
    String formId = bigrForm.getFormDefinitionId();
    if (Constants.DEFAULT_RESULTS_VIEW_ID.equalsIgnoreCase(bigrForm.getFormDefinitionId())) {
      btxDetails.addActionError(new BtxActionError("error.noPrivilege", "delete the system default view"));
    }
    else {
      validatePerformDeleteFormDefinition(btxDetails);
    }
    return btxDetails;
  }
  
  /**
   * 
   */
  private BTXDetails validatePerformDeleteFormDefinition(BtxDetailsKcFormDefinitionDelete btxDetails) throws Exception {
    // Use the BIGR form definition validation service to validate that the specified form
    // definition is deleteable.
    BigrFormDefinitionValidationService service = new BigrFormDefinitionValidationService();
    service.setSecurityInfo(btxDetails.getLoggedInUserSecurityInfo());
    service.setFormDefinition(btxDetails.getFormDefinition());
    service.setCheckDeleteable(true);
    BtxActionErrors errors = service.validate();
    if (!errors.empty()) {

      btxDetails.addActionErrors(errors);
      commonSetup(btxDetails);
    }
    else {
      btxDetails.setFormDefinition(service.getFormDefinition());
    }
    return btxDetails;
  }
  
  private BigrFormDefinition[] findFormDefinitions(BigrFormDefinitionQueryCriteria criteria) {    
    if (criteria != null) {
      FormDefinitionServiceResponse response =
        FormDefinitionService.SINGLETON.findFormDefinitions(criteria.toKcFormDefinitionQueryCriteria());      

      // Create new BIGR form definitions for each KC form definition that was returned.
      FormDefinition[] kcForms = response.getFormDefinitions();
      if (kcForms != null) {
        int numForms = kcForms.length;
        BigrFormDefinition[] bigrForms = new BigrFormDefinition[numForms]; 
        for (int i = 0; i < numForms; i++) {
          bigrForms[i] = new BigrFormDefinition(kcForms[i]);
        }
        return bigrForms;
      }
      else {
        return new BigrFormDefinition[0];
      }
    }
    else {
      return new BigrFormDefinition[0];      
    }
  }
  
  //Note that this method retrieves all data form definitions for an account.  
  private void getDataFormsForAccount(BtxDetailsKcFormDefinition btxDetails) {
    BigrFormDefinitionQueryCriteria criteria = new BigrFormDefinitionQueryCriteria();
    criteria.addFormType(FormDefinitionTypes.DATA);
    criteria.setAccount(btxDetails.getLoggedInUserSecurityInfo().getAccount());
    BigrFormDefinition[] forms = findFormDefinitions(criteria);
    for (int i=0; i <forms.length; i++) {
      btxDetails.addFormDefinition(forms[i]);
    }
  }
  
  private void getResultsFormsForUser(BtxDetailsKcFormDefinition btxDetails) {
    List resultsForms = FormUtils.getResultsFormDefinitionsForUser(btxDetails.getLoggedInUserSecurityInfo(), true);
    Iterator iterator = resultsForms.iterator();
    while (iterator.hasNext()) {
      btxDetails.addFormDefinition((BigrFormDefinition)iterator.next());
    }
  }
  
  //method to remove obsolete and/or inaccessible columns from a results form definition
  private void cleanResultsFormDefinition(BtxDetailsKcFormDefinition btxDetails) {
    ResultsFormDefinition resultsFormDefinition = null;
    try {
      resultsFormDefinition = (ResultsFormDefinition)btxDetails.getFormDefinition().getKcFormDefinition();
    }
    catch (Exception e) {
      //do nothing - couldn't parse XML so won't try to clean it
      return;
    }
    //gather the information needed to clean the form.  First get a map of data form definitions
    //referenced by this results form definition.
    Map parentDataFormDefinitions = getDataFormDefinitionsForResultFormDefinition(btxDetails.getLoggedInUserSecurityInfo(), resultsFormDefinition);

    //now get the accesible BIGR columns
    Set validBigrColumnIds = ColumnConstants.getAccessibleResultsViewColumns(btxDetails.getLoggedInUserSecurityInfo());
    //now walk the form elements, removing them if they are accessible or obsolete
    ResultsFormDefinitionElements formElements = resultsFormDefinition.getResultsFormElements();
    ResultsFormDefinitionElement[] elements = formElements.getResultsFormElements();
    boolean elementRemoved = false;
    for (int i = 0; i < elements.length; i++) {
      boolean removeElement = false;
      ResultsFormDefinitionElement element = elements[i];
      if (element.isDataElement()) {
        String formId = null;
        String cui = element.getResultsDataElement().getCui();
        Tag[] tags = element.getResultsDataElement().getTags();
        for (int j = 0; (j < tags.length) && (formId == null); j++) {
          Tag tag = tags[j];
          if (TagTypes.PARENT.equalsIgnoreCase(tag.getType())) {
            formId = tag.getValue();
          }
        }
        //remove the data element if any of the following are true:
        //1) the data element doesn't have a cui
        //2) the data element doesn't have a source data form definition id associated with it
        //3) the data element has a cui that is not in the DET
        //4) the source data form definition is not in the map of parent definitions passed in
        //5) the source data form definition doesn't contain the data element
        if (ApiFunctions.isEmpty(cui)) {
          removeElement = true;
        }
        else if (ApiFunctions.isEmpty(formId)) {
          removeElement = true;
        }
        else {
          DetDataElement detDataElement = 
            DetService.SINGLETON.getDataElementTaxonomy().getDataElement(cui);
          if (detDataElement == null || ApiFunctions.isEmpty(detDataElement.getCui())) {
            removeElement = true;
          }
          BigrFormDefinition formDef = (BigrFormDefinition)parentDataFormDefinitions.get(formId);
          if (formDef == null) {
            removeElement = true;
          }
          else {
            DataFormDefinitionDataElement dfde = ((DataFormDefinition)formDef.getKcFormDefinition()).getDataDataElement(cui);
            if (dfde == null) {
              removeElement = true;
            }
          }
        }
      }
      else if (element.isHostElement()) {
        String hostId = element.getResultsHostElement().getHostId();
        //remove the host element if any of the following are true:
        //1) the host id is not in the list of accessible BIGR columns for this user
        if (!validBigrColumnIds.contains(hostId)) {
          removeElement = true;
        }
      }
      if (removeElement) {
        formElements.removeResultsFormElement(element);
        elementRemoved = true;
      }
    }
    //if we removed any elements, update the XML to reflect the elements left on the form
    if (elementRemoved) {
      //if the resultFormDefinition contains any elements, create a new form definition without the 
      //removed elements.
      ResultsFormDefinition cleanedKcFormDef = null;
      FormDefinitionElements remainingFormElements = resultsFormDefinition.getFormElements();
      if (remainingFormElements != null && remainingFormElements.getFormElements().length > 0) {
        cleanedKcFormDef = ResultsFormDefinition.createFromXml(resultsFormDefinition.toXml());
      }
      else {
        //If the results form definition contains no form elements, return a new, empty form 
        //definition with the various properties set appropriately
        cleanedKcFormDef = new ResultsFormDefinition();
        cleanedKcFormDef.setFormDefinitionId(resultsFormDefinition.getFormDefinitionId());
        cleanedKcFormDef.setName(resultsFormDefinition.getName());
        cleanedKcFormDef.setResultsFormElements(new ResultsFormDefinitionElements());
        cleanedKcFormDef.setEnabled(resultsFormDefinition.isEnabled());
        Tag[] tags = resultsFormDefinition.getTags();
        for (int i=0; i < tags.length; i++) {
          cleanedKcFormDef.addTag(tags[i]);
        }
      }
      BigrFormDefinition oldForm = btxDetails.getFormDefinition();
      //clear the xml from the old form, as it contains the elements we've removed
      oldForm.setFormDefinitionXml(null);
      //create a BigrFormDefinition from the old one (which will hold the correct value of tags, etc)
      //and the new KC form definition
      BigrFormDefinition newForm = new BigrFormDefinition(oldForm, cleanedKcFormDef);
      btxDetails.setFormDefinition(newForm);
    }
  }
  
  private Map getDataFormDefinitionsForResultFormDefinition(SecurityInfo securityInfo, ResultsFormDefinition resultsFormDefinition) {
    Map returnValue = new HashMap();
    ResultsFormDefinitionDataElement[] dataElements = resultsFormDefinition.getResultsDataElements();
    if (dataElements.length > 0) {
      // Walk all the data elements, and for each one do the following:
      //1) determine the id of it's parent data form definition
      //2) retrieve the parent form definition if we haven't already done so
      //3) add the paarent form definition to the map to be returned
      for (int i = 0; i < dataElements.length; i++) {
        ResultsFormDefinitionDataElement dataElement = dataElements[i];
        String cui = dataElement.getCui();
        //1) determine the id of it's parent data form definition
        String parentFormDefinitionId = null;
        Tag[] tags = dataElement.getTags();
        for (int j = 0; j < tags.length; j++) {
          Tag tag = tags[j];
          if (TagTypes.PARENT.equalsIgnoreCase(tag.getType())) {
            parentFormDefinitionId = tag.getValue();
            break;
          }
        }
        //2) retrieve the parent form definition if we haven't already done so
        BigrFormDefinition dataFormDef = (BigrFormDefinition) returnValue.get(parentFormDefinitionId);
        if (dataFormDef == null) {
          dataFormDef = FormUtils.getFormDefinition(securityInfo, parentFormDefinitionId);
          //3) add the paarent form definition to the map to be returned
          returnValue.put(parentFormDefinitionId, dataFormDef);
        }
      }
    }
    return returnValue;
  }
  
  /**
   * Perform common setup activities that need to be performed by many performers and validators 
   * in this performer class.
   * 
   * @param  btxDetails  the <code>BtxDetailsKcFormDefinition</code>
   */
  private void commonSetup(BtxDetailsKcFormDefinition btxDetails) {
    String account; 
    LegalValueSet accountList;

    // First get the account list.  If the user is a system owner, get the entire account list,
    // otherwise the list will contain only a single entry which is the account of the user.
    if (btxDetails.getLoggedInUserSecurityInfo().isInRoleSystemOwner()) {
      accountList = IltdsUtils.getAccountList();
    }
    else {
      account = btxDetails.getLoggedInUserSecurityInfo().getAccount();
      accountList = new LegalValueSet();
      accountList.addLegalValue(account, IltdsUtils.getSingleAccountInList(account));
    }
    btxDetails.setAccountList(accountList);
    
    // Get the form definitions as specified by the input query criteria.
    BigrFormDefinition[] forms = findFormDefinitions(btxDetails.getQueryCriteria());
    if (forms != null) {
      for (int i=0; i < forms.length; i++) {
        btxDetails.addFormDefinition(forms[i]);
      }      
    }
  }
  
  /**
   * Invoke the specified method on this class.  This is only meant to be
   * called from BtxTransactionPerformerBase, please don't call it from anywhere
   * else as a mechanism to gain access to private methods in this class.  Every
   * object that the BTX framework dispatches to must contain this
   * method definition, and its implementation should be exactly the same
   * in each class.  Please don't alter this method or its implementation
   * in any way.
   */
  protected BTXDetails invokeBtxEntryPoint(java.lang.reflect.Method method, BTXDetails btxDetails)
    throws Exception {

    // **** DO NOT EDIT THIS METHOD, see the Javadoc comment above.
    return (BTXDetails) method.invoke(this, new Object[] { btxDetails });
  }
}
