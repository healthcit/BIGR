package com.ardais.bigr.iltds.btx;

import java.util.Set;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.kc.form.BigrFormInstance;
import com.ardais.bigr.kc.form.def.BigrFormDefinition;

/**
 * Represents the details of obtaining the default registration form for a sample.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BtxDetailsImportedSample BtxDetailsImportedSample} for fields inherited
 * by that class.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #getPolicyId() policyId}: The policy id of the sample's case.</li>
 * <li>{@link #getSampleData().getSampleType() sampleType}: The sample type of the sample.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #getSampleData().setRegistrationForm(DataFormDefinition) registrationForm}: 
 * The default registration form definition for the sample.</li>
 * <li>{@link #getSampleData().setRegistrationFormInstance(FormInstance) registrationFormInstance}: 
 * The default registration form instance for the sample, if it exists.</li>
 * </ul>
 */
public class BtxDetailsGetSampleRegistrationForm extends BtxDetailsImportedSample {

  public BtxDetailsGetSampleRegistrationForm() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_GET_SAMPLE_REG_FORM;
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
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#populateFromHistoryRecord(com.ardais.bigr.iltds.btx.BTXHistoryRecord)
   */
  public void populateFromHistoryRecord(BTXHistoryRecord history) {
    throw new ApiException("populateFromHistoryRecord should not have been called, this class does not support transaction logging.");
  }
}
