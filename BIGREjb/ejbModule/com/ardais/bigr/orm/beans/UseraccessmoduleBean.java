package com.ardais.bigr.orm.beans;
import javax.ejb.EntityBean;
/**
 * This is an Entity Bean class with CMP fields
 */
/*******************************************************************************************************
	Modified by 	Date		 	Modification
	Nagaraja Rao	03/27/2002		Changes made as described in MR #3508 to Initialize all CMP fields     
*******************************************************************************************************/
public class UseraccessmoduleBean implements EntityBean {
  public static final String DEFAULT_ardais_acct_key = null;
  public static final String DEFAULT_ardais_user_id = null;
  public static final String DEFAULT_objects_object_id = null;
  public static final String DEFAULT_new_order_creation = null;
  public static final String DEFAULT_order_maintain = null;
  public static final String DEFAULT_order_view = null;
  public static final String DEFAULT_description = null;
  public static final java.sql.Timestamp DEFAULT_create_date = null;
  public static final String DEFAULT_created_by = null;
  public static final java.sql.Timestamp DEFAULT_update_date = null;
  public static final String DEFAULT_updated_by = null;

  public String ardais_acct_key;
  public String ardais_user_id;
  public java.lang.String objects_object_id;
  public String new_order_creation;
  public String order_maintain;
  public String order_view;
  public String description;
  public java.sql.Timestamp create_date;
  public String created_by;
  public java.sql.Timestamp update_date;
  public String updated_by;

  private javax.ejb.EntityContext entityContext = null;
  final static long serialVersionUID = 3206093459760846163L;
  private transient com.ibm.ivj.ejb.associations.interfaces.SingleLink objectsLink = null;

  private static final com.ardais.bigr.api.CMPDefaultValues _fieldDefaultValues =
    new com.ardais.bigr.api.CMPDefaultValues(com.ardais.bigr.orm.beans.UseraccessmoduleBean.class);

  /**
   * _copyFromEJB
   */
  public java.util.Hashtable _copyFromEJB() {
    com.ibm.ivj.ejb.runtime.AccessBeanHashtable h = new com.ibm.ivj.ejb.runtime.AccessBeanHashtable();

    h.put("create_date", getCreate_date());
    h.put("update_date", getUpdate_date());
    h.put("updated_by", getUpdated_by());
    h.put("order_view", getOrder_view());
    h.put("created_by", getCreated_by());
    h.put("order_maintain", getOrder_maintain());
    h.put("description", getDescription());
    h.put("objectsKey", getObjectsKey());
    h.put("new_order_creation", getNew_order_creation());
    h.put("__Key", getEntityContext().getPrimaryKey());

    return h;
  }
  /**
   * _copyToEJB
   */
  public void _copyToEJB(java.util.Hashtable h) {
    java.sql.Timestamp localCreate_date = (java.sql.Timestamp) h.get("create_date");
    java.sql.Timestamp localUpdate_date = (java.sql.Timestamp) h.get("update_date");
    java.lang.String localUpdated_by = (java.lang.String) h.get("updated_by");
    java.lang.String localOrder_view = (java.lang.String) h.get("order_view");
    java.lang.String localCreated_by = (java.lang.String) h.get("created_by");
    java.lang.String localOrder_maintain = (java.lang.String) h.get("order_maintain");
    java.lang.String localDescription = (java.lang.String) h.get("description");
    java.lang.String localNew_order_creation = (java.lang.String) h.get("new_order_creation");

    if (h.containsKey("create_date"))
      setCreate_date((localCreate_date));
    if (h.containsKey("update_date"))
      setUpdate_date((localUpdate_date));
    if (h.containsKey("updated_by"))
      setUpdated_by((localUpdated_by));
    if (h.containsKey("order_view"))
      setOrder_view((localOrder_view));
    if (h.containsKey("created_by"))
      setCreated_by((localCreated_by));
    if (h.containsKey("order_maintain"))
      setOrder_maintain((localOrder_maintain));
    if (h.containsKey("description"))
      setDescription((localDescription));
    if (h.containsKey("new_order_creation"))
      setNew_order_creation((localNew_order_creation));
  }
  /**
   * This method was generated for supporting the associations.
   * @return java.util.Vector
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  protected java.util.Vector _getLinks() {
    java.util.Vector links = new java.util.Vector();
    links.addElement(getObjectsLink());
    return links;
  }

  /**
   * This method was generated for supporting the associations.
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  protected void _initLinks() {
    objectsLink = null;
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
   * ejbCreate method for a CMP entity bean
   * @param argArdais_acct_key java.lang.String
   * @param argArdais_user_id java.lang.String
   * @param argObjects com.ardais.bigr.orm.beans.ObjectsKey
   * @exception javax.ejb.CreateException The exception description.
   */
  public UseraccessmoduleKey ejbCreate(
    java.lang.String argArdais_acct_key,
    java.lang.String argArdais_user_id,
    com.ardais.bigr.orm.beans.ObjectsKey argObjects,
    java.lang.String argCreated_by,
    java.sql.Timestamp argCreate_date,
    java.lang.String argUpdated_by,
    java.sql.Timestamp argUpdate_date)
    throws javax.ejb.CreateException, javax.ejb.EJBException {
    _fieldDefaultValues.assignTo(this);
    _initLinks();
    // All CMP fields should be initialized here.
    ardais_acct_key = argArdais_acct_key;
    ardais_user_id = argArdais_user_id;
    create_date = argCreate_date;
    created_by = argCreated_by;
    update_date = argUpdate_date;
    updated_by = argUpdated_by;
    boolean objects_NULLTEST = (argObjects == null);

    if (objects_NULLTEST)
      objects_object_id = null;
    else
      objects_object_id = argObjects.object_id;
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
   * @param argArdais_acct_key java.lang.String
   * @param argArdais_user_id java.lang.String
   * @param argObjects com.ardais.bigr.orm.beans.ObjectsKey
   */
  public void ejbPostCreate(
    java.lang.String argArdais_acct_key,
    java.lang.String argArdais_user_id,
    com.ardais.bigr.orm.beans.ObjectsKey argObjects,
    java.lang.String argCreated_by,
    java.sql.Timestamp argCreate_date,
    java.lang.String argUpdated_by,
    java.sql.Timestamp argUpdate_date)
    throws javax.ejb.EJBException {
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
   * Getter method for description
   * @return java.lang.String
   */
  public java.lang.String getDescription() {
    return description;
  }

  /**
   * getEntityContext method comment
   * @return javax.ejb.EntityContext
   */
  public javax.ejb.EntityContext getEntityContext() {
    return entityContext;
  }

  /**
   * Getter method for new_order_creation
   * @return java.lang.String
   */
  public java.lang.String getNew_order_creation() {
    return new_order_creation;
  }

  /**
   * This method was generated for supporting the association named Useraccessmodule SYS_C005047 Objects.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return com.ardais.bigr.orm.beans.Objects
   * @exception javax.ejb.FinderException The exception description.
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public com.ardais.bigr.orm.beans.Objects getObjects()
    throws java.rmi.RemoteException, javax.ejb.FinderException {
    return (com.ardais.bigr.orm.beans.Objects) this.getObjectsLink().value();
  }

  /**
   * This method was generated for supporting the association named Useraccessmodule SYS_C005047 Objects.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return com.ardais.bigr.orm.beans.ObjectsKey
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public com.ardais.bigr.orm.beans.ObjectsKey getObjectsKey() {
    com.ardais.bigr.orm.beans.ObjectsKey temp = null;
    temp = new com.ardais.bigr.orm.beans.ObjectsKey();
    boolean objects_NULLTEST = true;
    objects_NULLTEST &= (objects_object_id == null);
    temp.object_id = objects_object_id;
    if (objects_NULLTEST)
      temp = null;
    return temp;
  }

  /**
   * This method was generated for supporting the association named Useraccessmodule SYS_C005047 Objects.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return com.ibm.ivj.ejb.associations.interfaces.SingleLink
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  protected com.ibm.ivj.ejb.associations.interfaces.SingleLink getObjectsLink() {
    if (objectsLink == null)
      objectsLink = new UseraccessmoduleToObjectsLink(this);
    return objectsLink;
  }

  /**
   * Getter method for order_maintain
   * @return java.lang.String
   */
  public java.lang.String getOrder_maintain() {
    return order_maintain;
  }

  /**
   * Getter method for order_view
   * @return java.lang.String
   */
  public java.lang.String getOrder_view() {
    return order_view;
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
   * This method was generated for supporting the association named Useraccessmodule SYS_C005047 Objects.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param inKey com.ardais.bigr.orm.beans.ObjectsKey
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void privateSetObjectsKey(com.ardais.bigr.orm.beans.ObjectsKey inKey) {
    boolean objects_NULLTEST = (inKey == null);
    if (objects_NULLTEST)
      objects_object_id = null;
    else
      objects_object_id = inKey.object_id;
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
   * Setter method for description
   * @param newValue java.lang.String
   */
  public void setDescription(java.lang.String newValue) {
    this.description = newValue;
  }

  /**
   * setEntityContext method comment
   * @param ctx javax.ejb.EntityContext
   */
  public void setEntityContext(javax.ejb.EntityContext ctx) throws java.rmi.RemoteException {
    entityContext = ctx;
  }

  /**
   * Setter method for new_order_creation
   * @param newValue java.lang.String
   */
  public void setNew_order_creation(java.lang.String newValue) {
    this.new_order_creation = newValue;
  }

  /**
   * This method was generated for supporting the association named Useraccessmodule SYS_C005047 Objects.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param anObjects com.ardais.bigr.orm.beans.Objects
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void setObjects(com.ardais.bigr.orm.beans.Objects anObjects)
    throws java.rmi.RemoteException {
    this.getObjectsLink().set(anObjects);
  }

  /**
   * Setter method for order_maintain
   * @param newValue java.lang.String
   */
  public void setOrder_maintain(java.lang.String newValue) {
    this.order_maintain = newValue;
  }

  /**
   * Setter method for order_view
   * @param newValue java.lang.String
   */
  public void setOrder_view(java.lang.String newValue) {
    this.order_view = newValue;
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
   * unsetEntityContext method comment
   */
  public void unsetEntityContext() throws java.rmi.RemoteException {
    entityContext = null;
  }
}