package com.ardais.bigr.es.beans;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.Enumeration;
import java.util.Hashtable;
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
public class ArdaisorderBean implements EntityBean {

	public static final java.math.BigDecimal DEFAULT_order_number = null;
	public static final String DEFAULT_ardaisuser_ardaisaccount_ardais_acct_key = null;
	public static final String DEFAULT_ardaisuser_ardais_user_id = null;
	public static final java.sql.Timestamp DEFAULT_order_date = null;
	public static final String DEFAULT_order_status = null;
	public static final java.math.BigDecimal DEFAULT_order_amount = null;
	public static final java.math.BigDecimal DEFAULT_bill_to_addr_id = null;
	public static final String DEFAULT_ship_instruction = null;
	public static final String DEFAULT_approval_user_id = null;
	public static final java.sql.Timestamp DEFAULT_approved_date = null;
	public static final String DEFAULT_order_po_number = null;

	public java.math.BigDecimal order_number;
  /**
   * Implementation field for persistent attribute: ardaisuser_ardaisaccount_ardais_acct_key
   */
  public java.lang.String ardaisuser_ardaisaccount_ardais_acct_key;
  /**
   * Implementation field for persistent attribute: ardaisuser_ardais_user_id
   */
  public java.lang.String ardaisuser_ardais_user_id;
	public java.sql.Timestamp order_date;
	public String order_status;
	public java.math.BigDecimal order_amount;
	public java.math.BigDecimal bill_to_addr_id;
	public String ship_instruction;
	public String approval_user_id;
  /**
   * Implementation field for persistent attribute: approved_date
   */
  public java.sql.Timestamp approved_date;
	public String order_po_number;

	private javax.ejb.EntityContext entityContext = null;
  
	final static long serialVersionUID = 3206093459760846163L;
  
	private transient com.ibm.ivj.ejb.associations.interfaces.SingleLink ardaisuserLink;
	private transient com
		.ibm
		.ivj
		.ejb
		.associations
		.interfaces
		.ManyLink orderlineLink;
	private transient com
		.ibm
		.ivj
		.ejb
		.associations
		.interfaces
		.ManyLink ardaisorderstatusLink;
    
	private javax.ejb.EntityContext myEntityCtx;

  private static final com.ardais.bigr.api.CMPDefaultValues _fieldDefaultValues =
    new com.ardais.bigr.api.CMPDefaultValues(com.ardais.bigr.es.beans.ArdaisorderBean.class);

	/**
	 * This method was generated for supporting the associations.
	 */
	protected java.util.Vector _getLinks() {
		java.util.Vector links = new java.util.Vector();
		links.add(getArdaisuserLink());
		links.add(getOrderlineLink());
		links.add(getArdaisorderstatusLink());
		return links;
	}
	/**
	 * This method was generated for supporting the associations.
	 */
	protected void _initLinks() {
		ardaisuserLink = null;
		orderlineLink = null;
		ardaisorderstatusLink = null;
	}
	/**
	 * This method was generated for supporting the associations.
	 */
	protected void _removeLinks()
		throws java.rmi.RemoteException, javax.ejb.RemoveException {
		java.util.List links = _getLinks();
		for (int i = 0; i < links.size(); i++) {
			try {
				((com.ibm.ivj.ejb.associations.interfaces.Link) links.get(i))
					.remove();
			} catch (javax.ejb.FinderException e) {
			} //Consume Finder error since I am going away
		}
	}
	/**
	 * ejbActivate
	 */
	public void ejbActivate() throws java.rmi.RemoteException {
		_initLinks();
	}
	/**
	 * ejbCreate method for a CMP entity bean
	 * @param argOrder_number java.math.BigDecimal
	 * @param argArdaisuser com.ardais.bigr.es.beans.ArdaisuserKey
	 * @exception javax.ejb.CreateException The exception description.
	 */
	public ArdaisorderKey ejbCreate(
		java.math.BigDecimal argOrder_number,
		com.ardais.bigr.es.beans.ArdaisuserKey argArdaisuser)
		throws javax.ejb.CreateException, EJBException {
		_fieldDefaultValues.assignTo(this);
		_initLinks();
		// All CMP fields should be initialized here.
		order_number = argOrder_number;
		boolean ardaisuser_NULLTEST = (argArdaisuser == null);
		if (ardaisuser_NULLTEST)
			ardaisuser_ardais_user_id = null;
		else
			ardaisuser_ardais_user_id = argArdaisuser.ardais_user_id;
		if (ardaisuser_NULLTEST)
			ardaisuser_ardaisaccount_ardais_acct_key = null;
		else
			ardaisuser_ardaisaccount_ardais_acct_key = argArdaisuser.ardaisaccount_ardais_acct_key;
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
	 * @param argOrder_number java.math.BigDecimal
	 * @param argArdaisuser com.ardais.bigr.es.beans.ArdaisuserKey
	 */
	public void ejbPostCreate(java.math.BigDecimal argOrder_number, com.ardais.bigr.es.beans.ArdaisuserKey argArdaisuser)
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
	 * Getter method for approval_user_id
	 */
	public java.lang.String getApproval_user_id() {
		return approval_user_id;
	}
	/**
	 * This method was generated for supporting the relationship role named ardaisorderstatus.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public java.util.Enumeration getArdaisorderstatus()
		throws java.rmi.RemoteException, javax.ejb.FinderException {
		return this.getArdaisorderstatusLink().enumerationValue();
	}
	/**
	 * This method was generated for supporting the relationship role named ardaisorderstatus.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	protected com
		.ibm
		.ivj
		.ejb
		.associations
		.interfaces
		.ManyLink getArdaisorderstatusLink() {
		if (ardaisorderstatusLink == null)
			ardaisorderstatusLink =
				new ArdaisorderToArdaisorderstatusLink(this);
		return ardaisorderstatusLink;
	}
  /**
   * This method was generated for supporting the relationship role named ardaisuser.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public com.ardais.bigr.es.beans.Ardaisuser getArdaisuser()
    throws java.rmi.RemoteException, javax.ejb.FinderException {
    return (com.ardais.bigr.es.beans.Ardaisuser) this.getArdaisuserLink().value();
  }
  /**
   * This method was generated for supporting the relationship role named ardaisuser.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public com.ardais.bigr.es.beans.ArdaisuserKey getArdaisuserKey() {
    com.ardais.bigr.es.beans.ArdaisuserKey temp = new com.ardais.bigr.es.beans.ArdaisuserKey();
    boolean ardaisuser_NULLTEST = true;
    ardaisuser_NULLTEST &= (ardaisuser_ardais_user_id == null);
    temp.ardais_user_id = ardaisuser_ardais_user_id;
    ardaisuser_NULLTEST &= (ardaisuser_ardaisaccount_ardais_acct_key == null);
    temp.ardaisaccount_ardais_acct_key = ardaisuser_ardaisaccount_ardais_acct_key;
    if (ardaisuser_NULLTEST)
      temp = null;
    return temp;
  }
  /**
   * This method was generated for supporting the relationship role named ardaisuser.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  protected com.ibm.ivj.ejb.associations.interfaces.SingleLink getArdaisuserLink() {
    if (ardaisuserLink == null)
      ardaisuserLink = new ArdaisorderToArdaisuserLink(this);
    return ardaisuserLink;
  }
	/**
	 * Getter method for bill_to_addr_id
	 */
	public java.math.BigDecimal getBill_to_addr_id() {
		return bill_to_addr_id;
	}
	/**
	 * getEntityContext
	 */
	public javax.ejb.EntityContext getEntityContext() {
		return myEntityCtx;
	}
	/**
	 * Getter method for order_amount
	 * @return java.math.BigDecimal
	 */
	public java.math.BigDecimal getOrder_amount() {
		return order_amount;
	}
	/**
	 * Getter method for order_date
	 * @return java.sql.Timestamp
	 */
	public java.sql.Timestamp getOrder_date() {
		return order_date;
	}
	/**
	 * Getter method for order_po_number
	 */
	public java.lang.String getOrder_po_number() {
		return order_po_number;
	}
	/**
	 * Getter method for order_status
	 * @return java.lang.String
	 */
	public java.lang.String getOrder_status() {
		return order_status;
	}
	/**
	 * This method was generated for supporting the relationship role named orderline.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public java.util.Enumeration getOrderline()
		throws java.rmi.RemoteException, javax.ejb.FinderException {
		return this.getOrderlineLink().enumerationValue();
	}
	/**
	 * This method was generated for supporting the relationship role named orderline.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	protected com
		.ibm
		.ivj
		.ejb
		.associations
		.interfaces
		.ManyLink getOrderlineLink() {
		if (orderlineLink == null)
			orderlineLink = new ArdaisorderToOrderlineLink(this);
		return orderlineLink;
	}
	/**
	 * Getter method for ship_instruction
	 * @return java.lang.String
	 */
	public java.lang.String getShip_instruction() {
		return ship_instruction;
	}
  /**
   * This method was generated for supporting the relationship role named ardaisuser.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void privateSetArdaisuserKey(com.ardais.bigr.es.beans.ArdaisuserKey inKey) {
    boolean ardaisuser_NULLTEST = (inKey == null);
    ardaisuser_ardais_user_id = (ardaisuser_NULLTEST) ? null : inKey.ardais_user_id;
    ardaisuser_ardaisaccount_ardais_acct_key =
      (ardaisuser_NULLTEST) ? null : inKey.ardaisaccount_ardais_acct_key;
  }
	/**
	 * This method was generated for supporting the relationship role named ardaisorderstatus.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public void secondaryAddArdaisorderstatus(
		com.ardais.bigr.es.beans.Ardaisorderstatus anArdaisorderstatus)
		throws java.rmi.RemoteException {
		this.getArdaisorderstatusLink().secondaryAddElement(
			anArdaisorderstatus);
	}
	/**
	 * This method was generated for supporting the relationship role named orderline.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public void secondaryAddOrderline(
		com.ardais.bigr.es.beans.Orderline anOrderline)
		throws java.rmi.RemoteException {
		this.getOrderlineLink().secondaryAddElement(anOrderline);
	}
	/**
	 * This method was generated for supporting the relationship role named ardaisorderstatus.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public void secondaryRemoveArdaisorderstatus(
		com.ardais.bigr.es.beans.Ardaisorderstatus anArdaisorderstatus)
		throws java.rmi.RemoteException {
		this.getArdaisorderstatusLink().secondaryRemoveElement(
			anArdaisorderstatus);
	}
	/**
	 * This method was generated for supporting the relationship role named orderline.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public void secondaryRemoveOrderline(
		com.ardais.bigr.es.beans.Orderline anOrderline)
		throws java.rmi.RemoteException {
		this.getOrderlineLink().secondaryRemoveElement(anOrderline);
	}
  /**
   * This method was generated for supporting the relationship role named ardaisuser.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void secondarySetArdaisuser(com.ardais.bigr.es.beans.Ardaisuser anArdaisuser)
    throws java.rmi.RemoteException {
    this.getArdaisuserLink().secondarySet(anArdaisuser);
  }
	/**
	 * Setter method for approval_user_id
	 */
	public void setApproval_user_id(java.lang.String newValue) {
		this.approval_user_id = newValue;
	}
  /**
   * This method was generated for supporting the relationship role named ardaisuser.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void setArdaisuser(com.ardais.bigr.es.beans.Ardaisuser anArdaisuser)
    throws java.rmi.RemoteException {
    this.getArdaisuserLink().set(anArdaisuser);
  }
	/**
	 * Setter method for bill_to_addr_id
	 */
	public void setBill_to_addr_id(java.math.BigDecimal newValue) {
		this.bill_to_addr_id = newValue;
	}
	/**
	 * setEntityContext
	 */
	public void setEntityContext(javax.ejb.EntityContext ctx)
		throws java.rmi.RemoteException {
		myEntityCtx = ctx;
	}
	/**
	 * Setter method for order_amount
	 * @param newValue java.math.BigDecimal
	 */
	public void setOrder_amount(java.math.BigDecimal newValue) {
		this.order_amount = newValue;
	}
	/**
	 * Setter method for order_date
	 * @param newValue java.sql.Timestamp
	 */
	public void setOrder_date(java.sql.Timestamp newValue) {
		this.order_date = newValue;
	}
	/**
	 * Setter method for order_po_number
	 */
	public void setOrder_po_number(java.lang.String newValue) {
		this.order_po_number = newValue;
	}
	/**
	 * Setter method for order_status
	 * @param newValue java.lang.String
	 */
	public void setOrder_status(java.lang.String newValue) {
		this.order_status = newValue;
	}
	/**
	 * Setter method for ship_instruction
	 * @param newValue java.lang.String
	 */
	public void setShip_instruction(java.lang.String newValue) {
		this.ship_instruction = newValue;
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
  public com.ardais.bigr.es.beans.ArdaisorderKey ejbCreate(
    java.math.BigDecimal order_number,
    com.ardais.bigr.es.beans.Ardaisuser argArdaisuser)
    throws javax.ejb.CreateException {
    _fieldDefaultValues.assignTo(this);
    _initLinks();
    this.order_number = order_number;
    try {
      setArdaisuser(argArdaisuser);
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
    java.math.BigDecimal order_number,
    com.ardais.bigr.es.beans.Ardaisuser argArdaisuser)
    throws javax.ejb.CreateException {
    try {
      setArdaisuser(argArdaisuser);
    }
    catch (java.rmi.RemoteException remoteEx) {
      throw new javax.ejb.CreateException(remoteEx.getMessage());
    }
  }
	/**
	 * Get accessor for persistent attribute: approved_date
	 */
	public java.sql.Timestamp getApproved_date() {
		return approved_date;
	}
	/**
	 * Set accessor for persistent attribute: approved_date
	 */
	public void setApproved_date(java.sql.Timestamp newApproved_date) {
		approved_date = newApproved_date;
	}
  /**
   * _copyFromEJB
   */
  public java.util.Hashtable _copyFromEJB() {
    com.ibm.ivj.ejb.runtime.AccessBeanHashtable h = new com.ibm.ivj.ejb.runtime.AccessBeanHashtable();

    h.put("order_po_number", getOrder_po_number());
    h.put("ardaisuserKey", getArdaisuserKey());
    h.put("bill_to_addr_id", getBill_to_addr_id());
    h.put("order_date", getOrder_date());
    h.put("approved_date", getApproved_date());
    h.put("order_status", getOrder_status());
    h.put("order_amount", getOrder_amount());
    h.put("ship_instruction", getShip_instruction());
    h.put("approval_user_id", getApproval_user_id());
    h.put("__Key", getEntityContext().getPrimaryKey());

    return h;
  }
  /**
   * _copyToEJB
   */
  public void _copyToEJB(java.util.Hashtable h) {
    java.lang.String localOrder_po_number = (java.lang.String) h.get("order_po_number");
    java.math.BigDecimal localBill_to_addr_id = (java.math.BigDecimal) h.get("bill_to_addr_id");
    java.sql.Timestamp localOrder_date = (java.sql.Timestamp) h.get("order_date");
    java.sql.Timestamp localApproved_date = (java.sql.Timestamp) h.get("approved_date");
    java.lang.String localOrder_status = (java.lang.String) h.get("order_status");
    java.math.BigDecimal localOrder_amount = (java.math.BigDecimal) h.get("order_amount");
    java.lang.String localShip_instruction = (java.lang.String) h.get("ship_instruction");
    java.lang.String localApproval_user_id = (java.lang.String) h.get("approval_user_id");

    if (h.containsKey("order_po_number"))
      setOrder_po_number((localOrder_po_number));
    if (h.containsKey("bill_to_addr_id"))
      setBill_to_addr_id((localBill_to_addr_id));
    if (h.containsKey("order_date"))
      setOrder_date((localOrder_date));
    if (h.containsKey("approved_date"))
      setApproved_date((localApproved_date));
    if (h.containsKey("order_status"))
      setOrder_status((localOrder_status));
    if (h.containsKey("order_amount"))
      setOrder_amount((localOrder_amount));
    if (h.containsKey("ship_instruction"))
      setShip_instruction((localShip_instruction));
    if (h.containsKey("approval_user_id"))
      setApproval_user_id((localApproval_user_id));
  }
}