package com.ardais.bigr.iltds.beans;
import javax.ejb.*;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import com.ibm.etools.ejb.client.runtime.*;
/**
 * AsmformFactory
 * @generated
 */
public class AsmformFactory extends AbstractEJBFactory {
  /**
   * AsmformFactory
   * @generated
   */
  public AsmformFactory() {
    super();
  }
  /**
   * _acquireAsmformHome
   * @generated
   */
  protected com.ardais.bigr.iltds.beans.AsmformHome _acquireAsmformHome()
    throws java.rmi.RemoteException {
    return (com.ardais.bigr.iltds.beans.AsmformHome) _acquireEJBHome();
  }
  /**
   * acquireAsmformHome
   * @generated
   */
  public com.ardais.bigr.iltds.beans.AsmformHome acquireAsmformHome()
    throws javax.naming.NamingException {
    return (com.ardais.bigr.iltds.beans.AsmformHome) acquireEJBHome();
  }
  /**
   * getDefaultJNDIName
   * @generated
   */
  public String getDefaultJNDIName() {
    return "com/ardais/bigr/iltds/beans/Asmform";
  }
  /**
   * getHomeInterface
   * @generated
   */
  protected Class getHomeInterface() {
    return com.ardais.bigr.iltds.beans.AsmformHome.class;
  }
  /**
   * resetAsmformHome
   * @generated
   */
  public void resetAsmformHome() {
    resetEJBHome();
  }
  /**
   * setAsmformHome
   * @generated
   */
  public void setAsmformHome(com.ardais.bigr.iltds.beans.AsmformHome home) {
    setEJBHome(home);
  }
  /**
   * findByPrimaryKey
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Asmform findByPrimaryKey(
    com.ardais.bigr.iltds.beans.AsmformKey primaryKey)
    throws javax.ejb.FinderException,
    java.rmi.RemoteException {
    return _acquireAsmformHome().findByPrimaryKey(primaryKey);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Asmform create(
    java.lang.String argAsm_form_id,
    com.ardais.bigr.iltds.beans.ConsentKey argConsent,
    java.lang.String argArdais_id) throws javax.ejb.CreateException, java.rmi.RemoteException {
    return _acquireAsmformHome().create(argAsm_form_id, argConsent, argArdais_id);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Asmform create(
    java.lang.String argAsm_form_id,
    com.ardais.bigr.iltds.beans.ConsentKey argConsent,
    com.ardais.bigr.iltds.beans.ArdaisstaffKey argArdaisstaff)
    throws javax.ejb.CreateException,
    java.rmi.RemoteException {
    return _acquireAsmformHome().create(argAsm_form_id, argConsent, argArdaisstaff);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Asmform create(
    java.lang.String argAsm_form_id,
    com.ardais.bigr.iltds.beans.ConsentKey argConsent)
    throws javax.ejb.CreateException,
    java.rmi.RemoteException {
    return _acquireAsmformHome().create(argAsm_form_id, argConsent);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Asmform create(
    java.lang.String asm_form_id,
    com.ardais.bigr.iltds.beans.Consent argConsent)
    throws javax.ejb.CreateException,
    java.rmi.RemoteException {
    return _acquireAsmformHome().create(asm_form_id, argConsent);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Asmform create(
    java.lang.String argAsm_form_id,
    com.ardais.bigr.iltds.beans.ConsentKey argConsent,
    com.ardais.bigr.iltds.beans.ArdaisstaffKey argArdaisstaff,
    java.lang.String argArdais_id) throws javax.ejb.CreateException, java.rmi.RemoteException {
    return _acquireAsmformHome().create(argAsm_form_id, argConsent, argArdaisstaff, argArdais_id);
  }
  /**
   * findByASMFormID
   * @generated
   */
  public java.util.Enumeration findByASMFormID(java.lang.String asmFormID)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireAsmformHome().findByASMFormID(asmFormID);
  }
  /**
   * findAsmformByConsent
   * @generated
   */
  public java.util.Enumeration findAsmformByConsent(com.ardais.bigr.iltds.beans.ConsentKey inKey)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireAsmformHome().findAsmformByConsent(inKey);
  }
}
