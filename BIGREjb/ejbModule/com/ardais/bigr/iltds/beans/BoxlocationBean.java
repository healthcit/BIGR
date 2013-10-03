package com.ardais.bigr.iltds.beans;

import javax.ejb.EJBException;
import javax.ejb.EntityBean;

import com.ardais.bigr.iltds.assistants.StorageLocAsst;
/**
 * This is an Entity Bean class with CMP fields
 */
public class BoxlocationBean implements EntityBean {
  public static final String DEFAULT_geolocation_location_address_id = null;
  public static final String DEFAULT_room_id = null;
  public static final String DEFAULT_drawer_id = null;
  public static final String DEFAULT_slot_id = null;
  public static final String DEFAULT_samplebox_box_barcode_id = null;
  public static final String DEFAULT_available_ind = null;
  public static final String DEFAULT_location_status = null;
  public static final String DEFAULT_unitName = null;
  public static final String DEFAULT_storageTypeCid = null;

  /**
   * Implementation field for persistent attribute: geolocation_location_address_id
   */
  public java.lang.String geolocation_location_address_id;
  public String room_id;
  public String drawer_id;
  public String slot_id;
  /**
   * Implementation field for persistent attribute: samplebox_box_barcode_id
   */
  public java.lang.String samplebox_box_barcode_id;
  public String available_ind;
  public String location_status;
  private javax.ejb.EntityContext entityContext = null;
  final static long serialVersionUID = 3206093459760846163L;
  private transient com.ibm.ivj.ejb.associations.interfaces.SingleLink geolocationLink;
  private transient com.ibm.ivj.ejb.associations.interfaces.SingleLink sampleboxLink;
  private static final com.ardais.bigr.api.CMPDefaultValues _fieldDefaultValues =
    new com.ardais.bigr.api.CMPDefaultValues(com.ardais.bigr.iltds.beans.BoxlocationBean.class);
  private javax.ejb.EntityContext myEntityCtx;
  /**
   * Implementation field for persistent attribute: unitName
   */
  public java.lang.String unitName;
  /**
   * Implementation field for persistent attribute: storageTypeCid
   */
  public java.lang.String storageTypeCid;
  /**
   * This method was generated for supporting the associations.
   */
  protected java.util.Vector _getLinks() {
    java.util.Vector links = new java.util.Vector();
    links.add(getGeolocationLink());
    links.add(getSampleboxLink());
    return links;
  }
  /**
   * This method was generated for supporting the associations.
   */
  protected void _initLinks() {
    geolocationLink = null;
    sampleboxLink = null;
  }
  /**
   * This method was generated for supporting the associations.
   */
  protected void _removeLinks() throws java.rmi.RemoteException, javax.ejb.RemoveException {
    java.util.List links = _getLinks();
    for (int i = 0; i < links.size(); i++) {
      try {
        ((com.ibm.ivj.ejb.associations.interfaces.Link) links.get(i)).remove();
      }
      catch (javax.ejb.FinderException e) {
      } //Consume Finder error since I am going away
    }
  }
  /**
   * ejbActivate
   */
  public void ejbActivate() throws java.rmi.RemoteException {
    _initLinks();
  }
  
  /**
   * @param asst
   * @return
   * @throws javax.ejb.CreateException
   * @throws EJBException
   */
  public BoxlocationKey ejbCreate(StorageLocAsst asst)
    throws javax.ejb.CreateException, EJBException {
    _fieldDefaultValues.assignTo(this);
    _initLinks();
    // All CMP fields should be initialized here.
    this.geolocation_location_address_id = asst.getLocationAddressId();
    this.drawer_id = asst.getDrawerId();
    this.room_id = asst.getRoomId();
    this.slot_id = asst.getSlotId();
    this.available_ind = asst.getAvailableInd();
    this.location_status = asst.getLocationStatus();
    this.samplebox_box_barcode_id = asst.getBoxBarcodeId();
    this.unitName = asst.getUnitName();
    this.storageTypeCid = asst.getStorageTypeCid();

    return null;
  }
  /**
   * ejbLoad
   */
  public void ejbLoad() throws java.rmi.RemoteException {
    _initLinks();
  }
  /**
   * ejbPassivate
   */
  public void ejbPassivate() throws java.rmi.RemoteException {
  }
  /**
   * @param asst
   * @throws EJBException
   */
  public void ejbPostCreate(StorageLocAsst asst) throws EJBException {
  }
  /**
   * ejbRemove
   */
  public void ejbRemove() throws javax.ejb.RemoveException, java.rmi.RemoteException {
    try {
      _removeLinks();
    }
    catch (java.rmi.RemoteException e) {
      throw new javax.ejb.RemoveException(e.getMessage());
    }
  }
  /**
   * ejbStore
   */
  public void ejbStore() throws java.rmi.RemoteException {
  }
  /**
   * Getter method for available_ind
   * @return java.lang.String
   */
  public java.lang.String getAvailable_ind() {
    return available_ind;
  }
  /**
   * getEntityContext
   */
  public javax.ejb.EntityContext getEntityContext() {
    return myEntityCtx;
  }
  /**
   * This method was generated for supporting the relationship role named geolocation.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public com.ardais.bigr.iltds.beans.Geolocation getGeolocation()
    throws java.rmi.RemoteException, javax.ejb.FinderException {
    return (com.ardais.bigr.iltds.beans.Geolocation) this.getGeolocationLink().value();
  }
  /**
   * Insert the method's description here.
   * Creation date: (10/31/2001 12:40:19 PM)
   * @return java.lang.String
   */
  public java.lang.String getGeolocation_location_address_id() {
    return geolocation_location_address_id;
  }
  /**
   * This method was generated for supporting the relationship role named geolocation.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public com.ardais.bigr.iltds.beans.GeolocationKey getGeolocationKey() {
    com.ardais.bigr.iltds.beans.GeolocationKey temp =
      new com.ardais.bigr.iltds.beans.GeolocationKey();
    boolean geolocation_NULLTEST = true;
    geolocation_NULLTEST &= (geolocation_location_address_id == null);
    temp.location_address_id = geolocation_location_address_id;
    if (geolocation_NULLTEST)
      temp = null;
    return temp;
  }
  /**
   * This method was generated for supporting the relationship role named geolocation.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  protected com.ibm.ivj.ejb.associations.interfaces.SingleLink getGeolocationLink() {
    if (geolocationLink == null)
      geolocationLink = new BoxlocationToGeolocationLink(this);
    return geolocationLink;
  }
  /**
   * Getter method for location_status
   * @return java.lang.String
   */
  public java.lang.String getLocation_status() {
    return location_status;
  }
  /**
   * This method was generated for supporting the relationship role named samplebox.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public com.ardais.bigr.iltds.beans.Samplebox getSamplebox()
    throws java.rmi.RemoteException, javax.ejb.FinderException {
    return (com.ardais.bigr.iltds.beans.Samplebox) this.getSampleboxLink().value();
  }
  /**
   * Insert the method's description here.
   * Creation date: (2/26/01 10:34:31 AM)
   * @return java.lang.String
   */
  public java.lang.String getSamplebox_box_barcode_id() {
    return samplebox_box_barcode_id;
  }
  /**
   * This method was generated for supporting the relationship role named samplebox.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public com.ardais.bigr.iltds.beans.SampleboxKey getSampleboxKey() {
    com.ardais.bigr.iltds.beans.SampleboxKey temp = new com.ardais.bigr.iltds.beans.SampleboxKey();
    boolean samplebox_NULLTEST = true;
    samplebox_NULLTEST &= (samplebox_box_barcode_id == null);
    temp.box_barcode_id = samplebox_box_barcode_id;
    if (samplebox_NULLTEST)
      temp = null;
    return temp;
  }
  /**
   * This method was generated for supporting the relationship role named samplebox.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  protected com.ibm.ivj.ejb.associations.interfaces.SingleLink getSampleboxLink() {
    if (sampleboxLink == null)
      sampleboxLink = new BoxlocationToSampleboxLink(this);
    return sampleboxLink;
  }
  /**
   * This method was generated for supporting the relationship role named geolocation.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void privateSetGeolocationKey(com.ardais.bigr.iltds.beans.GeolocationKey inKey) {
    boolean geolocation_NULLTEST = (inKey == null);
    geolocation_location_address_id = (geolocation_NULLTEST) ? null : inKey.location_address_id;
  }
  /**
   * This method was generated for supporting the relationship role named samplebox.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void privateSetSampleboxKey(com.ardais.bigr.iltds.beans.SampleboxKey inKey) {
    boolean samplebox_NULLTEST = (inKey == null);
    samplebox_box_barcode_id = (samplebox_NULLTEST) ? null : inKey.box_barcode_id;
  }
  /**
   * This method was generated for supporting the relationship role named samplebox.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void secondarySetSamplebox(com.ardais.bigr.iltds.beans.Samplebox aSamplebox)
    throws java.rmi.RemoteException {
    this.getSampleboxLink().secondarySet(aSamplebox);
  }
  /**
   * Setter method for available_ind
   * @param newValue java.lang.String
   */
  public void setAvailable_ind(java.lang.String newValue) {
    this.available_ind = newValue;
  }
  /**
   * setEntityContext
   */
  public void setEntityContext(javax.ejb.EntityContext ctx) throws java.rmi.RemoteException {
    myEntityCtx = ctx;
  }
  /**
   * This method was generated for supporting the relationship role named geolocation.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void setGeolocation(com.ardais.bigr.iltds.beans.Geolocation aGeolocation)
    throws java.rmi.RemoteException {
    this.getGeolocationLink().set(aGeolocation);
  }
  /**
   * Insert the method's description here.
   * Creation date: (10/31/2001 12:40:19 PM)
   * @param newGeolocation_location_address_id java.lang.String
   */
  public void setGeolocation_location_address_id(
    java.lang.String newGeolocation_location_address_id) {
    geolocation_location_address_id = newGeolocation_location_address_id;
  }
  /**
   * Setter method for location_status
   * @param newValue java.lang.String
   */
  public void setLocation_status(java.lang.String newValue) {
    this.location_status = newValue;
  }
  /**
   * This method was generated for supporting the relationship role named samplebox.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void setSamplebox(com.ardais.bigr.iltds.beans.Samplebox aSamplebox)
    throws java.rmi.RemoteException {
    this.getSampleboxLink().set(aSamplebox);
  }
  /**
   * Insert the method's description here.
   * Creation date: (2/26/01 10:34:31 AM)
   * @param newSamplebox_box_barcode_id java.lang.String
   */
  public void setSamplebox_box_barcode_id(java.lang.String newSamplebox_box_barcode_id) {
    samplebox_box_barcode_id = newSamplebox_box_barcode_id;
  }
  /**
   * unsetEntityContext
   */
  public void unsetEntityContext() throws java.rmi.RemoteException {
    myEntityCtx = null;
  }
  /**
   * Get accessor for persistent attribute: storageTypeCid
   */
  public java.lang.String getStorageTypeCid() {
    return storageTypeCid;
  }
  /**
   * Set accessor for persistent attribute: storageTypeCid
   */
  public void setStorageTypeCid(java.lang.String newStorageTypeCid) {
    storageTypeCid = newStorageTypeCid;
  }
  /**
   * _copyFromEJB
   */
  public java.util.Hashtable _copyFromEJB() {
    com.ibm.ivj.ejb.runtime.AccessBeanHashtable h = new com.ibm.ivj.ejb.runtime.AccessBeanHashtable();

    h.put("geolocation_location_address_id", getGeolocation_location_address_id());
    h.put("sampleboxKey", getSampleboxKey());
    h.put("samplebox_box_barcode_id", getSamplebox_box_barcode_id());
    h.put("available_ind", getAvailable_ind());
    h.put("storageTypeCid", getStorageTypeCid());
    h.put("location_status", getLocation_status());
    h.put("geolocationKey", getGeolocationKey());
    h.put("__Key", getEntityContext().getPrimaryKey());

    return h;
  }
  /**
   * _copyToEJB
   */
  public void _copyToEJB(java.util.Hashtable h) {
    java.lang.String localGeolocation_location_address_id = (java.lang.String) h
      .get("geolocation_location_address_id");
    java.lang.String localSamplebox_box_barcode_id = (java.lang.String) h
      .get("samplebox_box_barcode_id");
    java.lang.String localAvailable_ind = (java.lang.String) h.get("available_ind");
    java.lang.String localStorageTypeCid = (java.lang.String) h.get("storageTypeCid");
    java.lang.String localLocation_status = (java.lang.String) h.get("location_status");

    if (h.containsKey("geolocation_location_address_id"))
      setGeolocation_location_address_id((localGeolocation_location_address_id));
    if (h.containsKey("samplebox_box_barcode_id"))
      setSamplebox_box_barcode_id((localSamplebox_box_barcode_id));
    if (h.containsKey("available_ind"))
      setAvailable_ind((localAvailable_ind));
    if (h.containsKey("storageTypeCid"))
      setStorageTypeCid((localStorageTypeCid));
    if (h.containsKey("location_status"))
      setLocation_status((localLocation_status));
  }
}
