package com.ardais.bigr.query;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ardais.bigr.library.SampleSet;
import com.ardais.bigr.library.btx.BTXDetailsAddToHoldList;
import com.ardais.bigr.library.btx.BTXDetailsGetSamples;
import com.ardais.bigr.library.web.form.ResultsForm;

/**
 * @author dfeldman
 *
 * This class wraps a SampleSet to act like a SampleResults.  The main difference is that
 * SampleResults has a list of selected IDs for gui support.  
 *
 * @todo:  refactor - this is sort of doubly implementing SampleSet -- once
 * as a wrapper, once by inheriting the behavior.
 * BE SURE this class masks all superclass behavior, b/c we want to be a wrapper, not
 * a separate implementation of SampleSet. 
 */
public class SampleSetResultsWrapper extends SampleResults {

  private SampleSet _sampleSet;
  private String[] _selectedIds = new String[0];
  private String _displayName;

  /**
   * Constructor for SampleSetResultsWrapper.
   */
  public SampleSetResultsWrapper(SampleSet sampSet, String displayName) {
    super(null); // null securityinfo, b/c sampSet has the secinfo
    _sampleSet = sampSet;
    _displayName = displayName;
  }

  /**
   * @see com.ardais.bigr.query.SampleResults#clearOnHoldIds()
   */
  //  public void clearOnHoldIds() {
  //    _sampleSet.removeIds(_sampleSet.getSampleIds());
  //  }

  /**
   * @see com.ardais.bigr.query.SampleResults#clearSelectedIds()
   */
  public void clearSelectedIds() {
    _selectedIds = new String[0];
  }

  public void addSelectedIds(String[] ids) {
    Set existing = new HashSet(Arrays.asList(_selectedIds));
    Set newIds = new HashSet(Arrays.asList(ids));
    existing.addAll(newIds);
    _selectedIds = (String[]) existing.toArray(new String[existing.size()]);
  }

  /**
   * Method clearSelectedIdsForCurrentChunk.
   */
  public void clearSelectedIdsForCurrentChunk() {
    _selectedIds = new String[0]; // always at chunk 0, which is the only chunk
  }

  /**
   * @see com.ardais.bigr.query.SampleResults#getSelectedIds()
   */
  public String[] getSelectedIds() {
    return _selectedIds;
  }

  /**
   * @see com.ardais.bigr.query.SampleResults#setForm(ResultsForm)
   */
  public void setForm(ResultsForm form) {
    _selectedIds = form.getSamples();
  }

  // --- below, pass methods to methods of same name of SampleSet
  // --- once all pass through, subclass some SampleSet class, rather than delegate
  /**
   * @see com.ardais.bigr.query.SampleResults#getHelpers()
   */
  public BTXDetailsGetSamples getSampleData(BTXDetailsGetSamples btx) {
    return _sampleSet.getSampleData(btx);
  }

  public String[] getSampleIds(int chunk) {
    if (chunk == 0)
      return getSampleIds();
    else
      throw new UnsupportedOperationException("No chunked data available for samples");
  }

  /**
   * return the ids of the data I hold 
   */
  public String[] getSampleIds() {
    return _sampleSet.getSampleIds();
  }

  /**
   * @see com.ardais.bigr.query.SampleResults#getOnHoldIds()
   */
  //  public String[] getOnHoldIds() {
  //    return _sampleSet.getSampleIds();
  //  }

  /**
   * @see com.ardais.bigr.query.SampleResults#setOnHoldIds(String[])
   */
  //  public void setOnHoldIds(String[] ids) {
  //    _sampleSet.removeIds(_sampleSet.getSampleIds());
  //    _sampleSet.addSamplesById(ids);
  //  }

  /**
   * @see com.ardais.bigr.query.SampleResults#getSummaryHelper()
   */
  public SampleSelectionSummary getSummary() {
    return _sampleSet.getSummary();
  }

  /**
   * @see com.ardais.bigr.library.UpdatableSampleSet#addSamplesById(Map)
   */
  public BTXDetailsAddToHoldList addSamplesByIdWithAmounts(BTXDetailsAddToHoldList btx) {
    return _sampleSet.addSamplesByIdWithAmounts(btx);
  }

  /**
   * @see com.ardais.bigr.library.UpdatableSampleSet#addSamplesById(String[])
   */
  public String[] addSamplesById(String[] ids) {
    return _sampleSet.addSamplesById(ids);
  }

  /**
   * @see com.ardais.bigr.library.UpdatableSampleSet#removeIds(String[])
   */
  public void removeIds(String[] ids) {
    _sampleSet.removeIds(ids);
  }

  /**
   * @see com.ardais.bigr.query.SampleResults#getSampleAmounts()
   */
  public Map getSampleAmounts() {
    return _sampleSet.getSampleAmounts();
  }

  /**
   * Returns the displayName.
   * @return String
   */
  public String getDisplay() {
    return _displayName;
  }

  /**
   * @see com.ardais.bigr.query.SampleResults#getProductType()
   */
  public String getProductType() {
    return ""; // Only RnaQuery and SampleQuery have an associated product type.
    // @todo: add to SampleSet, since it is a backend construct, and hold list. simple set
    // can have a type, since they can be explicitly requested
  }

  /**
   * @see com.ardais.bigr.query.SampleResults#setCurrentChunkNumber(int)
   */
  public void setCurrentChunkNumber(int chunkNo) {
    // currently, current chunk is always zero.  ignore.
  }

}
