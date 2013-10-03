package com.ardais.bigr.query.filters;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.ardais.bigr.userprofile.UserProfileTopicSerializer;

/**
 *  A filter to check that a string value equals any one of a set of values.
 */
public abstract class FilterStringsEqual extends Filter {

	private String[] _filterValues;

	/**
	 * Creates a new <code>FilterStringEqual</code>.
	 */
	public FilterStringsEqual(String key, String[] filterValues) {
		super(key);
		_filterValues = filterValues;
	}

	/* (non-Javadoc)
	 * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
	 */
//	public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
//		queryBuilder.addFilter(getKey(), getFilterValues());
//	}

	/**
	 * Returns the array of filter values.
	 * 
	 * @return  the filter values
	 */
	public String[] getFilterValues() {
		return _filterValues;
	}

    /**
     * The filter values that are visible.  Subclasses can have implicit, invisible values.
     */
    protected String[] getDisplayedFilterValues() {
        return getFilterValues();
    }
    
    /**
     * Return a human-readable snippet, suitable for inclusion in a summary.
     * @see ProductFilters.toString()
     */
    public String toString() {
        return valsAsDisplayString(displayName(), remap(getDisplayedFilterValues()), true);
    }
    
    public static String valsAsDisplayString(String displayName, String[] vals, boolean isOneOf) {
        StringBuffer buf = new StringBuffer();
        buf.append(displayName);
        if (isOneOf)
            buf.append(" is ");
        else
            buf.append(" is NOT ");
        if (vals.length > 1)
            buf.append("one of:\n      "); // break and indent
        for (int i=0; i<vals.length; i++) {
            if (i>0)
                buf.append("; ");
            buf.append(vals[i]);
        }
        buf.append('.');
        return buf.toString();
    }
    
    /**
     * @param s and internal code representing some filter value
     * @return the human readable description of that code.
     */
    protected String remap(String s) {
        String mapto = (String) codeDisplayMap().get(s);
        return mapto == null ? s : mapto;
    }
    
    /**
     * Apply remap() a number of times.  
     */
    protected final String[] remap(String[] s) {
        String[] mapped = new String[s.length];
        for (int i=0; i<s.length; i++) {
            mapped[i] = remap(s[i]);
        }
        return mapped;
    }
    
  protected void appendVals(StringBuffer buf) {
    String commaSepVals = StringUtils.join(_filterValues, ",");
    buf.append(commaSepVals); // @todo: efficiently add to buffer, don't create extra string
  }
}
