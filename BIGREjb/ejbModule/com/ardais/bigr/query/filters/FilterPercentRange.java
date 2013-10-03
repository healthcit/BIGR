package com.ardais.bigr.query.filters;

/**
 * @author dfeldman
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public abstract class FilterPercentRange extends FilterNumericRangeInteger {

    /**
     * Constructor for FilterCompositionValue.
     * @param key  Internal constant to represent the data value being filtered
     * @param min  The Integer min of the percent range.  (null, implies 0)
     * @param max  The Integer max of the percent range.  (null, implies 100)
     */
    public FilterPercentRange(String key, Integer min, Integer max) {
        super(key, min, max, 0, 100);
    }

    /**
     * Constructor for FilterCompositionValue.
     * @param key  Internal constant to represent the data value being filtered
     * @param min  The string min of the percent range.  (null, empty String implies 0)
     * @param max  The string max of the percent range.  (null, empty String implies 100)
     */
    public FilterPercentRange(String key, String min, String max) {
        super(key, min, max, 0, 100);
    }
    
    public boolean isEmpty() {
        boolean minEmpty = getMin()==null || getMin().intValue()==0;
        boolean maxEmpty = getMax()==null || getMax().intValue()==100;
        return minEmpty && maxEmpty;
    }

}
