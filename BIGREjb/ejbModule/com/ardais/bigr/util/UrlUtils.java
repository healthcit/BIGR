/*
 * Created on Oct 28, 2004
 *
 */
package com.ardais.bigr.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.javabeans.UrlDto;
import com.ardais.bigr.util.gen.DbAliases;
import com.ardais.bigr.util.gen.DbConstants;

/**
 * @author jesielionis
 *
 * Utility functions for url functionality.
 */

public class UrlUtils {

  public static final int URL_MAX_LENGTH = 4000;
  public static final int LABEL_MAX_LENGTH = 100;
  public static final int TARGET_MAX_LENGTH = 50;
  public static final String INSERTION_STRING_DELIMITER = "$$";
  public static final String INSERTION_STRING_DONOR_ALIAS = INSERTION_STRING_DELIMITER + "donor_alias" + INSERTION_STRING_DELIMITER;
  public static final String INSERTION_STRING_DONOR_ID = INSERTION_STRING_DELIMITER + "donor_id" + INSERTION_STRING_DELIMITER;
  public static final String INSERTION_STRING_CASE_ALIAS = INSERTION_STRING_DELIMITER + "case_alias" + INSERTION_STRING_DELIMITER;
  public static final String INSERTION_STRING_CASE_ID = INSERTION_STRING_DELIMITER + "case_id" + INSERTION_STRING_DELIMITER;
  public static final String INSERTION_STRING_SAMPLE_ALIAS = INSERTION_STRING_DELIMITER + "sample_alias" + INSERTION_STRING_DELIMITER;
  public static final String INSERTION_STRING_SAMPLE_ID = INSERTION_STRING_DELIMITER + "sample_id" + INSERTION_STRING_DELIMITER;
  public static final List VALID_INSERTION_STRINGS;
  public static final String OBJECT_TYPE_DONOR = "donor";
  public static final String OBJECT_TYPE_CASE = "case";
  public static final String OBJECT_TYPE_SAMPLE = "sample";
  public static final String OBJECT_TYPE_MENU = "menu";
  public static final List VALID_OBJECT_TYPES;
  public static final Comparator LABEL_ORDER = new UrlLabelComparator();
  
  static {
    List temp = new ArrayList();
    temp.add(INSERTION_STRING_DONOR_ALIAS);
    temp.add(INSERTION_STRING_DONOR_ID);
    temp.add(INSERTION_STRING_CASE_ALIAS);
    temp.add(INSERTION_STRING_CASE_ID);
    temp.add(INSERTION_STRING_SAMPLE_ALIAS);
    temp.add(INSERTION_STRING_SAMPLE_ID);
    VALID_INSERTION_STRINGS = Collections.unmodifiableList(temp);
    temp = new ArrayList();
    temp.add(OBJECT_TYPE_DONOR);
    temp.add(OBJECT_TYPE_CASE);
    temp.add(OBJECT_TYPE_SAMPLE);
    temp.add(OBJECT_TYPE_MENU);
    VALID_OBJECT_TYPES = Collections.unmodifiableList(temp);
  }

  private static class UrlLabelComparator implements Comparator, Serializable {
    static final long serialVersionUID = 3002292737309700957L;
    public int compare(Object o1, Object o2) {
      try {
        UrlDto u1 = (UrlDto) o1;
        UrlDto u2 = (UrlDto) o2;
        String u1Label = u1.getLabel();
        String u2Label = u2.getLabel();
        return u1Label.compareToIgnoreCase(u2Label);
      }
      catch (ClassCastException cce) {
        ApiFunctions.throwAsRuntimeException(cce);
        return 0; // unreached, keep compiler happy
      }
    }
  }

  /**
   * Return information about the url with the specified id.
   * 
   * @param urlId The primary key of the url.
   * @param exceptionIfNotExists If true, throw an exception if there is no url
   *     with the specified id.  If false, return null if the url doesn't exist.
   * @param resultClass The class of object to create to hold the result.  This must be
   *     {@link UrlDto} or a class that extends it.  This method only populates
   *     fields that exist on the UrlDto class itself.
   * @return the policy information
   */
  public static UrlDto getUrlDto(
    String urlId,
    boolean exceptionIfNotExists,
    Class resultClass) {

    UrlDto result = null;
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      StringBuffer query = new StringBuffer(256);
      query.append("SELECT * FROM ");
      query.append(DbConstants.TABLE_ARD_URLS);
      query.append(" ");
      query.append(DbAliases.TABLE_ARD_URLS);
      query.append(" WHERE ");
      query.append(DbAliases.TABLE_ARD_URLS);
      query.append(".");
      query.append(DbConstants.ARD_URLS_ID);
      query.append(" = ?");
      
      result = (UrlDto) resultClass.newInstance();

      result.setUrlId(urlId);

      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query.toString());
      pstmt.setBigDecimal(1, new BigDecimal(urlId));
      rs = ApiFunctions.queryDb(pstmt, con);
      Map columns = DbUtils.getColumnNames(rs);

      if (rs.next()) {
        result.populate(columns, rs);
      }
      else if (exceptionIfNotExists){
        throw new ApiException("Could not retrieve url information for url_id = " + urlId);
      }
      else {
        result = null;
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
   * Return information about the url with the specified url id.
   * 
   * @param urlId The primary key of the url.
   * @return the url information
   */
  public static UrlDto getUrlDto(String urlId) {
    return getUrlDto(urlId, true, UrlDto.class);
  }

  /**
   * Return true if there is a url with the specified id.
   * 
   * @param urlId The primary key of the url.
   * @return true if there is a url with the specified id.
   */
  public static boolean isExistingUrl(String urlId) {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      StringBuffer query = new StringBuffer(256);
      query.append("SELECT * FROM ");
      query.append(DbConstants.TABLE_ARD_URLS);
      query.append(" ");
      query.append(DbAliases.TABLE_ARD_URLS);
      query.append(" WHERE ");
      query.append(DbAliases.TABLE_ARD_URLS);
      query.append(".");
      query.append(DbConstants.ARD_URLS_ID);
      query.append(" = ?");
      pstmt = con.prepareStatement(query.toString());
      pstmt.setBigDecimal(1, new BigDecimal(urlId));
      rs = ApiFunctions.queryDb(pstmt, con);
      return rs.next();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
      return false; // unreached, keep compiler happy
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
  }
  
  /**
   * Get the urls to which the account has visiblity as a list ordered by label.
   * 
   * @param accountId the account id
   * 
   * @return a list of the urls defined for the account.
   */
  public static List getUrlsByAccountId(String accountId) {
    return getUrlsByAccountId(accountId, null);
  }

  /**
   * Get all the urls as a list ordered by account then label.
   * 
   * @param accountId the account id
   * 
   * @return a list of the urls defined for the account.
   */
  public static List getAllUrls() {
    List result = new ArrayList();

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      con = ApiFunctions.getDbConnection();
      StringBuffer query = new StringBuffer(256);
      query.append("SELECT * FROM ");
      query.append(DbConstants.TABLE_ARD_URLS);
      query.append(" ");
      query.append(DbAliases.TABLE_ARD_URLS);
      query.append(" ORDER BY ");
      query.append(DbAliases.TABLE_ARD_URLS);
      query.append(".");
      query.append(DbConstants.ARD_URLS_ACCT_KEY);
      query.append(" ASC, ");
      query.append("upper(");
      query.append(DbAliases.TABLE_ARD_URLS);
      query.append(".");
      query.append(DbConstants.ARD_URLS_LABEL);
      query.append(") ASC");
      pstmt = con.prepareStatement(query.toString());
      rs = ApiFunctions.queryDb(pstmt, con);
      Map columns = DbUtils.getColumnNames(rs);
      while (rs.next()) {
        UrlDto url = new UrlDto();
        url.populate(columns, rs);
        result.add(url);
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
   * Get the urls to which the account has visiblity as a list ordered by label.
   * 
   * @param accountId the account id
   * 
   * @return a list of the urls defined for the account.
   */
  public static List getUrlsByAccountId(String accountId, List objectTypes) {
    List result = new ArrayList();

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      con = ApiFunctions.getDbConnection();
      StringBuffer query = new StringBuffer(256);
      query.append("SELECT * FROM ");
      query.append(DbConstants.TABLE_ARD_URLS);
      query.append(" ");
      query.append(DbAliases.TABLE_ARD_URLS);
      query.append(" WHERE ");
      query.append(DbAliases.TABLE_ARD_URLS);
      query.append(".");
      query.append(DbConstants.ARD_URLS_ACCT_KEY);
      query.append(" = ?");
      if (!ApiFunctions.isEmpty(objectTypes)) {
        query.append(" AND ");
        query.append(DbAliases.TABLE_ARD_URLS);
        query.append(".");
        query.append(DbConstants.ARD_URLS_OBJECT_TYPE);
        query.append(" IN ");
        query.append(ApiFunctions.makeBindParameterList(objectTypes.size()));
      }
      query.append(" ORDER BY upper(");
      query.append(DbAliases.TABLE_ARD_URLS);
      query.append(".");
      query.append(DbConstants.ARD_URLS_LABEL);
      query.append(") ASC");
      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, accountId);
      if (!ApiFunctions.isEmpty(objectTypes)) {
		  ApiFunctions.bindBindParameterList(pstmt, 2, objectTypes);
      }
      rs = ApiFunctions.queryDb(pstmt, con);
      Map columns = DbUtils.getColumnNames(rs);
      while (rs.next()) {
        UrlDto url = new UrlDto();
        url.populate(columns, rs);
        result.add(url);
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
   * The url id as stored in the database is just a number.  In some situations
   * we need a prefixed, fixed-length id -- for example to have an identifiable id type
   * that can be returned by getDirectlyInvolvedObjects() to index a transaction.
   * This function returns the url id in that form (or null/empty if the url id is
   * null/empty). 
   * 
   * @return the list of prefixed url ids.
   */
  public static IdList makePrefixedUrlIds(IdList urlIds) {
    if (urlIds == null || urlIds.size() == 0) {
      return urlIds;
    }
    IdList prefixedUrlIds = new IdList();
    Iterator iter = urlIds.iterator();
    while (iter.hasNext()) {
      String id = (String) iter.next();
      prefixedUrlIds.add(FormLogic.makePrefixedPolicyId(id));
    }
    return prefixedUrlIds;
  }

}
