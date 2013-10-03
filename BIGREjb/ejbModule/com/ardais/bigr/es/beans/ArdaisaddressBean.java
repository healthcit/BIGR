package com.ardais.bigr.es.beans;

import java.math.BigDecimal;
import java.rmi.RemoteException;
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
import com.ibm.ivj.ejb.associations.links.Link;
import com.ibm.ivj.ejb.runtime.AccessBeanHashtable;
/**
 * This is an Entity Bean class with CMP fields
 */
public class ArdaisaddressBean implements EntityBean {
	public static final java.math.BigDecimal DEFAULT_address_id = null;
	public static final String DEFAULT_ardais_acct_key = null;
	public static final String DEFAULT_address_type = null;
	public static final String DEFAULT_address_1 = null;
	public static final String DEFAULT_address_2 = null;
	public static final String DEFAULT_addr_city = null;
	public static final String DEFAULT_addr_state = null;
	public static final String DEFAULT_addr_zip_code = null;
	public static final String DEFAULT_addr_country = null;

	public java.math.BigDecimal address_id;
	public String ardais_acct_key;
	public String address_type;
	public String address_1;
	public String address_2;
	public String addr_city;
	public String addr_state;
	public String addr_zip_code;
	public String addr_country;
	private javax.ejb.EntityContext entityContext = null;
	final static long serialVersionUID = 3206093459760846163L;

	private static final com.ardais.bigr.api.CMPDefaultValues _fieldDefaultValues =
		new com.ardais.bigr.api.CMPDefaultValues(com.ardais.bigr.es.beans.ArdaisaddressBean.class);
  /**
   * _copyFromEJB
   */
  public java.util.Hashtable _copyFromEJB() {
    com.ibm.ivj.ejb.runtime.AccessBeanHashtable h = new com.ibm.ivj.ejb.runtime.AccessBeanHashtable();

    h.put("address_type", getAddress_type());
    h.put("addr_country", getAddr_country());
    h.put("addr_state", getAddr_state());
    h.put("address_2", getAddress_2());
    h.put("address_1", getAddress_1());
    h.put("addr_city", getAddr_city());
    h.put("addr_zip_code", getAddr_zip_code());
    h.put("__Key", getEntityContext().getPrimaryKey());

    return h;
  }
  /**
   * _copyToEJB
   */
  public void _copyToEJB(java.util.Hashtable h) {
    java.lang.String localAddress_type = (java.lang.String) h.get("address_type");
    java.lang.String localAddr_country = (java.lang.String) h.get("addr_country");
    java.lang.String localAddr_state = (java.lang.String) h.get("addr_state");
    java.lang.String localAddress_2 = (java.lang.String) h.get("address_2");
    java.lang.String localAddress_1 = (java.lang.String) h.get("address_1");
    java.lang.String localAddr_city = (java.lang.String) h.get("addr_city");
    java.lang.String localAddr_zip_code = (java.lang.String) h.get("addr_zip_code");

    if (h.containsKey("address_type"))
      setAddress_type((localAddress_type));
    if (h.containsKey("addr_country"))
      setAddr_country((localAddr_country));
    if (h.containsKey("addr_state"))
      setAddr_state((localAddr_state));
    if (h.containsKey("address_2"))
      setAddress_2((localAddress_2));
    if (h.containsKey("address_1"))
      setAddress_1((localAddress_1));
    if (h.containsKey("addr_city"))
      setAddr_city((localAddr_city));
    if (h.containsKey("addr_zip_code"))
      setAddr_zip_code((localAddr_zip_code));
  }
	/**
	 * This method was generated for supporting the associations.
	 * @return java.util.Vector
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	protected java.util.Vector _getLinks() {
		java.util.Vector links = new java.util.Vector();
		return links;
	}
	/**
	 * This method was generated for supporting the associations.
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	protected void _initLinks() {
	}
	/**
	 * This method was generated for supporting the associations.
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	protected void _removeLinks() throws java.rmi.RemoteException, javax.ejb.RemoveException {
		java.util.Enumeration links = _getLinks().elements();
		while (links.hasMoreElements()) {
			try {
				((com.ibm.ivj.ejb.associations.interfaces.Link) (links.nextElement())).remove();
			} catch (javax.ejb.FinderException e) {
			} //Consume Finder error since I am going away
		}
	}
	/**
	 * ejbActivate method comment
	 */
	public void ejbActivate() throws java.rmi.RemoteException {
		_initLinks();
	}
	/**
	 * ejbCreate method for a CMP entity bean
	 * @param argAddress_id java.math.BigDecimal
	 * @param argArdais_acct_key java.lang.String
	 * @exception javax.ejb.CreateException The exception description.
	 */
	public ArdaisaddressKey ejbCreate(java.math.BigDecimal argAddress_id, java.lang.String argArdais_acct_key)
		throws javax.ejb.CreateException, EJBException {
		_fieldDefaultValues.assignTo(this);
		_initLinks();
		// All CMP fields should be initialized here.
		address_id = argAddress_id;
		ardais_acct_key = argArdais_acct_key;
		return null;
	}
	/**
	 * ejbCreate method for a CMP entity bean
	 * @param argAddress_id java.math.BigDecimal
	 * @param argArdais_acct_key java.lang.String
	 * @exception javax.ejb.CreateException The exception description.
	 */
	public ArdaisaddressKey ejbCreate(
		java.math.BigDecimal argAddress_id,
		java.lang.String argArdais_acct_key,
		java.lang.String argAddress1)
		throws javax.ejb.CreateException, EJBException {
		_fieldDefaultValues.assignTo(this);
		_initLinks();
		// All CMP fields should be initialized here.
		address_id = argAddress_id;
		address_1 = argAddress1;
		ardais_acct_key = argArdais_acct_key;
		return null;
	}
	/**
	 * ejbLoad method comment
	 */
	public void ejbLoad() throws java.rmi.RemoteException {
		_initLinks();
	}
	/**
	 * ejbPassivate method comment
	 */
	public void ejbPassivate() throws java.rmi.RemoteException {
	}
	/**
	 * ejbPostCreate method for a CMP entity bean
	 * @param argAddress_id java.math.BigDecimal
	 * @param argArdais_acct_key java.lang.String
	 */
	public void ejbPostCreate(java.math.BigDecimal argAddress_id, java.lang.String argArdais_acct_key)
		throws EJBException {
	}
	/**
	 * ejbPostCreate method for a CMP entity bean
	 * @param argAddress_id java.math.BigDecimal
	 * @param argArdais_acct_key java.lang.String
	 */
	public void ejbPostCreate(
		java.math.BigDecimal argAddress_id,
		java.lang.String argArdais_acct_key,
		java.lang.String argAddress1)
		throws EJBException {
	}
	/**
	 * ejbRemove method comment
	 */
	public void ejbRemove() throws java.rmi.RemoteException, javax.ejb.RemoveException {
		_removeLinks();
	}
	/**
	 * ejbStore method comment
	 */
	public void ejbStore() throws java.rmi.RemoteException {
	}
	/**
	 * Getter method for addr_city
	 * @return java.lang.String
	 */
	public java.lang.String getAddr_city() {
		return addr_city;
	}
	/**
	 * Getter method for addr_country
	 * @return java.lang.String
	 */
	public java.lang.String getAddr_country() {
		return addr_country;
	}
	/**
	 * Getter method for addr_state
	 * @return java.lang.String
	 */
	public java.lang.String getAddr_state() {
		return addr_state;
	}
	/**
	 * Getter method for addr_zip_code
	 * @return java.lang.String
	 */
	public java.lang.String getAddr_zip_code() {
		return addr_zip_code;
	}
	/**
	 * Getter method for address_1
	 * @return java.lang.String
	 */
	public java.lang.String getAddress_1() {
		return address_1;
	}
	/**
	 * Getter method for address_2
	 * @return java.lang.String
	 */
	public java.lang.String getAddress_2() {
		return address_2;
	}
	/**
	 * Getter method for address_type
	 * @return java.lang.String
	 */
	public java.lang.String getAddress_type() {
		return address_type;
	}
	/**
	 * getEntityContext method comment
	 * @return javax.ejb.EntityContext
	 */
	public javax.ejb.EntityContext getEntityContext() {
		return entityContext;
	}
	/**
	 * Setter method for addr_city
	 * @param newValue java.lang.String
	 */
	public void setAddr_city(java.lang.String newValue) {
		this.addr_city = newValue;
	}
	/**
	 * Setter method for addr_country
	 * @param newValue java.lang.String
	 */
	public void setAddr_country(java.lang.String newValue) {
		this.addr_country = newValue;
	}
	/**
	 * Setter method for addr_state
	 * @param newValue java.lang.String
	 */
	public void setAddr_state(java.lang.String newValue) {
		this.addr_state = newValue;
	}
	/**
	 * Setter method for addr_zip_code
	 * @param newValue java.lang.String
	 */
	public void setAddr_zip_code(java.lang.String newValue) {
		this.addr_zip_code = newValue;
	}
	/**
	 * Setter method for address_1
	 * @param newValue java.lang.String
	 */
	public void setAddress_1(java.lang.String newValue) {
		this.address_1 = newValue;
	}
	/**
	 * Setter method for address_2
	 * @param newValue java.lang.String
	 */
	public void setAddress_2(java.lang.String newValue) {
		this.address_2 = newValue;
	}
	/**
	 * Setter method for address_type
	 * @param newValue java.lang.String
	 */
	public void setAddress_type(java.lang.String newValue) {
		this.address_type = newValue;
	}
	/**
	 * setEntityContext method comment
	 * @param ctx javax.ejb.EntityContext
	 */
	public void setEntityContext(javax.ejb.EntityContext ctx) throws java.rmi.RemoteException {
		entityContext = ctx;
	}
	/**
	 * unsetEntityContext method comment
	 */
	public void unsetEntityContext() throws java.rmi.RemoteException {
		entityContext = null;
	}
}