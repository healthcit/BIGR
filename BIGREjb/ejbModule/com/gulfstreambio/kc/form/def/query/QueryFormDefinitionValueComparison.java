package com.gulfstreambio.kc.form.def.query;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.util.BigrXmlUtils;

public class QueryFormDefinitionValueComparison extends QueryFormDefinitionValueBase 
  implements Serializable {

  public final static String OPERATOR_EQ = "eq";
  public final static String OPERATOR_NE = "ne";
  public final static String OPERATOR_LT = "lt";
  public final static String OPERATOR_LE = "le";
  public final static String OPERATOR_GT = "gt";
  public final static String OPERATOR_GE = "ge";
  
  private static Set _validOperators;
  static {
    _validOperators = new HashSet();
    _validOperators.add(OPERATOR_EQ);
    _validOperators.add(OPERATOR_NE);
    _validOperators.add(OPERATOR_LT);
    _validOperators.add(OPERATOR_LE);
    _validOperators.add(OPERATOR_GT);
    _validOperators.add(OPERATOR_GE);
  }
  
  private String _value;
  private String _operator;
  
  /**
   * Creates a new <code>QueryFormDefinitionValueComparison</code>.
   */
  public QueryFormDefinitionValueComparison() {
    super();
  }

  public QueryFormDefinitionValueComparison(QueryFormDefinitionValueComparison value) {
    this();
    setValue(value.getValue());
    setOperator(value.getOperator());
  }

  public String getValueType() {
    return QueryFormDefinitionValueTypes.COMPARISON;
  }
  
  public String getOperator() {
    return _operator;
  }
  
  public void setOperator(String operator) {
    _operator = operator;
  }
  
  public String getValue() {
    return _value;
  }
  
  public void setValue(String value) {
    _value = value;
  }
  
  public void toXml(StringBuffer buf) {
    BigrXmlUtils.writeElementStartTag(buf, "valueComparison", getXmlIndentLevel());
    BigrXmlUtils.writeAttribute(buf, "value", getValue());
    BigrXmlUtils.writeAttribute(buf, "operator", getOperator());
    buf.append("/>");
  }

}
