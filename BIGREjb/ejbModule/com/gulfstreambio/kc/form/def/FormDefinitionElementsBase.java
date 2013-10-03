package com.gulfstreambio.kc.form.def;

import java.io.Serializable;

/**
 * Abstract base class implementation of {@link FormDefinitionElements} that implements common
 * behavior among all subclasses. 
 */
public abstract class FormDefinitionElementsBase implements FormDefinitionElements, Serializable {

  public FormDefinitionElementsBase() {
    super();
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

  public boolean isHasPages() {
    FormDefinitionElement[] elements = getFormElements(); 
    for (int i = 0; i < elements.length; i++) {
      FormDefinitionElement element = elements[i];
      if (element.isCategory()) {
        FormDefinitionCategory category = element.getCategory();
        if (category.isPage()) {
          return true;
        }     
      }
    }
    return false;
  }
}

