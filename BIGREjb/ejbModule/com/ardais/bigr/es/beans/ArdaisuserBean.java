package com.ardais.bigr.es.beans;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.ardais.bigr.api.CMPDefaultValues;
import com.ibm.ivj.ejb.associations.interfaces.ManyLink;
import com.ibm.ivj.ejb.associations.interfaces.SingleLink;
import com.ibm.ivj.ejb.associations.links.Link;
import com.ibm.ivj.ejb.runtime.AccessBeanHashtable;
/**
 * This is an Entity Bean class with CMP fields
 */
public class ArdaisuserBean implements EntityBean {
	public static final String DEFAULT_ardais_user_id = null;
	public static final String DEFAULT_ardaisaccount_ardais_acct_key = null;
	public static final String DEFAULT_password = null;
	public static final String DEFAULT_user_verif_question = null;
	public static final String DEFAULT_user_verif_answer = null;
	public static final String DEFAULT_user_lastname = null;
	public static final String DEFAULT_user_firstname = null;
	public static final String DEFAULT_user_title = null;
  public static final String DEFAULT_user_department = null;
	public static final String DEFAULT_user_functional_title = null;
	public static final String DEFAULT_user_affiliation = null;
	public static final String DEFAULT_user_phone_number = null;
	public static final String DEFAULT_user_phone_ext = null;
	public static final String DEFAULT_user_fax_number = null;
	public static final String DEFAULT_user_mobile_number = null;
	public static final String DEFAULT_user_email_address = null;
	public static final String DEFAULT_created_by = null;
	public static final java.sql.Timestamp DEFAULT_create_date = null;
	public static final java.sql.Timestamp DEFAULT_update_date = null;
	public static final String DEFAULT_updated_by = null;
	public static final java.math.BigDecimal DEFAULT_user_address_id = null;
	public static final String DEFAULT_user_active_ind = null;
  public static final String DEFAULT_passwordPolicyCid = null;
  public static final java.sql.Timestamp DEFAULT_passwordLastChangeDate = null;
  public static final Integer DEFAULT_consecutive_failed_logins = null;
  public static final Integer DEFAULT_consecutive_failed_answers = null;

	/**
	 * Implementation field for persistent attribute: ardais_user_id
	 */
	public java.lang.String ardais_user_id;
	public String password;
	public String user_verif_question;
	public String user_verif_answer;
	public String user_lastname;
	public String user_firstname;
	public String user_title;
	public String user_functional_title;
	public String user_affiliation;
	public String user_phone_number;
	public String user_phone_ext;
	public String user_fax_number;
	public String user_mobile_number;
	public String user_email_address;
	public String created_by;
	public java.sql.Timestamp create_date;
	public java.sql.Timestamp update_date;
	public String updated_by;
	public java.math.BigDecimal user_address_id;
	public String user_active_ind;

	private javax.ejb.EntityContext entityContext = null;
	final static long serialVersionUID = 3206093459760846163L;
	private transient com
		.ibm
		.ivj
		.ejb
		.associations
		.interfaces
		.ManyLink ardaisorderLink;
	private static final com.ardais.bigr.api.CMPDefaultValues _fieldDefaultValues =
		new com.ardais.bigr.api.CMPDefaultValues(
			com.ardais.bigr.es.beans.ArdaisuserBean.class);
	private javax.ejb.EntityContext myEntityCtx;
	private transient com
		.ibm
		.ivj
		.ejb
		.associations
		.interfaces
		.ManyLink shoppingcartLink;
  /**
   * Implementation field for persistent attribute: ardaisaccount_ardais_acct_key
   */
  public java.lang.String ardaisaccount_ardais_acct_key;
  private transient com.ibm.ivj.ejb.associations.interfaces.SingleLink ardaisaccountLink;
  /**
   * Implementation field for persistent attribute: passwordPolicyCid
   */
  public java.lang.String passwordPolicyCid;
  /**
   * Implementation field for persistent attribute: passwordLastChangeDate
   */
  public java.sql.Timestamp passwordLastChangeDate;
  /**
   * Implementation field for persistent attribute: user_department
   */
  public java.lang.String user_department;
  /**
   * Implementation field for persistent attribute: consecutive_failed_logins
   */
  public java.lang.Integer consecutive_failed_logins;
  /**
   * Implementation field for persistent attribute: consecutive_failed_answers
   */
  public java.lang.Integer consecutive_failed_answers;
  /**
   * This method was generated for supporting the associations.
   */
  protected java.util.Vector _getLinks() {
    java.util.Vector links = new java.util.Vector();
    links.add(getArdaisorderLink());
    links.add(getShoppingcartLink());
    links.add(getArdaisaccountLink());
    return links;
  }
  /**
   * This method was generated for supporting the associations.
   */
  protected void _initLinks() {
    ardaisorderLink = null;
    shoppingcartLink = null;
    ardaisaccountLink = null;
  }
  /**
   * This method was generated for supporting the associations.
   */
  protected void _removeLinks() throws java.rmi.RemoteException, javax.ejb.RemoveException {
    java.util.List links = _getLinks();
    for (int i = 0; i < links.size(); i++) {
      try {
        ((com.ibm.ivj.ejb.associations.interfaces.Link) links.get(i)).remove();
      }
      catch (javax.ejb.FinderException e) {
      } //Consume Finder error since I am going away
    }
  }
	/**
	 * This method was generated for supporting the relationship role named ardaisorder.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public void addArdaisorder(
		com.ardais.bigr.es.beans.Ardaisorder anArdaisorder)
		throws java.rmi.RemoteException {
		this.getArdaisorderLink().addElement(anArdaisorder);
	}
	/**
	 * ejbActivate
	 */
	public void ejbActivate() throws java.rmi.RemoteException {
		_initLinks();
	}
	/**
	 * ejbCreate method for a CMP entity bean
	 * @param argArdais_user_id java.lang.String
	 * @param argArdaisaccount com.ardais.bigr.es.beans.ArdaisaccountKey
	 * @exception javax.ejb.CreateException The exception description.
	 */
	public ArdaisuserKey ejbCreate(
		java.lang.String argArdais_user_id,
		com.ardais.bigr.es.beans.ArdaisaccountKey argArdaisaccount,
    java.lang.String argPasswordPolicyCid)
		throws javax.ejb.CreateException, EJBException {
		_fieldDefaultValues.assignTo(this);
		_initLinks();
		// All CMP fields should be initialized here.
		ardais_user_id = argArdais_user_id;
    setPasswordPolicyCid(argPasswordPolicyCid);
    setPasswordLastChangeDate(new java.sql.Timestamp(System.currentTimeMillis()));
		boolean ardaisaccount_NULLTEST = (argArdaisaccount == null);
		if (ardaisaccount_NULLTEST)
			ardaisaccount_ardais_acct_key = null;
		else
			ardaisaccount_ardais_acct_key = argArdaisaccount.ardais_acct_key;
		return null;
	}
	public ArdaisuserKey ejbCreate(
		java.lang.String argArdais_user_id,
		com.ardais.bigr.es.beans.ArdaisaccountKey argArdaisaccount,
		java.lang.String argPassword,
		java.lang.String argCreated_by,
		java.sql.Timestamp argCreate_date,
    java.lang.String argPasswordPolicyCid)
		throws javax.ejb.CreateException, EJBException {
		_fieldDefaultValues.assignTo(this);
		_initLinks();
		// All CMP fields should be initialized here.
		ardais_user_id = argArdais_user_id;
		password = argPassword;
		created_by = argCreated_by;
		create_date = argCreate_date;
    setPasswordPolicyCid(argPasswordPolicyCid);
    setPasswordLastChangeDate(new java.sql.Timestamp(System.currentTimeMillis()));

		boolean ardaisaccount_NULLTEST = (argArdaisaccount == null);
		if (ardaisaccount_NULLTEST)
			ardaisaccount_ardais_acct_key = null;
		else
			ardaisaccount_ardais_acct_key = argArdaisaccount.ardais_acct_key;
		return null;
	}
	/**
	 * ejbLoad
	 */
	public void ejbLoad() throws java.rmi.RemoteException {
		_initLinks();
	}
	/**
	 * ejbPassivate
	 */
	public void ejbPassivate() throws java.rmi.RemoteException {
	}
	/**
	 * ejbPostCreate method for a CMP entity bean
	 * @param argArdais_user_id java.lang.String
	 * @param argArdaisaccount com.ardais.bigr.es.beans.ArdaisaccountKey
	 */
	public void ejbPostCreate(
		java.lang.String argArdais_user_id,
		com.ardais.bigr.es.beans.ArdaisaccountKey argArdaisaccount,
    java.lang.String argPasswordPolicyCid)
		throws EJBException {
	}
	/**
	 * ejbPostCreate method for a CMP entity bean
	 * @param argArdais_user_id java.lang.String
	 * @param argArdaisaccount com.ardais.bigr.es.beans.ArdaisaccountKey
	 */
	public void ejbPostCreate(
		java.lang.String argArdais_user_id,
		com.ardais.bigr.es.beans.ArdaisaccountKey argArdaisaccount,
		java.lang.String argPassword,
		java.lang.String argCreated_by,
		java.sql.Timestamp argCreate_date,
    java.lang.String argPasswordPolicyCid)
		throws EJBException {
	}
	/**
	 * ejbRemove
	 */
	public void ejbRemove()
		throws javax.ejb.RemoveException, java.rmi.RemoteException {
		try {
			_removeLinks();
		} catch (java.rmi.RemoteException e) {
			throw new javax.ejb.RemoveException(e.getMessage());
		}
	}
	/**
	 * ejbStore
	 */
	public void ejbStore() throws java.rmi.RemoteException {
	}
	/**
	 * This method was generated for supporting the relationship role named ardaisorder.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public java.util.Enumeration getArdaisorder()
		throws java.rmi.RemoteException, javax.ejb.FinderException {
		return this.getArdaisorderLink().enumerationValue();
	}
	/**
	 * This method was generated for supporting the relationship role named ardaisorder.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	protected com
		.ibm
		.ivj
		.ejb
		.associations
		.interfaces
		.ManyLink getArdaisorderLink() {
		if (ardaisorderLink == null)
			ardaisorderLink = new ArdaisuserToArdaisorderLink(this);
		return ardaisorderLink;
	}
	/**
	 * Getter method for create_date
	 * @return java.sql.Timestamp
	 */
	public java.sql.Timestamp getCreate_date() {
		return create_date;
	}
	/**
	 * Getter method for created_by
	 * @return java.lang.String
	 */
	public java.lang.String getCreated_by() {
		return created_by;
	}
	/**
	 * getEntityContext
	 */
	public javax.ejb.EntityContext getEntityContext() {
		return myEntityCtx;
	}
	/**
	 * Getter method for password
	 * @return java.lang.String
	 */
	public java.lang.String getPassword() {
		return password;
	}
	/**
	 * Getter method for update_date
	 * @return java.sql.Timestamp
	 */
	public java.sql.Timestamp getUpdate_date() {
		return update_date;
	}
	/**
	 * Getter method for updated_by
	 * @return java.lang.String
	 */
	public java.lang.String getUpdated_by() {
		return updated_by;
	}
	/**
	 * Getter method for user_active_ind
	 */
	public java.lang.String getUser_active_ind() {
		return user_active_ind;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (4/2/2002 6:53:59 PM)
	 * @return java.math.BigDecimal
	 */
	public java.math.BigDecimal getUser_address_id() {
		return user_address_id;
	}
	/**
	 * Getter method for user_affiliation
	 * @return java.lang.String
	 */
	public java.lang.String getUser_affiliation() {
		return user_affiliation;
	}
	/**
	 * Getter method for user_email_address
	 * @return java.lang.String
	 */
	public java.lang.String getUser_email_address() {
		return user_email_address;
	}
	/**
	 * Getter method for user_fax_number
	 * @return java.lang.String
	 */
	public java.lang.String getUser_fax_number() {
		return user_fax_number;
	}
	/**
	 * Getter method for user_firstname
	 * @return java.lang.String
	 */
	public java.lang.String getUser_firstname() {
		return user_firstname;
	}
	/**
	 * Getter method for user_functional_title
	 * @return java.lang.String
	 */
	public java.lang.String getUser_functional_title() {
		return user_functional_title;
	}
	/**
	 * Getter method for user_lastname
	 * @return java.lang.String
	 */
	public java.lang.String getUser_lastname() {
		return user_lastname;
	}
	/**
	 * Getter method for user_mobile_number
	 * @return java.lang.String
	 */
	public java.lang.String getUser_mobile_number() {
		return user_mobile_number;
	}
	/**
	 * Getter method for user_phone_ext
	 * @return java.lang.String
	 */
	public java.lang.String getUser_phone_ext() {
		return user_phone_ext;
	}
	/**
	 * Getter method for user_phone_number
	 * @return java.lang.String
	 */
	public java.lang.String getUser_phone_number() {
		return user_phone_number;
	}
	/**
	 * Getter method for user_title
	 * @return java.lang.String
	 */
	public java.lang.String getUser_title() {
		return user_title;
	}
	/**
	 * Getter method for user_verif_answer
	 * @return java.lang.String
	 */
	public java.lang.String getUser_verif_answer() {
		return user_verif_answer;
	}
	/**
	 * Getter method for user_verif_question
	 * @return java.lang.String
	 */
	public java.lang.String getUser_verif_question() {
		return user_verif_question;
	}
	/**
	 * This method was generated for supporting the relationship role named ardaisorder.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public void secondaryAddArdaisorder(
		com.ardais.bigr.es.beans.Ardaisorder anArdaisorder)
		throws java.rmi.RemoteException {
		this.getArdaisorderLink().secondaryAddElement(anArdaisorder);
	}
	/**
	 * This method was generated for supporting the relationship role named ardaisorder.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public void secondaryRemoveArdaisorder(
		com.ardais.bigr.es.beans.Ardaisorder anArdaisorder)
		throws java.rmi.RemoteException {
		this.getArdaisorderLink().secondaryRemoveElement(anArdaisorder);
	}
	/**
	 * Setter method for create_date
	 * @param newValue java.sql.Timestamp
	 */
	public void setCreate_date(java.sql.Timestamp newValue) {
		this.create_date = newValue;
	}
	/**
	 * Setter method for created_by
	 * @param newValue java.lang.String
	 */
	public void setCreated_by(java.lang.String newValue) {
		this.created_by = newValue;
	}
	/**
	 * setEntityContext
	 */
	public void setEntityContext(javax.ejb.EntityContext ctx)
		throws java.rmi.RemoteException {
		myEntityCtx = ctx;
	}
	/**
	 * Setter method for password
	 * @param newValue java.lang.String
	 */
	public void setPassword(java.lang.String newValue) {
		this.password = newValue;
	}
	/**
	 * Setter method for update_date
	 * @param newValue java.sql.Timestamp
	 */
	public void setUpdate_date(java.sql.Timestamp newValue) {
		this.update_date = newValue;
	}
	/**
	 * Setter method for updated_by
	 * @param newValue java.lang.String
	 */
	public void setUpdated_by(java.lang.String newValue) {
		this.updated_by = newValue;
	}
	/**
	 * Setter method for user_active_ind
	 */
	public void setUser_active_ind(java.lang.String newValue) {
		this.user_active_ind = newValue;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (4/2/2002 6:53:59 PM)
	 * @param newUser_address_id java.math.BigDecimal
	 */
	public void setUser_address_id(java.math.BigDecimal newUser_address_id) {
		user_address_id = newUser_address_id;
	}
	/**
	 * Setter method for user_affiliation
	 * @param newValue java.lang.String
	 */
	public void setUser_affiliation(java.lang.String newValue) {
		this.user_affiliation = newValue;
	}
	/**
	 * Setter method for user_email_address
	 * @param newValue java.lang.String
	 */
	public void setUser_email_address(java.lang.String newValue) {
		this.user_email_address = newValue;
	}
	/**
	 * Setter method for user_fax_number
	 * @param newValue java.lang.String
	 */
	public void setUser_fax_number(java.lang.String newValue) {
		this.user_fax_number = newValue;
	}
	/**
	 * Setter method for user_firstname
	 * @param newValue java.lang.String
	 */
	public void setUser_firstname(java.lang.String newValue) {
		this.user_firstname = newValue;
	}
	/**
	 * Setter method for user_functional_title
	 * @param newValue java.lang.String
	 */
	public void setUser_functional_title(java.lang.String newValue) {
		this.user_functional_title = newValue;
	}
	/**
	 * Setter method for user_lastname
	 * @param newValue java.lang.String
	 */
	public void setUser_lastname(java.lang.String newValue) {
		this.user_lastname = newValue;
	}
	/**
	 * Setter method for user_mobile_number
	 * @param newValue java.lang.String
	 */
	public void setUser_mobile_number(java.lang.String newValue) {
		this.user_mobile_number = newValue;
	}
	/**
	 * Setter method for user_phone_ext
	 * @param newValue java.lang.String
	 */
	public void setUser_phone_ext(java.lang.String newValue) {
		this.user_phone_ext = newValue;
	}
	/**
	 * Setter method for user_phone_number
	 * @param newValue java.lang.String
	 */
	public void setUser_phone_number(java.lang.String newValue) {
		this.user_phone_number = newValue;
	}
	/**
	 * Setter method for user_title
	 * @param newValue java.lang.String
	 */
	public void setUser_title(java.lang.String newValue) {
		this.user_title = newValue;
	}
	/**
	 * Setter method for user_verif_answer
	 * @param newValue java.lang.String
	 */
	public void setUser_verif_answer(java.lang.String newValue) {
		this.user_verif_answer = newValue;
	}
	/**
	 * Setter method for user_verif_question
	 * @param newValue java.lang.String
	 */
	public void setUser_verif_question(java.lang.String newValue) {
		this.user_verif_question = newValue;
	}
	/**
	 * unsetEntityContext
	 */
	public void unsetEntityContext() throws java.rmi.RemoteException {
		myEntityCtx = null;
	}
  /**
   * ejbCreate method for a CMP entity bean.
   */
  public com.ardais.bigr.es.beans.ArdaisuserKey ejbCreate(
    java.lang.String ardais_user_id,
    com.ardais.bigr.es.beans.Ardaisaccount argArdaisaccount,
    java.lang.String argPasswordPolicyCid)
    throws javax.ejb.CreateException {
    _fieldDefaultValues.assignTo(this);
    _initLinks();
    this.ardais_user_id = ardais_user_id;
    setPasswordPolicyCid(argPasswordPolicyCid);
    setPasswordLastChangeDate(new java.sql.Timestamp(System.currentTimeMillis()));
    try {
      setArdaisaccount(argArdaisaccount);
    }
    catch (java.rmi.RemoteException remoteEx) {
      throw new javax.ejb.CreateException(remoteEx.getMessage());
    }
    return null;
  }
  /**
   * ejbPostCreate
   */
  public void ejbPostCreate(
    java.lang.String ardais_user_id,
    com.ardais.bigr.es.beans.Ardaisaccount argArdaisaccount,
    java.lang.String argPasswordPolicyCid)
    throws javax.ejb.CreateException {
    try {
      setArdaisaccount(argArdaisaccount);
    }
    catch (java.rmi.RemoteException remoteEx) {
      throw new javax.ejb.CreateException(remoteEx.getMessage());
    }
  }
	/**
	 * This method was generated for supporting the relationship role named shoppingcart.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public void secondaryAddShoppingcart(
		com.ardais.bigr.es.beans.Shoppingcart aShoppingcart)
		throws java.rmi.RemoteException {
		this.getShoppingcartLink().secondaryAddElement(aShoppingcart);
	}
	/**
	 * This method was generated for supporting the relationship role named shoppingcart.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public void secondaryRemoveShoppingcart(
		com.ardais.bigr.es.beans.Shoppingcart aShoppingcart)
		throws java.rmi.RemoteException {
		this.getShoppingcartLink().secondaryRemoveElement(aShoppingcart);
	}
	/**
	 * This method was generated for supporting the relationship role named shoppingcart.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	protected com
		.ibm
		.ivj
		.ejb
		.associations
		.interfaces
		.ManyLink getShoppingcartLink() {
		if (shoppingcartLink == null)
			shoppingcartLink = new ArdaisuserToShoppingcartLink(this);
		return shoppingcartLink;
	}
	/**
	 * This method was generated for supporting the relationship role named shoppingcart.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public java.util.Enumeration getShoppingcart()
		throws java.rmi.RemoteException, javax.ejb.FinderException {
		return this.getShoppingcartLink().enumerationValue();
	}
  /**
   * This method was generated for supporting the relationship role named ardaisaccount.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void setArdaisaccount(com.ardais.bigr.es.beans.Ardaisaccount anArdaisaccount)
    throws java.rmi.RemoteException {
    this.getArdaisaccountLink().set(anArdaisaccount);
  }
  /**
   * This method was generated for supporting the relationship role named ardaisaccount.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  protected com.ibm.ivj.ejb.associations.interfaces.SingleLink getArdaisaccountLink() {
    if (ardaisaccountLink == null)
      ardaisaccountLink = new ArdaisuserToArdaisaccountLink(this);
    return ardaisaccountLink;
  }
  /**
   * This method was generated for supporting the relationship role named ardaisaccount.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public com.ardais.bigr.es.beans.ArdaisaccountKey getArdaisaccountKey() {
    com.ardais.bigr.es.beans.ArdaisaccountKey temp =
      new com.ardais.bigr.es.beans.ArdaisaccountKey();
    boolean ardaisaccount_NULLTEST = true;
    ardaisaccount_NULLTEST &= (ardaisaccount_ardais_acct_key == null);
    temp.ardais_acct_key = ardaisaccount_ardais_acct_key;
    if (ardaisaccount_NULLTEST)
      temp = null;
    return temp;
  }
  /**
   * This method was generated for supporting the relationship role named ardaisaccount.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void privateSetArdaisaccountKey(com.ardais.bigr.es.beans.ArdaisaccountKey inKey) {
    boolean ardaisaccount_NULLTEST = (inKey == null);
    ardaisaccount_ardais_acct_key = (ardaisaccount_NULLTEST) ? null : inKey.ardais_acct_key;
  }
  /**
   * This method was generated for supporting the relationship role named ardaisaccount.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public com.ardais.bigr.es.beans.Ardaisaccount getArdaisaccount()
    throws java.rmi.RemoteException, javax.ejb.FinderException {
    return (com.ardais.bigr.es.beans.Ardaisaccount) this.getArdaisaccountLink().value();
  }
  /**
   * Get accessor for persistent attribute: passwordPolicyCid
   */
  public java.lang.String getPasswordPolicyCid() {
    return passwordPolicyCid;
  }
  /**
   * Set accessor for persistent attribute: passwordPolicyCid
   */
  public void setPasswordPolicyCid(java.lang.String newPasswordPolicyCid) {
    passwordPolicyCid = newPasswordPolicyCid;
  }
  /**
   * Get accessor for persistent attribute: passwordLastChangeDate
   */
  public java.sql.Timestamp getPasswordLastChangeDate() {
    return passwordLastChangeDate;
  }
  /**
   * Set accessor for persistent attribute: passwordLastChangeDate
   */
  public void setPasswordLastChangeDate(java.sql.Timestamp newPasswordLastChangeDate) {
    passwordLastChangeDate = newPasswordLastChangeDate;
  }
  /**
   * _copyFromEJB
   */
  public java.util.Hashtable _copyFromEJB() {
    com.ibm.ivj.ejb.runtime.AccessBeanHashtable h = new com.ibm.ivj.ejb.runtime.AccessBeanHashtable();

    h.put("user_phone_ext", getUser_phone_ext());
    h.put("password", getPassword());
    h.put("user_lastname", getUser_lastname());
    h.put("update_date", getUpdate_date());
    h.put("user_active_ind", getUser_active_ind());
    h.put("updated_by", getUpdated_by());
    h.put("user_affiliation", getUser_affiliation());
    h.put("created_by", getCreated_by());
    h.put("user_phone_number", getUser_phone_number());
    h.put("user_title", getUser_title());
    h.put("user_verif_question", getUser_verif_question());
    h.put("user_firstname", getUser_firstname());
    h.put("create_date", getCreate_date());
    h.put("ardaisaccountKey", getArdaisaccountKey());
    h.put("user_fax_number", getUser_fax_number());
    h.put("passwordPolicyCid", getPasswordPolicyCid());
    h.put("user_verif_answer", getUser_verif_answer());
    h.put("user_functional_title", getUser_functional_title());
    h.put("user_email_address", getUser_email_address());
    h.put("user_address_id", getUser_address_id());
    h.put("user_mobile_number", getUser_mobile_number());
    h.put("passwordLastChangeDate", getPasswordLastChangeDate());
    h.put("user_department", getUser_department());
    h.put("consecutive_failed_answers", getConsecutive_failed_answers());
    h.put("consecutive_failed_logins", getConsecutive_failed_logins());
    h.put("__Key", getEntityContext().getPrimaryKey());

    return h;
  }
  /**
   * _copyToEJB
   */
  public void _copyToEJB(java.util.Hashtable h) {
    java.lang.String localUser_phone_ext = (java.lang.String) h.get("user_phone_ext");
    java.lang.String localPassword = (java.lang.String) h.get("password");
    java.lang.String localUser_lastname = (java.lang.String) h.get("user_lastname");
    java.sql.Timestamp localUpdate_date = (java.sql.Timestamp) h.get("update_date");
    java.lang.String localUser_active_ind = (java.lang.String) h.get("user_active_ind");
    java.lang.String localUpdated_by = (java.lang.String) h.get("updated_by");
    java.lang.String localUser_affiliation = (java.lang.String) h.get("user_affiliation");
    java.lang.String localCreated_by = (java.lang.String) h.get("created_by");
    java.lang.String localUser_phone_number = (java.lang.String) h.get("user_phone_number");
    java.lang.String localUser_title = (java.lang.String) h.get("user_title");
    java.lang.String localUser_verif_question = (java.lang.String) h.get("user_verif_question");
    java.lang.String localUser_firstname = (java.lang.String) h.get("user_firstname");
    java.sql.Timestamp localCreate_date = (java.sql.Timestamp) h.get("create_date");
    java.lang.String localUser_fax_number = (java.lang.String) h.get("user_fax_number");
    java.lang.String localPasswordPolicyCid = (java.lang.String) h.get("passwordPolicyCid");
    java.lang.String localUser_verif_answer = (java.lang.String) h.get("user_verif_answer");
    java.lang.String localUser_functional_title = (java.lang.String) h.get("user_functional_title");
    java.lang.String localUser_email_address = (java.lang.String) h.get("user_email_address");
    java.math.BigDecimal localUser_address_id = (java.math.BigDecimal) h.get("user_address_id");
    java.lang.String localUser_mobile_number = (java.lang.String) h.get("user_mobile_number");
    java.sql.Timestamp localPasswordLastChangeDate = (java.sql.Timestamp) h
      .get("passwordLastChangeDate");
    java.lang.String localUser_department = (java.lang.String) h.get("user_department");
    java.lang.Integer localConsecutive_failed_answers = (java.lang.Integer) h
      .get("consecutive_failed_answers");
    java.lang.Integer localConsecutive_failed_logins = (java.lang.Integer) h
      .get("consecutive_failed_logins");

    if (h.containsKey("user_phone_ext"))
      setUser_phone_ext((localUser_phone_ext));
    if (h.containsKey("password"))
      setPassword((localPassword));
    if (h.containsKey("user_lastname"))
      setUser_lastname((localUser_lastname));
    if (h.containsKey("update_date"))
      setUpdate_date((localUpdate_date));
    if (h.containsKey("user_active_ind"))
      setUser_active_ind((localUser_active_ind));
    if (h.containsKey("updated_by"))
      setUpdated_by((localUpdated_by));
    if (h.containsKey("user_affiliation"))
      setUser_affiliation((localUser_affiliation));
    if (h.containsKey("created_by"))
      setCreated_by((localCreated_by));
    if (h.containsKey("user_phone_number"))
      setUser_phone_number((localUser_phone_number));
    if (h.containsKey("user_title"))
      setUser_title((localUser_title));
    if (h.containsKey("user_verif_question"))
      setUser_verif_question((localUser_verif_question));
    if (h.containsKey("user_firstname"))
      setUser_firstname((localUser_firstname));
    if (h.containsKey("create_date"))
      setCreate_date((localCreate_date));
    if (h.containsKey("user_fax_number"))
      setUser_fax_number((localUser_fax_number));
    if (h.containsKey("passwordPolicyCid"))
      setPasswordPolicyCid((localPasswordPolicyCid));
    if (h.containsKey("user_verif_answer"))
      setUser_verif_answer((localUser_verif_answer));
    if (h.containsKey("user_functional_title"))
      setUser_functional_title((localUser_functional_title));
    if (h.containsKey("user_email_address"))
      setUser_email_address((localUser_email_address));
    if (h.containsKey("user_address_id"))
      setUser_address_id((localUser_address_id));
    if (h.containsKey("user_mobile_number"))
      setUser_mobile_number((localUser_mobile_number));
    if (h.containsKey("passwordLastChangeDate"))
      setPasswordLastChangeDate((localPasswordLastChangeDate));
    if (h.containsKey("user_department"))
      setUser_department((localUser_department));
    if (h.containsKey("consecutive_failed_answers"))
      setConsecutive_failed_answers((localConsecutive_failed_answers));
    if (h.containsKey("consecutive_failed_logins"))
      setConsecutive_failed_logins((localConsecutive_failed_logins));
  }
  /**
   * Get accessor for persistent attribute: user_department
   */
  public java.lang.String getUser_department() {
    return user_department;
  }
  /**
   * Set accessor for persistent attribute: user_department
   */
  public void setUser_department(java.lang.String newUser_department) {
    user_department = newUser_department;
  }
  /**
   * Get accessor for persistent attribute: consecutive_failed_logins
   */
  public java.lang.Integer getConsecutive_failed_logins() {
    return consecutive_failed_logins;
  }
  /**
   * Set accessor for persistent attribute: consecutive_failed_logins
   */
  public void setConsecutive_failed_logins(java.lang.Integer newConsecutive_failed_logins) {
    consecutive_failed_logins = newConsecutive_failed_logins;
  }
  /**
   * Get accessor for persistent attribute: consecutive_failed_answers
   */
  public java.lang.Integer getConsecutive_failed_answers() {
    return consecutive_failed_answers;
  }
  /**
   * Set accessor for persistent attribute: consecutive_failed_answers
   */
  public void setConsecutive_failed_answers(java.lang.Integer newConsecutive_failed_answers) {
    consecutive_failed_answers = newConsecutive_failed_answers;
  }
}