package com.ardais.bigr.query;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.lims.btx.BTXDetailsGetPathQCSampleDetails;
import com.ardais.bigr.lims.javabeans.PathologyEvaluationSampleData;
import com.ardais.bigr.lims.web.helpers.PathQcHelper;
import com.ardais.bigr.security.SecurityInfo;

/**
 * Acts as a business delegate for performing Sample Selection queries.  
 * This class is intended to be used by the presentation or controller 
 * tier to hide the remote EJB calls necessary to perform the actual 
 * Sample Selection queries.  Separate methods exist to retrieve results
 * for all supported product types, either all at once or in chunks.
 * It is recommended that an instance of this class be saved in a user's 
 * session if it is to be used to obtain results in a chunked fashion.
 * 
 * @see  com.ardais.bigr.query.filters.ProductFilters
 * @see  com.ardais.bigr.query.RnaColumns
 * @see  com.ardais.bigr.query.SampleColumns
 * @see  com.ardais.bigr.query.SampleSelectionSummary 
 */
public class LimsPathQcQuery {
  public static final String LIMSPATHQCQUERY_KEY = "limsPathQcQueryKey";

  private int _chunkSize = 0;
  private int _nextIndex = 0;

  private boolean _isSampleSummaryQueryDone = false;

  private List _sampleChunks;
  private IdList _sampleSummary;
  private HashSet _markedSamples;
  private HashSet _modifiedSamples;
  private SecurityInfo _securityInfo;

  private Map _markedSampleFunctions;
  private Map _reasons;

  public LimsPathQcQuery(int chunkSize, IdList samples, SecurityInfo securityInfo) {
    _chunkSize = chunkSize;
    _sampleSummary = samples;
    _securityInfo = securityInfo;
  }

  /**
   * Returns partial query information for all samples matching the
   * query parameters.
   * 
   * @return  The <code>SampleSelectionSummary</code> containing the 
   * 			partial query information.
   */
  public IdList getSampleSummary() {
    return _sampleSummary;
  }

  /**
   * Returns <code>true</code> if a next chunk of samples exists.
   * 
   * @return  <code>true</code> if a next chunk of samples exists;
   *			<code>false</code> otherwise
   */
  public boolean hasSamplesNext() {
    int neededSize = _nextIndex + _chunkSize;
    if (_sampleSummary.size() < neededSize) {
      return false;
    }
    return true;
  }

  /**
   * Returns the next chunk of samples matching the query parameters.
   * If less than the chunk size number of samples are remaining, then 
   * just the remaining samples are returned.  The caller can check the 
   * length of the returned list to determine the actual number of 
   * samples returned.
   * 
   * @return  The list of sample data beans containing the full query results.
   */
  public List getSamplesChunk(int chunkIndex) {
    getSampleSummary();
    createSampleChunks();

    BTXDetailsGetPathQCSampleDetails btx = new BTXDetailsGetPathQCSampleDetails();

    btx.setBeginTimestamp(new Timestamp(new java.util.Date().getTime()));
    btx.setLoggedInUserSecurityInfo(_securityInfo);

    List samples = null;

    try {
      btx.setSampleIds(new IdList((List) _sampleChunks.get(chunkIndex)));
      btx.setTransactionType("lims_get_path_qc_sample_details");
      btx =
        (BTXDetailsGetPathQCSampleDetails) Btx.perform(btx);
      if (btx.isTransactionCompleted()) {
        //create PathQCHelpers from the PathologyEvaluationSampleData objects returned from the query
        samples = new ArrayList();
        Iterator iterator = btx.getPathologyEvaluationSampleDatas().iterator();
        while (iterator.hasNext()) {
          PathQcHelper helper = new PathQcHelper((PathologyEvaluationSampleData) iterator.next());
          samples.add(helper);
        }
      }

    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    return samples;
  }
  /**
   * Returns the chunk of samplesIds matching query parametrs.
   * 
   * @return The <code>List</code> of sampleIds. 
   */

  public List getSampleSummaryChunk(int chunkIndex) {
    getSampleSummary();
    createSampleChunks();
    return (List) _sampleChunks.get(chunkIndex);
  }

  /**
   * Returns number of sample chunks.
   * 
   * @return int.
   */

  public int getSamplesChunkNumber() {
    createSampleChunks();
    return _sampleChunks.size();
  }
  /**
     * Returns size of chunk.
     * 
     * @return int.
     */
  public int getChunkSize() {
    return _chunkSize;
  }
  /**
     * Creates sample chunks.
     */
  private void createSampleChunks() {
    if (_sampleChunks == null) {
      _sampleChunks = new ArrayList();

      if (_chunkSize == 0) {
        _sampleChunks.add(_sampleSummary);
      }
      else {
        while (hasSamplesNext()) {
          _sampleChunks.add(getIds(_nextIndex, _chunkSize));
          _nextIndex += _chunkSize;

        }
        if (_nextIndex < _sampleSummary.size()) {
          _sampleChunks.add(getIds(_nextIndex, _sampleSummary.size()));
          _nextIndex = _sampleSummary.size();
        }
      }
    }
  }
  /**
   * Returns SampleIds from the specified index.
   * 
   * @param <code>int<code> nextIndex.
   * @param <code>int</code> chunkSize.
   * @return <code>List</code> of SampleIds.
   */
  private List getIds(int nextIndex, int chunkSize) {
    List temp = new ArrayList();
    ArrayList blah = new ArrayList(_sampleSummary.getList());

    int tempIndex = nextIndex + chunkSize;
    if (tempIndex > blah.size()) {
      tempIndex = blah.size();
    }

    for (int i = nextIndex; i < tempIndex; i++) {
      temp.add((String) blah.get(i));

    }
    return temp;
  }

  /**
   * Adds sampleId to modified samples set.
   * <code>true</code> if this set did not already contain the specified element.
   */
  public boolean addToModifiedSamples(String sampleId) {
    if (_modifiedSamples == null) {
      _modifiedSamples = new HashSet();
    }
    return _modifiedSamples.add(sampleId);
  }

  /**
   * Returns <code>true</code> if modifiedSamples set contains specified
   * SampleId.
   */
  public boolean isModifiedSample(String sampleId) {
    if (_modifiedSamples == null) {
      return false;
    }
    return _modifiedSamples.contains(sampleId);
  }

  /**
   * Returns the markedSamples.
   * @return AbstractSet
   */
  public HashSet getMarkedSamples() {
    if (_markedSamples == null) {
      _markedSamples = new HashSet();
    }
    return _markedSamples;
  }

  /**
   * Removes all samples from markedSamples.
   * @return void
   */
  public void removeAllMarkedSamples() {
    if (_markedSamples == null) {
      return;
    }
    else {
      _markedSamples.clear();
    }
    return;
  }

  /**
   * Removes all from markedSampleFunctions.
   * @return void
   */
  public void removeAllMarkedSampleFunctions() {
    if (_markedSampleFunctions != null) {
      _markedSampleFunctions.clear();
    }
  }

  /**
   * Removes all entries from <code>Map</code> _reasons.
   * @return void
   */
  public void removeAllReasons() {
    if (_reasons != null) {
      _reasons.clear();
    }
  }

  /**
   * Adds key, value pair to <code>Map</code>_markedSampleFunctions. No values
   * will be added if key or value is empty.
   * @param key 
   * @param value to be added for the key.
   */
  public void addToMarkedSampleFunctions(String key, String value) {
    if ((!ApiFunctions.isEmpty(key)) && (!ApiFunctions.isEmpty(key))) {
      //Instantiate if empty
      if (_markedSampleFunctions == null) {
        _markedSampleFunctions = new HashMap();
      }
      _markedSampleFunctions.put(key, value);
    }
  }

  /**
   * Adds key, value pair to <code>Map</code>_reasons. No values
   * will be added if key or value is empty.
   * @param key 
   * @param value to be added for the key.
   */
  public void addToReasons(String key, String value) {
    if ((!ApiFunctions.isEmpty(key)) && (!ApiFunctions.isEmpty(key))) {
      //Instantiate if empty
      if (_reasons == null) {
        _reasons = new HashMap();
      }
      _reasons.put(key, value);
    }
  }

  /**
   * Returns String "checked" if value of name in _markedSampleFunction 
   * is tx. Returns empty String in all other cases.
   * @param name
   * @param tx
   * @return String
   */
  public String getCheckedValueForRadio(String name, String tx) {
    if ((!ApiFunctions.isEmpty(name)) && (!ApiFunctions.isEmpty(tx))) {
      //if _markedSampleFunctions is empty, return empty string.
      if (_markedSampleFunctions == null) {
        return "";
      }
      //
      else {
        String val = (String) _markedSampleFunctions.get(name);
        if (val == null) {
          return "";
        }
        else {
          if (val.equals(tx)) {
            return "checked";
          }
          else {
            return "";
          }
        }
      }

    }
    else {
      return "";
    }

  }
  /**
   * Returns reason for key.
   * @param key
   * @return value for given key. returns empty string if no match available.
   */
  public String getReason(String key) {
    if (_reasons == null) {
      return "";
    }
    else {
      String val = (String) _reasons.get(key);
      if (val == null) {
        return "";
      }
      else {
        return val;
      }
    }
  }
  /**
   * Returns the markedSampleFunctions.
   * @return Map
   */
  public Map getMarkedSampleFunctions() {
    return _markedSampleFunctions;
  }

} // End of class LimsPathQcQuery
