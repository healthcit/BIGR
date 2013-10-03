package com.ardais.bigr.iltds.beans;

import javax.ejb.EJBException;
import javax.ejb.EntityBean;
/**
 * This is an Entity Bean class with CMP fields
 */
public class ArdaisstaffBean implements EntityBean {
  public static final String DEFAULT_ardais_staff_id = null;
  public static final String DEFAULT_geolocation_location_address_id = null;
  public static final String DEFAULT_ardais_staff_lname = null;
  public static final String DEFAULT_ardais_staff_fname = null;
  public static final String DEFAULT_ardais_acct_key = null;
  public static final String DEFAULT_ardais_user_id = null;

  public String ardais_staff_id;
  /**
   * Implementation field for persistent attribute: geolocation_location_address_id
   */
  public java.lang.String geolocation_location_address_id;
  public String ardais_staff_lname;
  public String ardais_staff_fname;
  public String ardais_acct_key;
  public String ardais_user_id;

  private javax.ejb.EntityContext entityContext = null;
  final static long serialVersionUID = 3206093459760846163L;
  private transient com.ibm.ivj.ejb.associations.interfaces.SingleLink geolocationLink;
  private static final com.ardais.bigr.api.CMPDefaultValues _fieldDefaultValues =
    new com.ardais.bigr.api.CMPDefaultValues(com.ardais.bigr.iltds.beans.ArdaisstaffBean.class);
  /**
   * This method was generated for supporting the associations.
   */
  protected java.util.Vector _getLinks() {
    java.util.Vector links = new java.util.Vector();
    links.add(getGeolocationLink());
    return links;
  }
  /**
   * This method was generated for supporting the associations.
   */
  protected void _initLinks() {
    geolocationLink = null;
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
   * ejbActivate method comment
   */
  public void ejbActivate() throws java.rmi.RemoteException {
    _initLinks();
  }
  /**
   * ejbCreate method for a CMP entity bean.
   */
  public com.ardais.bigr.iltds.beans.ArdaisstaffKey ejbCreate(
    java.lang.String ardais_staff_id,
    com.ardais.bigr.iltds.beans.Geolocation argGeolocation)
    throws javax.ejb.CreateException, EJBException {
    _fieldDefaultValues.assignTo(this);
    _initLinks();
    this.ardais_staff_id = ardais_staff_id;
    try {
      setGeolocation(argGeolocation);
    }
    catch (java.rmi.RemoteException remoteEx) {
      throw new javax.ejb.CreateException(remoteEx.getMessage());
    }
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
  public void ejbPostCreate(
    java.lang.String ardais_staff_id,
    com.ardais.bigr.iltds.beans.Geolocation argGeolocation)
    throws javax.ejb.CreateException, EJBException {
    try {
      setGeolocation(argGeolocation);
    }
    catch (java.rmi.RemoteException remoteEx) {
      throw new javax.ejb.CreateException(remoteEx.getMessage());
    }
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
   * Getter method for ardais_acct_key
   */
  public java.lang.String getArdais_acct_key() {
    return ardais_acct_key;
  }
  /**
   * Getter method for ardais_staff_fname
   * @return java.lang.String
   */
  public java.lang.String getArdais_staff_fname() {
    return ardais_staff_fname;
  }
  /**
   * Getter method for ardais_staff_lname
   * @return java.lang.String
   */
  public java.lang.String getArdais_staff_lname() {
    return ardais_staff_lname;
  }
  /**
   * Getter method for ardais_user_id
   */
  public java.lang.String getArdais_user_id() {
    return ardais_user_id;
  }
  /**
   * getEntityContext method comment
   * @return javax.ejb.EntityContext
   */
  public javax.ejb.EntityContext getEntityContext() {
    return entityContext;
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
   * Creation date: (4/5/01 8:03:13 PM)
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
      geolocationLink = new ArdaisstaffToGeolocationLink(this);
    return geolocationLink;
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
   * This method was generated for supporting the relationship role named geolocation.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void secondarySetGeolocation(com.ardais.bigr.iltds.beans.Geolocation aGeolocation)
    throws java.rmi.RemoteException {
    this.getGeolocationLink().secondarySet(aGeolocation);
  }
  /**
   * Setter method for ardais_acct_key
   */
  public void setArdais_acct_key(java.lang.String newValue) {
    this.ardais_acct_key = newValue;
  }
  /**
   * Setter method for ardais_staff_fname
   * @param newValue java.lang.String
   */
  public void setArdais_staff_fname(java.lang.String newValue) {
    this.ardais_staff_fname = newValue;
  }
  /**
   * Setter method for ardais_staff_lname
   * @param newValue java.lang.String
   */
  public void setArdais_staff_lname(java.lang.String newValue) {
    this.ardais_staff_lname = newValue;
  }
  /**
   * Setter method for ardais_user_id
   */
  public void setArdais_user_id(java.lang.String newValue) {
    this.ardais_user_id = newValue;
  }
  /**
   * setEntityContext method comment
   * @param ctx javax.ejb.EntityContext
   */
  public void setEntityContext(javax.ejb.EntityContext ctx) throws java.rmi.RemoteException {
    entityContext = ctx;
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
   * Creation date: (4/5/01 8:03:13 PM)
   * @param newGeolocation_location_address_id java.lang.String
   */
  public void setGeolocation_location_address_id(
    java.lang.String newGeolocation_location_address_id) {
    geolocation_location_address_id = newGeolocation_location_address_id;
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

    h.put("ardais_user_id", getArdais_user_id());
    h.put("ardais_staff_fname", getArdais_staff_fname());
    h.put("geolocationKey", getGeolocationKey());
    h.put("ardais_staff_lname", getArdais_staff_lname());
    h.put("ardais_acct_key", getArdais_acct_key());
    h.put("geolocation_location_address_id", getGeolocation_location_address_id());
    h.put("__Key", getEntityContext().getPrimaryKey());

    return h;
  }
  /**
   * _copyToEJB
   */
  public void _copyToEJB(java.util.Hashtable h) {
    java.lang.String localArdais_user_id = (java.lang.String) h.get("ardais_user_id");
    java.lang.String localArdais_staff_fname = (java.lang.String) h.get("ardais_staff_fname");
    java.lang.String localArdais_staff_lname = (java.lang.String) h.get("ardais_staff_lname");
    java.lang.String localArdais_acct_key = (java.lang.String) h.get("ardais_acct_key");
    java.lang.String localGeolocation_location_address_id = (java.lang.String) h
      .get("geolocation_location_address_id");

    if (h.containsKey("ardais_user_id"))
      setArdais_user_id((localArdais_user_id));
    if (h.containsKey("ardais_staff_fname"))
      setArdais_staff_fname((localArdais_staff_fname));
    if (h.containsKey("ardais_staff_lname"))
      setArdais_staff_lname((localArdais_staff_lname));
    if (h.containsKey("ardais_acct_key"))
      setArdais_acct_key((localArdais_acct_key));
    if (h.containsKey("geolocation_location_address_id"))
      setGeolocation_location_address_id((localGeolocation_location_address_id));
  }
}
