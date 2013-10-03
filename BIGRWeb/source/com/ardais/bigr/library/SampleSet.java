package com.ardais.bigr.library;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.library.btx.BTXDetailsAddToHoldList;
import com.ardais.bigr.library.btx.BTXDetailsGetSamples;
import com.ardais.bigr.library.web.helper.SampleCountHelper;
import com.ardais.bigr.query.SampleSelectionSummary;
import com.ardais.bigr.security.SecurityInfo;

/**
 * This represents a set of samples.  It can manage itself across http requests, 
 * so it has persistent state either across sessions, or persistently in the database 
 * across logins. */

public abstract class SampleSet {

  private SecurityInfo _securityInfo;

  public SampleSet(SecurityInfo secInfo) {
    _securityInfo = secInfo;
  }

  public abstract BTXDetailsGetSamples getSampleData(BTXDetailsGetSamples btx);

  public abstract SampleSelectionSummary getSummary();
  public abstract String[] getSampleIds();

  private String shortClassName = ApiFunctions.shortClassName(getClass().getName());

  public SampleCountHelper getSampleCount(String sampleType) {
    SampleCountHelper ch = new SampleCountHelper(sampleType);
    Integer count = getSummary().getSampleCount(sampleType);
    ch.setTotal(count == null ? 0 : count.intValue());
    ch.setSelected(0); // I do not have any selections (see SampleResult);      
    return ch;
  }

  /**
   * @return  a Map with an entry for each element in the SampleSet that has an 
   * associated amount.
   * 
   * The amounts are Integer objects, and they are keyed by String sampleID objects.
   * Null amounts may indicate zero amount, or a missing key may indicate zero amount or 
   * that amounts are not applicable to that sample.
   */
  public abstract Map getSampleAmounts();

  /**
   * @param ids the ids of the samples to remove from the hold list
   * @return List of SampleData objects representing samples on the 
   * hold list after the remove.
   */
  public void removeIds(String[] ids) {
    throw new UnsupportedOperationException("Cannot update ids for: " + shortClassName);
  }

  /**
   * Add the ids to the set, returning a list of ids that were unsuccesfully added.
   * @param quantitiesById  a map associating some/all ids with quantities as Numeric objects
   */
  public BTXDetailsAddToHoldList addSamplesByIdWithAmounts(BTXDetailsAddToHoldList btx) {
    throw new UnsupportedOperationException("Cannot update ids and qty for: " + shortClassName);
  }

  /**
   * Add the ids with null amounts to the set
   */
  /**
   * add the ids to the hold list, with null amounts
   */
  public String[] addSamplesById(String[] ids) {
    Map m = new HashMap(ids.length * 2);
    for (int i = 0; i < ids.length; i++) {
      m.put(ids[i], null);
    }
    BTXDetailsAddToHoldList btx = new BTXDetailsAddToHoldList();
    btx.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
    btx.setLoggedInUserSecurityInfo(_securityInfo, true);
    btx.setIdsAndAmounts(m);
    btx = addSamplesByIdWithAmounts(btx);
    return btx.getUnavailSamples();
  }

  public void setSampleData(List sampleData) {
    throw new UnsupportedOperationException("Cannot set sample data for: " + shortClassName);
  }
  /**
   * Returns the securityInfo.
   * @return SecurityInfo
   */
  protected SecurityInfo getSecurityInfo() {
    return _securityInfo;
  }
}
