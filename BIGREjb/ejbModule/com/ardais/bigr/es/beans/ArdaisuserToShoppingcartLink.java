package com.ardais.bigr.es.beans;
import java.util.*;
import java.rmi.*;
import javax.ejb.*;
import javax.naming.*;
import com.ibm.ivj.ejb.associations.interfaces.*;
import com.ibm.ivj.ejb.associations.links.*;
/**
 * ArdaisuserToShoppingcartLink
 * @generated
 */
public class ArdaisuserToShoppingcartLink
	extends com.ibm.ivj.ejb.associations.links.SingleToManyLink {
	/**
	 * @generated
	 */
	private static com.ardais.bigr.es.beans.ShoppingcartHome targetHome;
	/**
	 * @generated
	 */
	private static final java.lang.String targetHomeName =
		"ejb/com/ardais/bigr/es/beans/ShoppingcartHome";
	/**
	 * Create a link instance with the passed source bean.
	 * @generated
	 */
	public ArdaisuserToShoppingcartLink(javax.ejb.EntityBean anEntityBean) {
		super(anEntityBean);
	}
	/**
	 * Get the target home for the link.
	 * @generated
	 */
	protected synchronized com
		.ardais
		.bigr
		.es
		.beans
		.ShoppingcartHome getTargetHome(
		com.ibm.ivj.ejb.associations.links.Link aLink)
		throws javax.naming.NamingException {
		if (targetHome == null)
			targetHome =
				(com
					.ardais
					.bigr
					.es
					.beans
					.ShoppingcartHome) lookupTargetHome("java:comp/env/ejb/Shoppingcart",
					com.ardais.bigr.es.beans.ShoppingcartHome.class);
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
					(com.ardais.bigr.es.beans.ShoppingcartHome) getTargetHome(
						this)).findShoppingcartByArdaisuser(
					(com.ardais.bigr.es.beans.ArdaisuserKey) getEntityContext()
						.getPrimaryKey());
		} catch (NamingException e) {
			throw new FinderException(e.toString());
		}
		return enum1;
	}
	/**
	 * Get the entity context from the source bean.
	 * @generated
	 */
	public javax.ejb.EntityContext getEntityContext() {
		return ((com.ardais.bigr.es.beans.ArdaisuserBean) source)
			.getEntityContext();
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
	public void setNullCounterLinkOf(javax.ejb.EJBObject anEJB)
		throws java.rmi.RemoteException {
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
			com.ardais.bigr.es.beans.Shoppingcart.class);
	}
	/**
	 * Check if I contain anEJB by comparing primary keys.
	 * @generated
	 */
	protected boolean currentlyContains(javax.ejb.EJBObject anEJB)
		throws java.rmi.RemoteException {
		return super.currentlyContains(anEJB)
			|| getEntityContext().getPrimaryKey().equals(
				((com.ardais.bigr.es.beans.Shoppingcart) anEJB)
					.getArdaisuserKey());
	}
}