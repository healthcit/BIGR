package com.ardais.bigr.orm.btx;

import java.util.List;
import java.util.Set;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;

/**
 * Represent the details of starting the process of maintaining box layouts.
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
 * <li>{@link #setBoxLayoutId(String) Box Layout Id}: The box layout id that we're operating on. 
 *     This is required for "Update" operations and must be null on input for "Create"
 *     operations.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>{@link #setPopulatePolicyOutputFields(boolean) Populate box layout output fields}:
 *     When false, the box layout fields (output parameters) will not be be populated when
 *     they normally would (see below).  Instead, these fields will be left with whatever values
 *     they had on input.  This is useful when you're coming back to this action in order to
 *     display a validation error to a user's previous attempt at doing something and you don't
 *     want to overwrite the (invalid) data that they had entered (so that they can see what they
 *     entered and correct it more easily).</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setBoxLayouts(List) Box Layout list}: This is a list of all box layouts that
 *     currently exist in the database.  Each item in the list is a 
 *     {@link com.ardais.bigr.javabeans.BoxLayoutDto} DTO.</li>
 * </ul>
 */
public class BtxDetailsMaintainBoxLayoutStart extends BtxDetailsMaintainBoxLayout {
  static final long serialVersionUID = 4447507153968408847L;

  private List _boxLayouts = null;
  private boolean _populateOutputFields = true;

  public BtxDetailsMaintainBoxLayoutStart() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_MAINTAIN_BOX_LAYOUT_START;
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
  public List getBoxLayouts() {
    return _boxLayouts;
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
  public void setBoxLayouts(List list) {
    _boxLayouts = list;
  }

  /**
   * @param b
   */
  public void setPopulateOutputFields(boolean b) {
    _populateOutputFields = b;
  }
}
