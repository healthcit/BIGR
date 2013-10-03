package com.ardais.bigr.iltds.performers;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.btx.framework.BtxTransactionPerformerBase;
import com.ardais.bigr.configuration.LabelPrintingConfiguration;
import com.ardais.bigr.iltds.assistants.LegalValue;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.assistants.LogicalRepository;
import com.ardais.bigr.iltds.bizlogic.DerivationOperation;
import com.ardais.bigr.iltds.bizlogic.DerivationOperationFactory;
import com.ardais.bigr.iltds.bizlogic.SampleFinder;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.btx.BtxActionMessage;
import com.ardais.bigr.iltds.btx.BtxDetailsCreateImportedSample;
import com.ardais.bigr.iltds.btx.BtxDetailsDerivation;
import com.ardais.bigr.iltds.btx.BtxDetailsDerivationBatch;
import com.ardais.bigr.iltds.btx.BtxDetailsDerivationBatchLookup;
import com.ardais.bigr.iltds.btx.BtxDetailsDerivationBatchSelect;
import com.ardais.bigr.iltds.btx.BtxDetailsDerivationBatchStart;
import com.ardais.bigr.iltds.btx.BtxDetailsModifyImportedSample;
import com.ardais.bigr.iltds.databeans.SampleType;
import com.ardais.bigr.iltds.databeans.SampleTypeConfiguration;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.iltds.helpers.SampleSelect;
import com.ardais.bigr.iltds.validation.DerivativeOperationsUiValidationServiceFactory;
import com.ardais.bigr.javabeans.DerivationBatchDto;
import com.ardais.bigr.javabeans.DerivationDto;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.kc.form.def.TagTypes;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.DbUtils;
import com.ardais.bigr.util.LogicalRepositoryUtils;
import com.ardais.bigr.validation.Validator;
import com.gulfstreambio.bigr.error.BigrValidationError;
import com.gulfstreambio.bigr.error.BigrValidationErrors;
import com.gulfstreambio.bigr.labels.LabelService;
import com.gulfstreambio.gboss.GbossFactory;
import com.gulfstreambio.kc.det.DataElementTaxonomy;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.form.def.FormDefinitionService;
import com.gulfstreambio.kc.form.def.FormDefinitionServiceResponse;
import com.gulfstreambio.kc.form.def.Tag;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionElement;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionElements;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionHostElement;

/**
 * Performs derivation operations.
 */
public class BtxPerformerDerivationOperations extends BtxTransactionPerformerBase {

  public BtxPerformerDerivationOperations() {
    super();
  }

  /**
   * Records a single derivation operation.
   */
  private BTXDetails performDerivation(BtxDetailsDerivation btxDetails) throws Exception {

    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    DerivationDto dto = btxDetails.getDto();

    // Create the appropriate derivation operation instance so we can use it to execute the
    // operation-specific business rules.
    DerivationOperation derivOp = 
      DerivationOperationFactory.SINGLETON.createDerivationOperation(dto);

    // Make a copy of the parent and child lists, and clear the lists on the DTO.  We'll alter the
    // parents and children in the copy of the list as necessary, and then add them back into the
    // DTO when we're done, before returning.
    List parents = new ArrayList(dto.getParents());
    List children = new ArrayList(dto.getChildren());
    dto.getParents().clear();
    dto.getChildren().clear();

    // Look up each parent sample and adjust each parent's consumed status and amount remaining if 
    // specified by the user.  Replace the parent with the presisted version with the updated
    // consumed and volume.  Also save the adjusted parent ids in the dirty parents list.
    Set dirtyParentIds = new HashSet();
    int size = parents.size();
    for (int i = 0; i < size; i++) {
      SampleData parent = (SampleData) parents.get(i);
      SampleData persistedParent = 
        SampleFinder.find(securityInfo, SampleSelect.BASIC_IMPORTED_CONFIG_INVENTORYGROUPS, parent.getSampleId());
      SampleTypeConfiguration config = persistedParent.getSampleTypeConfiguration();
      String sampleTypeCui = persistedParent.getSampleTypeCui();
      String registrationFormId = config.getSampleType(sampleTypeCui).getRegistrationFormId();
      FormDefinitionServiceResponse response = FormDefinitionService.SINGLETON.findFormDefinitionById(registrationFormId);
      DataFormDefinition registrationFormDef = response.getDataFormDefinition();
     
    
      boolean isVolumeRelevant = (registrationFormDef.getDataHostElement(ArtsConstants.ATTRIBUTE_VOLUME) != null);
      boolean isWeightRelevant = (registrationFormDef.getDataHostElement(ArtsConstants.ATTRIBUTE_WEIGHT) != null);
      
      if (persistedParent.isConsumed()) {
        // Intentionally do nothing is the persisted parent is consumed, since we don't
        // want to mark it consumed again or set its volume or weight. 
      }
      else if (parent.isConsumed()) {
        persistedParent.setConsumed(true);
        //if the sample was marked consumed, null out it's volume 
        persistedParent.setVolume(null);
        persistedParent.setWeight(null);
        dirtyParentIds.add(persistedParent.getSampleId());
      }
      
       
       if (!parent.isConsumed() && isWeightRelevant) {
          BigDecimal weight = parent.getWeight();
          BigDecimal persistedWeight = persistedParent.getWeight();

          // Set the persisted weight to the specified weight in the following situations:
          //   - A weight was specified and there is no persisted weight.
          //   - A weight was specified, a persisted weight exists, and they're not the same.
          // If a weight was not specified, then do not alter the persisted weight.  
          if (weight != null) {
            if ((persistedWeight == null)
              || ((persistedWeight != null) && !weight.equals(persistedWeight))) {
              persistedParent.setWeight(weight);
              persistedParent.setWeightUnitCui(parent.getWeightUnitCui());
              dirtyParentIds.add(persistedParent.getSampleId());
            }
          }
        }
       if (!parent.isConsumed() && isVolumeRelevant) {
         BigDecimal volume = parent.getVolume();
         BigDecimal persistedVolume = persistedParent.getVolume();
         

         // Set the persisted volume to the specified volume in the following situations:
         //   - A volume was specified and there is no persisted volume.
         //   - A volume was specified, a persisted volume exists, and they're not the same.
         // If a volume was not specified, then do not alter the persisted volume.  
         if (volume != null) {
           if ((persistedVolume == null)
             || ((persistedVolume != null) && !volume.equals(persistedVolume))) {
             persistedParent.setVolume(volume);
             persistedParent.setVolumeUnitCui(parent.getVolumeUnitCui());
             dirtyParentIds.add(persistedParent.getSampleId());
           }
         }
       }
        
       
       
      parents.remove(i);
      parents.add(i, persistedParent);
      derivOp.addParentSample(persistedParent);
    }
    
    //set the list of inventory groups to use, if necessary. 
    List inventoryGroupIds = null;
    if (FormLogic.DB_YES.equalsIgnoreCase(dto.getInheritInventoryGroupsYN())) {
      //if we are to inherit inventory group membership from the parents, determine the
      //groups to inherit.  We can use any of the parents to do this, because at this point 
      //we have determined that they all share a common set of inventory groups
      inventoryGroupIds = new ArrayList();
      List myGroups = LogicalRepositoryUtils.getInventoryGroupsVisibleToUser(securityInfo, 
                               ((SampleData)parents.get(0)).getLogicalRepositories());
      Iterator groupIterator = myGroups.iterator();
      while (groupIterator.hasNext()) {
        LogicalRepository inventoryGroup = (LogicalRepository)groupIterator.next();
        inventoryGroupIds.add(inventoryGroup.getId());
      }
    }
    else if (FormLogic.DB_NO.equalsIgnoreCase(dto.getInheritInventoryGroupsYN())) {
      inventoryGroupIds = ApiFunctions.safeToList(dto.getInventoryGroups());
    }

    // Populate each child sample with inherited attributes from the parent and the prepared by 
    // user from the derivation operation.  Also populate the parent sample id value IF the 
    // operation has a single parent and populate the account key with the value of the account to 
    // which the sample id has been assigned.
    for (int i = 0; i < children.size(); i++) {
      derivOp.addChildSample((SampleData) children.get(i));
    }
    derivOp.populateChildren(btxDetails.getLoggedInUserSecurityInfo(), dto);

    // Adjust the consumed status and/or amount remaining of each parent that has not already been 
    // adjusted, and save the adjusted parent ids in the dirty parents list.
    derivOp.clearParentSamples();
    for (int i = 0; i < parents.size(); i++) {
      SampleData parent = (SampleData) parents.get(i);
      if (!dirtyParentIds.contains(parent.getSampleId())) {
        derivOp.addParentSample(parent);
      }
    }
    List adjustedParents = derivOp.adjustParentAmounts();
    for (int i = 0; i < adjustedParents.size(); i++) {
      SampleData adjustedParent = (SampleData) adjustedParents.get(i);
      dirtyParentIds.add(adjustedParent.getSampleId());
    }

    // Create the derivation operation record.
    String derivationId = createDerivationOperation(dto);
    dto.setDerivationId(derivationId);
    
    // Create each child sample and add it to the output.
    BtxDetailsCreateImportedSample createSampleDetails = new BtxDetailsCreateImportedSample();
    createSampleDetails.setBeginTimestamp(btxDetails.getBeginTimestamp());
    createSampleDetails.setLoggedInUserSecurityInfo(securityInfo);
    createSampleDetails.setTransactionType("iltds_create_imported_sample");
    createSampleDetails.setDerivativeOperationAction(true);
    createSampleDetails.setInventoryGroupIds(inventoryGroupIds);        
    for (int i = 0; i < children.size(); i++) {
      SampleData child = (SampleData) children.get(i);
      createSampleDetails.setSampleData(child);
      createSampleDetails = (BtxDetailsCreateImportedSample) Btx.perform(createSampleDetails);
      dto.addChild(createSampleDetails.getSampleData());
    }

    // Modify each dirty parent and add it to the output.  If the parent is not dirty, then simply
    // add it to the output without modification. 
    BtxDetailsModifyImportedSample modifySampleDetails = new BtxDetailsModifyImportedSample();
    modifySampleDetails.setBeginTimestamp(btxDetails.getBeginTimestamp());
    modifySampleDetails.setLoggedInUserSecurityInfo(securityInfo);
    for (int i = 0; i < parents.size(); i++) {
      SampleData parent = (SampleData) parents.get(i);
      if (dirtyParentIds.contains(parent.getSampleId())) {
        if (parent.isImported()) {
          modifySampleDetails.setTransactionType("iltds_modify_imported_sample");        
        }
        else {
          modifySampleDetails.setTransactionType("iltds_modify_nonimported_sample");        
        }
        modifySampleDetails.setSampleData(parent);
        modifySampleDetails.setDerivativeOperationAction(true);        
        Btx.perform(modifySampleDetails);
      }      
      dto.addParent(parent);
    }

    // Make the parent/child associations.
    for (int i = 0; i < parents.size(); i++) {
      SampleData parent = (SampleData) parents.get(i);
      String parentId = parent.getSampleId();
      for (int j = 0; j < children.size(); j++) {
        SampleData child = (SampleData) children.get(j);
        String childId = child.getSampleId();
        associateParentAndChild(derivationId, parentId, childId);
      }   
    }

    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Records a batch of derivation operations.
   */
  private BTXDetails performDerivationBatch(BtxDetailsDerivationBatch btxDetails)
    throws Exception {

    DerivationBatchDto batchDto = btxDetails.getDto();

    // Create and initialize a BTX details to be used for each individual derivation operation.
    // Use the "no validate" version of the transaction since we did all validation in the
    // validator for this transaction, so we don't need to repeat it when we call the individual
    // transaction.
    BtxDetailsDerivation btxDetailsSingle = new BtxDetailsDerivation();
    btxDetailsSingle.setBeginTimestamp(btxDetails.getBeginTimestamp());
    btxDetailsSingle.setLoggedInUserSecurityInfo(btxDetails.getLoggedInUserSecurityInfo());
    btxDetailsSingle.setTransactionType("iltds_derivation_operation_no_validate");

    // Iterate over all of the derivation operations in the batch and perform each one.
    List dtos = batchDto.getDerivations();
    int size = dtos.size();
    for (int i = 0; i < size; i++) {
      DerivationDto dto = (DerivationDto) dtos.get(i);
      dto.populateEmptyBasicInformation(batchDto);
      btxDetailsSingle.setDto(dto);
      btxDetailsSingle = (BtxDetailsDerivation) Btx.perform(btxDetailsSingle);
      btxDetails.addActionMessages(btxDetailsSingle.getActionMessages());
      
      dtos.remove(i);
      dtos.add(i, btxDetailsSingle.getDto());
    }
    
    //include label printing information, in order to determine if the print labels
    //button should be displayed or not.
    populateLabelPrintingData(btxDetails);
    
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Performs validation for the performDerivationBatch transaction.
   */
  private BTXDetails validatePerformDerivationBatch(BtxDetailsDerivationBatch btxDetails)
    throws Exception {

    // Validate that the appropriate input was specified.
    DerivationBatchDto inputDto = btxDetails.getDto();
    if ((inputDto == null) || (inputDto.getDerivations().isEmpty())) {
      btxDetails.addActionError(
        new BtxActionError("generic.message", "No Parent or Derivative Barcodes were entered."));
    }

    // Perform all necessary validations.  First clone the inputDto so we can restore it
    // if any validations fail.
    else {
      inputDto = new DerivationBatchDto(inputDto); 
      DerivativeOperationsUiValidationServiceFactory factory =
        new DerivativeOperationsUiValidationServiceFactory(btxDetails);
      Validator validator =
        factory.getInstance(DerivativeOperationsUiValidationServiceFactory.UI_STEP3);
      btxDetails.addActionErrors(validator.validate());
    }

    // If validation fails, make sure to restore the input DTO, set the prepared by list and 
    // populate any necessary parent and child info needed in the UI.
    if (!btxDetails.getActionErrors().empty()) {
      btxDetails.setDto(inputDto);
      btxDetails.setPreparedByList(
        IltdsUtils.getArdaisStaffInAccountAsLegalValueSet(
          btxDetails.getLoggedInUserSecurityInfo()));
      populateParentData(btxDetails);
      populateChildData(btxDetails);
    }

    return btxDetails;
  }

  /**
   * Starts a batch of derivation operations.  This performer is intended to be called from the
   * menu item or other similar link that starts the derivation transaction.  
   */
  private BTXDetails performDerivationBatchStart(BtxDetailsDerivationBatchStart btxDetails)
    throws Exception {
    
    // Replace any existing derivation batch dto (which contains all of the information for any
    // previous operation(s)) with a new, empty derivation batch dto.
    btxDetails.setDto(new DerivationBatchDto());

    // if there are selectedSampleIds specified on the btxDetails, the user has selected children
    // from the previous derivation operation(s) to be parents for a subsequent operation.
    // for each such sample create a derivation dto, add the sample to it as a parent, and add 
    // the derivation to the batch
    String[] selectedSampleIds = btxDetails.getSelectedSampleIds();
    if (selectedSampleIds != null && selectedSampleIds.length > 0) {
      for (int i=0; i < selectedSampleIds.length; i++) {
        DerivationDto dto = new DerivationDto();
        SampleData parent = getSampleDetails(selectedSampleIds[i]);
        dto.addParent(parent);
        btxDetails.getDto().addDerivation(dto);
      }
    }

    // Set the prepared by list. 
    btxDetails.setPreparedByList(IltdsUtils.getArdaisStaffInAccountAsLegalValueSet(btxDetails.getLoggedInUserSecurityInfo()));
    //SWP-1054/5 default the prepared by value to the currently logged in user
    String currentUserId = btxDetails.getLoggedInUserSecurityInfo().getUsername();
    if (btxDetails.getPreparedByList().contains(currentUserId)) {
      btxDetails.getDto().setPreparedBy(currentUserId);
    }

    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Performs the operation selection step of a batch of derivation operations.  This 
   * does nothing since this transaction mainly exists for just the validation.
   */
  private BTXDetails performDerivationBatchSelect(BtxDetailsDerivationBatchSelect btxDetails)
    throws Exception {

    // Return the prepared by list so the user can add/change this info 
    btxDetails.setPreparedByList(IltdsUtils.getArdaisStaffInAccountAsLegalValueSet(btxDetails.getLoggedInUserSecurityInfo()));

    //poulate the label printing data so the page can determine whether or not to offer
    //the ability to generate sample ids
    populateLabelPrintingData(btxDetails);
    
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails; 
  }

  /**
   * Performs validation for the performDerivationBatchSelect transaction.
   */
  private BTXDetails validatePerformDerivationBatchSelect(BtxDetailsDerivationBatch btxDetails)
    throws Exception {

    // Validate that the appropriate input was specified.
    DerivationBatchDto inputDto = btxDetails.getDto();
    if (inputDto == null) {
      btxDetails.addActionError(
        new BtxActionError("generic.message", "No derivation operations specified."));
    }

    // Perform all necessary validations.  First clone the inputDto so we can restore it
    // if any validations fail.
    else {
      inputDto = new DerivationBatchDto(inputDto);      
      DerivativeOperationsUiValidationServiceFactory factory =
        new DerivativeOperationsUiValidationServiceFactory(btxDetails);
      Validator validator =
        factory.getInstance(DerivativeOperationsUiValidationServiceFactory.UI_STEP1);
      btxDetails.addActionErrors(validator.validate());
    }

    // If validation fails, make sure to restore the input DTO and set the prepared by list. 
    if (!btxDetails.getActionErrors().empty()) {
      btxDetails.setDto(inputDto);      
      btxDetails.setPreparedByList(
        IltdsUtils.getArdaisStaffInAccountAsLegalValueSet(
          btxDetails.getLoggedInUserSecurityInfo()));
    }
    
    return btxDetails;
  }

  /**
   * Performs the barcode lookup step of a batch of derivation operations.  This transaction
   * performs a query to get all pertinent sample details for each parent sample.
   */
  private BTXDetails performDerivationBatchLookup(BtxDetailsDerivationBatchLookup btxDetails)
    throws Exception {

    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();

    List childSampleTypeChoices = new ArrayList();
    List childSampleRegistrationForms = new ArrayList();
    btxDetails.setPreparedByList(IltdsUtils.getArdaisStaffInAccountAsLegalValueSet(securityInfo));
    btxDetails.setChildSampleTypeChoices(childSampleTypeChoices);
    btxDetails.setChildSampleRegistrationForms(childSampleRegistrationForms);
    DerivationBatchDto batchDto = btxDetails.getDto();
    int dtoCount = batchDto.getDerivations().size();
    boolean generateChildIds = batchDto.isGenerateChildIds();
    // Iterate over all derivation operations, populating the necessary info
    for (int i=0; i<dtoCount; i++) {
      DerivationDto dto = (DerivationDto) batchDto.getDerivations().get(i);
      dto.populateEmptyBasicInformation(batchDto);

      DerivationOperation derivOp = 
        DerivationOperationFactory.SINGLETON.createDerivationOperation(dto);

      // Iterate over all parents in the current derivation operation and replace them with
      // their persisted version.
      List parents = dto.getParents();
      int parentCount = parents.size();
      for (int j = 0; j < parentCount; j++) {
        SampleData parent = (SampleData) parents.get(j);
        SampleData persistedParent =
          SampleFinder.find(securityInfo, SampleSelect.BASIC_IMPORTED_CONFIG_INVENTORYGROUPS, parent.getSampleId());
        parents.remove(j);
        parents.add(j, persistedParent);
        derivOp.addParentSample(persistedParent);
      }
      
      // If we generated child ids, then we assume that the user will want to print them as well,
      // so set the print flag for each child.  The user may choose to override this in the UI,
      // but this is a reasonable default.
      if (generateChildIds) {
        List children = dto.getChildren();
        for (int j = 0; j < children.size(); j++) {
          SampleData child = (SampleData) children.get(j);
          child.setPrintLabel(true);
        }
      }

      //determine if the children should be allowed to inherit the inventory group(s) 
      //of the parent(s) 
      boolean canChildInheritInventoryGroups = IltdsUtils.isSamplesInIdenticalInventoryGroups(securityInfo, parents);
      btxDetails.getAllowInventoryGroupInheritance().put(new Integer(i),new Boolean(canChildInheritInventoryGroups));

      // Find the value child sample types based on the parents and operation.
      List validChildSampleTypes = derivOp.findValidChildSampleTypes();
      LegalValueSet choices = new LegalValueSet();
      for (int k = 0; k < validChildSampleTypes.size(); k++) {
        String sampleTypeCui = (String) validChildSampleTypes.get(k);
        choices.addLegalValue(sampleTypeCui, GbossFactory.getInstance().getDescription(sampleTypeCui));
      }
      childSampleTypeChoices.add(choices);
      
      populateChildRegistrationForms(btxDetails, dto, choices);
      
    }
    
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails; 
  }

  private BTXDetails validatePerformDerivationBatchLookup(BtxDetailsDerivationBatch btxDetails)
    throws Exception {

    // Validate that the appropriate input was specified.
    DerivationBatchDto inputDto = btxDetails.getDto();
    if ((inputDto == null) || (inputDto.getDerivations().isEmpty())) {
      btxDetails.addActionError(
        new BtxActionError("generic.message", "No parent or derivative sample ids or aliases were entered."));
    }

    // Perform all necessary validations.  First clone the inputDto so we can restore it
    // if any validations fail.
    else {
      inputDto = new DerivationBatchDto(inputDto);      
      DerivativeOperationsUiValidationServiceFactory factory =
        new DerivativeOperationsUiValidationServiceFactory(btxDetails);
      Validator validator =
        factory.getInstance(DerivativeOperationsUiValidationServiceFactory.UI_STEP2);
      btxDetails.addActionErrors(validator.validate());
    }

    // If validation fails, make sure to restore the input DTO, set the prepared by list and 
    // populate any necessary parent info needed in the UI.
    if (!btxDetails.getActionErrors().empty()) {
      btxDetails.setDto(inputDto);
      btxDetails.setPreparedByList(
        IltdsUtils.getArdaisStaffInAccountAsLegalValueSet(
          btxDetails.getLoggedInUserSecurityInfo()));
      populateParentData(btxDetails);
    }

    return btxDetails;
  }

  private BTXDetails validatePerformDerivationBatchSummary(BtxDetailsDerivationBatch btxDetails)
    throws Exception {
    // Validate that the appropriate input was specified.
    DerivationBatchDto inputDto = btxDetails.getDto();
    if ((inputDto == null) || (inputDto.getDerivations().isEmpty())) {
      btxDetails.addActionError(
        new BtxActionError("generic.message", "No derivations were specified."));
    }
    return btxDetails;
  }

  /**
   * Begins the process of printing labels for the children in a derivation.
   */
  private BTXDetails performDerivationBatchPrintLabelsStart(BtxDetailsDerivationBatch btxDetails) throws Exception {
    //For every derivation specified, get it's details 
    DerivationBatchDto batchDto = btxDetails.getDto();
    List inputDtos = batchDto.getDerivations();
    DerivationBatchDto outputBatchDto = new DerivationBatchDto();
    for (int i = 0; i < inputDtos.size(); i++) {
      DerivationDto inputDto = (DerivationDto) inputDtos.get(i);
      if (!ApiFunctions.isEmpty(inputDto.getDerivationId())) {
        outputBatchDto.addDerivation(getDerivationDetails(inputDto, btxDetails.getLoggedInUserSecurityInfo(), true, true));
      }
    }
    //iterate over the child samples, defaulting the "print label" checkbox and the number of
    //times to print each label
    Iterator derivationIterator = outputBatchDto.getDerivations().iterator();
    while (derivationIterator.hasNext()) {
      DerivationDto derivation = (DerivationDto)derivationIterator.next();
      Iterator childIterator = derivation.getChildren().iterator();
      while (childIterator.hasNext()) {
        SampleData child = (SampleData)childIterator.next();
        child.setPrintLabel(true);
        child.setLabelCount("1");
      }
    }
    btxDetails.setDto(outputBatchDto);
    populateLabelPrintingData(btxDetails);
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  private BTXDetails validatePerformDerivationBatchPrintLabelsStart(BtxDetailsDerivationBatch btxDetails)
    throws Exception {
    // Validate that the appropriate input was specified.
    DerivationBatchDto inputDto = btxDetails.getDto();
    if ((inputDto == null) || (inputDto.getDerivations().isEmpty())) {
      btxDetails.addActionError(
        new BtxActionError("generic.message", "No derivations were specified."));
    }
    //make sure all specified derivations exist
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    Iterator derivationIterator = inputDto.getDerivations().iterator();
    while (derivationIterator.hasNext()) {
      DerivationDto derivation = (DerivationDto)derivationIterator.next();
      String derivationId = derivation.getDerivationId();
      if (ApiFunctions.isEmpty(derivation.getDerivationId())) {
        btxDetails.addActionError(
            new BtxActionError("generic.message", "A derivation with no id was specified."));
      }
      else {
        derivation = getDerivationDetails(derivation, securityInfo, false, false);
        if (ApiFunctions.isEmpty(derivation.getDerivationId())) {
          btxDetails.addActionError(
              new BtxActionError("generic.message", "A derivation with an unrecognized id (" 
                  + derivationId + ") was specified."));
        }
      }
    }
    return btxDetails;
  }

  /**
   * Print labels for selected children in a derivation.
   */
  private BTXDetails performDerivationBatchPrintLabels(BtxDetailsDerivationBatch btxDetails) throws Exception {
    LabelService labelService = new LabelService();
    Iterator derivationIterator = btxDetails.getDto().getDerivations().iterator();
    int sampleCount = 0;
    while (derivationIterator.hasNext()) {
      DerivationDto derivation = (DerivationDto)derivationIterator.next();
      Iterator sampleIterator = derivation.getChildren().iterator();
      String previousCount = null;
      String currentCount = null;
      String previousTemplateName = null;
      String currentTemplateName = null;
      String previousPrinterName = null;
      String currentPrinterName = null;
      List processedSampleIds = new ArrayList();
      while (sampleIterator.hasNext()) {
        SampleData sample = (SampleData)sampleIterator.next();
        if (sample.isPrintLabel()) {
          sampleCount = sampleCount + 1;
          String sampleId = sample.getSampleId();
          //if this sample is the start of a new group (count, template, and printer are all the 
          //same), collect its information
          if (ApiFunctions.isEmpty(processedSampleIds)) {
            previousCount = sample.getLabelCount();
            previousTemplateName = sample.getTemplateDefinitionName();
            previousPrinterName = sample.getPrinterName();
            processedSampleIds.add(sampleId);
          }
          else {
            //get the count, template, and printer for the current sample
            currentCount = sample.getLabelCount();
            currentTemplateName = sample.getTemplateDefinitionName();
            currentPrinterName = sample.getPrinterName();
            //if the current count, template, and printer match the previous count, template, and
            //printer, add this sample to the list of processed samples
            if (currentCount.equalsIgnoreCase(previousCount) &&
                currentTemplateName.equalsIgnoreCase(previousTemplateName) &&
                currentPrinterName.equalsIgnoreCase(previousPrinterName)) {
              processedSampleIds.add(sampleId);
            }
            //if not, print the processed samples and start a new group
            else {
              String[] sampleIds = ApiFunctions.toStringArray(processedSampleIds);
              labelService.printSampleLabels(btxDetails.getLoggedInUserSecurityInfo(), 
                  sampleIds, previousCount, previousTemplateName, previousPrinterName);
              previousCount = sample.getLabelCount();
              previousTemplateName = sample.getTemplateDefinitionName();
              previousPrinterName = sample.getPrinterName();
              processedSampleIds.clear();
              processedSampleIds.add(sampleId);
            }
          }
        }
      }
      //print the last group of samples
      String[] sampleIds = ApiFunctions.toStringArray(processedSampleIds);
      labelService.printSampleLabels(btxDetails.getLoggedInUserSecurityInfo(), 
          sampleIds, previousCount, previousTemplateName, previousPrinterName);
    }
    if (sampleCount == 1) {
      btxDetails.addActionMessage(new BtxActionMessage("orm.message.label.labelPrinted", 
          "the specified", Constants.LABEL_PRINTING_OBJECT_TYPE_SAMPLE));
    }
    else {
      btxDetails.addActionMessage(new BtxActionMessage("orm.message.label.labelsPrinted", 
          "the specified", Constants.LABEL_PRINTING_OBJECT_TYPE_SAMPLE + "s"));
    }
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  private BTXDetails validatePerformDerivationBatchPrintLabels(BtxDetailsDerivationBatch btxDetails)
    throws Exception {
    //perform the same validation as was done at the start of the process
    validatePerformDerivationBatchPrintLabelsStart(btxDetails);
    //populate label printing data, in case we end up returning an error
    populateLabelPrintingData(btxDetails);
    
    //make sure all label printing data for each child sample is fully and correctly specified
    //since all of the logic for validating the printing of sample labels is contained
    //in the label service, call out to the service and convert any errors returned
    //into BtxActionErrors.
    LabelService labelService = new LabelService();
    Iterator derivationIterator = btxDetails.getDto().getDerivations().iterator();
    boolean sampleSpecified = false;
    while (derivationIterator.hasNext()) {
      DerivationDto derivation = (DerivationDto)derivationIterator.next();
      Iterator sampleIterator = derivation.getChildren().iterator();
      while (sampleIterator.hasNext()) {
        SampleData sample = (SampleData)sampleIterator.next();
        if (sample.isPrintLabel()) {
          sampleSpecified = true;
          String sampleId = sample.getSampleId();
          String[] sampleIds = new String[1];
          sampleIds[0] = sampleId;
          BigrValidationErrors bve = labelService.validatePrintSampleLabels(btxDetails.getLoggedInUserSecurityInfo(), 
              sampleIds, sample.getLabelCount(), sample.getTemplateDefinitionName(), sample.getPrinterName());
          BigrValidationError[] errors = bve.getErrors();
          for (int i = 0; i < errors.length; i++) {
            String message = errors[i].getMessage();
            message = message.substring(0, 1).toLowerCase() + message.substring(1);
            btxDetails.addActionError(new BtxActionError("generic.message", "For sample " 
                + sampleId + ", " + message));
          }
        }
      }
    }
    
    //if no samples were selected for printing, notify the user that they must select at least one.
    if (!sampleSpecified) {
      btxDetails.addActionError(new BtxActionError("generic.message", 
          "At least one " + Constants.LABEL_PRINTING_OBJECT_TYPE_SAMPLE + " must be selected."));
    }
    return btxDetails;
  }

  /**
   * Returns the summary for a batch of derivation operations.
   */
  private BTXDetails performDerivationBatchSummary(BtxDetailsDerivationBatch btxDetails) throws Exception {
    //For every derivation specified, get it's details 
    DerivationBatchDto batchDto = btxDetails.getDto();
    List inputDtos = batchDto.getDerivations();
    DerivationBatchDto outputBatchDto = new DerivationBatchDto();
    for (int i = 0; i < inputDtos.size(); i++) {
      DerivationDto inputDto = (DerivationDto) inputDtos.get(i);
      if (!ApiFunctions.isEmpty(inputDto.getDerivationId())) {
        //get the details for the derivation. Don't retrieve details for the children, as 
        //that will be handled in DerivationBatchDetailsAction due to the need to have those 
        //details in a ResultsHelper for display.
        outputBatchDto.addDerivation(getDerivationDetails(inputDto, btxDetails.getLoggedInUserSecurityInfo(), false, false));
      }
    }
    btxDetails.setDto(outputBatchDto);
    
    //include label printing information, in order to determine if the print labels
    //button should be displayed or not. (SWP-1064)
    populateLabelPrintingData(btxDetails);
    
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }
  
  private DerivationDto getDerivationDetails(DerivationDto dto, SecurityInfo securityInfo,
                                             boolean retrieveParentDetails, boolean retrieveChildDetails) {
    DerivationDto returnValue = new DerivationDto();
    String derivationQuery = "select * from iltds_derivation where derivation_id = ?";
    String genealogyQuery = "select * from iltds_sample_genealogy where derivation_id = ?";
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      // Prepare and execute the derivationQuery query.
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(derivationQuery);
      pstmt.setString(1, dto.getDerivationId());
      rs = pstmt.executeQuery();
      if (rs.next()) {
        returnValue.setDerivationId(rs.getString("derivation_id"));
        returnValue.setOperationCui(rs.getString("operation_cui"));
        returnValue.setOtherOperation(rs.getString("other_operation"));
        returnValue.setOperationDate(rs.getDate("derivation_date"));
        returnValue.setPreparedBy(rs.getString("prepared_by"));
      }
      rs.close();
      pstmt.close();
      pstmt = con.prepareStatement(genealogyQuery);
      pstmt.setString(1, dto.getDerivationId());
      rs = pstmt.executeQuery();
      Set parentIds = new HashSet();
      Set childIds = new HashSet();
      while (rs.next()) {
        String parentId = rs.getString("parent_sample_barcode_id");
        if (!parentIds.contains(parentId)) {
          parentIds.add(parentId);
          SampleData parentSample = null;
          if (retrieveParentDetails) {
            parentSample = getSampleDetails(parentId);
          }
          else {
            parentSample = new SampleData();
            parentSample.setSampleId(parentId);
          }
          returnValue.addParent(parentSample);
        }
        String childId = rs.getString("child_sample_barcode_id");
        if (!childIds.contains(childId)) {
          childIds.add(childId);
          SampleData childSample = null;
          if (retrieveChildDetails) {
            childSample = getSampleDetails(childId);
          }
          else {
            childSample = new SampleData();
            childSample.setSampleId(childId);
          }
          returnValue.addChild(childSample);
        }
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    return returnValue;
  }
  
  private SampleData getSampleDetails(String sampleId) {
    SampleData returnValue = null;
    String query = "select * from iltds_sample where sample_barcode_id = ?";
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      pstmt.setString(1, sampleId);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        returnValue = new SampleData(DbUtils.getColumnNames(rs), rs);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(rs);
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }
    return returnValue;
  }

  private String createDerivationOperation(DerivationDto dto) {
    String derivationId = null;
    String insertString =
      "{ call insert"
      + " into iltds_derivation (operation_cui, other_operation, derivation_date, prepared_by)"
      + " values (?, ?, ?, ?) returning derivation_id into ? }";

    Connection con = null;
    CallableStatement cstmt = null;
    try {
      con = ApiFunctions.getDbConnection();
      cstmt = con.prepareCall(insertString);

      cstmt.setString(1, dto.getOperationCui());
      cstmt.setString(2, dto.getOtherOperation());
      cstmt.setDate(3, dto.getOperationDate());
      cstmt.setString(4, dto.getPreparedBy());
      cstmt.registerOutParameter(5, Types.VARCHAR);

      cstmt.execute();

      derivationId = cstmt.getString(5);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(cstmt);
      ApiFunctions.close(con);
    }
    return derivationId;
  }

  private void associateParentAndChild(
    String derivationId,
    String parentSampleId,
    String childSampleId) {
    
    String insertString =
      "insert into"
      + " iltds_sample_genealogy (derivation_id, parent_sample_barcode_id, child_sample_barcode_id)"
      + " values (?, ?, ?)";

    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(insertString);

      pstmt.setString(1, derivationId);
      pstmt.setString(2, parentSampleId);
      pstmt.setString(3, childSampleId);

      pstmt.executeUpdate();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }    
  }
  
  /*
   * Method to populate any parent data required by the front end.
   */
  private void populateParentData(BtxDetailsDerivationBatch btxDetails) {

    //Iterate over the derivative operations, and replace each parent with it's corresponding
    //persisted version so that any data that might be needed in the front end is in place.
    //For the attributes a user can modify on a parent (i.e. isConsumed and Volume), override
    //the persisted values with the user input.
    int dtoCount = btxDetails.getDto().getDerivations().size();
    for (int i=0; i<dtoCount; i++) {
      DerivationDto dto = (DerivationDto) btxDetails.getDto().getDerivations().get(i);
      List parents = dto.getParents();
      int size = parents.size();
      for (int j = 0; j < size; j++) {
        SampleData parent = (SampleData) parents.get(j);
        SampleData persistedParent =
          SampleFinder.find(btxDetails.getLoggedInUserSecurityInfo(), 
                            SampleSelect.BASIC_IMPORTED_CONFIG_INVENTORYGROUPS, parent.getSampleId());
        if (persistedParent != null) {
          persistedParent.setConsumed(parent.isConsumed());
          persistedParent.setVolumeAsString(parent.getVolumeAsString());
          persistedParent.setVolumeUnitCui(parent.getVolumeUnitCui());
          persistedParent.setWeightAsString(parent.getWeightAsString());
          persistedParent.setWeightUnitCui(parent.getWeightUnitCui());
          
          
          parents.remove(j);
          parents.add(j, persistedParent);
        }
      }
      //determine if the children should be allowed to inherit the inventory group(s) 
      //of the parent(s) 
      boolean canChildInheritInventoryGroups = IltdsUtils.isSamplesInIdenticalInventoryGroups(btxDetails.getLoggedInUserSecurityInfo(), parents);
      btxDetails.getAllowInventoryGroupInheritance().put(new Integer(i),new Boolean(canChildInheritInventoryGroups));
    }
  }

  /*
   * Method to populate the child sample type information for each derivation in the batch.
   */
  private void populateChildData(BtxDetailsDerivationBatch btxDetails) {
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();

    List childSampleTypeChoices = new ArrayList();
    List childSampleRegistrationForms = new ArrayList();
    btxDetails.setChildSampleTypeChoices(childSampleTypeChoices);
    btxDetails.setChildSampleRegistrationForms(childSampleRegistrationForms);
    
    Iterator derivativeIterator = btxDetails.getDto().getDerivations().iterator();
    while (derivativeIterator.hasNext()) {
      DerivationDto dto = (DerivationDto) derivativeIterator.next();
      DerivationOperation derivOp = 
        DerivationOperationFactory.SINGLETON.createDerivationOperation(dto);
      List parents = dto.getParents();
      for (int i = 0; i < parents.size(); i++) {
        derivOp.addParentSample((SampleData) parents.get(i));
      }
      List validChildSampleTypes = derivOp.findValidChildSampleTypes(); 
      LegalValueSet choices = new LegalValueSet();
      for (int i = 0; i < validChildSampleTypes.size(); i++) {
        String sampleTypeCui = (String) validChildSampleTypes.get(i);
        choices.addLegalValue(sampleTypeCui, GbossFactory.getInstance().getDescription(sampleTypeCui));
      }
      childSampleTypeChoices.add(choices);
      
      populateChildRegistrationForms(btxDetails, dto, choices);
    }
  }
    
  private void populateChildRegistrationForms(BtxDetailsDerivationBatch btxDetails, DerivationDto dto, LegalValueSet choices) {
    
    //first get the sample type configuration information from the policy of the parents in the 
    //operation (all parents must belong to the same case, so just use the first parent)
    SampleData parent = SampleFinder.find(btxDetails.getLoggedInUserSecurityInfo(), SampleSelect.BASIC_IMPORTED_CONFIG_INVENTORYGROUPS, dto.getParent(0).getSampleId());
    SampleTypeConfiguration stc = parent.getPolicyData().getSampleTypeConfiguration();
    //now get the registration form for each of the child sample types
    Iterator sampleTypeIterator = choices.getIterator();
    Map sampleTypeToFormMap = new HashMap();
    while (sampleTypeIterator.hasNext()) {
      SampleType childSampleType = stc.getSampleType(((LegalValue) sampleTypeIterator.next()).getValue());
      String registrationFormId = childSampleType.getRegistrationFormId();
      if (!ApiFunctions.isEmpty(registrationFormId)) {
        FormDefinitionServiceResponse response = FormDefinitionService.SINGLETON.findFormDefinitionById(registrationFormId);
        if (response.getErrors().empty()) {
          DataFormDefinition registrationFormDef = response.getDataFormDefinition();
          //remove any data elements marked as ignored by derivative operations, set the
          //data elements to have their standard name, etc
          processRegistrationFormForDerivativeOperation(registrationFormDef);
          //add the registration form to the map from sample types to registration forms
          sampleTypeToFormMap.put(childSampleType.getCui(), registrationFormDef);
        }
      }
    }
    btxDetails.getChildSampleRegistrationForms().add(sampleTypeToFormMap);
  }
  
  private void processRegistrationFormForDerivativeOperation(DataFormDefinition dataFormDefinition) {
    DataElementTaxonomy dataElementTaxonomy = DetService.SINGLETON.getDataElementTaxonomy();
    DataFormDefinitionElements retainedElements = new DataFormDefinitionElements();
    //process the data elements on the form, retaining all that are not to be ignored
    DataFormDefinitionElement[] existingElements = dataFormDefinition.getDataFormElements().getDataFormElements();
    for (int elementCount=0; elementCount < existingElements.length; elementCount ++) { 
      DataFormDefinitionElement formElement = existingElements[elementCount];
      boolean ignoreElement = false;
      Tag[] tags = null;
      if (formElement.isHostElement()) {
        tags = formElement.getDataHostElement().getTags();
      }
      else {
        tags = formElement.getDataDataElement().getTags();
      }
      for (int tagCount=0; tagCount<tags.length; tagCount++) {
        Tag tag = tags[tagCount];
        if (TagTypes.DERIV_IGNORES.equalsIgnoreCase(tag.getType())) {
          ignoreElement = new Boolean(tag.getValue()).booleanValue();
        }
      }
      if (!ignoreElement) {
        if (formElement.isHostElement()) {
          DataFormDefinitionHostElement hostElement = formElement.getDataHostElement();
          //reset the display name on the data element to be the system name, since
          //we will always show system names for the column headers
          hostElement.setDisplayName(GbossFactory.getInstance().getDescription(hostElement.getHostId()));
        }
        else {
          DataFormDefinitionDataElement dataElement = formElement.getDataDataElement();
          //reset the display name on the data element to be the system name, since
          //we will always show system names for the column headers
          dataElement.setDisplayName(dataElementTaxonomy.getDataElement(dataElement.getCui()).getDescription());
        }
        retainedElements.addDataFormElement(formElement);
      }
    }
    dataFormDefinition.setDataFormElements(retainedElements);
  }

  private void populateLabelPrintingData(BtxDetailsDerivationBatch btxDetails) {
    //determine the templates available for each sample type, and the printers available for each
    //template. if the sample label printing configuration information is invalid, return a message 
    //letting the user know about the problem
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    String accountId = securityInfo.getAccount();
    try {
      Map sampleTypesToTemplates = LabelPrintingConfiguration.getSampleLabelTemplateDefinitionsAndPrintersBySampleType(accountId);
      btxDetails.setLabelPrintingData(sampleTypesToTemplates);
    }
    catch (IllegalStateException ise) {
      //log the error so the problems are documented
      ApiLogger.log("Label Printing Configuration refresh failed.", ise);
      btxDetails.setLabelPrintingData(new HashMap());
      btxDetails.addActionMessage(
          new BtxActionMessage("orm.error.label.invalidPrintingConfiguration"));
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
