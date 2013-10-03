package com.ardais.bigr.iltds.beans;
import javax.ejb.*;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import com.ibm.etools.ejb.client.runtime.*;
/**
 * AsmFactory
 * @generated
 */
public class AsmFactory extends AbstractEJBFactory {
  /**
   * AsmFactory
   * @generated
   */
  public AsmFactory() {
    super();
  }
  /**
   * _acquireAsmHome
   * @generated
   */
  protected com.ardais.bigr.iltds.beans.AsmHome _acquireAsmHome() throws java.rmi.RemoteException {
    return (com.ardais.bigr.iltds.beans.AsmHome) _acquireEJBHome();
  }
  /**
   * acquireAsmHome
   * @generated
   */
  public com.ardais.bigr.iltds.beans.AsmHome acquireAsmHome() throws javax.naming.NamingException {
    return (com.ardais.bigr.iltds.beans.AsmHome) acquireEJBHome();
  }
  /**
   * getDefaultJNDIName
   * @generated
   */
  public String getDefaultJNDIName() {
    return "com/ardais/bigr/iltds/beans/Asm";
  }
  /**
   * getHomeInterface
   * @generated
   */
  protected Class getHomeInterface() {
    return com.ardais.bigr.iltds.beans.AsmHome.class;
  }
  /**
   * resetAsmHome
   * @generated
   */
  public void resetAsmHome() {
    resetEJBHome();
  }
  /**
   * setAsmHome
   * @generated
   */
  public void setAsmHome(com.ardais.bigr.iltds.beans.AsmHome home) {
    setEJBHome(home);
  }
  /**
   * findByConsentID
   * @generated
   */
  public java.util.Enumeration findByConsentID(java.lang.String argConsentID)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireAsmHome().findByConsentID(argConsentID);
  }
  /**
   * findByPrimaryKey
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Asm findByPrimaryKey(com.ardais.bigr.iltds.beans.AsmKey key)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireAsmHome().findByPrimaryKey(key);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Asm create(java.lang.String asm_id)
    throws javax.ejb.CreateException,
    java.rmi.RemoteException {
    return _acquireAsmHome().create(asm_id);
  }
}
