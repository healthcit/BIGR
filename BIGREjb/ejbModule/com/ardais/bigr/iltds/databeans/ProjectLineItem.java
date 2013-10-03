/**
 * Created on Feb 5, 2003
 */
package com.ardais.bigr.iltds.databeans;

import java.util.Date;

/**
 * @author jesielionis
 *
 * Databean to represent one line item in a project.
 */
public class ProjectLineItem implements java.io.Serializable {
	private static final long serialVersionUID = 3822518250793496754L;

	private int _lineNumber;
	private String _ardaisUserId;
	private String _projectName;
	private String _ardaisAccountKey;
	private String _sampleId;
	private Date _requestDate;
	
	/**
	 * Constructor for ProjectLineItem.
	 */
	public ProjectLineItem() {
		super();
	}
	/**
	 * Returns the ardaisAccountKey.
	 * @return String
	 */
	public String getArdaisAccountKey() {
		return _ardaisAccountKey;
	}

	/**
	 * Returns the ardaisUserId.
	 * @return String
	 */
	public String getArdaisUserId() {
		return _ardaisUserId;
	}

	/**
	 * Returns the lineNumber.
	 * @return int
	 */
	public int getLineNumber() {
		return _lineNumber;
	}

	/**
	 * Returns the projectName.
	 * @return String
	 */
	public String getProjectName() {
		return _projectName;
	}

	/**
	 * Returns the requestDate.
	 * @return Date
	 */
	public Date getRequestDate() {
		return _requestDate;
	}

	/**
	 * Returns the sampleId.
	 * @return String
	 */
	public String getSampleId() {
		return _sampleId;
	}

	/**
	 * Sets the ardaisAccountKey.
	 * @param ardaisAccountKey The ardaisAccountKey to set
	 */
	public void setArdaisAccountKey(String ardaisAccountKey) {
		_ardaisAccountKey = ardaisAccountKey;
	}

	/**
	 * Sets the ardaisUserId.
	 * @param ardaisUserId The ardaisUserId to set
	 */
	public void setArdaisUserId(String ardaisUserId) {
		_ardaisUserId = ardaisUserId;
	}

	/**
	 * Sets the lineNumber.
	 * @param lineNumber The lineNumber to set
	 */
	public void setLineNumber(int lineNumber) {
		_lineNumber = lineNumber;
	}

	/**
	 * Sets the projectName.
	 * @param projectName The projectName to set
	 */
	public void setProjectName(String projectName) {
		_projectName = projectName;
	}

	/**
	 * Sets the requestDate.
	 * @param requestDate The requestDate to set
	 */
	public void setRequestDate(Date requestDate) {
		_requestDate = requestDate;
	}

	/**
	 * Sets the sampleId.
	 * @param sampleId The sampleId to set
	 */
	public void setSampleId(String sampleId) {
		_sampleId = sampleId;
	}

}