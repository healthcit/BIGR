package com.ardais.bigr.btx.framework;

import com.ibm.ejs.container.*;

/**
 * EJSRemoteStatelessBtxTransaction_af322ae2
 */
public class EJSRemoteStatelessBtxTransaction_af322ae2 extends EJSWrapper implements BtxTransaction {
	/**
	 * EJSRemoteStatelessBtxTransaction_af322ae2
	 */
	public EJSRemoteStatelessBtxTransaction_af322ae2() throws java.rmi.RemoteException {
		super();	}
	/**
	 * perform
	 */
	public com.ardais.bigr.iltds.btx.BTXDetails perform(com.ardais.bigr.iltds.btx.BTXDetails btxDetails) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.iltds.btx.BTXDetails _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = btxDetails;
			}
	com.ardais.bigr.btx.framework.BtxTransactionBean beanRef = (com.ardais.bigr.btx.framework.BtxTransactionBean)container.preInvoke(this, 0, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.perform(btxDetails);
		}
		catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedException(ex);
		}
		catch (Throwable ex) {
			_EJS_s.setUncheckedException(ex);
			throw new java.rmi.RemoteException("bean method raised unchecked exception", ex);
		}

		finally {
			try{
				container.postInvoke(this, 0, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
}
