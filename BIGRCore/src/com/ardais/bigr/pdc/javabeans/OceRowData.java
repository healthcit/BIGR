package com.ardais.bigr.pdc.javabeans;

import java.io.Serializable;

/**
 * @author Nagraj
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class OceRowData implements Serializable {
	private String _lineId;
	private String _otherText;
	private String _status;
	private String _artsCode;
	private String _fullySpecifiedName;
    private String _whereClause;
  private boolean _clone = false;

	/**
	 * Creates new OceRowData.
	 */
	public OceRowData( String newOtherText, String status) {
		_status = status;
		_otherText = newOtherText;
	}
	
	/**
	 * Creates new OceRowData.
	 */
	public OceRowData(String newLineId, String newOtherText, String status) {
		 this(newOtherText, status);				 
		 _lineId = newLineId;	
	}
	
	/**
	 * Creates new OceRowData.
	 */
	public OceRowData() {
	}

	/**
	 * Returns the lineId.
	 * @return String
	 */
	public String getLineId() {
		return _lineId;
	}

	/**
	 * Returns the otherText.
	 * @return String
	 */
	public String getOtherText() {
		return _otherText;
	}

	/**
	 * Sets the lineId.
	 * @param lineId The lineId to set
	 */
	public void setLineId(String lineId) {
		_lineId = lineId;
	}

	/**
	 * Sets the otherText.
	 * @param otherText The otherText to set
	 */
	public void setOtherText(String otherText) {
		_otherText = otherText;
	}

	/**
	 * Returns the artsCode.
	 * @return String
	 */
	public String getArtsCode() {
		return _artsCode;
	}

	/**
	 * Returns the status.
	 * @return String
	 */
	public String getStatus() {
		return _status;
	}

	/**
	 * Sets the artsCode.
	 * @param artsCode The artsCode to set
	 */
	public void setArtsCode(String artsCode) {
		_artsCode = artsCode;
	}

	/**
	 * Sets the status.
	 * @param status The status to set
	 */
	public void setStatus(String status) {
		_status = status;
	}

	/**
	 * Returns the fullySpecifiedName.
	 * @return String
	 */
	public String getFullySpecifiedName() {
		return _fullySpecifiedName;
	}

	/**
	 * Sets the fullySpecifiedName.
	 * @param fullySpecifiedName The fullySpecifiedName to set
	 */
	public void setFullySpecifiedName(String fullySpecifiedName) {
		_fullySpecifiedName = fullySpecifiedName;
	}

	/**
	 * Returns the whereClause.
	 * @return String
	 */
	public String getWhereClause() {
		return _whereClause;
	}

	/**
	 * Sets the whereClause.
	 * @param whereClause The whereClause to set
	 */
	public void setWhereClause(String whereClause) {
		_whereClause = whereClause;
	}
  /**
   * Returns the clone.
   * @return boolean
   */
  public boolean isClone() {
    return _clone;
  }

  /**
   * Sets the clone.
   * @param clone The clone to set
   */
  public void setClone(boolean clone) {
    _clone = clone;
  }

}
