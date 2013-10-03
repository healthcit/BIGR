package com.ardais.bigr.lims.btx;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 * Represent the details of a print slides business transaction.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setUserId(String) limsUserId}: The id of the user performing the transaction.</li>
 * <li>{@link #setSlidesList(List) slideData}: A list of Slides.</li>
 * <li>{@link #setPrintAllSlides(boolean) printAllSlides}: A boolean which specifies to print all slides.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setMessage(String) message}: The message which specifies the status 
 * of printing label(s).</li>
 * </ul>
 */
public class BTXDetailsPrintSlides extends BTXDetails implements Serializable {
  public static final String OP_TYPE_SCAN_USER = "scanUser";
  public static final String OP_TYPE_ADD_SLIDE = "addSlide";
  public static final String OP_TYPE_PRINT_LABELS = "printLabels";

  private String _limsUserId;
  private String _delimitedSlidesList;
  private IdList _slidesList;
  private IdList _samplesList;

  private String _operationType;
  private String _currentSlide;

  /**
   * Constructor for BTXDetailsPrintSlides.
   */
  public BTXDetailsPrintSlides() {
    super();
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_PRINT_SLIDES;
  }

  /**
     * Return a set of the object ids of the objects that are directly involved
     * in this transaction.  This set does not contain the ids of objects that
     * are considered to be indirectly involved in the transaction, and it does
     * not include the user id of the user who performed the transaction.
     * <p>
     * For example, a transaction that scans a box of samples directly involves the box
     * object and each of the sample objects, and indirectly involves the
     * asm, asm form, case and donor objects for each sample.
     *
     * @return the set of directly involved object ids.
     */
  public Set getDirectlyInvolvedObjects() {
    Set objects = new HashSet();
    if (!IdList.isEmpty(_slidesList)) {
      objects.addAll(_slidesList.getDirectlyInvolvedObjects());
    } 
    if (!IdList.isEmpty(_samplesList)) {
      objects.addAll(_samplesList.getDirectlyInvolvedObjects());
    } 
    return objects;
  }

  /**
   * This method is called from the BTXDetails base class when its getDetailsAsHTML() 
   * method has been called from the BTXHistoryReaderBean.getHistoryDisplayLines 
   * method. This method must not make use of any fields that aren't set by the 
   * populateFromHistoryRecord method. For this object type, the fields we can use here 
   * are:   
   *    getLimsUserId()
   *    getDelimitedSlidesList()
   *
   * @return an HTML string that defines how the transaction details are presented
   * in a transaction-history web page
   */
  protected String doGetDetailsAsHTML() {
    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();
    StringBuffer buf = new StringBuffer(256);
    boolean first = true;
    // The result is of this form:
    // <user> printed label(s) for slide(s) <slide id>, <slide id>....
    Escaper.htmlEscape(getLimsUserId(), buf);
    buf.append(" printed label(s) for slide(s) ");
    StringTokenizer tokens = new StringTokenizer(getDelimitedSlidesList(), ", ");
    while (tokens.hasMoreTokens()) {
      if (first) {
        first = false;
      } else {
        buf.append(", ");
      }
      buf.append(IcpUtils.prepareLink(tokens.nextToken(), securityInfo));
    }
    return buf.toString();
  }

  /**
    * Fill a business transaction history record object with information
    * from this transaction details object.  This method will set <b>all</b>
    * fields on the history record, even ones not used by the this type of
    * transaction.  Fields that aren't used by this transaction type will be
    * set to their initial default values.
    * <p>
    * This method is only meant to be used internally by the business
    * transaction framework implementation.  Please don't use it anywhere else.
    *
    * @param history the history record object that will have its fields set to
    *    the transaction information.
    */
  public void describeIntoHistoryRecord(BTXHistoryRecord history) {
    super.describeIntoHistoryRecord(history);
    history.setAttrib1(getLimsUserId());
    history.setAttrib2(_slidesList.pack());
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
    setLimsUserId(history.getAttrib1());
    setDelimitedSlidesList(history.getAttrib2());
  }

  /**
   * Returns the slidesList.
   * @return String
   */
  public IdList getSlidesList() {
    return _slidesList;
  }

  /**
   * Sets the slidesList.
   * @param slidesList The slidesList to set
   */
  public void setSlidesList(IdList slidesList) {
    _slidesList = slidesList;
  }
  /**
   * Returns the limsUserId.
   * @return String
   */
  public String getLimsUserId() {
    return _limsUserId;
  }

  /**
   * Sets the limsUserId.
   * @param limsUserId The limsUserId to set
   */
  public void setLimsUserId(String limsUserId) {
    _limsUserId = limsUserId;
  }

  /**
   * Returns the operationType.
   * @return String
   */
  public String getOperationType() {
    return _operationType;
  }

  /**
   * Sets the operationType.
   * @param operationType The operationType to set
   */
  public void setOperationType(String operationType) {
    _operationType = operationType;
  }

  /**
   * Returns the currentSlide.
   * @return String
   */
  public String getCurrentSlide() {
    return _currentSlide;
  }

  /**
   * Sets the currentSlide.
   * @param currentSlide The currentSlide to set
   */
  public void setCurrentSlide(String currentSlide) {
    _currentSlide = currentSlide;
  }

  /**
   * Returns the delimitedSlidesList.
   * @return String
   */
  public String getDelimitedSlidesList() {
    return _delimitedSlidesList;
  }

  /**
   * Sets the delimitedSlidesList.
   * @param delimitedSlidesList The delimitedSlidesList to set
   */
  public void setDelimitedSlidesList(String delimitedSlidesList) {
    _delimitedSlidesList = delimitedSlidesList;
  }

  /**
   * Returns the samplesList.
   * @return IdList
   */
  public IdList getSamplesList() {
    return _samplesList;
  }

  /**
   * Sets the samplesList.
   * @param samplesList The samplesList to set
   */
  public void setSamplesList(IdList samplesList) {
    _samplesList = samplesList;
  }

}
