package com.ardais.bigr.lims.beans;

/**
 * EJSCMPPathologyEvaluationHomeBean
 */
public class EJSCMPPathologyEvaluationHomeBean extends com.ardais.bigr.lims.beans.EJSCMPPathologyEvaluationHomeBean_bb9279f9 {
	/**
	 * EJSCMPPathologyEvaluationHomeBean
	 */
	public EJSCMPPathologyEvaluationHomeBean() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.lims.beans.PathologyEvaluation postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.lims.beans.PathologyEvaluation) super.postCreate(beanO, ejsKey);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
	}
}
