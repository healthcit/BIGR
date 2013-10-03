package com.ardais.bigr.iltds.assistants;

import java.util.*;

/**
 * <code>LegalValueSetHierarchy</code> is a <code>LegalValueSet</code>
 * that can have a sub-<code>LegalValueSet</code> for each legal
 * value.  This can be useful when the selection of a legal value
 * is used to indicate the legal value set for another value.
 */
public class LegalValueSetHierarchy extends LegalValueSet {
//TODO: roll this functionality back into LegalValueSet
//this was made separate for PTS, but shouldn't really be a sub-class.
	private HashMap _subLegalValueSets = new HashMap();
/**
 * Creates a new <code>LegalValueSetHierarchy</code>.
 */
public LegalValueSetHierarchy() {
	super();
}
/**
 * Adds a legal value with just a value and a sub-<code>LegalValueSet</code>
 * to this <code>LegalValueSet</code> at the specified position.
 *
 * @param  index  the zero-based position
 * @param  value  the legal value's value
 * @param  lvs  the sub-<code>LegalValueSet</code>
 */
public void addLegalValue(int index, String value, LegalValueSet lvs) {
	addLegalValue(index, value);
	_subLegalValueSets.put(value, lvs);
}
/**
 * Adds a legal value with a value, display value and a 
 * sub-<code>LegalValueSet</code> to this <code>LegalValueSet</code>
 * at the specified position.
 *
 * @param  index  the zero-based position
 * @param  value  the legal value's value
 * @param  value  the legal value's display value
 * @param  lvs  the sub-<code>LegalValueSet</code>
 */
public void addLegalValue(int index, String value, String display, LegalValueSet lvs) {
	addLegalValue(index, value, display);
	_subLegalValueSets.put(value, lvs);
}
/**
 * Adds a legal value with just a value and a sub-<code>LegalValueSet</code>
 * to this <code>LegalValueSet</code> at the end.
 *
 * @param  value  the legal value's value
 * @param  lvs  the sub-<code>LegalValueSet</code>
 */
public void addLegalValue(String value, LegalValueSet lvs) {
	addLegalValue(value);
	_subLegalValueSets.put(value, lvs);
}
/**
 * Adds a legal value with a value, display value and a 
 * sub-<code>LegalValueSet</code> to this <code>LegalValueSet</code>
 * at the end.
 *
 * @param  value  the legal value's value
 * @param  value  the legal value's display value
 * @param  lvs  the sub-<code>LegalValueSet</code>
 */
public void addLegalValue(String value, String display, LegalValueSet lvs) {
	addLegalValue(value, display);
	_subLegalValueSets.put(value, lvs);
}
/**
 * Returns the sub-<code>LegalValueSet</code> of the legal
 * value at the specified index.
 *
 * @param  index  the index
 * @return  The sub-<code>LegalValueSet</code>.
 */
public LegalValueSet getSubLegalValueSet(int index) {
	return (LegalValueSet) _subLegalValueSets.get(getValue(index));
}
/**
 * Returns the sub-<code>LegalValueSet</code> of the legal
 * value with the specified value.
 *
 * @param  value  the value
 * @return  The sub-<code>LegalValueSet</code>.
 */
public LegalValueSet getSubLegalValueSet(String value) {
	return (LegalValueSet) _subLegalValueSets.get(value);
}
}
