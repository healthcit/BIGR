package com.ardais.bigr.library.web.form;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringsEqual;
import com.ardais.bigr.query.filters.FilterStringsLike;
import com.ardais.bigr.query.generator.FilterConsentAliasId;
import com.ardais.bigr.query.generator.FilterConsentId;
import com.ardais.bigr.query.generator.FilterDonorAliasId;
import com.ardais.bigr.query.generator.FilterDonorId;
import com.ardais.bigr.query.generator.FilterRnaId;
import com.ardais.bigr.query.generator.FilterSampleAliasId;
import com.ardais.bigr.query.generator.FilterSampleId;
import com.ardais.bigr.util.IdValidator;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * @author JThompson
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class QueryIdForm extends BigrActionForm {

  private String[] _caseIds;
  private String[] _donorIds;
  private String[] _itemIds;
  private String[] _aliasPatterns;
  private boolean _findDonors;
  private boolean _findConsents;
  private boolean _findSamples;

  public QueryIdForm() {
    reset();
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(BigrActionMapping, HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    reset();
  }

  private void reset() {
    _caseIds = null;
    _donorIds = null;
    _itemIds = null;
    _aliasPatterns = null;
    _findDonors = false;
    _findConsents = false;
    _findSamples = false;
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(BigrActionMapping, HttpServletRequest)
   */
  // javascript now populates individual id field into list fields before submit.
  public ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {

    ActionErrors errors = new ActionErrors();
    
    // Please provide an alias list to search on based on your selection criteria set.
    if ((isFindDonors() || isFindConsents() || isFindSamples()) && ApiFunctions.isEmpty(_aliasPatterns)) {
      errors.add(
        ActionErrors.GLOBAL_ERROR,
        new ActionError("library.ss.error.aliasPattern.noAliasListSpecified"));
    }
    
    // Please provide a selection criteria to search on based on the alias entered.
    if (!ApiFunctions.isEmpty(_aliasPatterns) && (!isFindDonors() && !isFindConsents() && !isFindSamples())) {
      errors.add(
        ActionErrors.GLOBAL_ERROR,
        new ActionError("library.ss.error.aliasPattern.noSelectionCriteriaSpecified"));
    }
    return errors;
  }

  /**
   * Returns the caseIds.
   *  5/18/03 added the ValidateIds to create fully formed ids, if they are not
   * @return String[]
   */
  public String[] getCaseIds() {

    if ((_caseIds != null) && (_caseIds.length > 0)) {
      // validate the ids here
      ValidateIds validId = new ValidateIds();
      String[] full_caseIdList = new String[_caseIds.length];
      for (int i = 0; i < _caseIds.length; i++) {
        full_caseIdList[i] = validId.canonicalize(_caseIds[i]);
      }

      _caseIds = full_caseIdList;
    }

    return _caseIds;
  }

  /**
   * Returns the donorIds.
   *   5/18/03 added the ValidateIds to create fully formed ids, if they are not
   * @return String[]
   */
  public String[] getDonorIds() {

    if ((_donorIds != null) && (_donorIds.length > 0)) {
      // validate the ids here
      ValidateIds validId = new ValidateIds();
      String[] full_donorIdList = new String[_donorIds.length];
      for (int i = 0; i < _donorIds.length; i++) {
        full_donorIdList[i] = validId.canonicalize(_donorIds[i]);
      }

      _donorIds = full_donorIdList;
    }

    return _donorIds;
  }

  /**
   * Returns the itemIds.
   * @return String[]
   */
  public String[] getItemIds() {
    return _itemIds;
  }
  public String[] getRnaIds() {
    return _itemIds == null ? null : IdValidator.validRnaIds(_itemIds);
  }
  public String[] getTissueIds() {
    return _itemIds == null ? null : IdValidator.validSampleIds(_itemIds);
  }

  /**
   * Sets the caseIds.
   * @param caseIds The caseIds to set
   */
  public void setCaseIds(String[] caseIds) {
    _caseIds = caseIds;
  }

  /**
   * Sets the donorIds.
   * @param donorIds The donorIds to set
   */
  public void setDonorIds(String[] donorIds) {
    _donorIds = donorIds;
  }

  /**
   * Sets the itemIds.
   * @param itemIds The itemIds to set
   */
  public void setItemIds(String[] itemIds) {
    _itemIds = itemIds;
  }

  /**
   * @return
   */
  public String[] getAliasPatterns() {
    return _aliasPatterns;
  }

  /**
   * @param strings
   */
  public void setAliasPatterns(String[] strings) {
    _aliasPatterns = strings;
  }

  /**
   * @return
   */
  public boolean isFindConsents() {
    return _findConsents;
  }

  /**
   * @return
   */
  public boolean isFindDonors() {
    return _findDonors;
  }

  /**
   * @return
   */
  public boolean isFindSamples() {
    return _findSamples;
  }

  /**
   * @param b
   */
  public void setFindConsents(boolean b) {
    _findConsents = b;
  }

  /**
   * @param b
   */
  public void setFindDonors(boolean b) {
    _findDonors = b;
  }

  /**
   * @param b
   */
  public void setFindSamples(boolean b) {
    _findSamples = b;
  }

  public Map getFilters() {
    Map results = new HashMap();
    addStringsEqualToMap(new FilterConsentId(getCaseIds()), results);
    addStringsEqualToMap(new FilterDonorId(getDonorIds()), results);
    addStringsEqualToMap(new FilterSampleId(getTissueIds()), results);
    addStringsEqualToMap(new FilterRnaId(getRnaIds()), results);
    
    if (isFindDonors()) {
      addStringsLikeToMap(new FilterDonorAliasId(getAliasPatterns()), results);
    }
    if (isFindConsents()) {
      addStringsLikeToMap(new FilterConsentAliasId(getAliasPatterns()), results);
    }
    if (isFindSamples()) {
      addStringsLikeToMap(new FilterSampleAliasId(getAliasPatterns()), results);
    }

    return results;
  }

  // helper to add FilterStringsEqual object only if the String array is not empty
  private void addStringsEqualToMap(FilterStringsEqual f, Map m) {
    String[] ids = f.getFilterValues();
    if (!ApiFunctions.isEmpty(ids)) {
      m.put(f.getKey(), f);
    }
  }

  // helper to add FilterStringsLike object only if the String array is not empty
  private void addStringsLikeToMap(FilterStringsLike f, Map m) {
    String[] patterns = f.getPatterns();
    if (!ApiFunctions.isEmpty(patterns)) {
      m.put(f.getKey(), f);
    }
  }

  /**
   * Method setFilters.
   * @param filters
   */
  public void setFilters(Map filters) {
    String caseidkey = FilterConstants.KEY_CASEID;
    String donoridkey = FilterConstants.KEY_DONORID;
    String tissueidkey = FilterConstants.KEY_TISSUEID;
    String rnaidkey = FilterConstants.KEY_RNAID;
    String donorAliasIdKey = FilterConstants.KEY_DONOR_ALIAS_ID;
    String consentAliasIdKey = FilterConstants.KEY_CONSENT_ALIAS_ID;
    String sampleAliasIdKey = FilterConstants.KEY_SAMPLE_ALIAS_ID;

    if (filters.containsKey(caseidkey)) {
      FilterConsentId f = (FilterConsentId) filters.get(caseidkey);
      setCaseIds(f.getFilterValues());
    }
    if (filters.containsKey(donoridkey)) {
      FilterDonorId f = (FilterDonorId) filters.get(donoridkey);
      setDonorIds(f.getFilterValues());
    }
    List itemIds = new ArrayList();
    if (filters.containsKey(tissueidkey)) {
      FilterSampleId f = (FilterSampleId) filters.get(tissueidkey);
      itemIds.addAll(Arrays.asList(f.getFilterValues()));
    }
    if (filters.containsKey(rnaidkey)) {
      FilterRnaId f = (FilterRnaId) filters.get(rnaidkey);
      itemIds.addAll(Arrays.asList(f.getFilterValues()));
    }
    setItemIds((String[]) itemIds.toArray(new String[0]));

    if (filters.containsKey(donorAliasIdKey)) {
      setFindDonors(true);
      if (ApiFunctions.isAllEmpty(_aliasPatterns)) {
        FilterDonorAliasId f = (FilterDonorAliasId) filters.get(donorAliasIdKey);
        setAliasPatterns(f.getPatterns());
      }
    }
    if (filters.containsKey(consentAliasIdKey)) {
      setFindConsents(true);
      if (ApiFunctions.isAllEmpty(_aliasPatterns)) {
        FilterConsentAliasId f = (FilterConsentAliasId) filters.get(consentAliasIdKey);
        setAliasPatterns(f.getPatterns());
      }
    }
    if (filters.containsKey(sampleAliasIdKey)) {
      setFindSamples(true);
      if (ApiFunctions.isAllEmpty(_aliasPatterns)) {
        FilterSampleAliasId f = (FilterSampleAliasId) filters.get(sampleAliasIdKey);
        setAliasPatterns(f.getPatterns());
      }
    }
  }
}
