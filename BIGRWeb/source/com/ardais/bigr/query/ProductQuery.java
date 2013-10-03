package com.ardais.bigr.query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.library.btx.BTXDetailsGetSampleSummary;
import com.ardais.bigr.library.btx.BTXDetailsGetSamples;
import com.ardais.bigr.security.SecurityInfo;

/**
 * Acts as a business delegate for performing Sample Selection queries, and
 * represents the results of a single query.  This class is intended to be used 
 * by the presentation tier to hide the remote EJB calls necessary to perform 
 * the actual Sample Selection queries.  In addition, this class represents
 * the results of a single query, including the number of chunks, the user's
 * current chunk and the samples selected by the user.  
 * <p>
 * This class is abstract, and as such is intended to be extended by classes
 * that represent specific types of products.  It is recommended that
 * instances of subclasses be saved in a user's session if they are to be used 
 * to obtain results in a chunked fashion, so that the initial summary query
 * does not have to be executed on every request for another chunk of results.
 */
public abstract class ProductQuery extends SampleResults {

  /**
   * the helper that wraps the SampleSelectionSummary object.
   */
  private SampleSelectionSummary _summary;

  /**
   * The chunk size, which is 0 by default, indicating that there is no
   * chunking.
   */
  private int _chunkSize = 0;

  /**
   * The number of the current chunk, i.e. the one that was last returned to 
   * the user.
   */
  private int _currentChunkNumber = 0;

  /**
   * The list of id chunks.  Each item in the list is an array of id, with
   * size equal to the chunk size.
   */
  private List _chunks; // cached ids for each chunk (summary has ids all in one list)

  /**
   * The set of selected ids.
   */
  private Set _selections = new HashSet();

  private static Log _perfLog = LogFactory.getLog(ApiLogger.BIGR_LOGNAME_LIBRARY_PERFORMANCE);

  /**
   * Creates a new <code>ProductQuery</code>, initializing itself 
   * based on the specified security information, and using the specified
   * chunk size.
   * 
   * @param  chunkSize  the chunk size
   * @param  securityInfo  a {@link com.ardais.bigr.security.SecurityInfo}
   * 												representing the user's role and privileges
   */
  public ProductQuery(int chunkSize, SecurityInfo securityInfo) {
    super(securityInfo);
    _chunkSize = chunkSize;
  }

  /**
   * Returns the chunk size.  If zero is returned, that indicates that
   * chunking is turned off.
   * 
   * @return  The chunk size.
   */
  public int getChunkSize() {
    return _chunkSize;
  }

  /**
   * Returns the starting index of items in the current chunk.
   * 
   * @return  The zero-based starting index of the current chunk.
   */
  public int getCurrentChunkIndex() {
    return getCurrentChunkNumber() * getChunkSize();
  }

  /**
   * Returns the number of the current chunk (that is, which chunk is going to be shown).
   * 
   * @return  The zero-based number of the current chunk.
   */
  public int getCurrentChunkNumber() {
    return _currentChunkNumber;
  }

  /**
   * Returns summary query information and caches the summary information for future use.
   * This calls {@link doGetSummary(com.ardais.bigr.iltds.btx.BTXDetails)} which is
   * defined as abstract in this class to get the summary.
   * 
   * @return  The <code>SampleSelectionSummary</code> containing the 
   * 					 partial query information.
   */
  public BTXDetailsGetSampleSummary loadSummary(BTXDetailsGetSampleSummary summBtx)
    throws Exception {
    summBtx = (BTXDetailsGetSampleSummary) doGetSummary(summBtx);
    _summary = summBtx.getSampleSelectionSummary();
    return summBtx;
  }

  /**
   * Get the current Summary Helper.  Must call loadSummary() first.
   */
  public SampleSelectionSummary getSummary() {
    return _summary;
  }

  /**
   * Returns the SQL statement for the partial query.
   * 
   * @return  The SQL statement.
   */
  public BTXDetails getSummarySql(BTXDetails btx) throws Exception {
    return doGetSummarySql(btx);
  }

  /**
   * Returns the number of chunks of samples matching the filters.
   * 
   * @return  The number of chunks of samples matching the filters.
   */
  public int getNumberOfChunks() {
    createChunks();
    return _chunks.size();
  }

  /**
   * Set the ids as selected.  duplicates are ok.
   */
  public void addSelectedIds(String[] ids) {
    if (_selections == null) {
      _selections = new HashSet();
    }
    int len = ids.length;
    for (int i = 0; i < len; i++) {
      _selections.add(ids[i]);
    }
  }

  /**
   * Remove all selected IDs in the current chunk of IDs.
   */
  public void clearSelectedIdsForCurrentChunk() {
    String[] ids = getSampleIds(getCurrentChunkNumber());
    int len = ids.length;
    for (int i = 0; i < len; i++) {
      _selections.remove(ids[i]);
    }
  }

  /**
   * Returns partial query information for the specific product represented
   * by this class.  This method is called by {@link #getSummary()} to perform
   * the summary query for a specific product represented by a subclass of
   * <code>ProductQuery</code>.
   * 
   * @return  The <code>SampleSelectionSummary</code> containing partial query 
   * 					 information.
   */
  protected abstract BTXDetails doGetSummary(BTXDetails request) throws Exception;

  /**
   * Returns the SQL statement for the partial query for the specific product 
   * represented by this class.  This method is called by 
   * {@link #getSummarySql()} to perform the summary query for a specific 
   * product represented by a subclass of <code>ProductQuery</code>.
   * 
   * @return  The SQL statement.
   */
  protected abstract BTXDetails doGetSummarySql(BTXDetails btx) throws Exception;

  /**
   * Returns the details of the ids of the specific product represented
   * by this class.  This method is called by {@link #getDetails()} to perform
   * the details query for a specific product represented by a subclass of
   * <code>ProductQuery</code>.
   * 
   * @param  ids  an array of ids whose details are desired
   * @return  The list of {@link com.ardais.bigr.javabeans.ProductDto}
   * 						data beans containing the full query results.
   */
  protected abstract BTXDetailsGetSamples doGetDetails(BTXDetailsGetSamples btx) throws Exception;

  /**
   * Creates the list of chunks of ids if not already created.  This should
   * be called after the summary query is run to chunk the ids.
   */
  private void createChunks() {
    if (_chunks == null) {
      _chunks = new ArrayList();

      if (_chunkSize == 0) {
        _chunks.add(_summary.getIds());
      }
      else {
        int nextIndex = 0;
        int numIdsLeft = _summary == null ? 0 : _summary.getTotalSampleCount();
        while (numIdsLeft >= _chunkSize) {
          _chunks.add(_summary.getIds(nextIndex, _chunkSize));
          nextIndex += _chunkSize;
          numIdsLeft -= _chunkSize;
        }
        if (numIdsLeft > 0) { // add leftover partial chunk 
          _chunks.add(_summary.getIds(nextIndex, numIdsLeft));
        }
      }
      if (_chunks.isEmpty())
        _chunks.add(new String[0]); // one chunk of size zero is easier to handle 
    }
  }

  public String[] getSampleIds(int chunk) {
    createChunks();
    if (chunk >= _chunks.size())
      return getIdsForLastChunk();
    else
      return (String[]) _chunks.get(chunk);
  }

  private String[] getIdsForLastChunk() {
    return (String[]) _chunks.get(_chunks.size() - 1); // last chunk
  }

  public void setChunk(int chunkNo) {
    _currentChunkNumber = chunkNo;
  }

  /**
   * Updates the data in this object to represent the specified chunk number.
   * 
   * @param  chunkIndex  the zero-based index of the desired chunk
   * @request the current HTTPServletRequest
   */
  protected BTXDetailsGetSamples retrieveDetailsChunk(BTXDetailsGetSamples btx) throws Exception {

    long tStart = 0;
    String myClassName = null;
    if (_perfLog.isDebugEnabled()) {
      myClassName = ApiFunctions.shortClassName(getClass().getName());
      _perfLog.debug("    B: START get details chunk " + myClassName);
      tStart = System.currentTimeMillis();
    }

    long t0 = System.currentTimeMillis();
    long t1;

    // remember last chunk (when no BTX chunk is specified we use the most recent chunk)
    int specifiedChunk = btx.getCurrentChunk();
    _currentChunkNumber = specifiedChunk;

    // ids per chunk is here in the session.  add to BTX request object 
    String[] idsForChunk = (btx.getRetrieveAllChunks()) ? getAllSampleIds() : getSampleIds(_currentChunkNumber);
    btx.setSampleIds(idsForChunk);

    t1 = System.currentTimeMillis();
    long elapsedA = t1 - t0;
    t0 = t1;

    btx = doGetDetails(btx); // invoke the server side BTX request for the ids

    t1 = System.currentTimeMillis();
    long elapsedB = t1 - t0;
    t0 = t1;

    if (_perfLog.isDebugEnabled()) {
      long elapsedTime = System.currentTimeMillis() - tStart;
      _perfLog.debug(
        "    B: END   get details chunk "
          + myClassName
          + " ("
          + elapsedTime
          + " ms ["
          + elapsedA
          + "/"
          + elapsedB
          + "])");
    }
    return btx;
  }

  public void clearSelectedIds() {
    _selections = new HashSet();
  }

  /**
   * Return the intersection of ids in this chunk with selected ids.
   */
  public String[] getSelectedIds(int chunkNumber) {
    String[] chunk = (String[]) _chunks.get(chunkNumber);
    int len = chunk.length;
    Set sel = new HashSet();
    for (int i = 0; i < len; i++) {
      if (_selections.contains(chunk[i])) {
        sel.add(chunk[i]);
      }
    }
    return (String[]) sel.toArray(new String[sel.size()]);
  }

  public int getSelectedIdsCount() {
    return _selections.size();
  }

  /**
   * Flatten the array of arrays structure into a single array by concatenating
   * all the arrays into one array.
   */
  public String[] getSelectedIds() {
    return (String[]) _selections.toArray(new String[_selections.size()]);
  }

  /**
   * Returns an indication of whether BIGR supports placing this product 
   * type on hold.
   * 
   * @return  <code>true</code> if the current product type can be placed
   *                   on hold; <code>false</code> otherwise.
   */
  public abstract boolean isCanBePlacedOnHold();

  /**
   * return the Ids of the samples returned and cached by this query
   */
  public String[] getSampleIds() {
    return _summary.getIds();
  }

  /**
   * @see com.ardais.bigr.query.SampleResults#getSampleAmounts()
   */
  public Map getSampleAmounts() {
    return Collections.EMPTY_MAP;
  }

	protected String[] getAllSampleIds()
	{
		createChunks();
		final List<String> result = new ArrayList<String>();
		for (Object chunk : _chunks)
		{
			Collections.addAll(result, (String[])chunk);
		}
		return result.toArray(new String[result.size()]);
    }
} // End of class ProductQuery
