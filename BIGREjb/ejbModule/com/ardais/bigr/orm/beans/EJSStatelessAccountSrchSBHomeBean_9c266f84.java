package com.ardais.bigr.orm.beans;

import com.ibm.ejs.container.*;

/**
 * EJSStatelessAccountSrchSBHomeBean_9c266f84
 */
public class EJSStatelessAccountSrchSBHomeBean_9c266f84 extends EJSHome {
	/**
	 * EJSStatelessAccountSrchSBHomeBean_9c266f84
	 */
	public EJSStatelessAccountSrchSBHomeBean_9c266f84() throws java.rmi.RemoteException {
		super();	}
	/**
	 * create
	 */
	public com.ardais.bigr.orm.beans.AccountSrchSB create() throws javax.ejb.CreateException, java.rmi.RemoteException {
BeanO beanO = null;
com.ardais.bigr.orm.beans.AccountSrchSB _EJS_result = null;
boolean createFailed = false;
try {
	_EJS_result = (com.ardais.bigr.orm.beans.AccountSrchSB) super.createWrapper(new BeanId(this, null));
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
