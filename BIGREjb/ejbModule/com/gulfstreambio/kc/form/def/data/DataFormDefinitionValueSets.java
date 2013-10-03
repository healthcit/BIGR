package com.gulfstreambio.kc.form.def.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.util.BigrXmlUtils;
import com.gulfstreambio.kc.form.def.FormDefinition;

/**
 * Represents the collection of value set definitions in a KnowledgeCapture
 * {@link DataFormDefinition}.  Each <code>DataFormDefinition</code> instance can have zero or one 
 * <code>DataFormDefinitionValueSets</code> instance that contains all of the value sets that 
 * comprise the form definition.  Since a form definition may not have form-specific value sets, a
 * <code>DataFormDefinition</code> instance may not have a 
 * <code>DataFormDefinitionValueSets</code> instance.  
 */
public class DataFormDefinitionValueSets implements Serializable {

  private DataFormDefinition _form;
  private List _valueSets;
  private Map _valueSetsCache;
  private int _xmlIndentLevel = 0;

  /**
   *  Creates a new <code>DataFormDefinitionValueSets</code>.
   */
  public DataFormDefinitionValueSets() {
    super();
  }

  public DataFormDefinitionValueSets(DataFormDefinitionValueSets valueSets) {
    this();
    DataFormDefinitionValueSet[] v = valueSets.getValueSets();
    for (int i = 0; i < v.length; i++) {
      addValueSet(new DataFormDefinitionValueSet(v[i]));
    }
  }

  public DataFormDefinitionValueSet[] getValueSets() {
    if (_valueSets == null) {
      return new DataFormDefinitionValueSet[0];
    }
    else {
      return (DataFormDefinitionValueSet[]) _valueSets.toArray(new DataFormDefinitionValueSet[0]);
    }
  }

  public DataFormDefinitionValueSet getValueSet(String id) {
    if (_valueSetsCache == null) {
      return null;
    }
    else {
      return (DataFormDefinitionValueSet) _valueSetsCache.get(id);       
    }
  }

  /**
   * Adds a value set definition to this collection of value set definitions.
   *  
   * @param  valueSet  the <code>DataFormDefinitionValueSet</code>
   */
  public void addValueSet(DataFormDefinitionValueSet valueSet) {
    if (_valueSets == null) {
      _valueSets = new ArrayList();
      _valueSetsCache = new HashMap();
    }
    _valueSets.add(valueSet);
    _valueSetsCache.put(valueSet.getId(), valueSet);
    valueSet.setDataForm(getDataForm());
  }

  public FormDefinition getForm() {
    return getDataForm();
  }

  public DataFormDefinition getDataForm() {
    return _form;
  }

  void setDataForm(DataFormDefinition form) {
    _form = form;
    if (_valueSets != null) {
      Iterator i = _valueSets.iterator();
      while (i.hasNext()) {
        DataFormDefinitionValueSet def = (DataFormDefinitionValueSet) i.next();
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
    DataFormDefinitionValueSet[] valueSets = getValueSets();
    if (valueSets.length > 0) {
      int indent = getXmlIndentLevel();
      BigrXmlUtils.writeElementStartTag(buf, "valueSetDefinitions", indent);
      buf.append('>');
      
      for (int i = 0; i < valueSets.length; i++) {
        valueSets[i].setXmlIndentLevel(indent + 1);
        valueSets[i].toXml(buf);
      }
          
      BigrXmlUtils.writeElementEndTag(buf, "valueSetDefinitions", indent);
    }
  }
  
  private int getXmlIndentLevel() {
    return _xmlIndentLevel;
  }

  void setXmlIndentLevel(int indentLevel) {
    _xmlIndentLevel = indentLevel;
  }
}
