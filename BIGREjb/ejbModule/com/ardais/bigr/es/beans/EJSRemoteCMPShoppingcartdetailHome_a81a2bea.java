package com.ardais.bigr.es.beans;

import com.ibm.ejs.container.*;

/**
 * EJSRemoteCMPShoppingcartdetailHome_a81a2bea
 */
public class EJSRemoteCMPShoppingcartdetailHome_a81a2bea extends EJSWrapper implements com.ardais.bigr.es.beans.ShoppingcartdetailHome {
	/**
	 * EJSRemoteCMPShoppingcartdetailHome_a81a2bea
	 */
	public EJSRemoteCMPShoppingcartdetailHome_a81a2bea() throws java.rmi.RemoteException {
		super();	}
	/**
	 * create
	 */
	public com.ardais.bigr.es.beans.Shoppingcartdetail create(java.lang.String barcode_id, java.lang.String productType, java.math.BigDecimal shopping_cart_line_number, java.lang.String userId, java.lang.String acctId, java.math.BigDecimal cartNo) throws javax.ejb.CreateException, java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.es.beans.Shoppingcartdetail _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[6];
				_jacc_parms[0] = barcode_id;
				_jacc_parms[1] = productType;
				_jacc_parms[2] = shopping_cart_line_number;
				_jacc_parms[3] = userId;
				_jacc_parms[4] = acctId;
				_jacc_parms[5] = cartNo;
			}
	com.ardais.bigr.es.beans.EJSCMPShoppingcartdetailHomeBean_a81a2bea beanRef = (com.ardais.bigr.es.beans.EJSCMPShoppingcartdetailHomeBean_a81a2bea)container.preInvoke(this, 0, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.create(barcode_id, productType, shopping_cart_line_number, userId, acctId, cartNo);
		}
		catch (javax.ejb.CreateException ex) {
			_EJS_s.setCheckedException(ex);
			throw ex;
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
	 * create
	 */
	public com.ardais.bigr.es.beans.Shoppingcartdetail create(java.math.BigDecimal shopping_cart_line_number, com.ardais.bigr.es.beans.Shoppingcart argShoppingcart) throws javax.ejb.CreateException, java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.es.beans.Shoppingcartdetail _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[2];
				_jacc_parms[0] = shopping_cart_line_number;
				_jacc_parms[1] = argShoppingcart;
			}
	com.ardais.bigr.es.beans.EJSCMPShoppingcartdetailHomeBean_a81a2bea beanRef = (com.ardais.bigr.es.beans.EJSCMPShoppingcartdetailHomeBean_a81a2bea)container.preInvoke(this, 1, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.create(shopping_cart_line_number, argShoppingcart);
		}
		catch (javax.ejb.CreateException ex) {
			_EJS_s.setCheckedException(ex);
			throw ex;
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
	 * findByPrimaryKey
	 */
	public com.ardais.bigr.es.beans.Shoppingcartdetail findByPrimaryKey(com.ardais.bigr.es.beans.ShoppingcartdetailKey primaryKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.es.beans.Shoppingcartdetail _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = primaryKey;
			}
	com.ardais.bigr.es.beans.EJSCMPShoppingcartdetailHomeBean_a81a2bea beanRef = (com.ardais.bigr.es.beans.EJSCMPShoppingcartdetailHomeBean_a81a2bea)container.preInvoke(this, 2, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.findByPrimaryKey(primaryKey);
		}
		catch (javax.ejb.FinderException ex) {
			_EJS_s.setCheckedException(ex);
			throw ex;
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
	 * findByBarcodeUserAccount
	 */
	public java.util.Enumeration findByBarcodeUserAccount(java.lang.String barcode_arg, java.lang.String user_arg, java.lang.String account_arg) throws java.rmi.RemoteException, javax.ejb.FinderException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.Enumeration _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[3];
				_jacc_parms[0] = barcode_arg;
				_jacc_parms[1] = user_arg;
				_jacc_parms[2] = account_arg;
			}
	com.ardais.bigr.es.beans.EJSCMPShoppingcartdetailHomeBean_a81a2bea beanRef = (com.ardais.bigr.es.beans.EJSCMPShoppingcartdetailHomeBean_a81a2bea)container.preInvoke(this, 3, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.findByBarcodeUserAccount(barcode_arg, user_arg, account_arg);
		}
		catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedException(ex);
		}
		catch (javax.ejb.FinderException ex) {
			_EJS_s.setCheckedException(ex);
			throw ex;
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
	 * findByUserAccountOrderByLineNumber
	 */
	public java.util.Enumeration findByUserAccountOrderByLineNumber(java.lang.String user, java.lang.String account) throws java.rmi.RemoteException, javax.ejb.FinderException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.Enumeration _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[2];
				_jacc_parms[0] = user;
				_jacc_parms[1] = account;
			}
	com.ardais.bigr.es.beans.EJSCMPShoppingcartdetailHomeBean_a81a2bea beanRef = (com.ardais.bigr.es.beans.EJSCMPShoppingcartdetailHomeBean_a81a2bea)container.preInvoke(this, 4, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.findByUserAccountOrderByLineNumber(user, account);
		}
		catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedException(ex);
		}
		catch (javax.ejb.FinderException ex) {
			_EJS_s.setCheckedException(ex);
			throw ex;
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
		return _EJS_result;
	}
	/**
	 * findShoppingcartdetailByShoppingcart
	 */
	public java.util.Enumeration findShoppingcartdetailByShoppingcart(com.ardais.bigr.es.beans.ShoppingcartKey inKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.Enumeration _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = inKey;
			}
	com.ardais.bigr.es.beans.EJSCMPShoppingcartdetailHomeBean_a81a2bea beanRef = (com.ardais.bigr.es.beans.EJSCMPShoppingcartdetailHomeBean_a81a2bea)container.preInvoke(this, 5, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.findShoppingcartdetailByShoppingcart(inKey);
		}
		catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedException(ex);
		}
		catch (javax.ejb.FinderException ex) {
			_EJS_s.setCheckedException(ex);
			throw ex;
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
		return _EJS_result;
	}
	/**
	 * getEJBMetaData
	 */
	public javax.ejb.EJBMetaData getEJBMetaData() throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		javax.ejb.EJBMetaData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[0];
			}
	com.ardais.bigr.es.beans.EJSCMPShoppingcartdetailHomeBean_a81a2bea beanRef = (com.ardais.bigr.es.beans.EJSCMPShoppingcartdetailHomeBean_a81a2bea)container.preInvoke(this, 6, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getEJBMetaData();
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
				container.postInvoke(this, 6, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * getHomeHandle
	 */
	public javax.ejb.HomeHandle getHomeHandle() throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		javax.ejb.HomeHandle _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[0];
			}
	com.ardais.bigr.es.beans.EJSCMPShoppingcartdetailHomeBean_a81a2bea beanRef = (com.ardais.bigr.es.beans.EJSCMPShoppingcartdetailHomeBean_a81a2bea)container.preInvoke(this, 7, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getHomeHandle();
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
				container.postInvoke(this, 7, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * remove
	 */
	public void remove(java.lang.Object arg0) throws java.rmi.RemoteException, javax.ejb.RemoveException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = arg0;
			}
	com.ardais.bigr.es.beans.EJSCMPShoppingcartdetailHomeBean_a81a2bea beanRef = (com.ardais.bigr.es.beans.EJSCMPShoppingcartdetailHomeBean_a81a2bea)container.preInvoke(this, 8, _EJS_s, _jacc_parms);
			beanRef.remove(arg0);
		}
		catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedException(ex);
		}
		catch (javax.ejb.RemoveException ex) {
			_EJS_s.setCheckedException(ex);
			throw ex;
		}
		catch (Throwable ex) {
			_EJS_s.setUncheckedException(ex);
			throw new java.rmi.RemoteException("bean method raised unchecked exception", ex);
		}

		finally {
			try{
				container.postInvoke(this, 8, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return ;
	}
	/**
	 * remove
	 */
	public void remove(javax.ejb.Handle arg0) throws java.rmi.RemoteException, javax.ejb.RemoveException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = arg0;
			}
	com.ardais.bigr.es.beans.EJSCMPShoppingcartdetailHomeBean_a81a2bea beanRef = (com.ardais.bigr.es.beans.EJSCMPShoppingcartdetailHomeBean_a81a2bea)container.preInvoke(this, 9, _EJS_s, _jacc_parms);
			beanRef.remove(arg0);
		}
		catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedException(ex);
		}
		catch (javax.ejb.RemoveException ex) {
			_EJS_s.setCheckedException(ex);
			throw ex;
		}
		catch (Throwable ex) {
			_EJS_s.setUncheckedException(ex);
			throw new java.rmi.RemoteException("bean method raised unchecked exception", ex);
		}

		finally {
			try{
				container.postInvoke(this, 9, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return ;
	}
}
