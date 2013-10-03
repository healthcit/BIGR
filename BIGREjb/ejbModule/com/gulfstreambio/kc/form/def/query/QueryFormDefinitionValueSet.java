package com.gulfstreambio.kc.form.def.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.util.BigrXmlUtils;

/**
 * The specification of a single query value set definition as a collection of instances of 
 * implementors of {@link QueryFormDefinitionValue}.  
 */
public class QueryFormDefinitionValueSet extends QueryFormDefinitionValueBase 
  implements Serializable {

  public static final String OPERATOR_AND = "and";
  public static final String OPERATOR_OR = "or";
  
  private String _operator;
  private List _values;

  /**
   * Creates a new <code>QueryFormDefinitionValueSet</code>.
   */
  public QueryFormDefinitionValueSet() {
    super();
  }

  public QueryFormDefinitionValueSet(QueryFormDefinitionValueSet valueSet) {
    this();
    setOperator(valueSet.getOperator());
    QueryFormDefinitionValue[] values = valueSet.getValues();
    for (int i = 0; i < values.length; i++) {
      QueryFormDefinitionValue v = values[i];
      String type = v.getValueType(); 
      if (type.equals(QueryFormDefinitionValueTypes.ANY)) {
        addValue(new QueryFormDefinitionValueAny((QueryFormDefinitionValueAny)v));
      }
      else if (type.equals(QueryFormDefinitionValueTypes.COMPARISON)) {
        addValue(new QueryFormDefinitionValueComparison((QueryFormDefinitionValueComparison)v));        
      }
      else  {
        throw new ApiException("Attempt to add unknown value type to QueryFormDefinitionValueSet");        
      }
    }
  }

  public String getValueType() {
    return QueryFormDefinitionValueTypes.VALUESET;
  }

  /**
   * Returns the operator of this <code>QueryFormDefinitionValueSet</code>.
   * 
   * @return  The operator, which will be one of {@link #OPERATOR_AND} or {@link #OPERATOR_OR}. 
   */
  public String getOperator() {
    return _operator;
  }

  /**
   * Sets the operator of this <code>QueryFormDefinitionValueSet</code>.
   * 
   * @param  operator the operator, which must be one of {@link #OPERATOR_AND} or 
   * {@link #OPERATOR_OR}
   */
  public void setOperator(String operator) {
    _operator = operator;      
  }

  /**
   * Returns the values that are direct children of this value set.
   * 
   * @return  An array of {@link QueryFormDefinitionValue} instances.  If there are no values,
   *          then an empty list is returned.
   */
  public QueryFormDefinitionValue[] getValues() {
    if (_values == null) {
      return new QueryFormDefinitionValue[0];
    }
    else {
      return (QueryFormDefinitionValue[]) _values.toArray(new QueryFormDefinitionValue[0]);
    }
  }

  /**
   * Adds a <code>QueryFormDefinitionValueComparison</code> to this value set definition.
   *   
   * @param  value  the <code>QueryFormDefinitionValueComparison</code>
   */
  public void addValue(QueryFormDefinitionValueComparison value) {
    addValueBase(value);
  }

  /**
   * Adds a <code>QueryFormDefinitionValueAny</code> to this value set definition.
   *   
   * @param  value  the <code>QueryFormDefinitionValueAny</code>
   */
  public void addValue(QueryFormDefinitionValueAny value) {
    addValueBase(value);
  }

  private void addValueBase(QueryFormDefinitionValueBase value) {
    if (_values == null) {
      _values = new ArrayList();
    }
    _values.add(value);
    value.setQueryForm(getQueryForm());
  }
  
  protected void setQueryForm(QueryFormDefinition form) {
    super.setQueryForm(form);
    if (_values != null) {
      Iterator i = _values.iterator();
      while (i.hasNext()) {
        QueryFormDefinitionValueBase value = (QueryFormDefinitionValueBase) i.next();
        value.setQueryForm(form);
      }
    }
  }
  
  /**
   * Sets a default operator for this value set if appropriate.
   */
  void setDefaultOperator() {
    if (getOperator() != null) {
      return;
    }
    QueryFormDefinitionValue[] values = getValues();
    if (values.length == 0) {
      return; 
    }
    QueryFormDefinitionValue value = values[0];
    if (value.getValueType().equals(QueryFormDefinitionValueTypes.COMPARISON)) {
      QueryFormDefinitionValueComparison concreteValue = 
        (QueryFormDefinitionValueComparison) value;
      if (concreteValue.getOperator().equals(QueryFormDefinitionValueComparison.OPERATOR_EQ)) {
        setOperator(OPERATOR_OR);
      }
      else if (concreteValue.getOperator().equals(QueryFormDefinitionValueComparison.OPERATOR_NE)) {
        setOperator(OPERATOR_AND);
      }
    }
  }
  
  public void toXml(StringBuffer buf) {
    int indent = getXmlIndentLevel();
    BigrXmlUtils.writeElementStartTag(buf, "valueSet", indent);
    BigrXmlUtils.writeAttribute(buf, "operator", getOperator());
    buf.append('>');
    
    QueryFormDefinitionValue[] values = getValues();
    for (int i = 0; i < values.length; i++) {
      QueryFormDefinitionValueBase value = (QueryFormDefinitionValueBase) values[i];
      value.setXmlIndentLevel(indent + 1);
      value.toXml(buf);
    }
        
    BigrXmlUtils.writeElementEndTag(buf, "valueSet", indent);
  }

}
