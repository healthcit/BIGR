package com.ardais.bigr.es.beans;

/**
 * This is a Home interface for the Entity Bean
 */
public interface ArdaisaddressHome extends javax.ejb.EJBHome {

/**
 * create method for a CMP entity bean
 * @return com.ardais.bigr.es.beans.Ardaisaddress
 * @param argAddress_id java.math.BigDecimal
 * @param argArdais_acct_key java.lang.String
 * @exception javax.ejb.CreateException The exception description.
 */
com.ardais.bigr.es.beans.Ardaisaddress create(java.math.BigDecimal argAddress_id, java.lang.String argArdais_acct_key) throws javax.ejb.CreateException, java.rmi.RemoteException;
/**
 * 
 * @return com.ardais.bigr.es.beans.Ardaisaddress
 * @param argAddress_id java.math.BigDecimal
 * @param argArdais_acct_key java.lang.String
 * @param argAddress1 java.lang.String
 */
com.ardais.bigr.es.beans.Ardaisaddress create(java.math.BigDecimal argAddress_id, java.lang.String argArdais_acct_key, java.lang.String argAddress1) throws javax.ejb.CreateException, java.rmi.RemoteException;
/**
 * Insert the method's description here.
 * Creation date: (4/5/2001 9:44:23 AM)
 * @return java.util.Enumeration
 * @param arg_Account java.lang.String
 * @param arg_Type java.lang.String
 * @exception javax.ejb.FinderException The exception description.
 */
java.util.Enumeration findByAccountandType(String arg_Account, String arg_Type) throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * findByPrimaryKey method comment
 * @return com.ardais.bigr.es.beans.Ardaisaddress
 * @param key com.ardais.bigr.es.beans.ArdaisaddressKey
 * @exception javax.ejb.FinderException The exception description.
 */
com.ardais.bigr.es.beans.Ardaisaddress findByPrimaryKey(com.ardais.bigr.es.beans.ArdaisaddressKey key) throws java.rmi.RemoteException, javax.ejb.FinderException;
}
