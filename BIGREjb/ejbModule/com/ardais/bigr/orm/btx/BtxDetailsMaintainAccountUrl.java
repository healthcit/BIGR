package com.ardais.bigr.orm.btx;

import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.IcpUtils;

/**
 * Represent the details of maintaining a url (create/edit/delete).
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setOperation(String) Operation}: The maintenance operation that is being
 *     performed.  This must be one of "Create", "Update", or "Delete" (case-sensitive).</li>
 * <li>{@link #setUrlId(String) url id}: The url id that we're
 *     operating on.  This is required for "Update" and "Delete" operations and must be
 *     null on input for "Create" operations (it is an output parameter for "Create").</li>
 * <li>{@link #setAccountId(String) Account id}: The id of the account to which this url "belongs". 
 *     This is required for "Create" and "Update" operations and is ignored on input for the 
 *     "Delete" operation.</li>
 * <li>{@link #setObjectType(String) Object type}: The type of object to which the URL applies.
 *     This a required field for "Update" and "Create" operations and is ignored
 *     on input for the "Delete" operation. The value must be one of "Sample", "Case", "Donor", or
 *     "Menu" (case-sensitive).</li>
 * <li>{@link #setUrl(String) Url}: The URL string. This is required for "Create" and "Update" 
 *     operations and is ignored on input for the "Delete" operation. The URL may contain 0-n
 *     insertion strings, each of which must be one of "$$sample_id$$", "$$case_id$$", "$$donor_id$$",
 *     "$$ardais_sample_id$$", "$$ardais_case_id$$", or "$$ardais_donor_id$$".</li>
 * </ul>
 * 
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>{@link #setTarget(String)Target}: The target for the URL anchor. This is an optional input
 *     for "Create" and "Update" operations and is ignored on input for the "Delete" operation.</li>
 * </ul>
 * 
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setUrlId(String) Url id}: This is sometimes an output parameter, as
 *     described above.</li>
 * <li>{@link #setAccountId(String) Account id}: This is sometimes an output parameter.</li>
 * <li>{@link #setObjectType(String) Object type}: This is sometimes an output parameter.</li>
 * <li>{@link #setUrl(String) Url}: This is sometimes an output parameter.</li>
 * </ul>
 */
public class BtxDetailsMaintainAccountUrl extends BTXDetails {
  static final long serialVersionUID = -2405778066573476265L;

  private String _operation = null;
  private String _urlId = null;
  private String _accountId = null;
  private String _objectType = null;
  private String _url = null;
  private String _label = null;
  private String _target = null;
  private LegalValueSet _accountList = null;

  public BtxDetailsMaintainAccountUrl() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_MAINTAIN_ACCOUNT_URL;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  public Set getDirectlyInvolvedObjects() {
    // Url ids are just numbers and so can't be usefully distinguished as transaction
    // indexers.  So, here we use a "virtual" prefixed form of the url id, not the
    // actual id as stored in the database.

    Set set = new HashSet();
    
    set.add(getUrlIdPrefixed());

    return set;
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
    history.setAttrib1(getOperation());
    history.setAttrib2(getUrlId());
    history.setAttrib3(getAccountId());
    history.setAttrib4(getObjectType());
    history.setAttrib5(getUrl());
    history.setAttrib6(getLabel());
    history.setAttrib7(getTarget());
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
    setOperation(history.getAttrib1());    
    setUrlId(history.getAttrib2());
    setAccountId(history.getAttrib3());
    setObjectType(history.getAttrib4());
    setUrl(history.getAttrib5());
    setLabel(history.getAttrib6());
    setTarget(history.getAttrib7());

    // These fields don't correspond to anything in the history record
    // but we must set them anyways.
    //
    // <None>
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    // NOTE: This method must not make use of any fields that aren't
    // set by the populateFromHistoryRecord method.
    //
    // For this object type, the fields we can use here are:
    //   getOperation
    //   getUrlId
    //   getAccountId
    //   getObjectType
    //   getUrl
    //   getLabel
    //   getTarget

    StringBuffer sb = new StringBuffer(256);

    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();

    // The form of the result depends on the operation.
    // Operation "Create":
    //    Created url <label> (<urlId>), with the following attributes: [attributes]
    // Operation "Update":
    //    Updated url <label> (<urlId>), with the following attributes: [attributes]
    // Operation "Delete":
    //    Removed url <label> (<urlId>), with the following attributes: [attributes]

    String operation = getOperation();
    String prefixedUrlId = getUrlIdPrefixed();
    String label = getLabel();

    if (Constants.OPERATION_CREATE.equals(operation)) {
      sb.append("Created url ");
    }
    else if (Constants.OPERATION_UPDATE.equals(operation)) {
      sb.append("Updated url ");
    }
    else if (Constants.OPERATION_DELETE.equals(operation)) {
      sb.append("Removed url ");
    }
    Escaper.htmlEscape(label, sb);
    sb.append(" (");
    sb.append(IcpUtils.prepareLink(prefixedUrlId, securityInfo));
    sb.append("), with the following attributes: ");
    sb.append("Object type (");
    Escaper.htmlEscape(getObjectType(), sb);
    sb.append("), ");
    sb.append("Url (");
    Escaper.htmlEscape(getUrl(), sb);
    sb.append("), ");
    sb.append("Target (");
    Escaper.htmlEscape(getTarget(), sb);
    sb.append(").");

    return sb.toString();
  }

  /**
   * The url id as stored in the database is just a number.  In some situations
   * we need a prefixed, fixed-length id -- for example to have an identifiable id type
   * that can be returned by getDirectlyInvolvedObjects() to index a transaction.
   * This function returns the url id in that form (or null/empty if the url id is
   * null/empty). 
   * 
   * @return the prefixed url id.
   */
  private String getUrlIdPrefixed() {
    return FormLogic.makePrefixedUrlId(getUrlId());
  }
  /**
   * @return
   */
  public String getAccountId() {
    return _accountId;
  }

  /**
   * @return
   */
  public String getObjectType() {
    return _objectType;
  }

  /**
   * @return
   */
  public String getOperation() {
    return _operation;
  }

  /**
   * @return
   */
  public String getTarget() {
    return _target;
  }

  /**
   * @return
   */
  public String getUrl() {
    return _url;
  }

  /**
   * @return
   */
  public String getLabel() {
    return _label;
  }

  /**
   * @return
   */
  public String getUrlId() {
    return _urlId;
  }

  /**
   * @param string
   */
  public void setAccountId(String string) {
    _accountId = string;
  }

  /**
   * @param string
   */
  public void setObjectType(String string) {
    _objectType = string;
  }

  /**
   * @param string
   */
  public void setOperation(String string) {
    _operation = string;
  }

  /**
   * @param string
   */
  public void setTarget(String string) {
    _target = string;
  }

  /**
   * @param string
   */
  public void setUrl(String string) {
    _url = string;
  }

  /**
   * @param string
   */
  public void setLabel(String string) {
    _label = string;
  }

  /**
   * @param string
   */
  public void setUrlId(String string) {
    _urlId = string;
  }

  /**
   * @return
   */
  public LegalValueSet getAccountList() {
    return _accountList;
  }

  /**
   * @param set
   */
  public void setAccountList(LegalValueSet set) {
    _accountList = set;
  }

}