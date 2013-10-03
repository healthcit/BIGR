package com.ardais.bigr.orm.btx;

import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.IcpUtils;

/**
 * Represent the details of maintaining a logical repository (create/edit/delete).
 * For historical reasons the code uses the term "logical repository" but
 * users know this as "inventory group".
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setOperation(String) Operation}: The maintenance operation that is being
 *     performed.  This must be one of "Create", "Update", or "Delete" (case-sensitive).</li>
 * <li>{@link #setRepositoryId(String) Logical repository id}: The logical repository id that we're
 *     operating on.  This is required for "Update" and "Delete" operations and must be
 *     null on input for "Create" operations (it is an output parameter for "Create").</li>
 * <li>{@link #setRepositoryFullName(String) Logical repository full name}: The full name of
 *     the logical repository.  This is required for "Create" and "Update" operations and
 *     is ignored on input for "Delete" operations (it is an output parameter for "Delete").
 *     Any whitespace at the beginning or end of the name will be trimmed.</li>
 * <li>{@link #setRepositoryShortName(String) Logical repository short name}: The short name of
 *     the logical repository.  This is required for "Create" and "Update" operations and
 *     is ignored on input for "Delete" operations (it is an output parameter for "Delete").
 *     Any whitespace at the beginning or end of the name will be trimmed.</li>
 * <li>{@link #setBms(Boolean) Logical repository BMS flag}: Indicates whether the logical
 *     repository is a BMS repository or not.  This is required for "Create" and "Update"
 *     operations and is ignored on input for "Delete" operations (it is an output parameter
 *     for "Delete").</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setRepositoryId(String) Logical repository id}: This is sometimes an
 *     output parameter, as described above.</li>
 * <li>{@link #setRepositoryFullName(String) Logical repository full name}: This is sometimes an
 *     output parameter, as described above.</li>
 * <li>{@link #setRepositoryShortName(String) Logical repository short name}: This is sometimes an
 *     output parameter, as described above.</li>
 * <li>{@link #setBms(Boolean) Logical repository BMS flag}: This is sometimes an
 *     output parameter, as described above.</li>
 * </ul>
 */
public class BtxDetailsMaintainLogicalRepository
  extends BTXDetails
  implements java.io.Serializable {
  private static final long serialVersionUID = -262997537763340998L;

  private String _operation = null;
  private String _repositoryId = null;
  private String _repositoryFullName = null;
  private String _repositoryShortName = null;
  private Boolean _bms = null;

  public BtxDetailsMaintainLogicalRepository() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_MAINTAIN_LOGICAL_REPOSITORY;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  public Set getDirectlyInvolvedObjects() {
    // Logical repositories ids are just numbers and so can't be
    // usefully distinguished as transaction indexers.  So, here we use a "virtual" prefixed
    // form of the repository id, not the actual id as stored in the database.

    Set set = new HashSet();

    set.add(getRepositoryIdPrefixed());

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

    Boolean isBms = getBms();

    history.setAttrib1(getOperation());
    history.setAttrib2(getRepositoryId());
    history.setAttrib3(getRepositoryFullName());
    history.setAttrib4(getRepositoryShortName());
    if (isBms == null) {
      history.setAttrib5(null);
    }
    else {
      history.setAttrib5(isBms.booleanValue() ? "Y" : "N");
    }
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
    setRepositoryId(history.getAttrib2());
    setRepositoryFullName(history.getAttrib3());
    setRepositoryShortName(history.getAttrib4());

    String bmsString = history.getAttrib5();
    if (ApiFunctions.isEmpty(bmsString)) {
      setBms(null);
    }
    else {
      setBms("Y".equals(bmsString) ? Boolean.TRUE : Boolean.FALSE);
    }

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
    //   getRepositoryId
    //   getRepositoryFullName
    //   getRepositoryShortName

    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();

    StringBuffer sb = new StringBuffer(512);

    // The form of the result depends on the operation.
    // Operation "Create":
    //    Created inventory group <id> '<fullName>' (<shortName>).
    //    This [is / is not] a BMS group.
    // Operation "Update":
    //    Updated inventory group <id>.  Name = '<fullName>' (<shortName>).
    //    This [is / is not] a BMS group.
    // Operation "Delete":
    //    Removed inventory group <id> '<fullName>' (<shortName>).

    String operation = getOperation();
    String reposId = getRepositoryId();
    String prefixedReposId = getRepositoryIdPrefixed();
    String reposFullName = getRepositoryFullName();
    String reposShortName = getRepositoryShortName();
    Boolean isBms = getBms();

    if (Constants.OPERATION_CREATE.equals(operation)) {
      sb.append("Created inventory group ");
      sb.append(IcpUtils.prepareLink(prefixedReposId, reposId, securityInfo));
      sb.append(" '");
      Escaper.htmlEscape(reposFullName, sb);
      sb.append("' (");
      Escaper.htmlEscape(reposShortName, sb);
      sb.append(").  This is");
      if (!isBms.booleanValue()) {
        sb.append(" not");
      }
      sb.append(" a BMS group.");
    }
    else if (Constants.OPERATION_UPDATE.equals(operation)) {
      sb.append("Updated inventory group ");
      sb.append(IcpUtils.prepareLink(prefixedReposId, reposId, securityInfo));
      sb.append(".  Name = '");
      Escaper.htmlEscape(reposFullName, sb);
      sb.append("' (");
      Escaper.htmlEscape(reposShortName, sb);
      sb.append(").  This is");
      if (!isBms.booleanValue()) {
        sb.append(" not");
      }
      sb.append(" a BMS group.");
    }
    else if (Constants.OPERATION_DELETE.equals(operation)) {
      sb.append("Removed inventory group ");
      sb.append(IcpUtils.prepareLink(prefixedReposId, reposId, securityInfo));
      sb.append(" '");
      Escaper.htmlEscape(reposFullName, sb);
      sb.append("' (");
      Escaper.htmlEscape(reposShortName, sb);
      sb.append(").");
    }

    return sb.toString();
  }

  /**
   * @return
   */
  public Boolean getBms() {
    return _bms;
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
  public String getRepositoryFullName() {
    return _repositoryFullName;
  }

  /**
   * @return
   */
  public String getRepositoryId() {
    return _repositoryId;
  }

  /**
   * The logical repository id as stored in the database is just a number.  In some situations
   * we need a prefixed, fixed-length id -- for example to have an identifiable id type
   * that can be returned by getDirectlyInvolvedObjects() to index a transaction.
   * This function returns the repository id in that form (or null/empty if the repository id is
   * null/empty). 
   * 
   * @return the prefixed logical repository id.
   */
  private String getRepositoryIdPrefixed() {
    return FormLogic.makePrefixedLogicalRepositoryId(getRepositoryId());
  }

  /**
   * @return
   */
  public String getRepositoryShortName() {
    return _repositoryShortName;
  }

  /**
   * @param boolean1
   */
  public void setBms(Boolean boolean1) {
    _bms = boolean1;
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
  public void setRepositoryFullName(String string) {
    _repositoryFullName = ApiFunctions.safeTrim(string);
  }

  /**
   * @param string
   */
  public void setRepositoryId(String string) {
    _repositoryId = string;
  }

  /**
   * @param string
   */
  public void setRepositoryShortName(String string) {
    _repositoryShortName = ApiFunctions.safeTrim(string);
  }

}