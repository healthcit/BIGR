package com.ardais.bigr.es.beans;

public class ArdaisorderKey implements java.io.Serializable {
	public java.math.BigDecimal order_number;
	private final static long serialVersionUID = 3206093459760846163L;
/**
 * Default constructor
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public ArdaisorderKey() {
	super();
}
  /**
   * Creates a key for Entity Bean: Ardaisorder
   */
  public ArdaisorderKey(java.math.BigDecimal order_number) {
    this.order_number = order_number;
  }
/**
 * Initialize a key from the passed values
 * @param argOrder_number java.math.BigDecimal
 * @param argArdaisuser com.ardais.bigr.es.beans.ArdaisuserKey
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public ArdaisorderKey(java.math.BigDecimal argOrder_number, com.ardais.bigr.es.beans.ArdaisuserKey argArdaisuser) {
	order_number = argOrder_number;
}
  /**
   * Returns true if both keys are equal.
   */
  public boolean equals(java.lang.Object otherKey) {
    if (otherKey instanceof com.ardais.bigr.es.beans.ArdaisorderKey) {
      com.ardais.bigr.es.beans.ArdaisorderKey o =
        (com.ardais.bigr.es.beans.ArdaisorderKey) otherKey;
      return ((this.order_number.equals(o.order_number)));
    }
    return false;
  }
  /**
   * Returns the hash code for the key.
   */
  public int hashCode() {
    return (order_number.hashCode());
  }
}
