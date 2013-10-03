package com.ardais.bigr.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.javabeans.PrivilegeDto;
import com.ardais.bigr.javabeans.RoleDto;
import com.ardais.bigr.javabeans.UserDto;
import com.ardais.bigr.util.gen.DbAliases;
import com.ardais.bigr.util.gen.DbConstants;

/**
 * Utility functions for role functionality.
 */
public class RoleUtils {

  /**
   * Return information about the role with the specified id.
   * 
   * @param roleId The primary key of the role.
   * @param exceptionIfNotExists If true, throw an exception if there is no role
   *     with the specified id.  If false, return null if the role doesn't exist.
   * @param resultClass The class of object to create to hold the result.  This must be
   *     {@link RoleDto} or a class that extends it.  This method only populates
   *     fields that exist on the RoleDto class itself.
   * @return the role information
   */
  public static RoleDto getRoleData(
    String roleId,
    boolean exceptionIfNotExists,
    Class resultClass) {

    RoleDto role = null;
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      StringBuffer query = new StringBuffer(256);
      query.append("SELECT * FROM ");
      query.append(DbConstants.TABLE_BIGR_ROLE);
      query.append(" ");
      query.append(DbAliases.TABLE_BIGR_ROLE);
      query.append(" WHERE ");
      query.append(DbAliases.TABLE_BIGR_ROLE);
      query.append(".");
      query.append(DbConstants.BIGR_ROLE_ID);
      query.append(" = ?");
      
      role = (RoleDto) resultClass.newInstance();

      role.setId(roleId);

      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, roleId);
      rs = ApiFunctions.queryDb(pstmt, con);
      Map columns = DbUtils.getColumnNames(rs);

      if (rs.next()) {
        role.populate(columns, rs);
        populateUsersAndPrivileges(role);
      }
      else if (exceptionIfNotExists){
        throw new ApiException("Could not retrieve role information for role_id = " + roleId);
      }
      else {
        role = null;
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return role;
  }
  
  private static void populateUsersAndPrivileges(RoleDto role) {
    String roleId = role.getId();
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    StringBuffer query = new StringBuffer(256);
    try {
      //get the users that belong to the role
      query = new StringBuffer(256);
      query.append("SELECT * FROM ");
      query.append(DbConstants.TABLE_ARDAIS_USER);
      query.append(" ");
      query.append(DbAliases.TABLE_ARDAIS_USER);
      query.append(" WHERE ");
      query.append(DbAliases.TABLE_ARDAIS_USER);
      query.append(".");
      query.append(DbConstants.ARDAIS_USER_ID);
      query.append(" IN (SELECT ");
      query.append(DbAliases.TABLE_BIGR_ROLE_USER);
      query.append(".");
      query.append(DbConstants.BIGR_ROLE_USER_USER_ID);
      query.append(" FROM ");
      query.append(DbConstants.TABLE_BIGR_ROLE_USER);
      query.append(" ");
      query.append(DbAliases.TABLE_BIGR_ROLE_USER);
      query.append(" WHERE ");
      query.append(DbAliases.TABLE_BIGR_ROLE_USER);
      query.append(".");
      query.append(DbConstants.BIGR_ROLE_USER_ROLE_ID);
      query.append(" = ?)");
      query.append(" ORDER BY ");
      query.append(DbAliases.TABLE_ARDAIS_USER);
      query.append(".");
      query.append(DbConstants.ARDAIS_USER_USER_LASTNAME);
      query.append(",");
      query.append(DbAliases.TABLE_ARDAIS_USER);
      query.append(".");
      query.append(DbConstants.ARDAIS_USER_USER_FIRSTNAME);
      
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, roleId);
      rs = ApiFunctions.queryDb(pstmt, con);
      Map columns = DbUtils.getColumnNames(rs);
      while (rs.next()) {
        UserDto user = new UserDto(columns,rs);
        role.getUsers().add(user);
      }
      ApiFunctions.close(pstmt);
      
      //get the privileges assigned to the role
      query = new StringBuffer(256);
      query.append("SELECT * FROM ARD_OBJECTS OB WHERE OBJECT_ID IN (SELECT ");
      query.append(DbAliases.TABLE_BIGR_ROLE_PRIVILEGE);
      query.append(".");
      query.append(DbConstants.BIGR_ROLE_PRIVILEGE_PRIVILEGE_ID);
      query.append(" FROM ");
      query.append(DbConstants.TABLE_BIGR_ROLE_PRIVILEGE);
      query.append(" ");
      query.append(DbAliases.TABLE_BIGR_ROLE_PRIVILEGE);
      query.append(" WHERE ");
      query.append(DbAliases.TABLE_BIGR_ROLE_PRIVILEGE);
      query.append(".");
      query.append(DbConstants.BIGR_ROLE_PRIVILEGE_ROLE_ID);
      query.append(" = ?)");
      query.append(" ORDER BY OB.OBJECT_DESCRIPTION");
      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, roleId);
      rs = ApiFunctions.queryDb(pstmt, con);
      columns = DbUtils.getColumnNames(rs);
      Collection users = new ArrayList();
      while (rs.next()) {
        PrivilegeDto privilege = new PrivilegeDto();
        privilege.setDescription(rs.getString("object_description"));
        privilege.setId(rs.getString("object_id"));
        role.getPrivileges().add(privilege);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    
  }

  /**
   * Return information about the role with the specified role id.
   * 
   * @param roleId The primary key of the role.
   * @return the role information
   */
  public static RoleDto getRoleData(String roleId) {
    return getRoleData(roleId, true, RoleDto.class);
  }

  /**
   * Return true if there is a role with the specified id.
   * 
   * @param roleId The primary key of the role.
   * @return true if there is a role with the specified id.
   */
  public static boolean isExistingRole(String roleId) {    
    RoleDto roleDto = getRoleData(roleId, false, RoleDto.class);
    return roleDto == null;
  }

  /**
   * Get a map of all roles.
   * @return the map of all roles.
   */
  public static Map getAllRoleMap() {
    Map roleMap = new HashMap();
    
    List listRoles = getAllRoles();
    Iterator roles = listRoles.iterator();
    
    while (roles.hasNext()) {
      RoleDto role = (RoleDto) roles.next();
      roleMap.put(role.getId(), role);      
    }
    return roleMap;
  }

  /**
   * Get a list of all roles, sorted by name.
   * @return the list of all roles.
   */
  public static List getAllRoles() {
    List result = new ArrayList();

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      
      // SELECT * FROM bigr_role r, es_ardais_account a
      // WHERE r.account_id = a.ARDAIS_ACCT_KEY
      // ORDER BY upper(a.ARDAIS_ACCT_COMPANY_DESC) ASC, upper(r.NAME) ASC

      StringBuffer query = new StringBuffer(256);
      query.append("SELECT * FROM ");
      query.append(DbConstants.TABLE_BIGR_ROLE);
      query.append(" ");
      query.append(DbAliases.TABLE_BIGR_ROLE);
      query.append(", ");
      query.append(DbConstants.TABLE_ARDAIS_ACCOUNT);
      query.append(" ");
      query.append(DbAliases.TABLE_ARDAIS_ACCOUNT);
      query.append(" WHERE ");
      query.append(DbAliases.TABLE_BIGR_ROLE);
      query.append(".");
      query.append(DbConstants.BIGR_ROLE_ACCOUNT_ID);
      query.append(" = ");
      query.append(DbAliases.TABLE_ARDAIS_ACCOUNT);
      query.append(".");
      query.append(DbConstants.ACCOUNT_KEY);
      query.append(" ORDER BY upper(");
      query.append(DbAliases.TABLE_ARDAIS_ACCOUNT);
      query.append(".");
      query.append(DbConstants.ACCOUNT_NAME);
      query.append(") ASC, upper(");
      query.append(DbAliases.TABLE_BIGR_ROLE);
      query.append(".");
      query.append(DbConstants.BIGR_ROLE_NAME);
      query.append(") ASC");

      pstmt = con.prepareStatement(query.toString());
      rs = ApiFunctions.queryDb(pstmt, con);
      Map columns = DbUtils.getColumnNames(rs);

      while (rs.next()) {
        RoleDto role = new RoleDto();
        role.populate(columns, rs);
        populateUsersAndPrivileges(role);
        result.add(role);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return result;
  }

  /**
   * Get a list of roles by id, sorted by name.
   * 
   * @param ids the ids of the roles to retrieve
   * 
   * @return the list of roles matching the ids passed in.
   */
  public static List getRolesByIds(List ids) {
    List result = new ArrayList();

    //if no ids were passed in then just return
    if (ids.size() < 1) {
      return result;
    }

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();

      StringBuffer query = new StringBuffer(256);
      query.append("SELECT * FROM ");
      query.append(DbConstants.TABLE_BIGR_ROLE);
      query.append(" ");
      query.append(DbAliases.TABLE_BIGR_ROLE);
      query.append(" WHERE ");
      query.append(DbAliases.TABLE_BIGR_ROLE);
      query.append(".");
      query.append(DbConstants.BIGR_ROLE_ID);
      query.append(" IN (");
      boolean addComma = false;
      for (int i = 0; i < ids.size(); i++) {
        if (addComma) {
          query.append(",");
        }
        query.append("'");
        query.append((String) ids.get(i));
        query.append("'");
        addComma = true;
      }
      query.append(")");
      query.append(" ORDER BY upper(");
      query.append(DbAliases.TABLE_BIGR_ROLE);
      query.append(".");
      query.append(DbConstants.BIGR_ROLE_NAME);
      query.append(") ASC");

      pstmt = con.prepareStatement(query.toString());
      rs = ApiFunctions.queryDb(pstmt, con);
      Map columns = DbUtils.getColumnNames(rs);

      while (rs.next()) {
        RoleDto role = new RoleDto();
        role.populate(columns, rs);
        populateUsersAndPrivileges(role);
        result.add(role);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return result;
  }
  
  /**
   * Get the roles to which the account has visiblity as a list ordered by name.
   * 
   * @param accountId the account id
   * 
   * @return a list of the visible roles.
   */
  public static List getRolesByAccountId(String accountId)
  {
    List result = new ArrayList();
    if (ApiFunctions.isEmpty(accountId)) {
      return result;
    }

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      con = ApiFunctions.getDbConnection();

      StringBuffer query = new StringBuffer(256);
      query.append("SELECT * FROM ");
      query.append(DbConstants.TABLE_BIGR_ROLE);
      query.append(" WHERE ");
      query.append(DbConstants.BIGR_ROLE_ACCOUNT_ID);
      query.append(" = ? ");
      query.append(" ORDER BY upper(");
      query.append(DbConstants.BIGR_ROLE_NAME);
      query.append(") ASC");

      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, accountId);
      rs = ApiFunctions.queryDb(pstmt, con);
      Map columns = DbUtils.getColumnNames(rs);

      while (rs.next()) {
        RoleDto role = new RoleDto();
        role.populate(columns, rs);
        populateUsersAndPrivileges(role);
        result.add(role);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    
    return result;
  }
  
  /**
   * Get the roles to which a user belongs as a list ordered by name.
   * 
   * @param user the user id
   * 
   * @return a list of the roles to which the specified user belongs.
   */
  public static List getRolesByUserId(String userId)
  {
    List result = new ArrayList();
    if (ApiFunctions.isEmpty(userId)) {
      return result;
    }

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      con = ApiFunctions.getDbConnection();

      StringBuffer query = new StringBuffer(256);
      query.append("SELECT ");
      query.append(DbAliases.TABLE_BIGR_ROLE_USER);
      query.append(".");
      query.append(DbConstants.BIGR_ROLE_USER_ROLE_ID);
      query.append(" FROM ");
      query.append(DbConstants.TABLE_BIGR_ROLE_USER);
      query.append(" ");
      query.append(DbAliases.TABLE_BIGR_ROLE_USER);
      query.append(" WHERE ");
      query.append(DbConstants.BIGR_ROLE_USER_USER_ID);
      query.append(" = ? ");

      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, userId);
      rs = ApiFunctions.queryDb(pstmt, con);
      List roleIdList = new ArrayList();

      while (rs.next()) {
        roleIdList.add(rs.getString(DbConstants.BIGR_ROLE_USER_ROLE_ID));
      }
      if(!ApiFunctions.isEmpty(roleIdList)) {
        result = getRolesByIds(roleIdList);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    
    return result;
  }

  /**
   * Get the privileges to which a user has access via the roles to which they belong
   * as a list ordered by privilege description.
   * 
   * @param user the user id
   * 
   * @return a list of the privileges to which the specified user has access via the
   * roles to which they belong.
   */
  public static List getPrivilegesViaRoleMembership(String userId) {
    List returnValue = new ArrayList();
    HashMap privileges = new HashMap();
    Iterator roleIterator = getRolesByUserId(userId).iterator();
    while (roleIterator.hasNext()) {
      Iterator privilegeIterator = ((RoleDto)roleIterator.next()).getPrivileges().iterator();
      while (privilegeIterator.hasNext()) {
        PrivilegeDto privilege = (PrivilegeDto)privilegeIterator.next();
        privileges.put(privilege.getDescription(), privilege);
      }
    }
    if (!ApiFunctions.isEmpty(privileges)) {
      //sort the privileges by description
      TreeMap sortedPrivileges = new TreeMap(privileges);
      //add each privilege to the return value
      Iterator keyIterator = sortedPrivileges.keySet().iterator();
      while (keyIterator.hasNext()) {
        String key = (String)keyIterator.next();
        returnValue.add(sortedPrivileges.get(key));
      }
    }
    return returnValue;
  }
}

