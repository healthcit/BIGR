package com.ardais.bigr.iltds.btx;

import java.util.List;
import java.util.Set;

import com.ardais.bigr.api.ApiException;

/**
 * Represent the details of starting the process of modifying an imported sample.
 * This doesn't actually do any database changes, it just gets data that is needed to
 * prepare the user input screens.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields: One of the following</h4>
 * <ul>
 * <li>{@link #setSampleData(SampleData) sampleData}: The sample data object to modify.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setMatchingSamples(List) matching samples}: A list of matching samples, should
 * the user supply a customer id and more than 1 sample has that customer id.</li>
 * <li>{@link #setSampleData(SampleData) sampleData}: The sample data object to modify.</li>
 * </ul>
 */
public class BtxDetailsModifyImportedSampleStart extends BtxDetailsModifyImportedSample implements java.io.Serializable {
  private static final long serialVersionUID = 7818939217010955520L;
  
  private List _matchingSamples;
  private boolean _readOnly = false;

  public BtxDetailsModifyImportedSampleStart() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_MODIFY_IMPORTED_SAMPLE_START;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public Set getDirectlyInvolvedObjects() {
    return null;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    throw new ApiException("doGetDetailsAsHTML should not have been called, this class does not support transaction logging.");
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#describeIntoHistoryRecord(com.ardais.bigr.iltds.btx.BTXHistoryRecord)
   */
  public void describeIntoHistoryRecord(BTXHistoryRecord history) {
    throw new ApiException("describeIntoHistoryRecord should not have been called, this class does not support transaction logging.");

    //super.describeIntoHistoryRecord(history);
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#populateFromHistoryRecord(com.ardais.bigr.iltds.btx.BTXHistoryRecord)
   */
  public void populateFromHistoryRecord(BTXHistoryRecord history) {
    throw new ApiException("populateFromHistoryRecord should not have been called, this class does not support transaction logging.");

    //super.populateFromHistoryRecord(history);
  }

  /**
   * @return
   */
  public List getMatchingSamples() {
    return _matchingSamples;
  }

  /**
   * @param list
   */
  public void setMatchingSamples(List list) {
    _matchingSamples = list;
  }

  public boolean isReadOnly() {
    return _readOnly;
  }
  
  public void setReadOnly(boolean readOnly) {
    _readOnly = readOnly;
  }
  
}