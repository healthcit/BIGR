package com.gulfstreambio.kc.form.def;

/**
 * An interface that represents basic form definition information common to all types of form
 * definitions.
 * 
 * @see FormDefinitionElements
 * @see FormDefinitionElement
 * @see FormDefinitionCategory
 * @see FormDefinitionDataElement
 */
public interface FormDefinition {

  /**
   * Returns the unique KnowledgeCapture identifier for this form definition.
   * 
   * @return  The KnowledgeCapture identifier.
   */
  String getFormDefinitionId();

  /**
   * Sets the unique KnowledgeCapture identifier for this form definition.
   * 
   * @param  id  the KnowledgeCapture identifier.
   */
  void setFormDefinitionId(String id);

  /**
   * Returns the type of this form definition.  
   * 
   * @return  The type.  This will be one of the constant values defined in 
   * {@link FormDefinitionTypes}.
   */
  String getType();
  
  /**
   * Returns the form elements contained in this form definition.
   * 
   * @return  The <code>FormDefinitionElements</code>.
   */
  FormDefinitionElements getFormElements();
  
  /**
   * Returns all category definitions that comprise this form definition.  The returned array
   * is a flattened view of all descendant categories in depth-first order.  If a type of 
   * form definition implementing this interface does not support categories, its implementation 
   * of this method must throw an <code>UnsupportedOperationException</code>.
   * 
   * @return  An array of categories, or an empty array if the form definition does not have
   * any categories.
   */
  FormDefinitionCategory[] getCategories();
  
  /**
   * Returns the category definition, at any depth within the form definition, with the specified 
   * display name.  If a type of form definition implementing this interface does not support 
   * categories, its implementation of this method must throw an 
   * <code>UnsupportedOperationException</code>.
   * 
   * @param  displayName  the case-sensitive display name of the category
   * @return  The <code>FormDefinitionCategory</code>.  If such a category does not exist, 
   *          then null is returned.
   */
  FormDefinitionCategory getCategory(String displayName);

  /**
   * Returns an indication of whether there is a "page" category within this form definition. 
   * If a type of form definition implementing this interface does not support categories, its 
   * implementation of this method must return <code>false</code>.
   * 
   * @return  <code>true</code> if there is at least 1 "page" category in the form at any depth; 
   *          <code>false</code> otherwise.
   */
  boolean isHasPages();
  
  /**
   * Returns all data element definitions that comprise this form definition.  The returned array
   * is a flattened view of all descendant data elements in depth-first order.
   * 
   * @return  An array of data elements, or an empty array if the form definition does not have
   * any data elements.
   */
  FormDefinitionDataElement[] getDataElements();
  
  /**
   * Returns the data element definition, at any depth within the form definition, with the 
   * specified data element CUI or system name. 
   * 
   * @param  cuiOrSystemName the CUI or system name
   * @return  The <code>FormDefinitionDataElement</code>.  If such a data element does not  
   *          exist, then <code>null</code> is returned.
   */
  FormDefinitionDataElement getDataElement(String cuiOrSystemName);

    /**
   * Returns the name of this form definition.
   * 
   * @return  The name.
   */
  String getName();

  /**
   * Sets the name of this form definition.
   * 
   * @param  name  the name
   */
  void setName(String name);
  
  /**
   * Returns an indication of whether this form definition is enabled.
   * 
   * @return  <code>true</code> if this form definition is enabled; <code>false</code> otherwise.
   */
  boolean isEnabled();

  /**
   * Sets this form definition to either be enabled or disabled.
   * 
   * @param  enabled  <code>true</code> if this form definition is to be enabled; 
   * <code>false</code> if this form definition is to be disabled.
   */
  void setEnabled(boolean enabled);

  /**
   * Returns the set of tags associated with this form definition.
   * 
   * @return  The tags, or an empty array if there are no tags.
   */
  Tag[] getTags();

  /**
   * Adds a tag to this form definition.
   * 
   * @param  tag  the <code>Tag</code> instance
   */
  void addTag(Tag tag);

  /**
   * Creates and returns a copy of this form definition.
   * 
   * @return  The <code>FormDefinition</code>.
   */
  FormDefinition copy();

  /**
   * Converts this form definition to its XML representation, and returns that representation.
   * 
   * @return  The XML, as a string.
   */
  String toXml();

  String[] getRolesSharedTo();

  void setRolesSharedTo(String[] roles);

  String[] getDefaultSharedViews();

  void setDefaultSharedViews(String[] roles);
}
