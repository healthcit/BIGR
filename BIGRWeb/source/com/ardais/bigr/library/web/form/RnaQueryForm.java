package com.ardais.bigr.library.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * @author dfeldman
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class RnaQueryForm extends BigrActionForm {

    private String rnaVialId;

    public RnaQueryForm() {
        reset();
    }

    /**
     * @see com.ardais.bigr.web.form.BigrActionForm#doReset(BigrActionMapping, HttpServletRequest)
     */
    protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
        reset();
    }

    private void reset() {
        rnaVialId = null;
    }

    /**
     * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(BigrActionMapping, HttpServletRequest)
     */
    protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
        return null;
    }

    /**
     * Returns the rnaVialId.
     * @return String
     */
    public String getRnaVialId() {
        return rnaVialId;
    }

    /**
     * Sets the rnaVialId.
     * @param rnaVialId The rnaVialId to set
     */
    public void setRnaVialId(String rnaVialId) {
        this.rnaVialId = rnaVialId;
    }

}
