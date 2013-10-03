package com.ardais.bigr.orm.btx;

import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.IcpUtils;

/**
 * Represent the details of maintenance of account box layout (create/edit/delete).
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setOperation(String) Operation}: The maintenance operation that is being
 *     performed.  This must be one of "Create", "Update", or "Delete" (case-sensitive).</li>
 * <li>{@link #setAccountBoxLayoutId(int) Account box layout id}: The account box layout id
 *     that we're operating on.  This is a required field for "Update" and "Delete" operations
 *     and must be zero on input for the "Create" operations (it is an output parameter for
 *     "Create" operation).</li>
 * <li>{@link #setBoxLayoutName(String) Box layout name}: The box layout name must be unique for
 *     the account that we are operating on. The string length must be less than a 100 characters
 *     in length.  This is a required field for the "Update" and "Create" operations and is
 *     ignored on input for the "Delete" operation.</li>
 * <li>{@link #setAccountId(String) Account id}: The account id that we are operation on.  This
 *     id must be valid and exist in our system.  This is a required field for the "Update" and
 *     "Create" operations and is ignored on input for the "Delete" operation.</li>
 * <li>{@link #setBoxLayoutId(String) Box layout id}: The box layout id must be unique for the
 *     account that we are operation on.  The box layout id must be valid and exist in our
 *     system.  This is a required field for the "Update" and "Create" operations and is ignored
 *     on input for the "Delete" operation.</li>
 * <li>{@link #setDefaultAccountBoxLayout(boolean) Default account box layout}: The default account
 *     box layout flag is set to true to indicate that the current account box layout will become
 *     the default box layout for the account. Please note that the default account layout cannot
 *     be deleted.  The user must select or create a new account box layout prior to deleting the
 *     account box layout that was once the default.  This is a required field for the "Update"
 *     and "Create" operations and is ignored on input for the "Delete" operation.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 * 
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setAccountBoxLayoutId(String) Account box layout id}: This is sometimes an output
 *     parameter, as described above.</li>
 * <li>{@link #setBoxLayoutName(String) Box layout name}: This is sometimes an output parameter,
 *     as described above.</li>
 * <li>{@link #setAccountId(String) Account id}: This is sometimes an output parameter, as
 *     described above.</li>
 * <li>{@link #setBoxLayoutId(String) The box layout id}: This is sometimes an output parameter,
 *     as described above.</li>
 * <li>{@link #setDefaultAccountBoxLayout(String) The default account box layout}: This is
 *     sometimes an output parameter, as described above.</li>
 * </ul>
 */
public class BtxDetailsManageAccountBoxLayout extends BTXDetails {
  static final long serialVersionUID = 759712516974304367L;

  private String _operation = null;

  private int _accountBoxLayoutId = 0;
  private String _boxLayoutName = null;
  private String _accountId = null;
  private String _boxLayoutId = null;
  private boolean _defaultAccountBoxLayout = false;

  public BtxDetailsManageAccountBoxLayout() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_MANAGE_ACCOUNT_BOX_LAYOUT;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  public Set getDirectlyInvolvedObjects() {

    Set set = new HashSet();
    
    set.add(getAccountId());
    set.add(getBoxLayoutId());

    return set;
  }

  /**
   * Fill a business transaction history record object with information
   * from this transaction details object.  This method will set <b>all</b>
   * fields on the history record, even ones not used by the this type of
   * transaction.  Fields that aren't used by this transaction type will be
   * set to their initial default values.
   * <p>
   * This method is only meant to be used internally by the business
   * transaction framework implementation.  Please don't use it anywhere else.
   *
   * @param history the history record object that will have its fields set to
   *    the transaction information.
   */
  public void describeIntoHistoryRecord(BTXHistoryRecord history) {
    super.describeIntoHistoryRecord(history);

    history.setAttrib1(getOperation());
    history.setAttrib2(getBoxLayoutName());
    history.setAttrib3(getAccountId());
    history.setAttrib4(getBoxLayoutId());
  }

  /**
   * Populate the fields of this object with information contained in a
   * business transaction history record object.  This method must set <b>all</b>
   * fields on this object, as if it had been newly created immediately before
   * this method was called.  A runtime exception is thrown if the transaction type
   * represented by the history record doesn't match the transaction type represented
   * by this object.
   * <p>
   * This method is only meant to be used internally by the business
   * transaction framework implementation.  Please don't use it anywhere else.
   *
   * @param history the history record object that will be used as the information
   * source. A runtime exception is thrown if this is null.
   */
  public void populateFromHistoryRecord(BTXHistoryRecord history) {
    super.populateFromHistoryRecord(history);
    
    setOperation(history.getAttrib1());
    setBoxLayoutName(history.getAttrib2());
    setAccountId(history.getAttrib3());
    setBoxLayoutId(history.getAttrib4());

    // These fields don't correspond to anything in the history record
    // but we must set them anyways.
    //
    setAccountBoxLayoutId(0);
    setDefaultAccountBoxLayout(false);
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    // NOTE: This method must not make use of any fields that aren't
    // set by the populateFromHistoryRecord method.
    //
    // For this object type, the fields we can use here are:
    //   getOperation
    //   getBoxLayoutId
    //   getBoxLayoutAttributes

    StringBuffer sb = new StringBuffer(256);

    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();

    // The form of the result depends on the operation.
    // Operation "Create":
    //    Created account box layout named (<boxLayoutName>), for account <accountId>, using box layout <boxLayoutId>.
    // Operation "Update":
    //    Updated account box layout named (<boxLayoutName>), for account <accountId>, using box layout <boxLayoutId>.
    // Operation "Delete":
    //    Deleted account box layout named (<boxLayoutName>), for account <accountId>, using box layout <boxLayoutId>.

    String operation = getOperation();
    String boxLayoutName = getBoxLayoutName();
    String accountId = getAccountId();
    String boxLayoutId = getBoxLayoutId();

    if (Constants.OPERATION_CREATE.equals(operation)) {
      sb.append("Created account box layout (");
    }
    else if (Constants.OPERATION_UPDATE.equals(operation)) {
      sb.append("Updated account box layout (");
    }
    else if (Constants.OPERATION_DELETE.equals(operation)) {
      sb.append("Removed account box layout (");
    }
    Escaper.htmlEscape(boxLayoutName, sb);
    sb.append("), for account ");
    sb.append(IcpUtils.prepareLink(accountId, securityInfo));
    sb.append(", using box layout ");
    sb.append(IcpUtils.prepareLink(boxLayoutId, securityInfo));
    sb.append(".");

    return sb.toString();
  }

  /**
   * @return
   */
  public int getAccountBoxLayoutId() {
    return _accountBoxLayoutId;
  }

  /**
   * @return
   */
  public String getAccountId() {
    return _accountId;
  }

  /**
   * @return
   */
  public String getBoxLayoutId() {
    return _boxLayoutId;
  }

  /**
   * @return
   */
  public String getBoxLayoutName() {
    return _boxLayoutName;
  }

  /**
   * @return
   */
  public boolean isDefaultAccountBoxLayout() {
    return _defaultAccountBoxLayout;
  }

  /**
   * @return
   */
  public String getOperation() {
    return _operation;
  }

  /**
   * @param i
   */
  public void setAccountBoxLayoutId(int i) {
    _accountBoxLayoutId = i;
  }

  /**
   * @param string
   */
  public void setAccountId(String string) {
    _accountId = string;
  }

  /**
   * @param string
   */
  public void setBoxLayoutId(String string) {
    _boxLayoutId = string;
  }

  /**
   * @param string
   */
  public void setBoxLayoutName(String string) {
    _boxLayoutName = string;
  }

  /**
   * @param b
   */
  public void setDefaultAccountBoxLayout(boolean b) {
    _defaultAccountBoxLayout = b;
  }

  /**
   * @param string
   */
  public void setOperation(String string) {
    _operation = string;
  }
}