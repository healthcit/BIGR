package com.ardais.bigr.iltds.beans;

/**
 * This is a Home interface for the Session Bean
 */
public interface BTXHistoryReaderHome extends javax.ejb.EJBHome {

/**
 * @return com.ardais.bigr.iltds.beans.BTXHistoryReader
 * @exception javax.ejb.CreateException
 */
com.ardais.bigr.iltds.beans.BTXHistoryReader create() throws javax.ejb.CreateException, java.rmi.RemoteException;
}
