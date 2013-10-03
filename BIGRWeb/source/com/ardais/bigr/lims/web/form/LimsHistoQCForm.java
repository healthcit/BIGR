package com.ardais.bigr.lims.web.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.DbUtils;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * This represents the web form that is used to request that a sample be
 * subdivided.
 */
public class LimsHistoQCForm extends BigrActionForm {

  private ArrayList _sampleDataList = new ArrayList();

  private String _inputId;

  private String _allHistoReembedReason;
  private String _allOtherHistoReembedReason;
  private String _allHistoSubdividable;

  private String[] _sampleIdList;
  private String[] _consentIdList;
  private String[] _asmPositionList;
  private String[] _slidesExistList;
  private String[] _diMinThicknessList;
  private String[] _diMaxThicknessList;
  private String[] _diWidthAcrossList;
  private String[] _histoMinThicknessList;
  private String[] _histoMaxThicknessList;
  private String[] _histoWidthAcrossList;
  private String[] _histoSubdividableList;
  private String[] _paraffinFeatureCidList;
  private String[] _otherParaffinFeatureList;
  private String[] _histoNotesList;
  private String[] _histoReembedReasonList;
  private String[] _otherHistoReembedReasonList;

  private String[] _parentIdList;
  private String[] _sampleTypeCidList;
  private String[] _salesStatusList;
  private String[] _pulledList;
  private String[] _subdividedList;

  public static final String HISTO_QC_GET_SAMPLE = "/lims/limsHistoQCGetSample";
  public static final String HISTO_QC_CONFIRM = "/lims/limsHistoQCConfirm";

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(BigrActionMapping, HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(BigrActionMapping, HttpServletRequest)
   */
  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {

    // Note: only primitive validations occur here. Any business rule or
    // business validation should occur in session bean method.

    ActionErrors errors = new ActionErrors();

    // Check if action is HISTO_QC_GET_SAMPLE.
    if (mapping.getPath().equals(HISTO_QC_GET_SAMPLE)) {
      // Check if getting another sample row.
      if (!ApiFunctions.isEmpty(_inputId)) {
        // Check if the inputId is a valid slide or sample id.
        String validatedId = ValidateIds.validateId(_inputId, ValidateIds.TYPESET_SAMPLE_AND_SLIDE, true);
        if (!ApiFunctions.isEmpty(validatedId)) {
          _inputId = validatedId;
        }
        else {
          errors.add(
            "inputError",
            new ActionError("lims.error.histoQc.invalidSlideOrSampleFormat", _inputId));
          _inputId = null;
        }
      }
      else {
        // The input id is empty.
        errors.add("inputError", new ActionError("lims.error.histoQc.invalidInput"));
      }
    }
    
    if (!mapping.getPath().equals(HISTO_QC_CONFIRM)) {

      // Copy form into sample data list.
      if (!ApiFunctions.isEmpty(_sampleIdList)) {
        for (int i = 0; i < _sampleIdList.length; i++) {

          SampleData sampleData = new SampleData();

          sampleData.setSampleId(_sampleIdList[i]);
          sampleData.setConsentId(_consentIdList[i]);
          sampleData.setAsmPosition(_asmPositionList[i]);
          sampleData.setSlidesExist(_slidesExistList[i]);
          sampleData.setDiMinThicknessPfinCid(_diMinThicknessList[i]);
          sampleData.setDiMaxThicknessPfinCid(_diMaxThicknessList[i]);
          sampleData.setDiWidthAcrossPfinCid(_diWidthAcrossList[i]);
          sampleData.setHistoMinThicknessPfinCid(_histoMinThicknessList[i]);
          sampleData.setHistoMaxThicknessPfinCid(_histoMaxThicknessList[i]);
          sampleData.setHistoWidthAcrossPfinCid(_histoWidthAcrossList[i]);
          sampleData.setHistoSubdividable(_histoSubdividableList[i]);
          sampleData.setParaffinFeatureCid(_paraffinFeatureCidList[i]);
          sampleData.setOtherParaffinFeature(_otherParaffinFeatureList[i]);
          sampleData.setHistoNotes(_histoNotesList[i]);

          sampleData.setHistoReembedReasonCid(_histoReembedReasonList[i]);
          if (_histoReembedReasonList[i].equals(FormLogic.OTHER_HISTO_REEMBED_REASON)) {
            sampleData.setOtherHistoReembedReason(_otherHistoReembedReasonList[i]);
          }
          else {
            sampleData.setOtherHistoReembedReason("");
          }

          sampleData.setParentId(_parentIdList[i]);
          sampleData.setSampleTypeCui(_sampleTypeCidList[i]);
          sampleData.setSalesStatus(_salesStatusList[i]);
          sampleData.setPulled(DbUtils.convertStringToBoolean(_pulledList[i]));

          sampleData.setSubdivided(DbUtils.convertStringToBoolean(_subdividedList[i]));

          _sampleDataList.add(sampleData);
        }
      }
    }

    return errors;
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
   * Return the legal value set for histo reembed reasons.
   * 
   * @return LegalValueSet
   */
  public LegalValueSet getHistoReembedReasonSet() {
    return BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_HISTO_REEMBED_REASON);
  }

  /**
   * Returns the sampleDataList.
   * 
   * @return List
   */
  public List getSampleDataList() {
    return _sampleDataList;
  }

  /**
   * Sets the sampleDataList.
   * 
   * @param sampleDataList The sampleDataList to set.
   */
  public void setSampleDataList(List sampleDataList) {
    _sampleDataList = (ArrayList) sampleDataList;
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
    _inputId = inputId;
  }

  /**
   * Returns the allHistoReembedReason.
   * 
   * @return String
   */
  public String getAllHistoReembedReason() {
    return _allHistoReembedReason;
  }

  /**
   * Sets the allHistoReembedReason.
   * 
   * @param allHistoReembedReason The allHistoReembedReason to set.
   */
  public void setAllHistoReembedReason(String allHistoReembedReason) {
    _allHistoReembedReason = allHistoReembedReason;
  }

  /**
   * Returns the allOtherHistoReembedReason.
   * 
   * @return String
   */
  public String getAllOtherHistoReembedReason() {
    return _allOtherHistoReembedReason;
  }

  /**
   * Sets the allOtherHistoReembedReason.
   * 
   * @param allOtherHistoReembedReason The allOtherHistoReembedReason to set.
   */
  public void setAllOtherHistoReembedReason(String allOtherHistoReembedReason) {
    _allOtherHistoReembedReason = allOtherHistoReembedReason;
  }

  /**
   * Returns the allHistoSubdividable.
   * 
   * @return String
   */
  public String getAllHistoSubdividable() {
    return _allHistoSubdividable;
  }

  /**
   * Sets the allHistoSubdividable.
   * 
   * @param allHistoSubdividable The allHistoSubdividable to set.
   */
  public void setAllHistoSubdividable(String allHistoSubdividable) {
    _allHistoSubdividable = allHistoSubdividable;
  }

  /**
   * Returns the sampleIdList.
   * 
   * @return String[]
   */
  public String[] getSampleIdList() {
    return _sampleIdList;
  }

  /**
   * Sets the sampleIdList.
   * 
   * @param sampleIdList The sampleIdList to set.
   */
  public void setSampleIdList(String[] sampleIdList) {
    _sampleIdList = sampleIdList;
  }

  /**
   * Returns the consentIdList.
   * 
   * @return String[]
   */
  public String[] getConsentIdList() {
    return _consentIdList;
  }

  /**
   * Sets the consentIdList.
   * 
   * @param consentIdList The consentIdList to set.
   */
  public void setConsentIdList(String[] consentIdList) {
    _consentIdList = consentIdList;
  }

  /**
   * Returns the asmPositionList.
   * 
   * @return String[]
   */
  public String[] getAsmPositionList() {
    return _asmPositionList;
  }

  /**
   * Sets the asmPositionList.
   * 
   * @param asmPositionList The asmPositionList to set.
   */
  public void setAsmPositionList(String[] asmPositionList) {
    _asmPositionList = asmPositionList;
  }

  /**
   * Returns the slidesExistList.
   * 
   * @return String[]
   */
  public String[] getSlidesExistList() {
    return _slidesExistList;
  }

  /**
   * Sets the slidesExistList.
   * 
   * @param slidesExistList The slidesExistList to set.
   */
  public void setSlidesExistList(String[] slidesExistList) {
    _slidesExistList = slidesExistList;
  }

  /**
   * Returns the diMinThicknessList.
   * 
   * @return String[]
   */
  public String[] getDiMinThicknessList() {
    return _diMinThicknessList;
  }

  /**
   * Sets the diMinThicknessList.
   * 
   * @param diMinThicknessList The diMinThicknessList to set.
   */
  public void setDiMinThicknessList(String[] diMinThicknessList) {
    _diMinThicknessList = diMinThicknessList;
  }

  /**
   * Returns the diMaxThicknessList.
   * 
   * @return String[]
   */
  public String[] getDiMaxThicknessList() {
    return _diMaxThicknessList;
  }

  /**
   * Sets the diMaxThicknessList.
   * 
   * @param diMaxThicknessList The diMaxThicknessList to set.
   */
  public void setDiMaxThicknessList(String[] diMaxThicknessList) {
    _diMaxThicknessList = diMaxThicknessList;
  }

  /**
   * Returns the diWidthAcrossList.
   * 
   * @return String[]
   */
  public String[] getDiWidthAcrossList() {
    return _diWidthAcrossList;
  }

  /**
   * Sets the diWidthAcrossList.
   * 
   * @param diWidthAcrossList The diWidthAcrossList to set.
   */
  public void setDiWidthAcrossList(String[] diWidthAcrossList) {
    _diWidthAcrossList = diWidthAcrossList;
  }

  /**
   * Returns the histoMinThicknessList.
   * 
   * @return String[]
   */
  public String[] getHistoMinThicknessList() {
    return _histoMinThicknessList;
  }

  /**
   * Sets the histoMinThicknessList.
   * 
   * @param histoMinThicknessList The histoMinThicknessList to set.
   */
  public void setHistoMinThicknessList(String[] histoMinThicknessList) {
    _histoMinThicknessList = histoMinThicknessList;
  }

  /**
   * Returns the histoMaxThicknessList.
   * 
   * @return String[]
   */
  public String[] getHistoMaxThicknessList() {
    return _histoMaxThicknessList;
  }

  /**
   * Sets the histoMaxThicknessList.
   * 
   * @param histoMaxThicknessList The histoMaxThicknessList to set.
   */
  public void setHistoMaxThicknessList(String[] histoMaxThicknessList) {
    _histoMaxThicknessList = histoMaxThicknessList;
  }

  /**
   * Returns the histoWidthAcrossList.
   * 
   * @return String[]
   */
  public String[] getHistoWidthAcrossList() {
    return _histoWidthAcrossList;
  }

  /**
   * Sets the histoWidthAcrossList.
   * 
   * @param histoWidthAcrossList The histoWidthAcrossList to set.
   */
  public void setHistoWidthAcrossList(String[] histoWidthAcrossList) {
    _histoWidthAcrossList = histoWidthAcrossList;
  }

  /**
   * Returns the histoSubdividableList.
   * 
   * @return String[]
   */
  public String[] getHistoSubdividableList() {
    return _histoSubdividableList;
  }

  /**
   * Sets the histoSubdividableList.
   * 
   * @param histoSubdividableList The histoSubdividableList to set.
   */
  public void setHistoSubdividableList(String[] histoSubdividableList) {
    _histoSubdividableList = histoSubdividableList;
  }

  /**
   * Returns the paraffinFeatureCidList.
   * 
   * @return String[]
   */
  public String[] getParaffinFeatureCidList() {
    return _paraffinFeatureCidList;
  }

  /**
   * Sets the paraffinFeatureCidList.
   * 
   * @param paraffinFeatureCidList The paraffinFeatureCidList to set.
   */
  public void setParaffinFeatureCidList(String[] paraffinFeatureCidList) {
    _paraffinFeatureCidList = paraffinFeatureCidList;
  }

  /**
   * Returns the otherPparaffinFeatureList.
   * 
   * @return String[]
   */
  public String[] getOtherParaffinFeatureList() {
    return _otherParaffinFeatureList;
  }

  /**
   * Sets the otherParaffinFeatureList.
   * 
   * @param otherParaffinFeatureList The otherParaffinFeatureList to set.
   */
  public void setOtherParaffinFeatureList(String[] otherParaffinFeatureList) {
    _otherParaffinFeatureList = otherParaffinFeatureList;
  }

  /**
   * Returns the histoNotesList.
   * 
   * @return String[]
   */
  public String[] getHistoNotesList() {
    return _histoNotesList;
  }

  /**
   * Sets the histoNotesList.
   * 
   * @param histoNotesList The histoNotesList to set.
   */
  public void setHistoNotesList(String[] histoNotesList) {
    _histoNotesList = histoNotesList;
  }

  /**
   * Returns the histoReembedReasonList.
   * 
   * @return String[]
   */
  public String[] getHistoReembedReasonList() {
    return _histoReembedReasonList;
  }

  /**
   * Sets the histoReembedReasonList.
   * 
   * @param histoReembedReasonList The histoReembedReasonList to set.
   */
  public void setHistoReembedReasonList(String[] histoReembedReasonList) {
    _histoReembedReasonList = histoReembedReasonList;
  }

  /**
   * Returns the otherHistoReembedReasonList.
   * 
   * @return String[]
   */
  public String[] getOtherHistoReembedReasonList() {
    return _otherHistoReembedReasonList;
  }

  /**
   * Sets the otherHistoReembedReasonList.
   * 
   * @param otherHistoReembedReasonList The otherHistoReembedReasonList to set.
   */
  public void setOtherHistoReembedReasonList(String[] otherHistoReembedReasonList) {
    _otherHistoReembedReasonList = otherHistoReembedReasonList;
  }

  /**
   * Returns the parentIdList.
   * 
   * @return String[]
   */
  public String[] getParentIdList() {
    return _parentIdList;
  }

  /**
   * Sets the parentIdList.
   * 
   * @param parentIdList The parentIdList to set.
   */
  public void setParentIdList(String[] parentIdList) {
    _parentIdList = parentIdList;
  }

  /**
   * Returns an array of sample type CIDs.
   * 
   * @return String[]
   */
  public String[] getSampleTypeCidList() {
    return _sampleTypeCidList;
  }

  /**
   * Sets the sample type CID array.
   * 
   * @param sampleTypeCidList The sample type CID list to set.
   */
  public void setSampleTypeCidList(String[] sampleTypeCidList) {
    _sampleTypeCidList = sampleTypeCidList;
  }

  /**
   * Returns the salesStatusList.
   * 
   * @return String[]
   */
  public String[] getSalesStatusList() {
    return _salesStatusList;
  }

  /**
   * Sets the salesStatusList.
   * 
   * @param salesStatusList The salesStatusList to set.
   */
  public void setSalesStatusList(String[] salesStatusList) {
    _salesStatusList = salesStatusList;
  }

  /**
   * Returns the pulledList.
   * 
   * @return String[]
   */
  public String[] getPulledList() {
    return _pulledList;
  }

  /**
   * Sets the pulledList.
   * 
   * @param pulledList The pulledList to set.
   */
  public void setPulledList(String[] pulledList) {
    _pulledList = pulledList;
  }

  /**
   * Returns the subdividedList.
   * 
   * @return String[]
   */
  public String[] getSubdividedList() {
    return _subdividedList;
  }

  /**
   * Sets the subdividedList.
   * 
   * @param subdividedList The subdividedList to set.
   */
  public void setSubdividedList(String[] subdividedList) {
    _subdividedList = subdividedList;
  }
}
