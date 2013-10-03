package com.ardais.bigr.query.generator;

import com.ardais.bigr.api.ApiException;

/**
 * This exception indicates that a database LIKE pattern was specified to match some diagnoses or
 * or tissues but did not match anything.  For the short term, we are considering this an exception,
 * much like submitting a diagnosis or tissue code that does not exist in our heirarchy tables.
 * 
 * Ideally, processing (including validation) of these matching patterns can be performed
 * early on, perhaps in the Web area or even in the mechanics of the GUI.
 * 
 * @see com.ardais.bigr.query.generator.NoMatchingTissuesAndDiagnosesException.
 */
public class NoMatchingDiagnosisException extends NoMatchTissueOrDiagnosisException {

    /**
     * Constructor for NoMatchingDataException.
     */
    public NoMatchingDiagnosisException(String pattern) {
        super(pattern);
    }

    /**
     * @see com.ardais.bigr.query.generator.NoMatchTissueOrDiagnosisException#getErrorCode()
     */
    public String getErrorCode() {
        return "library.ss.error.nodiagnosisforpattern";
    }

}
