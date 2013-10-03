package com.ardais.bigr.lims.beans;

import com.ibm.ejs.container.*;

/**
 * EJSStatelessLimsOperationHomeBean_01af3214
 */
public class EJSStatelessLimsOperationHomeBean_01af3214 extends EJSHome {
	/**
	 * EJSStatelessLimsOperationHomeBean_01af3214
	 */
	public EJSStatelessLimsOperationHomeBean_01af3214() throws java.rmi.RemoteException {
		super();	}
	/**
	 * create
	 */
	public com.ardais.bigr.lims.beans.LimsOperation create() throws javax.ejb.CreateException, java.rmi.RemoteException {
BeanO beanO = null;
com.ardais.bigr.lims.beans.LimsOperation _EJS_result = null;
boolean createFailed = false;
try {
	_EJS_result = (com.ardais.bigr.lims.beans.LimsOperation) super.createWrapper(new BeanId(this, null));
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
