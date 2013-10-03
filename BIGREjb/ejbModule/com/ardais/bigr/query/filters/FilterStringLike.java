package com.ardais.bigr.query.filters;

import java.io.Serializable;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.query.generator.ProductSummaryQueryBuilder;
import com.ardais.bigr.userprofile.UserProfileTopicSerializer;
import com.ardais.bigr.util.DbUtils;

/**
 * A filter to check that one string is like one particular value, where "like"
 * implies the SQL definition of like.
 */
public abstract class FilterStringLike extends Filter {

	private String _pattern;

	/**
	 * Creates a new <code>FilterStringEqual</code>.
	 */
	public FilterStringLike(String key, String pattern) {
		super(key);
		_pattern = pattern;
	}

	/* (non-Javadoc)
	 * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
	 */
	public final void addToQueryBuilder(InitializableFromFilter queryBuilder) {
        ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
        privateAddToProductSummaryQueryBuilder(qb);
	}

    protected abstract void privateAddToProductSummaryQueryBuilder(ProductSummaryQueryBuilder qb) ;
    
	/**
	 * Returns the array of filter values, just as entered by the user or creator of this
     * object.
	 * 
	 * @return  the pattern, without conversion of ? and * wildcards into DB characters.
     * 
	 */
    public String getPatternForDisplay() {
        return _pattern;
    }

    /**
     * Returns the filter pattern with user entered chars (such as asterix and question mark)
     * converted to DB LIKE characters, such as percent.
     * @return the pattern with conversion of wildcards performed.
     */
    public String getPatternForDB() {
        return DbUtils.convertLikeSpecialChars(_pattern);
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append(displayName());
        buf.append(" contains: ");
        buf.append(getPatternForDisplay());
        buf.append('.');
        return buf.toString();
    }
  
    public void appendVals(StringBuffer buf) {
      buf.append(_pattern);
    }    
}
