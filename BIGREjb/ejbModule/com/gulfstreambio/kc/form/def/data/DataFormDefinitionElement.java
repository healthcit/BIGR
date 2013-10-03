package com.gulfstreambio.kc.form.def.data;

import java.io.Serializable;

import com.ardais.bigr.api.ApiException;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionCategory;
import com.gulfstreambio.kc.form.def.FormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.FormDefinitionElement;
import com.gulfstreambio.kc.form.def.FormDefinitionHostElement;

/**
 * Represents a single form element definition in a KnowledgeCapture {@link DataFormDefinition}.
 */
public class DataFormDefinitionElement implements FormDefinitionElement, Serializable {

  private DataFormDefinition _form;
  private DataFormDefinitionCategory _category;
  private DataFormDefinitionCategory _parentCategory;
  private DataFormDefinitionDataElement _dataElement;
  private DataFormDefinitionHostElement _hostElement;
  private int _xmlIndentLevel = 0;

  /**
   *  Creates a new <code>DataFormDefinitionElement</code>. 
   */
  public DataFormDefinitionElement() {
    super();
  }

  /**
   *  Creates a new <code>DataFormDefinitionElement</code> from the specified 
   * <code>DataFormDefinitionElement</code> 
   * 
   * @param  element the <code>DataFormDefinitionElement</code>
   */
  public DataFormDefinitionElement(DataFormDefinitionElement element) {
    this();
    if (element.isCategory()) {
      setDataCategory(new DataFormDefinitionCategory(element.getDataCategory()));
    }
    else if (element.isDataElement()) {
      setDataDataElement(new DataFormDefinitionDataElement(element.getDataDataElement()));
    }
    else if (element.isHostElement()) {
      setDataHostElement(new DataFormDefinitionHostElement(element.getDataHostElement()));
    }
    else {
      throw new ApiException("Unrecognized data form definition element in constructor");
    }
  }

  /**
   *  Creates a new <code>DataFormDefinitionElement</code> from the specified 
   * <code>DataFormDefinitionDataElement</code> 
   * 
   * @param  dataElement the <code>DataFormDefinitionDataElement</code>
   */
  public DataFormDefinitionElement(DataFormDefinitionDataElement dataElement) {
    this();
    setDataDataElement(dataElement);
  }

  /**
   *  Creates a new <code>DataFormDefinitionElement</code> from the specified 
   * <code>DataFormDefinitionHostElement</code> 
   * 
   * @param  hostElement the <code>DataFormDefinitionHostElement</code>
   */
  public DataFormDefinitionElement(DataFormDefinitionHostElement hostElement) {
    this();
    setDataHostElement(hostElement);
  }

  /**
   *  Creates a new <code>DataFormDefinitionElement</code> from the specified 
   * <code>DataFormDefinitionCategory</code>
   * 
   * @param  category the <code>DataFormDefinitionCategory</code>
   */
  public DataFormDefinitionElement(DataFormDefinitionCategory category) {
    this();
    setDataCategory(category);
  }

  public boolean isCategory() {
    return (_category != null);
  }

  public boolean isDataElement() {
    return (_dataElement != null);
  }

  public boolean isHostElement() {
    return (_hostElement != null);
  }
  
  public FormDefinitionCategory getCategory() {
    return getDataCategory();
  }

  public FormDefinitionDataElement getDataElement() {
    return getDataDataElement();
  }

  public FormDefinitionHostElement getHostElement() {
    return getDataHostElement();
  }

  public FormDefinition getForm() {
    return getDataForm();
  }

  /**
   * Returns the category definition if this form element definition contains a category definition.
   *  
   * @return  The category definition, or null if this form element definition does not contain 
   *          a category definition.
   */
  public DataFormDefinitionCategory getDataCategory() {
    return _category;
  }

  /**
   * Sets the category definition contained in this form element definition.  If this form element
   * definition already contains a data element definition, an exception is thrown.
   * 
   * @param  category  the category definition
   */
  public void setDataCategory(DataFormDefinitionCategory category) {
    if (isDataElement()) {
      throw new ApiException("Attempt to set a category definition for a form definition that contains a data element definition.");
    }
    _category = category;
    category.setDataForm(getDataForm());
  }

  /**
   * Returns the data element definition if this form element definition contains a data element
   * definition.
   *  
   * @return  The data element definition, or null if this form element definition does not contain 
   *          a data element definition.
   */
  public DataFormDefinitionDataElement getDataDataElement() {
    return _dataElement;
  }

  /**
   * Sets the category definition contained in this form element definition.  If this form element
   * definition already contains a data element definition, an exception is thrown.
   * 
   * @param  dataElement  the data element definition
   */
  public void setDataDataElement(DataFormDefinitionDataElement dataElement) {
    if (isCategory()) {
      throw new ApiException("Attempt to set a data element definition for a form definition that contains a category definition.");
    }
    _dataElement = dataElement;
    dataElement.setDataForm(getDataForm());
  }

  /**
   * Returns the host element if this form element definition contains a host element.
   *  
   * @return  The host element, or null if this form element definition does not contain 
   *          a host element.
   */
  public DataFormDefinitionHostElement getDataHostElement() {
    return _hostElement;
  }

  /**
   * Sets the host element contained in this form element definition.  If this form element
   * definition already contains a data element definition or category, an exception is thrown.
   * 
   * @param  hostElement the host element
   */
  public void setDataHostElement(DataFormDefinitionHostElement hostElement) {
    if (isDataElement()) {
      throw new ApiException("Attempt to set a host element for a form definition that contains a data element definition.");
    }
    if (isCategory()) {
      throw new ApiException("Attempt to set a host element for a form definition that contains a category.");
    }
    _hostElement = hostElement;
    _hostElement.setDataForm(getDataForm());
  }


  /**
   * Returns the parent category definition of this form element definition.  This will return
   * <code>null</code> if the form element does not have a parent category.
   * 
   * @return The parent category definition.
   */
  public DataFormDefinitionCategory getParentDataCategory() {
    return _parentCategory;
  }

  /**
   * Sets the parent category definition of this form element definition.
   * 
   * @param  category  the parent category definition
   */
  void setParentDataCategory(DataFormDefinitionCategory category) {
    _parentCategory = category;
    if (isCategory()) {
      getDataCategory().setParentDataCategory(category);
    }
    else if (isDataElement()) {
      getDataDataElement().setParentDataCategory(category);      
    }
  }

  public DataFormDefinition getDataForm() {
    return _form;
  }

  void setDataForm(DataFormDefinition form) {
    _form = form;
    if (isCategory()) {
      getDataCategory().setDataForm(form);
    }
    else if (isDataElement()) {
      getDataDataElement().setDataForm(form);
    }
    else if (isHostElement()) {
      getDataHostElement().setDataForm(form);
    }
  }

  public String toXml() {
    StringBuffer buf = new StringBuffer(1024);
    toXml(buf);
    return buf.toString();
  }
 
  public void toXml(StringBuffer buf) {
    int indent = getXmlIndentLevel();
    if (isCategory()) {
      DataFormDefinitionCategory category = getDataCategory();
      category.setXmlIndentLevel(indent);
      category.toXml(buf);
    }
    else if (isDataElement()) {
      DataFormDefinitionDataElement element = getDataDataElement();
      element.setXmlIndentLevel(indent);
      element.toXml(buf);
    }
    else if (isHostElement()) {
      DataFormDefinitionHostElement element = getDataHostElement();
      element.setXmlIndentLevel(indent);
      element.toXml(buf);
    }
  }

  private int getXmlIndentLevel() {
    return _xmlIndentLevel;
  }

  void setXmlIndentLevel(int indentLevel) {
    _xmlIndentLevel = indentLevel;
  }
    
  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof DataFormDefinitionElement)) {
      return false;
    }
    
    DataFormDefinitionElement fe = (DataFormDefinitionElement) obj;
    
    if (isCategory()) {
      if (!fe.isCategory()) {
        return false;
      }
      else {
        return getDataCategory().equals(fe.getDataCategory()); 
      }
    }
    else if (isDataElement()) {
      if (!fe.isDataElement()) {
        return false;
      }
      else {
        return getDataDataElement().equals(fe.getDataDataElement()); 
      }
    }
    else if (isHostElement()) {
      if (!fe.isHostElement()) {
        return false;
      }
      else {
        return getDataHostElement().equals(fe.getDataHostElement()); 
      }
    }
    else {
      throw new ApiException("Error in DataFormDefinitionElement.equals: form element unaccounted for.");
    }

  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  public int hashCode() {
    int result = 17;
    
    if (isCategory()) {
      result = 37*result + getDataCategory().hashCode();
    }
    else if (isDataElement()) {
      result = 37*result + getDataDataElement().hashCode();
    }
    else if (isHostElement()) {
      result = 37*result + getDataHostElement().hashCode();
    }
    else {
      throw new ApiException("Error in DataFormDefinitionElement.hashCode: form element unaccounted for.");
    }

    return result;
  }

}
