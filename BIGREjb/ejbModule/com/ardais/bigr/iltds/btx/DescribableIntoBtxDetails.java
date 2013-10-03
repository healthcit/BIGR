package com.ardais.bigr.iltds.btx;

/**
 * Objects that implement this interface can set the state of a BTXDetails
 * object, typically by copying information into it from the object's own
 * internal state.
 */
public interface DescribableIntoBtxDetails {
    
    /**
     * Set the state of the supplied BTXDetails object.
     *
     * @param btxDetails the BTXDetails object whose state will be set.
     *    This must not be null, and when it is null implementations of this
     *    method throw an IllegalArgumentException stating that the btxDetails
     *    parameter was null.
     */
    public void describeIntoBtxDetails(BTXDetails btxDetails);

}
