/**
 * Created on Feb 5, 2003
 */
package com.ardais.bigr.iltds.databeans;

import java.util.Date;

/**
 * @author jesielionis
 *
 * Databean to represent one line item in a shopping cart.
 */
public class ShoppingCartLineItem implements java.io.Serializable {
	private static final long serialVersionUID = 6705865937814334625L;

	private int _lineNumber;
	private String _ardaisUserId;
	private int _shoppingCartId;
	private String _ardaisAccountKey;
	private String _sampleId;
	private Date _creationDate;
	
	/**
	 * Constructor for ShoppingCartLineItem.
	 */
	public ShoppingCartLineItem() {
		super();
	}

	/**
	 * Returns the ardaisAccountKey.
	 * @return String
	 */
	public String getArdaisAccountKey() {
		return _ardaisAccountKey;
	}

	/**
	 * Returns the ardaisUserId.
	 * @return String
	 */
	public String getArdaisUserId() {
		return _ardaisUserId;
	}

	/**
	 * Returns the creationDate.
	 * @return Date
	 */
	public Date getCreationDate() {
		return _creationDate;
	}

	/**
	 * Returns the lineNumber.
	 * @return int
	 */
	public int getLineNumber() {
		return _lineNumber;
	}

	/**
	 * Returns the sampleId.
	 * @return String
	 */
	public String getSampleId() {
		return _sampleId;
	}

	/**
	 * Returns the shoppingCartId.
	 * @return int
	 */
	public int getShoppingCartId() {
		return _shoppingCartId;
	}

	/**
	 * Sets the ardaisAccountKey.
	 * @param ardaisAccountKey The ardaisAccountKey to set
	 */
	public void setArdaisAccountKey(String ardaisAccountKey) {
		_ardaisAccountKey = ardaisAccountKey;
	}

	/**
	 * Sets the ardaisUserId.
	 * @param ardaisUserId The ardaisUserId to set
	 */
	public void setArdaisUserId(String ardaisUserId) {
		_ardaisUserId = ardaisUserId;
	}

	/**
	 * Sets the creationDate.
	 * @param creationDate The creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		_creationDate = creationDate;
	}

	/**
	 * Sets the lineNumber.
	 * @param lineNumber The lineNumber to set
	 */
	public void setLineNumber(int lineNumber) {
		_lineNumber = lineNumber;
	}

	/**
	 * Sets the sampleId.
	 * @param sampleId The sampleId to set
	 */
	public void setSampleId(String sampleId) {
		_sampleId = sampleId;
	}

	/**
	 * Sets the shoppingCartId.
	 * @param shoppingCartId The shoppingCartId to set
	 */
	public void setShoppingCartId(int shoppingCartId) {
		_shoppingCartId = shoppingCartId;
	}

}
