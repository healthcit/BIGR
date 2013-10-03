package com.ardais.bigr.query.filters;

/**
 * Superclass of filters that are not like a string pattern.  
 */
public abstract class FilterStringNotLike extends FilterStringLike {

    /**
     * Constructor for FilterStringNotLike.
     * @param key
     * @param pattern
     */
    public FilterStringNotLike(String key, String pattern) {
        super(key, pattern);
    }
    
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append(displayName());
        buf.append(" does NOT contain: ");
        buf.append(getPatternForDisplay());
        buf.append('.');
        return buf.toString();
    }
    
}
