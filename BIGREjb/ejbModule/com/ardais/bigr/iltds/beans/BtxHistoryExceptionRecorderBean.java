package com.ardais.bigr.iltds.beans;

/**
 * @see BtxHistoryExceptionRecorder
 */
public class BtxHistoryExceptionRecorderBean
    extends BTXHistoryRecorderBean
    implements javax.ejb.SessionBean {
        
    // NOTE TO DEVELOPERS:  This class may seem useless because it doesn't
    // override any behavior from its superclass, but it isn't.  See the
    // comments on the BtxHistoryExceptionRecorder interface for details.
    // Briefly, we need this bean so that we can give it different
    // container-managed transaction properties than we give to
    // the BTXHistoryRecorder bean.

    private javax.ejb.SessionContext mySessionCtx;

    public javax.ejb.SessionContext getSessionContext() {
        return mySessionCtx;
    }

    public void setSessionContext(javax.ejb.SessionContext ctx) {
        mySessionCtx = ctx;
    }

    public void ejbCreate() throws javax.ejb.CreateException {
    }

    public void ejbActivate() {
    }

    public void ejbPassivate() {
    }

    public void ejbRemove() {
    }
}
