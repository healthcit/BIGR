package com.gulfstreambio.kc.form.def.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.util.BigrXmlUtils;
import com.gulfstreambio.kc.form.def.FormDefinition;

/**
 * Represents the collection of rollup value sets in a KnowledgeCapture
 * {@link QueryFormDefinition}.  Each <code>QueryFormDefinition</code> instance can have zero or  
 * one <code>QueryFormDefinitionRollupValueSets</code> instance that contains all of the rollup 
 * value sets that comprise the form definition.  Since a form definition may not have rollup 
 * value sets, a <code>QueryFormDefinition</code> instance may not have a 
 * <code>QueryFormDefinitionRollupValueSets</code> instance.  
 */
public class QueryFormDefinitionRollupValueSets implements Serializable {

  private QueryFormDefinition _form;
  private List _valueSets;
  private Map _valueSetsCache;
  private int _xmlIndentLevel = 0;

  /**
   *  Creates a new <code>QueryFormDefinitionRollupValueSets</code>.
   */
  public QueryFormDefinitionRollupValueSets() {
    super();
  }

  public QueryFormDefinitionRollupValueSets(QueryFormDefinitionRollupValueSets valueSets) {
    this();
    QueryFormDefinitionRollupValueSet[] v = valueSets.getValueSets();
    for (int i = 0; i < v.length; i++) {
      addValueSet(new QueryFormDefinitionRollupValueSet(v[i]));
    }
  }

  public QueryFormDefinitionRollupValueSet[] getValueSets() {
    if (_valueSets == null) {
      return new QueryFormDefinitionRollupValueSet[0];
    }
    else {
      return (QueryFormDefinitionRollupValueSet[]) 
        _valueSets.toArray(new QueryFormDefinitionRollupValueSet[0]);
    }
  }

  public QueryFormDefinitionRollupValueSet getValueSet(String id) {
    if (_valueSetsCache == null) {
      return null;
    }
    else {
      return (QueryFormDefinitionRollupValueSet) _valueSetsCache.get(id);       
    }
  }

  /**
   * Adds a rollup value set definition to this collection of rollup value set definitions.
   *  
   * @param  valueSet  the <code>QueryFormDefinitionRollupValueSet</code>
   */
  public void addValueSet(QueryFormDefinitionRollupValueSet valueSet) {
    if (_valueSets == null) {
      _valueSets = new ArrayList();
      _valueSetsCache = new HashMap();
    }
    _valueSets.add(valueSet);
    _valueSetsCache.put(valueSet.getId(), valueSet);
    valueSet.setQueryForm(getQueryForm());
  }

  public FormDefinition getForm() {
    return getQueryForm();
  }

  public QueryFormDefinition getQueryForm() {
    return _form;
  }

  void setQueryForm(QueryFormDefinition form) {
    _form = form;
    if (_valueSets != null) {
      Iterator i = _valueSets.iterator();
      while (i.hasNext()) {
        QueryFormDefinitionRollupValueSet valueSet = (QueryFormDefinitionRollupValueSet) i.next();
        valueSet.setQueryForm(form);
      }
    }
  }

  public String toXml() {
    StringBuffer buf = new StringBuffer(1024);
    toXml(buf);
    return buf.toString();
  }
 
  public void toXml(StringBuffer buf) {
    QueryFormDefinitionRollupValueSet[] valueSets = getValueSets();
    if (valueSets.length > 0) {
      int indent = getXmlIndentLevel();
      BigrXmlUtils.writeElementStartTag(buf, "rollupValueSets", indent);
      buf.append('>');
      
      for (int i = 0; i < valueSets.length; i++) {
        valueSets[i].setXmlIndentLevel(indent + 1);
        valueSets[i].toXml(buf);
      }
          
      BigrXmlUtils.writeElementEndTag(buf, "rollupValueSets", indent);
    }
  }
  
  private int getXmlIndentLevel() {
    return _xmlIndentLevel;
  }

  void setXmlIndentLevel(int indentLevel) {
    _xmlIndentLevel = indentLevel;
  }
}
