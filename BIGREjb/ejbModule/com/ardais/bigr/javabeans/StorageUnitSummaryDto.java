package com.ardais.bigr.javabeans;

import java.io.Serializable;

import com.ardais.bigr.api.ApiFunctions;
import com.gulfstreambio.gboss.GbossFactory;

public class StorageUnitSummaryDto implements Serializable {
  
  private String _storageTypeCui;
  private String _roomId;
  private String _unitName;
  private String _numberOfDrawers;
  private String _minimumSlotId;
  private String _maximumSlotId;
  
  /**
   * @return Returns the maximumSlotId.
   */
  public String getMaximumSlotId() {
    return _maximumSlotId;
  }
  /**
   * @return Returns the minimumSlotId.
   */
  public String getMinimumSlotId() {
    return _minimumSlotId;
  }
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
   * @return Returns the storageTypeCui.
   */
  public String getStorageTypeCui() {
    return _storageTypeCui;
  }
  /**
   * @return Returns the storageTypeCui.
   */
  public String getStorageTypeDescription() {
    if (!ApiFunctions.isEmpty(_storageTypeCui)) {
      return GbossFactory.getInstance().getDescription(_storageTypeCui);
    }
    else {
      return "";
    }
  }
  
  /**
   * @return Returns the unitName.
   */
  public String getUnitName() {
    return _unitName;
  }
  /**
   * @param maximumSlotId The maximumSlotId to set.
   */
  public void setMaximumSlotId(String maximumSlotId) {
    _maximumSlotId = maximumSlotId;
  }
  /**
   * @param minimumSlotId The minimumSlotId to set.
   */
  public void setMinimumSlotId(String minimumSlotId) {
    _minimumSlotId = minimumSlotId;
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
