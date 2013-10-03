package com.ardais.bigr.orm.btx;

import java.util.List;
import java.util.Set;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;

/**
 * Represent the details of starting the process of maintaining a logical repository.
 * This doesn't actually do the maintenance changes, it just gets data that is needed to
 * prepare the user input screens.
 * <p>
 * For historical reasons the code uses the term "logical repository" but
 * users know this as "inventory group".
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setOperation(String) Operation}: The maintenance operation that is being
 *     performed.  This must be one of "Create" or "Update" (case-sensitive).</li>
 * <li>{@link #setRepositoryId(String) Logical repository id}: The logical repository id that
 *     we're operating on.  This is required for "Update" operations and must be
 *     null on input for "Create" operations.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>{@link #setPopulateRepositoryOutputFields(boolean) Populate repository output fields}:
 *     When false, the RepositoryFullName, RepositoryShortName, and Bms output parameters
 *     won't be populated when they normally would (see below).  Instead, these fields
 *     will be left with whatever values they had on input.  This is useful when you're
 *     coming back to this action in order to display a validation error to a user's
 *     previous attempt at doing something and you don't want to overwrite the (invalid)
 *     data that they had entered (so that they can see what they entered and correct
 *     it more easily).</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setLogicalRepositories(List) Logical repository list}: This is a list of
 *     all logical repositories that currently exist in the database.  Each item in the
 *     list is a {@link com.ardais.bigr.iltds.assistants.LogicalRepository} data bean.</li>
 * <li>{@link #setRepositoryFullName(String) Logical repository full name}: This is an output
 *     parameter for "Update" operations.</li>
 * <li>{@link #setRepositoryShortName(String) Logical repository short name}: This is an output
 *     parameter for "Update" operations.</li>
 * <li>{@link #setBms(Boolean) Logical repository BMS flag}: This is an output
 *     parameter for "Update" operations.</li>
 * </ul>
 */
public class BtxDetailsMaintainLogicalRepositoryStart
  extends BtxDetailsMaintainLogicalRepository
  implements java.io.Serializable {
  private static final long serialVersionUID = -262997765833190998L;

  private List _logicalRepositories = null;
  private boolean _populateRepositoryOutputFields = true;

  public BtxDetailsMaintainLogicalRepositoryStart() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_MAINTAIN_LOGICAL_REPOSITORY_START;
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
  public List getLogicalRepositories() {
    return _logicalRepositories;
  }

  /**
   * @return
   */
  public boolean isPopulateRepositoryOutputFields() {
    return _populateRepositoryOutputFields;
  }

  /**
   * @param list
   */
  public void setLogicalRepositories(List list) {
    _logicalRepositories = list;
  }

  /**
   * @param b
   */
  public void setPopulateRepositoryOutputFields(boolean b) {
    _populateRepositoryOutputFields = b;
  }

}