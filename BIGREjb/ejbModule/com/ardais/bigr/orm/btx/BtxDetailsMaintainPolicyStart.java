package com.ardais.bigr.orm.btx;

import java.util.List;
import java.util.Set;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;

/**
 * Represent the details of starting the process of maintaining policies.
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
 * <li>{@link #setPolicyId(String) Policy id}: The policy id that we're operating on. 
 *     This is required for "Update" operations and must be null on input for "Create"
 *     operations.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>{@link #setPopulatePolicyOutputFields(boolean) Populate policy output fields}:
 *     When false, the policy fields (output parameters) will not be be populated when
 *     they normally would (see below).  Instead, these fields
 *     will be left with whatever values they had on input.  This is useful when you're
 *     coming back to this action in order to display a validation error to a user's
 *     previous attempt at doing something and you don't want to overwrite the (invalid)
 *     data that they had entered (so that they can see what they entered and correct
 *     it more easily).</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setPolicies(List) Policy list}: This is a list of all policies that
 *     currently exist in the database.  Each item in the list is a 
 *     {@link com.ardais.bigr.iltds.databeans.PolicyData} data bean.</li>
 * </ul>
 */
public class BtxDetailsMaintainPolicyStart extends BtxDetailsMaintainPolicy {
  static final long serialVersionUID = -1607555401858313434L;

  private List _policies = null;
  private boolean _populatePolicyOutputFields = true;

  public BtxDetailsMaintainPolicyStart() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_MAINTAIN_POLICY_START;
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
  public List getPolicies() {
    return _policies;
  }

  /**
   * @return
   */
  public boolean isPopulatePolicyOutputFields() {
    return _populatePolicyOutputFields;
  }

  /**
   * @param list
   */
  public void setPolicies(List list) {
    _policies = list;
  }

  /**
   * @param b
   */
  public void setPopulatePolicyOutputFields(boolean b) {
    _populatePolicyOutputFields = b;
  }
}
