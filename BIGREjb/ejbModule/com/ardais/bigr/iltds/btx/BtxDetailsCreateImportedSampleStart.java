package com.ardais.bigr.iltds.btx;

import java.util.Set;

import com.ardais.bigr.api.ApiException;

/**
 * Represent the details of starting the process of creating an imported sample.
 * This doesn't actually do any database changes, it just gets data that is needed to
 * prepare the user input screens.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BtxDetailsCreateImportedSample BtxDetailsCreateImportedSample} for fields inherited
 * by this class.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setConsentChoices(List) consentChoices}: A list of consent versions accessible to 
 * the user. Necessary when the user is creating a case at the same time they are creating the
 * sample.</li>
 * <li>{@link #setPolicyChoices(List) policyChoices}: A list of policy choices accessible to the 
 * user. Necessary when the user is creating a case at the same time they are creating the
 * sample.</li>
 * </ul>
 */
public class BtxDetailsCreateImportedSampleStart extends BtxDetailsCreateImportedSample implements java.io.Serializable {
  private static final long serialVersionUID = 8380640200219821125L;
  

  public BtxDetailsCreateImportedSampleStart() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_CREATE_IMPORTED_SAMPLE_START;
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
}