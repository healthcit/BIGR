package com.ardais.bigr.iltds.beans;

public class ProjectKey implements java.io.Serializable {
	public java.lang.String projectid;
	private final static long serialVersionUID = 3206093459760846163L;

/**
 * Default constructor
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public ProjectKey() {
	super();
}
/**
 * Initialize a key from the passed values
 * @param argProjectid java.lang.String
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public ProjectKey(java.lang.String argProjectid) {
	projectid = argProjectid;
}
/**
 * equals method
 * @return boolean
 * @param o java.lang.Object
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public boolean equals(Object o) {
	if (o instanceof ProjectKey) {
		ProjectKey otherKey = (ProjectKey) o;
		return ((this.projectid.equals(otherKey.projectid)));
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
	return (projectid.hashCode());
}
}
