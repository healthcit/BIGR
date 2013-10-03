package com.ardais.bigr.javabeans;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.iltds.beans.ArdaisstaffAccessBean;
import com.ardais.bigr.iltds.beans.ArdaisstaffKey;
import com.ardais.bigr.util.gen.DbAliases;
import com.ardais.bigr.util.gen.DbConstants;

/**
 * Represents the raw data of an ASM.
 */
public class ArdaisStaff implements Serializable {

	private String _staffId;
	private String _locationAddressId;
  private String _firstName;
	private String _lastName;
	private String _accountKey;
  private String _userId;

	/**
	 * Creates a new <code>ArdaisStaff</code>.
	 */
	public ArdaisStaff() {
	}

	/**
	 * Creates a new <code>ArdaisStaff</code>, initialized from
	 * the data in the current row of the result set.
	 *
   * @param  columns  a <code>Map</code> containing a key for each column in
   *                   the <code>ResultSet</code>.  Each key must be one of the
   *                   column alias constants in {@link com.ardais.bigr.util.DbAliases}
   *                   or {@link com.ardais.bigr.util.DbConstants}
	 * @param  rs  the <code>ResultSet</code>
	 */
	public ArdaisStaff(Map columns, ResultSet rs) {
    this();
    populate(columns, rs);
	}
  /**
   * Creates a new <code>ArdaisStaff</code>, initialized from
   * the data in an ArdaisStaffAccessBean.
   * 
   * @param  ardaisStaff an <code>ArdaisStaffAccessBean</code> the staff access bean
   */
  public ArdaisStaff(ArdaisstaffAccessBean ardaisStaff) {
    this();
    populate(ardaisStaff);
  }

	/**
	 * Populates this <code>ArdaisStaff</code> from the data in the current row
	 * of the result set.
	 * 
	 * @param  columns  a <code>Map</code> containing a key for each column in
	 * 									 the <code>ResultSet</code>.  Each key must be one of the
	 * 									 column alias constants in {@link com.ardais.bigr.util.DbAliases}
   *                   or {@link com.ardais.bigr.util.DbConstants}
	 * @param  rs  the <code>ResultSet</code>
	 */
	public void populate(Map columns, ResultSet rs) {
    try {
	    if (columns.containsKey(DbAliases.ARDAIS_STAFF_STAFF_ID)) {
	    	setStaffId(rs.getString(DbAliases.ARDAIS_STAFF_STAFF_ID));
	    }
	    if (columns.containsKey(DbAliases.STAFF_LOCATION_ADDRESS_ID)) {
	    	setLocationAddressId(rs.getString(DbAliases.STAFF_LOCATION_ADDRESS_ID));
	    }
	    if (columns.containsKey(DbAliases.ARDAIS_STAFF_STAFF_LNAME)) {
	    	setLastName(rs.getString(DbAliases.ARDAIS_STAFF_STAFF_LNAME));
	    }
	    if (columns.containsKey(DbAliases.ARDAIS_STAFF_STAFF_FNAME)) {
	    	setFirstName(rs.getString(DbAliases.ARDAIS_STAFF_STAFF_FNAME));
	    }
	    if (columns.containsKey(DbAliases.ARDAIS_STAFF_ACCT_KEY)) {
	    	setAccountKey(rs.getString(DbAliases.ARDAIS_STAFF_ACCT_KEY));
	    }
	    if (columns.containsKey(DbConstants.ARDAIS_STAFF_USER_ID)) {
	    	setUserId(rs.getString(DbConstants.ARDAIS_STAFF_USER_ID));
	    }
      if (columns.containsKey(DbConstants.ARDAIS_STAFF_STAFF_ID)) {
        setStaffId(rs.getString(DbConstants.ARDAIS_STAFF_STAFF_ID));
      }
      if (columns.containsKey(DbConstants.STAFF_LOCATION_ADDRESS_ID)) {
        setLocationAddressId(rs.getString(DbConstants.STAFF_LOCATION_ADDRESS_ID));
      }
      if (columns.containsKey(DbConstants.ARDAIS_STAFF_STAFF_LNAME)) {
        setLastName(rs.getString(DbConstants.ARDAIS_STAFF_STAFF_LNAME));
      }
      if (columns.containsKey(DbConstants.ARDAIS_STAFF_STAFF_FNAME)) {
        setFirstName(rs.getString(DbConstants.ARDAIS_STAFF_STAFF_FNAME));
      }
      if (columns.containsKey(DbConstants.ARDAIS_STAFF_ACCT_KEY)) {
        setAccountKey(rs.getString(DbConstants.ARDAIS_STAFF_ACCT_KEY));
      }
      if (columns.containsKey(DbConstants.ARDAIS_STAFF_USER_ID)) {
        setUserId(rs.getString(DbConstants.ARDAIS_STAFF_USER_ID));
      }
    } catch (SQLException e) {
			ApiFunctions.throwAsRuntimeException(e);
    }
	}
  
  /**
   * Populates this <code>ArdaisStaff</code> from an ArdaisStaffAccessBean.
   * 
   * @param  ardaisStaff an <code>ArdaisStaffAccessBean</code> the staff access bean
   */
  public void populate(ArdaisstaffAccessBean ardaisStaff) {

    if (ardaisStaff == null) {
      return;
    }

    ArdaisstaffKey staffPk = null;

    try {
      staffPk = (ArdaisstaffKey) ardaisStaff.getEJBRef().getPrimaryKey();
      setStaffId(staffPk.ardais_staff_id);
      setLocationAddressId(ardaisStaff.getGeolocation_location_address_id());
      setLastName(ardaisStaff.getArdais_staff_lname());
      setFirstName(ardaisStaff.getArdais_staff_fname());
      setAccountKey(ardaisStaff.getArdais_acct_key());
      setUserId(ardaisStaff.getArdais_user_id());
    }
    catch (Exception e) {
      ApiLogger.log(
        "Error retrieving data from ArdaisstaffAccessBean with PK = "
          + staffPk.ardais_staff_id
          + ": Error = "
          + e.getLocalizedMessage());
      throw new ApiException(e);
    }
  }

  /**
   * @return
   */
  public String getAccountKey() {
    return _accountKey;
  }

  /**
   * @return
   */
  public String getFirstName() {
    return _firstName;
  }

  /**
   * @return
   */
  public String getLastName() {
    return _lastName;
  }

  /**
   * @return
   */
  public String getFullName() {
    return ApiFunctions.safeString(getFirstName()) + " " + ApiFunctions.safeString(getLastName());
  }

  /**
   * @return
   */
  public String getLocationAddressId() {
    return _locationAddressId;
  }

  /**
   * @return
   */
  public String getStaffId() {
    return _staffId;
  }

  /**
   * @return
   */
  public String getUserId() {
    return _userId;
  }

  /**
   * @param string
   */
  public void setAccountKey(String string) {
    _accountKey = string;
  }

  /**
   * @param string
   */
  public void setFirstName(String string) {
    _firstName = string;
  }

  /**
   * @param string
   */
  public void setLastName(String string) {
    _lastName = string;
  }

  /**
   * @param string
   */
  public void setLocationAddressId(String string) {
    _locationAddressId = string;
  }

  /**
   * @param string
   */
  public void setStaffId(String string) {
    _staffId = string;
  }

  /**
   * @param string
   */
  public void setUserId(String string) {
    _userId = string;
  }

}
