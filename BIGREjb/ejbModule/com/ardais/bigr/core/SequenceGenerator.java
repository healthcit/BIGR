package com.ardais.bigr.core;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.ardais.bigr.api.ApiException;

/**
 * Remote interface for Enterprise Bean: SequenceGenerator
 */
public interface SequenceGenerator extends EJBObject {

  /**
   * 
   * @return Integer
   * @param seqName String
   */
  Integer getSeqNextVal(String seqName) throws RemoteException, ApiException;
}
