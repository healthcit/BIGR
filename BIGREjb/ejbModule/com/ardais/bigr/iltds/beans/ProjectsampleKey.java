package com.ardais.bigr.iltds.beans;

public class ProjectsampleKey implements java.io.Serializable {
	public java.lang.String lineitem_lineitemid;
	public java.lang.String project_projectid;
	public java.lang.String samplebarcodeid;
	private final static long serialVersionUID = 3206093459760846163L;

/**
 * Default constructor
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public ProjectsampleKey() {
	super();
}
/**
 * Initialize a key from the passed values
 * @param argSamplebarcodeid java.lang.String
 * @param argLineitem com.ardais.bigr.iltds.beans.LineitemKey
 * @param argProject com.ardais.bigr.iltds.beans.ProjectKey
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public ProjectsampleKey(java.lang.String argSamplebarcodeid, com.ardais.bigr.iltds.beans.LineitemKey argLineitem, com.ardais.bigr.iltds.beans.ProjectKey argProject) {
	samplebarcodeid = argSamplebarcodeid;
	privateSetLineitemKey(argLineitem);
	privateSetProjectKey(argProject);
}
/**
 * equals method
 * @return boolean
 * @param o java.lang.Object
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public boolean equals(Object o) {
	if (o instanceof ProjectsampleKey) {
		ProjectsampleKey otherKey = (ProjectsampleKey) o;
		return ((this.samplebarcodeid.equals(otherKey.samplebarcodeid)
		 && this.lineitem_lineitemid.equals(otherKey.lineitem_lineitemid)
		 && this.project_projectid.equals(otherKey.project_projectid)));
	}
	else
		return false;
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
	if (lineitem_NULLTEST) temp = null;
	return temp;
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
	if (project_NULLTEST) temp = null;
	return temp;
}
/**
 * hashCode method
 * @return int
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public int hashCode() {
	return (samplebarcodeid.hashCode()
		 + lineitem_lineitemid.hashCode()
		 + project_projectid.hashCode());
}
/**
 * This method was generated for supporting the association named Projectsample FK_PTS_SAMPLE_LINEITEM Lineitem.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param inKey com.ardais.bigr.iltds.beans.LineitemKey
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void privateSetLineitemKey(com.ardais.bigr.iltds.beans.LineitemKey inKey) {
	boolean lineitem_NULLTEST = (inKey == null);
	if (lineitem_NULLTEST) lineitem_lineitemid = null; else lineitem_lineitemid = inKey.lineitemid;
}
/**
 * This method was generated for supporting the association named Projectsample FK_PTS_SAMPLE_PROJECT Project.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param inKey com.ardais.bigr.iltds.beans.ProjectKey
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void privateSetProjectKey(com.ardais.bigr.iltds.beans.ProjectKey inKey) {
	boolean project_NULLTEST = (inKey == null);
	if (project_NULLTEST) project_projectid = null; else project_projectid = inKey.projectid;
}
}
