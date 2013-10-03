package com.gulfstreambio.kc.form.def;

/**
 * Represents a single data element within a {@link FormDefinition}.  This interface contains 
 * methods common to data elements across all types of KnowledgeCapture form defintions. 
 */
public interface FormDefinitionDataElement {

  /**
   * Returns the CUI of the data element.
   * 
   * @return  The CUI.
   */
  public String getCui();

  /**
   * Returns the display name of the data element.  This only returns the display name as defined
   * in the form definition, and does not return the description from the DET if no display name
   * was supplied.
   * 
   * @return  The display name, or <code>null</code> if no display name was specified for the
   *          data element in the form definition.
   */
  public String getDisplayName();
  
  /**
   * Returns the set of tags associated with the data element.
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
