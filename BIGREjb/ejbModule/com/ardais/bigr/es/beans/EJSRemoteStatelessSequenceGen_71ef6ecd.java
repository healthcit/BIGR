package com.ardais.bigr.es.beans;

import com.ibm.ejs.container.*;

/**
 * EJSRemoteStatelessSequenceGen_71ef6ecd
 */
public class EJSRemoteStatelessSequenceGen_71ef6ecd extends EJSWrapper implements SequenceGen {
	/**
	 * EJSRemoteStatelessSequenceGen_71ef6ecd
	 */
	public EJSRemoteStatelessSequenceGen_71ef6ecd() throws java.rmi.RemoteException {
		super();	}
	/**
	 * getSeqNextVal
	 */
	public int getSeqNextVal(java.lang.String seqName) throws java.rmi.RemoteException, com.ardais.bigr.api.ApiException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		int _EJS_result = 0;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = seqName;
			}
	com.ardais.bigr.es.beans.SequenceGenBean beanRef = (com.ardais.bigr.es.beans.SequenceGenBean)container.preInvoke(this, 0, _EJS_s, _jacc_parms);
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
