package com.ardais.bigr.es.beans;
import java.util.*;
import java.rmi.*;
import javax.ejb.*;
import javax.naming.*;
import com.ibm.ivj.ejb.associations.interfaces.*;
import com.ibm.ivj.ejb.associations.links.*;
/**
 * ArdaisaccountToArdaisuserLink
 * @generated
 */
public class ArdaisaccountToArdaisuserLink
	extends com.ibm.ivj.ejb.associations.links.SingleToManyLink {
  /**
   * @generated
   */
  private static com.ardais.bigr.es.beans.ArdaisuserHome targetHome;
  /**
   * @generated
   */
  private static final java.lang.String targetHomeName = "com/ardais/bigr/es/beans/Ardaisuser";
  /**
   * Create a link instance with the passed source bean.
   * @generated
   */
  public ArdaisaccountToArdaisuserLink(javax.ejb.EntityBean anEntityBean) {
    super(anEntityBean);
  }
  /**
   * Get the target home for the link.
   * @generated
   */
  protected synchronized com.ardais.bigr.es.beans.ArdaisuserHome getTargetHome(
    com.ibm.ivj.ejb.associations.links.Link aLink)
    throws javax.naming.NamingException {
    if (targetHome == null)
      targetHome =
        (com.ardais.bigr.es.beans.ArdaisuserHome) lookupTargetHome("java:comp/env/ejb/Ardaisuser",
          com.ardais.bigr.es.beans.ArdaisuserHome.class);
    return targetHome;
  }
  /**
   * Fetch the target for this many link, return an enumeration of the members.
   * @generated
   */
  protected java.util.Enumeration fetchTargetEnumeration()
    throws javax.ejb.FinderException, java.rmi.RemoteException {
    Enumeration enum1 = null;
    try {
      enum1 =
        (
          (com.ardais.bigr.es.beans.ArdaisuserHome) getTargetHome(
            this)).findArdaisuserByArdaisaccount(
          (com.ardais.bigr.es.beans.ArdaisaccountKey) getEntityContext().getPrimaryKey());
    }
    catch (NamingException e) {
      throw new FinderException(e.toString());
    }
    return enum1;
  }
  /**
   * Get the entity context from the source bean.
   * @generated
   */
  public javax.ejb.EntityContext getEntityContext() {
    return ((com.ardais.bigr.es.beans.ArdaisaccountBean) source).getEntityContext();
  }
  /**
   * Return whether or not the source key is valid for querying.
   * @generated
   */
  protected boolean isKeyValid() {
    return (getEntityContext().getPrimaryKey() != null);
  }
  /**
   * Direct my counterLink to set my source as its target.
   * @generated
   */
  public void secondarySetCounterLinkOf(javax.ejb.EJBObject anEJB)
    throws java.rmi.RemoteException {
  }
  /**
   * Send my counterLink #secondaryDisconnect by routing through my target EJB.
   * @generated
   */
  public void setNullCounterLinkOf(javax.ejb.EJBObject anEJB) throws java.rmi.RemoteException {
  }
  /**
   * Send my counterLink #secondaryDisconnect by routing through my target EJB.
   * @generated
   */
  public void secondarySetNullCounterLinkOf(javax.ejb.EJBObject anEJB)
    throws java.rmi.RemoteException {
  }
  /**
   * Narrow the remote object.
   * @generated
   */
  protected javax.ejb.EJBObject narrowElement(java.lang.Object element)
    throws java.rmi.RemoteException {
    return (EJBObject) javax.rmi.PortableRemoteObject.narrow(
      element,
      com.ardais.bigr.es.beans.Ardaisuser.class);
  }
  /**
   * Check if I contain anEJB by comparing primary keys.
   * @generated
   */
  protected boolean currentlyContains(javax.ejb.EJBObject anEJB) throws java.rmi.RemoteException {
    return super.currentlyContains(anEJB)
      || getEntityContext().getPrimaryKey().equals(
        ((com.ardais.bigr.es.beans.Ardaisuser) anEJB).getArdaisaccountKey());
  }
}
