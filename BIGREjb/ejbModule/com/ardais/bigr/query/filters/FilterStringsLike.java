package com.ardais.bigr.query.filters;

import org.apache.commons.lang.StringUtils;

import com.ardais.bigr.util.DbUtils;

/**
 * A filter to check that a string pattern is like any set of values, where "like"
 * implies the SQL definition of like.
 */
public abstract class FilterStringsLike extends Filter {

  private String _patterns[];

  /**
   * Creates a new <code>FilterStringsLike</code>.
   */
  public FilterStringsLike(String key, String[] patterns) {
    super(key);
    _patterns = patterns;
  }

  /**
   * Returns the array of filter patterns, just as entered by the user or creator of this
   * object.
   * 
   * @return  the patterns, without conversion of ? and * wildcards into DB characters.
    * 
   */
  public String[] getPatterns() {
    return _patterns;
  }

  /**
   * Returns the filter pattern with user entered chars (such as asterix and question mark)
   * converted to DB LIKE characters, such as percent.
   * @return the patterns with conversion of wildcards performed.
   */
  public String[] getPatternsForDB() {
    return DbUtils.convertLikeSpecialChars(_patterns);
  }

  public String toString() {
    StringBuffer buf = new StringBuffer();
    buf.append(displayName());
    buf.append(" contains: [");

    for (int i = 0; i < _patterns.length; i++) {
      if (i > 0) {
        buf.append("] [");
      }
      buf.append(_patterns[i]);
    }
    buf.append("].");
    return buf.toString();
  }

  public void appendVals(StringBuffer buf) {
    String commaSepVals = StringUtils.join(_patterns, ",");
    buf.append(commaSepVals); // @todo: efficiently add to buffer, don't create extra string
  }
}
