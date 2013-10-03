package com.ardais.bigr.iltds.beans;

import javax.ejb.EJBException;
import javax.ejb.EntityBean;
/**
 * This is an Entity Bean class with CMP fields
 */
public class ProjectBean implements EntityBean {
  public static final String DEFAULT_projectid = null;
  public static final String DEFAULT_projectname = null;
  public static final String DEFAULT_ardaisaccountkey = null;
  public static final String DEFAULT_ardaisuserid = null;
  public static final java.sql.Timestamp DEFAULT_daterequested = null;
  public static final java.sql.Timestamp DEFAULT_dateapproved = null;
  public static final java.sql.Timestamp DEFAULT_dateshipped = null;
  public static final String DEFAULT_notes = null;
  public static final String DEFAULT_status = null;
  public static final java.math.BigDecimal DEFAULT_percentcomplete = new java.math.BigDecimal(0);

  public String projectid;
  public String projectname;
  public String ardaisaccountkey;
  public String ardaisuserid;
  public java.sql.Timestamp daterequested;
  public java.sql.Timestamp dateapproved;
  public java.sql.Timestamp dateshipped;
  public String notes;
  public String status;
  public java.math.BigDecimal percentcomplete;

  private javax.ejb.EntityContext entityContext = null;
  private final static long serialVersionUID = 3206093459760846163L;
  private transient com.ibm.ivj.ejb.associations.interfaces.ManyLink lineitemLink = null;
  private transient com.ibm.ivj.ejb.associations.interfaces.ManyLink projectsampleLink = null;
  private transient com.ibm.ivj.ejb.associations.interfaces.ManyLink workorderLink = null;

  private static final com.ardais.bigr.api.CMPDefaultValues _fieldDefaultValues =
    new com.ardais.bigr.api.CMPDefaultValues(com.ardais.bigr.iltds.beans.ProjectBean.class);
  /**
   * This method was generated for supporting the associations.
   * @return java.util.Vector
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  protected java.util.Vector _getLinks() {
    java.util.Vector links = new java.util.Vector();
    links.addElement(getWorkorderLink());
    links.addElement(getLineitemLink());
    links.addElement(getProjectsampleLink());
    return links;
  }
  /**
   * This method was generated for supporting the associations.
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  protected void _initLinks() {
    workorderLink = null;
    lineitemLink = null;
    projectsampleLink = null;
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
   * This method was generated for supporting the association named Lineitem FK_PTS_LINEITEM_PROJECT Project.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param aLineitem com.ardais.bigr.iltds.beans.Lineitem
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void addLineitem(com.ardais.bigr.iltds.beans.Lineitem aLineitem)
    throws java.rmi.RemoteException {
    this.getLineitemLink().addElement(aLineitem);
  }
  /**
   * This method was generated for supporting the association named Workorder FK_PTS_WORKORDER_PROJECT Project.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param aWorkorder com.ardais.bigr.iltds.beans.Workorder
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void addWorkorder(com.ardais.bigr.iltds.beans.Workorder aWorkorder)
    throws java.rmi.RemoteException {
    this.getWorkorderLink().addElement(aWorkorder);
  }
  /**
   * ejbActivate method comment
   */
  public void ejbActivate() throws java.rmi.RemoteException {
    _initLinks();
  }
  /**
   * ejbCreate method for a CMP entity bean
   * @param argProjectid java.lang.String
   * @exception javax.ejb.CreateException The exception description.
   */
  public ProjectKey ejbCreate(java.lang.String argProjectid)
    throws javax.ejb.CreateException, EJBException {
    _fieldDefaultValues.assignTo(this);
    _initLinks();
    // All CMP fields should be initialized here.
    projectid = argProjectid;
    return null;
  }
  /**
   * ejbCreate method for a CMP entity bean
   * @param argProjectid java.lang.String
   * @exception javax.ejb.CreateException The exception description.
   */
  public ProjectKey ejbCreate(
    java.lang.String argProjectid,
    java.lang.String argProjectName,
    java.lang.String argClient,
    java.lang.String argRequestedBy,
    java.sql.Timestamp argRequestDate)
    throws javax.ejb.CreateException, EJBException {
    _fieldDefaultValues.assignTo(this);
    _initLinks();
    // All CMP fields should be initialized here.
    projectid = argProjectid;
    projectname = argProjectName;
    ardaisaccountkey = argClient;
    ardaisuserid = argRequestedBy;
    daterequested = argRequestDate;
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
   * @param argProjectid java.lang.String
   */
  public void ejbPostCreate(java.lang.String argProjectid) throws EJBException {
  }

  public void ejbPostCreate(
    java.lang.String argProjectid,
    java.lang.String argProjectName,
    java.lang.String argClient,
    java.lang.String argRequestedBy,
    java.sql.Timestamp argRequestDate)
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
   * Getter method for ardaisaccountkey
   * @return java.lang.String
   */
  public java.lang.String getArdaisaccountkey() {
    return ardaisaccountkey;
  }
  /**
   * Getter method for ardaisuserid
   * @return java.lang.String
   */
  public java.lang.String getArdaisuserid() {
    return ardaisuserid;
  }
  /**
   * Getter method for dateapproved
   * @return java.sql.Timestamp
   */
  public java.sql.Timestamp getDateapproved() {
    return dateapproved;
  }
  /**
   * Getter method for daterequested
   * @return java.sql.Timestamp
   */
  public java.sql.Timestamp getDaterequested() {
    return daterequested;
  }
  /**
   * Getter method for dateshipped
   * @return java.sql.Timestamp
   */
  public java.sql.Timestamp getDateshipped() {
    return dateshipped;
  }
  /**
   * getEntityContext method comment
   * @return javax.ejb.EntityContext
   */
  public javax.ejb.EntityContext getEntityContext() {
    return entityContext;
  }
  /**
   * This method was generated for supporting the association named Lineitem FK_PTS_LINEITEM_PROJECT Project.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return java.util.Enumeration
   * @exception javax.ejb.FinderException The exception description.
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public java.util.Enumeration getLineitem()
    throws java.rmi.RemoteException, javax.ejb.FinderException {
    return this.getLineitemLink().enumerationValue();
  }
  /**
   * This method was generated for supporting the association named Lineitem FK_PTS_LINEITEM_PROJECT Project.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return com.ibm.ivj.ejb.associations.interfaces.ManyLink
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  protected com.ibm.ivj.ejb.associations.interfaces.ManyLink getLineitemLink() {
    if (lineitemLink == null)
      lineitemLink = new ProjectToLineitemLink(this);
    return lineitemLink;
  }
  /**
   * Getter method for notes
   * @return java.lang.String
   */
  public java.lang.String getNotes() {
    return notes;
  }
  /**
   * Getter method for percentcomplete
   * @return java.math.BigDecimal
   */
  public java.math.BigDecimal getPercentcomplete() {
    return percentcomplete;
  }
  /**
   * Getter method for projectid
   * @return java.lang.String
   */
  public java.lang.String getProjectId() {
    return projectid;
  }
  /**
   * Getter method for projectname
   * @return java.lang.String
   */
  public java.lang.String getProjectname() {
    return projectname;
  }
  /**
   * This method was generated for supporting the association named Projectsample FK_PTS_SAMPLE_PROJECT Project.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return java.util.Enumeration
   * @exception javax.ejb.FinderException The exception description.
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public java.util.Enumeration getProjectsample()
    throws java.rmi.RemoteException, javax.ejb.FinderException {
    return this.getProjectsampleLink().enumerationValue();
  }
  /**
   * This method was generated for supporting the association named Projectsample FK_PTS_SAMPLE_PROJECT Project.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return com.ibm.ivj.ejb.associations.interfaces.ManyLink
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  protected com.ibm.ivj.ejb.associations.interfaces.ManyLink getProjectsampleLink() {
    if (projectsampleLink == null)
      projectsampleLink = new ProjectToProjectsampleLink(this);
    return projectsampleLink;
  }
  /**
   * Getter method for status
   * @return java.lang.String
   */
  public java.lang.String getStatus() {
    return status;
  }
  /**
   * This method was generated for supporting the association named Workorder FK_PTS_WORKORDER_PROJECT Project.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return java.util.Enumeration
   * @exception javax.ejb.FinderException The exception description.
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public java.util.Enumeration getWorkorder()
    throws java.rmi.RemoteException, javax.ejb.FinderException {
    return this.getWorkorderLink().enumerationValue();
  }
  /**
   * This method was generated for supporting the association named Workorder FK_PTS_WORKORDER_PROJECT Project.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return com.ibm.ivj.ejb.associations.interfaces.ManyLink
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  protected com.ibm.ivj.ejb.associations.interfaces.ManyLink getWorkorderLink() {
    if (workorderLink == null)
      workorderLink = new ProjectToWorkorderLink(this);
    return workorderLink;
  }
  /**
   * This method was generated for supporting the association named Lineitem FK_PTS_LINEITEM_PROJECT Project.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param aLineitem com.ardais.bigr.iltds.beans.Lineitem
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void removeLineitem(com.ardais.bigr.iltds.beans.Lineitem aLineitem)
    throws java.rmi.RemoteException {
    this.getLineitemLink().removeElement(aLineitem);
  }
  /**
   * This method was generated for supporting the association named Workorder FK_PTS_WORKORDER_PROJECT Project.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param aWorkorder com.ardais.bigr.iltds.beans.Workorder
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void removeWorkorder(com.ardais.bigr.iltds.beans.Workorder aWorkorder)
    throws java.rmi.RemoteException {
    this.getWorkorderLink().removeElement(aWorkorder);
  }
  /**
   * This method was generated for supporting the association named Lineitem FK_PTS_LINEITEM_PROJECT Project.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param aLineitem com.ardais.bigr.iltds.beans.Lineitem
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void secondaryAddLineitem(com.ardais.bigr.iltds.beans.Lineitem aLineitem) {
    this.getLineitemLink().secondaryAddElement(aLineitem);
  }
  /**
   * This method was generated for supporting the association named Projectsample FK_PTS_SAMPLE_PROJECT Project.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param aProjectsample com.ardais.bigr.iltds.beans.Projectsample
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void secondaryAddProjectsample(com.ardais.bigr.iltds.beans.Projectsample aProjectsample) {
    this.getProjectsampleLink().secondaryAddElement(aProjectsample);
  }
  /**
   * This method was generated for supporting the association named Workorder FK_PTS_WORKORDER_PROJECT Project.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param aWorkorder com.ardais.bigr.iltds.beans.Workorder
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void secondaryAddWorkorder(com.ardais.bigr.iltds.beans.Workorder aWorkorder) {
    this.getWorkorderLink().secondaryAddElement(aWorkorder);
  }
  /**
   * This method was generated for supporting the association named Lineitem FK_PTS_LINEITEM_PROJECT Project.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param aLineitem com.ardais.bigr.iltds.beans.Lineitem
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void secondaryRemoveLineitem(com.ardais.bigr.iltds.beans.Lineitem aLineitem) {
    this.getLineitemLink().secondaryRemoveElement(aLineitem);
  }
  /**
   * This method was generated for supporting the association named Projectsample FK_PTS_SAMPLE_PROJECT Project.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param aProjectsample com.ardais.bigr.iltds.beans.Projectsample
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void secondaryRemoveProjectsample(
    com.ardais.bigr.iltds.beans.Projectsample aProjectsample) {
    this.getProjectsampleLink().secondaryRemoveElement(aProjectsample);
  }
  /**
   * This method was generated for supporting the association named Workorder FK_PTS_WORKORDER_PROJECT Project.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param aWorkorder com.ardais.bigr.iltds.beans.Workorder
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void secondaryRemoveWorkorder(com.ardais.bigr.iltds.beans.Workorder aWorkorder) {
    this.getWorkorderLink().secondaryRemoveElement(aWorkorder);
  }
  /**
   * Setter method for ardaisaccountkey
   * @param newValue java.lang.String
   */
  public void setArdaisaccountkey(java.lang.String newValue) {
    this.ardaisaccountkey = newValue;
  }
  /**
   * Setter method for ardaisuserid
   * @param newValue java.lang.String
   */
  public void setArdaisuserid(java.lang.String newValue) {
    this.ardaisuserid = newValue;
  }
  /**
   * Setter method for dateapproved
   * @param newValue java.sql.Timestamp
   */
  public void setDateapproved(java.sql.Timestamp newValue) {
    this.dateapproved = newValue;
  }
  /**
   * Setter method for daterequested
   * @param newValue java.sql.Timestamp
   */
  public void setDaterequested(java.sql.Timestamp newValue) {
    this.daterequested = newValue;
  }
  /**
   * Setter method for dateshipped
   * @param newValue java.sql.Timestamp
   */
  public void setDateshipped(java.sql.Timestamp newValue) {
    this.dateshipped = newValue;
  }
  /**
   * setEntityContext method comment
   * @param ctx javax.ejb.EntityContext
   */
  public void setEntityContext(javax.ejb.EntityContext ctx) throws java.rmi.RemoteException {
    entityContext = ctx;
  }
  /**
   * Setter method for notes
   * @param newValue java.lang.String
   */
  public void setNotes(java.lang.String newValue) {
    this.notes = newValue;
  }
  /**
   * Setter method for percentcomplete
   * @param newValue java.math.BigDecimal
   */
  public void setPercentcomplete(java.math.BigDecimal newValue) {
    this.percentcomplete = newValue;
  }
  /**
   * Setter method for projectname
   * @param newValue java.lang.String
   */
  public void setProjectname(java.lang.String newValue) {
    this.projectname = newValue;
  }
  /**
   * Setter method for status
   * @param newValue java.lang.String
   */
  public void setStatus(java.lang.String newValue) {
    this.status = newValue;
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
    com.ibm.ivj.ejb.runtime.AccessBeanHashtable h = new com.ibm.ivj.ejb.runtime.AccessBeanHashtable();

    h.put("ardaisaccountkey", getArdaisaccountkey());
    h.put("status", getStatus());
    h.put("dateapproved", getDateapproved());
    h.put("dateshipped", getDateshipped());
    h.put("projectId", getProjectId());
    h.put("notes", getNotes());
    h.put("projectname", getProjectname());
    h.put("daterequested", getDaterequested());
    h.put("ardaisuserid", getArdaisuserid());
    h.put("percentcomplete", getPercentcomplete());
    h.put("__Key", getEntityContext().getPrimaryKey());

    return h;
  }
  /**
   * _copyToEJB
   */
  public void _copyToEJB(java.util.Hashtable h) {
    java.lang.String localArdaisaccountkey = (java.lang.String) h.get("ardaisaccountkey");
    java.lang.String localStatus = (java.lang.String) h.get("status");
    java.sql.Timestamp localDateapproved = (java.sql.Timestamp) h.get("dateapproved");
    java.sql.Timestamp localDateshipped = (java.sql.Timestamp) h.get("dateshipped");
    java.lang.String localNotes = (java.lang.String) h.get("notes");
    java.lang.String localProjectname = (java.lang.String) h.get("projectname");
    java.sql.Timestamp localDaterequested = (java.sql.Timestamp) h.get("daterequested");
    java.lang.String localArdaisuserid = (java.lang.String) h.get("ardaisuserid");
    java.math.BigDecimal localPercentcomplete = (java.math.BigDecimal) h.get("percentcomplete");

    if (h.containsKey("ardaisaccountkey"))
      setArdaisaccountkey((localArdaisaccountkey));
    if (h.containsKey("status"))
      setStatus((localStatus));
    if (h.containsKey("dateapproved"))
      setDateapproved((localDateapproved));
    if (h.containsKey("dateshipped"))
      setDateshipped((localDateshipped));
    if (h.containsKey("notes"))
      setNotes((localNotes));
    if (h.containsKey("projectname"))
      setProjectname((localProjectname));
    if (h.containsKey("daterequested"))
      setDaterequested((localDaterequested));
    if (h.containsKey("ardaisuserid"))
      setArdaisuserid((localArdaisuserid));
    if (h.containsKey("percentcomplete"))
      setPercentcomplete((localPercentcomplete));
  }
}
