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
 * The specification of a single rollup value set as a collection of 
 * {@link QueryFormDefinitionRollupValue} instances.  
 */
public class QueryFormDefinitionRollupValueSet implements Serializable {

  private QueryFormDefinition _form;
  private String _id;
  private List _values;
  private Map _valuesCache;
  private int _xmlIndentLevel = 0;

  /**
   * Creates a new <code>QueryFormDefinitionRollupValueSet</code>.
   */
  public QueryFormDefinitionRollupValueSet() {
    super();
  }

  public QueryFormDefinitionRollupValueSet(QueryFormDefinitionRollupValueSet valueSet) {
    this();
    setId(valueSet.getId());
    QueryFormDefinitionRollupValue[] v = valueSet.getValues();
    for (int i = 0; i < v.length; i++) {
      addValue(new QueryFormDefinitionRollupValue(v[i]));
    }
  }

  /**
   * Returns the unique id of this <code>QueryFormDefinitionRollupValueSet</code> within its 
   * {@link QueryFormDefinition}.
   * 
   * @return  The id.
   */
  public String getId() {
    return _id;
  }

  /**
   * Sets the unique id of this <code>QueryFormDefinitionRollupValueSet</code> within its 
   * {@link QueryFormDefinition}.
   * 
   * @param  id  the id
   */
  public void setId(String id) {
    _id = id;
  }

  /**
   * Returns the rollup values that are direct children of this rollup value set.
   * 
   * @return  An immutable list of {@link QueryFormDefinitionRollupValue} instances.  If there are 
   *          no values, then an empty list is returned.
   */
  public QueryFormDefinitionRollupValue[] getValues() {
    if (_values == null) {
      return new QueryFormDefinitionRollupValue[0];
    }
    else {
      return (QueryFormDefinitionRollupValue[]) 
        _values.toArray(new QueryFormDefinitionRollupValue[0]);
    }
  }

  /**
   * Returns the rollup value with the specified display name from all values.
   * 
   * @param  displayName  the displayName
   * @return  The <code>QueryFormDefinitionRollupValue</code>.  If such a value does not exist, 
   *          then null is returned.
   */
  public QueryFormDefinitionRollupValue getValue(String displayName) {
    cacheValues();
    return (_valuesCache == null) 
      ? null : (QueryFormDefinitionRollupValue) _valuesCache.get(displayName);
  }

  /**
   * Adds a <code>QueryFormDefinitionRollupValue</code> to this rollup value set.
   *   
   * @param  value  the <code>QueryFormDefinitionRollupValue</code>
   */
  public void addValue(QueryFormDefinitionRollupValue value) {
    if (_values == null) {
      _values = new ArrayList();
    }
    _values.add(value);
    value.setQueryForm(getQueryForm());
  }

  private void cacheValues() {
    if (_valuesCache == null) {
      _valuesCache = new HashMap();
      QueryFormDefinitionRollupValue[] values = getValues();
      for (int i = 0; i < values.length; i++) {
        QueryFormDefinitionRollupValue value = values[i];
        _valuesCache.put(value.getDisplayName(), value);
      }
    }
  }

  public FormDefinition getForm() {
    return getQueryForm();
  }

  public QueryFormDefinition getQueryForm() {
    return _form;
  }

  void setQueryForm(QueryFormDefinition form) {
    _form = form;
    if (_values != null) {
      Iterator i = _values.iterator();
      while (i.hasNext()) {
        QueryFormDefinitionRollupValue value = (QueryFormDefinitionRollupValue) i.next();
        value.setQueryForm(form);
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
    BigrXmlUtils.writeElementStartTag(buf, "rollupValueSet", indent);
    BigrXmlUtils.writeAttribute(buf, "id", getId());
    buf.append('>');
    
    QueryFormDefinitionRollupValue[] values = getValues();
    for (int i = 0; i < values.length; i++) {
      values[i].setXmlIndentLevel(indent + 1);
      values[i].toXml(buf);
    }
        
    BigrXmlUtils.writeElementEndTag(buf, "rollupValueSet", indent);
  }

  private int getXmlIndentLevel() {
    return _xmlIndentLevel;
  }

  void setXmlIndentLevel(int indentLevel) {
    _xmlIndentLevel = indentLevel;
  }
}
