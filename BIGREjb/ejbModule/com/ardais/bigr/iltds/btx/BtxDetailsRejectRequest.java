package com.ardais.bigr.iltds.btx;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.javabeans.RequestDto;
import com.ardais.bigr.javabeans.RequestItemDto;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 * Class to represent a reject request business transaction.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} and {@link BtxDetailsRequestOperation BtxDetailsRequestOperation} for fields 
 * that are shared by all business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setInputRequestDto(RequestDto) inputRequestDto}: The RequestDto containing all 
 *      necessary input data.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setOutputRequestDto(RequestDto) outputRequestDto}: The RequestDto containing all 
 *      necessary output data.</li>
 * </ul>
 */
public class BtxDetailsRejectRequest extends BtxDetailsFindRequestDetails implements Serializable {
  private static final long serialVersionUID = -4458060307825434062L;

  /**
   * Constructor.
   */
  public BtxDetailsRejectRequest() {
    super();
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

    history.setAttrib1(getOutputRequestDto().getId());
    history.setAttrib2(getOutputRequestDto().getRequestManagerComments());
  }
  /**
   * Return an HTML string that defines how the transaction details are presented
   * in a transaction-history web page.  Derived classes must override this method.
   * <p>
   * This method is protected, the corresponding public method is
   * {@link #getDetailsAsHTML() getDetailsAsHTML}, which calls this method.
   * getDetailsAsHTML handles common tasks such as returning the
   * details in the case that the transaction represents a failed transaction
   * (it has a non-null exceptionText property).  For such a transaction, the
   * doGetDetailsAsHTML method will not be called.  This framework is intended
   * to make it easier to implement doGetDetailsAsHTML in derived classes, as
   * the code there may assume that the transaction succeeded and that the
   * transaction's data fields aren't malformed.
   * <p>
   * <b>Implementation of this method must only make use of fields that are populated
   * by the populateFromHistory method.</b>
   *
   * @return the HTML detail string.
   */
  protected String doGetDetailsAsHTML() {
    // NOTE: This method must not make use of any fields that aren't
    // set by the populateFromHistoryRecord method.
    //
    // For this object type, the fields we can use here are:
    //   getOutputRequestDto().getId()
    //   getOutputRequestDto().getRequestManagerComments()

    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();

    StringBuffer sb = new StringBuffer(2048);

    // The result has this form:
    // Request <request id> was rejected for the following reason: <reason>
    
    RequestDto requestDto = getOutputRequestDto();

    sb.append("Request ");
    sb.append(IcpUtils.prepareLink(requestDto.getId(), securityInfo));
    sb.append(" was rejected ");
    String managerComments = requestDto.getRequestManagerComments();
    if (!ApiFunctions.isEmpty(managerComments)) {
      sb.append(" for the following reason: ");
      Escaper.htmlEscapeAndPreserveWhitespace(managerComments, sb);
    }
    return sb.toString();
  }

  /**
   * Return the business transaction type code for the transaction that this
   * class represents.  Derived classes must override this method.
   *
   * @return the transaction type code.
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_REJECT_REQUEST;
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
  public java.util.Set getDirectlyInvolvedObjects() {
    Set set = new HashSet();
    set.add(getOutputRequestDto().getId());
    List items = getOutputRequestDto().getItems();
    if (!ApiFunctions.isEmpty(items)) {
      Iterator iterator = items.iterator();
      while (iterator.hasNext()) {
        RequestItemDto item = (RequestItemDto)iterator.next();
        set.add(item.getItemId());
      }
    }
    return set;
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
    RequestDto req = new RequestDto();
    req.setId(history.getAttrib1());
    req.setRequestManagerComments(history.getAttrib2());
    setOutputRequestDto(req);
  }

}
