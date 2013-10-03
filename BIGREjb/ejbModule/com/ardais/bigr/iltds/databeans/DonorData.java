package com.ardais.bigr.iltds.databeans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.gulfstreambio.gboss.GbossFactory;

public class DonorData implements java.io.Serializable {
  private final static long serialVersionUID = 6179442911536582702L;
  private List cases = new ArrayList();
  private String donor_id;
  private String customer_id;
  private String gender;
  private String race;
  private int _repCount;

  /**
   * DonorBean constructor comment.
   */
  public DonorData() {
    super();
  }

  /**
   * DonorBean constructor comment.
   */
  public DonorData(DonorData donorData) {
    this();
    BigrBeanUtilsBean.SINGLETON.copyProperties(this, donorData);
    //any mutable objects must be handled seperately (BigrBeanUtilsBean.copyProperties
    //does a shallow copy.
    if (!ApiFunctions.isEmpty(donorData.getCases())) {
      CaseData newCase = null;
      CaseData originalCase = null;
      cases.clear();
      Iterator iterator = donorData.getCases().iterator();
      while (iterator.hasNext()) {
        //any original cases pointing back to the original donorData need
        //to be handled specially, to prevent an infinite loop of copying.
        originalCase = (CaseData)iterator.next();
        if (donorData.equals(originalCase.getParent())) {
          newCase = new CaseData(originalCase, false);
          newCase.setParent(this);
        }
        else {
          newCase = new CaseData(originalCase);
        }
        cases.add(newCase);
      }
    }
  }
  /**
   * Insert the method's description here.
   * Creation date: (3/20/2002 11:00:36 AM)
   * @param newCases java.util.Map
   */
  public void addCase(CaseData newCase) {
    cases.add(newCase);
  }
  /**
   * Insert the method's description here.
   * Creation date: (3/21/2002 8:43:35 AM)
   * @return java.util.List
   */
  public java.util.List getCases() {
    return cases;
  }
  /**
   * Insert the method's description here.
   * Creation date: (3/20/2002 10:56:47 AM)
   * @return java.lang.String
   */
  public java.lang.String getDonor_id() {
    return donor_id;
  }
  /**
   * Insert the method's description here.
   * Creation date: (7/2/2002 11:54:06 AM)
   * @return java.lang.String
   */
  public java.lang.String getGender() {
    if (gender == null)
      return "";
    if (gender.equalsIgnoreCase("m"))
      return "Male";
    if (gender.equalsIgnoreCase("f"))
      return "Female";
    if (gender.equalsIgnoreCase("o"))
      return "Other";
    if (gender.equalsIgnoreCase("u"))
      return "Unknown";
    return "";
  }
  /**
   * Insert the method's description here.
   * Creation date: (7/2/2002 11:54:06 AM)
   * @return java.lang.String
   */
  public java.lang.String getRace() {
    return GbossFactory.getInstance().getDescription(/*"RACE",*/race);
  }
  /**
   * Insert the method's description here.
   * Creation date: (3/20/2002 11:00:36 AM)
   * @param newCases java.util.Map
   */
  public void removeCase(CaseData newCase) {
    cases.remove(newCase);
  }
  /**
   * Insert the method's description here.
   * Creation date: (3/21/2002 8:43:35 AM)
   * @param newCases java.util.List
   */
  public void setCases(java.util.List newCases) {
    cases = newCases;
  }
  /**
   * Insert the method's description here.
   * Creation date: (3/20/2002 10:56:47 AM)
   * @param newDonor_id java.lang.String
   */
  public void setDonor_id(java.lang.String newDonor_id) {
    donor_id = newDonor_id;
  }
  /**
   * Insert the method's description here.
   * Creation date: (7/2/2002 11:54:06 AM)
   * @param newGender java.lang.String
   */
  public void setGender(java.lang.String newGender) {
    gender = newGender;
  }
  /**
   * Insert the method's description here.
   * Creation date: (7/2/2002 11:54:06 AM)
   * @param newRace java.lang.String
   */
  public void setRace(java.lang.String newRace) {
    race = newRace;
  }

  /**
   * Returns the repCount.
   * @return String
   */
  public int getRepCount() {
    return _repCount;
  }

  /**
   * Sets the repCount.
   * @param repCount The repCount to set
   */
  public void setRepCount(int repCount) {
    _repCount = repCount;
  }

  public String getRepeatDonorDisplay() {
    if (_repCount < 2) {
      return "No";
    }

    return _repCount + " cases";
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

}
