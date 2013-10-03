package com.ardais.bigr.query.generator;

import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.query.filters.RepeatingFilterData;
import com.ardais.bigr.query.filters.RepeatingSingleData;
import com.ardais.bigr.query.generator.gen.ColumnMetaData;
import com.ardais.bigr.query.generator.gen.FilterMetaData;
import com.ardais.bigr.query.generator.gen.TableMetaData;
import com.ardais.bigr.util.BigrPreparedStatement;

/**
 * Generic query builder that holds all columns, filters and tables needed to 
 * perform a single query.  This is a generic package-visible class that is 
 * intended to be used by other query builders such as 
 * {@link ProductSummaryQueryBuilder} and {@link ProductDetailQueryBuilder} to 
 * build specific queries.  Contains methods to allow columns, filters and 
 * tables to be added, with actual query parameters if appropriate, to return 
 * the query's SQL statement, and to bind the actual values captured to the
 * <code>PreparedStatement</code> created from the SQL statement.
 */
class ProductQueryBuilder {
  /**
   * The maximum value that can be passed to {@link #setHintPriority(int)}.
   */
  public static final int MAX_HINT_PRIORITY = Integer.MAX_VALUE;
  
  /**
   * Holds keys into the column meta data.
   */
  private SortedSet _columns = new TreeSet();

  /**
   * Holds keys into the table meta data.  In addition, the values are
   * TableData objects that hold the actual parameters.
   */
  private SortedMap _tables = new TreeMap();

  /**
   * Holds keys into the filter meta data.  In addition, the values are
   * FilterData objects that hold the actual parameters.
   */
  private SortedMap _filters = new TreeMap();

  /**
   * Holds keys into the column meta data for "group by".
   */
  private SortedSet _groupBy = new TreeSet();

  /**
   * Holds groups of filters that should be ORed (as opposed to the default 
   * AND).  Each key is an OR_GROUP_* constant as defined in this class, and 
   * each value is a SortedSet of the keys of the filters that are in the 
   * group.
   */
  private HashMap _orGroups = new HashMap();

  /**
   * Holds an optimizer hint to include in the query.  This isn't a
   * particularly elegant mechanism at the moment because the caller has
   * to know more about the query that gets generated than is ideal.  It
   * would be nice to come up with a way to do this that is somehow based
   * on the same metadata concepts as the rest of the query builder.
   */
  private String _optimizerHint = null;

  /**
   * Holds the priority for the current hint.
   */
  private int _hintPriority = 0;

  /**
   * when bindParameter is called, this value will be updated with the number of parameters
   * used in bind.
   */
  //    private int _paramCount = 0;

  /**
   * Holds query-specific filter data, such as actual parameters.
   */
  class FilterData {
    private int _numFilters = 1;
    private String[] _stringArgs;
    private boolean _processed;
    private SortedSet _orGroup;
    private RepeatingFilterData _repeatingValues = null;

    /**
     * Creates a new <code>FilterData</code>.  Use this constructor when the
     * filter takes no arguments.
     */
    FilterData() {
    }

    /**
     * Creates a new <code>FilterData</code> for a single filter with the 
     * specified actual argument.
     * 
     * @param  argument  the actual argument to be bound to the filter when
     * 										the filter is used in a query
     */
    FilterData(String argument) {
      _numFilters = 1;
      _stringArgs = new String[] { argument };
    }

    /**
     * Creates a new <code>FilterData</code> for the specified number of
     * filters, with the specified actual arguments for all of the filters.
     * The number of arguments specified must be the product of the number of 
     * filters specified and the number of arguments that each filter takes.
     * 
     * @param  arguments  the actual arguments to be bound to the filters when
     * 										 the filters are used in a query
     * @param  numFilters  the number of filters that this to which this
     * 											<code>FilterData</code> applies
     */
    FilterData(String[] arguments, int numFilters) {
      if (arguments.length == 0)
        throw new ApiException("Empty argument list passed to String[] filter");
      _numFilters = numFilters;
      _stringArgs = arguments;
    }

    FilterData(RepeatingFilterData repeatingValues) {
      _repeatingValues = repeatingValues;
      if (repeatingValues != null) {
        _numFilters = repeatingValues.size();  
      }
      _stringArgs = repeatingValues.getParameterValues();
    }

    /**
     * Returns the number of filters that this to which this 
     * <code>FilterData</code> applies
     * 
     * @return  The number of filters.
     */
    int getNumFilters() {
      return _numFilters;
    }

    /**
     * Returns the actual arguments that apply to all filters to which this 
     * <code>FilterData</code> applies
     * 
     * @return  The actual filter arguments.
     */
    String[] getStringArgs() {
      return _stringArgs;
    }

    /**
     * Returns a flag indicating whether this filter has been processed.  This
     * is used during the generation of the query specified in this
     * <code>ProductQueryBuilder</code>.
     * 
     * @return  <code>true</code> if this filter has been processed;
     * 					 <code>false</code> otherwise
     * 
     */
    boolean isProcessed() {
      return _processed;
    }

    /**
     * Sets a flag indicating whether this filter has been processed.  This
     * is used during the generation of the query specified in this
     * <code>ProductQueryBuilder</code>.
     * 
     * @param  processed  <code>true</code> if this filter has been processed;
     * 										 <code>false</code> otherwise
     * 
     */
    void setProcessed(boolean processed) {
      _processed = processed;
    }

    /**
     * Returns the OR group to which this filter belongs.
     * 
     * @return  A <code>SortedSet</code> of filter keys that indicate
     * 					 the filters that belong in the OR group.
     * 
     */
    SortedSet getOrGroup() {
      return _orGroup;
    }

    /**
     * Sets the OR group to which this filter belongs.  An OR group is a set
     * of filters that should be ORed together when the query is generated,
     * instead of the default of being ANDed with all other filters.
     * 
     * @param  orGroup  a <code>SortedSet</code> of filter keys that indicate
     * 									 the filters that belong in the OR group.
     * 
     */
    void setOrGroup(SortedSet orGroup) {
      _orGroup = orGroup;
    }

    /**
     * @return
     */
    public RepeatingFilterData getRepeatingValues() {
      return _repeatingValues;
    }

  } // End of inner class FilterData 

  /**
   * Holds query-specific table data, such as parameters for inline views.
   */
  class TableData {
    private String[] _stringArgs;

    /**
     * Creates a new <code>TableData</code> for a table with the specified 
     * actual argument.
     * 
     * @param  argument  the actual argument to be bound to the table when
     * 										the table is used in a query
     */
    TableData(String argument) {
      _stringArgs = new String[] { argument };
    }

    /**
     * Creates a new <code>TableData</code> for a table with the specified 
     * actual arguments.
     * 
     * @param  arguments  the actual arguments to be bound to the table when
     * 										 the table is used in a query
     */
    TableData(String[] arguments) {
      _stringArgs = arguments;
    }

    /**
     * Returns the actual arguments that apply to the table to which this
     * <code>TableData</code> applies
     * 
     * @return  The actual table arguments.
     */
    String[] getStringArgs() {
      return _stringArgs;
    }

  } // End of inner class TableData 

  /**
   * Creates a new <code>ProductQueryBuilder</code>.
   */
  public ProductQueryBuilder() {
  }

  /**
   * Writes the SQL statement for the query specified by this
   * <code>ProductQueryBuilder</code> into the supplied string buffer.
   * 
   * @param  buf  the <code>StringBuffer</code> into which the SQL should
   * 							 be written
   */
  void getQuery(StringBuffer buf) {
    getSelectClause(buf);
    buf.append(" \r\n");
    getFromClause(buf);
    buf.append(" \r\n");
    getWhereClause(buf);
    buf.append(" \r\n");
    getGroupByClause(buf);
  }

  /**
   * Returns the SQL statement for the query specified by this
   * <code>ProductQueryBuilder</code>.
   * 
   * @return  The SQL statement.
   */
  String getQuery() {
    StringBuffer buf = new StringBuffer(1024);
    getQuery(buf);
    return buf.toString();
  }

  /**
   * Writes the SQL for the SELECT clause specified by this
   * <code>ProductQueryBuilder</code> into the supplied string buffer.
   * 
   * @param  buf  the <code>StringBuffer</code> into which the SQL SELECT 
   * 							 clause should be written
   */
  private void getSelectClause(StringBuffer buf) {
    if (!_columns.isEmpty()) {
      boolean first = true;
      Iterator iter = _columns.iterator();
      buf.append("SELECT ");
      String hint = getOptimizerHint();
      if (!ApiFunctions.isEmpty(hint)) {
        buf.append('/');
        buf.append("*+ ");
        buf.append(hint);
        buf.append(" *");
        buf.append("/ ");
      }
      do {
        if (first) {
          first = false;
        }
        else {
          buf.append(",");
        }
        String columnKey = (String) iter.next();
        ColumnMetaData metaData = ProductQueryMetaData.getColumnMetaData(columnKey);
        if (metaData == null) {
          throw new ApiException(
            "In ProductQueryBuilder.getSelectClause: No meta-data entry for column key "
              + columnKey
              + ".  Check that this key is mapped to an entry in ProductQueryMetaData.");
        }
        buf.append("\r\n  ");
        buf.append(metaData.getSelectFragment());
      }
      while (iter.hasNext());
    }
  }

  /**
   * Writes the SQL for the FROM clause specified by this
   * <code>ProductQueryBuilder</code> into the supplied string buffer.
   * 
   * @param  buf  the <code>StringBuffer</code> into which the SQL FROM
   * 							 clause should be written
   */
  private void getFromClause(StringBuffer buf) {
    if (!_tables.isEmpty()) {
      boolean first = true;
      Iterator iter = _tables.keySet().iterator();
      buf.append("FROM ");
      do {
        if (first) {
          first = false;
        }
        else {
          buf.append(",");
        }
        String tableKey = (String) iter.next();
        TableMetaData metaData = ProductQueryMetaData.getTableMetaData(tableKey);
        if (metaData == null) {
          throw new ApiException(
            "In ProductQueryBuilder.getFromClause: No meta-data entry for table key "
              + tableKey
              + ".  Check that this key is mapped to an entry in ProductQueryMetaData.");
        }
        buf.append("\r\n  ");
        buf.append(metaData.getFromFragment());
      }
      while (iter.hasNext());
    }
  }

  /**
   * Writes the SQL for the GROUP BY clause specified by this
   * <code>ProductQueryBuilder</code> into the supplied string buffer.
   * 
   * @param  buf  the <code>StringBuffer</code> into which the SQL GROUP BY 
   *                           clause should be written
   */
  private void getGroupByClause(StringBuffer buf) {
    if (!_groupBy.isEmpty()) {
      boolean first = true;
      Iterator iter = _groupBy.iterator();
      buf.append("GROUP BY ");
      do {
        if (first) {
          first = false;
        }
        else {
          buf.append(" ,");
        }
        String columnKey = (String) iter.next();
        ColumnMetaData metaData = ProductQueryMetaData.getColumnMetaData(columnKey);
        if (metaData == null) {
          throw new ApiException(
            "In ProductQueryBuilder.getGroupByClause: No meta-data entry for column key "
              + columnKey
              + ".  Check that this key is mapped to an entry in ProductQueryMetaData.");
        }
        buf.append(metaData.getTableAlias());
        buf.append(".");
        buf.append(metaData.getColumn());
      }
      while (iter.hasNext());
    }
  }

  /**
   * Writes the SQL for the WHERE clause specified by this
   * <code>ProductQueryBuilder</code> into the supplied string buffer.
   * 
   * @param  buf  the <code>StringBuffer</code> into which the SQL WHERE 
   * 							 clause should be written
   */
  private void getWhereClause(StringBuffer buf) {
    if (!_filters.isEmpty()) {

      // Mark all filters as not being processed.
      Iterator iter = _filters.entrySet().iterator();
      while (iter.hasNext()) {
        Map.Entry filterEntry = (Map.Entry) iter.next();
        FilterData filterData = (FilterData) filterEntry.getValue();
        filterData.setProcessed(false);
      }

      // Iterate over all filters.
      iter = _filters.keySet().iterator();
      for (String separator = "WHERE \r\n  "; iter.hasNext(); separator = "\r\n  AND ") {
        String filterKey = (String) iter.next();
        FilterData filterData = (FilterData) _filters.get(filterKey);

        // Only process this filter if it has not already been processed.
        // It will have been processed if it was part of an OR group attached
        // to another filter.
        if (!filterData.isProcessed()) {

          // Determine whether the filter currently being processed
          // participates in an OR group.
          SortedSet orGroup = filterData.getOrGroup();
          boolean isOrGroup = ((orGroup != null) && (orGroup.size() > 1)) ? true : false;

          // Append the appropriate separator for the WHERE clause.
          buf.append(separator);

          // Create an array of all filter keys that participate in the OR
          // group.  If there is no OR group, then create an array with
          // a single element that is the current filter key.
          String[] ors = null;
          if (isOrGroup) {
            buf.append("((");
            ors = (String[]) orGroup.toArray(new String[] {
            });
          }
          else {
            ors = new String[] { filterKey };
          }

          // Loop through all filters in the OR group, generating
          // the appropriate WHERE fragment for each.
          int numOrs = ors.length;
          for (int i = 0; i < numOrs; i++) {
            filterKey = ors[i];

            // Get the meta-data for the current filter.
            FilterMetaData metaData = ProductQueryMetaData.getFilterMetaData(filterKey);
            if (metaData == null) {
              throw new ApiException(
                "In ProductQueryBuilder.getWhereClause: No meta-data entry for filter key "
                  + filterKey
                  + ".  Check that this key is mapped to an entry in ProductQueryMetaData.");
            }

            String fragment = metaData.getWhereFragment();
            String multiOp = metaData.getMultipleFilterOperator();
            String additionalClause = metaData.getAdditionalClause();

            // Get the number of WHERE fragments to generate for
            // this filter.
            filterData = (FilterData) _filters.get(filterKey);
            
            RepeatingFilterData repeatData = filterData.getRepeatingValues();
            if (repeatData != null && repeatData.size() > 0) {
              String[] fragmentParts = ApiFunctions.separateRepeatFragmentParts(fragment);
              String fragmentPrefix = fragmentParts[0];
              String fragmentRepeat = fragmentParts[1];
              String fragmentSuffix = fragmentParts[2];

              if (additionalClause != null) // additional open paren matched below
                buf.append('(');
              
              Iterator iter1 = repeatData.getValues().iterator();
              boolean start = true;
              buf.append('(');
              while (iter1.hasNext()) {
                RepeatingSingleData singleData = (RepeatingSingleData) iter1.next();
                if (!singleData.isEmpty()) {
                  if (!start) {
                    buf.append(" OR ");
                  }
                  buf.append(fragmentPrefix);
                  buf.append("( ");
                  boolean start1 = true;
                  for (int count1 = 0; count1 < singleData.repeatSize(); count1++) {
                    if (!start1) {
                      buf.append(multiOp);
                    }
                    buf.append(" (");
                    buf.append(fragmentRepeat);
                    buf.append(")");
                    start1 = false;
                  }
                  buf.append(")");
                  buf.append(fragmentSuffix);
                  start = false;
                }
              }
              buf.append(')');
              if (additionalClause != null) { // add clause inside overall parens
                buf.append(' ');
                buf.append(additionalClause);
                buf.append(')');
              }
            }
            else {
              int numFilters = filterData.getNumFilters();
              if (numFilters == 1) {
                if (additionalClause == null) {
                  buf.append(fragment); // no parens for single fragment
                }
                else { // single fragment with additional needs: (fragment addit)
                  buf.append('(');
                  buf.append(fragment);
                  buf.append(additionalClause);
                  buf.append(')');
                }
              }
              else {
                if (additionalClause != null) // additional open paren matched below
                  buf.append('(');
                buf.append("((");
                buf.append(fragment);
                for (int j = 1; j < numFilters; j++) {
                  buf.append(") ");
                  buf.append(multiOp);
                  buf.append(" (");
                  buf.append(fragment);
                }
                buf.append("))");
                if (additionalClause != null) { // add clause inside overall parens
                  buf.append(' ');
                  buf.append(additionalClause);
                  buf.append(')');
                }
              }

            }


            // If we're adding another filter in the OR group, then
            // add the OR with appropriate parentheses.
            if ((i + 1) < numOrs) {
              buf.append(") OR (");
            }

            // Mark this filter as being processed.
            filterData.setProcessed(true);

          } // for filters in OR group

          if (isOrGroup) {
            buf.append("))");
          }
        } // has not already been processed
      } // for all filters
    }
  }

  /**
   * Binds all parameters in the in the supplied <code>PreparedStatement</code>.
   * The <code>PreparedStatement</code> must have been generated from this
   * <code>ProductQueryBuilder</code> to ensure that the number of parameters
   * in the <code>PreparedStatement</code> match the number of parameters in
   * this <code>ProductQueryBuilder</code>.  The values to bind are taken from 
   * the tables (for inline views) and filters that are specified in this
   * <code>ProductQueryBuilder</code>.
   * 
   * @param  pstmt  the <code>PreparedStatement</code>.
   */
  void bindAllParameters(BigrPreparedStatement pstmt) throws SQLException {

    //        int paramIndex = 1;

    // First, bind all parameters for the tables (those with subqueries).
    if (!_tables.isEmpty()) {
      Iterator iter = _tables.keySet().iterator();

      // Iterate over all specified tables to bind parameter values to
      // the placeholders in the PreparedStatement.  There will only be
      // table-specific data if the table has an inline view/subquery.
      while (iter.hasNext()) {
        String tableKey = (String) iter.next();
        Object o = _tables.get(tableKey);
        if (o != null) {
          TableData tableData = (TableData) o;
          TableMetaData metaData = (TableMetaData) ProductQueryMetaData.getTableMetaData(tableKey);

          // Bind the actual parameters to the table, based on data type.
          switch (metaData.getDataType()) {
            case Types.VARCHAR :
              String[] params = tableData.getStringArgs();
              int numParamsToBind = params.length;
              for (int i = 0; i < numParamsToBind; i++) {
                pstmt.setNextString(params[i]);
              }
              break;
              //					case Types.DECIMAL:
              //						break;
              //					case Types.DATE:
              //						break;
          }
        }
      }
    }

    // Bind all parameters for the filters.
    if (!_filters.isEmpty()) {

      // Mark all filters as not being processed.
      Iterator iter = _filters.entrySet().iterator();
      while (iter.hasNext()) {
        Map.Entry filterEntry = (Map.Entry) iter.next();
        FilterData filterData = (FilterData) filterEntry.getValue();
        filterData.setProcessed(false);
      }

      // Iterate over all specified filters to bind parameter values to
      // the placeholders in the PreparedStatement.
      iter = _filters.keySet().iterator();
      while (iter.hasNext()) {
        String filterKey = (String) iter.next();
        FilterData filterData = (FilterData) _filters.get(filterKey);

        // Only process this filter if it has not already been processed.
        // It will have been processed if it was part of an OR group attached
        // to another filter.
        if (!filterData.isProcessed()) {

          // Determine whether the filter currently being processed
          // participates in an OR group.
          SortedSet orGroup = filterData.getOrGroup();
          boolean isOrGroup = ((orGroup != null) && (orGroup.size() > 1)) ? true : false;

          // Create an array of all filter keys that participate in the OR
          // group.  If there is no OR group, then create an array with
          // a single element that is the current filter key.
          String[] ors = null;
          if (isOrGroup) {
            ors = (String[]) orGroup.toArray(new String[] {
            });
          }
          else {
            ors = new String[] { filterKey };
          }

          // Loop through all filters in the OR group, binding actual 
          // parameters for each.
          int numOrs = ors.length;
          for (int i = 0; i < numOrs; i++) {
            filterKey = ors[i];
            FilterMetaData metaData =
              (FilterMetaData) ProductQueryMetaData.getFilterMetaData(filterKey);
            filterData = (FilterData) _filters.get(filterKey);

            // Bind the actual parameters to the filter, based on data type.
            switch (metaData.getDataType()) {
              case Types.VARCHAR :
                String[] params = filterData.getStringArgs();
                if (!ApiFunctions.isEmpty(params)) {
                  int numParamsToBind = params.length;
                  for (int j = 0; j < numParamsToBind; j++) {
                    pstmt.setNextString(params[j]);
                  }
                }

                break;
                //					case Types.DECIMAL:
                //						break;
                //					case Types.DATE:
                //						break;
            }

            // Mark this filter as being processed.
            filterData.setProcessed(true);
          }
        }
      }
    }

  }

  /**
   * Adds the column with the specified key to this query.
   * 
   * @param  columnKey  the column key; must be one of the <code>KEY_*</code>
   * 							 			 constants in {@link ColumnMetaData}
   */
  void addColumn(String columnKey) {
    _columns.add(columnKey);
  }

  /**
   * Adds the group by with the specified key to this query.
   * 
   * @param  columnKey  the column key; must be one of the <code>KEY_*</code>
   *                                       constants in {@link ColumnMetaData}
   */
  void addGroupBy(String columnKey) {
    _groupBy.add(columnKey);
  }

  /**
   * Adds the table with the specified key to this query.
   * 
   * @param  tableKey  the table key; must be one of the <code>KEY_*</code>
   * 							 			constants in {@link TableMetaData}
   */
  void addTable(String tableKey) {
    _tables.put(tableKey, null);
  }

  /**
   * Adds the table with the specified key and parameters to this query.  Use
   * this method for adding tables with inline views/subqueries that take
   * parameters.
   * 
   * @param  tableKey  the table key; must be one of the <code>KEY_*</code>
   * 							 			constants in {@link TableMetaData}
   * @param  params  the parameters
   */
  void addTable(String tableKey, String[] params) {
    int numParams = ProductQueryMetaData.getTableMetaData(tableKey).getNumberParameters();
    if (numParams != params.length) {
      throw new ApiException(
        "In ProductQueryBuilder.addTable: for key "
          + tableKey
          + ", "
          + String.valueOf(params.length)
          + " parameters specified, but inline view requires "
          + String.valueOf(numParams));
    }
    _tables.put(tableKey, new TableData(params));
  }

  /**
   * Adds the join with the specified key to be ANDed in this query.
   * 
   * @param  joinKey  the join key; must be one of the <code>KEY_*</code>
   * 									 constants in {@link FilterMetaData}
   */
  void addJoin(String joinKey) {
    _filters.put(joinKey, new FilterData());
  }

  /**
   * Adds the join with the specified key to be ORed in this query.
   * 
   * @param  orGroupKey  a key that identifies the group of filters that should
   * 											be ORed.  Each distinct group of filters to be ORed
   * 											must have a unique key.
   * @param  joinKey  the join key; must be one of the <code>KEY_*</code>
   * 									 constants in {@link FilterMetaData}
   */
  /*
   * This is done in the special case where we have a sub-query in the
   * from clause, and we want to filter out everything but the values
   * in the sub-query.  (e.g. we query diagnoses like *breast* and we
   * want to filter out samples with no match in that set.) 
   * This is a join, because it links a table with a sub-query result set,
   * and it is also like a filter, because it restricts results to those
   * with a set of values
   */
  void addJoinOr(String orGroupKey, String joinKey) {
    addJoin(joinKey);
    addFilterToOrGroup(orGroupKey, joinKey);
  }

  /**
   * Adds the filter with the specified key to be ANDed in this query.  Use
   * this method to add a filter with no parameters.
   * 
   * @param  filterKey  the filter key; must be one of the <code>KEY_*</code>
   * 							 			 constants in {@link FilterMetaData}
   */
  void addFilter(String filterKey) {
    _filters.put(filterKey, new FilterData());
    updateHint(filterKey);
  }

  /**
   * If the filter has higher priority hint than the current hint priority
   * then update the _hintPriority and _optimizerHint from the filter.
   *
   * @param  filterKey  the filter key; must be one of the <code>KEY_*</code>
   *                     constants in {@link FilterMetaData}
   */
  private void updateHint(String filterKey) {
    FilterMetaData filterMeta = ProductQueryMetaData.getFilterMetaData(filterKey);
    int hintPriority = filterMeta.getHintPriority();
    
    // Only change the hint if the new priority is strictly greater than the
    // current hint priority.  This avoids, for example, repeatedly setting the
    // hint back to null when none of the filters have any hints (and so all of the
    // hint priorities are zero, and all of the hint texts null).
    //
    if (hintPriority > getHintPriority()) {
      setHintPriority(hintPriority);
      setOptimizerHint(filterMeta.getHintText());
    }
    
  }
  /**
   * Adds the filter with the specified key and parameter to be ANDed in
   * this query.
   * 
   * @param  filterKey  the filter key; must be one of the <code>KEY_*</code>
   * 							 			 constants in {@link FilterMetaData}
   * @param  parameter  the filter's parameter
   */
  void addFilter(String filterKey, String parameter) {
    int numParamsPerClause =
      ProductQueryMetaData.getFilterMetaData(filterKey).getNumberParameters();
    if (numParamsPerClause <= 0) {
      throw new ApiException(
        "In ProductQueryBuilder.addFilter: for key "
          + filterKey
          + ", 1 parameter specified, but none expected");
    }
    else if (numParamsPerClause > 1) {
      throw new ApiException(
        "In ProductQueryBuilder.addFilter: for key "
          + filterKey
          + ", only 1 parameter specified, but clause requires "
          + numParamsPerClause);
    }
    _filters.put(filterKey, new FilterData(parameter));
    updateHint(filterKey);
  }

  /**
   * Adds the filter with the specified key and parameter to be ORed in this 
   * query.
   * 
   * @param  orGroupKey  a key that identifies the group of filters that should
   *                      be ORed.  Each distinct group of filters to be ORed
   *                      must have a unique key.
   * @param  filterKey  the filter key; must be one of the <code>KEY_*</code>
   *                     constants in {@link FilterMetaData}
   * @param  repeatingValues  the filter's parameter
   */
  void addFilterOr(String orGroupKey, String filterKey, RepeatingFilterData repeatingValues) {
    addFilter(filterKey, repeatingValues);
    addFilterToOrGroup(orGroupKey, filterKey);
  }


  /**
   * Adds the filter with the specified key and parameters to be ANDed in
   * this query.
   * 
   * @param  filterKey  the filter key; must be one of the <code>KEY_*</code>
   *                     constants in {@link FilterMetaData}
   * @param repeatingValues the filter's parameter
   */
  void addFilter(String filterKey, RepeatingFilterData repeatingValues) {
    int numParamsInPrefix =
      ProductQueryMetaData.getFilterMetaData(filterKey).getNumberParametersInPrefix();
    int numParamsInRepeat =
      ProductQueryMetaData.getFilterMetaData(filterKey).getNumberParametersInRepeat();
    int numParamsInSuffix =
      ProductQueryMetaData.getFilterMetaData(filterKey).getNumberParametersInSuffix();

    
    Iterator repeatValues = repeatingValues.getValues().iterator();
    
    while (repeatValues.hasNext()) {
      RepeatingSingleData singleData = (RepeatingSingleData) repeatValues.next();
      if (!singleData.isEmpty()) {
        if (numParamsInPrefix != singleData.prefixSize()) {
          throw new ApiException(
            "In ProductQueryBuilder.addFilter: for key "
              + filterKey
              + ", number of parameters specified in prefix ("
              + singleData.prefixSize()
              + ") does not match the number of parameters ("
              + numParamsInPrefix
              + ")");
        }
        if (singleData.repeatSize() % numParamsInRepeat > 0) {
          throw new ApiException(
            "In ProductQueryBuilder.addFilter: for key "
              + filterKey
              + ", number of parameters specified in repeat ("
              + singleData.repeatSize()
              + ") is not a multiple of the number of parameters ("
              + numParamsInRepeat
              + ")");
        }
        if (numParamsInSuffix != singleData.suffixSize()) {
          throw new ApiException(
            "In ProductQueryBuilder.addFilter: for key "
              + filterKey
              + ", number of parameters specified in suffix ("
              + singleData.suffixSize()
              + ") does not match the number of parameters ("
              + numParamsInSuffix
              + ")");
        }
      }
    }
    _filters.put(filterKey, new FilterData(repeatingValues));
    updateHint(filterKey);
  }

  /**
   * Adds the filter with the specified key and parameters to be ANDed in
   * this query.
   * 
   * @param  filterKey  the filter key; must be one of the <code>KEY_*</code>
   * 							 			 constants in {@link FilterMetaData}
   * @param  parameters  the filter's parameters
   */
  void addFilter(String filterKey, String[] parameters) {
    int numParamsPerClause =
      ProductQueryMetaData.getFilterMetaData(filterKey).getNumberParameters();
    if (numParamsPerClause <= 0) {
      throw new ApiException(
        "In ProductQueryBuilder.addFilter: for key "
          + filterKey
          + ", "
          + parameters.length
          + " parameters specified, but none expected");
    }
    else if (parameters.length % numParamsPerClause > 0) {
      throw new ApiException(
        "In ProductQueryBuilder.addFilter: for key "
          + filterKey
          + ", number of parameters specified ("
          + parameters.length
          + ") not a multiple of the number of parameters per clause ("
          + numParamsPerClause
          + ")");
    }
    int numClauses = parameters.length / numParamsPerClause;
    _filters.put(filterKey, new FilterData(parameters, numClauses));
    updateHint(filterKey);
  }

  /**
   * Adds the filter with the specified key to be ORed in this query.  Use
   * this method to add a filter with no parameters.
   * 
   * @param  orGroupKey  a key that identifies the group of filters that should
   * 											be ORed.  Each distinct group of filters to be ORed
   * 											must have a unique key.
   * @param  filterKey  the filter key; must be one of the <code>KEY_*</code>
   * 							 			 constants in {@link FilterMetaData}
   */
  void addFilterOr(String orGroupKey, String filterKey) {
    addFilter(filterKey);
    addFilterToOrGroup(orGroupKey, filterKey);
  }

  /**
   * Adds the filter with the specified key and parameter to be ORed in this 
   * query.
   * 
   * @param  orGroupKey  a key that identifies the group of filters that should
   * 											be ORed.  Each distinct group of filters to be ORed
   * 											must have a unique key.
   * @param  filterKey  the filter key; must be one of the <code>KEY_*</code>
   * 							 			 constants in {@link FilterMetaData}
   * @param  parameter  the filter's parameter
   */
  void addFilterOr(String orGroupKey, String filterKey, String parameter) {
    addFilter(filterKey, parameter);
    addFilterToOrGroup(orGroupKey, filterKey);
  }

  /**
   * Adds the filter with the specified key and parameters to be ORed in this 
   * query.
   * 
   * @param  orGroupKey  a key that identifies the group of filters that should
   * 											be ORed.  Each distinct group of filters to be ORed
   * 											must have a unique key.
   * @param  filterKey  the filter key; must be one of the <code>KEY_*</code>
   * 							 			 constants in {@link FilterMetaData}
   * @param  parameter  the filter's parameters
   */
  void addFilterOr(String orGroupKey, String filterKey, String[] parameters) {
    addFilter(filterKey, parameters);
    addFilterToOrGroup(orGroupKey, filterKey);
  }

  /**
   * Adds the filter with the specified key to the specified OR group.  The
   * OR group is created if necessary.
   * 
   * @param  orGroupKey  the OR group key
   * @param  filterKey  the filter key; must be one of the <code>KEY_*</code>
   * 							 			 constants in {@link FilterMetaData}
   */
  private void addFilterToOrGroup(String orGroupKey, String filterKey) {
    SortedSet orGroup = null;
    if (_orGroups.containsKey(orGroupKey)) {
      orGroup = (SortedSet) _orGroups.get(orGroupKey);
    }
    else {
      orGroup = new TreeSet();
      _orGroups.put(orGroupKey, orGroup);
    }
    orGroup.add(filterKey);

    FilterData filterData = (FilterData) _filters.get(filterKey);
    filterData.setOrGroup(orGroup);
  }

  /**
   * Return's an indication of whether the filter with the specified key has 
   * been added to this query.
   * 
   * @return  <code>true</code> if the filter has been added;
   * 					 <code>false</code> otherwise
   */
  boolean existsFilter(String filterKey) {
    return _filters.containsKey(filterKey);
  }

  /**
   * Returns the optimizer hint to include in the query.  This isn't a
   * particularly elegant mechanism at the moment because the caller has
   * to know more about the query that gets generated than is ideal.  It
   * would be nice to come up with a way to do this that is somehow based
   * on the same metadata concepts as the rest of the query builder.
   * 
   * @return the hint
   */
  public String getOptimizerHint() {
    return _optimizerHint;
  }

  /**
   * Sets the optimizer hint to include in the query.  This isn't a
   * particularly elegant mechanism at the moment because the caller has
   * to know more about the query that gets generated than is ideal.  It
   * would be nice to come up with a way to do this that is somehow based
   * on the same metadata concepts as the rest of the query builder.
   * 
   * @param optimizerHint The optimizer hint to set
   */
  public void setOptimizerHint(String optimizerHint) {
    _optimizerHint = optimizerHint;
  }

  //    /**
  //     * Returns the paramCount.
  //     * @return int
  //     */
  //    public int getParamCount() {
  //      return _paramCount;
  //    }

  /**
   * Returns the hint priority
   * @return int
   */
  public int getHintPriority() {
    return _hintPriority;
  }

  /**
   * Sets the priority for the hint
   * @param i
   */
  public void setHintPriority(int i) {
    _hintPriority = i;
  }

} // End of class ProductQueryBuilder
