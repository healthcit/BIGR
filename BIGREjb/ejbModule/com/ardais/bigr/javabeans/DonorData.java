package com.ardais.bigr.javabeans;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.kc.form.BigrFormInstance;
import com.ardais.bigr.kc.form.BigrFormInstanceEnabled;
import com.ardais.bigr.util.gen.DbAliases;

/**
 * Represents the raw data of a case/donor.
 */
public class DonorData implements BigrFormInstanceEnabled, Serializable {

  private String _donorId;
  private String _consentCount;
  private String _customerId;
  private String _race; // acutually holds the CUI of race
  
  private BigrFormInstance _bigrFormInstance;

  /**
   * Creates a new <code>DonorData</code>.
   */
  public DonorData() {
  }

  /**
   * Creates a new <code>DonorData</code>, initialized from
   * the data in the DonorData passed in.
   *
   * @param  donorData  a <code>DonorData</code>
   */
  public DonorData(DonorData donorData) {
    this();
    BigrBeanUtilsBean.SINGLETON.copyProperties(this, donorData);
    if (donorData.getBigrFormInstance() != null) {
      setBigrFormInstance(new BigrFormInstance(donorData.getBigrFormInstance().getKcFormInstance()));
    }
  }

  /**
   * Creates a new <code>DonorData</code>, initialized from
   * the data in the current row of the result set.
   *
   * @param  columns  a <code>Map</code> containing a key for each column in
   * 									 the <code>ResultSet</code>.  Each key must be one of the
   * 									 column alias constants in {@link com.ardais.bigr.util.DbAliases}
   * 									 and the corresponding value is ignored.
   * @param  rs  the <code>ResultSet</code>
   */
  public DonorData(Map columns, ResultSet rs) {
    this();
    populate(columns, rs);
  }

  /**
   * Populates this <code>DonorData</code> from the data in the current row
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
      if (columns.containsKey(DbAliases.DONOR_ID)) {
        setDonorId(rs.getString(DbAliases.DONOR_ID));
      }
      if (columns.containsKey(DbAliases.DONOR_CONSENT_COUNT)) {
        setConsentCount(rs.getString(DbAliases.DONOR_CONSENT_COUNT));
      }
      if (columns.containsKey(DbAliases.DONOR_CUSTOMER_ID)) {
        setCustomerId(rs.getString(DbAliases.DONOR_CUSTOMER_ID));
      }
      
      if (columns.containsKey(DbAliases.DONOR_RACE)) {
        setRace(rs.getString(DbAliases.DONOR_RACE));
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
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
   * Sets the donor ID.
   * 
   * @param  donorId  The donor ID to set.
   */
  public void setDonorId(String donorId) {
    _donorId = donorId;
  }
  /**
   * Returns the consentCount.
   * @return String
   */
  public String getConsentCount() {
    return _consentCount;
  }

  /**
   * Sets the consentCount.
   * @param consentCount The consentCount to set
   */
  public void setConsentCount(String consentCount) {
    _consentCount = consentCount;
  }

  /**
   * @return
   */
  public String getCustomerId() {
    return _customerId;
  }

  /**
   * @param string
   */
  public void setCustomerId(String string) {
    _customerId = string;
  }

  
  /**
   * Returns the race cui.
   * 
   * @return  The race cui.
   */
  public String getRace() {
    return _race;
  }

  /**
   * Sets the race cui.
   * 
   * @param  race cui  The race cui to set.
   */
  public void setRace(String race) {
    _race = race;
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

}
