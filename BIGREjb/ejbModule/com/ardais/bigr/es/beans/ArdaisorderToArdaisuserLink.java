package com.ardais.bigr.es.beans;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.ibm.ivj.ejb.associations.links.Link;
import com.ibm.ivj.ejb.associations.links.ManyToSingleLink;
import java.util.*;
import java.rmi.*;
import javax.ejb.*;
import javax.naming.*;
import com.ibm.ivj.ejb.associations.interfaces.*;
import com.ibm.ivj.ejb.associations.links.*;
public class ArdaisorderToArdaisuserLink extends ManyToSingleLink {
	private static ArdaisuserHome targetHome = null;
	private final static java.lang.String targetHomeName = "Ardaisuser";
	/**
	 * Create a link instance with the passed source bean
	 * @param anEntityBean javax.ejb.EntityBean
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public ArdaisorderToArdaisuserLink(javax.ejb.EntityBean anEntityBean) {
		super(anEntityBean);
		isRequired = true;
	}
	/**
	 * Fetch the target for this single link, return an instance of the target class.
	 * @return javax.ejb.EJBObject
	 * @exception javax.ejb.FinderException The exception description.
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	protected javax.ejb.EJBObject fetchTarget() throws java.rmi.RemoteException, javax.ejb.FinderException {
		EJBObject target = null;
		com.ardais.bigr.es.beans.ArdaisuserKey key = ((com.ardais.bigr.es.beans.ArdaisorderBean) source).getArdaisuserKey();
		try {
			target = ((com.ardais.bigr.es.beans.ArdaisuserHome) getTargetHome(this)).findByPrimaryKey(key);
		} catch (NamingException e) {
			throw new FinderException(e.toString());
		}
		return target;
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
	 * @return com.ardais.bigr.es.beans.ArdaisuserHome
	 * @param aLink com.ibm.ivj.ejb.associations.links.Link
	 * @exception javax.naming.NamingException The exception description.
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	protected static synchronized ArdaisuserHome getTargetHome(com.ibm.ivj.ejb.associations.links.Link aLink)
		throws javax.naming.NamingException {
		if (targetHome == null) {
			Context initCtx = new InitialContext();
			Context env = (Context) initCtx.lookup("java:comp/env");
			String homeName = (String) env.lookup("ejb10-properties/Ardaisuser");
			targetHome =
				(com.ardais.bigr.es.beans.ArdaisuserHome) lookupTargetHome(homeName, com.ardais.bigr.es.beans.ArdaisuserHome.class);
		}
		return targetHome;
	}
	/**
	 * Return whether or not the source key is valid for querying.
	 * @return boolean
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	protected boolean isKeyValid() {
		return (((com.ardais.bigr.es.beans.ArdaisorderBean) source).getArdaisuserKey() != null);
	}
	/**
	 * Reset my target key.
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	protected void resetKey() throws java.rmi.RemoteException {
		((com.ardais.bigr.es.beans.ArdaisorderBean) source).privateSetArdaisuserKey(null);
	}
	/**
	 * Forward inverse maintenance through my target EJB.
	 * @param anEJB javax.ejb.EJBObject
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public void secondaryAddElementCounterLinkOf(javax.ejb.EJBObject anEJB) throws java.rmi.RemoteException {
		if (anEJB != null)
			((com.ardais.bigr.es.beans.Ardaisuser) anEJB).secondaryAddArdaisorder(
				(com.ardais.bigr.es.beans.Ardaisorder) getEntityContext().getEJBObject());
	}
	/**
	 * Forward inverse maintenance through my target EJB.
	 * @param anEJB javax.ejb.EJBObject
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public void secondaryRemoveElementCounterLinkOf(javax.ejb.EJBObject anEJB) throws java.rmi.RemoteException {
		if (anEJB != null)
			((com.ardais.bigr.es.beans.Ardaisuser) anEJB).secondaryRemoveArdaisorder(
				(com.ardais.bigr.es.beans.Ardaisorder) getEntityContext().getEJBObject());
	}
	/**
	 * Set the target for this single link, an instance of the target class.
	 * @param targetEJB javax.ejb.EJBObject
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public void set(javax.ejb.EJBObject targetEJB) throws java.rmi.RemoteException {
		super.set(targetEJB);
		if (targetEJB == null)
			 ((com.ardais.bigr.es.beans.ArdaisorderBean) source).privateSetArdaisuserKey(null);
		else
			((com.ardais.bigr.es.beans.ArdaisorderBean) source).privateSetArdaisuserKey(
				(com.ardais.bigr.es.beans.ArdaisuserKey) targetEJB.getPrimaryKey());
	}
}