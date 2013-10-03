package com.ardais.bigr.query.filters;




/**
 * A filter to check that one string is like one particular value, where "like"
 * implies the SQL definition of like.
 */
public abstract class FilterStringContains extends Filter {

	private String _phrase;

	/**
	 * Constructor
     * @param key   the internal code of which value to filter on.
     * @param phrase  a string that the field must contain (no wildcards used)
	 */
	public FilterStringContains(String key, String phrase) {
		super(key);
		_phrase = phrase;
	}

	/* (non-Javadoc)
	 * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
	 */
//	public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
//	  queryBuilder.addFilterLike(getKey(), DbUtils.convertLikeSpecialChars(getPhrase()));
//	}

	/**
	 * Returns the array of filter values.
	 * 
	 * @return  the filter values, without conversion of ? and * wildcards into DB characters.
     * 
	 */
	public String getPhrase() {
		return _phrase;
	}

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append(displayName());
        buf.append(" contains: ");
        buf.append(getPhrase());
        buf.append('.');
        return buf.toString();
    }
    
  /**
   * @see com.ardais.bigr.query.filters.Filter#appendVals(StringBuffer)
   */
  protected void appendVals(StringBuffer buf) {
    buf.append(_phrase);
  }

}
