/*
 * Created on Dec 9, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ardais.bigr.library.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @author sislam
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class WhereClause {

  public WhereClause() {
    _operator = "OR";
  }
  private String _operator;
  private List _filterKeys = new ArrayList();
  BigrLibraryMetaData _metaData;

  public void addFilterKey(FilterKey filterKey) {
    _filterKeys.add(filterKey);

  }

  /**
   * @return
   */
  public List getFilterKeys() {
    return _filterKeys;
  }

  /**
   * @return
   */
  public String getOperator() {
    return _operator;
  }

  /**
   * @param string
   */
  public void setOperator(String string) {
    _operator = string;
  }

  /*
   * Make sure that the keys used are already defined
   */
  public boolean validate(BigrLibraryMetaData data) {
    boolean verified = true;
    _metaData = data;
    HashMap filterMap = _metaData.getFilterHashMap();

    Iterator i = _filterKeys.iterator();

    while (i.hasNext()) {
      FilterKey key = (FilterKey)i.next();
      if (!filterMap.containsKey(key.getKeyName())) {
        verified = false;
        StringBuffer buff = new StringBuffer(key.getKeyName() + " cannot be found in filter collection");
        System.out.println("Warning: " + buff.toString());
      }
    }
    return verified;
  }

  public String getSqlFragment() {
    String sqlFragment = null;
    boolean start = true;
    String innerSql = null;

    Iterator i = _filterKeys.iterator();
      while (i.hasNext()) {
        FilterKey key = (FilterKey) i.next();
        Filter filter =  _metaData.getFilter(key.getKeyName());

        if (start) {
          start = false;
          innerSql = filter.getSqlFragment();
        }
        else {
          innerSql =
            innerSql
              + " "
              +  getOperator()
              + " "
              + filter.getSqlFragment();
        }
      }
    if (_filterKeys.size() > 1) {
      sqlFragment = "(" + innerSql + ")";
    }
    else {
      sqlFragment = innerSql;
    }

    return sqlFragment;
  }
}
