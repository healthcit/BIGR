package com.ardais.bigr.iltds.beans;

public class ArdaisstaffKey implements java.io.Serializable {
	public java.lang.String ardais_staff_id;
	final static long serialVersionUID = 3206093459760846163L;

/**
 * Default constructor
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public ArdaisstaffKey() {
	super();
}
/**
 * Initialize a key from the passed values
 * @param argArdais_staff_id java.lang.String
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public ArdaisstaffKey(java.lang.String argArdais_staff_id) {
	ardais_staff_id = argArdais_staff_id;
}
/**
 * equals method
 * @return boolean
 * @param o java.lang.Object
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public boolean equals(Object o) {
	if (o instanceof ArdaisstaffKey) {
		ArdaisstaffKey otherKey = (ArdaisstaffKey) o;
		return ((this.ardais_staff_id.equals(otherKey.ardais_staff_id)));
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
	return (ardais_staff_id.hashCode());
}
}
