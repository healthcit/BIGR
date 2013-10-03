package com.ardais.bigr.query;

import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.library.SampleSet;
import com.ardais.bigr.library.web.helper.SampleCountHelper;
import com.ardais.bigr.security.SecurityInfo;

/**
 * @author dfeldman
 *
 * This represents a set of samples, a summary of data about the samples, and a set of 
 * selected items within those samples.
 * 
 * The super-class specifies the sample and summary data; this interface further
 * demands that implementors support tracking selected items for GUI support.
 * 
 * This should be repackaged to the Web Project, as it adds GUI support to a data-oriented
 * superclass.
 */
public abstract class SampleResults extends SampleSet {
  
  public SampleResults(SecurityInfo secInfo) {
    super(secInfo);
  }

  // the ids that have become unavailable since the query was last run    
  private String[] _removedIds = new String[0];

  public abstract String[] getSelectedIds();
  public abstract void clearSelectedIds();
  public abstract void addSelectedIds(String[] ids);
  public abstract void clearSelectedIdsForCurrentChunk() ;
  
  public String[] getSampleIds() {
    return getSampleIds(getCurrentChunkNumber());
  }

  public abstract String[] getSampleIds(int chunk) ;

  /**
   *  Add new ids to the overall list of removed ids.
   *  @param ids - the ids to add.
   */
  public void addRemovedIds(String[] ids) {
    if (ids == null || ids.length == 0)
      return;
    Set all = new HashSet();
    for (int i = 0; i < _removedIds.length; i++)
      all.add(_removedIds[i]);
    for (int i = 0; i < ids.length; i++)
      all.add(ids[i]);
    _removedIds = (String[]) all.toArray(new String[0]);
  }

  public String[] getRemovedIds() {
    return _removedIds;
  }

//  /**
//   * Clears the removed product ids.
//   */
//  public void clearRemovedIds() {
//    _removedIds = new String[0];
//  }

  /**
   * The index of the start of the currently displayed chunk.  
   * Should be zero for non-chunking implementations.
   */
  public int getCurrentChunkIndex() {
    return 0; // by default, always start at index 0, because always at chunk 0
  }

  /**
   * Which chunk is being displayed.  Zero for non-chunking implementations
   */
  public int getCurrentChunkNumber() {
    return 0;
  }

  /**
   * Set the currently viewed chunk to <code>chunkNo</code>.  If the chunk number is 
   * beyond the max chunk of this set, set the chunk number to the max.
   * @param chunkNo
   */
  public void setChunk(int chunkNo) {
    // by default, ignore chunks, because all chunks will be >= 0
  }

  /**
   * A user-readable string describing this set of results.
   */
  public abstract String getDisplay();
  public abstract String getProductType();

  /**
   * @see com.ardais.bigr.library.SampleSet#getFrozenCounts()
   * Updates the selected value on superclass implementation.
   */
  public SampleCountHelper getSampleCount(String sampleType) {
    String[] ids = getSelectedIds();
    SampleCountHelper ch = super.getSampleCount(sampleType);
    ch.setSelected(countBySampleType(ids, sampleType));
    return ch;
  }

  private int countBySampleType(String[] ids, String sampleType) {
    int count = 0;
    int numIds = ids.length;
    SampleSelectionSummary summary = getSummary();

    for (int i = 0; i < numIds; i++) {
      if (summary.getSampleType(ids[i]).equals(sampleType)) {
        count++;
      }
    }
    return count;
  }
}
