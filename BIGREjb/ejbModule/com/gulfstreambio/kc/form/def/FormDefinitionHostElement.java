package com.gulfstreambio.kc.form.def;

/**
 * Represents a single host element within a {@link FormDefinition}.  This interface contains 
 * methods common to host elements across all types of KnowledgeCapture form defintions. 
 */
public interface FormDefinitionHostElement {
  
  /**
   * Returns the id of the host element.
   * 
   * @return  The id.
   */
  public String getHostId();
  
  /**
   * Returns the display name of the host element.
   * 
   * @return  The display name.
   */
  public String getDisplayName();

  /**
   * Returns the set of tags associated with the host element.
   *  
   * @return An array of tags.
   */
  public Tag[] getTags();
  
  /**
   * Returns the form definition with which this data element is associated.
   * 
   * @return  The form definition.
   */
  public FormDefinition getForm();  
}
