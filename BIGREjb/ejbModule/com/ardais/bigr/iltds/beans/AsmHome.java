package com.ardais.bigr.iltds.beans;

import java.util.*;
/**
 * This is a Home interface for the Entity Bean
 */
public interface AsmHome extends javax.ejb.EJBHome {

	/**
	 * Creates an instance from a key for Entity Bean: Asm
	 */
	public com.ardais.bigr.iltds.beans.Asm create(java.lang.String asm_id)
		throws javax.ejb.CreateException, java.rmi.RemoteException;
/**
 * Insert the method's description here.
 * Creation date: (2/9/2001 3:01:20 PM)
 * @return java.util.Enumeration
 * @param argConsentID java.lang.String
 * @exception javax.ejb.FinderException The exception description.
 */
java.util.Enumeration findByConsentID(String argConsentID) throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * findByPrimaryKey method comment
 * @return com.ardais.bigr.iltds.beans.Asm
 * @param key com.ardais.bigr.iltds.beans.AsmKey
 * @exception javax.ejb.FinderException The exception description.
 */
com.ardais.bigr.iltds.beans.Asm findByPrimaryKey(com.ardais.bigr.iltds.beans.AsmKey key) throws java.rmi.RemoteException, javax.ejb.FinderException;

}
