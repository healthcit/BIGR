package com.gulfstreambio.kc.form.def.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.util.BigrXmlUtils;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionElement;
import com.gulfstreambio.kc.form.def.FormDefinitionElementsBase;

/**
 * Represents the collection of form elements in a KnowledgeCapture {@link DataFormDefinition}.
 */
public class DataFormDefinitionElements extends FormDefinitionElementsBase implements Serializable {

  private DataFormDefinition _form;
  private List _formElements;
  private int _xmlIndentLevel = 0;

  /**
   *  Creates a new <code>DataFormDefinitionElements</code>.
   */
  public DataFormDefinitionElements() {
    super();
  }

  /**
   * Creates a new <code>DataFormDefinitionElements</code> initialized from the specified one.
   * 
   * @param  form  the form definition elements used to initialize this form definition elements  
   */
  public DataFormDefinitionElements(DataFormDefinitionElements elements) {
    this();
    DataFormDefinitionElement[] e = elements.getDataFormElements();
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
      return (DataFormDefinitionElement[]) _formElements.toArray(new DataFormDefinitionElement[0]);
    }
  }

  /**
   * Adds a <code>DataFormDefinitionElement</code> to this collection of form element definitions.
   *  
   * @param  formElement  the <code>DataFormDefinitionElement</code>
   */
  public void addDataFormElement(DataFormDefinitionElement element) {
    if (_formElements == null) {
      _formElements = new ArrayList();
    }
    _formElements.add(element);
    element.setDataForm(getDataForm());
  }

  /**
   * Adds a <code>DataFormDefinitionDataElement</code> to this collection of form element 
   * definitions.
   *  
   * @param  dataElement  the <code>DataFormDefinitionDataElement</code>
   */
  public void addDataDataElement(DataFormDefinitionDataElement dataElement) {
    addDataFormElement(new DataFormDefinitionElement(dataElement));
  }

  /**
   * Adds a <code>DataFormDefinitionHostElement</code> to this collection of form element 
   * definitions.
   *  
   * @param  hostElement  the <code>DataFormDefinitionHostElement</code>
   */
  public void addDataHostElement(DataFormDefinitionHostElement hostElement) {
    addDataFormElement(new DataFormDefinitionElement(hostElement));
  }

  /**
   * Adds a <code>DataFormDefinitionCategory</code> to this collection of form element definitions.
   *  
   * @param  category  the <code>DataFormDefinitionCategory</code>
   */
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

  public String toXml() {
    StringBuffer buf = new StringBuffer(1024);
    toXml(buf);
    return buf.toString();
  }
 
  public void toXml(StringBuffer buf) {
    int indent = getXmlIndentLevel();
    BigrXmlUtils.writeElementStartTag(buf, "formElementDefinitions", indent);
    buf.append('>');
    
    DataFormDefinitionElement[] elements = getDataFormElements();
    for (int i = 0; i < elements.length; i++) {
      elements[i].setXmlIndentLevel(indent + 1);
      elements[i].toXml(buf);
    }
        
    BigrXmlUtils.writeElementEndTag(buf, "formElementDefinitions", indent);
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
    if (!(obj instanceof DataFormDefinitionElements)) {
      return false;
    }
    
    DataFormDefinitionElements fe = (DataFormDefinitionElements) obj;
    return (_formElements == null) 
      ? fe._formElements == null : _formElements.equals(fe._formElements);
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

