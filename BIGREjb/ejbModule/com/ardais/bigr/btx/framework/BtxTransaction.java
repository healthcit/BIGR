package com.ardais.bigr.btx.framework;

import com.ardais.bigr.iltds.btx.BTXDetails;

/**
 * This is the session bean through which the BTX framework dispatches
 * all BTX business transactions.
 * 
 * <p>This bean is only intended to be used internally by the BTX framework.  Clients
 * should always use the {@link Btx} class to invoke BTX business transactions.  If BTX
 * transactions are invoked in any other way, parts of the transaction that should always
 * be done may be skipped, such as authorization checks, exception handling, and logging.
 */
public interface BtxTransaction extends javax.ejb.EJBObject {

  /**
   * Perform a business transaction.
   *
   * <p>Performs the transaction indicated by the supplied {@link BTXDetails} object's
   * {@link BTXDetails#getTransactionType() getTransactionType} method, using the BTXDetails
   * parameter to supply the transaction's input data and return its output data.  It does other
   * tasks such as assigning a transaction id, exception handling, and recording the
   * transaction in the transaction history tables.
   * 
   * <p>This method guarantees that the value returned by the <code>getTxType</code>
   * method of the returned BTXDetails object will be the same as the value returned
   * for by <code>getTxType</code> on the btxDetails parameter at the time the perform
   * method was called.  It also guarantees that this is the transaction type that the
   * BTXDetails object will have when it is passed to the history-recording method.  We
   * guarantee this to avoid confusion that could arise if the transaction-performed method
   * for a specific transaction inadvertently changes the transaction type, for example in the
   * course of passing the same BTXDetails object on to a different transaction internally.
   * 
   * <p>This method is only intended to be used internally by the BTX framework.  Clients
   * should always use the {@link Btx} class to invoke BTX business transactions.  If BTX
   * transactions are invoked in any other way, parts of the transaction that should always
   * be done may be skipped, such as authorization checks, exception handling, and logging.
   * 
   * @param btxDetails the detailed description of what transaction to perform.
   * @return the transaction's result details.
   */
  BTXDetails perform(BTXDetails btxDetails) throws java.rmi.RemoteException;
}
