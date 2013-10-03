package com.ardais.bigr.iltds.btx;

import java.io.Serializable;
import java.util.List;

import com.ardais.bigr.kc.form.def.BigrFormDefinition;

/**
 * Represent the details of creating a sample.
 * <p>
 * The transaction-specific fields are described below.
 * 
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setNewDonor(String) newDonor}: An indicator as to whether the specified donor is
 * new and therefore needs to be created.</li>
 * <li>{@link #setLinkedIndicator(String) linkedIndicator}: If the specified case is new and therefore needs
 * to be created, this field specifies if the case is to be linked or unlinked.</li>
 * <li>{@link #setConsentVersionId(String) consentVersionId}: If the specified case is new and therefore needs
 * to be created and is linked, this field specifies the consent version under which the case
 * should be created.</li>
 * <li>{@link #setPolicyId(String) policyId}: If the specified case is new and therefore needs
 * to be created and is unlinked, this field specifies the policy under which the case
 * should be created.</li>
 * <li>{@link #setNewCase(String) newCase}: An indicator as to whether the specified case is
 * new and therefore needs to be created.</li>
 * <li>{@link #setSampleData(SampleData) sampleData}: The sample data object to create.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>{@link #setInventoryGroupIds(List) list}: A list of inventory group ids to which the
 * new sample should be assigned.</li>
 * <li>{@link #setCreateForm(boolean) createForm}: An indicator as to whether the user wishes
 * to navigate to the form creation page when the case is created or not.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setSampleAction(String) sampleAction}: An indicator as to whether the
 * sample was created or modified.  Because the specified sample might already exist
 * due to a box scan operation, a "Create Sample" transaction might in fact end up
 * modifying a sample instead of creating it.</li>
 * <li>{@link #setSample(SampleData) sample}: A <code>SampleData</code> bean holding the information
 * for the sample.  Set as part of creating or modifying a sample, to enable logging.</li>
 * <li>{@link #setFormDefinitions(BigrFormDefinition[]) formDefinitions}: An array of KC form definitions available for samples. </li>
 * </ul>
 */
public class BtxDetailsCreateImportedSample extends BtxDetailsImportedSample implements Serializable {
  private static final long serialVersionUID = 4157733775432122687L;

  private String _rememberedData;
  private List _consentChoices;
  private List _policyChoices;
  private List _inventoryGroupIds;
  private boolean _createForm = false;
  private BigrFormDefinition[] _formDefinitions;

  public BtxDetailsCreateImportedSample() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_CREATE_IMPORTED_SAMPLE;
  }

  /**
   * @return
   */
  public List getConsentChoices() {
    return _consentChoices;
  }

  /**
   * @return
   */
  public List getPolicyChoices() {
    return _policyChoices;
  }

  /**
   * @return
   */
  public String getRememberedData() {
    return _rememberedData;
  }

  /**
   * @param list
   */
  public void setConsentChoices(List list) {
    _consentChoices = list;
  }

  /**
   * @param list
   */
  public void setPolicyChoices(List list) {
    _policyChoices = list;
  }

  /**
   * @param string
   */
  public void setRememberedData(String string) {
    _rememberedData = string;
  }
  /**
   * @return
   */
  public List getInventoryGroupIds() {
    return _inventoryGroupIds;
  }

  /**
   * @param list
   */
  public void setInventoryGroupIds(List list) {
    _inventoryGroupIds = list;
  }

  public boolean isCreateForm() {
    return _createForm;
  }

  public void setCreateForm(boolean createForm) {
    _createForm = createForm;
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
}
