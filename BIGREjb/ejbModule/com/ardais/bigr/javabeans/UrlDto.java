/*
 * Created on Oct 28, 2004
 */
package com.ardais.bigr.javabeans;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.util.gen.DbAliases;
import com.ardais.bigr.util.gen.DbConstants;

/**
 * @author jesielionis
 *
 * Represents the raw data of a Url definition
 */

public class UrlDto implements Serializable{

  private String _urlId = null;
  private String _accountId = null;
  private String _accountName = null;
  private String _objectType = null;
  private String _url = null;
  private String _label = null;
  private String _target = null;
  
  public UrlDto() {
    super();
  }
  
  /**
   * Populates this <code>UrlDto</code> from the data in the current row of 
   * the result set.
   * 
   * @param  columns  a <code>Map</code> containing a key for each column in
   *                   the <code>ResultSet</code>.
   * @param  rs  the <code>ResultSet</code>
   */
  public void populate(Map columns, ResultSet rs) {
    try {
      if (columns.containsKey(DbConstants.ARD_URLS_ID)) {
        setUrlId(rs.getString(DbConstants.ARD_URLS_ID));
      }
      if (columns.containsKey(DbAliases.ARD_URLS_ID)) {
        setUrlId(rs.getString(DbAliases.ARD_URLS_ID));
      }
      if (columns.containsKey(DbConstants.ARD_URLS_URL)) {
        setUrl(rs.getString(DbConstants.ARD_URLS_URL));
      }
      if (columns.containsKey(DbAliases.ARD_URLS_URL)) {
        setUrl(rs.getString(DbAliases.ARD_URLS_URL));
      }
      if (columns.containsKey(DbConstants.ARD_URLS_TARGET)) {
        setTarget(rs.getString(DbConstants.ARD_URLS_TARGET));
      }
      if (columns.containsKey(DbAliases.ARD_URLS_TARGET)) {
        setTarget(rs.getString(DbAliases.ARD_URLS_TARGET));
      }
      if (columns.containsKey(DbConstants.ARD_URLS_OBJECT_TYPE)) {
        setObjectType(rs.getString(DbConstants.ARD_URLS_OBJECT_TYPE));
      }
      if (columns.containsKey(DbAliases.ARD_URLS_OBJECT_TYPE)) {
        setObjectType(rs.getString(DbAliases.ARD_URLS_OBJECT_TYPE));
      }
      if (columns.containsKey(DbConstants.ARD_URLS_LABEL)) {
        setLabel(rs.getString(DbConstants.ARD_URLS_LABEL));
      }
      if (columns.containsKey(DbAliases.ARD_URLS_LABEL)) {
        setLabel(rs.getString(DbAliases.ARD_URLS_LABEL));
      }
      if (columns.containsKey(DbConstants.ARD_URLS_ACCT_KEY)) {
        setAccountId(rs.getString(DbConstants.ARD_URLS_ACCT_KEY));
        setAccountName(IltdsUtils.getAccountName(getAccountId()));
      }
      if (columns.containsKey(DbAliases.ARD_URLS_ACCT_KEY)) {
        setAccountId(rs.getString(DbAliases.ARD_URLS_ACCT_KEY));
        setAccountName(IltdsUtils.getAccountName(getAccountId()));
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }
  
  /**
   * @return
   */
  public String getAccountId() {
    return _accountId;
  }

  /**
   * @return
   */
  public String getLabel() {
    return _label;
  }

  /**
   * @return
   */
  public String getObjectType() {
    return _objectType;
  }

  /**
   * @return
   */
  public String getTarget() {
    return _target;
  }

  /**
   * @return
   */
  public String getUrl() {
    return _url;
  }

  /**
   * @return
   */
  public String getUrlId() {
    return _urlId;
  }

  /**
   * @param string
   */
  public void setAccountId(String string) {
    _accountId = string;
  }

  /**
   * @param string
   */
  public void setLabel(String string) {
    _label = string;
  }

  /**
   * @param string
   */
  public void setObjectType(String string) {
    _objectType = string;
  }

  /**
   * @param string
   */
  public void setTarget(String string) {
    _target = string;
  }

  /**
   * @param string
   */
  public void setUrl(String string) {
    _url = string;
  }

  /**
   * @param string
   */
  public void setUrlId(String string) {
    _urlId = string;
  }

  /**
   * @return
   */
  public String getAccountName() {
    return _accountName;
  }

  /**
   * @param string
   */
  public void setAccountName(String string) {
    _accountName = string;
  }

}
