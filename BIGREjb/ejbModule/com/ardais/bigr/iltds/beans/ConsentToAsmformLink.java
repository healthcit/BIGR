package com.ardais.bigr.iltds.beans;

import com.ibm.ivj.ejb.associations.interfaces.*;
import com.ibm.ivj.ejb.associations.links.*;
import java.rmi.*;
import java.util.*;
import javax.ejb.*;
import javax.naming.*;
public class ConsentToAsmformLink extends SingleToManyLink {
	private static com.ardais.bigr.iltds.beans.AsmformHome targetHome = null;
	private final static java.lang.String targetHomeName = "Asmform";
/**
 * Create a link instance with the passed source bean
 * @param anEntityBean javax.ejb.EntityBean
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public ConsentToAsmformLink(javax.ejb.EntityBean anEntityBean) {
	super(anEntityBean);
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
						.equals(((com.ardais.bigr.iltds.beans.Asmform)anEJB).getConsentKey()));
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
		enum1 = ((com.ardais.bigr.iltds.beans.AsmformHome)getTargetHome(this)).findAsmformByConsent((com.ardais.bigr.iltds.beans.ConsentKey)getEntityContext().getPrimaryKey());
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
	return ((com.ardais.bigr.iltds.beans.ConsentBean)source).getEntityContext();
}
/**
 * Get the target home for the link.
 * @return com.ardais.bigr.iltds.beans.AsmformHome
 * @param aLink com.ibm.ivj.ejb.associations.links.Link
 * @exception javax.naming.NamingException The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
protected static synchronized com.ardais.bigr.iltds.beans.AsmformHome getTargetHome(com.ibm.ivj.ejb.associations.links.Link aLink) throws javax.naming.NamingException {
	if (targetHome == null) {
		Context initCtx = new InitialContext();
		Context env = (Context) initCtx.lookup("java:comp/env");
		String homeName = (String) env.lookup("ejb10-properties/Asmform");
		targetHome = (com.ardais.bigr.iltds.beans.AsmformHome) lookupTargetHome(homeName, com.ardais.bigr.iltds.beans.AsmformHome.class);
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
	return (EJBObject)javax.rmi.PortableRemoteObject.narrow(element, com.ardais.bigr.iltds.beans.Asmform.class);
}
/**
 * Direct my counterLink to set my source as its target
 * @param anEJB javax.ejb.EJBObject
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void secondarySetCounterLinkOf(javax.ejb.EJBObject anEJB) throws java.rmi.RemoteException {}
/**
 * Send my counterLink #secondaryDisconnect by routing through my target EJB
 * @param anEJB javax.ejb.EJBObject
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void secondarySetNullCounterLinkOf(javax.ejb.EJBObject anEJB) throws java.rmi.RemoteException {}
/**
 * Send my counterLink #secondaryDisconnect by routing through my target EJB
 * @param anEJB javax.ejb.EJBObject
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void setNullCounterLinkOf(javax.ejb.EJBObject anEJB) throws java.rmi.RemoteException {}
	/**
	 * Add an element to this many link.  Disallow null adds.
	 * @generated
	 */
	public void addElement(javax.ejb.EJBObject targetEJB)
		throws java.rmi.RemoteException {
		if (targetEJB != null) {
			super.addElement(targetEJB);
			(
				(
					com
						.ardais
						.bigr
						.iltds
						.beans
						.Asmform) targetEJB)
						.privateSetConsentKey(
				(com.ardais.bigr.iltds.beans.ConsentKey) getEntityContext()
					.getPrimaryKey());
		}
	}
}
