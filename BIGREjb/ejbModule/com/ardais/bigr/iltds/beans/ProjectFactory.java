package com.ardais.bigr.iltds.beans;
import javax.ejb.*;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import com.ibm.etools.ejb.client.runtime.*;
/**
 * ProjectFactory
 * @generated
 */
public class ProjectFactory extends AbstractEJBFactory {
  /**
   * ProjectFactory
   * @generated
   */
  public ProjectFactory() {
    super();
  }
  /**
   * _acquireProjectHome
   * @generated
   */
  protected com.ardais.bigr.iltds.beans.ProjectHome _acquireProjectHome()
    throws java.rmi.RemoteException {
    return (com.ardais.bigr.iltds.beans.ProjectHome) _acquireEJBHome();
  }
  /**
   * acquireProjectHome
   * @generated
   */
  public com.ardais.bigr.iltds.beans.ProjectHome acquireProjectHome()
    throws javax.naming.NamingException {
    return (com.ardais.bigr.iltds.beans.ProjectHome) acquireEJBHome();
  }
  /**
   * getDefaultJNDIName
   * @generated
   */
  public String getDefaultJNDIName() {
    return "com/ardais/bigr/iltds/beans/Project";
  }
  /**
   * getHomeInterface
   * @generated
   */
  protected Class getHomeInterface() {
    return com.ardais.bigr.iltds.beans.ProjectHome.class;
  }
  /**
   * resetProjectHome
   * @generated
   */
  public void resetProjectHome() {
    resetEJBHome();
  }
  /**
   * setProjectHome
   * @generated
   */
  public void setProjectHome(com.ardais.bigr.iltds.beans.ProjectHome home) {
    setEJBHome(home);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Project create(java.lang.String argProjectid)
    throws javax.ejb.CreateException,
    java.rmi.RemoteException {
    return _acquireProjectHome().create(argProjectid);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Project create(
    java.lang.String argProjectid,
    java.lang.String projectName,
    java.lang.String client,
    java.lang.String requestedBy,
    java.sql.Timestamp requestDate) throws javax.ejb.CreateException, java.rmi.RemoteException {
    return _acquireProjectHome()
      .create(argProjectid, projectName, client, requestedBy, requestDate);
  }
  /**
   * findByPrimaryKey
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Project findByPrimaryKey(
    com.ardais.bigr.iltds.beans.ProjectKey key)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireProjectHome().findByPrimaryKey(key);
  }
}
