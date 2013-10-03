package com.ardais.bigr.query.generator;

import java.util.List;

import com.ardais.bigr.api.ApiException;

/**
 * This Exception holds a list of Exceptions pertaining to patterns for diagnoses and 
 * tissue types that did not match any actual codes in the system.
 * 
 * It contains all 
 * instances of @link com.ardais.bigr.query.generator.NoMatchingDaignosisOrTissueException
 * that are created during query construction from the Filters.  We need this container to 
 * batch up the messages instead of bombing out on the first one.
 */
public class NoMatchingTissuesAndDiagnosesException extends ApiException{

    List noMatchErrors;
    
    /**
     * Constructor for NoMatchingTissuesAndDiagnosesException.
     */
    public NoMatchingTissuesAndDiagnosesException(List noMatchErrors) {
        this.noMatchErrors = noMatchErrors;
    }

    /**
     * Return all the errors this container contains.
     */
    public List getExceptions() {
        return noMatchErrors;
    }
}
