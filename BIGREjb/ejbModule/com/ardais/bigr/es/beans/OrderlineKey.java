package com.ardais.bigr.es.beans;

public class OrderlineKey implements java.io.Serializable {
  /**
   * Implementation field for persistent attribute: ardaisorder_order_number
   */
  public java.math.BigDecimal ardaisorder_order_number;
	public java.math.BigDecimal order_line_number;
	private final static long serialVersionUID = 3206093459760846163L;
/**
 * Default constructor
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public OrderlineKey() {
	super();
}
  /**
   * Creates a key for Entity Bean: Orderline
   */
  public OrderlineKey(
    java.math.BigDecimal order_line_number,
    com.ardais.bigr.es.beans.ArdaisorderKey argArdaisorder) {
    this.order_line_number = order_line_number;
    privateSetArdaisorderKey(argArdaisorder);
  }
  /**
   * Returns true if both keys are equal.
   */
  public boolean equals(java.lang.Object otherKey) {
    if (otherKey instanceof com.ardais.bigr.es.beans.OrderlineKey) {
      com.ardais.bigr.es.beans.OrderlineKey o = (com.ardais.bigr.es.beans.OrderlineKey) otherKey;
      return (
        (this.order_line_number.equals(o.order_line_number))
          && (this.ardaisorder_order_number.equals(o.ardaisorder_order_number)));
    }
    return false;
  }
  /**
   * This method was generated for supporting the relationship role named ardaisorder.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public com.ardais.bigr.es.beans.ArdaisorderKey getArdaisorderKey() {
    com.ardais.bigr.es.beans.ArdaisorderKey temp = new com.ardais.bigr.es.beans.ArdaisorderKey();
    boolean ardaisorder_NULLTEST = true;
    ardaisorder_NULLTEST &= (ardaisorder_order_number == null);
    temp.order_number = ardaisorder_order_number;
    if (ardaisorder_NULLTEST)
      temp = null;
    return temp;
  }
  /**
   * Returns the hash code for the key.
   */
  public int hashCode() {
    return (order_line_number.hashCode() + ardaisorder_order_number.hashCode());
  }
  /**
   * This method was generated for supporting the relationship role named ardaisorder.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void privateSetArdaisorderKey(com.ardais.bigr.es.beans.ArdaisorderKey inKey) {
    boolean ardaisorder_NULLTEST = (inKey == null);
    ardaisorder_order_number = (ardaisorder_NULLTEST) ? null : inKey.order_number;
  }
}
