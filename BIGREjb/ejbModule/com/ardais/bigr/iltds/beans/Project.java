package com.ardais.bigr.iltds.beans;

/**
 * Remote interface for Enterprise Bean: Project
 */
public interface Project extends javax.ejb.EJBObject, com.ibm.ivj.ejb.runtime.CopyHelper {




/**
 * This method was generated for supporting the association named Lineitem FK_PTS_LINEITEM_PROJECT Project.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aLineitem com.ardais.bigr.iltds.beans.Lineitem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void addLineitem(com.ardais.bigr.iltds.beans.Lineitem aLineitem) throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Workorder FK_PTS_WORKORDER_PROJECT Project.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aWorkorder com.ardais.bigr.iltds.beans.Workorder
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void addWorkorder(com.ardais.bigr.iltds.beans.Workorder aWorkorder) throws java.rmi.RemoteException;
/**
 * Getter method for ardaisaccountkey
 * @return java.lang.String
 */
java.lang.String getArdaisaccountkey() throws java.rmi.RemoteException;
/**
 * Getter method for ardaisuserid
 * @return java.lang.String
 */
java.lang.String getArdaisuserid() throws java.rmi.RemoteException;
/**
 * Getter method for dateapproved
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getDateapproved() throws java.rmi.RemoteException;
/**
 * Getter method for daterequested
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getDaterequested() throws java.rmi.RemoteException;
/**
 * Getter method for dateshipped
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getDateshipped() throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Lineitem FK_PTS_LINEITEM_PROJECT Project.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return java.util.Enumeration
 * @exception javax.ejb.FinderException The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
java.util.Enumeration getLineitem() throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * Getter method for notes
 * @return java.lang.String
 */
java.lang.String getNotes() throws java.rmi.RemoteException;
/**
 * 
 * @return java.math.BigDecimal
 */
java.math.BigDecimal getPercentcomplete() throws java.rmi.RemoteException;
/**
 * 
 * @return java.lang.String
 */
java.lang.String getProjectId() throws java.rmi.RemoteException;
/**
 * Getter method for projectname
 * @return java.lang.String
 */
java.lang.String getProjectname() throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Projectsample FK_PTS_SAMPLE_PROJECT Project.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return java.util.Enumeration
 * @exception javax.ejb.FinderException The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
java.util.Enumeration getProjectsample() throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * Getter method for status
 * @return java.lang.String
 */
java.lang.String getStatus() throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Workorder FK_PTS_WORKORDER_PROJECT Project.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return java.util.Enumeration
 * @exception javax.ejb.FinderException The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
java.util.Enumeration getWorkorder() throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * This method was generated for supporting the association named Lineitem FK_PTS_LINEITEM_PROJECT Project.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aLineitem com.ardais.bigr.iltds.beans.Lineitem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void removeLineitem(com.ardais.bigr.iltds.beans.Lineitem aLineitem) throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Workorder FK_PTS_WORKORDER_PROJECT Project.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aWorkorder com.ardais.bigr.iltds.beans.Workorder
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void removeWorkorder(com.ardais.bigr.iltds.beans.Workorder aWorkorder) throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Lineitem FK_PTS_LINEITEM_PROJECT Project.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aLineitem com.ardais.bigr.iltds.beans.Lineitem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void secondaryAddLineitem(com.ardais.bigr.iltds.beans.Lineitem aLineitem) throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Projectsample FK_PTS_SAMPLE_PROJECT Project.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aProjectsample com.ardais.bigr.iltds.beans.Projectsample
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void secondaryAddProjectsample(com.ardais.bigr.iltds.beans.Projectsample aProjectsample) throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Workorder FK_PTS_WORKORDER_PROJECT Project.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aWorkorder com.ardais.bigr.iltds.beans.Workorder
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void secondaryAddWorkorder(com.ardais.bigr.iltds.beans.Workorder aWorkorder) throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Lineitem FK_PTS_LINEITEM_PROJECT Project.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aLineitem com.ardais.bigr.iltds.beans.Lineitem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void secondaryRemoveLineitem(com.ardais.bigr.iltds.beans.Lineitem aLineitem) throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Projectsample FK_PTS_SAMPLE_PROJECT Project.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aProjectsample com.ardais.bigr.iltds.beans.Projectsample
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void secondaryRemoveProjectsample(com.ardais.bigr.iltds.beans.Projectsample aProjectsample) throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Workorder FK_PTS_WORKORDER_PROJECT Project.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aWorkorder com.ardais.bigr.iltds.beans.Workorder
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void secondaryRemoveWorkorder(com.ardais.bigr.iltds.beans.Workorder aWorkorder) throws java.rmi.RemoteException;
/**
 * Setter method for ardaisaccountkey
 * @param newValue java.lang.String
 */
void setArdaisaccountkey(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for ardaisuserid
 * @param newValue java.lang.String
 */
void setArdaisuserid(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for dateapproved
 * @param newValue java.sql.Timestamp
 */
void setDateapproved(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
/**
 * Setter method for daterequested
 * @param newValue java.sql.Timestamp
 */
void setDaterequested(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
/**
 * Setter method for dateshipped
 * @param newValue java.sql.Timestamp
 */
void setDateshipped(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
/**
 * Setter method for notes
 * @param newValue java.lang.String
 */
void setNotes(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * 
 * @return void
 * @param newValue java.math.BigDecimal
 */
void setPercentcomplete(java.math.BigDecimal newValue) throws java.rmi.RemoteException;
/**
 * Setter method for projectname
 * @param newValue java.lang.String
 */
void setProjectname(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for status
 * @param newValue java.lang.String
 */
void setStatus(java.lang.String newValue) throws java.rmi.RemoteException;
}
