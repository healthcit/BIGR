package com.ardais.bigr.es.beans;
import javax.rmi.*;
import com.ibm.ivj.ejb.runtime.*;
/**
 * ArdaisuserAccessBean
 * @generated
 */
public class ArdaisuserAccessBean
  extends AbstractEntityAccessBean
  implements com.ardais.bigr.es.beans.ArdaisuserAccessBeanData {
  /**
   * @generated
   */
  private Ardaisuser __ejbRef;
  /**
   * @generated
   */
  private java.lang.String init_ardais_user_id;
  /**
   * @generated
   */
  private com.ardais.bigr.es.beans.Ardaisaccount init_argArdaisaccount;
  /**
   * @generated
   */
  private java.lang.String init_passwordPolicyCid;
  /**
   * getUser_phone_ext
   * @generated
   */
  public java.lang.String getUser_phone_ext()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("user_phone_ext")));
  }
  /**
   * setUser_phone_ext
   * @generated
   */
  public void setUser_phone_ext(java.lang.String newValue) {
    __setCache("user_phone_ext", newValue);
  }
  /**
   * getPassword
   * @generated
   */
  public java.lang.String getPassword()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("password")));
  }
  /**
   * setPassword
   * @generated
   */
  public void setPassword(java.lang.String newValue) {
    __setCache("password", newValue);
  }
  /**
   * getUser_lastname
   * @generated
   */
  public java.lang.String getUser_lastname()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("user_lastname")));
  }
  /**
   * setUser_lastname
   * @generated
   */
  public void setUser_lastname(java.lang.String newValue) {
    __setCache("user_lastname", newValue);
  }
  /**
   * getUpdate_date
   * @generated
   */
  public java.sql.Timestamp getUpdate_date()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("update_date")));
  }
  /**
   * setUpdate_date
   * @generated
   */
  public void setUpdate_date(java.sql.Timestamp newValue) {
    __setCache("update_date", newValue);
  }
  /**
   * getUser_active_ind
   * @generated
   */
  public java.lang.String getUser_active_ind()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("user_active_ind")));
  }
  /**
   * setUser_active_ind
   * @generated
   */
  public void setUser_active_ind(java.lang.String newValue) {
    __setCache("user_active_ind", newValue);
  }
  /**
   * getUpdated_by
   * @generated
   */
  public java.lang.String getUpdated_by()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("updated_by")));
  }
  /**
   * setUpdated_by
   * @generated
   */
  public void setUpdated_by(java.lang.String newValue) {
    __setCache("updated_by", newValue);
  }
  /**
   * getUser_affiliation
   * @generated
   */
  public java.lang.String getUser_affiliation()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("user_affiliation")));
  }
  /**
   * setUser_affiliation
   * @generated
   */
  public void setUser_affiliation(java.lang.String newValue) {
    __setCache("user_affiliation", newValue);
  }
  /**
   * getCreated_by
   * @generated
   */
  public java.lang.String getCreated_by()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("created_by")));
  }
  /**
   * setCreated_by
   * @generated
   */
  public void setCreated_by(java.lang.String newValue) {
    __setCache("created_by", newValue);
  }
  /**
   * getUser_phone_number
   * @generated
   */
  public java.lang.String getUser_phone_number()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("user_phone_number")));
  }
  /**
   * setUser_phone_number
   * @generated
   */
  public void setUser_phone_number(java.lang.String newValue) {
    __setCache("user_phone_number", newValue);
  }
  /**
   * getUser_title
   * @generated
   */
  public java.lang.String getUser_title()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("user_title")));
  }
  /**
   * setUser_title
   * @generated
   */
  public void setUser_title(java.lang.String newValue) {
    __setCache("user_title", newValue);
  }
  /**
   * getUser_verif_question
   * @generated
   */
  public java.lang.String getUser_verif_question()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("user_verif_question")));
  }
  /**
   * setUser_verif_question
   * @generated
   */
  public void setUser_verif_question(java.lang.String newValue) {
    __setCache("user_verif_question", newValue);
  }
  /**
   * getUser_firstname
   * @generated
   */
  public java.lang.String getUser_firstname()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("user_firstname")));
  }
  /**
   * setUser_firstname
   * @generated
   */
  public void setUser_firstname(java.lang.String newValue) {
    __setCache("user_firstname", newValue);
  }
  /**
   * getCreate_date
   * @generated
   */
  public java.sql.Timestamp getCreate_date()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("create_date")));
  }
  /**
   * setCreate_date
   * @generated
   */
  public void setCreate_date(java.sql.Timestamp newValue) {
    __setCache("create_date", newValue);
  }
  /**
   * getArdaisaccountKey
   * @generated
   */
  public com.ardais.bigr.es.beans.ArdaisaccountKey getArdaisaccountKey()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((com.ardais.bigr.es.beans.ArdaisaccountKey) __getCache("ardaisaccountKey")));
  }
  /**
   * getUser_fax_number
   * @generated
   */
  public java.lang.String getUser_fax_number()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("user_fax_number")));
  }
  /**
   * setUser_fax_number
   * @generated
   */
  public void setUser_fax_number(java.lang.String newValue) {
    __setCache("user_fax_number", newValue);
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
   * getUser_verif_answer
   * @generated
   */
  public java.lang.String getUser_verif_answer()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("user_verif_answer")));
  }
  /**
   * setUser_verif_answer
   * @generated
   */
  public void setUser_verif_answer(java.lang.String newValue) {
    __setCache("user_verif_answer", newValue);
  }
  /**
   * getUser_functional_title
   * @generated
   */
  public java.lang.String getUser_functional_title()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("user_functional_title")));
  }
  /**
   * setUser_functional_title
   * @generated
   */
  public void setUser_functional_title(java.lang.String newValue) {
    __setCache("user_functional_title", newValue);
  }
  /**
   * getUser_email_address
   * @generated
   */
  public java.lang.String getUser_email_address()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("user_email_address")));
  }
  /**
   * setUser_email_address
   * @generated
   */
  public void setUser_email_address(java.lang.String newValue) {
    __setCache("user_email_address", newValue);
  }
  /**
   * getUser_address_id
   * @generated
   */
  public java.math.BigDecimal getUser_address_id()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.math.BigDecimal) __getCache("user_address_id")));
  }
  /**
   * setUser_address_id
   * @generated
   */
  public void setUser_address_id(java.math.BigDecimal newValue) {
    __setCache("user_address_id", newValue);
  }
  /**
   * getUser_mobile_number
   * @generated
   */
  public java.lang.String getUser_mobile_number()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("user_mobile_number")));
  }
  /**
   * setUser_mobile_number
   * @generated
   */
  public void setUser_mobile_number(java.lang.String newValue) {
    __setCache("user_mobile_number", newValue);
  }
  /**
   * getPasswordLastChangeDate
   * @generated
   */
  public java.sql.Timestamp getPasswordLastChangeDate()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("passwordLastChangeDate")));
  }
  /**
   * setPasswordLastChangeDate
   * @generated
   */
  public void setPasswordLastChangeDate(java.sql.Timestamp newValue) {
    __setCache("passwordLastChangeDate", newValue);
  }
  /**
   * setInit_ardais_user_id
   * @generated
   */
  public void setInit_ardais_user_id(java.lang.String newValue) {
    this.init_ardais_user_id = (newValue);
  }
  /**
   * setInit_argArdaisaccount
   * @generated
   */
  public void setInit_argArdaisaccount(com.ardais.bigr.es.beans.Ardaisaccount newValue) {
    this.init_argArdaisaccount = (newValue);
  }
  /**
   * setInit_passwordPolicyCid
   * @generated
   */
  public void setInit_passwordPolicyCid(java.lang.String newValue) {
    this.init_passwordPolicyCid = (newValue);
  }
  /**
   * ArdaisuserAccessBean
   * @generated
   */
  public ArdaisuserAccessBean() {
    super();
  }
  /**
   * ArdaisuserAccessBean
   * @generated
   */
  public ArdaisuserAccessBean(javax.ejb.EJBObject o) throws java.rmi.RemoteException {
    super(o);
  }
  /**
   * defaultJNDIName
   * @generated
   */
  public String defaultJNDIName() {
    return "com/ardais/bigr/es/beans/Ardaisuser";
  }
  /**
   * ejbHome
   * @generated
   */
  private com.ardais.bigr.es.beans.ArdaisuserHome ejbHome()
    throws java.rmi.RemoteException,
    javax.naming.NamingException {
    return (com.ardais.bigr.es.beans.ArdaisuserHome) PortableRemoteObject.narrow(
      getHome(),
      com.ardais.bigr.es.beans.ArdaisuserHome.class);
  }
  /**
   * ejbRef
   * @generated
   */
  private com.ardais.bigr.es.beans.Ardaisuser ejbRef() throws java.rmi.RemoteException {
    if (ejbRef == null)
      return null;
    if (__ejbRef == null)
      __ejbRef = (com.ardais.bigr.es.beans.Ardaisuser) PortableRemoteObject.narrow(
        ejbRef,
        com.ardais.bigr.es.beans.Ardaisuser.class);

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

    ejbRef = ejbHome().create(init_ardais_user_id, init_argArdaisaccount, init_passwordPolicyCid);
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
      com.ardais.bigr.es.beans.ArdaisuserKey pKey = (com.ardais.bigr.es.beans.ArdaisuserKey) this
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
   * findByUserId
   * @generated
   */
  public ArdaisuserAccessBean findByUserId(java.lang.String userId)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.es.beans.ArdaisuserHome localHome = ejbHome();
    com.ardais.bigr.es.beans.Ardaisuser ejbs = localHome.findByUserId(userId);
    return (ArdaisuserAccessBean) createAccessBeans(ejbs);
  }
  /**
   * ArdaisuserAccessBean
   * @generated
   */
  public ArdaisuserAccessBean(
    java.lang.String argArdais_user_id,
    com.ardais.bigr.es.beans.ArdaisaccountKey argArdaisaccount,
    java.lang.String argPassword,
    java.lang.String argCreated_by,
    java.sql.Timestamp argCreate_date,
    java.lang.String argPasswordPolicyCid) throws javax.naming.NamingException,
    javax.ejb.CreateException, java.rmi.RemoteException {
    ejbRef = ejbHome().create(
      argArdais_user_id,
      argArdaisaccount,
      argPassword,
      argCreated_by,
      argCreate_date,
      argPasswordPolicyCid);
  }
  /**
   * findArdaisuserByArdaisaccount
   * @generated
   */
  public java.util.Enumeration findArdaisuserByArdaisaccount(
    com.ardais.bigr.es.beans.ArdaisaccountKey inKey)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.es.beans.ArdaisuserHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findArdaisuserByArdaisaccount(inKey);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * ArdaisuserAccessBean
   * @generated
   */
  public ArdaisuserAccessBean(com.ardais.bigr.es.beans.ArdaisuserKey primaryKey)
    throws javax.naming.NamingException, javax.ejb.FinderException, javax.ejb.CreateException,
    java.rmi.RemoteException {
    ejbRef = ejbHome().findByPrimaryKey(primaryKey);
  }
  /**
   * ArdaisuserAccessBean
   * @generated
   */
  public ArdaisuserAccessBean(
    java.lang.String argArdais_user_id,
    com.ardais.bigr.es.beans.ArdaisaccountKey argArdaisaccount,
    java.lang.String argPasswordPolicyCid) throws javax.naming.NamingException,
    javax.ejb.CreateException, java.rmi.RemoteException {
    ejbRef = ejbHome().create(argArdais_user_id, argArdaisaccount, argPasswordPolicyCid);
  }
  /**
   * getArdaisaccount
   * @generated
   */
  public com.ardais.bigr.es.beans.ArdaisaccountAccessBean getArdaisaccount()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    com.ardais.bigr.es.beans.Ardaisaccount localEJBRef = ejbRef().getArdaisaccount();
    if (localEJBRef != null)
      return new com.ardais.bigr.es.beans.ArdaisaccountAccessBean(localEJBRef);
    else
      return null;
  }
  /**
   * addArdaisorder
   * @generated
   */
  public void addArdaisorder(com.ardais.bigr.es.beans.Ardaisorder anArdaisorder)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().addArdaisorder(anArdaisorder);
  }
  /**
   * secondaryRemoveArdaisorder
   * @generated
   */
  public void secondaryRemoveArdaisorder(com.ardais.bigr.es.beans.Ardaisorder anArdaisorder)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryRemoveArdaisorder(anArdaisorder);
  }
  /**
   * getArdaisorder
   * @generated
   */
  public java.util.Enumeration getArdaisorder()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    return ejbRef().getArdaisorder();
  }
  /**
   * secondaryRemoveShoppingcart
   * @generated
   */
  public void secondaryRemoveShoppingcart(com.ardais.bigr.es.beans.Shoppingcart aShoppingcart)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryRemoveShoppingcart(aShoppingcart);
  }
  /**
   * secondaryAddArdaisorder
   * @generated
   */
  public void secondaryAddArdaisorder(com.ardais.bigr.es.beans.Ardaisorder anArdaisorder)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryAddArdaisorder(anArdaisorder);
  }
  /**
   * getShoppingcart
   * @generated
   */
  public java.util.Enumeration getShoppingcart()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    return ejbRef().getShoppingcart();
  }
  /**
   * secondaryAddShoppingcart
   * @generated
   */
  public void secondaryAddShoppingcart(com.ardais.bigr.es.beans.Shoppingcart aShoppingcart)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryAddShoppingcart(aShoppingcart);
  }
  /**
   * getUser_department
   * @generated
   */
  public java.lang.String getUser_department()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("user_department")));
  }
  /**
   * setUser_department
   * @generated
   */
  public void setUser_department(java.lang.String newValue) {
    __setCache("user_department", newValue);
  }
  /**
   * getConsecutive_failed_answers
   * @generated
   */
  public java.lang.Integer getConsecutive_failed_answers()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.Integer) __getCache("consecutive_failed_answers")));
  }
  /**
   * setConsecutive_failed_answers
   * @generated
   */
  public void setConsecutive_failed_answers(java.lang.Integer newValue) {
    __setCache("consecutive_failed_answers", newValue);
  }
  /**
   * getConsecutive_failed_logins
   * @generated
   */
  public java.lang.Integer getConsecutive_failed_logins()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.Integer) __getCache("consecutive_failed_logins")));
  }
  /**
   * setConsecutive_failed_logins
   * @generated
   */
  public void setConsecutive_failed_logins(java.lang.Integer newValue) {
    __setCache("consecutive_failed_logins", newValue);
  }
}
