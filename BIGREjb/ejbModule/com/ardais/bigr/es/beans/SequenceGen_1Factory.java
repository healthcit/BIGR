package com.ardais.bigr.es.beans;
import javax.ejb.*;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import com.ibm.etools.ejb.client.runtime.*;
/**
 * SequenceGen_1Factory
 * @generated
 */
public class SequenceGen_1Factory extends AbstractEJBFactory {
	/**
	 * SequenceGen_1Factory
	 * @generated
	 */
	public SequenceGen_1Factory() {
		super();
	}
	/**
	 * _acquireSequenceGen_1Home
	 * @generated
	 */
	protected com
		.ardais
		.bigr
		.es
		.beans
		.SequenceGenHome _acquireSequenceGen_1Home()
		throws java.rmi.RemoteException {
		return (com.ardais.bigr.es.beans.SequenceGenHome) _acquireEJBHome();
	}
	/**
	 * acquireSequenceGen_1Home
	 * @generated
	 */
	public com.ardais.bigr.es.beans.SequenceGenHome acquireSequenceGen_1Home()
		throws javax.naming.NamingException {
		return (com.ardais.bigr.es.beans.SequenceGenHome) acquireEJBHome();
	}
	/**
	 * getDefaultJNDIName
	 * @generated
	 */
	public String getDefaultJNDIName() {
		return "com/ardais/bigr/es/beans/SequenceGen";
	}
	/**
	 * getHomeInterface
	 * @generated
	 */
	protected Class getHomeInterface() {
		return com.ardais.bigr.es.beans.SequenceGenHome.class;
	}
	/**
	 * resetSequenceGen_1Home
	 * @generated
	 */
	public void resetSequenceGen_1Home() {
		resetEJBHome();
	}
	/**
	 * setSequenceGen_1Home
	 * @generated
	 */
	public void setSequenceGen_1Home(
		com.ardais.bigr.es.beans.SequenceGenHome home) {
		setEJBHome(home);
	}
	/**
	 * create
	 * @generated
	 */
	public com.ardais.bigr.es.beans.SequenceGen create()
		throws CreateException, RemoteException {
		return _acquireSequenceGen_1Home().create();
	}
}
