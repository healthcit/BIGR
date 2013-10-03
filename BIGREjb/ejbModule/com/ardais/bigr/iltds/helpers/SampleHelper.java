package com.ardais.bigr.iltds.helpers;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.assistants.SampleDataBean;
import com.ardais.bigr.orm.helpers.BigrGbossData;

/**
 * <code>SampleHelper</code> aids in the generation of JSPs
 * that allow viewing and editing of samples.
 */
public class SampleHelper extends JspHelper {

	// Constants that specify request parameter names.
	public static final String ID_SAMPLE_ID = "sampleId";
	
	// The data bean that holds the sample data.
	private SampleDataBean _dataBean;

	// Helpers for the date fields.
	private DateHelper _deliveredDate;
	private DateHelper _dividedDate;
	private DateHelper _pathVerifiedDate;
	private DateHelper _requestedDate;
	private DateHelper _submittedToPathDate;
/**
 * Creates an empty <code>SampleHelper</code>.
 */
public SampleHelper() {
	super();
}
/**
 * Creates a <code>SampleHelper</code> using a data bean
 * that holds the data that this helper will display.
 */
public SampleHelper(SampleDataBean dataBean) {
	this();
	_dataBean = dataBean;
}
/**
 * Returns this sample's acellular stroma value, suitable for display. 
 * If the value is <code>null</code>, then the empty string
 * is returned.
 * 
 * @return  This sample's acellular stroma value.
 */
public String getAcellularStroma() {
	String value = null;
	if (_dataBean != null) {
		value = _dataBean.getAcellularStroma();
	}
	return (value == null) ? "" : value;
}
/**
 * Returns this sample's Ardais id, suitable for display. 
 * If the id is <code>null</code>, then the empty string
 * is returned.
 * 
 * @return  This sample's Ardais id.
 */
public String getArdaisId() {
	String id = null;
	if (_dataBean != null) {
		id = _dataBean.getArdaisId();
	}
	return (id == null) ? "" : id;
}
/**
 * Returns this sample's ASM appearance, suitable for display. 
 * If the appearance is <code>null</code>, then the empty string
 * is returned.
 * 
 * @return  This sample's ASM appearance.
 */
public String getAsmAppearance() {
	String value = null;
	if (_dataBean != null) {
		value = _dataBean.getAsmAppearance();
	}
	return (value == null) ? "" : value;
}
/**
 * Returns this sample's ASM position, suitable for display. 
 * If the position is <code>null</code>, then the empty string
 * is returned.
 * 
 * @return  This sample's ASM position.
 */
public String getAsmPosition() {
	String value = getRawAsmPosition();
	return (value == null) ? "" : value;
}
/**
 * Returns the account of the cart that this sample is in, if any.
 * If the cart account is <code>null</code>, then the empty string
 * is returned.
 * 
 * @return  This sample's cart account.
 */
public String getCartAccount() {
	String value = getRawCartAccount();
	return (value == null) ? "" : value;
}
/**
 * Returns the user of the cart that this sample is in, if any.
 * If the cart user is <code>null</code>, then the empty string
 * is returned.
 * 
 * @return  This sample's cart user.
 */
public String getCartUser() {
	String value = getRawCartUser();
	return (value == null) ? "" : value;
}
/**
 * Returns this sample's case id, suitable for display. 
 * If the id is <code>null</code>, then the empty string
 * is returned.
 * 
 * @return  This sample's case id.
 */
public String getCaseId() {
	String id = null;
	if (_dataBean != null) {
		id = _dataBean.getCaseId();
	}
	return (id == null) ? "" : id;
}
/**
 * Returns this sample's cellular stroma value, suitable for display. 
 * If the value is <code>null</code>, then the empty string
 * is returned.
 * 
 * @return  This sample's cellular stroma value.
 */
public String getCellularStroma() {
	String value = null;
	if (_dataBean != null) {
		value = _dataBean.getCellularStroma();
	}
	return (value == null) ? "" : value;
}
/**
 * Returns this sample's external comments, suitable for display. 
 * If the comments are <code>null</code>, then the empty string
 * is returned.
 * 
 * @return  This sample's external comments.
 */
public String getComments() {
	String value = null;
	if (_dataBean != null) {
		value = _dataBean.getComments();
	}
	return (value == null) ? "" : value;
}
/**
 * Returns the date that this sample's was delivered to histology or R&D,
 * suitable for display.  If the date is <code>null</code>, then 
 * the empty string is returned.
 * 
 * @return  This sample's delivered to histology or R&D date.
 */
public String getDeliveredDate() {
	DateHelper helper = getDeliveredDateHelper();
	return (helper == null) ? "" : helper.getFormattedDate();
}
/**
 * Returns a <code>DateHelper</code> for the date this sample 
 * was delivered to histology or R&D.  <code>null</code> is returned 
 * if there is no delivered date.
 * 
 * @return  The delivered date <code>DateHelper</code>.
 */
private DateHelper getDeliveredDateHelper() {
	if ((_deliveredDate == null) && (_dataBean != null)) {
		java.sql.Date deliveredDate = _dataBean.getDeliveredDate();
		if (deliveredDate != null) {
			_deliveredDate = new DateHelper(deliveredDate);
		}
	}
	return _deliveredDate;
}
/**
 * Returns where this sample was delivered, either histology or R&D, suitable
 * for display.  If the delivered to is <code>null</code>, then the empty
 * string is returned.
 * 
 * @return  Where this sample was delivered to.
 */
public String getDeliveredTo() {
	String value = getRawDeliveredTo();
	if (value != null) {
		if (value.equals(FormLogic.SMPL_ARMVTOPATH)) {
			return "Histology";
		}
		else if (value.equals(FormLogic.SMPL_CORND)) {
			return "R & D";
		}
    else if (value.equals(FormLogic.SMPL_CHECKEDOUT)) {
      return "Checked Out";
    }
	}
	return "";
}
/**
 * Returns this sample's diagnosis, suitable for display. 
 * If the diagnosis is <code>null</code>, then the empty string
 * is returned.
 * 
 * @return  This sample's diagnosis.
 */
public String getDiagnosis() throws ApiException {
	String dx = null;
	if (_dataBean != null) {
		dx = _dataBean.getProductDiagnosis();
		if (dx == null) {
			dx = _dataBean.getConsentDiagnosis();
		}
	}
	return (dx == null) ? "" : BigrGbossData.getDiagnosisDescription(dx);
}
/**
 * Returns the date that this sample was divided, suitable for 
 * display.  If the date is <code>null</code>, then the empty 
 * string is returned.  This is only relevant for paraffin samples.
 * 
 * @return  This sample's divided date.
 */
public String getDividedDate() {
	DateHelper helper = getDividedDateHelper();
	return (helper == null) ? "" : helper.getFormattedDate();
}
/**
 * Returns a <code>DateHelper</code> for the sample's divided date.
 * <code>null</code> is returned if there is no divided date.
 * 
 * @return  The divided date <code>DateHelper</code>.
 */
private DateHelper getDividedDateHelper() {
	if ((_dividedDate == null) && (_dataBean != null)) {
		java.sql.Date dividedDate = _dataBean.getDividedDate();
		if (dividedDate != null) {
			_dividedDate = new DateHelper(dividedDate);
		}
	}
	return _dividedDate;
}
/**
 * Returns a summary of the division status of the sample.
 * 
 * @return  The division status.
 */
public String getDivisionSummary() {
	if (_dataBean != null) {
		String canBeDivided = _dataBean.getCanBeDivided();
		canBeDivided = (canBeDivided == null) ? "U" : canBeDivided;
		return _dataBean.getFormat() + "." + canBeDivided;
	}
	return "";
}
/**
 * Returns this sample's format, suitable for display. 
 * If the format is <code>null</code>, then the empty string
 * is returned.
 * 
 * @return  This sample's format.
 */
public String getFormat() {
	String value = null;
	if (_dataBean != null) {
		value = _dataBean.getFormat();
	}
	return (value == null) ? "" : value;
}
/**
 * Returns this sample's sample id, suitable for display. 
 * If the id is <code>null</code>, then the empty string
 * is returned.
 * 
 * @return  This sample's id.
 */
public String getId() {
	String value = getRawId();
	return (value == null) ? "" : value;
}
/**
 * Returns this sample's internal comments, suitable for display. 
 * If the comments are <code>null</code>, then the empty string
 * is returned.
 * 
 * @return  This sample's internal comments.
 */
public String getInternalComments() {
	String value = null;
	if (_dataBean != null) {
		value = _dataBean.getInternalComments();
	}
	return (value == null) ? "" : value;
}
/**
 * Returns this sample's line item id, suitable for display. 
 * If the line item id is <code>null</code>, then the empty string
 * is returned.
 * 
 * @return  This sample's line item id.
 */
public String getLineItemId() {
	String value = getRawLineItemId();
	return (value == null) ? "" : value;
}
/**
 * Returns this sample's necrosis value, suitable for display. 
 * If the value is <code>null</code>, then the empty string
 * is returned.
 * 
 * @return  This sample's necrosis value.
 */
public String getNecrosis() {
	String value = null;
	if (_dataBean != null) {
		value = _dataBean.getNecrosis();
	}
	return (value == null) ? "" : value;
}
/**
 * Returns this sample's pathology status, suitable for display. 
 * If the status is <code>null</code>, then the empty string
 * is returned.
 * 
 * @return  This sample's pathology status.
 */
public String getPathStatus() {
	String status = getRawPathStatus();
	return (status == null) ? "" : status;
}
/**
 * Returns the date that this sample's was path verified,
 * suitable for display.  If the date is <code>null</code>, then 
 * the empty string is returned.
 * 
 * @return  This sample's path verified date.
 */
public String getPathVerifiedDate() {
	DateHelper helper = getPathVerifiedDateHelper();
	return (helper == null) ? "" : helper.getFormattedDate();
}
/**
 * Returns a <code>DateHelper</code> for the sample's path verified date.
 * <code>null</code> is returned if there is no path verified date.
 * 
 * @return  The path verified date <code>DateHelper</code>.
 */
private DateHelper getPathVerifiedDateHelper() {
	if ((_pathVerifiedDate == null) && (_dataBean != null)) {
		java.sql.Date pathVerifiedDate = _dataBean.getPathVerifiedDate();
		if (pathVerifiedDate != null) {
			_pathVerifiedDate = new DateHelper(pathVerifiedDate);
		}
	}
	return _pathVerifiedDate;
}
/**
 * Returns this sample's project id, suitable for display. 
 * If the project id is <code>null</code>, then the empty string
 * is returned.
 * 
 * @return  This sample's project id.
 */
public String getProjectId() {
	String value = null;
	if (_dataBean != null) {
		value = _dataBean.getProjectId();
	}
	return (value == null) ? "" : value;
}
/**
 * Returns this sample's ASM position.  If the ASM position 
 * is <code>null</code>, then <code>null</code> is returned.
 * 
 * @return  This sample's ASM position.
 */
private String getRawAsmPosition() {
	if (_dataBean != null) {
		return _dataBean.getAsmPosition();
	}
	return null;
}
/**
 * Returns the account of the cart that this sample is in, if any.
 * If the cart account is <code>null</code>, then <code>null</code>
 * is returned.
 * 
 * @return  This sample's cart account.
 */
public String getRawCartAccount() {
	return (_dataBean == null) ? null : _dataBean.getCartAccount();
}
/**
 * Returns the user of the cart that this sample is in, if any.
 * If the cart user is <code>null</code>, then <code>null</code>
 * is returned.
 * 
 * @return  This sample's cart user.
 */
public String getRawCartUser() {
	return (_dataBean == null) ? null : _dataBean.getCartUser();
}
/**
 * Returns where this sample was delivered, either histology or R&D, as
 * the corresponding sample status.  If the delivered to is
 * <code>null</code>, then <code>null</code> is returned.
 * 
 * @return  Where this sample was delivered to.
 */
private String getRawDeliveredTo() {
	return (_dataBean != null) ? _dataBean.getDeliveredTo() : null;
}
/**
 * Returns this sample's sample id.  If the id is 
 * <code>null</code>, then null is returned.
 * 
 * @return  This sample's id.
 */
public String getRawId() {
	if (_dataBean != null) {
		return _dataBean.getId();
	}
	return null;
}
/**
 * Returns this sample's line item id, suitable for display. 
 * If the line item id is <code>null</code>, then <code>null</code>
 * is returned.
 * 
 * @return  This sample's line item id.
 */
public String getRawLineItemId() {
	return (_dataBean == null) ? null : _dataBean.getLineItemId();
}
/**
 * Returns this sample's pathology status.  If the path status is
 * <code>null</code>, then <code>null</code> is returned.
 * 
 * @return  This sample's pathology status.
 */
public String getRawPathStatus() {
	return (_dataBean != null) ? _dataBean.getPathStatus() : null;
}
/**
 * Returns this sample's requested date, suitable for display. 
 * If the date is <code>null</code>, then the empty string
 * is returned.
 * 
 * @return  This sample's requested date.
 */
public String getRequestedDate() {
	DateHelper helper = getRequestedDateHelper();
	return (helper == null) ? "" : helper.getFormattedDate();
}
/**
 * Returns a <code>DateHelper</code> for the sample's requested date.
 * <code>null</code> is returned if there is no requested date.
 * 
 * @return  The requested date <code>DateHelper</code>.
 */
private DateHelper getRequestedDateHelper() {
	if ((_requestedDate == null) && (_dataBean != null)) {
		java.sql.Date requestedDate = _dataBean.getRequestedDate();
		if (requestedDate != null) {
			_requestedDate = new DateHelper(requestedDate);
		}
	}
	return _requestedDate;
}
/**
 * Returns this sample's appearance, suitable for display. 
 * If the appearance is <code>null</code>, then the empty string
 * is returned.
 * 
 * @return  This sample's appearance.
 */
public String getSampleAppearance() {
	String value = null;
	if (_dataBean != null) {
		value = _dataBean.getSampleAppearance();
	}
	return (value == null) ? "" : value;
}
/**
 * Returns the date that this sample's was submitted to pathology,
 * suitable for display.  If the date is <code>null</code>, then 
 * the empty string is returned.
 * 
 * @return  This sample's submitted to pathology date.
 */
public String getSubmittedToPathDate() {
	DateHelper helper = getSubmittedToPathDateHelper();
	return (helper == null) ? "" : helper.getFormattedDate();
}
/**
 * Returns a <code>DateHelper</code> for the date this sample 
 * was submitted to pathology.  <code>null</code> is returned 
 * if there is no submitted to pathology date.
 * 
 * @return  The submitted to pathology date <code>DateHelper</code>.
 */
private DateHelper getSubmittedToPathDateHelper() {
	if ((_submittedToPathDate == null) && (_dataBean != null)) {
		java.sql.Date submittedToPathDate = _dataBean.getSubmittedToPathDate();
		if (submittedToPathDate != null) {
			_submittedToPathDate = new DateHelper(submittedToPathDate);
		}
	}
	return _submittedToPathDate;
}
/**
 * Returns this sample's tissue, suitable for display. 
 * If the tissue is <code>null</code>, then the empty string
 * is returned.
 * 
 * @return  This sample's tissue.
 */
public String getTissue() throws ApiException {
	String tc = null;
	if (_dataBean != null) {
		tc = _dataBean.getProductTissue();
		if (tc == null) {
			tc = _dataBean.getAsmTissue();
		}
	}
	return (tc == null) ? "" : BigrGbossData.getTissueDescription(tc);
}
/**
 * Returns this sample's viable lesional cell value, suitable for display. 
 * If the value is <code>null</code>, then the empty string
 * is returned.
 * 
 * @return  This sample's viable lesional cell value.
 */
public String getViableLesionalCell() {
	String value = null;
	if (_dataBean != null) {
		value = _dataBean.getViableLesionalCell();
	}
	return (value == null) ? "" : value;
}
/**
 * Returns this sample's viable ni content value, suitable for display. 
 * If the value is <code>null</code>, then the empty string
 * is returned.
 * 
 * @return  This sample's viable ni content value.
 */
public String getViableNiContent() {
	String value = null;
	if (_dataBean != null) {
		value = _dataBean.getViableNiContent();
	}
	return (value == null) ? "" : value;
}
/**
 * Returns this sample's viable tumor cell value, suitable for display. 
 * If the value is <code>null</code>, then the empty string
 * is returned.
 * 
 * @return  This sample's viable tumor cell value.
 */
public String getViableTumorCell() {
	String value = null;
	if (_dataBean != null) {
		value = _dataBean.getViableTumorCell();
	}
	return (value == null) ? "" : value;
}
/**
 * Returns <code>true</code> if this sample has external comments;
 * <code>false</code> otherwise.
 * 
 * @return  A boolean indicating whether this sample has external comments.
 */
public boolean hasComments() {
	if ((_dataBean != null) && (_dataBean.getComments() != null)) {
		return true;
	}
	return false;
}
/**
 * Returns <code>true</code> if this sample has internal comments;
 * <code>false</code> otherwise.
 * 
 * @return  A boolean indicating whether this sample has internal comments.
 */
public boolean hasInternalComments() {
	if ((_dataBean != null) && (_dataBean.getInternalComments() != null)) {
		return true;
	}
	return false;
}
/**
 * Returns <code>true</code> if this sample has been put on hold;
 * <code>false</code> otherwise.
 * 
 * @return  <code>true</code> if this sample has been put on hold
 */
public boolean isOnHold() {
	if (_dataBean != null) {
		Boolean b = _dataBean.isOnHold();
		if ((b != null) && (b.equals(Boolean.TRUE))) {
			return true;
		}
	}
	return false;
}
/**
 * Returns <code>true</code> if this sample is a parent;
 * <code>false</code> otherwise.
 * 
 * @return  An indication of whether the sample is a parent.
 */
public boolean isParent() {
	String value = getRawAsmPosition();
	if ((value != null) && (value.length() == 2)) {
		return true;
	}
	return false;
}
/**
 * Returns <code>true</code> if this sample is path verified;
 * <code>false</code> otherwise.
 * 
 * @return  <code>true</code> if this sample is path verified.
 */
public boolean isPathVerified() {
	if (_dataBean != null) {
		Boolean b = _dataBean.isPathVerified();
		if ((b != null) && (b.equals(Boolean.TRUE))) {
			return true;
		}
	}
	return false;
}
/**
 * Returns <code>true</code> if this sample is restricted;
 * <code>false</code> otherwise.
 * 
 * @return  <code>true</code> if this sample is restricted.
 */
public boolean isRestricted() {
	if (_dataBean != null) {
		Boolean b = _dataBean.isRestricted();
		if ((b != null) && (b.equals(Boolean.TRUE))) {
			return true;
		}
	}
	return false;
}
/**
 * Returns <code>true</code> if this sample has been submitted
 * to pathology; <code>false</code> otherwise.
 * 
 * @return  <code>true</code> if this sample has been submitted to pathology.
 */
public boolean isSubmittedToPath() {
	if (_dataBean != null) {
		Boolean b = _dataBean.isSubmittedToPath();
		if ((b != null) && (b.equals(Boolean.TRUE))) {
			return true;
		}
	}
	return false;
}
/**
 * Returns <code>true</code> if the work order data associated 
 * with this helper is valid; otherwise returns <code>false</code>.
 * Generally this method is called after calling the constructor
 * that takes a <code>ServletRequest</code> as input, since it
 * is desirable to validate the request parameters.  If validation
 * fails, the <code>getMessages</code> method can be called to
 * display a message to the user indicating the problems.
 *
 * @return  <code>true</code> if all parameters are valid;
 *			<code>false</code> otherwise.
 */
public boolean isValid() {
	return true;
}
}
