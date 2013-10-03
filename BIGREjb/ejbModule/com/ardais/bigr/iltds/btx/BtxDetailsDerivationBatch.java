package com.ardais.bigr.iltds.btx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.btx.framework.BtxHistoryAttributes;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.javabeans.DerivationBatchDto;

/**
 * Holds the details of a batch of derivation operations.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 * 
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setInputDerivationBatchDto(DerivationBatchDto) inputDerivationBatchDto}: The  
 * DerivationBatchDto containing all necessary input data for all individual derivation operations
 * in the batch.
 * </li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setOutputDerivationBatchDto(DerivationBatchDto) outputDerivationBatchDto}: The 
 * DerivationBatchDto containing all output data for all individual derivation operations
 * in the batch.
 * </li>
 * <li>{@link #setChildSampleDataElements(List ) list}: A list of DataFormDefinitions (one for each 
 * derivation), each of which contains all elements utilized across the potential child sample types 
 * for that derivation.
 * <li>{@link #setChildSampleRegistrationForms(List ) list}: A list of Maps (one for each 
 * derivation), mapping a child sample type to it's registration form DataFormDefinition.
 * <li>{@link #setAllowInventoryGroupInheritance(Map ) map}: A map indicating for each
 * derivation operation if the children should be allowed to inherit inventory group
 * membership from the parents.
 * </li>
 * </ul>
 */
public class BtxDetailsDerivationBatch extends BTXDetails {

  private DerivationBatchDto _dto;
  private LegalValueSet _preparedByList;
  private List _childSampleTypeChoices;
  
  //a list of Maps (one for each derivation), mapping a child sample type to it's registration 
  //form DataFormDefinition
  private List _childSampleRegistrationForms;
  
  //map that indicates for each derivation whether children should be allowed to
  //inherit inventory group membership from their parents or not.  Children will
  //be allowed to inherit inventory group membership from their parents when it is
  //true that each parent belongs to an identical set of inventory groups.
  Map _allowInventoryGroupInheritance = new HashMap();
  
  //field to hold sample label printing information
  private Map _labelPrintingData;
  private String _childIdsStyle;

  
  public BtxDetailsDerivationBatch() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_DERIVATION_BATCH;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public Set getDirectlyInvolvedObjects() {
    return null;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    throw new ApiException("Attempt to call doGetDetailsAsHTML in BtxDetailsDerivationBatch - should not have been called since this class does not support transaction logging.");
  }


  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#describeIntoHistoryRecord(com.ardais.bigr.iltds.btx.BTXHistoryRecord)
   */
  public void describeIntoHistoryRecord(BTXHistoryRecord history) {
    throw new ApiException("Attempt to call describeIntoHistoryRecord in BtxDetailsDerivationBatch - should not have been called since this class does not support transaction logging.");
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#populateFromHistoryObject(com.ardais.bigr.btx.framework.BtxHistoryAttributes)
   */
  protected void populateFromHistoryObject(BtxHistoryAttributes attributes) {
    throw new ApiException("Attempt to call populateFromHistoryObject in BtxDetailsDerivationBatch - should not have been called since this class does not support transaction logging.");
  }

  /**
   * Returns the DTO.
   *   
   * @return  The DerivationBatchDto.
   */
  public DerivationBatchDto getDto() {
    return _dto;
  }

  /**
   * Sets the DTO.
   *   
   * @param  dto  the DerivationBatchDto
   */
  public void setDto(DerivationBatchDto dto) {
    _dto = dto;
  }
  
  public LegalValueSet getPreparedByList() {
    return _preparedByList;
  }

  public void setPreparedByList(LegalValueSet list) {
    _preparedByList = list;
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
  public Map getAllowInventoryGroupInheritance() {
    if (_allowInventoryGroupInheritance == null) {
      _allowInventoryGroupInheritance = new HashMap();
    }
    return _allowInventoryGroupInheritance;
  }

  /**
   * @param map
   */
  public void setAllowInventoryGroupInheritance(Map map) {
    _allowInventoryGroupInheritance = map;
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
  }

  /**
   * @return Returns the labelPrintingData.
   */
  public Map getLabelPrintingData() {
    return _labelPrintingData;
  }
  
  /**
   * @param labelPrintingData The labelPrintingData to set.
   */
  public void setLabelPrintingData(Map labelPrintingData) {
    _labelPrintingData = labelPrintingData;
  }
  public String getChildIdsStyle() {
    // return scanned by default.
    return (_childIdsStyle == null) ? "scanned" : _childIdsStyle;
  }
  public void setChildIdsStyle(String childIdsStyle) {
    _childIdsStyle = childIdsStyle;
  }
}
