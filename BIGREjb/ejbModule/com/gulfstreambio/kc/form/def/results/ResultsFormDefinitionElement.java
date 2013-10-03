package com.gulfstreambio.kc.form.def.results;

import java.io.Serializable;

import com.ardais.bigr.api.ApiException;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionCategory;
import com.gulfstreambio.kc.form.def.FormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.FormDefinitionElement;
import com.gulfstreambio.kc.form.def.FormDefinitionHostElement;

/**
 * Represents a single form element definition in a KnowledgeCapture {@link ResultsFormDefinition}.
 */
public class ResultsFormDefinitionElement implements FormDefinitionElement, Serializable {

  private ResultsFormDefinition _formDef;
  private ResultsFormDefinitionDataElement _dataElement;
  private ResultsFormDefinitionHostElement _hostElement;

  /**
   *  Creates a new <code>ResultsFormDefinitionElement</code>. 
   */
  public ResultsFormDefinitionElement() {
    super();
  }

  /**
   * Creates a new <code>ResultsFormDefinitionElement</code> from the specified 
   * <code>ResultsFormDefinitionElement</code> 
   * 
   * @param  element the <code>ResultsFormDefinitionElement</code>
   */
  public ResultsFormDefinitionElement(ResultsFormDefinitionElement element) {
    this();
    if (element.isDataElement()) {
      setResultsDataElement(new ResultsFormDefinitionDataElement(element.getResultsDataElement()));      
    }
    else if (element.isHostElement()) {
      setResultsHostElement(new ResultsFormDefinitionHostElement(element.getResultsHostElement()));
    }
    else {
      throw new ApiException("Unrecognized results form definition element in constructor");
    }
  }

  /**
   *  Creates a new <code>ResultsFormDefinitionElement</code> from the specified 
   * <code>ResultsFormDefinitionDataElement</code> 
   * 
   * @param  dataElement  the <code>ResultsFormDefinitionDataElement</code>
   */
  public ResultsFormDefinitionElement(ResultsFormDefinitionDataElement dataElement) {
    this();
    setResultsDataElement(dataElement);
  }

  /**
   *  Creates a new <code>ResultsFormDefinitionElement</code> from the specified 
   * <code>ResultsFormDefinitionHostElement</code>
   * 
   * @param  hostElement  the <code>ResultsFormDefinitionHostElement</code>
   */
  public ResultsFormDefinitionElement(ResultsFormDefinitionHostElement hostElement) {
    this();
    setResultsHostElement(hostElement);
  }

  public boolean isCategory() {
    return false;
  }

  public boolean isDataElement() {
    return (_dataElement != null);
  }

  public FormDefinitionCategory getCategory() {
    throw new UnsupportedOperationException("KnowledgeCapture results form definitions do not contain categories.");
  }
  
  public FormDefinitionDataElement getDataElement() {
    return getResultsDataElement();
  }

  public FormDefinitionHostElement getHostElement() {
    return getResultsHostElement();
  }

  public FormDefinition getForm() {
    return getResultsForm();
  }

  /**
   * Returns the host element if this form element definition contains a host element.
   *  
   * @return  The host element, or null if this form element definition does not contain 
   *          a host element.
   */
  public ResultsFormDefinitionHostElement getResultsHostElement() {
    return _hostElement;
  }

  /**
   * Sets the host element contained in this form element definition.  If this form element
   * definition already contains a data element definition, an exception is thrown.
   * 
   * @param  hostElement the host element
   */
  public void setResultsHostElement(ResultsFormDefinitionHostElement hostElement) {
    if (isDataElement()) {
      throw new ApiException("Attempt to set a host element for a form definition that contains a data element definition.");
    }
    _hostElement = hostElement;
    _hostElement.setResultsForm(getResultsForm());
  }

  /**
   * Returns the data element definition if this form element definition contains a data element
   * definition.
   *  
   * @return  The data element definition, or null if this form element definition does not contain 
   *          a data element definition.
   */
  public ResultsFormDefinitionDataElement getResultsDataElement() {
    return _dataElement;
  }

  /**
   * Sets the category definition contained in this form element definition.  If this form element
   * definition already contains a data element definition, an exception is thrown.
   * 
   * @param  dataElement  the data element definition
   */
  public void setResultsDataElement(ResultsFormDefinitionDataElement dataElement) {
    if (isHostElement()) {
      throw new ApiException("Attempt to set a data element definition for a form definition that contains a host element.");
    }
    _dataElement = dataElement;
    dataElement.setResultsForm(getResultsForm());
  }
  
  /**
   * Returns an indication of whether this <code>ResultsFormDefinitionElement</code> contains a host element
   * definition.
   *  
   * @return  true if this <code>ResultsFormDefinitionElement</code> contains a host element definition; 
   *          false otherwise.
   */
  public boolean isHostElement() {
    return (_hostElement != null);
  }
  
  public ResultsFormDefinition getResultsForm() {
    return _formDef;
  }

  void setResultsForm(ResultsFormDefinition form) {
    _formDef = form;
    if (isHostElement()) {
      getResultsHostElement().setResultsForm(form);
    }
    else if (isDataElement()) {
      getResultsDataElement().setResultsForm(form);
    }
  }

  public String toXml() {
    StringBuffer buf = new StringBuffer(1024);
    toXml(buf);
    return buf.toString();
  }
 
  public void toXml(StringBuffer buf) {
    if (isHostElement()) {
      getResultsHostElement().toXml(buf);
    }
    else if (isDataElement()) {
      getResultsDataElement().toXml(buf);
    }
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof ResultsFormDefinitionElement)) {
      return false;
    }
    
    ResultsFormDefinitionElement fe = (ResultsFormDefinitionElement) obj;
    
    if (isHostElement()) {
      if (!fe.isHostElement()) {
        return false;
      }
      else {
        return getResultsHostElement().equals(fe.getResultsHostElement()); 
      }
    }
    else if (isDataElement()) {
      if (!fe.isDataElement()) {
        return false;
      }
      else {
        return getResultsDataElement().equals(fe.getResultsDataElement()); 
      }
    }
    else {
      throw new ApiException("Error in ResultsFormDefinitionElement.equals: form element unaccounted for.");
    }

  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  public int hashCode() {
    int result = 17;
    
    if (isHostElement()) {
      result = 37*result + getResultsHostElement().hashCode();
    }
    else if (isDataElement()) {
      result = 37*result + getResultsDataElement().hashCode();
    }
    else {
      throw new ApiException("Error in ResultsFormDefinitionElement.hashCode: form element unaccounted for.");
    }

    return result;
  }

}
