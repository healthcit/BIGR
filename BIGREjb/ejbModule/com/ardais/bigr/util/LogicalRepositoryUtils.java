/*
 * Created on Nov 24, 2003
 */
package com.ardais.bigr.util;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.assistants.LogicalRepository;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.gen.DbAliases;
import com.ardais.bigr.util.gen.DbConstants;

/**
 * @author jesielionis
 *
 * Utility functions for logical repository functionality
 */
public class LogicalRepositoryUtils {

  public static final Comparator FULL_NAME_ORDER = new LogicalRepositoryFullNameComparator();
  public static final Comparator SHORT_NAME_ORDER = new LogicalRepositoryShortNameComparator();

  private static class LogicalRepositoryFullNameComparator implements Comparator, Serializable {
    private static final long serialVersionUID = 8575799808977634451L;
    public int compare(Object o1, Object o2) {
      try {
        LogicalRepository lr1 = (LogicalRepository) o1;
        LogicalRepository lr2 = (LogicalRepository) o2;
        String lr1Name = lr1.getFullName();
        String lr2Name = lr2.getFullName();
        return lr1Name.compareToIgnoreCase(lr2Name);
      }
      catch (ClassCastException cce) {
        ApiFunctions.throwAsRuntimeException(cce);
        return 0; // unreached, keep compiler happy
      }
    }
  }

  private static class LogicalRepositoryShortNameComparator implements Comparator, Serializable {
    private static final long serialVersionUID = 8575799808977634467L;
    public int compare(Object o1, Object o2) {
      try {
        LogicalRepository lr1 = (LogicalRepository) o1;
        LogicalRepository lr2 = (LogicalRepository) o2;
        String lr1Name = lr1.getShortName();
        String lr2Name = lr2.getShortName();
        return lr1Name.compareToIgnoreCase(lr2Name);
      }
      catch (ClassCastException cce) {
        ApiFunctions.throwAsRuntimeException(cce);
        return 0; // unreached, keep compiler happy
      }
    }
  }

  /**
   * Get a list of all logical repositories, sorted by name.
   * @return the list of all logical repositories.
   */
  public static List getAllLogicalRepositories() {
    List result = new ArrayList();

    ResultSet rs = null;
    PreparedStatement pstmt = null;
    Connection con = null;
    try {
      con = ApiFunctions.getDbConnection();
      StringBuffer query = new StringBuffer(256);
      query.append("SELECT ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS);
      query.append(".");
      query.append(DbConstants.LOGICAL_REPOS_ID);
      query.append(" ");
      query.append(DbAliases.LOGICAL_REPOS_ID);
      query.append(", ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS);
      query.append(".");
      query.append(DbConstants.LOGICAL_REPOS_FULL_NAME);
      query.append(" ");
      query.append(DbAliases.LOGICAL_REPOS_FULL_NAME);
      query.append(", ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS);
      query.append(".");
      query.append(DbConstants.LOGICAL_REPOS_SHORT_NAME);
      query.append(" ");
      query.append(DbAliases.LOGICAL_REPOS_SHORT_NAME);
      query.append(", ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS);
      query.append(".");
      query.append(DbConstants.LOGICAL_REPOS_BMS_YN);
      query.append(" ");
      query.append(DbAliases.LOGICAL_REPOS_BMS_YN);
      query.append(" FROM ");
      query.append(DbConstants.TABLE_LOGICAL_REPOS);
      query.append(" ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS);
      query.append(" ORDER BY upper(");
      query.append(DbAliases.TABLE_LOGICAL_REPOS);
      query.append(".");
      query.append(DbConstants.LOGICAL_REPOS_FULL_NAME);
      query.append(") ASC");
      pstmt = con.prepareStatement(query.toString());
      rs = ApiFunctions.queryDb(pstmt, con);

      while (rs.next()) {
        LogicalRepository lr = new LogicalRepository();
        lr.setId(rs.getString(1));
        lr.setFullName(rs.getString(2));
        lr.setShortName(rs.getString(3));
        lr.setBmsYN(rs.getString(4));
        result.add(lr);
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
   * Get a list of logical repositories by id, sorted by long name.
   * 
   * @param ids the ids of the logical repositories to retrieve
   * 
   * @return the list of logical repositories matching the ids passed in.
   */
  public static List getLogicalRepositoriesById(List ids) {
    List result = new ArrayList();

    //if no ids were passed in then just return
    if (ids.size() < 1) {
      return result;
    }

    ResultSet rs = null;
    PreparedStatement pstmt = null;
    Connection con = null;
    try {
      con = ApiFunctions.getDbConnection();
      StringBuffer query = new StringBuffer(256);
      query.append("SELECT ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS);
      query.append(".");
      query.append(DbConstants.LOGICAL_REPOS_ID);
      query.append(" ");
      query.append(DbAliases.LOGICAL_REPOS_ID);
      query.append(", ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS);
      query.append(".");
      query.append(DbConstants.LOGICAL_REPOS_FULL_NAME);
      query.append(" ");
      query.append(DbAliases.LOGICAL_REPOS_FULL_NAME);
      query.append(", ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS);
      query.append(".");
      query.append(DbConstants.LOGICAL_REPOS_SHORT_NAME);
      query.append(" ");
      query.append(DbAliases.LOGICAL_REPOS_SHORT_NAME);
      query.append(", ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS);
      query.append(".");
      query.append(DbConstants.LOGICAL_REPOS_BMS_YN);
      query.append(" ");
      query.append(DbAliases.LOGICAL_REPOS_BMS_YN);
      query.append(" FROM ");
      query.append(DbConstants.TABLE_LOGICAL_REPOS);
      query.append(" ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS);
      query.append(" WHERE ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS);
      query.append(".");
      query.append(DbConstants.LOGICAL_REPOS_ID);
      query.append(" IN (");
      boolean addComma = false;
      for (int i = 0; i < ids.size(); i++) {
        if (addComma) {
          query.append(",");
        }
        query.append((String) ids.get(i));
        addComma = true;
      }
      query.append(")");
      query.append(" ORDER BY upper(");
      query.append(DbAliases.TABLE_LOGICAL_REPOS);
      query.append(".");
      query.append(DbConstants.LOGICAL_REPOS_FULL_NAME);
      query.append(") ASC");

      pstmt = con.prepareStatement(query.toString());
      rs = ApiFunctions.queryDb(pstmt, con);

      while (rs.next()) {
        LogicalRepository lr = new LogicalRepository();
        lr.setId(rs.getString(1));
        lr.setFullName(rs.getString(2));
        lr.setShortName(rs.getString(3));
        lr.setBmsYN(rs.getString(4));
        result.add(lr);
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
   * Get the logical repositories to which the user has visiblity as an unordered set.
   * 
   * @param userid the user id
   * 
   * @return a set of the visible logical repositories.
   */
  public static Set getLogicalRepositoriesForUser(String userid) {
    Set result = new HashSet();

    ResultSet rs = null;
    PreparedStatement pstmt = null;
    Connection con = null;
    try {
      con = ApiFunctions.getDbConnection();
      StringBuffer query = new StringBuffer(256);
      query.append("SELECT ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS);
      query.append(".");
      query.append(DbConstants.LOGICAL_REPOS_ID);
      query.append(" ");
      query.append(DbAliases.LOGICAL_REPOS_ID);
      query.append(", ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS);
      query.append(".");
      query.append(DbConstants.LOGICAL_REPOS_FULL_NAME);
      query.append(" ");
      query.append(DbAliases.LOGICAL_REPOS_FULL_NAME);
      query.append(", ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS);
      query.append(".");
      query.append(DbConstants.LOGICAL_REPOS_SHORT_NAME);
      query.append(" ");
      query.append(DbAliases.LOGICAL_REPOS_SHORT_NAME);
      query.append(", ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS);
      query.append(".");
      query.append(DbConstants.LOGICAL_REPOS_BMS_YN);
      query.append(" ");
      query.append(DbAliases.LOGICAL_REPOS_BMS_YN);
      query.append(" FROM ");
      query.append(DbConstants.TABLE_LOGICAL_REPOS);
      query.append(" ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS);
      query.append(", ");
      query.append(DbConstants.TABLE_LOGICAL_REPOS_USER);
      query.append(" ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS_USER);
      query.append(" WHERE ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS_USER);
      query.append(".");
      query.append(DbConstants.LOGICAL_REPOS_USER_USER_ID);
      query.append(" = ? AND ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS_USER);
      query.append(".");
      query.append(DbConstants.LOGICAL_REPOS_USER_REPOSITORY_ID);
      query.append(" = ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS);
      query.append(".");
      query.append(DbConstants.LOGICAL_REPOS_ID);
      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, userid);
      rs = ApiFunctions.queryDb(pstmt, con);

      while (rs.next()) {
        LogicalRepository lr = new LogicalRepository();
        lr.setId(rs.getString(1));
        lr.setFullName(rs.getString(2));
        lr.setShortName(rs.getString(3));
        lr.setBmsYN(rs.getString(4));
        result.add(lr);
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
   * Get the logical repositories to which the user has visiblity as a list ordered by long name.
   * 
   * @param userid the user id
   * 
   * @return a list of the visible logical repositories.
   */
  public static List getLogicalRepositoriesForUserByFullName(String userid) {
    List result = new ArrayList();

    ResultSet rs = null;
    PreparedStatement pstmt = null;
    Connection con = null;
    try {
      con = ApiFunctions.getDbConnection();
      StringBuffer query = new StringBuffer(256);
      query.append("SELECT ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS);
      query.append(".");
      query.append(DbConstants.LOGICAL_REPOS_ID);
      query.append(" ");
      query.append(DbAliases.LOGICAL_REPOS_ID);
      query.append(", ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS);
      query.append(".");
      query.append(DbConstants.LOGICAL_REPOS_FULL_NAME);
      query.append(" ");
      query.append(DbAliases.LOGICAL_REPOS_FULL_NAME);
      query.append(", ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS);
      query.append(".");
      query.append(DbConstants.LOGICAL_REPOS_SHORT_NAME);
      query.append(" ");
      query.append(DbAliases.LOGICAL_REPOS_SHORT_NAME);
      query.append(", ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS);
      query.append(".");
      query.append(DbConstants.LOGICAL_REPOS_BMS_YN);
      query.append(" ");
      query.append(DbAliases.LOGICAL_REPOS_BMS_YN);
      query.append(" FROM ");
      query.append(DbConstants.TABLE_LOGICAL_REPOS);
      query.append(" ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS);
      query.append(", ");
      query.append(DbConstants.TABLE_LOGICAL_REPOS_USER);
      query.append(" ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS_USER);
      query.append(" WHERE ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS_USER);
      query.append(".");
      query.append(DbConstants.LOGICAL_REPOS_USER_USER_ID);
      query.append(" = ? AND ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS_USER);
      query.append(".");
      query.append(DbConstants.LOGICAL_REPOS_USER_REPOSITORY_ID);
      query.append(" = ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS);
      query.append(".");
      query.append(DbConstants.LOGICAL_REPOS_ID);
      query.append(" ORDER BY upper(");
      query.append(DbAliases.TABLE_LOGICAL_REPOS);
      query.append(".");
      query.append(DbConstants.LOGICAL_REPOS_FULL_NAME);
      query.append(") ASC");
      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, userid);
      rs = ApiFunctions.queryDb(pstmt, con);

      while (rs.next()) {
        LogicalRepository lr = new LogicalRepository();
        lr.setId(rs.getString(1));
        lr.setFullName(rs.getString(2));
        lr.setShortName(rs.getString(3));
        lr.setBmsYN(rs.getString(4));
        result.add(lr);
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
   * Get the logical repositories to which a sample belongs, sorted by name.
   * 
   * @param userid the user id
   * 
   * @return a list of the visible logical repositories.
   */
  public static List getLogicalRepositoriesForSample(String sampleId, SecurityInfo securityInfo) {
    List result = new ArrayList();

    ResultSet rs = null;
    PreparedStatement pstmt = null;
    Connection con = null;
    try {
      con = ApiFunctions.getDbConnection();
      StringBuffer query = new StringBuffer(256);
      query.append("SELECT ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS);
      query.append(".");
      query.append(DbConstants.LOGICAL_REPOS_ID);
      query.append(" ");
      query.append(DbAliases.LOGICAL_REPOS_ID);
      query.append(", ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS);
      query.append(".");
      query.append(DbConstants.LOGICAL_REPOS_FULL_NAME);
      query.append(" ");
      query.append(DbAliases.LOGICAL_REPOS_FULL_NAME);
      query.append(", ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS);
      query.append(".");
      query.append(DbConstants.LOGICAL_REPOS_SHORT_NAME);
      query.append(" ");
      query.append(DbAliases.LOGICAL_REPOS_SHORT_NAME);
      query.append(", ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS);
      query.append(".");
      query.append(DbConstants.LOGICAL_REPOS_BMS_YN);
      query.append(" ");
      query.append(DbAliases.LOGICAL_REPOS_BMS_YN);
      query.append(" FROM ");
      query.append(DbConstants.TABLE_LOGICAL_REPOS);
      query.append(" ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS);
      query.append(", ");
      query.append(DbConstants.TABLE_LOGICAL_REPOS_ITEM);
      query.append(" ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS_ITEM);
      query.append(" WHERE ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS_ITEM);
      query.append(".");
      query.append(DbConstants.LOGICAL_REPOS_ITEM_ITEM_ID);
      query.append(" = ? AND ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS_ITEM);
      query.append(".");
      query.append(DbConstants.LOGICAL_REPOS_ITEM_REPOSITORY_ID);
      query.append(" = ");
      query.append(DbAliases.TABLE_LOGICAL_REPOS);
      query.append(".");
      query.append(DbConstants.LOGICAL_REPOS_ID);
      //if a SecurityInfo object was passed in, have this query only return
      //logical repositories to which the user has been granted access if the
      //user doesn't have the "View All Logical Repositories" privilege
      if (securityInfo != null
        && !securityInfo.isHasPrivilege(SecurityInfo.PRIV_VIEW_ALL_LOGICAL_REPOS)) {
        query.append(" AND ");
        query.append(DbAliases.TABLE_LOGICAL_REPOS);
        query.append(".");
        query.append(DbConstants.LOGICAL_REPOS_ID);
        query.append(" IN (");
        query.append(
          LogicalRepositoryUtils.getLogicalRepositoryIdsForSql(
            securityInfo.getLogicalRepositories()));
        query.append(")");
      }
      query.append(" ORDER BY upper(");
      query.append(DbAliases.TABLE_LOGICAL_REPOS);
      query.append(".");
      query.append(DbConstants.LOGICAL_REPOS_FULL_NAME);
      query.append(") ASC");
      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, sampleId);
      rs = ApiFunctions.queryDb(pstmt, con);
      while (rs.next()) {
        LogicalRepository lr = new LogicalRepository();
        lr.setId(rs.getString(1));
        lr.setFullName(rs.getString(2));
        lr.setShortName(rs.getString(3));
        lr.setBmsYN(rs.getString(4));
        result.add(lr);
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
   * Given a list of inventory groups, return a list of those that are accessible by the user.
   * 
   * @param securityInfo   the securityInfo for the user
   * @param inventoryGroups   a list of LogicalRepository objects
   * 
   * @return a list of the accessible logical repositories.
   */
  public static List getInventoryGroupsVisibleToUser(SecurityInfo securityInfo, List inventoryGroups) {
    //First, turn the list of inventory groups into a set of inventory groups
    Set inventoryGroupSet = new HashSet();
    if (!ApiFunctions.isEmpty(inventoryGroups)) {
      Iterator igIterator = inventoryGroups.iterator();
      while (igIterator.hasNext()) {
        LogicalRepository inventoryGroup = (LogicalRepository)igIterator.next();
        inventoryGroupSet.add(inventoryGroup);
      }
      //now, if the user doesn't have access to all inventory groups remove any to which
      //they do not have access
      if (!securityInfo.isHasPrivilege(SecurityInfo.PRIV_VIEW_ALL_LOGICAL_REPOS)) {
        Set accessibleInventoryGroups = securityInfo.getLogicalRepositories();
        inventoryGroupSet.retainAll(accessibleInventoryGroups);
      }
    }
    return new ArrayList(inventoryGroupSet);
  }

  /** 
   * Return a comma seperated list of logical repository ids given a collection of LogicalRepository
   * objects whose getId() properties contain the ids, quoted in a format suitable for
   * inclusion in an SQL statement.
   * 
   * @param repositories the collection of logical repositories.
   * @return the id list string.
   */
  public static String getLogicalRepositoryIdsForSql(Collection repositories) {
    if (ApiFunctions.isEmpty(repositories)) {
      return "''";
    }
    Iterator iterator = repositories.iterator();
    StringBuffer sb = new StringBuffer();
    boolean includeComma = false;
    while (iterator.hasNext()) {
      if (includeComma) {
        sb.append(", ");
      }
      else {
        includeComma = true;
      }
      sb.append("'");
      sb.append(((LogicalRepository) iterator.next()).getId());
      sb.append("'");
    }
    return sb.toString();
  }

  /**
   * The logical repository id as stored in the database is just a number.  In some situations
   * we need a prefixed, fixed-length id -- for example to have an identifiable id type
   * that can be returned by getDirectlyInvolvedObjects() to index a transaction.
   * This function returns the repository id in that form (or null/empty if the repository id is
   * null/empty). 
   * 
   * @return the list of prefixed logical repository ids.
   */
  public static IdList makePrefixedLogicalRepositoryIds(IdList reposIds) {

    if (reposIds == null || reposIds.size() == 0) {
      return reposIds;
    }

    IdList prefixedReposIds = new IdList();
    Iterator iter = reposIds.iterator();
    while (iter.hasNext()) {
      String id = (String) iter.next();
      prefixedReposIds.add(FormLogic.makePrefixedLogicalRepositoryId(id));
    }

    return prefixedReposIds;
  }
}
