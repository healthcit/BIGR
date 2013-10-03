package com.gulfstreambio.kc.form.def;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * An abstract base class that implements the {@link FormDefinition} interface, providing default
 * implementations as appropriate.
 */
public abstract class FormDefinitionBase implements FormDefinition, Serializable {

  private String _formDefinitionId;
  private String _name;
  private Set _tags;
  private boolean _enabled;
  private String[] rolesSharedTo;
  private String[] defaultSharedViews;

  /**
   * Creates a new <code>FormDefinitionBase</code>. 
   */
  public FormDefinitionBase() {
    super();
  }
  
  /**
   * Creates a new <code>FormDefinitionBase</code> initialized from the specified one.
   * 
   * @param  form  the form definition used to initialize this form definition  
   */
  public FormDefinitionBase(FormDefinitionBase form) {
    this();
    setFormDefinitionId(form.getFormDefinitionId());
    setName(form.getName());
    setEnabled(form.isEnabled());
    Tag[] tags = form.getTags();
    for (int i = 0; i < tags.length; i++) {
      addTag(new Tag(tags[i]));
    }
	setRolesSharedTo(form.getRolesSharedTo());
	setDefaultSharedViews(form.getDefaultSharedViews());
  }

  public String getFormDefinitionId() {
    return _formDefinitionId;
  }

  public void setFormDefinitionId(String id) {
    _formDefinitionId = id;
  }

  public boolean isHasPages() {
    try {
      FormDefinitionCategory[] categories = getCategories();      
      for (int i = 0; i < categories.length; i++) {
        if (categories[i].isPage()) {
          return true;
        }
      }
      return false;
    }
    
    // If the type of form does not support categories then we'll get this exception, so catch
    // it and simply return false.
    catch (UnsupportedOperationException e) {
      return false;
    }
  }
  
  public String getName() {
    return _name;
  }

  public void setName(String name) {
    _name = name.trim();
  }

  public Tag[] getTags() {
    if (_tags == null) {
      return new Tag[0];
    }
    else {
      return (Tag[]) _tags.toArray(new Tag[0]);      
    }
  }

  public void addTag(Tag tag) {
    if (_tags == null) {
      _tags = new HashSet();
    }
    _tags.add(tag);
  }

  public boolean isEnabled() {
    return _enabled;
  }

  public void setEnabled(boolean enabled) {
    _enabled = enabled;
  }

	public String[] getRolesSharedTo()
	{
		return rolesSharedTo;
	}

	public void setRolesSharedTo(String[] rolesSharedTo)
	{
		this.rolesSharedTo = rolesSharedTo;
	}

	public String[] getDefaultSharedViews()
	{
		return defaultSharedViews;
	}

	public void setDefaultSharedViews(String[] defaultSharedViews)
	{
		this.defaultSharedViews = defaultSharedViews;
	}

  public String toXml() {
    StringBuffer buf = new StringBuffer(1024);
    toXml(buf);
    return buf.toString();
  }
  
  public abstract void toXml(StringBuffer buf);

}
