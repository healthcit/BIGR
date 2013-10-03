package com.ardais.bigr.iltds.beans;

/**
 * This session bean implements operations that record business
 * transaction history for transactions in which a Java exception occurred.
 * It provides exactly the same interface as {@link BTXHistoryRecorder},
 * but is a separate bean so that we can set its transaction properties
 * differently.  Specifically, unlike most of our beans, this bean has its
 * database transaction deployment property set to TX_REQUIRES_NEW rather
 * than TX_REQUIRED.  This is necessary so that a transaction history record
 * will be written even if the an exception occurs during performing the
 * business transaction that causes the business transaction itself to roll
 * back.  In this case, the history record that is written will include
 * details of the exception.
 */
public interface BtxHistoryExceptionRecorder
    extends BTXHistoryRecorder, javax.ejb.EJBObject {
}
