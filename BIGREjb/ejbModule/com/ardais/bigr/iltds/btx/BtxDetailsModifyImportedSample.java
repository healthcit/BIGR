package com.ardais.bigr.iltds.btx;

import com.ardais.bigr.util.Constants;


/**
 * Represent the details of modifying an imported sample.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
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
 * <li>None.</li>
 * </ul>
 */
public class BtxDetailsModifyImportedSample extends BtxDetailsImportedSample implements java.io.Serializable {
  private static final long serialVersionUID = 6959009211971683096L;

  public BtxDetailsModifyImportedSample() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_MODIFY_IMPORTED_SAMPLE;
  }
  /**
   * @return
   */
  public String getSampleAction() {
    return Constants.OPERATION_UPDATE;
  }
}