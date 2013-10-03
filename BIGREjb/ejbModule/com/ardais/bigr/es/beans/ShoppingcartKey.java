package com.ardais.bigr.es.beans;
/**
 * Key class for Entity Bean: Shoppingcart
 */
public class ShoppingcartKey implements java.io.Serializable {
	static final long serialVersionUID = 3206093459760846163L;
	/**
	 * Implementation field for persistent attribute: shopping_cart_id
	 */
	public java.math.BigDecimal shopping_cart_id;
  /**
   * Implementation field for persistent attribute: ardaisuser_ardais_user_id
   */
  public java.lang.String ardaisuser_ardais_user_id;
  /**
   * Implementation field for persistent attribute: ardaisuser_ardaisaccount_ardais_acct_key
   */
  public java.lang.String ardaisuser_ardaisaccount_ardais_acct_key;
	/**
	 * Creates an empty key for Entity Bean: Shoppingcart
	 */
	public ShoppingcartKey() {
	}
  /**
   * Creates a key for Entity Bean: Shoppingcart
   */
  public ShoppingcartKey(
    java.math.BigDecimal shopping_cart_id,
    com.ardais.bigr.es.beans.ArdaisuserKey argArdaisuser) {
    this.shopping_cart_id = shopping_cart_id;
    privateSetArdaisuserKey(argArdaisuser);
  }
  /**
   * Returns true if both keys are equal.
   */
  public boolean equals(java.lang.Object otherKey) {
    if (otherKey instanceof com.ardais.bigr.es.beans.ShoppingcartKey) {
      com.ardais.bigr.es.beans.ShoppingcartKey o =
        (com.ardais.bigr.es.beans.ShoppingcartKey) otherKey;
      return (
        (this.shopping_cart_id.equals(o.shopping_cart_id))
          && (this.ardaisuser_ardais_user_id.equals(o.ardaisuser_ardais_user_id))
          && (this
            .ardaisuser_ardaisaccount_ardais_acct_key
            .equals(o.ardaisuser_ardaisaccount_ardais_acct_key)));
    }
    return false;
  }
  /**
   * Returns the hash code for the key.
   */
  public int hashCode() {
    return (
      shopping_cart_id.hashCode()
        + ardaisuser_ardais_user_id.hashCode()
        + ardaisuser_ardaisaccount_ardais_acct_key.hashCode());
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
}
