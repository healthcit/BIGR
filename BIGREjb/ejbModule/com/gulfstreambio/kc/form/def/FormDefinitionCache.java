package com.gulfstreambio.kc.form.def;

import java.util.HashMap;
import java.util.Map;

/**
 * Used by {@link FormDefinitionService} to cache form definitions for performance. 
 */
class FormDefinitionCache {

  private Map _forms;
  private Map _resultsForms;
  
  FormDefinitionCache() {
    super();
    initializeFormsCache();
    initializeResultsFormsCache();
  }

  private synchronized void initializeFormsCache() {
    _forms = new HashMap(503);    
  }
  
  private synchronized void initializeResultsFormsCache() {
    _resultsForms = new HashMap(307);
  }

  synchronized void add(FormDefinition formDefinition) {
    _forms.put(formDefinition.getFormDefinitionId(), formDefinition);
    if (FormDefinitionTypes.RESULTS.equals(formDefinition.getType())) {
      _resultsForms.put(formDefinition.getFormDefinitionId(), formDefinition);
    }
  }

  synchronized FormDefinition get(String formDefinitionId) {
    // Return a copy of the form to allow the caller to modify the returned form and not affect
    // the instance of the form in the cache.
    FormDefinition form = (FormDefinition) _forms.get(formDefinitionId);
    return (form != null) ? form.copy() : null; 
  }
  
  synchronized void remove(String formDefinitionId) {
    _forms.remove(formDefinitionId);
    _resultsForms.remove(formDefinitionId);
  }

  synchronized void removeResultsForms() {
    initializeResultsFormsCache();
  }
}
