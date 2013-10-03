package com.ardais.bigr.pdc.javabeans;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.gulfstreambio.kc.form.FormInstance;
import com.gulfstreambio.kc.form.def.FormDefinition;

/**
 * This class represents donor data.
 */
public class DonorData implements Serializable {
  private String _ardaisId;
  private String _yyyyDob;
  private String _gender;
  private String _ethnicCategory;
  private String _race;
  private String _zipCode;
  private String _countryOfBirth;
  private String _donorProfileNotes;
  private String _createUser;
  private String _lastUpdateUser;
  private String _customerId;
  private String _importedYN = "N"; //default to N
  private String _ardaisAccountKey;
  private String _registrationFormId;
  private boolean _exists = false;
  
  private FormInstance _formInstance;
  private FormDefinition _registrationForm;

  // The list of consents for the donor.  Each item in the
  // list is a ConsentData bean.
  private List _consents;

  /**
   * Create a new <code>DonorData</code>.
   */
  public DonorData() {
    super();
  }
  /**
   * Create a new <code>DonorData</code>.
   */
  public DonorData(ResultSet rs) {
    this();
    try {
      ResultSetMetaData meta = rs.getMetaData();
      int columnCount = meta.getColumnCount();
      HashMap lookup = new HashMap();

      for (int i = 0; i < columnCount; i++) {
        lookup.put(meta.getColumnName(i + 1).toLowerCase(), null);
      }

      if (lookup.containsKey("ardais_id"))
        setArdaisId(rs.getString("ardais_id"));

      if (lookup.containsKey("yyyy_dob"))
        setYyyyDob(rs.getString("yyyy_dob"));

      if (lookup.containsKey("gender"))
        setGender(rs.getString("gender"));

      if (lookup.containsKey("ethnic_category"))
        setEthnicCategory(rs.getString("ethnic_category"));

      if (lookup.containsKey("race"))
        setRace(rs.getString("race"));

      if (lookup.containsKey("zip_code"))
        setZipCode(rs.getString("zip_code"));

      if (lookup.containsKey("country_of_birth"))
        setCountryOfBirth(rs.getString("country_of_birth"));

      if (lookup.containsKey("donor_profile_notes"))
        setDonorProfileNotes(ApiFunctions.getStringFromClob(rs.getClob("donor_profile_notes")));

      if (lookup.containsKey("imported_yn"))
        setImportedYN(rs.getString("imported_yn"));

      if (lookup.containsKey("ardais_acct_key"))
        setArdaisAccountKey(rs.getString("ardais_acct_key"));

      if (lookup.containsKey("customer_id"))
        setCustomerId(rs.getString("customer_id"));

      if (lookup.containsKey("donor_registration_form"))
        setRegistrationFormId(rs.getString("donor_registration_form"));

    }
    catch (SQLException e) {
      throw new ApiException(e);
    }
  }
  /**
   * Adds a <code>ConsentData</code> bean to this <code>DonorData</code> bean.
   * The consent is added to the end of an ordered list.
   *
   * @param  dataBean  the <code>ConsentData</code> bean
   */
  public void addConsent(ConsentData dataBean) {
    if (_consents == null)
      _consents = new ArrayList();
    _consents.add(dataBean);
  }  

  /**
   * @return
   */
  public String getArdaisAccountKey() {
    return _ardaisAccountKey;
  }

  /**
   * Returns this donor's id.
   *
   * @return  The donor (Ardais) id.
   */
  public String getArdaisId() {
    return _ardaisId;
  }
  /**
   * Returns the list of consents (<code>ConsentData</code> beans) that were
   * added to this <code>DonorData</code> bean.  If there are no consents,
   * an empty list is returned.
   *
   * @return  The list of <code>ConsentData</code> beans.
   */
  public List getConsents() {
    if (_consents == null)
      _consents = new ArrayList();
    return _consents;
  }
  /**
   * Returns this donor's country_of_birth.
   *
   * @return  The donor country_of_birth.
   */
  public String getCountryOfBirth() {
    return _countryOfBirth;
  }
  /**
   * Insert the method's description here.
   * Creation date: (7/23/2002 4:00:28 PM)
   * @return java.lang.String
   */
  public String getCreateUser() {
    return _createUser;
  }

  /**
   * @return
   */
  public String getCustomerId() {
    return _customerId;
  }

  /**
   * Insert the method's description here.
   * Creation date: (7/23/2002 3:39:47 PM)
   * @return java.lang.String
   */
  public String getDonorProfileNotes() {
    return _donorProfileNotes;
  }

  /**
   * Returns this donor's ethnic_category.
   *
   * @return  The donor ethnic_category.
   */
  public String getEthnicCategory() {
    return _ethnicCategory;
  }
  
  public boolean isExists() {
    return _exists;
  }
  
  /**
       * Returns this donor's gender.
       *
       * @return  The donor gender.
       */
  public String getGender() {
    return _gender;
  }

  /**
   * @return
   */
  public String getImportedYN() {
    return _importedYN;
  }

  /**
   * Insert the method's description here.
   * Creation date: (7/23/2002 4:03:18 PM)
   * @return java.lang.String
   */
  public String getLastUpdateUser() {
    return _lastUpdateUser;
  }
  /**
   * Returns this donor's race.
   *
   * @return  The donor race.
   */
  public String getRace() {
    return _race;
  }
  
  /**
   * Returns this donor's year of birth.
   *
   * @return  The donor year og birth.
   */
  public String getYyyyDob() {
    return _yyyyDob;
  }
  /**
   * Returns this donor's zip_code.
   *
   * @return  The donor zip_code.
   */
  public String getZipCode() {
    return _zipCode;
  }

  /**
   * @param string
   */
  public void setArdaisAccountKey(String string) {
    _ardaisAccountKey = string;
  }

  /**
   * Sets this donor's (Ardais) id.
   *
   * @param id  The donor's (Ardais) id.
   */
  public void setArdaisId(String id) {
    _ardaisId = id;
  }
  /**
   * Sets this donor's consents.
   *
   * @param consents  The donor's consents.
   */
  public void setConsents(List consents) {
    _consents = consents;
  }
  /**
   * Sets this donor's country_of_birth.
   *
   * @param country_of_birth  The donor's country_of_birth.
   */
  public void setCountryOfBirth(String countryOfBirth) {
    _countryOfBirth = countryOfBirth;
  }
  /**
   * Insert the method's description here.
   * Creation date: (7/23/2002 3:59:43 PM)
   * @param user java.lang.String
   */
  public void setCreateUser(String user) {
    _createUser = user;
  }

  /**
   * @param string
   */
  public void setCustomerId(String string) {
    _customerId = string;
  }

  /**
   * Insert the method's description here.
   * Creation date: (7/22/2002 12:56:58 PM)
   */
  public void setDonorProfileNotes(String notes) {
    _donorProfileNotes = notes;

  }

  /**
   * Sets this donor's ethnic_category.
   *
   * @param ethnic_category  The donor's ethnic_category.
   */
  public void setEthnicCategory(String ethnicCategory) {
    _ethnicCategory = ethnicCategory;
  }
  
  /**
   * @param donorExists
   */
  public void setExists(boolean donorExists) {
    _exists = donorExists;
  }

  /**
   * Sets this donor's gender.
   *
   * @param gender  The donor's gender.
   */
  public void setGender(String gender) {
    _gender = gender;
  }

  /**
   * @param string
   */
  public void setImportedYN(String string) {
    _importedYN = string;
  }

  /**
   * Insert the method's description here.
   * Creation date: (7/23/2002 4:02:06 PM)
   * @param user java.lang.String
   */
  public void setLastUpdateUser(String user) {
    _lastUpdateUser = user;
  }
  /**
   * Sets this donor's race.
   *
   * @param race  The donor's race.
   */
  public void setRace(String race) {
    _race = race;
  }
 
  /**
   * Sets this donor's year of birth.
   *
   * @param  yyyy_dob  the donor year of birth
   */
  public void setYyyyDob(String yyyyDob) {
    _yyyyDob = yyyyDob;
  }
  /**
   * Sets this donor's zip_code.
   *
   * @param zip_code  The donor's zip_code.
   */
  public void setZipCode(String zipCode) {
    _zipCode = zipCode;
  }
  
  public String getRegistrationFormId() {
    return _registrationFormId;
  }
  
  public void setRegistrationFormId(String id) {
    _registrationFormId = id;
  }

  public FormInstance getFormInstance() {
    return _formInstance;
  }
  public void setFormInstance(FormInstance formInstance) {
    _formInstance = formInstance;
  }

  public FormDefinition getRegistrationForm() {
    return _registrationForm;
  }
  public void setRegistrationForm(FormDefinition registrationForm) {
    _registrationForm = registrationForm;
  }
}
