package com.ardais.bigr.iltds.beans;

import javax.ejb.EJBException;
import javax.ejb.EntityBean;
/**
 * This is an Entity Bean class with CMP fields
 */
public class WorkorderBean implements EntityBean {
  public static final java.lang.String DEFAULT_project_projectid = null;
  public static final String DEFAULT_workorderid = null;
  public static final String DEFAULT_workordertype = null;
  public static final String DEFAULT_workordername = null;
  public static final java.math.BigDecimal DEFAULT_listorder = null;
  public static final java.sql.Timestamp DEFAULT_startdate = null;
  public static final java.sql.Timestamp DEFAULT_enddate = null;
  public static final String DEFAULT_status = null;
  public static final java.math.BigDecimal DEFAULT_percentcomplete = null;
  public static final java.math.BigDecimal DEFAULT_numberofsamples = null;
  public static final String DEFAULT_notes = null;

  public java.lang.String project_projectid;
  public String workorderid;
  public String workordertype;
  public String workordername;
  public java.math.BigDecimal listorder;
  public java.sql.Timestamp startdate;
  public java.sql.Timestamp enddate;
  public String status;
  public java.math.BigDecimal percentcomplete;
  public java.math.BigDecimal numberofsamples;
  public String notes;

  private javax.ejb.EntityContext entityContext = null;
  private final static long serialVersionUID = 3206093459760846163L;
  private transient com.ibm.ivj.ejb.associations.interfaces.SingleLink projectLink = null;

  private static final com.ardais.bigr.api.CMPDefaultValues _fieldDefaultValues =
    new com.ardais.bigr.api.CMPDefaultValues(com.ardais.bigr.iltds.beans.WorkorderBean.class);

  /**
   * This method was generated for supporting the associations.
   * @return java.util.Vector
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  protected java.util.Vector _getLinks() {
    java.util.Vector links = new java.util.Vector();
    links.addElement(getProjectLink());
    return links;
  }
  /**
   * This method was generated for supporting the associations.
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  protected void _initLinks() {
    projectLink = null;
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
   * @param argWorkorderid java.lang.String
   * @exception javax.ejb.CreateException The exception description.
   */
  public WorkorderKey ejbCreate(java.lang.String argWorkorderid)
    throws javax.ejb.CreateException, EJBException {
    _fieldDefaultValues.assignTo(this);
    _initLinks();
    // All CMP fields should be initialized here.
    workorderid = argWorkorderid;
    return null;
  }
  /**
   * ejbCreate method for a CMP entity bean
   * @param argWorkorderid java.lang.String
   * @exception javax.ejb.CreateException The exception description.
   */
  public WorkorderKey ejbCreate(
    java.lang.String argWorkorderid,
    java.lang.String argProjectid,
    java.lang.String argWorkordertype,
    java.lang.String argWorkordername,
    java.math.BigDecimal argListorder)
    throws javax.ejb.CreateException, EJBException {
    _fieldDefaultValues.assignTo(this);
    _initLinks();
    // All CMP fields should be initialized here.
    workorderid = argWorkorderid;
    listorder = argListorder;
    project_projectid = argProjectid;
    workordername = argWorkordername;
    workordertype = argWorkordertype;
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
   * @param argWorkorderid java.lang.String
   */
  public void ejbPostCreate(java.lang.String argWorkorderid) throws EJBException {
  }

  public void ejbPostCreate(
    java.lang.String argWorkorderid,
    java.lang.String argProjectid,
    java.lang.String argWorkordertype,
    java.lang.String argWorkordername,
    java.math.BigDecimal argListorder)
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
   * Getter method for enddate
   * @return java.sql.Timestamp
   */
  public java.sql.Timestamp getEnddate() {
    return enddate;
  }
  /**
   * getEntityContext method comment
   * @return javax.ejb.EntityContext
   */
  public javax.ejb.EntityContext getEntityContext() {
    return entityContext;
  }
  /**
   * Getter method for listorder
   * @return java.math.BigDecimal
   */
  public java.math.BigDecimal getListorder() {
    return listorder;
  }
  /**
   * Getter method for notes
   * @return java.lang.String
   */
  public java.lang.String getNotes() {
    return notes;
  }
  /**
   * Getter method for numberofsamples
   * @return java.math.BigDecimal
   */
  public java.math.BigDecimal getNumberofsamples() {
    return numberofsamples;
  }
  /**
   * Getter method for percentcomplete
   * @return java.math.BigDecimal
   */
  public java.math.BigDecimal getPercentcomplete() {
    return percentcomplete;
  }
  /**
   * This method was generated for supporting the association named Workorder FK_PTS_WORKORDER_PROJECT Project.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return com.ardais.bigr.iltds.beans.Project
   * @exception javax.ejb.FinderException The exception description.
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public com.ardais.bigr.iltds.beans.Project getProject()
    throws java.rmi.RemoteException, javax.ejb.FinderException {
    return (com.ardais.bigr.iltds.beans.Project) this.getProjectLink().value();
  }
  /**
   * This method was generated for supporting the association named Workorder FK_PTS_WORKORDER_PROJECT Project.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return com.ardais.bigr.iltds.beans.ProjectKey
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public com.ardais.bigr.iltds.beans.ProjectKey getProjectKey() {
    com.ardais.bigr.iltds.beans.ProjectKey temp = null;
    temp = new com.ardais.bigr.iltds.beans.ProjectKey();
    boolean project_NULLTEST = true;
    project_NULLTEST &= (project_projectid == null);
    temp.projectid = project_projectid;
    if (project_NULLTEST)
      temp = null;
    return temp;
  }
  /**
   * This method was generated for supporting the association named Workorder FK_PTS_WORKORDER_PROJECT Project.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return com.ibm.ivj.ejb.associations.interfaces.SingleLink
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  protected com.ibm.ivj.ejb.associations.interfaces.SingleLink getProjectLink() {
    if (projectLink == null)
      projectLink = new WorkorderToProjectLink(this);
    return projectLink;
  }
  /**
   * Getter method for startdate
   * @return java.sql.Timestamp
   */
  public java.sql.Timestamp getStartdate() {
    return startdate;
  }
  /**
   * Getter method for status
   * @return java.lang.String
   */
  public java.lang.String getStatus() {
    return status;
  }
  /**
   * Getter method for workordername
   * @return java.lang.String
   */
  public java.lang.String getWorkordername() {
    return workordername;
  }
  /**
   * Getter method for workordertype
   * @return java.lang.String
   */
  public java.lang.String getWorkordertype() {
    return workordertype;
  }
  /**
   * This method was generated for supporting the association named Workorder FK_PTS_WORKORDER_PROJECT Project.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param inKey com.ardais.bigr.iltds.beans.ProjectKey
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void privateSetProjectKey(com.ardais.bigr.iltds.beans.ProjectKey inKey) {
    boolean project_NULLTEST = (inKey == null);
    if (project_NULLTEST)
      project_projectid = null;
    else
      project_projectid = inKey.projectid;
  }
  /**
   * This method was generated for supporting the association named Workorder FK_PTS_WORKORDER_PROJECT Project.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param aProject com.ardais.bigr.iltds.beans.Project
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void secondarySetProject(com.ardais.bigr.iltds.beans.Project aProject)
    throws java.rmi.RemoteException {
    this.getProjectLink().secondarySet(aProject);
  }
  /**
   * Setter method for enddate
   * @param newValue java.sql.Timestamp
   */
  public void setEnddate(java.sql.Timestamp newValue) {
    this.enddate = newValue;
  }
  /**
   * setEntityContext method comment
   * @param ctx javax.ejb.EntityContext
   */
  public void setEntityContext(javax.ejb.EntityContext ctx) throws java.rmi.RemoteException {
    entityContext = ctx;
  }
  /**
   * Setter method for listorder
   * @param newValue java.math.BigDecimal
   */
  public void setListorder(java.math.BigDecimal newValue) {
    this.listorder = newValue;
  }
  /**
   * Setter method for notes
   * @param newValue java.lang.String
   */
  public void setNotes(java.lang.String newValue) {
    this.notes = newValue;
  }
  /**
   * Setter method for numberofsamples
   * @param newValue java.math.BigDecimal
   */
  public void setNumberofsamples(java.math.BigDecimal newValue) {
    this.numberofsamples = newValue;
  }
  /**
   * Setter method for percentcomplete
   * @param newValue java.math.BigDecimal
   */
  public void setPercentcomplete(java.math.BigDecimal newValue) {
    this.percentcomplete = newValue;
  }
  /**
   * This method was generated for supporting the association named Workorder FK_PTS_WORKORDER_PROJECT Project.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param aProject com.ardais.bigr.iltds.beans.Project
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void setProject(com.ardais.bigr.iltds.beans.Project aProject)
    throws java.rmi.RemoteException {
    this.getProjectLink().set(aProject);
  }
  /**
   * Setter method for startdate
   * @param newValue java.sql.Timestamp
   */
  public void setStartdate(java.sql.Timestamp newValue) {
    this.startdate = newValue;
  }
  /**
   * Setter method for status
   * @param newValue java.lang.String
   */
  public void setStatus(java.lang.String newValue) {
    this.status = newValue;
  }
  /**
   * Setter method for workordername
   * @param newValue java.lang.String
   */
  public void setWorkordername(java.lang.String newValue) {
    this.workordername = newValue;
  }
  /**
   * Setter method for workordertype
   * @param newValue java.lang.String
   */
  public void setWorkordertype(java.lang.String newValue) {
    this.workordertype = newValue;
  }
  /**
   * unsetEntityContext method comment
   */
  public void unsetEntityContext() throws java.rmi.RemoteException {
    entityContext = null;
  }
  /**
   * _copyFromEJB
   */
  public java.util.Hashtable _copyFromEJB() {
    com.ibm.ivj.ejb.runtime.AccessBeanHashtable h =
      new com.ibm.ivj.ejb.runtime.AccessBeanHashtable();

    h.put("projectKey", getProjectKey());
    h.put("workordername", getWorkordername());
    h.put("listorder", getListorder());
    h.put("enddate", getEnddate());
    h.put("status", getStatus());
    h.put("numberofsamples", getNumberofsamples());
    h.put("notes", getNotes());
    h.put("workordertype", getWorkordertype());
    h.put("startdate", getStartdate());
    h.put("percentcomplete", getPercentcomplete());
    h.put("__Key", getEntityContext().getPrimaryKey());

    return h;
  }
  /**
   * _copyToEJB
   */
  public void _copyToEJB(java.util.Hashtable h) {
    java.lang.String localWorkordername = (java.lang.String) h.get("workordername");
    java.math.BigDecimal localListorder = (java.math.BigDecimal) h.get("listorder");
    java.sql.Timestamp localEnddate = (java.sql.Timestamp) h.get("enddate");
    java.lang.String localStatus = (java.lang.String) h.get("status");
    java.math.BigDecimal localNumberofsamples = (java.math.BigDecimal) h.get("numberofsamples");
    java.lang.String localNotes = (java.lang.String) h.get("notes");
    java.lang.String localWorkordertype = (java.lang.String) h.get("workordertype");
    java.sql.Timestamp localStartdate = (java.sql.Timestamp) h.get("startdate");
    java.math.BigDecimal localPercentcomplete = (java.math.BigDecimal) h.get("percentcomplete");

    if (h.containsKey("workordername"))
      setWorkordername((localWorkordername));
    if (h.containsKey("listorder"))
      setListorder((localListorder));
    if (h.containsKey("enddate"))
      setEnddate((localEnddate));
    if (h.containsKey("status"))
      setStatus((localStatus));
    if (h.containsKey("numberofsamples"))
      setNumberofsamples((localNumberofsamples));
    if (h.containsKey("notes"))
      setNotes((localNotes));
    if (h.containsKey("workordertype"))
      setWorkordertype((localWorkordertype));
    if (h.containsKey("startdate"))
      setStartdate((localStartdate));
    if (h.containsKey("percentcomplete"))
      setPercentcomplete((localPercentcomplete));
  }
}
