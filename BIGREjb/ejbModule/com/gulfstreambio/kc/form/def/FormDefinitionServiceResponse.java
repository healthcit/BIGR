package com.gulfstreambio.kc.form.def;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinition;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinition;

/**
 * The response object that is returned by all {@link FormDefinitionService} methods.  
 * One of the following methods will return the value(s) that comprise the actual response
 * from the <code>FormDefinitionService</code> method that is called.
 * <ul>
 * <li>{@link #getErrors() getErrors}: If there was one or more validation errors they will be 
 * returned by a call to this method.  Clients should call this method first to discover if
 * the method call produced any errors before attempting to call any other method.
 * </li> 
 * <li>{@link #getDataFormDefinition() getDataFormDefinition}: If the 
 * <code>FormDefinitionService</code> method that was called returns a single 
 * {@link DataFormDefinition}, then this convenience method returns it. 
 * </li> 
 * <li>{@link #getDataFormDefinitions() getDataFormDefinitions}: If the 
 * <code>FormDefinitionService</code> method that was called returns any array of 
 * <code>DataFormDefinition</code>s, then this method returns them. 
 * </li> 
 * <li>{@link #getQueryFormDefinition() getQueryFormDefinition}: If the 
 * <code>FormDefinitionService</code> method that was called returns a single 
 * {@link QueryFormDefinition}, then this convenience method returns it. 
 * </li> 
 * <li>{@link #getQueryFormDefinitions() getQueryFormDefinitions}: If the 
 * <code>FormDefinitionService</code> method that was called returns any array of 
 * <code>QueryFormDefinition</code>s, then this method returns them. 
 * </li> 
 * <li>{@link #getResultsFormDefinition() getResultsFormDefinition}: If the 
 * <code>FormDefinitionService</code> method that was called returns a single 
 * {@link ResultsFormDefinition}, then this convenience method returns it. 
 * </li> 
 * <li>{@link #getResultsFormDefinitions() getResultsFormDefinitions}: If the 
 * <code>FormDefinitionService</code> method that was called returns any array of 
 * <code>ResultsFormDefinition</code>s, then this method returns them. 
 * </li> 
 * </ul>
 */
public class FormDefinitionServiceResponse {

  private List _forms;
  private Map _formsCache;
  private List _dataForms;
  private List _queryForms;
  private List _resultsForms;
  private BtxActionErrors _errors;
  
  FormDefinitionServiceResponse() {
    super();
  }

  /**
   * Returns the errors that are part of this response. 
   * 
   * @return  The <code>BtxActionErrors</code> instance that contains the errors.  If there are
   *          no errors, then an empty <code>BtxActionErrors</code> is returned.
   */
  public BtxActionErrors getErrors() {
    return (_errors == null) ? new BtxActionErrors() : _errors;
  }

  /**
   * Returns the form definition that is part of this response.  If this method is called when more 
   * than one form definition is present in the response, the first form definition is returned.
   * 
   * @return  The <code>FormDefinition</code> instance.  If there is no 
   * <code>FormDefinition</code> in the response, then <code>null</code> is returned.
   */
  public FormDefinition getFormDefinition() {
    return (_forms == null) ? null : (FormDefinition) _forms.get(0);      
  }

  /**
   * Returns the data form definition that is part of this response.  This is a convenience method
   * that can be used when a single data form definition is expected, such as when calling 
   * {@link FormDefinitionService#findFormDefinitionById(String) findFormDefinitionById}.  If this
   * method is called when more than one data form definition is present in the response, the
   * first data form definition is returned.
   * 
   * @return  The <code>DataFormDefinition</code> instance.  If there is no 
   * <code>DataFormDefinition</code> in the response, then <code>null</code> is returned.
   */
  public DataFormDefinition getDataFormDefinition() {
    return (_dataForms == null) ? null : (DataFormDefinition) _dataForms.get(0);  
  }

  /**
   * Returns the query form definition that is part of this response.  This is a convenience method
   * that can be used when a single query form definition is expected, such as when calling 
   * {@link FormDefinitionService#findFormDefinitionById(String) findFormDefinitionById}.  If this
   * method is called when more than one query form definition is present in the response, the
   * first query form definition is returned.
   * 
   * @return  The <code>QueryFormDefinition</code> instance.  If there is no 
   * <code>QueryFormDefinition</code> in the response, then <code>null</code> is returned.
   */
  public QueryFormDefinition getQueryFormDefinition() {
    return (_queryForms == null) ? null : (QueryFormDefinition) _queryForms.get(0);  
  }

  /**
   * Returns the results form definition that is part of this response.  This is a convenience 
   * method that can be used when a single results form definition is expected, such as when calling 
   * {@link FormDefinitionService#findFormDefinitionById(String) findFormDefinitionById}.  If this
   * method is called when more than one results form definition is present in the response, the
   * first results form definition is returned.
   * 
   * @return  The <code>ResultsFormDefinition</code> instance.  If there is no 
   * <code>ResultsFormDefinition</code> in the response, then <code>null</code> is returned.
   */
  public ResultsFormDefinition getResultsFormDefinition() {
    return (_resultsForms == null) ? null : (ResultsFormDefinition) _resultsForms.get(0);  
  }

  /**
   * Returns the array of form definitions that are part of this response. 
   * 
   * @return  The array of <code>FormDefinition</code> instances.  If there are no 
   * <code>FormDefinition</code>s in the response, then a zero-length array is returned.
   */
  public FormDefinition[] getFormDefinitions() {
    if (_forms == null) {
      // Note: we use DataFormDefinition as the concrete class, but it really doesn't matter which
      // concrete class we use since we're returning a zero-length array. 
      return new DataFormDefinition[0];
    }
    else {
      FormDefinition[] forms = new FormDefinition[_forms.size()];
      Iterator formsIterator = _forms.iterator();
      for (int i = 0 ; formsIterator.hasNext(); i++) {
        forms[i] = (FormDefinition) formsIterator.next();
      }
      return forms; 
    }
  }
  
  /**
   * Returns the array of data form definitions that are part of this response. 
   * 
   * @return  The array of <code>DataFormDefinition</code> instances.  If there are no 
   * <code>DataFormDefinition</code>s in the response, then a zero-length array is returned.
   */
  public DataFormDefinition[] getDataFormDefinitions() {
    if (_dataForms == null) {
      return new DataFormDefinition[0];
    }
    else {
      return (DataFormDefinition[]) _dataForms.toArray(new DataFormDefinition[0]); 
    }
  }
  
  /**
   * Returns the array of query form definitions that are part of this response. 
   * 
   * @return  The array of <code>QueryFormDefinition</code> instances.  If there are no 
   * <code>QueryFormDefinition</code>s in the response, then a zero-length array is returned.
   */
  public QueryFormDefinition[] getQueryFormDefinitions() {
    if (_queryForms == null) {
      return new QueryFormDefinition[0];
    }
    else {
      return (QueryFormDefinition[]) _queryForms.toArray(new QueryFormDefinition[0]); 
    }
  }

  /**
   * Returns the array of results form definitions that are part of this response. 
   * 
   * @return  The array of <code>ResultsFormDefinition</code> instances.  If there are no 
   * <code>ResultsFormDefinition</code>s in the response, then a zero-length array is returned.
   */
  public ResultsFormDefinition[] getResultsFormDefinitions() {
    if (_resultsForms == null) {
      return new ResultsFormDefinition[0];
    }
    else {
      return (ResultsFormDefinition[]) _resultsForms.toArray(new ResultsFormDefinition[0]); 
    }
  }

  void setErrors(BtxActionErrors errors) {
    _errors = errors;
  }

  void addFormDefinition(FormDefinition form) {
    // First add the form definition to the generic form definition list.
    if (_forms == null) {
      _forms = new ArrayList();
      _formsCache = new HashMap();
    }
    _forms.add(form);
    _formsCache.put(form.getFormDefinitionId(), form);

    // Next, add the form definition to the concrete form definition list.
    String type = form.getType();
    if (FormDefinitionTypes.DATA.equals(type)) {
      if (_dataForms == null) {
        _dataForms = new ArrayList();
      }
      _dataForms.add(form);      
    }
    else if (FormDefinitionTypes.QUERY.equals(type)) {
      if (_queryForms == null) {
        _queryForms = new ArrayList();
      }
      _queryForms.add(form);            
    }
    else if (FormDefinitionTypes.RESULTS.equals(type)) {
      if (_resultsForms == null) {
        _resultsForms = new ArrayList();
      }
      _resultsForms.add(form);            
    }
    else {
      throw new ApiException("Attempt to add form definition of unknown type to FormDefinitionServiceResponse: " + type);
    }
  }

  FormDefinition getFormDefinition(String formDefinitionId) {
    return (_forms == null) ? null : (FormDefinition) _formsCache.get(formDefinitionId);      
  }
}
