package com.ardais.bigr.userprofile;

import com.ibm.ejs.container.*;

/**
 * EJSStatelessUserProfHomeBean_4714acdf
 */
public class EJSStatelessUserProfHomeBean_4714acdf extends EJSHome {
	/**
	 * EJSStatelessUserProfHomeBean_4714acdf
	 */
	public EJSStatelessUserProfHomeBean_4714acdf() throws java.rmi.RemoteException {
		super();	}
	/**
	 * create
	 */
	public com.ardais.bigr.userprofile.UserProf create() throws javax.ejb.CreateException, java.rmi.RemoteException {
BeanO beanO = null;
com.ardais.bigr.userprofile.UserProf _EJS_result = null;
boolean createFailed = false;
try {
	_EJS_result = (com.ardais.bigr.userprofile.UserProf) super.createWrapper(new BeanId(this, null));
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
