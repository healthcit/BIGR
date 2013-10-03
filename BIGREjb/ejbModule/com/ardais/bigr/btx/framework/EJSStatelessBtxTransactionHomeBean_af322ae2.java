package com.ardais.bigr.btx.framework;

import com.ibm.ejs.container.*;

/**
 * EJSStatelessBtxTransactionHomeBean_af322ae2
 */
public class EJSStatelessBtxTransactionHomeBean_af322ae2 extends EJSHome {
	/**
	 * EJSStatelessBtxTransactionHomeBean_af322ae2
	 */
	public EJSStatelessBtxTransactionHomeBean_af322ae2() throws java.rmi.RemoteException {
		super();	}
	/**
	 * create
	 */
	public com.ardais.bigr.btx.framework.BtxTransaction create() throws javax.ejb.CreateException, java.rmi.RemoteException {
BeanO beanO = null;
com.ardais.bigr.btx.framework.BtxTransaction _EJS_result = null;
boolean createFailed = false;
try {
	_EJS_result = (com.ardais.bigr.btx.framework.BtxTransaction) super.createWrapper(new BeanId(this, null));
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
