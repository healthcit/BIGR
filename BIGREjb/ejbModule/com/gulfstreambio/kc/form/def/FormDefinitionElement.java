package com.gulfstreambio.kc.form.def;

/**
 * Represents a single form element within a KnowledgeCapture {@link FormDefinition}.  
 * A <code>FormDefinitionElement</code> instance is contained within a 
 * {@link FormDefinitionElements} instance, where the <code>FormDefinitionElements</code>
 * instance is a simple collection of all form elements.  A single <code>FormDefinition</code> 
 * may be composed of heterogeneous elements, and each is represented by a single 
 * <code>FormDefinitionElement</code>.  Thus a <code>FormDefinitionElement</code> is 
 * simply a container for exactly one of the heterogeneous elements that comprise a 
 * <code>FormDefinition</code>.
 */
public interface FormDefinitionElement {

  /**
   * Returns an indication of whether this form definition element is a category.
   *  
   * @return  <code>true</code> if this form definition element is a category;
   *          <code>false</code> otherwise.
   */
  public boolean isCategory();

  /**
   * Returns an indication of whether this form definition element is a data element.
   *  
   * @return  <code>true</code> if this form definition element is a data element;
   *          <code>false</code> otherwise.
   */
  public boolean isDataElement();

  /**
   * Returns an indication of whether this form definition element is a host element.
   *  
   * @return  <code>true</code> if this form definition element is a host element;
   *          <code>false</code> otherwise.
   */
  public boolean isHostElement();

  /**
   * Returns the category contained within this form definition element. 
   *  
   * @return  The <code>FormDefinitionCategory</code>, or <code>null</code> if this form definition
   *          element is not a category.
   */
  public FormDefinitionCategory getCategory();

  /**
   * Returns the data element contained within this form definition element. 
   *  
   * @return  The <code>FormDefinitionDataElement</code>, or <code>null</code> if this form 
   *          definition element is not a data element.
   */
  public FormDefinitionDataElement getDataElement();
  
  /**
   * Returns the host element contained within this form definition element. 
   *  
   * @return  The <code>FormDefinitionHostElement</code>, or <code>null</code> if this form 
   *          definition element is not a host element.
   */
  public FormDefinitionHostElement getHostElement();

  /**
   * Returns the form definition with which this form element is associated.
   * 
   * @return  The form definition.
   */
  public FormDefinition getForm();  
  
}
