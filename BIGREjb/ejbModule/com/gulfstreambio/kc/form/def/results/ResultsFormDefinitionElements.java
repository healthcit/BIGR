package com.gulfstreambio.kc.form.def.results;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionElement;
import com.gulfstreambio.kc.form.def.FormDefinitionElementsBase;

/**
 * Represents the collection of form elements in a KnowledgeCapture {@link ResultsFormDefinition}.
 */
public class ResultsFormDefinitionElements extends FormDefinitionElementsBase implements Serializable {

  private ResultsFormDefinition _form;
  private List _formElementDefinitions;

  /**
   *  Creates a new <code>ResultsFormDefinitionElements</code>.
   */
  public ResultsFormDefinitionElements() {
    super();
  }

  /**
   * Creates a new <code>ResultsFormDefinitionElements</code> initialized from the specified one.
   * 
   * @param  form  the form definition elements used to initialize this form definition elements  
   */
  public ResultsFormDefinitionElements(ResultsFormDefinitionElements elements) {
    this();
    ResultsFormDefinitionElement[] e = elements.getResultsFormElements();
    for (int i = 0; i < e.length; i++) {
      addResultsFormElement(new ResultsFormDefinitionElement(e[i]));
    }    
  }

  public FormDefinitionElement[] getFormElements() {
    return getResultsFormElements();
  }

  public FormDefinition getForm() {
    return getResultsForm();
  }

  public ResultsFormDefinitionElement[] getResultsFormElements() {
    if (_formElementDefinitions == null) {
      return new ResultsFormDefinitionElement[0];
    }
    else {
      return (ResultsFormDefinitionElement[]) 
        _formElementDefinitions.toArray(new ResultsFormDefinitionElement[0]);
    }
  }

  /**
   * Adds a <code>ResultsFormDefinitionElement</code> to this collection of form element definitions.
   *  
   * @param  element  the <code>ResultsFormDefinitionElement</code>
   */
  public void addResultsFormElement(ResultsFormDefinitionElement element) {
    if (_formElementDefinitions == null) {
      _formElementDefinitions = new ArrayList();
    }
    _formElementDefinitions.add(element);
    element.setResultsForm(getResultsForm());
  }

  /**
   * Removes a <code>ResultsFormDefinitionElement</code> from this collection of form element 
   * definitions.  If the element does not exist in this collection, this method quietly does
   * nothing.
   *  
   * @param  element  the <code>ResultsFormDefinitionElement</code>
   */
  public void removeResultsFormElement(ResultsFormDefinitionElement element) {
    if (_formElementDefinitions != null) {
      _formElementDefinitions.remove(element);
    }
  }

  /**
   * Adds a <code>ResultsFormDefinitionDataElement</code> to this collection of form element definitions.
   *  
   * @param  dataElement the <code>ResultsFormDefinitionDataElement</code>
   */
  public void addResultsDataElement(ResultsFormDefinitionDataElement dataElement) {
    addResultsFormElement(new ResultsFormDefinitionElement(dataElement));
  }

  /**
   * Adds a <code>ResultsFormDefinitionHostElement</code> to this collection of form element definitions.
   *  
   * @param  hostElement  the <code>ResultsFormDefinitionHostElement</code>
   */
  public void addResultsHostElement(ResultsFormDefinitionHostElement hostElement) {
    addResultsFormElement(new ResultsFormDefinitionElement(hostElement));
  }

  public ResultsFormDefinition getResultsForm() {
    return _form;
  }

  void setResultsForm(ResultsFormDefinition form) {
    _form = form;
    if (_formElementDefinitions != null) {
      Iterator i = _formElementDefinitions.iterator();
      while (i.hasNext()) {
        ResultsFormDefinitionElement def = (ResultsFormDefinitionElement) i.next();
        def.setResultsForm(form);
      }
    }
  }

  public String toXml() {
    StringBuffer buf = new StringBuffer(1024);
    toXml(buf);
    return buf.toString();
  }
 
  public void toXml(StringBuffer buf) {
    buf.append("\n<formElements>\n");
    
    ResultsFormDefinitionElement[] elements = getResultsFormElements();
    for (int i = 0; i < elements.length; i++) {
      elements[i].toXml(buf);
    }
        
    buf.append("\n</formElements>\n");
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof ResultsFormDefinitionElements)) {
      return false;
    }
    
    ResultsFormDefinitionElements fe = (ResultsFormDefinitionElements) obj;
    return (_formElementDefinitions == null)
      ? fe._formElementDefinitions == null
      : _formElementDefinitions.equals(fe._formElementDefinitions);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  public int hashCode() {
    int result = 17;
    
    result =
      37 * result + ((_formElementDefinitions == null) ? 0 : _formElementDefinitions.hashCode());
    
    return result;
  }

}
