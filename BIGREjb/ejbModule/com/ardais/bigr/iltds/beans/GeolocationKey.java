package com.ardais.bigr.iltds.beans;

public class GeolocationKey implements java.io.Serializable {
	public java.lang.String location_address_id;
	final static long serialVersionUID = 3206093459760846163L;

/**
 * Default constructor
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public GeolocationKey() {
	super();
}
/**
 * Initialize a key from the passed values
 * @param argLocation_address_id java.lang.String
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public GeolocationKey(java.lang.String argLocation_address_id) {
	location_address_id = argLocation_address_id;
}
/**
 * equals method
 * @return boolean
 * @param o java.lang.Object
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public boolean equals(Object o) {
	if (o instanceof GeolocationKey) {
		GeolocationKey otherKey = (GeolocationKey) o;
		return ((this.location_address_id.equals(otherKey.location_address_id)));
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
	return (location_address_id.hashCode());
}
}
