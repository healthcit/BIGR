package com.ardais.bigr.iltds.beans;

import com.ardais.bigr.iltds.btx.BTXDetails;

/**
 * This session bean implements operations that record business
 * transaction history.  It has a <code>record</code> method that
 * records the history, given a {@link BTXDetails} object describing the
 * transaction.
 * 
 * <p>You must use a different class to record transactions in which a
 * Java exception occurs: {@link BtxHistoryExceptionRecorder}.  If you use
 * this class, the history record won't actually get recorded in the database,
 * because the Java exception will cause the database transaction that we
 * use here to roll back.  See BtxHistoryExceptionRecorder for more details.
 */
public interface BTXHistoryRecorder extends javax.ejb.EJBObject {

    /**
     * Record the specified transaction in the transaction history tables.
     * Recording the transaction assigns it a transaction id, which is set
     * into the transaction id property of the returned BTXDetails object.
     * 
     * @param btxDetails the details of the transaction to be recorded.
     * @return the transaction details augmented with additional information
     *    such as the transaction id.
     */
    BTXDetails record(BTXDetails btxDetails) throws java.rmi.RemoteException;
}
