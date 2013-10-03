package com.ardais.bigr.pdc.oce;

import com.ibm.ejs.container.*;

/**
 * EJSStatelessOceHomeBean_1c4c8256
 */
public class EJSStatelessOceHomeBean_1c4c8256 extends EJSHome {
	/**
	 * EJSStatelessOceHomeBean_1c4c8256
	 */
	public EJSStatelessOceHomeBean_1c4c8256() throws java.rmi.RemoteException {
		super();	}
	/**
	 * create
	 */
	public com.ardais.bigr.pdc.oce.Oce create() throws javax.ejb.CreateException, java.rmi.RemoteException {
BeanO beanO = null;
com.ardais.bigr.pdc.oce.Oce _EJS_result = null;
boolean createFailed = false;
try {
	_EJS_result = (com.ardais.bigr.pdc.oce.Oce) super.createWrapper(new BeanId(this, null));
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
