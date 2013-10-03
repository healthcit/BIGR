package com.ardais.bigr.es.beans;

import java.rmi.RemoteException;
import java.util.Enumeration;

import javax.ejb.EJBObject;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import com.ibm.ivj.ejb.associations.links.Link;
import com.ibm.ivj.ejb.associations.links.SingleToManyLink;
import java.util.*;
import java.rmi.*;
import javax.ejb.*;
import javax.naming.*;
import com.ibm.ivj.ejb.associations.interfaces.*;
import com.ibm.ivj.ejb.associations.links.*;
public class ArdaisorderToOrderlineLink extends SingleToManyLink {
	private static com.ardais.bigr.es.beans.OrderlineHome targetHome = null;
	private final static java.lang.String targetHomeName = "Orderline";
	/**
	 * Create a link instance with the passed source bean
	 * @param anEntityBean javax.ejb.EntityBean
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public ArdaisorderToOrderlineLink(javax.ejb.EntityBean anEntityBean) {
		super(anEntityBean);
	}
	/**
	 * Check if I contain anEJB by comparing primary keys.
	 * @return boolean
	 * @param anEJB javax.ejb.EJBObject
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	protected boolean currentlyContains(javax.ejb.EJBObject anEJB) throws java.rmi.RemoteException {
		return (
			super.currentlyContains(anEJB)
				|| getEntityContext().getPrimaryKey().equals(((com.ardais.bigr.es.beans.Orderline) anEJB).getArdaisorderKey()));
	}
	/**
	 * Fetch the target for this many link, return an enumeration of the members.
	 * @return java.util.Enumeration
	 * @exception javax.ejb.FinderException The exception description.
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	protected java.util.Enumeration fetchTargetEnumeration() throws java.rmi.RemoteException, javax.ejb.FinderException {
		Enumeration enum1 = null;
		try {
			enum1 =
				((com.ardais.bigr.es.beans.OrderlineHome) getTargetHome(this)).findOrderlineByArdaisorder(
					(com.ardais.bigr.es.beans.ArdaisorderKey) getEntityContext().getPrimaryKey());
		} catch (NamingException e) {
			throw new FinderException(e.toString());
		}
		return enum1;
	}
	/**
	 * Get the entity context from the source bean.
	 * @return javax.ejb.EntityContext
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public javax.ejb.EntityContext getEntityContext() {
		return ((com.ardais.bigr.es.beans.ArdaisorderBean) source).getEntityContext();
	}
	/**
	 * Get the target home for the link.
	 * @return com.ardais.bigr.es.beans.OrderlineHome
	 * @param aLink com.ibm.ivj.ejb.associations.links.Link
	 * @exception javax.naming.NamingException The exception description.
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	protected static synchronized com.ardais.bigr.es.beans.OrderlineHome getTargetHome(
		com.ibm.ivj.ejb.associations.links.Link aLink)
		throws javax.naming.NamingException {
		if (targetHome == null) {
			Context initCtx = new InitialContext();
			Context env = (Context) initCtx.lookup("java:comp/env");
			String homeName = (String) env.lookup("ejb10-properties/Orderline");
			targetHome =
				(com.ardais.bigr.es.beans.OrderlineHome) lookupTargetHome(homeName, com.ardais.bigr.es.beans.OrderlineHome.class);
		}
		return targetHome;
	}
	/**
	 * Return whether or not the source key is valid for querying.
	 * @return boolean
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	protected boolean isKeyValid() {
		return (getEntityContext().getPrimaryKey() != null);
	}
	/**
	 * Narrow the remote object.
	 * @return javax.ejb.EJBObject
	 * @param element java.lang.Object
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	protected javax.ejb.EJBObject narrowElement(java.lang.Object element) throws java.rmi.RemoteException {
		return (EJBObject) javax.rmi.PortableRemoteObject.narrow(element, com.ardais.bigr.es.beans.Orderline.class);
	}
	/**
	 * Direct my counterLink to set my source as its target
	 * @param anEJB javax.ejb.EJBObject
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public void secondarySetCounterLinkOf(javax.ejb.EJBObject anEJB) throws java.rmi.RemoteException {
	}
	/**
	 * Send my counterLink #secondaryDisconnect by routing through my target EJB
	 * @param anEJB javax.ejb.EJBObject
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public void secondarySetNullCounterLinkOf(javax.ejb.EJBObject anEJB) throws java.rmi.RemoteException {
	}
	/**
	 * Send my counterLink #secondaryDisconnect by routing through my target EJB
	 * @param anEJB javax.ejb.EJBObject
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public void setNullCounterLinkOf(javax.ejb.EJBObject anEJB) throws java.rmi.RemoteException {
	}
}