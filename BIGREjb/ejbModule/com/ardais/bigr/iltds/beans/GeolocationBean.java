package com.ardais.bigr.iltds.beans;

import javax.ejb.EJBException;
import javax.ejb.EntityBean;

import com.ardais.bigr.iltds.assistants.LocationManagementAsst;
/**
 * This is an Entity Bean class with CMP fields
 */
public class GeolocationBean implements EntityBean {
  public static final String DEFAULT_location_address_id = null;
  public static final String DEFAULT_location_name = null;
  public static final String DEFAULT_location_address_1 = null;
  public static final String DEFAULT_location_address_2 = null;
  public static final String DEFAULT_location_city = null;
  public static final String DEFAULT_location_state = null;
  public static final String DEFAULT_location_zip = null;
  public static final String DEFAULT_location_phone = null;
  public static final String DEFAULT_location_country = null;

  public String location_address_id;
  public String location_name;
  public String location_address_1;
  public String location_address_2;
  public String location_city;
  public String location_state;
  public String location_zip;
  public String location_phone;
  public String location_country;

  private javax.ejb.EntityContext entityContext = null;
  final static long serialVersionUID = 3206093459760846163L;
  private transient com.ibm.ivj.ejb.associations.interfaces.ManyLink ardaisstaffLink;
  private transient com.ibm.ivj.ejb.associations.interfaces.ManyLink boxlocationLink;
  private transient com.ibm.ivj.ejb.associations.interfaces.ManyLink consentLink = null;
  private static final com.ardais.bigr.api.CMPDefaultValues _fieldDefaultValues =
    new com.ardais.bigr.api.CMPDefaultValues(com.ardais.bigr.iltds.beans.GeolocationBean.class);
  /**
   * This method was generated for supporting the associations.
   */
  protected java.util.Vector _getLinks() {
    java.util.Vector links = new java.util.Vector();
    links.add(getConsentLink());
    links.add(getArdaisstaffLink());
    links.add(getBoxlocationLink());
    return links;
  }
  /**
   * This method was generated for supporting the associations.
   */
  protected void _initLinks() {
    consentLink = null;
    ardaisstaffLink = null;
    boxlocationLink = null;
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
   * This method was generated for supporting the relationship role named ardaisstaff.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void addArdaisstaff(com.ardais.bigr.iltds.beans.Ardaisstaff anArdaisstaff)
    throws java.rmi.RemoteException {
    this.getArdaisstaffLink().addElement(anArdaisstaff);
  }
  /**
   * This method was generated for supporting the association named Consent REFILTDS_GEOGRAPHY_LOCATION19 Geolocation.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param aConsent com.ardais.bigr.iltds.beans.Consent
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void addConsent(com.ardais.bigr.iltds.beans.Consent aConsent)
    throws java.rmi.RemoteException {
    this.getConsentLink().addElement(aConsent);
  }
  /**
   * ejbActivate method comment
   */
  public void ejbActivate() throws java.rmi.RemoteException {
    _initLinks();
  }
  /**
   * ejbCreate method for a CMP entity bean
   * @param argLocation_address_id java.lang.String
   * @exception javax.ejb.CreateException The exception description.
   */
  public GeolocationKey ejbCreate(LocationManagementAsst locAsst)
    throws javax.ejb.CreateException, EJBException {
    _fieldDefaultValues.assignTo(this);
    _initLinks();
    // All CMP fields should be initialized here.
    location_address_id = locAsst.getAddressId();
    this.location_address_1 = locAsst.getLocAdd1();
    this.location_address_2 = locAsst.getLocAdd2();
    this.location_city = locAsst.getLocCity();
    this.location_name = locAsst.getLocName();
    this.location_phone = locAsst.getLocPhone();
    this.location_state = locAsst.getLocState();
    this.location_zip = locAsst.getLocZip();
    this.location_country = locAsst.getLocCountry();
    return null;
  }
  /**
   * ejbCreate method for a CMP entity bean.
   */
  public com.ardais.bigr.iltds.beans.GeolocationKey ejbCreate(java.lang.String location_address_id)
    throws javax.ejb.CreateException, EJBException {
    _fieldDefaultValues.assignTo(this);
    _initLinks();
    this.location_address_id = location_address_id;
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
   * ejbPostCreate method for a CMP entity bean
   * @param argLocation_address_id java.lang.String
   */
  public void ejbPostCreate(LocationManagementAsst locAsst) throws EJBException {
  }
  /**
   * ejbPostCreate
   */
  public void ejbPostCreate(java.lang.String location_address_id)
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
   * This method was generated for supporting the relationship role named ardaisstaff.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public java.util.Enumeration getArdaisstaff()
    throws java.rmi.RemoteException, javax.ejb.FinderException {
    return this.getArdaisstaffLink().enumerationValue();
  }
  /**
   * This method was generated for supporting the relationship role named ardaisstaff.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  protected com.ibm.ivj.ejb.associations.interfaces.ManyLink getArdaisstaffLink() {
    if (ardaisstaffLink == null)
      ardaisstaffLink = new GeolocationToArdaisstaffLink(this);
    return ardaisstaffLink;
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
  protected com.ibm.ivj.ejb.associations.interfaces.ManyLink getBoxlocationLink() {
    if (boxlocationLink == null)
      boxlocationLink = new GeolocationToBoxlocationLink(this);
    return boxlocationLink;
  }
  /**
   * This method was generated for supporting the association named Consent REFILTDS_GEOGRAPHY_LOCATION19 Geolocation.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return java.util.Enumeration
   * @exception javax.ejb.FinderException The exception description.
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public java.util.Enumeration getConsent()
    throws java.rmi.RemoteException, javax.ejb.FinderException {
    return this.getConsentLink().enumerationValue();
  }
  /**
   * This method was generated for supporting the association named Consent REFILTDS_GEOGRAPHY_LOCATION19 Geolocation.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return com.ibm.ivj.ejb.associations.interfaces.ManyLink
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  protected com.ibm.ivj.ejb.associations.interfaces.ManyLink getConsentLink() {
    if (consentLink == null)
      consentLink = new GeolocationToConsentLink(this);
    return consentLink;
  }
  /**
   * getEntityContext method comment
   * @return javax.ejb.EntityContext
   */
  public javax.ejb.EntityContext getEntityContext() {
    return entityContext;
  }
  /**
   * Getter method for location_address_1
   * @return java.lang.String
   */
  public java.lang.String getLocation_address_1() {
    return location_address_1;
  }
  /**
   * Getter method for location_address_2
   * @return java.lang.String
   */
  public java.lang.String getLocation_address_2() {
    return location_address_2;
  }
  /**
   * Getter method for location_city
   * @return java.lang.String
   */
  public java.lang.String getLocation_city() {
    return location_city;
  }
  /**
   * Getter method for location_name
   */
  public java.lang.String getLocation_name() {
    return location_name;
  }
  /**
   * Getter method for location_phone
   * @return java.lang.String
   */
  public java.lang.String getLocation_phone() {
    return location_phone;
  }
  /**
   * Get accessor for persistent attribute: location_country
   */
  public java.lang.String getLocation_country() {
    return location_country;
  }
  /**
   * Getter method for location_state
   * @return java.lang.String
   */
  public java.lang.String getLocation_state() {
    return location_state;
  }
  /**
   * Getter method for location_zip
   * @return java.lang.String
   */
  public java.lang.String getLocation_zip() {
    return location_zip;
  }
  /**
   * This method was generated for supporting the association named Consent REFILTDS_GEOGRAPHY_LOCATION19 Geolocation.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param aConsent com.ardais.bigr.iltds.beans.Consent
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void removeConsent(com.ardais.bigr.iltds.beans.Consent aConsent)
    throws java.rmi.RemoteException {
    this.getConsentLink().removeElement(aConsent);
  }
  /**
   * This method was generated for supporting the relationship role named ardaisstaff.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void secondaryAddArdaisstaff(com.ardais.bigr.iltds.beans.Ardaisstaff anArdaisstaff)
    throws java.rmi.RemoteException {
    this.getArdaisstaffLink().secondaryAddElement(anArdaisstaff);
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
   * This method was generated for supporting the association named Consent REFILTDS_GEOGRAPHY_LOCATION19 Geolocation.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param aConsent com.ardais.bigr.iltds.beans.Consent
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void secondaryAddConsent(com.ardais.bigr.iltds.beans.Consent aConsent) {
    this.getConsentLink().secondaryAddElement(aConsent);
  }
  /**
   * This method was generated for supporting the relationship role named ardaisstaff.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void secondaryRemoveArdaisstaff(com.ardais.bigr.iltds.beans.Ardaisstaff anArdaisstaff)
    throws java.rmi.RemoteException {
    this.getArdaisstaffLink().secondaryRemoveElement(anArdaisstaff);
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
   * This method was generated for supporting the association named Consent REFILTDS_GEOGRAPHY_LOCATION19 Geolocation.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param aConsent com.ardais.bigr.iltds.beans.Consent
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void secondaryRemoveConsent(com.ardais.bigr.iltds.beans.Consent aConsent) {
    this.getConsentLink().secondaryRemoveElement(aConsent);
  }
  /**
   * setEntityContext method comment
   * @param ctx javax.ejb.EntityContext
   */
  public void setEntityContext(javax.ejb.EntityContext ctx) throws java.rmi.RemoteException {
    entityContext = ctx;
  }
  /**
   * Setter method for location_address_1
   * @param newValue java.lang.String
   */
  public void setLocation_address_1(java.lang.String newValue) {
    this.location_address_1 = newValue;
  }
  /**
   * Setter method for location_address_2
   * @param newValue java.lang.String
   */
  public void setLocation_address_2(java.lang.String newValue) {
    this.location_address_2 = newValue;
  }
  /**
   * Setter method for location_city
   * @param newValue java.lang.String
   */
  public void setLocation_city(java.lang.String newValue) {
    this.location_city = newValue;
  }
  /**
   * Setter method for location_name
   */
  public void setLocation_name(java.lang.String newValue) {
    this.location_name = newValue;
  }
  /**
   * Setter method for location_phone
   * @param newValue java.lang.String
   */
  public void setLocation_phone(java.lang.String newValue) {
    this.location_phone = newValue;
  }
  /**
   * Set accessor for persistent attribute: location_country
   */
  public void setLocation_country(java.lang.String newLocation_country) {
    location_country = newLocation_country;
  }
  /**
   * Setter method for location_state
   * @param newValue java.lang.String
   */
  public void setLocation_state(java.lang.String newValue) {
    this.location_state = newValue;
  }
  /**
   * Setter method for location_zip
   * @param newValue java.lang.String
   */
  public void setLocation_zip(java.lang.String newValue) {
    this.location_zip = newValue;
  }
  /**
   * unsetEntityContext method comment
   */
  public void unsetEntityContext() throws java.rmi.RemoteException {
    entityContext = null;
  }
  /**
   * Insert the method's description here.
   * Creation date: (10/31/01 11:25:00 AM)
   * @param locAsst com.ardais.bigr.iltds.assistants.LocationManagementAsst
   * @exception com.ardais.bigr.api.ApiException The exception description.
   */
  public void updateGeoLoc(com.ardais.bigr.iltds.assistants.LocationManagementAsst locAsst)
    throws com.ardais.bigr.api.ApiException {
    this.location_name = locAsst.getLocName();
    this.location_address_1 = locAsst.getLocAdd1();
    this.location_address_2 = locAsst.getLocAdd2();
    this.location_city = locAsst.getLocCity();
    this.location_state = locAsst.getLocState();
    this.location_phone = locAsst.getLocPhone();
    this.location_zip = locAsst.getLocZip();
    this.location_country = locAsst.getLocCountry();
  }
  /**
   * _copyFromEJB
   */
  public java.util.Hashtable _copyFromEJB() {
    com.ibm.ivj.ejb.runtime.AccessBeanHashtable h = new com.ibm.ivj.ejb.runtime.AccessBeanHashtable();

    h.put("location_zip", getLocation_zip());
    h.put("location_name", getLocation_name());
    h.put("location_state", getLocation_state());
    h.put("location_city", getLocation_city());
    h.put("location_address_2", getLocation_address_2());
    h.put("location_address_1", getLocation_address_1());
    h.put("location_phone", getLocation_phone());
    h.put("location_country", getLocation_country());
    h.put("__Key", getEntityContext().getPrimaryKey());

    return h;
  }
  /**
   * _copyToEJB
   */
  public void _copyToEJB(java.util.Hashtable h) {
    java.lang.String localLocation_zip = (java.lang.String) h.get("location_zip");
    java.lang.String localLocation_name = (java.lang.String) h.get("location_name");
    java.lang.String localLocation_state = (java.lang.String) h.get("location_state");
    java.lang.String localLocation_city = (java.lang.String) h.get("location_city");
    java.lang.String localLocation_address_2 = (java.lang.String) h.get("location_address_2");
    java.lang.String localLocation_address_1 = (java.lang.String) h.get("location_address_1");
    java.lang.String localLocation_phone = (java.lang.String) h.get("location_phone");
    java.lang.String localLocation_country = (java.lang.String) h.get("location_country");

    if (h.containsKey("location_zip"))
      setLocation_zip((localLocation_zip));
    if (h.containsKey("location_name"))
      setLocation_name((localLocation_name));
    if (h.containsKey("location_state"))
      setLocation_state((localLocation_state));
    if (h.containsKey("location_city"))
      setLocation_city((localLocation_city));
    if (h.containsKey("location_address_2"))
      setLocation_address_2((localLocation_address_2));
    if (h.containsKey("location_address_1"))
      setLocation_address_1((localLocation_address_1));
    if (h.containsKey("location_phone"))
      setLocation_phone((localLocation_phone));
    if (h.containsKey("location_country"))
      setLocation_country((localLocation_country));
  }
}
