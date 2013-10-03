package com.ardais.bigr.es.beans;

public class ArdaisaccountKey implements java.io.Serializable {
	public java.lang.String ardais_acct_key;
	final static long serialVersionUID = 3206093459760846163L;

/**
 * Default constructor
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public ArdaisaccountKey() {
	super();
}
/**
 * Initialize a key from the passed values
 * @param argArdais_acct_key java.lang.String
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public ArdaisaccountKey(java.lang.String argArdais_acct_key) {
	ardais_acct_key = argArdais_acct_key;
}
/**
 * equals method
 * @return boolean
 * @param o java.lang.Object
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public boolean equals(Object o) {
	if (o instanceof ArdaisaccountKey) {
		ArdaisaccountKey otherKey = (ArdaisaccountKey) o;
		return ((this.ardais_acct_key.equals(otherKey.ardais_acct_key)));
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
	return (ardais_acct_key.hashCode());
}
}
