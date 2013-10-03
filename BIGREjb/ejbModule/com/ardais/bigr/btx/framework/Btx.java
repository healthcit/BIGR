package com.ardais.bigr.btx.framework;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.util.EjbHomes;

/**
 * This is the class through which all BTX business transactions must be invoked.
 */
public final class Btx {
  // This class is intended to be a very simple facade that delegates to the BtxTransaction
  // session bean for all the real work.  When adding things here, please keep that in mind.

  /**
   * This class has only static members, so prevent instantiation.
   */
  private Btx() {
    super();
  }

  /**
   * Perform a business transaction.  This is the method that must be used to invoke all
   * BTX business transactions.  If BTX transactions are invoked in any other way,
   * parts of the transaction that should always be done may be skipped, such as
   * authorization checks, exception handling, and logging.
   *
   * <p>This method performs the transaction indicated by the supplied {@link BTXDetails}
   * object's {@link BTXDetails#getTransactionType() getTransactionType} method, using the
   * BTXDetails parameter to supply the transaction's input data and return its output data.
   * It does other tasks such as assigning a transaction id, exception handling, and recording
   * the transaction in the transaction history tables.
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
   * @param btxDetails the detailed description of what transaction to perform.
   * @return the transaction's result details.
   */
  public static BTXDetails perform(BTXDetails btxDetails) {
    // The BtxTransaction session bean is considered to be internal to the BTX framework
    // and no code outside of the framework itself should ever use it.

    // This class is intended to be a very simple facade that delegates to the BtxTransaction
    // session bean for all the real work.  When adding things here, please keep that in mind.

    BTXDetails result = null;

    try {
      BtxTransactionHome home = (BtxTransactionHome) EjbHomes.getHome(BtxTransactionHome.class);
      BtxTransaction remote = home.create();
      result = remote.perform(btxDetails);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }

    return result;
  }

  /**
   * Perform a specified business transaction.
   * 
   * <p>This is a convenience method that simply does the following:
   * <ol>
   * <li>Temporarily save away the BTXDetails'
   *     {@link BTXDetails#getTransactionType() getTransactionType} value.</li>
   * <li>Call {@link BTXDetails#setTransactionType(String) btxDetails.setTransactionType(txType)}
   *     to set the transaction type to the supplied type.</li>
   * <li>Call {@link #perform(BTXDetails) perform(btxDetails)} to perform the indicated
   *     transaction.</li>
   * <li>Restore the input btxDetails' txType property to the value it had when this
   *     method was first called.</li>
   * <li>Return the result BTXDetails object.</li>
   * </ol>
   * 
   * This provides a simple way to perform a transaction on a BTXDetails object without
   * persistently altering its txType property.  This can be convenient when one BTX transaction
   * calls a second BTX transaction, passing its own BTXDetails parameter to the second
   * transaction.
   * 
   * @param btxDetails the detailed description of what transaction to perform.
   * @param txType the type of transaction to perform.
   * @return the transaction's result details.
   */
  public static BTXDetails perform(BTXDetails btxDetails, String txType) {
    BTXDetails result = null;
    String originalTxType = btxDetails.getTransactionType();
    try {
      btxDetails.setTransactionType(txType);
      result = perform(btxDetails);
    }
    finally {
      btxDetails.setTransactionType(originalTxType);
    }
    return result;
  }

}
