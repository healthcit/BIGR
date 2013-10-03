package com.ardais.bigr.iltds.beans;

public class SamplestatusKey implements java.io.Serializable {
	private final static long serialVersionUID = 3206093459760846163L;
	/**
	 * Implementation field for persistent attribute: id
	 */
	public java.math.BigDecimal id;
	/**
	 * Returns true if both keys are equal.
	 */
	public boolean equals(java.lang.Object otherKey) {
		if (otherKey instanceof com.ardais.bigr.iltds.beans.SamplestatusKey) {
			com.ardais.bigr.iltds.beans.SamplestatusKey o =
				(com.ardais.bigr.iltds.beans.SamplestatusKey) otherKey;
			return ((this.id.equals(o.id)));
		}
		return false;
	}
	/**
	 * Returns the hash code for the key.
	 */
	public int hashCode() {
		return (id.hashCode());
	}
	/**
	 * Creates a key for Entity Bean: Samplestatus
	 */
	public SamplestatusKey() {
	}
	/**
	 * Creates a key for Entity Bean: Samplestatus
	 */
	public SamplestatusKey(java.math.BigDecimal id) {
		this.id = id;
	}
}
