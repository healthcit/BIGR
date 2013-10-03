package com.ardais.bigr.iltds.beans;
import javax.ejb.*;
import javax.rmi.*;
import com.ibm.ivj.ejb.runtime.*;
/**
 * WorkorderAccessBean
 * @generated
 */
public class WorkorderAccessBean
	extends AbstractEntityAccessBean
	implements com.ardais.bigr.iltds.beans.WorkorderAccessBeanData {
	/**
	 * @generated
	 */
	private Workorder __ejbRef;
	/**
	 * @generated
	 */
	private java.lang.String init_argWorkorderid;
	/**
	 * @generated
	 */
	private java.lang.String init_argProjectid;
	/**
	 * @generated
	 */
	private java.lang.String init_argWorkordertype;
	/**
	 * @generated
	 */
	private java.lang.String init_argWorkordername;
	/**
	 * @generated
	 */
	private java.math.BigDecimal init_argListorder;
	/**
	 * getProjectKey
	 * @generated
	 */
	public com.ardais.bigr.iltds.beans.ProjectKey getProjectKey()
		throws
			java.rmi.RemoteException,
			javax.ejb.CreateException,
			javax.ejb.FinderException,
			javax.naming.NamingException {
		return (
			((com
				.ardais
				.bigr
				.iltds
				.beans
				.ProjectKey) __getCache("projectKey")));
	}
	/**
	 * getWorkordername
	 * @generated
	 */
	public java.lang.String getWorkordername()
		throws
			java.rmi.RemoteException,
			javax.ejb.CreateException,
			javax.ejb.FinderException,
			javax.naming.NamingException {
		return (((java.lang.String) __getCache("workordername")));
	}
	/**
	 * setWorkordername
	 * @generated
	 */
	public void setWorkordername(java.lang.String newValue) {
		__setCache("workordername", newValue);
	}
	/**
	 * getListorder
	 * @generated
	 */
	public java.math.BigDecimal getListorder()
		throws
			java.rmi.RemoteException,
			javax.ejb.CreateException,
			javax.ejb.FinderException,
			javax.naming.NamingException {
		return (((java.math.BigDecimal) __getCache("listorder")));
	}
	/**
	 * setListorder
	 * @generated
	 */
	public void setListorder(java.math.BigDecimal newValue) {
		__setCache("listorder", newValue);
	}
	/**
	 * getEnddate
	 * @generated
	 */
	public java.sql.Timestamp getEnddate()
		throws
			java.rmi.RemoteException,
			javax.ejb.CreateException,
			javax.ejb.FinderException,
			javax.naming.NamingException {
		return (((java.sql.Timestamp) __getCache("enddate")));
	}
	/**
	 * setEnddate
	 * @generated
	 */
	public void setEnddate(java.sql.Timestamp newValue) {
		__setCache("enddate", newValue);
	}
	/**
	 * getStatus
	 * @generated
	 */
	public java.lang.String getStatus()
		throws
			java.rmi.RemoteException,
			javax.ejb.CreateException,
			javax.ejb.FinderException,
			javax.naming.NamingException {
		return (((java.lang.String) __getCache("status")));
	}
	/**
	 * setStatus
	 * @generated
	 */
	public void setStatus(java.lang.String newValue) {
		__setCache("status", newValue);
	}
	/**
	 * getNumberofsamples
	 * @generated
	 */
	public java.math.BigDecimal getNumberofsamples()
		throws
			java.rmi.RemoteException,
			javax.ejb.CreateException,
			javax.ejb.FinderException,
			javax.naming.NamingException {
		return (((java.math.BigDecimal) __getCache("numberofsamples")));
	}
	/**
	 * setNumberofsamples
	 * @generated
	 */
	public void setNumberofsamples(java.math.BigDecimal newValue) {
		__setCache("numberofsamples", newValue);
	}
	/**
	 * getNotes
	 * @generated
	 */
	public java.lang.String getNotes()
		throws
			java.rmi.RemoteException,
			javax.ejb.CreateException,
			javax.ejb.FinderException,
			javax.naming.NamingException {
		return (((java.lang.String) __getCache("notes")));
	}
	/**
	 * setNotes
	 * @generated
	 */
	public void setNotes(java.lang.String newValue) {
		__setCache("notes", newValue);
	}
	/**
	 * getWorkordertype
	 * @generated
	 */
	public java.lang.String getWorkordertype()
		throws
			java.rmi.RemoteException,
			javax.ejb.CreateException,
			javax.ejb.FinderException,
			javax.naming.NamingException {
		return (((java.lang.String) __getCache("workordertype")));
	}
	/**
	 * setWorkordertype
	 * @generated
	 */
	public void setWorkordertype(java.lang.String newValue) {
		__setCache("workordertype", newValue);
	}
	/**
	 * getStartdate
	 * @generated
	 */
	public java.sql.Timestamp getStartdate()
		throws
			java.rmi.RemoteException,
			javax.ejb.CreateException,
			javax.ejb.FinderException,
			javax.naming.NamingException {
		return (((java.sql.Timestamp) __getCache("startdate")));
	}
	/**
	 * setStartdate
	 * @generated
	 */
	public void setStartdate(java.sql.Timestamp newValue) {
		__setCache("startdate", newValue);
	}
	/**
	 * getPercentcomplete
	 * @generated
	 */
	public java.math.BigDecimal getPercentcomplete()
		throws
			java.rmi.RemoteException,
			javax.ejb.CreateException,
			javax.ejb.FinderException,
			javax.naming.NamingException {
		return (((java.math.BigDecimal) __getCache("percentcomplete")));
	}
	/**
	 * setPercentcomplete
	 * @generated
	 */
	public void setPercentcomplete(java.math.BigDecimal newValue) {
		__setCache("percentcomplete", newValue);
	}
	/**
	 * setInit_argWorkorderid
	 * @generated
	 */
	public void setInit_argWorkorderid(java.lang.String newValue) {
		this.init_argWorkorderid = (newValue);
	}
	/**
	 * setInit_argProjectid
	 * @generated
	 */
	public void setInit_argProjectid(java.lang.String newValue) {
		this.init_argProjectid = (newValue);
	}
	/**
	 * setInit_argWorkordertype
	 * @generated
	 */
	public void setInit_argWorkordertype(java.lang.String newValue) {
		this.init_argWorkordertype = (newValue);
	}
	/**
	 * setInit_argWorkordername
	 * @generated
	 */
	public void setInit_argWorkordername(java.lang.String newValue) {
		this.init_argWorkordername = (newValue);
	}
	/**
	 * setInit_argListorder
	 * @generated
	 */
	public void setInit_argListorder(java.math.BigDecimal newValue) {
		this.init_argListorder = (newValue);
	}
	/**
	 * WorkorderAccessBean
	 * @generated
	 */
	public WorkorderAccessBean() {
		super();
	}
	/**
	 * WorkorderAccessBean
	 * @generated
	 */
	public WorkorderAccessBean(javax.ejb.EJBObject o)
		throws java.rmi.RemoteException {
		super(o);
	}
	/**
	 * defaultJNDIName
	 * @generated
	 */
	public String defaultJNDIName() {
		return "com/ardais/bigr/iltds/beans/Workorder";
	}
	/**
	 * ejbHome
	 * @generated
	 */
	private com.ardais.bigr.iltds.beans.WorkorderHome ejbHome()
		throws java.rmi.RemoteException, javax.naming.NamingException {
		return (
			com
				.ardais
				.bigr
				.iltds
				.beans
				.WorkorderHome) PortableRemoteObject
				.narrow(
			getHome(),
			com.ardais.bigr.iltds.beans.WorkorderHome.class);
	}
	/**
	 * ejbRef
	 * @generated
	 */
	private com.ardais.bigr.iltds.beans.Workorder ejbRef()
		throws java.rmi.RemoteException {
		if (ejbRef == null)
			return null;
		if (__ejbRef == null)
			__ejbRef =
				(
					com
						.ardais
						.bigr
						.iltds
						.beans
						.Workorder) PortableRemoteObject
						.narrow(
					ejbRef,
					com.ardais.bigr.iltds.beans.Workorder.class);

		return __ejbRef;
	}
	/**
	 * instantiateEJB
	 * @generated
	 */
	protected void instantiateEJB()
		throws
			javax.naming.NamingException,
			javax.ejb.CreateException,
			java.rmi.RemoteException {
		if (ejbRef() != null)
			return;

		ejbRef =
			ejbHome().create(
				init_argWorkorderid,
				init_argProjectid,
				init_argWorkordertype,
				init_argWorkordername,
				init_argListorder);
	}
	/**
	 * instantiateEJBByPrimaryKey
	 * @generated
	 */
	protected boolean instantiateEJBByPrimaryKey()
		throws
			javax.ejb.CreateException,
			java.rmi.RemoteException,
			javax.naming.NamingException {
		boolean result = false;

		if (ejbRef() != null)
			return true;

		try {
			com.ardais.bigr.iltds.beans.WorkorderKey pKey =
				(com.ardais.bigr.iltds.beans.WorkorderKey) this.__getKey();
			if (pKey != null) {
				ejbRef = ejbHome().findByPrimaryKey(pKey);
				result = true;
			}
		} catch (javax.ejb.FinderException e) {
		}
		return result;
	}
	/**
	 * refreshCopyHelper
	 * @generated
	 */
	public void refreshCopyHelper()
		throws
			java.rmi.RemoteException,
			javax.ejb.CreateException,
			javax.ejb.FinderException,
			javax.naming.NamingException {
		refreshCopyHelper(ejbRef());
	}
	/**
	 * commitCopyHelper
	 * @generated
	 */
	public void commitCopyHelper()
		throws
			java.rmi.RemoteException,
			javax.ejb.CreateException,
			javax.ejb.FinderException,
			javax.naming.NamingException {
		commitCopyHelper(ejbRef());
	}
	/**
	 * WorkorderAccessBean
	 * @generated
	 */
	public WorkorderAccessBean(com.ardais.bigr.iltds.beans.WorkorderKey key)
		throws
			javax.naming.NamingException,
			javax.ejb.FinderException,
			javax.ejb.CreateException,
			java.rmi.RemoteException {
		ejbRef = ejbHome().findByPrimaryKey(key);
	}
	/**
	 * WorkorderAccessBean
	 * @generated
	 */
	public WorkorderAccessBean(java.lang.String argWorkorderid)
		throws
			javax.naming.NamingException,
			javax.ejb.CreateException,
			java.rmi.RemoteException {
		ejbRef = ejbHome().create(argWorkorderid);
	}
	/**
	 * findWorkorderByProject
	 * @generated
	 */
	public java.util.Enumeration findWorkorderByProject(
		com.ardais.bigr.iltds.beans.ProjectKey inKey)
		throws
			javax.naming.NamingException,
			javax.ejb.FinderException,
			java.rmi.RemoteException {
		com.ardais.bigr.iltds.beans.WorkorderHome localHome = ejbHome();
		java.util.Enumeration ejbs = localHome.findWorkorderByProject(inKey);
		return (java.util.Enumeration) createAccessBeans(ejbs);
	}
	/**
	 * setProject
	 * @generated
	 */
	public void setProject(com.ardais.bigr.iltds.beans.Project aProject)
		throws
			javax.naming.NamingException,
			javax.ejb.CreateException,
			java.rmi.RemoteException {
		instantiateEJB();
		ejbRef().setProject(aProject);
	}
	/**
	 * getProject
	 * @generated
	 */
	public com.ardais.bigr.iltds.beans.ProjectAccessBean getProject()
		throws
			javax.naming.NamingException,
			javax.ejb.FinderException,
			javax.ejb.CreateException,
			java.rmi.RemoteException {
		instantiateEJB();
		com.ardais.bigr.iltds.beans.Project localEJBRef = ejbRef().getProject();
		if (localEJBRef != null)
			return new com.ardais.bigr.iltds.beans.ProjectAccessBean(
				localEJBRef);
		else
			return null;
	}
	/**
	 * secondarySetProject
	 * @generated
	 */
	public void secondarySetProject(
		com.ardais.bigr.iltds.beans.Project aProject)
		throws
			javax.naming.NamingException,
			javax.ejb.CreateException,
			java.rmi.RemoteException {
		instantiateEJB();
		ejbRef().secondarySetProject(aProject);
	}
	/**
	 * privateSetProjectKey
	 * @generated
	 */
	public void privateSetProjectKey(
		com.ardais.bigr.iltds.beans.ProjectKey inKey)
		throws
			javax.naming.NamingException,
			javax.ejb.CreateException,
			java.rmi.RemoteException {
		instantiateEJB();
		ejbRef().privateSetProjectKey(inKey);
	}
}
