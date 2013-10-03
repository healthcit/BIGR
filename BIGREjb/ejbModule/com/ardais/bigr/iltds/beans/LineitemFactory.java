package com.ardais.bigr.iltds.beans;
import javax.ejb.*;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import com.ibm.etools.ejb.client.runtime.*;
/**
 * LineitemFactory
 * @generated
 */
public class LineitemFactory extends AbstractEJBFactory {
  /**
   * LineitemFactory
   * @generated
   */
  public LineitemFactory() {
    super();
  }
  /**
   * _acquireLineitemHome
   * @generated
   */
  protected com.ardais.bigr.iltds.beans.LineitemHome _acquireLineitemHome()
    throws java.rmi.RemoteException {
    return (com.ardais.bigr.iltds.beans.LineitemHome) _acquireEJBHome();
  }
  /**
   * acquireLineitemHome
   * @generated
   */
  public com.ardais.bigr.iltds.beans.LineitemHome acquireLineitemHome()
    throws javax.naming.NamingException {
    return (com.ardais.bigr.iltds.beans.LineitemHome) acquireEJBHome();
  }
  /**
   * getDefaultJNDIName
   * @generated
   */
  public String getDefaultJNDIName() {
    return "com/ardais/bigr/iltds/beans/Lineitem";
  }
  /**
   * getHomeInterface
   * @generated
   */
  protected Class getHomeInterface() {
    return com.ardais.bigr.iltds.beans.LineitemHome.class;
  }
  /**
   * resetLineitemHome
   * @generated
   */
  public void resetLineitemHome() {
    resetEJBHome();
  }
  /**
   * setLineitemHome
   * @generated
   */
  public void setLineitemHome(com.ardais.bigr.iltds.beans.LineitemHome home) {
    setEJBHome(home);
  }
  /**
   * findByPrimaryKey
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Lineitem findByPrimaryKey(
    com.ardais.bigr.iltds.beans.LineitemKey key)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireLineitemHome().findByPrimaryKey(key);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Lineitem create(java.lang.String argLineitemid)
    throws javax.ejb.CreateException,
    java.rmi.RemoteException {
    return _acquireLineitemHome().create(argLineitemid);
  }
  /**
   * findLineitemByProject
   * @generated
   */
  public java.util.Enumeration findLineitemByProject(com.ardais.bigr.iltds.beans.ProjectKey inKey)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireLineitemHome().findLineitemByProject(inKey);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Lineitem create(
    java.lang.String argLineitemid,
    java.lang.String argProjectid,
    java.math.BigDecimal argLineitemnumber,
    java.math.BigDecimal argQuantity) throws javax.ejb.CreateException, java.rmi.RemoteException {
    return _acquireLineitemHome().create(
      argLineitemid,
      argProjectid,
      argLineitemnumber,
      argQuantity);
  }
}
