package com.ardais.bigr.iltds.beans;

import javax.ejb.EJBException;
import javax.ejb.EntityBean;
/**
 * This is an Entity Bean class with CMP fields
 */
public class ManifestBean implements EntityBean {
  public static final String DEFAULT_manifest_number = null;
  public static final java.lang.String DEFAULT_geolocation_location_address_id = null;
  public static final java.lang.String DEFAULT_geolocation1_location_address_id = null;
  public static final String DEFAULT_airbill_tracking_number = null;
  public static final java.sql.Timestamp DEFAULT_ship_datetime = null;
  public static final String DEFAULT_ship_staff_id = null;
  public static final String DEFAULT_shipment_status = null;
  public static final String DEFAULT_mnft_create_staff_id = null;
  public static final java.sql.Timestamp DEFAULT_mnft_create_datetime = null;
  public static final String DEFAULT_ship_verify_staff_id = null;
  public static final java.sql.Timestamp DEFAULT_ship_verify_datetime = null;
  public static final String DEFAULT_receipt_by_staff_id = null;
  public static final java.sql.Timestamp DEFAULT_receipt_datetime = null;
  public static final String DEFAULT_receipt_verify_staff_id = null;
  public static final java.sql.Timestamp DEFAULT_receipt_verify_datetime = null;

  public String manifest_number;
  public String airbill_tracking_number;
  public java.sql.Timestamp ship_datetime;
  public String ship_staff_id;
  public String shipment_status;
  public String mnft_create_staff_id;
  public java.sql.Timestamp mnft_create_datetime;
  public String ship_verify_staff_id;
  public java.sql.Timestamp ship_verify_datetime;
  public String receipt_by_staff_id;
  public java.sql.Timestamp receipt_datetime;
  public String receipt_verify_staff_id;
  public java.sql.Timestamp receipt_verify_datetime;

  private javax.ejb.EntityContext entityContext = null;
  final static long serialVersionUID = 3206093459760846163L;
  private transient com.ibm.ivj.ejb.associations.interfaces.ManyLink sampleboxLink = null;

  private static final com.ardais.bigr.api.CMPDefaultValues _fieldDefaultValues =
    new com.ardais.bigr.api.CMPDefaultValues(com.ardais.bigr.iltds.beans.ManifestBean.class);
  /**
   * This method was generated for supporting the associations.
   */
  protected java.util.Vector _getLinks() {
    java.util.Vector links = new java.util.Vector();
    links.add(getSampleboxLink());
    return links;
  }
  /**
   * This method was generated for supporting the associations.
   */
  protected void _initLinks() {
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
   * This method was generated for supporting the association named Samplebox REFILTDS_MANIFEST15 Manifest.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param aSamplebox com.ardais.bigr.iltds.beans.Samplebox
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void addSamplebox(com.ardais.bigr.iltds.beans.Samplebox aSamplebox)
    throws java.rmi.RemoteException {
    this.getSampleboxLink().addElement(aSamplebox);
  }
  /**
   * ejbActivate method comment
   */
  public void ejbActivate() throws java.rmi.RemoteException {
    _initLinks();
  }
  /**
   * ejbCreate method for a CMP entity bean.
   */
  public com.ardais.bigr.iltds.beans.ManifestKey ejbCreate(java.lang.String manifest_number)
    throws javax.ejb.CreateException, EJBException {
    _fieldDefaultValues.assignTo(this);
    _initLinks();
    this.manifest_number = manifest_number;
    return null;
  }
  /**
   * ejbLoad method comment
   */
  public void ejbLoad() throws java.rmi.RemoteException {
    _initLinks();
  }
  /**
   * ejbPassivate method comment
   */
  public void ejbPassivate() throws java.rmi.RemoteException {
  }
  /**
   * ejbPostCreate
   */
  public void ejbPostCreate(java.lang.String manifest_number)
    throws javax.ejb.CreateException, EJBException {
  }
  /**
   * ejbRemove method comment
   */
  public void ejbRemove() throws java.rmi.RemoteException, javax.ejb.RemoveException {
    _removeLinks();
  }
  /**
   * ejbStore method comment
   */
  public void ejbStore() throws java.rmi.RemoteException {
  }
  /**
   * Getter method for airbill_tracking_number
   * @return java.lang.String
   */
  public java.lang.String getAirbill_tracking_number() {
    return airbill_tracking_number;
  }
  /**
   * getEntityContext method comment
   * @return javax.ejb.EntityContext
   */
  public javax.ejb.EntityContext getEntityContext() {
    return entityContext;
  }
  /**
   * Getter method for mnft_create_datetime
   * @return java.sql.Timestamp
   */
  public java.sql.Timestamp getMnft_create_datetime() {
    return mnft_create_datetime;
  }
  /**
   * Getter method for mnft_create_staff_id
   * @return java.lang.String
   */
  public java.lang.String getMnft_create_staff_id() {
    return mnft_create_staff_id;
  }
  /**
   * Getter method for receipt_by_staff_id
   * @return java.lang.String
   */
  public java.lang.String getReceipt_by_staff_id() {
    return receipt_by_staff_id;
  }
  /**
   * Getter method for receipt_datetime
   * @return java.sql.Timestamp
   */
  public java.sql.Timestamp getReceipt_datetime() {
    return receipt_datetime;
  }
  /**
   * Getter method for receipt_verify_datetime
   */
  public java.sql.Timestamp getReceipt_verify_datetime() {
    return receipt_verify_datetime;
  }
  /**
   * Getter method for receipt_verify_staff_id
   * @return java.lang.String
   */
  public java.lang.String getReceipt_verify_staff_id() {
    return receipt_verify_staff_id;
  }
  /**
   * This method was generated for supporting the association named Samplebox REFILTDS_MANIFEST15 Manifest.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return java.util.Enumeration
   * @exception javax.ejb.FinderException The exception description.
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public java.util.Enumeration getSamplebox()
    throws java.rmi.RemoteException, javax.ejb.FinderException {
    return this.getSampleboxLink().enumerationValue();
  }
  /**
   * This method was generated for supporting the association named Samplebox REFILTDS_MANIFEST15 Manifest.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return com.ibm.ivj.ejb.associations.interfaces.ManyLink
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  protected com.ibm.ivj.ejb.associations.interfaces.ManyLink getSampleboxLink() {
    if (sampleboxLink == null)
      sampleboxLink = new ManifestToSampleboxLink(this);
    return sampleboxLink;
  }
  /**
   * Getter method for ship_datetime
   * @return java.sql.Timestamp
   */
  public java.sql.Timestamp getShip_datetime() {
    return ship_datetime;
  }
  /**
   * Getter method for ship_staff_id
   * @return java.lang.String
   */
  public java.lang.String getShip_staff_id() {
    return ship_staff_id;
  }
  /**
   * Getter method for ship_verify_datetime
   * @return java.sql.Timestamp
   */
  public java.sql.Timestamp getShip_verify_datetime() {
    return ship_verify_datetime;
  }
  /**
   * Getter method for ship_verify_staff_id
   * @return java.lang.String
   */
  public java.lang.String getShip_verify_staff_id() {
    return ship_verify_staff_id;
  }
  /**
   * Getter method for shipment_status
   * @return java.lang.String
   */
  public java.lang.String getShipment_status() {
    return shipment_status;
  }
  /**
   * This method was generated for supporting the association named Samplebox REFILTDS_MANIFEST15 Manifest.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param aSamplebox com.ardais.bigr.iltds.beans.Samplebox
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void removeSamplebox(com.ardais.bigr.iltds.beans.Samplebox aSamplebox)
    throws java.rmi.RemoteException {
    this.getSampleboxLink().removeElement(aSamplebox);
  }
  /**
   * This method was generated for supporting the association named Samplebox REFILTDS_MANIFEST15 Manifest.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param aSamplebox com.ardais.bigr.iltds.beans.Samplebox
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void secondaryAddSamplebox(com.ardais.bigr.iltds.beans.Samplebox aSamplebox) {
    this.getSampleboxLink().secondaryAddElement(aSamplebox);
  }
  /**
   * This method was generated for supporting the association named Samplebox REFILTDS_MANIFEST15 Manifest.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param aSamplebox com.ardais.bigr.iltds.beans.Samplebox
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void secondaryRemoveSamplebox(com.ardais.bigr.iltds.beans.Samplebox aSamplebox) {
    this.getSampleboxLink().secondaryRemoveElement(aSamplebox);
  }
  /**
   * Setter method for airbill_tracking_number
   * @param newValue java.lang.String
   */
  public void setAirbill_tracking_number(java.lang.String newValue) {
    this.airbill_tracking_number = newValue;
  }
  /**
   * setEntityContext method comment
   * @param ctx javax.ejb.EntityContext
   */
  public void setEntityContext(javax.ejb.EntityContext ctx) throws java.rmi.RemoteException {
    entityContext = ctx;
  }
  /**
   * Setter method for mnft_create_datetime
   * @param newValue java.sql.Timestamp
   */
  public void setMnft_create_datetime(java.sql.Timestamp newValue) {
    this.mnft_create_datetime = newValue;
  }
  /**
   * Setter method for mnft_create_staff_id
   * @param newValue java.lang.String
   */
  public void setMnft_create_staff_id(java.lang.String newValue) {
    this.mnft_create_staff_id = newValue;
  }
  /**
   * Setter method for receipt_by_staff_id
   * @param newValue java.lang.String
   */
  public void setReceipt_by_staff_id(java.lang.String newValue) {
    this.receipt_by_staff_id = newValue;
  }
  /**
   * Setter method for receipt_datetime
   * @param newValue java.sql.Timestamp
   */
  public void setReceipt_datetime(java.sql.Timestamp newValue) {
    this.receipt_datetime = newValue;
  }
  /**
   * Setter method for receipt_verify_datetime
   */
  public void setReceipt_verify_datetime(java.sql.Timestamp newValue) {
    this.receipt_verify_datetime = newValue;
  }
  /**
   * Setter method for receipt_verify_staff_id
   * @param newValue java.lang.String
   */
  public void setReceipt_verify_staff_id(java.lang.String newValue) {
    this.receipt_verify_staff_id = newValue;
  }
  /**
   * Setter method for ship_datetime
   * @param newValue java.sql.Timestamp
   */
  public void setShip_datetime(java.sql.Timestamp newValue) {
    this.ship_datetime = newValue;
  }
  /**
   * Setter method for ship_staff_id
   * @param newValue java.lang.String
   */
  public void setShip_staff_id(java.lang.String newValue) {
    this.ship_staff_id = newValue;
  }
  /**
   * Setter method for ship_verify_datetime
   * @param newValue java.sql.Timestamp
   */
  public void setShip_verify_datetime(java.sql.Timestamp newValue) {
    this.ship_verify_datetime = newValue;
  }
  /**
   * Setter method for ship_verify_staff_id
   * @param newValue java.lang.String
   */
  public void setShip_verify_staff_id(java.lang.String newValue) {
    this.ship_verify_staff_id = newValue;
  }
  /**
   * Setter method for shipment_status
   * @param newValue java.lang.String
   */
  public void setShipment_status(java.lang.String newValue) {
    this.shipment_status = newValue;
  }
  /**
   * unsetEntityContext method comment
   */
  public void unsetEntityContext() throws java.rmi.RemoteException {
    entityContext = null;
  }
  /**
   * _copyFromEJB
   */
  public java.util.Hashtable _copyFromEJB() {
    com.ibm.ivj.ejb.runtime.AccessBeanHashtable h = new com.ibm.ivj.ejb.runtime.AccessBeanHashtable();

    h.put("ship_staff_id", getShip_staff_id());
    h.put("ship_verify_datetime", getShip_verify_datetime());
    h.put("receipt_datetime", getReceipt_datetime());
    h.put("receipt_verify_datetime", getReceipt_verify_datetime());
    h.put("shipment_status", getShipment_status());
    h.put("ship_datetime", getShip_datetime());
    h.put("airbill_tracking_number", getAirbill_tracking_number());
    h.put("mnft_create_staff_id", getMnft_create_staff_id());
    h.put("receipt_verify_staff_id", getReceipt_verify_staff_id());
    h.put("receipt_by_staff_id", getReceipt_by_staff_id());
    h.put("ship_verify_staff_id", getShip_verify_staff_id());
    h.put("mnft_create_datetime", getMnft_create_datetime());
    h.put("__Key", getEntityContext().getPrimaryKey());

    return h;
  }
  /**
   * _copyToEJB
   */
  public void _copyToEJB(java.util.Hashtable h) {
    java.lang.String localShip_staff_id = (java.lang.String) h.get("ship_staff_id");
    java.sql.Timestamp localShip_verify_datetime = (java.sql.Timestamp) h
      .get("ship_verify_datetime");
    java.sql.Timestamp localReceipt_datetime = (java.sql.Timestamp) h.get("receipt_datetime");
    java.sql.Timestamp localReceipt_verify_datetime = (java.sql.Timestamp) h
      .get("receipt_verify_datetime");
    java.lang.String localShipment_status = (java.lang.String) h.get("shipment_status");
    java.sql.Timestamp localShip_datetime = (java.sql.Timestamp) h.get("ship_datetime");
    java.lang.String localAirbill_tracking_number = (java.lang.String) h
      .get("airbill_tracking_number");
    java.lang.String localMnft_create_staff_id = (java.lang.String) h.get("mnft_create_staff_id");
    java.lang.String localReceipt_verify_staff_id = (java.lang.String) h
      .get("receipt_verify_staff_id");
    java.lang.String localReceipt_by_staff_id = (java.lang.String) h.get("receipt_by_staff_id");
    java.lang.String localShip_verify_staff_id = (java.lang.String) h.get("ship_verify_staff_id");
    java.sql.Timestamp localMnft_create_datetime = (java.sql.Timestamp) h
      .get("mnft_create_datetime");

    if (h.containsKey("ship_staff_id"))
      setShip_staff_id((localShip_staff_id));
    if (h.containsKey("ship_verify_datetime"))
      setShip_verify_datetime((localShip_verify_datetime));
    if (h.containsKey("receipt_datetime"))
      setReceipt_datetime((localReceipt_datetime));
    if (h.containsKey("receipt_verify_datetime"))
      setReceipt_verify_datetime((localReceipt_verify_datetime));
    if (h.containsKey("shipment_status"))
      setShipment_status((localShipment_status));
    if (h.containsKey("ship_datetime"))
      setShip_datetime((localShip_datetime));
    if (h.containsKey("airbill_tracking_number"))
      setAirbill_tracking_number((localAirbill_tracking_number));
    if (h.containsKey("mnft_create_staff_id"))
      setMnft_create_staff_id((localMnft_create_staff_id));
    if (h.containsKey("receipt_verify_staff_id"))
      setReceipt_verify_staff_id((localReceipt_verify_staff_id));
    if (h.containsKey("receipt_by_staff_id"))
      setReceipt_by_staff_id((localReceipt_by_staff_id));
    if (h.containsKey("ship_verify_staff_id"))
      setShip_verify_staff_id((localShip_verify_staff_id));
    if (h.containsKey("mnft_create_datetime"))
      setMnft_create_datetime((localMnft_create_datetime));
  }
}
