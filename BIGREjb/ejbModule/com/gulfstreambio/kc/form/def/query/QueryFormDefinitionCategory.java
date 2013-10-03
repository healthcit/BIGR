package com.gulfstreambio.kc.form.def.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.util.BigrXmlUtils;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionCategoryBase;
import com.gulfstreambio.kc.form.def.FormDefinitionElement;

/**
 * Represents a single category definition in a KnowledgeCapture {@link QueryFormDefinition}.
 */
public class QueryFormDefinitionCategory 
  extends FormDefinitionCategoryBase implements Serializable {

  private QueryFormDefinition _form;
  private List _formElements;
  private QueryFormDefinitionCategory _parentCategory;
  private int _xmlIndentLevel = 0;

  /**
   *  Creates a new <code>QueryFormDefinitionCategory</code>. 
   */
  public QueryFormDefinitionCategory() {
    super();
  }

  public QueryFormDefinitionCategory(QueryFormDefinitionCategory category) {
    super(category);
    QueryFormDefinitionElement[] e = category.getQueryFormElements();
    for (int i = 0; i < e.length; i++) {
      addQueryFormElement(new QueryFormDefinitionElement(e[i]));
    }     
  }

  public FormDefinitionElement[] getFormElements() {
    return getQueryFormElements();
  }

  public FormDefinition getForm() {
    return getQueryForm();
  }

  public QueryFormDefinitionElement[] getQueryFormElements() {
    if (_formElements == null) {
      return new QueryFormDefinitionElement[0];
    }
    else {
      return (QueryFormDefinitionElement[]) 
        _formElements.toArray(new QueryFormDefinitionElement[0]);
    }
  }

  public void addQueryFormElement(QueryFormDefinitionElement element) {
    if (_formElements == null) {
      _formElements = new ArrayList();
    }
    _formElements.add(element);
    element.setQueryForm(getQueryForm());
    element.setParentQueryCategory(this);
  }

  public void addQueryDataElement(QueryFormDefinitionDataElement dataElement) {
    addQueryFormElement(new QueryFormDefinitionElement(dataElement));
  }

  public void addQueryCategory(QueryFormDefinitionCategory category) {
    addQueryFormElement(new QueryFormDefinitionElement(category));
  }

  public QueryFormDefinition getQueryForm() {
    return _form;
  }

  void setQueryForm(QueryFormDefinition form) {
    _form = form;
    if (_formElements != null) {
      Iterator i = _formElements.iterator();
      while (i.hasNext()) {
        QueryFormDefinitionElement def = (QueryFormDefinitionElement) i.next();
        def.setQueryForm(form);
      }
    }
  }

  /**
   * Returns the parent category definition of this category definition.  This will return
   * <code>null</code> if the category does not have a parent category.
   * 
   * @return The parent category definition.
   */
  public QueryFormDefinitionCategory getParentQueryCategory() {
    return _parentCategory;
  }

  /**
   * Sets the parent category definition of this category definition.
   * 
   * @param  category  the parent category definition
   */
  void setParentQueryCategory(QueryFormDefinitionCategory category) {
    _parentCategory = category;
  }

  public String toXml() {
    StringBuffer buf = new StringBuffer(1024);
    toXml(buf);
    return buf.toString();
  }
 
  public void toXml(StringBuffer buf) {
    int indent = getXmlIndentLevel();
    BigrXmlUtils.writeElementStartTag(buf, "category", indent);
    BigrXmlUtils.writeAttribute(buf, "displayName", getDisplayName());
    BigrXmlUtils.writeAttribute(buf, "categoryType", getCategoryType());
    buf.append('>');
    
    QueryFormDefinitionElement[] elements = getQueryFormElements();
    for (int i = 0; i < elements.length; i++) {
      elements[i].setXmlIndentLevel(indent + 1);
      elements[i].toXml(buf);      
    }
        
    BigrXmlUtils.writeElementEndTag(buf, "category", indent);
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
    if (!(obj instanceof QueryFormDefinitionCategory)) {
      return false;
    }
    
    QueryFormDefinitionCategory cat = (QueryFormDefinitionCategory) obj;    
    String thisCategory = getCategoryType();
    String otherCategory = cat.getCategoryType();
    String thisDisplayName = getDisplayName();    
    String otherDisplayName = cat.getDisplayName();

    boolean result = true;
    result = result
        && ((thisCategory  == null) 
            ? otherCategory == null : thisCategory .equals(otherCategory));  
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
