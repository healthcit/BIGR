package com.ardais.bigr.iltds.assistants;

import java.io.*;
import java.sql.Date;

/**
 * This class represents the data for a sample.
 */
public class SampleDataBean implements Serializable {
	private String _acellularStroma;
	private String _ardaisId;
	private String _asmAppearance;  //aka specimen type
	private String _asmPosition;
	private String _canBeDivided;
	private String _cartAccount;
	private String _cartUser;
	private String _caseId;
	private String _cellularStroma;
	private String _comments;
	private String _deliveredTo;
	private Date _deliveredDate;
	private Date _dividedDate;
	private String _consentDx;
	private String _format;
	private String _id;
	private String _internalComments;
	private Boolean _isOnHold;
	private String _lineItemId;
	private String _necrosis;
	private String _pathStatus;
	private Boolean _pathVerified;
	private Date _pathVerifiedDate;
	private String _productDx;
	private String _productTissue;
	private String _projectId;
	private Date _requestedDate;
	private Boolean _restricted;
	private String _sampleAppearance;  //aka specimen type
	private Boolean _submittedToPath;
	private Date _submittedToPathDate;
	private String _asmTissue;
	private String _viableLesionalCell;
	private String _viableNiContent;
	private String _viableTumorCell;
/**
 * Creates a new empty <code>SampleDataBean</code>.
 */
public SampleDataBean() {
	super();
}
/**
 * Returns this sample's acellular stroma value.
 * 
 * @return  The acellular stroma.
 */
public String getAcellularStroma() {
	return _acellularStroma;
}
/**
 * Returns this Ardais id with which this sample is associated.
 * 
 * @return  The Ardais id.
 */
public String getArdaisId() {
	return _ardaisId;
}
/**
 * Returns this sample's appearance based on the appearance specified
 * in the ASM.
 * 
 * @return  The ASM appearance.
 */
public String getAsmAppearance() {
	return _asmAppearance;
}
/**
 * Returns this sample's ASM position.
 * 
 * @return  The ASM position.
 */
public String getAsmPosition() {
	return _asmPosition;
}
/**
 * Returns the tissue type of this sample's ASM.
 * 
 * @return  The tissue type.
 */
public String getAsmTissue() {
	return _asmTissue;
}
/**
 * Returns whether this sample can be divided or not.  Returns
 * one of "U", "Y" or "N", for unknown, yes or no, respectively.
 * 
 * @return  Whether this sample can be divided.
 */
public String getCanBeDivided() {
	return _canBeDivided;
}
/**
 * Returns the account of the cart that this sample is in, if any.
 * 
 * @return  The cart account.
 */
public String getCartAccount() {
	return _cartAccount;
}
/**
 * Returns the user of the cart that this sample is in, if any.
 * 
 * @return  The cart user.
 */
public String getCartUser() {
	return _cartUser;
}
/**
 * Returns this case id with which this sample is associated.
 * 
 * @return  The case id.
 */
public String getCaseId() {
	return _caseId;
}
/**
 * Returns this sample's cellular stroma value.
 * 
 * @return  The cellular stroma.
 */
public String getCellularStroma() {
	return _cellularStroma;
}
/**
 * Returns this sample's external comments
 * 
 * @return  The external comments.
 */
public String getComments() {
	return _comments;
}
/**
 * Returns the diagnosis of this sample's consent.
 * 
 * @return  The diagnosis.
 */
public String getConsentDiagnosis() {
	return _consentDx;
}
/**
 * Returns the date that this sample was delivered to histology or R&D.
 * 
 * @return  The delivered date.
 */
public Date getDeliveredDate() {
	return _deliveredDate;
}
/**
 * Returns where this sample was delivered, either histology or pathology,
 * as the corresponding sample status.
 * 
 * @return  Where this sample was delivered.
 */
public String getDeliveredTo() {
	return _deliveredTo;
}
/**
 * Returns the date that this sample was divided.  Only
 * relevant for paraffin samples.
 * 
 * @return  The divided date.
 */
public Date getDividedDate() {
	return _dividedDate;
}
/**
 * Returns this sample's format.
 * 
 * @return  The format.
 */
public String getFormat() {
	return _format;
}
/**
 * Returns this sample's id.
 * 
 * @return  The id.
 */
public String getId() {
	return _id;
}
/**
 * Returns this sample's internal comments
 * 
 * @return  The internal comments.
 */
public String getInternalComments() {
	return _internalComments;
}
/**
 * Returns this sample's line item id.
 * 
 * @return  The line item id.
 */
public String getLineItemId() {
	return _lineItemId;
}
/**
 * Returns this sample's necrosis value.
 * 
 * @return  The necrosis value.
 */
public String getNecrosis() {
	return _necrosis;
}
/**
 * Insert the method's description here.
 * Creation date: (2/27/2002 10:13:34 AM)
 * @return java.lang.String
 */
public String getPathStatus() {
	return _pathStatus;
}
/**
 * Returns the date that this sample was pathology verified.
 * 
 * @return  The pathology verified date.
 */
public Date getPathVerifiedDate() {
	return _pathVerifiedDate;
}
/**
 * Returns the diagnosis according to this sample's product id.
 * 
 * @return  The diagnosis.
 */
public String getProductDiagnosis() {
	return _productDx;
}
/**
 * Returns the tissue type according to this sample's product id.
 * 
 * @return  The tissue type.
 */
public String getProductTissue() {
	return _productTissue;
}
/**
 * Returns this sample's project id.
 * 
 * @return  The project id.
 */
public String getProjectId() {
	return _projectId;
}
/**
 * Returns the date that this sample was requested for
 * biological operations.
 * 
 * @return  The request date.
 */
public Date getRequestedDate() {
	return _requestedDate;
}
/**
 * Returns this sample's appearance at the sample level.
 * 
 * @return  The sample-level appearance.
 */
public String getSampleAppearance() {
	return _sampleAppearance;
}
/**
 * Returns the date that this sample was submitted to pathology.
 * 
 * @return  The submitted to pathology date.
 */
public Date getSubmittedToPathDate() {
	return _submittedToPathDate;
}
/**
 * Returns this sample's viable lesional cell value.
 * 
 * @return  The viable lesional cell value.
 */
public String getViableLesionalCell() {
	return _viableLesionalCell;
}
/**
 * Returns this sample's viable nicontent value.
 * 
 * @return  The viable nicontent.
 */
public String getViableNiContent() {
	return _viableNiContent;
}
/**
 * Returns this sample's viable tumor cell value.
 * 
 * @return  The viable tumor cell value.
 */
public String getViableTumorCell() {
	return _viableTumorCell;
}
/**
 * Returns <code>true</code> if this sample is on hold.
 * 
 * @return  <code>true</code> if this sample is on hold.
 */
public Boolean isOnHold() {
	return _isOnHold;
}
/**
 * Returns <true> if this sample is path verified.
 * 
 * @return  <true> if this sample is path verified.
 */
public Boolean isPathVerified() {
	return _pathVerified;
}
/**
 * Returns <true> if this sample is restricted.
 * 
 * @return  <true> if this sample is restricted.
 */
public Boolean isRestricted() {
	return _restricted;
}
/**
 * Returns <true> if this sample was submitted to pathology.
 * 
 * @return  <true> if this sample was submitted to pathology.
 */
public Boolean isSubmittedToPath() {
	return _submittedToPath;
}
/**
 * Sets this sample's acellular stroma value.
 * 
 * @param  acellularStroma  the acellular stroma value
 */
public void setAcellularStroma(String acellularStroma) {
	_acellularStroma = acellularStroma;
}
/**
 * Sets this sample's Ardais id.
 * 
 * @param  ardaisId  the Ardais id
 */
public void setArdaisId(String ardaisId) {
	_ardaisId = ardaisId;
}
/**
 * Sets this sample's appearance based on its ASM.
 * 
 * @param  asmAppearance  the ASM appearance
 */
public void setAsmAppearance(String asmAppearance) {
	_asmAppearance = asmAppearance;
}
/**
 * Sets this sample's position within the ASM.
 * 
 * @param  asmPosition  the ASM position
 */
public void setAsmPosition(String asmPosition) {
	_asmPosition = asmPosition;
}
/**
 * Sets the tissue type of this sample's ASM.
 * 
 * @param  tissue  the tissue type
 */
public void setAsmTissue(String tissue) {
	_asmTissue = tissue;
}
/**
 * Sets whether this sample can be divided or not.  Value should
 * be one of "U", "Y" or "N", for unknown, yes or no, respectively.
 * 
 * @param  canBeDivided  one of "U", "Y" or "N"
 */
public void setCanBeDivided(String canBeDivided) {
	_canBeDivided = canBeDivided;
}
/**
 * Sets the account of that cart that this sample is in, if any.
 * 
 * @param  cartAccount  the cart account
 */
public void setCartAccount(String cartAccount) {
	_cartAccount = cartAccount;
}
/**
 * Sets the user of that cart that this sample is in, if any.
 * 
 * @param  cartUser  the cart user
 */
public void setCartUser(String cartUser) {
	_cartUser = cartUser;
}
/**
 * Sets this sample's case id.
 * 
 * @param  caseId  the case id
 */
public void setCaseId(String caseId) {
	_caseId = caseId;
}
/**
 * Sets this sample's cellular stroma value.
 * 
 * @param  cellularStroma  the cellular stroma value
 */
public void setCellularStroma(String cellularStroma) {
	_cellularStroma = cellularStroma;
}
/**
 * Sets this sample's external comments.
 * 
 * @param  comments  the external comments
 */
public void setComments(String comments) {
	_comments = comments;
}
/**
 * Sets the diagnosis of this sample's consent.
 * 
 * @param  diagnosis  the diagnosis
 */
public void setConsentDiagnosis(String diagnosis) {
	_consentDx = diagnosis;
}
/**
 * Sets the date that this sample was delivered to histology or R&D.
 * 
 * @param  deliveredDate  the delivered date
 */
public void setDeliveredDate(Date deliveredDate) {
	_deliveredDate = deliveredDate;
}
/**
 * Sets where this sample was delivered, either histology or pathology.
 * The input parameter should be the corresponding sample status.
 * 
 * @param  deliveredTo  where the sample was delivered to
 */
public void setDeliveredTo(String deliveredTo) {
	_deliveredTo = deliveredTo;
}
/**
 * Sets the date that this sample was divided.  Only
 * relevant for paraffin samples.
 * 
 * @param  dividedDate  the date the sample was divided
 */
public void setDividedDate(Date dividedDate) {
	_dividedDate = dividedDate;
}
/**
 * Sets this sample's format.
 * 
 * @param  format  the format
 */
public void setFormat(String format) {
	_format = format;
}
/**
 * Sets this sample's id.
 * 
 * @param  id  the sample id
 */
public void setId(String id) {
	_id = id;
}
/**
 * Sets this sample's internal comments.
 * 
 * @param  comments  the internal comments
 */
public void setInternalComments(String comments) {
	_internalComments = comments;
}
/**
 * Sets this sample's line item id.
 * 
 * @param  lineItemId  the sample's line item id
 */
public void setLineItemId(String lineItemId) {
	_lineItemId = lineItemId;
}
/**
 * Sets this sample's necrosis value.
 * 
 * @param  necrosis  the necrosis value
 */
public void setNecrosis(String necrosis) {
	_necrosis = necrosis;
}
/**
 * Sets whether this sample is on hold or not.
 * 
 * @param  isOnHold  <code>true</code> if this sample has been put on hold
 */
public void setOnHold(Boolean isOnHold) {
	_isOnHold = isOnHold;
}
/**
 * Insert the method's description here.
 * Creation date: (2/27/2002 10:13:34 AM)
 * @param new_pathStatus java.lang.String
 */
public void setPathStatus(java.lang.String new_pathStatus) {
	_pathStatus = new_pathStatus;
}
/**
 * Sets whether this sample has been path verified or not.
 * 
 * @param  pathVerified  <code>true</code> if this sample has been path verified
 */
public void setPathVerified(Boolean pathVerified) {
	_pathVerified = pathVerified;
}
/**
 * Sets the date that this sample was pathology verified.
 * 
 * @param  pathVerifiedDate  the pathology verified date
 */
public void setPathVerifiedDate(Date pathVerifiedDate) {
	_pathVerifiedDate = pathVerifiedDate;
}
/**
 * Sets the diagnosis of this sample's product id.
 * 
 * @param  diagnosis  the diagnosis
 */
public void setProductDiagnosis(String diagnosis) {
	_productDx = diagnosis;
}
/**
 * Sets the tissue type of this sample's product id.
 * 
 * @param  tissue  the tissue type
 */
public void setProductTissue(String tissue) {
	_productTissue = tissue;
}
/**
 * Sets this sample's project id.
 * 
 * @param  projectId  the sample's project id
 */
public void setProjectId(String projectId) {
	_projectId = projectId;
}
/**
 * Sets the date that this sample was requested for biological operations.
 * 
 * @param  requestedDate  the requested date
 */
public void setRequestedDate(Date requestedDate) {
	_requestedDate = requestedDate;
}
/**
 * Sets whether this sample is restricted or not.
 * 
 * @param  restricted  <code>true</code> if this sample is restricted
 */
public void setRestricted(Boolean restricted) {
	_restricted = restricted;
}
/**
 * Sets this sample's appearance.
 * 
 * @param  sampleAppearance  the appearance
 */
public void setSampleAppearance(String sampleAppearance) {
	_sampleAppearance = sampleAppearance;
}
/**
 * Sets whether this sample has been submitted to pathology or not.
 * 
 * @param  submittedToPath  <code>true</code> if this sample has been submitted to pathology
 */
public void setSubmittedToPath(Boolean submittedToPath) {
	_submittedToPath = submittedToPath;
}
/**
 * Sets the date that this sample was submitted to pathology.
 * 
 * @param  submittedToPathDate  the submitted to pathology date
 */
public void setSubmittedToPathDate(Date submittedToPathDate) {
	_submittedToPathDate = submittedToPathDate;
}
/**
 * Sets this sample's viable lesional cell value.
 * 
 * @param  viableLesionalCell  the viable lesional cell value
 */
public void setViableLesionalCell(String viableLesionalCell) {
	_viableLesionalCell = viableLesionalCell ;
}
/**
 * Sets this sample's viable ni content value.
 * 
 * @param  viableNiContent  the viable ni content value
 */
public void setViableNiContent(String viableNiContent) {
	_viableNiContent = viableNiContent;
}
/**
 * Sets this sample's viable tumor cell value.
 * 
 * @param  viableTumorCell  the viable tumor cell value
 */
public void setViableTumorCell(String viableTumorCell) {
	_viableTumorCell = viableTumorCell;
}
}
