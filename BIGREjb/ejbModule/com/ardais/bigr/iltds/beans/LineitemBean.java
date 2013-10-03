package com.ardais.bigr.iltds.beans;

import javax.ejb.EJBException;
import javax.ejb.EntityBean;
/**
 * This is an Entity Bean class with CMP fields
 */
public class LineitemBean implements EntityBean {
  public static final String DEFAULT_lineitemid = null;
  public static final java.lang.String DEFAULT_project_projectid = null;
  public static final java.math.BigDecimal DEFAULT_lineitemnumber = null;
  public static final String DEFAULT_format = null;
  public static final java.math.BigDecimal DEFAULT_quantity = new java.math.BigDecimal(0);
  public static final String DEFAULT_notes = null;
  public static final String DEFAULT_comments = null;

  public String lineitemid;
  public java.lang.String project_projectid;
  public java.math.BigDecimal lineitemnumber;
  public String format;
  public java.math.BigDecimal quantity;
  public String notes;
  public String comments;

  private javax.ejb.EntityContext entityContext = null;
  private final static long serialVersionUID = 3206093459760846163L;
  private transient com.ibm.ivj.ejb.associations.interfaces.SingleLink projectLink = null;
  private transient com.ibm.ivj.ejb.associations.interfaces.ManyLink projectsampleLink = null;

  private static final com.ardais.bigr.api.CMPDefaultValues _fieldDefaultValues =
    new com.ardais.bigr.api.CMPDefaultValues(com.ardais.bigr.iltds.beans.LineitemBean.class);
  /**
   * This method was generated for supporting the associations.
   * @return java.util.Vector
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  protected java.util.Vector _getLinks() {
    java.util.Vector links = new java.util.Vector();
    links.addElement(getProjectLink());
    links.addElement(getProjectsampleLink());
    return links;
  }
  /**
   * This method was generated for supporting the associations.
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  protected void _initLinks() {
    projectLink = null;
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
   * ejbActivate method comment
   */
  public void ejbActivate() throws java.rmi.RemoteException {
    _initLinks();
  }
  /**
   * ejbCreate method for a CMP entity bean
   * @param argLineitemid java.lang.String
   * @exception javax.ejb.CreateException The exception description.
   */
  public LineitemKey ejbCreate(java.lang.String argLineitemid)
    throws javax.ejb.CreateException, EJBException {
    _fieldDefaultValues.assignTo(this);
    _initLinks();
    // All CMP fields should be initialized here.
    lineitemid = argLineitemid;
    return null;
  }
  /**
   * ejbCreate method for a CMP entity bean
   * @param argLineitemid java.lang.String
   * @exception javax.ejb.CreateException The exception description.
   */
  public LineitemKey ejbCreate(
    java.lang.String argLineitemid,
    java.lang.String argProjectid,
    java.math.BigDecimal argLineitemnumber,
    java.math.BigDecimal argQuantity)
    throws javax.ejb.CreateException, EJBException {
    _fieldDefaultValues.assignTo(this);
    _initLinks();
    // All CMP fields should be initialized here.
    lineitemid = argLineitemid;
    lineitemnumber = argLineitemnumber;
    project_projectid = argProjectid;
    quantity = argQuantity;
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
   * @param argLineitemid java.lang.String
   */
  public void ejbPostCreate(java.lang.String argLineitemid) throws EJBException {
  }

  public void ejbPostCreate(
    java.lang.String argLineitemid,
    java.lang.String argProjectid,
    java.math.BigDecimal argLineitemnumber,
    java.math.BigDecimal argQuantity)
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
   * Getter method for comments
   * @return java.lang.String
   */
  public java.lang.String getComments() {
    return comments;
  }
  /**
   * getEntityContext method comment
   * @return javax.ejb.EntityContext
   */
  public javax.ejb.EntityContext getEntityContext() {
    return entityContext;
  }
  /**
   * Getter method for format
   * @return java.lang.String
   */
  public java.lang.String getFormat() {
    return format;
  }
  /**
   * Getter method for lineitemnumber
   * @return java.math.BigDecimal
   */
  public java.math.BigDecimal getLineitemnumber() {
    return lineitemnumber;
  }
  /**
   * Getter method for notes
   * @return java.lang.String
   */
  public java.lang.String getNotes() {
    return notes;
  }
  /**
   * This method was generated for supporting the association named Lineitem FK_PTS_LINEITEM_PROJECT Project.  
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
   * This method was generated for supporting the association named Lineitem FK_PTS_LINEITEM_PROJECT Project.  
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
   * This method was generated for supporting the association named Lineitem FK_PTS_LINEITEM_PROJECT Project.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return com.ibm.ivj.ejb.associations.interfaces.SingleLink
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  protected com.ibm.ivj.ejb.associations.interfaces.SingleLink getProjectLink() {
    if (projectLink == null)
      projectLink = new LineitemToProjectLink(this);
    return projectLink;
  }
  /**
   * This method was generated for supporting the association named Projectsample FK_PTS_SAMPLE_LINEITEM Lineitem.  
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
   * This method was generated for supporting the association named Projectsample FK_PTS_SAMPLE_LINEITEM Lineitem.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return com.ibm.ivj.ejb.associations.interfaces.ManyLink
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  protected com.ibm.ivj.ejb.associations.interfaces.ManyLink getProjectsampleLink() {
    if (projectsampleLink == null)
      projectsampleLink = new LineitemToProjectsampleLink(this);
    return projectsampleLink;
  }
  /**
   * Getter method for quantity
   * @return java.math.BigDecimal
   */
  public java.math.BigDecimal getQuantity() {
    return quantity;
  }
  /**
   * This method was generated for supporting the association named Lineitem FK_PTS_LINEITEM_PROJECT Project.  
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
   * This method was generated for supporting the association named Projectsample FK_PTS_SAMPLE_LINEITEM Lineitem.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param aProjectsample com.ardais.bigr.iltds.beans.Projectsample
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void secondaryAddProjectsample(com.ardais.bigr.iltds.beans.Projectsample aProjectsample) {
    this.getProjectsampleLink().secondaryAddElement(aProjectsample);
  }
  /**
   * This method was generated for supporting the association named Projectsample FK_PTS_SAMPLE_LINEITEM Lineitem.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param aProjectsample com.ardais.bigr.iltds.beans.Projectsample
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void secondaryRemoveProjectsample(
    com.ardais.bigr.iltds.beans.Projectsample aProjectsample) {
    this.getProjectsampleLink().secondaryRemoveElement(aProjectsample);
  }
  /**
   * This method was generated for supporting the association named Lineitem FK_PTS_LINEITEM_PROJECT Project.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param aProject com.ardais.bigr.iltds.beans.Project
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void secondarySetProject(com.ardais.bigr.iltds.beans.Project aProject)
    throws java.rmi.RemoteException {
    this.getProjectLink().secondarySet(aProject);
  }
  /**
   * Setter method for comments
   * @param newValue java.lang.String
   */
  public void setComments(java.lang.String newValue) {
    this.comments = newValue;
  }
  /**
   * setEntityContext method comment
   * @param ctx javax.ejb.EntityContext
   */
  public void setEntityContext(javax.ejb.EntityContext ctx) throws java.rmi.RemoteException {
    entityContext = ctx;
  }
  /**
   * Setter method for format
   * @param newValue java.lang.String
   */
  public void setFormat(java.lang.String newValue) {
    this.format = newValue;
  }
  /**
   * Setter method for lineitemnumber
   * @param newValue java.math.BigDecimal
   */
  public void setLineitemnumber(java.math.BigDecimal newValue) {
    this.lineitemnumber = newValue;
  }
  /**
   * Setter method for notes
   * @param newValue java.lang.String
   */
  public void setNotes(java.lang.String newValue) {
    this.notes = newValue;
  }
  /**
   * This method was generated for supporting the association named Lineitem FK_PTS_LINEITEM_PROJECT Project.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param aProject com.ardais.bigr.iltds.beans.Project
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void setProject(com.ardais.bigr.iltds.beans.Project aProject)
    throws java.rmi.RemoteException {
    this.getProjectLink().set(aProject);
  }
  /**
   * Setter method for quantity
   * @param newValue java.math.BigDecimal
   */
  public void setQuantity(java.math.BigDecimal newValue) {
    this.quantity = newValue;
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

    h.put("projectKey", getProjectKey());
    h.put("quantity", getQuantity());
    h.put("comments", getComments());
    h.put("notes", getNotes());
    h.put("format", getFormat());
    h.put("lineitemnumber", getLineitemnumber());
    h.put("__Key", getEntityContext().getPrimaryKey());

    return h;
  }
  /**
   * _copyToEJB
   */
  public void _copyToEJB(java.util.Hashtable h) {
    java.math.BigDecimal localQuantity = (java.math.BigDecimal) h.get("quantity");
    java.lang.String localComments = (java.lang.String) h.get("comments");
    java.lang.String localNotes = (java.lang.String) h.get("notes");
    java.lang.String localFormat = (java.lang.String) h.get("format");
    java.math.BigDecimal localLineitemnumber = (java.math.BigDecimal) h.get("lineitemnumber");

    if (h.containsKey("quantity"))
      setQuantity((localQuantity));
    if (h.containsKey("comments"))
      setComments((localComments));
    if (h.containsKey("notes"))
      setNotes((localNotes));
    if (h.containsKey("format"))
      setFormat((localFormat));
    if (h.containsKey("lineitemnumber"))
      setLineitemnumber((localLineitemnumber));
  }
}
