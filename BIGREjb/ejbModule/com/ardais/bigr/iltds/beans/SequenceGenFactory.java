package com.ardais.bigr.iltds.beans;
import javax.ejb.*;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import com.ibm.etools.ejb.client.runtime.*;
/**
 * SequenceGenFactory
 * @generated
 */
public class SequenceGenFactory extends AbstractEJBFactory {
	/**
	 * SequenceGenFactory
	 * @generated
	 */
	public SequenceGenFactory() {
		super();
	}
	/**
	 * _acquireSequenceGenHome
	 * @generated
	 */
	protected com
		.ardais
		.bigr
		.iltds
		.beans
		.SequenceGenHome _acquireSequenceGenHome()
		throws java.rmi.RemoteException {
		return (com.ardais.bigr.iltds.beans.SequenceGenHome) _acquireEJBHome();
	}
	/**
	 * acquireSequenceGenHome
	 * @generated
	 */
	public com.ardais.bigr.iltds.beans.SequenceGenHome acquireSequenceGenHome()
		throws javax.naming.NamingException {
		return (com.ardais.bigr.iltds.beans.SequenceGenHome) acquireEJBHome();
	}
	/**
	 * getDefaultJNDIName
	 * @generated
	 */
	public String getDefaultJNDIName() {
		return "com/ardais/bigr/iltds/beans/Sequence";
	}
	/**
	 * getHomeInterface
	 * @generated
	 */
	protected Class getHomeInterface() {
		return com.ardais.bigr.iltds.beans.SequenceGenHome.class;
	}
	/**
	 * resetSequenceGenHome
	 * @generated
	 */
	public void resetSequenceGenHome() {
		resetEJBHome();
	}
	/**
	 * setSequenceGenHome
	 * @generated
	 */
	public void setSequenceGenHome(
		com.ardais.bigr.iltds.beans.SequenceGenHome home) {
		setEJBHome(home);
	}
	/**
	 * create
	 * @generated
	 */
	public com.ardais.bigr.iltds.beans.SequenceGen create()
		throws CreateException, RemoteException {
		return _acquireSequenceGenHome().create();
	}
}
