package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.container.*;

/**
 * EJSRemoteStatelessBtxHistoryExceptionRecorder_fd9b6294
 */
public class EJSRemoteStatelessBtxHistoryExceptionRecorder_fd9b6294 extends EJSWrapper implements BtxHistoryExceptionRecorder {
	/**
	 * EJSRemoteStatelessBtxHistoryExceptionRecorder_fd9b6294
	 */
	public EJSRemoteStatelessBtxHistoryExceptionRecorder_fd9b6294() throws java.rmi.RemoteException {
		super();	}
	/**
	 * record
	 */
	public com.ardais.bigr.iltds.btx.BTXDetails record(com.ardais.bigr.iltds.btx.BTXDetails btxDetails) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.iltds.btx.BTXDetails _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = btxDetails;
			}
	com.ardais.bigr.iltds.beans.BtxHistoryExceptionRecorderBean beanRef = (com.ardais.bigr.iltds.beans.BtxHistoryExceptionRecorderBean)container.preInvoke(this, 0, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.record(btxDetails);
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
