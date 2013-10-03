package com.ardais.bigr.core;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Home interface for Enterprise Bean: SequenceGenerator
 */
public interface SequenceGeneratorHome extends EJBHome {
  /**
   * Creates a default instance of Session Bean: SequenceGenerator
   */
  public SequenceGenerator create() throws CreateException, RemoteException;
}
