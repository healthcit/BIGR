package com.ardais.bigr.iltds.icp.ejb.session;

import com.ibm.ejs.container.*;

/**
 * EJSStatelessIcpOperationHomeBean_ad5b9602
 */
public class EJSStatelessIcpOperationHomeBean_ad5b9602 extends EJSHome {
	/**
	 * EJSStatelessIcpOperationHomeBean_ad5b9602
	 */
	public EJSStatelessIcpOperationHomeBean_ad5b9602() throws java.rmi.RemoteException {
		super();	}
	/**
	 * create
	 */
	public com.ardais.bigr.iltds.icp.ejb.session.IcpOperation create() throws javax.ejb.CreateException, java.rmi.RemoteException {
BeanO beanO = null;
com.ardais.bigr.iltds.icp.ejb.session.IcpOperation _EJS_result = null;
boolean createFailed = false;
try {
	_EJS_result = (com.ardais.bigr.iltds.icp.ejb.session.IcpOperation) super.createWrapper(new BeanId(this, null));
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