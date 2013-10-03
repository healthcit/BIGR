package com.ardais.bigr.iltds.btx;

import java.sql.Timestamp;

/**
 * This class represents the entry for a single business transaction in
 * a web page that displays transaction history.  It contains the
 * transaction id, the transactions ending date/time, the user that
 * performed the transaction, and an HTML string describing the
 * transaction details.
 */
public class BTXHistoryDisplayLine implements java.io.Serializable {
	private static final long serialVersionUID = 410002670266435552L;
	
	private long _transactionId = 0;
	private String _btxType = null;
	private Timestamp _endTimestamp = null;
	private String _userId = null;
	private String _detailsAsHTML = null;
public BTXHistoryDisplayLine() {
	super();
}
/**
 * Return the business transaction type code for the transaction.
 * See {@link #setBTXType(String) setBTXType} for a description of
 * the valid values for this field.
 *
 * @return the transaction type code.
 */
public String getBTXType() {
	return _btxType;
}
/**
 * Return a user-friendly human-readable name corresponding to the
 * business transaction type of this record.  This will throw a runtime
 * exception if the transaction type code property
 * ({@link #setBTXType(String) setBTXType}) isn't set to a
 * valid non-null business transaction type code
 * (as defined by the BTX_TYPE_* constants in {@link BTXDetails}).
 * 
 * @return the display string for transaction type of this record.
 */
public String getBTXTypeDisplayName()
{
	return BTXDetails.getBTXTypeDisplayName(_btxType);
}
/**
 * Return an HTML string describing the transaction details in human-readable
 * form.  This string does not include information about transaction attributes
 * that are common to all transactions (for example, transaction id, user id,
 * and timestamp).
 *
 * @return transaction details in HTML format.
 */
public String getDetailsAsHTML() {
	return _detailsAsHTML;
}
/**
 * Return the date/time at which the transaction completed.
 *
 * @return the transaction completion date/time.
 */
public Timestamp getEndTimestamp() {
	return _endTimestamp;
}
/**
 * Return the transaction id that uniquely identifies the transaction.
 *
 * @return the transaction id.
 */
public long getTransactionId() {
	return _transactionId;
}
/**
 * Return the user id of the user who performed the transaction.
 *
 * @return the transaction user id.
 */
public String getUserId() {
	return _userId;
}
/**
 * Set the business transaction type code for the transaction.
 *
 * @param newBTXType the transaction type code.  This must be one
 * of the BTX_TYPE_* constants defined in {@link BTXDetails}.
 */
public void setBTXType(String newBTXType) {
	_btxType = newBTXType;
}
/**
 * Set an HTML string describing the transaction details in human-readable
 * form.  This string should not include information about transaction attributes
 * that are common to all transactions (for example, transaction id, user id,
 * and timestamp).
 *
 * @param newDetailsAsHTML the transaction details in HTML format.
 */
public void setDetailsAsHTML(String newDetailsAsHTML) {
	_detailsAsHTML = newDetailsAsHTML;
}
/**
 * Set the date/time at which the transaction completed.
 *
 * @param newEndTimestamp the transaction completion date/time.
 */
public void setEndTimestamp(Timestamp newEndTimestamp) {
	_endTimestamp = newEndTimestamp;
}
/**
 * Set the transaction id that uniquely identifies the transaction.
 *
 * @param newTransactionId the transaction id.
 */
public void setTransactionId(long newTransactionId) {
	_transactionId = newTransactionId;
}
/**
 * Set the user id of the user who performed the transaction.
 *
 * @param newUserId the transaction user id.
 */
public void setUserId(String newUserId) {
	_userId = newUserId;
}
}
