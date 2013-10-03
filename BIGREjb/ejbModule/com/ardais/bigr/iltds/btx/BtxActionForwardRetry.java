package com.ardais.bigr.iltds.btx;

/**
 * This class is just like {@link BTXActionForward} except its
 * {@link BTXActionForward#isRetryActionForward() isRetryActionForward}
 * property is initialized to true.
 */
public class BtxActionForwardRetry extends BTXActionForward {

    /**
     * Constructor for BtxActionForwardRetry.  This initializes the
     * {@link BTXActionForward#isRetryActionForward() isRetryActionForward}
     * to true.
     */
    public BtxActionForwardRetry() {
        super();
        setRetryActionForward(true);
    }

}
