package com.ardais.bigr.iltds.web.form;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxDetailsCreateClinicalFinding;
import com.ardais.bigr.javabeans.ClinicalFindingData;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.pdc.helpers.PdcUtils;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * Struts ActionForm class for clinical findings.
 */
public class ClinicalFindingsForm extends BigrActionForm {
  private String _ardaisId;
  private String _consentId;
  private Map _consentsAndFindings;
  private ClinicalFindingData _clinicalFinding;
  private boolean _newFinding;
  private LegalValueSet _dreChoices;

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    _ardaisId = null;
    _consentId = null;
    _consentsAndFindings = null;
    //reset clinicalFinding to a new data bean, so create/edit of clinical finding
    //data will work correctly
    _clinicalFinding = new ClinicalFindingData();
    _newFinding = false;
    _dreChoices = BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_DRE_RESULTS);
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
    return null;
  }
  /* 
   * Store necessary session-based information
   * @see com.ardais.bigr.web.form.PopulatesRequestFromBtxDetails#populateRequestFromBtxDetails(com.ardais.bigr.iltds.btx.BTXDetails, com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  public void populateRequestFromBtxDetails(BTXDetails btxDetails, 
                                            BigrActionMapping mapping,
                                            HttpServletRequest request) {
    //if the transaction was successful, store the donor and case information in the session
    if (btxDetails.isTransactionCompleted() && !btxDetails.isHasException()) {
      if (btxDetails instanceof BtxDetailsCreateClinicalFinding) {
        BtxDetailsCreateClinicalFinding myDetails = (BtxDetailsCreateClinicalFinding)btxDetails;
        String donorId = myDetails.getArdaisId();
        String caseId = myDetails.getConsentId();
        //we pass "true" for the last parameter because if the user is modifying the same
        //case as the one currently saved in the session we don't want to erase any saved 
        //sample id.  If the user is modifying a different case then any sample information 
        //will be erased regardless of the value of the last parameter.
        PdcUtils.storeLastUsedDonorCaseAndSample(request, donorId, caseId, null, true);
      }
      else {
        throw new IllegalStateException("Unexpected BtxDetails type of " 
        + btxDetails.getClass().getName() + " encountered.");
      }
    }    
  }

  /**
   * @return
   */
  public String getArdaisId() {
    return _ardaisId;
  }

  /**
   * @return
   */
  public ClinicalFindingData getClinicalFinding() {
    return _clinicalFinding;
  }

  /**
   * @return
   */
  public String getConsentId() {
    return _consentId;
  }

  /**
   * @return
   */
  public Map getConsentsAndFindings() {
    return _consentsAndFindings;
  }

  /**
   * @return
   */
  public LegalValueSet getDreChoices() {
    return _dreChoices;
  }

  /**
   * @param string
   */
  public void setArdaisId(String string) {
    _ardaisId = string;
  }

  /**
   * @param data
   */
  public void setClinicalFinding(ClinicalFindingData data) {
    _clinicalFinding = data;
  }

  /**
   * @param string
   */
  public void setConsentId(String string) {
    _consentId = string;
  }

  /**
   * @param map
   */
  public void setConsentsAndFindings(Map map) {
    _consentsAndFindings = map;
  }

  /**
   * @return
   */
  public boolean isNewFinding() {
    return _newFinding;
  }

  /**
   * @param b
   */
  public void setNewFinding(boolean b) {
    _newFinding = b;
  }

}
