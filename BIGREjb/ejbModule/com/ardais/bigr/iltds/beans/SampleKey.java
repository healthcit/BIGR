package com.ardais.bigr.iltds.beans;

public class SampleKey implements java.io.Serializable {
	public java.lang.String sample_barcode_id;
	private final static long serialVersionUID = 3206093459760846163L;
/**
 * Default constructor
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public SampleKey() {
	super();
}
/**
 * Initialize a key from the passed values
 * @param argSample_barcode_id java.lang.String
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public SampleKey(java.lang.String argSample_barcode_id) {
	sample_barcode_id = argSample_barcode_id;
}
/**
 * equals method
 * @return boolean
 * @param o java.lang.Object
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public boolean equals(java.lang.Object o) {
	if (o instanceof SampleKey) {
		SampleKey otherKey = (SampleKey) o;
		return ((this.sample_barcode_id.equals(otherKey.sample_barcode_id)));
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
	return (sample_barcode_id.hashCode());
}
}
