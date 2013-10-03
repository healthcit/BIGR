package com.gulfstreambio.kc.form.def;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstract base class implementation of {@link FormDefinitionDataElement} that implements common
 * behavior among all subclasses. 
 */
public abstract class FormDefinitionDataElementBase 
  implements FormDefinitionDataElement, Serializable {

  private String _cui;
  private String _displayName;
  private Set _tags;
  
  public FormDefinitionDataElementBase() {
    super();
  }

  public FormDefinitionDataElementBase(FormDefinitionDataElementBase dataElement) {
    this();
    setCui(dataElement.getCui());
    setDisplayName(dataElement.getDisplayName());
    Tag[] tags = dataElement.getTags();
    for (int i = 0; i < tags.length; i++) {
      addTag(new Tag(tags[i]));
    }
  }

  public String getCui() {
    return _cui;
  }

  /**
   * Sets the CUI of this data element.
   * 
   * @param  cui  the CUI
   */
  public void setCui(String cui) {
    _cui = cui;
  }

  public String getDisplayName() {
    return _displayName;
  }

  /**
   * Sets the display name of this data element.
   * 
   * @param  displayName  the display name
   */
  public void setDisplayName(String displayName) {
    _displayName = displayName;
  }

  public Tag[] getTags() {
    if (_tags == null) {
      return new Tag[0];
    }
    else {
      return (Tag[]) _tags.toArray(new Tag[0]);      
    }
  }

  /**
   * Adds a tag to this data element.
   * 
   * @param tag the Tag
   */
  public void addTag(Tag tag) {
    if (_tags == null) {
      _tags = new HashSet();
    }
    _tags.add(tag);
  }
}
