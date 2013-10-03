package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.container.*;

/**
 * EJSStatelessConsentOperationHomeBean_46ef92f9
 */
public class EJSStatelessConsentOperationHomeBean_46ef92f9 extends EJSHome {
	/**
	 * EJSStatelessConsentOperationHomeBean_46ef92f9
	 */
	public EJSStatelessConsentOperationHomeBean_46ef92f9() throws java.rmi.RemoteException {
		super();	}
	/**
	 * create
	 */
	public com.ardais.bigr.iltds.beans.ConsentOperation create() throws javax.ejb.CreateException, java.rmi.RemoteException {
BeanO beanO = null;
com.ardais.bigr.iltds.beans.ConsentOperation _EJS_result = null;
boolean createFailed = false;
try {
	_EJS_result = (com.ardais.bigr.iltds.beans.ConsentOperation) super.createWrapper(new BeanId(this, null));
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
