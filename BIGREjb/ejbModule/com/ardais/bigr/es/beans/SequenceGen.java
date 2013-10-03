package com.ardais.bigr.es.beans;

/**
 * This is an Enterprise Java Bean Remote Interface
 */
public interface SequenceGen extends javax.ejb.EJBObject {

/**
 * 
 * @return int
 * @param seqName java.lang.String
 */
int getSeqNextVal(java.lang.String seqName) throws java.rmi.RemoteException, com.ardais.bigr.api.ApiException;
}
