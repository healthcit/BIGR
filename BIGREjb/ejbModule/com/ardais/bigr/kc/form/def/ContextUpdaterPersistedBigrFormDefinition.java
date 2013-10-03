package com.ardais.bigr.kc.form.def;

import com.ardais.bigr.validation.ValidatorContext;
import com.ardais.bigr.validation.ValidatorContextUpdater;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionService;
import com.gulfstreambio.kc.form.def.FormDefinitionServiceResponse;

/**
 * A <code>ValidatorContextUpdater</code> for use with the
 * {@link BigrFormDefinitionValidatorContext} that queries for the persisted form definition
 * specified by the id of the  
 * {@link com.ardais.bigr.kc.form.def.BigrFormDefinition BigrFormDefinition} already in the context
 * and adds the persisted form definition as a <code>BigrFormDefinition</code> to the context.
 */
public class ContextUpdaterPersistedBigrFormDefinition implements ValidatorContextUpdater {

  /**
   * Creates a new <code>ContextUpdaterPersistedFormDefinition</code>.
   */
  public ContextUpdaterPersistedBigrFormDefinition() {
    super();
  }

  public String getName() {
    return "context updater: gets the persisted form definition";
  }
  
  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.ValidatorContextUpdater#update(com.ardais.bigr.validation.ValidatorContext)
   */
  public void update(ValidatorContext context) {
    BigrFormDefinitionValidatorContext c = (BigrFormDefinitionValidatorContext) context;
    String formDefinitionId = c.getFormDefinition().getFormDefinitionId();    
    FormDefinitionServiceResponse response =
      FormDefinitionService.SINGLETON.findFormDefinitionById(formDefinitionId);
    FormDefinition persistedKcFormDef = response.getFormDefinition();
    if (persistedKcFormDef != null) {
      c.setPersistedFormDefinition(new BigrFormDefinition(persistedKcFormDef));      
    }
    else {
      c.setPersistedFormDefinition(c.getFormDefinition());       
    }
  }

}
