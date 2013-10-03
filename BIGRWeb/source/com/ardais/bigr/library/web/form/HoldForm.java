package com.ardais.bigr.library.web.form;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * @author JThompson
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

/**
 * @deprecated  Use the ResultsForm for all submits from the Results page.
 */

public class HoldForm extends BigrActionForm {

    private String[] _samples;
    private List _sampleHelpers;
    private String _deliverTo;
    private String _requestType;
    
    public HoldForm() {
        reset();
    }

    /**
     * @see com.ardais.bigr.web.form.BigrActionForm#doReset(BigrActionMapping, HttpServletRequest)
     */
    protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
        reset();
    }

    private void reset() {
        _samples = null;
        _sampleHelpers = null;
        _deliverTo = null;
    }

    /**
     * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(BigrActionMapping, HttpServletRequest)
     */
    public ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {

        ActionErrors errors = new ActionErrors();

        return errors;
    }

    /**
     * Returns the samples.
     * @return String[]
     */
    public String[] getSamples() {
        return _samples;
    }

    /**
     * Sets the samples.
     * @param samples The samples to set
     */
    public void setSamples(String[] samples) {
        _samples = samples;
    }

    /**
     * Returns the sampleHelpers.
     * @return List
     */
    public List getSampleHelpers() {
        return _sampleHelpers;
    }

    /**
     * Sets the sampleHelpers.
     * @param sampleHelpers The sampleHelpers to set
     */
    public void setSampleHelpers(List sampleHelpers) {
        _sampleHelpers = sampleHelpers;
    }

	/**
	 * Returns the deliverTo.
	 * @return String
	 */
	public String getDeliverTo() {
		return _deliverTo;
	}

	/**
	 * Sets the deliverTo.
	 * @param deliverTo The deliverTo to set
	 */
	public void setDeliverTo(String deliverTo) {
		_deliverTo = deliverTo;
	}

	/**
	 * Returns the requestType.
	 * @return String
	 */
	public String getRequestType() {
		return _requestType;
	}

	/**
	 * Sets the requestType.
	 * @param requestType The requestType to set
	 */
	public void setRequestType(String requestType) {
		_requestType = requestType;
	}

}
