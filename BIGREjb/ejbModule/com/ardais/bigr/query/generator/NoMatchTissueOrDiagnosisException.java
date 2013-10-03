package com.ardais.bigr.query.generator;

import com.ardais.bigr.api.ApiException;

/**
 * @author dfeldman
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public abstract class NoMatchTissueOrDiagnosisException extends ApiException {

    String filterDescription = null;
    String pattern = null;
    
    /**
     * Constructor for NoMatchTissueOrDiagnosisException.
     */
    public NoMatchTissueOrDiagnosisException(String pattern) {
        super("no match for: " + pattern);
        this.pattern = pattern;
    }
    
    /**
     * Set the user-readable description of the filter that caused this problem.
     */
    public void setFilterDescription(String fdesc) {
        filterDescription = fdesc;
    }
    
    public String getFilterDescription() {
        return filterDescription;
    }
    
    /**
     * @return the error code for this error.  Currently we give this to struts, so it should be
     * a valid struts error code.
     */
    public abstract String getErrorCode() ;
    

    /**
     * Returns the pattern.
     * @return String
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * Sets the pattern.
     * @param pattern The pattern to set
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

}
