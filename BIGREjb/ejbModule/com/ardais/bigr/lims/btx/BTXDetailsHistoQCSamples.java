package com.ardais.bigr.lims.btx;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.javabeans.SampleData;

/**
 * Represent the details of a histology qc sample business transaction.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setInputId(String) inputId}: The id of the slide or sample.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setSampleDataList(List) sampleDataList}: The sample data list.</li>
 * </ul>
 */
public class BTXDetailsHistoQCSamples extends BTXDetails implements Serializable {

  private static final long serialVersionUID = -6540709743626308928L;

  private String _inputId;
  private List _sampleDataList;

  /** 
   * Default constructor.
   */
  public BTXDetailsHistoQCSamples() {
    super();
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_HISTO_QC_SAMPLES;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public java.util.Set getDirectlyInvolvedObjects() {
    Set set = new HashSet();

    if (getSampleDataList() != null) {
      Iterator iterator = getSampleDataList().iterator();
      while (iterator.hasNext()) {
        SampleData sampleData = (SampleData) iterator.next();
        set.add(sampleData.getSampleId());
      }
    }

    return set;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    String msg = "BTXDetailsHistoQCSamples.doGetDetailsAsHTML() method called in error!";
    return msg;
  }

  /**
   * This method appends the newly retrieved sampleData to the sample data list.
   */
  public void appendSampleData(SampleData sampleData) {

    sampleData.setSampleId(ApiFunctions.safeString(sampleData.getSampleId()));
    sampleData.setConsentId(ApiFunctions.safeString(sampleData.getConsentId()));
    sampleData.setAsmPosition(ApiFunctions.safeString(sampleData.getAsmPosition()));
    sampleData.setSlidesExist(ApiFunctions.safeString(sampleData.getSlidesExist()));
    sampleData.setDiMinThicknessPfinCid(
      ApiFunctions.safeString(sampleData.getDiMinThicknessPfinCid()));
    sampleData.setDiMaxThicknessPfinCid(
      ApiFunctions.safeString(sampleData.getDiMaxThicknessPfinCid()));
    sampleData.setDiWidthAcrossPfinCid(
      ApiFunctions.safeString(sampleData.getDiWidthAcrossPfinCid()));
    sampleData.setHistoMinThicknessPfinCid(
      ApiFunctions.safeString(sampleData.getHistoMinThicknessPfinCid()));
    sampleData.setHistoMaxThicknessPfinCid(
      ApiFunctions.safeString(sampleData.getHistoMaxThicknessPfinCid()));
    sampleData.setHistoWidthAcrossPfinCid(
      ApiFunctions.safeString(sampleData.getHistoWidthAcrossPfinCid()));
    sampleData.setHistoSubdividable(ApiFunctions.safeString(sampleData.getHistoSubdividable()));
    sampleData.setParaffinFeatureCid(ApiFunctions.safeString(sampleData.getParaffinFeatureCid()));
    sampleData.setOtherParaffinFeature(
      ApiFunctions.safeString(sampleData.getOtherParaffinFeature()));
    sampleData.setHistoNotes(ApiFunctions.safeString(sampleData.getHistoNotes()));
    sampleData.setHistoReembedReasonCid(
      ApiFunctions.safeString(sampleData.getHistoReembedReasonCid()));
    sampleData.setOtherHistoReembedReason(
      ApiFunctions.safeString(sampleData.getOtherHistoReembedReason()));
    sampleData.setParentId(ApiFunctions.safeString(sampleData.getParentId()));
    sampleData.setSampleTypeCui(ApiFunctions.safeString(sampleData.getSampleTypeCui()));
    sampleData.setSalesStatus(ApiFunctions.safeString(sampleData.getSalesStatus()));

    // Don't need to set isPulled, because it is a boolean. 

    _sampleDataList.add(sampleData);
  }

  /**
   * Returns the inputId.
   * @return String
   */
  public String getInputId() {
    return _inputId;
  }

  /**
   * Sets the inputId
   * @param inputId The inputId to set (could be a sample or slide id).
   */
  public void setInputId(String inputId) {
    _inputId = inputId;
  }

  /**
   * Returns the sampleDataList.
   * @return List
   */
  public List getSampleDataList() {
    return _sampleDataList;
  }

  /**
   * Sets the sampleDataList.
   * @param sampleDataList The slideDataList to set
   */
  public void setSampleDataList(List sampleDataList) {
    _sampleDataList = sampleDataList;
  }
}
