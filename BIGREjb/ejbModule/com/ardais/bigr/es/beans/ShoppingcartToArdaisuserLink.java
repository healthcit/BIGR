package com.ardais.bigr.es.beans;
import java.util.*;
import java.rmi.*;
import javax.ejb.*;
import javax.naming.*;
import com.ibm.ivj.ejb.associations.interfaces.*;
import com.ibm.ivj.ejb.associations.links.*;
/**
 * ShoppingcartToArdaisuserLink
 * @generated
 */
public class ShoppingcartToArdaisuserLink
	extends com.ibm.ivj.ejb.associations.links.ManyToSingleLink {
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
  public ShoppingcartToArdaisuserLink(javax.ejb.EntityBean anEntityBean) {
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
   * Fetch the target for this single link, return an instance of the target class.
   * @generated
   */
  protected javax.ejb.EJBObject fetchTarget()
    throws javax.ejb.FinderException, java.rmi.RemoteException {
    EJBObject target = null;
    com.ardais.bigr.es.beans.ArdaisuserKey key =
      ((com.ardais.bigr.es.beans.ShoppingcartBean) source).getArdaisuserKey();
    try {
      target =
        ((com.ardais.bigr.es.beans.ArdaisuserHome) getTargetHome(this)).findByPrimaryKey(key);
    }
    catch (NamingException e) {
      throw new FinderException(e.toString());
    }
    return target;
  }
  /**
   * Get the entity context from the source bean.
   * @generated
   */
  public javax.ejb.EntityContext getEntityContext() {
    return ((com.ardais.bigr.es.beans.ShoppingcartBean) source).getEntityContext();
  }
  /**
   * Return whether or not the source key is valid for querying.
   * @generated
   */
  protected boolean isKeyValid() {
    return (((com.ardais.bigr.es.beans.ShoppingcartBean) source).getArdaisuserKey() != null);
  }
  /**
   * Forward inverse maintenance through my target EJB.
   * @generated
   */
  public void secondaryRemoveElementCounterLinkOf(javax.ejb.EJBObject anEJB)
    throws java.rmi.RemoteException {
    if (anEJB != null)
      ((com.ardais.bigr.es.beans.Ardaisuser) anEJB).secondaryRemoveShoppingcart(
        (com.ardais.bigr.es.beans.Shoppingcart) getEntityContext().getEJBObject());
  }
  /**
   * Forward inverse maintenance through my target EJB.
   * @generated
   */
  public void secondaryAddElementCounterLinkOf(javax.ejb.EJBObject anEJB)
    throws java.rmi.RemoteException {
    if (anEJB != null)
      ((com.ardais.bigr.es.beans.Ardaisuser) anEJB).secondaryAddShoppingcart(
        (com.ardais.bigr.es.beans.Shoppingcart) getEntityContext().getEJBObject());
  }
  /**
   * Set the target for this single link, an instance of the target class.
   * @generated
   */
  public void set(javax.ejb.EJBObject targetEJB) throws java.rmi.RemoteException {
    super.set(targetEJB);
    if (targetEJB == null)
       ((com.ardais.bigr.es.beans.ShoppingcartBean) source).privateSetArdaisuserKey(null);
    else
      ((com.ardais.bigr.es.beans.ShoppingcartBean) source).privateSetArdaisuserKey(
        (com.ardais.bigr.es.beans.ArdaisuserKey) targetEJB.getPrimaryKey());
  }
  /**
   * Reset my target key.
   * @generated
   */
  protected void resetKey() throws java.rmi.RemoteException {
    ((com.ardais.bigr.es.beans.ShoppingcartBean) source).privateSetArdaisuserKey(null);
  }
}
