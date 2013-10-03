package com.ardais.bigr.iltds.beans;
/**
 * Remote interface for Enterprise Bean: SequenceGen
 */
public interface SequenceGen extends javax.ejb.EJBObject {
/**
 * 
 * @return java.lang.String
 * @param seqName java.lang.String
 */
java.lang.String getSeqNextVal(java.lang.String seqName) throws java.rmi.RemoteException, com.ardais.bigr.api.ApiException;
}
