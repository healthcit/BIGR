package com.ardais.bigr.lims.btx;

import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 * Represent the details of an unpull sample business transaction.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setSampleId(String) sampleId}: The id of the sample to unpull.</li>
 * <li>{@link #setReason(String) reason}: The reason the sample is being unpulled.</li>
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
public class BTXDetailsMarkSampleUnpulled extends BTXDetails implements java.io.Serializable {
	private static final long serialVersionUID = -7037612183266169040L;

	private String _sampleId;
	private String _reason;
  private String _reportMostRecentEval;
  private String _reportedEvalId;
	
	private String _message;
	
	/** 
	 * Constructor
	 */
	public BTXDetailsMarkSampleUnpulled() {
		super();
	}
	
	/**
	 * Returns the reason.
	 * @return String
	 */
	public String getReason() {
		return _reason;
	}

	/**
	 * Returns the sampleId.
	 * @return String
	 */
	public String getSampleId() {
		return _sampleId;
	}

	/**
	 * Sets the reason.
	 * @param reason The reason to set
	 */
	public void setReason(String reason) {
		_reason = reason;
	}

	/**
	 * Sets the sampleId.
	 * @param sampleId The sampleId to set
	 */
	public void setSampleId(String sampleId) {
		_sampleId = sampleId;
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
		history.setAttrib1(getSampleId());
		history.setAttrib2(getReason());
    history.setAttrib3(getReportedEvalId());
	}
	
	/**
	 * This method is called from the BTXDetails base class when its getDetailsAsHTML() 
	 * method has been called from the BTXHistoryReaderBean.getHistoryDisplayLines 
	 * method. This method must not make use of any fields that aren't set by the 
	 * populateFromHistoryRecord method. For this object type, the fields we can use here 
	 * are:
	 * 		getSampleId()
	 * 		getReason()
   *    getReportedEvalId()
	 *
	 * @return an HTML string that defines how the transaction details are presented
	 * in a transaction-history web page
	 */
  protected String doGetDetailsAsHTML() {
       
        SecurityInfo securityInfo = getLoggedInUserSecurityInfo();

		StringBuffer sb = new StringBuffer(128);

		// If no evaluation was marked reported as a result of this transaction, the result has this form:
		//    Sample <sample id> was marked unpulled with a reason of <reason>.
    // If an evaluation was marked reported as part of unpulling the sample, an additional
    // line of text is included:
    //    Sample <sample id> was marked unpulled with a reason of <reason>, causing evaluation <eval id>
    //    to be marked reported.

		sb.append("Sample ");
		sb.append(IcpUtils.prepareLink(getSampleId(), securityInfo));
		sb.append(" was marked unpulled with a reason of \"");
		Escaper.htmlEscape(getReason(),sb);
    sb.append("\"");
    if (getReportedEvalId() != null) {
      sb.append(", causing evaluation ");
      sb.append(getReportedEvalId());
      sb.append(" to be marked reported");
    }
		sb.append(".");

		return sb.toString();
	}

	
	/**
	 * Return the business transaction type code for the transaction that this
	 * class represents.
	 *
	 * @return the transaction type code.
	 */
	public String getBTXType() {
		return BTXDetails.BTX_TYPE_MARK_SAMPLE_UNPULLED;
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

		set.add(getSampleId());
    //if we've reported an evaluation as part of unpulling the sample, include
    //it in the list of involved objects.
    if (getReportedEvalId() != null) {
      set.add(getReportedEvalId());
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
		setSampleId(history.getAttrib1());
		setReason(history.getAttrib2());
    setReportedEvalId(history.getAttrib3());
	}

	/**
	 * Returns the message.
	 * @return String
	 */
	public String getMessage() {
		return _message;
	}

	/**
	 * Sets the message.
	 * @param message The message to set
	 */
	public void setMessage(String message) {
		_message = message;
	}

  /**
   * @return
   */
  public String getReportMostRecentEval() {
    return _reportMostRecentEval;
  }

  /**
   * @param string
   */
  public void setReportMostRecentEval(String string) {
    _reportMostRecentEval = string;
  }

  /**
   * @return
   */
  public String getReportedEvalId() {
    return _reportedEvalId;
  }

  /**
   * @param string
   */
  public void setReportedEvalId(String string) {
    _reportedEvalId = string;
  }

}