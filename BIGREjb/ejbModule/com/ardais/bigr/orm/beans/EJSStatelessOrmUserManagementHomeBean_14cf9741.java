package com.ardais.bigr.orm.beans;

import com.ibm.ejs.container.*;

/**
 * EJSStatelessOrmUserManagementHomeBean_14cf9741
 */
public class EJSStatelessOrmUserManagementHomeBean_14cf9741 extends EJSHome {
	/**
	 * EJSStatelessOrmUserManagementHomeBean_14cf9741
	 */
	public EJSStatelessOrmUserManagementHomeBean_14cf9741() throws java.rmi.RemoteException {
		super();	}
	/**
	 * create
	 */
	public com.ardais.bigr.orm.beans.OrmUserManagement create() throws javax.ejb.CreateException, java.rmi.RemoteException {
BeanO beanO = null;
com.ardais.bigr.orm.beans.OrmUserManagement _EJS_result = null;
boolean createFailed = false;
try {
	_EJS_result = (com.ardais.bigr.orm.beans.OrmUserManagement) super.createWrapper(new BeanId(this, null));
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
