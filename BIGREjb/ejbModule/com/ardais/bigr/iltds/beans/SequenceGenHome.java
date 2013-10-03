package com.ardais.bigr.iltds.beans;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;


/**
 * This is a Home interface for the Session Bean
 */
public interface SequenceGenHome extends javax.ejb.EJBHome {

	/**
	 * create method for a session bean
	 * @return com.ardais.bigr.iltds.beans.Sequence
	 * @exception javax.ejb.CreateException The exception description.
	 */
	com.ardais.bigr.iltds.beans.SequenceGen create()
		throws javax.ejb.CreateException, java.rmi.RemoteException;
}