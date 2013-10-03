package com.ardais.bigr.library.javabeans;

import java.util.Arrays;
import java.util.List;

/**
 * A simple pair of sample IDS that are available, and those that are unavailable. 
 * <p>
 * For example, if someone tries to put samples on hold, but some cannot be put on hold,
 * the successfully added, and unsuccssfully added can both be returned as a single object
 * using an instance of this class.
 */

/**
 * @deprecated.  this is not currently used
 */
public class SuccessFailureResultPair{

    private List _successItems;
    private List _failedItems;
    
    /**
     * Constructor for AvailableUnavailableSamplePair.
     */
    public SuccessFailureResultPair(String[] success, String[] failure) {
        _successItems = Arrays.asList(success);
        _failedItems = Arrays.asList(failure);
    }


    /**
     * Returns the failedItems.
     * @return List
     */
    public List getFailedItems() {
        return _failedItems;
    }

    /**
     * Returns the successItems.
     * @return List
     */
    public List getSuccessItems() {
        return _successItems;
    }

}
