package com.ardais.bigr.es.beans;
/**
 * Key class for Entity Bean: Shoppingcartdetail
 */
public class ShoppingcartdetailKey implements java.io.Serializable {
	static final long serialVersionUID = 3206093459760846163L;
	/**
	 * Implementation field for persistent attribute: shopping_cart_line_number
	 */
	public java.math.BigDecimal shopping_cart_line_number;
  /**
   * Implementation field for persistent attribute: shoppingcart_shopping_cart_id
   */
  public java.math.BigDecimal shoppingcart_shopping_cart_id;
  /**
   * Implementation field for persistent attribute: shoppingcart_ardaisuser_ardais_user_id
   */
  public java.lang.String shoppingcart_ardaisuser_ardais_user_id;
  /**
   * Implementation field for persistent attribute: shoppingcart_ardaisuser_ardaisaccount_ardais_acct_key
   */
  public java.lang.String shoppingcart_ardaisuser_ardaisaccount_ardais_acct_key;
	/**
	 * Creates an empty key for Entity Bean: Shoppingcartdetail
	 */
	public ShoppingcartdetailKey() {
	}
  /**
   * Creates a key for Entity Bean: Shoppingcartdetail
   */
  public ShoppingcartdetailKey(
    java.math.BigDecimal shopping_cart_line_number,
    com.ardais.bigr.es.beans.ShoppingcartKey argShoppingcart) {
    this.shopping_cart_line_number = shopping_cart_line_number;
    privateSetShoppingcartKey(argShoppingcart);
  }
  /**
   * Returns true if both keys are equal.
   */
  public boolean equals(java.lang.Object otherKey) {
    if (otherKey instanceof com.ardais.bigr.es.beans.ShoppingcartdetailKey) {
      com.ardais.bigr.es.beans.ShoppingcartdetailKey o =
        (com.ardais.bigr.es.beans.ShoppingcartdetailKey) otherKey;
      return (
        (this.shopping_cart_line_number.equals(o.shopping_cart_line_number))
          && (this.shoppingcart_shopping_cart_id.equals(o.shoppingcart_shopping_cart_id))
          && (this
            .shoppingcart_ardaisuser_ardais_user_id
            .equals(o.shoppingcart_ardaisuser_ardais_user_id))
          && (this
            .shoppingcart_ardaisuser_ardaisaccount_ardais_acct_key
            .equals(o.shoppingcart_ardaisuser_ardaisaccount_ardais_acct_key)));
    }
    return false;
  }
  /**
   * Returns the hash code for the key.
   */
  public int hashCode() {
    return (
      shopping_cart_line_number.hashCode()
        + shoppingcart_shopping_cart_id.hashCode()
        + shoppingcart_ardaisuser_ardais_user_id.hashCode()
        + shoppingcart_ardaisuser_ardaisaccount_ardais_acct_key.hashCode());
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
}
