package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.container.*;

/**
 * EJSRemoteStatelessBTXHistoryReader_8f44ac6a
 */
public class EJSRemoteStatelessBTXHistoryReader_8f44ac6a extends EJSWrapper implements BTXHistoryReader {
	/**
	 * EJSRemoteStatelessBTXHistoryReader_8f44ac6a
	 */
	public EJSRemoteStatelessBTXHistoryReader_8f44ac6a() throws java.rmi.RemoteException {
		super();	}
	/**
	 * getHistoryDisplayLines
	 */
	public java.util.List getHistoryDisplayLines(java.lang.String objectId, com.ardais.bigr.security.SecurityInfo securityInfo) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.List _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[2];
				_jacc_parms[0] = objectId;
				_jacc_parms[1] = securityInfo;
			}
	com.ardais.bigr.iltds.beans.BTXHistoryReaderBean beanRef = (com.ardais.bigr.iltds.beans.BTXHistoryReaderBean)container.preInvoke(this, 0, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getHistoryDisplayLines(objectId, securityInfo);
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
