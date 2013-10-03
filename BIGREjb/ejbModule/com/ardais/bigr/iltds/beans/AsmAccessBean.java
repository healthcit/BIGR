package com.ardais.bigr.iltds.beans;
import javax.ejb.*;
import javax.rmi.*;
import com.ibm.ivj.ejb.runtime.*;
/**
 * AsmAccessBean
 * @generated
 */
public class AsmAccessBean
	extends AbstractEntityAccessBean
	implements com.ardais.bigr.iltds.beans.AsmAccessBeanData {
  /**
   * @generated
   */
  private Asm __ejbRef;
  /**
   * @generated
   */
  private java.lang.String init_asm_id;
  /**
   * getSpecimen_type
   * @generated
   */
  public java.lang.String getSpecimen_type()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("specimen_type")));
  }
  /**
   * setSpecimen_type
   * @generated
   */
  public void setSpecimen_type(java.lang.String newValue) {
    __setCache("specimen_type", newValue);
  }
  /**
   * getModule_weight
   * @generated
   */
  public java.lang.Integer getModule_weight()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.Integer) __getCache("module_weight")));
  }
  /**
   * setModule_weight
   * @generated
   */
  public void setModule_weight(java.lang.Integer newValue) {
    __setCache("module_weight", newValue);
  }
  /**
   * getArdais_id
   * @generated
   */
  public java.lang.String getArdais_id()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("ardais_id")));
  }
  /**
   * setArdais_id
   * @generated
   */
  public void setArdais_id(java.lang.String newValue) {
    __setCache("ardais_id", newValue);
  }
  /**
   * getConsent_id
   * @generated
   */
  public java.lang.String getConsent_id()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("consent_id")));
  }
  /**
   * setConsent_id
   * @generated
   */
  public void setConsent_id(java.lang.String newValue) {
    __setCache("consent_id", newValue);
  }
  /**
   * getPfin_meets_specs
   * @generated
   */
  public java.lang.String getPfin_meets_specs()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("pfin_meets_specs")));
  }
  /**
   * setPfin_meets_specs
   * @generated
   */
  public void setPfin_meets_specs(java.lang.String newValue) {
    __setCache("pfin_meets_specs", newValue);
  }
  /**
   * getOrgan_site_concept_id_other
   * @generated
   */
  public java.lang.String getOrgan_site_concept_id_other()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("organ_site_concept_id_other")));
  }
  /**
   * setOrgan_site_concept_id_other
   * @generated
   */
  public void setOrgan_site_concept_id_other(java.lang.String newValue) {
    __setCache("organ_site_concept_id_other", newValue);
  }
  /**
   * getModule_comments
   * @generated
   */
  public java.lang.String getModule_comments()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("module_comments")));
  }
  /**
   * setModule_comments
   * @generated
   */
  public void setModule_comments(java.lang.String newValue) {
    __setCache("module_comments", newValue);
  }
  /**
   * getAsm_entry_date
   * @generated
   */
  public java.sql.Timestamp getAsm_entry_date()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("asm_entry_date")));
  }
  /**
   * setAsm_entry_date
   * @generated
   */
  public void setAsm_entry_date(java.sql.Timestamp newValue) {
    __setCache("asm_entry_date", newValue);
  }
  /**
   * getOrgan_site_concept_id
   * @generated
   */
  public java.lang.String getOrgan_site_concept_id()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("organ_site_concept_id")));
  }
  /**
   * setOrgan_site_concept_id
   * @generated
   */
  public void setOrgan_site_concept_id(java.lang.String newValue) {
    __setCache("organ_site_concept_id", newValue);
  }
  /**
   * getAsm_form_id
   * @generated
   */
  public java.lang.String getAsm_form_id()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("asm_form_id")));
  }
  /**
   * setAsm_form_id
   * @generated
   */
  public void setAsm_form_id(java.lang.String newValue) {
    __setCache("asm_form_id", newValue);
  }
  /**
   * setInit_asm_id
   * @generated
   */
  public void setInit_asm_id(java.lang.String newValue) {
    this.init_asm_id = (newValue);
  }
  /**
   * AsmAccessBean
   * @generated
   */
  public AsmAccessBean() {
    super();
  }
  /**
   * AsmAccessBean
   * @generated
   */
  public AsmAccessBean(javax.ejb.EJBObject o) throws java.rmi.RemoteException {
    super(o);
  }
  /**
   * defaultJNDIName
   * @generated
   */
  public String defaultJNDIName() {
    return "com/ardais/bigr/iltds/beans/Asm";
  }
  /**
   * ejbHome
   * @generated
   */
  private com.ardais.bigr.iltds.beans.AsmHome ejbHome()
    throws java.rmi.RemoteException,
    javax.naming.NamingException {
    return (com.ardais.bigr.iltds.beans.AsmHome) PortableRemoteObject.narrow(
      getHome(),
      com.ardais.bigr.iltds.beans.AsmHome.class);
  }
  /**
   * ejbRef
   * @generated
   */
  private com.ardais.bigr.iltds.beans.Asm ejbRef() throws java.rmi.RemoteException {
    if (ejbRef == null)
      return null;
    if (__ejbRef == null)
      __ejbRef = (com.ardais.bigr.iltds.beans.Asm) PortableRemoteObject.narrow(
        ejbRef,
        com.ardais.bigr.iltds.beans.Asm.class);

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

    ejbRef = ejbHome().create(init_asm_id);
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
      com.ardais.bigr.iltds.beans.AsmKey pKey = (com.ardais.bigr.iltds.beans.AsmKey) this
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
   * findByConsentID
   * @generated
   */
  public java.util.Enumeration findByConsentID(java.lang.String argConsentID)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.iltds.beans.AsmHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findByConsentID(argConsentID);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * AsmAccessBean
   * @generated
   */
  public AsmAccessBean(com.ardais.bigr.iltds.beans.AsmKey key) throws javax.naming.NamingException,
    javax.ejb.FinderException, javax.ejb.CreateException, java.rmi.RemoteException {
    ejbRef = ejbHome().findByPrimaryKey(key);
  }
  /**
   * secondaryRemoveSample
   * @generated
   */
  public void secondaryRemoveSample(com.ardais.bigr.iltds.beans.Sample aSample)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryRemoveSample(aSample);
  }
  /**
   * removeSample
   * @generated
   */
  public void removeSample(com.ardais.bigr.iltds.beans.Sample aSample)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().removeSample(aSample);
  }
  /**
   * addSample
   * @generated
   */
  public void addSample(com.ardais.bigr.iltds.beans.Sample aSample)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().addSample(aSample);
  }
  /**
   * getSample
   * @generated
   */
  public java.util.Enumeration getSample()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    return ejbRef().getSample();
  }
  /**
   * secondaryAddSample
   * @generated
   */
  public void secondaryAddSample(com.ardais.bigr.iltds.beans.Sample aSample)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryAddSample(aSample);
  }
}
