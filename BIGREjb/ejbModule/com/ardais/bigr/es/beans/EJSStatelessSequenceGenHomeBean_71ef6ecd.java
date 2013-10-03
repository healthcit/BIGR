package com.ardais.bigr.es.beans;

import com.ibm.ejs.container.*;

/**
 * EJSStatelessSequenceGenHomeBean_71ef6ecd
 */
public class EJSStatelessSequenceGenHomeBean_71ef6ecd extends EJSHome {
	/**
	 * EJSStatelessSequenceGenHomeBean_71ef6ecd
	 */
	public EJSStatelessSequenceGenHomeBean_71ef6ecd() throws java.rmi.RemoteException {
		super();	}
	/**
	 * create
	 */
	public com.ardais.bigr.es.beans.SequenceGen create() throws javax.ejb.CreateException, java.rmi.RemoteException {
BeanO beanO = null;
com.ardais.bigr.es.beans.SequenceGen _EJS_result = null;
boolean createFailed = false;
try {
	_EJS_result = (com.ardais.bigr.es.beans.SequenceGen) super.createWrapper(new BeanId(this, null));
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
