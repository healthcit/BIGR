package com.gulfstreambio.kc.form.def.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.util.BigrXmlUtils;
import com.gulfstreambio.kc.det.DetValueSetValue;
import com.gulfstreambio.kc.form.def.FormDefinition;

/**
 * Represents a single value within a value set.
 */
public class DataFormDefinitionValue implements Serializable {

  private DataFormDefinition _form;
  private String _cui;
  private String _displayName;
  private List _values;
  private Map _valuesCache;
  private int _xmlIndentLevel = 0;

  /**
   * Creates a new <code>DataFormDefinitionValue</code>.
   */
  public DataFormDefinitionValue() {
    super();
  }

  public DataFormDefinitionValue(DataFormDefinitionValue value) {
    this();
    setCui(value.getCui());
    setDisplayName(value.getDisplayName());
    DataFormDefinitionValue[] v = value.getValues();
    for (int i = 0; i < v.length; i++) {
      addValue(new DataFormDefinitionValue(v[i]));
    }
  }

  /**
   * Returns the CUI of the value.
   * 
   * @return  The CUI.
   */
  public String getCui() {
    return _cui;
  }

  /**
   * Sets the CUI of the data value.
   * 
   * @param  cui  the CUI
   */
  public void setCui(String cui) {
    _cui = cui;
  }

  /**
   * Returns the display name of this value.
   * 
   * @return  The display name.
   */
  public String getDisplayName() {
    return _displayName;      
  }

  /**
   * Sets the display name of this value.
   * 
   * @param  name  the display name
   */
  public void setDisplayName(String displayName) {
    _displayName = displayName;
  }

  /**
   * Returns the value definitions that are direct children of this value definition.
   * 
   * @return  An immutable list of {@link DataFormDefinitionValue} instances.  If there are no  
   *          child value definitions, then an empty list is returned.
   */
  public DataFormDefinitionValue[] getValues() {
    if (_values == null) {
      return new DataFormDefinitionValue[0];
    }
    else {
      return (DataFormDefinitionValue[]) _values.toArray(new DataFormDefinitionValue[0]);
    }
  }

  public DataFormDefinitionValue getValue(String cui) {
    return (_valuesCache == null) ? null : (DataFormDefinitionValue) _valuesCache.get(cui);
  }

  /**
   * Adds a <code>DataFormDefinitionValue</code> to this <code>DataFormDefinitionValue</code>, supporting
   * multi-level value sets.
   *   
   * @param  valueDef  the <code>DataFormDefinitionValue</code> to add
   */
  public void addValue(DataFormDefinitionValue valueDef) {
    if (_values == null) {
      _values = new ArrayList();
      _valuesCache = new HashMap();
    }
    _values.add(valueDef);
    _valuesCache.put(valueDef.getCui(), valueDef);
    valueDef.setDataForm(getDataForm());
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


  public String toXml() {
    StringBuffer buf = new StringBuffer(1024);
    toXml(buf);
    return buf.toString();
  }
 
  public void toXml(StringBuffer buf) {
    int indent = getXmlIndentLevel();
    BigrXmlUtils.writeElementStartTag(buf, "valueDefinition", indent);
    BigrXmlUtils.writeAttribute(buf, "cui", getCui());
    String displayName = getDisplayName();
    if (!ApiFunctions.isEmpty(displayName)) {
      BigrXmlUtils.writeAttribute(buf, "displayName", displayName);
    }
    
    DataFormDefinitionValue[] values = getValues();
    if (values.length > 0) {
      buf.append('>');
      for (int i = 0; i < values.length; i++) {
        values[i].setXmlIndentLevel(indent + 1);
        values[i].toXml(buf);
      }
      BigrXmlUtils.writeElementEndTag(buf, "valueDefinition", indent);
    }
    else {
      buf.append("/>");
    }        
  }

  private int getXmlIndentLevel() {
    return _xmlIndentLevel;
  }

  void setXmlIndentLevel(int indentLevel) {
    _xmlIndentLevel = indentLevel;
  }
}
