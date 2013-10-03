package com.ardais.bigr.iltds.performers;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.ObjectNotFoundException;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.api.ApiProperties;
import com.ardais.bigr.api.ApiResources;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.btx.framework.BtxTransactionPerformerBase;
import com.ardais.bigr.configuration.LabelPrintingConfiguration;
import com.ardais.bigr.es.beans.ArdaisaccountAccessBean;
import com.ardais.bigr.es.beans.ArdaisaccountKey;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.beans.ArdaisstaffAccessBean;
import com.ardais.bigr.iltds.beans.ArdaisstaffKey;
import com.ardais.bigr.iltds.beans.BoxlocationAccessBean;
import com.ardais.bigr.iltds.beans.BoxlocationKey;
import com.ardais.bigr.iltds.beans.ConsentAccessBean;
import com.ardais.bigr.iltds.beans.ConsentKey;
import com.ardais.bigr.iltds.beans.ConsentOperation;
import com.ardais.bigr.iltds.beans.ConsentOperationHome;
import com.ardais.bigr.iltds.beans.Geolocation;
import com.ardais.bigr.iltds.beans.ListGenerator;
import com.ardais.bigr.iltds.beans.ListGeneratorHome;
import com.ardais.bigr.iltds.beans.RevokedconsentAccessBean;
import com.ardais.bigr.iltds.beans.RevokedconsentKey;
import com.ardais.bigr.iltds.beans.SampleAccessBean;
import com.ardais.bigr.iltds.beans.SampleKey;
import com.ardais.bigr.iltds.btx.BTXBoxLocation;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.iltds.btx.BtxActionMessage;
import com.ardais.bigr.iltds.btx.BtxDetailsCreateCase;
import com.ardais.bigr.iltds.btx.BtxDetailsCreateImportedCase;
import com.ardais.bigr.iltds.btx.BtxDetailsCreateImportedCaseStart;
import com.ardais.bigr.iltds.btx.BtxDetailsGenerateCasePickList;
import com.ardais.bigr.iltds.btx.BtxDetailsGetCaseFormSummary;
import com.ardais.bigr.iltds.btx.BtxDetailsGetCaseRegistrationForm;
import com.ardais.bigr.iltds.btx.BtxDetailsImportedSample;
import com.ardais.bigr.iltds.btx.BtxDetailsModifyImportedCase;
import com.ardais.bigr.iltds.btx.BtxDetailsModifyImportedCaseStart;
import com.ardais.bigr.iltds.btx.BtxDetailsPullCase;
import com.ardais.bigr.iltds.btx.BtxDetailsPullCaseStart;
import com.ardais.bigr.iltds.btx.BtxDetailsRevokeCase;
import com.ardais.bigr.iltds.btx.BtxDetailsRevokeCaseStart;
import com.ardais.bigr.iltds.databeans.IrbVersionData;
import com.ardais.bigr.iltds.databeans.PolicyData;
import com.ardais.bigr.iltds.databeans.SampleTypeConfiguration;
import com.ardais.bigr.iltds.helpers.DateHelper;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.javabeans.ArdaisStaff;
import com.ardais.bigr.javabeans.ConsentData;
import com.ardais.bigr.javabeans.LocationData;
import com.ardais.bigr.javabeans.ManifestDto;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.kc.form.BigrFormInstance;
import com.ardais.bigr.kc.form.BtxDetailsKcFormInstanceDomainObjectSummary;
import com.ardais.bigr.kc.form.def.BigrFormDefinitionQueryCriteria;
import com.ardais.bigr.kc.form.def.BtxDetailsKcFormDefinitionLookup;
import com.ardais.bigr.kc.form.def.TagTypes;
import com.ardais.bigr.orm.beans.AccountSrchSB;
import com.ardais.bigr.orm.beans.AccountSrchSBHome;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.pdc.beans.DDCDonor;
import com.ardais.bigr.pdc.beans.DDCDonorHome;
import com.ardais.bigr.pdc.helpers.PdcUtils;
import com.ardais.bigr.pdc.javabeans.DonorData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.BoxLayoutUtils;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.PolicyUtils;
import com.gulfstreambio.bigr.Sample;
import com.gulfstreambio.bigr.error.BigrValidationError;
import com.gulfstreambio.bigr.error.BigrValidationErrors;
import com.gulfstreambio.bigr.labels.LabelService;
import com.gulfstreambio.gboss.GbossFactory;
import com.gulfstreambio.kc.form.FormInstance;
import com.gulfstreambio.kc.form.FormInstanceQueryCriteria;
import com.gulfstreambio.kc.form.FormInstanceService;
import com.gulfstreambio.kc.form.FormInstanceServiceResponse;
import com.gulfstreambio.kc.form.def.FormDefinitionService;
import com.gulfstreambio.kc.form.def.FormDefinitionServiceResponse;
import com.gulfstreambio.kc.form.def.FormDefinitionTypes;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionHostElement;
import com.ibm.ivj.ejb.runtime.AccessBeanEnumeration;


/**
 * This performs case-related ILTDS BTX business transactions.
 */
public class BtxPerformerCaseOperations extends BtxTransactionPerformerBase {

  private static int MAX_COMMENT_LENGTH = 4000;

  private static int MAX_PULL_COMMENT_LENGTH = 255;

  public BtxPerformerCaseOperations() {
    super();
  }

  /**
   * This is the main BTX entry point for performing the transaction that starts the process of
   * creating a new imported case.
   */
  private BTXDetails performCreateImportedCaseStart(BtxDetailsCreateImportedCaseStart btxDetails)
      throws Exception {
    btxDetails.setImportedYN(FormLogic.DB_YES);
    btxDetails.setArdaisAcctKey(btxDetails.getLoggedInUserSecurityInfo().getAccount());
    String acctKey = btxDetails.getArdaisAcctKey();
    ListGeneratorHome home = (ListGeneratorHome) EjbHomes.getHome(ListGeneratorHome.class);
    ListGenerator list = home.create();
    List consents = list.findActiveConsentVersions(acctKey, false, false);
    btxDetails.setConsentChoices(consents);
    btxDetails.setConsentPolicyIds(getConsentPolicyIds(consents));
    List policies = PolicyUtils.getPoliciesByAccountId(btxDetails.getArdaisAcctKey(), false, false,
        true);
    btxDetails.setPolicyChoices(policies);
    btxDetails.setCaseRegistrationFormIds(getCaseRegistrationFormIds(policies));

    // Get the form definitions for cases in the user's account.
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    Timestamp now = new Timestamp(System.currentTimeMillis());
    BtxDetailsKcFormDefinitionLookup btxDetailsLookup = new BtxDetailsKcFormDefinitionLookup();
    btxDetailsLookup.setBeginTimestamp(now);
    btxDetailsLookup.setLoggedInUserSecurityInfo(securityInfo);
    BigrFormDefinitionQueryCriteria criteria = new BigrFormDefinitionQueryCriteria();
    criteria.setEnabled(true);
    criteria.setAccount(securityInfo.getAccount());
    criteria.addFormType(FormDefinitionTypes.DATA);
    criteria.addObjectType(TagTypes.DOMAIN_OBJECT_VALUE_CASE);
    criteria.setUse(TagTypes.USES_VALUE_ANNOTATION);
    btxDetailsLookup.setQueryCriteria(criteria);
    btxDetailsLookup = (BtxDetailsKcFormDefinitionLookup) Btx.perform(btxDetailsLookup,
        "kc_form_defs_lookup");
    if (btxDetailsLookup.isTransactionCompleted()) {
      btxDetails.setFormDefinitions(btxDetailsLookup.getFormDefinitions());
    }
    else {
      //TODO MC: copy errors/messages to request attribute as in BigrAction.saveErrors
    }
    
    //populate the label printing information
    populateLabelPrintingData(btxDetails);

    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performCreateImportedCase performer method.
   */
  private BTXDetails validatePerformCreateImportedCase(BtxDetailsCreateImportedCase btxDetails)
      throws Exception {
    
    //populate the label printing information, in case there are errors returned
    populateLabelPrintingData(btxDetails);
    
    //MR7905
    btxDetails.setCustomerId(ApiFunctions.safeTrim(btxDetails.getCustomerId()));
    //customer id validation
    //make sure an id has been specified
    String customerId = btxDetails.getCustomerId();
    if (ApiFunctions.isEmpty(customerId)) {
      btxDetails.addActionError(new BtxActionError("dataImport.error.customerIdNotSpecified"));
    }
    //if an id has been specified, make sure there is no other case under this account with the
    //same id
    else {
      ConsentData consent = IltdsUtils.findConsentFromCustomerId(customerId, btxDetails
          .getLoggedInUserSecurityInfo().getAccount());
      if (consent != null) {
        btxDetails.addActionError(new BtxActionError(
            "dataImport.error.existingEntityWithCustomerId", "case"));
      }
    }

    //donor validation
    String ardaisId = btxDetails.getArdaisId();
    String ardaisId2 = btxDetails.getArdaisId2();
    String ardaisCustomerId = btxDetails.getArdaisCustomerId();
    String ardaisCustomerId2 = btxDetails.getArdaisCustomerId2();
    //make sure the user has specified either donor ids or customer ids
    if (ApiFunctions.isEmpty(ardaisId) && ApiFunctions.isEmpty(ardaisId2)
        && ApiFunctions.isEmpty(ardaisCustomerId) && ApiFunctions.isEmpty(ardaisCustomerId2)) {
      btxDetails.addActionError(new BtxActionError("dataImport.error.donorNotSpecified"));
    }
    //make sure the user has not specified both ardais ids and customer ids
    if ((!ApiFunctions.isEmpty(ardaisId) || !ApiFunctions.isEmpty(ardaisId2))
        && (!ApiFunctions.isEmpty(ardaisCustomerId) || !ApiFunctions.isEmpty(ardaisCustomerId2))) {
      btxDetails
          .addActionError(new BtxActionError("dataImport.error.cannotMixArdaisAndCustomerIds"));
    }
    //make sure that if ardais ids are specified they match
    if (!ApiFunctions.isEmpty(ardaisId)) {
      //if they match, make sure there is an existing imported donor with that id and
      //that the donor belongs to this users account
      DonorData donor = new DonorData();
      donor.setArdaisId(ardaisId);
      DDCDonorHome home = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
      DDCDonor donorOperation = home.create();
      DonorData donorData = donorOperation.getDonorProfile(donor);
      boolean donorImported = "Y".equalsIgnoreCase(donorData.getImportedYN());
      boolean inAccount = btxDetails.getLoggedInUserSecurityInfo().getAccount().equals(
          donorData.getArdaisAccountKey());
      if (!donorImported || !inAccount) {
        btxDetails.addActionError(new BtxActionError("dataImport.error.nonExistentEntity", "donor",
            "Ardais Id"));
      }
    }
    //make sure that if customer ids are specified they match
    if (!ApiFunctions.isEmpty(ardaisCustomerId)) {
      //if they match, make sure there is an existing imported donor with that id and
      //that the donor belongs to this users account
      boolean donorFound = false;
      String derivedArdaisId = PdcUtils.findDonorIdFromCustomerId(ardaisCustomerId, btxDetails
          .getLoggedInUserSecurityInfo().getAccount());
      if (!ApiFunctions.isEmpty(derivedArdaisId)) {
        DonorData donor = new DonorData();
        donor.setArdaisId(derivedArdaisId);
        DDCDonorHome home = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
        DDCDonor donorOperation = home.create();
        DonorData donorData = donorOperation.getDonorProfile(donor);
        boolean donorImported = "Y".equalsIgnoreCase(donorData.getImportedYN());
        boolean inAccount = btxDetails.getLoggedInUserSecurityInfo().getAccount().equals(
            donorData.getArdaisAccountKey());
        if (donorImported && inAccount) {
          donorFound = true;
        }
      }
      if (!donorFound) {
        btxDetails.addActionError(new BtxActionError("dataImport.error.nonExistentEntity", "donor",
            "alias"));
      }
    }

    //linked/unlinked validation
    String linked = btxDetails.getLinkedIndicator();
    if (ApiFunctions.isEmpty(linked)) {
      btxDetails.addActionError(new BtxActionError("dataImport.error.linkedNotSpecified"));
    }
    //if the linked/unlinked information is present, make sure required info baased on that info
    //is present
    else {
      if ("Y".equalsIgnoreCase(linked)) {
        String consentVersion = btxDetails.getConsentVersionId();
        if (ApiFunctions.isEmpty(consentVersion)) {
          btxDetails.addActionError(new BtxActionError(
              "dataImport.error.consentVersionNotSpecified"));
        }
        else {
          String acctKey = btxDetails.getArdaisAcctKey();
          ListGeneratorHome home = (ListGeneratorHome) EjbHomes.getHome(ListGeneratorHome.class);
          ListGenerator list = home.create();
          List validChoices = list.findActiveConsentVersions(acctKey, false, false);
          Iterator iterator = validChoices.iterator();
          boolean foundChoice = false;
          while (iterator.hasNext() && !foundChoice) {
            IrbVersionData irb = (IrbVersionData) iterator.next();
            foundChoice = consentVersion.equalsIgnoreCase(irb.getConsentVersionId());
          }
          if (!foundChoice) {
            btxDetails.addActionError(new BtxActionError(
                "iltds.error.createCase.invalidPolicyOrIRB", "IRB Protocol/Consent Version"));
          }
        }
        String myYear = btxDetails.getYear();
        String myMonth = btxDetails.getMonth();
        String myHours = btxDetails.getHours();
        String myMinutes = btxDetails.getMinutes();
        String myAmpm = btxDetails.getAmpm();
        if (!ApiFunctions.isEmpty(myYear) || !ApiFunctions.isEmpty(myMonth)
            || !ApiFunctions.isEmpty(myHours) || !ApiFunctions.isEmpty(myMinutes)
            || !ApiFunctions.isEmpty(myAmpm)) {
          validateConsentDateTime(btxDetails);
        }
      }
      if ("N".equalsIgnoreCase(linked)) {
        String consentPolicy = btxDetails.getPolicyId();
        if (ApiFunctions.isEmpty(consentPolicy)) {
          btxDetails
              .addActionError(new BtxActionError("dataImport.error.consentPolicyNotSpecified"));
        }
        else {
          List validChoices = PolicyUtils.getPoliciesByAccountId(btxDetails.getArdaisAcctKey(),
              false, false, true);
          Iterator iterator = validChoices.iterator();
          boolean foundChoice = false;
          while (iterator.hasNext() && !foundChoice) {
            PolicyData policy = (PolicyData) iterator.next();
            foundChoice = consentPolicy.equalsIgnoreCase(policy.getPolicyId());
          }
          if (!foundChoice) {
            btxDetails.addActionError(new BtxActionError(
                "iltds.error.createCase.invalidPolicyOrIRB", "policy"));
          }
        }
      }
    }

    //Make sure that if other was chosen for diagnosis, then an other value was specified
    String diagnosis = ApiFunctions.safeString(btxDetails.getDiagnosis());
    String diagnosisOther = ApiFunctions.safeString(btxDetails.getDiagnosisOther());
    if (diagnosis.equalsIgnoreCase(FormLogic.OTHER_DX) && ApiFunctions.isEmpty(diagnosisOther)) {
      btxDetails.addActionError(new BtxActionError("error.noOtherText", "Diagnosis"));
    }
    if (!diagnosis.equalsIgnoreCase(FormLogic.OTHER_DX) && !ApiFunctions.isEmpty(diagnosisOther)) {
      btxDetails.addActionError(new BtxActionError("error.otherTextSpecified", "Diagnosis"));
    }

    //comment validataion
    String comments = btxDetails.getComments();
    if (!ApiFunctions.isEmpty(comments) && comments.length() > MAX_COMMENT_LENGTH) {
      btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Comments",
          MAX_COMMENT_LENGTH + " characters"));
    }

    // Do some validation on the consent date.
    Date consentDate = btxDetails.getConsentDate();
    if (consentDate != null && consentDate.getTime() > System.currentTimeMillis()) {
      btxDetails.addActionError(new BtxActionError("dataImport.error.futureConsentDate"));
    }

    // Make sure that the policy has collectible sample types and an enabled registration form.
    String policyId = null;
    if (FormLogic.DB_YES.equalsIgnoreCase(btxDetails.getLinkedIndicator())) {
      String consentVersionId = btxDetails.getConsentVersionId();
      if (!ApiFunctions.isEmpty(consentVersionId)) {
        IrbVersionData irbData = IltdsUtils.getIrbVersionData(consentVersionId);
        policyId = irbData.getIrbPolicyId();
      }
    }
    else if (FormLogic.DB_NO.equalsIgnoreCase(btxDetails.getLinkedIndicator())) {
      policyId = btxDetails.getPolicyId();
    }
    if (!ApiFunctions.isEmpty(policyId)) {
      SampleTypeConfiguration sampleTypeConfiguration = PolicyUtils
          .getSampleTypeConfiguration(policyId);
      PolicyData policyData = PolicyUtils.getPolicyData(policyId);
      if (sampleTypeConfiguration.getSupportedSampleTypesAsLVS().getCount() == 0) {
        btxDetails.addActionError(new BtxActionError("dataImport.error.noCollectibleSampleTypes",
            policyData.getPolicyName()));
      }

      // Make sure the policy has an enabled case registration form. If it does, then also
      // validate the requiredness of the BIGR core elements on the form and call KC to
      // validate the form instance.
      String formId = policyData.getCaseRegistrationFormId();
      FormDefinitionServiceResponse response = FormDefinitionService.SINGLETON
          .findFormDefinitionById(formId);
      DataFormDefinition form = response.getDataFormDefinition();
      if ((form != null) && form.isEnabled()) {
        btxDetails.setRegistrationForm(form);
      }
      else {
        btxDetails.addActionError(new BtxActionError("dataImport.error.noCaseRegForm"));
      }

      validateCaseInformation(btxDetails);
      validateKcFormInstanceForCreate(btxDetails);
    }

    //make sure there is a primary location defined for the account of the user creating
    //the case (see MR8986)
    Geolocation staffGeolocation = null;
    String acctKey = null;
    try {
      ArdaisstaffAccessBean staff = new ArdaisstaffAccessBean(new ArdaisstaffKey(btxDetails
          .getUserId()));
      staffGeolocation = (Geolocation) staff.getGeolocation().getEJBRef();
      acctKey = staff.getArdais_acct_key();
    }
    catch (Exception e) {
      //no need to handle the exception, as we will return an action error if there was any
      //issue finding the location
    }
    if (ApiFunctions.isEmpty(acctKey)) {
      btxDetails
          .addActionError(new BtxActionError("dataImport.error.noLocationSpecifiedForAccount"));
    }
    
    //validate all information for printing has been provided.  Although printing is
    //handled outside of this class (in the ImportedCaseAction), we need to make sure 
    //that all printing information has been provided and is valid here. It would be 
    //annoying to the user to let them proceed with creating a case and then tell them 
    //they provided invalid printing information when it is too late for them to fix the problem.
    validateLabelPrintingData(btxDetails);

    if (!btxDetails.getActionErrors().empty()) {
      String ardaisAcctKey = btxDetails.getArdaisAcctKey();
      ListGeneratorHome home = (ListGeneratorHome) EjbHomes.getHome(ListGeneratorHome.class);
      ListGenerator list = home.create();
      List consents = list.findActiveConsentVersions(ardaisAcctKey, false, false);
      btxDetails.setConsentChoices(consents);
      btxDetails.setConsentPolicyIds(getConsentPolicyIds(consents));
      List policies = PolicyUtils.getPoliciesByAccountId(btxDetails.getArdaisAcctKey(), false,
          false, true);
      btxDetails.setPolicyChoices(policies);
      btxDetails.setCaseRegistrationFormIds(getCaseRegistrationFormIds(policies));

      // Get the form definitions for cases in the user's account.
      SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
      Timestamp now = new Timestamp(System.currentTimeMillis());
      BtxDetailsKcFormDefinitionLookup btxDetailsLookup = new BtxDetailsKcFormDefinitionLookup();
      btxDetailsLookup.setBeginTimestamp(now);
      btxDetailsLookup.setLoggedInUserSecurityInfo(securityInfo);
      BigrFormDefinitionQueryCriteria criteria = new BigrFormDefinitionQueryCriteria();
      criteria.setEnabled(true);
      criteria.setAccount(securityInfo.getAccount());
      criteria.addFormType(FormDefinitionTypes.DATA);
      criteria.addObjectType(TagTypes.DOMAIN_OBJECT_VALUE_CASE);
      criteria.setUse(TagTypes.USES_VALUE_ANNOTATION);
      btxDetailsLookup.setQueryCriteria(criteria);
      btxDetailsLookup = (BtxDetailsKcFormDefinitionLookup) Btx.perform(btxDetailsLookup,
          "kc_form_defs_lookup");
      if (btxDetailsLookup.isTransactionCompleted()) {
        btxDetails.setFormDefinitions(btxDetailsLookup.getFormDefinitions());
      }
      else {
        //TODO MC: copy errors/messages to request attribute as in BigrAction.saveErrors
      }

    }
    
    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that creates a new imported
   * case.
   */
  private BTXDetails performCreateImportedCase(BtxDetailsCreateImportedCase btxDetails)
      throws Exception {
    Timestamp myTimestamp = new Timestamp(System.currentTimeMillis());
    String entryUserId = btxDetails.getUserId();
    boolean isLinked = "Y".equalsIgnoreCase(btxDetails.getLinkedIndicator());
    String policyId; // input for unlinked, output for linked (obtained from consentVersionId)
    String consentVersionId = null; // used for linked cases only
    Date consentDate = null; // used for linked cases only
    IrbVersionData irb = null; // used for linked cases only
    String irbProtocolAndVersionName = null; // used for linked cases only

    // Gather data and set output fields.
    if (isLinked) {
      consentVersionId = btxDetails.getConsentVersionId();
      consentDate = filterConsentDate(btxDetails.getConsentDate());
      irb = IltdsUtils.getIrbVersionData(consentVersionId);
      policyId = irb.getIrbPolicyId();
      irbProtocolAndVersionName = irb.getIrbProtocolAndVersionName();

      // Set output fields
      btxDetails.setPolicyId(policyId);
      btxDetails.setIrbProtocolAndVersionName(irbProtocolAndVersionName);
      btxDetails.setConsentDate(consentDate); // set to the filtered consent date
    }
    else { // unlinked
      // Unlinked cases must have a policy id passed in since there's no IRB to get it from.
      policyId = btxDetails.getPolicyId();

      // Set output fields
      btxDetails.setIrbProtocolAndVersionName(null);
    }

    // Set output fields
    PolicyData policyData = PolicyUtils.getPolicyData(policyId);
    btxDetails.setPolicyName(policyData.getPolicyName());

    // Create the new case.

    // Get staffGeolocation
    Geolocation staffGeolocation = null;
    {
      ArdaisstaffAccessBean staff = new ArdaisstaffAccessBean(new ArdaisstaffKey(entryUserId));
      staffGeolocation = (Geolocation) staff.getGeolocation().getEJBRef();
    }
    String acctKey = btxDetails.getArdaisAcctKey();

    String ardaisId = btxDetails.getArdaisId();
    //if the user has not specified an Ardais donor id, determine the donor id from the
    //customer id (validation will have ensured that one or the other is available)
    if (ApiFunctions.isEmpty(ardaisId)) {
      ardaisId = PdcUtils.findDonorIdFromCustomerId(btxDetails.getArdaisCustomerId(), btxDetails
          .getLoggedInUserSecurityInfo().getAccount());
      btxDetails.setArdaisId(ardaisId);
    }

    //get the consent id
    String consentId = getIdForNewImportedCase();
    btxDetails.setConsentId(consentId);

    // Create the consent record
    {
      ConsentAccessBean myConsent = new ConsentAccessBean();
      myConsent.setInit_ardais_id(ardaisId);
      myConsent.setInit_consent_id(consentId);
      myConsent.setInit_policy_id(new BigDecimal(policyId));
      myConsent.setInit_ardais_acct_key(acctKey);
      myConsent.setInit_imported_yn(btxDetails.getImportedYN());
      myConsent.setInit_case_registration_form(policyData.getCaseRegistrationFormId());

      if (isLinked) {
        BigDecimal consentVersionKey = new BigDecimal(consentVersionId);
        Timestamp myConsentTimestamp = null;
        if (consentDate != null) {
          myConsentTimestamp = new Timestamp(consentDate.getTime());
        }
        myConsent.setConsent_datetime(myConsentTimestamp);
        myConsent.setConsent_version_id(consentVersionKey);
        myConsent.setConsent_version_num(irbProtocolAndVersionName);
      }

      myConsent.setForm_entry_datetime(myTimestamp);
      myConsent.setLinked((isLinked ? "Y" : "N"));
      //for imported cases the assumption is that the bankable_ind field should be
      //"Y" - otherwise why enter the case?
      myConsent.setBankable_ind("Y");
      myConsent.setArdais_staff_id(entryUserId);
      myConsent.setForm_entry_staff_id(entryUserId);
      myConsent.setGeolocation(staffGeolocation);
      myConsent.setComments(btxDetails.getComments());
      myConsent.setCustomer_id(btxDetails.getCustomerId());
      myConsent.setDisease_concept_id(btxDetails.getDiagnosis());
      myConsent.setDisease_concept_id_other(btxDetails.getDiagnosisOther());

      myConsent.commitCopyHelper();
    }
     
    
    // Create the KC form instance if there are any KC data elements.
    FormInstance formInstance = btxDetails.getRegistrationFormInstance();
    if (formInstance != null) {
      formInstance.setDomainObjectId(consentId);
      if (formInstance.getDataElements().length > 0) {
        FormInstanceServiceResponse response = FormInstanceService.SINGLETON
            .createFormInstance(formInstance);
        FormInstance kcFormInstance = response.getFormInstance();
        if (kcFormInstance != null) {
          btxDetails.setRegistrationFormInstance(kcFormInstance);
        }
      }
    }
    
    StringBuffer consentText = new StringBuffer(50);
    consentText.append(consentId);
    if (!ApiFunctions.isEmpty(btxDetails.getCustomerId())) {
      consentText.append(" (");
      consentText.append(btxDetails.getCustomerId());
      consentText.append(")");
    }
    
    //get the donor alias value for display on the resulting page, for storage in the history
    //record, and for storage in the session
    String donorAlias = btxDetails.getArdaisCustomerId();
    if (ApiFunctions.isEmpty(donorAlias)) {
      DonorData dd = new DonorData();
      dd.setArdaisId(btxDetails.getArdaisId());
      DDCDonorHome home = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
      DDCDonor donorOperation = home.create();
      dd = donorOperation.getDonorProfile(dd);
      donorAlias = dd.getCustomerId();
      btxDetails.setArdaisCustomerId(donorAlias);
    }
    
    StringBuffer donorText = new StringBuffer(50);
    donorText.append(ardaisId);
    if (!ApiFunctions.isEmpty(donorAlias)) {
      donorText.append(" (");
      donorText.append(donorAlias);
      donorText.append(")");
    }

    btxDetails.addActionMessage(new BtxActionMessage("dataImport.message.caseAction", 
        Escaper.htmlEscapeAndPreserveWhitespace(consentText.toString()),
        Escaper.htmlEscapeAndPreserveWhitespace(donorText.toString()), "created"));

    //if the user has indicated they wish to proceed to form creation for the new case
    //bring them there, otherwise return to the create case functionality.
    if (btxDetails.isCreateForm()) {
      btxDetails.setActionForwardTXCompleted("successWithCreateForm");
    }
    else {
      btxDetails.setActionForwardTXCompleted("success");
    }

    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performGetCaseFormSummary performer method.
   */
  private BTXDetails validatePerformGetCaseFormSummary(BtxDetailsGetCaseFormSummary btxDetails)
      throws Exception {
    String consentId = btxDetails.getConsentId();
    String customerId = btxDetails.getCustomerId();
    String account = btxDetails.getLoggedInUserSecurityInfo().getAccount();

    //make sure an id has been specified
    if (ApiFunctions.isEmpty(consentId) && ApiFunctions.isEmpty(customerId)) {
      btxDetails.addActionError(new BtxActionError("dataImport.error.caseNotSpecified"));
    }

    //make sure the user has not specified both a case id and customer id
    else if ((!ApiFunctions.isEmpty(consentId) && !ApiFunctions.isEmpty(customerId))) {
      btxDetails.addActionError(new BtxActionError("dataImport.error.cannotMixCaseAndCustomerId"));
    }

    //if a consent id has been specified, make sure it is an imported case, it exists, and it
    //belongs to the current users account. If it is read-only, then it can be either imported
    //or legacy and it does not have to belong to the current account.
    else if (!ApiFunctions.isEmpty(consentId)) {
      if (btxDetails.isReadOnly()) {
        String validatedId = (new ValidateIds())
            .validate(consentId, ValidateIds.TYPESET_CASE, true);
        if (validatedId == null) {
          btxDetails.addActionError(new BtxActionError("dataImport.error.nonExistentEntity",
              "case", "case id"));
        }
      }
      else {
        String validatedId = (new ValidateIds()).validate(consentId,
            ValidateIds.TYPESET_CASE_IMPORTED, true);
        if (!IltdsUtils.caseImportedByAccount(validatedId, account)) {
          btxDetails.addActionError(new BtxActionError("dataImport.error.nonExistentEntity",
              "case", "case id"));
        }
      }
    }

    //if a customer id has been specified, try to find the case from it
    else if (!ApiFunctions.isEmpty(customerId)) {
      ConsentData consent = IltdsUtils.findConsentFromCustomerId(customerId, account);
      if (consent == null) {
        btxDetails.addActionError(new BtxActionError("dataImport.error.nonExistentEntity", "case",
            "alias"));
      }
    }
    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that gets the summary
   * information for a case.
   */
  private BTXDetails performGetCaseFormSummary(BtxDetailsGetCaseFormSummary btxDetails)
      throws Exception {

	  if (!btxDetails.getLoggedInUserSecurityInfo().isHasPrivilege(SecurityInfo.PRIV_ORM_ACCESS_CASE_VIEW))
	  {
		  btxDetails.addActionError(new BtxActionError("error.noPrivilege", "view case information"));
		  btxDetails.setActionForwardTXIncomplete("error");
		  return btxDetails;
	  }

    String consentId = btxDetails.getConsentId();
    String customerId = btxDetails.getCustomerId();

    //if a customer id was specified, get the consent id from it
    if (!ApiFunctions.isEmpty(customerId)) {
      consentId = IltdsUtils.findConsentFromCustomerId(customerId,
          btxDetails.getLoggedInUserSecurityInfo().getAccount()).getConsentId();
    }
    //otherwise use a validated version of the consent id
    else {
      if (btxDetails.isReadOnly()) {
        consentId = (new ValidateIds()).validate(consentId, ValidateIds.TYPESET_CASE, true);
      }
      else {
        consentId = (new ValidateIds())
            .validate(consentId, ValidateIds.TYPESET_CASE_IMPORTED, true);
      }
    }

    //get the consent information and populate the btxDetails object
    ConsentAccessBean consent = new ConsentAccessBean(new ConsentKey(consentId));
    btxDetails.setArdaisAcctKey(consent.getArdais_acct_key());
    btxDetails.setImportedYN(consent.getImported_yn());
    btxDetails.setCustomerId(consent.getCustomer_id());
    btxDetails.setDiagnosis(consent.getDisease_concept_id());
    btxDetails.setDiagnosisOther(consent.getDisease_concept_id_other());
    btxDetails.setLinkedIndicator(consent.getLinked());
    btxDetails.setArdaisId(consent.getArdais_id());
    btxDetails.setConsentId(consentId);
    btxDetails.setPolicyId((new Integer(consent.getPolicy_id().intValue())).toString());
    btxDetails.setPolicyName(IltdsUtils.getPolicyForConsent(consentId).getPolicyName());
    if ("Y".equalsIgnoreCase(consent.getLinked())) {
      btxDetails.setConsentVersionId((new Integer(consent.getConsent_version_id().intValue()))
          .toString());
      btxDetails.setIrbProtocolAndVersionName(IltdsUtils.getIrbVersionData(
          btxDetails.getConsentVersionId()).getIrbProtocolAndVersionName());
      btxDetails.setConsentDate(consent.getConsent_datetime());
    }
    btxDetails.setComments(consent.getComments());

    //get the donor lias value for display on the resulting page
    DonorData dd = new DonorData();
    dd.setArdaisId(btxDetails.getArdaisId());
    DDCDonorHome home = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
    DDCDonor donorOperation = home.create();
    dd = donorOperation.getDonorProfile(dd);
    btxDetails.setArdaisCustomerId(dd.getCustomerId());

    // Get the existing form instances for the donor.
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    Timestamp now = new Timestamp(System.currentTimeMillis());
    BigrFormInstance form = new BigrFormInstance();
    form.setDomainObjectId(consentId);
    BtxDetailsKcFormInstanceDomainObjectSummary btxDetailsDos = new BtxDetailsKcFormInstanceDomainObjectSummary();
    btxDetailsDos.setBeginTimestamp(now);
    btxDetailsDos.setLoggedInUserSecurityInfo(securityInfo);
    btxDetailsDos.setFormInstance(form);
    btxDetailsDos = (BtxDetailsKcFormInstanceDomainObjectSummary) Btx.perform(btxDetailsDos,
        "kc_form_inst_domain_object_summary");
    if (btxDetailsDos.isTransactionCompleted()) {
      btxDetails.setFormInstances(btxDetailsDos.getFormInstances());
    }
    else {
      //TODO MC: copy errors/messages to request attribute as in BigrAction.saveErrors
    }

    // Get the form definitions for cases in the user's account.
    BtxDetailsKcFormDefinitionLookup btxDetailsLookup = new BtxDetailsKcFormDefinitionLookup();
    btxDetailsLookup.setBeginTimestamp(now);
    btxDetailsLookup.setLoggedInUserSecurityInfo(securityInfo);
    BigrFormDefinitionQueryCriteria criteria = new BigrFormDefinitionQueryCriteria();
    criteria.setEnabled(true);
    criteria.setAccount(securityInfo.getAccount());
    criteria.addFormType(FormDefinitionTypes.DATA);
    criteria.addObjectType(TagTypes.DOMAIN_OBJECT_VALUE_CASE);
    criteria.setUse(TagTypes.USES_VALUE_ANNOTATION);
    btxDetailsLookup.setQueryCriteria(criteria);
    btxDetailsLookup = (BtxDetailsKcFormDefinitionLookup) Btx.perform(btxDetailsLookup,
        "kc_form_defs_lookup");
    if (btxDetailsLookup.isTransactionCompleted()) {
      btxDetails.setFormDefinitions(btxDetailsLookup.getFormDefinitions());
    }
    else {
      //TODO MC: copy errors/messages to request attribute as in BigrAction.saveErrors
    }

    //if the case has been pulled, return a message to the user letting them know that
    if (consent.getConsent_pull_datetime() != null) {
      btxDetails.addActionMessage(new BtxActionMessage("dataImport.message.modifyingPulledCase",
          consentId));
    }

    //return the information
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performModifyImportedCaseStart performer method.
   */
  private BTXDetails validatePerformModifyImportedCaseStart(
                                                            BtxDetailsModifyImportedCaseStart btxDetails)
      throws Exception {
    //make sure an id has been specified
    String consentId = btxDetails.getConsentId();
    String customerId = btxDetails.getCustomerId();
    if (ApiFunctions.isEmpty(consentId) && ApiFunctions.isEmpty(customerId)) {
      btxDetails.addActionError(new BtxActionError("dataImport.error.caseNotSpecified"));
    }
    //make sure the user has not specified both a case id and customer id
    if ((!ApiFunctions.isEmpty(consentId) && !ApiFunctions.isEmpty(customerId))) {
      btxDetails.addActionError(new BtxActionError("dataImport.error.cannotMixCaseAndCustomerId"));
    }
    //otherwise make sure the case exists
    else {
      String account = btxDetails.getLoggedInUserSecurityInfo().getAccount();
      //if a consent id has been specified, make sure it is an imported case, it exists, and it
      //belongs to the current users account
      if (!ApiFunctions.isEmpty(consentId)) {
        String validatedId = (new ValidateIds()).validate(consentId,
            ValidateIds.TYPESET_CASE_IMPORTED, true);
        if (!IltdsUtils.caseImportedByAccount(validatedId, account)) {
          btxDetails.addActionError(new BtxActionError("dataImport.error.nonExistentEntity",
              "case", "case id"));
        }
      }
      //if a customer id has been specified, try to find the case from it
      else if (!ApiFunctions.isEmpty(customerId)) {
        ConsentData consent = IltdsUtils.findConsentFromCustomerId(customerId, account);
        if (consent == null) {
          btxDetails.addActionError(new BtxActionError("dataImport.error.nonExistentEntity",
              "case", "alias"));
        }
      }
    }
    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that starts the process of
   * modifying a new imported case.
   */
  private BTXDetails performModifyImportedCaseStart(BtxDetailsModifyImportedCaseStart btxDetails)
      throws Exception {
    String consentId = btxDetails.getConsentId();
    String customerId = btxDetails.getCustomerId();

    //if a customer id was specified, get the consent id from it
    if (!ApiFunctions.isEmpty(customerId)) {
      consentId = IltdsUtils.findConsentFromCustomerId(customerId,
          btxDetails.getLoggedInUserSecurityInfo().getAccount()).getConsentId();
    }
    //otherwise use a validated version of the consent id
    else {
      consentId = (new ValidateIds()).validate(consentId, ValidateIds.TYPESET_CASE_IMPORTED, true);
    }

    //get the consent information and populate the btxDetails object
    ConsentAccessBean consent = new ConsentAccessBean(new ConsentKey(consentId));
    btxDetails.setArdaisAcctKey(consent.getArdais_acct_key());
    btxDetails.setImportedYN(consent.getImported_yn());
    btxDetails.setCustomerId(consent.getCustomer_id());
    btxDetails.setDiagnosis(consent.getDisease_concept_id());
    btxDetails.setDiagnosisOther(consent.getDisease_concept_id_other());
    btxDetails.setLinkedIndicator(consent.getLinked());
    btxDetails.setArdaisId(consent.getArdais_id());
    btxDetails.setConsentId(consentId);
    btxDetails.setPolicyId((new Integer(consent.getPolicy_id().intValue())).toString());
    btxDetails.setPolicyName(IltdsUtils.getPolicyForConsent(consentId).getPolicyName());
    if ("Y".equalsIgnoreCase(consent.getLinked())) {
      btxDetails.setConsentVersionId((new Integer(consent.getConsent_version_id().intValue()))
          .toString());
      btxDetails.setIrbProtocolAndVersionName(IltdsUtils.getIrbVersionData(
          btxDetails.getConsentVersionId()).getIrbProtocolAndVersionName());
      btxDetails.setConsentDate(consent.getConsent_datetime());
    }
    btxDetails.setComments(consent.getComments());

    //get the donor alias value for display on the resulting page
    DonorData dd = new DonorData();
    dd.setArdaisId(btxDetails.getArdaisId());
    DDCDonorHome home = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
    DDCDonor donorOperation = home.create();
    dd = donorOperation.getDonorProfile(dd);
    btxDetails.setArdaisCustomerId(dd.getCustomerId());

    //if the case has been pulled, return a message to the user letting them know that
    if (consent.getConsent_pull_datetime() != null) {
      btxDetails.addActionMessage(new BtxActionMessage("dataImport.message.modifyingPulledCase",
          consentId));
    }

    // Get the registration form definition.
    FormDefinitionServiceResponse response1 = FormDefinitionService.SINGLETON
        .findFormDefinitionById(consent.getCase_registration_form());
    btxDetails.setRegistrationForm(response1.getDataFormDefinition());

    // Get the form instance, if any.
    FormInstanceQueryCriteria criteria = new FormInstanceQueryCriteria();
    criteria.addDomainObjectId(consentId);
    criteria.addFormDefinitionId(consent.getCase_registration_form());
    FormInstanceServiceResponse response = FormInstanceService.SINGLETON
        .findFormInstances(criteria);
    FormInstance form = response.getFormInstance();
    if (form != null) {
      response = FormInstanceService.SINGLETON.findFormInstanceById(form.getFormInstanceId());
      form = response.getFormInstance();
      if (form != null) {
        btxDetails.setRegistrationFormInstance(form);
      }
    }
    
    //populate the label printing information
    populateLabelPrintingData(btxDetails);

    //return the information
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performModifyImportedCase performer method.
   */
  private BTXDetails validatePerformModifyImportedCase(BtxDetailsModifyImportedCase btxDetails)
      throws Exception {
    
    //populate the label printing information, in case there are errors returned
    populateLabelPrintingData(btxDetails);

    //make sure a valid case has been specified
    String account = btxDetails.getLoggedInUserSecurityInfo().getAccount();
    String validatedId = (new ValidateIds()).validate(btxDetails.getConsentId(),
        ValidateIds.TYPESET_CASE_IMPORTED, true);
    if (!IltdsUtils.caseImportedByAccount(validatedId, account)) {
      btxDetails.addActionError(new BtxActionError("dataImport.error.nonExistentEntity", "case",
          "case id"));
    }

    //MR7905
    btxDetails.setCustomerId(ApiFunctions.safeTrim(btxDetails.getCustomerId()));
    //customer id validation
    //make sure an id has been specified
    String customerId = btxDetails.getCustomerId();
    ConsentData consent = null;
    if (ApiFunctions.isEmpty(customerId)) {
      btxDetails.addActionError(new BtxActionError("dataImport.error.customerIdNotSpecified"));
    }
    //if an id has been specified, make sure there is no other case under this account with the
    //same id
    else {
      consent = IltdsUtils.findConsentFromCustomerId(customerId, account);
      if (consent != null && !consent.getConsentId().equalsIgnoreCase(validatedId)) {
        btxDetails.addActionError(new BtxActionError(
            "dataImport.error.existingEntityWithCustomerId", "case"));
      }
    }

    //Make sure that if other was chosen for diagnosis, then an other value was specified
    String diagnosis = ApiFunctions.safeString(btxDetails.getDiagnosis());
    String diagnosisOther = ApiFunctions.safeString(btxDetails.getDiagnosisOther());
    if (diagnosis.equalsIgnoreCase(FormLogic.OTHER_DX) && ApiFunctions.isEmpty(diagnosisOther)) {
      btxDetails.addActionError(new BtxActionError("error.noOtherText", "Diagnosis"));
    }
    if (!diagnosis.equalsIgnoreCase(FormLogic.OTHER_DX) && !ApiFunctions.isEmpty(diagnosisOther)) {
      btxDetails.addActionError(new BtxActionError("error.otherTextSpecified", "Diagnosis"));
    }

    //comment validation
    String comments = btxDetails.getComments();
    if (!ApiFunctions.isEmpty(comments) && comments.length() > MAX_COMMENT_LENGTH) {
      btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Comments",
          MAX_COMMENT_LENGTH + " characters"));
    }

    // Do some validation on the consent date.
    String linked = btxDetails.getLinkedIndicator();
    if ("Y".equalsIgnoreCase(linked)) {
      String myYear = btxDetails.getYear();
      String myMonth = btxDetails.getMonth();
      String myHours = btxDetails.getHours();
      String myMinutes = btxDetails.getMinutes();
      String myAmpm = btxDetails.getAmpm();
      if (!ApiFunctions.isEmpty(myYear) || !ApiFunctions.isEmpty(myMonth)
          || !ApiFunctions.isEmpty(myHours) || !ApiFunctions.isEmpty(myMinutes)
          || !ApiFunctions.isEmpty(myAmpm)) {
        validateConsentDateTime(btxDetails);
      }
    }

    Date consentDate = btxDetails.getConsentDate();
    if (consentDate != null && consentDate.getTime() > System.currentTimeMillis()) {
      btxDetails.addActionError(new BtxActionError("dataImport.error.futureConsentDate"));
    }

    if (consent != null) {
      // Set so this is returned in BTX details and copied to form to be displayed.
      if ("Y".equalsIgnoreCase(consent.getLinked())) {
        btxDetails.setIrbProtocolAndVersionName(IltdsUtils.getIrbVersionData(
            btxDetails.getConsentVersionId()).getIrbProtocolAndVersionName());
      }
      PolicyData policyData = IltdsUtils.getPolicyForConsent(consent.getConsentId());
      btxDetails.setPolicyId(policyData.getPolicyId());
      btxDetails.setPolicyName(policyData.getPolicyName());

      FormDefinitionServiceResponse response = FormDefinitionService.SINGLETON
          .findFormDefinitionById(consent.getCaseRegistrationFormId());
      DataFormDefinition form = response.getDataFormDefinition();
      if (form != null) {
        btxDetails.setRegistrationForm(form);

        validateCaseInformation(btxDetails);

        FormInstanceQueryCriteria criteria = new FormInstanceQueryCriteria();
        criteria.addDomainObjectId(btxDetails.getConsentId());
        criteria.addFormDefinitionId(consent.getCaseRegistrationFormId());
        FormInstanceServiceResponse response1 = FormInstanceService.SINGLETON
            .findFormInstances(criteria);
        FormInstance form1 = response1.getFormInstance();
        if (form1 != null) {
          btxDetails.getRegistrationFormInstance().setFormInstanceId(form1.getFormInstanceId());
          validateKcFormInstanceForModify(btxDetails);
        }
        else {
          validateKcFormInstanceForCreate(btxDetails);
        }
      }
      else {
        btxDetails.addActionError(new BtxActionError("dataImport.error.noCaseRegForm"));
      }

    }
    
    //validate all information for printing has been provided.  Although printing is
    //handled outside of this class (in the ImportedCaseAction), we need to make sure 
    //that all printing information has been provided and is valid here. It would be 
    //annoying to the user to let them proceed with creating a case and then tell them 
    //they provided invalid printing information when it is too late for them to fix the problem.
    validateLabelPrintingData(btxDetails);

    //retrieve any information used on the display but not passed back in from the original form
    //SWP-241
    String consentId = btxDetails.getConsentId();
    ConsentAccessBean oldConsent = new ConsentAccessBean(new ConsentKey(consentId));
    btxDetails.setPolicyName(IltdsUtils.getPolicyForConsent(consentId).getPolicyName());
    if ("Y".equalsIgnoreCase(oldConsent.getLinked())) {
      String consentVersionId = (new Integer(oldConsent.getConsent_version_id().intValue())).toString();
      btxDetails.setIrbProtocolAndVersionName(IltdsUtils.getIrbVersionData(consentVersionId).getIrbProtocolAndVersionName());
    }

    // Get the registration form definition.
    //SWP-241
    FormDefinitionServiceResponse response1 = FormDefinitionService.SINGLETON
        .findFormDefinitionById(oldConsent.getCase_registration_form());
    btxDetails.setRegistrationForm(response1.getDataFormDefinition());
      
    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that modifies an imported case.
   */
  private BTXDetails performModifyImportedCase(BtxDetailsModifyImportedCase btxDetails)
      throws Exception {
    boolean isLinked = "Y".equalsIgnoreCase(btxDetails.getLinkedIndicator());
    Date consentDate = null; // used for linked cases only

    // Gather data and set output fields.
    if (isLinked) {
      consentDate = filterConsentDate(btxDetails.getConsentDate());
      // Set output fields
      btxDetails.setConsentDate(consentDate); // set to the filtered consent date
    }

    // Modify the case.
    ConsentAccessBean myConsent = new ConsentAccessBean(new ConsentKey(btxDetails.getConsentId()));

    if (isLinked) {
      Timestamp myConsentTimestamp = null;
      if (consentDate != null) {
        myConsentTimestamp = new Timestamp(consentDate.getTime());
      }
      else {
        myConsentTimestamp = null;
      }
      myConsent.setConsent_datetime(myConsentTimestamp);
    }
    myConsent.setComments(btxDetails.getComments());
    myConsent.setCustomer_id(btxDetails.getCustomerId());
    myConsent.setDisease_concept_id(btxDetails.getDiagnosis());
    myConsent.setDisease_concept_id_other(btxDetails.getDiagnosisOther());
    myConsent.commitCopyHelper();

    // Either create a new registration form instance or modify the existing one.
    FormInstance formInstance = btxDetails.getRegistrationFormInstance();
    if (formInstance != null) {
      formInstance.setDomainObjectId(btxDetails.getConsentId());
      if (formInstance.getDataElements().length > 0) {
        FormInstanceQueryCriteria criteria = new FormInstanceQueryCriteria();
        criteria.addDomainObjectId(btxDetails.getConsentId());
        criteria.addFormDefinitionId(myConsent.getCase_registration_form());
        FormInstanceServiceResponse response = FormInstanceService.SINGLETON
            .findFormInstances(criteria);
        FormInstance form = response.getFormInstance();
        if (form != null) {
          response = FormInstanceService.SINGLETON.updateFormInstance(formInstance);
        }
        else {
          response = FormInstanceService.SINGLETON.createFormInstance(formInstance);
        }
        FormInstance kcFormInstance = response.getFormInstance();
        if (kcFormInstance != null) {
          btxDetails.setRegistrationFormInstance(kcFormInstance);
        }
      }
    }
    
    StringBuffer consentText = new StringBuffer(50);
    consentText.append(btxDetails.getConsentId());
    if (!ApiFunctions.isEmpty(btxDetails.getCustomerId())) {
      consentText.append(" (");
      consentText.append(btxDetails.getCustomerId());
      consentText.append(")");
    }
    
    //get the donor alias value for display on the resulting page, for storage in the history
    //record, and for storage in the session
    String donorAlias = btxDetails.getArdaisCustomerId();
    if (ApiFunctions.isEmpty(donorAlias)) {
      DonorData dd = new DonorData();
      dd.setArdaisId(btxDetails.getArdaisId());
      DDCDonorHome home = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
      DDCDonor donorOperation = home.create();
      dd = donorOperation.getDonorProfile(dd);
      donorAlias = dd.getCustomerId();
      btxDetails.setArdaisCustomerId(donorAlias);
    }
    
    StringBuffer donorText = new StringBuffer(50);
    donorText.append(btxDetails.getArdaisId());
    if (!ApiFunctions.isEmpty(donorAlias)) {
      donorText.append(" (");
      donorText.append(donorAlias);
      donorText.append(")");
    }

    btxDetails.addActionMessage(new BtxActionMessage("dataImport.message.caseAction", 
        Escaper.htmlEscapeAndPreserveWhitespace(consentText.toString()), 
        Escaper.htmlEscapeAndPreserveWhitespace(donorText.toString()), "modified"));
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performRevokeCaseStart performer method.
   */
  private BTXDetails validatePerformRevokeCaseStart(BtxDetailsRevokeCaseStart btxDetails)
      throws Exception {
    //make sure an id has been specified
    String consentId = btxDetails.getConsentId();
    String consentId2 = btxDetails.getConsentId2();
    String txType = btxDetails.getTxType();
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();

    String account = btxDetails.getLoggedInUserSecurityInfo().getAccount();

    // make sure that the user has specified both ids
    if (ApiFunctions.isEmpty(consentId) || ApiFunctions.isEmpty(consentId2)) {
      btxDetails.addActionError(new BtxActionError("iltds.error.general.invalidCaseId"));
    }
    if (!consentId.equals(consentId2)) {
      btxDetails.addActionError(new BtxActionError("general.error.valuesDontMatch", "Case Ids"));
    }

    String validatedId = (new ValidateIds()).validate(consentId, ValidateIds.TYPESET_CASE_LINKED,
        false);
    // check to see if the case exists and belongs to the user
    if (!IltdsUtils.caseExists(validatedId)
        || (!(securityInfo.isInRoleSystemOwner()) && !account.equals(IltdsUtils
            .getAccountForCase(validatedId)))) {
      btxDetails.addActionError(new BtxActionError("dataImport.error.nonExistentEntity", "case",
          "case id"));
    }

    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that starts the process of
   * pulling a case.
   */
  private BTXDetails performRevokeCaseStart(BtxDetailsRevokeCaseStart btxDetails) throws Exception {

    String consentId = btxDetails.getConsentId();
    String consentId2 = btxDetails.getConsentId2();

    String account = btxDetails.getLoggedInUserSecurityInfo().getAccount();

    consentId = (new ValidateIds()).validate(consentId, ValidateIds.TYPESET_CASE_WITHOUT_IMPORTED,
        true);
    consentId2 = (new ValidateIds()).validate(consentId2,
        ValidateIds.TYPESET_CASE_WITHOUT_IMPORTED, true);

    btxDetails.setConsentId(consentId);
    btxDetails.setConsentId2(consentId2);

    if (IltdsUtils.consentRevoked(btxDetails.getConsentId())) {
      ConsentAccessBean consent = new ConsentAccessBean();
      AccessBeanEnumeration consentEnum = (AccessBeanEnumeration) consent
          .findByConsentID(btxDetails.getConsentId());
      consent = (ConsentAccessBean) consentEnum.nextElement();
      ConsentKey key = (ConsentKey) consent.__getKey();
      RevokedconsentAccessBean consentRevoked = new RevokedconsentAccessBean(new RevokedconsentKey(
          btxDetails.getConsentId(), consent.getArdais_id()));
      DateHelper dateHelper = new DateHelper(consentRevoked.getRevoked_timestamp());
      ArdaisstaffAccessBean staff = new ArdaisstaffAccessBean(new ArdaisstaffKey(consentRevoked
          .getRevoked_by_staff_id()));
      String revokeRequestedBy = staff.getArdais_staff_fname() + " "
          + staff.getArdais_staff_lname();
      String revokeDate = dateHelper.getFormattedDate();
      btxDetails.addActionMessage(new BtxActionMessage("iltds.error.consent.caseAlreadyRevoked",
          btxDetails.getConsentId(), revokeDate, revokeRequestedBy));
      btxDetails.setActionForwardTXCompleted("caseAlreadyRevoked");
    }
    else {
      LegalValueSet lvs = new LegalValueSet();
      String user = btxDetails.getLoggedInUserSecurityInfo().getUsername();
      List staffList = IltdsUtils.getArdaisStaffInAccount(user, account);
      Iterator iterator = staffList.iterator();
      while (iterator.hasNext()) {
        ArdaisStaff staff = (ArdaisStaff) iterator.next();
        lvs.addLegalValue(staff.getStaffId(), staff.getFullName());
      }
      btxDetails.setPullRequestedByChoices(lvs);
      btxDetails.setActionForwardTXCompleted("success");
    }

    //return the information
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performPullCaseStart performer method.
   */
  private BTXDetails validatePerformPullCaseStart(BtxDetailsPullCaseStart btxDetails)
      throws Exception {
    //make sure an id has been specified
    String consentId = btxDetails.getConsentId();
    String consentId2 = btxDetails.getConsentId2();
    String customerId = btxDetails.getCustomerId();
    String txType = btxDetails.getTxType();

    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    String account = securityInfo.getAccount();
    if (txType.equalsIgnoreCase(Constants.TRANSACTION_SR)) {
      if (ApiFunctions.isEmpty(consentId) && ApiFunctions.isEmpty(customerId)) {
        btxDetails.addActionError(new BtxActionError("dataImport.error.caseNotSpecified"));
      }
      //make sure the user has not specified both a case id and customer id
      if ((!ApiFunctions.isEmpty(consentId) && !ApiFunctions.isEmpty(customerId))) {
        btxDetails
            .addActionError(new BtxActionError("dataImport.error.cannotMixCaseAndCustomerId"));
      }
      //otherwise make sure the case exists
      else {
        //if a consent id has been specified, make sure it is an imported case, it exists, and it
        //belongs to the current users account
        if (!ApiFunctions.isEmpty(consentId)) {
          String validatedId = (new ValidateIds()).validate(consentId,
              ValidateIds.TYPESET_CASE_IMPORTED, true);
          if (!IltdsUtils.caseImportedByAccount(validatedId, account)) {
            btxDetails.addActionError(new BtxActionError("dataImport.error.nonExistentEntity",
                "case", "case id"));
          }
        }
        //if a customer id has been specified, try to find the case from it
        else if (!ApiFunctions.isEmpty(customerId)) {
          ConsentData consent = IltdsUtils.findConsentFromCustomerId(customerId, account);
          if (consent == null) {
            btxDetails.addActionError(new BtxActionError("dataImport.error.nonExistentEntity",
                "case", "alias"));
          }
        }
      }
    }
    else if (txType.equalsIgnoreCase(Constants.TRANSACTION_ILTDS)) {
      // make sure that the user has specified both ids
      if (ApiFunctions.isEmpty(consentId) || ApiFunctions.isEmpty(consentId2)) {
        btxDetails.addActionError(new BtxActionError("iltds.error.general.invalidCaseId"));
      }
      if (!consentId.equals(consentId2)) {
        btxDetails.addActionError(new BtxActionError("general.error.valuesDontMatch", "Case Ids"));
      }
      String validatedId = (new ValidateIds()).validate(consentId,
          ValidateIds.TYPESET_CASE_WITHOUT_IMPORTED, true);
      //make sure the case exists, and that the case belongs to the user if the user is
      //not the system owner
      if (!IltdsUtils.caseExists(validatedId)
          || (!(securityInfo.isInRoleSystemOwner()) && !account.equals(IltdsUtils
              .getAccountForCase(validatedId)))) {
        btxDetails.addActionError(new BtxActionError("dataImport.error.nonExistentEntity", "case",
            "case id"));
      }
    }
    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that starts the process of
   * pulling a case.
   */
  private BTXDetails performPullCaseStart(BtxDetailsPullCaseStart btxDetails) throws Exception {

    String consentId = btxDetails.getConsentId();
    String consentId2 = btxDetails.getConsentId2();
    String customerId = btxDetails.getCustomerId();
    String account = btxDetails.getLoggedInUserSecurityInfo().getAccount();

    //if a customer id was specified, get the consent id from it
    if (!ApiFunctions.isEmpty(customerId)) {
      consentId = IltdsUtils.findConsentFromCustomerId(customerId, account).getConsentId();
    }
    //otherwise use a validated version of the consent id
    else {
      if (btxDetails.getTxType().equals(Constants.TRANSACTION_SR)) {
        consentId = (new ValidateIds())
            .validate(consentId, ValidateIds.TYPESET_CASE_IMPORTED, true);
        consentId2 = (new ValidateIds()).validate(consentId2, ValidateIds.TYPESET_CASE_IMPORTED,
            true);
      }
      else if (btxDetails.getTxType().equals(Constants.TRANSACTION_ILTDS)) {
        consentId = (new ValidateIds()).validate(consentId,
            ValidateIds.TYPESET_CASE_WITHOUT_IMPORTED, true);
        consentId2 = (new ValidateIds()).validate(consentId2,
            ValidateIds.TYPESET_CASE_WITHOUT_IMPORTED, true);
      }
    }

    btxDetails.setConsentId(consentId);
    btxDetails.setConsentId2(consentId2);

    ConsentAccessBean consent = new ConsentAccessBean();
    AccessBeanEnumeration consentEnum = (AccessBeanEnumeration) consent.findByConsentID(btxDetails
        .getConsentId());
    if (consentEnum.hasMoreElements()) {
      consent = (ConsentAccessBean) consentEnum.nextElement();
      btxDetails.setCustomerId(consent.getCustomer_id());
      //if the consent has already been pulled, return a message to that effect
      if (consent.getConsent_pull_datetime() != null) {
        DateHelper dateHelper = new DateHelper(consent.getConsent_pull_datetime());
        ArdaisstaffAccessBean staff = new ArdaisstaffAccessBean(new ArdaisstaffKey(consent
            .getConsent_pull_staff_id()));
        String pullPerformedBy = staff.getArdais_staff_fname() + " "
            + staff.getArdais_staff_lname();
        staff = new ArdaisstaffAccessBean(new ArdaisstaffKey(consent.getConsent_pull_request_by()));
        String pullRequestedBy = staff.getArdais_staff_fname() + " "
            + staff.getArdais_staff_lname();
        String pullDate = dateHelper.getFormattedDate();
        btxDetails.addActionMessage(new BtxActionMessage("dataImport.message.caseAlreadyPulled",
            btxDetails.getConsentId(), pullDate, pullPerformedBy, pullRequestedBy));
        btxDetails.setActionForwardTXCompleted("caseAlreadyPulled");
      }
      //otherwise return the data the user needs to pull the case
      else {
        LegalValueSet lvs = new LegalValueSet();
        String user = btxDetails.getLoggedInUserSecurityInfo().getUsername();
        List staffList = IltdsUtils.getArdaisStaffInAccount(user, account);
        Iterator iterator = staffList.iterator();
        while (iterator.hasNext()) {
          ArdaisStaff staff = (ArdaisStaff) iterator.next();
          lvs.addLegalValue(staff.getStaffId(), staff.getFullName());
        }
        btxDetails.setPullRequestedByChoices(lvs);
        if (btxDetails.getTxType().equals(Constants.TRANSACTION_SR)) {
          btxDetails.setPullReasonChoices(BigrGbossData
              .getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_IMPORTED_CASE_PULL_REASON));
        }
        else if (btxDetails.getTxType().equals(Constants.TRANSACTION_ILTDS)) {
          btxDetails.setPullReasonChoices(BigrGbossData
              .getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_CASE_PULL_REASON));
        }
        btxDetails.setActionForwardTXCompleted("success");
      }
    }

    //return the information
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performRevokeCase performer method.
   */
  private BTXDetails validatePerformRevokeCase(BtxDetailsRevokeCase btxDetails) throws Exception {

    //make sure a valid case has been specified
    String account = btxDetails.getLoggedInUserSecurityInfo().getAccount();
    String txType = btxDetails.getTxType();
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();

    String validatedId = null;
    validatedId = (new ValidateIds()).validate(btxDetails.getConsentId(),
        ValidateIds.TYPESET_CASE_LINKED, false);
    if (!IltdsUtils.caseExists(validatedId)
        || (!(securityInfo.isInRoleSystemOwner()) && !account.equals(IltdsUtils
            .getAccountForCase(validatedId)))) {
      btxDetails.addActionError(new BtxActionError("dataImport.error.nonExistentEntity", "case",
          "case id"));
    }

    //if the case has not already been revoked, do additional validation
    boolean caseAlreadyRevoked = IltdsUtils.consentRevoked(validatedId);
    if (!caseAlreadyRevoked) {
      //make sure a requested by value have been specified
      if (ApiFunctions.isEmpty(btxDetails.getPullRequestedBy())) {
        btxDetails.addActionError(new BtxActionError("error.noValue", "Requested By"));
      }
    }

    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that revokes a case.
   */
  private BTXDetails performRevokeCase(BtxDetailsRevokeCase btxDetails) throws Exception {
    ConsentAccessBean consent = new ConsentAccessBean();
    AccessBeanEnumeration consentEnum = (AccessBeanEnumeration) consent.findByConsentID(btxDetails
        .getConsentId());
    if (consentEnum.hasMoreElements()) {
      consent = (ConsentAccessBean) consentEnum.nextElement();
      //if the consent has already been revoked, return a message to that effect. This should never
      //happen because the performRevokeCaseStart method should handle it, but better safe
      //than sorry

      if (IltdsUtils.consentRevoked(btxDetails.getConsentId())) {
        RevokedconsentAccessBean consentRevoked = new RevokedconsentAccessBean(
            new RevokedconsentKey(btxDetails.getConsentId(), consent.getArdais_id()));
        DateHelper dateHelper = new DateHelper(consentRevoked.getRevoked_timestamp());
        ArdaisstaffAccessBean staff = new ArdaisstaffAccessBean(new ArdaisstaffKey(consentRevoked
            .getRevoked_by_staff_id()));
        String revokeRequestedBy = staff.getArdais_staff_fname() + " "
            + staff.getArdais_staff_lname();
        String revokeDate = dateHelper.getFormattedDate();
        btxDetails.addActionMessage(new BtxActionMessage("iltds.error.consent.caseAlreadyRevoked",
            btxDetails.getConsentId(), revokeDate, revokeRequestedBy));
        btxDetails.setActionForwardTXCompleted("caseAlreadyRevoked");
      }
      //otherwise revoke the case
      else {

        String staffId = btxDetails.getPullRequestedBy();

        String ardaisId = consent.getArdais_id();
        ConsentKey ckey = (ConsentKey) consent.__getKey();
        ConsentOperationHome home = (ConsentOperationHome) EjbHomes.getHome(ConsentOperationHome.class);
        ConsentOperation consentOp = home.create();
        consentOp.revokeConsent(ckey.consent_id, ardaisId, staffId, "");

        //add the ardais id of the consent to the btxDetails, so the transaction can be logged
        //correctly (i.e. shows up for the donor as well as the case)
        btxDetails.setArdaisId(consent.getArdais_id());

        //remove any samples that belonged to the case from hold
        List samples = IltdsUtils.getSamplesForConsent(btxDetails.getConsentId());
        if (samples.size() > 0) {
          Iterator iterator = samples.iterator();
          while (iterator.hasNext()) {
            String sampleId = (String) iterator.next();
            consentOp.pullFromESHold(sampleId);
          }
        }
        btxDetails.addActionMessage(new BtxActionMessage("dataImport.message.caseAction",
            btxDetails.getConsentId(), consent.getArdais_id(), "revoked"));

        //send email to repository manager telling him/her the case is pulled
        //send email to request manager, informing them of new request
        String accountId = btxDetails.getLoggedInUserSecurityInfo().getAccount();
        ArdaisaccountAccessBean account = new ArdaisaccountAccessBean(new ArdaisaccountKey(
            accountId));
        String requestMgrEmail = account.getRequest_mgr_email_address();
        if (!ApiFunctions.isEmpty(requestMgrEmail)) {
          StringBuffer mailMessage = new StringBuffer(100);
          mailMessage.append("Case ");
          mailMessage.append(btxDetails.getConsentId());
          mailMessage.append(" has been revoked.");
          ApiFunctions.generateEmail(ApiProperties.getProperty(ApiResources.API_MAIL_FROM_DEFAULT),
              requestMgrEmail, mailMessage.toString(), mailMessage.toString());
        }
        btxDetails.setActionForwardTXCompleted("success");
      }
    }
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performPullCase performer method.
   */
  private BTXDetails validatePerformPullCase(BtxDetailsPullCase btxDetails) throws Exception {

    //make sure a valid case has been specified
    String account = btxDetails.getLoggedInUserSecurityInfo().getAccount();
    String txType = btxDetails.getTxType();
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();

    String validatedId = null;
    if (txType.equalsIgnoreCase(Constants.TRANSACTION_SR)) {
      validatedId = (new ValidateIds()).validate(btxDetails.getConsentId(),
          ValidateIds.TYPESET_CASE_IMPORTED, true);
      if (!IltdsUtils.caseImportedByAccount(validatedId, account)) {
        btxDetails.addActionError(new BtxActionError("dataImport.error.nonExistentEntity", "case",
            "case id"));
      }
    }
    else if (txType.equalsIgnoreCase(Constants.TRANSACTION_ILTDS)) {
      validatedId = (new ValidateIds()).validate(btxDetails.getConsentId(),
          ValidateIds.TYPESET_CASE_WITHOUT_IMPORTED, true);
      //make sure the case exists, and that the case belongs to the user if the user is
      //not the system owner
      if (!IltdsUtils.caseExists(validatedId)
          || (!(securityInfo.isInRoleSystemOwner()) && !account.equals(IltdsUtils
              .getAccountForCase(validatedId)))) {
        btxDetails.addActionError(new BtxActionError("dataImport.error.nonExistentEntity", "case",
            "case id"));
      }
    }

    //if the case has not already been pulled, do additional validation
    boolean caseAlreadyPulled = IltdsUtils.consentPulled(validatedId);
    if (!caseAlreadyPulled) {
      //make sure a pull reason and requested by value have been specified
      if (ApiFunctions.isEmpty(btxDetails.getPullReason())) {
        btxDetails.addActionError(new BtxActionError("error.noValue", "Reason"));
      }
      else {
        LegalValueSet pullReasons = null;
        if (txType.equalsIgnoreCase(Constants.TRANSACTION_SR)) {
          pullReasons = BigrGbossData
              .getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_IMPORTED_CASE_PULL_REASON);
        }
        else if (txType.equalsIgnoreCase(Constants.TRANSACTION_ILTDS)) {
          pullReasons = BigrGbossData
              .getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_CASE_PULL_REASON);
        }

        if (pullReasons.getDisplayValue(btxDetails.getPullReason()) == null) {
          btxDetails.addActionError(new BtxActionError("dataImport.error.invalidValue", "Reason"));
        }
      }
      if (ApiFunctions.isEmpty(btxDetails.getPullRequestedBy())) {
        btxDetails.addActionError(new BtxActionError("error.noValue", "Requested By"));
      }

      //comment validation
      String comments = btxDetails.getPullNote();
      if (!ApiFunctions.isEmpty(comments) && comments.length() > MAX_PULL_COMMENT_LENGTH) {
        btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Comments",
            MAX_PULL_COMMENT_LENGTH + " characters"));
      }
    }

    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that pulls a case.
   */
  private BTXDetails performPullCase(BtxDetailsPullCase btxDetails) throws Exception {
    ConsentAccessBean consent = new ConsentAccessBean();
    AccessBeanEnumeration consentEnum = (AccessBeanEnumeration) consent.findByConsentID(btxDetails
        .getConsentId());
    if (consentEnum.hasMoreElements()) {
      consent = (ConsentAccessBean) consentEnum.nextElement();
      //if the consent has already been pulled, return a message to that effect. This should never
      //happen because the performPullCaseStart method should handle it, but better safe
      //than sorry
      if (consent.getConsent_pull_datetime() != null) {
        DateHelper dateHelper = new DateHelper(consent.getConsent_pull_datetime());
        ArdaisstaffAccessBean staff = new ArdaisstaffAccessBean(new ArdaisstaffKey(consent
            .getConsent_pull_staff_id()));
        String pullPerformedBy = staff.getArdais_staff_fname() + " "
            + staff.getArdais_staff_lname();
        staff = new ArdaisstaffAccessBean(new ArdaisstaffKey(consent.getConsent_pull_request_by()));
        String pullRequestedBy = staff.getArdais_staff_fname() + " "
            + staff.getArdais_staff_lname();
        String pullDate = dateHelper.getFormattedDate();
        btxDetails.addActionMessage(new BtxActionMessage("dataImport.message.caseAlreadyPulled",
            btxDetails.getConsentId(), pullDate, pullPerformedBy, pullRequestedBy));
        btxDetails.setActionForwardTXIncomplete("success");
      }
      //otherwise pull the case
      else {
        Timestamp currentTime = new Timestamp((new java.util.Date()).getTime());
        //pull the case
        consent.setConsent_pull_staff_id(btxDetails.getLoggedInUserSecurityInfo().getUsername());
        consent.setConsent_pull_datetime(currentTime);
        consent.setConsent_pull_reason_cd(btxDetails.getPullReason());
        consent.setConsent_pull_request_by(btxDetails.getPullRequestedBy());
        consent.setConsent_pull_comment(btxDetails.getPullNote());
        consent.commitCopyHelper();

        //add the ardais id of the consent to the btxDetails, so the transaction can be logged
        //correctly (i.e. shows up for the donor as well as the case)
        btxDetails.setArdaisId(consent.getArdais_id());

        //remove any samples that belonged to the case from hold
        List samples = IltdsUtils.getSamplesForConsent(btxDetails.getConsentId());
        if (samples.size() > 0) {
          ConsentOperationHome home = null;
          ConsentOperation opBean = null;
          Iterator iterator = samples.iterator();
          while (iterator.hasNext()) {
            String sampleId = (String) iterator.next();
            if (home == null) home = (ConsentOperationHome) EjbHomes.getHome(ConsentOperationHome.class);
            if (opBean == null) opBean = home.create();
            opBean.pullFromESHold(sampleId);
          }
        }
        btxDetails.addActionMessage(new BtxActionMessage("dataImport.message.caseAction",
            btxDetails.getConsentId(), consent.getArdais_id(), "pulled"));

        //send email to repository manager telling him/her the case is pulled
        //send email to request manager, informing them of new request
        String accountId = btxDetails.getLoggedInUserSecurityInfo().getAccount();
        ArdaisaccountAccessBean account = new ArdaisaccountAccessBean(new ArdaisaccountKey(
            accountId));
        String requestMgrEmail = account.getRequest_mgr_email_address();
        if (!ApiFunctions.isEmpty(requestMgrEmail)) {
          StringBuffer mailMessage = new StringBuffer(100);
          mailMessage.append("Case ");
          mailMessage.append(btxDetails.getConsentId());
          mailMessage.append(" has been pulled.");
          ApiFunctions.generateEmail(ApiProperties.getProperty(ApiResources.API_MAIL_FROM_DEFAULT),
              requestMgrEmail, mailMessage.toString(), mailMessage.toString());
        }
        btxDetails.setActionForwardTXCompleted("success");
      }
    }
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performGenerateImportedCasePickList performer method.
   */
  private BTXDetails validatePerformGenerateCasePickList(BtxDetailsGenerateCasePickList btxDetails)
      throws Exception {
    //make sure an id has been specified
    String consentId = btxDetails.getConsentId();
    String txType = btxDetails.getTxType();
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();

    if (ApiFunctions.isEmpty(consentId)) {
      btxDetails.addActionError(new BtxActionError("error.noValue", "Case id"));
    }
    //otherwise make sure the case exists
    else {
      String account = btxDetails.getLoggedInUserSecurityInfo().getAccount();
      //if a consent id has been specified, make sure it is an imported case, it exists, and it
      //belongs to the current users account
      if (!ApiFunctions.isEmpty(consentId)) {
        if (txType.equalsIgnoreCase(Constants.TRANSACTION_SR)) {
          String validatedId = (new ValidateIds()).validate(consentId,
              ValidateIds.TYPESET_CASE_IMPORTED, true);
          if (!IltdsUtils.caseImportedByAccount(validatedId, account)) {
            btxDetails.addActionError(new BtxActionError("dataImport.error.nonExistentEntity",
                "case", "case id"));
          }
        }
        else if (txType.equalsIgnoreCase(Constants.TRANSACTION_ILTDS)) {
          String validatedId = (new ValidateIds()).validate(consentId,
              ValidateIds.TYPESET_CASE_WITHOUT_IMPORTED, true);
          if (!(securityInfo.isInRoleSystemOwner())
              && !account.equals(IltdsUtils.getAccountForCase(validatedId))) {
            btxDetails.addActionError(new BtxActionError("dataImport.error.nonExistentEntity",
                "case", "case id"));
          }
        }
      }
    }
    return btxDetails;
  }

  /**
   * This is the main BTX entry point for generating a picklist for an imported case.
   */
  private BTXDetails performGenerateCasePickList(BtxDetailsGenerateCasePickList btxDetails)
      throws Exception {

    //get a list of the sample ids belonging to the case
    List sampleIdList = IltdsUtils.getSamplesForConsent(btxDetails.getConsentId());

    //initialize output data
    List samplesInRepository = new ArrayList();
    List samplesInTransit = new ArrayList();
    List samplesCheckedOut = new ArrayList();

    //walk the list of sample ids, getting the apropriate information for each sample,
    //and adding that information to the list of SampleData objects we will return
    Iterator iterator = sampleIdList.iterator();
    List locationIdList = new ArrayList();
    SampleAccessBean sampleAB = null;
    while (iterator.hasNext()) {
      sampleAB = new SampleAccessBean(new SampleKey((String) iterator.next()));
      SampleData sampleData = new SampleData();
      sampleData.setSampleId(((SampleKey) sampleAB.__getKey()).sample_barcode_id);
      if (sampleAB.getSampleboxKey() != null) {
        sampleData.setBoxBarcodeId(sampleAB.getSampleboxKey().box_barcode_id);
        BoxlocationAccessBean boxLocation = new BoxlocationAccessBean();
        Enumeration boxLocationEnum = boxLocation.findBoxlocationBySamplebox(sampleAB
            .getSampleboxKey());
        if (boxLocationEnum.hasMoreElements()) {
          BTXBoxLocation storageLocation = new BTXBoxLocation();
          sampleData.setStorageLocation(storageLocation);
          BoxlocationKey boxLocationKey = (BoxlocationKey) ((BoxlocationAccessBean) boxLocationEnum
              .nextElement()).__getKey();
          storageLocation.setLocationAddressID(boxLocationKey.geolocation_location_address_id);
          if (!locationIdList.contains(boxLocationKey.geolocation_location_address_id)) {
            locationIdList.add(boxLocationKey.geolocation_location_address_id);
          }
          storageLocation.setRoomID(boxLocationKey.room_id);
          storageLocation.setUnitName(boxLocationKey.unitName);
          storageLocation.setDrawerID(boxLocationKey.drawer_id);
          storageLocation.setSlotID(boxLocationKey.slot_id);
          sampleData.setLocation(BoxLayoutUtils.translateCellRef(sampleAB.getCell_ref_location(),
              sampleAB.getSampleboxKey().box_barcode_id));
          samplesInRepository.add(sampleData);
        }
        else {
          if (sampleAB.getSamplebox().getManifestKey() != null) {
            ManifestDto manifest = new ManifestDto();
            manifest.setManifestNumber(sampleAB.getSamplebox().getManifestKey().manifest_number);
            sampleData.setManifest(manifest);
            samplesInTransit.add(sampleData);
          }
          else {
            sampleData.setInvStatus(sampleAB.getInv_status());
            sampleData.setInvStatusDate(sampleAB.getInv_status_date());
            samplesCheckedOut.add(sampleData);
          }
        }
      }
      else {
        sampleData.setInvStatus(sampleAB.getInv_status());
        sampleData.setInvStatusDate(sampleAB.getInv_status_date());
        samplesCheckedOut.add(sampleData);
      }
    }

    //get a list of LocationData beans from the locationId list
    List locations = getBoxLocationInformation(locationIdList);

    //return the output information for the samples
    btxDetails.setSamplesInRepository(samplesInRepository);
    btxDetails.setSamplesInTransit(samplesInTransit);
    btxDetails.setSamplesCheckedOut(samplesCheckedOut);
    btxDetails.setLocations(locations);

    ConsentAccessBean consentAB = new ConsentAccessBean(new ConsentKey(btxDetails.getConsentId()));
    if (btxDetails.getCaseAction().equals(Constants.CASE_PULL)) {
      //retrieve the necessary information for the consent
      ConsentData consentData = new ConsentData();
      consentData.setPullComment(consentAB.getConsent_pull_comment());
      String requestedBy = consentAB.getConsent_pull_request_by();
      consentData.setPullRequestedById(requestedBy);
      if (!ApiFunctions.isEmpty(requestedBy)) {
        ArdaisstaffAccessBean staff = new ArdaisstaffAccessBean(new ArdaisstaffKey(requestedBy));
        consentData.setPullRequestedByName(staff.getArdais_staff_fname() + " "
            + staff.getArdais_staff_lname());
      }
      else {
        consentData.setPullRequestedByName("");
      }
      btxDetails.setConsentData(consentData);
    }
    else if (btxDetails.getCaseAction().equals(Constants.CASE_REVOKE)) {
      RevokedconsentAccessBean revokedConsent = new RevokedconsentAccessBean(new RevokedconsentKey(
          btxDetails.getConsentId(), consentAB.getArdais_id()));
      ArdaisstaffAccessBean staff = new ArdaisstaffAccessBean(new ArdaisstaffKey(revokedConsent
          .getRevoked_by_staff_id()));
      btxDetails.setRevokeStaffName(staff.getArdais_staff_fname() + " "
          + staff.getArdais_staff_lname());
    }

    //populate the details with the name of the user creating the report
    try {
      ArdaisstaffAccessBean staff = new ArdaisstaffAccessBean(new ArdaisstaffKey(btxDetails
          .getLoggedInUserSecurityInfo().getUsername()));
      btxDetails.setReportGeneratedByName(staff.getArdais_staff_fname() + " "
          + staff.getArdais_staff_lname());
    }
    catch (Exception e) {
      btxDetails.setReportGeneratedByName("Unknown");
    }

    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performCreateCase performer method.
   */
  private BTXDetails validatePerformCreateCase(BtxDetailsCreateCase btxDetails) throws Exception {
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    boolean ardaisIdOkSoFar = true;
    boolean consentIdOkSoFar = true;

    boolean isLinked = btxDetails.isLinked();
    String ardaisId = btxDetails.getArdaisId();
    String consentId = btxDetails.getConsentId();
    String policyId = btxDetails.getPolicyId();
    Date consentDate = filterConsentDate(btxDetails.getConsentDate());
    String consentVersionId = btxDetails.getConsentVersionId();
    String comments = btxDetails.getComments();
    String bloodSampleYN = btxDetails.getBloodSampleYN();
    String additionalNeedleStickYN = btxDetails.getAdditionalNeedleStickYN();
    String futureContactYN = btxDetails.getFutureContactYN();

    if (ApiFunctions.isEmpty(ardaisId)) {
      ardaisIdOkSoFar = false;
      btxDetails.addActionError(new BtxActionError("iltds.error.general.requiredArdaisId"));
    }
    // MR 7777 using validateId. note that this will not allow imported ardais ids...
    else if ((ValidateIds.validateId(ardaisId, (isLinked ? ValidateIds.TYPESET_DONOR_LINKED
        : ValidateIds.TYPESET_DONOR_UNLINKED), false) == null)) {
      ardaisIdOkSoFar = false;
      if (isLinked)
        btxDetails.addActionError(new BtxActionError("iltds.error.general.needLinkedArdaisId"));
      else
        btxDetails.addActionError(new BtxActionError("iltds.error.general.needUnlinkedArdaisId"));
    }

    if (ApiFunctions.isEmpty(consentId)) {
      consentIdOkSoFar = false;
      btxDetails.addActionError(new BtxActionError("iltds.error.general.requiredCaseId"));
    }
    else if ((ValidateIds.validateId(consentId, (isLinked ? ValidateIds.TYPESET_CASE_LINKED
        : ValidateIds.TYPESET_CASE_UNLINKED), false) == null)) {
      consentIdOkSoFar = false;
      if (isLinked)
        btxDetails.addActionError(new BtxActionError("iltds.error.general.needLinkedCaseId",
            consentId));
      else
        btxDetails.addActionError(new BtxActionError("iltds.error.general.needUnlinkedCaseId",
            consentId));
    }
    else if (consentIDExists(consentId)) {
      consentIdOkSoFar = false;
      btxDetails.addActionError(new BtxActionError("iltds.error.createCase.caseExists", consentId));
    }

    //make sure the comments field does not exceed 4000 characters
    if (!ApiFunctions.isEmpty(comments) && comments.length() > MAX_COMMENT_LENGTH) {
      btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Comments",
          MAX_COMMENT_LENGTH + " characters"));
    }

    // Check that we didn't get parameters we only expected to get for linked cases for
    // an unlinked case and vice-versa.
    //  
    if (isLinked) {
      if (ApiFunctions.isEmpty(consentVersionId)) {
        btxDetails.addActionError(new BtxActionError(
            "iltds.error.createCase.requiredConsentVersionId"));
      }

      // Do some validation on the consent date.
      if (consentDate == null) {
        btxDetails.addActionError(new BtxActionError("iltds.error.createCase.requiredConsentDate"));
      }
      else if (consentDate.getTime() > System.currentTimeMillis()) {
        btxDetails.addActionError(new BtxActionError("iltds.error.createCase.futureConsentDate"));
      }

      // Linked cases get their policy from their IRB consent,
      // it is an error if a policy id was passed in.
      //
      if (!ApiFunctions.isEmpty(policyId)) {
        btxDetails.addActionError(new BtxActionError("iltds.error.createCase.policyIdProhibited"));
      }

      //linked cases that employ a policy with an "additional_questions_grpcode" value
      //of "Buke Breast SPORE" need to have values for bloodSampleYN, additionalNeedleStickYN, and
      // futureContactYN
      if (!ApiFunctions.isEmpty(consentVersionId)) {
        IrbVersionData irbData = IltdsUtils.getIrbVersionData(consentVersionId);
        PolicyData policy = IltdsUtils.getPolicyForIrb(irbData.getIrbProtocolName());
        policyId = policy.getPolicyId();
        String additionalQuestionsGroup = policy.getAdditionalQuestionsGrp();
        if (!ApiFunctions.isEmpty(additionalQuestionsGroup)) {
          if (additionalQuestionsGroup.equalsIgnoreCase(FormLogic.ADDITIONAL_QUESTIONS_GRP_DBS)) {
            if (ApiFunctions.isEmpty(bloodSampleYN)) {
              btxDetails.addActionError(new BtxActionError(
                  "iltds.error.createCase.requiredAdditionalQuestion",
                  "Blood sample may be obtained"));
            }
            if (ApiFunctions.isEmpty(additionalNeedleStickYN)) {
              btxDetails.addActionError(new BtxActionError(
                  "iltds.error.createCase.requiredAdditionalQuestion", "Additional needle stick"));
            }
            if (ApiFunctions.isEmpty(futureContactYN)) {
              btxDetails.addActionError(new BtxActionError(
                  "iltds.error.createCase.requiredAdditionalQuestion", "Willing to be contacted"));
            }
          }
        }
        //Note - we're not verifying that the answers to the additional questions are null when
        //they are not required (either because the consent version employed doesn't use them or
        //because this is an unlinked case) because this is a temporary solution. When a more
        //robust solution is implemented this type of verification should be performed.
      }

    }
    else { // unlinked
      if (ApiFunctions.isEmpty(policyId)) {
        btxDetails.addActionError(new BtxActionError("iltds.error.createCase.requiredCasePolicy"));
      }

      // Unlinked cases don't have a consent date
      if (consentDate != null) {
        btxDetails
            .addActionError(new BtxActionError("iltds.error.createCase.consentDateProhibited"));
      }

      // Unlinked cases don't have an IRB consent version.
      if (!ApiFunctions.isEmpty(consentVersionId)) {
        btxDetails.addActionError(new BtxActionError(
            "iltds.error.createCase.consentVersionIdProhibited"));
      }
    }

    if (ardaisIdOkSoFar) {
      //is there is an existing donor with the specified id make sure
      //that donor belongs to this users account
      DonorData donor = new DonorData();
      donor.setArdaisId(ardaisId);
      DDCDonorHome home = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
      DDCDonor donorOperation = home.create();
      DonorData donorData = donorOperation.getDonorProfile(donor);
      if (!ApiFunctions.isEmpty(donorData.getArdaisId())) {
        if (!btxDetails.getLoggedInUserSecurityInfo().getAccount().equals(
            donorData.getArdaisAccountKey())) {
          ardaisIdOkSoFar = false;
          btxDetails.addActionError(new BtxActionError("iltds.error.createCase.invalidDonor",
              "belongs to a different donor institution"));
        }
      }
    }

    if (ardaisIdOkSoFar && consentIdOkSoFar) {
      boolean donorHasCases = donorHasExistingCases(ardaisId);
      if (consentId.startsWith(Constants.LINKED_CONSENT_PREFIX_INITIAL) && donorHasCases) {
        // Check for correct consent id prefix. A Donor cannot be linked to a case
        // starting with "CI0" unless there are no existing cases for them.
        consentIdOkSoFar = false;
        btxDetails.addActionError(new BtxActionError("iltds.error.createCase.wrongRepeatPrefix",
            Constants.LINKED_CONSENT_PREFIX_REPEAT));
      }
      else if (consentId.startsWith(Constants.LINKED_CONSENT_PREFIX_REPEAT) && !donorHasCases) {
        // Check for correct consent id prefix. A Donor cannot be linked to a case
        // starting with "CI7" unless there are already existing cases for them.
        consentIdOkSoFar = false;
        btxDetails.addActionError(new BtxActionError("iltds.error.createCase.wrongNoRepeatPrefix",
            Constants.LINKED_CONSENT_PREFIX_INITIAL));
      }
    }

    // If there is not already a donor, then we'll have to create one, so make sure the
    // account is set up with a donor registration form.
    if (ardaisIdOkSoFar && !ApiFunctions.isEmpty(policyId)) {
      DonorData donor = new DonorData();
      donor.setArdaisId(ardaisId);
      DDCDonorHome home = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
      DDCDonor donorOperation = home.create();
      DonorData donorData = donorOperation.getDonorProfile(donor);
      if (ApiFunctions.isEmpty(donorData.getArdaisId())) {
        String regFormId = IltdsUtils.getAccountDonorRegistrationFormId(securityInfo.getAccount());
        if (ApiFunctions.isEmpty(regFormId)) {
          btxDetails.addActionError(new BtxActionError(
              "dataImport.error.noDonorRegistrationFormSpecifiedForAccount"));
        }
      }
    }

    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that creates a new case.
   */
  private BTXDetails performCreateCase(BtxDetailsCreateCase btxDetails) throws Exception {
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    Timestamp myTimestamp = new Timestamp(System.currentTimeMillis());
    String entryUserId = btxDetails.getUserId();
    boolean isLinked = btxDetails.isLinked();
    String policyId; // input for unlinked, output for linked (obtained from consentVersionId)
    String consentVersionId = null; // used for linked cases only
    Date consentDate = null; // used for linked cases only
    IrbVersionData irb = null; // used for linked cases only
    String irbProtocolAndVersionName = null; // used for linked cases only

    // Gather data and set output fields. This section does things that we need to
    // regardless of whether we're actually going to create the case or whether we're
    // just validating input and preparing to request confirmation from the user.
    //
    if (isLinked) {
      consentVersionId = btxDetails.getConsentVersionId();
      consentDate = filterConsentDate(btxDetails.getConsentDate());
      irb = IltdsUtils.getIrbVersionData(consentVersionId);
      policyId = irb.getIrbPolicyId();
      irbProtocolAndVersionName = irb.getIrbProtocolAndVersionName();

      // Set output fields
      btxDetails.setPolicyId(policyId);
      btxDetails.setIrbProtocolAndVersionName(irbProtocolAndVersionName);
      btxDetails.setConsentDate(consentDate); // set to the filtered consent date
    }
    else { // unlinked
      // Unlinked cases must have a policy id passed in since there's no IRB to get it from.
      policyId = btxDetails.getPolicyId();

      // Set output fields
      btxDetails.setIrbProtocolAndVersionName(null);
    }

    // Set output fields
    PolicyData policyData = PolicyUtils.getPolicyData(policyId);
    btxDetails.setPolicyName(policyData.getPolicyName());

    if (btxDetails.isValidateOnly()) {
      btxDetails.setActionForwardTXIncomplete("requestConfirmation");
      return btxDetails;
    }
    else if (btxDetails.isConfirmCanceled()) {
      btxDetails.setActionForwardRetry();
      return btxDetails;
    }

    // If we get here, we're going to actually create the new case.

    String ardaisId = btxDetails.getArdaisId();
    String consentId = btxDetails.getConsentId();

    // Get staffGeolocation
    //
    Geolocation staffGeolocation = null;
    String acctKey = null;
    {
      ArdaisstaffAccessBean staff = new ArdaisstaffAccessBean(new ArdaisstaffKey(entryUserId));
      staffGeolocation = (Geolocation) staff.getGeolocation().getEJBRef();
      acctKey = staff.getArdais_acct_key();
    }

    {
      // Check if the donor profile exist.
      DonorData donor = new DonorData();
      donor.setArdaisId(ardaisId);

      DDCDonorHome home = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
      DDCDonor donorOperation = home.create();
      DonorData donorData = donorOperation.getDonorProfile(donor);
      if (ApiFunctions.isEmpty(donorData.getArdaisId())) {
        donor.setImportedYN(FormLogic.DB_NO);
        donor.setCreateUser(btxDetails.getUserId());
        donor.setArdaisAccountKey(btxDetails.getUserAccount());
        String regFormId = IltdsUtils.getAccountDonorRegistrationFormId(securityInfo.getAccount());
        donor.setRegistrationFormId(regFormId);

        // Create a donor profile record.
        donorOperation.createDonorProfile(donor, btxDetails.getLoggedInUserSecurityInfo());
      }
    }

    // Create the consent record
    {
      ConsentAccessBean myConsent = new ConsentAccessBean();
      myConsent.setInit_ardais_id(ardaisId);
      myConsent.setInit_consent_id(consentId);
      myConsent.setInit_policy_id(new BigDecimal(policyId));
      myConsent.setInit_ardais_acct_key(acctKey);
      myConsent.setInit_imported_yn(FormLogic.DB_NO);
      myConsent.setInit_case_registration_form(policyData.getCaseRegistrationFormId());

      if (isLinked) {
        BigDecimal consentVersionKey = new BigDecimal(consentVersionId);
        Timestamp myConsentTimestamp = new Timestamp(consentDate.getTime());
        myConsent.setConsent_datetime(myConsentTimestamp);
        myConsent.setConsent_version_id(consentVersionKey);
        myConsent.setConsent_version_num(irbProtocolAndVersionName);
        myConsent.setInit_policy_id(new BigDecimal(policyId));
        if (FormLogic.DB_YES.equalsIgnoreCase(btxDetails.getBloodSampleYN())) {
          myConsent.setBlood_sample_yn(FormLogic.DB_YES);
        }
        else if (FormLogic.DB_NO.equalsIgnoreCase(btxDetails.getBloodSampleYN())) {
          myConsent.setBlood_sample_yn(FormLogic.DB_NO);
        }
        if (FormLogic.DB_YES.equalsIgnoreCase(btxDetails.getAdditionalNeedleStickYN())) {
          myConsent.setAdditional_needle_stick_yn(FormLogic.DB_YES);
        }
        else if (FormLogic.DB_NO.equalsIgnoreCase(btxDetails.getAdditionalNeedleStickYN())) {
          myConsent.setAdditional_needle_stick_yn(FormLogic.DB_NO);
        }
        if (FormLogic.DB_YES.equalsIgnoreCase(btxDetails.getFutureContactYN())) {
          myConsent.setFuture_contact_yn(FormLogic.DB_YES);
        }
        else if (FormLogic.DB_NO.equalsIgnoreCase(btxDetails.getFutureContactYN())) {
          myConsent.setFuture_contact_yn(FormLogic.DB_NO);
        }
      }

      myConsent.setForm_entry_datetime(myTimestamp);
      myConsent.setLinked((isLinked ? "Y" : "N"));
      myConsent.setBankable_ind((isLinked ? null : "Y"));
      myConsent.setArdais_staff_id(entryUserId);
      myConsent.setForm_entry_staff_id(entryUserId);
      myConsent.setGeolocation(staffGeolocation);
      myConsent.setComments(btxDetails.getComments());

      myConsent.commitCopyHelper();
    }

    btxDetails.addActionMessage(new BtxActionMessage("iltds.error.createCase.confirmCreation",
        consentId, ardaisId));

    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  private BTXDetails validatePerformGetRegistrationForm(BtxDetailsGetCaseRegistrationForm btxDetails)
      throws Exception {
    // The policy id is required, so make sure it was specified.
    String policyId = btxDetails.getPolicyId();
    if (ApiFunctions.isEmpty(policyId)) {
      btxDetails.addActionError(new BtxActionError("error.noValue", "policy id"));
    }
    else {
      // Get the sample registration form for the sample type from the policy. If the form is
      // not found or disabled, return an error, otherwise add it to the btx details.
      PolicyData policyData = PolicyUtils.getPolicyData(policyId);
      String formId = policyData.getCaseRegistrationFormId();
      FormDefinitionServiceResponse response = FormDefinitionService.SINGLETON
          .findFormDefinitionById(formId);
      DataFormDefinition form = response.getDataFormDefinition();
      if ((form != null) && form.isEnabled()) {
        btxDetails.setRegistrationForm(form);
      }
      else {
        btxDetails.addActionError(new BtxActionError("dataImport.error.noCaseRegForm"));
      }
    }

    return btxDetails;
  }

  private BTXDetails performGetRegistrationForm(BtxDetailsGetCaseRegistrationForm btxDetails)
      throws Exception {
    // Note: The validate method already put the form definition in the BTX details if it was
    // found, so we don't have to do anything.
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  private void validateCaseInformation(BtxDetailsCreateImportedCase btxDetails) {
    // Check all of the host elements in the registration form. Make sure that the required
    // elements have a value and that the value is valid. In addition, keep track of which host
    // elements are not in the registration form so we can ensure that they do not have values.
    Set allHostAttributes = ArtsConstants.getCaseAttributes();
    DataFormDefinition regForm = btxDetails.getRegistrationForm();
    DataFormDefinitionHostElement[] hostElements = regForm.getDataHostElements();
    for (int i = 0; i < hostElements.length; i++) {
      DataFormDefinitionHostElement hostElement = hostElements[i];
      String hostId = hostElement.getHostId();
      allHostAttributes.remove(hostId);
      if (hostElement.isRequired()) {
        validateAttributeRequired(btxDetails, hostElement);
      }
    }

    // Iterate over all of the host elements that were not in the registration form and
    // check that their values are empty.
    Iterator i = allHostAttributes.iterator();
    while (i.hasNext()) {
      String attr = (String) i.next();
      validateAttributeEmpty(btxDetails, attr);
    }
  }

  private void validateAttributeRequired(BtxDetailsCreateImportedCase btxDetails,
                                         DataFormDefinitionHostElement hostElement) {
    String hostId = hostElement.getHostId(); // note: host id is a CUI
    String desc = hostElement.getDisplayName();
    if (ApiFunctions.isEmpty(desc)) {
      desc = GbossFactory.getInstance().getDescription(hostId);
    }
    if (hostId.equals(ArtsConstants.ATTRIBUTE_CASE_COMMENTS)) {
      if (ApiFunctions.isEmpty(btxDetails.getComments())) {
        btxDetails.addActionError(new BtxActionError("error.noValue", desc));
      }
    }
    else if (hostId.equals(ArtsConstants.ATTRIBUTE_CASE_DIAGNOSIS)) {
      if (ApiFunctions.isEmpty(btxDetails.getDiagnosis())) {
        btxDetails.addActionError(new BtxActionError("error.noValue", desc));
      }
    }
  }

  private void validateAttributeEmpty(BtxDetailsCreateImportedCase btxDetails, String attr) {
    String desc = GbossFactory.getInstance().getDescription(attr);
    if (attr.equals(ArtsConstants.ATTRIBUTE_CASE_COMMENTS)) {
      if (!ApiFunctions.isEmpty(btxDetails.getComments())) {
        btxDetails.addActionError(new BtxActionError("error.valueNotInRegForm", desc));
      }
    }
    else if (attr.equals(ArtsConstants.ATTRIBUTE_CASE_DIAGNOSIS)) {
      if (!ApiFunctions.isEmpty(btxDetails.getDiagnosis())) {
        btxDetails.addActionError(new BtxActionError("error.valueNotInRegForm", desc));
      }
    }
  }

  private void validateKcFormInstanceCommon(BtxDetailsCreateImportedCase btxDetails) {
    String regFormId = btxDetails.getRegistrationForm().getFormDefinitionId();

    FormInstance formInstance = btxDetails.getRegistrationFormInstance();
    if ((formInstance != null) && formInstance.getDataElements().length > 0) {
      String caseId = btxDetails.getConsentId();
      String domainObjectId = formInstance.getDomainObjectId();
      if ((domainObjectId != null) && !domainObjectId.equals(caseId)) {
        btxDetails.addActionError(new BtxActionError("generic.message",
            "Domain object id specified for sample registration form (" + domainObjectId
                + ") does not match case id (" + caseId + ")."));
      }
      formInstance.setDomainObjectId(caseId);

      String domainObjectType = formInstance.getDomainObjectType();
      if ((domainObjectType != null) && !domainObjectType.equals(TagTypes.DOMAIN_OBJECT_VALUE_CASE)) {
        btxDetails.addActionError(new BtxActionError("generic.message",
            "Domain object type specified for sample registration form (" + domainObjectType
                + ") must be '" + TagTypes.DOMAIN_OBJECT_VALUE_CASE + "'."));
      }
      formInstance.setDomainObjectType(TagTypes.DOMAIN_OBJECT_VALUE_CASE);

      String formDefinitionId = formInstance.getFormDefinitionId();
      if ((formDefinitionId != null) && !formDefinitionId.equals(regFormId)) {
        btxDetails.addActionError(new BtxActionError("generic.message",
            "Form definition id specified for sample registration form (" + formDefinitionId
                + ") does not match expected form definition id (" + regFormId + ")."));
      }
      formInstance.setFormDefinitionId(regFormId);
    }
  }

  private void validateKcFormInstanceForCreate(BtxDetailsCreateImportedCase btxDetails) {
    validateKcFormInstanceCommon(btxDetails);

    FormInstance formInstance = btxDetails.getRegistrationFormInstance();
    if ((formInstance != null) && formInstance.getDataElements().length > 0) {
      BtxActionErrors errors = FormInstanceService.SINGLETON
          .validateCreateFormInstance(formInstance);
      if (!errors.empty()) {
        btxDetails.addActionErrors(errors);
      }
    }
  }

  private void validateKcFormInstanceForModify(BtxDetailsModifyImportedCase btxDetails) {
    validateKcFormInstanceCommon(btxDetails);

    FormInstance formInstance = btxDetails.getRegistrationFormInstance();
    if ((formInstance != null) && formInstance.getDataElements().length > 0) {
      BtxActionErrors errors = FormInstanceService.SINGLETON
          .validateUpdateFormInstance(formInstance);
      if (!errors.empty()) {
        btxDetails.addActionErrors(errors);
      }
    }
  }

  /**
   * Return a date that is the same as the supplied date except that the day of month is set to 1
   * and the seconds and milliseconds are set to zero. This gives the consent date as it should be
   * stored in the database. We set the day of month to 1 because we don't want the consent date to
   * be identifiable to a finer granularity than the month, and we set the second and millisecond to
   * zero because we aren't interested in finer granularity than minutes for the time part.
   * 
   * @return the modified consent date.
   */
  private Date filterConsentDate(Date date) {
    if (date == null) {
      return null;
    }
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(Calendar.DAY_OF_MONTH, 1);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTime();
  }

  /**
   * Check to see if a Consent ID has previously been entered.
   */
  private boolean consentIDExists(String consentID) throws Exception {
    try {
      new ConsentAccessBean(new ConsentKey(consentID));
      return true;
    }
    catch (ObjectNotFoundException e) {
      return false;
    }
  }

  /**
   * Check to see if the donor has existing cases.
   */
  private boolean donorHasExistingCases(String donorId) throws Exception {
    //get the existing cases (if any) for the donor
    ConsentAccessBean myConsent = new ConsentAccessBean();
    Enumeration myEnum = myConsent.findConsentByArdaisID(donorId);
    return (myEnum.hasMoreElements());
  }

  private List getBoxLocationInformation(List locationIds) {
    List returnValue = new ArrayList();
    if (!ApiFunctions.isEmpty(locationIds)) {
      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      StringBuffer buff = new StringBuffer(100);
      buff.append("select * from iltds_geography_location where ");
      buff.append(ApiFunctions.makeBindConditionStringOrList("location_address_id", locationIds
          .size()));
      try {
        con = ApiFunctions.getDbConnection();
        pstmt = con.prepareStatement(buff.toString());
        ApiFunctions.bindBindParameterList(pstmt, 1, locationIds);
        rs = ApiFunctions.queryDb(pstmt, con);
        while (rs.next()) {
          LocationData locationData = new LocationData();
          locationData.setAddressId(rs.getString("LOCATION_ADDRESS_ID"));
          locationData.setName(rs.getString("LOCATION_NAME"));
          locationData.setAddress1(rs.getString("LOCATION_ADDRESS_1"));
          locationData.setAddress2(rs.getString("LOCATION_ADDRESS_2"));
          locationData.setCity(rs.getString("LOCATION_CITY"));
          locationData.setState(rs.getString("LOCATION_STATE"));
          locationData.setZipCode(rs.getString("LOCATION_ZIP"));
          returnValue.add(locationData);
        }
      }
      catch (SQLException e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
      finally {
        ApiFunctions.close(con, pstmt, rs);
      }
    }
    return returnValue;
  }

  //private method used to get an id for a new imported case
  private String getIdForNewImportedCase() {
    String consentId = null;
    Connection connection = null;
    CallableStatement cstmt = null;
    ResultSet results = null;
    try {
      connection = ApiFunctions.getDbConnection();
      cstmt = connection.prepareCall("begin DATA_IMPORT_GET_CONSENT_ID(?,?); end;");
      cstmt.registerOutParameter(1, Types.VARCHAR);
      cstmt.registerOutParameter(2, Types.VARCHAR);
      cstmt.executeQuery();
      Object obj = cstmt.getObject(2);
      if (obj != null) {
        String emsg = obj.toString();
        //throw an exception
        throw new ApiException(
            "DATA_IMPORT_GET_CONSENT_ID failed at BtxPerformerCaseOperations.getIdForNewImportedCase(): "
                + emsg);
      }
      else {
        consentId = cstmt.getString(1);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(connection, cstmt, results);
    }
    return consentId;
  }

  private Map getCaseRegistrationFormIds(List policies) {
    Map ids = new HashMap();
    Iterator i = policies.iterator();
    while (i.hasNext()) {
      PolicyData d = (PolicyData) i.next();
      ids.put(d.getPolicyId(), d.getCaseRegistrationFormId());
    }
    return ids;
  }

  private Map getConsentPolicyIds(List consents) {
    Map ids = new HashMap();
    Iterator i = consents.iterator();
    while (i.hasNext()) {
      IrbVersionData d = (IrbVersionData) i.next();
      ids.put(d.getConsentVersionId(), d.getIrbPolicyId());
    }
    return ids;
  }

  private void populateLabelPrintingData(BtxDetailsCreateImportedCase btxDetails) {
    //determine the templates and the printers available for case label printing
    //If the label printing configuration information is invalid, return a message 
    //letting the user know about the problem
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    String accountId = securityInfo.getAccount();
    try {
      Map labelPrintingData = LabelPrintingConfiguration.getLabelTemplateDefinitionsAndPrintersForObjectType(securityInfo.getAccount(), Constants.LABEL_PRINTING_OBJECT_TYPE_CASE);
      btxDetails.setLabelPrintingData(labelPrintingData);
    }
    catch (IllegalStateException ise) {
      //log the error so the problems are documented
      ApiLogger.log("Label Printing Configuration refresh failed.", ise);
      btxDetails.setLabelPrintingData(new HashMap());
      btxDetails.addActionMessage(
          new BtxActionMessage("orm.error.label.invalidPrintingConfiguration"));
    }
  }
  
  private void validateLabelPrintingData(BtxDetailsCreateImportedCase btxDetails) {
    if (btxDetails.isPrintCaseLabel()) {
      //since all of the logic for validating the printing of case labels is contained
      //in the label service, call out to the service and convert any errors returned
      //into BtxActionErrors.
      LabelService labelService = new LabelService();
      BigrValidationErrors bve = labelService.validateCommonLabelPrintingData(btxDetails.getLoggedInUserSecurityInfo().getAccount(), 
          btxDetails.getLabelCount() , btxDetails.getTemplateDefinitionName(), 
          btxDetails.getPrinterName(), Constants.LABEL_PRINTING_OBJECT_TYPE_CASE);
      BigrValidationError[] errors = bve.getErrors();
      for (int i = 0; i < errors.length; i++) {
        btxDetails.addActionError(new BtxActionError("generic.message", errors[i].getMessage()));
      }
    }
  }

  private void validateConsentDateTime(BtxDetailsCreateImportedCase btxDetails) {
    btxDetails.setConsentDate(null);
    boolean isValid = true;

    String myYear = btxDetails.getYear();
    String myMonth = btxDetails.getMonth();
    String myHours = btxDetails.getHours();
    String myMinutes = btxDetails.getMinutes();
    String myAmpm = btxDetails.getAmpm();

    if (ApiFunctions.isEmpty(myYear)) {
      btxDetails
          .addActionError(new BtxActionError("iltds.error.createCase.consentDateMissingYear"));
      isValid = false;
    }

    if (ApiFunctions.isEmpty(myMonth)) {
      btxDetails
          .addActionError(new BtxActionError("iltds.error.createCase.consentDateMissingMonth"));
      isValid = false;
    }

    if (ApiFunctions.isEmpty(myHours)) {
      btxDetails
          .addActionError(new BtxActionError("iltds.error.createCase.consentDateMissingHours"));
      isValid = false;
    }

    if (ApiFunctions.isEmpty(myMinutes)) {
      btxDetails.addActionError(new BtxActionError(
          "iltds.error.createCase.consentDateMissingMinutes"));
      isValid = false;
    }

    if (ApiFunctions.isEmpty(myAmpm)) {
      btxDetails
          .addActionError(new BtxActionError("iltds.error.createCase.consentDateInvalidAmpm"));
      isValid = false;
    }

    if (!isValid) {
      return;
    }

    if (!ApiFunctions.isPositiveInteger(myYear)) {
      btxDetails
          .addActionError(new BtxActionError("iltds.error.createCase.consentDateInvalidYear"));
      isValid = false;
    }

    if (!ApiFunctions.isPositiveInteger(myMonth)) {
      btxDetails
          .addActionError(new BtxActionError("iltds.error.createCase.consentDateInvalidMonth"));
      isValid = false;
    }

    if (!ApiFunctions.isPositiveInteger(myHours)) {
      btxDetails
          .addActionError(new BtxActionError("iltds.error.createCase.consentDateInvalidHours"));
      isValid = false;
    }

    if (!ApiFunctions.isPositiveInteger(myMinutes)) {
      btxDetails.addActionError(new BtxActionError(
          "iltds.error.createCase.consentDateInvalidMinutes"));
      isValid = false;
    }

    if (!("am".equals(myAmpm) || "pm".equals(myAmpm))) {
      btxDetails
          .addActionError(new BtxActionError("iltds.error.createCase.consentDateInvalidAmpm"));
      isValid = false;
    }

    if (!isValid) {
      return;
    }

    int myIntYear = Integer.parseInt(myYear);
    int myIntMonth = Integer.parseInt(myMonth) - 1;
    int myIntHours = Integer.parseInt(myHours);
    int myIntMinutes = Integer.parseInt(myMinutes);

    // Change time to 24 hour format.
    if (myAmpm.equals("pm") && myIntHours != 12) {
      myIntHours += 12;
    }
    else if (myAmpm.equals("am") && myIntHours == 12) {
      myIntHours = 0;
    }

    if (myIntYear < 0) {
      btxDetails
          .addActionError(new BtxActionError("iltds.error.createCase.consentDateInvalidYear"));
      isValid = false;
    }

    if (!(myIntMonth >= Calendar.JANUARY && myIntMonth <= Calendar.DECEMBER)) {
      btxDetails
          .addActionError(new BtxActionError("iltds.error.createCase.consentDateInvalidMonth"));
      isValid = false;
    }

    if (!(myIntHours >= 0 && myIntHours <= 23)) {
      btxDetails
          .addActionError(new BtxActionError("iltds.error.createCase.consentDateInvalidHours"));
      isValid = false;
    }

    if (!(myIntMinutes >= 0 && myIntMinutes <= 59)) {
      btxDetails.addActionError(new BtxActionError(
          "iltds.error.createCase.consentDateInvalidMinutes"));
      isValid = false;
    }

    if (isValid) {
      // Create a current Timestamp and a Timestamp from above values
      Calendar myCalendar = Calendar.getInstance();
      myCalendar.setLenient(false);
      myCalendar.set(myIntYear, myIntMonth, 1, myIntHours, myIntMinutes, 0);

      btxDetails.setConsentDate(myCalendar.getTime());
    }

    return;
  }

  /**
   * Invoke the specified method on this class. This is only meant to be called from
   * BtxTransactionPerformerBase, please don't call it from anywhere else as a mechanism to gain
   * access to private methods in this class. Every object that the BTX framework dispatches to must
   * contain this method definition, and its implementation should be exactly the same in each
   * class. Please don't alter this method or its implementation in any way.
   */
  protected BTXDetails invokeBtxEntryPoint(java.lang.reflect.Method method, BTXDetails btxDetails)
      throws Exception {

    // **** DO NOT EDIT THIS METHOD, see the Javadoc comment above.
    return (BTXDetails) method.invoke(this, new Object[] { btxDetails });
  }

}
