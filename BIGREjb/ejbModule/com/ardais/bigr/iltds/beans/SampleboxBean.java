package com.ardais.bigr.iltds.beans;

import javax.ejb.EJBException;
import javax.ejb.EntityBean;
/**
 * This is an Entity Bean class with CMP fields
 */
public class SampleboxBean implements EntityBean {
  public static final String DEFAULT_box_barcode_id = null;
  public static final java.lang.String DEFAULT_manifest_manifest_number = null;
  public static final java.sql.Timestamp DEFAULT_box_check_in_date = null;
  public static final java.sql.Timestamp DEFAULT_box_check_out_date = null;
  public static final String DEFAULT_box_check_out_reason = null;
  public static final String DEFAULT_box_checkout_request_staff_id = null;
  public static final String DEFAULT_box_status = null;
  public static final String DEFAULT_box_note = null;
  public static final java.math.BigDecimal DEFAULT_manifest_order = null;
  public static final String DEFAULT_boxLayoutId = null;
  public static final String DEFAULT_storageTypeCid = null;

  public String box_barcode_id;
  public java.lang.String manifest_manifest_number;
  public java.sql.Timestamp box_check_in_date;
  public java.sql.Timestamp box_check_out_date;
  public String box_check_out_reason;
  public String box_checkout_request_staff_id;
  public String box_status;
  public String box_note;
  private javax.ejb.EntityContext entityContext = null;
  final static long serialVersionUID = 3206093459760846163L;
  private transient com.ibm.ivj.ejb.associations.interfaces.ManyLink boxlocationLink;
  private transient com.ibm.ivj.ejb.associations.interfaces.SingleLink manifestLink = null;
  private transient com.ibm.ivj.ejb.associations.interfaces.ManyLink sampleLink = null;
  private static final com.ardais.bigr.api.CMPDefaultValues _fieldDefaultValues =
    new com.ardais.bigr.api.CMPDefaultValues(com.ardais.bigr.iltds.beans.SampleboxBean.class);

  /**
   * Implementation field for persistent attribute: manifest_order
   */
  public java.math.BigDecimal manifest_order;
  /**
   * Implementation field for persistent attribute: boxLayoutId
   */
  public java.lang.String boxLayoutId;
  /**
   * Implementation field for persistent attribute: storageTypeCid
   */
  public java.lang.String storageTypeCid;
  /**
   * This method was generated for supporting the associations.
   */
  protected java.util.Vector _getLinks() {
    java.util.Vector links = new java.util.Vector();
    links.add(getSampleLink());
    links.add(getBoxlocationLink());
    links.add(getManifestLink());
    return links;
  }
  /**
   * This method was generated for supporting the associations.
   */
  protected void _initLinks() {
    sampleLink = null;
    boxlocationLink = null;
    manifestLink = null;
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
   * This method was generated for supporting the association named Sample REFILTDS_SAMPLE_BOX10 Samplebox.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param aSample com.ardais.bigr.iltds.beans.Sample
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void addSample(com.ardais.bigr.iltds.beans.Sample aSample)
    throws java.rmi.RemoteException {
    this.getSampleLink().addElement(aSample);
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
  public com.ardais.bigr.iltds.beans.SampleboxKey ejbCreate(java.lang.String box_barcode_id)
    throws javax.ejb.CreateException, EJBException {
    _fieldDefaultValues.assignTo(this);
    _initLinks();
    this.box_barcode_id = box_barcode_id;
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
  public void ejbPostCreate(java.lang.String box_barcode_id)
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
   * Getter method for box_check_in_date
   * @return java.sql.Timestamp
   */
  public java.sql.Timestamp getBox_check_in_date() {
    return box_check_in_date;
  }
  /**
   * Getter method for box_check_out_date
   * @return java.sql.Timestamp
   */
  public java.sql.Timestamp getBox_check_out_date() {
    return box_check_out_date;
  }
  /**
   * Getter method for box_check_out_reason
   * @return java.lang.String
   */
  public java.lang.String getBox_check_out_reason() {
    return box_check_out_reason;
  }
  /**
   * Getter method for box_checkout_request_staff_id
   * @return java.lang.String
   */
  public java.lang.String getBox_checkout_request_staff_id() {
    return box_checkout_request_staff_id;
  }
  /**
   * Getter method for box_note
   * @return java.lang.String
   */
  public java.lang.String getBox_note() {
    return box_note;
  }
  /**
   * Getter method for box_status
   * @return java.lang.String
   */
  public java.lang.String getBox_status() {
    return box_status;
  }
  /**
   * This method was generated for supporting the relationship role named boxlocation.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  protected com.ibm.ivj.ejb.associations.interfaces.ManyLink getBoxlocationLink() {
    if (boxlocationLink == null)
      boxlocationLink = new SampleboxToBoxlocationLink(this);
    return boxlocationLink;
  }
  /**
   * getEntityContext method comment
   * @return javax.ejb.EntityContext
   */
  public javax.ejb.EntityContext getEntityContext() {
    return entityContext;
  }
  /**
   * This method was generated for supporting the association named Samplebox REFILTDS_MANIFEST15 Manifest.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return com.ardais.bigr.iltds.beans.Manifest
   * @exception javax.ejb.FinderException The exception description.
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public com.ardais.bigr.iltds.beans.Manifest getManifest()
    throws java.rmi.RemoteException, javax.ejb.FinderException {
    return (com.ardais.bigr.iltds.beans.Manifest) this.getManifestLink().value();
  }
  /**
   * This method was generated for supporting the association named Samplebox REFILTDS_MANIFEST15 Manifest.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return com.ardais.bigr.iltds.beans.ManifestKey
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public com.ardais.bigr.iltds.beans.ManifestKey getManifestKey() {
    com.ardais.bigr.iltds.beans.ManifestKey temp = null;
    temp = new com.ardais.bigr.iltds.beans.ManifestKey();
    boolean manifest_NULLTEST = true;
    manifest_NULLTEST &= (manifest_manifest_number == null);
    temp.manifest_number = manifest_manifest_number;
    if (manifest_NULLTEST)
      temp = null;
    return temp;
  }
  /**
   * This method was generated for supporting the association named Samplebox REFILTDS_MANIFEST15 Manifest.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return com.ibm.ivj.ejb.associations.interfaces.SingleLink
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  protected com.ibm.ivj.ejb.associations.interfaces.SingleLink getManifestLink() {
    if (manifestLink == null)
      manifestLink = new SampleboxToManifestLink(this);
    return manifestLink;
  }
  /**
   * This method was generated for supporting the association named Sample REFILTDS_SAMPLE_BOX10 Samplebox.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return java.util.Enumeration
   * @exception javax.ejb.FinderException The exception description.
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public java.util.Enumeration getSample()
    throws java.rmi.RemoteException, javax.ejb.FinderException {
    return this.getSampleLink().enumerationValue();
  }
  /**
   * This method was generated for supporting the association named Sample REFILTDS_SAMPLE_BOX10 Samplebox.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return com.ibm.ivj.ejb.associations.interfaces.ManyLink
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  protected com.ibm.ivj.ejb.associations.interfaces.ManyLink getSampleLink() {
    if (sampleLink == null)
      sampleLink = new SampleboxToSampleLink(this);
    return sampleLink;
  }
  /**
   * This method was generated for supporting the association named Samplebox REFILTDS_MANIFEST15 Manifest.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param inKey com.ardais.bigr.iltds.beans.ManifestKey
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void privateSetManifestKey(com.ardais.bigr.iltds.beans.ManifestKey inKey) {
    boolean manifest_NULLTEST = (inKey == null);
    if (manifest_NULLTEST)
      manifest_manifest_number = null;
    else
      manifest_manifest_number = inKey.manifest_number;
  }
  /**
   * This method was generated for supporting the association named Sample REFILTDS_SAMPLE_BOX10 Samplebox.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param aSample com.ardais.bigr.iltds.beans.Sample
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void removeSample(com.ardais.bigr.iltds.beans.Sample aSample)
    throws java.rmi.RemoteException {
    this.getSampleLink().removeElement(aSample);
  }
  /**
   * This method was generated for supporting the association named Sample REFILTDS_SAMPLE_BOX10 Samplebox.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param aSample com.ardais.bigr.iltds.beans.Sample
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void secondaryAddSample(com.ardais.bigr.iltds.beans.Sample aSample) {
    this.getSampleLink().secondaryAddElement(aSample);
  }
  /**
   * This method was generated for supporting the association named Sample REFILTDS_SAMPLE_BOX10 Samplebox.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param aSample com.ardais.bigr.iltds.beans.Sample
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void secondaryRemoveSample(com.ardais.bigr.iltds.beans.Sample aSample) {
    this.getSampleLink().secondaryRemoveElement(aSample);
  }
  /**
   * This method was generated for supporting the association named Samplebox REFILTDS_MANIFEST15 Manifest.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param aManifest com.ardais.bigr.iltds.beans.Manifest
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void secondarySetManifest(com.ardais.bigr.iltds.beans.Manifest aManifest)
    throws java.rmi.RemoteException {
    this.getManifestLink().secondarySet(aManifest);
  }
  /**
   * Setter method for box_check_in_date
   * @param newValue java.sql.Timestamp
   */
  public void setBox_check_in_date(java.sql.Timestamp newValue) {
    this.box_check_in_date = newValue;
  }
  /**
   * Setter method for box_check_out_date
   * @param newValue java.sql.Timestamp
   */
  public void setBox_check_out_date(java.sql.Timestamp newValue) {
    this.box_check_out_date = newValue;
  }
  /**
   * Setter method for box_check_out_reason
   * @param newValue java.lang.String
   */
  public void setBox_check_out_reason(java.lang.String newValue) {
    this.box_check_out_reason = newValue;
  }
  /**
   * Setter method for box_checkout_request_staff_id
   * @param newValue java.lang.String
   */
  public void setBox_checkout_request_staff_id(java.lang.String newValue) {
    this.box_checkout_request_staff_id = newValue;
  }
  /**
   * Setter method for box_note
   * @param newValue java.lang.String
   */
  public void setBox_note(java.lang.String newValue) {
    this.box_note = newValue;
  }
  /**
   * Setter method for box_status
   * @param newValue java.lang.String
   */
  public void setBox_status(java.lang.String newValue) {
    this.box_status = newValue;
  }
  /**
   * setEntityContext method comment
   * @param ctx javax.ejb.EntityContext
   */
  public void setEntityContext(javax.ejb.EntityContext ctx) throws java.rmi.RemoteException {
    entityContext = ctx;
  }
  /**
   * This method was generated for supporting the association named Samplebox REFILTDS_MANIFEST15 Manifest.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param aManifest com.ardais.bigr.iltds.beans.Manifest
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void setManifest(com.ardais.bigr.iltds.beans.Manifest aManifest)
    throws java.rmi.RemoteException {
    this.getManifestLink().set(aManifest);
  }
  /**
   * unsetEntityContext method comment
   */
  public void unsetEntityContext() throws java.rmi.RemoteException {
    entityContext = null;
  }
  /**
   * This method was generated for supporting the relationship role named boxlocation.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void secondaryAddBoxlocation(com.ardais.bigr.iltds.beans.Boxlocation aBoxlocation)
    throws java.rmi.RemoteException {
    this.getBoxlocationLink().secondaryAddElement(aBoxlocation);
  }
  /**
   * This method was generated for supporting the relationship role named boxlocation.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void secondaryRemoveBoxlocation(com.ardais.bigr.iltds.beans.Boxlocation aBoxlocation)
    throws java.rmi.RemoteException {
    this.getBoxlocationLink().secondaryRemoveElement(aBoxlocation);
  }
  /**
   * This method was generated for supporting the relationship role named boxlocation.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public java.util.Enumeration getBoxlocation()
    throws java.rmi.RemoteException, javax.ejb.FinderException {
    return this.getBoxlocationLink().enumerationValue();
  }
  /**
   * This method was generated for supporting the relationship role named boxlocation.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void addBoxlocation(com.ardais.bigr.iltds.beans.Boxlocation aBoxlocation)
    throws java.rmi.RemoteException {
    this.getBoxlocationLink().addElement(aBoxlocation);
  }
  /**
   * This method was generated for supporting the relationship role named boxlocation.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void removeBoxlocation(com.ardais.bigr.iltds.beans.Boxlocation aBoxlocation)
    throws java.rmi.RemoteException {
    this.getBoxlocationLink().removeElement(aBoxlocation);
  }
  /**
   * Get accessor for persistent attribute: manifest_order
   */
  public java.math.BigDecimal getManifest_order() {
    return manifest_order;
  }
  /**
   * Set accessor for persistent attribute: manifest_order
   */
  public void setManifest_order(java.math.BigDecimal newManifest_order) {
    manifest_order = newManifest_order;
  }
  /**
   * Get accessor for persistent attribute: boxLayoutId
   */
  public java.lang.String getBoxLayoutId() {
    return boxLayoutId;
  }
  /**
   * Set accessor for persistent attribute: boxLayoutId
   */
  public void setBoxLayoutId(java.lang.String newBoxLayoutId) {
    boxLayoutId = newBoxLayoutId;
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

    h.put("manifestKey", getManifestKey());
    h.put("boxLayoutId", getBoxLayoutId());
    h.put("storageTypeCid", getStorageTypeCid());
    h.put("box_check_out_reason", getBox_check_out_reason());
    h.put("box_status", getBox_status());
    h.put("box_checkout_request_staff_id", getBox_checkout_request_staff_id());
    h.put("manifest_order", getManifest_order());
    h.put("box_check_in_date", getBox_check_in_date());
    h.put("box_note", getBox_note());
    h.put("box_check_out_date", getBox_check_out_date());
    h.put("__Key", getEntityContext().getPrimaryKey());

    return h;
  }
  /**
   * _copyToEJB
   */
  public void _copyToEJB(java.util.Hashtable h) {
    java.lang.String localBoxLayoutId = (java.lang.String) h.get("boxLayoutId");
    java.lang.String localStorageTypeCid = (java.lang.String) h.get("storageTypeCid");
    java.lang.String localBox_check_out_reason = (java.lang.String) h.get("box_check_out_reason");
    java.lang.String localBox_status = (java.lang.String) h.get("box_status");
    java.lang.String localBox_checkout_request_staff_id = (java.lang.String) h
      .get("box_checkout_request_staff_id");
    java.math.BigDecimal localManifest_order = (java.math.BigDecimal) h.get("manifest_order");
    java.sql.Timestamp localBox_check_in_date = (java.sql.Timestamp) h.get("box_check_in_date");
    java.lang.String localBox_note = (java.lang.String) h.get("box_note");
    java.sql.Timestamp localBox_check_out_date = (java.sql.Timestamp) h.get("box_check_out_date");

    if (h.containsKey("boxLayoutId"))
      setBoxLayoutId((localBoxLayoutId));
    if (h.containsKey("storageTypeCid"))
      setStorageTypeCid((localStorageTypeCid));
    if (h.containsKey("box_check_out_reason"))
      setBox_check_out_reason((localBox_check_out_reason));
    if (h.containsKey("box_status"))
      setBox_status((localBox_status));
    if (h.containsKey("box_checkout_request_staff_id"))
      setBox_checkout_request_staff_id((localBox_checkout_request_staff_id));
    if (h.containsKey("manifest_order"))
      setManifest_order((localManifest_order));
    if (h.containsKey("box_check_in_date"))
      setBox_check_in_date((localBox_check_in_date));
    if (h.containsKey("box_note"))
      setBox_note((localBox_note));
    if (h.containsKey("box_check_out_date"))
      setBox_check_out_date((localBox_check_out_date));
  }
}
