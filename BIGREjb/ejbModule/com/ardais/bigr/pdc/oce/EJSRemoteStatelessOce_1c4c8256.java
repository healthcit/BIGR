package com.ardais.bigr.pdc.oce;

import com.ibm.ejs.container.*;

/**
 * EJSRemoteStatelessOce_1c4c8256
 */
public class EJSRemoteStatelessOce_1c4c8256 extends EJSWrapper implements Oce {
	/**
	 * EJSRemoteStatelessOce_1c4c8256
	 */
	public EJSRemoteStatelessOce_1c4c8256() throws java.rmi.RemoteException {
		super();	}
	/**
	 * getOceData
	 */
	public com.ardais.bigr.pdc.javabeans.OceData getOceData(com.ardais.bigr.pdc.javabeans.OceData databean) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.pdc.javabeans.OceData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = databean;
			}
	com.ardais.bigr.pdc.oce.OceBean beanRef = (com.ardais.bigr.pdc.oce.OceBean)container.preInvoke(this, 0, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getOceData(databean);
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
	/**
	 * updateOce
	 */
	public com.ardais.bigr.pdc.javabeans.OceData updateOce(com.ardais.bigr.pdc.javabeans.OceData databean) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.pdc.javabeans.OceData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = databean;
			}
	com.ardais.bigr.pdc.oce.OceBean beanRef = (com.ardais.bigr.pdc.oce.OceBean)container.preInvoke(this, 1, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.updateOce(databean);
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
				container.postInvoke(this, 1, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * getTableColumnNames
	 */
	public java.util.Map getTableColumnNames() throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.Map _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[0];
			}
	com.ardais.bigr.pdc.oce.OceBean beanRef = (com.ardais.bigr.pdc.oce.OceBean)container.preInvoke(this, 2, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getTableColumnNames();
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
				container.postInvoke(this, 2, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * retrieveCaseInfo
	 */
	public java.util.Map retrieveCaseInfo(java.lang.String whereClause) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.Map _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = whereClause;
			}
	com.ardais.bigr.pdc.oce.OceBean beanRef = (com.ardais.bigr.pdc.oce.OceBean)container.preInvoke(this, 3, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.retrieveCaseInfo(whereClause);
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
				container.postInvoke(this, 3, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * createOce
	 */
	public void createOce(java.lang.String tableName, java.lang.String columnName, java.lang.String typeCode, java.lang.String whereClause, java.lang.String user, java.lang.String otherText, java.lang.String othertext_Column_Name) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[7];
				_jacc_parms[0] = tableName;
				_jacc_parms[1] = columnName;
				_jacc_parms[2] = typeCode;
				_jacc_parms[3] = whereClause;
				_jacc_parms[4] = user;
				_jacc_parms[5] = otherText;
				_jacc_parms[6] = othertext_Column_Name;
			}
	com.ardais.bigr.pdc.oce.OceBean beanRef = (com.ardais.bigr.pdc.oce.OceBean)container.preInvoke(this, 4, _EJS_s, _jacc_parms);
			beanRef.createOce(tableName, columnName, typeCode, whereClause, user, otherText, othertext_Column_Name);
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
				container.postInvoke(this, 4, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return ;
	}
	/**
	 * updateStatus
	 */
	public void updateStatus(java.lang.String tableName, java.lang.String columnName, java.lang.String typeCode, java.lang.String primaryKey, java.lang.String user, java.lang.String statusFlag) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[6];
				_jacc_parms[0] = tableName;
				_jacc_parms[1] = columnName;
				_jacc_parms[2] = typeCode;
				_jacc_parms[3] = primaryKey;
				_jacc_parms[4] = user;
				_jacc_parms[5] = statusFlag;
			}
	com.ardais.bigr.pdc.oce.OceBean beanRef = (com.ardais.bigr.pdc.oce.OceBean)container.preInvoke(this, 5, _EJS_s, _jacc_parms);
			beanRef.updateStatus(tableName, columnName, typeCode, primaryKey, user, statusFlag);
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
				container.postInvoke(this, 5, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return ;
	}
}
