package com.ardais.bigr.iltds.beans;

/**
 * Remote interface for Enterprise Bean: Workorder
 */
public interface Workorder extends javax.ejb.EJBObject, com.ibm.ivj.ejb.runtime.CopyHelper {


/**
 * Getter method for enddate
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getEnddate() throws java.rmi.RemoteException;
/**
 * Getter method for listorder
 * @return java.math.BigDecimal
 */
java.math.BigDecimal getListorder() throws java.rmi.RemoteException;
/**
 * Getter method for notes
 * @return java.lang.String
 */
java.lang.String getNotes() throws java.rmi.RemoteException;
/**
 * Getter method for numberofsamples
 * @return java.math.BigDecimal
 */
java.math.BigDecimal getNumberofsamples() throws java.rmi.RemoteException;
/**
 * Getter method for percentcomplete
 * @return java.math.BigDecimal
 */
java.math.BigDecimal getPercentcomplete() throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Workorder FK_PTS_WORKORDER_PROJECT Project.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return com.ardais.bigr.iltds.beans.Project
 * @exception javax.ejb.FinderException The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
com.ardais.bigr.iltds.beans.Project getProject() throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * This method was generated for supporting the association named Workorder FK_PTS_WORKORDER_PROJECT Project.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return com.ardais.bigr.iltds.beans.ProjectKey
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
com.ardais.bigr.iltds.beans.ProjectKey getProjectKey() throws java.rmi.RemoteException;
/**
 * Getter method for startdate
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getStartdate() throws java.rmi.RemoteException;
/**
 * Getter method for status
 * @return java.lang.String
 */
java.lang.String getStatus() throws java.rmi.RemoteException;
/**
 * Getter method for workordername
 * @return java.lang.String
 */
java.lang.String getWorkordername() throws java.rmi.RemoteException;
/**
 * Getter method for workordertype
 * @return java.lang.String
 */
java.lang.String getWorkordertype() throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Workorder FK_PTS_WORKORDER_PROJECT Project.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param inKey com.ardais.bigr.iltds.beans.ProjectKey
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void privateSetProjectKey(com.ardais.bigr.iltds.beans.ProjectKey inKey) throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Workorder FK_PTS_WORKORDER_PROJECT Project.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aProject com.ardais.bigr.iltds.beans.Project
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void secondarySetProject(com.ardais.bigr.iltds.beans.Project aProject) throws java.rmi.RemoteException;
/**
 * Setter method for enddate
 * @param newValue java.sql.Timestamp
 */
void setEnddate(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
/**
 * Setter method for listorder
 * @param newValue java.math.BigDecimal
 */
void setListorder(java.math.BigDecimal newValue) throws java.rmi.RemoteException;
/**
 * Setter method for notes
 * @param newValue java.lang.String
 */
void setNotes(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for numberofsamples
 * @param newValue java.math.BigDecimal
 */
void setNumberofsamples(java.math.BigDecimal newValue) throws java.rmi.RemoteException;
/**
 * Setter method for percentcomplete
 * @param newValue java.math.BigDecimal
 */
void setPercentcomplete(java.math.BigDecimal newValue) throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Workorder FK_PTS_WORKORDER_PROJECT Project.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aProject com.ardais.bigr.iltds.beans.Project
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void setProject(com.ardais.bigr.iltds.beans.Project aProject) throws java.rmi.RemoteException;
/**
 * Setter method for startdate
 * @param newValue java.sql.Timestamp
 */
void setStartdate(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
/**
 * Setter method for status
 * @param newValue java.lang.String
 */
void setStatus(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for workordername
 * @param newValue java.lang.String
 */
void setWorkordername(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for workordertype
 * @param newValue java.lang.String
 */
void setWorkordertype(java.lang.String newValue) throws java.rmi.RemoteException;
}
