package com.ardais.bigr.iltds.beans;

import java.util.List;

import com.ardais.bigr.security.SecurityInfo;

/**
 * This session bean implements operations that read the business
 * transaction history tables.  It includes methods to retrieve
 * history-display data for all transactions that match certain
 * criteria, such as all transactions that involve a given object id
 * or that involve a specified storage location.
 */
public interface BTXHistoryReader extends javax.ejb.EJBObject {

    /**
     * Retrieve history display information for all transactions that involve
     * the specified object.
     *
     * @param objectId the transactions that involve this object will be
     *     returned.
     * @param securityInfo the security credentials of the current user.
     * 
     * @return a list of
     *    {@link com.ardais.bigr.iltds.btx.BTXHistoryDisplayLine}
     *    objects matching the specified criteria, sorted in reverse
     *    chronological order by transaction end date.
     */
    List getHistoryDisplayLines(String objectId, SecurityInfo securityInfo)
        throws java.rmi.RemoteException;
}
