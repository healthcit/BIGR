package com.ardais.bigr.query.filters;

/**
 */
public interface InitializableFromFilter {

//	public void addFilter(String key);

// public void addFilter(String key, String filterValue);
	
    /**
     * Add a filter saying that the data corresponding to <code>key</code> must be in the list
     * of values in <code>filterValues</code>.
     * 
     * @param key - constant indicating what column to constrain.
     * @param filterValues - constants indicating what values to select for.
     */
//    public void addFilter(String key, String[] filterValues);

    /**
     * Add a filter saying that the data corresponding to <code>key</code> must be in the list
     * of values in <code>filterValues</code>.
     * 
     * @param key - constant indicating what column to constrain.
     * @param pattern - pattern indicating what values to match against 
     *                  (use asterix and question mark as wildcards).
     */
//    public void addFilterLike(String key, String pattern);


    /**
     * Add a filter saying that the data corresponding to <code>key</code> must equal
     * to <code>filterValue</code>.
     * 
     * @param key - constant indicating what column to constrain.
     * @param filterValue - constant indicating what (single) value to select for.
     */
//    void addFilter(String string, String val);

    /**
     * Add a filter saying that the data corresponding to <code>key</code> must be
     * numerically between <code>min</code> and <code> max </code>, inclusive.
     * 
     * @param key - constant indicating what column to constrain.
     * @param min - constant indicating minimum value of acceptable range.
     * @param max - constant indicating maximum value of acceptable range.
     */
//    void addFilter(String string, Integer min, Integer max);


    /**
     * Add a filter saying that the data corresponding to <code>key</code> must be
     * logically equal to the boolean value <code>bool</code>.
     * 
     * @param key - constant indicating what column to constrain.
     * @param bool - constant indicating that the key column must be either true or false 
     */
//	void addFilter(String key, boolean bool);
    
    
    // -----------------------  Individual Field Filters ---------------------
    /**
     * Filter the Restricted Status to be one of our predefined codes, for the account specified
     */
//    void addRestrictedStatusFilter(String status, String accountName);
}
