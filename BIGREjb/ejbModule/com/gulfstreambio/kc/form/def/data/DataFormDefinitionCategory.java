package com.gulfstreambio.kc.form.def.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.util.BigrXmlUtils;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionCategory;
import com.gulfstreambio.kc.form.def.FormDefinitionCategoryBase;
import com.gulfstreambio.kc.form.def.FormDefinitionElement;

/**
 * Represents a single category definition in a KnowledgeCapture {@link DataFormDefinition}.
 */
public class DataFormDefinitionCategory extends FormDefinitionCategoryBase implements Serializable {

  private DataFormDefinition _form;
  private List _formElements;
  private DataFormDefinitionCategory _parentCategory;
  private int _xmlIndentLevel = 0;

  /**
   *  Creates a new <code>DataFormDefinitionCategory</code>. 
   */
  public DataFormDefinitionCategory() {
    super();
  }

  public DataFormDefinitionCategory(DataFormDefinitionCategory category) {
    super(category);
    DataFormDefinitionElement[] e = category.getDataFormElements();
    for (int i = 0; i < e.length; i++) {
      addDataFormElement(new DataFormDefinitionElement(e[i]));
    }     
  }

  public FormDefinitionElement[] getFormElements() {
    return getDataFormElements();
  }

  public FormDefinition getForm() {
    return getDataForm();
  }

  public DataFormDefinitionElement[] getDataFormElements() {
    if (_formElements == null) {
      return new DataFormDefinitionElement[0];
    }
    else {
      return (DataFormDefinitionElement[]) 
        _formElements.toArray(new DataFormDefinitionElement[0]);
    }
  }

  public void addDataFormElement(DataFormDefinitionElement element) {
    if (_formElements == null) {
      _formElements = new ArrayList();
    }
    _formElements.add(element);
    element.setDataForm(getDataForm());
    element.setParentDataCategory(this);
  }

  public void addDataDataElement(DataFormDefinitionDataElement dataElement) {
    addDataFormElement(new DataFormDefinitionElement(dataElement));
  }

  public void addDataHostElement(DataFormDefinitionHostElement hostElement) {
    addDataFormElement(new DataFormDefinitionElement(hostElement));
  }

  public void addDataCategory(DataFormDefinitionCategory category) {
    addDataFormElement(new DataFormDefinitionElement(category));
  }

  public DataFormDefinition getDataForm() {
    return _form;
  }

  void setDataForm(DataFormDefinition form) {
    _form = form;
    if (_formElements != null) {
      Iterator i = _formElements.iterator();
      while (i.hasNext()) {
        DataFormDefinitionElement def = (DataFormDefinitionElement) i.next();
        def.setDataForm(form);
      }
    }
  }

  /**
   * Returns the parent category definition of this category definition.  This will return
   * <code>null</code> if the category does not have a parent category.
   * 
   * @return The parent category definition.
   */
  public DataFormDefinitionCategory getParentDataCategory() {
    return _parentCategory;
  }

  /**
   * Sets the parent category definition of this category definition.
   * 
   * @param  category  the parent category definition
   */
  void setParentDataCategory(DataFormDefinitionCategory category) {
    _parentCategory = category;
  }
  
  /**
   * Returns an indication of whether this category or any of its sub-categories have any required
   * elements.
   * 
   * @return <code>true</code> if this category or any of its sub-categories have at least one
   *         required data element or host element; <code>false</code> otherwise.
   */
  public boolean isHasRequiredElements() {
    boolean hasRequired = false;
    if (_formElements != null) {
      Iterator i = _formElements.iterator();
      while (i.hasNext() && !hasRequired) {
        DataFormDefinitionElement element = (DataFormDefinitionElement) i.next();
        if (element.isCategory()) {
          hasRequired = element.getDataCategory().isHasRequiredElements();
        }
        else if (element.isDataElement()) {
          hasRequired = element.getDataDataElement().isRequired();
        }
        else if (element.isHostElement()) {
          hasRequired = element.getDataHostElement().isRequired();
        }
      }
    }
    return hasRequired;
  }

  public String toXml() {
    StringBuffer buf = new StringBuffer(1024);
    toXml(buf);
    return buf.toString();
  }
 
  public void toXml(StringBuffer buf) {
    int indent = getXmlIndentLevel();
    BigrXmlUtils.writeElementStartTag(buf, "categoryDefinition", indent);
    BigrXmlUtils.writeAttribute(buf, "displayName", getDisplayName());
    BigrXmlUtils.writeAttribute(buf, "categoryType", getCategoryType());
    buf.append('>');
    
    DataFormDefinitionElement[] defs = getDataFormElements();
    for (int i = 0; i < defs.length; i++) {
      defs[i].setXmlIndentLevel(indent + 1);
      defs[i].toXml(buf);
    }
        
    BigrXmlUtils.writeElementEndTag(buf, "categoryDefinition", indent);
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
    if (!(obj instanceof DataFormDefinitionCategory)) {
      return false;
    }
    
    DataFormDefinitionCategory cat = (DataFormDefinitionCategory) obj;    
    String thisCategory = getCategoryType();
    String otherCategory = cat.getCategoryType();
    String thisDisplayName = getDisplayName();    
    String otherDisplayName = cat.getDisplayName();
    
    boolean result = true;
    result = result
        && ((thisCategory == null) 
            ? otherCategory == null : thisCategory.equals(otherCategory));  
    result = result
        && ((thisDisplayName == null) 
            ? otherDisplayName == null : thisDisplayName.equals(otherDisplayName));
    result =
      result
        && ((_formElements == null)
          ? cat._formElements == null : _formElements.equals(cat._formElements));
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  public int hashCode() {
    String category = getCategoryType();
    String displayName = getDisplayName();    

    int result = 17;
    
    result = 37*result + ((category == null) ? 0 : category.hashCode());
    result = 37*result + ((displayName == null) ? 0 : displayName.hashCode());
    result =
      37 * result + ((_formElements == null) ? 0 : _formElements.hashCode());
    
    return result;
  }

}
