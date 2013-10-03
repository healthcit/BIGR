package com.gulfstreambio.kc.form.def.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.util.BigrXmlUtils;
import com.gulfstreambio.kc.det.DetValueSet;
import com.gulfstreambio.kc.det.DetValueSetValue;
import com.gulfstreambio.kc.form.def.FormDefinition;

/**
 * The specification of a single value set definition as a collection of 
 * {@link DataFormDefinitionValue} instances.  
 */
public class DataFormDefinitionValueSet implements Serializable {

  private DataFormDefinition _form;
  private String _id;
  private List _values;
  private Map _valuesCache;
  private int _xmlIndentLevel = 0;

  /**
   * Creates a new <code>DataFormDefinitionValueSet</code>.
   */
  public DataFormDefinitionValueSet() {
    super();
  }

  public DataFormDefinitionValueSet(DataFormDefinitionValueSet valueSet) {
    this();
    setId(valueSet.getId());
    DataFormDefinitionValue[] v = valueSet.getValues();
    for (int i = 0; i < v.length; i++) {
      addValue(new DataFormDefinitionValue(v[i]));
    }
  }

  /**
   * Returns the unique id of this <code>DataFormDefinitionValueSet</code> within its 
   * {@link DataFormDefinition}.
   * 
   * @return  The id.
   */
  public String getId() {
    return _id;
  }

  /**
   * Sets the unique id of this <code>DataFormDefinitionValueSet</code> within its 
   * {@link DataFormDefinition}.
   * 
   * @param  id  the id
   */
  public void setId(String id) {
    _id = id;
  }

  /**
   * Returns the value definitions that are direct children of this value set definition.
   * 
   * @return  An immutable list of {@link DataFormDefinitionValue} instances.  If there are no value 
   *          definitions, then an empty list is returned.
   */
  public DataFormDefinitionValue[] getValues() {
    if (_values == null) {
      return new DataFormDefinitionValue[0];
    }
    else {
      return (DataFormDefinitionValue[]) _values.toArray(new DataFormDefinitionValue[0]);
    }
  }

  /**
   * Returns the value definition with the specified CUI from all descendant value definitions.
   * 
   * @param  cui  the CUI
   * @return  The <code>DataFormDefinitionValue</code>.  If such a value does not exist, then null  
   *          is returned.
   */
  public DataFormDefinitionValue getValue(String cui) {
    cacheValues();
    return (_valuesCache == null) ? null : (DataFormDefinitionValue) _valuesCache.get(cui);
  }

  /**
   * Adds a <code>DataFormDefinitionValue</code> to this value set definition.
   *   
   * @param  value  the <code>DataFormDefinitionValue</code>
   */
  public void addValue(DataFormDefinitionValue value) {
    if (_values == null) {
      _values = new ArrayList();
    }
    _values.add(value);
    value.setDataForm(getDataForm());
  }

  public FormDefinition getForm() {
    return getDataForm();
  }

  public DataFormDefinition getDataForm() {
    return _form;
  }

  void setDataForm(DataFormDefinition form) {
    _form = form;
    if (_values != null) {
      Iterator i = _values.iterator();
      while (i.hasNext()) {
        DataFormDefinitionValue def = (DataFormDefinitionValue) i.next();
        def.setDataForm(form);
      }
    }
  }
  
 
  private void cacheValues() {
    if (_valuesCache == null) {
      _valuesCache = new HashMap();
      DataFormDefinitionValue[] values = getValues();
      for (int i = 0; i < values.length; i++) {
        DataFormDefinitionValue value = values[i];
        _valuesCache.put(value.getCui(), value);
        cacheValueDefinitionChildren(value);
      }
    }
  }

  private void cacheValueDefinitionChildren(DataFormDefinitionValue definition) {
    DataFormDefinitionValue[] values = definition.getValues();
    for (int i = 0; i < values.length; i++) {
      DataFormDefinitionValue value = values[i];
      _valuesCache.put(value.getCui(), value);
      cacheValueDefinitionChildren(value);
    }    
  }

  public String toXml() {
    StringBuffer buf = new StringBuffer(1024);
    toXml(buf);
    return buf.toString();
  }
 
  public void toXml(StringBuffer buf) {
    int indent = getXmlIndentLevel();
    BigrXmlUtils.writeElementStartTag(buf, "valueSetDefinition", indent);
    BigrXmlUtils.writeAttribute(buf, "id", getId());
    buf.append('>');
    
    DataFormDefinitionValue[] values = getValues();
    for (int i = 0; i < values.length; i++) {
      values[i].setXmlIndentLevel(indent + 1);
      values[i].toXml(buf);
    }
        
    BigrXmlUtils.writeElementEndTag(buf, "valueSetDefinition", indent);
  }

  private int getXmlIndentLevel() {
    return _xmlIndentLevel;
  }

  void setXmlIndentLevel(int indentLevel) {
    _xmlIndentLevel = indentLevel;
  }
}
