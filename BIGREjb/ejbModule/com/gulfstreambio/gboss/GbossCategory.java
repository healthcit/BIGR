package com.gulfstreambio.gboss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;

public class GbossCategory extends GbossConcept {
  
  private final static String DB_TYPE_CONVENTIONAL = "conventional"; 
  private final static String DB_TYPE_EAV = "eav"; 
  
  private List _categoryList = new ArrayList();
  private Map _categoryMap = new HashMap();
  private List _dataElementList = new ArrayList();
  private Map _dataElementMap = new HashMap();
  private List _descendantDataElementList;
  private String _systemName = null;
  private String _databaseType = null;
  private GbossCategory _parentCategory = null;

  /**
   * Create a new GbossCategory object.
   */
  public GbossCategory() {
    super();
  }
  
  public String toHtml() {
    StringBuffer buff = new StringBuffer(100);
    buff.append(super.toHtml());
    if (!ApiFunctions.isEmpty(getSystemName())) {
      buff.append(", ");
      buff.append("system name = ");
      buff.append(Escaper.htmlEscapeAndPreserveWhitespace(getSystemName()));
    }
    if (!ApiFunctions.isEmpty(getDatabaseType())) {
      buff.append(", ");
      buff.append("database type = ");
      buff.append(Escaper.htmlEscapeAndPreserveWhitespace(getDatabaseType()));
    }
    return buff.toString();
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
      category.setParentCategory(this);
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
   * @return Returns this category if it is a database category, and otherwise returns the 
   * ancestor category that is the database category.
   */
  public GbossCategory getDatabaseCategory() {
    GbossCategory category = this;
    while (category != null) {
      if (!ApiFunctions.isEmpty(category.getDatabaseType())) {
        return category;
      }
      category = category.getParentCategory();
    }
    
    // We should never get here since validation ensures that each category is either a database
    // category or has exactly one database category ancestor, so if we do get here throw an 
    // exception.
    throw new ApiException("Could not determine database category for category " + getCui());
  }

  /**
   * Retrieve a list of the Categories contained by this GbossCategory
   * @return a list of the Categories contained by this GbossCategory
   */
  public List getCategories() {
    return _categoryList;
  }

  /**
   * Add a new GbossDataElement
   * @param dataElement  the GbossDataElement to add
   */
  public void addDataElement(GbossDataElement dataElement) {    
    checkImmutable();
    if (dataElement != null) {
      _dataElementList.add(dataElement);
      _dataElementMap.put(dataElement.getCui(), dataElement);
      dataElement.setParentCategory(this);
    }
  }
  
  /**
   * Retrieve a specific GbossDataElement
   * @param cui  the cui of the GbossDataElement to be retrieved
   * @return  A GbossDataElement with the specified cui, or null if no such GbossDataElement exists
   */
  public GbossDataElement getDataElement(String cui) {
    GbossDataElement returnValue = (GbossDataElement)_dataElementMap.get(cui);
    return returnValue;
  }
  
  /**
   * Retrieve a list of the DataElements contained by this GbossCategory
   * @return a list of the DataElements contained by this GbossCategory
   */
  public List getDataElements() {
    return _dataElementList;
  }

  /**
   * Returns a list of all data elements that are descendants of this category.
   * 
   * @return  The list of descendant data elements.
   */
  public List getDataElementsAllDescendants() {
    return _descendantDataElementList;
  }

  /**
   * Set this instance to be immutable.  Any attempts to modify immutable
   * instances will throw an exception.
   */
  public void setImmutable() {
    super.setImmutable();
    //mark each GbossCategory immutable
    Iterator categories = _categoryList.iterator();
    while (categories.hasNext()) {
      ((GbossCategory)categories.next()).setImmutable();
    }
    //mark each GbossDataElement immutable
    Iterator dataElements = _dataElementList.iterator();
    while (dataElements.hasNext()) {
      ((GbossDataElement)dataElements.next()).setImmutable();
    }
    //make the lists and maps unmodifiable
    _categoryList = Collections.unmodifiableList(_categoryList);
    _categoryMap = Collections.unmodifiableMap(_categoryMap);
    _dataElementList = Collections.unmodifiableList(_dataElementList);
    _dataElementMap = Collections.unmodifiableMap(_dataElementMap);
  }
  
  /**
   * @return Returns the systemName.
   */
  public String getSystemName() {
    return _systemName;
  }
  
  /**
   * @param systemName The systemName to set.
   */
  public void setSystemName(String systemName) {
    checkImmutable();
    _systemName = systemName;
  }
  
  /**
   * @return Returns the databaseType.
   */
  public String getDatabaseType() {
    return _databaseType;
  }
  
  /**
   * @param databaseType The databaseType to set.
   */
  public void setDatabaseType(String databaseType) {
    checkImmutable();
    _databaseType = databaseType;
  }
  
  public boolean isDatabaseTypeEav() {
    return DB_TYPE_EAV.equals(getDatabaseType());
  }
  
  public boolean isDatabaseTypeConventional() {
    return DB_TYPE_CONVENTIONAL.equals(getDatabaseType());
  }
  
  /**
   * @return Returns the parentCategory.
   */
  public GbossCategory getParentCategory() {
    return _parentCategory;
  }
  
  /**
   * @param parentCategory The parentCategory to set.
   */
  public void setParentCategory(GbossCategory parentCategory) {
    checkImmutable();
    _parentCategory = parentCategory;
  }

  public boolean hasSingleValuedDataElements() {
    return hasSingleValuedDataElements(this);
  }
  
  private boolean hasSingleValuedDataElements(GbossCategory category) {
    List dataElements = category.getDataElements();
    for (int i = 0; i < dataElements.size(); i++) {
      GbossDataElement dataElement = (GbossDataElement) dataElements.get(i);
      if (!dataElement.isMultiValued()) {
        return true;
      }
    }

    List categories = category.getCategories();
    for (int i = 0; i < categories.size(); i++) {
      GbossCategory subcategory = (GbossCategory) categories.get(i);
      if (hasSingleValuedDataElements(subcategory)) {
        return true;
      }
    }

    return false;
  }
  
  public boolean hasMultivaluedDataElements() {
    return hasMultivaluedDataElements(this);
  }
  
  private boolean hasMultivaluedDataElements(GbossCategory category) {
    List dataElements = category.getDataElements();
    for (int i = 0; i < dataElements.size(); i++) {
      GbossDataElement dataElement = (GbossDataElement) dataElements.get(i);
      if (dataElement.isMultiValued()) {
        return true;
      }
    }

    List categories = category.getCategories();
    for (int i = 0; i < categories.size(); i++) {
      GbossCategory subcategory = (GbossCategory) categories.get(i);
      if (hasMultivaluedDataElements(subcategory)) {
        return true;
      }
    }

    return false;
  }

  void initCache() {
    _descendantDataElementList = new ArrayList();
    initCacheCategoryAndDataElements(this);
  }
  
  private void initCacheCategoryAndDataElements(GbossCategory category) {
    List categories = category.getCategories();    
    for (int i = 0; i < categories.size(); i++) {
      initCacheCategoryAndDataElements((GbossCategory)categories.get(i));
    }
    
    List dataElements = category.getDataElements();
    for (int i = 0; i < dataElements.size(); i++) {
      GbossDataElement dataElement = (GbossDataElement) dataElements.get(i);
      _descendantDataElementList.add(dataElement);
    }
  }  
}
