package com.ardais.bigr.iltds.assistants;

import java.io.Serializable;

public class StorageLocAsst implements Serializable {
  
  private String _locationAddressId;
  private String _roomId;
  private String _drawerId;
  private String _slotId;
  private String _boxBarcodeId;
  private String _availableInd = "Y";
  private String _locationStatus = "A";
  private String _unitName;
  private String _storageTypeCid;

  /**
   * @return
   */
  public String getAvailableInd() {
    return _availableInd;
  }

  /**
   * @return
   */
  public String getBoxBarcodeId() {
    return _boxBarcodeId;
  }

  /**
   * @return
   */
  public String getDrawerId() {
    return _drawerId;
  }

  /**
   * @return
   */
  public String getLocationAddressId() {
    return _locationAddressId;
  }

  /**
   * @return
   */
  public String getLocationStatus() {
    return _locationStatus;
  }

  /**
   * @return
   */
  public String getRoomId() {
    return _roomId;
  }

  /**
   * @return
   */
  public String getSlotId() {
    return _slotId;
  }

  /**
   * @return
   */
  public String getStorageTypeCid() {
    return _storageTypeCid;
  }

  /**
   * @return
   */
  public String getUnitName() {
    return _unitName;
  }

  /**
   * @param string
   */
  public void setAvailableInd(String string) {
    _availableInd = string;
  }

  /**
   * @param string
   */
  public void setBoxBarcodeId(String string) {
    _boxBarcodeId = string;
  }

  /**
   * @param string
   */
  public void setDrawerId(String string) {
    _drawerId = string;
  }

  /**
   * @param string
   */
  public void setLocationAddressId(String string) {
    _locationAddressId = string;
  }

  /**
   * @param string
   */
  public void setLocationStatus(String string) {
    _locationStatus = string;
  }

  /**
   * @param string
   */
  public void setRoomId(String string) {
    _roomId = string;
  }

  /**
   * @param string
   */
  public void setSlotId(String string) {
    _slotId = string;
  }

  /**
   * @param string
   */
  public void setStorageTypeCid(String string) {
    _storageTypeCid = string;
  }

  /**
   * @param string
   */
  public void setUnitName(String string) {
    _unitName = string;
  }
}
