package com.ardais.bigr.javabeans;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.ardais.bigr.util.gen.DbAliases;
import com.ardais.bigr.util.gen.DbConstants;

/**
 * A Data Transfer Object that represents a box layout.
 */
public class AccountBoxLayoutDto implements Serializable {
  static final long serialVersionUID = -2879689496087738965L;

  private int _accountBoxLayoutId;
  private String _boxLayoutName;
  private String _accountId;
  private String _accountName;
  private String _boxLayoutId;

  /**
   * Creates a new BoxLayoutDto.
   */
  public AccountBoxLayoutDto() {
    super();
  }

  public void populateFromResultSet(Map columns, ResultSet rs) throws SQLException {

    if (columns.containsKey(DbConstants.ACCT_LY_ID)) {
      setAccountBoxLayoutId(rs.getInt(DbConstants.ACCT_LY_ID));
    }
    if (columns.containsKey(DbAliases.ACCT_LY_ID)) {
      setAccountBoxLayoutId(rs.getInt(DbAliases.ACCT_LY_ID));
    }
    if (columns.containsKey(DbConstants.ACCT_LY_BOX_LAYOUT_NAME)) {
      setBoxLayoutName(rs.getString(DbConstants.ACCT_LY_BOX_LAYOUT_NAME));
    }
    if (columns.containsKey(DbAliases.ACCT_LY_BOX_LAYOUT_NAME)) {
      setBoxLayoutName(rs.getString(DbAliases.ACCT_LY_BOX_LAYOUT_NAME));
    }
    if (columns.containsKey(DbConstants.ACCT_LY_ARDAIS_ACCT_KEY)) {
      setAccountId(rs.getString(DbConstants.ACCT_LY_ARDAIS_ACCT_KEY));
    }
    if (columns.containsKey(DbAliases.ACCT_LY_ARDAIS_ACCT_KEY)) {
      setAccountId(rs.getString(DbAliases.ACCT_LY_ARDAIS_ACCT_KEY));
    }
    if (columns.containsKey(DbConstants.ACCT_LY_BOX_LAYOUT_ID)) {
      setBoxLayoutId(rs.getString(DbConstants.ACCT_LY_BOX_LAYOUT_ID));
    }
    if (columns.containsKey(DbAliases.ACCT_LY_BOX_LAYOUT_ID)) {
      setBoxLayoutId(rs.getString(DbAliases.ACCT_LY_BOX_LAYOUT_ID));
    }
    if (columns.containsKey(DbConstants.ACCOUNT_NAME)) {
      setAccountName(rs.getString(DbConstants.ACCOUNT_NAME));
    }
    if (columns.containsKey(DbAliases.ACCOUNT_NAME)) {
      setAccountName(rs.getString(DbAliases.ACCOUNT_NAME));
    }
  }

  /**
   * @return
   */
  public int getAccountBoxLayoutId() {
    return _accountBoxLayoutId;
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
  public String getAccountName() {
    return _accountName;
  }

  /**
   * @return
   */
  public String getBoxLayoutId() {
    return _boxLayoutId;
  }

  /**
   * @return
   */
  public String getBoxLayoutName() {
    return _boxLayoutName;
  }

  /**
   * @param i
   */
  public void setAccountBoxLayoutId(int i) {
    _accountBoxLayoutId = i;
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
  public void setAccountName(String string) {
    _accountName = string;
  }

  /**
   * @param string
   */
  public void setBoxLayoutId(String string) {
    _boxLayoutId = string;
  }

  /**
   * @param string
   */
  public void setBoxLayoutName(String string) {
    _boxLayoutName = string;
  }

}
