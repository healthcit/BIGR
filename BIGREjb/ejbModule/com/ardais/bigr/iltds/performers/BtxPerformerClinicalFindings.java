package com.ardais.bigr.iltds.performers;

import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.btx.framework.BtxTransactionPerformerBase;
import com.ardais.bigr.iltds.beans.ConsentAccessBean;
import com.ardais.bigr.iltds.beans.ConsentKey;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.btx.BtxActionMessage;
import com.ardais.bigr.iltds.btx.BtxDetailsCreateClinicalFinding;
import com.ardais.bigr.iltds.btx.BtxDetailsGetClinicalFindings;
import com.ardais.bigr.javabeans.ClinicalFindingData;
import com.ardais.bigr.pdc.beans.DDCDonor;
import com.ardais.bigr.pdc.beans.DDCDonorHome;
import com.ardais.bigr.pdc.helpers.PdcUtils;
import com.ardais.bigr.pdc.javabeans.DonorData;
import com.ardais.bigr.util.EjbHomes;

/**
 * This performs clinical finding related BTX business transactions.
 */
public class BtxPerformerClinicalFindings extends BtxTransactionPerformerBase {
  
  private static int MAX_COMMENT_LENGTH = 4000;

  public BtxPerformerClinicalFindings() {
    super();
  }
  
  /**
   * This is the main BTX entry point for performing the transaction that starts the process of
   * creating/editing clinical finding information.
   */
  private BTXDetails performGetClinicalFindings(BtxDetailsGetClinicalFindings btxDetails) throws Exception {
    String ardaisId = (new ValidateIds()).validate(btxDetails.getArdaisId(), ValidateIds.TYPESET_DONOR, true);
    String consentId = (new ValidateIds()).validate(btxDetails.getConsentId(), ValidateIds.TYPESET_CASE, true);

    //if a consent id was specified, get any clinical finding data for the consent
    //and go right to the details screen
    if (!ApiFunctions.isEmpty(consentId)) {
      ConsentAccessBean consent = new ConsentAccessBean(new ConsentKey(consentId));
      btxDetails.setClinicalFinding(getClinicalFinding(consent));
      //if the finding is empty that indicates we will be creating one and if not
      //that indicates this is an existing finding so
      //set the new indicator on the btxDetails appropriately
      btxDetails.setNewFinding(isClinicalFindingEmpty(btxDetails.getClinicalFinding()));
      //set the donor id as an output parameter, so it can be stored as the last used donor
      //in the session.  Also set the consent id since the caller may have been passed
      //in in shortened form (i.e. CI9) 
      btxDetails.setArdaisId(consent.getArdais_id());
      btxDetails.setConsentId(consentId);
      btxDetails.setActionForwardTXCompleted("consent");
    }
    else {
      //set the donor id as an output parameter, as it may have been passed in in shortened form (i.e. AI7)
      btxDetails.setArdaisId(ardaisId);
      //get the consents and findings for the specified donor and go to the consent list screen
      btxDetails.setConsentsAndFindings(generateConsentToFindingMap(ardaisId));
      btxDetails.setActionForwardTXCompleted("consentList");
    }

    //return the information
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performGetClinicalFindings performer method.
   */
  private BTXDetails validatePerformGetClinicalFindings(BtxDetailsGetClinicalFindings btxDetails) throws Exception {
    
    //handle spaces
    btxDetails.setArdaisId(ApiFunctions.safeTrim(btxDetails.getArdaisId()));
    btxDetails.setConsentId(ApiFunctions.safeTrim(btxDetails.getConsentId()));

    //make sure either a donor and/or case has been specified
    String donorId = btxDetails.getArdaisId();
    String validatedDonorId = null;
    String consentId = btxDetails.getConsentId();
    
    //if neither a donor nor case was specified then return an error
    if (ApiFunctions.isEmpty(donorId) && ApiFunctions.isEmpty(consentId)) {
      btxDetails.addActionError(new BtxActionError("error.noValue", "donor id and/or consent id"));
    }
    
    //if a donor was specified make sure it's valid and accessible to the user
    if (!ApiFunctions.isEmpty(donorId)) {
      validatedDonorId = (new ValidateIds()).validate(donorId, ValidateIds.TYPESET_DONOR, true);

      if (ApiFunctions.isEmpty(validatedDonorId)) {
        btxDetails.addActionError(new BtxActionError("iltds.error.general.invalidArdaisId"));
      }
      if (!PdcUtils.isDonorAccessibleByDdcUser(btxDetails.getLoggedInUserSecurityInfo(), validatedDonorId)) {
        btxDetails.addActionError(new BtxActionError("iltds.error.general.invalidArdaisId"));
      }

      DonorData donor = new DonorData();
      donor.setArdaisId(validatedDonorId);
      DDCDonorHome home = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
      DDCDonor donorOperation = home.create();
      DonorData donorData = donorOperation.getDonorProfile(donor);
      boolean inAccount =
        btxDetails.getLoggedInUserSecurityInfo().getAccount().equals(
          donorData.getArdaisAccountKey());
      if (!inAccount) {
        btxDetails.addActionError(
          new BtxActionError("iltds.error.general.invalidArdaisId"));
      }

    }
    
    //if a consent was specified make sure it's valid and accessible to the user, and that it
    //belongs to the specified donor (if any)
    if (!ApiFunctions.isEmpty(consentId)) {
      String validatedConsentId = (new ValidateIds()).validate(consentId, ValidateIds.TYPESET_CASE, true);
      if (ApiFunctions.isEmpty(validatedConsentId)) {
        btxDetails.addActionError(new BtxActionError("iltds.error.general.invalidConsentId"));
      }
      else {
        try {
          ConsentAccessBean consent = new ConsentAccessBean(new ConsentKey(validatedConsentId));
          boolean inAccount =
            btxDetails.getLoggedInUserSecurityInfo().getAccount().equals(
              consent.getArdais_acct_key());
          if (!inAccount) {
            btxDetails.addActionError(
              new BtxActionError("iltds.error.general.invalidConsentId"));
          }
          
          //if a donor was specified, make sure this consent belongs to that donor
          if (!ApiFunctions.isEmpty(validatedDonorId) && !validatedDonorId.equalsIgnoreCase(consent.getArdais_id())) {
            btxDetails.addActionError(
              new BtxActionError("iltds.error.general.invalidConsentAndDonorCombination", validatedConsentId, validatedDonorId));
          }
        }
        catch (Exception e) {
          btxDetails.addActionError(
            new BtxActionError("iltds.error.general.invalidConsentId"));
        }
      }
    }

    return btxDetails;
  }
  
  /**
   * This is the main BTX entry point for performing the transaction that
   * creates/edits clinical finding information.
   */
  private BTXDetails performStoreClinicalFinding(BtxDetailsCreateClinicalFinding btxDetails) throws Exception {
    ConsentAccessBean consent = new ConsentAccessBean(new ConsentKey(btxDetails.getConsentId()));
    consent.setPsa(ApiFunctions.safeBigDecimal(btxDetails.getClinicalFinding().getPsa()));
    consent.setDre_cid(btxDetails.getClinicalFinding().getDre());
    consent.setClinical_finding_notes(btxDetails.getClinicalFinding().getClinicalFindingNotes());
    consent.commitCopyHelper();
    //set the ardais id on the btxDetails so it can be stored in the session
    btxDetails.setArdaisId(consent.getArdais_id());
    //return an appropriate message based on create versus edit
    String action = "created";
    if (!btxDetails.isNewFinding()) {
      action = "modified";
    }
    //now that we've save the clinical finding, it is no longr new so reflect that
    //in the btxDetails
    btxDetails.setNewFinding(false);
    btxDetails.addActionMessage(new BtxActionMessage("iltds.message.clinicalFinding.stateChange",btxDetails.getConsentId(), action));
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }
  
  /**
   * Do BTX transaction validation for the performStoreClinicalFindings performer method.
   */
  private BTXDetails validatePerformStoreClinicalFinding(BtxDetailsCreateClinicalFinding btxDetails) throws Exception {
    //make sure the consent id was specified
    if (ApiFunctions.isEmpty(btxDetails.getConsentId())) {
      btxDetails.addActionError(new BtxActionError("error.missedCase", "Consent Id"));
    }
    //make sure there is a clinical data bean provided
    if (btxDetails.getClinicalFinding() == null) {
      btxDetails.addActionError(new BtxActionError("error.missedCase", "Clinical Finding"));
    }
    else {
      //because we don't store the fact that "Not Available" was selected for the PSA
      //value, if we have a null PSA value that is ok.  This is a UI based constraint.
      String psaValue = ApiFunctions.safeTrim(btxDetails.getClinicalFinding().getPsa());
      if (!ApiFunctions.isEmpty(psaValue)) {
        //don't allow negative numbers.  The check for < 0 will catch all cases except for
        //-0.XX, so check for that as well
        if (psaValue.indexOf("-") >= 0) {
          btxDetails.addActionError(new BtxActionError("error.badvalue", psaValue, "Prostate Specific Antigen", "a number between 0.00 and 999.99 (with at most 2 decimal places)"));
        }
        else {
          BigDecimal psa = ApiFunctions.safeBigDecimal(psaValue);
          if (psa == null || psa.intValue() < 0 || psa.intValue() > 999) {
            btxDetails.addActionError(new BtxActionError("error.badvalue", psaValue, "Prostate Specific Antigen", "a number between 0.00 and 999.99 (with at most 2 decimal places)"));
          }
          else {
            //only allow 2 decimals after the decimal point for PSA.  Per Gail, truncate any decimals after 2
            int decPoint = psaValue.indexOf(".");
            if (decPoint > 0 && (psaValue.length() > (decPoint + 3))) {
              psaValue = psaValue.substring(0, decPoint + 3);
            }
            btxDetails.getClinicalFinding().setPsa(psaValue);
          }
        }
      }
      if (ApiFunctions.isEmpty(btxDetails.getClinicalFinding().getDre())) {
        btxDetails.addActionError(new BtxActionError("error.noValue", "Digital Rectal Exam"));
      }
      String notes = btxDetails.getClinicalFinding().getClinicalFindingNotes();
      if (!ApiFunctions.isEmpty(notes) && notes.length() > MAX_COMMENT_LENGTH) {
        btxDetails.addActionError(
          new BtxActionError("error.lengthExceeded", "Clinical Finding Comments", MAX_COMMENT_LENGTH + " characters"));
      }
    }
    return btxDetails;
  }

  private Map generateConsentToFindingMap(String ardaisId) throws Exception {
    Map theMap = new HashMap();
    ConsentAccessBean myConsent = new ConsentAccessBean();
    Enumeration myEnum = myConsent.findConsentByArdaisID(ardaisId);
    while (myEnum.hasMoreElements()) {
      myConsent = (ConsentAccessBean)myEnum.nextElement();
      theMap.put(((ConsentKey)myConsent.__getKey()).consent_id, getClinicalFinding(myConsent));
    }
    return theMap;
  }
  
  private ClinicalFindingData getClinicalFinding(ConsentAccessBean consent) throws Exception {
    //this method always returns a non-null ClinicalFindingData bean, so that the downstream
    //code (i.e. clinicalFinding.jsp) doesn't have to jump through hoops
    ClinicalFindingData clinicalFinding = new ClinicalFindingData();
    String psa = ApiFunctions.safeToString(consent.getPsa());
    String dre = consent.getDre_cid();
    String notes = consent.getClinical_finding_notes();
    if (!ApiFunctions.isEmpty(psa) || !ApiFunctions.isEmpty(dre) || !ApiFunctions.isEmpty(notes)) {
      clinicalFinding.setConsentId(((ConsentKey)consent.__getKey()).consent_id);
      clinicalFinding.setPsa(psa);
      clinicalFinding.setDre(dre);
      clinicalFinding.setClinicalFindingNotes(notes);
    }
    return clinicalFinding;
  }
  
  private boolean isClinicalFindingEmpty(ClinicalFindingData finding) {
    boolean retValue = false;
    if (finding == null) {
      retValue = true;
    }
    else {
      retValue = ApiFunctions.isEmpty(finding.getClinicalFindingNotes()) &&
          ApiFunctions.isEmpty(finding.getDre()) &&
          ApiFunctions.isEmpty(finding.getPsa());
    }
    return retValue;
  }

  /**
   * Invoke the specified method on this class.  This is only meant to be
   * called from BtxTransactionPerformerBase, please don't call it from anywhere
   * else as a mechanism to gain access to private methods in this class.  Every
   * object that the BTX framework dispatches to must contain this
   * method definition, and its implementation should be exactly the same
   * in each class.  Please don't alter this method or its implementation
   * in any way.
   */
  protected BTXDetails invokeBtxEntryPoint(java.lang.reflect.Method method, BTXDetails btxDetails)
    throws Exception {

    // **** DO NOT EDIT THIS METHOD, see the Javadoc comment above.
    return (BTXDetails) method.invoke(this, new Object[] { btxDetails });
  }

}
