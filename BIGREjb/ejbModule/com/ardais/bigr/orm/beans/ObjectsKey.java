package com.ardais.bigr.orm.beans;

public class ObjectsKey implements java.io.Serializable {
	public java.lang.String object_id;
	final static long serialVersionUID = 3206093459760846163L;

/**
 * Default constructor
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public ObjectsKey() {
	super();
}
/**
 * Initialize a key from the passed values
 * @param argObject_id java.lang.String
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public ObjectsKey(java.lang.String argObject_id) {
	object_id = argObject_id;
}
/**
 * equals method
 * @return boolean
 * @param o java.lang.Object
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public boolean equals(Object o) {
	if (o instanceof ObjectsKey) {
		ObjectsKey otherKey = (ObjectsKey) o;
		return ((this.object_id.equals(otherKey.object_id)));
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
	return (object_id.hashCode());
}
}
