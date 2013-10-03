package com.ardais.bigr.lims.btx;

import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 * Represent the details of a create image business transaction.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 * 
 * NOTE: This BTXDetails class is different from the others in that a database trigger is used to populate 
 * the ILTDS_BTX_HISTORY and ILTDS_BTX_INVOLVED_OBJECT tables - this class is never used for anything other
 * than to display the details of the transaction.  As such, there are no required input or output fields.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>None.
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 */
public class BTXDetailsCreateImage extends BTXDetails implements java.io.Serializable {
  private static final long serialVersionUID = -3170865243593035387L;

  private String _sampleId;
  private String _slideId;
  private String _magnification;
  private String _procedure;
  private String _notes;
  private String _imageFileName;

  /** 
   * Constructor
   */
  public BTXDetailsCreateImage() {
    super();
  }

  /**
   * Returns the imageFileName.
   * @return String
   */
  public String getImageFileName() {
    return _imageFileName;
  }

  /**
   * Returns the magnification.
   * @return String
   */
  public String getMagnification() {
    return _magnification;
  }

  /**
   * Returns the notes.
   * @return String
   */
  public String getNotes() {
    return _notes;
  }

  /**
   * Returns the procedure.
   * @return String
   */
  public String getProcedure() {
    return _procedure;
  }

  /**
   * Returns the sampleId.
   * @return String
   */
  public String getSampleId() {
    return _sampleId;
  }

  /**
   * Returns the slideId.
   * @return String
   */
  public String getSlideId() {
    return _slideId;
  }

  /**
   * Sets the imageFileName.
   * @param imageFileName The imageFileName to set
   */
  public void setImageFileName(String imageFileName) {
    _imageFileName = imageFileName;
  }

  /**
   * Sets the magnification.
   * @param magnification The magnification to set
   */
  public void setMagnification(String magnification) {
    _magnification = magnification;
  }

  /**
   * Sets the notes.
   * @param notes The notes to set
   */
  public void setNotes(String notes) {
    _notes = notes;
  }

  /**
   * Sets the procedure.
   * @param procedure The procedure to set
   */
  public void setProcedure(String procedure) {
    _procedure = procedure;
  }

  /**
   * Sets the sampleId.
   * @param sampleId The sampleId to set
   */
  public void setSampleId(String sampleId) {
    _sampleId = sampleId;
  }

  /**
   * Sets the slideId.
   * @param slideId The slideId to set
   */
  public void setSlideId(String slideId) {
    _slideId = slideId;
  }

  /**
   * This method is called from the BTXDetails base class when its getDetailsAsHTML() 
   * method has been called from the BTXHistoryReaderBean.getHistoryDisplayLines 
   * method. This method must not make use of any fields that aren't set by the 
   * populateFromHistoryRecord method. For this object type, the fields we can use here 
   * are:
  	getSampleId();
  	getSlideId();
  	getMagnification();
  	getProcedure();
  	getNotes();
  	getImageFileName()
   *
   * @return an HTML string that defines how the transaction details are presented
   * in a transaction-history web page
   */
  protected String doGetDetailsAsHTML() {

    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();

    StringBuffer sb = new StringBuffer(2048);

    // The result has this form:
    //    <user> captured a <magnification> image for <slide id> (created from sample <sample id> using procedure <procedure>).
    // and including the following line if there were any notes entered.
    //	  <user> recorded the following note(s): <notes>.

    Escaper.htmlEscape(getUserId(), sb);
    sb.append(" captured a  ");
    Escaper.htmlEscape(getMagnification(), sb);
    sb.append(" image, named ");
    Escaper.htmlEscape(getImageFileName(), sb);
    sb.append(", for slide ");
    sb.append(IcpUtils.prepareLink(getSlideId(), securityInfo));
    sb.append(" (created from sample ");
    sb.append(IcpUtils.prepareLink(getSampleId(), securityInfo));
    sb.append(" using procedure ");
    Escaper.htmlEscape(getProcedure(), sb);
    sb.append(").");

    String notes = getNotes();
    if (ApiFunctions.safeStringLength(notes) > 0) {
      sb.append(" ");
      Escaper.htmlEscape(getUserId(), sb);
      sb.append(" recorded the following note(s): ");
      Escaper.htmlEscape(notes, sb);
      sb.append(".");
    }

    return sb.toString();
  }

  /**
   * Return the business transaction type code for the transaction that this
   * class represents.
   *
   * @return the transaction type code.
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_CREATE_IMAGE;
  }

  /**
   * This method is here only because it's required (the method declaration on the parent is abstract).
   * It should never be called (see the comment at the top of this class), since this class is never
   * used to record data in the database.
   */
  public Set getDirectlyInvolvedObjects() {
    return null;
  }

  /**
   * Populate the fields of this object with information contained in a
   * business transaction history record object.  This method must set <b>all</b>
   * fields on this object, as if it had been newly created immediately before
   * this method was called.  A runtime exception is thrown if the transaction type
   * represented by the history record doesn't match the transaction type represented
   * by this object.
   * <p>
   * This method is only meant to be used internally by the business
   * transaction framework implementation.  Please don't use it anywhere else.
   *
   * @param history the history record object that will be used as the
   *    information source.  A runtime exception is thrown if this is null.
   */
  public void populateFromHistoryRecord(BTXHistoryRecord history) {
    super.populateFromHistoryRecord(history);
    setSampleId(history.getAttrib1());
    setSlideId(history.getAttrib2());
    setMagnification(history.getAttrib3());
    setProcedure(history.getAttrib4());
    setNotes(history.getAttrib5());
    setImageFileName(history.getAttrib6());
  }
}