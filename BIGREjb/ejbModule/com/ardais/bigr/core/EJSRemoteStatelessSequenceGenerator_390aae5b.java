package com.ardais.bigr.core;

import com.ibm.ejs.container.*;

/**
 * EJSRemoteStatelessSequenceGenerator_390aae5b
 */
public class EJSRemoteStatelessSequenceGenerator_390aae5b extends EJSWrapper implements SequenceGenerator {
	/**
	 * EJSRemoteStatelessSequenceGenerator_390aae5b
	 */
	public EJSRemoteStatelessSequenceGenerator_390aae5b() throws java.rmi.RemoteException {
		super();	}
	/**
	 * getSeqNextVal
	 */
	public java.lang.Integer getSeqNextVal(java.lang.String seqName) throws java.rmi.RemoteException, com.ardais.bigr.api.ApiException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.lang.Integer _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = seqName;
			}
	com.ardais.bigr.core.SequenceGeneratorBean beanRef = (com.ardais.bigr.core.SequenceGeneratorBean)container.preInvoke(this, 0, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getSeqNextVal(seqName);
		}
		catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedException(ex);
		}
		catch (com.ardais.bigr.api.ApiException ex) {
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
