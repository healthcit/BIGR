package com.ardais.bigr.pdc.beans;

import com.ibm.ejs.container.*;

/**
 * EJSStatelessDDCPathologyHomeBean_12501321
 */
public class EJSStatelessDDCPathologyHomeBean_12501321 extends EJSHome {
	/**
	 * EJSStatelessDDCPathologyHomeBean_12501321
	 */
	public EJSStatelessDDCPathologyHomeBean_12501321() throws java.rmi.RemoteException {
		super();	}
	/**
	 * create
	 */
	public com.ardais.bigr.pdc.beans.DDCPathology create() throws javax.ejb.CreateException, java.rmi.RemoteException {
BeanO beanO = null;
com.ardais.bigr.pdc.beans.DDCPathology _EJS_result = null;
boolean createFailed = false;
try {
	_EJS_result = (com.ardais.bigr.pdc.beans.DDCPathology) super.createWrapper(new BeanId(this, null));
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
