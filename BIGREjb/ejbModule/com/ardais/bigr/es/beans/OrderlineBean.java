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
import com.ibm.ivj.ejb.associations.interfaces.SingleLink;
import com.ibm.ivj.ejb.associations.links.Link;
import com.ibm.ivj.ejb.runtime.AccessBeanHashtable;
/**
 * This is an Entity Bean class with CMP fields
 */
public class OrderlineBean implements EntityBean {
	public static final java.math.BigDecimal DEFAULT_order_line_number = null;
	public static final java.math.BigDecimal DEFAULT_ardaisorder_order_number = null;
	public static final java.math.BigDecimal DEFAULT_order_line_amount = null;
	public static final String DEFAULT_barcode_id = null;
	public static final String DEFAULT_consortium_ind = null;

	public java.math.BigDecimal order_line_number;
  /**
   * Implementation field for persistent attribute: ardaisorder_order_number
   */
  public java.math.BigDecimal ardaisorder_order_number;
	public java.math.BigDecimal order_line_amount;
	public String barcode_id;
	public String consortium_ind;

	private javax.ejb.EntityContext entityContext = null;
	final static long serialVersionUID = 3206093459760846163L;
  private transient com.ibm.ivj.ejb.associations.interfaces.SingleLink ardaisorderLink;
	private static final com.ardais.bigr.api.CMPDefaultValues _fieldDefaultValues =
		new com.ardais.bigr.api.CMPDefaultValues(com.ardais.bigr.es.beans.OrderlineBean.class);
	private javax.ejb.EntityContext myEntityCtx;
  /**
   * _copyFromEJB
   */
  public java.util.Hashtable _copyFromEJB() {
    com.ibm.ivj.ejb.runtime.AccessBeanHashtable h = new com.ibm.ivj.ejb.runtime.AccessBeanHashtable();

    h.put("barcode_id", getBarcode_id());
    h.put("order_line_amount", getOrder_line_amount());
    h.put("consortium_ind", getConsortium_ind());
    h.put("ardaisorderKey", getArdaisorderKey());
    h.put("__Key", getEntityContext().getPrimaryKey());

    return h;
  }
  /**
   * _copyToEJB
   */
  public void _copyToEJB(java.util.Hashtable h) {
    java.lang.String localBarcode_id = (java.lang.String) h.get("barcode_id");
    java.math.BigDecimal localOrder_line_amount = (java.math.BigDecimal) h.get("order_line_amount");
    java.lang.String localConsortium_ind = (java.lang.String) h.get("consortium_ind");

    if (h.containsKey("barcode_id"))
      setBarcode_id((localBarcode_id));
    if (h.containsKey("order_line_amount"))
      setOrder_line_amount((localOrder_line_amount));
    if (h.containsKey("consortium_ind"))
      setConsortium_ind((localConsortium_ind));
  }
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
	 * @param argOrder_line_number java.math.BigDecimal
	 * @param argArdaisorder com.ardais.bigr.es.beans.ArdaisorderKey
	 * @exception javax.ejb.CreateException The exception description.
	 */
	public OrderlineKey ejbCreate(
		java.math.BigDecimal argOrder_line_number,
		com.ardais.bigr.es.beans.ArdaisorderKey argArdaisorder)
		throws javax.ejb.CreateException, EJBException {
		_fieldDefaultValues.assignTo(this);
		_initLinks();
		// All CMP fields should be initialized here.
		order_line_number = argOrder_line_number;
		boolean ardaisorder_NULLTEST = (argArdaisorder == null);
		if (ardaisorder_NULLTEST)
			ardaisorder_order_number = null;
		else
			ardaisorder_order_number = argArdaisorder.order_number;
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
	 * @param argOrder_line_number java.math.BigDecimal
	 * @param argArdaisorder com.ardais.bigr.es.beans.ArdaisorderKey
	 */
	public void ejbPostCreate(
		java.math.BigDecimal argOrder_line_number,
		com.ardais.bigr.es.beans.ArdaisorderKey argArdaisorder)
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
      ardaisorderLink = new OrderlineToArdaisorderLink(this);
    return ardaisorderLink;
  }
	/**
	 * Getter method for barcode_id
	 * @return java.lang.String
	 */
	public java.lang.String getBarcode_id() {
		return barcode_id;
	}
	/**
	 * Getter method for consortium_ind
	 */
	public java.lang.String getConsortium_ind() {
		return consortium_ind;
	}
	/**
	 * getEntityContext
	 */
	public javax.ejb.EntityContext getEntityContext() {
		return myEntityCtx;
	}
	/**
	 * Getter method for order_line_amount
	 * @return java.math.BigDecimal
	 */
	public java.math.BigDecimal getOrder_line_amount() {
		return order_line_amount;
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
	 * Setter method for barcode_id
	 * @param newValue java.lang.String
	 */
	public void setBarcode_id(java.lang.String newValue) {
		this.barcode_id = newValue;
	}
	/**
	 * Setter method for consortium_ind
	 */
	public void setConsortium_ind(java.lang.String newValue) {
		this.consortium_ind = newValue;
	}
	/**
	 * setEntityContext
	 */
	public void setEntityContext(javax.ejb.EntityContext ctx)
		throws java.rmi.RemoteException {
		myEntityCtx = ctx;
	}
	/**
	 * Setter method for order_line_amount
	 * @param newValue java.math.BigDecimal
	 */
	public void setOrder_line_amount(java.math.BigDecimal newValue) {
		this.order_line_amount = newValue;
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
  public com.ardais.bigr.es.beans.OrderlineKey ejbCreate(
    java.math.BigDecimal order_line_number,
    com.ardais.bigr.es.beans.Ardaisorder argArdaisorder)
    throws javax.ejb.CreateException {
    _fieldDefaultValues.assignTo(this);
    _initLinks();
    this.order_line_number = order_line_number;
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
    java.math.BigDecimal order_line_number,
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