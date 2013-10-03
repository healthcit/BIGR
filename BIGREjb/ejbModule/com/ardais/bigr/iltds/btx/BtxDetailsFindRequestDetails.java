package com.ardais.bigr.iltds.btx;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.iltds.helpers.RequestSelect;
import com.ardais.bigr.orm.helpers.BoxScanData;

/**
 * Represents the details of a find request details business transaction.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} and {@link BTXDetailsRequestOperation} for fields that are 
 * shared by all business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setInputRequestDto(RequestDto) inputRequestDto}: The RequestDto containing all 
 *      necessary input data (in this case a request id).</li>
 * <li>{@link #setRequestSelect(RequestSelect) requestSelect}: A {@link RequestSelect RequestSelect}
        object specifying what level of details to return for the specified request.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>{@link #setGetPickListInfo(boolean) getPickListInfo}: A boolean that indicates whether 
 *      some additional information, above and beyond what is normally returned for item
 *      details, should be returned because a pick list is being generated.  This includes
 *      things like the current location of the sample, the last 2 slides pv'd, etc.</li>
 * <li>{@link #setSortItemsBy(String) getSortItemsBy}: A string indicating the criteria
 *      by which the items in the request should be sorted.  If this is empty the items
 *      will be sorted in the order they are returned from the RequestFinder (currently
 *      the order in which the items were placed on the request).  See </li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setOutputRequestDto(RequestDto) outputRequestDto}: The RequestDto containing all 
 *      necessary output data.</li>
 * <li>{@link #setItemLocations(Map) itemLocations}: A Map containing a 
 *      {@link BoxLocation BoxLocation} for each item on the request.  This field is
 *      populated only if the value of {@link #isGetPickListInfo() isGetPickListInfo}
 *      method is true.</li>
 * <li>{@link #setMostRecentSlides(Map) mostRecentSlides}: A Map containing a String holding
 *      the most recent 2 slides for each item on the request.  This field is
 *      populated only if the value of {@link #isGetPickListInfo() isGetPickListInfo}
 *      method is true.</li>
 * </ul>
 */
public class BtxDetailsFindRequestDetails extends BtxDetailsRequestOperation implements Serializable {
  private static final long serialVersionUID = -8130125493548502007L;

  private RequestSelect _requestSelect;
  private boolean _getPickListInfo = false;
  private String _itemSort;
  private BoxScanData _boxScanData;
  
  /** 
   * Constructor
   */
  public BtxDetailsFindRequestDetails() {
    super();
  }

  /**
   * This method is called from the BTXDetails base class when its getDetailsAsHTML() 
   * method has been called from the BTXHistoryReaderBean.getHistoryDisplayLines 
   * method.  Since this transaction is not logged, there won't be any history so this 
   * method should never be called.  If for some reason this method is called it 
   * returns a message to the effect that it was called in error.
   *
   * @return an HTML string that defines how the transaction details are presented
   * in a transaction-history web page
   */
  protected String doGetDetailsAsHTML() {
    String msg = "BTXDetailsFindRequestDetails.doGetDetailsAsHTML() method called in error!";
    return msg;
  }
  
  /**
   * Return the business transaction type code for the transaction that this
   * class represents.
   *
   * @return the transaction type code.
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_FIND_REQUEST_DETAILS;
  }
  
  /**
   * Return a set of the object ids of the objects that are directly involved
   * in this transaction.  This set does not contain the ids of objects that
   * are considered to be indirectly involved in the transaction, and it does
   * not include the user id of the user who performed the transaction.
   * <p>
   * For example, a transaction that scans a box of samples directly involves the box
   * object and each of the sample objects, and indirectly involves the
   * asm, asm form, case and donor objects for each sample.
   *
   * @return the set of directly involved object ids.
   */
  public Set getDirectlyInvolvedObjects() {
    Set set = new HashSet();
    set.add(getInputRequestDto().getId());
    return set;
  }

  /**
   * @return
   */
  public BoxScanData getBoxScanData() {
    return _boxScanData;
  }

  /**
   * @return
   */
  public boolean isGetPickListInfo() {
    return _getPickListInfo;
  }

  /**
   * @return
   */
  public String getItemSort() {
    return _itemSort;
  }

  /**
   * @return
   */
  public RequestSelect getRequestSelect() {
    return _requestSelect;
  }

  /**
   * @param data
   */
  public void setBoxScanData(BoxScanData data) {
    _boxScanData = data;
  }

  /**
   * @param b
   */
  public void setGetPickListInfo(boolean b) {
    _getPickListInfo = b;
  }

  /**
   * @param string
   */
  public void setItemSort(String string) {
    _itemSort = string;
  }

  /**
   * @param select
   */
  public void setRequestSelect(RequestSelect select) {
    _requestSelect = select;
  }
}
