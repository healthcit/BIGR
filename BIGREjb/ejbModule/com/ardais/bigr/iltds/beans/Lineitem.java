package com.ardais.bigr.iltds.beans;

/**
 * Remote interface for Enterprise Bean: Lineitem
 */
public interface Lineitem extends javax.ejb.EJBObject, com.ibm.ivj.ejb.runtime.CopyHelper {


/**
 * Getter method for comments
 * @return java.lang.String
 */
java.lang.String getComments() throws java.rmi.RemoteException;
/**
 * Getter method for format
 * @return java.lang.String
 */
java.lang.String getFormat() throws java.rmi.RemoteException;
/**
 * Getter method for lineitemnumber
 * @return java.math.BigDecimal
 */
java.math.BigDecimal getLineitemnumber() throws java.rmi.RemoteException;
/**
 * Getter method for notes
 * @return java.lang.String
 */
java.lang.String getNotes() throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Lineitem FK_PTS_LINEITEM_PROJECT Project.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return com.ardais.bigr.iltds.beans.Project
 * @exception javax.ejb.FinderException The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
com.ardais.bigr.iltds.beans.Project getProject() throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * This method was generated for supporting the association named Lineitem FK_PTS_LINEITEM_PROJECT Project.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return com.ardais.bigr.iltds.beans.ProjectKey
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
com.ardais.bigr.iltds.beans.ProjectKey getProjectKey() throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Projectsample FK_PTS_SAMPLE_LINEITEM Lineitem.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return java.util.Enumeration
 * @exception javax.ejb.FinderException The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
java.util.Enumeration getProjectsample() throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * Getter method for quantity
 * @return java.math.BigDecimal
 */
java.math.BigDecimal getQuantity() throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Lineitem FK_PTS_LINEITEM_PROJECT Project.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param inKey com.ardais.bigr.iltds.beans.ProjectKey
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void privateSetProjectKey(com.ardais.bigr.iltds.beans.ProjectKey inKey) throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Projectsample FK_PTS_SAMPLE_LINEITEM Lineitem.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aProjectsample com.ardais.bigr.iltds.beans.Projectsample
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void secondaryAddProjectsample(com.ardais.bigr.iltds.beans.Projectsample aProjectsample) throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Projectsample FK_PTS_SAMPLE_LINEITEM Lineitem.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aProjectsample com.ardais.bigr.iltds.beans.Projectsample
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void secondaryRemoveProjectsample(com.ardais.bigr.iltds.beans.Projectsample aProjectsample) throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Lineitem FK_PTS_LINEITEM_PROJECT Project.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aProject com.ardais.bigr.iltds.beans.Project
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void secondarySetProject(com.ardais.bigr.iltds.beans.Project aProject) throws java.rmi.RemoteException;
/**
 * Setter method for comments
 * @param newValue java.lang.String
 */
void setComments(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for format
 * @param newValue java.lang.String
 */
void setFormat(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for lineitemnumber
 * @param newValue java.math.BigDecimal
 */
void setLineitemnumber(java.math.BigDecimal newValue) throws java.rmi.RemoteException;
/**
 * Setter method for notes
 * @param newValue java.lang.String
 */
void setNotes(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Lineitem FK_PTS_LINEITEM_PROJECT Project.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aProject com.ardais.bigr.iltds.beans.Project
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void setProject(com.ardais.bigr.iltds.beans.Project aProject) throws java.rmi.RemoteException;
/**
 * Setter method for quantity
 * @param newValue java.math.BigDecimal
 */
void setQuantity(java.math.BigDecimal newValue) throws java.rmi.RemoteException;
}
