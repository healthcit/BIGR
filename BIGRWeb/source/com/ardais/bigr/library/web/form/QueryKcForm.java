package com.ardais.bigr.library.web.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.query.filters.Filter;
import com.ardais.bigr.query.kc.filters.FilterKc;
import com.ardais.bigr.query.kc.filters.FilterKcAny;
import com.ardais.bigr.query.kc.filters.FilterKcAnyAde;
import com.ardais.bigr.query.kc.filters.FilterKcComparison;
import com.ardais.bigr.query.kc.filters.FilterKcComparisonAde;
import com.ardais.bigr.query.kc.filters.FilterKcDiscrete;
import com.ardais.bigr.query.kc.filters.FilterKcDiscreteAde;
import com.ardais.bigr.query.kc.filters.FilterKcRange;
import com.ardais.bigr.query.kc.filters.FilterKcRangeAde;
import com.ardais.bigr.query.kc.filters.FilterKcRollup;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;
import com.gulfstreambio.kc.det.DetAdeElement;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.form.def.FormDefinitionService;
import com.gulfstreambio.kc.form.def.FormDefinitionServiceResponse;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinition;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionAdeElement;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValue;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueComparison;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueSet;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueTypes;
import com.gulfstreambio.kc.web.support.QueryFormAdeElementContext;
import com.gulfstreambio.kc.web.support.QueryFormDataElementContext;

public class QueryKcForm extends BigrActionForm {

  // The query criteria values entered by the user, as a JSON string.
  private String _queryCriteria;
  
  // The set of all KC query forms in the account.
  private QueryFormDefinition[] _queryForms;

  // The KC query form that the user chose.
  private QueryFormDefinition _queryForm; 

  class QueryElement {
    private List _values = new ArrayList();
    private String _logOp;
    private QueryFormDefinitionDataElement _dataElement;
    QueryElement(QueryFormDefinitionDataElement dataElement, String logOp) {
      _dataElement = dataElement;
      _logOp = logOp;
    }
    void addValue(String value, String compOp) {
      _values.add(new QueryValue(value, compOp));
    }
    FilterKc createFilter() {
      int numValues = _values.size();
      if (numValues == 0) {
        return null;        
      }
      
      QueryFormDataElementContext elementContext = new QueryFormDataElementContext(_dataElement);
      if (elementContext.isRenderAsDiscrete()) {
        String[] values = new String[numValues];
        String compOp = null;
        for (int i = 0; i < numValues; i++) {
          QueryValue queryValue = (QueryValue) _values.get(i);
          compOp = queryValue.getCompOp();
          values[i] = queryValue.getValue();
        }
        if (elementContext.getValueSetRollup() != null) {          
          return new FilterKcRollup(_dataElement, _logOp, compOp, values);
        }
        else {
          return new FilterKcDiscrete(_dataElement, _logOp, compOp, values);          
        }
      }
      else if (elementContext.isRenderAsRange()) {
        // If only one value was specified, then create a simple comparison filter.
        if (numValues == 1) {
          QueryValue value = (QueryValue) _values.get(0);
          return new FilterKcComparison(_dataElement, value.getCompOp(), value.getValue());
        }

        // If two values were specified, then create a range filter.
        else if (numValues == 2) {
          QueryValue value1 = (QueryValue) _values.get(0);
          QueryValue value2 = (QueryValue) _values.get(1);
          return new FilterKcRange(_dataElement, _logOp, value1.getCompOp(),value1.getValue(), 
              value2.getCompOp(), value2.getValue());
        }

        // This should never happen, and if it does it is a coding error, likely in the JavaScript
        // that forms the JSON string to send to the server, so throw an exception in this case.
        else if (numValues > 2) {
          throw new ApiException("More than 2 query criteria values for a non-CV element");
        }
      }
      else {        
        throw new ApiException("Not clear how to render query criteria for element " + elementContext.getCui());
      }
      return null;
    }
  }

  class QueryElementAde {
    private List _values = new ArrayList();
    private String _logOp;
    private DetAdeElement _adeElement;
    private boolean _anyValue = false;
    private QueryFormDefinitionDataElement _dataElement;
    QueryElementAde(QueryFormDefinitionDataElement dataElement, String adeElementId, boolean any) {
      _dataElement = dataElement;
      _adeElement = DetService.SINGLETON.getDataElementTaxonomy().getAdeElement(adeElementId);
      _anyValue = any;
    }
    QueryElementAde(QueryFormDefinitionDataElement dataElement, String adeElementId, String logOp) {
      this(dataElement, adeElementId, false);
      _logOp = logOp;
    }
    void addValue(String value, String compOp) {
      _values.add(new QueryValue(value, compOp));
    }
    FilterKc createFilter() {
      if (_anyValue) {
        return new FilterKcAnyAde(_dataElement, _adeElement);                  
      }
      else {
        int numValues = _values.size();
        if (numValues == 0) {
          return null;        
        }
        
        QueryFormAdeElementContext elementContext = new QueryFormAdeElementContext(_adeElement);
        if (elementContext.isRenderAsDiscrete()) {
          String[] values = new String[numValues];
          String compOp = null;
          for (int i = 0; i < numValues; i++) {
            QueryValue queryValue = (QueryValue) _values.get(i);
            compOp = queryValue.getCompOp();
            values[i] = queryValue.getValue();
          }
          return new FilterKcDiscreteAde(_dataElement, _adeElement, _logOp, compOp, values);
        }
        else if (elementContext.isRenderAsRange()) {
          // If only one value was specified, then create a simple comparison filter.
          if (numValues == 1) {
            QueryValue value = (QueryValue) _values.get(0);
            return new FilterKcComparisonAde(_dataElement, _adeElement, value.getCompOp(),
                value.getValue());
          }

          // If two values were specified, then create a range filter.
          else if (numValues == 2) {
            QueryValue value1 = (QueryValue) _values.get(0);
            QueryValue value2 = (QueryValue) _values.get(1);
            return new FilterKcRangeAde(_dataElement, _adeElement, _logOp, value1.getCompOp(), 
                value1.getValue(), value2.getCompOp(), value2.getValue());
          }

          // This should never happen, and if it does it is a coding error, likely in the 
          // JavaScript that forms the JSON string to send to the server, so throw an exception 
          // in this case.
          else if (numValues > 2) {
            throw new ApiException("More than 2 query criteria values for a non-CV element");
          }
        }
        else {        
          throw new ApiException("Not clear how to render query criteria for element " + elementContext.getCui());
        }
        return null;        
      }
    }
  }

  class QueryValue {
    private String _value;
    private String _compOp;
    QueryValue(String value, String compOp) {
      _value = value;
      _compOp = compOp;
    }
    String getValue() { return _value; };
    String getCompOp() { return _compOp; };
  }
  
  public QueryKcForm() {
    reset();
  }
  
  /* (non-Javadoc)
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    reset();
  }

  private void reset() {
    _queryCriteria = null;
    _queryForm = null;
  }
  
  /* (non-Javadoc)
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
    return new ActionErrors();
  }

  public Map getFilters() {
    Map results = new HashMap();

    String jsonString = getQueryCriteria();
    if (!ApiFunctions.isEmpty(jsonString)) {
      try {
        JSONObject jsonObj = new JSONObject(jsonString);
        String formDefinitionId = jsonObj.getString("formDefinitionId");
        if (!ApiFunctions.isEmpty(formDefinitionId)) {
          JSONArray jsonDataElements = jsonObj.optJSONArray("elements");
          if (jsonDataElements != null) {
            QueryFormDefinition form = getQueryFormDefinition();    
            if (form != null && form.getFormDefinitionId() != null && !form.getFormDefinitionId().equals(formDefinitionId)) {
              FormDefinitionServiceResponse response = 
                FormDefinitionService.SINGLETON.findFormDefinitionById(formDefinitionId);
              if (response.getErrors().size() > 0) {
                throw new ApiException("Could not find query form definition with id " + formDefinitionId);
              }
              form = response.getQueryFormDefinition();
            }
            for (int i = 0; i < jsonDataElements.length(); i++) {
              JSONObject jsonDataElement = jsonDataElements.getJSONObject(i);

              String dataElementId = jsonDataElement.getString("elementId");
              QueryFormDefinitionDataElement dataElement = form.getQueryDataElement(dataElementId);
              if (dataElement == null) {
                throw new ApiException("Could not find data element " + dataElementId + " in query form definition " + form.getFormDefinitionId());
              }

              List adeElements = processAdeElements(jsonDataElement, dataElement);
              boolean hasAdeElements = adeElements.size() > 0;

              boolean anyValue = jsonDataElement.optBoolean("anyValue");
              if (anyValue) {
                Filter.addToMap(new FilterKcAny(dataElement), results);
                if (hasAdeElements) {
                  for (int n = 0; n < adeElements.size(); n++) {
                    FilterKc filter = ((QueryElementAde) adeElements.get(n)).createFilter();
                    if (filter != null) {
                      Filter.addToMap(filter, results);
                    }
                  }
                }
              }

              else {
                JSONObject jsonValueSet = jsonDataElement.optJSONObject("valueSet");
                if (jsonValueSet == null) continue;
                JSONArray jsonValues = jsonValueSet.optJSONArray("values");
                if (jsonValues == null) continue;
                String logOp = jsonValueSet.optString("logOp");
                QueryElement queryElement = new QueryElement(dataElement, logOp);
                for (int j = 0; j < jsonValues.length(); j++) {
                  JSONObject jsonValue = jsonValues.getJSONObject(j);
                  String value = jsonValue.optString("value");
                  String compOp = jsonValue.optString("compOp");
                  if (!ApiFunctions.isEmpty(value) || !ApiFunctions.isEmpty(compOp)) {
                    queryElement.addValue(value, compOp);
                  }
                }
                FilterKc filter = queryElement.createFilter();
                if (filter != null) {
                  Filter.addToMap(filter, results);
                  if (hasAdeElements) {
                    for (int n = 0; n < adeElements.size(); n++) {
                      filter = ((QueryElementAde) adeElements.get(n)).createFilter();
                      if (filter != null) {
                        Filter.addToMap(filter, results);
                      }
                    }
                  }
                }
                else if (hasAdeElements) {
                  boolean addedAdeFilter = false;
                  for (int n = 0; n < adeElements.size(); n++) {
                    filter = ((QueryElementAde) adeElements.get(n)).createFilter();
                    if (filter != null) {
                      Filter.addToMap(filter, results);
                      addedAdeFilter = true;
                    }
                  }
                  if (addedAdeFilter) {
                    Filter.addToMap(new FilterKcAny(dataElement), results);
                  }
                }
              }
            } // for all JSON data elements
          } // JSON data elements not null
        } // non-empty form definition id
      } // try
      
      // Since the JSON string is formed in JavaScript in the browser we expect it to always
      // be properly formed.  Therefore if it is not, rethrow the JSON exception as a runtime 
      // exception since this is a bug and not something the user can do anything about.
      catch (JSONException e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
    }

    setFilters(results);
    return results;
  }
  
  private List processAdeElements(JSONObject jsonDataElement,
                                  QueryFormDefinitionDataElement dataElement) {
    JSONArray jsonAdeElements = jsonDataElement.optJSONArray("adeElements");
    List adeElements = new ArrayList();
    if (jsonAdeElements != null) {
      try {
        for (int i = 0; i < jsonAdeElements.length(); i++) {
          JSONObject jsonAdeElement = jsonAdeElements.getJSONObject(i);
          String adeElementId = jsonAdeElement.getString("elementId");
          boolean anyValue = jsonAdeElement.optBoolean("anyValue");
          if (anyValue) {
            adeElements.add(new QueryElementAde(dataElement, adeElementId, true));
          }
          else {
            JSONObject jsonValueSet = jsonAdeElement.optJSONObject("valueSet");
            if (jsonValueSet == null) continue;
            JSONArray jsonValues = jsonValueSet.optJSONArray("values");
            if (jsonValues == null) continue;
            String logOp = jsonValueSet.optString("logOp");                   
            QueryElementAde adeElement = new QueryElementAde(dataElement, adeElementId, logOp);   
            for (int k = 0; k < jsonValues.length(); k++) {
              JSONObject jsonValue = jsonValues.getJSONObject(k);
              String value = jsonValue.optString("value");                
              String compOp = jsonValue.optString("compOp");                
              if (!ApiFunctions.isEmpty(value) || !ApiFunctions.isEmpty(compOp)) {
                adeElement.addValue(value, compOp);                
              }
              adeElements.add(adeElement);
            }
          }
        }
      }

      // Since the JSON string is formed in JavaScript in the browser we expect it to always
      // be properly formed.  Therefore if it is not, rethrow the JSON exception as a runtime 
      // exception since this is a bug and not something the user can do anything about.
      catch (JSONException e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
    }
    return adeElements;
  }
  
  public Map getFiltersFromQueryForm() {
    Map m = new HashMap();
    
    QueryFormDefinition form = getQueryFormDefinition();
    if (form != null) {
      QueryFormDefinitionDataElement[] dataElements = form.getQueryDataElements();
      for (int i = 0; i < dataElements.length; i++) {
        QueryFormDefinitionDataElement dataElement = dataElements[i];
        QueryFormDefinitionValueSet valueSet = dataElement.getValueSet();
        if (valueSet != null) {
          QueryFormDefinitionValue[] values = valueSet.getValues();
          if (values.length > 0) {
            if (values[0].getValueType().equals(QueryFormDefinitionValueTypes.ANY)) {
              FilterKc filter = new FilterKcAny(dataElement);              
              m.put(filter.getKey(), filter);
            }
            else {
              QueryElement queryElement = new QueryElement(dataElement, valueSet.getOperator());
              for (int n = 0; n < values.length; n++) {
                QueryFormDefinitionValue value = values[n];
                if (value.getValueType().equals(QueryFormDefinitionValueTypes.COMPARISON)) {
                  QueryFormDefinitionValueComparison concreteValue =
                    (QueryFormDefinitionValueComparison) value;
                  queryElement.addValue(concreteValue.getValue(), concreteValue.getOperator());                
                }
              }
              FilterKc filter = queryElement.createFilter();
              if (filter != null) {
                m.put(filter.getKey(), filter);
              }
            }
          }
        }
        QueryFormDefinitionAdeElement[] adeElements = dataElement.getAdeElements();
        for (int j = 0; j < adeElements.length; j++) {
          QueryFormDefinitionAdeElement adeElement = adeElements[j];
          valueSet = adeElement.getValueSet();
          if (valueSet != null) {
            QueryFormDefinitionValue[] values = valueSet.getValues();
            if (values.length > 0) {
              if (values[0].getValueType().equals(QueryFormDefinitionValueTypes.ANY)) {                
                QueryElementAde q = new QueryElementAde(dataElement, adeElement.getCui(), true);              
                FilterKc filter = q.createFilter();              
                m.put(filter.getKey(), filter);
              }
              else {
                QueryElementAde q = 
                  new QueryElementAde(dataElement, adeElement.getCui(), valueSet.getOperator());   
                for (int n = 0; n < values.length; n++) {
                  QueryFormDefinitionValue value = values[n];
                  if (value.getValueType().equals(QueryFormDefinitionValueTypes.COMPARISON)) {
                    QueryFormDefinitionValueComparison concreteValue =
                      (QueryFormDefinitionValueComparison) value;
                    q.addValue(concreteValue.getValue(), concreteValue.getOperator());                
                  }
                }
                FilterKc filter = q.createFilter();
                if (filter != null) {
                  m.put(filter.getKey(), filter);
                }
              }
            }
          }          
        }
      }
    }
    
    return m;
  }

  public void setFilters(Map filters) {
    QueryFormDefinition queryForm = getQueryFormDefinition();
    if (queryForm == null || filters == null) {
      return;
    }
    
    // First clear all of the values of the data elements in the query form.  This will ensure
    // that values for which no filter was created will be cleared.
    QueryFormDefinitionDataElement[] dataElements = queryForm.getQueryDataElements();
    for (int n = 0; n < dataElements.length; n++) {
      QueryFormDefinitionDataElement dataElement = dataElements[n];
      dataElement.setValueSet(null);
      QueryFormDefinitionAdeElement[] adeElements = dataElement.getAdeElements();
      for (int m = 0; m < adeElements.length; m++) {
        adeElements[m].setValueSet(null);
      }      
    }
    
    Iterator i = filters.keySet().iterator();
    while (i.hasNext()) {
      String key = (String) i.next();
      if (FilterKc.isKcFilterKey(key)) {
        FilterKc filter = (FilterKc) filters.get(key);
        filter.populateDataElement(queryForm);
      }
    }
  }

  /**
   * Returns all of the KC query criteria as a JSON string.
   *  
   * @return  The KC query critiera as a JSON string.
   */
  public String getQueryCriteria() {
    return _queryCriteria;
  }

  /**
   * Sets all of the KC query criteria as a JSON string.
   *  
   * @param  queryCriteria  the JSON string
   */
  public void setQueryCriteria(String queryCriteria) {
    _queryCriteria = queryCriteria;
  }
  
  public QueryFormDefinition getQueryFormDefinition() {
    return _queryForm;
  }
  
  public void setQueryFormDefinition(QueryFormDefinition queryForm) {
    _queryForm = queryForm;
  }
  
  public QueryFormDefinition[] getQueryFormDefinitions() {
    return (_queryForms == null) ? new QueryFormDefinition[0] : _queryForms;
  }
  
  void setQueryFormDefinitions(QueryFormDefinition[] queryForms) {
    _queryForms = queryForms;
  }
  
}
