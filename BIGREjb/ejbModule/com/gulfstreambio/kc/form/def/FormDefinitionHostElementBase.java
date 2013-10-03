package com.gulfstreambio.kc.form.def;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public abstract class FormDefinitionHostElementBase 
  implements FormDefinitionHostElement, Serializable {

  private String _hostId;
  private String _displayName;
  private Set _tags;
  
  public FormDefinitionHostElementBase() {
    super();
  }

  public FormDefinitionHostElementBase(FormDefinitionHostElementBase hostElement) {
    this();
    setHostId(hostElement.getHostId());
    setDisplayName(hostElement.getDisplayName());
    Tag[] tags = hostElement.getTags();
    for (int i = 0; i < tags.length; i++) {
      addTag(new Tag(tags[i]));
    }
  }

  public String getHostId() {
    return _hostId;
  }
  
  /**
   * Sets the host id of this host element.
   * 
   * @param id the host id
   */
  public void setHostId(String id) {
    _hostId = id;
  }

  public String getDisplayName() {
    return _displayName;
  }

  /**
   * Sets the display name of this host element.
   * 
   * @param displayName the display name
   */
  public void setDisplayName(String displayName) {
    _displayName = displayName;
  }

  /**
   * Adds the specified tag to this host element.
   * 
   * @param tag the Tag
   */
  public void addTag(Tag tag) {
    if (_tags == null) {
      _tags = new HashSet();
    }
    _tags.add(tag);
  }

  public Tag[] getTags() {
    if (_tags == null) {
      return new Tag[0];
    }
    else {
      return (Tag[]) _tags.toArray(new Tag[0]);      
    }
  }

}
