package com.ardais.bigr.iltds.databeans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.orm.helpers.BigrGbossData;

/**
 * Insert the type's description here.
 * Creation date: (3/20/2002 10:41:33 AM)
 * @author: Jake Thompson
 */
public class CaseData implements java.io.Serializable {
	private final static long serialVersionUID = 6242763279072802001L;
	private List asms = new ArrayList();
  private String donor_id;
	private String case_id;
  private String customer_id;
  private String linked;
	private String donor_location;
  private String consentAccount;
	private String ddc_check;
	private String path_report_id;

	private String diagnosis;
  private String dx_other;
  private String tissue;
  private String tc_finding_other;
  private String tc_origin_other;
	private String procedure;
  private String proc_other;
	private String status;

	private String ascii_report;
	
  private DateData consentDate;
	private DateData release_date;
	private DateData verified_date;
	private DateData revoked_date;
	private DateData pulled_date;
	
	private DonorData parent;
	private boolean exists = false;
  
  private String policy_name;
  private String policy_id;
  private String protocol_name;
/**
 * CaseBean constructor comment.
 */
public CaseData() {
	super();
}
/**
 * CaseBean constructor comment.
 */
public CaseData(CaseData caseData) {
  this(caseData, true);
}
/**
 * CaseBean constructor comment.
 */
public CaseData(CaseData caseData, boolean copyParent) {
  this();
  BigrBeanUtilsBean.SINGLETON.copyProperties(this, caseData);
  //any mutable objects must be handled seperately (BigrBeanUtilsBean.copyProperties
  //does a shallow copy.
  if (!ApiFunctions.isEmpty(caseData.getAsms())) {
    AsmData newAsm = null;
    AsmData originalAsm = null;
    asms.clear();
    Iterator iterator = caseData.getAsms().iterator();
    while (iterator.hasNext()) {
      //any original asms pointing back to the original caseData need
      //to be handled specially, to prevent an infinite loop of copying.
      originalAsm = (AsmData)iterator.next();
      if (caseData.equals(originalAsm.getParent())) {
        newAsm = new AsmData(originalAsm, false);
        newAsm.setParent(this);
      }
      else {
        newAsm = new AsmData(originalAsm);
      }
      asms.add(newAsm);
    }
  }
  if (caseData.getConsentDate() != null) {
    setConsentDate(new DateData(caseData.getConsentDate()));
  }
  if (caseData.getRelease_date() != null) {
    setRelease_date(new DateData(caseData.getRelease_date()));
  }
  if (caseData.getVerified_date() != null) {
    setVerified_date(new DateData(caseData.getVerified_date()));
  }
  if (caseData.getRevoked_date() != null) {
    setRevoked_date(new DateData(caseData.getRevoked_date()));
  }
  if (caseData.getPulled_date() != null) {
    setPulled_date(new DateData(caseData.getPulled_date()));
  }
  if (copyParent && caseData.getParent() != null) {
    setParent(new DonorData(caseData.getParent()));
  }
}
/**
 * Insert the method's description here.
 * Creation date: (3/20/2002 10:45:19 AM)
 * @param newAsms java.util.Map
 */
public void addAsm(AsmData newAsm) {
	asms.add(newAsm);
}
/**
 * Insert the method's description here.
 * Creation date: (7/2/2002 10:34:45 AM)
 * @return java.lang.String
 */
public java.lang.String getAscii_report() {
	return ascii_report;
}
/**
 * Insert the method's description here.
 * Creation date: (3/21/2002 8:42:35 AM)
 * @return java.util.List
 */
public java.util.List getAsms() {
	return asms;
}
/**
 * Insert the method's description here.
 * Creation date: (3/20/2002 10:56:25 AM)
 * @return java.lang.String
 */
public java.lang.String getCase_id() {
	return case_id;
}
/**
 * Insert the method's description here.
 * Creation date: (7/2/2002 10:16:37 AM)
 * @return java.lang.String
 */
public java.lang.String getDdc_check() {
	return ddc_check;
}
/**
 * Insert the method's description here.
 * Creation date: (7/2/2002 10:27:03 AM)
 * @return java.lang.String
 */
public java.lang.String getDiagnosis() {
	return BigrGbossData.getDiagnosisDescription(diagnosis);
}
/**
 * Insert the method's description here.
 * Creation date: (7/2/2002 10:27:03 AM)
 * @return java.lang.String
 */
public java.lang.String getDx_Other() {
  return dx_other;
}
/**
 * Insert the method's description here.
 * Creation date: (3/21/2002 10:49:23 AM)
 * @return java.lang.String
 */
public java.lang.String getDonor_id() {
	return donor_id;
}
/**
 * Insert the method's description here.
 * Creation date: (6/28/2002 2:26:02 PM)
 * @return java.lang.String
 */
public java.lang.String getDonor_location() {
	return donor_location;
}
/**
 * Insert the method's description here.
 * Creation date: (3/22/2002 7:25:45 AM)
 * @return com.ardais.bigr.iltds.databeans.DonorData
 */
public DonorData getParent() {
	return parent;
}
/**
 * Insert the method's description here.
 * Creation date: (3/20/2002 10:56:25 AM)
 * @return java.lang.String
 */
public java.lang.String getPath_report_id() {
	return path_report_id;
}
public java.lang.String getPolicy_name() {
  return policy_name;
}
public java.lang.String getProtocol_name() {
  return protocol_name;
}
/**
 * Insert the method's description here.
 * Creation date: (7/10/2003)
 * @return java.lang.String
 */
public java.lang.String getProc_other() {
  return proc_other;
}
/**
 * Insert the method's description here.
 * Creation date: (7/2/2002 10:27:03 AM)
 * @return java.lang.String
 */
public java.lang.String getProcedure() {
	return BigrGbossData.getProcedureDescription(procedure);
}
/**
 * Insert the method's description here.
 * Creation date: (7/2/2002 4:03:27 PM)
 * @return com.ardais.bigr.iltds.databeans.DateData
 */
public DateData getPulled_date() {
	return pulled_date;
}
/**
 * Insert the method's description here.
 * Creation date: (4/5/2002 2:20:13 PM)
 * @return com.ardais.bigr.iltds.databeans.DateData
 */
public DateData getRelease_date() {
	return release_date;
}
/**
 * Insert the method's description here.
 * Creation date: (7/2/2002 4:03:27 PM)
 * @return com.ardais.bigr.iltds.databeans.DateData
 */
public DateData getRevoked_date() {
	return revoked_date;
}
/**
 * Insert the method's description here.
 * Creation date: (7/2/2002 10:27:03 AM)
 * @return java.lang.String
 */
public java.lang.String getStatus() {
    status = "Viable";
    
    if (pulled_date.getTimestamp() != null) {
        status = "Pulled";
    }
    if (revoked_date.getTimestamp() != null) {
	    status = "Revoked";
    }

    return status;

}
/**
 * Insert the method's description here.
 * Creation date: (7/2/2002 10:27:03 AM)
 * @return java.lang.String
 */
public java.lang.String getTc_Finding_Other() {
  return tc_finding_other;
}
/**
 * Insert the method's description here.
 * Creation date: (7/2/2002 10:27:03 AM)
 * @return java.lang.String
 */
public java.lang.String getTc_Origin_Other() {
  return tc_origin_other;
}
/**
 * Insert the method's description here.
 * Creation date: (7/2/2002 10:27:03 AM)
 * @return java.lang.String
 */
public java.lang.String getTissue() {
	return BigrGbossData.getTissueDescription(tissue);
}
/**
 * Insert the method's description here.
 * Creation date: (4/5/2002 2:20:13 PM)
 * @return com.ardais.bigr.iltds.databeans.DateData
 */
public DateData getVerified_date() {
	return verified_date;
}

  /**
   * @return - the account of the user who created the consent
   */
  public String getConsentAccount() {
    return consentAccount;
  }

  /**
   * @return
   */
  public DateData getConsentDate() {
    return consentDate;
  }

/**
 * Insert the method's description here.
 * Creation date: (4/19/2002 11:08:36 AM)
 * @return boolean
 */
public boolean isExists() {
	return exists;
}

public boolean isLinked() {
  if ( (linked == null) || (linked.equals("N")))
      return false;
  else 
    return true;
}

/**
 * Insert the method's description here.
 * Creation date: (3/20/2002 10:45:19 AM)
 * @param newAsms java.util.Map
 */
public void removeAsm(AsmData newAsm) {
	asms.remove(newAsm);
}
/**
 * Insert the method's description here.
 * Creation date: (7/2/2002 10:34:45 AM)
 * @param newAscii_report java.lang.String
 */
public void setAscii_report(java.lang.String newAscii_report) {
	ascii_report = newAscii_report;
}
/**
 * Insert the method's description here.
 * Creation date: (3/21/2002 8:42:35 AM)
 * @param newAsms java.util.List
 */
public void setAsms(java.util.List newAsms) {
	asms = newAsms;
}
/**
 * Insert the method's description here.
 * Creation date: (3/20/2002 10:56:25 AM)
 * @param newCase_id java.lang.String
 */
public void setCase_id(java.lang.String newCase_id) {
	case_id = newCase_id;
}
/**
 * Insert the method's description here.
 * Creation date: (7/2/2002 10:16:37 AM)
 * @param newDdc_check java.lang.String
 */
public void setDdc_check(java.lang.String newDdc_check) {
	ddc_check = newDdc_check;
}
/**
 * Insert the method's description here.
 * Creation date: (7/2/2002 10:27:03 AM)
 * @param newDiagnosis java.lang.String
 */
public void setDiagnosis(java.lang.String newDiagnosis) {
	diagnosis = newDiagnosis;
}
/**
 * Insert the method's description here.
 * Creation date: (7/2/2002 10:27:03 AM)
 * @param newDiagnosis java.lang.String
 */
public void setDx_Other(java.lang.String newDxOther) {
  dx_other = newDxOther;
}
/**
 * Insert the method's description here.
 * Creation date: (3/21/2002 10:49:23 AM)
 * @param newDonor_id java.lang.String
 */
public void setDonor_id(java.lang.String newDonor_id) {
	donor_id = newDonor_id;
}
/**
 * Insert the method's description here.
 * Creation date: (6/28/2002 2:26:02 PM)
 * @param newDonor_location java.lang.String
 */
public void setDonor_location(java.lang.String newDonor_location) {
	donor_location = newDonor_location;
}
/**
 * Insert the method's description here.
 * Creation date: (4/19/2002 11:08:36 AM)
 * @param newExists boolean
 */
public void setExists(boolean newExists) {
	exists = newExists;
}
/**
 * Insert the method's description here.
 * Creation date: (3/22/2002 7:25:45 AM)
 * @param newParent com.ardais.bigr.iltds.databeans.DonorData
 */
public void setParent(DonorData newParent) {
	parent = newParent;
}
/**
 * Insert the method's description here.
 * Creation date: (3/20/2002 10:56:25 AM)
 * @param newCase_id java.lang.String
 */
public void setPath_report_id(java.lang.String newPath_report_id) {
	path_report_id = newPath_report_id;
}
public void setPolicy_name(java.lang.String newPolicy_name) {
  policy_name = newPolicy_name;
}
public void setProtocol_name(java.lang.String newProtocol_name) {
  protocol_name = newProtocol_name;
}
/**
 * Insert the method's description here.
 * Creation date: (7/2/2002 10:27:03 AM)
 * @param newProcedure java.lang.String
 */
public void setProcedure(java.lang.String newProcedure) {
	procedure = newProcedure;
}
/**
 * Insert the method's description here.
 * Creation date: (7/10/2003 10:27:03 AM)
 * @param newOtherProcedure java.lang.String
 */
public void setProc_other(java.lang.String newOtherProcedure) {
  proc_other = newOtherProcedure;
}
/**
 * Insert the method's description here.
 * Creation date: (7/2/2002 4:03:27 PM)
 * @param newPulled_date com.ardais.bigr.iltds.databeans.DateData
 */
public void setPulled_date(DateData newPulled_date) {
	pulled_date = newPulled_date;
}
/**
 * Insert the method's description here.
 * Creation date: (4/5/2002 2:20:13 PM)
 * @param newRelease_date com.ardais.bigr.iltds.databeans.DateData
 */
public void setRelease_date(DateData newRelease_date) {
	release_date = newRelease_date;
}
/**
 * Insert the method's description here.
 * Creation date: (7/2/2002 4:03:27 PM)
 * @param newRevoked_date com.ardais.bigr.iltds.databeans.DateData
 */
public void setRevoked_date(DateData newRevoked_date) {
	revoked_date = newRevoked_date;
}
/**
 * Insert the method's description here.
 * Creation date: (7/2/2002 10:27:03 AM)
 * @param newStatus java.lang.String
 */
public void setStatus(java.lang.String newStatus) {
	status = newStatus;
}
/**
 * Insert the method's description here.
 * Creation date: (7/2/2002 10:27:03 AM)
 * @param newTissue java.lang.String
 */
public void setTissue(java.lang.String newTissue) {
	tissue = newTissue;
}
/**
 * Insert the method's description here.
 * Creation date: (7/2/2002 10:27:03 AM)
 * @param newTissue java.lang.String
 */
public void setTc_Finding_Other(java.lang.String newTcFindingOther) {
  tc_finding_other = newTcFindingOther;
}
/**
 * Insert the method's description here.
 * Creation date: (7/2/2002 10:27:03 AM)
 * @param newTissue java.lang.String
 */
public void setTc_Origin_Other(java.lang.String newTcOriginOther) {
  tc_origin_other = newTcOriginOther;
}
/**
 * Insert the method's description here.
 * Creation date: (4/5/2002 2:20:13 PM)
 * @param newVerified_date com.ardais.bigr.iltds.databeans.DateData
 */
public void setVerified_date(DateData newVerified_date) {
	verified_date = newVerified_date;
}

  /**
   * @param string - the account of the user who created the consent
   */
  public void setConsentAccount(String string) {
    consentAccount = string;
  }

  /**
   * @param data
   */
  public void setConsentDate(DateData data) {
    consentDate = data;
  }

  /**
   * @return
   */
  public String getPolicy_id() {
    return policy_id;
  }

  /**
   * @param string
   */
  public void setPolicy_id(String string) {
    policy_id = string;
  }

  public String getPrefixedPolicy_id() {
    return FormLogic.makePrefixedPolicyId(policy_id);
  }

  public void setLinked(String linked) {
    this.linked = linked;
  }

  public String getLinked() {
    return linked;
  }


  /**
   * @return
   */
  public String getCustomer_id() {
    return customer_id;
  }

  /**
   * @param string
   */
  public void setCustomer_id(String string) {
    customer_id = string;
  }

  public String getAgeAtCollection() {
    String ageAtCollection = "";
    String minAge = IltdsUtils.getCaseMinAgeAtCollection(getCase_id());;
    String maxAge = IltdsUtils.getCaseMaxAgeAtCollection(getCase_id());
    String dashString = "";
  
    if (!ApiFunctions.isEmpty(minAge) && (!ApiFunctions.isEmpty(maxAge))) {
      dashString = "-";
    }
  
    if (minAge.equals(maxAge)) {
      ageAtCollection = minAge;
    }
    else {
      ageAtCollection = minAge + dashString + maxAge;
    }
  
    return ageAtCollection;  
  }

}
