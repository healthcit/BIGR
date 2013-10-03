package com.ardais.bigr.javabeans;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.assistants.Address;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.helpers.RequestState;
import com.ardais.bigr.iltds.helpers.RequestType;
import com.ardais.bigr.util.gen.DbAliases;

/**
 * This represents a Request.  Request objects are stored in the database in ILTDS_REQUEST
 * and its dependent tables (ILTDS_REQUEST_ITEM, ILTDS_REQUEST_BOX).
 */
public class RequestDto implements Serializable {
  static final long serialVersionUID = 8937554766353031013L;

  private String _id;
  private Timestamp _createDate;
  private RequestState _state;
  private boolean _closed;
  private String _requesterAccount;
  private String _requesterId;
  private String _requesterName;
  private String _deliverToAccount;
  private String _deliverToId;
  private String _deliverToName;
  private String _requesterComments;
  private String _destinationId;
  private String _destinationName;
  private String _requestManagerComments;
  private RequestType _type;
  private List _items = new ArrayList();
  private List _boxes = new ArrayList();
  private List _shippingPartners;
  private Address _shippingAddress;

  /**
   * @return RequestDto - a new RequestDto, that is an exact copy of this RequestDto.
   */
  public Object clone(){
    RequestDto newDto = new RequestDto();
    newDto.setBoxes(new ArrayList(this.getBoxes()));
    newDto.setClosed(this.isClosed());
    newDto.setCreateDate(this.getCreateDate());
    newDto.setDeliverToId(this.getDeliverToId());
    newDto.setDeliverToName(this.getDeliverToName());
    newDto.setDestinationId(this.getDestinationId());
    newDto.setDestinationName(this.getDestinationName());
    newDto.setId(this.getId());
    newDto.setItems(new ArrayList(this.getItems()));
    newDto.setRequesterComments(this.getRequesterComments());
    newDto.setRequesterId(this.getRequesterId());
    newDto.setRequesterName(this.getRequesterName());
    newDto.setRequestManagerComments(this.getRequestManagerComments());
    newDto.setState(this.getState());
    newDto.setType(this.getType());
    if (!ApiFunctions.isEmpty(this.getShippingPartners())) {
      newDto.setShippingPartners(new ArrayList(this.getShippingPartners()));
    }
    if (this.getShippingAddress() != null) {
      newDto.setShippingAddress(new Address(this.getShippingAddress()));
    }
    return newDto;
  }
  /**
   * @return
   */
  public List getBoxes() {
    return _boxes;
  }

  /**
   * @return
   */
  public boolean isClosed() {
    return _closed;
  }
  
  public String getStatus() {
    if (isClosed()) {
      return "Closed";
    }
    else {
      return "Open";
    }
  }

  /**
   * @return
   */
  public Timestamp getCreateDate() {
    return _createDate;
  }

  /**
   * @return
   */
  public String getDestinationId() {
    return _destinationId;
  }

  /**
   * @return
   */
  public String getDestinationName() {
    return _destinationName;
  }

  /**
   * @return
   */
  public String getId() {
    return _id;
  }

  /**
   * @return the list of items in the request.  Each item is a {@link RequestItemDto}.
   */
  public List getItems() {
    return _items;
  }

  /**
   * @return The number of items in the request.
   */
  public int getItemCount() {
    return ((_items == null) ? 0 : _items.size());
  }

  /**
   * @return
   */
  public String getRequesterAccount() {
    return _requesterAccount;
  }

  /**
   * @return
   */
  public String getRequesterComments() {
    return _requesterComments;
  }

  /**
   * @return
   */
  public String getRequesterId() {
    return _requesterId;
  }

  /**
   * @return
   */
  public String getRequesterName() {
    return _requesterName;
  }

  /**
   * @return
   */
  public String getRequestManagerComments() {
    return _requestManagerComments;
  }

  /**
   * @return
   */
  public RequestState getState() {
    return _state;
  }

  /**
   * @return
   */
  public RequestType getType() {
    return _type;
  }

  /**
   * @return
   */
  public String getDeliverToAccount() {
    return _deliverToAccount;
  }

  /**
   * @return
   */
  public String getDeliverToId() {
    return _deliverToId;
  }

  /**
   * @return
   */
  public String getDeliverToName() {
    return _deliverToName;
  }

  /**
   * @param list the list of items in the request.  Each item is a {@link RequestItemDto}.
   */
  public void setBoxes(List list) {
    _boxes = list;
  }

  public void addBox(String boxId) {
    RequestBoxDto rbd = new RequestBoxDto();
    rbd.setBoxId(boxId);
    addBox(rbd);
  }
  
  public void addBox(RequestBoxDto box) {
    _boxes.add(box);
  }

  /**
   * @param b
   */
  public void setClosed(boolean b) {
    _closed = b;
  }

  /**
   * @param date
   */
  public void setCreateDate(Timestamp date) {
    _createDate = date;
  }

  /**
   * @param string
   */
  public void setDestinationId(String string) {
    _destinationId = string;
  }

  /**
   * @param string
   */
  public void setDestinationName(String string) {
    _destinationName = string;
  }

  /**
   * @param string
   */
  public void setId(String string) {
    _id = string;
  }

  /**
   * @param list
   */
  public void setItems(List list) {
    _items = list;
  }
  
  public void addItem(String itemId) {
    RequestItemDto rid = new RequestItemDto();
    rid.setItemId(itemId);
    addItem(rid);
  }
  
  public void addItem(RequestItemDto item) {
    _items.add(item);
  }

  /**
   * @param string
   */
  public void setRequesterAccount(String string) {
    _requesterAccount = string;
  }

  /**
   * @param string
   */
  public void setRequesterComments(String string) {
    _requesterComments = string;
  }

  /**
   * @param string
   */
  public void setRequesterId(String string) {
    _requesterId = string;
  }

  /**
   * @param string
   */
  public void setRequesterName(String string) {
    _requesterName = string;
  }

  /**
   * @param string
   */
  public void setRequestManagerComments(String string) {
    _requestManagerComments = string;
  }

  /**
   * @param state
   */
  public void setState(RequestState state) {
    _state = state;
  }

  /**
   * @param type
   */
  public void setType(RequestType type) {
    _type = type;
  }

  /**
   * @param string
   */
  public void setDeliverToAccount(String string) {
    _deliverToAccount = string;
  }

  /**
   * @param string
   */
  public void setDeliverToId(String string) {
    _deliverToId = string;
  }

  /**
   * @param string
   */
  public void setDeliverToName(String string) {
    _deliverToName = string;
  }

  /**
   * Populates this <code>RequestDto</code> from the data in the current row
   * of the result set.
   * 
   * @param  columns  a <code>Map</code> containing a key for each column in
   *                   the <code>ResultSet</code>.  Each key must be one of the
   *                   column alias constants in {@link com.ardais.bigr.util.DbAliases}
   *                   and the corresponding value is ignored.
   * @param  rs  the <code>ResultSet</code>
   */
  public void populate(Map columns, ResultSet rs) {
    try {
      if (columns.containsKey(DbAliases.REQUEST_REQUEST_ID)) {
        setId(rs.getString(DbAliases.REQUEST_REQUEST_ID));
      }
      if (columns.containsKey(DbAliases.REQUEST_CREATE_DATE)) {
        setCreateDate(rs.getTimestamp(DbAliases.REQUEST_CREATE_DATE));
      }
      if (columns.containsKey(DbAliases.REQUEST_STATE)) {
        setState(RequestState.getInstance(rs.getString(DbAliases.REQUEST_STATE)));
      }
      if (columns.containsKey(DbAliases.REQUEST_CLOSED_YN)) {
        setClosed("Y".equals(rs.getString(DbAliases.REQUEST_CLOSED_YN).toUpperCase()));
      }
      if (columns.containsKey(DbAliases.REQUEST_REQUESTER_USER_ID)) {
        setRequesterId(rs.getString(DbAliases.REQUEST_REQUESTER_USER_ID));
      }
      if (columns.containsKey(DbAliases.REQUEST_DELIVER_TO_USER_ID)) {
        setDeliverToId(rs.getString(DbAliases.REQUEST_DELIVER_TO_USER_ID));
      }
      if (columns.containsKey(DbAliases.REQUEST_REQUESTER_COMMENTS)) {
        setRequesterComments(rs.getString(DbAliases.REQUEST_REQUESTER_COMMENTS));
      }
      if (columns.containsKey(DbAliases.REQUEST_DESTINATION_ID)) {
        setDestinationId(rs.getString(DbAliases.REQUEST_DESTINATION_ID));
      }
      if (columns.containsKey(DbAliases.REQUEST_REQUEST_MGR_COMMENTS)) {
        setRequestManagerComments(rs.getString(DbAliases.REQUEST_REQUEST_MGR_COMMENTS));
      }
      if (columns.containsKey(DbAliases.REQUEST_REQUEST_TYPE)) {
        setType(RequestType.getInstance(rs.getString(DbAliases.REQUEST_REQUEST_TYPE)));
      }
    } catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

  /**
   * @return
   */
  public List getShippingPartners() {
    return _shippingPartners;
  }

  /**
   * @param set
   */
  public void setShippingPartners(List list) {
    _shippingPartners = list;
  }

  /**
   * @return
   */
  public Address getShippingAddress() {
    return _shippingAddress;
  }

  /**
   * @param address
   */
  public void setShippingAddress(Address address) {
    _shippingAddress = address;
  }

}