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
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.btx.framework.BtxHistoryAttributes;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.iltds.helpers.ProductType;
import com.ardais.bigr.iltds.helpers.RequestType;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.javabeans.ProductDto;
import com.ardais.bigr.javabeans.RequestDto;
import com.ardais.bigr.javabeans.RequestItemDto;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 * Abstract class to represent a create request business transaction.
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
public abstract class BtxDetailsCreateRequest extends BtxDetailsRequestOperation implements Serializable {
  private static final long serialVersionUID = 4331424057953350328L;
  
  public BtxDetailsCreateRequest() {
    super();
  }

  public abstract RequestType getRequestType();
  
  protected String doGetDetailsAsHTML() {
    // NOTE: This method must not make use of any fields that aren't
    // set by the populateFromHistoryRecord method.
    //
    // For this object type, the fields we can use here are:
    //   getOutputRequestDto().getId()
    //   getOutputRequestDto().getDestinationId()
    //   getOutputRequestDto().getDestinationName()
    //   getOutputRequestDto().getItems() (ids (and as of 5.9 itemData) only - the item types 
    //                                     are not logged)
        
    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();

    StringBuffer sb = new StringBuffer(2048);

    // The result has this form:
    // Request <request id> was created, requesting the following items: <item ids>
    
    RequestDto requestDto = getOutputRequestDto();

    sb.append(getRequestType().toString());
    sb.append(" request ");
    sb.append(IcpUtils.prepareLink(requestDto.getId(), securityInfo));
    if (!ApiFunctions.isEmpty(requestDto.getDestinationName())) {
      sb.append(", with a destination of ");
      Escaper.htmlEscape(requestDto.getDestinationName(), sb);
      sb.append(",");
    }
    sb.append(" was created ");
    List items = requestDto.getItems();
    if (ApiFunctions.isEmpty(items)) {
      sb.append("and contained no items");
    }
    else {
      sb.append("requesting the following items: ");

      IdList itemIds = new IdList();
      List linkTexts = new ArrayList();
      Iterator itemIterator = items.iterator();
      while (itemIterator.hasNext()) {
        RequestItemDto item = (RequestItemDto)itemIterator.next();
        itemIds.add(item.getItemId());
        if (item.getItem() != null) {
          linkTexts.add(IltdsUtils.getSampleIdAndAlias(item.getItem().getSampleData()));
        }
        else {
          linkTexts.add(item.getItemId());
        }
      }
      itemIds.appendICPHTML(sb, linkTexts, securityInfo);
    }
    sb.append(".");
    return sb.toString();
  }
  
  public java.util.Set getDirectlyInvolvedObjects() {
    Set set = new HashSet();
    if (getOutputRequestDto() != null) {
      if (getOutputRequestDto().getId() != null) {
        set.add(getOutputRequestDto().getId());
      }
      if (!ApiFunctions.isEmpty(getOutputRequestDto().getItems())) {
        Iterator itemIterator = getOutputRequestDto().getItems().iterator();
        while (itemIterator.hasNext()) {
          set.add(((RequestItemDto)itemIterator.next()).getItemId());
        }
      }
    }
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
    IdList itemIds = new IdList();
    Iterator itemIterator = getOutputRequestDto().getItems().iterator();
    while (itemIterator.hasNext()) {
      itemIds.add(((RequestItemDto)itemIterator.next()).getItemId());
    }
    history.setAttrib1(getOutputRequestDto().getId());
    history.setAttrib2(getOutputRequestDto().getDestinationId());
    history.setAttrib3(getOutputRequestDto().getDestinationName());
    history.setIdList1(itemIds);
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
    req.setDestinationId(history.getAttrib2());
    req.setDestinationName(history.getAttrib3());
    
    //if we stored information about the samples on the request, build a map of request items
    Map itemMap = new HashMap();
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
          itemMap.put(sampleId, item);
        }
      }
    }
    
    Iterator idIterator = history.getIdList1().iterator();

    while (idIterator.hasNext()) {
      String id = (String)idIterator.next();
      RequestItemDto item = (RequestItemDto)itemMap.get(id);
      if (item != null) {
        req.addItem(item);
      }
      else {
        req.addItem(id);
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
  
}
