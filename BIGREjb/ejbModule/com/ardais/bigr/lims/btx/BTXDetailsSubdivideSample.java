package com.ardais.bigr.lims.btx;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BTXDetails;

/**
 * Represent the details of a sample subdivision get sample business transaction.
 * 
 * <p>The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setInputId(String) Input id}: The sample or slide id of the sample being
 * divided.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setParentId(String) parentId}: The id of the parent sample.</li>
 * <li>{@link #setConsentId(String) consentId}: The case id associated with the sample.</li>
 * <li>{@link #setAsmPosition(String) asmPosition}: The asm position of the sample.</li>
 * <li>{@link #setDiMinThicknessPfinCid(String) diMinThicknessPfinCid}: The DI MIN thickness of the
 * paraffin sample expressed as a Cid.</li>
 * <li>{@link #setDiMaxThicknessPfinCid(String) diMaxThicknessPfinCid}: The DI MAX thickness of the
 * paraffin sample expressed as a Cid.</li>
 * <li>{@link #setDiWidthAcrossPfinCid(String) diWidthAcrossPfinCid}: The DI width across the
 * paraffin sample expressed as a Cid.</li>
 * <li>{@link #setHistoMinThicknessPfinCid(String) histoMinThicknessPfinCid}: The histo MIN
 * thickness of the paraffin sample expressed as a Cid.</li>
 * <li>{@link #setHistoMaxThicknessPfinCid(String) histoMaxThicknessPfinCid}: The histo MAX
 * thickness of the paraffin sample expressed as a Cid.</li>
 * <li>{@link #setHistoWidthAcrossPfinCid(String) histoWidthAcrossPfinCid}: The histo width across
 * the paraffin sample expressed as a Cid.</li>
 * <li>{@link #setParaffinFeatureCid(String) paraffinFeatureCid}: The paraffin feature expressed as
 * a Cid.</li>
 * <li>{@link #setOtherParaffinFeature(String) otherParaffinFeature}: The other paraffin feature
 * text.</li>
 * <li>{@link #setHistoNotes(String) histoNotes}: The histo notes.</li>
 * </ul>
 * 
 * <h4>Optional output fields</h4>
 * <ul>
 * <li>{@link #setWarningInfo(Map) warningInfo}: The sample has some warnings that you should be
 * made aware of.</li>
 * </ul>
 */
public class BTXDetailsSubdivideSample extends BTXDetails implements Serializable {

  private static final long serialVersionUID = 7961490968768675309L;

  private String _inputId;

  private String _parentId;
  private String _consentId;
  private String _asmPosition;
  private String _diMinThicknessPfinCid;
  private String _diMaxThicknessPfinCid;
  private String _diWidthAcrossPfinCid;
  private String _histoMinThicknessPfinCid;
  private String _histoMaxThicknessPfinCid;
  private String _histoWidthAcrossPfinCid;
  private String _paraffinFeatureCid;
  private String _otherParaffinFeature;
  private String _histoNotes;

  private Map _warningInfo;

  /** 
   * Default constructor.
   */
  public BTXDetailsSubdivideSample() {
    super();
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_SUBDIVIDE_SAMPLE;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public java.util.Set getDirectlyInvolvedObjects() {
    Set set = new HashSet();

    if (_parentId != null) {
      set.add(_parentId);
    }

    return set;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    String msg = "BTXDetailsSubdivideSample.doGetDetailsAsHTML() method called in error!";
    return msg;
  }

  /**
   * Returns the inputId.
   * 
   * @return String
   */
  public String getInputId() {
    return _inputId;
  }

  /**
   * Sets the inputId.
   * 
   * @param inputId The inputId to set (could be a sample or slide id).
   */
  public void setInputId(String inputId) {
    _inputId = inputId;
  }

  /**
   * Returns the parentIdList.
   * 
   * @return String
   */
  public String getParentId() {
    return _parentId;
  }

  /**
   * Sets the parentId.
   * 
   * @param parentId The parentId to set.
   */
  public void setParentId(String parentId) {
    _parentId = ApiFunctions.safeString(parentId);
  }

  /**
   * Returns the consentIdList.
   * 
   * @return String
   */
  public String getConsentId() {
    return _consentId;
  }

  /**
   * Sets the consentId.
   * 
   * @param consentId The consentId to set.
   */
  public void setConsentId(String consentId) {
    _consentId = ApiFunctions.safeString(consentId);
  }

  /**
   * Returns the asmPosition.
   * 
   * @return String
   */
  public String getAsmPosition() {
    return _asmPosition;
  }

  /**
   * Sets the asmPosition.
   * 
   * @param asmPosition The asmPosition to set
   */
  public void setAsmPosition(String asmPosition) {
    _asmPosition = ApiFunctions.safeString(asmPosition);
  }

  /**
   * Returns the diMinThicknessPfinCid.
   * 
   * @return String
   */
  public String getDiMinThicknessPfinCid() {
    return _diMinThicknessPfinCid;
  }

  /**
   * Sets the diMinThicknessPfinCid.
   * 
   * @param diMinThicknessPfinCid The diMinThicknessPfinCid to set.
   */
  public void setDiMinThicknessPfinCid(String diMinThicknessPfinCid) {
    _diMinThicknessPfinCid = ApiFunctions.safeString(diMinThicknessPfinCid);
  }

  /**
   * Returns the diMaxThicknessPfinCid.
   * 
   * @return String
   */
  public String getDiMaxThicknessPfinCid() {
    return _diMaxThicknessPfinCid;
  }

  /**
   * Sets the diMaxThicknessPfinCid.
   * 
   * @param diMaxThicknessPfinCid The diMaxThicknessPfinCid to set.
   */
  public void setDiMaxThicknessPfinCid(String diMaxThicknessPfinCid) {
    _diMaxThicknessPfinCid = ApiFunctions.safeString(diMaxThicknessPfinCid);
  }

  /**
   * Returns the diWidthAcrossPfinCid.
   * 
   * @return String
   */
  public String getDiWidthAcrossPfinCid() {
    return _diWidthAcrossPfinCid;
  }

  /**
   * Sets the diWidthAcrossPfinCid.
   * 
   * @param diWidthAcrossPfinCid The diWidthAcrossPfinCid to set.
   */
  public void setDiWidthAcrossPfinCid(String diWidthAcrossPfinCid) {
    _diWidthAcrossPfinCid = ApiFunctions.safeString(diWidthAcrossPfinCid);
  }

  /**
   * Returns the histoMinThicknessPfinCid.
   * 
   * @return String
   */
  public String getHistoMinThicknessPfinCid() {
    return _histoMinThicknessPfinCid;
  }

  /**
   * Sets the histoMinThicknessPfinCid.
   * 
   * @param histoMinThicknessPfinCid The histoMinThicknessPfinCid to set.
   */
  public void setHistoMinThicknessPfinCid(String histoMinThicknessPfinCid) {
    _histoMinThicknessPfinCid = ApiFunctions.safeString(histoMinThicknessPfinCid);
  }

  /**
   * Returns the histoMaxThicknessPfinCid.
   * 
   * @return String
   */
  public String getHistoMaxThicknessPfinCid() {
    return _histoMaxThicknessPfinCid;
  }

  /**
   * Sets the histoMaxThicknessPfinCid.
   * 
   * @param histoMaxThicknessPfinCid The histoMaxThicknessPfinCid to set.
   */
  public void setHistoMaxThicknessPfinCid(String histoMaxThicknessPfinCid) {
    _histoMaxThicknessPfinCid = ApiFunctions.safeString(histoMaxThicknessPfinCid);
  }

  /**
   * Returns the histoWidthAcrossPfinCid.
   * 
   * @return String
   */
  public String getHistoWidthAcrossPfinCid() {
    return _histoWidthAcrossPfinCid;
  }

  /**
   * Sets the histoWidthAcrossPfinCid.
   * 
   * @param histoWidthAcrossPfinCid The histoWidthAcrossPfinCid to set.
   */
  public void setHistoWidthAcrossPfinCid(String histoWidthAcrossPfinCid) {
    _histoWidthAcrossPfinCid = ApiFunctions.safeString(histoWidthAcrossPfinCid);
  }

  /**
   * Returns the paraffinFeatureCid.
   * 
   * @return String
   */
  public String getParaffinFeatureCid() {
    return _paraffinFeatureCid;
  }

  /**
   * Sets the paraffinFeatureCid.
   * 
   * @param paraffinFeatureCid The paraffinFeatureCid to set.
   */
  public void setParaffinFeatureCid(String paraffinFeatureCid) {
    _paraffinFeatureCid = ApiFunctions.safeString(paraffinFeatureCid);
  }

  /**
   * Returns the otherParaffinFeature.
   * 
   * @return String
   */
  public String getOtherParaffinFeature() {
    return _otherParaffinFeature;
  }

  /**
   * Sets the otherParaffinFeature.
   * 
   * @param otherParaffinFeature The otherParaffinFeature to set.
   */
  public void setOtherParaffinFeature(String otherParaffinFeature) {
    _otherParaffinFeature = ApiFunctions.safeString(otherParaffinFeature);
  }

  /**
   * Returns the histoNotes.
   * 
   * @return String
   */
  public String getHistoNotes() {
    return _histoNotes;
  }

  /**
   * Sets the histoNotes.
   * 
   * @param histoNotes The histoNotes to set.
   */
  public void setHistoNotes(String histoNotes) {
    _histoNotes = ApiFunctions.safeString(histoNotes);
  }

  /**
   * Returns the warningInfo map.
   * 
   * @returns Map
   */
  public Map getWarningInfo() {
    return _warningInfo;
  }

  /**
   * Sets the warningInfo map.
   * 
   * @param warningInfo The warningInfo map to set.
   */
  public void setWarningInfo(Map warningInfo) {
    _warningInfo = warningInfo;
  }
}
