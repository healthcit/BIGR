package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.container.*;

/**
 * EJSStatelessSequenceGenHomeBean_d6adb3ab
 */
public class EJSStatelessSequenceGenHomeBean_d6adb3ab extends EJSHome {
	/**
	 * EJSStatelessSequenceGenHomeBean_d6adb3ab
	 */
	public EJSStatelessSequenceGenHomeBean_d6adb3ab() throws java.rmi.RemoteException {
		super();	}
	/**
	 * create
	 */
	public com.ardais.bigr.iltds.beans.SequenceGen create() throws javax.ejb.CreateException, java.rmi.RemoteException {
BeanO beanO = null;
com.ardais.bigr.iltds.beans.SequenceGen _EJS_result = null;
boolean createFailed = false;
try {
	_EJS_result = (com.ardais.bigr.iltds.beans.SequenceGen) super.createWrapper(new BeanId(this, null));
}
catch (javax.ejb.CreateException ex) {
	createFailed = true;
	throw ex;
} catch (java.rmi.RemoteException ex) {
	createFailed = true;
	throw ex;
} catch (Throwable ex) {
	createFailed = true;
	throw new CreateFailureException(ex);
} finally {
	if (createFailed) {
		super.createFailure(beanO);
	}
}
return _EJS_result;	}
}
