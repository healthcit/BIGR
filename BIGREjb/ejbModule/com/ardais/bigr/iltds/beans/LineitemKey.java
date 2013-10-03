package com.ardais.bigr.iltds.beans;

public class LineitemKey implements java.io.Serializable {
	public java.lang.String lineitemid;
	private final static long serialVersionUID = 3206093459760846163L;

/**
 * Default constructor
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public LineitemKey() {
	super();
}
/**
 * Initialize a key from the passed values
 * @param argLineitemid java.lang.String
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public LineitemKey(java.lang.String argLineitemid) {
	lineitemid = argLineitemid;
}
/**
 * equals method
 * @return boolean
 * @param o java.lang.Object
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public boolean equals(Object o) {
	if (o instanceof LineitemKey) {
		LineitemKey otherKey = (LineitemKey) o;
		return ((this.lineitemid.equals(otherKey.lineitemid)));
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
	return (lineitemid.hashCode());
}
}
