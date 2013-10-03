package com.ardais.bigr.kc.form.def;

import com.ardais.bigr.validation.ValidatorContext;
import com.ardais.bigr.validation.ValidatorContextUpdater;

/**
 * A <code>ValidatorContextUpdater</code> for use with the
 * {@link BigrFormDefinitionValidatorContext} that creates a new 
 * {@link com.ardais.bigr.kc.form.def.BigrFormDefinition BigrFormDefinition} from the existing
 * <code>BigrFormDefinition</code> and 
 * {@link com.gulfstreambio.kc.form.def.data.DataFormDefinition DataFormDefinition} in the context and
 * writes the newly created <code>BigrFormDefinition</code> back into the context.
 */
public class ContextUpdaterBigrFormDefinition implements ValidatorContextUpdater {

  /**
   * Creates a new <code>ContextUpdaterFormDefinition</code>.
   */
  public ContextUpdaterBigrFormDefinition() {
    super();
  }

  public String getName() {
    return "context updater: creates a new BIGR form definition from BIGR and KC form definitions";
  }
  
  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.ValidatorContextUpdater#update(com.ardais.bigr.validation.ValidatorContext)
   */
  public void update(ValidatorContext context) {
    BigrFormDefinitionValidatorContext c = (BigrFormDefinitionValidatorContext) context;
    c.setFormDefinition(new BigrFormDefinition(c.getFormDefinition(), c.getKcFormDefinition()));
  }

}
