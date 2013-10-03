package com.ardais.bigr.library.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 *
 * This Form holds data needed to show the results.  
 */
public class ShowResultsForm extends BigrActionForm {
  
    private String _productType;
    private String _txType;
    private String _resultsFormDefinitionId;
    

    public ShowResultsForm() {
        reset();
    }

    /**
     * @see com.ardais.bigr.web.form.BigrActionForm#doReset(BigrActionMapping, HttpServletRequest)
     */
    protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
        reset();
    }

    private void reset() {
        _txType = null;
        _productType = null;
        _resultsFormDefinitionId = null;
    }

    /**
     * Returns the productType.
     * @return String
     */
    public String getProductType() {
        return _productType;
    }

    /**
     * Sets the productType.
     * @param productType The productType to set
     */
    public void setProductType(String productType) {
        _productType = productType;
    }

    /**
     * Returns the txType.
     * @return String
     */
    public String getTxType() {
        return _txType;
    }

    /**
     * Sets the txType.
     * @param txType The txType to set
     */
    public void setTxType(String txType) {
        _txType = txType;
    }
  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(BigrActionMapping, HttpServletRequest)
   */
  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
    return null;
  }
  
  public String getResultsFormDefinitionId() {
    return _resultsFormDefinitionId;
  }
      
  public void setResultsFormDefinitionId(String resultsFormDefinitionId) {
    _resultsFormDefinitionId = resultsFormDefinitionId;
  }

}
