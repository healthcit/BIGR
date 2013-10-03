package com.gulfstreambio.kc.form.def.query;

import java.io.Serializable;

import com.ardais.bigr.api.ApiException;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionCategory;
import com.gulfstreambio.kc.form.def.FormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.FormDefinitionElement;
import com.gulfstreambio.kc.form.def.FormDefinitionHostElement;

/**
 * Represents a single form element definition in a KnowledgeCapture {@link QueryFormDefinition}.
 */
public class QueryFormDefinitionElement implements FormDefinitionElement, Serializable {

  private QueryFormDefinition _form;
  private QueryFormDefinitionCategory _category;
  private QueryFormDefinitionCategory _parentCategory;
  private QueryFormDefinitionDataElement _dataElement;
  private int _xmlIndentLevel = 0;

  /**
   *  Creates a new <code>QueryFormDefinitionElement</code>. 
   */
  public QueryFormDefinitionElement() {
    super();
  }

  /**
   * Creates a new <code>QueryFormDefinitionElement</code> from the specified 
   * <code>QueryFormDefinitionElement</code> 
   * 
   * @param  element the <code>QueryFormDefinitionElement</code>
   */
  public QueryFormDefinitionElement(QueryFormDefinitionElement element) {
    this();
    if (element.isCategory()) {
      setQueryCategory(new QueryFormDefinitionCategory(element.getQueryCategory()));
    }
    else if (element.isDataElement()) {
      setQueryDataElement(new QueryFormDefinitionDataElement(element.getQueryDataElement()));      
    }
    else {
      throw new ApiException("Unrecognized query form definition element in constructor");
    }
  }

  /**
   * Creates a new <code>QueryFormDefinitionElement</code> from the specified 
   * <code>QueryFormDefinitionDataElement</code> 
   * 
   * @param  dataElement  the <code>QueryFormDefinitionDataElement</code>
   */
  public QueryFormDefinitionElement(QueryFormDefinitionDataElement dataElement) {
    this();
    setQueryDataElement(dataElement);
  }

  /**
   * Creates a new <code>QueryFormDefinitionElement</code> from the specified 
   * <code>QueryFormDefinitionCategory</code>
   * 
   * @param  category  the <code>QueryFormDefinitionCategory</code>
   */
  public QueryFormDefinitionElement(QueryFormDefinitionCategory category) {
    this();
    setQueryCategory(category);
  }

  public boolean isCategory() {
    return (_category != null);
  }

  public boolean isDataElement() {
    return (_dataElement != null);
  }
  
  public boolean isHostElement() {
    return false;
  }

  public FormDefinitionCategory getCategory() {
    return getQueryCategory();
  }
  
  public FormDefinitionDataElement getDataElement() {
    return getQueryDataElement();
  }

  public FormDefinitionHostElement getHostElement() {
    throw new UnsupportedOperationException("KnowledgeCapture query form definitions do not contain host elements.");
  }

  public FormDefinition getForm() {
    return getQueryForm();
  }

  /**
   * Returns the category if this form element is a category.
   *  
   * @return  The <code>QueryFormDefinitionCategory</code>, or <code>null</code> if this form 
   *          element does not contain a category.
   */
  public QueryFormDefinitionCategory getQueryCategory() {
    return _category;
  }

  /**
   * Sets the category of this form element.  If this form element already contains a data element, 
   * an exception is thrown.
   * 
   * @param  category  the category 
   */
  public void setQueryCategory(QueryFormDefinitionCategory category) {
    if (isDataElement()) {
      throw new ApiException("Attempt to set a category for a query form definition element that already contains a data element.");
    }
    _category = category;
    category.setQueryForm(getQueryForm());
  }

  /**
   * Returns the data element if this form element is a data element.
   *  
   * @return  The <code>QueryFormDefinitionDataElement</code>, or <code>null</code> if this form 
   *          element does not contain a data element.
   */
  public QueryFormDefinitionDataElement getQueryDataElement() {
    return _dataElement;
  }

  /**
   * Sets the data element of this form element.  If this form element already contains a category, 
   * an exception is thrown.
   * 
   * @param  dataElement  the data element
   */
  public void setQueryDataElement(QueryFormDefinitionDataElement dataElement) {
    if (isCategory()) {
      throw new ApiException("Attempt to set a data element for a query form definition element that already contains a category.");
    }
    _dataElement = dataElement;
    dataElement.setQueryForm(getQueryForm());
  }

  /**
   * Returns the parent category of this form element.
   * 
   * @return  The parent category, or <code>null</code> if the form element does not have a parent 
   *          category (i.e. it is either a top-level category or an uncategorized data element).
   */
  public QueryFormDefinitionCategory getParentQueryCategory() {
    return _parentCategory;
  }

  /**
   * Sets the parent category of this form element.
   * 
   * @param  category  the parent category
   */
  void setParentQueryCategory(QueryFormDefinitionCategory category) {
    _parentCategory = category;
    if (isCategory()) {
      getQueryCategory().setParentQueryCategory(category);
    }
    else if (isDataElement()) {
      getQueryDataElement().setParentQueryCategory(category);      
    }
  }

  /**
   * Returns the query form definition with which this form element is associated.
   *  
   * @return  The <code>QueryFormDefinition</code>.
   */
  public QueryFormDefinition getQueryForm() {
    return _form;
  }

  /**
   * Sets the query form definition with which this form element is associated.
   * As a side effect, also set the query form definition in the current child element. 
   *  
   * @param  form  the <code>QueryFormDefinition</code>
   */
  void setQueryForm(QueryFormDefinition form) {
    _form = form;
    if (isCategory()) {
      getQueryCategory().setQueryForm(form);
    }
    else if (isDataElement()) {
      getQueryDataElement().setQueryForm(form);
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
      QueryFormDefinitionCategory category = getQueryCategory();
      category.setXmlIndentLevel(indent);
      category.toXml(buf);
    }
    else if (isDataElement()) {
      QueryFormDefinitionDataElement element = getQueryDataElement();
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
    if (!(obj instanceof QueryFormDefinitionElement)) {
      return false;
    }
    
    QueryFormDefinitionElement element = (QueryFormDefinitionElement) obj;
    
    if (isCategory()) {
      if (!element.isCategory()) {
        return false;
      }
      else {
        return getQueryCategory().equals(element.getQueryCategory()); 
      }
    }
    else if (isDataElement()) {
      if (!element.isDataElement()) {
        return false;
      }
      else {
        return getQueryDataElement().equals(element.getQueryDataElement()); 
      }
    }
    else {
      throw new ApiException("Error in QueryFormDefinitionElement.equals: unknown type of form element.");
    }

  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  public int hashCode() {
    int result = 17;
    
    if (isCategory()) {
      result = 37*result + getQueryCategory().hashCode();
    }
    else if (isDataElement()) {
      result = 37*result + getQueryDataElement().hashCode();
    }
    else {
      throw new ApiException("Error in QueryFormDefinitionElement.hashCode: unknown type of form element.");
    }

    return result;
  }

}
