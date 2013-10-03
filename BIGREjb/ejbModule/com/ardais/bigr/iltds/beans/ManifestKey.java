package com.ardais.bigr.iltds.beans;

public class ManifestKey implements java.io.Serializable {
	public java.lang.String manifest_number;
	final static long serialVersionUID = 3206093459760846163L;

/**
 * Default constructor
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public ManifestKey() {
	super();
}
/**
 * Initialize a key from the passed values
 * @param argManifest_number java.lang.String
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public ManifestKey(java.lang.String argManifest_number) {
	manifest_number = argManifest_number;
}
/**
 * equals method
 * @return boolean
 * @param o java.lang.Object
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public boolean equals(Object o) {
	if (o instanceof ManifestKey) {
		ManifestKey otherKey = (ManifestKey) o;
		return ((this.manifest_number.equals(otherKey.manifest_number)));
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
	return (manifest_number.hashCode());
}
}
