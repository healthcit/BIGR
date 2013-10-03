package com.ardais.bigr.iltds.beans;

import javax.ejb.EJBException;
import javax.ejb.EntityBean;
/**
 * This is an Entity Bean class with CMP fields
 */
public class ProjectsampleBean implements EntityBean {
  public static final java.lang.String DEFAULT_project_projectid = null;
  public static final java.lang.String DEFAULT_lineitem_lineitemid = null;
  public static final String DEFAULT_samplebarcodeid = null;

  public java.lang.String project_projectid;
  public java.lang.String lineitem_lineitemid;
  public String samplebarcodeid;

  private javax.ejb.EntityContext entityContext = null;
  private final static long serialVersionUID = 3206093459760846163L;
  private transient com.ibm.ivj.ejb.associations.interfaces.SingleLink lineitemLink = null;
  private transient com.ibm.ivj.ejb.associations.interfaces.SingleLink projectLink = null;

  private static final com.ardais.bigr.api.CMPDefaultValues _fieldDefaultValues =
    new com.ardais.bigr.api.CMPDefaultValues(com.ardais.bigr.iltds.beans.ProjectsampleBean.class);

  /**
   * This method was generated for supporting the associations.
   * @return java.util.Vector
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  protected java.util.Vector _getLinks() {
    java.util.Vector links = new java.util.Vector();
    links.addElement(getLineitemLink());
    links.addElement(getProjectLink());
    return links;
  }
  /**
   * This method was generated for supporting the associations.
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  protected void _initLinks() {
    lineitemLink = null;
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
   * @param argSamplebarcodeid java.lang.String
   * @param argLineitem com.ardais.bigr.iltds.beans.LineitemKey
   * @param argProject com.ardais.bigr.iltds.beans.ProjectKey
   * @exception javax.ejb.CreateException The exception description.
   */
  public ProjectsampleKey ejbCreate(
    java.lang.String argSamplebarcodeid,
    com.ardais.bigr.iltds.beans.LineitemKey argLineitem,
    com.ardais.bigr.iltds.beans.ProjectKey argProject)
    throws javax.ejb.CreateException, EJBException {
    _fieldDefaultValues.assignTo(this);
    _initLinks();
    // All CMP fields should be initialized here.
    samplebarcodeid = argSamplebarcodeid;
    boolean lineitem_NULLTEST = (argLineitem == null);
    if (lineitem_NULLTEST)
      lineitem_lineitemid = null;
    else
      lineitem_lineitemid = argLineitem.lineitemid;
    boolean project_NULLTEST = (argProject == null);
    if (project_NULLTEST)
      project_projectid = null;
    else
      project_projectid = argProject.projectid;
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
   * @param argSamplebarcodeid java.lang.String
   * @param argLineitem com.ardais.bigr.iltds.beans.LineitemKey
   * @param argProject com.ardais.bigr.iltds.beans.ProjectKey
   */
  public void ejbPostCreate(
    java.lang.String argSamplebarcodeid,
    com.ardais.bigr.iltds.beans.LineitemKey argLineitem,
    com.ardais.bigr.iltds.beans.ProjectKey argProject)
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
   * getEntityContext method comment
   * @return javax.ejb.EntityContext
   */
  public javax.ejb.EntityContext getEntityContext() {
    return entityContext;
  }
  /**
   * This method was generated for supporting the association named Projectsample FK_PTS_SAMPLE_LINEITEM Lineitem.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return com.ardais.bigr.iltds.beans.Lineitem
   * @exception javax.ejb.FinderException The exception description.
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public com.ardais.bigr.iltds.beans.Lineitem getLineitem()
    throws java.rmi.RemoteException, javax.ejb.FinderException {
    return (com.ardais.bigr.iltds.beans.Lineitem) this.getLineitemLink().value();
  }
  /**
   * This method was generated for supporting the association named Projectsample FK_PTS_SAMPLE_LINEITEM Lineitem.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return com.ardais.bigr.iltds.beans.LineitemKey
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public com.ardais.bigr.iltds.beans.LineitemKey getLineitemKey() {
    com.ardais.bigr.iltds.beans.LineitemKey temp = null;
    temp = new com.ardais.bigr.iltds.beans.LineitemKey();
    boolean lineitem_NULLTEST = true;
    lineitem_NULLTEST &= (lineitem_lineitemid == null);
    temp.lineitemid = lineitem_lineitemid;
    if (lineitem_NULLTEST)
      temp = null;
    return temp;
  }
  /**
   * This method was generated for supporting the association named Projectsample FK_PTS_SAMPLE_LINEITEM Lineitem.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return com.ibm.ivj.ejb.associations.interfaces.SingleLink
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  protected com.ibm.ivj.ejb.associations.interfaces.SingleLink getLineitemLink() {
    if (lineitemLink == null)
      lineitemLink = new ProjectsampleToLineitemLink(this);
    return lineitemLink;
  }
  /**
   * This method was generated for supporting the association named Projectsample FK_PTS_SAMPLE_PROJECT Project.  
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
   * This method was generated for supporting the association named Projectsample FK_PTS_SAMPLE_PROJECT Project.  
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
   * This method was generated for supporting the association named Projectsample FK_PTS_SAMPLE_PROJECT Project.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return com.ibm.ivj.ejb.associations.interfaces.SingleLink
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  protected com.ibm.ivj.ejb.associations.interfaces.SingleLink getProjectLink() {
    if (projectLink == null)
      projectLink = new ProjectsampleToProjectLink(this);
    return projectLink;
  }
  /**
   * This method was generated for supporting the association named Projectsample FK_PTS_SAMPLE_LINEITEM Lineitem.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param inKey com.ardais.bigr.iltds.beans.LineitemKey
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void privateSetLineitemKey(com.ardais.bigr.iltds.beans.LineitemKey inKey) {
    boolean lineitem_NULLTEST = (inKey == null);
    if (lineitem_NULLTEST)
      lineitem_lineitemid = null;
    else
      lineitem_lineitemid = inKey.lineitemid;
  }
  /**
   * This method was generated for supporting the association named Projectsample FK_PTS_SAMPLE_PROJECT Project.  
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
   * setEntityContext method comment
   * @param ctx javax.ejb.EntityContext
   */
  public void setEntityContext(javax.ejb.EntityContext ctx) throws java.rmi.RemoteException {
    entityContext = ctx;
  }
  /**
   * This method was generated for supporting the association named Projectsample FK_PTS_SAMPLE_LINEITEM Lineitem.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param aLineitem com.ardais.bigr.iltds.beans.Lineitem
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void setLineitem(com.ardais.bigr.iltds.beans.Lineitem aLineitem)
    throws java.rmi.RemoteException {
    this.getLineitemLink().set(aLineitem);
  }
  /**
   * This method was generated for supporting the association named Projectsample FK_PTS_SAMPLE_PROJECT Project.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param aProject com.ardais.bigr.iltds.beans.Project
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void setProject(com.ardais.bigr.iltds.beans.Project aProject)
    throws java.rmi.RemoteException {
    this.getProjectLink().set(aProject);
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
    h.put("lineitemKey", getLineitemKey());
    h.put("__Key", getEntityContext().getPrimaryKey());

    return h;
  }
  /**
   * _copyToEJB
   */
  public void _copyToEJB(java.util.Hashtable h) {

  }
}
