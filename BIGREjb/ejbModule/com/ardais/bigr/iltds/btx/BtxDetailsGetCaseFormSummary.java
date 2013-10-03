package com.ardais.bigr.iltds.btx;

import java.util.List;
import java.util.Set;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.kc.form.BigrFormInstance;
import com.ardais.bigr.kc.form.def.BigrFormDefinition;

/**
 * Represent the summary of forms for an imported case.
 * This doesn't actually do any database changes, it just gets data that is needed to
 * prepare the user input screens.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields: One of the following</h4>
 * <ul>
 * <li>{@link #setConsentId(String) consent id}: The Ardais consent id.</li>
 * <li>{@link #setCustomerId(String) customer id}: The customer consent id.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 */
public class BtxDetailsGetCaseFormSummary extends BtxDetailsModifyImportedCase implements java.io.Serializable {
  private static final long serialVersionUID = 3203215166095921736L;
  
  private BigrFormDefinition[] _formDefinitions;
  private BigrFormInstance[] _formInstances;
  private boolean _readOnly;  

  public BtxDetailsGetCaseFormSummary() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_GET_CASE_FORM_SUMMARY;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public Set getDirectlyInvolvedObjects() {
    return null;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    throw new ApiException("doGetDetailsAsHTML should not have been called, this class does not support transaction logging.");
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#describeIntoHistoryRecord(com.ardais.bigr.iltds.btx.BTXHistoryRecord)
   */
  public void describeIntoHistoryRecord(BTXHistoryRecord history) {
    throw new ApiException("describeIntoHistoryRecord should not have been called, this class does not support transaction logging.");

    //super.describeIntoHistoryRecord(history);
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#populateFromHistoryRecord(com.ardais.bigr.iltds.btx.BTXHistoryRecord)
   */
  public void populateFromHistoryRecord(BTXHistoryRecord history) {
    throw new ApiException("populateFromHistoryRecord should not have been called, this class does not support transaction logging.");

    //super.populateFromHistoryRecord(history);
  }
  
  /**
   * @return Returns the formInstances.
   */
  public BigrFormInstance[] getFormInstances() {
    return _formInstances;
  }
  
  /**
   * @param formInstances The formInstances to set.
   */
  public void setFormInstances(BigrFormInstance[] formInstances) {
    _formInstances = formInstances;
  }
  
  /**
   * @return Returns the formDefinitions.
   */
  public BigrFormDefinition[] getFormDefinitions() {
    return _formDefinitions;
  }
  
  /**
   * @param formDefinitions The formDefinitions to set.
   */
  public void setFormDefinitions(BigrFormDefinition[] formDefinitions) {
    _formDefinitions = formDefinitions;
  }

  public boolean isReadOnly() {
    return _readOnly;
  }

  public void setReadOnly(boolean readOnly) {
    _readOnly = readOnly;
  }
}