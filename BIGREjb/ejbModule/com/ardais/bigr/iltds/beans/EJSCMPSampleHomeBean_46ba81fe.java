package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.container.*;

/**
 * EJSCMPSampleHomeBean_46ba81fe
 */
public class EJSCMPSampleHomeBean_46ba81fe extends EJSHome {
	/**
	 * EJSCMPSampleHomeBean_46ba81fe
	 */
	public EJSCMPSampleHomeBean_46ba81fe() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.iltds.beans.Sample postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.iltds.beans.Sample) super.postCreate(beanO, ejsKey, true);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		super.afterPostCreate(beanO, ejsKey);
	}
	/**
	 * findSampleByAsm
	 */
	public java.util.Enumeration findSampleByAsm(com.ardais.bigr.iltds.beans.AsmKey inKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.iltds.beans.EJSFinderSampleBean)persister).findSampleByAsm(inKey));	}
	/**
	 * create
	 */
	public com.ardais.bigr.iltds.beans.Sample create(java.lang.String sample_barcode_id, java.lang.String importedYN, java.lang.String sampleTypeCid) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.iltds.beans.Sample _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.iltds.beans.SampleBean bean = (com.ardais.bigr.iltds.beans.SampleBean) beanO.getEnterpriseBean();
			bean.ejbCreate(sample_barcode_id, importedYN, sampleTypeCid);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(sample_barcode_id, importedYN, sampleTypeCid);
			afterPostCreateWrapper(beanO, _primaryKey);
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
		return _EJS_result;
	}
	/**
	 * findAll
	 */
	public java.util.Enumeration findAll() throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.iltds.beans.EJSFinderSampleBean)persister).findAll());	}
	/**
	 * findSampleBySamplebox
	 */
	public java.util.Enumeration findSampleBySamplebox(com.ardais.bigr.iltds.beans.SampleboxKey inKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.iltds.beans.EJSFinderSampleBean)persister).findSampleBySamplebox(inKey));	}
	/**
	 * findByPrimaryKey
	 */
	public com.ardais.bigr.iltds.beans.Sample findByPrimaryKey(com.ardais.bigr.iltds.beans.SampleKey key) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return ((com.ardais.bigr.iltds.beans.EJSJDBCPersisterCMPSampleBean_46ba81fe) persister).findByPrimaryKey(key);
	}
	/**
	 * keyFromBean
	 */
	public Object keyFromBean(javax.ejb.EntityBean generalEJB) {
		com.ardais.bigr.iltds.beans.SampleBean tmpEJB = (com.ardais.bigr.iltds.beans.SampleBean) generalEJB;
		com.ardais.bigr.iltds.beans.SampleKey keyClass = new com.ardais.bigr.iltds.beans.SampleKey();
		keyClass.sample_barcode_id = tmpEJB.sample_barcode_id;
		return keyClass;
	}
	/**
	 * keyFromFields
	 */
	public com.ardais.bigr.iltds.beans.SampleKey keyFromFields(java.lang.String f0) {
		com.ardais.bigr.iltds.beans.SampleKey keyClass = new com.ardais.bigr.iltds.beans.SampleKey();
		keyClass.sample_barcode_id = f0;
		return keyClass;
	}
}
