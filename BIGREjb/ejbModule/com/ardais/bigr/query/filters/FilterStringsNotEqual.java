package com.ardais.bigr.query.filters;


/**
 *  A filter to check that a string value equals any one of a set of values.
 */
public abstract class FilterStringsNotEqual extends FilterStringsEqual {

	/**
	 * Creates a new <code>FilterStringEqual</code>.
	 */
	public FilterStringsNotEqual(String key, String[] filterValues) {
		super(key, filterValues);
	}



}
