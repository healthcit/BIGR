package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.container.*;

/**
 * EJSStatelessBTXHistoryRecorderHomeBean_5eb38b2c
 */
public class EJSStatelessBTXHistoryRecorderHomeBean_5eb38b2c extends EJSHome {
	/**
	 * EJSStatelessBTXHistoryRecorderHomeBean_5eb38b2c
	 */
	public EJSStatelessBTXHistoryRecorderHomeBean_5eb38b2c() throws java.rmi.RemoteException {
		super();	}
	/**
	 * create
	 */
	public com.ardais.bigr.iltds.beans.BTXHistoryRecorder create() throws javax.ejb.CreateException, java.rmi.RemoteException {
BeanO beanO = null;
com.ardais.bigr.iltds.beans.BTXHistoryRecorder _EJS_result = null;
boolean createFailed = false;
try {
	_EJS_result = (com.ardais.bigr.iltds.beans.BTXHistoryRecorder) super.createWrapper(new BeanId(this, null));
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
