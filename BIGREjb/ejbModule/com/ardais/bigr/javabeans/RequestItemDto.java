package com.ardais.bigr.javabeans;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.helpers.ProductType;
import com.ardais.bigr.util.gen.DbAliases;

/**
 * @author jesielionis
 *
 * This represents an item in a request.  This isn't a first-class object, request items
 * only exist as part of a {@link RequestDto}.  Request items are stored in the
 * database in ILTDS_REQUEST_ITEM.
 */
public class RequestItemDto implements Serializable {
  static final long serialVersionUID = -3740859742519469093L;

  // We allow the item id and item type to be stored independently from from the
  // ProductDto that represents the item because it is sometimes useful to be able
  // to just specify the id and/or type without creating a fullblown ProductDto object.
  // If _itemData is set to a non-null value, we ignore any value that may have been
  // explicitly set into either _itemId or _itemType and return the actual item id and type
  // from the itemData instead.
  // 
  private String _itemId;
  private ProductType _itemType;
  private ProductDto _itemData;

  /**
   * @return the item.
   */
  public ProductDto getItem() {
    return _itemData;
  }

  /**
   * @return the item id.  If {@link #getItem()} is non-null, this returns that ProductDto's
   * id and ignores any value that may have been supplied by calling the
   * {@link #setItemId(String)} method.
   */
  public String getItemId() {
    ProductDto item = getItem();
    return ((item == null) ? _itemId : item.getId());
  }

  /**
   * @return the item type.  If {@link #getItem()} is non-null, this returns that ProductDto's
   * type and ignores any value that may have been supplied by calling the
   * {@link #setItemType(ProductType)} method.
   */
  public ProductType getItemType() {
    ProductDto item = getItem();
    return ((item == null) ? _itemType : item.getType());
  }

  /**
   * @param data the item.
   */
  public void setItem(ProductDto data) {
    _itemData = data;
  }

  /**
   * @param string the item id.  This is ignored by {@link #getItemId()} when
   *   {@link #getItem()} returns a non-null value.  This does NOT change the id
   *   of the object returned by {@link #getItem()}.
   * @see #getItemId()
   */
  public void setItemId(String string) {
    _itemId = string;
  }

  /**
   * @param string the item type.  This is ignored by {@link #getItemType()} when
   *   {@link #getItem()} returns a non-null value.  This does NOT change the type
   *   of the object returned by {@link #getItem()}.
   * @see #getItemType()
   */
  public void setItemType(ProductType type) {
    _itemType = type;
  }

  /**
   * Populates this <code>RequestBoxDto</code> from the data in the current row
   * of the result set.
   * 
   * @param  columns  a <code>Map</code> containing a key for each column in
   *                   the <code>ResultSet</code>.  Each key must be one of the
   *                   column alias constants in {@link com.ardais.bigr.util.DbAliases}
   *                   and the corresponding value is ignored.
   * @param  rs  the <code>ResultSet</code>
   */
  public void populate(Map columns, ResultSet rs) {
    try {
      if (columns.containsKey(DbAliases.REQUEST_ITEM_ITEM_ID)) {
        setItemId(rs.getString(DbAliases.REQUEST_ITEM_ITEM_ID));
      }
      if (columns.containsKey(DbAliases.REQUEST_ITEM_ITEM_TYPE)) {
        setItemType(ProductType.getInstance(rs.getString(DbAliases.REQUEST_ITEM_ITEM_TYPE)));
      }
    } catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

}
