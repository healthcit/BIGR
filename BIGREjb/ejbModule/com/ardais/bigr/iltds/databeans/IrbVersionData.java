package com.ardais.bigr.iltds.databeans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.util.gen.DbAliases;
import com.ardais.bigr.util.gen.DbConstants;

public class IrbVersionData implements java.io.Serializable {
  private final static long serialVersionUID = 5251682778425382953L;
  
  private String _consentVersionId = null;
  private String _consentVersionName = null;
  private String _irbProtocolId = null; 
  private String _irbProtocolName = null;
  private String _irbPolicyId = null;

  public IrbVersionData() {
    super();
  }

  /**
   * Values from ES_ARDAIS_CONSENTVER.CONSENT_VERSION_ID.
   */
  public String getConsentVersionId() {
    return _consentVersionId;
  }

  /**
   * Values from ES_ARDAIS_CONSENTVER.CONSENT_VERSION.
   */
  public String getConsentVersionName() {
    return _consentVersionName;
  }

  /**
   * Values from ES_ARDAIS_IRB.IRBPROTOCOL_ID.
   */
  public String getIrbProtocolId() {
    return _irbProtocolId;
  }

  /**
   * Values from ES_ARDAIS_IRB.IRBPROTOCOL.
   */
  public String getIrbProtocolName() {
    return _irbProtocolName;
  }

  /**
   * Values from ES_ARDAIS_IRB.POLICY_ID.
   */
  public String getIrbPolicyId() {
    return _irbPolicyId;
  }

  /**
   * Returns a concatention of the IRB protocol name and the consent version name.
   * 
   * @return the combined IRB/version name.
   */
  public String getIrbProtocolAndVersionName() {
    return ApiFunctions.safeString(getIrbProtocolName())
      + "/"
      + ApiFunctions.safeString(getConsentVersionName());
  }

  /**
   * Values from ES_ARDAIS_CONSENTVER.CONSENT_VERSION_ID.
   */
  public void setConsentVersionId(String string) {
    _consentVersionId = string;
  }

  /**
   * Values from ES_ARDAIS_CONSENTVER.CONSENT_VERSION.
   */
  public void setConsentVersionName(String string) {
    _consentVersionName = string;
  }

  /**
   * Values from ES_ARDAIS_IRB.IRBPROTOCOL_ID.
   */
  public void setIrbProtocolId(String string) {
    _irbProtocolId = string;
  }

  /**
   * Values from ES_ARDAIS_IRB.IRBPROTOCOL.
   */
  public void setIrbProtocolName(String string) {
    _irbProtocolName = string;
  }

  /**
   * Values from ES_ARDAIS_IRB.POLICY_ID.
   */
  public void setIrbPolicyId(String string) {
    _irbPolicyId = string;
  }

  /**
   * Populates this <code>IrbVersionData</code> from the data in the current row of 
   * the result set.
   * 
   * @param  columns  a <code>Map</code> containing a key for each column in
   *                   the <code>ResultSet</code>.
   * @param  rs  the <code>ResultSet</code>
   */
  public void populate(Map columns, ResultSet rs) {
    try {
      if (columns.containsKey(DbConstants.CONSENT_VERSION_ID )) {
        setConsentVersionId(rs.getString(DbConstants.CONSENT_VERSION_ID));
      }
      if (columns.containsKey(DbAliases.CONSENT_VERSION_ID)) {
        setConsentVersionId(rs.getString(DbAliases.CONSENT_VERSION_ID));
      }
      if (columns.containsKey(DbConstants.CONSENT_VERSION)) {
        setConsentVersionName(rs.getString(DbConstants.CONSENT_VERSION));
      }
      if (columns.containsKey(DbAliases.CONSENT_VERSION)) {
        setConsentVersionName(rs.getString(DbAliases.CONSENT_VERSION));
      }
      if (columns.containsKey(DbConstants.IRB_PROTOCOL_ID )) {
        setIrbProtocolId(rs.getString(DbConstants.IRB_PROTOCOL_ID));
      }
      if (columns.containsKey(DbAliases.IRB_PROTOCOL_ID)) {
        setIrbProtocolId(rs.getString(DbAliases.IRB_PROTOCOL_ID));
      }
      if (columns.containsKey(DbConstants.IRB_PROTOCOL)) {
        setIrbProtocolName(rs.getString(DbConstants.IRB_PROTOCOL));
      }
      if (columns.containsKey(DbAliases.IRB_PROTOCOL)) {
        setIrbProtocolName(rs.getString(DbAliases.IRB_PROTOCOL));
      }
      if (columns.containsKey(DbConstants.IRB_POLICY_ID)) {
        setIrbPolicyId(rs.getString(DbConstants.IRB_POLICY_ID));
      }
      if (columns.containsKey(DbAliases.IRB_POLICY_ID)) {
        setIrbPolicyId(rs.getString(DbAliases.IRB_POLICY_ID));
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

}
