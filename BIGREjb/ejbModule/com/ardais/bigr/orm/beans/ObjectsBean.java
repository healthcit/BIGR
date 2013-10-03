package com.ardais.bigr.orm.beans;
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
import com.ibm.ivj.ejb.associations.interfaces.ManyLink;
import com.ibm.ivj.ejb.associations.links.Link;
import com.ibm.ivj.ejb.runtime.AccessBeanHashtable;
/**
 * This is an Entity Bean class with CMP fields
 */
/*******************************************************************************************************
	Modified by 	Date		 	Modification
	Nagaraja Rao	03/27/2002		Changes made as described in MR #3508 to Initialize all CMP fields     
*******************************************************************************************************/
public class ObjectsBean implements EntityBean {

	public static final String DEFAULT_object_id = null;
	public static final String DEFAULT_object_description = null;
	public static final java.sql.Timestamp DEFAULT_create_date = null;
	public static final String DEFAULT_created_by = null;
	public static final java.sql.Timestamp DEFAULT_update_date = null;
	public static final String DEFAULT_updated_by = null;
	public static final String DEFAULT_long_description = null;
	public static final String DEFAULT_url = null;

	public String object_id;
	public String object_description;
	public java.sql.Timestamp create_date;
	public String created_by;
	public java.sql.Timestamp update_date;
	public String updated_by;
	public String long_description;
	public String url;

	private javax.ejb.EntityContext entityContext = null;
	final static long serialVersionUID = 3206093459760846163L;
	private transient com
		.ibm
		.ivj
		.ejb
		.associations
		.interfaces
		.ManyLink useraccessmoduleLink =
		null;
	private static final com.ardais.bigr.api.CMPDefaultValues _fieldDefaultValues =
		new com.ardais.bigr.api.CMPDefaultValues(
			com.ardais.bigr.orm.beans.ObjectsBean.class);

  /**
   * _copyFromEJB
   */
  public java.util.Hashtable _copyFromEJB() {
    com.ibm.ivj.ejb.runtime.AccessBeanHashtable h = new com.ibm.ivj.ejb.runtime.AccessBeanHashtable();

    h.put("create_date", getCreate_date());
    h.put("url", getUrl());
    h.put("update_date", getUpdate_date());
    h.put("updated_by", getUpdated_by());
    h.put("created_by", getCreated_by());
    h.put("object_description", getObject_description());
    h.put("long_description", getLong_description());
    h.put("__Key", getEntityContext().getPrimaryKey());

    return h;
  }
  /**
   * _copyToEJB
   */
  public void _copyToEJB(java.util.Hashtable h) {
    java.sql.Timestamp localCreate_date = (java.sql.Timestamp) h.get("create_date");
    java.lang.String localUrl = (java.lang.String) h.get("url");
    java.sql.Timestamp localUpdate_date = (java.sql.Timestamp) h.get("update_date");
    java.lang.String localUpdated_by = (java.lang.String) h.get("updated_by");
    java.lang.String localCreated_by = (java.lang.String) h.get("created_by");
    java.lang.String localObject_description = (java.lang.String) h.get("object_description");
    java.lang.String localLong_description = (java.lang.String) h.get("long_description");

    if (h.containsKey("create_date"))
      setCreate_date((localCreate_date));
    if (h.containsKey("url"))
      setUrl((localUrl));
    if (h.containsKey("update_date"))
      setUpdate_date((localUpdate_date));
    if (h.containsKey("updated_by"))
      setUpdated_by((localUpdated_by));
    if (h.containsKey("created_by"))
      setCreated_by((localCreated_by));
    if (h.containsKey("object_description"))
      setObject_description((localObject_description));
    if (h.containsKey("long_description"))
      setLong_description((localLong_description));
  }
  /**
   * This method was generated for supporting the associations.
   */
  protected java.util.Vector _getLinks() {
    java.util.Vector links = new java.util.Vector();
    links.add(getUseraccessmoduleLink());
    return links;
  }
  /**
   * This method was generated for supporting the associations.
   */
  protected void _initLinks() {
    useraccessmoduleLink = null;
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
	 * ejbActivate method comment
	 */
	public void ejbActivate() throws java.rmi.RemoteException {
		_initLinks();
	}

  /**
   * ejbCreate method for a CMP entity bean.
   */
  public com.ardais.bigr.orm.beans.ObjectsKey ejbCreate(java.lang.String object_id)
    throws javax.ejb.CreateException, EJBException {
    _initLinks();
    this.object_id = object_id;
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
   * ejbPostCreate
   */
  public void ejbPostCreate(java.lang.String object_id)
    throws javax.ejb.CreateException, javax.ejb.EJBException {
  }
	/**
	 * ejbRemove method comment
	 */
	public void ejbRemove()
		throws java.rmi.RemoteException, javax.ejb.RemoveException {
		_removeLinks();
	}

	/**
	 * ejbStore method comment
	 */
	public void ejbStore() throws java.rmi.RemoteException {
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
	 * getEntityContext method comment
	 * @return javax.ejb.EntityContext
	 */
	public javax.ejb.EntityContext getEntityContext() {
		return entityContext;
	}

	/**
	 * Getter method for long_description
	 * @return java.lang.String
	 */
	public java.lang.String getLong_description() {
		return long_description;
	}

	/**
	 * Getter method for object_description
	 * @return java.lang.String
	 */
	public java.lang.String getObject_description() {
		return object_description;
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
	 * Getter method for url
	 * @return java.lang.String
	 */
	public java.lang.String getUrl() {
		return url;
	}

	/**
	 * This method was generated for supporting the association named Useraccessmodule SYS_C005047 Objects.  
	 * 	It will be deleted/edited when the association is deleted/edited.
	 * @return java.util.Enumeration
	 * @exception javax.ejb.FinderException The exception description.
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public java.util.Enumeration getUseraccessmodule()
		throws java.rmi.RemoteException, javax.ejb.FinderException {
		return this.getUseraccessmoduleLink().enumerationValue();
	}

	/**
	 * This method was generated for supporting the association named Useraccessmodule SYS_C005047 Objects.  
	 * 	It will be deleted/edited when the association is deleted/edited.
	 * @return com.ibm.ivj.ejb.associations.interfaces.ManyLink
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	protected com
		.ibm
		.ivj
		.ejb
		.associations
		.interfaces
		.ManyLink getUseraccessmoduleLink() {
		if (useraccessmoduleLink == null)
			useraccessmoduleLink = new ObjectsToUseraccessmoduleLink(this);
		return useraccessmoduleLink;
	}

	/**
	 * This method was generated for supporting the association named Useraccessmodule SYS_C005047 Objects.  
	 * 	It will be deleted/edited when the association is deleted/edited.
	 * @param anUseraccessmodule com.ardais.bigr.orm.beans.Useraccessmodule
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public void secondaryAddUseraccessmodule(
		com.ardais.bigr.orm.beans.Useraccessmodule anUseraccessmodule) {
		this.getUseraccessmoduleLink().secondaryAddElement(anUseraccessmodule);
	}

	/**
	 * This method was generated for supporting the association named Useraccessmodule SYS_C005047 Objects.  
	 * 	It will be deleted/edited when the association is deleted/edited.
	 * @param anUseraccessmodule com.ardais.bigr.orm.beans.Useraccessmodule
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public void secondaryRemoveUseraccessmodule(
		com.ardais.bigr.orm.beans.Useraccessmodule anUseraccessmodule) {
		this.getUseraccessmoduleLink().secondaryRemoveElement(anUseraccessmodule);
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
	 * setEntityContext method comment
	 * @param ctx javax.ejb.EntityContext
	 */
	public void setEntityContext(javax.ejb.EntityContext ctx)
		throws java.rmi.RemoteException {
		entityContext = ctx;
	}

	/**
	 * Setter method for long_description
	 * @param newValue java.lang.String
	 */
	public void setLong_description(java.lang.String newValue) {
		this.long_description = newValue;
	}

	/**
	 * Setter method for object_description
	 * @param newValue java.lang.String
	 */
	public void setObject_description(java.lang.String newValue) {
		this.object_description = newValue;
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
	 * Setter method for url
	 * @param newValue java.lang.String
	 */
	public void setUrl(java.lang.String newValue) {
		this.url = newValue;
	}

	/**
	 * unsetEntityContext method comment
	 */
	public void unsetEntityContext() throws java.rmi.RemoteException {
		entityContext = null;
	}
}