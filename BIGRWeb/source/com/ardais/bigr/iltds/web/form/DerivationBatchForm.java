package com.ardais.bigr.iltds.web.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.configuration.SystemConfiguration;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.assistants.LogicalRepository;
import com.ardais.bigr.iltds.beans.ListGenerator;
import com.ardais.bigr.iltds.beans.ListGeneratorHome;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.javabeans.DerivationBatchDto;
import com.ardais.bigr.javabeans.DerivationBatchDtoForUi;
import com.ardais.bigr.javabeans.DerivationDto;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.javabeans.SampleDataForUI;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.LogicalRepositoryUtils;
import com.ardais.bigr.util.WebUtils;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;
import com.gulfstreambio.bigr.error.BigrValidationError;
import com.gulfstreambio.bigr.error.BigrValidationException;
import com.gulfstreambio.bigr.id.SampleIdService;
import com.gulfstreambio.gboss.GbossFactory;
import com.gulfstreambio.kc.form.DataElement;
import com.gulfstreambio.kc.form.DataElementValue;
import com.gulfstreambio.kc.form.FormInstance;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionElement;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionHostElement;

public class DerivationBatchForm extends BigrActionForm {

  private DerivationBatchDto _dto;
  private LegalValueSet _preparedByList;
  private LegalValueSet _appearanceChoices;
  private LegalValueSet _fixativeTypeChoices;
  private LegalValueSet _formatDetailChoices;
  private LegalValueSet _bufferTypeChoices;
  private LegalValueSet _totalNumberOfCellsExponentialRepChoices;
  private LegalValueSet _volumeUnitChoices;
  private LegalValueSet _weightUnitChoices;
  private LegalValueSet _operationChoices;
  private LegalValueSet _inventoryGroupChoices;
  private List _childSampleTypeChoices;
  
  //map that indicates for each derivation whether children should be allowed to
  //inherit inventory group membership from their parents or not.  Children will
  //be allowed to inherit inventory group membership from their parents when it is
  //true that each parent belongs to an identical set of inventory groups.
  private Map _allowInventoryGroupInheritance = new HashMap();
  
  //a list of Maps (one for each derivation), mapping a child sample type to it's registration 
  //form DataFormDefinition
  private List _childSampleRegistrationForms;
  
  private String _resultsFormDefinitionId;
  private List _resultsFormDefinitions;
  
  //a string array of child sample ids created in one set of operations to be used as parents for
  //subsequent operations (MR8912)
  private String[] _selectedSampleIds;

  //a mapping from derivation indexes to the potential data elements used by it's children
  Map _derivationToPotentialChildDataElements;
  
  //all potential child data data elements (not host) across all derivations
  DataFormDefinitionElement[] _allPotentialChildDataDataElements;
  
  //label printing information (available templates, printers, etc)
  private Map _labelPrintingData;
  
  private boolean _removeEmptyDerivations;

  /* (non-Javadoc)
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    _dto = null;
    _preparedByList = null;
    _appearanceChoices = null;
    _fixativeTypeChoices = null;
    _formatDetailChoices = null;
    _bufferTypeChoices = null;
    _totalNumberOfCellsExponentialRepChoices = null;
    _volumeUnitChoices = null;
    _weightUnitChoices = null;
    _operationChoices = null;
    _childSampleTypeChoices = null;
    _childSampleRegistrationForms = null;
    _allowInventoryGroupInheritance = null;
    //populate the inventory group choices
    resetInventoryGroupChoices(WebUtils.getSecurityInfo(request));
    _resultsFormDefinitionId = null;
    //don't refresh the results form definition list, as it won't have changed and this
    //way there is no need to find all of the places where it would need to be refreshed.
    //_resultsFormDefinitions = null;
    _selectedSampleIds = null;
    _derivationToPotentialChildDataElements = null;
    _allPotentialChildDataDataElements = null;
    _labelPrintingData = null;
    _removeEmptyDerivations = true;
  }
  
  private void resetInventoryGroupChoices(SecurityInfo securityInfo) {
    //populate the list of available inventory groups from which the user can choose
    _inventoryGroupChoices = new LegalValueSet();
    List logicalRepositories = null;
    if (securityInfo.isHasPrivilege(SecurityInfo.PRIV_VIEW_ALL_LOGICAL_REPOS)) {
      logicalRepositories = LogicalRepositoryUtils.getAllLogicalRepositories();      
    }
    else {
      logicalRepositories = securityInfo.getLogicalRepositoriesByFullName();
    }
    Iterator iterator = logicalRepositories.iterator();
    while (iterator.hasNext()) {
      LogicalRepository lr = (LogicalRepository)iterator.next();
      _inventoryGroupChoices.addLegalValue(lr.getId(),lr.getFullName());
    }
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
    return null;
  }

  public String[] getHourList() {
    return Constants.HOURS;
  }

  public String[] getMinuteList() {
    return Constants.MINUTES;
  }

  /**
   * @return
   */
  public LegalValueSet getOperationChoices() {
    if (_operationChoices == null) {
      _operationChoices =
        SystemConfiguration
          .getConceptList(SystemConfiguration.CONCEPT_LIST_DERIVATION_OPERATIONS)
          .toLegalValueSet();
    }
    return _operationChoices;
  }

  /**
   * @return
   */
  public LegalValueSet getPreparedByList() {
    return _preparedByList;
  }

  /**
   * @param list
   */
  public void setPreparedByList(LegalValueSet list) {
    _preparedByList = list;
  }

  public String getOperation() {
    String returnValue = null;
    String operationCui = getDto().getOperationCui();
    if (!ApiFunctions.isEmpty(operationCui))  {
      returnValue = GbossFactory.getInstance().getDescription(operationCui);
      if (ArtsConstants.OTHER_DERIVATION_OPERATION.equalsIgnoreCase(operationCui)) {
        returnValue = returnValue + " (" + getDto().getOtherOperation() + ")";
      }
    }
    return returnValue;
  }
  
  /**
   * @return
   */
  public LegalValueSet getAppearanceChoices() {
    if (_appearanceChoices == null) {
      try {
        ListGeneratorHome home = (ListGeneratorHome) EjbHomes.getHome(ListGeneratorHome.class);
        ListGenerator listBean = home.create();
        _appearanceChoices = listBean.getSpecimenTypes();
      }
      catch (Exception e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
    }
    return _appearanceChoices;
  }

  /**
   * @return
   */
  public LegalValueSet getBufferTypeChoices() {
    if (_bufferTypeChoices == null) {
      _bufferTypeChoices = BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_BUFFER_TYPE);
    }
    return _bufferTypeChoices;
  }

  /**
   * @return
   */
  public LegalValueSet getFixativeTypeChoices() {
    if (_fixativeTypeChoices == null) {
      _fixativeTypeChoices = BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_TYPE_OF_FIXATIVE);
    }
    return _fixativeTypeChoices;
  }

  /**
   * @return
   */
  public LegalValueSet getFormatDetailChoices() {
    if (_formatDetailChoices == null) {
      _formatDetailChoices = BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_SAMPLE_FORMAT_DETAIL);
    }
    return _formatDetailChoices;
  }

  /**
   * @return
   */
  public LegalValueSet getTotalNumberOfCellsExponentialRepChoices() {
    if (_totalNumberOfCellsExponentialRepChoices == null) {
      _totalNumberOfCellsExponentialRepChoices = BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_EXP_CELLS_PER_ML);
    }
    return _totalNumberOfCellsExponentialRepChoices;
  }
  
  public LegalValueSet getVolumeUnitChoices() {
    if (_volumeUnitChoices == null) {
      _volumeUnitChoices = BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_SAMPLE_VOLUME_UNIT);   
    }
    return _volumeUnitChoices;
  }
  
  public LegalValueSet getWeightUnitChoices() {
    if (_weightUnitChoices == null) {
      _weightUnitChoices = BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_SAMPLE_WEIGHT_UNIT);
    }
    return _weightUnitChoices;
  }
  
  /* (non-Javadoc)
   * @see com.ardais.bigr.web.form.BigrActionForm#describeIntoBtxDetails(com.ardais.bigr.iltds.btx.BTXDetails, com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  public void describeIntoBtxDetails(
    BTXDetails btxDetails0,
    BigrActionMapping mapping,
    HttpServletRequest request) {

    DerivationBatchDto batchDto = getDto();
    boolean generateChildIds = batchDto.isGenerateChildIds();
    SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);
    
    // Remove all empty derivations.  First we'll remove empty parents and children from each
    // derivation, and then we'll remove the derivation itself if it is empty.
    List derivativeDtos = batchDto.getDerivations();
    int totalDerivations = derivativeDtos.size();
    for (int j = totalDerivations - 1; j >= 0; j--) {
      DerivationDto derivationDto = (DerivationDto) derivativeDtos.get(j);
      List parents = derivationDto.getParents();
      List children = derivationDto.getChildren();

      // If we're not generating child ids, then make sure the generated sample count is null. 
      if (!generateChildIds) {
        derivationDto.setGeneratedSampleCount(null);
      }
      
      String generatedSampleCount = derivationDto.getGeneratedSampleCount();
      
      // Remove all empty children.
      for (int k = children.size() - 1; k >= 0; k--) {
        SampleData sampleData = (SampleData) children.get(k);
        String sampleId = sampleData.getSampleId();
        String generatedSampleId = sampleData.getGeneratedSampleId();
        // If we're generating children, then remove the child if there is not a generated id.
        // If there is a generated id, then that means we previously generated the child sample
        // id so keep the child.
        if (generateChildIds) {
          if (ApiFunctions.isEmpty(generatedSampleId) || ApiFunctions.isEmpty(generatedSampleCount)) {
            children.remove(k);
          }
        }
        // If we're not generating children, then make sure the generated id is empty and remove 
        // the child if the sample id is empty. 
        else {
          sampleData.setGeneratedSampleId(null);
          if (ApiFunctions.isEmpty(sampleId)) {
            children.remove(k);
          }
        }
      }
      // Remove all empty parents.
      for (int k = parents.size() - 1; k >= 0; k--) {
        if (ApiFunctions.isEmpty(((SampleData) parents.get(k)).getSampleId())) {
          parents.remove(k);
        }
      }

      // Remove the derivation itself if all children and parents were empty.
      if ((children.size() == 0) && (parents.size() == 0) 
          && ApiFunctions.isEmpty(generatedSampleCount)
          && isRemoveEmptyDerivations()) {
        derivativeDtos.remove(j);
      }
      
      else {
        // If we are to generate child sample ids, then generate them here.  They may have already
        // been generated, or the user may have used the back button to specify more or less ids
        // be generated.  This method will deal with all of these situations and alter the input
        // children list.
        try {
          if (generateChildIds) {
            generateChildIds(children, generatedSampleCount, securityInfo);
          }
        }

        //TODO MRC: Change btxAction.doPerform to look for BTX action errors after creating the
        // btx details and not call the performer if there are any
        catch (BigrValidationException e) {
          BigrValidationError[] errors = e.getErrors().getErrors();
          for (int k = 0; k < errors.length; k++) {
            btxDetails0.addActionError(new BtxActionError("generic.message", errors[k].getMessage()));
          }
        }

        //populate the child registration forms
        Iterator childIterator = children.iterator();
        while (childIterator.hasNext()) {
          SampleData child = (SampleData) childIterator.next();
          String jsonForm = child.getKcForm();
          FormInstance form = ApiFunctions.isEmpty(jsonForm) ?
              new FormInstance() :
              com.gulfstreambio.kc.web.support.WebUtils.convertToFormInstance(jsonForm);
          child.setRegistrationFormInstance(form);
          // Remove all data elements that have no value, for two reasons.  
          // 1) Most importantly, the data element might have no value because it is not applicable 
          //    to the child sample and therefore should not be passed along as part of the form 
          //    instance to avoid validation errors about invalid data elements.  Because the child 
          //    table is a conglomerate of all the data elements used across the various child 
          //    types, each child will have a DataElement for every data element in the table, 
          //    regardless of whether that data element is applicable to the specific child type.  
          //    If the data element is not applicable to the child it will be empty, so by removing
          //    all empty data elements we remove all non-applicable ones.  
          // 2) It serves no purpose to pass along empty data elements, so the form we do pass along 
          //    will be cleaner.
          DataElement[] dataElements = form.getDataElements();
          for (int m = dataElements.length - 1; m >= 0; m--) {
            DataElement dataElement = dataElements[m];
            DataElementValue[] values = dataElement.getDataElementValues();
            if (values.length == 0) {
              form.removeDataElement(dataElement);
            }
            else {
              boolean hasValue = false;
              for (int n = 0; n < values.length; n++) {
                DataElementValue value = values[n];
                if (!(ApiFunctions.isEmpty(value.getValue()) 
                    && ApiFunctions.isEmpty(value.getValueOther()))) {
                  hasValue = true;
                  break;
                }
              }
              if (!hasValue) {
                form.removeDataElement(dataElement);
              }
            }
          }
        }
        
        //copy the appropriate derivation batch details to each individual derivation.  
        derivationDto.populateEmptyBasicInformation(batchDto);
      }
    }

    super.describeIntoBtxDetails(btxDetails0, mapping, request);
  }

  // Generates child ids for the number of children specified.  For each generated child id,
  // creates a SampleData instance and adds it to the list.
  private void generateChildIds(List children, String sampleCount, SecurityInfo securityInfo) 
    throws BigrValidationException {
    // First, make sure sampleCount is valid since it has not been validated yet.  If it is
    // not valid, return since a validation error will be reported during validation later on.
    // Also make sure to return an empty children list since the count is not a positive number.
    // The list may have children in it if the back button was pressed, for example, but now
    // the number of children is not valid so delete the children.
    int count = ApiFunctions.safeInteger(sampleCount, 0);
    if (count <= 0) {
      for (int i = children.size() - 1; i >= 0; i--) {
        children.remove(i);
      }
      return;
    }
    
    // Make sure we have the correct number of children.  First, for each child we want, check
    // if we already have the child.  If we do, simply make sure its sample id is set to the
    // generated sample id.  If we don't, then create the new child, setting both the generated
    // sample id and sample id to a newly generated sample id, and add the new child to the list.
    SampleIdService service = SampleIdService.getInstance();
    int existing = children.size();
    for (int i = 0; i < count; i++) {
      if (i < existing) {
        SampleData sampleData = (SampleData) children.get(i);
        sampleData.setSampleId(sampleData.getGeneratedSampleId());
      }
      else {
        SampleData sampleData = new SampleDataForUI();
        String sampleId = service.generateId(securityInfo);
        sampleData.setGeneratedSampleId(sampleId);
        sampleData.setSampleId(sampleId);
        children.add(sampleData);
      }
    }
    
    // If there are more existing children than needed, then delete the ones we don't need.
    if (existing > count) {
      for (int i = existing - 1; i >= count; i--) {
        children.remove(i);
      }
    }
  }

  public DerivationBatchDto getDto() {
    if (_dto == null) {
      _dto = new DerivationBatchDtoForUi();
    }
    return _dto;
  }

  public void setDto(DerivationBatchDto dto) {
    _dto = dto;
  }
  
  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.PopulatableFromBtxDetails#populateFromBtxDetails(com.ardais.bigr.iltds.btx.BTXDetails)
   */
  public void populateFromBtxDetails(BTXDetails btxDetails) {
    super.populateFromBtxDetails(btxDetails);

    DerivationBatchDto batchDto = getDto();
    if (batchDto.isGenerateChildIds()) {
      List derivationDtos = batchDto.getDerivations();
      for (int i = 0; i < derivationDtos.size(); i++) {
        DerivationDto derivationDto = (DerivationDto) derivationDtos.get(i);
        List children = derivationDto.getChildren();
        for (int j = 0; j < children.size(); j++) {
          SampleData sampleData = (SampleData) children.get(j);
          sampleData.setSampleId(null);
        }
      }
    }
  }

  /**
   * @return
   */
  public List getChildSampleTypeChoices() {
    return _childSampleTypeChoices;
  }

  /**
   * @param list
   */
  public void setChildSampleTypeChoices(List list) {
    _childSampleTypeChoices = list;
  }

  /**
   * @return
   */
  public LegalValueSet getInventoryGroupChoices() {
    return _inventoryGroupChoices;
  }

  /**
   * @return
   */
  public Map getAllowInventoryGroupInheritance() {
    return _allowInventoryGroupInheritance;
  }

  /**
   * @param map
   */
  public void setAllowInventoryGroupInheritance(Map map) {
    _allowInventoryGroupInheritance = map;
  }
  
  public boolean isAllowInventoryGroupInheritance(String derivationId) {
    boolean returnValue = false;
    Map myMap = getAllowInventoryGroupInheritance();
    if (myMap != null) {
      Boolean b = (Boolean) myMap.get(new Integer(derivationId));
      if (b != null) {
        returnValue = b.booleanValue();
      }
    }
    return returnValue;
  }

  public String getResultsFormDefinitionId() {
    return _resultsFormDefinitionId;
  }
  
  public List getResultsFormDefinitions() {
    return _resultsFormDefinitions;
  }
  
  public void setResultsFormDefinitionId(String resultsFormDefinitionId) {
    _resultsFormDefinitionId = resultsFormDefinitionId;
  }
  
  public void setResultsFormDefinitions(List resultsFormDefinitions) {
    _resultsFormDefinitions = resultsFormDefinitions;
  }
  
  public String[] getSelectedSampleIds() {
    if (_selectedSampleIds == null)
      _selectedSampleIds = new String[0];
    return _selectedSampleIds;
  }
  
  public void setSelectedSampleIds(String[] selectedSampleIds) {
    _selectedSampleIds = selectedSampleIds;
  }
  
  /**
   * @return Returns the childSampleRegistrationForms.
   */
  public List getChildSampleRegistrationForms() {
    return _childSampleRegistrationForms;
  }
  
  /**
   * @param childSampleRegistrationForms The childSampleRegistrationForms to set.
   */
  public void setChildSampleRegistrationForms(List childSampleRegistrationForms) {
    _childSampleRegistrationForms = childSampleRegistrationForms;
    //process the child sample registration forms, creating any data structures the jsp might need
    processChildSampleRegistrationForms();
  }
  
  private void processChildSampleRegistrationForms() {
    //create a map from derivation index to the child sample data elements that might be
    //displayed for that derivation as well as a list of all data elements that might be used
    //across all derivations
    List allPotentialChildDataDataElements = new ArrayList();
    List allProcessedDataElementCuis = new ArrayList();
    _derivationToPotentialChildDataElements = new HashMap();
    int index = 0;
    Iterator registrationFormsIterator = ApiFunctions.safeList(_childSampleRegistrationForms).iterator();
    while (registrationFormsIterator.hasNext()) {
      List dataElementsInDerivation = new ArrayList();
      List processedFormDefinitions = new ArrayList();
      List processedElementCuis = new ArrayList();
      Map sampleTypeToRegistrationForms = (Map)registrationFormsIterator.next();
      Iterator registrationFormIterator = sampleTypeToRegistrationForms.values().iterator();
      while (registrationFormIterator.hasNext()) {
        DataFormDefinition registrationForm = (DataFormDefinition)registrationFormIterator.next();
        //if we've already handled this registration form for this derivation don't process it again
        if (!processedFormDefinitions.contains(registrationForm.getFormDefinitionId())) {
          DataFormDefinitionElement[] dataElements = registrationForm.getDataFormElements().getDataFormElements();
          for (int elementCount=0; elementCount < dataElements.length; elementCount ++) { 
            DataFormDefinitionElement formElement = dataElements[elementCount];
            if (formElement.isHostElement()) {
              DataFormDefinitionHostElement hostElement = formElement.getDataHostElement();
              if (!processedElementCuis.contains(hostElement.getHostId())) {
                dataElementsInDerivation.add(formElement);
                processedElementCuis.add(hostElement.getHostId());
              }
            }
            else {
              DataFormDefinitionDataElement dataElement = formElement.getDataDataElement();
              if (!processedElementCuis.contains(dataElement.getCui())) {
                dataElementsInDerivation.add(formElement);
                processedElementCuis.add(dataElement.getCui());
              }
            }
          }
          processedFormDefinitions.add(registrationForm.getFormDefinitionId());
        }
      }
      //now that we've walked all the registration forms for the derivation, create the mapping
      //from the derivation index to it's potential child data elements and add any new elements
      //to the list of all data elements
      if (ApiFunctions.isEmpty(dataElementsInDerivation)) {
        _derivationToPotentialChildDataElements.put(new Integer(index), new DataFormDefinitionElement[0]);
      }
      else {
        _derivationToPotentialChildDataElements.put(new Integer(index), dataElementsInDerivation.toArray(new DataFormDefinitionElement[0]));
        Iterator dataElementIterator = dataElementsInDerivation.iterator();
        while (dataElementIterator.hasNext()) {
          DataFormDefinitionElement formElement = (DataFormDefinitionElement)dataElementIterator.next();
          if (formElement.isDataElement()) {
            DataFormDefinitionDataElement dataElement = formElement.getDataDataElement();
            if (!allProcessedDataElementCuis.contains(dataElement.getCui())) {
              allPotentialChildDataDataElements.add(formElement);
              allProcessedDataElementCuis.add(dataElement.getCui());
            }
          }
        }
      }
      index = index + 1;
    }
    if (ApiFunctions.isEmpty(allPotentialChildDataDataElements)) {
      _allPotentialChildDataDataElements = new DataFormDefinitionElement[0];
    }
    else {
      _allPotentialChildDataDataElements = (DataFormDefinitionElement[])allPotentialChildDataDataElements.toArray(new DataFormDefinitionElement[0]);
    }

  }
  
  public DataFormDefinitionElement[] getPotentialDataFormDefinitionElementsForDerivation(int index) {
    return (DataFormDefinitionElement[]) _derivationToPotentialChildDataElements.get(new Integer(index));
  }
  
  public DataFormDefinitionElement[] getAllPotentialChildDataDataElements() {
    if (_allPotentialChildDataDataElements == null) {
      _allPotentialChildDataDataElements = new DataFormDefinitionElement[0];      
    }
    return _allPotentialChildDataDataElements;
  }

  public Map getLabelPrintingData() {
    return _labelPrintingData;
  }
  
  public void setLabelPrintingData(Map labelPrintingData) {
    _labelPrintingData = labelPrintingData;
  }
  
  public boolean isRemoveEmptyDerivations() {
    return _removeEmptyDerivations;
  }

  public void setRemoveEmptyDerivations(boolean removeEmptyDerivations) {
    _removeEmptyDerivations = removeEmptyDerivations;
  }
}
