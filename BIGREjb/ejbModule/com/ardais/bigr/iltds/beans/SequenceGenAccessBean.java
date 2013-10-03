package com.ardais.bigr.iltds.beans;
import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBObject;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import com.ardais.bigr.api.ApiException;
import com.ibm.ivj.ejb.runtime.AbstractSessionAccessBean;
import javax.ejb.*;
import javax.rmi.*;
import com.ibm.ivj.ejb.runtime.*;
/**
 * SequenceGenAccessBean
 * @generated
 */
public class SequenceGenAccessBean extends AbstractSessionAccessBean {
	/**
	 * @generated
	 */
	private SequenceGen __ejbRef;
	/**
	 * SequenceGenAccessBean
	 * @generated
	 */
	public SequenceGenAccessBean() {
		super();
	}
	/**
	 * SequenceGenAccessBean
	 * @generated
	 */
	public SequenceGenAccessBean(javax.ejb.EJBObject o)
		throws java.rmi.RemoteException {
		super(o);
	}
	/**
	 * defaultJNDIName
	 * @generated
	 */
	public String defaultJNDIName() {
		return "com/ardais/bigr/iltds/beans/Sequence";
	}
	/**
	 * ejbHome
	 * @generated
	 */
	private com.ardais.bigr.iltds.beans.SequenceGenHome ejbHome()
		throws java.rmi.RemoteException, javax.naming.NamingException {
		return (
			com
				.ardais
				.bigr
				.iltds
				.beans
				.SequenceGenHome) PortableRemoteObject
				.narrow(
			getHome(),
			com.ardais.bigr.iltds.beans.SequenceGenHome.class);
	}
	/**
	 * ejbRef
	 * @generated
	 */
	private com.ardais.bigr.iltds.beans.SequenceGen ejbRef()
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
						.SequenceGen) PortableRemoteObject
						.narrow(
					ejbRef,
					com.ardais.bigr.iltds.beans.SequenceGen.class);

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

		ejbRef = ejbHome().create();
	}
	/**
	 * getSeqNextVal
	 * @generated
	 */
	public java.lang.String getSeqNextVal(java.lang.String seqName)
		throws
			javax.naming.NamingException,
			com.ardais.bigr.api.ApiException,
			javax.ejb.CreateException,
			java.rmi.RemoteException {
		instantiateEJB();
		return ejbRef().getSeqNextVal(seqName);
	}
}