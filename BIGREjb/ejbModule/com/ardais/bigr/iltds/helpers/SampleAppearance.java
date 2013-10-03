package com.ardais.bigr.iltds.helpers;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.lims.beans.LimsDataValidator;
import com.ardais.bigr.lims.beans.LimsDataValidatorHome;
import com.ardais.bigr.lims.btx.BTXDetailsMarkSampleDiscordant;
import com.ardais.bigr.lims.btx.BTXDetailsMarkSamplePulled;
import com.ardais.bigr.lims.helpers.LimsConstants;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.security.SecurityInfo;

/**
 *  This class is used to generate a sample, or microscopic, level appearance for a given 
 *  sample id.	 Appearance is calculated based on the values for diagnoses and microscopic 
 *  values that are set when calling the class.  There current business rules for defining 
 *  the microscopic appearance are encapsulated in the methods 
 *  <code>sampleAppearanceAcceptability</code> and <code>sampleAppearanceEval</code>.  The 
 *  decision was made to pass in the values used in the calculations when calling the class, 
 *  rather than calling the DB and retrieving the values (with the exception of looking up the 
 *  details for the donor institution diagnosis passed in)	   
 */

public class SampleAppearance {
  private String sampleId;
  private String diagDonorInst;
  private String diagPathVerification;
  private int tumor;
  private int lesion;
  private int normal;
  private int necrosis;
  private int tumor_acellular_stroma;
  private int tumor_cellular_stroma;
  private SecurityInfo _securityInfo;

  private String _sampleAppearance;
  private String _stateToSet;
  private int _discordantRule;
  private boolean _notToBeEvaluated = true;

  /**
  * The constructor.  All values are set to enable the calculation to occur with 
  * minimal database calls.
  *
  * @param sampleId  the barcode/blocklabel/sample id to be analyzed
  * @param diagDonorInst   diagnosis from the donor institution.  either ILTDS or DDC
  * @param diagPathVerification diagnosis specified during the PV process
  * @param tumor the percentage entered during the PV process
  * @param lesion the percentage entered during the PV process
  * @param normal the percentage entered during the PV process
  * @param necrosis the percentage entered during the PV process
  * @param tumor_acellular_stroma the percentage entered during the PV process
  * @param tumor_cellular_stroma the percentage entered during the PV process
  * @param userId the id of the user that should be recorded for updating the sample state, if applicable
  */
  public SampleAppearance(
    String sampleId,
    String diagDonorInst,
    String diagPathVerification,
    int tumor,
    int lesion,
    int normal,
    int necrosis,
    int tumor_acellular_stroma,
    int tumor_cellular_stroma,
    SecurityInfo securityInfo) {

    this.sampleId = sampleId;
    this.diagDonorInst = diagDonorInst;
    this.diagPathVerification = diagPathVerification;
    this.tumor = tumor;
    this.lesion = lesion;
    this.normal = normal;
    this.necrosis = necrosis;
    this.tumor_acellular_stroma = tumor_acellular_stroma;
    this.tumor_cellular_stroma = tumor_cellular_stroma;
    _securityInfo = securityInfo;

    // default sample appearance
    _sampleAppearance = FormLogic.SPEC_OTHER_IND;

    // default state is null, which means not to update a state...
    _stateToSet = null;
  }

  /**
  *  Method to compute the microscopic appearance for a sample.
  *
  * @return   the computed microscopic appearance
  */
  public String computeSampleAppearance() {
    return computeSampleAppearance(true);
  }

  /**
  *  Method to compute the the microscopic appearance for a sample.
  * 
  * @param setState   a boolean to indicate if this class should actually set the
  * 					 state of the sample in the database if it should be pulled
  * 					 or marked discordant based on the microscopic appearance value
  *
  * @return   the computed microscopic appearance
  */
  public String computeSampleAppearance(boolean setState) {

    // examine in 1st pass...
    _notToBeEvaluated = (sampleAppearanceAcceptability() != FormLogic.MICROSCOPIC_APPEARANCE_OK);

    // if "fails" 1st pass, do not use the 2nd set of rules...
    if (_notToBeEvaluated) {
      if (setState) {
        setState();
      }
    }
    else { // passed 1st pass, so move on to 2nd pass...
      sampleAppearanceEval();
    }

    return _sampleAppearance;

  }

  /**
   * Insert the method's description here.
   * Creation date: (8/20/2002 2:45:23 PM)
   * @return java.lang.String
   */
  public String getSampleAppearance() {
    return _sampleAppearance;
  }

  /**
   * Encapsulates First Pass Business Rules for Microscopic Appearance (MA)
   * <ul>
   * <li>(a) Any sample for which the Tumor, Lesion and Normal values total 0%,
   * set MA to TLN0 and assign STATE_SAMPLE_PULLED state</li>
   *
   * <li>(b) Any sample for which the Tumor value is greater than 0 for a Case 
   * Dx of "Non-neoplastic" or "NO DIAGNOSTIC ABNORMALITY", set MA to Tumor and 
   * assign STATE_SAMPLE_DISCORDANT state.</li>
   * </ul>
   *
   * <li>(c) Any sample with a Case Dx of "Non-neoplastic" and a PV Dx of 
   * "NEOPLASTIC", set MA to Tumor and assign STATE_SAMPLE_DISCORDANT state.</li>
   * </ul>
   *
   * @return int rule number if one of these rules applied; else 0 for OK
   *
   * 8/20/02 SAT changed business rules -- two rules instead of previous four
   * 4/10/03 JEE changed business rules -- three rules instead of two (MR4490)
   */
  public int sampleAppearanceAcceptability() {

    // rule (a)
    if ((tumor + lesion + normal) == 0) {
      _sampleAppearance = FormLogic.SPEC_TLN0_IND;
      _stateToSet = FormLogic.STATE_SAMPLE_PULLED;
      return FormLogic.MICROSCOPIC_APPEARANCE_RULE_1;
    }

    //because the next two rules rely on a donor institution diagnosis being available,
    //don't check them if that is not the case.  This could happen for imported samples,
    //since we don't require the imported cases to have a diagnosis specified.
    if (!ApiFunctions.isEmpty(diagDonorInst)) {
      
      // rule (b)
      if (tumor > 0
        && (BigrGbossData.isTopLevelDiagnosisNonNeoplastic(diagDonorInst)
          || BigrGbossData.isTopLevelDiagnosisNoAbnormality(diagDonorInst))) {
        _sampleAppearance = FormLogic.SPEC_TUMOR_IND;
        _stateToSet = FormLogic.STATE_SAMPLE_DISCORDANT;
        _discordantRule = FormLogic.MICROSCOPIC_APPEARANCE_RULE_2;
        return FormLogic.MICROSCOPIC_APPEARANCE_RULE_2;
      }
  
      // rule (c)
      if (BigrGbossData.isTopLevelDiagnosisNonNeoplastic(diagDonorInst)
        && BigrGbossData.isTopLevelDiagnosisNeoplastic(diagPathVerification)) {
        _sampleAppearance = FormLogic.SPEC_TUMOR_IND;
        _stateToSet = FormLogic.STATE_SAMPLE_DISCORDANT;
        _discordantRule = FormLogic.MICROSCOPIC_APPEARANCE_RULE_3;
        return FormLogic.MICROSCOPIC_APPEARANCE_RULE_3;
      }
      
    }

    // no matches here, so we want to do the second pass...
    return FormLogic.MICROSCOPIC_APPEARANCE_OK;

  } // end sampleAppearanceAcceptability ( )

  /**
  * Encapsulates Second Pass Business Rules for Microscopic Appearance (MA)
  * <ul>
  * <li>(a) if TUMOR > 0, MA = TUMOR</li>
  *
  * <li>(b) if TUMOR_ACELLULAR_STROMA = 0 and TUMOR_CELLULAR_STROMA = 0
  *	  and TUMOR = 0 and LESION > 0, MA = LESION</li>
  *
  * <li>(c) if TUMOR_ACELLULAR_STROMA = 0 and TUMOR_CELLULAR_STROMA = 0
  *	  and TUMOR = 0 and LESION = 0 and NORMAL > 0, MA = NORMAL</li>
  *
  * <li>ELSE MA = "OTHER"</li>
  * </ul>
  *
  *  8/20/02  SAT  changed business rules from six to four
  *                 removed the use of diagnosis as a criteria in this section
  *                 
  */
  private void sampleAppearanceEval() {

    _sampleAppearance = FormLogic.SPEC_OTHER_IND; // default is rule (d)

    if (tumor > 0) { // rule (a)
      _sampleAppearance = FormLogic.SPEC_TUMOR_IND;
    }

    if ((lesion > 0) // rule (b)
      && (tumor == 0)
      && (tumor_acellular_stroma == 0)
      && (tumor_cellular_stroma == 0)) {
      _sampleAppearance = FormLogic.SPEC_LESION_IND;
    }

    if ((normal > 0) // rule (c)
      && (tumor == 0)
      && (lesion == 0)
      && (tumor_acellular_stroma == 0)
      && (tumor_cellular_stroma == 0)) {
      _sampleAppearance = FormLogic.SPEC_NORMAL_IND;
    }
  } // end sampleAppearanceEval()

  /**
  *  Update the state of the sample.  Note that it is assumed that the <code>_stateToSet</code>
  *  was set in the business rules methods...
  */
  private void setState() {
    try {
      // do not update state if value is null...
      if (!(ApiFunctions.isEmpty(_stateToSet))) {
        // If the state is STATE_SAMPLE_PULLED, mark the sample pulled if it's not already pulled.
        if (FormLogic.STATE_SAMPLE_PULLED.equals(_stateToSet)) {
          boolean sampleIsPulled = false;
          try {
            LimsDataValidatorHome ldvHome = (LimsDataValidatorHome) EjbHomes.getHome(LimsDataValidatorHome.class);
            LimsDataValidator validator = ldvHome.create();
            sampleIsPulled = validator.isSamplePulled(sampleId);
          }
          catch (Exception e) {
            throw new ApiException(
              "Error calling LimsDataValidatorAccessBean.isSamplePulled from SampleAppearance.setState()",
              e);
          }
          if (!sampleIsPulled) {
            BTXDetailsMarkSamplePulled btxDetailsPull = new BTXDetailsMarkSamplePulled();
            btxDetailsPull.setReason(LimsConstants.AUTO_PULL_REASON);
            btxDetailsPull.setSampleId(sampleId);
            btxDetailsPull.setLoggedInUserSecurityInfo(_securityInfo, true);
            btxDetailsPull.setBeginTimestamp(
              new java.sql.Timestamp(new java.util.Date().getTime()));
            btxDetailsPull.setTransactionType("lims_mark_sample_pulled");
            btxDetailsPull = (BTXDetailsMarkSamplePulled) Btx.perform(btxDetailsPull);
            if (!btxDetailsPull.isTransactionCompleted()) {
              throw new ApiException("SampleAppearance was unable to mark the sample pulled.");
            }
          }
        }
        // If the state is STATE_SAMPLE_DISCORDANT, mark the sample discordant if it's not already discordant.
        else if (FormLogic.STATE_SAMPLE_DISCORDANT.equals(_stateToSet)) {
          boolean sampleIsDiscordant = false;
          try {
            LimsDataValidatorHome ldvHome = (LimsDataValidatorHome) EjbHomes.getHome(LimsDataValidatorHome.class);
            LimsDataValidator validator = ldvHome.create();
            sampleIsDiscordant = validator.isSampleDiscordant(sampleId);
          }
          catch (Exception e) {
            throw new ApiException(
              "Error calling LimsDataValidatorAccessBean.isSampleDiscordant from SampleAppearance.setState()",
              e);
          }
          if (!sampleIsDiscordant) {
            BTXDetailsMarkSampleDiscordant btxDetailsDiscordant =
              new BTXDetailsMarkSampleDiscordant();
            if (_discordantRule == FormLogic.MICROSCOPIC_APPEARANCE_RULE_2) {
              btxDetailsDiscordant.setReason(LimsConstants.AUTO_DISCORDANT_REASON1);
            }
            else {
              btxDetailsDiscordant.setReason(LimsConstants.AUTO_DISCORDANT_REASON2);
            }
            btxDetailsDiscordant.setSampleId(sampleId);
            btxDetailsDiscordant.setLoggedInUserSecurityInfo(_securityInfo, true);
            btxDetailsDiscordant.setBeginTimestamp(
              new java.sql.Timestamp(new java.util.Date().getTime()));
            btxDetailsDiscordant.setTransactionType("lims_mark_sample_discordant");
            btxDetailsDiscordant =
              (BTXDetailsMarkSampleDiscordant) Btx.perform(btxDetailsDiscordant);
            if (!btxDetailsDiscordant.isTransactionCompleted()) {
              throw new ApiException("SampleAppearance was unable to mark the sample discordant.");
            }
          }
        }
        else {
          throw new ApiException(
            "Unknown state to set encountered in SampleAppearance.setState() {"
              + _stateToSet
              + "}");
        }
      }

    }
    catch (Exception ex) {
      //System.err.println("SampleAppearance::setStatus " + ex.toString());
      // follow guidelines of handling exceptions...
      ApiFunctions.throwAsRuntimeException(ex);
    }
  }
}
