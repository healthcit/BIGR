package com.ardais.bigr.iltds.helpers;

import java.io.Serializable;

/**
 * Instances of <code>RequestSelect</code> specify what Request details should be
 * returned from a request query.
 * 
 * <p>When you select full box details using the {@link #BOX_INFO_DETAILS} selector code,
 * the query results will include details of the current contents of those boxes, if any.
 * It is important to note that ILTDS_REQUEST_BOX only stores the ids of boxes that
 * the request samples were placed into at the time of fulfilling a request.  These records
 * remain in the database after the request has been fulfilled, but after a certain point
 * in a request's lifecycle (which may depend on the request types), these boxes are no
 * longer needed for the request and may be reused for other purposes in a repository.
 * So, after a certain point, the box contents of these boxes will no longer accurately
 * reflect what was stored in them at the time of fulfilling a request.  Keep this in mind
 * when using the contents represented inside the {@link BoxDto} objects that the query returns.
 * The box contents in the results are the <b>current</b> box contents and may be very different
 * from what was put into these boxes when the request was fulfilled.
 * 
 * @see com.ardais.bigr.iltds.bizlogic.RequestFinder
 * @see RequestFilter
 */
public class RequestSelect implements Serializable {
  /**
   * Specifies to return no information about the items on requests.
   */
  public final static int ITEM_INFO_NONE = 0;
  /**
   * Specifies to return just basic details (id and type) of the items on requests.
   */
  public final static int ITEM_INFO_BASIC = 1;
  /**
   * Specifies to return full details of the items on requests.
   */
  public final static int ITEM_INFO_DETAILS = 2;

  /**
   * Specifies to return no information about the boxes on requests.
   */
  public final static int BOX_INFO_NONE = 0;
  /**
   * Specifies to return just basic details (id and type) of the boxes on requests.
   */
  public final static int BOX_INFO_BASIC = 1;
  /**
   * Specifies to return full details of the boxes on requests, including the box contents.
   * Please see the comments at the beginning of this class for important information
   * about the accuracy of box information in query results in certain situations.
   */
  public final static int BOX_INFO_DETAILS = 2;

  /**
   * A selector that retrieves only the basic details.
   */
  public final static RequestSelect BASIC =
    new RequestSelect(true, true, ITEM_INFO_NONE, BOX_INFO_NONE);
  /**
   * A selector that retrieves the basic details plus the basic details of boxes on the requests.
   */
  public final static RequestSelect BASIC_PLUS_BOX_BASICS =
    new RequestSelect(true, true, ITEM_INFO_NONE, BOX_INFO_BASIC);
  /**
   * A selector that retrieves the basic details plus the basic details of items on the requests.
   */
  public final static RequestSelect BASIC_PLUS_ITEM_BASICS =
    new RequestSelect(true, true, ITEM_INFO_BASIC, BOX_INFO_NONE);
  /**
   * A selector that retrieves ONLY the basic details of items on the requests.  It does not
   * include basic details on the requests themselves except for request id and type.
   */
  public final static RequestSelect ITEM_BASICS_ONLY =
    new RequestSelect(true, false, ITEM_INFO_BASIC, BOX_INFO_NONE);
  /**
   * A selector that retrieves the basic details plus the full details of items on the requests.
   */
  public final static RequestSelect BASIC_PLUS_ITEM_DETAILS =
    new RequestSelect(true, true, ITEM_INFO_DETAILS, BOX_INFO_NONE);
  /**
   * A selector that retrieves ONLY the full details of items on the requests.  It does not
   * include basic details on the requests themselves except for request id and type.
   */
  public final static RequestSelect ITEM_DETAILS_ONLY =
    new RequestSelect(true, false, ITEM_INFO_DETAILS, BOX_INFO_NONE);

  private boolean _immutable = false;
  private boolean _includeBasic = true;
  private int _itemInfo = ITEM_INFO_NONE;
  private int _boxInfo = BOX_INFO_NONE;

  /**
   * Create an instance with default property settings.
   */
  public RequestSelect() {
    super();
  }

  /**
   * Create an instance with the specified property settings.  If the <code>immutable</code>
   * parameter is true, the new instance is made immutable.
   */
  public RequestSelect(boolean immutable, boolean includeBasic, int itemInfo, int boxInfo) {
    this();
    setIncludeBasic(includeBasic);
    setItemInfo(itemInfo);
    setBoxInfo(boxInfo);
    if (immutable) {
      setImmutable();
    }
  }

  /**
   * @return True if this instance is immutable.
   */
  public boolean isImmutable() {
    return _immutable;
  }

  /**
   * @return True if the basic direct request properties should be populated in the query
   *    result objects.  This includes all of the simple properties on
   *    {@link com.ardais.bigr.javabeans.RequestDto} such as id, comments, requester, and so
   *    forth.  It does not include properties that are complex subobjects such as the list
   *    of items that are on the request.   This is true by default.
   *    When this is false, the only basic request properties that will be populated are
   *    request id and type.
   */
  public boolean isIncludeBasic() {
    return _includeBasic;
  }

  /**
   * @return The level of detail to include in the query results about the lists of items
   *   that are on each request.  This will be one of the ITEM_INFO_* constants defined
   *   in this class.  The default value is ITEM_INFO_NONE.
   */
  public int getItemInfo() {
    return _itemInfo;
  }

  /**
   * Please see the comments at the beginning of this class for important information
   * about the accuracy of box information in query results in certain situations.
   * 
   * @return The level of detail to include in the query results about the lists of boxes
   *   that are associated with each request.  This will be one of the BOX_INFO_* constants defined
   *   in this class.  The default value is BOX_INFO_NONE.
   */
  public int getBoxInfo() {
    return _boxInfo;
  }

  /**
   * @return True if this instance includes at least one of the pieces/categories of information
   *   that can be included.
   */
  public boolean isSomethingSelected() {
    return isIncludeBasic() || (getItemInfo() != ITEM_INFO_NONE) || (getBoxInfo() != BOX_INFO_NONE);
  }

  /**
   * Set this instance to be immutable.  Any attempts to modify immutable
   * instances will throw an exception.
   */
  public void setImmutable() {
    _immutable = true;
  }

  /**
   * Throw an exception if this instance is immutable.
   */
  private void checkImmutable() {
    if (_immutable) {
      throw new IllegalStateException("Attempted to modify an immutable instance.");
    }
  }

  /**
   * @param b True if the basic direct request properties should be populated in the query
   *    result objects.  This includes all of the simple properties on
   *    {@link com.ardais.bigr.javabeans.RequestDto} such as id, comments, requester, and so
   *    forth.  It does not include properties that are complex subobjects such as the list
   *    of items that are on the request.   This is true by default.
   *    When this is false, the only basic request properties that will be populated are
   *    request id and type.
   */
  public void setIncludeBasic(boolean b) {
    checkImmutable();
    _includeBasic = b;
  }

  /**
   * @param code The level of detail to include in the query results about the lists of items
   *   that are on each request.  This must be one of the ITEM_INFO_* constants defined
   *   in this class.  The default value is ITEM_INFO_NONE.
   */
  public void setItemInfo(int code) {
    checkImmutable();
    if (code != ITEM_INFO_NONE && code != ITEM_INFO_BASIC && code != ITEM_INFO_DETAILS) {
      throw new IllegalArgumentException("Invalid itemInfo code: " + code);
    }
    _itemInfo = code;
  }

  /**
   * Please see the comments at the beginning of this class for important information
   * about the accuracy of box information in query results in certain situations.
   * 
   * @param code The level of detail to include in the query results about the lists of boxes
   *   that are associated with each request.  This will be one of the BOX_INFO_* constants defined
   *   in this class.  The default value is BOX_INFO_NONE.
   */
  public void setBoxInfo(int code) {
    checkImmutable();
    if (code != BOX_INFO_NONE && code != BOX_INFO_BASIC && code != BOX_INFO_DETAILS) {
      throw new IllegalArgumentException("Invalid boxInfo code: " + code);
    }
    _boxInfo = code;
  }

}
