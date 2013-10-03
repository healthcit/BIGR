package com.ardais.bigr.iltds.beans;

public class SampleboxKey implements java.io.Serializable {
	public java.lang.String box_barcode_id;
	final static long serialVersionUID = 3206093459760846163L;

/**
 * Default constructor
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public SampleboxKey() {
	super();
}
/**
 * Initialize a key from the passed values
 * @param argBox_barcode_id java.lang.String
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public SampleboxKey(java.lang.String argBox_barcode_id) {
	box_barcode_id = argBox_barcode_id;
}
/**
 * equals method
 * @return boolean
 * @param o java.lang.Object
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public boolean equals(Object o) {
	if (o instanceof SampleboxKey) {
		SampleboxKey otherKey = (SampleboxKey) o;
		return ((this.box_barcode_id.equals(otherKey.box_barcode_id)));
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
	return (box_barcode_id.hashCode());
}
}
