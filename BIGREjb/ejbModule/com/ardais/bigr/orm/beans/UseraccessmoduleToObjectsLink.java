package com.ardais.bigr.orm.beans;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.ibm.ivj.ejb.associations.interfaces.Link;
import com.ibm.ivj.ejb.associations.links.ManyToSingleLink;
public class UseraccessmoduleToObjectsLink extends ManyToSingleLink {
	private static com.ardais.bigr.orm.beans.ObjectsHome targetHome = null;
	private final static java.lang.String targetHomeName = "Objects";
	/**
	 * Create a link instance with the passed source bean
	 * @param anEntityBean javax.ejb.EntityBean
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public UseraccessmoduleToObjectsLink(javax.ejb.EntityBean anEntityBean) {
		super(anEntityBean);
		isRequired = true;
	}
	/**
	 * Fetch the target for this single link, return an instance of the target class.
	 * @return javax.ejb.EJBObject
	 * @exception javax.ejb.FinderException The exception description.
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	protected javax.ejb.EJBObject fetchTarget()
		throws java.rmi.RemoteException, javax.ejb.FinderException {
		EJBObject target = null;
		com.ardais.bigr.orm.beans.ObjectsKey key =
			((com.ardais.bigr.orm.beans.UseraccessmoduleBean) source).getObjectsKey();
		try {
			target =
				((com.ardais.bigr.orm.beans.ObjectsHome) getTargetHome(this)).findByPrimaryKey(
					key);
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
		return ((com.ardais.bigr.orm.beans.UseraccessmoduleBean) source)
			.getEntityContext();
	}
	/**
	 * Get the target home for the link.
	 * @return com.ardais.bigr.orm.beans.ObjectsHome
	 * @param aLink com.ibm.ivj.ejb.associations.links.Link
	 * @exception javax.naming.NamingException The exception description.
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	protected static synchronized com
		.ardais
		.bigr
		.orm
		.beans
		.ObjectsHome getTargetHome(com.ibm.ivj.ejb.associations.links.Link aLink)
		throws javax.naming.NamingException {
		if (targetHome == null) {
			Context initCtx = new InitialContext();
			Context env = (Context) initCtx.lookup("java:comp/env");
			String homeName = (String) env.lookup("ejb10-properties/Objects");
			targetHome =
				(com.ardais.bigr.orm.beans.ObjectsHome) lookupTargetHome(homeName,
					com.ardais.bigr.orm.beans.ObjectsHome.class);
		}
		return targetHome;
	}
	/**
	 * Return whether or not the source key is valid for querying.
	 * @return boolean
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	protected boolean isKeyValid() {
		return (
			((com.ardais.bigr.orm.beans.UseraccessmoduleBean) source).getObjectsKey()
				!= null);
	}
	/**
	 * Reset my target key.
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	protected void resetKey() throws java.rmi.RemoteException {
		((com.ardais.bigr.orm.beans.UseraccessmoduleBean) source).privateSetObjectsKey(
			null);
	}
	/**
	 * Forward inverse maintenance through my target EJB.
	 * @param anEJB javax.ejb.EJBObject
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public void secondaryAddElementCounterLinkOf(javax.ejb.EJBObject anEJB)
		throws java.rmi.RemoteException {
		if (anEJB != null)
			((com.ardais.bigr.orm.beans.Objects) anEJB).secondaryAddUseraccessmodule(
				(com.ardais.bigr.orm.beans.Useraccessmodule) getEntityContext().getEJBObject());
	}
	/**
	 * Forward inverse maintenance through my target EJB.
	 * @param anEJB javax.ejb.EJBObject
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public void secondaryRemoveElementCounterLinkOf(javax.ejb.EJBObject anEJB)
		throws java.rmi.RemoteException {
		if (anEJB != null)
			((com.ardais.bigr.orm.beans.Objects) anEJB).secondaryRemoveUseraccessmodule(
				(com.ardais.bigr.orm.beans.Useraccessmodule) getEntityContext().getEJBObject());
	}
	/**
	 * Set the target for this single link, an instance of the target class.
	 * @param targetEJB javax.ejb.EJBObject
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public void set(javax.ejb.EJBObject targetEJB)
		throws java.rmi.RemoteException {
		super.set(targetEJB);
		if (targetEJB == null)
			((com.ardais.bigr.orm.beans.UseraccessmoduleBean) source).privateSetObjectsKey(
				null);
		else
			((com.ardais.bigr.orm.beans.UseraccessmoduleBean) source).privateSetObjectsKey(
				(com.ardais.bigr.orm.beans.ObjectsKey) targetEJB.getPrimaryKey());
	}
}