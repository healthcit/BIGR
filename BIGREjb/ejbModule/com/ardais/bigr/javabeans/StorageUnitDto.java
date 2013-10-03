package com.ardais.bigr.javabeans;

import java.io.Serializable;

public class StorageUnitDto implements Serializable {
  
  private String _storageTypeCui;
  private String _roomId;
  private String _unitName;
  private String _numberOfDrawers;
  private String _slotsPerDrawer;
  
  /**
   * @return Returns the numberOfDrawers.
   */
  public String getNumberOfDrawers() {
    return _numberOfDrawers;
  }
  /**
   * @return Returns the roomId.
   */
  public String getRoomId() {
    return _roomId;
  }
  /**
   * @return Returns the slotsPerDrawer.
   */
  public String getSlotsPerDrawer() {
    return _slotsPerDrawer;
  }
  /**
   * @return Returns the storageTypeCui.
   */
  public String getStorageTypeCui() {
    return _storageTypeCui;
  }
  /**
   * @return Returns the unitName.
   */
  public String getUnitName() {
    return _unitName;
  }
  /**
   * @param numberOfDrawers The numberOfDrawers to set.
   */
  public void setNumberOfDrawers(String numberOfDrawers) {
    _numberOfDrawers = numberOfDrawers;
  }
  /**
   * @param roomId The roomId to set.
   */
  public void setRoomId(String roomId) {
    _roomId = roomId;
  }
  /**
   * @param slotsPerDrawer The slotsPerDrawer to set.
   */
  public void setSlotsPerDrawer(String slotsPerDrawer) {
    _slotsPerDrawer = slotsPerDrawer;
  }
  /**
   * @param storageTypeCui The storageTypeCui to set.
   */
  public void setStorageTypeCui(String storageTypeCui) {
    _storageTypeCui = storageTypeCui;
  }
  /**
   * @param unitName The unitName to set.
   */
  public void setUnitName(String unitName) {
    _unitName = unitName;
  }
}
