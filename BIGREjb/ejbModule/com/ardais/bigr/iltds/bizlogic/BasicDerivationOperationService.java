package com.ardais.bigr.iltds.bizlogic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.concept.BigrConcept;
import com.ardais.bigr.concept.BigrConceptList;
import com.ardais.bigr.configuration.SystemConfiguration;
import com.ardais.bigr.iltds.databeans.DerivativeOperation;
import com.ardais.bigr.iltds.databeans.SampleTypeConfiguration;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.iltds.helpers.SampleSelect;
import com.ardais.bigr.javabeans.DerivationDto;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.kc.form.def.TagTypes;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.VariablePrecisionDateTime;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.det.DetValueSet;
import com.gulfstreambio.kc.form.DataElement;
import com.gulfstreambio.kc.form.DataElementValue;
import com.gulfstreambio.kc.form.FormInstance;
import com.gulfstreambio.kc.form.def.FormDefinitionService;
import com.gulfstreambio.kc.form.def.FormDefinitionServiceResponse;
import com.gulfstreambio.kc.form.def.Tag;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionElement;

/**
 * Enforces the basic, generic business rules of derivation operations.  This class handles the
 * bulk of these rules using data-driven mechanisms based on sample types and the operation being 
 * performed, but it is expected that this class will be subclassed to enforce rules for specific 
 * derivation operations that cannot be handled in such a fashion.
 */
public class BasicDerivationOperationService implements DerivationOperation {

  private DerivationDto _dto;  
  private List _parentSamples;
  private List _childSamples;
  
  private static final String OTHER = "Other";
  private static final String UNIT = "Unit";
  
  /**
   * Creates a new BasicDerivationOperationService for the specified derivation operation.
   * Only the basic information of the input derivation operation will be used when necessary, not
   * the parents and children.  These must still be set separately via calls to 
   * {@link addParentSample(com.ardais.bigr.javabeans.SampleData)} and
   * {@link addChildSample(com.ardais.bigr.javabeans.SampleData)} as necessary before calling
   * a method that uses them.
   * 
   * @param  dto  the DerivationDto that specifies the derivation operation.
   */
  BasicDerivationOperationService(DerivationDto dto) {
    super();
    _dto = dto;    
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.bizlogic.DerivationOperation#addParentSample(com.ardais.bigr.javabeans.SampleData)
   */
  public void addParentSample(SampleData sample) {
    getParentSamples().add(sample);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.bizlogic.DerivationOperation#clearParentSamples()
   */
  public void clearParentSamples() {
    _parentSamples = null;
  }

  private List getParentSamples() {
    if (_parentSamples == null) {
      _parentSamples = new ArrayList();
    }
    return _parentSamples;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.bizlogic.DerivationOperation#addChildSample(com.ardais.bigr.javabeans.SampleData)
   */
  public void addChildSample(SampleData sample) {
    getChildSamples().add(sample);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.bizlogic.DerivationOperation#clearChildSamples()
   */
  public void clearChildSamples() {
    _childSamples = null;
  }

  private List getChildSamples() {
    if (_childSamples == null) {
      _childSamples = new ArrayList();
    }
    return _childSamples;
  }
  /**
   * Performs weight adjustment of the parent sample
   */
  public List adjustParentWeight(SampleData parent, boolean doAdjustVolume){
    List adjustedParents = new ArrayList();
    Iterator children = getChildSamples().iterator();
    BigDecimal totalChildWeight = new BigDecimal(0.000);
    BigDecimal childMgConversionFactor = null;
    BigDecimal parentWeightInMg = parent.getWeightInMg();
    int childScale = 0;
    while (children.hasNext()) {
       SampleData child = (SampleData) children.next();
      BigDecimal childWeight = child.getWeight();
       childMgConversionFactor = Constants.getWeightInMgConversionFactor(child.getWeightUnitCui());
       //weight has been set but weightInMg still needs to be calculated here. Not available yet
      BigDecimal weightInMg = null;
      
      if (childWeight != null) {
        
//      use scale of child with highest scale
        if (childScale < child.getWeight().scale()){
        childScale = child.getWeight().scale();
        }
        
        weightInMg = childWeight.multiply(childMgConversionFactor);
        BigDecimal childWeightInMg = child.getWeightInMg();
        totalChildWeight = totalChildWeight.add(childWeightInMg);
      }
    }
  
    // Finally, adjust the parent's weight.  If the calculation makes the volume 
    //or weight less than zero, then set it to zero.
    BigDecimal newWeightInMg = parentWeightInMg.subtract(totalChildWeight);
    if (newWeightInMg.compareTo(new BigDecimal(0.0)) == -1) {
      newWeightInMg = new BigDecimal(0.000);
    }
   
    BigDecimal parentMgConversionFactor = Constants.getWeightInMgConversionFactor(parent.getWeightUnitCui());
    
    //decimal scale should accomodate unit conversion, or sample with most decimal places.
    int parentScale = parent.getWeight().scale();
    
    int newScale = childScale;
    if (parentScale > childScale){
       newScale = parentScale;
          }
    if (childMgConversionFactor != null && childMgConversionFactor.compareTo(new BigDecimal("0")) > 0){
      BigDecimal conversionFactorRatio = parentMgConversionFactor.divide(childMgConversionFactor, BigDecimal.ROUND_HALF_DOWN);
      String conversionFactorRatioString = conversionFactorRatio.toString();
             int conversionFactorScale = conversionFactorRatioString.length() - 1;
    						newScale = conversionFactorScale + newScale;
      }
    
    //no reason to have more than 9 decimal places this takes a 999999 g to  1ng range
    if (newScale > 9){
      newScale = 9;
    }
    
   BigDecimal parentWeight = newWeightInMg.divide(parentMgConversionFactor, newScale,BigDecimal.ROUND_HALF_DOWN);
    parent.setWeight(parentWeight);
   
    if (!doAdjustVolume){
    adjustedParents.add(parent);
    return adjustedParents;
    }
    
    else {adjustedParents = adjustParentVolume(parent);
      return adjustedParents;
    }
    
  }
  /**
   * Performs volume adjustment of the parent sample
   */
  public List adjustParentVolume(SampleData parent){
    List adjustedParents = new ArrayList();
    Iterator children = getChildSamples().iterator();
    BigDecimal totalChildVolume = new BigDecimal(0.000);
    BigDecimal childUlConversionFactor = null;
    BigDecimal parentVolumeInUl = parent.getVolumeInUl();
    int childScale = 0;
    while (children.hasNext()) {
      SampleData child = (SampleData) children.next();
      
      BigDecimal childVolume = child.getVolume();
       childUlConversionFactor = Constants.getVolumeInUlConversionFactor(child.getVolumeUnitCui());
       //volume has been set but volumeInUl still needs to be calculated here. 
      
      if (childVolume != null) {
        
//      use scale of child with highest scale
        if (childScale < child.getVolume().scale()){
        childScale = child.getVolume().scale();
        }
        
       
        BigDecimal childVolumeInUl = child.getVolumeInUl();
        totalChildVolume = totalChildVolume.add(childVolumeInUl);
      }
    }
 
    // Finally, adjust the parent's volume   If the calculation makes the volume 
    //or weight less than zero, then set it to zero.
    BigDecimal newVolumeInUl = parentVolumeInUl.subtract(totalChildVolume);
    if (newVolumeInUl.compareTo(new BigDecimal(0.0)) == -1) {
      newVolumeInUl = new BigDecimal(0.000);
    }
    BigDecimal parentUlConversionFactor = Constants.getVolumeInUlConversionFactor(parent.getVolumeUnitCui());
    
    //decimal scale should accomodate unit conversion, or sample with most decimal places.
    int parentScale = parent.getVolume().scale();
    
    int newScale = childScale;
    if (parentScale > childScale){
       newScale = parentScale;
          }
    
    if (childUlConversionFactor != null && childUlConversionFactor.compareTo(new BigDecimal("0")) > 0){
      BigDecimal conversionFactorRatio = parentUlConversionFactor.divide(childUlConversionFactor, BigDecimal.ROUND_HALF_DOWN);
      String conversionFactorRatioString = conversionFactorRatio.toString();
             int conversionFactorScale = conversionFactorRatioString.length() - 1;
             newScale = conversionFactorScale + newScale;
      }
    //no reason to have more than 6 decimal places this takes a 999999 mL to  1nl range
    if (newScale > 6){
      newScale = 6;
    }
   BigDecimal parentVolume = newVolumeInUl.divide(parentUlConversionFactor, newScale,BigDecimal.ROUND_HALF_DOWN);
    parent.setVolume(parentVolume);
    adjustedParents.add(parent);
    return adjustedParents;
  }
  
  /**
   * Performs generic parent amount adjustment.  This consists of adjusting the volume of a single
   * parent to subtract the volumes of all children.  If there is more than one parent or volume
   * is not applicable to the parent, then no adjustments are made and an empty list is returned. 
   * 
   * @returns  The list of adjusted parents.  This will either be a single parent sample or an
   *           empty list if the parent was not adjusted.
   * @see com.ardais.bigr.iltds.bizlogic.DerivationOperation#adjustParentAmounts()
   */
  public List adjustParentAmounts() {
    List adjustedParents = new ArrayList();
    List parents = getParentSamples();
    
    // This method only adjusts a single parent, so there has to be exactly one parent.
    if (parents.size() != 1) {
      return adjustedParents;
    }
   // If the parent's volume is null, then do not attempt to adjust it, just leave it null.
    SampleData parent = (SampleData) parents.get(0);
    
    BigDecimal parentVolumeInUl = parent.getVolumeInUl();
    BigDecimal parentWeightInMg = parent.getWeightInMg();
    if (parentVolumeInUl == null && parentWeightInMg == null) {
      return adjustedParents;      
    }
    
    // If volume and weight is not relevant for the parent, then return without adjusting the parent.
    boolean isVolumeRelevant = false;
    boolean isWeightRelevant = false;
    
    SampleTypeConfiguration config = parent.getSampleTypeConfiguration();
    if (config != null) {
      String sampleTypeCui = parent.getSampleTypeCui();
      String registrationFormId = config.getSampleType(sampleTypeCui).getRegistrationFormId();
      FormDefinitionServiceResponse response = FormDefinitionService.SINGLETON.findFormDefinitionById(registrationFormId);
      DataFormDefinition registrationFormDef = response.getDataFormDefinition();
      isVolumeRelevant = (registrationFormDef.getDataHostElement(ArtsConstants.ATTRIBUTE_VOLUME) != null);
      isWeightRelevant = (registrationFormDef.getDataHostElement(ArtsConstants.ATTRIBUTE_WEIGHT) != null);
    }
    
    if (!isVolumeRelevant && !isWeightRelevant) {
      return adjustedParents;
    }
    
   boolean doAdjustWeight = false;
   boolean doAdjustVolume = false;
   if (isWeightRelevant && !ApiFunctions.isEmpty(parent.getWeightAsString())){
     doAdjustWeight = true;
   }
   if (isVolumeRelevant && !ApiFunctions.isEmpty(parent.getVolumeAsString())){
     doAdjustVolume = true;
   }
   
   //if samples have volume and weight, we dont want one to erase the results of the other
   //if they have both, adjustWeight will pass the parent to adjustVolume
   if (doAdjustWeight){
     adjustedParents = adjustParentWeight(parent, doAdjustVolume);
   } 
   if (doAdjustVolume  && !doAdjustWeight){
     adjustedParents = adjustParentVolume(parent);
    }      
      return adjustedParents;
  }
  
  /**
   * For each child sample in a derivative operation, populates all attributes that are marked as 
   * derivativeInherits="true" and not derivativeIgnores="true" in the registration form definition 
   * used for the child if the parent(s) have a value for the attribute.  Also populates the
   * "Prepared By" attribute on each child from the value specified on the derivation, if that 
   * attribute is applicable to the child.
   *
   * @return  The list of all children set into this object, whether they were altered or not. 
   * @see com.ardais.bigr.iltds.bizlogic.DerivationOperation#populateChildren()
   */
  public List populateChildren(SecurityInfo securityInfo, DerivationDto dto) {
    SampleData repSample = findCommonParentValues();
    
    //first get the sample type configuration information from the policy of the parents in the 
    //operation (all parents must belong to the same case, so just use the first parent)
    SampleData parent = SampleFinder.find(securityInfo, SampleSelect.BASIC_IMPORTED_CONFIG_INVENTORYGROUPS, ((SampleData)getParentSamples().get(0)).getSampleId());
    SampleTypeConfiguration stc = parent.getPolicyData().getSampleTypeConfiguration();
    
    //now, for every child populate it's data
    Iterator children = getChildSamples().iterator();
    while (children.hasNext()) {
      SampleData child = (SampleData) children.next();
      //populate the consent and donor ids, parent id (if there is a single parent), and account key
      child.setConsentId(repSample.getConsentId());
      child.setArdaisId(repSample.getArdaisId());
      if (getParentSamples().size() == 1) {
        child.setParentId(((SampleData)getParentSamples().get(0)).getSampleId());
      }
      child.setArdaisAcctKey(IltdsUtils.getAccountAssignedToSample(child.getSampleId()));
      //now populate the inherited attributes
      String sampleType = child.getSampleTypeCui();
      String registrationFormId = stc.getSampleType(sampleType).getRegistrationFormId();
      FormDefinitionServiceResponse response = FormDefinitionService.SINGLETON.findFormDefinitionById(registrationFormId);
      DataFormDefinition registrationFormDef = response.getDataFormDefinition();
      DataFormDefinitionElement[] dataElements = registrationFormDef.getDataFormElements().getDataFormElements();
      //walk the elements on the registration form, identifying those that inherit (and are not
      //ignored) in derivative operations
      List inheritedAttributes = new ArrayList();
      for (int elementCount=0; elementCount < dataElements.length; elementCount ++) { 
        DataFormDefinitionElement formElement = dataElements[elementCount];
        Tag[] tags = null;
        String cui = null;
        if (formElement.isHostElement()) {
          tags = formElement.getDataHostElement().getTags();
          cui = formElement.getHostElement().getHostId();
        }
        else {
          tags = formElement.getDataDataElement().getTags();
          cui = formElement.getDataDataElement().getCui();
        }
        boolean isElementInherited = false;
        boolean isElementIgnored = false;
        //date of collection and date of preservation are always inherited and cannot be
        //ignored
        if (ArtsConstants.ATTRIBUTE_DATE_OF_COLLECTION.equalsIgnoreCase(cui) ||
            ArtsConstants.ATTRIBUTE_DATE_OF_PRESERVATION.equalsIgnoreCase(cui)) {
          isElementInherited = true;
          isElementIgnored = false;
        }
        else {
          for (int tagCount=0; tagCount<tags.length; tagCount++) {
            Tag tag = tags[tagCount];
            if (TagTypes.DERIV_INHERITS.equalsIgnoreCase(tag.getType())) {
              isElementInherited = new Boolean(tag.getValue()).booleanValue();
            }
            if (TagTypes.DERIV_IGNORES.equalsIgnoreCase(tag.getType())) {
              isElementIgnored = new Boolean(tag.getValue()).booleanValue();
            }
          }
        }
        if (isElementInherited && !isElementIgnored) {
          inheritedAttributes.add(cui);
        }
      }

      Iterator i = inheritedAttributes.iterator();
      while (i.hasNext()) {
        String cui = (String) i.next();
        if (cui.equals(ArtsConstants.ATTRIBUTE_BUFFER_TYPE)) {
          String value = repSample.getBufferTypeCui();
          if ((value != null) && ApiFunctions.isEmpty(child.getBufferTypeCui())) {
            child.setBufferTypeCui(value);
            if (ArtsConstants.OTHER_BUFFER_TYPE.equals(value)) {
              String otherValue = repSample.getBufferTypeOther();
              if ((otherValue != null) && ApiFunctions.isEmpty(child.getBufferTypeOther())) {
                child.setBufferTypeOther(otherValue);
              }
            }
          }
        }

        else if (cui.equals(ArtsConstants.ATTRIBUTE_CELLS_PER_ML)) {
          BigDecimal value = repSample.getCellsPerMl();
          if ((value != null) && (child.getCellsPerMl() == null)) {
            child.setCellsPerMl(value);
          }
        }
      
        else if (cui.equals(ArtsConstants.ATTRIBUTE_COMMENT)) {
          String value = repSample.getAsmNote();
          if ((value != null) && ApiFunctions.isEmpty(child.getAsmNote())) {
            child.setAsmNote(value);
          }
        }

        else if (cui.equals(ArtsConstants.ATTRIBUTE_CONCENTRATION)) {
          BigDecimal value = repSample.getConcentration();
          if ((value != null) && (child.getConcentration() == null)) {
            child.setConcentration(value);
          }
        }

        else if (cui.equals(ArtsConstants.ATTRIBUTE_DATE_OF_COLLECTION)) { 
          VariablePrecisionDateTime value = repSample.getCollectionDateTime();
          if ((value != null) && 
              ((child.getCollectionDateTime() == null) ||
               (child.getCollectionDateTime().isEmpty()))) {
            child.setCollectionDateTime(value);
          }
        }

        else if (cui.equals(ArtsConstants.ATTRIBUTE_DATE_OF_PRESERVATION)) { 
          VariablePrecisionDateTime value = repSample.getPreservationDateTime();
          if ((value != null) && 
              ((child.getPreservationDateTime() == null) ||
               (child.getPreservationDateTime().isEmpty()))) {
            child.setPreservationDateTime(value);
          }
        }

        else if (cui.equals(ArtsConstants.ATTRIBUTE_FIXATIVE_TYPE)) { 
          String value = repSample.getFixativeType();
          if ((value != null) && ApiFunctions.isEmpty(child.getFixativeType())) {
            child.setFixativeType(value);
          }
        }

        else if (cui.equals(ArtsConstants.ATTRIBUTE_FORMAT_DETAIL)) { 
          String value = repSample.getFormatDetail();
          if ((value != null) && ApiFunctions.isEmpty(child.getFormatDetail())) {
            child.setFormatDetail(value);
          }
        }

        else if (cui.equals(ArtsConstants.ATTRIBUTE_GROSS_APPEARANCE)) { 
          String value = repSample.getGrossAppearance();
          if ((value != null) && ApiFunctions.isEmpty(child.getGrossAppearance())) {
            child.setGrossAppearance(value);
          }
        }

        else if (cui.equals(ArtsConstants.ATTRIBUTE_PERCENT_VIABILITY)) { 
          Integer value = repSample.getPercentViability();
          if ((value != null) && (child.getPercentViability() == null)) {
            child.setPercentViability(value);
          }
        }

        else if (cui.equals(ArtsConstants.ATTRIBUTE_PREPARED_BY)) { 
          String value = repSample.getPreparedBy();
          if ((value != null) && ApiFunctions.isEmpty(child.getPreparedBy())) {
            child.setPreparedBy(value);
          }
        }

        else if (cui.equals(ArtsConstants.ATTRIBUTE_PROCEDURE)) { 
          String value = repSample.getProcedure();
          if ((value != null) && ApiFunctions.isEmpty(child.getProcedure())) {
            child.setProcedure(value);
            if (ArtsConstants.OTHER_PROCEDURE.equals(value)) {
              String otherValue = repSample.getProcedureOther();
              if ((otherValue != null) && ApiFunctions.isEmpty(child.getProcedureOther())) {
                child.setProcedureOther(otherValue);
              }
            }
          }
        }

        else if (cui.equals(ArtsConstants.ATTRIBUTE_SOURCE)) { 
          String value = repSample.getSource();
          if ((value != null) && ApiFunctions.isEmpty(child.getSource())) {
            child.setSource(value);
          }
        }

        else if (cui.equals(ArtsConstants.ATTRIBUTE_TISSUE)) { 
          String value = repSample.getTissue();
          if ((value != null) && ApiFunctions.isEmpty(child.getTissue())) {
            child.setTissue(value);
            if (ArtsConstants.OTHER_TISSUE.equals(value)) {
              String otherValue = repSample.getTissueOther();
              if ((otherValue != null) && ApiFunctions.isEmpty(child.getTissueOther())) {
                child.setTissueOther(otherValue);
              }
            }
          }
        }

        else if (cui.equals(ArtsConstants.ATTRIBUTE_TOTAL_NUMBER_OF_CELLS)) { 
          BigDecimal value = repSample.getTotalNumOfCells();
          if ((value != null) && (child.getTotalNumOfCells() == null)) {
            child.setTotalNumOfCells(value);
            String unitCui = repSample.getTotalNumOfCellsExRepCui();
            if ((unitCui != null) && ApiFunctions.isEmpty(child.getTotalNumOfCellsExRepCui())) {
              child.setTotalNumOfCellsExRepCui(unitCui);
            }
          }
        }        
        else if (cui.equals(ArtsConstants.ATTRIBUTE_VOLUME)) {
          BigDecimal value = repSample.getVolume();
          if ((value != null) && (child.getVolume() == null)) {
            child.setVolumeUnitCui(repSample.getVolumeUnitCui());
            child.setVolume(value);
         }
        }
        else if (cui.equals(ArtsConstants.ATTRIBUTE_WEIGHT)) {
            BigDecimal value = repSample.getWeight();
            if ((value != null) && (child.getWeight() == null)) {
             child.setWeightUnitCui(repSample.getWeightUnitCui());
              child.setWeight(value);
            }
          }
        
        else if (cui.equals(ArtsConstants.ATTRIBUTE_YIELD)) {
          BigDecimal value = repSample.getYield();
          if ((value != null) && (child.getYield() == null)) {
            child.setYield(value);
          }
        }
        else {
          //if the cui is not one of the BIGR core attributes, it must be a KC data element
          //if the child doesn't have a registration form instance, give it one since we're about
          //to set a value on it
          FormInstance childRegistrationFormInstance = child.getRegistrationFormInstance();
          if (childRegistrationFormInstance == null) {
            childRegistrationFormInstance = new FormInstance();
            child.setRegistrationFormInstance(childRegistrationFormInstance);
          }
          //see if the representative parent has a data element that corresponds to this one,
          //and if so see the data element on the child registration form
          FormInstance parentRegistrationFormInstance = repSample.getRegistrationFormInstance();
          if (parentRegistrationFormInstance != null) {
            DataElement dataElement = parentRegistrationFormInstance.getDataElement(cui);
            if (dataElement != null) {
              childRegistrationFormInstance.addDataElement(dataElement);
            }
          }
        }
      }  // next attribute
      
      //populate the prepared by attribute on the child with the value on the derivation, if 
      //applicable
      boolean isPreparedByApplicable = registrationFormDef.getDataHostElement(ArtsConstants.ATTRIBUTE_PREPARED_BY) != null;
      if (isPreparedByApplicable) {
        child.setPreparedBy(dto.getPreparedBy());
      }
      
    }  // next child
    
    return getChildSamples();
  }

  /**
   * Returns a SampleData populated with the values of any attributes having a non-empty, common 
   * value across parents.  Attributes that have either an empty or non-common value are not 
   * populated.
   * 
   * @return  a SampleData
   */
  public SampleData findCommonParentValues() {
    Map bigrAttributeValues = new HashMap();
    Map kcDataElements = new HashMap();
    SampleData repSample = new SampleData();
    
    if (!ApiFunctions.isEmpty(getParentSamples())) {
      
      // Consent id and donor id always inherit, and they must be the same for all parents, so
      // save them from any parent.
      repSample.setConsentId(((SampleData)getParentSamples().get(0)).getConsentId());
      repSample.setArdaisId(((SampleData)getParentSamples().get(0)).getArdaisId());
      
      //handle the BIGR core attributes
      findCommonParentBigrValues(bigrAttributeValues);
      populateCommonParentBigrValues(repSample, bigrAttributeValues);
      
      //handle the KC attributes
      findCommonParentKcValues(kcDataElements);
      populateCommonParentKcValues(repSample, kcDataElements);
      
    }
    return repSample;
  }
  
  private void findCommonParentBigrValues(Map attributeValues) {
    Set attributeValuesConflict = new HashSet();

    // Iterate over all of the attributes 
    BigrConceptList attributes = SystemConfiguration.getConceptList(SystemConfiguration.CONCEPT_LIST_SAMPLE_ATTRIBUTES);

    // Iterate over all of the parent samples.  For every attribute get the parent(s) value(s), 
    // and save the value for inheritance by each child if the value of each non-null parent 
    // matches. 
    Iterator parents = getParentSamples().iterator();
    while (parents.hasNext()) {
      SampleData parent = (SampleData) parents.next();
          
      Iterator i = attributes.iterator();
      while (i.hasNext()) {
        String attributeCui = ((BigrConcept) i.next()).getCode();
        if (!attributeValuesConflict.contains(attributeCui)) {

          if (attributeCui.equals(ArtsConstants.ATTRIBUTE_BUFFER_TYPE)) { 
            String value = parent.getBufferTypeCui();
            determineParentValueString(attributeCui, value, attributeValues, attributeValuesConflict);
            if (!attributeValuesConflict.contains(attributeCui)) {
              if (ArtsConstants.OTHER_BUFFER_TYPE.equals(value)) {
                String otherValue = parent.getBufferTypeOther();
                determineParentValueOther(attributeCui, otherValue, attributeValues, attributeValuesConflict);
              }
            }
          }

          else if (attributeCui.equals(ArtsConstants.ATTRIBUTE_CELLS_PER_ML)) { 
            BigDecimal value = parent.getCellsPerMl();
            determineParentValueBigDecimal(attributeCui, value, attributeValues, attributeValuesConflict);
          }

          else if (attributeCui.equals(ArtsConstants.ATTRIBUTE_COMMENT)) { 
            String value = parent.getAsmNote();
            determineParentValueString(attributeCui, value, attributeValues, attributeValuesConflict);
          }

          else if (attributeCui.equals(ArtsConstants.ATTRIBUTE_CONCENTRATION)) { 
            BigDecimal value = parent.getConcentration();
            determineParentValueBigDecimal(attributeCui, value, attributeValues, attributeValuesConflict);
          }

          else if (attributeCui.equals(ArtsConstants.ATTRIBUTE_DATE_OF_COLLECTION)) { 
            VariablePrecisionDateTime value = parent.getCollectionDateTime();
            determineParentValueVpdt(attributeCui, value, attributeValues, attributeValuesConflict);
          }

          else if (attributeCui.equals(ArtsConstants.ATTRIBUTE_DATE_OF_PRESERVATION)) { 
            VariablePrecisionDateTime value = parent.getPreservationDateTime();
            determineParentValueVpdt(attributeCui, value, attributeValues, attributeValuesConflict);
          }

          else if (attributeCui.equals(ArtsConstants.ATTRIBUTE_FIXATIVE_TYPE)) { 
            String value = parent.getFixativeType();
            determineParentValueString(attributeCui, value, attributeValues, attributeValuesConflict);
          }

          else if (attributeCui.equals(ArtsConstants.ATTRIBUTE_FORMAT_DETAIL)) { 
            String value = parent.getFormatDetail();
            determineParentValueString(attributeCui, value, attributeValues, attributeValuesConflict);
          }

          else if (attributeCui.equals(ArtsConstants.ATTRIBUTE_GROSS_APPEARANCE)) { 
            String value = parent.getGrossAppearance();
            determineParentValueString(attributeCui, value, attributeValues, attributeValuesConflict);
          }

          else if (attributeCui.equals(ArtsConstants.ATTRIBUTE_PERCENT_VIABILITY)) { 
            Integer value = parent.getPercentViability();
            determineParentValueInteger(attributeCui, value, attributeValues, attributeValuesConflict);
          }

          else if (attributeCui.equals(ArtsConstants.ATTRIBUTE_PREPARED_BY)) { 
            String value = parent.getPreparedBy();
            determineParentValueString(attributeCui, value, attributeValues, attributeValuesConflict);
          }

          else if (attributeCui.equals(ArtsConstants.ATTRIBUTE_PROCEDURE)) { 
            String value = parent.getProcedure();
            determineParentValueString(attributeCui, value, attributeValues, attributeValuesConflict);
            if (!attributeValuesConflict.contains(attributeCui)) {
              if (ArtsConstants.OTHER_PROCEDURE.equals(value)) {
                String otherValue = parent.getProcedureOther();
                determineParentValueOther(attributeCui, otherValue, attributeValues, attributeValuesConflict);
              }
            }
          }

          else if (attributeCui.equals(ArtsConstants.ATTRIBUTE_TISSUE)) { 
            String value = parent.getTissue();
            determineParentValueString(attributeCui, value, attributeValues, attributeValuesConflict);
            if (!attributeValuesConflict.contains(attributeCui)) {
              if (ArtsConstants.OTHER_TISSUE.equals(value)) {
                String otherValue = parent.getTissueOther();
                determineParentValueOther(attributeCui, otherValue, attributeValues, attributeValuesConflict);
              }
            }
          }

          else if (attributeCui.equals(ArtsConstants.ATTRIBUTE_TOTAL_NUMBER_OF_CELLS)) {
            BigDecimal value = parent.getTotalNumOfCells();
            determineParentValueBigDecimal(attributeCui, value, attributeValues, attributeValuesConflict);
            if (value != null && !attributeValuesConflict.contains(attributeCui)) {
              String unitValue = parent.getTotalNumOfCellsExRepCui();
              determineParentValueUnit(attributeCui, unitValue, attributeValues, attributeValuesConflict);
            }
          }

          else if (attributeCui.equals(ArtsConstants.ATTRIBUTE_VOLUME)) { 
            BigDecimal value = parent.getVolume();
            String unitValue = parent.getVolumeUnitCui();
            determineParentValueBigDecimal(attributeCui, value, attributeValues, attributeValuesConflict);
            //add the  unit of measure to the common values
            if (value != null && !attributeValuesConflict.contains(attributeCui)) {
           determineParentValueUnit(attributeCui, unitValue, attributeValues, attributeValuesConflict);
            }
          }
          
          else if (attributeCui.equals(ArtsConstants.ATTRIBUTE_WEIGHT)) { 
            BigDecimal value = parent.getWeight();
            String unitValue = parent.getWeightUnitCui();
            determineParentValueBigDecimal(attributeCui, value, attributeValues, attributeValuesConflict);
            //add the  unit of measure to the common values
           if (value != null && !attributeValuesConflict.contains(attributeCui)) {
             determineParentValueUnit(attributeCui, unitValue, attributeValues, attributeValuesConflict);
            }
          }
          
          else if (attributeCui.equals(ArtsConstants.ATTRIBUTE_YIELD)) { 
            BigDecimal value = parent.getYield();
            determineParentValueBigDecimal(attributeCui, value, attributeValues, attributeValuesConflict);
          }
          
          else if (attributeCui.equals(ArtsConstants.ATTRIBUTE_SOURCE)) { 
            String value = parent.getSource();
            determineParentValueString(attributeCui, value, attributeValues, attributeValuesConflict);
          }
          
          else {
            throw new ApiException("Unrecognized attribute CUI encountered: " + attributeCui);
          }
        }
      }
    }
  }
  
  private void populateCommonParentBigrValues(SampleData repSample, Map attributeValues) {
    //populate the representative sample with common BIGR values, if any
    if (!attributeValues.isEmpty()) {
      Iterator i = attributeValues.keySet().iterator();
      while (i.hasNext()) {
        String attributeCui = (String) i.next();
        if (!attributeCui.endsWith(OTHER) && !attributeCui.endsWith(UNIT)) {
        
          if (attributeCui.equals(ArtsConstants.ATTRIBUTE_BUFFER_TYPE)) {
            repSample.setBufferTypeCui((String)attributeValues.get(attributeCui));
            repSample.setBufferTypeOther((String)attributeValues.get(attributeCui + OTHER));
          }

          else if (attributeCui.equals(ArtsConstants.ATTRIBUTE_CELLS_PER_ML)) { 
            repSample.setCellsPerMl((BigDecimal)attributeValues.get(attributeCui));
          }

          else if (attributeCui.equals(ArtsConstants.ATTRIBUTE_COMMENT)) { 
            repSample.setAsmNote((String)attributeValues.get(attributeCui));
          }

          else if (attributeCui.equals(ArtsConstants.ATTRIBUTE_CONCENTRATION)) { 
            repSample.setConcentration((BigDecimal)attributeValues.get(attributeCui));
          }

          else if (attributeCui.equals(ArtsConstants.ATTRIBUTE_DATE_OF_COLLECTION)) {
            repSample.setCollectionDateTime((VariablePrecisionDateTime)attributeValues.get(attributeCui));
          }

          else if (attributeCui.equals(ArtsConstants.ATTRIBUTE_DATE_OF_PRESERVATION)) { 
            repSample.setPreservationDateTime((VariablePrecisionDateTime)attributeValues.get(attributeCui));
          }

          else if (attributeCui.equals(ArtsConstants.ATTRIBUTE_FIXATIVE_TYPE)) { 
            repSample.setFixativeType((String)attributeValues.get(attributeCui));
          }

          else if (attributeCui.equals(ArtsConstants.ATTRIBUTE_FORMAT_DETAIL)) { 
            repSample.setFormatDetail((String)attributeValues.get(attributeCui));
          }

          else if (attributeCui.equals(ArtsConstants.ATTRIBUTE_GROSS_APPEARANCE)) { 
            repSample.setGrossAppearance((String)attributeValues.get(attributeCui));
          }

          else if (attributeCui.equals(ArtsConstants.ATTRIBUTE_PERCENT_VIABILITY)) { 
            repSample.setPercentViability((Integer)attributeValues.get(attributeCui));
          }

          else if (attributeCui.equals(ArtsConstants.ATTRIBUTE_PREPARED_BY)) { 
            repSample.setPreparedBy((String)attributeValues.get(attributeCui));
          }

          else if (attributeCui.equals(ArtsConstants.ATTRIBUTE_PROCEDURE)) { 
            repSample.setProcedure((String)attributeValues.get(attributeCui));
            repSample.setProcedureOther((String)attributeValues.get(attributeCui + OTHER));
          }

          else if (attributeCui.equals(ArtsConstants.ATTRIBUTE_SOURCE)) { 
            repSample.setSource((String)attributeValues.get(attributeCui));
          }
          
          else if (attributeCui.equals(ArtsConstants.ATTRIBUTE_TISSUE)) { 
            repSample.setTissue((String)attributeValues.get(attributeCui));
            repSample.setTissueOther((String)attributeValues.get(attributeCui + OTHER));
          }

          else if (attributeCui.equals(ArtsConstants.ATTRIBUTE_TOTAL_NUMBER_OF_CELLS)) {
            repSample.setTotalNumOfCells((BigDecimal)attributeValues.get(attributeCui));
            repSample.setTotalNumOfCellsExRepCui((String)attributeValues.get(attributeCui + UNIT));
          }

          else if (attributeCui.equals(ArtsConstants.ATTRIBUTE_VOLUME)) { 
            repSample.setVolume((BigDecimal)attributeValues.get(attributeCui));
            repSample.setVolumeUnitCui((String)attributeValues.get(attributeCui + UNIT));
           }

          else if (attributeCui.equals(ArtsConstants.ATTRIBUTE_WEIGHT)) { 
            repSample.setWeight((BigDecimal)attributeValues.get(attributeCui));
            repSample.setWeightUnitCui((String)attributeValues.get(attributeCui + UNIT));
          }
          
          else if (attributeCui.equals(ArtsConstants.ATTRIBUTE_YIELD)) { 
            repSample.setYield((BigDecimal)attributeValues.get(attributeCui));
          }

          else {
            throw new ApiException("Unrecognized attribute CUI encountered: " + attributeCui);
          }
        }
      }
    }
  }
  
  private void populateCommonParentKcValues(SampleData repSample, Map dataElements) {
    FormInstance registrationFormInstance = new FormInstance();
    repSample.setRegistrationFormInstance(registrationFormInstance);
    Iterator dataElementIterator = dataElements.entrySet().iterator();
    while (dataElementIterator.hasNext()) {
      Map.Entry entry = (Map.Entry)dataElementIterator.next();
      DataElement dataElement = (DataElement)entry.getValue();
      registrationFormInstance.addDataElement(dataElement);
    }
  }
  
  private void findCommonParentKcValues(Map dataElements) {
    //Populate the map with the cuis of the KC data elements in use across the parent 
    //registration form definitions.
    Iterator parents = getParentSamples().iterator();
    while (parents.hasNext()) {
      SampleData parent = (SampleData) parents.next();
      DataFormDefinition formDef = parent.getRegistrationForm();
      if (formDef != null) {
        DataFormDefinitionDataElement[] dataElementDefs = formDef.getDataDataElements();
        for (int i=0; i< dataElementDefs.length; i++) {
          DataFormDefinitionDataElement dataElementDef = dataElementDefs[i];
          dataElements.put(dataElementDef.getCui(), null);
        }
      }
    }
    
    //if there are no KC data elements in use, there is nothing to do so just return
    if (ApiFunctions.isEmpty(dataElements)) {
      return;
    }
    
    //for each KC data element, determine the common parent instance values.  Remove from the map 
    //any element for which there are conflicting non-null instance values as well as any element
    //containing a standard value
    Set dataElementsConflict = new HashSet();
    Set dataElementsWithStandardValue = new HashSet();
    parents = getParentSamples().iterator();
    while (parents.hasNext()) {
      SampleData parent = (SampleData) parents.next();
      DataFormDefinition registrationFormDefinition = parent.getRegistrationForm();
      FormInstance registrationFormInstance = parent.getRegistrationFormInstance();          
      if (registrationFormDefinition != null && registrationFormInstance != null) {
        Iterator dataElementIterator = dataElements.keySet().iterator();
        while (dataElementIterator.hasNext()) {
          String cui = (String)dataElementIterator.next();
          //if we have not already identified a conflicting value for the data element and
          //have not already identified a standard value for the data element and
          //the data element is applicable to the form instance, process it's value
          if (!dataElementsConflict.contains(cui) &&
              !dataElementsWithStandardValue.contains(cui) &&
              registrationFormDefinition.getDataDataElement(cui) != null) {
            DataElement value = registrationFormInstance.getDataElement(cui);
            determineParentValueKCDataElement(cui, value, dataElements, dataElementsConflict, dataElementsWithStandardValue);
            //SWP-812.  If the data element holds a standard value, remove the data element from the map
            if (dataElementsWithStandardValue.contains(cui)) {
              dataElementIterator.remove();
            }
            //if we found a conflicting value, remove the data element from the map
            else if (dataElementsConflict.contains(cui)) {
              dataElementIterator.remove();
            }
          }
        }        
      }
    }
    
    //remove any data elements for which there are no instance values
    Iterator dataElementIterator = dataElements.keySet().iterator();
    while (dataElementIterator.hasNext()) {
      String cui = (String)dataElementIterator.next();
      if (dataElements.get(cui) == null) {
        dataElementIterator.remove();
      }
    }
  }
  
  private void determineParentValueKCDataElement(String cui, DataElement dataElement, Map dataElements, 
                                                 Set dataElementsConflict, Set dataElementsWithStandardValue) {
    if (dataElement != null && dataElement.getDataElementValues().length > 0) {
      //if the element holds a standard value it is not to be included so don't bother
      //checking for a conflict.
      if (isDataElementValuesContainsStandardValue(dataElement)) {
        dataElementsWithStandardValue.add(cui);
      }
      else {
        DataElement previousValue = (DataElement) dataElements.get(cui);
        if (previousValue == null) {
          dataElements.put(cui, dataElement);
        }
        else if (!areDataElementsEqual(previousValue, dataElement)) {
          dataElementsConflict.add(cui);
        }
      }
    }
  }
  
  /*
   * Private method to compare two DataElement objects for equality.  Comparison is made based
   * only on values, and ignores notes, ades, etc.
   */
  private boolean areDataElementsEqual(DataElement value1, DataElement value2) {
    boolean returnValue = true;

    DataElementValue[] value1Values = value1.getDataElementValues();
    DataElementValue[] value2Values = value2.getDataElementValues();
    //if the two data elements do not hold the same number of values return false
    if (value1Values.length != value2Values.length) {
      returnValue = false;
    }
    else {
      //if the two data elements do not hold the same values return false
      for (int i = 0; i < value1Values.length; i++) {
        DataElementValue value1Value = value1Values[i];
        if (!isDataElementValueInDataElementValues(value1Value, value2Values)) {
          returnValue = false;
        }
      }
    }

    return returnValue;
  }
  
  private boolean isDataElementValueInDataElementValues(DataElementValue value, DataElementValue[] values) {
    boolean returnValue = false;
    for (int i = 0; i < values.length; i++) {
      DataElementValue candidate = values[i];
      if (ApiFunctions.safeString(value.getValue()).equals(ApiFunctions.safeString(candidate.getValue())) &&
          ApiFunctions.safeString(value.getValueOther()).equals(ApiFunctions.safeString(candidate.getValueOther()))) {
        returnValue = true;
        break;
      }
    }
    return returnValue;
  }
  
  private boolean isDataElementValuesContainsStandardValue(DataElement dataElement) {
    boolean standardValueFound = false;
    if (dataElement != null) {
      DetValueSet standardValues = 
        DetService.SINGLETON.getDataElementTaxonomy().getSystemStandardValues();
      DataElementValue[] values = dataElement.getDataElementValues();
      for (int i = 0; i < values.length && !standardValueFound; i++) {
        if (standardValues.containsValue(values[i].getValue())) {
          standardValueFound = true;
        }
      }
    }
    return standardValueFound;
  }
  
  private void determineParentValueString(String attributeCui, String value, Map attributeValues, Set attributeValuesConflict) { 
    if (!ApiFunctions.isEmpty(value)) { 
      String previousValue = (String) attributeValues.get(attributeCui);
      if (previousValue == null) {
        attributeValues.put(attributeCui, value);
      }
      else if (!previousValue.equals(value)) {
        handleConflictingValue(attributeCui, attributeValues, attributeValuesConflict);
      }
    }
  }

  private void determineParentValueOther(String attributeCui, String otherValue, Map attributeValues, Set attributeValuesConflict) {
    if (!ApiFunctions.isEmpty(otherValue)) { 
      String otherKey = attributeCui + OTHER;
      String previousValue = (String) attributeValues.get(otherKey);
      if (previousValue == null) {
        attributeValues.put(otherKey, otherValue);
      }
      else if (!previousValue.equals(otherValue)) {
        handleConflictingValue(attributeCui, attributeValues, attributeValuesConflict);
      }
    }
  }

  private void determineParentValueUnit(String attributeCui, String unitCui, Map attributeValues, Set attributeValuesConflict) {
    if (!ApiFunctions.isEmpty(unitCui)) { 
      String unitKey = attributeCui + UNIT;
      String previousValue = (String) attributeValues.get(unitKey);
      if (previousValue == null) {
        attributeValues.put(unitKey, unitCui);
      }
      else if (!previousValue.equals(unitCui)) {
        handleConflictingValue(attributeCui, attributeValues, attributeValuesConflict);
      }
    }
  }

  private void determineParentValueInteger(String attributeCui, Integer value, Map attributeValues, Set attributeValuesConflict) { 
    if (value != null) { 
      Integer previousValue = (Integer) attributeValues.get(attributeCui);
      if (previousValue == null) {
        attributeValues.put(attributeCui, value);
      }
      else if (!previousValue.equals(value)) {
        handleConflictingValue(attributeCui, attributeValues, attributeValuesConflict);
      }
    }
  }

  private void determineParentValueBigDecimal(String attributeCui, BigDecimal value, Map attributeValues, Set attributeValuesConflict) { 
    if (value != null) { 
      BigDecimal previousValue = (BigDecimal) attributeValues.get(attributeCui);
      if (previousValue == null) {
        attributeValues.put(attributeCui, value);
      }
      else if (previousValue.compareTo(value) != 0) {
        handleConflictingValue(attributeCui, attributeValues, attributeValuesConflict);
      }
    }
  }

  private void determineParentValueVpdt(String attributeCui, VariablePrecisionDateTime value, Map attributeValues, Set attributeValuesConflict) { 
    if (value != null && !value.isEmpty()) { 
      VariablePrecisionDateTime previousValue = (VariablePrecisionDateTime) attributeValues.get(attributeCui);
      if (previousValue == null) {
        attributeValues.put(attributeCui, value);
      }
      else if (!previousValue.equals(value)) {
        handleConflictingValue(attributeCui, attributeValues, attributeValuesConflict);
      }
    }
  }
  
  private void handleConflictingValue(String attributeCui, Map attributeValues, Set attributeValuesConflict)
  {
    //remove the value
    attributeValues.remove(attributeCui);
    //remove any unit that might be present
    attributeValues.remove(attributeCui + UNIT);
    //remove any other value that might be present
    attributeValues.remove(attributeCui + OTHER);
    //mark the attribute as conflicting
    attributeValuesConflict.add(attributeCui);
  }

  /**
   * Returns a list of valid child sample types for the specified parents and operation.
   * The parents must have already been added to this instance before calling this method.
   * 
   * @return  The List of possible child sample types.  Each entry in the list is a sample type CUI.
   */
  public List findValidChildSampleTypes() {
    List sampleTypes = new ArrayList();
    String operationCui = getDto().getOperationCui();
    List parents = getParentSamples();
    
    // If there are no parents, return an empty list.
    if (ApiFunctions.isEmpty(parents)) {
      return sampleTypes;
    }
    
    // If we can't find the DerivativeOperation information for the operation, return 
    // an empty list.
    DerivativeOperation derivOp = SystemConfiguration.getDerivativeOperation(operationCui);
    if (derivOp == null) {
      return sampleTypes;
    }
    
    // Iterate over all parent samples and save the unique set of consents and sample types. 
    Set consentIds = new HashSet();
    Set parentSampleTypes = new HashSet();
    SampleData parent = null;
    for (int i = 0; i < parents.size(); i++) {
      parent = (SampleData) parents.get(i);
      consentIds.add(parent.getConsentId());
      parentSampleTypes.add(parent.getSampleTypeCui());
    }

    // Make sure that there is a common consent id across all the parents.  If there isn't one,
    // return an empty list.
    if (consentIds.size() != 1) {
      return sampleTypes;
    }

    // Get the lists of child CUIs for the specified parents and for the specified operation. 
    List childCuisForOperation = 
      derivOp.getChildSampleTypeCuisForParentTypeCuis(new ArrayList(parentSampleTypes));

    SampleTypeConfiguration sampleTypeConfiguration = parent.getSampleTypeConfiguration();
    List childCuisForPolicy = sampleTypeConfiguration.getSupportedSampleTypes();

    // Finally, form the union of the two lists and return it.
    Iterator childCuisForPolicyIterator = childCuisForPolicy.iterator();
    while (childCuisForPolicyIterator.hasNext()) {
      String sampleTypeCui = (String) childCuisForPolicyIterator.next();
      if (childCuisForOperation.contains(sampleTypeCui)) {
        sampleTypes.add(sampleTypeCui);
      }
    }
    return sampleTypes;
  }

  private String sampleListToString(Collection collection) {
    if ((collection == null) || collection.isEmpty()) {
      return ApiFunctions.EMPTY_STRING;
    }
    else {
      StringBuffer buf = new StringBuffer();
      boolean first = true;
      Iterator i = collection.iterator();
      while (i.hasNext()) {
        if (first) {
          first = false;
        }
        else {
          buf.append(", ");
        }
        buf.append((String) i.next());
      }
      return buf.toString();
    }
  }

  private DerivationDto getDto() {
    return _dto;
  }

  private void setDto(DerivationDto dto) {
    _dto = dto;
  }

}
