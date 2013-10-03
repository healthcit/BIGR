package com.ardais.bigr.iltds.beans;
import javax.ejb.*;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import com.ibm.etools.ejb.client.runtime.*;
/**
 * WorkorderFactory
 * @generated
 */
public class WorkorderFactory extends AbstractEJBFactory {
	/**
	 * WorkorderFactory
	 * @generated
	 */
	public WorkorderFactory() {
		super();
	}
	/**
	 * _acquireWorkorderHome
	 * @generated
	 */
	protected com.ardais.bigr.iltds.beans.WorkorderHome _acquireWorkorderHome()
		throws java.rmi.RemoteException {
		return (com.ardais.bigr.iltds.beans.WorkorderHome) _acquireEJBHome();
	}
	/**
	 * acquireWorkorderHome
	 * @generated
	 */
	public com.ardais.bigr.iltds.beans.WorkorderHome acquireWorkorderHome()
		throws javax.naming.NamingException {
		return (com.ardais.bigr.iltds.beans.WorkorderHome) acquireEJBHome();
	}
	/**
	 * getDefaultJNDIName
	 * @generated
	 */
	public String getDefaultJNDIName() {
		return "com/ardais/bigr/iltds/beans/Workorder";
	}
	/**
	 * getHomeInterface
	 * @generated
	 */
	protected Class getHomeInterface() {
		return com.ardais.bigr.iltds.beans.WorkorderHome.class;
	}
	/**
	 * resetWorkorderHome
	 * @generated
	 */
	public void resetWorkorderHome() {
		resetEJBHome();
	}
	/**
	 * setWorkorderHome
	 * @generated
	 */
	public void setWorkorderHome(
		com.ardais.bigr.iltds.beans.WorkorderHome home) {
		setEJBHome(home);
	}
	/**
	 * findByPrimaryKey
	 * @generated
	 */
	public com.ardais.bigr.iltds.beans.Workorder findByPrimaryKey(
		com.ardais.bigr.iltds.beans.WorkorderKey key)
		throws RemoteException, FinderException {
		return _acquireWorkorderHome().findByPrimaryKey(key);
	}
	/**
	 * create
	 * @generated
	 */
	public com.ardais.bigr.iltds.beans.Workorder create(
		java.lang.String argWorkorderid,
		java.lang.String argProjectid,
		java.lang.String argWorkordertype,
		java.lang.String argWorkordername,
		java.math.BigDecimal argListorder)
		throws CreateException, RemoteException {
		return _acquireWorkorderHome().create(
			argWorkorderid,
			argProjectid,
			argWorkordertype,
			argWorkordername,
			argListorder);
	}
	/**
	 * create
	 * @generated
	 */
	public com.ardais.bigr.iltds.beans.Workorder create(
		java.lang.String argWorkorderid)
		throws CreateException, RemoteException {
		return _acquireWorkorderHome().create(argWorkorderid);
	}
	/**
	 * findWorkorderByProject
	 * @generated
	 */
	public java.util.Enumeration findWorkorderByProject(
		com.ardais.bigr.iltds.beans.ProjectKey inKey)
		throws RemoteException, FinderException {
		return _acquireWorkorderHome().findWorkorderByProject(inKey);
	}
}
