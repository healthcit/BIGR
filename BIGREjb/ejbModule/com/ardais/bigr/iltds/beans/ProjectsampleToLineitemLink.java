package com.ardais.bigr.iltds.beans;

import com.ibm.ivj.ejb.associations.interfaces.*;
import com.ibm.ivj.ejb.associations.links.*;
import java.rmi.*;
import java.util.*;
import javax.ejb.*;
import javax.naming.*;
public class ProjectsampleToLineitemLink extends ManyToSingleLink {
	private static com.ardais.bigr.iltds.beans.LineitemHome targetHome = null;
	private final static java.lang.String targetHomeName = "Lineitem";
/**
 * Create a link instance with the passed source bean
 * @param anEntityBean javax.ejb.EntityBean
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public ProjectsampleToLineitemLink(javax.ejb.EntityBean anEntityBean) {
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
	com.ardais.bigr.iltds.beans.LineitemKey key = ((com.ardais.bigr.iltds.beans.ProjectsampleBean)source).getLineitemKey();
	try {
		target = ((com.ardais.bigr.iltds.beans.LineitemHome)getTargetHome(this)).findByPrimaryKey(key);
	}
	catch (NamingException e) {
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
	return ((com.ardais.bigr.iltds.beans.ProjectsampleBean)source).getEntityContext();
}
/**
 * Get the target home for the link.
 * @return com.ardais.bigr.iltds.beans.LineitemHome
 * @param aLink com.ibm.ivj.ejb.associations.links.Link
 * @exception javax.naming.NamingException The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
protected static synchronized com.ardais.bigr.iltds.beans.LineitemHome getTargetHome(com.ibm.ivj.ejb.associations.links.Link aLink) throws javax.naming.NamingException {
	if (targetHome == null) {
		Context initCtx = new InitialContext();
		Context env = (Context)initCtx.lookup("java:comp/env");
		String homeName = (String)env.lookup("ejb10-properties/Lineitem");
		targetHome = (com.ardais.bigr.iltds.beans.LineitemHome)lookupTargetHome(homeName, com.ardais.bigr.iltds.beans.LineitemHome.class);
	}
	return targetHome;
}
/**
 * Return whether or not the source key is valid for querying.
 * @return boolean
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
protected boolean isKeyValid() {
	return (((com.ardais.bigr.iltds.beans.ProjectsampleBean)source).getLineitemKey() != null);
}
/**
 * Reset my target key.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
protected void resetKey() throws java.rmi.RemoteException {
	((com.ardais.bigr.iltds.beans.ProjectsampleBean)source).privateSetLineitemKey(null);
}
/**
 * Forward inverse maintenance through my target EJB.
 * @param anEJB javax.ejb.EJBObject
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void secondaryAddElementCounterLinkOf(javax.ejb.EJBObject anEJB) throws java.rmi.RemoteException {
	if (anEJB != null)
		((com.ardais.bigr.iltds.beans.Lineitem)anEJB).secondaryAddProjectsample((com.ardais.bigr.iltds.beans.Projectsample)getEntityContext().getEJBObject());
}
/**
 * Forward inverse maintenance through my target EJB.
 * @param anEJB javax.ejb.EJBObject
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void secondaryRemoveElementCounterLinkOf(javax.ejb.EJBObject anEJB) throws java.rmi.RemoteException {
	if (anEJB != null)
		((com.ardais.bigr.iltds.beans.Lineitem)anEJB).secondaryRemoveProjectsample((com.ardais.bigr.iltds.beans.Projectsample)getEntityContext().getEJBObject());
}
/**
 * Set the target for this single link, an instance of the target class.
 * @param targetEJB javax.ejb.EJBObject
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void set(javax.ejb.EJBObject targetEJB) throws java.rmi.RemoteException {
	super.set(targetEJB);
	if (targetEJB == null)
		((com.ardais.bigr.iltds.beans.ProjectsampleBean)source).privateSetLineitemKey(null);
	else
		((com.ardais.bigr.iltds.beans.ProjectsampleBean)source).privateSetLineitemKey((com.ardais.bigr.iltds.beans.LineitemKey)targetEJB.getPrimaryKey());
}
}
