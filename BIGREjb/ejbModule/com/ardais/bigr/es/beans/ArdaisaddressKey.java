package com.ardais.bigr.es.beans;

public class ArdaisaddressKey implements java.io.Serializable {
	public java.math.BigDecimal address_id;
	public java.lang.String ardais_acct_key;
	final static long serialVersionUID = 3206093459760846163L;

/**
 * Default constructor
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public ArdaisaddressKey() {
	super();
}
/**
 * Initialize a key from the passed values
 * @param argAddress_id java.math.BigDecimal
 * @param argArdais_acct_key java.lang.String
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public ArdaisaddressKey(java.math.BigDecimal argAddress_id, java.lang.String argArdais_acct_key) {
	address_id = argAddress_id;
	ardais_acct_key = argArdais_acct_key;
}
/**
 * equals method
 * @return boolean
 * @param o java.lang.Object
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public boolean equals(Object o) {
	if (o instanceof ArdaisaddressKey) {
		ArdaisaddressKey otherKey = (ArdaisaddressKey) o;
		return ((this.address_id.equals(otherKey.address_id)
		 && this.ardais_acct_key.equals(otherKey.ardais_acct_key)));
	}
	else
		return false;
}
/**
 * hashCode method
 * @return int
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public int hashCode() {
	return (address_id.hashCode()
		 + ardais_acct_key.hashCode());
}
}
