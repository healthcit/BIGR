package com.ardais.bigr.es.beans;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.ardais.bigr.es.helpers.FormLogic;

/**
 * Bean implementation class for Enterprise Bean: Shoppingcartdetail
 */
public class ShoppingcartdetailBean implements javax.ejb.EntityBean {
  public static final BigDecimal DEFAULT_shopping_cart_line_number = null;
  public static final BigDecimal DEFAULT_shopping_cart_line_amount = null;
  public static final String DEFAULT_barcode_id = null;
  public static final Integer DEFAULT_quantity = null;
  public static final String DEFAULT_productType = "";
  public static final String DEFAULT_search_desc = null;
  public static final Timestamp DEFAULT_creation_dt = null;
  public static final BigDecimal DEFAULT_shoppingcart_shopping_cart_id = null;
  public static final String DEFAULT_shoppingcart_ardaisuser_ardais_user_id = null;
  public static final String DEFAULT_shoppingcart_ardaisuser_ardaisaccount_ardais_acct_key = null;

  private javax.ejb.EntityContext myEntityCtx;
  /**
   * Implementation field for persistent attribute: shopping_cart_line_number
   */
  public java.math.BigDecimal shopping_cart_line_number;
  /**
   * Implementation field for persistent attribute: shopping_cart_line_amount
   */
  public java.math.BigDecimal shopping_cart_line_amount;
  /**
   * Implementation field for persistent attribute: barcode_id
   */
  public java.lang.String barcode_id;
  /**
   * Implementation field for persistent attribute: search_desc
   */
  public java.lang.String search_desc;
  /**
   * Implementation field for persistent attribute: creation_dt
   */
  public java.sql.Timestamp creation_dt;
  /**
   * Implementation field for persistent attribute: shoppingcart_shopping_cart_id
   */
  public java.math.BigDecimal shoppingcart_shopping_cart_id;
  /**
   * Implementation field for persistent attribute: shoppingcart_ardaisuser_ardais_user_id
   */
  public java.lang.String shoppingcart_ardaisuser_ardais_user_id;
  private transient com.ibm.ivj.ejb.associations.interfaces.SingleLink shoppingcartLink;
  /**
   * Implementation field for persistent attribute: shoppingcart_ardaisuser_ardaisaccount_ardais_acct_key
   */
  public java.lang.String shoppingcart_ardaisuser_ardaisaccount_ardais_acct_key;
  /**
   * Implementation field for persistent attribute: quantity
   */
  public java.lang.Integer quantity;
  /**
   * Implementation field for persistent attribute: productType
   */
  public java.lang.String productType = DEFAULT_productType;

  private static final com.ardais.bigr.api.CMPDefaultValues _fieldDefaultValues =
    new com.ardais.bigr.api.CMPDefaultValues(com.ardais.bigr.es.beans.ShoppingcartdetailBean.class);

  /**
   * getEntityContext
   */
  public javax.ejb.EntityContext getEntityContext() {
    return myEntityCtx;
  }
  /**
   * setEntityContext
   */
  public void setEntityContext(javax.ejb.EntityContext ctx) {
    myEntityCtx = ctx;
  }
  /**
   * unsetEntityContext
   */
  public void unsetEntityContext() {
    myEntityCtx = null;
  }
  /**
   * ejbActivate
   */
  public void ejbActivate() {
    _initLinks();
  }
  /**
   * ejbCreate method for a CMP entity bean.
   */
  public com.ardais.bigr.es.beans.ShoppingcartdetailKey ejbCreate(
    java.math.BigDecimal shopping_cart_line_number,
    com.ardais.bigr.es.beans.Shoppingcart argShoppingcart)
    throws javax.ejb.CreateException {
    _fieldDefaultValues.assignTo(this);
    _initLinks();
    this.shopping_cart_line_number = shopping_cart_line_number;
    try {
      setShoppingcart(argShoppingcart);
    }
    catch (java.rmi.RemoteException remoteEx) {
      throw new javax.ejb.CreateException(remoteEx.getMessage());
    }
    return null;
  }
  /**
     * ejbCreate method for a CMP entity bean.
     */
  public com.ardais.bigr.es.beans.ShoppingcartdetailKey ejbCreate(
    String barcode_id,
    String product_type,
    java.math.BigDecimal shopping_cart_line_number,
    String userId,
    String acctId,
    BigDecimal cartNo)
    throws javax.ejb.CreateException {
    _fieldDefaultValues.assignTo(this);
    _initLinks();
    this.shopping_cart_line_number = shopping_cart_line_number;
    this.shoppingcart_ardaisuser_ardais_user_id = userId;
    this.shoppingcart_ardaisuser_ardaisaccount_ardais_acct_key = acctId;
    this.shoppingcart_shopping_cart_id = cartNo;
    this.barcode_id = barcode_id;
    this.productType = product_type;

    return null;
  }

  public void ejbPostCreate(
    String barcode_id,
    String product_type,
    java.math.BigDecimal shopping_cart_line_number,
    String userId,
    String acctId,
    BigDecimal cartNo)
    throws javax.ejb.CreateException {
  }

  /**
   * ejbLoad
   */
  public void ejbLoad() {
    _initLinks();
    productType = DEFAULT_productType;
  }
  /**
   * ejbPassivate
   */
  public void ejbPassivate() {
  }
  /**
   * ejbPostCreate
   */
  public void ejbPostCreate(
    java.math.BigDecimal shopping_cart_line_number,
    com.ardais.bigr.es.beans.Shoppingcart argShoppingcart)
    throws javax.ejb.CreateException {
    try {
      setShoppingcart(argShoppingcart);
    }
    catch (java.rmi.RemoteException remoteEx) {
      throw new javax.ejb.CreateException(remoteEx.getMessage());
    }
  }
  /**
   * ejbRemove
   */
  public void ejbRemove() throws javax.ejb.RemoveException {
    try {
      _removeLinks();
    }
    catch (java.rmi.RemoteException e) {
      throw new javax.ejb.RemoveException(e.getMessage());
    }
  }
  /**
   * ejbStore
   */
  public void ejbStore() {
    // the Product Type is only for populating the DB.  It is "write only."
    // in the Java code, we determine the product type from the 
    // first two letters of the sample ID and don't use an explicit
    // product type field
    if (barcode_id == null)
      return;
    productType =
      barcode_id.startsWith("RN") ? FormLogic.RNA_PRODUCT_TYPE : FormLogic.TISSUE_PRODUCT_TYPE;
  }
  /**
   * This method was generated for supporting the associations.
   */
  protected void _initLinks() {
    shoppingcartLink = null;
  }
  /**
   * This method was generated for supporting the associations.
   */
  protected java.util.Vector _getLinks() {
    java.util.Vector links = new java.util.Vector();
    links.add(getShoppingcartLink());
    return links;
  }
  /**
   * This method was generated for supporting the associations.
   */
  protected void _removeLinks() throws java.rmi.RemoteException, javax.ejb.RemoveException {
    java.util.List links = _getLinks();
    for (int i = 0; i < links.size(); i++) {
      try {
        ((com.ibm.ivj.ejb.associations.interfaces.Link) links.get(i)).remove();
      }
      catch (javax.ejb.FinderException e) {
      } //Consume Finder error since I am going away
    }
  }
  /**
   * Get accessor for persistent attribute: shopping_cart_line_amount
   */
  public java.math.BigDecimal getShopping_cart_line_amount() {
    return shopping_cart_line_amount;
  }
  /**
   * Set accessor for persistent attribute: shopping_cart_line_amount
   */
  public void setShopping_cart_line_amount(java.math.BigDecimal newShopping_cart_line_amount) {
    shopping_cart_line_amount = newShopping_cart_line_amount;
  }
  /**
   * Get accessor for persistent attribute: barcode_id
   */
  public java.lang.String getBarcode_id() {
    return barcode_id;
  }
  /**
   * Set accessor for persistent attribute: barcode_id
   */
  public void setBarcode_id(java.lang.String newBarcode_id) {
    barcode_id = newBarcode_id;
  }
  /**
   * Get accessor for persistent attribute: search_desc
   */
  public java.lang.String getSearch_desc() {
    return search_desc;
  }
  /**
   * Set accessor for persistent attribute: search_desc
   */
  public void setSearch_desc(java.lang.String newSearch_desc) {
    search_desc = newSearch_desc;
  }
  /**
   * Get accessor for persistent attribute: creation_dt
   */
  public java.sql.Timestamp getCreation_dt() {
    return creation_dt;
  }
  /**
   * Set accessor for persistent attribute: creation_dt
   */
  public void setCreation_dt(java.sql.Timestamp newCreation_dt) {
    creation_dt = newCreation_dt;
  }
  /**
   * This method was generated for supporting the relationship role named shoppingcart.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void setShoppingcart(com.ardais.bigr.es.beans.Shoppingcart aShoppingcart)
    throws java.rmi.RemoteException {
    this.getShoppingcartLink().set(aShoppingcart);
  }
  /**
   * This method was generated for supporting the relationship role named shoppingcart.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  protected com.ibm.ivj.ejb.associations.interfaces.SingleLink getShoppingcartLink() {
    if (shoppingcartLink == null)
      shoppingcartLink = new ShoppingcartdetailToShoppingcartLink(this);
    return shoppingcartLink;
  }
  /**
   * This method was generated for supporting the relationship role named shoppingcart.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public com.ardais.bigr.es.beans.ShoppingcartKey getShoppingcartKey() {
    com.ardais.bigr.es.beans.ShoppingcartKey temp = new com.ardais.bigr.es.beans.ShoppingcartKey();
    boolean shoppingcart_NULLTEST = true;
    shoppingcart_NULLTEST &= (shoppingcart_shopping_cart_id == null);
    temp.shopping_cart_id = shoppingcart_shopping_cart_id;
    shoppingcart_NULLTEST &= (shoppingcart_ardaisuser_ardais_user_id == null);
    temp.ardaisuser_ardais_user_id = shoppingcart_ardaisuser_ardais_user_id;
    shoppingcart_NULLTEST &= (shoppingcart_ardaisuser_ardaisaccount_ardais_acct_key == null);
    temp.ardaisuser_ardaisaccount_ardais_acct_key =
      shoppingcart_ardaisuser_ardaisaccount_ardais_acct_key;
    if (shoppingcart_NULLTEST)
      temp = null;
    return temp;
  }
  /**
   * This method was generated for supporting the relationship role named shoppingcart.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void privateSetShoppingcartKey(com.ardais.bigr.es.beans.ShoppingcartKey inKey) {
    boolean shoppingcart_NULLTEST = (inKey == null);
    shoppingcart_shopping_cart_id = (shoppingcart_NULLTEST) ? null : inKey.shopping_cart_id;
    shoppingcart_ardaisuser_ardais_user_id =
      (shoppingcart_NULLTEST) ? null : inKey.ardaisuser_ardais_user_id;
    shoppingcart_ardaisuser_ardaisaccount_ardais_acct_key =
      (shoppingcart_NULLTEST) ? null : inKey.ardaisuser_ardaisaccount_ardais_acct_key;
  }
  /**
   * This method was generated for supporting the relationship role named shoppingcart.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public com.ardais.bigr.es.beans.Shoppingcart getShoppingcart()
    throws java.rmi.RemoteException, javax.ejb.FinderException {
    return (com.ardais.bigr.es.beans.Shoppingcart) this.getShoppingcartLink().value();
  }
  /**
   * Get accessor for persistent attribute: quantity
   */
  public java.lang.Integer getQuantity() {
    return quantity;
  }
  /**
   * Set accessor for persistent attribute: quantity
   */
  public void setQuantity(java.lang.Integer newQuantity) {
    quantity = newQuantity;
  }
  /**
   * Get accessor for persistent attribute: productType
   */
  public java.lang.String getProductType() {
    return productType;
  }
  /**
   * Set accessor for persistent attribute: productType
   */
  public void setProductType(java.lang.String newProductType) {
    productType = newProductType;
  }
  /**
   * _copyFromEJB
   */
  public java.util.Hashtable _copyFromEJB() {
    com.ibm.ivj.ejb.runtime.AccessBeanHashtable h = new com.ibm.ivj.ejb.runtime.AccessBeanHashtable();

    h.put("barcode_id", getBarcode_id());
    h.put("quantity", getQuantity());
    h.put("shoppingcartKey", getShoppingcartKey());
    h.put("productType", getProductType());
    h.put("shopping_cart_line_amount", getShopping_cart_line_amount());
    h.put("creation_dt", getCreation_dt());
    h.put("search_desc", getSearch_desc());
    h.put("__Key", getEntityContext().getPrimaryKey());

    return h;
  }
  /**
   * _copyToEJB
   */
  public void _copyToEJB(java.util.Hashtable h) {
    java.lang.String localBarcode_id = (java.lang.String) h.get("barcode_id");
    java.lang.Integer localQuantity = (java.lang.Integer) h.get("quantity");
    java.lang.String localProductType = (java.lang.String) h.get("productType");
    java.math.BigDecimal localShopping_cart_line_amount = (java.math.BigDecimal) h
      .get("shopping_cart_line_amount");
    java.sql.Timestamp localCreation_dt = (java.sql.Timestamp) h.get("creation_dt");
    java.lang.String localSearch_desc = (java.lang.String) h.get("search_desc");

    if (h.containsKey("barcode_id"))
      setBarcode_id((localBarcode_id));
    if (h.containsKey("quantity"))
      setQuantity((localQuantity));
    if (h.containsKey("productType"))
      setProductType((localProductType));
    if (h.containsKey("shopping_cart_line_amount"))
      setShopping_cart_line_amount((localShopping_cart_line_amount));
    if (h.containsKey("creation_dt"))
      setCreation_dt((localCreation_dt));
    if (h.containsKey("search_desc"))
      setSearch_desc((localSearch_desc));
  }
}
