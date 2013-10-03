package com.gulfstreambio.kc.web.support;

import java.util.HashMap;
import java.util.Map;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.gulfstreambio.kc.det.DataElementTaxonomy;
import com.gulfstreambio.kc.det.DetElement;
import com.gulfstreambio.kc.det.DetElementDatatypes;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.det.DetValueSet;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValue;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueComparison;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueSet;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueTypes;

public abstract class QueryFormElementContextBase implements QueryFormElementContext {

  private static final Map _comparisonOperators;
  static {
    _comparisonOperators = new HashMap();
    _comparisonOperators.put(QueryFormDefinitionValueComparison.OPERATOR_EQ, "=");
    _comparisonOperators.put(QueryFormDefinitionValueComparison.OPERATOR_NE, "<>");
    _comparisonOperators.put(QueryFormDefinitionValueComparison.OPERATOR_LT, "<");
    _comparisonOperators.put(QueryFormDefinitionValueComparison.OPERATOR_LE, "<=");
    _comparisonOperators.put(QueryFormDefinitionValueComparison.OPERATOR_GT, ">");
    _comparisonOperators.put(QueryFormDefinitionValueComparison.OPERATOR_GE, ">=");   
  }

  QueryFormElementContextBase() {
    super();
  }
  
  protected abstract DetElement getDetElement();

  protected abstract QueryFormDefinitionValueSet getValueSet();
  
  public String getCui() {
    DetElement element = getDetElement();
    return (element == null) ? null : element.getCui();
  }

  public String getSystemName() {
    DetElement element = getDetElement();
    return (element == null) ? null : element.getSystemName();
  }

  private String getDatatype() {
    DetElement element = getDetElement();
    return (element == null) ? null : element.getDatatype();
  }

  public boolean isDatatypeDate() {
    String datatype = getDatatype();
    return (datatype == null) ? false : DetElementDatatypes.DATE.equals(datatype);
  }

  public boolean isDatatypeInt() {
    String datatype = getDatatype();
    return (datatype == null) ? false : DetElementDatatypes.INT.equals(datatype);
  }

  public boolean isDatatypeFloat() {
    String datatype = getDatatype();
    return (datatype == null) ? false : DetElementDatatypes.FLOAT.equals(datatype);
  }

  public boolean isDatatypeReport() {
    String datatype = getDatatype();
    return (datatype == null) ? false : DetElementDatatypes.REPORT.equals(datatype);
  }

  public boolean isDatatypeText() {
    String datatype = getDatatype();
    return (datatype == null) ? false : DetElementDatatypes.TEXT.equals(datatype);
  }

  public boolean isDatatypeVpd() {
    String datatype = getDatatype();
    return (datatype == null) ? false : DetElementDatatypes.VPD.equals(datatype);
  }

  public boolean isDatatypeCv() {
    String datatype = getDatatype();
    return (datatype == null) ? false : DetElementDatatypes.CV.equals(datatype);
  }

  public boolean isMlvs() {
    DetElement element = getDetElement();
    return (element == null) ? false : element.isMultilevelValueSet();
  }

  public String getUnitCui() {
    DetElement element = getDetElement();
    return (element == null) ? null : element.getUnitCui();
  }

  public String getUnitDescription() {
    DetElement element = getDetElement();
    return (element == null) ? null : element.getUnitDescription();
  }

  public String getDisplayNameWithUnits() {
    StringBuffer buf = new StringBuffer(getDisplayName());

    // If we have a display name, then append units to it, if there are any.
    if (buf.length() > 0) {
      String units = getUnitDescription();
      if (!ApiFunctions.isEmpty(units)) {
        buf.append(" (");
        buf.append(units);
        buf.append(')');
      }
    }
    return buf.toString();
  }
  
  public String getSummary() {
    QueryFormDefinitionValue[] values = getValues();
    if (values.length == 0) {
      return null;
    }
    else {
      StringBuffer buf = new StringBuffer(32);
      buf.append(getDisplayNameWithUnits());
      if (isValueAny()) {
        buf.append(" has any value");
      }
      else if (isRenderAsRange()) {
        buf.append(" is ");
        String op = (String) _comparisonOperators.get(getValueComparisonOperator1());
        if (op == null) {
          op = "???";
        }
        buf.append(op);
        buf.append(' ');
        String value = getValue1();
        if (ApiFunctions.isEmpty(value)) {
          value = "???";
        }
        buf.append(value);
        if (values.length > 1) {
          buf.append(' ');          
          buf.append(getValueLogicalOperator());
          buf.append(' ');
          op = (String) _comparisonOperators.get(getValueComparisonOperator2());
          if (op == null) {
            op = "???";
          }
          buf.append(op);
          buf.append(' ');
          value = getValue2();
          if (ApiFunctions.isEmpty(value)) {
            value = "???";
          }
          buf.append(value);
        }
      }
      else if (isRenderAsDiscrete()) {
        DataElementTaxonomy det = DetService.SINGLETON.getDataElementTaxonomy();
        String logOp = getValueLogicalOperator();
        if ("or".equals(logOp)) {
          buf.append(" is one of: ");
        }
        else if ("and".equals(logOp)) {
          buf.append(" is not one of: ");
        }
        else {
          buf.append(" ???: ");
        }
        boolean first = true;
        for (int i = 0; i < values.length; i++) {
          QueryFormDefinitionValue value = values[i];
          if (value.getValueType().equals(QueryFormDefinitionValueTypes.COMPARISON)) {
            QueryFormDefinitionValueComparison concreteValue =
              (QueryFormDefinitionValueComparison) value;
            if (first) {
              first = false;
            }
            else {
              buf.append(", ");
            }
            buf.append(det.getCuiDescription(concreteValue.getValue()));            
          }
        }
      }
      else {
        throw new ApiException("It is unclear how to render the summary of element: " + getSystemName());
      }
      return buf.toString();      
    }
  }

  /* (non-Javadoc)
   * @see com.gulfstreambio.kc.web.support.QueryFormElementContext#getPropertyAny()
   */
  public String getPropertyAny() {
    return getDetElement().getSystemName() + ".any";
  }

  /* (non-Javadoc)
   * @see com.gulfstreambio.kc.web.support.QueryFormElementContext#getPropertyLogicalOperator()
   */
  public String getPropertyLogicalOperator() {
    return getDetElement().getSystemName() + ".logOp";
  }

  /* (non-Javadoc)
   * @see com.gulfstreambio.kc.web.support.QueryFormElementContext#getPropertyComparisonOperator1()
   */
  public String getPropertyComparisonOperator1() {
    return getDetElement().getSystemName() + ".compOp.1";
  }

  /* (non-Javadoc)
   * @see com.gulfstreambio.kc.web.support.QueryFormElementContext#getPropertyComparisonOperator2()
   */
  public String getPropertyComparisonOperator2() {
    return getDetElement().getSystemName() + ".compOp.2";
  }

  /* (non-Javadoc)
   * @see com.gulfstreambio.kc.web.support.QueryFormElementContext#getPropertyValue()
   */
  public String getPropertyValue() {
    return getDetElement().getSystemName() + ".value";
  }

  /* (non-Javadoc)
   * @see com.gulfstreambio.kc.web.support.QueryFormElementContext#getPropertyValue1()
   */
  public String getPropertyValue1() {
    return getDetElement().getSystemName() + ".value.1";
  }

  /* (non-Javadoc)
   * @see com.gulfstreambio.kc.web.support.QueryFormElementContext#getPropertyValue2()
   */
  public String getPropertyValue2() {
    return getDetElement().getSystemName() + ".value.2";
  }

  /* (non-Javadoc)
   * @see com.gulfstreambio.kc.web.support.QueryFormElementContext#getPropertyButtonAddAde()
   */
  public String getPropertyButtonAddAde() {
    return getDetElement().getSystemName() + ".addAde";
  }

  /* (non-Javadoc)
   * @see com.gulfstreambio.kc.web.support.QueryFormElementContext#getPropertyButtonClear()
   */
  public String getPropertyButtonClear() {
    return getDetElement().getSystemName() + ".clear";
  }

  public String getPropertyAdeSummary() {
    return getDetElement().getSystemName() + "_ade";
  }

  public String getJavascriptReference() {
    return getSystemName();
  }
  
  public String getJavascriptReferenceQueryElements() {
    return HtmlElementNamer.getJavaScriptReferenceQueryElements();
  }

  public DetValueSet getValueSetBroad() {
    DetElement metadata = getDetElement();
    return (metadata == null) ? null : metadata.getBroadValueSet();    
  }

  public boolean isValueAny() {
    QueryFormDefinitionValueSet valueSet = getValueSet();
    if (valueSet != null) {
      QueryFormDefinitionValue[] values = valueSet.getValues();
      for (int i = 0; i < values.length; i++) {
        if (values[i].getValueType().equals(QueryFormDefinitionValueTypes.ANY)) {
          return true;
        }
      }
    }
    return false;
  }
  
  public String getValueLogicalOperator() {
    QueryFormDefinitionValueSet valueSet = getValueSet();
    return (valueSet != null) ? valueSet.getOperator() : null;
  }

  public String getValueComparisonOperator1() {
    QueryFormDefinitionValueSet valueSet = getValueSet();
    if (valueSet != null) {
      QueryFormDefinitionValue[] values = valueSet.getValues();
      if (values.length >= 1) {
        QueryFormDefinitionValue value = values[0];
        if (value.getValueType().equals(QueryFormDefinitionValueTypes.COMPARISON)) {
          return ((QueryFormDefinitionValueComparison) value).getOperator();
        }
      }
    }
    return null;
  }
  public String getValueComparisonOperator2() {
    QueryFormDefinitionValueSet valueSet = getValueSet();
    if (valueSet != null) {
      QueryFormDefinitionValue[] values = valueSet.getValues();
      if (values.length >= 2) {
        QueryFormDefinitionValue value = values[1];
        if (value.getValueType().equals(QueryFormDefinitionValueTypes.COMPARISON)) {
          return ((QueryFormDefinitionValueComparison) value).getOperator();
        }
      }
    }
    return null;
  }

  public String getValue1() {
    QueryFormDefinitionValueSet valueSet = getValueSet();
    if (valueSet != null) {
      QueryFormDefinitionValue[] values = valueSet.getValues();
      if (values.length >= 1) {
        QueryFormDefinitionValue value = values[0];
        if (value.getValueType().equals(QueryFormDefinitionValueTypes.COMPARISON)) {
          return ((QueryFormDefinitionValueComparison) value).getValue();
        }
      }
    }
    return null;    
  }
  
  public String getValue2() {
    QueryFormDefinitionValueSet valueSet = getValueSet();
    if (valueSet != null) {
      QueryFormDefinitionValue[] values = valueSet.getValues();
      if (values.length >= 2) {
        QueryFormDefinitionValue value = values[1];
        if (value.getValueType().equals(QueryFormDefinitionValueTypes.COMPARISON)) {
          return ((QueryFormDefinitionValueComparison) value).getValue();
        }
      }
    }
    return null;    
  }
  
  public QueryFormDefinitionValue[] getValues() {
    QueryFormDefinitionValueSet valueSet = getValueSet();
    return (valueSet != null) ? valueSet.getValues() : new QueryFormDefinitionValue[0];
  }

}
