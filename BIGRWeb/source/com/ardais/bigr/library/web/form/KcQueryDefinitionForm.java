package com.ardais.bigr.library.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.library.btx.BTXDetailsGetKcQueryForm;
import com.ardais.bigr.library.web.helper.ResultsHelper;
import com.ardais.bigr.util.WebUtils;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;
import com.ardais.bigr.web.form.PopulatesRequestFromBtxDetails;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinition;

public class KcQueryDefinitionForm extends BigrActionForm implements PopulatesRequestFromBtxDetails {

  private String _formDefinitionId;  
  private String _dataElementId;  
  private QueryFormDefinition _formDefinition;
  private String _txType;
  
  /* (non-Javadoc)
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    _formDefinitionId = null;
    _dataElementId = null;
    _formDefinition = null;
    _txType = null;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
    return null;
  }

  public void populateRequestFromBtxDetails(BTXDetails btxDetails, BigrActionMapping mapping,
                                            HttpServletRequest request) {

    String txType = getTxType();
    ResultsHelper rh = !ApiFunctions.isEmpty(txType) ? ResultsHelper.get(txType, request) : null;
    QueryFormDefinition form = null;      
    
    if ("useSessionForm".equals(mapping.getTag())) {
      if (rh != null) {
        form = rh.getQueryForm();      
      }
    }
    else {
      BTXDetailsGetKcQueryForm btx = (BTXDetailsGetKcQueryForm) btxDetails;
      form = btx.getFormDefinition();      
      if (rh != null) {
        synchronized (rh) {
          rh.setQueryForm(form);
        }      
      }
    }

    setFormDefinition(form);
    WebUtils.commonKcQuerySetup(request, txType, form, getDataElementId());    
  }

  public String getFormDefinitionId() {
    return _formDefinitionId;
  }
  
  public void setFormDefinitionId(String formDefinitionId) {
    _formDefinitionId = formDefinitionId;
  }
  
  public String getDataElementId() {
    return _dataElementId;
  }
  
  public void setDataElementId(String dataElementId) {
    _dataElementId = dataElementId;
  }
  
  public QueryFormDefinition getFormDefinition() {
    return _formDefinition;
  }
  
  private void setFormDefinition(QueryFormDefinition formDefinition) {
    _formDefinition = formDefinition;
  }
  
  public String getTxType() {
    return _txType;
  }
  
  public void setTxType(String txType) {
    _txType = txType;
  }
}
