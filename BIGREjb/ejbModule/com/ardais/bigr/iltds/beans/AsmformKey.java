package com.ardais.bigr.iltds.beans;

public class AsmformKey implements java.io.Serializable {
	public java.lang.String asm_form_id;
	final static long serialVersionUID = 3206093459760846163L;

/**
 * Default constructor
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public AsmformKey() {
	super();
}
	/**
	 * Creates a key for Entity Bean: Asmform
	 */
	public AsmformKey(java.lang.String asm_form_id) {
		this.asm_form_id = asm_form_id;
	}
	/**
	 * Returns true if both keys are equal.
	 */
	public boolean equals(java.lang.Object otherKey) {
		if (otherKey instanceof com.ardais.bigr.iltds.beans.AsmformKey) {
			com.ardais.bigr.iltds.beans.AsmformKey o =
				(com.ardais.bigr.iltds.beans.AsmformKey) otherKey;
			return ((this.asm_form_id.equals(o.asm_form_id)));
		}
		return false;
	}
	/**
	 * Returns the hash code for the key.
	 */
	public int hashCode() {
		return (asm_form_id.hashCode());
	}
}
