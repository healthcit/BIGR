package com.ardais.bigr.iltds.beans;

import java.rmi.*;
import javax.ejb.*;

/**
 * <code>PtsOperationHome</code> is the home interface for the PtsOperation bean.
 */
public interface PtsOperationHome extends EJBHome {

/**
 * Creates a PtsOperation session bean.
 *
 * @return  The remote interface of the <code>PtsOperation</code> session bean.
 * @exception CreateException
 * @exception RemoteException
 */
PtsOperation create() throws CreateException, RemoteException;
}
