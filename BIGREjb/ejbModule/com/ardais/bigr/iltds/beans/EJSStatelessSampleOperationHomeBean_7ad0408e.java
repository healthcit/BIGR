package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.container.*;

/**
 * EJSStatelessSampleOperationHomeBean_7ad0408e
 */
public class EJSStatelessSampleOperationHomeBean_7ad0408e extends EJSHome {
	/**
	 * EJSStatelessSampleOperationHomeBean_7ad0408e
	 */
	public EJSStatelessSampleOperationHomeBean_7ad0408e() throws java.rmi.RemoteException {
		super();	}
	/**
	 * create
	 */
	public com.ardais.bigr.iltds.beans.SampleOperation create() throws javax.ejb.CreateException, java.rmi.RemoteException {
BeanO beanO = null;
com.ardais.bigr.iltds.beans.SampleOperation _EJS_result = null;
boolean createFailed = false;
try {
	_EJS_result = (com.ardais.bigr.iltds.beans.SampleOperation) super.createWrapper(new BeanId(this, null));
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
