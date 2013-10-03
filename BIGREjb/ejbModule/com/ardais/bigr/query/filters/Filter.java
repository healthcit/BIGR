package com.ardais.bigr.query.filters;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.util.Constants;

/**
 * The abstract class for all filters.  Filters know how to limit query resutls based on
 * the value of an attribute.  Different subclasses handle cases such as strings equal
 * to a particular value, a number in a numeric range, etc.
 * 
 * This class is also a "Filter Factory" because it creates filters of the necessary
 * class and adds them to a filter map.  
 * 
 * Each concrete subclass of this class should have a corresponding 
 * addXxxxToMap() method here.
 * 
 * 
 */
public abstract class Filter implements Serializable {

  public static final String XML_TAG_NAME = "filter";

  /**
   * utility function to add a filter to a map, if the filter is not empty.
   */
  public static void addStringEqualToMap(FilterStringEquals f, Map m) {
    String val = f.getFilterValue();
    if (!isEmpty(val))
      m.put(f.getKey(), f);
  }

  /**
   *  Utility function to add a Filter for an attribute "like" a comparison string.
   *  The comparison string is similar to a database search string, but allows use of
   * asterix and question mark patterns, in addition to percent and underbar which normally
   * work in the database.
   * 
   * @param key - the attribute to constrain
   * @param pattern - the pattern string.  
   */
  public static void addStringLikeToMap(FilterStringLike f, Map m) {
    String pattern = f.getPatternForDisplay();
    if (!isEmpty(pattern))
      m.put(f.getKey(), f);
  }

  public static void addStringContainsToMap(FilterStringContains f, Map m) {
    String phrase = f.getPhrase();
    if (isEmpty(phrase))
      return; // do not add empty filters or those equal to reserved "any" value
    m.put(f.getKey(), f);
  }

  public static void addStringsEqualToMap(FilterStringsEqual f, Map m) {
    String[] vals = f.getFilterValues();
    if (!isEmpty(vals))
      m.put(f.getKey(), f);
  }

  public static void addStringsLikeToMap(FilterStringsLike f, Map m) {
    String[] patterns = f.getPatterns();
    if (!isEmpty(patterns)) {
      m.put(f.getKey(), f);
    }
  }

  public static boolean isEmpty(String[] vals) {
    if (vals == null)
      return true;
    if (vals.length == 0)
      return true;
    if (vals.length == 1 && Constants.ANY.equals(vals[0]))
      return true;
    return false;
  }

  /**
   *  Add a filter restricting a numeric attribute to a percentage range
   */
  public static void addPercentRangeToMap(FilterNumericRange f, Map m) {
    if (!f.isEmpty())
      m.put(f.getKey(), f);
  }

  public static void addDateRangeToMap(FilterDateRange f, Map m) {
    if (!f.isEmpty())
      m.put(f.getKey(), f);
  }

  /**
   * Add a filter for an attribute equal to a boolean value.
   */
  public static void addBooleanFilterToMap(FilterBoolean f, Map m) {
    m.put(f.getKey(), f);
  }

  /**
   * Add any old filter to the map
   */
  public static void addToMap(Filter f, Map m) {
    m.put(f.getKey(), f);
  }

  public static boolean isEmpty(String s) {
    return ApiFunctions.isEmpty(s) || Constants.ANY.equalsIgnoreCase(s);
  }
  // =================  end factory methods ==================================

  private String _key; // unique identifier for the value being filtered
  private String _orGroupCode; // unique id for the "or group" this condition goes into, if any

  /**
   * Creates a new <code>Filter</code>.
   */
  public Filter(String key) {
    super();
    _key = key;
  }

  /**
   * Returns the key.
   * 
   * @return  the key
   */
  public String getKey() {
    return _key;
  }

  /**
   * Update the QueryBuilder with this objects filter condition.
   */
  public abstract void addToQueryBuilder(InitializableFromFilter queryBuilder);

  /**
   * Prints a human-readable description of the filter.  Should use same terminology
   * as GUI.
   */
  public String toString() {
    // by default, print the internal code.  Ugly, but better than an error or classname
    return "Filter on code: " + getKey();
  }

  public final void toXml(StringBuffer buf) {
    buf.append("<");
    buf.append(XML_TAG_NAME);
    buf.append(" key=\"");
    buf.append(getKey()); // which filter
    buf.append("\" values=\"");
    appendVals(buf); // filter parameters
    buf.append("\"/>");
  }

  protected abstract void appendVals(StringBuffer buf);

  //  {
  //    buf.append ("undefinedfilterconditions");
  //  }

  /**
   * The display name for this filter type.  Override in subclasses to provide
   * readable toString() method.
   */
  protected String displayName() {
    return "((" + getKey() + "))";
  }

  /**
   * The mapping of internal filter codes to displayable values.  Empty by default.
   * @todo: make this abstract, which will require all fiters to implement display logic.
   */
  protected Map codeDisplayMap() {
    return Collections.EMPTY_MAP;
  }
  /**
   * Returns the orGroupCode.
   * @return String id for the or group this belongs in.  Filters are "AND"ed together, but
   * some groups are "OR"ed within the ands, to yeild a Conjunctive Normal Form filter condition.
   */
  public String getOrGroupCode() {
    return _orGroupCode;
  }

  /**
   * Sets the orGroupCode.
   * @param orGroupCode The orGroupCode to set
   */
  public void setOrGroupCode(String orGroupCode) {
    _orGroupCode = orGroupCode;
  }

}
