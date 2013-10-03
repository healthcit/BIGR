package com.gulfstreambio.gboss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GbossDataElementTaxonomy {
  
  private List _categoryList = new ArrayList();
  private Map _categoryMap = new HashMap();
  private boolean _immutable = false;

  /**
   * Create a new GbossDataElementTaxonomy object.
   */
  public GbossDataElementTaxonomy() {
    super();
  }

  /**
   * Add a new GbossCategory
   * @param category  the GbossCategory to add
   */
  public void addCategory(GbossCategory category) {    
    checkImmutable();
    if (category != null) {
      _categoryList.add(category);
      _categoryMap.put(category.getCui(), category);
    }
  }
  
  /**
   * Retrieve a specific GbossCategory
   * @param cui  the cui of the GbossCategory to be retrieved
   * @return  A GbossCategory with the specified cui, or null if no such GbossCategory exists
   */
  public GbossCategory getCategory(String cui) {
    GbossCategory returnValue = (GbossCategory)_categoryMap.get(cui);
    return returnValue;
  }
  
  /**
   * Retrieve a list of the Categories contained by this GbossDataElementTaxonomy
   * @return a list of the Categories contained by this GbossDataElementTaxonomy
   */
  public List getCategories() {
    return _categoryList;
  }
  
  /**
   * Set this instance to be immutable.  Any attempts to modify immutable
   * instances will throw an exception.
   */
  public void setImmutable() {
    _immutable = true;
    //mark each GbossCategory immutable
    Iterator categories = _categoryList.iterator();
    while (categories.hasNext()) {
      ((GbossCategory)categories.next()).setImmutable();
    }
    //make the list and map unmodifiable
    _categoryList = Collections.unmodifiableList(_categoryList);
    _categoryMap = Collections.unmodifiableMap(_categoryMap);
  }

  /**
   * Is this instance immutable.
   */
  public boolean isImmutable() {
    return _immutable;
  }

  /**
   * Throw an exception if this instance is immutable.
   */
  private void checkImmutable() {
    if (_immutable) {
      throw new IllegalStateException("Attempted to modify an immutable GbossDataElementTaxonomy: " + this);
    }
  }

}
