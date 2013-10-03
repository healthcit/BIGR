package com.gulfstreambio.kc.form.def.query;

import java.io.Serializable;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.util.BigrXmlUtils;

/**
 * Represents a single ADE element within a KnowledgeCapture {@link QueryFormDefinition}.
 */
public class QueryFormDefinitionAdeElement implements Serializable {

  private QueryFormDefinition _form;
  private String _cui;
  private QueryFormDefinitionValueSet _valueSet;
  private int _xmlIndentLevel = 0;

  /**
   * Creates a new <code>QueryFormDefinitionAdeElement</code>.
   */
  public QueryFormDefinitionAdeElement() {
    super();
  }

  public QueryFormDefinitionAdeElement(QueryFormDefinitionAdeElement adeElement) {
    this();
    setCui(adeElement.getCui());
    QueryFormDefinitionValueSet valueSet = adeElement.getValueSet();
    if (valueSet != null) {
      setValueSet(new QueryFormDefinitionValueSet(valueSet));      
    }
  }

  /**
   * Returns the CUI of this ADE element.
   * 
   * @return  The CUI.
   */
  public String getCui() {
    return _cui;
  }

  /**
   * Sets the CUI of this ADE element.
   * 
   * @param  cui  the CUI
   */
  public void setCui(String cui) {
    _cui = cui;
  }

  /**
   * Returns the query form definition with which this data element is associated.
   *  
   * @return  The <code>QueryFormDefinition</code>.
   */
  public QueryFormDefinition getQueryForm() {
    return _form;
  }

  public QueryFormDefinition getForm() {
    return getQueryForm();
  }

  /**
   * Sets the query form definition with which this data element is associated.
   * As a side effect, also set the query form definition in the value set, if any. 
   *  
   * @param  form  the <code>QueryFormDefinition</code>
   */
  void setQueryForm(QueryFormDefinition form) {
    _form = form;
    QueryFormDefinitionValueSet valueSet = getValueSet();
    if (valueSet != null) {
      valueSet.setQueryForm(form);      
    }
  }

  /**
   * Returns the value set associated with this ADE element, if any.
   * 
   * @return  The value set.  Since value sets are optional, this may return <code>null</code>. 
   */
  public QueryFormDefinitionValueSet getValueSet() {
    return _valueSet;
  }

  /**
   * Sets the value set associated with this ADE element.
   * 
   * @param  valueSet the value set 
   */
  public void setValueSet(QueryFormDefinitionValueSet valueSet) {
    _valueSet = valueSet;
    if (valueSet != null) {
      valueSet.setQueryForm(getQueryForm());      
    }
  }

  public String toXml() {
    StringBuffer buf = new StringBuffer(1024);
    toXml(buf);
    return buf.toString();
  }
 
  public void toXml(StringBuffer buf) {
    int indent = getXmlIndentLevel();
    BigrXmlUtils.writeElementStartTag(buf, "adeElement", indent);
    BigrXmlUtils.writeAttribute(buf, "cui", getCui());
    buf.append('>');
    QueryFormDefinitionValueSet valueSet = getValueSet();
    if (valueSet != null) {
      valueSet.setXmlIndentLevel(indent + 1);
      valueSet.toXml(buf);
    }
    BigrXmlUtils.writeElementEndTag(buf, "adeElement", indent);
  }

  private int getXmlIndentLevel() {
    return _xmlIndentLevel;
  }

  void setXmlIndentLevel(int indentLevel) {
    _xmlIndentLevel = indentLevel;
  }
    
}
