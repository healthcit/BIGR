package com.ardais.bigr.javabeans;

import java.io.Serializable;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.iltds.beans.ArdaisstaffAccessBean;
import com.ardais.bigr.iltds.beans.ArdaisstaffKey;
import com.ardais.bigr.iltds.beans.ConsentAccessBean;
import com.ardais.bigr.iltds.beans.ConsentKey;
import com.ardais.bigr.iltds.helpers.DateHelper;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.kc.form.BigrFormInstance;
import com.ardais.bigr.kc.form.BigrFormInstanceEnabled;
import com.ardais.bigr.lims.javabeans.DiagnosticTestResultData;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.pdc.javabeans.PathReportData;
import com.ardais.bigr.util.gen.DbAliases;
import com.ardais.bigr.util.gen.DbConstants;

/**
 * Represents the raw data of a case/consent.
 */
public class ConsentData implements BigrFormInstanceEnabled, Serializable {

  private String _consentId;
  private String _diagnosis;
  private String _diagnosisOther;
  private String _donorId;
  private String _gender;
  private String _donorComments;
  private String _age;
  private String _location;
  private DonorData _donorData;
  private String _importedYN;
  private String _ardaisAcctKey;
  private String _customerId;
  private String _donorCustomerId;
  private String _linked;
  private String _pullStaffId;
  private String _pullStaffName;
  private String _pullRequestedById;
  private String _pullRequestedByName;
  private String _pullDate;
  private String _pullReasonCode;
  private String _pullComment;
  private String _psa;
  private String _dreCid;
  private String _clinicalFindingNotes;
  private String _caseRegistrationFormId;

  private List _diagnosticTestResultData = new ArrayList();
  private PathReportData _pathData;
  
  private BigrFormInstance _bigrFormInstance;

  /**
   * Creates a new <code>ConsentData</code>.
   */
  public ConsentData() {
  }

  /**
   * Creates a new <code>ConsentData</code>, initialized from
   * the data in the ConsentData passed in.
   *
   * @param  consentData  the <code>ConsentData</code>
   */
  public ConsentData(ConsentData consentData) {
    this();
    BigrBeanUtilsBean.SINGLETON.copyProperties(this, consentData);
    //any mutable objects must be handled seperately (BigrBeanUtilsBean.copyProperties
    //does a shallow copy.
    if (consentData.getDonorData() != null) {
      setDonorData(new DonorData(consentData.getDonorData()));
    }
    if (!ApiFunctions.isEmpty(consentData.getDiagnosticTestResultData())) {
      _diagnosticTestResultData.clear();
      Iterator iterator = consentData.getDiagnosticTestResultData().iterator();
      addDiagnosticTestResultData(new DiagnosticTestResultData((DiagnosticTestResultData)iterator.next()));
    }
    if (consentData.getPathReportData() != null) {
      setPathReportData(new PathReportData(consentData.getPathReportData()));
    }
    if (consentData.getBigrFormInstance() != null) {
      setBigrFormInstance(new BigrFormInstance(consentData.getBigrFormInstance().getKcFormInstance()));
    }
  }

  /**
   * Creates a new <code>ConsentData</code>, initialized from
   * the data in the current row of the result set.
   *
   * @param  columns  a <code>Map</code> containing a key for each column in
   * 									 the <code>ResultSet</code>.  Each key must be one of the
   * 									 column alias constants in {@link com.ardais.bigr.util.DbAliases}
   * 									 and the corresponding value is ignored.
   * @param  rs  the <code>ResultSet</code>
   */
  public ConsentData(Map columns, ResultSet rs) {
    this();
    populate(columns, rs);
  }

  /**
   * Creates a new <code>ConsentData</code> from a ConsentAccessBean.
   * 
   * @param  consentBean a <code>ConsentAccessBean</code> the consent access bean
   */
  public ConsentData(ConsentAccessBean consentBean) {
    this();
    populate(consentBean);
  }

  /**
   * Populates this <code>ConsentData</code> from the data in the current row
   * of the result set.
   * 
   * @param  columns  a <code>Map</code> containing a key for each column in
   * 									 the <code>ResultSet</code>.  Each key must be one of the
   * 									 column alias constants in {@link com.ardais.bigr.util.DbAliases}
   * 									 and the corresponding value is ignored.
   * @param  rs  the <code>ResultSet</code>
   */
  public void populate(Map columns, ResultSet rs) {
    try {
      if (columns.containsKey(DbAliases.ASM_CONSENT_ID)) {
        setConsentId(rs.getString(DbAliases.ASM_CONSENT_ID));
      }
      if (columns.containsKey(DbAliases.CONSENT_DX)) {
        setDiagnosis(rs.getString(DbAliases.CONSENT_DX));
      }
      if (columns.containsKey(DbAliases.CONSENT_DX_OTHER)) {
        setDiagnosisOther(rs.getString(DbAliases.CONSENT_DX_OTHER));
      }
      if (columns.containsKey(DbAliases.CONSENT_ID)) {
        setConsentId(rs.getString(DbAliases.CONSENT_ID));
      }
      if (columns.containsKey(DbAliases.CONSENT_LOCATION)) {
        setLocation(rs.getString(DbAliases.CONSENT_LOCATION));
      }
      if (columns.containsKey(DbAliases.DONOR_ID)) {
        setDonorId(rs.getString(DbAliases.DONOR_ID));
      }
      if (columns.containsKey(DbAliases.DONOR_CUSTOMER_ID)) {
        setDonorCustomerId(rs.getString(DbAliases.DONOR_CUSTOMER_ID));
      }
      if (columns.containsKey(DbAliases.DONOR_GENDER)) {
        setGender(rs.getString(DbAliases.DONOR_GENDER));
      }
      if (columns.containsKey(DbAliases.DONOR_PROFILE_NOTES)) {
        Clob clob = rs.getClob(DbAliases.DONOR_PROFILE_NOTES);
        if (clob != null) {
          String cmts = clob.getSubString(1, 200);
          if (clob.length() > 200)
            cmts = cmts + "...";
          setDonorComments(cmts);
        }
      }
      if (columns.containsKey(DbAliases.CONSENT_ARDAIS_ACCT_KEY)) {
        setArdaisAcctKey(rs.getString(DbAliases.CONSENT_ARDAIS_ACCT_KEY));
      }
      if (columns.containsKey(DbAliases.CONSENT_IMPORTED_YN)) {
        setImportedYN(rs.getString(DbAliases.CONSENT_IMPORTED_YN));
      }
      if (columns.containsKey(DbAliases.CONSENT_CUSTOMER_ID)) {
        setCustomerId(rs.getString(DbAliases.CONSENT_CUSTOMER_ID));
      }
      if (columns.containsKey(DbAliases.CONSENT_PSA)) {
        setPsa(rs.getString(DbAliases.CONSENT_PSA));
      }
      if (columns.containsKey(DbAliases.CONSENT_DRE_CID)) {
        setDreCid(rs.getString(DbAliases.CONSENT_DRE_CID));
      }
      if (columns.containsKey(DbAliases.CONSENT_CLINICAL_FINDING_NOTES)) {
        setClinicalFindingNotes(rs.getString(DbAliases.CONSENT_CLINICAL_FINDING_NOTES));
      }
      if (columns.containsKey(DbConstants.CONSENT_ID)) {
        setConsentId(rs.getString(DbConstants.CONSENT_ID));
      }
      if (columns.containsKey(DbConstants.CONSENT_DX)) {
        setDiagnosis(rs.getString(DbConstants.CONSENT_DX));
      }
      if (columns.containsKey(DbConstants.CONSENT_DX_OTHER)) {
        setDiagnosisOther(rs.getString(DbConstants.CONSENT_DX_OTHER));
      }
      if (columns.containsKey(DbConstants.DONOR_ID)) {
        setDonorId(rs.getString(DbConstants.DONOR_ID));
      }
      if (columns.containsKey(DbConstants.CONSENT_LINKED)) {
        setLinked(rs.getString(DbConstants.CONSENT_LINKED));
      }
      if (columns.containsKey(DbConstants.CONSENT_ARDAIS_ACCT_KEY)) {
        setArdaisAcctKey(rs.getString(DbConstants.CONSENT_ARDAIS_ACCT_KEY));
      }
      if (columns.containsKey(DbConstants.CONSENT_IMPORTED_YN)) {
        setImportedYN(rs.getString(DbConstants.CONSENT_IMPORTED_YN));
      }
      if (columns.containsKey(DbConstants.CONSENT_CUSTOMER_ID)) {
        setCustomerId(rs.getString(DbConstants.CONSENT_CUSTOMER_ID));
      }
      if (columns.containsKey(DbConstants.CONSENT_PSA)) {
        setPsa(rs.getString(DbConstants.CONSENT_PSA));
      }
      if (columns.containsKey(DbConstants.CONSENT_DRE_CID)) {
        setDreCid(rs.getString(DbConstants.CONSENT_DRE_CID));
      }
      if (columns.containsKey(DbConstants.CONSENT_CLINICAL_FINDING_NOTES)) {
        setClinicalFindingNotes(rs.getString(DbConstants.CONSENT_CLINICAL_FINDING_NOTES));
      }
      if (columns.containsKey(DbConstants.CONSENT_REG_FORM)) {
        setCaseRegistrationFormId(rs.getString(DbConstants.CONSENT_REG_FORM));
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }
  /**
   * Populates this <code>ConsentData</code> from a ConsentAccessBean.
   * 
   * @param  consentBean a <code>ConsentAccessBean</code> the consent access bean
   */
  public void populate(ConsentAccessBean consentBean) {
    try {
      setArdaisAcctKey(consentBean.getArdais_acct_key());
      setConsentId(((ConsentKey) consentBean.__getKey()).consent_id);
      setCustomerId(consentBean.getCustomer_id());
      setDiagnosis(consentBean.getDisease_concept_id());
      setDiagnosisOther(consentBean.getDisease_concept_id_other());
      setDonorComments(consentBean.getComments());
      setDonorId(consentBean.getArdais_id());
      setImportedYN(consentBean.getImported_yn());
      setLinked(consentBean.getLinked());
      setPullComment(consentBean.getConsent_pull_comment());
      Timestamp pullDate = consentBean.getConsent_pull_datetime();
      if (pullDate != null) {
        setPullDate((new DateHelper(pullDate)).getFormattedDate());
      }
      setPullReasonCode(consentBean.getConsent_pull_reason_cd());
      String pullRequestedBy = consentBean.getConsent_pull_request_by();
      if (!ApiFunctions.isEmpty(pullRequestedBy)) {
        setPullRequestedById(pullRequestedBy);
        ArdaisstaffAccessBean staffBean =
          new ArdaisstaffAccessBean(new ArdaisstaffKey(pullRequestedBy));
        setPullRequestedByName(
          staffBean.getArdais_staff_fname() + " " + staffBean.getArdais_staff_lname());
      }
      String pulledBy = consentBean.getConsent_pull_staff_id();
      if (!ApiFunctions.isEmpty(pulledBy)) {
        setPullStaffId(pulledBy);
        ArdaisstaffAccessBean staffBean = new ArdaisstaffAccessBean(new ArdaisstaffKey(pulledBy));
        setPullStaffName(
          staffBean.getArdais_staff_fname() + " " + staffBean.getArdais_staff_lname());
      }
      if (consentBean.getPsa() != null) {
        setPsa(consentBean.getPsa().toString());
      }
      setDreCid(consentBean.getDre_cid());
      setClinicalFindingNotes(consentBean.getClinical_finding_notes());
      setCaseRegistrationFormId(consentBean.getCase_registration_form());
    }
    catch (Exception e) {
      try {
        ApiLogger.log(
          "Error retrieving data from ConsentAccessBean with PK = "
            + ((ConsentKey) consentBean.__getKey()).consent_id
            + ": Error = "
            + e.getLocalizedMessage());
      }
      catch (Exception e1) {
      }
      throw new ApiException(e);
    }
  }

  /**
   * Returns the consent ID.
   * 
   * @return  The consent ID.
   */
  public String getConsentId() {
    return _consentId;
  }

  /**
   * Returns the diagnosis.
   * 
   * @return  The diagnosis.
   */
  public String getDiagnosis() {
    return _diagnosis;
  }

  /**
   * Returns the other diagnosis.
   * 
   * @return  The other diagnosis.
   */
  public String getDiagnosisOther() {
    return _diagnosisOther;
  }

  /**
   * Returns the donor id.
   * 
   * @return  The donor id.
   */
  public String getDonorId() {
    return _donorId;
  }

  /**
   * Returns the gender.
   * 
   * @return  The gender.
   */
  public String getGender() {
    return _gender;
  }

  /**
   * Sets the consent ID.
   * 
   * @param  consentId  The consent ID to set.
   */
  public void setConsentId(String consentId) {
    _consentId = consentId;
  }

  /**
   * Sets the diagnosis.
   * 
   * @param  diagnosis  the diagnosis
   */
  public void setDiagnosis(String diagnosis) {
    _diagnosis = diagnosis;
  }

  /**
   * Sets the other diagnosis.
   * 
   * @param  diagnosisOther  the other diagnosis
   */
  public void setDiagnosisOther(String diagnosisOther) {
    _diagnosisOther = diagnosisOther;
  }

  /**
   * Sets the donor id.
   * @param  donorId  the donor id
   */
  public void setDonorId(String donorId) {
    _donorId = donorId;
  }

  /**
   * Sets the gender.
   * 
   * @param  gender  the gender
   */
  public void setGender(String gender) {
    _gender = gender;
  }


  /**
   * Returns the path report data bean for the path report associated with 
   * this consent.
   * 
   * @return  The PathReportData bean.
   */
  public PathReportData getPathReportData() {
    return _pathData;
  }

  /**
   * Sets the path report data bean for the path report associated with 
   * this consent.
   * 
   * @param  pathData  the PathReportData bean
   */
  public void setPathReportData(PathReportData pathData) {
    _pathData = pathData;
  }

  /** Returns the diagnosis name */
  public String getDiagnosisName() {
    String returnValue = "";
    String code = getDiagnosis();
    if (!ApiFunctions.isEmpty(code)) {
      if (code.equals(FormLogic.OTHER_DX)) {
        returnValue = getDiagnosisOther();
      }
      else {
        returnValue = BigrGbossData.getDiagnosisDescription(code);
      }
    }
    return returnValue;
  }

  /**
   * Returns the age.
   * @return String
   */
  public String getAge() {
    return _age;
  }

  /**
   * Sets the age.
   * @param age The age to set
   */
  public void setAge(String age) {
    _age = age;
  }

  /**
   * Returns the location.
   * @return String
   */
  public String getLocation() {
    return _location;
  }

  /**
   * Sets the location.
   * @param location The location to set
   */
  public void setLocation(String location) {
    _location = location;
  }

  /**
   * Returns the donorData.
   * @return DonorData
   */
  public DonorData getDonorData() {
    return _donorData;
  }

  /**
   * Sets the donorData.
   * @param donorData The donorData to set
   */
  public void setDonorData(DonorData donorData) {
    _donorData = donorData;
  }

  /**
   * Returns the donorComments.
   * @return String
   */
  public String getDonorComments() {
    return _donorComments;
  }

  /**
   * Sets the donorComments.
   * @param donorComments The donorComments to set
   */
  public void setDonorComments(String donorComments) {
    _donorComments = donorComments;
  }

  /**
   * @param DiagnosticTestResultData
   */
  public void addDiagnosticTestResultData(DiagnosticTestResultData diagnosticTestResultData) {
    _diagnosticTestResultData.add(diagnosticTestResultData);
  }

  /**
   * @return
   */
  public List getDiagnosticTestResultData() {
    return _diagnosticTestResultData;
  }

  /**
   * @return
   */
  public String getArdaisAcctKey() {
    return _ardaisAcctKey;
  }

  /**
   * @return
   */
  public String getCustomerId() {
    return _customerId;
  }

  /**
   * @return
   */
  public String getDonorCustomerId() {
    return _donorCustomerId;
  }

  /**
   * @return
   */
  public String getImportedYN() {
    return _importedYN;
  }

  /**
   * @param string
   */
  public void setArdaisAcctKey(String string) {
    _ardaisAcctKey = string;
  }

  /**
   * @param string
   */
  public void setCustomerId(String string) {
    _customerId = string;
  }

  /**
   * @param string
   */
  public void setDonorCustomerId(String string) {
    _donorCustomerId = string;
  }

  /**
   * @param string
   */
  public void setImportedYN(String string) {
    _importedYN = string;
  }

  /**
   * @return
   */
  public String getLinked() {
    return _linked;
  }

  /**
   * @param string
   */
  public void setLinked(String string) {
    _linked = string;
  }

  /**
   * @return
   */
  public String getPullComment() {
    return _pullComment;
  }

  /**
   * @return
   */
  public String getPullDate() {
    return _pullDate;
  }

  /**
   * @return
   */
  public String getPullReasonCode() {
    return _pullReasonCode;
  }

  /**
   * @return
   */
  public String getPullRequestedById() {
    return _pullRequestedById;
  }

  /**
   * @return
   */
  public String getPullRequestedByName() {
    return _pullRequestedByName;
  }

  /**
   * @return
   */
  public String getPullStaffId() {
    return _pullStaffId;
  }

  /**
   * @return
   */
  public String getPullStaffName() {
    return _pullStaffName;
  }

  /**
   * @param string
   */
  public void setPullComment(String string) {
    _pullComment = string;
  }

  /**
   * @param string
   */
  public void setPullDate(String string) {
    _pullDate = string;
  }

  /**
   * @param string
   */
  public void setPullReasonCode(String string) {
    _pullReasonCode = string;
  }

  /**
   * @param string
   */
  public void setPullRequestedById(String string) {
    _pullRequestedById = string;
  }

  /**
   * @param string
   */
  public void setPullRequestedByName(String string) {
    _pullRequestedByName = string;
  }

  /**
   * @param string
   */
  public void setPullStaffId(String string) {
    _pullStaffId = string;
  }

  /**
   * @param string
   */
  public void setPullStaffName(String string) {
    _pullStaffName = string;
  }

  /**
   * @return
   */
  public String getClinicalFindingNotes() {
    return _clinicalFindingNotes;
  }

  /**
   * @return
   */
  public String getDreCid() {
    return _dreCid;
  }

  /**
   * @return
   */
  public String getPsa() {
    return _psa;
  }

  /**
   * @param string
   */
  public void setClinicalFindingNotes(String string) {
    _clinicalFindingNotes = string;
  }

  /**
   * @param string
   */
  public void setDreCid(String string) {
    _dreCid = string;
  }

  /**
   * @param string
   */
  public void setPsa(String string) {
    _psa = string;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.kc.form.BigrFormInstanceEnabled#getBigrFormInstance()
   */
  public BigrFormInstance getBigrFormInstance() {
    return _bigrFormInstance;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.kc.form.BigrFormInstanceEnabled#setBigrFormInstance(com.ardais.bigr.kc.form.BigrFormInstance)
   */
  public void setBigrFormInstance(BigrFormInstance bigrFormInstance) {
    _bigrFormInstance = bigrFormInstance;
  }


  public String getCaseRegistrationFormId() {
    return _caseRegistrationFormId;
  }
  public void setCaseRegistrationFormId(String caseRegistrationFormId) {
    _caseRegistrationFormId = caseRegistrationFormId;
  }
}
