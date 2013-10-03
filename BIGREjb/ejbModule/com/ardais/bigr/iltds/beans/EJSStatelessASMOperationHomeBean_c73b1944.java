package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.container.*;

/**
 * EJSStatelessASMOperationHomeBean_c73b1944
 */
public class EJSStatelessASMOperationHomeBean_c73b1944 extends EJSHome {
	/**
	 * EJSStatelessASMOperationHomeBean_c73b1944
	 */
	public EJSStatelessASMOperationHomeBean_c73b1944() throws java.rmi.RemoteException {
		super();	}
	/**
	 * create
	 */
	public com.ardais.bigr.iltds.beans.ASMOperation create() throws javax.ejb.CreateException, java.rmi.RemoteException {
BeanO beanO = null;
com.ardais.bigr.iltds.beans.ASMOperation _EJS_result = null;
boolean createFailed = false;
try {
	_EJS_result = (com.ardais.bigr.iltds.beans.ASMOperation) super.createWrapper(new BeanId(this, null));
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
