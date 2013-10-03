package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.container.*;

/**
 * EJSStatelessBtxHistoryExceptionRecorderHomeBean_fd9b6294
 */
public class EJSStatelessBtxHistoryExceptionRecorderHomeBean_fd9b6294 extends EJSHome {
	/**
	 * EJSStatelessBtxHistoryExceptionRecorderHomeBean_fd9b6294
	 */
	public EJSStatelessBtxHistoryExceptionRecorderHomeBean_fd9b6294() throws java.rmi.RemoteException {
		super();	}
	/**
	 * create
	 */
	public com.ardais.bigr.iltds.beans.BtxHistoryExceptionRecorder create() throws javax.ejb.CreateException, java.rmi.RemoteException {
BeanO beanO = null;
com.ardais.bigr.iltds.beans.BtxHistoryExceptionRecorder _EJS_result = null;
boolean createFailed = false;
try {
	_EJS_result = (com.ardais.bigr.iltds.beans.BtxHistoryExceptionRecorder) super.createWrapper(new BeanId(this, null));
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
