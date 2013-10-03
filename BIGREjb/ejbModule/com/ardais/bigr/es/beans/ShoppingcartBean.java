package com.ardais.bigr.es.beans;
/**
 * Bean implementation class for Enterprise Bean: Shoppingcart
 */
public class ShoppingcartBean implements javax.ejb.EntityBean {

  public static final java.math.BigDecimal DEFAULT_shopping_cart_id = null;
  public static final java.sql.Timestamp DEFAULT_last_update_dt = null;
  public static final java.sql.Timestamp DEFAULT_cart_create_date = null;
  public static final String DEFAULT_ardaisuser_ardais_user_id = null;
  public static final String DEFAULT_ardaisuser_ardaisaccount_ardais_acct_key = null;

  private javax.ejb.EntityContext myEntityCtx;
  /**
   * Implementation field for persistent attribute: shopping_cart_id
   */
  public java.math.BigDecimal shopping_cart_id;
  /**
   * Implementation field for persistent attribute: last_update_dt
   */
  public java.sql.Timestamp last_update_dt;
  /**
   * Implementation field for persistent attribute: cart_create_date
   */
  public java.sql.Timestamp cart_create_date;
  /**
   * Implementation field for persistent attribute: ardaisuser_ardais_user_id
   */
  public java.lang.String ardaisuser_ardais_user_id;
  private transient com.ibm.ivj.ejb.associations.interfaces.SingleLink ardaisuserLink;
  private transient com.ibm.ivj.ejb.associations.interfaces.ManyLink shoppingcartdetailLink;
  /**
   * Implementation field for persistent attribute: ardaisuser_ardaisaccount_ardais_acct_key
   */
  public java.lang.String ardaisuser_ardaisaccount_ardais_acct_key;
  private static final com.ardais.bigr.api.CMPDefaultValues _fieldDefaultValues =
    new com.ardais.bigr.api.CMPDefaultValues(com.ardais.bigr.es.beans.ShoppingcartBean.class);

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
  public com.ardais.bigr.es.beans.ShoppingcartKey ejbCreate(
    java.math.BigDecimal shopping_cart_id,
    com.ardais.bigr.es.beans.Ardaisuser argArdaisuser)
    throws javax.ejb.CreateException {
    _fieldDefaultValues.assignTo(this);
    _initLinks();
    this.shopping_cart_id = shopping_cart_id;
    try {
      setArdaisuser(argArdaisuser);
    }
    catch (java.rmi.RemoteException remoteEx) {
      throw new javax.ejb.CreateException(remoteEx.getMessage());
    }
    return null;
  }
  /**
   * ejbCreate method for a CMP entity bean.
   */
  public com.ardais.bigr.es.beans.ShoppingcartKey ejbCreate(
    java.math.BigDecimal shopping_cart_id,
    String userId,
    String acctId)
    throws javax.ejb.CreateException {
    _fieldDefaultValues.assignTo(this);
    _initLinks();
    this.shopping_cart_id = shopping_cart_id;
    this.ardaisuser_ardais_user_id = userId;
    this.ardaisuser_ardaisaccount_ardais_acct_key = acctId;
    return null;
  }
  public void ejbPostCreate(java.math.BigDecimal shopping_cart_id, String userId, String acctId)
    throws javax.ejb.CreateException {
  }
  /**
   * ejbLoad
   */
  public void ejbLoad() {
    _initLinks();
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
    java.math.BigDecimal shopping_cart_id,
    com.ardais.bigr.es.beans.Ardaisuser argArdaisuser)
    throws javax.ejb.CreateException {
    try {
      setArdaisuser(argArdaisuser);
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
  }
  /**
   * This method was generated for supporting the associations.
   */
  protected void _initLinks() {
    ardaisuserLink = null;
    shoppingcartdetailLink = null;
  }
  /**
   * This method was generated for supporting the associations.
   */
  protected java.util.Vector _getLinks() {
    java.util.Vector links = new java.util.Vector();
    links.add(getArdaisuserLink());
    links.add(getShoppingcartdetailLink());
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
   * Get accessor for persistent attribute: last_update_dt
   */
  public java.sql.Timestamp getLast_update_dt() {
    return last_update_dt;
  }
  /**
   * Set accessor for persistent attribute: last_update_dt
   */
  public void setLast_update_dt(java.sql.Timestamp newLast_update_dt) {
    last_update_dt = newLast_update_dt;
  }
  /**
   * Get accessor for persistent attribute: cart_create_date
   */
  public java.sql.Timestamp getCart_create_date() {
    return cart_create_date;
  }
  /**
   * Set accessor for persistent attribute: cart_create_date
   */
  public void setCart_create_date(java.sql.Timestamp newCart_create_date) {
    cart_create_date = newCart_create_date;
  }
  /**
   * This method was generated for supporting the relationship role named ardaisuser.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void setArdaisuser(com.ardais.bigr.es.beans.Ardaisuser anArdaisuser)
    throws java.rmi.RemoteException {
    this.getArdaisuserLink().set(anArdaisuser);
  }
  /**
   * This method was generated for supporting the relationship role named ardaisuser.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  protected com.ibm.ivj.ejb.associations.interfaces.SingleLink getArdaisuserLink() {
    if (ardaisuserLink == null)
      ardaisuserLink = new ShoppingcartToArdaisuserLink(this);
    return ardaisuserLink;
  }
  /**
   * This method was generated for supporting the relationship role named ardaisuser.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public com.ardais.bigr.es.beans.ArdaisuserKey getArdaisuserKey() {
    com.ardais.bigr.es.beans.ArdaisuserKey temp = new com.ardais.bigr.es.beans.ArdaisuserKey();
    boolean ardaisuser_NULLTEST = true;
    ardaisuser_NULLTEST &= (ardaisuser_ardais_user_id == null);
    temp.ardais_user_id = ardaisuser_ardais_user_id;
    ardaisuser_NULLTEST &= (ardaisuser_ardaisaccount_ardais_acct_key == null);
    temp.ardaisaccount_ardais_acct_key = ardaisuser_ardaisaccount_ardais_acct_key;
    if (ardaisuser_NULLTEST)
      temp = null;
    return temp;
  }
  /**
   * This method was generated for supporting the relationship role named ardaisuser.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void privateSetArdaisuserKey(com.ardais.bigr.es.beans.ArdaisuserKey inKey) {
    boolean ardaisuser_NULLTEST = (inKey == null);
    ardaisuser_ardais_user_id = (ardaisuser_NULLTEST) ? null : inKey.ardais_user_id;
    ardaisuser_ardaisaccount_ardais_acct_key =
      (ardaisuser_NULLTEST) ? null : inKey.ardaisaccount_ardais_acct_key;
  }
  /**
   * This method was generated for supporting the relationship role named ardaisuser.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public com.ardais.bigr.es.beans.Ardaisuser getArdaisuser()
    throws java.rmi.RemoteException, javax.ejb.FinderException {
    return (com.ardais.bigr.es.beans.Ardaisuser) this.getArdaisuserLink().value();
  }
  /**
   * This method was generated for supporting the relationship role named shoppingcartdetail.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void secondaryAddShoppingcartdetail(
    com.ardais.bigr.es.beans.Shoppingcartdetail aShoppingcartdetail)
    throws java.rmi.RemoteException {
    this.getShoppingcartdetailLink().secondaryAddElement(aShoppingcartdetail);
  }
  /**
   * This method was generated for supporting the relationship role named shoppingcartdetail.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void secondaryRemoveShoppingcartdetail(
    com.ardais.bigr.es.beans.Shoppingcartdetail aShoppingcartdetail)
    throws java.rmi.RemoteException {
    this.getShoppingcartdetailLink().secondaryRemoveElement(aShoppingcartdetail);
  }
  /**
   * This method was generated for supporting the relationship role named shoppingcartdetail.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  protected com.ibm.ivj.ejb.associations.interfaces.ManyLink getShoppingcartdetailLink() {
    if (shoppingcartdetailLink == null)
      shoppingcartdetailLink = new ShoppingcartToShoppingcartdetailLink(this);
    return shoppingcartdetailLink;
  }
  /**
   * This method was generated for supporting the relationship role named shoppingcartdetail.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public java.util.Enumeration getShoppingcartdetail()
    throws java.rmi.RemoteException, javax.ejb.FinderException {
    return this.getShoppingcartdetailLink().enumerationValue();
  }
  /**
   * _copyFromEJB
   */
  public java.util.Hashtable _copyFromEJB() {
    com.ibm.ivj.ejb.runtime.AccessBeanHashtable h = new com.ibm.ivj.ejb.runtime.AccessBeanHashtable();

    h.put("cart_create_date", getCart_create_date());
    h.put("last_update_dt", getLast_update_dt());
    h.put("ardaisuserKey", getArdaisuserKey());
    h.put("__Key", getEntityContext().getPrimaryKey());

    return h;
  }
  /**
   * _copyToEJB
   */
  public void _copyToEJB(java.util.Hashtable h) {
    java.sql.Timestamp localCart_create_date = (java.sql.Timestamp) h.get("cart_create_date");
    java.sql.Timestamp localLast_update_dt = (java.sql.Timestamp) h.get("last_update_dt");

    if (h.containsKey("cart_create_date"))
      setCart_create_date((localCart_create_date));
    if (h.containsKey("last_update_dt"))
      setLast_update_dt((localLast_update_dt));
  }
}
