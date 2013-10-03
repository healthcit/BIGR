package com.gulfstreambio.kc.form.def;

import com.ardais.bigr.api.ApiException;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinition;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinition;

public class FormDefinitionFactory {

  public static final FormDefinitionFactory SINGLETON = new FormDefinitionFactory();
  
  private FormDefinitionFactory() {
    super();
  }

  public FormDefinition create(String type) {
    if (FormDefinitionTypes.DATA.equals(type)) {
      return new DataFormDefinition();
    }
    else if (FormDefinitionTypes.QUERY.equals(type)) {
      return new QueryFormDefinition();
    }
    else if (FormDefinitionTypes.RESULTS.equals(type)) {
      return new ResultsFormDefinition();
    }
    else  {
      throw new ApiException("In FormDefinitionFactory, attempt to create unknown form type: " + type);
    }
  }

  public FormDefinition createFromXml(String type, String xml) {
    if (FormDefinitionTypes.DATA.equals(type)) {
      return DataFormDefinition.createFromXml(xml);
    }
    else if (FormDefinitionTypes.QUERY.equals(type)) {
      return QueryFormDefinition.createFromXml(xml);
    }
    else if (FormDefinitionTypes.RESULTS.equals(type)) {
      return ResultsFormDefinition.createFromXml(xml);
    }
    else  {
      throw new ApiException("In FormDefinitionFactory, attempt to create unknown form type: " + type);
    }
  }
  
}
