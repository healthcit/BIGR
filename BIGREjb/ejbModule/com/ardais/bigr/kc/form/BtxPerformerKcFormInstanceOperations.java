
package com.ardais.bigr.kc.form;

import java.util.ArrayList;
import java.util.List;

import com.ardais.bigr.btx.framework.BtxTransactionPerformerBase;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.kc.form.def.BigrFormDefinition;
import com.ardais.bigr.kc.form.def.TagTypes;
import com.gulfstreambio.kc.form.FormInstance;
import com.gulfstreambio.kc.form.FormInstanceQueryCriteria;
import com.gulfstreambio.kc.form.FormInstanceService;
import com.gulfstreambio.kc.form.FormInstanceServiceResponse;
import com.gulfstreambio.kc.form.def.FormDefinitionService;
import com.gulfstreambio.kc.form.def.FormDefinitionServiceResponse;
import com.gulfstreambio.kc.form.def.Tag;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;


public class BtxPerformerKcFormInstanceOperations extends BtxTransactionPerformerBase {

  public BtxPerformerKcFormInstanceOperations() {
    super();
  }

  /**
   * Returns summary information for all form instances for the specified domain object, including 
   * the associated form definition for each.
   */
  private BTXDetails performDomainObjectSummary(BtxDetailsKcFormInstanceDomainObjectSummary btxDetails) throws Exception {
    BigrFormInstance bigrFormInstance = btxDetails.getFormInstance();
    FormInstanceQueryCriteria criteria = new FormInstanceQueryCriteria();
    criteria.addDomainObjectId(bigrFormInstance.getDomainObjectId());
    FormInstanceServiceResponse response = 
      FormInstanceService.SINGLETON.findFormInstances(criteria);

    // Create new BIGR form instances for each KC form instance that was returned.  Make sure to
    // only add instances of annotation forms.
    FormInstance[] kcFormInstances = response.getFormInstances();
    if (kcFormInstances != null) {
      int numForms = kcFormInstances.length;
      List formInstances = new ArrayList(); 
      for (int i = 0; i < numForms; i++) {
        BigrFormInstance formInstance = new BigrFormInstance(kcFormInstances[i]);
        FormDefinitionServiceResponse response2 =
          FormDefinitionService.SINGLETON.findFormDefinitionById(formInstance.getFormDefinitionId());
        DataFormDefinition formDef = response2.getDataFormDefinition();
        boolean isAnnotation = false;
        Tag[] tags = formDef.getTags();
        for (int j = 0; j < tags.length; j++) {
          Tag tag = tags[j];
          if (tag.getType().equals(TagTypes.USES) && tag.getValue().equals(TagTypes.USES_VALUE_ANNOTATION)) {
            isAnnotation = true;
            break;
          }
        }
        if (isAnnotation) {
          formInstance.setFormDefinition(new BigrFormDefinition(formDef));
          formInstances.add(formInstance);
        }
      }
      btxDetails.setFormInstances((BigrFormInstance[])formInstances.toArray(new BigrFormInstance[0]));
    }
    else {
      btxDetails.setFormInstances(new BigrFormInstance[0]);
    }
    btxDetails.setActionForwardTXCompleted("success");    
    return btxDetails;
  }

  /**
   * 
   */
  private BTXDetails validatePerformDomainObjectSummary(BtxDetailsKcFormInstanceDomainObjectSummary btxDetails) throws Exception {
/*   BigrFormInstance bigrFormInstance = btxDetails.getFormInstance();
    BtxActionErrors errors  =
      FormInstanceService.SINGLETON.validateFindFormInstancesSummary(bigrFormInstance.getDomainObjectId(), null);
    if (!errors.empty()) {
      btxDetails.addActionErrors(errors);      
    }
*/    
    return btxDetails;
  }

  /**
   * Prepares for creating a form instance by retrieving and returning the form definition upon
   * which the form instance is to be based. 
   */
  private BTXDetails performCreateFormInstancePrepare(BtxDetailsKcFormInstanceCreatePrepare btxDetails) throws Exception {
    // Find the form definition with the specified id.
    BigrFormInstance formInstance = btxDetails.getFormInstance();
    FormDefinitionServiceResponse response = 
      FormDefinitionService.SINGLETON.findFormDefinitionById(formInstance.getFormDefinitionId());

    // Get the KC form definition from the response, and create a new BIGR form definition to
    // be returned.
    DataFormDefinition kcFormDef = response.getDataFormDefinition();
    if (kcFormDef != null) {
      btxDetails.setFormDefinition(new BigrFormDefinition(kcFormDef));
    }

    btxDetails.setActionForwardTXCompleted("success");        
    return btxDetails;
  }  
  
  private BTXDetails validatePerformCreateFormInstancePrepare(BtxDetailsKcFormInstanceCreatePrepare btxDetails) throws Exception {
    BigrFormInstance bigrFormInstance = btxDetails.getFormInstance();
    BtxActionErrors errors = 
      FormDefinitionService.SINGLETON.validateFindFormDefinitionById(bigrFormInstance.getFormDefinitionId());                                               
    if (!errors.empty()) {
      // This is a bit of a hack, but we know that there is only a single error that can be 
      // returned, which is that the form definition id was not specified, so if we get the
      // error create a new one with a better message.
      errors = new BtxActionErrors();
      errors.add(BtxActionErrors.GLOBAL_ERROR, 
          new BtxActionError("kc.error.forminst.formdefnotspecified"));
      btxDetails.addActionErrors(errors);      
    }
    return btxDetails;
  }  
  
  /**
   * Creates a single KnowledgeCapture form instance.
   */
  private BTXDetails performCreateFormInstance(BtxDetailsKcFormInstanceCreate btxDetails) throws Exception {
    BigrFormInstance bigrFormInstance = btxDetails.getFormInstance();
    FormInstanceServiceResponse response = 
      FormInstanceService.SINGLETON.createFormInstance(bigrFormInstance.getKcFormInstance());
                                                

    if (!response.getErrors().empty()) {
      btxDetails.addActionErrors(response.getErrors());
      btxDetails.setActionForwardRetry();      
    }
    else {
      FormInstance kcFormInstance = response.getFormInstance();
      if (kcFormInstance != null ) {
        // Create a new BIGR form instance based on the KC form instance.
        bigrFormInstance = new BigrFormInstance(kcFormInstance);

        // Lookup and return the form definition as well.
        lookupFormDefinitionForFormInstance(bigrFormInstance);
        
        btxDetails.setFormInstance(bigrFormInstance);
        btxDetails.setActionForwardTXCompleted("success");      
      }      
    }
    return btxDetails;
  }

  private BTXDetails validatePerformCreateFormInstance(BtxDetailsKcFormInstanceCreate btxDetails) throws Exception {
    BigrFormInstance bigrFormInstance = btxDetails.getFormInstance();
    BtxActionErrors errors = 
      FormInstanceService.SINGLETON.validateCreateFormInstance(bigrFormInstance.getKcFormInstance());                                               
    if (!errors.empty()) {
      btxDetails.addActionErrors(errors);
      //we still need to make sure the formDefinition is made available to the GUI
      FormDefinitionServiceResponse response = 
        FormDefinitionService.SINGLETON.findFormDefinitionById(bigrFormInstance.getFormDefinitionId());

      // Get the KC form definition from the response, and create a new BIGR form definition to
      // be returned.
      DataFormDefinition kcFormDef = response.getDataFormDefinition();
      if (kcFormDef != null) {
        btxDetails.setFormDefinition(new BigrFormDefinition(kcFormDef));
      }
    }
    return btxDetails;
  }

  /**
   * Returns a single KnowledgeCapture form instance for a specified id, and its associated
   * form definition.
   */
  private BTXDetails performFormInstanceSummary(BtxDetailsKcFormInstanceSummary btxDetails) throws Exception {
    // Find the form definition with the specified id.
    BigrFormInstance bigrFormInstance = btxDetails.getFormInstance();
    FormInstanceServiceResponse response = 
      FormInstanceService.SINGLETON.findFormInstanceById(bigrFormInstance.getFormInstanceId());

    // Get the KC form instance from the response and create a BIGR form instance from it.
    FormInstance formInst = response.getFormInstance();
    if (formInst!= null) {
      BigrFormInstance bigrFormInst = new BigrFormInstance(formInst);
      btxDetails.setFormInstance(bigrFormInst);

      // Lookup and return the form definition as well.
      lookupFormDefinitionForFormInstance(bigrFormInst);
    }

    btxDetails.setActionForwardTXCompleted("success");        
    return btxDetails;
  }

  private BTXDetails validatePerformFormInstanceSummary(BtxDetailsKcFormInstanceSummary btxDetails) throws Exception {
    BigrFormInstance formInstance = btxDetails.getFormInstance();
    BtxActionErrors errors = 
      FormInstanceService.SINGLETON.validateFindFormInstanceById(formInstance.getFormInstanceId()); 
    BigrFormInstance bigrFormInstance = btxDetails.getFormInstance();
    if (!errors.empty()) {
      btxDetails.addActionErrors(errors);      
    }
    return btxDetails;
  }

  /**
   * Prepares for creating a form instance by retrieving and returning the form instance and
   * the definition upon which the form instance is to be based. 
   */
  private BTXDetails performUpdateFormInstancePrepare(BtxDetailsKcFormInstanceUpdatePrepare btxDetails) throws Exception {
    // Find the form definition with the specified id.
    BigrFormInstance formInstance = btxDetails.getFormInstance();
    FormInstanceServiceResponse response = 
      FormInstanceService.SINGLETON.findFormInstanceById(formInstance.getFormInstanceId()); 

    // Get the KC form definition from the response, and create a new BIGR form definition to
    // be returned.
    FormInstance kcFormInstance = response.getFormInstance();
    if (kcFormInstance != null) {
      formInstance = new BigrFormInstance(kcFormInstance);
      btxDetails.setFormInstance(formInstance);
      
      // Lookup and return the form definition as well.
      lookupFormDefinitionForFormInstance(formInstance);
    }
    btxDetails.setActionForwardTXCompleted("success");        
    return btxDetails;
  }  
  
  private BTXDetails validatePerformUpdateFormInstancePrepare(BtxDetailsKcFormInstanceUpdatePrepare btxDetails) throws Exception {
    BigrFormInstance formInstance = btxDetails.getFormInstance();
    BtxActionErrors errors = 
      FormInstanceService.SINGLETON.validateFindFormInstanceById(formInstance.getFormInstanceId()); 
    BigrFormInstance bigrFormInstance = btxDetails.getFormInstance();
    if (!errors.empty()) {
      btxDetails.addActionErrors(errors);      
    }
    return btxDetails;
  }  

  /**
   * Updates a single KnowledgeCapture form instance.
   */
  private BTXDetails performUpdateFormInstance(BtxDetailsKcFormInstanceUpdate btxDetails) throws Exception {
    BigrFormInstance bigrFormInstance = btxDetails.getFormInstance();
    FormInstanceServiceResponse response =
      FormInstanceService.SINGLETON.updateFormInstance(bigrFormInstance.getKcFormInstance());

    if (response.getFormInstance() != null ) {
      // Lookup and return the form definition as well.
      lookupFormDefinitionForFormInstance(bigrFormInstance);
      btxDetails.setActionForwardTXCompleted("success");
    }
    if (!response.getErrors().empty()) {
      btxDetails.addActionErrors(response.getErrors());
      btxDetails.setActionForwardRetry();      
    }
    else {
      FormInstance kcFormInstance = response.getFormInstance();
      if (kcFormInstance != null ) {
        // Create a new BIGR form instance based on the KC form instance.
        bigrFormInstance = new BigrFormInstance(kcFormInstance);

        // Lookup and return the form definition as well.
        lookupFormDefinitionForFormInstance(bigrFormInstance);
        
        btxDetails.setFormInstance(bigrFormInstance);
        btxDetails.setActionForwardTXCompleted("success");      
      }      
    }
    return btxDetails;
  }

  private BTXDetails validatePerformUpdateFormInstance(BtxDetailsKcFormInstanceUpdate btxDetails) throws Exception {
    BigrFormInstance bigrFormInstance = btxDetails.getFormInstance();
    BtxActionErrors errors = 
      FormInstanceService.SINGLETON.validateUpdateFormInstance(bigrFormInstance.getKcFormInstance());                                               
    if (!errors.empty()) {
      btxDetails.addActionErrors(errors);
      //we still need to make sure the formDefinition is made available to the GUI
      FormDefinitionServiceResponse response = 
        FormDefinitionService.SINGLETON.findFormDefinitionById(bigrFormInstance.getFormDefinitionId());

      // Get the KC form definition from the response, and create a new BIGR form definition to
      // be returned.
      DataFormDefinition kcFormDef = response.getDataFormDefinition();
      if (kcFormDef != null) {
        btxDetails.setFormDefinition(new BigrFormDefinition(kcFormDef));
      }      
    }
    return btxDetails;
  }

  private void lookupFormDefinitionForFormInstance(BigrFormInstance formInstance) {
    // Find the form definition for the specified form instance.
    FormDefinitionServiceResponse response = 
      FormDefinitionService.SINGLETON.findFormDefinitionById(formInstance.getFormDefinitionId());

    // Get the KC form definition from the response, and create a new BIGR form definition to
    // be returned.
    DataFormDefinition kcFormDef = response.getDataFormDefinition();
    if (kcFormDef != null) {
      formInstance.setFormDefinition(new BigrFormDefinition(kcFormDef));
    }
  }
  
  /**
   * Invoke the specified method on this class.  This is only meant to be
   * called from BtxTransactionPerformerBase, please don't call it from anywhere
   * else as a mechanism to gain access to private methods in this class.  Every
   * object that the BTX framework dispatches to must contain this
   * method definition, and its implementation should be exactly the same
   * in each class.  Please don't alter this method or its implementation
   * in any way.
   */
  protected BTXDetails invokeBtxEntryPoint(java.lang.reflect.Method method, BTXDetails btxDetails)
    throws Exception {

    // **** DO NOT EDIT THIS METHOD, see the Javadoc comment above.
    return (BTXDetails) method.invoke(this, new Object[] { btxDetails });
  }
}
