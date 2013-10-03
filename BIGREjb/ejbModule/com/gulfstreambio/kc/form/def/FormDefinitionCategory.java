package com.gulfstreambio.kc.form.def;

/**
 * Represents a category within a {@link FormDefinition}.  This interface contains methods common 
 * to categories across all types of KnowledgeCapture form defintions. 
 */
public interface FormDefinitionCategory {

  /**
   * Returns the category type of this form definition category.
   * 
   * @return  The category type.
   */
  public String getCategoryType();  
  
  /**
   * Returns an indication of whether this form definition category's type is page.
   * 
   * @return  <code>true</code> if this form definition category is a page;
   *          <code>false</code> otherwise.
   */
  public boolean isPage();

  /**
   * Returns an indication of whether this form definition category's type is heading.
   * 
   * @return  <code>true</code> if this form definition category is a heading;
   *          <code>false</code> otherwise.
   */
  public boolean isHeading();

  /**
   * Returns the display name of this form definition category.
   * 
   * @return  The display name.
   */
  public String getDisplayName();
  
  /**
   * Returns the form elements contained in this form definition category.
   * 
   * @return  The form elements.
   */
  public FormDefinitionElement[] getFormElements();

  /**
   * Returns an indication of whether there is a "heading" category within the form elements
   * of this category.
   * 
   * @return  <code>true</code> if there is at least 1 "heading" sub-category of this category; 
   *          <code>false</code> otherwise.
   */
  public boolean isHasHeadings();

  /**
   * Returns the form definition with which this category is associated.
   * 
   * @return  The form definition.
   */
  public FormDefinition getForm();  

}
