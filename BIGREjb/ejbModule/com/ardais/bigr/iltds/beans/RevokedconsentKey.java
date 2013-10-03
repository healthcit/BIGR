package com.ardais.bigr.iltds.beans;

public class RevokedconsentKey implements java.io.Serializable {
	public java.lang.String consent_id;
	private final static long serialVersionUID = 3206093459760846163L;
	public java.lang.String ardais_id;
/**
 * Default constructor
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public RevokedconsentKey() {
	super();
}
/**
 * Initialize a key from the passed values
 * @param argConsent_id java.lang.String
 * @param argArdais_id java.lang.String
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public RevokedconsentKey(java.lang.String argConsent_id, java.lang.String argArdais_id) {
	consent_id = argConsent_id;
	ardais_id = argArdais_id;
}
/**
 * equals method
 * @return boolean
 * @param o java.lang.Object
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public boolean equals(java.lang.Object o) {
	if (o instanceof RevokedconsentKey) {
		RevokedconsentKey otherKey = (RevokedconsentKey) o;
		return ((this.consent_id.equals(otherKey.consent_id)
		 && this.ardais_id.equals(otherKey.ardais_id)));
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
	return (consent_id.hashCode()
		 + ardais_id.hashCode());
}
}
