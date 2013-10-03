package com.ardais.bigr.iltds.btx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.btx.framework.BtxHistoryAttributes;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.iltds.helpers.ProductType;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.javabeans.ProductDto;
import com.ardais.bigr.javabeans.RequestBoxDto;
import com.ardais.bigr.javabeans.RequestDto;
import com.ardais.bigr.javabeans.RequestItemDto;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 * Class to represent a fulfill request business transaction.
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
 * <li>{@link #setNewBoxId(String) newBoxId}: The id of a newly scanned box to
 * be used in fulfilling the request.</li>
 * <li>{@link #setNewBoxLayoutId(String) newBoxLayoutId}: The box layout id to use on the newly
 * scanned box used in fulfilling the request.</li>
 * <li>{@link #setPartialFulfillConfirmed(boolean) partialFulfillConfirmed}: A boolean to indicate
 * whether or not the user has confirmed that they intended to partially fulfill the request.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setOutputRequestDto(RequestDto) outputRequestDto}: The RequestDto containing all 
 *      necessary output data.</li>
 * <li>{@link #setFulfilledItems(IdList) fulfilledItems}: An IdList containing all fulfilled items
 *      on the request.</li>
 * <li>{@link #setUnfulfilledItems(IdList) unfulfilledItems}: An IdList containing all unfulfilled items
 *      on the request.</li>
 * </ul>
 */
public class BtxDetailsFulfillRequest extends BtxDetailsFindRequestDetails implements Serializable {
  private static final long serialVersionUID = 699577070792619699L;

  private String _newBoxId;
  private String _newBoxLayoutId;
  private boolean _partialRequest;
  private boolean _partialFulfillConfirmed;
  private IdList _fulfilledItems;
  private IdList _unfulfilledItems;
  
  /**
   * Constructor.
   */
  public BtxDetailsFulfillRequest() {
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
    history.setIdList1(getFulfilledItems());
    history.setIdList2(getUnfulfilledItems());
    //since there are only 2 idlists on the history record, we need to use
    //one of the clob fields to store the ids of the boxes used to fulfill the request.
    IdList boxIds = new IdList();
    Iterator boxIterator = getOutputRequestDto().getBoxes().iterator();
    while (boxIterator.hasNext()) {
      boxIds.add(((RequestBoxDto)boxIterator.next()).getBoxId());
    }
    history.setClob1(boxIds.pack());
    history.setHistoryObject(describeAsHistoryObject());
  }
  
  /**
   * Returns a BtxHistoryAttributes that describes the samples involved
   * in the request.  This method creates an attribute for each sample id, with a value
   * of a BtxHistoryAttributes object that contains various values for each sample.  For now
   * the only value that we store is the sample alias, but additional attributes may be added
   * as needed.
   */
  private Object describeAsHistoryObject() {
    BtxHistoryAttributes attributes = null;
    List items = getOutputRequestDto().getItems();
    if (!ApiFunctions.isEmpty(items)) {
      attributes = new BtxHistoryAttributes();
      Iterator itemIterator = items.iterator();
      while (itemIterator.hasNext()) {
        RequestItemDto item = (RequestItemDto) itemIterator.next();
        if (ProductType.SAMPLE.equals(item.getItemType())) {
          BtxHistoryAttributes sampleAttributes = new BtxHistoryAttributes();
          attributes.setAttribute(item.getItemId(), sampleAttributes);
          sampleAttributes.setAttribute("sampleAlias", item.getItem().getSampleData().getSampleAlias());
        }
      }
    }
    return attributes;
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
    //   getFulfilledItems()
    //   getUnfulfilledItems()
    //   getOutputRequestDto().getBoxes()
    //   getOutputRequestDto().getItems()
        
    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();
    
    Map itemIdToItemMap = new HashMap();
    if (!ApiFunctions.isEmpty(getOutputRequestDto().getItems())) {
      Iterator itemIterator = getOutputRequestDto().getItems().iterator();
      while (itemIterator.hasNext()) {
        RequestItemDto item = (RequestItemDto)itemIterator.next();
        itemIdToItemMap.put(item.getItemId(), item);
      }
    }

    StringBuffer sb = new StringBuffer(2048);

    // The result can take one of two forms.  If the request was completely filled, it
    // takes this form:
    //   Request <request id> was fulfilled.  All item(s) on this request (<filled item ids>) were 
    //   packaged in box(es) <box ids>, and these items and boxes were checked out of inventory.
    // If the request was partially filled, it takes this form:
    //   Request <request id> was partially fulfilled.  Some item(s) on the request (<unfilled item ids>)
    //   were not included.  The other item(s) on the request (<filled item ids>) were packaged in
    //   box(es) <box ids>, and these items and boxes were checked out of inventory.

    boolean complete = getUnfulfilledItems().size() <= 0;
    sb.append("Request ");
    sb.append(IcpUtils.prepareLink(getOutputRequestDto().getId(), securityInfo));
    sb.append(" was ");
    if (complete) {
      sb.append("fulfilled.  All item(s) on this request (");
    }
    else {
      sb.append("partially fulfilled.  Some item(s) on the request (");
      List linkTexts = new ArrayList();
      Iterator idIterator = getUnfulfilledItems().iterator();
      while (idIterator.hasNext()) {
        String id = (String)idIterator.next();
        RequestItemDto item = (RequestItemDto)itemIdToItemMap.get(id);
        StringBuffer linkText = new StringBuffer(50);
        if (item != null) {
          linkText.append(IltdsUtils.getSampleIdAndAlias(item.getItem().getSampleData()));
        }
        else {
          linkText.append(id);
        }
        linkTexts.add(linkText.toString());
      }
      getUnfulfilledItems().appendICPHTML(sb, linkTexts, securityInfo);
      sb.append(") were not included.  The other item(s) on the request (");
    }
    List linkTexts = new ArrayList();
    Iterator idIterator = getFulfilledItems().iterator();
    while (idIterator.hasNext()) {
      String id = (String)idIterator.next();
      RequestItemDto item = (RequestItemDto)itemIdToItemMap.get(id);
      StringBuffer linkText = new StringBuffer(50);
      if (item != null) {
        linkText.append(IltdsUtils.getSampleIdAndAlias(item.getItem().getSampleData()));
      }
      else {
        linkText.append(id);
      }
      linkTexts.add(linkText.toString());
    }
    getFulfilledItems().appendICPHTML(sb, linkTexts, securityInfo);
    sb.append(") were packaged in ");
    if (getOutputRequestDto().getBoxes().size() > 1) {
      sb.append("boxes ");
    }
    else {
      sb.append("box ");
    }
    Iterator boxIterator = getOutputRequestDto().getBoxes().iterator();
    boolean boxProcessed = false;
    while (boxIterator.hasNext()) {
      String boxId = ((RequestBoxDto)boxIterator.next()).getBoxId();
      if (boxProcessed) {
        if (!boxIterator.hasNext()) {
          sb.append(" and ");
        }
        else {
          sb.append(", ");
        }
      }
      boxProcessed = true;
      sb.append(IcpUtils.prepareLink(boxId,securityInfo));
    }
    sb.append(", and these items and boxes were checked out of inventory.");
    return sb.toString();
  }

  /**
   * Return the business transaction type code for the transaction that this
   * class represents.  Derived classes must override this method.
   *
   * @return the transaction type code.
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_FULFILL_REQUEST;
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
    Iterator itemIterator = getFulfilledItems().iterator();
    while (itemIterator.hasNext()) {
      set.add((String)itemIterator.next());
    }
    //include the unfulfilled items as well, so that the history for
    //those items shows them being placed on the request AND the fact
    //they were not ultimately included in the fulfillment of that
    //request.  Otherwise, ICP shows them being put on the request but
    //nothing further about what happened to them in the context of
    //that request
    itemIterator = getUnfulfilledItems().iterator();
    while (itemIterator.hasNext()) {
      set.add((String)itemIterator.next());
    }
    Iterator boxIterator = getOutputRequestDto().getBoxes().iterator();
    while (boxIterator.hasNext()) {
      set.add(((RequestBoxDto)boxIterator.next()).getBoxId());
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
    setFulfilledItems(history.getIdList1());
    setUnfulfilledItems(history.getIdList2());
    //since there are only 2 idlists on the history record, we needed to use
    //one of the clob fields to store the ids of the boxes used to fulfill the request.
    IdList boxIds = new IdList(history.getClob1());
    Iterator boxIdIterator = boxIds.iterator();
    while (boxIdIterator.hasNext()) {
      req.addBox((String)boxIdIterator.next());
    }
    
    //if we stored information about the items on the request, repopulate request items
    BtxHistoryAttributes attributes = (BtxHistoryAttributes)history.getHistoryObject();
    if (attributes != null) {
      Map map = attributes.asMap();
      if (!map.isEmpty()) {
        Iterator itemIdIterator = map.keySet().iterator();
        while (itemIdIterator.hasNext()) {
          String sampleId = (String)itemIdIterator.next();
          SampleData sample = new SampleData();
          sample.setSampleId(sampleId);
          populateSampleAttributesFromHistoryObject(attributes, sample);
          RequestItemDto item = new RequestItemDto();
          ProductDto product = new ProductDto();
          product.setSampleData(sample);
          item.setItem(product);
          item.setItemId(sampleId);
          req.addItem(item);
        }
      }
    }
    setOutputRequestDto(req);
  }
  
  private void populateSampleAttributesFromHistoryObject(BtxHistoryAttributes attributes, SampleData sample) {
    if (attributes != null) {
      BtxHistoryAttributes sampleAttributes = (BtxHistoryAttributes)attributes.getAttribute(sample.getSampleId());
      if (sampleAttributes != null) {
        if (sampleAttributes.containsAttribute("sampleAlias")) {
          sample.setSampleAlias((String)sampleAttributes.getAttribute("sampleAlias"));
        }
      }
    }
  }

  /**
   * @return
   */
  public IdList getFulfilledItems() {
    return _fulfilledItems;
  }

  /**
   * @return
   */
  public String getNewBoxId() {
    return _newBoxId;
  }

  /**
   * @return
   */
  public String getNewBoxLayoutId() {
    return _newBoxLayoutId;
  }

  /**
   * @return
   */
  public boolean isPartialFulfillConfirmed() {
    return _partialFulfillConfirmed;
  }

  /**
   * @return
   */
  public boolean isPartialRequest() {
    return _partialRequest;
  }

  /**
   * @return
   */
  public IdList getUnfulfilledItems() {
    return _unfulfilledItems;
  }

  /**
   * @param list
   */
  public void setFulfilledItems(IdList list) {
    _fulfilledItems = list;
  }

  /**
   * @param string
   */
  public void setNewBoxId(String string) {
    _newBoxId = string;
  }

  /**
   * @param string
   */
  public void setNewBoxLayoutId(String string) {
    _newBoxLayoutId = string;
  }

  /**
   * @param b
   */
  public void setPartialFulfillConfirmed(boolean b) {
    _partialFulfillConfirmed = b;
  }

  /**
   * @param b
   */
  public void setPartialRequest(boolean b) {
    _partialRequest = b;
  }

  /**
   * @param list
   */
  public void setUnfulfilledItems(IdList list) {
    _unfulfilledItems = list;
  }

}
