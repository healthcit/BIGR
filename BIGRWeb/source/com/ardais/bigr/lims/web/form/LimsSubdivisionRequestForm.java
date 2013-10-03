package com.ardais.bigr.lims.web.form;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.lims.btx.BTXDetailsSubdivideSample;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;
import com.gulfstreambio.gboss.GbossFactory;

/**
 * This represents the web form that is used to request that a sample be
 * subdivided.
 */
public class LimsSubdivisionRequestForm extends BigrActionForm {

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
  private String _numberOfChildren;
  private String _paraffinFeatureCid;
  private String _otherParaffinFeature;
  private String _histoNotes;

  private Map _warningInfo;

  private static final String SUBDIVIDE_GET_SAMPLE = "/lims/subdivideGetSample";
  private static final String SUBDIVIDE_EDIT = "/lims/subdivideEdit";

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(BigrActionMapping, HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(BigrActionMapping, HttpServletRequest)
   */
  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {

    ActionErrors errors = new ActionErrors();

    // Check if action is SUBDIVIDE_EDIT.
    if (mapping.getPath().equals(SUBDIVIDE_EDIT)) {
      // For this action, the NumberOfChildren property is required.  The only thing we need
      // to check here is that it is present and can be converted to an int (so that we can
      // set it into the corresponding property on the BTXDetails object).  The rest of the
      // validation for this action is done in the transaction performer class.

      String numOfChildren = getNumberOfChildren();

      if (ApiFunctions.isEmpty(numOfChildren)) {
        errors.add("submitError", new ActionError("iltds.error.subdivide.noChildNumber"));
      }
      else if (!FormLogic.validNumbers(numOfChildren)) {
        errors.add("submitError", new ActionError("iltds.error.subdivide.childNumberNotANumber"));
      }
    }

    return errors;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.PopulatableFromBtxDetails#populateFromBtxDetails(BTXDetails)
   */
  public void populateFromBtxDetails(BTXDetails btxDetails) {
    super.populateFromBtxDetails(btxDetails);

    //MR6249 - default histo min/max thickness and width to no data
    if (btxDetails instanceof BTXDetailsSubdivideSample) {
      if (ApiFunctions.isEmpty(getHistoMaxThicknessPfinCid())) {
        setHistoMaxThicknessPfinCid(ArtsConstants.PARAFFIN_DIMENSION_NODATA);
      }
      if (ApiFunctions.isEmpty(getHistoMinThicknessPfinCid())) {
        setHistoMinThicknessPfinCid(ArtsConstants.PARAFFIN_DIMENSION_NODATA);
      }
      if (ApiFunctions.isEmpty(getHistoWidthAcrossPfinCid())) {
        setHistoWidthAcrossPfinCid(ArtsConstants.PARAFFIN_DIMENSION_NODATA);
      }
    }
  }

  /**
   * Return the legal value set for paraffin dimensions.
   * 
   * @return LegalValueSet
   */
  public LegalValueSet getParaffinDimensionsSet() {
    return BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_PARAFFIN_DIMENSIONS);
  }

  /**
   * Returns the diMinThicknessPfin.
   * 
   * @return String
   */
  public String getDiMinThicknessPfin() {
    return (ApiFunctions.isEmpty(_diMinThicknessPfinCid) ? "" : GbossFactory.getInstance().getDescription(_diMinThicknessPfinCid));
  }

  /**
   * Returns the diMaxThicknessPfin.
   * 
   * @return String
   */
  public String getDiMaxThicknessPfin() {
    return (ApiFunctions.isEmpty(_diMaxThicknessPfinCid) ? "" : GbossFactory.getInstance().getDescription(_diMaxThicknessPfinCid));
  }

  /**
   * Returns the diWidthAcrossPfin.
   * 
   * @return String
   */
  public String getDiWidthAcrossPfin() {
    return (ApiFunctions.isEmpty(_diWidthAcrossPfinCid) ? "" : GbossFactory.getInstance().getDescription(_diWidthAcrossPfinCid));
  }

  /**
   * Returns the histoMinThicknessPfin.
   * 
   * @return String
   */
  public String getHistoMinThicknessPfin() {
    return (ApiFunctions.isEmpty(_histoMinThicknessPfinCid) ? "" : GbossFactory.getInstance().getDescription(_histoMinThicknessPfinCid));
  }

  /**
   * Returns the histoMaxThicknessPfinCid.
   * 
   * @return String
   */
  public String getHistoMaxThicknessPfin() {
    return (ApiFunctions.isEmpty(_histoMaxThicknessPfinCid) ? "" : GbossFactory.getInstance().getDescription(_histoMaxThicknessPfinCid));
  }

  /**
   * Returns the histoWidthAcrossPfinCid.
   * 
   * @return String
   */
  public String getHistoWidthAcrossPfin() {
    return (ApiFunctions.isEmpty(_histoWidthAcrossPfinCid) ? "" : GbossFactory.getInstance().getDescription(_histoWidthAcrossPfinCid));
  }

  /**
   * Returns the paraffinFeatureCid.
   * 
   * @return String
   */
  public String getParaffinFeature() {
    return (ApiFunctions.isEmpty(_paraffinFeatureCid) ? "" : GbossFactory.getInstance().getDescription(_paraffinFeatureCid));
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
   * @param sampleId The inputId to set (could be a sample or slide id).
   */
  public void setInputId(String inputId) {
    _inputId = ApiFunctions.safeTrim(inputId);
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
    _parentId = ApiFunctions.safeTrim(parentId);
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
    _consentId = ApiFunctions.safeTrim(consentId);
  }

  /**
   * Returns the asmPosition.
   * 
   * @return String
   */
  public String getAsmPosition() {
    return ApiFunctions.safeString(_asmPosition);
  }

  /**
   * Sets the asmPosition.
   * 
   * @param asmPosition The asmPosition to set
   */
  public void setAsmPosition(String asmPosition) {
    _asmPosition = ApiFunctions.safeTrim(asmPosition);
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
    _diMinThicknessPfinCid = ApiFunctions.safeTrim(diMinThicknessPfinCid);
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
    _diMaxThicknessPfinCid = ApiFunctions.safeTrim(diMaxThicknessPfinCid);
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
    _diWidthAcrossPfinCid = ApiFunctions.safeTrim(diWidthAcrossPfinCid);
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
    _histoMinThicknessPfinCid = ApiFunctions.safeTrim(histoMinThicknessPfinCid);
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
    _histoMaxThicknessPfinCid = ApiFunctions.safeTrim(histoMaxThicknessPfinCid);
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
    _histoWidthAcrossPfinCid = ApiFunctions.safeTrim(histoWidthAcrossPfinCid);
  }

  /**
   * Returns the number of children.
   * 
   * @return the number of children.
   */
  public String getNumberOfChildren() {
    return _numberOfChildren;
  }

  /**
   * Sets the number of children.
   * 
   * @param numberOfChildren The number of children to set
   */
  public void setNumberOfChildren(String numberOfChildren) {
    _numberOfChildren = ApiFunctions.safeTrim(numberOfChildren);
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
    _paraffinFeatureCid = ApiFunctions.safeTrim(paraffinFeatureCid);
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
    _otherParaffinFeature = ApiFunctions.safeTrim(otherParaffinFeature);
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
    _histoNotes = ApiFunctions.safeTrim(histoNotes);
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
