package com.ardais.bigr.lims.beans;

import com.ibm.ejs.container.*;

/**
 * EJSStatelessLimsDataValidatorHomeBean_586fcd57
 */
public class EJSStatelessLimsDataValidatorHomeBean_586fcd57 extends EJSHome {
	/**
	 * EJSStatelessLimsDataValidatorHomeBean_586fcd57
	 */
	public EJSStatelessLimsDataValidatorHomeBean_586fcd57() throws java.rmi.RemoteException {
		super();	}
	/**
	 * create
	 */
	public com.ardais.bigr.lims.beans.LimsDataValidator create() throws javax.ejb.CreateException, java.rmi.RemoteException {
BeanO beanO = null;
com.ardais.bigr.lims.beans.LimsDataValidator _EJS_result = null;
boolean createFailed = false;
try {
	_EJS_result = (com.ardais.bigr.lims.beans.LimsDataValidator) super.createWrapper(new BeanId(this, null));
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
