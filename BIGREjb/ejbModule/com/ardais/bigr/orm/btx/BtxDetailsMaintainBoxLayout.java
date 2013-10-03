package com.ardais.bigr.orm.btx;

import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.IcpUtils;
import com.gulfstreambio.gboss.GbossFactory;

/**
 * Represent the details of maintaining a box layout (create/edit/delete).
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setOperation(String) Operation}: The maintenance operation that is being
 *     performed.  This must be one of "Create", "Update", or "Delete" (case-sensitive).</li>
 * <li>{@link #setBoxLayoutId(String) Box layout id}: The box layout id that we're operating on.
 *     This is a required field for "Update" and "Delete" operations and must be null on input for
 *     "Create" operations (it is an output parameter for "Create" operation).</li>
 * <li>{@link #setNumberOfColumns(int) The number of columns in a box}: This number is used to
 *     determine the number of columns a box has.  This number must be greater than zero. There is
 *     no max limit constraint, but for now it will be set to 100.  This is a required field for
 *     the "Update" and "Create" operations and is ignored on input for the "Delete" operation.</li>
 * <li>{@link #setNumberOfRows(int) The number of rows in a box}: This number is used to determine
 *     the number of rows a box has.  This number must be greater than zero.  There is no max limit
 *     constraint, but for now it will be set to 100.  This is a required field for the "Update"
 *     and "Create" operations and is ignored on input for the "Delete" operation.</li>
 * <li>{@link #setXAxisLabelCid(String) X axis label type}: The x axis label is used to determine
 *     what labeling policy to apply on the box on the x axis.  This is a required field for the
 *     "Create" and "Update" operations and is ignored on input for the "Delete" operation.  The
 *     value must be a valid value in the "BOX_LAYOUT_LABEL_TYPE_XY_AXIS" concept graph.</li>
 * <li>{@link #setYAxisLabelCid(String) Y axis label type}: The y axis label is used to determine
 *     what labeling policy to apply on the box on the y axis.  This is a required field for the
 *     "Create" and "Update" operations and is ignored on input for the "Delete" operation.  The
 *     value must be a valid value in the "BOX_LAYOUT_LABEL_TYPE_XY_AXIS" concept graph.</li>
 * <li>{@link #setTabIndexCid(String) Tab index policy (cid)}: The tab index policy determines
 *     which way the TAB key works.  This is a required field for the "Create" and "Update"
 *     operations and is ignored on input for the "Delete" operation.  The value must be a valid
 *     value in the "BOX_LAYOUT_TAB_DIRECTION" concept graph.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 * 
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setBoxLayoutId(String) Box layout id}: This is sometimes an output parameter, as
 *     described above.</li>
 * <li>{@link #setNumberOfColumns(String) The number of columns in a box}: This is sometimes an
 *     output parameter, as described above.</li>
 * <li>{@link #setNumberOfRows(String) The number of rows in a box}: This is sometimes an output
 *     parameter, as described above.</li>
 * <li>{@link #setXAxisLabelCid(String) The x axis label type}: This is sometimes an output
 *     parameter, as described above.</li>
 * <li>{@link #setYAxisLabelCid(String) The y axis label type}: This is sometimes an output
 *     parameter, as described above.</li>
 * <li>{@link #setTabIndexCid(String) Tab index policy}: This is sometimes an output parameter, as
 *     described above.</li>
 * </ul>
 */
public class BtxDetailsMaintainBoxLayout extends BTXDetails {
  static final long serialVersionUID = -7272931970335764038L;

  private String _operation = null;

  private String _boxLayoutId = null;
  private int _numberOfColumns = 0;
  private int _numberOfRows = 0;
  private String _xaxisLabelCid = null;
  private String _yaxisLabelCid = null;
  private String _tabIndexCid = null;

  private String _boxLayoutAttributes = null;

  public BtxDetailsMaintainBoxLayout() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_MAINTAIN_BOX_LAYOUT;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  public Set getDirectlyInvolvedObjects() {

    Set set = new HashSet();
    
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
    history.setAttrib2(getBoxLayoutId());
    history.setAttrib3(convertBoxLayoutDtoToAttributes());
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
    setBoxLayoutId(history.getAttrib2());
    setBoxLayoutAttributes(history.getAttrib3());

    // These fields don't correspond to anything in the history record
    // but we must set them anyways.
    //
    setNumberOfColumns(0);
    setNumberOfRows(0);
    setXaxisLabelCid(null);
    setYaxisLabelCid(null);
    setTabIndexCid(null);
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
    //    Created box layout (<boxLayoutId>), with the following attributes: <boxLayoutAttributes>.
    // Operation "Update":
    //    Updated box layout (<boxLayoutId>), with the following attributes: <boxLayoutAttributes>.
    // Operation "Delete":
    //    Removed box layout (<boxLayoutId>), with the following attributes: <boxLayoutAttributes>.

    String operation = getOperation();
    String boxLayoutId = getBoxLayoutId();
    String boxLayoutAttributes = getBoxLayoutAttributes();

    if (Constants.OPERATION_CREATE.equals(operation)) {
      sb.append("Created box layout (");
    }
    else if (Constants.OPERATION_UPDATE.equals(operation)) {
      sb.append("Updated box layout (");
    }
    else if (Constants.OPERATION_DELETE.equals(operation)) {
      sb.append("Removed box layout (");
    }
    sb.append(IcpUtils.prepareLink(boxLayoutId, securityInfo));
    sb.append("), with the following attributes: ");
    Escaper.htmlEscape(boxLayoutAttributes, sb);
    sb.append(".");

    return sb.toString();
  }

  /**
   * @return
   */
  public String convertBoxLayoutDtoToAttributes() {
    StringBuffer sb = new StringBuffer();

    sb.append(getNumberOfColumns());
    sb.append("x");
    sb.append(getNumberOfRows());
    sb.append(" x:");
    sb.append(GbossFactory.getInstance().getDescription(getXaxisLabelCid()));
    sb.append(", y:");
    sb.append(GbossFactory.getInstance().getDescription(getYaxisLabelCid()));
    sb.append(", tab:");
    sb.append(GbossFactory.getInstance().getDescription(getTabIndexCid()));

    return sb.toString();
  }

  /**
   * @return
   */
  public String getBoxLayoutAttributes() {
    return _boxLayoutAttributes;
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
  public int getNumberOfColumns() {
    return _numberOfColumns;
  }

  /**
   * @return
   */
  public int getNumberOfRows() {
    return _numberOfRows;
  }

  /**
   * @return
   */
  public String getOperation() {
    return _operation;
  }

  /**
   * @return
   */
  public String getTabIndexCid() {
    return _tabIndexCid;
  }

  /**
   * @return
   */
  public String getXaxisLabelCid() {
    return _xaxisLabelCid;
  }

  /**
   * @return
   */
  public String getYaxisLabelCid() {
    return _yaxisLabelCid;
  }

  /**
   * @param string
   */
  public void setBoxLayoutAttributes(String string) {
    _boxLayoutAttributes = string;
  }

  /**
   * @param string
   */
  public void setBoxLayoutId(String string) {
    _boxLayoutId = string;
  }

  /**
   * @param i
   */
  public void setNumberOfColumns(int i) {
    _numberOfColumns = i;
  }

  /**
   * @param i
   */
  public void setNumberOfRows(int i) {
    _numberOfRows = i;
  }

  /**
   * @param string
   */
  public void setOperation(String string) {
    _operation = string;
  }

  /**
   * @param string
   */
  public void setTabIndexCid(String string) {
    _tabIndexCid = string;
  }

  /**
   * @param string
   */
  public void setXaxisLabelCid(String string) {
    _xaxisLabelCid = string;
  }

  /**
   * @param string
   */
  public void setYaxisLabelCid(String string) {
    _yaxisLabelCid = string;
  }
}