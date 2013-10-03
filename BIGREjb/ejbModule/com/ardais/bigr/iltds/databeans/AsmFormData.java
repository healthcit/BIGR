package com.ardais.bigr.iltds.databeans;

import java.io.Serializable;

/**
 * @author sthomashow
 * 1/09/03
 * 
 * the start of a databean for AsmForm.  added this to enable
 * the use of procedureWithOther tag.
 * 
 */
public class AsmFormData implements Serializable {

    private static final long serialVersionUID = -6549450689283875759L;
	private String _procedure;
	private String _otherProcedure;

	/**
	 * Constructor for AsmFormData.
	 */
	public AsmFormData() {
		super();
	}

	/**
	 * Returns the _otherProcedure.
	 * @return String
	 */
	public String getOtherProcedure() {
		return _otherProcedure;
	}

	/**
	 * Returns the _procedure.
	 * @return String
	 */
	public String getProcedure() {
		return _procedure;
	}

	/**
	 * Sets the _otherProcedure.
	 * @param _otherProcedure The _otherProcedure to set
	 */
	public void setOtherProcedure(String otherProcedure) {
		_otherProcedure = otherProcedure;
	}

	/**
	 * Sets the _procedure.
	 * @param _procedure The _procedure to set
	 */
	public void setProcedure(String procedure) {
		_procedure = procedure;
	}
}
