package com.ardais.bigr.iltds.beans;

public class AsmKey implements java.io.Serializable {
	public java.lang.String asm_id;
	private final static long serialVersionUID = 3206093459760846163L;
/**
 * Default constructor
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public AsmKey() {
	super();
}
/**
 * Initialize a key from the passed values
 * @param argAsm_id java.lang.String
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public AsmKey(java.lang.String argAsm_id) {
	asm_id = argAsm_id;
}
/**
 * equals method
 * @return boolean
 * @param o java.lang.Object
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public boolean equals(java.lang.Object o) {
	if (o instanceof AsmKey) {
		AsmKey otherKey = (AsmKey) o;
		return ((this.asm_id.equals(otherKey.asm_id)));
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
	return (asm_id.hashCode());
}
}
