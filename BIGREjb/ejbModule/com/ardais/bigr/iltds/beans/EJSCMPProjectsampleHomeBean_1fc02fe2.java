package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.container.*;

/**
 * EJSCMPProjectsampleHomeBean_1fc02fe2
 */
public class EJSCMPProjectsampleHomeBean_1fc02fe2 extends EJSHome {
	/**
	 * EJSCMPProjectsampleHomeBean_1fc02fe2
	 */
	public EJSCMPProjectsampleHomeBean_1fc02fe2() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.iltds.beans.Projectsample postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.iltds.beans.Projectsample) super.postCreate(beanO, ejsKey, true);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		super.afterPostCreate(beanO, ejsKey);
	}
	/**
	 * create
	 */
	public com.ardais.bigr.iltds.beans.Projectsample create(java.lang.String argSamplebarcodeid, com.ardais.bigr.iltds.beans.LineitemKey argLineitem, com.ardais.bigr.iltds.beans.ProjectKey argProject) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.iltds.beans.Projectsample _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.iltds.beans.ProjectsampleBean bean = (com.ardais.bigr.iltds.beans.ProjectsampleBean) beanO.getEnterpriseBean();
			bean.ejbCreate(argSamplebarcodeid, argLineitem, argProject);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(argSamplebarcodeid, argLineitem, argProject);
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
	 * findProjectsampleByLineitem
	 */
	public java.util.Enumeration findProjectsampleByLineitem(com.ardais.bigr.iltds.beans.LineitemKey inKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.iltds.beans.EJSFinderProjectsampleBean)persister).findProjectsampleByLineitem(inKey));	}
	/**
	 * findByPrimaryKey
	 */
	public com.ardais.bigr.iltds.beans.Projectsample findByPrimaryKey(com.ardais.bigr.iltds.beans.ProjectsampleKey key) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return ((com.ardais.bigr.iltds.beans.EJSJDBCPersisterCMPProjectsampleBean_1fc02fe2) persister).findByPrimaryKey(key);
	}
	/**
	 * findProjectsampleByProject
	 */
	public java.util.Enumeration findProjectsampleByProject(com.ardais.bigr.iltds.beans.ProjectKey inKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.iltds.beans.EJSFinderProjectsampleBean)persister).findProjectsampleByProject(inKey));	}
	/**
	 * keyFromBean
	 */
	public Object keyFromBean(javax.ejb.EntityBean generalEJB) {
		com.ardais.bigr.iltds.beans.ProjectsampleBean tmpEJB = (com.ardais.bigr.iltds.beans.ProjectsampleBean) generalEJB;
		com.ardais.bigr.iltds.beans.ProjectsampleKey keyClass = new com.ardais.bigr.iltds.beans.ProjectsampleKey();
		keyClass.lineitem_lineitemid = tmpEJB.lineitem_lineitemid;
		keyClass.project_projectid = tmpEJB.project_projectid;
		keyClass.samplebarcodeid = tmpEJB.samplebarcodeid;
		return keyClass;
	}
	/**
	 * keyFromFields
	 */
	public com.ardais.bigr.iltds.beans.ProjectsampleKey keyFromFields(java.lang.String f0, java.lang.String f1, java.lang.String f2) {
		com.ardais.bigr.iltds.beans.ProjectsampleKey keyClass = new com.ardais.bigr.iltds.beans.ProjectsampleKey();
		keyClass.lineitem_lineitemid = f0;
		keyClass.project_projectid = f1;
		keyClass.samplebarcodeid = f2;
		return keyClass;
	}
}
