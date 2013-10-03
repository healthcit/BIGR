package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.container.*;

/**
 * EJSStatelessPtsOperationHomeBean_52dcd328
 */
public class EJSStatelessPtsOperationHomeBean_52dcd328 extends EJSHome {
	/**
	 * EJSStatelessPtsOperationHomeBean_52dcd328
	 */
	public EJSStatelessPtsOperationHomeBean_52dcd328() throws java.rmi.RemoteException {
		super();	}
	/**
	 * create
	 */
	public com.ardais.bigr.iltds.beans.PtsOperation create() throws javax.ejb.CreateException, java.rmi.RemoteException {
BeanO beanO = null;
com.ardais.bigr.iltds.beans.PtsOperation _EJS_result = null;
boolean createFailed = false;
try {
	_EJS_result = (com.ardais.bigr.iltds.beans.PtsOperation) super.createWrapper(new BeanId(this, null));
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
