package com.ardais.bigr.iltds.btx;

import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 * Represent the details of a Pathology/R&D picklist transaction.
 * <p>
 * The transaction-specific fields are described below. See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setPickList(String) PickList}: The pick list ID.</li>
 * <li>{@link #setDeliverTo(String) DeliverTo}: The histologist to send the
 * picked samples to.</li>
 * <li>{@link #setBTXType(String) BTXType}: The transaction type.  Note: This field must
 * be set in this transaction to differentiate between a pathology pick list and
 * an R&D pick list. </li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields.</h4>
 * <ul>
 * <li>{@link #setSampleList(IdList) SampleList}: The list of samples belonging
 * to the pick list.</li>
 * </ul>
 * 
 */
public class BTXDetailsPickListRequest extends BTXDetails implements java.io.Serializable {
	private static final long serialVersionUID = 6059289927971380918L;

	private String _pickList = null;
	private String _deliverTo = null;
	private IdList _sampleList = null;

	private String _BTXType = null;

	public BTXDetailsPickListRequest() {
		super();
	}
	public void describeIntoHistoryRecord(BTXHistoryRecord history) {
		super.describeIntoHistoryRecord(history);

		history.setBTXType(getBTXType());
		history.setAttrib1(getDeliverTo());
		history.setAttrib2(getPickList());
		history.setIdList1(getSampleList());
	}
	protected String doGetDetailsAsHTML() {
		// NOTE: This method must not make use of any fields that aren't
		// set by the populateFromHistoryRecord method.
		//
		// For this object type, the fields we can use here are:
		//   getDeliverTo
		//   getPickList
		//   getSampleList
        
    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();

		StringBuffer sb = new StringBuffer(2048);

		// The result has this form:
		//    Pick list <pick list> for delivery to <deliver to> includes
		//    samples <sample list>

		sb.append("Pick list ");
		sb.append(IcpUtils.prepareLink(getPickList(), securityInfo));
		sb.append(" for delivery to ");
		Escaper.htmlEscape(getDeliverTo(), sb);
		sb.append(" includes samples ");
		getSampleList().appendICPHTML(sb, securityInfo);

		return sb.toString();
	}
	/**
	 * Return the business transaction type code for the transaction that this
	 * class represents.  Derived classes must override this method.
	 *
	 * @return the transaction type code.
	 */
	public String getBTXType() {
		if (_BTXType != null) {
			return _BTXType;
		} else {
			return BTXDetails.BTX_TYPE_PATH_SAMPLE_REQUEST;
		}
	}
	/**
	 * Get the histologist whom the sample were delivered to. This is a required input field.
	 * 
	 * @return the histologist id.
	 */
	public java.lang.String getDeliverTo() {
		return _deliverTo;
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

		if (_sampleList != null) {
			set.addAll(_sampleList.getDirectlyInvolvedObjects());
		}

		set.add(_pickList);

		return set;
	}
	/**
	 * Get the pick list ID.  This is a required input field.
	 *
	 * @return the picklist id.
	 */
	public String getPickList() {
		return _pickList;
	}
	/**
	 * Get the samples associated with the picklist.  This is an output field.
	 *
	 * @return IdList of samples
	 */
	public IdList getSampleList() {
		return _sampleList;
	}
	/**
	 * Populate this instance of the object using a BTXHistoryRecord object.
	 * 
	 * @param history com.ardais.bigr.iltds.btx.BTXHistoryRecord
	 */
	public void populateFromHistoryRecord(BTXHistoryRecord history) {
		//The btx type needs to be set before the superclass
		//executes populateFromHistoryRecord to handle the
		//use of this data object by multiple transactions.
		setBTXType(history.getBTXType());

		super.populateFromHistoryRecord(history);

		setDeliverTo(history.getAttrib1());
		setPickList(history.getAttrib2());
		setSampleList(history.getIdList1());

	}
	/**
	 * Set the transaction type.  Used to differentiate between path/R&D pick lists.
	 * This method does not exist in other BTX object because this object is used for
	 * multiple transactions. 
	 * 
	 * @param new_BTXType java.lang.String
	 */
	public void setBTXType(java.lang.String newBTXType) {
		_BTXType = newBTXType;
	}
	/**
	 * Set the histologist id whom the samples were delivered.
	 * 
	 * @param new_deliverTo java.lang.String
	 */
	public void setDeliverTo(java.lang.String newDeliverTo) {
		_deliverTo = newDeliverTo;
	}
	/**
	 * Set the pick list id.  
	 * 
	 * @param new_pickList java.lang.String
	 */
	public void setPickList(java.lang.String newPickList) {
		_pickList = newPickList;
	}
	/**
	 * Set the list of samples in the pick list.
	 * 
	 * @param new_sampleList IdList
	 */
	public void setSampleList(IdList newSampleList) {
		_sampleList = newSampleList;
	}
}