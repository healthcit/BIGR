package com.ardais.bigr.orm.btx;

import java.util.List;
import java.util.Set;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;

/**
 * Represent the details of starting the process of maintaining account box layouts.
 * This doesn't actually do the maintenance changes, it just gets data that is needed to
 * prepare the user input screens.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setOperation(String) Operation}: The maintenance operation that is being
 *     performed.  This must be one of "Create" or "Update" (case-sensitive).</li>
 * <li>{@link #setAccountBoxLayoutId(String) Box Layout Id}: The account box layout id that
 *     we're operating on.  This is required for "Update" operations and must be null on input
 *     for "Create" operations.</li>
 * <li>{@link #setAccountId(String) Account Id}: The account id that we are operation on.  This
 *     is required in order to fetch the appropriate account box layouts for the account.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>{@link #setPopulatePolicyOutputFields(boolean) Populate account box layout output fields}:
 *     When false, the account box layout fields (output parameters) will not be be populated when
 *     they normally would (see below).  Instead, these fields will be left with whatever values
 *     they had on input.  This is useful when you're coming back to this action in order to
 *     display a validation error to a user's previous attempt at doing something and you don't
 *     want to overwrite the (invalid) data that they had entered (so that they can see what they
 *     entered and correct it more easily).</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setAccountBoxLayouts(List) The Account Box Layout list}: This is a list of all the
 *     account box layouts that are associated with the account id passed in.  Each item in the
 *     list is a {@link com.ardais.bigr.javabeans.AccountBoxLayoutDto} DTO.</li>
 * </ul>
 */
public class BtxDetailsManageAccountBoxLayoutStart extends BtxDetailsManageAccountBoxLayout {
  static final long serialVersionUID = 3054472036644019433L;

  private String _currentDefaultBoxLayoutId = null;
  private List _accountBoxLayouts = null;
  private boolean _populateOutputFields = true;

  public BtxDetailsManageAccountBoxLayoutStart() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_MANAGE_ACCOUNT_BOX_LAYOUT_START;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public Set getDirectlyInvolvedObjects() {
    throw new ApiException("getDirectlyInvolvedObjects should not have been called, this class does not support transaction logging.");
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
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#populateFromHistoryRecord(com.ardais.bigr.iltds.btx.BTXHistoryRecord)
   */
  public void populateFromHistoryRecord(BTXHistoryRecord history) {
    throw new ApiException("populateFromHistoryRecord should not have been called, this class does not support transaction logging.");
  }

  /**
   * @return
   */
  public List getAccountBoxLayouts() {
    return _accountBoxLayouts;
  }

  /**
   * @return
   */
  public String getCurrentDefaultBoxLayoutId() {
    return _currentDefaultBoxLayoutId;
  }

  /**
   * @return
   */
  public boolean isPopulateOutputFields() {
    return _populateOutputFields;
  }

  /**
   * @param list
   */
  public void setAccountBoxLayouts(List list) {
    _accountBoxLayouts = list;
  }

  /**
   * @param string
   */
  public void setCurrentDefaultBoxLayoutId(String string) {
    _currentDefaultBoxLayoutId = string;
  }

  /**
   * @param b
   */
  public void setPopulateOutputFields(boolean b) {
    _populateOutputFields = b;
  }
}
