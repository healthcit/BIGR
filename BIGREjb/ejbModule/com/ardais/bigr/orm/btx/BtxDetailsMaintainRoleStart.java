package com.ardais.bigr.orm.btx;

import java.util.List;
import java.util.Set;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;

/**
 * Represent the details of starting the process of maintaining roles.
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
 * <li>{@link #setRoleId(String) Role id}: The role id that we're operating on. 
 *     This is required for "Update" operations and must be null on input for "Create"
 *     operations.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>{@link #setPopulateRoleOutputFields(boolean) Populate role output fields}:
 *     When false, the role fields (output parameters) will not be be populated when
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
 * <li>{@link #setRoles(List) Role list}: This is a list of all roles that
 *     currently exist in the database that are accessible to the user.  Each item in the 
 *     list is a {@link com.ardais.bigr.javabeans.RoleDto} data bean.</li>
 * </ul>
 */
public class BtxDetailsMaintainRoleStart extends BtxDetailsMaintainRole {
  //static final long serialVersionUID = -1607555401858313434L;

  private List _roles = null;
  private boolean _populateRoleOutputFields = true;

  public BtxDetailsMaintainRoleStart() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_MAINTAIN_ROLE_START;
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
  public List getRoles() {
    return _roles;
  }

  /**
   * @return
   */
  public boolean isPopulateRoleOutputFields() {
    return _populateRoleOutputFields;
  }

  /**
   * @param list
   */
  public void setRoles(List list) {
    _roles = list;
  }

  /**
   * @param b
   */
  public void setPopulateRoleOutputFields(boolean b) {
    _populateRoleOutputFields = b;
  }
}
