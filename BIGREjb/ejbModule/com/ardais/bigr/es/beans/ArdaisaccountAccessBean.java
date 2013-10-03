package com.ardais.bigr.es.beans;
import javax.rmi.*;
import com.ibm.ivj.ejb.runtime.*;
/**
 * ArdaisaccountAccessBean
 * @generated
 */
public class ArdaisaccountAccessBean
  extends AbstractEntityAccessBean
  implements com.ardais.bigr.es.beans.ArdaisaccountAccessBeanData {
  /**
   * @generated
   */
  private Ardaisaccount __ejbRef;
  /**
   * @generated
   */
  private java.lang.String init_ardais_acct_key;
  /**
   * getArdais_acct_open_date
   * @generated
   */
  public java.sql.Timestamp getArdais_acct_open_date()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("ardais_acct_open_date")));
  }
  /**
   * setArdais_acct_open_date
   * @generated
   */
  public void setArdais_acct_open_date(java.sql.Timestamp newValue) {
    __setCache("ardais_acct_open_date", newValue);
  }
  /**
   * getDefaultBoxLayoutId
   * @generated
   */
  public java.lang.String getDefaultBoxLayoutId()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("defaultBoxLayoutId")));
  }
  /**
   * setDefaultBoxLayoutId
   * @generated
   */
  public void setDefaultBoxLayoutId(java.lang.String newValue) {
    __setCache("defaultBoxLayoutId", newValue);
  }
  /**
   * getArdais_acct_reactivate_date
   * @generated
   */
  public java.sql.Timestamp getArdais_acct_reactivate_date()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("ardais_acct_reactivate_date")));
  }
  /**
   * setArdais_acct_reactivate_date
   * @generated
   */
  public void setArdais_acct_reactivate_date(java.sql.Timestamp newValue) {
    __setCache("ardais_acct_reactivate_date", newValue);
  }
  /**
   * getArdais_acct_type
   * @generated
   */
  public java.lang.String getArdais_acct_type()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("ardais_acct_type")));
  }
  /**
   * setArdais_acct_type
   * @generated
   */
  public void setArdais_acct_type(java.lang.String newValue) {
    __setCache("ardais_acct_type", newValue);
  }
  /**
   * getArdais_parent_acct_comp_desc
   * @generated
   */
  public java.lang.String getArdais_parent_acct_comp_desc()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("ardais_parent_acct_comp_desc")));
  }
  /**
   * setArdais_parent_acct_comp_desc
   * @generated
   */
  public void setArdais_parent_acct_comp_desc(java.lang.String newValue) {
    __setCache("ardais_parent_acct_comp_desc", newValue);
  }
  /**
   * getArdais_acct_deactivate_date
   * @generated
   */
  public java.sql.Timestamp getArdais_acct_deactivate_date()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("ardais_acct_deactivate_date")));
  }
  /**
   * setArdais_acct_deactivate_date
   * @generated
   */
  public void setArdais_acct_deactivate_date(java.sql.Timestamp newValue) {
    __setCache("ardais_acct_deactivate_date", newValue);
  }
  /**
   * getArdais_acct_status
   * @generated
   */
  public java.lang.String getArdais_acct_status()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("ardais_acct_status")));
  }
  /**
   * setArdais_acct_status
   * @generated
   */
  public void setArdais_acct_status(java.lang.String newValue) {
    __setCache("ardais_acct_status", newValue);
  }
  /**
   * getRequest_mgr_email_address
   * @generated
   */
  public java.lang.String getRequest_mgr_email_address()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("request_mgr_email_address")));
  }
  /**
   * setRequest_mgr_email_address
   * @generated
   */
  public void setRequest_mgr_email_address(java.lang.String newValue) {
    __setCache("request_mgr_email_address", newValue);
  }
  /**
   * getArdais_acct_close_date
   * @generated
   */
  public java.sql.Timestamp getArdais_acct_close_date()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("ardais_acct_close_date")));
  }
  /**
   * setArdais_acct_close_date
   * @generated
   */
  public void setArdais_acct_close_date(java.sql.Timestamp newValue) {
    __setCache("ardais_acct_close_date", newValue);
  }
  /**
   * getPasswordPolicyCid
   * @generated
   */
  public java.lang.String getPasswordPolicyCid()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("passwordPolicyCid")));
  }
  /**
   * setPasswordPolicyCid
   * @generated
   */
  public void setPasswordPolicyCid(java.lang.String newValue) {
    __setCache("passwordPolicyCid", newValue);
  }
  /**
   * getPrimaryLocation
   * @generated
   */
  public java.lang.String getPrimaryLocation()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("primaryLocation")));
  }
  /**
   * setPrimaryLocation
   * @generated
   */
  public void setPrimaryLocation(java.lang.String newValue) {
    __setCache("primaryLocation", newValue);
  }
  /**
   * getLinked_cases_only_yn
   * @generated
   */
  public java.lang.String getLinked_cases_only_yn()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("linked_cases_only_yn")));
  }
  /**
   * setLinked_cases_only_yn
   * @generated
   */
  public void setLinked_cases_only_yn(java.lang.String newValue) {
    __setCache("linked_cases_only_yn", newValue);
  }
  /**
   * getArdais_acct_active_date
   * @generated
   */
  public java.sql.Timestamp getArdais_acct_active_date()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("ardais_acct_active_date")));
  }
  /**
   * setArdais_acct_active_date
   * @generated
   */
  public void setArdais_acct_active_date(java.sql.Timestamp newValue) {
    __setCache("ardais_acct_active_date", newValue);
  }
  /**
   * getArdais_acct_company_desc
   * @generated
   */
  public java.lang.String getArdais_acct_company_desc()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("ardais_acct_company_desc")));
  }
  /**
   * setArdais_acct_company_desc
   * @generated
   */
  public void setArdais_acct_company_desc(java.lang.String newValue) {
    __setCache("ardais_acct_company_desc", newValue);
  }
  /**
   * getPasswordLifeSpan
   * @generated
   */
  public int getPasswordLifeSpan()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((Integer) __getCache("passwordLifeSpan")).intValue());
  }
  /**
   * setPasswordLifeSpan
   * @generated
   */
  public void setPasswordLifeSpan(int newValue) {
    __setCache("passwordLifeSpan", new Integer(newValue));
  }
  /**
   * getArdais_acct_deactivate_reason
   * @generated
   */
  public java.lang.String getArdais_acct_deactivate_reason()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("ardais_acct_deactivate_reason")));
  }
  /**
   * setArdais_acct_deactivate_reason
   * @generated
   */
  public void setArdais_acct_deactivate_reason(java.lang.String newValue) {
    __setCache("ardais_acct_deactivate_reason", newValue);
  }
  /**
   * setInit_ardais_acct_key
   * @generated
   */
  public void setInit_ardais_acct_key(java.lang.String newValue) {
    this.init_ardais_acct_key = (newValue);
  }
  /**
   * ArdaisaccountAccessBean
   * @generated
   */
  public ArdaisaccountAccessBean() {
    super();
  }
  /**
   * ArdaisaccountAccessBean
   * @generated
   */
  public ArdaisaccountAccessBean(javax.ejb.EJBObject o) throws java.rmi.RemoteException {
    super(o);
  }
  /**
   * defaultJNDIName
   * @generated
   */
  public String defaultJNDIName() {
    return "com/ardais/bigr/es/beans/Ardaisaccount";
  }
  /**
   * ejbHome
   * @generated
   */
  private com.ardais.bigr.es.beans.ArdaisaccountHome ejbHome()
    throws java.rmi.RemoteException,
    javax.naming.NamingException {
    return (com.ardais.bigr.es.beans.ArdaisaccountHome) PortableRemoteObject.narrow(
      getHome(),
      com.ardais.bigr.es.beans.ArdaisaccountHome.class);
  }
  /**
   * ejbRef
   * @generated
   */
  private com.ardais.bigr.es.beans.Ardaisaccount ejbRef() throws java.rmi.RemoteException {
    if (ejbRef == null)
      return null;
    if (__ejbRef == null)
      __ejbRef = (com.ardais.bigr.es.beans.Ardaisaccount) PortableRemoteObject.narrow(
        ejbRef,
        com.ardais.bigr.es.beans.Ardaisaccount.class);

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

    ejbRef = ejbHome().create(init_ardais_acct_key);
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
      com.ardais.bigr.es.beans.ArdaisaccountKey pKey = (com.ardais.bigr.es.beans.ArdaisaccountKey) this
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
   * ArdaisaccountAccessBean
   * @generated
   */
  public ArdaisaccountAccessBean(
    java.lang.String ardais_acct_key,
    java.lang.String linked_cases_only_yn,
    java.lang.String argPasswordPolicyCid,
    int argPasswordLifeSpan) throws javax.naming.NamingException, javax.ejb.CreateException,
    java.rmi.RemoteException {
    ejbRef = ejbHome().create(
      ardais_acct_key,
      linked_cases_only_yn,
      argPasswordPolicyCid,
      argPasswordLifeSpan);
  }
  /**
   * ArdaisaccountAccessBean
   * @generated
   */
  public ArdaisaccountAccessBean(com.ardais.bigr.es.beans.ArdaisaccountKey key)
    throws javax.naming.NamingException, javax.ejb.FinderException, javax.ejb.CreateException,
    java.rmi.RemoteException {
    ejbRef = ejbHome().findByPrimaryKey(key);
  }
  /**
   * getArdaisuser
   * @generated
   */
  public java.util.Enumeration getArdaisuser()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    return ejbRef().getArdaisuser();
  }
  /**
   * secondaryAddArdaisuser
   * @generated
   */
  public void secondaryAddArdaisuser(com.ardais.bigr.es.beans.Ardaisuser anArdaisuser)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryAddArdaisuser(anArdaisuser);
  }
  /**
   * secondaryRemoveArdaisuser
   * @generated
   */
  public void secondaryRemoveArdaisuser(com.ardais.bigr.es.beans.Ardaisuser anArdaisuser)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryRemoveArdaisuser(anArdaisuser);
  }
  /**
   * getDonorRegistrationForm
   * @generated
   */
  public java.lang.String getDonorRegistrationForm()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("donorRegistrationForm")));
  }
  /**
   * setDonorRegistrationForm
   * @generated
   */
  public void setDonorRegistrationForm(java.lang.String newValue) {
    __setCache("donorRegistrationForm", newValue);
  }
}
