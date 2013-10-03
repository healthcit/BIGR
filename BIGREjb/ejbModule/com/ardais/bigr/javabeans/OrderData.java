package com.ardais.bigr.javabeans;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.es.helpers.FormLogic;
import com.ardais.bigr.util.gen.DbAliases;

/**
 * @author sislam
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class OrderData implements Serializable {

	private String _orderNumber;
	private String _orderDescription;
    private Timestamp _creationDate;
    private String _userId;
    private String _accountId;
    private String _userFullName;
    private String _status;

	/**
	 * Creates a new <code>OrderData</code>.
	 */
	public OrderData() {
	}

  /**
   * Creates a new <code>OrderData</code>, initialized from
   * the data in the OrderData passed in.
   *
   * @param  orderData  the <code>OrderData</code>
   */
  public OrderData(OrderData orderData) {
    this();
    BigrBeanUtilsBean.SINGLETON.copyProperties(this, orderData);
    //any mutable objects must be handled seperately (BigrBeanUtilsBean.copyProperties
    //does a shallow copy.
    if (orderData.getCreationDate() != null) {
      setCreationDate((Timestamp)orderData.getCreationDate().clone());
    }
  }

	/**
	 * Creates a new <code>OrderData</code>, initialized from
	 * the data in the current row of the result set.
	 *
	 * @param  columns  a <code>Map</code> containing a key for each column in
	 * 									 the <code>ResultSet</code>.  Each key must be one of the
	 * 									 column alias constants in {@link com.ardais.bigr.util.DbAliases}
	 * 									 and the corresponding value is ignored.
	 * @param  rs  the <code>ResultSet</code>
	 */
	public OrderData(Map columns, ResultSet rs) {
    this();
    populate(columns, rs);
	}

	/**
	 * Populates this <code>OrderData</code> from the data in the current row
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
	    if (columns.containsKey(DbAliases.ORDER_DESCRIPTION)) {
	    	setOrderDescription(rs.getString(DbAliases.ORDER_DESCRIPTION));
	    }
	    if (columns.containsKey(DbAliases.ORDER_NUMBER)) {
	    	setOrderNumber(rs.getString(DbAliases.ORDER_NUMBER));
	    }
    } catch (SQLException e) {
			ApiFunctions.throwAsRuntimeException(e);
    }
	}

	/**
	 * Returns the orderDescription.
	 * @return String
	 */
	public String getOrderDescription() {
		return _orderDescription;
	}

	/**
	 * Returns the orderNumber.
	 * @return String
	 */
	public String getOrderNumber() {
		return _orderNumber;
	}

	/**
	 * Sets the orderDescription.
	 * @param orderDescription The orderDescription to set
	 */
	public void setOrderDescription(String orderDescription) {
		_orderDescription = orderDescription;
	}

	/**
	 * Sets the orderNumber.
	 * @param orderNumber The orderNumber to set
	 */
	public void setOrderNumber(String orderNumber) {
		_orderNumber = orderNumber;
	}

    /**
     * Returns the creationDate.
     * @return Date
     */
    public String getCreationDateString() {
        DateFormat formatter = DateFormat.getDateInstance(DateFormat.SHORT);
        return formatter.format(_creationDate);
    }

    /**
     * Returns the status.
     * @return String
     */
    public String getStatus() {
        return FormLogic.translateOrderStatusClient(_status);
    }

    /**
     * Sets the status.
     * @param status The status to set
     */
    public void setStatus(String status) {
        _status = status;
    }
    /**
     * Returns the creationDate.
     * @return Timestamp
     */
    public Timestamp getCreationDate() {
        return _creationDate;
    }

    /**
     * Sets the creationDate.
     * @param creationDate The creationDate to set
     */
    public void setCreationDate(Timestamp creationDate) {
        _creationDate = creationDate;
    }

    /**
     * Returns the userId.
     * @return String
     */
    public String getUserId() {
        return _userId;
    }

    /**
     * Sets the userId.
     * @param userId The userId to set
     */
    public void setUserId(String userId) {
        _userId = userId;
    }

    /**
     * Returns the accountId.
     * @return String
     */
    public String getAccountId() {
        return _accountId;
    }

    /**
     * Sets the accountId.
     * @param accountId The accountId to set
     */
    public void setAccountId(String accountId) {
        _accountId = accountId;
    }

    /**
     * Returns the userFullName.
     * @return String
     */
    public String getUserFullName() {
        return _userFullName;
    }

    /**
     * Sets the userFullName.
     * @param userFullName The userFullName to set
     */
    public void setUserFullName(String userFullName) {
        _userFullName = userFullName;
    }

}
