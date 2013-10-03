package com.gulfstreambio.kc.form.def;

/**
 * An interface to access the collection of form elements in a KnowledgeCapture 
 * {@link FormDefinition}.  Each <code>FormDefinition</code> must have a single
 * <code>FormDefinitionElements</code> instance that contains all of the elements that comprise
 * the form definition. 
 */
public interface FormDefinitionElements {

  /**
   * Returns the form elements contained in this form elements collection.
   * 
   * @return  The form elements.
   */
  public FormDefinitionElement[] getFormElements();
  
  /**
   * Returns an indication of whether there is a "heading" category within the form elements
   * of this collection.  If a type of form definition implementing this interface does not support 
   * categories, its implementation of this method must return <code>false</code>.
   * 
   * @return  <code>true</code> if there is at least 1 "heading" category in the collection; 
   *          <code>false</code> otherwise.
   */
  public boolean isHasHeadings();

  /**
   * Returns an indication of whether there is a "page" category within the form elements
   * of this collection.  If a type of form definition implementing this interface does not support 
   * categories, its implementation of this method must return <code>false</code>.
   * 
   * @return  <code>true</code> if there is at least 1 "page" category in the collection; 
   *          <code>false</code> otherwise.
   */
  public boolean isHasPages();

  /**
   * Returns the form definition with which this form elements collection is associated.
   * 
   * @return  The form definition.
   */
  public FormDefinition getForm();  

}
