package com.ardais.bigr.iltds.beans;
import javax.ejb.*;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import com.ibm.etools.ejb.client.runtime.*;
/**
 * ProjectsampleFactory
 * @generated
 */
public class ProjectsampleFactory extends AbstractEJBFactory {
  /**
   * ProjectsampleFactory
   * @generated
   */
  public ProjectsampleFactory() {
    super();
  }
  /**
   * _acquireProjectsampleHome
   * @generated
   */
  protected com.ardais.bigr.iltds.beans.ProjectsampleHome _acquireProjectsampleHome()
    throws java.rmi.RemoteException {
    return (com.ardais.bigr.iltds.beans.ProjectsampleHome) _acquireEJBHome();
  }
  /**
   * acquireProjectsampleHome
   * @generated
   */
  public com.ardais.bigr.iltds.beans.ProjectsampleHome acquireProjectsampleHome()
    throws javax.naming.NamingException {
    return (com.ardais.bigr.iltds.beans.ProjectsampleHome) acquireEJBHome();
  }
  /**
   * getDefaultJNDIName
   * @generated
   */
  public String getDefaultJNDIName() {
    return "com/ardais/bigr/iltds/beans/Projectsample";
  }
  /**
   * getHomeInterface
   * @generated
   */
  protected Class getHomeInterface() {
    return com.ardais.bigr.iltds.beans.ProjectsampleHome.class;
  }
  /**
   * resetProjectsampleHome
   * @generated
   */
  public void resetProjectsampleHome() {
    resetEJBHome();
  }
  /**
   * setProjectsampleHome
   * @generated
   */
  public void setProjectsampleHome(com.ardais.bigr.iltds.beans.ProjectsampleHome home) {
    setEJBHome(home);
  }
  /**
   * findProjectsampleByLineitem
   * @generated
   */
  public java.util.Enumeration findProjectsampleByLineitem(
    com.ardais.bigr.iltds.beans.LineitemKey inKey)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireProjectsampleHome().findProjectsampleByLineitem(inKey);
  }
  /**
   * findProjectsampleByProject
   * @generated
   */
  public java.util.Enumeration findProjectsampleByProject(
    com.ardais.bigr.iltds.beans.ProjectKey inKey)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireProjectsampleHome().findProjectsampleByProject(inKey);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Projectsample create(
    java.lang.String argSamplebarcodeid,
    com.ardais.bigr.iltds.beans.LineitemKey argLineitem,
    com.ardais.bigr.iltds.beans.ProjectKey argProject)
    throws javax.ejb.CreateException,
    java.rmi.RemoteException {
    return _acquireProjectsampleHome().create(argSamplebarcodeid, argLineitem, argProject);
  }
  /**
   * findByPrimaryKey
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Projectsample findByPrimaryKey(
    com.ardais.bigr.iltds.beans.ProjectsampleKey key)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireProjectsampleHome().findByPrimaryKey(key);
  }
}
