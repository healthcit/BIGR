package com.ardais.bigr.orm.btx;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.javabeans.ShippingPartnerDto;
import com.ardais.bigr.javabeans.StringList;
import com.ardais.bigr.util.IcpUtils;

/**
 * Represent the details of a manage shipping partners transaction.
 * 
 * <p>The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setAccountId(String)}: The id of the account.</li>
 * <li>{@link #setSelectedShippingPartners(String[])}: A String array containing the shipping
 *      partner ids to which the account should be given access.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setAvailableShippingPartners(List)}: All the shipping partners that have not been assigned and that are available to the account.</li>
 * <li>{@link #setAccessibleShippingPartners(List)}: The shipping partners to which the account currently has access.</li>
 * <li>{@link #setRemovedShippingPartners(List)}: Any shipping partners to which the account just had access removed.</li>
 * <li>{@link #setAddedShippingPartners(List)}: Any shipping partners to which the account just had access added.</li>
 * </ul>
 */
public class BtxDetailsManageShippingPartners extends BTXDetails {
  static final long serialVersionUID = 8186607266439024070L;

  private String _accountId = null;
  private String[] _selectedShippingPartners = null;
  
  private List _availableShippingPartners = null;
  private List _assignedShippingPartners = null;
  private List _removedShippingPartners = null;
  private List _addedShippingPartners = null;

  /** 
   * Default constructor.
   */
  public BtxDetailsManageShippingPartners() {
    super();
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_MANAGE_SHIPPING_PARTNERS;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public Set getDirectlyInvolvedObjects() {
    Set set = new HashSet();
    set.add(getAccountId());
    return set;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    // NOTE: This method must not make use of any fields that aren't
    // set by the populateFromHistoryRecord method.
    //
    // For this object type, the fields we can use here are:
    // setAccountId
    // setAddedShippingPartners
    // setRemovedShippingPartners
    // setAvailableShippingPartners - note this will be null
    // setAssignedShippingPartners - note this will be null
    // setSelectedShippingPartners - note this will be null

    // The result has this form:
    //    Account <accountId> had access to the following shipping partners revoked: <shipping partners>.
    //    The account was granted access to the following shipping partners: <shipping partners>.
    StringBuffer sb = new StringBuffer(512);
    sb.append("Account ");
    Escaper.htmlEscape(getAccountId(), sb);
    // If no changes were made, say that
    if (getRemovedShippingPartners().isEmpty() && getAddedShippingPartners().isEmpty()) {
      sb.append(" had no changes made to its shipping partner access.");
    }
    else {
      if (!getRemovedShippingPartners().isEmpty()) {
        sb.append(" had access revoked to the following ");

        if (getRemovedShippingPartners().size() > 1) {
          sb.append("shipping partners: ");
        }
        else {
          sb.append("shipping partner: ");
        }
        Iterator iterator = getRemovedShippingPartners().iterator();
        boolean addComma = false;
        while (iterator.hasNext()) {
          if (addComma) {
            sb.append(", ");
          }
          addComma = true;
          addIcpLink((ShippingPartnerDto)iterator.next(),sb);
        }
        sb.append(".");
      }
      if (!getAddedShippingPartners().isEmpty()) {
        if (getRemovedShippingPartners().isEmpty()) {
          sb.append(" had access granted to the following ");
        }
        else {
          // If we said something about removed shipping partners, start a new sentence.
          sb.append(" The account was granted access to the following ");
        }

        if (getAddedShippingPartners().size() > 1) {
          sb.append("shipping partners: ");
        }
        else {
          sb.append("shipping partner: ");
        }
        Iterator iterator = getAddedShippingPartners().iterator();
        boolean addComma = false;
        while (iterator.hasNext()) {
          if (addComma) {
            sb.append(", ");
          }
          addComma = true;

          addIcpLink((ShippingPartnerDto)iterator.next(),sb);
        }
        sb.append(".");
      }
    }
    return sb.toString();
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#describeIntoHistoryRecord(com.ardais.bigr.iltds.btx.BTXHistoryRecord)
   */
  public void describeIntoHistoryRecord(BTXHistoryRecord history) {
    super.describeIntoHistoryRecord(history);
    IdList addedSPIds = new IdList();
    StringList addedSPNames = new StringList();
    Iterator iterator = getAddedShippingPartners().iterator();
    while (iterator.hasNext()) {
      ShippingPartnerDto spdto = (ShippingPartnerDto)iterator.next();
      addedSPIds.add(spdto.getShippingPartnerId());
      addedSPNames.add(spdto.getShippingPartnerName());
    }
    IdList removedSPIds = new IdList();
    StringList removedSPNames = new StringList();
    iterator = getRemovedShippingPartners().iterator();
    while (iterator.hasNext()) {
      ShippingPartnerDto spdto = (ShippingPartnerDto)iterator.next();
      removedSPIds.add(spdto.getShippingPartnerId());
      removedSPNames.add(spdto.getShippingPartnerName());
    }
    history.setAttrib1(getAccountId());
    history.setIdList1(addedSPIds);
    history.setClob1(addedSPNames.pack());
    history.setIdList2(removedSPIds);
    history.setClob2(removedSPNames.pack());
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#populateFromHistoryRecord(com.ardais.bigr.iltds.btx.BTXHistoryRecord)
   */
  public void populateFromHistoryRecord(BTXHistoryRecord history) {
    super.populateFromHistoryRecord(history);
    IdList addedSPIds = history.getIdList1();
    StringList addedSPNames = new StringList(history.getClob1());
    Iterator idIterator = addedSPIds.iterator();
    Iterator nameIterator = addedSPNames.iterator();
    List addedSPs = new ArrayList();
    while (idIterator.hasNext() && nameIterator.hasNext()) {
      ShippingPartnerDto spdto = new ShippingPartnerDto();
      spdto.setShippingPartnerName((String)nameIterator.next());
      spdto.setShippingPartnerId((String)idIterator.next());
      addedSPs.add(spdto);
    }
    IdList removedSPIds = history.getIdList2();
    StringList removedSPNames = new StringList(history.getClob2());
    idIterator = removedSPIds.iterator();
    nameIterator = removedSPNames.iterator();
    List removedSPs = new ArrayList();
    while (idIterator.hasNext() && nameIterator.hasNext()) {
      ShippingPartnerDto spdto = new ShippingPartnerDto();
      spdto.setShippingPartnerName((String)nameIterator.next());
      spdto.setShippingPartnerId((String)idIterator.next());
      removedSPs.add(spdto);
    }
    setAccountId(history.getAttrib1());
    setAddedShippingPartners(addedSPs);
    setRemovedShippingPartners(removedSPs);
    // These fields don't correspond to anything in the history record
    // but we must set them anyways.
    setAvailableShippingPartners(null);
    setAssignedShippingPartners(null);
    setSelectedShippingPartners(null);
  }

  private void addIcpLink(ShippingPartnerDto spdto, StringBuffer sb) {
    String shippingPartnerName = spdto.getShippingPartnerName();
    String shippingPartnerId = spdto.getShippingPartnerId();

    sb.append(IcpUtils.prepareLink(shippingPartnerId, shippingPartnerName, getLoggedInUserSecurityInfo()));
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
  public List getAddedShippingPartners() {
    return _addedShippingPartners;
  }

  /**
   * @return
   */
  public List getAssignedShippingPartners() {
    return _assignedShippingPartners;
  }

  /**
   * @return
   */
  public List getAvailableShippingPartners() {
    return _availableShippingPartners;
  }

  /**
   * @return
   */
  public List getRemovedShippingPartners() {
    return _removedShippingPartners;
  }

  /**
   * @return
   */
  public String[] getSelectedShippingPartners() {
    return _selectedShippingPartners;
  }

  /**
   * @param string
   */
  public void setAccountId(String string) {
    _accountId = string;
  }

  /**
   * @param list
   */
  public void setAddedShippingPartners(List list) {
    _addedShippingPartners = list;
  }

  /**
   * @param list
   */
  public void setAssignedShippingPartners(List list) {
    _assignedShippingPartners = list;
  }

  /**
   * @param list
   */
  public void setAvailableShippingPartners(List list) {
    _availableShippingPartners = list;
  }

  /**
   * @param list
   */
  public void setRemovedShippingPartners(List list) {
    _removedShippingPartners = list;
  }

  /**
   * @param strings
   */
  public void setSelectedShippingPartners(String[] strings) {
    _selectedShippingPartners = strings;
  }
}
