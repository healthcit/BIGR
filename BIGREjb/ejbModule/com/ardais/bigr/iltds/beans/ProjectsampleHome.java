package com.ardais.bigr.iltds.beans;

/**
 * This is a Home interface for the Entity Bean
 */
public interface ProjectsampleHome extends javax.ejb.EJBHome {

/**
 * create method for a CMP entity bean
 * @return com.ardais.bigr.iltds.beans.Projectsample
 * @param argSamplebarcodeid java.lang.String
 * @param argLineitem com.ardais.bigr.iltds.beans.LineitemKey
 * @param argProject com.ardais.bigr.iltds.beans.ProjectKey
 * @exception javax.ejb.CreateException The exception description.
 */
com.ardais.bigr.iltds.beans.Projectsample create(java.lang.String argSamplebarcodeid, com.ardais.bigr.iltds.beans.LineitemKey argLineitem, com.ardais.bigr.iltds.beans.ProjectKey argProject) throws javax.ejb.CreateException, java.rmi.RemoteException;
/**
 * findByPrimaryKey method comment
 * @return com.ardais.bigr.iltds.beans.Projectsample
 * @param key com.ardais.bigr.iltds.beans.ProjectsampleKey
 * @exception javax.ejb.FinderException The exception description.
 */
com.ardais.bigr.iltds.beans.Projectsample findByPrimaryKey(com.ardais.bigr.iltds.beans.ProjectsampleKey key) throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * This method was generated for supporting the association named Projectsample FK_PTS_SAMPLE_LINEITEM Lineitem.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return java.util.Enumeration
 * @param inKey com.ardais.bigr.iltds.beans.LineitemKey
 * @exception javax.ejb.FinderException The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
java.util.Enumeration findProjectsampleByLineitem(com.ardais.bigr.iltds.beans.LineitemKey inKey) throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * This method was generated for supporting the association named Projectsample FK_PTS_SAMPLE_PROJECT Project.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return java.util.Enumeration
 * @param inKey com.ardais.bigr.iltds.beans.ProjectKey
 * @exception javax.ejb.FinderException The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
java.util.Enumeration findProjectsampleByProject(com.ardais.bigr.iltds.beans.ProjectKey inKey) throws java.rmi.RemoteException, javax.ejb.FinderException;
}
