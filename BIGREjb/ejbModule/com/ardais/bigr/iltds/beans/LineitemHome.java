package com.ardais.bigr.iltds.beans;

/**
 * This is a Home interface for the Entity Bean
 */
public interface LineitemHome extends javax.ejb.EJBHome {

/**
 * create method for a CMP entity bean
 * @return com.ardais.bigr.iltds.beans.Lineitem
 * @param argLineitemid java.lang.String
 * @exception javax.ejb.CreateException The exception description.
 */
com.ardais.bigr.iltds.beans.Lineitem create(java.lang.String argLineitemid) throws javax.ejb.CreateException, java.rmi.RemoteException;
/**
 * create method for a CMP entity bean
 * @return com.ardais.bigr.iltds.beans.Lineitem
 * @param argLineitemid java.lang.String
 * @exception javax.ejb.CreateException The exception description.
 */
com.ardais.bigr.iltds.beans.Lineitem create(java.lang.String argLineitemid, java.lang.String argProjectid, java.math.BigDecimal argLineitemnumber, java.math.BigDecimal argQuantity) throws javax.ejb.CreateException, java.rmi.RemoteException;
/**
 * findByPrimaryKey method comment
 * @return com.ardais.bigr.iltds.beans.Lineitem
 * @param key com.ardais.bigr.iltds.beans.LineitemKey
 * @exception javax.ejb.FinderException The exception description.
 */
com.ardais.bigr.iltds.beans.Lineitem findByPrimaryKey(com.ardais.bigr.iltds.beans.LineitemKey key) throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * This method was generated for supporting the association named Lineitem FK_PTS_LINEITEM_PROJECT Project.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return java.util.Enumeration
 * @param inKey com.ardais.bigr.iltds.beans.ProjectKey
 * @exception javax.ejb.FinderException The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
java.util.Enumeration findLineitemByProject(com.ardais.bigr.iltds.beans.ProjectKey inKey) throws java.rmi.RemoteException, javax.ejb.FinderException;
}
