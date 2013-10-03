package com.ardais.bigr.iltds.beans;

import com.ibm.ivj.ejb.associations.interfaces.*;
import com.ibm.ivj.ejb.associations.links.*;
import java.rmi.*;
import java.util.*;
import javax.ejb.*;
import javax.naming.*;
public class BoxlocationToGeolocationLink extends ManyToSingleLink {
	private static com.ardais.bigr.iltds.beans.GeolocationHome targetHome = null;
	private final static java.lang.String targetHomeName = "Geolocation";
/**
 * Create a link instance with the passed source bean
 * @param anEntityBean javax.ejb.EntityBean
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public BoxlocationToGeolocationLink(javax.ejb.EntityBean anEntityBean) {
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
	com.ardais.bigr.iltds.beans.GeolocationKey key = ((com.ardais.bigr.iltds.beans.BoxlocationBean)source).getGeolocationKey();
	try {
		target = ((com.ardais.bigr.iltds.beans.GeolocationHome)getTargetHome(this)).findByPrimaryKey(key);
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
	return ((com.ardais.bigr.iltds.beans.BoxlocationBean)source).getEntityContext();
}
/**
 * Get the target home for the link.
 * @return com.ardais.bigr.iltds.beans.GeolocationHome
 * @param aLink com.ibm.ivj.ejb.associations.links.Link
 * @exception javax.naming.NamingException The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
protected static synchronized com.ardais.bigr.iltds.beans.GeolocationHome getTargetHome(com.ibm.ivj.ejb.associations.links.Link aLink) throws javax.naming.NamingException {
	if (targetHome == null) {
			Context initCtx = new InitialContext();
		Context env = (Context) initCtx.lookup("java:comp/env");
		String homeName = (String) env.lookup("ejb10-properties/Geolocation");
		targetHome = (com.ardais.bigr.iltds.beans.GeolocationHome) lookupTargetHome(homeName, com.ardais.bigr.iltds.beans.GeolocationHome.class);
	}
	return targetHome;
}
/**
 * Return whether or not the source key is valid for querying.
 * @return boolean
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
protected boolean isKeyValid() {
	return (((com.ardais.bigr.iltds.beans.BoxlocationBean)source).getGeolocationKey() != null);
}
/**
 * Reset my target key.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
protected void resetKey() throws java.rmi.RemoteException {
	((com.ardais.bigr.iltds.beans.BoxlocationBean)source).privateSetGeolocationKey(null);
}
/**
 * Forward inverse maintenance through my target EJB.
 * @param anEJB javax.ejb.EJBObject
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void secondaryAddElementCounterLinkOf(javax.ejb.EJBObject anEJB) throws java.rmi.RemoteException {
	if (anEJB != null)
		((com.ardais.bigr.iltds.beans.Geolocation)anEJB).secondaryAddBoxlocation((com.ardais.bigr.iltds.beans.Boxlocation)getEntityContext().getEJBObject());
}
/**
 * Forward inverse maintenance through my target EJB.
 * @param anEJB javax.ejb.EJBObject
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void secondaryRemoveElementCounterLinkOf(javax.ejb.EJBObject anEJB) throws java.rmi.RemoteException {
	if (anEJB != null)
		((com.ardais.bigr.iltds.beans.Geolocation)anEJB).secondaryRemoveBoxlocation((com.ardais.bigr.iltds.beans.Boxlocation)getEntityContext().getEJBObject());
}
/**
 * Set the target for this single link, an instance of the target class.
 * @param targetEJB javax.ejb.EJBObject
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void set(javax.ejb.EJBObject targetEJB) throws java.rmi.RemoteException {
	super.set(targetEJB);
	if (targetEJB == null)
		((com.ardais.bigr.iltds.beans.BoxlocationBean)source).privateSetGeolocationKey(null);
	else
		((com.ardais.bigr.iltds.beans.BoxlocationBean)source).privateSetGeolocationKey((com.ardais.bigr.iltds.beans.GeolocationKey)targetEJB.getPrimaryKey());
}
}
