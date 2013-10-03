package com.ardais.bigr.javabeans;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.util.gen.DbAliases;
import com.ardais.bigr.util.gen.DbConstants;

/**
 * A Data Transfer Object that represents a role.
 */
public class RoleDto implements Serializable {
  static final long serialVersionUID = -7444898563411597557L;
  
  private String _id;
  private String _accountId;
  private String _name;
  private List _privileges = new ArrayList();  //privs currently assigned to the role
  private List _users = new ArrayList();  //users currently in the role

  /**
   * Creates a new RoleDto.
   */
  public RoleDto() {
    super();
  }

  /**
   * Creates a new <code>RoleDto</code>, initialized from
   * the data in the current row of the result set.
   *
   * @param  columns  a <code>Map</code> containing a key for each column in
   *                   the <code>ResultSet</code>.  Each key must be one of the
   *                   column alias constants in {@link com.ardais.bigr.util.DbAliases}
   *                   and the corresponding value is ignored.
   * @param  rs  the <code>ResultSet</code>
   */
  public RoleDto(Map columns, ResultSet rs) {
    this();
    populate(columns, rs);
  }

  public void populate(Map columns, ResultSet rs) {
    try {
      if (columns.containsKey(DbConstants.BIGR_ROLE_ID)) {
        setId(rs.getString(DbConstants.BIGR_ROLE_ID));
      }
      if (columns.containsKey(DbConstants.BIGR_ROLE_NAME)) {
        setName(rs.getString(DbConstants.BIGR_ROLE_NAME));
      }
      if (columns.containsKey(DbConstants.BIGR_ROLE_ACCOUNT_ID)) {
        setAccountId(rs.getString(DbConstants.BIGR_ROLE_ACCOUNT_ID));
      }
      if (columns.containsKey(DbAliases.BIGR_ROLE_ID)) {
        setId(rs.getString(DbAliases.BIGR_ROLE_ID));
      }
      if (columns.containsKey(DbAliases.BIGR_ROLE_NAME)) {
        setName(rs.getString(DbAliases.BIGR_ROLE_NAME));
      }
      if (columns.containsKey(DbAliases.BIGR_ROLE_ACCOUNT_ID)) {
        setAccountId(rs.getString(DbAliases.BIGR_ROLE_ACCOUNT_ID));
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

  /**
   * @return Returns the accountId.
   */
  public String getAccountId() {
    return _accountId;
  }

  public String getAccountName() {
    String returnValue = "";
    if (!ApiFunctions.isEmpty(getAccountId())) {
      returnValue = IltdsUtils.getAccountName(_accountId) + " (" + _accountId + ")";
    }
    return returnValue;
  }
  
  /**
   * @return Returns the id.
   */
  public String getId() {
    return _id;
  }
  
  /**
   * @return Returns the name.
   */
  public String getName() {
    return _name;
  }
  
  /**
   * @return Returns the privileges.
   */
  public List getPrivileges() {
    return _privileges;
  }
  
  /**
   * @return Returns the users.
   */
  public List getUsers() {
    return _users;
  }
  
  /**
   * @param accountId The accountId to set.
   */
  public void setAccountId(String accountId) {
    _accountId = accountId;
  }
  
  /**
   * @param id The id to set.
   */
  public void setId(String id) {
    _id = id;
  }
  
  /**
   * @param name The name to set.
   */
  public void setName(String name) {
    _name = name;
  }
  
  /**
   * @param privileges The privileges to set.
   */
  public void setPrivileges(List privileges) {
    _privileges = privileges;
  }
  
  /**
   * @param users The users to set.
   */
  public void setUsers(List users) {
    _users = users;
  }
}
