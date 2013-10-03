package com.ardais.bigr.iltds.btx;

import java.util.Set;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.assistants.LegalValueSet;

/**
 * Represent the details of starting the process of pulling an imported case.
 * This doesn't actually do any database changes, it just gets data that is needed to
 * prepare the user input screens.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields: One of the following</h4>
 * <ul>
 * <li>{@link #setConsentId(String) consent id}: The Ardais consent id.</li>
 * <li>{@link #setCustomerId(String) customer id}: The customer consent id.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setPullRequestedByChoices(List) Requested by choices}: A list of people who can request
 * the pulling of a case.  This is a list of the employees in the same account as the
 * current user.</li>
 * </ul>
 */
public class BtxDetailsPullCaseStart extends BtxDetailsPullCase implements java.io.Serializable {
  private static final long serialVersionUID = -2824851442142920689L;
  private LegalValueSet _pullRequestedByChoices = null;
  private LegalValueSet _pullReasonChoices = null;

  public BtxDetailsPullCaseStart() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_PULL_CASE_START;
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
  public LegalValueSet getPullRequestedByChoices() {
    return _pullRequestedByChoices;
  }

  /**
   * @param list
   */
  public void setPullRequestedByChoices(LegalValueSet lvs) {
    _pullRequestedByChoices = lvs;
  }

  /**
   * @return
   */
  public LegalValueSet getPullReasonChoices() {
    return _pullReasonChoices;
  }

  /**
   * @param set
   */
  public void setPullReasonChoices(LegalValueSet set) {
    _pullReasonChoices = set;
  }

}