package com.ardais.bigr.iltds.beans;

public class WorkorderKey implements java.io.Serializable {
	private final static long serialVersionUID = 3206093459760846163L;
	public java.lang.String workorderid;

/**
 * Default constructor
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public WorkorderKey() {
	super();
}
/**
 * Initialize a key from the passed values
 * @param argWorkorderid java.lang.String
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public WorkorderKey(java.lang.String argWorkorderid) {
	workorderid = argWorkorderid;
}
/**
 * equals method
 * @return boolean
 * @param o java.lang.Object
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public boolean equals(Object o) {
	if (o instanceof WorkorderKey) {
		WorkorderKey otherKey = (WorkorderKey) o;
		return ((this.workorderid.equals(otherKey.workorderid)));
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
	return (workorderid.hashCode());
}
}
