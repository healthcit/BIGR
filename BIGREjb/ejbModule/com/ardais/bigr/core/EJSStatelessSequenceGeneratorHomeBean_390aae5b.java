package com.ardais.bigr.core;

import com.ibm.ejs.container.*;

/**
 * EJSStatelessSequenceGeneratorHomeBean_390aae5b
 */
public class EJSStatelessSequenceGeneratorHomeBean_390aae5b extends EJSHome {
	/**
	 * EJSStatelessSequenceGeneratorHomeBean_390aae5b
	 */
	public EJSStatelessSequenceGeneratorHomeBean_390aae5b() throws java.rmi.RemoteException {
		super();	}
	/**
	 * create
	 */
	public com.ardais.bigr.core.SequenceGenerator create() throws javax.ejb.CreateException, java.rmi.RemoteException {
BeanO beanO = null;
com.ardais.bigr.core.SequenceGenerator _EJS_result = null;
boolean createFailed = false;
try {
	_EJS_result = (com.ardais.bigr.core.SequenceGenerator) super.createWrapper(new BeanId(this, null));
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
