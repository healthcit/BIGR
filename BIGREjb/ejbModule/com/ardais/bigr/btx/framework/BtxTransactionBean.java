package com.ardais.bigr.btx.framework;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BTXDetails;

/**
 * Bean implementation class for Enterprise Bean: BtxTransaction
 * 
 * <p>This bean is only intended to be used internally by the BTX framework.  Clients
 * should always use the {@link Btx} class to invoke BTX business transactions.  If BTX
 * transactions are invoked in any other way, parts of the transaction that should always
 * be done may be skipped, such as authorization checks, exception handling, and logging.
 * 
 * @see BtxTransaction
 */
public class BtxTransactionBean implements javax.ejb.SessionBean {

  private javax.ejb.SessionContext mySessionCtx;

  /**
   * Perform a business transaction.
   *
   * <p>This method is only intended to be used internally by the BTX framework.  Clients
   * should always use the {@link Btx} class to invoke BTX business transactions.  If BTX
   * transactions are invoked in any other way, parts of the transaction that should always
   * be done may be skipped, such as authorization checks, exception handling, and logging.
   * 
   * @see BtxTransaction#perform(BTXDetails)
   */
  public BTXDetails perform(BTXDetails btxDetails) {
    // Get the metadata for this transaction type and look up the transaction's performer class.
    // Create a new instance of this class and call its perform method to do the rest of the work.
    //
    // In general when extending this method, you should keep this method as thin a facade
    // as possible with all of the real work being done by BtxTransactionPerformerBase.perform.
    //
    // We create a new class instance each time rather than using some sort of instance cache
    // to skirt thorny issues that could arise if the performer classes were coded to be
    // stateful or not thread-safe.  By creating a new instance each time we know we're always
    // starting from a clean slate.

    BtxTransactionMetaData txMeta = BtxConfiguration.getTransaction(btxDetails.getTransactionType());
    Class performerClass = txMeta.getPerformerClass();
    BtxTransactionPerformerBase performerInstance = null;

    try {
      // All performer classes must extend BtxTransactionPerformerBase, we
      // check for this when we create the BtxTransactionMetaData object so
      // we know we won't get a ClassCastException here.
      //
      performerInstance = (BtxTransactionPerformerBase) performerClass.newInstance();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }

    return performerInstance.perform(btxDetails);
  }

  /**
   * getSessionContext
   */
  public javax.ejb.SessionContext getSessionContext() {
    return mySessionCtx;
  }
  /**
   * setSessionContext
   */
  public void setSessionContext(javax.ejb.SessionContext ctx) {
    mySessionCtx = ctx;
  }
  /**
   * ejbCreate
   */
  public void ejbCreate() throws javax.ejb.CreateException {
  }
  /**
   * ejbActivate
   */
  public void ejbActivate() {
  }
  /**
   * ejbPassivate
   */
  public void ejbPassivate() {
  }
  /**
   * ejbRemove
   */
  public void ejbRemove() {
  }
}
