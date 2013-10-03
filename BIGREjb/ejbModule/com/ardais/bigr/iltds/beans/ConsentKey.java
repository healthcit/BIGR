package com.ardais.bigr.iltds.beans;

public class ConsentKey implements java.io.Serializable {
	public java.lang.String consent_id;
	final static long serialVersionUID = 3206093459760846163L;

/**
 * Default constructor
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public ConsentKey() {
	super();
}
	/**
	 * Creates a key for Entity Bean: Consent
	 */
	public ConsentKey(java.lang.String consent_id) {
		this.consent_id = consent_id;
	}
	/**
	 * Returns true if both keys are equal.
	 */
	public boolean equals(java.lang.Object otherKey) {
		if (otherKey instanceof com.ardais.bigr.iltds.beans.ConsentKey) {
			com.ardais.bigr.iltds.beans.ConsentKey o =
				(com.ardais.bigr.iltds.beans.ConsentKey) otherKey;
			return ((this.consent_id.equals(o.consent_id)));
		}
		return false;
	}
	/**
	 * Returns the hash code for the key.
	 */
	public int hashCode() {
		return (consent_id.hashCode());
	}
}
