package com.ardais.bigr.iltds.btx;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.helpers.RequestFilter;
import com.ardais.bigr.javabeans.RequestDto;

/**
 * Represents the details of a manage requests business transaction.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setRequestFilter(RequestFilter) requestFilter}: A {@link RequestFilter RequestFilter}
        object containing the various filter values to employ for finding requests to be managed.
   </li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setOpenRequestDtos(List) list}: A list of {@link RequestDto RequestDto} objects, one
 * per open request that matches the filter values.</li>
 * <li>{@link #setClosedRequestDtos(List) list}: A list of {@link RequestDto RequestDto} objects, one
 * per closed request that matches the filter values.</li>
 * <li>{@link #setRequesters(List) requesters}: 
 * A {@link LegalValueSet LegalValueSet} of users who have made requests, used to populate
 * a dropdown list on the client which allows the user to specify that only requests made by a
 * given user should be returned.</li>
 * </ul>
 */
public class BtxDetailsFindRequests extends BTXDetails implements Serializable {
  private static final long serialVersionUID = -8066620614779098626L;

  private RequestFilter _requestFilter;
  private List _openRequestDtos;
  private List _closedRequestDtos;
  private LegalValueSet _requesters;
  
  /** 
   * Constructor
   */
  public BtxDetailsFindRequests() {
    super();
  }

  /**
   * This method is called from the BTXDetails base class when its getDetailsAsHTML() 
   * method has been called from the BTXHistoryReaderBean.getHistoryDisplayLines 
   * method.  Since this transaction is not logged, there won't be any history so this 
   * method should never be called.  If for some reason this method is called it 
   * returns a message to the effect that it was called in error.
   *
   * @return an HTML string that defines how the transaction details are presented
   * in a transaction-history web page
   */
  protected String doGetDetailsAsHTML() {
    String msg = "BTXDetailsFindRequests.doGetDetailsAsHTML() method called in error!";
    return msg;
  }
  
  /**
   * Return the business transaction type code for the transaction that this
   * class represents.
   *
   * @return the transaction type code.
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_FIND_REQUESTS;
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
    Set set = new HashSet();
    if (!ApiFunctions.isEmpty(getOpenRequestDtos())) {
      Iterator iterator = getOpenRequestDtos().iterator();
      while (iterator.hasNext()) {
        set.add(((RequestDto)iterator.next()).getId());
      }
    }
    if (!ApiFunctions.isEmpty(getClosedRequestDtos())) {
      Iterator iterator = getClosedRequestDtos().iterator();
      while (iterator.hasNext()) {
        set.add(((RequestDto)iterator.next()).getId());
      }
    }
    return set;
  }
  /**
   * @return
   */
  public List getClosedRequestDtos() {
    return _closedRequestDtos;
  }

  /**
   * @return
   */
  public List getOpenRequestDtos() {
    return _openRequestDtos;
  }

  /**
   * @return
   */
  public LegalValueSet getRequesters() {
    return _requesters;
  }

  /**
   * @return
   */
  public RequestFilter getRequestFilter() {
    return _requestFilter;
  }

  /**
   * @param list
   */
  public void setClosedRequestDtos(List list) {
    _closedRequestDtos = list;
  }

  /**
   * @param list
   */
  public void setOpenRequestDtos(List list) {
    _openRequestDtos = list;
  }

  /**
   * @param set
   */
  public void setRequesters(LegalValueSet set) {
    _requesters = set;
  }

  /**
   * @param filter
   */
  public void setRequestFilter(RequestFilter filter) {
    _requestFilter = filter;
  }

}
