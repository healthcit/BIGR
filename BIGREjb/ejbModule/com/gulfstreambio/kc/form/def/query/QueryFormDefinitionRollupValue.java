package com.gulfstreambio.kc.form.def.query;

import java.io.Serializable;

import com.ardais.bigr.util.BigrXmlUtils;
import com.gulfstreambio.kc.form.def.FormDefinition;

/**
 * Represents a single rollup within a {@link QueryFormDefinitionRollupValueSet} instance.  
 */
public class QueryFormDefinitionRollupValue implements Serializable {

  private QueryFormDefinition _form;
  private String _displayName;
  private QueryFormDefinitionValueSet _valueSet;
  private int _xmlIndentLevel = 0;

  /**
   * Creates a new <code>QueryFormDefinitionRollupValue</code>.
   */
  public QueryFormDefinitionRollupValue() {
    super();
  }

  public QueryFormDefinitionRollupValue(QueryFormDefinitionRollupValue value) {
    super();
    setDisplayName(value.getDisplayName());
    QueryFormDefinitionValueSet valueSet = value.getValueSet();
    if (valueSet != null) {      
      setValueSet(new QueryFormDefinitionValueSet(valueSet));
    }
  }

  /**
   * Returns the display name of this rollup value.
   * 
   * @return  The display name.
   */
  public String getDisplayName() {
    return _displayName;      
  }

  /**
   * Sets the display name of this rollup value.
   * 
   * @param  name  the display name
   */
  public void setDisplayName(String displayName) {
    _displayName = displayName;
  }

  /**
   * Returns the value set that contains the actual values of this rollup value.
   * 
   * @return  the <code>QueryFormDefinitionValueSet</code>
   */
  public QueryFormDefinitionValueSet getValueSet() {
    return _valueSet;
  }

  /**
   * Sets the value set that contains the actual values of this rollup value.
   * 
   * @param  valueSet  the <code>QueryFormDefinitionValueSet</code>
   */
  public void setValueSet(QueryFormDefinitionValueSet valueSet) {
    _valueSet = valueSet;
  }

  public FormDefinition getForm() {
    return getQueryForm();
  }

  public QueryFormDefinition getQueryForm() {
    return _form;
  }

  void setQueryForm(QueryFormDefinition form) {
    _form = form;
    if (_valueSet != null) {
      _valueSet.setQueryForm(form);
    }
  }

  public String toXml() {
    StringBuffer buf = new StringBuffer(1024);
    toXml(buf);
    return buf.toString();
  }
 
  public void toXml(StringBuffer buf) {
    int indent = getXmlIndentLevel();
    BigrXmlUtils.writeElementStartTag(buf, "rollupValue", indent);
    BigrXmlUtils.writeAttribute(buf, "displayName", getDisplayName());
    buf.append('>');
    
    QueryFormDefinitionValueSet valueSet = getValueSet();
    if (valueSet != null) {
      valueSet.setXmlIndentLevel(indent + 1);
      valueSet.toXml(buf);
    }
        
    BigrXmlUtils.writeElementEndTag(buf, "rollupValue", indent);
  }

  private int getXmlIndentLevel() {
    return _xmlIndentLevel;
  }

  void setXmlIndentLevel(int indentLevel) {
    _xmlIndentLevel = indentLevel;
  }
}
