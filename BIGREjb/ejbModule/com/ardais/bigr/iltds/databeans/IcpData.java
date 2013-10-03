package com.ardais.bigr.iltds.databeans;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * <code>IcpHelper</code> aids in the generation of pages that display ICP information.
 */
public class IcpData implements Serializable {
  private String _id = null;
  private String _type = null;
  private Object _data = null;
  private boolean _showHistoryOnly = false;
  private boolean _printerFriendly = false;
  private List _history = null;

  public IcpData() {
  }

  /**
   * @return The ICP object data.  The type of object varies depending on the ICP object type.
   */
  public Object getData() {
    return _data;
  }

  /**
   * Return the transaction history list for this ICP item.  Because some history events are
   * filtered out depending on who the current user is, this may not be the complete
   * transaction history for this item.
   * 
   * @return The history items, as a list of
   *   {@link com.ardais.bigr.iltds.btx.BTXHistoryDisplayLine} objects.  This will never return
   *   null, even if setHistory(null) has been called -- it will return an empty list in that case.
   *   Also, the returned list is unmodifiable.
   */
  public List getHistory() {
    return ((_history == null) ? Collections.EMPTY_LIST : Collections.unmodifiableList(_history));
  }

  /**
   * @return The id of the ICP object.
   */
  public String getId() {
    return _id;
  }

  /**
   * @return True if we should show only history information and omit the usual header and
   *     detail inforation for this item.
   */
  public boolean isShowHistoryOnly() {
    return _showHistoryOnly;
  }

  /**
   * @return The type of ICP object.  This must be one of the type constants from
   *     {@link com.ardais.bigr.iltds.helpers.TypeFinder TypeFinder}.
   */
  public String getType() {
    return _type;
  }

  /**
   * @return Returns the printerFriendly.
   */
  public boolean isPrinterFriendly() {
    return _printerFriendly;
  }

  /**
   * Set the ICP object data.
   * 
   * @param object The ICP object data.  The type of object varies depending on the ICP
   *     object type.
   */
  public void setData(Object data) {
    _data = data;
  }

  /**
   * Set the transaction history list for this ICP item.  Because some history events are
   * filtered out depending on who the current user is, this may not be the complete
   * transaction history for this item.
   * 
   * @param list The history items, as a list of
   *   {@link com.ardais.bigr.iltds.btx.BTXHistoryDisplayLine} objects.
   */
  public void setHistory(List list) {
    _history = list;
  }

  /**
   * Set the ICP object id.
   * 
   * @param id The id of the ICP object.
   */
  public void setId(String id) {
    _id = id;
  }

  /**
   * Set to true to indicate that we should show only history information and omit the usual
   * header and detail inforation for this item.
   * 
   * @param b The new setting.
   */
  public void setShowHistoryOnly(boolean b) {
    _showHistoryOnly = b;
  }

  /**
   * Set the ICP object type code.
   * 
   * @param type The type of ICP object.  This must be one of the type constants from
   *     {@link com.ardais.bigr.iltds.helpers.TypeFinder TypeFinder}.
   */
  public void setType(String type) {
    _type = type;
  }

  /**
   * @param printerFriendly The printerFriendly to set.
   */
  public void setPrinterFriendly(boolean printerFriendly) {
    _printerFriendly = printerFriendly;
  }
}
