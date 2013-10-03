package com.gulfstreambio.kc.form.def.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.util.BigrXmlUtils;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionElement;
import com.gulfstreambio.kc.form.def.FormDefinitionElementsBase;

/**
 * Represents the collection of form elements in a KnowledgeCapture {@link QueryFormDefinition}.
 */
public class QueryFormDefinitionElements extends FormDefinitionElementsBase implements Serializable {

  private QueryFormDefinition _form;
  private List _formElements;
  private int _xmlIndentLevel = 0;

  /**
   *  Creates a new <code>QueryFormDefinitionElements</code>.
   */
  public QueryFormDefinitionElements() {
    super();
  }

  /**
   * Creates a new <code>QueryFormDefinitionElements</code> initialized from the specified one.
   * 
   * @param  form  the form definition elements used to initialize this form definition elements  
   */
  public QueryFormDefinitionElements(QueryFormDefinitionElements elements) {
    this();
    QueryFormDefinitionElement[] e = elements.getQueryFormElements();
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

  /**
   * Adds a <code>QueryFormDefinitionElement</code> to this collection of form elements.
   *  
   * @param  element  the <code>QueryFormDefinitionElement</code>
   */
  public void addQueryFormElement(QueryFormDefinitionElement element) {
    if (_formElements == null) {
      _formElements = new ArrayList();
    }
    _formElements.add(element);
    element.setQueryForm(getQueryForm());
  }

  /**
   * Adds a <code>QueryFormDefinitionDataElement</code> to this collection of form elements.
   *  
   * @param  dataElement  the <code>QueryFormDefinitionDataElement</code>
   */
  public void addQueryDataElement(QueryFormDefinitionDataElement dataElement) {
    addQueryFormElement(new QueryFormDefinitionElement(dataElement));
  }

  /**
   * Adds a <code>QueryFormDefinitionCategory</code> to this collection of form elements.
   *  
   * @param  category  the <code>QueryFormDefinitionCategory</code>
   */
  public void addQueryCategory(QueryFormDefinitionCategory category) {
    addQueryFormElement(new QueryFormDefinitionElement(category));
  }

  /**
   * Returns the query form definition with which this collection of form elements
   * is associated.
   *  
   * @return  The <code>QueryFormDefinition</code>.
   */
  public QueryFormDefinition getQueryForm() {
    return _form;
  }

  /**
   * Sets the query form definition with which this collection of form elements is associated.
   * As a side effect, also sets the query form definition in all current child elements. 
   *  
   * @param  form  the <code>QueryFormDefinition</code>
   */
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

  public String toXml() {
    StringBuffer buf = new StringBuffer(1024);
    toXml(buf);
    return buf.toString();
  }
 
  public void toXml(StringBuffer buf) {
    int indent = getXmlIndentLevel();
    BigrXmlUtils.writeElementStartTag(buf, "formElements", indent);
    buf.append('>');
    QueryFormDefinitionElement[] elements = getQueryFormElements();
    for (int i = 0; i < elements.length; i++) {
      elements[i].setXmlIndentLevel(indent + 1);
      elements[i].toXml(buf);
    }        
    BigrXmlUtils.writeElementEndTag(buf, "formElements", indent);
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
    if (!(obj instanceof QueryFormDefinitionElements)) {
      return false;
    }
    
    QueryFormDefinitionElements elements = (QueryFormDefinitionElements) obj;
    return (_formElements == null)
      ? elements._formElements == null : _formElements.equals(elements._formElements);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  public int hashCode() {
    int result = 17;
    
    result =
      37 * result + ((_formElements == null) ? 0 : _formElements.hashCode());
    
    return result;
  }

}

