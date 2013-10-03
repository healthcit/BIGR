package com.ardais.bigr.iltds.beans;
import javax.ejb.*;
import javax.rmi.*;
import com.ibm.ivj.ejb.runtime.*;
/**
 * LineitemAccessBean
 * @generated
 */
public class LineitemAccessBean
	extends AbstractEntityAccessBean
	implements com.ardais.bigr.iltds.beans.LineitemAccessBeanData {
  /**
   * @generated
   */
  private Lineitem __ejbRef;
  /**
   * @generated
   */
  private java.lang.String init_argLineitemid;
  /**
   * @generated
   */
  private java.lang.String init_argProjectid;
  /**
   * @generated
   */
  private java.math.BigDecimal init_argLineitemnumber;
  /**
   * @generated
   */
  private java.math.BigDecimal init_argQuantity;
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
   * getQuantity
   * @generated
   */
  public java.math.BigDecimal getQuantity()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.math.BigDecimal) __getCache("quantity")));
  }
  /**
   * setQuantity
   * @generated
   */
  public void setQuantity(java.math.BigDecimal newValue) {
    __setCache("quantity", newValue);
  }
  /**
   * getComments
   * @generated
   */
  public java.lang.String getComments()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("comments")));
  }
  /**
   * setComments
   * @generated
   */
  public void setComments(java.lang.String newValue) {
    __setCache("comments", newValue);
  }
  /**
   * getNotes
   * @generated
   */
  public java.lang.String getNotes()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("notes")));
  }
  /**
   * setNotes
   * @generated
   */
  public void setNotes(java.lang.String newValue) {
    __setCache("notes", newValue);
  }
  /**
   * getFormat
   * @generated
   */
  public java.lang.String getFormat()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("format")));
  }
  /**
   * setFormat
   * @generated
   */
  public void setFormat(java.lang.String newValue) {
    __setCache("format", newValue);
  }
  /**
   * getLineitemnumber
   * @generated
   */
  public java.math.BigDecimal getLineitemnumber()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.math.BigDecimal) __getCache("lineitemnumber")));
  }
  /**
   * setLineitemnumber
   * @generated
   */
  public void setLineitemnumber(java.math.BigDecimal newValue) {
    __setCache("lineitemnumber", newValue);
  }
  /**
   * setInit_argLineitemid
   * @generated
   */
  public void setInit_argLineitemid(java.lang.String newValue) {
    this.init_argLineitemid = (newValue);
  }
  /**
   * setInit_argProjectid
   * @generated
   */
  public void setInit_argProjectid(java.lang.String newValue) {
    this.init_argProjectid = (newValue);
  }
  /**
   * setInit_argLineitemnumber
   * @generated
   */
  public void setInit_argLineitemnumber(java.math.BigDecimal newValue) {
    this.init_argLineitemnumber = (newValue);
  }
  /**
   * setInit_argQuantity
   * @generated
   */
  public void setInit_argQuantity(java.math.BigDecimal newValue) {
    this.init_argQuantity = (newValue);
  }
  /**
   * LineitemAccessBean
   * @generated
   */
  public LineitemAccessBean() {
    super();
  }
  /**
   * LineitemAccessBean
   * @generated
   */
  public LineitemAccessBean(javax.ejb.EJBObject o) throws java.rmi.RemoteException {
    super(o);
  }
  /**
   * defaultJNDIName
   * @generated
   */
  public String defaultJNDIName() {
    return "com/ardais/bigr/iltds/beans/Lineitem";
  }
  /**
   * ejbHome
   * @generated
   */
  private com.ardais.bigr.iltds.beans.LineitemHome ejbHome()
    throws java.rmi.RemoteException,
    javax.naming.NamingException {
    return (com.ardais.bigr.iltds.beans.LineitemHome) PortableRemoteObject.narrow(
      getHome(),
      com.ardais.bigr.iltds.beans.LineitemHome.class);
  }
  /**
   * ejbRef
   * @generated
   */
  private com.ardais.bigr.iltds.beans.Lineitem ejbRef() throws java.rmi.RemoteException {
    if (ejbRef == null)
      return null;
    if (__ejbRef == null)
      __ejbRef = (com.ardais.bigr.iltds.beans.Lineitem) PortableRemoteObject.narrow(
        ejbRef,
        com.ardais.bigr.iltds.beans.Lineitem.class);

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

    ejbRef = ejbHome().create(
      init_argLineitemid,
      init_argProjectid,
      init_argLineitemnumber,
      init_argQuantity);
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
      com.ardais.bigr.iltds.beans.LineitemKey pKey = (com.ardais.bigr.iltds.beans.LineitemKey) this
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
   * LineitemAccessBean
   * @generated
   */
  public LineitemAccessBean(com.ardais.bigr.iltds.beans.LineitemKey key)
    throws javax.naming.NamingException, javax.ejb.FinderException, javax.ejb.CreateException,
    java.rmi.RemoteException {
    ejbRef = ejbHome().findByPrimaryKey(key);
  }
  /**
   * LineitemAccessBean
   * @generated
   */
  public LineitemAccessBean(java.lang.String argLineitemid) throws javax.naming.NamingException,
    javax.ejb.CreateException, java.rmi.RemoteException {
    ejbRef = ejbHome().create(argLineitemid);
  }
  /**
   * findLineitemByProject
   * @generated
   */
  public java.util.Enumeration findLineitemByProject(com.ardais.bigr.iltds.beans.ProjectKey inKey)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.iltds.beans.LineitemHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findLineitemByProject(inKey);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * secondaryRemoveProjectsample
   * @generated
   */
  public void secondaryRemoveProjectsample(com.ardais.bigr.iltds.beans.Projectsample aProjectsample)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryRemoveProjectsample(aProjectsample);
  }
  /**
   * getProjectsample
   * @generated
   */
  public java.util.Enumeration getProjectsample()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    return ejbRef().getProjectsample();
  }
  /**
   * setProject
   * @generated
   */
  public void setProject(com.ardais.bigr.iltds.beans.Project aProject)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().setProject(aProject);
  }
  /**
   * secondaryAddProjectsample
   * @generated
   */
  public void secondaryAddProjectsample(com.ardais.bigr.iltds.beans.Projectsample aProjectsample)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryAddProjectsample(aProjectsample);
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
   * secondarySetProject
   * @generated
   */
  public void secondarySetProject(com.ardais.bigr.iltds.beans.Project aProject)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondarySetProject(aProject);
  }
  /**
   * privateSetProjectKey
   * @generated
   */
  public void privateSetProjectKey(com.ardais.bigr.iltds.beans.ProjectKey inKey)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().privateSetProjectKey(inKey);
  }
}
