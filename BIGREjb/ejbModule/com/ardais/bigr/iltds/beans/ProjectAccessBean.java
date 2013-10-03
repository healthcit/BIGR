package com.ardais.bigr.iltds.beans;
import javax.ejb.*;
import javax.rmi.*;
import com.ibm.ivj.ejb.runtime.*;
/**
 * ProjectAccessBean
 * @generated
 */
public class ProjectAccessBean
	extends AbstractEntityAccessBean
	implements com.ardais.bigr.iltds.beans.ProjectAccessBeanData {
  /**
   * @generated
   */
  private Project __ejbRef;
  /**
   * @generated
   */
  private java.lang.String init_argProjectid;
  /**
   * @generated
   */
  private java.lang.String init_projectName;
  /**
   * @generated
   */
  private java.lang.String init_client;
  /**
   * @generated
   */
  private java.lang.String init_requestedBy;
  /**
   * @generated
   */
  private java.sql.Timestamp init_requestDate;
  /**
   * getArdaisaccountkey
   * @generated
   */
  public java.lang.String getArdaisaccountkey()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("ardaisaccountkey")));
  }
  /**
   * setArdaisaccountkey
   * @generated
   */
  public void setArdaisaccountkey(java.lang.String newValue) {
    __setCache("ardaisaccountkey", newValue);
  }
  /**
   * getStatus
   * @generated
   */
  public java.lang.String getStatus()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("status")));
  }
  /**
   * setStatus
   * @generated
   */
  public void setStatus(java.lang.String newValue) {
    __setCache("status", newValue);
  }
  /**
   * getDateapproved
   * @generated
   */
  public java.sql.Timestamp getDateapproved()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("dateapproved")));
  }
  /**
   * setDateapproved
   * @generated
   */
  public void setDateapproved(java.sql.Timestamp newValue) {
    __setCache("dateapproved", newValue);
  }
  /**
   * getDateshipped
   * @generated
   */
  public java.sql.Timestamp getDateshipped()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("dateshipped")));
  }
  /**
   * setDateshipped
   * @generated
   */
  public void setDateshipped(java.sql.Timestamp newValue) {
    __setCache("dateshipped", newValue);
  }
  /**
   * getProjectId
   * @generated
   */
  public java.lang.String getProjectId()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("projectId")));
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
   * getProjectname
   * @generated
   */
  public java.lang.String getProjectname()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("projectname")));
  }
  /**
   * setProjectname
   * @generated
   */
  public void setProjectname(java.lang.String newValue) {
    __setCache("projectname", newValue);
  }
  /**
   * getDaterequested
   * @generated
   */
  public java.sql.Timestamp getDaterequested()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("daterequested")));
  }
  /**
   * setDaterequested
   * @generated
   */
  public void setDaterequested(java.sql.Timestamp newValue) {
    __setCache("daterequested", newValue);
  }
  /**
   * getArdaisuserid
   * @generated
   */
  public java.lang.String getArdaisuserid()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("ardaisuserid")));
  }
  /**
   * setArdaisuserid
   * @generated
   */
  public void setArdaisuserid(java.lang.String newValue) {
    __setCache("ardaisuserid", newValue);
  }
  /**
   * getPercentcomplete
   * @generated
   */
  public java.math.BigDecimal getPercentcomplete()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.math.BigDecimal) __getCache("percentcomplete")));
  }
  /**
   * setPercentcomplete
   * @generated
   */
  public void setPercentcomplete(java.math.BigDecimal newValue) {
    __setCache("percentcomplete", newValue);
  }
  /**
   * setInit_argProjectid
   * @generated
   */
  public void setInit_argProjectid(java.lang.String newValue) {
    this.init_argProjectid = (newValue);
  }
  /**
   * setInit_projectName
   * @generated
   */
  public void setInit_projectName(java.lang.String newValue) {
    this.init_projectName = (newValue);
  }
  /**
   * setInit_client
   * @generated
   */
  public void setInit_client(java.lang.String newValue) {
    this.init_client = (newValue);
  }
  /**
   * setInit_requestedBy
   * @generated
   */
  public void setInit_requestedBy(java.lang.String newValue) {
    this.init_requestedBy = (newValue);
  }
  /**
   * setInit_requestDate
   * @generated
   */
  public void setInit_requestDate(java.sql.Timestamp newValue) {
    this.init_requestDate = (newValue);
  }
  /**
   * ProjectAccessBean
   * @generated
   */
  public ProjectAccessBean() {
    super();
  }
  /**
   * ProjectAccessBean
   * @generated
   */
  public ProjectAccessBean(javax.ejb.EJBObject o) throws java.rmi.RemoteException {
    super(o);
  }
  /**
   * defaultJNDIName
   * @generated
   */
  public String defaultJNDIName() {
    return "com/ardais/bigr/iltds/beans/Project";
  }
  /**
   * ejbHome
   * @generated
   */
  private com.ardais.bigr.iltds.beans.ProjectHome ejbHome()
    throws java.rmi.RemoteException,
    javax.naming.NamingException {
    return (com.ardais.bigr.iltds.beans.ProjectHome) PortableRemoteObject.narrow(
      getHome(),
      com.ardais.bigr.iltds.beans.ProjectHome.class);
  }
  /**
   * ejbRef
   * @generated
   */
  private com.ardais.bigr.iltds.beans.Project ejbRef() throws java.rmi.RemoteException {
    if (ejbRef == null)
      return null;
    if (__ejbRef == null)
      __ejbRef = (com.ardais.bigr.iltds.beans.Project) PortableRemoteObject.narrow(
        ejbRef,
        com.ardais.bigr.iltds.beans.Project.class);

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
      init_argProjectid,
      init_projectName,
      init_client,
      init_requestedBy,
      init_requestDate);
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
      com.ardais.bigr.iltds.beans.ProjectKey pKey = (com.ardais.bigr.iltds.beans.ProjectKey) this
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
   * ProjectAccessBean
   * @generated
   */
  public ProjectAccessBean(java.lang.String argProjectid) throws javax.naming.NamingException,
    javax.ejb.CreateException, java.rmi.RemoteException {
    ejbRef = ejbHome().create(argProjectid);
  }
  /**
   * ProjectAccessBean
   * @generated
   */
  public ProjectAccessBean(com.ardais.bigr.iltds.beans.ProjectKey key)
    throws javax.naming.NamingException, javax.ejb.FinderException, javax.ejb.CreateException,
    java.rmi.RemoteException {
    ejbRef = ejbHome().findByPrimaryKey(key);
  }
  /**
   * secondaryRemoveLineitem
   * @generated
   */
  public void secondaryRemoveLineitem(com.ardais.bigr.iltds.beans.Lineitem aLineitem)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryRemoveLineitem(aLineitem);
  }
  /**
   * addLineitem
   * @generated
   */
  public void addLineitem(com.ardais.bigr.iltds.beans.Lineitem aLineitem)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().addLineitem(aLineitem);
  }
  /**
   * addWorkorder
   * @generated
   */
  public void addWorkorder(com.ardais.bigr.iltds.beans.Workorder aWorkorder)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().addWorkorder(aWorkorder);
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
   * getLineitem
   * @generated
   */
  public java.util.Enumeration getLineitem()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    return ejbRef().getLineitem();
  }
  /**
   * removeLineitem
   * @generated
   */
  public void removeLineitem(com.ardais.bigr.iltds.beans.Lineitem aLineitem)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().removeLineitem(aLineitem);
  }
  /**
   * secondaryAddLineitem
   * @generated
   */
  public void secondaryAddLineitem(com.ardais.bigr.iltds.beans.Lineitem aLineitem)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryAddLineitem(aLineitem);
  }
  /**
   * secondaryRemoveWorkorder
   * @generated
   */
  public void secondaryRemoveWorkorder(com.ardais.bigr.iltds.beans.Workorder aWorkorder)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryRemoveWorkorder(aWorkorder);
  }
  /**
   * removeWorkorder
   * @generated
   */
  public void removeWorkorder(com.ardais.bigr.iltds.beans.Workorder aWorkorder)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().removeWorkorder(aWorkorder);
  }
  /**
   * getWorkorder
   * @generated
   */
  public java.util.Enumeration getWorkorder()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    return ejbRef().getWorkorder();
  }
  /**
   * secondaryAddWorkorder
   * @generated
   */
  public void secondaryAddWorkorder(com.ardais.bigr.iltds.beans.Workorder aWorkorder)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryAddWorkorder(aWorkorder);
  }
}
