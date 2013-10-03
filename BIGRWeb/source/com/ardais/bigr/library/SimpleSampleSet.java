package com.ardais.bigr.library;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.library.btx.BTXDetailsAddToHoldList;
import com.ardais.bigr.library.btx.BTXDetailsGetSamples;
import com.ardais.bigr.query.SampleSelectionSummary;
import com.ardais.bigr.security.SecurityInfo;

/**
 * @author dfeldman
 *
 * This SampleSet holds its data in fields.  So if it is stored in the HTTPSession, the
 * set of samples will be Session-scoped data.  If an instance is created and then discarded
 * the data will have no persistence.  
 * 
 * In contrast, other SampleSets such as the HoldListSampleSet store thier data in the 
 * Database via the HoldList.
 */
public class SimpleSampleSet extends SampleSet {

  /**
   * This map holds all the ID's in the set, and associated amounts for those that have
   * amounts set.
   */
  private Map _idsAndAmounts = new HashMap();

  /**
   * This is the list of SampleData objects corresponding to the ID's in this collection.
   * It is really a cache, since the ID's are primary, and this *could* be retreived
   * every time from the back end.
   */
  private SampleViewData _data;

  private SampleSelectionSummary _summary;

  public SimpleSampleSet(SecurityInfo securityInfo) {
    super(securityInfo);
  }

  /**
   * Return ids that are inappropriate for adding to this set.
   * By default, everything is valid.  Subclasses override to disallow some
   * items from being put into a set.
   */
  protected BTXDetailsAddToHoldList getInvalidIds(BTXDetailsAddToHoldList btx) {
    btx.setUnavailSamples(new String[0]);
    return btx; // no processing -- unavail sample collection still empty
  }

  /**
   * @see com.ardais.bigr.library.SampleSet#addToHoldList(String[])
   */
  public BTXDetailsAddToHoldList addSamplesByIdWithAmounts(BTXDetailsAddToHoldList btx) {
    Map idsToAmounts = btx.getIdsAndAmounts();

    // update the unavailable IDs on the BTX object (may involve a btx validate call in subclasses)
    btx = getInvalidIds(btx);
    String[] unavail = btx.getUnavailSamples();

    // update this sample set
    _idsAndAmounts.putAll(idsToAmounts);
    for (int i = 0; i < unavail.length; i++) {
      _idsAndAmounts.remove(unavail[i]);
    }

    // clear cached data values, now that the ID list has changed
    _data = null;

    return btx; // return btx, with unavailable samples set on it
  }

  // return null if both null, i+j otherwise with null=>0 if the other is non-null
  private Integer addIntegers(Integer i, Integer j) {
    if (i == null && j == null)
      return null;
    int ii = i == null ? 0 : i.intValue();
    int jj = j == null ? 0 : j.intValue();
    return new Integer(ii + jj);
  }

  /**
   * @see com.ardais.bigr.library.SampleSet#getSampleData()
   */
  public BTXDetailsGetSamples getSampleData(BTXDetailsGetSamples btx) {
    try {
      String[] ids = (String[]) _idsAndAmounts.keySet().toArray(new String[0]);
      //populate from session bean
      btx.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
      btx.setSampleIds(ids);
      btx.setTransactionType("library_get_details");
      btx = (BTXDetailsGetSamples) Btx.perform(btx);
      _data = new SampleViewData(btx);

      // Get the sample types from the sample ids.
      String[] sampleIds = btx.getSampleIds();
      Set sampleTypes = IltdsUtils.getSampleTypesBySampleIds(ApiFunctions.safeToList(sampleIds));
      btx.setSampleTypes(sampleTypes);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
      return null;
    }
    return btx;
  }

  /**
   * @see com.ardais.bigr.library.SampleSet#getSampleIds()
   */
  public String[] getSampleIds() {
    return (String[]) _idsAndAmounts.keySet().toArray(new String[0]);
  }

  /**
   * @see com.ardais.bigr.library.SampleSet#getSummary()
   */
  public SampleSelectionSummary getSummary() {
    return _summary;
  }

  /**
   * @see com.ardais.bigr.library.SampleSet#removeIds(String[])
   */
  public void removeIds(String[] ids) {
    for (int i = 0; i < ids.length; i++)
      _idsAndAmounts.remove(ids[i]);
    _data = null; // clear the data cache
  }

  private Set asSet(String[] strs) {
    Set set = new HashSet();
    for (int i = 0; i < strs.length; i++) {
      set.add(strs[i]);
    }
    return set;
  }

  /**
   * @see com.ardais.bigr.query.SampleResults#getSampleAmounts()
   */
  public Map getSampleAmounts() {
    return Collections.unmodifiableMap(_idsAndAmounts);
  }
}
