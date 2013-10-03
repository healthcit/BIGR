package com.ardais.bigr.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import com.ardais.bigr.api.ApiFunctions;
import com.gulfstreambio.gboss.GbossValue;

public class LabelTemplateDefinition implements Serializable, Comparable {
  private String _name;
  private String _displayName;
  private String _objectType;
  private String _objectSubtypes;
  private List _objectSubtypesList = new ArrayList();
  private String _resultsFormDefinitionName;
  private Account _parent;
  private boolean _immutable = false;
  private final String LABEL_PRINTING_OBJECT_SUBTYPE_DELIMITER = ",";
  
  /**
   * @return Returns the displayName.
   */
  public String getDisplayName() {
    return _displayName;
  }
  
  /**
   * @return Returns the name.
   */
  public String getName() {
    return _name;
  }
  
  /**
   * @return Returns the objectSubtypes.
   */
  public String getObjectSubtypes() {
    return _objectSubtypes;
  }
  
  public Collection getObjectSubtypesCollection() {
    return _objectSubtypesList;
  }
  
  /**
   * @return Returns the objectType.
   */
  public String getObjectType() {
    return _objectType;
  }
  
  /**
   * @return Returns the resultsFormDefinitionName.
   */
  public String getResultsFormDefinitionName() {
    return _resultsFormDefinitionName;
  }
  
  Account getParent() {
    return _parent;
  }
  
  /**
   * @param displayName The displayName to set.
   */
  public void setDisplayName(String displayName) {
    checkImmutable();
    _displayName = displayName;
  }
  
  /**
   * @param id The name to set.
   */
  public void setName(String name) {
    checkImmutable();
    _name = name;
  }
  
  /**
   * @param objectSubtypes The objectSubtypes to set.
   */
  public void setObjectSubtypes(String objectSubtypes) {
    checkImmutable();
    _objectSubtypes = objectSubtypes;
    if (!ApiFunctions.isEmpty(_objectSubtypes)) {
      StringTokenizer tokenizer = new StringTokenizer(objectSubtypes, LABEL_PRINTING_OBJECT_SUBTYPE_DELIMITER);
      while (tokenizer.hasMoreTokens()) {
        String objectSubtype = ApiFunctions.safeTrim(tokenizer.nextToken());
        _objectSubtypesList.add(objectSubtype);
      }
    }
  }
  
  /**
   * @param objectType The objectType to set.
   */
  public void setObjectType(String objectType) {
    checkImmutable();
    _objectType = objectType;
  }
  
  /**
   * @param resultsFormDefinitionName The resultsFormDefinitionName to set.
   */
  public void setResultsFormDefinitionName(String resultsFormDefinitionName) {
    checkImmutable();
    _resultsFormDefinitionName = resultsFormDefinitionName;
  }
  
  void setParent(Account parent) {
    checkImmutable();
    _parent = parent;
  }

  /**
   * Throw an exception if this instance is immutable.
   */
  private void checkImmutable() {
    if (_immutable) {
      throw new IllegalStateException("Attempted to modify an immutable LabelTemplateDefinition: " + this);
    }
  }
  
  /**
   * Mark this instance as immutable.
   */
  public void setImmutable() {
    _immutable = true;
    _objectSubtypesList = Collections.unmodifiableList(_objectSubtypesList);
  }

  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   * @return  a negative integer, zero, or a positive integer as this object
   *    is less than, equal to, or greater than the specified object.
   * 
   * @throws ClassCastException if the specified object's type prevents it
   *         from being compared to this Object.
   */
  public int compareTo(Object o) {
    LabelTemplateDefinition otherTemplateDefinition = (LabelTemplateDefinition)o;
    return getDisplayName().compareTo(otherTemplateDefinition.getDisplayName());
  }
  
}
