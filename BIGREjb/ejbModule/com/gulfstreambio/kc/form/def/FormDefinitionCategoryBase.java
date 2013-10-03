package com.gulfstreambio.kc.form.def;

import java.io.Serializable;

import com.ardais.bigr.api.ApiException;

/**
 * Abstract base class implementation of {@link FormDefinitionCategory} that implements common
 * behavior among all subclasses. 
 */
public abstract class FormDefinitionCategoryBase implements FormDefinitionCategory, Serializable {

  private String _categoryType;
  private String _displayName;

  public FormDefinitionCategoryBase() {
    super();
  }

  public FormDefinitionCategoryBase(FormDefinitionCategoryBase category) {
    this();
    setCategoryType(category.getCategoryType());
    setDisplayName(category.getDisplayName());
  }

  public String getCategoryType() {
    return _categoryType;
  }

  /**
   * Sets the category type of this form definition category.
   * 
   * @param  type  the category type.  The value must be one of the  
   *               constants defined in {@link FormDefinitionCategoryTypes}.  
   */
  public void setCategoryType(String type) {
    if (FormDefinitionCategoryTypes.isValidCategoryType(type)) {
      _categoryType = type;
    }
    else {
      throw new ApiException("Attempt to set invalid category type ('" + type + "')");
    }
  }

  public boolean isPage() {
    return FormDefinitionCategoryTypes.PAGE.equals(getCategoryType());
  }

  public boolean isHeading() {
    return FormDefinitionCategoryTypes.HEADING.equals(getCategoryType());
  }

  public String getDisplayName() {
    return _displayName;
  }

  /**
   * Sets the display name of this form definition category.
   * 
   * @param  name  the display name
   */
  public void setDisplayName(String name) {
    _displayName = name;
  }

  public boolean isHasHeadings() {
    FormDefinitionElement[] elements = getFormElements(); 
    for (int i = 0; i < elements.length; i++) {
      FormDefinitionElement element = elements[i];
      if (element.isCategory()) {
        FormDefinitionCategory category = element.getCategory();
        if (category.isHeading()) {
          return true;
        }     
      }
    }
    return false;
  }
}
