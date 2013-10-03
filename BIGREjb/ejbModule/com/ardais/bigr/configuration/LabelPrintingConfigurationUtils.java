package com.ardais.bigr.configuration;

import java.util.List;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.kc.form.def.TagTypes;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionService;
import com.gulfstreambio.kc.form.def.FormDefinitionServiceResponse;
import com.gulfstreambio.kc.form.def.Tag;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinition;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionHostElement;

public class LabelPrintingConfigurationUtils {
  
  public static boolean isResultsFormDefinitionValid(ResultsFormDefinition resultsFormdef) {
    boolean returnValue = true;
    ResultsFormDefinitionHostElement[] hostElements = resultsFormdef.getResultsHostElements();
    if (hostElements.length > 0) {
      List validHostElements = SystemConfiguration.getSimpleList(SystemConfiguration.SIMPLE_LIST_RESULTS_VIEW_COLUMNS).getListItems();
      for (int i=0; i<hostElements.length; i++) {
        ResultsFormDefinitionHostElement hostElement = hostElements[i];
        String hostId = hostElement.getHostId();
        //make sure the host id exists and is valid
        if (ApiFunctions.isEmpty(hostId) || !validHostElements.contains(hostElement.getHostId())) {
          returnValue = false;
        }
      }
    }
    
    ResultsFormDefinitionDataElement[] dataElements = resultsFormdef.getResultsDataElements();
    if (dataElements.length > 0) {
      for (int i=0; i<dataElements.length; i++) {
        ResultsFormDefinitionDataElement dataElement = dataElements[i];
        String cui = dataElement.getCui();
        //if there is no cui, return an error
        if (ApiFunctions.isEmpty(cui)) {
          returnValue = false;
        }
        //otherwise, make sure the data element specifies a parent form definition that
        //exists and actually contains the data element
        else {
          Tag[] tags = dataElement.getTags();
          String parentFormId = null;
          for (int j=0; j<tags.length; j++) {
            Tag tag = tags[j];
            if (TagTypes.PARENT.equalsIgnoreCase(tag.getType())) {
              parentFormId = tag.getValue();
            }
          }
          if (ApiFunctions.isEmpty(parentFormId)) {
            returnValue = false;
          }
          else {
            FormDefinitionServiceResponse response = FormDefinitionService.SINGLETON.findFormDefinitionById(parentFormId);
            FormDefinition formDefinition = response.getFormDefinition();
            if (formDefinition == null) {
              returnValue = false;
            }
            else {
              if (formDefinition.getDataElement(cui) == null) {
                returnValue = false;
              }
            }
          }
        }
      }
    }
    
    if (hostElements.length == 0 && dataElements.length == 0) {
      returnValue = false;
    }
    
    return returnValue;
  }
  
}
