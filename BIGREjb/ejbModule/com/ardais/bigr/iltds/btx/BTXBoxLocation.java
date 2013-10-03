package com.ardais.bigr.iltds.btx;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.security.SecurityInfo;
import com.gulfstreambio.gboss.GbossFactory;

/**
 * This class represents a box location that is involved in a business transaction.
 * It includes the site id and name, room, unit name, drawer, slot, and unit type
 * (for example frozen or paraffin).
 */
public class BTXBoxLocation implements java.io.Serializable {
  private static final long serialVersionUID = 4463588804097142500L;

  private String _roomID = null;
  private String _drawerID = null;
  private String _slotID = null;
  private String _locationAddressID = null;
  private String _locationAddressName = null;
  private String _unitName = null;
  private String _storageTypeCid = null;
  private String _storageTypeDesc = null;
  private String _boxBarcodeID = null;

  public BTXBoxLocation() {
    super();
  }
  
  public BTXBoxLocation(BTXBoxLocation btxBoxLocation) {
    this();
    BigrBeanUtilsBean.SINGLETON.copyProperties(this, btxBoxLocation);
  }

  /**
   * Append to the supplied string buffer an HTML representation
   * of the location that will contain ICP hyperlinks for the locations
   * once ICP supports location links.
   *
   * @param buffer the string buffer to append to.
   */
  public void appendICPHTML(StringBuffer buffer, SecurityInfo securityInfo) {

    // Format of result:
    //   <loc id> <room>: <storage type> <unit>/<drawer>/<slot>

    // Until we have a real location abbreviation field, the address id is
    // standing in for it.
    buffer.append(getLocationAddressID());
    buffer.append(' ');
    buffer.append(getRoomID());
    buffer.append(": ");
    buffer.append(getStorageTypeDesc());
    buffer.append(' ');
    buffer.append(getUnitName());
    buffer.append('/');
    buffer.append(getDrawerID());
    buffer.append('/');
    buffer.append(getSlotID());
  }

  /**
   * Return the drawer id.
   * 
   * @return the drawer id.
   */
  public String getDrawerID() {
    return _drawerID;
  }

  /**
   * Return the id of the geographic site (building) at which the box is located.
   * 
   * @return the site id.
   */
  public String getLocationAddressID() {
    return _locationAddressID;
  }

  /**
   * Return the user-friendly name of the geographic site (building) at which the box is located.
   * 
   * @return the site name.
   */
  public String getLocationAddressName() {
    return _locationAddressName;
  }

  /**
   * Return the room id.
   * 
   * @return the room id.
   */
  public String getRoomID() {
    return _roomID;
  }

  /**
   * Return the slot id.
   * 
   * @return the slot id.
   */
  public String getSlotID() {
    return _slotID;
  }

  
  public String getBoxBarcodeID() {
    return _boxBarcodeID;
  }
 
  public void setBoxBarcodeID(String barID) {
     _boxBarcodeID = barID;
  }
  
  /**
   * Set the drawer id.
   * 
   * @param newDrawerID the drawer id.
   */
  public void setDrawerID(String newDrawerID) {
    _drawerID = newDrawerID;
  }

  /**
   * Set the id of the geographic site (building) at which the box is located.
   * 
   * @param newLocationAddressID the site id.
   */
  public void setLocationAddressID(String newLocationAddressID) {
    _locationAddressID = newLocationAddressID;
  }

  /**
   * Set the user-friendly name of the geographic site (building) at which the box is located.
   * 
   * @param newLocationAddressName the site name.
   */
  public void setLocationAddressName(String newLocationAddressName) {
    _locationAddressName = newLocationAddressName;
  }

  /**
   * Set the room id.
   * 
   * @param newDrawerID the room id.
   */
  public void setRoomID(String newRoomID) {
    _roomID = newRoomID;
  }

  /**
   * Set the slot id.
   * 
   * @param newDrawerID the slot id.
   */
  public void setSlotID(String newSlotID) {
    _slotID = newSlotID;
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
  public void setStorageTypeCid(String string) {
    _storageTypeCid = string;
    if (!ApiFunctions.isEmpty(string)) {
      setStorageTypeDesc(GbossFactory.getInstance().getDescription(_storageTypeCid));
    }
  }

  /**
   * @param string
   */
  public void setUnitName(String string) {
    _unitName = string;
  }

  /**
   * @return
   */
  public String getStorageTypeDesc() {
    return _storageTypeDesc;
  }

  /**
   * @param string
   */
  public void setStorageTypeDesc(String string) {
    _storageTypeDesc = string;
  }
}
