/*
 * Created on Nov 24, 2003
 *
 */
package com.ardais.bigr.iltds.assistants;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.util.gen.DbAliases;

/**
 * @author jesielionis
 *
 * Java bean to hold information about a logical repository
 */
public class LogicalRepository implements Serializable {
  private String _id;
  private String _fullName;
  private String _shortName;
  private String _bmsYN;
  
  /**
   * Creates a new <code>LogicalRepository</code>.
   */
  public LogicalRepository() {
  }
  
  /**
   * Creates a new <code>LogicalRepository</code>, initialized from
   * the data in the LogicalRepository passed in.
   *
   * @param  logicalRepository  the <code>LogicalRepository</code>
   */
  public LogicalRepository(LogicalRepository logicalRepository) {
    this();
    BigrBeanUtilsBean.SINGLETON.copyProperties(this, logicalRepository);
  }
  
  /**
   * Creates a new <code>LogicalRepository</code>, initialized from
   * the data in the current row of the result set.
   *
   * @param  columns  a <code>Map</code> containing a key for each column in
   *                   the <code>ResultSet</code>.  Each key must be one of the
   *                   column alias constants in {@link com.ardais.bigr.util.DbAliases}
   *                   and the corresponding value is ignored.
   * @param  rs  the <code>ResultSet</code>
   */
  public LogicalRepository(Map columns, ResultSet rs) {
    this();
    populate(columns, rs);
  }
  
  /**
   * Populates this <code>LogicalRepository</code> from the data in the current row of 
   * the result set.
   * 
   * @param  columns  a <code>Map</code> containing a key for each column in
   *                   the <code>ResultSet</code>.  Each key must be one of the
   *                   column alias constants in {@link com.ardais.bigr.util.DbAliases}
   *                   and the corresponding value is ignored.
   * @param  rs  the <code>ResultSet</code>
   */
  public void populate(Map columns, ResultSet rs) {
      try {
        if (columns.containsKey(DbAliases.LOGICAL_REPOS_ID)) {
          setId(rs.getString(DbAliases.LOGICAL_REPOS_ID));
        }
        if (columns.containsKey(DbAliases.LOGICAL_REPOS_FULL_NAME)) {
          setFullName(rs.getString(DbAliases.LOGICAL_REPOS_FULL_NAME));
        }
        if (columns.containsKey(DbAliases.LOGICAL_REPOS_SHORT_NAME)) {
          setShortName(rs.getString(DbAliases.LOGICAL_REPOS_SHORT_NAME));
        }
        if (columns.containsKey(DbAliases.LOGICAL_REPOS_BMS_YN)) {
          setBmsYN(rs.getString(DbAliases.LOGICAL_REPOS_BMS_YN));
        }
      } 
      catch (SQLException e) {
          ApiFunctions.throwAsRuntimeException(e);
      }
  }

  /**
   * Determine if this object is equal to another object.
   * In a valid LogicalRepository, the id property is non-empty and uniquely
   * identifies a concept.  This equals function assumes that the repository
   * object is valid and this object is considered to be equal to another
   * object if and only if they are both LogicalRepository objects and their
   * id properties are equal.
   * 
   * @param obj the object to compare to
   * @return true if this object is equal to the specified object
   */
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    else if (!(obj instanceof LogicalRepository)) {
      return false;
    }
    else {
      LogicalRepository o2 = (LogicalRepository) obj;

      if (_id == null) {
        return (o2.getId() == null);
      }
      else {
        return _id.equals(o2.getId());
      }
    }
  }

  /**
   * Return a hash code that is consistent with the
   * {@link #equals(Object) equals} method in that it depends only on
   * the value of the <code>id</code> property.
   * 
   * @return the hash code
   */
  public int hashCode() {
    // This returns the sample value as we'd get from
    //   return new HashCodeBuilder(initialValue, multiplier).append(_code).toHashCode();
    // but without the extra overhead.  HashCodeBuilder is a class in
    // org.apache.commons.lang.builder that generates good hash codes
    // that comply with the guidelines Joshua Bloch lays out in
    // "Effective Java: Programming Language Guide".
    //
    // If you're copying code from here to other hashCode implementations,
    // please read the Javadoc for HashCodeBuilder first, and ideally
    // Bloch's book as well.  There are a lot of guidelines that need
    // to be followed to create a hashCode function that obeys the
    // general contract it must observe and still yields good hash
    // distributions.  One thing to note is that the multiplier and
    // initialValue constants must both be odd, and ideally the
    // multiplier should be a prime number.

    final int multiplier = 11;
    final int initialValue = 59;

    int total = initialValue;
    int c;

    if (_id == null) {
      c = 0;
    }
    else {
      c = _id.hashCode();
    }

    total = total * multiplier + c;

    return total;
  }

  /**
   * @return
   */
  public String getBmsYN() {
    return _bmsYN;
  }
  
  public boolean isBms() {
    return "Y".equals(_bmsYN);
  }

  /**
   * @return
   */
  public String getFullName() {
    return _fullName;
  }

  /**
   * @return
   */
  public String getId() {
    return _id;
  }

  /**
   * @return
   */
  public String getShortName() {
    return _shortName;
  }

  /**
   * @param string
   */
  public void setBmsYN(String string) {
    _bmsYN = string;
  }

  /**
   * @param string
   */
  public void setFullName(String string) {
    _fullName = string;
  }

  /**
   * @param string
   */
  public void setId(String string) {
    _id = string;
  }

  /**
   * @param string
   */
  public void setShortName(String string) {
    _shortName = string;
  }

}
