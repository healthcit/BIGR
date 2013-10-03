package com.ardais.bigr.kc.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.kc.form.def.BigrFormDefinition;
import com.ardais.bigr.kc.form.def.BtxDetailsKcFormDefinition;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;
import com.ardais.bigr.web.form.PopulatesRequestFromBtxDetails;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionDataElement;
import com.gulfstreambio.kc.web.support.DataFormDataElementContext;
import com.gulfstreambio.kc.web.support.FormContext;
import com.gulfstreambio.kc.web.support.FormContextStack;

public class AdePopupForm extends BigrActionForm implements PopulatesRequestFromBtxDetails {

  private String _formDefinitionId;
  private String _dataElementSystemName;
  private String[] _excludedValues;


  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    _formDefinitionId = null;
    _dataElementSystemName = null;
    _excludedValues = null;
  }

  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
    return null;
  }

  private String getDataElementSystemName() {
    return _dataElementSystemName;
  }

  public void setDataElementSystemName(String dataElementSystemName) {
    _dataElementSystemName = dataElementSystemName;
  }
  
  private String[] getExcludedValues() {
    return _excludedValues;
  }
  
  public void setExcludedValues(String[] excludedValues) {
    _excludedValues = excludedValues;
  }
  
  private String getFormDefinitionId() {
    return _formDefinitionId;
  }
  
  public void setFormDefinitionId(String formDefinitionId) {
    _formDefinitionId = formDefinitionId;
  }

  public void describeIntoBtxDetails(BTXDetails btxDetails0, BigrActionMapping mapping,
                                     HttpServletRequest request) {
    super.describeIntoBtxDetails(btxDetails0, mapping, request);

    // Create a new BIGR form definition and populate it with the user's input.
    BigrFormDefinition formDef = new BigrFormDefinition();
    formDef.setFormDefinitionId(getFormDefinitionId());

    // Populate the BTX details.
    BtxDetailsKcFormDefinition formDefBtxDetails = (BtxDetailsKcFormDefinition) btxDetails0;
    formDefBtxDetails.setFormDefinition(formDef);    
  }
  
  public void populateRequestFromBtxDetails(BTXDetails btxDetails, BigrActionMapping mapping,
                                            HttpServletRequest request) {
    super.populateRequestFromBtxDetails(btxDetails, mapping, request);
    
    String dataElementSystemName = getDataElementSystemName();
    
    BtxDetailsKcFormDefinition formDefBtxDetails = (BtxDetailsKcFormDefinition) btxDetails;
    BigrFormDefinition formDef = formDefBtxDetails.getFormDefinition();
    DataFormDefinition kcFormDef = (DataFormDefinition) formDef.getKcFormDefinition();
    DataFormDefinitionDataElement dataElementDef = 
      kcFormDef.getDataDataElement(dataElementSystemName);
    
    // Push all information onto the stack regarding the current form and data element.
    FormContextStack stack = FormContextStack.getFormContextStack(request);
    FormContext formContext = stack.peek();
    formContext.setDataFormDefinition(kcFormDef);
    formContext.setDataFormDefinitionDataElement(dataElementDef);
    stack.push(formContext);
    
    // Get the data element context from the top of the stack and set it to treat the data
    // element as if it was singlevalued, even if it is multivalued.  This is needed since
    // the ADE popup only allows a single value of a data element to be chosen.
    formContext = stack.peek();
    DataFormDataElementContext dataElementContext = formContext.getDataFormDataElementContext();
    dataElementContext.setTreatAsSinglevalued(true);
    
    // Also set the values to exclude from the list of values for a CV data element.  The list
    // of excluded values will contain those that have already been selected for the data element
    // with this ADE, since we only allow a value to be selected once for an instance of a
    // data element.
    String[] excludedValues = getExcludedValues();
    if (!ApiFunctions.isEmpty(excludedValues)) {
      dataElementContext.setExcludedValues(excludedValues);
    }    
  }

}
