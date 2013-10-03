package com.ardais.bigr.iltds.beans;

/**
 * This is a Home interface for the Entity Bean
 */
public interface WorkorderHome extends javax.ejb.EJBHome {

/**
 * create method for a CMP entity bean
 * @return com.ardais.bigr.iltds.beans.Workorder
 * @param argWorkorderid java.lang.String
 * @exception javax.ejb.CreateException The exception description.
 */
com.ardais.bigr.iltds.beans.Workorder create(java.lang.String argWorkorderid) throws javax.ejb.CreateException, java.rmi.RemoteException;
/**
 * create method for a CMP entity bean
 * @return com.ardais.bigr.iltds.beans.Workorder
 * @param argWorkorderid java.lang.String
 * @exception javax.ejb.CreateException The exception description.
 */
com.ardais.bigr.iltds.beans.Workorder create(java.lang.String argWorkorderid, java.lang.String argProjectid, java.lang.String argWorkordertype,java.lang.String argWorkordername,java.math.BigDecimal argListorder) throws javax.ejb.CreateException, java.rmi.RemoteException;
/**
 * findByPrimaryKey method comment
 * @return com.ardais.bigr.iltds.beans.Workorder
 * @param key com.ardais.bigr.iltds.beans.WorkorderKey
 * @exception javax.ejb.FinderException The exception description.
 */
com.ardais.bigr.iltds.beans.Workorder findByPrimaryKey(com.ardais.bigr.iltds.beans.WorkorderKey key) throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * This method was generated for supporting the association named Workorder FK_PTS_WORKORDER_PROJECT Project.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return java.util.Enumeration
 * @param inKey com.ardais.bigr.iltds.beans.ProjectKey
 * @exception javax.ejb.FinderException The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
java.util.Enumeration findWorkorderByProject(com.ardais.bigr.iltds.beans.ProjectKey inKey) throws java.rmi.RemoteException, javax.ejb.FinderException;
}
