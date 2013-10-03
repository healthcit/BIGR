package com.ardais.bigr.lims.btx;

import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.lims.javabeans.SlideData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 * Represent the details of a path lab slide business transaction.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setLimsUserId(String) limsUserId}: The id of the LIMS user performing the transaction.</li>
 * <li>{@link #setSlideData(SlideData) slideData}: A SlideData data bean, holding a slide id.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 */
public class BTXDetailsPathLabSlidesSingle extends BTXDetails implements java.io.Serializable {
	private static final long serialVersionUID = -812332037490696257L;

	private String _limsUserId;
	private SlideData _slideData;
	//NOTE - the SlideData set into this object will not have it's sampleId value specified, as that
	//info is not required for any business functionality for this transaction.  It is, however,
	//required for logging purposes.  Therefore the method that performs the business functionality 
	//for this transaction (BtxPerformerHistologyOperation.performPathLabSlidesSingle) will need to (and
	//does) set the sampleId on the slideData object so it can be logged.
	
	/** 
	 * Constructor
	 */
	public BTXDetailsPathLabSlidesSingle() {
		super();
	}
	
	/**
	 * Returns the limsUserId.
	 * @return String
	 */
	public String getLimsUserId() {
		return _limsUserId;
	}

	/**
	 * Returns the slideData.
	 * @return List
	 */
	public SlideData getSlideData() {
		return _slideData;
	}

	/**
	 * Sets the limsUserId.
	 * @param limsUserId The limsUserId to set
	 */
	public void setLimsUserId(String limsUserId) {
		_limsUserId = limsUserId;
	}

	/**
	 * Sets the slideData.
	 * @param slideData The slideData to set
	 */
	public void setSlideData(SlideData slideData) {
		_slideData = slideData;
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
		history.setAttrib1(getLimsUserId());
		history.setAttrib2(getSlideData().getSampleId());
		history.setAttrib3(getSlideData().getSlideId());
	}
	
	/**
	 * This method is called from the BTXDetails base class when its getDetailsAsHTML() 
	 * method has been called from the BTXHistoryReaderBean.getHistoryDisplayLines 
	 * method. This method must not make use of any fields that aren't set by the 
	 * populateFromHistoryRecord method. For this object type, the fields we can use here 
	 * are:
	 * 		getSampleId()
	 * 		getLimsUserId()
	 * 		getSlideData().getSlideId()
	 *
	 * @return an HTML string that defines how the transaction details are presented
	 * in a transaction-history web page
	 */
  protected String doGetDetailsAsHTML() {
       
        SecurityInfo securityInfo = getLoggedInUserSecurityInfo();

		StringBuffer sb = new StringBuffer(128);

		// The result has this form:
		//    <user id> performed the path lab transaction using slide
		//    <slide id> for sample <sample id>

    Escaper.htmlEscape(getLimsUserId(), sb);
		sb.append(" performed the path lab transaction using slide ");
		sb.append(IcpUtils.prepareLink(getSlideData().getSlideId(), securityInfo));
		sb.append(" for sample ");
		sb.append(IcpUtils.prepareLink(getSlideData().getSampleId(), securityInfo));

		return sb.toString();
	}

	
	/**
	 * Return the business transaction type code for the transaction that this
	 * class represents.
	 *
	 * @return the transaction type code.
	 */
	public String getBTXType() {
		return BTXDetails.BTX_TYPE_PATHLAB_SLIDES_SINGLE;
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

		if (getSlideData() != null) {
			set.add(getSlideData().getSampleId());
			set.add(getSlideData().getSlideId());
		}

		return set;
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
		setLimsUserId(history.getAttrib1());
		SlideData slideData = new SlideData();
		slideData.setSampleId(history.getAttrib2());
		slideData.setSlideId(history.getAttrib3());
		setSlideData(slideData);
	}

}