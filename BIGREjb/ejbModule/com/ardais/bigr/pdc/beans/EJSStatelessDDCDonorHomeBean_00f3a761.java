package com.ardais.bigr.pdc.beans;

import com.ibm.ejs.container.*;

/**
 * EJSStatelessDDCDonorHomeBean_00f3a761
 */
public class EJSStatelessDDCDonorHomeBean_00f3a761 extends EJSHome {
	/**
	 * EJSStatelessDDCDonorHomeBean_00f3a761
	 */
	public EJSStatelessDDCDonorHomeBean_00f3a761() throws java.rmi.RemoteException {
		super();	}
	/**
	 * create
	 */
	public com.ardais.bigr.pdc.beans.DDCDonor create() throws javax.ejb.CreateException, java.rmi.RemoteException {
BeanO beanO = null;
com.ardais.bigr.pdc.beans.DDCDonor _EJS_result = null;
boolean createFailed = false;
try {
	_EJS_result = (com.ardais.bigr.pdc.beans.DDCDonor) super.createWrapper(new BeanId(this, null));
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
