package com.ardais.bigr.library.btx;

import java.util.List;
import java.util.Set;

import com.ardais.bigr.iltds.btx.BTXDetails;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinition;

public class BTXDetailsGetSampleSummaryStart extends BTXDetails {

  private String _txType;
  private QueryFormDefinition[] _queryForms; 
  private String _resultsFormDefinitionId;
  private List _resultsFormDefinitions;
  
  public BTXDetailsGetSampleSummaryStart() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_GET_SAMPLE_SUMMARY_START;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public Set getDirectlyInvolvedObjects() {
    return null;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    return "BTXDetailsGetSampleSummaryStart.doGetDetailsAsHTML() method called in error!";
  }

  public QueryFormDefinition[] getQueryFormDefinitions() {
    if (_queryForms == null) {
      _queryForms = new QueryFormDefinition[0];
    }
    return _queryForms;
  }
  
  public void setQueryFormDefinitions(QueryFormDefinition[] queryForms) {
    _queryForms = queryForms;
  }
  
  public String getResultsFormDefinitionId() {
    return _resultsFormDefinitionId;
  }
      
  public void setResultsFormDefinitionId(String resultsFormDefinitionId) {
    _resultsFormDefinitionId = resultsFormDefinitionId;
  }
  
  public List getResultsFormDefinitions() {
    return _resultsFormDefinitions;
  }
  
  public void setResultsFormDefinitions(List resultsFormDefinitions) {
    _resultsFormDefinitions = resultsFormDefinitions;
  }

  public String getTxType() {
    return _txType;
  }
  
  public void setTxType(String txType) {
    _txType = txType;
  }
}
