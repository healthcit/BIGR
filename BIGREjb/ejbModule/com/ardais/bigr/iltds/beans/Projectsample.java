package com.ardais.bigr.iltds.beans;

/**
 * Remote interface for Enterprise Bean: Projectsample
 */
public interface Projectsample extends javax.ejb.EJBObject, com.ibm.ivj.ejb.runtime.CopyHelper {


/**
 * This method was generated for supporting the association named Projectsample FK_PTS_SAMPLE_LINEITEM Lineitem.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return com.ardais.bigr.iltds.beans.Lineitem
 * @exception javax.ejb.FinderException The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
com.ardais.bigr.iltds.beans.Lineitem getLineitem() throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * This method was generated for supporting the association named Projectsample FK_PTS_SAMPLE_LINEITEM Lineitem.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return com.ardais.bigr.iltds.beans.LineitemKey
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
com.ardais.bigr.iltds.beans.LineitemKey getLineitemKey() throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Projectsample FK_PTS_SAMPLE_PROJECT Project.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return com.ardais.bigr.iltds.beans.Project
 * @exception javax.ejb.FinderException The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
com.ardais.bigr.iltds.beans.Project getProject() throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * This method was generated for supporting the association named Projectsample FK_PTS_SAMPLE_PROJECT Project.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return com.ardais.bigr.iltds.beans.ProjectKey
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
com.ardais.bigr.iltds.beans.ProjectKey getProjectKey() throws java.rmi.RemoteException;
}
