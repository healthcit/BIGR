package com.ardais.bigr.iltds.btx;

import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.beans.ListGenerator;
import com.ardais.bigr.iltds.beans.ListGeneratorHome;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.IcpUtils;

/**
 * Represent the details of a LIMS Slide Quality Control transaction.
 * <p>
 * The transaction-specific fields are described below. See also
 * {@link BTXDetails BTXDetails} for fields taht are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setHistologist(String) Histologist}: The user performing the transaction.</li>
 * <li>{@link #setSampleId(String) SampleId}: The sample being QC'd.</li>
 * <li>{@link #setSlideID(String) SlideID}: The slide used in the QC process.</li>
 * </ul>
 *
 * <h4>Optional input fields.</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields.</h4>
 * <ul>
 * <li>{@link #setResult(String) Result}: The outcome of the QC transaction.</li>
 * </ul>
 */
public class BTXDetailsQualityControl extends BTXDetails implements java.io.Serializable {
	private String _histologist = null;
	private String _result = null;
	private String _slideID = null;
	private String _sampleId = null;

	public BTXDetailsQualityControl() {
		super();
	}
	/**
	 * Fill a business transaction history record object with information
	 * from this transaction details object.  This method will set <b>all</b>
	 * fields on the history record, even ones not used by the this type of
	 * transaction.  Fields that aren't used by this transaction type will be
	 * set to their initial default values.
	 * <p>
	 * This method is only meant to be used internally by the business
	 * transaction framework implementation.  Please don't use it anywhere else.
	 *
	 * @param history the history record object that will have its fields set to
	 *    the transaction information.
	 */
	public void describeIntoHistoryRecord(BTXHistoryRecord history) {
		super.describeIntoHistoryRecord(history);

		history.setAttrib1(getResult());
		history.setAttrib2(getSampleId());
		history.setAttrib3(getHistologist());
		history.setAttrib4(getSlideID());
	}
	/**
	 * Return an HTML string that defines how the transaction details are presented
	 * in a transaction-history web page.  Derived classes must override this method.
	 * <p>
	 * This method is protected, the corresponding public method is
	 * {@link #getDetailsAsHTML() getDetailsAsHTML}, which calls this method.
	 * getDetailsAsHTML handles common tasks such as returning the
	 * details in the case that the transaction represents a failed transaction
	 * (it has a non-null exceptionText property).  For such a transaction, the
	 * doGetDetailsAsHTML method will not be called.  This framework is intended
	 * to make it easier to implement doGetDetailsAsHTML in derived classes, as
	 * the code there may assume that the transaction succeeded and that the
	 * transaction's data fields aren't malformed.
	 * <p>
	 * <b>Implementation of this method must only make use of fields that are populated
	 * by the populateFromHistory method.</b>
	 *
	 * @return the HTML detail string.
	 */
	protected String doGetDetailsAsHTML() {
		// NOTE: This method must not make use of any fields that aren't
		// set by the populateFromHistoryRecord method.
		//
		// For this object type, the fields we can use here are:
		//   getSampleId
		//   getHistologist
		//   getSlideID
		//   getResult
        
        SecurityInfo securityInfo = getLoggedInUserSecurityInfo();

		StringBuffer sb = new StringBuffer(128);

		// The result has this form:
		//    <user id> performed slide quality control using slide
		//    <slide id> for sample <sample id> with the result <result>

    Escaper.htmlEscape(getHistologist(), sb);
		sb.append(" performed slide quality control using slide ");
		sb.append(IcpUtils.prepareLink(getSlideID(), securityInfo));
		sb.append(" for sample ");
		sb.append(IcpUtils.prepareLink(getSampleId(), securityInfo));
		sb.append(" with the result ");
		Escaper.htmlEscape(getResult(), sb);

		return sb.toString();
	}
	/**
	 * Return the business transaction type code for the transaction that this
	 * class represents.  Derived classes must override this method.
	 *
	 * @return the transaction type code.
	 */
	public String getBTXType() {
		return BTXDetails.BTX_TYPE_QUALITY_CONTROL;
	}
	/**
	 * Return a set of the object ids of the objects that are directly involved
	 * in this transaction.  This set does not contain the ids of objects that
	 * are considered to be indirectly involved in the transaction, and it does
	 * not include the user id of the user who performed the transaction.
	 * <p>
	 * For example, a transaction that scans a box of samples directly involves the box
	 * object and each of the sample objects, and indirectly involves the
	 * asm, asm form, case and donor objects for each sample.
	 *
	 * @return the set of directly involved object ids.
	 */
	public java.util.Set getDirectlyInvolvedObjects() {
		Set set = new HashSet();

		set.add(getSlideID());

		set.add(getSampleId());

		return set;
	}
	/**
	 * Return the histologist id performing the transaction.  This is a required input field.  
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getHistologist() {
		return _histologist;
	}
	/**
	 * Return the result of the the QC process. 
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getResult() {
		return _result;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (7/26/2002 1:45:06 PM)
	 * @return java.lang.String
	 */
	public java.lang.String getSampleId() {
		return _sampleId;
	}
	/**
	 * Return the list of slide id's used in the QC process.  This is a required input field.
	 * 
	 * @return com.ardais.bigr.iltds.assistants.IdList
	 */
	public String getSlideID() {
		return _slideID;
	}
	/**
	 * Populate the fields of this object with information contained in a
	 * business transaction history record object.  This method must set <b>all</b>
	 * fields on this object, as if it had been newly created immediately before
	 * this method was called.  A runtime exception is thrown if the transaction type
	 * represented by the history record doesn't match the transaction type represented
	 * by this object.
	 * <p>
	 * This method is only meant to be used internally by the business
	 * transaction framework implementation.  Please don't use it anywhere else.
	 *
	 * @param history the history record object that will be used as the
	 *    information source.  A runtime exception is thrown if this is null.
	 */
	public void populateFromHistoryRecord(BTXHistoryRecord history) {
		super.populateFromHistoryRecord(history);

		setResult(history.getAttrib1());
		setSampleId(history.getAttrib2());
		setHistologist(history.getAttrib3());
		setSlideID(history.getAttrib4());
	}
	/**
	 * Set the histologist id performing the transaction.
	 * 
	 * @param new_userId java.lang.String
	 */
	public void setHistologist(String newUserId) {
		_histologist = newUserId;
	}
	/**
	 * Set the result of the QC process.
	 * 
	 * @param new_result java.lang.String
	 */
	public void setResult(String newResult) {
		_result = newResult;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (7/26/2002 1:45:06 PM)
	 * @param newSampleId java.lang.String
	 */
	public void setSampleId(java.lang.String newSampleId) {
		_sampleId = newSampleId;
	}
	/**
	 * Set the slide ids being used in the transaction.
	 * 
	 * @param new_slideIDs com.ardais.bigr.iltds.assistants.IdList
	 */
	public void setSlideID(String newSlideID) {
		_slideID = newSlideID;
	}
	/**
	 * Set the slide ids being used in the transaction.
	 * 
	 * @param new_slideIDs com.ardais.bigr.iltds.assistants.IdList
	 */
	public void setSlideIDandSampleID(String newSlideID) throws Exception {
		_slideID = newSlideID;

    ListGeneratorHome home = (ListGeneratorHome) EjbHomes.getHome(ListGeneratorHome.class);
    ListGenerator list = home.create();
		setSampleId(list.getSampleFromSlide(_slideID));
	}
}