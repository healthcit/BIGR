package com.ardais.bigr.es.beans;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Timestamp;
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
import com.ibm.ivj.ejb.associations.interfaces.SingleLink;
import com.ibm.ivj.ejb.associations.links.Link;
import com.ibm.ivj.ejb.runtime.AccessBeanHashtable;
/**
 * This is an Entity Bean class with CMP fields
 */
public class ArdaisorderstatusBean implements EntityBean {
	public static final String DEFAULT_order_status_id = null;
	public static final String DEFAULT_ardais_user_id = null;
	public static final String DEFAULT_ardais_acct_key = null;
	public static final java.math.BigDecimal DEFAULT_ardaisorder_order_number = null;
	public static final String DEFAULT_order_status_comment = null;
	public static final java.sql.Timestamp DEFAULT_order_status_date = null;

	public String order_status_id;
	public String ardais_user_id;
	public String ardais_acct_key;
  /**
   * Implementation field for persistent attribute: ardaisorder_order_number
   */
  public java.math.BigDecimal ardaisorder_order_number;
	public String order_status_comment;
	public java.sql.Timestamp order_status_date;

	private javax.ejb.EntityContext entityContext = null;
	final static long serialVersionUID = 3206093459760846163L;
  private transient com.ibm.ivj.ejb.associations.interfaces.SingleLink ardaisorderLink;
	private static final com.ardais.bigr.api.CMPDefaultValues _fieldDefaultValues =
		new com.ardais.bigr.api.CMPDefaultValues(com.ardais.bigr.es.beans.ArdaisorderstatusBean.class);
	private javax.ejb.EntityContext myEntityCtx;
	/**
	 * This method was generated for supporting the associations.
	 */
	protected java.util.Vector _getLinks() {
		java.util.Vector links = new java.util.Vector();
		links.add(getArdaisorderLink());
		return links;
	}
	/**
	 * This method was generated for supporting the associations.
	 */
	protected void _initLinks() {
		ardaisorderLink = null;
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
	 * @param argOrder_status_date java.sql.Timestamp
	 * @param argOrder_status_id java.lang.String
	 * @param argArdaisorder com.ardais.bigr.es.beans.ArdaisorderKey
	 * @param argArdais_acct_key java.lang.String
	 * @param argArdais_user_id java.lang.String
	 * @exception javax.ejb.CreateException The exception description.
	 */
	public ArdaisorderstatusKey ejbCreate(
		java.sql.Timestamp argOrder_status_date,
		java.lang.String argOrder_status_id,
		ArdaisorderKey argArdaisorder,
		java.lang.String argArdais_acct_key,
		java.lang.String argArdais_user_id)
		throws javax.ejb.CreateException, EJBException {
		_fieldDefaultValues.assignTo(this);
		_initLinks();
		// All CMP fields should be initialized here.
		order_status_date = argOrder_status_date;
		order_status_id = argOrder_status_id;
		boolean ardaisorder_NULLTEST = (argArdaisorder == null);
		if (ardaisorder_NULLTEST)
			ardaisorder_order_number = null;
		else
			ardaisorder_order_number = argArdaisorder.order_number;
		ardais_acct_key = argArdais_acct_key;
		ardais_user_id = argArdais_user_id;
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
	 * @param argOrder_status_date java.sql.Timestamp
	 * @param argOrder_status_id java.lang.String
	 * @param argArdaisorder com.ardais.bigr.es.beans.ArdaisorderKey
	 * @param argArdais_acct_key java.lang.String
	 * @param argArdais_user_id java.lang.String
	 */
	public void ejbPostCreate(
		java.sql.Timestamp argOrder_status_date,
		java.lang.String argOrder_status_id,
		ArdaisorderKey argArdaisorder,
		java.lang.String argArdais_acct_key,
		java.lang.String argArdais_user_id)
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
  public com.ardais.bigr.es.beans.Ardaisorder getArdaisorder()
    throws java.rmi.RemoteException, javax.ejb.FinderException {
    return (com.ardais.bigr.es.beans.Ardaisorder) this.getArdaisorderLink().value();
  }
  /**
   * This method was generated for supporting the relationship role named ardaisorder.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public com.ardais.bigr.es.beans.ArdaisorderKey getArdaisorderKey() {
    com.ardais.bigr.es.beans.ArdaisorderKey temp = new com.ardais.bigr.es.beans.ArdaisorderKey();
    boolean ardaisorder_NULLTEST = true;
    ardaisorder_NULLTEST &= (ardaisorder_order_number == null);
    temp.order_number = ardaisorder_order_number;
    if (ardaisorder_NULLTEST)
      temp = null;
    return temp;
  }
  /**
   * This method was generated for supporting the relationship role named ardaisorder.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  protected com.ibm.ivj.ejb.associations.interfaces.SingleLink getArdaisorderLink() {
    if (ardaisorderLink == null)
      ardaisorderLink = new ArdaisorderstatusToArdaisorderLink(this);
    return ardaisorderLink;
  }
	/**
	 * getEntityContext
	 */
	public javax.ejb.EntityContext getEntityContext() {
		return myEntityCtx;
	}
	/**
	 * Getter method for order_status_comment
	 * @return java.lang.String
	 */
	public java.lang.String getOrder_status_comment() {
		return order_status_comment;
	}
  /**
   * This method was generated for supporting the relationship role named ardaisorder.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void privateSetArdaisorderKey(com.ardais.bigr.es.beans.ArdaisorderKey inKey) {
    boolean ardaisorder_NULLTEST = (inKey == null);
    ardaisorder_order_number = (ardaisorder_NULLTEST) ? null : inKey.order_number;
  }
  /**
   * This method was generated for supporting the relationship role named ardaisorder.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void setArdaisorder(com.ardais.bigr.es.beans.Ardaisorder anArdaisorder)
    throws java.rmi.RemoteException {
    this.getArdaisorderLink().set(anArdaisorder);
  }
	/**
	 * setEntityContext
	 */
	public void setEntityContext(javax.ejb.EntityContext ctx)
		throws java.rmi.RemoteException {
		myEntityCtx = ctx;
	}
	/**
	 * Setter method for order_status_comment
	 * @param newValue java.lang.String
	 */
	public void setOrder_status_comment(java.lang.String newValue) {
		this.order_status_comment = newValue;
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
	public com.ardais.bigr.es.beans.ArdaisorderstatusKey ejbCreate(
		java.sql.Timestamp order_status_date,
		java.lang.String ardais_acct_key,
		java.lang.String ardais_user_id,
		java.lang.String order_status_id,
		com.ardais.bigr.es.beans.Ardaisorder argArdaisorder)
		throws javax.ejb.CreateException {
    _fieldDefaultValues.assignTo(this);
		_initLinks();
		this.order_status_date = order_status_date;
		this.ardais_acct_key = ardais_acct_key;
		this.ardais_user_id = ardais_user_id;
		this.order_status_id = order_status_id;
		try {
			setArdaisorder(argArdaisorder);
		} catch (java.rmi.RemoteException remoteEx) {
			throw new javax.ejb.CreateException(remoteEx.getMessage());
		}
		return null;
	}
	/**
	 * ejbPostCreate
	 */
	public void ejbPostCreate(
		java.sql.Timestamp order_status_date,
		java.lang.String ardais_acct_key,
		java.lang.String ardais_user_id,
		java.lang.String order_status_id,
		com.ardais.bigr.es.beans.Ardaisorder argArdaisorder)
		throws javax.ejb.CreateException {
		try {
			setArdaisorder(argArdaisorder);
		} catch (java.rmi.RemoteException remoteEx) {
			throw new javax.ejb.CreateException(remoteEx.getMessage());
		}
	}
  /**
   * _copyFromEJB
   */
  public java.util.Hashtable _copyFromEJB() {
    com.ibm.ivj.ejb.runtime.AccessBeanHashtable h = new com.ibm.ivj.ejb.runtime.AccessBeanHashtable();

    h.put("order_status_comment", getOrder_status_comment());
    h.put("ardaisorderKey", getArdaisorderKey());
    h.put("__Key", getEntityContext().getPrimaryKey());

    return h;
  }
  /**
   * _copyToEJB
   */
  public void _copyToEJB(java.util.Hashtable h) {
    java.lang.String localOrder_status_comment = (java.lang.String) h.get("order_status_comment");

    if (h.containsKey("order_status_comment"))
      setOrder_status_comment((localOrder_status_comment));
  }
  /**
   * ejbCreate method for a CMP entity bean.
   */
  public com.ardais.bigr.es.beans.ArdaisorderstatusKey ejbCreate(
    java.lang.String ardais_acct_key,
    java.lang.String order_status_id,
    java.sql.Timestamp order_status_date,
    java.lang.String ardais_user_id,
    com.ardais.bigr.es.beans.Ardaisorder argArdaisorder)
    throws javax.ejb.CreateException {
    _fieldDefaultValues.assignTo(this);
    _initLinks();
    this.ardais_acct_key = ardais_acct_key;
    this.order_status_id = order_status_id;
    this.order_status_date = order_status_date;
    this.ardais_user_id = ardais_user_id;
    try {
      setArdaisorder(argArdaisorder);
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
    java.lang.String ardais_acct_key,
    java.lang.String order_status_id,
    java.sql.Timestamp order_status_date,
    java.lang.String ardais_user_id,
    com.ardais.bigr.es.beans.Ardaisorder argArdaisorder)
    throws javax.ejb.CreateException {
    try {
      setArdaisorder(argArdaisorder);
    }
    catch (java.rmi.RemoteException remoteEx) {
      throw new javax.ejb.CreateException(remoteEx.getMessage());
    }
  }
}