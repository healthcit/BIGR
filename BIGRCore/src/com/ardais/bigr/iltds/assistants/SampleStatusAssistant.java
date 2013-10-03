package com.ardais.bigr.iltds.assistants;

import com.ardais.bigr.beanutils.BigrBeanUtilsBean;

/**
 * A simple data bean to hold status type and date/time for a sample status.
 */
public class SampleStatusAssistant implements java.io.Serializable {
	private static final long serialVersionUID = -7440588819865090685L;

	private String _sampleBarcodeId = null;
	private String _statusTypeCode = null;
	private long _statusDateTime = 0;
	
public SampleStatusAssistant() {
    super();
}
  
public SampleStatusAssistant(SampleStatusAssistant sampleStatusAssistant) {
  this();
  BigrBeanUtilsBean.SINGLETON.copyProperties(this, sampleStatusAssistant);
}
/**
 * Create a status bean with specified sample id, type and date/time.
 *
 * @param sampleID the sample id that this status applies to
 * @param type the status type, e.g. "BOXSCAN"
 * @param datetime the date/time at which the status occurred
 */
public SampleStatusAssistant(String sampleID, String type, long datetime) {
    setSampleBarcodeId(sampleID);
	setStatusTypeCode(type);
	setStatusDateTime(datetime);
}
/**
 * Returns the sample id that this status applies to.
 *
 * @return the sample id
 */
public String getSampleBarcodeId() {
	return _sampleBarcodeId;
}
/**
 * Return the timestamp for the date/time at which this status occurred.
 *
 * @return the status date/time
 */
public long getStatusDateTime() {
	return _statusDateTime;
}
/**
 * Returns the type of this status, e.g. "BOXSCAN".
 *
 * @return the status type code
 */
public String getStatusTypeCode() {
	return _statusTypeCode;
}
/**
 * Sets the sample id that this status applies to.
 *
 * @param newValue the new sample id
 */
public void setSampleBarcodeId(String newValue) {
	_sampleBarcodeId = newValue;
}
/**
 * Sets the timestamp for when this status occurred.
 *
 * @param newValue the new status date/time
 */
public void setStatusDateTime(long newValue) {
	_statusDateTime = newValue;
}
/**
 * Sets the status type, e.g. "BOXSCAN".
 *
 * @param newValue the new status type code
 */
public void setStatusTypeCode(String newValue) {
	_statusTypeCode = newValue;
}
}
