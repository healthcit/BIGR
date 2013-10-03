package com.ardais.bigr.iltds.beans;

import com.ibm.ivj.ejb.associations.interfaces.*;
import com.ibm.ivj.ejb.associations.links.*;
import java.rmi.*;
import java.util.*;
import javax.ejb.*;
import javax.naming.*;
public class SampleboxToSampleLink extends SingleToManyLink {
	private static com.ardais.bigr.iltds.beans.SampleHome targetHome = null;
	private final static java.lang.String targetHomeName = "Sample";
/**
 * Create a link instance with the passed source bean
 * @param anEntityBean javax.ejb.EntityBean
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public SampleboxToSampleLink(javax.ejb.EntityBean anEntityBean) {
	super(anEntityBean);
}
/**
 * Add an element to this many link.  Disallow null adds.
 * @param targetEJB javax.ejb.EJBObject
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void addElement(javax.ejb.EJBObject targetEJB) throws java.rmi.RemoteException {
	if (targetEJB != null) {
		super.addElement(targetEJB);
		((com.ardais.bigr.iltds.beans.Sample)targetEJB).privateSetSampleboxKey((com.ardais.bigr.iltds.beans.SampleboxKey)getEntityContext().getPrimaryKey());
	}
}
/**
 * Check if I contain anEJB by comparing primary keys.
 * @return boolean
 * @param anEJB javax.ejb.EJBObject
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
protected boolean currentlyContains(javax.ejb.EJBObject anEJB) throws java.rmi.RemoteException {
	return (	super.currentlyContains(anEJB)
			||	getEntityContext().getPrimaryKey()
						.equals(((com.ardais.bigr.iltds.beans.Sample)anEJB).getSampleboxKey()));
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
		enum1 = ((com.ardais.bigr.iltds.beans.SampleHome)getTargetHome(this)).findSampleBySamplebox((com.ardais.bigr.iltds.beans.SampleboxKey)getEntityContext().getPrimaryKey());
	}
	catch (NamingException e) {
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
	return ((com.ardais.bigr.iltds.beans.SampleboxBean)source).getEntityContext();
}
/**
 * Get the target home for the link.
 * @return com.ardais.bigr.iltds.beans.SampleHome
 * @param aLink com.ibm.ivj.ejb.associations.links.Link
 * @exception javax.naming.NamingException The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
protected static synchronized com.ardais.bigr.iltds.beans.SampleHome getTargetHome(com.ibm.ivj.ejb.associations.links.Link aLink) throws javax.naming.NamingException {
	if (targetHome == null) {
		Context initCtx = new InitialContext();
		Context env = (Context) initCtx.lookup("java:comp/env");
		String homeName = (String) env.lookup("ejb10-properties/Sample");
		targetHome = (com.ardais.bigr.iltds.beans.SampleHome) lookupTargetHome(homeName, com.ardais.bigr.iltds.beans.SampleHome.class);
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
	return (EJBObject)javax.rmi.PortableRemoteObject.narrow(element, com.ardais.bigr.iltds.beans.Sample.class);
}
/**
 * Direct my counterLink to set my source as its target
 * @param anEJB javax.ejb.EJBObject
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void secondarySetCounterLinkOf(javax.ejb.EJBObject anEJB) throws java.rmi.RemoteException {
	if (anEJB != null)
		((com.ardais.bigr.iltds.beans.Sample)anEJB).secondarySetSamplebox((com.ardais.bigr.iltds.beans.Samplebox)getEntityContext().getEJBObject());
}
/**
 * Send my counterLink #secondaryDisconnect by routing through my target EJB
 * @param anEJB javax.ejb.EJBObject
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void secondarySetNullCounterLinkOf(javax.ejb.EJBObject anEJB) throws java.rmi.RemoteException {
	if (anEJB != null)
		((com.ardais.bigr.iltds.beans.Sample)anEJB).secondarySetSamplebox(null);
}
/**
 * Send my counterLink #secondaryDisconnect by routing through my target EJB
 * @param anEJB javax.ejb.EJBObject
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void setNullCounterLinkOf(javax.ejb.EJBObject anEJB) throws java.rmi.RemoteException {
	if (anEJB != null)
		((com.ardais.bigr.iltds.beans.Sample)anEJB).setSamplebox(null);
}
}
