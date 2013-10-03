package com.ardais.bigr.iltds.btx;

import java.sql.Timestamp;

import com.ardais.bigr.javabeans.BoxDto;
import com.ardais.bigr.javabeans.IdList;

/**
 * This class represents the entry for a single business transaction in
 * the transaction history tables.  It contains the
 * transaction id, the transactions ending date/time, the user that
 * performed the transaction, and other information about the transaction.
 *
 * This is a fairly raw form of the transaction details, and is meant to
 * only be used as an internal utility class by the business-transaction
 * framework.  Please don't use it elsewhere, use the various classes
 * derived from {@link BTXDetails} instead.
 */
public class BTXHistoryRecord implements java.io.Serializable {

  private static final long serialVersionUID = 410002670266435552L;

  // **** DEVELOPERS NOTE: When you add a field here, don't give it an
  // **** initial value here.  Initialize it in the clear() method.
  // ****
  // 
  private long _transactionId;
  private String _btxType;
  private String _transactionType;
  private String _userId;
  private Timestamp _beginTimestamp;
  private Timestamp _endTimestamp;
  private String _exceptionText;
  private String _exceptionStackTrace;
  private BTXBoxLocation _boxLocation1;
  private BTXBoxLocation _boxLocation2;
  private BoxDto _boxDto;
  private IdList _idList1;
  private IdList _idList2;

  private String _attrib1;
  private String _attrib2;
  private String _attrib3;
  private String _attrib4;
  private String _attrib5;
  private String _attrib6;
  private String _attrib7;
  private String _attrib8;
  private String _attrib9;
  private String _attrib10;
  private String _attrib11;
  private String _attrib12;
  private String _attrib13;
  private String _attrib14;
  private String _attrib15;
  private String _attrib16;
  private String _attrib17;
  private String _attrib18;
  private String _attrib19;
  private String _attrib20;

  private String _clob1;
  private String _clob2;
  
  private Object _historyObject;
  //
  // ****	
  // **** DEVELOPERS NOTE: When you add a field here, don't give it an
  // **** initial value here.  Initialize it in the clear() method.

  // GRY, 29-Mar-2002:  Removed the detailsAsHTML property due to CLOB problems,
  // see BTXHistoryRecordedBean.record for details.
  public BTXHistoryRecord() {
    super();

    clear();
  }
  /**
   * Reset all data fields to their default values.
   */
  public void clear() {
    // As a convention, order fields here the same way that they
    // are ordered in the database table definition for the
    // ILTDS_BTX_HISTORY table.

    _transactionId = 0;
    _btxType = null;
    _transactionType = null;
    _userId = null;
    _beginTimestamp = null;
    _endTimestamp = null;
    _exceptionText = null;
    _exceptionStackTrace = null;
    _boxLocation1 = null;
    _boxLocation2 = null;
    _boxDto = null; // Corresponds to both BOX_BARCODE_ID
    // and BOX_CONTENTS columns
    _idList1 = null;
    _idList2 = null;

    _attrib1 = null;
    _attrib2 = null;
    _attrib3 = null;
    _attrib4 = null;
    _attrib5 = null;
    _attrib6 = null;
    _attrib7 = null;
    _attrib8 = null;
    _attrib9 = null;
    _attrib10 = null;
    _attrib11 = null;
    _attrib12 = null;
    _attrib13 = null;
    _attrib14 = null;
    _attrib15 = null;
    _attrib16 = null;
    _attrib17 = null;
    _attrib18 = null;
    _attrib19 = null;
    _attrib20 = null;

    _clob1 = null;
    _clob2 = null;
    
    _historyObject = null;
  }
  public String getAttrib1() {
    return _attrib1;
  }
  public String getAttrib10() {
    return _attrib10;
  }
  public String getAttrib11() {
    return _attrib11;
  }
  public String getAttrib12() {
    return _attrib12;
  }
  public String getAttrib13() {
    return _attrib13;
  }
  public String getAttrib14() {
    return _attrib14;
  }
  public String getAttrib15() {
    return _attrib15;
  }
  public String getAttrib16() {
    return _attrib16;
  }
  public String getAttrib17() {
    return _attrib17;
  }
  public String getAttrib18() {
    return _attrib18;
  }
  public String getAttrib19() {
    return _attrib19;
  }
  public String getAttrib2() {
    return _attrib2;
  }
  public String getAttrib20() {
    return _attrib20;
  }
  public String getAttrib3() {
    return _attrib3;
  }
  public String getAttrib4() {
    return _attrib4;
  }
  public String getAttrib5() {
    return _attrib5;
  }
  public String getAttrib6() {
    return _attrib6;
  }
  public String getAttrib7() {
    return _attrib7;
  }
  public String getAttrib8() {
    return _attrib8;
  }
  public String getAttrib9() {
    return _attrib9;
  }
  /**
   * Return the date/time when the transaction began.  This may be null
   * for business transactions that currently only handle the history-recording
   * part of the transaction (and do not actually perform the transaction itself).
   * Ultimately all BIGR transactions will be performed by business transaction
   * objects, but initially the business transaction objects will only record
   * history and the transaction will be performed in the ops or beans where
   * they are currently performed.  To avoid accumulating misleading transaction
   * duration data, transactions should only set the begin timestamp when the
   * business transaction object actually performs the entire transaction.
   *
   * @return the starting date/time.
   */
  public Timestamp getBeginTimestamp() {
    return _beginTimestamp;
  }
  /**
   * Return the box involved in the transaction.
   *
   * @return the box.
   */
  public BoxDto getBoxDto() {
    return _boxDto;
  }
  /**
   * Return the id of the box involved in the transaction.
   *
   * @return the box id.
   */
  public String getBoxId() {
    return ((_boxDto == null) ? null : _boxDto.getBoxId());
  }
  /**
   * Return the location assigned to a box involved in the transaction.
   *
   * @return the box location.
   */
  public BTXBoxLocation getBoxLocation1() {
    return _boxLocation1;
  }
  /**
   * Return the location assigned to a box involved in the transaction.
   *
   * @return the box location.
   */
  public BTXBoxLocation getBoxLocation2() {
    return _boxLocation2;
  }
  /**
   * Return the business transaction type code for the transaction.
   * See {@link BTXDetails} for a description of
   * the valid values for this field.
   *
   * @return the transaction type code.
   */
  public String getBTXType() {
    return _btxType;
  }
  public String getClob1() {
    return _clob1;
  }
  public String getClob2() {
    return _clob2;
  }
  
  /**
   * Return the History Object property.  See {@link #setHistoryObject(Object)}.
   */
  public Object getHistoryObject() {
    return _historyObject;
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
   * Return the stack trace of the exception that occurred while performing
   * the transaction, if any.
   *
   * @return the exception stack trace.
   */
  public String getExceptionStackTrace() {
    return _exceptionStackTrace;
  }
  /**
   * Return the error message for the exception that occurred while performing
   * the transaction, if any.
   *
   * @return the exception message.
   */
  public String getExceptionText() {
    return _exceptionText;
  }
  /**
   * Return a list of object ids.  Different transaction may store different
   * kinds of objects in this list, or may store nothing at all.
   *
   * @return the list of object ids.
   */
  public IdList getIdList1() {
    return _idList1;
  }
  /**
   * Return a list of object ids.  Different transaction may store different
   * kinds of objects in this list, or may store nothing at all.
   *
   * @return the list of object ids.
   */
  public IdList getIdList2() {
    return _idList2;
  }
  /**
   * Return the packed string representation of the contents
   * of the box involved in the transaction.
   *
   * @return the packed box contents.
   */
  public String getPackedBoxContents() {
    String packedBoxContents = null;
    if (_boxDto != null) {
      packedBoxContents = _boxDto.pack();
    }
    return packedBoxContents;
  }
  /**
   * Return the packed string representation of the IdList property.
   *
   * @return the packed id list.
   */
  public String getPackedIdList1() {
    String packed = ((_idList1 == null) ? null : _idList1.pack());

    // Never return an empty string, return null instead in that case.
    //
    if (packed != null && packed.length() == 0) {
      packed = null;
    }

    return packed;
  }
  /**
   * Return the packed string representation of the IdList property.
   *
   * @return the packed id list.
   */
  public String getPackedIdList2() {
    String packed = ((_idList2 == null) ? null : _idList2.pack());

    // Never return an empty string, return null instead in that case.
    //
    if (packed != null && packed.length() == 0) {
      packed = null;
    }

    return packed;
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
   * Get the transaction type.  See {@link BTXDetails#getTransactionType()}.
   * 
   * @return The transaction type.
   */
  public String getTransactionType() {
    return _transactionType;
  }

  /**
   * Return the user id of the user who performed the transaction.
   *
   * @return the transaction user id.
   */
  public String getUserId() {
    return _userId;
  }
  public void setAttrib1(String newAttrib) {
    _attrib1 = newAttrib;
  }
  public void setAttrib10(String newAttrib) {
    _attrib10 = newAttrib;
  }
  public void setAttrib11(String newAttrib) {
    _attrib11 = newAttrib;
  }
  public void setAttrib12(String newAttrib) {
    _attrib12 = newAttrib;
  }
  public void setAttrib13(String newAttrib) {
    _attrib13 = newAttrib;
  }
  public void setAttrib14(String newAttrib) {
    _attrib14 = newAttrib;
  }
  public void setAttrib15(String newAttrib) {
    _attrib15 = newAttrib;
  }
  public void setAttrib16(String newAttrib) {
    _attrib16 = newAttrib;
  }
  public void setAttrib17(String newAttrib) {
    _attrib17 = newAttrib;
  }
  public void setAttrib18(String newAttrib) {
    _attrib18 = newAttrib;
  }
  public void setAttrib19(String newAttrib) {
    _attrib19 = newAttrib;
  }
  public void setAttrib2(String newAttrib) {
    _attrib2 = newAttrib;
  }
  public void setAttrib20(String newAttrib) {
    _attrib20 = newAttrib;
  }
  public void setAttrib3(String newAttrib) {
    _attrib3 = newAttrib;
  }
  public void setAttrib4(String newAttrib) {
    _attrib4 = newAttrib;
  }
  public void setAttrib5(String newAttrib) {
    _attrib5 = newAttrib;
  }
  public void setAttrib6(String newAttrib) {
    _attrib6 = newAttrib;
  }
  public void setAttrib7(String newAttrib) {
    _attrib7 = newAttrib;
  }
  public void setAttrib8(String newAttrib) {
    _attrib8 = newAttrib;
  }
  public void setAttrib9(String newAttrib) {
    _attrib9 = newAttrib;
  }
  /**
   * Set the date/time when the transaction began.  This may be null
   * for business transactions that currently only handle the history-recording
   * part of the transaction (and do not actually perform the transaction itself).
   * Ultimately all BIGR transactions will be performed by business transaction
   * objects, but initially the business transaction objects will only record
   * history and the transaction will be performed in the ops or beans where
   * they are currently performed.  To avoid accumulating misleading transaction
   * duration data, transactions should only set the begin timestamp when the
   * business transaction object actually performs the entire transaction.
   *
   * @param newTimestamp the starting date/time.
   */
  public void setBeginTimestamp(Timestamp newTimestamp) {
    _beginTimestamp = newTimestamp;
  }
  /**
   * Set the id of the box involved in the transaction.
   *
   * @param newBox the box id.
   */
  public void setBox(BoxDto newBox) {
    _boxDto = newBox;
  }
  /**
   * Set the location assigned to a box involved in the transaction.
   *
   * @param newBoxLocation the box location.
   */
  public void setBoxLocation1(BTXBoxLocation newBoxLocation) {
    _boxLocation1 = newBoxLocation;
  }
  /**
   * Set the location assigned to a box involved in the transaction.
   *
   * @param newBoxLocation the box location.
   */
  public void setBoxLocation2(BTXBoxLocation newBoxLocation) {
    _boxLocation2 = newBoxLocation;
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
  public void setClob1(String newClob) {
    _clob1 = newClob;
  }
  public void setClob2(String newClob) {
    _clob2 = newClob;
  }
  
  public void setHistoryObject(Object historyObject) {
    _historyObject = historyObject;
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
   * Set the stack trace of the exception that occurred while performing
   * the transaction, if any.
   *
   * @param newExceptionStackTrace the exception stack trace.
   */
  public void setExceptionStackTrace(String newExceptionStackTrace) {
    _exceptionStackTrace = newExceptionStackTrace;
  }
  /**
   * Set the error message for the exception that occurred while performing
   * the transaction, if any.
   *
   * @param newExceptionText the exception message.
   */
  public void setExceptionText(String newExceptionText) {
    _exceptionText = newExceptionText;
  }
  /**
   * Set a list of object ids.  Different transaction may store different
   * kinds of objects in this list, or may store nothing at all.
   *
   * @param newIds the list of object ids.
   */
  public void setIdList1(IdList newIds) {
    _idList1 = newIds;
  }
  /**
   * Set a list of object ids.  Different transaction may store different
   * kinds of objects in this list, or may store nothing at all.
   *
   * @param newIds the list of object ids.
   */
  public void setIdList2(IdList newIds) {
    _idList2 = newIds;
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
   * Set the transaction type.  See {@link BTXDetails#setTransactionType(String)}.
   * 
   * @param transactionType The transaction type.
   */
  public void setTransactionType(String transactionType) {
    _transactionType = transactionType;
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
