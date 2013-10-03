package com.ardais.bigr.iltds.beans;
import javax.ejb.*;
import javax.rmi.*;
import com.ibm.ivj.ejb.runtime.*;
/**
 * ProjectsampleAccessBean
 * @generated
 */
public class ProjectsampleAccessBean
	extends AbstractEntityAccessBean
	implements com.ardais.bigr.iltds.beans.ProjectsampleAccessBeanData {
  /**
   * @generated
   */
  private Projectsample __ejbRef;
  /**
   * @generated
   */
  private java.lang.String init_argSamplebarcodeid;
  /**
   * @generated
   */
  private com.ardais.bigr.iltds.beans.LineitemKey init_argLineitem;
  /**
   * @generated
   */
  private com.ardais.bigr.iltds.beans.ProjectKey init_argProject;
  /**
   * getProjectKey
   * @generated
   */
  public com.ardais.bigr.iltds.beans.ProjectKey getProjectKey()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((com.ardais.bigr.iltds.beans.ProjectKey) __getCache("projectKey")));
  }
  /**
   * getLineitemKey
   * @generated
   */
  public com.ardais.bigr.iltds.beans.LineitemKey getLineitemKey()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((com.ardais.bigr.iltds.beans.LineitemKey) __getCache("lineitemKey")));
  }
  /**
   * setInit_argSamplebarcodeid
   * @generated
   */
  public void setInit_argSamplebarcodeid(java.lang.String newValue) {
    this.init_argSamplebarcodeid = (newValue);
  }
  /**
   * setInit_argLineitem
   * @generated
   */
  public void setInit_argLineitem(com.ardais.bigr.iltds.beans.LineitemKey newValue) {
    this.init_argLineitem = (newValue);
  }
  /**
   * setInit_argProject
   * @generated
   */
  public void setInit_argProject(com.ardais.bigr.iltds.beans.ProjectKey newValue) {
    this.init_argProject = (newValue);
  }
  /**
   * ProjectsampleAccessBean
   * @generated
   */
  public ProjectsampleAccessBean() {
    super();
  }
  /**
   * ProjectsampleAccessBean
   * @generated
   */
  public ProjectsampleAccessBean(javax.ejb.EJBObject o) throws java.rmi.RemoteException {
    super(o);
  }
  /**
   * defaultJNDIName
   * @generated
   */
  public String defaultJNDIName() {
    return "com/ardais/bigr/iltds/beans/Projectsample";
  }
  /**
   * ejbHome
   * @generated
   */
  private com.ardais.bigr.iltds.beans.ProjectsampleHome ejbHome()
    throws java.rmi.RemoteException,
    javax.naming.NamingException {
    return (com.ardais.bigr.iltds.beans.ProjectsampleHome) PortableRemoteObject.narrow(
      getHome(),
      com.ardais.bigr.iltds.beans.ProjectsampleHome.class);
  }
  /**
   * ejbRef
   * @generated
   */
  private com.ardais.bigr.iltds.beans.Projectsample ejbRef() throws java.rmi.RemoteException {
    if (ejbRef == null)
      return null;
    if (__ejbRef == null)
      __ejbRef = (com.ardais.bigr.iltds.beans.Projectsample) PortableRemoteObject.narrow(
        ejbRef,
        com.ardais.bigr.iltds.beans.Projectsample.class);

    return __ejbRef;
  }
  /**
   * instantiateEJB
   * @generated
   */
  protected void instantiateEJB()
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    if (ejbRef() != null)
      return;

    ejbRef = ejbHome().create(init_argSamplebarcodeid, init_argLineitem, init_argProject);
  }
  /**
   * instantiateEJBByPrimaryKey
   * @generated
   */
  protected boolean instantiateEJBByPrimaryKey()
    throws javax.ejb.CreateException,
    java.rmi.RemoteException,
    javax.naming.NamingException {
    boolean result = false;

    if (ejbRef() != null)
      return true;

    try {
      com.ardais.bigr.iltds.beans.ProjectsampleKey pKey = (com.ardais.bigr.iltds.beans.ProjectsampleKey) this
        .__getKey();
      if (pKey != null) {
        ejbRef = ejbHome().findByPrimaryKey(pKey);
        result = true;
      }
    } catch (javax.ejb.FinderException e) {
    }
    return result;
  }
  /**
   * refreshCopyHelper
   * @generated
   */
  public void refreshCopyHelper()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    refreshCopyHelper(ejbRef());
  }
  /**
   * commitCopyHelper
   * @generated
   */
  public void commitCopyHelper()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    commitCopyHelper(ejbRef());
  }
  /**
   * findProjectsampleByLineitem
   * @generated
   */
  public java.util.Enumeration findProjectsampleByLineitem(
    com.ardais.bigr.iltds.beans.LineitemKey inKey)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.iltds.beans.ProjectsampleHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findProjectsampleByLineitem(inKey);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * findProjectsampleByProject
   * @generated
   */
  public java.util.Enumeration findProjectsampleByProject(
    com.ardais.bigr.iltds.beans.ProjectKey inKey)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.iltds.beans.ProjectsampleHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findProjectsampleByProject(inKey);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * ProjectsampleAccessBean
   * @generated
   */
  public ProjectsampleAccessBean(com.ardais.bigr.iltds.beans.ProjectsampleKey key)
    throws javax.naming.NamingException, javax.ejb.FinderException, javax.ejb.CreateException,
    java.rmi.RemoteException {
    ejbRef = ejbHome().findByPrimaryKey(key);
  }
  /**
   * getProject
   * @generated
   */
  public com.ardais.bigr.iltds.beans.ProjectAccessBean getProject()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    com.ardais.bigr.iltds.beans.Project localEJBRef = ejbRef().getProject();
    if (localEJBRef != null)
      return new com.ardais.bigr.iltds.beans.ProjectAccessBean(localEJBRef);
    else
      return null;
  }
  /**
   * getLineitem
   * @generated
   */
  public com.ardais.bigr.iltds.beans.LineitemAccessBean getLineitem()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    com.ardais.bigr.iltds.beans.Lineitem localEJBRef = ejbRef().getLineitem();
    if (localEJBRef != null)
      return new com.ardais.bigr.iltds.beans.LineitemAccessBean(localEJBRef);
    else
      return null;
  }
}
