package com.ardais.bigr.javabeans;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.iltds.assistants.Address;

/**
 * A Data Transfer Object that represents a shipping manifest.
 */
public class ManifestDto implements Serializable {
  static final long serialVersionUID = 6948203343168721213L;

  private String _manifestNumber;
  private String _trackingNumber;
  private String _shipmentStatus;
  private String _shipFromAddressId;
  private Address _shipFromAddress;
  private String _shipToAddressId;
  private Address _shipToAddress;
  private Timestamp _createDate;
  private String _createUserId;
  private Timestamp _shipDate;
  private String _shipUserId;
  private Timestamp _shipVerifyDate;
  private String _shipVerifyUserId;
  private Timestamp _receiptVerifyDate;
  private String _receiptVerifyUserId;
  private Timestamp _receiptDate;
  private String _receiptUserId;
  private String _producedByName;
  private List _boxIdList;
  private List _boxes;

  /**
   * Creates a new ManifestDto.
   */
  public ManifestDto() {
    super();
  }
  /**
   * Creates a new <code>ManifestDto</code>, initialized from
   * the data in the ManifestDto passed in.
   *
   * @param  manifestDto  the <code>ManifestDto</code>
   */
  public ManifestDto(ManifestDto manifestDto) {
    this();
    BigrBeanUtilsBean.SINGLETON.copyProperties(this, manifestDto);
    //any mutable objects must be handled seperately (BigrBeanUtilsBean.copyProperties
    //does a shallow copy.
    if (manifestDto.getShipFromAddress() != null) {
      setShipFromAddress(new Address(manifestDto.getShipFromAddress()));
    }
    if (manifestDto.getShipToAddress() != null) {
      setShipToAddress(new Address(manifestDto.getShipToAddress()));
    }
    if (manifestDto.getCreateDate() != null) {
      setCreateDate((Timestamp) manifestDto.getCreateDate().clone());
    }
    if (manifestDto.getShipDate() != null) {
      setShipDate((Timestamp) manifestDto.getShipDate().clone());
    }
    if (manifestDto.getShipVerifyDate() != null) {
      setShipVerifyDate((Timestamp) manifestDto.getShipVerifyDate().clone());
    }
    if (manifestDto.getReceiptVerifyDate() != null) {
      setReceiptVerifyDate((Timestamp) manifestDto.getReceiptVerifyDate().clone());
    }
    if (manifestDto.getReceiptDate() != null) {
      setReceiptDate((Timestamp) manifestDto.getReceiptDate().clone());
    }
    //box id list is a list of strings
    if (!ApiFunctions.isEmpty(manifestDto.getBoxIdList())) {
      _boxIdList.clear();
      Iterator iterator = manifestDto.getBoxIdList().iterator();
      while (iterator.hasNext()) {
        _boxIdList.add((String)iterator.next());
      }
    }
    if (!ApiFunctions.isEmpty(manifestDto.getBoxes())) {
      _boxes.clear();
      Iterator iterator = manifestDto.getBoxes().iterator();
      while (iterator.hasNext()) {
        _boxes.add(new BoxDto((BoxDto)iterator.next()));
      }
    }
  }

  public List getBoxIdList() {
    if (_boxIdList == null) {
      _boxIdList = new ArrayList();
    }
    return _boxIdList;
  }

  public void setBoxIdList(List boxIds) {
    _boxIdList = boxIds;
  }

  public void addBoxId(String boxId) {
    getBoxIdList().add(boxId);
  }

  /**
   * Returns a list of {@link BoxDto} objects.
   * 
   * @return The list of boxes.
   */
  public List getBoxes() {
    if (_boxes == null) {
      _boxes = new ArrayList();
    }
    return _boxes;
  }
  public void addBox(BoxDto dto) {
    getBoxes().add(dto);
  }

  public Timestamp getCreateDate() {
    return _createDate;
  }

  public String getCreateUserId() {
    return _createUserId;
  }

  public String getManifestNumber() {
    return _manifestNumber;
  }

  public String getShipFromAddressId() {
    return _shipFromAddressId;
  }

  public String getShipmentStatus() {
    return _shipmentStatus;
  }

  public String getShipToAddressId() {
    return _shipToAddressId;
  }

  public String getTrackingNumber() {
    return _trackingNumber;
  }

  public void setCreateDate(Timestamp timestamp) {
    _createDate = timestamp;
  }

  public void setCreateUserId(String string) {
    _createUserId = string;
  }

  public void setManifestNumber(String string) {
    _manifestNumber = string;
  }

  public void setShipFromAddressId(String string) {
    _shipFromAddressId = string;
  }

  public void setShipmentStatus(String string) {
    _shipmentStatus = string;
  }

  public void setShipToAddressId(String string) {
    _shipToAddressId = string;
  }

  public void setTrackingNumber(String string) {
    _trackingNumber = string;
  }

  public Timestamp getReceiptDate() {
    return _receiptDate;
  }

  public String getReceiptUserId() {
    return _receiptUserId;
  }

  public Timestamp getReceiptVerifyDate() {
    return _receiptVerifyDate;
  }

  public String getReceiptVerifyUserId() {
    return _receiptVerifyUserId;
  }

  public Timestamp getShipDate() {
    return _shipDate;
  }

  public String getShipUserId() {
    return _shipUserId;
  }

  public Timestamp getShipVerifyDate() {
    return _shipVerifyDate;
  }

  public String getShipVerifyUserId() {
    return _shipVerifyUserId;
  }

  public void setReceiptDate(Timestamp timestamp) {
    _receiptDate = timestamp;
  }

  public void setReceiptUserId(String string) {
    _receiptUserId = string;
  }

  public void setReceiptVerifyDate(Timestamp timestamp) {
    _receiptVerifyDate = timestamp;
  }

  public void setReceiptVerifyUserId(String string) {
    _receiptVerifyUserId = string;
  }

  public void setShipDate(Timestamp timestamp) {
    _shipDate = timestamp;
  }

  public void setShipUserId(String string) {
    _shipUserId = string;
  }

  public void setShipVerifyDate(Timestamp timestamp) {
    _shipVerifyDate = timestamp;
  }

  public void setShipVerifyUserId(String string) {
    _shipVerifyUserId = string;
  }

  public Address getShipFromAddress() {
    return _shipFromAddress;
  }

  public Address getShipToAddress() {
    return _shipToAddress;
  }

  public void setShipFromAddress(Address address) {
    _shipFromAddress = address;
  }

  public void setShipToAddress(Address address) {
    _shipToAddress = address;
  }

  public String getProducedByName() {
    return _producedByName;
  }

  public void setProducedByName(String string) {
    _producedByName = string;
  }

  public void populateFromResultSet(Map columns, ResultSet rs) throws SQLException {
    if (columns.containsKey("manifest_number")) {
      setManifestNumber(rs.getString("manifest_number"));
    }
    if (columns.containsKey("airbill_tracking_number")) {
      setTrackingNumber(rs.getString("airbill_tracking_number"));
    }
    if (columns.containsKey("ship_from_addr_id")) {
      setShipFromAddressId(rs.getString("ship_from_addr_id"));
    }
    if (columns.containsKey("ship_to_addr_id")) {
      setShipToAddressId(rs.getString("ship_to_addr_id"));
    }
    if (columns.containsKey("mnft_create_datetime")) {
      setCreateDate(rs.getTimestamp("mnft_create_datetime"));
    }
    if (columns.containsKey("mnft_create_staff_id")) {
      setCreateUserId(rs.getString("mnft_create_staff_id"));
    }
    if (columns.containsKey("ship_datetime")) {
      setShipDate(rs.getTimestamp("ship_datetime"));
    }
    if (columns.containsKey("ship_staff_id")) {
      setShipUserId(rs.getString("ship_staff_id"));
    }
    if (columns.containsKey("ship_verify_datetime")) {
      setShipVerifyDate(rs.getTimestamp("ship_verify_datetime"));
    }
    if (columns.containsKey("ship_verify_staff_id")) {
      setShipVerifyUserId(rs.getString("ship_verify_staff_id"));
    }
    if (columns.containsKey("receipt_verify_datetime")) {
      setReceiptVerifyDate(rs.getTimestamp("receipt_verify_datetime"));
    }
    if (columns.containsKey("receipt_verify_staff_id")) {
      setReceiptVerifyUserId(rs.getString("receipt_verify_staff_id"));
    }
    if (columns.containsKey("receipt_datetime")) {
      setReceiptDate(rs.getTimestamp("receipt_datetime"));
    }
    if (columns.containsKey("receipt_by_staff_id")) {
      setReceiptUserId(rs.getString("receipt_by_staff_id"));
    }
    if (columns.containsKey("shipment_status")) {
      setShipmentStatus(rs.getString("shipment_status"));
    }
  }
}
